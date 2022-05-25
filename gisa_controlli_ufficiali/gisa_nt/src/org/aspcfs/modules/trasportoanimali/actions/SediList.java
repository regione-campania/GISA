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
import java.util.Collection;
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
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesa.worksheet.editor.HtmlWorksheetEditor;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public final class SediList extends CFSModule
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


			db = getConnection(context);

			PreparedStatement	stat	= db.prepareStatement( "select * from organization_sediveicoli where org_id="+newOrg.getId());
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
					String query="update organization_sediveicoli set ";
					valoriAggiornati=new HashMap<String, String>();

					UniqueProperty u =new UniqueProperty("id",s);

					WorksheetRow row= worksheet.getRow(u);

					Collection<WorksheetColumn> columns = row.getColumns();
					for (WorksheetColumn colonna : columns) { 
						String changedValue = colonna.getChangedValue();    
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

				}

			}

			
		
			
			
			tf.setItems( newOrg.getListaDistributori());


			tf.setColumnProperties(
					"comune", "indirizzo", "provincia", 
					"latitudine", "longitudine", "cap", "stato"
			);
			tf.setStateAttr("restore");					
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
			row.setUniqueProperty("comune"); // the unique worksheet properties to identify the row
			
			tf.getTable().getRow().getColumn( "comune" ).setTitle( "Comune" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "Indirizzo" );
			tf.getTable().getRow().getColumn( "provincia" ).setTitle( "Provincia" );
			tf.getTable().getRow().getColumn( "latitudine" ).setTitle( "Latitudine" );
			tf.getTable().getRow().getColumn( "longitudine" ).setTitle( "Longitudine" );
			tf.getTable().getRow().getColumn( "cap" ).setTitle( "Cap" );
			tf.getTable().getRow().getColumn( "stato" ).setTitle( "Stato" );



			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{



				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("id");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg.setFilterable( true );

				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("provincia");
				cg.setFilterable( true );
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("latitudine");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("longitudine");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cap");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("stato");
				cg.setFilterable( true );


			}

			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella2", tabella );
			
			
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

}


