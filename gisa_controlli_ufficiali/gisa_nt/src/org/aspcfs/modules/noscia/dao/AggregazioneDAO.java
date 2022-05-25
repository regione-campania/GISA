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

import org.aspcfs.modules.gestioneanagrafica.base.Aggregazione;
import org.aspcfs.utils.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregazioneDAO extends GenericDAO
{
	
	private static final Logger logger = LoggerFactory.getLogger( AggregazioneDAO.class );
	
	//Filtri applicabili
	private Aggregazione aggregazione;
	private Integer tipoAttivita;

	//Costruttore 1: tutti i filtri vuoti - utile per tirare fuori tutti i record
	public AggregazioneDAO()
	{
		this.aggregazione=new Aggregazione();
	}
	
	
	//Costruttore 2: solo id valorizzato - utile per ricerche puntuali
	public AggregazioneDAO(Integer tipoAttivita, Aggregazione aggregazione)
	{
		this.tipoAttivita=tipoAttivita;
		this.aggregazione=aggregazione;
	}
	
	
	public AggregazioneDAO(Aggregazione aggregazione)
	{
		this.aggregazione=aggregazione;
		
	}
	
	public AggregazioneDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
	{
		Bean.populate(this, properties);
	}
	
	//Costruttore 4: utile per impostare tutti i filtri provenienti da un resultset (questa cosa dovrebbe avvenire tipicamente da altri dao o da Action)
	//Linea Guida: dal resultset devono arrivare parametri aventi lo stesso nome della variabile di questo bean
	public AggregazioneDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
	
	//Lista tutte le asls che soddisfano i filtri impostati nel dao
	//Miglioramenti: si puo' studiare qualcosa per impostare in automatico tutti i parametri della query senza scriverli sempre a mano
	public  ArrayList<Aggregazione> getItems(Connection conn) throws SQLException 
	{

	  	String sql = " SELECT id, id_macroarea as \"macroarea.id\", codice_attivita, aggregazione, id_flusso_originale FROM public.get_aggregazione( ?, ?, ?, ?, ? ) "  ;
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, aggregazione.getId());
		st.setObject(2, aggregazione.getMacroarea().getId());
		st.setString(3, aggregazione.getCodice_attivita());
		st.setString(4, aggregazione.getAggregazione());
		st.setObject(5, aggregazione.getId_flusso_originale());
		//st.setObject(6, tipoAttivita);
		
		ResultSet rs = st.executeQuery();
		ArrayList<Aggregazione> aggregazioni = new ArrayList<Aggregazione>();
		
		while(rs.next())
		{
			aggregazioni.add(new Aggregazione(rs));
		}
		
		return aggregazioni;
	}
	
	
	   public  ArrayList<Aggregazione> getItemsCodAtt(Connection conn) throws SQLException 
	    {
	       
	        String sql = " select id_aggregazione as \"id\", id_macroarea as \"macroarea.id\", codice_attivita, aggregazione from ml8_linee_attivita_nuove_materializzata ml8  join  master_list_no_scia_abilitate  mls on mls.codice_univoco_ml=ml8.codice_attivita where codice_univoco_ml ilike ? ";
	        PreparedStatement st = conn.prepareStatement(sql);
	        st.setString(1, aggregazione.getCodice_attivita());
	        ResultSet rs = st.executeQuery();
	        ArrayList<Aggregazione> aggregazioni = new ArrayList<Aggregazione>();
	        
	        while(rs.next())
	        {
	            aggregazioni.add(new Aggregazione(rs));
	        }
	        
	        return aggregazioni;
	    }
}
