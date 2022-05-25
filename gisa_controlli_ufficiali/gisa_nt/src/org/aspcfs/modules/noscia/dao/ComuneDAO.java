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

import org.aspcfs.modules.gestioneanagrafica.base.Comune;
import org.aspcfs.utils.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComuneDAO extends GenericDAO
{
	
	private static final Logger logger = LoggerFactory.getLogger( ComuneDAO.class );

	//Filtri applicabili
	private Comune comune;
	
	//Costruttore 1: tutti i filtri vuoti - utile per tirare fuori tutti i record
	public ComuneDAO() throws SQLException
	{
		this.comune = new Comune();
	}
	
	public ComuneDAO(Integer code) throws SQLException
	{
		this.comune = new Comune(code);
	}
	
	public ComuneDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
	{
		Bean.populate(this, properties);
	}
	
	//Costruttore 2: solo id valorizzato - utile per ricerche puntuali
	public ComuneDAO(Comune comune)
	{
		this.comune=comune;
	}
	
	//Costruttore 4: utile per impostare tutti i filtri provenienti da un resultset (questa cosa dovrebbe avvenire tipicamente da altri dao o da Action)
	//Linea Guida: dal resultset devono arrivare parametri aventi lo stesso nome della variabile di questo bean
	public ComuneDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
	
		
	public ArrayList<Comune> getItems( Connection connection ) throws SQLException 
	{      
	    String sql = " select id, cod_comune, cod_regione, c.cod_provincia as  \"provincia.code\", nome, istat, cap, c.cod_nazione, id_asl as \"asl.code\", p.description as \"provincia.description\" from public.get_comuni(?,?,?,?,?)  c"
		            + " join lookup_province p on p.code=c.cod_provincia" ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setObject(1, comune.getId());
		st.setString(2, comune.getNome());
		st.setString(3, comune.getCod_regione());
		st.setObject(4, comune.getProvincia().getCode());
		st.setObject(5, comune.getAsl().getCode());
		ResultSet rs = st.executeQuery();
		
		ArrayList<Comune> comuni = new ArrayList<Comune>();
		
		while(rs.next())
		{
			comuni.add(new Comune(rs));
		}
		
		return comuni;
	}
	


}
