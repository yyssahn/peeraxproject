<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['tag']) && isset($_POST['phonenumber'])) {
	$phonenumber = $_POST['phonenumber'];
	
 
   // Connect to db
   require_once "database_helper.php";
   $db = new DatabaseHelper();
	
	
	
	// check if row inserted or not
   $sql = "SELECT subject, criteria, price FROM tutorsubject WHERE phonenumber = ?";
   $stmt = $db->prepareStatement($sql);
   
   $db->bindParameter($stmt,'i',$phonenumber);
   $db->executeStatement($stmt);
	
   
   $result = $db->getResult($stmt);
	// check if row inserted or not
   
   if ($result) {   
	// successfully inserted into database
      $response["success"] = 1;
      $response["message"] = "updated";
	 
	  $response["subjects"] = array();
	 
	 foreach ($result as &$row) {
        // temp user array
        $subject = array();
		$subject["criteria"]=$row["criteria"];
		$subject["subject"]=$row["subject"];
        $subject["price"] = $row["price"];
        // push single product into final response array
        array_push($response["subjects"], $subject);
  
  }
	
	
     
      // echoing JSON response
      echo json_encode($response);
	} 
	
	
	else {	
      $response["success"] = 0;
      $response["message"] = "does not exist";

        // echoing JSON response
        echo json_encode($response);
		
		
 } 

  
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
