/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiIstopatologici;


import java.io.InputStream;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;

import com.itextpdf.text.pdf.Barcode128;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.BarCode;
import it.us.web.util.report.PdfReport;
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
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("istopatologico");
	}

	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		EsameIstopatologico esame = (EsameIstopatologico) persistence.find (EsameIstopatologico.class, interoFromRequest("id") );	
		if(esame.getTipoDiagnosi().getId()!=3)
			esame.setDiagnosiNonTumorale("");
		
		
		String outputFileName = "RichiestaIstopatologico - IST" + esame.getId() + ".pdf";
		InputStream template = this.getClass().getResourceAsStream( "EsameIstopatologico.pdf" );
		
		res.setContentType( "application/pdf" );
		res.setHeader( "Content-Disposition","attachment; filename=\"" + outputFileName + "\";" );
		
		Barcode128 barCodeEsame   = BarCode.getBarCode128(esame.getNumero());
		Barcode128 barCodeAnimale = BarCode.getBarCode128(esame.getCartellaClinica().getAccettazione().getAnimale().getIdentificativo());
		
		Map<String, Object> mappaProprietaAddizionali = new Hashtable<String, Object>();
		mappaProprietaAddizionali.put( "barCodeEsame",    barCodeEsame );
		mappaProprietaAddizionali.put( "barCodeAnimale",  barCodeAnimale );
		
		
		if(esame.getCartellaClinica().getAccettazione().getAnimale().getDecedutoNonAnagrafe())
		{
			mappaProprietaAddizionali.put( "sesso",  esame.getCartellaClinica().getAccettazione().getAnimale().getSesso());
			if(esame.getCartellaClinica().getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.CANE)
			{
				if(esame.getCartellaClinica().getAccettazione().getAnimale().getTaglia()!=null)
					mappaProprietaAddizionali.put( "taglia", CaninaRemoteUtil.getTaglia(esame.getCartellaClinica().getAccettazione().getAnimale().getTaglia(), connection, req)  );
				if(esame.getCartellaClinica().getAccettazione().getAnimale().getRazza()!=null)
					mappaProprietaAddizionali.put( "razza",  CaninaRemoteUtil.getRazza(esame.getCartellaClinica().getAccettazione().getAnimale().getRazza(), connection, req) );
			}
			else if(esame.getCartellaClinica().getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.GATTO&& esame.getCartellaClinica().getAccettazione().getAnimale().getRazza()!=null)
				mappaProprietaAddizionali.put( "razza",  FelinaRemoteUtil.getRazza(esame.getCartellaClinica().getAccettazione().getAnimale().getRazza(), connection, req) );
		}
		else if(esame.getCartellaClinica().getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.SINANTROPO)
		{
			mappaProprietaAddizionali.put( "sesso",  esame.getCartellaClinica().getAccettazione().getAnimale().getSesso());
			mappaProprietaAddizionali.put( "razza",  esame.getCartellaClinica().getAccettazione().getAnimale().getSpecieSinantropoString() + " - " + esame.getCartellaClinica().getAccettazione().getAnimale().getRazzaSinantropo());
		}
		else if(esame.getCartellaClinica().getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.CANE)
		{
			ServicesStatus status = new ServicesStatus();
			Cane cane = CaninaRemoteUtil.findCane(esame.getCartellaClinica().getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, req);
			mappaProprietaAddizionali.put( "sesso",  cane.getSesso());
			mappaProprietaAddizionali.put( "razza",  cane.getDescrizioneRazza()==null?"":cane.getDescrizioneRazza());
			mappaProprietaAddizionali.put( "taglia",  cane.getDescrizioneTaglia()==null?"":cane.getDescrizioneTaglia());
		}
		else if(esame.getCartellaClinica().getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.GATTO)
		{
			ServicesStatus status = new ServicesStatus();
			Gatto gatto = FelinaRemoteUtil.findGatto(esame.getCartellaClinica().getAccettazione().getAnimale().getIdentificativo(), utente, status, connection, req);
			mappaProprietaAddizionali.put( "sesso",  gatto.getSesso());
			mappaProprietaAddizionali.put( "razza",  gatto.getDescrizioneRazza()==null?"":gatto.getDescrizioneRazza());
			mappaProprietaAddizionali.put( "taglia",  gatto.getDescrizioneTaglia()==null?"":gatto.getDescrizioneTaglia());
		}
		
		ServletOutputStream sout = res.getOutputStream();		
		sout.write( PdfReport.fillDocument( template, esame, mappaProprietaAddizionali ) );
		sout.flush();
		
	}

	

	
}



















