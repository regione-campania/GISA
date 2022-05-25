<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
require_once("dal_connessione.php");
include_once('Xlsx/PHPExcel/IOFactory.php');

$inputFileNames = array(
					'Mod 4 2019  ASL BN.xlsx',
					'Mod 4 2019  ASL CASERTA definitivo.xlsx',
					'Mod 4 2019 ASL NA 3 SUD deinitivo.xlsx',
					'Mod 4 ASL NA 2 nord 2019 definitivo.xlsx',
					'mod 4 ASL SALERNO 2019 definitivo.xlsx',
					'Mod 4 NA1  2019 definitivo.xlsx',
					'Mod 4_VET A.xlsx',
					'Mod 4 VET B + OSSERVATORIO EPIDEMIOLOGICO.xlsx',
					'Mod 4 VET C.xlsx',
					'MOD 4 SIAN.xlsx');
					

pg_query("truncate table matrix.update_xls");
					
$rownum = 0;

foreach($inputFileNames as $inputFileName){
	$file = $inputFileName;
	$inputFileName = 'Xlsx/'.$inputFileName;
//	echo '<br />%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%';
//	echo '<br />FILE '.$inputFileName;
//	echo  '<br />%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%<br />';
	$inputFileType = PHPExcel_IOFactory::identify($inputFileName);
	$objReader = PHPExcel_IOFactory::createReader($inputFileType);
	$objPHPExcel = $objReader->load($inputFileName);

	$i = 0;
	$worksheetList = $objReader->listWorksheetNames($inputFileName);
	for ($i=0; $i<count($worksheetList); $i++){
		$j=0;
		$data = array(1,$objPHPExcel->getSheet($i)->toArray(null,true,false,true));
		$insert = 'insert into matrix.update_xls(';
		$cols = 'nome_file, nome_foglio, rownum, ';

		$worksheetList = $objReader->listWorksheetNames($inputFileName);
		$sheetname = str_replace("'","", $worksheetList[$i]);
		if($sheetname == 'Foglio1' || $sheetname == 'altre UOSD Vet')
			break;
		// echo '<br />--------------------------------------------------------------------------------------------';
		// echo '<br />SHEET '.$sheetname.'  , Righe:' .count($data[1]);
		// echo '<br />--------------------------------------------------------------------------------------------<br />';
			
		if($data[0]==1 ){
			foreach($data[1] AS $row){
				$j++;
				if($j == 3){
					$cols_num = 0;
					foreach($row AS $column){
						if ($column != ''){
							$cols_num++;
							$query = "select distinct colonna from matrix.lookup_xls where descrizione = '$column'";
							$result = pg_query($query);
							$c = pg_fetch_row($result);
							$cols = $cols. $c[0].', ' ;
						}
					}
					$cols = substr( $cols, 0, -2 );
					$cols = $cols .' ) values ( ';
				}else if ($j > 3){
					$cols_count = 0;
					$rownum++;
					$vals = "'$file', '$sheetname', $j,";
					foreach($row AS $column){
						$column = str_replace("'","", $column);
						if ($cols_count < $cols_num){
							$vals = $vals ."'". $column."'".', ';
							$cols_count++;
						}
					}
				$vals = substr( $vals, 0, -2 );
				$vals = $vals .' );';
				$stat = $insert.$cols.$vals;
				pg_query($stat);
				echo $stat;
				echo '<br><br>';
				}
			}
		}

	}
}

$update = "update matrix.import_xls i 
set carico_effettivo_uos_ups = u.carico_effettivo_uos_ups, carico_effettivo_uos_uba = u.carico_effettivo_uos_uba
from matrix.update_xls u
where u.nome_file = i.nome_file and u.nome_foglio = i.nome_foglio and u.rownum = i.rownum";

pg_query($update);

?>