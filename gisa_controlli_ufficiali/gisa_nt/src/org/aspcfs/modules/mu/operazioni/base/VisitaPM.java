/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.operazioni.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.modules.mu.base.CapoUnivoco;
import org.aspcfs.modules.mu.base.Organo;
import org.aspcfs.utils.DateUtils;

public class VisitaPM {
	
	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_macellazioni";
	
	
	@Column(columnName = "data_ricezione_esito_visita_pm", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataRicezioneEsitoVisitaPm = null;
	
	@Column(columnName = "id_patologia_rilevata_visita_pm", columnType = INT, table = nome_tabella)
	private int idPatologiaRilevataVisitaPm = -1;
	
	
	@Column(columnName = "causa_presunta_accertata_visita_pm", columnType = STRING, table = nome_tabella)
	private String causaPresuntaAccertataVisitaPm = null;
	
	
	@Column(columnName = "note_visita_pm", columnType = STRING, table = nome_tabella)
	private String noteVisitaPm = null;
	

	
//	//private ArrayList<Integer> listaEsito = new ArrayList<Integer>();
	private ArrayList<Integer> listaPatologieRilevate = new ArrayList<Integer>();
	
	
	private ArrayList<Organo> listaOrgani = new ArrayList<Organo>();


	public Timestamp getDataRicezioneEsitoVisitaPm() {
		return dataRicezioneEsitoVisitaPm;
	}


	public void setDataRicezioneEsitoVisitaPm(Timestamp dataRicezioneEsitoVisitaPm) {
		this.dataRicezioneEsitoVisitaPm = dataRicezioneEsitoVisitaPm;
	}
	
	
	public void setDataRicezioneEsitoVisitaPm(String dataVisita) {
		this.dataRicezioneEsitoVisitaPm =   DateUtils.parseDateStringNew(dataVisita, "dd/MM/yyyy");
	}


	public int getIdPatologiaRilevataVisitaPm() {
		return idPatologiaRilevataVisitaPm;
	}


	public void setIdPatologiaRilevataVisitaPm(int idPatologiaRilevataVisitaPm) {
		this.idPatologiaRilevataVisitaPm = idPatologiaRilevataVisitaPm;
	}


	public String getCausaPresuntaAccertataVisitaPm() {
		return causaPresuntaAccertataVisitaPm;
	}


	public void setCausaPresuntaAccertataVisitaPm(String causaPresuntaAccertataVisitaPm) {
		this.causaPresuntaAccertataVisitaPm = causaPresuntaAccertataVisitaPm;
	}


	public String getNoteVisitaPm() {
		return noteVisitaPm;
	}


	public void setNoteVisitaPm(String noteVisitaPm) {
		this.noteVisitaPm = noteVisitaPm;
	}
	
	
	
	
//	public ArrayList<Integer> getListaEsito() {
//		return listaEsito;
//	}




	public ArrayList<Integer> getListaPatologieRilevate() {
		return listaPatologieRilevate;
	}





//	public void setListaEsito(ArrayList<String> lista) {
//		
//		if (lista != null && lista.size() > 0){
//			Iterator i = lista.iterator();
//			while (i.hasNext()){
//				String element = (String) i.next();
//				int elementInt = Integer.parseInt(element);
//				this.listaEsito.add(elementInt);
//			}
//		}
//	}
	
	
//	
	public void setListaPatologieRilevate(ArrayList<String> lista) {
		
		if (lista != null && lista.size() > 0){
			Iterator i = lista.iterator();
			while (i.hasNext()){
				String element = (String) i.next();
				int elementInt = Integer.parseInt(element);
				this.listaPatologieRilevate.add(elementInt);
			}
		}
	}
	
	
//	protected void salvaEsiti(Connection db, CapoUnivoco thisCapo) {
//
//		Iterator i = listaEsito.iterator();
//
//		String addConformita = "INSERT INTO mu_visita_pm_esiti( "
//				+ "id_capo, id_esito, id_partita, id_seduta)" + " VALUES (?, ?, ?, ?)";
//
//		while (i.hasNext()) {
//			try {
//
//				PreparedStatement pst = db.prepareStatement(addConformita);
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
//
//	}

	protected void salvaPatologie(Connection db, CapoUnivoco thisCapo) {
		Iterator i = listaPatologieRilevate.iterator();

		String addProvvedimento = "INSERT INTO mu_visita_pm_patologie( "
				+ " id_capo, id_patologia, id_partita, id_seduta) " + " VALUES (?, ?, ?, ?)";

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
	
	
	
	
//	protected void getEsiti(Connection db, int idCapo) {
//
//		//Iterator i = listaNonConformita.iterator();
//
//		String selectEsiti = "select id_esito from mu_visita_pm_esiti where id_capo = ? ";
//
//			try {
//
//				PreparedStatement pst = db.prepareStatement(selectEsiti);
//				int k = 0;
//				pst.setInt(++k, idCapo);
//				pst.execute();
//				
//				
//				ResultSet rs = pst.executeQuery();
//				
//				while (rs.next()){
//					listaEsito.add(rs.getInt("id_esito"));
//				}
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	

	protected void getPatologie(Connection db, int idCapo) {
		Iterator i = listaPatologieRilevate.iterator();

		String selectProvvedimento = "select id_patologia from  mu_visita_pm_patologie where id_capo = ? ";
				
		try {

			PreparedStatement pst = db.prepareStatement(selectProvvedimento);
			int k = 0;
			pst.setInt(++k, idCapo);
			pst.execute();
			
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				listaPatologieRilevate.add(rs.getInt("id_patologia"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	
	
	public ArrayList<Organo> getListaOrgani() {
		return listaOrgani;
	}


	public void setListaOrgani(ArrayList<Organo> listaOrgani) {
		this.listaOrgani = listaOrgani;
	}
	
	
	public void salvaOrgani (Connection con) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException{
		Iterator i = listaOrgani.iterator();
		
		while (i.hasNext()){
			Organo thisOrgano = (Organo) i.next();
		//	thisOrgano.setId_seduta(this.getId);
			thisOrgano.store(con);
		}
	}

	
	public void getOrgani(Connection db, int idCapo)  {
		

		String selectProvvedimento = "select * from  mu_vp_organi_patologie where id_capo = ? ";
				
		try {

			PreparedStatement pst = db.prepareStatement(selectProvvedimento);
			int k = 0;
			pst.setInt(++k, idCapo);
			pst.execute();
			
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				Organo thisOrgano = new Organo();
				thisOrgano.loadResultSet(rs);
						listaOrgani.add(thisOrgano);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}



	@Column(columnName = "data_visita_pm", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataVisitaPm = null;
	@Column(columnName = "id_esito_pm", columnType = INT, table = nome_tabella)
	int idEsitoPm = -1;
	@Column(columnName = "progressivo_macellazione_pm", columnType = STRING, table = nome_tabella)
	private String progressivoMacellazionePm = "";
	@Column(columnName = "id_tipo_macellazione_pm", columnType = INT, table = nome_tabella)
	private int idTipoMacellazionePm = -1;
	@Column(columnName = "id_veterinario1_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario1Pm = -1;
	@Column(columnName = "id_veterinario2_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario2Pm = -1;
	@Column(columnName = "id_veterinario3_pm", columnType = INT, table = nome_tabella)
	private int idVeterinario3Pm = -1;
	
	
	
	


	public Timestamp getDataVisitaPm() {
		return dataVisitaPm;
	}

	public void setDataVisitaPm(Timestamp dataVisitaPm) {
		this.dataVisitaPm = dataVisitaPm;
	}

	public int getIdEsitoPm() {
		return idEsitoPm;
	}

	public void setIdEsitoPm(int idEsitoPm) {
		this.idEsitoPm = idEsitoPm;
	}

	public void setIdEsitoPm(String idEsito) {
		this.idEsitoPm = Integer.valueOf(idEsito);
	}
	
	public void setDataVisitaPm(String dataVisita) {
		this.dataVisitaPm =   DateUtils.parseDateStringNew(dataVisita, "dd/MM/yyyy");
	}

	public String getProgressivoMacellazionePm() {
		return progressivoMacellazionePm;
	}

	public void setProgressivoMacellazionePm(String progressivoMacellazionePm) {
		this.progressivoMacellazionePm = progressivoMacellazionePm;
	}

	public int getIdTipoMacellazionePm() {
		return idTipoMacellazionePm;
	}

	public void setIdTipoMacellazionePm(int idTipoMacellazionePm) {
		this.idTipoMacellazionePm = idTipoMacellazionePm;
	}
	
	public void setIdTipoMacellazionePm(String idTipoMacellazionePm) {
		this.idTipoMacellazionePm = Integer.parseInt(idTipoMacellazionePm);
	}

	public int getIdVeterinario1Pm() {
		return idVeterinario1Pm;
	}

	public void setIdVeterinario1Pm(int idVeterinario1Pm) {
		this.idVeterinario1Pm = idVeterinario1Pm;
	}
	
	public void setIdVeterinario1Pm(String idVeterinario1Pm) {
		if (idVeterinario1Pm != null && !("").equals(idVeterinario1Pm))
			this.idVeterinario1Pm = Integer.parseInt(idVeterinario1Pm);
	}

	public int getIdVeterinario2Pm() {
		return idVeterinario2Pm;
	}

	public void setIdVeterinario2Pm(int idVeterinario2Pm) {
		this.idVeterinario2Pm = idVeterinario2Pm;
	}
	
	public void setIdVeterinario2Pm(String idVeterinario2Pm) {
		if (idVeterinario2Pm != null && !("").equals(idVeterinario2Pm))
			this.idVeterinario2Pm = Integer.parseInt(idVeterinario2Pm);
	}

	public int getIdVeterinario3Pm() {
		return idVeterinario3Pm;
	}

	public void setIdVeterinario3Pm(int idVeterinario3Pm) {
		this.idVeterinario3Pm = idVeterinario3Pm;
	}
	
	public void setIdVeterinario3Pm(String idVeterinario3Pm) {
		if (idVeterinario3Pm != null && !("").equals(idVeterinario3Pm))
			this.idVeterinario3Pm = Integer.parseInt(idVeterinario3Pm);
	}
	
	
	

}
