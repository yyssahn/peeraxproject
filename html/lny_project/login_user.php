<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

 

// check for required fields

if (isset($_POST['phonenumber']) && isset($_POST['password']) ) {
 
   $phonenumber = $_POST['phonenumber'];
   $password = $_POST['password'];
 
    // connecting to db
   require_once "database_helper.php";
   $db = new DatabaseHelper();

	// check if row inserted or not
	$sql = "SELECT * FROM users WHERE phonenumber = ?";
   $stmt = $db->prepareStatement($sql);
   $db->bindParameter($stmt,'s',$phonenumber);
   $db->executeStatement($stmt);
   $result = $db->getResult($stmt);
	$no_of_rows = count($result);
   $user = $result[0];
	$usersalt = $user["salt"];    
	$hash = base64_encode(sha1($password.$usersalt, true).$usersalt);
	$hash2 = base64_encode(sha1("sdfjlsdf", "323232",true).$usersalt);
	if ($user["password"]==$hash){
	$check = true;
	}else{
	$check = false;
	}
    // check if row inserted or not
    if ($result) {
		if($no_of_rows > 0 && $check == true){
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "id and password exists";
        $response["name"]= $user["lastname"];
		$response["phonenumber"]= $user["phonenumber"];
		$response["about"]= $user["about"];
		$response["degree"]= $user["degree"];
		$response["seen"] = $user["seen"];
		$response["password"]=$_POST["password"];
        // echoing JSON response
        echo json_encode($response);
		}
		else{
		$response["success"] = 0;
        $response["message"] = "does not exist";
		$response["password"]=$user["password"];
		$response["hash"]=$hash;
 
        // echoing JSON response
        echo json_encode($response);
		
		}
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
