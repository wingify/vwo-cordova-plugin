/**
 */
package android;

import com.vwo.mobile.Initializer;
import com.vwo.mobile.VWO;
import com.vwo.mobile.VWOConfig;
import com.vwo.mobile.events.VWOStatusListener;
import com.vwo.mobile.listeners.ActivityLifecycleListener;
import com.vwo.mobile.utils.VWOLog;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class VWOCordovaPlugin extends CordovaPlugin {

    private static final String TAG = VWOCordovaPlugin.class.getSimpleName();
    private VWOConfig mConfig;
    private ActivityLifecycleListener listener;

    private static final int LOG_LEVEL_DEBUG = 1;
    private static final int LOG_LEVEL_INFO = 2;
    private static final int LOG_LEVEL_WARNING = 3;
    private static final int LOG_LEVEL_ERROR = 4;
    private static final int LOG_LEVEL_OFF = 5;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        listener = new ActivityLifecycleListener();
        mConfig = new VWOConfig.Builder().setLifecycleListener(listener).build();
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("launchSynchronously")) {

            String apiKey = args.getString(0);
            Long timeout = args.getLong(1);
            launchSynchronously(apiKey, timeout, callbackContext);
            return true;

        } else if (action.equals("launch")) {

            String apiKey = args.getString(0);
            launchAsynchronously(apiKey, callbackContext);
            return true;

        } else if (action.equals("launchWithCallback")) {

            String apiKey = args.getString(0);
            launchAsynchronouslyWithCallback(apiKey, callbackContext);
            return true;

        } else if (action.equals("setCustomVariable")) {

            String key = args.getString(0);
            String value = args.getString(1);
            setCustomVariable(key, value, callbackContext);
            return true;

        } else if (action.equals("version")) {

            getVersion(callbackContext);
            return true;

        } else if (action.equals("variationForKey")) {

            String key = args.getString(0);
            getVariation(key, callbackContext);
            return true;

        } else if (action.equals("markConversionForGoal")) {

            String goalIdentifier = args.getString(0);
            markConversionForGoal(goalIdentifier, callbackContext);
            return true;

        } else if (action.equals("markConversionForGoalWithValue")) {

            String goalIdentifier = args.getString(0);
            double value = Double.parseDouble(args.getString(1));
            markConversionForGoalWithValue(goalIdentifier, value, callbackContext);
            return true;

        } else if (action.equals("setLogLevel")) {
            int logLevel = args.getInt(0);
            setLogLevel(logLevel, callbackContext);
        }
        return false;
    }

    private void setLogLevel(final int level, final CallbackContext callbackContext) {
        final int logLevel;
        switch (level) {
            case LOG_LEVEL_DEBUG:
                logLevel = VWOLog.CONFIG;
                break;
            case LOG_LEVEL_INFO:
                logLevel = VWOLog.INFO;
                break;
            case LOG_LEVEL_WARNING:
                logLevel = VWOLog.WARNING;
                break;
            case LOG_LEVEL_ERROR:
                logLevel = VWOLog.SEVERE;
                break;
            case LOG_LEVEL_OFF:
                logLevel = VWOLog.OFF;
                break;
            default:
                VWOLog.e(VWOLog.CONFIG_LOGS, "Invalid log level : " + level, false, false);
                logLevel = -1;
        }

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if(logLevel == -1) {
                    callbackContext.error("Failed to change log level");
                } else {
                    VWOLog.setLogLevel(logLevel);
                    callbackContext.success("Log level changed successfully");
                }
            }
        });
    }

    private void markConversionForGoal(final String goalIdentifier, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                VWO.markConversionForGoal(goalIdentifier);
                callbackContext.success("Conversion marked");
            }
        });
    }

    private void markConversionForGoalWithValue(final String goalIdentifier, final double value, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                VWO.markConversionForGoal(goalIdentifier, value);
                callbackContext.success("Conversion marked");
            }
        });
    }

    private void getVariation(final String key, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                JSONObject wrapperObject = new JSONObject();
                Object object = VWO.getVariationForKey(key);

                if (object == null) {
                    try {
                        wrapperObject.put(key, JSONObject.NULL);
                        callbackContext.success(wrapperObject);
                    } catch (JSONException exception) {
                        VWOLog.e(VWOLog.DATA, exception, false, false);
                        callbackContext.error(exception.getMessage());
                    }
                } else {
                    try {
                        wrapperObject.put(key, object);
                        callbackContext.success(wrapperObject);
                    } catch (JSONException exception) {
                        VWOLog.e(VWOLog.DATA, exception, false, false);
                        callbackContext.error(exception.getMessage());
                    }
                }
            }
        });
    }

    private void setCustomVariable(final String key, final String value, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                VWO.setCustomVariable(key, value);
                callbackContext.success("Custom Variable Set");
            }
        });
    }

    private void launchSynchronously(final String apiKey, final Long timeout, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                VWO.with(cordova.getActivity(), apiKey).config(mConfig).launchSynchronously(timeout);
                callbackContext.success("VWO Initialized");
            }
        });
    }

    private void getVersion(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                callbackContext.success(VWO.version());
            }
        });
    }

    private void launchAsynchronously(final String apiKey, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {

                Initializer initializer = VWO.with(cordova.getActivity(), apiKey).config(mConfig);
                if(callbackContext != null) {
                    initializer.launch(new VWOStatusListener() {
                        @Override
                        public void onVWOLoaded() {
                            callbackContext.success("VWO Initialized");
                        }

                        @Override
                        public void onVWOLoadFailure(String reason) {
                            callbackContext.error(reason);
                        }
                    });
                } else {
                    initializer.launch();
                }
            }
        });
    }

    private VWOConfig getVWOConfig(HashMap<String, String> map) {
        return new VWOConfig
                .Builder()
                .setLifecycleListener(new ActivityLifecycleListener())
                .setCustomSegmentationMapping(map)
                .build();
    }

    private HashMap<String, String> jsonToMap(JSONObject json) throws JSONException {
        HashMap<String, String> retMap = new HashMap<String, String>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private void launchAsynchronouslyWithCallback(final String apiKey, final CallbackContext callbackContext) {

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                VWO.with(cordova.getActivity(), apiKey).config(mConfig).launch(new VWOStatusListener() {
                    @Override
                    public void onVWOLoaded() {
                        callbackContext.success("VWO Loaded");
                    }

                    @Override
                    public void onVWOLoadFailure(String reason) {
                        callbackContext.error("VWO Load Failure");
                    }
                });
            }
        });
    }

    @Override
    public void onPause(boolean multitasking) {
        listener.onPause();
        super.onPause(multitasking);
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        listener.onResume();
    }

    @Override
    public void onStop() {
        listener.onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.onStart();
    }

    private HashMap<String, String> toMap(JSONObject object) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            String value = (String) object.get(key);

            map.put(key, value);
        }
        return map;
    }

    private List<String> toList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++) {
            String value = (String) array.get(i);
            list.add(value);
        }
        return list;
    }
}
