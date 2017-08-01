#import "VWOCordovaPlugin.h"
#import "VWO.h"

#import <Cordova/CDVAvailability.h>

@implementation VWOCordovaPlugin

- (void)pluginInitialize {
}

- (void)launchSynchronously:(CDVInvokedUrlCommand *)command {
  NSString* callbackId = [command callbackId];
  NSString* apiKey = [[command arguments] objectAtIndex:0];
  
  CDVPluginResult* result;
  if(apiKey != nil){
    result = [CDVPluginResult
                              resultWithStatus:CDVCommandStatus_OK
                              messageAsString:@"VWO initialized"];
    [VWO launchSynchronouslyForAPIKey:apiKey];
  }else{
    result = [CDVPluginResult
                              resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)launchAsynchronously:(CDVInvokedUrlCommand *)command{
  NSString* callbackId = [command callbackId];
  NSString* apiKey = [[command arguments] objectAtIndex:0];

  CDVPluginResult* result;
  if(apiKey != nil){
    result = [CDVPluginResult 
                            resultWithStatus: CDVCommandStatus_OK
                            messageAsString:@"VWO Initialized"];
    [VWO launchForAPIKey:apiKey];
  }else{
    result = [CDVPluginResult 
                            resultWithStatus:CDVCommandStatus_ERROR];
  }
  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)launchAsynchronouslyWithCallback:(CDVInvokedUrlCommand *)command{
  NSString* callbackId = [command callbackId];
  NSString* apiKey = [[command arguments] objectAtIndex:0];

  CDVPluginResult* result;
  if(apiKey != nil){
    result = [CDVPluginResult
                            resultWithStatus: CDVCommandStatus_OK
                            messageAsString:@"VWO Initialized"];
    [VWO launchForAPIKey:apiKey completion:^{
      [self.commandDelegate sendPluginResult:result callbackId:callbackId];
    }];
  }else{
    result = [CDVPluginResult
                            resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:result callbackId:callbackId];
  }

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)getVariationForKey: (CDVInvokedUrlCommand *)command {
  NSString* callbackId = [command callbackId];
  NSString* key = [[command arguments] objectAtIndex:0];

  id variation = [VWO variationForKey:key];

  CDVPluginResult* result;
  if (variation != nil) {
    NSDictionary *resultDictionary = @{
        @"variableKey": key,
        @"variableValue": variation
      };
    result = [CDVPluginResult
                            resultWithStatus:CDVCommandStatus_OK
                            messageAsDictionary:resultDictionary];
  }else{
    result = [CDVPluginResult
              resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)getVariationForKeyWithDefaultValue: (CDVInvokedUrlCommand *)command {
  NSString* callbackId = [command callbackId];
  NSString* key = [[command arguments] objectAtIndex:0];
  NSObject* object = [[command arguments] objectAtIndex:1];

  id variation = [VWO variationForKey:key defaultValue:object];

  CDVPluginResult* result;
  if (variation != nil) {
    NSDictionary *resultDictionary = @{
        @"variableKey": key,
        @"variableValue": variation
      };
    result = [CDVPluginResult
                            resultWithStatus:CDVCommandStatus_OK
                            messageAsDictionary:resultDictionary];
  }else{
    result = [CDVPluginResult
              resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)markConversionForGoal: (CDVInvokedUrlCommand *)command{
  NSString* callbackId = [command callbackId];
  NSString* conversionGoal = [[command arguments] objectAtIndex:0];

  CDVPluginResult* result;
  if(conversionGoal!=nil){

    [VWO markConversionForGoal:conversionGoal];
    result = [CDVPluginResult
                            resultWithStatus:CDVCommandStatus_OK
                            messageAsString:@"Goal marked for Conversion!"];
  }else{
    result = [CDVPluginResult
              resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];

}

- (void)markConversionForGoalWithValue: (CDVInvokedUrlCommand *)command{
  NSString* callbackId = [command callbackId];
  NSString* conversionGoal = [[command arguments] objectAtIndex:0];
  id value = [[command arguments] objectAtIndex:1];

  CDVPluginResult* result;
  if(conversionGoal!=nil){
    [VWO markConversionForGoal:conversionGoal withValue:[value doubleValue]];
    result = [CDVPluginResult
                            resultWithStatus:CDVCommandStatus_OK
                            messageAsString:@"Goal marked for Conversion!"];
  }else{
    result = [CDVPluginResult
              resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];

}

- (void)version: (CDVInvokedUrlCommand *)command{
  NSString* callbackId = [command callbackId];

  NSString* ver;
  ver = [VWO version];
  CDVPluginResult* result = [CDVPluginResult
                                          resultWithStatus:CDVCommandStatus_OK];

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

- (void)setCustomVariable: (CDVInvokedUrlCommand *)command{
  NSString* callbackId = [command callbackId];
  NSString* key = [[command arguments] objectAtIndex:0];
  NSString* value = [[command arguments] objectAtIndex:1];

  [VWO setCustomVariable:key withValue:value];

  CDVPluginResult* result = [CDVPluginResult
                                          resultWithStatus:CDVCommandStatus_OK
                                          messageAsString:@"Custom Variable set"];

  [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}


@end
