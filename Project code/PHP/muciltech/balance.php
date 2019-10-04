<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    $Pin = $_POST['pin'];
		
		$sql = "SELECT amount FROM  customer WHERE identification = '$Pin'";
        $res = mysqli_query($con,$sql);

		$check = mysqli_fetch_array($res);
 
     if(isset($check)){
     $Amount = $check['amount']; 
	 echo $Amount;

}else{
echo 'failure';
}
		

 mysqli_close($con);
	  
	     
	     
	} 
	 
	?>