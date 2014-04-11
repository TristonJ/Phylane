package com.lvadt.phylane.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.lvadt.phylane.R;

public class Prefs extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}

}
