/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class NotificaPagatore {
	
	Logger logger = Logger.getLogger("MainLogger");

	private int id = -1;
	private int idSanzione = -1;
	private int idPagatore = -1;
	private String tipoNotifica = null;
	private String dataNotifica = null;
	private boolean dataScadenzaProrogata = false;
	private boolean notificaAggiornata = false;

	public NotificaPagatore(){
		
	}
	

	public NotificaPagatore(Connection db, int idSanzione, int idPagatore) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from pagopa_sanzioni_pagatori_notifiche where id_sanzione = ? and id_pagatore = ? and trashed_date is null");
		pst.setInt(1, idSanzione);
		pst.setInt(2, idPagatore);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
		}
		
	}


	private void buildRecord(ResultSet rs) throws SQLException {
		this.id= rs.getInt("id");
		this.idSanzione=  rs.getInt("id_sanzione");
		this.idPagatore=rs.getInt("id_pagatore");
		this.tipoNotifica=rs.getString("tipo_notifica");
		this.dataNotifica=rs.getString("data_notifica");
		this.dataScadenzaProrogata=rs.getBoolean("data_scadenza_prorogata");
		this.notificaAggiornata=rs.getBoolean("notifica_aggiornata");

		}


	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdSanzione() {
		return idSanzione;
	}


	public void setIdSanzione(int idSanzione) {
		this.idSanzione = idSanzione;
	}


	public int getIdPagatore() {
		return idPagatore;
	}


	public void setIdPagatore(int idPagatore) {
		this.idPagatore = idPagatore;
	}


	public String getTipoNotifica() {
		return tipoNotifica;
	}


	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica; 
	}


	public String getDataNotifica() {
		return dataNotifica;
	}


	public void setDataNotifica(String dataNotifica) {
		this.dataNotifica = dataNotifica;
	}


	public boolean isDataScadenzaProrogata() {
		return dataScadenzaProrogata;
	}


	public void setDataScadenzaProrogata(boolean dataScadenzaProrogata) {
		this.dataScadenzaProrogata = dataScadenzaProrogata;
	}

	public static void insert(Connection db, int idPagatore, int idSanzione, String tipoNotifica, String dataNotifica, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into pagopa_sanzioni_pagatori_notifiche(id_sanzione, id_pagatore, tipo_notifica, data_notifica, enteredby) values (?, ?, ?, ?, ?);");

		int i = 0;

		pst.setInt(++i, idSanzione);
		pst.setInt(++i, idPagatore);
		pst.setString(++i, tipoNotifica);
		pst.setString(++i, dataNotifica);
		pst.setInt(++i, userId);
		pst.executeUpdate();
		
	}


	public static void update(Connection db, int idPagatore, int idSanzione, String tipoNotifica, String dataNotifica, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update pagopa_sanzioni_pagatori_notifiche set modified = now(), modifiedby = ?, tipo_notifica = ?, data_notifica = ?, notifica_aggiornata = true, note_hd = concat(note_hd, '; Modifica in data ', now(), ' Vecchi valori: ', row_to_json(pagopa_sanzioni_pagatori_notifiche)) where id_sanzione = ? and id_pagatore = ? and trashed_date is null;");

		int i = 0;
		pst.setInt(++i, userId);
		pst.setString(++i, tipoNotifica);
		pst.setString(++i, dataNotifica);

		pst.setInt(++i, idSanzione);
		pst.setInt(++i, idPagatore);
		
		pst.executeUpdate();		
	}
	
	public static void restore(Connection db, int idPagatore, int idSanzione, String tipoNotifica, String dataNotifica, int userId) throws SQLException { 
		PreparedStatement pst = db.prepareStatement("update pagopa_sanzioni_pagatori_notifiche set modified = now(), modifiedby = ?, tipo_notifica = ?, data_notifica = ?, notifica_aggiornata = false, note_hd = concat(note_hd, '; Modifica in data ', now(), ' Vecchi valori: ', row_to_json(pagopa_sanzioni_pagatori_notifiche)) where id_sanzione = ? and id_pagatore = ? and trashed_date is null;");

		int i = 0;
		pst.setInt(++i, userId);
		pst.setString(++i, tipoNotifica);
		pst.setString(++i, dataNotifica);

		pst.setInt(++i, idSanzione);
		pst.setInt(++i, idPagatore);
		
		pst.executeUpdate();		
	}
	
	public static void delete(Connection db, int idSanzione, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update pagopa_sanzioni_pagatori_notifiche set trashed_date = now() where id_sanzione = ? and trashed_date is null;");

		int i = 0;
		pst.setInt(++i, idSanzione);
		
		pst.executeUpdate();		  
	}


	public boolean isNotificaAggiornata() {
		return notificaAggiornata;
	}


	public void setNotificaAggiornata(boolean notificaAggiornata) {
		this.notificaAggiornata = notificaAggiornata;
	}
	
}


