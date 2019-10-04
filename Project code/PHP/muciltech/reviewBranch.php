<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    $bid = $_POST['bid'];
	    $bname = $_POST['bname'];
        $blocation = $_POST['blocation'];
		$baddress = $_POST['baddress'];
        $bcontact = $_POST['bcontact'];
		
		$sql = "UPDATE branches SET name = '$bname',location = '$blocation',address = '$baddress',contacts = '$bcontact' WHERE name = '$bid' ";
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