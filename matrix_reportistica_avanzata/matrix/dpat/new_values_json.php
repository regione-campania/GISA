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

$millis = round(microtime(true) * 1000);

$id = $_REQUEST['id_asl'];
$year = $_CONFIG["mod4_year"];


if($id == null || $id == "null"){
	/*$query = "select 'Struttura ASL', null, null, null, '9999/10008' as path, 10008 as id, null, 'REGIONE' as descrizione, 0 as n_livello_gisa, 'REGIONE' as descrizione_breve, 8 as id_gisa, null
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 201 
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 202
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 203
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 204 
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 205
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 206
	union
	select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = 207
	order by path";*/

	$query = "select a.id_gisa, coalesce(a.ups,0) as ups, coalesce(a.uba,0) as uba from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl A on A.id = t.id_node
	join (select id_node_ref, sum(coalesce(m.ups,0)) as ups, sum(coalesce(m.uba,0)) as uba from matrix.vw_tree_nodes_down_asl v join matrix.struttura_asl a on v.id_node_ref = a.id 
	left join matrix.mod4_strutture m on a.id_gisa = m.id_struttura group by 1) vv on vv.id_node_ref = t.id_node where a.anno = $year
	order by path";
	
	$lev_start = -1;
}
else{
	//$query = "select * from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl_2019 A on A.id = t.id_node where a.id_asl = ".intval(intval($id) + 200) ." order by path";
	$query = "select a.id_gisa, coalesce(a.ups,0) as ups, coalesce(a.uba,0) as uba from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl A on A.id = t.id_node
	join (select id_node_ref, sum(coalesce(m.ups,0)) as ups, sum(coalesce(m.uba,0)) as uba from matrix.vw_tree_nodes_down_asl v join matrix.struttura_asl a on v.id_node_ref = a.id 
	left join matrix.mod4_strutture m on a.id_gisa = m.id_struttura group by 1) vv on vv.id_node_ref = t.id_node where a.anno = $year and id_asl = $id
	order by path";
	$lev_start = 1;
}
//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;


$j=0;
foreach($arResults as $row) {
	$resp->data[$j]['ups'] = $row['ups'];
	$resp->data[$j]['uba'] = $row['uba'];
	$resp->data[$j]['id_struttura'] = $row['id_gisa'];
	$j++;
}

$sss = json_encode($resp); 

//echo " ".(round(microtime(true) * 1000))-$millis;

//echo $json;
echo $sss;

?>
