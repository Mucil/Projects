<?php
	require_once('dbConnect.php');
	
	$Rid = $_POST['rid'];
	
	$sql = "select * from products where id = '$Rid'";
	
	$res = mysqli_query($con,$sql);
	
	if($res)
	 {
$sql2 = "UPDATE products SET offer_price = '',offer = '' WHERE id = '$Rid' ";
mysqli_query($con,$sql2);
echo 'success';

}
else
{
echo 'failure';
}

	
	mysqli_close($con);
	?>

	
	
	