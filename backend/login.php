<?php 
require "conn.php";
$username = $_POST["username"];
$password = $_POST["password"];
$sql = "select * from users where username like '$username' and password like '$password';";
$res = mysqli_query($conn, $sql);

if(mysqli_num_rows($res)>0) {

$result = array(); 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('username'=>$row[0],
'firstname'=>$row[2],
'secondname'=>$row[3]
));
}
echo json_encode(array("result"=>$result)); 
$conn->close();

}

else {
echo "Login error, please try again";
}
?> 