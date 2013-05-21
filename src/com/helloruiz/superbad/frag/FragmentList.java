package com.helloruiz.superbad.frag;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentList extends ListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Devices older than Honeycomb use 'simple_list_item_1'
		int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
				android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

		setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Content.list_content));
	}

	@Override
	public void onStart() {
		super.onStart();

		// Keeps the selected item in the list highlighted.
		// According to the Open Source Project, it's done at onStart() because listview isn't available during onCreate().
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Tying the interface object with the host activity
		// Make sure that the host activity implements the interface!
		runItBackBecause = (HeadlineSelection) activity;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		runItBackBecause.anItemWasJustSelected(position);

		// "Set the item as checked to be highlighted when in two-pane layout"
		// This line didn't make any difference when I was testing it...
		getListView().setItemChecked(position, true);
	}

	public interface HeadlineSelection {

		public void anItemWasJustSelected(int position);
	}

	HeadlineSelection runItBackBecause;
}
