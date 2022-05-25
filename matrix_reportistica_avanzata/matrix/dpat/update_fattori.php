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
$once = true;

foreach($array as $values) {
	$id_fattore = $values[0];
	$id_nominativo = $values[1];
	$valore = $values[2];
	$note = $values[3];

	if($id_fattore == null && $valore == null){ //prima volta
		$q = "delete from matrix.mod4_nominativi_fattori where id_nominativo_struttura = $id_nominativo";
			//echo $q;
		
		if(!pg_query($q)){
			echo "KO";
			echo $q;
			exit;
		}

		$note = str_replace("'", "", $note);

		$q = "insert into matrix.mod4_nominativi(id_nominativo_struttura, fattori_text) values ($id_nominativo, unaccent('$note'))
				ON CONFLICT (id_nominativo_struttura) 
				DO
					UPDATE
					SET fattori_text = unaccent('$note')";
					//echo $q;
				
		if(!pg_query($q)){
			echo "KO";
			echo $q;
			exit;
		}

	}else{

		$q = "insert into matrix.mod4_nominativi_fattori(id_nominativo_struttura, id_fattore, valore) values ($id_nominativo, $id_fattore, $valore)
				ON CONFLICT (id_nominativo_struttura, id_fattore) 
				DO
					UPDATE
					SET valore = $valore";
					//echo $q;
				
		if(!pg_query($q)){
			echo "KO";
			echo $q;
			exit;
		}
	}
}
echo "OK";
?>
