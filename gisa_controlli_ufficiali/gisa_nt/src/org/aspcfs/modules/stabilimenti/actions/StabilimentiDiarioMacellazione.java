/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stabilimenti.actions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.stabilimenti.base.DiarioMacellazione;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.utils.web.LookupList;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.darkhorseventures.framework.actions.ActionContext;

public class StabilimentiDiarioMacellazione extends CFSModule
{
	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandList( context );
	}
	
	public String executeCommandElimina(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-diario-delete"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		String id = context.getParameter( "id" );
		
		try
		{
			db = getConnection( context );
			
			Timestamp now = new Timestamp( System.currentTimeMillis() );
			
			DiarioMacellazione dm = DiarioMacellazione.load( Integer.parseInt( id ), db );
			dm.setModified_by( getUserId(context) );
			dm.setModified( now );
			dm.setTrashed_date( now );
			dm.setEnabled( false );;
			
			dm.update( db );
			
			context.getRequest().setAttribute( "messaggio", "Elemento Eliminato dal Diario" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			context.getRequest().setAttribute("messaggio", e.getMessage());
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return executeCommandList(context);
	}
	
	public String executeCommandAdd(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-diario-add"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		String data				= context.getParameter( "data" );
		String id_specie		= context.getParameter( "id_specie" );
		String id_stabilimento	= context.getParameter( "orgId" );
		
		try
		{
			db = getConnection( context );
			
			Timestamp now = new Timestamp( System.currentTimeMillis() );
			
			DiarioMacellazione dm = new DiarioMacellazione();
			dm.setData( new Date( (new SimpleDateFormat( "dd/MM/yyyy" )).parse( data ).getTime() ) );
			dm.setId_stabilimento( Integer.parseInt( id_stabilimento ) );
			dm.setId_specie( Integer.parseInt( id_specie ) );
			dm.setEntered_by( getUserId(context) );
			dm.setModified_by( getUserId(context) );
			dm.setEntered( now );
			dm.setModified( now );
			dm.setEnabled( true );
			
			dm.store( db );
			
			context.getRequest().setAttribute( "messaggio", "Elemento Aggiunto al Diario" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			context.getRequest().setAttribute("messaggio", e.getMessage());
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return executeCommandList(context);
	}

	public String executeCommandList(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-diario-view"))
		{
			return ("PermissionError");
		}
		
		Connection	db				= null;
		String		id_stabilimento	= context.getParameter( "orgId" );
		boolean		canDelete		= false;
		if (hasPermission(context, "stabilimenti-stabilimenti-diario-delete"))
		{
			canDelete = true;
		}
		
		try
		{
			db = getConnection( context );
			ArrayList<DiarioMacellazione> list = DiarioMacellazione.loadByStabilimento( id_stabilimento, db );
			context.getRequest().setAttribute( "Diario", list );

			final LookupList specie = new LookupList( db, "lookup_specie_allevata" );
			specie.addItem( -1, "-- Selezionare --" );
			context.getRequest().setAttribute( "Specie", specie );
			
			TableFacade tf = TableFacadeFactory.createTableFacade( (canDelete) ? ("3") : ("2"), context.getRequest() );
			tf.setItems( list );
			if( canDelete )
			{
				tf.setColumnProperties( "id", "data", "id_specie" );
			}
			else
			{
				tf.setColumnProperties( "data", "id_specie" );
			}
			
			tf.setStateAttr("restore");
			tf.getTable().getRow().getColumn( "id_specie" ).setTitle( "Specie" );
			tf.getTable().getRow().getColumn( "id" ).setTitle( " " );

			Organization org = new Organization( db, Integer.parseInt( id_stabilimento ) );
			context.getRequest().setAttribute( "OrgDetails", org );

			HtmlColumn cg = null;

			cg = (HtmlColumn) tf.getTable().getRow().getColumn("data");
			cg.setFilterable( false );
			cg.getCellRenderer().setCellEditor( new DateCellEditor("dd/MM/yyyy") );
			
			cg = (HtmlColumn) tf.getTable().getRow().getColumn("id_specie");
			cg.getCellRenderer().setCellEditor( 
				new CellEditor()
	    		{	
	    			public Object getValue(Object item, String property, int rowCount)
	    			{
	    				Integer value = (Integer)new BasicCellEditor().getValue(item, property, rowCount);
	    				return specie.getSelectedValue( value.intValue() );
	    			}
	    		} 
			);
			cg.setFilterable( false );
			cg.setSortable( false );
			
			if( canDelete )
			{
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("id");
				cg.getCellRenderer().setCellEditor( 
					new CellEditor()
		    		{	
		    			public Object getValue(Object item, String property, int rowCount)
		    			{
		    				Integer value = (Integer)new BasicCellEditor().getValue(item, property, rowCount);
		    				Integer id_st = (Integer)new BasicCellEditor().getValue(item, "id_stabilimento", rowCount);
		    				return "<a href=\"StabDiarioMacellazione.do?command=Elimina&id=" + value + "&orgId=" + id_st + "\" onclick=\"javascript:return confirm('Eliminare l\\'elemento selezionato?');\">elimina</a>";
		    			}
		    		} 
				);
				cg.setFilterable( false );
				cg.setSortable( false );
			}
			
			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "ListOK";
	}
}
