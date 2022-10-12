<?php
 if(isset($_POST['submit']))
 {
  $fn=$_POST['fname'];
  $ln=$_POST['lname'];
$dob=$_POST['dob'];
  $em=$_POST['email'];
  $mob=$_POST['mobile'];
  $city=$_POST['city'];
  $gender=$_POST['gender'];
  $hobies=$_POST['hobby'];
  $pass=$_POST['pass'];

  echo "First name : $fn <br><br>";
  echo "Last name : $ln <br><br>";
echo "DOB : $dob <br><br>";
  echo "Email : $em <br><br>";
  echo "Mobile : $mob <br><br>";
  echo "City : $city <br><br>";
  echo "Gender : $gender <br><br>";

  echo "<h3>Hobbies</h3>";

  $i=0;

  while($i<sizeof($hobies))
  {
   echo $hobies[$i]."<br>";

   $i++;
  }
 }
?>