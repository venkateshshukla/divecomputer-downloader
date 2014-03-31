package com.subsurface;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class UseBluetooth extends SherlockActivity {
	private static final String TAG = "UseBluetooth";
	private static final int REQUEST_ENABLE_BLUETOOTH = 99998;
	private BluetoothAdapter bluetoothAdapter;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usebluetooth);
		setTitle(R.string.title_bluetooth);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		context = this;

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			Log.e(TAG, "Device does not support Bluetooth");
			showErrorDialog(context, R.string.title_no_bluetooth_adapter,
					R.string.msg_no_bluetooth_adapter);
		}

		if (!bluetoothAdapter.isEnabled()) {
		    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			switch(requestCode) {
			case REQUEST_ENABLE_BLUETOOTH:
				break;
			}
		} else {
			switch(requestCode) {
			case REQUEST_ENABLE_BLUETOOTH:
				Log.v(TAG, "Bluetooth not enabled");
				showErrorDialog(context, R.string.title_bluetooth_not_enabled,
						R.string.msg_bluetooth_not_enabled);
				break;
			}
		}
	}

	private void showErrorDialog(Context context, int title, int message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						finish();
					}
				});
		builder.setMessage(message);
		builder.create();
		builder.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}

