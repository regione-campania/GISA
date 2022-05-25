/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;


import it.izs.bdn.bean.InfoAllevamentoBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationAddressList;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.allevamenti.base.AllevamentoAjax;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.gestioneanagrafica.actions.GestioneAnagraficaAction;
import org.aspcfs.modules.gestioneml.base.LivelloAggiuntivo;
import org.aspcfs.modules.gestioneml.base.SuapMasterListLineaAttivita;
import org.aspcfs.modules.gestioneml.base.SuapMasterListSchedaAggiuntiva;
import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.DatiMobile;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.GestoreComunicazioniVam;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.InformazioniStabilimento;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.LineeMobiliHtmlFields;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.opu.base.Storico;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrige;
import org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrigeCampo;
import org.aspcfs.modules.sintesis.base.SintesisProdotto;
import org.aspcfs.modules.stabilimenti.base.OperatoriAssociatiMercatoIttico;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.suap.base.StoricoRichieste;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.InvalidFileException;
import org.aspcfs.utils.InvioMassivoDistributori;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.Utility;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;


public class StabilimentoAction extends CFSModule {

	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(StabilimentoAction.class);

	Integer[] array = null;
	List<String> uniquePropertyValues = null;
	int k = -1;

	public String executeCommandInfo(ActionContext context) {
		return "infoOK";

	}
	
	public String executeCommandSendBdu(ActionContext context) {
		int idRelStabLp = -1;
		int tipoLinea = Integer.parseInt(context.getParameter("tipoLinea"));
		Connection db = null;
		Connection connectionBdu = null;
		String stabId = context.getParameter("stabId");
		context.getRequest().setAttribute("idStab", stabId);
		try {

			db = super.getConnection(context);
			idRelStabLp = Integer.parseInt(context.getParameter("idRelStabLp"));
			GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
			String query = gBdu.recuperaOperatoreSuapGisa(db, idRelStabLp, tipoLinea);
			connectionBdu = GestoreConnessioni.getConnectionBdu();
			gBdu.inserisciOperatoreBdu(connectionBdu, query);
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			this.freeConnection(context, db);
			this.freeConnectionBdu(context, connectionBdu);
		}
		return executeCommandDetails(context);
	}
	 
	public String executeCommandMyHomePage(ActionContext context) throws ParseException, NumberFormatException, IndirizzoNotFoundException {
		  
		  Connection db = null ;
	try
	{
		db = this.getConnection(context);
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		StabilimentoList listaStabilimenti = new StabilimentoList();
//		listaStabilimenti.setCodiceFiscaleSoggettoFisico(thisUser.getContact().getVisibilitaDelega());
		listaStabilimenti.setNumeroRegistrazione(thisUser.getContact().getNumRegistrazioneStabilimentoAssociato());
		listaStabilimenti.buildTrasportatoriDistributori(db);
		
		context.getRequest().setAttribute("listaStabilimenti", listaStabilimenti);
		
		if (listaStabilimenti.size()==1)
		{
			context.getRequest().setAttribute("idStab", ((Stabilimento)listaStabilimenti.get(0)).getIdStabilimento());
			return executeCommandDetails(context);
		}
		return "MyHomePageOK";
		
	}
	catch(Exception e)
	{
		logger.error("####TRASPORTATORI/DISTRIBUTORI Errore in accesso ");

		return "MyHomePageOK";
	}
	finally
	{
		this.freeConnection(context, db);
	}
	}
	
	public String executeCommandSincronizzaConBDN(ActionContext context) throws ParseException, NumberFormatException, IndirizzoNotFoundException {
		  
		  
		
		String codAzienda = context.getRequest().getParameter("codAzienda");
		String idFiscale = context.getRequest().getParameter("idFiscale");
		String specie = context.getRequest().getParameter("specieAllevata");
		String denominazione = context.getRequest().getParameter("denominazione");
		int idRel =  Integer.parseInt(context.getRequest().getParameter("idRel"));

		Connection db = null;
		try
		{
			db = this.getConnection(context);
		
		AjaxCalls call = new AjaxCalls();
		InfoAllevamentoBean allevamento = null ;
		
		if (!specie.equals("0131") && ! specie.equals("0146"))
		{
			allevamento =call.getInfoAllevamento(codAzienda, idFiscale, specie,db);
		}
		
		else
		{
			
				ArrayList<InfoAllevamentoBean> listaAll = call.getAllevamenti(codAzienda, denominazione, specie);
				allevamento = listaAll.get(0);
				allevamento.getOrientamentoProduzioneAvicoli(db, allevamento.getFlag_carne_latte());
				
				
			
		}
		
		Stabilimento org = new Stabilimento(db,Integer.parseInt(context.getParameter("orgId")));
		if(allevamento != null )
		{
			
			Timestamp dataFineAttivita= null ;
			
			if (allevamento.getData_fine_attivita() != null)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				dataFineAttivita=new Timestamp (sdf.parse(allevamento.getData_fine_attivita()).getTime());
				
				org.setDataFineAttivita(dataFineAttivita);
				
			
			}
			
//			org.setOrientamentoProd(allevamento.getOrientamento_prod());
//			if (allevamento.getNum_capi_totali() != null && !"".equals(allevamento.getNum_capi_totali()))
//				org.setNumero_capi(Integer.parseInt( allevamento.getNum_capi_totali()));
			 
			allevamento.getCod_fiscale_det();
			
			
			if (context.getRequest().getParameter("dataFineAttivita")!=null && !context.getRequest().getParameter("dataFineAttivita").equals("null"))
				allevamento.setData_fine_attivita(context.getRequest().getParameter("dataFineAttivita"));
			//allevamento.setNum_capi_totali(context.getRequest().getParameter("numeroCapi"));
			allevamento.setSpecie_allevata(context.getRequest().getParameter("specieAllevata"));
			
			allevamento.sincronizzaopuBdn(db,org.getIdStabilimento(), idRel);
			context.getRequest().setAttribute("idStab", org.getIdStabilimento());
			
		}
		context.getRequest().setAttribute("orgId",org.getIdStabilimento());

		context.getRequest().setAttribute("InfoAllevamentoBean",allevamento);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}finally
		{
			 this.freeConnection(context, db);
		}
	
	


return executeCommandDetails(context);
}
	
	
	/* AGGIUNTO DA D.ZANFARDINO PER LA COMPARAZIONE DEI DATI ALLEVAMENTO CON WS DELLA BDN */
	  public String executeCommandCompareWS(ActionContext context) {
		  AllevamentoAjax allevamento = null;
		  Stabilimento OrgDetails = null;
		  ArrayList diff = null;
		  Connection db = null;
			try {
			  db = this.getConnection(context);
			
			
			  String orgId = context.getParameter("orgId");
			  String codice_azienda = context.getParameter("codiceAzienda");
			  String partita_iva = context.getParameter("pIva");
			  String codice_specie = context.getParameter("codSpecie");
			  String denominazione = context.getParameter("denominazione");
			  String idRel =  context.getParameter("idRel");
			  context.getRequest().setAttribute("idRel", idRel);

			  
			  denominazione.replaceAll("|", "'");
			  
			  AjaxCalls ajaxCalls = new AjaxCalls();
			  if (!"0131".equals(codice_specie) && ! "0146".equals(codice_specie))
			  {
				  allevamento = ajaxCalls.getAllevamento(codice_azienda, partita_iva, codice_specie);
			  }
			  else
			  {
				  ArrayList<InfoAllevamentoBean> listaAll = ajaxCalls.getAllevamenti(codice_azienda, denominazione, codice_specie);
				  if (listaAll.size()>0)
				  {
					InfoAllevamentoBean iab = listaAll.get(0);
					iab.getOrientamentoProduzioneAvicoli(db, iab.getFlag_carne_latte());
				
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					
					LookupList listaSpeci = new LookupList(db,"lookup_specie_allevata");
					
					
						allevamento = new AllevamentoAjax();
						allevamento.setDenominazione( iab.getDenominazione() );
						allevamento.setCodice_azienda( iab.getCodice_azienda() );
						allevamento.setCodice_fiscale(iab.getCod_fiscale());
						allevamento.setIndirizzo(iab.getIndirizzo());
						allevamento.setComune(Utility.getComuneFromCodiceAzienda(iab.getCodice_azienda(), db));
						allevamento.setNote(iab.getNote());
						allevamento.setSpecie_allevata(Integer.parseInt(iab.getSpecie_allevata()));
						allevamento.setDescrizione_specie(listaSpeci.getSelectedValue(allevamento.getSpecie_allevata()));
						allevamento.setCfDetentore(iab.getCod_fiscale_det());
						allevamento.setCfProprietario(iab.getCod_fiscale());
						
						if (iab.getData_inizio_attivita()!=null)
						{
						String dataInizioAttivita = iab.getData_inizio_attivita().substring(0, 10);
						try{
							allevamento.setData_inizio_attivita( sdf1.format( sdf2.parse(dataInizioAttivita) ) );
						}
						catch (Exception e) {
							
						}
						}
						
						if (iab.getData_fine_attivita() != null)
						{
							String dataFineAttivita = iab.getData_fine_attivita().substring(0, 10);
							try{
								allevamento.setData_fine_attivita( sdf1.format( sdf2.parse(dataFineAttivita) ) );
							}
							catch (Exception e) {
								e.printStackTrace();
								
							}
						}
						allevamento.setInBDN( true );
					
					
				  }
			  }
			
			  
			  OrgDetails = new Stabilimento(db,Integer.parseInt(orgId));
			  
			//Gestione campi estesi sulle linee produttive
			//	caricaLineeCampiEstesi(context, db, OrgDetails);
			  context.getRequest().setAttribute("OrgDetails", OrgDetails);
			  diff = OrgDetails.equals(allevamento, db, Integer.parseInt(idRel));
			  Iterator iDiff = diff.iterator();
			  while (iDiff.hasNext())
			  {
				  String differenza = iDiff.next().toString();
			  }
			}
		  catch (Exception errorMessage){
			  errorMessage.printStackTrace();
				
			}
			finally
			{
				freeConnection(context, db);
			}
		  context.getRequest().setAttribute("AllevamentoBDN", allevamento);
		  context.getRequest().setAttribute("DiffenzeBDN", diff);
		  context.getRequest().setAttribute("OrgDetails", OrgDetails);

		  
		  return ("CompareWsOK");
		  
		  
	  }
	  
	  
	public String executeCommandToAdd(ActionContext context) {

		String scia = context.getRequest().getParameter("scia");
		if (scia!=null && scia.equals("1"))
			return "ToAddSciaOK";
		else if (scia!=null && scia.equals("0"))
			return "ToAddNonSciaOK";

		return "ToAddOK";

	}

	public String executeCommandDefault(ActionContext context) {
		
		
		Connection db = null ;
		try
		
		{
			db = this.getConnection(context);
			ComuniAnagrafica c = new ComuniAnagrafica();
			c.setInRegione(true);
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
		}
		catch(SQLException e)
		{
			
		}finally{
			this.freeConnection(context, db);
		}
		
		return "DefaultOK";
//		return  executeCommandSearchForm(context);

	}
	
	public String executeCommandImportAutoveicoli(ActionContext context) {
		
		
		Connection db = null ;
		try
		
		{
			db = this.getConnection(context);
			String orgId = context.getParameter("orgId");
			String tempAltId = context.getRequest().getParameter("altId");		
			String idStabilimento = context.getRequest().getParameter("stabId");
			Stabilimento stab = new Stabilimento(db, Integer.parseInt(idStabilimento));
			stab.insertAutomezziImport(db,Integer.parseInt(orgId));
		}
		catch(SQLException e)
		{
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.freeConnection(context, db);
		}
		
		return executeCommandDetails(context);
//		return  executeCommandSearchForm(context);

	}

	public String executeCommandAdd(ActionContext context) {




		Connection db = null;
		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			String flagDia = context.getParameter("flagDia");
			if (flagDia==null)
				flagDia = "false" ;

			LookupList TipoMobili = new LookupList(db,"lookup_tipo_mobili");
			TipoMobili.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoMobili", TipoMobili);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaList", TipoImpresaList);


			LookupList TipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");

			String fissa = context.getRequest().getParameter("fissa");			
			if (fissa!=null && fissa.equals("true"))
				TipoAttivita.removeElementByCode(2);
			else if (fissa!=null && fissa.equals("false"))
				TipoAttivita.removeElementByCode(1);
			TipoAttivita.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivita", TipoAttivita);
			context.getRequest().setAttribute("fissa", fissa);		

			LookupList TipoCarattere = new LookupList(db,"opu_lookup_tipologia_carattere");
			TipoCarattere.addItem(-1, "Seleziona Tipo Carattere");
			context.getRequest().setAttribute("TipoCarattere", TipoCarattere);




			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaList", TipoSocietaList);

			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);


			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);


			Stabilimento newStabilimento = new Stabilimento();


			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");

			listaToponimi.buildList(db);

			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);


			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				if ((Stabilimento) context.getRequest().getAttribute("Stabilimento") != null)
					newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");


			newStabilimento.setIdStabilimento(DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq"));

			newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			newStabilimento.getPrefissoPageCampiEstesi(context.getAction().getActionName());
			if (context.getParameter("tipoInserimento")!=null)
				newStabilimento.setTipoInserimentoScia(Integer.parseInt(context.getParameter("tipoInserimento")));
			int idOperatore = -1;



			if (context.getRequest().getParameter("idOp") != null)
				idOperatore = Integer.parseInt(context.getRequest().getParameter("idOp"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest().getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			context.getRequest().setAttribute("Operatore", operatore);
			newStabilimento.setOperatore(operatore);
			if(context.getParameter("stato")!=null && !"".equals(context.getParameter("stato")))
				newStabilimento.setStato(Integer.parseInt(context.getParameter("stato")));

			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);

			//			if(thisUser.getUserRecord().getSuap()!=null)
			//			{
			//
			//				newStabilimento.getSedeOperativa().setInfoComune(thisUser.getUserRecord().getSuap().getComune(),db);
			//			}

			context.getRequest().setAttribute("newStabilimento",newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LineaProduttivaList lpList = new LineaProduttivaList();
			//lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute("tipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));





			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			//			else
			//			{
			//				provinciaAsl.getProvincia(db, thisUser.getUserRecord().getSuap().getComune());
			//			}
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			if (context.getRequest().getAttribute("newStabilimento")!=null)
				context.getRequest().setAttribute("newStabilimento",context.getRequest().getAttribute("newStabilimento"));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "AddStabilimento");

	}


	public String executeCommandAddSuap(ActionContext context) {


		Connection db = null;
		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			String flagDia = context.getParameter("flagDia");
			if (flagDia==null)
				flagDia = "false" ;




			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaList", TipoImpresaList);


			LookupList TipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			TipoAttivita.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivita", TipoAttivita);

			LookupList TipoCarattere = new LookupList(db,"opu_lookup_tipologia_carattere");
			TipoCarattere.addItem(-1, "Seleziona Tipo Carattere");
			context.getRequest().setAttribute("TipoCarattere", TipoCarattere);


			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");

			listaToponimi.buildList(db);

			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa'");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaList", TipoSocietaList);

			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);


			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);


			Stabilimento newStabilimento = new Stabilimento();





			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				if ((Stabilimento) context.getRequest().getAttribute("Stabilimento") != null)
					newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");

			newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			newStabilimento.getPrefissoPageCampiEstesi(context.getAction().getActionName());

			newStabilimento.setIdStabilimento(DatabaseUtils.getNextSeq(db,"opu_stabilimento_id_seq"));


			int idOperatore = -1;

			if (context.getRequest().getParameter("idOp") != null)
				idOperatore = Integer.parseInt(context.getRequest().getParameter("idOp"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest().getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			context.getRequest().setAttribute("Operatore", operatore);
			newStabilimento.setOperatore(operatore);
			newStabilimento.setTipoInserimentoScia(Integer.parseInt(context.getParameter("tipoInserimento")));


			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);

			//			if(thisUser.getUserRecord().getSuap()!=null)
			//			{
			//
			//				newStabilimento.getSedeOperativa().setInfoComune(thisUser.getUserRecord().getSuap().getComune(),db);
			//			}

			context.getRequest().setAttribute("newStabilimento",newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LineaProduttivaList lpList = new LineaProduttivaList();
			//lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute("tipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));





			Provincia provinciaAsl = new Provincia();
			if (thisUser.getUserRecord().getSuap()==null)
				provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			//			else
			//			{
			//				provinciaAsl.getProvincia(db, thisUser.getUserRecord().getSuap().getComune());
			//			}
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			if (context.getRequest().getAttribute("newStabilimento")!=null)
				context.getRequest().setAttribute("newStabilimento",context.getRequest().getAttribute("newStabilimento"));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "AddStabilimento");
	}

	public String executeCommandInsert(ActionContext context)
			throws SQLException, IndirizzoNotFoundException {

		if (!hasPermission(context, "opu-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;

		String flagDia = context.getParameter("flagDia");
		if (flagDia==null)
			flagDia = "false" ;
		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		try {
			//			SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "");

			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);


			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);

			if (("false").equals((String) context.getParameter("doContinueStab"))) { 

				LineaProduttiva lp = null;
				newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));

				LineaProduttivaList arrayListeProduttiveDaConservare = new LineaProduttivaList();
				arrayListeProduttiveDaConservare.clear();

				if (	context.getRequest().getParameterValues("idLineaProduttiva") != null && 
						context.getRequest().getParameterValues("idLineaProduttiva").length > 0
						) 
				{

					try {


						String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
						for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
							if (!lineeProduttiveSelezionate[i].equals("")) 
							{
								if (Integer.parseInt(lineeProduttiveSelezionate[i])>0)
								{
									lp = new LineaProduttiva();
									lp.queryRecordScelta(db, Integer.parseInt(lineeProduttiveSelezionate[i]));
									lp.setDataInizio(context.getParameter("dataInizio"+Integer.parseInt(lineeProduttiveSelezionate[i])));
									lp.setDataFine(context.getParameter("dataFine"+Integer.parseInt(lineeProduttiveSelezionate[i])));

									InformazioniStabilimento infoStab = new InformazioniStabilimento(context);
									lp.setInfoStab(infoStab);


									arrayListeProduttiveDaConservare.add(lp);
								}
							}
						}

						newStabilimento.setListaLineeProduttive(arrayListeProduttiveDaConservare);
					} catch (Exception e) {
						e.printStackTrace();
					} // regione

				}



				if (context.getRequest().getParameter("inregione") != null && ("no").equals(context.getRequest().getParameter("inregione")))
					newStabilimento.setFlagFuoriRegione(true);

				//				soggettoAdded = new SoggettoFisico(context.getRequest());
				//				Indirizzo add = soggettoAdded.getIndirizzo();
				//
				//				add.setDescrizioneComune(comuniList.getSelectedValue(add.getComune()));
				//				add.setDescrizione_provincia(provinceList.getSelectedValue(add.getProvincia()));

				//				soggettoAdded.setIndirizzo(add);
				//				newStabilimento.setRappLegale(soggettoAdded);


				Indirizzo indirizzoAdded = null;
				if (new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
					indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
					indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa
					indirizzoAdded.setDescrizione_provincia(indirizzoAdded.getProvincia());
					indirizzoAdded.setLatitudine(context.getParameter("latitudine"));
					indirizzoAdded.setLongitudine(context.getParameter("longitudine"));


				} else {
					indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
					indirizzoAdded.setLatitudine(context.getParameter("latitudine"));
					indirizzoAdded.setLongitudine(context.getParameter("longitudine"));
					indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa

				}
				indirizzoAdded.setDescrizione_provincia(provinceList.getSelectedValue(indirizzoAdded.getProvincia()));					
				indirizzoAdded.setLatitudine(context.getParameter("latitudine"));


				indirizzoAdded.setDescrizioneComune(comuniList.getSelectedValue(indirizzoAdded.getComune()));

				newStabilimento.setSedeOperativa(indirizzoAdded);
				context.getRequest().setAttribute("LineaProduttivaScelta", arrayListeProduttiveDaConservare);
				context.getRequest().setAttribute("newStabilimento",newStabilimento);


				Operatore operatore = new Operatore () ;
				operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

				if (newStabilimento.getIdOperatore() >0)
					operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());
				context.getRequest().setAttribute("Operatore", operatore);

				return executeCommandAdd(context);
			}
			newStabilimento.setEnteredBy(super.getUserId(context));
			newStabilimento.setModifiedBy(super.getUserId(context));
			newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));
			newStabilimento.getPrefissoAction(context.getAction().getActionName());



			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");







			Indirizzo indirizzoAdded = null;
			if (context.getRequest().getParameter("via") != null && new Integer(context.getRequest().getParameter("via")).intValue() > 0) {
				indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setLatitudine(context.getParameter("latitudine"));
				indirizzoAdded.setLongitudine(context.getParameter("longitudine"));
				indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa
				indirizzoAdded.updateCoordinate(db);

			} else {
				indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
				indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA); // Operativa

			}
			newStabilimento.setSedeOperativa(indirizzoAdded);

			/**
			 * CONTROLLO DI ESISTENZA stabilimento per id indirizzo e id operatore
			 */
			exist = newStabilimento.checkEsistenzaStabilimento(db);

			if (exist) 
			{
				context.getRequest().setAttribute("Exist","Esiste Uno Stabilimento con la stessa sede Operativa ");

				return executeCommandAdd(context);
			}
			for (int i = 0; i < newStabilimento.getListaLineeProduttive().size(); i++) {
				newStabilimento.getListaLineeProduttive().remove(i);
			}
			if (context.getRequest().getParameterValues("idLineaProduttiva") != null && context.getRequest().getParameterValues("idLineaProduttiva").length ==1 && context.getRequest().getParameterValues("idLineaProduttiva")[0].equals("")) {
				Operatore operatore = new Operatore () ;
				operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());
				context.getRequest().setAttribute("Operatore", operatore);
				context.getRequest().setAttribute("Error", "Attenzione Selezionare almeno una linea produttiva");
				return executeCommandAdd(context);
			}
			else

				if (context.getRequest().getParameterValues("idLineaProduttiva") != null && context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {

					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");

					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						if (!lineeProduttiveSelezionate[i].equals("")) {
							LineaProduttiva lp = new LineaProduttiva();

							lp.setIdRelazioneAttivita(lineeProduttiveSelezionate[i]);
							if (context.getRequest().getParameter("dataInizio" + lp.getIdRelazioneAttivita()) != null)
								lp.setDataInizio(context.getRequest().getParameter("dataInizio"+ lp.getIdRelazioneAttivita()));

							if (context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()) != null)
								lp.setDataFine(context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()));

							if (context.getRequest().getParameter("stato" + lp.getIdRelazioneAttivita()) != null)
								lp.setStato(Integer.parseInt(context.getRequest().getParameter("stato"+ lp.getIdRelazioneAttivita())));
							if (context.getRequest().getParameter("tipo_attivita_produttiva" + lp.getIdRelazioneAttivita()) != null)
								lp.setTipoAttivitaProduttiva(Integer.parseInt(context.getRequest().getParameter("tipo_attivita_produttiva"+ lp.getIdRelazioneAttivita())));
							if (context.getParameter("principale")!=null && Integer.parseInt(lineeProduttiveSelezionate[i])== (Integer.parseInt(context.getParameter("principale"))))
								lp.setPrincipale(true);
							InformazioniStabilimento infoStab = new InformazioniStabilimento(context);
							lp.setInfoStab(infoStab);
							newStabilimento.getListaLineeProduttive().add(lp);
						}
					}

				}

			String inRegione = (String) context.getRequest().getParameter("inregione");

			if (inRegione != null) {
				newStabilimento.setFlagFuoriRegione(inRegione);
			}
			Object[] asl;
			if (!newStabilimento.isFlagFuoriRegione()) 
				asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
			else
				asl = null;

			if (asl != null && asl.length > 0) {

				Object[] aslVal = (Object[]) asl[0];
				if (aslVal != null && aslVal.length > 0)
					newStabilimento.setIdAsl((Integer) aslVal[0]);

			} else {
				newStabilimento.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
			}
			isValid = this.validateObject(context, db, newStabilimento);

			if (isValid) {   
				recordInserted = newStabilimento.insert(db, true,context);
				if (newStabilimento.isFlagDia()==false)
				{
					int idComune = newStabilimento.getSedeOperativa().getComune();
					ComuniAnagrafica comune = new ComuniAnagrafica(db, idComune);

					//					newStabilimento.generaCodice(context,db, comune.getCodice());


				}
			}
			if (recordInserted) {

				Stabilimento stabilimentoInserito = new Stabilimento(db, newStabilimento.getIdStabilimento());
				context.getRequest().setAttribute("Stabilimento",stabilimentoInserito);
				context.getRequest().setAttribute("opId",stabilimentoInserito.getIdOperatore());
				context.getRequest().setAttribute("idStab",stabilimentoInserito.getIdStabilimento());
			}


		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);

		}

		return (executeCommandDetails(context));

	}

	public String executeCommandUpdateLineaProduttiva(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException
	{

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;


		try {

			db = this.getConnection(context);
			Stabilimento newStabilimento = new Stabilimento(db,Integer.parseInt(context.getParameter("stabId")));


			LineaProduttiva lp = null;
			LineaProduttivaList arrayListeProduttiveDaConservare = new LineaProduttivaList();
			arrayListeProduttiveDaConservare.clear();
			if (	context.getRequest().getParameterValues("idLineaProduttiva") != null && 
					context.getRequest().getParameterValues("idLineaProduttiva").length > 0
					) 
			{

				try {


					String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
					for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
						if (!lineeProduttiveSelezionate[i].equals("")) 
						{
							if (Integer.parseInt(lineeProduttiveSelezionate[i])>0)
							{
								lp = new LineaProduttiva();
								lp.queryRecordScelta(db, Integer.parseInt(lineeProduttiveSelezionate[i]));
								if (context.getRequest().getParameter("dataInizio" + lp.getIdRelazioneAttivita()) != null)
									lp.setDataInizio(context.getRequest().getParameter("dataInizio"+ lp.getIdRelazioneAttivita()));

								if (context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()) != null)
									lp.setDataFine(context.getRequest().getParameter("dataFine" + lp.getIdRelazioneAttivita()));

								if (context.getRequest().getParameter("stato" + lp.getIdRelazioneAttivita()) != null)
									lp.setStato(Integer.parseInt(context.getRequest().getParameter("stato"+ lp.getIdRelazioneAttivita())));
								if (context.getRequest().getParameter("tipo_attivita_produttiva" + lp.getIdRelazioneAttivita()) != null)
									lp.setTipoAttivitaProduttiva(Integer.parseInt(context.getRequest().getParameter("tipo_attivita_produttiva"+ lp.getIdRelazioneAttivita())));
								if (context.getParameter("principale")!=null && Integer.parseInt(lineeProduttiveSelezionate[i])== (Integer.parseInt(context.getParameter("principale"))))
									lp.setPrincipale(true);



								arrayListeProduttiveDaConservare.add(lp);
							}
						}
					}



					newStabilimento.setListaLineeProduttive(arrayListeProduttiveDaConservare);
					newStabilimento.getPrefissoAction(context.getAction().getActionName());
					newStabilimento.getPrefissoPageCampiEstesi(context.getAction().getActionName());

				} catch (Exception e) {
					e.printStackTrace();
				} // regione

			}


			if (("false").equals((String) context.getParameter("doContinueStab"))) { 
				context.getRequest().setAttribute("newStabilimento",newStabilimento);
				return "ModifyLpOK";
			}
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			newStabilimento.setModifiedBy(user.getUserId());
			int idComune = newStabilimento.getSedeOperativa().getComune();
			ComuniAnagrafica comune = new ComuniAnagrafica(db, idComune);
			//			newStabilimento.generaCodice(context,db, comune.getCodice());


			newStabilimento.updateLineeProduttive(db);

			Stabilimento stabilimentoInserito = new Stabilimento(db, newStabilimento.getIdStabilimento());
			context.getRequest().setAttribute("Stabilimento",newStabilimento);
			context.getRequest().setAttribute("opId",stabilimentoInserito.getIdOperatore());
			context.getRequest().setAttribute("idStab",stabilimentoInserito.getIdStabilimento());


			return executeCommandDetails(context);


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return null;

	}

	public String executeCommandCambiainOsa(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = ""
						+ (Integer) context.getRequest().getAttribute("opId");
			}

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, tempid);	
			context.getRequest().setAttribute("Operatore", operatore);

			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.setFlagDia(false);
			newStabilimento.cambiainOsa(db,((UserBean) context.getSession().getAttribute("User")).getUserId());
			newStabilimento.getPrefissoAction(context.getAction().getActionName());

			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			LookupList serviziocompetente = new LookupList(db, "lookup_account_stage");
			serviziocompetente.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", serviziocompetente);


			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,
					((UserBean) context.getSession().getAttribute("User"))
					.getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);

			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			return getReturn(context, "Details");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandGeneraNumero(ActionContext context) throws IndirizzoNotFoundException {

		if (!hasPermission(context, "opu_genera_numero-add")) {
			return ("PermissionError");
		}

		String tempOpId = context.getRequest().getParameter("opId");
		if (tempOpId == null) {
			tempOpId = ""
					+ (Integer) context.getRequest().getAttribute("opId");
		}

		String tempStabId = context.getRequest().getParameter("stabId");
		context.getRequest().setAttribute("stabId", tempStabId);
		context.getRequest().setAttribute("opId", tempOpId);
		if (tempStabId == null) {
			tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer stabid = null;

		if (tempOpId != null) {
			tempid = Integer.parseInt(tempOpId);
		}

		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		Connection db = null;
		try {
			db = this.getConnection(context);	
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			int idComune = newStabilimento.getSedeOperativa().getComune();


			ComuniAnagrafica comune = new ComuniAnagrafica(db, idComune);
			//			newStabilimento.generaCodice(context,db, comune.getCodice());
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			super.freeConnection(context, db);
		}

		return executeCommandDetails(context);

	}


	public String executeCommandDetails(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {
			db = this.getConnection(context);	
			
			String tempAltId = context.getRequest().getParameter("altId");
			
			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) 
			{
				tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
			}
			
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;

			int altId = -1;
			try 
			{
				altId = Integer.parseInt(tempAltId);
			}
			catch (Exception e)
			{
				
			}

			if (altId>0) 
			{
				newStabilimento = new Stabilimento(db,  altId, true);
			}
			
			else if (tempStabId != null) 
			{
				stabid = Integer.parseInt(tempStabId);
				newStabilimento = new Stabilimento(db,  stabid);
				
				String sincronizza = context.getParameter("sincronizzaBdu");
				if(sincronizza!=null)
				{
					GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
					gBdu.inserisciNuovaSciaBdu(stabid);
					newStabilimento.setCodiceErroreSuap("Sincronizzazione Avvenuta Con succeso");
					
				}
				String sincronizzaMq = context.getParameter("sincronizzaBduMq");
				if(sincronizzaMq!=null)
				{
					int idRelCanile = Integer.parseInt(context.getParameter("idRelCanile"));
					GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
					int superficie = newStabilimento.getSuperficieMqCanile(db, idRelCanile);
					System.out.println("Tentativo di sincronizzazione: id rel" + idRelCanile+ " superficie "+superficie);
					if (superficie >=0) 
					{
						gBdu.variazioneSuperficie(stabid,superficie, getUserId(context));
						newStabilimento.setCodiceErroreSuap("Sincronizzazione Avvenuta Con succeso");
					}
					else 
					{
						newStabilimento.setCodiceErroreSuap("Sincronizzazione NON AVVENUTA - Valore non consentito nel campo MQ.");
					}
					
				}
			}

			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",	newStabilimento);
			
			
			ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico.getOperatoriStabilimentiOpuById(db, newStabilimento.getIdStabilimento());
			context.getRequest().setAttribute("stabilimentiAssociateMercatoIttico",	stabilimentiAssociateMercatoIttico);
			
			context.getRequest().setAttribute("Operatore", newStabilimento.getOperatore());
			 
			RicercaOpu AnagraficaStabilimento = new RicercaOpu(db,newStabilimento.getIdStabilimento(),"id_stabilimento");
			
			context.getRequest().setAttribute("AnagraficaStabilimento", AnagraficaStabilimento);
				
			
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,newStabilimento.getIdStabilimento(),null,null,context.getAction().getActionName()));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,newStabilimento.getIdStabilimento(),null,null,context.getAction().getActionName()));



			//Gestione campi estesi sulle linee produttive
			//caricaLineeCampiEstesi(context, db, newStabilimento);
			
			caricaInfoAllevamento(context, db, newStabilimento);
			
			context.getRequest().setAttribute("id_stabilimento", newStabilimento.getIdStabilimento());

			return "DetailsTempOk";
			
			//return getReturn(context, "Details");
			

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
	}
	
	
	
	public String executeCommandRiattivazioneSospensione(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {
			db = this.getConnection(context);	

			String tempAltId = context.getRequest().getParameter("altId");
			
			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;

			int altId = -1;
			try {
				altId = Integer.parseInt(tempAltId);
				}
			catch (Exception e){}


			if (altId>0) {
				newStabilimento = new Stabilimento(db,  altId, true);
			}
			
			else if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
				newStabilimento = new Stabilimento(db,  stabid);
			}
			
			String dataRiattivazione = context.getParameter("dataRiattivazione");
			String numDecreto = context.getRequest().getParameter("decretoRiattivazione");
			String note =context.getRequest().getParameter("noteRiattivazione");
			
			String[] idLineeProduttiveDaRiattivare = context.getRequest().getParameterValues("idLineaProduttivaRiattivazione");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for(String idLinea : idLineeProduttiveDaRiattivare )
			{
				LineaProduttiva lp = null ;
				
				  Iterator<LineaProduttiva> itLp = newStabilimento.getListaLineeProduttive().iterator();
			      while (itLp.hasNext())
			      {
			    	   lp = itLp.next();
			    	  if(lp.getId()==Integer.parseInt(idLinea))
			    	  {
			    		  break ;
			    	  }
			      }
				
				
				String update = "update opu_lista_decreti_riconosciuti set ultimoDecreto = false where id_stabilimento = ? and id_linea_attivita = ? ";
				PreparedStatement pst = db.prepareStatement(update);
				pst.setInt(1, newStabilimento.getIdStabilimento());
				pst.setInt(2,Integer.parseInt(idLinea));
				pst.execute();
				
				String insert = "insert into opu_lista_decreti_riconosciuti ( data_riattivazione,note,num_decreto,riattivato_da,data_inserimento,data_sospensione,id_stabilimento,id_linea_attivita,ultimoDecreto)"
						+ "values (?,?,?,?,current_timestamp,?,?,?,?)";
				 pst = db.prepareStatement(insert);
				 pst.setTimestamp(1, new Timestamp(sdf.parse(dataRiattivazione).getTime()));
				 pst.setString(2, note);
				 pst.setString(3, numDecreto);
				 pst.setInt(4, getUserId(context));
				 pst.setTimestamp(5,lp.getDataSospensionevolontaria());
				 pst.setInt(6, newStabilimento.getIdStabilimento());
				 pst.setInt(7, Integer.parseInt(idLinea));
				 pst.setBoolean(8, true);
				 pst.execute();
				 
				 
				 pst = db.prepareStatement("update opu_relazione_stabilimento_linee_produttive set stato = ? where id_stabilimento = ? and id_linea_produttiva = ? and enabled;update opu_stabilimento set stato = ? where id = ?");
				pst.setInt(1, Stabilimento.STATO_AUTORIZZATO);
				pst.setInt(2, newStabilimento.getIdStabilimento());
				pst.setInt(3, Integer.parseInt(idLinea));
				pst.setInt(4, Stabilimento.STATO_AUTORIZZATO);
				pst.setInt(5, newStabilimento.getIdStabilimento());
				pst.execute();
				
			}
			
			
			
			
			
			if (altId>0) {
				newStabilimento = new Stabilimento(db,  altId, true);
			}
			
			else if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
				newStabilimento = new Stabilimento(db,  stabid);
			}
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			context.getRequest().setAttribute("Operatore", newStabilimento.getOperatore());


			TicketList diffList = new TicketList();
			diffList.setIdStabilimento(newStabilimento.getIdStabilimento());
			diffList.buildListDiffideOsaCentralizzato(db);

			for(int i = 0 ; i <diffList.size();i++ )
				((Ticket) diffList.get(i)).setAction(context.getAction().getActionName());

			context.getRequest().setAttribute("DiffideList", diffList);

			
			RicercheAnagraficheTab.inserOpu(db, newStabilimento.getIdStabilimento());

			//Gestione campi estesi sulle linee produttive
			//caricaLineeCampiEstesi(context, db, newStabilimento);

			

			return getReturn(context, "Details");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}


	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();


		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer stabid = null;



		if (tempStabId != null) {
			try {stabid = Integer.parseInt(tempStabId);} catch (Exception e) {}
		}
		
		if (stabid!=null && stabid>0)
			ticList.setIdStabilimento(stabid);
		
		
		String statusId = context.getRequest().getParameter("statusId");
		ticList.setStatusId(statusId);		
		
		
		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink(context.getAction().getActionName()+".do?command=ViewVigilanza&stabId="+stabid
				);
		ticList.setPagedListInfo(ticketListInfo);
		try {	



			db = this.getConnection(context);	

			
			if (stabid == null || stabid<=0){
				String tempAltId = context.getRequest().getParameter("altId");
				if (tempAltId == null) {
					tempAltId = ""
							+ (Integer) context.getRequest().getAttribute("altId");
				}
				Integer altid = null;
				if (tempAltId != null) {
					altid = Integer.parseInt(tempAltId);
				}
				stabid = altid-20000000; //sviluppare in futuro una funzione per ricavare l'id originale dall'alt_id
				ticList.setIdStabilimento(stabid);
			}
			



			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());

			context.getRequest().setAttribute("OrgDetails", newStabilimento);

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db,
					"lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db,
					"lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setIdStabilimento(newStabilimento.getIdStabilimento());
			/*
			 * int punteggioAccumulato =
			 * controlliList.buildListControlliUltimiAnni(db, passedId);
			 * context.getRequest().setAttribute("punteggioUltimiAnni",
			 * punteggioAccumulato);
			 */
			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			// find record permissions for portal users

				
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
				
			ticList.buildList(db);

			context.getRequest().setAttribute("TicList", ticList);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}


	public String executeCommandViewVigilanzaDistributori(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Stabilimento newOrg = null;
		//Process request parameters
		Integer stabid = null;


		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
		}


		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}  

		int passedIdMacchinetta = Integer.parseInt(
				context.getRequest().getParameter("id"));

		context.getRequest().setAttribute("idMacchinetta", ""+passedIdMacchinetta);


		try {
			db = this.getConnection(context);
			//find record permissions for portal users

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			/* if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/


			newOrg = new Stabilimento(db, stabid);
			newOrg.getPrefissoAction(context.getAction().getActionName());
			ArrayList<Distrubutore> l = new ArrayList<Distrubutore>();
			newOrg.setListaDistributori(l);
			//Prepare PagedListInfo
			newOrg.getPrefissoAction(context.getAction().getActionName());
			newOrg.getPrefissoPageCampiEstesi(context.getAction().getActionName());

			PagedListInfo ticketListInfo = this.getPagedListInfo(
					context, "AccountTicketInfo", "t.entered", "desc");
			ticketListInfo.setLink(
					newOrg.getAction()+".do?command=ViewVigilanza&orgId=" + stabid);
			ticList.setPagedListInfo(ticketListInfo);
			ticList.setIdStabilimento(stabid);
			ticList.setIdMacchinetta(passedIdMacchinetta);

			ticList.buildListMacchinette(db);
			context.getRequest().setAttribute("id", context.getRequest().getParameter("id"));
			int idDistributore=-1;
			if(context.getRequest().getParameter("id")!=null)
				idDistributore=Integer.parseInt(context.getRequest().getParameter("id"));

			Distrubutore distributore=new Distrubutore();
			if(idDistributore!=-1){

				distributore= distributore.loadDistributore(newOrg.getOrgId(), idDistributore, db);
				context.getRequest().setAttribute("NewDistributore",distributore);
				context.getRequest().setAttribute("id",idDistributore);

			}
			if(context.getRequest().getParameter("asl")!=null)
				context.getRequest().setAttribute("aslMacchinetta", context.getRequest().getParameter("asl"));
			else

				context.getRequest().setAttribute("aslMacchinetta", context.getRequest().getParameter("aslMacchinetta"));

			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}



	public String executeCommandViewCessazionevariazione(ActionContext context) {
		if (!hasPermission(context,
				"opu-cessazionevariazione-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
		Stabilimento newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"stabId"));


		int opId = Integer.parseInt(context.getRequest().getParameter(
				"opId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.entered", "desc");
		ticketListInfo
		.setLink("StabilimentoAction.do?command=ViewCessazionevariazione&stabId="
				+ passedId+"&opId="+opId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users

			newOrg = new Stabilimento(db, passedId);
			newOrg.getPrefissoAction(context.getAction().getActionName());
			newOrg.getPrefissoPageCampiEstesi(context.getAction().getActionName());
			ticList.setIdStabilimento(passedId);

			ticList.buildList(db);

			org.aspcfs.modules.cessazionevariazione.base.TicketList richieste = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
			richieste.buildListRichiesteVolture(db, opId, -1);
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("RichiesteVoltureOperatore", richieste);

			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewCessazionevariazione");
	}



	public String executeCommandRaggruppaStabilimentiImprese(ActionContext context) {
		if (!hasPermission(context,
				"accounts-accounts-cessazionevariazione-view")) {
			return ("PermissionError");
		}
		Connection db = null;

		Stabilimento newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"stabId"));


		int opId = Integer.parseInt(context.getRequest().getParameter(
				"opId"));


		try {
			db = this.getConnection(context);
			// find record permissions for portal users

			newOrg = new Stabilimento(db, passedId);
			newOrg.getPrefissoAction(context.getAction().getActionName());
			newOrg.getPrefissoPageCampiEstesi(context.getAction().getActionName());
			String sel1 = "select * from opu_raggruppamento_operatori where id_stabilimento =?";
			PreparedStatement pst1 = db.prepareStatement(sel1);
			pst1.setInt(1,newOrg.getIdStabilimento());
			ResultSet rs =  pst1.executeQuery();


			if (!rs.next())
			{
				String sel = "select * from raggruppa_operatori_852(?)";
				PreparedStatement pst = db.prepareStatement(sel);
				pst.setInt(1,newOrg.getIdOperatore());
				pst.execute();

				sel1 = "select * from opu_raggruppamento_operatori where id_stabilimento =?";
				pst1 = db.prepareStatement(sel1);
				pst1.setInt(1,newOrg.getIdStabilimento());
				rs =  pst1.executeQuery();
				if (rs.next())
				{
					context.getRequest().setAttribute("idStab", rs.getInt("id_stabilimento_mantenuto"));
					context.getRequest().setAttribute("opId", rs.getInt("id_opu_operatore_nuovo"));


				}

			}
			else
			{
				if (rs.next())
				{
					context.getRequest().setAttribute("idStab", rs.getInt("id_stabilimento_mantenuto"));
					context.getRequest().setAttribute("opId", rs.getInt("id_opu_operatore_nuovo"));


				}
			}


			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandDetails(context);
	}


public String executeCommandTrashStabilimento(ActionContext context) {
		
		
		Connection db = null;
		Stabilimento newStabilimento = null;
		try {
			db=super.getConnection(context);
			
			
			
		String tempStabId = context.getRequest().getParameter("idAnagrafica");
		Stabilimento stab = new Stabilimento(db, Integer.parseInt(tempStabId));
		stab.eliminaAnagrafica(db, getUserId(context));
		
		/*Ticket tic = new Ticket();
		tic.setIdStabilimento(stab.getIdStabilimento());
		tic.logicdelete(db, getDbNamePath(context));
		*/
		RicercheAnagraficheTab.inserOpu(db, stab.getIdStabilimento());
		context.getRequest().setAttribute("idStab", stab.getIdStabilimento());
		return executeCommandSearchForm(context);
		
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	public String executeCommandModifyLineaProduttiva(ActionContext context) throws IndirizzoNotFoundException {

		Connection db = null;
		org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
		Stabilimento newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"stabId"));


		int opId = Integer.parseInt(context.getRequest().getParameter(
				"opId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.entered", "desc");
		ticketListInfo
		.setLink("StabilimentoAction.do?command=ViewCessazionevariazione&stabId="
				+ passedId+"&opId="+opId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users

			newOrg = new Stabilimento(db, passedId);
			newOrg.getPrefissoAction(context.getAction().getActionName());
			newOrg.getPrefissoPageCampiEstesi(context.getAction().getActionName());




			context.getRequest().setAttribute("newStabilimento",newOrg);
			context.getRequest().setAttribute("ModificaLp","OK");

		}
		catch(SQLException e)
		{
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return ("ModifyLpOK");



	}

	public String executeCommandModifyImpresa(ActionContext context) {

		OperatoreAction op = new OperatoreAction();
		return op.executeCommandModify(context);

	}

	public String executeCommandUpdateSoggettoImpresa(ActionContext context) {

		OperatoreAction op = new OperatoreAction();
		try {
			return op.executeCommandUpdateSoggettoFisicoImpresa(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("SystemError"); 

	}


	public String executeCommandUpdateSedeLegale(ActionContext context) throws SQLException{
		OperatoreAction op = new OperatoreAction();
		return op.executeCommandUpdateSedeLegale(context) ;
	}


	public String executeCommandSearchLineaProduttiva(ActionContext context) throws SQLException{
		LineaProduttivaAction op = new LineaProduttivaAction();
		return op.executeCommandSearch(context) ;
	}







	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "opu-view"))) {
			return ("PermissionError");
		}

		//Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			ListaStati.removeElementByLevel(1);
			ListaStati.removeElementByLevel(3);
			ListaStati.removeElementByLevel(5);
			ListaStati.removeElementByLevel(6);
			ListaStati.removeElementByLevel(7);
			
			
			LookupList normeList = new LookupList(db, "opu_lookup_norme_master_list");
			normeList.addItem(-1, "--Seleziona Norma--");
			context.getRequest().setAttribute("normeList", normeList);
			
			LookupList tipoRicerca = new LookupList(db, "lookup_tipo_ricerca_anagrafica");
			tipoRicerca.addItem(-1, "--TUTTI I TIPI DI ANAGRAFICHE--");
			context.getRequest().setAttribute("tipoRicerca", tipoRicerca);
			
			
			
			context.getRequest().setAttribute("ListaStati", ListaStati);

			this.deletePagedListInfo(context, "SearchOpuListInfo");
			//reset the offset and current letter of the paged list in order to make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);

			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}




	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "opu-view")) {
			return ("PermissionError");
		}



		StabilimentoList organizationList = new StabilimentoList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
		searchListInfo.setLink("OpuStab.do?command=Search");

		Connection db = null;
		try {
			db = this.getConnection(context);	      

			searchListInfo.setSearchCriteria(organizationList, context);     
			organizationList.setPagedListInfo(searchListInfo);
			//	organizationList.setEscludiInDomanda(true);
			//	organizationList.setEscludiRespinti(true);
			organizationList.setIdAsl(context.getRequest().getParameter("searchaslSedeProduttiva"));
			organizationList.buildList(db);


			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ListaStati", ListaStati);

			context.getRequest().setAttribute("StabilimentiList", organizationList);

			return "ListOK";



		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}


	public String executeCommandVerificaEsistenza(ActionContext context)
			throws SQLException, IndirizzoNotFoundException,Exception {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;
		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

		String flagDia = context.getParameter("flagDia");
		if (flagDia==null)
			flagDia = "false" ;
		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		try {
			//				SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);
			OperatoreAction actionOperatore = new OperatoreAction();
			String esitoInserimentoOperatore = actionOperatore.executeCommandVerificaEsistenza(context,db);

			if(esitoInserimentoOperatore.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE))
			{
				// ESISTE UNA IMPRESA CON DATI DIVERSI


				List<Operatore> listaOperatori = (List<Operatore>)context.getRequest().getAttribute("ListaOperatori");
				newStabilimento.setListaOperatori(listaOperatori);
				newStabilimento.setCodiceErroreSuap("2");
				newStabilimento.setErroreSuap("Attenzione Esiste una impresa con la partita iva inserita");
				context.getRequest().setAttribute("Stabilimento", newStabilimento);
				return "JsonSuapStabilimentoOpu";
			}
			else
			{
				if(esitoInserimentoOperatore.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE_ORGANIZATION))
				{
					// ESISTE UNA IMPRESA CON DATI DIVERSI


					newStabilimento.setCodiceErroreSuap("2");
					newStabilimento.setErroreSuap("Attenzione Esiste una impresa con la partita iva inserita in altri Cavalieri");
					context.getRequest().setAttribute("Stabilimento", newStabilimento);
					return "JsonSuapStabilimentoOpu";
				}
				else
				{
					newStabilimento.setCodiceErroreSuap("0");
					newStabilimento.setErroreSuap("IMPRESA NON ESISTENTE");
					context.getRequest().setAttribute("Stabilimento", newStabilimento);
				}
			}
		}
		catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return "JsonSuapStabilimentoOpu";

		} finally {
			this.freeConnection(context, db);

		}
		return "JsonSuapStabilimentoOpu" ;


	}


	public String executeCommandVerificaEsistenzaStabilimento(ActionContext context) throws IndirizzoNotFoundException
	{
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;
		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

		String flagDia = context.getParameter("flagDia");
		if (flagDia==null)
			flagDia = "false" ;
		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");
		try {


			db = this.getConnection(context);
			StabilimentoList listaStab = new StabilimentoList();
			listaStab.setOperazione(context.getParameter("methodRequest"));
			if(context.getParameter("numeroRegistrazione")!=null && !"".equals(context.getParameter("numeroRegistrazione"))){
				listaStab.setNumeroRegistrazione(context.getParameter("numeroRegistrazione"));
				System.out.println("##### operazione eseguita"+context.getParameter("methodRequest"));
				if(context.getParameter("methodRequest")!=null &&   ! context.getParameter("methodRequest").equals("cambioTitolarita") )
					listaStab.setPartitaIva(context.getParameter("partitaIva"));
			}
			else
			{
				
				LookupList listaToponimi = new LookupList();
				listaToponimi.setTable("lookup_toponimi");
				listaToponimi.buildList(db);
				
				String descrToponimo = null;
				if(context.getParameter("toponimoSedeOperativa")!=null && context.getParameter("toponimoSedeOperativa").equals(""))
					descrToponimo = listaToponimi.getSelectedValue(Integer.parseInt(context.getParameter("toponimoSedeOperativa")));
				else if(context.getParameter("toponimoSedeOperativaId")!=null && !context.getParameter("toponimoSedeOperativaId").equals("") && !context.getParameter("toponimoSedeOperativaId").equals("undefined"))
					descrToponimo = listaToponimi.getSelectedValue(Integer.parseInt(context.getParameter("toponimoSedeOperativaId")));
				String civico = context.getParameter("civicoSedeOperativa");

				
				listaStab.setPartitaIva(context.getParameter("partitaIva"));
				listaStab.setCodiceFiscaleImpresa(context.getParameter("codFiscale"));
				listaStab.setComuneSedeProduttiva(context.getRequest().getParameter("searchcodeIdComuneStabinput"));
				listaStab.setIndirizzoSedeProduttiva(descrToponimo+"  "+context.getRequest().getParameter("viaStabinput"));
				listaStab.setCivicoSedeProduttiva(civico);
				listaStab.setiTipoAttivita(Integer.parseInt(context.getParameter("tipoAttivita")));
				if(context.getParameter("idLineaProduttiva")!=null && !"".equals(context.getParameter("idLineaProduttiva")))
				{
					//se l'id linea (ne puo' arrivare solo 1 ) è -1, allora è stato usato un candidato ranked
					//quindi devo usare un parametro di arrivo diverso
					int idLineaProd = -1;
					if("-1".equals(context.getParameter("idLineaProduttiva")))
					{
						//cerco parametro della forma candidato-n dove n è il numero della linea vecchia per la quale
						//e' stato usato candidato
						Map<String,String[]> tuttiPars = context.getRequest().getParameterMap();
						for(Map.Entry<String, String[]> entryPar : tuttiPars.entrySet())
						{
							if(entryPar.getKey().contains("candidato-") )
							{
								idLineaProd = Integer.parseInt(entryPar.getValue()[0]);
								break;
							}
						}
					}
					else
					{
						idLineaProd = Integer.parseInt(context.getParameter("idLineaProduttiva"));
					}
					listaStab.setIdLineaProduttivaMobile(idLineaProd);
				}
				
			}
			if (context.getParameter("methodRequest") != null && "new".equals(context.getParameter("methodRequest")))
			{
				listaStab.setFlagSearch(true);

			}
			

			
			listaStab.buildListSearch(db);

			List<Stabilimento> lista = new ArrayList<Stabilimento>();
			Iterator<Stabilimento> itStab =  listaStab.iterator();
			while (itStab.hasNext())
				lista.add(itStab.next());


			context.getRequest().setAttribute("ListaStabilimenti",lista);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "JsonStabilimentoListOpu" ;
	}

	
	
	
	public String executeCommandVerificaEsistenzaTarghe(ActionContext context) throws IndirizzoNotFoundException
	{
		Connection db = null;
	
		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");
		newOperatore.setPartitaIva(context.getParameter("partitaIva"));
		SoggettoFisico sf = new SoggettoFisico();
		sf.setCodFiscale(context.getParameter("codFiscaleSoggetto"));
		newOperatore.setRappLegale(sf);
		newStabilimento.setOperatore(newOperatore);
		try {


			db = this.getConnection(context);
			ArrayList<DatiMobile> listaTarghe = newStabilimento.cercaAltreTarghe(db);
			
			context.getRequest().setAttribute("ListaTarghe",listaTarghe);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "JsonStabilimentoListTargheOpu" ;
	}



	public String parameter(Object bean) throws ClassNotFoundException, NoSuchMethodException, SecurityException
	{

		String toRet = "" ;

		if (bean!=null)
		{
			Class obj =bean.getClass();
			Field[] fieldList = obj.getDeclaredFields();
			Method metodo = null; 
			Method[] listMethods = obj.getDeclaredMethods();

			for (Field field : fieldList)
			{
				Object ritorno = null ;

				for (Method met : listMethods)
				{
					if (met.getName().equalsIgnoreCase("get" + field.getName()))
					{
						try {
							ritorno = (String) met.invoke(bean, new Object[0]);
							toRet+=""+field.getName()+"="+ritorno+";";
						}
						catch (Exception ecc) { }

					}
				}




			}
		}
		return toRet ;

	}


	public String executeCommandInsertSuap(ActionContext context)
			throws SQLException, IndirizzoNotFoundException,Exception {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;



		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");

		Organization OrgImport = null;
		try {
			//				SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);

			String  retValue = "" ;
			String  operazione = context.getParameter("methodRequest");


			switch(operazione)
			{

			case "new" :
			{
				retValue = this.insertNuovoStabilimento(db, newStabilimento, context, OrgImport) ;
			
				break ;
			}

			

			}




			if (retValue!=null)
				return retValue;




		}

		catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return "JsonSuapStabilimentoOpu";

		} finally {
			this.freeConnection(context, db);

		}

		return "JsonSuapStabilimentoOpu";

	}


	public String executeCommandGestioneRichieste(ActionContext context) {
		if (!hasPermission(context, "opu-view")) {
			return ("PermissionError");
		}

		StabilimentoList organizationList = new StabilimentoList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
		searchListInfo.setLink("OpuStab.do?command=Search");

		Connection db = null;
		try {
			db = this.getConnection(context);	      

			organizationList.setInDomanda(true);


			int idAsl = ((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId();
			if (idAsl>0)
				organizationList.setIdAsl(idAsl);

			organizationList.buildList(db);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);
			context.getRequest().setAttribute("StabilimentiList", organizationList);

			return "GestioneRichiesteListOK";

		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}

	public String executeCommandDetailsMinimale(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;

			//			if (tempOpId != null) {
			//				tempid = Integer.parseInt(tempOpId);
			//			}

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	


			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",	newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);


			return getReturn(context, "DetailsMinimale");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandPrepareValidazione(ActionContext context) {


		if (!hasPermission(context, "opu_genera_numero-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			int stabid = Integer.parseInt(context.getRequest().getParameter("stabId"));

			db = this.getConnection(context);	


			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",	newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);


			return getReturn(context, "PrepareValidazione");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandValida(ActionContext context) {

		if (!hasPermission(context, "opu_genera_numero-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			int stabid = Integer.parseInt(context.getRequest().getParameter("stabId"));

			db = this.getConnection(context);	


			newStabilimento = new Stabilimento(db,  stabid);

			if (newStabilimento.getStato()!=Stabilimento.STATO_REGISTRAZIONE_ND){
				context.getRequest().setAttribute("Error", "Attenzione! Lo stabilimento risulta gia' validato!");
				return ("SystemError");
			}

			String esito = context.getRequest().getParameter("esitoValidazione");
			String note = context.getRequest().getParameter("note");
			Boolean esitoValidazione = Boolean.valueOf(esito);


			newStabilimento.setModifiedBy(super.getUserId(context));
			newStabilimento.updateValidazione(db, esitoValidazione);

			String[] infoUtente = {"-1", "", ""};
			infoUtente[0] =  String.valueOf(super.getUserId(context));
			infoUtente[1] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleRichiedente();
			infoUtente[2] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleDelegato();
			gestioneStorico(newStabilimento, (esitoValidazione) ? Storico.VERIFICA_OK : Storico.VERIFICA_KO, infoUtente, note, db);

			if (esitoValidazione){ 
				newStabilimento.generaNumeroRegistrazione(context, db);
				gestioneStorico(newStabilimento, Storico.REGISTRAZIONE_DISPONIBILE, infoUtente, note, db);
			}

			return getReturn(context, "Validazione");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public static void gestioneStorico(Stabilimento stab, int idOperazione, String[] infoUtente, String note, Connection db){
		Storico storico = new Storico();
		storico.setIdStabilimento(stab.getIdStabilimento());
		storico.setIdOperazione(idOperazione);
		storico.setIdUtente(infoUtente[0]);
		storico.setCodFiscaleUtenteRichiedente(infoUtente[1]);
		storico.setCodFiscaleUtenteDelegato(infoUtente[2]);
		storico.setNote(note);
		storico.insert(db);
	}

	public String executeCommandStoricoPratica(ActionContext context) {

		if (!hasPermission(context, "opu_storico-view")) {
			return ("PermissionError");
		}


		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {
			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}

			Integer stabid = null;

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	

			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			LookupList OperazioniList = new LookupList(db,"lookup_suap_operazioni");
			context.getRequest().setAttribute("OperazioniList", OperazioniList);



			Storico sto = new Storico();
			Vector storicoList = new Vector();
			storicoList = sto.cercaStoricoPratica(db, newStabilimento.getIdStabilimento());
			context.getRequest().setAttribute("listaStorico", storicoList);

			context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));

			return getReturn(context, "StoricoPratica");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandStoricoRichieste(ActionContext context) {

		if (!hasPermission(context, "opu_storico-view")) {
			return ("PermissionError");
		}


		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {
			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}

			Integer stabid = null;

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	

			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			StoricoRichieste sto = new StoricoRichieste();
			Vector storicoList = new Vector();
			storicoList = sto.cercaStoricoRichiesteVersioneConPendenti(db, newStabilimento.getIdStabilimento());
			context.getRequest().setAttribute("listaStorico", storicoList);

			context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));

			LookupList TipiRichiesta = new LookupList(db, "suap_lookup_tipo_richiesta");
			context.getRequest().setAttribute("TipiRichiesta", TipiRichiesta);
			
			
			
			return getReturn(context, "StoricoRichieste");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	public String executeCommandPrepareApproval(ActionContext context) {


		if (!hasPermission(context, "opu_genera_approval-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			int stabid = Integer.parseInt(context.getRequest().getParameter("stabId"));

			db = this.getConnection(context);	


			newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",	newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);


			return getReturn(context, "PrepareApproval");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandInsertApproval(ActionContext context) {

		if (!hasPermission(context, "opu_genera_approval-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {

			int stabid = Integer.parseInt(context.getRequest().getParameter("stabId"));

			db = this.getConnection(context);	


			newStabilimento = new Stabilimento(db,  stabid);

			if (newStabilimento.getStato()!=Stabilimento.STATO_IN_DOMANDA){
				context.getRequest().setAttribute("Error", "Attenzione! Lo stabilimento non risulta in domanda!");
				return ("SystemError");
			}

			String approval = context.getRequest().getParameter("approval");

			if (!newStabilimento.controllaApproval(approval, db)){
				context.getRequest().setAttribute("error", "Attenzione! L'Approval Number inserito risulta gia' presente in GISA o non conforme.");
				context.getRequest().setAttribute("stabid", stabid);
				return executeCommandPrepareApproval(context);
			}

			newStabilimento.setModifiedBy(super.getUserId(context));
			newStabilimento.updateValidazione(db, true);
			newStabilimento.generaApprovalNumber(approval, db);

			String[] infoUtente = {"-1", "", ""};
			infoUtente[0] =  String.valueOf(super.getUserId(context));
			infoUtente[1] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleRichiedente();
			infoUtente[2] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleDelegato();
			gestioneStorico(newStabilimento, Storico.APPROVAL_DISPONIBILE, infoUtente, "", db);

			return getReturn(context, "Validazione");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}



	public String executeCommandSearchFormNonFissa(ActionContext context) {
		if (!(hasPermission(context, "opu-view"))) {
			return ("PermissionError");
		}

		//Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("SiteList", siteList);



			this.deletePagedListInfo(context, "SearchOpuListInfo");
			//reset the offset and current letter of the paged list in order to make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);



		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchNonFissaOK");
	}

	public String executeCommandToAddNonFissa(ActionContext context) {
		return "AddNonFissaOK";
	}

	public String executeCommandAddImbarcazione(ActionContext context) {

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList tipoPesca = new LookupList(db, "lookup_tipo_pesca");
			context.getRequest().setAttribute("TipoPesca", tipoPesca);

			LookupList sistemaPesca = new LookupList(db, "lookup_sistema_pesca");
			context.getRequest().setAttribute("SistemaPesca", sistemaPesca);

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			String provincia = thisUser.getContact().getState();
			ArrayList<Integer> asl_id = new ArrayList<Integer>();

			if(provincia!=null){
				if(provincia.equals("AV")){
					asl_id.add(1);
					asl_id.add(2);
				}else if(provincia.equals("BN")){
					asl_id.add(3);
				}else if(provincia.equals("CE")){
					asl_id.add(4);
					asl_id.add(5);
				}else if(provincia.equals("NA")){
					asl_id.add(6);
					asl_id.add(7);
					asl_id.add(8);
					asl_id.add(9);
					asl_id.add(10);
				}else if(provincia.equals("SA")){
					asl_id.add(11);
					asl_id.add(12);
					asl_id.add(13);
				}}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		return getReturn(context, "AddImbarcazione");
	}

	public String executeCommandPrepareAddAutomezzo(ActionContext context) {
		return "PrepareAddAutomezzoOK";
	}

	public String executeCommandAddOperatoreFuoriRegione(ActionContext context) {

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList Site2 = new LookupList(db, "lookup_site_id");
			Site2.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Site_2", Site2);


			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		return ("AddOperatoreFuoriRegioneOK");

	}

	public String executeCommandSearchNonFissa(ActionContext context) {
		if (!hasPermission(context, "opu-view")) {
			return ("PermissionError");
		}

		StabilimentoList organizationList = new StabilimentoList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
		searchListInfo.setLink("OpuStab.do?command=Search");

		Connection db = null;
		try {
			db = this.getConnection(context);	      

			searchListInfo.setSearchCriteria(organizationList, context);     
			organizationList.setPagedListInfo(searchListInfo);
			organizationList.buildList(db);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);
			context.getRequest().setAttribute("StabilimentiList", organizationList);
			return "ListNonFissaOK";

		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}

	public String executeCommandAddAutomezzo(ActionContext context) {Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	try {

		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer stabid = null;

		//			if (tempOpId != null) {
		//				tempid = Integer.parseInt(tempOpId);
		//			}

		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}

		db = this.getConnection(context);	


		newStabilimento = new Stabilimento(db,  stabid);
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		context.getRequest().setAttribute("StabilimentoDettaglio",	newStabilimento);

		Operatore operatore = new Operatore () ;
		org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
		org.setName(operatore.getRagioneSociale());
		operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
		operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
		context.getRequest().setAttribute("Operatore", operatore);


		return getReturn(context, "AddAutomezzo");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}

	}

	public String executeCommandPrepareUpdateLineePregresse(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {


			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			Integer stabid = null;

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	

			int tipo = 2;
			String commit = context.getRequest().getParameter("commit");
			if(commit!=null && commit.equals("false"))
				tipo =1;
			
			context.getRequest().setAttribute("tipoAttivita", context.getRequest().getParameter("tipoAttivita"));
			context.getRequest().setAttribute("commit", context.getRequest().getParameter("commit"));
			context=org.aspcfs.modules.accounts.actions.Accounts.PrepareUpdateLineePregresse(context,db,""+stabid,tipo);
			
			
			
			return getReturn(context, "PrepareUpdateLineePregresse");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}


	
	
	
	public String executeCommandUpdateLineePregresse(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {


			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			Integer stabid = null;

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	
			
			String commit = context.getRequest().getParameter("commit");
			context.getRequest().setAttribute("commit", commit);
			
			context.getRequest().getSession().setAttribute("context", context);
			
			if(commit==null || commit.equals("") || commit.equals("null"))
				return org.aspcfs.modules.accounts.actions.Accounts.UpdateLineePregresse(context,db,""+stabid,2,getUserId(context));
			else
				return "UpdateLineePregresseOK";
			


		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	
	
	public String executeCommandVerificaEsistenzaStabilimentoInAnagrafica(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {


			String tipoAttivita = context.getParameter("tipoAttivita");
			
			String partitaIva = context.getParameter("partitaIva");
			String comune = context.getParameter("comune");
			String indirizzo = context.getParameter("indirizzo");
			
			String idLineaProduttiva = context.getParameter("idLineaProduttiva");
			
			db = this.getConnection(context);	
			
			RicercaList rList = new RicercaList();
			rList.setPartitaIva(partitaIva);
			rList.setComuneSedeProduttiva(comune);
			rList.setIndirizzoSedeProduttiva(indirizzo);
			rList.buildList(db);
		
			
			
			
			return "UpdateLineePregresseOK";

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}



	public String executeCommandPrepareImport(ActionContext context) {
				if (!hasPermission(context, "opu-import-add")) {
					return ("PermissionError");
				}

		Connection db = null;

		try {
			db = this.getConnection(context);	
			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c
					.buildList(db, user.getSiteId());
			LookupList comuniList = new LookupList(listaComuni);
			comuniList.addItem(-1, "Tutti i comuni");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		return "PrepareImportOK";
	}

	
	
	
	public String executeCommandMappabilitaLinee(ActionContext context)
	{
		String tipoOp = context.getRequest().getParameter("op");
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pst1 = null;
		
	
		try
		{
			db = getConnection(context);
			if(tipoOp.equals("get_tipologie"))
			{
				HashMap<Integer,String> tipologieOp = new HashMap<Integer,String>();
				pst = db.prepareStatement("select * from lookup_tipologia_operatore where code in (select distinct id_tipologia_operatore from lista_linee_attivita_vecchia_anagrafica  )");
				rs = pst.executeQuery();
				while(rs.next())
				{
					int idTipo = rs.getInt(1);
					String descr = rs.getString(2);
					
					tipologieOp.put(idTipo,descr);
				}
				
				context.getRequest().setAttribute("tipologie",tipologieOp);
			}
			else if(tipoOp.equals("get_linee"))
			{
				int idTipologia = Integer.parseInt(context.getRequest().getParameter("tipologia"));
				String stato = context.getRequest().getParameter("stato");
				
				ArrayList<BeanMappingLinea> linee = new ArrayList<BeanMappingLinea>();
				
				if(stato.equals("mappate")) //voglio tutte quelle che sono state mappate col knowledge based ...
				{
					
					pst = db.prepareStatement("select lv.id,lv.macroarea,lv.aggregazione,lv.attivita, kb.id_nuova_linea::integer,kb.macroarea,kb.aggregazione,kb.attivita, kb.ranking "+ 
									"from lista_linee_attivita_vecchia_anagrafica  lv join knowledge_based_mapping kb on lv.id = kb.id_vecchia_linea where "+ ( idTipologia != -1 ? "kb.id_tipologia_operatore = "+idTipologia : "1=1" ));
					
					rs = pst.executeQuery();
					while(rs.next())
					{
						int idLineaV = rs.getInt(1);
						String macroV = rs.getString(2);
						String aggrV = rs.getString(3);
						String attV = rs.getString(4);
						int idLineaN = rs.getInt(5);
						String macroN = rs.getString(6);
						String aggrN = rs.getString(7);
						String attN = rs.getString(8);
						int ranking = rs.getInt(9);
						BeanMappingLinea bm = new BeanMappingLinea(idLineaV,macroV,aggrV,attV,idLineaN,macroN,aggrN,attN,ranking);
						linee.add(bm);
					}
					
					
				}
				else //tutte quelle non mappate
				{
					pst = db.prepareStatement("select * from lista_linee_attivita_vecchia_anagrafica where id not in (select distinct id_vecchia_linea from knowledge_based_mapping) and " + (idTipologia == -1 ? "1=1" : "id_tipologia_operatore="+idTipologia) );
					rs = pst.executeQuery();
					while(rs.next())
					{
						int idLineaV = rs.getInt(1);
						String macroV = rs.getString(4);
						String aggrV = rs.getString(5);
						String attV = rs.getString(6);
						BeanMappingLinea bm = new BeanMappingLinea(idLineaV,macroV,aggrV,attV,-1,"","","",0);
						linee.add(bm);
					}
					 
					
				}
				context.getRequest().setAttribute("linee",linee);
				
			}
			else if(tipoOp.equals("get_stabilimenti"))
			{
				int idLineaV = Integer.parseInt(context.getRequest().getParameter("idLineaV"));
				int idLineaN = Integer.parseInt(context.getRequest().getParameter("idLineaN"));
				ArrayList<BeanMappingGen> toJson = new ArrayList<BeanMappingGen>();
				
				pst = db.prepareStatement("select * from org_importati_to_mapping oi join knowledge_based_mapping kb on oi.id_knowledge_based_mapping = kb.id "
						+ "where kb.id_vecchia_linea = ? and kb.id_nuova_linea::integer = ? limit 20");
				pst.setInt(1,idLineaV);
				pst.setInt(2,idLineaN);
				
				
				
				rs = pst.executeQuery();
				while(rs.next())
				{
					int idStab = rs.getInt(2);
					int orgId = rs.getInt(1);
					pst1 = db.prepareStatement("select distinct on (id_opu_operatore) * from opu_operatori_denormalizzati_view"
							+ " where id_stabilimento = ? and riferimento_org_id = ? ");
					pst1.setInt(1,idStab);
					pst1.setInt(2,orgId);
					
					rs1 = pst1.executeQuery();
					
					ArrayList<BeanMappingGen> orgs = BeanMappingGen.buildList(rs1);
					for(BeanMappingGen el : orgs)
					{
						toJson.add(el);
					}
					
					pst1.close();
					rs1.close();
				}
				
				context.getRequest().setAttribute("orgs", toJson);
			}
			
		context.getRequest().setAttribute("op",tipoOp);
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
			try{pst1.close();}catch(Exception ex){}
			try{rs1.close();}catch(Exception ex){}
			try{freeConnection(context, db);}catch(Exception ex){}
			
		}
		
		
		return "prepareReportJSON";
	}
	
	
	
	
	public String executeCommandCaricaImport(ActionContext context) {

//		if (!hasPermission(context, "opu-import-add")) {
//			return ("PermissionError");
//		}
		Connection db = null;
		try {
			int orgId = -1;

			try {
				orgId= Integer.parseInt(context.getRequest().getParameter("orgId"));
			}
			catch (Exception e){
				
				try {
					orgId= Integer.parseInt((String) context.getRequest().getAttribute("orgId"));
				}
				catch (Exception e2){

				}	
				
				
			}
			db = this.getConnection(context);

			org.aspcfs.modules.accounts.base.Organization orgImport;

			if (orgId==-1)
				orgImport = new org.aspcfs.modules.accounts.base.Organization();
			else
				orgImport = new org.aspcfs.modules.accounts.base.Organization(db, orgId);
			

			if (orgImport.isImportOpu()){
				context.getRequest().setAttribute("Error", "Impossibile proseguire su questo operatore poiche' risulta gia' importato.");
				return executeCommandPrepareImport(context);
			}

			List<Integer> tipiDaNonImportare = Stabilimento.getTipologieDaNonImportare();
			if (tipiDaNonImportare.contains(orgImport.getTipologia())){
				context.getRequest().setAttribute("Error", "Impossibile proseguire su questo operatore poiche' risulta di una tipologia non importabile.");
				return executeCommandPrepareImport(context);
			}



			if (orgImport.getTipologia()==2)
			{
			
			orgImport.sovrascriviProprietario(db);
			orgImport.sovrascriviSedeAzienda(db);
			
			
			}
			
			// Controllo validita' indirizzi
			// Comune nascita
			if (orgImport.getLuogoNascitaRappresentante()!=null)
				orgImport.setLuogoNascitaRappresentante(checkIndirizzo(db,orgImport.getLuogoNascitaRappresentante(),"lookup_comuni"));
			// Comune residenza		
			if (orgImport.getCity_legale_rapp()!=null)
				orgImport.setCity_legale_rapp(checkIndirizzo(db,orgImport.getCity_legale_rapp(),"lookup_comuni"));
			// Provincia residenza		
			if (orgImport.getProv_legale_rapp()!=null)
				orgImport.setProv_legale_rapp(checkIndirizzo(db,orgImport.getProv_legale_rapp(),"lookup_province"));



			Indirizzo ind1 = new Indirizzo();
			ind1.queryRecord(db, orgImport.getCity_legale_rapp(), orgImport.getAddress_legale_rapp());
			if (ind1.getIdIndirizzo()<=0)
			{

				ind1.setIdComuneFromdescrizione(db, orgImport.getCity_legale_rapp());

			}
			
			
			orgImport.setIndirizzoOpuRappresentante(ind1);

			OrganizationAddressList newAddrList = new OrganizationAddressList();

			boolean checkSedeLegale = false ;
			boolean checkSedeOperativa = false ;
			Iterator it = orgImport.getAddressList().iterator();
			while (it.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) it
						.next();
				if (thisAddress.getType() == 5) {
					checkSedeOperativa = true ;
					// Controllo provincia sede operatore
					if (thisAddress.getState()!=null){
						thisAddress.setState(checkIndirizzo(db,thisAddress.getState(),"lookup_province"));
					}else{
						thisAddress.setState("");
					}
					// Controllo comune sede operatore
					if (thisAddress.getCity()!=null){
						thisAddress.setCity(checkIndirizzo(db,thisAddress.getCity(),"lookup_comuni"));
					}else{
						thisAddress.setCity("");
					}

					Indirizzo ind = new Indirizzo();
					ind.queryRecord(db, thisAddress.getCity(), thisAddress.getStreetAddressLine1());
					if (ind.getIdIndirizzo()<=0)
					{

						ind.setIdComuneFromdescrizione(db, thisAddress.getCity());

					}

					thisAddress.setIndirizzoOpu(ind);
					newAddrList.add(thisAddress);
				}else if (thisAddress.getType() == 1) {
					checkSedeLegale=true ;
					// Controllo comune sede operatore
					if (thisAddress.getState()!=null){
						thisAddress.setState(checkIndirizzo(db,thisAddress.getState(),"lookup_province"));
					}else{
						thisAddress.setState("");
					}
					// Controllo comune sede operatore
					if (thisAddress.getCity()!=null){
						thisAddress.setCity(checkIndirizzo(db,thisAddress.getCity(),"lookup_comuni"));
					}else{
						thisAddress.setCity("");
					}

					Indirizzo ind = new Indirizzo();
					ind.queryRecord(db, thisAddress.getCity(), thisAddress.getStreetAddressLine1());
					if (ind.getIdIndirizzo()<=0)
					{

						ind.setIdComuneFromdescrizione(db, thisAddress.getCity());

					}

					thisAddress.setIndirizzoOpu(ind);

					newAddrList.add(thisAddress);
				}
				else if (thisAddress.getType() == 7) {
					checkSedeLegale=true ;
					// Controllo comune sede operatore
					if (thisAddress.getState()!=null){
						thisAddress.setState(checkIndirizzo(db,thisAddress.getState(),"lookup_province"));
					}else{
						thisAddress.setState("");
					}
					// Controllo comune sede operatore
					if (thisAddress.getCity()!=null){
						thisAddress.setCity(checkIndirizzo(db,thisAddress.getCity(),"lookup_comuni"));
					}else{
						thisAddress.setCity("");
					}

					Indirizzo ind = new Indirizzo();
					ind.queryRecord(db, thisAddress.getCity(), thisAddress.getStreetAddressLine1());
					if (ind.getIdIndirizzo()<=0)
					{

						ind.setIdComuneFromdescrizione(db, thisAddress.getCity());

					}

					thisAddress.setIndirizzoOpu(ind);

					newAddrList.add(thisAddress);
				}
				else{
					newAddrList.add(thisAddress);
				}
				//if (thisAddress.getType() == 7) {
				//	sedeMobile = thisAddress;
				//}
			}

			if (checkSedeOperativa==false)
			{
				OrganizationAddress thisAddress = new OrganizationAddress();
				Indirizzo ind = new Indirizzo();
				ind.queryRecord(db, thisAddress.getCity(), thisAddress.getStreetAddressLine1());
				if (ind.getIdIndirizzo()<=0)
				{

					ind.setIdComuneFromdescrizione(db, thisAddress.getCity());

				}
				thisAddress.setType(5);
				thisAddress.setIndirizzoOpu(ind);
				newAddrList.add(thisAddress);
			}
			if (checkSedeLegale==false)
			{
				OrganizationAddress thisAddress = new OrganizationAddress();
				thisAddress.setType(1);
				Indirizzo ind = new Indirizzo();
				ind.queryRecord(db, thisAddress.getCity(), thisAddress.getStreetAddressLine1());
				if (ind.getIdIndirizzo()<=0)
				{

					ind.setIdComuneFromdescrizione(db, thisAddress.getCity());

				}

				thisAddress.setIndirizzoOpu(ind);
				newAddrList.add(thisAddress);
			}

			orgImport.setAddressList(newAddrList);


			context.getRequest().setAttribute("OrgImport", orgImport);


			String cf=orgImport.getCodiceFiscaleRappresentante();
			SoggettoFisico controllo = new SoggettoFisico(cf,db);

			context.getRequest().setAttribute("SoggettoFisico", controllo);


			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			String flagDia = context.getParameter("flagDia");
			if (flagDia==null)
				flagDia = "false" ;




			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaList", TipoImpresaList);


			LookupList TipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			TipoAttivita.addItem(-1, "Seleziona Tipo Attivita");
			TipoAttivita.removeElementByCode(3);
			context.getRequest().setAttribute("TipoAttivita", TipoAttivita);

			LookupList TipoCarattere = new LookupList(db,"opu_lookup_tipologia_carattere");
			TipoCarattere.addItem(-1, "Seleziona Tipo Carattere");
			context.getRequest().setAttribute("TipoCarattere", TipoCarattere);

			LookupList TipoMobili = new LookupList(db,"lookup_tipo_mobili");
			TipoMobili.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoMobili", TipoMobili);


			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaList", TipoSocietaList);

			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

			LookupList ServizioCompetente = new LookupList(db, "lookup_account_stage");
			ServizioCompetente.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", ServizioCompetente);


			LookupList carattere = new LookupList(db, "lookup_contact_source");
			carattere.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("Carattere", carattere);


			Stabilimento newStabilimento = new Stabilimento();


			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");

			listaToponimi.buildList(db);

			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);


			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				if ((Stabilimento) context.getRequest().getAttribute("Stabilimento") != null)
					newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");


			newStabilimento.setIdStabilimento(DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq"));
			newStabilimento.setFlagDia(Boolean.parseBoolean(flagDia));
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			newStabilimento.getPrefissoPageCampiEstesi(context.getAction().getActionName());
			
			if (orgImport!= null && orgImport.getOrgId()>0 && orgImport.getTipologia()>0)
				if(orgImport.getTipologia()==3 || orgImport.getTipologia()==97 || orgImport.getTipologia()==800)
					newStabilimento.setTipoInserimentoScia(Integer.parseInt("2"));//context.getParameter("tipoInserimento")));
			else
				newStabilimento.setTipoInserimentoScia(Integer.parseInt("1"));
			
			int idOperatore = -1;

			if (context.getRequest().getParameter("idOp") != null)
				idOperatore = Integer.parseInt(context.getRequest().getParameter("idOp"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest().getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			context.getRequest().setAttribute("Operatore", operatore);
			newStabilimento.setOperatore(operatore);
			newStabilimento.setStato(Stabilimento.STATO_AUTORIZZATO);
			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);

			//			if(thisUser.getUserRecord().getSuap()!=null)
			//			{
			//
			//				newStabilimento.getSedeOperativa().setInfoComune(thisUser.getUserRecord().getSuap().getComune(),db);
			//			}


			context.getRequest().setAttribute("newStabilimento",newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);
			
			context.getRequest().setAttribute("asl_short_description", (orgImport.getSiteId()!=-1)?(aslList.getElementfromValue(orgImport.getSiteId()).getShort_description()):(""));

			LineaProduttivaList lpList = new LineaProduttivaList();
			//lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute("tipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));





			Provincia provinciaAsl = new Provincia();
			if (thisUser.getUserRecord().getSuap()==null)
				provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			//			else
			//			{
			//				provinciaAsl.getProvincia(db, thisUser.getUserRecord().getSuap().getComune());
			//			}
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			LookupList tipoList = new LookupList(db, "lookup_tipo_scheda_operatore");
			context.getRequest().setAttribute( "tipoList", tipoList);

			if (context.getRequest().getAttribute("newStabilimento")!=null)
				context.getRequest().setAttribute("newStabilimento",context.getRequest().getAttribute("newStabilimento"));
			
			if(context.getRequest().getParameter("pregresso") != null && context.getRequest().getParameter("pregresso").equals("true"))
			{
				context.getRequest().setAttribute("pregresso", "true");
			}

			if (orgId>0){
				ArrayList<LineeAttivita> linee_attivita=  null;

				//			if (orgImport.getTipologia()==97){ //SOA
				//				linea_attivita_principale = LineeAttivita. sottoattivita_principale_soa_per_org_id(String.valueOf(orgId), db);
				//				linee_attivita_secondarie = LineeAttivita.load_sottoattivita_secondaria_soa_per_org_id(String.valueOf(orgId), db);
				//			}
				//			else if (orgImport.getTipologia()==3){ //853
				//				linea_attivita_principale = LineeAttivita.load_sottoattivita_principale_853_per_org_id(String.valueOf(orgId), db);
				//				linee_attivita_secondarie = LineeAttivita.load_sottoattivita_secondaria_853_per_org_id(String.valueOf(orgId), db);
				//			}
				//			else {
				//				linea_attivita_principale = LineeAttivita.load_linea_attivita_principale_per_org_id(String.valueOf(orgId), db);
				//				linee_attivita_secondarie = LineeAttivita.load_linee_attivita_secondarie_per_org_id(String.valueOf(orgId), db);
				//			}

				
//				linee_attivita = LineeAttivita.load_linee_globali_per_org_id(String.valueOf(orgId), db);
				
				//versione knowledge based
				linee_attivita = LineeAttivita.load_tutteLLAVAeORIG_perOrg(orgId+"", db);
				/*if(linee_attivita.size() == 0) //nel caso in cui non trovi linee vecchie usando la rappresentazione in lista_linee_attivita... allora usiamo la vecchia rappresentazione
				{
					linee_attivita = LineeAttivita.load_linee_globali_per_org_id(String.valueOf(orgId), db);
				}*/

				
				ArrayList<LineeAttivita> linee_attivita_ = LineeAttivita
						.load_linee_attivita_per_org_id(orgId+"", db);
				if(orgImport.getTipoDest()!= null && orgImport.getTipoDest().equalsIgnoreCase("Es. Commerciale"))
				{
					if(linee_attivita.size()==1)
					{
						for(LineeAttivita la : linee_attivita_)
						{
							if(la.isCommercioAmbulante())
							{
								orgImport.setTipoDest("Autoveicolo");
								context.getRequest().setAttribute("OrgImport", orgImport);

							}
						}
					}
					
				}
				
				context.getRequest().setAttribute("linee_attivita",linee_attivita);


			}





		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "CaricaImport");
	}

	public String checkIndirizzo(Connection db, String valore, String tabella) {
		try {

			if (tabella.equals("lookup_province")) {
				String query = "select description from " + tabella + " where description = ? or cod_provincia = ?";
				PreparedStatement pst1 = db.prepareStatement(query);
				pst1.setString(1, valore.toUpperCase());
				pst1.setString(2, valore.toUpperCase());
				ResultSet rs = pst1.executeQuery();
				if (rs.next())
					return rs.getString("description");
				else
					return "";
			} else {
				String query = "select description from " + tabella + " where description = ? ";
				PreparedStatement pst1 = db.prepareStatement(query);
				pst1.setString(1, valore.toUpperCase());
				ResultSet rs = pst1.executeQuery();
				if (rs.next())
					return rs.getString("description");
				else
					return "";
			}
		} catch (Exception e) {
			return "";
		}
	}





	private String insertNuovoStabilimento(Connection db,Stabilimento newStabilimento , ActionContext context, Organization OrgImport) 
	{

		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
		boolean isValid,recordInserted=false ;
		boolean isAutocommit = false;
		
		try
		{
		//isAutocommit = db.getAutoCommit();
		
		//db.setAutoCommit(false);
		
		ComuniAnagrafica c = new ComuniAnagrafica();
		ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
		LookupList comuniList = new LookupList();
		comuniList.queryListComuni(listaComuni, -1);
		comuniList.addItem(-1, "");
		context.getRequest().setAttribute("ComuniList", comuniList);

		LookupList provinceList = new LookupList(db, "lookup_province");
		provinceList.addItem(-1, "");

		LookupList LookupTipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
		LookupTipoAttivita.removeElementByValue("3");
		LookupTipoAttivita.removeElementByValue("-1");
		context.getRequest().setAttribute("LookupTipoAttivita", LookupTipoAttivita);

		LookupList nazioniList = new LookupList(db,"lookup_nazioni");

		newStabilimento.setStato(Integer.parseInt(context.getParameter("stato")));
		newStabilimento.setTipoInserimentoScia(Integer.parseInt(context.getParameter("tipoInserimentoScia")));
		
		OperatoreAction actionOperatore = new OperatoreAction();
		String esitoInserimentoOperatore = actionOperatore.executeCommandInsertSuap(context,db);




		Indirizzo indirizzoAdded = null;
		//	RECUPERO INDIRIZZO DELLA SEDE OPERATIVA
		
//			INSERISCO L'INDIRIZZO DELLA SEDE OPERATIVA
			indirizzoAdded = new Indirizzo();
			indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA);
			indirizzoAdded.setLatitudine(context.getRequest().getParameter("latStab"));
			indirizzoAdded.setLongitudine(context.getRequest().getParameter("longStab"));
			indirizzoAdded.setCap(context.getRequest().getParameter("capStab"));
			indirizzoAdded.getSedeOperativaSuap(context.getRequest(), nazioniList, db,context);

		


		indirizzoAdded.setDescrizione_provincia(provinceList.getSelectedValue(indirizzoAdded.getProvincia()));					
		indirizzoAdded.setDescrizioneComune(comuniList.getSelectedValue(indirizzoAdded.getComune()));

		if (indirizzoAdded.getLatitudine()>0 && indirizzoAdded.getLongitudine()>0)
			indirizzoAdded.updateCoordinate(db);
		
	

		newStabilimento.setSedeOperativa(indirizzoAdded);


		// DATI RELATIVI AL CASO DI IMPOER DA VECCHIA ANAGRAFICA : ARRIVA COME PARAMETRO L'ORGID
		String importOp = context.getRequest().getParameter("orgId");
		boolean importato = false;
		boolean pregresso = false;
		
		if (importOp!=null && !"".equals(importOp)  && Integer.parseInt(importOp)>0)
			importato = true;
		newStabilimento.setImportato(importato);
		
		if(context.getRequest().getParameter("pregresso") != null && context.getRequest().getParameter("pregresso").equals( "true"))
		{
			pregresso = true;
		}

		int tipologiaOperatore = -1 ;
		if(importato){
			OrgImport = new Organization (db, Integer.parseInt(importOp));
			tipologiaOperatore = OrgImport.getTipologia();
			newStabilimento.setOrgId(importOp);
			newStabilimento.setTipoVecchiaOrg(OrgImport.getTipologia());
			
			if (context.getRequest().getParameter("categoria_rischio")!= null && !"".equals(context.getRequest().getParameter("categoria_rischio")))
				newStabilimento.setCategoriaRischio(Integer.parseInt(context.getRequest().getParameter("categoria_rischio")));

			

			if (!"".equals(context.getRequest().getParameter("prossimo_controllo")))

				newStabilimento.setDataProssimoControllo(DateUtils.parseDateStringNew(context.getRequest().getParameter("prossimo_controllo"),"dd/MM/yyyy"));
		}

		String numero_registrazione="";
		String codiceNazionale="";
		if (importOp!=null && !"".equals(importOp)  && Integer.parseInt(importOp)>0)
		{
			numero_registrazione = org.aspcfs.modules.accounts.base.Organization.get_numero_registrazione(db,Integer.parseInt(importOp));
		
			numero_registrazione = numero_registrazione == null ? "" : numero_registrazione;
			
			if (tipologiaOperatore!=1)
			{
				codiceNazionale=numero_registrazione;
			}
		}
		
		
	


		if ( !numero_registrazione.equals(""))
			newStabilimento.setCodice_ufficiale_esistente(numero_registrazione);
		
		if (!codiceNazionale.equals(""))
		{
			for(int i = 0 ; i < newStabilimento.getListaLineeProduttive().size(); i++)
			{
				((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(i)).setCodice(codiceNazionale);
			}
		}
		
		
		

		newStabilimento.setNote(context.getRequest().getParameter("noteStab"));
		newStabilimento.setDataFineAttivita(context.getRequest().getParameter("dataFineAttivita"));
		newStabilimento.setEnteredBy(super.getUserId(context));
		newStabilimento.setModifiedBy(super.getUserId(context));
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		



		if(context.getRequest().getParameter("dataInizioAttivita")!=null && !((String)context.getRequest().getParameter("dataInizioAttivita")).equals(""))
			newStabilimento.setDataInizioAttivita(context.getRequest().getParameter("dataInizioAttivita"));
		else
			newStabilimento.setDataInizioAttivita(new Timestamp(System.currentTimeMillis()));

		if ("1".equals(context.getRequest().getParameter("tipoCarattere"))){
			//newStabilimento.setDataInizioAttivita(new Timestamp(System.currentTimeMillis()));
			newStabilimento.setDataFineAttivita("");
		}

	

		if(!importato){
			
				newStabilimento.setCategoriaRischio(2);
			
		}

		for (int i = 0; i < newStabilimento.getListaLineeProduttive().size(); i++) {
			newStabilimento.getListaLineeProduttive().remove(i);
		}
 
		
		
		//se non riceviamo il parametro rankModeOn con la richiesta, allora il rank non è stato proprio implementato clientside
		/*boolean rankModeON = false; 
		rankModeON = context.getRequest().getParameter("rankModeON") != null ;
		
		
		//controllo se esiste almeno una linea scelta usando il candidato rank
		//NB: MODIFICA---> L'ID CHE VA EFFETTIVAMENTE REGISTRATO COME ID LINEA NUOVA NEL KNOWLEDGE BASED MAPPING E' QUELLO DEL TERZO LIVELLO
		//(POICHE' POTREBBE ESSERE ARRIVATO UN ID LINEA RELATIVO A LIVELLI SUCCESSIVI)
		Map<String,String[]> tuttiParametriRequest = context.getRequest().getParameterMap();
		HashMap<Integer,Integer> mapLineaLLAVA_LineaML = new HashMap<Integer,Integer>();  
		//me li metto da parte
		if(importato && rankModeON)
		{
			for(String nomePar : tuttiParametriRequest.keySet())
			{
				if(nomePar.contains("candidato-"))
				{
					String[] t = nomePar.split("-");
					Integer idLineaLLAVA = Integer.parseInt(t[1]); //id linea vecchia   in lista linee attivita vecchia anagrafica
					String valoreParametro = context.getRequest().getParameter(nomePar); //il valore e' l'id della nuova linea associata
					Integer idLineaML = Integer.parseInt(valoreParametro);
					mapLineaLLAVA_LineaML.put(idLineaLLAVA, idLineaML);
				}
			}
		}*/
		
 
		boolean esitoInserimentoStab = esitoInserimentoOperatore.equals(Stabilimento.OPERAZIONE_INSERIMENTO_OK);
		int idAttPrincipale = -1; //quella considerata la principale, è quella che arriva per prima nella lista
	 
		
		/*ArrayList<LineeAttivita> lineeLLAVAeORIG_perOrg = new ArrayList<LineeAttivita>(); 
		if(importato)
		{
			lineeLLAVAeORIG_perOrg = LineeAttivita.load_tutteLLAVAeORIG_perOrg(String.valueOf(importOp), db);
		}*/
		
		//lista linee vecchia anagrafica, allora quelli che ci arrivano come id linea vecchia, sono quelli non della rappresentazione di lista_linee vecchia anagrafica (ma id della precedente gestione)
		//quindi non ci interessano per  aggiornamento della tabella knowledge based mapping del rank
		
		//gestisco solo quelli importati in maniera classica (o allo stesso modo se non sono importati)
		if (context.getRequest().getParameterValues("idLineaProduttiva") != null && context.getRequest().getParameterValues("idLineaProduttiva").length > 0) {

			String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");



			for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
				if (!lineeProduttiveSelezionate[i].equals("")) {
 
					if(i==0)
					{
						idAttPrincipale = new Integer( lineeProduttiveSelezionate[i] ).intValue();
					}
					
					/*if(importato && rankModeON && lineeProduttiveSelezionate[i].equals("-1")) //...saltando quelli per i quali non e' fornito id (quindi saranno gestiti con candidato)
					{
						//questa e' una per la quale e' stato preferito un candidato, salto
						//perche' aggiungo alla fine tutte quelle per le quali sono stati scelti candidati
						continue;
					}*/
					
					
 
					LineaProduttiva  lp = new LineaProduttiva();
					if (!numero_registrazione.equals(""))
						lp.setCodice_ufficiale_esistente(numero_registrazione);
					
					if (!codiceNazionale.equals("") && importato==true)
						lp.setCodice(codiceNazionale);
					else
					{
//						if(!importato)
//						{
//							lp.setCodice(context.getParameter("codice_nazionale_"+lineeProduttiveSelezionate[i]));
//						}
						lp.setCodice(context.getParameter("codice_nazionale_"+i));
					}

					if(importato){

						lp.setIdTipoVecchiaOrg(OrgImport.getTipologia());

						if (!"".equals(context.getRequest().getParameter("categoria_rischio")))

							lp.setCategoriaRischio(Integer.parseInt(context.getRequest().getParameter("categoria_rischio")));
						if (context.getRequest().getParameter("prossimo_controllo")!= null && !"".equals(context.getRequest().getParameter("prossimo_controllo")))
							lp.setDataProssimoControllo(DateUtils.parseDateStringNew(context.getRequest().getParameter("prossimo_controllo"),"dd/MM/yyyy"));
					}else{
						lp.setCategoriaRischio(newStabilimento.getCategoriaRischio());
					}

					lp.setDataInizio(context.getRequest().getParameter("dataInizioLinea"+((i==0) ? "":""+i)));
					lp.setDataFine(context.getRequest().getParameter("dataFineLinea"+((i==0) ? "":""+i)));
					lp.setIdRelazioneAttivita(lineeProduttiveSelezionate[i]);
					lp.setIdVecchiaLinea(context.getRequest().getParameter("_b_idLineaVecchia"+((i==0) ? "":""+i))); /*NB QUI GLI ARRIVA L'ID LINEA VECCHIA ORIGINALE, NON QUELLA DI LISTA_LINEE_ATTIVITA_VECCHIA_ANAGRAFICA */
					//					if (context.getParameter("principale")!=null && Integer.parseInt(lineeProduttiveSelezionate[i])== (Integer.parseInt(context.getParameter("principale"))))
					
					if (idAttPrincipale == new Integer( lineeProduttiveSelezionate[i] ).intValue())
						lp.setPrincipale(true);
					
					
					
					
					//per ogni linea produttiva, devo ottenere tutti i campi estesi (se ne esistono).
					//Questi arrivano nel multiPart del form come nome_campoIDTIPOLINEA
					//quindi per prenderli faccio query per vedere quali sono previsti per quell'idtipolinea 
					//faccio la concatenazione e li estraggo 
					
					HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>> valoriCampiEstesi =
							costruisciValoriCampiEstesi(context,db,lp.getIdRelazioneAttivita());
					
					
					lp.addCampiEstesi(valoriCampiEstesi);
					

					newStabilimento.getListaLineeProduttive().add(lp);
					
				}
			}
			
			
			
		}

		
		
		//Gestisco campi aggiuntivi per att.mobili
		if (newStabilimento.getTipoAttivita()==2)
			gestisciCampiMobile(context, newStabilimento, db);



		if (esitoInserimentoOperatore.equals(Stabilimento.OPERAZIONE_INSERIMENTO_OK))
		{
			if(context.getRequest().getAttribute("Operatore")!=null)
			{
				Operatore newOperatore = (Operatore)context.getRequest().getAttribute("Operatore");
				newStabilimento.setOperatore(newOperatore);
				newStabilimento.setIdOperatore(newOperatore.getIdOperatore());

				Object[] asl = null;
				if (!newStabilimento.isFlagFuoriRegione()) 
				{
					if (newStabilimento.getTipoAttivita()==1 )
						asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
					else
						if (newStabilimento.getTipoAttivita()==2  || newStabilimento.getTipoAttivita()==3 || newStabilimento.getTipoAttivita() <=0)
						{
							if (newOperatore.getTipo_impresa()!=1)
								asl = DwrUtil.getValoriAsl(newOperatore.getSedeLegale().getComune());
							else
								asl = DwrUtil.getValoriAsl(newOperatore.getRappLegale().getIndirizzo().getComune());

						}
				}
				else
					asl = null;

				if (asl != null && asl.length > 0) {

					Object[] aslVal = (Object[]) asl[0];
					if (aslVal != null && aslVal.length > 0)
						newStabilimento.setIdAsl((Integer) aslVal[0]);

				} else {
					newStabilimento.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
				}



			}
			String  operazione = context.getParameter("methodRequest");
			
			String esitoStabilimento = newStabilimento.checkEsistenzaStabilimentoSuap(db,esitoInserimentoOperatore,importato);
			
			
			
			
			if (!esitoStabilimento.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_STABILIMENTO_ESISTENTE) && !esitoStabilimento.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE_ORGANIZATION) )
			{
			
					isValid = this.validateObject(context, db, newStabilimento);
					if (isValid){
						
						if(importato && newStabilimento.getTipoAttivita()==2)
						{
							newStabilimento.setTargaImportata(OrgImport.getNomeCorrentista());
							newStabilimento.setIdTipoVeicolo(OrgImport.getTipoVeicoloOpu());
							
						}
						
						
						recordInserted = newStabilimento.insert(db, true,context);
						
						
						
					
						
						if (importato)
						{
							StabilimentoImportUtil.importaOperatoriMercatoIttico(db, OrgImport.getOrgId(), newStabilimento.getIdStabilimento());

							StabilimentoImportUtil.cancellaOrganization(db, Integer.parseInt(importOp));
							newStabilimento.aggiornaCategoriaRischio(db);
							
							
							if (OrgImport.getTipologia()==3){
								Stabilimento stabNew = new Stabilimento (db, newStabilimento.getIdStabilimento()); 
								StabilimentoImportUtil.importaMacellazioni(db, OrgImport, stabNew);
							}
							
							if(OrgImport.getOrgIdCanina()>0)
							{
							GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
							gBdu.aggiornaIdStabilimentoGisaBdu(newStabilimento.getIdStabilimento(), OrgImport.getOrgIdCanina());
							}
							
							
							
							if(newStabilimento.getTipoAttivita()==2)
							{
								/*Per Altri Org Id*/
								String[] altriOrgId = context.getRequest().getParameterValues("altriOrgId");
								
								HashMap<String, String> listaOrgId = new HashMap<String,String>();
								if(altriOrgId!=null)
								for(String orgIdImportato : altriOrgId)
								{
									String[] valori = orgIdImportato.split(";");
									if(Integer.parseInt(valori[2])!=OrgImport.getOrgId() && !listaOrgId.containsKey(valori[2]))
									{
										listaOrgId.put(valori[2], valori[2]);
										newStabilimento.importaTarga(db, valori[0], valori[1], newStabilimento.getIdRelStabLpMobile());
										newStabilimento.importaControlliUfficialionTarga(db, Integer.parseInt(valori[2]), newStabilimento.getIdRelStabLpMobile(), true,valori[0], "",user.getUserId());
										Organization org = new Organization(db, Integer.parseInt(valori[2]));
									
									
										if(org.getAccountNumber()!=null && !"".equals(org.getAccountNumber()))
										{
										PreparedStatement pst = db.prepareStatement("update opu_relazione_Stabilimento_linee_produttive set codice_ufficiale_esistente = codice_ufficiale_esistente||';<br>'|| ? where id=? ");
										pst.setString(1, org.getAccountNumber());
										pst.setInt(2, newStabilimento.getIdRelStabLpMobile());
										pst.execute();
										}
										StabilimentoImportUtil.cancellaOrganization(db, Integer.parseInt(valori[2]));
									}
								}
							}
							
							
						}
						newStabilimento.generaNumeroRegistrazione(context, db);
						RicercheAnagraficheTab.inserOpu(db, newStabilimento.getIdStabilimento());
						
						String sqlUpdate = "update opu_operatore set flag_clean = true where id in (select id_opu_operatore from anagrafica_stabilimenti_puliti where partita_iva =?);";
						PreparedStatement pst = db.prepareStatement(sqlUpdate);
						pst.setString(1, newStabilimento.getOperatore().getPartitaIva());
						pst.execute();
						

						String[] infoUtente = {"-1", "", ""};
						infoUtente[0] =  String.valueOf(super.getUserId(context));
						infoUtente[1] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleRichiedente();
						infoUtente[2] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleDelegato();
						gestioneStorico(newStabilimento, Storico.REGISTRAZIONE_NON_DISPONIBILE , infoUtente, "", db);

						/*lancio dbi che si porta dietro ex code l30 solo se siamo in import stabilimento o importa pregresso */
						if(importato)
						{
							StabilimentoImportUtil.completaDati(db,Integer.parseInt(importOp),newStabilimento.getListaLineeProduttive());
						}
						else if(pregresso)
						{
							StabilimentoImportUtil.completaDati(db, -1 ,newStabilimento.getListaLineeProduttive());
						}
						
						
							
						
						newStabilimento.setErroreSuap("Inserimento Avvenuto con Successo");
						newStabilimento.setCodiceErroreSuap("0&"+newStabilimento.getIdStabilimento());
						context.getRequest().setAttribute("Stabilimento", newStabilimento);
						//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FACCIO COMMIT TTTTTTT------------------------");
						//db.commit();
						//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FATTO  COMMIT TTTTTTT------------------------");
						return "JsonSuapStabilimentoOpu";

					}
					else
					{

						newStabilimento.setErroreSuap("Attenzione Controllare di aver Inserito tutti i Campi");
						newStabilimento.setCodiceErroreSuap("1");
						context.getRequest().setAttribute("Stabilimento", newStabilimento);
						//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FACCIO COMMIT TTTTTTT------------------------");
						//db.commit();
						//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FATTO  COMMIT TTTTTTT------------------------");
						return "JsonSuapStabilimentoOpu";
					}
				

			}
			else
			{


				/**
				 * LO STABILIMENTO ESISTE NEL SISTEMA
				 */
				if(esitoStabilimento.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_STABILIMENTO_ESISTENTE))
				{
					
					if(newStabilimento.getTipoAttivita()==2 && OrgImport!=null && OrgImport.getTipoDest()!=null && OrgImport.getTipoDest().equalsIgnoreCase("autoveicolo") && newStabilimento.getIdStabilimento()>0)
					{ //qui deve entrare
						newStabilimento.setErroreSuap("MESSAGGIO NUOVO?");
						newStabilimento.setCodiceErroreSuap("1");
						context.getRequest().setAttribute("Stabilimento", newStabilimento);
						
						if (importato)
						{
							
							StabilimentoImportUtil.importaOperatoriMercatoIttico(db, OrgImport.getOrgId(), newStabilimento.getIdStabilimento());
							StabilimentoImportUtil.cancellaOrganization(db, Integer.parseInt(importOp));
							newStabilimento.aggiornaCategoriaRischio(db);
							
							int idVecchiaLinea =  ((LineaProduttiva )newStabilimento.getListaLineeProduttive().get(0)).getIdVecchiaLinea();
							int nuovaLinea = -1 ;
							
							
							
							/*Per Organization Imortato*/
							newStabilimento.importaTarga(db, OrgImport.getNomeCorrentista(), OrgImport.getTipoVeicoloOpu()+"", newStabilimento.getIdRelStabLpMobile());
							newStabilimento.importaControlliUfficialionTarga(db, idVecchiaLinea, newStabilimento.getIdRelStabLpMobile(), true,OrgImport.getNomeCorrentista(), "",user.getUserId());
							
							
							
							/*Per Altri Org Id*/
							String[] altriOrgId = context.getRequest().getParameterValues("altriOrgId");
							String altriNumReg = "" ;
							HashMap<String, String> listaOrgId = new HashMap<String,String>();

							if(altriOrgId!=null)
							if(altriOrgId!=null)
							for(String orgIdImportato : altriOrgId)
							{
								String[] valori = orgIdImportato.split(";");
								if(Integer.parseInt(valori[2])!=OrgImport.getOrgId() && !listaOrgId.containsKey(valori[2]))
								{
									listaOrgId.put(valori[2], valori[2]);
									newStabilimento.importaTarga(db, valori[0], valori[1], newStabilimento.getIdRelStabLpMobile());
									newStabilimento.importaControlliUfficialionTarga(db, Integer.parseInt(valori[2]), newStabilimento.getIdRelStabLpMobile(), true,valori[0], "",user.getUserId());
									Organization org = new Organization(db, Integer.parseInt(valori[2]));
								
									if(org.getAccountNumber()!=null && !"".equals(org.getAccountNumber()))
									{
									PreparedStatement pst = db.prepareStatement("update opu_relazione_Stabilimento_linee_produttive set codice_ufficiale_esistente = codice_ufficiale_esistente||';<br>'|| ? where id=? ");
									pst.setString(1, org.getAccountNumber());
									pst.setInt(2, newStabilimento.getIdRelStabLpMobile());
									pst.execute();
									}
									StabilimentoImportUtil.cancellaOrganization(db, Integer.parseInt(valori[2]));
								}
							}
							
							RicercheAnagraficheTab.inserOpu(db, newStabilimento.getIdStabilimento());
							
						}
						
					}
					else
					{
						
						if(newStabilimento.getTipoAttivita()==1 && ((OrgImport!=null && OrgImport.getTipoDest()!=null && !OrgImport.getTipoDest().equalsIgnoreCase("autoveicolo")) || OrgImport==null )  && newStabilimento.getIdStabilimento()>0)
						{
							boolean esitoConvergenza = true ; 
							newStabilimento.setErroreSuap("Inserimento Avvenuto con Successo");
							newStabilimento.setCodiceErroreSuap("1");
							context.getRequest().setAttribute("Stabilimento", newStabilimento);
							
							
							String msgWarning = "Attenzione ! Le seguenti Attivita esistono in Anagrafica Stabilimenti : \n\n";
							String endMsgWarning = "La funzione aggiungi pregresso serveper inserire attivita non presenti nel sistema antecedenti il DL318/11";
							String msgLineeWarning ="";
							for(LineaProduttiva lp : (Vector<LineaProduttiva>) newStabilimento.getListaLineeProduttive())
							{
								
							String selVerifica = "select l.path_descrizione,* from opu_relazione_stabilimento_linee_produttive r "
									+ "join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita=r.id_linea_produttiva where r.id_stabilimento = ? and  r.id_linea_produttiva =? and r.enabled";
							PreparedStatement pst1 = db.prepareStatement(selVerifica);
							
							pst1.setInt(1, newStabilimento.getIdStabilimento()) ;
							pst1.setInt(2, lp.getIdRelazioneAttivita()); 
							ResultSet rs1 = pst1.executeQuery();
							
							
							if(!rs1.next() ||  importato==true)
							{
								int idVecchiaLinea =  lp.getIdVecchiaLinea();
								int nuovaLinea = -1 ;
								newStabilimento.aggiungiLineaProduttiva(db, lp,user.getUserId());
							/*Controllo Se Esiste gia la linea. Se gia Esiste non devo fare niente*/
							
							}
							else
							{
								if(!importato)
								{
									esitoConvergenza = false;
									
									msgLineeWarning+="-"+rs1.getString("path_descrizione")+"\n";
									newStabilimento.setCodiceErroreSuap("5");
								
								}
							}
							

							}
							
							
							
							if (importato)
							{
								StabilimentoImportUtil.importaOperatoriMercatoIttico(db, OrgImport.getOrgId(), newStabilimento.getIdStabilimento());
								StabilimentoImportUtil.cancellaOrganization(db, Integer.parseInt(importOp));
								newStabilimento.aggiornaCategoriaRischio(db);
								
							}
							RicercheAnagraficheTab.inserOpu(db, newStabilimento.getIdStabilimento());
							
							if(newStabilimento.getCodiceErroreSuap().equals("5")){
								newStabilimento.setErroreSuap(msgWarning+msgLineeWarning+"\n\n"+endMsgWarning);
								newStabilimento.setCodiceErroreSuap("1");
							}
							
							
						}
						else
						{
							isValid = this.validateObject(context, db, newStabilimento);
							//qui bisogna agire
							Integer idStabilimentoEsistente = newStabilimento.getIdEsistenteStabilimentoSuap(db, importato);		
							Stabilimento stabNew = new Stabilimento (db, idStabilimentoEsistente); 
							
							
							if (isValid && idStabilimentoEsistente<0) 
							{
								if(importato && newStabilimento.getTipoAttivita()==2)
								{
									stabNew.setTargaImportata(OrgImport.getNomeCorrentista());
									stabNew.setIdTipoVeicolo(OrgImport.getTipoVeicoloOpu());
								}
								
								LineaProduttivaList listaLineeProduttive = newStabilimento.getListaLineeProduttive();
								Iterator<LineaProduttiva> itLp= listaLineeProduttive.iterator();
								while(itLp.hasNext())
								{
									LineaProduttiva temp = itLp.next();
									temp.generaNumeroProtocollo(db, stabNew.getNumProtocollo());
									
									stabNew.aggiungiLineaProduttiva(db, temp,user.getUserId());
								}
								
								if (importato)
								{
									StabilimentoImportUtil.importaOperatoriMercatoIttico(db, OrgImport.getOrgId(), idStabilimentoEsistente);

									StabilimentoImportUtil.cancellaOrganization(db, Integer.parseInt(importOp));
									stabNew.aggiornaCategoriaRischio(db);
									
									if (OrgImport.getTipologia()==3)
									{
										StabilimentoImportUtil.importaMacellazioni(db, OrgImport, stabNew);
									}
									
									if(OrgImport.getOrgIdCanina()>0)
									{
										GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
										gBdu.aggiornaIdStabilimentoGisaBdu(idStabilimentoEsistente, OrgImport.getOrgIdCanina());
									}
								}
								
								RicercheAnagraficheTab.inserOpu(db, stabNew.getIdStabilimento());
								
								String sqlUpdate = "update opu_operatore set flag_clean = true where id in (select id_opu_operatore from anagrafica_stabilimenti_puliti where partita_iva =?);";
								PreparedStatement pst = db.prepareStatement(sqlUpdate);
								pst.setString(1, stabNew.getOperatore().getPartitaIva());
								pst.execute();
								

								String[] infoUtente = {"-1", "", ""};
								infoUtente[0] =  String.valueOf(super.getUserId(context));
								infoUtente[1] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleRichiedente();
								infoUtente[2] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleDelegato();
								gestioneStorico(newStabilimento, Storico.REGISTRAZIONE_NON_DISPONIBILE , infoUtente, "", db);

								/*lancio dbi che si porta dietro ex code l30 solo se siamo in import stabilimento o importa pregresso */
								if(importato)
								{
									StabilimentoImportUtil.completaDati(db,Integer.parseInt(importOp),stabNew.getListaLineeProduttive());
								}
								else if(pregresso)
								{
									StabilimentoImportUtil.completaDati(db, -1 ,stabNew.getListaLineeProduttive());
								}
								
								
									
								
								newStabilimento.setErroreSuap("Inserimento Avvenuto con Successo");
								newStabilimento.setCodiceErroreSuap("0&"+newStabilimento.getIdStabilimento());
								context.getRequest().setAttribute("Stabilimento", newStabilimento);
								//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FACCIO COMMIT TTTTTTT------------------------");
								//db.commit();
								//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FATTO  COMMIT TTTTTTT------------------------");
								return "JsonSuapStabilimentoOpu";

							}
							/*else
							{

								//messaggio da restituire??	
								//newStabilimento.setErroreSuap("Attenzione Controllare di aver Inserito tutti i Campi...sto qui!!!");
								
								newStabilimento.setErroreSuap("Attenzione Esiste una impresa con la partita iva inserita nella nuova anagrafica");
								newStabilimento.setCodiceErroreSuap("1");
								context.getRequest().setAttribute("Stabilimento", newStabilimento);
								//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FACCIO COMMIT TTTTTTT------------------------");
								//db.commit();
								//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FATTO  COMMIT TTTTTTT------------------------");
								return "JsonSuapStabilimentoOpu";
							}*/
							
						}
						
					newStabilimento.setErroreSuap("Codice errore 42: Errore nell'import. Rivolgersi all'Help Desk di I Livello.");
					newStabilimento.setCodiceErroreSuap("42");
					context.getRequest().setAttribute("Stabilimento", newStabilimento);
				
					}
					
					//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FACCIO COMMIT TTTTTTT------------------------");
					//db.commit();
					//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FATTO  COMMIT TTTTTTT------------------------");
				return "JsonSuapStabilimentoOpu";
				}
				
					else
					{
						if(esitoStabilimento.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE_ORGANIZATION))
						{
							//						newStabilimento.setListaOperatori(listaOperatori);
							newStabilimento.setCodiceErroreSuap("1");
							newStabilimento.setErroreSuap("Attenzione Esiste una impresa con la partita iva inserita presente negli altri cavalieri. Esegure un Import i nuova Anagrafica");
							context.getRequest().setAttribute("Stabilimento", newStabilimento);
							
							//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FACCIO COMMIT TTTTTTT------------------------");
							//db.commit();
							//System.out.println("STAMPA>>>>>>>>>>>>>>>>>>>> FATTO  COMMIT TTTTTTT------------------------");
							return "JsonSuapStabilimentoOpu";

						}




				}

			}

		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			newStabilimento.setCodiceErroreSuap("2");
			newStabilimento.setErroreSuap("Errore : "+e.getMessage());
			context.getRequest().setAttribute("Stabilimento", newStabilimento);
			
			try {//db.rollback(); 
				System.out.println("ROLLED BACK ");
			} catch(Exception ex){ ex.printStackTrace(); }
			
			return "JsonSuapStabilimentoOpu";
		}
		finally
		{
			try { /*db.setAutoCommit(isAutocommit);*/ } catch(Exception ex) {ex.printStackTrace();}
		}
		
		newStabilimento.setCodiceErroreSuap("2");
		newStabilimento.setErroreSuap("Attenzione Esiste una impresa con la partita iva inserita con dati diversi da quelli indicati");

		
		context.getRequest().setAttribute("Stabilimento", newStabilimento);
		return "JsonSuapStabilimentoOpu";
		
		
		
	}








	
	
	

	public static HashMap<Integer, ArrayList<LineaProduttivaCampoEsteso>> costruisciValoriCampiEstesi(ActionContext context,Connection db, int idRelazioneAttivita) throws SQLException {
		 
		
		PreparedStatement pst1 = db.prepareStatement("select * from linee_mobili_html_fields where id_linea = ? and enabled ");
		pst1.setInt(1,idRelazioneAttivita);
		ResultSet rs1 = pst1.executeQuery();
		HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>> valoriCampiEstesi = new HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso> >(); //la chiave e il nome del dom, il valore e un array dove la prima stringa e il valore, la seconda l'id su html fields
		while(rs1.next())
		{
			int idHtmlField = rs1.getInt("id");
			
			
			LineaProduttivaCampoEsteso  campoEsteso = new LineaProduttivaCampoEsteso();
			campoEsteso.setIdFieldHtml(idHtmlField);
			campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
			campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
			campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
			
			try
			{
				System.out.println("-----------------------");
				System.out.println(campoEsteso.getNomeCampo()+","+campoEsteso.getValore());
			}
			catch(Exception ex)
			{
				
			}
			
			String nomeCampoEstesoNellaForm = rs1.getString("nome_campo")+idRelazioneAttivita;
			//lo prendo dal multipart
			String[] values = context.getRequest().getParameterValues(nomeCampoEstesoNellaForm);
			
			if(values == null && rs1.getString("tipo_campo") != null && rs1.getString("tipo_campo").equalsIgnoreCase("checkbox") )
			{ //nb: un checkbox non checked non arriva proprio nel form !
				
				if (valoriCampiEstesi.get(idHtmlField)!=null)
				{
					campoEsteso.setValore("false");
					ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
					listaValori.add(campoEsteso);
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
//					valoriCampiEstesi.put(idHtmlField,new String[]{"false",idHtmlField+""}); //perche in tal caso e un checkbox che dovrebbe esserci, ma non essendo arrivato vuol dire che non era checked
				}
				else
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = new ArrayList<LineaProduttivaCampoEsteso>();
					campoEsteso.setValore("false");
					listaValori.add(campoEsteso);
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
					
				
				}
			else if(values != null)
			{
				if (valoriCampiEstesi.get(idHtmlField)!=null)
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
					for (int j = 0 ; j <values.length;j++)
					{
						if (campoEsteso.getNomeTabella()!=null && !"".equals(campoEsteso.getNomeTabella()))
						{
							LookupList lookup = new LookupList(db,campoEsteso.getNomeTabella());
							campoEsteso.setValore(lookup.getSelectedValue(Integer.parseInt(values[j])));
							
						}
						else
						{
						campoEsteso.setValore(values[j]);
						}
						listaValori.add(campoEsteso);
						
					}
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				else
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = new ArrayList<LineaProduttivaCampoEsteso>();
					for (int j = 0 ; j <values.length;j++)
					{
						if (campoEsteso.getNomeTabella()!=null && !"".equals(campoEsteso.getNomeTabella()))
						{
							
							 campoEsteso = new LineaProduttivaCampoEsteso();
								campoEsteso.setIdFieldHtml(idHtmlField);
								campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
								campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
								campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
							LookupList lookup = new LookupList(db,campoEsteso.getNomeTabella());
							campoEsteso.setValore(lookup.getSelectedValue(Integer.parseInt(values[j])));
							
						}
						else
						{
						campoEsteso.setValore(values[j]);
						}
						listaValori.add(campoEsteso);
						
					}
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				
			}
			else
			{
				
				if (valoriCampiEstesi.get(idHtmlField)!=null)
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
					 campoEsteso = new LineaProduttivaCampoEsteso();
						campoEsteso.setIdFieldHtml(idHtmlField);
						campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
						campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
						campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
						campoEsteso.setValore("");
						listaValori.add(campoEsteso);
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				else
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = new ArrayList<LineaProduttivaCampoEsteso>();
					
						
							
							 campoEsteso = new LineaProduttivaCampoEsteso();
								campoEsteso.setIdFieldHtml(idHtmlField);
								campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
								campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
								campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
								
							campoEsteso.setValore("");
					
						listaValori.add(campoEsteso);
					
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				
			}
			
			
		}
		
		return valoriCampiEstesi;
		
	}
	
	
	
	

	/*public String executeCommandSearchImport(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}


		UserBean user = (UserBean) context.getSession().getAttribute("User");
		OrganizationListView organizationList = new OrganizationListView();

		organizationList.setIgnoraImportOpu(true);

		String source = (String) context.getRequest().getParameter("source");
		String tipoRicerca = "op";
		String asl = (String) context.getRequest().getParameter("searchcodeOrgSiteId");
		String comune = (String) context.getRequest().getParameter("searchAccountCity");
		String name = (String) context.getRequest().getParameter("searchAccountName");
		String iva = (String) context.getRequest().getParameter("searchPartitaIva");
		String cf = (String) context.getRequest().getParameter("searchCodiceFiscale");

		if( asl != null )
			organizationList.setOrgSiteId(asl);

		if (comune != null && !comune.trim().equals(""))
			organizationList.setAccountCity(comune); 

		if(name != null && !name.equals(""))
			organizationList.setAccountName(name);

		if(cf != null && !cf.equals(""))
			organizationList.setCodiceFiscale(cf);

		if(iva != null && !iva.equals(""))
			organizationList.setPartitaIva(iva);

		//Anche il filtro sulla tipologia della ricerca deve essere gestito
		if(tipoRicerca!=null){
			organizationList.setTipoRicerca(tipoRicerca);
		}


		addModuleBean(context, "View Accounts", "Search Results");

		this.deletePagedListInfo(context, "SearchOrgListInfo");
		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,"SearchOrgListInfo");
		searchListInfo.setLink("Accounts.do?command=Search");
		searchListInfo.setListView("all");

		SystemStatus systemStatus = this.getSystemStatus(context);
		//Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);

		Connection db = null;
		try {

			db = this.getConnection(context); 


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
			LookupList comuniList = new LookupList(listaComuni);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			//Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
					searchListInfo.setLink("GlobalSearch.do?command=Search");
				}
				organizationList.setPagedListInfo(searchListInfo);
				organizationList.setMinerOnly(false);
				searchListInfo.setSearchCriteria(organizationList, context);
				if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (organizationList.getOrgSiteId() == -1) {
					organizationList.setIncludeOrganizationWithoutSite(true);
				}
				//fetching criterea for account status (active, disabled or any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				//organizationList.setCessato(enabled);
				organizationList.setIncludeEnabled(enabled);


				organizationList.buildListView(db,tipoRicerca, false);
				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType", "accounts");

			} 
		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ListImportOK";
	}*/
	private void resetPagedListInfo(ActionContext context) {

		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
	}


		private void gestisciCampiMobile(ActionContext context, Stabilimento newStabilimento, Connection db) {
			for (int i=0; i<10;i++){
				DatiMobile dato = new DatiMobile(context, i);
				dato.setIdStabilimento(newStabilimento.getIdStabilimento());
				try {
					dato.insert(db);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


public String executeCommandScelta(ActionContext context){

	Connection db = null;
	try {
		db = this.getConnection(context);

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		String fissa = context.getRequest().getParameter("fissa");			
		context.getRequest().setAttribute("fissa", fissa);		


		Stabilimento newStabilimento = new Stabilimento();
		newStabilimento.setIdStabilimento(DatabaseUtils.getNextSeq(db, "opu_stabilimento_id_seq"));
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		newStabilimento.getPrefissoPageCampiEstesi(context.getAction().getActionName());
		if (context.getParameter("tipoInserimento")!=null)
			newStabilimento.setTipoInserimentoScia(Integer.parseInt(context.getParameter("tipoInserimento")));
		int idOperatore = -1;
		idOperatore = newStabilimento.getIdOperatore();

		Operatore operatore = new Operatore () ;
		operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
		if (idOperatore>0)
			operatore.queryRecordOperatore(db, idOperatore);
		context.getRequest().setAttribute("Operatore", operatore);
		newStabilimento.setOperatore(operatore);
		if(context.getParameter("stato")!=null && !"".equals(context.getParameter("stato")))
			newStabilimento.setStato(Integer.parseInt(context.getParameter("stato")));

		if (newStabilimento.getSedeOperativa().getComune() <= 0)
			newStabilimento.getSedeOperativa().setComune(-1);

		context.getRequest().setAttribute("newStabilimento",newStabilimento);

	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}

	return getReturn(context, "Scelta");


}

public String executeCommandTrovaPartitaIva(ActionContext context)
		throws SQLException, IndirizzoNotFoundException,Exception {

	Connection db = null;
	boolean recordInserted = false;
	boolean isValid = false;
	boolean exist = false ;
	UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
	
	Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");

	String partitaIva = context.getRequest().getParameter("partita_iva");
	try {
		//				SoggettoFisico soggettoAdded = null;

		db = this.getConnection(context);
		
		OperatoreAction actionOperatore = new OperatoreAction();
		String esitoInserimentoOperatore = actionOperatore.executeCommandTrovaPartitaIva(context, partitaIva);
		List<Operatore> listaOperatori = (List<Operatore>)context.getRequest().getAttribute("ListaOperatori");
		newStabilimento.setListaOperatori(listaOperatori);
		
	}
	catch (Exception errorMessage) {

		errorMessage.printStackTrace();
		context.getRequest().setAttribute("Error", errorMessage);
		return "JsonSuapStabilimentoOpu";

	} finally {
		this.freeConnection(context, db);

	}
	return "JsonSuapStabilimentoOpu" ;


}

public String executeCommandTrovaStabilimentoComuneIva(ActionContext context) throws IndirizzoNotFoundException
{
	Connection db = null;
	boolean recordInserted = false;
	boolean isValid = false;
	boolean exist = false ;
	UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

	String iva = context.getRequest().getParameter("partita_iva");
	String comune = context.getRequest().getParameter("comune");
	
	// Integer orgId = null;
	Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
	Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");
	try {


		db = this.getConnection(context);
		StabilimentoList listaStab = new StabilimentoList();
		listaStab.setPartitaIva(iva);
		listaStab.setComuneSedeProduttiva(comune);
		listaStab.buildList(db);

		List<Stabilimento> lista = new ArrayList<Stabilimento>();
		Iterator<Stabilimento> itStab =  listaStab.iterator();
		while (itStab.hasNext())
			lista.add(itStab.next());
		
		
		if (lista.size()==0){
			
			OrganizationList listaOrg = new OrganizationList();
			listaOrg.setPartitaIva(iva);
			listaOrg.setAccountCity(comune);
			listaOrg.buildList(db);
			
			List<Organization> lista2 = new ArrayList<Organization>();
			Iterator<Organization> itOrg =  listaOrg.iterator();
			while (itOrg.hasNext())
				lista2.add(itOrg.next());
			
			context.getRequest().setAttribute("ListaOrganization",lista2);
			
		}

		context.getRequest().setAttribute("ListaStabilimenti",lista);
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	finally
	{
		this.freeConnection(context, db);
	}
	return "JsonStabilimentoListOpu" ;
}

public static void gestisciMappingLineePregresse(int vecchia, int nuova, int vecchiaRel, int stab, int utente, Connection db) throws SQLException{
	String sqlInsert = "insert into suap_mapping_linee (id_linea_vecchia, id_linea_nuova, id_stabilimento, id_rel_stab_lp_vecchia, id_utente, data_mapping) values (?, ?, ?, ?, ?, now());";
	PreparedStatement pst = db.prepareStatement(sqlInsert);
	pst.setInt(1, vecchia);
	pst.setInt(2, nuova);
	pst.setInt(3, stab);
	pst.setInt(4, vecchiaRel);
	pst.setInt(5, utente);
	pst.execute();

	
}



public String executeCommandGestioneCampiEstesiAnagrafica(ActionContext context)
{
	if (!hasPermission(context, "modifica-campiestesi-anag-view")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null; 
	try
	{
		String idStab = context.getRequest().getParameter("stabId");
		if (idStab == null) {
			idStab = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		db = this.getConnection(context);	
		newStabilimento = new Stabilimento(db,  Integer.parseInt(idStab));
		LineaProduttivaList linee = newStabilimento.getListaLineeProduttive();
		
		HttpServletRequest req = context.getRequest();
		
		String fallBackUrl = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/OpuStab.do?command=Details&stabId="+idStab;
				
		req.setAttribute("linee", linee);
		req.setAttribute("fallBackUrl", fallBackUrl);
		req.setAttribute("idStab", idStab);
		return getReturn(context, "GestioneCampiEstesiAnagrafici");
	}
	catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	
}


public String executeCommandGestioneExCodiceL30(ActionContext context)
{
	if (!hasPermission(context, "modifica-excodel30-view")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null; 
	try
	{
		String idStab = context.getRequest().getParameter("stabId");
		if (idStab == null) {
			idStab = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		db = this.getConnection(context);	
		newStabilimento = new Stabilimento(db,  Integer.parseInt(idStab));
		LineaProduttivaList linee = newStabilimento.getListaLineeProduttive();
		
		HttpServletRequest req = context.getRequest();
		
		String fallBackUrl = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/OpuStab.do?command=Details&stabId="+idStab;
				
		req.setAttribute("linee", linee);
		req.setAttribute("fallBackUrl", fallBackUrl);
		req.setAttribute("idStab", idStab);
		return getReturn(context, "GestioneExCodiceL30");
	}
	catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	
}


public String executeCommandAggiornaCampiEstesiDiAnagrafica(ActionContext context)
{
	
	
	String toRet = "paginaRispostaJson";
	Connection db = null;
	try
	{
		db = this.getConnection(context);	
		/*ottengo tutti i campi estesi di tipo anag con valore per quella linea in opu */
		Integer idRelStabLp = Integer.parseInt(context.getRequest().getParameter("idRelStabLp"));
		
		ArrayList<LineeMobiliHtmlFields> campiEstesiAnagConVals = 
				new org.aspcfs.modules.suap.actions.StabilimentoAction().ottieniInputsPerLineaAnagraficiConValori(db,idRelStabLp);
		
		/*li scorro, faccio append con idrelstablp e prendo valore */
		for(LineeMobiliHtmlFields campoEst : campiEstesiAnagConVals)
		{
			String nomeDaClient = campoEst.getNome_campo()+idRelStabLp;
			String valoreDaClient = context.getRequest().getParameter(nomeDaClient);
			if(valoreDaClient == null || valoreDaClient.trim().length() == 0)
			{
				continue;
			}
			System.out.print("valore ricevuto per "+campoEst.getId()+" e' "+valoreDaClient);
			LineeMobiliHtmlFields.updateValorePerFieldValues(db, valoreDaClient, campoEst.getId(), idRelStabLp);
		}
		
		context.getRequest().setAttribute("risultato", "OK");
	}
	
	catch(Exception ex)
	{
		
		context.getRequest().setAttribute("risultato", "KO");
		
		
	}
	finally
	{
		this.freeConnection(context, db);
	}
	
	return toRet;
}


/*SALVATAGGIO EX CODICE L30 DA MASCHERA DETTAGLIO OSA */
public String executeCommandAggiornaCampiEstesiExCodeL30(ActionContext context)
{
	
	
	String toRet = "paginaRispostaJson";
	Connection db = null;
	try
	{
		db = this.getConnection(context);	
		/*ottengo tutti i campi estesi di tipo anag con valore per quella linea in opu */
		Integer idRelStabLp = Integer.parseInt(context.getRequest().getParameter("idRelStabLp"));
		
		ArrayList<LineeMobiliHtmlFields> campiEstesiExCodeL30 = 
				new org.aspcfs.modules.suap.actions.StabilimentoAction().ottieniInputsPerLineaExCodeL30ConValori(db,idRelStabLp);
		
		/*li scorro, faccio append con idrelstablp e prendo valore */
		for(LineeMobiliHtmlFields campoEst : campiEstesiExCodeL30)
		{
			String nomeDaClient = campoEst.getNome_campo()+idRelStabLp;
			String valoreDaClient = context.getRequest().getParameter(nomeDaClient);
			if(valoreDaClient == null || valoreDaClient.trim().length() == 0)
			{
				continue;
			}
			System.out.print("valore ricevuto per "+campoEst.getId()+" e' "+valoreDaClient);
			if (!LineeMobiliHtmlFields.updateValorePerExCodeL30(db, valoreDaClient, campoEst.getId(), idRelStabLp))
				throw new Exception ();
			//throw new EccezioneCodiceL30GiaInUso("Valore riservato"); 
		}
		
		context.getRequest().setAttribute("risultato", "OK");
	}
	catch(EccezioneCodiceL30GiaInUso ex)
	{
		context.getRequest().setAttribute("msg_ko", ex.getMessage() ); 
		context.getRequest().setAttribute("risultato", "KO");
	}
	catch(Exception ex)
	{
		
		context.getRequest().setAttribute("risultato", "KO");
		
	}
	finally
	{
		this.freeConnection(context, db);
	}
	
	return toRet;
}



public String executeCommandGestioneMobile(ActionContext context) {
	if (!hasPermission(context, "opu-gestione-mobile-view")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	try {
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		db = this.getConnection(context);	
		newStabilimento = new Stabilimento(db,  stabid);
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);

		Operatore operatore = new Operatore () ;
		org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
		org.setName(operatore.getRagioneSociale());
		operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
		operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
		context.getRequest().setAttribute("Operatore", operatore);

		LookupList OperazioniList = new LookupList(db,"lookup_suap_operazioni");
		context.getRequest().setAttribute("OperazioniList", OperazioniList);

		Storico sto = new Storico();
		Vector storicoList = new Vector();
		storicoList = sto.cercaStoricoPratica(db, newStabilimento.getIdStabilimento());
		context.getRequest().setAttribute("listaStorico", storicoList);

		context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
		
		/*LineaProduttivaList listaLineeProduttive= newStabilimento.getListaLineeProduttive();
		LineaProduttivaList listaLineeProduttiveAutomezzi= new LineaProduttivaList();
		LineaProduttivaList listaLineeProduttiveDistributori= new LineaProduttivaList();
		LineaProduttivaList listaLineeProduttiveProdotti= new LineaProduttivaList();

		Iterator it = listaLineeProduttive.iterator();
		while (it.hasNext()){
			LineaProduttiva lp =(LineaProduttiva) it.next();
			if(lp.getMobile()==1)
				listaLineeProduttiveAutomezzi.add(lp);
			else if(lp.getMobile()==2) 
				listaLineeProduttiveDistributori.add(lp);
			else if(lp.getMobile()==3)
				listaLineeProduttiveProdotti.add(lp);
		}
	
		context.getRequest().setAttribute("ListaLineeProduttiveAutomezzi", listaLineeProduttiveAutomezzi);
		context.getRequest().setAttribute("ListaLineeProduttiveDistributori", listaLineeProduttiveDistributori);
		context.getRequest().setAttribute("listaLineeProduttiveProdotti", listaLineeProduttiveProdotti);*/
		context.getRequest().setAttribute("listaLineeProduttive", newStabilimento.getListaLineeProduttive());


  		return getReturn(context, "GestioneMobile");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
}

public String executeCommandInserisciDettaglioMobile(ActionContext context) {
	if (!hasPermission(context, "opu-gestione-mobile-add")) {
		return getReturn(context, "PermissionError");
	
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	try {
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		
		String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		
		db = this.getConnection(context);	
	

		LineeMobiliHtmlFields newModulo = new LineeMobiliHtmlFields();
	
		try {
			newModulo.insertDettaglioMobile( db, Integer.parseInt(lda_macroarea), Integer.parseInt(lda_rel_stab), stabid, context);
		}
		catch (Exception e) {
			context.getRequest().setAttribute("messaggioOk", "Errore inserimento dato. Controllare i valori inseriti. ");
			return executeCommandGestioneMobile(context);
		}
		RicercheAnagraficheTab.inserOpu(db, stabid);
		
		
		//newPnaa.update(db, idCampione);	
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return getReturn(context, "SystemError");

		} finally {
			this.freeConnection(context, db);
		}
	//context.getRequest().setAttribute("idCampione", String.valueOf(idCampione));
	//context.getRequest().setAttribute("orgId", orgId);
	//context.getRequest().setAttribute("tipo", tipoAnalita);
	context.getRequest().setAttribute("messaggioOk", "Inserimento avvenuto con successo.");
	//return getReturn(context, "AggiuntaDettaglioMobile");
	return executeCommandGestioneMobile(context);

}



public String executeCommandModificaDettaglioMobile(ActionContext context) 
{
	if (!hasPermission(context, "opu-gestione-mobile-add")) 
	{
		return getReturn(context, "PermissionError");
	
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	try 
	{
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) 
		{
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) 
		{
			stabid = Integer.parseInt(tempStabId);
		}
		
		String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		
		db = this.getConnection(context);	
	

		LineeMobiliHtmlFields newModulo = new LineeMobiliHtmlFields();
		newModulo.updateDettaglioMobile( db, Integer.parseInt(lda_macroarea), Integer.parseInt(lda_rel_stab), stabid, context);
		RicercheAnagraficheTab.inserOpu(db, stabid);
		
		GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
		gBdu.aggiornaDatiEstesi(Integer.parseInt(lda_rel_stab));
		
		//newPnaa.update(db, idCampione);	
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return getReturn(context, "SystemError");

		} finally {
			this.freeConnection(context, db);
		}
	context.getRequest().setAttribute("messaggioOk", "Modifica avvenuta con successo.");
	return executeCommandGestioneMobile(context);
}


public String executeCommandPreparazioneLineaMobile(ActionContext context) {
	if (!hasPermission(context, "opu-gestione-mobile-add")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
	try {
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		
		db = this.getConnection(context);	
		
		newStabilimento = new Stabilimento(db,  stabid);
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);
		
			
		Iterator it = newStabilimento.getListaLineeProduttive().iterator();
		while (it.hasNext())
		{
			LineaProduttiva lp =(LineaProduttiva) it.next();
			if(lp.getId()==Integer.parseInt(lda_macroarea))
			{
				context.getRequest().setAttribute("consentiUploadFile", lp.getConsentiUploadFile());
				context.getRequest().setAttribute("consentiValoriMultipli", lp.getConsentiValoriMultipli());
				break;
			}
			
		}

		Operatore operatore = new Operatore () ;
		operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
		context.getRequest().setAttribute("Operatore", operatore);
		
		

		
		//ComuniAnagrafica com = null ;
		int idComune = -1 ;

		String indirizzo_hid="";
		String comune_hid="";
		String provincia_hid="";
		String asl_hid="";
		String cap_hid="";

		
		//INDIVIDUALE
		if (operatore.getTipo_impresa()==1){
			//Mobile
			if (newStabilimento.getTipoAttivita()==2 || newStabilimento.getTipoAttivita()==3){
				//codice individuale mobile: RESIDENZA
				//com = new ComuniAnagrafica(db, operatore.getRappLegale().getIndirizzo().getComune());
				idComune = operatore.getRappLegale().getIndirizzo().getComune() ;
				indirizzo_hid=((operatore.getRappLegale().getIndirizzo().getDescrizioneToponimo()!=null && !operatore.getRappLegale().getIndirizzo().getDescrizioneToponimo().equalsIgnoreCase("null")) ? operatore.getRappLegale().getIndirizzo().getDescrizioneToponimo() : "VIA")+" "+operatore.getRappLegale().getIndirizzo().getVia();				
				comune_hid=operatore.getRappLegale().getIndirizzo().getDescrizioneComune();
				provincia_hid=operatore.getRappLegale().getIndirizzo().getDescrizione_provincia();
				asl_hid=operatore.getRappLegale().getIndirizzo().getDescrizioneAsl();
				cap_hid=operatore.getRappLegale().getIndirizzo().getCap();				
			}
			else if (newStabilimento.getTipoAttivita()==1){
				//codice individuale fissa: OPERATIVA
				//com = new ComuniAnagrafica(db, newStabilimento.getSedeOperativa().getComune());
				idComune = newStabilimento.getSedeOperativa().getComune();
				indirizzo_hid=((newStabilimento.getSedeOperativa().getDescrizioneToponimo()!=null && !newStabilimento.getSedeOperativa().getDescrizioneToponimo().equalsIgnoreCase("null")) ? newStabilimento.getSedeOperativa().getDescrizioneToponimo() : "VIA")+" "+newStabilimento.getSedeOperativa().getVia();
				comune_hid=newStabilimento.getSedeOperativa().getDescrizioneComune();
				provincia_hid=newStabilimento.getSedeOperativa().getDescrizione_provincia();
				asl_hid=newStabilimento.getSedeOperativa().getDescrizioneAsl();
				cap_hid=newStabilimento.getSedeOperativa().getCap();

			}
		}
		//FISSA
		else if (newStabilimento.getTipoAttivita()==1){
			//codice fissa: operativa
			//com = new ComuniAnagrafica(db, newStabilimento.getSedeOperativa().getComune());
			idComune = newStabilimento.getSedeOperativa().getComune();
			indirizzo_hid=((newStabilimento.getSedeOperativa().getDescrizioneToponimo()!=null && !newStabilimento.getSedeOperativa().getDescrizioneToponimo().equalsIgnoreCase("null")) ? newStabilimento.getSedeOperativa().getDescrizioneToponimo() : "VIA")+" "+newStabilimento.getSedeOperativa().getVia();
			comune_hid=newStabilimento.getSedeOperativa().getDescrizioneComune();
			provincia_hid=newStabilimento.getSedeOperativa().getDescrizione_provincia();
			asl_hid=newStabilimento.getSedeOperativa().getDescrizioneAsl();
			cap_hid=newStabilimento.getSedeOperativa().getCap();
		}
		//MOBILE
		else if (newStabilimento.getTipoAttivita()==2 || newStabilimento.getTipoAttivita()==3){
			//codice mobile: legale
			//com = new ComuniAnagrafica(db, operatore.getSedeLegale().getComune());
			idComune = operatore.getSedeLegale().getComune();
			indirizzo_hid=((operatore.getSedeLegale().getDescrizioneToponimo()!=null && !operatore.getSedeLegale().getDescrizioneToponimo().equalsIgnoreCase("null")) ? operatore.getSedeLegale().getDescrizioneToponimo() : "VIA")+" "+operatore.getSedeLegale().getVia();
			comune_hid=operatore.getSedeLegale().getDescrizioneComune();
			provincia_hid=operatore.getSedeLegale().getDescrizione_provincia();
			asl_hid=operatore.getSedeLegale().getDescrizioneAsl();
			cap_hid=operatore.getSedeLegale().getCap();
			
		}

	  	context.getRequest().setAttribute("indirizzohid", indirizzo_hid);
	  	context.getRequest().setAttribute("comunehid", comune_hid);
	  	context.getRequest().setAttribute("provinciahid", provincia_hid);
	  	context.getRequest().setAttribute("aslhid", asl_hid);
	  	context.getRequest().setAttribute("caphid", cap_hid);

		
		
		

		// Costruzione campi in base al tipo di attivita'
		LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campi = new LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>>();
  		String query="";
  		query = "select campi.*, v.valore_campo as valore_campo,v.indice as indice "+
  				" from linee_mobili_html_fields campi "+
  				" join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = campi.id and (v.id_opu_rel_stab_linea = ? or id_rel_stab_linea = ?) and v.enabled "+
  				" where campi.id_linea= ? and campi.enabled order by ordine_campo asc ";
		PreparedStatement pst = db.prepareStatement(query);
		pst.setInt(1, Integer.parseInt(lda_rel_stab));
		pst.setInt(2, Integer.parseInt(lda_rel_stab));
  		pst.setInt(3, Integer.parseInt(lda_macroarea));
  		ResultSet rs = pst.executeQuery();
  		while (rs.next()){
  			LineeMobiliHtmlFields campo = new LineeMobiliHtmlFields();
  			campo.buildRecord(db, rs);
  			
  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
  				ArrayList<LineeMobiliHtmlFields> campoArray = new ArrayList<LineeMobiliHtmlFields>(); //Crea array vuoto
  				campoArray.add(campo); //Aggiungi campo
  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
  			}
  			else
  				campi.get(campo.getNome_campo()).add(campo);
  		}
  		pst.close();
  		LineeMobiliHtmlFields c = new LineeMobiliHtmlFields();
  		LinkedHashMap<String, String> ris = c.costruisciHtmlDaHashMap(db, campi,thisUser.getRoleId());
  		// Form inerente agli inserimenti
	  	context.getRequest().setAttribute("campiHash", ris);
	  	
	  	
	  	
	  		// Costruzione campi in base al tipo di attivita' SENZA VALORE
	 		LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campiSenzaValore = new LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>>();
	   		String querySenzaValore="";
	   		query = "select campi.*, '' as valore_campo,'' as indice "+
	   				" from linee_mobili_html_fields campi "+
	   				" where campi.id_linea= ? and campi.enabled order by ordine_campo asc ";
	 		PreparedStatement pstSenzaValore = db.prepareStatement(query);
	   		pstSenzaValore.setInt(1, Integer.parseInt(lda_macroarea));
	   		ResultSet rsSenzaValore = pstSenzaValore.executeQuery();
	   		while (rsSenzaValore.next()){
	   			LineeMobiliHtmlFields campo = new LineeMobiliHtmlFields();
	   			campo.buildRecord(db, rsSenzaValore);
	   			
	   			if  (!campiSenzaValore.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
	   				ArrayList<LineeMobiliHtmlFields> campoArray = new ArrayList<LineeMobiliHtmlFields>(); //Crea array vuoto
	   				campoArray.add(campo); //Aggiungi campo
	   				campiSenzaValore.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
	   			}
	   			else
	   				campiSenzaValore.get(campo.getNome_campo()).add(campo);
	   		}
	   		pstSenzaValore.close();
	  	LinkedHashMap<String, String> risSenzaValore = c.costruisciHtmlDaHashMap(db, campiSenzaValore,thisUser.getRoleId());
	  	context.getRequest().setAttribute("campiHashSenzaValore", risSenzaValore);
		
		// Form inerente alla visualizzazione / eliminazione
	  	ArrayList<LineeMobiliHtmlFields> campoArray = new ArrayList<LineeMobiliHtmlFields>(); //Crea array vuoto
	  	
	  	//LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campiVisualize = new LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>>();	
		query = "select campi.*, mod.valore_campo, mod.indice, mod.data_modifica,mod.id_utente_modifica,mod.data_inserimento,mod.id_utente_inserimento  from linee_mobili_html_fields campi "+   
				" JOIN linee_mobili_fields_value mod on mod.id_linee_mobili_html_fields = campi.id and (mod.id_rel_stab_linea = ? or mod.id_opu_rel_stab_linea = ?)  "+ 
				" where campi.id_linea =  ? and (mod.enabled = true or mod.enabled is null) and campi.enabled=true order by mod.indice,campi.ordine_campo asc ";

		pst = db.prepareStatement(query);
		pst.setInt(1, Integer.parseInt(lda_rel_stab)); //1
		pst.setInt(2, Integer.parseInt(lda_rel_stab)); //1
		pst.setInt(3, Integer.parseInt(lda_macroarea)); //2
		rs = pst.executeQuery();
		while (rs.next()){
			LineeMobiliHtmlFields campo = new LineeMobiliHtmlFields();
			campo.buildRecord(db, rs);
			campoArray.add(campo); //Aggiungi campo
		}
		pst.close();
		context.getRequest().setAttribute("listaElementi", campoArray);
				
		context.getRequest().setAttribute("ldaMacroId", lda_macroarea);
		context.getRequest().setAttribute("ldaStabId", lda_rel_stab);
		context.getRequest().setAttribute("stabId", stabid);
		
	  	//Variabile che conta i campi del dettagli
	 	context.getRequest().setAttribute("numeroCampi", ris.size());

	 	LookupList lookup_tipo_alimento_distributore = new LookupList(db,"lookup_tipo_alimento_distributore");
		context.getRequest().setAttribute("TipoAlimentoDistributore", lookup_tipo_alimento_distributore);

		LookupList lookup_tipo_mobili = new LookupList(db,"lookup_tipo_mobili");
		context.getRequest().setAttribute("TipoMobili", lookup_tipo_mobili);
				
		return getReturn(context, "AggiuntaDettaglioMobile");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
}


public String executeCommandEliminaMobileDaLinea(ActionContext context) {
	if (!hasPermission(context, "opu-gestione-mobile-add")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	//Stabilimento newStabilimento = null;
	try {
		//String tempStabId = context.getRequest().getParameter("stabId");
		//if (tempStabId == null) {
		//	tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		//}
		//Integer stabid = null;
		//if (tempStabId != null) {
		//	stabid = Integer.parseInt(tempStabId);
		//}
		//String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		String indice=context.getRequest().getParameter("indice");
		String tempStabId=context.getRequest().getParameter("stabId");
		Integer stabid = null;
		if (tempStabId != null) 
		{
			stabid = Integer.parseInt(tempStabId);
		}
		
		db = this.getConnection(context);	
		LineeMobiliHtmlFields.eliminazioneLogicaDettaglioMobile(db,Integer.parseInt(indice),Integer.parseInt(lda_rel_stab),context);
		RicercheAnagraficheTab.inserOpu(db, stabid);

	}catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("messaggioOk", "Eliminazione avvenuta con successo.");
	//return getReturn(context, "AggiuntaDettaglioMobile");
	return executeCommandGestioneMobile(context);
}

public String executeCommandDismettiMobileDaLinea(ActionContext context) {
	if (!hasPermission(context, "datiaggiuntivi-edit")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	String esito = "";
	//Stabilimento newStabilimento = null;
	try {
		//String tempStabId = context.getRequest().getParameter("stabId");
		//if (tempStabId == null) {
		//	tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		//}
		//Integer stabid = null;
		//if (tempStabId != null) {
		//	stabid = Integer.parseInt(tempStabId);
		//}
		//String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		String indice=context.getRequest().getParameter("indice");
		String tempStabId=context.getRequest().getParameter("stabId");
		String note=context.getRequest().getParameter("note");
		String data=context.getRequest().getParameter("data");

		Integer stabid = null;
		if (tempStabId != null) 
		{
			stabid = Integer.parseInt(tempStabId);
		}
		
		db = this.getConnection(context);	
		esito = LineeMobiliHtmlFields.dismissioneDettaglioMobile(db,Integer.parseInt(indice),Integer.parseInt(lda_rel_stab),note, data, context);
		RicercheAnagraficheTab.inserOpu(db, stabid);

	}catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("messaggioOk", esito);
	//return getReturn(context, "AggiuntaDettaglioMobile");
	return executeCommandGestioneMobile(context);
}

public String executeCommandDettaglioRichiesta(ActionContext context) {
int idRichiesta = Integer.parseInt(context.getRequest().getParameter("idRichiesta"));

Connection db = null;
SystemStatus systemStatus = this.getSystemStatus(context);
try {
	
	db = this.getConnection(context);	

	org.aspcfs.modules.suap.base.Stabilimento richiesta = new org.aspcfs.modules.suap.base.Stabilimento();
	richiesta.queryRecordStabilimentoIdOperatore(db, idRichiesta);
	
	
	context.getRequest().setAttribute("Richiesta", richiesta);

} catch (Exception e) {
	e.printStackTrace();
	// Go through the SystemError process
	context.getRequest().setAttribute("Error", e);
	return ("SystemError");
} finally {
	this.freeConnection(context, db);
}

return "DettaglioRichiestaOK";
}

public String executeCommandAddStabilimentoPregresso(ActionContext context) {
	return "AddStabilimentoPregressoOK";

}

private void caricaInfoAllevamento(ActionContext context, Connection db, Stabilimento newStabilimento) throws SQLException{
	LineaProduttivaList listaLineeProduttive= newStabilimento.getListaLineeProduttive();
	
	Iterator it = listaLineeProduttive.iterator();
	while (it.hasNext()){
		LineaProduttiva lp =(LineaProduttiva) it.next();
		lp.setDecodificaBdn(db);
		if (lp.getDecodificaTipoProduzioneBdn()!=null){
			context.getRequest().setAttribute("bdnSpecie", lp.getDecodificaSpecieBdn());
			context.getRequest().setAttribute("bdnCodAzienda", lp.getCodiceNazionale()!=null ? lp.getCodiceNazionale() : lp.getCodice_ufficiale_esistente());
			break;
		}
	}
}



public String executeCommandDetailsOperatoriMercatiIttici(
		ActionContext context) {
	if (!hasPermission(context, "stabilimenti-stabilimenti-view")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	org.aspcfs.modules.stabilimenti.base.Organization newOrg = null;
	ArrayList elencoAttivita = null;
	try {

		

		String temporgId = context.getRequest().getParameter("orgId");
		if (temporgId == null) {
			temporgId = (String) context.getRequest().getAttribute("orgId");
		}
		int tempid = Integer.parseInt(temporgId);
		
		
		String stab = context.getRequest().getParameter("stabId");
		if (stab == null) {
			stab = (String) context.getRequest().getAttribute("stabId");
		}
		int stabId = Integer.parseInt(stab);

		db = this.getConnection(context);
		/*if (!isRecordAccessPermitted(context, db, Integer
				.parseInt(temporgId))) {
			return ("PermissionError");
		}*/
		newOrg = new org.aspcfs.modules.stabilimenti.base.Organization(db, tempid,"opu");
		// check whether or not the owner is an active User

		// Aggiornamento categoria mercato ittico in caso di discrepanza
		if (hasPermission(context, "stabilimenti-operatori-ittici-view")) {
			int nCategoriaMax = maxCategoriaRischioMercatoIttico(context,
					stabId,db);

			if (nCategoriaMax != newOrg.getCategoriaRischio())
				aggiornaCategoriaRischioGlobaleMercatoIttico(context,
						stabId,db);
		}

		LookupList siteList = new LookupList(db, "lookup_site_id");
		siteList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("SiteList", siteList);

		LookupList statoLab = new LookupList(db, "lookup_stato_lab");
		statoLab.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("statoLab", statoLab);

		LookupList categoriaRischioList = new LookupList(db,
				"lookup_org_catrischio");
		categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("OrgCategoriaRischioList",
				categoriaRischioList);

		LookupList impianto = new LookupList(db, "lookup_impianto");
		impianto.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("impianto", impianto);

		LookupList IstatList = new LookupList(db, "lookup_codistat");
		IstatList.addItem(-1, getSystemStatus(context).getLabel(
				"calendar.none.4dashes"));
		context.getRequest().setAttribute("IstatList", IstatList);

		

		LookupList categoriaList = new LookupList(db, "lookup_categoria");
		categoriaList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("categoriaList", categoriaList);

		LookupList imballataList = new LookupList(db,
				"lookup_sottoattivita_imballata");
		imballataList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("imballataList", imballataList);

		LookupList tipoAutorizzazioneList = new LookupList(db,
				"lookup_sottoattivita_tipoautorizzazione");
		tipoAutorizzazioneList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("tipoAutorizzazioneList",
				tipoAutorizzazioneList);


		/*
		 * org.aspcfs.modules.audit.base.AuditList audit = new
		 * org.aspcfs.modules.audit.base.AuditList(); int AuditOrgId =
		 * newOrg.getOrgId(); audit.setOrgId(AuditOrgId);
		 * 
		 * //audit.buildList(db);
		 * 
		 * if( (audit.size() - 1)>=0){
		 * 
		 * context.getRequest().setAttribute("Audit",audit.get(0) ); }
		 */
		

		ArrayList<OperatoriAssociatiMercatoIttico> impreseAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
				.getOperatoriImpreseById(db, stabId);
		context.getRequest().setAttribute("impreseAssociateMercatoIttico",
				impreseAssociateMercatoIttico);

		ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
				.getOperatoriStabilimentiById(db, stabId);
		context.getRequest().setAttribute(
				"stabilimentiAssociateMercatoIttico",
				stabilimentiAssociateMercatoIttico);

	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	addRecentItem(context, newOrg);
	String action = context.getRequest().getParameter("action");
	if (action != null && action.equals("modify")) {
		// If user is going to the modify form
		addModuleBean(context, "Accounts", "Modify Account Details");
		return ("DetailsOperatoriMercatiItticiOK");
	} else {
		// If user is going to the detail screen
		addModuleBean(context, "View Accounts", "View Account Details");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		return getReturn(context, "DetailsOperatoriMercatiIttici");
	}
}

public int maxCategoriaRischioMercatoIttico(ActionContext context, int id,Connection db) throws SQLException {
	int maxCategoria = -1;

	
	
		ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico.getOperatoriStabilimentiOpuById(db, id);

		

		for (OperatoriAssociatiMercatoIttico stabilimento : (ArrayList<OperatoriAssociatiMercatoIttico>) stabilimentiAssociateMercatoIttico) {
			if (stabilimento.getStabilimento().getCategoriaRischio() > -1) {
				if (stabilimento.getStabilimento().getCategoriaRischio() > maxCategoria)
					maxCategoria = stabilimento.getStabilimento()
					.getCategoriaRischio();
			} else {
				if (maxCategoria < 3)
					maxCategoria = stabilimento.getStabilimento()
					.getCategoriaRischio();
			}
		}

	

	return maxCategoria;

}




public int aggiornaCategoriaRischioGlobaleMercatoIttico(
		ActionContext context, int id,Connection db) throws SQLException, IndirizzoNotFoundException {
	int ret = -1;
	int cat_attuale = -564;

		ArrayList<OperatoriAssociatiMercatoIttico> impreseAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
				.getOperatoriImpreseOpuById(db, id);
		ArrayList<OperatoriAssociatiMercatoIttico> stabilimentiAssociateMercatoIttico = OperatoriAssociatiMercatoIttico
				.getOperatoriStabilimentiOpuById(db, id);

		for (OperatoriAssociatiMercatoIttico impresa : (ArrayList<OperatoriAssociatiMercatoIttico>) impreseAssociateMercatoIttico) {
			if (impresa.getImpresa().getCategoriaRischio() > 0)
				cat_attuale = impresa.getImpresa().getCategoriaRischio();
			else
				cat_attuale = 3;

			if (cat_attuale > ret)
				ret = cat_attuale;
		}

		for (OperatoriAssociatiMercatoIttico stabilimento : (ArrayList<OperatoriAssociatiMercatoIttico>) stabilimentiAssociateMercatoIttico) {
			if (stabilimento.getStabilimento().getCategoriaRischio() > 0)
				cat_attuale = stabilimento.getStabilimento()
				.getCategoriaRischio();
			else
				cat_attuale = 3;

			if (cat_attuale > ret)
				ret = cat_attuale;
		}

		Stabilimento newOrg = new Stabilimento(db, id);

		if (newOrg.getCategoriaRischio() > ret) {
			ret = newOrg.getCategoriaRischio();
		}

		
		

		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));

		newOrg.updateCategoriaPrecedente(db, ret, id);

	

	return ret;

}


public String executeCommandAggiungiOperatoreMercatoIttico(
		ActionContext context) {
	Connection db = null;
	int org_id = Integer.parseInt(context.getRequest()
			.getParameter("orgId"));
	int idOperatore = Integer.parseInt(context.getRequest().getParameter(
			"idOperatore"));
	int tipoOperatore = Integer.parseInt(context.getRequest().getParameter(
			"tipoOperatore"));

	context.getRequest().setAttribute("idStab", org_id);
	try {
		db = getConnection(context);

		OperatoriAssociatiMercatoIttico nuovoOperatore = new OperatoriAssociatiMercatoIttico();

		nuovoOperatore.setEntered_by(getUserId(context));
		nuovoOperatore.setIdMercatoIttico(org_id);
		nuovoOperatore.setIdOperatore(idOperatore);
		nuovoOperatore.setTipo(tipoOperatore);
		nuovoOperatore.setContenitoreMercatoIttico("opu");
		nuovoOperatore.store(db);

		
		aggiornaCategoriaRischioGlobaleMercatoIttico(context, org_id,db);

	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		e.printStackTrace();
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	return executeCommandDetails(context);

}


public String executeCommandEliminaOperatoreMercatoIttico(
		ActionContext context) {
	Connection db = null;
	int org_id = Integer.parseInt(context.getRequest()
			.getParameter("orgId"));

	context.getRequest().setAttribute("idStab", org_id);
	try {
		db = getConnection(context);

		int id = Integer.parseInt(context.getRequest().getParameter("id"));
		OperatoriAssociatiMercatoIttico.delete(db, id);

		aggiornaCategoriaRischioGlobaleMercatoIttico(context, org_id,db);

	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		e.printStackTrace();
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	return executeCommandDetails(context);

}


public String executeCommandListaOperatoriMercatoIttico(
		ActionContext context) {
	String ret = "ListaOperatoriMercatoItticoOK";
	
	
	int ORG_ID=0;
	
	
	
	
	if (context.getRequest().getParameter("orgId")==null){
		ORG_ID=Integer.parseInt((String) context.getRequest().getAttribute(
				"orgId"));
		
	}else{
		ORG_ID=Integer.parseInt(context.getRequest().getParameter(
				"orgId"));
	}
	
	final int org_id = ORG_ID;
	
	

	if (!hasPermission(context, "stabilimenti-operatori-ittici-view")) {
		return "PermissionError";
	}

	Connection db = null;
	RowSetDynaClass rsdc = null;

	try {
		db = getConnection(context);

		

		PreparedStatement stat = db
				.prepareStatement("SELECT DISTINCT description as asl,"
						+ " tipologia, "
						+ "name, "
						+ "partita_iva,"
						+ "codice_fiscale,"
						+ "CASE WHEN tipologia=3 THEN 'IMPRESA' ELSE 'STABILIMENTO' END as tipo,"
						+ "org_id,"
						+ "'' as edit"
						+ " FROM organization, lookup_site_id "
						+ " WHERE lookup_site_id.code=site_id AND ( tipologia=3 AND direct_bill=true) AND (trashed_date is null) "
						+ " AND org_id not in (select id_operatore as org_id from operatori_associati_mercato_ittico WHERE importato_in_anagrafica =false)"
						+ " and site_id=(select id_asl from opu_stabilimento where id="+org_id+" ) " + " ORDER BY asl,tipo,name");

		ResultSet rs = stat.executeQuery();
		rsdc = new RowSetDynaClass(rs);

		TableFacade tf = null;

		tf = TableFacadeFactory
				.createTableFacade("5", context.getRequest());
		tf.setItems(rsdc.getRows());
		tf.setColumnProperties("asl", "tipo", "name", "partita_iva",
				"codice_fiscale", "edit");

		tf.setStateAttr("restore");

		Limit limit = tf.getLimit();

		if (limit.isExported()) {
			tf.render();
			return "ListOK";
		} else {

			tf.getTable().getRow().getColumn("name").setTitle(
					"Ragione Sociale");
			tf.getTable().getRow().getColumn("partita_iva").setTitle(
					"Partita Iva");
			tf.getTable().getRow().getColumn("codice_fiscale").setTitle(
					"Codice Fiscale");
			tf.getTable().getRow().getColumn("tipo").setTitle(
					"Tipo Operatore");

			HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn(
					"tipo");
			cg.getFilterRenderer().setFilterEditor(
					new DroplistFilterEditor());

			tf.getTable().getRow().getColumn("edit").setTitle("Azione");
			cg = (HtmlColumn) tf.getTable().getRow().getColumn("edit");

			tf.getTable().getRow().getColumn("asl").setTitle("A.S.L.");
			HtmlColumn cg2 = (HtmlColumn) tf.getTable().getRow().getColumn(
					"asl");
			cg2.getFilterRenderer().setFilterEditor(
					new DroplistFilterEditor());

			cg.getCellRenderer().setCellEditor(new CellEditor() {
				public Object getValue(Object item, String property,
						int rowCount) {

					String idOperatore = (String) (new HtmlCellEditor())
							.getValue(item, "org_id", rowCount);

					String tipoOperatore = (String) (new HtmlCellEditor())
							.getValue(item, "tipologia", rowCount);

					return "<a href=\"OpuStab.do?command=AggiungiOperatoreMercatoIttico&orgId="
					+ org_id
					+ "&idOperatore="
					+ idOperatore
					+ "&tipoOperatore="
					+ tipoOperatore
					+ "\" onclick=\"return confirm('Sei sicuro di voler aggiungere l\'operatore selezionato?');\">Aggiungi</a>";
				}
			}

					);

		}

		String tabella = tf.render();
		context.getRequest().setAttribute("tabella", tabella);

		LookupList siteList = new LookupList(db, "lookup_site_id");
		siteList.addItem(0, "Regione");
		context.getRequest().setAttribute("SiteList", siteList);

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		this.freeConnection(context, db);
	}

	return ret;
}






public String executeCommandDownloadFileEsitoImportDistributori(ActionContext context) throws SQLException, IOException, ParseException {
	
	Connection db = null ;
	try
	{
		db = super.getConnection(context);
		int idInvio =Integer.parseInt(context.getParameter("idInvio"));
		InvioMassivoDistributori  invio = new InvioMassivoDistributori();
		invio.queryRecord(db, idInvio);
		ArrayList<Distrubutore> listaRecord = invio.getAllRecordsKo(db);
		
		String contentFile = "Matricola;data;comune;asl;ubicazione;descrizione errore\n" ;
		
		for (Distrubutore dist : listaRecord)
		{
			contentFile+=dist.getMatricola()+";"+dist.getDataInst()+";"+dist.getComune()+";"+dist.getAsl()+";"+dist.getUbicazione()+";"+dist.getDescrizioneErrore()+"\n";
		}
		
		
		
		context.getResponse().setContentType("text/csv");
		context.getResponse().setHeader("Content-Disposition", "attachment; filename=\"ImportDistributori_"+invio.getData()+".csv\"");
	    try
	    {
	        OutputStream outputStream = context.getResponse().getOutputStream();
	        outputStream.write(contentFile.getBytes());
	        outputStream.flush();
	        outputStream.close();
	    }
	    catch(Exception e)
	    {
	        System.out.println(e.toString());
	    }
		
		
	}
	catch(SQLException e)
	{
		
	}
	finally
	{
		super.freeConnection(context, db);
	}
	
	return "-none-";
			
	
}



public String executeCommandListaImportDistributoriPerLinea(ActionContext context) throws SQLException, IOException {
	HttpServletRequest req = context.getRequest();
	String action = req.getParameter("action");
	Connection db = null;
	
	

	UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	
	
	int lda_rel_stab = Integer.parseInt(context.getParameter("ldaStabId"));
	
	InvioMassivoDistributori invioMassivo = new InvioMassivoDistributori();
	try {

		db = getConnection(context);
		

		invioMassivo.setId_rel_stab_lp(lda_rel_stab);
		
		 ArrayList<InvioMassivoDistributori> listaInvii = invioMassivo.getListaImportDistributori(db, context);
		 context.getRequest().setAttribute("listaInvii", listaInvii);
		

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		this.freeConnection(context, db);
	}
	
	
	
	context.getRequest().setAttribute("InvioMassivo", invioMassivo);
	return "ListaInviiDistributoriOK";

}






public String executeCommandImportDistributori(ActionContext context) throws SQLException, IOException {
	HttpServletRequest req = context.getRequest();
	String action = req.getParameter("action");
	Connection db = null;
	
	MultipartRequest multi = null;
	
	int maxUploadSize = 50000000;
	
//	String filePath = this.getPath(context, "riunioni");
	
	String filePath = getWebInfPath(context,"tmp_riunioni");

	multi = new MultipartRequest(req, filePath, maxUploadSize,"UTF-8");

	UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	String stabId = multi.getParameter("stabId");
	int stabIdInt = Integer.parseInt(stabId);
	int lda_rel_stab = Integer.parseInt(multi.getParameter("ldaStabId"));
	int lda_macroarea = Integer.parseInt(multi.getParameter("ldaMacroId"));
	InvioMassivoDistributori invioMassivo = new InvioMassivoDistributori();
	try {

		db = getConnection(context);
		
		
			
		File myFileT = multi.getFile("file1");
		FileInputStream fiStream = new FileInputStream(myFileT);
		// BufferedReader input = new BufferedReader(new
		// FileReader(myFileT));

		BufferedReader input = new BufferedReader(new FileReader(myFileT));

		
		invioMassivo.setInviato_da(thisUser.getUserId());
		invioMassivo.setData(LeggiFile.getData());
		invioMassivo.setStab_id(stabIdInt);
		invioMassivo.setId_rel_stab_lp(lda_rel_stab);
	
		 LeggiFile.leggiCampiDistributoriOpuCSV(context, db, myFileT, thisUser.getUserId(), lda_macroarea, lda_rel_stab, stabIdInt,invioMassivo,lda_macroarea);
		
		 ArrayList<InvioMassivoDistributori> listaInvii = invioMassivo.getListaImportDistributori(db, context);
		 context.getRequest().setAttribute("listaInvii", listaInvii);
		 ArrayList<Distrubutore> listaRecordKO = invioMassivo.getAllRecordsKo(db);
		 context.getRequest().setAttribute("listaRecordKO", listaRecordKO);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		
		if(invioMassivo.getId()>0)
		{
			invioMassivo.setEsito("KO - "+e.getMessage());
			invioMassivo.aggiornaEsito(db);
		}
		
	} catch (InvalidFileException e) {
		
		invioMassivo.setEsito("KO - "+e.getMessage());
		invioMassivo.aggiornaEsito(db);
		
		e.printStackTrace();
	}finally {
		this.freeConnection(context, db);
	}
	
	
	context.getRequest().setAttribute("idStab", stabIdInt);
	context.getRequest().setAttribute("InvioMassivo", invioMassivo);
	return executeCommandGestioneMobile(context);

}


public String executeCommandPrepareSincronizzaVam(ActionContext context) throws IndirizzoNotFoundException {
	int id = -1;
	
	String idString = context.getRequest().getParameter("id");
	
	try {id = Integer.parseInt(idString);} catch (Exception e) {}
	
	if (id==-1){
		idString = (String) context.getRequest().getAttribute("id");
		try {id = Integer.parseInt(idString);} catch (Exception e) {}

	}
	
	Connection db = null;

	try {
		db = getConnection(context);
		Stabilimento stab = new Stabilimento(db, id);
		boolean isVam = false;
		for (int i = 0; i< stab.getListaLineeProduttive().size(); i++){
			LineaProduttiva linea = (LineaProduttiva) stab.getListaLineeProduttive().get(i);
			PreparedStatement pst = db.prepareStatement("select * from get_flag_linea(?, ?)");
			pst.setInt(1, linea.getIdAttivita());
			pst.setInt(2, MasterListImportUtil.FLAG_VAM);
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				isVam = rs.getBoolean(1);
				if (isVam)
					break;
			}
		}
		
		if (isVam){
		context.getRequest().setAttribute("StabilimentoDettaglio", stab);
		}
		else {
			context.getRequest().setAttribute("msg", "Nessuna linea propagabile in VAM");	
		}
	}catch(SQLException e){
		e.printStackTrace();
	}finally{
		this.freeConnection(context, db);
	}
	return "prepareSincronizzaVamOK";
}

public String executeCommandSincronizzaVam(ActionContext context) throws IndirizzoNotFoundException, SQLException {
	int stabId = Integer.parseInt(context.getParameter("stabId"));
	String nome = context.getRequest().getParameter("nome");
	String nomeBreve = context.getRequest().getParameter("nomeBreve");
	String asl = context.getRequest().getParameter("asl");
	String comune = context.getRequest().getParameter("comune");
	String indirizzo = context.getRequest().getParameter("indirizzo");
	String email = context.getRequest().getParameter("email");
	String telefono = context.getRequest().getParameter("telefono");
	String noteHd = "Propagato da GISA da utente (id gisa): "+getUserId(context);
	String msg ="";
	GestoreComunicazioniVam gVam = new GestoreComunicazioniVam();
	msg = gVam.inserisciNuovaClinicaVam(stabId, nome, nomeBreve, asl, comune, indirizzo, email ,telefono, noteHd);
	context.getRequest().setAttribute("msg", msg);
	
	context.getRequest().setAttribute("id", String.valueOf(stabId));

	return executeCommandPrepareSincronizzaVam(context);
}



public String executeCommandListaLivelliAggiuntivi(ActionContext context) {

	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	ArrayList<LivelloAggiuntivo> alberoLivelli = new ArrayList<LivelloAggiuntivo>();
	Stabilimento newStabilimento = null;
	try {


		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;

		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}

		db = this.getConnection(context);
		
		newStabilimento = new Stabilimento(db, stabid);

		for (int i = 0; i<newStabilimento.getListaLineeProduttive().size(); i++ ){
			LineaProduttiva linea = (LineaProduttiva) newStabilimento.getListaLineeProduttive().get(i);
			
			ArrayList<LivelloAggiuntivo> listaLivelliLinea = new ArrayList<LivelloAggiuntivo>();
			listaLivelliLinea = LivelloAggiuntivo.getElencoLivelliAggiuntivi(db, linea.getId_rel_stab_lp(), linea.getCodice(), linea.getDescrizione_linea_attivita());
			alberoLivelli.addAll(listaLivelliLinea);
			
		}
		
		context.getRequest().setAttribute("alberoLivelli", alberoLivelli);
		context.getRequest().setAttribute("Stabilimento", newStabilimento);

		String esito = (String) context.getRequest().getAttribute("esito");
		context.getRequest().setAttribute("esito", esito);

		
		return getReturn(context, "PrepareListaLivelliAggiuntivi");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}

}


public String executeCommandSalvaLivelliAggiuntivi(ActionContext context) {

	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	
	try {

		int stabId= Integer.parseInt(context.getRequest().getParameter("stabId"));
		int sizeAlbero = Integer.parseInt(context.getRequest().getParameter("sizeAlbero"));
		db = this.getConnection(context);
		
		for (int i = 0; i< sizeAlbero; i++){
		int idIstanza = Integer.parseInt(context.getRequest().getParameter("idIstanza"+i));
		int idFoglia = Integer.parseInt(context.getRequest().getParameter("idFoglia"+i));
		boolean checked = false;
		String checkedString = context.getRequest().getParameter(idFoglia+"");
		if (checkedString != null && checkedString.equals("on"))
			checked = true;
		LivelloAggiuntivo.gestisciUpdate(db, idFoglia, idIstanza, checked);
		}		
		context.getRequest().setAttribute("esito", "ok");
		context.getRequest().setAttribute("idStab", stabId);
		return executeCommandListaLivelliAggiuntivi(context);

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}

}


public String executeCommandListaProdottiLinea(ActionContext context) throws IndirizzoNotFoundException{
	

	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	
	
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		ArrayList<SuapMasterListSchedaAggiuntiva> listaSchede = new ArrayList<SuapMasterListSchedaAggiuntiva>();
		
		LineaProduttiva rel = new LineaProduttiva();
		rel.queryRecordByIdRelStabLp(db, Integer.parseInt(idRelazione));
		
		Stabilimento stab = new Stabilimento(db, rel.getIdStabilimento());

		int idLinea = rel.getIdAttivita();
		SuapMasterListLineaAttivita linea = new SuapMasterListLineaAttivita(db, idLinea);
			
			ArrayList<SuapMasterListSchedaAggiuntiva> listaSchedeAggiuntiveLinea =  new ArrayList<SuapMasterListSchedaAggiuntiva>();
			listaSchedeAggiuntiveLinea = SuapMasterListSchedaAggiuntiva.getElencoSchedeAggiuntive(db, rel.getId_rel_stab_lp(), linea);
			listaSchede.addAll(listaSchedeAggiuntiveLinea);
		
		context.getRequest().setAttribute("listaSchede", listaSchede);
		
		
		HashMap<Integer, SintesisProdotto> hashProdotti = new HashMap<Integer, SintesisProdotto>();

			ArrayList<SintesisProdotto> listaProdottiLinea = SintesisProdotto.getListaProdottiPerLinea(db, rel.getId_rel_stab_lp(), "opu");
			
			for (int j= 0; j< listaProdottiLinea.size(); j++){
				SintesisProdotto prod = (SintesisProdotto) listaProdottiLinea.get(j);
				hashProdotti.put(prod.getIdProdotto(), prod);
			}
		
		context.getRequest().setAttribute("hashProdotti", hashProdotti);
			
		context.getRequest().setAttribute("Stabilimento", stab);
		context.getRequest().setAttribute("Relazione", rel);


	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	context.getRequest().setAttribute("esito", (String)context.getRequest().getAttribute("esito"));
	
	return "ListaProdottiOK";
}


public String executeCommandSalvaProdotti(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "sintesis-prodotti-edit")) {
		return ("PermissionError");
	}
	
	int totSchede = Integer.parseInt(context.getRequest().getParameter("totSchede"));
	int idStabilimento = Integer.parseInt(context.getRequest().getParameter("idStabilimento"));
	int idRelazione = Integer.parseInt(context.getRequest().getParameter("idRelazione"));

	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
	for (int i = 0; i< totSchede; i++){
		
		int idProdotto = Integer.parseInt(context.getRequest().getParameter("idProdotto_"+i));
		boolean checked = false;
		if (context.getRequest().getParameter("cb_prod_"+i)!=null)
			checked = true;
		String valore = context.getRequest().getParameter("valore_prod_"+i);
		
		SintesisProdotto prod = new SintesisProdotto();
		prod.setIdProdotto(idProdotto);
		prod.setIdLinea(idRelazione);
		prod.setIdStabilimento(idStabilimento);
		prod.setChecked(checked);
		prod.setValoreProdotto(valore);
		prod.setOrigine("opu");
		prod.gestisciUpdate(db);
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
//	context.getRequest().setAttribute("id", String.valueOf(idStabilimento));
//	context.getRequest().setAttribute("esito", "ok");
//
//	return executeCommandListaProdotti(context);
	
	context.getRequest().setAttribute("idRelazione", String.valueOf(idRelazione));
	context.getRequest().setAttribute("esito", "ok");

	return executeCommandListaProdottiLinea(context);

}


public String executeCommandPrepareModificaLineeCampiEstesi2(ActionContext context) {

	Connection db = null;
	String tempStabId = context.getRequest().getParameter("stabId");
	Integer stabid = null;

	if (tempStabId != null) {
		stabid = Integer.parseInt(tempStabId);
	}

	try {

		db = this.getConnection(context);	
		Stabilimento newStabilimento = new Stabilimento(db,  stabid);
		context.getRequest().setAttribute("newStabilimento",newStabilimento);
		caricaLineeCampiEstesi2(context, db, newStabilimento);
		
		LookupList lookup_tipo_alimento_distributore = new LookupList(db,"lookup_tipo_alimento_distributore");
		context.getRequest().setAttribute("TipoAlimentoDistributore", lookup_tipo_alimento_distributore);

		LookupList lookup_tipo_mobili = new LookupList(db,"lookup_tipo_mobili");
		context.getRequest().setAttribute("TipoMobili", lookup_tipo_mobili);
		
		LookupList lookup_tipo_sex = new LookupList(db,"sex_table_lookup");
		context.getRequest().setAttribute("TipoSex", lookup_tipo_sex);

	} catch (Exception errorMessage) {
		context.getRequest().setAttribute("Error", errorMessage);
		errorMessage.printStackTrace();
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	return getReturn(context, "PrepareModificaLineeCampiEstesi");
}

private void caricaLineeCampiEstesi2(ActionContext context, Connection db, Stabilimento newStabilimento) throws SQLException{
	LinkedHashMap<Integer,LinkedHashMap<Integer,LineeCampiEstesi>> lineeCampiEstesi = newStabilimento.getCampiEstesiLinee2(db);
	context.getRequest().setAttribute("LineeCampiEstesi", lineeCampiEstesi);

}

public String executeCommandUpdateLineeCampiEstesi2(ActionContext context) {

	Connection db = null;
	String tempStabId = context.getRequest().getParameter("stabId");
	Integer stabid = null;

	if (tempStabId != null) {
		stabid = Integer.parseInt(tempStabId);
	}

	try {

		db = this.getConnection(context);	
		Stabilimento newStabilimento = new Stabilimento(db,  stabid);
		context.getRequest().setAttribute("newStabilimento",newStabilimento);

		LinkedHashMap<Integer,LinkedHashMap<Integer,LineeCampiEstesi>> lineeCampiEstesiOld = newStabilimento.getCampiEstesiLinee2(db);


		Iterator<Integer> itKeySet = lineeCampiEstesiOld.keySet().iterator();
		while (itKeySet.hasNext())
		{


			int idRelStabLp = itKeySet.next();
			LinkedHashMap listaCampiPerLinea = lineeCampiEstesiOld.get(idRelStabLp);
			Set<Entry>  entries = listaCampiPerLinea.entrySet();

			for (Entry elemento : entries) 
			{
				
					LineeCampiEstesi campo = (LineeCampiEstesi) elemento.getValue();
					
					if (campo.getId_rel_stab_lp()<=0)
						campo.setId_rel_stab_lp(idRelStabLp);
					int id = campo.getIdValore();
					String nuovoValore = context.getRequest().getParameter("input_"+id);
					String idValore = String.valueOf(id);
					campo.gestisciUpdate(nuovoValore, Integer.parseInt(idValore),  getUserId(context), db);
				
			}
		}


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "UpdateLineeCampiEstesi");
	}




}