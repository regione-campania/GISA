<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<form method="post" action="backend.jsp" enctype="multipart/form-data" >

<table>
<tr><td><input type="file" name="file1"></td></tr>
<tr><td><input type="file" name="file2"></td></tr>
<tr><td><input type="file" name="file3"></td></tr>
<tr><td><input type="file" name="file4"></td></tr>
<tr><td><input type="file" name="file5"></td></tr>
<tr><td><input type="file" name="file6"></td></tr>

</table>
<input type="submit" value="Send more file">
</form>
<br>
<hr>

<form method="post" action="backend.jsp" enctype="multipart/form-data" >

<table>
<tr><td><input type="file" name="file1"></td></tr>


</table>
<input type="submit" value="Send one file">
</form>