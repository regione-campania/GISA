/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.distributori.actions;



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
import org.aspcfs.modules.distributori.base.Organization;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.test.SpringParametersAdapter;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesa.worksheet.editor.HtmlWorksheetEditor;

import com.darkhorseventures.framework.actions.ActionContext;
//import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public final class DistributoriList extends CFSModule
{

	public String executeCommandDefault(ActionContext context)
	{

			


		return executeCommandList(context);

			
		
	}

	String org_id="-1";
	Connection db=null;


	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-view")) {
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
			
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User
			

			String  org_id=context.getRequest().getParameter("orgId");


			db = getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from distributori_automatici ,lookup_tipo_distributore where alimenti_distribuiti=code and org_id="+newOrg.getId());
			ResultSet			rs		= stat.executeQuery();
		



			TableFacade tf = TableFacadeFactory.createTableFacade("15", context.getRequest());
			tf.setEditable(true);
			Worksheet worksheet = tf.getWorksheet();

	
			 Collection<Distrubutore> coll=	newOrg.getListaDistributori();
				coll.add(new Distrubutore("","","","","","","","",null,-1,""));
				
				tf.setItems( coll);

			tf.setColumnProperties( 
					"matricola", "data", "comune", "provincia", "indirizzo",
					"cap", "latitudine", "longitudine", "descrizioneTipoAlimenti","note","ubicazione"
			);
			tf.setStateAttr("restore");					
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
			row.setUniqueProperty("matricola"); // the unique worksheet properties to identify the row


			tf.getTable().getRow().getColumn( "matricola" ).setTitle( "matricola" );
			tf.getTable().getRow().getColumn( "data" ).setTitle( "data Installazione" );
			tf.getTable().getRow().getColumn( "comune" ).setTitle( "comune" );
			tf.getTable().getRow().getColumn( "provincia" ).setTitle( "provincia" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "indirizzo" );
			tf.getTable().getRow().getColumn( "cap" ).setTitle( "cap" );
			tf.getTable().getRow().getColumn( "latitudine" ).setTitle( "latitudine" );
			tf.getTable().getRow().getColumn( "longitudine" ).setTitle( "longitudine" );
			tf.getTable().getRow().getColumn( "descrizioneTipoAlimenti" ).setTitle( "Alimento Distribuito" );
			tf.getTable().getRow().getColumn( "note" ).setTitle( "note" );
			tf.getTable().getRow().getColumn( "ubicazione" ).setTitle( "ubicazione" );



			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{



				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("matricola");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				//cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("provincia");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cap");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("latitudine");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("longitudine");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("note");
				cg.setFilterable( false );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("ubicazione");
				cg.setFilterable( false );


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
	
	
	
		
	
	
	

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-view")) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {


			
			/**
			 * Contollo temporaneo 
			 */
		
			
			
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			

			String  org_id=context.getRequest().getParameter("orgId");


			db = getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from distributori_automatici ,lookup_tipo_distributore where alimenti_distribuiti=code and org_id="+temporgId);
			ResultSet			rs		= stat.executeQuery();
		



			TableFacade tf = TableFacadeFactory.createTableFacade("15", context.getRequest());
			tf.setEditable(true);
			Worksheet worksheet = tf.getWorksheet();

			if (worksheet.isSaving() || worksheet.hasChanges())
			{ 



				String uniquePropertyName = WorksheetUtils.getUniquePropertyName(worksheet);
				List<String> uniquePropertyValues = WorksheetUtils.getUniquePropertyValues(worksheet);



				HashMap<String, String> valoriAggiornati=null;

				for(String s : uniquePropertyValues){
					String query="update distributori_automatici set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("matricola",s);

					WorksheetRow row= worksheet.getRow(u);

					Collection<WorksheetColumn> columns = row.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();   
						changedValue = changedValue.replaceAll("'", "''");
						String nomeColonna=colonna.getProperty();

						validateColumn(colonna, changedValue);    
						if (colonna.hasError()) {  
							context.getRequest().setAttribute("errore", colonna.getError());
							
							continue;    }

						if(!colonna.hasError()){

							valoriAggiornati.put(nomeColonna, changedValue);

						}

					}

					java.util.Iterator<String> it=valoriAggiornati.keySet().iterator();
					int c=0;
					
					PreparedStatement pst2=db.prepareStatement("select * from distributori_automatici where matricola='"+s+"' and org_id="+org_id);
					ResultSet rs2=pst2.executeQuery();
					if(!rs2.next()){
						
						
						Distrubutore dist=new Distrubutore("","","","","","","","",null,-1,"");
						int flag=0;
						while(it.hasNext()){
							
							
							String kiaveNomeColonna=it.next();
							String valore=valoriAggiornati.get(kiaveNomeColonna);
							
							
							if(kiaveNomeColonna.equals("matricola")){
								dist.setMatricola(valore);
								
							}else{
								if(kiaveNomeColonna.equals("comune")){
									dist.setComune(valore);
									
								}else{
									if(kiaveNomeColonna.equals("provincia")){
										dist.setProvincia(valore);
										
									}else{
										if(kiaveNomeColonna.equals("indirizzo")){
											dist.setIndirizzo(valore);
											
										}else{
											if(kiaveNomeColonna.equals("cap")){
												dist.setCap(valore);
												
											}else{
												if(kiaveNomeColonna.equals("latitudine")){
													dist.setLatitudine(valore);
													
												}else{
													if(kiaveNomeColonna.equals("longitudine")){
														
														dist.setLongitudine(valore);
													}else{
														if(kiaveNomeColonna.equals("descrizioneTipoAlimenti")){
															if(valore.equals("Acqua e Bevande")){
																dist.setAlimentoDistribuito(1);
																flag=1;
															}
															else{
																if(valore.equals("Alimenti in Confezione")){
																	dist.setAlimentoDistribuito(2);
																	flag=1;
																}
																else{
																	if(valore.equals("Latte , caffe'")){
																		dist.setAlimentoDistribuito(3);
																		flag=1;
																	}
																	else{
																		if(valore.equals("Misto")){
																			dist.setAlimentoDistribuito(4);
																			flag=1;
																		}
																	}
																}
																
																
															}
																
																

															
														}else{
															if(kiaveNomeColonna.equals("note")){
																dist.setNote(valore);
																
															}else{
																if(kiaveNomeColonna.equals("data")){

																	SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
																	java.util.Date d=ss.parse(valore);

																dist.setData(d);
																}else{
																	if(kiaveNomeColonna.equals("ubicazione")){
																		dist.setUbicazione(valore);
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
					
						
						dist.insert(db, Integer.parseInt(org_id));
						
						
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

						
						if(kiaveNomeColonna.equals("descrizioneTipoAlimenti")){
							if(valore.equals("Acqua e Bevande")){
								tipo=1;
								flag=1;
							}
							else{
								if(valore.equals("Alimenti in Confezione")){
									tipo=2;
									flag=1;
								}
								else{
									if(valore.equals("Latte , caffe")){
										tipo=3;
										flag=1;
									}
									else{
										if(valore.equals("Misto")){
											tipo=4;
											flag=1;
										}
									}
								}
								
								
							}
								
								


							query=query+""+kiaveNomeColonna+"="+tipo;

						}
						
						

						if(kiaveNomeColonna.equals("data")){

							SimpleDateFormat ss=new SimpleDateFormat("dd/MM/yyyy");
							java.util.Date d=ss.parse(valore);

							query=query+""+kiaveNomeColonna+"=to_date('"+valore+"','dd/MM/yyyy') ";
						}else{


							query=query+""+kiaveNomeColonna+"='"+valore+"' ";



						}
c++;

					}
					
					query=query+" where "+uniquePropertyName+"='"+s+"' and org_id ="+org_id;
					if(c!=0){
					
					
						
					PreparedStatement pst=db.prepareStatement(query);
					pst.execute();
					pst.close();
					
						
				}

					
					
					
					}/*else{
						if(s.equals("")){
							
							Distrubutore ddd=new  Distrubutore("","","","","","","","",null,-1);
							ddd.insert(db, Integer.parseInt(org_id));
							
							
							
							
							
							
						}
						
					}*/
					
					
					
					
					

				}

			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User
			
			
		
			 Collection<Distrubutore> coll=	newOrg.getListaDistributori();
			
			
			tf.setItems( coll);
			tf.getWorksheet().removeAllChanges();

			tf.setColumnProperties( 
					"matricola", "data", "comune", "provincia", "indirizzo",
					"cap", "latitudine", "longitudine", "descrizioneTipoAlimenti","note","ubicazione"
			);
			tf.setStateAttr("restore");					
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
			row.setUniqueProperty("matricola"); // the unique worksheet properties to identify the row


			tf.getTable().getRow().getColumn( "matricola" ).setTitle( "matricola" );
			tf.getTable().getRow().getColumn( "data" ).setTitle( "data Installazione" );
			tf.getTable().getRow().getColumn( "comune" ).setTitle( "comune" );
			tf.getTable().getRow().getColumn( "provincia" ).setTitle( "provincia" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "indirizzo" );
			tf.getTable().getRow().getColumn( "cap" ).setTitle( "cap" );
			tf.getTable().getRow().getColumn( "latitudine" ).setTitle( "latitudine" );
			tf.getTable().getRow().getColumn( "longitudine" ).setTitle( "longitudine" );
			tf.getTable().getRow().getColumn( "descrizioneTipoAlimenti" ).setTitle( "Alimento Distribuito" );
			tf.getTable().getRow().getColumn( "note" ).setTitle( "note" );
			tf.getTable().getRow().getColumn( "ubicazione" ).setTitle( "ubicazione" );


			
			
			HtmlColumn cg1 = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
			cg1.getCellRenderer().setCellEditor( 
	        		new CellEditor()
	        		{	
	        			public Object getValue(Object item, String property, int rowCount)
	        			{
	        				LookupList alimenti = null;
							try {
								alimenti = new LookupList(db,"lookup_tipo_distributore");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        		
	        				String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
	        				String id	= (String) (new HtmlCellEditor()).getValue(item, "id", rowCount);
	        				temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
	        				return alimenti.getHtmlSelect("alimentidistribuiti", -1);
	        			}
	        		}
	        
	        	);
			

			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{



				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("matricola");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				//cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("data");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("provincia");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cap");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("latitudine");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("longitudine");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
				cg.setFilterable( false );
				cg.setEditable(false);

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("note");
				cg.setFilterable( false );
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("ubicazione");
				cg.setFilterable( false );


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





	private String validateColumn(WorksheetColumn colonna, String changedValue) {


		String nomeColonna=colonna.getProperty();
String errore="";
		if(nomeColonna.equals("data")){

			String changedValue1 = colonna.getChangedValue();    

			try{
				SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
				sd.parse(changedValue1);


			}catch(Exception e){
				colonna.setError("Formato data Non Valido"); 
			errore="Formato data Non Valido usare il seguente formato : (gg/mm/aaaa)";
			}
if(errore.equals(""))
	colonna.removeError();







		}
		return errore;
	}

	 public String executeCommandList( ActionContext context )
		{
			if ( !hasPermission(context, "aiequidi-view") )
			{
			      return ("PermissionError");
			}
			
			SystemStatus 	systemStatus	= this.getSystemStatus(context);
		    Connection		db				= null;
		    RowSetDynaClass	rsdc			= null;
		    
			try
			{
				db = getConnection(context);
				
				 UserBean utenteConnesso=(UserBean) context.getSession().getAttribute("User");
				  PreparedStatement	stat=null;
				  String sql = "select distributori_automatici.*,lookup_site_id.description as asl ,case when distributori_automatici.org_id >0 then 'pregresso'::text when distributori_automatici.id_stabilimento>0 then 'nuovo operatore' end as tipo_operatore, aslstab.description as asl_stab,organization.name, opu_operatore.ragione_sociale, " +
						  "organization.org_id,opu_stabilimento.id as id_stab " +
						  ",lookup_tipo_distributore.description as descrizione,asl1.code,asl1.description " +  
						  "from distributori_automatici  " +
						  "left join comuni on distributori_automatici.comune=comuni.comune  " +
						  "left join lookup_site_id asl1 on asl1.codiceistat =comuni.codiceistatasl   " +
						  "left join lookup_tipo_distributore on alimenti_distribuiti=lookup_tipo_distributore.code " +
						  "left join organization on distributori_automatici.org_id=organization.org_id  " +
						  "left join opu_stabilimento on distributori_automatici.id_stabilimento=opu_stabilimento.id " +
						  "left join lookup_site_id aslstab on aslstab.code = opu_stabilimento.id_asl " +
						  "left join opu_operatore on opu_operatore.id = opu_stabilimento.id_operatore " +
						  "left join lookup_site_id on distributori_automatici.org_id=organization.org_id and organization.site_id=lookup_site_id.code" ;
						  		 
						  			  stat	= db.prepareStatement( sql);
						  		 
						  		  

						  		
						  		  

						  		  ResultSet			rs		= stat.executeQuery();

						  		  int siteid=utenteConnesso.getSiteId();
						  		  ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();
						  		  while(rs.next()){
						  			  String matricola=rs.getString("matricola");
						  			  String comune=rs.getString("comune");
						  			 
						  			  int idAslAppartenenzaComune=-1;
						  			  String descrAslComune="";

						  				  idAslAppartenenzaComune=rs.getInt("code");
						  				  descrAslComune=rs.getString("description");
						  			  if(idAslAppartenenzaComune==-1)
						  			  {
						  				  idAslAppartenenzaComune = 16;
						  				  descrAslComune = "Fuori Asl";
						  			  }

						  			 int org_id=rs.getInt("org_id");
						  			 if (org_id <=0)
						  				 org_id =rs.getInt("id_stab");
						  			 
						  			 int id=rs.getInt("id");
						  			  String provincia=rs.getString("provincia");
						  			  String latitudine=rs.getString("latitudine");
						  			  String longitudine=rs.getString("longitudine");
						  			  String cap=rs.getString("cap");
						  			  String note=rs.getString("note");
						  			  String indirizzo=rs.getString("indirizzo");
						  			  Date data=rs.getDate("data");
						  			  String description=rs.getString("descrizione");
						  			  int alimentiDstribuiti=rs.getInt("alimenti_distribuiti");
						  			  String ubicazione=rs.getString("ubicazione");
						  			  Distrubutore dist=new Distrubutore(matricola,comune,indirizzo,cap,provincia,latitudine,longitudine,note,data,alimentiDstribuiti,ubicazione);
						  			  dist.setDescrizioneTipoAlimenti(description);
						  			  dist.setOrg_id(org_id);
						  			  dist.setAslMacchinetta(idAslAppartenenzaComune);
						  			  String asl=rs.getString("asl");
						  			  if (asl == null || "".equals(asl))
						  				  asl=rs.getString("asl_stab");
						  			  dist.setNomeImpresa(rs.getString("name"));
						  			  if (dist.getNomeImpresa()==null || ("").equals(dist.getNomeImpresa()))
						  			  {
						  				  dist.setNomeImpresa(rs.getString("ragione_sociale"));
						  				  dist.setTipoOperatoreOpu(1);
						  			  }
						  			  else
						  			  {
						  				  dist.setTipoOperatoreOpu(0);
						  			  }
						  			  dist.setId(id);
						  			  dist.setAslMacchinettaDesc(descrAslComune);
			 			  			  dist.setAsl(asl);
						  			  dist.setTipo_operatore(rs.getString("tipo_operatore"));
							  listaDistributori.add(dist);

		 				  //		}
						  //}
				  }



				  TableFacade tf = TableFacadeFactory.createTableFacade("15", context.getRequest());
				  
				  Worksheet worksheet = tf.getWorksheet();
				  tf.setItems(listaDistributori);
				  tf.setColumnProperties( 
						  "matricola", "data","ubicazione", "indirizzo", "comune", "provincia",
						  "cap","asl","aslMacchinettaDesc","descrizioneTipoAlimenti",  "note","nomeImpresa","tipo_operatore"
				  );
				tf.setStateAttr("restore");					
				HtmlRow row = (HtmlRow) tf.getTable().getRow();
				row.setUniqueProperty("matricola"); // the unique worksheet properties to identify the row
  

				tf.getTable().getRow().getColumn( "matricola" ).setTitle( "matricola" );
				tf.getTable().getRow().getColumn( "data" ).setTitle( "data Installazione" );
				tf.getTable().getRow().getColumn( "comune" ).setTitle( "comune" );
				tf.getTable().getRow().getColumn( "provincia" ).setTitle( "provincia" );
				tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "indirizzo" );
				tf.getTable().getRow().getColumn( "cap" ).setTitle( "cap" );
			

				tf.getTable().getRow().getColumn( "descrizioneTipoAlimenti" ).setTitle( "Alimento Distribuito" );
				tf.getTable().getRow().getColumn( "note" ).setTitle( "note" );
				tf.getTable().getRow().getColumn( "nomeImpresa" ).setTitle( "Nome Impresa" );
				tf.getTable().getRow().getColumn( "ubicazione" ).setTitle( "Ubicazione" );
				tf.getTable().getRow().getColumn( "asl" ).setTitle( "ASL Impresa" );
				  tf.getTable().getRow().getColumn( "aslMacchinettaDesc" ).setTitle( "ASL Macchinetta" );
				  tf.getTable().getRow().getColumn( "tipo_operatore" ).setTitle( "Tipo Impresa" );
				
		


				Limit limit = tf.getLimit();
				if(! limit.isExported() )
				{



					 HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("matricola");
					  cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
					  
					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_operatore");
					  cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
					  cg.setFilterable( true );
					  //cg.setFilterable( false );
					  cg.setWidth("55");
					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("data");
					  cg.setFilterable( true );

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
					  cg.setFilterable( true );

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("provincia");
					  cg.setFilterable( false );

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
					  cg.setFilterable( true );

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("cap");
				 	  cg.setFilterable( false );

					 

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
					  cg.setFilterable( false );
					  cg.setEditable(false);

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("note");
					  cg.setFilterable( false );

					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("nomeImpresa");
					  cg.setFilterable( true );

					  cg.getCellRenderer().setCellEditor( 
			  	        		new CellEditor()
			  	        		{	
			  	        			public Object getValue(Object item, String property, int rowCount)
			  	        			{
			  	        			
			  							
			  	        	
			  	        				String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
			  	        				String id	= (String) (new HtmlCellEditor()).getValue(item, "nomeImpresa", rowCount);
			  	        				String org_id=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
			  	        				String val=(String)(new HtmlCellEditor()).getValue(item, "id", rowCount);
			  	        				String tipo	= (String) (new HtmlCellEditor()).getValue(item, "tipo_operatore", rowCount);

			  	        		
			  	        				
			  	        				if ("pregresso".equalsIgnoreCase(tipo))
			  	        					return "<a href='Distributori.do?command=Details&orgId="+org_id+"&id="+val+"'>"+id+"</a>";
			  	        				else
			  	        					return "<a href='Distributori.do?command=DetailsOpu&stabId="+org_id+"&id="+val+"'>"+id+"</a>";
			  	        				
			  	        			}
			  	        		}
			  	        
			  	        	);
					  
					  
					  
					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("asl");
					  cg.setFilterable( true );
					  cg = (HtmlColumn) tf.getTable().getRow().getColumn("aslMacchinettaDesc");
					  cg.setFilterable( true );


				}

				
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );
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
			
			return "ListOKMacchinette";
		}
	
}


