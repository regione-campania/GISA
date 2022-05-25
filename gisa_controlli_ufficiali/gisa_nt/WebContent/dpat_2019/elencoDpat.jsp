<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatRisorseStrumentali"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo"%>
<%@page import="org.aspcfs.modules.dpat2019.base.Dpat"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>
<jsp:useBean id="elencoDpat" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="idPadre" class="java.lang.String" scope="request"/>
<jsp:useBean id="idAsl" class="java.lang.String" scope="request"/>
<jsp:useBean id="edit" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp"%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="Oia.do">Modellatore Organizzazione ASL</a> &gt <a href="dpat2019.do?&idAsl=<%=idAsl%>&idPadre=<%=idPadre%>&anno=corrente">DPAT</a> &gt Elenco All.5
			</td>
		</tr>
	</table>
<%  if (elencoDpat.size()>0){%>
	Elenco All. 5 Carichi di Lavoro : <br>  
<%		for (int i=0;i<elencoDpat.size();i++){
			Dpat d = (Dpat)elencoDpat.get(i);%>
			<a href="dpat2019.do?command=DpatDetailGenerale&idAsl=<%=d.getIdAsl()%>&idPadre=<%=idPadre%>&anno=<%=d.getAnno()%>&edit=<%=edit%>"><%=d.getAnno()%></a><br>
<% 		}
	} else {%>
		DPAT NON PRESENTE
<%  }%>
<br><br>


<%  
ArrayList<DpatStrumentoCalcolo> elencoDpatSC = (ArrayList<DpatStrumentoCalcolo>) request.getAttribute("ListaDpatSC");
if (elencoDpatSC.size()>0){%>
DPAT ALL. 2 STRUMENTI DI CALCOLO

<%		for (int i=0;i<elencoDpatSC.size();i++){
	DpatStrumentoCalcolo d = (DpatStrumentoCalcolo)elencoDpatSC.get(i);%>
			<a href="DpatSDC2019.do?command=AddModify&idAsl=<%=d.getIdAsl()%>&idPadre=<%=idPadre%>&anno=<%=d.getAnno()%>&edit=view"><%=d.getAnno()%></a>
<% 		}
	} else {%>
		DPAT ALL. 2 STRUMENTI DI CALCOLO
<%  }%>
<br><br>

<%
ArrayList<DpatRisorseStrumentali> elencoDpatRS = (ArrayList<DpatRisorseStrumentali>) request.getAttribute("ListaDpatRS");
if (elencoDpatRS.size()>0){%>
		DPAT ALL. 5 RISORSE STRUMENTALI
<%		for (int i=0;i<elencoDpatRS.size();i++){
	DpatRisorseStrumentali d = (DpatRisorseStrumentali)elencoDpatRS.get(i);%>
			<a href="DpatRS.do?command=AddModify&idAsl=<%=d.getIdAsl()%>&edit=view&idPadre=<%=idPadre%>&anno=<%=d.getAnno()%>"><%=d.getAnno()%></a>
<% 		}
	} else {%>
		DPAT ALL. 5 RISORSE STRUMENTALI
<%  }%>
<br><br>



