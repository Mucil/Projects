<?php
	require_once('dbConnect.php');
	
	$sql = "select * from products where offer = '1'";
	
	$res = mysqli_query($con,$sql);
	
	$result = array();
	
	while($row = mysqli_fetch_array($res)){
		array_push($result,array(
		"id"=>$row['id'],
		"offer_price"=>$row['offer_price'],
		"name"=>$row['name'],
		"price"=>$row['price'],
		"description"=>$row['description'],
		"url"=>$row['image']
			));
	}
	
	echo json_encode(array("result"=>$result));
	
	mysqli_close($con);
	?>

	
	
	