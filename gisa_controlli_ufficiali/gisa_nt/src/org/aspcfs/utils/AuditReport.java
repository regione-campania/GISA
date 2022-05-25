/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.awt.Color;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.AuditChecklist;
import org.aspcfs.checklist.base.AuditChecklistType;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
 
public class AuditReport extends PdfPageEventHelper
{
    public Image headerImage;
    public Image headerImage_asl;
    public PdfPTable table;
    public PdfGState gstate;
    public PdfTemplate tpl;
    public BaseFont helv;
    
    private ActionContext context ;
    public int id_asl ;
    
    private String asl;
    private String tipo_audit;
    private String descrizione_audit;
    private float larghezza_tabella;
    private Audit audit;
  
    

    public ActionContext getContext() {
		return context;
	}


	public void setContext(ActionContext context) {
		this.context = context;
	}

	
	public String getPathLogoAsl ()
	{
		String path = "" 	;
		String nomeFile = "";
		

		switch (id_asl)
		{
		case 201 : {
			
			nomeFile = "AVELLINO.jpg";
			break ;
		}
		case 202 : {
			
			nomeFile = "BENEVENTO.jpg";
			break ;
		}
case 203 : {
			
			nomeFile = "CASERTA.jpg";
			break ;
		}
case 204 : {
	
	nomeFile = "NAPOLI 1 CENTRO.jpg";
	break ;
}
case 205 : {
	
	nomeFile = "NAPOLI 2 NORD.jpg";
	break ;
}
case 206 : {
	
	nomeFile = "NAPOLI 3 SUD.jpg";
	break ;
}

case 207 : {
	
	nomeFile = "SALERNO.jpg";
	break ;
}

		 default :
		{
			nomeFile = "regionecampania.jpg";
			break ;
		}
		
		
		
		}
		
		
		return nomeFile ;
		
	}

	public void generatePdf(	OutputStream out)
throws DocumentException, SQLException
{

Document document = new Document( PageSize.A4, 15, 15, 60, 25 );
PdfWriter writer = PdfWriter.getInstance(document, out);
writer.setPageEvent( this );
document.open();
larghezza_tabella = document.right() - document.left();

float[] widths = { 0.20f, 0.80f };
PdfPTable infoAuditTable = new PdfPTable( widths );
infoAuditTable.getDefaultCell().setPadding(2);
infoAuditTable.setHeaderRows(1);
Phrase tab1tit = new Phrase( "Informazioni Audit", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
PdfPCell cell = new PdfPCell( new Paragraph(tab1tit) );
cell.setBackgroundColor(new Color(204, 204, 255));
cell.setColspan( 2 );
infoAuditTable.addCell( cell );

infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
Phrase rag_soc = new Phrase( "Denominazione Impresa", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
infoAuditTable.addCell( rag_soc );
infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
Phrase rag_soc_val = new Phrase( "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
infoAuditTable.addCell( rag_soc_val );


document.add(infoAuditTable);
document.close();
}

    
    
	public void generate(	OutputStream out,
									Audit audit,
									GenericBean org,
									ArrayList<CustomLookupList> checklistList,
									CustomLookupList checklistType,
									ArrayList<AuditChecklist> auditChecklist,
									ArrayList<AuditChecklistType> auditChecklistType,
									Connection db )
		throws DocumentException, SQLException
	{
	    LookupList categoriaRischioList	= new LookupList(db, "lookup_org_catrischio");
	    this.audit = audit;
	   
	    Ticket cu = new Ticket();
	    cu.queryRecord(db,new Integer(audit.getIdControllo()));
	    org.aspcfs.checklist.base.Organization thisOrg = new org.aspcfs.checklist.base.Organization(db,cu.getOrgId());
	    id_asl = cu.getSiteId();
		asl = (new LookupList(db, "lookup_site_id")).getSelectedValue( cu.getSiteId() );
		tipo_audit = categoriaRischioList.getSelectedValue( audit.getTipoChecklist() );
		descrizione_audit = "";//categoriaRischioList.getSelectedItems( org.getAccountSize() );
		
		Document document = new Document( PageSize.A4, 15, 15, 60, 25 );
		
		PdfWriter writer = PdfWriter.getInstance(document, out);

        writer.setPageEvent( this );
		
		document.open();
		
		//scritta sopra
		/*
		float[] wid = { 0.90f };
		PdfPTable intestazione = new PdfPTable( wid );
		intestazione.getDefaultCell().setPadding(1);
		intestazione.setHeaderRows(1);
		PdfPCell cellInt = new PdfPCell( new Paragraph("Audit") );
		cellInt.setColspan( 7 );
		intestazione.addCell( cellInt );
		
		document.add(intestazione);
		*/
		
		larghezza_tabella = document.right() - document.left();
		
		float[] widths = { 0.20f, 0.80f };
		
		PdfPTable infoAuditTable = new PdfPTable( widths );
		infoAuditTable.getDefaultCell().setPadding(2);
		infoAuditTable.setHeaderRows(1);
		Phrase tab1tit = new Phrase( "Informazioni Check List", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		PdfPCell cell = new PdfPCell( new Paragraph(tab1tit) );
		cell.setBackgroundColor(new Color(204, 204, 255));
		cell.setColspan( 2 );
		infoAuditTable.addCell( cell );
		
		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		Phrase rag_soc = new Phrase( "Ragione sociale", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( rag_soc );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
		Phrase rag_soc_val = new Phrase( thisOrg.getRagioneSociale(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( rag_soc_val );
		
		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		Phrase site_id = new Phrase( "A.S.L", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( site_id );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
		Phrase a_s_l = new Phrase( asl, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( a_s_l );
		
		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		Phrase catosa = new Phrase( "Categoria", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( catosa );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);		
		Iterator auditCkList			= auditChecklist.iterator();
		Iterator auditCkListType		= auditChecklistType.iterator();
		
		
		Iterator auditCkListType2		= auditChecklistType.iterator();
		
		Iterator<CustomLookupElement> itrTypeList = checklistType.iterator();
		Phrase catosaVal = new Phrase( tipo_audit, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( catosaVal );
		
		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		/*Phrase addr = new Phrase( "Indirizzo", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( addr );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
		Iterator iaddress = org.getAddressList().iterator();
		String address_value = "";
	    while (iaddress.hasNext())
	    {
			OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
			if( thisAddress.getType() == 1 )
			{
				address_value = thisAddress.toString();
			}
	    }

		Phrase addr_val = new Phrase( address_value, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( addr_val );
		
		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));*/
		Phrase numreg = new Phrase( "Numero Registrazione", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( numreg );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
		Phrase numregVal = new Phrase( audit.getNumeroRegistrazione() , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( numregVal );
		
		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		Phrase comgrp = new Phrase( "Componenti Gruppo", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( comgrp );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
		Phrase comgrpVal = new Phrase( audit.getComponentiGruppo(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( comgrpVal );

		infoAuditTable.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		Phrase livris = new Phrase( "Punteggio", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( livris );
		infoAuditTable.getDefaultCell().setBackgroundColor(Color.white);
		Phrase livrisVal = new Phrase( (audit.getLivelloRischioFinale() == -1) 
				? (audit.getLivelloRischio() + "") : (audit.getLivelloRischioFinale() ) + "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable.addCell( livrisVal );

		
		infoAuditTable.setTotalWidth( larghezza_tabella );
		document.add( infoAuditTable );
		
		document.add( new Phrase( " ", new Font(Font.TIMES_ROMAN, 8, Font.BOLDITALIC, Color.white) ) );
	
		float[] widths2 = { 0.20f, 0.80f };
		PdfPTable infoAuditTable2 = new PdfPTable( widths2 );
		infoAuditTable2.setTotalWidth( larghezza_tabella );
		infoAuditTable2.setHeaderRows(1);
		infoAuditTable2.getDefaultCell().setPadding(2);
		Phrase tab2tit = new Phrase( "Dettagli Addizionali", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		PdfPCell celle = new PdfPCell( new Paragraph(tab2tit) );
		celle.setBackgroundColor(new Color(204, 204, 255));
		celle.setColspan( 2 );
	    infoAuditTable2.addCell( celle );
	    
	    
	    infoAuditTable2.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
	    Phrase note = new Phrase( "Note", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
	    infoAuditTable2.addCell( note );
	    infoAuditTable2.getDefaultCell().setBackgroundColor(Color.white);
		Phrase noteVal = new Phrase( audit.getNote(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		infoAuditTable2.addCell( noteVal );
		
		document.add( infoAuditTable2 );
		
	    document.add( new Phrase( " ", new Font(Font.TIMES_ROMAN, 2, Font.BOLDITALIC, Color.white) ) );
		
		for( int indexChecklistList = 0; itrTypeList.hasNext(); indexChecklistList++ )
		{
			CustomLookupElement thisTypeList = itrTypeList.next();
			String checklistRange = thisTypeList.getValue("range");
			
			
			float[] widthsR = { 0.10f, 0.60f, 0.60f, 0.35f, 0.10f, 0.10f, 0.15f };
			PdfPTable risposte = new PdfPTable( widthsR );
			risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			risposte.setTotalWidth( larghezza_tabella );
			//PdfPTable risposte = new PdfPTable( 7 );
			risposte.getDefaultCell().setPadding(2);
			risposte.setHeaderRows( 2 );
			String checklistDescription = thisTypeList.getValue( "description" );
			Phrase phrase = new Phrase( checklistDescription, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			PdfPCell header = new PdfPCell( new Paragraph( phrase ) );
			header.setBackgroundColor(new Color(153, 255, 153));
			header.setColspan( 7 );
			CustomLookupList checklist = (CustomLookupList) checklistList.get(indexChecklistList);
			//risposte.addCell( phrase );
			risposte.addCell( header );
			
			risposte.getDefaultCell().setBackgroundColor(new Color(204, 204, 255));
			Phrase nulla = new Phrase( "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( nulla );
			Phrase dom = new Phrase( "Domanda", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( dom );
			Phrase domanda2 = new Phrase( "Ulteriore Quesito in caso di risposta affermativa", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( domanda2 );
			risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			Phrase modalita = new Phrase( "Modalita' di controllo", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( modalita );
			Phrase si = new Phrase( "SI", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( si );
			Phrase no = new Phrase( "NO", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( no );
			Phrase punt = new Phrase( "Punti", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			risposte.addCell( punt );
			risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
			risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			risposte.getDefaultCell().setBackgroundColor(Color.white);
			
			Iterator itrChecklist = checklist.iterator();
			
			String	descrizione	= null;
		    String	level		= null;
		    int		rowid		= 0;
			

		    
		   
		    while (itrChecklist.hasNext())
			{	
				rowid = (rowid != 1?1:2);
				
				CustomLookupElement thisChecklist = (CustomLookupElement) itrChecklist.next();
				boolean enabled = thisChecklist.getValue("enabled") == "true" ? true : false;
				boolean defaultItem = thisChecklist.getValue("default_item") == "true" ? true : false;
				String id = thisChecklist.getValue("id");
				String parentId = thisChecklist.getValue("parent_id");
				String domanda = thisChecklist.getValue("domanda");
				descrizione = thisChecklist.getValue("descrizione");
				String puntiNo = thisChecklist.getValue("punti_no");
				String puntiSi = thisChecklist.getValue("punti_si");
				level = thisChecklist.getValue("level");
				int code = id.startsWith("--") ? -1 : Integer.parseInt(id);
			      
			    boolean risposto	= false;
			    boolean risposta	= false;
			    int punti			= 0;
			    
			    AuditChecklist audiCkListTemp	= null;
				Iterator array					= auditChecklist.iterator();
				//System.out.println( "array.lenght = " + array.toString() );
				
				
				while( array.hasNext() && !risposto )
				{ 
					audiCkListTemp = (AuditChecklist)array.next();
					risposto = ( Integer.parseInt(id) == audiCkListTemp.getChecklistId() );
				}
				
				
				if( risposto )
				{
					risposta	= audiCkListTemp.getRisposta();
					code		= id.startsWith("--") ? -1 : Integer.parseInt(id);
					punti		= audiCkListTemp.getPunti();
				}
				
				if (parentId != null && !parentId.equals("-1"))
				{ 
					rowid = (rowid != 1?1:2);
					
				}
				else
				{
					
				}

		    	if(rowid == 1){
		    		 risposte.getDefaultCell().setBackgroundColor(new Color(230, 230, 230));
		    	}
		    	else{
		    		 risposte.getDefaultCell().setBackgroundColor(Color.white);
		    	}
				
				auditCkList=auditChecklist.iterator();
			      
				if(risposto)
			   	{
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
					risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					risposte.getDefaultCell().setUseAscender(true);
					risposte.getDefaultCell().setPaddingTop(3f);
					Phrase livel = new Phrase( level, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( livel );
					risposte.getDefaultCell().setPaddingTop(2f);
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
					
					if( parentId == null || parentId.equals("-1") )
					{
						Phrase domVal = new Phrase( domanda, new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
						risposte.addCell( domVal );
						Phrase vuoto = new Phrase( "", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
						risposte.addCell( vuoto );
					}
					else
					{
						Phrase vuoto = new Phrase( "", new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
						risposte.addCell( vuoto );
						Phrase domVal = new Phrase( domanda, new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
						risposte.addCell( domVal );
					}
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
					risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					Phrase desc = new Phrase( descrizione, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( desc );
					
					Phrase rispSi = new Phrase( (risposto && risposta) ? ("X") : ("") , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell(rispSi );
					Phrase rispNo = new Phrase( (risposto && !risposta) ? ("X") : ("") , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( rispNo );
					Phrase punteggi = new Phrase( punti + "" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( punteggi );
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
					risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			   	}
				else
				{
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
					risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					risposte.getDefaultCell().setPaddingTop(3f);
					Phrase livDiv = new Phrase( level , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( livDiv );
					risposte.getDefaultCell().setPaddingTop(2f);
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
					
					if( parentId == null || parentId.equals("-1") )
					{
						Phrase domDiv = new Phrase( domanda , new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
						risposte.addCell( domDiv );
						Phrase nulloDiv = new Phrase( "" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
						risposte.addCell( nulloDiv );
					}
					else
					{
						Phrase nullDiv = new Phrase( "" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
						risposte.addCell( nullDiv );
						Phrase domDiv = new Phrase( domanda , new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
						risposte.addCell( domDiv );
					}
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
					risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					Phrase descriz = new Phrase( descrizione , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( descriz );
					risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT);
					risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					Phrase nulla1 = new Phrase( "" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( nulla1 );
					Phrase nulla2 = new Phrase( "" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( nulla2 );
					Phrase nulla3 = new Phrase( "" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
					risposte.addCell( nulla3 );
				}
			}
		    
		    String nota	= "";
		    String add	= "";
		    String sub	= "";
		    String operazione	= null;
		    String numero		= "";
		    if(auditCkListType.hasNext())
		    {
		    	AuditChecklistType act = (AuditChecklistType)auditCkListType.next();
		    	operazione = act.getOperazione();

		    	if( operazione.equals("+") || operazione.equals("-") )
		    	{	
		    		nota	= act.getNota();
		    		numero	= Integer.toString( act.getValoreRange() );
		    	}
		    }
		    risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			Phrase levels = new Phrase( (Integer.parseInt(level) + 1) + ""  , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		    risposte.addCell(levels );
		    risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_JUSTIFIED_ALL );
			risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Phrase papiel = new Phrase( (( 
		    		"Esistono delle condizioni particolari non contemplate sopra che possono " +
		    		"diminuire o aumentare il punteggio di rischio? Se si, riportarle qui sotto " +
		    		"aggiungendo o sottraendo un punteggio nel range +" + checklistRange + ", -" +
		    		checklistRange + " da scrivere nella casella a lato" )) + ""  , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		    PdfPCell papiello = new PdfPCell( papiel );
		    papiello.setColspan( 3 );
		    risposte.addCell( papiello );
		    
		    risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			Phrase piu = new Phrase( "+", new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
			if(!"".equals(operazione) && operazione!=null){
			if( operazione.equals("+") ) {
				risposte.getDefaultCell().setBackgroundColor(new Color(204, 255, 204));
			}else {
				risposte.getDefaultCell().setBackgroundColor(Color.WHITE);
			}
			risposte.addCell( piu );
			
			Phrase meno = new Phrase( "-", new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
			if( operazione.equals("-") ) {
				risposte.getDefaultCell().setBackgroundColor(new Color(204, 255, 204));
			}else {
				risposte.getDefaultCell().setBackgroundColor(Color.WHITE);
			}
			risposte.addCell( meno );
		    risposte.getDefaultCell().setBackgroundColor(Color.WHITE);
			}
			Phrase num = new Phrase( numero, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		    risposte.addCell( num );
		    
		    if( nota.trim().length() != 0 )
		    {
		    	risposte.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
				risposte.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				Phrase notaFin = new Phrase( nota, new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		    	PdfPCell notaCell = new PdfPCell( notaFin );
		    	notaCell.setColspan( 7 );
		    	risposte.addCell( notaCell );
		    }
		    
		    document.add( risposte );
		    
		}
		
		//float[] widthsLiv = { 0.70f, 0.30f };
		PdfPTable livelloRischio = new PdfPTable( 1 );
		livelloRischio.setTotalWidth( larghezza_tabella );
		livelloRischio.getDefaultCell().setPadding(2);
		livelloRischio.getDefaultCell().setBackgroundColor(new Color(255, 255, 153));
		livelloRischio.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		livelloRischio.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		Phrase livelloRischioFineVal = new Phrase( "Punteggio          " + ( (audit.getLivelloRischioFinale() == -1) 
				? (audit.getLivelloRischio() + "") : (audit.getLivelloRischioFinale() ) + ""), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		livelloRischio.addCell( livelloRischioFineVal );
		
		document.add( livelloRischio );
		
		
		PdfPTable table2_empty = new PdfPTable (2);
		table2_empty.getDefaultCell().setBorderWidth(0);
		table2_empty.addCell(new Phrase( "			",null));
		document.add(table2_empty);
		
		
		PdfPTable table1 = new PdfPTable (2);
		table1.getDefaultCell().setBorderWidth(0);
		table1.getDefaultCell().setHorizontalAlignment( Element.ALIGN_BOTTOM);
   		table1.getDefaultCell().setVerticalAlignment( Element.ALIGN_LEFT );
   		Phrase tab1titVal1 = new Phrase( "DATA", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
   		table1.addCell(tab1titVal1);
   		table1.getDefaultCell().setHorizontalAlignment( Element.ALIGN_BOTTOM);
   		table1.getDefaultCell().setVerticalAlignment( Element.ALIGN_RIGHT);
   		Phrase tab1titVal2 = new Phrase( "FIRMA", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
   		table1.addCell(tab1titVal2);

   		Ticket ticket = new Ticket();
   		ticket.queryRecord(db, Integer.parseInt(audit.getIdControllo()));
   		
   		table1.addCell(new Phrase( "____________________________", new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleo()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoDue()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoTre()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoQuattro()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoCinque()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoSei()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoSette()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoOtto()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoNove()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( getValueofString(ticket.getComponenteNucleoDieci()), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) ));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "Per L'impresa/ stabilimento : ______________________",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		table1.addCell(new Phrase( "",  new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black)));
   		document.add(table1);
   		
		document.close();
	
	}
	
	public String getValueofString(String input)
	{
		if (input != null)
		{
			return input.toUpperCase() ;
		}
		return "" ;
	}
	
	 protected String getWebInfPath(ActionContext context, String moduleFolderName) {
		  if(context.getRequest().getLocalAddr() != null && context.getRequest().getLocalAddr().equals("10.1.15.9") ){
			  if(moduleFolderName.equals("reports") || moduleFolderName.equals("template_report")){
				  moduleFolderName = moduleFolderName + "_demo";
			  }
		  }
	    return (context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + moduleFolderName + File.separator);
	  }
	
	public void onOpenDocument(PdfWriter writer, Document document)
    {
        try
        {
        	//AuditReport.class.getResource( "regionecampania.jpg" ).getPath()
        	// initialization of the header table
        	String path = (AuditReport.class.getResource("regionecampania.jpg").getPath());
        	String path_logo = getWebInfPath(context,"template_report")+File.separator+ "images"+File.separator+getPathLogoAsl();
            headerImage = Image.getInstance( path.replaceAll("%20"," "));
            headerImage_asl = Image.getInstance( path_logo.replaceAll("%20"," "));
            
            headerImage.scaleToFit( 50, 50 );
            headerImage_asl.scaleToFit(80, 80);
            table = new PdfPTable( 3 );
            table.getDefaultCell().setBorderWidth(0);
            
    		table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
            table.addCell(new Phrase(new Chunk(headerImage, 0, 0)));

    		table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
    		table.getDefaultCell().setVerticalAlignment( Element.ALIGN_MIDDLE );
            Phrase p = new Phrase();
            Chunk ck = new Chunk("Scheda " + tipo_audit + ( (audit.getLivelloRischioFinale() == -1) ? (" Provvisoria") : ("") ), new Font(Font.TIMES_ROMAN, 11, Font.BOLD, Color.black));
            p.add(ck);
            table.addCell(p);
            
    		
            PdfPTable table1 = new PdfPTable( 2 );
            table1.getDefaultCell().setBorderWidth(0);
            table1.getDefaultCell().setHorizontalAlignment( Element.ALIGN_TOP);
    		table1.getDefaultCell().setVerticalAlignment( Element.ALIGN_RIGHT );
    		Phrase tab1titVal = new Phrase( "		" , new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
    		table1.addCell(tab1titVal);
    		
    		
    		table1.getDefaultCell().setHorizontalAlignment( Element.ALIGN_TOP);
    		table1.getDefaultCell().setVerticalAlignment( Element.ALIGN_BOTTOM);
    		table1.addCell(new Phrase(new Chunk(headerImage_asl, 0, 0)));
    		table1.getDefaultCell().setHorizontalAlignment( Element.ALIGN_TOP);
    		table1.getDefaultCell().setVerticalAlignment( Element.ALIGN_LEFT);
    		table.addCell(table1);
    	
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
           // table.addCell(new Phrase(new Chunk(headerImage, 0, 0)));
            // initialization of the Graphic State
            gstate = new PdfGState();
            gstate.setFillOpacity(0.3f);
            gstate.setStrokeOpacity(0.3f);
            // initialization of the template
            tpl = writer.getDirectContent().createTemplate(100, 100);
            tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
            // initialization of the font
            helv = BaseFont.createFont( BaseFont.TIMES_ROMAN, BaseFont.WINANSI, false);
            
          
            
        }
        catch(Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        // write the headertable
        table.setTotalWidth( document.right() - document.left() );
        table.writeSelectedRows( 0, -1, document.left(), document.getPageSize().getHeight() - 5, cb);
        // compose the footer
        String text = "Pagina " + writer.getPageNumber() ;
        float textSize = helv.getWidthPoint(text, 8);
        float textBase = document.bottom() - 15;
        cb.beginText();
        cb.setFontAndSize(helv, 8);
      
            float adjust = helv.getWidthPoint("0", 12);
            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.right() - adjust, textBase);
        //}
        cb.saveState();
      
    }
	
  
}
