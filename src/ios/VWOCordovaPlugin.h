#import <Cordova/CDVPlugin.h>

@interface VWOCordovaPlugin : CDVPlugin {
}

- (void)setLogLevel:(CDVInvokedUrlCommand *)command;

- (void)launchSynchronously:(CDVInvokedUrlCommand *)command;

- (void)launchWithCallback:(CDVInvokedUrlCommand *)command;

- (void)variationForKey:(CDVInvokedUrlCommand *)command;

- (void)trackConversion:(CDVInvokedUrlCommand *)command;

- (void)trackConversionWithValue:(CDVInvokedUrlCommand *)command;

- (void)version:(CDVInvokedUrlCommand *)command;

@end
