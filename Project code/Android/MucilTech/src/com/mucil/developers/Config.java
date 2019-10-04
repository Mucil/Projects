package com.mucil.developers;

import com.mucil.developers.ProductsActivity.JsonTask;

public class Config {



    //Address of our scripts 
	public static final String URL = "http://192.168.43.206/muciltech/";
	//public static final String URL = "http://192.168.1.5/muciltech/";
    public static final String URL_GET_BRANCES = URL + "branches.php";
    public static final String PRODUCTS_URL = URL + "getAllProducts.php";
    public static final String ADMIN_LOGIN =  URL + "login.php";
    public static final String ADD_BRANCH_URL = URL + "addBranch.php";
    public static final String CART_URL =  URL + "buying.php";
    public static final String NEW_URL =  URL + "getNewProducts.php";
    public static final String OFFERS_URL =  URL + "getOffers.php";
    public static final String GET_PRODUCTS = URL + "getAllProducts.php";
    public static final String GET_NON_OFFERS = URL + "getNonOffers.php";
    public static final String OFFERS_UPLOAD = URL + "offerUpload.php";
    public static final String REMOVE_OFFER_URL = URL + "removeOffer.php";
    public static final String GET_OFFERS = URL + "getOffers.php";
    public static final String GET_ORDERS_URL = URL + "getOrders.php";
	public static final String APPROVR_ORDER_URL = URL + "approveOrder.php";
	public static final String UPLOAD_URL = URL + "upload.php";
	static final String REGISTER_URL = URL + "register.php";
	public static final String LOAD_URL = URL + "querryUser.php";
	public static final String RESET_URL = URL + "reset.php";
	public static final String QUERYY_BRANCH_URL = URL + "querryBranch.php";
	public static final String REVIEW_BRANCH_URL = URL + "reviewBranch.php";
	public static final String BRANCHES_URL = URL + "branches.php";
	public static final String REVIEW_URL = URL + "review.php";
	public static final String GET_SALES_URL = URL + "getSales.php";
	public static final String TRANSACTION_URL = URL + "transaction.php";
	public static final String BALANCE_URL = URL + "balance.php";
	public static final String INPUT_OUTPUT_URL = URL + "getInputOutput.php";
	public static final String COM_NET_URL = URL + "getComNett.php";
	public static final String COMP_SYSTEM_URL = URL + "getCompSystem.php";
	public static final String STORAGE_URL = URL + "getStorage.php";
	public static final String PROCESSING_URL = URL + "getProcessingCPU.php";
	public static final String MISCELLANEOUS_URL = URL + "getMiscellaneous.php";
    
    
    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_CONTACTS = "contacts";
    
   
	
	
}

