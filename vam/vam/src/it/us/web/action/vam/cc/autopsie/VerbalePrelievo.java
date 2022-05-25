/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.autopsie;


import java.awt.Color;
import java.io.InputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.bytecode.Descriptor.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.Barcode128;



import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.decessi.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.BarCode;
import it.us.web.util.DateUtils;
import it.us.web.util.PdfUtil;
import it.us.web.util.report.PdfReport;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;


public class VerbalePrelievo extends GenericAction 
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
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		//Inizializzazione variabili utile al riempimento del documento
		Autopsia autopsia 		  = (Autopsia) persistence.find (Autopsia.class, interoFromRequest("idAutopsia") );	
		Animale animale 		  = autopsia.getCartellaClinica().getAccettazione().getAnimale();
		String keyDettaglioEsame  = stringaFromRequest("keyDettaglioEsame");
		int idOrgano 		      = Integer.parseInt(keyDettaglioEsame.split(";")[0].split("---")[0]);
		String organo 			  = keyDettaglioEsame.split(";")[0].split("---")[1];
		int idTipologia 		  = Integer.parseInt(keyDettaglioEsame.split(";")[1].split("---")[0]);
		String tipologia 		  = keyDettaglioEsame.split(";")[1].split("---")[1];
		String dettaglioRichiesta = ((keyDettaglioEsame.split(";").length>2)?(keyDettaglioEsame.split(";")[2]):(""));
		String codiceVerbale 	  = generaCodiceVerbale(idOrgano, idTipologia, autopsia.getId());
		Map<String, Object> mappaProprietaAddizionali = new Hashtable<String, Object>();
		
		//Gestione dati morte
		if(!animale.getDecedutoNonAnagrafe())
		{
			if (animale.getLookupSpecie().getId() == 1) 
			{
				RegistrazioniCaninaResponse res	= CaninaRemoteUtil.getInfoDecesso( animale.getIdentificativo(), utente, null, connection, req );
				mappaProprietaAddizionali.put("dataMorte", (res==null || res.getDataEvento()==null)?(""):(res.getDataEvento()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (res==null)?(""):(res.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (res==null || res.getDecessoValue()==null)?(""):(res.getDecessoValue()));
			}
			else if (animale.getLookupSpecie().getId() == 2)
			{
				RegistrazioniFelinaResponse rfr = FelinaRemoteUtil.getInfoDecesso(animale.getIdentificativo(), utente, null, connection, req );
				mappaProprietaAddizionali.put("dataMorte", (rfr==null || rfr.getDataEvento()==null)?(""):(rfr.getDataEvento()));
				mappaProprietaAddizionali.put("dataMorteCertezza", (rfr==null)?(""):(rfr.getDataMorteCertezza()));
				mappaProprietaAddizionali.put("causaMorte", (rfr==null || rfr.getDecessoValue()==null)?(""):(rfr.getDecessoValue()));
			}
			else if (animale.getLookupSpecie().getId() == 3)
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
		}	
		
		
		if(animale.getDecedutoNonAnagrafe())
		{
			mappaProprietaAddizionali.put( "sesso",  animale.getSesso());
			if(animale.getLookupSpecie().getId()==Specie.CANE)
			{
				if(animale.getTaglia()!=null)
					mappaProprietaAddizionali.put( "taglia", CaninaRemoteUtil.getTaglia(animale.getTaglia(), connection, req)  );
				mappaProprietaAddizionali.put( "razza",  CaninaRemoteUtil.getRazza(animale.getRazza(), connection, req) );
			}
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				mappaProprietaAddizionali.put( "razza",  FelinaRemoteUtil.getRazza(animale.getRazza(), connection, req) );
		}
		else if(animale.getLookupSpecie().getId()==Specie.SINANTROPO)
		{
			mappaProprietaAddizionali.put( "sesso",  animale.getSesso());
			mappaProprietaAddizionali.put( "razza",  animale.getSpecieSinantropoString() + " - " + animale.getRazzaSinantropo());
			if(animale.getTaglia()!=null)
				mappaProprietaAddizionali.put( "taglia", ((LookupTaglie)persistence.find(LookupTaglie.class, animale.getTaglia())).getDescription() );
		}
		else if(animale.getLookupSpecie().getId()==Specie.CANE)
		{
			ServicesStatus status = new ServicesStatus();
			Cane cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connection, req);
			mappaProprietaAddizionali.put( "sesso",  cane.getSesso());
			mappaProprietaAddizionali.put( "razza",  cane.getDescrizioneRazza());
			mappaProprietaAddizionali.put( "taglia",  (cane.getDescrizioneTaglia()!=null)?(cane.getDescrizioneTaglia()):(""));
		}
		else if(animale.getLookupSpecie().getId()==Specie.GATTO)
		{
			ServicesStatus status = new ServicesStatus();
			Gatto gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connection, req);
			mappaProprietaAddizionali.put( "sesso",  gatto.getSesso());
			mappaProprietaAddizionali.put( "razza",  gatto.getDescrizioneRazza());
			mappaProprietaAddizionali.put( "taglia",  (gatto.getDescrizioneTaglia()!=null)?(gatto.getDescrizioneTaglia()):(""));
		}
		
		//Gestione data nascita certezza/presunta
		mappaProprietaAddizionali.put("dataNascitaCertezza",(animale==null || animale.getDataNascita()==null)?(""):(animale.getDataNascitaCertezza()));

		//Gestione dettaglio esame(Organo, tipologia e dettagli richiesta)
		mappaProprietaAddizionali.put("organo", 			organo);
		mappaProprietaAddizionali.put("tipologia", 			tipologia);
		mappaProprietaAddizionali.put("dettaglioRichiesta", dettaglioRichiesta);
		
		//Gestione BarCode
		Barcode128 barCodeVerbale   = BarCode.getBarCode128(codiceVerbale);
		mappaProprietaAddizionali.put( "barCodeVerbale",    barCodeVerbale );
		
		//Riempimento dei campi
		InputStream template = this.getClass().getResourceAsStream( "VerbalePrelievo.pdf" );
		byte[] pdf =  PdfReport.fillDocument( template, autopsia, mappaProprietaAddizionali ) ;
		
		//Stampa su standard output
		String outputFileName = "Verbale di Prelievo - " + codiceVerbale + ".pdf";
		res.setContentType( "application/pdf" );
		res.setHeader( "Content-Disposition","attachment; filename=\"" + outputFileName + "\";" );
		ServletOutputStream sout = res.getOutputStream();	
		sout.write( pdf );
		sout.flush();
		
	}
	
	
	@SuppressWarnings("unchecked")
	private String generaCodiceVerbale(int idOrgano, int idTipologia, int idAutopsia) 
	{
		String codiceVerbale = "VP-";
		DecimalFormat decimalFormat = new DecimalFormat( "00000" );
		codiceVerbale += decimalFormat.format(idAutopsia); 
		decimalFormat = new DecimalFormat( "000" );
		codiceVerbale += decimalFormat.format(idOrgano); 
		decimalFormat = new DecimalFormat( "00" );
		codiceVerbale += decimalFormat.format(idTipologia); 
		return codiceVerbale;
	}

}



















