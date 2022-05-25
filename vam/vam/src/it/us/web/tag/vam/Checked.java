/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.tag.vam;

import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.tag.GenericTag;

import javax.servlet.jsp.JspWriter;

public class Checked extends GenericTag
{
	private static final long serialVersionUID = 1L;
	private static final String checkedText = "checked=\"checked\"";
	
	private int idAccettazione;
	private int idLookupOperazione;
	private Persistence persistence;
	
	public int doStartTag()
	{
		try
		{
			persistence = PersistenceFactory.getPersistence();
			
//			ServletRequest req = pageContext.getRequest();
			JspWriter o = pageContext.getOut();
			
			
			if( idAccettazione > 0 && idLookupOperazione > 0 && persistence != null )
			{
				Accettazione acc = (Accettazione) persistence.find( Accettazione.class, idAccettazione );
				LookupOperazioniAccettazione op = (LookupOperazioniAccettazione) 
					persistence.find( LookupOperazioniAccettazione.class, idLookupOperazione );
			
				if( acc.getOperazioniRichieste().contains( op ) )
				{
					o.print(  checkedText  );
				}
			}
			
			return SKIP_BODY;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			PersistenceFactory.closePersistence( persistence, true );
		}
		
		return SKIP_BODY;
	}
	
		
	public int doEndTag()
	{
		return EVAL_BODY_INCLUDE;
	}


	public int getIdAccettazione() {
		return idAccettazione;
	}


	public void setIdAccettazione(int idAccettazione) {
		this.idAccettazione = idAccettazione;
	}


	public int getIdLookupOperazione() {
		return idLookupOperazione;
	}


	public void setIdLookupOperazione(int idLookupOperazione) {
		this.idLookupOperazione = idLookupOperazione;
	}


}