<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.Autopsia"%>

<div class="">
	<form id="fileupload" action="vam.accettazione.UploadImg.us" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="classRef" value="<%=Autopsia.class.getCanonicalName() %>" />
		<input type="hidden" name="idClass" value="1" />
		<input type="hidden" name="idAccettazione" value="1" />
		<input type="hidden" name="" value="">
	    <div class="row" style="height: 20px">
	        <div class="span16 fileupload-buttonbar">
	            <div class="progressbar fileupload-progressbar" style="width: 600px"><div style="width:0%;"></div></div>
	            <span class="btn success fileinput-button">
	                <span>Aggiungi immagini...</span>
	                <input type="file" name="files[]" multiple>
	            </span>
	            <%--
	            <button type="submit" class="btn primary start">Avvia upload</button>
	            <button type="reset" class="btn info cancel">Annulla upload</button>
	            <button type="button" class="btn danger delete">Elimina selezionati</button>
	            <input type="checkbox" class="toggle">
	            --%>
	        </div>
	    </div>
	    <br>
	    <div class="row">
	        <div class="span16">
	            <table class="zebra-striped"><tbody class="files"></tbody></table>
	        </div>
	    </div>
	</form>
</div>
