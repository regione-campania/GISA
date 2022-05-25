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
$year = $_CONFIG["mod4_year"];


//$query = "select * from matrix.vw_cf_uos vcu join matrix.struttura_asl a on a.id = vcu.id_s where vcu.id_s = $id;";

//$query = "select * from \"Analisi_dev\".vw_dpat_get_nominativi_validi n join matrix.struttura_asl a on a.id = n.id_struttura_semplice where n.id_struttura_semplice = $id;"; 

$query = "select livello_formativo, case when livello_formativo is not null then nominativo ||' [Salvato]' else nominativo end, codice_fiscale, qualifica, s.id, a.descrizione_breve, 
coalesce(m.carico_annuale, 365) as carico_annuale, coalesce(m.perc_sottr, 0) as perc_sottr, coalesce(m.perc_sottr2, 4) as perc_sottr2,
coalesce(m.fattore1, false) as fattore1, coalesce(m.fattore2, false) as fattore2, coalesce(m.fattore3, false) as fattore3, coalesce(m.uba_ora,0) as ubaora, coalesce(m.fattori_text,'') as fattori_text,
coalesce(m.perc_dest_uba, 0) as perc_dest_uba
from matrix.nominativi n join matrix.nominativi_struttura s on n.id = s.id_nominativo 
left join matrix.struttura_asl a on a.id_gisa = s.id_struttura 
left join matrix.mod4_nominativi m on m.id_nominativo_struttura = s.id
where anno = $year 
and s.id_struttura = $id";

//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;

$j = 0;
foreach($arResults as $row) {
	$resp->data[$j]["livello"] = strtoupper($row['livello_formativo']);
	$resp->data[$j]["cf"] = strtoupper($row['codice_fiscale']);
	$resp->data[$j]["uos"] = $row['descrizione_breve'];
	$resp->data[$j]["nominativo"] = strtoupper($row['nominativo']);
	$resp->data[$j]["qualifica"] = $row['qualifica'];
	$resp->data[$j]["id_nominativo"] = $row['id'];
	$resp->data[$j]["carico_annuale"] = $row['carico_annuale'];
	$resp->data[$j]["sottr"] = $row['perc_sottr'];
	$resp->data[$j]["sottr2"] = $row['perc_sottr2'];
	$resp->data[$j]["fattore1"] = $row['fattore1'];
	$resp->data[$j]["fattore2"] = $row['fattore2'];
	$resp->data[$j]["fattore3"] = $row['fattore3'];
	$resp->data[$j]["ubaora"] = $row['ubaora'];

	$resp->data[$j]["fatt_text"] = $row['fattori_text'];
	$resp->data[$j]["perc_dest_uba"] = $row['perc_dest_uba'];

   	$j++;
}

$sss = json_encode($resp); 

//echo " ".(round(microtime(true) * 1000))-$millis;

//echo $json;
echo $sss;

?>
