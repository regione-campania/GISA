<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.Indice"%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.Criterio"%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicatura"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="criteri" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />

<%
String param1 = "idAnimale=" + animaleDettaglio.getIdAnimale() + "&idSpecie=" + animaleDettaglio.getIdSpecie();
	
if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
{
%>	
<form method="post" name="form" id="form" action="SchedaMorsicaturaAction.do?command=Add">

<input type="hidden" name="idAnimale" value="<%=animaleDettaglio.getIdAnimale()%>" />
<%
}
%>


<%@ include file="../initPage.jsp"%>


		
	
	
		<table cellpadding="4" cellspacing="0" border="0" width="100%" 
		<%
	if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
	{
%>
			
			class="details"
<%
	}
%>
>
		
	<%
	if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
	{
%>	
		<tr class="containerBody" >
				<th><label id="intestazioneSchedaCriterio">CRITERIO</label></th>
				<th><label id="intestazioneSchedaIndice">INDICE</label></th>

				<th>VALORE</th>

			</tr>
			
<%
	}
		int i=0;
		while(i<criteri.size())
		{
			Criterio criterio = (Criterio)criteri.get(i);
			ArrayList<Indice> indici = criterio.getIndici();
%>
			<tr 
			
			<%
	if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
	{
%>
			
			class="containerBody"
<%
	}
%>			
			
			>
				<td
				<%
	if(request.getAttribute("fromRegistrazione")!=null && !((String)request.getAttribute("fromRegistrazione")).equals("") && !((String)request.getAttribute("fromRegistrazione")).equals("null") && ((String)request.getAttribute("fromRegistrazione")).equals("true"))
	{
%>
			
			class="formLabel"
<%
	}
%>				
				
				><dhv:label name=""><%=criterio.getNome()%></dhv:label></td>
				<td
				
<%
	if(request.getAttribute("fromRegistrazione")!=null && !((String)request.getAttribute("fromRegistrazione")).equals("") && !((String)request.getAttribute("fromRegistrazione")).equals("null") && ((String)request.getAttribute("fromRegistrazione")).equals("true"))
	{
%>
			
			style="background-color: white; border-right: 0px;"
<%
	}
%>								
				
				>
<%
					int j=0;
					while(j<indici.size())
					{
						Indice indice = (Indice)indici.get(j);
						if(!indice.isValoreManuale())
						{
%>
							<input value="<%=indice.getId()%>" id="indice<%=criterio.getId()%>" name="indice<%=criterio.getId()%>" onclick="calcolaPunteggio('<%=criterio.getId()%>','<%=criterio.getFormulaCalcoloPunteggio()%>','<%=indice.getPunteggio()%>');" type="radio"   />
<%
						}						
%>	
							<%=indice.getNome().toUpperCase()%>
<%
						if(indice.isValoreManuale())
						{
%>
							<input value="<%=indice.getId()%>" id="<%=indice.isDivisore()%><%=criterio.getId()%>" name="<%=indice.isDivisore()%><%=criterio.getId()%>" type="hidden"   />
							<input value="<%=indice.getId()%>" id="indice<%=criterio.getId()%>" name="indice<%=criterio.getId()%>" type="hidden"   />
							<input value="" id="valoreManualeIndice<%=indice.getId()%>" name="valoreManualeIndice<%=indice.getId()%>" type="number" onblur="calcolaPunteggio('<%=criterio.getId()%>','<%=criterio.getFormulaCalcoloPunteggio()%>',null);"  />
<%							
						}
%>
							<br/>
<%
						j++;
					}
%>
				</td>
<%
				if(request.getAttribute("fromRegistrazione")==null ||((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
				{
%>
				<td><input disabled="true" id="punteggio<%=criterio.getId()%>" type="text" name="punteggio" /></td>
				
<%
				}
				else
				{
%>
					<input style="display:none;" disabled="true" id="punteggio<%=criterio.getId()%>" type="text" name="punteggio" />
<%
				}
%>
			</tr>
<%
			i++;
		}
%>
	
<%
				if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
				{
%>		
		<tr class="containerBody">
			<td colspan="3">
				<input type="button" onclick="if(checkFormRegistrazioneMorsicatura()){document.form.submit();}" value="Salva" />
			</td>
		</tr>
	

		
<%
				}
%>			
		</table>
<%		
	if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
{
%>	
		</form>
<%
}
%>	
		
		
		<script type="text/javascript">
			function checkFormRegistrazioneMorsicatura()
			{
<%
				int j=0;
				while(j<criteri.size())
				{
					Criterio criterio = (Criterio)criteri.get(j);
					if(!criterio.getFormulaCalcoloPunteggio().equals("divisione_indice"))
					{
%>
						if(document.getElementById('punteggio<%=criterio.getId()%>').value=='')
						{
							alert('Selezionare tutti gli indici');
							return false;
						}
<%
					}
					else
					{
						ArrayList<Indice> indiciCriterio = criterio.getIndici();
						
						int k=0;
						while(k<indiciCriterio.size())
						{
							Indice indice = (Indice)indiciCriterio.get(k);
%>
							if(document.getElementById('valoreManualeIndice<%=indice.getId()%>').value=='' || document.getElementById('valoreManualeIndice<%=indice.getId()%>').value=='' )
							{
								alert("Inserire tutti i valori per l'indice <%=indice.getNome()%> del criterio <%=criterio.getNome()%>");
								return false;
							}	
<%							
							k++;
						}
							

					}
					j++;
				}
%>
				return true;
			}
			
			function calcolaPunteggio(idCriterio,formula,punteggio)
			{
				if(formula=='divisione_indice')
				{
					var idDividendo = document.getElementById('false' + idCriterio).value;
					var idDivisore = document.getElementById('true' + idCriterio).value;
					var dividendo = document.getElementById('valoreManualeIndice' + idDividendo).value;
					var divisore = document.getElementById('valoreManualeIndice' + idDivisore).value;
					
					if(dividendo!=null && dividendo!='' && divisore!=null && divisore!='')
					{
						//Moltiplicazione *4 aggiunta come da richiesta della Pompameo - riferimento tt: 013398, Mail "scheda morsicatore: errore TT 013398" del 28/02/2019
						document.getElementById('punteggio' + idCriterio).value=dividendo/divisore*4;
					}
					else
					{
						document.getElementById('punteggio' + idCriterio).value='';
					}
				}
				else
				{
					document.getElementById('punteggio' + idCriterio).value=punteggio;
		        }
				
			}
			
	
			
			
			
			
			
			<%
			if(request.getAttribute("fromRegistrazione")!=null && !((String)request.getAttribute("fromRegistrazione")).equals("") && !((String)request.getAttribute("fromRegistrazione")).equals("null") && ((String)request.getAttribute("fromRegistrazione")).equals("true"))
			{
		%>
					gestisciTipologiaMorso(-1);
		<%
			}
		%>				
		
		
			
			
			
			
			
			
			
		</script>