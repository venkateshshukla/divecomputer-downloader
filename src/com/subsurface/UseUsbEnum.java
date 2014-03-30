package com.subsurface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListActivity;

public class UseUsbEnum extends SherlockListActivity {
	private UsbManager usbmanager;
	private HashMap<String, UsbDevice> devicemap;
	private static final String TAG = "UseUsbEnum";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_usb_enum);
		usbmanager = (UsbManager) getSystemService(Context.USB_SERVICE);
		devicemap = usbmanager.getDeviceList();
		ArrayList<String> devicelist = new ArrayList<String>();

		Collection<UsbDevice> allUsbDevices = devicemap.values();

		for(UsbDevice d:allUsbDevices) {
			devicelist.add(d.toString());
		}
		Log.v(TAG, String.valueOf(devicelist));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, devicelist);
		setListAdapter(adapter);
	}
}

