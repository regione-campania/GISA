/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;

public class Gatto extends Animale {

	public final static int idSpecie = 2;

//	private Operatore detentore;
//	private int idDetentore = -1;
	private int idGatto = -1;
	//private int idTaglia = -1;
	
	private Timestamp dataVaccino;
	private String numeroLottoVaccino;
	private boolean flagCattura = false;
	private int idComuneCattura;
	private Timestamp dataCattura;
	private String luogoCattura;
	private String verbaleCattura;
	private boolean flagRiCattura = false;
	boolean flagReimmesso = false;
	private boolean flagFuoriRegione;
	private int idRegioneProvenienza = -1;
	private boolean flagSindacoFuoriRegione;

	
	
	public boolean isFlagRiCattura() {
		return flagRiCattura;
	}

	public void setFlagRiCattura(boolean flagRiCattura) {
		this.flagRiCattura = flagRiCattura;
	}

	public boolean isFlagReimmesso() {
		return flagReimmesso;
	}

	public void setFlagReimmesso(boolean flagReimmesso) {
		this.flagReimmesso = flagReimmesso;
	}

	public Timestamp getDataVaccino() {
		return dataVaccino;
	}

	public void setDataVaccino(Timestamp dataVaccino) {
		this.dataVaccino = dataVaccino;
	}

	public void setDataVaccino(String dataVaccino) {
		this.dataVaccino = DatabaseUtils.parseDateToTimestamp(dataVaccino);
	}

	public String getNumeroLottoVaccino() {
		return numeroLottoVaccino;
	}

	public void setNumeroLottoVaccino(String numeroLottoVaccino) {
		this.numeroLottoVaccino = numeroLottoVaccino;
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
	
	public boolean isFlagFuoriRegione() {
		return flagFuoriRegione;
	}

	public void setFlagFuoriRegione(boolean flagFuoriRegione) {
		this.flagFuoriRegione = flagFuoriRegione;
	}
	
	public void setFlagFuoriRegione(String flagFuoriRegione) {
		this.flagFuoriRegione = DatabaseUtils.parseBoolean(flagFuoriRegione);;
	}
	


	public int getIdRegioneProvenienza() {
		return idRegioneProvenienza;
	}

	public void setIdRegioneProvenienza(int idRegioneProvenienza) {
		this.idRegioneProvenienza = idRegioneProvenienza;
	}
	
	public void setIdRegioneProvenienza(String idRegioneProvenienza) {
		this.idRegioneProvenienza = Integer.valueOf(idRegioneProvenienza);
	}

	public boolean isFlagSindacoFuoriRegione() {
		return flagSindacoFuoriRegione;
	}

	public void setFlagSindacoFuoriRegione(boolean flagSindacoFuoriRegione) {
		this.flagSindacoFuoriRegione = flagSindacoFuoriRegione;
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

	//private int idDetentoreUltimoTrasferimentoFRegione = -1; //Conservo l'id del detentore precedente a ultimo trasferimento fuori regione
	
	//private int idDetentoreUltimoTrasferimentoFStato = -1; //Conservo l'id del detentore precedente a ultimo trasferimento fuori stato

	public Gatto() {
		super();
	}
	
	





/*
	public Operatore getDetentore() {
		return detentore;
	}

	public void setDetentore(Operatore detentore) {
		this.detentore = detentore;
	}*/

/*	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}
*/
	public int getIdGatto() {
		return idGatto;
	}

	public void setIdGatto(int idGatto) {
		this.idGatto = idGatto;
	}

	public void setIdGatto(String idGatto) {
		this.idGatto = new Integer(idGatto).intValue();
	}
	
	
	
	

/*	public int getIdTaglia() {
		return idTaglia;
	}

	public void setIdTaglia(int idTaglia) {
		this.idTaglia = idTaglia;
	}
	
	public void setIdTaglia(String idTaglia) {
		this.idTaglia = new Integer(idTaglia).intValue();
	}*/

//	public int getIdDetentoreUltimoTrasferimentoFRegione() {
//		return idDetentoreUltimoTrasferimentoFRegione;
//	}
//
//	public void setIdDetentoreUltimoTrasferimentoFRegione(
//			int idDetentoreUltimoTrasferimentoFRegione) {
//		this.idDetentoreUltimoTrasferimentoFRegione = idDetentoreUltimoTrasferimentoFRegione;
//	}
//	
//	
//
//	public int getIdDetentoreUltimoTrasferimentoFStato() {
//		return idDetentoreUltimoTrasferimentoFStato;
//	}
//
//
//
//	public void setIdDetentoreUltimoTrasferimentoFStato(
//			int idDetentoreUltimoTrasferimentoFStato) {
//		this.idDetentoreUltimoTrasferimentoFStato = idDetentoreUltimoTrasferimentoFStato;
//	}








	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			

			super.insert(db);

			// idAnimale = super.getIdAnimale() ;

			idGatto = DatabaseUtils
					.getNextSeqPostgres(db, "gatto_id_gatto_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO gatto(id_gatto,id_animale, flag_fuori_regione, flag_sindaco_fuori_regione ");

/*			if (idDetentore > -1) {
				sql.append(",id_detentore ");
			}
			*/
/*			if (idTaglia > -1) {
				sql.append(",id_taglia ");
			}*/
			
/*			if (dataSecondoMicrochip != null) {
				sql.append(",data_secondo_microchip");
			}
			
			if (tatuaggio != null  && !("").equals(tatuaggio)) {
				sql.append(",secondo_microchip");
			}*/
			
			if (dataVaccino != null) {
				sql.append(",vaccino_data");
			}
			if (numeroLottoVaccino != null) {
				sql.append(",vaccino_numero_lotto");
			}
			
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
			
			if (idRegioneProvenienza > 0) {
				sql.append(",id_regione_provenienza");
			}
			
			
			
			
			sql.append(", flag_catturato");
			

			sql.append(")");

			sql.append("VALUES ( ?,?, ?, ?  ");

/*			if (idDetentore > -1) {
				sql.append(",?");
			}*/
/*			
			if (idTaglia > -1) {
				sql.append(",?");
			}*/
			
			if (dataVaccino != null) {
				sql.append(",?");
			}
			if (numeroLottoVaccino != null) {
				sql.append(",?");
			}
			
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
			
			if (idRegioneProvenienza > 0) {
				sql.append(",?");
			}
			
			sql.append(",?");
/*			
			if (dataSecondoMicrochip != null) {
				sql.append(", ?");
			}
			
			if (tatuaggio != null  && !("").equals(tatuaggio)) {
				sql.append(", ?");
			}*/

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idGatto);
			pst.setInt(++i, this.getIdAnimale());
			pst.setBoolean(++i,  flagFuoriRegione);
			pst.setBoolean(++i,  flagSindacoFuoriRegione);
/*			if (idDetentore > -1) {
				pst.setInt(++i, idDetentore);
			}*/
			
/*			if (idTaglia > -1) {
				pst.setInt(++i, idTaglia);
			}*/
			
/*			if (dataSecondoMicrochip != null) {
				pst.setTimestamp(++i, dataSecondoMicrochip);
			}
			
			if (tatuaggio != null  && !("").equals(tatuaggio)) {
				pst.setString(++i, tatuaggio);
			}*/
			
			if (dataVaccino != null) {
				pst.setTimestamp(++i, dataVaccino);
			}
			if (numeroLottoVaccino != null) {
				pst.setString(++i, numeroLottoVaccino);
			}

			
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
			
			if (idRegioneProvenienza > 0) {
				pst.setInt(++i, idRegioneProvenienza);
			}
			
			pst.setBoolean(++i, flagCattura);
			
		//	System.out.println("AGGIUNGI GATTO " +pst.toString());
			pst.execute();
			pst.close();

			this.idGatto = DatabaseUtils.getCurrVal(db, "gatto_id_gatto_seq",
					idGatto);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public Gatto(Connection db, int idAnimale) throws SQLException {

		super(db, idAnimale);
		
		PreparedStatement pst = db
				.prepareStatement("Select * from gatto where id_animale = ?");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
/*			if (rs.getInt("id_detentore") > -1)
				try {
					{
						detentore = new Operatore();
						detentore.queryRecordOperatorebyIdLineaProduttiva(db,
								rs.getInt("id_detentore"));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
*/
		//	this.setDetentore(detentore);

		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		idGatto = rs.getInt("id_gatto");
	//	idDetentore = rs.getInt("id_detentore");
	//	idDetentoreUltimoTrasferimentoFRegione = rs.getInt("id_detentore_ultimo_trasferimento_a_regione");
	//	idDetentoreUltimoTrasferimentoFStato = rs.getInt("id_detentore_ultimo_trasferimento_a_stato");
		//idTaglia = rs.getInt("id_taglia");
		dataVaccino = rs.getTimestamp("vaccino_data");
		numeroLottoVaccino = rs.getString("vaccino_numero_lotto");
	//	tatuaggio = rs.getString("secondo_microchip");
		//dataSecondoMicrochip = rs.getTimestamp("data_secondo_microchip");
		idComuneCattura = rs.getInt("id_comune_cattura");
		dataCattura = rs.getTimestamp("data_cattura");
		luogoCattura = rs.getString("luogo_cattura");
		verbaleCattura = rs.getString("verbale_cattura");
		flagCattura = rs.getBoolean("flag_catturato");
		try {
			rs.findColumn("id_regione_provenienza");
			idRegioneProvenienza = rs.getInt("id_regione_provenienza");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("flag_fuori_regione");
			flagFuoriRegione = rs.getBoolean("flag_fuori_regione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("flag_sindaco_fuori_regione");
			flagSindacoFuoriRegione =  rs.getBoolean("flag_sindaco_fuori_regione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
	

	}

	public void updateStato(Connection con) throws Exception {

		super.updateStato(con);
		PreparedStatement pst = con
				.prepareStatement("Update gatto set   vaccino_data=?, "
							+ " vaccino_numero_lotto=?, flag_reimmesso=? where id_gatto = "
						+ this.getIdGatto());
		int i = 0;
		//pst.setInt(++i, idDetentore);
		//pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
	//	pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);
		pst.setTimestamp(++i, dataVaccino);
		pst.setString(++i, numeroLottoVaccino);
		pst.setBoolean(++i, flagReimmesso);
/*		pst.setString(++i, tatuaggio);
		pst.setTimestamp(++i, dataSecondoMicrochip);*/
		int resultCount = pst.executeUpdate();
		pst.close();

	}
	
	public int update(Connection con) {
		int result = -1;
		try {
			super.update(con);
			PreparedStatement pst = con
					.prepareStatement("UPDATE gatto "
							+ "SET secondo_microchip=?, data_secondo_microchip=? "
							+ " WHERE id_gatto = ?");
			int i = 0;
		//	pst.setString(++i, this.getTatuaggio());
		//	pst.setTimestamp(++i, this.getDataSecondoMicrochip());
			pst.setInt(++i, this.getIdGatto());

			result = pst.executeUpdate();

			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	
//	public boolean isRandagio(Connection db){
//		boolean isRandagio = false;
//	try{
//		 //e' randagio se sta in colonia o se ha detentore tipo sindaco
//		
//			Operatore detentore = new Operatore();
//			detentore.queryRecordOperatorebyIdLineaProduttiva(db,  this.getIdDetentore());
//			Stabilimento stab = (Stabilimento) detentore.getListaStabilimenti().get(0);
//			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
//			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia || lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco){
//				isRandagio = true;			
//		}
//		
//	}catch (Exception e) {
//		e.printStackTrace();
//	}
//	
//	return isRandagio;
//	}
	
	
	
	
}
