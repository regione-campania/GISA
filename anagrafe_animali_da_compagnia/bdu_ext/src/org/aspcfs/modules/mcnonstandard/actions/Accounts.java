/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mcnonstandard.actions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;

import org.aspcfs.modules.mcnonstandard.base.McNonStandard;
import org.aspcfs.utils.web.LookupList;

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactoryImpl;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public final class Accounts extends CFSModule
{
	Connection db=null;
	//array contenente tutti i dati dei cani
	private ArrayList<McNonStandard> mc=new ArrayList<McNonStandard>();

	public String executeCommandDefault(ActionContext context)
	{
		if(context.getRequest().getParameter("command")==null){

		try {
			return executeCommandList2(context);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else
		return executeCommandAdd(context);
		return null;

	}

	public String executeCommandList2(ActionContext context) throws SQLException
	{
		RowSetDynaClass	rsdc			= null;
  	    
  		
			db = getConnection(context);
			
			PreparedStatement	stat	= db.prepareStatement( "select * from mc_non_standard m,lookup_asset_manufacturer l,lookup_asset_status k,lookup_asset_vendor v where m.razza=l.code and m.mantello=k.code and v.code=m.taglia  " );
			
			ResultSet			rs		= stat.executeQuery();
								
			TableFacade tf = TableFacadeFactory.createTableFacade( "15", context.getRequest() );
			tf.setEditable(true);
		 Collection<McNonStandard> coll = new ArrayList<McNonStandard>();
	
	
			//rsdc	= new RowSetDynaClass(rs);
			//recupero dei dati
			while(rs.next())
			{
				McNonStandard mc=new McNonStandard();
				mc.setSerial_number(rs.getString("serial_number"));
				mc.setData_di_nascita(rs.getDate("data_di_nascita"));
				mc.setComune(rs.getString("comune"));
				mc.setCognome_proprietario(rs.getString("cognome_proprietario"));
				mc.setNome_proprietario(rs.getString("nome_proprietario"));
				mc.setData_esame1(rs.getDate("data_esame1"));	
				mc.setNotes(rs.getString("notes"));
				mc.setCodice_fiscale(rs.getString("codice_fiscale"));
				mc.setIndirizzo(rs.getString("indirizzo"));
				mc.setTipo_esame1(rs.getString("tipo_esame1"));
			    mc.setEsito_esame1(rs.getInt("esito_esame1"));
				mc.setPassaporto(rs.getString("passaporto"));
				mc.setRazza(rs.getInt("razza"));
				mc.setMantello(rs.getInt("mantello"));
				mc.setTaglia(rs.getInt("taglia"));
				coll.add(mc);
			}
		 
		 
			tf.setItems(coll );
			tf.setColumnProperties( 
					"serial_number", "razza", "mantello", "taglia", "data_di_nascita",
					"passaporto", "nome_proprietario", "cognome_proprietario", 
					"codice_fiscale", "indirizzo","comune",
					"tipo_esame1", "data_esame1","esito_esame1", 
					"tipo_esame2","data_esame2","esito_esame2","notes"
			);
			tf.setStateAttr("restore");
			
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
	        row.setUniqueProperty("serial_number"); // the unique worksheet properties to identify the row
	      
		
	        tf.getTable().getRow().getColumn( "serial_number" ).setTitle( "Microchip" );
			tf.getTable().getRow().getColumn( "razza" ).setTitle( "Razza" );
			tf.getTable().getRow().getColumn( "mantello" ).setTitle( "Mantello" );
			tf.getTable().getRow().getColumn( "taglia" ).setTitle( "Taglia" );
			tf.getTable().getRow().getColumn( "data_di_nascita" ).setTitle( "Data di nascita" );
			tf.getTable().getRow().getColumn( "passaporto" ).setTitle( "Passaporto" );
			tf.getTable().getRow().getColumn( "nome_proprietario" ).setTitle( "Nome Proprietario" );
			tf.getTable().getRow().getColumn( "cognome_proprietario" ).setTitle( "Cognome Proprietario" );
			tf.getTable().getRow().getColumn( "codice_fiscale" ).setTitle( "Codice fiscale" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf.getTable().getRow().getColumn( "tipo_esame1" ).setTitle( "Tipo esame" );
			tf.getTable().getRow().getColumn( "data_esame1" ).setTitle( "Data Esame" );
			tf.getTable().getRow().getColumn( "esito_esame1" ).setTitle( "Esito Esame" );
			tf.getTable().getRow().getColumn( "tipo_esame2" ).setTitle( "Tipo Esame" );
			tf.getTable().getRow().getColumn( "data_esame2" ).setTitle( "Data Esame" );
			tf.getTable().getRow().getColumn( "esito_esame2" ).setTitle( "Esito Esame" );
			tf.getTable().getRow().getColumn( "notes" ).setTitle( "Note" );
					
		
			
			Integer[] array=null;
			Iterator<McNonStandard> it=coll.iterator();

	
	array=new Integer[coll.size()];
	int i=0;


			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{
				
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("serial_number");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("razza");
				cg.setFilterable( false );
				cg.setEditable(false);
				cg.getCellRenderer().setCellEditor( 
						
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								LookupList alimenti = null;
								try {
									alimenti = new LookupList(db,"lookup_asset_manufacturer");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String id	= (String) (new HtmlCellEditor()).getValue(item, "razza", rowCount);

								String serial_number	= (String) (new HtmlCellEditor()).getValue(item, "serial_number", rowCount);
								temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
								return alimenti.getHtmlSelect("razza_"+serial_number, id);
							}
						}

				);
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("mantello");
				cg.setFilterable( false );
				cg.setEditable(false);
				cg.getCellRenderer().setCellEditor( 
						
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								LookupList mantello = null;
								try {
									mantello= new LookupList(db,"lookup_asset_status");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String id	= (String) (new HtmlCellEditor()).getValue(item, "mantello", rowCount);

								String serial_number	= (String) (new HtmlCellEditor()).getValue(item, "serial_number", rowCount);
								temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
								return mantello.getHtmlSelect("mantello_"+serial_number, id);
							}
						}

				);
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("taglia");
				cg.setFilterable( false );
				cg.setEditable(false);
				cg.getCellRenderer().setCellEditor( 
						
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								LookupList taglia = null;
								try {
									taglia = new LookupList(db,"lookup_asset_vendor");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String id	= (String) (new HtmlCellEditor()).getValue(item, "taglia", rowCount);

								String serial_number	= (String) (new HtmlCellEditor()).getValue(item, "serial_number", rowCount);
								temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
								return taglia.getHtmlSelect("taglia_"+serial_number, id);
							}
						}

				);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_di_nascita");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("passaporto");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("nome_proprietario");
				cg.setFilterable( true );
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cognome_proprietario");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("codice_fiscale");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame1");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame1");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame1");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame2");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame2");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame2");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("notes");
				cg.setFilterable( false );
		
			}
		

		       //tf.getWorksheet().addRow()
			//tf.getWorksheet().addRow(new WorksheetRow)
			
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

			context.getRequest().setAttribute( "tf", tf );
			PreparedStatement	stat_cani	= db.prepareStatement( "select count(*) as numero from mc_non_standard");
			ResultSet			rs_cani		= stat_cani.executeQuery();
			int numeroCani=0;
			if(rs_cani.next())
				numeroCani=rs_cani.getInt(1);

			numeroCani=(numeroCani/15)+1;
			context.getRequest().setAttribute("numeroChip", numeroCani);
			rs.close();
			stat.close();
			rs_cani.close();
			stat_cani.close();
			
			return "ListOk";
	}
	
	 public String executeCommandList( ActionContext context ){
	 //controllo sui permessi 
	 if ( !hasPermission(context, "mcNonStandard-view") )
		{
		      return ("PermissionError");
		}
	 
	 SystemStatus 	systemStatus	= this.getSystemStatus(context);
	 RowSetDynaClass	rsdc			= null;
	 try{
		 //connessiona al db
		 db = this.getConnection(context);
		 //inizializzazione della collezione
		 Collection <McNonStandard> coll=mc;
		 //svuota la collezione da tutto altrimenti concatena
		 coll.removeAll(coll);
			//recupero tutti i microchip
		 
			PreparedStatement	stat	= db.prepareStatement( "select * from mc_non_standard" );
			ResultSet			rs		= stat.executeQuery();
			rsdc	= new RowSetDynaClass(rs);
			//recupero dei dati
			while(rs.next())
			{
				McNonStandard mc=new McNonStandard();
				mc.setSerial_number(rs.getString("serial_number"));
				mc.setData_di_nascita(rs.getDate("data_di_nascita"));
				mc.setComune(rs.getString("comune"));
				mc.setCognome_proprietario(rs.getString("cognome_proprietario"));
				mc.setNome_proprietario(rs.getString("nome_proprietario"));
				mc.setData_esame1(rs.getDate("data_esame1"));	
				mc.setNotes(rs.getString("notes"));
				mc.setCodice_fiscale(rs.getString("codice_fiscale"));
				mc.setIndirizzo(rs.getString("indirizzo"));
				mc.setTipo_esame1(rs.getString("tipo_esame1"));
			 	mc.setEsito_esame1(rs.getInt("esito_esame1"));
				mc.setPassaporto(rs.getString("passaporto"));
				mc.setRazza(rs.getInt("razza"));
				mc.setMantello(rs.getInt("mantello"));
				mc.setTaglia(rs.getInt("taglia"));
				coll.add(mc);
			}
			TableFacade tf = TableFacadeFactory.createTableFacade( "18", context.getRequest());
			tf.setEditable(true);
			
			Worksheet worksheet = tf.getWorksheet();
			if(worksheet!=null)
			{
			if (worksheet.isSaving() || worksheet.hasChanges())
			{ 
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);

				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
			
				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update mc_non_standatd set ";
					valoriAggiornati=new HashMap<String, String>();
				
					UniqueProperty u =new UniqueProperty("serial_number",s);

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
					
					if(s.equals(""))
						s="-1";
					PreparedStatement pst2=db.prepareStatement("select * from mc_non_standard where serial_number='"+s+"'");
					ResultSet rs2=pst2.executeQuery();
					if(!rs2.next()){
						
						
						McNonStandard dist=new McNonStandard();
						int flag=0;
						while(it.hasNext()){
							
							
							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);
						
//							tf.getTable().getRow().getColumn( "notes" ).setTitle( "Note" );
							if(kiaveNomeColonna.equals("serial_number")){
								dist.setSerial_number(valore);
								
							}else{
								if(kiaveNomeColonna.equals("comune")){
									dist.setComune(valore);
									
								}else{
									if(kiaveNomeColonna.equals("razza")){
									
										
									}else{
										if(kiaveNomeColonna.equals("mantello")){
											dist.setIndirizzo(valore);
											
										}else{
											if(kiaveNomeColonna.equals("taglia")){
												
											}else{
												if(kiaveNomeColonna.equals("data_di_nascita")){
													SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
													java.util.Date d=ss.parse(valore);
													dist.setData_di_nascita(new java.sql.Date(d.getTime()));
													
												}else{
													if(kiaveNomeColonna.equals("passaporto")){
														
														dist.setPassaporto(valore);
													}else{
														if(kiaveNomeColonna.equals("nome_proprietario")){
														
															dist.setNome_proprietario(valore);
														}else{
															if(kiaveNomeColonna.equals("cognome_proprietario")){
																dist.setCognome_proprietario(valore);
																
															}else{
																if(kiaveNomeColonna.equals("codice_fiscale")){

																	

																dist.setCodice_fiscale(valore);
																}
																else{
																	if(kiaveNomeColonna.equals("indirizzo")){
																		dist.setIndirizzo(valore);
																		
																	}
																	else{
																		if(kiaveNomeColonna.equals("comune")){
																			dist.setComune(valore);
																		}
																		else{
																			if(kiaveNomeColonna.equals("tipo_esame1"))
																			{
																				dist.setTipo_esame1(valore);
																			}
																			else{
																				if(kiaveNomeColonna.equals("tipo_esame2"))
																				{
																					}
																				else {
																					if(kiaveNomeColonna.equals("esito_esame1"))
																					{
																						dist.setEsito_esame1(Integer.valueOf(valore));
																					}
																					else {
																						if(kiaveNomeColonna.equals("esito_esame2"))
																						{
																						}
																						
																						else {
																							if(kiaveNomeColonna.equals("data_esame1"))
																							{
																								SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
																								java.util.Date d=ss.parse(valore);
																								dist.setData_esame1(new java.sql.Date(d.getTime()));
																							}
																							else {
																								if(kiaveNomeColonna.equals("data_esame2")){
																									
																								}
																								else{
																									if(kiaveNomeColonna.equals("notes")){
																										dist.setNotes(valore);
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																		
																	}
																
															}
																
															}
														}
													}
												}
											}
										}
									}
								}
								
							}



						}
					
						//dist.setAlimentoDistribuito(Integer.parseInt(context.getRequest().getParameter("alimentiDistribuiti_")));
						dist.insert(db);
						
						rs2.close();
						pst2.close();
					}else{
					
					
					
					int tipo=-1;
					int flag=0;
					while(it.hasNext()){
						if(c!=0){
							query+=",";
						}
						
						String kiaveNomeColonna=it.next();
						String valore=valoriAggiornati.get(kiaveNomeColonna);

						if(kiaveNomeColonna.equals("data")){

							SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
							java.util.Date d=ss.parse(valore);

							query=query+""+kiaveNomeColonna+"=to_date('"+valore+"','dd/MM/yyyy') ";
						}else
							query=query+""+kiaveNomeColonna+"='"+valore+"' ";
c++;

					}
					
					query=query+" where "+uniquePropertyName+"='"+s+"'";
					if(c!=0){
					
					
						
					PreparedStatement pst=db.prepareStatement(query);
					pst.execute();
					pst.close();
					
						
				}
	
					}

				}

			}
			}
			
			
			tf.setItems( rsdc.getRows());
			tf.setColumnProperties( 
					"serial_number", "razza", "mantello", "taglia", "data_di_nascita",
					"passaporto", "nome_proprietario", "cognome_proprietario", 
					"codice_fiscale", "indirizzo","comune",
					"tipo_esame1", "data_esame1","esito_esame1", 
					"tipo_esame2","data_esame2","esito_esame2","notes"
			);
			tf.setStateAttr("restore");		
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
			
			row.setUniqueProperty("serial_number");
			tf.getTable().getRow().getColumn( "serial_number" ).setTitle( "Microchip" );
			tf.getTable().getRow().getColumn( "razza" ).setTitle( "Razza" );
			tf.getTable().getRow().getColumn( "mantello" ).setTitle( "Mantello" );
			tf.getTable().getRow().getColumn( "taglia" ).setTitle( "Taglia" );
			tf.getTable().getRow().getColumn( "data_di_nascita" ).setTitle( "Data di nascita" );
			tf.getTable().getRow().getColumn( "passaporto" ).setTitle( "Passaporto" );
			tf.getTable().getRow().getColumn( "nome_proprietario" ).setTitle( "Nome Proprietario" );
			tf.getTable().getRow().getColumn( "cognome_proprietario" ).setTitle( "Cognome Proprietario" );
			tf.getTable().getRow().getColumn( "codice_fiscale" ).setTitle( "Codice fiscale" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf.getTable().getRow().getColumn( "tipo_esame1" ).setTitle( "Tipo esame" );
			tf.getTable().getRow().getColumn( "data_esame1" ).setTitle( "Data Esame" );
			tf.getTable().getRow().getColumn( "esito_esame1" ).setTitle( "Esito Esame" );
			tf.getTable().getRow().getColumn( "tipo_esame2" ).setTitle( "Tipo Esame" );
			tf.getTable().getRow().getColumn( "data_esame2" ).setTitle( "Data Esame" );
			tf.getTable().getRow().getColumn( "esito_esame2" ).setTitle( "Esito Esame" );
			tf.getTable().getRow().getColumn( "notes" ).setTitle( "Note" );
		
			
			Limit limit = tf.getLimit();
			if( limit.isExported() )
			{
				tf.render();
				return "ListOK";
		
			}
			else
			{
						
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("serial_number");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("razza");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("mantello");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("taglia");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_di_nascita");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("passaporto");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("nome_proprietario");
				cg.setFilterable( true );
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cognome_proprietario");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("codice_fiscale");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame1");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame1");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame1");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame2");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame2");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame2");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("notes");
				cg.setFilterable( false );
		
				ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
				item7.setTooltip( "Filtra" );
				tf.getToolbar().addToolbarItem( item7 );

				ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
				item8.setTooltip( "Resetta Filtro" );
				tf.getToolbar().addToolbarItem( item8 );

				ToolbarItem item18 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createPrevPageItem();
				item18.setTooltip( "Scorri pagina indietro" );
				tf.getToolbar().addToolbarItem( item18 );
				

				ToolbarItem item17 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createNextPageItem();
				item17.setTooltip( "Scorri pagina in avanti" );
				tf.getToolbar().addToolbarItem( item17 );
			}
	String tabella = tf.render();
	context.getRequest().setAttribute( "tabella", tabella );

			
	 }
	 catch(Exception e){
		//    logger.severe("[CANINA] - EXCEPTION nella action executeCommandList della classe Accounts");
		 	e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
	 }
	 finally{
		 //chiusura della connesisone
			this.freeConnection(context, db);
			System.gc();
	 }
	  
			return "ListOk";
	}
	

	 public String executeCommandAdd(ActionContext context){
		//controllo sui permessi
		 if ( !hasPermission(context, "mcNonStandard-view") )
			{
			      return ("PermissionError");
			}

		  SystemStatus systemStatus = this.getSystemStatus(context);
		  RowSetDynaClass	rsdc			= null;
		try{
			//connessione al db
			db = this.getConnection(context);
			Collection <McNonStandard> coll=mc;
			coll.removeAll(coll);
			//recupero tutti i microchip
			PreparedStatement	stat	= db.prepareStatement( "select * from mc_non_standard" );
			ResultSet			rs		= stat.executeQuery();
			
			while(rs.next())
			{
				McNonStandard mc=new McNonStandard();
				mc.setSerial_number(rs.getString("serial_number"));
				mc.setData_di_nascita(rs.getDate("data_di_nascita"));
				mc.setComune(rs.getString("comune"));
				mc.setCognome_proprietario(rs.getString("cognome_proprietario"));
				mc.setNome_proprietario(rs.getString("nome_proprietario"));
				mc.setData_esame1(rs.getDate("data_esame1"));	
				mc.setNotes(rs.getString("notes"));
				mc.setCodice_fiscale(rs.getString("codice_fiscale"));
				mc.setIndirizzo(rs.getString("indirizzo"));
				mc.setTipo_esame1(rs.getString("tipo_esame1"));
			    mc.setEsito_esame1(rs.getInt("esito_esame1"));
				mc.setPassaporto(rs.getString("passaporto"));
				mc.setRazza(rs.getInt("razza"));
				mc.setMantello(rs.getInt("mantello"));
				
				coll.add(mc);
			}
			rsdc	= new RowSetDynaClass(rs);
			TableFacade tf = TableFacadeFactory.createTableFacade("18", context.getRequest());
			tf.setEditable(true);
			Worksheet worksheet = tf.getWorksheet();
			
			if(worksheet!=null)
			{
			if (worksheet.isSaving() || worksheet.hasChanges())
			{ 
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);

				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
			
				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update mc_non_standard set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("serial_number",s);

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
					
					if(s.equals(""))
						s="-1";
					PreparedStatement pst2=db.prepareStatement("select * from mc_non_standard where serial_number='"+s+"'");
					ResultSet rs2=pst2.executeQuery();
					if(!rs2.next()){
						
						
						McNonStandard dist=new McNonStandard();
						int flag=0;
						while(it.hasNext()){
							
							
							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);
						
							tf.getTable().getRow().getColumn( "notes" ).setTitle( "Note" );
							if(kiaveNomeColonna.equals("serial_number")){
								dist.setSerial_number(valore);
								
							}else{
								if(kiaveNomeColonna.equals("comune")){
									dist.setComune(valore);
									
								}else{
									if(kiaveNomeColonna.equals("razza")){
										
										
									}else{
										if(kiaveNomeColonna.equals("mantello")){
											
										}else{
											if(kiaveNomeColonna.equals("taglia")){
												
											}else{
												if(kiaveNomeColonna.equals("data_di_nascita")){
													SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
													java.util.Date d=ss.parse(valore);
													dist.setData_di_nascita(new java.sql.Date(d.getTime()));
													
												}else{
													if(kiaveNomeColonna.equals("passaporto")){
														
														dist.setPassaporto(valore);
													}else{
														if(kiaveNomeColonna.equals("nome_proprietario")){
														
															dist.setNome_proprietario(valore);
														}else{
															if(kiaveNomeColonna.equals("cognome_proprietario")){
																dist.setCognome_proprietario(valore);
																
															}else{
																if(kiaveNomeColonna.equals("codice_fiscale")){
																dist.setCodice_fiscale(valore);
																}
																else{
																	if(kiaveNomeColonna.equals("indirizzo")){
																		dist.setIndirizzo(valore);
																		
																	}
																	else{
																		if(kiaveNomeColonna.equals("comune")){
																			dist.setComune(valore);
																		}
																		else{
																			if(kiaveNomeColonna.equals("tipo_esame1"))
																			{
																				dist.setTipo_esame1(valore);
																			}
																			else{
																				if(kiaveNomeColonna.equals("tipo_esame2"))
																				{
																				}
																				else {
																					if(kiaveNomeColonna.equals("esito_esame1"))
																					{
																						dist.setEsito_esame1(Integer.valueOf(valore));
																					}
																					else {
																						if(kiaveNomeColonna.equals("esito_esame2"))
																						{
																						}
																						
																						else {
																							if(kiaveNomeColonna.equals("data_esame1"))
																							{
																								SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
																								java.util.Date d=ss.parse(valore);
																								dist.setData_esame1(new java.sql.Date(d.getTime()));
																							}
																							else {
																								if(kiaveNomeColonna.equals("data_esame2")){
																					
																								}
																								else{
																									if(kiaveNomeColonna.equals("notes")){
																										dist.setNotes(valore);
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																		
																	}
																
															}
																
															}
														}
													}
												}
											}
										}
									}
								}
								
							}



						}
					
						//dist.setAlimentoDistribuito(Integer.parseInt(context.getRequest().getParameter("alimentiDistribuiti_")));
						dist.insert(db);
						
						
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

					
						if(kiaveNomeColonna.equals("data")){

							SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
							java.util.Date d=ss.parse(valore);

							query=query+""+kiaveNomeColonna+"=to_date('"+valore+"','dd/MM/yyyy') ";
						}else

							query=query+""+kiaveNomeColonna+"='"+valore+"' ";

c++;

					}
					
					query=query+" where "+uniquePropertyName+"='"+s+"'";
					if(c!=0){
					
					
						
					PreparedStatement pst=db.prepareStatement(query);
					pst.execute();
					pst.close();
					
						
				}
	
					}

				}

			}
			}
		
			coll.add(new McNonStandard("", -1, -1, -1, null, "", "","","", "","","","", 0, null));
			tf.setItems( coll);
			tf.setColumnProperties( 
					"serial_number", "razza", "mantello", "taglia", "data_di_nascita",
					"passaporto", "nome_proprietario", "cognome_proprietario", 
					"codice_fiscale", "indirizzo","comune",
					"tipo_esame1", "data_esame1","esito_esame1", 
					"tipo_esame2","data_esame2","esito_esame2","notes"
			);
			tf.setStateAttr("restore");		
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
			
			row.setUniqueProperty("serial_number");
					
			
			 // the unique worksheet properties to identify the row
			
			tf.getTable().getRow().getColumn( "serial_number" ).setTitle( "Microchip" );
			tf.getTable().getRow().getColumn( "razza" ).setTitle( "Razza" );
			tf.getTable().getRow().getColumn( "mantello" ).setTitle( "Mantello" );
			tf.getTable().getRow().getColumn( "taglia" ).setTitle( "Taglia" );
			tf.getTable().getRow().getColumn( "data_di_nascita" ).setTitle( "Data di nascita" );
			tf.getTable().getRow().getColumn( "passaporto" ).setTitle( "Passaporto" );
			tf.getTable().getRow().getColumn( "nome_proprietario" ).setTitle( "Nome Proprietario" );
			tf.getTable().getRow().getColumn( "cognome_proprietario" ).setTitle( "Cognome Proprietario" );
			tf.getTable().getRow().getColumn( "codice_fiscale" ).setTitle( "Codice fiscale" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf.getTable().getRow().getColumn( "tipo_esame1" ).setTitle( "Tipo esame" );
			tf.getTable().getRow().getColumn( "data_esame1" ).setTitle( "Data Esame" );
			tf.getTable().getRow().getColumn( "esito_esame1" ).setTitle( "Esito Esame" );
			tf.getTable().getRow().getColumn( "tipo_esame2" ).setTitle( "Tipo Esame" );
			tf.getTable().getRow().getColumn( "data_esame2" ).setTitle( "Data Esame" );
			tf.getTable().getRow().getColumn( "esito_esame2" ).setTitle( "Esito Esame" );
			tf.getTable().getRow().getColumn( "notes" ).setTitle( "Note" );
		
			Limit limit = tf.getLimit();
			if( limit.isExported() )
			{
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("serial_number");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg.setEditable(true);
				cg.setFilterable(false);
				
		
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("razza");
				cg.setEditable(true);
				cg.setFilterable(true);
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("mantello");
				cg.setEditable(false);
				cg.setFilterable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("taglia");
				cg.setEditable(false);
				cg.setFilterable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_di_nascita");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("passaporto");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("nome_proprietario");
				cg.setEditable(true);
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cognome_proprietario");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("codice_fiscale");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame1");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame1");
				cg.setEditable(true);
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame1");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame2");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame2");
				cg.setEditable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame2");
				cg.setEditable(true);
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("notes");
				cg.setEditable(true);

			}
			
			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );
			}
		catch(Exception e){
		//	logger.severe("[CANINA] - EXCEPTION nella action executeCommandAdd della classe Accounts");
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{ //chiusura della connesisone
			this.freeConnection(context, db);
			System.gc();
		}
		String action = context.getRequest().getParameter("action");
		return "ListOk";
	 }
}


