<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php

session_start();

//require_once("common/config.php");

$db_host = "dbGISAL";
$strConnection = "host=" . $db_host . " port=5432 dbname=gisa user=postgres";


$conn = pg_connect($strConnection) or die("Errore critico di Connessione al Database di login");


$id_asl = $_REQUEST['asl_id'];
$id_user = $_REQUEST['user_id'];
$called_url = $_REQUEST['called_url'];

echo $id_asl;
echo $id_user;


if ($id_asl == "-1") { //Regione o ORSA

	$readonly = '0';
} else {

	$query = "select id_ruolo::text, coalesce(id_struttura, -1) from public_functions.get_nominativi_modello4_dpat(" . $id_asl . ", date_part('year', CURRENT_DATE)::integer) where id_utente = " . $id_user;

	$result = pg_query($conn, $query);
	$n = pg_num_rows($result);
	$row = pg_fetch_row($result);
	$role = $row[0];
	$strut = $row[1];
	
	if ($role == '16') {
		$readonly = '1';
	} else {
		if ($n == 0) {
			//non presente in dpat ne' delegato
			header("Location: index.html");
			exit;
		}
		
		if ($role == '19' || $role == '21' || $role == '98' || $role == '59') { //Responsabile medico compex o vet complex o sempl dip o deleg
			$readonly = '0';
		} else {
			$readonly = '1';
		}
	}
}

$_SESSION['user'] = $u;
$_SESSION['id_asl'] = $id_asl;
$_SESSION['id_user'] = $id_user;
$_SESSION['called_url'] = $called_url;
$_SESSION['role'] = $role;
$_SESSION['strut'] = $strut;


////MODIFICA READONLY GIANLUCA
$_SESSION['readonly']=$readonly; //gestione vecchia
//$_SESSION['readonly'] = $_REQUEST['readonly']; //gestione nuova, readonly passato come parametro da gisa



header("location:/route2.php");
 
 /*
 
 if ( $_SESSION['called_url'] == 'formulematrix.gisacampania.it') {
	
	header("location:/formule.php"	 );

	} else {

		header("location:/tree.php"	 );
 }

*/
?>
<script>
	setTimeout(function() { console.log('') }, 1000);
</script>