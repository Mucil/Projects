<?php 
	//Importing Database Script 
	require_once('dbConnect.php');
	
	//Creating sql query
	$sql = "SELECT * FROM orders";
	
	//getting result 
	$r = mysqli_query($con,$sql);
	
	//creating a blank array 
	$result = array();
	
	//looping through all the records fetched
	while($row = mysqli_fetch_array($r)){

		array_push($result,array(
		
		    "order_user"=>$row['names'],
			"order_phone"=>$row['phone_no'],
			"oder_id"=>$row['id'],
			"oder_identification"=>$row['identification'],
			"oder_receiptcontents"=>$row['receiptcontents'],
			"oder_receiptquantities"=>$row['receiptquantities'],
			"oder_grandtotal"=>$row['grandtotal']
		));
	}
	
	//Displaying the array in json format 
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
	
?>