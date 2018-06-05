#import <Cordova/CDVPlugin.h>

@interface VWOCordovaPlugin : CDVPlugin {
}

- (void)setLogLevel:(CDVInvokedUrlCommand *)command;

- (void)launchSynchronously:(CDVInvokedUrlCommand *)command;

- (void)launch:(CDVInvokedUrlCommand *)command;

- (void)intForKey:(CDVInvokedUrlCommand *)command;

- (void)floatForKey:(CDVInvokedUrlCommand *)command;

- (void)boolForKey:(CDVInvokedUrlCommand *)command;

- (void)stringForKey:(CDVInvokedUrlCommand *)command;

- (void)objectForKey:(CDVInvokedUrlCommand *)command;

- (void)variationNameForTestKey:(CDVInvokedUrlCommand *)command;

- (void)trackConversion:(CDVInvokedUrlCommand *)command;

- (void)trackConversionWithValue:(CDVInvokedUrlCommand *)command;

- (void)version:(CDVInvokedUrlCommand *)command;

@end
