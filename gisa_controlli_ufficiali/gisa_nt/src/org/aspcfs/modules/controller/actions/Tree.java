/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.controller.actions;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.controller.base.TreeDAOImpl;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.utils.web.LookupList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.darkhorseventures.framework.actions.ActionContext;

public class Tree extends CFSModule{

	Logger logger=Logger.getLogger(Tree.class);
	public String executeCommandAddTree(ActionContext context)
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);
			org.aspcfs.modules.controller.base.TreeDAO treeDAO = new TreeDAOImpl();
			context.getRequest().setAttribute("ListaCombo", treeDAO.readLookup(db));
			if(context.getRequest().getAttribute("Errore")!=null)
				context.getRequest().setAttribute("Errore",context.getRequest().getAttribute("Errore"));
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "AddTreeOK" ;
	}
	
	
	
	public String executeCommandReloadStruttura(ActionContext context)
	{
		
		
		HashMap<Integer,ArrayList<OiaNodo>> strutture_asl =  (HashMap<Integer, ArrayList<OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
		
		Connection db = null ;
		try
		{
		db = this.getConnection(context);
		OiaNodo n = new OiaNodo();
		

		Calendar calCorrente = GregorianCalendar.getInstance();
		Date dataCorrente = new Date(System.currentTimeMillis());
		int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
		dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
		calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
		int anno_corrente = calCorrente.get(Calendar.YEAR);
		 logger.info("Caricamento Strutture Per Conto Di In memoria Anno "+anno_corrente); 
		
		strutture_asl.put(Integer.parseInt(context.getParameter("idAsl")), n.loadbyidAsl(context.getParameter("idAsl"),anno_corrente, db));

	
	context.getServletContext().setAttribute("StruttureOIA",strutture_asl);
		}catch(SQLException e)
	{
		
	}
	finally
	{
		freeConnection(context, db);
	}
	 return executeCommandListTree(context);
	}

	public String executeCommandCreateTree(ActionContext context)
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);
			org.aspcfs.modules.controller.base.TreeDAO treeDAO = new TreeDAOImpl();
			treeDAO.createTree(db, context.getParameter("nomeRelazione"), context.getParameter("tabella"),context.getParameter("descrizione"));
			context.getRequest().setAttribute("Tree", "true");

		}
		catch(SQLException e)
		{
			//e.printStackTrace() ;
			context.getRequest().setAttribute("Errore", e);
			return executeCommandAddTree(context) ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "CreateTreeOK" ;
	}

	public String executeCommandRimuoviElemento(ActionContext context) throws ParserConfigurationException, SAXException, IOException
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);
			String nomeTabella = context.getParameter("nomeTabella") ;
		
			File file = new File(context.getServletContext().getRealPath("/WEB-INF/trees.xml"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");
		
			String colonnaPadre="";
			String colonnaDesc = "" ;
			String colonnaLivello = "" ;
			String colonnaId = "" ;
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					if (nomeTabella.equals(fstElmnt.getAttribute("name")))
					{


						colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
						colonnaPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
						colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
						colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
//						colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
//						colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();
						context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));

						

					}
				}

			}


			int idNodo = Integer.parseInt(context.getParameter("idNodo"));


			String insert = "update "+nomeTabella + " set enabled = false where "+colonnaId + " = ? ";
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setInt(1, idNodo);
			pst.execute();
			context.getRequest().setAttribute("Tree", "true");

			context.getRequest().setAttribute("campoId", colonnaId);
			context.getRequest().setAttribute("campoPadre", colonnaPadre);
			context.getRequest().setAttribute("campoDesc", colonnaDesc);
			context.getRequest().setAttribute("campoLivello", colonnaLivello);
			context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));
			context.getRequest().setAttribute("tabella", context.getParameter("nomeTabella"));

			return executeCommandReloadAlbero(context);

		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "AggiungiLivelloOK" ;
	}

	public String executeCommandReloadAlbero(ActionContext context)
	{
		Connection db = null ;
		try
		{
			String tabella = context.getParameter("tabella");
			if (tabella == null)
				tabella = (String) context.getRequest().getAttribute("tabella");
			db = getConnection(context);
			System.out.println("InitTreeServlet --> start");
			File file = new File(context.getServletContext().getRealPath("/WEB-INF/trees.xml"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;

					String nomeTabella = fstElmnt.getAttribute("name");
					if(tabella.equals(nomeTabella))
					{
						String colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
						String colonnaIdPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
						String colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
						String colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
						String colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
						String colonnaSele = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();

						TreeDAOImpl treeDAO = new TreeDAOImpl();
						org.aspcfs.modules.controller.base.Tree tree = treeDAO.dettaglioTree(nomeTabella, colonnaId, colonnaIdPadre, colonnaDesc, colonnaLivello, null, colonnaEnabled, colonnaSele,db);

						System.out.println("caricata struttura ad albero -->"+nomeTabella);
						context.getServletContext().setAttribute(nomeTabella,tree);
						context.getRequest().setAttribute("campoId", colonnaId);
						context.getRequest().setAttribute("campoPadre", colonnaIdPadre);
						context.getRequest().setAttribute("campoDesc", colonnaDesc);
						context.getRequest().setAttribute("campoLivello", colonnaLivello);
						context.getRequest().setAttribute("Tree","true");
						context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));
					}
				}

			}
			System.out.println("InitTreeServlet --> end");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		return "AggiungiLivelloOK";
	}

	public String executeCommandAggiungiLivello(ActionContext context) throws ParserConfigurationException, SAXException, IOException
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);
			String nomeTabella = context.getParameter("nomeTabella") ;
			
			File file = new File(context.getServletContext().getRealPath("/WEB-INF/trees.xml"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");
		
			String colonnaPadre="";
			String colonnaDesc = "" ;
			String colonnaLivello = "" ;
			String colonnaId = "" ;
			String codiceesame = "" ;
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					if (nomeTabella.equals(fstElmnt.getAttribute("name")))
					{

						codiceesame= fstElmnt.getElementsByTagName("codiceesame").item(0).getTextContent();
						colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
						colonnaPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
						colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
						colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
//						colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
//						colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();
						context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));

						

					}
				}

			}

			String codEsame = context.getParameter("codEsame");


			String insercodiceesame = (codiceesame !=null && !"".equals(codiceesame)) && codEsame != null ? ","+ codiceesame : "" ;
			String paramcodiceesame = (codiceesame !=null && !"".equals(codiceesame)) && codEsame != null ? ",?" : "" ;
			
			int idPadre = Integer.parseInt(context.getParameter("idPadre"));
			int idLivello = Integer.parseInt(context.getParameter("livello"));
			String descrizione = context.getParameter("newItem");

			String insert = "insert into "+nomeTabella + "( "+colonnaPadre + ","+ colonnaDesc + ","+colonnaLivello + insercodiceesame+ ") values (?,?,?" +paramcodiceesame+")";
			PreparedStatement pst = db.prepareStatement(insert);
			pst.setInt(1, idPadre);
			pst.setString(2, descrizione.toUpperCase());
			pst.setInt(3, idLivello);
			if(codiceesame!= null && ! "".equals(codiceesame) && codEsame != null)
				pst.setString(4, codEsame);

				
				pst.execute();
			context.getRequest().setAttribute("Tree", "true");

			context.getRequest().setAttribute("campoId", colonnaId);
			context.getRequest().setAttribute("campoPadre", colonnaPadre);
			context.getRequest().setAttribute("campoDesc", colonnaDesc);
			context.getRequest().setAttribute("campoLivello", colonnaLivello);
			context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));
			context.getRequest().setAttribute("tabella", context.getParameter("nomeTabella"));
			return executeCommandReloadAlbero(context);


		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "AggiungiLivelloOK" ;
	}



	public String executeCommandConfigPianiTree(ActionContext context) throws IOException, ParserConfigurationException, SAXException
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);

			String tabIn = context.getParameter("nomeTabella");
			if(tabIn==null)
			{
				tabIn = (String)context.getRequest().getAttribute("nomeTabella");
			}
			File file = new File(context.getServletContext().getRealPath("/WEB-INF/trees.xml"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");
			String idPiano = context.getParameter("idPiano");
				
				if(idPiano==null)
				{
					idPiano = (String)context.getRequest().getAttribute("idPiano");
				}
			context.getRequest().setAttribute("idPiano", Integer.parseInt(idPiano));
			String colonnaId = "" ; 
			String colonnaIdPadre = "" ; 
			String colonnaDesc = "" ; 
			String colonnaEnabled = "" ; 
			String colonnaLivello = "" ;
			String colonnaSelezione = "" ;
			String tabellaMappingPiani = "" ;
			String colonnatabellaMappingPiani = "" ;
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					if (tabIn.equals(fstElmnt.getAttribute("name")))
					{

						tabellaMappingPiani = fstElmnt.getElementsByTagName("tablemappingpiani").item(0).getTextContent();
						colonnatabellaMappingPiani = fstElmnt.getElementsByTagName("colonnatablemappingpiani").item(0).getTextContent();

						colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
						colonnaIdPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
						colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
						colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
						colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
						colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();
						context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));

						context.getRequest().setAttribute("tabellaMappingPiani", tabellaMappingPiani);
						context.getRequest().setAttribute("colonnatabellaMappingPiani", colonnatabellaMappingPiani);

					}
				}

			}

			String campoEnabled = "" ;

			if(context.getParameter("campoEnabled")!=null)
			{
				context.getRequest().setAttribute("campoEnabled", context.getParameter("campoEnabled"));	
				campoEnabled =  context.getParameter("campoEnabled") ;
			}
			
			
			String nodo  = context.getRequest().getParameter("nodo");
			String  campoid1= context.getRequest().getParameter("campoId1");
			String campoid2 = context.getRequest().getParameter("campoId2");
			String multiple = context.getRequest().getParameter("multiplo");
			String divPath = context.getRequest().getParameter("divPath");
			String idRiga = context.getRequest().getParameter("idRiga");

			context.getRequest().setAttribute("idCampoIntero", campoid1);
			context.getRequest().setAttribute("idCampoTesto", campoid2)	;
			context.getRequest().setAttribute("multiplo", multiple)	;
			context.getRequest().setAttribute("divPath", divPath)	;
			context.getRequest().setAttribute("idRiga", idRiga)	;
			
			if( (idPiano!=null && ! "".equals(Integer.parseInt(idPiano))))
			{
				if (Integer.parseInt(idPiano)>0)
				{

					TreeDAOImpl treeDAO = new TreeDAOImpl();
					org.aspcfs.modules.controller.base.Tree tree = treeDAO.dettaglioTreePiani(Integer.parseInt(idPiano),tabellaMappingPiani,colonnatabellaMappingPiani,tabIn, colonnaId, colonnaIdPadre, colonnaDesc, colonnaLivello, null, colonnaEnabled,colonnaSelezione, db);
					LookupList listaPiani = new LookupList(db,"lookup_piano_monitoraggio");
					tree.setIdPiano(Integer.parseInt(context.getParameter("idPiano")));
					tree.setDescrizionePiano(listaPiani.getSelectedValue(Integer.parseInt(context.getParameter("idPiano"))));
					context.getRequest().setAttribute("Sel", context.getParameter("sel"));

					/*salvo l'albero nella request solo se ha nodi : se sono stati configurati i nodi per il piano preso in input*/
					if(tree.getListaNodi().size()>0)
						context.getRequest().setAttribute("TreePiani", tree);
				}
			}
			LookupList listaPiani1 = new LookupList(db,"lookup_piano_monitoraggio");
			context.getRequest().setAttribute("ListaPianiLookup", listaPiani1);
			//context.getRequest().setAttribute("ListaNodi", treeDAO.listaNodi(context.getParameter("nomeTabella"),campoEnabled,db));
			context.getRequest().setAttribute("Sel", context.getParameter("sel"));
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "ConfigPianiTreeOK" ;
	}

	public String executeCommandDettaglioTree(ActionContext context) throws IOException, ParserConfigurationException, SAXException
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);

			String tabIn = context.getParameter("nomeTabella");
			File file = new File(context.getServletContext().getRealPath("/WEB-INF/trees.xml"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");
			String colonnaId = "" ; 
			String colonnaIdPadre = "" ; 
			String colonnaDesc = "" ; 
			String colonnaEnabled = "" ; 
			String colonnaLivello = "" ;
			String colonnaSelezione = "" ;
			String tabellaMappingPiani = "" ;
			String colonnatabellaMappingPiani = "" ;

			String idNodo = context.getParameter("idNodo");
			
			context.getRequest().setAttribute("nomeTabella", tabIn);
			
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					if (tabIn.equals(fstElmnt.getAttribute("name")))
					{
						tabellaMappingPiani = fstElmnt.getElementsByTagName("tablemappingpiani").item(0).getTextContent();
						colonnatabellaMappingPiani = fstElmnt.getElementsByTagName("colonnatablemappingpiani").item(0).getTextContent();

						colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
						colonnaIdPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
						colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
						colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
						colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
						colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();
						context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));
						context.getRequest().setAttribute("tabellaMappingPiani", tabellaMappingPiani);
						context.getRequest().setAttribute("colonnatabellaMappingPiani", colonnatabellaMappingPiani);

					}
				}

			}

			String campoEnabled = "" ;

			if(context.getParameter("campoEnabled")!=null)
			{
				context.getRequest().setAttribute("campoEnabled", context.getParameter("campoEnabled"));	
				campoEnabled =  context.getParameter("campoEnabled") ;
			}

			String  campoid1= context.getRequest().getParameter("campoId1");
			String campoid2 = context.getRequest().getParameter("campoId2");
			String multiple = context.getRequest().getParameter("multiplo");
			String divPath = context.getRequest().getParameter("divPath");
			String idRiga = context.getRequest().getParameter("idRiga");

			context.getRequest().setAttribute("idCampoIntero", campoid1);
			context.getRequest().setAttribute("idCampoTesto", campoid2)	;
			context.getRequest().setAttribute("multiplo", multiple)	;
			context.getRequest().setAttribute("divPath", divPath)	;
			context.getRequest().setAttribute("idRiga", idRiga)	;

			
			/*COSTRUZIONE ALBERO PER PIANO*/
			if(context.getParameter("idPiano")!=null && ! "".equals(context.getParameter("idPiano")))
			{
				if (Integer.parseInt(context.getParameter("idPiano"))>0)
				{

					int idPiano = Integer.parseInt(context.getParameter("idPiano"));
					TreeDAOImpl treeDAO = new TreeDAOImpl();
					org.aspcfs.modules.controller.base.Tree tree = treeDAO.dettaglioTreePiani(idPiano,tabellaMappingPiani,colonnatabellaMappingPiani,tabIn, colonnaId, colonnaIdPadre, colonnaDesc, colonnaLivello, idNodo, colonnaEnabled,colonnaSelezione, db);
					LookupList listaPiani = new LookupList(db,"lookup_piano_monitoraggio");

					tree.setIdPiano(Integer.parseInt(context.getParameter("idPiano")));
					tree.setDescrizionePiano(listaPiani.getSelectedValue(Integer.parseInt(context.getParameter("idPiano"))));

					context.getRequest().setAttribute("Sel", context.getParameter("sel"));

					/*salvo l'albero nella request solo se ha nodi : se sono stati configurati i nodi per il piano preso in input*/
					if(tree.getListaNodi().size()>0)
						context.getRequest().setAttribute("TreePiani", tree);
				}
			}


		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "DettaglioTreeOK" ;
	}
	
	public String executeCommandDettaglioTreeCU(ActionContext context) throws IOException, ParserConfigurationException, SAXException
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);

			String tabIn = context.getParameter("nomeTabella");
			File file = new File(context.getServletContext().getRealPath("/WEB-INF/trees.xml"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuil = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = dbuil.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");
			String colonnaId = "" ; 
			String colonnaIdPadre = "" ; 
			String colonnaDesc = "" ; 
			String colonnaEnabled = "" ; 
			String colonnaLivello = "" ;
			String colonnaSelezione = "" ;
			

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					if (tabIn.equals(fstElmnt.getAttribute("name")))
					{
						colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
						colonnaIdPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
						colonnaDesc = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
						colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
						colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
						colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();
						context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));

					}
				}

			}

			String campoEnabled = "" ;

			if(context.getParameter("campoEnabled")!=null)
			{
				context.getRequest().setAttribute("campoEnabled", context.getParameter("campoEnabled"));	
				campoEnabled =  context.getParameter("campoEnabled") ;
			}

			String  campoid1= context.getRequest().getParameter("campoId1");
			String campoid2 = context.getRequest().getParameter("campoId2");
			String multiple = context.getRequest().getParameter("multiplo");
			String divPath = context.getRequest().getParameter("divPath");
			String idRiga = context.getRequest().getParameter("idRiga");

			context.getRequest().setAttribute("idCampoIntero", campoid1);
			context.getRequest().setAttribute("idCampoTesto", campoid2)	;
			context.getRequest().setAttribute("multiplo", multiple)	;
			context.getRequest().setAttribute("divPath", divPath)	;
			context.getRequest().setAttribute("idRiga", idRiga)	;

		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "DettaglioTreeConfOK" ;
	}

	public String executeCommandListTree(ActionContext context)
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteList", siteList);
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "ListTreeOK" ;
	}

	public String executeCommandSalvaLivello(ActionContext context)
	{
		Connection db = null ;
		try
		{
			db = getConnection(context);
			org.aspcfs.modules.controller.base.TreeDAO treeDAO = new TreeDAOImpl();
			String[] valori = context.getRequest().getParameterValues("valori") ;
			String nomeAlbero = context.getRequest().getParameter("nomeTabella");
			String nomeCombo =  context.getRequest().getParameter("tabella");
			String idPadre =  context.getRequest().getParameter("idPadre");
			String livello =  context.getRequest().getParameter("livello");
			treeDAO.salvaLivello(db, nomeAlbero, nomeCombo, Integer.parseInt(idPadre), Integer.parseInt(livello), valori);


		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		}
		finally
		{
			freeConnection(context, db);
		}

		return "-none-" ;
	}




}
