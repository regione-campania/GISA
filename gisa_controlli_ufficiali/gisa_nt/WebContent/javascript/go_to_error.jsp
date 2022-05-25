<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<%@page import="org.aspcfs.modules.programmazzionecu.base.ProgrammazioneCu"%>

<%@page import="org.aspcfs.modules.osservazioni.base.Osservazioni"%>
<%@page import="org.aspcfs.modules.zonecontrollo.base.Organization"%><head>
<jsp:useBean id="OrgAdd" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>

<jsp:useBean id="TicketDetails" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert</title>
</head>
<body>

<!-- Questa jsp deve essere per
tutti i metodi di inserimento al fine di 
evitare che cliccando sui tasti di refresh 
vengano inseriti pi� volte gli stessi record. 
Le variabili commandD e org_code devono essere 
valorizzate nel command insert. -->


<%
session.setAttribute("OrgAdded", OrgAdd);
%>
<script type="text/javascript">
var command;
var org_cod;
alert('ww');
<%
String contesto[] = new String[6];
int id=-1;
if(OrgAdd!=null){
	String ct = OrgAdd.getClass().toString();
	contesto = ct.split("[.]");
	ct = contesto[3];
	if(ct.equals("accounts")){
		
	org.aspcfs.modules.accounts.base.Organization onew ;
	onew = (org.aspcfs.modules.accounts.base.Organization)OrgAdd;
	
	id= onew.getOrgId();
	
	%>
	
	
	command = "?command=Add";
	org_cod = "&orgId="+"<%= id %>";
	
	<%
		
	}else
		if(ct.equals("riproduzioneanimale")){
		
			
		org.aspcfs.modules.riproduzioneanimale.base.Organization onew ;
		onew = (org.aspcfs.modules.riproduzioneanimale.base.Organization)OrgAdd;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
			
		}else
			
			if(ct.equals("opu")){
				
				
			org.aspcfs.modules.opu.base.Stabilimento onew ;
			onew = (org.aspcfs.modules.opu.base.Stabilimento)OrgAdd;
			id= onew.getIdStabilimento();
			%>
			command = "?command=Details";
			org_cod = "&stabId="+"<%= id %>";
			<%
				
			}else
		if(ct.equals("operatori_commerciali")){
		
			
			
		org.aspcfs.modules.operatori_commerciali.base.Organization onew ;
		onew = (org.aspcfs.modules.operatori_commerciali.base.Organization)OrgAdd;
		 id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";

		<%
			
			
		}
		else
	
		if(ct.equals("operatorinonaltrove")){
			
			
		org.aspcfs.modules.operatorinonaltrove.base.Organization onew ;
		onew = (org.aspcfs.modules.operatorinonaltrove.base.Organization)OrgAdd;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
			
		}else
			
			if(ct.equals("imbarcazioni")){
				
				
			org.aspcfs.modules.imbarcazioni.base.Organization onew ;
			onew = (org.aspcfs.modules.imbarcazioni.base.Organization)OrgAdd;
			id= onew.getOrgId();
			%>
			command = "?command=Details";
			org_cod = "&orgId="+"<%= id %>";
			<%
				
			}
	
	else if(ct.equals("esercentifuoriregione")){
		
		org.aspcfs.modules.esercentifuoriregione.base.Organization onew ;
		onew = (org.aspcfs.modules.esercentifuoriregione.base.Organization)OrgAdd;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
		}else if(ct.equals("abusivismi")){
		org.aspcfs.modules.abusivismi.base.Organization onew ;
		onew = (org.aspcfs.modules.abusivismi.base.Organization)OrgAdd;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
		}else if(ct.equals("requestor")){
			org.aspcfs.modules.requestor.base.Organization onew ;
			onew = (org.aspcfs.modules.requestor.base.Organization)OrgAdd;
			id= onew.getOrgId();
			%>
			command = "?command=Details";
			org_cod = "&orgId="+"<%= id %>";
			<%
			}
		else if(ct.equals("trasportoanimali")){
			
			
			
		org.aspcfs.modules.trasportoanimali.base.Organization onew ;
		onew = (org.aspcfs.modules.trasportoanimali.base.Organization)OrgAdd;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
			
			
			}else if(ct.equals("operatoriprivati")){
				org.aspcfs.modules.operatoriprivati.base.Organization onew ;
				onew = (org.aspcfs.modules.operatoriprivati.base.Organization)OrgAdd;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
				}else if(ct.equals("operatorifuoriregione")){
					org.aspcfs.modules.operatorifuoriregione.base.Organization onew ;
					onew = (org.aspcfs.modules.operatorifuoriregione.base.Organization)OrgAdd;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("osm")){
					
					
					org.aspcfs.modules.osm.base.Organization onew ;
					onew = (org.aspcfs.modules.osm.base.Organization)OrgAdd;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("osmregistrati")){
					
					org.aspcfs.modules.osmregistrati.base.Organization onew ;
					onew = (org.aspcfs.modules.osmregistrati.base.Organization)OrgAdd;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("operatoriprivati")){
					org.aspcfs.modules.operatoriprivati.base.Organization onew ;
					onew = (org.aspcfs.modules.operatoriprivati.base.Organization)OrgAdd;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("camera_commercio")){
					org.aspcfs.modules.operatoriprivati.base.Organization onew ;
					onew = (org.aspcfs.modules.operatoriprivati.base.Organization)OrgAdd;
					id= onew.getOrgId();
					%>
					command = "?command=Dettaglio";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("laboratorihaccp")){
						org.aspcfs.modules.laboratorihaccp.base.Organization onew ;
						onew = (org.aspcfs.modules.laboratorihaccp.base.Organization)OrgAdd;
						id= onew.getOrgId();
						%>
						command = "?command=Details";
						org_cod = "&orgId="+"<%= id %>";
						<%
						}
				else if(ct.equals("punti_di_sbarco")){
				
					
				org.aspcfs.modules.punti_di_sbarco.base.Organization onew ;
				onew = (org.aspcfs.modules.punti_di_sbarco.base.Organization)OrgAdd;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					
				}
				else if(ct.equals("zonecontrollo")){
					
					
				Organization onew ;
				onew = (org.aspcfs.modules.zonecontrollo.base.Organization)OrgAdd;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					
				}
				else if(ct.equals("aziendeagricole")){
					
					
				org.aspcfs.modules.aziendeagricole.base.Organization onew ;
				onew = (org.aspcfs.modules.aziendeagricole.base.Organization)OrgAdd;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					
				}
				else if(ct.equals("acquedirete")){
					
				org.aspcfs.modules.acquedirete.base.Organization onew ;
				onew = (org.aspcfs.modules.acquedirete.base.Organization)OrgAdd;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					
				}
				
				
					else if(ct.equals("trasportoanimali")){
					if(request.getAttribute("AuditDetail")!=null)
					{
						org.aspcfs.checklist.base.Audit tnew ;
						tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("AuditDetail") ;
						int oid= tnew.getOrgId();
						int tid = tnew.getId();
						request.setAttribute("id",tnew.getId());
						request.setAttribute("idC",request.getAttribute("idC"));
						request.setAttribute("idControllo", request.getAttribute("idControllo"));
						request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
						request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
						request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
					%>
					command = "?command=Modify";
					org_cod = "&aggiorna=true&isSalvata=false&assetId=<%=request.getAttribute("assetId")%>&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}}
				
				else
					if(ct.equals("stabilimenti")){
		
						
					org.aspcfs.modules.stabilimenti.base.Organization onew ;
					onew = (org.aspcfs.modules.stabilimenti.base.Organization)OrgAdd;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
						
					}
					else
						
							if(ct.equals("soa")){
								
									
									org.aspcfs.modules.soa.base.Organization onew ;
									onew = (org.aspcfs.modules.soa.base.Organization)OrgAdd;
									id= onew.getOrgId();
									%>
									command = "?command=Details";
									org_cod = "&orgId="+"<%= id %>";
									<%									
								
							}else if(ct.equals("osa")){
								
								
								
								
								org.aspcfs.modules.osa.base.Organization onew ;
								onew = (org.aspcfs.modules.osa.base.Organization)OrgAdd;
								id= onew.getOrgId();
								%>
								command = "?command=Details";
								org_cod = "&orgId="+"<%= id %>";
								<%
							}
								
							else
								if(ct.equals("farmacosorveglianza")){
									
								org.aspcfs.modules.farmacosorveglianza.base.Organization onew ;
								onew = (org.aspcfs.modules.farmacosorveglianza.base.Organization)OrgAdd;
								id= onew.getOrgId();
								%>
								command = "?command=DetailsFcie";
								org_cod = "&idFarmacia="+"<%= id %>";
								<%
									
								}
								else
									if(ct.equals("parafarmacie")){
										
										
									org.aspcfs.modules.parafarmacie.base.Organization onew ;
									onew = (org.aspcfs.modules.parafarmacie.base.Organization)OrgAdd;
									id= onew.getOrgId();
									%>
									command = "?command=DetailsFcie";
									org_cod = "&idFarmacia="+"<%= id %>";
									<%
										
									}
									else
	if(ct.equals("molluschibivalvi")){
		
		
			
	
	if((Object)OrgAdd instanceof org.aspcfs.modules.molluschibivalvi.base.Organization)
	{
	org.aspcfs.modules.molluschibivalvi.base.Organization onew ;
	onew = (org.aspcfs.modules.molluschibivalvi.base.Organization)OrgAdd;
	id= onew.getId();
	%>
	command = "?command=Details";
	org_cod = "&orgId="+"<%= id %>";
	<%
	}else
	{
		org.aspcfs.modules.molluschibivalvi.base.Concessionario onew ;
		onew = (org.aspcfs.modules.molluschibivalvi.base.Concessionario)OrgAdd;
		id= onew.getId();
		
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
	}
		
	}
	
				
			
	
	
}


%>


var url_= location.href;

url_nuovo = new Array();
url_nuovo=url_.split("?command");
url_ = url_nuovo[0];
var str = "Audit";

u =url_.split('/');
b = u[4];

window.location.href = url_+command+org_cod;
</script>

</body>

