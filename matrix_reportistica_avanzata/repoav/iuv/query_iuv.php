<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("../../dal_include.php");
require_once("../../dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
header('Content-Type: application/json; charset=UTF-8');
mb_internal_encoding("UTF-8");

$query = $_REQUEST['q'];

$session = $_REQUEST['session'];

//echo $query;

//$query = 'select  data_inizio_controllo_m||data_inizio_controllo_m_text as riga, asl as colonna, count(*) as valore from "Analisi".vw_conteggio_controlli_motivo a
//where id_indicatore = 7018 group by 1,2 order by 1,2';

//$query = 'select  data_inizio_controllo_m||data_inizio_controllo_m_text as riga, asl as colonna , id_asl as id_c, count(*) as valore from "Analisi".vw_conteggio_controlli_motivo a
//where id_indicatore = 7018 group by 1,2,3 order by 1,2';

//$query = 'select  data_inizio_controllo_q as riga, asl as colonna , id_asl as id_c, count(*) as valore from "Analisi".vw_conteggio_controlli_motivo a
//group by 1,2,3 order by 1,2';

$updSession = "insert into ra.session_log values ('".$session."_iuv', current_timestamp)
				on conflict (id)
				do update set last_timestamp = current_timestamp;";
pg_query($updSession);
$results = pg_query($query);
$arResults = CaricaArray($results);

$i = 0;
foreach($arResults as $row) {
	$responce->iuv[$i]['riga'] = $row['riga'];
	$responce->iuv[$i]['colonna'] = $row['colonna'];
	$responce->iuv[$i]['target'] = round($row['sum'],3);
	$responce->iuv[$i]['valore'] = $row['eseguiti'];
	$responce->iuv[$i]['id_c'] = $row['id_c'];
	$responce->iuv[$i]['id_r'] = $row['id_r'];

	if($row['sum'] != 0)
		$perc = 100/$row['sum']*$row['eseguiti'];
	else
		$perc = 0;
	
	$responce->iuv[$i]['perc'] = round($perc, 3);

	$i++;
}

$json = json_encode($responce); 

echo $json;

pg_close($connessione);

?>
