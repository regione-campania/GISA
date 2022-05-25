/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.decessi.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameObiettivoEsito;
import it.us.web.bean.vam.EsameObiettivoPatologieCongenite;
import it.us.web.bean.vam.EsameObiettivoSintoPatologia;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.TipoIntervento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoPeriodoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.bean.vam.lookup.LookupFerite;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class StampaDetail extends GenericAction implements Specie {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
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
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		//Se idCartellaClinica vuol dire che è già in sessione	
		int id = interoFromRequest("idCartellaClinica");
		
		if(id>0)
			session.setAttribute("idCc", id);		
		
		Animale animale = cc.getAccettazione().getAnimale();
		Date dataDecesso = null;
		
		//Se il cane non è morto senza mc bisogna leggere in bdr le info sulla morte
		ServicesStatus status = new ServicesStatus();
		if(animale.getDecedutoNonAnagrafe())
		{
			//req.setAttribute("fuoriAsl", false);
		}
		else
		{	
			if (animale.getLookupSpecie().getId() == Specie.CANE && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, status, connection,req );
				req.setAttribute("res", res);
				dataDecesso = (res == null) ? null : res.getDataEvento();
			}
			else if (animale.getLookupSpecie().getId() == Specie.GATTO && !animale.getDecedutoNonAnagrafe()) 
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso( cc.getAccettazione().getAnimale().getIdentificativo(), utente, status, connection,req );
				req.setAttribute("res", rfr);
				dataDecesso = (rfr == null) ? null : rfr.getDataEvento();
			}
			else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, cc.getAccettazione().getAnimale());
				req.setAttribute("res", rsr);
				dataDecesso = (rsr == null) ? null : rsr.getDataEvento();
			}
		}
		
		//Recupero di tutte le terapie
		ArrayList<TerapiaDegenza> tdList = (ArrayList<TerapiaDegenza>) persistence.getNamedQuery("GetTerapieDegenzaByCC").setInteger("idCartellaClinica", idCc).list();
		ArrayList<Diagnosi> diagnosi = (ArrayList<Diagnosi>) persistence.getNamedQuery("GetDiagnosiByCC").setInteger("idCartellaClinica", idCc).list();

		ArrayList<Trasferimento> trasfs = null;
		if(!cc.getCcPostTrasferimento() && !cc.getCcRiconsegna())
			trasfs = cc.getTrasferimentiOrderByStato();
		else if(cc.getCcPostTrasferimentoMorto())
			trasfs = cc.getTrasferimentiByCcMortoPostTrasfOrderByStato();
		else if(cc.getCcPostTrasferimento())
			trasfs = cc.getTrasferimentiByCcPostTrasfOrderByStato();
		else if(cc.getCcRiconsegna())
			trasfs = cc.getTrasferimentiByCcPostRiconsegnaOrderByStato();
		
		
		//Inizio Esame Obiettivo Generale
		ArrayList<LookupEsameObiettivoTipo> listEsameObiettivoTipo = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
			.add( Restrictions.eq( "specifico", false ) )	
			.list();
		
		int sizeEsameObiettivoTipo = listEsameObiettivoTipo.size();	
				
		ArrayList<ArrayList<LookupEsameObiettivoEsito>> superList   = new ArrayList();
						
		ArrayList<String> descrizioniEOTipo 						= new ArrayList();
		
		for (int i = 0; i < sizeEsameObiettivoTipo; i++) 
		{
			
			LookupEsameObiettivoTipo leot = (LookupEsameObiettivoTipo) listEsameObiettivoTipo.get(i);
			
			ArrayList<LookupEsameObiettivoEsito> listEsameObiettivo = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )
			.add( Restrictions.eq( "lookupEsameObiettivoTipo.id", leot.getId() ) )
			.add( Restrictions.isNull("lookupEsameObiettivoEsito") )
			.list();
									
			descrizioniEOTipo.add(listEsameObiettivo.get(0).getLookupEsameObiettivoTipo().getDescription());
			superList.add(listEsameObiettivo);
		}
		
		// La lista di tutti i padri
		ArrayList<LookupEsameObiettivoEsito> listEsameObiettivoFigliList = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )			
		.add( Restrictions.isNotNull("lookupEsameObiettivoEsito") )	
		.list();	
		HashMap<Integer, Integer> listEsameObiettivoPadri = new HashMap<Integer,Integer>();
		Iterator<LookupEsameObiettivoEsito> listEsameObiettivoFigliIter = listEsameObiettivoFigliList.iterator();
		Integer id2 = null;
		while(listEsameObiettivoFigliIter.hasNext())
		{
			id2 = listEsameObiettivoFigliIter.next().getLookupEsameObiettivoEsito().getId();
			listEsameObiettivoPadri.put(id2, id2);
		}
		
		req.setAttribute("listEsameObiettivoPadri", listEsameObiettivoPadri);
		req.setAttribute("listEsameObiettivoFigliList", listEsameObiettivoFigliList);
		req.setAttribute("superList", superList);
		req.setAttribute("descrizioniEOTipo", descrizioniEOTipo);
		req.setAttribute("idApparato", 0);
		
		
		/* Nel caso la cartella clinica contiene già degli esami obiettivo
		 * lo scopo è quello di visualizzare nella pagina di edit gli esiti già
		 * checkati in precedenza.*/
		if(cc.getEsamiObiettivoApparato( null ).size()>0) {	
			
			ArrayList<EsameObiettivoEsito> superListChecked = new ArrayList();
						
			//req.setAttribute( "dataEsame", cc.getEsameObiettivos().iterator().next().getDataEsameObiettivo());
			
			Iterator eoList = cc.getEsamiObiettivoApparato( null ).iterator();
			
			while(eoList.hasNext()) {
				EsameObiettivo eo = (EsameObiettivo)eoList.next();
				if(eo.getEsameObiettivoEsitos().size()>0) {
					Iterator eoeList = eo.getEsameObiettivoEsitos().iterator();
					while(eoeList.hasNext()) {
						EsameObiettivoEsito eoeRe = (EsameObiettivoEsito) eoeList.next();						
						superListChecked.add(eoeRe);
					}
				}
			}
			req.setAttribute("superListChecked", superListChecked);
			
		}
		
		if (cc.getFebbres().size()>0) {
			req.setAttribute("isFebbre", true);
		}
		else {
			req.setAttribute("isFebbre", false);
		}
		
		
		if(cc.getEsamiObiettivoApparato( null ).size()>0) {
			req.setAttribute( "dataEsame", cc.getEsamiObiettivoApparato( null ).iterator().next().getDataEsameObiettivo());
		}
		else if (cc.getEsamiObiettivoApparato( null ).size()==0 && cc.getFebbres().size()>0) {
			req.setAttribute( "dataEsame", cc.getFebbres().iterator().next().getEntered());
		}
		//Fine Esame Obiettivo Generale
			
		//Inizio esame obiettivo particolare
		ArrayList<LookupEsameObiettivoApparati> apparati = (ArrayList<LookupEsameObiettivoApparati>)persistence.createCriteria(LookupEsameObiettivoApparati.class).list();
		req.setAttribute("apparati", apparati.iterator());
		Iterator<LookupEsameObiettivoApparati> apparatiIter = apparati.iterator();
		
		while(apparatiIter.hasNext())
		{
			LookupEsameObiettivoApparati apparato =  (LookupEsameObiettivoApparati)apparatiIter.next();
			ArrayList<EsameObiettivoSintoPatologia> sintopat = (ArrayList<EsameObiettivoSintoPatologia>) persistence.createCriteria( EsameObiettivoSintoPatologia.class )
					.add( Restrictions.eq( "cartellaClinica", cc ) )
					.add( Restrictions.eq( "apparato", apparato ) )
					.list();
			
			if( cc.getEsamiObiettivoApparato( apparato ).size() > 0 || sintopat.size() > 0 )
			{
	 			
				int idApparato		  = apparato.getId();
				//Recupero Bean Apparati
				LookupEsameObiettivoApparati leoa = (LookupEsameObiettivoApparati) persistence.find(LookupEsameObiettivoApparati.class, idApparato);
				req.setAttribute( "apparato"+idApparato, leoa );
				
				ArrayList<LookupEsameObiettivoTipo> listEsameObiettivoTipoPart = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
					.add( Restrictions.eq( "specifico", true ) )	
					.add( Restrictions.eq( "lookupEsameObiettivoApparati.id", leoa.getId() ) )
					.list();
				
				int sizeEsameObiettivoTipoPart = listEsameObiettivoTipoPart.size();	
						
				ArrayList<ArrayList<LookupEsameObiettivoEsito>> superListPart   = new ArrayList();
				ArrayList<ArrayList<LookupEsameObiettivoEsito>> superListFigliPart   = new ArrayList();
				
				
				ArrayList<String> descrizioniEOTipoPart 						= new ArrayList();
				
				for (int i = 0; i < sizeEsameObiettivoTipoPart; i++) {
					
					LookupEsameObiettivoTipo leot = (LookupEsameObiettivoTipo) listEsameObiettivoTipoPart.get(i);
					
					ArrayList<LookupEsameObiettivoEsito> listEsameObiettivo = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )
					.add( Restrictions.eq( "lookupEsameObiettivoTipo.id", leot.getId() ) )	
					.add( Restrictions.isNull("lookupEsameObiettivoEsito") )
					.list();					
								
					descrizioniEOTipo.add(listEsameObiettivo.get(0).getLookupEsameObiettivoTipo().getDescription());
					superList.add(listEsameObiettivo);
					
				}
				
				
				// La lista di tutti i padri
				ArrayList<LookupEsameObiettivoEsito> listEsameObiettivoFigliListPart = (ArrayList<LookupEsameObiettivoEsito>) persistence.createCriteria( LookupEsameObiettivoEsito.class )			
				.add( Restrictions.isNotNull("lookupEsameObiettivoEsito") )	
				.list();
				
				HashMap<Integer, Integer> listEsameObiettivoPadriPart = new HashMap<Integer,Integer>();
				Iterator<LookupEsameObiettivoEsito> listEsameObiettivoFigliIterPart = listEsameObiettivoFigliListPart.iterator();
				Integer idPart = null;
				while(listEsameObiettivoFigliIterPart.hasNext())
				{
					id = listEsameObiettivoFigliIterPart.next().getLookupEsameObiettivoEsito().getId();
					listEsameObiettivoPadriPart.put(id, id);
				}
		
				req.setAttribute("listEsameObiettivoPadri"+idApparato, listEsameObiettivoPadriPart);
				req.setAttribute("listEsameObiettivoFigliList"+idApparato, listEsameObiettivoFigliListPart);
				req.setAttribute("superList"+idApparato, superListPart);
				req.setAttribute("descrizioniEOTipo"+idApparato, descrizioniEOTipoPart);
				req.setAttribute("idApparato"+idApparato, idApparato);
				req.setAttribute("esameObiettivo"+idApparato, leoa );
				
				
				
				/*Nel caso la cartella clinica contiene già degli esami obiettivo
				 * lo scopo è quello di visualizzare nella pagina di edit gli esiti già
				 * checkati in precedenza.*/
				if( cc.getEsamiObiettivoApparato( leoa ).size() > 0 )
				{	
					
					ArrayList<EsameObiettivoEsito> superListCheckedPart = new ArrayList();
								
					req.setAttribute( "dataEsamePart"+idApparato, cc.getEsamiObiettivoApparato( leoa ).iterator().next().getDataEsameObiettivo());
					
					Iterator eoListPart = cc.getEsamiObiettivoApparato( leoa ).iterator();
					
					while(eoListPart.hasNext()) {
						EsameObiettivo eoPart = (EsameObiettivo)eoListPart.next();
						if(eoPart.getEsameObiettivoEsitos().size()>0) {
							Iterator eoeListPart = eoPart.getEsameObiettivoEsitos().iterator();
							while(eoeListPart.hasNext()) {
								EsameObiettivoEsito eoeRePart = (EsameObiettivoEsito) eoeListPart.next();						
								superListCheckedPart.add(eoeRePart);
							}
						}
					}
					req.setAttribute("superListCheckedPart"+idApparato, superListCheckedPart);
					
				}
				
				
				
				/**
				 * Gestione sezione SINTOMATOLOGIA/PATOLOGIA
				 */
				 //caricamento elenco possibili sintomi
				ArrayList<LookupEsameObiettivoSintomi> sintomi = (ArrayList<LookupEsameObiettivoSintomi>) persistence.createCriteria( LookupEsameObiettivoSintomi.class )
					.add( Restrictions.eq( "apparato", leoa ) )
					.addOrder( Order.asc( "level" ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				//caricamento elenco possibili insorgenze sintomi
				ArrayList<LookupEsameObiettivoInsorgenzaSintomi> insorgenze = (ArrayList<LookupEsameObiettivoInsorgenzaSintomi>) persistence.createCriteria( LookupEsameObiettivoInsorgenzaSintomi.class )
					.add( Restrictions.eq( "apparato", leoa ) )
					.addOrder( Order.asc( "level" ) )
					.addOrder( Order.asc( "description" ) )
					.list();
				
				//caricamento elenco possibili periodi insorgenze sintomi
				ArrayList<LookupEsameObiettivoPeriodoInsorgenzaSintomi> periodoInsorgenze = (ArrayList<LookupEsameObiettivoPeriodoInsorgenzaSintomi>) persistence
					.createCriteria( LookupEsameObiettivoPeriodoInsorgenzaSintomi.class )
						.add( Restrictions.eq( "apparato", leoa ) )
						.addOrder( Order.asc( "level" ) )
						.addOrder( Order.asc( "description" ) )
					.list();
				
				//caricamento record PATOLOGIE CONGENITE eventualmente già presente
				List<EsameObiettivoPatologieCongenite> patologieCongenite = persistence.createCriteria( EsameObiettivoPatologieCongenite.class )
					.add( Restrictions.eq( "cartellaClinica", cc ) )
					.add( Restrictions.eq( "apparato", leoa ) )
					.list();
				
				
				req.setAttribute( "sintomi"+idApparato, sintomi );
				req.setAttribute( "insorgenze"+idApparato, insorgenze );
				req.setAttribute( "periodoInsorgenze"+idApparato, periodoInsorgenze );
				if( sintopat.size() > 0 )
				{
					req.setAttribute( "sintopatologia"+idApparato, sintopat.get( 0 ) );
				}
				if( patologieCongenite.size() > 0 )
				{
					req.setAttribute( "patologieCongenite"+idApparato, patologieCongenite.get( 0 ) );
				}
			}
		}
		//Fine esame obiettivo particolare
		
		req.setAttribute("tdList", tdList);
		req.setAttribute("trasferimenti", trasfs);
		

		req.setAttribute("diagnosi", diagnosi);		
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		
		req.setAttribute("divDiario", stringaFromRequest("divDiario"));
		req.setAttribute("divAnamnesi", stringaFromRequest("divAnamnesi"));
		req.setAttribute("divEsameObiettivoGenerale", stringaFromRequest("divEsameObiettivoGenerale"));
		req.setAttribute("divEsameObiettivoParticolare", stringaFromRequest("divEsameObiettivoParticolare"));
		req.setAttribute("divRic", stringaFromRequest("divRic"));
		req.setAttribute("divDiagn", stringaFromRequest("divDiagn"));
		req.setAttribute("divDimis", stringaFromRequest("divDimis"));
		req.setAttribute("divChir", stringaFromRequest("divChir"));
		req.setAttribute("divTerap", stringaFromRequest("divTerap"));
		req.setAttribute("divTrasf", stringaFromRequest("divTrasf"));
		req.setAttribute("divList", stringaFromRequest("divList"));
		req.setAttribute("divListUrine", stringaFromRequest("divListUrine"));
		req.setAttribute("divListCoprologico", stringaFromRequest("divListCoprologico"));
		req.setAttribute("divListDiagnosticaImmagini", stringaFromRequest("divListDiagnosticaImmagini"));
		req.setAttribute("divListEsterni", stringaFromRequest("divListEsterni"));
		req.setAttribute("divListECG", stringaFromRequest("divListECG"));
		req.setAttribute("divListCitologico", stringaFromRequest("divListCitologico"));
		req.setAttribute("divListIsto", stringaFromRequest("divListIsto"));
		req.setAttribute("divListNecro", stringaFromRequest("divListNecro"));
		
		
		
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, persistence, utente, status, connection,req));
		
		
		
		req.setAttribute( "idOpsBdr", 		IdOperazioniBdr.getInstance() );
		req.setAttribute( "idRichiesteVarie", IdRichiesteVarie.getInstance() );
		
		gotoPage("popup", "/jsp/vam/cc/popupDetails_new.jsp");
	}
}
