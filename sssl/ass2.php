<html>
<body>
<form action="" method="post">
<input type="text" name="string" />
<input type="submit" />
</form>
</body>
</html>
<?php
if($_POST)
{
$string = strtolower($_POST['string']);
$vowels = array('a','e','i','o','u');
$len = strlen($string);
$num = 0;
for($i=0; $i<$len; $i++){
if(in_array($string[$i], $vowels))
{
$num++;
}
}
function countDigits( $string )
{
return preg_match_all( "/[0-9]/", $string );
}
substr_count($string, ' ');
echo "Number of vowels : <span style='color:green; font-weight:bold;'>". $num."</span>";
echo "<br>";
echo "Number of digits :".countDigits($string);
echo "<br>";
echo "Number of spaces :" . substr_count($string," ");
}
?>