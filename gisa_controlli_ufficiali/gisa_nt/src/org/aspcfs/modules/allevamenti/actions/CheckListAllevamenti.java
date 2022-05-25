/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.actions;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.aspcfs.checklist.action.Checklist;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.AuditChecklist;
import org.aspcfs.checklist.base.AuditChecklistType;
import org.aspcfs.modules.allevamenti.actions.AccountVigilanza;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.allevamenti.base.ControlliHtmlFields;
import org.aspcfs.modules.allevamenti.base.ModuloControllo;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.campioni.base.ModuloCampione;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.AuditReport;
import org.aspcfs.utils.web.CustomLookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class CheckListAllevamenti  extends CFSModule{
	
	Logger logger = Logger.getLogger("MainLogger");
	
	 public String executeCommandAdd(ActionContext context)
	  {
		 logger.info("AGGIUNTA DI NUOVA CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 if (!hasPermission(context, "checklist-checklist-add"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandAdd(context);
			
			db = this.getConnection(context);
			Organization organization	 = 	new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			organization.setAccountSize(context.getRequest().getParameter("accountSize"));
			context.getRequest().setAttribute("OrgDetails", organization);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		 return "checklistAllevamentiAdd";
	 
	  }
	 
	 
	 public String executeCommandSave(ActionContext context)
	  {
		 logger.info("SALVATAGGIO DELLA CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 if (!hasPermission(context, "checklist-checklist-add")) 
		 {
				return ("PermissionError");
		 }		
		 
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandSave(context);
			
			db = this.getConnection(context);
			Organization organization	 = 	new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", organization);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		 return "checklistAllevamentiSaveOk";
	  }
	 
	 public String executeCommandView(ActionContext context)
	 {
		 logger.info("DETTAGLIO CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 if (!hasPermission(context, "checklist-checklist-view")) 
		{
			return ("PermissionError");
		}
				
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandView(context);
			
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization 	orgTemp 		= 	(org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization 							organization	= 	new Organization(db, orgTemp.getOrg_id());
			
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		 return "checklistAllevamentiView";
	 }
	 
	 public String executeCommandModify(ActionContext context)
	 {
		 logger.info("MODIFICA CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandModify(context);
			
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
		freeConnection(context, db);
		}
		 return "checklistAllevamentiModify";
	 }
	 
	 public String executeCommandUpdate(ActionContext context)
	 {
		 logger.info("UPDATE CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandUpdate(context);
			
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (SQLException e) 
		{
			context.getResponse().setStatus(2012);
			
			context.getRequest().setAttribute("ErroreChecklist", "Errore");
			return executeCommandModify(context);
		}
		finally
		{
			freeConnection(context, db);
		}
		 return "checklistAllevamentiUpdate";
	 }
	 
	 public String executeCommandDelete(ActionContext context)
	 {
		 logger.info("DELETE CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 
		 if (!hasPermission(context, "checklist-checklist-delete")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandDelete(context);
			
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
		
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		 return ("checklistAllevamentiDelete");
	 }
	 
	 public String executeCommandUpdateCategoria(ActionContext context)
	 {
		 logger.info("AGGIORNA LA CATEGORIA RISCHIO PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
					
		 Connection db = null;
			
			String idControllo = context.getRequest().getParameter("idC");
			String orgId = context.getRequest().getParameter("orgId");
			
			try 
			{
				db = this.getConnection(context);
				
				//verifica possibilita chiusura CU
				Ticket thisTicket = new Ticket(db, Integer.parseInt(idControllo));
				String messaggioAllegatiSanzione = thisTicket.isControlloChiudibileAllegatiSanzione(db);
				if (messaggioAllegatiSanzione!=null && !messaggioAllegatiSanzione.equals("")){
					int flag = 6;
					context.getRequest().setAttribute("Chiudi",""+flag);
					context.getRequest().setAttribute("Messaggio",messaggioAllegatiSanzione);
					context.getRequest().setAttribute("id", idControllo);
					context.getRequest().setAttribute("orgId", orgId);
					org.aspcfs.modules.allevamenti.actions.AccountVigilanza AV = new org.aspcfs.modules.allevamenti.actions.AccountVigilanza();
					//return AV.executeCommandTicketDetails(context);
					return AV.executeCommandChiudiTutto(context);
				}
				
			Checklist c = new Checklist();
			c.executeCommandUpdateLivello(context);
			
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
		
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		AccountVigilanza AV = new AccountVigilanza();
		return AV.executeCommandChiudiTutto(context);
		//return ("checklistAllevamentiUpdateCategoria");
	 }
	 
	 public String executeCommandStampa(ActionContext context)
	 {
		 logger.info("STAMPA CHECKLIST PER Allevamenti. ACTION CHIAMATA : CheckListAllevamenti ");
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandITextReport(context);
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
			
			OutputStream out = context.getResponse().getOutputStream();
			AuditReport report = new AuditReport();
			

			Audit 							audit 				= 	(Audit) context.getRequest().getAttribute("Audit");
			ArrayList<CustomLookupList> 	checklistList 		= 	( ArrayList<CustomLookupList>) context.getRequest().getAttribute("checklistList");
			CustomLookupList 				checklistType 		=	(CustomLookupList) context.getRequest().getAttribute("typeList");
			ArrayList<AuditChecklistType> 	auditChecklistType 	=	(ArrayList<AuditChecklistType>) context.getRequest().getAttribute("auditChecklistType");
			ArrayList<AuditChecklist> 		auditChecklist 		=	( ArrayList<AuditChecklist>) context.getRequest().getAttribute("auditChecklist");
			report.setContext(context);
			report.generate( out, audit, organization, checklistList, checklistType,auditChecklist, auditChecklistType, db);
			out.flush();
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		
		 return ("-none-");
	 }
	 
	 public String executeCommandModificaListaRiscontro(ActionContext context) {
			
			Connection db = null;
				Logger logger = Logger.getLogger("MainLogger");
				try {
					
					db = this.getConnection(context);	
					String idControllo = context.getParameter("idControllo");
					String specie = context.getParameter("specie");
					
					
					int idSpecie = Integer.parseInt(specie); 
					
					if (idSpecie!=1211 && idSpecie!= 131 && idSpecie!= 122 && idSpecie!=1461)
						specie = "-2";
					
				//Dati finestra modale
		  		//ArrayList<CampioniHtmlFields> campi = new ArrayList<CampioniHtmlFields>();
		  		LinkedHashMap<String, ArrayList<ControlliHtmlFields>> campi = new LinkedHashMap<String, ArrayList<ControlliHtmlFields>>();
		  		
		  		String query = "select campi.*, mod.valore from controlli_html_fields campi "+ 
		  						" LEFT JOIN controlli_fields_value mod on mod.id_controlli_html_fields = campi.id and mod.id_controllo = ? " +
		  						" where specie_allevata = ? " +
		  						" and (enabled = true or enabled is null) "+
		  						" order by ordine_campo asc";

		  		PreparedStatement pst = db.prepareStatement(query);
		  		pst.setInt(1, Integer.parseInt(idControllo));
		  		pst.setInt(2, Integer.parseInt(specie));
		  		ResultSet rs = pst.executeQuery();
		  		while (rs.next())
		  		{
		  			ControlliHtmlFields campo = new ControlliHtmlFields();
		  			campo.buildRecord(db, rs);
		  			
		  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
		  				ArrayList<ControlliHtmlFields> campoArray = new ArrayList<ControlliHtmlFields>(); //Crea array vuoto
		  				campoArray.add(campo); //Aggiungi campo
		  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
		  			}
		  			else
		  				campi.get(campo.getNome_campo()).add(campo);
		  		}
		  		pst.close();
		  		
		  		ControlliHtmlFields c = new ControlliHtmlFields();
		  		LinkedHashMap<String, String> ris = c.costruisciHtmlDaHashMap(db, campi);
		  		context.getRequest().setAttribute("campiHash", ris);
		  	
				}catch (Exception e) {
		  			e.printStackTrace();
		  			context.getRequest().setAttribute("Error", e);
		  			return ("SystemError");
		  		} finally {
		  			this.freeConnection(context, db);
		  		}

				if (context.getRequest().getParameter("messaggioOk")!=null && !context.getRequest().getParameter("messaggioOk").equals("null"))
					context.getRequest().setAttribute("messaggioOk", context.getRequest().getParameter("messaggioOk"));
		  		return "ModificaListaRiscontroOk";
		}

		public String executeCommandUpdateListaRiscontro(ActionContext context) {

			int idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));
			int specie = Integer.parseInt(context.getRequest().getParameter("specie"));
			
			Connection db = null;
			try {
				db = this.getConnection(context);	
				ControlliHtmlFields newModulo = new ControlliHtmlFields();
				newModulo.gestisciUpdate(context, db, idControllo, specie);
				//newPnaa.update(db, idCampione);	
				}catch (Exception e) {
					e.printStackTrace();
					context.getRequest().setAttribute("Error", e);
					return ("SystemError");
				} finally {
					this.freeConnection(context, db);
				}
			context.getRequest().setAttribute("idControllo", String.valueOf(idControllo));
			context.getRequest().setAttribute("specie", specie);
			context.getRequest().setAttribute("messaggioOk", "Modifica dati modulo avvenuta con successo.");
			return executeCommandModificaListaRiscontro(context);
		}
		
		public static void insertListaRiscontro(Connection db,int idControllo, int specie, ActionContext context) {

			Logger logger = Logger.getLogger("MainLogger");

			disabilitaVecchiaListaRiscontro(db, idControllo, specie);
			
			PreparedStatement pst = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer();		
		
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			int user_id = user.getUserRecord().getId();
			
			boolean trovato = false ;
			try {
				
				// determino l'insieme delle colonne
				sql.append("SELECT id, nome_campo,multiple FROM controlli_html_fields where specie_allevata =? order by ordine_campo");
				pst = db.prepareStatement(sql.toString());
				pst.setInt(1, specie);
				rs = pst.executeQuery();
			
					sql = new StringBuffer();
					sql.append("INSERT INTO controlli_fields_value (id_controllo, id_specie, id_controlli_html_fields,id_utente_inserimento, valore) values (?,?, ?,?, ?) ");
			
					PreparedStatement pst2 = db.prepareStatement(sql.toString());
					while (rs.next())
					{
						trovato = true ;
						int idCampo = rs.getInt("id");
						String nomeCampo = rs.getString("nome_campo");
				
						
						pst2.setInt(1, idControllo);
						pst2.setInt(2, specie);
						pst2.setInt(3, idCampo);
						pst2.setInt(4, user_id);
						
						
							if (rs.getBoolean("multiple")==false)
							{
								pst2.setString(5, context.getParameter(nomeCampo));
								pst2.execute();
							}
							else
							{
								
								String[] valueSel = context.getRequest().getParameterValues(nomeCampo);
								for(int i = 0 ; i < valueSel.length; i++)
								{
									pst2.setString(5, valueSel[i]);
									pst2.execute();
								}
								
							}
					}
					
				} catch (Exception e) {
				e.printStackTrace();
				context.getRequest().setAttribute("Error", e);
	  			
			} finally {
			}
			
		}
		
		
		public static void disabilitaVecchiaListaRiscontro(Connection db,int idControllo, int specie) {
			
			PreparedStatement pst = null;
			String sql ="UPDATE controlli_fields_value SET enabled = false where id_controllo = ? and id_specie = ? and enabled = true";
			try {
				pst = db.prepareStatement(sql);
				pst.setInt(1, idControllo);
				pst.setInt(2, specie);
				pst.executeUpdate();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void riempiListaRiscontroHTMLRsitrutturato(int idCampione, int idSpecie, ModuloControllo modulo , Connection db){
			
			if (idSpecie!=1211 && idSpecie!= 131 && idSpecie!= 122 && idSpecie!=1461)
				idSpecie = -2;
			
			try {
				  	
			  	PreparedStatement pst = db.prepareStatement
						("SELECT * FROM controlli_fields_value join controlli_html_fields v2 on v2.id = id_controlli_html_fields  " +
								"WHERE id_controllo = ? and id_specie = ? and enabled = true");
				
				pst.setInt(1, idCampione);
				pst.setInt(2, idSpecie);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					if (modulo.getListaCampiModulo().get(rs.getString("nome_campo"))!= null)
					{
						HashMap<String, String> valori_x_nome_campo = modulo.getListaCampiModulo().get(rs.getString("nome_campo")) ;
						
						if (rs.getBoolean("multiple")==false)
							valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore"));
						else
							valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore"), rs.getString("valore"));

						modulo.getListaCampiModulo().put(rs.getString("nome_campo"), valori_x_nome_campo);
					}
					else
					{
						HashMap<String, String> valori_x_nome_campo = new HashMap<String, String>();
						
						if (rs.getBoolean("multiple")==false)
							valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore"));
						else
							valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore"), rs.getString("valore"));

						modulo.getListaCampiModulo().put(rs.getString("nome_campo"), valori_x_nome_campo);
					}
							
				}
					
				
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
	 
}