/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.Struttura;
import it.us.web.constants.ExtendedOptions;
import it.us.web.constants.Sql;
import it.us.web.util.guc.GUCEndpoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClinicaDAO extends GenericDAO {

	private static final Logger logger = LoggerFactory.getLogger( ClinicaDAO.class );

	//CLINICHE VAM
	public static ArrayList<Clinica> getClinicheByRuolo(Connection db,int idRuolo){
		PreparedStatement stat = null;
		ResultSet rs = null;
		Clinica	bf = null;
		ArrayList<Clinica> ret = new  ArrayList<Clinica>();

		try{
			stat = db.prepareStatement( Sql.GET_CLINICHE );
			stat.setInt(1, idRuolo);
			rs = stat.executeQuery();

			while( rs.next() ){
				bf = loadResultSet(rs);
				ret.add( bf );
			}
		} catch (SQLException e){
			logger.error( "", e );
		}
		finally{
			close( rs, stat );
		}
		return ret;
	}

	private static Clinica loadResultSet(ResultSet rs) throws SQLException{
		Clinica clinica;
		clinica = new Clinica();
		clinica.setDescrizioneClinica(rs.getString("descrizione_clinica"));
		clinica.setIdClinica(rs.getInt("id_clinica"));
		return clinica;
	}


	//STRUTTURE GISA
	public static ArrayList<Struttura> getStruttureByRuolo(Connection db,int idRuolo){
		PreparedStatement stat = null;
		ResultSet rs = null;
		Struttura	bf = null;
		ArrayList<Struttura> ret = new  ArrayList<Struttura>();
	
		try{
			stat = db.prepareStatement( Sql.GET_STRUTTURE );
			stat.setInt(1, idRuolo);
			rs = stat.executeQuery();
	
			while( rs.next() ){
				bf = loadResultSetStrutt(rs);
				ret.add( bf );
			}
		} catch (SQLException e){
			logger.error( "", e );
		}
		finally{
			close( rs, stat );
		}
		return ret;
	}

	private static Struttura loadResultSetStrutt(ResultSet rs) throws SQLException{
		Struttura s;
		s = new Struttura();
		s.setDescrizioneStruttura(rs.getString("descrizione_struttura"));
		s.setIdStruttura(rs.getInt("id_struttura"));
		s.setIdPadre(rs.getInt("id_padre"));
		return s;
	}
	
	//CANILI BDU
		public static ArrayList<Canile> getCaniliByRuolo(Connection db,int idRuolo){
			PreparedStatement stat = null;
			ResultSet rs = null;
			Canile	bf = null;
			ArrayList<Canile> ret = new  ArrayList<Canile>();
		
			try{
				stat = db.prepareStatement( Sql.GET_CANILI );
				stat.setInt(1, idRuolo);
				rs = stat.executeQuery();
		
				while( rs.next() ){
					bf = loadResultSetCanili(rs);
					ret.add( bf );
				}
			} catch (SQLException e){
				logger.error( "", e );
			}
			finally{
				close( rs, stat );
			}
			return ret;
		}

		private static Canile loadResultSetCanili(ResultSet rs) throws SQLException{
			Canile c;
			c = new Canile();
			c.setDescrizioneCanile(rs.getString("descrizione_canile"));
			c.setIdCanile(rs.getInt("id_canile"));
			return c;
		}
		
		//IMPORTATORI
		public static ArrayList<Importatori> getImportatoriByRuolo(Connection db,int idRuolo){
			PreparedStatement stat = null;
			ResultSet rs = null;
			Importatori	bf = null;
			ArrayList<Importatori> ret = new  ArrayList<Importatori>();
		
			try{
				stat = db.prepareStatement( Sql.GET_IMPORTATORI );
				stat.setInt(1, idRuolo);
				rs = stat.executeQuery();
		
				while( rs.next() ){
					bf = loadResultSetImportatori(rs);
					ret.add( bf );
				}
			} catch (SQLException e){
				logger.error( "", e );
			}
			finally{
				close( rs, stat );
			}
			return ret;
		}
		
		private static Importatori loadResultSetImportatori(ResultSet rs) throws SQLException{
			Importatori i;
			i = new Importatori();
			i.setRagioneSociale(rs.getString("descrizione_importatore"));
			i.setIdImportatore(rs.getInt("id_importatore"));
			i.setPartitaIva(rs.getString("partita_iva"));
			return i;
		}
		
		
		//EXTENDED OPTION
		public static HashMap<String,HashMap<String,String>> getExeOpt(Connection db,int idRuolo){
			HashMap<String,HashMap<String,String>> ret = new HashMap<String,HashMap<String,String>>();
			PreparedStatement stat = null;
			ResultSet rs = null;
			try{
				for(GUCEndpoint endpoint : GUCEndpoint.values()){
					HashMap<String,String> opt = new HashMap<String,String>();
					stat = db.prepareStatement( Sql.GET_EXTOPT );
					stat.setInt(1, idRuolo);
					stat.setString(2,endpoint.toString());
					rs = stat.executeQuery();
					while( rs.next() ){
						opt.put(rs.getString("key"), rs.getString("val"));
					}				
					ret.put(endpoint.toString(), opt);
				}				
			} catch (SQLException e){
				logger.error( "", e );
			}
			finally{
				close( rs, stat );
			}
			return ret;
		}


}
