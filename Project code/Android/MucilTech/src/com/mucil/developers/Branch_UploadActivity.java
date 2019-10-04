package com.mucil.developers;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Branch_UploadActivity extends Activity implements OnClickListener {

	Boolean isInternetPresent = false;
	ConnectionDetector cd;

	EditText branch_name, branch_location, branch_address, branch_contact;
	Button add_branch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		branch_name = (EditText) findViewById(R.id.branch_name_et);
		branch_location = (EditText) findViewById(R.id.branch_location_et);
		branch_address = (EditText) findViewById(R.id.branch_address_et);
		branch_contact = (EditText) findViewById(R.id.branch_contact_et);
		add_branch = (Button) findViewById(R.id.addbranch);

		add_branch.setOnClickListener(this);

	}

	private void connection() {
		// TODO Auto-generated method stub
		cd = new ConnectionDetector(Branch_UploadActivity.this);
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			uploadBranch();

		} else {
			Toast.makeText(Branch_UploadActivity.this, "No connection!", Toast.LENGTH_LONG)
					.show();
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String pname2 = branch_name.getText().toString();
		String plocation2 = branch_location.getText().toString();
		String paddress2 = branch_address.getText().toString();
		String pcontact2 = branch_contact.getText().toString();
		if (pname2.contentEquals("") || plocation2.contentEquals("")
				|| paddress2.contentEquals("") || pcontact2.contentEquals("")) {
			if (pname2.contentEquals("")) {
				branch_name.setHint("Please input name !");
				branch_name.setHintTextColor(Color.RED);
			}

			if (plocation2.contentEquals("")) {
				branch_location.setHint("Please input location !");
				branch_location.setHintTextColor(Color.RED);
			}
			if (paddress2.contentEquals("")) {
				branch_address.setHint("Please input address !");
				branch_address.setHintTextColor(Color.RED);
			}

			if (pcontact2.contentEquals("")) {
				branch_contact.setHint("Please input contact !");
				branch_contact.setHintTextColor(Color.RED);

			}
		}

		else {
			connection();
			
		}

	}

	private void uploadBranch() {
		// TODO Auto-generated method stub
		class uploadBranch extends AsyncTask<Bitmap, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(Branch_UploadActivity.this,
						"Uploading...", null, true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
						.show();

				branch_name.setText("");
				branch_name.setHint("Kisii boxhouse");
				branch_name.setHintTextColor(Color.BLUE);

				branch_location.setText("");
				branch_location.setHint("Sansora complex-Kisii");
				branch_location.setHintTextColor(Color.BLUE);

				branch_address.setText("");
				branch_address.setHint("56-100 kisii");
				branch_address.setHintTextColor(Color.BLUE);

				branch_contact.setText("");
				branch_contact.setHint("0790959933");
				branch_contact.setHintTextColor(Color.BLUE);
			}

			@Override
			protected String doInBackground(Bitmap... params) {
				String pname = branch_name.getText().toString();
				String plocation = branch_location.getText().toString();
				String paddress = branch_address.getText().toString();
				String pcontact = branch_contact.getText().toString();

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("pname", pname);
				data.put("plocation", plocation);
				data.put("paddress", paddress);
				data.put("pcontact", pcontact);

				String result = rh.sendPostRequest(Config.ADD_BRANCH_URL, data);

				return result;
			}
		}

		uploadBranch ub = new uploadBranch();
		ub.execute();
	}

}
