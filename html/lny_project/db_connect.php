<?php
 
class DB_CONNECT {
 
    // constructor
    function __construct() {
          $this->connect();
    }
 
    // destructor
    function __destruct() {
        // $this->close();
		     $this->close();
    }
 
    // Connecting to database
    public function connect() {
		require_once __DIR__ . '/db_config.php';
        // Connecting to mysql database
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());

        // Selecing database
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());

        // returing connection cursor
        return $con;
    }
 
    // Closing database connection
    public function close() {
        mysql_close();
    }
 
}
 
?>