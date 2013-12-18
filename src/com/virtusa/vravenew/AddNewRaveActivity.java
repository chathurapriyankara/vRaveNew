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
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewRaveActivity extends Activity {
	private AutoCompleteTextView actvNameSuggesion;
	ArrayList<String> employeeNames = new ArrayList<String>();
	String[] from = { "txt" };
	int[] to = { R.id.textViewNameSuggesion };
	List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
	static JSONArray returnData = null;
	HashMap<String, String> params;
	static final String SERVER_URL = "http://rdeshapriya.com/vnotifications/webService.php?action=vplussearch&data=";

	String[] category = { "You are a role model",
			"Awesome code", "Sharp testing", "Good job",
			"Thanks for your advice", "Good thinking", "Thanks for your help",
			"Thanks for the great service" };
	int images[] = { R.drawable.pirl,
			R.drawable.killer_code, R.drawable.eagle_eye,
			R.drawable.great_work, R.drawable.great_advice,
			R.drawable.great_idea, R.drawable.thanks_for_your_help,
			R.drawable.super_service };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_rave);
		setProgressBarIndeterminate(true);
		setProgressBarIndeterminateVisibility(false);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DC8909")));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		EditText e = (EditText)findViewById(R.id.editTextselectcategory);

		actvNameSuggesion = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewNameSuggesion);
		actvNameSuggesion.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					String st = actvNameSuggesion.getText().toString();
					params = new HashMap<String, String>();
					params.put("query", st);
					new RaveNameSuggesionTask().execute(params);
				}

				catch (Exception e) {

				}

			}
		});


//		sendNewRave.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					sendNewRave.setVisibility(View.GONE);
//					PbSendrave.setVisibility(View.VISIBLE);
//				}
//
//				return false;
//			}
//		});

		final Spinner mySpinner = (Spinner) findViewById(R.id.category);
		mySpinner.setAdapter(new MyAdapter(this, R.layout.spinner_categories,
				category));
		//mySpinner.setVisibility(View.INVISIBLE);
		e.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mySpinner.performClick();
				return true;
			}
		});
		
	}
	
	 private boolean isNetworkAvailable() {
         ConnectivityManager connectivityManager = (ConnectivityManager) this
                         .getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetworkInfo = connectivityManager
                         .getActiveNetworkInfo();
         return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_new_rave, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            return true;
	        case R.id.action_send_rave:
	        	setProgressBarIndeterminateVisibility(true);
	            return true;
	        case android.R.id.home:
	            NavUtils.navigateUpFromSameTask(this);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private class RaveNameSuggesionTask extends
			AsyncTask<HashMap<String, String>, String, String> {
		JSONArray jArray;
		SimpleAdapter adapter;
		
		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
		}
		
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
				
			}

			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			setProgressBarIndeterminateVisibility(false);
			try {
				// clear the array list if items exist
				if (aList.size() > 0) {
					aList.clear();
				}

				for (int i = 0; i < employeeNames.size(); i++) {

					HashMap<String, String> hm = new HashMap<String, String>();
					hm.put("txt", employeeNames.get(i));
					aList.add(hm);
				}
				// hide progress dialog
				// hide inditerminate progress dialog on action bar
				// ((DashboardActivity)getActivity()).activateProgressBar(false);

				adapter = new SimpleAdapter(getApplicationContext(), aList,
						R.layout.search_suggesions_list_item, from, to);
				actvNameSuggesion.setAdapter(adapter);
			} catch (Exception e) {
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

	public class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}

		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.spinner_categories,
					parent, false);

			TextView content = (TextView) mySpinner
					.findViewById(R.id.categoryname);
			content.setText(category[position]);

			ImageView left_icon = (ImageView) mySpinner
					.findViewById(R.id.categoryiamge);
			left_icon.setImageResource(images[position]);

			return mySpinner;

		}

	}

}
