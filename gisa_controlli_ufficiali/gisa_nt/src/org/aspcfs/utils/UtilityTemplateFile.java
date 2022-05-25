/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class UtilityTemplateFile {

	/*public static void buildFile(ActionContext context)
	{
		String					dir		= context.getServletContext().getRealPath("\\") + "WEB-INF\\reports\\template\\" ;
	    System.out.println("dir "+dir);
		try
	    {
		PdfReader				reader	= new PdfReader( dir + "test1.pdf" );
	    ByteArrayOutputStream 	out		= new ByteArrayOutputStream();


	    PdfStamper				stamper	= new PdfStamper( reader, out );
	    AcroFields				form	= stamper.getAcroFields();
	    form.setField( "denominazione_asl", "provaaa" );
	    write(context,out,"prova.pdf",dir);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }


	}*/
	public static void write2( ActionContext context,Ticket t,Connection db)
	{
		try
		{
			PdfReader				reader = null;
			String	dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "reports"+File.separator+"template"+File.separator;

			String	dirImages		= context.getServletContext().getRealPath("/") + "gestione_documenti" + File.separator + "schede"+File.separator+"images"+File.separator;
					
			reader	= new PdfReader( dir + "Template_procedure_richiamo.pdf" );



			PdfStamper				stamper	= new PdfStamper( reader, new FileOutputStream(dir+"Mancato Richiamo "+t.getId()+".pdf") );
			AcroFields				form	= stamper.getAcroFields();
			String nomeLogo = "";
			String descrizioneAsl = "";
			switch (t.getSiteId())
			{
			case 201 :
			{
				nomeLogo		 = 	"avellino.jpg";
				descrizioneAsl 	= 	"Avellino"	;
				break;
			}
			case 202 :
			{
				nomeLogo		 = 	"benevento.jpg";
				descrizioneAsl 	= 	"Benevento"	;
				break;
			}
			case 203 :
			{
				nomeLogo		 = 	"caserta.jpg";
				descrizioneAsl 	= 	"Caserta"	;
				break;
			}
			case 204 :
			{
				nomeLogo		 = 	"napoli 1 centro.jpg";
				descrizioneAsl 	= 	"napoli 1 centro"	;
				break;
			}
			case 205 :
			{
				nomeLogo		 = 	"napoli 2 nord.jpg";
				descrizioneAsl 	= 	"Napoli 2 Nord"	;
				break;
			}
			case 206 :
			{
				nomeLogo		 = 	"napoli 3 sud.jpg";
				descrizioneAsl 	= 	"Napoli 3 Sud"	;
				break;
			}
			case 207 :
			{
				nomeLogo		 = 	"salerno.jpg";
				descrizioneAsl 	= 	"Salerno"	;
				break;
			}
			}

			float[] photograph = form.getFieldPositions("img_asl");

			Rectangle rect = new Rectangle(photograph[1], photograph[2], photograph[3], photograph[4]);
			Image img = Image.getInstance(dirImages+nomeLogo);
			img.scaleToFit(rect.getWidth(), rect.getHeight());
			img.setAbsolutePosition(photograph[1]+ (rect.getWidth() - img.getWidth ()) / 2,photograph[2]+ (rect.getHeight() - img.getHeight()) / 2);
			PdfContentByte cb =stamper.getOverContent(1);
			cb.addImage (img);

			String data =(new SimpleDateFormat("dd/MM/yyyy")).format(new Date(t.getAssignedDate().getTime()));
			HashMap<String, String> valori = getValoriBuildingFile(t,db);		

			form.setField("denominazione_asl",descrizioneAsl);
			form.setField("id_allerta",t.getCodiceAllerta());
			form.setField("codiceallerta",t.getCodiceAllerta());
			form.setField("data",data);


			form.setField("descrizione_breve",valori.get("descrizione_breve"));
			form.setField("nome_impresa",valori.get("nome_impresa"));
			form.setField("indirizzo",valori.get("indirizzo_impresa"));

			form.setField("componente_1",t.getComponenteNucleo());
			form.setField("componente_2",t.getComponenteNucleoDue());
			form.setField("componente_3",t.getComponenteNucleoTre());
			form.setField("componente_4",t.getComponenteNucleoQuattro());
			form.setField("componente_5",t.getComponenteNucleoCinque());
			form.setField("componente_6",t.getComponenteNucleoSei());
			form.setField("componente_7",t.getComponenteNucleoSette());
			form.setField("componente_8",t.getComponenteNucleoOtto());
			form.setField("componente_9",t.getComponenteNucleoNove());
			form.setField("componente_10",t.getComponenteNucleoDieci());

			stamper.setFormFlattening( true );
			stamper.close();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private static HashMap<String, String> getValoriBuildingFile(Ticket t,Connection db)
	{
		HashMap<String, String> mapValori = new HashMap<String, String>();

		try
		{
			String descr_breve_query 	= 	"select descrizionebreveallerta from ticket where id_allerta ='"+t.getCodiceAllerta()+"' ";
			String nome_indirizzo 		= 	"select o.name, oa.addrline1,oa.city,oa.state from organization o , organization_address oa " +
			" where o.org_id = oa.org_id and o.org_id ="+t.getOrgId();
			PreparedStatement pst = db.prepareStatement(descr_breve_query);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				mapValori.put("descrizione_breve", rs.getString(1));
			}
			rs.close();
			pst.close();
			PreparedStatement pst1 = db.prepareStatement(nome_indirizzo);
			ResultSet rs1 = pst1.executeQuery();
			if (rs1.next())
			{
				mapValori.put("nome_impresa", rs1.getString(1));
				mapValori.put("indirizzo_impresa", rs1.getString(2)+ ","+ rs1.getString(3)+" ("+rs1.getString(4)+")");
			}
			rs1.close();
			pst1.close();

		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}

		return mapValori;

	}

}
