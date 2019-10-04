package com.mucil.developers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;



public class MainActivity extends FragmentActivity {

	public static final int FRAGMENT_DASHBOARD = 0;
	public static final int FRAGMENT_REGISTER = 1;
	public static final int FRAGMENT_WALLET = 2;
	public static final int FRAGMENT_AUTHORITY = 4;
	public static final int FRAGMENT_PRODUCTS = 3;
	public static final int FRAGMENT_BRANCHES = 5;
	public static final int FRAGMENT_CART = 6;
	public static final int FRAGMENT_OFFERS = 7;
	public static final int FRAGMENT_COMPLIANCE = 8;
	public static final int FRAGMENT_ABOUTUS = 9;
	public static final int FRAGMENT_NEW = 10;
	

	
	
	private SlidingPaneLayout spl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
	}

	private void init() {
		spl = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
	}

	public void setNewPage(Fragment fragment, int pageIndex) {
		if (spl.isOpen()) {
			spl.closePane();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_frame, fragment, "currentFragment")
				.commit();

	}

	public void onDashBoard(View v) {
		setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
	}

	public void onRegister(View v) {
		setNewPage(new RegisterActivity(), FRAGMENT_REGISTER);
	}

	public void onWallet(View v) {
		setNewPage(new WalletActivity(), FRAGMENT_WALLET);
	}
	public void onAuthority(View v) {
		setNewPage(new AdminActivity(), FRAGMENT_AUTHORITY);
	}
	public void onProducts(View v) {
		setNewPage(new ProductsActivity(), FRAGMENT_PRODUCTS);
	}
	public void onBranches(View v) {
		setNewPage(new BranchesActivity(), FRAGMENT_BRANCHES);
	}
	public void onOffers(View v) {
		setNewPage(new OffersActivity(), FRAGMENT_OFFERS);
	}
	public void onCompliance(View v) {
		setNewPage(new ComplianceActivity(), FRAGMENT_COMPLIANCE);
	}
	public void onAboutUs(View v) {
		setNewPage(new AboutUsActivity(), FRAGMENT_ABOUTUS);
	}
	public void onNew(View v) {
		setNewPage(new NewActivity(), FRAGMENT_NEW);
	}
	
	
    
	public void onSliding(View v) {
		if (spl.isOpen()) {
			spl.closePane();
		} else {
			spl.openPane();
		}
	}
	public void onParentCart(View v) {
		setNewPage(new CartActivity(), FRAGMENT_CART);
		
	}

}
