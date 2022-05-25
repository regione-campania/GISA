/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.dwr.vam.cc.dimissioni;


import it.us.web.bean.BUtente;
import it.us.web.bean.remoteBean.RegistrazioniInterface;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.constants.Specie;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupDestinazioneAnimaleDAO;
import it.us.web.dao.vam.AnimaleDAONoH;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.util.dwr.vam.cc.dimissioni.Test;
import it.us.web.util.vam.AnimaliUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test 
{
	Logger logger = LoggerFactory.getLogger( Test.class );
	
	public String check( String idDestinazioneAnimale, String idUtente, String idAnimale, String idCc, String intraFuoriAsl, String versoAssocCanili, HttpServletRequest req)
	{
		Connection connection    = null;
		Connection connectionVam = null;
		String ret = "";
		try
		{
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			connection = ds.getConnection();
			aggiornaConnessioneApertaSessione(req);
			Context ctxVam = new InitialContext();
			javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
			connectionVam = dsVam.getConnection();
			aggiornaConnessioneApertaSessione(req);
			//BUtente utente 										 	 = (BUtente)persistence.find(BUtente.class, Integer.parseInt(idUtente));
			BUtente utente = UtenteDAO.getUtente(Integer.parseInt(idUtente));
			//Animale animale 									 	 = getAnimale(Integer.parseInt(idAnimale), persistence);
			AnimaleNoH animale 									 	 = AnimaleDAONoH.getAnimale(Integer.parseInt(idAnimale), connectionVam);
			RegistrazioniInterface opEffettuabiliBdr			 	 = AnimaliUtil.findRegistrazioniEffettuabili( connectionVam, animale, utente, connection,req );
			
			LookupDestinazioneAnimale destinazioneAnimale = LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connectionVam,  Integer.parseInt(idDestinazioneAnimale));
			CartellaClinicaNoH cc = CartellaClinicaDAONoH.getCc(connectionVam, Integer.parseInt(idCc));

			boolean anomaliaRiscontrata = false;
			
			if(nonEffettuabileInBdr(Integer.parseInt(idDestinazioneAnimale),opEffettuabiliBdr,cc, Boolean.parseBoolean(intraFuoriAsl), Boolean.parseBoolean(versoAssocCanili)) )
			{
				if(animale.getLookupSpecie().getId()==Specie.SINANTROPO)
					ret += "- " + destinazioneAnimale.getDescriptionSinantropo() + " non risulta effettuabile nella banca dati Sinantropi.\n" ;
				else
					ret += "- " + destinazioneAnimale.getDescription() + " non risulta effettuabile in BDR.\n" ;
				anomaliaRiscontrata=true;
			}
			
			if(!ret.equals(""))
				ret="Il motivo della dimissione non è registrabile per i seguenti motivi:\n"+ret;
			
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
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
			if(connection!=null)
			{
				try
				{
					connection.close();
					aggiornaConnessioneChiusaSessione(req);
				}
				catch(SQLException e)
				{
					logger.info(e.getMessage());
				}
			}
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
	
	//Verifico in BDR
	private boolean nonEffettuabileInBdr(int destinazioneAnimale, RegistrazioniInterface opEffettuabiliBdr, CartellaClinicaNoH cc, boolean intraFuoriAsl, boolean versoAssocCanili)
	{
		System.out.println("nonEffettuabileInBdr: " + destinazioneAnimale);
		System.out.println("nonEffettuabileInBdr: " + opEffettuabiliBdr.getReimmissione());
		return (destinazioneAnimale==2 && (opEffettuabiliBdr.getDecesso()==null || !opEffettuabiliBdr.getDecesso())) ||
			   (destinazioneAnimale==1 && (opEffettuabiliBdr.getRitornoProprietario()==null || !opEffettuabiliBdr.getRitornoProprietario())) ||
			   (destinazioneAnimale==3 && intraFuoriAsl    && (opEffettuabiliBdr.getAdozioneFuoriAsl()==null         || !opEffettuabiliBdr.getAdozioneFuoriAsl())) ||
			   (destinazioneAnimale==3 && versoAssocCanili && (opEffettuabiliBdr.getAdozioneVersoAssocCanili()==null || !opEffettuabiliBdr.getAdozioneVersoAssocCanili())) ||
			   (destinazioneAnimale==3 && !versoAssocCanili && !intraFuoriAsl &&(opEffettuabiliBdr.getAdozione()==null || !opEffettuabiliBdr.getAdozione())) ||
			   (destinazioneAnimale==4 && cc.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && (opEffettuabiliBdr.getTrasfCanile()==null || !opEffettuabiliBdr.getTrasfCanile())) ||
			   (destinazioneAnimale==5 && cc.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && (opEffettuabiliBdr.getReimmissione()==null || !opEffettuabiliBdr.getReimmissione()) ||
			   (destinazioneAnimale==8 && cc.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && (opEffettuabiliBdr.getRitornoAslOrigine()==null || !opEffettuabiliBdr.getRitornoAslOrigine())) ||
			   (destinazioneAnimale==9 && cc.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && (opEffettuabiliBdr.getRitornoCanileOrigine() ==null || !opEffettuabiliBdr.getRitornoCanileOrigine()))
					   );
		
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