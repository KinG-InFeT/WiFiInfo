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

var exec = require('cordova/exec');

module.exports = {

    getInfo: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'WiFiInfo', 'getInfo', []);
    },
};