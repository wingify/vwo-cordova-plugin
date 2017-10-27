#import "VWOCordovaPlugin.h"
#import "VWO.h"

#import <Cordova/CDVAvailability.h>

@implementation VWOCordovaPlugin

- (void)setLogLevel:(CDVInvokedUrlCommand *)command {
    int logLevel = [[command argumentAtIndex:0] intValue];
    switch (logLevel) {//Refer plugin.js for log level values
        case 1: [VWO setLogLevel:VWOLogLevelDebug]; break;
        case 2: [VWO setLogLevel:VWOLogLevelInfo]; break;
        case 3: [VWO setLogLevel:VWOLogLevelWarning]; break;
        case 4: [VWO setLogLevel:VWOLogLevelError]; break;
        case 5: [VWO setLogLevel:VWOLogLevelNone]; break;
        default: break;
    }
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void)launchSynchronously:(CDVInvokedUrlCommand *)command {
    NSString* apiKey = [command argumentAtIndex:0];
    double timeout = [[command argumentAtIndex:1] doubleValue];
    [VWO launchSynchronouslyForAPIKey:apiKey timeout:timeout];
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"VWO Initialized"];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)launch:(CDVInvokedUrlCommand *)command {
    NSString* apiKey = [command argumentAtIndex:0];
    [VWO launchForAPIKey:apiKey];
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"VWO Initialized"];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)launchWithCallback:(CDVInvokedUrlCommand *)command {
    NSString* apiKey = [command argumentAtIndex:0];

    [VWO launchForAPIKey:apiKey completion:^{
        CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"VWO Initialized"];
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    } failure:^(NSString * _Nonnull error) {
        CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Error"];
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

- (void)variationForKey:(CDVInvokedUrlCommand *)command {
    NSString *key = [command argumentAtIndex:0];
    id variation = [VWO variationForKey:key];
    NSDictionary *resultDictionary = @{ @"variableKey": key, @"variableValue": variation };

    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:resultDictionary];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)markConversionForGoal:(CDVInvokedUrlCommand *)command {
    NSString *goal = [command argumentAtIndex:0];
    [VWO markConversionForGoal:goal];
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void)markConversionForGoalWithValue:(CDVInvokedUrlCommand *)command {
    NSString *goal = [command argumentAtIndex:0];
    double value = [[command argumentAtIndex:1] doubleValue];
    [VWO markConversionForGoal:goal withValue:value];
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void)setCustomVariable:(CDVInvokedUrlCommand *)command {
    NSString *key = [command argumentAtIndex:0];
    NSString *value = [command argumentAtIndex:1];
    [VWO setCustomVariable:key withValue:value];
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void)version:(CDVInvokedUrlCommand *)command {
    NSString *version = [VWO version];
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:version];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
