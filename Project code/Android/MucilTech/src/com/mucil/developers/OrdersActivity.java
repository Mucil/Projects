package com.mucil.developers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mucil.developers.Product_ReviewActivity.JsonTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OrdersActivity extends Activity implements OnItemClickListener{

	private ListView listView;
	private String JSON_STRING;

	String Oid;
	String OId;
	String Ot;
	String Username;
	String PhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		

		listView = (ListView) findViewById(R.id.listViewOrders);
		listView.setOnItemClickListener(this);
		getJSON();
	}

	private void getJSON() {
		// TODO Auto-generated method stub
		class GetJSON extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(OrdersActivity.this,
						"Fetching...", null, false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				showOder();
			}

			@Override
			protected String doInBackground(Void... params) {
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.GET_ORDERS_URL);
				return s;
			}
		}
		GetJSON gj = new GetJSON();
		gj.execute();
	}

	private void showOder() {
		JSONObject jsonObject = null;
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			jsonObject = new JSONObject(JSON_STRING);
			JSONArray result = jsonObject.getJSONArray("result");

			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);

				String order_user = jo.getString("order_user");
				String order_phone = jo.getString("order_phone");
				String oder_id = jo.getString("oder_id");
				String oder_identification = jo
						.getString("oder_identification");
				String oder_receiptcontents = jo
						.getString("oder_receiptcontents");
				String oder_receiptquantities = jo
						.getString("oder_receiptquantities");
				String oder_grandtotal = jo.getString("oder_grandtotal");

				HashMap<String, String> orders = new HashMap<String, String>();

				orders.put("order_user", order_user);
				orders.put("order_phone", order_phone);
				orders.put("oder_id", oder_id);
				orders.put("oder_identification", oder_identification);
				orders.put("oder_receiptcontents", oder_receiptcontents);
				orders.put("oder_receiptquantities", oder_receiptquantities);
				orders.put("oder_grandtotal", oder_grandtotal);

				list.add(orders);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(OrdersActivity.this, list,
				R.layout.orders, new String[] { "order_user", "order_phone",
						"oder_id", "oder_identification",
						"oder_receiptcontents", "oder_receiptquantities",
						"oder_grandtotal" }, new int[] { R.id.order_user,
						R.id.order_phone, R.id.order_id,
						R.id.order_identification, R.id.order_receiptcontents,
						R.id.order_receiptquantities, R.id.order_total });

		listView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		TextView oderId = (TextView) arg1.findViewById(R.id.order_id);
		TextView oderIdentification = (TextView) arg1
				.findViewById(R.id.order_identification);
		TextView oderUser = (TextView) arg1.findViewById(R.id.order_user);
		TextView oderPhone = (TextView) arg1.findViewById(R.id.order_phone);
		// TextView orderReceiptcontents = (TextView) arg1
		// .findViewById(R.id.order_receiptcontents);
		// TextView orderReceiptquantities = (TextView) arg1
		// .findViewById(R.id.order_receiptquantities);
		TextView orderTotal = (TextView) arg1.findViewById(R.id.order_total);

		Oid = oderId.getText().toString();
		OId = oderIdentification.getText().toString();
		Username = oderUser.getText().toString();
		PhoneNumber = oderPhone.getText().toString();
		// final String Orc = orderReceiptcontents.getText().toString();
		// final String Orq = orderReceiptquantities.getText().toString();
		Ot = orderTotal.getText().toString();

		new AlertDialog.Builder(OrdersActivity.this)
				.setTitle("Approve order")
				.setMessage(Oid + "#" + OId + "#" + Ot)
				.setNeutralButton("message",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								approveOrder();
								message();
							}

						})
				.setPositiveButton("call",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								approveOrder();
								call();

							}

						}).show();

	}

	private void approveOrder() {

		class approveOrder extends AsyncTask<Bitmap, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(OrdersActivity.this,
						"Approving...", null, true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
						.show();
			}

			@Override
			protected String doInBackground(Bitmap... params) {

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("Oid", Oid);

				String result = rh.sendPostRequest(Config.APPROVR_ORDER_URL,
						data);

				return result;
			}
		}

		approveOrder ao = new approveOrder();
		ao.execute();

	}

	private void call() {
		// TODO Auto-generated method stub
		
		
		Intent refr = new Intent(OrdersActivity.this, OrdersActivity.class);
		startActivity(refr);
		
		try {
			String uri = "tel:" + PhoneNumber;
			Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

			startActivity(dialIntent);
		} catch (Exception e) {
			Toast.makeText(OrdersActivity.this, "Cant open dialpad...",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		// Toast.makeText(getApplicationContext(), Username,
		// Toast.LENGTH_LONG).show();

	}

	private void message() {
		// TODO Auto-generated method stub
		
		Intent refr = new Intent(OrdersActivity.this, OrdersActivity.class);
		startActivity(refr);
		
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);         
		sendIntent.setData(Uri.parse("sms:" + PhoneNumber));
		sendIntent.putExtra("sms_body", "Hi " + Username + ", Your order is due. Please wait."); 
		startActivity(sendIntent);

	}
}
