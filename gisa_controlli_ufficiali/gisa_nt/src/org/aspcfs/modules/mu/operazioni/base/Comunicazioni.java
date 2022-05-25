/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.operazioni.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.aspcfs.modules.mu.base.CapoUnivoco;
import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class Comunicazioni {
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_macellazioni";

	@Column(columnName = "data_comunicazioni_esterne", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataComunicazioniEsterne = null;

	@Column(columnName = "descrizione_non_conformita_comunicazioni", columnType = STRING, table = nome_tabella)
	private String descrizioneNonConformitaComunicazioni = "";

	@Column(columnName = "note_provvedimento_adottato", columnType = STRING, table = nome_tabella)
	private String noteProvvedimentoAdottato = "";
	
	@Column(columnName = "data_ricezione_comunicazioni_esterne", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataRicezioneComunicazioniEsterne = null;
	
	@Column(columnName = "note_ricezione_comunicazioni_esterne", columnType = STRING, table = nome_tabella)
	private String noteRicezioneComunicazioniEsterne = "";
	
	
	@Column(columnName = "comunicazione_asl_origine_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAslOrigineComunicazioni = false;
	
	
	@Column(columnName = "comunicazione_proprietario_animale_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneProprietarioAnimaleComunicazioni = false;
	
	
	@Column(columnName = "comunicazione_azienda_origine_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAziendaOrigineComunicazioni = false;
	
	@Column(columnName = "comunicazione_proprietario_macello_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneProprietarioMacelloComunicazioni = false;
	
	
	@Column(columnName = "comunicazione_pif_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazionePifComunicazioni = false;
	
	@Column(columnName = "comunicazione_uvac_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneUvacComunicazioni = false;
	
	
	@Column(columnName = "comunicazione_regione_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneRegioneComunicazioni = false;
	
	@Column(columnName = "comunicazione_altro_comunicazioni", columnType = BOOLEAN, table = nome_tabella)
	private boolean comunicazioneAltroComunicazioni = false;
	
	
	@Column(columnName = "comunucazione_altro_testo_comunicazioni", columnType = STRING, table = nome_tabella)
	private String comunucazioneAltroTestoComunicazioni = "";
	
	
	
	

	//private ArrayList<Integer> listaDestinatariComunicazioni = new ArrayList<Integer>(); SOSTITUITO DA BOOLEAN, RIVEDERE?!
	private ArrayList<Integer> listaNonConformita = new ArrayList<Integer>();
	private ArrayList<Integer> listaProvvedimenti = new ArrayList<Integer>();

	public Timestamp getDataComunicazioniEsterne() {
		return dataComunicazioniEsterne;
	}
	
	
	public void setDataComunicazioniEsterne(String dataComunicazione) {
		this.dataComunicazioniEsterne =   DateUtils.parseDateStringNew(dataComunicazione, "dd/MM/yyyy");
	}

	public void setDataComunicazioniEsterne(Timestamp dataComunicazioniEsterne) {
		this.dataComunicazioniEsterne = dataComunicazioniEsterne;
	}

	public String getDescrizioneNonConformitaComunicazioni() {
		return descrizioneNonConformitaComunicazioni;
	}

	public void setDescrizioneNonConformitaComunicazioni(String descrizioneNonConformitaComunicazioni) {
		this.descrizioneNonConformitaComunicazioni = descrizioneNonConformitaComunicazioni;
	}

	public String getNoteProvvedimentoAdottato() {
		return noteProvvedimentoAdottato;
	}

	public void setNoteProvvedimentoAdottato(String noteProvvedimentoAdottato) {
		this.noteProvvedimentoAdottato = noteProvvedimentoAdottato;
	}
	
	
	public boolean isComunicazioneAslOrigineComunicazioni() {
		return comunicazioneAslOrigineComunicazioni;
	}

	public void setComunicazioneAslOrigineComunicazioni(boolean comunicazioneAslOrigineComunicazioni) {
		this.comunicazioneAslOrigineComunicazioni = comunicazioneAslOrigineComunicazioni;
	}
	
	public void setComunicazioneAslOrigineComunicazioni(
			String flag) {
		this.comunicazioneAslOrigineComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}

	public boolean isComunicazioneProprietarioAnimaleComunicazioni() {
		return comunicazioneProprietarioAnimaleComunicazioni;
	}

	public void setComunicazioneProprietarioAnimaleComunicazioni(boolean comunicazioneProprietarioAnimaleComunicazioni) {
		this.comunicazioneProprietarioAnimaleComunicazioni = comunicazioneProprietarioAnimaleComunicazioni;
	}
	
	public void setComunicazioneProprietarioAnimaleComunicazioni(
			String flag) {
		this.comunicazioneProprietarioAnimaleComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}



	public boolean isComunicazioneProprietarioMacelloComunicazioni() {
		return comunicazioneProprietarioMacelloComunicazioni;
	}

	public void setComunicazioneProprietarioMacelloComunicazioni(boolean comunicazioneProprietarioMacelloComunicazioni) {
		this.comunicazioneProprietarioMacelloComunicazioni = comunicazioneProprietarioMacelloComunicazioni;
	}
	
	public void setComunicazioneProprietarioMacelloComunicazioni(
			String flag) {
		this.comunicazioneProprietarioMacelloComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}

	public boolean isComunicazionePifComunicazioni() {
		return comunicazionePifComunicazioni;
	}

	public void setComunicazionePifComunicazioni(boolean comunicazionePifComunicazioni) {
		this.comunicazionePifComunicazioni = comunicazionePifComunicazioni;
	}
	
	public void setComunicazionePifComunicazioni(
			String flag) {
		this.comunicazionePifComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}

	public boolean isComunicazioneUvacComunicazioni() {
		return comunicazioneUvacComunicazioni;
	}

	public void setComunicazioneUvacComunicazioni(boolean comunicazioneUvacComunicazioni) {
		this.comunicazioneUvacComunicazioni = comunicazioneUvacComunicazioni;
	}
	
	public void setComunicazioneUvacComunicazioni(
			String flag) {
		this.comunicazioneUvacComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}


	public boolean isComunicazioneRegioneComunicazioni() {
		return comunicazioneRegioneComunicazioni;
	}

	public void setComunicazioneRegioneComunicazioni(boolean comunicazioneRegioneComunicazioni) {
		this.comunicazioneRegioneComunicazioni = comunicazioneRegioneComunicazioni;
	}
	
	public void setComunicazioneRegioneComunicazioni(
			String flag) {
		this.comunicazioneRegioneComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}

	public boolean isComunicazioneAltroComunicazioni() {
		return comunicazioneAltroComunicazioni;
	}

	public void setComunicazioneAltroComunicazioni(boolean comunicazioneAltroComunicazioni) {
		this.comunicazioneAltroComunicazioni = comunicazioneAltroComunicazioni;
	}
	
	public void setComunicazioneAltroComunicazioni(
			String flag) {
		this.comunicazioneAltroComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}
	
	
	public boolean isComunicazioneAziendaOrigineComunicazioni() {
		return comunicazioneAziendaOrigineComunicazioni;
	}

	public void setComunicazioneAziendaOrigineComunicazioni(boolean comunicazioneAziendaOrigineComunicazioni) {
		this.comunicazioneAziendaOrigineComunicazioni = comunicazioneAziendaOrigineComunicazioni;
	}
	
	public void setComunicazioneAziendaOrigineComunicazioni(
			String flag) {
		this.comunicazioneAziendaOrigineComunicazioni = DatabaseUtils
				.parseBoolean(flag);
	}
	
	
	


//	public ArrayList<Integer> getListaDestinatariComunicazioni() {
//		return listaDestinatariComunicazioni;
//	}
//
//	public void setListaDestinatariComunicazioni(ArrayList<Integer> listaDestinatariComunicazioni) {
//		this.listaDestinatariComunicazioni = listaDestinatariComunicazioni;
//	}

	public String getComunucazioneAltroTestoComunicazioni() {
		return comunucazioneAltroTestoComunicazioni;
	}


	public void setComunucazioneAltroTestoComunicazioni(String comunucazioneAltroTestoComunicazioni) {
		this.comunucazioneAltroTestoComunicazioni = comunucazioneAltroTestoComunicazioni;
	}


	public ArrayList<Integer> getListaNonConformita() {
		return listaNonConformita;
	}

//	public void setListaNonConformita(ArrayList<Integer> listaNonConformita) {
//		this.listaNonConformita = listaNonConformita;
//	}
	
	
	public void setListaNonConformita(ArrayList<String> listaNonConformita) {
		
		if (listaNonConformita != null && listaNonConformita.size() > 0){
			Iterator i = listaNonConformita.iterator();
			while (i.hasNext()){
				String element = (String) i.next();
				int elementInt = Integer.parseInt(element);
				this.listaNonConformita.add(elementInt);
			}
		}
	}

	public ArrayList<Integer> getListaProvvedimenti() {
		return listaProvvedimenti;
	}

//	public void setListaProvvedimenti(ArrayList<Integer> listaProvvedimenti) {
//		this.listaProvvedimenti = listaProvvedimenti;
//	}
//	
	
	
	
	
	public void setListaProvvedimenti(String[] nonConformitaString) {
		
		ArrayList<String> nonConformita = new ArrayList( Arrays.asList( nonConformitaString ) );
		
		if (nonConformita != null && nonConformita.size() > 0){
			Iterator i = nonConformita.iterator();
			while (i.hasNext()){
				String element = (String) i.next();
				int elementInt = Integer.parseInt(element);
				this.listaProvvedimenti.add(elementInt);
			}
		}
	}
	
	public void setListaProvvedimenti(ArrayList<String> listaProvvedimenti) {
		
		if (listaProvvedimenti != null && listaProvvedimenti.size() > 0){
			Iterator i = listaProvvedimenti.iterator();
			while (i.hasNext()){
				String element = (String) i.next();
				int elementInt = Integer.parseInt(element);
				this.listaProvvedimenti.add(elementInt);
			}
		}
	}
	
	

	public Timestamp getDataRicezioneComunicazioniEsterne() {
		return dataRicezioneComunicazioniEsterne;
	}


	public void setDataRicezioneComunicazioniEsterne(Timestamp dataRicezioneComunicazioniEsterne) {
		this.dataRicezioneComunicazioniEsterne = dataRicezioneComunicazioniEsterne;
	}
	
	public void setDataRicezioneComunicazioniEsterne(String dataComunicazione) {
		this.dataRicezioneComunicazioniEsterne =   DateUtils.parseDateStringNew(dataComunicazione, "dd/MM/yyyy");
	}


	public String getNoteRicezioneComunicazioniEsterne() {
		return noteRicezioneComunicazioniEsterne;
	}


	public void setNoteRicezioneComunicazioniEsterne(String noteRicezioneComunicazioniEsterne) {
		this.noteRicezioneComunicazioniEsterne = noteRicezioneComunicazioniEsterne;
	}


//	protected void salvaDestinatari(Connection db, CapoUnivoco thisCapo) {
//
//		Iterator i = listaDestinatariComunicazioni.iterator();
//
//		String addDestinatario = "INSERT INTO mu_comunicazioni_destinatari("
//				+ " id_capo, id_destinatario,  id_partita, id_seduta) " + " VALUES (?, ?, ?, ?)";
//
//		while (i.hasNext()) {
//			try {
//
//				PreparedStatement pst = db.prepareStatement(addDestinatario);
//				int k = 0;
//				pst.setInt(++k, thisCapo.getId());
//				pst.setInt(++k, (Integer) i.next());
//				pst.setInt(++k, thisCapo.getIdPartita());
//				pst.setInt(++k, thisCapo.getIdSeduta());
//
//				pst.execute();
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	protected void salvaNonConformita(Connection db, CapoUnivoco thisCapo) {

		Iterator i = listaNonConformita.iterator();

		String addConformita = "INSERT INTO mu_comunicazioni_non_conformita_rilevate( "
				+ "id_capo, id_non_conformita, id_partita, id_seduta)" + " VALUES (?, ?, ?, ?)";

		while (i.hasNext()) {
			try {

				PreparedStatement pst = db.prepareStatement(addConformita);
				int k = 0;
				pst.setInt(++k, thisCapo.getId());
				pst.setInt(++k, (Integer) i.next());
				pst.setInt(++k, thisCapo.getIdPartita());
				pst.setInt(++k, thisCapo.getIdSeduta());

				pst.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected void salvaProvvedimenti(Connection db, CapoUnivoco thisCapo) {
		Iterator i = listaProvvedimenti.iterator();

		String addProvvedimento = "INSERT INTO mu_comunicazioni_provvedimenti( "
				+ " id_capo, id_provvedimento, id_partita, id_seduta) " + " VALUES (?, ?, ?, ?)";

		while (i.hasNext()) {
			try {

				PreparedStatement pst = db.prepareStatement(addProvvedimento);
				int k = 0;
				pst.setInt(++k, thisCapo.getId());
				pst.setInt(++k, (Integer) i.next());
				pst.setInt(++k, thisCapo.getIdPartita());
				pst.setInt(++k, thisCapo.getIdSeduta());

				pst.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	protected void getNonConformita(Connection db, int idCapo) {

		//Iterator i = listaNonConformita.iterator();

		String selectConformita = "select id_non_conformita from mu_comunicazioni_non_conformita_rilevate where id_capo = ? ";

			try {

				PreparedStatement pst = db.prepareStatement(selectConformita);
				int k = 0;
				pst.setInt(++k, idCapo);
				pst.execute();
				
				
				ResultSet rs = pst.executeQuery();
				
				while (rs.next()){
					listaNonConformita.add(rs.getInt("id_non_conformita"));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	

	protected void getProvvedimenti(Connection db, int idCapo) {
		Iterator i = listaProvvedimenti.iterator();

		String selectProvvedimento = "select id_provvedimento from  mu_comunicazioni_provvedimenti where id_capo = ? ";
				
		try {

			PreparedStatement pst = db.prepareStatement(selectProvvedimento);
			int k = 0;
			pst.setInt(++k, idCapo);
			pst.execute();
			
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				listaProvvedimenti.add(rs.getInt("id_provvedimento"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	

