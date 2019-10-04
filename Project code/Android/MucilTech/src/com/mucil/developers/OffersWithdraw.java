package com.mucil.developers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class OffersWithdraw extends Activity implements OnItemClickListener {

	ListView lvProducts;
	EditText inputSearch;

	ArrayList<Products> searchResults;
	ArrayList<Products> productsList;
	ProductAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offers_withdraw);

		inputSearch = (EditText) findViewById(R.id.etSearchOffer);

		lvProducts = (ListView) findViewById(R.id.lvOffersWithdraw);
		lvProducts.setOnItemClickListener(this);

		new JsonTask().execute(Config.GET_OFFERS);
	}

	public class JsonTask extends AsyncTask<String, String, List<Products>> {

		@Override
		protected List<Products> doInBackground(String... params) {

			// TODO Auto-generated method stub
			HttpURLConnection connection = null;
			InputStream stream;
			BufferedReader reader = null;
			StringBuffer buffer;
			try {
				URL url = new URL(params[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();
				stream = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(stream));
				buffer = new StringBuffer();

				String line = "";
				while ((line = reader.readLine()) != null) {
					buffer.append(line);

				}
				String finalJson = buffer.toString();
				JSONObject parentObject = new JSONObject(finalJson);
				JSONArray parentArray = parentObject.getJSONArray("result");

				productsList = new ArrayList<Products>();
				for (int i = 0; i < parentArray.length(); i++) {

					JSONObject finalObject = parentArray.getJSONObject(i);
					Products products = new Products();

					products.setId(finalObject.getString("id"));
					products.setName(finalObject.getString("name"));
					products.setPrice(finalObject.getString("price"));
					products.setDescription(finalObject
							.getString("description"));
					products.setOffer_price(finalObject
							.getString("offer_price"));

					// adding the final object in the list
					productsList.add(products);

				}
				searchResults = new ArrayList<Products>(productsList);
				return searchResults;

			} catch (MalformedURLException e) {
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null)
					connection.disconnect();

				try {
					if (reader != null)
						reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Products> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// need to set data to the list
			adapter = new ProductAdapter(OffersWithdraw.this,
					R.layout.offer_withdraw_list, result);
			lvProducts.setAdapter(adapter);
			inputSearch.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					// get the text in the EditText
					String searchString = inputSearch.getText().toString();
					int textLength = searchString.length();

					// clear the initial data set
					searchResults.clear();

					for (int i = 0; i < productsList.size(); i++) {
						String productNAME = productsList.get(i).getName();

						if (textLength <= productNAME.length()) {
							// compare the String in EditText with Names in the
							// ArrayList
							if (searchString.equalsIgnoreCase(productNAME
									.substring(0, textLength)))
								searchResults.add(productsList.get(i));
						}
					}

					adapter.notifyDataSetChanged();

				}

			});
		}

	}

	public class ProductAdapter extends ArrayAdapter {

		private List<Products> productsList;
		private int resource;
		private LayoutInflater inflater;

		public ProductAdapter(Context context, int resource,
				List<Products> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			productsList = objects;
			this.resource = resource;
			inflater = LayoutInflater.from(getContext());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = inflater.inflate(resource, null);
			}

			TextView id;
			TextView name;
			TextView price;
			TextView description;

			id = (TextView) convertView.findViewById(R.id.owId);
			name = (TextView) convertView.findViewById(R.id.owName);
			price = (TextView) convertView.findViewById(R.id.owP);
			description = (TextView) convertView.findViewById(R.id.owDescrptn);

			id.setText(productsList.get(position).getId());
			name.setText(productsList.get(position).getName());
			price.setText(productsList.get(position).getOffer_price());
			description.setText(productsList.get(position).getDescription());

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		TextView id;
		TextView name;

		id = (TextView) arg1.findViewById(R.id.owId);
		name = (TextView) arg1.findViewById(R.id.owName);

		final String rid;
		final String rname;
		rid = id.getText().toString();
		rname = name.getText().toString();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				OffersWithdraw.this);

		alertDialogBuilder
				.setTitle("Remove offer")
				.setMessage("[" + rid + "#" + rname + "]")
				.setNeutralButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						})
				.setPositiveButton("remove",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								offerRemove(rid);
								new JsonTask().execute(Config.GET_OFFERS);

							}

						}).show();

	}

	private void offerRemove(String rid) {
		// TODO Auto-generated method stub
		class offerRemove extends AsyncTask<String, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(OffersWithdraw.this,
						"Removing offer", "sending request", true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
						.show();

			}

			@Override
			protected String doInBackground(String... params) {

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("rid", params[0]);

				String result = rh.sendPostRequest(Config.REMOVE_OFFER_URL,
						data);

				return result;
			}

		}

		offerRemove or = new offerRemove();
		or.execute(rid);


	}

}
