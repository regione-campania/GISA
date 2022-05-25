<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
ini_set('max_execution_time', '0'); // for infinite time of execution 
ini_set('memory_limit', '512M');
header('Cache-Control: max-age=0');

require_once("../../dal_include.php");
require_once("../../dal_connessione.php");
header ("Content-Type: application/vnd.ms-excel");

$query = $_REQUEST['q'];

$d1 = $_REQUEST['d1'];

$d3 = $_REQUEST['d3'];



date_default_timezone_set('Italy/Rome');
$now = date('m/d/Y h:i:s a', time());
$filename="IUV.xls";
header ("Content-Disposition: inline; filename=$filename");

function n2l($n)
{
    for($r = ""; $n >= 0; $n = intval($n / 26) - 1)
        $r = chr($n%26 + 0x41) . $r;
    return $r;
}

//$query = str_replace("order by path_ord", "order by 1,3", $query);

$tokens = explode('order by', $query);
$query = $tokens[0].' order by 1,3';


echo "<script> console.log($query); </script>";

$results = pg_query($query);
$arResults = CaricaArray($results);

$cols = array();
foreach($arResults as $row){
	array_push($cols, $row['colonna']);
}
$cols = array_unique($cols);
$n_col = count($cols)*4 + 1;

$rows = array();
foreach($arResults as $row){
	array_push($rows, $row['riga']);
}
$rows = array_unique($rows);
$n_row = count($rows) + 6;

?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang=it><head>
<title>Titolo</title>
<style>
.num {
  mso-number-format:"\#\,\#\#0;
}

.per {
	mso-number-format:"Percent";
}
</style>
</head>
<body>
<table border="1">
<?php

echo "<tr> <td colspan = \"$n_col\"> Data di esportazione: $now </td> </tr>";
echo "<tr> <td colspan = \"$n_col\"> $d3 </td> </tr>";
echo "<tr> <td colspan = \"$n_col\"> $d1 </td> </tr>";


//echo "<tr> <td> $now </td> </tr>";
echo "<tr> 
	<td style=\"background-color: #d9d9d9\"> </td>";

foreach($cols as $c ){
		echo " <td style=\"text-align: center; background-color: #d9d9d9\" colspan = \"3\"> $c </td> <tdstyle=\"background-color: #d9d9d9\"><td style=\"background-color: #d9d9d9\" width=\"5\"> </td>";		
}
echo "</tr>";

echo "<tr> 
	<td style=\"background-color: #d9d9d9\"> </td>";
foreach($cols as $c ){
	echo "<td style=\"text-align: center;background-color: #d9d9d9\"> Eseguiti </td> <td style=\"text-align: center;background-color: #d9d9d9\"> Programmati </td> <td style=\"text-align: center;background-color: #d9d9d9\"> % </td> <td style=\"background-color: #d9d9d9\"></td>";
}
echo "</tr>";


echo "<tr> 
	<td style=\"background-color: #fff0b3\"> TOTALE </td>";

$j = 0;	
foreach($cols as $c ){
	echo "<td style=\"background-color: #fff0b3\"> =SOMMA(".n2l(($j*4)+1)."7".":".n2l(($j*4)+1).$n_row.") </td> <td style=\"background-color: #fff0b3\" > =SOMMA(".n2l(($j*4)+1+1)."7".":".n2l(($j*4)+1+1).$n_row.") </td> <td style=\"background-color: #fff0b3\" > =100/".n2l(($j*4)+1+1)."8*".n2l(($j*4)+1)."8  & \"%\" </td> <td style=\"background-color: #fff0b3\"> </td> ";		
	//echo "<td style=\"background-color: #d9d9d9\"></td> <td style=\"text-align: center; background-color: #d9d9d9\" > $c </td> <tdstyle=\"background-color: #d9d9d9\"> </td> <td style=\"background-color: #d9d9d9\"> </td>  <td style=\"background-color: #d9d9d9\"> </td>";		

	$j++;	
}
echo "</tr>";

$i = 0;
foreach($arResults as $row){
	$r = $row['riga'];
	$v = intval($row['eseguiti']);
	$t = intval($row['sum']);
	$p = intval(100/$t*$v);
	if($i % count($cols) == 0){
		if($i != 0)
			echo "</tr>";
		echo "<tr> <td style=\"background-color: #d9d9d9\"> $r </td> ";
	}
	echo "<td class=\"num\"> $v </td> <td class=\"num\"> $t </td> <td> $p % </td> <td></td>";
	$i++;
}




?>
</table>
</body></html>


