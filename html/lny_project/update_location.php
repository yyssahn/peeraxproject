<?php
 
/*
 * Following code will update user location
 * All product details are read from HTTP Post Request
 */

// Connect to database
require_once "database_helper.php";
$db = new DatabaseHelper();

// array for JSON response
$response = array();

// check for required fields

if (isset($_POST['tag']) && isset($_POST['phonenumber']) ) {
 
   $phonenumber = $_POST['phonenumber'];

   if ($_POST['tag']=='location' && isset($_POST['latitude']) && isset($_POST['longitude'])){
	   $latitude = doubleval($_POST['latitude']);
      $longitude = doubleval($_POST['longitude']);

      $sql = "INSERT INTO location
               (phonenumber, latitude, longitude, timestamp)
              VALUES
               (?,?,?,NOW())
              ON DUPLICATE KEY UPDATE
               latitude = VALUES(latitude),
               longitude = VALUES(longitude),
               timestamp = VALUES(timestamp)";
	   $stmt = $db->prepareStatement($sql);
	   $db->bindArray($stmt, array('s','d','d'), array($phonenumber,$latitude,$longitude));
	   $db->executeStatement($stmt);
	   $result = $db->getAffectedRows($stmt);

		if($result){
			$response["success"] = 1;
			$response["message"] = "successfully updated location for user $phonenumber";
			echo json_encode($response);
		}else{
			$response["success"] = 0;
			$response["message"] = "Error in 'location' clause";
				
		// echoing JSON response
			echo json_encode($response);
		}
	}
	else{
		$response["success"] = 0;
		$response["message"] = "Wrong tag";
		$response["tag"]=$_POST["tag"];
		$response["phonenumber"]=$_POST["phonenumber"];
      $response["query"]="UPDATE users SET seen = true WHERE phonenumber = '$phonenumber'";
		// echoing JSON response
			echo json_encode($response);
	}
}     
else{
   // required field is missing

   $response["success"] = 0;
   $response["message"] = "Required field(s) is missing";
	$response["tag"]=$_POST["tag"];
	$response["about"]=$_POST["about"];
	$response["phonenumber"]=$_POST["phonenumber"];
   
   // echoing JSON response
   echo json_encode($response);
}
?>
