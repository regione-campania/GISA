/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.autopsie;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupCMF;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupAutopsiaFenomeniCadaverici;
import it.us.web.bean.vam.lookup.LookupAutopsiaModalitaConservazione;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.AutopsiaDAO;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.ComparatorSuperUtenti;
import it.us.web.util.vam.ComparatorUtenti;
import it.us.web.util.vam.FelinaRemoteUtil;

public class ToEditRichiesta extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("esameNecroscopico");
	}

	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnection(connectionVam);
		
		if (cc.getAccettazione().getAnimale().getNecroscopiaNonEffettuabile()==true){
			setErrore("Impossibile procedere con la modifica. Esame Nescroscopico dichiarato non effettuabile");
			goToAction(new it.us.web.action.vam.cc.Detail());
		}
		else
		{
			if (cc.getAutopsia()==null) {
				goToAction( new ToAdd() );
			}
			else 
			{
				Autopsia a = cc.getAutopsia();
				
				LookupSpecie specie = cc.getAccettazione().getAnimale().getLookupSpecie();
				String filtroSpecie = "";
				if(specie.getId()==SpecieAnimali.cane)
					filtroSpecie = "cani";
				else if(specie.getId()==SpecieAnimali.gatto)
					filtroSpecie = "gatti";
				else
				{
					Animale animale = cc.getAccettazione().getAnimale();
					if(animale.getDecedutoNonAnagrafe())
					{
						if(animale.getSpecieSinantropo()!=null)
						{	
							if(animale.getSpecieSinantropo().equals("1"))
								filtroSpecie = "uccelli";
							else
							{
								//attualmente per i rettili mostriamo gli organi degli uccelli perchè abbiamo solo quelli, 
								//quando avremo gli organi per ogni specie allora lo adatteremo
								filtroSpecie = "mammiferi";
							}
						}
						else
							filtroSpecie = "uccelli";
					}
					else
					{
						Sinantropo s = SinantropoUtil.getSinantropoByNumero(persistence, cc.getAccettazione().getAnimale().getIdentificativo());
						if(s.getLookupSpecieSinantropi().getUccello())
							filtroSpecie = "uccelli";
						else
						{
							//attualmente per i rettili mostriamo gli organi degli uccelli perchè abbiamo solo quelli, 
							//quando avremo gli organi per ogni specie allora lo adatteremo
							filtroSpecie = "mammiferi";
						}
					}
				}
				
				/*ArrayList<LookupCMI> listCMI = (ArrayList<LookupCMI>) persistence.createCriteria( LookupCMI.class )
				.addOrder( Order.asc( "level" ) )
				.list();*/
				
				
				ArrayList<String> ruoli = new ArrayList<String>();
				ruoli.add("Ambulatorio - Veterinario");
				ruoli.add("Universita");
				
				Hashtable<Integer, SuperUtente> operatori = new Hashtable<Integer, SuperUtente>();
				
				
				//Nuova applicazione filtri
				ArrayList<LookupAsl> aslList = (ArrayList<LookupAsl>)persistence.findAll(LookupAsl.class);
				Hashtable<String, String> nominativi = new Hashtable<String, String>();
				for(LookupAsl asl :aslList)
				{
					for(BUtente u :asl.getUtentiDistinct())
					{
						String ruolo = u.getRuolo();
						if(ruolo!=null && (ruolo.equals("Universita") || ruolo.equals("8") || 
								   ruolo.equals("Referente Asl") || ruolo.equals("14") || 
								   ruolo.equals("Sinantropi") || ruolo.equals("13") || 
								   ruolo.equals("IZSM") || ruolo.equals("6") || 
								   ruolo.equals("Ambulatorio - Veterinario") || ruolo.equals("5")|| 
								   ruolo.equals("Borsisti") || ruolo.equals("12"))
								   && nominativi.get(u.getSuperutente().toString().toUpperCase())==null)
						{
							operatori.put(u.getSuperutente().getId(), u.getSuperutente());
							nominativi.put(u.getSuperutente().toString().toUpperCase(), u.getSuperutente().toString().toUpperCase());
						}
					}
				}
				//Fine nuova applicazione filtri
				
				Enumeration<SuperUtente> operatori2 = (Enumeration<SuperUtente>)operatori.elements();
				ArrayList<SuperUtente> operatori3 = new ArrayList<SuperUtente>();
				while( operatori2.hasMoreElements() )
				{
					operatori3.add( operatori2.nextElement() );
				}
				Collections.sort(operatori3, new ComparatorSuperUtenti());
				
				/*SERVIZIO DECESSO*/				
				ServicesStatus status = new ServicesStatus();
				
				if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 1 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
					RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection,req );
					
					//Errore nella comunicazione con il Wrapper
					if (!status.isAllRight()) {
						setMessaggio("Errore nella comunicazione con la BDR di riferimento");
						goToAction(new it.us.web.action.vam.cc.Detail());
					}
					
					req.setAttribute("res", res);
				}
				else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 2 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
					RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection,req );
					
					//Errore nella comunicazione con il Wrapper
					if (!status.isAllRight()) {
						setMessaggio("Errore nella comunicazione con la BDR di riferimento");
						goToAction(new it.us.web.action.vam.cc.Detail());
					}
					
					req.setAttribute("res", rfr);
				}
				else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 3) {
					RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
					req.setAttribute("res", rsr);
				}
				
				ArrayList<LookupAutopsiaSalaSettoria> listAutopsiaSalaSettoria     = (ArrayList<LookupAutopsiaSalaSettoria>) persistence.createCriteria( LookupAutopsiaSalaSettoria.class )
				.add( Restrictions.eq( "enabled", true ) )
				.add( Restrictions.eq( "esameRiferimento", "Necroscopico" ) )
				.addOrder( Order.asc( "esterna" ) )
				.addOrder( Order.asc( "level" ) )
				.addOrder( Order.asc( "description" ) ).list();
				
				req.setAttribute("a", a);		
				req.setAttribute("operatori", operatori3);
				req.setAttribute("listAutopsiaSalaSettoria", listAutopsiaSalaSettoria);
				req.setAttribute( "specie", SpecieAnimali.getInstance() );
				
				ArrayList<LookupAutopsiaModalitaConservazione> listModalitaConservazione = (ArrayList<LookupAutopsiaModalitaConservazione>) persistence.createCriteria( LookupAutopsiaModalitaConservazione.class )
						.addOrder( Order.asc( "level" ) )
						.list();
				
					req.setAttribute("listModalitaConservazione", listModalitaConservazione);
				
				req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(cc.getAccettazione().getAnimale(), persistence, utente, status, connection,req));
				
				req.setAttribute( "progressivo", AutopsiaDAO.getProgressivoNumRifMittente(connectionVam));
				
				gotoPage("/jsp/vam/cc/autopsie/edit_richiesta.jsp");
			}
		}
			
	}
}
	
