<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("../../dal_include.php");
require_once("../../dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
header('Content-Type: application/json; charset=UTF-8');
mb_internal_encoding("UTF-8");

$resp = $_REQUEST['resp'];

$query = "select * from ra.estrazioni where enabled";
 
$results = pg_query($query);
$arResults = CaricaArray($results);

$i = 0;
foreach($arResults as $row) {
	$responce->json[$i]['id'] = $row['id'];
	$responce->json[$i]['descr'] = $row['descr'];
	$responce->json[$i]['report'] = $row['report'];
	$responce->json[$i]['query'] = $row['query'];
	$responce->json[$i]['note'] = $row['note'];
	$responce->json[$i]['data_filter'] = $row['date_filter'];
	$responce->json[$i]['data_end_filter'] = $row['date_end_filter'];
	$responce->json[$i]['asl_filter'] = $row['asl_filter'];

	$i++;
}
$sss = json_encode($responce); 
//echo $json;
echo $sss;

?>
