package com.evernote.android.sample;

import java.util.List;

import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.edam.notestore.NotesMetadataList;
import com.evernote.edam.notestore.NotesMetadataResultSpec;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.transport.TTransportException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class NoteList extends ParentActivity {
	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list_view);
		// Show the Up button in the action bar.
		setupActionBar();
		
		mListView = (ListView) findViewById(R.id.note_listview);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		mListView.setAdapter(mAdapter);
		Intent intent = getIntent();
		if (intent != null){
			String notebookGuid = intent.getStringExtra(getResources().getString(R.string.notebook_guid));
			if (notebookGuid != null){
				initList(notebookGuid);
			}
		}
	}

	
	private void initList(String notebookGuid)	{
		NoteFilter filter = new NoteFilter(); 
		filter.setNotebookGuid(notebookGuid);
		NotesMetadataResultSpec resultSpec = new NotesMetadataResultSpec();
		 resultSpec.setIncludeTitle(true);
		
		try {
			mEvernoteSession.getClientFactory().createNoteStoreClient().findNotesMetadata(filter, 0, 100, resultSpec, 
					new OnClientCallback<NotesMetadataList>() {

						@Override
						public void onSuccess(NotesMetadataList notes) {
							mAdapter.clear();
							for (NoteMetadata note : notes.getNotes()) {
								mAdapter.add(note.getTitle());
							}
							
						}

						@Override
						public void onException(Exception exception) {
							// TODO Auto-generated method stub
							
						}
					}
			);
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	
	
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
		getMenuInflater().inflate(R.menu.note_list_view, menu);
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

}
