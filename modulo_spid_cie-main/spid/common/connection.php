<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php

function getConnection($db){

    require("common/config.php");

    if($db != "gesdasic"){
        $_CONFIG['db_psw'] = "";
    }

    $strConnection = "host=" . $_CONFIG["db_".$db] . " " .
				 "port=" . $_CONFIG['db_port'] . " " .
				 "dbname=" . $db . " " .
				 "user=" . $_CONFIG['db_user']  . " ".
                 "password=" . $_CONFIG['db_psw'];

    $conn = pg_connect($strConnection);// or die ("Errore critico di Connessione al Database $db");
    return $conn;

}


function isAdministrator($usr, $psw){
    getConnection("guc");
    $res = pg_query("select * from is_administrator('$usr', '$psw')");
    while($row = pg_fetch_assoc($res)){
        $isAdministrator = $row['is_administrator'];
    }
    return $isAdministrator;
}
			  			   
?>