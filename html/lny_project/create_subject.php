<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['criteria']) && isset($_POST['subject']) && isset($_POST['price'])&& isset($_POST['phonenumber']) ) {
	$phonenumber = $_POST['phonenumber'];
   $criteria = $_POST['criteria'];
   $subject = $_POST['subject'];
	$price = $_POST['price'];
	
   // Connect to db
   require_once "database_helper.php";
   $db = new DatabaseHelper();

	// check if row inserted or not
   $sql = "INSERT INTO tutorsubject(phonenumber, criteria, subject, price) VALUES(?,?,?,?)";
   $stmt = $db->prepareStatement($sql);
   $param_types = array('s','s','s','i');
   $params = array($phonenumber,$criteria,$subject,$price);
   $db->bindArray($stmt, $param_types,$params);
   $db->executeStatement($stmt);
   $result = $db->getAffectedRows($stmt);

   // check if row inserted or not
   if ($result) {
	   // successfully inserted into database
      $response["success"] = 1;
      $response["message"] = "updated";
	   $response["email"]=$email;
     
      // echoing JSON response
      echo json_encode($response);
		
      $response["success"] = 0;
      $response["message"] = "does not exist";

        // echoing JSON response
        echo json_encode($response);
		
		
 } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

		// echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing

    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
//}
?>
