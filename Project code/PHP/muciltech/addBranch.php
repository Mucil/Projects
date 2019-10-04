<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    $pname = $_POST['pname'];
        $plocation = $_POST['plocation'];
		$paddress = $_POST['paddress'];
        $pcontact = $_POST['pcontact'];
		
		$sql = "INSERT INTO branches(name,location,address,contacts)VALUES ('$pname','$plocation','$paddress','$pcontact')";
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