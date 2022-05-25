/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.actions;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.Suap;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUploadSuap;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato;
import org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineeMobiliHtmlFields;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.suap.base.BeanPerXmlRichiesta;
import org.aspcfs.modules.suap.base.DatiMobile;
import org.aspcfs.modules.suap.base.LineaProduttiva;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.suap.base.LineaProduttivaList;
import org.aspcfs.modules.suap.base.Operatore;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.suap.base.SoggettoFisico;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.modules.suap.base.StabilimentoList;
import org.aspcfs.modules.suap.base.Storico;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.Canile;
import org.aspcfs.utils.DbiBdu;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
 
import org.json.JSONArray;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;



public class StabilimentoAction extends CFSModule {

	
	private static final int MAX_SIZE_REQ = 50000000;

	Logger logger = Logger.getLogger(StabilimentoAction.class);
	public static final int INDIRIZZO_SEDE_MOBILE = -1 ;

	
	public static final int SCIA_NUOVO_STABILIMENTO = 1 ; 
	public static final int SCIA_AMPLIAMENTO = 2 ; 
	public static final int SCIA_CESSAZIONE= 3 ; 
	public static final int SCIA_VARIAZIONE_TITOLARITA= 4 ; 
	public static final int SCIA_SOSPENSIONE = 5 ; 
	public static final int SCIA_MODIFICA_STATO_LUOGHI = 6 ;





	Integer[] array = null;
	List<String> uniquePropertyValues = null;
	int k = -1;

	public static final int CONTEXT_GISA = 1 ;
	public static final int CONTEXT_SUAP = 2 ;

	private int getContext(ActionContext context)
	{
		String contesto = (String)context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
		if (contesto!=null && contesto.equals("_ext"))
			return CONTEXT_SUAP;
		return CONTEXT_GISA;
	}

	private String getContextReturn(ActionContext context,String forward)
	{
		if (getContext(context)== CONTEXT_SUAP)
			return getReturn(context, forward+"Suap");
		return getReturn(context, forward+"Gisa");
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
		//return  executeCommandSearchForm(context);

	}

	
	public String executeCommandOttieniCampiEstesi(ActionContext context)
	{
		String viewName = null;
		System.out.println(context.getRequest().getParameter("idLinea"));
		Integer idAttivita = Integer.parseInt(context.getRequest().getParameter("idLinea"));
		Connection db = null;
		try
		{
			db = getConnection(context);
			//devo costruire la lista dei campi di input del form, a seconda della linea di attivita
			ArrayList<LineeMobiliHtmlFields> inputs = ottieniInputsPerLinea(db,idAttivita);
			context.getRequest().setAttribute("inputs",inputs);
			context.getRequest().setAttribute("idLinea", idAttivita);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context,db);
		}
		
		
		viewName = "suapJsonOttenimentoCampiEstesi";
		
		return viewName;
	}
	
	
 
	
	public String executeCommandOttieniCampiEstesiAnagraficiConValori(ActionContext context) /*da opu, per le linee gia' validate */
	{
		
		
		
		
		String viewName = null;
		Connection db = null;
		try
		{
			db = getConnection(context);
			
			//System.out.println(context.getRequest().getParameter("idLinea"));
			Integer idRelStabLpOpu = Integer.parseInt(context.getRequest().getParameter("idStabRelLpOpu"));
			
			
			//devo costruire la lista dei campi di input del form, a seconda della linea di attivita
			ArrayList<LineeMobiliHtmlFields> inputs = ottieniInputsPerLineaAnagraficiConValori(db,idRelStabLpOpu);
			
			context.getRequest().setAttribute("idRelStabLpOpu",idRelStabLpOpu);
			context.getRequest().setAttribute("inputs",inputs);
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context,db);
		}
		
		viewName = "suapJsonOttenimentoCampiEstesi";
		return viewName;
	}
	
	
	
	/*questo metodo mette tutti i campi estesi, di tipo anagrafica, per tutte le linee gia' validate di uno stabilimento 
	 * e serve per capire, clientside, se uno stabilimento ha almeno una linea con campi estesi di tipo anagrafico (da opu)
	 */
	public String executeCommandOttieniEsistenzaAlmenoUnCampoEstesoAnagrafica(ActionContext context)
	{
		Integer idStab = Integer.parseInt(context.getRequest().getParameter("idStab"));
		Connection db = null;
		ArrayList<LineeMobiliHtmlFields> allCampiAnags = new ArrayList<LineeMobiliHtmlFields>();
		
		try
		{
			db = getConnection(context);
			org.aspcfs.modules.opu.base.Stabilimento newStabilimento = new org.aspcfs.modules.opu.base.Stabilimento(db,  idStab);
			org.aspcfs.modules.opu.base.LineaProduttivaList linee = newStabilimento.getListaLineeProduttive();
			
			for(Object linea : linee)
			{
				org.aspcfs.modules.opu.base.LineaProduttiva lineaL = (org.aspcfs.modules.opu.base.LineaProduttiva)linea;
				Integer idRelStabLpOpu = lineaL.getId_rel_stab_lp();
				ArrayList<LineeMobiliHtmlFields> campiEstesiAnags = ottieniInputsPerLineaAnagraficiConValori(db, idRelStabLpOpu);
				for(LineeMobiliHtmlFields campoAnag : campiEstesiAnags)
				{
					allCampiAnags.add(campoAnag);
				}
			}
			
			context.getRequest().setAttribute("inputs",allCampiAnags );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context,db);
		}
		
		
		return "suapJsonOttenimentoCampiEstesi";
	}
	

	
	
	public String executeCommandOttieniCampiEstesiExCodiceL30(ActionContext context) /*da opu, per le linee gia' validate, per solo tipo ex code l30 (es codice_stazione) */
	{
		
		
		
		
		String viewName = null;
		Connection db = null;
		try
		{
			db = getConnection(context);
			
			//System.out.println(context.getRequest().getParameter("idLinea"));
			Integer idRelStabLpOpu = Integer.parseInt(context.getRequest().getParameter("idStabRelLpOpu"));
			
			
			//devo costruire la lista dei campi di input del form, a seconda della linea di attivita
			ArrayList<LineeMobiliHtmlFields> inputs = ottieniInputsPerLineaExCodeL30ConValori(db,idRelStabLpOpu);
			
			context.getRequest().setAttribute("idRelStabLpOpu",idRelStabLpOpu);
			context.getRequest().setAttribute("inputs",inputs);
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context,db);
		}
		
		viewName = "suapJsonOttenimentoCampiEstesi";
		return viewName;
	}
	
	
	
	/*questo metodo mette tutti i campi estesi, di tipo ex code l30, per tutte le linee gia' validate di uno stabilimento 
	 * e serve per capire, clientside, se uno stabilimento ha almeno una linea con campi estesi di tipo anagrafico (da opu)
	 */
	public String executeCommandOttieniEsistenzaAlmenoUnCampoEstesoExL30(ActionContext context)
	{
		Integer idStab = Integer.parseInt(context.getRequest().getParameter("idStab"));
		Connection db = null;
		ArrayList<LineeMobiliHtmlFields> allCampiExCodeL30 = new ArrayList<LineeMobiliHtmlFields>();
		
		try
		{
			db = getConnection(context);
			org.aspcfs.modules.opu.base.Stabilimento newStabilimento = new org.aspcfs.modules.opu.base.Stabilimento(db,  idStab);
			org.aspcfs.modules.opu.base.LineaProduttivaList linee = newStabilimento.getListaLineeProduttive();
			
			for(Object linea : linee)
			{
				org.aspcfs.modules.opu.base.LineaProduttiva lineaL = (org.aspcfs.modules.opu.base.LineaProduttiva)linea;
				Integer idRelStabLpOpu = lineaL.getId_rel_stab_lp();
				ArrayList<LineeMobiliHtmlFields> campiEstesiAnags = ottieniInputsPerLineaExCodeL30ConValori(db, idRelStabLpOpu);
				for(LineeMobiliHtmlFields campoAnag : campiEstesiAnags)
				{
					allCampiExCodeL30.add(campoAnag);
				}
			}
			
			context.getRequest().setAttribute("inputs",allCampiExCodeL30 );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(context,db);
		}
		
		
		return "suapJsonOttenimentoCampiEstesi";
	}
	
	
	
	
	
	
	
	
	
	
	public String executeCommandNuovaScia(ActionContext context) throws ParseException {


		String dataRichiesta = context.getParameter("dataRichiesta");
		String comuneSuap = context.getParameter("comuneSuap");
		if (getContext(context)!= CONTEXT_SUAP && comuneSuap!=null && dataRichiesta !=null )
		{
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			user.getUserRecord().getSuap().setDescrizioneComune(comuneSuap.trim());

			Stabilimento st = (Stabilimento)context.getRequest().getAttribute("Stabilimento");
			st.setDataRichiestaSciaAsl(dataRichiesta);

			context.getRequest().setAttribute("Stabilimento",st);

			Connection db = null ;
			try
			{
				db = this.getConnection(context);
				String sql = "select lp.description as provincia , lp.code as idProvincia,c.id as idComune from comuni1 c join lookup_province lp on lp.code=c.cod_provincia::int where c.nome ilike ?";
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, comuneSuap);
				ResultSet rs= pst.executeQuery();
				if (rs.next())
				{	
					Suap suap = new Suap();

					suap.setIdComuneSuap(rs.getInt("idComune"));
					suap.setIdProvinciaSuap(rs.getInt("idProvincia"));
					suap.setDescrizioneProvincia(rs.getString("provincia"));
					suap.setDescrizioneComune(comuneSuap);
					suap.setPec(rs.getString(""));
					
					
					user.getUserRecord().setSuap(suap);

					context.getSession().setAttribute("User",user);

				}


			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				this.freeConnection(context, db);
			}



		}

		return getContextReturn(context,"NuovaScia");
		//return  executeCommandSearchForm(context);

	}

	
	
	public String executeCommandScelta(ActionContext context){

		Connection db = null;
		try {
			db = this.getConnection(context);

			String tipoInserimentoString = context.getRequest().getParameter("tipoInserimento");
			
			String tipoApicoltura = context.getRequest().getParameter("tipoApicoltura");

			
			LookupList tipoScia = new LookupList(db, "suap_lookup_tipo_richiesta");
			context.getRequest().setAttribute("SuapTipoScia", tipoScia);
			
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String dataRichiesta = context.getParameter("dataRichiesta");
			String comuneSuap = context.getParameter("comuneSuap");
			if (getContext(context)!= CONTEXT_SUAP && dataRichiesta!=null && comuneSuap!=null)
			{


				
				user.getUserRecord().getSuap().setDescrizioneComune(comuneSuap.trim());
				Stabilimento st = (Stabilimento)context.getRequest().getAttribute("Stabilimento");
				st.setDataRichiestaSciaAsl(dataRichiesta);

				context.getRequest().setAttribute("Stabilimento",st);


				String sql = "select lp.description as provincia , lp.code as idProvincia,c.id as idComune, c.istat as istatComune from comuni1 c join lookup_province lp on lp.code=c.cod_provincia::int where c.nome ilike ?";
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, comuneSuap);
				ResultSet rs= pst.executeQuery();
				if (rs.next())
				{	
					Suap suap = user.getUserRecord().getSuap();

					
					
					suap.setIdComuneSuap(rs.getInt("idComune"));
					suap.setIdProvinciaSuap(rs.getInt("idProvincia"));
					suap.setDescrizioneProvincia(rs.getString("provincia"));
					suap.setDescrizioneComune(comuneSuap);
					suap.setIstaComune(rs.getString("istatComune"));
					user.getUserRecord().setSuap(suap);

					context.getSession().setAttribute("User",user);

				}

			}


			

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			String fissa = context.getRequest().getParameter("fissa");			
			context.getRequest().setAttribute("fissa", fissa);		
			
			
//			if(fissa.equalsIgnoreCase("api"))
//			{
//				Integer idLineaProduttiva = -1 ;
//				String sql = "select id from master_list_suap where tipo_apicoltura ilike ? and enabled";
//				PreparedStatement pst = db.prepareStatement(sql);
//				pst.setString(1, tipoApicoltura);
//				ResultSet rs = pst.executeQuery();
//				if(rs.next())
//					idLineaProduttiva = rs.getInt(1);
//				context.getRequest().setAttribute("IdLineaProduttiva", new Integer(idLineaProduttiva));
//				
//			}

			int tipoInserimento = -1;
			try {tipoInserimento = Integer.parseInt(tipoInserimentoString);} catch (Exception e){}
			context.getRequest().setAttribute("tipoInserimento", tipoInserimentoString);	
	
			String statoString = context.getRequest().getParameter("stato");
			int stato = -1;
			try {stato = Integer.parseInt(statoString);} catch (Exception e){}
			context.getRequest().setAttribute("stato", stato);


			Stabilimento newStabilimento = new Stabilimento();

			if (tipoInserimento>-1)
				newStabilimento.setTipoInserimentoScia(tipoInserimento);

			int idOperatore = -1;
			idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			context.getRequest().setAttribute("Operatore", operatore);
			newStabilimento.setOperatore(operatore);

			if(stato>-1)
				newStabilimento.setStato(stato);

			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);
			newStabilimento.setDataRichiestaSciaAsl(context.getParameter("dataRichiesta"));
			context.getRequest().setAttribute("newStabilimento",newStabilimento);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getContextReturn(context,"Scelta");



	}

	public String executeCommandAdd(ActionContext context) {

		Connection db = null;
		Stabilimento newStabilimento = null;
		try {
			db = this.getConnection(context);

			String tipoInserimentoSciaString = context.getRequest().getParameter("tipoInserimentoScia");
			int tipoInserimentoScia = -1;
			try {tipoInserimentoScia = Integer.parseInt(tipoInserimentoSciaString); } catch (Exception e){}
			
			newStabilimento = new Stabilimento();
			
			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				if ((Stabilimento) context.getRequest().getAttribute("Stabilimento") != null)
					newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			
			if (tipoInserimentoScia>-1)
				newStabilimento.setTipoInserimentoScia(tipoInserimentoScia);

			String idOperatoreString = context.getRequest().getParameter("idOp");
			if (idOperatoreString == null || idOperatoreString.equals("null"))
				idOperatoreString = (String) context.getRequest().getAttribute("idOp");
			int idOperatore = -1;
			try {idOperatore = Integer.parseInt(idOperatoreString); } catch (Exception e){}

			String statoString = context.getRequest().getParameter("stato");
			int stato = -1;
			try {stato = Integer.parseInt(statoString); } catch (Exception e){}

			String fissa = context.getRequest().getParameter("fissa");	

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			boolean firmaDigitaleObbligatoria = false;
			firmaDigitaleObbligatoria = checkFirmaObbligatoria(db, thisUser.getUserRecord().getSuap().getIstaComune());
			context.getRequest().setAttribute("firmaDigitaleObbligatoria", firmaDigitaleObbligatoria);

			LookupList TipoMobili = new LookupList(db,"lookup_tipo_mobili");
			TipoMobili.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoMobili", TipoMobili);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);

			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			
			String contesto = (String)context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
			/*Elimino tipo impresa bed breakfast per i suap*/
			if (contesto!=null && contesto.equals("_ext") )
				TipoImpresaList.removeElementByLevel(7);
			
			
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);

			LookupList TipoAttivita = new LookupList(db,"opu_lookup_tipologia_attivita");
			if (fissa!=null && fissa.equals("true"))
			{
				TipoAttivita.removeElementByCode(2);
				TipoAttivita.removeElementByCode(3);
				
				
			}
			else if (fissa!=null && fissa.equals("false") )
			{
				TipoAttivita.removeElementByCode(1);
				TipoAttivita.removeElementByCode(3);
				TipoImpresaList.removeElementByLevel(7);
			}
			else
			{
				TipoAttivita.removeElementByCode(1);
				TipoAttivita.removeElementByCode(2);
				//newStabilimento.setTipoAttivita(newStabilimento.TIPO_SCIA_APICOLTURA);
				TipoImpresaList.removeElementByLevel(7);
				
			}
			
			context.getRequest().setAttribute("TipoImpresaList", TipoImpresaList);

			context.getRequest().setAttribute("TipoAttivita", TipoAttivita);
			context.getRequest().setAttribute("fissa", fissa);		

			LookupList TipoCarattere = new LookupList(db,"opu_lookup_tipologia_carattere");
			context.getRequest().setAttribute("TipoCarattere", TipoCarattere);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaList", TipoSocietaList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			c.setInRegione(true);
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

			


			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");

			listaToponimi.buildList(db);

			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);


			

			newStabilimento.setDataRichiestaSciaAsl(context.getParameter("dataRichiesta"));


			if (tipoInserimentoScia>-1)
				newStabilimento.setTipoInserimentoScia(tipoInserimentoScia);

			if (idOperatore==-1)
				idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			context.getRequest().setAttribute("Operatore", operatore);
			newStabilimento.setOperatore(operatore);

			if(stato>-1)
				newStabilimento.setStato(stato);

			if (newStabilimento.getSedeOperativa().getComune() <= 0)
				newStabilimento.getSedeOperativa().setComune(-1);

			
			
			if(context.getParameter("idStabilimentoOpu")!=null)
			{
				org.aspcfs.modules.opu.base.Stabilimento stab = new org.aspcfs.modules.opu.base.Stabilimento();
				stab.queryRecordStabilimento(db, Integer.parseInt(context.getParameter("idStabilimentoOpu")));
				UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

				//Se attività mobile
				if(stab.getTipoAttivita()==2)
				{
					user.getUserRecord().getSuap().setDescrizioneComune(stab.getOperatore().getSedeLegale().getDescrizioneComune().trim());
					user.getUserRecord().getSuap().setIdComuneSuap(stab.getOperatore().getSedeLegale().getComune());
					user.getUserRecord().getSuap().setDescrizioneProvincia(stab.getOperatore().getSedeLegale().getDescrizione_provincia());
					user.getUserRecord().getSuap().setIdProvinciaSuap(stab.getOperatore().getSedeLegale().getIdProvincia());
				}
				else
				{
					user.getUserRecord().getSuap().setDescrizioneComune(stab.getSedeOperativa().getDescrizioneComune().trim());
					user.getUserRecord().getSuap().setIdComuneSuap(stab.getSedeOperativa().getComune());
					user.getUserRecord().getSuap().setDescrizioneProvincia(stab.getSedeOperativa().getDescrizione_provincia());
					user.getUserRecord().getSuap().setIdProvinciaSuap(stab.getSedeOperativa().getIdProvincia());
				}
				String dataRichiesta = context.getParameter("dataRichiesta");
				
				Stabilimento st = (Stabilimento)context.getRequest().getAttribute("Stabilimento");
				st.setDataRichiestaSciaAsl(dataRichiesta);
				context.getRequest().setAttribute("StabilimentoOpu", stab);

			}
			else
			{
				org.aspcfs.modules.opu.base.Stabilimento stab = new org.aspcfs.modules.opu.base.Stabilimento();
				context.getRequest().setAttribute("StabilimentoOpu", stab);


			}
			context.getRequest().setAttribute("newStabilimento",newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LineaProduttivaList lpList = new LineaProduttivaList();
			//lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			context.getRequest().setAttribute("tipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());

			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			if (context.getRequest().getAttribute("newStabilimento")!=null)
				context.getRequest().setAttribute("newStabilimento",context.getRequest().getAttribute("newStabilimento"));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		String operazioneScelta = context.getRequest().getParameter("operazioneScelta");
		context.getRequest().setAttribute("operazioneScelta", operazioneScelta);

	
		if (operazioneScelta==null || !operazioneScelta.equals("new"))
			return getContextReturn(context,"ModifyStabilimento");


		return getContextReturn(context,"AddStabilimento");
		

	}





	private BeanPerXmlRichiesta preparaBeanPerFormClientDaXmlQuery(ActionContext cont, Connection db, int idTipoRichiesta, int idRichiesta) throws Exception {

		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList< HashMap<String,String> > entries = new ArrayList< HashMap<String,String> >();

//		pst = db.prepareStatement("select distinct "+
//				"op.description as Tipo_richiesta, tipo_impresa,tipo_societa, "+
//				"s.id_opu_operatore as id_richiesta, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa,s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, "+
//				"s.prov_sede_legale,s.domicilio_digitale, "+
//				"s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.indirizzo_rapp_sede_legale, "+
//				"s.comune_stab, s.indirizzo_stab, s.cap_stab, s.prov_stab, "+
//				"lta.description as tipo_attivita ,ltc.description as carattere, s.data_inizio_attivita,s.data_fine_attivita,macroarea,aggregazione,linea_attivita "+
//				"from  suap_ric_scia_operatori_denormalizzati_view s "+
//				"join opu_lookup_tipologia_attivita lta on lta.code = s.stab_id_attivita "+
//				"join opu_lookup_tipologia_carattere ltc on ltc.code = s.stab_id_carattere "+
//				"join suap_lookup_tipo_richiesta op on op.code ="+idTipoRichiesta+
//				" where id_opu_operatore="+idRichiesta);
		
		//Tipo_richiesta, tipo_impresa,tipo_societa, id_richiesta, ragione_sociale, partita_iva, codice_fiscale_impresa,comune_sede_legale, istat_legale, cap_sede_legale, prov_sede_legale,domicilio_digitale, cf_rapp_sede_legale, nome_rapp_sede_legale, cognome_rapp_sede_legale, indirizzo_rapp_sede_legale,comune_stab, indirizzo_stab, cap_stab, prov_stab,tipo_attivita ,carattere, data_inizio_attivita,data_fine_attivita,macroarea,aggregazione,linea_attivita 
		
		 
		pst = db.prepareStatement("select * from suap_query_richiesta(?, ?)");
		pst.setInt(1, idTipoRichiesta);
		pst.setInt(2, idRichiesta);

		rs = pst.executeQuery();
		rsmd = rs.getMetaData();

		while(rs.next())
		{
			HashMap<String,String> entry = new HashMap<String,String>();

			for(int i=1;i<=rsmd.getColumnCount(); i++)
			{
				String nomeColonna = rsmd.getColumnName(i);
				String valoreCampo = rs.getString(nomeColonna);
				entry.put(nomeColonna,valoreCampo+"");

			}
			System.out.println();
			for(String k : entry.keySet())
				System.out.print(k+" : " +entry.get(k)+"  ");
			System.out.println();

			entries.add(entry);
		}



		//riempio il bean con il risultato del parsing della query xml
		BeanPerXmlRichiesta beanPerXml = new BeanPerXmlRichiesta();
		beanPerXml.setListaEntries(entries);

		return beanPerXml;




	}

	
	
	
	
	
	public String executeCommandDetails(ActionContext context) {

		Connection db = null;
		Stabilimento newStabilimento = null;
		try {
			
		Integer idSuapRicSciaOperatore = null;
		
		String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}

			Integer stabid = null;

			try {
				stabid = Integer.parseInt(tempStabId);
			} catch (Exception e ){}
			
			String tempAltId = context.getRequest().getParameter("altId");
			
			int altId = -1;
			
			if (tempAltId != null) {
				altId = Integer.parseInt(tempAltId);
			}
			
			if (altId == -1 && stabid == null){
				String tempIdSuapRicSciaOperatore = context.getRequest().getParameter("idSuapRicSciaOperatore");
				try {idSuapRicSciaOperatore = Integer.parseInt(tempIdSuapRicSciaOperatore);	} catch (Exception e ){}
	}

			db = this.getConnection(context);	
			
			if (idSuapRicSciaOperatore!=null && idSuapRicSciaOperatore>0 && altId == -1){
				PreparedStatement pst = db.prepareStatement("select alt_id from suap_ric_scia_stabilimento where trashed_date is null and id_operatore = ?");
				pst.setInt(1, idSuapRicSciaOperatore);
				ResultSet rs = pst.executeQuery();
				while (rs.next())
					altId = rs.getInt("alt_id");
			}

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


			if (altId>0)
				newStabilimento = new Stabilimento(db,  altId, true);
			else
				newStabilimento = new Stabilimento(db,  stabid);
 
			
			LineaProduttivaList listaLinee =  newStabilimento.getListaLineeProduttive();
			
			Iterator<LineaProduttiva> itLp = listaLinee.iterator();
			if( listaLinee.size()>0)
			{
			while (itLp.hasNext())
			{
				LineaProduttiva lp1 = itLp.next();
				if (hasPermission(context, lp1.getPermesso()+"-view"))
				{
					newStabilimento.setFlagProsegubilita(true);
					break ; 
				}
			}
			}
			else
			{
				if (
						(newStabilimento.getTipoInserimentoScia()==Stabilimento.TIPO_SCIA_REGISTRABILI ||  
						newStabilimento.getTipoInserimentoScia()==Stabilimento.TIPO_SCIA_APICOLTURA
						) &&
						hasPermission(context, "suap-asl-validazione-view"))
				{
					
					newStabilimento.setFlagProsegubilita(true);
					
				}
				else
				{
					if (
							(newStabilimento.getTipoInserimentoScia()==Stabilimento.TIPO_SCIA_RICONOSCIUTI )
							&&
							hasPermission(context, "suap-regione-validazione-view"))
					{
							newStabilimento.setFlagProsegubilita(true);
						
					}
				}
				
			}
 
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			Operatore operatore = new Operatore () ;

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			LookupList TipoStruttura = new LookupList(db,
					"lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

 
			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ListaStati", ListaStati);


			LookupList serviziocompetente = new LookupList(db, "lookup_account_stage");
			serviziocompetente.addItem(-1, getSystemStatus(context).getLabel(
					"calendar.none.4dashes"));
			context.getRequest().setAttribute("ServizioCompetente", serviziocompetente);



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

			if (context.getRequest().getParameter("layout")!=null && context.getRequest().getParameter("layout").equals("style"))
				return "DetailsStyleOK";
			
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



	public String executeCommandVerificaEsistenzaStabilimento(ActionContext context) throws IndirizzoNotFoundException
	{
		Connection db = null;
		String numeroRegistrazione = context.getParameter("numeroRegistrazioneVariazione");
		String partitaIva = context.getParameter("partitaIva");
		String tipoAtt = context.getParameter("tipoAttivita");

		String idComune = context.getRequest().getParameter("searchcodeIdComuneStabinput");
		
		
		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");

		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");
		try {

			System.out.println("SUAP : ["+partitaIva+","+idComune+"] CERCO NELLE RICHIESTE");

			db = this.getConnection(context);
			StabilimentoList listaStab = new StabilimentoList();
			listaStab.setTipoAttivita(context.getParameter("tipoAttivita"));
			if(numeroRegistrazione!=null && !"".equals(numeroRegistrazione)){
				listaStab.setNumeroRegistrazione(numeroRegistrazione);
				listaStab.setPartitaIva(partitaIva);
				listaStab.setTipoAttivita(tipoAtt);
				
			}
			else
			{
				listaStab.setPartitaIva(partitaIva);
				listaStab.setComuneSedeProduttiva(idComune);
				listaStab.setTipoAttivita(tipoAtt);
			}
			//			listaStab.setFlagClean(true);
			
			
			if (getContext(context)!=CONTEXT_GISA){
				listaStab.setIdComuneRichiesta(user.getUserRecord().getSuap().getIdComuneSuap());
				listaStab.buildList(db);
			}

			

			if (getContext(context)==CONTEXT_GISA){ //SE SONO IN GISA, ESTENDO LA RICERCA
				
					System.out.println("SUAP: ["+partitaIva+","+idComune+"] CERCO IN OPU");
					listaStab = newStabilimento.getFromOpu(db, partitaIva, idComune,numeroRegistrazione,context.getParameter("tipoAttivita"));
				
				if (listaStab.size()==0){ //CERCO DA ORGANIZATION
					System.out.println("SUAP: ["+partitaIva+","+idComune+"] CERCO IN ORGANIZATION");
					listaStab = newStabilimento.getFromOrganization(db, partitaIva, idComune);
				}
			}

			List<Stabilimento> lista = new ArrayList<Stabilimento>();
			for (Object c : listaStab)
			{
				boolean trovato = false;
				Stabilimento temp = (Stabilimento)c ;

				for (Stabilimento operatore : lista)
				{

					if (temp.compareTo(operatore,temp)==0)
					{
						trovato = true ;
						break  ;

					}

				}

				if (trovato==false)
				{
					lista.add(temp);

				}



			}




			context.getRequest().setAttribute("JsonStabilimentoListSuap",lista);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "JsonStabilimentoListSuap" ;
	}



	public String executeCommandVerificaEsistenza(ActionContext context)
			throws SQLException, IndirizzoNotFoundException,Exception {

		Connection db = null;

		// Integer orgId = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		String partitaIva = context.getRequest().getParameter("partitaIva");
		try {
			//				SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);
			OperatoreAction actionOperatore = new OperatoreAction();

			System.out.println("SUAP: ["+partitaIva+"] CERCO NELLE RICHIESTE");

			String esitoInserimentoOperatore = actionOperatore.executeCommandVerificaEsistenza(context,db);

			if(esitoInserimentoOperatore.equals(Stabilimento.OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE))
			{
				// ESISTE UNA IMPRESA CON DATI DIVERSI

				System.out.println("SUAP: ["+partitaIva+"] TROVATO NELLE RICHIESTE");
				List<Operatore> listaOperatori = (List<Operatore>)context.getRequest().getAttribute("ListaOperatori");
				newStabilimento.setListaOperatori(listaOperatori);
				newStabilimento.setCodiceErroreSuap("2");
				newStabilimento.setErroreSuap("Selezionare una delle richieste Presenti");
				context.getRequest().setAttribute("StabilimentoSuap", newStabilimento);
				return "JsonSuapStabilimento";
			}
			else
			{

				if (getContext(context)==CONTEXT_SUAP){ //SE SONO DAL SUAP MI FERMO QUI
					newStabilimento.setCodiceErroreSuap("0");
					newStabilimento.setErroreSuap("IMPRESA NON ESISTENTE");
					context.getRequest().setAttribute("StabilimentoSuap", newStabilimento);
				}
				else { //SE SONO DA GISA
					Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");

					List<Operatore> listaOperatori = null;

					//CERCO IN OPU
					System.out.println("SUAP: ["+partitaIva+"] CERCO IN OPU");
					listaOperatori = newOperatore.getFromOpu(db);
					if (listaOperatori.size()==0) {//CERCO IN ORGANIZATION
						System.out.println("SUAP: ["+partitaIva+"] CERCO IN ORGANIZATION");
						listaOperatori = newOperatore.getFromOrganization(db);
					}

					if (listaOperatori.size()>0){
						System.out.println("SUAP: ["+partitaIva+"] TROVATO IN OPU/ORGANIZATION");
						newStabilimento.setListaOperatori(listaOperatori);
						newStabilimento.setCodiceErroreSuap("2");
						newStabilimento.setErroreSuap("Selezionare una delle richieste Presenti");
						context.getRequest().setAttribute("StabilimentoSuap", newStabilimento);
						return "JsonSuapStabilimento";
					}
					else {
						newStabilimento.setCodiceErroreSuap("0");
						newStabilimento.setErroreSuap("IMPRESA NON ESISTENTE");
						context.getRequest().setAttribute("StabilimentoSuap", newStabilimento);
					}
				}

			}
		}
		catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return "JsonSuapStabilimento";

		} finally {
			this.freeConnection(context, db);

		}
		return "JsonSuapStabilimento" ;


	}

	
	
	
public ArrayList<LineeMobiliHtmlFields> ottieniInputsPerLinea(Connection db,Integer idAttivita) {
		
		ArrayList<LineeMobiliHtmlFields> toRet = new ArrayList<LineeMobiliHtmlFields>();
		
		//Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		try
		{
			//db = getConnection(cont);
			
			pst = db.prepareStatement("select * from linee_mobili_html_fields where id_linea = ? and gestione_interna = true order by ordine asc");
			pst.setInt(1,idAttivita);
			System.out.println("LA QUERY PER OTTENERE CAMPI ESTESI E'"+pst);
			rs = pst.executeQuery();
			while(rs.next())
			{
				System.out.println("TROVATO INPUT");
				LineeMobiliHtmlFields lm = new LineeMobiliHtmlFields();
				lm.buildRecordNoValoriCampi(db, rs);
				toRet.add(lm);
			}
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			//freeConnection(cont, db);
			try {if(rs!=null) rs.close();} catch(Exception ex) {}
			try {if(pst!=null) pst.close();} catch(Exception ex) {}
		}
		
		return toRet;
	}



 public ArrayList<LineeMobiliHtmlFields> ottieniInputsPerLineaAnagraficiConValori(Connection db, Integer idRelStabLpInOpu) {
	
	ArrayList<LineeMobiliHtmlFields> toRet = new ArrayList<LineeMobiliHtmlFields>();
	
//	Connection db = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	
	try
	{
//		db = getConnection(cont);
		StringBuffer query = new StringBuffer("select * from linee_mobili_html_fields fields "
				+"join linee_mobili_fields_value values on fields.id = values.id_linee_mobili_html_fields"
				+ " where fields.nome_campo is not null and (values.id_opu_rel_stab_linea = ? or values.id_rel_stab_linea = ? ) ");
		
		/*query.append(" and ( ");
		  query.append("lower(fields.nome_campo) ilike '%nome%' "); /*nome e cognome */
		/*query.append("or lower(fields.nome_campo) ilike '%codice_fiscale%' "); /*cf */
		/*query.append("or lower(fields.nome_campo) ilike '%comune%' ");
		query.append("or lower(fields.nome_campo) ilike '%nazione%' ");
		query.append("or lower(fields.nome_campo) ilike '%provincia%' ");
		query.append("or lower(fields.nome_campo) ilike '%nome%' ");
		query.append("or lower(fields.nome_campo) ilike '%nome%' ");
		query.append("or lower(fields.nome_campo) ilike '%nome%' ");
		query.append("or lower(fields.nome_campo) ilike '%sesso%' ");
		query.append("or lower(fields.nome_campo) ilike '%nascita%' ");*/
		//query.append(" ) order by fields.ordine ");
		query.append("  order by fields.ordine ");
		pst = db.prepareStatement(query.toString());
		pst.setInt(1, idRelStabLpInOpu); /*idOpuRelStabLp */
		pst.setInt(2, idRelStabLpInOpu); /*idOpuRelStabLp */
		rs = pst.executeQuery();
		
		
		System.out.println("LA QUERY PER OTTENERE CAMPI ESTESI E'"+pst);
		rs = pst.executeQuery();
		while(rs.next())
		{
			System.out.println("TROVATO INPUT");
			LineeMobiliHtmlFields lm = new LineeMobiliHtmlFields();
			lm.buildRecordConValore(db, rs,idRelStabLpInOpu);
			toRet.add(lm);
		}
		
	}
	
	
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
//		freeConnection(cont, db);
		try {if(rs!=null) rs.close();} catch(Exception ex) {}
		try {if(pst!=null) pst.close();} catch(Exception ex) {}
	}
	
	return toRet;
}
	
 
 
 public ArrayList<LineeMobiliHtmlFields> ottieniInputsPerLineaExCodeL30ConValori(Connection db, Integer idRelStabLpInOpu) {
		
		ArrayList<LineeMobiliHtmlFields> toRet = new ArrayList<LineeMobiliHtmlFields>();
		
//		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		try
		{
//			db = getConnection(cont);
			StringBuffer query = new StringBuffer("select * from linee_mobili_html_fields fields "
					+"join linee_mobili_fields_value values on fields.id = values.id_linee_mobili_html_fields"
					+ " where fields.nome_campo is not null and (values.id_opu_rel_stab_linea = ? or values.id_rel_stab_linea = ? ) ");
			
			//query.append(" and ( ");
			//query.append("lower(fields.nome_campo) ilike '%codice_stazione%' ");  
			//query.append("or lower(fields.nome_campo) ilike '%codice_recapit%' "); 
			//query.append(" ) order by fields.ordine ");
			query.append(" order by fields.ordine ");
			pst = db.prepareStatement(query.toString());
			pst.setInt(1, idRelStabLpInOpu); /*idOpuRelStabLp */
			pst.setInt(2, idRelStabLpInOpu); /*idOpuRelStabLp */
			rs = pst.executeQuery();
			
			
			System.out.println("LA QUERY PER OTTENERE CAMPI ESTESI E'"+pst);
			rs = pst.executeQuery();
			while(rs.next())
			{
				System.out.println("TROVATO INPUT");
				LineeMobiliHtmlFields lm = new LineeMobiliHtmlFields();
				lm.buildRecordConValore(db, rs,idRelStabLpInOpu);
				toRet.add(lm);
			}
			
		}
		
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
//			freeConnection(cont, db);
			try {if(rs!=null) rs.close();} catch(Exception ex) {}
			try {if(pst!=null) pst.close();} catch(Exception ex) {}
		}
		
		return toRet;
	}

	private int caricaAllegati(ActionContext context, int altId, MultipartRequest parts, boolean obbligoCheckFile) throws SQLException, IOException{

		Enumeration<String> listaFile = parts.getFileNames();
		
		System.out.println("CARICA ALLEGATI LISTA FILE ALLEGATI ");
		while(listaFile.hasMoreElements())
		{
			
			
			String nomeFile = listaFile.nextElement();
			System.out.println("CARICA ALLEGATI LISTA FILE ALLEGATI "+nomeFile);

			if ((Object)  parts.getFile(nomeFile) instanceof File) {
				System.out.println("#####CARICA ALLEGATI ENTRATO NELL'IF INSTANCE OF");
				File file1 = (File) parts.getFile(nomeFile);
				
				GestioneAllegatiUploadSuap verbali = new GestioneAllegatiUploadSuap();
				
				verbali.setOp("suap");
				verbali.setAltId(altId);
				verbali.setSubject( parts.getParameter("subject"+nomeFile.substring(4)));
				System.out.println("#####CARICA ALLEGATI ENTRATO NELL'IF INSTANCE OF SUBSTRINGGG");

				verbali.setTipoAllegato("AllegatoScia");
				verbali.setFileDaCaricare(file1);
				verbali.setObbligoCheckFile(obbligoCheckFile);
				
				
				try {
					String esitoo=	verbali.allegaFile(context,parts);
					System.out.println("#####CARICA ALLEGATI ENTRATO NELL'IF INSTANCE OF ESITOOO  "+esitoo);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		return 1;
	}
	
	private HashMap<String, Boolean> controllaAllegati(ActionContext context, MultipartRequest parts) throws IllegalArgumentException, IOException, IllegalStateException, SQLException, ServletException, FileUploadException{
		
		HashMap<String, Boolean> esitoFile = new HashMap<String, Boolean>();
		
		Enumeration<String> listaFile = parts.getFileNames();
		while(listaFile.hasMoreElements())
		{
			String nomeFile = listaFile.nextElement();
			if ((Object)  parts.getFile(nomeFile) instanceof File) {
				File file1 = (File) parts.getFile(nomeFile);
				GestioneAllegatiUploadSuap verbali = new GestioneAllegatiUploadSuap();
				verbali.setOp("suap");
				verbali.setFileDaCaricare(file1);
				verbali.setSubject( parts.getParameter("subject"+nomeFile.substring(4)));
				verbali.setTipoAllegato("AllegatoScia");
				
				try {
					String[] esito = new String[2];
					esito = verbali.controllaFile(context,parts);
					String oggettoReturn = esito[0];
					boolean esitoReturn = false;
					if (esito[1]!=null && esito[1].equals("true"))
						esitoReturn = true;
					esitoFile.put(oggettoReturn, esitoReturn);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	return esitoFile;
	}

	
 
	
	
	public String executeCommandInsertSuap(ActionContext context)
			{


		Connection db = null;
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		String comune ="" ;
		
		File zipped = null;
		boolean exist = false ;
		File[] tempAllFiles = null;
		boolean recordInserted = false;
		boolean isValid = false;
		try {
			
	
	
//			<Connector port="80" protocol="HTTP/1.1"
//		               connectionTimeout="200000"
//		               redirectPort="9443" />
			
		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
		int idUser = user.getUserId();
//        String filePath = this.getPath(context, "allegatiscia");
//        File theDir = new File(filePath);
//        theDir.mkdirs();
		
        String filePath = getWebInfPath(context,"tmp_allegati_scia");
        
//        File filePathF = new File(filePath);
//		if(!filePathF.exists())
//		{
//			filePathF.mkdirs();
//		}
		
		MultipartRequest multi = new MultipartRequest(context.getRequest(),filePath,MAX_SIZE_REQ,"UTF-8"); 
		
		// Integer orgId = null;
		//
		
		newStabilimento.setUserId(idUser);

		Organization OrgImport = null;
		
			//				SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);

			
			// CONTROLLO ESISTENZA STABILIMENTO
			boolean aslInPossessoDocumenti = multi.getParameter("asl_in_possesso_documenti") != null;
			String controlloEsistenzaStabilimento = multi.getParameter("controlloEsistenzaStabilimento");
			System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO - controlloEsistenzaStabilimento "+controlloEsistenzaStabilimento);

			if (getContext(context)==CONTEXT_GISA && (controlloEsistenzaStabilimento==null || !controlloEsistenzaStabilimento.equals("ok")))
			{ 
				
				if (multi.getParameter("methodRequest").equals("new")) 
				{
					boolean esitoEsistenzaStabilimento = false;
					
					System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO - tipoAttivita "+multi.getParameter("tipoAttivita"));
	
					int _tipoatt = Integer.parseInt(multi.getParameter("tipoAttivita"));
					String _piva = multi.getParameter("partitaIva");
					String _comune = multi.getParameter("comuneSuap");
					String _via= multi.getParameter("viaStabinput");
					String _civico=multi.getParameter("civicoSedeOperativa");
					
					
					System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO - idLineaProduttiva "+multi.getParameter("idLineaProduttiva"));
	
					int _linea = Integer.parseInt(multi.getParameter("idLineaProduttiva"));
					
					esitoEsistenzaStabilimento = newStabilimento.getEsistenzaInserimento(db,idUser, _tipoatt, _piva ,_comune,_via ,_civico, _linea);
					System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO - esitoEsistenzaStabilimento "+esitoEsistenzaStabilimento);
	
					if (esitoEsistenzaStabilimento){
						newStabilimento.setCodiceErroreSuap("3");
						String erroreEsistenzaStabilimento="ATTENZIONE: Lo stabilimento esiste nel sistema.";
						newStabilimento.setErroreSuap(erroreEsistenzaStabilimento);
						return "JsonSuapStabilimento";
					}
				}
			}

			System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO - CONTROLLO ALLEGATI SCIA");

			/*
			//CONTROLLO ALLEGATI SCIA
			String obbligoCheckFileString = multi.getParameter("checkObbligatorio");
			boolean obbligoCheckFile = false;
			if (obbligoCheckFileString!=null)
				obbligoCheckFile=true;
			
			if (obbligoCheckFile){
				System.out.println("CONTROLLO ALLEGATI SCIA");
				HashMap <String, Boolean> esitoCheckFile = controllaAllegati(context,multi);
				boolean esitoGlobaleCheckFile = true;
				String erroreCheckFile="Controllo Allegati: \n";
				for (Map.Entry<String, Boolean> entry : esitoCheckFile.entrySet()) {
				    String oggettoSingolo = entry.getKey();
				    Boolean esitoSingolo = entry.getValue();
				    if (!esitoSingolo){
				    	esitoGlobaleCheckFile = false;
				    	erroreCheckFile+=oggettoSingolo+" : Firma digitale non valida.\n";			    	
				    }
				}
				System.out.println("FINE CONTROLLO ALLEGATI SCIA");
				
				if (!esitoGlobaleCheckFile){
					newStabilimento.setCodiceErroreSuap("1");
					newStabilimento.setErroreSuap(erroreCheckFile);
					return "JsonSuapStabilimento";
				}
			//FINE CONTROLLO ALLEGATI SCIA
			}
			*/
			
			System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO ");
			
			
			((Operatore)context.getRequest().getAttribute("Operatore")).setAslPossessoDocumenti(aslInPossessoDocumenti);
			
			
			String retValue = this.insertNuovoStabilimento(db, newStabilimento, context,multi) ;
			
			System.out.println("CARICA VERBALI --> VALORE DI ALT_ID "+newStabilimento.getAltId());
			System.out.println("INSERIMENTO RICHIESTA NUOVO STABILIMENTO - CARICA ALLEGATI ");

			//caricaAllegati(context,newStabilimento.getAltId(),multi, obbligoCheckFile);
			 

			if (retValue!=null)
			{

					newStabilimento = (Stabilimento)context.getRequest().getAttribute("Stabilimento"); 
					//-----GESTIONE DELL'INVIO DELLA MAIL PEC CON LO ZIP ALLEGATO

					/*TOLGO ALLEGATI */
					//File[] allegatiDaDocumentale = scaricaFilesDaDocumentale(context,newStabilimento.getAltId());
					File[] allegatiDaDocumentale = new File[0];/*TOLTI ALLEGATI */
					
					Date data = new Date();
					DateFormat form = new SimpleDateFormat("YYYYMMddHHmmss");
					//genero il file xml dal db e scarico gli allegati dal documentale
					String nomeFileXmlDaGenerare = "richiesta.xml";/*newStabilimento.getOperatore().getPartitaIva()+form.format(data)+".xml";*/

					//File xmlDaDb = generaFileXmlDaDbSecondoSchemaXSDPerNuovaRichiesta( context, db,newStabilimento.getIdOperatore(), newStabilimento.getOperatore().getIdOperazione(), nomeFileXmlDaGenerare,allegatiDaDocumentale );
					Boolean[] isValidXml = new Boolean[]{false};
					String destPath = getWebInfPath(context, "tmp_attachment_mail");
					File xmlDaDb = new XSDSchemaRestGisaGeneratore().generaFileXmlSecondoSchema (db, destPath, new int[]{newStabilimento.getOperatore().getIdOperazione(), newStabilimento.getIdOperatore()}, nomeFileXmlDaGenerare ,allegatiDaDocumentale, "RICHIESTA", isValidXml);
					//estraggo l'id stabilimento che mi serve per richiedere la lista dei file dal documentale (gli ritorno sempre il primo della lista poiche
					//unico stabilimento per richiesta SEMPRE)

					//li metto tutti assieme in un array di File
					tempAllFiles = new File[allegatiDaDocumentale.length+1];
					for(int i=0;i<allegatiDaDocumentale.length;i++)
					{
						tempAllFiles[i] = allegatiDaDocumentale[i];
					}
					tempAllFiles[allegatiDaDocumentale.length] = xmlDaDb;
					//li zippo, il nome del file sara PIVAYYYYMMDDHHMMSS

					String nomeZipDaGenerare = newStabilimento.getOperatore().getPartitaIva()+form.format(data)+".zip";

					zipped = zippaFiles(context,tempAllFiles,nomeZipDaGenerare);


					//e li invio per mail
					comune = getComuneRichiedente(db,newStabilimento.getOperatore().getIdOperatore());
					inviaMailPec(context,db,newStabilimento.getIdOperatore(),newStabilimento.getOperatore().getIdOperazione(),zipped,comune);
					

				Stabilimento newStabilimentoT = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
				int idTipoRichiesta = newStabilimentoT.getOperatore().getIdOperazione();
				int idRichiesta = newStabilimentoT.getOperatore().getIdOperatore();

				BeanPerXmlRichiesta beanPerXmlRichiesta = preparaBeanPerFormClientDaXmlQuery(context,db,idTipoRichiesta,idRichiesta);
				if(beanPerXmlRichiesta.getListaEntries()!= null && beanPerXmlRichiesta.getListaEntries().size()>0)
				{
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					beanPerXmlRichiesta.getListaEntries().add(entryEsito);
				}
				//lo metto nell'oggetto che verra trasformato in json

				newStabilimentoT.setBeanPerXml(beanPerXmlRichiesta);
				context.getRequest().setAttribute("Stabilimento",newStabilimentoT);


				return retValue;
			}
			else
			{
				
				BeanPerXmlRichiesta beanPerXml = new BeanPerXmlRichiesta();
				ArrayList< HashMap<String,String> > entries = new ArrayList< HashMap<String,String> >();
				
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					entries.add(entryEsito);
					beanPerXml.setListaEntries(entries);

			}
		}

		catch(EccezioneGenerazioneXml e)
		{
			Stabilimento newStabilimentoT = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			int idTipoRichiesta = newStabilimentoT.getOperatore().getIdOperazione();
			int idRichiesta = newStabilimentoT.getOperatore().getIdOperatore();

			BeanPerXmlRichiesta beanPerXmlRichiesta=null;
			try {
				beanPerXmlRichiesta = preparaBeanPerFormClientDaXmlQuery(context,db,idTipoRichiesta,idRichiesta);
				if(beanPerXmlRichiesta.getListaEntries()!= null && beanPerXmlRichiesta.getListaEntries().size()>0)
				{
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					beanPerXmlRichiesta.getListaEntries().add(entryEsito);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//lo metto nell'oggetto che verra trasformato in json

			newStabilimento.setBeanPerXml(beanPerXmlRichiesta);
			newStabilimento.setErroreSuap(newStabilimento. getErroreSuap()+ " - Si e verificato un problema nella generazione dell'xml ");
			logger.warn("##"+getContext(context)+"## Errore nella generazione dell'xml");

		}
		catch (AddressException e) 
		{
			Stabilimento newStabilimentoT = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			int idTipoRichiesta = newStabilimentoT.getOperatore().getIdOperazione();
			int idRichiesta = newStabilimentoT.getOperatore().getIdOperatore();

			
			BeanPerXmlRichiesta beanPerXmlRichiesta=null;
			try {
				beanPerXmlRichiesta = preparaBeanPerFormClientDaXmlQuery(context,db,idTipoRichiesta,idRichiesta);
				if(beanPerXmlRichiesta.getListaEntries()!= null && beanPerXmlRichiesta.getListaEntries().size()>0)
				{
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					beanPerXmlRichiesta.getListaEntries().add(entryEsito);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//lo metto nell'oggetto che verra trasformato in json

			newStabilimento.setBeanPerXml(beanPerXmlRichiesta);
			newStabilimento.setErroreSuap(newStabilimento. getErroreSuap()+ " - Si e verificato un problema nell invio della mail ");
			logger.warn("##"+getContext(context)+"## Errore Nell'invio della mail : Indirizzo Pec di destinazione non specificato Su Comune "+comune);

		} 
		catch (MessagingException e) 
		{
			Stabilimento newStabilimentoT = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			int idTipoRichiesta = newStabilimentoT.getOperatore().getIdOperazione();
			int idRichiesta = newStabilimentoT.getOperatore().getIdOperatore();

			
			BeanPerXmlRichiesta beanPerXmlRichiesta=null;
			try {
				beanPerXmlRichiesta = preparaBeanPerFormClientDaXmlQuery(context,db,idTipoRichiesta,idRichiesta);
				if(beanPerXmlRichiesta.getListaEntries()!= null && beanPerXmlRichiesta.getListaEntries().size()>0)
				{
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					beanPerXmlRichiesta.getListaEntries().add(entryEsito);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//lo metto nell'oggetto che verra trasformato in json

			newStabilimento.setBeanPerXml(beanPerXmlRichiesta);
			newStabilimento.setErroreSuap(newStabilimento. getErroreSuap()+ "- Si e verificato un problema nell invio della mail ");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			Stabilimento newStabilimentoT = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			
			int idTipoRichiesta = newStabilimentoT.getOperatore().getIdOperazione();
			int idRichiesta = newStabilimentoT.getOperatore().getIdOperatore();

			
			BeanPerXmlRichiesta beanPerXmlRichiesta=null;
			try {
				beanPerXmlRichiesta = preparaBeanPerFormClientDaXmlQuery(context,db,idTipoRichiesta,idRichiesta);
				if(beanPerXmlRichiesta.getListaEntries()!= null && beanPerXmlRichiesta.getListaEntries().size()>0)
				{
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					beanPerXmlRichiesta.getListaEntries().add(entryEsito);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//lo metto nell'oggetto che verra trasformato in json

			newStabilimento.setBeanPerXml(beanPerXmlRichiesta);
			newStabilimento.setErroreSuap(newStabilimento. getErroreSuap()+ "- Si e verificato un Errore sugli allegati Scia");
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			Stabilimento newStabilimentoT = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
			int idTipoRichiesta = newStabilimentoT.getOperatore().getIdOperazione();
			int idRichiesta = newStabilimentoT.getOperatore().getIdOperatore();

			
			BeanPerXmlRichiesta beanPerXmlRichiesta=null;
			try {
				beanPerXmlRichiesta = preparaBeanPerFormClientDaXmlQuery(context,db,idTipoRichiesta,idRichiesta);
				if(beanPerXmlRichiesta.getListaEntries()!= null && beanPerXmlRichiesta.getListaEntries().size()>0)
				{
					HashMap<String, String> entryEsito = new HashMap<String, String>();
					entryEsito.put("Esito", "OK");
					beanPerXmlRichiesta.getListaEntries().add(entryEsito);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//lo metto nell'oggetto che verra trasformato in json

			newStabilimento.setBeanPerXml(beanPerXmlRichiesta);
			newStabilimento.setErroreSuap(newStabilimento. getErroreSuap()+ "- Si e verificato un Errore "+ex.getMessage());
		} 
		
		finally { 
			 
			try
			{
				this.freeConnection(context, db);
			} 
			catch(Exception ex){}
			
			try 
			{
				zipped.delete();
			}
			catch(Exception ex){}
			
			try
			{
				for(File f : tempAllFiles)
				{
					try {f.delete();} catch(Exception ex) {}
				}
			} catch(Exception ex){}
			
			
		}

		return "JsonSuapStabilimento";

	}

	
	

	private String getComuneRichiedente(Connection db,int idRichiesta) throws SQLException
	{
		String comuneRichiedente ="";
		String sql = "select comune_richiesta from suap_ric_scia_operatori_denormalizzati_view where id_opu_operatore=?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idRichiesta);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			comuneRichiedente=rs.getString(1);
		return comuneRichiedente;
	}


	
	
	public String executeCommandInserisciNuovaSciaPerRest(ActionContext cont)
	{
		String toRet = "jspRisultatoInserimentoSciaPerRest";
		Connection db = null;
		try
		{
			
			db = getConnection(cont);
			Stabilimento newStabilimento = new Stabilimento();
			
			HttpServletRequest req = cont.getRequest();
			//trasforma i parametri dal nome xml al nome richiesto per insert suap
			String tmpFolderPath = getWebInfPath(cont,"tmp_fold_multipart");
			File tmpFold = new File(tmpFolderPath);
			if(!tmpFold.exists())
				tmpFold.mkdir();
			
			MultipartRequest multiPart = new MultipartRequest(cont.getRequest(),tmpFolderPath);
			
			insertNuovoStabilimento(db, newStabilimento, cont, multiPart);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			freeConnection(cont,db);
		}
		return toRet;
	}
	
	
	

	public String insertNuovoStabilimento(Connection db,Stabilimento newStabilimento , ActionContext context,MultipartRequest multiPart)  throws Exception
	{
		
		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
		boolean isValid,recordInserted=false ;
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

		LookupList nazioniList = new LookupList(db,"lookup_nazioni");
		newStabilimento.setStato(Integer.parseInt(multiPart.getParameter("stato")));
		newStabilimento.setTipoInserimentoScia(Integer.parseInt(multiPart.getParameter("tipoInserimentoScia")));
		newStabilimento.setNumeroRegistrazioneVariazione(multiPart.getParameter("numeroRegistrazioneVariazione"));
		newStabilimento.setPartitaIvaVariazione(multiPart.getParameter("partitaIvaVariazione"));
		if(multiPart.getParameter("superficie")!=null && !multiPart.getParameter("superficie").equals(""))
			newStabilimento.setSuperficie(Integer.parseInt(multiPart.getParameter("superficie")));
		OperatoreAction actionOperatore = new OperatoreAction();
		String esitoInserimentoOperatore = actionOperatore.executeCommandInsertSuap(context,db,multiPart);


		Indirizzo indirizzoAdded = null;
		//	RECUPERO INDIRIZZO DELLA SEDE OPERATIVA

		//			INSERISCO L'INDIRIZZO DELLA SEDE OPERATIVA
		indirizzoAdded = new Indirizzo();
		indirizzoAdded.setTipologiaSede(Indirizzo.TIPO_SEDE_OPERATIVA);
		indirizzoAdded.setLatitudine(multiPart.getParameter("latStab"));
		indirizzoAdded.setLongitudine(multiPart.getParameter("longStab"));
		indirizzoAdded.setCap(multiPart.getParameter("capStab"));
		indirizzoAdded.getSedeOperativaSuap(context.getRequest(),multiPart, nazioniList, db,context);
		indirizzoAdded.setDescrizione_provincia(provinceList.getSelectedValue(indirizzoAdded.getProvincia()));					
		indirizzoAdded.setDescrizioneComune(comuniList.getSelectedValue(indirizzoAdded.getComune()));

		if (indirizzoAdded.getLatitudine()>0 && indirizzoAdded.getLongitudine()>0)
			indirizzoAdded.updateCoordinate(db);

		newStabilimento.setSedeOperativa(indirizzoAdded);
		newStabilimento.setNote(multiPart.getParameter("noteStab"));

		newStabilimento.setEnteredBy(super.getUserId(context));
		newStabilimento.setModifiedBy(getUserId(context));
		newStabilimento.setDataFineAttivita(multiPart.getParameter("dataFineAttivitaCessazione"));
		newStabilimento.setTipoAttivita(multiPart.getParameter("tipoAttivita"));
		newStabilimento.setTipoCarattere(multiPart.getParameter("tipoCarattere"));
		newStabilimento.setDataInizioAttivita(multiPart.getParameter("dataInizioAttivita"));
		newStabilimento.setDataFineAttivita(multiPart.getParameter("dataFineAttivita"));
		newStabilimento.setCessazioneStabilimento(multiPart.getParameter("cessazioneStabilimento"));
		
		newStabilimento.setDataInizioSospensione(multiPart.getParameter("dataInizioSospensione"));
		newStabilimento.setSospensioneStabilimento(multiPart.getParameter("sospensioneStabilimento"));
		
		
		if (newStabilimento.getTipoCarattere()==2) //TEMPORANEO
		{
			newStabilimento.setDataInizioAttivita(multiPart.getParameter("dataInizioAttivita"));
			newStabilimento.setDataFineAttivita(multiPart.getParameter("dataFineAttivita"));
		}

		int tipoPratica = Storico.INSERIMENTO_SCIA;

		if (newStabilimento.getTipoInserimentoScia()==Stabilimento.TIPO_SCIA_REGISTRABILI && newStabilimento.getStato()==Stabilimento.STATO_REGISTRAZIONE_ND)
		{
			tipoPratica=Storico.INSERIMENTO_SCIA; // CASO DI NUOVA SCIA DA SUAP
		}
		else
		{
			tipoPratica=Storico.INSERIMENTO_RICONOSCIUTO ; // CASO DI NUOVA SCIA DA SUAP
		}


		if (multiPart.getParameterValues("idLineaProduttiva") != null && multiPart.getParameterValues("idLineaProduttiva").length > 0) {

			String[] lineeProduttiveSelezionate = multiPart.getParameterValues("idLineaProduttiva");
			for (int i = 0; i < lineeProduttiveSelezionate.length; i++) {
				if (!lineeProduttiveSelezionate[i].equals("")) {
					LineaProduttiva  lp = new LineaProduttiva();

					lp.setDataInizio(newStabilimento.getDataInizioAttivita());
					lp.setDataFine(newStabilimento.getDataFineAttivita());


					lp.setIdRelazioneAttivita(lineeProduttiveSelezionate[i]);
					lp.setIdVecchiaLinea(multiPart.getParameter("idLineaVecchia"+((i==0) ? "":""+i)));
					if (i==0)
						lp.setPrincipale(true);

					
					
					//per ogni linea produttiva, devo ottenere tutti i campi estesi (se ne esistono).
					//Questi arrivano nel multiPart del form come nome_campoIDTIPOLINEA
					//quindi per prenderli faccio query per vedere quali sono previsti per quell'idtipolinea 
					//faccio la concatenazione e li estraggo 
					PreparedStatement pst1 = db.prepareStatement("select * from linee_mobili_html_fields where id_linea = ? and gestione_interna is not null"); /*se gestione interna e' null allora sono campi estesi di validazione, e non ci interessano */
					pst1.setInt(1,lp.getIdRelazioneAttivita());
					ResultSet rs1 = pst1.executeQuery();
					HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>> valoriCampiEstesi = new HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso> >(); //la chiave e il nome del dom, il valore e un array dove la prima stringa e il valore, la seconda l'id su html fields
					while(rs1.next())
					{
						int idHtmlField = rs1.getInt("id");
						if(rs1.getString("nome_campo").trim().length() == 0 || rs1.getString("tipo_campo").equalsIgnoreCase("dummy_label")
								|| rs1.getString("tipo_campo").equalsIgnoreCase("horizontal_line")) /*campo dummy (es horizontal line) */
						{
							continue;
						}
						LineaProduttivaCampoEsteso  campoEsteso = new LineaProduttivaCampoEsteso();
						campoEsteso.setIdFieldHtml(idHtmlField);
						campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
						campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
						campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
						
						
						
						String nomeCampoEstesoNellaForm = rs1.getString("nome_campo")+lp.getIdRelazioneAttivita();
						//lo prendo dal multipart
						String[] values = multiPart.getParameterValues(nomeCampoEstesoNellaForm);
						
						if(values == null && rs1.getString("tipo_campo") != null && rs1.getString("tipo_campo").equalsIgnoreCase("checkbox") )
						{ //nb: un checkbox non checked non arriva proprio nel form !
							
							if (valoriCampiEstesi.get(idHtmlField)!=null)
							{
								campoEsteso.setValore("false");
								ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
								listaValori.add(campoEsteso);
								valoriCampiEstesi.put(idHtmlField, listaValori);
								
//								valoriCampiEstesi.put(idHtmlField,new String[]{"false",idHtmlField+""}); //perche in tal caso e un checkbox che dovrebbe esserci, ma non essendo arrivato vuol dire che non era checked
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
					
					lp.addCampiEstesi(valoriCampiEstesi);
					
					newStabilimento.getListaLineeProduttive().add(lp);
					
					
					
					pst1.close();
					rs1.close();
				}
			}

		}
		
		

		//Gestisco campi aggiuntivi per att.mobili
		if (newStabilimento.getTipoAttivita()==2 || newStabilimento.getTipoAttivita()==3)
		{
			gestisciCampiMobile(context, newStabilimento, db);
			newStabilimento.getSedeOperativa().setIdIndirizzo(INDIRIZZO_SEDE_MOBILE);

		}


		Operatore newOperatore = (Operatore)context.getRequest().getAttribute("Operatore");
		
		newStabilimento.setOperatore(newOperatore);
		newStabilimento.setIdOperatore(newOperatore.getIdOperatore());

		Object[] asl = null;

		if (newStabilimento.getTipoAttivita()==1 )
			asl = DwrUtil.getValoriAsl(indirizzoAdded.getComune());
		else
			if (newStabilimento.getTipoAttivita()==2 || newStabilimento.getTipoAttivita() ==3  || newStabilimento.getTipoAttivita() <=0)
			{
				if (newOperatore.getTipo_impresa()!=1)
					asl = DwrUtil.getValoriAsl(newOperatore.getSedeLegale().getComune());
				else
					asl = DwrUtil.getValoriAsl(newOperatore.getRappLegale().getIndirizzo().getComune());

			}

		if (asl != null && asl.length > 0) {

			Object[] aslVal = (Object[]) asl[0];
			if (aslVal != null && aslVal.length > 0)
				newStabilimento.setIdAsl((Integer) aslVal[0]);

		} else {
			newStabilimento.setIdAsl(Constants.ID_ASL_FUORI_REGIONE);
		}


		newStabilimento.setDataRichiestaSciaAsl(multiPart.getParameter("dataRichiesta"));


		isValid = this.validateObject(context, db, newStabilimento);
		if (isValid){// && newStabilimento.getSedeOperativa().getIdIndirizzo()>0) {  



			String numProtocollo = "" ;
			if (user.getUserRecord().getSuap()!=null  )
			{

				String istatComune = user.getUserRecord().getSuap().getIstaComune();
				if (istatComune==null)
				{
					istatComune = newStabilimento.getSedeOperativa().getIstatComune(db) ;
				}
			}

			//da qui si arriva a 'suap_ric_scia_relazione_stabilimento_linee_produttive'
			recordInserted = newStabilimento.insert(db, true,context);
			
			
			RicercheAnagraficheTab.inserRichiesta(db, newStabilimento.getAltId());
			
			if (newStabilimento.getTipoInserimentoScia()==newStabilimento.TIPO_SCIA_APICOLTURA)
			{
				
				
				String sql = "select * from public_functions.suap_insert_attivita_apicoltura_da_richiesta(?,?)";
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, newStabilimento.getOperatore().getIdOperatore());
				pst.setInt(2,getUserId(context));
				ResultSet rs = pst.executeQuery();
				boolean apicolturaInserita=false;
				if(rs.next())
					apicolturaInserita=rs.getBoolean(1);
				if(apicolturaInserita)
					logger.info("INSERITA NUOVA SCIA E ATTIVITA DI APICOLTURA");
				else
					logger.info("INSERITA NUOVA SCIA MA NON  ATTIVITA DI APICOLTURA - ATTIVITA ESISTENTE");
				
				HashMap<String,String> configs = new HashMap<String,String>();
				configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
				configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
				configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
				configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
				configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
				configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
				configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				configs.put("mail.smtp.socketFactory.fallback", "false");
				
				PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
				
				
				String msg = "Gentile Utente, le comunichiamo che e stato abilitato all'inserimento dei dati riguardanti la propria attivita di apicoltura nella banca dati apistica regionale.";
				
				String destinatario = newStabilimento.getOperatore().getDomicilioDigitale();
				if( ! ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale"))
				{
					destinatario = "gisadev@u-s.it";
				}
				
				
				
				try {
					sender.sendMail("BDAR-ABILITAZIONE CREDENZIALI", msg, "gisasuap@cert.izsmportici.it",destinatario , null);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
			

			String[] infoUtente = {"-1", "", ""};
			infoUtente[0] =  String.valueOf(getUserId(context));
			infoUtente[1] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleRichiedente();
			infoUtente[2] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleDelegato();
			gestioneStorico(newStabilimento, tipoPratica, infoUtente, "", db);
			gestioneStorico(newStabilimento, Storico.REGISTRAZIONE_NON_DISPONIBILE , infoUtente, "", db);



			newStabilimento.setErroreSuap("Inserimento Avvenuto con Successo");
			newStabilimento.setCodiceErroreSuap("0");
			context.getRequest().setAttribute("Stabilimento", newStabilimento);
			return "JsonSuapStabilimento";

		}
		else
		{

			newStabilimento.setErroreSuap("Attenzione Controllare di aver Inserito tutti i Campi");
			newStabilimento.setCodiceErroreSuap("1");
			context.getRequest().setAttribute("Stabilimento", newStabilimento);
			return "JsonSuapStabilimento";
		}



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


	private void gestioneStorico(Stabilimento stab, int idOperazione, String[] infoUtente, String note, Connection db){
		Storico storico = new Storico();
		storico.setIdStabilimento(stab.getIdStabilimento());
		storico.setIdOperazione(idOperazione);
		storico.setIdUtente(infoUtente[0]);
		storico.setCodFiscaleUtenteRichiedente(infoUtente[1]);
		storico.setCodFiscaleUtenteDelegato(infoUtente[2]);
		storico.setNote(note);
		storico.insert(db);
	}




	
	
	
	
	public File generaFileXmlDaDb(ActionContext cont,Connection db,int idRichiestaNuovoOp, int tipoRichiesta, String nomeFileXml) throws Exception
	{
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		ResultSet rs3 = null;

		try
		{
			String tmpFolderPath = getWebInfPath(cont,"tmp_attachment_pecmail");
			//			String tmpFolderPath = "C:/Users/davide/Desktop/temp_fold/";
			File tmpFold = new File(tmpFolderPath);
			tmpFold.mkdir();

			//estraggo la stringa xml che rappresenta il file


			
			
			String queryRichiesta = "select * from suap_query_richiesta(?, ?)";
			pst4 = db.prepareStatement(queryRichiesta);
			pst4.setInt(1, tipoRichiesta);
			pst4.setInt(2, idRichiestaNuovoOp);
			
			String query = "select query_to_xml('"+pst4.toString()+"',true,false,'')";  
			
			pst3=db.prepareStatement(query);
			rs3 = pst3.executeQuery();
			rs3.next();
			File xmlFile = new File(tmpFold.getAbsolutePath()+"/"+nomeFileXml);

			FileOutputStream os = new FileOutputStream(xmlFile);
			os.write(rs3.getString(1).getBytes()); //scrivo il contenuto estratto dal db sul file
			os.close();
			return xmlFile;
		}
		catch(Exception ex)
		{
			throw ex; //poiche la gestiamo fuori
		}
	}
	
	
	
	
	

	public void inviaMailPec(ActionContext cont,Connection db,int idRichiestaNuovoOp,int tipoRichiesta,File fileAllegato,String comuneRichiedente) throws SQLException, MessagingException
	{



		//configuro sender di mail usando proprieta salvate a sistema

		HashMap<String,String> configs = new HashMap<String,String>();
		configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
		configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
		configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
		configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
		configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
		configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
		configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		configs.put("mail.smtp.socketFactory.fallback", "false");
		
		PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
		//	creo cartella temporanea per salvare file xml il cui contenuto e estratto dal db	
		String destinatario =  "";
		try {
			String sql = "select * from suap_get_pec_comune_richiestente(?)";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, comuneRichiedente);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getString(1)!=null && !rs.getString(1).equals("")){
					if (ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale"))
						destinatario = rs.getString(1);
					else
						destinatario = "gisadev@u-s.it";	
				}
			}

			if (destinatario == null || destinatario.equals("")) {
				System.out.println("INDIRIZZO MAIL DESTINATARIO NULL! MAIL NON INVIATA");
				throw new AddressException();
			} else {
				// NON COMMITTARE
				// sender.sendMail("Inserimento nuova richiesta","Inserimento nuova richiesta operatore "+idRichiestaNuovoOp+" effettuata con successo. "
				// ,"gisasuap@cert.izsmportici.it", "gisadev@u-s.it",
				// fileAllegato);

				
				sender.sendMail("Inserimento nuova richiesta", "Inserimento nuova richiesta operatore "
						+ idRichiestaNuovoOp + " effettuata con successo. ", "gisasuap@cert.izsmportici.it",
						destinatario, fileAllegato);

			}

			logger.info("##" + getContext(cont) + "## Destinatario Mail : " + destinatario + " Comune Richiedente : "
					+ comuneRichiedente);

		} catch (AddressException e) {
			System.out.println("ERRORE IN INVIO PEC " + e.getMessage());
			throw e;

		} catch (MessagingException e) {
			System.out.println("ERRORE IN INVIO PEC " + e.getMessage());

			// TODO Auto-generated catch block
			throw e;
		}





	}

	public File zippaFiles(ActionContext cont,File[] filesToZip,String nomeZip) throws Exception
	{
		String tmpFolderPath = getWebInfPath(cont,"tmp_attachment_pecmail");
		File fileZip = new File(tmpFolderPath+nomeZip);
		InputStream iS = null;

		ZipOutputStream zOs = new ZipOutputStream(new FileOutputStream(fileZip));
		try
		{
			for(File fileToAdd : filesToZip)
			{
				ZipEntry zipEntry = new ZipEntry(fileToAdd.getName());
				zOs.putNextEntry(zipEntry);
				iS = new FileInputStream(fileToAdd);
				int t = -1;
				while((t = iS.read()) != -1)
				{
					zOs.write(t);
				}
				try{zOs.closeEntry();} catch(Exception ex){}
				try{iS.close();} catch(Exception ex){}

			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(zOs != null)
				zOs.close();
			if(iS != null)
				iS.close();

		}
		return fileZip;
	}


	

	public File[] scaricaFilesDaDocumentale(ActionContext cont,int altId) throws Exception { //ritorna file nella cartella temporanea che sono stati scaricati per quello stabilimento da gisa
		URL urlServizioListaCodici = null;
		HttpURLConnection conn = null;
		BufferedReader in = null;
		OutputStreamWriter wr = null;
		URL urlSpecificoDocumento = null;
		InputStream iS = null;
		OutputStream oS = null;
		String path_doc = null;
		ArrayList<File> alFileScaricati = new ArrayList<File>();

		//recupero l'id timbro
		//		String codDocumento 		=  null;
		//		codDocumento = context.getRequest().getParameter("codDocumento");
		//		if (codDocumento==null)
		//			codDocumento = (String)context.getRequest().getAttribute("codDocumento");
		//		String idDocumento 				= null;
		//		idDocumento = context.getRequest().getParameter("idDocumento");
		//
		//		String titolo="";
		//		String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
		String lista_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_LISTA_ALLEGATI");
		String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
		
		try
		{
			//			path_doc = "C:/Users/davide/Desktop/temp_fold/";
			path_doc = getWebInfPath(cont,"tmp_attachment_pecmail");
			File theDir = new File(path_doc);
			theDir.mkdirs();


			urlServizioListaCodici = new URL(lista_url);
			System.out.println("######scaricaFilesDaDocumentale apertura connessione http#####");
			System.out.println("######URLO::::"+lista_url);
			
			conn = (HttpURLConnection) urlServizioListaCodici.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("altId");
			requestParams.append("="+altId);
			requestParams.append("&app_name");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			System.out.println("######scaricaFilesDaDocumentale INVIO PARAMETRI##### ALTID "+altId+" APPNAME "+ApplicationProperties.getProperty("APP_NAME_GISA"));

			System.out.println("######scaricaFilesDaDocumentale inizioooo getoutput stream#####");

			
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			
			System.out.println("######scaricaFilesDaDocumentale fine getoutput stream#####");


			System.out.println("######scaricaFilesDaDocumentale inizio getInputstreamm stream#####");
			in = new BufferedReader( new InputStreamReader(conn.getInputStream()));

			StringBuffer rcv = new StringBuffer();
			if (in != null) 
			{
				rcv.append(in.readLine()); 
			}
			
			
			System.out.println("######scaricaFilesDaDocumentale fineeeeeee getInputstreamm stream#####");

			JSONArray jo = new JSONArray(rcv.toString());

			DocumentaleAllegatoList docList = new DocumentaleAllegatoList();
			docList.creaElenco(jo);

			for(Object infoDocumentoDaRichiedere : docList)
			{
				String codiceDocu = ((DocumentaleAllegato)infoDocumentoDaRichiedere).getIdHeader();
				//uso il codice per costruire l'url al quale mandare la richiesta per scaricare file
				String url_specificoDocumento = download_url+"?codDocumento="+codiceDocu;
				
				System.out.println("######scaricaFilesDaDocumentale url_specificoDocumento :"+url_specificoDocumento);
				urlSpecificoDocumento = new URL(url_specificoDocumento);
				iS= urlSpecificoDocumento.openStream();
				String nomeOggetto = ((DocumentaleAllegato)infoDocumentoDaRichiedere).getOggetto() != null ? ((DocumentaleAllegato)infoDocumentoDaRichiedere).getOggetto() : "file_allegato";
				String titoloFileTemp = nomeOggetto+"_"+codiceDocu+"."+((DocumentaleAllegato)infoDocumentoDaRichiedere).getEstensione();
				File f = new File(path_doc+titoloFileTemp);
				oS = new FileOutputStream(f);
				int t = -1;
				while( (t = iS.read()) != -1)
				{
					oS.write(t);

				}
				oS.close();
				iS.close();
				alFileScaricati.add(f);

			}

		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(in!= null)
				in.close();
			if(iS != null)
				iS.close();
			if(oS != null)
				oS.close();
		}


		return alFileScaricati.toArray(new File[alFileScaricati.size()]);

	}


	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();


		String tempAltId = context.getRequest().getParameter("altId");
		if (tempAltId == null) {
			tempAltId = ""
					+ (Integer) context.getRequest().getAttribute("altId");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer altid = null;



		if (tempAltId != null) {
			altid = Integer.parseInt(tempAltId);
		}
		ticList.setAltId(altid);
		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink(context.getAction().getActionName()+".do?command=ViewVigilanza&altId="+altid
				);
		ticList.setPagedListInfo(ticketListInfo);
		try {



			db = this.getConnection(context);	




			Stabilimento newStabilimento = new Stabilimento(db,  altid, true);
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
			controlliList.setAltId(newStabilimento.getAltId());
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

			context.getRequest().setAttribute("ListaStati", ListaStati);

			
			LookupList normeList = new LookupList(db, "(select * from opu_lookup_norme_master_list where flag_vecchia_gestione=false)");
			normeList.addItem(-1, "--Seleziona Norma--");
			context.getRequest().setAttribute("normeList", normeList);
			

			
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
	
	
	
	private boolean checkFirmaObbligatoria(Connection db, String istat){
		
		boolean firmaObbligatoria = false;
		String sql = "select * from suap_check_firma_obbligatoria_by_istat(?)";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
		
		pst.setString(1, istat);
		ResultSet rs= pst.executeQuery();
		if (rs.next())
		{	
			firmaObbligatoria = rs.getBoolean(1);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return firmaObbligatoria;
	}

	
	public String executeCommandGestioneScia(ActionContext context) throws ParseException {


		
		if (getContext(context)!= CONTEXT_SUAP  )
		{
			UserBean user = (UserBean) context.getSession().getAttribute("User");

			org.aspcfs.modules.opu.base.Stabilimento stabilimentoSoggettoAScia = new org.aspcfs.modules.opu.base.Stabilimento();
			

			Connection db = null ;
			try
			{
				db = this.getConnection(context);
				stabilimentoSoggettoAScia.queryRecordStabilimento(db, Integer.parseInt(context.getParameter("idStabilimento")));

				context.getRequest().setAttribute("StabilimentoSoggettoAScia",stabilimentoSoggettoAScia);
				LookupList tipoScia = new LookupList(db, "suap_lookup_tipo_richiesta");
				context.getRequest().setAttribute("SuapTipoScia", tipoScia);
				
				Stabilimento newStabilimento = new Stabilimento();
				newStabilimento.setTipoInserimentoScia(Integer.parseInt(context.getParameter("idTipoInserimentoScia")));
				
				
				context.getRequest().setAttribute("newStabilimento", newStabilimento);

				
				

				

			}
			catch(SQLException e)
			{
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndirizzoNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				this.freeConnection(context, db);
			}



		}

		return getContextReturn(context,"GestioneScia");
		//return  executeCommandSearchForm(context);

	}
	
	
			public String executeCommandStatoBdu(ActionContext context) throws ParseException 
			{
				Connection db = null ;
				int idStabilimento = -1;
				int orgId = -1;
				try
				{
					db = this.getConnectionBdu(context);
					if(context.getParameter("idStabilimento")!=null)
						idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
					if(context.getParameter("orgId")!=null)
						orgId = Integer.parseInt(context.getParameter("orgId"));
					Canile canile = DbiBdu.getStatoCanile(context, db, idStabilimento,orgId);
					context.getRequest().setAttribute("canile",canile);
					
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				} catch (NumberFormatException e) 
				{
					e.printStackTrace();
				} 
				finally
				{
					this.freeConnection(context, db);
				}
				return getReturn(context,"StatoCanileBdu");
			}


	
//	public String executeCommandRespingiPratica(ActionContext context) throws SQLException, IOException
//	{
//		
//		int idStabilimento =Integer.parseInt( context.getRequest().getParameter("idStabilimento"));
//
//		Connection db = null;
//		ResultSet rs = null;
//		PreparedStatement pst= null;
//		try
//		{
//			db = getConnection(context);
//
//			pst =db.prepareStatement("select * from suap_respingi_pratica(?, ?)");
//			pst.setInt(1, idStabilimento);
//			pst.setInt(2, getUserId(context));
//			rs = pst.executeQuery();
//		
//		}
//		catch(Exception ex)
//		{}
//		finally
//		{
//			try {
//				this.freeConnection(context, db);
//			} catch(Exception ex) 
//			{}
//		}
//		context.getRequest().setAttribute("idStab", idStabilimento);
//		return executeCommandDetails(context);
//	//		
//	}
	
}
