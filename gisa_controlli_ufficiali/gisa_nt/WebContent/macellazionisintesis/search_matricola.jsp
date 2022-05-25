<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.sintesis.base.SintesisStabilimento"	scope="request" />
<jsp:useBean id="Capo"				class="org.aspcfs.modules.macellazionisintesis.base.Capo"			scope="request" />
<jsp:useBean id="Razze"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Specie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBovine"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<%@ include file="../initPage.jsp" %>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			Ricerca Capo per Matricola
		</td>
	</tr>
</table>

<form method ="post" action = "MacellazioniSintesis.do?command=Search">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Ricerca Capo</strong>
          </th>
        </tr>
        <tr>
         <td class="formLabel">
           Matricola
          </td>
          <td>
            <input type="text" size="23" name="matricola" value="<%= toHtmlValue( Capo.getCd_matricola()) %>">
          </td>
        </tr>
        </table>
        
        <input type = "submit" value = "Ricerca">
</form>

<br>
<%
if (Capo != null && Capo.getId()>0 )
{
%>



<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody>
    <tr>
        <td valign="top" width="55%">
        
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
            <tbody>
             <tr>
                <th colspan="2"><strong>Dati Macellazione</strong></th>
            </tr>
            
            <tr class="containerBody">
            	<td class="formLabel">Anagrafato presso Macello</td>
            	<td>
            	<a href="StabilimentoSintesisAction.do?command=DettaglioStabilimento&altId=<%=OrgDetails.getAltId() %>"><%=OrgDetails.getName() %></a>
            	</td>
            </tr>
              <tr class="containerBody">
            	<td class="formLabel">Cancellato</td>
            	<td>
            	<%=(Capo.getTrashed_date()!=null) ? "Capo Cancellato in Data "+Capo.getTrashed_date()  : "Capo Attivo" %>
            	</td>
            </tr>
            
            <tr class="containerBody">
            	<td class="formLabel">Data Macellazione (Se Macellato)</td>
            	<td>
            	<%=Capo.getVpm_data() %>
            	</td>
            </tr>
            
          
            
            <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >Matricola</td>
                <td>
                	<%=Capo.getCd_matricola()%>
                </td>
            </tr>
            
            <tr class="containerBody" >
                <td class="formLabel" >Codice Azienda di Nascita</td>
                <td>
                	<%=Capo.getCd_codice_azienda() %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" >Specie<br></td>
                <td>
                	<%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Sesso</td>
              <td>
                 <%=Capo.isCd_maschio() ? ("M") : ("F") %>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Categoria</td>

              	<td>
              		<%=CategorieBovine.getSelectedValue( Capo.getCd_categoria_bovina() ) + CategorieBufaline.getSelectedValue( Capo.getCd_categoria_bufalina() ) %>&nbsp;
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Razza</td>

              	<td>
              		<%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;
              		<%if(Capo.getCd_id_razza() == 999){ %>
              		<%= Capo.getCd_razza_altro() != null && !Capo.getCd_razza_altro().equals("") ? ": " + Capo.getCd_razza_altro() : ": N.D." %>
					<%} %>
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data di nascita</td>
              <td>
              		<dhv:tz timestamp="<%=Capo.getCd_data_nascita() %>" dateOnly="true"/>&nbsp;
              </td>
            </tr>	
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Categoria di Rischio</td>
                <td>
                	<%=Capo.getCd_categoria_rischio() %>
				</td> 
            </tr>
              
             <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo </strong>                
                </th>
            </tr>
              <tr class="containerBody">
                <td class="formLabel" rowspan="3">&nbsp; </td>
                <td>&nbsp;1. <%=Capo.getCd_veterinario_1() %></td>
            </tr>
            
            <tr class="containerBody">
                <td>&nbsp;2. <%=Capo.getCd_veterinario_2() %></td>
            </tr>
            <tr class="containerBody">
                <td>&nbsp;3. <%=Capo.getCd_veterinario_3() %></td>
            </tr>
            
       
				
            
        </tbody></table>        </td>
     </tr>
     
     
     
	</tbody>
</table>	

	
		


<%	

}

%>


