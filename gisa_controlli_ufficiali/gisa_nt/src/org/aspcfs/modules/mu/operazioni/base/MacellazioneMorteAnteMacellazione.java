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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.modules.mu.base.CapoUnivoco;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class MacellazioneMorteAnteMacellazione extends Macellazione {

	private final static int idTipologiaMacellazioneMA = 2;
	private Comunicazioni comunicazioni = new Comunicazioni();
	private MorteANM morteAM =  new MorteANM();

	public Comunicazioni getComunicazioni() {
		return comunicazioni;
	}

	public void setComunicazioni(Comunicazioni comunicazioni) {
		this.comunicazioni = comunicazioni;
	}

	public MorteANM getMorteAM() {
		return morteAM;
	}

	public void setMorteAM(MorteANM morteAM) {
		this.morteAM = morteAM;
	}

	public MacellazioneMorteAnteMacellazione store(Connection db, ActionContext context, VisitaAMSemplificata visAM,
			VisitaPMSemplificata visPM, Comunicazioni comunicazioni, MorteANM mantem,  VisitaAM visitaAm, VisitaPM visitaPm) throws Exception

	{

		Method[] mante = null;
		Method[] comunic = null;
		try {

			this.setComunicazioni(comunicazioni);
			this.setMorteAM(mantem);

			// this.setId(1);
			this.setId(DatabaseUtils.getNextSeq(db, context, "mu_macellazioni", "id"));
			// id = DatabaseUtils.getNextSeq(db, context, "mu_articolo_17",
			// "id");

			String sql = "INSERT INTO  mu_macellazioni ( ";

			// Field[] f = this.getClass().getDeclaredFields();
			// String[] campi = new
			// String[RichiestaIstopatologico.class.getFields().length];
			HashMap<String, Integer> campi = new HashMap<String, Integer>();
			int k = 0;

			for (Field f : MacellazioneMorteAnteMacellazione.class.getDeclaredFields()) {
				// System.out.println("NOME: " +f.getName());
				Column column = f.getAnnotation(Column.class);
				// System.out.println("TIPO: " +f.getType());

				if (column != null) {
					// System.out.println(column.columnName());
					if (column.table() == null || (nome_tabella).equals(column.table()))
						campi.put(column.columnName(), column.columnType());

				} else if (f.getType().isInstance(new MorteANM())) {
					System.out.println("TIPO TROVATO!!! " + f.getType());
					for (Field f1 : MorteANM.class.getDeclaredFields()) {
						System.out.println(f1.getName());
						Column column1 = f1.getAnnotation(Column.class);
						if (column1 != null) {
							System.out.println("NOME COLONNA:  " + column1.columnName());
							if (column1.table() != null)
								campi.put(column1.columnName(), column1.columnType());
						}
					}

					mante = MorteANM.class.getDeclaredMethods();
				} else if (f.getType().isInstance(new Comunicazioni())) {

					System.out.println("TIPO TROVATO!!! " + f.getType());
					for (Field f1 : Comunicazioni.class.getDeclaredFields()) {
						System.out.println(f1.getName());
						Column column1 = f1.getAnnotation(Column.class);
						if (column1 != null) {
							System.out.println("NOME COLONNA:  " + column1.columnName());
							if (column1.table() != null)
								campi.put(column1.columnName(), column1.columnType());
						}
					}

					comunic = Comunicazioni.class.getDeclaredMethods();
				}
			}

			for (Field f : MacellazioneLiberoConsumo.class.getSuperclass().getDeclaredFields()) {
				System.out.println(f.getName());
				Column column = f.getAnnotation(Column.class);

				if (column != null) {
					System.out.println(column.columnName());
					if (column.table() == null || (nome_tabella).equals(column.table()))
						campi.put(column.columnName(), column.columnType());
				}
			}

			Method[] m1 = this.getClass().getMethods();
			//
			Method[] m2 = (Method[]) ArrayUtils.addAll(comunic, mante);
			//
			//
			Method[] m = (Method[]) ArrayUtils.addAll(m1, m2);

			// Method[] m = this.getClass().getMethods();

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
				Object o = null;
				try {
					// o = v.elementAt(i - 1).invoke(this);
					((Method) (v.elementAt(i - 1))).getDeclaringClass();
					for (int s = 0; s < m.length; s++) {
						Method thisM = (Method) m[s];
						if (thisM.getReturnType() == (((Method) (v.elementAt(i - 1))).getDeclaringClass())) {
							o = v.elementAt(i - 1).invoke(thisM.invoke(this));
						}
					}
				} catch (IllegalArgumentException e) {
					try {
						o = v.elementAt(i - 1).invoke(this);
					} catch (IllegalArgumentException e1) {
						// o = v.elementAt(i - 1).invoke(this.getVp());
					}

				}

				if (o == null) {
					stat.setNull(i, v2.elementAt(i - 1));
				} else {
					switch (super.parseType(o.getClass())) {
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

			CapoUnivoco thisCapo = new CapoUnivoco(db, this.getIdCapo());

			// this.getComunicazioni().salvaDestinatari(db, thisCapo);
			this.getComunicazioni().salvaNonConformita(db, thisCapo);
			this.getComunicazioni().salvaProvvedimenti(db, thisCapo);

			return this;
		} catch (Exception e) {
			System.out.println("Errore Capo.store ->" + e.getMessage());
			throw e;
		}

	}

	public void loadResultSet(ResultSet res) throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Method[] am = null;
		Method[] pm = null;

		HashMap<String, Integer> campi = new HashMap<String, Integer>();
		int k = 0;

		for (Field f : MacellazioneMorteAnteMacellazione.class.getDeclaredFields()) {
			Column column = f.getAnnotation(Column.class);
			if (column != null) {
				System.out.println(column.columnName());
				// if (column.table() == null ||
				// (nome_tabella).equals(column.table()) )
				campi.put(column.columnName(), column.columnType());
			}

			else if (f.getType().isInstance(new Comunicazioni())) {
				System.out.println("TIPO TROVATO!!! " + f.getType());
				for (Field f1 : Comunicazioni.class.getDeclaredFields()) {
					System.out.println(f1.getName());
					Column column1 = f1.getAnnotation(Column.class);
					if (column1 != null) {
						System.out.println("NOME COLONNA:  " + column1.columnName());
						if (column1.table() != null)
							campi.put(column1.columnName(), column1.columnType());
					}
				}

				am = Comunicazioni.class.getDeclaredMethods();
			} else if (f.getType().isInstance(new MorteANM())) {

				System.out.println("TIPO TROVATO!!! " + f.getType());
				for (Field f1 : MorteANM.class.getDeclaredFields()) {
					System.out.println(f1.getName());
					Column column1 = f1.getAnnotation(Column.class);
					if (column1 != null) {
						System.out.println("NOME COLONNA:  " + column1.columnName());
						if (column1.table() != null)
							campi.put(column1.columnName(), column1.columnType());
					}
				}

				pm = MorteANM.class.getDeclaredMethods();
			}
		}

		for (Field f : MacellazioneMorteAnteMacellazione.class.getSuperclass().getDeclaredFields()) {
			System.out.println(f.getName());
			Column column = f.getAnnotation(Column.class);

			if (column != null) {
				System.out.println(column.columnName());
				if (column.table() == null || (nome_tabella).equals(column.table()))
					campi.put(column.columnName(), column.columnType());
			}
		}

		Method[] m1 = this.getClass().getMethods();
		//
		Method[] m2 = (Method[]) ArrayUtils.addAll(am, pm);
		//
		//
		Method[] m = (Method[]) ArrayUtils.addAll(m1, m2);

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
							&& !(((Integer) pairs.getValue()) == DOUBLE && m[j].getParameterTypes()[0] == String.class)
							&& !(((Integer) pairs.getValue()) == BOOLEAN && m[j].getParameterTypes()[0] == String.class))
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

				try {
					// o = v.elementAt(i - 1).invoke(this);
					((Method) (setter)).getDeclaringClass();
					for (int s = 0; s < m.length; s++) {
						Method thisM = (Method) m[s];
						if (thisM.getReturnType() == (((Method) (setter)).getDeclaringClass())) {
							setter.invoke(thisM.invoke(this), o);
						}

					}

				} catch (IllegalArgumentException e) {
					try {
						setter.invoke(this, o);
					} catch (IllegalArgumentException e1) {
						e.printStackTrace();
					}

					// setter.invoke(this, o);

				}
			}

			// }
		}
		
		

	}
	
	
	public Macellazione getDettaglioMacellazioneByIdCapo(int idCapo, Connection con){
		
		
		try{

			super.getDettaglioMacellazioneByIdCapo(idCapo, con);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//CapoUnivoco thisCapo = new CapoUnivoco(con, this.getIdCapo());

		// this.getComunicazioni().salvaDestinatari(db, thisCapo);
		this.getComunicazioni().getNonConformita(con, this.getIdCapo());
		this.getComunicazioni().getProvvedimenti(con, this.getIdCapo());
		
		return this;
	}

}
