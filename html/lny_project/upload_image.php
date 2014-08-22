<?php
    $base=$_REQUEST['image'];
    $phonenumber=$_REQUEST['phonenumber'];
    $binary=base64_decode($base);
    header('Content-Type: image/jpeg; charset=utf-8');
	
    $filename = $phonenumber.".jpg";
    $file = fopen($filename, "wb") or die('failedtoopen');
    fwrite($file, $binary) or die('failedtowrite'); 
    fclose($file);
    echo 'Uploaded to' . $filename . ' successfully.';
?>