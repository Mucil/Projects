package com.mucil.developers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.mucil.developers.ProductsActivity.JsonTask;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends Fragment implements OnClickListener {
	
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	
	private TextView tv_title;
	private EditText editTextUserName, editTextPassword;
	private Button login;

	

	public static final String USER_NAME = "USERNAME";

	String username;
	String password;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View v = inflater.inflate(R.layout.activity_admin, container, false);

		editTextUserName = (EditText) v.findViewById(R.id.admin_username_tv);
		editTextPassword = (EditText) v.findViewById(R.id.admin_password_tv);
		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("Authority");
		
		login = (Button)v.findViewById(R.id.admin_login);
		login.setOnClickListener(this);

		return v;

	}
	private void connection() {
		// TODO Auto-generated method stub
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			invokeLogin();
			
		} else {
			Toast.makeText(getActivity(), "No connection!", Toast.LENGTH_LONG).show();			
		}
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		connection();

	}
	
	public void invokeLogin(){
	
		
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        
        if (username.contentEquals("") || password.contentEquals("")) {
			if (username.contentEquals("")) {
				editTextUserName.setHint("Username please ?");
				editTextUserName.setHintTextColor(Color.RED);
			}

			if (password.contentEquals("")) {
				editTextPassword.setHint("Password please ?");
				editTextPassword.setHintTextColor(Color.RED);
			}
			;
		} else {
			login(username,password);	
		}
 
        
 
    }
 
    private void login(final String username, String password) {
 
        class LoginAsync extends AsyncTask<String, Void, String>{
 
            private Dialog loadingDialog;
 
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(getActivity(), "Admin login", "Please wait...");
            }
 
            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];
 
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;
 
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Config.ADMIN_LOGIN);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 
                    HttpResponse response = httpClient.execute(httpPost);
 
                    HttpEntity entity = response.getEntity();
 
                    is = entity.getContent();
 
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
 
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
 
            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                	Intent intent = new Intent(getActivity(), AuthorityActivity.class);
                    //intent.putExtra(USER_NAME, username);
                    getActivity().finish();
                    startActivity(intent);                    
                }else {
                    Toast.makeText(getActivity(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }
 
        LoginAsync la = new LoginAsync();
        la.execute(username, password);
 
    }
	
}
