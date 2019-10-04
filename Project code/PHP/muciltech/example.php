<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','example');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$username = $_POST['username'];

$sql = "SELECT * FROM  admin WHERE username = '$username'";
 
$res = mysqli_query($con,$sql);

 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>
