/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Reimmissioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupDetentori;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.sinantropi.lookup.LookupTipiDocumento;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddMarini extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_anagrafica");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(AddMarini.class);
					
		Sinantropo s =  new Sinantropo();
		
		BeanUtils.populate(s, req.getParameterMap());
		s.setSinantropo(false);
		s.setZoo(false);
		s.setMarini(true);
		
		int idEta = interoFromRequest( "idEta" );
		if(idEta>0)
			s.setEta((LookupSinantropiEta) persistence.find( LookupSinantropiEta.class, interoFromRequest( "idEta" )));
		else
			s.setEta( null );
		
		s.setEntered(new Date());		
		s.setEnteredBy(utente);
		s.setModified(new Date());		
		s.setModifiedBy(utente);
		
		
		/* Gestione Specie Sinantropi */
		int specie = interoFromRequest("specieSinantropo");
		int idSS = 0;
		
		if (specie == 1)		
			idSS = interoFromRequest("tipologiaSinantropoM");
		else if (specie == 2)
			idSS = interoFromRequest("tipologiaSinantropoRA");
		else if (specie == 3)
			idSS = interoFromRequest("tipologiaSinantropoU");
		
		ArrayList<LookupSpecieSinantropi> lssList = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
		.list();		
		
		LookupSpecieSinantropi lss = null;
		
		Iterator lssIterator = lssList.iterator();
		
		Set<LookupSpecieSinantropi> setSS = new HashSet<LookupSpecieSinantropi>(0);
		while(lssIterator.hasNext()) {			
			lss = (LookupSpecieSinantropi) lssIterator.next();			
			if (lss.getId() == idSS){ 
				s.setLookupSpecieSinantropi(lss);
				s.setRazza(lss.getDescription());
			}
		}
		
		
		/*GESTIONE CATTURA*/		
		Catture c = new Catture();
		BeanUtils.populate(c, req.getParameterMap());	
		
		c.setEntered(new Date());		
		c.setEnteredBy(utente);
		c.setModified(new Date());		
		c.setModifiedBy(utente);
		
		String provinciaCattura = stringaFromRequest("provinciaCattura");
		int comuneCattura = 0; 
		
		if (provinciaCattura.equals("BN"))		
			comuneCattura = interoFromRequest("comuneCatturaBN");
		else if (provinciaCattura.equals("NA"))	
			comuneCattura = interoFromRequest("comuneCatturaNA");
		else if (provinciaCattura.equals("SA"))	
			comuneCattura = interoFromRequest("comuneCatturaSA");
		else if (provinciaCattura.equals("CE"))	
			comuneCattura = interoFromRequest("comuneCatturaCE");
		else if (provinciaCattura.equals("AV"))	
			comuneCattura = interoFromRequest("comuneCatturaAV");
				
		
/*		ArrayList<LookupComuni> listComuni = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.addOrder( Order.asc( "level" ) )
		.list(); 
		
		LookupComuni lc = null;*/
		
		LookupComuni lc = (LookupComuni)persistence.find(LookupComuni.class, comuneCattura);
		c.setComuneCattura(lc);
		
//		Iterator listComuniIterator = listComuni.iterator();
		
		Set<Catture> setCatture = new HashSet<Catture>(0);
/*		while(listComuniIterator.hasNext()) {			
			lc = (LookupComuni) listComuniIterator.next();			
			if (lc.getId() == comuneCattura) 
				c.setComuneCattura(lc);
		} */
		c.setSinantropo(s);
		setCatture.add(c);
		s.setCattureis(setCatture);
		
		
		
		/*GESTIONE DETENZIONE*/
		Detenzioni d = new Detenzioni();
		BeanUtils.populate(d, req.getParameterMap());	
		
		String provinciaDetenzione = stringaFromRequest("provinciaDetenzione");
		int comuneDetenzione = 0; 
		
		if (provinciaDetenzione.equals("BN"))		
			comuneDetenzione = interoFromRequest("comuneDetenzioneBN");
		else if (provinciaDetenzione.equals("NA"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneNA");
		else if (provinciaDetenzione.equals("SA"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneSA");
		else if (provinciaDetenzione.equals("CE"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneCE");
		else if (provinciaDetenzione.equals("AV"))	
			comuneDetenzione = interoFromRequest("comuneDetenzioneAV");
				
		
/*		ArrayList<LookupComuni> listComuni2 = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		LookupComuni lc2 = null;*/
		
		if (comuneDetenzione!=0){
			LookupComuni lc2 = (LookupComuni)persistence.find(LookupComuni.class, comuneDetenzione);
			d.setComuneDetenzione(lc2);
		}
		
//		Iterator listComuniIterator2 = listComuni2.iterator();
		
		Set<Detenzioni> setDetenzioni = new HashSet<Detenzioni>(0);
/*		while(listComuniIterator2.hasNext()) {			
			lc2 = (LookupComuni) listComuniIterator2.next();			
			if (lc2.getId() == comuneDetenzione) 
				d.setComuneDetenzione(lc2);
		}*/
		d.setCatture(c);
		setDetenzioni.add(d);
		c.setDetenzionis(setDetenzioni);		
//		d.setSinantropo(s);
//		setDetenzioni.add(d);
//		s.setDetenzionis(setDetenzioni);
		
		/* Gestione Tipologia di detentore */		
		int idDetentore = interoFromRequest("tipologiaDetentore");
		
		ArrayList<LookupDetentori> ldList = (ArrayList<LookupDetentori>) persistence.createCriteria( LookupDetentori.class )
		.list();		
		
		LookupDetentori ld = null;
		
		Iterator ldIterator = ldList.iterator();
		
		//Set<LookupTipiDocumento> setTD = new HashSet<LookupTipiDocumento>(0);
		while(ldIterator.hasNext()) {			
			ld = (LookupDetentori) ldIterator.next();			
			if (ld.getId() == idDetentore) 				
				d.setLookupDetentori(ld);
		}
		
		
		/* Gestione Tipologie documento del privato detentore */
		int idTD = interoFromRequest("tipologiaDocumento");
		
		ArrayList<LookupTipiDocumento> ltdList = (ArrayList<LookupTipiDocumento>) persistence.createCriteria( LookupTipiDocumento.class )
		.list();		
		
		LookupTipiDocumento ltd = null;
		
		Iterator ltdIterator = ltdList.iterator();
		
		Set<LookupTipiDocumento> setTD = new HashSet<LookupTipiDocumento>(0);
		while(ltdIterator.hasNext()) {			
			ltd = (LookupTipiDocumento) ltdIterator.next();			
			if (ltd.getId() == idTD) 				
				d.setLookupTipologiaDocumento(ltd);
		}
		
		
		
		/*GESTIONE RE-IMMISSIONE*/
		Reimmissioni r = new Reimmissioni();
		BeanUtils.populate(r, req.getParameterMap());	
		
		String provinciaReimmissione = stringaFromRequest("provinciaReimmissione");
		int comuneReimmissione = 0; 
		
		if (provinciaReimmissione.equals("BN"))		
			comuneReimmissione = interoFromRequest("comuneReimmissioneBN");
		else if (provinciaReimmissione.equals("NA"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneNA");
		else if (provinciaReimmissione.equals("SA"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneSA");
		else if (provinciaReimmissione.equals("CE"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneCE");
		else if (provinciaReimmissione.equals("AV"))	
			comuneReimmissione = interoFromRequest("comuneReimmissioneAV");
				
		
/*		ArrayList<LookupComuni> listComuni3 = (ArrayList<LookupComuni>) persistence.createCriteria( LookupComuni.class )
		.addOrder( Order.asc( "level" ) )
		.list();
		
		LookupComuni lc3 = null;*/
		LookupComuni lc3 = (LookupComuni)persistence.find(LookupComuni.class, comuneReimmissione);
		r.setComuneReimmissione(lc3);
		
//		Iterator listComuniIterator3 = listComuni3.iterator();
		
		Set<Reimmissioni> setReimmissioni = new HashSet<Reimmissioni>(0);
/*		while(listComuniIterator3.hasNext()) {			
			lc3 = (LookupComuni) listComuniIterator3.next();			
			if (lc3.getId() == comuneReimmissione) 
				r.setComuneReimmissione(lc3);
		}*/
		r.setCatture(c);		
				
		
		/* SETTAGGIO DEL NUMERO ASSEGNATO AL SINANTROPO */
		s.setNumeroAutomatico(SinantropoUtil.getNumero(persistence));
				
		
		try {
			
			validaBean( s, new ToAdd() );
			validaBean( c, new ToAdd() );
			validaBean( d, new ToAdd() );
			validaBean( r, new ToAdd() );
			
			if (SinantropoUtil.checkUniquenessNumeroUfficiale(persistence, stringaFromRequest("numeroUfficiale")) == false) {
				setErrore( "Numero Ufficiale dell'Istituto Faunistico già presente in BDR" );
				goToAction( new ToAdd() );
			}
			else if (SinantropoUtil.checkUniquenessMc(persistence, stringaFromRequest("mc")) == false) {
				setErrore( "Microchip già presente in BDR" );
				goToAction( new ToAdd() );
			}
			else if (SinantropoUtil.checkUniquenessCodiceIspra(persistence, stringaFromRequest("codiceIspra")) == false) {
				setErrore( "Codice ISPRA già presente in BDR" );
				goToAction( new ToAdd() );
			}
			else {	
				//Inserimento cattura
				if (dataFromRequest("dataCattura")!=null && !stringaFromRequest("provinciaCattura").equals("X")) {
					s.setLastOperation("RINVENIMENTO");
					persistence.insert(c);
				}
					
				//Inserimento detenzione
				if (dataFromRequest("dataDetenzioneDa")!=null) {
					s.setLastOperation("DETENZIONE");
					persistence.insert(d);
				}
				//Inserimento re-immissione
				if (dataFromRequest("dataReimmissione")!=null && !stringaFromRequest("provinciaReimmissione").equals("X")){
					s.setLastOperation("RILASCIO");
					persistence.insert(r);
					c.setReimmissioni(r);
					persistence.update(c);
				}
				
				//Inserimento Sinantropo
				persistence.insert(s);								
			}						
		
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
			logger.error("Cannot save Sinantropo" + e.getMessage());
			throw e;		
		}
				
		
		setMessaggio( "Registrazione Animale marino effettuata" );		
		
		redirectTo  ( "sinantropi.DetailMarini.us?idSinantropo="+s.getId()+"&&interactiveMode="+stringaFromRequest("interactiveMode"));
			
	}	
}



