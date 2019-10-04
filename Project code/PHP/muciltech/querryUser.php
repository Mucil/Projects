<?php
	require_once('dbConnect.php');

	  
	    $cid = $_POST['cid'];
		
		$sql = "SELECT * FROM  customer WHERE identification = '$cid'";
        $res = mysqli_query($con,$sql);
	    
       $result = array();
	
	   while($row = mysqli_fetch_array($res)){
		array_push($result,array(
		"id"=>$row['id'],
		"names"=>$row['names'],
		"identification"=>$row['identification'],
		"address"=>$row['address'],
		"phone"=>$row['phone_no'],
		"region"=>$row['region']
		
			));
	}
	echo json_encode(array("result"=>$result));
	
	

       mysqli_close($con);

	 
	?>
	
	