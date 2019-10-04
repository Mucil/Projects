<?php
require_once('dbConnect.php');
 
$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * FROM  admin WHERE username = '$username' AND password = '$password' ";
 
$res = mysqli_query($con,$sql);
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>
