<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php

if(md5($_REQUEST['username']) != 'e0cbf0e62d03796f31da47099682b72b' ||  md5($_REQUEST['password']) != 'e0cbf0e62d03796f31da47099682b72b'){
    die("Username o password errati");
}

$id_asl = $_REQUEST["id_asl"];
session_start();
$_SESSION["id_asl"] = $id_asl;
header("location: ra.php");

?>
