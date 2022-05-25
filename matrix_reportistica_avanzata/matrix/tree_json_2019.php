<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
require_once("dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
//header('Content-Type: application/json; charset=UTF-8');
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


$query = "select 
coalesce(p.alias,'')||' '||p.descrizione_breve as descr_piano,
p.id,
t.sum as target,
i.sum as impegnati,
t.target_ups,
t.target_uba,
i.target_ups as impegnati_ups,
i.target_uba as impegnati_uba,
p.livello as level,
p.color as color,
case when round(t.sum::numeric,2) = round(i.sum::numeric,2) then 0 else 1 end,
f_uba.fattore_fin as fattore_uba,
f_ups.fattore_fin as fattore_ups,
p.id_formula_ups,
p.id_formula_uba
from matrix.vw_tree_nodes_up_piani p
left join matrix.vw_struttura_piano_target_tree t on p.id_node = t.id_node_ref and t.id_struttura = $id
left join matrix.vw_struttura_piano_impegnato_tree i on p.id_node = i.id_node_ref and i.id_struttura = $id
join matrix.struttura_piani pp on pp.id = p.id and (pp.data_scadenza > current_timestamp or pp.data_scadenza is null)
left join matrix.formule f_ups on p.id_formula_ups = f_ups.id 
left join matrix.formule f_uba on p.id_formula_uba = f_uba.id 
where p.anno = $anno
order by p.path_ord";


//echo $query;

$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;

$levels = [];
$count = 0;
foreach($arResults as $row){
	$l = $row['level'];
	$levels[$l] = $count;
//	echo $l."= ".$levels[$l]."  ".$count."\n";
	if($row['case']==1){
		for($i=0; $i<$l; $i++){
			//echo "\t".$i."  ".$levels[$i]."\n";
		//	echo "\t".$arResults[$levels[$i]]['case']."\n";
			$arResults[$levels[$i]]['case'] = 1;
			//echo "\t".$arResults[$levels[$i]]['case']."\n";
		}
	}
	$count++;
}


$lev_prec = 0;
$json = '';
$i;
$j;
$count = 0;
foreach($arResults as $row) {
   if($lev_prec == $row['level'] && $row['level']!= 0){
		$json = $json . '},'; 
   }else if ($lev_prec < $row['level']){
	   $json = $json . ', "children": [  ';
	   $lev_prec = $row['level'];
   }else if ( $row['level']!= 0){
	   for($i = $row['level']; $i < $lev_prec  ; $i++){
			$json = $json . '}
';
			for($j = 0; $j < $i; $j++){
				$json = $json . '    ';				
			}
			$json = $json . ']';
	   }
	    $json  = $json .' },';
	    $lev_prec = $row['level'];
   }
   $json = $json .'
   ';
   for($i = 0; $i < $lev_prec ; $i++){
	   $json = $json .'    ';
   }
   $descr = str_replace('"',"'",$row['descr_piano']);
   $descr = str_replace (array("\r\n", "\n", "\r"), '', $descr);
   $json2 = ' {"name":"'. $descr .'"'
    .' ,"id_piano":"'. $row['id'] .'"'
    .' ,"target":"'. $row['target'] .'"'
   .' ,"valore":"'. $row['impegnati'] .'"'
    .' ,"fattore_ups_reale":"'. $row['fattore_ups'] .'"'
    .' ,"fattore_uba_reale":"'. $row['fattore_uba'] .'"'
    .' ,"ups_target":"'. $row['target_ups'] .'"'
    .' ,"uba_target":"'. $row['target_uba'] .'"'
    .' ,"ups_impegnati":"'. $row['impegnati_ups'] .'"'
   .' ,"uba_impegnati":"'. $row['impegnati_uba'] .'"'
   .' ,"level":"'. $row['level'] .'"'
   .' ,"path":"'. $row['path'] .'"'
   .' ,"id_formula_ups":"'. $row['id_formula_ups'] .'"'
   .' ,"id_formula_uba":"'. $row['id_formula_uba'] .'"'
   .' ,"color":"'. $row['color'] .'"'
   .' ,"case":"'. $row['case'] .'"'
   .' ,"count":"'. $count .'"';
   $count++;
   $json = $json . $json2;
}
for ($i = 0; $i < $lev_prec ; $i++){
	$json = $json . '} ]';
}
$json = $json . '} ';
$sss = json_encode($json); 

//echo " ".(round(microtime(true) * 1000))-$millis;

//echo $json;
echo $sss;

?>
