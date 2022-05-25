/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.dwr.vam.cc.autopsie;


import it.us.web.bean.BUtente;
import it.us.web.bean.remoteBean.RegistrazioniInterface;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.constants.Specie;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupDestinazioneAnimaleDAO;
import it.us.web.dao.vam.AnimaleDAONoH;
import it.us.web.dao.vam.AutopsiaDAO;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.util.vam.AnimaliUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test 
{
	Logger logger = LoggerFactory.getLogger( Test.class );
	
	public String getProgressivoNumRifMittente( HttpServletRequest req)
	{
		Connection connectionVam = null;
		String ret = "";
		DecimalFormat decimalFormat = new DecimalFormat( "000" );
		
		try
		{
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			aggiornaConnessioneApertaSessione(req);
			
			ret = decimalFormat.format(AutopsiaDAO.getProgressivoNumRifMittente(connectionVam));

			connectionVam.close();
			aggiornaConnessioneChiusaSessione(req);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			aggiornaConnessioneChiusaSessione(req);
			if(connectionVam!=null)
			{
				try
				{
					connectionVam.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		return ret;
		
	}
	public String existNumRifMittente( String numRifMittente, HttpServletRequest req)
	{
		Connection connectionVam = null;
		String ret = "";
		try
		{
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			aggiornaConnessioneApertaSessione(req);
			

			if(AutopsiaDAO.existNumRifMittente(connectionVam, numRifMittente))
				ret="Il numero di riferimento mittente è stato già usato.";
			
			connectionVam.close();
			aggiornaConnessioneChiusaSessione(req);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		finally
		{
			aggiornaConnessioneChiusaSessione(req);
			if(connectionVam!=null)
			{
				try
				{
					connectionVam.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		return ret;
		
	}
	
	
	public String existNumRifMittente( int idAutopsia, String numRifMittente, HttpServletRequest req)
	{
		Connection connectionVam = null;
		String ret = "";
		try
		{
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			aggiornaConnessioneApertaSessione(req);
			

			if(AutopsiaDAO.existNumRifMittente(connectionVam, numRifMittente, idAutopsia))
				ret="Il numero di riferimento mittente è stato già usato.";
			
			connectionVam.close();
			aggiornaConnessioneChiusaSessione(req);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		finally
		{
			aggiornaConnessioneChiusaSessione(req);
			if(connectionVam!=null)
			{
				try
				{
					connectionVam.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
			aggiornaConnessioneChiusaSessione(req);
		}
		return ret;
		
	}
	
	
	
	
	public void aggiornaConnessioneApertaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
			numConnessioniDb = numConnessioniDb+1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			req.getSession().setAttribute("timeConnOpen",     new Date());
		}
	}
	
	public void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
			numConnessioniDb = numConnessioniDb-1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			if(numConnessioniDb==0)
				req.getSession().setAttribute("timeConnOpen",     null);
		}
	}
	
	
	
}