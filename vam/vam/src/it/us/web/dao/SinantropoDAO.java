/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.constants.Sql;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupCMIDAO;
import it.us.web.dao.sinantropi.CattureDAO;
import it.us.web.dao.vam.ClinicaDAO;
import it.us.web.util.Md5;
import it.us.web.util.bean.Bean;
import it.us.web.util.sinantropi.SinantropoUtil;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinantropoDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( SinantropoDAO.class );
	
	
	
	public static Sinantropo getSinantropoByNumero (Connection connection, String numeroSinantropo) throws ParseException, SQLException {
		
		Sinantropo s = null;		
		
		ResultSet rs = getSinantropo(connection, numeroSinantropo);
		if(rs!=null)
		{
			s = new Sinantropo();
			s.setLastOperation(rs.getString("last_operation"));
			s.setDataDecesso(rs.getDate("data_decesso"));
			s.setStatoAttuale(rs.getString("stato_attuale"));
			s.setDataDecessoPresunta(rs.getBoolean("data_decesso_presunta"));
			s.setCattureis(CattureDAO.getCatture(rs.getInt("id"), connection));
			s.setLookupCMI(LookupCMIDAO.getCMI(rs.getInt("causa_decesso"), connection));
		}
		
		
		return s;
	}
	
	
	public static RegistrazioniSinantropiResponse getInfoDecesso (Connection connection,Animale animale) throws SQLException{
		RegistrazioniSinantropiResponse ret = null;
		ResultSet s = getSinantropo(connection, animale.getIdentificativo());
				
		if (s!=null && LookupCMIDAO.getCMI(s.getInt("causa_decesso"),connection).getDescription() != null && s.getTimestamp("data_decesso") != null) {
			ret = new RegistrazioniSinantropiResponse();
			ret.setDecessoValue(LookupCMIDAO.getCMI(s.getInt("causa_decesso"),connection).getDescription());
			ret.setDataEvento(s.getTimestamp("data_decesso"));
			ret.setDataDecessoPresunta(s.getBoolean("data_decesso_presunta"));
		}
				
		
		return ret;
	}

	
	
	public static String getCodiceIspra(Connection connection, String numeroSinantropo) throws SQLException{
		String codiceIspra="";
		ResultSet rs=getSinantropo(connection, numeroSinantropo);
		if(rs!=null)
			codiceIspra=rs.getString("codice_ispra");
		return codiceIspra;
	}
	
	
	public static ResultSet getSinantropo(Connection connection, String numeroSinantropo) throws SQLException{
		String query="";
		PreparedStatement st;
		
		query="select * from Sinantropo s where s.mc = ? ";
		st = connection.prepareStatement(query);
		st.setString(1, numeroSinantropo);
		ResultSet rs_sinantropiMc = st.executeQuery();

		query="select * from Sinantropo s where s.numero_ufficiale = ? ";
		st = connection.prepareStatement(query);
		st.setString(1, numeroSinantropo);
		ResultSet rs_sinantropiUfficiali = st.executeQuery();

		query="select * from Sinantropo s where s.numero_automatico = ? ";
		st = connection.prepareStatement(query);
		st.setString(1, numeroSinantropo);
		ResultSet rs_sinantropiAutomatici = st.executeQuery();

		query="select * from Sinantropo s where s.codice_ispra = ? ";
		st = connection.prepareStatement(query);
		st.setString(1, numeroSinantropo);
		ResultSet rs_sinantropiCodiceIspra = st.executeQuery();

		if (rs_sinantropiUfficiali.next()) {			
			return rs_sinantropiUfficiali;					
		}		
		else if (rs_sinantropiAutomatici.next()){			
			return rs_sinantropiAutomatici;								
		}
		else if (rs_sinantropiMc.next()){			
			return rs_sinantropiMc;								
		}
		else if (rs_sinantropiCodiceIspra.next()){			
			return rs_sinantropiCodiceIspra;								
		}
		else {
			return null;
		}
	}
	
}
