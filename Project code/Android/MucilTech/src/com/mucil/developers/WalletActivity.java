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

public class WalletActivity extends Fragment implements OnClickListener {

	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;

	private TextView tv_title;
	EditText pin, rechargepin, transactionid;
	Button balance, recharge;
	String Pin, rechargePin, transactionId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View v = inflater.inflate(R.layout.activity_wallet, container, false);

		pin = (EditText) v.findViewById(R.id.wallet_pin_tv);
		rechargepin = (EditText) v.findViewById(R.id.wallet_pin2_tv);
		transactionid = (EditText) v.findViewById(R.id.wallet_id_tv);

		recharge = (Button) v.findViewById(R.id.wallet_transact);
		balance = (Button) v.findViewById(R.id.wallet_balance);

		recharge.setOnClickListener(this);
		balance.setOnClickListener(this);

		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("Wallet");

		return v;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.wallet_balance:
			// invokeBalance(arg0);
			connection();

			break;
		case R.id.wallet_transact:
			// invokeTransaction(arg0);
			connection2();

			break;

		}

	}

	private void connection2() {
		// TODO Auto-generated method stub

		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			invokeTransaction();

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.create();
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

	private void connection() {
		// TODO Auto-generated method stub

		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			invokeBalance();

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.create();
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

	private void invokeTransaction() {
		// TODO Auto-generated method stub
		rechargePin = rechargepin.getText().toString();
		transactionId = transactionid.getText().toString();
		if (rechargePin.contentEquals("") || transactionId.contentEquals("")) {
			if (rechargePin.contentEquals("")) {
				rechargepin.setHint("Please enter pin");
			}

			if (transactionId.contentEquals("")) {
				transactionid.setHint("Please enter transaction id");
			}

		}
		//
		else {
			rechargepin.setHint("Pin or Identification");
			transactionid.setHint("Transaction Id");

			Transaction(rechargePin, transactionId);
			rechargepin.setText(null);
			transactionid.setText(null);

		}

	}

	private void Transaction(String rechargepin, String transactionid) {
		// TODO Auto-generated method stub
		class TransactionAsync extends AsyncTask<String, Void, String> {

			private Dialog loadingDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loadingDialog = ProgressDialog.show(getActivity(),
						"Transacting", "please wait...");
			}

			@Override
			protected String doInBackground(String... params) {
				String rechargepin = params[0];
				String transactionid = params[1];

				InputStream is = null;
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("pin", rechargepin));
				nameValuePairs.add(new BasicNameValuePair("id", transactionid));
				String result = null;

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(Config.TRANSACTION_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					HttpResponse response = httpClient.execute(httpPost);

					HttpEntity entity = response.getEntity();

					is = entity.getContent();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null) {
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
			protected void onPostExecute(String result) {
				String s = result.trim();
				loadingDialog.dismiss();
				if (s.equalsIgnoreCase("success")) {
					Toast.makeText(getActivity(), "Successful transaction",
							Toast.LENGTH_LONG).show();

				}
				else if(s.equalsIgnoreCase("failure")) {
					Toast.makeText(getActivity(), "Invalid transaction id",
							Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(getActivity(),
							"Invalid pin or transaction id", Toast.LENGTH_LONG)
							.show();
				}
			}
		}

		TransactionAsync Ta = new TransactionAsync();
		Ta.execute(rechargePin, transactionId);

	}

	// public void invokeBalance(View arg0)
	public void invokeBalance() {

		Pin = pin.getText().toString();
		if (Pin.contentEquals("")) {

			pin.setHint("Please enter pin");
		} else {
			pin.setHint("Pin or Identification");
			balance(Pin);
			pin.setText(null);
		}

	}

	private void balance(final String pin) {

		class BalanceAsync extends AsyncTask<String, Void, String> {

			private Dialog loadingDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loadingDialog = ProgressDialog.show(getActivity(),
						"Retrieving", "please wait...");
			}

			@Override
			protected String doInBackground(String... params) {
				String pin = params[0];

				InputStream is = null;
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("pin", pin));
				String result = null;

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(Config.BALANCE_URL);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					HttpResponse response = httpClient.execute(httpPost);

					HttpEntity entity = response.getEntity();

					is = entity.getContent();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null) {
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
			protected void onPostExecute(String result) {
				String s = result.trim();
				loadingDialog.dismiss();
				if (s.equalsIgnoreCase("failure")) {
					Toast.makeText(getActivity(),
							"Invalid pin...identification", Toast.LENGTH_LONG)
							.show();

				} else {
					// Toast.makeText(getActivity(), "Your balance is " + s,
					// Toast.LENGTH_LONG).show();
					new AlertDialog.Builder(getActivity())
							.setTitle("Balance notification")
							.setMessage(
									"Your wallet balance is..\n"
											+ "           " + s)
							.setNeutralButton("ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();

					//
				}
			}
		}

		BalanceAsync ba = new BalanceAsync();
		ba.execute(Pin);

	}

	//

}
