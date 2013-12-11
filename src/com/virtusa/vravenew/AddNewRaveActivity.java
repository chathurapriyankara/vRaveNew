package com.virtusa.vravenew;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class AddNewRaveActivity extends Activity {
	private AutoCompleteTextView actvNameSuggesion;
	ArrayList<String> employeeNames = new ArrayList<String>();
	String[] from = {"txt"};
    int[] to = {R.id.textViewNameSuggesion};
    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
    static JSONArray returnData = null;
    HashMap<String, String> params;
    static final String SERVER_URL = "http://rdeshapriya.com/vnotifications/webService.php?action=vplussearch&data=";
    ProgressBar PbSendrave; 
    ImageView sendNewRave; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_rave);
		sendNewRave = (ImageView) findViewById(R.id.imageView4);
		PbSendrave = (ProgressBar) findViewById(R.id.progressBarSendRave);
		
		actvNameSuggesion = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewNameSuggesion);
		actvNameSuggesion.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				sendNewRave.setVisibility(View.GONE);
				PbSendrave.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				try{
                	String st = actvNameSuggesion.getText().toString();
                	 params = new HashMap<String, String>();
                     params.put("query", st);
                     new RaveNameSuggesionTask().execute(params);
                	}
				
                	catch(Exception e){
                		
                	}
				
			}
		});
		
		
		PbSendrave.setVisibility(View.GONE);
		
		
		sendNewRave.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					sendNewRave.setVisibility(View.GONE);
					PbSendrave.setVisibility(View.VISIBLE);
				}
				
				return false;
			}
		});
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_rave, menu);
		return true;
	}
	
	private class RaveNameSuggesionTask extends AsyncTask<HashMap<String, String>, String, String>{
		JSONArray jArray;
        SimpleAdapter adapter;

		@Override
		protected String doInBackground(HashMap<String, String>... key) {
			 try {
                 // clear all arraylists if items are there
                 if (employeeNames.size() > 0) {
                	 employeeNames.clear();
                 }
                 jArray = postRequestWithData("vplussearch", key[0]);
                 for (int i = 0; i < jArray.length(); i++) {
                         String suggestKey = jArray.getJSONObject(i).getString(
                                         "name");
                         String dest = jArray.getJSONObject(i).getString(
                                         "designation");
                         employeeNames.add(suggestKey);

                 }

         } catch (Exception e) {
                 Log.w("Error", e.getMessage());
         }

         return null;
		}
		
		 @Override
         protected void onPostExecute(String s) {
			 sendNewRave.setVisibility(View.VISIBLE);
				PbSendrave.setVisibility(View.GONE);
         	try{
                 // clear the array list if items exist
                 if (aList.size() > 0) {
                         aList.clear();
                 }

                 for (int i = 0; i < employeeNames.size(); i++) {

                         HashMap<String, String> hm = new HashMap<String, String>();
                         hm.put("txt", employeeNames.get(i));
                         aList.add(hm);
                 }
               //hide progress dialog
                 //hide inditerminate progress dialog on action bar
                   //  ((DashboardActivity)getActivity()).activateProgressBar(false);

                 adapter = new SimpleAdapter(getApplicationContext(), aList,
                                 R.layout.search_suggesions_list_item, from, to);
                 actvNameSuggesion.setAdapter(adapter);
         	}
         	catch(Exception e){
         	}
         }
		
	}
	
	  public static JSONArray postRequestWithData(String action,
              HashMap<String, String> params) throws JSONException {
      String json_data = encodeData(params);
      try {

              // Construct data
              String data = URLEncoder.encode("action", "UTF-8") + "="
                              + URLEncoder.encode(action, "UTF-8");
              data += "&" + URLEncoder.encode("data", "UTF-8") + "="
                              + URLEncoder.encode(json_data, "UTF-8");
              // Send data
              URL url = new URL(SERVER_URL);
              URLConnection conn = url.openConnection();
              conn.setDoOutput(true);
              OutputStreamWriter wr = new OutputStreamWriter(
                              conn.getOutputStream());
              wr.write(data);
              wr.flush();
              StringBuilder answer = new StringBuilder();

              BufferedReader rd = new BufferedReader(new InputStreamReader(
                              conn.getInputStream()));
              String line = "";
              while ((line = rd.readLine()) != null) {
                      answer.append(line);
              }

              wr.close();
              rd.close();
              returnData = new JSONArray(answer.toString());
              // returnData = new JSONObject(answer.toString());

      } catch (Exception e) {
              // Log.e("ERROR", "");
              // returnData = new JSONObject();
              // returnData.put("message", "ERROR");
      }
      return returnData;
}

private static String encodeData(HashMap<String, String> data) {
      JSONObject jsonObject = new JSONObject();
      for (Map.Entry<String, String> entry : data.entrySet()) {
              try {
                      jsonObject.put(entry.getKey(), entry.getValue());
              } catch (Exception e) {
                      e.printStackTrace();
              }
      }
      return jsonObject.toString();

}
	

}
