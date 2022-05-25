/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.base;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.modules.mu_wkf.base.Path;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Articolo17 extends GenericBean {

	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_articolo_17";

	// STATI POSSIBILI ARTICOLO 17

	public static final int idStatoAperto = 1;
	public static final int idStatoChiuso = 2;

	@Column(columnName = "id", columnType = INT, table = nome_tabella)
	private int id = -1;
	@Column(columnName = "id_esercente", columnType = INT, table = nome_tabella)
	private int idEsercente = -1;
	@Column(columnName = "nome_esercente", columnType = STRING, table = nome_tabella)
	private String nomeEsercente = "";
	@Column(columnName = "data_creazione", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp dataCreazione;
	@Column(columnName = "id_stato", columnType = INT, table = nome_tabella)
	private int idStato = -1;
	@Column(columnName = "entered", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp entered;
	@Column(columnName = "enteredby", columnType = INT, table = nome_tabella)
	private int enteredby = -1;
	@Column(columnName = "modified", columnType = TIMESTAMP, table = nome_tabella)
	private Timestamp modified;
	@Column(columnName = "modifiedby", columnType = INT, table = nome_tabella)
	private int modifiedby = -1;
	@Column(columnName = "id_macello", columnType = INT, table = nome_tabella)
	private int idMacello = -1;
	@Column(columnName = "id_seduta", columnType = INT, table = nome_tabella)
	private int idSeduta = -1;
	@Column(columnName = "descrizione", columnType = STRING, table = nome_tabella)
	private String descrizione = "";
	@Column(columnName = "progressivo", columnType = INT, table = nome_tabella)
	private int progressivo = -1;
	@Column(columnName = "anno", columnType = INT, table = nome_tabella)
	private int anno = -1;
	
	
	private int giorniAperturaArticolo = 0;

	private ArrayList<CapoUnivoco> listaCapi = new ArrayList<CapoUnivoco>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEsercente() {
		return idEsercente;
	}

	public void setIdEsercente(int idEsercente) {
		this.idEsercente = idEsercente;
	}

	public void setIdEsercente(String idEsercente) {
		if (idEsercente != null && !("").equals(idEsercente))
			this.idEsercente = Integer.parseInt(idEsercente);
	}

	public String getNomeEsercente() {
		return nomeEsercente;
	}

	public void setNomeEsercente(String nomeEsercente) {
		this.nomeEsercente = nomeEsercente;
	}

	public int getIdMacello() {
		return idMacello;
	}

	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public int getEnteredby() {
		return enteredby;
	}

	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public int getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}

	public ArrayList<CapoUnivoco> getListaCapi() {
		return listaCapi;
	}

	public void setListaCapi(ArrayList<CapoUnivoco> listaCapi) {
		this.listaCapi = listaCapi;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getIdSeduta() {
		return idSeduta;
	}

	public void setIdSeduta(int idSeduta) {
		this.idSeduta = idSeduta;
	}

	public void setIdSeduta(String idSeduta) {

		if (idSeduta != null && !("").equals(idSeduta))
			this.idSeduta = Integer.parseInt(idSeduta);
	}

	public int getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	
	


	public int getGiorniAperturaArticolo() {
		return giorniAperturaArticolo;
	}

	public void setGiorniAperturaArticolo(int giorniAperturaArticolo) {
		this.giorniAperturaArticolo = giorniAperturaArticolo;
	}

	public Articolo17() {
		super();
	}

	public Articolo17(int idArticolo, Connection con) {

		String query = "select * from " + nome_tabella + " where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, idArticolo);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				loadResultSet(rs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ArrayList<Step> listaStep = new ArrayList<Step>();

		// listaSteps = Step.buildListByIdPath(this.id, con);

	}

	public void loadResultSet(ResultSet res) throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		HashMap<String, Integer> campi = new HashMap<String, Integer>();
		int k = 0;

		for (Field f : Articolo17.class.getDeclaredFields()) {
			Column column = f.getAnnotation(Column.class);
			if (column != null) {
				System.out.println(column.columnName());
				// if (column.table() == null ||
				// (nome_tabella).equals(column.table()) )
				campi.put(column.columnName(), column.columnType());
			}
		}

		Method[] m = this.getClass().getMethods();

		Iterator it = campi.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());

			// for( int i = 0; i < campi.length; i++ )
			// {
			String field = (String) pairs.getKey();
			Method getter = null;
			Method setter = null;
			for (int j = 0; j < m.length; j++) {
				String met = m[j].getName();
				if (met.equalsIgnoreCase("GET" + field.replaceAll("_", ""))
						|| met.equalsIgnoreCase("IS" + field.replaceAll("_", ""))) {
					getter = m[j];
				} else if (met.equalsIgnoreCase("SET" + field.replaceAll("_", ""))) {
					if (!(((Integer) pairs.getValue()) == INT && m[j].getParameterTypes()[0] == String.class)
							&& !(((Integer) pairs.getValue()) == TIMESTAMP && m[j].getParameterTypes()[0] == String.class)
							&& !(((Integer) pairs.getValue()) == DOUBLE && m[j].getParameterTypes()[0] == String.class))
						setter = m[j];
				}
			}

			if ((getter != null) && (setter != null) && (field != null)) {
				Object o = null;

				switch (((Integer) pairs.getValue())) {
				case INT:
					o = res.getInt(field);
					break;
				case STRING:
					o = res.getString(field);
					break;
				case BOOLEAN:
					o = res.getBoolean(field);
					break;
				case TIMESTAMP:
					o = res.getTimestamp(field);
					break;
				case DATE:
					o = res.getDate(field);
					break;
				case FLOAT:
					o = res.getFloat(field);
					break;
				case DOUBLE:
					o = res.getDouble(field);
					break;
				}

				setter.invoke(this, o);

			}
		}

		this.recuperaCapi(null);
		
		GregorianCalendar cal=(GregorianCalendar) GregorianCalendar.getInstance();
		cal.setTime(this.getEntered());
		GregorianCalendar c=new GregorianCalendar() ;
		giorniAperturaArticolo =  DateUtils.getDaysBetween(cal, c );

		// }
	}

	public Articolo17 store(Connection db, ActionContext context) throws Exception

	{
		try {

			this.entered = new Timestamp(System.currentTimeMillis());
			this.modified = this.entered;
			calcolaCodiceIdentificativo(db);

			id = DatabaseUtils.getNextSeq(db, context, "mu_articolo_17", "id");

			String sql = "INSERT INTO " + nome_tabella + "( ";

			// Field[] f = this.getClass().getDeclaredFields();
			// String[] campi = new
			// String[RichiestaIstopatologico.class.getFields().length];
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : Articolo17.class.getDeclaredFields()) {
				Column column = f.getAnnotation(Column.class);
				if (column != null) {
					System.out.println(column.columnName());
					if (column.table() == null || (nome_tabella).equals(column.table()))
						campi.put(column.columnName(), column.columnType());
				}
			}

			Method[] m = this.getClass().getMethods();
			Vector<Method> v = new Vector<Method>();
			Vector<Integer> v2 = new Vector<Integer>();
			boolean firstField = true;

			Iterator it = campi.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				// for( int i = 0; i < campi.length; i++ )
				// {
				String field = (String) pairs.getKey();

				for (int j = 0; j < m.length; j++) {
					String met = m[j].getName();
					if (
					// !field.equalsIgnoreCase("id")
					// &&
					(met.equalsIgnoreCase("GET" + field.replaceAll("_", "")) || met.equalsIgnoreCase("IS"
							+ field.replaceAll("_", "")))) {
						v.add(m[j]);
						v2.add((Integer) pairs.getValue());
						sql += (firstField) ? ("") : (",");
						firstField = false;
						sql += " " + field;
					}
				}

			}

			sql += " ) VALUES (";
			firstField = true;

			for (int i = 0; i < v.size(); i++) {
				{
					sql += (firstField) ? ("") : (",");
					sql += " ?";
					firstField = false;
				}
			}

			sql += " )";

			PreparedStatement stat = db.prepareStatement(sql);

			for (int i = 1; i <= v.size(); i++) {
				Object o = v.elementAt(i - 1).invoke(this);

				if (o == null) {
					stat.setNull(i, v2.elementAt(i - 1));
				} else {
					switch (parseType(o.getClass())) {
					case INT:
						stat.setInt(i, (Integer) o);
						break;
					case STRING:
						String s = (String) o;
						s = s.replaceAll("u13", " ");
						s = s.replaceAll("u10", " ");
						s = s.replaceAll("\\r", " ");
						s = s.replaceAll("\\n", " ");
						
						stat.setString(i, s);
						break;
					case BOOLEAN:
						stat.setBoolean(i, (Boolean) o);
						break;
					case TIMESTAMP:
						stat.setTimestamp(i, (Timestamp) o);
						break;
					case DATE:
						stat.setDate(i, (Date) o);
						break;
					case FLOAT:
						stat.setFloat(i, (Float) o);
						break;
					case DOUBLE:
						stat.setDouble(i, (Double) o);
						break;
					}
				}
			}

			stat.execute();
			stat.close();

			this.salvaCapi(db);

			return this;
		} catch (Exception e) {
			System.out.println("Errore Capo.store ->" + e.getMessage());
			throw e;
		}

	}

	protected void salvaCapi(Connection db) throws SQLException {
		Iterator i = this.listaCapi.iterator();
		while (i.hasNext()) {
			CapoUnivoco thisCapo = (CapoUnivoco) i.next();
			this.salvaCapo(thisCapo, db);
		}
	}

	protected void recuperaCapi(Connection con) throws SQLException {

		boolean closeConnection = false;

		if (con == null) {
			con = GestoreConnessioni.getConnection();

		}

		String getCapi = "Select * from mu_articolo_17_lista_capi where id_articolo_17 = ?";
		PreparedStatement pst = con.prepareStatement(getCapi);
		pst.setInt(1, id);

		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			CapoUnivoco thisCapo = new CapoUnivoco(con, rs.getInt("id_capo"), true, false);
			this.listaCapi.add(thisCapo);
		}

		if (closeConnection)
			GestoreConnessioni.freeConnection(con);
	}

	protected void salvaCapo(CapoUnivoco capo, Connection con) throws SQLException {
		String insertCapo = "INSERT INTO mu_articolo_17_lista_capi(" + "id_articolo_17, id_capo, data_macellazione)"
				+ " VALUES (?, ?, ?)";

		PreparedStatement pst = con.prepareStatement(insertCapo);
		int i = 0;
		pst.setInt(++i, this.getId());
		pst.setInt(++i, capo.getId());
		pst.setTimestamp(++i, capo.getDataMacellazione());

		pst.execute();

	}

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

//	public static boolean checkEsistenza(int idEsercente, int idMacello) {
//		boolean esiste = false;
//		Connection db = null;
//		String check = "select * from mu_articolo_17 where id_esercente = ? and id_macello = ? and id_stato = ? ";
//		try {
//
//			db = GestoreConnessioni.getConnection();
//			PreparedStatement pst = db.prepareStatement(check);
//			pst.setInt(1, idEsercente);
//			pst.setInt(2, idMacello);
//			pst.setInt(3, idStatoAperto);
//			ResultSet rs = pst.executeQuery();
//			if (rs.next()) {
//				esiste = true;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			GestoreConnessioni.freeConnection(db);
//		}
//
//		return esiste;
//	}

	public static boolean checkEsistenza(int idEsercente, int idMacello, int idSeduta) {
		boolean esiste = false;
		Connection db = null;
		String check = "select * from mu_articolo_17 where id_esercente = ? and id_macello = ? and id_stato = ? and id_seduta = ? ";
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(check);
			pst.setInt(1, idEsercente);
			pst.setInt(2, idMacello);
			pst.setInt(3, idStatoAperto);
			pst.setInt(4, idSeduta);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				esiste = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}

		return esiste;
	}

	public void close(int idArticolo) {
		boolean ret = false;
		Connection db = null;
		String check = "update mu_articolo_17 set id_stato = ?  where id = ?";
		try {

			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(check);
			pst.setInt(1, idStatoChiuso);
			pst.setInt(2, idArticolo);
			ret = pst.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(db);
		}
		// return !ret;

	}

	private void calcolaCodiceIdentificativo(Connection db) throws SQLException {
		int anno_corrente = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new java.util.Date()));
		String sql = "select coalesce( max( progressivo ), 0 ) + 1 as num from mu_articolo_17 where "
				+ "id_macello = ? and anno = ?";
		PreparedStatement stat = db.prepareStatement(sql);
		stat.setInt(1, idMacello);
		stat.setInt(2, anno_corrente);
		ResultSet res = stat.executeQuery();

		if (res.next()) {
			this.progressivo = res.getInt("num");
			this.anno = anno_corrente;
		}

		stat.close();
	}

}
