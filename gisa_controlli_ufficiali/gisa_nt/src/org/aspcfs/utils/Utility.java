/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.util.*;
import java.util.logging.Logger;

import org.aspcfs.modules.gestioneanagrafica.base.MetadatoTemplate;
import org.aspcfs.modules.gestioneanagrafica.base.Record;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.*;



public class Utility {
	
	static Logger logger = Logger.getLogger("MainLogger");
	
	 
	
	  
	public static String changeDataFromSql(Date data_sql){
		String dataFormattata = "";
		
		if (data_sql ==  null){
			return null;
		} else {		
			try {
				SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy" );
				dataFormattata = df.format(data_sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataFormattata;
	}	
	
	
	public static java.sql.Date changeStringToDataSql(String data_str){
		java.util.Date data = null;
		
		if (data_str ==  null || data_str.isEmpty()){
			return null;
		} else {		
			try {
				SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
				data = df.parse(data_str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new java.sql.Date(data.getTime());
	}
	

	public static int changeStringToIntSql(String int_str){
		int i = 0;
		
		if (int_str ==  null || int_str.isEmpty()){
			return 0;
		} else {		
			try {
				i = Integer.valueOf(int_str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return i;
	}	
	
	
	public static String getParametriRichiesta(javax.servlet.http.HttpServletRequest request){
		String elencoParametri = "PARAMETRI RICHIESTA -> ";
		for(java.util.Enumeration e = request.getParameterNames(); e.hasMoreElements();){
			String ParameterNames = (String)e.nextElement();
			elencoParametri += ParameterNames +": "+ request.getParameter(ParameterNames)+ ";  ";
		}
		
		return elencoParametri;
	}
		
	
	public static String getSqlFiltroLike(String nomeFiltro, String valoreFiltro){		
	    if (valoreFiltro != null && !valoreFiltro.equals("")){
	    	return " AND "+ nomeFiltro +" LIKE '" + valoreFiltro + "%'";
	    }
	    return "";
	}
	

	public static String getSqlFiltroLikeDate(String nomeFiltro, String valoreFiltro){
		if ((valoreFiltro != null) && (!(valoreFiltro.equals("")))){
			String data_valore_filtro = valoreFiltro.substring(0, 10);
		    return " AND "+ nomeFiltro +" LIKE '" + data_valore_filtro + "%'";
	    }
	    return "";
	}
		
	
	public static String getSqlFiltro(String nomeFiltro, String valoreFiltro){		
	    if (valoreFiltro != null && !valoreFiltro.equals("")){
	    	return " AND "+ nomeFiltro +" = " + valoreFiltro;
	    }
	    return "";
	}
	
	
	public static int getIdAslFromIstatAsl(String istatAslOriginale, Connection conn, String matricola) throws SQLException{
		
		int idAsl = -1;

		String istatAsl = istatAslOriginale;
		
		if(istatAslOriginale != null && !istatAslOriginale.equals("")){
			
			if(istatAslOriginale.startsWith("R")){
				
				istatAsl = istatAslOriginale.substring(1);
			
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				try{
					String query = "select code from lookup_site_id where codiceistat = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, istatAsl);
					rs = pst.executeQuery();
					
					if(rs.next()){
						idAsl = rs.getInt(1);
					}
					else{
						logger.warning("Non e stato possibile recuperare l'ASL con codice istat " + istatAslOriginale);
										}
					
				}
				finally{
					if(rs != null){
						rs.close();
					}
					if(pst != null){
						pst.close();
					}
				}
			}
			else{
				idAsl = 16;
			}
		
		}
		
		return idAsl;
		
	}
	
	
	public static String getComuneFromCodiceAzienda(String codiceAzienda, Connection conn) throws SQLException{
		
		String comune = "";
		
		if(codiceAzienda!=null)
		if(codiceAzienda.length() >= 5){
			
			String siglaProvincia = codiceAzienda.substring(3, 5);
			siglaProvincia = siglaProvincia.toUpperCase();
			
			HashMap<String, String> codiciIstatProvince = new HashMap<String, String>();
			codiciIstatProvince.put("CE", "061");
			codiciIstatProvince.put("BN", "062");
			codiciIstatProvince.put("NA", "063");
			codiciIstatProvince.put("AV", "064");
			codiciIstatProvince.put("SA", "065");
			
			String codiceIstatProvincia = "";
			if(codiciIstatProvince.containsKey(siglaProvincia)){
				
				codiceIstatProvincia = codiciIstatProvince.get(siglaProvincia);
				
				String codiceIstatComune = codiceIstatProvincia + codiceAzienda.substring(0, 3);
				
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				try{
					String query = "select comune from comuni where codiceistatcomune = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, codiceIstatComune);
					rs = pst.executeQuery();
					
					if(rs.next()){
						comune = rs.getString(1);
					}
					else{
						logger.warning("Non e stato possibile recuperare il comune con codice istat " + codiceIstatComune);
					}
					
				}
				finally{
					if(rs != null){
						rs.close();
					}
					if(pst != null){
						pst.close();
					}
				}
				
			}
		
		}
		
		return comune;
		
	}
	
	
	
public static String getComuneFromCodiceIstat(String codiceAzienda, String comCodice, Connection conn) throws SQLException{
		
		String comune = "";
		
		if(codiceAzienda!=null)
		if(codiceAzienda.length() >= 5){
			
			String siglaProvincia = codiceAzienda.substring(3, 5);
			siglaProvincia = siglaProvincia.toUpperCase();
			
			HashMap<String, String> codiciIstatProvince = new HashMap<String, String>();
			codiciIstatProvince.put("CE", "061");
			codiciIstatProvince.put("BN", "062");
			codiciIstatProvince.put("NA", "063");
			codiciIstatProvince.put("AV", "064");
			codiciIstatProvince.put("SA", "065");
			
			String codiceIstatProvincia = "";
			if(codiciIstatProvince.containsKey(siglaProvincia)){
				
				codiceIstatProvincia = codiciIstatProvince.get(siglaProvincia);
				
				String codiceIstatComune = codiceIstatProvincia + comCodice;
				
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				try{
					String query = "select comune from comuni where codiceistatcomune = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, codiceIstatComune);
					rs = pst.executeQuery();
					
					if(rs.next()){
						comune = rs.getString(1);
					}
					else{
						logger.warning("Non e stato possibile recuperare il comune con codice istat " + codiceIstatComune);
					}
					
				}
				finally{
					if(rs != null){
						rs.close();
					}
					if(pst != null){
						pst.close();
					}
				}
				
			}
		
		}
		
		return comune;
		
	}


	
public static String getIstatFromCodiceAzienda(String codiceAzienda, Connection conn) throws SQLException{
		
		String comune = "";
		
		if(codiceAzienda!=null)
		if(codiceAzienda.length() >= 5){
			
			String siglaProvincia = codiceAzienda.substring(3, 5);
			
			HashMap<String, String> codiciIstatProvince = new HashMap<String, String>();
			codiciIstatProvince.put("CE", "061");
			codiciIstatProvince.put("BN", "062");
			codiciIstatProvince.put("NA", "063");
			codiciIstatProvince.put("AV", "064");
			codiciIstatProvince.put("SA", "065");
			
			String codiceIstatProvincia = "";
			if(codiciIstatProvince.containsKey(siglaProvincia)){
				
				codiceIstatProvincia = codiciIstatProvince.get(siglaProvincia);
				
				String codiceIstatComune = codiceIstatProvincia + codiceAzienda.substring(0, 3);
				
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				try{
					String query = "select istat from comuni where codiceistatcomune = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, codiceIstatComune);
					rs = pst.executeQuery();
					
					if(rs.next()){
						comune = rs.getString(1);
					}
					else{
						logger.warning("Non e stato possibile recuperare il comune con codice istat " + codiceIstatComune);
					}
					
				}
				finally{
					if(rs != null){
						rs.close();
					}
					if(pst != null){
						pst.close();
					}
				}
				
			}
		
		}
		
		return comune;
		
	}
	
	
	public static int getIdSpecieFromTextCodeSpecie(String textCodeSpecie, Connection conn, String matricola) throws SQLException{
			
			int idSpecie = -1;
			
			PreparedStatement pst = null;
			ResultSet rs = null;
			
			try{
				String query = "select code from m_lookup_specie where text_code ilike ?";
				pst = conn.prepareStatement(query);
				pst.setString(1, textCodeSpecie);
				rs = pst.executeQuery();
				
				if(rs.next()){
					idSpecie = rs.getInt(1);
				}
				else{
					logger.warning("Non e stato possibile recuperare la specie con text code " + textCodeSpecie);
						}
				
			}
			finally{
				if(rs != null){
					rs.close();
				}
				if(pst != null){
					pst.close();
				}
			}
			
			return idSpecie;
			
		}
	
	
	public static int getIdRazzaFromTextCodeRazza(String textCodeRazza, Connection conn, String matricola) throws SQLException{
		
		int idRazza = -1;
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			String query = "select code from m_lookup_razze where text_code = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, textCodeRazza);
			rs = pst.executeQuery();
			
			if(rs.next()){
				idRazza = rs.getInt(1);
			}
			
			
		}
		finally{	
			if(rs != null){
				rs.close();
			}
			if(pst != null){
				pst.close();
			}
		}
		
		return idRazza;
		
	}
	
	
	public static int getIdRegioneFromIstatAsl(String istatAslOriginale, Connection conn) throws SQLException{
		
		
		int idRegione = -1;
		
		if(istatAslOriginale != null && istatAslOriginale.length() > 1){
			
			String iniziale = istatAslOriginale.substring(0, 1);
			
			if(iniziale.equals("R")){
				idRegione = 15;
			}
			else{
				
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				try{
					String query = "select code from lookup_regioni where inizialeistat = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, iniziale);
					rs = pst.executeQuery();
					
					if(rs.next()){
						idRegione = rs.getInt(1);
					}
					else{
						logger.warning("Non e stato possibile recuperare la regione con istat asl " + istatAslOriginale);
					}
					
				}
				finally{
					if(rs != null){
						rs.close();
					}
					if(pst != null){
						pst.close();
					}
				}
			
			}
			
		}
		
		return idRegione;
	}
	
	
	public static List<Object> resultSetToHashMapLookup(ResultSet rs) throws SQLException
    {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();//int size =rs.size(); 
      
        ArrayList listRecord = new ArrayList<>();
            
              while (rs.next())
              {
                  HashMap row = new LinkedHashMap(columns);
                    for(int i=1; i<=columns; ++i)
                    {           
                        row.put(md.getColumnName(i),rs.getObject(i));
                
                    }   
                    listRecord.add(row);
              }
                
                
        return listRecord;
    }

	
	   public static List<MetadatoTemplate> resultSetToHashMap(ArrayList<Record> rs) throws SQLException
	    {
	  
	        int size =rs.size();// md.getColumnCount();
	  
	        List<MetadatoTemplate> listRecord = new LinkedList<>();
	        //HashMap<String, Object> list = new LinkedHashMap<>();

	            HashMap row = new LinkedHashMap(size);
	            for(int i=0; i<size; ++i)
	            {           
	                //row.put(md.getColumnName(i),rs.getObject(i));
	                //row.put(rs.get(i).getLabel(), rs.get(i).getValue());
	                MetadatoTemplate record = new MetadatoTemplate(rs.get(i).getAttributo(), rs.get(i).getLabel(), rs.get(i).getValue(), rs.get(i).getListaLookup(), rs.get(i).getName(), rs.get(i).getStyle());
	                listRecord.add(record);
	                
	            }
	            //list.put("lista",listRecord);
	            return listRecord;
	    }
	    

	
}

