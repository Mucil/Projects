package com.mucil.developers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mucil.developers.ProductsActivity.JsonTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Product_ReviewActivity extends Activity implements
		OnItemClickListener, OnItemSelectedListener {
	ListView lvProducts;
	EditText inputSearch;

	ArrayList<Products> searchResults;
	ArrayList<Products> productsList;
	ProductAdapter adapter;

	private String[] refresh = { "Refresh" , "Settings" };
	private Spinner Refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_product);
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				Product_ReviewActivity.this).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);

		inputSearch = (EditText) findViewById(R.id.editTextSearch);
		Refresh = (Spinner) findViewById(R.id.refresh);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Product_ReviewActivity.this,
				android.R.layout.simple_spinner_item, refresh);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Refresh.setAdapter(adapter);
		Refresh.setOnItemSelectedListener(this);

		lvProducts = (ListView) findViewById(R.id.listViewProducts);
		lvProducts.setOnItemClickListener(this);

		new JsonTask().execute(Config.PRODUCTS_URL);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		ImageView Image = (ImageView) arg1.findViewById(R.id.ProductImage);
		TextView Name = (TextView) arg1.findViewById(R.id.ProductName);
		TextView Price = (TextView) arg1.findViewById(R.id.ProductPrice);

		Drawable drawable = Image.getDrawable();
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();

		String name = Name.getText().toString();
		String price = Price.getText().toString();

		Intent review = new Intent(Product_ReviewActivity.this, Review.class);
		review.putExtra("image", b);
		// review.putExtra("image", bitmap);
		review.putExtra("name", name);
		review.putExtra("price", price);

		startActivity(review);

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

					products.setImageUrl(finalObject.getString("url"));
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
			adapter = new ProductAdapter(Product_ReviewActivity.this,
					R.layout.products_list, result);
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
						// String productPRICE=productsList.get(i).getPrice();

						if (textLength <= productNAME.length()) {
							// compare the String in EditText with Names in the
							// ArrayList
							if (searchString.equalsIgnoreCase(productNAME
									.substring(0, textLength)))
								searchResults.add(productsList.get(i));
						}
					}

					adapter.notifyDataSetChanged();
					//

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

			ImageView picture;
			TextView name;
			TextView price;
			TextView description;

			picture = (ImageView) convertView.findViewById(R.id.ProductImage);
			name = (TextView) convertView.findViewById(R.id.ProductName);
			price = (TextView) convertView.findViewById(R.id.ProductPrice);
			description = (TextView) convertView
					.findViewById(R.id.ProductDescription);

			//
			// load image here
			ImageLoader.getInstance().displayImage(
					productsList.get(position).getImageUrl(), picture,
					new ImageLoadingListener() {

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
							// TODO Auto-generated method stub

						}

					});
			//

			name.setText(productsList.get(position).getName());
			price.setText(productsList.get(position).getPrice());
			description.setText(productsList.get(position).getDescription());

			return convertView;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		String REfresh = arg0.getItemAtPosition(arg2).toString();
		if (REfresh.contentEquals("Refresh")) {
			new JsonTask().execute(Config.PRODUCTS_URL);
			//Toast.makeText(getApplicationContext(), REfresh, Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
