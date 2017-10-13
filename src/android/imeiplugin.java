package com.joandilee;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.cordova.PermissionHelper;


import android.telephony.TelephonyManager;
import android.content.Context;
import android.util.Log;

public class imeiplugin extends CordovaPlugin {

    String [] permissions = { Manifest.permission.READ_PHONE_STATE };

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getImei")) {
            if(hasPermisssion())
            {
                this.DeviceImeiNumber(callbackContext);
                return true;
            }
            else {
                PermissionHelper.requestPermissions(this, 0, permissions);
            }

            return true;
        }
        return false;
    }

    public boolean hasPermisssion() {
        for(String p : permissions)
        {
            if(!PermissionHelper.hasPermission(this, p))
            {
                return false;
            }
        }
        return true;
    }

    /*
     * We override this so that we can access the permissions variable, which no longer exists in
     * the parent class, since we can't initialize it reliably in the constructor!
     */

    public void requestPermissions(int requestCode)
    {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

    public void DeviceImeiNumber(CallbackContext callbackContext) {
        Context context=this.cordova.getActivity().getApplicationContext();

        TelephonyManager tManager = (TelephonyManager)cordova.getActivity().getSystemService(context.TELEPHONY_SERVICE);
        callbackContext.success(tManager.getDeviceId());
    }

    private void getImei(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    } 
} 
