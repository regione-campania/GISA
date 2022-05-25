/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.cambiopassword;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.xml.crypto.Data;

import org.apache.tomcat.util.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import it.us.web.action.GenericAction;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.gucinterazioni.GucInterazioni;
import it.us.web.bean.mail.PecMailSender;
import it.us.web.bean.mail.PecMailSenderThread;
import it.us.web.dao.UtenteDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;

public class EseguiCambioPassword extends GenericAction
{
	private String username ="";
	private String pwdOld ="";
	private String pwd1 = "";
	private String pwd2 = "";
	
	static byte[] keyValue = new byte[] { 'U', 'S', '9', '5', '6', '0', '0', '3', '1', '.', 'd','o', 'd', 'i', 'c', 'i' };
	
	public EseguiCambioPassword() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
			
		 username = stringaFromRequest( "username" );
		 pwdOld = stringaFromRequest( "pwdOld" );
		 pwd1= stringaFromRequest( "pwd1" );
		 pwd2 =  stringaFromRequest( "pwd2" );
		
		GucInterazioni gi = new GucInterazioni();
		
		String result = controllaCoerenzaDati(gi);
		
		if (result.equals("OK")){
			String msg = eseguiProceduraCambioPassword(gi);
			
			utenteGuc = UtenteDAO.authenticateUnifiedAccess(username, pwd1, db);
			invioMail(utenteGuc.getEmail());
			
			req.setAttribute("ConfermaCambioPassword", msg);
			gotoPage("/jsp/cambiopassword/cambioPassword.jsp" );
		}
		else {
			req.setAttribute("ErroreCambioPassword", result);
			gotoPage("/jsp/cambiopassword/cambioPassword.jsp" );
		}
	
	}

private boolean controllaContenutoPassword(){
	
	String CONTIENE_NUMERO = "(?=.*\\d)";
	String CONTIENE_MINUSCOLA = "(?=.*[a-z])";
	String CONTIENE_MAIUSCOLA =   "(?=.*[A-Z])";
	String DIMENSIONE =  "{10,15}";
	
	String PASSWORD_PATTERN = "("+CONTIENE_MINUSCOLA+CONTIENE_NUMERO+CONTIENE_MAIUSCOLA+"."+DIMENSIONE+")";
	 
	Pattern pattern;
	Matcher matcher;
	boolean esitoContenutoPassword = false;
	
	pattern = Pattern.compile(PASSWORD_PATTERN);
	
	matcher = pattern.matcher(pwd1);
	esitoContenutoPassword =  matcher.matches();
	
	
	return esitoContenutoPassword;
	
}

private String controllaCoerenzaDati(GucInterazioni gi) throws SQLException{
	String result = "OK";
	
	if (!gi.verificaEsistenzaUsername(username)){
		result ="ERRORE: Username non esistente o disabilitato.";
		return result;
	}
	if (!gi.verificaVecchiaPassword(username, pwdOld)){
		result ="ERRORE: La vecchia password non corrisponde.";
		return result;
	}
	if (gi.verificaCambioPasswordRecente(username)){
		result ="ERRORE: Richiesta di cambio password inviata recentemente. Riprovare tra qualche minuto.";
		return result;
	}
	if (!pwd1.equals(pwd2)){
		result ="ERRORE: La password di conferma non corrisponde alla nuova password";
		return result;
	}
	if (!controllaContenutoPassword()){
		result ="ERRORE: La nuova password non rispetta la policy.";
		return result;
	}
	
	return result;
	
}
	private String eseguiProceduraCambioPassword(GucInterazioni gi) throws Exception{
		String msg = "";
		EndPointConnectorList epcList = gi.getListaEndPointConnector().getByIdOperazione(Operazione.CAMBIOPASSWORD);
		
		for (int i = 0; i<epcList.size(); i++){
			EndPointConnector epc = (EndPointConnector) epcList.get(i);
			String sql = epc.getSql();
			String dataSource = epc.getEndPoint().getDataSourceSlave();
			
			try {
				Connection db = DbUtil.ottieniConnessioneJDBC(dataSource);
				PreparedStatement pst = db.prepareStatement(sql);
				
					pst.setString(1, username);
					pst.setString(2, pwd1);
					
					if (epc.getEndPoint().getId() == EndPoint.GUC){
						
						String pwdOldEncrypted = encryptPassword(pwdOld);
						String pwdNewEncrypted = encryptPassword(pwd1);
						
						pst.setString(3, pwdOldEncrypted);
						pst.setString(4, (String)req.getRemoteAddr());
						pst.setString(5, pwdNewEncrypted);
					}
					
					System.out.println("[SCA] Eseguo query su: "+epc.getEndPoint().getNome()+"/"+epc.getEndPoint().getDataSourceSlave()+": "+pst.toString());
					
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					msg+= "Aggiornamento password su "+epc.getEndPoint().getNome().toUpperCase()+": "+rs.getString(1)+";      ";
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				DbUtil.chiudiConnessioneJDBC(null, db);
	}
			
		}
		return msg;
		//Recupera Connessione GUC
		// Lancia DBI Update su guc
		
		//Recupera Connessione EndPoint [...]
		// Lancia DBI Update su EndPoint
		
		
	}
	
	private void invioMail(String mailUtente) throws SendFailedException, MessagingException{
		String mailUS = ApplicationProperties.getProperty("mail.cc");
		String invioMailUtente = ApplicationProperties.getProperty("mail.invio.utente");

		if (invioMailUtente!=null && invioMailUtente.equals("no"))
			mailUtente = mailUS;
		
		System.out.println("[SCA] Preparazione invio mail a "+mailUtente+" e "+mailUS);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dataModifica = dateFormat.format(date).toString();
			
		Runnable run = new PecMailSenderThread(username, req.getRemoteAddr(), dataModifica, mailUtente, mailUS);
		new Thread(run).start();
	
	}
	
	
private String encryptPassword(String password) throws Exception{
	// byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
	String encrypteToken = encrypt(password, keyValue);
	return encrypteToken;
 }



public static String encrypt(String plainText, byte[] encryptionKey) throws Exception {
	  Key key = generateKey(encryptionKey);
      Cipher c = Cipher.getInstance("AES");
      c.init(Cipher.ENCRYPT_MODE, key);
      byte[] encVal = c.doFinal(plainText.getBytes());
      String encryptedValue = new BASE64Encoder().encode(encVal);
      return encryptedValue;
  }
 
private static Key generateKey(byte[] keyValue) throws Exception {
    Key key = new SecretKeySpec(keyValue, "AES");
    return key;
}

public static String decrypt(String encryptedData, byte[] encryptionKey) throws Exception {
    Key key = generateKey(encryptionKey);
    Cipher c = Cipher.getInstance("AES");
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = c.doFinal(decordedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;
}









	
}
	
