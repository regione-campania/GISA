/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.gestioneanagrafica.base.Impresa;
import org.aspcfs.utils.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImpresaDAO extends GenericDAO
{
	
	private static final Logger logger = LoggerFactory.getLogger( ImpresaDAO.class );
	

	public Impresa impresa;
	
	//Costruttore 1: tutti i filtri vuoti - utile per tirare fuori tutti i record
	public ImpresaDAO(){}
	
	//Costruttore 2: solo id valorizzato - utile per ricerche puntuali
	public ImpresaDAO(Integer id)
	{
		impresa = new Impresa(id);
	}
	
	//Costruttore 4: utile per impostare tutti i filtri provenienti da un resultset (questa cosa dovrebbe avvenire tipicamente da altri dao o da Action)
	//Linea Guida: dal resultset devono arrivare parametri aventi lo stesso nome della variabile di questo bean
	public ImpresaDAO(ResultSet rs) throws SQLException 
	{
		Bean.populate(this, rs);
	}
	

	public ImpresaDAO(Impresa impresa) {
		this.impresa=impresa;
	}

	public ArrayList<Impresa> getItems(Connection conn) throws SQLException 
	{

		String sql = "select id, ragione_sociale ,codfisc ,piva, id_tipo_impresa_societa as \"tipo_impresa.code\", pec, note from anagrafica.anagrafica_cerca_impresa(?,?,?,?,?,?,?,?) "
				   + " union "
				   +"select id, ragione_sociale ,codfisc ,piva, id_tipo_impresa_societa as \"tipo_impresa.code\", pec, note from anagrafica.anagrafica_cerca_impresa_old_anagrafica(?,?,?,?,?,?,?,?) ";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setObject(1, impresa.getId());
		st.setString(2, impresa.getRagione_sociale());
		st.setString(3, impresa.getCodfisc());
		st.setString(4, impresa.getPiva());
		st.setObject(5, impresa.getTipo_impresa());
		st.setString(6, impresa.getPec());
	
		ResultSet rs = st.executeQuery();
		ArrayList<Impresa> imprese = new ArrayList<Impresa>();
		
		while(rs.next())
		{
			imprese.add(new Impresa(rs));
		}
		
		return imprese;
	}

	
	public ArrayList<Impresa> checkEsistenza(Connection conn) throws SQLException 
	{

		String sql = " select * from public_functions.cerca_verifica_impresa(null, ?, ?, ?) impresa " +
					 " join public_functions.cerca_verifica_soggetto_fisico(?,?) sogg_fisico "+
					 " on impresa.piva= sogg_fisico.piva";
		
		PreparedStatement st = conn.prepareStatement(sql);
		
		st.setString( 1,impresa.getRagione_sociale() );
		st.setString( 2,impresa.getCodfisc().trim() );
		st.setString( 3,impresa.getPiva().trim() );
		st.setString( 4,impresa.getCodfisc().trim() );
		st.setString( 5,impresa.getPiva().trim() );
		ResultSet rs = st.executeQuery();
		ArrayList<Impresa> imprese = new ArrayList<Impresa>();
		
		while(rs.next())
		{
			imprese.add(new Impresa(rs));
		}
		
		return imprese;
	}
	



}
