/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupCMIDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupEsameObiettivoTipoDAO;
import it.us.web.dao.lookup.LookupSpecieDAO;
import it.us.web.dao.sinantropi.lookup.LookupSinantropiEtaDAO;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cacheonix.util.array.HashSet;

public class EsameObiettivoDAO extends GenericDAO {
	public static final Logger logger = LoggerFactory.getLogger(EsameObiettivoDAO.class);

	public static HashSet<EsameObiettivoEsito> getEsameObiettivoEsitos(EsameObiettivo eo, Connection connection) throws SQLException 
	{
		HashSet<EsameObiettivoEsito> esiti = new HashSet<EsameObiettivoEsito>();
		String sql = " select eo.data_esame as data_esame_eo, eo.entered as entered_eo,eo.entered_by as entered_by_eo, "
				+ "    eo.id as id_eo,esito as esito_eo , eo.modified as modified_eo, eo.modified_by as modified_by_eo, eo.note as note_eo, "
				+ "    eo_esito.id as id_eo_esito, eo_esito.description as description_eo_esito, eo_esito.level as level_eo_esito, "
				+ "    eo_esito.enabled as enabled_eo_esito  "
				+ "    from esame_obiettivo_esito eo "
				+ "    left join lookup_esame_obiettivo_esito eo_esito on eo_esito.id = eo.esito and eo_esito.enabled "
				+ "    where eo.esame_obiettivo = ? and trashed_date is null ";
		
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt (   1, eo.getId());
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			EsameObiettivoEsito esito = new EsameObiettivoEsito();
			esito.setDataEsameObiettivo(rs.getDate("data_esame_eo"));
			esito.setEntered(rs.getDate("entered_eo"));
			esito.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by_eo"), connection));
			esito.setEsameObiettivo(eo);
			esito.setId(rs.getInt("id_eo"));
			esito.setLookupEsameObiettivoEsito(getLookupEsameObiettivoEsito(rs));
			esito.setModified(rs.getDate("modified_eo"));
			esito.setModifiedBy(UtenteDAO.getUtenteAll(rs.getInt("modified_by_eo"), connection));
			esito.setNote(rs.getString("note_eo"));
			esiti.add(esito);
		}
		return esiti;
	}
	
	
	
	private static LookupEsameObiettivoEsito getLookupEsameObiettivoEsito( ResultSet rs ) throws SQLException
	{
		LookupEsameObiettivoEsito esito = new LookupEsameObiettivoEsito();
		esito.setId(rs.getInt("id_eo_esito"));
		esito.setDescription(rs.getString("description_eo_esito"));
		esito.setEnabled(rs.getBoolean("enabled_eo_esito"));
		esito.setLevel(rs.getInt("level_eo_esito"));
		return esito;
	}
	
	
}
