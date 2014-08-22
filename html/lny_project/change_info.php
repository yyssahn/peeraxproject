<?php
 
/*
 * Following code will create a new product row
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

   if ($_POST['tag']=='about'){
	   $about= $_POST['about'];
      $sql = "UPDATE users SET about = ? WHERE phonenumber = ? AND about!=?";
	   $stmt = $db->prepareStatement($sql);
	   $db->bindArray($stmt, array('s','s','s'), array($about,$phonenumber,$about));
	   $db->executeStatement($stmt);
	   $result = $db->getResult($stmt);

		if($result){
			$response["success"] = 1;
			$response["message"] = "successfully updated about for user $phonenumber";
			echo json_encode($response);
		}else{
			$response["success"] = 0;
			$response["message"] = "Error in 'about' clause";
				
		// echoing JSON response
			echo json_encode($response);
		}
	
	}
	else if($_POST['tag']=='info'){
		$sql = "SELECT about,degree,subject FROM users,tutorsubject 
					WHERE tutorsubject.phonenumber=? AND users.phonenumber=?";

	   $stmt = $db->prepareStatement($sql);
	   $db->bindArray($stmt, array('s','s'), array($phonenumber,$phonenumber));
	   $db->executeStatement($stmt);
	   $result = $db->getResult($stmt);

		if(!$result){
			$sql = "SELECT about,degree FROM users WHERE phonenumber=?";

			$stmt = $db->prepareStatement($sql);
			$db->bindParameter($stmt, 's', $phonenumber);
			$db->executeStatement($stmt);
			$result = $db->getResult($stmt);
		}
		if($result){
			$response["success"] = 1;
			$response["message"] = "successfully retrieved info for user $phonenumber";
			
			if($result){
				$response["about"] = $result[0]["about"];
				$response["degree"] = $result[0]["degree"];
				$response["subject"] = $result[0]["subject"];
			}
		}
		else{
			$response["success"] = 0;
			$response["message"] = "Error in 'info' clause";
			$response["subject"] = $phonenumber;
			
		}
		
		echo json_encode($response);

	}
	else if($_POST['tag']=='degree'){
   	$degree = $_POST['degree'];
      $sql = "UPDATE users SET degree=? WHERE phonenumber=? AND degree!=?";	
      $stmt = $db->prepareStatement($sql);
	   $db->bindArray($stmt, array('i','s','i'), array($degree,$phonenumber,$degree));
	   $db->executeStatement($stmt);
	   $result = $db->getAffectedRows($stmt);
		
		if($result){
			$response["success"] = 1;
			$response["message"] = "successfully updated degree for user $phonenumber";
			echo json_encode($response);
      }		
		else{
			$response["success"] = 0;
			$response["degree"]=$degree;
			$response["phonenumber"]=$phonenumber;
			$response["message"] = "Error in 'degree' clause";
				
		// echoing JSON response
			echo json_encode($response);
		}
	}else if($_POST['tag']=='regid'){
   	$regid = $_POST['regid'];
      $sql = "UPDATE users SET regid=? WHERE phonenumber=? AND regid!=?";	
      $stmt = $db->prepareStatement($sql);
	   $db->bindArray($stmt, array('s','s','s'), array($regid,$phonenumber,$regid));
	   $db->executeStatement($stmt);
			$result = $db->getResult($stmt);
		
		if($result){
			$response["success"] = 1;
			$response["message"] = "successfully updated degree for user $phonenumber";
			echo json_encode($response);
      }		
		else{
			$response["success"] = 0;
			
$response["regid"]=$regid;
			$response["phonenumber"]=$phonenumber;
			$response["message"] = "Error in 'regid' clause";
				
		// echoing JSON response
			echo json_encode($response);
		}
	}	
	else if ($_POST['tag']=='seen'){
      $sql = "UPDATE users SET seen=true WHERE phonenumber=?";	   
      $stmt = $db->prepareStatement($sql);
	   $db->bindParameter($stmt, 's', $phonenumber);
	   $db->executeStatement($stmt);
	   $result = $db->getAffectedRows($stmt);
		
		if ($result){
			$response["success"]=1;
			$response["message"]="successfully updated seen for user $phonenumber";
			echo json_encode($response);
		}
      else{
			$response["success"] = 0;
			$response["message"] = "Error in 'seen' clause";
				
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
