<?php
 
/*
 * Following code will update user location
 * All product details are read from HTTP Post Request
 * 
 * Query for nearby users from:
 *
 * Title: 		Nearest-location finder for MySQL
 * Author: 		Oliver Jones
 * Published:	March 12, 2014
 * Website:		http://www.plumislandmedia.net/mysql/haversine-mysql-nearest-loc/
 *	
 */

// Connect to database
require_once "database_helper.php";
$db = new DatabaseHelper();

// array for JSON response
$response = array();

// check for required fields

if (isset($_POST['tag']) && isset($_POST['phonenumber']) ) {
 
   $phonenumber = $_POST['phonenumber'];

   if ($_POST['tag']=='nearby' && isset($_POST['latitude']) && isset($_POST['longitude']) && isset($_POST['filters'])){
	   //User's location (center of search area)
	   $latitude = doubleval($_POST['latitude']);
      $longitude = doubleval($_POST['longitude']);
      $radius = 30; //30km
      $distance = 111.045; // Used for km in Haversine Formula (for sql query). 69 for miles.
      $filters = $_POST['filters'];
      $degree = $filters[0];
      $minRating = $filters[1];
      $maxRating = $filters[2];
      $minPrice = $filters[3];
      $maxPrice = $filters[4];
      

		// Query for selecting users within a circular area of the user using the Haversine formula
      $sql =  "SELECT 
	               phonenumber, lastname, about, degree, distance
               FROM (
	               SELECT 
		               l.phonenumber,	l.latitude, l.longitude,
		               u.lastname, u.about, u.degree,
		               r.rating,
		               p.radius,
		               p.distance_unit
		               * DEGREES(ACOS(COS(RADIANS(p.latpoint))
		                 * COS(RADIANS(l.latitude))
		                 * COS(RADIANS(p.longpoint - l.longitude))
	                 + SIN(RADIANS(p.latpoint))
		                 * SIN(RADIANS(l.latitude)))) AS distance
	               FROM users AS u
	               JOIN (   
		               SELECT	$latitude 	AS latpoint, 	$longitude 	AS longpoint,
					               $radius		AS radius, 		$distance 	AS distance_unit
	               ) AS p
	               INNER JOIN location AS l ON u.phonenumber = l.phonenumber
	               INNER JOIN ratings AS r ON l.phonenumber = r.tutor_phone
	               INNER JOIN tutorsubject AS t ON r.tutor_phone = t.phonenumber
	               WHERE u.degree >= $degree AND u.phonenumber != $phonenumber
		               AND (r.rating BETWEEN $minRating AND $maxRating) 
		               AND (t.price BETWEEN $minPrice AND $maxPrice) 
		               AND l.latitude
			               BETWEEN p.latpoint  - (p.radius / p.distance_unit)
				               AND p.latpoint  + (p.radius / p.distance_unit)
		               AND l.longitude
			               BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))
				               AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))
               ) AS d
               WHERE distance <= radius
               ORDER BY distance
               LIMIT 15;";
	   $stmt = $db->prepareStatement($sql);
	   $db->bindArray($stmt, array('d','d','i','d'), array($latitude,$longitude,$radius,$distane));
	   $db->executeStatement($stmt);
	   $result = $db->getResult($stmt);

		if($result){
			$response["success"] = 1;
			$response["message"] = "successfully retrieved users near user $phonenumber";
			$response["people"] = array();
			
			foreach ($result as &$row){
        		$people = array();
				$people["name"]=$row["lastname"];
				$people["about"]=$row["about"];
				$people["phonenumber"]= $row["phonenumber"];
        		// push single product into final response array
        		array_push($response["people"], $people);
  			}
  			
			echo json_encode($response);
		}else{
			$response["success"] = 0;
			$response["message"] = "Error in 'nearby' clause";
				
		// echoing JSON response
			echo json_encode($response);
		}
	}
	else{
		$response["success"] = 0;
		$response["message"] = "Wrong tag";
		$response["tag"]=$_POST["tag"];
		$response["phonenumber"]=$_POST["phonenumber"];

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
