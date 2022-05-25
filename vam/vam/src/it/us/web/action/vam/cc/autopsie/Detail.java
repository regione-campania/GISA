/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.autopsie;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaFenomeniCadaverici;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class Detail extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("esameNecroscopico");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);	
		
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnection(connectionVam);
		
		/* =============================================  */
		/* =========CONTROLLO DECESSO IN BDR============  */
		/* =============================================  */		
		ServicesStatus status = new ServicesStatus();
		
		if (cc==null){
			cc = (CartellaClinica)persistence.find (CartellaClinica.class, (Integer)session.getAttribute("idCc"));
		}
		
		if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 1 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
			RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection,req);
			
			req.setAttribute("res", res);
			
			//Errore nella comunicazione con il Wrapper
			/*if (!status.isAllRight()) {
				setErrore("Errore nella comunicazione con la BDR di riferimento");
				goToAction(new it.us.web.action.vam.cc.Detail());
			}*/
			
			if (res == null) 
				setMessaggio("Sembra che il decesso ancora non sia stato registrato. Verifica appena possibile!");
		}
		else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 2 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
			RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection,req);
			
			req.setAttribute("res", rfr);
			
			if (rfr == null) 
				setMessaggio("Sembra che il decesso ancora non sia stato registrato. Verifica appena possibile!");
		}
		
		else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == 3 && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
			RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
			
			req.setAttribute("res", rsr);
			
			if (rsr == null) 
				setMessaggio("Sembra che il decesso ancora non sia stato registrato. Verifica appena possibile!");
		}
		
		ArrayList<LookupAutopsiaFenomeniCadaverici> listFenomeniCadaverici = (ArrayList<LookupAutopsiaFenomeniCadaverici>) persistence.createCriteria( LookupAutopsiaFenomeniCadaverici.class )
		.add( Restrictions.isNull("padre") )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		req.setAttribute("listFenomeniCadaverici", listFenomeniCadaverici);		
		
		
		//RECUPERO DATI AUTOPSIA!!!!		
		
		if (cc.getAutopsia() == null && cc.getDataChiusura()==null) {
			goToAction(new ToAdd());
		}
		else if(cc.getAutopsia() == null && cc.getDataChiusura()!=null)
		{
			setMessaggio( "L'esame necroscopico non è stato inserito." );
			redirectTo( "vam.cc.Detail.us" );	
		}
		else 
		{
			
			Autopsia a = cc.getAutopsia();
		
			ArrayList<LookupAutopsiaOrgani> listOrganiAutopsia = (ArrayList<LookupAutopsiaOrgani>) persistence.createCriteria( LookupAutopsiaOrgani.class )
			.add( Restrictions.eq( "tessuto", false ) )
			.add( Restrictions.eq( "enabledSde", true ) )
			.addOrder( Order.asc( "levelSde" ) )
			.list();
			
			ArrayList<LookupAutopsiaTipiEsami> listTipiAutopsia = (ArrayList<LookupAutopsiaTipiEsami>) persistence.createCriteria( LookupAutopsiaTipiEsami.class )
			.list();
			
			ArrayList<LookupAutopsiaEsitiEsami> listEsitiAutopsia = (ArrayList<LookupAutopsiaEsitiEsami>) persistence.createCriteria( LookupAutopsiaEsitiEsami.class )
			.list();
			
			ArrayList<LookupAutopsiaOrganiTipiEsamiEsiti> listOrganiTipiEsiti = (ArrayList<LookupAutopsiaOrganiTipiEsamiEsiti>) persistence.getNamedQuery("LookupAutopsiaOrganiTipiEsamiEsiti_OrganoEnabled").list();
				
			ArrayList<AutopsiaOrganoPatologie> organi = (ArrayList<AutopsiaOrganoPatologie>) persistence.getNamedQuery("GetAutopsiaOrganoPatologiaFromAutopsia").setInteger("idAutopsia", a.getId()).list();

			//Controllo se posso modificare la richiesta
			boolean canEditRichiesta = true;
			boolean accettato = false;
			int idClinica=utente.getClinica().getId();
			Trasferimento trasf = ((cc.getTrasferimentiByCcPostTrasf().size()>0)?(cc.getTrasferimentiByCcPostTrasf().iterator().next()):(null));
			if(trasf!=null)
				idClinica=trasf.getClinicaOrigine().getId();
			if(!TrasferimentoDAO.getTrasferimentiAccettati("U", cc.getAccettazione().getAnimale().getId(), idClinica, connectionVam).isEmpty())
				accettato=true;
			if(a!=null &&  ((!a.getLass().getEsterna() && (a.getDataEsito()!=null )) || (a.getLass().getEsterna() && accettato )))
				canEditRichiesta = false;
			req.setAttribute("canEditRichiesta", canEditRichiesta);
			req.setAttribute("accettato", accettato);
			//Fine controllo
			
			
			//Controllo se posso inserire la necroscopia
			boolean utenteStrutturaNecroscopia = true;
			if(a.getLass().getEsterna())
			{
				if(a.getLass().getDescription().toLowerCase().contains("izsm-avellino") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-avellino"))
					utenteStrutturaNecroscopia = true;
				else if(a.getLass().getDescription().toLowerCase().contains("izsm-avellino") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-avellino"))
					utenteStrutturaNecroscopia = true;
				else if(a.getLass().getDescription().toLowerCase().contains("izsm-benevento") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-benevento"))
					utenteStrutturaNecroscopia = true;
				else if(a.getLass().getDescription().toLowerCase().contains("izsm-caserta") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-caserta"))
					utenteStrutturaNecroscopia = true;
				else if(a.getLass().getDescription().toLowerCase().contains("izsm-portici") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-portici"))
					utenteStrutturaNecroscopia = true;
				else if(a.getLass().getDescription().toLowerCase().contains("izsm-salerno") && utente.getClinica().getNomeBreve().toLowerCase().contains("izsm-salerno"))
					utenteStrutturaNecroscopia = true;
				else if(a.getLass().getDescription().toLowerCase().contains("unina") && utente.getClinica().getNomeBreve().toLowerCase().contains("unina"))
					utenteStrutturaNecroscopia = true;
				else 
					utenteStrutturaNecroscopia = false;
			}
			else
			{
				if(utente.getClinica().getId()!=a.getEnteredBy().getClinica().getId())
					utenteStrutturaNecroscopia = false;
			}
			
			req.setAttribute("utenteStrutturaNecroscopia", utenteStrutturaNecroscopia);
			//Fine controllo
			
			req.setAttribute("a", a);	
			req.setAttribute("organi", 		organi);
			req.setAttribute("listOrganiAutopsia", 		listOrganiAutopsia);
			req.setAttribute("listTipiAutopsia", 		listTipiAutopsia);	
			req.setAttribute("listEsitiAutopsia", 		listEsitiAutopsia);	
			req.setAttribute("allOrganiTipiEsiti",      getOrganoTipoEsitoForJsp(listOrganiTipiEsiti));	
			req.setAttribute( "specie", SpecieAnimali.getInstance() );		
			
			req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(cc.getAccettazione().getAnimale(), persistence, utente, status, connection,req));
			gotoPage( "/jsp/vam/cc/autopsie/detail.jsp");
		}
	}
	
	
	public HashMap<String, Set<LookupAutopsiaEsitiEsami>> getOrganoTipoEsitoForJsp(ArrayList<LookupAutopsiaOrganiTipiEsamiEsiti> listOrganiTipiEsiti) 
	{
		HashMap<String, Set<LookupAutopsiaEsitiEsami>>     allOrganiTipiEsiti = new HashMap<String, Set<LookupAutopsiaEsitiEsami>>(0);
		Iterator<LookupAutopsiaOrganiTipiEsamiEsiti> iter = listOrganiTipiEsiti.iterator();
		while(iter.hasNext())
		{
			LookupAutopsiaOrganiTipiEsamiEsiti ote = iter.next();
			int idOrgano      = ote.getLookupOrganiAutopsia().getId();
			int idTipo        = ote.getLookupAutopsiaTipiEsami().getId();
			String descOrgano = ote.getLookupOrganiAutopsia().getDescription();
			String descTipo   = ote.getLookupAutopsiaTipiEsami().getDescription();
			String chiave     = idOrgano+"---"+descOrgano+";"+idTipo+"---"+descTipo;
			if(allOrganiTipiEsiti.containsKey(chiave))
			{
				Set<LookupAutopsiaEsitiEsami> temp = allOrganiTipiEsiti.get(chiave);
				allOrganiTipiEsiti.remove(chiave);
				temp.add(ote.getEsito());
				allOrganiTipiEsiti.put(chiave, temp);
			}
			else
			{
				Set<LookupAutopsiaEsitiEsami> toAdd = new HashSet<LookupAutopsiaEsitiEsami>();
				toAdd.add(ote.getEsito());
				allOrganiTipiEsiti.put(chiave, toAdd);
			}
		}
		
		return allOrganiTipiEsiti;
	}
}
