function validateForm()
{        
    var space=/\s/i;
    var x=document.forms["form1"]["fName"].value; 
	var patt=/@my\.bcit\.ca/ig;
	var atpos=x.indexOf("@");
	var dotpos=x.lastIndexOf(".");  
	
	if (x.length == 0)
	{
		alert("First name must be filled out");
		document.forms["form1"]["fName"].focus();
		return false;
	}
	else if (space.test(x) == true)
	{
		alert("First name should not have any spaces");
		document.forms["form1"]["fName"].focus();
		return false;
	}
		
	x=document.forms["form1"]["lName"].value;
	
	if (x == null || x == "")
	{
		alert("Last name must be filled out");
		document.forms["form1"]["lName"].focus();
		return false;
	}
	else if (space.test(x) == true)
	{
		alert("Last name should not have any spaces");
		document.forms["form1"]["lName"].focus();
		return false;
	}
		
	x=document.forms["form1"]["email"].value;
	
	if (x == null || x == "")
	{
		alert("BCIT Email must be filled out");
		document.forms["form1"]["email"].focus();
		return false;
	}
	else if (space.test(x) == true)
	{
		alert("BCIT Email should not have any spaces");
		document.forms["form1"]["email"].focus();
		return false;
	}
	else if(patt.test(x) == false || 
			 x.indexOf("@") != x.lastIndexOf("@") ||			
			 patt.lastIndex != x.length)
	{	
		alert("Invalid BCIT Email");
		document.forms["form1"]["email"].focus();
		return false;
	}
	   
	x = document.forms["form1"]["altEmail"].value;
	                 
	if (space.test(x) == true)
	{
		alert("Alternate Email should not have any spaces");
		document.forms["form1"]["altEmail"].focus();
		return false;
	}
	else if (x != null && 
			 x != "" &&
			(atpos<1 ||
			  dotpos<atpos+2 || 
			  dotpos+2>=x.length || 
			  atpos!=x.lastIndexOf("@")))
	{
		alert("Invalid Alternate Email");
		document.forms["form1"]["altEmail"].focus();
		return false;
	}
		
	x = document.forms["form1"]["password"].value;
	
	if (x == null || x == "" || x.length < 8)
	{
		alert("Must have a password with 8 letters or longer");
		document.forms["form1"]["password"].focus();
		return false;
	}
	
	x = document.forms["form1"]["rePassword"].value; 
	
	if (x == null || x == "" || x != document.forms["form1"]["password"].value)
	{
		alert("Passwords do not match");
		document.forms["form1"]["rePassword"].focus();
		return false;
	}
	
	if (document.forms["form1"]["ageCheckbox"].checked == false)
	{
		alert("You must be over 18 years old to register");
		document.forms["form1"]["ageCheckbox"].focus();
		return false;
	}
	
	if (document.forms["form1"]["tosCheckbox"].checked == false)
	{
		alert("You must agree with our ToS to register");
		document.forms["form1"]["tosCheckbox"].focus();
		return false;
	}
	
	document.forms["form1"].action="./registration.php";
	document.forms["form1"].submit();
	window.location="./success.html";
	
	return true;
}