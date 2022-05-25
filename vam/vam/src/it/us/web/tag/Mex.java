/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.tag;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;

public class Mex extends GenericTag
{
	private static final long serialVersionUID = 1L;
	private static final String templatePre1 = "<div class=\"";
	private static final String templatePre2 = "\"><strong>";
	private static final String templatePost = "</strong></div>";
	private static final String defaultClass = "etichetta-nera";
	private String alt;
	private String size;
	private String classe;
	
	public String getClasse() {
		return classe;
	}


	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public int doStartTag()
	{
		try
		{
			ServletRequest req = pageContext.getRequest();
			String messaggio = (String)req.getAttribute( "messaggio" );
			String templatePre = templatePre1 + ( (classe == null) ? (defaultClass) : (classe) ) + templatePre2;
			
			if ( (messaggio != null) && !(messaggio.equalsIgnoreCase( "" )) ) 
			{
				if(size!=null){
					messaggio=messaggio.substring(0, Integer.parseInt(size))+"...";
				}
				JspWriter o = pageContext.getOut();
				o.print(  templatePre + toHtml( messaggio ) + templatePost  );
				return SKIP_BODY;
				
			}
			else
			{
				if(size!=null && alt!=null && alt.length()<= Integer.parseInt(size))
				{
					alt=alt.substring(0, Integer.parseInt(size))+"...";
					
				}
				if(alt==null)
				{
					JspWriter o = pageContext.getOut();
					o.print(  templatePre + toHtml( ""  ) + templatePost  );
					return SKIP_BODY;
				}
				else{
					JspWriter o = pageContext.getOut();
					o.print(  templatePre + toHtml( alt  ) + templatePost  );
					return SKIP_BODY;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return SKIP_BODY;
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


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}
}