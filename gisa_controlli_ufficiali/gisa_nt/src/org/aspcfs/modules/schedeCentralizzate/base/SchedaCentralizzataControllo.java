/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedeCentralizzate.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspcfs.utils.DatabaseUtils;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.actions.ActionContext;

public class SchedaCentralizzataControllo {
	
	Logger logger = Logger.getLogger("MainLogger");
	
	private int ticketId = -1;
	private int tipo = -1;
	private String ASL = "";
	
	private int id = -1;
	private String label = "";
	private String attributo = "";
	private String destinazione = "";
	private String ordine = "-1";
	private boolean enabled = false;
	
	private LinkedHashMap<String, String[]> listaElementi = new LinkedHashMap<String, String[]>();
	
	public String getASL() {
		return ASL;
	}

	public void setASL(String aSL) {
		ASL = aSL;
	}

	
	
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public void setTicketId(String ticketId) {
		if (ticketId!=null && !ticketId.equals("null") && !ticketId.equals(""))
		this.ticketId = Integer.parseInt(ticketId);
	}
	
	

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public void setTipo(String tipo) {
		if (tipo!=null && !tipo.equals("null") && !tipo.equals(""))
		this.tipo = Integer.parseInt(tipo);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if (id!=null && !id.equals("") && !id.equals("null"))
			this.id = Integer.parseInt(id);
		else
			this.id = -999;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAttributo() {
		return attributo;
	}

	public void setAttributo(String attributo) {
		this.attributo = attributo;
	}

	public String getOrdine() {
		return ordine;
	}

	public void setOrdine(String ordine) {
		this.ordine = ordine;
	}
	
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setEnabled(String enabled) {
		if (enabled!=null && !enabled.equals("") && !enabled.equals("null")){
		if (enabled.equals("on"))
			this.enabled = true;
		else
			this.enabled = false;
		}
	}
	
	public void popolaScheda(Connection db){
		
		queryMapping(db);
		
		}
	
	
	
	public void queryMapping(Connection db){
		String query = "Select * from cu_dati_mapping where tipo = ? and enabled = true order by ordine ASC";
		PreparedStatement pst = null;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(1, tipo);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		while (rs.next()) {
			int idFunzione = rs.getInt("id_funzione");
			queryFunzioni(db, idFunzione);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}	
		
	
	public void queryFunzioni(Connection db, int idFunzione){
		String query = "select * from cu_dati_funzioni where id = ?";
		PreparedStatement pst = null;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(1, idFunzione);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String funzione = rs.getString("chiamata_funzione");
			String label = rs.getString("label");
			querySelect(db, idFunzione, funzione, label);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void querySelect(Connection db, int idFunzione, String funzione, String label){
		String query = "select * from "+funzione;
		PreparedStatement pst = null;
		try {
			pst = db.prepareStatement(query);
		
		pst.setInt(1, ticketId);
		ResultSet rs = pst.executeQuery();
		if (rs.isBeforeFirst()){
			aggiungiInLista(label, "", "capitolo");
		}
		while (rs.next()) {
			//buildRecord(rs, idFunzione);
			buildRecord(rs);
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void buildRecord (ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		for (int i = 1; i<=size; i++){
			  String name = rsmd.getColumnLabel(i);
			 aggiungiInLista(name, String.valueOf(rs.getObject(i)), "");
		}
	}
	
	private void aggiungiInLista(String key, String value, String attributo){
		String[] sb = listaElementi.get(key);
		String[] valori = {value, attributo};
		if (sb == null)
		   listaElementi.put(key, valori);
		else{
			String[] valori2 = {sb[0] + "<br/><br/>" + value, attributo};
			listaElementi.put(key,valori2);
			}
	}
	
	
	public LinkedHashMap<String, String[]> getListaElementi() {
		return listaElementi;
	}


public String getDestinazione() {
	return destinazione;
}

public void setDestinazione(String destinazione) {
	this.destinazione = destinazione;
}


}
