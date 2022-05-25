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

$idStruttura = $array[0];
$f1 = $array[1];
$f2 = $array[2];
$sottr = $array[3];
$ups = $array[4];
$uba = $array[5];


$q = "insert into matrix.mod4_strutture(id_struttura, sottr,
			fattore1, fattore2, ups, uba) values ($idStruttura, $sottr, $f1, $f2, $ups, $uba)
		ON CONFLICT (id_struttura) 
		DO
			UPDATE
			SET sottr = $sottr, fattore1 = $f1, fattore2 = $f2, ups = $ups, uba = $uba";
		
if(!pg_query($q)){
	echo "KO ";
	echo $q;
	exit;
}

$q = "update matrix.struttura_asl set ups = $ups, uba= $uba where id_gisa = $idStruttura";
	if(!pg_query($q)){
		echo "KO ";
		echo $q;
		exit;
	}

$q = "select * from matrix.update_valori_somme()";
if(!pg_query($q)){
	echo "KO ";
	echo $q;
	exit;
}

echo "OK";
?>
