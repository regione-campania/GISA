/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apicolture.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CfUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String regex =  "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
		String input = "RNLNNT58M42F625E";

		
	}
	
	public static String extractCodiceFiscale1(String input)
	{
		
		if (input.length()==16)
		{
		String regex =  "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d[a-zA-Z0-9][a-zA-Z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find())
			return matcher.group() ;
		}
		else
		{
			if (input.length()==11)
				return input ;
		}
		return "" ;
		
	}
	
	public static String extractCodiceFiscale2(String input)
	{
		
		if (input.length()==16)
		{
		String regex =  "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d[a-zA-Z0-9][a-zA-Z]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find())
			return matcher.group() ;
		}
		return "" ;
		
	}
	
	public static String extractCodiceFiscale(String input)
	{
		String risultato="";
		if(input.length()==11){
			risultato=ControllaPI(input);
		}else if(input.length()==16){
			risultato=ControllaCF(input);
		}else{
			risultato="ATTENZIONE! Inserire un codice fiscale o una partita iva corretta.";
		}
		return risultato;
		
		
		
				
	}
	
	
	static String ControllaPI(String pi) {
		int i, c, s;
		for (i = 0; i < 11; i++) {
			if (pi.charAt(i) < '0' || pi.charAt(i) > '9')
				 return "ATTENTIONE! La partita IVA contiene dei caratteri non ammessi.";
				}
		s = 0;
		for (i = 0; i <= 9; i += 2)
			s += pi.charAt(i) - '0';
		for (i = 1; i <= 9; i += 2) {
			c = 2 * (pi.charAt(i) - '0');
			if (c > 9)
				c = c - 9;
			s += c;
		}
		if ((10 - s % 10) % 10 != pi.charAt(10) - '0')
			return "ATTENZIONE! Partita iva inserita non corretta.";

		return "";
	}

	public static String ControllaCF(String cf) {
		int i, s, c;
		String cf2;
		int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
		cf2 = cf.toUpperCase();
		for (i = 0; i < 16; i++) {
			c = cf2.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z'))			
				 return "ATTENZIONE! Il codice fiscale contiene dei caratteri non validi.";		
		}
		s = 0;
		for (i = 1; i <= 13; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				s = s + c - '0';
			else
				s = s + c - 'A';
		}
		for (i = 0; i <= 14; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				c = c - '0' + 'A';
			s = s + setdisp[c - 'A'];
		}
		if (s % 26 + 'A' != cf2.charAt(15))
			return "ATTENZIONE! Codice fiscale inserito non corretto.";

		return "";
	}


}
