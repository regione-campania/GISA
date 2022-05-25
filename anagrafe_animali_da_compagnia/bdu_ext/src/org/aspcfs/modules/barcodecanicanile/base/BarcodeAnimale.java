/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.barcodecanicanile.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.CampoModificato;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.beans.GenericBean;

public class BarcodeAnimale extends GenericBean {

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.anagrafe_animali.base.Animale.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}

	private int idAnimale = -1;
	private String proprietario = null;
	private String detentore = null;
	private String mantello = null;
	private String microchip = null;
	private String tatuaggio = null;
	private String specie = null;
	private String sesso = null;
	private Timestamp dataPrelievoLeish = null;
	private Timestamp dataEsitoLeish = null;
	private String esitoLeish = null;

	
	public BarcodeAnimale(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
	}

	public BarcodeAnimale() {
		// TODO Auto-generated constructor stub
	}


	private void buildRecord(ResultSet rs) throws SQLException {

		this.idAnimale = rs.getInt("id");
		this.specie = rs.getString("specie");
		this.proprietario = rs.getString("proprietario");
		this.detentore = rs.getString("detentore");
		this.dataPrelievoLeish = rs.getTimestamp("data_prelievo_leish");
		this.dataEsitoLeish = rs.getTimestamp("data_esito_leish");
		this.esitoLeish = rs.getString("esito_leish");
		this.microchip = rs.getString("microchip");
		this.tatuaggio = rs.getString("tatuaggio");
		this.mantello = (rs.getString("mantello"));
		this.sesso = (rs.getString("sesso"));
		}

	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

	public String getDetentore() {
		return detentore;
	}

	public void setDetentore(String detentore) {
		this.detentore = detentore;
	}

	public String getMantello() {
		return mantello;
	}

	public void setMantello(String mantello) {
		this.mantello = mantello;
	}

	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}

	public String getTatuaggio() {
		return tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public Timestamp getDataPrelievoLeish() {
		return dataPrelievoLeish;
	}

	public void setDataPrelievoLeish(Timestamp dataPrelievoLeish) {
		this.dataPrelievoLeish = dataPrelievoLeish;
	}

	public Timestamp getDataEsitoLeish() {
		return dataEsitoLeish;
	}

	public void setDataEsitoLeish(Timestamp dataEsitoLeish) {
		this.dataEsitoLeish = dataEsitoLeish;
	}

	public String getEsitoLeish() {
		return esitoLeish;
	}

	public void setEsitoLeish(String esitoLeish) {
		this.esitoLeish = esitoLeish;
	}

	
}
