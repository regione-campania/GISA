/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.contacts.base.ContactAddressList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class SoggettoFisico extends GenericBean  {
	 
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.SoggettoFisico.class);
	  static {
	    if (System.getProperty("DEBUG") != null) {
	      log.setLevel(Level.DEBUG);
	    }
	  }
	  
	  
	private int idAsl ;
	private String descrAsl ; 
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
	
	private boolean provenienzaEstera;
	private boolean cfLibero; //Modificabile solo da db!
	
	public SoggettoFisico() {
		indirizzo = new Indirizzo(); 
	}
	
	
	
	public int getIdAsl() {
		return idAsl;
	}



	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}



	public String getDescrAsl() {
		return descrAsl;
	}



	public void setDescrAsl(String descrAsl) {
		this.descrAsl = descrAsl;
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
	

	public SoggettoFisico(String cf,Connection db) throws SQLException {
		
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
		        "left join opu_operatore op on (os.id_operatore = op.id)  where o.trashed_date is null and trim(o.codice_fiscale) ilike ?");
		
		PreparedStatement pst = db.prepareStatement(sqlSelect.toString());
	    pst.setString(1, cf);
	    ResultSet rs = pst.executeQuery();
	    if (rs.next()) {
	      buildRecord(rs);
	      calcolaAsl(db, indirizzo.getComune());
	    }

	   
	    

	    
	    rs.close();
	    pst.close();
		
	}
	
	
	


	public SoggettoFisico(HttpServletRequest request) {

		    if (request.getParameter("tipoSoggettoFisico") != null) {
		    	this.tipoSoggettoFisico = Integer.parseInt(
		          (String) request.getParameter("tipoSoggettoFisico"));
		    }
		      
		      this.nome = (String) request.getParameter("nome");
		      this.cognome = (String) request.getParameter("cognome");
		      this.codFiscale = (String) request.getParameter("codFiscaleSoggetto");
		      this.documentoIdentita = (String) request.getParameter("documentoIdentita");
		      this.comuneNascita = (String) request.getParameter("comuneNascita");
		      this.provinciaNascita = (String) request.getParameter("provinciaNascita");
		      this.setDataNascita((String) request.getParameter("dataNascita"));
		      this.setFax(request.getParameter("fax"));
		      this.setTelefono1(request.getParameter("telefono1"));
		      this.setTelefono2(request.getParameter("telefono2"));
		      this.setEmail(request.getParameter("email"));
		      this.sesso = (String) request.getParameter("sesso");
		      this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
		      
		      
		      /*this.indirizzo = new Indirizzo();
		      this.indirizzo.setCap(request.getParameter("addressLegaleZip"));
		      this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
		      this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
		      this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
		      */
		      this.indirizzo = new Indirizzo();
		      if(request.getParameter("addressLegaleLine1")!=null)
		    	  this.indirizzo.setIdIndirizzo(Integer.parseInt(request.getParameter("addressLegaleLine1")));
		      //this.indirizzo.setCap(request.getParameter("cap"));
		      this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
		      this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
		      this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
		      this.indirizzo.setCap(request.getParameter("cap"));


		  }
	
	//NELLA NUOVA PAGINA UNICA DI MODIFICA, BISOGNA PRENDERE CAMPI DIVERSI
	//A SECONDA DEL RAPPRESENTANTE LEGALE O RESPONSABILE STABILIMENTO
	public SoggettoFisico(HttpServletRequest request, int tipo) {

	    if (request.getParameter("tipoSoggettoFisico") != null) {
	    	this.tipoSoggettoFisico = Integer.parseInt(
	          (String) request.getParameter("tipoSoggettoFisico"));
	    }
	      
	    if (tipo==1){ //PRIVATO
	        this.nome = (String) request.getParameter("nome");
		      this.cognome = (String) request.getParameter("cognome");
		      this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTesto");
		      this.idSoggetto = Integer.parseInt(request.getParameter("idSoggetto"));
		      this.documentoIdentita = (String) request.getParameter("documentoIdentita");
		      this.comuneNascita = (String) request.getParameter("comuneNascita");
		      this.provinciaNascita = (String) request.getParameter("provinciaNascita");
		      this.setDataNascita((String) request.getParameter("dataNascita"));
		      this.setFax(request.getParameter("fax"));
		      this.setTelefono1(request.getParameter("telefono1"));
		      this.setTelefono2(request.getParameter("telefono2"));
		      this.setEmail(request.getParameter("email"));
		      this.sesso = (String) request.getParameter("sesso");
		      this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
		      this.indirizzo = new Indirizzo();
		      this.indirizzo.setCap(request.getParameter("cap"));
		      this.indirizzo.setComune(request.getParameter("searchcodeIdComune"));
		      this.indirizzo.setProvincia(request.getParameter("searchcodeIdprovincia"));
		      this.indirizzo.setVia(request.getParameter("viaTesto"));
	    }
	    else if (tipo==2) //RESPONSABILE STABILIMENTO
	    {
	      this.nome = (String) request.getParameter("nomeResp");
	      this.cognome = (String) request.getParameter("cognomeResp");
	      this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTestoResp");
	      this.idSoggetto = Integer.parseInt(request.getParameter("idSoggettoResp"));
	      this.documentoIdentita = (String) request.getParameter("documentoIdentitaResp");
	      this.comuneNascita = (String) request.getParameter("comuneNascitaResp");
	      this.provinciaNascita = (String) request.getParameter("provinciaNascitaResp");
	      this.setDataNascita((String) request.getParameter("dataNascitaResp"));
	      this.setFax(request.getParameter("faxResp"));
	      this.setTelefono1(request.getParameter("telefono1Resp"));
	      this.setTelefono2(request.getParameter("telefono2Resp"));
	      this.setEmail(request.getParameter("emailResp"));
	      this.sesso = (String) request.getParameter("sessoResp");
	      this.provenienzaEstera = Boolean.valueOf(request.getParameter("esteroResp"));
	      this.indirizzo = new Indirizzo();
	      this.indirizzo.setCap(request.getParameter("capResp"));
	      this.indirizzo.setComune(request.getParameter("idComuneResponsabile"));
	      this.indirizzo.setProvincia(request.getParameter("idProvinciaResponsabile"));
	      this.indirizzo.setVia(request.getParameter("viaResponsabile"));
	    }
	    else if (tipo==4) // RAPPRESENTANTE LEGALE
	    	{ 
		        this.nome = (String) request.getParameter("nome");
			      this.cognome = (String) request.getParameter("cognome");
			      this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTesto");
			      this.idSoggetto = Integer.parseInt(request.getParameter("idSoggetto"));
			      this.documentoIdentita = (String) request.getParameter("documentoIdentita");
			      this.comuneNascita = (String) request.getParameter("comuneNascita");
			      this.provinciaNascita = (String) request.getParameter("provinciaNascita");
			      this.setDataNascita((String) request.getParameter("dataNascita"));
			      this.setFax(request.getParameter("fax"));
			      this.setTelefono1(request.getParameter("telefono1"));
			      this.setTelefono2(request.getParameter("telefono2"));
			      this.setEmail(request.getParameter("email"));
			      this.sesso = (String) request.getParameter("sesso");
			      this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
			      
			      this.indirizzo = new Indirizzo();
			      this.indirizzo.setCap(request.getParameter("capRapp"));
			      this.indirizzo.setComune(request.getParameter("idComuneRappresentante"));
			      this.indirizzo.setProvincia(request.getParameter("idProvinciaRappresentante"));
			      this.indirizzo.setVia(request.getParameter("viaRappresentante"));
		    }
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
	      this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
	      this.documentoIdentita = (String) request.getParameter("documentoIdentita");
	      this.insert(db);
	      


	  }
	
	public SoggettoFisico(HttpServletRequest request,Connection db, int tipo) throws SQLException {

	    if (request.getParameter("tipoSoggettoFisico") != null) {
	    	this.tipoSoggettoFisico = Integer.parseInt(
	          (String) request.getParameter("tipoSoggettoFisico"));
	    }
	    UserBean user = (UserBean)request.getSession().getAttribute("User");
	      
	    if (tipo==1){
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
	 	      this.indirizzo.setCap(request.getParameter("cap"));
	 	      this.indirizzo.setComune(request.getParameter("searchcodeIdComune"));
	 	      this.indirizzo.setProvincia(request.getParameter("searchcodeIdprovincia"));
	 	      this.indirizzo.setVia(request.getParameter("viaTesto"));
	 	      this.sesso = (String) request.getParameter("sesso");
	 	     this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
	 	      this.documentoIdentita = (String) request.getParameter("documentoIdentita");
	 	      this.insert(db);
	    }
	    else if (tipo==2) //RESPONSABILE STABILIMENTO
	    {
	      this.nome = (String) request.getParameter("nomeResp");
	      this.cognome = (String) request.getParameter("cognomeResp");
	      this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTestoResp");
	      this.comuneNascita = (String) request.getParameter("comuneNascitaResp");
	      this.provinciaNascita = (String) request.getParameter("provinciaNascitaResp");
	      this.setDataNascita((String) request.getParameter("dataNascitaResp"));
	      this.setFax(request.getParameter("faxResp"));
	      this.setTelefono1(request.getParameter("telefono1Resp"));
	      this.setTelefono2(request.getParameter("telefono2Resp"));
	      this.setEmail(request.getParameter("emailResp"));
	      this.setIpEnteredBy(user.getUserRecord().getIp());
	      this.setIpModifiedBy(user.getUserRecord().getIp());
	      this.setEnteredBy(user.getUserId());
	      this.setModifiedBy(user.getUserId());
	      this.indirizzo = new Indirizzo();
	      this.indirizzo.setIdIndirizzo(request.getParameter("idViaResponsabile"));
	      this.indirizzo.setCap((request.getParameter("capResp") != null) ? request.getParameter("capResp"): "");
	      this.indirizzo.setComune(request.getParameter("idComuneResponsabile"));
	      this.indirizzo.setProvincia(request.getParameter("idProvinciaResponsabile"));
	      this.indirizzo.setVia(request.getParameter("viaResponsabile"));
	      this.sesso = (String) request.getParameter("sessoResp");
	      this.provenienzaEstera = Boolean.valueOf(request.getParameter("esteroResp"));
	      this.documentoIdentita = (String) request.getParameter("documentoIdentitaResp");
	      this.insert(db);
	    }
	    else if (tipo==3){
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
		 	      this.indirizzo.setCap(request.getParameter("capRapp"));
		 	      this.indirizzo.setComune(request.getParameter("idComuneRappresentante"));
		 	      this.indirizzo.setProvincia(request.getParameter("idProvinciaRappresentante"));
		 	      this.indirizzo.setVia(request.getParameter("viaRappresentante"));
		 	      this.sesso = (String) request.getParameter("sesso");
		 	     this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
		 	      this.documentoIdentita = (String) request.getParameter("documentoIdentita");
		 	      this.insert(db);
		    }


	  }
	
	public SoggettoFisico(EventoCessione cessione ,Connection db, User user) throws SQLException {


	   // UserBean user = (UserBean)request.getSession().getAttribute("User");
	      
	      this.nome = (String) cessione.getNome();
	      this.cognome = (String) cessione.getCognome();
	      this.codFiscale = (String) cessione.getCodiceFiscale();
	      this.comuneNascita = (String) cessione.getLuogoNascita();
	      this.provinciaNascita = (String) cessione.getLuogoNascita();
	      this.dataNascita = cessione.getDataNascita();
	      this.setDataNascita(cessione.getDataNascita());
	      this.setFax("");
	      this.setTelefono1("");
	      this.setTelefono2("");
	      this.setEmail("");
	      this.setIpEnteredBy(user.getIp());
	      this.setIpModifiedBy(user.getIp());
	      this.setEnteredBy(user.getId());
	      this.setModifiedBy(user.getId());
	      this.indirizzo = new Indirizzo();
	      this.enteredBy = user.getId();
	      this.modifiedBy = user.getId();
	      
	      this.indirizzo.setCap(cessione.getCap());
	      this.indirizzo.setComune(cessione.getIdComune());
	      this.indirizzo.setIdProvincia(cessione.getIdProvincia());
	      this.indirizzo.setVia(cessione.getIndirizzo());
	      this.sesso = cessione.getSesso();
	      this.documentoIdentita = cessione.getDocIdentita();
	      this.telefono1 = cessione.getNumeroTelefono();
	      this.insert(db);
	      


	  }
	
	
	public SoggettoFisico(EventoAdozioneFuoriAsl cessione ,Connection db, User user) throws SQLException {


		   // UserBean user = (UserBean)request.getSession().getAttribute("User");
		      
		      this.nome = (String) cessione.getNome();
		      this.cognome = (String) cessione.getCognome();
		      this.codFiscale = (String) cessione.getCodiceFiscale();
		      this.comuneNascita = (String) cessione.getLuogoNascita();
		      this.provinciaNascita = (String) cessione.getLuogoNascita();
		      this.dataNascita = cessione.getDataNascita();
		      this.setDataNascita(cessione.getDataNascita());
		      this.setFax("");
		      this.setTelefono1("");
		      this.setTelefono2("");
		      this.setEmail("");
		      this.setIpEnteredBy(user.getIp());
		      this.setIpModifiedBy(user.getIp());
		      this.setEnteredBy(user.getId());
		      this.setModifiedBy(user.getId());
		      this.indirizzo = new Indirizzo();
		      this.enteredBy = user.getId();
		      this.modifiedBy = user.getId();
		      
		      this.indirizzo.setCap(cessione.getCap());
		      this.indirizzo.setComune(cessione.getIdComune());
		      this.indirizzo.setIdProvincia(cessione.getIdProvincia());
		      this.indirizzo.setVia(cessione.getIndirizzo());
		      this.sesso = cessione.getSesso();
		      this.documentoIdentita = cessione.getDocIdentita();
		      this.telefono1 = cessione.getNumeroTelefono();
		      this.insert(db);
		      


		  }
	
	
	public SoggettoFisico verificaSoggetto(Connection db ) throws SQLException
	{
		SoggettoFisico soggettoEsistente = new SoggettoFisico(this.getCodFiscale(),db);
		return soggettoEsistente ;
		
	}
	
	
	
	
	public SoggettoFisico(Connection db, int idSoggetto) throws SQLException{
		if (idSoggetto == -1){
			throw new SQLException("Invalid Account");
		}
		
		
		StringBuffer sqlSelect = new StringBuffer("");
		sqlSelect.append(
		        "SELECT distinct o.*, o.id as id_soggetto ," +
		        "i.*, i.id as id_indirizzo, asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia   " +
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
		  this.documentoIdentita = rs.getString("documento_identita");
		  this.provenienzaEstera= rs.getBoolean("provenienza_estera");
		  this.cfLibero= rs.getBoolean("cf_libero");
	      this.indirizzo = new Indirizzo(rs);
	    	  
	  }
	
	
	  
	 public boolean insert(Connection db) throws SQLException{
		 StringBuffer sql = new StringBuffer();
		 try{
			 
			 
			 
			 if (this.indirizzo.getIdIndirizzo()<0)
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
			 
			 if (provenienzaEstera ==true){
				 sql.append(", provenienza_estera");
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
			 
			 
			 if (documentoIdentita != null && !("").equals(documentoIdentita)){
				 sql.append(", documento_identita");
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
				 
				 if (provenienzaEstera == true){
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
				 
				 if (documentoIdentita != null && !("").equals(documentoIdentita)){
					 sql.append(", ?");
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
				 
				 if (provenienzaEstera ==true ){
						pst.setBoolean(++i, this.isProvenienzaEstera());
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
				 
				 if (documentoIdentita != null && !("").equals(documentoIdentita)){
					 pst.setString(++i, this.getDocumentoIdentita());
				 }
		      
		      
		      
		      
		      pst.execute();
		      pst.close();
		      
		      this.insertStorico(db);
		      
		      
		      //Aggiungi relazione operatore - soggetto fisico
		      
		   //   this.aggiungiRelazione(db, idOperatore, tipoLegameSoggettoOperatore);
		      
		      
			 
		 }catch (SQLException e) {
		     
			      throw new SQLException(e.getMessage());
			    } finally {
			    
			    }
		 
		 return true;
		 
	 }
	 
	 
	 public boolean insertStorico(Connection db) throws SQLException{
		 StringBuffer sql = new StringBuffer();
		 try{
			 
			    
			 
		
			 
			 sql.append("INSERT INTO opu_soggetto_fisico_storico (");
			 
			
			 
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
			 
				 sql.append(", entered , modified ");
			
			 
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
			 
			 
			 if (documentoIdentita != null && !("").equals(documentoIdentita)){
				 sql.append(", documento_identita");
			 }
			 
			 sql.append(", id_opu_soggetto_fisico");
			 
			 
			 sql.append(")");
		      
		      sql.append("VALUES (?,?,?,?,?,?,?");
		      
		      if(this.indirizzo!=null && this.indirizzo.getIdIndirizzo()>0)
			    {
					 sql.append(", ?");		      
				}
			 
		     
		      
		      
				 if (enteredBy > -1){
					 sql.append(",?");
				 }
				 
				 if (modifiedBy > -1){
					 sql.append(",?");
				 }
				 sql.append(",current_timestamp , current_timestamp ");
				 
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
				 
				 if (documentoIdentita != null && !("").equals(documentoIdentita)){
					 sql.append(", ?");
				 }
				 sql.append(", ?");
				
				 
		      sql.append(")");
		      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		     
		      
		      
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
				 
				 if (documentoIdentita != null && !("").equals(documentoIdentita)){
					 pst.setString(++i, this.getDocumentoIdentita());
				 }
				 pst.setInt(++i,  this.getIdSoggetto());
				
		      
		      
		      
		      
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
		  return update(db,true);
		  
	  }
	  
	  
	  
	  
	  
	  public int update(Connection db,boolean aggiornaIndirizzo) throws SQLException {
			 
		  int resultCount = -1;
		  try {
			  
		
		//	System.out.println("INIZIO UPDATE SOGGETTO FISICO id: "+idSoggetto);
			StringBuffer sql = new StringBuffer();
			sql.append("update opu_soggetto_fisico ");
			sql.append(" set codice_fiscale = ?, data_nascita = ?, fax = ?, sesso = ?, provenienza_estera = ?,nome = ?,  cognome = ?, documento_identita = ?, comune_nascita = ?, provincia_nascita = ?, telefono = ?, "
					+ "telefono1 = ?, email = ?, modifiedby = ?, ipmodifiedby = ? ");
			
			if(aggiornaIndirizzo)
			{
				if (indirizzo.getIdIndirizzo()>-1)
					sql.append(", indirizzo_id = ?");
				else
				{
					indirizzo.insert(db);
					sql.append(", indirizzo_id = ? ");
				}
			}
				
			sql.append(" where id = ? ");
			
			PreparedStatement pst = db.prepareStatement(sql.toString());
			int i = 0;
			pst.setString(++i, codFiscale);
			pst.setTimestamp(++i, dataNascita);
			pst.setString(++i, fax);
			pst.setString(++i, sesso);
			pst.setBoolean(++i, provenienzaEstera);
			pst.setString(++i, nome);
			pst.setString(++i, cognome);
			pst.setString(++i, documentoIdentita);
			pst.setString(++i, comuneNascita);
			pst.setString(++i, provinciaNascita);
			pst.setString(++i, telefono1);
			pst.setString(++i, telefono2);
			pst.setString(++i, email);
			pst.setInt(++i, modifiedBy);
			pst.setString(++i, ipModifiedBy);
			if(aggiornaIndirizzo)
			{
				if (indirizzo.getIdIndirizzo()>-1)
					pst.setInt(++i, indirizzo.getIdIndirizzo());
			}
			pst.setInt(++i, idSoggetto);
			resultCount = pst.executeUpdate();
			pst.close();
			
			
		  }catch (SQLException e) {
				throw new SQLException(e.getMessage());
			} finally {
				
			}
			this.insertStorico(db);
					
		   	
		    return resultCount;
	  }
	 
	  public int updateSoloIndirizzo(Connection db) throws SQLException  {
		  
		  StringBuffer sql = new StringBuffer();
			sql.append("update opu_soggetto_fisico ");
			sql.append(" set modifiedby = ?, ipmodifiedby = ?, indirizzo_id = ? where id = ? ");
			  int resultCount = -1;
			PreparedStatement pst;
			try {
				pst = db.prepareStatement(sql.toString());
			
					
			int i = 0;
			pst.setInt(++i, this.modifiedBy);
			pst.setString(++i, this.ipModifiedBy);
			pst.setInt(++i, this.indirizzo.getIdIndirizzo());
			pst.setInt(++i, this.idSoggetto);
		//	System.out.println("Query update indirizzo soggetto fisico: "+pst.toString());
			 resultCount = pst.executeUpdate();
			 pst.close();
			}catch (SQLException e) {
					throw new SQLException(e.getMessage());
				    } finally {
				    }
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
	  
	  public int calcolaAsl(Connection db ,int comune) throws SQLException
		{
			int idAsl= -1 ;
			String sql = "select codiceistatasl,description  from comuni1 join lookup_asl_rif asl on codiceistatasl::int = code where id = "+comune ;
			ResultSet rs = db.prepareStatement(sql).executeQuery();
			if (rs.next())
			{
				this.idAsl = rs.getInt(1);
				this.descrAsl = rs.getString(2);
			}
			return idAsl ;
		}



	public void setProvenienzaEstera(boolean provenienzaEstera) {
		this.provenienzaEstera = provenienzaEstera;
	}



	public boolean isProvenienzaEstera() {
		return provenienzaEstera;
	}
	  
	public boolean isCfLibero() {
		return cfLibero;
	}
	  
	 public int verificaPresenzaCf(Connection db) throws SQLException
		{
			int idTrovato= -1 ;
			
			if (this.codFiscale.length()!=16) //SE NON E' UN CF STANDARD PERMETTI DUPLICATI (ES. 'N.D.')
				return -1;
			
			String sql = "select id from opu_soggetto_fisico where codice_fiscale = ? and trashed_date is null ";
			PreparedStatement pst = db.prepareStatement(sql.toString());
			
			pst.setString(1, this.codFiscale);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				idTrovato = rs.getInt(1);
				}
			if (idTrovato==-1 || idTrovato==this.idSoggetto) //Se non ho trovato risultati, oppure ho trovato il soggetto che sto modificando
				return -1; //ok
			return idTrovato; //Altrimenti restituisci l'id del soggetto che ha il mio codice fiscale
			
		}


	
}
