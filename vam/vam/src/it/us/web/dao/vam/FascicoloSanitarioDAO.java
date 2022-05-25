/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.jmesa.limit.RowSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FascicoloSanitarioDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( FascicoloSanitarioDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static FascicoloSanitario getFascicoloSanitario( Connection connection, int id) throws SQLException
	{
		FascicoloSanitario fs = null;
		//String sql = " select id, numero from fascicolo_sanitario where id = ? ";
		String sql = " select * from fascicolo_sanitario where id = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			fs = new FascicoloSanitario();
			fs = (FascicoloSanitario) Bean.populate(fs, rs);
		}
		return fs;
	}
	
	
	public static int getNextProgressivo(Connection connection) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		int nextProgressivo = 1;

		try {
			stat = connection.prepareStatement("select max(id)+1 as progressivo "
					+ " from fascicolo_sanitario");
			rs = stat.executeQuery();

			if (rs.next()) 
			{
				nextProgressivo = rs.getInt("progressivo");
			}

		} 
		catch (SQLException e) 
		{
			logger.error("", e);
		} 
		finally 
		{
			close(rs, stat, null);
		}

		return nextProgressivo;
	}
	
	public static void chiudi(CartellaClinicaNoH cc, Connection connection) throws SQLException
	{
		String sql = "update fascicolo_sanitario set data_chiusura  = ? where id = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setDate(1, new java.sql.Date(cc.getDataChiusura().getTime()));
		st.setInt( 2, cc.getFascicoloSanitario().getId());
		st.executeUpdate();
		st.close();
	}
	
	
}
