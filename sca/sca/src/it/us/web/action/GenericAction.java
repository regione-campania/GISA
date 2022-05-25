/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.Parameter;
import it.us.web.bean.UserOperation;
import it.us.web.bean.guc.Utente;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.NotLoggedException;
import it.us.web.permessi.Permessi;
import it.us.web.util.DateUtils;
import it.us.web.util.FloatConverter;
import it.us.web.util.Md5;
import it.us.web.util.ParameterUtils;
import it.us.web.dao.UtenteDAO;
import it.us.web.db.DbUtil;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.db.ApplicationProperties;
import it.us.web.util.properties.Message;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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
	
	public String getDescrizione()
	{
		return this.getClass().getName();
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
	 * Utilizza le notation di hibernate per valutare se un bean � popolato rispettando i vincoli imposti
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
	
	public void logOperazioniUtente(Utente utente, String operazione, String descrizioneOperazione, String ip) throws Exception
	{
		
		//STORICO OPERAZIONI UTENTE CENTRALIZZATO
		//RECUPERO TUTTI I PARAMETRI
		Enumeration requestParameters = ((HttpServletRequest)req).getParameterNames();
		HashMap<String, String> mapParameters = new HashMap<String, String>();
	    while (requestParameters.hasMoreElements()) {
	    	String value = null;
	        String element = (String) requestParameters.nextElement();
	        if (element != null && !("password").equals(element)){
	        	value = req.getParameter(element);
	        	mapParameters.put(element, value);
	        }
	        
	        if (element != null && value != null) {
	            logger.info("param Name : " + element + " value: " + value);
	        }
	    }
		String parameters = mapParameters.toString();
		
		
		if(!operazione.equals("login.LogoutSca")) {
			int idUser = -1;

			String username = null;
			if(utente == null){
				 username = mapParameters.get("username"); //utente nei parametri � l'username
				if(username == null){
					username = mapParameters.get("utente");
				}
				System.out.println("username recuperato : " +username);
				utente = UtenteDAO.authenticatebyUsername(username, db);
			}
			
			ArrayList<UserOperation> user_operazioni = (ArrayList<UserOperation>)req.getSession().getAttribute("operazioni");

			UserOperation uo = new UserOperation();
			if(utente != null && utente.getId() > 0){
				uo.setUser_id(utente.getId());
				uo.setUsername(utente.getUsername());
				req.getSession().setAttribute("idUser", utente.getId());
			}
			
			uo.setIp(ip);
			uo.setData(new Timestamp(new Date().getTime()));
			uo.setUrl(req.getRequestURL().toString()+(req.getQueryString()!=null ? "?"+req.getQueryString() : ""));
			uo.setParameter(parameters);
			uo.setUserBrowser(req.getHeader("user-agent"));
			uo.setAction(operazione);
			if (user_operazioni!=null){
				user_operazioni.add(uo);
			} else {
				user_operazioni = new ArrayList<UserOperation>();
				user_operazioni.add(uo);
			}
			
			req.getSession().setAttribute("ip", ip);
			req.getSession().setAttribute("operazioni", user_operazioni);
			
			if(operazione.equals("login.LoginEndpoint")){
				System.out.println("invalidata sessione");
				HashMap<String, HttpSession> utenti = (HashMap<String, HttpSession>) session.getServletContext().getAttribute("utenti");
				utenti.remove(utente.getUsername());
				session.setAttribute( "utenteGuc", null );
				context.setAttribute("utenti", utenti);
				session.invalidate();	
				
			}
			
		}
		
		
		
		
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
		if(ApplicationProperties.getAmbiente()==null){
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
	
	
	
	public boolean gestioneEsistenzaUtente ( Element operationCheckEsistenzaUtente , GUCOperationType operationType, Utente u, Connection conn){
		  boolean ret = false;
		  switch (operationType) {
			case Sql:
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{

					String query = operationCheckEsistenzaUtente.getElementsByTagName("query").item(0).getTextContent();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					pst.setString(++i, u.getOldUsername());
		
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
				
				break;
			case Json:
				ret = true;
				break;
				
			default:
				try {
					throw new Exception("[gestioneEsistenzaUtente] - Il tipo di operazione " + operationType + " non � gestito.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		  return ret;
		  
	  }
	
	public static void writeStorico(ArrayList<UserOperation> op, Boolean automatico){
		Context ctx;
		Connection db = null;
		try {
			if (op!=null && op.size()>0){
				db = DbUtil.getConnessioneStorico();		
						
				if (db!=null){
					for (int i=0; i<op.size();i++){
						op.get(i).insert(db, automatico);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore nella scrittura sul db storico. I dati sono stati inseriti sul db locale");
			Connection conn = null;
			try {
				
				conn = DbUtil.getConnection();
				if (conn!=null){
					for (int i=0; i<op.size();i++){
						op.get(i).insert(conn, automatico);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (conn!=null){
					try {
						conn.close();
					} catch (SQLException e1){
						e1.printStackTrace();
					}
				}
			}
		} finally {
			if (db!=null)
				try {
					db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static void writeLoginFault(String username, String password, String ip, String error, boolean blocked){
		if (ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente")!=null &&
				ApplicationProperties.getProperty("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
			Connection conn = null;
			try {
				
				conn = DbUtil.getConnessioneStorico();
				if (conn!=null){
					PreparedStatement pst_select = conn.prepareStatement("select max(data) as maxTime,n_attempts from login_fallite where endpoint='SCA' and username = ? and trashed_date is null group by n_attempts order by n_attempts desc");
					pst_select.setString(1, username);
					ResultSet rs = pst_select.executeQuery();
					int num = 0;
					Timestamp ltemp = null;
					if(rs.next()){
						num = rs.getInt("n_attempts");
						ltemp = rs.getTimestamp("maxTime");
					}
					PreparedStatement pst = conn.prepareStatement("insert into login_fallite (id,endpoint,ip,username,password, data,error_message, n_attempts,last_attempts, blocked) values (nextval('login_fallite_id_seq'),'SCA',?,?,?,now(),?,?,?,?);");
					int i=0;
					pst.setString(++i, ip);
					pst.setString(++i, username);
					pst.setString(++i,  Md5.encrypt(password));
					pst.setString(++i, error);
					pst.setInt(++i, num+1);
					pst.setTimestamp(++i, ltemp);
					pst.setBoolean(++i, blocked);
					pst.execute();
					pst.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			} 	
		}
	}
	
	
	
	
	
	
	
}
