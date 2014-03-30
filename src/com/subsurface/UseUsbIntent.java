package com.subsurface;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class UseUsbIntent extends SherlockActivity{

	Intent receivedIntent;
	TextView tvUsbDeviceInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.useusbintent);
		setTitle(R.string.title_usb_intent);
		receivedIntent = getIntent();
		tvUsbDeviceInfo = (TextView) findViewById(R.id.tvUsbDeviceInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		UsbDevice device = (UsbDevice) receivedIntent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
		tvUsbDeviceInfo.setText(device.toString());
	}
}
