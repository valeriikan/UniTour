<?php 
require "conn.php";
$username = $_POST["username"];
$password = $_POST["password"];
$firstname = $_POST["firstname"];
$secondname = $_POST["secondname"];
$mysql_qry = "insert into users (username, password, firstname, secondname) values ('$username','$password','$firstname','$secondname')";
if($conn->query($mysql_qry) === TRUE) {
echo "Registration successful";
}
else {
echo "Error: " . $mysql_qry . "<br>" . $conn->error;
}
$conn->close();
?> 