/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.accounts.base;

/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ComuniAnagrafica {

	private String value = null;
	private String label = null;
	private int idComune = -1;

	private String codice = null;
	private String descrizione = null;
	private int idAsl;
	private String descrizioneAsl;
	public static final int CODICE_REGIONE_CAMPANIA = 15;
	public static final int IN_REGIONE = 1;
	public static final int FUORI_REGIONE = 2;
	public static final int TUTTI = 3;

	public ComuniAnagrafica() {
	}
	
	

	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public int getIdComune() {
		return idComune;
	}



	public void setIdComune(int idComune) {
		this.idComune = idComune;
	}
	
	public void setIdComune(String idComune) {
		this.idComune = new Integer(idComune);
	}



	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public String getDescrizioneAsl() {
		return descrizioneAsl != null
				&& !descrizioneAsl.equalsIgnoreCase("null") ? descrizioneAsl
				: "";
	}

	public void setDescrizioneAsl(String descrizioneAsl) {
		this.descrizioneAsl = descrizioneAsl;
	}

	public ComuniAnagrafica(Connection db, String comune) throws SQLException {
		queryRecord(db, comune);
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public void queryRecord(Connection db, String comune) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT codice FROM comuni c WHERE c.comune = ? and trashed is false ");
		st = db.prepareStatement(sql.toString());
		st.setString(1, comune);
		rs = st.executeQuery();

		if (rs.next()) {
			codice = rs.getString(1);
		}
		rs.close();
		st.close();
	}

	public ArrayList<ComuniAnagrafica> buildList(Connection db, int asl,
			int tipoSelezione) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList<ComuniAnagrafica> lista = new ArrayList<ComuniAnagrafica>();
		if (tipoSelezione == this.IN_REGIONE) {
			if (asl != -1)
				sql
						.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat and c.trashed is false and asl.code = ? order by nome");
			else
				sql
						.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat and c.trashed is false order by nome");

		} else {
			if (tipoSelezione == this.FUORI_REGIONE) {
				sql
						.append("SELECT id,nome FROM comuni1  where  cod_regione != '"
								+ CODICE_REGIONE_CAMPANIA + "' and c.trashed is false order by nome");

			} else {
				if (asl != -1)
					sql
							.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat and c.trashed is false and asl.code = ? union SELECT id,nome FROM comuni1  where  trashed is false and  cod_regione != '"
									+ CODICE_REGIONE_CAMPANIA
									+ "' order by nome");
				else
					sql
							.append("SELECT id,nome FROM comuni1 c left join lookup_site_id asl on c.codiceistatasl =asl.codiceistat and c.trashed is false order by nome");

			}
		}

		st = db.prepareStatement(sql.toString());
		if (tipoSelezione == this.IN_REGIONE) {
			if (asl != -1) {
				st.setInt(1, asl);
				// st.setString(2, "15");//regione campania
			}
		} else {
			if (tipoSelezione == this.TUTTI) {
				if (asl != -1) {
					st.setInt(1, asl);
				}
			}
		}
		rs = st.executeQuery();

		while (rs.next()) {
			codice = rs.getString(1);
			descrizione = rs.getString(2);
			ComuniAnagrafica c = new ComuniAnagrafica();
			c.setCodice(codice);
			c.setDescrizione(descrizione);
			lista.add(c);
		}
		rs.close();
		st.close();
		return lista;
	}

	public ArrayList<ComuniAnagrafica> buildList_all(Connection db, int asl)
			throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList<ComuniAnagrafica> lista = new ArrayList<ComuniAnagrafica>();

		sql.append("SELECT id,nome FROM comuni1 c where c.trashed is false order by nome");

		st = db.prepareStatement(sql.toString());

		rs = st.executeQuery();

		while (rs.next()) {
			codice = rs.getString(1);
			descrizione = rs.getString(2);
			ComuniAnagrafica c = new ComuniAnagrafica();
			c.setCodice(codice);
			c.setDescrizione(descrizione);
			lista.add(c);
		}
		rs.close();
		st.close();
		return lista;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public HashMap<String, Object> getHashmap()
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Field[] campi = this.getClass().getDeclaredFields();
		Method[] metodi = this.getClass().getMethods();
		for (int i = 0; i < campi.length; i++) {
			String nomeCampo = campi[i].getName();

			for (int j = 0; j < metodi.length; j++) {
				if (metodi[j].getName().equalsIgnoreCase("GET" + nomeCampo)) {

					map.put(nomeCampo, metodi[j].invoke(this));
				}

			}

		}

		return map;

	}

	public ArrayList<ComuniAnagrafica> getComuni(Connection db, int idProvincia) {
		ArrayList<ComuniAnagrafica> listaComuni = new ArrayList<ComuniAnagrafica>();

		String sql = "select id,nome from comuni1 where trashed is false ";
		if (idProvincia != -1) {
			sql += " and cod_provincia::int = ?";
		} else {

			sql += " and cod_regione::int = " + this.CODICE_REGIONE_CAMPANIA;
		}

		sql += "order by nome ";
		try {
			PreparedStatement pst = db.prepareStatement(sql);
			if (idProvincia != -1) {
				pst.setInt(1, idProvincia);
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int codice = rs.getInt("id");
				String descrizione = rs.getString("nome");
				ComuniAnagrafica p = new ComuniAnagrafica();
				p.setCodice("" + codice);
				p.setDescrizione(descrizione);
				listaComuni.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaComuni;

	}

	public ArrayList<ComuniAnagrafica> getComuni(Connection db,
			int idProvincia, String nome, String inRegione, int idAsl, String estero) {
		ArrayList<ComuniAnagrafica> listaComuni = new ArrayList<ComuniAnagrafica>();

		String sql = "select id,nome,asl.description,asl.code from comuni1 "
				+ " left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true  "
				+ " left join lookup_province p on p.code = comuni1.cod_provincia::int  "
				+ " where  comuni1.trashed is false ";
		if (idProvincia != -1) {
			sql += " and comuni1.cod_provincia::int = ? ";
		}
		
		if (estero!=null && estero.equalsIgnoreCase("no")) 
		{
			sql += " and p.cod_nazione is not null ";
		}

		if ((inRegione != null && inRegione.equalsIgnoreCase("si"))) {
			sql += " and comuni1.cod_regione::int = "
					+ ComuniAnagrafica.CODICE_REGIONE_CAMPANIA;
		}else {
			sql += " and comuni1.cod_regione::int <> "
					+ ComuniAnagrafica.CODICE_REGIONE_CAMPANIA;
		}

		sql += " and nome ilike ? ";

		if (idAsl > -1 && (inRegione == null || inRegione.equalsIgnoreCase("si"))) {
			sql += " and asl.code::int = ? ";
		}

		sql += " order by nome limit 30 ";
		try {
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql);
			if (idProvincia != -1) {
				pst.setInt(++i, idProvincia);
				pst.setString(++i, "" + nome + "%");
			} else {
				pst.setString(++i, "" + nome + "%");
			}

			if (idAsl > -1 && (inRegione == null || inRegione.equalsIgnoreCase("si"))) {
				pst.setInt(++i, idAsl);
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int codice = rs.getInt("id");
				String descrizione = rs.getString("nome");
				ComuniAnagrafica p = new ComuniAnagrafica();
				p.setIdAsl(rs.getInt("code"));
				p.setDescrizioneAsl(rs.getString("description"));
				p.setCodice("" + codice);
				p.setDescrizione(descrizione);
				listaComuni.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaComuni;

	}
	
	//Funzione per popolamento aree di testo, per ora usate in registrazione di modifica residenza prop/det	
	public ArrayList<ComuniAnagrafica> getComuniTesto(Connection db,
			int idProvincia, String nome) {
		ArrayList<ComuniAnagrafica> listaComuni = new ArrayList<ComuniAnagrafica>();

		String sql = "select id,nome  from comuni1 "
				+ ""
				+ " where trashed is false ";
		
		if (idProvincia != -1) {
			sql += " and cod_provincia::int = ?";
		}



		sql += " and nome ilike ? ";

		sql += "order by nome";
		try {
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql);
			if (idProvincia != -1) {
				pst.setInt(++i, idProvincia);
				pst.setString(++i, "%" + nome + "%");
			} else {
				pst.setString(++i, "%" + nome + "%");
			}


			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				
				value = rs.getString(2);
			      label = rs.getString(2); //Si pu� modificare
			      idComune = rs.getInt(1);
				
			

				

				int codice = rs.getInt("id");
				
				ComuniAnagrafica p = new ComuniAnagrafica();
			
				p.setValue(value);
				p.setLabel(label);
				p.setIdComune(idComune);
				listaComuni.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaComuni;

	}

	public ArrayList<ComuniAnagrafica> getComuniByIdAsl(Connection db, int idAsl) {
		ArrayList<ComuniAnagrafica> listaComuni = new ArrayList<ComuniAnagrafica>();

		String sql = "select id,nome,asl.description,asl.code from comuni1 "
				+ "left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true "
				+ " where trashed is false  ";
		//SE ASL E' >0 E' IN REGIONE, ALTRIMENTI CARICA TUTTI I COMUNI FUORI REGIONE
		if (idAsl>0)
			sql=sql+" and asl.code = ?";
		else
			sql= sql+ " and asl.code is null ";
		sql= sql+ " order by nome ";
		
		try {
			PreparedStatement pst = db.prepareStatement(sql);
			if (idAsl>0)
				pst.setInt(1, idAsl);
			if (System.getProperty("DEBUG") != null) 
				System.out.println(pst);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int codice = rs.getInt("id");
				String descrizione = rs.getString("nome");
				ComuniAnagrafica p = new ComuniAnagrafica();
				p.setIdAsl(rs.getInt("code"));
				p.setDescrizioneAsl(rs.getString("description"));
				p.setCodice("" + codice);
				p.setDescrizione(descrizione);
				listaComuni.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaComuni;

	}

}
