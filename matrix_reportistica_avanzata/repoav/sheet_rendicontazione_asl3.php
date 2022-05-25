<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
ini_set('max_execution_time', '0'); // for infinite time of execution 
ini_set('memory_limit', '512M');
header('Cache-Control: max-age=0');
mb_internal_encoding("UTF-8");

require_once("../dal_include.php");
require_once("../dal_connessione.php");
header ("Content-Type: application/vnd.ms-excel; charset=utf-8");

$query = $_REQUEST['q'];

$d1 = $_REQUEST['d1'];

$d2 = $_REQUEST['d2'];

$d3 = $_REQUEST['d3'];

$d4 = $_REQUEST['d4'];

$filename = $_REQUEST['filename'];

date_default_timezone_set('Italy/Rome');
$now = date('d/m/Y h:i:s a', time());
if($filename == null)
	$filename="RepoAvanzata.xls";
header ("Content-Disposition: inline; filename=$filename");

function n2l($n)
{
    for($r = ""; $n >= 0; $n = intval($n / 26) - 1)
        $r = chr($n%26 + 0x41) . $r;
    return $r;
}

//$query = str_replace("order by path_ord", "order by 1,3", $query);

$tokens = explode('order by', $query);
$query = $tokens[0].' order by coalesce(ordinamento,a.colonna), 1';

//echo "<script> console.log($query); </script>";

$results = pg_query($query);
$arResults = CaricaArray($results);

$cols = array();
foreach($arResults as $row){
	array_push($cols, $row['colonna']);
}
$cols = array_unique($cols);
$n_col = count($cols)*4 + 1;

$rows = array();
foreach($arResults as $row){
	array_push($rows, $row['riga']);
}
$rows = array_unique($rows);
$n_row = count($rows) + 8;



?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang=it><head>
<meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">
<title>Titolo</title>
<style>
.num {
  mso-number-format:"\#\,\#\#0;
}

.per {
	mso-number-format:"Percent";
}
</style>
</head>
<body>
<table border="1">
<?php
echo "<tr> <td> Data di esportazione: $now </td> </tr>";
echo "<tr> <td> $d3 </td> </tr>";
if($_REQUEST['filename'] == null) //rednicontazione
	echo "<tr> <td> $d4 </td> </tr>";
echo "<tr> <td> $d1 </td> </tr>";
echo "<tr> <td> $d2 </td> </tr>";

//echo "<tr> <td> $now </td> </tr>";

if($_REQUEST['filename'] != null){ //rednicontazione
	echo "<tr> 
		<td style=\"background-color: #d9d9d9\"> </td>
		<td style=\"background-color: #d9d9d9\"> </td>
		<td style=\"background-color: #d9d9d9\"> </td>";
	$i = 0;
	foreach($rows as $c ){
        echo " <td style=\"text-align: center; background-color: #d9d9d9\"> $c </td>";
    }
	echo "<td style=\"background-color: #a6a6a6\"> TOTALE </td> 
		</tr>";
}

$oldPadre = "";
$oldIndicatore = "";
$n_asl = count($rows);


$arraySumProgrammati = array(); 
$arraySumEseguiti = array();

foreach($rows as $asl){
	$arraySumProgrammati[$asl] = 0;
	$arraySumEseguiti[$asl] = 0;
}

$count = 0;
foreach($arResults as $row){
	$count++;

	if($count == $row['count_padre']){
		echo "<td rowspan='3' style=\"text-align: center;background-color: #a6a6a6\">Totale</td><td rowspan='3' style=\"text-align: center;background-color: #a6a6a6\"></td><td style=\"background-color: #a6a6a6\">Eseguiti</td>";
		$sumEseguitiTot = 0;
		foreach($rows as $c){
			$sumEseguitiTot += $arraySumEseguiti[$c];
			echo '<td>'.$arraySumEseguiti[$c].'</td>';
		//	$arraySumEseguiti[$c] = 0;
		}
		echo '<td style="background-color: #ffe066">'.$sumEseguitiTot.'</td></tr>';

		echo "<td style=\"background-color: #a6a6a6\">Programmati</td>";
		$sumProgrammatiTot = 0;
		foreach($rows as $c){
			$sumProgrammatiTot += $arraySumProgrammati[$c];
			echo '<td>'.$arraySumProgrammati[$c].'</td>';
		//	$arraySumProgrammati[$c] = 0;
		}
		echo '<td style="background-color: #ffe066">'.$sumProgrammatiTot.'</td></tr>';

		echo "<td style=\"background-color: #a6a6a6\">%</td>";
		foreach($rows as $c){
			echo '<td>'.intval(100/$arraySumProgrammati[$c]*$arraySumEseguiti[$c]).'</td>';
			$arraySumEseguiti[$c] = 0;
			$arraySumProgrammati[$c] = 0;
		}
		echo '<td style="background-color: #ffe066">'.intval(100/$sumProgrammatiTot*$sumEseguitiTot).'</td></tr>';
		echo "<tr><td></td></tr>";
		$count = 0;
	}

	$newRow = false;
	if($row["colonna_padre"] != $oldPadre){
		echo "<tr>";
		$newRow = true;

		echo '<td style="background-color: #d9d9d9; vertical-align:middle" rowspan="'.((intval($row["count_padre"])/$n_asl)*4).'">'.$row['colonna_padre'].'</td>';
		$oldPadre = $row['colonna_padre'];


	}
	if($row["colonna"] != $oldIndicatore){
		if(!$newRow){
			echo "<tr>";
			$newRow = true;
		}

		echo '<td style="background-color: #d9d9d9; vertical-align:middle"" rowspan="3">'.$row['colonna'].'</td> <td style="background-color: #d9d9d9">Eseguiti</td>';
		$sumEseguiti = 0;
		foreach($rows as $c ){//ciclo sulle asl
			$found = false;
			foreach($arResults as $row2){
				if($row2['colonna'] == $row['colonna'] && $row2['riga'] == $c){
					echo "<td>".$row2['valore']."</td>";
					$sumEseguiti += $row2['valore'];
					$arraySumEseguiti[$c] += $row2['valore'];
					$found = true;
					break;
				}
			}
			if(!$found){
				echo "<td>0</td>";
			}
		}
		echo "<td style=\"background-color: #fff0b3\">$sumEseguiti</td>";

		echo "<tr><td style=\"background-color: #d9d9d9\">Programmati</td>";
		$sumProgrammati = 0;
		foreach($rows as $c ){//ciclo sulle asl
			$found = false;
			foreach($arResults as $row2){
				if($row2['colonna'] == $row['colonna'] && $row2['riga'] == $c){
					echo "<td>".$row2['target']."</td>";
					$sumProgrammati += $row2['target'];
					$arraySumProgrammati[$c] += $row2['target'];
					$found = true;
					break;
				}
			}
			if(!$found){
				echo "<td>0</td>";
			}
		}
		echo "<td style=\"background-color: #fff0b3\">$sumProgrammati</td>";
		echo "</tr>";

		echo "<tr><td style=\"background-color: #d9d9d9\">%</td>";
		foreach($rows as $c ){//ciclo sulle asl
			$found = false;
			foreach($arResults as $row2){
				if($row2['colonna'] == $row['colonna'] && $row2['riga'] == $c){
					echo "<td>".intval(100/$row2['target']*$row2['valore'])."</td>";
					$sumProgrammati += $row2['target'];
					$sumEseguiti += $row2['valore'];
					$found = true;
					break;
				}
			}
			if(!$found){
				echo "<td>0</td>";
			}
		}
		echo "<td style=\"background-color: #fff0b3\">".intval(100/$sumProgrammati*$sumEseguiti)."</td>";
		echo "</tr>";
		echo "<tr><td></td></tr>";
		$oldIndicatore = $row['colonna'];
	}
}


?>
</table>
</body></html>


