package com.aht.android.rest;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by roblick on 22.02.2017.
 */

public class RestResultReceiver extends ResultReceiver {

	private Receiver mReceiver;
	/**
	 * Create a new ResultReceive to receive results.  Your
	 * {@link #onReceiveResult} method will be called from the thread running
	 * <var>handler</var> if given, or from an arbitrary thread if null.
	 *
	 * @param handler
	 */
	public RestResultReceiver(Handler handler) {
		super(handler);
	}

	public void setReceiver(Receiver receiver) {
		mReceiver = receiver;
	}

	public interface Receiver {
		void onReceiveResult(int resultCode, Bundle resultData);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		}
	}
}
