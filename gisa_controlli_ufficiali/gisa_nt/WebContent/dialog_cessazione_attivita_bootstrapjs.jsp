<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style type="text/css">
    .bs-example{
    	margin: 20px;
    }
</style>


<script>


        
</script>






<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" style="align:left">
  Recupero Operazioni Pregresse
</button>


<div style="width: 100%" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
<div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
              <h4 class="modal-title" id="myModalLabel">Recupero Operazioni Pregresse</h4>
      </div>
       <div class="modal-body">
<div class="bs-example">
    <div class="panel-group" id="accordion">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">1. CESSAZIONE SENZA SCIA</a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in">
                <div class="panel-body">
                    <form method="post" action="<%=request.getParameter("urlSubmitCessazione")%>" >
<input type = "hidden" name="idAnagrafica" value="<%=request.getParameter("idAnagrafica") %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>INDICARE LA DATA DI CESSAZIONE ATTIVITA'</strong>
    </th>
  </tr>
 
    <tr>
      <td nowrap class="formLabel">
        Data
      </td>
      <td>
      
       <input type = "text" pattern="\d{1,2}/\d{1,2}/\d{4}" name="dataCessazioneAttivita"  id = "dataCessazioneAttivita" required="required" >
      	<a href="#" onClick="cal19.select(document.getElementById('dataCessazioneAttivita'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			
      </td>
    </tr>
    
      <tr>
      <td nowrap class="formLabel">
        Note
      </td>
      <td>
      <textarea rows="8" cols="50" name="noteCessazione"></textarea>		
      </td>
    </tr>
    
     
    
</table>
 <input type="submit" value="SALVA">
</form>
                    
                    
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">2. AMPLIAMENTO SENZASCIA</a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <div class="panel-body">
                    OPERAZIONE AL MOMENTO NON DISPONIBILE
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">3. VARIAZIONE IMPRESA SENZA SCIA</a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse">
                <div class="panel-body">
                    OPERAZIONE AL MOMENTO NON DISPONIBILE
                </div>
            </div>
        </div>
    </div>
	<p><strong>Note:</strong> Operazioni su Anagrafica in Assenza di un atto formale (SCIA)</p>
</div>

 <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">CHIUDI</button>
      </div>
</div>
</div></div>

</div>