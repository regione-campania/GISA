/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.erratacorrige.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.buffer.base.Comune;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.stabilimenti.actions.StabilimentiDiarioMacellazione;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.ibm.wsdl.util.StringUtils;

public class GestioneErrataCorrige extends CFSModule {

	public String executeCommandErrataCorrige(ActionContext context) throws IndirizzoNotFoundException
	{

		String returnTo = "" ;
		Connection db = null ;
		try
		{
			db = super.getConnection(context);

			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");
			listaToponimi.buildList(db);
			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);


			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaList", TipoImpresaList);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaList", TipoSocietaList);


			int tipoErrataCorrige= Integer.parseInt(context.getParameter("tipoErrataCorrige"));
			switch(tipoErrataCorrige)
			{
			case 1 : /*Variazione Soggetto Fisico*/
			{
				int idStabilimento = Integer.parseInt(context.getParameter("stabId"));
				Stabilimento stab = new Stabilimento();
				stab.queryRecordStabilimento(db, idStabilimento);

				stab.getOperatore().getRappLegale().calcolaAsl(db, stab.getOperatore().getRappLegale().getIndirizzo().getComune());
				context.getRequest().setAttribute("StabilimentoDettaglio", stab);
				returnTo = "ErrataCorrigeSFOK";
				break ;


			}
			case 2 : /*Variazione Dati Impresa*/
			{
				int idStabilimento = Integer.parseInt(context.getParameter("stabId"));
				Stabilimento stab = new Stabilimento();
				stab.queryRecordStabilimento(db, idStabilimento);
				stab.getOperatore().getRappLegale().calcolaAsl(db, stab.getOperatore().getRappLegale().getIndirizzo().getComune());
				context.getRequest().setAttribute("StabilimentoDettaglio", stab);
				returnTo = "ErrataCorrigeImpresaOK";
				break ;


			}
			case 3 : /*Variazione Dati Impresa*/
			{
				int idStabilimento = Integer.parseInt(context.getParameter("stabId"));
				Stabilimento stab = new Stabilimento();
				stab.queryRecordStabilimento(db, idStabilimento);
				stab.getOperatore().getRappLegale().calcolaAsl(db, stab.getOperatore().getRappLegale().getIndirizzo().getComune());
				context.getRequest().setAttribute("StabilimentoDettaglio", stab);
				returnTo = "ErrataCorrigeLineaOK";
				break ;


			}

			}



		}
		catch(SQLException e )
		{

		}
		finally
		{
			super.freeConnection(context, db);
		}

		return returnTo;
	}

	public String executeCommandVerificaEsistenzaSoggettoFisico(ActionContext context) throws IndirizzoNotFoundException
	{

		Connection db = null ;
		try
		{
			db = super.getConnection(context);


			SoggettoFisico sf = new SoggettoFisico(context.getRequest());
			SoggettoFisico esistente = sf.comparaDatiSoggettoFisico( db);
			context.getRequest().setAttribute("SoggettoEsistente", esistente);


		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			super.freeConnection(context, db);
		}

		return "VerificaEsistenzaoggettoOK";
	}







	public String executeCommandVerificaEsistenzaAltriStabilimentiImpresa(ActionContext context) throws IndirizzoNotFoundException
	{

		Connection db = null ;
		try
		{
			db = super.getConnection(context);


			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaListStab", TipoImpresaList);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaListStab", TipoSocietaList);

			int idStabilimentoErrataCorrige = Integer.parseInt(context.getParameter("idStabilimento"));

			Stabilimento stabilimento = new  Stabilimento(db,idStabilimentoErrataCorrige);

			Operatore opReq = new Operatore();

			Indirizzo sedeLegReq = new Indirizzo(context.getRequest(), db,context);
			sedeLegReq.setTipologiaSede(1);

			sedeLegReq.setDescrizioneComune(ComuniAnagrafica.getDescrizione(db, sedeLegReq.getComune()));
			opReq.setTipo_impresa(Integer.parseInt(context.getParameter("tipo_impresa")));
			if(context.getParameter("tipo_societa")!=null && !context.getParameter("tipo_societa").equals(""))
				opReq.setTipo_societa(Integer.parseInt(context.getParameter("tipo_societa")));
			else
				opReq.setTipo_societa(-1);
			opReq.setRagioneSociale(context.getParameter("ragioneSociale"));
			opReq.setPartitaIva(context.getParameter("partitaIva"));
			opReq.setCodFiscale(context.getParameter("codFiscale"));
			opReq.setDomicilioDigitale(context.getParameter("domicilioDigitale"));
			opReq.setRappLegale(stabilimento.getOperatore().getRappLegale());
			opReq.setSedeLegaleImpresa(sedeLegReq);
			opReq.getListaSediOperatore().add(sedeLegReq);

			Stabilimento st = new  Stabilimento(db,idStabilimentoErrataCorrige);

			st.setOperatore(opReq);

			StabilimentoList listaStabilimenti = new StabilimentoList();
			
			if(stabilimento.getOperatore().getPartitaIva()!=null && !stabilimento.getOperatore().getPartitaIva().equals(""))
				listaStabilimenti.setPartitaIva(stabilimento.getOperatore().getPartitaIva());
			else if(stabilimento.getOperatore().getCodFiscale()!=null && !stabilimento.getOperatore().getCodFiscale().equals(""))
				listaStabilimenti.setCodiceFiscaleImpresa(stabilimento.getOperatore().getCodFiscale());
			else
				listaStabilimenti.setIdOperatore(stabilimento.getOperatore().getIdOperatore());
			listaStabilimenti.buildList(db);



			StabilimentoList listaStabSoggettiDiversi = new StabilimentoList();

			for (int i = 0 ; i <listaStabilimenti.size();i++)
			{
				Stabilimento thisStab = (Stabilimento) listaStabilimenti.get(i);
				if(thisStab.getOperatore().compareToDatiImpresa(opReq)!=0 )
				{
					thisStab.getOperatore().getRappLegale().calcolaAsl(db, thisStab.getOperatore().getRappLegale().getIndirizzo().getComune());
					listaStabSoggettiDiversi.add(thisStab);
				}


			}



			context.getRequest().setAttribute("ListaStabilimenti", listaStabSoggettiDiversi);
			context.getRequest().setAttribute("StabilimentoEC", stabilimento);
			context.getRequest().setAttribute("StabilimentoEC2", st);


			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");
			listaToponimi.buildList(db);
			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);

			if((listaStabilimenti.contains(stabilimento) && listaStabilimenti.size()==1))
			{
				System.out.println("Stabilimento contenuto in lista");
			}




		}
		catch(SQLException e )
		{

		}
		finally
		{
			super.freeConnection(context, db);
		}

		return "ListaStabilimentiJson";
	}


	public String executeCommandVerificaEsistenzaAltriStabilimenti(ActionContext context) throws IndirizzoNotFoundException
	{

		Connection db = null ;
		try
		{
			db = super.getConnection(context);

			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaListStab", TipoImpresaList);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaListStab", TipoSocietaList);

			int idStabilimentoErrataCorrige = Integer.parseInt(context.getParameter("idStabilimento"));

			Stabilimento stabilimento = new  Stabilimento(db,idStabilimentoErrataCorrige);
			SoggettoFisico sf = new SoggettoFisico(context.getRequest());
			sf.getIndirizzo().setDescrizioneComune(ComuniAnagrafica.getDescrizione(db, sf.getIndirizzo().getComune()));
			sf.calcolaAsl(db, sf.getIndirizzo().getComune());

			Stabilimento st = new  Stabilimento(db,idStabilimentoErrataCorrige);

			st.getOperatore().setRappLegale(sf);

			StabilimentoList listaStabilimenti = new StabilimentoList();


			listaStabilimenti.setCodiceFiscaleSoggettoFisico(sf.getCodFiscale());
			listaStabilimenti.setIdSoggettoFisico(sf.getIdSoggetto());
			listaStabilimenti.setIdOperatore(stabilimento.getIdOperatore());
			listaStabilimenti.buildListErrataCorrigeSoggettoFisico(db);
			
			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");
			listaToponimi.buildList(db);
			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);


			StabilimentoList listaStabSoggettiDiversi = new StabilimentoList();

			for (int i = 0 ; i <listaStabilimenti.size();i++)
			{
				Stabilimento thisStab = (Stabilimento) listaStabilimenti.get(i);
				if(thisStab.getOperatore().getRappLegale().compareToDatiResidenza(sf)!=0 )
				{
					thisStab.getOperatore().getRappLegale().calcolaAsl(db, thisStab.getOperatore().getRappLegale().getIndirizzo().getComune());
					listaStabSoggettiDiversi.add(thisStab);
				}


			}


			context.getRequest().setAttribute("ListaStabilimenti", listaStabSoggettiDiversi);
			context.getRequest().setAttribute("StabilimentoEC", stabilimento);
			context.getRequest().setAttribute("StabilimentoEC2", st);

			if((listaStabilimenti.contains(stabilimento) && listaStabilimenti.size()==1))
			{
				System.out.println("Stabilimento contenuto in lista");
			}




		}
		catch(SQLException e )
		{

		}
		finally
		{
			super.freeConnection(context, db);
		}

		return "ListaStabilimentiJson";
	}


	public String executeCommandErrataCorrigeSoggettoFisico(ActionContext context) throws IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	{

		Connection db = null ;
		StabilimentoList listaStabilimentiNonModificati = new StabilimentoList();

		try
		{
			db = super.getConnection(context);


			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaListStab", TipoImpresaList);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaListStab", TipoSocietaList);

			SoggettoFisico sf = new SoggettoFisico(context.getRequest());
			sf.getIndirizzo().setDescrizioneComune(ComuniAnagrafica.getDescrizione(db, sf.getIndirizzo().getComune()));
			sf.calcolaAsl(db, sf.getIndirizzo().getComune());
			String[] listaStabilimentiSelezionati = context.getRequest().getParameterValues("checkStab");


			String insertStorico = "insert into opu_gestione_errata_corrige(data_errata_corrige,tipo_errata_corrige,id_stabilimento_coinvolto,id_operatore_precedente,id_soggetto_fisico_precedente,id_utente) values (current_timestamp,1,?,?,?,?)";
			PreparedStatement pstStorico = db.prepareStatement(insertStorico);


			context.getRequest().setAttribute("stabId", context.getRequest().getParameter("idStabilimento"));

			SoggettoFisico sfModificato= sf.applicaErrataCorrige(db, context);

			ArrayList<Integer> listaImprese = new ArrayList<Integer>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


			StringBuffer msgComunicazione= new StringBuffer();// "In data "+sdf.format(new Date(System.currentTimeMillis()))+" l'utente con username: "+getUser(context, getUserId(context)).getUsername()+" Ha Effettuato una Errata Corrige sul soggetto Fisico presso i Seguenti Stabilimenti :\n\n ";
			aggiongiIntestazioneMail(msgComunicazione);

			for (int i=0;i<listaStabilimentiSelezionati.length;i++)
			{

				Stabilimento thisStab = new Stabilimento(db, Integer.parseInt(listaStabilimentiSelezionati[i]));
				aggiungiAllaMail(thisStab, msgComunicazione, db,true,"",context);


				Operatore op = thisStab.getOperatore();

				int idOperatoreCorrente = thisStab.getOperatore().getIdOperatore();

				int idSoggettoFisicoPrecedente = op.getRappLegale().getIdSoggetto();
				op.setRappLegale(sfModificato);

				/*Verifica l'asl di appartenenza dello stabilimento*/
				if(verificaAslStabilimento(thisStab, op, db)==true)
				{
					if(!listaImprese.contains(idOperatoreCorrente))
						listaImprese.add(idOperatoreCorrente);

					op.applicaErrataCorrige(db,context);
					thisStab.setOperatore(op);
					thisStab.applicaErrataCorrige(db,context);

					RicercheAnagraficheTab.inserOpu(db, thisStab.getIdStabilimento());

					pstStorico.setInt(1, thisStab.getIdStabilimento());
					pstStorico.setInt(2, idOperatoreCorrente);
					pstStorico.setInt(3, idSoggettoFisicoPrecedente);
					pstStorico.setInt(4, getUserId(context));
					pstStorico.execute();
					aggiungiAllaMail(thisStab, msgComunicazione, db,false,"OK",context);


				}
				else
				{
					thisStab = new Stabilimento(db, Integer.parseInt(listaStabilimentiSelezionati[i]));
					listaStabilimentiNonModificati.add(thisStab);
					aggiungiAllaMail(thisStab, msgComunicazione, db,false,"KO",context);


				}

			}

			for(Integer ii : listaImprese)
			{
				PreparedStatement pst = db.prepareStatement("update opu_operatore set note_internal_use_only_hd ='APPLICATA ERRATA CORRIGE SOGGETTO FISICO', trashed_date = current_date " +
						"where id not in (select id_operatore from opu_stabilimento where id_operatore = opu_operatore.id and trashed_date is null) and opu_operatore.id = ?");
				pst.setInt(1, ii);
				pst.execute();
			}


			msgComunicazione.append("</table>");
			HashMap<String,String> configs = new HashMap<String,String>();
			configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
			configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
			configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
			configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
			configs.put("mail.smtp.ssl.enable","true");
			configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
			configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			configs.put("mail.smtp.socketFactory.fallback", "false");
			
			PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
			try {
				sender.sendMail("Comunicazione Errata Corrige Soggetto Fisico",msgComunicazione.toString(),"gisasuap@cert.izsmportici.it", "gisadev@usmail.it", null);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			


		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			super.freeConnection(context, db);
		}

		context.getRequest().setAttribute("ListaStabilimentiNonModificati", listaStabilimentiNonModificati);

		if(listaStabilimentiNonModificati.size()==0)
			return "ApplicataErrataCorrigeOK";

		return "ApplicataErrataCorrigeKO";
	}


	public String executeCommandErrataCorrigeDatiImpresa(ActionContext context) throws IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	{

		Connection db = null ;
		StabilimentoList listaStabilimentiNonModificati = new StabilimentoList();
		try
		{
			db = super.getConnection(context);


			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaListStab", TipoImpresaList);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaListStab", TipoSocietaList);

			Operatore opReq = new Operatore();

			Indirizzo sedeLegReq = new Indirizzo(context.getRequest(), db,context);
			sedeLegReq.setTipologiaSede(1);

			sedeLegReq.setDescrizioneComune(ComuniAnagrafica.getDescrizione(db, sedeLegReq.getComune()));
			opReq.setTipo_impresa(Integer.parseInt(context.getParameter("tipo_impresa")));
			
			if(context.getParameter("tipo_societa")!=null && !"".equals(context.getParameter("tipo_societa")))
				opReq.setTipo_societa(Integer.parseInt(context.getParameter("tipo_societa")));
			else
				opReq.setTipo_societa(-1);
			opReq.setRagioneSociale(context.getParameter("ragioneSociale"));
			opReq.setPartitaIva(context.getParameter("partitaIva"));
			opReq.setCodFiscale(context.getParameter("codFiscale"));
			opReq.setDomicilioDigitale(context.getParameter("domicilioDigitale"));
			opReq.setSedeLegaleImpresa(sedeLegReq);
			opReq.getListaSediOperatore().add(sedeLegReq);

			String[] listaStabilimentiSelezionati = context.getRequest().getParameterValues("checkStab");


			String insertStorico = "insert into opu_gestione_errata_corrige(data_errata_corrige,tipo_errata_corrige,id_stabilimento_coinvolto,id_operatore_precedente,id_soggetto_fisico_precedente,id_utente) values (current_timestamp,2,?,?,?,?)";
			PreparedStatement pstStorico = db.prepareStatement(insertStorico);


			context.getRequest().setAttribute("stabId", context.getRequest().getParameter("idStabilimento"));


			ArrayList<Integer> listaImprese = new ArrayList<Integer>();



			StringBuffer msgComunicazione= new StringBuffer();//"In data "+sdf.format(new Date(System.currentTimeMillis()))+" l'utente con username: "+getUser(context, getUserId(context)).getUsername()+" Ha Effettuato una Errata Corrige sul soggetto Fisico presso i Seguenti Stabilimenti :\n\n ";
			aggiongiIntestazioneMail(msgComunicazione);
			for (int i=0;i<listaStabilimentiSelezionati.length;i++)
			{

				Stabilimento thisStab = new Stabilimento(db, Integer.parseInt(listaStabilimentiSelezionati[i]));

				aggiungiAllaMail(thisStab, msgComunicazione, db, true, "",context);
				opReq.setRappLegale(thisStab.getOperatore().getRappLegale());

				Operatore op = thisStab.getOperatore();

				int idOperatoreCorrente = thisStab.getOperatore().getIdOperatore();

				if(!listaImprese.contains(idOperatoreCorrente))
					listaImprese.add(idOperatoreCorrente);

				int idSoggettoFisicoPrecedente = op.getRappLegale().getIdSoggetto();
				opReq.setRappLegale(op.getRappLegale());

				if(verificaAslStabilimento(thisStab, opReq, db)==true)
				{
					opReq.applicaErrataCorrige(db,context);

					thisStab.setOperatore(opReq);




					thisStab.applicaErrataCorrige(db,context);

					RicercheAnagraficheTab.inserOpu(db, thisStab.getIdStabilimento());

					pstStorico.setInt(1, thisStab.getIdStabilimento());
					pstStorico.setInt(2, idOperatoreCorrente);
					pstStorico.setInt(3, idSoggettoFisicoPrecedente);
					pstStorico.setInt(4, getUserId(context));
					pstStorico.execute();


					aggiungiAllaMail(thisStab, msgComunicazione, db, false, "OK",context);

				}
				else
				{
					listaStabilimentiNonModificati.add(thisStab);
					aggiungiAllaMail(thisStab, msgComunicazione, db, false, "KO",context);
				}

				RicercheAnagraficheTab.inserOpu(db, thisStab.getIdStabilimento());

			}

			for(Integer ii : listaImprese)
			{
				PreparedStatement pst = db.prepareStatement("update opu_operatore set note_internal_use_only_hd ='APPLICATA ERRATA CORRIGE SOGGETTO FISICO', trashed_date = current_date " +
						"where id not in (select id_operatore from opu_stabilimento where id_operatore = opu_operatore.id and trashed_date is null) and opu_operatore.id = ?");
				pst.setInt(1, ii);
				pst.execute();
			}
			msgComunicazione.append("</table>");
			HashMap<String,String> configs = new HashMap<String,String>();
			configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
			configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
			configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
			configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
			configs.put("mail.smtp.ssl.enable","true");
			configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
			configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			configs.put("mail.smtp.socketFactory.fallback", "false");
			
			PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
			try {
				sender.sendMail("Comunicazione Errata Corrige Dati Impresa",msgComunicazione.toString(),"gisasuap@cert.izsmportici.it", "gisadev@smail.it", null);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			


		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			super.freeConnection(context, db);
		}

		context.getRequest().setAttribute("ListaStabilimentiNonModificati", listaStabilimentiNonModificati);
		if(listaStabilimentiNonModificati.size()==0)
			return "ApplicataErrataCorrigeOK";

		return  "ApplicataErrataCorrigeKO";
	}
	public String executeCommandErrataCorrigeDatiStabilimento(ActionContext context) throws IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	{

		Connection db = null ;
		StabilimentoList listaStabilimentiNonModificati = new StabilimentoList();
		try
		{
			db = super.getConnection(context);


			LookupList TipoImpresaList = new LookupList(db,"lookup_opu_tipo_impresa");
			TipoImpresaList.addItem(-1, "Seleziona Tipo Impresa");
			TipoImpresaList.setRequired(true);
			context.getRequest().setAttribute("TipoImpresaListStab", TipoImpresaList);

			LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");
			TipoSocietaList.setRequired(true);
			context.getRequest().setAttribute("TipoSocietaListStab", TipoSocietaList);




			Stabilimento thisStab = new Stabilimento(db, Integer.parseInt(context.getParameter("idStabilimento")));

			RicercaOpu rOpu = new RicercaOpu(db,thisStab.getIdStabilimento(),"id_stabilimento");

			ArrayList<LineaProduttiva> lpListAttuali = rOpu.getListaControlliPerLinea();

			context.getRequest().setAttribute("stabId", thisStab.getIdStabilimento());
			StringBuffer msgComunicazione= new StringBuffer();//"In data "+sdf.format(new Date(System.currentTimeMillis()))+" l'utente con username: "+getUser(context, getUserId(context)).getUsername()+" Ha Effettuato una Errata Corrige sul soggetto Fisico presso i Seguenti Stabilimenti :\n\n ";


			aggiongiIntestazioneMailVariazioneStab(msgComunicazione);
			String[] lineeSelezionate =context.getRequest().getParameterValues("idRelStabLp") ;
			if(lineeSelezionate!=null && lineeSelezionate.length>0)
			{
				for (int i=0;i<lineeSelezionate.length;i++)
				{

					int numCu = 0 ;
					LineaProduttiva lpp=null;

					for(LineaProduttiva lpAttaule : lpListAttuali)
					{
						if(lpAttaule.getId_rel_stab_lp()==Integer.parseInt(lineeSelezionate[i]))
						{
							lpp = lpAttaule;

						}
					}
					if(context.getParameter("idLineaProduttiva"+lineeSelezionate[i])!=null)
					{
						int idNuovaLinea = Integer.parseInt(context.getParameter("idLineaProduttiva"+lineeSelezionate[i]));




						LineaProduttiva lpNuova = new LineaProduttiva();
						lpNuova.queryRecordScelta(db, idNuovaLinea);

						HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>> valoriCampiEstesi =
								costruisciValoriCampiEstesi(context,db,lpNuova.getIdRelazioneAttivita());


						lpNuova.addCampiEstesi(valoriCampiEstesi);

						String sql = "select * from opu_errata_corrige_stabilimento_variazione_linee_attivita(?,?,?,?)";
						PreparedStatement pst =db.prepareStatement(sql);
						pst.setInt(1, Integer.parseInt(lineeSelezionate[i]));
						pst.setInt(2, idNuovaLinea);
						pst.setInt(3, getUserId(context));
						pst.setString(4, context.getParameter("codice_nazionale_"+idNuovaLinea));
						ResultSet rs =  pst.executeQuery();
						if(rs.next())
						{
							lpNuova.setId(rs.getInt(1));
						}

						if(lpNuova.getCampiEstesi()!=null)
							for(Integer idLineeMobiliHtmlFields : lpNuova.getCampiEstesi().keySet())
							{
								//PreparedStatement  pst0 = db.prepareStatement("insert into linee_mobili_fields_value(id_opu_rel_stab_linea,id_linee_mobili_html_fields,valore_campo,data_inserimento,id_utente_inserimento) values(?,?,?,current_timestamp,?)");
								//select * from public.dbi_insert_campi_estesi(id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, id_utente_inserimento, id_opu_rel_stab_linea, riferimento_org_id)
								PreparedStatement  pst0 = db.prepareStatement("select * from public.dbi_insert_campi_estesi(-1, ?, ?, -1, ?, ?, -1)");

								int idRelStabLinea = lpNuova.getId();

								//pst0.setInt(1,idRelStabLinea);
								//pst0.setInt(2,idLineeMobiliHtmlFields);
								//pst0.setInt(4,getUserId(context));
								pst0.setInt(1,idLineeMobiliHtmlFields);
								pst0.setInt(3,getUserId(context));
								pst0.setInt(4,idRelStabLinea);
								
								String valoreCampo = "" ;

								if (lpNuova.getCampiEstesi().get(idLineeMobiliHtmlFields).size()>1)
								{
									for (LineaProduttivaCampoEsteso valore : lpNuova.getCampiEstesi().get(idLineeMobiliHtmlFields))
									{
										valoreCampo+=""+valore.getValore()+";";

									}
								}
								else
								{
									valoreCampo = lpNuova.getCampiEstesi().get(idLineeMobiliHtmlFields).get(0).getValore();
								}

								//pst0.setString(3, valoreCampo);
								pst0.setString(2, valoreCampo);
								pst0.executeUpdate();


								pst0.close();


							}
						GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
						gBdu.inserisciNuovaSciaBdu(thisStab.getIdStabilimento());

						aggiungiAllaMailVariazioneStab(thisStab, msgComunicazione, db, false, "OK", context, lpp, lpNuova.getDescrizione_linea_attivita());

					}
					else
					{
						/*Contrallare di aver selezionato almeno una linea nuova*/
						break;

					}

				}
				RicercheAnagraficheTab.inserOpu(db, thisStab.getIdStabilimento());
			}

			else{
				/*Contrallare di aver selezionato almeno una linea da variare*/
			}


			msgComunicazione.append("</table>");
			HashMap<String,String> configs = new HashMap<String,String>();
			configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
			configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
			configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
			configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
			configs.put("mail.smtp.ssl.enable","true");
			configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
			configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			configs.put("mail.smtp.socketFactory.fallback", "false");
			
			PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
			try {
				sender.sendMail("Comunicazione Errata Corrige Dati Stabilimento",msgComunicazione.toString(),"gisasuap@cert.izsmportici.it", "gisadev@usmail.it", null);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			


		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
		finally
		{
			super.freeConnection(context, db);
		}

		return "ApplicataErrataCorrigeOK";

	}

	public static HashMap<Integer, ArrayList<LineaProduttivaCampoEsteso>> costruisciValoriCampiEstesi(ActionContext context,Connection db, int idRelazioneAttivita) throws SQLException {


		PreparedStatement pst1 = db.prepareStatement("select * from linee_mobili_html_fields where id_linea = ? "); /*per non prendere i valori dei dummy */
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


		}

		return valoriCampiEstesi;

	}
	private int getIdAsl(Connection db,int idComune) throws SQLException
	{
		int idAsl=-1;
		String sql = "select codiceistatasl::int from comuni1 where id = ?";
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = null ;
		pst.setInt(1, idComune);
		rs=pst.executeQuery();
		if(rs.next())
			idAsl=rs.getInt(1);
		return idAsl;
	}
	private boolean verificaAslStabilimento(Stabilimento thisStab,Operatore op,Connection db) throws SQLException
	{
		int idAsl=-1;

		if(thisStab.getTipoAttivita()==Stabilimento.ATTIVITA_MOBILE && op.getTipo_impresa()==1)
		{
			idAsl=getIdAsl(db, op.getRappLegale().getIndirizzo().getComune());

			if(thisStab.getIdAsl()==idAsl)
			{
				return true ;
			}
			else
			{
				return false;
			}
		}
		else
		{
			/*Se Fissa la sede operativa non e' oggetto di errata corrige per cui non c'e il rischio che cambi l'asl.*/
			return true ;

		}


	}



	private void aggiungiAllaMail(Stabilimento stabilimento,StringBuffer messaggio,Connection db,boolean preModifica,String esito,ActionContext context) throws SQLException
	{

		LookupList TipoImpresaListStab = new LookupList(db,"lookup_opu_tipo_impresa");
		TipoImpresaListStab.addItem(-1, "Seleziona Tipo Impresa");
		TipoImpresaListStab.setRequired(true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		LookupList TipoSocietaList = new LookupList(db,"lookup_opu_tipo_impresa_societa");
		TipoSocietaList.addItem(-1, "Seleziona Tipo Societa");

		LookupList listaToponimi = new LookupList();
		listaToponimi.setTable("lookup_toponimi");
		listaToponimi.buildList(db);

		messaggio.append("<tr >");
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+sdf.format(new Date(System.currentTimeMillis()))+" </div></td>");
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+getUser(context, getUserId(context)).getUsername()+" </div></td>");

		messaggio.append("<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >");
		messaggio.append(toHtml2(stabilimento.getOperatore().getRappLegale().getCognome()) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getNome()) + " "+toHtml2(stabilimento.getOperatore().getRappLegale().getCodFiscale()) +"<br>");
		messaggio.append(toHtml2(stabilimento.getOperatore().getRappLegale().getComuneNascita()) +" "+toDateasString(stabilimento.getOperatore().getRappLegale().getDataNascita()) + " "+toHtml2(stabilimento.getOperatore().getRappLegale().getSesso())+"<br>");
		messaggio.append(toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()) +"<br>");
		messaggio.append(toHtml2( listaToponimi.getSelectedValue(stabilimento.getOperatore().getRappLegale().getIndirizzo().getToponimo())) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getVia())+" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getCivico())+"<br>");
		messaggio.append("</div>");
		messaggio.append("</td>	<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >");
		messaggio.append("RAGIONE SOCIALE : "+ toHtml2(stabilimento.getOperatore().getRagioneSociale()) +"<br>");  
		messaggio.append("TIPO IMPRESA : "+ TipoImpresaListStab.getSelectedValue(stabilimento.getOperatore().getTipo_impresa()) +"<br>");  
		messaggio.append((stabilimento.getOperatore().getTipo_societa()>0)? "TIPO SOCIETA : "+ TipoSocietaList.getSelectedValue(stabilimento.getOperatore().getTipo_societa()) +"<br>" :""); 

		messaggio.append("PARTITA IVA : "+  toHtml2(stabilimento.getOperatore().getPartitaIva())+"<br>");   

		if(stabilimento.getOperatore().getSedeLegaleImpresa()!=null){ 
			messaggio.append("SEDE LEGALE "+ toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizioneComune()) +" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())+"<br>");
			messaggio.append( toHtml2( listaToponimi.getSelectedValue(stabilimento.getOperatore().getSedeLegaleImpresa().getToponimo()))   +" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getVia())+" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())+"<br>");
		}
		messaggio.append("</div></td><td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\">");
		messaggio.append("<div >"+toHtml2(stabilimento.getNumero_registrazione())+" </div></td>");	

		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+stabilimento.getIdAsl()+" </div></td>");

		messaggio.append("<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\">");
		messaggio.append("<div >");
		messaggio.append((stabilimento.getSedeOperativa().getIdIndirizzo()>0) ? "SEDE OPERATIVA : "+toHtml2(stabilimento.getSedeOperativa().getDescrizioneComune()) + " "+toHtml2(stabilimento.getSedeOperativa().getVia())+ ","+toHtml2(stabilimento.getSedeOperativa().getCivico())+"<br>" :"");
		messaggio.append( (stabilimento.getTipoAttivita()==1) ? "TIPO ATTIVITA : FISSA" : "TIPO ATTIVITA : MOBILE" );

		messaggio.append("</div></td>");	

		messaggio.append("<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\">");
		messaggio.append("<div >"+((preModifica==true) ? "PRIMA DELLA MODIFICA":"DOPO LA MODIFICA - ESITO-"+esito)+"</div></td>");	

		messaggio.append("</tr>");
	}

	public void aggiongiIntestazioneMail(StringBuffer messaggio)
	{
		messaggio.append("<table cellpadding=\"8\" cellspacing=\"0\" border=\"2\" style=\"width: 100%\" class=\"pagedList\"><thead>");
		messaggio.append("<tr><th colspan=\"7\">ERRATA CORRIGE ESEGUITA SUI SEGUENTI STABILIMENTI</th></tr><tr>"
				+ "<th><div align=\"center\"><strong>DATA OPERAZIONE</strong></div></th>"
				+ "<th><div align=\"center\"><strong>ESEGUITO DA</strong></div></th>"
				+ "<th><div align=\"center\"><strong>DATI SOGGETTO FISICO</strong></div></th>");


		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>DATI IMPRESA</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");   

		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>N.REG</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");

		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>ASL</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>SEDE PRODUTTIVA</strong>");
		messaggio.append("</div>");
		messaggio.append("</th> ");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>ESITO ERRATA CORRIGE</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("</tr> ");
	}




	private void aggiungiAllaMailVariazioneStab(Stabilimento stabilimento,StringBuffer messaggio,Connection db,boolean preModifica,String esito,ActionContext context,LineaProduttiva lineaVecchia,String lineaNew) throws SQLException
	{	

		LookupList listaToponimi = new LookupList();
		listaToponimi.setTable("lookup_toponimi");
		listaToponimi.buildList(db);

		LookupList TipoImpresaListStab = new LookupList(db,"lookup_opu_tipo_impresa");
		TipoImpresaListStab.addItem(-1, "Seleziona Tipo Impresa");
		TipoImpresaListStab.setRequired(true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		messaggio.append("<tr >");
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+sdf.format(new Date(System.currentTimeMillis()))+" </div></td>");
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+getUser(context, getUserId(context)).getUsername()+" </div></td>");

		messaggio.append("<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >");
		messaggio.append(toHtml2(stabilimento.getOperatore().getRappLegale().getCognome()) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getNome()) + " "+toHtml2(stabilimento.getOperatore().getRappLegale().getCodFiscale()) +"<br>");
		messaggio.append(toHtml2(stabilimento.getOperatore().getRappLegale().getComuneNascita()) +" "+toDateasString(stabilimento.getOperatore().getRappLegale().getDataNascita()) + " "+toHtml2(stabilimento.getOperatore().getRappLegale().getSesso())+"<br>");
		messaggio.append(toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()) +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()) +"<br>");
		messaggio.append( toHtml2( listaToponimi.getSelectedValue(stabilimento.getOperatore().getRappLegale().getIndirizzo().getToponimo()))  +" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getVia())+" "+toHtml2(stabilimento.getOperatore().getRappLegale().getIndirizzo().getCivico())+"<br>");
		messaggio.append("</div>");
		messaggio.append("</td>	<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >");
		messaggio.append("RAGIONE SOCIALE : "+ toHtml2(stabilimento.getOperatore().getRagioneSociale()) +"<br>");  
		messaggio.append("TIPO IMPRESA : "+ TipoImpresaListStab.getSelectedValue(stabilimento.getOperatore().getTipo_impresa()) +"<br>");  
		messaggio.append((stabilimento.getOperatore().getTipo_societa()>0)? "TIPO SOCIETA : "+ TipoImpresaListStab.getSelectedValue(stabilimento.getOperatore().getTipo_societa()) +"<br>" :""); 

		messaggio.append("PARTITA IVA : "+  toHtml2(stabilimento.getOperatore().getPartitaIva())+"<br>");   

		if(stabilimento.getOperatore().getSedeLegaleImpresa()!=null){ 
			messaggio.append("SEDE LEGALE "+ toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizioneComune()) +" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())+"<br>");
			messaggio.append(toHtml2(listaToponimi.getSelectedValue(stabilimento.getOperatore().getSedeLegaleImpresa().getToponimo())) +" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getVia())+" "+toHtml2(stabilimento.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia())+"<br>");
		}
		messaggio.append("</div></td><td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\">");
		messaggio.append("<div >"+toHtml2(stabilimento.getNumero_registrazione())+" </div></td>");	

		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+stabilimento.getIdAsl()+" </div></td>");

		messaggio.append("<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\">");
		messaggio.append("<div >");
		messaggio.append((stabilimento.getSedeOperativa().getIdIndirizzo()>0) ? "SEDE OPERATIVA : "+toHtml2(stabilimento.getSedeOperativa().getDescrizioneComune()) + " "+toHtml2(stabilimento.getSedeOperativa().getVia())+ ","+toHtml2(stabilimento.getSedeOperativa().getCivico())+"<br>" :"");
		messaggio.append( (stabilimento.getTipoAttivita()==1) ? "TIPO ATTIVITA : FISSA" : "TIPO ATTIVITA : MOBILE" );

		messaggio.append("</div></td>");	
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+lineaVecchia.getDescrizione_linea_attivita()+" </div></td>");
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+lineaVecchia.getNumeroControlliUfficialiEseguiti()+" </div></td>");
		messaggio.append(" <td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\"><div >"+lineaNew+" </div></td>");		messaggio.append("<td valign=\"center\"  align=\"center\"  nowrap style=\"width: 10%;\">");
		messaggio.append("<div >"+((preModifica==true) ? "PRIMA DELLA MODIFICA":"DOPO LA MODIFICA - ESITO-"+esito)+"</div></td>");	

		messaggio.append("</tr>");
	}

	public void aggiongiIntestazioneMailVariazioneStab(StringBuffer messaggio)
	{
		messaggio.append("<table cellpadding=\"8\" cellspacing=\"0\" border=\"2\" style=\"width: 100%\" class=\"pagedList\"><thead>");
		messaggio.append("<tr><th colspan=\"7\">ERRATA CORRIGE ESEGUITA SUI SEGUENTI STABILIMENTI</th></tr><tr>"
				+ "<th><div align=\"center\"><strong>DATA OPERAZIONE</strong></div></th>"
				+ "<th><div align=\"center\"><strong>ESEGUITO DA</strong></div></th>"
				+ "<th><div align=\"center\"><strong>DATI SOGGETTO FISICO</strong></div></th>");


		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>DATI IMPRESA</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");   

		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>N.REG</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");

		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>ASL</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>SEDE PRODUTTIVA</strong>");
		messaggio.append("</div>");
		messaggio.append("</th> ");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>LINEA ATTIVITA PRECEDENTE</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>NUM. CU SU LINEA</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>LINEA ATTIVITA VARIATA</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("<th>");
		messaggio.append("<div align=\"center\">");
		messaggio.append("<strong>ESITO ERRATA CORRIGE</strong>");
		messaggio.append("</div>");
		messaggio.append("</th>");
		messaggio.append("</tr> ");
	}

	public static String toHtml2(String s) {

		return org.aspcfs.utils.StringUtils.toHtml2(s).trim().replaceAll("'","").toUpperCase().replaceAll("NULL", "");
	}

	public static String toDateasString(Timestamp time)
	{
		String toRet = "";
		try
		{ 
			if (time != null){
				java.sql.Date d = new java.sql.Date(time.getTime());
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				toRet=sdf.format(d);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return toRet;
	}




}
