/*
 *
 * WiFi Info plugin for Android
 *
 * @Author Linkpass Srl
 *
 * @licenze GPL v3
 *
 *
 */

package org.apache.cordova.wifiinfo;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.cordova.CordovaWebView;
import org.json.JSONObject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import org.apache.cordova.CordovaInterface;

public class WiFiInfo extends CordovaPlugin {

	public static String WIFI_NAME = "";

	ConnectivityManager sockMan;
	BroadcastReceiver receiver;

	/**
	 * Constructor.
	 */
	public WiFiInfo() {
		this.receiver = null;
	}

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.sockMan = (ConnectivityManager) cordova.getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

		cordova.getActivity().registerReceiver(this.receiver, intentFilter);

	}

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {

		if (action.equals("getInfo")) {

			NetworkInfo info = sockMan.getActiveNetworkInfo();
			WIFI_NAME = this.getInfo(info).toUpperCase().replace("\"", "");

			Log.d("WiFiInfo DEBUG", "WIFI SSID: " + WIFI_NAME);

			PluginResult pluginResult = new PluginResult(
					PluginResult.Status.OK, WIFI_NAME);

			pluginResult.setKeepCallback(true);
			callbackContext.sendPluginResult(pluginResult);

			callbackContext.success(WIFI_NAME);

			return true;

		} else {
			return false;
		}

	}

	private String getInfo(NetworkInfo info) {
		JSONObject thisInfo = this.getConnectionInfo(info);
		String connectionType = "";
		try {
			connectionType = thisInfo.get("ssid").toString();
		} catch (JSONException e) {
		}

		return connectionType;
	}

	private JSONObject getConnectionInfo(NetworkInfo info) {

		String extraInfo = "";
		if (info != null) {
			extraInfo = info.getExtraInfo();
		}

		//Log.d("CordovaNetworkManager", "Connection Extra Info: " + extraInfo);
        
		JSONObject connectionInfo = new JSONObject();
		try {

			connectionInfo.put("ssid", extraInfo);
		} catch (JSONException e) {
		}
		return connectionInfo;
	}

}
