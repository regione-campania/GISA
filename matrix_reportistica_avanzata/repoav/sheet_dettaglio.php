<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
ini_set('memory_limit', '2048M');
require_once("../dal_include.php");
require_once("../dal_connessione.php");
header('Cache-Control: max-age=0');
header ("Content-Type: application/vnd.ms-excel; charset=utf-8");

$query = $_REQUEST['q'];
$d1 = $_REQUEST['d1'];
$d2 = $_REQUEST['d2'];
$d3 = $_REQUEST['d3'];
$d4 = $_REQUEST['d4'];

//$query = 'select cu.id_controllo_ufficiale, cu.data_inizio_controllo, cu.unita_operativa,  cu.asl, cu.tipo_ispezione_o_audit, cu.codice_macroarea, cu.codice_aggregazione, cu.codice_attivita					from   matrix.struttura_piani p 					 join  matrix.struttura_asl a  on  p.parent = -1 and  a.id_padre = 8					left join ( SELECT g.*,						 p.id_ref as p_id_ref,						 a.id_ref as a_id_ref,						 mr.id_ref as m_id_ref						 FROM "Analisi".gisa_controlli_ufficiali g						 left join "Analisi".mvw_stabilimenti_codici_rel s on s.id_record_anagrafica = g.id_record_anagrafica						  left  join matrix.vw_macroarea_ref mr on mr.id = s.id and mr.id_ref   = -1						  join matrix.vw_struttura_piani_ref p on p.id = g.id_indicatore and p.id_ref  in (select id from matrix.struttura_piani where parent = -1)						   left  join matrix.struttura_asl_ref a on  g.id_struttura_uo = a.id and a.id_ref  in (select id from matrix.struttura_asl where id_padre = 8)						  where 1=1					 ) cu on  p.id = cu.p_id_ref  and  a.id = cu.a_id_ref 					 left join matrix.vw_target t on t.id_piano = cu.p_id_ref and cu.a_id_ref = t.id_struttura 					  where  1=1 order by id_controllo_ufficiale;';

date_default_timezone_set('Italy/Rome');
$now = date('m/d/Y h:i:s a', time());
$filename="Dettaglio_RA.xls";
header ("Content-Disposition: inline; filename=$filename");

function n2l($n)
{
    for($r = ""; $n >= 0; $n = intval($n / 26) - 1)
        $r = chr($n%26 + 0x41) . $r;
    return $r;
}

$query = str_replace("order by path_ord", "order by 1,3", $query);

echo "<script> console.log($query); </script>";

pg_query('set lc_time to \'it_IT.utf8\';');

$results = pg_query($query);
$arResults = CaricaArray($results);

?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang=it><head>
<title>Titolo</title>
<style>
.num {
  mso-number-format:"\#\,\#\#0;
}

.per {
	mso-number-format:"Percent";
}

.text {
	white-space: nowrap;
	mso-number-format:"\@";/*force text*/
}

</style>
</head>
<body>
<table border="1">
<?php

$n_col = 8;

echo "<tr> <td style=\"background-color: #fff0b3\" colspan = \"$n_col\" class=\"text\"> Data di esportazione: $now </td> </tr>";
echo "<tr> <td style=\"background-color: #fff0b3\" colspan = \"$n_col\" class=\"text\"> $d3 </td> </tr>";
echo "<tr> <td style=\"background-color: #fff0b3\" colspan = \"$n_col\" class=\"text\"> $d4 </td> </tr>";
echo "<tr> <td style=\"background-color: #fff0b3\" colspan = \"$n_col\" class=\"text\"> $d1 </td> </tr>";
echo "<tr> <td style=\"background-color: #fff0b3\" colspan = \"$n_col\" class=\"text\"> $d2 </td> </tr>";

echo "<tr> <td style=\"background-color: #d9d9d9\"> ID CONTROLLO </td> <td style=\"background-color: #d9d9d9\"> ID CAMPIONE </td>
		 <td style=\"background-color: #d9d9d9\"> DATA </td> <td style=\"background-color: #d9d9d9\"> ASL </td> 
		<td style=\"background-color: #d9d9d9\"> UO </td> <td style=\"background-color: #d9d9d9\"> PIANO/ATTIVITA' </td> 
		<td style=\"background-color: #d9d9d9\"> CODICE LINEA </td>
		<td style=\"background-color: #d9d9d9\"> RAGIONE SOCIALE </td>
		</tr>";


$i = 0;
foreach($arResults as $row){
	$id = $row['id_controllo_ufficiale'];
	$id_c = $row['id_campione'];
	$data = $row['data_inizio_controllo'];
	$uo = $row['unita_operativa'];
	$asl = $row['asl'];
	$piano = strtoupper($row["tipo_ispezione_o_audit"]);
	$codice_linea = $row['codice_linea'];
	$ragione_sociale = $row['ragione_sociale'];
	if($codice_linea == 'tot')
		$codice_linea = '';
	echo "<tr class=\"text\"> <td> $id </td>  <td> $id_c </td> <td> $data </td> <td> $asl </td>  <td> $uo </td> <td> $piano </td>  <td class=\"text\"> $codice_linea </td> <td class=\"text\"> $ragione_sociale </td> </tr>";
	$i++;
}




?>
</table>
</body></html>


