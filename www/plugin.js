
var exec = require('cordova/exec');

var PLUGIN_NAME = 'VWOCordovaPlugin';

//noinspection JSUnusedGlobalSymbols

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
VWO.setLogLevel: function(success, error, level){
  if (typeof level !== 'number') {
    throw new Error('Value must be a number');
  }
  exec(success, error, PLUGIN_NAME, 'setLogLevel', [level]);
};

/** Launch VWO Synchronously
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param apiKey {string} The App Key generated in VWO's console
 * @param timeout {number} Connection Timeout of API
 */
VWO.launchSynchronously: function(success, error, apiKey, timeout){
  if (typeof timeout !== 'number') {
    throw new Error('Value must be a number');
  }
  if (!apiKey) {
    throw new Error('Must pass in a API Key');
  }
  exec(success, error, PLUGIN_NAME, 'launchSynchronously', [apiKey, timeout]);
};

/** Launch VWO Asynchronously
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param apiKey {string} The App Key generated in VWO's console
 */
VWO.launch: function(success, error, apiKey){
  if(!apiKey){
    throw new Error('Must pass in a API Key');
  }
  exec(success, error, PLUGIN_NAME, 'launch', [apiKey]);
};

/** Launch VWO Asynchronously with Callback
 *
 * @param success {callback} Success Callback
 * @param failureCallback {callback} Failure Callback
 * @param apiKey {string} The App Key generated in VWO's console
 */
VWO.launchWithCallback: function(success, error, apiKey){
  if(!apiKey){
    throw new Error('Must pass in a API Key');
  }
  exec(success, error, PLUGIN_NAME, 'launchWithCallback', [apiKey]);
};

/** Get the Variation object for a key
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param key {string} Key for the campaign
 *
 * @return Variation object
 */
VWO.variationForKey: function (success, error, key) {
  if (!key) {
    throw new Error('Must pass Key for Campaign');
  }
  exec(success, error, PLUGIN_NAME, 'variationForKey', [key]);
};

/** Get the Variation object for a key. Default value in case key is not found or null.
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param key {string} Key for the campaign
 * @param defaultValue {value} Default Valye
 * @return Variation object
 */
VWO.variationForKeyWithDefaultValue: function(success, error, key, defaultValue){
  var value = variationForKey(function(){
    console.log("variationForKeyWithDefaultValue Success");
  }, function () {
    console.log("variationForKeyWithDefaultValue Success");
  }, key)
};

/** Mark the conversion for goal
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param goal {string} Goal's name
 */
VWO.markConversionForGoal:function (success, error, goal) {
  if (!goal) {
    throw new Error('Must pass Goal name');
  }
  exec(success, error, PLUGIN_NAME, 'markConversionForGoal', [goal]);
};

/** Mark the conversion for goal with value
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param goal {string} Goal's name
 * @param value {double} Goal's value
 */
VWO.markConversionForGoalWithValue:function (success, error, goal, value) {
  if (typeof value !== 'number') {
    throw new Error('Value must be a number');
  }
  if (!goal) {
    throw new Error('Must pass Goal name');
  }
  exec(success, error, PLUGIN_NAME, 'markConversionForGoalWithValue', [goal, value]);
};
  
/** Set custom variable by passing key and value
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 * @param key {string} Key
 * @param value {string} Value
 */
VWO.setCustomVariable: function(success, error, key, value){
  if(!key || !value){
    throw new Error('Key or Value is null');
  }
  exec(success, error, PLUGIN_NAME, 'setCustomVariable', [key, value]);
};

/** Get the version of the SDK
 *
 * @param success {callback} Success Callback
 * @param error {callback} Failure Callback
 */
VWO.version: function(success, error){
  exec(success, error, PLUGIN_NAME, 'version', []);
};

module.exports = VWO;
