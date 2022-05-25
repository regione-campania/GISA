/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.util.dwr;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazioneCondizionate;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.util.Md5;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAccettazioneDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( TestAccettazioneDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupOperazioniAccettazione getOperazioneAccettazione( int id, Connection connection ) throws SQLException
	{
		
		LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
		String sql = " select  id,description, id_bdr, effettuabile_da_vivo, effettuabile_da_morto, effettuabile_fuori_asl, effettuabile_fuori_asl_morto  " + 
					 " from lookup_operazioni_accettazione " + 
					 " where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			op.setDescription(rs.getString("description"));
			op.setId(rs.getInt("id"));
			op.setEffettuabileDaMorto(rs.getBoolean("effettuabile_da_morto"));
			op.setEffettuabileDaVivo(rs.getBoolean("effettuabile_da_vivo"));
			op.setEffettuabileFuoriAsl(rs.getBoolean("effettuabile_fuori_asl"));
			op.setEffettuabileFuoriAslMorto(rs.getBoolean("effettuabile_fuori_asl_morto"));
			op.setIdBdr(rs.getInt("id_bdr"));
			op.setOperazioniCondizionate(getOperazioniCondizionate(rs.getInt("id"), connection, op));
		}
		return op;
	}
	
	
	private static Set<LookupOperazioniAccettazioneCondizionate> getOperazioniCondizionate( int id, Connection connection, LookupOperazioniAccettazione opCondizionante ) throws SQLException
	{
		
		Set<LookupOperazioniAccettazioneCondizionate> ops = new HashSet<LookupOperazioniAccettazioneCondizionate>();
		
		
		String sql = " select *  " + 
					 " from lookup_operazioni_accettazione_condizionate " + 
					 " where operazione_condizionante = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			LookupOperazioniAccettazioneCondizionate op = new LookupOperazioniAccettazioneCondizionate();
			op.setOperazioneCondizionata(getOperazioneAccettazioneSmart(rs.getInt("operazione_condizionata"), connection));
			op.setOperazioneDaFare(rs.getString("operazione_da_fare"));
			op.setOperazioneCondizionante(opCondizionante);
			ops.add(op);			
		}
		return ops;
	}
	
	private static LookupOperazioniAccettazione getOperazioneAccettazioneSmart( int id, Connection connection ) throws SQLException
	{
		
		LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
		String sql = " select  id,description, id_bdr  " + 
					 " from lookup_operazioni_accettazione " + 
					 " where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			op.setDescription(rs.getString("description"));
			op.setId(rs.getInt("id"));
			op.setIdBdr(rs.getInt("id_bdr"));
		}
		return op;
	}
	
}
