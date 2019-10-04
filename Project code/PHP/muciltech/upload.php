<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = $_POST['image'];
		$name = $_POST['name'];
		$price = $_POST['price'];
		$description = $_POST['description'];
		$category = $_POST['category'];
		
		require_once('dbConnect.php');
		
		$sql ="SELECT id FROM products ORDER BY id ASC";
		
		$res = mysqli_query($con,$sql);
		
		$id = 0;
		
		while($row = mysqli_fetch_array($res)){
				$id = $row['id'];
		}
		
		$path = "products/$id.png";
		
		$actualpath = "http://192.168.43.135/muciltech/$path";
		
		$sql = "INSERT INTO products (image,name,description,price,category) VALUES ('$actualpath','$name','$description','$price','$category' )";
		
		if(mysqli_query($con,$sql)){
			file_put_contents($path,base64_decode($image));
			echo "Successfully Uploaded";
		}
		
		mysqli_close($con);
	}else{
		echo "Error";
	}