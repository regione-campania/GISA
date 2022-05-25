/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu_ext.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddress;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.contacts.base.ContactAddressList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class SoggettoFisico extends GenericBean  {
	 
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu_ext.base.SoggettoFisico.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	  
	  
	  
	private int idSoggetto = -1;
	private int idTitolo;
	private String nome;
	private String cognome;
	private String sesso;
	private String codFiscale;
	private String documentoIdentita = "";
	private Indirizzo indirizzo;
	private java.sql.Timestamp dataNascita;
	private String dataNascitaString ;
	private String comuneNascita;
	private String provinciaNascita;
	
	private int enteredBy;
	private int modifiedBy;
	private int owner;
	private String ipEnteredBy;
	private String ipModifiedBy;
	
	private int tipoSoggettoFisico;
	private int statoRuolo;
	
	
	private String telefono1;
	private String telefono2;
	private String email;
	private String fax;
	
	public SoggettoFisico() {
		indirizzo = new Indirizzo(); 
	}
	
	
	
	public int getIdSoggetto() {
		return idSoggetto;
	}


	
	public String getDataNascitaString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (dataNascita!=null) ? sdf.format(new Date(dataNascita.getTime())) : "";
	}



	public void setDataNascitaString(String dataNascitaString) {
		this.dataNascitaString = dataNascitaString;
	}



	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	
	public void setIdSoggetto(String idSoggetto){
		this.idSoggetto = new Integer(idSoggetto).intValue();
	}


	public void setTipoSoggettoFisico(int tipoSoggettoFisico) {
		this.tipoSoggettoFisico = tipoSoggettoFisico;
	}
	
	


	public int getIdTitolo() {
		return idTitolo;
	}



	public void setIdTitolo(int idTitolo) {
		this.idTitolo = idTitolo;
	}
	
	
	public void setIdTitolo(String idTitolo){
		this.idTitolo = new Integer(idTitolo).intValue();
	}



	public void setDataNascita(java.sql.Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}



	
	
	public String getSesso() {
		return (sesso!=null) ? sesso.trim() : "";
	}



	public void setSesso(String sesso) {
		this.sesso = sesso;
	}



	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getCodFiscale() {
		return (codFiscale!=null) ? codFiscale.trim() : "";
	}

	public String getNome() {
		return (nome!=null) ? nome.trim() : "";
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return (cognome!=null) ? cognome.trim() : "";
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getTipoSoggettoFisico() {
		return tipoSoggettoFisico;
	}

	public void setTipo_soggetto_fisico(int tipo_soggetto_fisico) {
		this.tipoSoggettoFisico = tipo_soggetto_fisico;
	}



	public java.sql.Timestamp getDataNascita() {
		return dataNascita;
	}



	public void setDataNascita(String data) {
		this.dataNascita = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}



	public String getComuneNascita() {
		return (comuneNascita!=null) ? comuneNascita.trim() : "";
	}



	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}



	public String getProvinciaNascita() {
		return (provinciaNascita!=null) ? provinciaNascita.trim() : "";

	}



	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	
	


	public String getTelefono1() {
		return (telefono1!=null) ? telefono1.trim() : "";
	}



	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}



	public String getTelefono2() {
		return (telefono2!=null) ? telefono2.trim() : "";
	}



	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}



	public String getEmail() {
		return (email!=null) ? email.trim() : "";
	}



	public String getDocumentoIdentita() {
		return documentoIdentita;
	}



	public void setDocumentoIdentita(String documentoIdentita) {
		this.documentoIdentita = documentoIdentita;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getFax() {
		return (fax!=null) ? fax.trim() : "";
	}



	public void setFax(String fax) {
		this.fax = fax;
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



	public int getOwner() {
		return owner;
	}



	public void setOwner(int owner) {
		this.owner = owner;
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
	
	
	



	public static Logger getLog() {
		return log;
	}



	public static void setLog(Logger log) {
		SoggettoFisico.log = log;
	}




	public int getStatoRuolo() {
		return statoRuolo;
	}



	public void setStatoRuolo(int statoRuolo) {
		this.statoRuolo = statoRuolo;
	}
	
	
	public void setStatoRuolo(String statoRuolo) {
		
		this.statoRuolo = new Integer(statoRuolo).intValue();

	}
	


	public SoggettoFisico(HttpServletRequest request) {

		    if (request.getParameter("tipoSoggettoFisico") != null) {
		    	this.tipoSoggettoFisico = Integer.parseInt(
		          (String) request.getParameter("tipoSoggettoFisico"));
		    }
		      
		      this.nome = (String) request.getParameter("nome");
		      this.cognome = (String) request.getParameter("cognome");
		      this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTesto");
		      this.idSoggetto = Integer.parseInt(request.getParameter("codFiscaleSoggetto"));
		      this.comuneNascita = (String) request.getParameter("comuneNascita");
		      this.provinciaNascita = (String) request.getParameter("provinciaNascita");
		      this.setDataNascita((String) request.getParameter("dataNascita"));
		      this.setFax(request.getParameter("fax"));
		      this.setTelefono1(request.getParameter("telefono1"));
		      this.setTelefono2(request.getParameter("telefono2"));
		      this.setEmail(request.getParameter("email"));
		      this.sesso = (String) request.getParameter("sesso");
		      
		      
		      
		      this.indirizzo = new Indirizzo();
		      this.indirizzo.setCap(request.getParameter("addressLegaleZip"));
		      this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
		      this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
		      this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
		      
		      


		  }
	
	
	public SoggettoFisico(HttpServletRequest request,Connection db) throws SQLException {

	    if (request.getParameter("tipoSoggettoFisico") != null) {
	    	this.tipoSoggettoFisico = Integer.parseInt(
	          (String) request.getParameter("tipoSoggettoFisico"));
	    }
	    UserBean user = (UserBean)request.getSession().getAttribute("User");
	      
	      this.nome = (String) request.getParameter("nome");
	      this.cognome = (String) request.getParameter("cognome");
	      this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTesto");
	      this.comuneNascita = (String) request.getParameter("comuneNascita");
	      this.provinciaNascita = (String) request.getParameter("provinciaNascita");
	      this.setDataNascita((String) request.getParameter("dataNascita"));
	      this.setFax(request.getParameter("fax"));
	      this.setTelefono1(request.getParameter("telefono1"));
	      this.setTelefono2(request.getParameter("telefono2"));
	      this.setEmail(request.getParameter("email"));
	      this.setIpEnteredBy(user.getUserRecord().getIp());
	      this.setIpModifiedBy(user.getUserRecord().getIp());
	      this.setEnteredBy(user.getUserId());
	      this.setModifiedBy(user.getUserId());
	      this.indirizzo = new Indirizzo();
	      this.indirizzo.setCap(request.getParameter("addressLegaleZip"));
	      this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
	      this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
	      this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
	      this.sesso = (String) request.getParameter("sesso");
	      this.insert(db);
	      


	  }
	
	
	
	
	public SoggettoFisico(Connection db, int idSoggetto) throws SQLException{
		if (idSoggetto == -1){
			throw new SQLException("Invalid Account");
		}
		
		
		StringBuffer sqlSelect = new StringBuffer("");
		sqlSelect.append(
		        "SELECT distinct o.*, o.id as id_soggetto ," +
		        "i.*,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia   " +
				"FROM opu_soggetto_fisico o " +
		        "left join opu_indirizzo i on o.indirizzo_id=i.id " +
				"left join comuni1 on (comuni1.id = i.comune) " +
				"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
				"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
		        "left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) " +
		        "left join opu_operatore op on (os.id_operatore = op.id)  where o.id = ?");
		
		PreparedStatement pst = db.prepareStatement(sqlSelect.toString());
	    pst.setInt(1, idSoggetto);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      buildRecord(rs);
	    }

	    if (idSoggetto == -1) {
	      throw new SQLException(Constants.NOT_FOUND_ERROR);
	    }
	    

	    
	    rs.close();
	    pst.close();
	}
	
	
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  this.idSoggetto = rs.getInt("id_soggetto");
		  this.idTitolo = rs.getInt("titolo");
		  this.nome = rs.getString("nome");
		  this.cognome = rs.getString("cognome");
		  this.codFiscale = rs.getString("codice_fiscale");
		  this.comuneNascita = rs.getString("comune_nascita");
		  this.provinciaNascita = rs.getString("provincia_nascita");
		  this.dataNascita = rs.getTimestamp("data_nascita");
		  this.sesso = rs.getString("sesso");
		  this.telefono1 = rs.getString("telefono");
		  this.telefono2 = rs.getString("telefono1");
		  this.email = rs.getString("email");
		  this.fax = rs.getString("fax");
		  this.enteredBy = rs.getInt("enteredby");
		  this.modifiedBy = rs.getInt("modifiedby");
		  this.ipEnteredBy = rs.getString("ipenteredby");
		  this.ipModifiedBy = rs.getString("ipmodifiedby");
		  
	      this.indirizzo = new Indirizzo(rs);
	      
		  
		  
	  }
	
	
	  
	 public boolean insert(Connection db) throws SQLException{
		 StringBuffer sql = new StringBuffer();
		 try{
			 
			 
			 
			 
			 this.indirizzo.insert(db);
			 //Controllare se c'è già soggetto fisico, se no inserirlo
			 idSoggetto = DatabaseUtils.getNextSeq(db, "opu_soggetto_fisico_id_seq");
			 
			 sql.append("INSERT INTO opu_soggetto_fisico (");
			 
			 if (idSoggetto > -1)
				 sql.append("id,");
			 
			 sql.append("titolo, cognome, nome, data_nascita, comune_nascita, provincia_nascita, codice_fiscale ");
			 if(this.indirizzo!=null && this.indirizzo.getIdIndirizzo()>0)
		    {
				 sql.append(", indirizzo_id");		      
			}
			 
			 if (enteredBy > -1){
				 sql.append(", enteredBy");
			 }
			 
			 if (modifiedBy > -1){
				 sql.append(", modifiedBy");
			 }
			 
			 if (ipEnteredBy != null && !ipEnteredBy.equals("")){
				 sql.append(", ipenteredBy");
			 }
			 
			 if (ipModifiedBy != null && !ipModifiedBy.equals("")){
				 sql.append(", ipModifiedBy");
			 }
			 
			 if (sesso != null && !sesso.equals("")){
				 sql.append(", sesso");
			 }
			 
			 if (telefono1 != null && !telefono1.equals("")){
				 sql.append(", telefono");
			 }
			 
			 
			 if (telefono2 != null && !telefono2.equals("")){
				 sql.append(", telefono1");
			 }
			 
			 
			 if (fax != null && !fax.equals("")){
				 sql.append(", fax");
			 }
			 
			 
			 if (email != null && !email.equals("")){
				 sql.append(", email");
			 }
			 
			 
			 
			 
			 sql.append(")");
		      
		      sql.append("VALUES (?,?,?,?,?,?,?");
		      
		      if(this.indirizzo!=null && this.indirizzo.getIdIndirizzo()>0)
			    {
					 sql.append(", ?");		      
				}
			 
		      if (idSoggetto > -1) {
		          sql.append(",?");
		        }
		      
		      
				 if (enteredBy > -1){
					 sql.append(",?");
				 }
				 
				 if (modifiedBy > -1){
					 sql.append(",?");
				 }
				 
				 if (ipEnteredBy != null && !ipEnteredBy.equals("")){
					 sql.append(",?");
				 }
				 
				 if (ipModifiedBy != null && !ipModifiedBy.equals("")){
					 sql.append(",?");
				 }
				 
				 if (sesso != null && !sesso.equals("")){
					 sql.append(",?");
				 }
				 
				 
				 
				 if (telefono1 != null && !telefono1.equals("")){
					 sql.append(",?");
				 }
				 
				 
				 if (telefono2 != null && !telefono2.equals("")){
					 sql.append(",?");
				 }
				 
				 
				 if (fax != null && !fax.equals("")){
					 sql.append(",?");
				 }
				 
				 
				 if (email != null && !email.equals("")){
					 sql.append(",?");
				 }
				 
				 
		      sql.append(")");
		      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      if (idSoggetto > -1) {
		    	  pst.setInt(++i, idSoggetto);
		      }
		      
		      
		      pst.setInt(++i, this.idTitolo);
		      pst.setString(++i, this.cognome);
		      pst.setString(++i, this.nome);
		      pst.setTimestamp(++i, this.getDataNascita());
		      pst.setString(++i, this.getComuneNascita());
		      pst.setString(++i, this.getProvinciaNascita());
		      pst.setString(++i, this.getCodFiscale());
		      
		      if(this.indirizzo!=null && this.indirizzo.getIdIndirizzo()>0)
		      {
		    	  pst.setInt(++i, this.getIndirizzo().getIdIndirizzo());
		      }
		      
		      
				 if (enteredBy > -1){
					 pst.setInt(++i, this.getEnteredBy());
				 }
				 
				 if (modifiedBy > -1){
					 pst.setInt(++i, this.getModifiedBy());
				 }
				 
				 if (ipEnteredBy != null && !ipEnteredBy.equals("")){
					 pst.setString(++i, this.getIpEnteredBy());
				 }
				 
				 if (ipModifiedBy != null && !ipModifiedBy.equals("")){
					 pst.setString(++i, this.getIpModifiedBy());
				 }
				 
				 if (sesso != null && !sesso.equals("")){
					pst.setString(++i, this.getSesso());
				 }
				 
				 if (telefono1 != null && !telefono1.equals("")){
					 pst.setString(++i, this.getTelefono1());
				 }
				 
				 
				 if (telefono2 != null && !telefono2.equals("")){
					 pst.setString(++i, this.getTelefono2());
				 }
				 
				 
				 if (fax != null && !fax.equals("")){
					pst.setString(++i, this.getFax());
				 }
				 
				 
				 if (email != null && !email.equals("")){
					pst.setString(++i, this.getEmail());
				 }
		      
		      
		      
		      
		      pst.execute();
		      pst.close();

		      
		      
		      //Aggiungi relazione operatore - soggetto fisico
		      
		   //   this.aggiungiRelazione(db, idOperatore, tipoLegameSoggettoOperatore);
		      
		      
			 
		 }catch (SQLException e) {
		     
			      throw new SQLException(e.getMessage());
			    } finally {
			    
			    }
		 
		 return true;
		 
	 }
	 
	 
	 public boolean aggiungiRelazione(Connection db, int idOperatore, int tipoLegame) throws SQLException{
		 
		 StringBuffer sql = new StringBuffer();
		 try{
			 
			 //Controllare se c'è già soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db, "rel_operatore_soggetto_fisico_seq");
			 
			 sql.append("INSERT INTO rel_operatore_soggetto_fisico (");
			 
			 if (idRelazione > -1)
				 sql.append("id,");
			 
			 sql.append("id_operatore, id_soggetto_fisico, tipo_soggetto_fisico");
			 
			 
			 sql.append(")");
		      
		      sql.append("VALUES (?,?,?");
			 
		      if (idRelazione > -1) {
		          sql.append(",?");
		        }
		      sql.append(")");
		      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		      if (idRelazione > -1) {
		    	  pst.setInt(++i, idRelazione);
		      }
		      
		      
		      pst.setInt(++i, idOperatore);
		      pst.setInt(++i, this.getIdSoggetto());
		      pst.setInt(++i, tipoLegame);
		      
		      pst.execute();
		      pst.close();

		  

		      
		      
			 
		 }catch (SQLException e) {
		     
			      throw new SQLException(e.getMessage());
			    } finally {
			    
			    }
		 
		 return true;
		 
	 }
	 
	  

	 
	 public void buildAddress(ActionContext context){
		 HttpServletRequest request = context.getRequest();
		 this.indirizzo = new Indirizzo();
	      this.indirizzo.setCap(request.getParameter("addressLegaleZip"));
	      this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
	      this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
	      this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
		}
	
	  public SoggettoFisico(ResultSet rs) throws SQLException {
		    buildRecord(rs);
		  }
	  
	  
		
	  public int update(Connection db) throws SQLException {
		  
		  
		//e lo risetto sulla request 
			System.out.println("INIZIO UPDATE SOGGETTO FISICO id: "+idSoggetto+" indirizzo: "+indirizzo);
		    int resultCount = 0;
		    
			System.out.println("Dati soggetto fisico: ");
			System.out.println("Nome: "+nome+" "+cognome);
			System.out.println("CF: "+codFiscale);
			System.out.println("Sesso: "+sesso);
			System.out.println("Comune nascita: "+comuneNascita);
			System.out.println("Provincia nascita: "+provinciaNascita);
			System.out.println("Telefono: "+telefono1);
			System.out.println("Fax: "+fax);
			
			
			
		    PreparedStatement pst = null;
		    StringBuffer sql = new StringBuffer(); 
		    sql.append("Update opu_soggetto_fisico ");
			 
			 
			 /*sql.append("set titolo = ?, cognome = ?, nome = ?, data_nascita = ?, comune_nascita = ?, provincia_nascita =? , codice_fiscale = ?, " +
			 		"indirizzo_residenza = ?, cap_residenza = ?, comune_residenza = ?, provincia_residenza = ?");*/
		    
		    sql.append("set fax = '"+fax+"'");
		//	sql.append(", sesso= '"+sesso+"'");
			sql.append(", nome= '"+nome+"'"); 
			sql.append(", cognome= '"+cognome+"'"); 
			sql.append(", comune_nascita= '"+comuneNascita+"'"); 
			sql.append(", provincia_nascita='"+provinciaNascita+"'");
			sql.append(", telefono= '"+telefono1+"'"); 
			sql.append(", telefono1= '"+telefono2+"'"); 
			sql.append(", email= '"+email+"'"); 
			sql.append(", modifiedBy = '"+modifiedBy+"'");
			sql.append(", ipModifiedBy = '"+ipModifiedBy+"'");
			sql.append(" where id = "+idSoggetto);
		      
		    pst = db.prepareStatement(sql.toString());
		    System.out.println("Query update soggetto fisico: "+pst.toString());
		    
		    resultCount = pst.executeUpdate();
		    System.out.println("Query update soggetto fisico: "+pst.toString());
		    pst.close();

		    
		    return resultCount;
	  }
	 
	  public HashMap<String, Object> getHashmap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	  {

	  	HashMap<String, Object> map = new HashMap<String, Object>();
	  	Field[] campi = this.getClass().getDeclaredFields();
	  	Method[] metodi = this.getClass().getMethods();
	  	for (int i = 0 ; i <campi.length; i++)
	  	{
	  		String nomeCampo = campi[i].getName();
	  		
	  		if (! nomeCampo.equalsIgnoreCase("indirizzo") && ! nomeCampo.equalsIgnoreCase("log") && ! nomeCampo.equalsIgnoreCase("enteredBy") 
	  				&& ! nomeCampo.equalsIgnoreCase("modifiedBy")  && ! nomeCampo.equalsIgnoreCase("ipEnteredBy") 
	  				&& ! nomeCampo.equalsIgnoreCase("ipModifiedBy") && !   nomeCampo.equalsIgnoreCase("owner"))
	  		{
	  		for (int j=0; j<metodi.length; j++ )
	  		{
	  			
	  			if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
	  			{
	  			if(nomeCampo.equalsIgnoreCase("codFiscale"))
				{
					map.put("descrizione", metodi[j].invoke(this));
				}
				else{
					if(nomeCampo.equalsIgnoreCase("idSoggetto"))
					{
						map.put("codice", metodi[j].invoke(this));
					}
					else
					{
	  					map.put(nomeCampo, ""+metodi[j].invoke(this)+"");

					}
				}
	  			
	  			
	  			

	  			}
	  			}
	  			
	  		}
	  		
	  	}
	  	
	  	if(indirizzo!=null)
	  	{
	  		HashMap<String, Object> mapIndirizzi = indirizzo.getHashmap();
	  		Iterator<String> itKey = mapIndirizzi.keySet().iterator();
	  		while(itKey.hasNext())
	  		{
	  			String key = itKey.next();
	  			map.put(key, mapIndirizzi.get(key));
	  		}
	  		
	  		
	  	}
	  	
	  	
	  	
	  	return map ;
	  	
	  }	
	  
	  
	

	
}
