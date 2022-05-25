/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspcfs.modules.richiestecontributi.base.Cane.TipoAnimale;


import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;


public class ListaCani {
	
	private static Logger log = Logger.getLogger(org.aspcfs.modules.richiestecontributi.base.ListaCani.class);
	private transient static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MainLogger");
	
	
	
	public List<Cane> listaCani;


	public ListaCani() {
		this.listaCani=new ArrayList();
	}
	
	public List<Cane> getListaCani() {
		return listaCani;
	}

	public void setListaCani(List<Cane> listaCani) {
		this.listaCani = listaCani;
	}
	
	
	public List<Cane> getLista (Connection db, int asl,int id_pratica)throws SQLException {
		ResultSet rs=null;
		ResultSet rs2=null;
		String sql="";
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		String query_microchip_contributo_pagato="Select microchip from contributi_lista_univocita";
		ArrayList<String> elenco_mc=new ArrayList<String>();
		
	    //final String IP_FELINA = ApplicationProperties.getProperty("IP_FELINA");
		final String DATABASE_FELINA = ApplicationProperties.getProperty("DATABASE_FELINA");
		final String USERNAME_FELINA = ApplicationProperties.getProperty("USERNAME_FELINA");
		final String PASSWORD_FELINA = ApplicationProperties.getProperty("PASSWORD_FELINA");
		
		Connection db_felina = null;
			
		PreparedStatement st3 = null;
		ResultSet rs3 = null; 

		try{
			sql="Select * from estrazione_cani_contributi " +
					"where id_pratica_contributi = ?  and " +
							"(asl = ? or asl_dest= ? )";
			
			ps = db.prepareStatement(sql.toString());
			ps.setInt(1,id_pratica);
			ps.setInt(2,asl);
			ps.setInt(3,asl);
		   
			rs = DatabaseUtils.executeQuery(db, ps);
			
			Cane cane;
			while (rs.next()) {
				cane=new Cane();
				cane.setId_cane(rs.getInt("id_cane"));
				cane.setMicrochip(rs.getString("microchip"));
				cane.setProprietario(rs.getString("proprietario"));
				cane.setTipologia(rs.getString("tipologia"));
				cane.setDataSterilizzazione(rs.getTimestamp("data_sterilizzazione"));
				cane.setComuneCattura(rs.getString("comune_cattura"));
				cane.setComune_proprietario(rs.getString("comune_proprietario"));
				cane.setComune_colonia("");
				cane.setAsl(rs.getInt("asl"));
				cane.setId_pratica(rs.getInt("id_pratica_contributi"));
				cane.setTipo_animale(TipoAnimale.CANE);
				listaCani.add(cane);
				
			}

		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getLista della classe ListaCani");
			db.rollback();
			throw new SQLException(e.getMessage());
		}
		finally{
			rs.close();
			if (ps != null) {
				ps.close();
			}
		}
		
		try{
			ps2 = db.prepareStatement(query_microchip_contributo_pagato);
			rs2 = DatabaseUtils.executeQuery(db, ps2);
			
			while(rs2.next()){
				elenco_mc.add(rs2.getString(1));
			}
			
				//db_felina = DbUtil.getConnectionFelina(DATABASE_FELINA, USERNAME_FELINA, PASSWORD_FELINA, "dbserver");
				String query="Select * from estrazione_gatti_contributi where id_pratica_contributi= ? and (asl = ? or asl_dest= ? )";
					
				st3 = db_felina.prepareStatement( query );
				st3.setInt(1, id_pratica);
				st3.setInt(2, asl);
				st3.setInt(3, asl);
				
				rs3 = st3.executeQuery();
				Cane cane;
				while(rs3.next()){
					
					if  (!elenco_mc.contains(rs3.getString("microchip"))){
						cane=new Cane();
						cane.setId_cane(rs3.getInt("id_gatto"));
						cane.setMicrochip(rs3.getString("microchip"));
						cane.setProprietario(rs3.getString("proprietario"));
						cane.setTipologia(rs3.getString("tipologia"));
						cane.setDataSterilizzazione(rs3.getTimestamp("data_sterilizzazione"));
						cane.setComuneCattura(rs3.getString("comune_cattura"));
						cane.setComune_proprietario(rs3.getString("comune_proprietario"));
						cane.setComune_colonia(rs3.getString("comune_colonia"));
						cane.setAsl(rs3.getInt("asl"));
						cane.setId_pratica(rs3.getInt("id_pratica_contributi"));
						cane.setTipo_animale(TipoAnimale.GATTO);
					
						listaCani.add(cane);
					}
				}
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getLista della classe ListaCani");
			e.printStackTrace();
		}	
		finally{
			
			//rs3.close();
			if (st3 != null) {
				st3.close();
			}
			if(db_felina!=null){
				db_felina.close();
			}
			
			
		}
		
		this.setListaCani(listaCani);
		return listaCani;
	}
	
	

 private boolean checkLista (Connection db, String mc)throws SQLException {
	 	  
	 ResultSet rs;
	 String sql="";
		try {				
			
			sql="select * from contributi_lista_univocita clu where clu.microchip='" + mc + "'";
						
			PreparedStatement ps = db.prepareStatement(sql.toString());
					
			rs = DatabaseUtils.executeQuery(db, ps);
						
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		}	

 }
 
  private boolean checkListaReport (Connection db, String mc, int idDettaglio)throws SQLException {
	 	  
	 ResultSet rs;
	 String sql="";
		try {				
			
			sql="select * from contributi_lista_univocita clu where clu.id_richiesta_contributi='" + idDettaglio + "' and clu.microchip='" + mc + "' ";
						
			PreparedStatement ps = db.prepareStatement(sql.toString());
					
			rs = DatabaseUtils.executeQuery(db, ps);
						
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		}	

 }
	
  public List<Cane> getListaDettaglio (Connection db, int idDettaglio)throws SQLException {
		
		ResultSet rs;
		String sql="";
		try {		
						
			sql="Select distinct id_cane, microchip ,proprietario,clc.tipologia,clc.comune_cattura, data_sterilizzazione,numero_protocollo,asl " +
					", l.description,comune_proprietario,tipo_animale,comune_colonia from  contributi_lista_cani clc " +
					"left join lookup_asl_rif l on l.code= clc.asl where clc.id_richiesta_contributi='" + idDettaglio + "' order by tipo_animale,microchip ";
					
			
			PreparedStatement ps = db.prepareStatement(sql.toString());
			rs = DatabaseUtils.executeQuery(db, ps);
			
			
			Cane cane;
			while (rs.next()) {
				cane=new Cane();
				
				
				cane.setId_cane(rs.getInt("id_cane"));

				cane.setMicrochip(rs.getString("microchip"));

				cane.setProprietario(rs.getString("proprietario"));
				cane.setTipologia(rs.getString("tipologia"));
		
				cane.setComuneCattura(rs.getString("comune_cattura"));
		
				cane.setDataSterilizzazione(rs.getTimestamp("data_sterilizzazione"));
		
				cane.setNumeroProtocollo(rs.getString("numero_protocollo"));
		
				cane.setAsl(rs.getInt("asl"));
		
				cane.setDescrizioneAsl(rs.getString("description"));
		
				cane.setComune_proprietario(rs.getString("comune_proprietario"));
		
				if (rs.getString(11).equals("CANE")){
				cane.setTipo_animale(TipoAnimale.CANE);
				}

				else if (rs.getString(11).equals("GATTO"))
				{
					cane.setTipo_animale(TipoAnimale.GATTO);
				}

				//cane.;
				if (this.checkLista(db, rs.getString("microchip"))==true) {					
					cane.setPagato(true);
				}
				else {					
				
					cane.setPagato(false);
				}
				
				cane.setComune_colonia(rs.getString("comune_colonia"));
				listaCani.add(cane);
				
				
			}

			rs.close();
			if (ps != null) {
				ps.close();
			}
			
			

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		}
		this.setListaCani(listaCani);
		return listaCani;
	}
	

  public List<Cane> getReportListaDettaglio (Connection db, int idDettaglio)throws SQLException {
		
		ResultSet rs;
		String sql="";
		try {		
						
			sql="Select distinct id_cane AS id_cane, microchip AS microchip, proprietario, clc.tipologia, data_sterilizzazione," +
					"numero_protocollo, comune_proprietario ,asl , l.description,clc.comune_cattura,clc.comune_colonia ,clc.tipo_animale " +
					"from contributi_lista_cani clc LEFT JOIN lookup_asl_rif l on l.code = clc.asl where clc.id_richiesta_contributi='" + idDettaglio + "'";
	
			PreparedStatement ps = db.prepareStatement(sql.toString());
			
			rs = DatabaseUtils.executeQuery(db, ps);
			
			
			Cane cane;
			while (rs.next()) {
				cane=new Cane();
				
				
				cane.setId_cane(rs.getInt(1));
				cane.setMicrochip(rs.getString(2));
				cane.setProprietario(rs.getString(3));
				cane.setTipologia(rs.getString(4));
				cane.setDataSterilizzazione(rs.getTimestamp(5));
				cane.setNumeroProtocollo(rs.getString(6));
				cane.setComune_proprietario(rs.getString(7));	
				cane.setAsl(rs.getInt(8));
				cane.setDescrizioneAsl(rs.getString(9));
				cane.setComuneCattura(rs.getString(10));
				cane.setComune_colonia(rs.getString(11));
				cane.setTipo_animale(TipoAnimale.valueOf(rs.getString(12)));
				
				if (this.checkListaReport(db, rs.getString(2), idDettaglio)==true) {					
					cane.setPagato(true);
				}
				
				else {					
					cane.setPagato(false);
				}
				listaCani.add(cane);
				
				
			}

			rs.close();
			if (ps != null) {
				ps.close();
			}

		} catch (SQLException e) {
			db.rollback();
			throw new SQLException(e.getMessage());
		}
		this.setListaCani(listaCani);
		return listaCani;
	}
	
   
  public List<Cane> getListaReport (Connection db, int asl,int id_pratica)throws SQLException {
		ResultSet rs=null;
		ResultSet rs2=null;
		String sql="";
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		String query_microchip_contributo_pagato="Select microchip from contributi_lista_univocita";
		ArrayList<String> elenco_mc=new ArrayList<String>();
		
	  //  final String IP_FELINA = ApplicationProperties.getProperty("IP_FELINA");
	    final String DATABASE_FELINA = ApplicationProperties.getProperty("DATABASE_FELINA");
		final String USERNAME_FELINA = ApplicationProperties.getProperty("USERNAME_FELINA");
		final String PASSWORD_FELINA = ApplicationProperties.getProperty("PASSWORD_FELINA");
		
		Connection db_felina = null;
			
		PreparedStatement st3 = null;
		ResultSet rs3 = null; 

		try{
			sql="Select * from estrazione_cani_report_contributi " +
					"where id_pratica_contributi = ?  and " +
							"(asl = ? or asl_dest= ? )";
			
			ps = db.prepareStatement(sql.toString());
			ps.setInt(1,id_pratica);
			ps.setInt(2,asl);
			ps.setInt(3,asl);
			rs = DatabaseUtils.executeQuery(db, ps);
			
			Cane cane;
			while (rs.next()) {
				cane=new Cane();
				cane.setId_cane(rs.getInt("id_cane"));
				cane.setMicrochip(rs.getString("microchip"));
				cane.setProprietario(rs.getString("proprietario"));
				cane.setTipologia(rs.getString("tipologia"));
				cane.setDataSterilizzazione(rs.getTimestamp("data_sterilizzazione"));
				cane.setComuneCattura(rs.getString("comune_cattura"));
				cane.setComune_proprietario(rs.getString("comune_proprietario"));
				cane.setComune_colonia("");
				cane.setAsl(rs.getInt("asl"));
				cane.setId_pratica(rs.getInt("id_pratica_contributi"));
				cane.setTipo_animale(TipoAnimale.CANE);
				listaCani.add(cane);
				
			}

		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListaReport della classe ListaCani");
			db.rollback();
			throw new SQLException(e.getMessage());
		}
		finally{
			rs.close();
			if (ps != null) {
				ps.close();
			}
		}
		
		try{
			ps2 = db.prepareStatement(query_microchip_contributo_pagato);
			rs2 = DatabaseUtils.executeQuery(db, ps2);
			
			while(rs2.next()){
				elenco_mc.add(rs2.getString(1));
			}
			
		//	   db_felina = DbUtil.getConnectionFelina(DATABASE_FELINA, USERNAME_FELINA, PASSWORD_FELINA, "dbserver");
			   String query="Select * from estrazione_gatti_contributi where id_pratica_contributi= ? and (asl = ? or asl_dest= ? )";
				st3 = db_felina.prepareStatement( query );
				st3.setInt(1, id_pratica);
				st3.setInt(2, asl);
				st3.setInt(3, asl);
				
				rs3 = st3.executeQuery();
				Cane cane;
				while(rs3.next()){
					
					if  (!elenco_mc.contains(rs3.getString("microchip"))){
						cane=new Cane();
						cane.setId_cane(rs3.getInt("id_gatto"));
						cane.setMicrochip(rs3.getString("microchip"));
						cane.setProprietario(rs3.getString("proprietario"));
						cane.setTipologia(rs3.getString("tipologia"));
						cane.setDataSterilizzazione(rs3.getTimestamp("data_sterilizzazione"));
						cane.setComuneCattura(rs3.getString("comune_cattura"));
						cane.setComune_proprietario(rs3.getString("comune_proprietario"));
						cane.setComune_colonia(rs3.getString("comune_colonia"));
						cane.setAsl(rs3.getInt("asl"));
						cane.setId_pratica(rs3.getInt("id_pratica_contributi"));
						cane.setTipo_animale(TipoAnimale.GATTO);
					
						listaCani.add(cane);
					}
				}
		}
		catch(Exception e){
			logger.severe("[CANINA] - EXCEPTION nel metodo getListaReport della classe ListaCani");
			e.printStackTrace();
		}	
		finally{
			
			rs3.close();
			if (st3 != null) {
				st3.close();
			}
			if(db_felina!=null){
				db_felina.close();
			}
			
			
		}
		
		this.setListaCani(listaCani);
		return listaCani;
	}
	
	
}


