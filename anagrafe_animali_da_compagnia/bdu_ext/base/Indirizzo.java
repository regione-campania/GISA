/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Indirizzo extends GenericBean {

	
	
	
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.Indirizzo.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	
	private int idIndirizzo = -1;
	private String cap;
	private int comune = -1;
	private String descrizioneComune ;
	private String provincia;
	private String via;
	private String nazione;
	private double latitudine;
	private double longitudine;
	private int enteredBy = -1;
	private int modifiedBy = -1;
	private String ipEnteredBy;
	private String ipModifiedBy;
	private int idProvincia = -1 ;
	private int tipologiaSede = -1;
	private String descrizione_provincia;
	private int idAsl =-1;
	private String descrizioneAsl ="";
	
	
	public Indirizzo(){
		
	}
	
	public void inserComune (Connection db)
	{
		
		
	}
	
public Indirizzo(HttpServletRequest request,Connection db) throws SQLException{
	
	UserBean user = (UserBean) request.getSession().getAttribute("User");
	
	if ((String)request.getParameter("searchcodeIdprovincia") != null){
	if (new Integer ((String)request.getParameter("searchcodeIdprovincia")) > -1)
		this.setProvincia(request.getParameter("searchcodeIdprovincia"));
	else
		this.setProvincia(request.getParameter("searchcodeIdprovinciaTesto"));
	}else if ((String)request.getParameter("searchcodeIdprovinciaAsl") != null){
		this.setProvincia((String)request.getParameter("searchcodeIdprovinciaAsl"));
	}
	this.setComune(request.getParameter("searchcodeIdComune"));
	this.setVia(request.getParameter("viaTesto"));
	this.setLatitudine(request.getParameter("latitudine"));
	this.setLongitudine(request.getParameter("longitudine"));
	this.setCap(request.getParameter("cap"));
	this.setEnteredBy(user.getUserId());
	this.setModifiedBy(user.getUserId());
	String ip = user.getUserRecord().getIp();
	this.setIpEnteredBy(ip);
	this.setIpModifiedBy(ip);
	
	this.insert(db);
	
		
	}

	
	
	
	public int getIdAsl() {
	return idAsl;
}

public void setIdAsl(int idAsl) {
	this.idAsl = idAsl;
}

public String getDescrizioneAsl() {
	return descrizioneAsl;
}

public void setDescrizioneAsl(String descrizioneAsl) {
	this.descrizioneAsl = descrizioneAsl;
}

	public String getDescrizione_provincia() {
	return (descrizione_provincia!=null) ? descrizione_provincia.trim() : "";
}

public void setDescrizione_provincia(String descrizione_provincia) {
	this.descrizione_provincia = descrizione_provincia;
}

	public String getDescrizioneComune() {
		return (descrizioneComune!=null) ? descrizioneComune.trim() : "";
}

public void setDescrizioneComune(String descrizioneComune) {
	this.descrizioneComune = descrizioneComune;
}

	public int getIdProvincia() {
	return idProvincia;
}

public void setIdProvincia(int idProvincia) {
	this.idProvincia = idProvincia;
}

	public int getIdIndirizzo() {
		return idIndirizzo;
	}




	public static Logger getLog() {
		return log;
	}





	public static void setLog(Logger log) {
		Indirizzo.log = log;
	}





	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}



	public String getCap() {
		return (cap!=null) ? cap.trim() : "";
	}


	public void setCap(String cap) {
		this.cap = cap;
	}

	

	public int getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = new Integer(comune).intValue();
	}

	
	

	public void setComune(int idComune){
		this.comune =idComune;
	}




	public String getProvincia() {
		return (provincia!=null) ? provincia.trim() : "" ;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}


	public String getVia() {
		return (via!=null) ? via.trim() : "" ;
	}


	public void setVia(String via) {
		this.via = via;
	}


	public String getNazione() {
		return nazione;
	}





	public void setNazione(String nazione) {
		this.nazione = nazione;
	}






	public double getLatitudine() {
		return latitudine;
	}





	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}





	public double getLongitudine() {
		return longitudine;
	}





	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}

	
	
	  public void setLatitudine(String latitude) {
		    try {
		      this.latitudine = Double.parseDouble(latitude.replace(',', '.'));
		    } catch (Exception e) {
		      this.latitudine = 0;
		    }
		  }


      public void setLongitudine(String longitude) {
		    try {
		      this.longitudine = Double.parseDouble(longitude.replace(',', '.'));
		    } catch (Exception e) {
		      this.longitudine = 0;
		    }
		  }





	public int getTipologiaSede() {
		return tipologiaSede;
	}





	public void setTipologiaSede(int tipologiaSede) {
		this.tipologiaSede = tipologiaSede;
	}


	public void setTipologiaSede(String tipologiaSede) {
		this.tipologiaSede = new Integer(tipologiaSede).intValue();
	}


	public int getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}


	public int getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getIpEnteredBy() {
		return ipEnteredBy;
	}


	public void setIpEnteredBy(String ipEnteredBy) {
		this.ipEnteredBy = ipEnteredBy;
	}


	public String getIpModifiedBy() {
		return ipModifiedBy;
	}


	public void setIpModifiedBy(String ipModifiedBy) {
		this.ipModifiedBy = ipModifiedBy;
	}
	
	
	
	
	 public boolean insert(Connection db) throws SQLException{
		 StringBuffer sql = new StringBuffer();
		 try{
			 
			 
			 cap = ComuniAnagrafica.getCap(db, this.comune);
			 //Controllare se c'è già soggetto fisico, se no inserirlo
			 idIndirizzo = DatabaseUtils.getNextSeq(db, "opu_indirizzo_id_seq");
			 
			 sql.append("INSERT INTO opu_indirizzo (");
			 
			 if (idIndirizzo > -1)
				 sql.append("id,");
			 
			 sql.append("via, cap, comune, provincia, nazione");
			 
			 
				 sql.append(", latitudine");
			 
			 
		
				 sql.append(", longitudine");
			 
			 
			 sql.append(")");
		     
			 sql.append("VALUES (?,?,?,?,?");
				 
		      
		      if (idIndirizzo > -1) {
		          sql.append(",?");
		        }
		      
		     
					 sql.append(", ?");
				 
				 
				
					 sql.append(", ?");
				 
		      
		      
		      sql.append(")");
			 
			 int i = 0;
			 
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		      if (idIndirizzo > -1) {
		    	  pst.setInt(++i, idIndirizzo);
		      }
		      
		      
		      pst.setString(++i, this.via);
		      pst.setString(++i, this.cap);
		      pst.setInt(++i, this.comune);
		      pst.setString(++i, this.provincia);
		      pst.setString(++i, this.nazione);
		      pst.setDouble(++i, this.latitudine);
		      pst.setDouble(++i, this.longitudine);
				 
		      
		      pst.execute();
		      pst.close();

		  //   	System.out.println("Query di insert indirizzo eseguita: "+pst.toString());

		      
		      
			 
		 }catch (SQLException e) {
		     
			      throw new SQLException(e.getMessage());
			    } finally {
			    
			    }
		 
		 return true;
		 
	 }
	 
	 
	 
		public Indirizzo(Connection db, int idIndirizzo) throws SQLException{
			if (idIndirizzo == -1){
				throw new SQLException("Invalid Indirizzo");
			}
			
			PreparedStatement pst = db.prepareStatement("Select i.*,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia  from opu_indirizzo i " +
					"left join comuni1 on (comuni1.id = i.comune) " +
					"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
			"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
			" where i.id = ? ");
					
		    pst.setInt(1, idIndirizzo);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		      this.idAsl = rs.getInt("code");
		      this.descrizioneAsl = rs.getString("description");
		    }
		    else
		    {
		    	/*COMMENTATO DA G.BALZANO IN QUANTO MOLTI OPERATORI NON HANNO ASSOCIATO UN INDIRIZZO E MANDA SEMPRE IN ECCEZIONE*/
		    	//throw new IndirizzoNotFoundException("Indirizzo non Trovato");
		    }
		    /*COMMENTATO DA G.BALZANO IN QUANTO MOLTI OPERATORI NON HANNO ASSOCIATO UN INDIRIZZO E MANDA SEMPRE IN ECCEZIONE*/
		    /*if (idIndirizzo == -1) {
		      throw new SQLException(Constants.NOT_FOUND_ERROR);
		    }*/
		    

		    
		    rs.close();
		    pst.close();
		}
		
		
		
		  public Indirizzo (ResultSet rs) throws SQLException {
			  
			    buildRecord(rs);

			  }
		

	 
		
		  protected void buildRecord(ResultSet rs) throws SQLException {
			  
			  this.descrizione_provincia = rs.getString("descrizione_provincia");
			  this.idIndirizzo = rs.getInt("id");
			  this.comune = rs.getInt("comune");
			  if(rs.getString("cod_provincia")!=null)
				  this.idProvincia = Integer.parseInt(rs.getString("cod_provincia"));
			  this.provincia = rs.getString("provincia");
			  this.cap = rs.getString("cap");
			  this.descrizioneComune = rs.getString("descrizione_comune");
			  this.via = rs.getString("via");
			  this.nazione = rs.getString("nazione");
			  this.latitudine = rs.getDouble("latitudine");
			  this.longitudine = rs.getDouble("longitudine");
			  try{
			  	this.tipologiaSede = rs.getInt("tipologia_sede");
			  }catch (org.postgresql.util.PSQLException e){
				  
			  }
			  

			  
		  }
				 
		 
	
public HashMap<String, Object> getHashmap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
{

	HashMap<String, Object> map = new HashMap<String, Object>();
	Field[] campi = this.getClass().getDeclaredFields();
	Method[] metodi = this.getClass().getMethods();
	for (int i = 0 ; i <campi.length; i++)
	{
		String nomeCampo = campi[i].getName();
		
		for (int j=0; j<metodi.length; j++ )
		{
			if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
			{
				if(nomeCampo.equalsIgnoreCase("via"))
				{
					//map.put("descrizione", metodi[j].invoke(this));
					map.put("descrizionevia", metodi[j].invoke(this));
				}
				else{
					if(nomeCampo.equalsIgnoreCase("idIndirizzo"))
					{
						//map.put("codice", metodi[j].invoke(this));
						map.put("codicevia", metodi[j].invoke(this));
					}
					else
					{
						map.put(nomeCampo, (""+metodi[j].invoke(this)).trim());
					}
				}
			}
			
		}
		
	}
	
	return map ;
	
}	

public String toString()
{
	  String descrizione = "" ;
	  
	  if(via!=null)
		  descrizione = via;
	  if (cap != null)
		  descrizione += ", " + cap;
	  if (descrizioneComune!=null)
		  descrizione += " " + descrizioneComune ;
	  if(descrizione_provincia!=null )
		  descrizione+=" , "+descrizione_provincia ;

	  return descrizione ;
}

}
	
	
	
