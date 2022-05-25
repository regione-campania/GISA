/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspcfs.modules.gestioneanagrafica.base.MetadatoTemplate;
import org.aspcfs.modules.gestioneanagrafica.base.Record;
import org.aspcfs.utils.Bean;
import org.aspcfs.utils.ConvertRsToHash;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecordDAO extends GenericDAO
{
	
	private static final Logger logger = LoggerFactory.getLogger( RecordDAO.class );

	private MetadatoTemplate metadato;
	

	public RecordDAO()
	{
		this.metadato=new MetadatoTemplate();
	}
	
	public RecordDAO(MetadatoTemplate metadato)
	{
		this.metadato=metadato;
	}
	
	public RecordDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
	{
		Bean.populate(this, properties);
	}
	
	public RecordDAO(Map<String, String[]> properties,String prefix, boolean isPrefix) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
	{
		Bean.populate(this, properties, prefix, isPrefix);
	}
	
	public RecordDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
	
	
	
	public ArrayList<Record> getItems(Connection conn) throws SQLException
	{
        ArrayList<Record> records = new ArrayList<Record>();
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        
        String queryLookup = "";
        String query = generaQuery (metadato.getSql_campo(), metadato.getSql_origine(), metadato.getSql_condizione());
        
        String type = metadato.getHtml_type();
        
        if (type.equalsIgnoreCase("select") || type.equalsIgnoreCase("macroarea") || type.equalsIgnoreCase("multiple"))
        {
            queryLookup = generaQueryLookup(metadato.getSql_campo_lookup(), metadato.getSql_origine_lookup(), metadato.getSql_condizione_lookup());
        }
        
        if (metadato.getHtml_type().equalsIgnoreCase("indirizzo") || metadato.getHtml_type().equalsIgnoreCase("coordinate") )
        {
            
            System.out.println("Indirizzo o  coordinate");
            
            String value = "";
            String sql = "select html_label,html_name, sql_campo,sql_origine,sql_condizione,html_style from public.configuratore_template_no_scia where oid_parent=? order by html_ordine";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setObject(1, metadato.getId());
            List<Object> list =  new ArrayList<>();
            ResultSet rs = st.executeQuery();
            
            list = ConvertRsToHash.resultSetToHashMapLookup(rs);
            
        
            if (list.size()> 0)
            {
                HashMap Map = null;
                List<Object> obj= new ArrayList<>();
                
                Record record = new Record();
                for (int j=0; j<list.size(); j++)
                {
                    HashMap temp =(HashMap) list.get(j);
                    Map = new LinkedHashMap();
                    
                    String sql_campo = (String) temp.get("sql_campo");
                    String sql_origine = (String) temp.get("sql_origine");
                    String sql_condizione = (String) temp.get("sql_condizione");
                    
                  /*  String queryAddressValue = generaQueryAddress(sql_campo, sql_origine, sql_condizione);
                    //Aggiunto controllo su indirizzo...per query null
                    if(queryAddressValue != null && !queryAddressValue.equals("")){
                        pst2=fixStatement(queryAddressValue, conn);
                        System.out.println("queryindirizzo:"+pst2.toString());
                        pst2.setObject(1, metadato.getRichiesta().getId());   //capire come passare tale valore in maniera dinamica(id_pratica)
                        ResultSet rs2 = pst2.executeQuery();
                        if (rs2.next())
                        {
                            value = rs2.getString(1);
                            System.out.println("Value: "+value);    
                        }
                    }*/
                    Map.put("html_label", temp.get("html_label"));
                    Map.put("html_name",temp.get("html_name"));
                    Map.put("html_style", temp.get("html_style"));
                    Map.put("value", value);
                    obj.add(Map);
                }
                
                metadato.setListaLookup(obj);
            }

        }
        
    
        if (!query.equals("") && !queryLookup.equals(""))
        {
            
            System.out.println("Query o queryLookup");
            Record record = new Record();
            pst2 = conn.prepareStatement(queryLookup);
            System.out.println("querylookup:"+pst2);
            ResultSet rs2 = pst2.executeQuery();
            List<Object> list = ConvertRsToHash.resultSetToHashMapLookup(rs2);
            
           /* pst3=fixStatement(query, conn);
            pst3.setObject(1, metadato.getRichiesta().getId());  
            System.out.println("Query:"+ pst3);
            ResultSet rs3 = pst3.executeQuery();
            if ( rs3.next() )
            {
                record.setValue(rs3.getString(1));
            }
            */
            record.setMetadato(metadato);
            record.setLabel(metadato.getHtml_label());
            record.setAttributo(metadato.getHtml_type());
            record.setName(metadato.getHtml_name());
            record.setStyle(metadato.getHtml_style());
            record.setListaLookup(list);
            records.add(record);
                
            
            
        }
//      // Gestire quando non costruiamo la query
        else if (!query.equals(""))
        {
            System.out.println("Caso else if");
            
            PreparedStatement pst22=fixStatement(query, conn);
            //pst2 = conn.prepareStatement(query);
            //pst2.setObject(1, metadato.getId());
           pst22.setObject(1, 1);    // id = 1 rappresenta il record da prendere nella tabella gins.suap_xml 
            ResultSet rs22 = pst22.executeQuery();
            while (rs22.next())
            {
                //Inizia da qui
                Record record = new Record();
                try 
                {   record.setMetadato(metadato);
                    record.setLabel(metadato.getHtml_label());
                    record.setValue(rs22.getString(1));
                    record.setAttributo(metadato.getHtml_type());
                    record.setName(metadato.getHtml_name());
                    record.setStyle(metadato.getHtml_style());
                    record.setListaLookup(metadato.getListaLookup());

                }       
                catch (PSQLException e1)
                {
                    System.out.println("Error:"+e1);
                }
                    
                records.add(record);
            }
        }
        //Se query e querylookup sono valorizzate non dovremmo prendere solo la prima?
        else if (!queryLookup.equals(""))
        {
            
            System.out.println("Caso else if 2");
            //Inizia da qui
            Record record = new Record();
            pst3 = conn.prepareStatement(queryLookup);
            System.out.println("querylookup:"+pst3);
            ResultSet rs3 = pst3.executeQuery();
            List<Object> list = ConvertRsToHash.resultSetToHashMapLookup(rs3);
            record.setMetadato(metadato);
            record.setLabel(metadato.getHtml_label());
            record.setValue("");
            record.setAttributo(metadato.getHtml_type());
            record.setName(metadato.getHtml_name());
            record.setStyle(metadato.getHtml_style());
            record.setListaLookup(list);
            records.add(record);
                
        }
        else {
        
            System.out.println("Caso else");

            //Adding query null
            Record record = new Record();
            try 
            {
                record.setMetadato(metadato);
                record.setLabel(metadato.getHtml_label());
                record.setValue("");
                record.setAttributo(metadato.getHtml_type());
                record.setName(metadato.getHtml_name());
                record.setStyle(metadato.getHtml_style());
                record.setListaLookup(metadato.getListaLookup());
                records.add(record);
            }       
            catch (Exception e1)
            {
                System.out.println("Error:"+e1);
            }
    
        }
                
        return records;
    }
	
	private String generaQuery (String campo, String origine, String condizione)
	{
		
	   if (campo==null || origine==null || condizione==null || campo.equals("") || origine.equals("") || condizione.equals(""))
            return "";
        
        String query = "";
        query = "SELECT "+campo+" FROM "+origine+" WHERE "+condizione;
        return query;
	}

	
	private PreparedStatement fixStatement (MetadatoTemplate scheda, String query, Connection conn){
		ArrayList<Object> valori = new ArrayList<Object>();
		PreparedStatement pst = null;	
		Pattern pattern = Pattern.compile("#(.*?)#");
	    Matcher matcher = pattern.matcher(query);
	   
	    while (matcher.find()) 
	    {
	    	String var = matcher.group(1);
	    	query = query.replace("#"+var+"#", "?");
	    	valori.add(trovaParametro(scheda,var));
	    }
	 
		try 
		{
			pst = conn.prepareStatement(query);
			for (int i=0; i<valori.size(); i++)
			{
				pst.setObject(i+1, valori.get(i));
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
		
	}
	
	
	private Object trovaParametro (MetadatoTemplate scheda,String campoDaCercare)
	{
		
		Field[] f = scheda.getClass().getDeclaredFields();
		Method[] m = scheda.getClass().getMethods();
		Vector<Method> v = new Vector<Method>();
		Vector<Field> v2 = new Vector<Field>();
		Object o = null;
		
		for (int i= 0; i<f.length;i++)
		{
			String field = f[i].getName();
			for (int j=0; j<m.length; j++)
			{
				String met = m[j].getName();
				if( field.equalsIgnoreCase(campoDaCercare) && ( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) ) )
				{
					v.add(m[j]);
					v2.add(f[i]);
					try
					{
						o = v.elementAt(0).invoke(scheda);
					} catch (IllegalArgumentException e)
					{
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
	
	
	private String generaQueryLookup (String campo, String origine, String condizione)
    {
        String queryLookup = "";
        queryLookup = "SELECT "+campo+" FROM "+origine+" WHERE "+condizione;
        return queryLookup;
    }
    
	private String generaQueryAddress (String campo, String origine, String condizione)
    {
        if (campo==null || origine==null || condizione==null || campo.equals("") || origine.equals("") || condizione.equals(""))
            return "";
        
        String query = "";
        query = "SELECT "+campo+" FROM "+origine+" WHERE "+condizione;
        return query;
    }
	
	   private PreparedStatement fixStatement (String query, Connection conn){
	        ArrayList<Object> valori = new ArrayList<Object>();
	        PreparedStatement pst = null;   
	        Pattern pattern = Pattern.compile("#(.*?)#");
	        Matcher matcher = pattern.matcher(query);
	       
	        while (matcher.find()) {
	            String var = matcher.group(1);
	            query = query.replace("#"+var+"#", "?");
	            }
	     
	        try {
	            pst = conn.prepareStatement(query);
	        
	        for (int i=0; i<valori.size(); i++)
	            pst.setObject(i+1, valori.get(i));
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return pst;
	        
	    }
    
}
