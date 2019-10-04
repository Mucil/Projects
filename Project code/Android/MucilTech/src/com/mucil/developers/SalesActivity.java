package com.mucil.developers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SalesActivity extends Activity {

	private ListView listView;
	private String JSON_STRING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales);

		listView = (ListView) findViewById(R.id.listViewSales);
		getJSON();
	}

	private void getJSON() {
		// TODO Auto-generated method stub
		class GetJSON extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(SalesActivity.this,
						"Fetching...", null, false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				showSales();
			}

			@Override
			protected String doInBackground(Void... params) {
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.GET_SALES_URL);
				return s;
			}
		}
		GetJSON gj = new GetJSON();
		gj.execute();
	}

	private void showSales() {
		JSONObject jsonObject = null;
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			jsonObject = new JSONObject(JSON_STRING);
			JSONArray result = jsonObject.getJSONArray("result");

			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);
				String oder_id = jo.getString("oder_id");
				String oder_identification = jo
						.getString("oder_identification");
				String oder_grandtotal = jo.getString("oder_grandtotal");

				HashMap<String, String> sales = new HashMap<String, String>();
				sales.put("oder_id", oder_id);
				sales.put("oder_identification", oder_identification);
				sales.put("oder_grandtotal", oder_grandtotal);

				list.add(sales);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(SalesActivity.this, list,
				R.layout.sales, new String[] { "oder_id",
						"oder_identification", "oder_grandtotal" },
				new int[] { R.id.sale_id, R.id.sale_identification,
						R.id.sale_total });

		listView.setAdapter(adapter);
	}

}
