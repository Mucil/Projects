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

import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewActivity extends Fragment implements OnItemClickListener{
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	
	private TextView tv_title;
	ListView lvProducts;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.activity_new, container, false);
		
		DisplayImageOptions defaultOptions= new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);
		lvProducts=(ListView)v.findViewById(R.id.listViewNewProducts);
		lvProducts.setOnItemClickListener(this);



		tv_title =(TextView)v.findViewById(R.id.tv_title);
		tv_title.setText("New Products");
		
		connection();
		
		return v;
		
		
	}
	
	private void connection() {
		// TODO Auto-generated method stub
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {
			new JsonTask().execute(Config.NEW_URL);
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
	
	public class JsonTask extends AsyncTask<String, String, List<Products>> {

		ProgressDialog loading;
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getActivity(),"Fetching new products","Wait...",false,false);
        }

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
				String finalJson=buffer.toString();
				JSONObject parentObject=new JSONObject(finalJson);
				JSONArray parentArray=parentObject.getJSONArray("result");
				
				List<Products>productsList=new ArrayList<Products>(); 
				for(int i=0;i<parentArray.length();i++)
				{
					
					JSONObject finalObject=parentArray.getJSONObject(i);
					Products products=new Products();
					
					products.setImageUrl(finalObject.getString("url"));
					products.setName(finalObject.getString("name"));
					products.setPrice(finalObject.getString("price"));
					products.setDescription(finalObject.getString("description"));
					

					
					
					//adding the final object in the list
					productsList.add(products);			
					
				}
				
			
				return productsList;
				
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
			loading.dismiss();
			//need to set data to the list
			ProductAdapter adapter=new ProductAdapter(getActivity(),R.layout.new_list,result);
			lvProducts.setAdapter(adapter);
		}

	}
	
	
	public class ProductAdapter extends ArrayAdapter
	{

		private List<Products> productsList;
		private int resource;
		private LayoutInflater inflater;
		public ProductAdapter(Context context, int resource, List<Products>objects ) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			productsList=objects;
			this.resource=resource;
			inflater=LayoutInflater.from(getContext());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView==null)
			{
				convertView=inflater.inflate(resource, null); 
			}
			
			ImageView picture;
			TextView name;
			TextView price;
			TextView description;
			
			
			
			picture=(ImageView)convertView.findViewById(R.id.ProductImage);
			name=(TextView)convertView.findViewById(R.id.ProductName);
			price=(TextView)convertView.findViewById(R.id.ProductPrice);
			description=(TextView)convertView.findViewById(R.id.ProductDescription);
			
			//
			//load image here
					ImageLoader.getInstance().displayImage(productsList.get(position).getImageUrl(), picture, new ImageLoadingListener()
							{

						
								@Override
								public void onLoadingComplete(String arg0, View arg1,
										Bitmap arg2) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onLoadingCancelled(String arg0,
										View arg1) {
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ImageView Image = (ImageView) arg1.findViewById(R.id.ProductImage);
		TextView Name = (TextView) arg1.findViewById(R.id.ProductName);
		TextView Price = (TextView) arg1.findViewById(R.id.ProductPrice);
		TextView Description = (TextView) arg1
				.findViewById(R.id.ProductDescription);

		//Image.setDrawingCacheEnabled(true);
		//Image.buildDrawingCache();
		//Bitmap bitmap = Image.getDrawingCache();
		
		Drawable drawable=Image.getDrawable();
		Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		
		String name = Name.getText().toString();
		String price = Price.getText().toString();
		String description = Description.getText().toString();

		Intent details = new Intent(getActivity(), Details.class);
		details.putExtra("image", b);
		details.putExtra("name", name);
		details.putExtra("price", price);
		details.putExtra("description", description);

		startActivity(details);
	}
	
	
	
}