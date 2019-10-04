package com.mucil.developers;

import java.util.ArrayList;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {
	private Context context;
	 //list fields to be displayed
	 private ArrayList<String> txtName;
	 private ArrayList<String>txtPrice;
	 private ArrayList<String>txtQuantity;
	 private ArrayList<String>txtTotal;
	 
	 CartAdapter(Context c, ArrayList<String> txtName, ArrayList<String>txtPrice,ArrayList<String>txtQuantity,ArrayList<String>txtTotal) {
		this.context = c;
		//transfer content from database to temporary memory
		this.txtName = txtName;
		this.txtPrice = txtPrice;
		this.txtQuantity = txtQuantity;
		this.txtTotal= txtTotal;
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return txtName.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder holder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.cart_list, null);
			holder = new Holder();
			
			//link to TextView
			holder.txtName = (TextView) child.findViewById(R.id.cartProductName);
			holder.txtPrice = (TextView) child.findViewById(R.id.cartProductPrice);
			holder.txtQuantity = (TextView) child.findViewById(R.id.cartProductQuantity);
			holder.txtTotal = (TextView) child.findViewById(R.id.cartProductTotal);
		
			child.setTag(holder);
		} else {
			holder = (Holder) child.getTag();
		}
		//transfer to TextView in screen
		holder.txtName.setText(txtName.get(pos));
		holder.txtPrice.setText(txtPrice.get(pos));
		holder.txtQuantity.setText(txtQuantity.get(pos));
		holder.txtTotal.setText(txtTotal.get(pos));
		
		

		return child;
	}

	public class Holder {
		TextView txtName;
		TextView txtPrice;
		TextView txtQuantity;
		TextView txtTotal;
	}

	


}

