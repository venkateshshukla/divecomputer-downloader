package com.subsurface;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.subsurface.ui.DevicelistAdapter;

public class UseUsbEnum extends SherlockListActivity {
	private UsbManager usbmanager;
	private HashMap<String, UsbDevice> devicemap;
	private static final String TAG = "UseUsbEnum";
	private ArrayList<UsbDevice> allUsbDevices;
	private DevicelistAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_usb_enum);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		usbmanager = (UsbManager) getSystemService(Context.USB_SERVICE);
		allUsbDevices = new ArrayList<UsbDevice>();
		setListAdapter(refreshListAdapter());
	}

	@Override
	protected void onResume() {
		setListAdapter(refreshListAdapter());
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.usb_enum, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_usbenum_refresh:
			setListAdapter(refreshListAdapter());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private DevicelistAdapter refreshListAdapter()	{
		devicemap = usbmanager.getDeviceList();
		allUsbDevices.clear();
		allUsbDevices.addAll(devicemap.values());
		adapter = new DevicelistAdapter(this, allUsbDevices);
		Log.v(TAG, allUsbDevices.toString());
		return adapter;
	}
}
