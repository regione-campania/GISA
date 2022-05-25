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
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;

public class Furetto extends Animale {

	public final static int idSpecie = 3;

//	private Operatore detentore;
//	private int idDetentore = -1;
	private int idFuretto = -1;
	//private int idTaglia = 1; //INIZIALIZZARE A VALORE DI DEFAULT
	//private String tatuaggio = "";
//	private Timestamp dataSecondoMicrochip;
	
	
/*	public String getTatuaggio() {
		return tatuaggio;
	}








	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}*/








/*	public Timestamp getDataSecondoMicrochip() {
		return dataSecondoMicrochip;
	}








	public void setDataSecondoMicrochip(Timestamp dataSecondoMicrochip) {
		this.dataSecondoMicrochip = dataSecondoMicrochip;
	}
	

	public void setDataSecondoMicrochip(String dataSecondoMicrochip) {
		this.dataSecondoMicrochip = DatabaseUtils
		.parseDateToTimestamp(dataSecondoMicrochip);
	}*/

	//private int idDetentoreUltimoTrasferimentoFRegione = -1; //Conservo l'id del detentore precedente a ultimo trasferimento fuori regione
	
//	private int idDetentoreUltimoTrasferimentoFStato = -1; //Conservo l'id del detentore precedente a ultimo trasferimento fuori stato

	public Furetto() {
		super();
		super.setIdTaglia (1);
	}
	
	






/*	public Operatore getDetentore() {
		return detentore;
	}

	public void setDetentore(Operatore detentore) {
		this.detentore = detentore;
	}

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}*/

	public int getIdFuretto() {
		return idFuretto;
	}

	public void setIdFuretto(int idFuretto) {
		this.idFuretto = idFuretto;
	}

	public void setIdFuretto(String idFuretto) {
		this.idFuretto = new Integer(idFuretto).intValue();
	}
	
	
	
	
/*
	public int getIdTaglia() {
		return idTaglia;
	}

	public void setIdTaglia(int idTaglia) {
		this.idTaglia = idTaglia;
	}
	
	public void setIdTaglia(String idTaglia) {
		this.idTaglia = new Integer(idTaglia).intValue();
	}*/

/*	public int getIdDetentoreUltimoTrasferimentoFRegione() {
		return idDetentoreUltimoTrasferimentoFRegione;
	}

	public void setIdDetentoreUltimoTrasferimentoFRegione(
			int idDetentoreUltimoTrasferimentoFRegione) {
		this.idDetentoreUltimoTrasferimentoFRegione = idDetentoreUltimoTrasferimentoFRegione;
	}
	
	

	public int getIdDetentoreUltimoTrasferimentoFStato() {
		return idDetentoreUltimoTrasferimentoFStato;
	}



	public void setIdDetentoreUltimoTrasferimentoFStato(
			int idDetentoreUltimoTrasferimentoFStato) {
		this.idDetentoreUltimoTrasferimentoFStato = idDetentoreUltimoTrasferimentoFStato;
	}*/








	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			

			super.insert(db);

			// idAnimale = super.getIdAnimale() ;

			idFuretto = DatabaseUtils
					.getNextSeqPostgres(db, "furetto_id_furetto_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO furetto(id_furetto,id_animale ");

//			if (idDetentore > -1) {
//				sql.append(",id_detentore ");
//			}
			
/*			if (idTaglia > -1) {
				sql.append(",id_taglia ");
			}*/
			
/*			if (dataSecondoMicrochip != null) {
				sql.append(",data_secondo_microchip");
			}
			
			if (tatuaggio != null  && !("").equals(tatuaggio)) {
				sql.append(",secondo_microchip");
			}*/

			sql.append(")");

			sql.append("VALUES ( ?,?  ");

//			if (idDetentore > -1) {
//				sql.append(",?");
//			}
/*			
			if (idTaglia > -1) {
				sql.append(",?");
			}*/
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

			pst.setInt(++i, idFuretto);
			pst.setInt(++i, this.getIdAnimale());
//
//			if (idDetentore > -1) {
//				pst.setInt(++i, idDetentore);
//			}
			
/*			if (idTaglia > -1) {
				pst.setInt(++i, idTaglia);
			}*/
			
/*			if (dataSecondoMicrochip != null) {
				pst.setTimestamp(++i, dataSecondoMicrochip);
			}
			
			if (tatuaggio != null  && !("").equals(tatuaggio)) {
				pst.setString(++i, tatuaggio);
			}*/

			pst.execute();
			pst.close();

			this.idFuretto = DatabaseUtils.getCurrVal(db, "furetto_id_furetto_seq",
					idFuretto);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public Furetto(Connection db, int idAnimale) throws SQLException {

		super(db, idAnimale);

		PreparedStatement pst = db
				.prepareStatement("Select * from furetto where id_animale = ?");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
//			if (rs.getInt("id_detentore") > -1)
//				try {
//					{
//						detentore = new Operatore();
//						detentore.queryRecordOperatorebyIdLineaProduttiva(db,
//								rs.getInt("id_detentore"));
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			this.setDetentore(detentore);

		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	protected void buildRecord(ResultSet rs) throws SQLException {
		idFuretto = rs.getInt("id_furetto");
//		idDetentore = rs.getInt("id_detentore");
//		idDetentoreUltimoTrasferimentoFRegione = rs.getInt("id_detentore_ultimo_trasferimento_a_regione");
//		idDetentoreUltimoTrasferimentoFStato = rs.getInt("id_detentore_ultimo_trasferimento_a_stato");
		//idTaglia = rs.getInt("id_taglia");
	//	tatuaggio = rs.getString("secondo_microchip");
		//dataSecondoMicrochip = rs.getTimestamp("data_secondo_microchip");

	}

	public void updateStato(Connection con) throws Exception {

		super.updateStato(con);
/*		PreparedStatement pst = con
				.prepareStatement("Update furetto set  where id_furetto = "
						+ this.getIdFuretto());
		int i = 0;
	//	pst.setInt(++i, idDetentore);
//		pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
//		pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);
		pst.setString(++i, tatuaggio);
		pst.setTimestamp(++i, dataSecondoMicrochip);
		int resultCount = pst.executeUpdate();
		pst.close();*/

	}
	
	public int update(Connection con) {
		int result = -1;
		try {
			super.update(con);
			PreparedStatement pst = con
					.prepareStatement("UPDATE furetto "
							+ "SET secondo_microchip=?, data_secondo_microchip=? "
							+ " WHERE id_furetto = ?");
			int i = 0;
		//	pst.setString(++i, this.getTatuaggio());
		//	pst.setTimestamp(++i, this.getDataSecondoMicrochip());
			pst.setInt(++i, this.getIdFuretto());

			result = pst.executeUpdate();

			pst.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

}
