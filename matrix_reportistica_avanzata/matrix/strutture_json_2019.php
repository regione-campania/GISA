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

$id = $_REQUEST['id_struttura'];
$anno = $_REQUEST['anno'];


if($id != 8)
  $query = "select * from matrix.vw_tree_nodes_down_asl_descr d
  join  matrix.vw_tree_anno t on d.id_node_ref = t.root_id where t.anno = $anno and name = 'Struttura ASL'  and id_asl = $id+200 and n_livello = 1";
else
	$query = "select * from matrix.vw_tree_nodes_down_asl_descr d
	join  matrix.vw_tree_anno t on d.id_node_ref = t.root_id where t.anno = $anno and name = 'Struttura ASL' and d.n_livello = 0";
	
$results = pg_query($query);
if(!$results)
  echo "KO: $query";
else{
  $arResults = CaricaArray($results);
  foreach($arResults as $row){
    $id = $row['id'];
  }
}


/* $query = "select a.descrizione_breve as descr_s, a.id as id_a, p.id as id_piano_p,  a.ups as ups_totali, a.uba as uba_totali, * from matrix.struttura_asl a 
left outer join matrix.vw_struttura_piani p on 1=1 
left outer join matrix.struttura_piano_target t on a.id = t.id_struttura and p.id = t.id_piano
where a.id_padre = $id order by descr_s"; */

$query = "select a.descrizione_breve as descr_s, 
a.id as id_a, 
p.id as id_piano_p, 
a.ups as ups_totali, 
a.uba as uba_totali,
target
from matrix.vw_tree_nodes_asl_descr a 
left outer join matrix.struttura_piani p on p.anno = a.anno
left outer join matrix.struttura_piano_target t on a.id = t.id_struttura and p.id = t.id_piano
where a.p_id = $id and a.id_asl > 200 order by descr_s";

//echo($query);

 // $millis = round(microtime(true) * 1000);
$results = pg_query($query);
$arResults = CaricaArray($results);



$i = 0;
foreach($arResults as $row) {
	$responce->strutt[$i]['id'] = $row['id_a'];
	$responce->strutt[$i]['descr'] = $row['descr_s'];
	$responce->strutt[$i]['target'] = $row['target'];
	$responce->strutt[$i]['id_piano'] = $row['id_piano_p'];
	$responce->strutt[$i]['ups_totali'] = $row['ups_totali'];
	$responce->strutt[$i]['uba_totali'] = $row['uba_totali'];
	$i++;
}




$sss = json_encode($responce); 
//echo $json;

//echo " ".(round(microtime(true) * 1000))-$millis;

echo $sss;



?>
