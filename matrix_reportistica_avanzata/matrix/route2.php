<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php

	session_start();

	echo "session.user: " .	$_SESSION['user'];
	echo "<br> session.id_asl: " . $_SESSION['id_asl'];
	echo "<br> session.called_url: " . $_SESSION['called_url'];
	echo "<br> session.id_user: " . $_SESSION['id_user'];
	echo "<br> session.role: " . $_SESSION['role'];
	echo "<br> session.strut: " . $_SESSION['strut'];
	echo "<br> session.readonly: " . $_SESSION['readonly'];
		  
	if ( $_SESSION['called_url'] == 'formulematrix.gisacampania.it') {
		
		$dest = '/formule.php';
	} else {
		$dest = '/tree.php';
	}
	echo "<hr><a href='$dest'> Prosegui</a>";

?>
 
<script>
	setTimeout(function() { location='<?php echo $dest;?>'} , 500);
</script>
