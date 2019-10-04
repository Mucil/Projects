<?php
	
		if($_SERVER['REQUEST_METHOD']=='POST')
		{
		
		$name = $_POST['rname'];
		$price = $_POST['rprice'];
		
		
		require_once('dbConnect.php');
		
		$sql = "UPDATE products SET price = '$price' WHERE name = '$name' ";
		
		if(mysqli_query($con,$sql)){
			
			echo "Review successful";
		}
	else{
		echo "Error";
	}
	mysqli_close($con);
	}
	?>