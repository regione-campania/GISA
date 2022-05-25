<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.EsameIstopatologico"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<div class="">
	<form id="fileupload" action="vam.accettazione.UploadImg.us" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="classRef" value="<%=EsameIstopatologico.class.getCanonicalName() %>" />
		<input type="hidden" name="idClass" value="${esame.id }" />
		<c:choose>
			<c:when test="${cc!=null}">
				<input type="hidden" name="idAccettazione" value="${cc.accettazione.id}" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="idAccettazione" value="${esame.cartellaClinica.accettazione.id}" />			
			</c:otherwise>
		</c:choose>
	    <div class="row" style="height: 20px">
	        <div class="span13 fileupload-buttonbar">
	            <div class="progressbar fileupload-progressbar" style="width: 400px"><div style="width:0%;"></div></div>
	            <span class="btn success fileinput-button">
	                <span>Aggiungi immagini...</span>
	                <input type="file" name="files[]" multiple>
	            </span>
	            <span class="btn info fileinput-button">
	                <span>Chiudi Finestra</span>
	                <input type="button" onclick="javascript:window.close();" />
	            </span>
	            
	        </div>
	    </div>
	    <br>
	    <div class="row">
	        <div class="span14">
	            <table class="zebra-striped"><tbody class="files"></tbody></table>
	        </div>
	    </div>
	</form>
</div>
