<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
//require_once("dal_connessione.php");
require_once("common/config.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
header('Content-Type: application/json; charset=UTF-8');
mb_internal_encoding("UTF-8");

$id_f = $_REQUEST['id_f'];
$descr_f = $_REQUEST['descr_f'];
$testo_f = $_REQUEST['testo_f'];
$contiene_uba = $_REQUEST['contiene_uba'];
$valida_ups = $_REQUEST['valida_ups'];
$valida_uba = $_REQUEST['valida_uba'];
$fattore_fin = $_REQUEST['fattore_fin'];


$strConnection = "host=" . $_CONFIG['db_host'] . " " .
				 "port=" . $_CONFIG['db_port'] . " " .
				 "dbname=" . $_CONFIG['db_name'] . " " .
				 "user=" . $_CONFIG['db_user']  . " ";

			  
$conn = pg_connect($strConnection) or die ("Errore critico di Connessione al Database");


$query = "select * from MATRIX.updateformula($id_f, '$descr_f', '$testo_f', $contiene_uba, $valida_ups, $valida_uba, $fattore_fin)";


pg_query($conn, $query);

//echo $query;

$query_new = "select * from MATRIX.formule where descrizione = '$descr_f' and testo = '$testo_f' and contiene_uba = $contiene_uba and valida_ups = $valida_ups and valida_uba = $valida_uba and fattore_fin = $fattore_fin";


$results = pg_query($conn, $query_new);


$arResults = CaricaArray($results);

$i = 0;
foreach($arResults as $row) {
	$responce->json[$i]['id'] = $row['id'];
	$responce->json[$i]['descr'] = $row['descrizione'];
	$responce->json[$i]['testo'] = $row['testo'];
	$responce->json[$i]['valida_uba'] = $row['valida_uba'];
	$responce->json[$i]['valida_ups'] = $row['valida_ups'];
	$i++;
}

$sss = json_encode($responce); 
//echo $responce;
echo $sss;


?>
