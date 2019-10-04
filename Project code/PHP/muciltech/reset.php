<?php
require_once('dbConnect.php');	
	if (!empty($_POST)) {
	
	    $identification = $_POST['identification'];
	    $names = $_POST['names'];
        $address = $_POST['address'];
		$phone = $_POST['phone'];
		$location = $_POST['location'];
		
		$sql = "UPDATE customer SET names = '$names',address = '$address',phone_no = '$phone',region = '$location' WHERE identification = '$identification' ";
        $res = mysqli_query($con,$sql);
		if ($res)
{
    echo "success";
}
else
{
	echo "error";
}

 
        mysqli_close($con);
	  
	     
	     
	}
	 
	?>