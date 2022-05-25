/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali_ext.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;



import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu_ext.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu_ext.base.LineaProduttiva;
import org.aspcfs.modules.opu_ext.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

public class Cane extends Animale {

	public final static int idSpecie = 1;

	private int idCane;
	//private int idDetentore;
//	private int idTaglia;
	private boolean flagAggressivo;
	private boolean flagMorsicatore;
	private boolean flagNatoDaIncrocio;
	private String leishmaniosiNumeroOrdinanzaSindacale;
	private Timestamp leishmaniosiDataOrdinanzaSindacale;
	//private Operatore detentore;
	private boolean flagFuoriRegione;
	private int idRegioneProvenienza = -1;
	private boolean flagSindacoFuoriRegione;
	private Timestamp dataMorso; // data ultimo evento di morsicatura
	// private Timestamp dataMorso2;
	// private Timestamp dataMorso3;
	//private String tatuaggio = "";
	private Timestamp dataSecondoMicrochip;
	private boolean flagCattura;
	private int idComuneCattura;
	private Timestamp dataCattura;
	private String luogoCattura;
	private String verbaleCattura;
	private boolean flagRiCattura = false;
	boolean flagReimmesso = false;
	// private Timestamp dataMicrochip;

	private boolean flagControlloRickettsiosi = false;
	private boolean flagControlloEhrlichiosi = false;
	private Timestamp dataControlloRickettsiosi;
	private Timestamp dataControlloEhrlichiosi;
	private int esitoControlloEhrlichiosi = -1;
	private int esitoControlloRickettsiosi = -1;
	
	
	private boolean flagPrelievoDnaEffettuato = false;

	// private int idVeterinarioMicrochip;
	private Operatore VeterinarioChip;

	private boolean flagControlloIdentita;
	//private Timestamp dataVaccino;
//	private String numeroLottoVaccino;

//	private int idDetentoreUltimoTrasferimentoFRegione = -1; // Conservo l'id

	// del detentore
	// precedente a
	// ultimo
	// trasferimento
	// fuori regione
	
//	private int idDetentoreUltimoTrasferimentoFStato = -1;
	// Conservo l'id

	// del detentore
	// precedente a
	// ultimo
	// trasferimento
	// fuori stato

	public Cane() {
	}
	
	

	public boolean isFlagPrelievoDnaEffettuato() {
		return flagPrelievoDnaEffettuato;
	}



	public void setFlagPrelievoDnaEffettuato(boolean flagPrelievoDnaEffettuato) {
		this.flagPrelievoDnaEffettuato = flagPrelievoDnaEffettuato;
	}



	public boolean isFlagRiCattura() {
		return flagRiCattura;
	}

	public void setFlagRiCattura(boolean flagRiCattura) {
		this.flagRiCattura = flagRiCattura;
	}

	public void setFlagRiCattura(String flagRiCattura) {
		this.flagRiCattura = DatabaseUtils.parseBoolean(flagRiCattura);
	}

/*	public String getTatuaggio() {
		return tatuaggio;
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}*/

	public boolean isFlagReimmesso() {
		return flagReimmesso;
	}

	public void setFlagReimmesso(boolean flagReimmesso) {
		this.flagReimmesso = flagReimmesso;
	}

	public void setFlagReimmesso(String flagReimmesso) {
		this.flagReimmesso = DatabaseUtils.parseBoolean(flagReimmesso);
	}

//	public Timestamp getDataVaccino() {
//		return dataVaccino;
//	}
//
//	public void setDataVaccino(Timestamp dataVaccino) {
//		this.dataVaccino = dataVaccino;
//	}
//
//	public void setDataVaccino(String dataVaccino) {
//		this.dataVaccino = DatabaseUtils.parseDateToTimestamp(dataVaccino);
//	}
//
//	public String getNumeroLottoVaccino() {
//		return numeroLottoVaccino;
//	}
//
//	public void setNumeroLottoVaccino(String numeroLottoVaccino) {
//		this.numeroLottoVaccino = numeroLottoVaccino;
//	}

	public boolean isFlagControlloRickettsiosi() {
		return flagControlloRickettsiosi;
	}

	public boolean getFlagControlloRickettsiosi() {
		return flagControlloRickettsiosi;
	}

	public void setFlagControlloRickettsiosi(boolean flagControlloRickettsiosi) {
		this.flagControlloRickettsiosi = flagControlloRickettsiosi;
	}

	public void setFlagControlloRickettsiosi(String flagControlloRickettsiosi) {
		this.flagControlloRickettsiosi = DatabaseUtils
				.parseBoolean(flagControlloRickettsiosi);
	}

	/*
	 * public boolean isFlagControlloIdentita() { return flagControlloIdentita;
	 * }
	 */

	/*
	 * public void setFlagControlloIdentita(boolean flagControlloIdentita) {
	 * this.flagControlloIdentita = flagControlloIdentita; }
	 * 
	 * 
	 * public void setFlagControlloIdentita(String flagControlloIdentita) {
	 * this.flagControlloIdentita =
	 * DatabaseUtils.parseBoolean(flagControlloIdentita); }
	 */

	public boolean isFlagControlloEhrlichiosi() {
		return flagControlloEhrlichiosi;
	}

	public boolean getFlagControlloEhrlichiosi() {
		return flagControlloEhrlichiosi;
	}

	public void setFlagControlloEhrlichiosi(boolean flagControlloEhrlichiosi) {
		this.flagControlloEhrlichiosi = flagControlloEhrlichiosi;
	}

	public void setFlagControlloEhrlichiosi(String flag) {
		this.flagControlloEhrlichiosi = DatabaseUtils.parseBoolean(flag);
	}

	public Timestamp getDataMorso() {
		return dataMorso;
	}

	public Timestamp getDataSecondoMicrochip() {
		return dataSecondoMicrochip;
	}

	public void setDataSecondoMicrochip(Timestamp dataSecondoMicrochip) {
		this.dataSecondoMicrochip = dataSecondoMicrochip;
	}

	public void setDataSecondoMicrochip(String dataSecondoMicrochip) {
		this.dataSecondoMicrochip = DatabaseUtils
				.parseDateToTimestamp(dataSecondoMicrochip);
	}

	public boolean isFlagCattura() {
		return flagCattura;
	}

	public boolean getFlagCattura() {
		return flagCattura;
	}

	public void setFlagCattura(boolean flagCattura) {
		this.flagCattura = flagCattura;
	}

	public void setFlagCattura(String flagCattura) {
		this.flagCattura = DatabaseUtils.parseBoolean(flagCattura);
	}

	public int getIdComuneCattura() {
		return idComuneCattura;
	}

	public void setIdComuneCattura(int idComuneCattura) {
		this.idComuneCattura = idComuneCattura;
	}

	public void setIdComuneCattura(String idComuneCattura) {
		this.idComuneCattura = new Integer(idComuneCattura).intValue();
	}

	public String getLuogoCattura() {
		return luogoCattura;
	}

	public void setLuogoCattura(String luogoCattura) {
		this.luogoCattura = luogoCattura;
	}

	public String getVerbaleCattura() {
		return verbaleCattura;
	}

	public void setVerbaleCattura(String verbaleCattura) {
		this.verbaleCattura = verbaleCattura;
	}

	public boolean isFlagControlloIdentita() {
		return flagControlloIdentita;
	}

	public void setFlagControlloIdentita(boolean flagControlloIdentita) {
		this.flagControlloIdentita = flagControlloIdentita;
	}

	public Timestamp getDataCattura() {
		return dataCattura;
	}

	public void setDataCattura(Timestamp dataCattura) {
		this.dataCattura = dataCattura;
	}

	public void setDataCattura(String dataCattura) {
		this.dataCattura = DatabaseUtils.parseDateToTimestamp(dataCattura);
	}

	public java.sql.Timestamp getDataControlloEhrlichiosi() {
		return dataControlloEhrlichiosi;
	}

	public java.sql.Timestamp getDataControlloRickettsiosi() {
		return dataControlloRickettsiosi;
	}

	public void setDataControlloRickettsiosi(
			java.sql.Timestamp dataControlloRickettsiosi) {
		this.dataControlloRickettsiosi = dataControlloRickettsiosi;
	}

	public void setDataControlloRickettsiosi(String dataControlloRickettsiosi) {
		this.dataControlloRickettsiosi = DatabaseUtils
				.parseDateToTimestamp(dataControlloRickettsiosi);
	}

	public void setDataControlloEhrlichiosi(
			java.sql.Timestamp dataControlloEhrlichiosi) {
		this.dataControlloEhrlichiosi = dataControlloEhrlichiosi;
	}

	public void setDataControlloEhrlichiosi(String dataControlloEhrlichiosi) {
		this.dataControlloEhrlichiosi = DatabaseUtils
				.parseDateToTimestamp(dataControlloEhrlichiosi);
	}

	public void setEsitoControlloEhrlichiosi(String esitoControlloEhrlichiosi) {
		this.esitoControlloEhrlichiosi = new Integer(esitoControlloEhrlichiosi)
				.intValue();
	}

	public int getEsitoControlloEhrlichiosi() {
		return esitoControlloEhrlichiosi;
	}

	public void setEsitoControlloEhrlichiosi(int esitoControlloEhrlichiosi) {
		this.esitoControlloEhrlichiosi = esitoControlloEhrlichiosi;
	}

	public void setEsitoControlloRickettsiosi(String esitoControlloRickettsiosi) {
		this.esitoControlloRickettsiosi = new Integer(
				esitoControlloRickettsiosi).intValue();
	}

	public int getEsitoControlloRickettsiosi() {
		return esitoControlloRickettsiosi;
	}

	public void setEsitoControlloRickettsiosi(int esitoControlloRickettsiosi) {
		this.esitoControlloRickettsiosi = esitoControlloRickettsiosi;
	}

	public void setDataMorso(Timestamp dataMorso1) {
		this.dataMorso = dataMorso1;
	}

	public void setDataMorso(String dataMorso1) {
		this.dataMorso = DatabaseUtils.parseDateToTimestamp(dataMorso1);
	}

	/*
	 * public void setDataMorso2(String dataMorso2) { this.dataMorso2 =
	 * DatabaseUtils.parseDateToTimestamp(dataMorso2); }
	 * 
	 * public void setDataMorso3(String dataMorso3) { this.dataMorso3 =
	 * DatabaseUtils.parseDateToTimestamp(dataMorso3); }
	 */

	/*
	 * public int getIdVeterinarioMicrochip() { return idVeterinarioMicrochip; }
	 * 
	 * public void setIdVeterinarioMicrochip(int idVeterinarioMicrochip) {
	 * this.idVeterinarioMicrochip = idVeterinarioMicrochip; }
	 * 
	 * public void setIdVeterinarioMicrochip(String idVeterinarioMicrochip) {
	 * 
	 * this.idVeterinarioMicrochip = DatabaseUtils.parseInt(
	 * idVeterinarioMicrochip, 0); }
	 */

	public Operatore getVeterinarioChip() {
		return VeterinarioChip;
	}

	public void setVeterinarioChip(Operatore veterinarioChip) {
		VeterinarioChip = veterinarioChip;
	}

	/*
	 * public Timestamp getDataMorso2() { return dataMorso2; }
	 * 
	 * public void setDataMorso2(Timestamp dataMorso2) { this.dataMorso2 =
	 * dataMorso2; }
	 * 
	 * public Timestamp getDataMorso3() { return dataMorso3; }
	 * 
	 * public void setDataMorso3(Timestamp dataMorso3) { this.dataMorso3 =
	 * dataMorso3; }
	 */

	
	
	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public boolean getFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
	}

	public void setFlagFuoriRegione(String flagFuoriRegione) {
		this.flagFuoriRegione = DatabaseUtils.parseBoolean(flagFuoriRegione);
	}

	public int getIdRegioneProvenienza() {
		return idRegioneProvenienza;
	}

	public boolean isFlagSindacoFuoriRegione() {
		return flagSindacoFuoriRegione;
	}

	public boolean getFlagSindacoFuoriRegione() {
		return flagSindacoFuoriRegione;
	}

	public void setFlagSindacoFuoriRegione(boolean flagSindacoFuoriRegione) {
		this.flagSindacoFuoriRegione = flagSindacoFuoriRegione;
	}

	public void setFlagSindacoFuoriRegione(String flagSindacoFuoriRegione) {
		this.flagSindacoFuoriRegione = DatabaseUtils
				.parseBoolean(flagSindacoFuoriRegione);
		;
	}

	public void setIdRegioneProvenienza(int idRegioneProvenienza) {
		this.idRegioneProvenienza = idRegioneProvenienza;
	}

	public void setIdRegioneProvenienza(String idRegioneProvenienza) {
		this.idRegioneProvenienza = DatabaseUtils.parseInt(
				idRegioneProvenienza, -1);
	}

	public boolean isFlagAggressivo() {
		return flagAggressivo;
	}

	public boolean getFlagAggressivo() {
		return flagAggressivo;
	}

	public void setFlagAggressivo(boolean flagAggressivo) {
		this.flagAggressivo = flagAggressivo;
	}

	public void setFlagAggressivo(String flagAggressivo) {
		this.flagAggressivo = DatabaseUtils.parseBoolean(flagAggressivo);
	}

	public boolean isFlagMorsicatore() {
		return flagMorsicatore;
	}

	public boolean getFlagMorsicatore() {
		return flagMorsicatore;
	}

	public void setFlagMorsicatore(boolean flagMorsicatore) {
		this.flagMorsicatore = flagMorsicatore;
	}

	public void setFlagMorsicatore(String flagMorsicatore) {
		this.flagMorsicatore = DatabaseUtils.parseBoolean(flagMorsicatore);
	}

	public boolean isFlagNatoDaIncrocio() {
		return flagNatoDaIncrocio;
	}

	public boolean getFlagNatoDaIncrocio() {
		return flagNatoDaIncrocio;
	}

	public void setFlagNatoDaIncrocio(boolean flagNatoDaIncrocio) {
		this.flagNatoDaIncrocio = flagNatoDaIncrocio;
	}

	public void setFlagNatoDaIncrocio(String flagNatoDaIncrocio) {
		this.flagNatoDaIncrocio = DatabaseUtils
				.parseBoolean(flagNatoDaIncrocio);
	}

	public String getLeishmaniosiNumeroOrdinanzaSindacale() {
		return leishmaniosiNumeroOrdinanzaSindacale;
	}

	public void setLeishmaniosiNumeroOrdinanzaSindacale(
			String leishmaniosiNumeroOrdinanzaSindacale) {
		this.leishmaniosiNumeroOrdinanzaSindacale = leishmaniosiNumeroOrdinanzaSindacale;
	}

	public Timestamp getLeishmaniosiDataOrdinanzaSindacale() {
		return leishmaniosiDataOrdinanzaSindacale;
	}

	public void setLeishmaniosiDataOrdinanzaSindacale(
			Timestamp leishmaniosiDataOrdinanzaSindacale) {
		this.leishmaniosiDataOrdinanzaSindacale = leishmaniosiDataOrdinanzaSindacale;
	}

	public void setLeishmaniosiDataOrdinanzaSindacale(
			String leishmaniosiDataOrdinanzaSindacale) {
		this.leishmaniosiDataOrdinanzaSindacale = DatabaseUtils
				.parseDateToTimestamp(leishmaniosiDataOrdinanzaSindacale);
	}

/*	public Operatore getDetentore() {
		return detentore;
	}

	public void setDetentore(Operatore detentore) {
		this.detentore = detentore;
	}*/

	public int getIdCane() {
		return idCane;
	}

	public void setIdCane(int idCane) {
		this.idCane = idCane;
	}

	public void setIdCane(String idCane) {
		this.idCane = new Integer(idCane).intValue();
	}

/*	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		if (idDetentore != null && !"".equals(idDetentore))
			this.idDetentore = Integer.parseInt(idDetentore);
	}*/
/*
	public int getIdTaglia() {
		return idTaglia;
	}*/

/*	public int getIdDetentoreUltimoTrasferimentoFRegione() {
		return idDetentoreUltimoTrasferimentoFRegione;
	}

	public void setIdDetentoreUltimoTrasferimentoFRegione(
			int idDetentoreUltimoTrasferimentoFRegione) {
		this.idDetentoreUltimoTrasferimentoFRegione = idDetentoreUltimoTrasferimentoFRegione;
	}

	public void setIdDetentoreUltimoTrasferimentoFRegione(
			String idDetentoreUltimoTrasferimentoFRegione) {
		this.idDetentoreUltimoTrasferimentoFRegione = new Integer(
				idDetentoreUltimoTrasferimentoFRegione).intValue();
	}*/
	
	
/*
	public int getIdDetentoreUltimoTrasferimentoFStato() {
		return idDetentoreUltimoTrasferimentoFStato;
	}

	public void setIdDetentoreUltimoTrasferimentoFStato(
			int idDetentoreUltimoTrasferimentoFStato) {
		this.idDetentoreUltimoTrasferimentoFStato = idDetentoreUltimoTrasferimentoFStato;
	}
	
	public void setIdDetentoreUltimoTrasferimentoFStato(
			String idDetentoreUltimoTrasferimentoFStato) {
		this.idDetentoreUltimoTrasferimentoFStato = new Integer (idDetentoreUltimoTrasferimentoFStato).intValue();
	}
*/
/*	public String getDecodificaIdTaglia(Connection db) {
		LookupList tagliaList = new LookupList();
		try {

			tagliaList.setTable("lookup_taglia");
			tagliaList.buildList(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tagliaList.getSelectedValue(this.getIdTaglia());
	}*/
//
//	public void setIdTaglia(int idTaglia) {
//		this.idTaglia = idTaglia;
//	}
//
//	public void setIdTaglia(String idTaglia) {
//		if (idTaglia != null && !"".equalsIgnoreCase(idTaglia))
//			this.idTaglia = Integer.parseInt(idTaglia);
//	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			

			super.insert(db);

			int idAnimale = super.getIdAnimale();

			idCane = DatabaseUtils.getNextSeqPostgres(db, "cane_id_cane_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO cane(id_cane,id_animale,flag_aggressivo,flag_morsicatore,flag_nato_da_incrocio,flag_fuori_regione," +
							"flag_sindaco_fuori_regione ");

			/*
			 * if (idVeterinarioMicrochip > 0) {
			 * sql.append(",id_veterinario_microchip"); }
			 */

//			if (dataVaccino != null) {
//				sql.append(",vaccino_data");
//			}
//			if (numeroLottoVaccino != null) {
//				sql.append(",vaccino_numero_lotto");
//			}

			if (leishmaniosiNumeroOrdinanzaSindacale != null) {
				sql.append(",leishmaniosi_numero_ordinanza_sindacale");
			}
			if (leishmaniosiDataOrdinanzaSindacale != null) {
				sql.append(",leishmaniosi_data_ordinanza_sindacale");
			}

			if (idRegioneProvenienza > 0) {
				sql.append(",id_regione_provenienza");
			}
			if (dataMorso != null) {
				sql.append(",data_morso_1");
			}

			/*
			 * if (dataMorso2 != null) { sql.append(",data_morso_2"); } if
			 * (dataMorso3 != null) { sql.append(",data_morso_3"); }
			 */

			if (dataSecondoMicrochip != null) {
				sql.append(",data_secondo_microchip");
			}
/*
			if (tatuaggio != null && !("").equals(tatuaggio)) {
				sql.append(",secondo_microchip");
			}*/

			if (idComuneCattura > -1) {
				sql.append(",id_comune_cattura");
			}
			if (dataCattura != null) {
				sql.append(",data_cattura");
			}
			if (luogoCattura != null) {
				sql.append(",luogo_cattura");
			}
			if (verbaleCattura != null) {
				sql.append(",verbale_cattura");
			}

			if (dataControlloRickettsiosi != null) {
				sql.append(",rickettsiosi_data_esito_controllo");
			}
			if (dataControlloEhrlichiosi != null) {
				sql.append(",ehrlichiosi_data_esito_controllo");
			}
			if (esitoControlloEhrlichiosi >= 0) {
				sql.append(",ehrlichiosi_esito_controllo");
			}
			if (esitoControlloRickettsiosi >= 0) {
				sql.append(",rickettsiosi_esito_controllo");
			}
		/*	if (idDetentoreUltimoTrasferimentoFRegione > -1) {
				sql.append(", id_detentore_ultimo_trasferimento_a_regione");
			}*/
/*			if (idDetentoreUltimoTrasferimentoFStato > -1) {
				sql.append(", id_detentore_ultimo_trasferimento_a_stato");
			}*/

			sql
					.append(",flag_catturato,flag_controllo_ehrlichiosi,flag_controllo_rickettiosi)");

			sql.append("VALUES ( ?,?,?,?,?,?,? ");

			/*
			 * if (idVeterinarioMicrochip > 0) { sql.append(",?"); }
			 */
//			if (dataVaccino != null) {
//				sql.append(",?");
//			}
//			if (numeroLottoVaccino != null) {
//				sql.append(",?");
//			}
			if (leishmaniosiNumeroOrdinanzaSindacale != null) {
				sql.append(",?");
			}
			if (leishmaniosiDataOrdinanzaSindacale != null) {
				sql.append(",?");
			}

			if (idRegioneProvenienza > 0) {
				sql.append(",?");
			}
			
			 if (dataMorso != null) 
			 
			 { sql.append(",?"); 
			 
			 }
			/*
			 * if (dataMorso2 !=
			 * null) { sql.append(",?"); } if (dataMorso3 != null) {
			 * sql.append(",?"); }
			 */

			if (dataSecondoMicrochip != null) {
				sql.append(",?");
			}

/*			if (tatuaggio != null && !("").equals(tatuaggio)) {
				sql.append(", ?");
			}*/

			if (idComuneCattura > -1) {
				sql.append(",?");
			}

			if (dataCattura != null) {
				sql.append(",?");
			}
			if (luogoCattura != null) {
				sql.append(",?");
			}
			if (verbaleCattura != null) {
				sql.append(",?");
			}

			if (dataControlloRickettsiosi != null) {
				sql.append(",?");
			}
			if (dataControlloEhrlichiosi != null) {
				sql.append(",?");
			}
			if (esitoControlloEhrlichiosi >= 0) {
				sql.append(",?");
			}
			if (esitoControlloRickettsiosi >= 0) {
				sql.append(",?");
			}
//			if (idDetentoreUltimoTrasferimentoFRegione > -1) {
//				sql.append(", ?");
//			}
/*			if (idDetentoreUltimoTrasferimentoFStato > -1) {
				sql.append(", ?");
			}*/

			sql.append(",?,?,?");
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idCane);
			pst.setInt(++i, idAnimale);
		//	pst.setInt(++i, idDetentore);
			//pst.setInt(++i, idTaglia);
			pst.setBoolean(++i, flagAggressivo);
			pst.setBoolean(++i, flagMorsicatore);
			pst.setBoolean(++i, flagNatoDaIncrocio);
			pst.setBoolean(++i, flagFuoriRegione);
			pst.setBoolean(++i, flagSindacoFuoriRegione);
			// pst.setTimestamp(++i, dataMicrochip);
			/*
			 * if (idVeterinarioMicrochip > 0) { pst.setInt(++i,
			 * idVeterinarioMicrochip); }
			 */
//			if (dataVaccino != null) {
//				pst.setTimestamp(++i, dataVaccino);
//			}
//			if (numeroLottoVaccino != null) {
//				pst.setString(++i, numeroLottoVaccino);
//			}
			if (leishmaniosiNumeroOrdinanzaSindacale != null) {
				pst.setString(++i, leishmaniosiNumeroOrdinanzaSindacale);
			}
			if (leishmaniosiDataOrdinanzaSindacale != null) {
				pst.setTimestamp(++i, leishmaniosiDataOrdinanzaSindacale);
			}

			if (idRegioneProvenienza > 0) {
				pst.setInt(++i, idRegioneProvenienza);
			}
			
			if (dataMorso != null) { pst.setTimestamp(++i, dataMorso); }
			
			
			/*
			 *  if
			 * (dataMorso2 != null) { pst.setTimestamp(++i, dataMorso2); } if
			 * (dataMorso3 != null) { pst.setTimestamp(++i, dataMorso3); }
			 */

			if (dataSecondoMicrochip != null) {
				pst.setTimestamp(++i, dataSecondoMicrochip);
			}

/*			if (tatuaggio != null && !("").equals(tatuaggio)) {
				pst.setString(++i, tatuaggio);
			}*/

			if (idComuneCattura > -1) {
				pst.setInt(++i, idComuneCattura);
			}
			if (dataCattura != null) {
				pst.setTimestamp(++i, dataCattura);
			}
			if (luogoCattura != null) {
				pst.setString(++i, luogoCattura);
			}
			if (verbaleCattura != null) {
				pst.setString(++i, verbaleCattura);
			}

			if (dataControlloRickettsiosi != null) {
				pst.setTimestamp(++i, dataControlloRickettsiosi);
			}
			if (dataControlloEhrlichiosi != null) {
				pst.setTimestamp(++i, dataControlloEhrlichiosi);
			}
			if (esitoControlloEhrlichiosi >= 0) {
				pst.setInt(++i, esitoControlloEhrlichiosi);
			}
			if (esitoControlloRickettsiosi >= 0) {
				pst.setInt(++i, esitoControlloRickettsiosi);
			}
//			if (idDetentoreUltimoTrasferimentoFRegione > -1) {
//				pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
//			}
/*			
			if (idDetentoreUltimoTrasferimentoFStato > -1) {
				pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);
			}*/

			pst.setBoolean(++i, flagCattura);
			pst.setBoolean(++i, flagControlloEhrlichiosi);

			pst.setBoolean(++i, flagControlloRickettsiosi);

			pst.execute();
			pst.close();

			this.idCane = DatabaseUtils.getCurrVal(db, "cane_id_cane_seq",
					idCane);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public Cane(Connection db, int idAnimale) throws SQLException {

		super(db, idAnimale);

		PreparedStatement pst = db
				.prepareStatement("Select * from cane where id_animale = ?");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
/*			if (rs.getInt("id_detentore") > 0) {
				detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs
						.getInt("id_detentore"));
			}*/
			if (rs.getInt("id_veterinario_microchip") > 0) {
				VeterinarioChip = new Operatore();
				VeterinarioChip.queryRecordOperatorebyIdLineaProduttiva(db, rs
						.getInt("id_veterinario_microchip"));

			}
		//	this.setDetentore(detentore);

		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	public Cane(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		flagSindacoFuoriRegione = rs.getBoolean("flag_sindaco_fuori_regione");
		idCane = rs.getInt("id_cane");
		//idTaglia = rs.getInt("id_taglia");
	//	idDetentore = rs.getInt("id_detentore");
		flagAggressivo = rs.getBoolean("flag_aggressivo");
		flagMorsicatore = rs.getBoolean("flag_morsicatore");
		flagNatoDaIncrocio = rs.getBoolean("flag_nato_da_incrocio");
		flagMorsicatore = rs.getBoolean("flag_morsicatore");
		leishmaniosiNumeroOrdinanzaSindacale = rs
				.getString("leishmaniosi_numero_ordinanza_sindacale");
		leishmaniosiDataOrdinanzaSindacale = rs
				.getTimestamp("leishmaniosi_data_ordinanza_sindacale");
		flagFuoriRegione = rs.getBoolean("flag_fuori_regione");
		idRegioneProvenienza = rs.getInt("id_regione_provenienza"); // Regione
		// se Cane è
		// registrato
		// con
		// provenienza
		// fuori
		// regione
		dataMorso = rs.getTimestamp("data_morso_1");
		/*
		 * dataMorso2 = rs.getTimestamp("data_morso_2"); dataMorso3 =
		 * rs.getTimestamp("data_morso_3");
		 */
	//	dataSecondoMicrochip = rs.getTimestamp("data_secondo_microchip");
		idComuneCattura = rs.getInt("id_comune_cattura");
		dataCattura = rs.getTimestamp("data_cattura");
		luogoCattura = rs.getString("luogo_cattura");
		verbaleCattura = rs.getString("verbale_cattura");
		dataControlloRickettsiosi = rs
				.getTimestamp("rickettsiosi_data_esito_controllo");
		dataControlloEhrlichiosi = rs
				.getTimestamp("ehrlichiosi_data_esito_controllo");
		esitoControlloEhrlichiosi = rs.getInt("ehrlichiosi_esito_controllo");
		esitoControlloRickettsiosi = rs.getInt("rickettsiosi_esito_controllo");
		flagCattura = rs.getBoolean("flag_catturato");
		flagControlloEhrlichiosi = rs.getBoolean("flag_controllo_ehrlichiosi");
		// flagControlloIdentita = rs.getBoolean("flag_controllo_identita");
		flagControlloRickettsiosi = rs.getBoolean("flag_controllo_rickettiosi");
		try {
			rs.findColumn("flag_prelievo_dna");
			flagPrelievoDnaEffettuato = rs.getBoolean("flag_prelievo_dna");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		//dataVaccino = rs.getTimestamp("vaccino_data");
	//	numeroLottoVaccino = rs.getString("vaccino_numero_lotto");
		// dataMicrochip = rs.getTimestamp("data_chippatura");
	//	idDetentoreUltimoTrasferimentoFRegione = rs
				//getInt("id_detentore_ultimo_trasferimento_a_regione");
	//	idDetentoreUltimoTrasferimentoFStato = rs.getInt("id_detentore_ultimo_trasferimento_a_stato");
	//	tatuaggio = rs.getString("secondo_microchip");

	}

	public void updateStato(Connection con) throws SQLException {

		super.updateStato(con);
		PreparedStatement pst = con
				.prepareStatement("Update cane set  flag_morsicatore = ?,  " +
						"flag_ricattura = ?, flag_reimmesso = ?, data_morso_1 = ?, flag_prelievo_dna = ? where id_cane = "
						+ this.getIdCane());
		int i = 0;
	//	pst.setInt(++i, idDetentore);
		pst.setBoolean(++i, flagMorsicatore);
	//	pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
	//	pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);
		pst.setBoolean(++i, flagRiCattura);
		pst.setBoolean(++i, flagReimmesso);
		pst.setTimestamp(++i, dataMorso);
		pst.setBoolean(++i, flagPrelievoDnaEffettuato);
	//	pst.setString(++i, tatuaggio);
		//pst.setTimestamp(++i, dataSecondoMicrochip);
		int resultCount = pst.executeUpdate();
		pst.close();

	}

	public int update(Connection con) {
		int result = -1;
		try {
			super.update(con);
			PreparedStatement pst = con
					.prepareStatement("UPDATE cane "
							+ "SET id_detentore=?, id_taglia=?, "
							+ "secondo_microchip=?, data_secondo_microchip=?, "
							+ "flag_aggressivo=?, flag_fuori_regione=?, "
							+ " id_regione_provenienza=?, flag_sindaco_fuori_regione=?, flag_prelievo_dna = ? "
							//+ "vaccino_data=?, "
						//	+ " vaccino_numero_lotto=? "
							//,  id_detentore_ultimo_trasferimento_a_regione = ?,  "
							+ " WHERE id_cane = ?");
			int i = 0;
			pst.setInt(++i, this.getIdDetentore());
		//	pst.setInt(++i, this.getIdTaglia());
			pst.setString(++i, this.getTatuaggio());
			pst.setTimestamp(++i, this.getDataSecondoMicrochip());
			pst.setBoolean(++i, this.isFlagAggressivo());
			pst.setBoolean(++i, this.isFlagFuoriRegione());
			pst.setInt(++i, this.getIdRegioneProvenienza());
			pst.setBoolean(++i, this.isFlagSindacoFuoriRegione());
			pst.setBoolean(++i, this.isFlagPrelievoDnaEffettuato());
			//pst.setTimestamp(++i, this.getDataVaccino());
			//pst.setString(++i, this.getNumeroLottoVaccino());
		//	pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
		//	pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);

			pst.setInt(++i, this.getIdCane());

			result = pst.executeUpdate();

			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

//	public int updateControlliCane(Connection con,
//			EventoEsitoControlli controlli) {
//		int result = -1;
//
//		try {
//			super.update(con);
//			PreparedStatement pst = con
//					.prepareStatement("UPDATE cane "
//							+ "SET ehrlichiosi_esito_controllo=?, rickettsiosi_data_esito_controllo=?,"
//							+ "flag_controllo_ehrlichiosi=?, flag_controllo_rickettiosi=?, "
//							+ "ehrlichiosi_data_esito_controllo=?, rickettsiosi_esito_controllo=?"
//							+ " WHERE id_cane = ?");
//			int i = 0;
//			if (controlli == null) {
//				pst.setInt(++i, this.getEsitoControlloEhrlichiosi());
//				pst.setTimestamp(++i, this.getDataControlloRickettsiosi());
//				pst.setBoolean(++i, this.isFlagControlloEhrlichiosi());
//				pst.setBoolean(++i, this.isFlagControlloRickettsiosi());
//				pst.setTimestamp(++i, this.getDataControlloEhrlichiosi());
//				pst.setInt(++i, this.getEsitoControlloRickettsiosi());
//			} else {
//				pst.setInt(++i, controlli.getEsitoEhrlichiosi());
//				pst.setTimestamp(++i, controlli.getDataRickettiosi());
//				pst.setBoolean(++i, controlli.isFlagEhrlichiosi());
//				pst.setBoolean(++i, controlli.isFlagRickettiosi());
//				pst.setTimestamp(++i, controlli.getDataEhrlichiosi());
//				pst.setInt(++i, controlli.getEsitoRickettiosi());
//			}
//
//			pst.setInt(++i, this.getIdCane());
//
//			result = pst.executeUpdate();
//
//			pst.close();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return result;
//	}
	
	
	public boolean isRandagio(Connection db){
		boolean isRandagio = false;
	try{
		
		isRandagio = this.getFlagCattura();
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	return isRandagio;
	}
	
	
	//vede se un campo è -1 e l'altro 0, il che significa che sn vuoit
	public boolean checkNotEmpty(Method metod, Animale animale){
		boolean ret = true;
		
		
		if (((Type) metod.getReturnType()).equals(Integer.TYPE)){
			try{
		if( (((Integer)metod.invoke(this)) == -1 && ((Integer)metod.invoke(animale)) == 0)	|| 
				(((Integer)(metod.invoke(this)) == 0 && ((Integer)metod.invoke(animale) == -1)))){
			ret = false;
		}
			}catch (Exception e) {
				// TODO: handle exception
			}
					
		}	
		
		
		return ret;
	}

//	public ArrayList<CampoModificato> checkModifiche(Connection db,
//			Animale animale) {
//
//		Field[] campi = this.getClass().getDeclaredFields();
//
//		ArrayList<CampoModificato> nomiCampiModificati = super.checkModifiche(
//				db, animale);
//
//		Method metodo = null;
//		Method metodoDecodifica = null;
//
//		//System.out.println("figlio: ");
//
//		for (int i = 0; i < campi.length; i++) {
//			int k = 0;
//
//			String nameToUpperCase = (campi[i].getName().substring(0, 1)
//					.toUpperCase() + campi[i].getName().substring(1,
//					campi[i].getName().length()));
//			if (!(nameToUpperCase.equals("Detentore") || nameToUpperCase.equals("IdUtenteInserimento") || nameToUpperCase.equals("IdUtenteModifica") )) {
//				try {
//					metodo = this.getClass().getMethod("get" + nameToUpperCase,
//							null);
//				} catch (NoSuchMethodException exc) {
//				}
//			}
//			if (metodo != null)
//				try {
//					if (!(metodo.invoke(animale).equals(metodo.invoke(this))) && this.checkNotEmpty(metodo,animale)) {
//						CampoModificato campo = new CampoModificato();
//						try {
//							metodoDecodifica = null;
//							Class[] params = new Class[1];
//							Class c = java.sql.Connection.class;
//							params[0] = c;
//							metodoDecodifica = this.getClass().getMethod(
//									"getDecodifica" + nameToUpperCase, params);
//						} catch (NoSuchMethodException exc) {
//							if (System.getProperty("DEBUG") != null) 
//								System.out.println(nameToUpperCase
//									+ ": (CANE) non c'è bisogno di decodifica");
//						}
//						if (metodoDecodifica != null) {
//
//							Object[] args = new Object[1];
//							args[0] = db;
//							campo
//									.setValorePrecedenteStringa((String) metodoDecodifica
//											.invoke(animale, args));
//							campo
//									.setValoreModificatoStringa((String) metodoDecodifica
//											.invoke(this, args));
//						} else {
//							campo.setValorePrecedenteStringa(metodo.invoke(
//									animale).toString());
//							campo.setValoreModificatoStringa(metodo
//									.invoke(this).toString());
//						}
//
//						campo.setNomeCampo(nameToUpperCase);
//						campo.setValorePrecedente(metodo.invoke(animale)
//								.toString());
//						campo.setValoreModificato(metodo.invoke(this)
//								.toString());
//						nomiCampiModificati.add(campo);
//						k++;
//						//System.out.println(nameToUpperCase);
//					}
//				} catch (Exception ecc) {
//				}
//		}
//
//		return nomiCampiModificati;
//	}
}
