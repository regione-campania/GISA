<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;
}

form {
	border: 3px solid #f1f1f1;
	padding 20px;

}

input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button {
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
}

button:hover {
  opacity: 0.8;
}

.container {
  padding: 15px;
	left: 55%;
    top: 20%;
    margin-left: -30%;
    position: absolute;
}

</style>
</head>
<body>

  <div class="container">
<?php include 'ep_config.php'; ?>
<h3 style="color:blue;font-family:calibri; background: #e9e9e9; text-align: center;">
Ambiente: 
<?php echo getEPEnv() . " - " . getEPip();?>
</h3>
<form action="/action_page2.php" method=post>


    <label for="uname"><b>Username</b></label>
    <input required type="text" placeholder="Enter Username" name="username" required>

    <label for="psw"><b>Password</b></label>
    <input required type="password" placeholder="Enter Password" name="password" required>
        
    <button type="submit">Login</button>
 

</form>
</div>
</body>
</html>
