<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','muciltech');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
	
	if (!empty($_POST)) {
	   
	    if (empty($_POST['amount'])) {
	       
	        echo "Please Enter Amount";
	     
	    }
	    $Amount = $_POST['amount'];
        $Transaction_id = uniqueID();
		$sql = "INSERT INTO deposit(amount,transaction_id)VALUES ('$Amount','$Transaction_id')";
        $res = mysqli_query($con,$sql);
		if ($res)
{
    echo $Transaction_id;
}
else
{
	echo "Failed";
}

 
        mysqli_close($con);
	  
	     
	     
	} else {
	?>
	    <h1>Transaction</h1>
	    <form action="deposit.php" method="post" enctype="multipart/form-data">
	        Amount in Ksh<br />
	        <input type="text" name="amount" value="" />
	        <br /><br />
	       
		<input type="submit" value="Deposit" />
	 </form>
	 
			
	    <?php
	}
	 //
	 function uniqueID() {

$unique_ref_length = 8;  
  
// A true/false variable that lets us know if we've  
// found a unique reference number or not  
$unique_ref_found = false;  
  
// Define possible characters.  
// Notice how characters that may be confused such  
// as the letter 'O' and the number zero don't exist  
$possible_chars = "23456789BCDFGHJKMNPQRSTVWXYZ";  
  
// Until we find a unique reference, keep generating new ones  
while (!$unique_ref_found) {  
  
    // Start with a blank reference number  
    $unique_ref = "";  
      
    // Set up a counter to keep track of how many characters have   
    // currently been added  
    $i = 0;  
      
    // Add random characters from $possible_chars to $unique_ref   
    // until $unique_ref_length is reached  
    while ($i < $unique_ref_length) {  
      
        // Pick a random character from the $possible_chars list  
        $char = substr($possible_chars, mt_rand(0, strlen($possible_chars)-1), 1);  
          
        $unique_ref .= $char;  
          
        $i++;  
      
    }  
        return $unique_ref;
        $unique_ref_found = true;  
      
    } 
} 	
	 //
	?>