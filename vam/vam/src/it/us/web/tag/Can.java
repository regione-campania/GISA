/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.tag;

import it.us.web.bean.BUtente;
import it.us.web.permessi.Permessi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

public class Can extends GenericTag
{
	/*
	 * questo TAG può contenere 5 campi
	 * nel caso che su una GUI non si abbia neanche il diritto di lettura
	 * allora sarà stampato il valore alt
	 * nel caso si abbia il diritto di lettura sarà stampato il valore value
	 * o in sua assenza alt
	 * nel caso si abbia il diritto di lettura e scrittura sarà eseguito
	 * ciò che è presente all'interno dei TAG
	 * 
	 */  
	
	private static final long serialVersionUID = 1L;
	private static final String templatePre = "<div class=\"etichetta-nera\"><strong>";
	private static final String templatePost = "</strong></div>";
	private String alt;
	private String f;//funzione
	private String sf;//sub-funzione
	private String og;//oggetto
	private String value;//valore per sola lettura
	private String r;//dice se eseguire il body se in sola lettura (r="r")
	
	
	public String getValue() 
	{
		return value;
	}


	public void setValue(String value) 
	{
		this.value = value;
	}


	public int doStartTag()
	{
		
		//NUOVA GESTIONE CONTROLLO PERMESSI
		HashMap<Integer, HashMap<String, String>> funzioniConcesse = (HashMap<Integer, HashMap<String, String>> )pageContext.getServletContext().getAttribute("funzioniConcesse");
		HttpSession ses = pageContext.getSession();
		BUtente bu=(BUtente)ses.getAttribute("utente");
		
		try
		{
			if(funzioniConcesse!=null && funzioniConcesse.get(bu.getSuperutente().getId())!=null &&  funzioniConcesse.get(bu.getSuperutente().getId()).get(f.toUpperCase()+"->"+sf.toUpperCase()+"->"+og.toUpperCase())!=null)
				return EVAL_BODY_INCLUDE;
			else
				return SKIP_BODY;
		}
		catch(Exception e)
		{
			System.out.println("rrr");
			return 0;
		}
	
		
		//VECCHIA GESTIONE CONTROLLO PERMESSI
		/*try
		{
			HttpSession ses = pageContext.getSession();
			
			BUtente bu=(BUtente)ses.getAttribute("utente");
			//System.out.println("Utente loggato: " + bu.getUsername());
			//System.out.println("Permesso da controllare: " + f.toUpperCase() + ", " + ", " + sf.toUpperCase() + ", " + og.toUpperCase());
			int permessi=Permessi.can(bu,f.toUpperCase(),sf.toUpperCase(),og.toUpperCase());
			//Log.debug( permessi + "" );
			
			if(permessi==0 && alt!=null)
			{
				JspWriter o = pageContext.getOut();
				o.print(  templatePre + toHtml( alt  ) + templatePost  );
				return SKIP_BODY;
			}
			if(permessi==0 && alt==null)
			{
				//JspWriter o = pageContext.getOut();
				//o.print(  templatePre + toHtml( ""  ) + templatePost  );
				return SKIP_BODY;
			}
			if(permessi==1)
			{
				if( r != null && r.equalsIgnoreCase( "r" ) )
				{
					return EVAL_BODY_INCLUDE;
				}
				else if(value!=null)
				{
					JspWriter o = pageContext.getOut();
					o.print(  templatePre + toHtml( value  ) + templatePost  );
					return SKIP_BODY;
				}
				else{
					if(alt!=null)
					{
						JspWriter o = pageContext.getOut();
						o.print(  templatePre + toHtml( alt  ) + templatePost  );
						return SKIP_BODY;
					}
					else
					{
						JspWriter o = pageContext.getOut();
						o.print(  templatePre + toHtml( ""  ) + templatePost  );
						return SKIP_BODY;
					}
				}
			}
			if(permessi==2)
			{
				return EVAL_BODY_INCLUDE;
			}
			
				
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return SKIP_BODY;
		}
		return SKIP_BODY;*/
	}
	
		
	public int doEndTag()
	{
		return EVAL_BODY_INCLUDE;
	}


	public String getAlt() {
		return alt;
	}


	public void setAlt(String alt) {
		this.alt = alt;
	}


	public String getF() {
		return f;
	}


	public void setF(String f) {
		this.f = f;
	}


	public String getOg() {
		return og;
	}


	public void setOg(String og) {
		this.og = og;
	}


	public String getSf() {
		return sf;
	}


	public void setSf(String sf) {
		this.sf = sf;
	}


	public String getR() {
		return r;
	}


	public void setR(String r) {
		this.r = r;
	}
}