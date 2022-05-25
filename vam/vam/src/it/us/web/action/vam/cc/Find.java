/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;


import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.RowSelect;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaList;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.lookup.LookupAlimentazioniDAO;
import it.us.web.dao.lookup.LookupFeriteDAO;
import it.us.web.dao.lookup.LookupHabitatDAO;
import it.us.web.dao.vam.CartellaClinicaDAO;
import it.us.web.dao.vam.ClinicaDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.bean.Bean;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class Find extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "LIST", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		Context ctx2 = new InitialContext();
		javax.sql.DataSource ds2 = (javax.sql.DataSource)ctx2.lookup("java:comp/env/jdbc/vamM");
		connection = ds2.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		int idClinica 	= utente.getClinica().getId();
		String tipoFind = stringaFromRequest("tipoFind");
		
		Enumeration<String> all = req.getParameterNames();
		while(all.hasMoreElements())
		{
			System.out.println(all.nextElement());
		}
		
		//Ricerca per MC
		if(tipoFind.equals("mc")) {
			
			String idAnimale = stringaFromRequest("numeroMC");
			
			//Recupero delle cartelle cliniche associate ad un determinato animale
			ArrayList<CartellaClinica> cartelleCliniche = CartellaClinicaDAO.getCcs(connection, idAnimale, (booleanoFromRequest("ricercaAllCliniche"))?(-1):(idClinica));
			
			if (cartelleCliniche.size() == 0) {
				
				setErrore("Nessuna cartella clinica presente con il seguente Numero di MC : " + idAnimale);				
				gotoPage("/jsp/vam/cc/find.jsp");
				
			}
			else {
				
				req.setAttribute("tipoFind", tipoFind);	
				req.setAttribute("numeroMC", idAnimale);	
				req.setAttribute("cartelleCliniche", cartelleCliniche);			
				gotoPage("/jsp/vam/cc/list.jsp");
				
			}
			
		}
		//Ricerca per numero di cartella clinica
		else if (tipoFind.equals("cc")) {
			
			String numeroCC = stringaFromRequest("numeroCC");
			
			//Recupero delle cartelle cliniche associate ad un determinato animale
			
			CartellaClinica cartellaClinica = CartellaClinicaDAO.getCc(connection, numeroCC, idClinica);
			
			if (cartellaClinica==null) {
				
				setErrore("Nessuna cartella clinica presente con il seguente Numero : " + numeroCC );				
				gotoPage("/jsp/vam/cc/find.jsp");
				
			}
			else {
				
				
				ArrayList<LookupAlimentazioni> listAlimentazioni = LookupAlimentazioniDAO.getAlimentazioni(connection);
				
				ArrayList<LookupHabitat> listHabitat = LookupHabitatDAO.getHabitat(connection);
				

				ArrayList<LookupFerite> listFerite = LookupFeriteDAO.getFerite(connection);
						
				req.setAttribute("listFerite", listFerite);	
				
				Animale animale = cartellaClinica.getAccettazione().getAnimale();
				if(animale.getDecedutoNonAnagrafe())
				{
					req.setAttribute("fuoriAsl", false);
					req.setAttribute("versoAssocCanili", false);
				}
				else
				{	
					if (cartellaClinica.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO) 
					{
						req.setAttribute("fuoriAsl", false);
						req.setAttribute("versoAssocCanili", false);
					}
				}
				
				Autopsia a = CartellaClinicaDAO.getAutopsia(connection, cartellaClinica.getId());
				req.setAttribute("a", a);
					
				req.setAttribute("listAlimentazioni", listAlimentazioni);		
				req.setAttribute("listHabitat", 	  listHabitat);	
				session.setAttribute("idCc", cartellaClinica.getId());
				session.setAttribute("cc", cartellaClinica);
				req.setAttribute( "specie", SpecieAnimali.getInstance() );
				req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(cartellaClinica.getAccettazione().getAnimale(), persistence, utente, new ServicesStatus(), connectionBdu,req));
				
				gotoPage("/jsp/vam/cc/home.jsp");
				
			}
			
		}
		
		else if (tipoFind.equals("all")) 
		{
			TableFacade tableFacade = new TableFacadeImpl("accettazioni", req);
			int totalRows = 0;
			
			String filtroMc = "";
			String mcFiltro = "";
			if(stringaFromRequest("accettazioni_f_accettazione.animale.identificativo")!=null)
			{
				filtroMc = " a.identificativo ilike '%" + stringaFromRequest("accettazioni_f_accettazione.animale.identificativo")+ "%' and ";
				mcFiltro = stringaFromRequest("accettazioni_f_accettazione.animale.identificativo");
				req.setAttribute("accettazioni_f_accettazione.animale.identificativo", stringaFromRequest("accettazioni_f_accettazione.animale.identificativo"));
			}
			String filtroTipologia = "";
			String tipologiaFiltro = "";
			boolean tipologiaFiltroMorto1       = false;
			boolean tipologiaFiltroMorto2       = true;
			boolean tipologiaFiltroDayHospital1 = false;
			boolean tipologiaFiltroDayHospital2 = true;
			if(stringaFromRequest("accettazioni_f_tipologia")!=null)
			{
				if(stringaFromRequest("accettazioni_f_tipologia").equals("Necroscopica"))
				{		
					filtroTipologia = " cc.cc_morto and ";
					tipologiaFiltroMorto1       = true;
				}
				else if(stringaFromRequest("accettazioni_f_tipologia").equals("Degenza"))
				{
					filtroTipologia = " cc.cc_morto=false and cc.day_hospital=false and ";
					tipologiaFiltroMorto2       = false;
					tipologiaFiltroDayHospital1 = false;
				}
				else if(stringaFromRequest("accettazioni_f_tipologia").equals("Day Hospital"))
				{
					filtroTipologia = " cc.cc_morto=false and cc.day_hospital=true and ";
					tipologiaFiltroDayHospital1 = true;
					tipologiaFiltroMorto2       = false;
				}
				tipologiaFiltro = stringaFromRequest("accettazioni_f_tipologia");
				req.setAttribute("accettazioni_f_tipologia", stringaFromRequest("accettazioni_f_tipologia"));
			}
			String filtroNum = "";
			String numCcFiltro = "";
			if(stringaFromRequest("accettazioni_f_numero")!=null)
			{
				filtroNum = " cc.numero ilike '%" + stringaFromRequest("accettazioni_f_numero")+ "%' and ";
				numCcFiltro = stringaFromRequest("accettazioni_f_numero");
			}
			String filtroFs = "";
			String fsFiltro = "";
			if(stringaFromRequest("accettazioni_f_fascicoloSanitario.numero")!=null)
			{
				filtroFs = " fs.numero ilike '%" + stringaFromRequest("accettazioni_f_fascicoloSanitario.numero") + "%' and ";
				fsFiltro = stringaFromRequest("accettazioni_f_fascicoloSanitario.numero");
				req.setAttribute("accettazioni_f_fascicoloSanitario.numero", stringaFromRequest("accettazioni_f_fascicoloSanitario.numero"));
			}
			
			String filtroDataAperturaInizio = "";
			String filtroDataAperturaFine = "";
			String dataAperturaFiltroInizio = "01/01/1976";
			String dataAperturaFiltroFine = "01/01/3000";
			if(stringaFromRequest("accettazioni_f_dataApertura")!=null)
			{
				if(stringaFromRequest("accettazioni_f_dataApertura").indexOf("-----")>0)
				{
					if(!stringaFromRequest("accettazioni_f_dataApertura").split("-----")[0].equals("nullo"))
						dataAperturaFiltroInizio = stringaFromRequest("accettazioni_f_dataApertura").split("-----")[0];
					if(!stringaFromRequest("accettazioni_f_dataApertura").split("-----")[1].equals("nullo"))
						dataAperturaFiltroFine = stringaFromRequest("accettazioni_f_dataApertura").split("-----")[1];
					filtroDataAperturaInizio = " cc.data_apertura >= cast ('" + dataAperturaFiltroInizio + "' as timestamp) and ";
					filtroDataAperturaFine = " cc.data_chiusura <= cast ('" + dataAperturaFiltroFine + "' as timestamp) and ";
				}
				else
				{
					dataAperturaFiltroInizio = stringaFromRequest("accettazioni_f_dataApertura");
					filtroDataAperturaInizio = " cc.data_apertura >= cast ('" + dataAperturaFiltroInizio + "' as timestamp) and ";
				}
			}
			
			String filtroDataChiusuraInizio = "";
			String filtroDataChiusuraFine = "";
			String dataChiusuraFiltroInizio = "01/01/1976";
			String dataChiusuraFiltroFine = "01/01/3000";
			if(stringaFromRequest("accettazioni_f_dataChiusura")!=null)
			{
				if(stringaFromRequest("accettazioni_f_dataChiusura").indexOf("-----")>0)
				{
					if(!stringaFromRequest("accettazioni_f_dataChiusura").split("-----")[0].equals("nullo"))
						dataChiusuraFiltroInizio = stringaFromRequest("accettazioni_f_dataChiusura").split("-----")[0];
					if(!stringaFromRequest("accettazioni_f_dataChiusura").split("-----")[1].equals("nullo"))
						dataChiusuraFiltroFine = stringaFromRequest("accettazioni_f_dataChiusura").split("-----")[1];
					filtroDataChiusuraInizio = " (cc.data_chiusura is null or cc.data_chiusura >= cast ('" + dataChiusuraFiltroInizio + "' as timestamp)) and ";
					filtroDataChiusuraFine = " (cc.data_chiusura is null or cc.data_chiusura <= cast ('" + dataChiusuraFiltroFine + "' as timestamp)) and ";
				}
				else
				{
					dataChiusuraFiltroInizio = stringaFromRequest("accettazioni_f_dataChiusura");
					filtroDataChiusuraInizio = "( cc.data_chiusura is null or  cc.data_chiusura >= cast ('" + dataChiusuraFiltroInizio + "' as timestamp)) and ";
				}
			}
			
			String query_tot = " select count(*) " +
					   " from cartella_clinica cc, utenti u, clinica cl, animale a, accettazione acc, fascicolo_sanitario fs " +
					   " where cc.trashed_date is null and cc.entered_by = u.id and " +
					   " cc.accettazione = acc.id and acc.animale = a.id and cc.fascicolo_sanitario = fs.id and " +
					   " u.clinica = cl.id and " +
					   filtroNum + filtroFs + filtroMc + 
					   filtroDataAperturaInizio + filtroDataAperturaFine +
					   filtroDataChiusuraInizio + filtroDataChiusuraFine +
					   filtroTipologia +
					   " cl.id = " + utente.getClinica().getId();
			PreparedStatement st = connection.prepareStatement(query_tot);
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
			{
				totalRows = rs.getInt(1);
			}
			
			
			RowSelect rowSelect = tableFacade.setTotalRows(totalRows);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			//Recupero delle cartelle cliniche associate alla clinica
			
			
			
			
			
			
			//Inizio Hibernate
			
			//Criteria c = persistence.createCriteria(CartellaClinica.class)
//			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
			//.createAlias("fascicoloSanitario", "fs")
			//.add(Restrictions.or( Restrictions.eq("ccMorto",     tipologiaFiltroMorto1), Restrictions.eq("ccMorto", tipologiaFiltroMorto2)))
			//.add(Restrictions.or( Restrictions.eq("dayHospital",     tipologiaFiltroDayHospital1), Restrictions.eq("dayHospital", tipologiaFiltroDayHospital2)))
			//.add(Restrictions.ilike("fs.numero", fsFiltro, MatchMode.ANYWHERE))
			//.add(Restrictions.ge("dataApertura", sdf.parse(dataAperturaFiltroInizio)))
			//.add(Restrictions.le("dataApertura", sdf.parse(dataAperturaFiltroFine)))
			//.add(Restrictions.or(Restrictions.ge("dataChiusura", sdf.parse(dataChiusuraFiltroInizio)), Restrictions.isNull("dataChiusura")))
			//.add(Restrictions.or(Restrictions.le("dataChiusura", sdf.parse(dataChiusuraFiltroFine)), Restrictions.isNull("dataChiusura")))
			//.createAlias("accettazione", "acc")
			//.createAlias("acc.animale", "an")
			//.add(Restrictions.ilike("an.identificativo", mcFiltro, MatchMode.ANYWHERE))
			//.add(Restrictions.in("enteredBy", ClinicaDAO.getUtenti(connection, utente.getClinica().getId())  ))
			//.add(Restrictions.ilike("numero", numCcFiltro, MatchMode.ANYWHERE));
			
			//c.setFetchMode("accettazione.operazioniRichieste", FetchMode.LAZY);
	
			String orderFilter = "";
			if(stringaFromRequest("accettazioni_s_0_numero")!=null && stringaFromRequest("accettazioni_s_0_numero").equals("asc"))
				orderFilter += " cc.numero asc ,";
				//c = c.addOrder(Order.asc("numero"));
			else if(stringaFromRequest("accettazioni_s_0_numero")!=null && stringaFromRequest("accettazioni_s_0_numero").equals("desc"))
				orderFilter += " cc.numero desc ,";
				//c = c.addOrder(Order.desc("numero"));
			if(stringaFromRequest("accettazioni_s_2_accettazione.animale.identificativo")!=null && stringaFromRequest("accettazioni_s_2_accettazione.animale.identificativo").equals("asc"))
				orderFilter += " an.identificativo asc ,";
				//c = c.addOrder(Order.asc("an.identificativo"));
			else if(stringaFromRequest("accettazioni_s_2_accettazione.animale.identificativo")!=null && stringaFromRequest("accettazioni_s_2_accettazione.animale.identificativo").equals("desc"))
				orderFilter += " an.identificativo desc ,";
				//c = c.addOrder(Order.desc("an.identificativo"));
			if(stringaFromRequest("accettazioni_s_3_dataApertura")!=null && stringaFromRequest("accettazioni_s_3_dataApertura").equals("asc"))
				orderFilter += " cc.data_apertura asc ,";
				//c = c.addOrder(Order.asc("dataApertura"));
			else if(stringaFromRequest("accettazioni_s_3_dataApertura")!=null && stringaFromRequest("accettazioni_s_3_dataApertura").equals("desc"))
				orderFilter += " cc.data_apertura desc ,";
				//c = c.addOrder(Order.desc("dataApertura"));
			if(stringaFromRequest("accettazioni_s_1_fascicoloSanitario.numero")!=null && stringaFromRequest("accettazioni_s_1_fascicoloSanitario.numero").equals("asc"))
				orderFilter += " fs.numero asc ,";
				//c = c.addOrder(Order.asc("fs.numero"));
			else if(stringaFromRequest("accettazioni_s_1_fascicoloSanitario.numero")!=null && stringaFromRequest("accettazioni_s_1_fascicoloSanitario.numero").equals("desc"))
				orderFilter += " fs.numero desc ,";
				//c = c.addOrder(Order.desc("fs.numero"));
			else if(stringaFromRequest("accettazioni_s_6_tipologia")!=null && stringaFromRequest("accettazioni_s_6_tipologia").equals("desc"))
				orderFilter += " cc.cc_morto desc ,";
				//c = c.addOrder(Order.desc("ccMorto"));
			else if(stringaFromRequest("accettazioni_s_6_tipologia")!=null && stringaFromRequest("accettazioni_s_6_tipologia").equals("asc"))
				orderFilter += " cc.cc_morto asc ,";
				//c = c.addOrder(Order.asc("ccMorto"));
			if(!orderFilter.equals(""))
				orderFilter = orderFilter.substring(0, orderFilter.length()-1) ;
			
			//Fatto
			//List<CartellaClinica> cartelleCliniche = c.addOrder(Order.desc("numero"))
				//	.addOrder(Order.desc("id"))
					//.setMaxResults(rowSelect.getMaxRows())
					//.setFirstResult(rowSelect.getRowStart())
					//.list();
			//Fine Hibernate
			
			List<CartellaClinica> cartelleCliniche = CartellaClinicaDAO.getCartelleCliniche(connection, rowSelect, tipologiaFiltroMorto1, tipologiaFiltroMorto2, tipologiaFiltroDayHospital1, tipologiaFiltroDayHospital2, fsFiltro, dataAperturaFiltroInizio, dataAperturaFiltroFine, dataChiusuraFiltroInizio, dataChiusuraFiltroFine, mcFiltro, idClinica, numCcFiltro,orderFilter);

			if (cartelleCliniche.size() == 0) 
			{
				setErrore("Nessuna cartella clinica per questa clinica");				
				gotoPage("/jsp/vam/cc/find.jsp");
				
			}
			else 
			{
				req.setAttribute("tipoFind", tipoFind);	
				req.setAttribute("cartelleCliniche", cartelleCliniche);
				req.setAttribute("limit", tableFacade.getLimit());
				gotoPage("/jsp/vam/cc/list.jsp");
			}
		}
		
			
	}
}
