/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiObiettivo;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameObiettivo;
import it.us.web.bean.vam.EsameObiettivoEsito;
import it.us.web.bean.vam.EsameObiettivoPatologieCongenite;
import it.us.web.bean.vam.EsameObiettivoSintoPatologia;
import it.us.web.bean.vam.Febbre;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoPeriodoInsorgenzaSintomi;
import it.us.web.bean.vam.lookup.LookupEsameObiettivoTipo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.vam.DiarioClinicoUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
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
		setSegnalibroDocumentazione("esameObiettivo");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Edit.class);
		
		int idApparato = interoFromRequest("idApparato");
			
		
		/* Cancellazione del precedente esame obiettivo */
		Set<EsameObiettivo> listEO = (Set) cc.getEsameObiettivos();
		Iterator iteratorEO = listEO.iterator();
		Iterator iteratorEOE;
		EsameObiettivo eoPrev;
		EsameObiettivoEsito eoePrev;
		
		while(iteratorEO.hasNext()){			
			eoPrev = (EsameObiettivo)iteratorEO.next();
			iteratorEOE = eoPrev.getEsameObiettivoEsitos().iterator();
			while(iteratorEOE.hasNext()) {
				eoePrev = (EsameObiettivoEsito) iteratorEOE.next();
				
				//Se è un esame obiettivo generale dal quale ho avuto richiesta di modifica				
				if (idApparato==0 && eoePrev.getLookupEsameObiettivoEsito().getLookupEsameObiettivoTipo().getSpecifico()==false)
				{
					persistence.delete(eoePrev);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(eoePrev);
					}
				}
				//Caso in cui si tenta di inserire un esame obiettivo particolare, dopo quello generale
				else if (idApparato!=0 && eoePrev.getLookupEsameObiettivoEsito().getLookupEsameObiettivoTipo().getSpecifico()==false)
					break;
				//Se invece è un esame obiettivo specifico di un determinato apparato
				else if (idApparato!=0 && eoePrev.getLookupEsameObiettivoEsito().getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati().getId()== idApparato)
				{
					persistence.delete(eoePrev);				
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(eoePrev);
					}
				}							
			}
			
			//Se è un esame obiettivo generale dal quale ho avuto richiesta di modifica			
			if (idApparato==0 && eoPrev.getLookupEsameObiettivoTipo().getSpecifico()==false)
			{
				persistence.delete(eoPrev);
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(eoPrev);
				}
			}
			//Caso in cui si tenta di inserire un esame obiettivo particolare, dopo quello generale
			if (idApparato!=0 && eoPrev.getLookupEsameObiettivoTipo().getSpecifico()==false)
				break;
			//Se invece è un esame obiettivo specifico di un determinato apparato			
			else if (idApparato!=0 && eoPrev.getLookupEsameObiettivoTipo().getLookupEsameObiettivoApparati().getId()== idApparato)
			{
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(eoPrev);
				}
				persistence.delete(eoPrev);		
			}			
					
		}
		
		
		
		/* Cancellazione del precedente settaggio, se esiste, di febbre */
		Set<Febbre> listFebbre = (Set) cc.getFebbres();
		
		if (!listFebbre.isEmpty()) {
			Iterator iteratorFebbre = listFebbre.iterator();
			while(iteratorFebbre.hasNext()) {
				Febbre febbre = (Febbre) iteratorFebbre.next();
				persistence.delete(febbre);
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(febbre);
				}
			}
		}
		persistence.commit();
		
		/* Inserimento dell'esame obiettivo editato*/		
		Date   dateEsameObiettivo = DateUtils.parseDateUtil(req.getParameter("dataEsameObiettivo"));	
				
		int set 	 = interoFromRequest("numeroElementi");
		String esito = null;

		//ArrayList che serve per il diario clinico 
		ArrayList<EsameObiettivo> esamiArray = new ArrayList<EsameObiettivo>();

		Febbre f = new Febbre();
		
		try {
			
			
			/* Scorro la lista di tutti gli elementi e per ognuno controllo l'esito */
			for (int i=1; i<set; i++) {
								
				EsameObiettivo eo = new EsameObiettivo();
								
				eo.setDataEsameObiettivo(dateEsameObiettivo);
				eo.setCartellaClinica(cc);		
				eo.setEntered(new Date());		
				eo.setEnteredBy(utente);
				eo.setModified(new Date());		
				eo.setModifiedBy(utente);
				
				ArrayList<LookupEsameObiettivoTipo> lookupEsameObiettivoTipo = (ArrayList<LookupEsameObiettivoTipo>) persistence.createCriteria( LookupEsameObiettivoTipo.class )
				.add( Restrictions.eq( "description", req.getParameter("descrizione_"+i) ) ).list();
								
				eo.setLookupEsameObiettivoTipo(lookupEsameObiettivoTipo.get(0));
								
				esito = req.getParameter("_"+i);
				
				if (esito.equalsIgnoreCase("Normale")) {		
					
					eo.setNormale(true);					
					persistence.insert(eo);
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(eo);
					}
					
					//per il diario clinico
					esamiArray.add( eo );
				}
				
				else if (esito.equals("Anormale")) {
					
					eo.setNormale(false);						
					persistence.insert(eo);						
					if(cc.getDataChiusura()!=null)
					{
						beanModificati.add(eo);
					}
					
					//per il diario clinico
					Set<EsameObiettivoEsito> esiti = new HashSet<EsameObiettivoEsito>();
					
					Iterator<LookupEsameObiettivoEsito> setEOEsito = objectList(LookupEsameObiettivoEsito.class, "op_").iterator();
					while(setEOEsito.hasNext()) {
						
						EsameObiettivoEsito eoe = new EsameObiettivoEsito();
						eoe.setEntered(new Date());
						eoe.setEnteredBy(utente);
						eoe.setModified(new Date());
						eoe.setModifiedBy(utente);
						eoe.setDataEsameObiettivo(dateEsameObiettivo);							
					
						LookupEsameObiettivoEsito leoe = setEOEsito.next();						
												
						if (leoe.getLookupEsameObiettivoTipo().getId() == lookupEsameObiettivoTipo.get(0).getId()) {	
							eoe.setLookupEsameObiettivoEsito(leoe);							
							eoe.setEsameObiettivo(eo);	
							persistence.insert(eoe);	
							if(cc.getDataChiusura()!=null)
							{
								beanModificati.add(eoe);
							}
							//per il diario clinico
							esiti.add( eoe );
						}
						
					}
					
					//per il diario clinico
					eo.setEsameObiettivoEsitos( esiti );
					esamiArray.add( eo );
				}
				
			}
			
			
			
			
			
			/* Gestione della febbre */
			String esitoTemperatura = null;
			
			if (req.getParameter("_"+9999) != null)
				//temperatura aggiornata
				esitoTemperatura = req.getParameter("_"+9999);
			else if (req.getParameter("_"+9998) != null)
				//nuova temperatura
				esitoTemperatura = req.getParameter("_"+9998);
			else
				esitoTemperatura = "";
			
			f.setCartellaClinica(cc);		
			f.setEntered(new Date());		
			f.setEnteredBy(utente);
			f.setModified(new Date());		
			f.setModifiedBy(utente);
			
			String temperatura = "Non previsto";
			if (esitoTemperatura.equalsIgnoreCase("Normale"))
			{		
				f.setNormale(true);					
				persistence.insert(f);
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(f);
				}
				temperatura = "Normale";
			}
			
			if (esitoTemperatura.equalsIgnoreCase("Anormale"))
			{		
				f.setNormale(false);
				f.setTemperatura(floatFromRequest("valore_temperatura"));
				persistence.insert(f);
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(f);
				}
				temperatura = "" + f.getTemperatura();
			}
			
			LookupEsameObiettivoApparati apparato = null;
			if( idApparato > 0 )
			{
				apparato = (LookupEsameObiettivoApparati) persistence.find( LookupEsameObiettivoApparati.class, idApparato );
			}
			
			if( esamiArray.size() > 0 || f.getId() > 0 )
			{
				DiarioClinicoUtil.save( cc, apparato, dateEsameObiettivo, temperatura, esamiArray, utente, persistence );
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
			logger.error("Cannot save Esame Obiettivo" + e.getMessage());
			throw e;		
		}
		
		/**
		 * Gestione inserimento/modifica sezioni SINTOMATOLOGIA/PATOLOGIA
		 */
		if( idApparato != 0 )
		{
			LookupEsameObiettivoApparati leoa = (LookupEsameObiettivoApparati) persistence.find(LookupEsameObiettivoApparati.class, idApparato);
	
			EsameObiettivoSintoPatologia sintopatologia = new EsameObiettivoSintoPatologia();
			
			ArrayList<EsameObiettivoSintoPatologia> sintopats = (ArrayList<EsameObiettivoSintoPatologia>) persistence.createCriteria( EsameObiettivoSintoPatologia.class )
				.add( Restrictions.eq( "cartellaClinica", cc ) )
				.add( Restrictions.eq( "apparato", leoa ) )
				.list();
			boolean edit = false;
			if( sintopats.size() > 0 )
			{
				sintopatologia = sintopats.get( 0 );
				edit = true;
			}
			
			BeanUtils.populate( sintopatologia, req.getParameterMap() );
			
			LookupEsameObiettivoInsorgenzaSintomi insorgenza = (LookupEsameObiettivoInsorgenzaSintomi) persistence
				.find( LookupEsameObiettivoInsorgenzaSintomi.class, interoFromRequest( "insorgenzaSintomiString" ) );
			
			LookupEsameObiettivoPeriodoInsorgenzaSintomi periodoInsorgenzaSintomi = (LookupEsameObiettivoPeriodoInsorgenzaSintomi) persistence
				.find( LookupEsameObiettivoPeriodoInsorgenzaSintomi.class, interoFromRequest( "periodoInsorgenzaSintomiString" ) );
			
			Set<LookupEsameObiettivoSintomi> sintomi = objectList( LookupEsameObiettivoSintomi.class, "sin_" );
			
			sintopatologia.setModified( new Date() );
			sintopatologia.setModifiedBy( utente );
			sintopatologia.setInsorgenzaSintomi( insorgenza );
			sintopatologia.setPeriodoInsorgenzaSintomi( periodoInsorgenzaSintomi );
			sintopatologia.setEsameObiettivoSintomis( sintomi );
			if( !edit )
			{
				sintopatologia.setEntered( sintopatologia.getModified() );
				sintopatologia.setEnteredBy( utente );
				sintopatologia.setApparato( leoa );
				sintopatologia.setCartellaClinica( cc );
				
				persistence.insert( sintopatologia );
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(sintopatologia);
				}
			}
			else
			{
				persistence.update( sintopatologia );
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(sintopatologia);
				}
			}
		}
		
		/**
		 * Gestione inserimento/modifica sezioni PATOLOGIE CONGENITE
		 */
		if( idApparato != 0 )
		{
			LookupEsameObiettivoApparati leoa = (LookupEsameObiettivoApparati) persistence.find(LookupEsameObiettivoApparati.class, idApparato);
	
			EsameObiettivoPatologieCongenite patologia = new EsameObiettivoPatologieCongenite();
			
			List<EsameObiettivoPatologieCongenite> patologieCongenite = persistence.createCriteria( EsameObiettivoPatologieCongenite.class )
				.add( Restrictions.eq( "cartellaClinica", cc ) )
				.add( Restrictions.eq( "apparato", leoa ) )
				.list();
			boolean edit = false;
			if( patologieCongenite.size() > 0 )
			{
				patologia = patologieCongenite.get( 0 );
				edit = true;
			}
			
			BeanUtils.populate( patologia, req.getParameterMap() );
			
			patologia.setModified( new Date() );
			patologia.setModifiedBy( utente );
			if( !edit )
			{
				patologia.setEntered( patologia.getModified() );
				patologia.setEnteredBy( utente );
				patologia.setApparato( leoa );
				patologia.setCartellaClinica( cc );
				
				persistence.insert( patologia );
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(patologia);
				}
			}
			else
			{
				persistence.update( patologia );
				if(cc.getDataChiusura()!=null)
				{
					beanModificati.add(patologia);
				}
			}
		}
		
		cc.setModified( new Date() );
		cc.setModifiedBy( utente );
		persistence.update( cc );
		persistence.commit();
		
			
		
		if( idApparato == 0 )
		{
			if( esamiArray.size() > 0 || f.getId() > 0 )
			{
				setMessaggio("Esame Obiettivo inserito");
			}
			else
			{
				setErrore( "Selezionare almeno un esito" );
			}
			redirectTo("vam.cc.esamiObiettivo.ToDetailGenerale.us");
		}
		else
		{
			setMessaggio("Esame Obiettivo inserito");
			redirectTo("vam.cc.esamiObiettivo.ToDetailSpecifico.us?idApparato="+idApparato);
		}
		
	}
	
	
	@Override
	public String getDescrizione()
	{
		return "Modifica Esame Obiettivo";
	}
}
