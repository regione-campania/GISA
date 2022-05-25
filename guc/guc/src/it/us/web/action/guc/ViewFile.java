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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;

public class ViewFile extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		
		
		
		File fileToview = new File (context.getAttribute("FILES_DIR")+File.separator + req.getParameter("fileName").replace("/", File.separator));
		
		res.setContentType("application/excel");
     	res.setHeader("Content-Disposition", "attachment; filename=\"" + fileToview.getName() + "\"");
     	InputStream fis = new FileInputStream(fileToview);
     	 ServletOutputStream os       = res.getOutputStream();
         byte[] bufferData = new byte[1024];
         int read=0;
         while((read = fis.read(bufferData))!= -1){
             os.write(bufferData, 0, read);
         }
         os.flush();
         os.close();
         fis.close();
		
		
	}
	
	

}
