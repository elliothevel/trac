<?php
if (isset($_COOKIE["username"]))
{
$printhome=1; 
}
else
{
header('Location: loginPage.php');
}
?>
<html>
    <head> 
        <title>TRAC Demo site--User Info</title>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	  <script src="js/jquery-1.11.0.js"></script>
	<script src="js/script.js"></script>
    </head>

<body class="theme1">
	<!-- Main Container tarts -->
	<div class="main-container">
		<!-- Header Wrapper starts -->
		<div class="header no-border">
			<div class="user-details">
				<span class="bold">Welcome:</span> <span><?php
				if($printhome==1)
				{
				echo $_COOKIE['username'];
				}
				else
				{}
				?></span>
			</div>
			<div class="product-name"></div>
			<div class="product-info">
				<h2>User Information</h2>
			</div>
		</div>
		<!-- Header Wrapper ends -->
		
		<!-- Navigation Tabs starts -->
		<ul class="nav-tabs">
			<li id="home">HOME</li>
			<li class="selected" id="userInfo">USER INFO</li>
			<li id="race">RACES</li>
			<li id="training">TRAINING LOG</li>
			<li id="liveView">LIVE LAP TIME</li>
		</ul>
		<form action="signout.php" method="post">
		<input type="submit" name="" value="Sign Out" id="signout" class="signout" />
		</form>
		<!-- Navigation Tabs starts -->
		
		<!-- Content Wrapper starts -->
		<div class="content">
			<div class="user-info">
			    <form action="" method="post">
				<h2><font color="#5D6770">Create a new account:</font> </h2>
				<div class="left-panel">
					<div class="box right">
					<div class="form-row">
						<label>Name:</label></div>
						<div class="form-row">
						<span><input name="name" value="name goes here" id="" type="text" /></span>
					</div>
					<div class="form-row">
						<label>User Name:</label></div>
						<div class="form-row">
						<span><input name="username" value="username goes here" id="" type="text" /></span>
					</div>
					<div class="form-row">
						<label>Tag Number:</label></div><div class="form-row">
						<span><input name="rfidnum" value="tag number is here" id="" type="text" /></span>
					</div>
					<div class="form-row">
						<label>Coach:</label></div><div class="form-row">
						<span><input name="coach" value="coachesname here" id="" type="text" /></span>
					</div>
					<div class="form-row">
						<label>Weight:</label></div><div class="form-row">
						<span><input name="weight" value="" id="" type="text" /></span>
					</div>
					<div class="form-row">
						<label>Male or Female:</label></div><div class="form-row">
						<span><select name="sex">
									<option value="male">Male</option><option value="female">Female</option>
							</select></span>
					</div>
                                        <div class="form-row">
						<label>User Type:</label></div><div class="form-row">
						<span>
							<select name="usertype">
									<option value="athlete">Athlete</option><option  value="coach">Coach</option>
							</select>
						</span>
					</div>
					
					<div class="form-row">
						<label>Password:</label></div><div class="form-row">
						<span><input name="password" value="******" id="" type="text" /></span>
					</div>
					<div class="form-row">
						<label>Verify Password:</label></div><div class="form-row">
						<span><input name="password2" value="******" id="" type="text" /></span>
					</div>
					<?php
					if($printbelow==1)
					{
					    echo"<font color='red'>Your Password/Username is invalid</font>";
					}
					?> 
					<div class="form-row button-container">
								<input type="submit" name="save" id="save" value="Save" />
							</div>
					</div>
					
					
				</div>
				

				
				<div class="right-panel">
					<div class="body">
					
					<img src="images/quote.png">
					
					               </div>
					</div>
				</div>
				
			</div>
			</form>
		
		<!-- Content Wrapper ends -->
	    
		<div class="pageFooter" width="1000px">
			<div class="compbase parbase globalfooter">
				<div class="centerContainer">
					<div class="contactModule gridRight">
						<label>Share</label>

						<a href="" target="_new" class="linkedin"></a>
						
					</div>
					<div class="signUp gridRight">
						<div class="newslettersignup newsletter"></div>
					</div>
					<a href="/en/home.html">
						<img alt="Logo" title="traclogo" class="cq-dd-image" src="images/traclogo_small.png">
					</a>
					<div class="contactModule bottom">
						<span class="copyright">&copy; 2014 Timing and Racing Around the Clock LLC. All rights reserved.</span>
						<span class="links">
							<a href="" target="_new">Locations</a>
							<a href="l" target="_new">Legal &amp; Privacy Notices</a>
							<a href="" target="_new">Contact Us</a>
						</span>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Wrapper ends -->
		
	</div>
	<!-- Main Container ends -->
</body>
</html>