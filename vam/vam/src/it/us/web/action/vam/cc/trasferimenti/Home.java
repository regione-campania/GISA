/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.RowSelect;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.ClinicaNoH;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.ClinicaDAO;
import it.us.web.dao.vam.ClinicaDAONoH;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.permessi.Permessi;
import it.us.web.util.vam.ComparatorTrasferimenti;
import it.us.web.util.vam.ComparatorTrasferimentiNoH;

public class Home extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "TRASFERIMENTI", "MAIN", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		BGuiView gui = GuiViewDAO.getView( "TRASFERIMENTI", "CRIUV", "MAIN" );
		
		if( Permessi.can( connection, utente, gui, "w" ) || Permessi.can( connection, utente, gui, "r" )) //se è l'utente criuv
		{
			//Filtri e ordinamenti su lista trasferimenti
			String filtroMc = "";
			if(stringaFromRequest("tr_f_cartellaClinica.identificativoAnimale")!=null)
				filtroMc = "and an.identificativo ilike '%" + stringaFromRequest("tr_f_cartellaClinica.identificativoAnimale")+ "%'";
			
			TableFacade tableFacade = new TableFacadeImpl("tr", req);
			int totalRows = 0;
			
			String sqlCount = " select count(*) as total_rows from trasferimento t " +  
			        " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null " +  
					" left join accettazione acc on acc.id = cc.accettazione and acc.trashed_date is null " +  
			        " left join animale an on an.id = acc.animale and an.trashed_date is null " +
					" where t.trashed_date is null "  + filtroMc ; 
			
			PreparedStatement st1 = connection.prepareStatement(sqlCount);
			ResultSet rs1 = st1.executeQuery();

			if(rs1.next())
				totalRows = rs1.getInt("total_rows");
			RowSelect rowSelect = tableFacade.setTotalRows(totalRows);
			
			ArrayList<TrasferimentoNoH> trasferimenti = TrasferimentoDAO.getTraferimentiAll(connection, rowSelect, filtroMc);
			Collections.sort(trasferimenti, new ComparatorTrasferimentiNoH());
			
			req.setAttribute("limit", tableFacade.getLimit());
			req.setAttribute( "trasferimenti", trasferimenti );
			gotoPage( "/jsp/vam/cc/trasferimenti/homeCriuv.jsp" );
		}
		else
		{
			//trasferimenti in uscita
			//trsferimenti in ingresso
			// NB. vengono recuperati direttamente dalla clinica associata all'utente
			
			//pezzotto per aggirare LazyInitializationException
			//Clinica clinica = (Clinica) persistence.find( Clinica.class, utente.getClinica().getId() );
			
			
			
			//Filtri e ordinamenti su lista trasferimenti uscita
			String filtroMc = "";
			if(stringaFromRequest("trOut_f_cartellaClinica.identificativoAnimale")!=null)
				filtroMc = "and an.identificativo ilike '%" + stringaFromRequest("trOut_f_cartellaClinica.identificativoAnimale")+ "%'";
			
			//Filtri e ordinamenti su lista trasferimenti ingresso
			String filtroMc2 = "";
			if(stringaFromRequest("trIn_f_cartellaClinica.identificativoAnimale")!=null)
				filtroMc2 = "and an.identificativo ilike '%" + stringaFromRequest("trIn_f_cartellaClinica.identificativoAnimale")+ "%'";
			
			ClinicaNoH clinica = ClinicaDAONoH.getClinica(utente.getClinica().getId(),connection );
			
			TableFacade tableFacadeOut = new TableFacadeImpl("trOut", req);
			TableFacade tableFacadeIn = new TableFacadeImpl("trIn", req);
			int totalRowsOut = 0;
			int totalRowsIn  = 0;
			
			
			String sqlCount = " select count(*) as total_rows "
					+  " from trasferimento t "
					+  " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null "
					+  " left join accettazione acc on acc.id = cc.accettazione and acc.trashed_date is null "
					+  " left join animale an on an.id = acc.animale and an.trashed_date is null "
					+  " where t.trashed_date is null " + filtroMc
					+  " and t.clinica_origine = ? "; 
			
			PreparedStatement st1 = connection.prepareStatement(sqlCount);
			st1.setInt(1, clinica.getId());
			ResultSet rs1 = st1.executeQuery();

			if(rs1.next())
				totalRowsOut = rs1.getInt("total_rows");
			RowSelect rowSelectOut = tableFacadeOut.setTotalRows(totalRowsOut);
			
			
			
			sqlCount = " select count(*) as total_rows "
					+  " from trasferimento t "
					+  " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null "
					+  " left join accettazione acc on acc.id = cc.accettazione and acc.trashed_date is null "
					+  " left join animale an on an.id = acc.animale and an.trashed_date is null "
					+  " where t.trashed_date is null " + filtroMc2
					+  " and t.clinica_destinazione = ? "; 
			
			st1 = connection.prepareStatement(sqlCount);
			st1.setInt(1, clinica.getId());
			rs1 = st1.executeQuery();

			if(rs1.next())
				totalRowsIn = rs1.getInt("total_rows");
			RowSelect rowSelectIn = tableFacadeIn.setTotalRows(totalRowsIn);
			
			
			clinica.setTrasferimentiUscita( TrasferimentoDAO.getTrasferimenti("U",utente.getClinica(),null,connection, rowSelectOut, filtroMc));
			clinica.setTrasferimentiIngresso( TrasferimentoDAO.getTrasferimenti("I",utente.getClinica(),null,connection, rowSelectIn, filtroMc2));

			
			req.setAttribute( "clinica", clinica );
			req.setAttribute("limitOut", tableFacadeOut.getLimit());
			req.setAttribute("limitIn", tableFacadeIn.getLimit());
			gotoPage( "/jsp/vam/cc/trasferimenti/home.jsp" );
		}
		
	}
}
