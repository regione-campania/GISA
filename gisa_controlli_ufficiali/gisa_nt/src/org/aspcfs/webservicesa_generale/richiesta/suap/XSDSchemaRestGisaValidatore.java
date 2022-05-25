/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XSDSchemaRestGisaValidatore {

	
	
	public static boolean validateAgainstXSD(InputStream xml,URL urlSchemaXsd/*, InputStream xsd*/,File xmlFileDup,ArrayList<String[]> campiInvalidi) throws Exception
	{
//		final List<SAXParseException> exceptions = new LinkedList<SAXParseException>();
		final HashMap<String, SAXParseException> exceptions = new HashMap<String, SAXParseException>();
		InputStreamReader read = new InputStreamReader(new FileInputStream(xmlFileDup));
		BufferedReader buffRead = new BufferedReader(read);
		
		String temp0 = "";
		ArrayList<String> tempLines = new ArrayList<String>();
		while((temp0 = buffRead.readLine() )!=null)
		{
			tempLines.add(temp0);
		}
		
		
		Pattern p =Pattern.compile(">.*</");
		Pattern p2 = Pattern.compile("</.*>");
		Matcher m = null;
		Matcher m2 = null;
		
	    try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	      //Schema schema = factory.newSchema(new StreamSource(xsd));
	        Schema schema = factory.newSchema(urlSchemaXsd);
	        Validator validator = schema.newValidator();
	        
	       
	        validator.setErrorHandler(new ErrorHandler()
	        {
	          @Override
	          public void warning(SAXParseException exception) throws SAXException
	          {
	            exceptions.put(exception.getLineNumber()+""+exception.getColumnNumber(),exception);
	          }

	          @Override
	          public void fatalError(SAXParseException exception) throws SAXException
	          {
	        	  exceptions.put(exception.getLineNumber()+""+exception.getColumnNumber(),exception);
	          }

	          @Override
	          public void error(SAXParseException exception) throws SAXException
	          {
	        	  exceptions.put(exception.getLineNumber()+""+exception.getColumnNumber(),exception);
	          }
	        });
	        
	        validator.validate(new StreamSource(xml));
	        if(exceptions.isEmpty())
	        	return true;
	        else
	        	throw new org.xml.sax.SAXParseException("",null);
	    }
	    catch(org.xml.sax.SAXParseException ex)
	    {
	    	
	    	
		    	System.out.println("XSD VALIDATOR>> VALORI INVALIDI:");
		    	campiInvalidi.clear();
		    	for(Map.Entry<String, SAXParseException> ecc : exceptions.entrySet())
		    	{
		    		 
		    		//estraggo il token che da problemi usando linea e colonna
//		    		int k = 1;
//		    		while(k++ < ecc.getValue().getLineNumber())
//		    		{
//		    			buffRead.readLine();
//		    		}
		    		
//		    		String temp = buffRead.readLine();
		    		String temp = tempLines.get(ecc.getValue().getLineNumber()-1);
//		    		if(temp == null)
//		    			continue;
//		    		System.out.println("eccezione: riga "+ ecc.getValue().getLineNumber() +" ,col "+ecc.getValue().getColumnNumber()+"----> "+temp.trim());
		    		System.out.println("messaggio originale eccezione: "+ecc);
		    		
		    		//estraggo nome tag e valore
		    		m = p.matcher(temp);
		    		m2 = p2.matcher(temp);
		    		if(!m.find() || !m2.find())
		    		{
		    			continue;
		    		}
		    		String valoreTag = m.group();
		    		valoreTag = valoreTag.substring(1,valoreTag.length()-2);//.substring(1,);
		    		String nomeTag = m2.group();
		    		nomeTag = nomeTag.substring(2,nomeTag.length()-1);//.substring(m.start()+2,m.end());
		    		//System.out.println(nomeTag +" "+valoreTag);
		    		String r = ecc.getValue().getLineNumber()+"";
		    		String c = ecc.getValue().getColumnNumber()+"";
		    	
		    		String[] toAdd = {r,c,nomeTag,valoreTag};
		    		campiInvalidi.add(toAdd);
		    	}
		    	
	    	
	    }
	    catch(Exception ex)
	    {
	        ex.printStackTrace();
	    }
	    finally
	    {
	    	buffRead.close();
	    }
	    return false;
	}
	
}
