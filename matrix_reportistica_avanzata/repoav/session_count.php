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


//$results = pg_query("select count(*) from \"Analisi_dev\".session_log where extract(epoch from(clock_timestamp()- last_timestamp ))/60 < 5");
$results = pg_query("SELECT count(*) FROM pg_stat_activity where state = 'active' and datname = 'mdgm'and query ilike '%allinea(%)%'");
$arResults = CaricaArray($results);
foreach($arResults as $row) {
    echo $row['count'];
}

?>