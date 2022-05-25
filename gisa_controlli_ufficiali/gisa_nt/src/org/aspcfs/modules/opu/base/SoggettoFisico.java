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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.batik.svggen.font.table.LookupList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddress;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.contacts.base.ContactAddressList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class SoggettoFisico extends GenericBean  {


	private boolean esiste ;
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
	private String comuneNascita;
	private int idComuneNascita;
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
	private int id_opu_soggetto_fisico_storico ;
	private Timestamp modified ;
	private String nazioneNascita ;
	private String nazioneResidenza ;
	private int idSoggettoPrecedente ;
	
	

	

	
	public int getIdSoggettoPrecedente() {
		return idSoggettoPrecedente;
	}


	public void setIdSoggettoPrecedente(int idSoggettoPrecedente) {
		this.idSoggettoPrecedente = idSoggettoPrecedente;
	}


	public int getIdComuneNascita() {
		return idComuneNascita;
	}


	public void setIdComuneNascita(int idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}


	public String getNazioneNascita() {
		return nazioneNascita;
	}
	

	public boolean isEsiste() {
		return esiste;
	}


	public void setEsiste(boolean esiste) {
		this.esiste = esiste;
	}


	public void setNazioneNascita(String nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}


	public String getNazioneResidenza() {
		return nazioneResidenza;
	}


	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}


	public Timestamp getModified() {
		return modified;
	}

	private String dataNascitaString ;
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


	public SoggettoFisico() {
		indirizzo = new Indirizzo(); 
	}

 
	public void setDataNascitaString(String dataNascitaString) {
		this.dataNascitaString = dataNascitaString;
	}


	public SoggettoFisico(String cf,Connection db) throws SQLException {


		StringBuffer sqlSelect = new StringBuffer("");
		sqlSelect.append(
				"SELECT distinct max(storico.id) as id_soggetto_storico ,i.toponimo,i.civico,comnasc.id as id_comune_nascita," +
						"o.nome,o.cognome," +
						"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono,'R'||comuni1.codiceasl_bdn as codice_asl," +
						"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
						" o.id as id_soggetto ," +
						"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,coalesce(comuni1.nome,i.comune_testo) as descrizione_comune," +
						"lp.description as descrizione_provincia   " +
						"FROM opu_soggetto_fisico o " +
						"left join opu_soggetto_fisico_storico storico on o.id = storico.id_opu_soggetto_fisico " +
						"left join opu_indirizzo i on o.indirizzo_id=i.id " +
						"left join comuni1 on (comuni1.id = i.comune) " +
						"left join comuni1 comnasc on comnasc.nome ilike o.comune_nascita "+
						"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
						"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
						"left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) " +
						"left join opu_operatore op on (os.id_operatore = op.id)  "
						+ "where trim(o.codice_fiscale) ilike ? and o.trashed_date is null " +
						"group by o.nome,o.cognome,comnasc.id," +
						"o.codice_fiscale,o.comune_nascita,i.toponimo,i.civico,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
						"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
						" o.id  ," +
						"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome ,'R'||comuni1.codiceasl_bdn,coalesce(comuni1.nome,i.comune_testo)," +
				"lp.description " );

		PreparedStatement pst = db.prepareStatement(sqlSelect.toString());
		pst.setString(1, cf);
		System.out.println("#######################OPERATORE ACTION SUAP #################################### QUERY CHE STO PER ESEGUIRE VERIFICA SOGGETTO FISICO "+pst.toString());

		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			calcolaAsl(db, indirizzo.getComune());
		}
		else
		{
			indirizzo = new Indirizzo();
			
		}


		rs.close();
		pst.close();

	}



	public SoggettoFisico(Connection db, HttpServletRequest request,org.aspcfs.utils.web.LookupList nazioniList) {




		this.nome = (String) request.getParameter("nome");
		this.cognome = (String) request.getParameter("cognome");
		this.sesso = (String) request.getParameter("sesso");
		this.setDataNascita((String) request.getParameter("dataNascita"));
		this.comuneNascita = (String) request.getParameter("comuneNascitainput");
		this.provinciaNascita = (String) request.getParameter("provinciaNascita");
		this.nazioneNascita= (String) request.getParameter("nazioneNascita");
		this.codFiscale = (String) request.getParameter("codFiscaleSoggetto");
		this.email = (String) request.getParameter("domicilioDigitalePecSF");
				
		if(request.getParameter("nazioneResidenza")!=null && !request.getParameter("nazioneResidenza").equals(""))
			this.nazioneResidenza= (String) request.getParameter("nazioneResidenza");
		else if(request.getParameter("nazioneResidenzaId")!=null && !request.getParameter("nazioneResidenzaId").equals(""))
			this.nazioneResidenza= (String) request.getParameter("nazioneResidenzaId");
		this.indirizzo = new Indirizzo();
		

		if("106".equals(this.nazioneResidenza))
		{
			indirizzo.setNazione(nazioniList.getSelectedValue(Integer.parseInt(nazioneResidenza)));
			if (request.getParameter("addressLegaleLine1input") != null)
			{
				
				this.indirizzo.setVia(request.getParameter("addressLegaleLine1input"));

			}
			else
			{
				this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
			}

			if(request.getParameter("addressLegaleCity")!=null && request.getParameter("addressLegaleCity")!=null)
			{
				this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
				this.indirizzo.setDescrizioneComune(request.getParameter("addressLegaleCityinput"));
			}
			else if(request.getParameter("addressLegaleCitta")!=null && request.getParameter("addressLegaleCitta")!=null)
			{
				this.indirizzo.setComune(request.getParameter("addressLegaleCityId"));
				this.indirizzo.setDescrizioneComune(request.getParameter("addressLegaleCitta"));
			}
			else 
			{
				this.indirizzo.setComune(request.getParameter("addressLegaleCityId"));
				this.indirizzo.setDescrizioneComune(request.getParameter("addressLegaleCityinput"));
			}
			
			
			
			
			if(request.getParameter("addressLegaleCountry")!=null && !"".equals(request.getParameter("addressLegaleCountry")))
			{
				this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
				this.indirizzo.setIdProvincia(Integer.parseInt(request.getParameter("addressLegaleCountry")));
				this.indirizzo.setDescrizione_provincia(request.getParameter("addressLegaleCountryinput"));
			} 
			else if(request.getParameter("addressLegaleCountrySigla")!=null && !"".equals(request.getParameter("addressLegaleCountrySigla")))
			{
				Provincia p = new Provincia().getProvinciaBySigla(db, request.getParameter("addressLegaleCountrySigla"));
				this.indirizzo.setProvincia(p.getCodice()+"");
				this.indirizzo.setIdProvincia(p.getCodice());
				this.indirizzo.setDescrizione_provincia(p.getDescrizione());
			}

		}
		else
		{
			this.indirizzo.setNazione(nazioniList.getSelectedValue(Integer.parseInt(nazioneResidenza)));
			this.indirizzo.setComune(-1);
			this.indirizzo.setComuneTesto(request.getParameter("addressLegaleCityinput"));
			this.indirizzo.setVia(request.getParameter("addressLegaleLine1input"));
		}
		
		if(request.getParameter("toponimoResidenza")!=null)
			this.indirizzo.setToponimo(request.getParameter("toponimoResidenza"));
		else
			this.indirizzo.setToponimo(request.getParameter("toponimoResidenzaId"));

		this.indirizzo.setCivico(request.getParameter("civicoResidenza"));
		

		this.indirizzo.setCap(request.getParameter("capResidenza"));




	}

	public SoggettoFisico(HttpServletRequest request) {


		if(request.getParameter("idSoggettoFisico")!=null && !"".equals(request.getParameter("idSoggettoFisico")))
			this.idSoggetto=Integer.parseInt(request.getParameter("idSoggettoFisico"));
		
		this.nome = (String) request.getParameter("nome");
		this.cognome = (String) request.getParameter("cognome");
		this.sesso = (String) request.getParameter("sesso");
		this.setDataNascita((String) request.getParameter("dataNascita"));
		this.comuneNascita = (String) request.getParameter("comuneNascitainput");
		this.provinciaNascita = (String) request.getParameter("provinciaNascita");


		this.codFiscale = (String) request.getParameter("codFiscaleSoggetto");
		this.documentoIdentita = (String) request.getParameter("documentoIdentita");



		this.setFax(request.getParameter("fax"));
		this.setTelefono1(request.getParameter("telefono1"));
		this.setTelefono2(request.getParameter("telefono2"));
		this.setEmail(request.getParameter("email"));

		this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
		this.indirizzo = new Indirizzo();
		if (request.getParameter("addressLegaleLine1") != null)
		{
//			this.indirizzo.setIdIndirizzo(Integer.parseInt(request.getParameter("addressLegaleLine1")));
			this.indirizzo.setVia(request.getParameter("addressLegaleLine1input"));

		}
		else
		{
			this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
		}

		//this.indirizzo.setCap(request.getParameter("cap"));
		this.indirizzo.setComune(request.getParameter("addressLegaleCity"));
		this.indirizzo.setProvincia(request.getParameter("addressLegaleCountry"));
		if(this.indirizzo!=null && this.indirizzo.getProvincia()!=null && !"".equals(this.indirizzo.getProvincia()))
			this.indirizzo.setIdProvincia(Integer.parseInt(this.indirizzo.getProvincia()));
		this.indirizzo.setToponimo(request.getParameter("toponimoResidenza"));
		this.indirizzo.setCivico(request.getParameter("civicoResidenza"));


	}

	public void popolaBeanRappresentanteStabilimentoVoltura(HttpServletRequest request) {

		if (request.getParameter("tipoSoggettoFisicoStab") != null) {
			this.tipoSoggettoFisico = Integer.parseInt((String) request.getParameter("tipoSoggettoFisicoStab"));
		}

		this.nome = (String) request.getParameter("nomeStab");
		this.cognome = (String) request.getParameter("cognomeStab");
		this.codFiscale = (String) request.getParameter("codFiscaleSoggettoStab");
		this.documentoIdentita = (String) request.getParameter("documentoIdentitaStab");
		this.comuneNascita = (String) request.getParameter("comuneNascitaStab");
		this.provinciaNascita = (String) request.getParameter("provinciaNascitaStab");
		this.setDataNascita((String) request.getParameter("dataNascitaStab"));
		this.setFax(request.getParameter("faxStab"));
		this.setTelefono1(request.getParameter("telefono1Stab"));
		this.setTelefono2(request.getParameter("telefono2Stab"));
		this.setEmail(request.getParameter("emailStab"));
		this.sesso = (String) request.getParameter("sessoStab");
		this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
		this.indirizzo = new Indirizzo();
		if (request.getParameter("addressLegaleLine1Stab") != null)
			this.indirizzo.setIdIndirizzo(Integer.parseInt(request.getParameter("addressLegaleLine1Stab")));
		//this.indirizzo.setCap(request.getParameter("cap"));
		this.indirizzo.setComune(request.getParameter("addressLegaleCityStab"));
		this.indirizzo.setProvincia(request.getParameter("addressLegaleCountryStab"));
		this.indirizzo.setVia(request.getParameter("addressLegaleLine1TestoStab"));


	}

	public SoggettoFisico(HttpServletRequest request,Connection db,ActionContext context) throws SQLException {

		if (request.getParameter("tipoSoggettoFisico") != null) {
			this.tipoSoggettoFisico = Integer.parseInt(
					(String) request.getParameter("tipoSoggettoFisico"));
		}
		UserBean user = (UserBean)request.getSession().getAttribute("User");

		this.nome = (String) request.getParameter("nome");
		this.cognome = (String) request.getParameter("cognome");
		this.codFiscale = (String) request.getParameter("codFiscaleSoggettoTesto");
		this.comuneNascita = (String) request.getParameter("comuneNascitainput");
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
		this.indirizzo.setToponimo(request.getParameter("toponimoResidenza"));
		this.indirizzo.setVia(request.getParameter("addressLegaleLine1Testo"));
		this.sesso = (String) request.getParameter("sesso");
		this.provenienzaEstera = Boolean.valueOf(request.getParameter("estero"));
		this.documentoIdentita = (String) request.getParameter("documentoIdentita");
		this.insert(db,context);

	}

	public SoggettoFisico(Connection db, int idSoggetto) throws SQLException{



		if (idSoggetto>0)
		{

			StringBuffer sqlSelect = new StringBuffer("");
			sqlSelect.append(
					"SELECT distinct max(storico.id) as id_soggetto_storico ," +
							"o.nome,o.cognome," +
							"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono,comnasc.id as id_comune_nascita," +
							"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
							" o.id as id_soggetto ," +
							"i.id,i.comune,i.provincia,i.civico,i.cap,i.via,i.nazione,'R'||comuni1.codiceasl_bdn as codice_asl,i.latitudine,i.longitudine,i.toponimo,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
							"lp.description as descrizione_provincia   " +
							"FROM opu_soggetto_fisico o " +
							"left join opu_soggetto_fisico_storico storico on o.id = storico.id_opu_soggetto_fisico " +
							"left join opu_indirizzo i on o.indirizzo_id=i.id " +
							"left join comuni1 on (comuni1.id = i.comune) " +
							"left join comuni1 comnasc on comnasc.nome ilike o.comune_nascita "+
							"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
							"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
							"left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) " +
							"left join opu_operatore op on (os.id_operatore = op.id)  where o.id = ?" +
							"group by o.nome,o.cognome,comnasc.id," +
							"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
							"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
							" o.id  ,'R'||comuni1.codiceasl_bdn," +
							"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome ," +
					"lp.description,i.toponimo,i.civico " );

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
	}



	public void queryRecordStorico(Connection db, int idSoggettoStorico) throws SQLException{



		if (idSoggettoStorico>0)
		{

			StringBuffer sqlSelect = new StringBuffer("");
			sqlSelect.append(
					"SELECT o.id as id_soggetto_storico ," +
							"o.nome,o.cognome,comnasc.id as id_comune_nascita," +
							"o.codice_fiscale,o.comune_nascita,o.provincia_nascita,'R'||comuni1.codiceasl_bdn as codice_asl,o.data_nascita,o.sesso,o.telefono1,o.telefono," +
							"o.email,o.fax,o.enteredby,o.modifiedby,o.documento_identita,o.provenienza_estera," +
							" o.id as id_soggetto ," +
							"i.id,i.comune,i.provincia,i.cap,i.via,i.nazione,i.latitudine,i.longitudine,asl.code , asl.description ,comuni1.cod_provincia,comuni1.nome as descrizione_comune," +
							"lp.description as descrizione_provincia   " +
							"FROM opu_soggetto_fisico_storico o " +
							"left join opu_indirizzo i on o.indirizzo_id=i.id " +
							"left join comuni1 on (comuni1.id = i.comune) " +
							"left join comuni1 comnasc on comnasc.nome ilike o.comune_nascita "+
							"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
							"left join lookup_province lp on lp.code = comuni1.cod_provincia::int " +
							"left join opu_rel_operatore_soggetto_fisico os on (o.id = os.id_soggetto_fisico) " +
					"left join opu_operatore op on (os.id_operatore = op.id)  where o.id = ?"  );

			PreparedStatement pst = db.prepareStatement(sqlSelect.toString());
			pst.setInt(1, idSoggettoStorico);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRecord(rs);
			}





			rs.close();
			pst.close();
		}
	}




	public int getId_opu_soggetto_fisico_storico() {
		return id_opu_soggetto_fisico_storico;
	}


	public void setId_opu_soggetto_fisico_storico(int id_opu_soggetto_fisico_storico) {
		this.id_opu_soggetto_fisico_storico = id_opu_soggetto_fisico_storico;
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










	public void setProvenienzaEstera(boolean provenienzaEstera) {
		this.provenienzaEstera = provenienzaEstera;
	}



	public boolean isProvenienzaEstera() {
		return provenienzaEstera;
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

	public SoggettoFisico verificaSoggetto(Connection db ) throws SQLException
	{
		SoggettoFisico soggettoEsistente = new SoggettoFisico(this.getCodFiscale(),db);
		return soggettoEsistente ;

	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		this.idSoggetto = rs.getInt("id_soggetto");
		this.nome = rs.getString("nome");
		this.cognome = rs.getString("cognome");
		this.codFiscale = rs.getString("codice_fiscale");
		this.comuneNascita = rs.getString("comune_nascita");
		this.provinciaNascita = rs.getString("provincia_nascita");
		this.dataNascita = rs.getTimestamp("data_nascita");
		if (dataNascita!=null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataNascitaString =sdf.format(new Date(dataNascita.getTime()));
		}
		
		this.sesso = rs.getString("sesso");
		this.telefono1 = rs.getString("telefono");
		this.telefono2 = rs.getString("telefono1");
		this.email = rs.getString("email");
		this.fax = rs.getString("fax");
		this.enteredBy = rs.getInt("enteredby");
		this.modifiedBy = rs.getInt("modifiedby");
		this.documentoIdentita = rs.getString("documento_identita");
		this.provenienzaEstera= rs.getBoolean("provenienza_estera");
		this.indirizzo = new Indirizzo(rs);
		try
		{
		this.idComuneNascita = rs.getInt("id_comune_nascita");
		}
		catch(SQLException e)
		{
			
		}
		try
		{
		this.id_opu_soggetto_fisico_storico = rs.getInt("id_soggetto_storico");
		}
		catch(SQLException e)
		{
			
		}

	}


	protected void buildRecordStorico(ResultSet rs) throws SQLException {

		this.nome = rs.getString("nome");
		this.cognome = rs.getString("cognome");
		this.codFiscale = rs.getString("codice_fiscale");
		this.comuneNascita = rs.getString("comune_nascita");
		this.provinciaNascita = rs.getString("provincia_nascita");
		this.dataNascita = rs.getTimestamp("data_nascita");
		modified = rs.getTimestamp("modified");
		this.modifiedBy = rs.getInt("modifiedby");
		this.indirizzo = new Indirizzo(rs);

	}

	
	
	public SoggettoFisico comparaDatiSoggettoFisico(Connection db) throws SQLException
	{
		SoggettoFisico sfEsistente = this.verificaSoggetto(db);
		if(sfEsistente.getIdSoggetto()>0)
		{
			
			if(!sfEsistente.getNome().trim().equalsIgnoreCase(this.getNome().trim()))
			{
				sfEsistente.setEsiste(false);
				return sfEsistente;
			}
			if(!sfEsistente.getCognome().trim().equalsIgnoreCase(this.getCognome().trim()))
			{
				sfEsistente.setEsiste(false);
				return sfEsistente;
			}
			if(!sfEsistente.getComuneNascita().trim().equalsIgnoreCase(this.getComuneNascita().trim()))
			{
				sfEsistente.setEsiste(false);
				
				return sfEsistente;
			}
			if(sfEsistente.getDataNascita().compareTo(this.getDataNascita())!=0)
			{
				sfEsistente.setEsiste(false);
				return sfEsistente;
			}
			
			if(sfEsistente.getIndirizzo().getIdProvincia()!= this.getIndirizzo().getIdProvincia())
			{
				sfEsistente.setEsiste(false);
				return sfEsistente;
			}
			if(sfEsistente.getIndirizzo().getComune()!= this.getIndirizzo().getComune())
			{
				sfEsistente.setEsiste(false);
				
				return sfEsistente;
			}
			if(!sfEsistente.getIndirizzo().getVia().trim().equalsIgnoreCase(this.getIndirizzo().getVia().trim()))
			{
				sfEsistente.setEsiste(false);
				return sfEsistente;
			}
			if(!sfEsistente.getIndirizzo().getCivico().trim().equalsIgnoreCase(this.getIndirizzo().getCivico().trim()))
			{
				sfEsistente.setEsiste(false);
				return sfEsistente;
			}
			if(!sfEsistente.getIndirizzo().getToponimo().equalsIgnoreCase(this.getIndirizzo().getToponimo().trim()))
			{
				sfEsistente.setEsiste(false);
				
				return sfEsistente;
			}
			
		}
		
		sfEsistente.setEsiste(true);
		return sfEsistente;
		
	}




	public int insert(Connection db,ActionContext context) throws SQLException{
		StringBuffer sql = new StringBuffer();
		int idStorico = -1 ;
		try{

			if (this.indirizzo.getIdIndirizzo()<0)
				this.indirizzo.insert(db,context);
			//Controllare se c'e' gia' soggetto fisico, se no inserirlo
			idSoggetto = DatabaseUtils.getNextSeq(db, "opu_soggetto_fisico_id_seq");

			sql.append("INSERT INTO opu_soggetto_fisico (");

			if (idSoggetto > -1)
				sql.append("id,");

			sql.append("titolo, cognome, nome, data_nascita, comune_nascita, provincia_nascita, codice_fiscale,id_soggetto_precedente ");
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

			sql.append("VALUES (?,?,?,?,?,?,?,?");

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
			pst.setInt(++i, idSoggettoPrecedente);

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
			idStorico = this.insertStorico(db,context);


		}catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return idStorico;

	}


	public int insertStorico(Connection db,ActionContext context) throws SQLException{
		int id_storico = DatabaseUtils.getNextSeqInt(db, context,"opu_soggetto_fisico_storico","id");
		StringBuffer sql = new StringBuffer();
		try{

			sql.append("INSERT INTO opu_soggetto_fisico_storico (id,");
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

			sql.append("VALUES (?,?,?,?,?,?,?,?");

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
			pst.setInt(++i, id_storico);
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

		}catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return id_storico;

	}


	public boolean aggiungiRelazione(Connection db, int idOperatore, int tipoLegame,ActionContext context) throws SQLException{

		StringBuffer sql = new StringBuffer();
		try{

			//Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db,context, "rel_operatore_soggetto_fisico","id");

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



	public int update(Connection db,ActionContext context) throws SQLException {

		int resultCount = -1;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update opu_soggetto_fisico ");
			sql.append(" set codice_fiscale = ?, data_nascita = ?, fax = ?, sesso = ?, provenienza_estera = ?,nome = ?,  cognome = ?, documento_identita = ?, comune_nascita = ?, provincia_nascita = ?, telefono = ?, "
					+ "telefono1 = ?, email = ?, modifiedby = ?, ipmodifiedby = ? ");

			if (indirizzo.getIdIndirizzo()>-1)
				sql.append(", indirizzo_id = ?");
			else
			{
				indirizzo.insert(db,context);
				sql.append(", indirizzo_id = ? ");
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
			if (indirizzo.getIdIndirizzo()>-1)
				pst.setInt(++i, indirizzo.getIdIndirizzo());
			pst.setInt(++i, idSoggetto);
			resultCount = pst.executeUpdate();
			pst.close();

		}catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}
		return this.insertStorico(db,context);

	}   

	public int updateSoloIndirizzo(Connection db,ActionContext context) throws SQLException  {

		
		
		
		indirizzo.insert(db, context);
		StringBuffer sql = new StringBuffer();
		sql.append("update opu_soggetto_fisico set nome = ? , cognome = ? , comune_nascita=?,data_nascita=?");
		
		sql.append(" , modifiedby = ?, ipmodifiedby = ?, indirizzo_id = ? where id = ? ");
		boolean doCommit = true;
		int resultCount = -1;
		PreparedStatement pst;
		try {
			if (doCommit = db.getAutoCommit()) {
				db.setAutoCommit(false);
			}
			pst = db.prepareStatement(sql.toString());
			int i = 0;
			pst.setString(++i, nome);
			pst.setString(++i, cognome);
			pst.setString(++i, comuneNascita);
			pst.setTimestamp(++i, dataNascita);
			pst.setInt(++i, this.modifiedBy);
			pst.setString(++i, this.ipModifiedBy);
			pst.setInt(++i, this.indirizzo.getIdIndirizzo());
			pst.setInt(++i, this.idSoggetto);
			resultCount = pst.executeUpdate();
			pst.close();
		}catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			/*if (doCommit) {
							db.setAutoCommit(true);
						}*/
		}
		return resultCount;
	}
	
	public HashMap<String, Object> getHashmapSoggettoFisico() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
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
						
						map.put(nomeCampo,new String (""+metodi[j].invoke(this)+""));
						
						

					}
				}

			}

		}

		if(indirizzo!=null)
		{
			
			JSONObject residenza = new JSONObject(indirizzo.getHashmapIndirizzo());
				map.put("indirizzo",residenza );
		
		}



		return map ;

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
		String sql = "select codiceistatasl,description  from comuni1 join lookup_site_id asl on codiceistatasl::int = code where id = "+comune ;
		ResultSet rs = db.prepareStatement(sql).executeQuery();
		if (rs.next())
		{
			this.idAsl = rs.getInt(1);
			this.descrAsl = rs.getString(2);
		}
		return idAsl ;
	}
	
	
	
	public int compareTo(SoggettoFisico otherSoggetto) {
	       
		String nome = otherSoggetto.getNome();
		String cognome= otherSoggetto.getCognome();
		String codFiscale=otherSoggetto.getCodFiscale();
		String sesso = otherSoggetto.getSesso();
		String dataNascita = otherSoggetto.getDataNascitaString();
		
		String comuneNascita = otherSoggetto.getComuneNascita();
		
		Indirizzo residenzaSoggetto = otherSoggetto.getIndirizzo();
		
		
		if(
				this.getNome().equalsIgnoreCase(nome) &&
				this.getCognome().equalsIgnoreCase(cognome) &&
				this.getCodFiscale().equalsIgnoreCase(codFiscale) &&
				this.getSesso().equalsIgnoreCase(sesso) &&
				this.getDataNascitaString().equalsIgnoreCase(dataNascita) &&
				this.getComuneNascita().equalsIgnoreCase(comuneNascita) &&
				this.getIndirizzo().compareTo(residenzaSoggetto)==0
		 )
			return 0;
		return 1;

    }
	
	
			
			public int compareToDatiResidenza(SoggettoFisico otherSoggetto) {
	       
		String nome = otherSoggetto.getNome();
		String cognome= otherSoggetto.getCognome();
		String codFiscale=otherSoggetto.getCodFiscale();
		String sesso = otherSoggetto.getSesso();
		String dataNascita = otherSoggetto.getDataNascitaString();
		
		String comuneNascita = otherSoggetto.getComuneNascita();
		
		Indirizzo residenzaSoggetto = otherSoggetto.getIndirizzo();
		
		
		if(
				this.getIndirizzo().compareTo(residenzaSoggetto)==0
				
		 )
			return 0;
		return 1;

    }
	
	public int compareToDatiAnagrafici(SoggettoFisico otherSoggetto) {
	       
		String nome = otherSoggetto.getNome();
		String cognome= otherSoggetto.getCognome();
		String codFiscale=otherSoggetto.getCodFiscale();
		String sesso = otherSoggetto.getSesso();
		String dataNascita = otherSoggetto.getDataNascitaString();
		
		String comuneNascita = otherSoggetto.getComuneNascita();
		
		Indirizzo residenzaSoggetto = otherSoggetto.getIndirizzo();
		
		
		if(
				this.getNome().equalsIgnoreCase(nome) &&
				this.getCognome().equalsIgnoreCase(cognome) &&
				this.getCodFiscale().equalsIgnoreCase(codFiscale) &&
				this.getSesso().equalsIgnoreCase(sesso) &&
				this.getDataNascitaString().equalsIgnoreCase(dataNascita) &&
				this.getComuneNascita().equalsIgnoreCase(comuneNascita) 
				
		 )
			return 0;
		return 1;

    }
	
	
	
	
	
	
	
	
	
	public SoggettoFisico applicaErrataCorrige(Connection db,ActionContext context) throws SQLException
	{
		
		if(this.getIndirizzo().getIdIndirizzo()<=0)
			this.getIndirizzo().insert(db, context);
		int idSoggettoFisicoPrimaDellaModifica = this.getIdSoggetto();
		boolean soggettoFisicoEsistente = false;
		
		SoggettoFisicoList listaSoggetti =new SoggettoFisicoList();
		listaSoggetti.buildList(db, this.getCodFiscale());
		
		for(int i = 0 ; i < listaSoggetti.size();i++)
		{
			SoggettoFisico soggettoEsistente = (SoggettoFisico) listaSoggetti.get(i);
			
			if(this.compareToDatiAnagrafici(soggettoEsistente)!=0 && soggettoEsistente.getIdSoggetto()>0 )
			{
				soggettoEsistente.setComuneNascita(this.getComuneNascita());
				soggettoEsistente.setDataNascita(this.getDataNascita());
				soggettoEsistente.setNome(this.getNome());
				soggettoEsistente.setCognome(this.getCognome());
				soggettoEsistente.setSesso(this.getSesso());
				soggettoEsistente.aggiornaDatiAnagrafici(db);
			}
		
			if(this.compareToDatiResidenza(soggettoEsistente)==0)
			{
				soggettoFisicoEsistente=true ;
				this.setIdSoggettoPrecedente(idSoggettoFisicoPrimaDellaModifica);
				this.setIdSoggetto(soggettoEsistente.getIdSoggetto());
				
			}
			
			
		}
		
		if(soggettoFisicoEsistente==false)
		{
			/*duplico il soggetto con i dati modificati */
			this.setIdSoggetto(-1);
			this.setIdSoggettoPrecedente(idSoggettoFisicoPrimaDellaModifica);
			this.insert(db, context);
		}
		
		
		
		
		
		return this;
	}
	
	
	public void aggiornaDatiAnagrafici(Connection db) throws SQLException
	{
		
		String sql = "update opu_soggetto_fisico set comune_nascita=?,data_nascita=?,nome=?,cognome=?,sesso=? where id=?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, comuneNascita);
		pst.setTimestamp(2, dataNascita);
		pst.setString(3,nome);
		pst.setString(4, cognome);
		pst.setString(5, sesso);
		pst.setInt(6, idSoggetto);
		pst.execute();
		
	}

}
