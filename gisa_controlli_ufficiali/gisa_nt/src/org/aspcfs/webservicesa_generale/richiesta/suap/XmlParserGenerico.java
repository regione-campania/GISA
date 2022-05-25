/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlParserGenerico {

	
	DocumentBuilderFactory dbf = null;
	DocumentBuilder dbuild = null; 
	File xmlFile;
	
	
	public XmlParserGenerico( File xmlFile) throws ParserConfigurationException
	{
	 
		dbf = DocumentBuilderFactory.newInstance();
		dbuild =  dbf.newDocumentBuilder();
		this.xmlFile = xmlFile;
		
		
	}
	
	
	//RITORNA NULL SE NON TROVA NULLA
	public String getFirstEntryTagContentFor(String tagName)
	{
		FileInputStream fis = null;
		String res = null;
		try
		{
			fis = new FileInputStream( xmlFile);
			Document doc = dbuild.parse(fis);
			return deepSearchForTagContent(doc,tagName).getTextContent();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{fis.close();} catch(Exception ex){}
		}
		return res;
	}
	
	
	//RITORNA NULL SE NON TROVA NULLA
	private Node deepSearchForTagContent(Node node,String tagName)
	{
		if(node.getNodeName().equals(tagName))
		{
			return node;//.getTextContent();
		}
		
		NodeList childs = node.getChildNodes();
		
		for(int i = 0;i < childs.getLength();i++)
		{
			Node t = deepSearchForTagContent(childs.item(i),tagName);
			if(t != null)
			{
				return t;
			}
		}
		return null;
		
	}
	
	
	
	//RITORNA NULL SE NON TROVA NULLA
	public String getFirstEntryAttributeContentFor(String tagName,String tagAttrName)
	{
		FileInputStream fis = null;
		String res = null;
		try
		{
			fis = new FileInputStream( xmlFile);
			Document doc = dbuild.parse(fis);
			return deepSearchForAttributeContent(doc,tagName,tagAttrName);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{fis.close();} catch(Exception ex){}
		}
		return res;
	}
	
	
	//RITORNA NULL SE NON TROVA ELEMENTO
	private String deepSearchForAttributeContent(Node node,String tagName,String tagAttrName)
	{
		NamedNodeMap attrs = node.getAttributes();
		if(node.getNodeName().equals(tagName) && attrs != null && attrs.getNamedItem(tagAttrName) != null)
		{
			return attrs.getNamedItem(tagAttrName).getTextContent();
		}
		 
		
		NodeList childs = node.getChildNodes();
		
		for(int i = 0;i < childs.getLength();i++)
		{
			String t = deepSearchForAttributeContent(childs.item(i),tagName,tagAttrName);
			if(t != null)
			{
				return t;
			}
		}
		return null;
		
	}
	
	
	//RITORNA LISTA VUOTA SE NON TROVA NULLA
	public ArrayList<String> getAllTagContentsFor(String tagName)
	{
		ArrayList<String> toRet = new ArrayList<String>();
		 
		
		FileInputStream fis = null;
		String res = null;
		try
		{
			fis = new FileInputStream( xmlFile);
			Document doc = dbuild.parse(fis);
			return deepSearchForAllTagContents(doc,tagName);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{fis.close();} catch(Exception ex){}
		}
		return toRet;
	}
	
	
	//RITORNA LISTA VUOTA SE NON TROVA NULLA
	private ArrayList<String> deepSearchForAllTagContents(Node node,String tagName)
	{
		ArrayList<String> toRet = new ArrayList<String>();
		
		NodeList childs = node.getChildNodes();
		for(int i = 0;i<childs.getLength(); i++)
		{
			ArrayList<String> t = deepSearchForAllTagContents(childs.item(i), tagName);
			if(t.size() > 0)
			{
				for(String el : t)
				{
					toRet.add(el);
				}
			}
		}
		
		if(node.getNodeName().equals(tagName))
		{
			 
			toRet.add(node.getTextContent());
		}
		
		
		return toRet;
	}
	
	
	
	//RITORNA LISTA VUOTA SE NON TROVA NULLA
	public ArrayList<String> getAllAttributeContentsFor(String tagName,String attrName)
	{
		ArrayList<String> toRet =new ArrayList<String>();
		
		FileInputStream fis = null;
		String res = null;
		try
		{
			fis = new FileInputStream( xmlFile);
			Document doc = dbuild.parse(fis);
			return deepSearchForAllAttrContents(doc,tagName,attrName);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{fis.close();} catch(Exception ex){}
		}
		return toRet;
	}
	
	
	//RITORNA LISTA VUOTA SE NON TROVA NULLA
	private ArrayList<String> deepSearchForAllAttrContents(Node node,String tagName,String attrName)
	{
		ArrayList<String> toRet = new ArrayList<String>();
		
		NodeList childs = node.getChildNodes();
		for(int i = 0;i<childs.getLength(); i++)
		{
			ArrayList<String> t = deepSearchForAllAttrContents(childs.item(i), tagName, attrName);
			if(t.size() > 0)
			{
				for(String el : t)
				{
					toRet.add(el);
				}
			}
		}
		
		if(node.getNodeName().equals(tagName) )
		{
			NamedNodeMap attrs = node.getAttributes();
			if(attrs != null && attrs.getNamedItem(attrName) != null)
			{
				toRet.add(attrs.getNamedItem(attrName).getTextContent());
			}
			
		}
		
		
		return toRet;
	}
	
	
	//TO-DO : OTTENERE TUTTI VALORI DEI FIGLI (CHIAVE COME NOME TAG, VALORE) DATO UN TAG
	//NULL SE SONO VUOTI O HANNO ALTRI FIGLI
	public HashMap<String,String> getAllChildsValuesFor(String tagName, String[] daScartare)
	{
		FileInputStream fis = null;
		HashMap<String,String> toRet = new HashMap<String,String>();
		try
		{
			
			HashMap<String,Boolean> filtri = new HashMap<String,Boolean>();
			if(daScartare != null)
			{
				
				for(String nomeTag : daScartare)
				{
					filtri.put(nomeTag, true);
				}
			}
			
			fis = new FileInputStream(this.xmlFile);
			Document doc = this.dbuild.parse(new FileInputStream(this. xmlFile));
			 
			Node t = deepSearchForTagContent(doc,tagName);
			
			if(t == null)
			{
				return toRet;
			}
			
			NodeList childs = t.getChildNodes();
			for(int i=0;i<childs.getLength();i++)
			{
				
				Node t2 = childs.item(i);
				if(filtri.containsKey(t2.getNodeName()))
					continue;
				
				String contenuto = t2.getTextContent();
				if(contenuto.trim().length() == 0 || t2.getChildNodes() == null )
				{
					contenuto = null;
				}
				 
				toRet.put(t2.getNodeName(), contenuto);
			}
			
			
			return toRet;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		finally
		{
			try{fis.close();} catch(Exception ex){}
		}
		
	}
	
	
	
	
}
