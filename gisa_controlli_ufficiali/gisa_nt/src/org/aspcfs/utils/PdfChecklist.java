/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.itextpdf.text.BaseColor;
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
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseField;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PushbuttonField;
import com.lowagie.text.pdf.RadioCheckField;
import com.lowagie.text.pdf.TextField;
 
public class PdfChecklist implements PdfPCellEvent
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
    protected int tf;
    
    /**
     * Creates a cell event that will add a text field to a cell.
     * @param tf a text field index.
     */
    public PdfChecklist(int tf) {
        this.tf = tf;
    }
 
    

    public void generatePdf(	OutputStream out)
throws DocumentException, SQLException, IOException
{
    	Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, 
			out);
		document.open();
		
		PdfPTable table = new PdfPTable(2);
		table.getDefaultCell().setPadding(5f); // Code 1
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		PdfPCell cell;		
		 table.addCell("Name:");
	        cell = new PdfPCell();
	        cell.setCellEvent(new PdfChecklist(1));
	        
	        table.addCell(cell);
	        
	      
				
		document.add(table);
		document.close();
}
    public void manipulatePdf(String src, OutputStream dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader("C:"+File.separator+"Test.pdf");
        PdfStamper stamper = new PdfStamper(reader, dest);
        AcroFields form = stamper.getAcroFields();
        form.setField("text_1", "Bruno Lowagie");
        form.setFieldProperty("text_2", "fflags", 0, null);
        form.setFieldProperty("text_2", "bordercolor", BaseColor.RED, null);
        form.setField("text_2", "bruno");
        form.setFieldProperty("text_3", "clrfflags", TextField.PASSWORD, null);
        form.setFieldProperty("text_3", "setflags", PdfAnnotation.FLAGS_PRINT, null);
        form.setField("text_3", "12345678", "xxxxxxxx");
        form.setFieldProperty("text_4", "textsize", new Float(12), null);
        form.regenerateField("text_4");
        stamper.close();
    }

    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
    	PdfWriter writer = canvases[0].getPdfWriter();
	        TextField text = new TextField(writer, rectangle,
	                String.format("text_%s", tf));
	        text.setBackgroundColor(new GrayColor(0.75f));
	        switch(tf) {
	        case 1:
	            text.setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
	            text.setText("Enter your name here...");
	            text.setFontSize(0);
	            text.setAlignment(Element.ALIGN_CENTER);
	            text.setOptions(TextField.REQUIRED);
	            break;
	        case 2:
	            text.setMaxCharacterLength(8);
	            text.setOptions(TextField.COMB);
	            text.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
	           
	            text.setBorderWidth(2);
	            break;
	        case 3:
	            text.setBorderStyle(PdfBorderDictionary.STYLE_INSET);
	            text.setOptions(TextField.PASSWORD);
	            text.setVisibility(TextField.VISIBLE_BUT_DOES_NOT_PRINT);
	            break;
	        case 4:
	            text.setBorderStyle(PdfBorderDictionary.STYLE_DASHED);
	           
	            text.setBorderWidth(2);
	            text.setFontSize(8);
	            text.setText(
	                "Enter the reason why you want to win a free accreditation for the Foobar Film Festival");
	            text.setOptions(TextField.MULTILINE | TextField.REQUIRED);
	            break;
	        }
	        try {
	            PdfFormField field = text.getTextField();
	            if (tf == 3) {
	                field.setUserName("Choose a password");
	            }
	            writer.addAnnotation(field);
	        }
	        catch(IOException ioe) {
	            throw new ExceptionConverter(ioe);
	        }
	        catch(DocumentException de) {
	            throw new ExceptionConverter(de);
	        }
	    
	}

	
  
}
