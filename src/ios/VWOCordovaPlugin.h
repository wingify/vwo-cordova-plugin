#import <Cordova/CDVPlugin.h>

@interface VWOCordovaPlugin : CDVPlugin {
}

- (void)setLogLevel:(CDVInvokedUrlCommand *)command;

- (void)launchSynchronously:(CDVInvokedUrlCommand *)command;

- (void)launch:(CDVInvokedUrlCommand *)command;

- (void)launchWithCallback:(CDVInvokedUrlCommand *)command;

- (void)variationForKey:(CDVInvokedUrlCommand *)command;

- (void)markConversionForGoal:(CDVInvokedUrlCommand *)command;

- (void)markConversionForGoalWithValue:(CDVInvokedUrlCommand *)command;

- (void)setCustomVariable:(CDVInvokedUrlCommand *)command;

- (void)version:(CDVInvokedUrlCommand *)command;

@end
