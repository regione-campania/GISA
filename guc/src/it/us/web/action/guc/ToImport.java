/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.bean.guc.Asl;
import it.us.web.dao.AslDAO;
import it.us.web.exceptions.AuthorizationException;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

public class ToImport extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		File dir = new File (""+context.getAttribute("FILES_DIR"));
		HashMap<String, File[]> listaUpload = new HashMap<String, File[]>();
		File [] listaFile = dir.listFiles();
		if (listaFile != null)
		for (int i = 0 ; i < listaFile.length;i++)
		{
			File ff =listaFile[i];
			
			
			listaUpload.put(ff.getName(), ff.listFiles());
		}
		req.setAttribute("DirList", listaUpload);

		gotoPage( "/jsp/guc/import.jsp" );
		
	}
	
	

}
