/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.controller;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.*;

import ext.aspcfs.modules.apiari.base.ModelloC;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apicolture.actions.CfUtil;

import org.apache.batik.svggen.font.table.LookupList;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Revenue;
import org.aspcfs.modules.accounts.base.RevenueList;
import org.aspcfs.modules.actionlist.base.ActionLists;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.bloccocu.base.EventoBloccoCu;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.NucleoIspettivo;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.RispostaDwrCodicePiano;
import org.aspcfs.utils.TipoIspezioniRendicontazione;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ObjectValidator.java,v 1.1.2.3 2004/09/08 16:37:11 partha
 *          Exp $
 * @created September 3, 2004
 */
public class ObjectValidator {

  private static int REQUIRED_FIELD = 2004090301;
  private static int IS_BEFORE_TODAY = 2004090302;
  private static int PUBLIC_ACCESS_REQUIRED = 2004090303;
  private static int INVALID_DATE = 2004090801;
  private static int INVALID_NUMBER = 2004091001;
  private static int INVALID_EMAIL = 2004091002;
  private static int INVALID_NOT_REQUIRED_DATE = 2004091003;
  private static int INVALID_EMAIL_NOT_REQUIRED = 2005060801;
  private static int PAST_DATE = 2006110117;
  private static int SIZE_FILE = 1048576 *3;
  
  
  public static int cuPregresso = -1 ;
  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param db           Description of the Parameter
   * @param object       Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean validate(ActionContext ctx, SystemStatus systemStatus, Connection db, Object object) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "ObjectValidator-> Checking object: " + object.getClass().getName());
    }
    boolean isValid = true ; 
    // TODO: use required fields and warnings from systemStatus; this might
    //       be a list of ValidationTasks that perform complex tasks

    // NOTE: For now, just code the validation tasks in

    // Organization
    
    if (object.getClass().getName() .equals("org.aspcfs.modules.diffida.base.Ticket"))
	  {
    	ResultSet rs =null ;
    	org.aspcfs.modules.diffida.base.Ticket diffida = (org.aspcfs.modules.diffida.base.Ticket) object ;
    	String sql="select distinct diffide.id_Controllo_ufficiale from norme_violate_sanzioni "+ 
    			"join ticket diffide on diffide.ticketid = idticket and tipologia=11 "
    			+ "join ticket cu on cu.id_controllo_ufficiale=diffide.id_controllo_ufficiale and cu.tipologia=3 "
    			+ "join ticket nc on nc.ticketid=diffide.id_nonconformita and nc.tipologia=8 "+
    			"where diffide.id_Controllo_ufficiale =? and norme_violate_sanzioni.id_norma=? "
    			//+ "and norme_violate_sanzioni.stato_diffida = 0  "
    			+ "and diffide.ticketid not in (?) and diffide.trashed_date is null and cu.trashed_date is null "
    			+ "and current_timestamp<= (cu.assigned_date + interval '5 years') and norme_violate_sanzioni.org_id = ? and nc.trashed_date is null ";
  
    	PreparedStatement pst = db.prepareStatement(sql);
    	pst.setString(1, diffida.getIdControlloUfficiale());
    	pst.setInt(3,diffida.getId());
    	pst.setInt(4,diffida.getOrgId());
    						
    	for(String idNorma : diffida.getIdNormeViolate())
    	{
    		pst.setInt(2,Integer.parseInt(idNorma));
    		rs = pst.executeQuery();
    		if (rs.next())
    		{
    			isValid = false;
    			addError2(systemStatus, object, "listanorme", "L'operatore risulta gia' diffidato per questa norma.");
    		}
    	}
    						
    						
    	
    	
    	
	  }
    
   
    if (object.getClass().getName() .equals("ext.aspcfs.modules.apiari.base.ModelloC"))
	  { 
    	ModelloC modello = (ModelloC)object ;
    	
    	//if (modello.getDataMovimentazione().after(new Timestamp(System.currentTimeMillis())))
      	//{
      		//isValid = false;
    		//addError2(systemStatus, object, "dataMovimentazione", "Data Successiva alla data Odierna");
      	//}
	  }
    
    if (object.getClass().getName() .equals("ext.aspcfs.modules.apiari.base.Stabilimento"))
	  { 
  	
  	Stabilimento stabilimento = (Stabilimento)object ;
  	
  	
  	if (stabilimento.getDataApertura().after(new Timestamp(System.currentTimeMillis())))
  	{
  		isValid = false;
			addError2(systemStatus, object, "dataInizio", "Data Successiva alla data Odierna");
  	}
  	
  	if (stabilimento.getSedeOperativa()== null || stabilimento.getSedeOperativa().getComune()<=0  )
  	{
  		isValid = false;
			addError2(systemStatus, object, "comune", "Comune Richiesto");
  	}

  	if (stabilimento.getSedeOperativa()!= null && stabilimento.getSedeOperativa().getLatitudine()<=0 )
  	{
  		isValid = false;
			addError2(systemStatus, object, "latitudine", "Latitudine Richiesto");
  	}
	if (stabilimento.getSedeOperativa()!= null && stabilimento.getSedeOperativa().getLongitudine()<=0 )
  	{
  		isValid = false;
			addError2(systemStatus, object, "longitudine", "Longitudine Richiesto");
  	}
  	
  	if (stabilimento.getDataApertura()==null )
  	{
  			isValid = false;
			addError2(systemStatus, object, "dataApertura", "Data Apertura Apiario Richiesto");
  	}
  	
  	if (stabilimento.getSedeOperativa()== null || stabilimento.getSedeOperativa().getVia()== null || "".equals(stabilimento.getSedeOperativa().getVia()) )
  	{
  			isValid = false;
			addError2(systemStatus, object, "indirizzo", "Indirizzo Richiesto");
  	}
  	
	if (stabilimento.getIdApicolturaClassificazione()<=0 )
  	{
  			isValid = false;
			addError2(systemStatus, object, "classificazione", "Classificazione Richiesto");
  	}
	
	Operatore apicoltura = new Operatore(db, stabilimento.getIdOperatore());
	if (apicoltura.getDataInizio()!= null && stabilimento.getDataApertura().before(apicoltura.getDataInizio()) )
  	{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  			isValid = false;
			addError2(systemStatus, object, "dataApertura", "Data Apertura Apiario  precedente della data inizio attivita :"+sdf.format(new Date(apicoltura.getDataInizio().getTime())));
  	}
	
	
	
	if (stabilimento.getIdApicolturaModalita()<=0 )
  	{
  			isValid = false;
			addError2(systemStatus, object, "modalita", "Modalita Richiesto");
  	}
	
	if (stabilimento.getIdApicolturaSottospecie()<=0 )
  	{
  			isValid = false;
			addError2(systemStatus, object, "sottospecie", "Sottospecie Richiesto");
  	}
	
	
  	
	  }
    
    if (object.getClass().getName() .equals("ext.aspcfs.modules.opu.base.Stabilimento"))
	  { 
	
	org.aspcfs.modules.opu.base.Stabilimento stabilimento = (org.aspcfs.modules.opu.base.Stabilimento)object ;
	
	
	if (stabilimento.getListaLineeProduttive().size()==0)
	{
			isValid = false;
			addError2(systemStatus, object, "LineaAttivita", "Inserire Almeno una Linea di Attivita");
	}
	
	if ( (stabilimento.getOperatore().getPartitaIva()== null || (stabilimento.getOperatore().getPartitaIva()!=null && stabilimento.getOperatore().getPartitaIva().length()!=11) || stabilimento.getOperatore().getPartitaIva().equals("")) && stabilimento.getOperatore().getCodFiscale()== null || stabilimento.getOperatore().getCodFiscale().equals("")  )
	{
		isValid = false;
			addError2(systemStatus, object, "PivaCf", "Inserire La partita Iva o codice Fiscale Impresa");
	}

	
	
	
	
	  }
    if (object.getClass().getName() .equals("ext.aspcfs.modules.apiari.base.Operatore"))
	  { 
    	
    	Operatore operatore = (Operatore)object ;
    	
    	if (operatore.getSedeLegale() == null || (operatore.getSedeLegale().getComune()<=0 ) && "".equals(operatore.getSedeLegale().getComuneTesto()))
    	{
    		isValid = false;
			addError2(systemStatus, object, "comuneSedeLegale", "Comune Richiesto");
    	}
    	
    	if (operatore.getSedeLegale() == null || operatore.getSedeLegale().getProvincia() == null || "".equals(operatore.getSedeLegale().getProvincia()))
    	{
    		isValid = false;
			addError2(systemStatus, object, "provinciaSedeLegale", "Provincia Richiesto");
    	}
    	
    	if (operatore.getSedeLegale() == null || "".equals(operatore.getSedeLegale().getCap()) || operatore.getSedeLegale().getCap()==null)
    	{
    		isValid = false;
			addError2(systemStatus, object, "capSedeLegale", "Cap Richiesto");
    	}
    	
    	
    	if (operatore.getSedeLegale() == null || "".equals(operatore.getSedeLegale().getVia()) || operatore.getSedeLegale().getVia()==null)
    	{
    		isValid = false;
			addError2(systemStatus, object, "viaSedeLegale", "Indirizzo Richiesto");
    	}
    	
    	
    	if (operatore.getRappLegale()==null || operatore.getRappLegale().getIdSoggetto()<=0)
    	{
    		isValid = false;
			addError2(systemStatus, object, "cfSoggettoFisico", "Codice Fiscale Richiesto o Persona non Presente (Inserire la Persona)");
    	}
    	
    	
    	if (operatore.getIdTipoAttivita()<=0)
    	{
    		isValid = false;
			addError2(systemStatus, object, "tipoAttivita", "Tipo Attivita Richiesto");
    	}
    	
    	if (operatore.getDomicilioDigitale()==null || "".equals(operatore.getDomicilioDigitale()))
    	{
    		isValid = false;
			addError2(systemStatus, object, "domicilioDigitale", "Domicilio Digitale Richiesto");
    	}
    	
    	if (operatore.getTelefono1()==null || "".equals(operatore.getTelefono1()))
    	{
    		isValid = false;
			addError2(systemStatus, object, "telefonoFisso", "Telefono Fisso Richiesto");
    	}
    	
    	if (operatore.getTelefono2()==null || "".equals(operatore.getTelefono2()))
    	{
    		isValid = false;
			addError2(systemStatus, object, "telefonoCellulare", "Telefono Cellulare Richiesto");
    	}
    	
	  }
    
    if (object.getClass().getName() .equals("ext.aspcfs.modules.apiari.base.SoggettoFisico"))
	  { 
    	SoggettoFisico soggettoFisico = (SoggettoFisico)object ;
    	
    	UserBean user = (UserBean) ctx.getSession().getAttribute("User");
    	if (user.getRoleId()==Role.RUOLO_APICOLTORE && ctx.getParameter("tipo")==null) // in caso di detentore (tipo=Detentore) salta il controllo
    	{
    		if (user.getContact().getCodiceFiscale() == null || ! user.getContact().getCodiceFiscale().trim().equalsIgnoreCase(soggettoFisico.getCodFiscale().trim()))
    		{
    			isValid = false;
    			addError2(systemStatus, object, "cf", "Codice Fiscale Non coincide con quello dell'utente che sta eseguendo l'inserimento");
    		}
    	}
    	
    	
    	
    	
    	if ("".equalsIgnoreCase(soggettoFisico.getCodFiscale() ))
    	{
    		isValid = false;
			addError2(systemStatus, object, "cf", "Codice Fiscale Richiesto");
    	}else{
    		String controlloCF= ext.aspcfs.modules.apicolture.actions.CfUtil.extractCodiceFiscale(soggettoFisico.getCodFiscale());
    		if(!controlloCF.equalsIgnoreCase(""))
    		{
    			isValid = false;
    			addError2(systemStatus, object, "cf", controlloCF);
        		}	
    	}
    	if ("".equalsIgnoreCase(soggettoFisico.getCognome() ))
    	{
    		isValid = false;
			addError2(systemStatus, object, "cognome", "Cognome Richiesto");
    	}
    	if ("".equalsIgnoreCase(soggettoFisico.getNome() ))
    	{
    		isValid = false;
			addError2(systemStatus, object, "nome", "Nome Richiesto");
    	}
    	
    	
    	if ("".equalsIgnoreCase(soggettoFisico.getIndirizzo().getNazione() ))
    	{
    		isValid = false;
			addError2(systemStatus, object, "nazione", "Nazione Richiesta");
    	}
    	if (soggettoFisico.getIndirizzo().getComune() <=0 && "".equals(soggettoFisico.getIndirizzo().getComuneTesto()))
    	{
    		isValid = false;
			addError2(systemStatus, object, "Comune", "Comune Richiesto");
    	}
    	if ("".equalsIgnoreCase(soggettoFisico.getIndirizzo().getVia() ))
    	{
    		isValid = false;
			addError2(systemStatus, object, "Indirizzo", "Indirizzo Richiesto");
    	}
    	
	  }
    if (object.getClass().getName() .equals("org.aspcfs.modules.vigilanza.base.Ticket"))
	  {
    	
    	Ticket cu = (Ticket)object ; 
 
			Timestamp dataControllo =cu.getAssignedDate();
		
		
			
			
			
		Calendar cal=GregorianCalendar.getInstance();
	    cal.setTime(new Date (dataControllo.getTime()));
		int annoControllo =  cal.get(Calendar.YEAR);
		
		Timestamp data_attuale_time = new Timestamp(System.currentTimeMillis());
		Calendar calCorrente = GregorianCalendar.getInstance();
		Calendar calCorrente2 = GregorianCalendar.getInstance();
		Date dataCorrente = new Date(System.currentTimeMillis());
		
		Date dataCorrente2 = new Date(System.currentTimeMillis());
		calCorrente2.setTime(new Timestamp(dataCorrente2.getTime()));
		int anno_corrente2 = calCorrente2.get(Calendar.YEAR);
		int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
		dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
		calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
		int anno_corrente = calCorrente.get(Calendar.YEAR);
		
		String suffisso = (String)ctx.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");

		if ( (suffisso != null && !suffisso.equals("_ext")) && (annoControllo != anno_corrente )&& cuPregresso ==-1)
		{
			isValid = false;
			if (annoControllo>anno_corrente)
				addError2(systemStatus, object, "assigned_date", org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("BLOCCO4_MESSAGGIO"));
			else
				addError2(systemStatus, object, "assigned_date", org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("BLOCCO1_MESSAGGIO"));
			
		}
		
		
		EventoBloccoCu blocco = new EventoBloccoCu();
		blocco.queryRecord(db);
		
		if ( (suffisso != null && !suffisso.equals("_ext")) && anno_corrente2 ==  annoControllo && blocco.getData_sblocco()!= null && dataControllo.compareTo(blocco.getData_sblocco())<=0)
		{
			isValid = false;
			addError2(systemStatus, object, "assigned_date", org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("BLOCCO2_MESSAGGIO")+" dal "+blocco.getData_bloccoString()+ " al "+blocco.getData_sbloccoString()+ " "+org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("BLOCCO2_1MESSAGGIO"));
			;
		}
		
		
		if(cu.getTipoCampione()==2 && annoControllo == anno_corrente )
		{
			isValid = false;
			addError2(systemStatus, object, "assignedDate", "Per la tecnica di controllo selezionata non e possibile inserire CU nell'anno in corso");
		
		}
		
		
		boolean inPiano = false ; 
		boolean motivoPresente = true  ;
		
		if ((cu.getTipoCampione()==4 || cu.getTipoCampione()==3) && cu.getEnteredBy()<10000000)
		{
		if(!cu.getTipoIspezione().isEmpty())
		{
			
		
			Iterator<Integer> itTipoIspezione = cu.getTipoIspezione().keySet().iterator();
			while (itTipoIspezione.hasNext())
			{
				int tipo_ispezione = itTipoIspezione.next();
				
				if (tipo_ispezione<=0)
				{
					motivoPresente = false ;
				}
				
				RispostaDwrCodicePiano codiceInterno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", tipo_ispezione);
				if("2a".equalsIgnoreCase(codiceInterno.getCodiceInterno()))// tipo ispe<ione scelta e' in monitoraggio
				{
					
					inPiano=true ;
				}
			
				
			}
		}
		else
		{
			motivoPresente = false;
		}
		
		if (motivoPresente==false)
		{
			isValid = false;
			addError2(systemStatus, object, "motivoIspezione", "Controllare di Aver Selezionato il motivo di ispezione");
		}
		else
		{
			if (inPiano==true && cu.getPianoMonitoraggio().size()==0)
			{
				isValid = false;
				addError2(systemStatus, object, "motivoIspezione", "Controllare di Aver Selezionato il motivo di ispezione compreso il piano di monitoraggio");
			}
			
		}
		
		if (inPiano==true)
		for (Piano p : cu.getPianoMonitoraggio())
		{
			
			
			if (p!= null )
			{
				
			
			if (p.getId_uo()<=0)
			{
				isValid = false;
				addError2(systemStatus, object, "unitaOperativaPerContoDi", "Controllare di Aver Selezionato il campo per conto di Per il piano di monitoraggio");
				
			}
			}
		}
		
		if (cu.getLista_uo_ispezione().size()>0)
		{
		Iterator <Integer> itMotivi =cu.getLista_uo_ispezione().keySet().iterator();
		while(itMotivi.hasNext())
		{
			int idTipoIspezione = itMotivi.next();
			if(idTipoIspezione!=89)
			{
			OiaNodo uo = cu.getLista_uo_ispezione().get(idTipoIspezione);
			if (uo==null || uo.getId()<=0)
			{
				isValid = false;
				addError2(systemStatus, object, "unitaOperativaPerContoDi", "Controllare di Aver Selezionato il campo per conto di Per il tipo di ispezione");
			}
			}
		}
		
		}
		
		
		
		
		
		}
		
		
		if (cu.getTipoCampione()!=4 && cu.getTipoCampione()!=3)
		{	
			String [] uo = cu.getUo();
		
			
			if (cu.getTipologia_operatore()==6)
			{
				if (cu.getListaStruttureControllareAutoritaCompetenti().size()==0)
				{
					isValid = false;
					
					addError2(systemStatus, object, "struttureControllateAutoritaCompetenti", "Controllare di Aver Selezionato le strutture Controllate!");
				}
			}
			
			if (uo!=null)
			if (uo.length==0)
			{
				isValid = false;
			
				addError2(systemStatus, object, "unitaOperativaPerContoDi", "Controllare di Aver Selezionato il campo per conto di Per il tipo di ispezione");
			}
			else
			for(int i = 0 ; i <uo.length; i++)
			{
				if(!uo[i].equals("-1"))
				{
					try
					{
						Integer.parseInt(uo[i]);
						isValid=true;
					}
					catch(Exception e)
					{
						isValid = false;
						addError2(systemStatus, object, "unitaOperativaPerContoDi", "Controllare di Aver Selezionato il campo per conto di Per il tipo di ispezione");
					}
				}
				else
				{
					isValid = false;
					addError2(systemStatus, object, "unitaOperativaPerContoDi", "Controllare di Aver Selezionato il campo per conto di");
				}

			}
			else{
				isValid=false;
				addError2(systemStatus, object, "unitaOperativaPerContoDi", "Controllare di Aver Selezionato il campo per conto di Per il tipo di ispezione");

			}
		}
		
		/*	
		if (cu.getModificabile().equalsIgnoreCase("false") || cu.getModificabile().equalsIgnoreCase("no")){
			isValid = false;
			addError2(systemStatus, object, "motivoIspezione", "Motivo di ispezione non modificabile in quanto uno o piu campioni sono presenti nel controllo.");
		}
		*/
		
		
		int tipologia = -1 ;
		boolean isMercato = false;
		String sql = "select tipologia,direct_bill from organization where org_id = ?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, cu.getOrgId());
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			tipologia=rs.getInt(1);
			isMercato = rs.getBoolean(2);
		}
		if (tipologia==2)
		{
			
			org.aspcfs.utils.web.LookupList piani = new org.aspcfs.utils.web.LookupList(db,"lookup_piano_monitoraggio");
			org.aspcfs.utils.web.LookupList attiCondizionalita = new org.aspcfs.utils.web.LookupList(db,"lookup_condizionalita");
			
			Iterator <Integer > itKeyCond = cu.getTipo_ispezione_condizionalita().keySet().iterator();
			while(itKeyCond.hasNext())
			{
				
				String val = cu.getTipo_ispezione_condizionalita().get(itKeyCond.next());
				if(val.contains("ATTO B11")) {
					
					if(cu.getFlag_checklist() == null || cu.getFlag_checklist().equals("") || cu.getFlag_checklist().equals("-1")){
						isValid = false;
						addError2(systemStatus, object, "flag_checklist", "E' obbligatorio specificare se e' stata rilasciata copia cartacea della checklist all'allevatore");
						break ;
					}
					
					if  ((cu.getFlag_preavviso() != null && !cu.getFlag_preavviso().equals("N") && 
							(cu.getData_preavviso_ba()==null || "".equals(cu.getData_preavviso_ba())) ||cu.getFlag_preavviso().equals("-1") ) 
							
							)
					{
						isValid = false;
						  addError2(systemStatus, object, "data_preavviso", "Data Preavviso e Flag Preavviso Obbligatori");
						break ;
					}
					
				}
				
			}
			
			
			for (Piano p : cu.getPianoMonitoraggio())
			{
				
				
				if (p!= null && (piani.getSelectedValue(p.getId()).startsWith("20 PIANO") || piani.getSelectedValue(p.getId()).startsWith("49 PIANO")) )
				{
					if  ((cu.getFlag_preavviso() != null && !cu.getFlag_preavviso().equals("N") && 
							(cu.getData_preavviso_ba()==null || "".equals(cu.getData_preavviso_ba())) ||cu.getFlag_preavviso().equals("-1") ) 
							
							)
					{
						isValid = false;
						  addError2(systemStatus, object, "data_preavviso", "Data Preavviso e Flag Preavviso Obbligatori");
						break ;
					}
				}
				
				
			}
			
			
			
		}
		if(cu.getOrgId()>0)
		{
			
		
			if(tipologia==1  )
			{
				
				if(cu.getTipoCampione()==4 || cu.getTipoCampione()==3)
				{
					String[] linee_Attivita = ctx.getRequest().getParameterValues("id_linea_sottoposta_a_controllo");

					if(linee_Attivita!=null && linee_Attivita.length==0)
					{
						isValid = false;
						  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");
						  
					}
					else
					{
						if(linee_Attivita!=null)
						{
						for(int i = 0 ; i < linee_Attivita.length; i++)
						{
							if( linee_Attivita[i].equals("-1") || linee_Attivita[i].equals(""))
							{
								isValid = false;
								  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");
								  
								
							}
						}
						}
						else
						{
							isValid = false;
							  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");
							  
						}
					}
				}
					
					
			}
			else
			{
				if( (tipologia==3 && isMercato==false) || tipologia==97 )
				{
					

				if(cu.getTipoCampione()==4 || cu.getTipoCampione()==3)
				{
					String[] linee_Attivita_stab_soa = ctx.getRequest().getParameterValues("codici_selezionabili");
					
					if (linee_Attivita_stab_soa!=null)
					{
					
					ArrayList<String> lineeControllate = new ArrayList<String>();
					for(int i = 0 ; i < linee_Attivita_stab_soa.length; i++)
					{
						if(!linee_Attivita_stab_soa[i].equals("-1") &&  ! linee_Attivita_stab_soa[i].equals(""))
						{
							lineeControllate.add(linee_Attivita_stab_soa[i]);
							  
						}
					}
					
					if(lineeControllate.size() ==0)
					{
						isValid = false;
						  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");
						  
					}
					
					
					}

					else
					{
						{
							isValid = false;
							  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");
						}
					}
				}
				
				
				}
			}
			
		}
		else
		{
			if(cu.getIdStabilimento()>0)
			{
				//Aggiunta la condizione sull'apiario per cui non e' richiesto di specificare la linea di attivita'.
				if((cu.getTipoCampione()==4 || cu.getTipoCampione()==3) && cu.getIdApiario() <= 0)
				{
					String[] linee_Attivita = ctx.getRequest().getParameterValues("id_linea_sottoposta_a_controllo");

					if( linee_Attivita!=null  && linee_Attivita.length==0)
					{
						
						isValid=false;
						  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");

					}
					else
					{
						
						if(linee_Attivita!=null){
						for(int i = 0 ; i < linee_Attivita.length; i++)
						{
							if(linee_Attivita[i].equals("-1") || linee_Attivita[i].equals(""))
							{
								isValid=false;
								  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");

								
							}
						}
						}
						else
						{
							isValid=false;
							  addError2(systemStatus, object, "lineaAttivita", "Controllare di Aver Selezionato La Linea di Attivita Controllata");
						}
					}
				}
			}
		}
		
		
	
		
	  }
    
    //  FileItem
    if (object.getClass().getName().equals("com.zeroio.iteam.base.FileItem")) {
      FileItem fileItem = (FileItem) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "filename", REQUIRED_FIELD);
      if (fileItem.getLinkModuleId() == -1 || fileItem.getLinkItemId() == -1) {
        addError(systemStatus, object, "action", "object.validation.noId");
      }
    }

    //  FileFolder
    if (object.getClass().getName().equals("com.zeroio.iteam.base.FileFolder"))
    {
      FileFolder fileFolder = (FileFolder) object;
      if (fileFolder.getLinkModuleId() == -1 || fileFolder.getLinkItemId() == -1)
      {
        addError(systemStatus, object, "action", "object.validation.noId");
      }
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "subject", 255);
    }
    
    /*VALIDATE CAMPI CONTROLLO UFFICIALE*/
    if (object.getClass().getName().equals("org.aspcfs.modules.vigilanza.base.Ticket"))
    {
    	org.aspcfs.modules.vigilanza.base.Ticket controlloUfficiale = (org.aspcfs.modules.vigilanza.base.Ticket) object;
    	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    	
    	if (controlloUfficiale.getAssignedDate()==null || (controlloUfficiale.getAssignedDate() != null && controlloUfficiale.getAssignedDate().after(currentTimestamp)) )
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
    	
    	else
    	{
    		Timestamp dataFineControllo = controlloUfficiale.getDataFineControllo();
    		if (dataFineControllo != null && controlloUfficiale.getAssignedDate()!=null)
    		{
    			if (dataFineControllo.before(controlloUfficiale.getAssignedDate()))
    			{
    				isValid=false;
    	    		 addError(systemStatus, object, "dataFineControllo", "object.validation.dataFineControllo");
    			}
    		}
    	}
    	
    	
    	if (controlloUfficiale.getTipoCampione()==-1)
    	{
    		isValid=false;
			  addError(systemStatus, object, "tipoControllo", "object.validation.tipocontrollo");
    	}
    	
    	if (controlloUfficiale.getEnteredBy()<10000000)
    	{
    	 isValid = checkTipoAudit(db, systemStatus, object,isValid);
    	 isValid = checkTipoIspezione(db,systemStatus, object,isValid);
    	 isValid = checkUoMultiple(systemStatus, object,isValid);
    	}
    	 isValid =  checkNuceloIspettivo(systemStatus, object,isValid);
     	
    }
    
    
    if (object.getClass().getName().equals("org.aspcfs.modules.campioni.base.Ticket"))
    {
    	org.aspcfs.modules.campioni.base.Ticket campione = (org.aspcfs.modules.campioni.base.Ticket) object;
    	Ticket cu = new Ticket(db,Integer.parseInt(campione.getIdControlloUfficiale()));
    	if (campione.getAssignedDate()==null || 
    			(campione.getAssignedDate() != null && campione.getAssignedDate().before(cu.getAssignedDate())) || 
    			(campione.getAssignedDate() != null && campione.getAssignedDate().after(new Timestamp(System.currentTimeMillis())))
    			)
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
     	
    }
    
    if (object.getClass().getName().equals("org.aspcfs.modules.campioni.base.Ticket"))
    {
    	org.aspcfs.modules.campioni.base.Ticket campione = (org.aspcfs.modules.campioni.base.Ticket) object;
		String numVerbale="";
		
		if(campione.getLocation() != null && !campione.getLocation().equals("") && !campione.getLocation().equalsIgnoreCase("automatico")){
			
			numVerbale = fixStringa(campione.getLocation());
			campione.setLocation(numVerbale);
			
		String sql = 	"select * from ticket where location = ? and trashed_date is null and site_id = ? " ;
    	PreparedStatement pst = db.prepareStatement(sql);
    	pst.setString(1, numVerbale);
		pst.setInt(2, campione.getSiteId());
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			isValid = false;
			break;
		}
    	
		if(!isValid)
			addError(systemStatus, object, "location", "object.validation.verbale");
   
		}
		
String numVerbaleNew="";
		
		if(campione.getLocation_new() != null && !campione.getLocation_new().equals("")){
			numVerbaleNew = fixStringa(campione.getLocation_new());
			campione.setLocation_new(numVerbaleNew);
			
		String sql = 	"select * from ticket where location_new = ? and trashed_date is null " ;
    	PreparedStatement pst = db.prepareStatement(sql);
    	pst.setString(1, numVerbaleNew);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			isValid = false;
			break;
		}
    	
		if(!isValid)
			addError(systemStatus, object, "location", "object.validation.verbale");
   
		}
		
String numVerbaleNewBarcodeOsa="";
		
		if(campione.getLocation_new() != null && !campione.getLocation_new().equals("") ){
			numVerbaleNewBarcodeOsa = campione.getLocation_new();
		String sql = 	"select * from barcode_osa b join ticket t on t.ticketid = b.ticket_id and t.trashed_date is null where b.barcode_new = ? " ;
    	PreparedStatement pst = db.prepareStatement(sql);
    	pst.setString(1, numVerbaleNewBarcodeOsa);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			isValid = false;
			break;
		}
    	
		if(!isValid)
			addError(systemStatus, object, "location", "object.validation.verbale");
   
		}
		
		
		
    }
    if (object.getClass().getName().equals("org.aspcfs.modules.tamponi.base.Ticket"))
    {
    	org.aspcfs.modules.tamponi.base.Ticket tampone = (org.aspcfs.modules.tamponi.base.Ticket) object;
    	Ticket cu = new Ticket(db,Integer.parseInt(tampone.getIdControlloUfficiale()));
    	if (tampone.getAssignedDate()==null || 
    			(tampone.getAssignedDate() != null && tampone.getAssignedDate().before(cu.getAssignedDate())) )
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
     	
    }
    
    if (object.getClass().getName().equals("org.aspcfs.modules.sanzioni.base.Ticket"))
    {
    	org.aspcfs.modules.sanzioni.base.Ticket sanzioni = (org.aspcfs.modules.sanzioni.base.Ticket) object;
    	Ticket cu = new Ticket(db,Integer.parseInt(sanzioni.getIdControlloUfficiale()));
    	if (sanzioni.getAssignedDate()==null || 
    			(sanzioni.getAssignedDate() != null && sanzioni.getAssignedDate().before(cu.getAssignedDate())) )
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
     	
    }
    

    
    if (object.getClass().getName().equals("org.aspcfs.modules.reati.base.Ticket"))
    {
    	org.aspcfs.modules.reati.base.Ticket reati = (org.aspcfs.modules.reati.base.Ticket) object;
    	Ticket cu = new Ticket(db,Integer.parseInt(reati.getIdControlloUfficiale()));
    	if (reati.getAssignedDate()==null || 
    			(reati.getAssignedDate() != null && reati.getAssignedDate().before(cu.getAssignedDate())) )
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
     	
    }
    if (object.getClass().getName().equals("org.aspcfs.modules.followup.base.Ticket"))
    {
    	org.aspcfs.modules.followup.base.Ticket followup = (org.aspcfs.modules.followup.base.Ticket) object;
    	Ticket cu = new Ticket(db,Integer.parseInt(followup.getIdControlloUfficiale()));
    	if (followup.getAssignedDate()==null || 
    			(followup.getAssignedDate() != null && followup.getAssignedDate().before(cu.getAssignedDate())) )
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
     	
    }
    
    if (object.getClass().getName().equals("org.aspcfs.modules.diffida.base.Ticket"))
    {
    	org.aspcfs.modules.diffida.base.Ticket diffida = (org.aspcfs.modules.diffida.base.Ticket) object;
    	Ticket cu = new Ticket(db,Integer.parseInt(diffida.getIdControlloUfficiale()));
    	if (diffida.getAssignedDate()==null || 
    			(diffida.getAssignedDate() != null && diffida.getAssignedDate().before(cu.getAssignedDate())) )
    	{
    		isValid=false;
    		 addError(systemStatus, object, "assignedDate", "object.validation.data");
    	}
     	
    }
    if (object.getClass().getName().equals("org.aspcfs.modules.allerte_new.base.Ticket"))
    {
    	org.aspcfs.modules.allerte_new.base.Ticket allerta = (org.aspcfs.modules.allerte_new.base.Ticket) object;
    	if (allerta.getNumero_notifica_allerta() == null || allerta.numeroNotificaDuplicato(db))
    	{
    		isValid=false;
    		 addError2(systemStatus, object, "numero_notifica_allerta", "Il numero di notifica indicato risulta gia' presente nel sistema.");
    	}
     	
    }

    
  
	return isValid;
	
	
  
  }
  public static boolean validateInBacheca(SystemStatus systemStatus, Connection db, Object object) throws SQLException {
	  
	  boolean valid = true;
	  if (object.getClass().getName().equals("com.zeroio.iteam.base.FileItem")) {
	      FileItem fileItem = (FileItem) object;
	      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
	      checkError(systemStatus, object, "filename", REQUIRED_FIELD);
	      String bacheca_abilitata = ApplicationProperties.getProperty("flagbacheca");
	      if(bacheca_abilitata.equalsIgnoreCase("si") && fileItem.getSize()>SIZE_FILE)
	      {
	    	  addError(systemStatus, object, "file_size", "object.validation.noExtension");
	    	  valid = false;
	      }
	     
	    	 
	    	
	      
	    }
	  return valid;
  }

  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param db           Description of the Parameter
   * @param object       Description of the Parameter
   * @param map          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean validate( SystemStatus systemStatus, Connection db, Object object, HashMap map) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "ObjectValidator-> Checking object: " + object.getClass().getName());
    }
    //  TicketReplacementPart
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketReplacementPart")) {
      //TicketReplacementPart replacement = (TicketReplacementPart) object;
      String parseItem = (String) map.get("parseItem");
      String partNumber = (String) map.get("partNumber");
      String partDescription = (String) map.get("partDescription");
      if ((partNumber != null && !"".equals(partNumber)) &&
          (partDescription == null || "".equals(partDescription))) {
        addError(
            systemStatus, object, "partDescription" + parseItem, REQUIRED_FIELD);
        System.out.println("Adding Errror -->-0> partDescription is required");
      }
      if ((partDescription != null && !"".equals(partDescription)) &&
          (partNumber == null || "".equals(partNumber))) {
        addError(
            systemStatus, object, "partNumber" + parseItem, REQUIRED_FIELD);
        System.out.println("Adding Errror -->-0> partNumber is required");
      }
    }

    //TicketPerDayDescription
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketPerDayDescription")) {
      //TicketPerDayDescription thisDescription = (TicketPerDayDescription) object;
      String parseItem = (String) map.get("parseItem");
      String descriptionOfService = (String) map.get("descriptionOfService");
      if (descriptionOfService == null || "".equals(
          descriptionOfService.trim())) {
        addError(
            systemStatus, object, "descriptionOfService" + parseItem, REQUIRED_FIELD);
        addError(
            systemStatus, object, "action", "object.validation.genericActionError");
      }

    
      //String timeZone = user.getTimeZone();
     
    }

    return true;
  }


  /**
   * Adds a feature to the Error attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Error attribute
   * @param object       The feature to be added to the Error attribute
   * @param field        The feature to be added to the Error attribute
   * @param errorType    The feature to be added to the Error attribute
   * @return Description of the Return Value
   */
  public static boolean checkError(SystemStatus systemStatus, Object object, String field, int errorType) {
    boolean returnValue = true;
    if (errorType == REQUIRED_FIELD) {
      String result = ObjectUtils.getParam(object, field);
      if (result == null || "".equals(result.trim())) {
        returnValue = false;
        addError(systemStatus, object, field, REQUIRED_FIELD);
      }
    } else if (errorType == INVALID_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date == null || "".equals(date.trim())) {
          returnValue = false;
          addError(systemStatus, object, field, REQUIRED_FIELD);
        } else {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_NOT_REQUIRED_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date != null && !"".equals(date.trim())) {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        } else {
          returnValue = false;
          addError(systemStatus, object, field, INVALID_EMAIL);
        }
      } catch (Exception e) {
        returnValue = false;
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL_NOT_REQUIRED) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return returnValue;
  }


  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param object       Description of the Parameter
   * @param field        Description of the Parameter
   * @param errorName    Description of the Parameter
   * @param errorType    Description of the Parameter
   */
  public static void checkError(SystemStatus systemStatus, Object object, String field, String errorName, int errorType) {
    if (errorType == REQUIRED_FIELD) {
      String result = ObjectUtils.getParam(object, field);
      if (result == null || "".equals(result.trim())) {
        addError(systemStatus, object, errorName, REQUIRED_FIELD);
      }
    } else if (errorType == INVALID_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date == null || "".equals(date.trim())) {
          addError(systemStatus, object, errorName, REQUIRED_FIELD);
        } else {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, errorName, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_NOT_REQUIRED_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date != null && !"".equals(date.trim())) {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, errorName, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        } else {
          addError(systemStatus, object, field, INVALID_EMAIL);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL_NOT_REQUIRED) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Adds a feature to the Error attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Error attribute
   * @param field        The feature to be added to the Error attribute
   * @param errorType    The feature to be added to the Error attribute
   * @param object       The feature to be added to the Error attribute
   */
  public static void addError(SystemStatus systemStatus, Object object, String field, int errorType) {
    if (errorType == REQUIRED_FIELD) {
      addError(
          systemStatus, object, field, "object.validation.required");
    } else if (errorType == PAST_DATE) {
      addError(
          systemStatus, object, field, "object.validation.dateInThePast");
    } else if (errorType == INVALID_DATE || errorType == INVALID_NOT_REQUIRED_DATE) {
      addError(
          systemStatus, object, field, "object.validation.incorrectDateFormat");
    } else if (errorType == INVALID_NUMBER) {
      addError(
          systemStatus, object, field, "object.validation.incorrectNumberFormat");
    } else if (errorType == INVALID_EMAIL || errorType == INVALID_EMAIL_NOT_REQUIRED) {
      addError(
          systemStatus, object, field, "object.validation.invalidEmailAddress");
    }
  }


  /**
   * Adds a feature to the Error attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Error attribute
   * @param field        The feature to be added to the Error attribute
   * @param errorKey     The feature to be added to the Error attribute
   * @param object       The feature to be added to the Error attribute
   */
  private static void addError(SystemStatus systemStatus, Object object, String field, String errorKey) {
    HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
    if (systemStatus != null) {
      errors.put(field + "Error", systemStatus.getLabel(errorKey));
    } else {
      errors.put(field + "Error", "field error");
    }
  }
  public static void addError2(SystemStatus systemStatus, Object object, String field, String error) {
	    HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
	    errors.put(field + "Error", error);
	   
	  }

  /**
   * Adds a feature to the Warning attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Warning attribute
   * @param object       The feature to be added to the Warning attribute
   * @param field        The feature to be added to the Warning attribute
   * @param warningType  The feature to be added to the Warning attribute
   */
  public static void checkWarning(SystemStatus systemStatus, Object object, String field, int warningType) {
    if (warningType == IS_BEFORE_TODAY) {
      Timestamp result = (Timestamp) ObjectUtils.getObject(object, field);
      if (result != null && result.before(new java.util.Date())) {
        addWarning(
            systemStatus, object, field, "object.validation.beforeToday");
      }
    }
  }


  /**
   * Adds a feature to the Warning attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Warning attribute
   * @param object       The feature to be added to the Warning attribute
   * @param field        The feature to be added to the Warning attribute
   * @param warningKey   The feature to be added to the Warning attribute
   */
  public static void addWarning(SystemStatus systemStatus, Object object, String field, String warningKey) {
    HashMap warnings = (HashMap) ObjectUtils.getObject(object, "warnings");
    if (systemStatus != null) {
      warnings.put(field + "Warning", systemStatus.getLabel(warningKey));
    } else {
      warnings.put(field + "Warning", "field warning");
    }
  }


  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param object       Description of the Parameter
   * @param errorName    Description of the Parameter
   * @param fieldName    Description of the Parameter
   * @param length       Description of the Parameter
   */
  public static void checkLength(SystemStatus systemStatus, Object object, String errorName, String fieldName, int length) {
    String value = (String) ObjectUtils.getObject(object, fieldName);
    if (value != null && value.length() > length) {
      addError(systemStatus, object, fieldName, errorName);
    }
  }
  
  
  
  public static boolean checkTipoIspezione(Connection db ,SystemStatus systemStatus, Object object,boolean valido) {
	  org.aspcfs.modules.vigilanza.base.Ticket controlloUfficiale = (org.aspcfs.modules.vigilanza.base.Ticket) object;
	  
	  boolean isValid = valido ;
	  if (controlloUfficiale.getTipoCampione()==4 && controlloUfficiale.getEnteredBy()<10000000)
	  {
		  if (controlloUfficiale.getLista_uo_ispezione().size()<=0 || (controlloUfficiale.getLista_uo_ispezione().size()==1 && controlloUfficiale.getLista_uo_ispezione().containsKey(-1)))
		  {
			  isValid = false;
			  addError(systemStatus, object, "tipoControllo", "object.validation.tipocontrollo");
		  }
		  
		  if (controlloUfficiale.getLista_uo_ispezione().size()>0)
		  {
			 HashMap<Integer, OiaNodo> listaMotiviIsp = controlloUfficiale.getLista_uo_ispezione();
			 Iterator<Integer> itKey = listaMotiviIsp.keySet().iterator();
			 while (itKey.hasNext())
			 {
				 int thisKey = itKey.next() ;
				 
				RispostaDwrCodicePiano tipoIspezione= ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", thisKey);
				 if ("2a".equalsIgnoreCase(tipoIspezione.getCodiceInterno() ) )		// controllo per i piani di monitoraggio
				 {
					ArrayList<Piano> listaPiani =  controlloUfficiale.getPianoMonitoraggio() ;
					if (listaPiani.size()<=0 || (listaPiani.size()==1 && (listaPiani.get(0).getId()<=0)) )
					{
						isValid = false;
						 addError(systemStatus, object, "tipoControllo", "object.validation.tipocontrollo");
					}
					else
					{
						for (Piano p : listaPiani)
						{
							if (p.getId_uo()<=0)
							{
								isValid = false;
								addError(systemStatus, object, "tipoControlloUO", "object.validation.unitaoperative");
								break ;
							}
						}
					}
				 }
				 else
				 {
					 
					 if ("7a".equalsIgnoreCase(tipoIspezione.getCodiceInterno()))		// controllo per i campi in allerta tipo allarme rapido
					 {
						 if (controlloUfficiale.getCodiceAllerta().equals(""))
						 {
							 isValid = false;
							 addError(systemStatus, object, "tipoControlloAllerta", "object.validation.tipoallerta");
						 }
						 
						 if (controlloUfficiale.getEsitoControllo()==-1)
						 {
							 isValid = false;
							 addError(systemStatus, object, "tipoControlloAllerta", "object.validation.tipoallerta");
						 }
						 
//						 if ((controlloUfficiale.getEsitoControllo()==13 || controlloUfficiale.getEsitoControllo()== 14) && controlloUfficiale.getIdFileAllegato()==-1)
//						 {
//							 isValid = false;
//							 addError(systemStatus, object, "tipoControlloAllertaFile", "object.validation.tipoallertafile");
//						 }
						 
						 if (controlloUfficiale.getAzioniAdottate().size()== 0 || (controlloUfficiale.getAzioniAdottate().size()==1 && controlloUfficiale.getAzioniAdottate().containsKey(-1)))
						 {
							 isValid = false;
							 addError(systemStatus, object, "tipoControlloAllerta", "object.validation.tipoallerta");
						 }
						 
						 
					 }
					 else
					 {
					 
					 OiaNodo nodo = listaMotiviIsp.get(thisKey);
					 if (nodo== null  )
					 {
						 isValid = false;
						  addError(systemStatus, object, "tipoControlloUO", "object.validation.unitaoperative");
						  break ;

					 }
					 if (  nodo.getId() <=0 )
					 {
						 isValid = false;
						  addError(systemStatus, object, "tipoControlloUO", "object.validation.unitaoperative");
						  break ;

					 }
				 }
				 
				 }
			 }
			 
		  }
	  }
	  return isValid;
  }
  
  
  public static boolean checkTipoAudit(Connection db, SystemStatus systemStatus, Object object,boolean valido) {
	  org.aspcfs.modules.vigilanza.base.Ticket controlloUfficiale = (org.aspcfs.modules.vigilanza.base.Ticket) object;
	  boolean isValid = valido ; 
	  if (controlloUfficiale.getTipoCampione()==3 && controlloUfficiale.getEnteredBy()<10000000)
	  {
		  if (controlloUfficiale.getLista_uo_ispezione().size()<=0 || (controlloUfficiale.getLista_uo_ispezione().size()==1 && controlloUfficiale.getLista_uo_ispezione().containsKey(-1)))
		  {
			  isValid = false;
			  addError(systemStatus, object, "tipoControllo", "object.validation.tipocontrollo");
		  }
		  
		  if (controlloUfficiale.getLista_uo_ispezione().size()>0)
		  {
			 HashMap<Integer, OiaNodo> listaMotiviIsp = controlloUfficiale.getLista_uo_ispezione();
			 Iterator<Integer> itKey = listaMotiviIsp.keySet().iterator();
			 while (itKey.hasNext())
			 {
				 int thisKey = itKey.next() ;
				 
				RispostaDwrCodicePiano tipoIspezione= ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", thisKey);
				 if ("2a".equalsIgnoreCase(tipoIspezione.getCodiceInterno() ) )		// controllo per i piani di monitoraggio
				 {
					ArrayList<Piano> listaPiani =  controlloUfficiale.getPianoMonitoraggio() ;
					if (listaPiani.size()<=0 || (listaPiani.size()==1 && (listaPiani.get(0).getId()<=0)) )
					{
						isValid = false;
						 addError(systemStatus, object, "tipoControllo", "object.validation.tipocontrollo");
					}
					else
					{
						for (Piano p : listaPiani)
						{
							if (p.getId_uo()<=0)
							{
								isValid = false;
								addError(systemStatus, object, "tipoControlloUO", "object.validation.unitaoperative");
								break ;
							}
						}
					}
				 }
				 else
				 {
					 
					 if ("7a".equalsIgnoreCase(tipoIspezione.getCodiceInterno()))		// controllo per i campi in allerta tipo allarme rapido
					 {
						 if (controlloUfficiale.getCodiceAllerta().equals(""))
						 {
							 isValid = false;
							 addError(systemStatus, object, "tipoControlloAllerta", "object.validation.tipoallerta");
						 }
						 
						 if (controlloUfficiale.getEsitoControllo()==-1)
						 {
							 isValid = false;
							 addError(systemStatus, object, "tipoControlloAllerta", "object.validation.tipoallerta");
						 }
						 
//						 if ((controlloUfficiale.getEsitoControllo()==13 || controlloUfficiale.getEsitoControllo()== 14) && controlloUfficiale.getIdFileAllegato()==-1)
//						 {
//							 isValid = false;
//							 addError(systemStatus, object, "tipoControlloAllertaFile", "object.validation.tipoallertafile");
//						 }
						 
						 if (controlloUfficiale.getAzioniAdottate().size()== 0 || (controlloUfficiale.getAzioniAdottate().size()==1 && controlloUfficiale.getAzioniAdottate().containsKey(-1)))
						 {
							 isValid = false;
							 addError(systemStatus, object, "tipoControlloAllerta", "object.validation.tipoallerta");
						 }
						 
						 
					 }
					 else
					 {
					 
					 OiaNodo nodo = listaMotiviIsp.get(thisKey);
					 if (nodo== null  )
					 {
						 isValid = false;
						  addError(systemStatus, object, "tipoControlloUO", "object.validation.unitaoperative");
						  break ;

					 }
					 if (  nodo.getId() <=0 )
					 {
						 isValid = false;
						  addError(systemStatus, object, "tipoControlloUO", "object.validation.unitaoperative");
						  break ;

					 }
				 }
				 
				 }
			 }
			 
		  }
	  }
	  return isValid;
	      
	  
	  }
  
  
  public static boolean checkUoMultiple(SystemStatus systemStatus, Object object,boolean valido) {
	  org.aspcfs.modules.vigilanza.base.Ticket controlloUfficiale = (org.aspcfs.modules.vigilanza.base.Ticket) object;
	 
	  boolean isValid = valido ;
	  if (controlloUfficiale.getTipoCampione()==3 || controlloUfficiale.getTipoCampione() == 5)
	  {
		  String [] uo_audit_sorveglianza =  controlloUfficiale.getUo() ;
		  if(uo_audit_sorveglianza != null){
			  if (uo_audit_sorveglianza.length==1 && uo_audit_sorveglianza[0].equals("-1"))
			  {
				  isValid = false;
				  addError(systemStatus, object, "tipoControlloUoMultiple", "object.validation.uoauditsorv");

			  }
		  }
		
	  }
	  return isValid ; 
  
  }
  
  
  
  
  
  
  
  public static boolean checkNuceloIspettivo(SystemStatus systemStatus, Object object,boolean valido) {
	  
	  org.aspcfs.modules.vigilanza.base.Ticket controlloUfficiale = (org.aspcfs.modules.vigilanza.base.Ticket) object;
	  boolean isValid = valido ;
	  
	  if (controlloUfficiale.getTipoCampione()!=2)
	  {
	  ArrayList<NucleoIspettivo> nucleo = controlloUfficiale.getNucleoasList() ;
	
	  if (nucleo.size()<=0)
	  {
			  addError(systemStatus, object, "nuceloIspettivo", "object.validation.nucleoispettivo");
			  isValid = false ;

		  }
	  else
	  {
		  for (NucleoIspettivo nucleocorrente : nucleo)
		  {
			  if (nucleocorrente.getNucleo()>0 && nucleocorrente.getComponente().length()<=0)
			  {
				  addError(systemStatus, object, "nuceloIspettivo", "object.validation.nucleoispettivo");
				  isValid = false ;

				  break ;
			  }
		  }
	  }
	  }
	  return isValid ; 
  
  }
  
  private static String fixStringa(String stringa){
	  String result = stringa.trim();
	  result = result.replaceAll("\t", "");
	  result = result.replaceAll("\n", "");
	  result = result.replaceAll(" ", "");
	  return result;
  }

}

