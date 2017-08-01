/**
 */
package android;

import com.vwo.mobile.VWO;
import com.vwo.mobile.VWOConfig;
import com.vwo.mobile.events.VWOStatusListener;
import com.vwo.mobile.utils.VWOLog;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VWOCordovaPlugin extends CordovaPlugin {

  private static final String TAG = VWOCordovaPlugin.class.getSimpleName();

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    VWOLog.setLogLevel(VWOLog.ALL);
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("launchSynchronously")) {

      String apiKey = args.getString(0);
      launchSynchronously(apiKey, callbackContext);
      return true;

    } else if (action.equals("launchAsynchronously")) {

      String apiKey = args.getString(0);
      launchAsynchronously(apiKey, callbackContext);
      return true;

    } else if(action.equals("launchAsynchronouslyWithCallback")){

      String apiKey = args.getString(0);
      launchAsynchronouslyWithCallback(apiKey, callbackContext);
      return true;

    } else if (action.equals("launchWithConfig")) {

      String apiKey = args.getString(0);
      HashMap<String, String> map = jsonToMap(args.getJSONObject(1));
      launchWithConfig(apiKey, map, callbackContext);
      return true;

    } else if (action.equals("setCustomVariable")) {

      String key = args.getString(0);
      String value = args.getString(1);
      setCustomVariable(key, value, callbackContext);
      return true;

    } else if (action.equals("version")) {

      getVersion(callbackContext);
      return true;

    } else if (action.equals("getVariationForKey")) {

      String key = args.getString(0);
      getVariation(key, callbackContext);
      return true;

    } else if (action.equals("getVariationForKeyWithValue")) {

      String key = args.getString(0);
      if (args.length()>1 && args.getJSONObject(1) != JSONObject.NULL) {
        Object control = args.getJSONObject(1);
        getVariationWithDefaultValue(key, control, callbackContext);
      } else {
        getVariation(key, callbackContext);
      }
      return true;

    }else if (action.equals("markConversionForGoal")) {

      String goalIdentifier = args.getString(0);
      markConversionForGoal(goalIdentifier, callbackContext);
      return true;

    }else if (action.equals("markConversionForGoalWithValue")) {

      String goalIdentifier = args.getString(0);
      double value = Double.parseDouble(args.getString(1));
      markConversionForGoalWithValue(goalIdentifier, value, callbackContext);
      return true;

    }
    return false;
  }

  private void markConversionForGoal(final String goalIdentifier, final CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        VWO.markConversionForGoal(goalIdentifier);
      }
    });
    callbackContext.success("Conversion marked");
  }

  private void markConversionForGoalWithValue(final String goalIdentifier, final double value, final CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        VWO.markConversionForGoal(goalIdentifier, value);
      }
    });
    callbackContext.success("Conversion marked");
  }

  private void getVariation(final String key, final CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        JSONObject object = (JSONObject) VWO.getVariationForKey(key);
          if(object!=null){
            callbackContext.success(object);
          }
        }
    });
  }

  private void getVariationWithDefaultValue(final String key, final Object control, final CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        JSONObject object = (JSONObject) VWO.getVariationForKey(key, control);
        callbackContext.success(object);
      }
    });
  }

  private void setCustomVariable(final String key, final String value, CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        VWO.setCustomVariable(key, value);
      }
    });
    callbackContext.success("Custom Variable Set");
  }

  private void launchSynchronously(final String apiKey, final CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        VWO.with(cordova.getActivity(), apiKey).launchSynchronously();
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

        VWO.with(cordova.getActivity(), apiKey).launch();

        callbackContext.success("VWO Initialized");
      }
    });
  }

  private void launchWithConfig(final String apiKey, final HashMap<String, String> configData, final CallbackContext callbackContext) {

    final VWOConfig vwoConfig = getVWOConfig(configData);

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {

        VWO.with(cordova.getActivity(), apiKey).config(vwoConfig).launchSynchronously();

        callbackContext.success("VWO Initialized");
      }
    });
  }

  private VWOConfig getVWOConfig(HashMap<String, String> map) {
    return new VWOConfig
      .Builder()
      .setCustomSegmentationMapping(map)
      .build();
  }
  
  private HashMap<String, String> jsonToMap(JSONObject json) throws JSONException {
    HashMap<String, String> retMap = new HashMap<String, String>();

    if(json != JSONObject.NULL) {
      retMap = toMap(json);
    }
    return retMap;
  }

  private void launchAsynchronouslyWithCallback(final String apiKey, final CallbackContext callbackContext){

    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        VWO.with(cordova.getActivity(), apiKey).launch(new VWOStatusListener() {
            @Override public void onVwoLoaded() {
              callbackContext.success("VWO Loaded");
            }

            @Override public void onVwoLoadFailure() {
              callbackContext.error("VWO Load Failure");
            }
        });      
      }
    });
  }

  private HashMap<String, String> toMap(JSONObject object) throws JSONException {
    HashMap<String, String> map = new HashMap<String, String>();
    Iterator<String> keysItr = object.keys();
    while(keysItr.hasNext()) {
      String key = keysItr.next();
      String value = (String) object.get(key);

      map.put(key, value);
    }
    return map;
  }

  private List<String> toList(JSONArray array) throws JSONException {
    List<String> list = new ArrayList<String>();
    for(int i = 0; i < array.length(); i++) {
      String value = (String) array.get(i);
      list.add(value);
    }
    return list;
  }
}
