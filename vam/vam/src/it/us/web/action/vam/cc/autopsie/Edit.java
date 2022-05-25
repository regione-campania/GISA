/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.autopsie;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.trasferimenti.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaCMF;
import it.us.web.bean.vam.AutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaFenomeniCadaverici;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;
import it.us.web.bean.vam.lookup.LookupCMF;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupAutopsiaModalitaConservazione;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.AutopsiaDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edit extends GenericAction {

	
	public void can() throws Exception
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
		final Logger logger = LoggerFactory.getLogger(Edit.class);		
		
		/*===================================================*/
		/*===================================================*/
		/* MODIFICA DELLA SCHEDA AUTOPTICA*/
		/*===================================================*/
		/*===================================================*/
		
		//VERSIONE PEZZOTTA JNDI
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		connection = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnection(connection);
				
		
		int idAutopsia  = interoFromRequest("idAutopsia");
		
		
		if(AutopsiaDAO.existNumRifMittente(connection, req.getParameter("numeroAccettazioneSigla"), idAutopsia))
		{
			setErrore("E' presente una richiesta di autopsia con lo stesso numero di riferimento mittente.");
			goToAction(new it.us.web.action.vam.cc.autopsie.Detail(), req, res);
		}
		
		
		
		Autopsia a = (Autopsia) persistence.find(Autopsia.class, idAutopsia);
		Date dataEsitoVecchia = a.getDataEsito();
		
		
				
		
		int patologiaDefinitiva = interoFromRequest("patologiaDefinitivaId");
		if(patologiaDefinitiva>0) 
			a.setPatologiaDefinitiva((LookupAutopsiaPatologiePrevalenti) persistence.find(LookupAutopsiaPatologiePrevalenti.class, patologiaDefinitiva));
		
		String operatoriString = req.getParameter("idOp");
		
		if(operatoriString!=null && !operatoriString.equals("")){
		String[] operatori = operatoriString.split(",");
		Set<SuperUtente> operatoriHash = new HashSet<SuperUtente>();
		for(int i=0;i<operatori.length;i++)
		{
			int			idOperatore = Integer.parseInt(operatori[i]);
			SuperUtente		user					= (SuperUtente) persistence.find( SuperUtente.class, idOperatore);
			operatoriHash.add(user);
		}
		a.setOperatori(operatoriHash);
		}
//		if(cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe() && cc.getCcPostTrasferimento()==false && cc.getCcPostTrasferimentoMorto()==false)
//		{
//			String provinciaRitrovamento = stringaFromRequest("provinciaRitrovamento");
//			int comuneRitrovamento = 0; 
//		
//			if (provinciaRitrovamento.equals("BN"))		
//				comuneRitrovamento = interoFromRequest("comuneRitrovamentoBN");
//			else if (provinciaRitrovamento.equals("NA"))	
//				comuneRitrovamento = interoFromRequest("comuneRitrovamentoNA");
//			else if (provinciaRitrovamento.equals("SA"))	
//				comuneRitrovamento = interoFromRequest("comuneRitrovamentoSA");
//			else if (provinciaRitrovamento.equals("CE"))	
//				comuneRitrovamento = interoFromRequest("comuneRitrovamentoCE");
//			else if (provinciaRitrovamento.equals("AV"))	
//				comuneRitrovamento = interoFromRequest("comuneRitrovamentoAV");
//				
//			ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class ).addOrder( Order.asc( "level" ) ).list();
//		
//			LookupComuni lc = null;
//		
//			Iterator listComuniIterator = listComuni.iterator();
//		
//			while(listComuniIterator.hasNext()) {			
//				lc = (LookupComuni) listComuniIterator.next();			
//				if (lc.getId() == comuneRitrovamento) 
//					a.setComuneRitrovamento(lc);
//			}
//		}
		
		BeanUtils.populate(a, req.getParameterMap());	
		
		if(a.getDataEsito()==null)
		{
			a.setModified(new Date());		
			a.setModifiedBy(utente);
		}
		else
		{
			if(dataEsitoVecchia==null)
			{
				a.setEnteredEsito(new Date());		
				a.setEnteredByEsito(utente);
			}
			a.setModifiedEsito(new Date());		
			a.setModifiedByEsito(utente);
		}
		
		/* Gestione Causa Morte iniziale => Scelta Singola */
		int idCM = interoFromRequest("causaMorteIniziale");
				
		
		/* Mi faccio dare la precedente causa di decesso. Se è diverso dal nuovo, 
		 * vuol dire che c'è
		 * stato un cambiamento di esito che, in BDR, deve corrispondere ad una nuova
		 * registrazione. Questo secondo la logica adottata in BDR circa le registrazioni
		 * di Autopsia*/
//		int idOldEsito = a.getLookupCMIs().iterator().next().getId();
//						
//		if (idOldEsito != idCM) {
//			
//			String dataMorte = stringaFromRequest("dataMorte");
//			
//			RegistrazioniCaninaResponse	res	= CaninaRemoteUtil.eseguiDecesso(cc.getAccettazione().getAnimale(), idCM, dataMorte );
//			setMessaggio("Aggiornata la BDR - Registrazione numero" + res.getIdRegistrazione());			
//			if (res == null) {
//				setErrore( "Si è verificato un errore durante l'aggiornamento in BDR" );
//				redirectTo("vam.cc.autopsie.Detail.us");
//			}
//		}
//		
				
		
		
		ArrayList<LookupCMI> lcmiList = (ArrayList<LookupCMI>) persistence.createCriteria( LookupCMI.class )
		.list();		
		
		LookupCMI lcmi = null;
		
		Iterator lcmiIterator = lcmiList.iterator();
		
		Set<LookupCMI> setCMI = new HashSet<LookupCMI>(0);
		while(lcmiIterator.hasNext()) {			
			lcmi = (LookupCMI) lcmiIterator.next();			
			if (lcmi.getId() == idCM) 
				setCMI.add(lcmi);	
		}
		a.setLookupCMIs((Set<LookupCMI>) setCMI);
		
				
		/* Gestione Modalità di conservazione*/				
		int idMC = interoFromRequest("modalitaConservazione");
		
		ArrayList<LookupAutopsiaModalitaConservazione> lmcList = (ArrayList<LookupAutopsiaModalitaConservazione>) persistence.createCriteria( LookupAutopsiaModalitaConservazione.class )
		.list();		
		
		LookupAutopsiaModalitaConservazione lmc = null;
		
		Iterator lmcIterator = lmcList.iterator();
		
		while(lmcIterator.hasNext()) {
			lmc = (LookupAutopsiaModalitaConservazione) lmcIterator.next();
			if (lmc.getId() == idMC)
				a.setLmc(lmc);		
		}
		
		int idMCRichiesta = interoFromRequest("modalitaConservazioneRichiesta");
		Iterator lmcIteratorRichiesta = lmcList.iterator();
		while(lmcIteratorRichiesta.hasNext()) {
			lmc = (LookupAutopsiaModalitaConservazione) lmcIteratorRichiesta.next();
			if (lmc.getId() == idMCRichiesta)
				a.setLmcRichiesta(lmc);		
		}
		
		
		/* Gestione del Laboratorio destinazione */
		int idNcp = interoFromRequest("lookupAutopsiaSalaSettoria");
		
		if(a.getLass().getEsterna() && idNcp!=a.getLass().getId())
		{
			Trasferimento t = null;
			if(!cc.getTrasferimenti().isEmpty())
			{
				t = cc.getTrasferimenti().iterator().next();
				persistence.delete(t);
				cc.setDataChiusura(null);
			}
		}
		
		ArrayList<LookupAutopsiaSalaSettoria> ltsList = (ArrayList<LookupAutopsiaSalaSettoria>) persistence.createCriteria( LookupAutopsiaSalaSettoria.class )
		.add( Restrictions.eq( "esameRiferimento", "Necroscopico" ) )
		.list();		
		
		LookupAutopsiaSalaSettoria lass = null;
		
		Iterator lassIterator = ltsList.iterator();
		
		while(lassIterator.hasNext()) {			
			lass = (LookupAutopsiaSalaSettoria) lassIterator.next();			
			if (lass.getId() == idNcp) 
				a.setLass(lass);						
		}
		
		/* Gestione Trasferimento automatico a seguito di richiesta necroscopico */
		if(
			(cc.getTrasferimenti().isEmpty() 				|| cc.getTrasferimenti().iterator().next().getAutomaticoPerNecroscopia()				) && 
			(cc.getTrasferimentiByCcPostTrasf().isEmpty() 	|| cc.getTrasferimentiByCcPostTrasf().iterator().next().getAutomaticoPerNecroscopia()	) && 
			a.getLass()!=null && 
			a.getLass().getEsterna()
		  )
		{
				trasferimentoPerNecroscopia(cc,a);
		}
				
		/* Gestione Fenomeni cadaverici */
		Set<LookupAutopsiaFenomeniCadaverici> lfcFigli = (Set<LookupAutopsiaFenomeniCadaverici>)objectListDaValore(LookupAutopsiaFenomeniCadaverici.class, "fc_");
		Set<LookupAutopsiaFenomeniCadaverici> lfcTutti = new HashSet<LookupAutopsiaFenomeniCadaverici>();
		for(LookupAutopsiaFenomeniCadaverici temp:lfcFigli)
		{
			for(LookupAutopsiaFenomeniCadaverici temp2:temp.getAlberoGenealogicoArray())
			{
				lfcTutti.add(temp2);
			}
		}
		if(!lfcTutti.isEmpty())
			a.setFenomeniCadaverici(lfcTutti);
		
		/* Gestione Causa Morte finali*/		
		
		//Vengono prima cancellate le vecchie cause, per poi fare spazio alle nuove
		AutopsiaCMF cmfDelete;
		Iterator cmfList = a.getCmf().iterator();
		while (cmfList.hasNext()) {
			cmfDelete = (AutopsiaCMF) cmfList.next();
			persistence.delete(cmfDelete);
		}
		
		ArrayList<LookupCMF> listaCMF = (ArrayList<LookupCMF>) persistence.createCriteria( LookupCMF.class )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		LookupCMF lookupCmf;
		AutopsiaCMF autopsiaCMF = null;
		
		int cmf = interoFromRequest("causaMorteFinale");
		if(cmf>0) 
		{
			lookupCmf = (LookupCMF) persistence.find(LookupCMF.class, cmf);
			autopsiaCMF = new AutopsiaCMF();
			autopsiaCMF.setAutopsia(a);
			autopsiaCMF.setLookupCMF(lookupCmf);
				
			String tipoCMF = req.getParameter("tipoCMF");
				
			if(tipoCMF!=null)
			{
				if (tipoCMF.equalsIgnoreCase("Provata")) 
					autopsiaCMF.setProvata(true);
				else 
					autopsiaCMF.setProvata(false);
			}					
			persistence.insert(autopsiaCMF);													
		}
	
		try {
		
			validaBean( a , new ToAdd() );
			
			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
			persistence.update(a);
				
			/* Gestione della sezione macroscopica => 
			 * Organi e esame morfologico degli organi*/
			
			//Essendo la Edit, vengono cancellate le vecchie per poi far spazio alle nuove
			AutopsiaOrganoPatologie aopDelete;
			Iterator aopList = a.getAops().iterator();
			while (aopList.hasNext()) {
				aopDelete = (AutopsiaOrganoPatologie) aopList.next();
				persistence.delete(aopDelete);
			}
			
			
			int set = interoFromRequest("numeroElementi");
			AutopsiaOrganoPatologie aop;
						
			for (int i = 1; i < set; i++) {
				
				aop = new AutopsiaOrganoPatologie();
				aop.setAutopsia(a);
				
				if (stringaFromRequest("esaminato_"+i).equalsIgnoreCase("Esaminato")) {
				
					ArrayList<LookupAutopsiaOrgani> lookupOrganiAutopsia = (ArrayList<LookupAutopsiaOrgani>) persistence.createCriteria( LookupAutopsiaOrgani.class )
					.add( Restrictions.eq( "description", req.getParameter("organo_"+i) ) ).list();
					
					aop.setEsaminato(true);
					
					aop.setLookupOrganiAutopsia(lookupOrganiAutopsia.get(0));
					aop.setLookupPatologiePrevalentiAutopsias(objectList(LookupAutopsiaPatologiePrevalenti.class, "op"+i+"_"));
					
					if (req.getParameter("altro_"+i) != null)
						aop.setAltro(req.getParameter("altro_"+i));
					
					persistence.insert(aop);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(aop);
					}
				}
				else if (stringaFromRequest("esaminato_"+i).equalsIgnoreCase("Normale")) {
					
					ArrayList<LookupAutopsiaOrgani> lookupOrganiAutopsia = (ArrayList<LookupAutopsiaOrgani>) persistence.createCriteria( LookupAutopsiaOrgani.class )
					.add( Restrictions.eq( "description", req.getParameter("organo_"+i) ) ).list();
					
					aop.setEsaminato(false);
					
					aop.setLookupOrganiAutopsia(lookupOrganiAutopsia.get(0));
					
					persistence.insert(aop);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(aop);
					}
				}
			}
			
			
			/* Gestione della sezione macroscopica => 
			 * Organi e patologie prevalenti per i tessuti*/
			int setTessuti = interoFromRequest("numeroElementiTessuti");
			AutopsiaOrganoPatologie aopTessuti;
							
			for (int i = 1; i < setTessuti; i++) {
				
				HashSet hs = new HashSet();					
				aopTessuti = new AutopsiaOrganoPatologie();
				aopTessuti.setAutopsia(a);
				
				if (stringaFromRequest("esaminatoTessuto_"+i).equalsIgnoreCase("Esaminato")) {
				
					ArrayList<LookupAutopsiaOrgani> lookupOrganiAutopsia = (ArrayList<LookupAutopsiaOrgani>) persistence.createCriteria( LookupAutopsiaOrgani.class )
					.add( Restrictions.eq( "description", req.getParameter("tessuto_"+i) ) ).list();
					
					String[] patologie = req.getParameterValues("patologieTessuti_"+i);
					
					if(patologie!=null)
					{
						for(int j=0;j<patologie.length;j++)
						{
							ArrayList<LookupAutopsiaPatologiePrevalenti> lookupAutopsiaPatologiePrevalenti = (ArrayList<LookupAutopsiaPatologiePrevalenti>) persistence.createCriteria( LookupAutopsiaPatologiePrevalenti.class )
									.add( Restrictions.eq( "id", Integer.parseInt(patologie[j])) ).list();
						
						
							if (!lookupAutopsiaPatologiePrevalenti.isEmpty())
								hs.add(lookupAutopsiaPatologiePrevalenti.get(0));
						}
					}
					
					aopTessuti.setEsaminato(true);
					
					aopTessuti.setLookupOrganiAutopsia(lookupOrganiAutopsia.get(0));
					aopTessuti.setLookupPatologiePrevalentiAutopsias(hs);
					
					if (req.getParameter("altroTessuto_"+i) != null)
						aopTessuti.setAltro(req.getParameter("altroTessuto_"+i));
					
					persistence.insert(aopTessuti);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(aopTessuti);
					}
				}
				
				else if (stringaFromRequest("esaminatoTessuto_"+i).equalsIgnoreCase("Normale")) {
					
					ArrayList<LookupAutopsiaOrgani> lookupOrganiAutopsia = (ArrayList<LookupAutopsiaOrgani>) persistence.createCriteria( LookupAutopsiaOrgani.class )
					.add( Restrictions.eq( "description", req.getParameter("tessuto_"+i) ) ).list();
						
					aopTessuti.setEsaminato(false);
					
					aopTessuti.setLookupOrganiAutopsia(lookupOrganiAutopsia.get(0));
										
					persistence.insert(aopTessuti);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(aopTessuti);
					}
				}
			}
			
			
			/* GESTIONE SEZIONI ESAMI PER OGNI ORGANO*/
			//Elimino i vecchi
			Object[] dettagliEsamiVecchi = a.getDettaglioEsami().toArray();
			for(int i=0;i<dettagliEsamiVecchi.length;i++)
			{
				persistence.delete(dettagliEsamiVecchi[i]);
			}
			//Inserisco i nuovi
			//Finchè il parametro organo+i è valorizzato, allora la riga i-esima esiste
			for(int i=1;stringaFromRequest("organo"+i)!=null &&  !stringaFromRequest("organo"+i).equals("");i++)
			{
				String esitiString= stringaFromRequest("esiti"+i);
				while(esitiString.indexOf("[")>=0)
					esitiString = esitiString.replace("[", "");
				while(esitiString.indexOf("]")>=0)
					esitiString = esitiString.replace("]", "");
				while(esitiString.indexOf(" ")>=0)
					esitiString = esitiString.replace(" ", "");
				String[] esitiArray= esitiString.split(",");
				if(!esitiArray[0].equals(""))
				{
					for(int j=0;j<esitiArray.length;j++)
					{
						AutopsiaOrganiTipiEsamiEsiti aoet = new AutopsiaOrganiTipiEsamiEsiti();
						LookupAutopsiaOrganiTipiEsamiEsiti lookupOTE = new LookupAutopsiaOrganiTipiEsamiEsiti();
						lookupOTE = (LookupAutopsiaOrganiTipiEsamiEsiti)persistence.find(LookupAutopsiaOrganiTipiEsamiEsiti.class, Integer.parseInt(esitiArray[j]));
					
						aoet.setLookupAutopsiaOrganiTipiEsamiEsiti(lookupOTE);
						aoet.setAutopsia(a);
						aoet.setNote(stringaFromRequest("note"+i));
						aoet.setValore(getValoreEsito(i, ""+lookupOTE.getId(), stringaFromRequest("esitiValore"+i)));
						
						persistence.insert(aoet);
						if(cc.getDataChiusura()!=null)
						{
							beanModificati.add(aoet);
						}
					}
				}
				else
				{
					AutopsiaOrganiTipiEsamiEsiti aoet = new AutopsiaOrganiTipiEsamiEsiti();
					//Recupero la lookup LookupAutopsiaOrganiTipiEsamiEsiti dove organo e tipo sono quelli selezionati e esito è nullo
								
					
//					LookupAutopsiaOrganiTipiEsamiEsiti lookupOTE = new LookupAutopsiaOrganiTipiEsamiEsiti();
//					lookupOTE = (LookupAutopsiaOrganiTipiEsamiEsiti) persistence.getNamedQuery("GetLookupOrganoTipoEsito_EsitoNullo")
//						.setInteger("idOrgano",interoFromRequest("organo"+i))
//						.setInteger("idTipo",interoFromRequest("tipo"+i) );
//					
//					aoet.setLookupAutopsiaOrganiTipiEsamiEsiti(lookupOTE);
					
					
					ArrayList<LookupAutopsiaOrganiTipiEsamiEsiti> lookupOTE = (ArrayList<LookupAutopsiaOrganiTipiEsamiEsiti>) persistence.getNamedQuery("GetLookupOrganoTipoEsito_EsitoNullo")
					.setInteger("idOrgano",interoFromRequest("organo"+i))
					.setInteger("idTipo",interoFromRequest("tipo"+i) ).list();
				
					aoet.setLookupAutopsiaOrganiTipiEsamiEsiti(lookupOTE.get(0));
										
					aoet.setAutopsia(a);
					aoet.setNote(stringaFromRequest("note"+i));
					
					persistence.insert(aoet);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(aoet);
					}
				}
				/* Cerco, per la riga i, l'esito da anagrafare */		
				String esitoDaAnagrafareDesc   = stringaFromRequest("esitoDaAnagrafare"+i);
				String esitoDaAnagrafareValore = stringaFromRequest("esitoDaAnagrafareValore"+i);
				if(esitoDaAnagrafareDesc!=null && !esitoDaAnagrafareDesc.equals(""))
				{
					AutopsiaOrganiTipiEsamiEsiti aoet = new AutopsiaOrganiTipiEsamiEsiti();
					LookupAutopsiaEsitiEsami esitoDaAnagrafare = new LookupAutopsiaEsitiEsami();
					esitoDaAnagrafare.setDescription(esitoDaAnagrafareDesc);
					esitoDaAnagrafare.setEnabled(true);
					//100000 come level è fittizio, per fare in modo che gli esiti anagrafati dall'utente 
					//risultino gli ultimi nelle liste
					esitoDaAnagrafare.setLevel(100000);
					persistence.insert(esitoDaAnagrafare);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(esitoDaAnagrafare);
					}	
					
					LookupAutopsiaOrganiTipiEsamiEsiti terna = new LookupAutopsiaOrganiTipiEsamiEsiti();
					terna.setEsito(esitoDaAnagrafare);
					terna.setLookupOrganiAutopsia((LookupAutopsiaOrgani)persistence.find(LookupAutopsiaOrgani.class, interoFromRequest("organo"+i)));
					terna.setLookupAutopsiaTipiEsami((LookupAutopsiaTipiEsami)persistence.find(LookupAutopsiaTipiEsami.class, interoFromRequest("tipo"+i)));
					terna.setEnabled(true);
					terna.setLevel(100000);
					persistence.insert(terna);	
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(terna);
					}
						
					aoet.setLookupAutopsiaOrganiTipiEsamiEsiti(terna);
					aoet.setAutopsia(a);
					aoet.setNote(stringaFromRequest("note"+i));
					aoet.setValore(esitoDaAnagrafareValore);
					
					persistence.insert(aoet);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(aoet);
					}
				}
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(a);
				}
			}
			/* FINE GESTIONE SEZIONI ESAMI PER OGNI ORGANO */
						
			persistence.commit();
		}
		catch (RuntimeException e)
		{
			try
			{		
				persistence.rollBack();				
			}
			catch (HibernateException e1)
			{				
				logger.error("Error during Rollback transaction" + e1.getMessage());
			}		
			logger.error("Cannot Modify Autopsia" + e.getMessage());
			throw e;		
		}
		
		String messaggioConferma = "Richiesta Esame necroscopico modificata.";
		if(a.getDataEsito()!=null)
			messaggioConferma = "Esame necroscopico modificato.";
		
		if(cc.getAccettazione().getAnimale().getDataSmaltimentoCarogna()==null)
			setMessaggio(messaggioConferma + "\nSi ricorda di inserire i dati sullo smaltimento nella pagina di accettazione prima di chiudere la cc.");
		else
			setMessaggio(messaggioConferma);
		redirectTo("vam.cc.autopsie.Detail.us");
				
	}
	
	@SuppressWarnings("unchecked")
	private void trasferimentoPerNecroscopia(CartellaClinica cc, Autopsia autopsia) throws Exception 
	{

		Trasferimento trasferimento = new Trasferimento();
		trasferimento.setCartellaClinica(cc);
		trasferimento.setDataRichiesta(autopsia.getDataAutopsia());
		trasferimento.setNotaRichiesta("Trasferimento effettuato in automatico dal sistema dopo la richiesta del necroscopico.");
		trasferimento.setAutomaticoPerNecroscopia(true);
		Set<LookupOperazioniAccettazione> operazioniRichieste = new HashSet<LookupOperazioniAccettazione>();
		operazioniRichieste.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdRichiesteVarie.esameNecroscopico));
		trasferimento.setOperazioniRichieste(operazioniRichieste);
		trasferimento.setModified( new Date() );
		trasferimento.setModifiedBy( utente );
		trasferimento.setEntered( trasferimento.getModified() );
		trasferimento.setEnteredBy( utente );
		trasferimento.setClinicaOrigine( utente.getClinica() );
		trasferimento.setUrgenza( true );
		
		//Rimando alla clinica appropriata se si è scelti come laboratorio di destinazione IZSM o Unina, 
		//da valutare per gli altri (DOGPARK e PROTEG) che non sono cliniche.
		if(autopsia.getLass().getId()==5)
			trasferimento.setClinicaDestinazione(((ArrayList<Clinica>) persistence.createCriteria( Clinica.class ).add(Restrictions.ilike("nomeBreve", "unina", MatchMode.ANYWHERE)).list()).get(0));
		else if(autopsia.getLass().getId()==8)
			trasferimento.setClinicaDestinazione(((ArrayList<Clinica>) persistence.createCriteria( Clinica.class ).add(Restrictions.ilike("nomeBreve", "izsm-avellino", MatchMode.ANYWHERE)).list()).get(0));
		else if(autopsia.getLass().getId()==9)
			trasferimento.setClinicaDestinazione(((ArrayList<Clinica>) persistence.createCriteria( Clinica.class ).add(Restrictions.ilike("nomeBreve", "izsm-benevento", MatchMode.ANYWHERE)).list()).get(0));
		else if(autopsia.getLass().getId()==10)
			trasferimento.setClinicaDestinazione(((ArrayList<Clinica>) persistence.createCriteria( Clinica.class ).add(Restrictions.ilike("nomeBreve", "izsm-caserta", MatchMode.ANYWHERE)).list()).get(0));
		else if(autopsia.getLass().getId()==4)
			trasferimento.setClinicaDestinazione(((ArrayList<Clinica>) persistence.createCriteria( Clinica.class ).add(Restrictions.ilike("nomeBreve", "izsm-portici", MatchMode.ANYWHERE)).list()).get(0));
		else if(autopsia.getLass().getId()==11)
			trasferimento.setClinicaDestinazione(((ArrayList<Clinica>) persistence.createCriteria( Clinica.class ).add(Restrictions.ilike("nomeBreve", "izsm-salerno", MatchMode.ANYWHERE)).list()).get(0));
			
		//La cartella clinica attuale viene chiusa dopo il trasferimento dell'animale
		cc.setDataChiusura(autopsia.getDataAutopsia());
		
		validaBean( trasferimento,  new ToAdd()  );
		
		persistence.insert( trasferimento );
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(trasferimento);
		}
		cc.setModified( new Date() );
		cc.setModifiedBy( utente );
		persistence.update( cc );		
	}
	
	private String getValoreEsito(int riga,String idEsito,String esitiValoreRiga)
	{
		String[] array = esitiValoreRiga.split("&&&&&");
		for(int i=0;i<array.length;i++)
		{
			String temp = array[i];
			String[] array2 = temp.split("###");
			if(array2[0].equals(idEsito))
			{
				if(array2.length==2)
					return array2[1];
				else
					return "";
			}
		}
		return "";
	}
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Necroscopia";
	}
				
}	



