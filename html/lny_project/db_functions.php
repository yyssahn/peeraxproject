<?php
 
class DB_Functions {
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() {
      require_once "database_helper.php";
      $db = new DatabaseHelper();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($phone, $password) {
         $sql = "INSERT INTO users(name, phone, password, about, degree) VALUES(?,?,?,?,?)";
         $stmt = $db->prepareStatement($sql);
         $param_types = array('s','s','s','s','s');
         $params = array('','$phone', '$password','','');
         $db->bindArray($stmt,$param_types,$params);
         $db->executeStatement($stmt);
         $result = $db->getAffectedRows($stmt);           
         // check for successful store
        if ($result) {
            // get user details 
            $uid = mysqli_insert_id(); // last inserted id
            $sql = "SELECT * FROM users WHERE uid = ?";
            $stmt = $db->prepareStatement($sql);
            $db->bindParameter($stmt,'i',$uid);
            $db->executeStatement($stmt);
            $result = $db->getResult($stmt);
            // return user details
            return $result[0];
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($phone, $password) {
        $sql = "SELECT * FROM users WHERE phone = ?";
        $stmt = $db->prepareStatement($sql);
         $db->bindParameter($stmt,'s',$phone);
         $db->executeStatement($stmt);
         $result = $db->getResult($stmt);
        // check for result 
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $salt = $result[0]['salt'];
            $encrypted_password = $result[0]['encrypted_password'];
         //   $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result[0];
            }
        } else {
            // user not found
            return false;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $sql = "SELECT phone from users WHERE phone = ?";
      $stmt = $db->prepareStatement($sql);
         $db->bindParameter($stmt,'s',$email);
         $db->executeStatement($stmt);
         $result = $db->getResult($stmt);
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed 
            return true;
        } else {
            // user not existed
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
}
 
?>
