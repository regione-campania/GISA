/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.aspcfs.modules.gestioneanagrafica.base.LineaAttivita;
import org.aspcfs.utils.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineaAttivitaDAO extends GenericDAO
{
	
	private static final Logger logger = LoggerFactory.getLogger( LineaAttivitaDAO.class );
	
	private LineaAttivita lineaAttivita;

	public LineaAttivitaDAO()
	{
		this.lineaAttivita = new LineaAttivita();
	}
	
	public LineaAttivitaDAO(LineaAttivita lineaAttivita)
	{
		this.lineaAttivita=lineaAttivita;
	}
	
	public LineaAttivitaDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
	{
		Bean.populate(this, properties);
	}
	
	public LineaAttivitaDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
	
	//Lista tutte le asls che soddisfano i filtri impostati nel dao
	//Miglioramenti: si puo' studiare qualcosa per impostare in automatico tutti i parametri della query senza scriverli sempre a mano
	public  ArrayList<LineaAttivita> getItems(Connection conn) throws SQLException 
	{
	  	String sql = " SELECT * FROM public.get_linee_attivita( ?, ?, ?, ?)";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, lineaAttivita.getId());
		st.setObject(2, lineaAttivita.getAggregazione().getId());
		st.setString(3, lineaAttivita.getLinea_attivita());
		st.setString(4, lineaAttivita.getCodice_univoco());
		
		ResultSet rs = st.executeQuery();
		ArrayList<LineaAttivita> attivita = new ArrayList<LineaAttivita>();
		
		while(rs.next())
		{
			attivita.add(new LineaAttivita(rs));
		}
		
		return attivita;
	}
	
	   public  ArrayList<LineaAttivita> getItemsCodAtt(Connection conn) throws SQLException 
	    {
	        String sql = " select id_nuova_linea_attivita as  \"id\", id_aggregazione,codice_aggregazione as \"codice_prodotto_specie\" , attivita as \"linea_attivita\", codice_univoco_ml as \"codice_univoco\" "
	                   + " from ml8_linee_attivita_nuove_materializzata ml8  join  master_list_no_scia_abilitate  mls on mls.codice_univoco_ml=ml8.codice_attivita where codice_univoco_ml ilike ?";
	      
	        PreparedStatement st = conn.prepareStatement(sql);
	        st.setString(1, lineaAttivita.getCodice_attivita());
	        ResultSet rs = st.executeQuery();
	        ArrayList<LineaAttivita> attivita = new ArrayList<LineaAttivita>();
	        
	        while(rs.next())
	        {
	            attivita.add(new LineaAttivita(rs));
	        }
	        
	        return attivita;
	    }

}
