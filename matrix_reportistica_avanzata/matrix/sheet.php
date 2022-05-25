<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
require_once("dal_connessione.php");
header ("Content-Type: application/vnd.ms-excel");
mb_internal_encoding("UTF-8");

$id = $_REQUEST['id'];
$strutt = $_REQUEST['strutt'];
$anno = $_REQUEST['anno'];
date_default_timezone_set('Italy/Rome');
$now = date('m/d/Y h:i:s a', time());
$filename="MATRIX-".$strutt.".xls";
header ("Content-Disposition: inline; filename=$filename");


$query = "select unaccent(descrizione_breve) as descr_s from matrix.vw_tree_nodes_asl_descr vtnad where p_id = $id order by descrizione";

$results = pg_query($query);
$arResults2 = CaricaArray($results);


$query = "
select * from (
select 
coalesce(p.alias,'')||' '||unaccent(p.descrizione_breve) as descr_piano,
coalesce(t.sum,0) as target,
coalesce(i.sum,0) as impegnati,
p.livello as level,
p.color as color,
null as uo,
p.path_ord ,
t.id_struttura,
1 as q,
a.descrizione as descrizione_asl
from matrix.vw_tree_nodes_up_piani p
join matrix.vw_tree_nodes_asl_descr a on a.id = $id and p.anno = $anno
left join matrix.vw_struttura_piano_target_tree t on p.id_node = t.id_node_ref and t.id_struttura = $id
left join matrix.vw_struttura_piano_impegnato_tree i on p.id_node = i.id_node_ref and i.id_struttura = $id

union 

select 
coalesce(p.alias,'')||' '||unaccent(p.descrizione_breve) as descr_piano,
null as target,
null as impegnati,
p.livello as level,
p.color as color,
coalesce(i.sum,0) as uo,
p.path_ord,
a.id,
2 as q,
a.descrizione as descrizione_asl
from matrix.vw_tree_nodes_up_piani p
join matrix.vw_tree_nodes_asl_descr a on a.id in (select id from matrix.vw_tree_nodes_asl_descr vtnad where p_id = $id) and p.anno = $anno
left join matrix.vw_struttura_piano_target_tree i on i.id_struttura = a.id and p.id_node = i.id_node_ref 
) p
order by p.path_ord, q, p.descrizione_asl 
";

$results = pg_query($query);
$arResults3 = CaricaArray($results);


?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang=it><head>
<title>Titolo</title>
<style>
.num {
  mso-number-format:"\#\,\#\#0\.00";
}
</style>
</head>
<body>
<table border="1">
<?php
//echo "<tr> <td> $now </td> </tr>";
echo "<tr>
		<td> </td>
				<td style=\"font-weight:bold; font-size: 20px\"> $strutt</td>
					<td style=\"font-weight:bold; font-size: 20px\"></td>
							<td style=\"font-weight:bold; font-size: 20px\"></td>
									<td width=\"750\" style=\"font-weight:bold; font-size: 20px\"></td>
										<td style=\"font-weight:bold; font-size: 20px\">TOTALE</td>
													<td style=\"font-weight:bold; font-size: 20px\"></td>
														<td style=\"font-weight:bold; font-size: 20px\">SEZIONE </td>
														<td style=\"font-weight:bold; font-size: 20px\"> </td>
														<td style=\"font-weight:bold; font-size: 20px\">TIPO </td>
														<td style=\"font-weight:bold; font-size: 20px\"> </td>
														<td style=\"font-weight:bold; font-size: 20px\">PIANO/ATT </td>
														<td style=\"font-weight:bold; font-size: 20px\"> </td>
														<td style=\"font-weight:bold; font-size: 20px\">INDICATORE </td>
														<td style=\"font-weight:bold; font-size: 20px\"> </td>
													</tr>";


echo "<tr>
		<td> $now </td>
				<td style=\"font-weight:bold; font-size: 20px\">SEZIONE</td>
					<td style=\"font-weight:bold; font-size: 20px\">TIPO</td>
							<td style=\"font-weight:bold; font-size: 20px\">NOME PIANO/ATT</td>
									<td width=\"750\" style=\"font-weight:bold; font-size: 20px\">INDICATORE</td>
										<td style=\"font-weight:bold; font-size: 17px\">PROGRAMMATI</td>
													<td style=\"font-weight:bold; font-size: 17px\">DISTRIBUITI</td>
														<td style=\"font-weight:bold; font-size: 17px\">PROGRAMMATI </td>
														<td style=\"font-weight:bold; font-size: 17px\">DISTRIBUITI </td>
														<td style=\"font-weight:bold; font-size: 17px\">PROGRAMMATI </td>
														<td style=\"font-weight:bold; font-size: 17px\">DISTRIBUITI </td>
														<td style=\"font-weight:bold; font-size: 17px\">PROGRAMMATI </td>
														<td style=\"font-weight:bold; font-size: 17px\">DISTRIBUITI </td>
														<td style=\"font-weight:bold; font-size: 17px\">PROGRAMMATI </td>
														<td style=\"font-weight:bold; font-size: 17px\">DISTRIBUITI </td>";
														//</tr>";
$dip_count = 0;
foreach($arResults2 as $row){
	$dip_count++;
	$s = $row['descr_s'];
	echo "<td style=\"font-weight:bold; font-size: 15px\"> $s </td>";
	
}
echo "</tr>";

$n_cols = 13 + $dip_count;
$old_p = -999;
foreach($arResults3 as $row) {
	$p = $row['descr_piano'];
	if ($p != $old_p){
		$old_p = $p;
		echo "</tr> <tr>";
		if($row['target'] != null){
			for ($i=0;$i < 13; $i++){
				$piano = strtoupper ($row['descr_piano']);
				$color = $row['color'];
				if($piano == null)
					$piano = $row['descr_piano'];
				if($row['level']==$i)
					echo "<td style=\"background-color: $color\">$piano</td>";
				else if ($i < 4)
					echo "<td style=\"background-color: $color\"> </td>";
				else if($i >=4)
					echo "<td style=\"background-color: #d9d9d9\"> </td>";

				
				$target = $row['target'];
				$impegnati = $row['impegnati'];
				
				if($row['level']*2+4==$i)
					echo "<td class=\"num\" style=\"background-color: #d9d9d9\"> $target </td>";
				
				if($row['level']*2+4==$i)
					echo "<td class=\"num\" style=\"background-color: #d9d9d9\"> $impegnati </td>";
				

			}
		}
		//else{
		//	$valore = $row['uo'];
		//	echo "<td class=\"num\" style=\"background-color: #fff0b3\"> $valore </td>";
		//}

		//$valore = $row['uo'];
		//echo "<td class=\"num\" style=\"background-color: #fff0b3\"> $valore </td>";
	}else{
		//for ($i=13;$i < $n_cols; $i++){
			$valore = $row['uo'];
			echo "<td class=\"num\" style=\"background-color: #fff0b3\"> $valore </td>";
		//}
	   // echo "</tr>";

	}

}

echo "</tr>";
/*for ($i=1;$i < 11; $i++)
{
   echo "<tr>";
   for ($j=1; $j<11;$j++)
   {
      $a = $i * $j;
      echo "<td>$a</td>";
   }
   echo "</tr>";
}*/
?>
</table>
</body></html>


