/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.operazioni.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.aspcfs.modules.macellazioni.base.Column;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.darkhorseventures.framework.actions.ActionContext;

public class Macellazione {

	protected static final int INT = Types.INTEGER;
	protected static final int STRING = Types.VARCHAR;
	protected static final int DOUBLE = Types.DOUBLE;
	protected static final int FLOAT = Types.FLOAT;
	protected static final int TIMESTAMP = Types.TIMESTAMP;
	protected static final int DATE = Types.DATE;
	protected static final int BOOLEAN = Types.BOOLEAN;
	protected static final String nome_tabella = "mu_macellazioni";

	@Column(columnName = "id", columnType = INT, table = nome_tabella)
	private int id = -1;
	@Column(columnName = "data_inserimento", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataInserimento = null;
	@Column(columnName = "data_modifica", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataModifica = null;
	@Column(columnName = "id_utente_inserimento", columnType = INT, table = nome_tabella)
	private int idUtenteInserimento = -1;
	@Column(columnName = "id_utente_modifica", columnType = INT, table = nome_tabella)
	private int idUtenteModifica = -1;
	@Column(columnName = "id_path_macellazione", columnType = INT, table = nome_tabella)
	private int idPathMacellazione = -1;
	@Column(columnName = "id_capo", columnType = INT, table = nome_tabella)
	private int idCapo = -1;
	@Column(columnName = "id_seduta", columnType = INT, table = nome_tabella)
	private int idSeduta = -1;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}

	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}

	public int getIdUtenteModifica() {
		return idUtenteModifica;
	}

	public void setIdUtenteModifica(int idUtenteModifica) {
		this.idUtenteModifica = idUtenteModifica;
	}

	public int getIdPathMacellazione() {
		return idPathMacellazione;
	}

	public void setIdPathMacellazione(int idPathMacellazione) {
		this.idPathMacellazione = idPathMacellazione;
	}
	
	public void setIdPathMacellazione(String idPathMacellazione) {
		if (idPathMacellazione != null && !("").equals(idPathMacellazione))
			this.idPathMacellazione = Integer.valueOf(idPathMacellazione);
	}
	
	
	
	public int getIdCapo() {
		return idCapo;
	}

	public void setIdCapo(int idCapo) {
		this.idCapo = idCapo;
	}
	
	

	public int getIdSeduta() {
		return idSeduta;
	}

	public void setIdSeduta(int idSeduta) {
		this.idSeduta = idSeduta;
	}

	
	public void setIdSeduta(String idSeduta) {
		if (idSeduta != null && !("").equals(idSeduta))
			this.idSeduta = Integer.valueOf(idSeduta);
	}
	public Macellazione store(Connection db, ActionContext context, VisitaAMSemplificata visAM, 
			VisitaPMSemplificata visPM, Comunicazioni comunicazioni, MorteANM mantem,  VisitaAM visitaAm, VisitaPM visitaPm) throws Exception

	{
		try {
			
			
		} catch (Exception e) {
			System.out.println("Errore Capo.store ->" + e.getMessage());
			throw e;
		}

		return this;
	}
	
	
	
	
	public Macellazione getDettaglioMacellazioneByIdCapo(int idCapo, Connection con){
		String select = "select * from mu_macellazioni where id_capo = ?";
		
		try{
			PreparedStatement pst = con.prepareStatement(select);
			pst.setInt(1, idCapo);
			
			ResultSet rs = pst.executeQuery();
				
			if( rs.next() )
			{
				this.loadResultSet(rs);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return this;
	}
	
	
	
	public void loadResultSet(ResultSet rs) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
	}
	
	
	
//	public VisitaAMSemplificata getVisitaAmSemplificata(){
//		VisitaAMSemplificata visita = new VisitaAMSemplificata();
//		
//		switch (this.idPathMacellazione) {
//		case MacellazioneLiberoConsumo.idTipologiaMacellazioneLC:{
//			visita = ((MacellazioneLiberoConsumo) this).getVa();
//			break;
//		}
//		default:
//			break;
//		}
//	
//	
//	return visita;
//	
//	
//	}
//	
//	
//	public VisitaPMSemplificata getVisitaPmSemplificata(){
//		VisitaPMSemplificata visita = new VisitaPMSemplificata();
//		
//		switch (this.idPathMacellazione) {
//		case MacellazioneLiberoConsumo.idTipologiaMacellazioneLC:{
//			visita = ((MacellazioneLiberoConsumo) this).getVp();
//			break;
//	w 	}
//		default:
//			break;
//		}
//	
//	
//	return visita;
//	
//	
//	}
	
	
	protected static int parseType(Class<?> type) {
		int ret = -1;

		String name = type.getSimpleName();

		if (name.equalsIgnoreCase("int") || name.equalsIgnoreCase("integer")) {
			ret = INT;
		} else if (name.equalsIgnoreCase("string")) {
			ret = STRING;
		} else if (name.equalsIgnoreCase("double")) {
			ret = DOUBLE;
		} else if (name.equalsIgnoreCase("float")) {
			ret = FLOAT;
		} else if (name.equalsIgnoreCase("timestamp")) {
			ret = TIMESTAMP;
		} else if (name.equalsIgnoreCase("date")) {
			ret = DATE;
		} else if (name.equalsIgnoreCase("boolean")) {
			ret = BOOLEAN;
		}

		return ret;
	}
	
	
	//UNIMPLEMENTED METHODS
	
	
	public Comunicazioni getComunicazioni() {
		throw new NotImplementedException();
	}

	public MorteANM getMorteAM() {
		throw new NotImplementedException();
	}
	
	public VisitaAMSemplificata getVa() {
		throw new NotImplementedException();
	}


	public VisitaPMSemplificata getVp() {
		throw new NotImplementedException();
	}

	
	public VisitaAM getVisitaAm() {
		throw new NotImplementedException();
	}

	public VisitaPM getVisitaPm() {
		throw new NotImplementedException();
	}
	

}
