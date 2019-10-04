package com.mucil.developers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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

import com.mucil.developers.OffersActivity.JsonTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class CartActivity extends Fragment implements OnItemClickListener,
		OnClickListener {

	Boolean isInternetPresent = false;
	ConnectionDetector cd;

	private TextView tv_title;
	TextView no_items;
	private DatabaseHelper dbHelp;
	String mCurFilter;
	private ListView lvCart;
	EditText pin;
	private SQLiteDatabase dataBase;
	Button place;

	String Pin;
	String Grandtotal;
	public int GrandTotal = 0;
	public String ReceiptContents;
	public String ReceiptQuantities;
	// variables to hold customer's details
	private ArrayList<String> txtName = new ArrayList<String>();
	private ArrayList<String> txtPrice = new ArrayList<String>();
	private ArrayList<String> txtQuantity = new ArrayList<String>();
	private ArrayList<String> txtTotal = new ArrayList<String>();
	private ArrayList<String> GRANDTOTAL = new ArrayList<String>();
	private ArrayList<String> NAMES = new ArrayList<String>();
	private ArrayList<String> QUANTITY = new ArrayList<String>();
	Cursor cursor;
	int pos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.activity_cart, container, false);

		dbHelp = new DatabaseHelper(getActivity());
		ReceiptQuantities = "";
		ReceiptContents = "";

		lvCart = (ListView) v.findViewById(R.id.listViewCart);
		lvCart.setOnItemClickListener(this);

		no_items = (TextView) v.findViewById(R.id.no_items);
		place = (Button) v.findViewById(R.id.btnPlace);
		place.setOnClickListener(this);

		displayData();
		check();
		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("Cart");
		return v;
	}

	private void connection() {
		// TODO Auto-generated method stub
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			invokeBuying();
		} else {

			Toast.makeText(getActivity(), "No connection!", Toast.LENGTH_LONG)
					.show();

		}

	}

	private void check() {
		// TODO Auto-generated method stub
		dataBase = dbHelp.getWritableDatabase();
		Cursor cursor = dataBase.rawQuery("SELECT * FROM "
				+ DatabaseHelper.TABLE_NAME, null);
		if (cursor.getCount() == 0) {
			place.setVisibility(View.INVISIBLE);
			no_items.setVisibility(View.VISIBLE);
		} else {
			place.setVisibility(View.VISIBLE);
			no_items.setVisibility(View.INVISIBLE);
		}

		cursor.close();

	}

	private int totalSum() {
		dataBase = dbHelp.getWritableDatabase();
		Cursor cursor = dataBase.rawQuery("SELECT * FROM "
				+ DatabaseHelper.TABLE_NAME, null);

		if (cursor.moveToFirst()) {
			do {

				GRANDTOTAL.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.TOTAL)));
			} while (cursor.moveToNext());
		}

		if (GrandTotal == 0) {
			for (int i = 0; i < GRANDTOTAL.size(); i++) {
				int Grand = Integer.parseInt(GRANDTOTAL.get(i));

				GrandTotal = GrandTotal + Grand;
			}
		}
		// else
		cursor.close();
		return GrandTotal;
	}// end totalSum

	private void displayData() {
		dataBase = dbHelp.getWritableDatabase();
		// the SQL command to fetched all records from the table
		Cursor cursor = dataBase.rawQuery("SELECT * FROM "
				+ DatabaseHelper.TABLE_NAME, null);

		// reset variables
		txtName.clear();
		txtPrice.clear();
		txtQuantity.clear();
		txtTotal.clear();

		// fetch each record
		if (cursor.moveToFirst()) {
			do {
				// get data from field
				txtName.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.NAME)));
				txtPrice.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.PRICE)));
				txtQuantity.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.QUANTITY)));
				txtTotal.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.TOTAL)));

			} while (cursor.moveToNext());
			// do above till data exhausted
		}

		// display to screen
		CartAdapter adapter = new CartAdapter(getActivity(), txtName, txtPrice,
				txtQuantity, txtTotal);
		lvCart.setAdapter(adapter);
		cursor.close();
	}// end displayData

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		TextView Name = (TextView) arg1.findViewById(R.id.cartProductName);
		TextView Price = (TextView) arg1.findViewById(R.id.cartProductPrice);
		TextView Quantity = (TextView) arg1
				.findViewById(R.id.cartProductQuantity);
		TextView Total = (TextView) arg1.findViewById(R.id.cartProductTotal);

		final String name = Name.getText().toString();
		final String price = Price.getText().toString();
		final String quantity = Quantity.getText().toString();
		final String total = Total.getText().toString();

		new AlertDialog.Builder(getActivity())
				.setTitle("Delete Item")
				.setMessage(
						name + "\n\n     unit cost.." + price
								+ "\n     quantity.." + quantity
								+ "\n\n     total........" + total)
				.setNeutralButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setPositiveButton("delete",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dbHelp.deletesingleRecord(name, price,
										quantity, total);
								check();
								displayData();

							}
						}).show();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		buyLoginDialog();
		
		

	}

	private void buyLoginDialog() {
		// TODO Auto-generated method stub
		// get prompts.xml view

		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View promptView = layoutInflater.inflate(R.layout.buy_login, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set prompts.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);
		final TextView receipt = (TextView) promptView
				.findViewById(R.id.receipt_contents);
		final TextView total = (TextView) promptView
				.findViewById(R.id.receipt_total);
		pin = (EditText) promptView.findViewById(R.id.receipt_pin);
		//
		dataBase = dbHelp.getWritableDatabase();
		Cursor cursor = dataBase.rawQuery("SELECT * FROM "
				+ DatabaseHelper.TABLE_NAME, null);
		if (cursor.moveToFirst()) {
			do {

				NAMES.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.NAME)));
				QUANTITY.add(cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.QUANTITY)));

			} while (cursor.moveToNext());
		}
		if (ReceiptContents.contentEquals("")) {
			ReceiptContents = "";
			for (int i = 0; i < NAMES.size(); i++) {

				ReceiptContents += "[" + NAMES.get(i) + "] \n";
			}
		}
		// else

		if (ReceiptQuantities.contentEquals("")) {
			ReceiptQuantities = "";
			for (int i = 0; i < QUANTITY.size(); i++) {

				ReceiptQuantities += "[" + QUANTITY.get(i) + "] \n";
			}
		}

		cursor.close();
		//
		receipt.setText(ReceiptQuantities);
		total.setText(Integer.toString(totalSum()));

		// setup a dialog window
		alertDialogBuilder
				.setTitle("Buyer Login")
				.setCancelable(false)
				.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Toast.makeText(getActivity(), pin.getText(),
								// Toast.LENGTH_LONG).show();

							}
						})
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						Pin = pin.getText().toString();
						if (Pin.contentEquals("")) {

							Toast.makeText(getActivity(),
									"No identification was entered !", Toast.LENGTH_LONG)
									.show();
						} else {
							// dbHelp.deleteAllRecord();
							// displayData();
							connection();
							
						}

					}

				});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();

	}

	private void invokeBuying() {
		// TODO Auto-generated method stub
		Pin = pin.getText().toString();
		Grandtotal = Integer.toString(GrandTotal);

		Buying(Pin, ReceiptContents, ReceiptQuantities, Grandtotal);

	}

	private void Buying(String pin, String receiptcontents,
			String receiptquantities, String grandtotal) {
		// TODO Auto-generated method stub

		class BuyAsync extends AsyncTask<String, Void, String> {

			private Dialog loadingDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loadingDialog = ProgressDialog.show(getActivity(),
						"Please wait", "sending order...");
			}

			@Override
			protected String doInBackground(String... params) {
				String PIN = params[0];
				String RECEIPTCONTENTS = params[1];
				String RECEIPTQUANTITIES = params[2];
				String GRANDTOTAL = params[3];

				InputStream is = null;
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("pin", PIN));
				nameValuePairs.add(new BasicNameValuePair("receiptcontents",
						RECEIPTCONTENTS));
				nameValuePairs.add(new BasicNameValuePair("receiptquantities",
						RECEIPTQUANTITIES));
				nameValuePairs.add(new BasicNameValuePair("grandtotal",
						GRANDTOTAL));
				String result = null;

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(Config.CART_URL);
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

					Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG)
							.show();

					dbHelp.deleteAllRecord();
					check();
					displayData();

				} else if (s.equalsIgnoreCase("insufficient")) {
					Toast.makeText(getActivity(), "insufficient funds",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(),
							"invalid identification", Toast.LENGTH_LONG)
							.show();
				}
			}
		}

		BuyAsync ba = new BuyAsync();
		ba.execute(Pin, ReceiptContents, ReceiptQuantities, Grandtotal);

	}

	//

}
