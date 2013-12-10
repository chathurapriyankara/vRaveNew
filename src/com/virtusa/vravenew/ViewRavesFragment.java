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
	        	  
                TextView rTitle = (TextView)view.findViewById(R.id.ravetitle);
                TextView rmessage = (TextView)view.findViewById(R.id.raveexplanation);
                TextView rsender = (TextView)view.findViewById(R.id.raveperson);
                
                String ravet = rTitle.getText().toString();
                String ravem = rmessage.getText().toString();
                String sender = rsender.getText().toString();
                
               
                
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
	
//	
//    //popup window method
//    private PopupWindow pwindo;
//    //person name
//    private String ravePerson;
//    public void initiatePopupWindow(String type, String des, String name) {
//
//            try {
//                     //layout_MainMenu.getForeground().setAlpha(50);
//            
//                    ravePerson = name;
//                    // We need to get the instance of the LayoutInflater
//                    LayoutInflater inflater = (LayoutInflater) getActivity()
//                                    .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
//                    View layout = inflater.inflate(R.layout.popup_screen, (ViewGroup)
//
//                    getActivity().findViewById(R.id.popup));
//                    
//    
//                    
//                    pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
//                                    ViewGroup.LayoutParams.MATCH_PARENT, true);
//                    pwindo.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
//                    
//                    
//                    
//                    //ravetype
//                    TextView rave_type = (TextView) layout.findViewById(R.id.ravetype);
//                    rave_type.setText(type);
//                    
//                    // rave description
//                    TextView rave_dec = (TextView) layout.findViewById(R.id.ravedes);
//                    rave_dec.setText(des);
//
//                    // rave person
//                    TextView rave_person = (TextView) layout
//                                    .findViewById(R.id.raveperson);
//                    rave_person.setText(name);
//
//                    //send thank you response
//                    
//                    ImageView sendThank = (ImageView) layout.findViewById(R.id.send_thank);
//                    sendThank.setOnClickListener(thank);
//
//                  
//                    LinearLayout l = (LinearLayout) layout.findViewById(R.id.outer_layout);
//            l.setOnClickListener(new OnClickListener() {
//                            
//                            @Override
//                            public void onClick(View v) {
//                                    pwindo.dismiss();
//                                    
//                            }
//                    });
//            LinearLayout l1 = (LinearLayout) layout.findViewById(R.id.popup);
//            l1.setOnClickListener(new OnClickListener() {
//                    
//                    @Override
//                    public void onClick(View v) {
//                            
//                    }
//            });
//
//            } catch (Exception e) {
//                    e.printStackTrace();
//            }
//    }
//    
//    //send thank you message method
//    private OnClickListener thank = new OnClickListener() {
//            public void onClick(View v) {
//                    pwindo.dismiss();
//                    AlertDialog.Builder altDialog = new AlertDialog.Builder(
//                                    getActivity());
//                    altDialog.setTitle("Confirm");
//                    StringBuilder message = new StringBuilder();
//                    message.append("Are you sure you want to thank ");
//                    message.append(ravePerson);
//                    message.append(" for raving you.");
//                    altDialog.setMessage(message);
//
//                    altDialog.setPositiveButton("Yes",
//                                    new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                    // if this button is clicked, close
//                                                    // current activity
//                                                    
//
//                                            }
//
//                                    });
//                    altDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                    // if this button is clicked, close
//                                    // current activity
//                                    
//
//                            }
//
//                    });
//                    altDialog.show();
//
//            }
//    };

}
