<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<%--
<input type = "button" value="NUOVA SCIA" onclick="location.href='OpuStab.do?command=Add&tipoInserimento=<%=Stabilimento.TIPO_SCIA_REGISTRABILI%>&stato=<%=Stabilimento.STATO_REGISTRAZIONE_ND%>'"/>
<input type = "button" title="CLICCANDO QUESTO BOTTONE, SI POTRANNO INSERIRE GLI STABILIMENTI PRESENTI SUL TERRITORIO E NON ANCORA REGISTRATI IN GISA" value="STABILIMENTO PREGRESSO" onclick="location.href='OpuStab.do?command=Add&tipoInserimento=<%=Stabilimento.TIPO_SCIA_REGISTRABILI%>&stato=<%=Stabilimento.STATO_AUTORIZZATO%>'"/>
--%>



<input type = "button" value="ATTIVITA' RICHIEDENTI SCIA" title="Operazioni SUAP a carico dell'ASL in quanto inoltrate dai SUAP dei Comuni di categoria 1 e 2" onclick="location.href='OpuStab.do?command=Add&tipoInserimento=<%=Stabilimento.TIPO_SCIA_REGISTRABILI%>&stato=<%=Stabilimento.STATO_REGISTRAZIONE_ND%>'"/>
<input type = "button" title="" value="PREGRESSO" onclick="location.href='OpuStab.do?command=CaricaImport'"/>
</body>
</html>