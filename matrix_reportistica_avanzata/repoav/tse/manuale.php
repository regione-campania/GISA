<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
ini_set('max_execution_time', '0'); // for infinite time of execution 
ini_set('memory_limit', '512M');
header('Cache-Control: max-age=0');

require_once("../../dal_include.php");
require_once("../../dal_connessione.php");

$anno = $_GET['anno'];

$results = pg_query("select  descr, upper(i.alias_indicatore) as alias, motivo from ra.config_adhoc ci
join \"Analisi_dev\".vw_dpat_indicatore_new_attivi i on i.cod_raggruppamento = ci.cod_raggruppamento and anno = $anno
join \"Analisi_dev\".vw_dpat_piano_attivita_new_attivi p on p.id = i.id_piano_attivita 
join \"Analisi_dev\".vw_dpat_sez_new_attivi s on p.id_sezione = s.id
order by
motivo, s.ordinamento, p.ordinamento, i.ordinamento");
$arResults = CaricaArray($results);


?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang=it><head>
<title>Manuale Rendicontazioni TSE</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<style>
    .header {
        top: 0px;
        padding: 3px;
        text-align: center;
        margin-bottom: 10px;
        background-image: linear-gradient(to bottom right, #80bfff, #0073e6);
        color: white;
        font-size: 6px;
        box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 10px 0 rgba(0, 0, 0, 0.19);
}
th {
    text-align:center
}
</style>
</head>
<body>
    <div class="header">
		<h3>Manuale Rendicontazioni TSE</h3>		
    </div> 
    <div style="padding: 3px">
    Di seguito solo riportati i piani/attività rendcontati nella sezione Rendicontazioni TSE della Reportistica Avanzata, e il dettaglio dei loro conteggi.
<br> 
    Si può navigare per Piani/Attivita' DPAT richiesti e selezionando un range di date (default anno corrente; data inizio e fine devono essere comprese nello stesso anno).
    </div>
<table border="1" style="margin: 4px">
<?php

echo "<tr> <th> Piano/Attività </th> <th> Conteggio </th> </tr>";

$i = 0;
foreach($arResults as $row){
    $descr = "";
    if($row['motivo'] == 't')
        $descr = "  Numero controlli ufficiali inseriti nel sistema GISA";
    else 
        $descr = $row['descr'];    
	echo "<tr><td > ".$row['alias']." </td> <td>   $descr </td></tr>";
	$i++;
}




?>
</table>
</body></html>


