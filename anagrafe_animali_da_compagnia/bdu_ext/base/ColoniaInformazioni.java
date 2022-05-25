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
		this.totalePresunto = DatabaseUtils.parseBoolean(totaleFPresunto);
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
		this.totalePresunto = DatabaseUtils.parseBoolean(totaleMPresunto);
	}

	public String getNomeVeterinario() {
		return nomeVeterinario;
	}

	public void setNomeVeterinario(String nomeVeterinario) {
		this.nomeVeterinario = nomeVeterinario;
	}

	public ColoniaInformazioni() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean insert(Connection db) throws SQLException {

	//	System.out.println("insert informazioni");

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
			//			System.out.println(nameToUpperCase);
					}
					metodoSet.invoke(info,
							(metodoGet.invoke(lp) != null) ? metodoGet.invoke(
									lp).toString() : null);
				}

			} catch (Exception e) {
			//	System.out.println(metodoGet + "   " + metodoSet + "   "
				//		+ nameToUpperCase + " Non trovato ");
				// e.printStackTrace();
			}

		}

		info.getInformazioni(db);

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
	}

}
