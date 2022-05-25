<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>

<%@page import="org.aspcfs.modules.osservazioni.base.Osservazioni"%>
<%@page import="org.aspcfs.modules.zonecontrollo.base.Organization"%><head>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="OrgDetailsCU" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>

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

<script type="text/javascript">
var command;
var org_cod;
<%

String contesto[] = new String[6];
int id=-1;
if(OrgDetails!=null){
	String ct = OrgDetails.getClass().toString();
	contesto = ct.split("[.]");
	ct = contesto[3];
	System.out.println("CONTESTO org: "+ct);
	if(ct.equals("accounts")){
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
		org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
		<%
		}
		else
		{
		
	org.aspcfs.modules.accounts.base.Organization onew ;
	onew = (org.aspcfs.modules.accounts.base.Organization)OrgDetails;
	id= onew.getOrgId();
	%>
	command = "?command=Details";
	org_cod = "&orgId="+"<%= id %>";
	<%
		}
		
	}else
		if(ct.equals("apiari") ){
			
			//aggiunta x checklist
			if(request.getAttribute("AuditDetail")!=null)
				{
					org.aspcfs.checklist.base.Audit tnew ;
					tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("AuditDetail") ;
					//modificato in idApiario
					int oid= tnew.getIdApiario();
					int tid = tnew.getId();
					request.setAttribute("id",tnew.getId());
					request.setAttribute("idC",request.getAttribute("idC"));
					request.setAttribute("idControllo", request.getAttribute("idControllo"));
					request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
					request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
					request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
				%>
				command = "?command=Modify";
				org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&stabId="+"<%= oid %>";
				<%
				}	
				else
				{
					if ((Object)OrgDetails instanceof ext.aspcfs.modules.apiari.base.Operatore  )
					{
					ext.aspcfs.modules.apiari.base.Operatore onew ;
					onew = (ext.aspcfs.modules.apiari.base.Operatore)OrgDetails;
					id= onew.getIdOperatore();
					%>
					command = "?command=Details";
					org_cod = "&opId="+"<%= id %>";
					<%
					
					}
					if ((Object)OrgDetails instanceof ext.aspcfs.modules.apiari.base.Stabilimento  )
					{
					ext.aspcfs.modules.apiari.base.Stabilimento onew ;
					onew = (ext.aspcfs.modules.apiari.base.Stabilimento)OrgDetails;
					id= onew.getIdStabilimento();
					%>
					command = "?command=Details";
					org_cod = "&stabId="+"<%= id %>";
					<%
					
					}
				}
			
			
			
			
			
				
			}
		else
		if(ct.equals("riproduzioneanimale")){
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
			org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
			<%
			}
			else
			{
			
		org.aspcfs.modules.riproduzioneanimale.base.Organization onew ;
		onew = (org.aspcfs.modules.riproduzioneanimale.base.Organization)OrgDetails;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
			}
		}else
			
			if(ct.equals("opu")){
				if(request.getAttribute("AuditDetail")!=null)
				{
					org.aspcfs.checklist.base.Audit tnew ;
					tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("AuditDetail") ;
					int oid= tnew.getIdStabilimento();
					int tid = tnew.getId();
					request.setAttribute("id",tnew.getId());
					request.setAttribute("idC",request.getAttribute("idC"));
					request.setAttribute("idControllo", request.getAttribute("idControllo"));
					request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
					request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
					request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
				%>
				command = "?command=Modify";
				org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&stabId="+"<%= oid %>";
				<%
				}
				else
				{
				
			org.aspcfs.modules.opu.base.Stabilimento onew ;
			onew = (org.aspcfs.modules.opu.base.Stabilimento)OrgDetails;
			id= onew.getIdStabilimento();
			%>
			command = "?command=Details";
			org_cod = "&stabId="+"<%= id %>";
			<%
				}
			}
			
			else
				
				if(ct.equals("suap")){
					if(request.getAttribute("AuditDetail")!=null)
					{
						org.aspcfs.checklist.base.Audit tnew ;
						tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("AuditDetail") ;
						int oid= tnew.getAltId();
						int tid = tnew.getId();
						request.setAttribute("id",tnew.getId());
						request.setAttribute("idC",request.getAttribute("idC"));
						request.setAttribute("idControllo", request.getAttribute("idControllo"));
						request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
						request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
						request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
					%>
					command = "?command=Modify";
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&altId="+"<%= oid %>";
					<%
					}
					else
					{
					
				org.aspcfs.modules.suap.base.Stabilimento onew ;
				onew = (org.aspcfs.modules.suap.base.Stabilimento)OrgDetails;
				id= onew.getAltId();
				%>
				command = "?command=Details";
				org_cod = "&altId="+"<%= id %>";
				<%
					}
				}
	
				else if(ct.equals("sintesis")){
					if(request.getAttribute("AuditDetail")!=null)
					{
						org.aspcfs.checklist.base.Audit tnew ;
						tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("AuditDetail") ;
						int oid= tnew.getAltId();
						int tid = tnew.getId();
						request.setAttribute("id",tnew.getId());
						request.setAttribute("idC",request.getAttribute("idC"));
						request.setAttribute("idControllo", request.getAttribute("idControllo"));
						request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
						request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
						request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
					%>
					command = "?command=Modify";
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&altId="+"<%= oid %>";
					<%
					}
					else
					{
					
				org.aspcfs.modules.sintesis.base.SintesisStabilimento onew ;
				onew = (org.aspcfs.modules.sintesis.base.SintesisStabilimento)OrgDetails;
				id= onew.getAltId();
				%>
				command = "?command=Details";
				org_cod = "&altId="+"<%= id %>";
				<%
					}
				}
	
				else if(ct.equals("gestioneanagrafica")){
					if(request.getAttribute("AuditDetail")!=null)
					{
						org.aspcfs.checklist.base.Audit tnew ;
						tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("AuditDetail") ;
						int oid= tnew.getAltId();
						int tid = tnew.getId();
						request.setAttribute("id",tnew.getId());
						request.setAttribute("idC",request.getAttribute("idC"));
						request.setAttribute("idControllo", request.getAttribute("idControllo"));
						request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
						request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
						request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
					%>
					command = "?command=Modify";
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&altId="+"<%= oid %>";
					<%
					}
					else
					{
					
				org.aspcfs.modules.gestioneanagrafica.base.Stabilimento onew ;
				onew = (org.aspcfs.modules.gestioneanagrafica.base.Stabilimento)OrgDetails;
				id= onew.getAltId();
				%>
				command = "?command=Details";
				org_cod = "&altId="+"<%= id %>";
				<%
					}
				}
	
			else
		if(ct.equals("operatori_commerciali")){
		
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
			org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
			<%
			}
			else
			{
			
		org.aspcfs.modules.operatori_commerciali.base.Organization onew ;
		onew = (org.aspcfs.modules.operatori_commerciali.base.Organization)OrgDetails;
		 id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";

		<%if (request.getAttribute("MsgBdu")!=null)
		{
			%>
			org_cod += "&msgbdu=<%=request.getAttribute("MsgBdu")%>";
			<%			
		}
			}
			
		}
		else
	
		if(ct.equals("operatorinonaltrove")){
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
			org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
			<%
			}
			else
			{
			
		org.aspcfs.modules.operatorinonaltrove.base.Organization onew ;
		onew = (org.aspcfs.modules.operatorinonaltrove.base.Organization)OrgDetails;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
			}
		}else
			
			if(ct.equals("imbarcazioni")){
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
				org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
				<%
				}
				else
				{
				
			org.aspcfs.modules.imbarcazioni.base.Organization onew ;
			onew = (org.aspcfs.modules.imbarcazioni.base.Organization)OrgDetails;
			id= onew.getOrgId();
			%>
			command = "?command=Details";
			org_cod = "&orgId="+"<%= id %>";
			<%
				}
			}
	
	else if(ct.equals("esercentifuoriregione")){
		
		org.aspcfs.modules.esercentifuoriregione.base.Organization onew ;
		onew = (org.aspcfs.modules.esercentifuoriregione.base.Organization)OrgDetails;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
		}else if(ct.equals("abusivismi")){
		org.aspcfs.modules.abusivismi.base.Organization onew ;
		onew = (org.aspcfs.modules.abusivismi.base.Organization)OrgDetails;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
		}else if(ct.equals("requestor")){
			org.aspcfs.modules.requestor.base.Organization onew ;
			onew = (org.aspcfs.modules.requestor.base.Organization)OrgDetails;
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
			org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
			<%
			}
			else
			{
			
		org.aspcfs.modules.trasportoanimali.base.Organization onew ;
		onew = (org.aspcfs.modules.trasportoanimali.base.Organization)OrgDetails;
		id= onew.getOrgId();
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
			}
			
			}else if(ct.equals("operatoriprivati")){
				org.aspcf.modules.controlliufficiali.base.Organization onew ;
				onew = (org.aspcf.modules.controlliufficiali.base.Organization)OrgDetailsCU;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
				}else if(ct.equals("operatorifuoriregione")){
					org.aspcfs.modules.operatorifuoriregione.base.Organization onew ;
					onew = (org.aspcfs.modules.operatorifuoriregione.base.Organization)OrgDetails;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("osm")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}
					else
					{
					org.aspcfs.modules.osm.base.Organization onew ;
					onew = (org.aspcfs.modules.osm.base.Organization)OrgDetails;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}}
				else if(ct.equals("osmregistrati")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}
					else
					{
					org.aspcfs.modules.osmregistrati.base.Organization onew ;
					onew = (org.aspcfs.modules.osmregistrati.base.Organization)OrgDetails;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}}
				else if(ct.equals("operatoriprivati")){
					org.aspcf.modules.controlliufficiali.base.Organization onew ;
					onew = (org.aspcf.modules.controlliufficiali.base.Organization)OrgDetailsCU;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("gestioneanagrafica")){
					org.aspcf.modules.controlliufficiali.base.Organization onew ;
					onew = (org.aspcf.modules.controlliufficiali.base.Organization)OrgDetailsCU;
					id= onew.getAltId();
					%>
					command = "?command=Details";
					org_cod = "&altId="+"<%= id %>";
					<%
					}
				else if(ct.equals("camera_commercio")){
					org.aspcfs.modules.operatoriprivati.base.Organization onew ;
					onew = (org.aspcfs.modules.operatoriprivati.base.Organization)OrgDetails;
					id= onew.getOrgId();
					%>
					command = "?command=Dettaglio";
					org_cod = "&orgId="+"<%= id %>";
					<%
					}
				else if(ct.equals("laboratorihaccp")){
						org.aspcfs.modules.laboratorihaccp.base.Organization onew ;
						onew = (org.aspcfs.modules.laboratorihaccp.base.Organization)OrgDetails;
						id= onew.getOrgId();
						%>
						command = "?command=Details";
						org_cod = "&orgId="+"<%= id %>";
						<%
						}
				else if(ct.equals("punti_di_sbarco")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}
					else
					{
					
				org.aspcfs.modules.punti_di_sbarco.base.Organization onew ;
				onew = (org.aspcfs.modules.punti_di_sbarco.base.Organization)OrgDetails;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					}
				}
				else if(ct.equals("zonecontrollo")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}
					else
					{
					
				Organization onew ;
				onew = (org.aspcfs.modules.zonecontrollo.base.Organization)OrgDetails;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					}
				}
				else if(ct.equals("aziendeagricole")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}
					else
					{
					
				org.aspcfs.modules.aziendeagricole.base.Organization onew ;
				onew = (org.aspcfs.modules.aziendeagricole.base.Organization)OrgDetails;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					}
				}
				else if(ct.equals("acquedirete")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}
					else
					{
					
				org.aspcfs.modules.acquedirete.base.Organization onew ;
				onew = (org.aspcfs.modules.acquedirete.base.Organization)OrgDetails;
				id= onew.getOrgId();
				%>
				command = "?command=Details";
				org_cod = "&orgId="+"<%= id %>";
				<%
					}
				}
				else if(ct.equals("canili")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}}
				else if(ct.equals("colonie")){
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
					org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
					<%
					}}
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
				else if(ct.equals("canipadronali")){
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
						org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
						<%
						}
						else
						{
						
					org.aspcfs.modules.stabilimenti.base.Organization onew ;
					onew = (org.aspcfs.modules.stabilimenti.base.Organization)OrgDetails;
					id= onew.getOrgId();
					%>
					command = "?command=Details";
					org_cod = "&orgId="+"<%= id %>";
					<%
						}
					}
					else
						if(ct.equals("allevamenti")){
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
							org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
							<%
							}
						}else
							if(ct.equals("soa")){
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
								org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
								<%
								}
								else
								{
									
									org.aspcfs.modules.soa.base.Organization onew ;
									onew = (org.aspcfs.modules.soa.base.Organization)OrgDetails;
									id= onew.getOrgId();
									%>
									command = "?command=Details";
									org_cod = "&orgId="+"<%= id %>";
									<%									
								}
							}else if(ct.equals("osa")){
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
								org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
								<%
								}
								else
								{
								
								
								
								org.aspcfs.modules.osa.base.Organization onew ;
								onew = (org.aspcfs.modules.osa.base.Organization)OrgDetails;
								id= onew.getOrgId();
								%>
								command = "?command=Details";
								org_cod = "&orgId="+"<%= id %>";
								<%
							}
								}
							else
								if(ct.equals("farmacosorveglianza")){
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
									org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
									<%
									}
									else
									{
									
								org.aspcfs.modules.farmacosorveglianza.base.Organization onew ;
								onew = (org.aspcfs.modules.farmacosorveglianza.base.Organization)OrgDetails;
								id= onew.getOrgId();
								%>
								command = "?command=DetailsFcie";
								org_cod = "&idFarmacia="+"<%= id %>";
								<%
									}
								}
								else
									if(ct.equals("parafarmacie")){
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
										org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
										<%
										}
										else
										{
										
									org.aspcfs.modules.parafarmacie.base.Organization onew ;
									onew = (org.aspcfs.modules.parafarmacie.base.Organization)OrgDetails;
									id= onew.getOrgId();
									%>
									command = "?command=DetailsFcie";
									org_cod = "&idFarmacia="+"<%= id %>";
									<%
										}
									}
									else
	if(ct.equals("molluschibivalvi")){
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
		org_cod = "&aggiorna=true&isSalvata=false&idControllo="+"<%=request.getAttribute("idControllo")%>"+"&idC="+"<%=request.getAttribute("idC")%>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
		<%
		}
		else
		{
		
			
	
	if((Object)OrgDetails instanceof org.aspcfs.modules.molluschibivalvi.base.Organization)
	{
	org.aspcfs.modules.molluschibivalvi.base.Organization onew ;
	onew = (org.aspcfs.modules.molluschibivalvi.base.Organization)OrgDetails;
	id= onew.getId();
	%>
	command = "?command=Details";
	org_cod = "&orgId="+"<%= id %>";
	<%
	}else
	{
		org.aspcfs.modules.molluschibivalvi.base.Concessionario onew ;
		onew = (org.aspcfs.modules.molluschibivalvi.base.Concessionario)OrgDetails;
		id= onew.getId();
		
		%>
		command = "?command=Details";
		org_cod = "&orgId="+"<%= id %>";
		<%
	}
		}
	}
	
				
			
	
	
}

if(TicketDetails!=null){
	String ct = TicketDetails.getClass().toString();
	contesto = ct.split("[.]");
	ct = contesto[3];
	System.out.println("CONTESTO ticket: "+ct);

	if(ct.equals("vigilanza")){
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
		org_cod = "&aggiorna=true&stabId="+"<%= tnew.getIdStabilimento() %>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
		<%
		
		}
		else
		{
		org.aspcfs.modules.vigilanza.base.Ticket tnew ;
		tnew = (org.aspcfs.modules.vigilanza.base.Ticket)TicketDetails;
		int oid= tnew.getOrgId();
		int tid = tnew.getId();
		String msg = null ;
		if (request.getAttribute("MsgBdu")!= null)
			 msg = (String)request.getAttribute("MsgBdu");
		request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
		request.setAttribute("idNodo",request.getAttribute("idNodo"));
		request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
		request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
		
		
		%>
		
		
		command = "?command=TicketDetails";
		org_cod = "&customerSatisfaction=si&altId=<%=tnew.getAltId()%>&idStabilimentoopu="+"<%= tnew.getIdStabilimento() %>"+"&id="+"<%= tid %>"+"&orgId="+"<%= oid %>&idConcessionario="+"<%=request.getAttribute("idConcessionario")%>";
		<%}
		if(request.getAttribute("idMacchinetta")!=null)
		{%>
			org_cod += "&idmacchinetta=<%=request.getAttribute("idMacchinetta")%>";
		<%}
		if(request.getAttribute("idNodo")!=null)
		{%>
			org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
		<%}
		if(request.getAttribute("assetId")!=null)
		{%>
			org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
		<%}
		if (request.getAttribute("MsgBdu")!=null)
		{
			%>
			org_cod += "&msgbdu=<%=request.getAttribute("MsgBdu")%>";
			<%			
		}
		
		}else if(ct.equals("campioni")){
			

			org.aspcfs.modules.campioni.base.Ticket tnew ;
			tnew = (org.aspcfs.modules.campioni.base.Ticket)TicketDetails;
			int oid= tnew.getOrgId();
			int tid = tnew.getId();
			
			if (request.getAttribute("SchedaValutazione")!=null)
			{
				%>
				command = "?command=View";
				org_cod ="&idCampione=<%=tnew.getId()%>&stabId=<%=tnew.getIdStabilimento()%>";
				<%
			}
			else
			{
			
			%>
			command = "?command=TicketDetails";
			<%if(tnew.getIdStabilimento() > 0){%>
				org_cod ="&idC=<%=request.getAttribute("idC")%>&id=<%= tid %>&stabId=<%= tnew.getIdStabilimento() %>&altId=<%= tnew.getAltId() %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>";
			<%} else {%>
				org_cod ="&idC=<%=request.getAttribute("idC")%>&id=<%= tid %>&stabId=<%= tnew.getIdApiario() %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>";	
			<%}%>
			
			<%if(request.getAttribute("assetId")!=null)
			{%>
				org_cod += "&customerSatisfaction=si&assetId=<%=request.getAttribute("assetId")%>";
			<%}
			if(request.getAttribute("idNodo")!=null)
			{%>
				org_cod += "&customerSatisfaction=si&idNodo=<%=request.getAttribute("idNodo")%>";
			<%}
			}
			%>
			
			<% 
			}else if(ct.equals("prvvedimentinc")){
				

				org.aspcfs.modules.prvvedimentinc.base.Ticket tnew ;
				tnew = (org.aspcfs.modules.prvvedimentinc.base.Ticket)TicketDetails;
				int oid= tnew.getOrgId();
				int tid = tnew.getId();
				
				
				
				%>
				command = "?command=TicketDetails";
				org_cod ="&idC=<%=request.getAttribute("idC")%>&stabId=<%= tnew.getIdStabilimento() %>&id=<%= tid %>&orgId=<%= oid %>&altId=<%= tnew.getAltId() %>";
				<%if(request.getAttribute("assetId")!=null)
				{%>
					org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
				<%}
				if(request.getAttribute("idNodo")!=null)
				{%>
					org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
				<%}
				
				%>
				
				<% 
				}
		else if(ct.equals("tamponi")){
				org.aspcfs.modules.tamponi.base.Ticket tnew ;
				tnew = (org.aspcfs.modules.tamponi.base.Ticket)TicketDetails;
				int oid= tnew.getOrgId();
				int tid = tnew.getId();
				%>
				command = "?command=TicketDetails";
				org_cod ="&stabId=<%=tnew.getIdStabilimento()%>&idC=<%=request.getAttribute("idC")%>&id=<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>";
				<%if(request.getAttribute("assetId")!=null)
				{%>
					org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
				<%}
				if(request.getAttribute("idNodo")!=null)
				{%>
					org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
				<%}
				}else if(ct.equals("nonconformita")){
					org.aspcfs.modules.nonconformita.base.Ticket tnew ;
					tnew = (org.aspcfs.modules.nonconformita.base.Ticket)TicketDetails;
					int oid= tnew.getOrgId();
					int tid = tnew.getId();
					
					%>
					command = "?command=TicketDetails";
					org_cod = "&stabId=<%=tnew.getIdStabilimento()%>&idC=<%=request.getAttribute("idC")%>&id=<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>&altId=<%= tnew.getAltId() %>";
					<%if(request.getAttribute("assetId")!=null)
					{%>
						org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
					<%}
					if(request.getAttribute("idNodo")!=null)
					{%>
						org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
					<%}}
				else
					if(ct.equals("altriprovvedimenti")){
						org.aspcfs.modules.altriprovvedimenti.base.Ticket tnew ;
						tnew = (org.aspcfs.modules.altriprovvedimenti.base.Ticket)TicketDetails;
						int oid= tnew.getOrgId();
						int tid = tnew.getId();
						
						%>
						command = "?command=TicketDetails";
						org_cod = "&stabId=<%=tnew.getIdStabilimento()%>&idC=<%=request.getAttribute("idC")%>&id=<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>&altId=<%= tnew.getAltId() %>";
						<%if(request.getAttribute("assetId")!=null)
						{%>
							org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
						<%}
						if(request.getAttribute("idNodo")!=null)
						{%>
							org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
						<%}
						}
						else 
						if(ct.equals("osservazioni")){
							
							Osservazioni tnew ;
							tnew = (Osservazioni) TicketDetails;
							int oid= tnew.getOrgId();
							int tid = tnew.getId();
							
							%>
						
							command = "?command=TicketDetails";
							org_cod = "&idC=<%=request.getAttribute("idC")%>&id=<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>&altId=<%= tnew.getAltId() %>";
							<%if(request.getAttribute("assetId")!=null)
							{%>
								org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
							<%}
							if(request.getAttribute("idNodo")!=null)
							{%>
								org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
							<%}
							}
						else
						if(ct.equals("sanzioni")){
						org.aspcfs.modules.sanzioni.base.Ticket tnew ;
						tnew = (org.aspcfs.modules.sanzioni.base.Ticket)TicketDetails;
						int oid= tnew.getOrgId();
						int tid = tnew.getId();
						%>

						
						command = "?command=TicketDetails";
						org_cod = "&stabId=<%=tnew.getIdStabilimento()%>&id=<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>&altId=<%= tnew.getAltId() %>";
						
						
						<%	if(request.getAttribute("idC")!=null)
						{%>
						org_cod +="&idC="+"<%=request.getAttribute("idC")%>";
							<%}%>
							<%	if(request.getAttribute("idNC")!=null)
							{%>
							org_cod +="&idNC="+"<%=request.getAttribute("idNC")%>";
								<%}%>
								<%if(request.getAttribute("assetId")!=null)
								{%>
									org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
								<%}
								if(request.getAttribute("idNodo")!=null)
								{%>
									org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
								<%}
						}else if(ct.equals("sequestri")){
							request.setAttribute("idC",request.getAttribute("idC"));
							request.setAttribute("idNC",request.getAttribute("idNC"));
							org.aspcfs.modules.sequestri.base.Ticket tnew ;
							tnew = (org.aspcfs.modules.sequestri.base.Ticket)TicketDetails;
							int oid= tnew.getOrgId();
							int tid = tnew.getId();
							%>
							command = "?command=TicketDetails";
							org_cod = "&stabId=<%=tnew.getIdStabilimento()%>&id=<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>&altId=<%= tnew.getAltId() %>";
							<%	if(request.getAttribute("idC")!=null)
							{%>
							org_cod +="&idC="+"<%=request.getAttribute("idC")%>";
								<%}%>
								<%	if(request.getAttribute("idNC")!=null)
								{%>
								org_cod +="&idNC="+"<%=request.getAttribute("idNC")%>";
									<%}
								if(request.getAttribute("idNodo")!=null)
								{%>
									org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
								<%}
							}else if(ct.equals("reati")){
								request.setAttribute("idC",request.getAttribute("idC"));
								request.setAttribute("idNC",request.getAttribute("idNC"));
								org.aspcfs.modules.reati.base.Ticket tnew ;
								tnew = (org.aspcfs.modules.reati.base.Ticket)TicketDetails;
								int oid= tnew.getOrgId();
								int tid = tnew.getId();
								%>
								command = "?command=TicketDetails";
								org_cod = "&stabId=<%=tnew.getIdStabilimento()%>&idNC=<%=request.getAttribute("idNC")%>&id="+"<%= tid %>&orgId=<%= oid %>&idConcessionario=<%=request.getAttribute("idConcessionario")%>&idMacchinetta=<%=request.getAttribute("idMacchinetta")%>&aslMacchinetta=<%=request.getAttribute("aslMacchinetta")%>&altId=<%= tnew.getAltId() %>";
								<%	if(request.getAttribute("idC")!=null)
								{%>
								org_cod +="&idC="+"<%=request.getAttribute("idC")%>";
									<%}%>
									<%	if(request.getAttribute("idNC")!=null)
									{%>
									org_cod +="&idNC="+"<%=request.getAttribute("idNC")%>";
										<%}%>
										<%if(request.getAttribute("assetId")!=null)
										{%>
											org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
										<%}
										if(request.getAttribute("idNodo")!=null)
										{%>
											org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
										<%}
								}else if(ct.equals("followup")){
									request.setAttribute("idC",request.getAttribute("idC"));
									request.setAttribute("idNC",request.getAttribute("idNC"));
									org.aspcfs.modules.followup.base.Ticket tnew ;
									tnew = (org.aspcfs.modules.followup.base.Ticket)TicketDetails;
									int oid= tnew.getOrgId();
									int tid = tnew.getId();
									%>
									command = "?command=TicketDetails";
									org_cod = "&stabId=<%=tnew.getIdStabilimento()%>&id=<%= tid %>&orgId=<%= oid %>&altId=<%= tnew.getAltId() %>";
									<%	if(request.getAttribute("idC")!=null)
									{%>
									org_cod +="&idC="+"<%=request.getAttribute("idC")%>";
										<%}%>
										<%	if(request.getAttribute("idNC")!=null)
										{%>
										org_cod +="&idNC="+"<%=request.getAttribute("idNC")%>";
											<%}
										if(request.getAttribute("idNodo")!=null)
										{%>
											org_cod += "&idNodo=<%=request.getAttribute("idNodo")%>";
										<%}
									}else if(ct.equals("allerte")){
										org.aspcfs.modules.allerte.base.Ticket tnew ;
										tnew = (org.aspcfs.modules.allerte.base.Ticket)TicketDetails;
										int tid = tnew.getId();
													
								
										if(((String)""+request.getAttribute("ListaCommercializzazzione")).equals("1"))
										{
										%>
										command = "?command=UploadToInsert";
										org_cod = "&idAllerta="+ "<%=request.getAttribute("idAllerta")%>"+"&tId="+"<%=request.getAttribute("tId")%>"+"&ListaCommercializzazzione="+"<%=request.getAttribute("ListaCommercializzazzione")%>"+"&folderId="+"<%=request.getAttribute("folderId")%>"+"&parentId="+"<%=request.getAttribute("parentId")%>";
										<%
										}
										else
										{
											%>
											command = "?command=Details";
											org_cod = "&id="+<%=tid%> ;
											<%
										}
										}else if(ct.equals("cessazionevariazione")){
											org.aspcfs.modules.cessazionevariazione.base.Ticket tnew ;
											tnew = (org.aspcfs.modules.cessazionevariazione.base.Ticket)TicketDetails;
											int tid = tnew.getId();
											%>
											command = "?command=TicketDetails";
											org_cod = "&id="+"<%= tid %>";
											<%
											}else if(ct.equals("audit")){
												request.setAttribute("idC",request.getAttribute("idC"));
												request.setAttribute("idNC",request.getAttribute("idNC"));
												org.aspcfs.checklist.base.Audit tnew ;
												tnew = (org.aspcfs.checklist.base.Audit)request.getAttribute("Audit");
												int oid= tnew.getOrgId();
												int tid = tnew.getId();
												//String salvataggio_checklist = (String) request.getAttribute("SalvataggioChecklist");
												%>
												command = "?command=View&SalvataggioChecklist=OK";
												org_cod = "aggiorna=true&&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
												<%	if(request.getAttribute("idC")!=null)
												{%>
												org_cod +="&idC="+"<%=request.getAttribute("idC")%>";
													<%}%>
													<%	if(request.getAttribute("idNC")!=null)
													{%>
													org_cod +="&idNC="+"<%=request.getAttribute("idNC")%>";
														<%}%>
														<%if(request.getAttribute("assetId")!=null)
														{%>
															org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
														<%}%>
												<%
}
else if(ct.equals("farmacosorveglianza")){
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
		org_cod = "&aggiorna=true&id="+"<%= tid %>"+"&orgId="+"<%= oid %>";
		<%
		}
		else
		{
		org.aspcfs.modules.vigilanza.base.Ticket tnew ;
		tnew = (org.aspcfs.modules.vigilanza.base.Ticket)TicketDetails;
		int oid= tnew.getOrgId();
		int tid = tnew.getId();
	
		request.setAttribute("idMacchinetta",request.getAttribute("idMacchinetta"));
		request.setAttribute("idConcessionario",request.getAttribute("idConcessionario"));
		request.setAttribute("aslMacchinetta", request.getAttribute("aslMacchinetta"));
		
		
		%>
		command = "?command=TicketDetails";
		org_cod = "&id="+"<%= tid %>"+"&orgId="+"<%= oid %>&idConcessionario="+"<%=request.getAttribute("idConcessionario")%>";
		<%}
		if(request.getAttribute("idMacchinetta")!=null)
		{%>
			org_cod += "&idmacchinetta=<%=request.getAttribute("idMacchinetta")%>";
		<%}
		if(request.getAttribute("assetId")!=null)
		{%>
			org_cod += "&assetId=<%=request.getAttribute("assetId")%>";
		<%}
		
		%>
		<%
		}
else
{

	
}
												
	
}

if(request.getAttribute("Tree")!=null)
{

String nomeTabella =(String )request.getAttribute("nomeTabella");
String campoId =(String )request.getAttribute("campoId");
String campoPadre =(String )request.getAttribute("campoPadre");
String campoDesc =(String )request.getAttribute("campoDesc");
String campoLivello =(String )request.getAttribute("campoLivello");

	
	%>
	

	url_ = 'Tree.do' ;
	command = '?command=DettaglioTree&nomeTabella=<%=nomeTabella%>&campoId=<%=campoId%>&campoPadre=<%=campoPadre%>&campoDesc=<%=campoDesc%>&campoLivello=<%=campoLivello%>' ; 
	org_cod = '' ;

<%
}
%>


var url_= location.href;

url_nuovo = new Array();
url_nuovo=url_.split("?command=");
url_ = url_nuovo[0];

commandold = url_nuovo[1].split("&")[0];
urlold_ =url_.split("/")[url_.split("/").length-1];

var str = "Audit";

u =url_.split('/');
b = u[4];
if ( b == 'CameraCommercio.do')
{
	url_ = url_.split(b)[0]+'Accounts.do';
}
if ( b == 'ImpresePregresso.do')
{
	url_ = url_.split(b)[0]+'Accounts.do';
}

if ( b.indexOf(str)>0)
{
	url_ = url_.split(b)[0]+'AccountVigilanza.do';
}
<%
String chiusura =(String) request.getAttribute("chiusura");
if(chiusura!=null)
{
%>
url_='OiaVigilanza.do';
<%}%>

var url_to_redirect = url_+command+org_cod+"&commandOld="+urlold_+";"+commandold + "&TimeIni=<%=(request.getAttribute("TimeIni")!=null ? ((Timestamp)request.getAttribute("TimeIni")).getTime()+"" : "" )%>";
<%
	if(request.getAttribute("esitoValidazione")!=null)
	{
%>
		url_to_redirect+= "&esitoValidazione=<%=(String)request.getAttribute("esitoValidazione")%>";
<%
	}
%>
<%-- url_to_redirect+= "&codice_preaccettazione=<%=(String)request.getAttribute("codice_preaccettazione")%>"; --%>


window.location.href = url_to_redirect;
</script>

</body>



