package com.virtusa.vravenew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewRavesFragment extends Fragment {
	
	List<HashMap<String, String>> raveList = new ArrayList<HashMap<String, String>>();
	// keys for hash set
    String[] from = {"rImg", "rTitle", "rMessage", "rSender" };
    // ui elements in the search suggesions
    int[] to = { R.id.imageViewRaveImage,R.id.ravetitle,  R.id.raveexplanation, R.id.raveperson };
    ListView l;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
		View view;
		view = inflater.inflate(R.layout.view_raves_fragment_layout, container,false);
		 l = (ListView) view.findViewById(R.id.listView1);
		//String[] from = {"Good job","Awesome code","Nice help","Nice thinking","Great help","Good job","Awesome code","Nice help","Nice thinking","Great help","Good job","Awesome code","Nice help","Nice thinking","Great help"};
		
		populateRaveList();
		
		
		
		l.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> adapter, View view,
	                int position, long id) {
	        	  
               // TextView rTitle = (TextView)view.findViewById(R.id.ravetitle);
                TextView rmessage = (TextView)view.findViewById(R.id.raveexplanation);
              //  TextView rsender = (TextView)view.findViewById(R.id.raveperson);
                
              
               
                if(rmessage.getLineCount()==1){
                	rmessage.setSingleLine(false);
                }
                else{
                	rmessage.setSingleLine(true);
                }
            //    initiatePopupWindow(ravet,ravem,sender);
	        }
	    });		
		
		return view;
	}
	
	
	
	public void populateRaveList(){
		
		int imgGreatAdvice  = R.drawable.great_advice;
		int imgEagleEye  = R.drawable.eagle_eye;
		int imgGreatIdea  = R.drawable.great_idea;
		int imgGreatWork  = R.drawable.great_work;
		int imgKillerCode  = R.drawable.killer_code;
		int imgOldRavesDefault  = R.drawable.old_raves_default;
		int imgPirl  = R.drawable.pirl;
		int imgSuperService  = R.drawable.super_service;
		int imgThanksForYourHelp  = R.drawable.thanks_for_your_help;
		
		HashMap<String, String> rave1 = new HashMap<String, String>();
		rave1.put("rImg", Integer.toString(imgGreatAdvice));
        rave1.put("rTitle", "Good Job");
        rave1.put("rMessage", "Appreciate your hard work given for the project");
        rave1.put("rSender", "Chathura Priyankara");
        
        raveList.add(rave1);
        
        HashMap<String, String> rave2 = new HashMap<String, String>();
        rave2.put("rImg", Integer.toString(imgKillerCode));
        rave2.put("rTitle", "Awesome  code");
        rave2.put("rMessage", "That code worked like magic. Thank you very much");
        rave2.put("rSender", "Dhanushka Jayasuriya");
        raveList.add(rave2);
        
        
        HashMap<String, String> rave3 = new HashMap<String, String>();
        rave3.put("rImg", Integer.toString(imgThanksForYourHelp));
        rave3.put("rTitle", "Great Help");
        rave3.put("rMessage", "Thanks heaps for helping out hrough out the project work.");
        rave3.put("rSender", "Madushi Dias");
        raveList.add(rave3);
        
        HashMap<String, String> rave4 = new HashMap<String, String>();
        rave4.put("rImg", Integer.toString(imgGreatIdea));
        rave4.put("rTitle", "Nice Thinking");
        rave4.put("rMessage", "Good thinking saved our neck");
        rave4.put("rSender", "Prasan Yapa");
        raveList.add(rave4);
        
        HashMap<String, String> rave5 = new HashMap<String, String>();
        rave5.put("rImg", Integer.toString(imgGreatWork));
        rave5.put("rTitle", "Good Job");
        rave5.put("rMessage", "Appreciate your hard work given for the project");
        rave5.put("rSender", "Dhanushka Jayasuriya");
        raveList.add(rave5);
        
        HashMap<String, String> rave6 = new HashMap<String, String>();
        rave6.put("rImg", Integer.toString(imgKillerCode));
        rave6.put("rTitle", "Awesome code");
        rave6.put("rMessage", "That code worked like magic. Thank you very much");
        rave6.put("rSender", "Dhanushka Jayasuriya");
        raveList.add(rave6);
        
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),raveList,R.layout.list_item_view_rave, from,to);
		l.setAdapter(adapter); 
	}
	


}
