package com.virtusa.vravenew;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewRavesFragment extends Fragment {
        
        ArrayList<Integer> categoryImage = new ArrayList<Integer>();
        ArrayList<String> raveTitle = new ArrayList<String>();
        ArrayList<String> raveMessage = new ArrayList<String>();
        ArrayList<String> raveSender = new ArrayList<String>();
        
        HashMap<String, String> params;
        // keys for hash set
        String[] from = { "cImg","rTitle", "rMessage", "rSender" };
        // ui elements in the search suggesions
        int[] to = { R.id.imageViewRaveImage,R.id.ravetitle, R.id.raveexplanation, R.id.raveperson };

        // array list use in the adaper
        List<HashMap<String, String>> raveList = new ArrayList<HashMap<String, String>>();

        // json array use to buils the result
        static JSONArray jDataArray = null;
        SimpleAdapter adapter;

        ListView lvRaveList;
        String SERVER_URL;
        String SERVER_URL_NEW;
        int counter = 1;
        public boolean taskReady = false;
        boolean noNull = true;
        ProgressBar pbLoadRaves;
        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
                View view;
                view = inflater.inflate(R.layout.view_raves_fragment_layout, container,false);
                
                pbLoadRaves = (ProgressBar) view.findViewById(R.id.progressBar1);
                pbLoadRaves.setVisibility(View.GONE);
                
                lvRaveList = (ListView) view.findViewById(R.id.listView1);
                 SERVER_URL = "http://ct-vnotificat.virtusa.com/vmobile/api/GetAllRaves/GetFilteredRaves?loginName=ckdesilva&pageCount=0";
                 new ViewRaveTask().execute();
                lvRaveList.setOnItemClickListener(new OnItemClickListener() {
         public void onItemClick(AdapterView<?> adapter, View childView,
         int position, long id) {
                 TextView rtitle = (TextView) childView.findViewById(R.id.ravetitle);
                 TextView rmessage = (TextView) childView.findViewById(R.id.raveexplanation);
                 TextView rperson = (TextView) childView.findViewById(R.id.raveperson);
                 
                 Intent intent = new Intent(getActivity(),RaveDetailActivity.class);
                 intent.putExtra("rave_title", rtitle.getText().toString());
                 intent.putExtra("rave_msg", rmessage.getText().toString());
                 intent.putExtra("rave_person", rperson.getText().toString());
                 startActivity(intent);
                 
// TextView rmessage = (TextView) childView.findViewById(R.id.raveexplanation);
// if(rmessage.getLineCount()==1){
//         rmessage.setSingleLine(false);
//         int textLength = rmessage.getText().length();
//         if(textLength > 36){
//                 rmessage.setSingleLine(false);
//         int lineCount = Math.round(textLength/36) +1 ;
//         int height = lineCount * 30;
//         childView.getLayoutParams().height = height+85;
//         childView.requestLayout();
//         childView.invalidate();
//         }
//                                        
// }
// else{
//         rmessage.setSingleLine(true);
//         childView.getLayoutParams().height = 100;
//         childView.requestLayout();
//         childView.invalidate();
// }
         }
         });        
                
                
                lvRaveList.setOnScrollListener(new OnScrollListener() {
                        
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                                if(scrollState==0){
                                        taskReady = true;
                                }
                                
                        }
                        
                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem,
                                        int visibleItemCount, int totalItemCount) {
                                 if (firstVisibleItem+visibleItemCount == totalItemCount &&taskReady && noNull ) {
                                                 taskReady = false;
                                                SERVER_URL_NEW = "http://ct-vnotificat.virtusa.com/vmobile/api/GetAllRaves/GetFilteredRaves?loginName=ckdesilva&pageCount="+counter;
                                                new GetMoreRaveTask().execute();
                         }
                        }
                });
                
                
                return view;
        }
        
        
        private class ViewRaveTask extends AsyncTask<String, Void, Void> {

                String jsonData;
                
                @Override
                protected void onPreExecute() {
                        pbLoadRaves.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(String... params) {
                        String rave_KeywordId;
                        String rave_Tit;
                        String rave_Mes;
                        String rave_Send;
                        HttpClient client = new DefaultHttpClient();

                        HttpGet getRequest = new HttpGet(SERVER_URL);

                        try {
                                HttpResponse response = client.execute(getRequest);

                                InputStream jsonStream = response.getEntity().getContent();
                                BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(jsonStream));
                                StringBuilder builder = new StringBuilder();

                                String line;
                                while ((line = reader.readLine()) != null) {
                                        builder.append(line);
                                }

                                jsonData = builder.toString();

                                jDataArray = new JSONArray(jsonData);
                                //Log.d("JSONARRAY", jDataArray.toString());
                                int arrayLength = jDataArray.length();
                                for (int i = 0; i < arrayLength; i++) {

                                        rave_KeywordId = jDataArray.getJSONObject(i)
                                                        .getJSONObject("Keyword").getString("KeywordId");
                                        
                                        rave_Tit = jDataArray.getJSONObject(i)
                                                        .getJSONObject("Keyword").getString("Tagline");

                                        rave_Mes = jDataArray.getJSONObject(i).getString(
                                                        "RaveText");

                                        rave_Send = jDataArray.getJSONObject(i)
                                                        .getJSONObject("Sender").getString("FullName");


                                        //keywordId.add(rave_KeywordId);
                                        if(rave_KeywordId.equals("1")){
                                                categoryImage.add(R.drawable.pirl);
                                        }
                                        
                                        else if(rave_KeywordId.equals("2")){
                                                categoryImage.add(R.drawable.killer_code);
                                        }
                                        
                                        else if (rave_KeywordId.equals("3")) {
                                                categoryImage.add(R.drawable.eagle_eye);
                                        }
                                        
                                        else if (rave_KeywordId.equals("4")) {
                                                categoryImage.add(R.drawable.great_work);
                                        }
                                        
                                        else if (rave_KeywordId.equals("5")) {
                                                categoryImage.add(R.drawable.great_advice);
                                        }
                                        
                                        else if (rave_KeywordId.equals("6")) {
                                                categoryImage.add(R.drawable.great_idea);
                                        }
                                        
                                        else if (rave_KeywordId.equals("7")) {
                                                categoryImage.add(R.drawable.thanks_for_your_help);
                                        }
                                        
                                        else if (rave_KeywordId.equals("8")) {
                                                categoryImage.add(R.drawable.super_service);
                                        }
                                        
                                        else if (rave_KeywordId.equals("9")) {
                                                categoryImage.add(R.drawable.old_raves_default);
                                        }
                                        
                                        
                                        else{
                                                categoryImage.add(R.drawable.old_raves_default);
                                        }
                                        
                                        raveTitle.add(rave_Tit);
                                        raveMessage.add(rave_Mes);
                                        raveSender.add(rave_Send);

                                }

                        } catch (ClientProtocolException e) {

                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("error", e.toString());

                        } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("error", e.toString());

                        }
                        return null;
                }

                protected void onPostExecute(Void result) {

                        

                        int length = raveTitle.size();
                        for (int i = 0; i < length; i++) {
                                HashMap<String, String> rave = new HashMap<String, String>();
                                rave.put("cImg", Integer.toString(categoryImage.get(i)));
                                rave.put("rTitle", raveTitle.get(i));
                                rave.put("rMessage", raveMessage.get(i));
                                rave.put("rSender", raveSender.get(i));
                                raveList.add(rave);
                                
                                
                        }
                        adapter = new SimpleAdapter(getActivity(), raveList,
                                        R.layout.list_item_view_rave, from, to);
                        pbLoadRaves.setVisibility(View.GONE);
                        lvRaveList.setAdapter(adapter);

                }
        }
        
        
        
        
        private class GetMoreRaveTask extends AsyncTask<String, Void, Void> {

                String jsonData;
                

                protected void onPreExecute() {
                        pbLoadRaves.setVisibility(View.VISIBLE);
                }

                @Override
                protected Void doInBackground(String... params) {
                        String rave_KeywordId;
                        String rave_Tit;
                        String rave_Mes;
                        String rave_Send;
                        HttpClient client = new DefaultHttpClient();

                        HttpGet getRequest = new HttpGet(SERVER_URL_NEW);

                        try {
                                HttpResponse response = client.execute(getRequest);

                                InputStream jsonStream = response.getEntity().getContent();
                                BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(jsonStream));
                                StringBuilder builder = new StringBuilder();

                                String line;
                                while ((line = reader.readLine()) != null) {
                                        builder.append(line);
                                }

                                jsonData = builder.toString();
                                if(jsonData.equals("[]")){
                                        noNull = false;
                                }
                                
                                else{

                                jDataArray = new JSONArray(jsonData);
                                
                                int arrayLength = jDataArray.length();
                                raveList.clear();
                                for (int i = 0; i < arrayLength; i++) {

                                        rave_KeywordId = jDataArray.getJSONObject(i)
                                                        .getJSONObject("Keyword").getString("KeywordId");
                                        
                                        rave_Tit = jDataArray.getJSONObject(i)
                                                        .getJSONObject("Keyword").getString("Tagline");

                                        rave_Mes = jDataArray.getJSONObject(i).getString(
                                                        "RaveText");

                                        rave_Send = jDataArray.getJSONObject(i)
                                                        .getJSONObject("Sender").getString("FullName");


                                        if(rave_KeywordId.equals("1")){
                                                categoryImage.add(R.drawable.pirl);
                                        }
                                        
                                        else if(rave_KeywordId.equals("2")){
                                                categoryImage.add(R.drawable.killer_code);
                                        }
                                        
                                        else if (rave_KeywordId.equals("3")) {
                                                categoryImage.add(R.drawable.eagle_eye);
                                        }
                                        
                                        else if (rave_KeywordId.equals("4")) {
                                                categoryImage.add(R.drawable.great_work);
                                        }
                                        
                                        else if (rave_KeywordId.equals("5")) {
                                                categoryImage.add(R.drawable.great_advice);
                                        }
                                        
                                        else if (rave_KeywordId.equals("6")) {
                                                categoryImage.add(R.drawable.great_idea);
                                        }
                                        
                                        else if (rave_KeywordId.equals("7")) {
                                                categoryImage.add(R.drawable.thanks_for_your_help);
                                        }
                                        
                                        else if (rave_KeywordId.equals("8")) {
                                                categoryImage.add(R.drawable.super_service);
                                        }
                                        
                                        else if (rave_KeywordId.equals("9")) {
                                                categoryImage.add(R.drawable.old_raves_default);
                                        }
                                        
                                        
                                        else{
                                                categoryImage.add(R.drawable.old_raves_default);
                                        }
                                        
                                        raveTitle.add(rave_Tit);
                                        raveMessage.add(rave_Mes);
                                        raveSender.add(rave_Send);

                                }
                                }

                        } catch (ClientProtocolException e) {

                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("error", e.toString());

                        } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.e("error", e.toString());

                        }
                        return null;
                }

                protected void onPostExecute(Void result) {
                        pbLoadRaves.setVisibility(View.GONE);
                        if(noNull==true){

                        int length = raveTitle.size();
                        for (int i = 0; i < length; i++) {
                                HashMap<String, String> rave = new HashMap<String, String>();
                                rave.put("cImg", Integer.toString(categoryImage.get(i)));
                                rave.put("rTitle", raveTitle.get(i));
                                rave.put("rMessage", raveMessage.get(i));
                                rave.put("rSender", raveSender.get(i));
                                raveList.add(rave);
                        }
                                adapter.notifyDataSetChanged();
                                taskReady = true;
                                counter ++;
                        }
                }
        }
        
         private boolean isNetworkAvailable() {
         ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                         .getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetworkInfo = connectivityManager
                         .getActiveNetworkInfo();
         return activeNetworkInfo != null && activeNetworkInfo.isConnected();
         }
}