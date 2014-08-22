<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

 

// check for required fields

if (isset($_POST['phone']) && isset($_POST['degree']) && isset($_POST['about']) isset($_POST['name'])) {
 
    $phone = $_POST['phone'];
    $degree = $_POST['password'];
	$name = $_POST['name'];
	$about = $_POST['about'];
	// include db connect class
   require_once "database_helper.php";
   $db = new DatabaseHelper();

	// check if row inserted or not
	$sql = "UPDATE users SET name = ?, about = ?, degree = ? WHERE phone = ?";
   $stmt = $db->prepareStatement($sql);
   $param_types = array('s','s','s','s');
   $params = array($name,$about,$degree,$phone);
   $db->bindArray($stmt,$param_types,$params);
   $db->executeStatement($stmt);
   $result = $db->getAffectedRows($stmt);
	     
    // check if row inserted or not
    if ($result) {
	  // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "updated";
		$response["phone"]=$phone;
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
