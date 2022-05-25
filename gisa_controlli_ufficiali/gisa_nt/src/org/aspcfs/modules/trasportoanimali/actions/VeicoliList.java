/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/



package org.aspcfs.modules.trasportoanimali.actions;

  

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.distributori.base.Distrubutore;

import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.modules.trasportoanimali.base.Organization;
import org.aspcfs.modules.trasportoanimali.base.Personale;
import org.aspcfs.modules.trasportoanimali.base.Sede;
import org.aspcfs.modules.trasportoanimali.base.Veicolo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactoryImpl;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;

import com.darkhorseventures.framework.actions.ActionContext;

public final class VeicoliList extends CFSModule
{
	String org_id="-1";
	Connection db=null;
	
	public String executeCommandDefault(ActionContext context)	{
		if(context.getRequest().getParameter("command")==null){
			return executeCommandDetails(context);
		}else
			return executeCommandAdd(context);
	}
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
			return ("PermissionError");
		}
		
		
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User
		
			String cosaAggiungo=context.getRequest().getParameter("tipoaggiunto");

			RowSetDynaClass	rsdc			= null;


			//db = getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
			ResultSet			rs		= stat.executeQuery();

			if(newOrg.getDunsType().equals("tipo1")){

				TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
				tf.setEditable(true);


				Collection<Veicolo> coll=	newOrg.getListaV(rs);


				if(cosaAggiungo!=null && cosaAggiungo.equals("veicolo")){
					coll.add(new Veicolo(tempid,"","",false,false));
				}					

				tf.setItems(coll );
				tf.getWorksheet().removeAllChanges();
				//tf.setColumnProperties("org_id","descrizione", "targa","checklist","accepted");
				tf.setColumnProperties("descrizione", "targa","accepted","elimina");


				tf.setStateAttr("restore");


				HtmlRow row = (HtmlRow) tf.getTable().getRow();
				row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




				//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );
				tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
				tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );

				//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
				tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
				tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );


				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{

					HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
					chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
					chkbox.setTitle("Check List");
					chkbox.setFilterable(false);
					chkbox.setSortable(false);


					//HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");
					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");



					cg.setFilterable( false );

					
					cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
					cg.setFilterable( true );

					cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
					cg.setEditable(false);
					cg.getCellRenderer().setCellEditor( 
			        		new CellEditor()
			        		{	
			        			public Object getValue(Object item, String property, int rowCount)
			        			{
			        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
			        				
			        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
			        				Comuni comune = new Comuni();
			        				
									
			        		
			        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
			        			}
			        		}
			        
			        	);
					cg.setFilterable(false);


				}
			
				
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );
				
				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );
													
				ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
				item2.setTooltip( "Salva" );
				tf.getToolbar().addToolbarItem( item2 );
				
				ToolbarItem item18 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
				item18.setTooltip( "Scorri pagina indietro" );
				tf.getToolbar().addToolbarItem( item18 );
				
				ToolbarItem item17 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
				item17.setTooltip( "Scorri pagina in avanti" );
				tf.getToolbar().addToolbarItem( item17 );
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );
				
				

			} else if(newOrg.getDunsType().equals("tipo2")){

					TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
					tf.setEditable(true);


					Collection<Veicolo> coll=	newOrg.getListaV(rs);


					if(cosaAggiungo!=null && cosaAggiungo.equals("veicolo")){
						coll.add(new Veicolo(tempid,"","",false,false));
					}



					tf.setItems(coll );
					tf.getWorksheet().removeAllChanges();


					//tf.setColumnProperties("org_id","descrizione", "targa","checklist","omologazione","accepted");
					tf.setColumnProperties("descrizione", "targa","lunghi_Viaggi","accepted","elimina");


					tf.setStateAttr("restore");


					HtmlRow row = (HtmlRow) tf.getTable().getRow();
					row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




					//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

					//tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
					tf.getTable().getRow().getColumn( "lunghi_Viaggi" ).setTitle( "Omologazione" );
					//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
					//tf.getTable().getRow().getColumn( "omologazione" ).setTitle( "Omologazione" );
					tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
					tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );



					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{

						HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
						chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
						chkbox.setTitle("Check List");
						chkbox.setFilterable(false);
						chkbox.setSortable(false);
						
						chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("lunghi_Viaggi");
						chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
						chkbox.setTitle("Omologazione");
						chkbox.setFilterable(false);
						chkbox.setSortable(false);
									

						/*HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");
							cg.setFilterable( false );
						*/


						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");

						
						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
						cg.setFilterable( true );
						
						cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
				        				
				        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
				        				Comuni comune = new Comuni();
				        				
										
				        		
				        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
				        			}
				        		}
				        
				        	);
						cg.setFilterable(false);


					}

					
					
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					tf.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					tf.getToolbar().addToolbarItem( item2 );
					
					ToolbarItem item19 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
					item19.setTooltip( "Scorri pagina indietro" );
					tf.getToolbar().addToolbarItem( item19 );
					
					ToolbarItem item20 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
					item20.setTooltip( "Scorri pagina in avanti" );
					tf.getToolbar().addToolbarItem( item20 );
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );

				} else if((newOrg.getDunsType().equals("tipo3"))||(newOrg.getDunsType().equals("tipo4"))){
		    	    			
										
					TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
					tf.setEditable(true);


					Collection<Veicolo> coll=	newOrg.getListaV(rs);


					if(cosaAggiungo!=null && cosaAggiungo.equals("veicolo")){
						coll.add(new Veicolo(tempid,"","",false,false));
					}



					tf.setItems(coll );
					tf.getWorksheet().removeAllChanges();


					//tf.setColumnProperties("org_id","descrizione", "targa","checklist","omologazione","accepted");
					tf.setColumnProperties("descrizione", "targa","elimina");


					tf.setStateAttr("restore");


					HtmlRow row = (HtmlRow) tf.getTable().getRow();
					row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




					//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

					//tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
					tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );

					


					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{

						

						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");

						

						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
						cg.setFilterable( true );
						cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
				        				
				        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
				        				Comuni comune = new Comuni();
				        				
										
				        		
				        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
				        			}
				        		}
				        
				        	);
						cg.setFilterable(false);

					}
					
					
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					tf.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					
					tf.getToolbar().addToolbarItem( item2 );
					
					
					ToolbarItem item21 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
					item21.setTooltip( "Scorri pagina indietro" );
					tf.getToolbar().addToolbarItem( item21 );
					
					ToolbarItem item22 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
					item22.setTooltip( "Scorri pagina in avanti" );
					tf.getToolbar().addToolbarItem( item22 );
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );
		      }

			RowSetDynaClass	rsdc2			= null;

			PreparedStatement	stat2	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid+" and elimina is null");
			ResultSet			rs2		= stat2.executeQuery();


			TableFacade tf2 = TableFacadeFactory.createTableFacade( "sedi", context.getRequest() );
			tf2.setEditable(true);

			Collection<Sede> coll2=	newOrg.getListaS(rs2);


			if(cosaAggiungo!=null && cosaAggiungo.equals("sede")){
				Sede s=new Sede("","","","","");
				s.setOrg_id(tempid);
				coll2.add(s);
			}
			tf2.setItems(coll2);
			tf2.getWorksheet().removeAllChanges();
			tf2.setColumnProperties( 
					"comune", "indirizzo", "provincia", "cap","elimina");
			tf2.setStateAttr("restore");


			HtmlRow row2 = (HtmlRow) tf2.getTable().getRow();
			row2.setUniqueProperty("id"); // l'id e' creato da noi quando importiamo la prima volta un file

			

			 PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numeroveicoli from organization_autoveicoli where org_id="+tempid+" and elimina is null" );
				ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
				int numeroVeicoli=0;
				if(rs_veicoli.next())
				numeroVeicoli=rs_veicoli.getInt(1);
				
				
				
			      PreparedStatement	stat_sedi	= db.prepareStatement( "select count(*) as numerosedi from organization_sediveicoli where org_id="+tempid+" and elimina is null" );
					ResultSet			rs_sedi	= stat_sedi.executeQuery();
					int numeroSedi=0;
					if(rs_sedi.next())
						numeroSedi=rs_sedi.getInt(1);
					
			
			 PreparedStatement	stat_persona	= db.prepareStatement( "select count(*) as numerpersone from organization_personale where org_id="+tempid+" and elimina is null" );
				ResultSet			rs_persona	= stat_persona.executeQuery();
				int numeropersone=0;
				if(rs_persona.next())
					numeropersone=rs_persona.getInt(1);
				
			
						
						numeropersone=(numeropersone/15)+1;
					
						numeroVeicoli=(numeroVeicoli/15)+1;
				numeroSedi=(numeroSedi/15)+1;
				context.getRequest().setAttribute("numeroPersonale", numeropersone);
				context.getRequest().setAttribute("numeroAutoveicoli", numeroVeicoli);
				context.getRequest().setAttribute("numeroSedi", numeroSedi);





			//tf2.getTable().getRow().getColumn( "id" ).setTitle( "ID" );

			tf2.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf2.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf2.getTable().getRow().getColumn( "provincia" ).setTitle( "Provincia" );
			tf2.getTable().getRow().getColumn( "cap" ).setTitle( "Cap" );
			tf2.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );
		
			//tf2.getTable().getRow().getColumn( "stato" ).setTitle( "Stato" );	

			Limit limit2 = tf2.getLimit();
			if(! limit2.isExported() )
			{



				HtmlColumn cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("comune");
				cg2.setEditable(false);
				cg2.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
		        				String comunedef=(String) (new HtmlCellEditor()).getValue(item, "comune", rowCount);
		        				String comunedefault=comunedef.replaceAll("'", "_");
		        				Comuni comune = new Comuni();
		        				String select = "<SELECT name = 'comune_"+iddef+"'>";
		        				String option="";
								try {
									ArrayList<String> comuni = comune.queryRecord2(db);
									
									for(String c:comuni){
										String com=c.replace("'", "_");
										option = "<OPTION value='"+com+"'";
										if(com.equals(comunedef))
											option+="selected='selected'>"+c+"</OPTION>";
										else
											option+=">"+c+"</OPTION>";
										
										select += option; 
									}
									String select2 = "</SELECT>";
									select += select2;
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        		
		        				return select;
		        			}
		        		}
		        
		        	);
				//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg2.setFilterable( true );
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("indirizzo");
				//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("id");
				//cg2.setEditable(false);
				//cg2.setFilterable(false);
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("provincia");

				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("cap");
				//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("stato");
				cg2.setFilterable( false );
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("elimina");
				cg2.setEditable(false);
				cg2.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
		        				
		        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		        				Comuni comune = new Comuni();
		        				
								
		        		
		        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=sede&id="+iddef+"'>Elimina </a>";
		        			}
		        		}
		        
		        	);
				cg2.setFilterable(false);


			}

			
			
			
			ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createFilterItem();
			item7.setTooltip( "Filtra" );
			tf2.getToolbar().addToolbarItem( item7 );
			
			ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createClearItem();
			item8.setTooltip( "Resetta Filtro" );
			tf2.getToolbar().addToolbarItem( item8 );
												
			ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createSaveWorksheetItem();
			item2.setTooltip( "Salva" );
			tf2.getToolbar().addToolbarItem( item2 );
			
			ToolbarItem item23 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createPrevPageItem();
			item23.setTooltip( "Scorri pagina indietro" );
			tf2.getToolbar().addToolbarItem( item23 );
			
			ToolbarItem item24 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createNextPageItem();
			item24.setTooltip( "Scorri pagina in avanti" );
			tf2.getToolbar().addToolbarItem( item24 );
			
			String tabella2 = tf2.render();
			context.getRequest().setAttribute( "tabella2", tabella2 );

			RowSetDynaClass	rsdc3			= null;

			PreparedStatement	stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid+"and elimina is null");
			ResultSet			rs3		= stat3.executeQuery();


			TableFacade tf3 = TableFacadeFactory.createTableFacade( "personale", context.getRequest() );
			tf3.setEditable(true);


			Collection<Personale> coll3=newOrg.getListaP(rs3);
			if(cosaAggiungo!=null && cosaAggiungo.equals("personale")){	
				Personale p=new Personale("","","","");
				p.setOrg_id(tempid);
				coll3.add(p);
			}
			tf3.setItems( coll3);
			tf3.getWorksheet().removeAllChanges();
			tf3.setColumnProperties( 
					"nome", "cognome", "mansione","cf","elimina");
			tf3.setStateAttr("restore");


			HtmlRow row3 = (HtmlRow) tf3.getTable().getRow();
			row3.setUniqueProperty("cf"); // the unique worksheet properties to identify the row

			tf3.getTable().getRow().getColumn( "nome" ).setTitle( "Nome" );
			tf3.getTable().getRow().getColumn( "cognome" ).setTitle( "Cognome" );
			tf3.getTable().getRow().getColumn( "mansione" ).setTitle( "Mansione" );
			tf3.getTable().getRow().getColumn( "cf" ).setTitle( "Codice Fiscale" );
			tf3.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );

			Limit limit3 = tf3.getLimit();
			if(! limit3.isExported() )
			{



				HtmlColumn cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
				//cg3.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				//cg3.setFilterable( true );
				//cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("cognome");
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("mansione");
				

				cg3.setFilterable( false );
				
				cg3.setEditable(true);
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("elimina");
				cg3.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "cf", rowCount);
		        				
		        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		        				Comuni comune = new Comuni();
		        				
								
		        		
		        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=persona&id="+iddef+"'>Elimina </a>";
		        			}
		        		}
		        
		        	);
				cg3.setFilterable(false);


			}

			
			
			ToolbarItem item13 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createFilterItem();
			item13.setTooltip( "Filtra" );
			tf3.getToolbar().addToolbarItem( item13 );
			
			ToolbarItem item15 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createClearItem();
			item15.setTooltip( "Resetta Filtro" );
			tf3.getToolbar().addToolbarItem( item15 );
												
			ToolbarItem item16 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createSaveWorksheetItem();
			item16.setTooltip( "Salva" );
			tf3.getToolbar().addToolbarItem( item16 );
			
			ToolbarItem item25 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createPrevPageItem();
			item25.setTooltip( "Scorri pagina indietro" );
			tf3.getToolbar().addToolbarItem( item25 );
			
			ToolbarItem item26= ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createNextPageItem();
			item26.setTooltip( "Scorri pagina in avanti" );
			tf3.getToolbar().addToolbarItem( item26 );
			
			
			String tabella3 = tf3.render();
			context.getRequest().setAttribute( "tabella3", tabella3 );

			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList llist = new LookupList(db,"lookup_specie_allevata");
			llist.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			llist.setMultiple(true);
			llist.setSelectSize(5);
			context.getRequest().setAttribute("SpecieA", llist);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			String codice1 = null;
			String codice2 = null;
			String codice3 = null;
			String codice4 = null;
			String codice5 = null;
			String codice6 = null;
			String codice7 = null;
			String codice8 = null;
			String codice9 = null;
			String codice10 = null;

			if(newOrg.getCodice1()!=null){
				codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
				context.getRequest().setAttribute("codice1", codice1);
			} if(newOrg.getCodice2()!=null){
				codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
				context.getRequest().setAttribute("codice2", codice2);
			} if(newOrg.getCodice3()!=null){
				codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
				context.getRequest().setAttribute("codice3", codice3);
			} if(newOrg.getCodice4()!=null){
				codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
				context.getRequest().setAttribute("codice4", codice4);
			} if(newOrg.getCodice5()!=null){
				codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
				context.getRequest().setAttribute("codice5", codice5);
			} if(newOrg.getCodice6()!=null){
				codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
				context.getRequest().setAttribute("codice6", codice6);
			} if(newOrg.getCodice7()!=null){
				codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
				context.getRequest().setAttribute("codice7", codice7);
			} if(newOrg.getCodice8()!=null){
				codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
				context.getRequest().setAttribute("codice8", codice8);
			} if(newOrg.getCodice9()!=null){
				codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
				context.getRequest().setAttribute("codice9", codice9);
			} if(newOrg.getCodice10()!=null){
				codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
				context.getRequest().setAttribute("codice10", codice10);
			}
		
			context.getRequest().setAttribute("codice1", codice1);
			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);


			LookupList stageList = new LookupList(db, "lookup_requestor_stage");
			stageList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("StageList", stageList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			String scroll = (String) context.getRequest().getParameter("scroll");
			context.getRequest().setAttribute( "scroll", scroll );
			String retPage = "DetailsOK";
			String tipo_richiesta = newOrg.getDunsType();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "Details_" + tipo_richiesta + "OK";

			return ( retPage );//("InsertOK");
			//return getReturn(context, "Details");
		}
	}
		
	
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
			return ("PermissionError");
		}
		

		String oggettoDaEliminare=context.getRequest().getParameter("oggetto");
		org_id=context.getRequest().getParameter("orgId");
		String tg=context.getRequest().getParameter("targa");
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");

			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User

			RowSetDynaClass	rsdc			= null;


			//db = getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
			ResultSet			rs		= stat.executeQuery();




			TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
			tf.setEditable(true);
			


			Worksheet worksheet = tf.getWorksheet();

			if (worksheet.isSaving() || worksheet.hasChanges())
			{ 



				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);

				

				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update organization_autoveicoli set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("targa",s);

					WorksheetRow row= worksheet.getRow(u);

					Collection<WorksheetColumn> columns = row.getColumns();
					for (WorksheetColumn colonna : columns) { 

						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						//validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());

							continue;    }


						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;

					PreparedStatement pst2=db.prepareStatement("select * from organization_autoveicoli where targa='"+s+"' and elimina is null and org_id="+org_id+" order by targa" );
					ResultSet rs2=pst2.executeQuery();
					if(!rs2.next()){
						


						Veicolo v=new Veicolo(Integer.parseInt(org_id),"","",false,false);

						//Distrubutore dist=new Distrubutore("","","","","","","","",null,-1);
						int flag=0;
						while(it.hasNext()){


							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							if(kiaveNomeColonna.equals("descrizione")){
								v.setDescrizione(valore);
							}else if(kiaveNomeColonna.equals("targa")){
									v.setTarga(valore);

								}else if (kiaveNomeColonna.equals("accepted")){
									if (valore.equals("checked")){
										v.setAccepted(true);
									}else{
										v.setAccepted(false);
									}									
								}else{
									if (kiaveNomeColonna.equals("lunghi_Viaggi")){
										if (valore.equals("checked")){
											v.setLunghi_Viaggi(true);
										}else{
											v.setLunghi_Viaggi(false);
										}
									}
								}
							



						}


						v.insert(db, Integer.parseInt(org_id));


						rs2.close();
					}else{



						int tipo=-1;
						int flag=0;
						while(it.hasNext()){
							if(c!=0){
								query+=",";
							}

							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);

							if (kiaveNomeColonna.equals("accepted")){
								if (valore.equals("checked")){
									query=query+"accepted=true";
								}else{
									query=query+"accepted=false";
								}									
							}else{
								if (kiaveNomeColonna.equals("lunghi_Viaggi")){
									if (valore.equals("checked")){
										query=query+"lunghi_viaggi=true";
									}else{
										query=query+"lunghi_viaggi=false";
									}
								}
								else{
									query=query+""+kiaveNomeColonna+"='"+valore+"'";
								}
							}
							c++;
						}

						query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
						if(c!=0){



							PreparedStatement pst=db.prepareStatement(query);
							pst.execute();
							pst.close();



						}



					}

				}

			}


if(oggettoDaEliminare!=null && oggettoDaEliminare.equals("veicolo")){
				
				String idOggetto=context.getRequest().getParameter("id");
				
				Veicolo.deleteVeicolo(idOggetto, Integer.parseInt(temporgId), db);
				
				
				
				
			}
			

			if(newOrg.getDunsType().equals("tipo1")){
				stat = db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
				rs = stat.executeQuery();


				tf.setItems( newOrg.getListaV(rs));
				tf.getWorksheet().removeAllChanges();
				//tf.setColumnProperties("org_id","descrizione", "targa","checklist","accepted");
				
				tf.setColumnProperties("descrizione", "targa","accepted","elimina");

				tf.setStateAttr("restore");

				HtmlRow row = (HtmlRow) tf.getTable().getRow();
				row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




				//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

				tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
				tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
				tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
				tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );
				//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );



				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{

					HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
					chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
					chkbox.setTitle("Check List");
					chkbox.setFilterable(false);
					chkbox.setSortable(false);

					/*HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");


					cg.setFilterable( false );
					*/


					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");


					cg.setFilterable( true );
					
					cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
					cg.setFilterable( true );

					cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
					cg.setEditable(false);
					cg.getCellRenderer().setCellEditor( 
			        		new CellEditor()
			        		{	
			        			public Object getValue(Object item, String property, int rowCount)
			        			{
			        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
			        				
			        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
			        				Comuni comune = new Comuni();
			        				
									
			        		
			        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
			        			}
			        		}
			        
			        	);
					cg.setFilterable(false);


				}
				
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );
				
				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );
													
				ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
				item2.setTooltip( "Salva" );
				tf.getToolbar().addToolbarItem( item2 );
				
				ToolbarItem item25 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
				item25.setTooltip( "Scorri pagina indietro" );
				tf.getToolbar().addToolbarItem( item25 );
				
				ToolbarItem item26= ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
				item26.setTooltip( "Scorri pagina in avanti" );
				tf.getToolbar().addToolbarItem( item26 );
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );

			}else if(newOrg.getDunsType().equals("tipo2")){
					stat = db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
					rs = stat.executeQuery();



					tf.setItems( newOrg.getListaV(rs));
					tf.getWorksheet().removeAllChanges();
					//tf.setColumnProperties("org_id","descrizione", "targa","checklist","omologazione","accepted");
					tf.setColumnProperties("descrizione", "targa","lunghi_Viaggi","accepted","elimina");

					tf.setStateAttr("restore");

					HtmlRow row = (HtmlRow) tf.getTable().getRow();
					row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




					//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

					tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
					tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );

					/*tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
					tf.getTable().getRow().getColumn( "omologazione" ).setTitle( "Omologazione" );
					*/
					

					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{

						HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("lunghi_Viaggi");
						chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
						chkbox.setTitle("Omologazione");
						chkbox.setFilterable(false);
						chkbox.setSortable(false);
						
						 chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
						chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
						chkbox.setTitle("Check List");
						chkbox.setFilterable(false);
						chkbox.setSortable(false);

						
						


						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");

						


						cg.setFilterable( true );

						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
						cg.setFilterable( true );
						cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
				        				
				        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
				        				Comuni comune = new Comuni();
				        				
										
				        		
				        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
				        			}
				        		}
				        
				        	);
						cg.setFilterable(false);
						


					}

					
					
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					
					tf.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					tf.getToolbar().addToolbarItem( item2 );
					
					ToolbarItem item27 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
					item27.setTooltip( "Scorri pagina indietro" );
					tf.getToolbar().addToolbarItem( item27 );
					
					ToolbarItem item28= ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
					item28.setTooltip( "Scorri pagina in avanti" );
					tf.getToolbar().addToolbarItem( item28 );
					
					
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );


				}
				else if((newOrg.getDunsType().equals("tipo3"))||(newOrg.getDunsType().equals("tipo4"))){
					stat = db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" and elimina is null order by targa" );
					rs = stat.executeQuery();



					tf.setItems( newOrg.getListaV(rs));
					tf.getWorksheet().removeAllChanges();
					//tf.setColumnProperties("org_id","descrizione", "targa","checklist","accepted");
					//tf.setColumnProperties("descrizione", "targa","checklist","accepted");
					tf.setColumnProperties("descrizione", "targa","elimina");
					
					tf.setStateAttr("restore");
					
					HtmlRow row = (HtmlRow) tf.getTable().getRow();
			        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
			        
			        
			        

			       // tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

					tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
					tf.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );
					//tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );

					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{

								
						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
						cg.setFilterable( true );
						cg.setEditable(false);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
									
						cg.setFilterable( true );
						cg = (HtmlColumn) tf.getTable().getRow().getColumn("elimina");
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
				        				
				        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
				        				Comuni comune = new Comuni();
				        				
										
				        		
				        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=veicolo&id="+iddef+"'>Elimina </a>";
				        			}
				        		}
				        
				        	);
						cg.setFilterable(false);
						
						
					}
					
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					tf.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					tf.getToolbar().addToolbarItem( item2 );
					
					ToolbarItem item27 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
					item27.setTooltip( "Scorri pagina indietro" );
					tf.getToolbar().addToolbarItem( item27 );
					
					ToolbarItem item28= ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
					item28.setTooltip( "Scorri pagina in avanti" );
					tf.getToolbar().addToolbarItem( item28 );
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );
		      }

			RowSetDynaClass	rsdc2			= null;

			PreparedStatement	stat2	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid+" and elimina is null");
			ResultSet			rs2		= stat2.executeQuery();


			TableFacade tf2 = TableFacadeFactory.createTableFacade( "sedi", context.getRequest());
			tf2.setEditable(true);
			
			PreparedStatement	stat1	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+temporgId+" and elimina is null");
			ResultSet			rs1		= stat1.executeQuery();
	ArrayList<Integer> listaMatricole=new ArrayList<Integer>();	
while(rs1.next()){
	
	listaMatricole.add(rs1.getInt("id"));
	
}
			
			
			
			String modificatipi="update organization_sediveicoli set comune=? where id=? and org_id=? ";
			int orgID_=Integer.parseInt(org_id);
			for(Integer s : listaMatricole){
				PreparedStatement pst=db.prepareStatement(modificatipi);
				
				String tipo=context.getRequest().getParameter("comune_"+s);
				if(tipo!=null){
				
				
					pst.setString(1, tipo);
					pst.setInt(2, s);
					pst.setInt(3, orgID_);
					pst.execute();
				
				
				}
				
					
			}
			

			
			Worksheet worksheet2 = tf2.getWorksheet();
		
			if (worksheet2.isSaving() || worksheet2.hasChanges())
			{ 

				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet2);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet2);


				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update organization_sediveicoli set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("id",s);

					WorksheetRow row1= worksheet2.getRow(u);

					Collection<WorksheetColumn> columns = row1.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						//validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());

							continue;    }

						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;

					PreparedStatement pst2=db.prepareStatement("select * from organization_sediveicoli where id='"+s+"' and org_id="+org_id);
					ResultSet rs22=pst2.executeQuery();
					if(!rs22.next()){

						Sede sede=new Sede("","","","","");
						sede.setId(Integer.parseInt(s));

						//Distrubutore dist=new Distrubutore("","","","","","","","",null,-1);
						int flag=0;
						while(it.hasNext()){


							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							if(kiaveNomeColonna.equals("comune")){
								valore = context.getRequest().getParameter("comune");
								sede.setComune(valore);
							}else{
								if(kiaveNomeColonna.equals("indirizzo")){
									sede.setIndirizzo(valore);

								}else{

									if(kiaveNomeColonna.equals("provincia")){
										sede.setProvincia(valore);

									}else{
										if(kiaveNomeColonna.equals("cap")){
											sede.setCap(valore);

										}/*else{
											if(kiaveNomeColonna.equals("stato")){
												sede.setStato(valore);

											}
										}*/



									}

								}

							}



						}

String com=context.getRequest().getParameter("comune_"+s);
sede.setComune(com);
						sede.insert(db, Integer.parseInt(org_id));
rs22.close();


					}else{



						int tipo=-1;
						int flag=0;
						while(it.hasNext()){
							if(c!=0){
								query+=",";
							}

							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);

							
							query=query+""+kiaveNomeColonna+"='"+valore+"'";



							c++;

						}
						String com=context.getRequest().getParameter("comune_"+s);
						query=query+",COMUNE='"+com+"'";

						query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
						if(c!=0){



							PreparedStatement pst=db.prepareStatement(query);
							pst.execute();
							pst.close();


						}

					}

				}

			}
			
			if(oggettoDaEliminare!=null && oggettoDaEliminare.equals("sede")){
				
				String idOggetto=context.getRequest().getParameter("id");
				int id=Integer.parseInt(idOggetto);
				Sede.deleteSede(id, Integer.parseInt(temporgId), db);
				
				
				
				
			}
			
			stat2 = db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid+" and elimina is null" );
			rs2 = stat2.executeQuery();

			tf2.setItems( newOrg.getListaS(rs2));
			tf2.getWorksheet().removeAllChanges();
			tf2.setColumnProperties( 
					"comune", "indirizzo", "provincia", "cap","elimina");
			tf2.setStateAttr("restore");

			HtmlRow row2 = (HtmlRow) tf2.getTable().getRow();
			row2.setUniqueProperty("id"); // the unique worksheet properties to identify the row

			//tf2.getTable().getRow().getColumn( "id" ).setTitle( "ID" );

			tf2.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf2.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf2.getTable().getRow().getColumn( "provincia" ).setTitle( "Provincia" );
			tf2.getTable().getRow().getColumn( "cap" ).setTitle( "Cap" );
			//tf2.getTable().getRow().getColumn( "stato" ).setTitle( "Stato" );	

			Limit limit2 = tf2.getLimit();
			if(! limit2.isExported() )
			{



				HtmlColumn cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("comune");
				cg2.setEditable(false);
				cg2.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
		        				String comunedef=(String) (new HtmlCellEditor()).getValue(item, "comune", rowCount);
		        				String comunedefault=comunedef.replaceAll("'", "_");
		        				Comuni comune = new Comuni();
		        				String select = "<SELECT name = 'comune_"+iddef+"'>";
		        				String option="";
								try {
									ArrayList<String> comuni = comune.queryRecord2(db);
									
									for(String c:comuni){
										String com=c.replace("'", "_");
										option = "<OPTION value='"+com+"'";
										if(com.equals(comunedef))
											option+="selected='selected'>"+c+"</OPTION>";
										else
											option+=">"+c+"</OPTION>";
										
										select += option; 
									}
									String select2 = "</SELECT>";
									select += select2;
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        		
		        				return select;
		        			}
		        		}
		        
		        	);
				//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg2.setFilterable( true );
				
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("elimina");
				cg2.setEditable(false);
				cg2.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
		        				
		        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		        				Comuni comune = new Comuni();
		        				String select = "<SELECT name = 'comune_"+iddef+"'>";
		        				String option="";
								
		        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=sede&id="+iddef+"'>Elimina </a>";
		        			}
		        		}
		        
		        	);
				
				
				
				
				
				
				
				
				
				
				
				//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg2.setFilterable( false );
				
				
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("indirizzo");
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("provincia");

				//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("id");
				//cg2.setEditable(false);
				//cg2.setFilterable(false);
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("cap");
				//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("stato");
				cg2.setFilterable( false );


			}
			
			
			ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createFilterItem();
			item7.setTooltip( "Filtra" );
			tf2.getToolbar().addToolbarItem( item7 );
			
			ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createClearItem();
			item8.setTooltip( "Resetta Filtro" );
			tf2.getToolbar().addToolbarItem( item8 );
												
			ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createSaveWorksheetItem();
			item2.setTooltip( "Salva" );
			tf2.getToolbar().addToolbarItem( item2 );
			
			ToolbarItem item27 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createPrevPageItem();
			item27.setTooltip( "Scorri pagina indietro" );
			tf2.getToolbar().addToolbarItem( item27 );
			
			ToolbarItem item28= ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createNextPageItem();
			item28.setTooltip( "Scorri pagina in avanti" );
			tf2.getToolbar().addToolbarItem( item28 );
			
			String tabella2 = tf2.render();
			context.getRequest().setAttribute( "tabella2", tabella2 );

			RowSetDynaClass	rsdc3			= null;

			PreparedStatement	stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid+" and elimina is null");
			ResultSet			rs3		= stat3.executeQuery();


			TableFacade tf3 = TableFacadeFactory.createTableFacade( "personale", context.getRequest() );
			tf3.setEditable(true);


			Worksheet worksheet3 = tf3.getWorksheet();

			if (worksheet3.isSaving() || worksheet3.hasChanges())
			{ 



				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet3);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet3);


				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update organization_personale set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("cf",s);

					WorksheetRow row1= worksheet3.getRow(u);

					Collection<WorksheetColumn> columns = row1.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						//validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());

							continue;    }

						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;

					//PreparedStatement pst2=db.prepareStatement("select * from organization_personale where cf='"+s+"' and org_id="+org_id);
					//ResultSet rs22=pst2.executeQuery();
					//if(!rs22.next()){

						Personale p =new Personale("","","","");
						p.setCf(s);
						//Distrubutore dist=new Distrubutore("","","","","","","","",null,-1);
						int flag=0;
						while(it.hasNext()){


							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							if(kiaveNomeColonna.equals("nome")){
								p.setNome(valore);									
								}else{
									if(kiaveNomeColonna.equals("cognome")){
										p.setCognome(valore.replaceAll("'", "_"));

									}else{

										if(kiaveNomeColonna.equals("mansione")){
											p.setMansione(valore);

										}else {
											if(kiaveNomeColonna.equals("cf")){
												p.setCf(valore);

											}
											
										}

									}

								}



						}


						boolean inserito=p.insert(db, Integer.parseInt(org_id));


						rs2.close();
					if(inserito==false){


						it=valoriAggiornati.keySet().iterator();
						int tipo=-1;
						 flag=0;
						while(it.hasNext()){
							if(c!=0){
								query+=",";
							}

							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);

							if(kiaveNomeColonna.equals("cognome")){
								
								query=query+""+kiaveNomeColonna+"='"+valore.replaceAll("'", "_")+"'";
							}
							else{
							
							query=query+""+kiaveNomeColonna+"='"+valore+"'";
							}


							c++;

						}

						query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
						if(c!=0){



							PreparedStatement pst=db.prepareStatement(query);
							pst.execute();
							pst.close();


						}




					}

				}

			}
			
			if(oggettoDaEliminare!=null && oggettoDaEliminare.equals("persona")){
				
				String cf=context.getRequest().getParameter("id");
				
				Personale.deletePersona(cf, Integer.parseInt(temporgId), db);
				
				
				
				
			}

	
			
			stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid+" and elimina is null");
			rs3		= stat3.executeQuery();

			
			tf3.setItems( newOrg.getListaP(rs3));
			tf3.getWorksheet().removeAllChanges();
			tf3.setColumnProperties( 
					"nome", "cognome", "mansione","cf","elimina");
			tf3.setStateAttr("restore");


			HtmlRow row3 = (HtmlRow) tf3.getTable().getRow();
			row3.setUniqueProperty("cf"); // the unique worksheet properties to identify the row

			tf3.getTable().getRow().getColumn( "nome" ).setTitle( "Nome" );
			tf3.getTable().getRow().getColumn( "cognome" ).setTitle( "Cognome" );
			tf3.getTable().getRow().getColumn( "mansione" ).setTitle( "Mansione" );
			tf3.getTable().getRow().getColumn( "cf" ).setTitle( "Codice Fiscale" );
			tf3.getTable().getRow().getColumn( "elimina" ).setTitle( "Elimina" );

			Limit limit3 = tf3.getLimit();
			if(! limit3.isExported() )
			{



				HtmlColumn cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
				//cg3.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				//cg3.setFilterable( true );
				//cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("cognome");
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("mansione");

				cg3.setFilterable( false );
				
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("elimina");
				cg3.setEditable(false);
				cg3.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "cf", rowCount);
		        				
		        				String orgid=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
		        				Comuni comune = new Comuni();
		        				
								
		        				return "<a href='VeicoliList.do?orgId="+orgid+"&oggetto=persona&id="+iddef+"'>Elimina </a>";
		        			}
		        		}
		        
		        	);
				
				
				
				
				
				
				
				
				
				
				
				//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg3.setFilterable( false );


			}
			
			 PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numeroveicoli from organization_autoveicoli where org_id="+tempid+" and elimina is null" );
				ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
				int numeroVeicoli=0;
				if(rs_veicoli.next())
				numeroVeicoli=rs_veicoli.getInt(1);
				
				
				
			      PreparedStatement	stat_sedi	= db.prepareStatement( "select count(*) as numerosedi from organization_sediveicoli where org_id="+tempid+" and elimina is null" );
					ResultSet			rs_sedi	= stat_sedi.executeQuery();
					int numeroSedi=0;
					if(rs_sedi.next())
						numeroSedi=rs_sedi.getInt(1);
					
			
			 PreparedStatement	stat_persona	= db.prepareStatement( "select count(*) as numerpersone from organization_personale where org_id="+tempid+" and elimina is null" );
				ResultSet			rs_persona	= stat_persona.executeQuery();
				int numeropersone=0;
				if(rs_persona.next())
					numeropersone=rs_persona.getInt(1);
				
				numeropersone=(numeropersone/15)+1;
				
				numeroVeicoli=(numeroVeicoli/15)+1;
		numeroSedi=(numeroSedi/15)+1;
				context.getRequest().setAttribute("numeroPersonale", numeropersone);
				context.getRequest().setAttribute("numeroAutoveicoli", numeroVeicoli);
				context.getRequest().setAttribute("numeroSedi", numeroSedi);
			

			
			
			ToolbarItem item13 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createFilterItem();
			item13.setTooltip( "Filtra" );
			tf3.getToolbar().addToolbarItem( item13 );
			
			ToolbarItem item15 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createClearItem();
			item15.setTooltip( "Resetta Filtro" );
			tf3.getToolbar().addToolbarItem( item15 );
												
			ToolbarItem item16 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createSaveWorksheetItem();
			item16.setTooltip( "Salva" );
			tf3.getToolbar().addToolbarItem( item16 );
			
			ToolbarItem item29 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createPrevPageItem();
			item29.setTooltip( "Scorri pagina indietro" );
			tf3.getToolbar().addToolbarItem( item29 );
			
			ToolbarItem item30= ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createNextPageItem();
			item30.setTooltip( "Scorri pagina in avanti" );
			tf3.getToolbar().addToolbarItem( item30 );
			
			
			String tabella3 = tf3.render();
			context.getRequest().setAttribute( "tabella3", tabella3 );



			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList llist = new LookupList(db,"lookup_specie_allevata");
			llist.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			llist.setMultiple(true);
			llist.setSelectSize(5);
			context.getRequest().setAttribute("SpecieA", llist);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			String codice1 = null;
			String codice2 = null;
			String codice3 = null;
			String codice4 = null;
			String codice5 = null;
			String codice6 = null;
			String codice7 = null;
			String codice8 = null;
			String codice9 = null;
			String codice10 = null;

			if(newOrg.getCodice1()!=null){
				codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
				context.getRequest().setAttribute("codice1", codice1);
			} if(newOrg.getCodice2()!=null){
				codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
				context.getRequest().setAttribute("codice2", codice2);
			} if(newOrg.getCodice3()!=null){
				codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
				context.getRequest().setAttribute("codice3", codice3);
			} if(newOrg.getCodice4()!=null){
				codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
				context.getRequest().setAttribute("codice4", codice4);
			} if(newOrg.getCodice5()!=null){
				codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
				context.getRequest().setAttribute("codice5", codice5);
			} if(newOrg.getCodice6()!=null){
				codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
				context.getRequest().setAttribute("codice6", codice6);
			} if(newOrg.getCodice7()!=null){
				codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
				context.getRequest().setAttribute("codice7", codice7);
			} if(newOrg.getCodice8()!=null){
				codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
				context.getRequest().setAttribute("codice8", codice8);
			} if(newOrg.getCodice9()!=null){
				codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
				context.getRequest().setAttribute("codice9", codice9);
			} if(newOrg.getCodice10()!=null){
				codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
				context.getRequest().setAttribute("codice10", codice10);
			}
		
			context.getRequest().setAttribute("codice1", codice1);
			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);


			LookupList stageList = new LookupList(db, "lookup_requestor_stage");
			stageList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("StageList", stageList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			String retPage = "DetailsOK";
			String tipo_richiesta = newOrg.getDunsType();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "Details_" + tipo_richiesta + "OK";

			return ( retPage );//("InsertOK");
			//return getReturn(context, "Details");
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String executeCommandElimina(ActionContext context) {
		if (!hasPermission(context, "trasportoanimali-trasportoanimali-view")) {
			return ("PermissionError");
		}
		
String oggettoDaEliminare=context.getRequest().getParameter("oggetto");
		org_id=context.getRequest().getParameter("orgId");
		String tg=context.getRequest().getParameter("targa");
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");

			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User

			RowSetDynaClass	rsdc			= null;


			//db = getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa" );
			ResultSet			rs		= stat.executeQuery();




			TableFacade tf = TableFacadeFactory.createTableFacade( "veicoli", context.getRequest() );
			tf.setEditable(true);
			


			Worksheet worksheet = tf.getWorksheet();

			if (worksheet.isSaving() || worksheet.hasChanges())
			{ 



				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);

				

				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update organization_autoveicoli set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("targa",s);

					WorksheetRow row= worksheet.getRow(u);

					Collection<WorksheetColumn> columns = row.getColumns();
					for (WorksheetColumn colonna : columns) { 

						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						//validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());

							continue;    }


						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;

					PreparedStatement pst2=db.prepareStatement("select * from organization_autoveicoli where targa='"+s+"' and org_id="+org_id+" order by targa" );
					ResultSet rs2=pst2.executeQuery();
					if(!rs2.next()){
						


						Veicolo v=new Veicolo(Integer.parseInt(org_id),"","",false,false);

						//Distrubutore dist=new Distrubutore("","","","","","","","",null,-1);
						int flag=0;
						while(it.hasNext()){


							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							if(kiaveNomeColonna.equals("descrizione")){
								v.setDescrizione(valore);
							}else if(kiaveNomeColonna.equals("targa")){
									v.setTarga(valore);

								}else if (kiaveNomeColonna.equals("accepted")){
									if (valore.equals("checked")){
										v.setAccepted(true);
									}else{
										v.setAccepted(false);
									}									
								}else{
									if (kiaveNomeColonna.equals("lunghi_Viaggi")){
										if (valore.equals("checked")){
											v.setLunghi_Viaggi(true);
										}else{
											v.setLunghi_Viaggi(false);
										}
									}
								}
							



						}


						v.insert(db, Integer.parseInt(org_id));


						rs2.close();
					}else{



						int tipo=-1;
						int flag=0;
						while(it.hasNext()){
							if(c!=0){
								query+=",";
							}

							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);

							if (kiaveNomeColonna.equals("accepted")){
								if (valore.equals("checked")){
									query=query+"accepted=true";
								}else{
									query=query+"accepted=false";
								}									
							}else{
								if (kiaveNomeColonna.equals("lunghi_Viaggi")){
									if (valore.equals("checked")){
										query=query+"lunghi_viaggi=true";
									}else{
										query=query+"lunghi_viaggi=false";
									}
								}
								else{
									query=query+""+kiaveNomeColonna+"='"+valore+"'";
								}
							}
							c++;
						}

						query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
						if(c!=0){



							PreparedStatement pst=db.prepareStatement(query);
							pst.execute();
							pst.close();



						}



					}

				}

			}



			if(newOrg.getDunsType().equals("tipo1")){
				stat = db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa" );
				rs = stat.executeQuery();


				tf.setItems( newOrg.getListaV(rs));
				tf.getWorksheet().removeAllChanges();
				//tf.setColumnProperties("org_id","descrizione", "targa","checklist","accepted");
				
				tf.setColumnProperties("descrizione", "targa","accepted");

				tf.setStateAttr("restore");

				HtmlRow row = (HtmlRow) tf.getTable().getRow();
				row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




				//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

				tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
				tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
				tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );
				//tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );



				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{

					HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
					chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
					chkbox.setTitle("Check List");
					chkbox.setFilterable(false);
					chkbox.setSortable(false);

					/*HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");


					cg.setFilterable( false );
					*/


					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");

					/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("checklist");
					cg.setEditable(false);
					cg.getCellRenderer().setCellEditor( 
							new CellEditor()
							{	
								public Object getValue(Object item, String property, int rowCount)
								{
									//String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
									String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
									String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
									//temp = (temp == null || "".equals(temp.trim())) ? ("Check List") : (temp);

									String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&file=allegato_B1.xml&targa="+targa+"\">CheckList </a>";

									return ret;
								}
							});
*/

					cg.setFilterable( true );
					
					cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
					cg.setFilterable( true );

					//cg.setFilterable( false );
					/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");
					cg.setFilterable( false );*/
					/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("checklist");
					cg.getCellRenderer().setCellEditor( 
							new CellEditor()
							{	
								public Object getValue(Object item, String property, int rowCount)
								{
									String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
									String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
									String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
									temp = (temp == null || "".equals(temp.trim())) ? ("Check List") : (temp);

									String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&targa="+targa+"&file=allegato_B1.xml\">" + temp + "</a>";
									return ret;
								}
							});*/


				}
				
				
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );
				
				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );
													
				ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
				item2.setTooltip( "Salva" );
				tf.getToolbar().addToolbarItem( item2 );
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );

			}else if(newOrg.getDunsType().equals("tipo2")){
					stat = db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa" );
					rs = stat.executeQuery();



					tf.setItems( newOrg.getListaV(rs));
					tf.getWorksheet().removeAllChanges();
					//tf.setColumnProperties("org_id","descrizione", "targa","checklist","omologazione","accepted");
					tf.setColumnProperties("descrizione", "targa","lunghi_Viaggi","accepted");

					tf.setStateAttr("restore");

					HtmlRow row = (HtmlRow) tf.getTable().getRow();
					row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row




					//tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

					tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );

					/*tf.getTable().getRow().getColumn( "checklist" ).setTitle( "Check List" );
					tf.getTable().getRow().getColumn( "omologazione" ).setTitle( "Omologazione" );
					*/
					

					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{

						HtmlColumn chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("lunghi_Viaggi");
						chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
						chkbox.setTitle("Omologazione");
						chkbox.setFilterable(false);
						chkbox.setSortable(false);
						
						 chkbox =(HtmlColumn) tf.getTable().getRow().getColumn("accepted");
						chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
						chkbox.setTitle("Check List");
						chkbox.setFilterable(false);
						chkbox.setSortable(false);

						/*HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");


						cg.setFilterable( false );*/
						


						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");

						/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("checklist");
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor( 
								new CellEditor()
								{	
									public Object getValue(Object item, String property, int rowCount)
									{
										//String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
										String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
										String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
										//temp = (temp == null || "".equals(temp.trim())) ? ("Check List") : (temp);

										String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&file=allegato_B1.xml&targa="+targa+"\">CheckList </a>";

										return ret;
									}
								});*/


						cg.setFilterable( true );

						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
						cg.setFilterable( true );

						//cg.setFilterable( false );
						/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("org_id");
						cg.setFilterable( false );*/
						/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("checklist");
									cg.getCellRenderer().setCellEditor( 
							        		new CellEditor()
							        		{	
							        			public Object getValue(Object item, String property, int rowCount)
							        			{
							        				String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
							        				String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
							        				String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
							        				temp = (temp == null || "".equals(temp.trim())) ? ("Check List") : (temp);

							        				String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&targa="+targa+"&file=allegato_B1.xml\">" + temp + "</a>";
							        				return ret;
							        			}
							        		});*/


						/*cg = (HtmlColumn) tf.getTable().getRow().getColumn("omologazione");
						cg.setFilterable(false);
						cg.setEditable(false);
						cg.getCellRenderer().setCellEditor( 
								new CellEditor()
								{	
									public Object getValue(Object item, String property, int rowCount)
									{

										String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
										String targa	= (String) (new HtmlCellEditor()).getValue(item, "targa", rowCount);
										String orgId	= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
										temp = (temp == null || "".equals(temp.trim())) ? ("Omologazione") : (temp);

										String ret = "<a href=\"TrasportoAnimali.do?command=PrintReport&id=" + orgId + "&targa="+targa+"&file=allegato_E1.xml\">" + temp + "</a>";
										return ret;
									}
								});*/


					}

				
					
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					
					tf.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					tf.getToolbar().addToolbarItem( item2 );
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );


				}
				else if((newOrg.getDunsType().equals("tipo3"))||(newOrg.getDunsType().equals("tipo4"))){
					stat = db.prepareStatement( "select * from organization_autoveicoli where org_id="+tempid+" order by targa" );
					rs = stat.executeQuery();



					tf.setItems( newOrg.getListaV(rs));
					tf.getWorksheet().removeAllChanges();
					//tf.setColumnProperties("org_id","descrizione", "targa","checklist","accepted");
					//tf.setColumnProperties("descrizione", "targa","checklist","accepted");
					tf.setColumnProperties("descrizione", "targa");
					
					tf.setStateAttr("restore");
					
					HtmlRow row = (HtmlRow) tf.getTable().getRow();
			        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
			        
			        
			        

			       // tf.getTable().getRow().getColumn( "org_id" ).setTitle( "OrgId" );

					tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione Veicolo" );
					//tf.getTable().getRow().getColumn( "accepted" ).setTitle( "Check List" );

					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{

								
						HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
						cg.setFilterable( true );
						cg.setEditable(false);

						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
									
						cg.setFilterable( true );
						
						
						
					}
					
					
					ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
					item7.setTooltip( "Filtra" );
					tf.getToolbar().addToolbarItem( item7 );
					
					ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
					item8.setTooltip( "Resetta Filtro" );
					tf.getToolbar().addToolbarItem( item8 );
														
					ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createSaveWorksheetItem();
					item2.setTooltip( "Salva" );
					tf.getToolbar().addToolbarItem( item2 );
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );
		      }

			RowSetDynaClass	rsdc2			= null;

			PreparedStatement	stat2	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid);
			ResultSet			rs2		= stat2.executeQuery();


			TableFacade tf2 = TableFacadeFactory.createTableFacade( "sedi", context.getRequest());
			tf2.setEditable(true);
			
			PreparedStatement	stat1	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+temporgId);
			ResultSet			rs1		= stat1.executeQuery();
	ArrayList<Integer> listaMatricole=new ArrayList<Integer>();	
while(rs1.next()){
	
	listaMatricole.add(rs1.getInt("id"));
	
}
			
			
			
			String modificatipi="update organization_sediveicoli set comune=? where id=? and org_id=? ";
			int orgID_=Integer.parseInt(org_id);
			for(Integer s : listaMatricole){
				PreparedStatement pst=db.prepareStatement(modificatipi);
				
				String tipo=context.getRequest().getParameter("comune_"+s);
				if(tipo!=null){
				
				
					pst.setString(1, tipo);
					pst.setInt(2, s);
					pst.setInt(3, orgID_);
					pst.execute();
				
				
				}
				
					
			}
			

			
			Worksheet worksheet2 = tf2.getWorksheet();
		
			if (worksheet2.isSaving() || worksheet2.hasChanges())
			{ 

				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet2);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet2);


				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update organization_sediveicoli set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("id",s);

					WorksheetRow row1= worksheet2.getRow(u);

					Collection<WorksheetColumn> columns = row1.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						//validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());

							continue;    }

						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;

					PreparedStatement pst2=db.prepareStatement("select * from organization_sediveicoli where id='"+s+"' and org_id="+org_id);
					ResultSet rs22=pst2.executeQuery();
					if(!rs22.next()){

						Sede sede=new Sede("","","","","");
						sede.setId(Integer.parseInt(s));

						//Distrubutore dist=new Distrubutore("","","","","","","","",null,-1);
						int flag=0;
						while(it.hasNext()){


							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							if(kiaveNomeColonna.equals("comune")){
								valore = context.getRequest().getParameter("comune");
								sede.setComune(valore);
							}else{
								if(kiaveNomeColonna.equals("indirizzo")){
									sede.setIndirizzo(valore);

								}else{

									if(kiaveNomeColonna.equals("provincia")){
										sede.setProvincia(valore);

									}else{
										if(kiaveNomeColonna.equals("cap")){
											sede.setCap(valore);

										}/*else{
											if(kiaveNomeColonna.equals("stato")){
												sede.setStato(valore);

											}
										}*/



									}

								}

							}



						}

String com=context.getRequest().getParameter("comune_"+s);
sede.setComune(com);
						sede.insert(db, Integer.parseInt(org_id));
rs22.close();


					}else{



						int tipo=-1;
						int flag=0;
						while(it.hasNext()){
							if(c!=0){
								query+=",";
							}

							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);

							
							query=query+""+kiaveNomeColonna+"='"+valore+"'";



							c++;

						}
						String com=context.getRequest().getParameter("comune_"+s);
						query=query+",COMUNE='"+com+"'";

						query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
						if(c!=0){



							PreparedStatement pst=db.prepareStatement(query);
							pst.execute();
							pst.close();


						}

					}

				}

			}
			stat2 = db.prepareStatement( "select * from organization_sediveicoli where org_id="+tempid );
			rs2 = stat2.executeQuery();

			tf2.setItems( newOrg.getListaS(rs2));
			tf2.getWorksheet().removeAllChanges();
			tf2.setColumnProperties( 
					"comune", "indirizzo", "provincia", "cap");
			tf2.setStateAttr("restore");

			HtmlRow row2 = (HtmlRow) tf2.getTable().getRow();
			row2.setUniqueProperty("id"); // the unique worksheet properties to identify the row

			//tf2.getTable().getRow().getColumn( "id" ).setTitle( "ID" );

			tf2.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf2.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf2.getTable().getRow().getColumn( "provincia" ).setTitle( "Provincia" );
			tf2.getTable().getRow().getColumn( "cap" ).setTitle( "Cap" );
			//tf2.getTable().getRow().getColumn( "stato" ).setTitle( "Stato" );	

			Limit limit2 = tf2.getLimit();
			if(! limit2.isExported() )
			{



				HtmlColumn cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("comune");
				cg2.setEditable(false);
				cg2.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String iddef=(String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
		        				String comunedef=(String) (new HtmlCellEditor()).getValue(item, "comune", rowCount);
		        				Comuni comune = new Comuni();
		        				String select = "<SELECT name = 'comune_"+iddef+"'>";
		        				String option="";
								try {
									ArrayList<String> comuni = comune.queryRecord2(db);
									
									for(String c:comuni){
										option = "<OPTION value='"+c+"'";
										if(c.equals(comunedef))
											option+="selected='selected'>"+c+"</OPTION>";
										else
											option+=">"+c+"</OPTION>";
										
										select += option; 
									}
									String select2 = "</SELECT>";
									select += select2;
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        		
		        				return select;
		        			}
		        		}
		        
		        	);
				//cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg2.setFilterable( true );
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("indirizzo");
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("provincia");

				//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("id");
				//cg2.setEditable(false);
				//cg2.setFilterable(false);
				cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("cap");
				//cg2 = (HtmlColumn) tf2.getTable().getRow().getColumn("stato");
				cg2.setFilterable( false );


			}
		
			ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createFilterItem();
			item7.setTooltip( "Filtra" );
			tf2.getToolbar().addToolbarItem( item7 );
			
			ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createClearItem();
			item8.setTooltip( "Resetta Filtro" );
			tf2.getToolbar().addToolbarItem( item8 );
												
			ToolbarItem item2 = ( new ToolbarItemFactoryImpl( tf2.getWebContext(), tf2.getCoreContext() ) ).createSaveWorksheetItem();
			item2.setTooltip( "Salva" );
			tf2.getToolbar().addToolbarItem( item2 );
			
			String tabella2 = tf2.render();
			context.getRequest().setAttribute( "tabella2", tabella2 );

			RowSetDynaClass	rsdc3			= null;

			PreparedStatement	stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid);
			ResultSet			rs3		= stat3.executeQuery();


			TableFacade tf3 = TableFacadeFactory.createTableFacade( "personale", context.getRequest() );
			tf3.setEditable(true);


			Worksheet worksheet3 = tf3.getWorksheet();

			if (worksheet3.isSaving() || worksheet3.hasChanges())
			{ 



				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet3);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet3);


				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update organization_personale set ";
					valoriAggiornati=new HashMap<String, String>();
					UniqueProperty u =new UniqueProperty("cf",s);

					WorksheetRow row1= worksheet3.getRow(u);

					Collection<WorksheetColumn> columns = row1.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();    
						String nomeColonna=colonna.getProperty();

						//validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());

							continue;    }

						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;

					PreparedStatement pst2=db.prepareStatement("select * from organization_personale where cf='"+s+"' and org_id="+org_id);
					ResultSet rs22=pst2.executeQuery();
					

						Personale p =new Personale("","","","");
						p.setCf(s);
						//Distrubutore dist=new Distrubutore("","","","","","","","",null,-1);
						int flag=0;
						while(it.hasNext()){


							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							if(kiaveNomeColonna.equals("nome")){
								p.setNome(valore);									
								}else{
									if(kiaveNomeColonna.equals("cognome")){
										p.setCognome(valore);

									}else{

										if(kiaveNomeColonna.equals("mansione")){
											p.setMansione(valore);

										}else {
											if(kiaveNomeColonna.equals("cf")){
												p.setCf(valore);

											}
											
										}

									}

								}



						}


					boolean insert=	p.insert(db, Integer.parseInt(org_id));


						rs2.close();
					if(insert==false){



						int tipo=-1;
					 flag=0;
						while(it.hasNext()){
							if(c!=0){
								query+=",";
							}

							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);


							query=query+""+kiaveNomeColonna+"='"+valore+"'";



							c++;

						}

						query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
						if(c!=0){



							PreparedStatement pst=db.prepareStatement(query);
							pst.execute();
							pst.close();


						}




					}

				}

			}
			stat3	= db.prepareStatement( "select * from organization_personale where org_id="+tempid);
			rs3		= stat3.executeQuery();

			
			tf3.setItems( newOrg.getListaP(rs3));
			tf3.getWorksheet().removeAllChanges();
			tf3.setColumnProperties( 
					"nome", "cognome", "mansione","cf");
			tf3.setStateAttr("restore");


			HtmlRow row3 = (HtmlRow) tf3.getTable().getRow();
			row3.setUniqueProperty("cf"); // the unique worksheet properties to identify the row

			tf3.getTable().getRow().getColumn( "nome" ).setTitle( "Nome" );
			tf3.getTable().getRow().getColumn( "cognome" ).setTitle( "Cognome" );
			tf3.getTable().getRow().getColumn( "mansione" ).setTitle( "Mansione" );
			tf3.getTable().getRow().getColumn( "cf" ).setTitle( "Codice Fiscale" );

			Limit limit3 = tf3.getLimit();
			if(! limit3.isExported() )
			{



				HtmlColumn cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
				//cg3.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				//cg3.setFilterable( true );
				//cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("nome");
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("cognome");
				cg3 = (HtmlColumn) tf3.getTable().getRow().getColumn("mansione");

				cg3.setFilterable( false );


			}

			
			
			ToolbarItem item13 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createFilterItem();
			item13.setTooltip( "Filtra" );
			tf3.getToolbar().addToolbarItem( item13 );
			
			ToolbarItem item15 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createClearItem();
			item15.setTooltip( "Resetta Filtro" );
			tf3.getToolbar().addToolbarItem( item15 );
												
			ToolbarItem item16 = ( new ToolbarItemFactoryImpl( tf3.getWebContext(), tf3.getCoreContext() ) ).createSaveWorksheetItem();
			item16.setTooltip( "Salva" );
			tf3.getToolbar().addToolbarItem( item16 );
			String tabella3 = tf3.render();
			context.getRequest().setAttribute( "tabella3", tabella3 );



			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList llist = new LookupList(db,"lookup_specie_allevata");
			llist.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
			llist.setMultiple(true);
			llist.setSelectSize(5);
			context.getRequest().setAttribute("SpecieA", llist);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);

			String codice1 = null;
			String codice2 = null;
			String codice3 = null;
			String codice4 = null;
			String codice5 = null;
			String codice6 = null;
			String codice7 = null;
			String codice8 = null;
			String codice9 = null;
			String codice10 = null;

			if(newOrg.getCodice1()!=null){
				codice1 = IstatList.getSelectedValueShort(newOrg.getCodice1(), db);
				context.getRequest().setAttribute("codice1", codice1);
			} if(newOrg.getCodice2()!=null){
				codice2 = IstatList.getSelectedValueShort(newOrg.getCodice2(), db);
				context.getRequest().setAttribute("codice2", codice2);
			} if(newOrg.getCodice3()!=null){
				codice3 = IstatList.getSelectedValueShort(newOrg.getCodice3(), db);
				context.getRequest().setAttribute("codice3", codice3);
			} if(newOrg.getCodice4()!=null){
				codice4 = IstatList.getSelectedValueShort(newOrg.getCodice4(), db);
				context.getRequest().setAttribute("codice4", codice4);
			} if(newOrg.getCodice5()!=null){
				codice5 = IstatList.getSelectedValueShort(newOrg.getCodice5(), db);
				context.getRequest().setAttribute("codice5", codice5);
			} if(newOrg.getCodice6()!=null){
				codice6 = IstatList.getSelectedValueShort(newOrg.getCodice6(), db);
				context.getRequest().setAttribute("codice6", codice6);
			} if(newOrg.getCodice7()!=null){
				codice7 = IstatList.getSelectedValueShort(newOrg.getCodice7(), db);
				context.getRequest().setAttribute("codice7", codice7);
			} if(newOrg.getCodice8()!=null){
				codice8 = IstatList.getSelectedValueShort(newOrg.getCodice8(), db);
				context.getRequest().setAttribute("codice8", codice8);
			} if(newOrg.getCodice9()!=null){
				codice9 = IstatList.getSelectedValueShort(newOrg.getCodice9(), db);
				context.getRequest().setAttribute("codice9", codice9);
			} if(newOrg.getCodice10()!=null){
				codice10 = IstatList.getSelectedValueShort(newOrg.getCodice10(), db);
				context.getRequest().setAttribute("codice10", codice10);
			}
			
			context.getRequest().setAttribute("codice1", codice1);
			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);


			LookupList stageList = new LookupList(db, "lookup_requestor_stage");
			stageList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("StageList", stageList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			String retPage = "DetailsOK";
			String tipo_richiesta = newOrg.getDunsType();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "Details_" + tipo_richiesta + "OK";

			return ( retPage );//("InsertOK");
			//return getReturn(context, "Details");
		}
	}

    private void validateColumn(WorksheetColumn worksheetColumn, String changedValue) {
    	if (changedValue.equals("foo")) {
    		worksheetColumn.setErrorKey("foo.error");
    	} else {
    		worksheetColumn.removeError();
    	}
    }
	
}
	
