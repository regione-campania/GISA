<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("../dal_include.php");
require_once("../dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
header('Content-Type: application/json; charset=UTF-8');
mb_internal_encoding("UTF-8");


$id = $_REQUEST['id_struttura'];
$anno = $_REQUEST['anno'];


if($id == 8){
	$query = "select * from matrix.vw_tree_nodes_up_asl  where anno = $anno order by path";
	
	$lev_start = -1;
}
else{
	$query = "select * from matrix.vw_tree_nodes_up_asl  where anno = $anno and id_asl = ".intval(intval($id) + 200) ." order by path";
	$lev_start = 1;

}
//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);


$lev_prec = $lev_start;
$json = '';
$i;
$j;
$count = 0;
foreach($arResults as $row) {
   if($lev_prec == $row['n_livello'] && $row['n_livello']!= $lev_start){
		$json = $json . '},'; 
   }else if ($lev_prec < $row['n_livello']){
	   $json = $json . ', "children": [  ';
	   $lev_prec = $row['n_livello'];
   }else if ( $row['n_livello']!= $lev_start){
	   for($i = $row['n_livello']; $i < $lev_prec  ; $i++){
			$json = $json . '}';
			for($j = $lev_start; $j < $i; $j++){
				$json = $json . '    ';				
			}
			$json = $json . ']';
	   }
	    $json  = $json .' },';
	    $lev_prec = $row['n_livello'];
   }
   $json = $json .'
   ';
   for($i = $lev_start; $i < $lev_prec ; $i++){
	   $json = $json .'    ';
   }
   $descr_b = str_replace('"',"",$row['descrizione_breve']);
   $descr = str_replace('"',"'",$row['descrizione']);
   
   $json2 = ' {"name":"'. $descr .'"'
    .' ,"id_struttura":"'. $row['id'] .'"'
    .' ,"id_asl":"'. $row['id_asl'] .'"'
   .' ,"descrizione_breve":"'. $descr_b .'"'
   .' ,"count":"'. $count .'"';
   $count++;
   $json = $json . $json2;
}
for ($i = $lev_start; $i < $lev_prec ; $i++){
	$json = $json . '} ]';
}
$json = $json . '} ';
$sss = json_encode($json); 


//echo $json;
echo $sss;

?>
