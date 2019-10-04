package com.mucil.developers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AuthorityActivity extends Activity implements OnClickListener {
	Button addproduct, reviewproduct, addbranch, reviewbranch, orders, sales, offers, offerwithdraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authority);

		addproduct = (Button) findViewById(R.id.addProduct);
		reviewproduct = (Button) findViewById(R.id.reviewProduct);
		addbranch = (Button) findViewById(R.id.addBranch);
		reviewbranch = (Button) findViewById(R.id.reviewBranch);
		orders = (Button) findViewById(R.id.orders);
		sales = (Button) findViewById(R.id.sales); 
		offers = (Button) findViewById(R.id.offers);
		offerwithdraw = (Button) findViewById(R.id.offerWithdraw);


		reviewproduct.setOnClickListener(this);
		addproduct.setOnClickListener(this);
		addbranch.setOnClickListener(this);
		reviewbranch.setOnClickListener(this);
		orders.setOnClickListener(this);
		sales.setOnClickListener(this);
		offers.setOnClickListener(this);
		offerwithdraw.setOnClickListener(this);



	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.addProduct:
			Intent view = new Intent(AuthorityActivity.this,
					Product_UploadActivity.class);
			startActivity(view);
			break;
		case R.id.reviewProduct:
			Intent review = new Intent(AuthorityActivity.this,
					Product_ReviewActivity.class);
			startActivity(review);
			break;
		case R.id.addBranch:
			Intent branch = new Intent(AuthorityActivity.this,
					Branch_UploadActivity.class);
			startActivity(branch);
			break;
		case R.id.reviewBranch:
			Intent branchReview = new Intent(AuthorityActivity.this,
					Review_Branch.class);
			startActivity(branchReview);
			break;
		case R.id.orders:
			Intent orders = new Intent(AuthorityActivity.this,
					OrdersActivity.class);
			startActivity(orders);
			break;
		case R.id.sales:
			Intent sales = new Intent(AuthorityActivity.this,
					SalesActivity.class);
			startActivity(sales);
			break;
			
		case R.id.offers:
			Intent offers = new Intent(AuthorityActivity.this,
					OffersUpload.class);
			startActivity(offers);
			break;
			
		case R.id.offerWithdraw:
			Intent offerwithdraw = new Intent(AuthorityActivity.this,
					OffersWithdraw.class);
			startActivity(offerwithdraw);
			break;
			
			
			
			
			

		}

	}
}

