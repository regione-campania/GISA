<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.allevamenti.base.Organization"
	scope="request" />


<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>

<script type="text/javascript">

	
	function doCheck(form) {
		//alert("nel docheck");
	    if (form.dosubmit.value == "false") {
            //alert("nell if");
		    return true;
	  } else {
	    return(checkForm(form));
	  }
	}
	
	
	function checkForm()
	{
		 formTest = true;
		    message = "";
		    alertMessage = "";

            //alert("nella funzione");
		    //alert("loc: "+form.localita.value);
		    //alert("data sosp: "+form.dataSospetto.value);
		    //alert("data ap: "+form.dataApertura.value);
		    if (form.localita){
		        if ((checkNullString(form.localita.value))){
		          message += "- Località richiesta\r\n";
		          formTest = false;
		        }
		      }

		    if(checkNullString(form.dataSospetto.value)){
	    		message += "- Data sospetto richiesta\r\n";
	    		formTest = false;
	    	}   

		    if(checkNullString(form.dataApertura.value)){
	    		message += "- Data apertura richiesta\r\n";
	    		formTest = false;
	    	}

		    if (formTest == false) {
		        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		        return false;
		      } else {
			      return true;
		      }
	};

	function controlloNumeri(obj) {
		if (isNaN(obj.value) || parseInt(obj.value)<0 || parseInt(obj.value) > 9999)
		{
		alert('Nel campo è possibile immettere solo numeri!');
		obj.value="";
		obj.focus();
		}
		}
	
	
</script>



<form name="focolai" action="Focolai.do?command=Salva" onsubmit="return doCheck(this);"  method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td><a href="Allevamenti.do"><dhv:label
			name="allevamenti.allevamenti">Accounts</dhv:label></a> > <%
			if (request.getParameter("return") == null) {
		%>
		<a href="Allevamenti.do?command=Search"><dhv:label
			name="allevamenti.SearchResults">Search Results</dhv:label></a> > <%
			} else if (request.getParameter("return").equals("dashboard")) {
		%>
		<a href="Allevamenti.do?command=Dashboard"><dhv:label
			name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
			}
		%>
			<a href="Allevamenti.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="allevamenti.details">Account Details</dhv:label> </a> >
		<a href="Focolai.do?&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="focolai">Focolai</dhv:label></a> >
		<dhv:label name="focolai.aggiungi">Aggiungi</dhv:label></td>
	</tr>
</table>

<%request.getSession().setAttribute("abilitaModulo","ok"); %>  <!-- serve ad abilitare la voce che permette la chiusura del modulo -->

<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="100%" valign="top">


		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label name="titolo">Denuncia di malattia infettiva e diffusiva degli animali</dhv:label></strong>
				</th>
			</tr>


			<!-- modifiche d.dauria -->
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="allevamenti.site">Site</dhv:label>
				</td>
				<td><%=Asl.getSelectedValue(OrgDetails.getSiteId())%> <input
					type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
					<input
					type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
					
				</td>
			</tr>


			<dhv:evaluate if="<%=hasText(OrgDetails.getName())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Denominazione</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getAccountNumber())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="organization.accountNumbera">Codice Azienda</dhv:label></td>
					<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getSpecieAllev())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="organization.specieAlleva">Specie Allevata</dhv:label></td>
					<td><%=toHtml(OrgDetails.getSpecieAllev())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getPartitaIva())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Partita IVA / Codice Fiscale</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getPartitaIva())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>


				<%
					Iterator iaddress = OrgDetails.getAddressList().iterator();
						if (iaddress.hasNext()) {
							while (iaddress.hasNext()) {
								OrganizationAddress thisAddress = (OrganizationAddress) iaddress
										.next();
				%>
				<tr class="containerBody">
					<td nowrap class="formLabel" valign="top">Sede Legale</td>
					<td><%=toHtml(thisAddress.toString())%>&nbsp; <dhv:evaluate
						if="<%=thisAddress.getPrimaryAddress()%>">
						<dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
					</dhv:evaluate></td>
				</tr>
				<%
					}
						} else {
				%>
				<tr class="containerBody">
					<td colspan="2"><font color="#9E9E9E"><dhv:label
						name="contacts.NoAddresses">No addresses entered.</dhv:label></font></td>
				</tr>
				<%
					}
				%>
			


			<!--  fine -->



			<tr>
				<th colspan="2"><strong><dhv:label
					name="focolai.denuncia">Denuncia malattia</dhv:label></strong></th>
			</tr>
			
			<tr>
				<td class="formLabel"><dhv:label name="loc">Malattia</dhv:label>
				</td>
				<td><input id="classe_sprincipale" type="text" maxlength="100"
					size="70" name="malattia" /></td>
			</tr>
			
			<%-- 
			<tr>
				<td class="formLabel"><dhv:label name="loc">Specie</dhv:label>
				</td>
				<td><input id="classe_sprincipdale" type="text" maxlength="100"
					size="70" name="specieAnimale" /></td>
			</tr>
			--%>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Località della stalla o del pascolo infetto</dhv:label>
				</td>
				<td><input id="classe_principale" type="text" maxlength="50"
					size="50" name="localita" /></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data del sospetto</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataSospetto"
					showTimeZone="false" /></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data della prova</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataProva"
					showTimeZone="false" /></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data apertura</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataApertura"
					showTimeZone="false" /></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Origine malattia</dhv:label>
				</td>
				<td><input id="classe_principaled" type="text" maxlength="50"
					size="50" name="origineMalattia" /></td>
			</tr>
			<tr>
				<td></td>
				<td>In caso di malattie soggette a piani di risanamento
				specificare</td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Animali di provenienza regionale</dhv:label>
				</td>
				<td><input type="checkbox" name="provenienzaRegionale" />
				&nbsp;&nbsp;Data ultima introduzione <zeroio:dateSelect
					form="focolai" field="dataProvenienzaRegionale"
					showTimeZone="false" /></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Animali di provenienza extraregionale</dhv:label>
				</td>
				<td><input type="checkbox" name="provenienzaExtraRegionale" />
				&nbsp;&nbsp;Data ultima introduzione <zeroio:dateSelect
					form="focolai" field="dataProvenienzaExtraRegionale"
					showTimeZone="false" /></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Pascolo infetto</dhv:label>
				</td>
				<td><input type="checkbox" name="pascoloInfetto" /></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Reinfezione</dhv:label>
				</td>
				<td><input type="checkbox" name="reinfezione" /></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Cani infetti</dhv:label>
				</td>
				<td><input type="checkbox" name="caniInfetti" /></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Monta esterna</dhv:label>
				</td>
				<td><input type="checkbox" name="montaEsterna" /></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Iatrogena</dhv:label>
				</td>
				<td><input type="checkbox" name="iatrogena" /></td>
			</tr>
		</table>
<br/>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label
					name="acque classificate.decrddeto">Censimento degli animali presenti in azienda</dhv:label></strong>
				</th>

			</tr>
			<tr>
			
				<td>
				<table width="100%" border="1">
					<tr>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">1 Specie</dhv:label>
						</td>


						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">2 n. complessivo</dhv:label>
						</td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">3 Nati in stalla</dhv:label>
						</td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">4 Introdotti</dhv:label>
						</td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">5 Ammalati</dhv:label>
						</td>


						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">6 Morti</dhv:label>
						</td>

					</tr>

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie1" /></td>


						<td align="center"><input type="text"   size="7"
							name="complessivo1" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"   size="7"
							name="natiStalla1" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti1" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"   size="7"
							name="ammalati1" onBlur="controlloNumeri(this)" /></td>


						<td align="center"><input type="text"  size="7" name="morti1" onBlur="controlloNumeri(this)" />
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"   size="20"
							name="specie2" /></td>


						<td align="center"><input type="text"  size="7"
							name="complessivo2" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="natiStalla2" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"   size="7"
							name="introdotti2" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"   size="7"
							name="ammalati2" onBlur="controlloNumeri(this)" /></td>


						<td align="center"><input type="text"   size="7" name="morti2" onBlur="controlloNumeri(this)" />
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie3" /></td>


						<td align="center"><input type="text" size="7"
							name="complessivo3" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="natiStalla3" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti3" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text" size="7"
							name="ammalati3" onBlur="controlloNumeri(this)" /></td>


						<td align="center"><input type="text" size="7" name="morti3" onBlur="controlloNumeri(this)" />
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie4" /></td>


						<td align="center"><input type="text" size="7"
							name="complessivo4" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="natiStalla4" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti4" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text" size="7"
							name="ammalati4" onBlur="controlloNumeri(this)" /></td>


						<td align="center"><input type="text"  size="7" name="morti4" onBlur="controlloNumeri(this)" />
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie5" /></td>


						<td align="center"><input type="text"  size="7"
							name="complessivo5" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text" size="7"
							name="natiStalla5" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti5" onBlur="controlloNumeri(this)" /></td>

						<td align="center"><input type="text" maxlength="50" size="7"
							name="ammalati5" onBlur="controlloNumeri(this)" /></td>


						<td align="center"><input type="text" size="7" name="morti5" onBlur="controlloNumeri(this)" />
						</td>

					</tr>

				</table>

				</td>
				
			</tr>


		</table>
		
		
		
		<br/>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label
					name="acque classificate.decrddeto">Provvedimenti</dhv:label></strong>
				</th>
			</tr>
			
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Sindaco</dhv:label>
				</td>
				<td>Data <zeroio:dateSelect form="focolai" field="dataProvvedimenti"
					showTimeZone="false" /> Numero dei provvedimenti  <input type="text" size="30" name="numeroProvvedimenti" />
					
				</td>
			</tr>
			
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Proposte</dhv:label>
				</td>
				<td>
				   <input type="text" size="30" name="proposteAdozione" />	
				</td>
			</tr>
			
				<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Date trattamenti immunizzanti</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataImmunizzanti"
					showTimeZone="false" /> 
					
				</td>
			</tr>
			
				<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Osservazioni</dhv:label>
				</td>
				<td>
				   <input type="text" size="50" name="osservazioni" />	
				</td>
			</tr>
			
		</table>	
		
		
		
		
		


		</td>

	</tr>

</table>


<input  type="submit"
	value="<dhv:label name="button.save">Save</dhv:label>"></form>


