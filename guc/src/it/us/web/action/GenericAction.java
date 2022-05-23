/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.action.endpointconnector.ConfigActionList;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.Parameter;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.EndPointList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.endpointconnector.OperazioneList;
import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Comune;
import it.us.web.bean.guc.GestoreAcque;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Struttura;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.ExtendedOptions;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.NotLoggedException;
import it.us.web.permessi.Permessi;
import it.us.web.util.DateUtils;
import it.us.web.util.FloatConverter;
import it.us.web.util.ParameterUtils;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.guc.UrlUtil;
import it.us.web.util.properties.Message;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import oracle.sql.ArrayDescriptor;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public abstract class GenericAction implements Action
{
	private static final Logger logger = LoggerFactory.getLogger( GenericAction.class );
	
	protected BUtente				utente		= null;
	protected Utente				utenteGuc		= null;
	protected HttpServletRequest	req			= null;
	protected HttpServletResponse	res			= null;
	protected ServletContext		context		= null;
	protected HttpSession			session		= null;
	protected Connection			db	= null;
	
	//attributi per l'XML
	protected static DocumentBuilderFactory factory;
	protected static DocumentBuilder builder;
	protected static Document doc;
	protected static XPathFactory xpathfactory;
	protected static XPath xpath;
	protected boolean chiamaReload = true ;
	
	

	private static Validator validator = null;
	
	public String DBI="";

	
	public boolean isChiamaReload() {
		return chiamaReload;
	}

	public void setChiamaReload(boolean chiamaReload) {
		this.chiamaReload = chiamaReload;
	}

	public void setConnectionDb( Connection db )
	{
		this.db = db ;
	}
	
	public Connection gtConnectionDb( )
	{
		return this.db ;
	}
	
	
	public static DocumentBuilderFactory getFactory() {
		return factory;
	}

	public static void setFactory(DocumentBuilderFactory factory) {
		GenericAction.factory = factory;
	}

	public static DocumentBuilder getBuilder() {
		return builder;
	}

	public static void setBuilder(DocumentBuilder builder) {
		GenericAction.builder = builder;
	}

	public static Document getDoc() {
		return doc;
	}

	public static void setDoc(Document doc) {
		GenericAction.doc = doc;
	}

	public static XPathFactory getXpathfactory() {
		return xpathfactory;
	}

	public static void setXpathfactory(XPathFactory xpathfactory) {
		GenericAction.xpathfactory = xpathfactory;
	}

	public static XPath getXpath() {
		return xpath;
	}

	public static void setXpath(XPath xpath) {
		GenericAction.xpath = xpath;
	}

	/**
	 * Utilizza le notation di hibernate per valutare se un bean e'' popolato rispettando i vincoli imposti
	 * @param bean
	 * @return null se il bean passa la validazione, una stringa che rappresenta gli errori riscontrati altrimenti
	 */
	public static String validaBean( Serializable bean )
	{
		String ret = null;
		
		Set<ConstraintViolation<Serializable>> violations = validator.validate( bean );
		
		if( violations.size() > 0 )
		{
			ret = "";
			for( ConstraintViolation<Serializable> cv: violations )
			{
				ret += ("\n- " + cv.getPropertyPath() + ((cv.getPropertyPath().toString().equals(""))?(""):(": ")) + cv.getMessage()+";");
			}
		}
		
		return ret;
	}
	
	public ArrayList<Parameter> parameterList( String prefisso )
	{
		return ParameterUtils.list( req, prefisso );
	}
	
	@SuppressWarnings("unchecked")
	public HashSet objectList( Class clazz, String prefisso ) throws Exception
	{
		HashSet ret = new HashSet();
		
		ArrayList<Parameter> params = parameterList( prefisso );
		
		for( Parameter p: params  )
		{
			if( booleanoFromRequest( p.getNome() ) )
			{
				//ret.add( persistence.find( clazz, p.getId() ) );
			}
		}
		
		return ret;
	}
	
	protected void gotoPage( String page )
		throws ServletException, IOException
	{	
		gotoPage( page, req, res );
	}
	
	protected void gotoPage( String template, String page )
		throws ServletException, IOException
	{	
		gotoPage( template, page, req, res );
	}
	
	protected void goToAction( Action actionToGo ) 
		throws Exception
	{
		goToAction( actionToGo, req, res );
	}
	
	protected void redirectTo( String url )
		throws IOException
	{
		redirectTo( url, req, res );
	}
	
	public void startup( HttpServletRequest _req, HttpServletResponse _res, ServletContext _context )
	{
		req		= _req;
		res		= _res;
		context	= _context;
		session	= _req.getSession();
		utente	= (BUtente) session.getAttribute( "utente" );
		utenteGuc	= (Utente) session.getAttribute( "utenteGuc" );

		req.setAttribute( "errore", session.getAttribute( "errore" ) );
		req.setAttribute( "messaggio", session.getAttribute( "messaggio" ) );
		
		session.setAttribute( "errore", null );
		session.setAttribute( "messaggio", null );
		if(ApplicationProperties.getAmbiente()==null)
		{
			ApplicationProperties.setAmbiente(req.getServerName().toString());
		}
	}
	
	protected void can( BGuiView gui, String permessi ) throws AuthorizationException
	{		
		isLogged();
		
		if( !Permessi.can( utente, gui, "w" ) ) //se nn posso scrivere
		{
			if( !Permessi.can( utente, gui, permessi ) ) //controllo se posso eseguire almeno il permesso richiesto (nel caso basti "r") 
			{
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita" ) );
			}
		}		
	}
	
	protected void isLogged() throws AuthorizationException
	{
		if( utente == null )
		{
			throw new NotLoggedException( Message.getSmart( "non_autenticato" ) );
		}
	}
	
	protected void setErrore( String errore, Exception e )
	{
		String err = errore + ":\n" + e.getMessage();
		setErrore( err );
	}
	
	protected void setErrore( Exception e )
	{
		setErrore( e.getMessage() );
	}
	
	protected void setErrore( String errore )
	{
		String err = (String) req.getAttribute( "errore" );
		err = (err == null) ? ("") : (err);
		err += "Errore: " + errore;
		req.setAttribute( "errore", err );
	}
	
	protected void setMessaggio( String messaggio )
	{		
		String mex = (String) req.getAttribute( "messaggio" );
		mex = (mex == null) ? ("") : (mex);
		mex += "\n" + messaggio;
		req.setAttribute( "messaggio", mex );
	}
	
	protected boolean booleanoFromRequest( String param )
	{
		String temp = req.getParameter( param );
		return 
			   "true"	.equalsIgnoreCase( temp ) 
					|| "ok"		.equalsIgnoreCase( temp ) 
					|| "si"		.equalsIgnoreCase( temp ) 	
					|| "yes"	.equalsIgnoreCase( temp )
					|| "on"		.equalsIgnoreCase( temp );
	}
	
	protected int interoFromRequest( String paramName )
	{
		int ret = -1;
		String temp = req.getParameter( paramName );
		try
		{
			ret = Integer.parseInt( temp );
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		return ret;
	}
	
	protected long longFromRequest( String paramName )
	{
		long ret = -1;
		String temp = req.getParameter( paramName );
		try
		{
			ret = Long.parseLong(temp);
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		return ret;
	}
	
	protected float floatFromRequest( String paramName )
	{
		float ret = -1;
		String temp = req.getParameter( paramName );
		try
		{
			FloatConverter fc = new FloatConverter();
			Float fo = (Float) fc.convert( Float.class, temp );
			if( fo != null )
			{
				ret = fo.floatValue();
			}
		}
		catch (Exception e)
		{
			logger.error( "", e );
		}
		return ret;
	}

	protected String stringaFromRequest( String paramName )
	{
		String temp = req.getParameter( paramName );
		return (temp == null) ? (temp) : (temp.trim());
	}
	
	protected Timestamp dataFromRequest( String paramName )
	{
		try
		{
			return DateUtils.parseTimestampSql( req.getParameter( paramName ) );
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	protected boolean isEmpty( String stringa )
	{
		return (stringa == null) ? (true) : (stringa.trim().length() <= 0);
	}
	
	@SuppressWarnings("deprecation")
	public static void gotoPage( String template, String page, HttpServletRequest req, HttpServletResponse res )
		throws ServletException, IOException
	{
		TilesContainer		tilesContainer		= TilesAccess.getContainer		( req.getSession().getServletContext() );
		AttributeContext	attributeContext	= tilesContainer.startContext	( req, res );

		attributeContext.putAttribute	( "body", new Attribute( page ) );
		tilesContainer.render			( template, req, res );
		tilesContainer.endContext		( req, res );
	}
	
	public static void gotoPage( String page, HttpServletRequest req, HttpServletResponse res )
		throws ServletException, IOException
	{
		gotoPage( "default", page, req, res );
	}
	
	public static void goToAction( Action actionToGo, HttpServletRequest req, HttpServletResponse res ) 
		throws Exception
	{
		Connection db = null ;
		try
		{
			req.getSession().setAttribute( "errore", req.getAttribute( "errore" ) );
			req.getSession().setAttribute( "messaggio", req.getAttribute( "messaggio" ) );
			
			actionToGo.startup( req, res, req.getSession().getServletContext() );
			db = it.us.web.db.DbUtil.getConnection();
			actionToGo.setConnectionDb(db);
			actionToGo.can();
			actionToGo.execute();
			
			it.us.web.db.DbUtil.close(db);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			it.us.web.db.DbUtil.close(db);
		}
	}
	
	public static void redirectTo( String url, HttpServletRequest req, HttpServletResponse res )
		throws IOException
	{
		req.getSession().setAttribute( "errore", req.getAttribute( "errore" ) );
		req.getSession().setAttribute( "messaggio", req.getAttribute( "messaggio" ) );
		
		res.sendRedirect( res.encodeRedirectURL( url ) );
	}
	
	public Boolean costruisciListaRuoli() throws Exception {
		  Boolean flag = false;
		  try {
			  
				EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			  
			  //Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
			  EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETRUOLIUTENTE); 
			  
			  ArrayList<Ruolo> ruoloUtenteList = null;
			  
			  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				  ruoloUtenteList = new ArrayList<Ruolo>();
				  System.out.println("END POINT "+epc.getEndPoint().getNome());
				  String dataSource = epc.getEndPoint().getDataSourceSlave();
				  
				 Connection conn = null;
				 conn = DbUtil.ottieniConnessioneJDBC(dataSource);
				  
				  try{
					  this.gestioneRuoliUtente(ruoloUtenteList, epc, conn);
					  req.setAttribute("ruoloUtenteList" + epc.getEndPoint().getNome(), ruoloUtenteList);
				  }
				  catch(Exception e){
					  
					  System.out.println("ERRORE "+e.getMessage());
					  logger.error("Errore durante la costruzione dei ruoli " + epc.getEndPoint().getNome());
					  e.printStackTrace();
				  }
				  finally{
						DbUtil.chiudiConnessioneJDBC(null, conn);
				  }		  
			  }
		  		
		  } 
		  catch (Exception e) {
			  flag=true;
			  logger.error("Errore durante la costruzione dei ruoli.");
			 throw(e);
			
		  }
		  return flag;
	  }
	
	public void costruisciListaCliniche(){
		  
		  try {
				EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			  //Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
			  EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETCLINICHEUTENTE); 
			  TreeMap<Integer, ArrayList<Clinica>> clinicheUtenteHash = null;
		
			  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				
					  clinicheUtenteHash = new TreeMap<Integer, ArrayList<Clinica>>();
					  this.gestioneClinicheUtente(clinicheUtenteHash, epc);
					  req.setAttribute("clinicheUtenteHash" + epc.getEndPoint().getNome(), clinicheUtenteHash);
				  
			  }
		  		
		  } 
		  catch (Exception e) {
			  logger.error("Errore durante la costruzione delle cliniche.");
			  e.printStackTrace();
		  }
		  
	}
	
	public void costruisciListaGestori(){
		  
		  try 
		  {
			  EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			  //Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
			  EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GET_GESTORI_ACQUE); 
			  ArrayList<GestoreAcque> gestoriList = null;
		
			  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++)
			  {
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				
				  gestoriList = new ArrayList<GestoreAcque>();
				  gestoriList = this.gestioneGestoriAcque(gestoriList, epc);
				  req.setAttribute("gestoriList", gestoriList);
				  
			  }
		  		
		  } 
		  catch (Exception e) {
			  logger.error("Errore durante la costruzione delle cliniche.");
			  e.printStackTrace();
		  }
		  
	}
	
	public void costruisciListaComuni(){
		  
		  try 
		  {
			  EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			  //Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
			  EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GET_COMUNI); 
			  ArrayList<Comune> comuniList = null;
		
			  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++)
			  {
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				
				  comuniList = new ArrayList<Comune>();
				  comuniList = this.gestioneComuneGestoriAcque(comuniList, epc);
				  req.setAttribute("comuni", comuniList);
				  
			  }
		  		
		  } 
		  catch (Exception e) {
			  logger.error("Errore durante la costruzione delle cliniche.");
			  e.printStackTrace();
		  }
		  
	}
	
	public void costruisciListaCaniliBDU(){
		  
		  try {
				EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			//Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
			EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETCANILIUTENTEBDU); 
			  TreeMap<Integer, ArrayList<Canile>> caniliUtenteHash = null;
		
			  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				  caniliUtenteHash = new TreeMap<Integer, ArrayList<Canile>>();
				  this.gestioneCaniliUtente(caniliUtenteHash,epc);
				  req.setAttribute("caniliUtenteHash" + epc.getEndPoint().getNome(), caniliUtenteHash);
				  }
		  } 
		  catch (Exception e) {
			  logger.error("Errore durante la costruzione dei canili.");
			  e.printStackTrace();
		  }
		  
	}
	
	//TO DO
//	public void costruisciListaStruttureGisa(){
//		 try {
//			 
//			 EndPointConnectorList listaEndPointConnector = inizializzaEndPointConnector();//Da prendere dalla session?
//			 //Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
//			 EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETSTRUTTUREUTENTEGISA); 
//			 TreeMap<Integer, ArrayList<Struttura>> struttureUtenteHash = null;
//		
//			 for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
//				 EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
//				 struttureUtenteHash = new TreeMap<Integer, ArrayList<Struttura>>();
//					  this.gestioneStruttureUtente(struttureUtenteHash, epc);
//					  req.setAttribute("struttureUtenteHash" +  epc.getEndPoint().getNome(), struttureUtenteHash);
//				  }
//		  } 
//		  catch (Exception e) {
//			  logger.error("Errore durante la costruzione dei canili.");
//			  e.printStackTrace();
//		  }
//	}
	 
	public void costruisciListaImportatori(){
		  
		  try {
				EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			//Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
			EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETIMPORTATORIUTENTE); 
			 TreeMap<Integer, ArrayList<Importatori>> importatoriUtenteHash = null;
		
			 for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
					EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
					importatoriUtenteHash = new TreeMap<Integer, ArrayList<Importatori>>();
					this.gestioneImportatoriUtente(importatoriUtenteHash, epc);
					  req.setAttribute("ImportatoriUtenteHash" + epc.getEndPoint().getNome(), importatoriUtenteHash);
				  }
		  } 
		  catch (Exception e) {
			  logger.error("Errore durante la costruzione delle cliniche.");
			  e.printStackTrace();
		  }
		  
	}
	
	public HashMap<String, Integer> costruisciListaProvince(){
		
		EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
		//Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
		EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.GETLISTAPROVINCE); 
		
		HashMap<String, Integer> HashProvince = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			
			for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
				EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				String queryProvince = epc.getSql();
				String dataSource = epc.getEndPoint().getDataSourceSlave();
				
				conn = DbUtil.ottieniConnessioneJDBC(dataSource);
				pst = conn.prepareStatement(queryProvince);
				
				DBI=pst.toString();
				
				rs = pst.executeQuery();
				
				HashProvince= new HashMap<String, Integer>();
				int code;
				String prov;
				while (rs.next()){
					code = rs.getInt("id");
					prov = rs.getString("descr");
					HashProvince.put(prov, code);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return HashProvince;
	}
	
	
	public HashMap<String,Boolean> verificaUtenteModificabile(String username){
			Connection conn = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			HashMap<String,Boolean> modificabile = new  HashMap<String,Boolean>();
			 try {
					EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
				//Da tutto l'endpoint connector prendo solo i record relativi a questa operazione
				EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.VERIFICAUTENTEMODIFICABILE); 
				
				for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
					EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
					String queryProvince = epc.getSql();
					String dataSource = epc.getEndPoint().getDataSourceSlave();
					conn = DbUtil.ottieniConnessioneJDBC(dataSource);
					pst = conn.prepareStatement(queryProvince);
					pst.setString(1, username);

					DBI=pst.toString();
						  
					rs = pst.executeQuery();
		
					while (rs.next()){
					 modificabile.put(epc.getEndPoint().getNome() , rs.getBoolean(1));
						  }
						  DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
					  }
			  } 
			  catch (Exception e) {
				  logger.error("Errore durante la verifica dell'utente.");
				  e.printStackTrace();
			  }
			return modificabile;
	}
	
	public boolean gestioneEsistenzaUtente ( EndPointConnector epc, Utente u, Connection conn){
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					pst.setString(++i, u.getOldUsername());
		
					DBI=pst.toString();
					
					rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK") || ris.equals("t")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			
		  return ret;
		  
	  }
	
public String gestioneLastLoginUtente ( EndPointConnector epc, Utente u, Connection conn, String timeout){
		
		String ris = "";
		int time = Integer.parseInt(timeout);
		
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getOldUsername());
					//Timeout
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH, -time);
					pst.setTimestamp(2, new Timestamp(cal.getTimeInMillis()));		
					System.out.println("timestamp: "+ new Timestamp(cal.getTimeInMillis()) );
					
					DBI=pst.toString();
					
					rs = pst.executeQuery();
					
					while (rs.next()){
						ris = rs.getString(1);
					}
					
					pst.close();
					rs.close();
				} catch (Exception e){
					e.printStackTrace();
				}
				
			
		  return ris;
		  
	  }
	
	
public String gestioneDisabilitaUtente ( EndPointConnector epc, Utente u, Connection conn){
		
		String ris = "";
		  
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getOldUsername());
					if(epc.getEndPoint().getId() == EndPoint.VAM)
						pst.setInt(++i, u.getClinicheVam().get(0).getIdClinica() );

					DBI=pst.toString();
					
					rs = pst.executeQuery();
					
					while (rs.next()){
						ris = rs.getString(1);
					}
					
					pst.close();
					rs.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			
		  return ris;
		  
	  }
	
	
	public boolean gestioneEsistenzaUtenteByStruttura (EndPointConnector epc, Utente u, Connection conn, int idStruttura){
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{
						String query = epc.getSql();
					
						pst = conn.prepareStatement(query);
						int i = 0;
						pst.setString(++i, u.getUsername());
						pst.setInt(++i, idStruttura);
			
						DBI=pst.toString();
						
						rs = pst.executeQuery();
	
						String ris = "";
						while (rs.next()){
							ris = rs.getString(1);
						}
						if (ris.equals("OK")){
							ret=true;
						}
						else{
							ret=false;
						}
						pst.close();
						rs.close();
					} catch (Exception e){
						e.printStackTrace();
					}
					
					
		  return ret;
	}
	
	
	public boolean gestioneInserimentoUtente (EndPointConnector epc, Utente u, Connection conn) throws Exception{
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				try{
				
					String dbDatasource = epc.getEndPoint().getDataSourceSlave();
					String query = epc.getSql();
					
					System.out.println("ENDPOINT "+epc.getEndPoint().getNome()+" ###### QUERY ##### : - "+query);
					
					pst = conn.prepareStatement(query);
					int i = 0;
										
					pst.setString(++i, u.getUsername());
					pst.setString(++i, u.getPassword());
					pst.setInt(++i, u.getRuoloId());
					pst.setInt(++i, u.getEnteredBy());
					pst.setInt(++i, u.getModifiedBy());
					pst.setBoolean(++i, u.isEnabled() );
					if(u.getAsl()!= null)
						pst.setInt(++i,u.getAsl().getId() );
					else
						pst.setInt(++i, -1 );
					pst.setString(++i, u.getNome());
					pst.setString(++i, u.getCognome());
					pst.setString(++i, u.getCodiceFiscale());
					pst.setString(++i, u.getNote());
					pst.setString(++i, u.getLuogo());
					pst.setString(++i, u.getNumAutorizzazione());
					pst.setString(++i, u.getEmail());
					if (u.getExpires()!=null){
						pst.setTimestamp(++i, new Timestamp(u.getExpires().getTime()));
					} else{
						Timestamp t = null;
						pst.setTimestamp(++i,t);
					}
					
					//if (dbHost.equals("endpointDBImportatore") || dbHost.equals("endpointDBBdu")){
				//	if (dbHost.equals("dbIMPORTATORIL") || dbHost.equals("dbBDUL")){
					if (epc.getEndPoint().getId() == EndPoint.IMPORTATORI || epc.getEndPoint().getId() == EndPoint.BDU){
						if (u.getId_importatore()!=null){
							pst.setInt(++i, u.getId_importatore());
						}
						else {
							pst.setInt(++i, -1);
						}
					}
					
					//------------------------------------------
					//GESTIONE CANILI - STRUTTURE E CLINICHE
					//if (dbHost.equals("endpointDBBdu")){
					//if (dbHost.equals("dbBDUL")){
					if (epc.getEndPoint().getId() == EndPoint.BDU){
								if (u.getCanilebduId()!=null){
							pst.setInt(++i, u.getCanilebduId());
						} else{
							pst.setInt(++i, -1);
						}
						pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato());
						pst.setString(++i, u.getNr_iscrione_albo_vet_privato());
					}
					
					//if (dbHost.equals("endpointDBGisa")){
					//if(endpoint.equalsIgnoreCase(""+GUCEndpoint.Gisa)){
					if (epc.getEndPoint().getId() == EndPoint.GISA){
									if (u.getStrutturagisaId()!=null){
							pst.setInt(++i, u.getStrutturagisaId());
						} else{
							pst.setInt(++i, -1);
						} 
						
						
						
					}
					
					//if (dbHost.equals("endpointDBVam")){
				//	if (dbHost.equals("dbVAML")){
					if (epc.getEndPoint().getId() == EndPoint.VAM){
								if (u.getClinicaId()!=null){
							pst.setInt(++i, u.getClinicaId());
						}
						else {
							pst.setInt(++i, -1);
						}
						pst.setString(++i, u.getLuogoVam());
						pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato_vam());
						pst.setString(++i, u.getNr_iscrione_albo_vet_privato_vam());
					}
					//---------------------------------------
					
					//---------------------------------------
					//GESTIONE ATTRIBUTI ESTESI (NUCLEO_ISPETTIVO, DPAT, ACCESS)
					if (epc.getEndPoint().getId() == EndPoint.GISA){
						// Super rammaggio per l'eventualita'' che i flag non siano presenti a livello di dati; li setto di default
						pst.setObject(17,"true");	pst.setObject(18,"false");	pst.setObject(19, "false");
					}
					
					HashMap<String,String> extOpt = u.getExtOption().get(epc.getEndPoint().getNome());
					TreeMap treeMap = new TreeMap();
					treeMap.putAll(extOpt);
					Iterator it = treeMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry e = (Map.Entry)it.next();
						//System.out.println("### "+i+": "+e.getKey()+" - "+e.getValue());
						pst.setObject(++i, e.getValue());
					}
					//---------------------------------------
					
					
					//if (dbHost.equals("endpointDBGisa_ext")){
					//if(endpoint.equalsIgnoreCase(""+GUCEndpoint.Gisa_ext)){
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getNumRegStab()!=null){
							pst.setString(++i, u.getNumRegStab());
						} else{
							pst.setString(++i, "");
						}
						pst.setInt(++i, u.getGestore());
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getPiva()!=null){
							pst.setString(++i, u.getPiva());
						} else{
							pst.setString(++i, "");
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getTipoAttivitaApicoltore()!=null){
							pst.setString(++i, u.getTipoAttivitaApicoltore());
						} else{
							pst.setString(++i, "");
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getComuneApicoltore()>0){
							pst.setInt(++i, u.getComuneApicoltore());
						} else{
							pst.setInt(++i, -1);
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getIndirizzoApicoltore()!=null){
							pst.setString(++i, u.getIndirizzoApicoltore());
						} else{
							pst.setString(++i, "");
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getCapIndirizzoApicoltore()!=null){
							pst.setString(++i, u.getCapIndirizzoApicoltore());
						} else{
							pst.setString(++i, "");
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getComuneTrasportatore()>0){
							pst.setInt(++i, u.getComuneTrasportatore());
						} else{
							pst.setInt(++i, -1);
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getIndirizzoTrasportatore()!=null){
							pst.setString(++i, u.getIndirizzoTrasportatore());
						} else{
							pst.setString(++i, "");
						}
					}
					
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						if (u.getCapIndirizzoTrasportatore()!=null){
							pst.setString(++i, u.getCapIndirizzoTrasportatore());
						} else{
							pst.setString(++i, "");
						}
					}
					
					pst.setString(++i, u.getTelefono());
					
					DBI=pst.toString();
					System.out.println("### ESEGUO : \n"+DBI+"\nsu : "+epc.getEndPoint().getNome());
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();

				}
				catch (Exception e){
					e.printStackTrace();
				}
				
		  return ret;
		  
	  }
	
	
	
	
	
	
	
	
	public boolean gestioneAccreditamentoSuap (EndPointConnector epc, Utente u, Connection conn) throws Exception{
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				try{
					
					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
										
					pst.setString(++i, u.getUsername());
					
					pst.setString(++i, u.getComuneSuap());
					pst.setString(++i, u.getIpSuap());
					pst.setString(++i, u.getPecSuap());
					pst.setString(++i, u.getCallbackSuap());
					pst.setString(++i, u.getSharedKeySuap());
					
					pst.setInt(++i, u.getLivelloAccreditamentoSuap());
					pst.setString(++i, u.getTelefono());
					pst.setString(++i, u.getCallbackSuap_ko());

					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();

				}
				catch (Exception e){
					e.printStackTrace();
				}
			
		  return ret;
		  
	  }
	
	
	public boolean gestioneCambioProfilo( EndPointConnector epc, Utente u, Connection conn) throws Exception{
		return gestioneCambioProfiloUtente(epc, u, conn) ;
	}
	
	public boolean gestioneRollbackProfilo(EndPointConnector epc, Utente u, Connection conn) throws Exception{
		return gestioneRollbackProfiloUtente(epc, u, conn) ;
	}
	
	
	public boolean gestioneAggiornamentoAnagraficaUtente ( EndPointConnector epc, Utente u, Connection conn) throws Exception{
		return gestioneAggiornamentoAnagraficaUtente (epc, u, conn, true);
	}
	
	public boolean gestioneAggiornamentoCfUtente ( EndPointConnector epc, Utente u, Connection conn) throws Exception{
		return gestioneAggiornamentoCfUtente (epc, u, conn, true);
	}
	
	public boolean gestioneAggiornamentoEmailUtente ( EndPointConnector epc, Utente u, Connection conn) throws Exception{
		return gestioneAggiornamentoEmailUtente (epc, u, conn, true);
	}
	
	public boolean gestioneCambioProfiloUtente ( EndPointConnector epc, Utente u, Connection conn) throws Exception{
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				try{
					
					String dbDatasource = epc.getEndPoint().getDataSourceSlave();
					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
										
					pst.setString(++i, u.getUsername());
					pst.setString(++i, u.getPassword());
					pst.setInt(++i, u.getRuoloId());
					pst.setInt(++i, u.getEnteredBy());
					pst.setInt(++i, u.getModifiedBy());
					pst.setBoolean(++i, u.isEnabled() );
					if(u.getAsl()!= null)
						pst.setInt(++i,u.getAsl().getId() );
					else
						pst.setInt(++i, -1 );
					pst.setString(++i, u.getNome());
					pst.setString(++i, u.getCognome());
					pst.setString(++i, u.getCodiceFiscale());
					pst.setString(++i, u.getNote());
					pst.setString(++i, u.getLuogo());
					pst.setString(++i, u.getNumAutorizzazione());
					pst.setString(++i, u.getEmail());
					if (u.getExpires()!=null){
						pst.setTimestamp(++i, new Timestamp(u.getExpires().getTime()));
					} else{
						Timestamp t = null;
						pst.setTimestamp(++i,t);
					}
					
					//if (dbHost.equals("endpointDBImportatore") || dbHost.equals("endpointDBBdu")){
				//	if (dbHost.equals("dbIMPORTATORIL") || dbHost.equals("dbBDUL")){
					if (epc.getEndPoint().getId() == EndPoint.IMPORTATORI || epc.getEndPoint().getId() == EndPoint.BDU){

								if (u.getId_importatore()!=null){
							pst.setInt(++i, u.getId_importatore());
						}
						else {
							pst.setInt(++i, -1);
						}
					}
					
					//------------------------------------------
					//GESTIONE CANILI - STRUTTURE E CLINICHE
					//if (dbHost.equals("endpointDBBdu")){
				//	if (dbHost.equals("dbBDUL")){
					if (epc.getEndPoint().getId() == EndPoint.BDU){

							if (u.getCanilebduId()!=null){
							pst.setInt(++i, u.getCanilebduId());
						} else{
							pst.setInt(++i, -1);
						}
						pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato());
						pst.setString(++i, u.getNr_iscrione_albo_vet_privato());
					}
					
					//if (dbHost.equals("endpointDBGisa")){
					
					//if(endpoint.equalsIgnoreCase(""+GUCEndpoint.Gisa)){
					if (epc.getEndPoint().getId() == EndPoint.GISA){
						if (u.getStrutturagisaId()!=null){
							pst.setInt(++i, u.getStrutturagisaId());
						} else{
							pst.setInt(++i, -1);
						} 
					}
					
					//if (dbHost.equals("endpointDBVam")){
					//if (dbHost.equals("dbVAML")){
					System.out.println("CONTROLLO ENDPOINT VAM");
					if (epc.getEndPoint().getId() == EndPoint.VAM){
						    System.out.println("ENDPOINT VAM: SI");
							if (u.getClinicheVam().size() > 0){
								System.out.println("ENDPOINT VAM: u.getClinicheVam().size() > 0 ");
							StringBuffer arrayCliniche = new StringBuffer();
						//	arrayCliniche.append("{");
							for (int k = 0; k < u.getClinicheVam().size(); k++) {
								if (k > 0)
									arrayCliniche.append(","+u.getClinicheVam().get(k));
								else
									arrayCliniche.append(u.getClinicheVam().get(k));
							}
							
							System.out.println("ENDPOINT VAM: arrayCliniche: " + arrayCliniche);

							pst.setString(++i, arrayCliniche.toString());
						}
						else {
							pst.setString(++i, "");
					}
							pst.setString(++i, u.getLuogoVam());
							pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato_vam());
							pst.setString(++i, u.getNr_iscrione_albo_vet_privato_vam());
//						if (u.getClinicaId()!=null){
//							pst.setInt(++i, u.getClinicaId());
//						}
//						else {
//							pst.setInt(++i, -1);
//						}
					}
					//---------------------------------------
					
					
					
					//---------------------------------------
					//GESTIONE ATTRIBUTI ESTESI (NUCLEO_ISPETTIVO, DPAT, ACCESS)
					HashMap<String,String> extOpt = u.getExtOption().get(epc.getEndPoint().getNome());
					TreeMap treeMap = new TreeMap();
					treeMap.putAll(extOpt);
					Iterator it = treeMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry e = (Map.Entry)it.next();
						pst.setObject(++i, e.getValue());
					}
					//---------------------------------------
					pst.setDate(++i, new Date (u.getDataScadenza().getTime()));
					
					pst.setBoolean(++i, u.isCessato());
					
					//if (dbHost.equals("endpointDBGisa_ext")){
				//	if (endpoint.equalsIgnoreCase(""+GUCEndpoint.Gisa_ext)){
					if (epc.getEndPoint().getId() == EndPoint.GISA_EXT){
								
					if (u.getNumRegStab()!=null && !"".equals(u.getNumRegStab())){
						pst.setString(++i, u.getNumRegStab());
					}
					else
						pst.setString(++i, "");
					}
					
					if(epc.getEndPoint().getId() == EndPoint.GISA_EXT)
					{
						pst.setInt(++i, u.getGestore());
						pst.setString(++i, u.getPiva());
					}
					
					
					/*if (epc.getEndPoint().getId() == EndPoint.VAM)
					{
						pst.setString(++i, u.getLuogoVam());
						pst.setInt(++i, u.getId_provincia_iscrizione_albo_vet_privato_vam());
						pst.setString(++i, u.getNr_iscrione_albo_vet_privato_vam());
					}*/
					
					
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();

				}
				catch (Exception e){
					e.printStackTrace();
				}
			
		  return ret;
		  
	  }
	
	public boolean gestioneRollbackProfiloUtente (EndPointConnector epc, Utente u, Connection conn) throws Exception{
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				try{
					//String dbHost = operationCambioprofiloUtente.getElementsByTagName("db_host").item(0).getTextContent();
					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
										
					pst.setString(++i, u.getUsername());
					pst.setDate(++i, new Date(u.getDataScadenza().getTime()));
					
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();

				}
				catch (Exception e){
					e.printStackTrace();
				}
				
		  return ret;
		  
	  }
	
	
	public boolean gestioneAggiornamentoAnagraficaUtente (EndPointConnector epc, Utente u, Connection conn, boolean reloadUtenti){
		  boolean ret = false;
		
				PreparedStatement pst = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getPassword());
					pst.setString(++i, u.getUsername());
					
					pst.setString(++i, u.getOldUsername());
					pst.setString(++i, u.getNome());
					pst.setString(++i, u.getCognome());
					pst.setString(++i, u.getCodiceFiscale());
					pst.setString(++i, u.getNote());
					pst.setString(++i, u.getEmail());
					if (epc.getEndPoint().getId() == EndPoint.VAM)
					{
						pst.setString(++i, u.getLuogoVam());
					}
					else
					{
						pst.setString(++i, u.getLuogo());
					}
					
					pst.setString(++i, u.getNumAutorizzazione());
					//---------------------------------------
					
					
					if(epc.getEndPoint().getId() == EndPoint.GISA_EXT)
					{
						pst.setInt(++i, u.getGestore());
					}
					
					
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
	
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			
		  return ret;
		  
	  }
	  
	public boolean gestioneAggiornamentoCfUtente (EndPointConnector epc, Utente u, Connection conn, boolean reloadUtenti){
		  boolean ret = false;
		
				PreparedStatement pst = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getUsername());
					pst.setString(++i, u.getCodiceFiscale());
					
				
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
	
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			
		  return ret;
		  
	  }
	
	public boolean gestioneAggiornamentoEmailUtente (EndPointConnector epc, Utente u, Connection conn, boolean reloadUtenti){
		  boolean ret = false;
		
				PreparedStatement pst = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getUsername());
					pst.setString(++i, u.getEmail());
					
				
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
	
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			
		  return ret;
		  
	  }
	
	public boolean gestioneInserimentoRuolo (EndPointConnector epc, Ruolo r, Connection conn){
		  boolean ret = false;
		
				PreparedStatement pst = null;
				try{

					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, r.getNomeRuolo());
					pst.setString(++i, r.getDescrizioneRuolo());
					pst.setInt(++i, r.getRuoloDaCopiare());
					if (epc.getEndPoint().getId() == EndPoint.GISA || epc.getEndPoint().getId() == EndPoint.GISA_EXT){
						pst.setBoolean(++i, r.isInAccess());
						pst.setBoolean(++i, r.isInDpat());
						pst.setBoolean(++i, r.isInNucleo());
						pst.setBoolean(++i, r.isQualifica());
						pst.setBoolean(++i, r.isListaNucleo());
						
					}  
				 
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
	
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			
		  return ret;
		  
	  }
	
/*	public boolean gestioneInserimentoUtentePostUpdate ( Element operationInsertUtente , GUCOperationType operationType, Utente u, Connection conn) throws Exception{
		return gestioneInserimentoUtentePostUpdate(operationInsertUtente, operationType, u, conn, true );
	}
	  
	public boolean gestioneInserimentoUtentePostUpdate ( Element operationInsertUtente , GUCOperationType operationType, Utente u, Connection conn, boolean reloadUtenti) {
		  boolean ret = false;
		  switch (operationType) {
			case Sql:
				PreparedStatement pst = null;
				
				try{
					String dbHost = operationInsertUtente.getElementsByTagName("db_host").item(0).getTextContent();
					
					String query = operationInsertUtente.getElementsByTagName("query").item(0).getTextContent();			
					pst = conn.prepareStatement(query);
					int i = 0;
					pst.setString(++i, u.getUsername());
					pst.setString(++i, u.getPassword());
					pst.setInt(++i, u.getRuoloId());
					pst.setInt(++i, u.getEnteredBy());
					pst.setInt(++i, u.getModifiedBy());
					pst.setBoolean(++i, u.isEnabled() );
					if(u.getAsl()!= null)
						pst.setInt(++i,u.getAsl().getId() );
					else
						pst.setInt(++i, -1 );
					pst.setString(++i, u.getNome());
					pst.setString(++i, u.getCognome());
					pst.setString(++i, u.getCodiceFiscale());
					pst.setString(++i, u.getNote());
					pst.setString(++i, u.getLuogo());
					pst.setString(++i, u.getNumAutorizzazione());
					pst.setString(++i, u.getEmail());
					if (u.getExpires()!=null){
						pst.setTimestamp(++i, new Timestamp(u.getExpires().getTime()));
					} else{
						Timestamp t = null;
						pst.setTimestamp(++i,t);
					}
					
					if (dbHost.equals("endpointDBImportatore") || dbHost.equals("endpointDBBdu")){
						if (u.getId_importatore()!=null){
							pst.setInt(++i, u.getId_importatore());
						}
						else {
							pst.setInt(++i, -1);
						}
					}
					
					if (dbHost.equals("endpointDBVam")){
						if (u.getClinicaId()!=null){
							pst.setInt(++i, u.getClinicaId());
						}
						else {
							pst.setInt(++i, -1);
						}
					}
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
					
					
					//reload utenti
					String urlReloadUtenti = "";
					urlReloadUtenti = operationInsertUtente.getElementsByTagName("url_reload_utenti").item(0).getTextContent();
	
					if (urlReloadUtenti!=null &&  !urlReloadUtenti.equals("")){
						urlReloadUtenti = urlReloadUtenti+""+u.getUsername();
						if(ret && urlReloadUtenti != null && !urlReloadUtenti.equals("") && chiamaReload == true){
							try{
								String resp = UrlUtil.getUrlResponse(urlReloadUtenti);
								logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
							}
							catch(Exception e){
								logger.error("Errore durante il Reload Utenti");
								e.printStackTrace();
							}
						}
					}
				} catch (Exception e){
					e.printStackTrace();
				}

				break;
			case Json:
				ret = true;
				break;
				

			default:
			try {
				throw new Exception("[gestioneInserimentoUtentePostUpdate] - Il tipo di operazione " + operationType + " non e'' gestito.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		  return ret;
		  
	  } */
	    
	public boolean gestioneDisabilitazioneUtente ( EndPointConnector epc, Utente u, Connection conn ) throws Exception{
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				try{
		
					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getUsername());
					
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
			
			
		  return ret;
		  
	  }
	
	public boolean gestioneAbilitazioneUtente(EndPointConnector epc, Utente u, Connection conn) throws Exception{
		return false;
	}
	  	  
	public boolean gestioneAbilitazioneUtenteByStruttura ( EndPointConnector epc, Utente u, Connection conn, int idStruttura) throws Exception{
		  boolean ret = false;
		 
				PreparedStatement pst = null;
				try{
					//String dbHost = operationEnableUtente.getElementsByTagName("db_host").item(0).getTextContent();
					String query = epc.getSql();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i, u.getUsername());
					pst.setInt(++i, idStruttura);
					
					
					DBI=pst.toString();
					
					ResultSet rs = pst.executeQuery();
					String ris = "";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
		  return ret;
		  
	  }  
	
	private void gestioneRuoliUtente( ArrayList<Ruolo> ruoloUtenteList, EndPointConnector epc, Connection conn ) throws Exception{
		
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{
	
					String queryRuoliUtente = epc.getSql();
					ExtendedOptions e = new ExtendedOptions();
					ArrayList<String> opt = e.getListOptions(epc.getEndPoint().getNome());
					pst = conn.prepareStatement(queryRuoliUtente);
				
					DBI=pst.toString();
					System.out.println("GET LISTA RUOLI "+epc.getEndPoint().getNome()+": "+pst.toString());

					rs = pst.executeQuery();
					Ruolo r = null;
					while( rs.next() ){
						r = new Ruolo();
						r.setRuoloInteger(rs.getInt("id_ruolo"));
						r.setRuoloString(rs.getString("descrizione_ruolo"));
						r.setNote(rs.getString("note_ruolo"));
						
						if (opt!=null && opt.size()>0){
							HashMap<String,String> o = new HashMap<String, String>();
							for (int i=0;i<opt.size();i++){
								o.put(epc.getEndPoint().getNome()+"_"+opt.get(i).toString(), rs.getObject(opt.get(i).toString()).toString());
							}
							r.setExtOpt(o);
						}
						ruoloUtenteList.add(r);
					}
				}
				catch (Exception e){
					throw e;
//					e.printStackTrace();
				}
			}
	
	
	
	private ArrayList<GestoreAcque> gestioneGestoriAcque( ArrayList<GestoreAcque> gestoriList, EndPointConnector epc ) throws Exception{
		  
		 
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
					
			String queryGestori = epc.getSql();
			String dataSource = epc.getEndPoint().getDataSourceSlave();
					
			conn = DbUtil.ottieniConnessioneJDBC( dataSource);
			pst = conn.prepareStatement(queryGestori);
			pst.setInt(1, -1);
			DBI=pst.toString();
					
			rs = pst.executeQuery();
			GestoreAcque g = null;		
			while( rs.next() )
			{
				g = new GestoreAcque();
				g.setId(rs.getInt("id"));
				g.setNome(rs.getString("nome"));
				gestoriList.add(g);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
		}
		return gestoriList;
	  
}
	
	private ArrayList<Comune> gestioneComuneGestoriAcque( ArrayList<Comune> comuneGestoriList, EndPointConnector epc ) throws Exception{
		  
		 
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
					
			String queryComuneGestori = epc.getSql();
			String dataSource = epc.getEndPoint().getDataSourceSlave();
					
			conn = DbUtil.ottieniConnessioneJDBC( dataSource);
			pst = conn.prepareStatement(queryComuneGestori);
			pst.setInt(1, -1);
			DBI=pst.toString();
					
			rs = pst.executeQuery();
			Comune c = null;		
			while( rs.next() )
			{
				c = new Comune();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				comuneGestoriList.add(c);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
		}
		return comuneGestoriList;
	  
}
		  
	  
	
	private void gestioneClinicheUtente( TreeMap<Integer, ArrayList<Clinica>> clinicheUtenteHash, EndPointConnector epc ) throws Exception{
		  
		 
				Connection conn = null;
						PreparedStatement pst = null;
						ResultSet rs = null;
						try{
							
							String queryRuoliUtente = epc.getSql();
							String dataSource = epc.getEndPoint().getDataSourceSlave();
							
							conn = DbUtil.ottieniConnessioneJDBC( dataSource);
							pst = conn.prepareStatement(queryRuoliUtente);

							DBI=pst.toString();
							
							rs = pst.executeQuery();
							Clinica c = null;		
							ArrayList<Clinica> listaCliniche = null;
							while( rs.next() ){
								c = new Clinica();
								c.setIdAsl(rs.getInt("asl_id"));
								c.setIdClinica(rs.getInt("id_clinica"));
								c.setDescrizioneClinica(rs.getString("nome_clinica"));
								if(clinicheUtenteHash.containsKey(c.getIdAsl())){
									clinicheUtenteHash.get(c.getIdAsl()).add(c);
								}
								else{
									listaCliniche = new ArrayList<Clinica>();
									listaCliniche.add(c);
									clinicheUtenteHash.put(c.getIdAsl(), listaCliniche);
								}
							}
						}
						catch (Exception e){
							e.printStackTrace();
						}
						finally{

							DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
						}
			  
	  }
	
	private void gestioneCaniliUtente( TreeMap<Integer, ArrayList<Canile>> caniliUtenteHash, EndPointConnector epc ) throws Exception{
		  
		
				Connection conn = null;
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{
				
					String queryCaniliUtente = epc.getSql();
					String dataSource = epc.getEndPoint().getDataSourceSlave();
					
					conn = DbUtil.ottieniConnessioneJDBC(dataSource);
					pst = conn.prepareStatement(queryCaniliUtente);
					
					DBI=pst.toString();
					
					rs = pst.executeQuery();
					Canile c = null;
					ArrayList<Canile> listaCanili = null;
					while( rs.next() ){
						c = new Canile();
						c.setIdAsl(rs.getInt("id_asl"));
						c.setIdCanile(rs.getInt("id_canile"));
						c.setDescrizioneCanile(rs.getString("ragione_sociale"));
						if(caniliUtenteHash.containsKey(c.getIdAsl())){
							caniliUtenteHash.get(c.getIdAsl()).add(c);
						}
						else{
							listaCanili = new ArrayList<Canile>();
							listaCanili.add(c);
							caniliUtenteHash.put(c.getIdAsl(), listaCanili);
						}
					}
				}
				finally{
					DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
				}
		  
	  }
	
	private void gestioneStruttureUtente( TreeMap<Integer, ArrayList<Struttura>> struttureUtenteHash, EndPointConnector epc ) throws Exception{
		
				Connection conn = null;
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{
					
					String queryStruttureUtente = epc.getSql();
					String dataSource = epc.getEndPoint().getDataSourceSlave();
					
					conn = DbUtil.ottieniConnessioneJDBC(dataSource);
					pst = conn.prepareStatement(queryStruttureUtente);
					
					DBI=pst.toString();
					
					rs = pst.executeQuery();
					Struttura s = null;
					ArrayList<Struttura> listaStrutture = null;
					while( rs.next() ){
						s = new Struttura();
						s.setIdAsl(rs.getInt("id_asl"));
						s.setIdStruttura(rs.getInt("id_struttura"));
						s.setDescrizioneStruttura(rs.getString("descrizione_lunga"));
						s.setIdPadre(rs.getInt("id_padre"));
						if(struttureUtenteHash.containsKey(s.getIdAsl())){
							struttureUtenteHash.get(s.getIdAsl()).add(s);
						}
						else{
							listaStrutture = new ArrayList<Struttura>();
							listaStrutture.add(s);
							struttureUtenteHash.put(s.getIdAsl(), listaStrutture);
						}
					}
				}
				finally{
					DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
				}
				
	}

	
	
	private void gestioneImportatoriUtente( TreeMap<Integer, ArrayList<Importatori>> importatoriUtenteHash, EndPointConnector epc ) throws Exception{
		  
			Connection conn = null;
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{
					
					String queryCaniliUtente = epc.getSql();
					String dataSource = epc.getEndPoint().getDataSourceSlave();
					
					conn = DbUtil.ottieniConnessioneJDBC(dataSource);
					pst = conn.prepareStatement(queryCaniliUtente);
					
					DBI=pst.toString();
					
					rs = pst.executeQuery();
					Importatori c = null;
					ArrayList<Importatori> listaImportatori = null;
					while( rs.next() ){
						c = new Importatori();
						c.setIdImportatore(rs.getInt("id_importatore"));
						c.setRagioneSociale(rs.getString("ragione_sociale"));
						c.setPartitaIva(rs.getString("partita_iva"));
						c.setIdAsl(rs.getInt("id_asl"));
						if(importatoriUtenteHash.containsKey(c.getIdAsl())){
							importatoriUtenteHash.get(c.getIdAsl()).add(c);
						}
						else{
							listaImportatori = new ArrayList<Importatori>();
							listaImportatori.add(c);
							importatoriUtenteHash.put(c.getIdAsl(), listaImportatori);
						}
					}
				}
				catch (Exception e){
					e.printStackTrace();
				}
				finally {
					DbUtil.chiudiConnessioneJDBC(rs, pst, conn);
				}
		  }
	
	
/*	
	public boolean gestioneDisabilitazioneGlobaleUtenti ( Element operationCheckEsistenzaUtente , GUCOperationType operationType, Connection conn){
		  boolean ret = false;
		  switch (operationType) {
			case Sql:
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{
					String query = operationCheckEsistenzaUtente.getElementsByTagName("query").item(0).getTextContent();					
					pst = conn.prepareStatement(query);
					rs = pst.executeQuery();
					String ris="";
					while (rs.next()){
						ris = rs.getString(1);
					}
					if (ris.equals("OK")){
						ret=true;
					}
					else{
						ret=false;
					}
					pst.close();
					rs.close();
				} catch (Exception e){
					e.printStackTrace();
				}
				break;
			case Json:
				ret = true;
				break;

			default:
				try {
					throw new Exception("[gestioneDisabilitazioneGlobaleUtenti] - Il tipo di operazione " + operationType + " non e'' gestito.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		  return ret;  
	  } */
}
