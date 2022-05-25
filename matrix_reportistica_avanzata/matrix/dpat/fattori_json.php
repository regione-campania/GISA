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

$query = "select mn.id as id_nominativo, mn.fattori_text, nf.id as id_relazione, nf.valore, mf.id as id_fattore, unaccent(mf.descr) descr, coalesce(max_perc, 100) max_perc 
, unaccent(mf.note) note
from matrix.mod4_nominativi mn 
right join matrix.mod4_fattori mf on  mn.id_nominativo_struttura = $id
left join matrix.mod4_nominativi_fattori nf on mn.id_nominativo_struttura = nf.id_nominativo_struttura and mf.id = nf.id_fattore
where enabled order by mf.id";

//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;

$j = 0;
foreach($arResults as $row) {
	$resp->data[$j]["id_nominativo"] = $row['id_nominativo'];
    $resp->data[$j]["fatt_text"] = $row['fattori_text'];
	$resp->data[$j]["id_relazione"] = $row['id_relazione'];
	$resp->data[$j]["valore"] = $row['valore'];
	$resp->data[$j]["id_fattore"] = $row['id_fattore'];
	$resp->data[$j]["descr"] = $row['descr'];
	$resp->data[$j]["max_perc"] = $row['max_perc'];
	$resp->data[$j]["note"] = $row['note'];

   	$j++;
}

$sss = json_encode($resp); 

//echo " ".(round(microtime(true) * 1000))-$millis;

//echo $json;
echo $sss;

?>
