<?php
require_once('dbConnect.php');
	
	if (!empty($_POST)) {
	   
	    if (empty($_POST['pin']) || empty($_POST['id'])) {
	       
	        echo "Please Enter identification(pin) and transaction id";
	     
	    }
		
	    $Pin = $_POST['pin'];
        $Id = $_POST['id'];
		
		$sql = "SELECT * FROM  deposit WHERE transaction_id = '$Id'";
        $res = mysqli_query($con,$sql);
		if ($res)
{
     $row = mysqli_fetch_array($res);
     $Amount = $row['amount'];
	 
	 $sql2 = "SELECT  amount FROM  customer WHERE identification = '$Pin'";
	 $res2 = mysqli_query($con,$sql2);
	if($res2)
	{
	 $row1 = mysqli_fetch_array($res2);
	 $Amount2 = $row1['amount'];
	 $Amount3 = $Amount2 + $Amount;
	 $sql3 = "UPDATE customer SET amount = '$Amount3' WHERE identification = '$Pin' ";
	 $res3 = mysqli_query($con,$sql3);
	 if ($res3)
     {
	 $sql4 = "DELETE from deposit WHERE transaction_id = '$Id' ";
	 $res4 = mysqli_query($con,$sql4);
     echo "success" ;
     }
    else
    {
  	echo "failure" ;
    }
	
	 }
	 else
	 {
	 echo "failure" ;
	 }
	 
	
}
else
{
	echo "failure";
}

 
        mysqli_close($con);
	  
	     
	     
	} else {
	?>
	    <h1>Transaction</h1>
	    <form action="transaction.php" method="post" enctype="multipart/form-data">
	        Identification/pin<br />
	        <input type="text" name="pin" value="" />
	        <br /><br />
			Transaction id<br />
	        <input type="text" name="id" value="" />
	        <br /><br />
	       
		<input type="submit" value="Transact" />
	 </form>
	 
			
	    <?php
	}
	 
	?>