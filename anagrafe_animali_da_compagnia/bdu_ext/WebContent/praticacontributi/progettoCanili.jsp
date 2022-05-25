<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table class="details"  cellspacing="0" cellpadding="4" border="0" width="50%">
	<input type="submit" value="Avvia la richiesta" onClick="this.form.dosubmit.value='true';this.form.praticaCo.value='2';if(doCheck(this.form)){this.form.submit()};"/>
	<input type="button" value="Annulla" onclick="location.href='PraticaContributi.do'" />
	<input type="hidden" id="dataCorrente" name="dataCorrente" nomecampo = "datacorrente" labelcampo="Data Corrente" value="<%= request.getAttribute("currentDate") %>"  >

		
	<table class="details"  cellspacing="0" cellpadding="4" border="0" width="50%">
	
	<tr>
		<th colspan="2">
					<strong>
						<dhv:label name="praticacontributi.new">New</dhv:label>
					</strong>
		</th>
	</tr>
	
		<tr>
			<td class="formLabel">
				<label><b>ASL lavorante o Asl di Riferimento</b>
				</label>
			</td>
			<td>
			
	   	<dhv:evaluate if="<%=(User.getAslRifId()== 0) %>">
			   <% aslRifList.setJsEvent("onChange=\"javascript:recuperoCaniliByAslSelected(document.getElementById('aslRif2').value);\"");%>
  			   <%= aslRifList.getHtmlSelect("aslRif2", User.getAslRifId()) %>
  			</dhv:evaluate>
			<font color="red">*</font>
       	</td>
		</tr>
		
		<tr>
		<td class="formLabel">
				<label><b>Canile/i coinvolto/i</b>
				</label>
		</td>
		<td>
		<select name="selectCanili" id="selectCanili"   multiple="multiple" title="Selezionare un canile" >
			</select>
		</td>
		</tr>
		<tr>
			<td class="formLabel">
				<label><b>Oggetto</b>
				</label>
			</td>
			<td>
				<textarea rows="4" cols="35" name="oggettoPratica2" value="<%=pratica.getOggettoPratica()%>"></textarea>
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<label><b>Numero del Decreto</b>
				</label>
			</td>
			<td>
			
				<input type="text" name="numeroDecretoPratica2" size="10" id="numeroDecretoPratica2" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<label><b>Data del Decreto</b>
				</label>
			</td>
			<td>
				<input type="text" name="dataDecreto2" size="10" readonly="readonly" value=""/>
    			&nbsp;
    			<a href="#" onClick="cal19.select(document.forms[0].dataDecreto2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
				</a>
	      		<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<label><b>Data Inizio</b>
				</label>
			</td>
			<td>
			 	<input type="text" name="dataInizioSterilizzazione2" size="10" readonly="readonly" labelcampo="Data Inizio Sterilizzazione" nomecampo="iniziosterilizzazione" tipocontrollo="T19" />
				&nbsp;
				<a href="#" onClick="cal19.select(document.forms[0].dataInizioSterilizzazione2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
				</a>
   				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<label><b>Data Fine</b>
				</label>
			</td>
			<td>
				<input  type="text" name="dataFineSterilizzazione2" size="10" readonly="readonly" />
    			&nbsp;
    			<a href="#" onClick="cal19.select(document.forms[0].dataFineSterilizzazione2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
				</a>
	      		<font color="red">*</font>
        	</td>
		</tr>
		<tr>
					<th colspan="2">
						<strong>
							<dhv:label name="">Se non ci sono gatti/cani catturati inserire 0</dhv:label>
						</strong>
					</th>
		</tr>
		<tr>
			<td class="formLabel">
				<label><b>Numero Totale Cani Catturati</b>
				</label>
			</td>
			<td>
				<input type="text" name="totaleCaniCatturati2" id="totaleCaniCatturati2" size="10" title="Nel caso in cui non ci siano cani catturati inserire il valore 0"/>
    	    	<font color="red">*</font>
        	</td>
		</tr>
	
</table>
</div>
