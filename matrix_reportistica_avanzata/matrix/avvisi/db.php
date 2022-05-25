<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("../dal_include.php");
require_once("../dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
mb_internal_encoding("UTF-8");

$avvisi = $_POST['avvisi'];
$sistema = $_REQUEST['sistema'];
$operation = $_REQUEST['operation'];

if($operation == 'save'){
    $result = "OK";

    pg_query("delete from matrix.avvisi where sistema = '$sistema'");
    foreach($avvisi as $value) {
        $value = str_replace("'", "''", $value);
        pg_query("insert into matrix.avvisi values ('$value', '$sistema')");
    }
    
    echo $result;
}else{
    $result =  pg_query("select * from matrix.avvisi where sistema = '$sistema'");
    $i = 0;
    while($row = pg_fetch_assoc($result)){
        $resp[$i] = $row["testo"];
        $i++;
    }
    echo json_encode($resp);
}


?>