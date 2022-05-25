/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.controller.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.jsp.JspWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.oia.base.OiaTipologiaCompetenzaNodo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException; 


import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Nodo extends GenericBean {

	private static final int INT		= Types.INTEGER;
	private static final int STRING		= Types.VARCHAR;
	private static final int DOUBLE		= Types.DOUBLE;
	private static final int FLOAT		= Types.FLOAT;
	private static final int TIMESTAMP	= Types.TIMESTAMP;
	private static final int DATE		= Types.DATE;
	private static final int BOOLEAN	= Types.BOOLEAN;

	private int id 				;
	private int idNodo 			;
	private String descrizione 	;
	private int idPadre 		;
	private int livello			;
	private String tabella 		;
	private ArrayList<Nodo> lista_nodi = new ArrayList<Nodo>();
	public static String colonnaPadre ;
	public static String colonnaSelezione ;

	public static String colonnaId;
	public static String colonnaLivello ;
	public static String colonnaDescrizione ;

	private String path ;
	private String id_paths ;

	private boolean  selMultipla ;

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getId_paths() {
		return id_paths;
	}
	public void setId_paths(String id_paths) {
		this.id_paths = id_paths;
	}
	public ArrayList<Nodo> getLista_nodi() {
		return lista_nodi;
	}
	public void setLista_nodi(ArrayList<Nodo> lista_nodi) {
		this.lista_nodi = lista_nodi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdNodo() {
		return idNodo;
	}
	public void setIdNodo(int idNodo) {
		this.idNodo = idNodo;
	}

	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}
	public int getLivello() {
		return livello;
	}
	public void setLivello(int livello) {
		this.livello = livello;
	}
	public String getTabella() {
		return tabella;
	}
	public void setTabella(String tabella) {
		this.tabella = tabella;
	}


	public boolean isSelMultipla() {
		return selMultipla;
	}
	public void setSelMultipla(boolean selMultipla) {
		this.selMultipla = selMultipla;
	}
	public static Nodo loadResultSet( ResultSet res ) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Nodo ret = new Nodo();
		ret.setId(res.getInt(colonnaId));
		ret.setIdPadre(res.getInt(colonnaPadre));
		ret.setLivello(res.getInt(colonnaLivello));
		ret.setDescrizione(res.getString(colonnaDescrizione)) ;
		ret.setPath(res.getString("path_desc"));
		ret.setId_paths(res.getString("path_id").replaceAll(",", ";"));
		ret.setSelMultipla(res.getBoolean("multipla_sel"));
		return ret;
	}





	protected static int parseType(Class<?> type)
	{
		int ret = -1;

		String name = type.getSimpleName();

		if( name.equalsIgnoreCase( "int" ) || name.equalsIgnoreCase("integer") )
		{
			ret = INT;
		}
		else if( name.equalsIgnoreCase( "string" ) )
		{
			ret = STRING;
		}
		else if( name.equalsIgnoreCase( "double" ) )
		{
			ret = DOUBLE;
		}
		else if( name.equalsIgnoreCase( "float" ) )
		{
			ret = FLOAT;
		}
		else if( name.equalsIgnoreCase( "timestamp" ) )
		{
			ret = TIMESTAMP;
		}
		else if( name.equalsIgnoreCase( "date" ) )
		{
			ret = DATE;
		}
		else if( name.equalsIgnoreCase( "boolean" ) )
		{
			ret = BOOLEAN;
		}

		return ret;
	}


	/**
	 * INPUT :
	 * @param nodo 		: nodo di primo livello con i figli settati
	 * @param tabella 	: nome della tabella che mappa l'albero
	 * @param db		: riferimento al db
	 * @return
	 */
	public static Nodo load_figli( Nodo nodo,String tabella,String campoEnabled, Connection db )
	{
		ArrayList<Nodo>	ret	= new ArrayList<Nodo>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		String sql = "" ;

		String filtro = "" ;
		String colonna1 = "" ;
		String colonna2 = "" ;
		if(!campoEnabled.equals(""))
		{
			filtro = " AND "+campoEnabled+ " = true ";
			colonna1 = ",t."+campoEnabled ;
			colonna2 = ","+campoEnabled ;
		}
		sql ="WITH RECURSIVE recursetree("+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+"," +colonnaSelezione+ " ) AS ( " +
		"SELECT "+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+",CAST("+colonnaPadre+" As varchar(1000)) as path_id ,CAST("+colonnaDescrizione+" As varchar(1000)) As path_desc "+colonna2 + ",enabled " +
		" FROM "+tabella+" WHERE "+colonnaPadre+" = -1 and enabled = true " +
		"UNION ALL " +
		"SELECT " +
		"t."+colonnaId+",t."+colonnaDescrizione+",t."+colonnaLivello+",t."+colonnaPadre+", t." +colonnaSelezione+","+
		"CAST(rt.path_id || ';' || t."+colonnaPadre+" As varchar(1000))  ,CAST(rt.path_desc || '->' || t."+colonnaDescrizione+" As varchar(1000)) As path_desc "+colonna1 + ",t.enabled " +
		" FROM "+tabella+" t " +
		"  JOIN recursetree rt ON rt."+colonnaId+" = t."+colonnaPadre+
		" ) " +
		"SELECT * FROM recursetree where "+colonnaPadre+ " ="+nodo.getId()+filtro+ " and enabled = true ORDER BY "+colonnaDescrizione ;
		try
		{
			stat = db.prepareStatement( sql );
			res		= stat.executeQuery();
			while( res.next() )
			{
				Nodo oia_n_f = null ;
				oia_n_f = loadResultSet( res) ;

				load_figli( oia_n_f,tabella,campoEnabled,  db );
				ret.add( oia_n_f );
				nodo.setLista_nodi(ret);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}

				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return nodo;
	}




	public static ArrayList<Nodo> loadNodiPrimoLivello(String tabella,String idColonna,String padreColonna,String descrzioneColonna,String livello ,String nodo,String campoEnabled ,String colonnaSele,  Connection db )
	{

		colonnaPadre = padreColonna ;
		colonnaSelezione = colonnaSele ;
		colonnaId = idColonna ;
		colonnaDescrizione = descrzioneColonna ; 
		colonnaLivello = livello ;
		ArrayList<Nodo>	ret	= new ArrayList<Nodo>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		String sql = "" ;
		String filtro = "" ;
		String colonna1 = "" ;
		String colonna2 = "" ;
		if(!campoEnabled.equals(""))
		{
			filtro = " AND "+campoEnabled+ " = true ";
			colonna1 = ",t."+campoEnabled ;
			colonna2 = ","+campoEnabled ;
		}
		if(nodo==null || nodo.equals("-1"))
			sql ="WITH RECURSIVE recursetree("+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+" ) AS ( " +
			"SELECT "+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+",CAST("+colonnaPadre+" As varchar(1000)) as path_id ,CAST("+colonnaDescrizione+" As varchar(1000)) As path_desc "+colonna2 +" , enabled "+
			" FROM "+tabella+" WHERE "+colonnaPadre+" = -1 and enabled = true " +
			" UNION ALL " +
			"SELECT " +
			"t."+colonnaId+",t."+colonnaDescrizione+",t."+colonnaLivello+",t."+colonnaPadre+", t." +colonnaSelezione+","+
			"CAST(rt.path_id || ';' || t."+colonnaPadre+" As varchar(1000)),CAST(rt.path_desc || '->' || t."+colonnaDescrizione+" As varchar(1000)) As path_desc " +colonna1 + ",t.enabled " +
			" FROM "+tabella+" t " +
			"  JOIN recursetree rt ON rt."+colonnaId+" = t."+colonnaPadre+" and t.enabled = true " +
			" ) " +
			"SELECT * FROM recursetree where "+colonnaPadre+ " = -1 and enabled = true "+filtro+" ORDER BY "+colonnaDescrizione ;

		else
			sql ="WITH RECURSIVE recursetree("+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+" ) AS ( " +
			"SELECT "+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+", CAST("+colonnaPadre+" As varchar(1000)) as path_id  ,CAST("+colonnaDescrizione+" As varchar(1000)) As path_desc "+colonna2 + ",enabled " +
			" FROM "+tabella+" WHERE "+colonnaPadre+" = -1 and enabled = true " +
			" UNION ALL " +
			"SELECT " +
			"t."+colonnaId+",t."+colonnaDescrizione+",t."+colonnaLivello+",t."+colonnaPadre+", t."+colonnaSelezione +","+
			"CAST(rt.path_id || ';' || t."+colonnaPadre+" As varchar(1000)) ,CAST(rt.path_desc || '->' || t."+colonnaDescrizione+" As varchar(1000)) As path_desc " +colonna1+ ",t.enabled " +
			" FROM "+tabella+" t " +
			"  JOIN recursetree rt ON rt."+colonnaId+" = t."+colonnaPadre+" " +
			" ) " +
			"SELECT * FROM recursetree where "+colonnaId+ " ="+nodo+ filtro+" and enabled = true and enabled = true ORDER BY "+colonnaDescrizione ;

		try
		{
			stat = db.prepareStatement( sql );
			res		= stat.executeQuery();
			while( res.next() )
			{
				Nodo oia_n_f = null ;
				oia_n_f = loadResultSet( res) ;
				load_figli( oia_n_f,tabella, campoEnabled , db );
				ret.add( oia_n_f );

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}

				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return ret;
	}





	public static Nodo getNodoFromListaById(ArrayList<Nodo> lista ,int idNodo)
	{
		for(Nodo nodo : lista)
		{
			if (nodo.getIdNodo() ==idNodo )
			{
				return nodo ;
			}
		}
		return null ;

	}


	
	
	
	/*
	 * 	QUESTO METODO RITORNA IL PATH DEI NODI DI UN ALBERO GENERICO (ATTUALMENTE MATRICI E CAMPIONI) , I CUI NODI SONO ASSOCIATI AL PIANO IN INPUT.
	 * 	DESCRIZIONE PARAMETRI INPUT
	 *  1. IDPIANO -> CODICE DEL PIANO DI MONITORAGGIO PER CUI SI VOGLIONO FILTRARE I NODI DELL'ALBERO
	 *  2.tabellaassociazionipiani -> NOME DELLA TABELLA DI MAPPING TRA I NODI (FOGLIE) DELL ALBERO E I PIANI
	 *  3.colonnatabellaassociazionipiani -> NOME DELLA COLONNA DELLA TABELLA tabellaassociazionipiani (PARAMETRO 2) CHE e' LA CHIAVE ESTERNA DEI NODI
	 *  4. tabella -> NOME DELLA TABELLA CHE MAPPA L'ALBERO
	 *  5. -> 	I CAMPI CHE SEGUNO RAPPRESENTANO I NOMI DELLE COLONNE DELLA TABELLA (PARAMETRO ) ESSI SONO CONFIGURATI IN UN XML , CHI CHIAMA
	 *   		QUESTO METODO PASSERA COME VALORI QUELLI LETTI DAL FILE XML.
	 * */
	public static ArrayList<Nodo> loadNodiAssociazioni(int idPiano,String tabellaassociazionipiani,String colonnatabellaassociazionipiani,String tabella,String idColonna,String padreColonna,String descrzioneColonna,String livello ,String nodo,String campoEnabled ,String colonnaSele,  Connection db )
	{

		colonnaPadre = padreColonna ;
		colonnaSelezione = colonnaSele ;
		colonnaId = idColonna ;
		colonnaDescrizione = descrzioneColonna ; 
		colonnaLivello = livello ;
		ArrayList<Nodo>	ret	= new ArrayList<Nodo>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		String sql = "" ;
		String filtro = "" ;
		String colonna1 = "" ;
		String colonna2 = "" ;
		if(!campoEnabled.equals(""))
		{
			filtro = " AND "+campoEnabled+ " = true ";
			colonna1 = ",t."+campoEnabled ;
			colonna2 = ","+campoEnabled ;
		}

		sql = 
			"WITH RECURSIVE recursetree("+colonnaId+" ,"+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+" ) AS ( "+
			"SELECT "+tabella+"."+colonnaId+","+tabella+"."+colonnaDescrizione+","+tabella+"."+colonnaLivello+","+tabella+"."+colonnaPadre+","+tabella+"."+colonnaSelezione+","+
			"CAST("+tabella+"."+colonnaId+"   As varchar(1000)) as path_id ,"+
			"CAST("+tabella+"."+colonnaDescrizione+" As varchar(1000)) As path_desc , enabled" +
			" "+  
			"FROM "+tabella+" "+
			"join "+tabellaassociazionipiani+" apm on apm."+colonnatabellaassociazionipiani+"="+tabella+"."+colonnaId+" and id_piano = ? where apm.trashed_date is null "+
			"UNION ALL "+
			"SELECT t."+colonnaId+",t."+colonnaDescrizione+",t."+colonnaLivello+",t."+colonnaPadre+", t."+colonnaSelezione+","+
			"CAST( t."+colonnaId+" || ';' ||rt.path_id  As varchar(1000)),"+
			"CAST( t."+colonnaDescrizione+" || '->' || rt.path_desc  As varchar(1000)) As path_desc ,t.enabled "+  
			"FROM "+tabella+" t   JOIN recursetree rt ON rt."+colonnaPadre+" = t "+
			"."+colonnaId+" and t.enabled = true  ) "+
			"SELECT distinct *  FROM recursetree where "+colonnaPadre+" = -1  and enabled = true  order by path_desc " ;



		try
		{
			stat = db.prepareStatement( sql );
			stat.setInt(1, idPiano);
			res		= stat.executeQuery();

			while( res.next() )
			{
				Nodo oia_n_f = loadResultSet( res) ;
				String [] path = res.getString("path_id").split(";");
				oia_n_f.setId( new Integer(path[path.length-1]));
				oia_n_f.setDescrizione(oia_n_f.getPath());
				if (path.length == 1)
					oia_n_f.setIdPadre(new Integer(path[path.length-1]));
				else
					oia_n_f.setIdPadre(new Integer(path[path.length-2]));
				
				ret.add( oia_n_f );

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{ 
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}

				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return ret;
	}


	public static ArrayList<Nodo> listaNodi(String tabella,String campoEnabled,  Connection db )
	{


		ArrayList<Nodo>	ret	= new ArrayList<Nodo>();
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		String sql = "" ;
		String filtro = "" ;
		String colonna1 = "" ;
		String colonna2 = "" ;
		if(!campoEnabled.equals(""))
		{
			filtro = " where "+campoEnabled+ " = true ";
			colonna1 = ",t."+campoEnabled ;
			colonna2 = ","+campoEnabled ;
		}
		sql ="WITH RECURSIVE recursetree("+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+" ) AS ( " +
		"SELECT "+colonnaId+","+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+ ",CAST("+colonnaPadre+" As varchar(1000)) as path_id ,CAST("+colonnaDescrizione+" As varchar(1000)) As path_desc " +colonna2+
		" FROM "+tabella+" WHERE "+colonnaPadre+" = -1 " +
		"UNION ALL " +
		"SELECT " +
		"t."+colonnaId+",t."+colonnaDescrizione+",t."+colonnaLivello+",t."+colonnaPadre+", t." +colonnaSelezione+"," +
		"CAST(rt.path_id || ';' || t."+colonnaPadre+" As varchar(1000))  ,CAST(rt.path_desc || '->' || t."+colonnaDescrizione+" As varchar(1000)) As path_desc " +colonna1+
		" FROM "+tabella+" t " +
		"  JOIN recursetree rt ON rt."+colonnaId+" = t."+colonnaPadre+" " +
		" ) " +
		"SELECT * FROM recursetree "+filtro+"  ORDER BY livello " ;
		try
		{
			stat = db.prepareStatement( sql );
			res		= stat.executeQuery();
			while( res.next() )
			{
				Nodo oia_n_f = null ;
				oia_n_f = loadResultSet( res) ;
				//load_figli( oia_n_f,tabella,  db );
				ret.add( oia_n_f );

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}

				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return ret;
	}


	/**
	 * PRESO IN INPUTL IL NOME DELLA TABELLA E ID DI UN NODO RITORNA IL NODO CORRISPONDENTE.
	 * IL NOME TABELLA IN INPUT SERVE PER RECUPERARE LA STRUTTURA DELLE COLONNE DAL FILE XML.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * 
	 * 
	 * */
	public static Nodo getRamo(int idNodo,ActionContext context,String nomeTabellaAlbero, Connection db ) throws ParserConfigurationException, SAXException, IOException
	{
		
		
		File file = new File(context.getServletContext().getRealPath("WEB-INF/trees.xml"));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbuil = dbf.newDocumentBuilder();
		org.w3c.dom.Document doc = dbuil.parse(file);
		doc.getDocumentElement().normalize();
		org.w3c.dom.NodeList nodeLst = doc.getElementsByTagName("tree");
	
		colonnaId = "" ; 
		colonnaPadre = "" ; 
		colonnaDescrizione = "" ; 
		String colonnaEnabled = "" ; 
		colonnaLivello = "" ;
		colonnaSelezione = "" ;
		String tabellaMappingPiani = "" ;
		String colonnatabellaMappingPiani = "" ;
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				if (nomeTabellaAlbero.equals(fstElmnt.getAttribute("name")))
				{

					tabellaMappingPiani = fstElmnt.getElementsByTagName("tablemappingpiani").item(0).getTextContent();
					colonnatabellaMappingPiani = fstElmnt.getElementsByTagName("colonnatablemappingpiani").item(0).getTextContent();

					colonnaId = fstElmnt.getElementsByTagName("id").item(0).getTextContent();
					colonnaPadre = fstElmnt.getElementsByTagName("padre").item(0).getTextContent();
					colonnaDescrizione = fstElmnt.getElementsByTagName("descrizione").item(0).getTextContent();
					colonnaLivello = fstElmnt.getElementsByTagName("livello").item(0).getTextContent();
					colonnaEnabled = fstElmnt.getElementsByTagName("enabled").item(0).getTextContent();
					colonnaSelezione = fstElmnt.getElementsByTagName("selezione").item(0).getTextContent();
					context.getRequest().setAttribute("nomeTabella", context.getParameter("nomeTabella"));

					context.getRequest().setAttribute("tabellaMappingPiani", tabellaMappingPiani);
					context.getRequest().setAttribute("colonnatabellaMappingPiani", colonnatabellaMappingPiani);

				}
			}

		}


		
		Nodo n = null ;
		PreparedStatement	stat= null;
		ResultSet			res	= null;
		String sql = "" ;
		String filtro = "" ;
		String colonna1 = "" ;
		String colonna2 = "" ;
	

		sql = 
			"WITH RECURSIVE recursetree("+colonnaId+" ,"+colonnaDescrizione+","+colonnaLivello+","+colonnaPadre+","+colonnaSelezione+" ) AS ( "+
			"SELECT "+nomeTabellaAlbero+"."+colonnaId+","+nomeTabellaAlbero+"."+colonnaDescrizione+","+nomeTabellaAlbero+"."+colonnaLivello+","+nomeTabellaAlbero+"."+colonnaPadre+","+nomeTabellaAlbero+"."+colonnaSelezione+","+
			"CAST("+nomeTabellaAlbero+"."+colonnaId+"   As varchar(1000)) as path_id ,"+
			"CAST("+nomeTabellaAlbero+"."+colonnaDescrizione+" As varchar(1000)) As path_desc , enabled" +
			" "+  
			"FROM "+nomeTabellaAlbero+" "+
			" WHERE " +nomeTabellaAlbero+"."+colonnaId +"="+idNodo +" "+
			"UNION ALL "+
			"SELECT t."+colonnaId+",t."+colonnaDescrizione+",t."+colonnaLivello+",t."+colonnaPadre+", t."+colonnaSelezione+","+
			"CAST( t."+colonnaId+" || ';' ||rt.path_id  As varchar(1000)),"+
			"CAST( t."+colonnaDescrizione+" || '->' || rt.path_desc  As varchar(1000)) As path_desc ,t.enabled "+  
			"FROM "+nomeTabellaAlbero+" t   JOIN recursetree rt ON rt."+colonnaPadre+" = t "+
			"."+colonnaId+" and t.enabled = true  ) "+
			"SELECT distinct *  FROM recursetree where "+colonnaPadre+" = -1  and enabled = true  order by path_desc " ;



		try
		{
			stat = db.prepareStatement( sql );
		
			res		= stat.executeQuery();
			
			if( res.next() )
			{
				n = loadResultSet( res) ;
				String [] path = res.getString("path_id").split(";");
				n.setId( new Integer(path[path.length-1]));
				n.setDescrizione(n.getPath());
				

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}

				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return n;
	}



	public static String tab = "" ;
	public static int level = -1 ;
	public static String idcampoIntero ;
	public static String idcampoTest ;
	public static String multiplo ;
	public static String divPath ;
	public static String idRiga ;
	public static String getAlbero(Nodo nodo,String nomeTabella,JspWriter out) throws IOException
	{
		buildTree2(nodo,nomeTabella,out) ;
		String s = tab ;
		tab = "" ;
		return s ;
	}


	public static String getAlberoCheckbox(Nodo nodo,String nomeTabella,JspWriter out,Tree treeNodiSelezionati) throws IOException
	{
		buildTree2WhitCheckBox(nodo,nomeTabella,out,treeNodiSelezionati) ;
		String s = tab ;
		tab = "" ;
		return s ;
	}

	public static void  buildTree2(Nodo nodo,String nomeTabella,JspWriter out) throws IOException
	{


		if(nodo.getLista_nodi()!=null && !nodo.getLista_nodi().isEmpty())
		{


			out.print("<li class=\"expandable\"><div class=\"hitarea expandable-hitarea\"></div><span><a class=\"mylinks\" onContextMenu=\"javascript:showPath('"+nodo.getPath().replaceAll("'", " ")+"','"+nodo.getId_paths()+"',"+nodo.getId()+","+nodo.getLivello()+")\" href=\"javascript:alert('Selezionare la matrice alimentare completa')\">"+nodo.getDescrizione()+"" +"</a></span>");
			out.print("<ul style=\"display: none;\">");
			for(Nodo n : nodo.getLista_nodi())
			{
				if (nodo.isSelMultipla()==true)
					n.setSelMultipla(true);
				buildTree2(n,nomeTabella,out);
			}
			out.print("</ul></li>");

		}
		else
		{

			out.print( "<li><a class=\"mylinks\" onContextMenu=\"javascript:showPath('"+nodo.getPath().replaceAll("'", " ")+"','"+nodo.getId_paths()+"',"+nodo.getId()+","+nodo.getLivello()+")\" href=\"javascript:setItem('"+nodo.isSelMultipla()+"','"+idcampoIntero+"','"+idcampoTest+"','"+nodo.getPath().replaceAll("'", " ")+"','"+nodo.getId_paths()+"',"+nodo.getId()+","+nodo.getLivello()+",'"+divPath+"','"+idRiga+"',"+nodo.getIdPadre()+")\">"+nodo.getDescrizione()+"" +"</a></li>");	

		}
		//return riga;

	}



	public static void  buildTree2WhitCheckBox(Nodo nodo,String nomeTabella,JspWriter out,Tree treeNodiSelezionati) throws IOException
	{


		if(nodo.getLista_nodi()!=null && !nodo.getLista_nodi().isEmpty())
		{


			out.print("<li class=\"expandable\">" +
					"<div class=\"hitarea expandable-hitarea\">" +
					"</div><span><a class=\"mylinks\"> " +
					""+nodo.getDescrizione()+"" +"" +
					"</a>" +
			"</span>");
			out.print("<ul style=\"display: none;\">");
			for(Nodo n : nodo.getLista_nodi())
			{
				if (nodo.isSelMultipla()==true)
					n.setSelMultipla(true);
				buildTree2WhitCheckBox(n,nomeTabella,out,treeNodiSelezionati);
			}
			out.print("</ul></li>");

		}
		else
		{
			
			String checked="";
			if (isSelezionato(nodo, treeNodiSelezionati)==true)
			{
				checked = "checked";
			}

			out.print( "<li>" +
					"<a class=\"mylinks\" >" +
					"<input type = 'checkbox' "+checked +"  name = 'nodo_ "+nodo.getIdPadre() +"' id='nodo_'"+nodo.getIdPadre()+"  value = '"+nodo.getId()+"'/>"+nodo.getDescrizione()+"" +"" +
					"</a>" +
					"</li>");	


		}
		//return riga;

	}
	
	public static boolean isSelezionato(Nodo nodo,Tree treeNodiSelezionati)
	{
		boolean selezionato = false ;
		for (Nodo n : treeNodiSelezionati.getListaNodi())
		{
			if (n.getId()==nodo.getId())
			{
				selezionato = true ;
				break ;
			}
		}
		return selezionato;
		
	}


	public static void  buildTree3(Nodo nodo,String nomeTabella)
	{


		if(nodo.getLista_nodi()!=null && !nodo.getLista_nodi().isEmpty())
		{

			if(!nodo.getDescrizione().equalsIgnoreCase("NON DEFINITO"))
			{
				tab += "<li>" +
				"<a class=\"mylinks\" href=\"javascript:showPath('"+nodo.getPath()+"','"+nodo.getId_paths()+"',"+nodo.getId()+","+nodo.getLivello()+")\">"+nodo.getDescrizione()+"" +
				"</a>" ;

				tab += "<ul>";
			}
			for(Nodo n : nodo.getLista_nodi())
			{

				buildTree3(n,nomeTabella);
			}
			tab += "</ul></li>";
			//tab +=  "<tr class='level_"+nodo.getLivello()+"'><td>"+nodo.getId()+"</td><td>"+nodo.getIdPadre()+"</td><td>"+nodo.getDescrizione()+"</td><td><a href=\"javascript:addLivello("+nodo.getId()+","+(nodo.getLivello()+1)+",'"+nomeTabella+"')\">Aggiungi Livello</a></td></tr>";
		}
		else
		{

			tab += "<li><a class=\"mylinks\" href=\"javascript:showPath('"+nodo.getPath()+"','"+nodo.getId_paths()+"',"+nodo.getId()+","+nodo.getLivello()+")\">"+nodo.getDescrizione()+"</a></li>";	

		}
		//return riga;

	}

	public static String getHtmlData(Nodo nodo,String nomeTabella)
	{
		buildTree3(nodo,nomeTabella) ;
		String s = tab ;
		tab = "" ;
		return s ;
	}


}
