<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
session_start();
$html = $_SESSION['html'];
$num_richiesta = $_SESSION['numero_richiesta'];
?>

<!DOCTYPE html>
<html lang="it">
<head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
  <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="../css/alert.css" rel="stylesheet" type="text/css">
  <link href="modulo.css?v2" rel="stylesheet" type="text/css">

  <script src="funzioni.js?v6"></script>
  <script src="https://d3js.org/d3.v4.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
  <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script></head>

<script>
    window.addEventListener('load', function () {
        esporta(event, '<?php echo $num_richiesta ?>', document.getElementById('modulo').innerHTML);
    })
</script>
<body>
    Esportazione richiesta <?php echo $num_richiesta ?>

    <div id="modulo" style="pointer-events: none; display: none">
        <?php echo $html ?>
    <div>
</body>


