<?php	
	require_once('dbConnect.php');
	
	$Names = $_POST['names'];
	$Identification = $_POST['identification'];
	$Pnone = $_POST['phone'];
	$Address = $_POST['address'];
	$Region = $_POST['region']; 
	
	$sql = "SELECT * FROM  customer WHERE identification = '$Identification' ";
	$res = mysqli_query($con,$sql);	
	$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'exist';
}
else{
        $sql = "INSERT INTO customer(names, identification, region, phone_no, address, amount)VALUES ('$Names','$Identification','$Region','$Pnone','$Address', '0')";
        $res = mysqli_query($con,$sql);
        if($res)
		{
		echo 'success';
		}
		else
		{
		echo 'error';
		}
   }
		
	mysqli_close($con);
 
?>
	     
	     
	
	 
