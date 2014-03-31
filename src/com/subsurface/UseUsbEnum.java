package com.subsurface;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.subsurface.ui.DevicelistAdapter;

public class UseUsbEnum extends SherlockListActivity implements OnItemClickListener {

	private UsbManager usbManager;
	private HashMap<String, UsbDevice> devicemap;
	private ArrayList<UsbDevice> allUsbDevices;
	private DevicelistAdapter adapter;
	private ListView listView;
	private PendingIntent permissionIntent;
	private UsbPermissionReceiver permissionReceiver;
	private static final String TAG = "UseUsbEnum";
	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_usb_enum);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		allUsbDevices = new ArrayList<UsbDevice>();
		setListAdapter(refreshListAdapter());

		listView = getListView();
		listView.setOnItemClickListener(this);

		permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		permissionReceiver = new UsbPermissionReceiver();
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		registerReceiver(permissionReceiver, filter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		UsbDevice device = (UsbDevice) parent.getAdapter().getItem(position);
		usbManager.requestPermission(device, permissionIntent);
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

	private DevicelistAdapter refreshListAdapter() {
		devicemap = usbManager.getDeviceList();
		allUsbDevices.clear();
		allUsbDevices.addAll(devicemap.values());
		adapter = new DevicelistAdapter(this, allUsbDevices);
		Log.v(TAG, "No. of USB devices : " + allUsbDevices.size());
		return adapter;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(permissionReceiver);
		super.onDestroy();
	}

	private class UsbPermissionReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_USB_PERMISSION)) {
				synchronized (this) {
					UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						if (device != null) {
							Toast.makeText(context, device.toString(), Toast.LENGTH_LONG).show();
							Intent usbIntent = new Intent(context, UseUsbIntent.class);
							usbIntent.putExtra(UsbManager.EXTRA_DEVICE, device);
							startActivity(usbIntent);
						}
					} else {
						Log.d(TAG, "permission denied for device " + device);
					}
				}
			}

		}
	}
}
