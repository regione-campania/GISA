/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import it.us.web.action.GenericAction;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Immagine;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.json.JSONArray;
import it.us.web.util.json.JSONException;
import it.us.web.util.json.JSONObject;
import it.us.web.util.properties.Application;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class UploadImg extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{

	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
	    String	destinationDirPath	= Application.get( "UPLOAD_ROOT_FOLDER" );
	    
		ServletFileUpload uploadHandler = new ServletFileUpload( new DiskFileItemFactory() );
		
    	PrintWriter writer = res.getWriter();
        res.setContentType("text/plain");
        res.setHeader( "Content-Disposition","inline; filename=\"response.json\";" );
        JSONArray jsonArray = new JSONArray();
        
		boolean isMultipart = ServletFileUpload.isMultipartContent( req );
        
		try
        {
	        if( isMultipart )
	        {
	        
	            List<FileItem> items = uploadHandler.parseRequest( req );
	            Accettazione acc = (Accettazione) persistence.find( Accettazione.class, Integer.parseInt( getMultipartParameter( items, "idAccettazione") ));

	            for(FileItem item : items)
	            {
	                if(!item.isFormField())
	                {
	                	Immagine imm = new Immagine();
	                	imm.setAccettazione( acc );
	                	imm.setRefClass( getMultipartParameter(items, "classRef") );
	                	if(!getMultipartParameter(items, "idClass").equals("-1")){
	                		imm.setIdRefClass( Integer.parseInt( getMultipartParameter(items, "idClass") ) );
	                	}
	                	else {
	                		imm.setIdRefClass( -1 );
	                	}
	                	imm.setDisplayName( item.getName() );
	                	imm.setOriginalName( item.getName() );
	                	imm.setSize( item.getSize() );
	                	imm.setContentType( item.getContentType() );
	                	imm.setEntered( new Date() );
	                	imm.setEnteredBy( utente );
	                	imm.setModified( imm.getEntered() );
	                	imm.setModifiedBy( utente );
	                	
	                	persistence.insert( imm );
	                	
	                	String folderTree = ( File.separator + "cl_" + ((utente.getClinica()==null)?("nocl"):(utente.getClinica().getId())) + File.separator + "acc_" + acc.getId() );
	            	    File destinationDir = new File( destinationDirPath + folderTree );
	            	    if( !destinationDir.exists() )
	            	    {
	            	    	destinationDir.mkdirs();
	            	    }
	
	                    File file = new File( destinationDir, imm.getId() + ".jpg" );
	                    item.write(file);
	                    item.getOutputStream().flush();
	                    item.getOutputStream().close();
	                    
	                    
	                    imm.setPathName( new String( folderTree.getBytes() ) + File.separator + imm.getId() + ".jpg"  );
	                    persistence.update( imm );

	                    jsonArray.put( toJson( imm ) );
	                    
	                    break;  //We get one file sent to us per request
	                }
	            }
	        }
	        else
	        {
	        	int idAccettazione	= interoFromRequest( "idAccettazione" );
	        	int idClass			= interoFromRequest( "idClass" );
	        	String classRef		= stringaFromRequest( "classRef" );
	        	
	        	List<Immagine> imgs = persistence.createCriteria( Immagine.class )
	        		.add( Restrictions.eq( "accettazione", persistence.find(Accettazione.class, idAccettazione) ) )
	        		.add( Restrictions.eq("refClass", classRef) )
	        		.add( Restrictions.eq("idRefClass", idClass) )
	        		.addOrder( Order.asc( "id" ) )
	        		.list();
	        	
	        	for( Immagine imm: imgs )
	        	{
	        		jsonArray.put( toJson( imm ) );
	        	}
	        }

	        writer.println(jsonArray);
	        System.out.println(jsonArray.toString());
        }
        catch (FileUploadException e)
        {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
            System.gc();
        }
	}

	private JSONObject toJson(Immagine imm) throws JSONException
	{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", imm.getDisplayName());
		jsonObj.put("type", imm.getContentType());
		jsonObj.put("size", imm.getSize());
		jsonObj.put("url", "vam.accettazione.DwnImg.us?id=" + imm.getId() + "&" + imm.getOriginalName());
		jsonObj.put("delete_url", "vam.accettazione.DelImg.us?id=" + imm.getId());
		jsonObj.put("delete_type", "DELETE");
		jsonObj.put("thumbnail_url", "vam.accettazione.DwnImg.us?thumb=on&inline=on&id=" + imm.getId() );//+ "&x=" + System.currentTimeMillis() );
		return jsonObj;
	}
	
	private String getMultipartParameter(List<FileItem> items, String parameter) throws FileUploadException
	{
		String ret = null;
		
        for( FileItem item : items )
        {
        	if( item.isFormField() )
        	{
        		if( parameter.equals( item.getFieldName() ) )
        		{
        			ret = item.getString();
        			break;
        		}
        	}
        }
		
		return ret;
	}
}
