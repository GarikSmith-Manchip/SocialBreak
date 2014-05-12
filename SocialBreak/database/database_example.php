<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<link rel="stylesheet" href="phpExample/style/style.css" />
		
		<title>CodersCult Webinar #001 Lesson</title>
	</head>
	
	<body id="body">
		<div class = "container">
		
			<script src="phpExample/javascript/javascript.js" type="text/javascript"></script>
			
			<div class = "header">
				<h1>Example #001 Garik Smith-Manchip</h1>
				<div>Understanding PHP, MySQL, HTML, CSS</div>
			</div>
			
			<div class = "content">
				<p>Todays date is: <?php echo date('F j, Y'); ?> </p>
				
				<?php
					mysql_connect("localhost", "team16", "#Team16BestTeam", "sb");
					$users = mysql_query("SELECT * FROM UserAccount");
				?>
								
				<table border = '1' cellpadding = '5' cellspacing = '0'>
					<thead>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Email</th>
							<th>Birthday</th>
						</tr>
					</thead>
					
					<tbody>
						<?php 
							while($row = mysql_fetch_object($users))
							{
						?>
							<tr>
								<td><?php echo $row->fname;?></td>
								<td><?php echo $row->lname;?></td>
								<td><a href = "mailto:<?php echo $row->email;?>"></td>
								<td><?php echo $row->DoB;?></td>
							</tr>
						<?php
							}
						?>
					</tbody>
				</table>
			</div>
			
			<div class = "footer">
				<p>Copyright &copy; 2014 GarikInc</p>
			</div>
		</div>
	</body>
</html>