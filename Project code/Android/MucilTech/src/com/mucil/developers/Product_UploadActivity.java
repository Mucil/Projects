package com.mucil.developers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Product_UploadActivity extends Activity implements OnClickListener {
	private Bitmap bitmap;
	private Uri filePath;

	private String[] category = { "Input/Output", "Comp-system",
			"Com-network", "Storage", "Processing/CPU", "Miscellaneous" };
	
    public static final String UPLOAD_IMAGE = "image";
    public static final String UPLOAD_NAME = "name";
    public static final String UPLOAD_PRICE = "price";
    public static final String UPLOAD_DESCRIPTION = "description";
    public static final String UPLOAD_CATEGORY = "category";
 
 
    private int PICK_IMAGE_REQUEST = 1;
 
    private Spinner categorySpinner;
    private Button upload;
    private EditText pname,pprice,pdescription;
    private ImageView image;
    
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_upload);
		
		categorySpinner = (Spinner)findViewById(R.id.sp_pcategory);
		upload = (Button) findViewById(R.id.pupload);
		pname = (EditText) findViewById(R.id.et_pname);
		pprice = (EditText) findViewById(R.id.et_pprice);
		pdescription = (EditText) findViewById(R.id.et_pdescrption);
		image = (ImageView) findViewById(R.id.pimage);
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this.getApplicationContext(), android.R.layout.simple_spinner_item,
				category);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		categorySpinner.setAdapter(adapter);

	

		upload.setOnClickListener(this);
		image.setOnClickListener(this);
		

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.pimage:
			Intent galleryIntent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(galleryIntent,PICK_IMAGE_REQUEST);
			break;
		case R.id.pupload:
			validate();
			break;
	}
		
	}
	private void validate() {
		// TODO Auto-generated method stub
        String name = pname.getText().toString();
        String price = pprice.getText().toString();
        String description = pdescription.getText().toString();
		if (name.contentEquals("") || price.contentEquals("") || description.contentEquals("")) {
			if (name.contentEquals("")) {
				pname.setHint("Product name empty !");
				pname.setHintTextColor(Color.RED);
			}

			if (price.contentEquals("")) {
				pprice.setHint("Product price empty !");
				pprice.setHintTextColor(Color.RED);
			}
			if (description.contentEquals("")) {
				pdescription.setHint("Product description empty !");
				pdescription.setHintTextColor(Color.RED);
			}
			
		} 
		else
		{
			uploadProduct();	
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			 
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //image.setImageBitmap(bitmap);
                image.setImageBitmap(Bitmap.createScaledBitmap(bitmap,250,150,false));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}
	
	 public String getStringImage(Bitmap bmp){
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	        byte[] imageBytes = baos.toByteArray();
	        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
	        return encodedImage;
	        
	        /*
	        Bitmap original = BitmapFactory.decodeStream(getAssets().open("imagg1.jpg"));
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        original.compress(Bitmap.CompressFormat.JPEG, 100, out);
	        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

	        and for png images

	        Bitmap original = BitmapFactory.decodeStream(getAssets().open("imagg1.png"));
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        original.compress(Bitmap.CompressFormat.PNG, 100, out);
	        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
	        */
	    }
	 private void uploadProduct(){
	        class uploadProduct extends AsyncTask<Bitmap,Void,String>{
	 
	            ProgressDialog loading;
	            RequestHandler rh = new RequestHandler();
	 
	            @Override
	            protected void onPreExecute() {
	                super.onPreExecute();
	                loading = ProgressDialog.show(Product_UploadActivity.this, "Uploading...", null,true,true);
	            }
	 
	            @Override
	            protected void onPostExecute(String s) {
	                super.onPostExecute(s);
	                loading.dismiss();
	                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
	            }
	 
	            @Override
	            protected String doInBackground(Bitmap... params) {
	                Bitmap bitmap = params[0];
	                String uploadImage = getStringImage(bitmap);
	                String name = pname.getText().toString();
	                String price = pprice.getText().toString();
	                String description = pdescription.getText().toString();
	                int row = categorySpinner.getSelectedItemPosition();
	        		String Category = category[row];
	                HashMap<String,String> data = new HashMap<String, String>();
	 
	                data.put(UPLOAD_IMAGE, uploadImage);
	                data.put(UPLOAD_NAME, name);
	                data.put(UPLOAD_PRICE, price);
	                data.put(UPLOAD_DESCRIPTION, description);
	                data.put(UPLOAD_CATEGORY, Category);
	                String result = rh.sendPostRequest(Config.UPLOAD_URL,data);
	 
	                return result;
	            }
	        }
	 
	        uploadProduct ui = new uploadProduct();
	        ui.execute(bitmap);
	    }
	

}