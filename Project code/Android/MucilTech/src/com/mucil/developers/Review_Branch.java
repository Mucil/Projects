package com.mucil.developers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Review_Branch extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private ArrayList<Branches> brancheslist;
	EditText branch_id, branch_name, branch_location, branch_address,
			branch_contact;
	Button review_branch;
	Spinner branch;
	String bid;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branch_review);

		branch = (Spinner) findViewById(R.id.sp_branch);
		brancheslist = new ArrayList<Branches>();
		branch.setOnItemSelectedListener(this);
		new GetBranches().execute();

		// branch_id = (EditText)findViewById(R.id.branch_id_etr);
		branch_name = (EditText) findViewById(R.id.branch_name_etr);
		branch_location = (EditText) findViewById(R.id.branch_location_etr);
		branch_address = (EditText) findViewById(R.id.branch_address_etr);
		branch_contact = (EditText) findViewById(R.id.branch_contact_etr);
		review_branch = (Button) findViewById(R.id.reviewbranch);

		review_branch.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.reviewbranch:
			validate();
			// reviewBranch();
			break;

		}

	}

	private void validate() {
		// TODO Auto-generated method stub
		String bname = branch_name.getText().toString();
		String blocation = branch_location.getText().toString();
		String baddress = branch_address.getText().toString();
		String bcontact = branch_contact.getText().toString();

		if (bname.contentEquals("") || blocation.contentEquals("")
				|| baddress.contentEquals("") || bcontact.contentEquals("")) {
			if (bname.contentEquals("")) {
				branch_name.setHint("Please enter name? ");
				branch_name.setHintTextColor(Color.RED);
			}

			if (blocation.contentEquals("")) {
				branch_location.setHint("Please enter location ?");
				branch_location.setHintTextColor(Color.RED);
			}
			if (baddress.contentEquals("")) {
				branch_address.setHint("Please enter address ?");
				branch_address.setHintTextColor(Color.RED);
			}
			if (bcontact.contentEquals("")) {
				branch_contact.setHint("Please enter contact ?");
				branch_contact.setHintTextColor(Color.RED);
			}

		} else {
			reviewBranch();
		}

	}

	private void reviewBranch() {
		// TODO Auto-generated method stub
		class reviewBranch extends AsyncTask<Bitmap, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(Review_Branch.this,
						"Reviewing", "pleas wait", true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
						.show();

				branch_name.setText("");
				branch_name.setHint("");
				
				branch_location.setText("");
				branch_location.setHint("");
				
				branch_address.setText("");
				branch_address.setHint("");
				
				branch_contact.setText("");
				branch_contact.setHint("");
			}

			@Override
			protected String doInBackground(Bitmap... params) {

				String bname = branch_name.getText().toString();
				String blocation = branch_location.getText().toString();
				String baddress = branch_address.getText().toString();
				String bcontact = branch_contact.getText().toString();

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("bid", bid);
				data.put("bname", bname);
				data.put("blocation", blocation);
				data.put("baddress", baddress);
				data.put("bcontact", bcontact);

				String result = rh.sendPostRequest(Config.REVIEW_BRANCH_URL,
						data);

				return result;

			}
		}

		reviewBranch rb = new reviewBranch();
		rb.execute();

	}

	private void querryBranch() {
		// TODO Auto-generated method stub

		class querryBranch extends AsyncTask<String, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(Review_Branch.this, "Loading",
						"Please wait", true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				// Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

				//
				String JSON_STRING = s;
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(JSON_STRING);
					JSONArray result = jsonObject.getJSONArray("result");

					for (int i = 0; i < result.length(); i++) {
						JSONObject jo = result.getJSONObject(i);
						// String bid = jo.getString("bid");
						String bname = jo.getString("bname");
						String blocation = jo.getString("blocation");
						String baddress = jo.getString("baddress");
						String bcontact = jo.getString("bcontact");

						// branch_id.setText(bid);
						branch_name.setText(bname);
						branch_location.setText(blocation);
						branch_address.setText(baddress);
						branch_contact.setText(bcontact);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				//
			}

			@Override
			protected String doInBackground(String... params) {
				// String pid = branch_id.getText().toString();
				// String pid = "Kondele box";

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("bid", bid);

				String result = rh.sendPostRequest(Config.QUERYY_BRANCH_URL,
						data);

				return result;
			}
		}

		querryBranch qb = new querryBranch();
		qb.execute();

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		bid = arg0.getItemAtPosition(arg2).toString();
		querryBranch();
		// Toast.makeText(getApplicationContext(),arg0.getItemAtPosition(arg2).toString()
		// + " Selected" ,Toast.LENGTH_LONG).show();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	private class GetBranches extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			RequestHandler rh = new RequestHandler();
			String json = rh.sendGetRequest(Config.BRANCHES_URL);

			try {
				JSONObject jsonObj = new JSONObject(json);
				if (jsonObj != null) {
					JSONArray branches = jsonObj.getJSONArray("result");

					for (int i = 0; i < branches.length(); i++) {
						JSONObject listObj = (JSONObject) branches.get(i);
						Branches list = new Branches(listObj.getString("name"));

						brancheslist.add(list);
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			populateSpinner();
		}

	}

	private void populateSpinner() {
		// TODO Auto-generated method stub
		List<String> lables = new ArrayList<String>();

		for (int i = 0; i < brancheslist.size(); i++) {
			lables.add(brancheslist.get(i).getName());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		branch.setAdapter(spinnerAdapter);

	}

}
