/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.dpat2019.base.DpatAnagraficaNominativo;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.vigilanza.base.RuoliUtentiParser;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.database.ConnectionElement;

import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

public class DwrApicoltura {



	
	
	public static boolean aggiornaCoordinateApiario(int idStab,String latitudine , String longitudine)
	{

		
		Connection db = null;
		boolean esito = false ;

		try
		{
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			
			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			db = GestoreConnessioni.getConnection()	;
			Stabilimento st = new Stabilimento(db,idStab);
			st.getSedeOperativa().setLatitudine(latitudine);
			st.getSedeOperativa().setLongitudine(longitudine);
			st.aggiornaCoordinate(db);
			esito = true ;
			
		}
		catch(SQLException e)
		{
			return esito;
		}
		catch(LoginRequiredException e)
		{
			throw e;
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}

	public static String notificaApiari(String tipoPratica,int idApiario)
	{

		
		Connection db = null;
		String toRet ="OK";

		try
		{
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			
			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			db = GestoreConnessioni.getConnection()	;
			
			StringBuffer updateNotificaApiario = new StringBuffer();
			updateNotificaApiario.append("update apicoltura_apiari set ") ;
			PreparedStatement pst = null ;
			switch(tipoPratica)
			{
			case "VU":
			{
				updateNotificaApiario.append("flag_notifica_variazione_ubicazione=true where id = ?") ;
				pst = db.prepareStatement(updateNotificaApiario.toString());
				pst.setInt(1, idApiario);
				pst.execute();
				
				break ;
			}
			case "VC":
			{
				updateNotificaApiario.append("flag_notifica_variazione_censimento=true where id = ?") ;
				pst = db.prepareStatement(updateNotificaApiario.toString());
				pst.setInt(1, idApiario);
				pst.execute();
				
				break ;
			}
			case "VD":
			{
				updateNotificaApiario.append("flag_notifica_variazione_detentore=true where id = ?") ;
				pst = db.prepareStatement(updateNotificaApiario.toString());
				pst.setInt(1, idApiario);
				pst.execute();
				break ;
			}
			case "NA":
			{
				updateNotificaApiario.append("stato=? where id = ?") ;
				pst = db.prepareStatement(updateNotificaApiario.toString());
				pst.setInt(1, StabilimentoAction.API_STATO_VALIDATO);
				pst.setInt(2, idApiario);
				pst.execute();
				break ;
			}
			case "all" :
			{
				updateNotificaApiario.append(" flag_notifica_variazione_ubicazione=true,flag_notifica_variazione_censimento=true,flag_notifica_variazione_detentore=true,stato=?  where id = ?") ;
				pst = db.prepareStatement(updateNotificaApiario.toString());
				pst.setInt(1, StabilimentoAction.API_STATO_VALIDATO);
				pst.setInt(2, idApiario);
				pst.execute();
				break ;
			
			}
			default : toRet ="KO"; break ;
			}

		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch(SQLException e)
		{
			 toRet ="KO";
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return toRet;
	}
	
	
	
	
	
	public static DpatStrumentoCalcoloNominativi[] getcomponentiNucleoIspettivoRistrutturato (Connection db ,int idQualificaRuolo,String idAsl,SystemStatus thisSystem,HttpServletRequest request)
	{

		DpatStrumentoCalcoloNominativiList nomList = new DpatStrumentoCalcoloNominativiList();
		nomList.setIdQualifica(idQualificaRuolo);
		

		try
		{
			
			/*SE NON e CRIUV*/

			int idAslIn = -1 ;
			if (idAsl!= null && !idAsl.equals(""))
				idAslIn=Integer.parseInt(idAsl);
			
			Calendar now = Calendar.getInstance();
			int anno = now.get(Calendar.YEAR);
			boolean buildUtentiFromDpat = false ;
			String sql = "select enabled from lookup_qualifiche where code = "+idQualificaRuolo;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				buildUtentiFromDpat = rs.getBoolean(1);
					
			
			
			ArrayList<User>  listaUtenti = UserUtils.getUserFromRole(request, idAslIn,idQualificaRuolo);
			if(buildUtentiFromDpat==true)
			{
				nomList.buildList2(db,idAslIn,anno,thisSystem);
			}

//			else
//			{
//				nomList.buildListNonInDpat(db,idAslIn,idQualificaRuolo,request);
//
//
//
//			}

			if (nomList.size()==0 && buildUtentiFromDpat==true )
			{
				DpatStrumentoCalcoloNominativi nom =  new DpatStrumentoCalcoloNominativi();
				User u = new User();
				u.getContact().setNameFirst("Nessuna risorsa");
				u.getContact().setNameLast("nel Dpat");
				u.setId(-1);
				u.setPassword("");
				u.setUsername("");
				nom.setNominativo(u);
				

				
				nomList.add(nom);
			}
			



			return nomList.getListaNominativiasArray();

		}
		catch(SQLException e)
		{
			
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return null;
	}
}
