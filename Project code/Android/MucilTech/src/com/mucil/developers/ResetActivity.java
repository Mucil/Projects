package com.mucil.developers;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ResetActivity extends Activity implements OnClickListener {
	Boolean isInternetPresent = false;
	ConnectionDetector cd;

	private String[] region = { "Western", "Nyanza", "Eastern", "Central",
			"North eastern", "Rift valley", "Coast" };

	EditText Identification, Names, Address, Phone;
	String identification, names, address, phone, location;
	Button load, reset;
	Spinner RegionSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset);

		Identification = (EditText) findViewById(R.id.customer_pin);
		Names = (EditText) findViewById(R.id.customer_names);
		Address = (EditText) findViewById(R.id.customer_address);
		Phone = (EditText) findViewById(R.id.customer_phone);

		load = (Button) findViewById(R.id.customer_load);
		reset = (Button) findViewById(R.id.customer_reset);

		RegionSpinner = (Spinner) findViewById(R.id.customer_region);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ResetActivity.this, android.R.layout.simple_spinner_item,
				region);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		RegionSpinner.setAdapter(adapter);

		load.setOnClickListener(this);
		reset.setOnClickListener(this);

	}

	private void connection() {
		// TODO Auto-generated method stub

		cd = new ConnectionDetector(ResetActivity.this);
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			load();

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(
					ResetActivity.this).create();
			alertDialog.setTitle("No connection!");
			alertDialog.setMessage("Turn on connection and try again");
			alertDialog.setIcon(R.drawable.no_internet);
			alertDialog.setButton("settings",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(Settings.ACTION_SETTINGS));

						}
					});
			alertDialog.show();

		}

	}

	private void connection2() {
		// TODO Auto-generated method stub

		cd = new ConnectionDetector(ResetActivity.this);
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {

			String id = Identification.getText().toString();
			String names = Names.getText().toString();
			if (id.contentEquals("") || names.contentEquals("")) {
				if (id.contentEquals("")) {
					Identification.setHint("Identification please ?");
					Identification.setHintTextColor(Color.RED);
				}

				if (names.contentEquals("")) {
					Names.setHint("Names please ?");
					Names.setHintTextColor(Color.RED);
				}
			} else {
				reset();
			}

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(
					ResetActivity.this).create();
			alertDialog.setTitle("No connection!");
			alertDialog.setMessage("Turn on connection and try again");
			alertDialog.setIcon(R.drawable.no_internet);
			alertDialog.setButton("settings",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(Settings.ACTION_SETTINGS));

						}
					});
			alertDialog.show();

		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		case R.id.customer_load:
			String id = Identification.getText().toString();

			if (id.contentEquals("")) {

				Toast.makeText(getApplicationContext(), "Please input identification",
						Toast.LENGTH_LONG).show();
			} else {
				connection();
			}

			break;
		case R.id.customer_reset:
			connection2();
			break;

		}

	}

	private void reset() {
		// TODO Auto-generated method stub
		class reset extends AsyncTask<String, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(ResetActivity.this,
						"Reseting deatails", "wait...", true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
						.show();

				Identification.setText("");
				Names.setText("");
				Address.setText("");
				Phone.setText("");

			}

			@Override
			protected String doInBackground(String... params) {
				identification = Identification.getText().toString();
				names = Names.getText().toString();
				address = Address.getText().toString();
				phone = Phone.getText().toString();

				int typerow = RegionSpinner.getSelectedItemPosition();
				location = region[typerow];

				HashMap<String, String> data = new HashMap<String, String>();
				data.put("identification", identification);
				data.put("names", names);
				data.put("address", address);
				data.put("phone", phone);
				data.put("location", location);

				String result = rh.sendPostRequest(Config.RESET_URL, data);

				return result;

			}
		}

		reset r = new reset();
		r.execute();

	}

	private void load() {
		// TODO Auto-generated method stub

		class load extends AsyncTask<String, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(ResetActivity.this,
						"Retrieving", "wait...", true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				String JSON_STRING = s;
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(JSON_STRING);
					JSONArray result = jsonObject.getJSONArray("result");

					for (int i = 0; i < result.length(); i++) {
						JSONObject jo = result.getJSONObject(i);
						names = jo.getString("names");
						address = jo.getString("address");
						phone = jo.getString("phone");

						Names.setText(names);
						Address.setText(address);
						Phone.setText(phone);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				String cid = Identification.getText().toString();

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("cid", cid);

				String result = rh.sendPostRequest(Config.LOAD_URL, data);
				return result;

			}
		}

		load l = new load();
		l.execute();

	}

}
