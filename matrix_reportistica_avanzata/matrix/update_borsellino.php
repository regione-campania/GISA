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

$id = $_REQUEST['struttura'];
$uba = $_REQUEST['uba'];
$ups = $_REQUEST['ups'];



$strConnection = "host=" . $_CONFIG['db_host'] . " " .
				 "port=" . $_CONFIG['db_port'] . " " .
				 "dbname=" . $_CONFIG['db_name'] . " " .
				 "user=" . $_CONFIG['db_user']  . " ";

			  
$conn = pg_connect($strConnection) or die ("Errore critico di Connessione al Database");


$query = "update matrix.struttura_asl set ups = $ups, uba= $uba where id = $id";


pg_query($conn, $query);


$query_new = "select * from matrix.update_valori_somme()";

pg_query($conn, $query_new);


$query_new = "insert into matrix.borsellino_log values ($ups, $uba, $id, current_timestamp)";

pg_query($conn, $query_new);


echo $query;


?>
