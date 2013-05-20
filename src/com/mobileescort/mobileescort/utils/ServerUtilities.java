package com.mobileescort.mobileescort.utils;

import com.google.android.gcm.GCMRegistrar;
import com.mobileescort.mobileescort.R;

import android.content.Context;
import android.util.Log;

import static com.mobileescort.mobileescort.utils.CommonUtilities.TAG;
import static com.mobileescort.mobileescort.utils.CommonUtilities.displayMessage;

public final class ServerUtilities {
	private static final int MAX_ATTEMPTS = 1;
	
    /**
     * Register this account/device pair within the server.
     *
     */
    public static void register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        displayMessage(context, context.getString(
                        R.string.server_registering, 1, MAX_ATTEMPTS));
        GCMRegistrar.setRegisteredOnServer(context, true);
        String message = context.getString(R.string.server_registered);
        CommonUtilities.displayMessage(context, message);
        
    }

    /**
     * Unregister this account/device pair within the server.
     */
    public static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        GCMRegistrar.setRegisteredOnServer(context, false);
        String message = context.getString(R.string.server_unregistered);
        CommonUtilities.displayMessage(context, message);
    }
}
