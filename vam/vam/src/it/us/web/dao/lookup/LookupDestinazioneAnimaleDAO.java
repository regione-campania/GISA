/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.lookup;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.jmesa.limit.RowSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupDestinazioneAnimaleDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupDestinazioneAnimaleDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static ArrayList<LookupDestinazioneAnimale> getDestinazioneAnimale( Connection connection, String tipologiaAnimale, Boolean dimissioniCcTrasferimento) throws SQLException
	{
		String condizione_dimissioniCcTrasferimento="";
		if (dimissioniCcTrasferimento)
			condizione_dimissioniCcTrasferimento=" and dimissioni_cc_trasferimento = true ";
		ArrayList<LookupDestinazioneAnimale> list = new ArrayList<LookupDestinazioneAnimale>();
		String sql = " select * from lookup_destinazione_animale where enabled_dimissioni and " + tipologiaAnimale + " = true and enabled " + condizione_dimissioniCcTrasferimento + " order by level asc";
        PreparedStatement st = connection.prepareStatement(sql);
		//st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			LookupDestinazioneAnimale corrente = new LookupDestinazioneAnimale();
			corrente.setId(rs.getInt("id"));
			corrente.setDescription(rs.getString("description"));
			corrente.setDescriptionSinantropo(rs.getString("description_sinantropo"));
			corrente.setLevel(rs.getInt("level"));
			corrente.setEnabled(rs.getBoolean("enabled"));
			corrente.setCane(rs.getBoolean("cane"));
			corrente.setGatto(rs.getBoolean("gatto"));
			corrente.setSinantropo(rs.getBoolean("sinantropo"));
			corrente.setDimissioniCcTrasferimento(rs.getBoolean("dimissioni_cc_trasferimento"));
			list.add(corrente);
		}
		return list;
	}

	
	public static LookupDestinazioneAnimale getDestinazioneAnimale( Connection connection, int id) throws SQLException
	{
		String sql = " select * from lookup_destinazione_animale where enabled = true and id = ?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		LookupDestinazioneAnimale corrente=null;
		if(rs.next())
		{
			corrente = new LookupDestinazioneAnimale();
			corrente.setId(rs.getInt("id"));
			corrente.setDescription(rs.getString("description"));
			corrente.setDescriptionSinantropo(rs.getString("description_sinantropo"));
			corrente.setLevel(rs.getInt("level"));
			corrente.setEnabled(rs.getBoolean("enabled"));
			corrente.setCane(rs.getBoolean("cane"));
			corrente.setGatto(rs.getBoolean("gatto"));
			corrente.setSinantropo(rs.getBoolean("sinantropo"));
			corrente.setDimissioniCcTrasferimento(rs.getBoolean("dimissioni_cc_trasferimento"));
		}
		return corrente;
	}


	
}
