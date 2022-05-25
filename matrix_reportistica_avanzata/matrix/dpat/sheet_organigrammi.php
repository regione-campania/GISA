<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
require_once("dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
header ("Content-Type: application/vnd.ms-excel");
mb_internal_encoding("UTF-8");

$id = $_REQUEST['id_asl'];
$year = $_CONFIG["mod4_year"];
/*if($_REQUEST['anno'] != null)
    $year = $_REQUEST['anno'];*/

$filename="Organigramma-$id.xls";
header ("Content-Disposition: inline; filename=$filename");



$query = "select a.descrizione_breve, a.n_livello, a.id_asl,
vv.ups as ups_struttura, vv.uba as uba_struttura, s.fattore1 as fattore1_struttura, s.fattore2 as fattore2_struttura, s.sottr as sottr_struttura,
n.nominativo, n.codice_fiscale, n.qualifica, mn.* from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl A on A.id = t.id_node
				join (select id_node, sum(coalesce(m.ups,0)) as ups, sum(coalesce(m.uba,0)) as uba from matrix.vw_tree_nodes_down_asl v join matrix.struttura_asl a on v.id_node_ref = a.id left 
				join matrix.mod4_strutture m on a.id_gisa = m.id_struttura group by 1) vv on vv.id_node = t.id_node
	 			left join matrix.nominativi_struttura nm on nm.id_struttura = a.id_gisa
	 			left join matrix.mod4_nominativi mn on mn.id_nominativo_struttura = nm.id
	 			left join matrix.nominativi n on n.id = nm.id_nominativo
	 			left join matrix.mod4_strutture s on s.id_struttura = a.id_gisa 

				where a.id_asl = $id and a.n_livello > 1
                 order by path";
                 
$query = "	
select a.descrizione_breve, a.n_livello, a.id_asl, coalesce(vv.uba,0) as uba_struttura, coalesce(vv.ups,0) as ups_struttura, 
s.fattore1 as fattore1_struttura, s.fattore2 as fattore2_struttura, s.sottr as sottr_struttura,
n.nominativo, n.codice_fiscale, n.qualifica, mn.*, coalesce(mn.ups, 0) as ups_nom, coalesce(mn.uba, 0) as una_nom
from matrix.vw_tree_nodes_up_asl T join matrix.struttura_asl A on A.id = t.id_node
join (select id_node_ref, sum(coalesce(m.ups,0)) as ups, sum(coalesce(m.uba,0)) as uba 
    from matrix.vw_tree_nodes_down_asl v join matrix.struttura_asl a on v.id_node = a.id 
    left join matrix.mod4_strutture m on a.id_gisa = m.id_struttura group by 1) vv on vv.id_node_ref = t.id_node 
left join matrix.nominativi_struttura nm on nm.id_struttura = a.id_gisa
left join matrix.mod4_nominativi mn on mn.id_nominativo_struttura = nm.id
left join matrix.nominativi n on n.id = nm.id_nominativo
left join matrix.mod4_strutture s on s.id_struttura = a.id_gisa 

where a.anno = $year and a.id_asl = $id and a.n_livello  >1

order by path
             ";

$res = pg_query($query);
$ar = CaricaArray($res);

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
echo "<tr>
<td style=\"font-weight:bold; \"> UOC </td>
                    <td style=\"font-weight:bold; \">UOS</td>
                                            <td style=\"font-weight:bold; \">NOMINATIVO</td>
                                                <td style=\"font-weight:bold; \">CF </td>
                                                <td style=\"font-weight:bold; \">QUALIFICA </td>
                                             </tr>
                                            ";


$old = "";
$oldlevel = -1;
$livelli = ['Nessuno', 'Ispezioni negli stabilimenti', 'Audit negli stabilimenti', 'Audit interni'];
foreach($ar as $row){

    $carico_annuale2 = intval($row['carico_annuale'] - ($row['carico_annuale']/100*$row['perc_sottr']));
    $carico_annuale2uba = intval($row['carico_annuale'] * 4 * $row['uba_ora'] - (($row['carico_annuale'] * 4 * $row['uba_ora'])/100*$row['perc_sottr']));
    
    $carico_annuale3 = intval($carico_annuale2 - ($carico_annuale2/100*$row['perc_sottr2']));
    $carico_annuale3uba = intval($carico_annuale2uba - ($carico_annuale2uba/100*$row['perc_sottr2']));

    if($row['livello_formativo'] != null ){
        $row['livello_formativo'] = $livelli[$row['livello_formativo']];
    }

    $fatt = "";
    $fatt_struttura = ""; 

    if($row['fattore1'] == 't')
        $fatt = $fatt."1.Eventuale insufficienza del numero di amministrativi afferenti alla struttura,      ";
    if($row['fattore2'] == 't')
        $fatt = $fatt."2.Eventuale insufficienza delle risorse strumentali in rapporto alle risorse umane presenti,      ";
    if($row['fattore3'] == 't')
        $fatt = $fatt."3.Caratteristiche geo-morfologiche del territorio    ";


    if($row['fattore1_struttura'] == 't')
        $fatt_struttura = $fatt_struttura."1.Condizioni socio-economiche del territorio,     ";
    if($row['fattore2_struttura'] == 't')
        $fatt_struttura = $fatt_struttura."2.Presenza di particolari problematiche  di natura sanitaria e/o ambientali     ";

    if($row['n_livello'] == 2){
        echo '<tr>
                <td style="background-color: #b3b3ff"> '.$row['descrizione_breve'].' </td>
            </tr>';
    }else if($row['n_livello'] == 3 && ($row['descrizione_breve'] != $old || $oldlevel != $row['n_livello'])){
        echo '<tr>
                <td style="background-color: #ccccff"> </td>
                <td style="background-color: #ccccff"> '.$row['descrizione_breve'].' </td>
             </tr>';

       

        echo '<tr>
             <td style="background-color: #e6e6ff"> </td>
             <td style="background-color: #e6e6ff"> </td>
             <td style="background-color: #e6e6ff"> '.$row['nominativo'].' </td>
             <td style="background-color: #e6e6ff"> '.$row['codice_fiscale'].' </td>
             <td style="background-color: #e6e6ff"> '.$row['qualifica'].' </td>
          </tr>';
        
    }else if($row['n_livello'] == 3 && $row['descrizione_breve'] == $old){
        echo '<tr>
        <td style="background-color: #e6e6ff"> </td>
        <td style="background-color: #e6e6ff"> </td>
        <td style="background-color: #e6e6ff"> '.$row['nominativo'].' </td>
        <td style="background-color: #e6e6ff"> '.$row['codice_fiscale'].' </td>
        <td style="background-color: #e6e6ff"> '.$row['qualifica'].' </td>
          </tr>';
    }
    $old = $row['descrizione_breve'];
    $oldlevel = $row['n_livello'];
}
?>
</table>
</body></html>