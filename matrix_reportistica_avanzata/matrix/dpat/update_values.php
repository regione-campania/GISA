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


$array = $_POST['values'];
//var_dump($array);

foreach($array as $values) {
	$id = $values[0];
	$livello = $values[1];
	$carico = $values[2];
	$sottr1 = $values[3];
	$sottr2 = $values[4];
	$f1 = $values[5];
	$f2 = $values[6];
	$f3 = $values[7];
	$ubaora = $values[8];
	//$text = $values[9];
	$ups = $values[9];
	$uba = $values[10];
	$perc_dest_uba = $values[11];

//	$text = str_replace("'", "", $text);

	$q = "insert into matrix.mod4_nominativi(id_nominativo_struttura, livello_formativo, carico_annuale, perc_sottr, perc_sottr2, 
				fattore1, fattore2, fattore3, uba_ora, ups, uba, perc_dest_uba, entered) values ($id, $livello, $carico, $sottr1, $sottr2, $f1, $f2, $f3, $ubaora, $ups, $uba, $perc_dest_uba, current_timestamp)
			ON CONFLICT (id_nominativo_struttura) 
			DO
				UPDATE
				SET livello_formativo = $livello, carico_annuale = $carico, perc_sottr = $sottr1, perc_sottr2 = $sottr2, fattore1 = $f1, fattore2 = $f2, fattore3 = $f3, uba_ora = $ubaora, ups = $ups, uba = $uba, perc_dest_uba = $perc_dest_uba  ,entered = current_timestamp";
				//echo $q;
			
	if(!pg_query($q)){
		echo "KO";
		echo $q;
		exit;
	}


}
echo "OK";
?>
