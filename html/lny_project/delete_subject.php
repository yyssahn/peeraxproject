<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['phonenumber'])) {
	$phonenumber = $_POST['phonenumber'];
	$price = $_POST['price'];
	$subject = $_POST['subject'];
	$criteria = $_POST['criteria'];
 
   // Connect to db
   require_once "database_helper.php";
   $db = new DatabaseHelper();
	
	
	
	// check if row inserted or not
   $sql = "Delete FROM tutorsubject WHERE phonenumber = ? AND criteria = ? AND subject= ? AND price = ?";
   $stmt = $db->prepareStatement($sql);
   
    $db->bindArray($stmt, array('i','s','s','i'), array($phonenumber,$criteria, $subject, $price));
	   $db->executeStatement($stmt);
	
     
	// successfully inserted into database
      $response["success"] = 1;
      $response["message"] = "deleted";
	 
	
	
     
      // echoing JSON response
      echo json_encode($response);
 

  
} 
else {
    // required field is missing

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
//}
?>
