/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneAnagraficaGetDatiLinea extends CFSModule{
	
	public String executeCommandSearch(ActionContext context) throws IOException{
		
		String output = "[]";
		String tipo_richiesta = context.getRequest().getParameter("tiporichiesta");
		System.out.println("query recupero richiesta " + tipo_richiesta);
		
		String json_flags = context.getRequest().getParameter("json_flags");
		System.out.println("JSON flags " + json_flags);
		
		String id_linee_selezionate = context.getRequest().getParameter("id_linee_selezionate");
		System.out.println("id linee selezionate " + id_linee_selezionate);

		
		if (tipo_richiesta.equalsIgnoreCase("macroarea")){
			output = getMacroarea(context, json_flags, id_linee_selezionate);
		} else if (tipo_richiesta.equalsIgnoreCase("aggregazione")){
			output = getAggregazione(context, json_flags, id_linee_selezionate);
		} else if (tipo_richiesta.equalsIgnoreCase("lineaattivita")){
			output = getLineaAttivita(context, json_flags, id_linee_selezionate);
		} else if (tipo_richiesta.equalsIgnoreCase("datilineaattivita")){
			output = getDatiLinea(context);
		} else if (tipo_richiesta.equalsIgnoreCase("macroareanoscia")){
			output = getMacroareaNoscia(context);
		} else if (tipo_richiesta.equalsIgnoreCase("aggregazionenoscia")){
			output = getAggregazioneNoscia(context);
		} else if (tipo_richiesta.equalsIgnoreCase("lineaattivitanoscia")){
			output = getLineaAttivitaNoscia(context);
		} else if (tipo_richiesta.equalsIgnoreCase("lineetrasformazione")){
		    output = getLineaAttivitaTrasformazione(context, id_linee_selezionate);
		}
		
		PrintWriter writer = context.getResponse().getWriter();
		writer.print(output);
		writer.close();
		return "";		
	
	}
	
	public String getMacroarea (ActionContext context, String json_flags, String id_linee_selezionate) throws IOException{
		
		String output = "[]";
		String sql = "";
		
		Connection db = null;
		try{
				
				sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_macroarea from("
						+ "select id::text as code, descrizione as description "
						+ "from mostra_ml(?, ?, ?::json, ?, ?) "
						+ ") tab";
			
			
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			
			int i = 0;
			pst.setInt(++i, 1);
			pst.setInt(++i, -1);
			pst.setString(++i, json_flags);
			pst.setInt(++i, +getUserId(context));
			pst.setString(++i, id_linee_selezionate);
	
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero macroarea " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_macroarea");		 
			}
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	public String getAggregazione(ActionContext context, String json_flags, String id_linee_selezionate) throws IOException{
		
		String output = "[]";
		int id_macroarea = Integer.parseInt(context.getRequest().getParameter("idmacroarea"));
		System.out.println("query recupero agg " + id_macroarea);
			
		String sql = "";
		Connection db = null;
		try{
			
	
				sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_aggreagazione from("
										+ "select id::text as code, descrizione as description "
										+ "from mostra_ml(?, ?, ?::json, ?, ?) "
										+ ") tab";
		

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			
			int i = 0;
			pst.setInt(++i, 2);
			pst.setInt(++i, id_macroarea);
			pst.setString(++i, json_flags);
			pst.setInt(++i, +getUserId(context));
			pst.setString(++i, id_linee_selezionate);
			
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero aggreagazione " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_aggreagazione");		 
			}
		
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	public String getLineaAttivita(ActionContext context, String json_flags, String id_linee_selezionate) throws IOException{
		
		String output = "[]";
		int id_aggregazione = Integer.parseInt(context.getRequest().getParameter("idaggregazione"));
		System.out.println("query recupero agg " + id_aggregazione);

		String sql = "";
		Connection db = null;
		try{
			
				sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_attivita from("
										+ "select id::text as code, descrizione as description "
										+ "from mostra_ml(?, ?, ?::json, ?, ?) "
										+ ") tab";
				
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			
			int i = 0;
			pst.setInt(++i, 3);
			pst.setInt(++i, id_aggregazione);
			pst.setString(++i, json_flags);
			pst.setInt(++i, +getUserId(context));
			pst.setString(++i, id_linee_selezionate);
			
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero attivita " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_attivita");		 
			}
		
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	public String getMacroareaNoscia(ActionContext context) throws IOException{
		
		String output = "[]";
		String sql = "";
		String id_linea =  context.getRequest().getParameter("lineanoscia");

		Connection db = null;
		try{
			
			sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_macroarea from("
					+ "select distinct mlm.id::text as code, "
					+ "concat(mlm.macroarea, ' (', mlm.norma, ')')::text as description "
					+ "from master_list_macroarea mlm "
					+ "join master_list_aggregazione mla on mlm.id = mla.id_macroarea "
					+ "join master_list_linea_attivita mlla on mla.id = mlla.id_aggregazione "
					+ "join master_list_flag_linee_attivita fl on mlla.id = fl.id_linea "
					+ "where fl.no_scia and fl.compatibilita_noscia = "
					+ "(select compatibilita_noscia from master_list_flag_linee_attivita "
					+ "where id_linea = ?::integer) "
					+ "and fl.rev = (select max(rev) from master_list_flag_linee_attivita ) "
					+ "order by description "
					+ ") tab";
	
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, id_linea);
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero macroarea " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_macroarea");		 
			}
			
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	public String getAggregazioneNoscia(ActionContext context) throws IOException{
		
		String output = "[]";
		int id_macroarea = Integer.parseInt(context.getRequest().getParameter("idmacroarea"));
		String codice_linea =  context.getRequest().getParameter("lineanoscia");
		System.out.println("query recupero agg " + id_macroarea);
		
		String sql = "";
		Connection db = null;
		try{
			
			sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_aggreagazione from("
					+ "select "
					+ "distinct mla.id::text as code,"
					+ "mla.aggregazione::text as description "
					+ "from master_list_aggregazione mla "
					+ "join master_list_linea_attivita mlla on mla.id = mlla.id_aggregazione "
					+ "join master_list_flag_linee_attivita fl on mlla.id = fl.id_linea "
					+ "where mla.id_macroarea = ? and fl.no_scia and fl.compatibilita_noscia = "
					+ "(select compatibilita_noscia from master_list_flag_linee_attivita "
					+ "where id_linea = ?::integer) "
					+ "and fl.rev = (select max(rev) from master_list_flag_linee_attivita ) "
					+ "order by description "
					+ ") tab";
			 
				
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id_macroarea);
			pst.setString(2, codice_linea);
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero aggreagazione " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_aggreagazione");		 
			}
		
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	public String getLineaAttivitaNoscia(ActionContext context) throws IOException{
		
		String output = "[]";
		int id_aggregazione = Integer.parseInt(context.getRequest().getParameter("idaggregazione"));
		String codice_linea =  context.getRequest().getParameter("lineanoscia");
		System.out.println("query recupero agg " + id_aggregazione);
		
		String sql = "";
		Connection db = null;
		try{
			
			sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_attivita from( "
					+ "select "
					+ "distinct mlla.id::text as code, "
					+ "mlla.linea_attivita::text as description "
					+ "from master_list_linea_attivita mlla "
					+ "join master_list_flag_linee_attivita fl on mlla.id = fl.id_linea "
					+ "where mlla.id_aggregazione  = ? and fl.no_scia and fl.compatibilita_noscia = "
					+ "(select compatibilita_noscia from master_list_flag_linee_attivita "
					+ "where id_linea = ?::integer) "
					+ "and fl.rev = (select max(rev) from master_list_flag_linee_attivita ) "
					+ "order by description "
					+ ") tab";
			
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id_aggregazione);
			pst.setString(2, codice_linea);
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero attivita " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_attivita");		 
			}
		
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	
public String getLineaAttivitaTrasformazione(ActionContext context, String id_linee_selezionate) throws IOException{
		
		String output = "[]";
		
		String sql = "";
		Connection db = null;
		try{
			
			sql = "select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as lista_attivita from( "
					+ "select "
					+ "distinct id::text as code, "
					+ "linea_attivita::text as description "
					+ "from master_list_linea_attivita "
					+ "where trim(codice_univoco) ilike "
					+ "(select trim(codice_univoco) from master_list_linea_attivita where id = ?) "
					+ "and id <> ? "
					+ "and rev = (select max(rev) from master_list_flag_linee_attivita) "
					+ "order by linea_attivita "
					+ ") tab";
			
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(id_linee_selezionate));
			pst.setInt(2, Integer.parseInt(id_linee_selezionate));
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero attivita " + pst);
			while(rs.next())
			{
				output = rs.getString("lista_attivita");		 
			}
		
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		
		return output;
	}
	
	
	public String getDatiLinea(ActionContext context) throws IOException{
		
		String output = "[]";
		int id_linea_attivita = Integer.parseInt(context.getRequest().getParameter("idlineaattivita"));
		System.out.println("query recupero linea di attivita completa " + id_linea_attivita);
		String sql = "select (concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json)::text as dati_linea_attivita from("
				+ "select id_nuova_linea_attivita::text as id, "
				+ "codice::text as codice, "
				+ "macroarea::text as macroarea, "
				+ "aggregazione::text as aggregazione, "
				+ "attivita::text as attivita "
				+ "from ml8_linee_attivita_nuove_materializzata where id_nuova_linea_attivita = ? and enabled) tab";

		Connection db = null;
		try{
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id_linea_attivita);
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query recupero dati linea di attivita completa " + pst);
			while(rs.next())
			{
				output = rs.getString("dati_linea_attivita");		 
			}
		
		}catch(LoginRequiredException e)
		{
			throw e;
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		System.out.println("dati linea di attivita completa log " + output);
		return output;
	}
}
