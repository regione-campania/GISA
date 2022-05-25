/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu_wkf.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.aspcfs.modules.macellazioni.base.Column;
import org.aspcfs.modules.macellazioni.base.RichiestaIstopatologico;

import com.darkhorseventures.framework.beans.GenericBean;

public class Step extends GenericBean {

	private static final int INT = Types.INTEGER;
	private static final int STRING = Types.VARCHAR;
	private static final int DOUBLE = Types.DOUBLE;
	private static final int FLOAT = Types.FLOAT;
	private static final int TIMESTAMP = Types.TIMESTAMP;
	private static final int DATE = Types.DATE;
	private static final int BOOLEAN = Types.BOOLEAN;
	private static final String nome_tabella = "mu_steps";

	@Column(columnName = "id", columnType = INT, table = nome_tabella)
	private int id = -1;
	@Column(columnName = "descrizione", columnType = STRING, table = nome_tabella)
	private String descrizione = "";
	@Column(columnName = "flag_is_required", columnType = BOOLEAN, table = nome_tabella)
	private boolean flagIsRequired = false;
	
//	private ArrayList<StepFields> listaCampi = new ArrayList<StepFields>(); GESTIONE CON TABELLA mu_step_fields, ABBANDONATA SU RICHIESTA DI DANTE 23/03/2015
	
	@Column(columnName = "jsp_page_to_include", columnType = STRING, table = nome_tabella)
	private String jspPageToInclude = "";   //GESTIONE CON PAGINA JSP PER OGNI STEP COME DA RICHIESTA DI DANTE 23/03/2015

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isFlagIsRequired() {
		return flagIsRequired;
	}

	public void setFlagIsRequired(boolean flagIsRequired) {
		this.flagIsRequired = flagIsRequired;
	}
	
	

//	public ArrayList<StepFields> getListaCampi() {
//		return listaCampi;
//	}
//
//	public void setListaCampi(ArrayList<StepFields> listaCampi) {
//		this.listaCampi = listaCampi;
//	}
	
	

	public Step() {
		super();
	}

	public String getJspPageToInclude() {
		return jspPageToInclude;
	}

	public void setJspPageToInclude(String jspPageToInclude) {
		this.jspPageToInclude = jspPageToInclude;
	}

	public Step(int idStep, Connection con) {

		String query = "select * from mu_steps where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, idStep);
			ResultSet rs = pst.executeQuery();

			loadResultSet(rs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public Step(int idStep, Connection con, boolean buildFields) {

		String query = "select * from mu_steps where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, idStep);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next())
				loadResultSet(rs);
			
//			if (buildFields)
//				listaCampi = StepFields.buildListByIdStep(this.id, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadResultSet(ResultSet res) throws SQLException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		HashMap<String, Integer> campi = new HashMap<String, Integer>();
		int k = 0;

		for (Field f : Step.class.getDeclaredFields()) {
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

	}
	
	
	public static ArrayList<Step> buildListByIdPath (int idPath, Connection db){
		 ArrayList<Step> lista = new ArrayList<Step>();
		 
		 String select = "select * from path_steps_relations where id_path = ?";
		 try {
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, idPath);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				Step thisStep = new Step(rs.getInt("id_step"), db);
				lista.add(thisStep);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return lista;
		
	}
	
	
	public static ArrayList<Step> buildListByIdPath (int idPath, Connection db, boolean buildFields){
		 ArrayList<Step> lista = new ArrayList<Step>();
		 
		 String select = "select * from mu_path_steps_relazione where id_path = ? order by ordine";
		 try {
			PreparedStatement pst = db.prepareStatement(select);
			pst.setInt(1, idPath);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				Step thisStep = new Step(rs.getInt("id_step"), db, buildFields);
				
				lista.add(thisStep);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return lista;
		
	}

}
