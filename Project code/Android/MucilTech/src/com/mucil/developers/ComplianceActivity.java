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

public class ComplianceActivity extends Fragment{

	private TextView tv_title;
	private WebView webView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.activity_compliance, container, false);

		tv_title =(TextView)v.findViewById(R.id.tv_title);
		tv_title.setText("Compliance");
		
		String htmlAsString = getString(R.string.html);

	    webView = (WebView) v.findViewById(R.id.webView);
	    webView.setBackgroundColor(Color.TRANSPARENT);
	    webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);
	
		
		return v;
	}

	
}
