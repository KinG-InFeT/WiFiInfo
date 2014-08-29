#import <Cordova/CDVPlugin.h>
#import <SystemConfiguration/CaptiveNetwork.h>

 @interface WiFiInfo : CDVPlugin

- (void)getInfo: (CDVInvokedUrlCommand*) command;

@end
