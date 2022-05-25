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


/*
$query = "select a.*,b.*, case when round(a.target::numeric,2) = round(b.impegnati::numeric,2) then 0 else 1 end  from (
  with recursive struttura_piani_from_leafs as
(
      select l.id, l.alias||' '||regexp_replace(descrizione_breve, '[^0-9a-zA-Z()_. -]', '', 'g') as descr_piano, parent, livello as level, path, coalesce(descrizione_breve, '') as descrizione_breve,
      fattore_ups, fattore_uba,coalesce(t.target,0) target_calc,coalesce(t.target,0)*fattore_ups target_ui,coalesce(t.target,0)*fattore_uba target_uba, l.color, l.path_ord, 1 as s
      from matrix.vw_struttura_piani l
        left outer join matrix.struttura_piano_target t on l.id = t.id_piano and t.id_struttura = $id
       where not exists (select  null from matrix.vw_struttura_piani x where x.parent = l.id)
   union all

      select c.id, c.alias||' '||regexp_replace(c.descrizione_breve, '[^0-9a-zA-Z()_. -]', '', 'g') as descr_piano, c.parent, c.livello, c.path, coalesce(c.descrizione_breve, '') as descrizione_breve,
      c.fattore_ups, c.fattore_uba,l.target_calc,l.target_ui,l.target_uba, c.color, c.path_ord ,1 as s
        from      struttura_piani_from_leafs l
             join matrix.vw_struttura_piani c on l.parent = c.id
       where c.id is not null 
) select id,descr_piano,parent,level,path,descrizione_breve, fattore_ups, fattore_uba,sum( target_calc) as target, sum( target_ui) as target_ups, sum(coalesce(target_uba,0)) as target_uba, color, path_ord, sum(s)
from struttura_piani_from_leafs l  group by 1,2,3,4,5,6,7,8,12,13
) a join

(
  with recursive struttura_piani_from_leafs as
(
      select l.id, parent,
      fattore_ups, fattore_uba,coalesce(t.target,0) target_calc,coalesce(t.target,0)*fattore_ups target_ui,coalesce(t.target,0)*fattore_uba target_uba, 
      coalesce(l.id_formula_ups,0) as id_formula_ups, coalesce(l.id_formula_uba,0) as id_formula_uba, l.color
        from matrix.vw_struttura_piani l
        left outer join matrix.struttura_piano_target t on l.id = t.id_piano and t.id_struttura in (select id from matrix.struttura_asl where id_padre= $id )
       where not exists (select  null from matrix.vw_struttura_piani x where x.parent = l.id)
   union all

      select c.id, c.parent,
      c.fattore_ups, c.fattore_uba,l.target_calc,l.target_ui,l.target_uba, c.id_formula_ups, c.id_formula_uba, l.color
        from      struttura_piani_from_leafs l
             join matrix.vw_struttura_piani c on l.parent = c.id
       where c.id is not null 
) select id, fattore_ups, fattore_uba,sum( target_calc) as impegnati ,sum( target_ui) as impegnati_ups ,sum( target_uba) as impegnati_uba, coalesce(id_formula_ups,0) as id_formula_ups, coalesce(id_formula_uba,0) as id_formula_uba
from struttura_piani_from_leafs l  group by 1,2,3,7,8
) b on b.id=a.id order by path_ord";*/

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
join matrix.struttura_piani pp on pp.id = p.id
left join matrix.formule f_ups on p.id_formula_ups = f_ups.id 
left join matrix.formule f_uba on p.id_formula_uba = f_uba.id 
where p.anno = $anno
order by p.path_ord";


$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;

$i=0;
foreach($arResults as $row){
	$responce->piano[$i]['id_piano'] = $row['id'];
	$responce->piano[$i]['target'] = $row['target'];
	$responce->piano[$i]['valore'] = $row['impegnati'];
	$responce->piano[$i]['ups_impegnati'] = $row['impegnati_ups'];
	$responce->piano[$i]['uba_impegnati'] = $row['impegnati_uba'];
	$responce->piano[$i]['ups_target'] = $row['target_ups'];
	$responce->piano[$i]['uba_target'] = $row['target_uba'];
	$responce->piano[$i]['case'] = $row['case'];
	$i++;
}


//echo " ".(round(microtime(true) * 1000))-$millis;

//echo $json;
$sss = json_encode($responce);
echo $sss;

?>
