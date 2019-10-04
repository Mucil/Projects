package com.mucil.developers;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Fragment implements OnClickListener {

	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	
	private TextView tv_title;

	private String[] region = { "Western", "Nyanza", "Eastern", "Central",
			"North eastern", "Rift valley", "Coast" };
	Spinner RegionSpinner;
	EditText names, identification, descriptive_address, pnone_no, pin;

	String Pin;
	Button register, reset;
	//
	private ProgressDialog pDialog;	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.activity_register, container, false);
		RegionSpinner = (Spinner) v.findViewById(R.id.sp_region);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_spinner_item,
				region);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		RegionSpinner.setAdapter(adapter);

		names = (EditText) v.findViewById(R.id.et_names);
		identification = (EditText) v.findViewById(R.id.et_id);
		descriptive_address = (EditText) v
				.findViewById(R.id.et_descriptive_address);
		pnone_no = (EditText) v.findViewById(R.id.et_phone_no);

		register = (Button) v.findViewById(R.id.register);
		reset = (Button) v.findViewById(R.id.reset);

		register.setOnClickListener(this);
		reset.setOnClickListener(this);

		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("Register");

		return v;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.register:
			connection();
			

			break;

		case R.id.reset:
			Intent reset = new Intent(getActivity(), ResetActivity.class);
			startActivity(reset);

			break;
		}

	}
	

	private void connection() {
		// TODO Auto-generated method stub

		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			dataProcess();

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.create();
			alertDialog.setTitle("No connection!");
			alertDialog.setMessage("Turn on connection and try again");
			alertDialog.setIcon(R.drawable.no_internet);
			alertDialog.setButton("settings", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(Settings.ACTION_SETTINGS));

				}
			});
			alertDialog.show();

		}

	}

	
	private void dataProcess() {
		// TODO Auto-generated method stub
		final String names2 = names.getText().toString();
		final String identification2 = identification.getText().toString();
		final String descriptive_address2 = descriptive_address.getText()
				.toString();
		final String pnone_no2 = pnone_no.getText().toString();
		int typerow = RegionSpinner.getSelectedItemPosition();
		final String type = region[typerow];

		if (names2.contentEquals("") || identification2.contentEquals("") || descriptive_address2.contentEquals("") || pnone_no2.contentEquals("")) {
			if (names2.contentEquals("")) {
				names.setHint("Please input your names ?");
				names.setHintTextColor(Color.RED);
			}

			if (identification2.contentEquals("")) {
				identification.setHint("Please input your ID ?");
				identification.setHintTextColor(Color.RED);
			}
			if (descriptive_address2.contentEquals("")) {
				descriptive_address.setHint("Please input your address ?");
				descriptive_address.setHintTextColor(Color.RED);
			}
			if (pnone_no2.contentEquals("")) {
				pnone_no.setHint("Please input your phone no ?");
				pnone_no.setHintTextColor(Color.RED);
			}
			
		} else {

			AlertDialog.Builder bld = new AlertDialog.Builder(
					this.getActivity());
			bld.setTitle("DETAILS");
			bld.setMessage("Details entered" + "\n" + names2 + "\n"
					+ identification2 + "\n" + descriptive_address2 + "\n"
					+ pnone_no2 + "\n" + type);
			bld.setPositiveButton("CONFIRM",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub

						}
					});
			bld.setNegativeButton("SAVE",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							new CreateUser().execute();

						}
					});
			AlertDialog alrt = bld.create();
			alrt.show();

		}
		;

	}

	class CreateUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		RequestHandler rh = new RequestHandler();
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Creating Customer...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			String Names = names.getText().toString();
			String Identification = identification.getText().toString();
			String Descriptive_address = descriptive_address.getText()
					.toString();
			String Pnone_no = pnone_no.getText().toString();
			int typerow = RegionSpinner.getSelectedItemPosition();
			String Region = region[typerow];

			HashMap<String, String> data = new HashMap<String, String>();
			data.put("names", Names);
			data.put("identification", Identification);
			data.put("address", Descriptive_address);
			data.put("phone", Pnone_no);
			data.put("region", Region);

			String result = rh.sendPostRequest(Config.REGISTER_URL, data);

			return result;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String result) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();

			String s = result.trim();
			if (s.equalsIgnoreCase("exist")) {
				Toast.makeText(getActivity(), "User with this ID exists",
						Toast.LENGTH_LONG).show();
			} else if (s.equalsIgnoreCase("success")) {
				Toast.makeText(getActivity(), "Customer added successfully",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getActivity(), "Error executing the request",
						Toast.LENGTH_LONG).show();
			}

		}

	}

}
