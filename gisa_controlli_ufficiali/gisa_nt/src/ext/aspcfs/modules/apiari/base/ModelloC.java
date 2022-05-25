/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class ModelloC  extends GenericBean{
	
	/**
	 * CAMPI DI SERVIZIO
	 */

	private Timestamp entered;
	private int entered_by ;
	private Timestamp modified;
	private int modified_by ;
	private Timestamp trashed_date;
	private String note ;
	private boolean sincronizzato_bdn;
	private boolean attestazioneSanitaria;
	private Timestamp data_sincronizzazione ;
	private int  sincronizzato_Da ;
	private int id_asl_apicoltore_origine ;
	private int stato ;
	private int accettazioneDestinatario ;
	private int utenteAccettazioneDestinatario ;
	private Timestamp dataAccettazioneDestinatario ;
	private int idBdn ;
	private String errore_sincronizzazione;
	private boolean accettazioneSincronizzatoBdn;
	private int accettazioneSincronizzataDa;
	private Timestamp accettazioneDataSincronizzazione;
	
	/**
	 * CAMPI DEL MODELLO
	 */
	private int id ;
	private String numero_modello;
	private Timestamp data_modello;
	private Timestamp dataIngresso;
	private Timestamp dataMovimentazione;
	private int idTipoMovimentazione ;
	
	/**
	 * CAMPI APIARIO ORGINE
	 */
	private String codiceAziendaOrigine ;
	private String progressivoApiarioOrigine ;
	private int idStabilimentoOrigine ;
	private int idBdaApiarioOrigine ;
	private int numApiariOrigine ;

	
	
	/**
	 * CAMPI APIRIO DESTINAZIONE
	 */
	private String codiceAziendaDestinazione ;
	private String progressivoApiarioDestinazione ;
	private int idStabilimentoDestinazione ;
	private int idBdaApiarioDestinazione ;
	private int numApiariDestinazione ;
	private String comune_dest;
	private String proprietarioDestinazione;
	private String cfProprietarioDestinazione;
	private String sigla_prov_comune_dest;
	private String indirizzo_dest;
	private String cfPartitaIvaApicoltore;
	private String denominazioneApicoltore;
	private Boolean recuperoMaterialeBiologico;
	private double latitudine_dest ;
	private double longitudine_dest ;

	
	/**
	 * NUMERO ALVEARI SPOSTATI
	 */
	private int numApiariSpostati ;
	
	/**
	 * CAMPI RIFERIMENTO A OGGETTI
	 */
	private Stabilimento apiarioOrigine ;
	private Stabilimento apiarioDestinazione ;
	
	
	/**
	 * NUMERO ALVEARI DA SPOSTARE
	 */
	private int numAlveariDaSpostare ;
	private int numSciamiDaSpostare ;
	private int numPacchiDaSpostare ;
	private int numRegineDaSpostare ;
	

	public String getNumero_modello() {
		return numero_modello;
	}

	public void setNumero_modello(String numero_modello) {
		this.numero_modello = numero_modello;
	}

	public Timestamp getData_modello() {
		return data_modello;
	}

	public void setData_modello(Timestamp data_modello) {
		this.data_modello = data_modello;
	}
	
	
	
	public void setData_modello(String data_modello) throws ParseException {
		if (data_modello!= null  && !"".equals(data_modello))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.data_modello = new Timestamp(sdf.parse(data_modello).getTime());
		}
	}
	
	public Timestamp getDataIngresso() {
		return dataIngresso;
	}

	public void setDataIngresso(Timestamp dataIngresso) {
		this.dataIngresso = dataIngresso;
	}
	
	
	
	public void setDataIngresso(String dataIngresso) throws ParseException {
		if (dataIngresso!= null  && !"".equals(dataIngresso))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataIngresso = new Timestamp(sdf.parse(dataIngresso).getTime());
		}
	}

	public String getComune_dest() {
		return comune_dest;
	}

	public void setComune_dest(String comune_dest) {
		this.comune_dest = comune_dest;
	}
	
	public String getProprietarioDestinazione() {
		return proprietarioDestinazione;
	}

	public void setProprietarioDestinazione(String proprietarioDestinazione) {
		this.proprietarioDestinazione = proprietarioDestinazione;
	}
	
	public String getCfProprietarioDestinazione() {
		return cfProprietarioDestinazione;
	}

	public void setCfProprietarioDestinazione(String cfProprietarioDestinazione) {
		this.cfProprietarioDestinazione = cfProprietarioDestinazione;
	}

	public String getSigla_prov_comune_dest() {
		return sigla_prov_comune_dest;
	}

	public void setSigla_prov_comune_dest(String sigla_prov_comune_dest) {
		this.sigla_prov_comune_dest = sigla_prov_comune_dest;
	}

	public String getIndirizzo_dest() {
		return indirizzo_dest;
	}

	public void setIndirizzo_dest(String indirizzo_dest) {
		this.indirizzo_dest = indirizzo_dest;
	}
	
	public void setCfPartitaIvaApicoltore(String cfPartitaIvaApicoltore) 
	{
		this.cfPartitaIvaApicoltore = cfPartitaIvaApicoltore;
	}
	
	public String getCfPartitaApicoltore() 
	{
		return cfPartitaIvaApicoltore;
	}
	
	public void setRecuperoMaterialeBiologico(Boolean recuperoMaterialeBiologico) 
	{
		this.recuperoMaterialeBiologico = recuperoMaterialeBiologico;
	}
	
	public Boolean getRecuperoMaterialeBiologico() 
	{
		return recuperoMaterialeBiologico;
	}
	
	public void setDenominazioneApicoltore(String denominazioneApicoltore) 
	{
		this.denominazioneApicoltore = denominazioneApicoltore;
	}
	
	public String getDenominazioneApicoltore() 
	{
		return denominazioneApicoltore;
	}
	
	public double getLatitudine_dest() {
		return latitudine_dest;
	}

	public void setLatitudine_dest(double latitudine_dest) {
		this.latitudine_dest = latitudine_dest;
	}
	public void setLatitudine_dest(String latitudine_dest) {
		if(latitudine_dest!=null &&!"".equals(latitudine_dest))
			this.latitudine_dest = Double.parseDouble(latitudine_dest);
		}

	
	
	public double getLongitudine_dest() {
		return longitudine_dest;
	}

	public void setLongitudine_dest(double longitudine_dest) {
		this.longitudine_dest = longitudine_dest;
	}
	
	public void setLongitudine_dest(String longitudine_dest) {
		if(longitudine_dest!=null &&!"".equals(longitudine_dest))
		this.longitudine_dest = Double.parseDouble(longitudine_dest);
	}

	public ModelloC(){}
	
	public int getId_asl_apicoltore_origine() {
		return id_asl_apicoltore_origine;
	}



	public void setId_asl_apicoltore_origine(int id_asl_apicoltore_origine) {
		this.id_asl_apicoltore_origine = id_asl_apicoltore_origine;
	}



	public int getStato() {
		return stato;
	}



	public void setStato(int stato) {
		this.stato = stato;
	}
	
	public int getAccettazioneDestinatario() {
		return accettazioneDestinatario;
	}



	public void setAccettazioneDestinatario(int accettazioneDestinatario) {
		this.accettazioneDestinatario = accettazioneDestinatario;
	}



	public boolean isSincronizzato_bdn() {
		return sincronizzato_bdn;
	}



	public void setAccettazioneSincronizzatoBdn(boolean accettazioneSincronizzatoBdn) {
		this.accettazioneSincronizzatoBdn = accettazioneSincronizzatoBdn;
	}
	
	public boolean isAccettazioneSincronizzatoBdn() {
		return accettazioneSincronizzatoBdn;
	}



	public void setSincronizzato_bdn(boolean sincronizzato_bdn) {
		this.sincronizzato_bdn = sincronizzato_bdn;
	}
	
	
	
	public boolean isAttestazioneSanitaria() {
		return attestazioneSanitaria;
	}



	public void setAttestazioneSanitaria(boolean attestazioneSanitaria) {
		this.attestazioneSanitaria = attestazioneSanitaria;
	}



	public Timestamp getData_sincronizzazione() {
		return data_sincronizzazione;
	}



	public void setData_sincronizzazione(Timestamp data_sincronizzazione) {
		this.data_sincronizzazione = data_sincronizzazione;
	}
	
	public Timestamp getDataAccettazioneDestinatario() {
		return dataAccettazioneDestinatario;
	}



	public void setDataAccettazioneDestinatario(Timestamp dataAccettazioneDestinatario) {
		this.dataAccettazioneDestinatario = dataAccettazioneDestinatario;
	}
	
	public Timestamp getAccettazioneDataSincronizzazione() {
		return accettazioneDataSincronizzazione;
	}



	public void setAccettazioneDataSincronizzazione(Timestamp accettazioneDataSincronizzazione) {
		this.accettazioneDataSincronizzazione = accettazioneDataSincronizzazione;
	}
	
	
	
	public String getErrore_sincronizzazione() {
		return errore_sincronizzazione;
	}



	public void setErrore_sincronizzazione(String errore_sincronizzazione) {
		this.errore_sincronizzazione = errore_sincronizzazione;
	}
	
	
	



	public int getSincronizzato_Da() {
		return sincronizzato_Da;
	}



	public void setSincronizzato_Da(int sincronizzato_Da) {
		this.sincronizzato_Da = sincronizzato_Da;
	}



	public Stabilimento getApiarioOrigine() {
		return apiarioOrigine;
	}



	public void setApiarioOrigine(Stabilimento apiarioOrigine) {
		this.apiarioOrigine = apiarioOrigine;
	}



	public Stabilimento getApiarioDestinazione() {
		return apiarioDestinazione;
	}



	public void setApiarioDestinazione(Stabilimento apiarioDestinazione) {
		this.apiarioDestinazione = apiarioDestinazione;
	}



	public int getNumApiariOrigine() {
		return numApiariOrigine;
	}



	public void setNumApiariOrigine(int numApiariOrigine) {
		this.numApiariOrigine = numApiariOrigine;
	}
	
	public void setNumApiariOrigine(String numApiariOrigine) {
		if(numApiariOrigine!=null && !"".equals(numApiariOrigine))
		
		this.numApiariOrigine = Integer.parseInt(numApiariOrigine);
	}



	public int getNumApiariDestinazione() {
		return numApiariDestinazione;
	}



	public void setNumApiariDestinazione(int numApiariDestinazione) {
		this.numApiariDestinazione = numApiariDestinazione;
	}
	
	public void setNumApiariDestinazione(String numApiariDestinazione) {
		if(numApiariDestinazione!=null && !"".equals(numApiariDestinazione))
			
			this.numApiariDestinazione = Integer.parseInt(numApiariDestinazione);
		}

	



	public int getNumApiariSpostati() {
		return numApiariSpostati;
	}

	public void setNumAlveariDaSpostare(int numAlveariDaSpostare) {
		this.numAlveariDaSpostare = numAlveariDaSpostare;
	}
	public void setNumAlveariDaSpostare(String numAlveariDaSpostare) {
		if(numAlveariDaSpostare!=null && !"".equals(numAlveariDaSpostare))
			
			this.numAlveariDaSpostare = Integer.parseInt(numAlveariDaSpostare);	
		
	}
	
	public int getNumAlveariDaSpostare() {
		return numAlveariDaSpostare;
	}
	
	public void setNumSciamiDaSpostare(int numSciamiDaSpostare) {
		this.numSciamiDaSpostare = numSciamiDaSpostare;
	}
	public void setNumSciamiDaSpostare(String numSciamiDaSpostare) {
		if(numSciamiDaSpostare!=null && !"".equals(numSciamiDaSpostare))
			
			this.numSciamiDaSpostare = Integer.parseInt(numSciamiDaSpostare);	
		
	}
	
	public int getNumSciamiDaSpostare() {
		return numSciamiDaSpostare;
	}
	
	public void setNumPacchiDaSpostare(int numPacchiDaSpostare) {
		this.numPacchiDaSpostare = numPacchiDaSpostare;
	}
	public void setNumPacchiDaSpostare(String numPacchiDaSpostare) {
		if(numPacchiDaSpostare!=null && !"".equals(numPacchiDaSpostare))
			
			this.numPacchiDaSpostare = Integer.parseInt(numPacchiDaSpostare);	
		
	}
	
	public int getNumPacchiDaSpostare() {
		return numPacchiDaSpostare;
	}
	
	public void setNumRegineDaSpostare(int numRegineDaSpostare) {
		this.numRegineDaSpostare = numRegineDaSpostare;
	}
	public void setNumRegineDaSpostare(String numRegineDaSpostare) {
		if(numRegineDaSpostare!=null && !"".equals(numRegineDaSpostare))
			
			this.numRegineDaSpostare = Integer.parseInt(numRegineDaSpostare);	
		
	}
	
	public int getNumRegineDaSpostare() {
		return numRegineDaSpostare;
	}



	public void setNumApiariSpostati(int numApiariSpostati) {
		this.numApiariSpostati = numApiariSpostati;
	}
	public void setNumApiariSpostati(String numApiariSpostati) {
		if(numApiariSpostati!=null && !"".equals(numApiariSpostati))
			
			this.numApiariSpostati = Integer.parseInt(numApiariSpostati);	
		
	}

	
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Timestamp getDataMovimentazione() {
		return dataMovimentazione;
	}



	public void setDataMovimentazione(Timestamp dataMovimentazione) {
		this.dataMovimentazione = dataMovimentazione;
	}

	public void setDataMovimentazione(String dataMovimentazione) throws ParseException {
		if (dataMovimentazione!= null  && !"".equals(dataMovimentazione))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataMovimentazione = new Timestamp(sdf.parse(dataMovimentazione).getTime());
		}
	}

	public Timestamp getModified() {
		return modified;
	}



	public void setModified(Timestamp modified) {
		this.modified = modified;
	}



	public int getModified_by() {
		return modified_by;
	}



	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	
	public Timestamp getEntered() {
		return entered;
	}



	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}



	public int getEntered_by() {
		return entered_by;
	}



	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}
	
	public int getAccettazioneSincronizzataDa() {
		return accettazioneSincronizzataDa;
	}



	public void setAccettazioneSincronizzataDa(int accettazioneSincronizzataDa) {
		this.accettazioneSincronizzataDa = accettazioneSincronizzataDa;
	}
	
	
	public int getUtenteAccettazioneDestinatario() {
		return utenteAccettazioneDestinatario;
	}



	public void setUtenteAccettazioneDestinatario(int utenteAccettazioneDestinatario) {
		this.utenteAccettazioneDestinatario = utenteAccettazioneDestinatario;
	}
	
	



	public Timestamp getTrashed_date() {
		return trashed_date;
	}



	public void setTrashed_date(Timestamp trashed_date) {
		this.trashed_date = trashed_date;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public int getIdTipoMovimentazione() {
		return idTipoMovimentazione;
	}



	public void setIdTipoMovimentazione(int idTipoMovimentazione) {
		this.idTipoMovimentazione = idTipoMovimentazione;
	}


	public void setIdTipoMovimentazione(String idTipoMovimentazione) {
		if (idTipoMovimentazione!=null && !"".equals(idTipoMovimentazione))
			this.idTipoMovimentazione = Integer.parseInt(idTipoMovimentazione);
	}


	


	public String getCodiceAziendaOrigine() {
		return codiceAziendaOrigine;
	}



	public void setCodiceAziendaOrigine(String codiceAziendaOrigine) {
		this.codiceAziendaOrigine = codiceAziendaOrigine;
	}



	public String getProgressivoApiarioOrigine() {
		return progressivoApiarioOrigine;
	}



	public void setProgressivoApiarioOrigine(String progressivoApiarioOrigine) {
		this.progressivoApiarioOrigine = progressivoApiarioOrigine;
	}


	public int getIdBdn() {
		return idBdn;
	}



	public void setIdBdn(int idBdn) {
		this.idBdn = idBdn;
	}

	public int getIdStabilimentoOrigine() {
		return idStabilimentoOrigine;
	}



	public void setIdStabilimentoOrigine(int idStabilimentoOrigine) {
		this.idStabilimentoOrigine = idStabilimentoOrigine;
	}



	public int getIdBdaApiarioOrigine() {
		return idBdaApiarioOrigine;
	}



	public void setIdBdaApiarioOrigine(int idBdaApiarioOrigine) {
		this.idBdaApiarioOrigine = idBdaApiarioOrigine;
	}

	
	public void setIdBdaApiarioOrigine(String idBdaApiarioOrigine) {
		if(idBdaApiarioOrigine!=null && !"".equals("idBdaApiarioOrigine"))
			this.idBdaApiarioOrigine = Integer.parseInt(idBdaApiarioOrigine);
	}


	public String getCodiceAziendaDestinazione() {
		return codiceAziendaDestinazione;
	}



	public void setCodiceAziendaDestinazione(String codiceAziendaDestinazione) {
		this.codiceAziendaDestinazione = codiceAziendaDestinazione;
	}



	public String getProgressivoApiarioDestinazione() {
		return progressivoApiarioDestinazione;
	}



	public void setProgressivoApiarioDestinazione(String progressivoApiarioDestinazione) {
		this.progressivoApiarioDestinazione = progressivoApiarioDestinazione;
	}



	public int getIdStabilimentoDestinazione() {
		return idStabilimentoDestinazione;
	}



	public void setIdStabilimentoDestinazione(int idStabilimentoDestinazione) {
		this.idStabilimentoDestinazione = idStabilimentoDestinazione;
	}



	public int getIdBdaApiarioDestinazione() {
		return idBdaApiarioDestinazione;
	}



	public void setIdBdaApiarioDestinazione(int idBdaApiarioDestinazione) {
		this.idBdaApiarioDestinazione = idBdaApiarioDestinazione;
	}
	
	public void setIdBdaApiarioDestinazione(String idBdaApiarioDestinazione) {
		if (idBdaApiarioDestinazione!=null && !"".equals(idBdaApiarioDestinazione))
			this.idBdaApiarioDestinazione = Integer.parseInt(idBdaApiarioDestinazione);
	}

	public ModelloC(Connection db , int id)
	{
		queryRecord(db, id);
	}


	public void insert (Connection db) throws Exception
	{
		PreparedStatement pst = null ;
		
		
		String insert = "INSERT INTO apicoltura_movimentazioni (id_asl_apicoltore_origine,sincronizzato_bdn,accettazione_sincronizzato_bdn,stato,id,data_movimentazione,entered,entered_by,accettazione_sincronizzata_da, note,id_tipo_movimentazione,codice_azienda_origine,progressivo_apiario_origine,"
				+ "id_stabilimento_apiario_origine,id_bda_apiario_origine,codice_azienda_destinazione,progressivo_apiario_destinazione,id_stabilimento_apiario_destinazione,id_bda_apiario_destinazione,num_apiari_origine,num_apiari_destinazione,num_apiari_spostati,data_modello,data_ingresso,numero_modello,comune_dest,proprietario_destinazione, cf_proprietario_destinazione,sigla_prov_comune_dest,indirizzo_dest,latitudine_dest,longitudine_dest"
				+ " , num_alveari_da_spostare,  num_sciami_da_spostare, num_pacchi_da_spostare ,  num_regine_da_spostare, denominazione_apicoltore, cf_partita_iva_apicoltore, recupero_materiale_biologico,accettazione_destinatario,data_accettazione_destinatario , accettazione_data_sincronizzazione, utente_accettazione_destinatario, attestazione_sanitaria  ) "
				+ "values (?,?,?,?,?,?,current_timestamp,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;

		id = DatabaseUtils.getNextInt(db, "apicoltura_movimentazioni", "id", 1);
		int i=0 ;
		pst  = db.prepareStatement(insert);
		pst.setInt(++i, id_asl_apicoltore_origine);

		pst.setBoolean(++i, sincronizzato_bdn);
		pst.setBoolean(++i, accettazioneSincronizzatoBdn);
		pst.setInt(++i, stato);

		
		pst.setInt(++i, id);
		pst.setTimestamp(++i, dataMovimentazione);
		pst.setInt(++i, entered_by);
		pst.setInt(++i, accettazioneSincronizzataDa);
		pst.setString(++i, note);
		pst.setInt(++i, idTipoMovimentazione);
		
		pst.setString(++i, codiceAziendaOrigine);
		pst.setString(++i, progressivoApiarioOrigine);
		pst.setInt(++i, idStabilimentoOrigine);
		pst.setInt(++i, idBdaApiarioOrigine);

		pst.setString(++i, codiceAziendaDestinazione);
		pst.setString(++i, progressivoApiarioDestinazione);
		pst.setInt(++i, idStabilimentoDestinazione);
		pst.setInt(++i, idBdaApiarioDestinazione);
		
		
		pst.setInt(++i, numApiariOrigine);
		pst.setInt(++i, numApiariDestinazione);
		pst.setInt(++i, numApiariSpostati);
		
		pst.setTimestamp(++i, data_modello);
		pst.setTimestamp(++i, dataIngresso);
		pst.setString(++i, numero_modello);
		pst.setString(++i,comune_dest);
		pst.setString(++i,proprietarioDestinazione);
		pst.setString(++i,cfProprietarioDestinazione);
		pst.setString(++i, sigla_prov_comune_dest);
		pst.setString(++i, indirizzo_dest);
		pst.setDouble(++i, latitudine_dest);
		pst.setDouble(++i, longitudine_dest);
		pst.setString(++i, numAlveariDaSpostare+"");
		pst.setString(++i, numSciamiDaSpostare+"");
		pst.setString(++i, numPacchiDaSpostare+"");
		pst.setString(++i, numRegineDaSpostare+"");
		pst.setString(++i, denominazioneApicoltore+"");
		pst.setString(++i, cfPartitaIvaApicoltore+"");
		pst.setObject(++i, recuperoMaterialeBiologico);
		pst.setInt(++i, accettazioneDestinatario);
		pst.setTimestamp(++i, dataAccettazioneDestinatario);
		pst.setTimestamp(++i, accettazioneDataSincronizzazione);
		pst.setInt(++i, utenteAccettazioneDestinatario);
		pst.setBoolean(++i, attestazioneSanitaria);
		
		pst.execute();
	}
	
	public void storico(Connection db)
	{
		PreparedStatement pst = null ;
		
		
		String insert = " INSERT INTO apicoltura_movimentazioni_storico select ?, * from apicoltura_movimentazioni where id = ? " ;
		try
		{
			Integer idStorico = DatabaseUtils.getNextSeq(db, "apicoltura_movimentazioni_id_storico_seq");
			int i=0 ;
			pst  = db.prepareStatement(insert);
			pst.setInt(++i, idStorico);
			pst.setInt(++i, id);
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void update (Connection db)
	{
		PreparedStatement pst = null ;
		
		
		String update = "update apicoltura_movimentazioni  set id_bdn = ?, sincronizzato_bdn = ? , accettazione_sincronizzato_bdn = ?, stato = ?,data_movimentazione = ?, modified = now(),modified_by = ? ,"
				+ " note = ? ,codice_azienda_destinazione = ?,progressivo_apiario_destinazione=?,id_stabilimento_apiario_destinazione=?,"
				+ " id_bda_apiario_destinazione = ?,num_apiari_origine=?,num_apiari_destinazione=?,num_apiari_spostati=?,data_modello=?,data_ingresso=?,"
				+ " numero_modello=?,comune_dest=?,proprietario_destinazione=?,cf_proprietario_destinazione=?,sigla_prov_comune_dest=?,indirizzo_dest=?,latitudine_dest=?,longitudine_dest=? , "
				+ " num_alveari_da_spostare = ?,  num_sciami_da_spostare = ?, num_pacchi_da_spostare = ?,  num_regine_da_spostare = ?, denominazione_apicoltore = ?,  cf_partita_iva_apicoltore = ?,  recupero_materiale_biologico = ?, data_sincronizzazione = ?, sincronizzato_da = ?, accettazione_sincronizzata_da = ?, accettazione_destinatario = ? , data_accettazione_destinatario = ?  , accettazione_data_sincronizzazione = ?  , utente_accettazione_destinatario = ?  , attestazione_sanitaria = ?"
				+ " where id = ? " ;
		try
		{
			int i=0 ;
			pst  = db.prepareStatement(update);

			pst.setInt(++i, idBdn);
			pst.setBoolean(++i, sincronizzato_bdn);
			pst.setBoolean(++i, accettazioneSincronizzatoBdn);
			pst.setInt(++i, stato);
			pst.setTimestamp(++i, dataMovimentazione);
			pst.setInt(++i, modified_by);
			pst.setString(++i, note);
			pst.setString(++i, codiceAziendaDestinazione);
			pst.setString(++i, progressivoApiarioDestinazione);
			pst.setInt(++i, idStabilimentoDestinazione);
			pst.setInt(++i, idBdaApiarioDestinazione);
			pst.setInt(++i, numApiariOrigine);
			pst.setInt(++i, numApiariDestinazione);
			pst.setInt(++i, numApiariSpostati);
			pst.setTimestamp(++i, data_modello);
			pst.setTimestamp(++i, dataIngresso);
			pst.setString(++i, numero_modello);
			pst.setString(++i,comune_dest);
			pst.setString(++i,proprietarioDestinazione);
			pst.setString(++i,cfProprietarioDestinazione);
			pst.setString(++i, sigla_prov_comune_dest);
			pst.setString(++i, indirizzo_dest);
			pst.setDouble(++i, latitudine_dest);
			pst.setDouble(++i, longitudine_dest);
			pst.setString(++i, numAlveariDaSpostare+"");
			pst.setString(++i, numSciamiDaSpostare+"");
			pst.setString(++i, numPacchiDaSpostare+"");
			pst.setString(++i, numRegineDaSpostare+"");
			pst.setObject(++i, denominazioneApicoltore+"");
			pst.setObject(++i, cfPartitaIvaApicoltore+"");
			pst.setObject(++i, recuperoMaterialeBiologico);
			pst.setTimestamp(++i, data_sincronizzazione);
			pst.setInt(++i, sincronizzato_Da);
			pst.setInt(++i, accettazioneSincronizzataDa);
			pst.setInt(++i, accettazioneDestinatario);
			pst.setTimestamp(++i, dataAccettazioneDestinatario);
			pst.setTimestamp(++i, accettazioneDataSincronizzazione);
			pst.setInt(++i, utenteAccettazioneDestinatario);
			pst.setBoolean(++i, attestazioneSanitaria);
			pst.setInt(++i, id);
			
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void accettazione (Connection db)
	{
		PreparedStatement pst = null ;
		
		
		String update = "update apicoltura_movimentazioni  set modified = current_timestamp, modified_by = ?, accettazione_destinatario = ? , data_accettazione_destinatario = ?  , utente_accettazione_destinatario = ?, progressivo_apiario_destinazione = ? "
				+ " where id = ? " ;
		try
		{
			int i=0 ;
			pst  = db.prepareStatement(update);

			pst.setInt(++i, modified_by);
			pst.setInt(++i, accettazioneDestinatario);
			pst.setTimestamp(++i, dataAccettazioneDestinatario);
			pst.setInt(++i, utenteAccettazioneDestinatario);
			pst.setString(++i, progressivoApiarioDestinazione);
			pst.setInt(++i, id);
			
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void sincronizzaAccettazioneBdn (Connection db)
	{
		PreparedStatement pst = null ;
		
		
		String update = "update apicoltura_movimentazioni  set modified = current_timestamp, modified_by = ?, accettazione_sincronizzato_bdn = ? , accettazione_sincronizzata_da = ?  , accettazione_data_sincronizzazione = ? "
				+ " where id = ? " ;
		try
		{
			int i=0 ;
			pst  = db.prepareStatement(update);

			pst.setInt(++i, modified_by);
			pst.setBoolean(++i, accettazioneSincronizzatoBdn);
			pst.setInt(++i, accettazioneSincronizzataDa);
			pst.setTimestamp(++i, accettazioneDataSincronizzazione);
			pst.setInt(++i, id);
			
			pst.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void queryRecord (Connection db,int id)
	{
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		String select = "SELECT * FROM  apicoltura_movimentazioni WHERE id = ? " ;
		try
		{
			pst  = db.prepareStatement(select);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next())
				buildRecord(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void buildRecord(ResultSet rs) throws SQLException
	{
		
		this.setId(rs.getInt("id"));
		this.setDataMovimentazione(rs.getTimestamp("data_movimentazione"));
		this.setEntered(rs.getTimestamp("entered"));
		this.setNote(rs.getString("note"));
		this.setEntered_by(rs.getInt("entered_by"));
		this.setAccettazioneSincronizzataDa(rs.getInt("accettazione_sincronizzata_da"));
		this.setIdTipoMovimentazione(rs.getInt("id_tipo_movimentazione"));
		
		this.setCodiceAziendaOrigine(rs.getString("codice_azienda_origine"));
		this.setProgressivoApiarioOrigine(rs.getString("progressivo_apiario_origine"));
		this.setIdStabilimentoOrigine(rs.getInt("id_stabilimento_apiario_origine"));
		this.setIdBdn(rs.getInt("id_bdn"));
		this.setIdBdaApiarioOrigine(rs.getInt("id_bda_apiario_origine"));
		this.setId_asl_apicoltore_origine(rs.getInt("id_asl_apicoltore_origine"));
		
		this.setCodiceAziendaDestinazione(rs.getString("codice_azienda_destinazione"));
		this.setProgressivoApiarioDestinazione(rs.getString("progressivo_apiario_destinazione"));
		this.setIdStabilimentoDestinazione(rs.getInt("id_stabilimento_apiario_destinazione"));
		this.setIdBdaApiarioDestinazione(rs.getInt("id_bda_apiario_destinazione"));
		this.setNumApiariDestinazione(rs.getInt("num_apiari_destinazione"));
		this.setNumApiariOrigine(rs.getInt("num_apiari_origine"));
		this.setNumApiariSpostati(rs.getInt("num_apiari_spostati"));
		this.setStato(rs.getInt("stato"));
		this.setSincronizzato_bdn(rs.getBoolean("sincronizzato_bdn"));
		this.setAccettazioneSincronizzatoBdn(rs.getBoolean("accettazione_sincronizzato_bdn"));
		
		this.setData_modello(rs.getTimestamp("data_modello"));
		this.setDataIngresso(rs.getTimestamp("data_ingresso"));
		this.setNumero_modello(rs.getString("numero_modello"));
		this.setComune_dest(rs.getString("comune_dest"));
		this.setProprietarioDestinazione(rs.getString("proprietario_destinazione"));
		this.setCfProprietarioDestinazione(rs.getString("cf_proprietario_destinazione"));
		this.setSigla_prov_comune_dest(rs.getString("sigla_prov_comune_dest"));
		this.setIndirizzo_dest(rs.getString("indirizzo_dest"));
		this.setDenominazioneApicoltore(rs.getString("denominazione_apicoltore"));
		this.setCfPartitaIvaApicoltore(rs.getString("cf_partita_iva_apicoltore"));
		this.setRecuperoMaterialeBiologico(rs.getBoolean("recupero_materiale_biologico"));
		this.setLatitudine_dest(rs.getDouble("latitudine_dest"));
		this.setLongitudine_dest(rs.getDouble("longitudine_dest"));

		this.setNumAlveariDaSpostare(rs.getInt("num_alveari_da_spostare"));
		this.setNumRegineDaSpostare(rs.getInt("num_regine_da_spostare"));
		this.setNumSciamiDaSpostare(rs.getInt("num_sciami_da_spostare"));
		this.setNumPacchiDaSpostare(rs.getInt("num_pacchi_da_spostare"));
		this.setErrore_sincronizzazione(rs.getString("errore_sincronizzazione"));
		this.setAccettazioneDestinatario(rs.getInt("accettazione_destinatario"));
		this.setDataAccettazioneDestinatario(rs.getTimestamp("data_accettazione_destinatario"));
		this.setAccettazioneDataSincronizzazione(rs.getTimestamp("accettazione_data_sincronizzazione"));
		this.setUtenteAccettazioneDestinatario(rs.getInt("utente_accettazione_destinatario"));
		this.setAttestazioneSanitaria(rs.getBoolean("attestazione_sanitaria"));
		
				
	}
	
	
	public void generaNumeroModello(Connection db) throws SQLException
	{
		
		
		
		Calendar cal=GregorianCalendar.getInstance();
	    cal.setTime(new Date (data_modello.getTime()));
		int anno =  cal.get(Calendar.YEAR);
		
		String sl ="select * from dbi_get_apicoltura_num_mod_movimentazione(?,?)";
		PreparedStatement pst = db.prepareStatement(sl);
		pst.setString(1, codiceAziendaOrigine);
		pst.setInt(2, anno);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			numero_modello = rs.getString(1);
		
		
	}
	
	public void sincronizzaBdn(Connection db,int modifiedby,int stato ) throws SQLException
	{
		String sql="update apicoltura_movimentazioni set id_bdn = ?, sincronizzato_bdn = true , errore_sincronizzazione = null, sincronizzato_da = ?,data_sincronizzazione = current_timestamp,stato=? where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setObject(1, getIdBdn());
		pst.setInt(2, modifiedby);
		pst.setInt(3, stato);
		pst.setInt(4, this.getId());
		
		System.out.println(pst.toString());
		pst.execute();
	}
	
	
	
}
