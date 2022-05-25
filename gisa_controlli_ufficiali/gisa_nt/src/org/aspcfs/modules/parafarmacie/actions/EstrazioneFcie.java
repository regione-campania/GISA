/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.parafarmacie.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;

import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public final class EstrazioneFcie extends CFSModule {

	private static final String squote = "<tr><td align=center>";
	private static final String equote = "</td></tr>";
	private static final String dquote = "</td><td align=center>";
	private static final String initialBold="<b>";
	private static final String endBold="</b>";
	
	
	private static final String blueFont = "<font color='blue'>";
	private static final String redFont = "<font color='red'>";
	private static final String endFont  = "</font>";
  
  public String executeCommandToElencoPerAnno(ActionContext context)
  {
	  if (!(hasPermission(context, "statistiche-view"))) {
		    return ("PermissionError");
		  }
	  
	     return "ToElencoOK";
  }
  
  
  
  public String executeCommandExcel(ActionContext context)
  {
	  if (!(hasPermission(context, "statistiche-view"))) {
		    return ("PermissionError");
		  }
	  
	     return "Excel";
  }
  
    
  private String resolveAsl (Connection conn, int siteId) throws SQLException {
	  
	   
	  PreparedStatement stat =null;
	  
	  stat= conn.prepareStatement("select description from lookup_site_id where code= ? " );
	  stat.setInt(1,siteId);
	  	  
	  ResultSet			rs		= stat.executeQuery();
	  	
	  String aslName="";
	  
	  if (rs.next()) {
		  aslName = rs.getString(1);
	  }
	  
	  
	  return aslName;
	 
	  
	  
  }
  
  public String executeCommandElenco(ActionContext context) throws SQLException
  {
	if (!(hasPermission(context, "parafarmacie-estrazione-view")))
	{
		return ("PermissionError");
	}

    Connection		db		= null;
    RowSetDynaClass	rsdc	= null;
    String			fileName=null;
    
    try
	{
		db = getConnection(context);
		
		int siteId=this.getUserSiteId(context);
	    
		String aslName="";
	    
		//Se siteId!=-1 allora e' una ASL
	    if (siteId!=-1)
	    	aslName=this.resolveAsl(db, siteId);
		
	    		
		PreparedStatement	stat = null;
		
				
		//Se e' richiesta una estrazione depositi
		stat= db.prepareStatement( "select * from farmacie_view ORDER BY asl" );
			
			
			fileName="Depositi_" +System.currentTimeMillis()+ ".xls";
	
		
		
		ResultSet			rs		= stat.executeQuery();
							rsdc	= new RowSetDynaClass(rs);
				
		HttpServletResponse res = context.getResponse();
	 	res.setContentType( "application/xls" );
	 	//res.setHeader( "Content-Disposition","attachment; filename=\"osa_" + anno + ".xls\";" ); 
	 	
	 	res.setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 

	 	ServletOutputStream sout = res.getOutputStream();
	 	sout.write( "<table border=1>".getBytes() );
	 		 		 	
	 	List<DynaBean> l = rsdc.getRows();
	 	
	 	//stampa i nomi delle colonne
	 	dynamicHeader(rs, sout);
	 	
	 	for( int i = 0; i < l.size(); i++ )
	 	{
	 		//Stampa ogni riga sul file
	 		this.dynamicRow(l.get(i), rs, sout);
	 	}
	 	sout.write( "</table>".getBytes() );
	 	sout.flush();
		
	}
	catch ( Exception e )
	{
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
    }
	finally
	{
		this.freeConnection(context, db);
		System.gc();
	}
	
	return "-none-";
  }

  /* Questo metodo serve a stampare, sulla prima riga del file 
   * XLS, i nomi degli attrbuti della tabella di riferimento.*/
  private void dynamicHeader (ResultSet rs , ServletOutputStream sout) throws IOException, SQLException {
	  
	  //Lo String Buffer
	  StringBuffer sb = new StringBuffer(); 
	  
	  //Serve ad ottenere i meta-dati del ResultSet
	  ResultSetMetaData rsmd=rs.getMetaData();
	  
	  //Il numero di attributi
	  int columnNumber=rsmd.getColumnCount();
	  
	  sb.append(squote);
		 
	  for (int i=1;i<=columnNumber;i++) {
		sb.append(initialBold);
		sb.append(blueFont);
		sb.append( rsmd.getColumnName(i) );
		sb.append(endFont);
		sb.append(endBold);
		if( i < columnNumber )
		{
			sb.append( dquote );
		}			
	 }
	  		
		sb.append( equote );
		sb.append( "\r\n" );
		
		sout.write( sb.toString().getBytes() );
	  
  }
  
  /* Questo metodo serve a stampare ogni tupla sul file*/
  private void dynamicRow (DynaBean dynaBean, ResultSet rs, ServletOutputStream sout) throws IOException, SQLException {
	  
	  //Lo String Buffer
	  StringBuffer sb = new StringBuffer(); 
	  
	  //Serve ad ottenere i meta-dati del ResultSet
	  ResultSetMetaData rsmd=rs.getMetaData();
	  
	  //Il numero di attributi
	  int columnNumber=rsmd.getColumnCount();
	  	  
	  sb.append(squote);
		 
	  for (int i=1;i<=columnNumber;i++) {
			
		sb.append( nullCheck( dynaBean, rsmd.getColumnName(i) ) );
		if( i < columnNumber )
		{
			sb.append( dquote );
		}
				
		}
	  		
		sb.append( equote );
		sb.append( "\r\n" );
		
		sout.write( sb.toString().getBytes() );
	  
	  
  }
  
  
private Object nullCheck( DynaBean dynaBean, String string )
{
	return (dynaBean.get( string ) == null) ? ("") : (dynaBean.get( string ));
}

	
}

