<?php
	/** Never put whitespace before PHP opening tag. */
	
	/** Turn on error reporting so you can see what goes wrong. */
	error_reporting(E_ALL); 
	ini_set('display_errors', 'On');

	/** Connects us to the database using the below includes' constants. */
	include_once 'includes/db_connect.php';
	
	/** Includes some information required for database connect. */
	include_once 'includes/psl-config.php'; //

	/** Write your query here, store it in a variable with the prep_ prefix. */
	$prep = "SELECT * FROM UserAccount";
	

	/** If the statement was prepared sucessfully.. */
	if ($result = mysqli_query($mysqli, $prep))
	{
		printf("Select returned %d rows.<br>", $result->num_rows); //eg. print how many rows you got
	} 
	else 
	{
		/** Always have an error message for when stuff goes wrong. */
		 $error_msg .= '<p class="error">Database error (' . $email . ')</p>';
	}
?>

<html>
	<body>
		<?php
			/** Fetch the columns (fields) */
			$finfo = $result->fetch_fields();
			
			/** Print out their names, for reference. */
			foreach ($finfo as $val)
			{
				printf("Column:	%s", $val->name);
				echo "<br>";
			}
			
			//Table start
			echo "<table border='1'>";
			
			//Table headers start
			echo "<tr>";
			foreach ($finfo as $val) //'for each column (field) as variable '$val'
			{
				echo "<th>" . $val->name . "</th>"; //print that column
			}
			echo "</tr>";
			
			
			//Table contents start
			while($row = mysqli_fetch_array($result)) //While we have rows in our result table..
			{
				echo "<tr>";
				
				for($i = 0; $i < mysqli_num_fields($result); $i++)
				{
					echo "<td>" . $row[$i] . "</td>"; //print row dynamically, based on # of columns called!
				}
				
				//Alternatively, you can specify which columns you want to print using names, not numbers.
				/*echo "<td>" . $row['id']	. "</td>";
				echo "<td>" . $row['email']	. "</td>";
				echo "<td>" . $row['fName']	. "</td>";
				echo "<td>" . $row['lName']	. "</td>";*/
				
				echo "</tr>";
			}
			echo "</table>";
			
			mysqli_free_result($result); //Free table afterwards!
		?>
	</body>
</html>