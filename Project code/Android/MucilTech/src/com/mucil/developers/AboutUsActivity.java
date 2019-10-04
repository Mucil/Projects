package com.mucil.developers;


import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsActivity extends Fragment{

	private TextView tv_title;
	private WebView webView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.activityaboutus, container, false);

		tv_title =(TextView)v.findViewById(R.id.tv_title);
		tv_title.setText("About MucilTech");
		
		String htmlAsString = getString(R.string.html2);

	    webView = (WebView) v.findViewById(R.id.webView1);
	    webView.setBackgroundColor(Color.TRANSPARENT);
	    webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);
	
		
		return v;
	}

	
}
