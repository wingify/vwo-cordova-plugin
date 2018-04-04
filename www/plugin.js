
var exec = require('cordova/exec');

var PLUGIN_NAME = 'VWOCordovaPlugin';

module.exports = VWOConfig;

// Default config object
const vwoConfig = {
  optOut: false,
  customVariables: {}
  disablePreview: false
}

var VWO = function () {};

VWO.logLevelDebug   = 1
VWO.logLevelInfo    = 2
VWO.logLevelWarning = 3
VWO.logLevelError   = 4
VWO.logLevelOff     = 5

/** Launch VWO Synchronously
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param level {number} Log level
 */
VWO.setLogLevel = function(level){
  if (typeof level !== 'number') {
    throw new Error('Invalid log level');
  }
  exec(function(data) {}, function(error) {}, PLUGIN_NAME, 'setLogLevel', [level]);
};

function validatedConfig(config) {
  if (config === null  || config === undefined) {
    return vwoConfig;
  }
  var finalConfig = {};
  for (prop in vwoConfig) {
      if (typeof(config[prop]) !== "undefined" && typeof(config[prop]) === typeof(vwoConfig[prop])) {
        finalConfig[prop] = config[prop];
      } else {
        finalConfig[prop] = vwoConfig[prop];
      }
  }
  return finalConfig;
}

/** Launch VWO synchronously
 *
 * @param apiKey {string} The App Key generated in VWO's console
 * @param timeout {number} Connection Timeout of API
 * @param config {Object} Launch configuration
 */
VWO.launchSynchronously = function(apiKey, timeout, config){
  if (typeof timeout !== 'number') {
    throw new Error('timeout must be a number');
  }
  if(!apiKey && typeof level !== 'string') {
    throw new Error('Invalid API Key');
  }
  exec(function(data) {},
    function(error) {},
    PLUGIN_NAME,
    'launchSynchronously',
    [apiKey, timeout, validatedConfig(config)]
  );
};


/** Launch VWO asynchronously with Callback
 *
 * @param apiKey {string} The App Key generated in VWO's console
 * @param config {Object} Launch configuration
 * @param success {callback} Success Callback
 * @param failureCallback {callback} Failure Callback
 */
VWO.launchWithCallback = function(apiKey, config, success, error){
  if(!apiKey) {
    throw new Error('Must pass in a API Key');
  }

  exec(success, error, PLUGIN_NAME, 'launchWithCallback', [apiKey, validatedConfig(config)]);
};

/** Get the Variation object for a key
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param key {string} Key for the campaign
 *
 * @return Variation object
 *
 * @deprecated Deprecated in favour of variationForKeyWithDefaultValue
 */
VWO.variationForKey = function (key, success) {
  console.warn("Deprecated. Use VWO.variationForKeyWithDefaultValue instead."); 
  if (!key) {
    throw new Error('Must pass Key for Campaign');
  }
  exec( function(wrappedData) {
    success(wrappedData[key]);
  }, function(error) {}, PLUGIN_NAME, 'variationForKey', [key]);
};

/** Get the Variation object for a key. Default value in case key is not found or null.
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param key {string} Key for the campaign
 * @param defaultValue {value} Default Valye
 * @return Variation object
 */
VWO.variationForKeyWithDefaultValue = function(key, defaultValue, success){
  var value = VWO.variationForKey(key, function(data) {
      if (!data) {
      success(defaultValue);
    } else {
      success(data);
    }
  });
};

/** Mark the conversion for goal
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param goal {string} Goal's name
 */
VWO.trackConversion = function (goal) {
  if (!goal) {
    throw new Error('Must pass Goal name');
  }
  exec(function(data) {}, function(error) {}, PLUGIN_NAME, 'trackConversion', [goal]);
};

/** Mark the conversion for goal with value
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param goal {string} Goal's name
 * @param value {double} Goal's value
 */
VWO.trackConversionWithValue = function (goal, value) {
  if (typeof value !== 'number') {
    throw new Error('Value must be a number');
  }
  if (!goal) {
    throw new Error('Must pass Goal name');
  }
  exec(function(data) {}, function(error) {}, PLUGIN_NAME, 'trackConversionWithValue', [goal, value]);
};

/** Get the version of the SDK
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 */
VWO.version = function(success) {
  exec(success, function(error) {}, PLUGIN_NAME, 'version', []);
};

module.exports = VWO;
