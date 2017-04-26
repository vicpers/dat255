<?php 
	$con=mysqli_connect("localhost", "my_user", "my_passport", "my_db")

	$vesselID = $_POST["vesselID"];

	$statement = mysqli_prepare($con, "INSERT INTO User (vesselID) VALUES(?) ");
	mysqli_stmt_bind_param($statement, "i", $vesselID); 
	mysqli_stmt_execute($statement);

	mysqli_stmt_close($statement);
	


	mysqli_close($con); 

?> 

