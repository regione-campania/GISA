/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.util;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.nuovi_report.base.Filtro;
import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class StampaPdf extends CFSModule{

	
	private Filtro filtro = null;
	
	public StampaPdf(ActionContext context, Filtro filter) { 
		
		setFiltro(filter);
	}
	
	public void setFiltro(Filtro filter) {
		this.filtro = filter;
	}
	
	public Filtro getFiltro() {
			return this.filtro;
	}
	
	//metodo per stampare le informazioni dei cani microchippati in un pdf
	public void stampaChip (ActionContext context, Filtro filter) {
		
		try { 
			HttpServletResponse res = context.getResponse();
			
			//recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "nuovi_report_pdf_template");
		    PdfReader reader = new PdfReader( reportDir + "CANI_MICROCHIPPATI.pdf");
			
		    //scrittura nel file
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    PdfStamper stamper = new PdfStamper(reader, out );
		    AcroFields form = stamper.getAcroFields();
		    
		   form.setField("asl",(filter.getAsl()==null)?(""):(filter.getAsl()));
			
			//In millisecondi 
			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			
			form.setField("data",dataOdierna);
		    form.setField("totale",""+filter.getTotCani());
		    form.setField("dal",(filter.getEntered2()==null)?(""):(filter.getEntered2()));
		    form.setField("al",(filter.getFine1()==null)?(""):(filter.getFine1()));
		    
		    if(filter.getStato().equals("1")){
		    	form.setField("stato","DECEDUTI");
		    }
		    else if(filter.getStato().equals("2")){
		    	form.setField("stato","VIVI");
		    }
		    else
		    	form.setField("stato","TUTTI");
		    
		    if(filter.getDetails().equals("4"))
		    form.setField("con","TATUAGGIO");
		    else
		    	form.setField("con","MICROCHIP");
		    
		    stamper.setFormFlattening( true );
		   
		    stamper.close();
		 	res.setContentType("application/pdf");
		 	res.getOutputStream().write(out.toByteArray());
	  		} catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
		}
	}
	
	
	//metodo per stampare le informazioni dei cani sterilizzati in un pdf
	public void stampaSter (ActionContext context, Filtro filter) {
		
		try { 
			HttpServletResponse res = context.getResponse();
			
			//recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "nuovi_report_pdf_template");
		    PdfReader reader = new PdfReader( reportDir + "CANI_STERILIZZATI.pdf");
			
		    //scrittura nel file
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    PdfStamper stamper = new PdfStamper(reader, out );
		    AcroFields form = stamper.getAcroFields();
		  //In millisecondi 
			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
		    //controllo sulle asl
		    if ( filter.getId_asl() == -1 ) {
		    	form.setField( "asl","TUTTE" );
		    }
		    else {
		    	form.setField("asl",(filter.getAsl()==null)?(""):(filter.getAsl()));
		    }
		    form.setField("data",dataOdierna);
		  //  form.setField("totale",""+filter.getTotCaniSter());
		    form.setField("dal",(filter.getEntered2()==null)?(""):(filter.getEntered2()));
		    form.setField("al",(filter.getFine1()==null)?(""):(filter.getFine1()));
		    if(filter.getStatoC().equals("Reimmissione")){
		    form.setField("stato","REIMMESSI");
		    form.setField("totale",""+filter.getTotCaniReimmessi());
		    
		    }
		    if(filter.getStatoC().equals("xxx")){
			    form.setField("stato","IN CANILE");
			    }
		    if(filter.getStatoC().equals("Adozione")){
			    form.setField("stato","ADOTTATI");
			    form.setField("totale",""+filter.getTotCaniAdottati());
			   }
			    
		    if (filter.getContributo().equals("4"))
		    form.setField("contributi","Con");
		    else{
		    	if (filter.getContributo().equals("5"))
		    		form.setField("contributi","Senza");
		    }
		    if(filter.getCattura().equals("6"))
		    form.setField("cattura","SI");
		    else
		    	form.setField("cattura","NO");
			
		    stamper.setFormFlattening( true );
		    stamper.close();
		 	
		    res.setContentType("application/pdf");
	  		res.getOutputStream().write(out.toByteArray() );
		    
		} catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
		}
		
	}
	//metodo per stampare le informazioni dei cani sterilizzati in un pdf
	public void stampaCatt (ActionContext context, Filtro filter) {
		
		try { 
			HttpServletResponse res = context.getResponse();
			
			//recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "nuovi_report_pdf_template");
		    PdfReader reader = new PdfReader( reportDir + "CANI_CATTURATI.pdf");
			
		    //scrittura nel file
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    PdfStamper stamper = new PdfStamper(reader, out );
		    AcroFields form = stamper.getAcroFields();
		    
		  //In millisecondi 
			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			form.setField("data",dataOdierna);
		    form.setField("totale",""+filter.getTotCaniCatt());
    	    form.setField("asl", "napoli");
    	    if( filter.getAsl()!= null){
    	    form.setField("asl",filter.getAsl());}
    	    else
    	    {form.setField("asl","TUTTE");}
    	    form.setField("dal",filter.getEntered2());
    	    form.setField("al",filter.getFine1());
    	    form.setField("comune",filter.getComuneCattura());
		    stamper.setFormFlattening( true );
		    stamper.close();
		 	
		    res.setContentType("application/pdf");
	  		res.getOutputStream().write(out.toByteArray() );
		    
		} catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
		}}
	
	//metodo per stampare le informazioni dei cani sterilizzati in un pdf
	public void stampaCessioni (ActionContext context, Filtro filter) {
		
		try { 
			HttpServletResponse res = context.getResponse();
			
			//recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "nuovi_report_pdf_template");
		    PdfReader reader = new PdfReader( reportDir + "CESSIONI.pdf");
			
		    //scrittura nel file
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    PdfStamper stamper = new PdfStamper(reader, out );
		    AcroFields form = stamper.getAcroFields();
		    //In millisecondi 
			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
		    
		    //controllo sulle asl
		    if ( filter.getId_asl() == -1 ) {
		    	form.setField( "asl","TUTTE" );
		    }
		    else {
		    	form.setField("asl",(filter.getAsl()==null)?(""):(filter.getAsl()));
		    }
		    form.setField("data",dataOdierna);
		    form.setField("totale",""+filter.getTotCessioni());
		    form.setField("dal",(filter.getEntered2()==null)?(""):(filter.getEntered2()));
		    form.setField("al",(filter.getFine1()==null)?(""):(filter.getFine1()));
		    
		    stamper.setFormFlattening( true );
		    stamper.close();
		 	
		    res.setContentType("application/pdf");
	  		res.getOutputStream().write(out.toByteArray() );
		    
		} catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
		}
		
	}

	public void stampaSmar(ActionContext context, Filtro f) {
		// TODO Auto-generated method stub
		try { 
			HttpServletResponse res = context.getResponse();
			
			//recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "nuovi_report_pdf_template");
		    PdfReader reader = new PdfReader( reportDir + "SMARRITI.pdf");
			
		    //scrittura nel file
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    PdfStamper stamper = new PdfStamper(reader, out );
		    AcroFields form = stamper.getAcroFields();
		    //In millisecondi 
			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
		    
		    //controllo sulle asl
		    if ( f.getId_asl() == -1 ) {
		    	form.setField( "asl","TUTTE" );
		    }
		    else {
		    	form.setField("asl",(f.getAsl()==null)?(""):(f.getAsl()));
		    }
		    form.setField("data",dataOdierna);
		    form.setField("totale",""+f.getTotSmar());
		    form.setField("da",(f.getEntered2()==null)?(""):(f.getEntered2()));
		    form.setField("al",(f.getFine1()==null)?(""):(f.getFine1()));
		    
		    stamper.setFormFlattening( true );
		    stamper.close();
		 	
		    res.setContentType("application/pdf");
	  		res.getOutputStream().write(out.toByteArray() );
		    
		} catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
		}
	}

public void stampaRes (ActionContext context, Filtro filter) {
		
		try { 
			HttpServletResponse res = context.getResponse();
			
			//recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "nuovi_report_pdf_template");
		    PdfReader reader = new PdfReader( reportDir + "CANI_RESTITUITI.pdf");
			
		    //scrittura nel file
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    PdfStamper stamper = new PdfStamper(reader, out );
		    AcroFields form = stamper.getAcroFields();
		    
		   form.setField("asl",(filter.getAsl()==null)?(""):(filter.getAsl()));
			
			//In millisecondi 
			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			
			form.setField("data",dataOdierna);
		    form.setField("totale",""+filter.getTotCani());
		    form.setField("da",(filter.getEntered2()==null)?(""):(filter.getEntered2()));
		    form.setField("al",(filter.getFine1()==null)?(""):(filter.getFine1()));
		    form.setField("comune", filter.getComuneProprietario());
		    stamper.setFormFlattening( true );
		   
		    stamper.close();
		 	res.setContentType("application/pdf");
		 	res.getOutputStream().write(out.toByteArray());
	  		} catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
		}
	}

}