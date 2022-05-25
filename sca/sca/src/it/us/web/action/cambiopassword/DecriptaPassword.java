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
import it.us.web.dao.UtenteDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;

public class DecriptaPassword extends GenericAction
{

	private String pwdDaDecriptare ="";
	private String pwdDecriptata = "";
	
	
	public DecriptaPassword() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
			
		pwdDaDecriptare = stringaFromRequest( "pwdDaDecriptare" );
		req.setAttribute("pwdDaDecriptare", pwdDaDecriptare);
		
		if (pwdDaDecriptare !=null){
			
			try {
			pwdDecriptata = EseguiCambioPassword.decrypt(pwdDaDecriptare, EseguiCambioPassword.keyValue);
			req.setAttribute("pwdDecriptata", pwdDecriptata);
			}
			catch (Exception e){
				req.setAttribute("pwdDecriptata", "----ERROR----");
			}
		}
		gotoPage("/jsp/cambiopassword/decriptaPassword.jsp" );
		
	}



	
}
	
