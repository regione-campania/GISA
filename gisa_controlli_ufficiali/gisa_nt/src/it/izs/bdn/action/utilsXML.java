/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class utilsXML {
	
	/** 
	 * Il metodo restituisce la stringa contenente il valore associato al nodo specificato del documento xml 
	 * 
	 * @param documento_xml - Stringa contenente il documento xml completo
	 * @param nodo - Stringa che indica il nome del nodo di cui si vuole prelevare il valore
	 * @return Stringa contenente il valore associato al nodo specificato
	 */
	public static String getValoreNodoXML(String documento_xml, String nodo) {
		String ret = null;
		DOMParser parser = new DOMParser();
	    try {
	    	ByteArrayInputStream byteStream = new ByteArrayInputStream(documento_xml.getBytes("UTF-8"));
//	    	Reader reader = new InputStreamReader(documento_xml.getBytes(),"UTF-8");
	    	InputSource is = new InputSource(byteStream);
	    	is.setEncoding("UTF-8");
	    	parser.parse(is);
	    	
	    	Document doc = parser.getDocument();
	    	Element E = doc.getDocumentElement();
	    	NodeList d = E.getElementsByTagName(nodo);
	    	
	    	if (d!=null && d.item(0) != null)
	    		ret = d.item(0).getTextContent();
	    	
	    } catch (SAXException s) {
	    	ret = null;
	        s.printStackTrace();
	    } catch (IOException i) {
	    	ret = null;
	        i.printStackTrace();
	    }
    	
	    return ret;
	}
	
	public static String getValoreNodoXML_new(String documento_xml, String nodo) {
		String ret = null;
		
		String nodoPartenza = "<" + nodo + ">";
		String nodoFinale = "</" + nodo + ">";
		
		int indicePartenza = -1;
		int indiceFinale = -1;
		indicePartenza = documento_xml.indexOf(nodoPartenza);
		indiceFinale = documento_xml.indexOf(nodoFinale);
		
		if(indicePartenza>0)
		{
			indicePartenza = indicePartenza + nodoPartenza.length();
			ret = documento_xml.substring(indicePartenza, indiceFinale);
		}
		
	    return ret;
	}
	
	
	public static int getNumeroNodiXML(String documento_xml, String nodo) throws SAXException, IOException {
		String ret = null;
		DOMParser parser = new DOMParser();
	    try {
	    	ByteArrayInputStream byteStream = new ByteArrayInputStream(documento_xml.getBytes("UTF-8"));
//	    	Reader reader = new InputStreamReader(documento_xml.getBytes(),"UTF-8");
	    	InputSource is = new InputSource(byteStream);
	    	is.setEncoding("UTF-8");
	    	parser.parse(is);
	    		
	    	Document doc = parser.getDocument();
	    	Element E = doc.getDocumentElement();
	    	NodeList d = E.getElementsByTagName(nodo);
	    
	    	 return d.getLength();
	    	
	    	
	    } catch (SAXException s) {
	    	
	        throw(s);
	    } catch (IOException f) {
	    	
	    	throw(f);
	    }
	}
	
	public static String getValueNodoXML(String documento_xml, String nomeTag,int i) {
		String ret = null;
		DOMParser parser = new DOMParser();
	    try {
	    
	    	ByteArrayInputStream byteStream = new ByteArrayInputStream(documento_xml.getBytes("UTF-8"));
//	    	Reader reader = new InputStreamReader(documento_xml.getBytes(),"UTF-8");
	    	InputSource is = new InputSource(byteStream);
	    	is.setEncoding("UTF-8");
	    	parser.parse(is);
	    		
	    	Document doc = parser.getDocument();
	    	
	    	
	    	Element E = doc.getDocumentElement();
	    	Node myNode = E.getElementsByTagName("ALLEVAMENTI").item(i);
	    	//NodeList d = E.getElementsByTagName(nodo);
	    	NodeList nodeList = myNode.getChildNodes();
	    	
	    	for (int j =0 ; j<nodeList.getLength() ; j++)
	    	{
	    		if(nodeList.item(j).getNodeName().equals(nomeTag))
	    			ret = nodeList.item(j).getTextContent();
	    		
	    	} 
	    	
	    	/*if (d!=null && d.item(i) != null)
	    		ret = d.item(i).getTextContent();*/
	    	
	    } catch (IOException f) {
	    	ret = null;
	        f.printStackTrace();
	    } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	    return ret;
	}
}
