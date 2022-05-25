/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.Parameter;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.UserOperation;
import it.us.web.bean.UtentiOperazioniModifiche;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.CodaServiziBdr;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.NotLoggedException;
import it.us.web.exceptions.ValidationBeanException;
import it.us.web.exceptions.ValidationBeanExceptionRedirect;
import it.us.web.util.DateUtils;
import it.us.web.util.FloatConverter;
import it.us.web.util.ParameterUtils;
import it.us.web.util.bean.Bean;
import it.us.web.util.properties.Application;
import it.us.web.util.properties.Message;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericAction implements Action
{
	private static final Logger logger = LoggerFactory.getLogger( GenericAction.class );
	
	protected BUtente				utente		= null;
	protected HashMap<String, HttpSession>   utenti = null;
	protected String				ruoloUtente	= null;
	protected HttpServletRequest	req			= null;
	protected HttpServletResponse	res			= null;
	protected ServletContext		context		= null;
	protected HttpSession			session		= null;
	protected Persistence			persistence	= null;
	protected Connection			connectionBdu	= null;
	protected Connection			connection	    = null;
	protected int					idCc;
	protected CartellaClinica 		cc;
	protected Set<Object> 	beanModificati = null;
	private String prova = "";
	
	private static Validator validator = null;
	
	static
	{
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
	}
	
	@Override
	public Persistence getPersistence()
	{
		return persistence;
	}
	
	@Override
	public void setPersistence(Persistence persistence)
	{
		this.persistence = persistence;
	}
	
	@Override
	public Connection getConnectionBdu()
	{
		return connectionBdu;
	}
	
	@Override
	public void setConnectionBdu(Connection connectionBdu)
	{
		this.connectionBdu = connectionBdu;
	}
	
	@Override
	public Connection getConnection()
	{
		return connection;
	}
	
	@Override
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}
	
	/**
	 * Utilizza le notation di hibernate per valutare se un bean è popolato rispettando i vincoli imposti
	 * @param bean
	 * @return null se il bean passa la validazione, una stringa che rappresenta gli errori riscontrati altrimenti
	 * @throws ValidationBeanException 
	 */
	public static void validaBean( Serializable bean, Action actionToGo ) throws ValidationBeanException
	{
		String errori = "";
		
		if(bean==null)
			errori = "";
		
		Set<ConstraintViolation<Serializable>> violations = validator.validate( bean );
		
		if( violations.size() > 0 )
		{
			errori = "";
			for( ConstraintViolation<Serializable> cv: violations )
			{
				errori += ("\n- " + cv.getPropertyPath() + ((cv.getPropertyPath().toString().equals(""))?(""):(": ")) + cv.getMessage()+";");
			}
		}
		
		
		if(!errori.equals(""))
			throw new ValidationBeanException(errori, actionToGo);
	}
	
	public static void validaBeanRedirect( Serializable bean, String url ) throws ValidationBeanExceptionRedirect
	{
		String errori = "";
		
		if(bean==null)
			errori = "";
		
		Set<ConstraintViolation<Serializable>> violations = validator.validate( bean );
		
		if( violations.size() > 0 )
		{
			errori = "";
			for( ConstraintViolation<Serializable> cv: violations )
			{
				errori += ("\n- " + cv.getPropertyPath() + ((cv.getPropertyPath().toString().equals(""))?(""):(": ")) + cv.getMessage()+";");
			}
		}
		
		if(!errori.equals(""))
			throw new ValidationBeanExceptionRedirect(errori, url);
	}
	
	public ArrayList<Parameter> parameterList( String prefisso, boolean idDaNome )
	{
		return ParameterUtils.list( req, prefisso, idDaNome );
	}
	
	@SuppressWarnings("unchecked")
	public HashSet objectList( Class clazz, String prefisso ) throws Exception
	{
		HashSet ret = new HashSet();
		
		ArrayList<Parameter> params = parameterList( prefisso, true );
		
		for( Parameter p: params  )
		{
			if( /*p.getId() > 0 )*/ booleanoFromRequest( p.getNome() ) )
			{
				//ret.add( persistence.find( clazz, Integer.parseInt(p.getValore()) ) );
				ret.add( persistence.find( clazz, p.getId() ) );
			}
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public HashSet objectListDaValore( Class clazz, String prefisso ) throws Exception
	{
		HashSet ret = new HashSet();
		
		ArrayList<Parameter> params = parameterList( prefisso, false );
		
		for( Parameter p: params  )
		{
			if( p.getId() > 0 )
			{
				ret.add( persistence.find( clazz, Integer.parseInt(p.getValore()) ) );
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
		utenti = (HashMap<String, HttpSession>)context.getAttribute("utenti");
		beanModificati = new HashSet<Object>();

		ruoloUtente	= (String) session.getAttribute( "ruoloUtente" );
		idCc	= session.getAttribute( "idCc" )==null?(0):((Integer)session.getAttribute( "idCc" ));
		
		if (idCc > 0){
			try { 
				cc = (CartellaClinica) persistence.find(CartellaClinica.class, idCc);
				session.setAttribute("cc", cc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		req.setAttribute( "errore", session.getAttribute( "errore" ) );
		req.setAttribute( "messaggio", session.getAttribute( "messaggio" ) );
		
		session.setAttribute( "errore", null );
		session.setAttribute( "messaggio", null );
	}
	
	@Override
	public void logOperazioniUtente(BUtente utente, String operazione, String descrizioneOperazione, String ip) 
	{
/*		if(utente==null && operazione.equals("login.Ballot"))
			utente = (BUtente)getPersistence().find(BUtente.class, interoFromRequest("id"));
		UtentiOperazioni uo = new UtentiOperazioni();
		uo.setEntered(new Date());
		uo.setEnteredBy(utente);
		uo.setModified(new Date());
		uo.setModifiedBy(utente);
		uo.setUtente(utente);
		uo.setOperazione(operazione);
		uo.setDescrizioneOperazione(descrizioneOperazione);
		if(utente!=null)
			uo.setAccessPositionErr(utente.getAccessPositionErr());
		if(utente!=null)
			uo.setAccessPositionLat(utente.getAccessPositionLat());
		if(utente!=null)
			uo.setAccessPositionLon(utente.getAccessPositionLon());
		if(utente!=null)
			uo.setUsername(utente.getUsername());
		else
			uo.setUsername(stringaFromRequest("utente"));
		uo.setIp(ip);
		uo.setCc(cc); 
		
		Iterator<Object> beans = beanModificati.iterator();
		Set<UtentiOperazioniModifiche> uoms = new HashSet<UtentiOperazioniModifiche>();
		while(beans.hasNext())
		{
			Object modifica = beans.next();
			UtentiOperazioniModifiche uom = new UtentiOperazioniModifiche();
			uom.setModifiche(Bean.getModifiche(modifica));
			uom.setOperazione(uo);
			String value = "";
			Method metodo2 = modifica.getClass().getMethod("getNomeEsame");
			if(metodo2!=null)
			{
				value = (String)metodo2.invoke(modifica, null);
			}
			else
			{
				value = modifica.getClass().getName();
			}
			uom.setBean(value);
			uoms.add(uom);
			getPersistence().insert(uom);
		}
		uo.setModifiche(uoms); */
	//	getPersistence().insert(uo);
		
		
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
		
		ArrayList<UserOperation> user_operazioni = (ArrayList<UserOperation>)req.getSession().getAttribute("operazioni");
		int idUser = -1;
		if (operazione.equals("login.Login"))
		{
			try
			{
			List<SuperUtente> sus = new ArrayList<SuperUtente>();
			String sql = "select * from utenti_super where trashed_date is null and username = ? and enabled " ;
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, stringaFromRequest("utente"));
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
			{
				SuperUtente su = new SuperUtente();
				su = (SuperUtente)Bean.populate(su, rs);
				su.setNumIscrizioneAlbo(rs.getString("num_iscrizione_albo"));
				su.setSiglaProvincia((rs.getString("sigla_provincia")));
				sus.add(su);
			}
			
			if (sus.size()>0){
				UserOperation uo = new UserOperation();
				uo.setUser_id(sus.get(0).getId());
				uo.setUsername(sus.get(0).getUsername());
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
				req.getSession().setAttribute("idUser", sus.get(0).getId());
				req.getSession().setAttribute("ip", ip);
				req.getSession().setAttribute("operazioni", user_operazioni);
				idUser = sus.get(0).getId();
			}
			
		}catch(Exception e)
			{
			logger.error("#VAM "+e.getMessage());
			}
			
		} if (operazione.equals("login.Ballot")){
			if (utente!=null){
				UserOperation uo = new UserOperation();
				uo.setUser_id(utente.getSuperutente().getId());
				uo.setUsername(utente.getUsername());
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
				req.getSession().setAttribute("idUser", utente.getSuperutente().getId());
				req.getSession().setAttribute("ip", ip);
				req.getSession().setAttribute("operazioni", user_operazioni);
				idUser = utente.getSuperutente().getId();
			}
		} else if (operazione.equals("login.LoginNoPassword")) {
			SuperUtente su = null;
			if (utente==null){
				su = (SuperUtente)req.getSession().getAttribute("su");
			} else {
				su = utente.getSuperutente();
			}
			UserOperation uo = new UserOperation();
			uo.setUser_id(su.getId());
			uo.setUsername(su.getUsername());
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
			req.getSession().setAttribute("idUser", su.getId());
			req.getSession().setAttribute("ip", ip);
			req.getSession().setAttribute("operazioni", user_operazioni);
			idUser = su.getId();
		} else if (!operazione.equals("Index")) {
			if (utente!=null){
				UserOperation uo = new UserOperation();
				uo.setUser_id(utente.getSuperutente().getId());
				uo.setUsername(utente.getUsername());
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
				req.getSession().setAttribute("idUser", utente.getSuperutente().getId());
				req.getSession().setAttribute("ip", ip);
				req.getSession().setAttribute("operazioni", user_operazioni);
				idUser = utente.getSuperutente().getId();
			}
		} 
		
		Iterator<Object> beans = beanModificati.iterator();
		while(beans.hasNext())
		{
			
			
			Object modifica = beans.next();
			UtentiOperazioniModifiche uom = new UtentiOperazioniModifiche();
			try {
				uom.setModifiche(Bean.getModifiche(modifica));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			}
			uom.setOperazione(-1);
			uom.setIdcc(cc.getId());
			uom.setUserid(idUser);
			uom.setEntered(new Date());
			uom.setNuovagestione(true);
			uom.setUrloperazione(operazione);
			uom.setDescrizioneoperazione(descrizioneOperazione);
			String value = "";
			Method metodo2=null;
			try {
				metodo2 = modifica.getClass().getMethod("getNomeEsame");
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			}
			if(metodo2!=null){
				try {
					value = (String)metodo2.invoke(modifica, null);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					logger.error("#VAM "+e.getMessage());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					logger.error("#VAM "+e.getMessage());
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					logger.error("#VAM "+e.getMessage());
				}
			}
			else{
				value = modifica.getClass().getName();
			}
			uom.setBean(value);
			try {
				getPersistence().insert(uom);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("#VAM "+e.getMessage());
			}
		}		
	}
	
	protected void canSession( BGuiView gui, String permessi ) throws AuthorizationException
	{	
		//NUOVA GESTIONE CONTROLLO PERMESSI
		isLoggedSession();
		
		HashMap<Integer, HashMap<String, String>> funzioniConcesse = (HashMap<Integer, HashMap<String, String>>)context.getAttribute("funzioniConcesse");
		
		
		if(!(funzioniConcesse!=null && funzioniConcesse.get(utente.getSuperutente().getId())!=null &&  funzioniConcesse.get(utente.getSuperutente().getId()).get(gui.getKey())!=null)){
			if (!context.getAttribute("dbMode").equals("slave"))
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita" ) );
			else
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita_in_ambiente_slave" ) );
		}
		
		//VECCHIA GESTIONE CONTROLLO PERMESSI
		/*isLogged();
		
		if( !Permessi.can( utente, gui, "w" ) ) //se nn posso scrivere
		{
			if( !Permessi.can( utente, gui, permessi ) ) //controllo se posso eseguire almeno il permesso richiesto (nel caso basti "r") 
			{
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita" ) );
			}
		}	*/	
	}
	
	protected void can( BGuiView gui, String permessi ) throws AuthorizationException
	{	
		//NUOVA GESTIONE CONTROLLO PERMESSI
		isLoggedSessionContext();
		
		HashMap<Integer, HashMap<String, String>> funzioniConcesse = (HashMap<Integer, HashMap<String, String>>)context.getAttribute("funzioniConcesse");

		
		if(!(funzioniConcesse!=null && funzioniConcesse.get(utente.getSuperutente().getId())!=null &&  funzioniConcesse.get(utente.getSuperutente().getId()).get(gui.getKey())!=null)){
			if (!context.getAttribute("dbMode").equals("slave"))
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita" ) );
			else
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita_in_ambiente_slave" ) );
		}
		
		//VECCHIA GESTIONE CONTROLLO PERMESSI
		/*isLogged();
		
		if( !Permessi.can( utente, gui, "w" ) ) //se nn posso scrivere
		{
			if( !Permessi.can( utente, gui, permessi ) ) //controllo se posso eseguire almeno il permesso richiesto (nel caso basti "r") 
			{
				throw new AuthorizationException( Message.getSmart( "azione_non_consentita" ) );
			}
		}	*/	
		
	}
	
	public void canClinicaCessata() throws AuthorizationException
	{
		if(utente!=null && utente.getClinica()!=null && utente.getClinica().getDataCessazione()!=null)
			throw new AuthorizationException( "Impossibile apportare questa modifica su una clinica cessata " );
	}
	
	protected void canCc( CartellaClinica cc ) throws AuthorizationException
	{	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(cc.getDataChiusura()!=null && !cc.getEsisteTrasfAutomaticoPerNecroscopia() && !utente.getRuolo().equals("Referente Asl") && !utente.getRuolo().equals("14"))
			throw new AuthorizationException( "Impossibile modificare la cc in quanto chiusa il " + sdf.format(cc.getDataChiusura()) );
		if(cc.getEnteredBy().getClinica().getId()!=utente.getClinica().getId())
			throw new AuthorizationException( "Impossibile modificare la cc non appartenente alla clinica " + utente.getClinica().getNome() );
	}
	
	public void isLoggedSession() throws AuthorizationException
	{
		if( utente == null)
		{
			throw new NotLoggedException( Message.getSmart( "non_autenticato" ) );
		}
	}
	
	public void isLoggedSessionContext() throws AuthorizationException
	{
		if( utente == null || utenti == null || !utenti.containsKey(utente.getSuperutente().getUsername()))
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
	
	
	protected void setStringRequest( String name,String value )
	{		
		
		req.setAttribute( name, value );
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
			logger.error( "", "Errore nella conversione del parametro " + paramName + ", valore: " + temp + ". Messaggio dell'eccezione: " + e.getMessage() );
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
			logger.error( "", "Errore nella conversione del parametro " + paramName + ", valore: " + temp + ". Messaggio dell'eccezione: " + e.getMessage() );
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
			logger.error( "", "Errore nella conversione del parametro " + paramName + ", valore: " + temp + ". Messaggio dell'eccezione: " + e.getMessage() );
		}
		return ret;
	}
	
	
	protected double doubleFromRequest(String paramName){
		
		double toReturn = 0.0;
		String temp = req.getParameter( paramName );
		
		
		if (temp != null && !temp.equals(""))
		{
			 toReturn = Double.parseDouble(temp);
		}
		
		return toReturn;
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
	
	public void goToAction( Action actionToGo, HttpServletRequest req, HttpServletResponse res ) 
		throws Exception
	{
		try
		{
			req.getSession().setAttribute( "errore", req.getAttribute( "errore" ) );
			req.getSession().setAttribute( "messaggio", req.getAttribute( "messaggio" ) );
			
			if (!(actionToGo instanceof Index ))
				actionToGo.setPersistence( persistence );
			
			actionToGo.startup( req, res, req.getSession().getServletContext() );

			actionToGo.can();
			actionToGo.setSegnalibroDocumentazione();
			actionToGo.execute();
//			if (!(actionToGo instanceof Index ))
//				PersistenceFactory.closePersistence( actionToGo.getPersistence(), true );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
//			if (!(actionToGo instanceof Index ))
//				PersistenceFactory.closePersistence( actionToGo.getPersistence(), false );
			String messaggioErrore = "Errore: "+e.getMessage()+" \n.";
			if( e instanceof JDBCConnectionException )
				messaggioErrore+="Si prega di riprovare.";
			req.setAttribute( "errore", messaggioErrore );
			if( e instanceof NotLoggedException || req.getSession().getAttribute("utente")==null)
			{
				try
				{
					goToAction( new Index(), req, res );
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				GenericAction.gotoPage( "/jsp/errore/errore.jsp", req, res );
			}
		}
		/*{
			e.printStackTrace();
			PersistenceFactory.closePersistence( actionToGo.getPersistence(), false );
		}*/
	}
	
	
	public static void forwardToAction( Action actionToGo, HttpServletRequest req, HttpServletResponse res ) 
			throws Exception
		{
			try
			{
				req.getSession().setAttribute( "errore", req.getAttribute( "errore" ) );
				req.getSession().setAttribute( "messaggio", req.getAttribute( "messaggio" ) );
				
				if (!(actionToGo instanceof Index ))
				{
					actionToGo.setPersistence(  PersistenceFactory.getPersistence()  );
					aggiornaConnessioneAperta(req);
				}
				actionToGo.startup( req, res, req.getSession().getServletContext() );

				actionToGo.can();
				actionToGo.execute();
				if (!(actionToGo instanceof Index ))
				{
					PersistenceFactory.closePersistence( actionToGo.getPersistence(), true );
					aggiornaConnessioneChiusa(req);
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				if (!(actionToGo instanceof Index ))
				{
					PersistenceFactory.closePersistence( actionToGo.getPersistence(), false );
					aggiornaConnessioneChiusa(req);
				}
				String messaggioErrore = "Errore: "+e.getMessage()+" \n.";
				if( e instanceof JDBCConnectionException )
					messaggioErrore+="Si prega di riprovare.";
				req.setAttribute( "errore", messaggioErrore );
				if( e instanceof NotLoggedException || req.getSession().getAttribute("utente")==null)
				{
					try
					{
						GenericAction.forwardToAction( new Index(), req, res );
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}
				}
				else
				{
					GenericAction.gotoPage( "/jsp/errore/errore.jsp", req, res );
				}
			}
			/*{
				e.printStackTrace();
				PersistenceFactory.closePersistence( actionToGo.getPersistence(), false );
			}*/
		}
	
	public static void redirectTo( String url, HttpServletRequest req, HttpServletResponse res )
		throws IOException
	{
		req.getSession().setAttribute( "errore", req.getAttribute( "errore" ) );
		req.getSession().setAttribute( "messaggio", req.getAttribute( "messaggio" ) );
		
		res.sendRedirect( res.encodeRedirectURL( url ) );
	}
	
	@SuppressWarnings("unchecked")
	public boolean inserisciUtenteContext( SuperUtente su, HttpSession sessione, String ambienteSirv ) throws Exception
	{
		if(utenti==null)
		{
			utenti = new HashMap<String, HttpSession>();
		}
		else if(utenti.get(su.getUsername())!=null)
		{
			return false;
		}

		session.setAttribute("ambienteSirv", ambienteSirv);
		utenti.put(su.getUsername(), sessione);
		context.setAttribute("utenti", utenti);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean esisteUtenteContext( String username ) throws Exception
	{
		if(utenti!=null && utenti.containsKey(username))
			return true;
		else 
			return false;
	}
	
	public HttpSession getSessioneUtenteContext( String username ) throws Exception
	{
		if(utenti==null)
			return null;
		else 
			return utenti.get(username);
	}
	
	@SuppressWarnings("unchecked")
	public void eliminaUtenteContext( String username ) throws Exception
	{
		try
		{
			HashMap<String, SuperUtente> utenti = (HashMap<String, SuperUtente>)context.getAttribute("utenti");
			if(utenti!=null)
			{
				utenti.remove( username );
				context.setAttribute("utenti", utenti);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public static void mettiInCoda(String operazione,Animale animale,String dataSmarrimento,String luogoSmarrimento,String noteSmarrimento,
			Boolean sanzione,String importoSanzione,BUtente utente,String dataRitrovamento,String luogoRitrovamento,
			String noteRitrovamento,String dataPassaporto,String numeroPassaporto,String notePassaporto,
			Integer decessoCode,String dataMorte,Boolean dataDecessoPresunta, Integer errorCode,
			String errorDescription,Persistence persistence, AttivitaBdr attivitaBdr) throws Exception
			{
				CodaServiziBdr s = new CodaServiziBdr();
				s.setAnimale(animale);
				s.setDataDecessoPresunta(dataDecessoPresunta);
				s.setDataMorte(dataMorte);
				s.setDataPassaporto(dataPassaporto);
				s.setDataRitrovamento(dataRitrovamento);
				s.setDataSmarrimento(dataSmarrimento);
				s.setDecessoCode((decessoCode==null)?(0):(decessoCode));
				s.setEntered(new Date());
				s.setEnteredBy(utente);
				s.setImportoSanzione(importoSanzione);
				s.setLuogoRitrovamento(luogoRitrovamento);
				s.setLuogoSmarrimento(luogoSmarrimento);
				s.setModified(new Date());
				s.setModifiedBy(utente);
				s.setNotePassaporto(notePassaporto);
				s.setNoteRitrovamento(noteRitrovamento);
				s.setNoteSmarrimento(noteSmarrimento);
				s.setNumeroPassaporto(numeroPassaporto);
				s.setOperazione(operazione);
				s.setSanzione(sanzione);
				s.setUtente(utente);
				s.setAttivitaBdr(attivitaBdr);
				persistence.insert(s);
				
				//Aggiorno anche il database sincronizzato, per mantenere aggiornato il database nel frattempo 
				//che la registrazione vada a buon fine
				/*if(animale.getLookupSpecie().getId()==Specie.CANE)
				{
					Cane cane = ((ArrayList<Cane>)persistence.getNamedQuery("GetCaneByMc").setString( "mc", animale.getIdentificativo() ).list()).get(0);
					if(operazione.equals("smarrimento"))
					{
						if(importoSanzione!=null && !importoSanzione.equals(""))
						{
							importoSanzione = importoSanzione.replaceAll("\\.", "");
							importoSanzione = importoSanzione.replaceAll(",", ".");
							cane.setImportoSanzioneSmarrimento(Float.parseFloat(importoSanzione) );
						}
						cane.setStatoAttuale("Smarrimento");
					}
					
					if(operazione.equals("ritrovamento"))
					{
						cane.setNoteRitrovamento(noteRitrovamento);
						cane.setStatoAttuale("Registrato");
					}
					
					if(operazione.equals("ritrovamentoSmarrNonDenunciato"))
					{
						cane.setNoteRitrovamento(noteRitrovamento);
						cane.setStatoAttuale("Registrato");
					}
					
					if(operazione.equals("passaporto"))
					{
						cane.setNumeroPassaporto(numeroPassaporto);
						cane.setPassaporto(true);
					}
					
					if(operazione.equals("decesso"))
					{
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						cane.setDataDecesso(sdf.parse(dataMorte));
						cane.setDecessoValue(((LookupCMI)persistence.find(LookupCMI.class, decessoCode)).getDescription());
						cane.setIdTipoDecesso(decessoCode);
						cane.setDataDecessoPresunta(dataDecessoPresunta);
						cane.setStatoAttuale("Decesso");
					}
					persistence.update(cane);
				}
				else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				{
					Gatto gatto = ((ArrayList<Gatto>)persistence.getNamedQuery("GetGattoByMc").setString( "mc", animale.getIdentificativo() ).list()).get(0);
					if(operazione.equals("passaporto"))
					{
						gatto.setNumeroPassaporto(numeroPassaporto);
						gatto.setPassaporto(true);
					}
					if(operazione.equals("smarrimento"))
					{
						gatto.setStatoAttuale("Smarrimento");
					}
					
					if(operazione.equals("ritrovamento"))
					{
						gatto.setNoteRitrovamento(noteRitrovamento);
						gatto.setStatoAttuale("Registrato");
					}
					
					if(operazione.equals("ritrovamentoSmarrNonDenunciato"))
					{
						gatto.setNoteRitrovamento(noteRitrovamento);
						gatto.setStatoAttuale("Registrato");
					}
					
					if(operazione.equals("decesso"))
					{
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						gatto.setDataDecesso(sdf.parse(dataMorte));
						gatto.setDecessoValue(((LookupCMI)persistence.find(LookupCMI.class, decessoCode)).getDescription());
						gatto.setIdTipoDecesso(decessoCode);
						gatto.setIsDataDecessoPresunta(dataDecessoPresunta);
						gatto.setStatoAttuale("Decesso");
					}
					persistence.update(gatto);
				}*/
				
				
			}
	
	public static void mettiInCodaAnagraficaAnimale(String operazione,Animale animale,BUtente utente,Integer errorCode,
			String errorDescription,Persistence persistence, int mantello, String sesso, int razza, LookupTaglie taglia) throws Exception
			{
				CodaServiziBdr s = new CodaServiziBdr();
				s.setRazza((LookupRazze)persistence.find(LookupRazze.class, razza));
				s.setMantello((LookupMantelli)persistence.find(LookupMantelli.class, mantello));
				s.setTaglia(taglia);
				s.setSesso(sesso);
				s.setAnimale(animale);
				s.setEntered(new Date());
				s.setEnteredBy(utente);
				s.setModified(new Date());
				s.setModifiedBy(utente);
				s.setOperazione(operazione);
				s.setUtente(utente);
				persistence.insert(s);
			}
	
	public void setSegnalibroDocumentazione( String segnalibroDocumentazione )
	{		
		session.setAttribute("segnalibroDocumentazione", segnalibroDocumentazione);		
	}
	
	public String getSegnalibroDocumentazione()
	{		
		return (String)session.getAttribute( "segnalibroDocumentazione" );		
	}
	
	public String getDescrizione()
	{
		return this.getClass().getName();
	}
	
	public static void aggiornaConnessioneApertaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
			numConnessioniDb = numConnessioniDb+1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			req.getSession().setAttribute("timeConnOpen",     new Date());
		}
	}
	
	public static void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
			numConnessioniDb = numConnessioniDb-1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			if(numConnessioniDb==0)
				req.getSession().setAttribute("timeConnOpen",     null);
		}
	}
	
	public static void aggiornaConnessioneAperta(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
			numConnessioniDb = numConnessioniDb+1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			req.getSession().setAttribute("timeConnOpen",     new Date());
		}
	}
	
	public static void aggiornaConnessioneChiusa(HttpServletRequest req)
	{
		if(req!=null)
		{
			int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
			numConnessioniDb = numConnessioniDb-1;
			req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
			if(numConnessioniDb==0)
				req.getSession().setAttribute("timeConnOpen",     null);
		}
	}
	
	public static void writeStorico(ArrayList<UserOperation> op, Boolean automatico){
		Context ctx;
		Connection db = null;
		try {
			if (op!=null && op.size()>0){
				ctx = new InitialContext();
				javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/storico");
				db = ds.getConnection();
				if (db!=null){
					for (int i=0; i<op.size();i++){
						op.get(i).insert(db, automatico);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[VAM] Errore nella scrittura sul db storico, recuperando il datasource storico. I dati verranno inseriti sul db locale.");
			Connection conn = null;
			try {
				ctx = new InitialContext();
				javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
				conn = ds.getConnection();
				if (conn!=null){
					for (int i=0; i<op.size();i++){
						op.get(i).insert(conn, automatico);
					}
				}
			} catch (Exception e1) {
				System.out.println("[VAM] Errore nella scrittura sul db locale, recuperando il datasource vamM");
				e1.printStackTrace();
			} finally {
				if (conn!=null){
					try {
						conn.close();
					} catch (SQLException e1){
						System.out.println("[VAM] Errore nella chiusura della connessione sul db locale, datasource vamM");
						e1.printStackTrace();
					}
				}
			}
		} finally {
			if (db!=null)
				try {
					db.close();
				} catch (SQLException e) {
					System.out.println("[VAM] Errore nella chiusura della connessione sul db storico, datasource storico");
					e.printStackTrace();
				}
		}
	}
	
	public static void writeLoginFault(String username, String ip, String error){
		if (Application.get("abilitaStoricoOperazioniUtente")!=null &&
    			Application.get("abilitaStoricoOperazioniUtente").equalsIgnoreCase("si") ){
			Context ctx;
			Connection conn = null;
			try {
				ctx = new InitialContext();
				javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/storico");
				conn = ds.getConnection();
				if (conn!=null){
					PreparedStatement pst = conn.prepareStatement("insert into login_fallite (endpoint,ip,username,data,error_message) values ('VAM',?,?,now(),?);");
					pst.setString(1, ip);
					pst.setString(2, username);
					pst.setString(3, error);
					pst.executeUpdate();
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
	
	
/*	public static void writeStorico(int idUtente, HashMap<Timestamp,String[]> op, String ip, ServletContext context, Boolean b){
		String insertStoricoOperazioniUtenti = "INSERT INTO vam_storico_operazioni_utenti(" +
		"user_id, username, ip, data, path, parametri, automatico) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		
		Persistence persistence = PersistenceFactory.getPersistence();
		String username = "";
		SuperUtente u = null;
		try {
			u= (SuperUtente) persistence.find(SuperUtente.class, idUtente); 
			username = u.getUsername();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PersistenceFactory.closePersistence( persistence, true );
		
		Context ctx;
		Connection conn = null;
		try {
			ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/storico");
			conn = ds.getConnection();
			Iterator it = op.entrySet().iterator();
			
			while (it.hasNext()){
				Map.Entry o = (Map.Entry)it.next();
				String[] data = (String[]) o.getValue();
				PreparedStatement ps = conn.prepareStatement(insertStoricoOperazioniUtenti);
				ps.setInt(1, u.getId());
				ps.setString(2, u.getUsername());
				ps.setString(3, ip);
				ps.setTimestamp(4, (Timestamp)o.getKey());
//				ps.setString(5, o.getValue().toString());
				ps.setString(5, data[0].toString());
				ps.setString(6, data[1].toString());
				ps.setBoolean(7, b);
				ps.executeUpdate();
				ps.close();
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
	} */
}
