<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="it.izs.bdn.bean.InfoAziendaBean"%>
<%@page import="it.izs.bdn.bean.InfoAllevamentoBean"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.aspcfs.utils.GestoreConnessioni"%>
<%

Connection db  = null ;
ArrayList<String> listaAziendeMancanti = new ArrayList<String>();
ArrayList<String> listaAziendeSenzaComune = new ArrayList<String>();
/*
try
{
db =GestoreConnessioni.getConnection();

String sql = "SELECT DISTINCT account_number FROM organization where tipologia = 2 and trashed_date is null and account_number !='027BN184' and account_number !='0000000' and account_number !='120AV103' and account_number not in (select cod_azienda from aziende)";
PreparedStatement pst = db.prepareStatement(sql);
out.print("lettura delle aziende mancanti in corso .. <br>");
out.flush();
ResultSet rs = pst.executeQuery();
while ( rs.next())
{
	listaAziendeMancanti.add(rs.getString(1));
}
out.print("fine lettura <br>");
out.flush();
}
catch(Exception e)
{
	e.printStackTrace();
}
finally
{
	GestoreConnessioni.freeConnection(db);
}

*/


try
{
db =GestoreConnessioni.getConnection();

String sql = "select cod_azienda from aziende where cod_comune_azienda is null or cod_comune_azienda=''";
PreparedStatement pst = db.prepareStatement(sql);
out.print("lettura delle aziende mancanti in corso .. <br>");
out.flush();
ResultSet rs = pst.executeQuery();
while ( rs.next())
{
	listaAziendeSenzaComune.add(rs.getString(1));
}
out.print("fine lettura <br>");
out.flush();
}
catch(Exception e)
{
	e.printStackTrace();
}
finally
{
	GestoreConnessioni.freeConnection(db);
}


InfoAziendaBean aziendaSerch = new InfoAziendaBean();/*
for (String cod : listaAziendeMancanti)
{
	db = GestoreConnessioni.getConnection();
	out.print("RECUPERO DA SERVIZIO "+cod+"<br>");
	out.flush();

	InfoAziendaBean RET = aziendaSerch.getInfoAllevamentoBean(cod);
	if (RET!=null)
	{
		RET.insert(db);
		out.print("FINE SERVIZIO OK "+cod+"<br><br>");

	}
	else
	{
		out.print("FINE SERVIZIO AZIENDA NON TROVATA KO<br><br>");

	}
	out.flush();

	GestoreConnessioni.freeConnection(db);
}*/



for (String cod : listaAziendeSenzaComune)
{
	out.print("ok AGGIORNATO COMUNE "+cod + " <BR> ");

	InfoAziendaBean RET = aziendaSerch.getInfoAllevamentoBean(cod);
	if (RET!=null)
	{
		db = GestoreConnessioni.getConnection();
		PreparedStatement pst = db.prepareStatement("UPDATE aziende set cod_comune_azienda = ? , prov_sede_azienda=? where cod_azienda ilike ?");
		pst.setString(1, RET.getComuneAziendaCalcolato());
		pst.setString(2, RET.getSiglaProvinciaAziendaCalcolato());
		pst.setString(3, cod);
		pst.execute();
		out.print("ok AGGIORNATO COMUNE"  + " <BR> ");
		out.flush();

		GestoreConnessioni.freeConnection(db);

	}
	
	
}






%>