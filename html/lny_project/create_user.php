<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
//include db_functions.php;

 
// array for JSON response
$response = array();
//$dbf = new DB_FUNCTIONS();

 
// check for required fields

if (isset($_POST['name']) && isset($_POST['phonenumber']) && isset($_POST['password'])) {
 
   $name = $_POST['name'];
   $phonenumber = $_POST['phonenumber'];
   $regid = $_POST['regid'];
	$password = $_POST['password'];
	$salt = sha1(rand());
	$salt = substr($salt, 0, 10);
	$encrypted = base64_encode(sha1($password.$salt, true). $salt);
	
	
	// Connect to db
   require_once "database_helper.php";
   $db = new DatabaseHelper();

   require_once __DIR__ . '/db_functions.php';
	$dbf = new DB_FUNCTIONS();
	 
   $sql = "SELECT * FROM users where phonenumber = ?";
   $stmt = $db->prepareStatement($sql);
	$db->bindParameter($stmt, 's',$phonenumber);
   $db->executeStatement($stmt);
	$exist = $db->getResult($stmt);

   if($exist){
      $no =  count($exist);
	   if($no > 0){
         $response["success"] = 0;
         $response["message"] = "already exist";
   	}
    // echoing JSON response
   	echo json_encode($response);
	}
	else{
		// check if row inserted or not
		$sql = "INSERT INTO users(phonenumber,lastname, password, salt, about, degree, regid) VALUES(?,?,?,?,?,?,?)";
		$stmt = $db->prepareStatement($sql);
		$param_types = array('s','s','s','s','s','i','s');
		$params = array($phonenumber,$name,$encrypted,$salt,'','',$regid);
		$db->bindArray($stmt,$param_types,$params);
		$db->executeStatement($stmt);
		$result = $db->getAffectedRows($stmt);
		
		// check if row inserted or not
		if ($result) {
			$response["success"] = 1;
			$response["message"] = "User created";
			$sql = "SELECT * FROM users WHERE phonenumber = ?";
			$stmt = $db->prepareStatement($sql);
			$db->bindParameter($stmt,'s',$phonenumber);
			$db->executeStatement($stmt);
			$result = $db->getResult($stmt); 
			$user = $result[0];
			if($user){
				$response["phonenumber"]= $user["phonenumber"];
				$response["name"]= $user["lastname"];
				$response["about"]= $user["about"];
				$response["degree"]= $user["degree"];
				$response["seen"] = $user["seen"];
				$response["password"]=$_POST["password"];
				$response["encrypted"]=$encrypted;
				$response["salt"]=$salt;
				$response["regid"]=$_POST["regid"];
				// echoing JSON response
				echo json_encode($response);
			}
    	}else {
		   // failed to insert row
		   $response["success"] = 0;
		   $response["message"] = "Oops! An error occurred.";
			$response["name"]= $_POST["name"];
			$response["phonenumber"]= $_POST["phonenumber"];
			$response["password"]=$_POST["password"];
			$response["query"]="INSERT INTO users(name, phonenumber, password, salt, about, degree) VALUES('$name', '$phonenumber', '$password', '','','')";
		
			// echoing JSON response
		   echo json_encode($response);
    	}
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
