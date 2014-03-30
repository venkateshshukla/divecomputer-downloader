package com.subsurface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

public class Home extends SherlockActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setTitle(R.string.title_home);
    }

    public void UseUsb(View view)
    {
	Intent intent = new Intent(this, UseUsbEnum.class);
	startActivity(intent);
    }

    public void UseBluetooth(View view)
    {
	Intent intent = new Intent(this, UseBluetooth.class);
	startActivity(intent);
    }
}
