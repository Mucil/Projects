package com.mucil.developers;


import static com.mucil.developers.Config.TAG_ID;
import static com.mucil.developers.Config.TAG_NAME;
import static com.mucil.developers.Config.TAG_LOCATION;
import static com.mucil.developers.Config.TAG_ADDRESS;
import static com.mucil.developers.Config.TAG_CONTACTS;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BranchesActivity extends Fragment implements OnItemClickListener{ 
private ListView listView;
private String JSON_STRING;
private TextView tv_title;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	super.onCreateView(inflater, container, savedInstanceState);
	
	View v = inflater.inflate(R.layout.activity_branches, container, false);
	
	listView = (ListView) v.findViewById(R.id.listView);
	listView.setOnItemClickListener(this);
    getJSON();
    tv_title =(TextView)v.findViewById(R.id.tv_title);
	tv_title.setText("Branches");
	
	return v;
}
private void getJSON() {
	// TODO Auto-generated method stub
	class GetJSON extends AsyncTask<Void,Void,String>{

        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getActivity(),"Fetching data","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            JSON_STRING = s;
            showEmployee();
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHandler rh = new RequestHandler();
            
            String s = rh.sendGetRequest(Config.URL_GET_BRANCES);
            return s;
        }
    }
    GetJSON gj = new GetJSON();
    gj.execute();
}
private void showEmployee(){
    JSONObject jsonObject = null;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    try {
        jsonObject = new JSONObject(JSON_STRING);
        JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

        
        
        
        
        for(int i = 0; i<result.length(); i++){
            JSONObject jo = result.getJSONObject(i);
            String id = jo.getString(Config.TAG_ID);
            String name = jo.getString(Config.TAG_NAME);
            String location = jo.getString(Config.TAG_LOCATION);
            String address = jo.getString(Config.TAG_ADDRESS);
            String contacts = jo.getString(Config.TAG_CONTACTS);
            
            
            
            
            HashMap<String,String> branches = new HashMap<String, String>();
            branches.put(TAG_ID,id);
            branches.put(TAG_NAME,name);
            branches.put(TAG_LOCATION,location);
            branches.put(TAG_ADDRESS,address);
            branches.put(TAG_CONTACTS,contacts);
            
            list.add(branches);
        }

    } catch (JSONException e) {
        e.printStackTrace();
    }

    ListAdapter adapter = new SimpleAdapter(
            getActivity(), list, R.layout.branchs_list,
            new String[]{Config.TAG_NAME,Config.TAG_LOCATION,Config.TAG_ADDRESS,Config.TAG_CONTACTS},
            new int[]{R.id.name,R.id.location, R.id.address,R.id.contacts}); 

    listView.setAdapter(adapter); 
	
}
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	TextView Phone = (TextView)arg1.findViewById(R.id.contacts);
	
    //final String total = Total.getText().toString();
	try {
		String uri = "tel:"+Phone.getText().toString();
		Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
		
		startActivity(dialIntent);
	}catch(Exception e) {
		Toast.makeText(getActivity(),"Cant open dialpad...",
			Toast.LENGTH_LONG).show();
		e.printStackTrace();
	}
    
   
}


}
