<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table class="details"  cellspacing="0" cellpadding="4" border="0" width="50%">
 
	<input type="button" value="Avvia la richiesta" onClick="this.form.dosubmit.value='true';this.form.praticaCo.value='1';if(doCheck(this.form)){this.form.submit()};"/>
	<input type="button" value="Annulla" onclick="location.href='PraticaContributi.do'" />
	<input type="hidden" id="dataCorrente" name="dataCorrente" nomecampo = "datacorrente" labelcampo="Data Corrente" value="<%= request.getAttribute("currentDate") %>"  >
	<!--<input type="hidden" id="praticaCo" name="praticaCo" value="1"  >-->

	<tr>
		<th colspan="2">
					<strong>
						<dhv:label name="praticacontributi">New</dhv:label>
					</strong>
		</th>
	</tr>
	
	
	<tr>
    	<td nowrap class="formLabel">
           		   <dhv:label name="">Asl di Riferimento</dhv:label>
    	</td>
    	<td>	
       		<dhv:evaluate if="<%= (User.getAslRifId() > 0) %>">
  				<%= User.getAslRif() %>
  				<script>
  						<% 	ArrayList<String> listaComuni=(ArrayList<String>)request.getAttribute("lista");%> 
 						 array[<%=User.getAslRifId() %>]=<%=listaComuni%>
  				</script>	
  				
  				 <input type="hidden" id="aslRif" name="aslRif" value="<%= User.getAslRifId() %>" >
  				 <font color="red">*</font>
  			</dhv:evaluate>
           		
           	<dhv:evaluate if="<%= (User.getAslRifId() == 0) %>" >
           		<%
  					Hashtable<String,ArrayList<String>> t=(Hashtable<String,ArrayList<String>>)request.getAttribute("hashtable");
  				 %>
  				 <script>
						<%Iterator<String> key=t.keySet().iterator();

						while(key.hasNext()){
									String kiave=key.next();
	
									ArrayList<String> arr=t.get(kiave);
									%>
									 array[<%="'"+kiave+"'"%>]=<%=arr%>;
						<%} %>
				</script>
				<%	aslRifList.setJsEvent( "onchange=onChangeState()");	%>
           		
           		<%=aslRifList.getHtmlSelect("aslRif", -1 ) %>
				<input type="hidden" name="aslRifFReg" />
				<font color="red">*</font>
       		</dhv:evaluate>
       		
       		 <tr id="comuni">
	<td class="formLabel" >Comune</td>
	<td>
	 	<select name="comune" id="comune" multiple="multiple" title="Per selezionare più comuni, digitare ctrl se i comuni non sono consecutivi nell'elenco, digitare shift nel caso di comuni consecutivi ">
		</select>
		<font color="red">*</font>
	</td>
	</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Oggetto</dhv:label>
	           		</td>
    	       		<td>
    	       		<textarea rows="4" cols="35" name="oggettoPratica" value="<%=pratica.getOggettoPratica()%>"></textarea>
    				</td>
           		</tr>	
           		<tr>
           			<td  class="formLabel">
           				<dhv:label name="">Numero del Decreto</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="text" name="numeroDecretoPratica" size="10" />
    	       			<font color="red">*</font>
    				</td>
           		</tr>		
           		<tr>
           			<td  class="formLabel">
           				<dhv:label name="">Data del Decreto</dhv:label>
	           		</td>
    	       		<td>
    	       		<input type="text" name="dataDecreto" size="10" readonly="readonly" />
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataDecreto,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				<font color="red">*</font>
        	   		</td>
           		</tr>		
           		<tr>
           			<td  class="formLabel">
           				<dhv:label name="">Data Inizio</dhv:label>
	           		</td>
    	       		<td>
    	       		
    	       		 	<input type="text" name="dataInizioSterilizzazione" size="10" readonly="readonly" labelcampo="Data Inizio Sterilizzazione" nomecampo="iniziosterilizzazione" />
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataInizioSterilizzazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				<font color="red">*</font>
	           	
        	   		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Data Fine</dhv:label>
	           		</td>
    	       		<td>
    	       		<input  type="text" name="dataFineSterilizzazione" size="10" readonly="readonly" />
    					&nbsp;
    					<a href="#" onClick="cal19.select(document.forms[0].dataFineSterilizzazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">
						</a>
	      				<font color="red">*</font>
        	   		</td>
           		</tr>
			<tr>
					<th colspan="2">
						<strong>
							<dhv:label name="">Se non ci sono gatti/cani padronali o catturati inserire 0</dhv:label>
						</strong>
					</th>
			</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Cani Padronali</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="text" name="totaleCaniPadronali"  size="10" title="Nel caso in cui non ci siano cani padronali inserire il valore 0"/>
    	       			<font color="red">*</font>
    	       		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Cani Catturati</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="text" name="totaleCaniCatturati" size="10" title="Nel caso in cui non ci siano cani catturati inserire il valore 0"/>
    	       			<font color="red">*</font>
        	   		</td>
           		</tr>
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Gatti Padronali</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="text" name="totaleGattiPadronali" size="10" title="Nel caso in cui non ci siano gatti padronali inserire il valore 0"/>
    	       			<font color="red">*</font>
        	   		</td>
           		</tr>	
           		<tr>
           			<td nowrap class="formLabel">
           				<dhv:label name="">Numero Totale Gatti Catturati</dhv:label>
	           		</td>
    	       		<td>
    	       			<input type="text" name="totaleGattiCatturati" size="10" title="Nel caso in cui non ci siano gatti catturati inserire il valore 0"/>
    	       			<font color="red">*</font>
        	   		</td>
           		</tr>	
           		
		</td>
    </tr>
</table>
