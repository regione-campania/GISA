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


//$query = "select a.id as id_a, * from struttura_matrix a left outer join struttura_piano_target t on a.id = t.id_struttura  where parent = 576";
$query = "select * from matrix.vw_tree_nodes_asl_descr a left outer join matrix.lock_asl l on a.id_asl = l.asl and l.anno = $anno where a.id = $id";
//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);

$i = 0;
foreach($arResults as $row) {
	$responce->strutt[$i]['id'] = $row['id'];
	$responce->strutt[$i]['descr'] = $row['descrizione_breve'];
	$responce->strutt[$i]['parent'] = $row['p_id'];
	$responce->strutt[$i]['livello'] = $row['n_livello'];
	$responce->strutt[$i]['id_asl'] = $row['id_asl'];
	$responce->strutt[$i]['is_locked'] = $row['is_locked'];

	$i++;
}
//echo $query;
$sss = json_encode($responce); 
//echo $json;
echo $sss;

?>
