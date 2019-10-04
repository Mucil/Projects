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
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OffersUpload extends Activity implements OnItemClickListener {
	ListView lvProducts;
	EditText inputSearch;
	
	String OFFERPRICE, ID;

	ArrayList<Products> searchResults;
	ArrayList<Products> productsList;
	ProductAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offers_upload);

		inputSearch = (EditText) findViewById(R.id.etSearch);

		lvProducts = (ListView) findViewById(R.id.lvOffers);
		lvProducts.setOnItemClickListener(this);

		new JsonTask()
				.execute(Config.GET_NON_OFFERS);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		TextView oname = (TextView) arg1.findViewById(R.id.ouName);
		TextView oprice = (TextView) arg1.findViewById(R.id.ouP);
		TextView oid = (TextView) arg1.findViewById(R.id.ouId);

		final String name = oname.getText().toString();
		final String price = oprice.getText().toString();
		final String id = oid.getText().toString();

		offerDialog(name, price, id);

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
			adapter = new ProductAdapter(OffersUpload.this,
					R.layout.offers_upload, result);
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

			id = (TextView) convertView.findViewById(R.id.ouId);
			name = (TextView) convertView.findViewById(R.id.ouName);
			price = (TextView) convertView.findViewById(R.id.ouP);
			description = (TextView) convertView.findViewById(R.id.ouDescrptn);

			id.setText(productsList.get(position).getId());
			name.setText(productsList.get(position).getName());
			price.setText(productsList.get(position).getPrice());
			description.setText(productsList.get(position).getDescription());

			return convertView;
		}

	}

	//
	private void offerDialog(String Name, final String Price, String Id) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(OffersUpload.this);
		View promptView = layoutInflater.inflate(R.layout.offer_upload, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				OffersUpload.this);

		alertDialogBuilder.setView(promptView);
		final TextView name = (TextView) promptView
				.findViewById(R.id.offerName);
		final TextView iprice = (TextView) promptView
				.findViewById(R.id.offerIPrice);
		final TextView id = (TextView) promptView
				.findViewById(R.id.offerID);
		final EditText cprice = (EditText) promptView
				.findViewById(R.id.offerCPrice);

		name.setText(Name);
		iprice.setText("Ksh " + Price);
		id.setText(Id);
		ID = Id;
		// setup a dialog window
		alertDialogBuilder
				.setTitle("Offer upload")
				.setCancelable(false)
				.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						})
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
						OFFERPRICE = cprice.getText().toString();
						
						int offerP = Integer.valueOf(OFFERPRICE);
						int initialP = Integer.valueOf(Price);
						if(offerP>=initialP)
						{
						Toast.makeText(getApplicationContext(), "Offer price must be less than initial price", Toast.LENGTH_LONG).show();	
						}
						
						else
						{
							offerUpload(ID,OFFERPRICE);	
							new JsonTask().execute(Config.GET_NON_OFFERS);
						}
						

					}

					

				});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();

	}
	//
	private void offerUpload(String iD, String offerPrice) {
		// TODO Auto-generated method stub
		class offerUpload extends AsyncTask<String, Void, String>{
			 
            private Dialog loadingDialog;
 
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(OffersUpload.this, "Please wait", "sending request...");
            }
 
            @Override
            protected String doInBackground(String... params) {
                String id = params[0];
                String offerprice = params[1];

 
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("oid", id));
                nameValuePairs.add(new BasicNameValuePair("oprice", offerprice));
              
                String result = null;
 
                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(Config.OFFERS_UPLOAD);
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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }
        }
 
		offerUpload ou = new offerUpload();
		ou.execute(ID,OFFERPRICE);
		
	}

}
