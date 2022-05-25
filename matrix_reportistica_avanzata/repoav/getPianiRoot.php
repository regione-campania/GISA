<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
require_once("dal_include.php");
require_once("dal_connessione.php");
error_reporting(E_ALL & ~E_NOTICE & ~E_STRICT & ~E_DEPRECATED & ~E_WARNING);
mb_internal_encoding("UTF-8");

$anno = $_REQUEST['anno'];
$query = "select id_root from matrix.tree_anno where anno = $anno and id_tree in (select id from matrix.trees where name = 'Struttura Piani')";

$results = pg_query($query);
$arResults = CaricaArray($results);

$i = 0;
foreach($arResults as $row) {
	echo $row['id_root'];
}

?>
