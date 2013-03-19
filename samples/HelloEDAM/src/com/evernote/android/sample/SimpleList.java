package com.evernote.android.sample;

import java.util.List;

import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

public class SimpleList extends ParentActivity {

		ArrayAdapter<String> adapter; 

	  private static final String LOGTAG = "SimpleList";	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.simple_list);
	        
	        
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1); 
	        initListView();
	  }
	
	   private void initListView(){
		   
		    try {
		        mEvernoteSession.getClientFactory().createNoteStoreClient().listNotebooks(new OnClientCallback<List<Notebook>>() {

		          @Override
		          public void onSuccess(final List<Notebook> notebooks) {
		        	  
		         
		        	  
		            Notebook notebook = null;
		            adapter.clear();
		            for (int index = 0; index < notebooks.size(); index++) {
		              notebook = notebooks.get(index);
		              adapter.add(notebook.getName());
		            }
		            ListView listView = (ListView) findViewById(R.id.listView1);
		              // アダプターを設定します
		            listView.setAdapter(adapter);


		          }

		          @Override
		          public void onException(Exception exception) {
		            Log.e(LOGTAG, "Error listing notebooks", exception);
		            Toast.makeText(getApplicationContext(), R.string.error_listing_notebooks, Toast.LENGTH_LONG).show();
		            removeDialog(DIALOG_PROGRESS);
		          }
		        });
		      } catch (TTransportException exception) {
		        Log.e(LOGTAG, "Error creating notestore", exception);
		        Toast.makeText(getApplicationContext(), R.string.error_creating_notestore, Toast.LENGTH_LONG).show();
		        removeDialog(DIALOG_PROGRESS);
		      }		   
		   
		   
		   
		   
		   
	   }
}
