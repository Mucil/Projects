<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    
	    $Oid = $_POST['Oid'];
			
		$sql = "SELECT * FROM orders WHERE id = '$Oid'";
		
        $res = mysqli_query($con,$sql);
        $check = mysqli_fetch_array($res);
 
     if(isset($check)){
     $Oide = $check['identification']; 
	 $Orc = $check['receiptcontents'];
     $Orq = $check['receiptquantities'];
	 $Ogt = $check['grandtotal'];
	 
	 $sql2 = "INSERT INTO sales(id,identification,receiptcontents,receiptquantities,grandtotal)VALUES ('$Oid','$Oide','$Orc','$Orq','$Ogt')";
     mysqli_query($con,$sql2);
	 
	 $sql3 = "DELETE FROM orders WHERE id = '$Oid'";
	 mysqli_query($con,$sql3);
	 
	 echo 'success';
  
     }
else{
echo 'failure';
}
		

 mysqli_close($con);
	  
	     
	     
	} 
	 
	?>