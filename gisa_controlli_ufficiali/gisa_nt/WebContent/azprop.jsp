<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="java.sql.SQLException"%>
<%@page import="it.izs.bdn.bean.InfoPersonaBean"%>
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

try
{
db =GestoreConnessioni.getConnection();

String sql = "SELECT DISTINCT upper(cf_correntista) FROM organization where tipologia = 2 and trashed_date is null and lower(cf_correntista) not in (select lower(cf) from operatori_allevamenti)"+
" UNION "+
"SELECT DISTINCT upper(cf_detentore) FROM organization where tipologia = 2 and trashed_date is null and lower(cf_detentore) not in (select lower(cf) from operatori_allevamenti)";
PreparedStatement pst = db.prepareStatement(sql);
out.print("lettura delle aziende mancanti in corso .. <br>");
out.print(pst.toString());
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









try
{
db =GestoreConnessioni.getConnection();

String sql = "SELECT cf from operatori_allevamenti where comune is null";
PreparedStatement pst = db.prepareStatement(sql);
out.print("lettura delle aziende mancanti in corso .. <br>");
out.print(pst.toString());
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








try
{
InfoPersonaBean aziendaSerch = new InfoPersonaBean();
for (String cod : listaAziendeMancanti)
{
	db = GestoreConnessioni.getConnection();
	out.print("RECUPERO DA SERVIZIO "+cod+"<br>");
	out.flush();

	InfoPersonaBean RET = aziendaSerch.getListaAziende(cod);
	if (RET!=null)
	{
	String insertScipt = "insert into operatori_allevamenti (cf,nominativo,indirizzo,cap,comune,prov)"
					+" values (?,?,?,?,?,?)";
			
			try
			{
				
				
				PreparedStatement pst = db.prepareStatement(insertScipt);
				pst.setString(1, cod);
				pst.setString(2, RET.getNominativo());
				pst.setString(3, RET.getIndirizzo());
				pst.setString(4, RET.getCap());
				pst.setString(5, RET.getComune());
				pst.setString(6, RET.getSiglaProvincia());
				
				pst.execute();
				
			}
			catch(SQLException e)
			{
				out.print(e.getMessage());
			
			
		}
		out.print("FINE SERVIZIO OK "+cod+"<br><br>");

	}
	else
	{
		out.print("FINE SERVIZIO AZIENDA NON TROVATA KO<br><br>");

	}
	out.flush();

	GestoreConnessioni.freeConnection(db);
}


}
catch(Exception e)
{
	e.printStackTrace();
}






%>