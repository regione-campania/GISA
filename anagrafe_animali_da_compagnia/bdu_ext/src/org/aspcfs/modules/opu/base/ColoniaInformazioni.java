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

public class ColoniaInformazioni extends LineaProduttiva {

	private int idInformazioni = -1;
	private java.sql.Timestamp dataRegistrazioneColonia;
	private java.sql.Timestamp dataCensimentoTotale;
	private String nrProtocollo = "";
	private int nrGattiTotale = 0;
	private boolean totalePresunto = false;
	private int nrGattiFTotale = 0;
	private boolean totaleFPresunto = false;
	private int nrGattiMTotale = 0;
	private boolean totaleMPresunto = false;
	private String nomeVeterinario = "";
	private int modifiedBy;
	
	//Parametri calcolati a partire dalla tabell animale
	private int totaleIdentificatiSterilizzati = 0; // # totale di gatti censiti
	private int nrGattiSterilizzati = 0;            // # totale di gatti censiti per i quali esiste una registrazione di sterilizzazione
	private int nrGattiDaSterilizzare = 0;			// # totale di gatti censiti per i quali non esiste una registrazione di sterilizzazione
	private int nrGattiDaCensire = 0;				// # totale gatti colonia - totaleIdentificatiSterilizzati
	
	
	public int getTotaleIdentificatiSterilizzati() {
		return totaleIdentificatiSterilizzati;
	}

	public void setTotaleIdentificatiSterilizzati(int totaleIdentificatiSterilizzati) {
		this.totaleIdentificatiSterilizzati = totaleIdentificatiSterilizzati;
	}

	//Controlli da gisa
	private int nrControlliAperti = 0;
	private int nrControlliChiusi = 0;
	private java.sql.Timestamp dataProssimoControllo = null;
	private int categoriaRischio = 3;

	
	
	
	
	
	
	
	public int getNrGattiDaCensire() {
		return nrGattiDaCensire;
	}

	public void setNrGattiDaCensire(int nrGattiDaCensire) {
		this.nrGattiDaCensire = nrGattiDaCensire;
	}

	public int getNrGattiDaSterilizzare() {
		return nrGattiDaSterilizzare;
	}

	public void setNrGattiDaSterilizzare(int nrGattiDaSterilizzare) {
		this.nrGattiDaSterilizzare = nrGattiDaSterilizzare;
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

	public java.sql.Timestamp getDataRegistrazioneColonia() {
		return dataRegistrazioneColonia;
	}

	public void setDataRegistrazioneColonia(
			java.sql.Timestamp dataRegistrazioneColonia) {
		this.dataRegistrazioneColonia = dataRegistrazioneColonia;
	}
	
	public void setDataRegistrazioneColonia(
			String dataRegistrazioneColonia) {
		this.dataRegistrazioneColonia = DateUtils.parseDateStringNew(
				dataRegistrazioneColonia, "dd/MM/yyyy");;
	}
	
	

	public int getIdInformazioni() {
		return idInformazioni;
	}

	public void setIdInformazioni(int idInformazioni) {
		this.idInformazioni = idInformazioni;
	}

	public java.sql.Timestamp getDataCensimentoTotale() {
		return dataCensimentoTotale;
	}

	public void setDataCensimentoTotale(java.sql.Timestamp data_censimento_totale) {
		this.dataCensimentoTotale = data_censimento_totale;
	}
	
	public void setDataCensimentoTotale(String data_censimento_totale) {
		this.dataCensimentoTotale = DateUtils.parseDateStringNew(
				data_censimento_totale, "dd/MM/yyyy");
	}


	public String getNrProtocollo() {
		return nrProtocollo;
	}

	public void setNrProtocollo(String nrProtocollo) {
		this.nrProtocollo = nrProtocollo;
	}

	public int getNrGattiTotale() {
		return nrGattiTotale;
	}

	public void setNrGattiTotale(int nrGattiTotale) {
		this.nrGattiTotale = nrGattiTotale;
	}

	public void setNrGattiTotale(String nrGattiTotale) {
		this.nrGattiTotale = new Integer(nrGattiTotale).intValue();
	}

	public boolean isTotalePresunto() {
		return totalePresunto;
	}

	public void setTotalePresunto(boolean totalePresunto) {
		this.totalePresunto = totalePresunto;
	}

	public void setTotalePresunto(String totalePresunto) {
		this.totalePresunto = DatabaseUtils.parseBoolean(totalePresunto);
	}

	public int getNrGattiFTotale() {
		return nrGattiFTotale;
	}

	public void setNrGattiFTotale(int nrGattiFTotale) {
		this.nrGattiFTotale = nrGattiFTotale;
	}

	public void setNrGattiFTotale(String nrGattiFTotale) {
		this.nrGattiFTotale = new Integer(nrGattiFTotale).intValue();
	}

	public boolean isTotaleFPresunto() {
		return totaleFPresunto;
	}

	public void setTotaleFPresunto(boolean totaleFPresunto) {
		this.totaleFPresunto = totaleFPresunto;
	}

	public void setTotaleFPresunto(String totaleFPresunto) {
		this.totaleFPresunto = DatabaseUtils.parseBoolean(totaleFPresunto);
	}

	public int getNrGattiMTotale() {
		return nrGattiMTotale;
	}

	public void setNrGattiMTotale(int nrGattiMTotale) {
		this.nrGattiMTotale = nrGattiMTotale;
	}

	public void setNrGattiMTotale(String nrGattiMTotale) {
		this.nrGattiMTotale = new Integer(nrGattiMTotale).intValue();
	}

	public boolean isTotaleMPresunto() {
		return totaleMPresunto;
	}

	public void setTotaleMPresunto(boolean totaleMPresunto) {
		this.totaleMPresunto = totaleMPresunto;
	}

	public void setTotaleMPresunto(String totaleMPresunto) {
		this.totaleMPresunto = DatabaseUtils.parseBoolean(totaleMPresunto);
	}

	public String getNomeVeterinario() {
		return nomeVeterinario;
	}

	public void setNomeVeterinario(String nomeVeterinario) {
		this.nomeVeterinario = nomeVeterinario;
	}
	
	

	public int getNrGattiSterilizzati() {
		return nrGattiSterilizzati;
	}

	public void setNrGattiSterilizzati(int nrGattiSterilizzati) {
		this.nrGattiSterilizzati = nrGattiSterilizzati;
	}

	public ColoniaInformazioni() {
		super();
		// TODO Auto-generated constructor stub
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
					.append("INSERT INTO opu_informazioni_colonia(id_relazione_stabilimento_linea_produttiva ");

			if (nrGattiTotale > 0) {
				sql.append(", nr_totale_gatti");
			}

			
				sql.append(", totale_presunto");
			

			if (dataRegistrazioneColonia != null) {
				sql.append(", data_registrazione_colonia");
			}
			
			if (dataCensimentoTotale != null) {
				sql.append(", data_censimento_totale");
			}

			if (nrProtocollo != null && !("").equals(nrProtocollo)) {
				sql.append(", nr_protocollo");
			}

			if (nrGattiMTotale > 0) {
				sql.append(", totale_maschi");
			}

			
				sql.append(", totale_maschi_flag_presunto");
			

			if (nrGattiFTotale > 0) {
				sql.append(", totale_femmine");
			}

			
				sql.append(", totale_femmine_flag_presunto");
			

			if (nomeVeterinario != null && !("").equals(nomeVeterinario)) {
				sql.append(", nominativo_veterinario");
			}
			
		//	sql.append(", totale_identificati_sterilizzati");

			sql.append(")");
			sql.append("VALUES ( ? ");

			if (nrGattiTotale > 0) {
				sql.append(", ?");
			}

			
				sql.append(", ?");
			

			if (dataRegistrazioneColonia != null) {
				sql.append(", ?");
			}
			
			if (dataCensimentoTotale != null) {
				sql.append(", ?");
			}

			if (nrProtocollo != null && !("").equals(nrProtocollo)) {
				sql.append(", ?");
			}

			if (nrGattiMTotale > 0) {
				sql.append(", ?");
			}

			
				sql.append(", ?");
			

			if (nrGattiFTotale > 0) {
				sql.append(", ?");
			}

			
				sql.append(", ?");
			

			if (nomeVeterinario != null && !("").equals(nomeVeterinario)) {
				sql.append(", ?");
			}

			//sql.append(", ?");
			
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, super.getId());

			if (nrGattiTotale > 0) {
				pst.setInt(++i, nrGattiTotale);
			}

			
				pst.setBoolean(++i, totalePresunto);
			

			if (dataRegistrazioneColonia != null) {
				pst.setTimestamp(++i, dataRegistrazioneColonia);
			}
			
			if (dataCensimentoTotale != null) {
				pst.setTimestamp(++i, dataCensimentoTotale);
			}

			if (nrProtocollo != null && !("").equals(nrProtocollo)) {
				pst.setString(++i, nrProtocollo);
			}

			if (nrGattiMTotale > 0) {
				pst.setInt(++i, nrGattiMTotale);
			}

			
				pst.setBoolean(++i, totaleMPresunto);
			

			if (nrGattiFTotale > 0) {
				pst.setInt(++i, nrGattiFTotale);
			}

			
				pst.setBoolean(++i, totaleFPresunto);
			

			if (nomeVeterinario != null && !("").equals(nomeVeterinario)) {
				pst.setString(++i, nomeVeterinario);
			}
			
			//pst.setInt(++i, totaleIdentificatiSterilizzati);

			pst.execute();
			pst.close();

			this.idInformazioni = DatabaseUtils.getCurrVal(db,
					"opu_informazioni_colonia_id_seq", idInformazioni);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		}
		return true;

	}

	public static ColoniaInformazioni getInfoAddizionali(LineaProduttiva lp,
			Connection db) {
		ColoniaInformazioni info = new ColoniaInformazioni();
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
						//System.out.println(nameToUpperCase);
					}
					metodoSet.invoke(info,
							(metodoGet.invoke(lp) != null) ? metodoGet.invoke(
									lp).toString() : null);
				}

			} catch (Exception e) {
				//System.out.println(metodoGet + "   " + metodoSet + "   "
				//		+ nameToUpperCase + " Non trovato ");
				// e.printStackTrace();
			}

		}

		
		info.getInformazioniControlli(db);
		info.getInformazioni(db);
		info.getInformazioniStatoControlli(db);

		return info;

	}

	public void getInformazioni(Connection db) {

		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
					.prepareStatement("Select * from opu_informazioni_colonia  where id_relazione_stabilimento_linea_produttiva = ?");
			pst.setInt(1, this.getId());
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				this.buildRecord(rs);
				this.nrGattiSterilizzati = this.checkNrGattiDaFlagSterilizzazione(db, true);
				this.nrGattiDaSterilizzare = this.checkNrGattiDaFlagSterilizzazione(db, false);
				this.totaleIdentificatiSterilizzati = nrGattiSterilizzati + nrGattiDaSterilizzare;
				this.nrGattiDaCensire = this.nrGattiTotale - this.totaleIdentificatiSterilizzati;
			}

			rs.close();
			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildRecord(ResultSet rs) throws SQLException {
 
		
		this.dataRegistrazioneColonia = rs
				.getTimestamp("data_registrazione_colonia");
		this.dataCensimentoTotale = rs.getTimestamp("data_censimento_totale");
		this.nrProtocollo = rs.getString("nr_protocollo");
		this.nrGattiTotale = rs.getInt("nr_totale_gatti");
		this.totalePresunto = rs.getBoolean("totale_presunto");
		this.nrGattiFTotale = rs.getInt("totale_femmine");
		this.totaleFPresunto = rs.getBoolean("totale_femmine_flag_presunto");
		this.nrGattiMTotale = rs.getInt("totale_maschi");
		this.totaleMPresunto = rs.getBoolean("totale_maschi_flag_presunto");
		this.nomeVeterinario = rs.getString("nominativo_veterinario");
		//this.totaleIdentificatiSterilizzati = rs.getInt("totale_identificati_sterilizzati");
	}
	
	
	
	
	public void getInformazioniControlli(Connection db) {

		// super(db, idEventoPadre);

		PreparedStatement pst;
		try {
			pst = db
			.prepareStatement("select cu_aperti, cu_chiusi " +
					"from ((select count(*) as cu_aperti from cu_colonie where id_colonia = ? and chiuso is null and trashed_date is null)) aperti," +
			"(select count(*) as cu_chiusi from cu_colonie where id_colonia = ? and chiuso is not null and trashed_date is null) chiusi");
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
	
	
	public int checkNrGattiDaFlagSterilizzazione(Connection db, boolean flagSterilizzazione){
		
		PreparedStatement pst;
		int toReturn = 0;
		try {
			pst = db
			.prepareStatement("Select count(*) as count from animale a  where a.id_specie = 2 and (a.id_proprietario = ? or a.id_detentore = ?) and flag_sterilizzazione = ? and a.data_cancellazione is null");
			int i = 0;
			pst.setInt(++i, this.getId());
			pst.setInt(++i, this.getId());
			pst.setBoolean(++i, flagSterilizzazione);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				toReturn = rs.getInt("count");
			}

			rs.close();
			pst.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toReturn;
		
	}
	
	
	public void aggiornaDimensioneColonia(Connection db){
		
		PreparedStatement pst;
		try {
			pst = db
			.prepareStatement("update opu_informazioni_colonia set nr_totale_gatti = ? where id_relazione_stabilimento_linea_produttiva = ? ");
			int i = 0;
			pst.setInt(++i, this.getNrGattiTotale());
			pst.setInt(++i, this.getId());
			
			pst.executeUpdate();
		

			
			pst.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void update (Connection db) throws SQLException{
		PreparedStatement pst;
		try {
			
			pst = db
			.prepareStatement("update opu_informazioni_colonia set nr_protocollo = ?, data_registrazione_colonia= ?, modified=now(), modifiedby=? where id_relazione_stabilimento_linea_produttiva = ? ");
			int i = 0;
			pst.setString(++i, this.getNrProtocollo());
			pst.setTimestamp(++i, this.getDataRegistrazioneColonia());
			pst.setInt(++i, this.modifiedBy);
			pst.setInt(++i, this.getId());
			pst.executeUpdate();
			pst.close();

			
			} catch (SQLException e) {
				
				throw new SQLException(e.getMessage());
			} finally {
			}
		
		
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
	
	public boolean salvaModificheColonia(Connection db, String protocollo, Timestamp dataRegistrazione, ColoniaInformazioni newColonia, int idRelazione, int idUtente) throws SQLException{
		StringBuffer sql = new StringBuffer("");
	//Controllo cambiamenti dati operatore
	
		 if (protocollo==null && newColonia.getNrProtocollo()!=null && !newColonia.getNrProtocollo().equals("") )
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Colonia nr protocollo'"+", ?, ?, "+idUtente+", now());");
			if (protocollo!=null && (!protocollo.equals(newColonia.getNrProtocollo())))
				sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("+idRelazione+","+"'Colonia data registrazione'"+", ?, ?, "+idUtente+", now());");
		 
	
	try {
		
		PreparedStatement pst = db.prepareStatement(sql.toString());
		int i = 0;
		
		 if (protocollo==null && newColonia.getNrProtocollo()!=null && !newColonia.getNrProtocollo().equals("") ){
			 pst.setString(++i, "null");
				pst.setString(++i, newColonia.getNrProtocollo());
		 }
				
		 else if (protocollo!=null && (!protocollo.equals(newColonia.getNrProtocollo()))) {
			pst.setString(++i, protocollo);
			pst.setString(++i, newColonia.getNrProtocollo());
		}
		if (dataRegistrazione!=null && !dataRegistrazione.equals(newColonia.getDataRegistrazioneColonia())){
			pst.setTimestamp(++i, dataRegistrazione);
			pst.setTimestamp(++i, newColonia.getDataRegistrazioneColonia());
		}	
		
		if (pst!=null)
			pst.execute();
		pst.close();
		 
		 
	}catch (SQLException e) {
		 
			throw new SQLException(e.getMessage());
		    } finally {
		    //	GestoreConnessioni.freeConnection(db);
		    	//DbUtil.chiudiConnessioneJDBC(null, null, db);
		    }
  
	return true;}

}
