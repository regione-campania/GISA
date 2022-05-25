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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.distributori.base.Organization;
import org.aspcfs.modules.trasportoanimali.base.Personale;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;

import com.darkhorseventures.framework.actions.ActionContext;

public final class PersonaleList extends CFSModule
{

	public String executeCommandDefault(ActionContext context)
	{
		return executeCommandDetails(context);
	}

	String org_id="-1";
	Connection db=null;
	
		public String executeCommandDetails(ActionContext context) {
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
		      
		    String  org_id=context.getRequest().getParameter("orgId");
		      
		      
		      
		      
		      
		     
			    RowSetDynaClass	rsdc			= null;
			    
				
					db = getConnection(context);
					
					
					/*TableFacade ttf=(TableFacade) context.getRequest().getSession().getAttribute("tf");
					System.out.println("ora deve andare ttf "+ttf.getWorksheet().getId());*/
					
					
					
		
					 //saveWorksheet(ttf,db,org_id);
					
					
					
					
					PreparedStatement	stat	= db.prepareStatement( "select * from organization_autoveicoli where org_id="+newOrg.getId());
					ResultSet			rs		= stat.executeQuery();
										rsdc	= new RowSetDynaClass(rs);
										
					TableFacade tf = TableFacadeFactory.createTableFacade( "15", context.getRequest() );
					tf.setEditable(true);
					tf.setItems( newOrg.getListaDistributori());
					
					
					tf.setColumnProperties( "selected",
							"descrizione", "targa");
					tf.setStateAttr("restore");					
					HtmlRow row = (HtmlRow) tf.getTable().getRow();
			        row.setUniqueProperty("targa"); // the unique worksheet properties to identify the row
			        
			        
			        
			        HtmlColumn chkbox = row.getColumn("selected");
			        chkbox.getCellRenderer().setWorksheetEditor(new CheckboxWorksheetEditor());
			        chkbox.setTitle("&nbsp;");
			        chkbox.setFilterable(false);
			        chkbox.setSortable(false);
					//tf.setExportTypes( context.getResponse(), ExportType.JEXCEL, ExportType.EXCEL, ExportType.PDFP, ExportType.PDF, ExportType.CSV );
					
				
					tf.getTable().getRow().getColumn( "targa" ).setTitle( "Targa" );
					tf.getTable().getRow().getColumn( "descrizione" ).setTitle( "Descrizione" );
				
					
					
					Limit limit = tf.getLimit();
					if(! limit.isExported() )
					{
						
						
						
									HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("targa");
						cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
						//cg.setFilterable( false );
						
						
						cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizione");
						cg.setFilterable( true );
						
						
					}
					
					String tabella = tf.render();
					context.getRequest().setAttribute( "tabella", tabella );
				
		      LookupList siteList = new LookupList(db, "lookup_site_id");
		      siteList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteList", siteList);
		      
		      LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
		      tipoDitributore.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
		      context.getRequest().setAttribute("TipoDitributore", tipoDitributore);      
		      
		      LookupList statoLab = new LookupList(db, "lookup_stato_lab");
		      statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("statoLab", statoLab);
		      
		      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
		      categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
		      
		      LookupList impianto = new LookupList(db, "lookup_impianto");
		      impianto.addItem(-1,  "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("impianto", impianto);
		      
		      
		      
		      LookupList stageList = new LookupList(db, "lookup_distributori_stage");
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
		      return getReturn(context, "Details");
		    }
		  }
		
		
		 private void saveWorksheet(TableFacade tableFacade,final Connection db,String org_id) {
		        Worksheet worksheet = tableFacade.getWorksheet();
		        if (!worksheet.isSaving() || !worksheet.hasChanges()) {
		            return;
		        }

		        String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
		        List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);
		        int orgidd=Integer.parseInt(org_id);
		        
		       final Map<String, Personale> presidents = Personale.getPresidentsByUniqueIds(orgidd,db);
		        
		       Collection<WorksheetRow> rows= worksheet.getRows();
		       for(WorksheetRow row : rows){
		    	   String uniqueValue = row.getUniqueProperty().getValue();
                Collection <WorksheetColumn> colonne=row.getColumns();		    	   

                for(WorksheetColumn colonna : colonne){
                	String value= colonna.getProperty();
                	
                	
                	
                }
		    	   
		       }
		       
		       
		       
		        /*worksheet.processRows(new WorksheetCallbackHandler() {
		            public void process(WorksheetRow worksheetRow) {
		                Collection<WorksheetColumn> columns = worksheetRow.getColumns();
		                String org_id="-1";
		                for (WorksheetColumn worksheetColumn : columns) {
		                    String changedValue = worksheetColumn.getChangedValue();

		                    validateColumn(worksheetColumn, changedValue);
		                    if (worksheetColumn.hasError()) {
		                        continue;
		                    }

		                    String uniqueValue = worksheetRow.getUniqueProperty().getValue();
		                    Distrubutore president = presidents.get(uniqueValue);
		                    String property = worksheetColumn.getProperty();

		                    try {
		                        if (worksheetColumn.getProperty().equals("selected")) {
		                            if (changedValue.equals(CheckboxWorksheetEditor.CHECKED)) {
		                                PropertyUtils.setProperty(president, property, "y");
		                            } else {
		                                PropertyUtils.setProperty(president, property, "n");
		                            }

		                        } else {
		                            PropertyUtils.setProperty(president, property, changedValue);
		                        }
		                    } catch (Exception ex) {
		                        throw new RuntimeException("Not able to set the property [" + property + "] when saving worksheet.");
		                    }
		                    int orid=Integer.parseInt(org_id);    
		                    
		                    president.update(db, -1);
		                }
		            }
		        });*/
		    }

		    /**
		     * An example of how to validate the worksheet column cells.
		     */
		    private void validateColumn(WorksheetColumn worksheetColumn, String changedValue) {
		        if (changedValue.equals("foo")) {
		            worksheetColumn.setErrorKey("foo.error");
		        } else {
		            worksheetColumn.removeError();
		        }
		    }
		
		
	}
	

