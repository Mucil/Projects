package com.mucil.developers;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Review extends Activity implements OnClickListener {

	ImageView Image;
	TextView Name;
	EditText Price;

	String name, price;
	Bitmap bitmap;
	Button Review;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);

		Image = (ImageView) findViewById(R.id.rimage);
		Name = (TextView) findViewById(R.id.tv_rname);
		Price = (EditText) findViewById(R.id.et_rprice);
		Review = (Button) findViewById(R.id.review);

		Intent i = getIntent();
		Bundle extras = getIntent().getExtras();
		byte[] b = extras.getByteArray("image");
		bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		name = i.getStringExtra("name");
		price = i.getStringExtra("price");

		Image.setImageBitmap(bitmap);
		Name.setText(name);
		Price.setText(price);

		Review.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.review:

			String price = Price.getText().toString();
			if(price.contentEquals(""))
			{
				Price.setHint("New price please ?");
				Price.setHintTextColor(Color.RED);
			}
			else
			{
				reviewProduct();
				Price.setHint("");
				Price.setHintTextColor(Color.BLACK);
			}
		
			
			break;
		}

	}

	private void reviewProduct() {
		class reviewProduct extends AsyncTask<Bitmap, Void, String> {

			ProgressDialog loading;
			RequestHandler rh = new RequestHandler();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(Review.this, "Reviewing",
						"please wait", true, true);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG)
						.show();
				Review.this.finish();
			}

			@Override
			protected String doInBackground(Bitmap... params) {

				String rname = Name.getText().toString();
				String rprice = Price.getText().toString();

				HashMap<String, String> data = new HashMap<String, String>();

				data.put("rname", rname);
				data.put("rprice", rprice);

				String result = rh.sendPostRequest(Config.REVIEW_URL, data);

				return result;
			}
		}

		reviewProduct rp = new reviewProduct();
		rp.execute();
	}

}
