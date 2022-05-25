<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
require_once("dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
header('Content-Type: application/json; charset=UTF-8');
mb_internal_encoding("UTF-8");


$id = $_REQUEST['id'];


//$query = "select * from matrix.vw_cf_uos vcu join matrix.struttura_asl a on a.id = vcu.id_s where vcu.id_s = $id;";

//$query = "select * from \"Analisi_dev\".vw_dpat_get_nominativi_validi n join matrix.struttura_asl a on a.id = n.id_struttura_semplice where n.id_struttura_semplice = $id;"; 

$query = "select * from matrix.lock_asl_mod4 where anno = 2020";

//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;

$j = 0;
foreach($arResults as $row) {
	$resp->data[$j]["asl"] = $row['asl'];
	$resp->data[$j]["is_locked"] = $row['is_locked'];
   	$j++;
}

$sss = json_encode($resp); 

//echo " ".(round(microtime(true) * 1000))-$millis;

//echo $json;
echo $sss;

?>
