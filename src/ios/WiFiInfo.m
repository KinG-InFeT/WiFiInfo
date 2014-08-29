#import "WiFiInfo.h"
#import <SystemConfiguration/CaptiveNetwork.h>


@implementation WiFiInfo

- (void)getInfo:(CDVInvokedUrlCommand*)command {

	NSLog(@"START PLUGIN WiFiInfo");
	
    // Check command.arguments here.
    [self.commandDelegate runInBackground:^{
        
        NSString *currentSSID = @"";
        
		CFArrayRef myArray = CNCopySupportedInterfaces();
	
		if (myArray != nil){
	
			NSDictionary* myDict = (NSDictionary *) CNCopyCurrentNetworkInfo(CFArrayGetValueAtIndex(myArray, 0));
		
			if (myDict!=nil){
				currentSSID=[myDict valueForKey:@"SSID"];
			} else {
				currentSSID=@"<<NONE>>";
			}
		} else {
			currentSSID=@"<<NONE>>";
		}
		
		NSString *currentSSIDUpper = [currentSSID uppercaseString];
				
		NSLog(@"WiFi Info SSID: %@", currentSSIDUpper);
        
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:currentSSIDUpper];
        // The sendPluginResult method is thread-safe.
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
	}];
	
	
	

}


@end
