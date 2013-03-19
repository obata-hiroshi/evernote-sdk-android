package com.evernote.android.sample;

import java.util.List;

import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;

import android.os.Build;

public class NoteBookList extends ParentActivity {
	  private ListView mListView;
	  private NotebookListAdapter mAdapter;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_book_list);
		// Show the Up button in the action bar.
		setupActionBar();
		
		mListView = (ListView) findViewById(R.id.notebook_listview);
		mAdapter = new NotebookListAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mItemClickListener);
		
		initList();
	}

	
	private void initList()	{
	    try {
	        mEvernoteSession.getClientFactory().createNoteStoreClient().listNotebooks(new OnClientCallback<List<Notebook>>() {
	          @Override
	          public void onSuccess(final List<Notebook> notebooks) {
	        	  listingNoteBook(notebooks);
	          }

	          @Override
	          public void onException(Exception exception) {
	            Toast.makeText(getApplicationContext(), R.string.error_listing_notebooks, Toast.LENGTH_LONG).show();
	          }
	        });
	      } catch (TTransportException exception) {
	        Toast.makeText(getApplicationContext(), R.string.error_creating_notestore, Toast.LENGTH_LONG).show();
	      }	
	}
	
	private void listingNoteBook(final List<Notebook> notebooks){
		mAdapter.clear();
		mAdapter.addAll(notebooks);
		mAdapter.notifyDataSetChanged();
		
		
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_book_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	  private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Notebook book = mAdapter.getItem(position);
			if (book != null){
				Intent intent = new Intent(getApplicationContext(), NoteList.class);
				intent.putExtra(getResources().getString(R.string.notebook_guid),book.getGuid());
				startActivity(intent);				
			}
		}
		  
	  };
	
}
