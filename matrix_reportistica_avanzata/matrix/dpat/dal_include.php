<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
	$charsetDB = "SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'";
	
	function CaricaArray($results){
		$arResults = array();

		if($results){
			if (pg_num_rows($results) != 0) {
				while ($row_results = pg_fetch_assoc($results)) {
					//$arResults[] = $row_results;
					$arResults[] = array_map('utf8_encode', $row_results);
				}
			}
		}
		pg_free_result($results);
		return $arResults;
	}

?>