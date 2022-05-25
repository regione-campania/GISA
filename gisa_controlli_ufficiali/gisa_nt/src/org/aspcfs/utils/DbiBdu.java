/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.operatori_commerciali.base.Organization;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.util.imports.DbUtil;
import org.aspcfs.modules.vigilanza.base.Ticket;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

public class DbiBdu {



	public static String inserisci_controlo_canile(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo ) throws SQLException
	{
		ResultSet rs = db.prepareStatement("(select org_id_c from organization where org_id = "+orgId+")").executeQuery();
		int orgidc = -1 ;
		if (rs.next())
		{
			orgidc = rs.getInt(1);
		}
		String msg = "" ;
		Connection dbbdu = null ;
		try
		{
			
			dbbdu = GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from inserisci_controllo_canile (?,?,?::timestamp,?)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, orgidc);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				msg = rsbdu.getString(1);
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo canile in BDU "+e.getMessage());
			msg = e.getMessage();
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return msg ;
	}
	
	
	public static String inserisci_controlo_canile_opu(ActionContext context, Connection db,int idStabilimentoGisa ,int idControllo , Timestamp dataControllo , String tipoControllo ) throws SQLException
	{
		
		String msg = "" ;
		Connection dbbdu = null ;
		try
		{
			
			dbbdu  =GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from inserisci_controllo_canile_opu (?,?,?::timestamp,?)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idStabilimentoGisa);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				msg = rsbdu.getString(1);
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo canile in BDU "+e.getMessage());
			msg = e.getMessage();
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return msg ;
	}
	
	
	public static Canile getStatoCanile(ActionContext context, Connection db,Integer idStabilimentoGisa, Integer orgId  ) throws SQLException
	{
		Connection dbbdu = null;
		Canile canile = null;
		
		try
		{
			
			dbbdu  = GestoreConnessioni.getConnectionBdu();

			String select = "select * from public_functions.get_stato_canile (?,?)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(select);
			pst1.setInt(1, idStabilimentoGisa);
			pst1.setInt(2, orgId);
			ResultSet rsbdu =  pst1.executeQuery();
			
			if (rsbdu.next())
			{
				canile = new Canile();
				canile.setDataFine(rsbdu.getTimestamp("data_fine"));
				canile.setDataOperazioneBlocco(rsbdu.getTimestamp("data_operazione"));
				canile.setDataRiattivazioneBlocco(rsbdu.getTimestamp("data_riattivazione"));
				canile.setDataSospensioneBlocco(rsbdu.getTimestamp("data_sospensione"));
				canile.setBloccato(rsbdu.getBoolean("bloccato"));
				canile.setMqDisponibili(rsbdu.getInt("mq_disponibili"));
				canile.setOccupazioneAttuale(rsbdu.getFloat("occupazione_attuale"));
				canile.setNumeroCaniVivi(rsbdu.getInt("numero_cani_vivi"));
			}
		}
		catch(SQLException e)
		{
			System.out.println("Errore reupero stato canile BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return canile;
	}

	
	
	public static String inserisci_controlo_colonia(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo ) throws SQLException
	{
		ResultSet rs = db.prepareStatement("(select org_id_c from organization where org_id = "+orgId+")").executeQuery();
		int orgidc = -1 ;
		if (rs.next())
		{
			orgidc = rs.getInt(1);
		}
		String msg = "" ;
		Connection dbbdu = null ;
		
		try
		{
				dbbdu =GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from inserisci_controllo_colonia (?,?,?::timestamp,?)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, orgidc);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				msg = rsbdu.getString(1);
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo canile in BDU "+e.getMessage());
			msg = e.getMessage();
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return msg ;
	}
	public static String aggiorna_controlo_canile(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{
		Connection dbbdu = null ;
		String msg = "" ;
		ResultSet rs = db.prepareStatement("(select org_id_c from organization where org_id = "+orgId+")").executeQuery();
		int orgidc = -1 ;
		if (rs.next())
		{
			orgidc = rs.getInt(1);
		}
		
		try
		{
		
				dbbdu = GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_canile (?,?::timestamp,?,?::timestamp,?::timestamp)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			pst1.setTimestamp(++j, closed);
			pst1.setTimestamp(++j, cancellato);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));

		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return msg ;
	}
	
	

	
	
	public static String aggiorna_controlo_colonia(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{
		Connection dbbdu = null ;
		String msg = "" ;
		ResultSet rs = db.prepareStatement("(select org_id_c from organization where org_id = "+orgId+")").executeQuery();
		
		
		try
		{
			
			
			dbbdu = GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_colonia (?,?::timestamp,?,?::timestamp,?::timestamp)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			pst1.setTimestamp(++j, closed);
			pst1.setTimestamp(++j, cancellato);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));

		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo colonia in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return msg ;
	}




	public static String cancella_controlo_canile(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{
		String msg = "" ;
		
		String passedId = context.getRequest().getParameter("id");
		Ticket thisTicket = new Ticket(db, Integer.parseInt(passedId));
		
		Connection dbbdu = null;
		try
		{
			
			dbbdu = GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_canile (?,?::timestamp,?,?::timestamp,current_date)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, thisTicket.getId());
			pst1.setTimestamp(++j,thisTicket.getAssignedDate());
			pst1.setString(++j, thisTicket.getTipoControllo());
			pst1.setTimestamp(++j, thisTicket.getClosed());
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));


		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}

		return msg ;
	}
	
	
	public static String cancella_controlo_colonia(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{
		String msg = "" ;
		
		String passedId = context.getRequest().getParameter("id");
		Ticket thisTicket = new Ticket(db, Integer.parseInt(passedId));
		Connection dbbdu = null;
		try
		{
			
			dbbdu =GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_colonia (?,?::timestamp,?,?::timestamp,current_date)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, thisTicket.getId());
			pst1.setTimestamp(++j,thisTicket.getAssignedDate());
			pst1.setString(++j, thisTicket.getTipoControllo());
			pst1.setTimestamp(++j, thisTicket.getClosed());
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));


		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo colonia in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}

		return msg ;
	}


	public static String chiudi_controlo_canile(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{

		String msg = "" ;

		Connection dbbdu = null ;
		
		try
		{
			
			dbbdu = GestoreConnessioni.getConnectionBdu();
			
			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_canile (?,?::timestamp,?,current_timestamp,?::timestamp)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			pst1.setTimestamp(++j, cancellato);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));


		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}

		return msg ;
	}

	

	public static String chiudi_controlo_colonia(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{

		String msg = "" ;

		Connection dbbdu = null ;

		try
		{
			
			dbbdu = GestoreConnessioni.getConnectionBdu();
			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_colonia (?,?::timestamp,?,current_timestamp,?::timestamp)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			pst1.setTimestamp(++j, cancellato);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));


		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}

		return msg ;
	}



	public static String apri_controlo_canile(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{

		String msg = "" ;
		Connection dbbdu = null;

		
		
		try
		{
			dbbdu = GestoreConnessioni.getConnectionBdu();


			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_canile (?,?::timestamp,?,null::timestamp,null::timestamp)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}


		return msg ;
	}

	
	public static String apri_controlo_colonia(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{

		String msg = "" ;
		Connection dbbdu = null;


		
		
		try
		{
			
			
			dbbdu =GestoreConnessioni.getConnectionBdu();



			int j = 0 ;
			String inser_bdu = "select * from aggiorna_controllo_colonia (?,?::timestamp,?,null::timestamp,null::timestamp)";
			PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
			pst1.setInt(++j, idControllo);
			pst1.setTimestamp(++j,dataControllo);
			pst1.setString(++j, tipoControllo);
			ResultSet rsbdu =  pst1.executeQuery();
			if (rsbdu.next())
				context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}


		return msg ;
	}



	public static String inserisci_controlo_operatore_commerciale(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo ) throws SQLException
	{
		ResultSet rs = db.prepareStatement("(select org_id_c from organization where org_id = "+orgId+")").executeQuery();
		int orgidc = -1 ;
		if (rs.next())
		{
			orgidc = rs.getInt(1);
		}
		
		Connection dbbdu = null ;
		try
		{
			dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from inserisci_controllo_operatore_commerciale (?,?,?::timestamp,?)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, orgidc);
		pst1.setInt(++j, idControllo);
		pst1.setTimestamp(++j,dataControllo);
		pst1.setString(++j, tipoControllo);
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		
	
		
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return null ;
	}
	
	
	public static String inserisci_controlo_operatore_commerciale_opu(ActionContext context, Connection db,int idStabilimentoGisa ,int idControllo , Timestamp dataControllo , String tipoControllo ) throws SQLException
	{
		
		Connection dbbdu = null ;
		try
		{
			dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from inserisci_controllo_operatore_commerciale_opu (?,?,?::timestamp,?)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, idStabilimentoGisa);
		pst1.setInt(++j, idControllo);
		pst1.setTimestamp(++j,dataControllo);
		pst1.setString(++j, tipoControllo);
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		
	
		
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return null ;
	}
	

	public static String aggiorna_controlo_operatore_commerciale(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{
		

		Connection dbbdu = null ;
		try
		{
		dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from aggiorna_controllo_operatori_commerciali (?::integer,?::timestamp without time zone,?::text,current_timestamp ::timestamp without time zone,?::timestamp without time zone)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, idControllo);
		pst1.setTimestamp(++j,dataControllo);
		pst1.setString(++j, tipoControllo);
		pst1.setTimestamp(++j, closed);
		pst1.setTimestamp(++j, cancellato);
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		
	
		
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		
		return null ;
	}

	
	
	
	
	
	
	
	
	
	
	public static String cancella_controlo_operatore_commerciale(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{
		Connection dbbdu = null;
		try
		{
			dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from aggiorna_controllo_operatori_commerciali (?::integer,?::timestamp without time zone,?::text,current_timestamp ::timestamp without time zone,?::timestamp without time zone)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, idControllo);
		pst1.setTimestamp(++j,dataControllo);
		pst1.setString(++j, tipoControllo);
		pst1.setTimestamp(++j, closed);
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
			
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		
	return null ;
	
	}

	public static String chiudi_controlo_operatore_commerciale(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{

		Connection dbbdu = null ;
		try
		{
		
		dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from aggiorna_controllo_operatori_commerciali (?::integer,?::timestamp without time zone,?::text,current_timestamp ::timestamp without time zone,?::timestamp without time zone)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, idControllo);
		pst1.setTimestamp(++j,dataControllo);
		pst1.setString(++j, tipoControllo);

		pst1.setTimestamp(++j, cancellato);
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		
	
		
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return null ;
	}



	public static String apri_controlo_operatore_commerciale(ActionContext context, Connection db,int orgId ,int idControllo , Timestamp dataControllo , String tipoControllo,Timestamp closed,Timestamp cancellato ) throws SQLException
	{

		Connection dbbdu = null;
		try
		{
			dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu ="select * from aggiorna_controllo_operatori_commerciali (?::integer,?::timestamp without time zone,?::text,current_timestamp ::timestamp without time zone,?::timestamp without time zone)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, idControllo);
		pst1.setTimestamp(++j,dataControllo);
		pst1.setString(++j, tipoControllo);
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		
	
		
		}
		catch(SQLException e)
		{
			System.out.println("Errore inserimento controllo operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		

		return null ;
	}


	public static String aggiorna_operatore_commerciale(ActionContext context ,Connection db , Organization newOrg) throws SQLException
	{
		ResultSet rs = db.prepareStatement("(select org_id_c from organization where org_id = "+newOrg.getId()+")").executeQuery();
		int orgidc = -1 ;
		if (rs.next())
		{
			orgidc = rs.getInt(1);
		}
		Connection dbbdu = null ;
		try
		{
		dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from aggiorna_operatore_ecommerciale " +
				"(?,?,?,?,?,?,?::timestamp,current_timestamp)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		
		pst1.setInt(++j, orgidc);
		pst1.setString(++j, newOrg.getName());
		pst1.setString(++j, newOrg.getNomeRappresentante());
		pst1.setString(++j, newOrg.getCognomeRappresentante());
		pst1.setString(++j, newOrg.getCodiceFiscaleRappresentante());
		pst1.setString(++j, newOrg.getLuogoNascitaRappresentante());
		pst1.setTimestamp(++j, newOrg.getDataNascitaRappresentante());
		System.out.println("Aggiornamento operatoe Commerciale "+pst1.toString());
		
		ResultSet rsbdu = pst1.executeQuery();
		
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		

		
		}
		catch(SQLException e)
		{
			System.out.println("Errore aggiornamento scheda operatore commerciale in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
		return null ;
	}

	public static String aggiorna_categoria_rischio(ActionContext context,org.aspcfs.checklist.base.Organization organ,Connection db,Ticket newTic,Timestamp date) throws SQLException
	{
		
		Connection dbbdu = null ;
		
		ResultSet rs1 = db.prepareStatement("(select org_id_c from organization where org_id = "+newTic.getOrgId()+")").executeQuery();
		int orgidc = -1 ;
		if (rs1.next())
		{
			orgidc = rs1.getInt(1);
		}
		try
		{
		dbbdu = GestoreConnessioni.getConnectionBdu();		
		
		
		int j = 0 ;
		String inser_bdu = "select * from aggiorna_categoria_rischio (?,?,?::timestamp)";
		PreparedStatement pst1 =  dbbdu.prepareStatement(inser_bdu);
		pst1.setInt(++j, orgidc);
		pst1.setInt(++j,organ.getCategoriaRischio());
		pst1.setTimestamp(++j, date);
	System.out.println("funzione cat rischio bdu " + pst1.toString());
		ResultSet rsbdu =  pst1.executeQuery();
		if (rsbdu.next())
			context.getRequest().setAttribute("MsgBdu", rsbdu.getString(1));
		
		
		
		
	
		
		}
		catch(SQLException e)
		{
			System.out.println("Errore aggiornamento categoria di rischio in BDU "+e.getMessage());
			context.getRequest().setAttribute("MsgBdu", e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnectionBdu(dbbdu);
		}
	
		return null ;
	}

}
