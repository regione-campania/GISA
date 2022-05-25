/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Download extends HttpServlet {
        protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                doPost(request,response);
        }

    protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       //  String id = request.getParameter("id");

         //String fileName = "C:\\workspace_VAM\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\WebTest\\En_dir\\test.html.pdf";
         String fileName = request.getParameter("nome");
         String titolo = request.getParameter("titolo");
         String fileType = "";
        
         if (new File(fileName).exists()){
         // Find this file id in database to get file name, and file type

         // You must tell the browser the file type you are going to send
         // for example application/pdf, text/plain, text/html, image/jpg
         response.setContentType(fileType);

         // Make sure to show the download dialog
         response.setHeader("Content-disposition","attachment; filename="+titolo);

         // Assume file name is retrieved from database
         // For example D:\\file\\test.pdf

         File my_file = new File(fileName);

         // This should send the file to browser
         OutputStream out = response.getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
    }
         else{
                 PrintWriter out = response.getWriter();
                 out.println("File non trovato!");
                 out.println(fileName);
                 out.println("E' possibile che sia stato spostato o cancellato.");
          }
}}
