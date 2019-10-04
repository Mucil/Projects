<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    $bid = $_POST['bid'];
		
		$sql = "SELECT * FROM  branches WHERE name = '$bid'";
        $res = mysqli_query($con,$sql);
	

	   $result = array();
	
	   while($row = mysqli_fetch_array($res)){
		array_push($result,array(
		"bid"=>$row['id'],
		"bname"=>$row['name'],
		"blocation"=>$row['location'],
		"baddress"=>$row['address'],
		"bcontact"=>$row['contacts']
			));
	}
	echo json_encode(array("result"=>$result));
	

       mysqli_close($con);
	  
	     
	     
	} 
	 
	?>
	
	