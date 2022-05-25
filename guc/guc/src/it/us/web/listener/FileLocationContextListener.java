/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
	package it.us.web.listener;
	import java.io.File;
	 
	import javax.servlet.ServletContext;
	import javax.servlet.ServletContextEvent;
	import javax.servlet.ServletContextListener;

	public class FileLocationContextListener implements ServletContextListener {
	 
	    public void contextInitialized(ServletContextEvent servletContextEvent) {
	        String rootPath = System.getProperty("catalina.home");
	        ServletContext ctx = servletContextEvent.getServletContext();
	        String relativePath = ctx.getInitParameter("tempfile.dir");
	        File file = new File(rootPath + File.separator + relativePath);
	        if(!file.exists()) file.mkdirs();
	        System.out.println("File Directory created to be used for storing files");
	        ctx.setAttribute("FILES_DIR_FILE", file);
	        ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
	    }
	 
	    public void contextDestroyed(ServletContextEvent servletContextEvent) {
	        //do cleanup if needed
	    }
	     
	}