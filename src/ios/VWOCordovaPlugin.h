#import <Cordova/CDVPlugin.h>

@interface VWOCordovaPlugin : CDVPlugin {
}

// The hooks for our plugin commands
- (void)launchSynchronously:(CDVInvokedUrlCommand *)command;

- (void)launchAsynchronously:(CDVInvokedUrlCommand *)command;

- (void)launchAsynchronouslyWithCallback:(CDVInvokedUrlCommand *)command;

- (void)getVariationForKey: (CDVInvokedUrlCommand *)command;

- (void)getVariationForKeyWithDefaultValue: (CDVInvokedUrlCommand *)command;

- (void)markConversionForGoal: (CDVInvokedUrlCommand *)command;

- (void)markConversionForGoalWithValue: (CDVInvokedUrlCommand *)command;

- (void)version: (CDVInvokedUrlCommand *)command;

- (void)setCustomVariable: (CDVInvokedUrlCommand *)command;

@end
