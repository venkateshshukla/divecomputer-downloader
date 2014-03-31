package com.subsurface;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class UseBluetooth extends SherlockActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usebluetooth);
		setTitle(R.string.title_bluetooth);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
