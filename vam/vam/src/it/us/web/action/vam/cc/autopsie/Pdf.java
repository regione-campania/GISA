/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.autopsie;


import java.awt.Color;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;

import sun.security.krb5.internal.ccache.CCacheInputStream;

import com.itextpdf.text.Element;


import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.decessi.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.PdfUtil;
import it.us.web.util.report.PdfReport;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;


public class Pdf extends GenericAction 
{

	@Override
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
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		Autopsia autopsia = (Autopsia) persistence.find (Autopsia.class, interoFromRequest("id") );	
		Animale animale = autopsia.getCartellaClinica().getAccettazione().getAnimale();
		AnimaleAnagrafica animaleAnagrafica = AnimaliUtil.getAnagrafica(animale, persistence, utente, new ServicesStatus(), connection, req );
		
		//PARTE STATICA - I PARTE
		Map<String, Object> mappaProprietaAddizionali = new Hashtable<String, Object>();
		ServicesStatus status = new ServicesStatus();
		
		if (animaleAnagrafica.getTaglia()!=null && !animaleAnagrafica.getTaglia().equals(""))
			mappaProprietaAddizionali.put("tagliaAnimale", animaleAnagrafica.getTaglia());
		else 
			mappaProprietaAddizionali.put("tagliaAnimale", "N.D.");
	
		if (animaleAnagrafica.getRazza()!=null && !animaleAnagrafica.getRazza().equals(""))
			mappaProprietaAddizionali.put("razzaAnimale", animaleAnagrafica.getRazza());
		else
			mappaProprietaAddizionali.put("razzaAnimale", "N.D.");
		
		if (animaleAnagrafica.getMantello()!=null && !animaleAnagrafica.getMantello().equals(""))
			mappaProprietaAddizionali.put("mantelloAnimale", animaleAnagrafica.getMantello());
		else
			mappaProprietaAddizionali.put("mantelloAnimale", "N.D.");
		
		if (animaleAnagrafica.getSterilizzato()!=null && !animaleAnagrafica.getSterilizzato().equals(""))
			mappaProprietaAddizionali.put("sterilizzato", animaleAnagrafica.getSterilizzato());
		else
			mappaProprietaAddizionali.put("sterilizzato", "N.D.");
		
		if(!animale.getDecedutoNonAnagrafe())
		{
			mappaProprietaAddizionali.put("randagio", "Non previsto");
			if (animale.getLookupSpecie().getId() == Specie.CANE) 
			{
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, null, connection, req );
				mappaProprietaAddizionali.put("dataMorte", (res==null || res.getDataEvento()==null)?(""):(res.getDataEvento()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (res==null)?(""):(res.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (res==null || res.getDecessoValue()==null)?(""):(res.getDecessoValue()));
			}
			else if (animale.getLookupSpecie().getId() == Specie.GATTO)
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso(animale.getIdentificativo(), utente, null, connection, req );
				mappaProprietaAddizionali.put("dataMorte", (rfr==null || rfr.getDataEvento()==null)?(""):(rfr.getDataEvento()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (rfr==null)?(""):(rfr.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (rfr==null || rfr.getDecessoValue()==null)?(""):(rfr.getDecessoValue()));

			}
			else if (animale.getLookupSpecie().getId() == Specie.SINANTROPO)
			{
				RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, animale);
				mappaProprietaAddizionali.put("dataMorte", (rsr==null || rsr.getDecessoValue()==null)?(""):(rsr.getDecessoValue()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (rsr==null)?(""):(rsr.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (rsr==null || rsr.getDecessoValue()==null)?(""):(rsr.getDecessoValue()));	
			}			
		}
		else
		{
			mappaProprietaAddizionali.put("dataMorte", (animale==null || animale.getDataMorte()==null)?(""):(animale.getDataMorte()));
			mappaProprietaAddizionali.put("dataMorteCertezza", (animale==null)?(""):(animale.getDataMorteCertezza()));
			mappaProprietaAddizionali.put("causaMorte", (animale==null || animale.getCausaMorte()==null)?(""):(animale.getCausaMorte().getDescription()));
			mappaProprietaAddizionali.put("randagio", (autopsia.getCartellaClinica().getAccettazione().getAnimale().getRandagio()?("Si"):("No")));
		}	
		InputStream template = this.getClass().getResourceAsStream( "RefertoEsameNecroscopico1.pdf" );
		byte[] parteStatica =  PdfReport.fillDocument( template, autopsia, mappaProprietaAddizionali ) ;
		
		//PARTE DINAMICA
		PdfReport pdfReport = new PdfReport();
		
		//Parte pdf per le patologie
		List<Object> patologieList = new ArrayList<Object>((ArrayList<AutopsiaOrganoPatologie>) persistence.getNamedQuery("GetAutopsiaOrganoPatologiaFromAutopsia").setInteger("idAutopsia", autopsia.getId()).list());
		byte[] patologie = null;
		if(!patologieList.isEmpty())
		{
			pdfReport.setColoreIntestazione(new Color(0,0,0));
			pdfReport.setIntestazione("Esame morfologico degli organi");
			pdfReport.addColonna("lookupOrganiAutopsia.description", "Organo",   3000, 3000);
			pdfReport.addColonna("descrizioneReferto", "Patologia", 3000, 3000);
			pdfReport.addColonna("noteReferto", "", 3000, 3000);
			pdfReport.setCellaTabAllineamentoOrizzontale(Element.ALIGN_LEFT);
			pdfReport.setPrintDataReport(false);
			pdfReport.setPrintNumeroPagina(false);
			pdfReport.setItems(patologieList);
			patologie = pdfReport.renderItems();
		}
		
		//Parte pdf per la sezione dettaglio esami
		List<Object> listSde = new ArrayList<Object>(autopsia.getDettaglioEsamiReferto());
		byte[] dettaglioEsami = null;
		if(!listSde.isEmpty())
		{
			pdfReport = new PdfReport();
			pdfReport.setColoreIntestazione(new Color(0,0,0));
			pdfReport.setIntestazione("Sezione dettaglio esami");
			pdfReport.addColonna("organo", "Organo",   3000, 3000);
			pdfReport.addColonna("tipo", "Tipologia", 3000, 3000);
			pdfReport.addColonna("dettaglio", "Dettaglio richiesta", 3000, 3000);
			pdfReport.addColonna("esiti", "Esiti", 3000, 3000);
			pdfReport.setCellaTabAllineamentoOrizzontale(Element.ALIGN_LEFT);
			pdfReport.setPrintDataReport(false);
			pdfReport.setPrintNumeroPagina(false);
			pdfReport.setItems(listSde);
			dettaglioEsami = pdfReport.renderItems();
		}
		
		//Parte pdf per esami istopatologici
		List<Object> esamiIstopatologiciList = new ArrayList<Object>(autopsia.getCartellaClinica().getEsameIstopatologicos());
		byte[] esamiIstopatologici = null;
		if(!esamiIstopatologiciList.isEmpty())
		{
			pdfReport = new PdfReport();
			pdfReport.setColoreIntestazione(new Color(0,0,0));
			pdfReport.setIntestazione("Sezione esami istopatologici");
			pdfReport.addColonna("dataRichiesta", "Data",   3000, 3000);
			pdfReport.addColonna("numero", "Numero",   3000, 3000);
			pdfReport.addColonna("sedeLesione", "Sede Lesione", 3000, 3000);
			pdfReport.addColonna("diagnosiReferto", "Diagnosi", 3000, 3000);
			pdfReport.setCellaTabAllineamentoOrizzontale(Element.ALIGN_LEFT);
			pdfReport.setPrintDataReport(false);
			pdfReport.setPrintNumeroPagina(false);
			pdfReport.setItems(esamiIstopatologiciList);
			esamiIstopatologici = pdfReport.renderItems();
		}
		
		//Parte pdf per cause morte finale
		List<Object> cmfList = new ArrayList<Object>(autopsia.getCmf());
		byte[] cmf = null;
		if(!cmfList.isEmpty())
		{
			pdfReport = new PdfReport();
			pdfReport.setColoreIntestazione(new Color(0,0,0));
			pdfReport.setIntestazione("Cause del decesso finale");
			pdfReport.addColonna("lookupCMF.description", "Descrizione",   3000, 3000);
			pdfReport.addColonna("provataReferto", "Provata/Sospetta", 3000, 3000);
			pdfReport.setCellaTabAllineamentoOrizzontale(Element.ALIGN_LEFT);
			pdfReport.setPrintDataReport(false);
			pdfReport.setPrintNumeroPagina(false);
			pdfReport.setItems(cmfList);
			cmf = pdfReport.renderItems();
		}
		
		//PARTE STATICA - II PARTE
		template = this.getClass().getResourceAsStream( "RefertoEsameNecroscopico2.pdf" );
		byte[] parteStatica2 =  PdfReport.fillDocument( template, autopsia, mappaProprietaAddizionali ) ;
		
		//Concatenazione dei pdf generati
		byte[] pdfFinale = parteStatica;
		if(!patologieList.isEmpty())
			pdfFinale= PdfUtil.join( parteStatica, patologie );
		if(!listSde.isEmpty())
			pdfFinale = PdfUtil.join( pdfFinale, dettaglioEsami);
		if(!esamiIstopatologiciList.isEmpty())
			pdfFinale = PdfUtil.join( pdfFinale, esamiIstopatologici);
		if(!cmfList.isEmpty())
			pdfFinale = PdfUtil.join( pdfFinale, cmf);
		pdfFinale = PdfUtil.join( pdfFinale, parteStatica2 );
		
		//Stampa su standard output
		String outputFileName = "Referto esame necroscopico - " + autopsia.getId() + ".pdf";
		res.setContentType( "application/pdf" );
		res.setHeader( "Content-Disposition","attachment; filename=\"" + outputFileName + "\";" );
		ServletOutputStream sout = res.getOutputStream();	
		sout.write( pdfFinale );
		sout.flush();
		
	}

}



















