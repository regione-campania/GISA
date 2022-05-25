/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class CanileInformazioni extends LineaProduttiva {

	private int idInformazioni = -1;
	private int nrControlliAperti = 0;
	private int nrControlliChiusi = 0;
	private java.sql.Timestamp dataProssimoControllo = null;
	private int categoriaRischio = 3;
	private boolean municipale = false;
	private boolean centroSterilizzazione = false;
	private boolean abusivo = false;
	private String autorizzazione;
	private Timestamp dataAutorizzazione;
	private Timestamp dataChiusura;
	private int modifiedBy;
	private boolean flagClinicaOspedale = false;
	private int mqDisponibili = 0;
	

	public void CanileInformazioni(){		


	}

	public void setAutorizzazione(String autorizzazione){
		this.autorizzazione=autorizzazione;

	}
	public String getAutorizzazione(){
		return autorizzazione;

	}
	public void setDataAutorizzazione(Timestamp dataAutorizzazione){
		this.dataAutorizzazione=dataAutorizzazione;

	}
	
	public void setDataAutorizzazione(String dataAutorizzazione){
		this.dataAutorizzazione=DateUtils.parseDateStringNew(dataAutorizzazione, "dd/MM/yyyy");

	}
	public Timestamp getDataAutorizzazione(){
		return dataAutorizzazione;
	}

	public int getIdInformazioni() {
		return idInformazioni;
	}

	public void setIdInformazioni(int idInformazioni) {
		this.idInformazioni = idInformazioni;
	}

	public int getNrControlliAperti() {
		return nrControlliAperti;
	}

	public void setNrControlliAperti(int nrControlliAperti) {
		this.nrControlliAperti = nrControlliAperti;
	}

	public int getNrControlliChiusi() {
		return nrControlliChiusi;
	}

	public void setNrControlliChiusi(int nrControlliChiusi) {
		this.nrControlliChiusi = nrControlliChiusi;
	}



	public Timestamp getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(Timestamp dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public java.sql.Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public void setDataProssimoControllo(java.sql.Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public boolean isMunicipale() {
		return municipale;
	}

	public void setMunicipale(boolean municipale) {
		this.municipale = municipale;
	}

	public void setMunicipale(String municipale) {
		this.municipale = DatabaseUtils
		.parseBoolean(municipale);
	}

	public boolean isCentroSterilizzazione() {
		return centroSterilizzazione;
	}

	public void setCentroSterilizzazione(boolean centroSterilizzazione) {
		this.centroSterilizzazione = centroSterilizzazione;
	}

	public void setCentroSterilizzazione(String centroSterilizzazione) {
		this.centroSterilizzazione = DatabaseUtils
		.parseBoolean(centroSterilizzazione);
	}
	
	

	public boolean isFlagClinicaOspedale() {
		return flagClinicaOspedale;
	}

	public void setFlagClinicaOspedale(boolean FlagClinicaOspedale) {
		this.flagClinicaOspedale = FlagClinicaOspedale;
	}

	public void setFlagClinicaOspedale(String flagClinicaOspedale) {
		this.flagClinicaOspedale = DatabaseUtils
		.parseBoolean(flagClinicaOspedale);
	}




	public boolean isAbusivo() {
		return abusivo;
	}

	public void setAbusivo(boolean abusivo) {
		this.abusivo = abusivo;
	}

	public void setAbusivo(String abusivo) {
		this.abusivo = DatabaseUtils
		.parseBoolean(abusivo);
	}
	
	
	



	public int getMqDisponibili() {
		return mqDisponibili;
	}

	public void setMqDisponibili(int mqDisponibili) {
		this.mqDisponibili = mqDisponibili;
	}
	
	public void setMqDisponibili(String mqDisponibili) {
		if (mqDisponibili != null)
			this.mqDisponibili = Integer.valueOf(mqDisponibili);
	}

	public static CanileInformazioni getInfoAddizionali(LineaProduttiva lp,
			Connection db) {
		CanileInformazioni info = new CanileInformazioni();
		Field[] campi = lp.getClass().getDeclaredFields();
		Method metodoGet = null;
		Method metodoSet = null;

		for (int i = 0; i < campi.length; i++) {
			int k = 0;

			String nameToUpperCase = (campi[i].getName().substring(0, 1)
					.toUpperCase() + campi[i].getName().substring(1,
							campi[i].getName().length()));

			// (nameToUpperCase.equals("IdTipoMantello")){
			try {

				metodoGet = lp.getClass().getMethod("get" + nameToUpperCase,
						null);
				metodoSet = info.getClass().getSuperclass().getMethod(
						"set" + nameToUpperCase, String.class);

				if (metodoGet != null && metodoSet != null) {
					if (("DataInizio").equals(nameToUpperCase)) {
						//		System.out.println(nameToUpperCase);
					}
					//if( ("DataFine").equals(nameToUpperCase)){ test
					metodoSet.invoke(info,
							(metodoGet.invoke(lp) != null) ? metodoGet.invoke(
									lp).toString() : null);
					//}
				}

			} catch (Exception e) {
				//System.out.println(metodoGet + "   " + metodoSet + "   "
				//	+ nameToUpperCase + " Non trovato ");
				// e.printStackTrace();
			}

		}

		info.getInformazioniControlli(db);
		info.getInformazioni(db);
		info.getInformazioniStatoControlli(db);

		return info;

	}

	public void getInformazioniControlli(Connection db) {

		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
			.prepareStatement("select cu_aperti, cu_chiusi " +
					"from ((select count(*) as cu_aperti from cu_canili where id_canile = ? and chiuso is null and trashed_date is null)) aperti," +
			"(select count(*) as cu_chiusi from cu_canili where id_canile = ? and chiuso is not null and trashed_date is null) chiusi");
			pst.setInt(1, this.getId());
			pst.setInt(2, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);

			if (rs.next()) {
				this.buildRecordControlli(rs);
			}

			rs.close();
			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildRecordControlli(ResultSet rs) throws SQLException {

		this.nrControlliAperti = rs.getInt( "cu_aperti" );
		this.nrControlliChiusi = rs.getInt("cu_chiusi");
	}

	public void buildRecordStatoControlli(ResultSet rs) throws SQLException {

		this.categoriaRischio = rs.getInt( "categoria_rischio" );
		this.dataProssimoControllo = rs.getTimestamp("data_prossimo_controllo");
	}

	public void getInformazioniStatoControlli(Connection db) {

		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
			.prepareStatement("Select * from opu_informazioni_controlli  where id_relazione_stabilimento_lp = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				this.buildRecordStatoControlli(rs);
			}

			rs.close();
			pst.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void getInformazioni(Connection db) {
		/**
		 * Potrebbe servire in futuro
		 */
		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
			.prepareStatement("Select * from opu_informazioni_canile  where id_relazione_stabilimento_linea_produttiva = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				this.buildRecord(rs);
			}

			rs.close();
			pst.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void buildRecord(ResultSet rs) throws SQLException{
		this.abusivo = rs.getBoolean("abusivo");
		this.municipale = rs.getBoolean("municipale");
		this.centroSterilizzazione = rs.getBoolean("centro_sterilizzazione");
		this.autorizzazione=rs.getString("autorizzazione");
		this.dataAutorizzazione=rs.getTimestamp("data_autorizzazione");
		this.dataChiusura=rs.getTimestamp("data_chiusura");
		this.flagClinicaOspedale=rs.getBoolean("flag_clinica_ospedale");
		this.mqDisponibili=rs.getInt("mq_disponibili");
		

	}

	public boolean insert(Connection db) throws SQLException {

		//System.out.println("insert informazioni");

		StringBuffer sql = new StringBuffer();
		try {
			

			super.insert(db);

			int idAnimale = super.getId();

			idInformazioni = DatabaseUtils.getNextSeqPostgres(db,
			"opu_informazioni_colonia_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
			.append("INSERT INTO opu_informazioni_canile(id_relazione_stabilimento_linea_produttiva, centro_sterilizzazione, municipale, abusivo, autorizzazione, data_autorizzazione, flag_clinica_ospedale, mq_disponibili ");



			sql.append(")");
			sql.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ");



			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, super.getId());

			pst.setBoolean(++i, this.centroSterilizzazione);
			pst.setBoolean(++i, this.municipale);
			pst.setBoolean(++i, this.abusivo);
			pst.setString(++i, this.autorizzazione);
			pst.setTimestamp(++i, this.dataAutorizzazione);
			pst.setBoolean(++i, this.flagClinicaOspedale);
			pst.setInt(++i, mqDisponibili);

			pst.execute();
			pst.close();

			this.idInformazioni = DatabaseUtils.getCurrVal(db,
					"opu_informazioni_canile_id_seq", idInformazioni);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}
	
	public int update (Connection db) throws SQLException 
	{
		//CERCO SE E' PRESENTE NEL DB
		StringBuffer sql_count = new StringBuffer();
		
		int count = 0;
		int i_count=0;
		sql_count.append("select id from opu_informazioni_canile where id_relazione_stabilimento_linea_produttiva= ?");
	
		PreparedStatement pst_count = db.prepareStatement(sql_count.toString());
		pst_count.setInt(++i_count, this.getId());
		ResultSet rs_count = pst_count.executeQuery();
		while(rs_count.next()){
	            count++;
	        }
		pst_count.close();
		if (count==0){ //SE NON E' PRESENTE, AGGIUNGO
			insert(db);
		}
		else
		{
			//ALTRIMENTI AGGIORNO
			StringBuffer sql = new StringBuffer();
			
			int i=0;
			int resultCount = -1;
			sql.append("Update opu_informazioni_canile set abusivo = ?, centro_sterilizzazione=?, municipale=?, autorizzazione=?, data_autorizzazione=?, modifiedby=?, "
					+ " flag_clinica_ospedale = ?, mq_disponibili = ?, modified=now() where id_relazione_stabilimento_linea_produttiva= ?");
	
			PreparedStatement pst;
			try {
				
				
				pst = db.prepareStatement(sql.toString());
			
			pst.setBoolean(++i, this.abusivo);
			pst.setBoolean(++i, this.centroSterilizzazione);
			pst.setBoolean(++i, this.municipale);
			pst.setString(++i, this.autorizzazione);
			pst.setTimestamp(++i, this.dataAutorizzazione);
			pst.setInt(++i, this.modifiedBy);
			pst.setBoolean(++i, this.flagClinicaOspedale);
			pst.setInt(++i, mqDisponibili);
			pst.setInt(++i, this.getId());
			resultCount = pst.executeUpdate();
			pst.close();
			
			} catch (SQLException e) {
				
				throw new SQLException(e.getMessage());
			} finally {
			
			}
			return resultCount ;
			}
		return 1;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = Integer.parseInt(modifiedBy);
	}

	public int getModifiedBy() {
		return modifiedBy;
	}
	
	public boolean salvaModificheCanile(Connection db, String autorizzazione, Timestamp dataAutorizzazione, boolean abusivo, boolean centroSterilizzazione,boolean municipale, CanileInformazioni newCanile, int idRelazione, int idUtente) throws SQLException{
		StringBuffer sql = new StringBuffer("");
	//Controllo cambiamenti dati operatore
	
		 if (autorizzazione==null && newCanile.getAutorizzazione()!=null && !newCanile.getAutorizzazione().equals("") ){
			 sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Canile autorizzazione'"+", ? , ?, "+idUtente+", now());");
		 }
			 else if (autorizzazione!=null && (!autorizzazione.equals(newCanile.getAutorizzazione()))) {
				 sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Canile autorizzazione'"+", ? , ?, "+idUtente+", now());"); 
			 }
	if (dataAutorizzazione!=null && !dataAutorizzazione.equals(newCanile.getDataAutorizzazione()))
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Canile data autorizzazione'"+", ?, ?, "+idUtente+", now());");
	if (abusivo!= newCanile.isAbusivo())
		sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Canile abusivo'"+", ?, ?, "+idUtente+", now());");
	if (centroSterilizzazione!= newCanile.isCentroSterilizzazione())
		sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Canile centro sterilizz.'"+", ?, ?, "+idUtente+", now());");
	if (municipale!= newCanile.isMunicipale())
		sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Canile municipale'"+", ?, ?, "+idUtente+", now());");

	try {
		
		PreparedStatement pst = db.prepareStatement(sql.toString());
		int i = 0;
		
		
		 if (autorizzazione==null && newCanile.getAutorizzazione()!=null && !newCanile.getAutorizzazione().equals("") ){
			 pst.setString(++i, "null");
				pst.setString(++i, newCanile.getAutorizzazione());}
		 else if (autorizzazione!=null && (!autorizzazione.equals(newCanile.getAutorizzazione()))) {
				pst.setString(++i, autorizzazione);
				pst.setString(++i, newCanile.getAutorizzazione()); }
		
		if (dataAutorizzazione!=null && !dataAutorizzazione.equals(newCanile.getDataAutorizzazione())){
			pst.setTimestamp(++i, dataAutorizzazione);
			pst.setTimestamp(++i, newCanile.getDataAutorizzazione());
		}	
		
		if (abusivo!= newCanile.isAbusivo()){
			pst.setBoolean(++i, abusivo);
			pst.setBoolean(++i, newCanile.isAbusivo());
			}

		if (centroSterilizzazione!= newCanile.isCentroSterilizzazione())
		{
			pst.setBoolean(++i, centroSterilizzazione);
			pst.setBoolean(++i, newCanile.isCentroSterilizzazione());
		}
		if (municipale!= newCanile.isMunicipale())
		{
			pst.setBoolean(++i, municipale);
			pst.setBoolean(++i, newCanile.isMunicipale());
		}
		if (pst!=null)
			pst.execute();
		pst.close();
		 
		 
	}catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		    } finally {
		    	/*if (doCommit) {
					db.setAutoCommit(true);
				}*/
		    //	GestoreConnessioni.freeConnection(db);
		    	//DbUtil.chiudiConnessioneJDBC(null, null, db);
		    }
  
	return true;}
	
}
