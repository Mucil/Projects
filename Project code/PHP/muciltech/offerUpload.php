<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    $Oid = $_POST['oid'];
		$Oprice = $_POST['oprice'];
		
        $sql = "UPDATE products SET offer_price = '$Oprice',offer = '1' WHERE id = '$Oid' ";
        $res = mysqli_query($con,$sql);

 
     if($res)
	 {

echo 'success';

}
else
{
echo 'failure';
}
		

 mysqli_close($con);
	  
	     
	     
	} 
	 
	?>