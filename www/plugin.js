
var exec = require('cordova/exec');

var PLUGIN_NAME = 'VWOCordovaPlugin';

//noinspection JSUnusedGlobalSymbols

var VWO = {

  /** Launch VWO Synchronously
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param apiKey {string} The App Key generated in VWO's console
   */
  launchSynchronously: function(successCallback, failureCallback, apiKey){
    if (!apiKey) {
      throw new Error('Must pass in a API Key');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'launchSynchronously', [apiKey]);
  },

  /** Launch VWO Asynchronously
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param apiKey {string} The App Key generated in VWO's console
   */
  launchAsynchronously: function(successCallback, failureCallback, apiKey){
    if(!apiKey){
      throw new Error('Must pass in a API Key');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'launchAsynchronously', [apiKey]);
  },

  /** Launch VWO Asynchronously with Callback
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param apiKey {string} The App Key generated in VWO's console
   */
  launchAsynchronouslyWithCallback: function(successCallback, failureCallback, apiKey){
    if(!apiKey){
      throw new Error('Must pass in a API Key');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'launchAsynchronouslyWithCallback', [apiKey]);
  },

  /** Get the Variation object for a key
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param key {string} Key for the campaign
   *
   * @return Variation object
   */
  getVariationForKey: function (successCallback, failureCallback, key) {
    if (!key) {
      throw new Error('Must pass Key for Campaign');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'getVariationForKey', [key]);
  },


  /** Get the Variation object for a key. Default value in case key is not found or null.
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param key {string} Key for the campaign
   * @param defaultValue {value} Default Valye
   * @return Variation object
   */
  getVariationForKeyWithDefaultValue: function(successCallback, failureCallback, key, defaultValue){
    if(!key){
      throw new Error('Must pass Key for Campaign');
    }

    exec(successCallback,failureCallback, PLUGIN_NAME, 'getVariationForKeyWithDefaultValue', [key, defaultValue]);
  },

  /** Mark the conversion for goal
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param goal {string} Goal's name
   */
  markConversionForGoal:function (successCallback, failureCallback, goal) {
    if (!goal) {
      throw new Error('Must pass Goal name');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'markConversionForGoal', [goal]);
  },

  /** Mark the conversion for goal with value
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param goal {string} Goal's name
   * @param value {double} Goal's value
   */
  markConversionForGoalWithValue:function (successCallback, failureCallback, goal, value) {
    if (!goal) {
      throw new Error('Must pass Goal name');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'markConversionForGoalWithValue', [goal, value]);
  },
  
  /** Set custom variable by passing key and value
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   * @param key {string} Key
   * @param value {string} Value
   */
  setCustomVariable: function(successCallback, failureCallback, key, value){
    if(!key || !value){
      throw new Error('Key or Value is null');
    }

    exec(successCallback, failureCallback, PLUGIN_NAME, 'setCustomVariable', [key, value]);
  },

  /** Get the version of the SDK
   *
   * @param successCallback {callback} Success Callback
   * @param failureCallback {callback} Failure Callback
   */
  getVersion: function(successCallback, failureCallback){
    exec(successCallback, failureCallback, PLUGIN_NAME, 'version', []);
  }

};

module.exports = VWO;
