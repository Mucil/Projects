package com.mucil.developers;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity implements OnClickListener{
	ImageView plus,minus,Image;
	TextView Quantity, Name, Price, Description,Total;
	String name, price, description,quantity,total;
	Bitmap bitmap;
	Button addToCart;
	
	private DatabaseHelper entry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        entry=new DatabaseHelper(this);
        
        plus = (ImageView) findViewById(R.id.cart_plus_img);
        minus = (ImageView) findViewById(R.id.cart_minus_img);
        Image = (ImageView) findViewById(R.id.pimage);
        Quantity = (TextView) findViewById(R.id.cart_product_quantity_tv);
        Name = (TextView) findViewById(R.id.cart_product_name_tv);
        Price = (TextView) findViewById(R.id.cart_product_price_tv);
        Description = (TextView) findViewById(R.id.cart_product_description_tv);
        Total = (TextView) findViewById(R.id.cart_product_total_tv);
        
        

        addToCart = (Button)findViewById(R.id.addToCart);
       
        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("image");
        bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        name = i.getStringExtra("name");
        price = i.getStringExtra("price");
        description = i.getStringExtra("desription");
        
        Image.setImageBitmap(bitmap);
        Name.setText(name);
        Price.setText(price);
        Description.setText(description);
        
        Total.setText(price);
        
        
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        addToCart.setOnClickListener(this);
        

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.cart_plus_img:
			Increment();
			break;
		case R.id.cart_minus_img:
			Decrement();
			break;
		case R.id.addToCart:
			try{
				quantity = Quantity.getText().toString();
				total = Total.getText().toString();
				
		        entry.insertDetails(name, price, quantity, total);			
		        Toast.makeText(getApplicationContext(), "Successfully added to cart", Toast.LENGTH_LONG).show();
			    entry.close();
			    Details.this.finish();
			}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "Error saving record", Toast.LENGTH_LONG).show();
			}
			break;
	}
	

}

	private void Decrement() {
		// TODO Auto-generated method stub
		String unit_price_string = Price.getText().toString();
		String present_value_string = Quantity.getText().toString();
		
		int unit_price_int = Integer.parseInt(unit_price_string);
        int present_value_int = Integer.parseInt(present_value_string);
        if(present_value_int > 1 )
        {  
            present_value_int--;
            
            int total = unit_price_int*present_value_int;

            Quantity.setText(String.valueOf(present_value_int));
            Total.setText(String.valueOf(total));
        }
        
		
	}

	private void Increment() {
		// TODO Auto-generated method stub
		String unit_price_string = Price.getText().toString();
		String present_value_string = Quantity.getText().toString();
		
		int unit_price_int = Integer.parseInt(unit_price_string);
        int present_value_int = Integer.parseInt(present_value_string);
        
        present_value_int++;
        
        int total = unit_price_int*present_value_int;

        Quantity.setText(String.valueOf(present_value_int));
        Total.setText(String.valueOf(total));
		
	}
}
