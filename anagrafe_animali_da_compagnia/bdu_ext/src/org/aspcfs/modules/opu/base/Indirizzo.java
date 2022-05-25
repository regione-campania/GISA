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
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class Indirizzo extends GenericBean {

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.opu.base.Indirizzo.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}

	private int idIndirizzo = -1;
	private String cap;
	private int comune = -1;
	private String descrizioneComune;
	private String provincia;
	private String via;
	private String nazione;
	private double latitudine;
	private double longitudine;
	private int enteredBy = -1;
	private int modifiedBy = -1;
	private String ipEnteredBy;
	private String ipModifiedBy;
	private int idProvincia = -1;
	private int tipologiaSede = 1; // DEFAULT
	private String descrizione_provincia;
	private int idAsl = -1;
	private String descrizioneAsl = "";
	private int idRegione;
	private String nomeRegione;

	public Indirizzo() {

	}

	public int calcolaAsl(Connection db) throws SQLException {
		int idAsl = -1;
		String sql = "select codiceistatasl from comuni1 where trashed is false and id = " + comune;
		ResultSet rs = db.prepareStatement(sql).executeQuery();
		if (rs.next())
			idAsl = rs.getInt(1);
		return idAsl;
	}

	public void inserComune(Connection db) {

	}

	public Indirizzo(HttpServletRequest request, Connection db)
			throws SQLException {

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		if ((String) request.getParameter("searchcodeIdprovincia") != null) 
		{
			if (new Integer((String) request.getParameter("searchcodeIdprovincia")) > -1)
				this.setProvincia(request.getParameter("searchcodeIdprovincia"));
			else
				this.setProvincia(request.getParameter("searchcodeIdprovinciaTesto"));
		} 
		else if ((String) request.getParameter("searchcodeIdprovinciaSedeLegale") != null) 
		{
			this.setProvincia(request.getParameter("searchcodeIdprovinciaTesto"));
		}
		else if ((String) request.getParameter("searchcodeIdprovinciaAsl") != null) 
		{
			this.setProvincia((String) request.getParameter("searchcodeIdprovinciaAsl"));
		}
		if(request.getParameter("searchcodeIdComune")!=null)
			this.setComune(request.getParameter("searchcodeIdComune"));
		else
			this.setComune(request.getParameter("searchcodeIdComuneSedeLegale"));
		this.setVia(request.getParameter("viaTesto"));
		this.setLatitudine(request.getParameter("latitudine"));
		this.setLongitudine(request.getParameter("longitudine"));
		if(request.getParameter("cap")!=null)
			this.setCap(request.getParameter("cap"));
		else if(request.getParameter("presso")!=null)
			this.setCap(request.getParameter("presso"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);

		this.insert(db);

	}
	
	
	public Indirizzo(HttpServletRequest request, Connection db, String tipo)
			throws SQLException {

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		if ((String) request.getParameter("searchcodeIdprovincia") != null && !tipo.equals("sedeLegale")) 
		{
			if (new Integer((String) request.getParameter("searchcodeIdprovincia")) > -1)
				this.setProvincia(request.getParameter("searchcodeIdprovincia"));
			else
				this.setProvincia(request.getParameter("searchcodeIdprovinciaTesto"));
		} 
		else if ((String) request.getParameter("searchcodeIdprovinciaSedeLegale") != null && tipo.equals("sedeLegale")) 
		{
			this.setProvincia(request.getParameter("searchcodeIdprovinciaTesto"));
		}
		else if ((String) request.getParameter("searchcodeIdprovinciaAsl") != null) 
		{
			this.setProvincia((String) request.getParameter("searchcodeIdprovinciaAsl"));
		}
		if(request.getParameter("searchcodeIdComune")!=null && !tipo.equals("sedeLegale"))
			this.setComune(request.getParameter("searchcodeIdComune"));
		else 
			this.setComune(request.getParameter("searchcodeIdComuneSedeLegale"));
		if(request.getParameter("viaTesto")!=null && !tipo.equals("sedeLegale"))
			this.setVia(request.getParameter("viaTesto"));
		else 
			this.setVia(request.getParameter("viaTestoSedeLegale"));
		this.setLatitudine(request.getParameter("latitudine"));
		this.setLongitudine(request.getParameter("longitudine"));
		if(request.getParameter("cap")!=null && !tipo.equals("sedeLegale"))
			this.setCap(request.getParameter("cap"));
		else if(request.getParameter("presso")!=null)
			this.setCap(request.getParameter("presso"));
		this.setEnteredBy(user.getUserId());
		this.setModifiedBy(user.getUserId());
		String ip = user.getUserRecord().getIp();
		this.setIpEnteredBy(ip);
		this.setIpModifiedBy(ip);

		this.insert(db);

	}

	public Indirizzo(HttpServletRequest request, Connection db, int variante)
			throws SQLException {
		// 1: SEDE OPERATIVA
		// 2: RESPONSABILE STABILIMENTO

		UserBean user = (UserBean) request.getSession().getAttribute("User");

		if (variante == 1) {

			if ((String) request.getParameter("idProvinciaSedeOperativa") != null) {
				if (new Integer(
						(String) request
								.getParameter("idProvinciaSedeOperativa")) > -1)
					this.setProvincia(request
							.getParameter("idProvinciaSedeOperativa"));
				else
					this.setProvincia(request
							.getParameter("provinciaSedeOperativa"));
			} else if ((String) request
					.getParameter("searchcodeIdprovinciaAsl") != null) {
				this.setProvincia((String) request
						.getParameter("searchcodeIdprovinciaAsl"));
			}
			this.setComune(request.getParameter("idComuneSedeOperativa"));
			this.setVia(request.getParameter("viaSedeOperativa"));
			this.setLatitudine(request.getParameter("latitudine"));
			this.setLongitudine(request.getParameter("longitudine"));
			this.setCap(request.getParameter("cap"));
			this.setEnteredBy(user.getUserId());
			this.setModifiedBy(user.getUserId());
			String ip = user.getUserRecord().getIp();
			this.setIpEnteredBy(ip);
			this.setIpModifiedBy(ip);

			this.insert(db);
		} else if (variante == 2) {

			if ((String) request.getParameter("idProvinciaResponsabile") != null) {
				if (new Integer(
						(String) request
								.getParameter("idProvinciaResponsabile")) > -1)
					this.setProvincia(request
							.getParameter("idProvinciaResponsabile"));
				else
					this.setProvincia(request
							.getParameter("provinciaResponsabile"));
			} else if ((String) request
					.getParameter("searchcodeIdprovinciaAsl") != null) {
				this.setProvincia((String) request
						.getParameter("searchcodeIdprovinciaAsl"));
			}
			this.setComune(request.getParameter("idComuneResponsabile"));
			this.setVia(request.getParameter("viaResponsabile"));
			this.setLatitudine(request.getParameter("latitudine"));
			this.setLongitudine(request.getParameter("longitudine"));
			this.setCap(request.getParameter("cap"));
			this.setEnteredBy(user.getUserId());
			this.setModifiedBy(user.getUserId());
			String ip = user.getUserRecord().getIp();
			this.setIpEnteredBy(ip);
			this.setIpModifiedBy(ip);

			this.insert(db);
		} else if (variante == 4) {

			if ((String) request.getParameter("idProvinciaRappresentante") != null) {
				if (new Integer(
						(String) request
								.getParameter("idProvinciaRappresentante")) > -1)
					this.setProvincia(request
							.getParameter("idProvinciaRappresentante"));
				else
					this.setProvincia(request
							.getParameter("provinciaResponsabile"));
			} else if ((String) request
					.getParameter("searchcodeIdprovinciaAsl") != null) {
				this.setProvincia((String) request
						.getParameter("searchcodeIdprovinciaAsl"));
			}
			this.setComune(request.getParameter("idComuneRappresentante"));
			this.setVia(request.getParameter("viaRappresentante"));
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
		return (descrizione_provincia != null) ? descrizione_provincia.trim()
				: "";
	}

	public void setDescrizione_provincia(String descrizione_provincia) {
		this.descrizione_provincia = descrizione_provincia;
	}

	public String getDescrizioneComune() {
		return (descrizioneComune != null) ? descrizioneComune.trim() : "";
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

	public void setIdIndirizzo(String idIndirizzo) {

		try {
			this.idIndirizzo = Integer.parseInt(idIndirizzo);
		} catch (Exception e) {
			this.idIndirizzo = -1;
		}

	}

	public String getCap() {
		return (cap != null) ? cap.trim() : "";
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

	public void setComune(int idComune) {
		this.comune = idComune;
	}

	public String getProvincia() {
		return (provincia != null) ? provincia.trim() : "";
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getVia() {
		return (via != null) ? via.trim() : "";
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

	public boolean insert(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();
		try {
			if(cap==null || cap.equals("") || cap.equalsIgnoreCase("null"))
				cap = ComuniAnagrafica.getCap(db, this.comune);
			// Controllare se c'è già soggetto fisico, se no inserirlo
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
			if (System.getProperty("DEBUG") != null)
				System.out.println("Query di insert indirizzo eseguita: "
						+ pst.toString());
			// JOptionPane.showMessageDialog(null,pst.toString()+"\nINSERT INTO opu_indirizzo SET via="+this.via+" COMUNE= "+this.comune+" PROVINCIA= "+this.provincia+" CAP= "+this.cap+" ID="+idIndirizzo+"\n Stringhe: Provincia: "+this.descrizione_provincia+" Comune: "+this.descrizioneComune);

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		} finally {

		}

		return true;

	}

	public Indirizzo(Connection db, int idIndirizzo) throws SQLException {
		/*
		 * if (idIndirizzo == -1){ throw new SQLException("Invalid Indirizzo");
		 * }
		 */

		PreparedStatement pst = db
				.prepareStatement("Select i.*, i.id as id_indirizzo, asl.code , asl.description ,comuni1.codiceistatasl, comuni1.cod_provincia,comuni1.nome as descrizione_comune,lp.description as descrizione_provincia, reg.code as idregione, reg.description as regione from opu_indirizzo i "
						+ "left join comuni1 on (comuni1.id = i.comune) "
						+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
						+ "left join lookup_province lp on lp.code = comuni1.cod_provincia::int left join lookup_regione reg on reg.code = lp.id_regione "
						+ " where i.id = ? and (trashed is false or trashed is null) ");

		pst.setInt(1, idIndirizzo);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			this.idAsl = rs.getInt("code");
			this.descrizioneAsl = rs.getString("description");

		} else {
			/*
			 * COMMENTATO DA G.BALZANO IN QUANTO MOLTI OPERATORI NON HANNO
			 * ASSOCIATO UN INDIRIZZO E MANDA SEMPRE IN ECCEZIONE
			 */
			// throw new IndirizzoNotFoundException("Indirizzo non Trovato");
		}
		/*
		 * COMMENTATO DA G.BALZANO IN QUANTO MOLTI OPERATORI NON HANNO ASSOCIATO
		 * UN INDIRIZZO E MANDA SEMPRE IN ECCEZIONE
		 */
		/*
		 * if (idIndirizzo == -1) { throw new
		 * SQLException(Constants.NOT_FOUND_ERROR); }
		 */

		rs.close();
		pst.close();
	}

	public Indirizzo(ResultSet rs) throws SQLException {

		buildRecord(rs);

	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		this.descrizione_provincia = rs.getString("descrizione_provincia");

		try {

			this.idIndirizzo = rs.getInt("id_indirizzo");

		} catch (org.postgresql.util.PSQLException e) {
			this.idIndirizzo = rs.getInt("id");
		}
		
		try {

			this.idAsl = rs.getInt("codiceistatasl");

		} catch (Exception e) {
			//System.out.println("Non è stato possibile recuperare l'asl dell'indirizzo: campo codiceistatasl non previsto in questa query. Messaggio: " + e.getMessage());
		}
		
		
		

		this.comune = rs.getInt("comune");
		if (rs.getString("cod_provincia") != null)
			this.idProvincia = Integer.parseInt(rs.getString("cod_provincia"));
		this.provincia = rs.getString("provincia");
		this.cap = rs.getString("cap");
		this.descrizioneComune = rs.getString("descrizione_comune");
		this.via = rs.getString("via");
		this.nazione = rs.getString("nazione");
		this.latitudine = rs.getDouble("latitudine");
		this.longitudine = rs.getDouble("longitudine");

		try {

			this.tipologiaSede = rs.getInt("tipologia_sede");

		} catch (org.postgresql.util.PSQLException e) {
			// System.out.println("not found");
		}

		try {
			this.idRegione = rs.getInt("idregione");
		} catch (org.postgresql.util.PSQLException e) {
		}
		try {
			this.nomeRegione = rs.getString("regione");
		} catch (org.postgresql.util.PSQLException e) {
		}

	}

	public HashMap<String, Object> getHashmap()
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] campi = this.getClass().getDeclaredFields();
		Method[] metodi = this.getClass().getMethods();
		for (int i = 0; i < campi.length; i++) {
			String nomeCampo = campi[i].getName();

			for (int j = 0; j < metodi.length; j++) {
				if (metodi[j].getName().equalsIgnoreCase("GET" + nomeCampo)) {
					if (nomeCampo.equalsIgnoreCase("via")) {
						// map.put("descrizione", metodi[j].invoke(this));
						map.put("descrizionevia", metodi[j].invoke(this));
						map.put("value", metodi[j].invoke(this));
						map.put("label", metodi[j].invoke(this));
					} else {
						if (nomeCampo.equalsIgnoreCase("idIndirizzo")) {
							// map.put("codice", metodi[j].invoke(this));
							map.put("codicevia", metodi[j].invoke(this));
							map.put("idindirizzo", metodi[j].invoke(this));
						} else {
							map.put(nomeCampo,
									("" + metodi[j].invoke(this)).trim());
						}
					}
				}

			}

		}

		return map;

	}

	public String toString() {
		String descrizione = "";

		if (via != null)
			descrizione = via;
		if (cap != null)
			descrizione += ", " + cap;
		if (descrizioneComune != null)
			descrizione += " " + descrizioneComune;
		if (descrizione_provincia != null)
			descrizione += " , " + descrizione_provincia;

		return descrizione;
	}

	// IN CASO DI MODIFICA INDIRIZZO, CREA NUOVA ENTRY PER TENERE TRACCIA DEL
	// CAMBIO
	public boolean insertModificaIndirizzo(Connection db, int operatoreId,
			int oldIndirizzoId, int newIndirizzoId, int userId, int tipoSede)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance()
				.getTimeInMillis());
		java.sql.Timestamp data_date = new Timestamp(timeNow.getTime());
		try {

			
			sql.append("INSERT INTO opu_indirizzo_history (id_operatore, id_vecchio_indirizzo, id_nuovo_indirizzo, utente_modifica, data_modifica, tipo_sede");
			sql.append(")");

			sql.append("VALUES (?,?,?, ?, ?, ?");
			sql.append(")");

			int i = 0;

			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, operatoreId);
			pst.setInt(++i, oldIndirizzoId);
			pst.setInt(++i, newIndirizzoId);
			pst.setInt(++i, userId);
			pst.setTimestamp(++i, data_date);
			pst.setInt(++i, tipoSede); // 1: LEGALE 2: OPERATIVA 3: RESPONSABILE

			pst.execute();
			pst.close();

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			/*
			 */
		}

		return true;

	}

	public void setIdRegione(int idRegione) {
		this.idRegione = idRegione;
	}

	public void setIdRegione(String idRegione) {
		this.idRegione = Integer.parseInt(idRegione);
	}

	public int getIdRegione() {
		return idRegione;
	}

	public void setNomeRegione(String nomeRegione) {
		this.nomeRegione = nomeRegione;
	}

	public String getNomeRegione() {
		return nomeRegione;
	}

	/*
	 * public int update(int idIndirizzo, Connection db) throws SQLException,
	 * IndirizzoNotFoundException { this.idIndirizzo=idIndirizzo;
	 * System.out.println("INIZIO UPDATE INDIRIZZO"); int resultCount = 0;
	 * 
	 * System.out.println("Dati indirizzo: ");
	 * System.out.println("Comune residenza: "+descrizioneComune);
	 * System.out.println("Cap residenza: "+cap);
	 * System.out.println("Provincia residenza: "+provincia);
	 * System.out.println("Via: "+via);
	 * 
	 * PreparedStatement pst = null; StringBuffer sql = new StringBuffer();
	 * 
	 * // Operatore old = new Operatore( db, idOperatore);
	 * 
	 * //Operatore old = new Operatore(); //old.queryRecordOperatore(db,
	 * idIndirizzo);
	 * sql.append("UPDATE opu_indirizzo SET via= ?, cap= ?, provincia = ? ");
	 * 
	 * sql.append(" where id = ?");
	 * 
	 * int i = 0; pst = db.prepareStatement(sql.toString()); pst.setString(++i,
	 * via); pst.setString(++i, cap); pst.setString(++i, provincia);
	 * pst.setInt(++i, idIndirizzo); //resultCount = pst.executeUpdate();
	 * pst.close();
	 * System.out.println("Query indirizzo (non eseguita): "+pst.toString());
	 * 
	 * return resultCount; }
	 */

}
