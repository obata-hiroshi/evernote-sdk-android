package com.evernote.android.sample;


import java.util.List;

import com.evernote.edam.type.Notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class NotebookListAdapter extends
		ArrayAdapter<com.evernote.edam.type.Notebook> {
		private LayoutInflater mInflater;
		private TextView mTitle;
		
	public NotebookListAdapter(Context context) {
		super(context, 0);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);
		}
		final Notebook item = this.getItem(position);
		if(item != null){
			mTitle = (TextView)convertView.findViewById(R.id.nameText);
			mTitle.setText(item.getName());		
		}
		return convertView;
	}
	
}
