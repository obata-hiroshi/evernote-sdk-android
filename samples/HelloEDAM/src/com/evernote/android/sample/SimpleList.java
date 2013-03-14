package com.evernote.android.sample;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

public class SimpleList extends ParentActivity {

	  private static final String LOGTAG = "SimpleList";	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.simple_list);

	    }
	
}
