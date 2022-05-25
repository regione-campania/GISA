<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
ini_set('max_execution_time', '0'); // for infinite time of execution 
ini_set('memory_limit', '3024M');
header('Cache-Control: max-age=0');
mb_internal_encoding("UTF-8");

require_once("../../dal_include.php");
require_once("../../dal_connessione.php");
header ("Content-Type: application/vnd.ms-excel");

ob_start();

$id = $_REQUEST['id'];
$inizio = $_REQUEST['inizio'];
$fine = $_REQUEST['fine'];

/*if($inizio == '')
	$inizio = '1900-01-01';
if($fine == '')
	$fine = '2100-01-01';*/

$results = pg_query("select * from ra.estrazioni where id = ". $id);
while ($row = pg_fetch_assoc($results)) {
	$query = $row['query'];
	$filename = $row['filename'];
//	$header = $row['header'];
}

$query = str_replace('$data_in$', $inizio, $query);
$query = str_replace('$data_fin$', $fine, $query);

//echo $query;

$filename=str_replace(' ', '_', $filename).".xls";
header ("Content-Disposition: inline; filename=$filename");

function n2l($n)
{
    for($r = ""; $n >= 0; $n = intval($n / 26) - 1)
        $r = chr($n%26 + 0x41) . $r;
    return $r;
}

//$columns = explode(",", $header);
//$arResults = CaricaArray($results);

?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang=it><head>
<title>Titolo</title>

</head>
<body>
<table border="1">
<?php
$results = pg_query($query);
$n = pg_num_fields($results);

echo"<tr> ";
for ($j = 0; $j < $n; $j++) {
	echo "<td style=\"background-color:blue; text-align:center; color: white\"> ".strtoupper(pg_field_name($results, $j))." </td>";
}
echo " </tr>";

//foreach($arResults as $row){
while ($row = pg_fetch_row($results)) {
	echo"<tr> ";
	//echo var_dump($row);
	//$i = 0;
	for ($j = 0; $j < $n; $j++) {
		$val = $row[$j];
		//$val = $row[trim($c)];
		echo "<td style=\"mso-number-format:'\@';\"> $val </td>";
	//	$i = $i+1;
	}
	echo " </tr>";
	ob_flush();
	flush();
}
ob_end_flush();
?>
</table>
</body></html>


