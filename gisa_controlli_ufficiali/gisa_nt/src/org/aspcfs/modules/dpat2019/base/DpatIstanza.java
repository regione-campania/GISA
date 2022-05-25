/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.dpat2019.base.Dpat;

import jdk.nashorn.internal.runtime.ListAdapter;

public class DpatIstanza {

	private int id ;
	private String stato ;
	private int anno; 
	private Timestamp entered;
	private int enteredby ;
	private Timestamp trashedDate ;
	private boolean flag_import_piani ;
	private boolean flag_import_attivita_ispezione=false ;


	
	private Dpat dpat;
	public boolean isFlag_import_attivita_ispezione() {
		return flag_import_attivita_ispezione;
	}
	public void setFlag_import_attivita_ispezione(boolean flag_import_attivita_ispezione) {
		this.flag_import_attivita_ispezione = flag_import_attivita_ispezione;
	}
	public boolean isFlag_import_piani() {
		return flag_import_piani;
	}
	public void setFlag_import_piani(boolean flag_import_piani) {
		this.flag_import_piani = flag_import_piani;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public Timestamp getEntered() {
		return entered;
	}
	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public Timestamp getTrashedDate() {
		return trashedDate;
	}
	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}

	public DpatIstanza ()
	{
		
	}
	
	public Dpat getDpat() {
		return dpat;
	}
	public void setDpat(Dpat dpat) {
		this.dpat = dpat;
	}
	public DpatIstanza (Connection db,int anno)
	{
		try
		{
			ResultSet rs = db.prepareStatement("select * from dpat_istanza where anno ="+anno+" and trashed_date is null").executeQuery();
			if (rs.next())
			{
				buildRecord(rs);
				 dpat= new Dpat();
				dpat.buildlistSezioni(db, anno);
				
				for (int i = 0; i < dpat.getElencoSezioni().size(); i++) {
					dpat.getElencoSezioni().get(i).buildlistPianiConfiguratore(db,dpat.getElencoSezioni().get(i).getId(),anno);
					
					for (int j = 0; j < dpat.getElencoSezioni().get(i).getElencoPiani().size(); j++) {
						dpat.getElencoSezioni().get(i).getElencoPiani().get(j).buildlistAttivitaConfiguratore(db, dpat.getElencoSezioni().get(i).getElencoPiani().get(j).getId(),anno);
						
						
						
						for (int k = 0; k < dpat.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().size(); k++) {
							dpat.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k).buildlistIndicatoriConfiguratore(	db,dpat.getElencoSezioni().get(i).getElencoPiani().get(j).getElencoAttivita().get(k).getId(),anno);
							
						}
						
					}
					}
					
				
				
				
			
				
		
				
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public int getStatoFromAnno (Connection db,int anno)
	{
		int stato = 0 ;
		try
		{
			ResultSet rs = db.prepareStatement("select * from dpat_istanza where anno ="+anno+" and trashed_date is null").executeQuery();
			if (rs.next())
			{
				String statoString = rs.getString("stato");
				stato = Integer.parseInt(statoString);
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return stato ;
	}
	
	
	public void congelaConfigurazione (Connection db)
	{
		try
		{
			PreparedStatement pst = db.prepareStatement("update dpat_istanza set stato = 2 where id = ?;update dpat_sezione_ set stato = 2 where id_dpat_istanza = ?");
			pst.setInt(1, this.id);
			pst.setInt(2, this.id);
			pst.execute();
			
			for(DpatSezione sez : dpat.getElencoSezioni() )
			{
				String update = "update dpat_sezione_ set stato = 2 where id = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL) ";
				 pst = db.prepareStatement(update);
				pst.setInt(1, sez.getId());
				pst.execute();
				
				for (DpatPiano piano : sez.getElencoPiani())
				{
					 update = "update dpat_piano_ set stato = 2 where id = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL) ";
					pst = db.prepareStatement(update);
					pst.setInt(1, piano.getId_());
					pst.execute();
					
					
					
					for (DpatAttivita att : piano.getElencoAttivita())
					{
						
						update = "update dpat_Attivita_ set stato = 2 where id = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL)  ";
						 pst = db.prepareStatement(update);
						pst.setInt(1, att.getId_());
						pst.execute();
						
						for (DpatIndicatore ind : att.getElencoIndicatori())
						{
							
							update = "update dpat_indicatore_ set stato = 2 where id = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL)  ";
							 pst = db.prepareStatement(update);
							pst.setInt(1, ind.getId_());
							pst.execute();
							
						}
						
					}
					
					
					
				}
				
			}
			
			pst = db.prepareStatement("select * from refresh_motivi_cu(?)");
			pst.setInt(1, anno);
			pst.execute();
				
			
//			for(DpatSezione sez : dpat.getElencoSezioni() )
//			{
//				String update = "update dpat_piano set stato = 2 where id_sezione = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL) ";
//				 pst = db.prepareStatement(update);
//				pst.setInt(1, sez.getId());
//				pst.execute();
//				
//				for (DpatPiano piano : sez.getElencoPiani())
//				{
//					 update = "update dpat_attivita set stato = 2 where id_piano = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL) ";
//					 pst = db.prepareStatement(update);
//					pst.setInt(1, piano.getId());
//					pst.execute();
//					
//					
//					for (DpatAttivita att : piano.getElencoAttivita())
//					{
//						
//						update = "update dpat_indicatore set stato = 2 where id_attivita = ? and (data_scadenza > 'now'::text::date OR data_scadenza IS NULL)  ";
//						 pst = db.prepareStatement(update);
//						pst.setInt(1, att.getId());
//						pst.execute();
//						
//					}
//				}
//				
//			
//				
//				
//			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void setFlagPianiImportati (Connection db, int anno)
	{
		try
		{

			String update = "update import_piani_attivita set flag_import_piani = true where  anno=? " ;
			String sel = "select * from import_piani_attivita where anno=? " ;
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, anno);
			ResultSet rs= pst.executeQuery();
			
			if(rs.next())
			{
			 pst = db.prepareStatement(update);
			pst.setInt(1, anno);
		
			pst.execute();
			}
			else
			{
				String insert = "insert into import_piani_attivita (anno,flag_import_piani) values(?,true); " ;
				 pst = db.prepareStatement(insert);
					pst.setInt(1, anno);
				
					pst.execute();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		this.flag_import_piani=true;
	}
	
	public void setValoreFlagPianiImportati (Connection db, int anno)
	{
		try
		{

			
			String sel = "select * from import_piani_attivita where anno=? " ;
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, anno);
			ResultSet rs= pst.executeQuery();
			
			if(rs.next())
			{
				flag_import_piani=rs.getBoolean("flag_import_piani");
			}
			else
			{
				flag_import_piani=false;
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	public void setFlagAttivitaImportate (Connection db, int anno)
	{
		try
		{

			String update = "update import_piani_attivita set flag_import_attivita = true where  anno=? " ;
			String sel = "select * from import_piani_attivita where anno=? " ;
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, anno);
			ResultSet rs= pst.executeQuery();
			
			if(rs.next())
			{
			 pst = db.prepareStatement(update);
			pst.setInt(1, anno);
		
			pst.execute();
			}
			else
			{
				String insert = "insert into import_piani_attivita (anno,flag_import_attivita) values(?,true); " ;
				 pst = db.prepareStatement(insert);
					pst.setInt(1, anno);
				
					pst.execute();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		this.flag_import_piani=true;
	}
	
	public void setValoreFlagAttivitaImportate (Connection db, int anno)
	{
		try
		{

			
			String sel = "select * from import_piani_attivita where anno=? " ;
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, anno);
			ResultSet rs= pst.executeQuery();
			
			if(rs.next())
			{
				flag_import_attivita_ispezione=rs.getBoolean("flag_import_attivita");
			}
			else
			{
				flag_import_attivita_ispezione=false;
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void deleteDpat (Connection db,int anno)
	{
		try
		{

			String delete = "update dpat_istanza set trashed_date = current_date where anno=? and id=? " ;
					
			PreparedStatement pst = db.prepareStatement(delete);
			pst.setInt(1, anno);
			pst.setInt(2, id);
		
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void salvaDefinitiva (Connection db,int anno)
	{
		try
		{

			
			PreparedStatement pst = db.prepareStatement("update dpat_istanza set stato='PERMANENTE' WHERE id =?");
			pst.setInt(1, id); 
			
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void importListaPiani (Connection db,int anno)
	{
		try
		{

			
			String update = "update lookup_piano_monitoraggio set enabled=false where enabled=true";
			PreparedStatement pst = db.prepareStatement("update dpat_istanza set stato='PERMANENTE' WHERE id =?");
			pst.setInt(1, id); 
			
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}



	public void buildRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		anno = rs.getInt("anno");
		trashedDate = rs.getTimestamp("trashed_date");
		entered =rs.getTimestamp("entered");
		enteredby=rs.getInt("enteredby");
		stato=rs.getString("stato");
		flag_import_piani =rs.getBoolean("flag_import_piani");


	}
}
