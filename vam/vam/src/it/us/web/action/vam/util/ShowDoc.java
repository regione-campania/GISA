/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.properties.Application;

public class ShowDoc extends GenericAction
{

	final static Logger logger = LoggerFactory.getLogger(ShowDoc.class);
	
	@Override
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		redirectTo( Application.get("DOCS_ROOT_FOLDER") + "#" + getSegnalibroDocumentazione() );
	}

}
