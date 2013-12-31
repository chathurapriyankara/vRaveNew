package com.virtusa.vravenew;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText etUserName;
	EditText etPassword;
	Button button;
	private ProgressDialog progressDialog;
	private ProgressBar loginProgress;
	// Sharedprefrance file name
	private static final String PREF_NAME = "SessionStore";
	// Session key
	private static final String LOG_SESSION = "valid";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		loginProgress = (ProgressBar) findViewById(R.id.progressBarLogin);
		loginProgress.setVisibility(View.GONE);

		etUserName = (EditText) findViewById(R.id.usernameFIeld);
		etPassword = (EditText) findViewById(R.id.passwordField);

		button = (Button) findViewById(R.id.button2);

		TextWatcher watcher = new LocalTextWatcher();
		etUserName.addTextChangedListener(watcher);
		etPassword.addTextChangedListener(watcher);

		updateButtonState();

	}

	void updateButtonState() {
		if (checkUsername(etUserName)) {
			button.setEnabled(false);
			button.setBackgroundResource(R.drawable.login_button);
		} else if (checkPassword(etPassword)) {
			button.setEnabled(false);
			button.setBackgroundResource(R.drawable.login_button);
		} else {
			button.setEnabled(true);
			button.setBackgroundResource(R.drawable.button_enable);
		}
	}

	private boolean checkUsername(EditText edit) {
		return edit.getText().length() == 0;
	}

	private boolean checkPassword(EditText edit) {
		return edit.getText().length() < 5;
	}

	private class LocalTextWatcher implements TextWatcher {
		public void afterTextChanged(Editable s) {
			updateButtonState();
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return true;
	}

	public void login(View view) {

		if (isNetworkAvailable(getApplicationContext())) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("username", etUserName.getText().toString());
			params.put("password", etPassword.getText().toString());
			new LoginAsyncTask().execute(params);

//			overridePendingTransition(R.anim.left_to_right,
//					R.anim.right_to_left);
			//new LoginAsyncTask().execute(params);
		} else {
			Toast.makeText(getApplication(),
					"Your internet connection is disabled", Toast.LENGTH_SHORT)
					.show();
		}

		// loginProgress.setVisibility(View.VISIBLE);
		Intent vraveIntent = new Intent(getApplicationContext(),
				VRaveMainActivity.class);
		startActivity(vraveIntent);
		// loginProgress.setVisibility(View.GONE);
//		overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
		finish();
	}

	static final String SERVER_URL = "http://rdeshapriya.com/vnotifications/webService.php?action=login&data=";
	static JSONObject returnData;

	private class LoginAsyncTask extends
			AsyncTask<HashMap<String, String>, Void, Boolean> {

		String result = "";

		protected void onPreExecute() {

			loginProgress.setVisibility(View.VISIBLE);
			// make fields disable
			etUserName.setEnabled(false);
			etPassword.setEnabled(false);
			button.setEnabled(false);

			// try {
			// progressDialog = ProgressDialog.show(LoginActivity.this, "",
			// "Loading...");
			// } catch (final Throwable th) {
			//
			// }
		}

		@Override
		protected Boolean doInBackground(HashMap<String, String>... params) {
			try {
				JSONObject data = postRequestWithData("login", params[0]);
				Log.i("json", data.toString());
				if (data.getString("message").equals("SUCCESS")) {
					result = "Success!";
					return true;
				}
				if (data.getString("message").equals("FAIL")) {
					result = "Error!";
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}

		@Override
		protected void onPostExecute(Boolean success) {
			loginProgress.setVisibility(View.INVISIBLE);
			// make login form fields disable
			etUserName.setEnabled(true);
			etPassword.setEnabled(true);
			button.setEnabled(true);
			// progressDialog.dismiss();

			if (result.equals("Success!")) {
				Intent dashBoardIntent = new Intent(LoginActivity.this,
						VRaveMainActivity.class);
				startActivity(dashBoardIntent);
				overridePendingTransition(R.anim.left_to_right,
						R.anim.right_to_left);

			} else {
				Toast.makeText(getApplication(), "Invalid login",
						Toast.LENGTH_SHORT).show();
			}

		}

	}

	public static JSONObject postRequestWithData(String action,
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
			returnData = new JSONObject(answer.toString());

		} catch (Exception e) {
			e.printStackTrace();
			returnData = new JSONObject();
			returnData.put("message", "ERROR");
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
		Log.i("Sent JSON", jsonObject.toString());
		return jsonObject.toString();

	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
