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
import java.util.List;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;

import org.aspcfs.modules.mcnonstandard.base.McNonStandard;

import org.aspcfs.modules.accounts.base.Organization;
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

public final class McNonStandardList extends CFSModule
{
		public String executeCommandDefault(ActionContext context)
	{

			if(context.getRequest().getParameter("command")==null){


		return executeCommandList(context);

			}else
				return executeCommandAdd(context);
		
	}

	String org_id="-1";
	Connection db=null;


	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "mcNonStandard-add")) {
			return ("PermissionError");
		}

		context.getRequest().setAttribute("add","add");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			context.getRequest().setAttribute("aggiunto", "true");

			String scroll = (String) context.getRequest().getParameter("scroll");
			context.getRequest().setAttribute( "scroll", scroll );
			
			db = this.getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from mc_non_standard " );
			ResultSet			rs		= stat.executeQuery();

			TableFacade tf = TableFacadeFactory.createTableFacade("15", context.getRequest());
			tf.setEditable(true);
			Worksheet worksheet = tf.getWorksheet();
			Collection<WorksheetRow> colle=worksheet.getRows();

			Collection<McNonStandard> coll=	new ArrayList<McNonStandard>();
		
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
			
			coll.add(new McNonStandard("",-1,-1,-1,null,"","","","","","","","",0,null));

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

			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("serial_number");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg.setEditable(true);
				cg.setFilterable(true);
		
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("razza");
				cg.setFilterable(false);
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
				//creazione della colonna relativa la mantello tramite la lookup
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("mantello");
				cg.setFilterable(false);
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
				cg.setEditable(false);
				cg.setFilterable( true );
				cg.getCellRenderer().setCellEditor( 
						
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								LookupList taglia = null;
								try {
									taglia= new LookupList(db,"lookup_asset_vendor");
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
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("passaporto");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("nome_proprietario");
				cg.setEditable(true);
				cg.setFilterable(true);
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cognome_proprietario");
				cg.setEditable(true);
				cg.setFilterable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("codice_fiscale");
				cg.setEditable(true);
				cg.setFilterable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setEditable(true);
				cg.setFilterable(true);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame1");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame1");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame1");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_esame2");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data_esame2");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("esito_esame2");
				cg.setEditable(true);
				cg.setFilterable(false);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("notes");
				cg.setEditable(true);
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
			
			PreparedStatement	stat_cani	= db.prepareStatement( "select count(*) as numero from mc_non_standard");
			ResultSet			rs_cani		= stat_cani.executeQuery();
			int numeroCani=0;
			if(rs_cani.next())
				numeroCani=rs_cani.getInt(1);

			numeroCani=(numeroCani/15)+1;
			context.getRequest().setAttribute("numeroChip", numeroCani);

			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ListOk";
	}

	public String executeCommandList(ActionContext context) {
		if (!hasPermission(context, "mcNonStandard-view")) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
	
		try {

			//coonnessione al db
			db = getConnection(context);
		
			PreparedStatement	stat	= db.prepareStatement( "select * from mc_non_standard" );
			ResultSet			rs		= stat.executeQuery();

			//recupero dei microchip
			PreparedStatement	stat1	= db.prepareStatement( "select * from mc_non_standard");
			ResultSet			rs1		= stat1.executeQuery();
			ArrayList<String> listaMatricole=new ArrayList<String>();	
			while(rs1.next())
			{
	
				listaMatricole.add(rs1.getString("serial_number"));
	
			}
			String modificatipi="update mc_non_standard set razza=? ,mantello=?, taglia=? where serial_number=?";
			for(String s : listaMatricole){
			int i=1;
				
				String tipo=context.getRequest().getParameter("razza_"+s);
				String tipo2=context.getRequest().getParameter("mantello_"+s);
				String tipo3=context.getRequest().getParameter("taglia_"+s);

				PreparedStatement pst3=db.prepareStatement(modificatipi);
				if(tipo!=null){
					int razza=Integer.parseInt(tipo);
					pst3.setInt(i++, razza);
				}
				if(tipo2!=null){
				int mantello=Integer.parseInt(tipo2);
					pst3.setInt(i++, mantello);
				}
				if(tipo3!=null){
				int taglia=Integer.parseInt(tipo3);
					pst3.setInt(i++, taglia);
				}
				if((tipo!=null)||(tipo2!=null)||(tipo3!=null)){
				pst3.setString(i, s);
				pst3.execute();
				}
					
			}

			TableFacade tf = TableFacadeFactory.createTableFacade("15", context.getRequest());
			tf.setEditable(true);
			Worksheet worksheet = tf.getWorksheet();
			
			UniqueProperty uu =new UniqueProperty("serial_number","");
//			WorksheetRow row1= worksheet.getRow(uu);
//	
//					WorksheetColumn microcips =null;
//					Collection<WorksheetColumn> columns1 = row1.getColumns();
//					for (WorksheetColumn colonna : columns1) { 
//					if(colonna.getProperty().equals("serial_number"))
//					{
//						microcips = colonna;
//						String changedValue = colonna.getChangedValue();    
//						String nomeColonna=colonna.getProperty();

//						validateColumn(microcips, changedValue);   
//					}
//						 
//						
//						}
			
			//controllo sul dsalvataggio o sul cambiamento dei dati
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
						validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());
							
							break;    
						}

						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;
					
					if(s.equals(""))
						s="-1";
					PreparedStatement pst2=db.prepareStatement("" +
							"select * from mc_non_standard m,lookup_asset_manufacturer l,lookup_asset_status k,lookup_asset_vendor v where m.razza=l.code and m.mantello=k.code and v.code=m.taglia and serial_number='"+s+"'");
					ResultSet rs2=pst2.executeQuery();
					if(!rs2.next()){
						
						McNonStandard dist=new McNonStandard();
						int flag=0;
						while(it.hasNext()){
							
							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);
							
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
													//conversione della data di nascita
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
						//recupero razza
						String razza =context.getRequest().getParameter("razza_");
						dist.setRazza(Integer.parseInt(razza));
						//recupero mantello
						String mant =context.getRequest().getParameter("mantello_");
						dist.setMantello(Integer.parseInt(mant));
						//recupero taglia
						String taglia =context.getRequest().getParameter("taglia_");
						dist.setTaglia(Integer.parseInt(taglia));
						
						//inserimento dati nel db
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
						}
						else{

							query=query+""+kiaveNomeColonna+"='"+valore+"' ";

						}
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
			
			 Collection<McNonStandard> coll=	new ArrayList<McNonStandard>();
			 	stat	= db.prepareStatement( "select * from mc_non_standard" );
				rs		= stat.executeQuery();
							
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
			 
			 String aggiunto=context.getRequest().getParameter("aggiunto");
			 if(context.getRequest().getParameter("add")!=null){
					
					}
			tf.setItems( coll);
			tf.getWorksheet().removeAllChanges();

			tf.setColumnProperties( 
					"serial_number", "razza", "mantello", "taglia", "data_di_nascita",
					"passaporto", "nome_proprietario", "cognome_proprietario", 
					"codice_fiscale", "indirizzo","comune",
					"tipo_esame1", "data_esame1","esito_esame1", 
					"notes"
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
				tf.getTable().getRow().getColumn( "notes" ).setTitle( "Note" );

			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{

				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("serial_number");
				cg.setFilterable(true);
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("razza");
				cg.setEditable(false);
				cg.setFilterable(false);
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
									mantello = new LookupList(db,"lookup_asset_status");
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
				cg.setEditable(false);
				cg.setFilterable( false );
				cg.getCellRenderer().setCellEditor( 
						
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{
								LookupList taglia = null;
								try {
									taglia= new LookupList(db,"lookup_asset_vendor");
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
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("notes");
				cg.setFilterable( false );

			}

			ToolbarItem item7 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createFilterItem();
			item7.setTooltip( "Cerca" );
			tf.getToolbar().addToolbarItem( item7 );
			
			ToolbarItem item8 = ( new ToolbarItemFactoryImpl( tf.getWebContext(), tf.getCoreContext() ) ).createClearItem();
			item8.setTooltip( "Resetta il Filtro di Ricerca" );
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
			
			PreparedStatement	stat_veicoli	= db.prepareStatement( "select count(*) as numero from mc_non_standard");
			ResultSet			rs_veicoli		= stat_veicoli.executeQuery();
			int numeroCani=0;
			if(rs_veicoli.next())
				numeroCani=rs_veicoli.getInt(1);

			numeroCani=(numeroCani/15)+1;
			context.getRequest().setAttribute("numeroChip", numeroCani);
			
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		return "ListOk";
	}

	//validazione sui campi
	private String validateColumn(WorksheetColumn colonna, String changedValue) {


		String nomeColonna=colonna.getProperty();
		String errore="";
		
		if(nomeColonna.equals("data_di_nascita")){

			String changedValue1 = colonna.getChangedValue();    

			try{
				SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
				sd.parse(changedValue1);

			}catch(Exception e){
			//	logger.severe("[CANINA] - EXCEPTION nel metodo validateColumn della classe McNonstandardList");
				colonna.setError("Formato data Non Valido"); 
				errore="Formato data Non Valido usare il seguente formato : (gg/mm/aaaa)";
			}
			if(errore.equals(""))
				colonna.removeError();

		}
	else{
	if(nomeColonna.equals("data_esame1")){

			String changedValue1 = colonna.getChangedValue();    

			try{
				SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
				sd.parse(changedValue1);

			}catch(Exception e){
			//	logger.severe("[CANINA] - EXCEPTION nel metodo validateColumn della classe McNonstandardList");
				colonna.setError("Formato data Non Valido"); 
				errore="Formato data Non Valido usare il seguente formato : (gg/mm/aaaa)";
			}
			if(errore.equals(""))
				colonna.removeError();

		}
		else{
	if(nomeColonna.equals("data_esame2")){

			String changedValue1 = colonna.getChangedValue();    

			try{
				SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
				sd.parse(changedValue1);

			}catch(Exception e){
			//	logger.severe("[CANINA] - EXCEPTION nel metodo validateColumn della classe McNonstandardList");
				colonna.setError("Formato data Non Valido"); 
				errore="Formato data Non Valido usare il seguente formato : (gg/mm/aaaa)";
			}
			if(errore.equals(""))
				colonna.removeError();

		}
		else
		{
		if(nomeColonna.equals("serial_number")){
		String changedValue1 = colonna.getChangedValue();   
		if(changedValue1.equals(""))
		{
			colonna.setError("Il Campo micrichips deve essere valorizzato"); 
				errore="Il Campo micrichips deve essere valorizzato";
			}
			if(errore.equals("  "))
				colonna.removeError();
		
	}

	}}}
		return errore;

}
}

