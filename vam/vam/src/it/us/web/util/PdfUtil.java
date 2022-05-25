/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil
{

	public static byte[] join(byte[] primaParte, byte[] secondaParte)
	{
		byte[] ret = null;
		
		if( (primaParte == null || primaParte.length <= 0) ) //manca la prima parte
		{
			ret = secondaParte.clone();
		}
		else if( (secondaParte == null || secondaParte.length <= 0) ) //manca la seconda parte
		{
			ret = primaParte.clone();
		}
		else //ci sono entrambe le parti
		{
			try
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				PdfReader reader1 = new PdfReader( primaParte );
				PdfReader reader2 = new PdfReader( secondaParte );
				
				Document	document	= new Document( reader1.getPageSizeWithRotation( 1 ) );
				PdfWriter	writer		= PdfWriter.getInstance( document, baos );
				
				document.open();
				
				PdfContentByte	cb				= writer.getDirectContent();
				PdfImportedPage	page			= null;;
				int				rotation		= -1;
				int				paginePrimo		= reader1.getNumberOfPages();
				int				pagineSecondo	= reader2.getNumberOfPages();
				
				for( int i = 1; i <= paginePrimo; i++ )
				{
					document.setPageSize( reader1.getPageSizeWithRotation(i) );
					document.newPage();
					page		= writer.getImportedPage( reader1, i );
					rotation	= reader1.getPageRotation( i );
					if (rotation == 90 || rotation == 270)
					{
						cb.addTemplate(page, 0, -1f, 1f, 0, 0, reader1.getPageSizeWithRotation(i).getHeight() );
					}
					else
					{
						cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
					}
				}
				
				for( int i = 1; i <= pagineSecondo; i++ )
				{
					document.setPageSize( reader2.getPageSizeWithRotation(i) );
					document.newPage();
					page		= writer.getImportedPage( reader2, i );
					rotation	= reader2.getPageRotation( i );
					if (rotation == 90 || rotation == 270)
					{
						cb.addTemplate(page, 0, -1f, 1f, 0, 0, reader2.getPageSizeWithRotation(i).getHeight() );
					}
					else
					{
						cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
					}
				}
				
				document.close();
				
				ret = baos.toByteArray();
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		
		return ret;
	}

}
