package com.virtusa.vravenew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RaveBadgesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
		View view;
		view = inflater.inflate(R.layout.rave_badges_fragment_layout, container,false);
		return view;
	}

}
