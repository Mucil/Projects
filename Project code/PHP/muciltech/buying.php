<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	  
		$Pin = $_POST['pin'];
		$Receiptcontents = $_POST['receiptcontents'];
		$Receiptquantities = $_POST['receiptquantities'];
		$Grandtotal = $_POST['grandtotal'];
		
        $sql = "SELECT amount FROM  customer WHERE identification = '$Pin'";
        $res = mysqli_query($con,$sql);
        $check = mysqli_fetch_array($res);
 
     if(isset($check)){
     $AMOUNT = $check['amount']; 
	 if($AMOUNT>$Grandtotal)
	 {
	 $Balance = $AMOUNT- $Grandtotal;
	 
	 $sql4 = "SELECT * FROM  customer WHERE identification = '$Pin'";
	 $res1 = mysqli_query($con,$sql4);
     $r = mysqli_fetch_array($res1);
	 $Names= $r['names'];
	 $Phone= $r['phone_no'];
	 
	 
	 $sql2 = "UPDATE customer SET amount = '$Balance' WHERE identification = '$Pin' ";
	 $sql3 = "INSERT INTO orders(identification,names,phone_no,receiptcontents,receiptquantities,grandtotal)VALUES ('$Pin','$Names','$Phone','$Receiptcontents','$Receiptquantities','$Grandtotal')";
	 //$sql3 = "INSERT INTO orders(identification,receiptcontents,receiptquantities,grandtotal)VALUES ('$Pin','$Receiptcontents','$Receiptquantities','$Grandtotal')";
     
	 mysqli_query($con,$sql2);
	 mysqli_query($con,$sql3);
	 
	 echo "success";
	 
	 }
	 else
	 {
	 echo "insufficient";
	 }
	 

}
    else{
echo 'error';
}
		

 mysqli_close($con);
	  
	     
	     
	} 
	
	?>
	    