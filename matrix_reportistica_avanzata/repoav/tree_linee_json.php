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

$id = $_REQUEST['id_struttura'];

$query = "select *, regexp_replace(descrizione, E'[\\n\\r]+', ' ', 'g' ) as descr from \"Analisi_dev\".macroarea order by  path, descr";
//echo $query;
$results = pg_query($query);
$arResults = CaricaArray($results);

//echo " ".(round(microtime(true) * 1000))-$millis;

$lev_prec = 0;
$json = '';
$i;
$j;
$count = 0;
foreach($arResults as $row) {
   if($lev_prec == $row['livello'] && $row['livello']!= 0){
		$json = $json . '},'; 
   }else if ($lev_prec < $row['livello']){
	   $json = $json . ', "children": [  ';
	   $lev_prec = $row['livello'];
   }else if ( $row['livello']!= 0){
	   for($i = $row['livello']; $i < $lev_prec  ; $i++){
			$json = $json . '}';
			for($j = 0; $j < $i; $j++){
				$json = $json . '    ';				
			}
			$json = $json . ']';
	   }
	    $json  = $json .' },';
	    $lev_prec = $row['livello'];
   }
   $json = $json .'
   ';
   for($i = 0; $i < $lev_prec ; $i++){
	   $json = $json .'    ';
   }
   
   $descr = str_replace('"',"",$row['descr']);
   $descr = str_replace('\n',"",$descr);

   
   
   $json2 = ' {"codice":"'. $descr .'"'
    .' ,"id":"'. $row[id] .'"'
    .' ,"id_gisa":"'. $row[id_gisa] .'"'
   .' ,"path":"'. $row[path] .'"'
   .' ,"codice":"'. $row[codice] .'"'
   .' ,"descrizione":"'. $descr .'"'
   .' ,"livello":"'. $row[livello] .'"'
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
