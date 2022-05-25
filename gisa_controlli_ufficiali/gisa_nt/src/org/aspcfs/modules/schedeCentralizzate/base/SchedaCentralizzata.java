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

public class SchedaCentralizzata {
	
	Logger logger = Logger.getLogger("MainLogger");
	private int orgId = -1;
	private int stabId = -1;
	private int altId = -1;
	private int addressId1 = -1;
	private int addressId2 = -1;
	private int addressId3 = -1;
	private int idCampoEsteso = -1;
	private int tipo = -1;
	private String ASL = "";
	
	private int id = -1;
	private String label = "";
	private String sql_campo = "";
	
	private String sql_origine ="";
	private String sql_condizione = "";
	private String attributo = "";
	private String destinazione = "";
	private String ordine = "-1";
	private boolean enabled = false;
	
	private String titolo = "Scheda";
	private boolean firmaData = false;
	
	private LinkedHashMap<String, String[]> listaElementi = new LinkedHashMap<String, String[]>();
	
	public String getASL() {
		return ASL;
	}

	public void setASL(String aSL) {
		ASL = aSL;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public void setOrgId(String orgId) {
		if (orgId!=null && !orgId.equals("null") && !orgId.equals(""))
		this.orgId = Integer.parseInt(orgId);
	}

	public int getStabId() {
		return stabId;
	}

	public void setStabId(int stabId) {
		this.stabId = stabId;
	}
	
	public void setStabId(String stabId) {
		if (stabId!=null && !stabId.equals("null") && !stabId.equals(""))
		this.stabId = Integer.parseInt(stabId);
	}
	
	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}
	
	public void setAltId(String altId) {
		if (altId!=null && !altId.equals("null") && !altId.equals(""))
		this.altId = Integer.parseInt(altId);
	}
	
	public int getAddressId1() {
		return addressId1;
	}

	public void setAddressId1(String addressId1) {
		if (addressId1!=null && !addressId1.equals("null") && !addressId1.equals(""))
		this.addressId1 = Integer.parseInt(addressId1);
	}
	public void setAddressId1(int addressId1) {
		this.addressId1 = addressId1;
	}

	public int getAddressId2() {
		return addressId2;
	}

	public void setAddressId2(String addressId2) {
		if (addressId2!=null && !addressId2.equals("null") && !addressId2.equals(""))
		this.addressId2 = Integer.parseInt(addressId2);
	}
	public void setAddressId2(int addressId2) {
		this.addressId2 = addressId2;
	}
	public int getAddressId3() {
		return addressId3;
	}

	public void setAddressId3(String addressId3) {
		if (addressId3!=null && !addressId3.equals("null") && !addressId3.equals(""))
		this.addressId3 = Integer.parseInt(addressId3);
	}
	public void setAddressId3(int addressId3) {
		this.addressId3 = addressId3;
	}

	
	public int getIdCampoEsteso() {
		return idCampoEsteso;
	}

	public void setIdCampoEsteso(int idCampoEsteso) {
		this.idCampoEsteso = idCampoEsteso;
	}
	
	public void setIdCampoEsteso(String idCampoEsteso) { 
		if (idCampoEsteso!=null && !idCampoEsteso.equals("null") && !idCampoEsteso.equals(""))
		this.idCampoEsteso = Integer.parseInt(idCampoEsteso);
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

	public String getSql_campo() {
		return sql_campo;
	}

	public void setSql_campo(String sql_campo) {
		this.sql_campo = sql_campo;
	}

	public String getSql_origine() {
		return sql_origine;
	}

	public void setSql_origine(String sql_origine) {
		this.sql_origine = sql_origine;
	}

	public String getSql_condizione() {
		return sql_condizione;
	}

	public void setSql_condizione(String sql_condizione) {
		this.sql_condizione = sql_condizione;
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
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public boolean isFirmaData() {
		return firmaData;
	}

	public void setFirmaData(boolean firmaData) {
		this.firmaData = firmaData;
	}

	
	public void popolaScheda(Connection db){
		
		PreparedStatement pst;
		PreparedStatement pst2;
		PreparedStatement pstScheda;
		String query = "";
		String label = "";
		String attributo = "";
		try {
			
			String queryScheda = "Select * from lookup_tipo_scheda_operatore where code = ? ";
			pstScheda = db.prepareStatement(queryScheda);
			pstScheda.setInt(1, tipo);
			ResultSet rsScheda = DatabaseUtils.executeQuery(db, pstScheda);
			if (rsScheda.next()) {
				this.titolo = rsScheda.getString("titolo");
				this.firmaData= rsScheda.getBoolean("firma_data");
			}
			
			String querySelect = "Select * from scheda_operatore_metadati where tipo_operatore = ? and enabled = true";
			
			if (destinazione!=null && !destinazione.equals("tutto"))
				querySelect+= " and (destinazione='' or destinazione = ?) ";
			querySelect += " order by ordine ASC";
			
			pst = db.prepareStatement(querySelect);
			pst.setInt(1, tipo);
			
			if (destinazione!=null && !destinazione.equals("tutto"))
				pst.setString(2, destinazione);
			
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				query = generaQuery (rs.getString("sql_campo"), rs.getString("sql_origine"), rs.getString("sql_condizione"));
				label = rs.getString("label");
				attributo = rs.getString("attributo");
				
				if (attributo!=null && attributo.equals("json"))
					query = "select row_to_json(t) from ("+query+") t";
				
				String value="";
				
				if(stabId>0 && "attivita".equalsIgnoreCase(label))
				{
					if (!query.equals("")){
						pst2 = fixStatement(query, db);
						ResultSet rs2 = pst2.executeQuery();
						
						while (rs2.next()){
							try {
								if (!value.equals(""))
									value=value+"<br/><br/>";
								label = rs2.getString(1);
								value =  rs2.getString(2);
								String[] valori = {value,attributo};
								listaElementi.put(label, valori);
								
							}	catch (PSQLException e1){}
					}
					}
					
					
				}
				else
				{
					if (!query.equals("")){
					pst2 = fixStatement(query, db);
					ResultSet rs2 = pst2.executeQuery();
					
					while (rs2.next()){
						try {
							if (!value.equals(""))
								value=value+"<br/><br/>";
							value = value+ rs2.getString(1);
						}	catch (PSQLException e1){}
				}
				}
				
				String[] valori = {value,attributo};
				listaElementi.put(label, valori);
				}
			
				if (attributo!=null && attributo.equalsIgnoreCase("ASL"))
					this.setASL(value);
			}	
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	System.out.println("Scheda centralizzata errore su query "+query);
			logger.info("Scheda centralizzata errore su query "+query);
			e.printStackTrace();
		} 
	}
	
	
	
	public LinkedHashMap<String, String[]> getListaElementi() {
		return listaElementi;
	}

		
	private Object trovaParametro (String campoDaCercare) {
		
		Field[]	f = this.getClass().getDeclaredFields();
	    Method[] m = this.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		Object o = null;
		
			for( int i = 0; i < f.length; i++ )
		    {
		        String field = f[i].getName();
		        for( int j = 0; j < m.length; j++ )
		        {
		            String met = m[j].getName();
		            if( field.equalsIgnoreCase(campoDaCercare) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
		            {
		                 v.add( m[j] );
		                 v2.add( f[i] );
		                 try {
							o = v.elementAt(0).invoke( this );
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                 
		            }
		        }
		        
		    }
			
		return o;	
			
		
		
	}
	
		
	private PreparedStatement fixStatement (String query, Connection db){
		ArrayList<Object> valori = new ArrayList<Object>();
		PreparedStatement pst = null;	
		Pattern pattern = Pattern.compile("#(.*?)#");
	    Matcher matcher = pattern.matcher(query);
	   
	    while (matcher.find()) {
	    	//System.out.println(matcher.group(1));
	    	String var = matcher.group(1);
	    	query = query.replace("#"+var+"#", "?");
	       	valori.add(trovaParametro(var));
	    	}
	 
		try {
			pst = db.prepareStatement(query);
		
		for (int i=0; i<valori.size(); i++)
			pst.setObject(i+1, valori.get(i));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
		
	}
	
	public String generaQuery (String campo, String origine, String condizione){
		if (campo==null || origine==null || condizione==null || campo.equals("") || origine.equals("") || condizione.equals(""))
			return "";
		
		String query = "";
		query = "SELECT "+campo+" FROM "+origine+" WHERE "+condizione;
		return query;
	}
	
public void dettaglioGestioneScheda(Connection db){
		
		PreparedStatement pst;
		String sql_campo = "";
		String sql_origine = "";
		String sql_condizione = "";
		String label = "";
		String attributo = "";
		String ordine = "";
		String id ="";
		String tipoOperatore = "";
		String enabled = "";
		String destinazione ="";
		try {
			pst = db.prepareStatement("Select * from scheda_operatore_metadati where tipo_operatore = ? order by enabled DESC, ordine ASC");
			pst.setInt(1, tipo);
			
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				id = rs.getString("id");
				ordine = rs.getString("ordine");
				tipoOperatore = rs.getString("tipo_operatore");
				sql_campo = rs.getString("sql_campo");
				sql_origine = rs.getString("sql_origine");
				sql_condizione =  rs.getString("sql_condizione");
				label = rs.getString("label");
				attributo = rs.getString("attributo");
				enabled = rs.getString("enabled");
				destinazione = rs.getString("destinazione");
				
				String[] valori = {tipoOperatore, sql_campo, sql_origine, sql_condizione, label, attributo, ordine, enabled, destinazione};
				listaElementi.put(id, valori);
			
				
			}	
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	System.out.println("Scheda centralizzata errore su query "+query);
			e.printStackTrace();
		} 
	}
	
public SchedaCentralizzata (Connection db, ActionContext context, int i){
	setId(context.getRequest().getParameter("id_"+i));
	setLabel(context.getRequest().getParameter("label_"+i));
	setSql_campo(context.getRequest().getParameter("sql_campo_"+i));
	setSql_origine(context.getRequest().getParameter("sql_origine_"+i));
	setSql_condizione(context.getRequest().getParameter("sql_condizione_"+i));
	setAttributo(context.getRequest().getParameter("attributo_"+i));
	setDestinazione(context.getRequest().getParameter("destinazione_"+i));
	setOrdine(context.getRequest().getParameter("ordine_"+i));
	setEnabled(context.getRequest().getParameter("enabled_"+i));
}

public SchedaCentralizzata() {
	// TODO Auto-generated constructor stub
}

public void update(Connection db){
	
	PreparedStatement pst;
	try {
		pst = db.prepareStatement("update scheda_operatore_metadati set label = ?, sql_campo = ?, sql_origine = ?, sql_condizione = ?, attributo = ?, destinazione = ?, ordine = ?, enabled  = ? where id = ?");
		pst.setString(1, label);
		pst.setString(2, sql_campo);
		pst.setString(3, sql_origine);
		pst.setString(4, sql_condizione);
		pst.setString(5, attributo);
		pst.setString(6, destinazione);
		pst.setString(7, ordine);
		pst.setBoolean(8, enabled);
		pst.setInt(9, id);
		pst.executeUpdate();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
	//	System.out.println("Scheda centralizzata errore su query "+query);
		e.printStackTrace();
	} 
}

public void insert(Connection db){
	
	PreparedStatement pst;
	try {
		pst = db.prepareStatement("insert into scheda_operatore_metadati (label, sql_campo, sql_origine, sql_condizione, attributo, destinazione, ordine, enabled, tipo_operatore) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, label);
		pst.setString(2, sql_campo);
		pst.setString(3, sql_origine);
		pst.setString(4, sql_condizione);
		pst.setString(5, attributo);
		pst.setString(6, destinazione);
		pst.setString(7, ordine);
		pst.setBoolean(8, enabled);
		pst.setInt(9, tipo);
		pst.execute();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
	//	System.out.println("Scheda centralizzata errore su query "+query);
		e.printStackTrace();
	} 
}

public String getDestinazione() {
	return destinazione;
}

public void setDestinazione(String destinazione) {
	this.destinazione = destinazione;
}

public static String getNomeFile(Connection db, String tipoOperatore){
	String nome = "";
	PreparedStatement pst;
	try {
		pst = db.prepareStatement("select nome_file from lookup_tipo_scheda_operatore where code = ?");
		pst.setInt(1, Integer.parseInt(tipoOperatore));
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			nome = rs.getString("nome_file");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	//	System.out.println("Scheda centralizzata errore su query "+query);
		e.printStackTrace();
	}
	return nome;
}

}
