/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc.rpm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.postgresql.Driver;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;

/************************************
 * AGGIORNA INFO RUOLO				*
 * SINCRONIZZA RUOLO CON ASSOCIATI  *
*************************************/

public class UpdateRoleAction extends GenericAction{
	RoleBean r;
	String db = null;	//DB DESTINAZIONE
	String d1 = null;	//NOME DB1
	String d2 = null;	//NOME DB2
	String n = null;	//NOME DEL RUOLO DA MODIFICARE
	String sync = null;	//SINCRONIZZAZIONE
	ArrayList <String> ruoli_ass = null;  //ELENCO DEI RUOLI ASSOCIATI
	
	//INFO CONNESSIONE DB METADATI
	private String meta_name = ApplicationProperties.getProperty( "DATABASE" );
	private String meta_ip = ApplicationProperties.getProperty( "IP" );
	private String meta_port = ApplicationProperties.getProperty( "PORT" );
	private String meta_user = ApplicationProperties.getProperty( "USERNAME" );
	private String meta_password = ApplicationProperties.getProperty( "PASSWORD" );
	
	@Override
	public void can() throws AuthorizationException {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() throws Exception {
		String save = req.getParameter("save");
		String delete = req.getParameter("delete");
		String add = req.getParameter("add");
		String home = req.getParameter("home");
		
		d1 = req.getParameter("DB1");
		d2 = req.getParameter("DB2");
		db = req.getParameter("DB");
		sync = req.getParameter("cb");
		n = req.getParameter("rn");
		
		r = new RoleBean();
		Db d = new Db();
		//INFO DB
		String id_db = d.getFieldValue(db, "id_db");
		String nomedb = d.getDBName(id_db);
		String tipo = d.getFieldValue(db, "tipo");
		String ip = d.getFieldValue(db, "ip");
		String utente = d.getFieldValue(db, "utente");
		String pass_utente = d.getFieldValue(db, "password_utente");
		String query = d.getQuery(id_db, "info_ruolo");
		String query2 = d.getQuery(id_db, "permessi_ruolo");
		getRoleInfo(n,nomedb,ip,utente,pass_utente,query,query2,tipo);	//INFO E PERMESSI RUOLO DA MODIFICARE
		ArrayList<String> perm = new ArrayList<String>();
		ArrayList<String> T = new ArrayList<String>();
				
		if (save!=null){ //salva cambiamenti al ruolo
			String nome = req.getParameter("role");
			String descr = req.getParameter("descr");
			String enabled = null,type = null,note = null;
			if (!tipo.equals("vam")){	//DB DESTINAZIONE TIPO CENTRIC
				enabled = req.getParameter("enabled");
				type = req.getParameter("role_type");
				note = req.getParameter("note");
				String v,a,e,del,trash;
				for (int i=0;i<r.getPerm().size()/4;i++){	//NUOVI VALORI DEI PERMESSI
					trash=req.getParameter("trash"+i);
					v=req.getParameter("t"+i);
					a=req.getParameter("u"+i);
					e=req.getParameter("v"+i);
					del=req.getParameter("z"+i);
					
					if (trash==null) T.add("false");
					else T.add("true");
					if (v==null) perm.add("false");
					else perm.add("true");
					if (a==null) perm.add("false");
					else perm.add("true");
					if (e==null) perm.add("false");
					else perm.add("true");
					if (del==null) perm.add("false");
					else perm.add("true");
				}
			}
			else {	//DB DESTINAZIONE TIPO VAM
				String read,write,trash;
				for (int i=0;i<r.getPerm().size();i++){	//NUOVI VALORI DEI PERMESSI
					trash=req.getParameter("trash"+i);
					read=req.getParameter("perm"+i);
					write=req.getParameter("perm"+i);
					if (trash==null) T.add("false");
					else T.add("true");
					if (read.equals("r")) perm.add("r");	
					if (write.equals("w")) perm.add("w");
				}
			}
						
			updateRoleAttribute(nome, descr, enabled, type, note);	//AGGIORNA ATTRIBUTI RUOLO
			if (perm.size()>0){ 
				updateRolePermission(perm,r.getPerm(),tipo,T);	//AGGIORNA PERMESSI
				
				if (sync!=null){ //AGGIORNA I PERMESSI DEI RUOLI ASSOCIATI
					getRuoliAssociati(id_db, String.valueOf(r.getIdr()));	//RICAVA I RUOLI ASSOCIATI
					int passo=1, index=0, index2=2;
					String info[], info2[], p_Ass;
					Boolean presente = false;
					String vaed [] = new String[4]; 		//CENTRIC View-Add-Edit-Delete
					String val = null;						//VAM     w / r
					if (!tipo.equals("vam")){
						passo=4;
						index=5;
						index2=3;
					}
					String trash = "false";
					int k=0;
					for (int i=0;i<ruoli_ass.size();i++){ //per ogni ruolo associato.....
						info = ruoli_ass.get(i).split(";");
						for (int j=0;j<r.getPerm().size();j=j+passo){
							info2 = r.getPerm().get(j).split(";"); //...controlla i permessi in comune 
							p_Ass = getPermessoAssociato(id_db,info[0],info2[index]);
							if (p_Ass!=null){ //...ed aggiornali
								presente = findPermesso(info[0],info[1],p_Ass); //ricerca p_ass nel ruolo di destinazione....
								if (!tipo.equals("vam")){
									vaed[0] = r.getPerm().get(j).split(";")[index2];
									vaed[1] = r.getPerm().get(j+1).split(";")[index2];
									vaed[2] = r.getPerm().get(j+2).split(";")[index2];
									vaed[3] = r.getPerm().get(j+3).split(";")[index2];
									k=j/4;
									trash = T.get(k);
								}
								else {
									val = r.getPerm().get(j).split(";")[index2];
									trash = T.get(j);
								}
								if (presente==false) //..... e aggiungi p_ass nel ruolo di destinazione
									add_updatePermesso(info[0],info[1],p_Ass,vaed,val,"add",trash); 
								else   //aggiorna p_ass nel ruolo di destinazione
									add_updatePermesso(info[0],info[1],p_Ass,vaed,val,"update",trash);
								trash="false";
							}
						}
					}
				}
			}
			res.sendRedirect("/guc/guc.rpm.MyConnectAction.us?db1="+d1+"&DB1="+d1+"&db2="+d2+"&DB2="+d2+"&check=CHECK");
		}

		if (delete!=null){ //elimina ruolo (DISABILITA)
			id_db = d.getFieldValue(db, "id_db");
			String nome = d.getDBName(id_db);
			tipo = d.getFieldValue(db, "tipo");
			ip = d.getFieldValue(db, "ip");
			utente = d.getFieldValue(db, "utente");
			pass_utente = d.getFieldValue(db, "password_utente");

			aggiornaMetadati(Integer.parseInt(id_db),r.getIdr()); //ELIMINA ASSOCIAZIONE DAI METADATI
			query = d.getQuery(id_db, "delete_role");
			deleteRole(ip,utente,pass_utente,query,nome,tipo);   
			res.sendRedirect("/guc/guc.rpm.MyConnectAction.us?db1="+d1+"&DB1="+d1+"&db2="+d2+"&DB2="+d2+"&check=CHECK");			
		}
		
		if (add!=null){ //aggiungi permessi al ruolo
			String nome = d.getDBName(id_db);
			query = d.getQuery(id_db, "perm_db");
			ArrayList<String> permessi = new ArrayList<String>();
			try{ 
				Statement statement;
				ResultSet rs;			
				String driverName = "org.postgresql.Driver";
				String databaseURL = "jdbc:postgresql://"+ip+"/"+nome;
				Class driverClass = Class.forName(driverName);

				Driver driver = (Driver) driverClass.newInstance();
				Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
				
				String category, description,idp;
				statement = connection.createStatement();
				rs = statement.executeQuery(query);
				while(rs.next()){
					if (!tipo.equals("vam")){
						category=rs.getObject(1).toString();
						description=rs.getObject(2).toString();
						idp = rs.getObject(3).toString();
						permessi.add(category+" -- "+description+" -- "+idp);
					}
					else
						permessi.add(rs.getObject(1).toString());		
				}
				connection.close();
				rs.close();
				statement.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}			
			
			req.setAttribute("permessi", permessi);			
			req.setAttribute("RoleBean", r);
			if (!tipo.equals("vam"))
				gotoPage("/jsp/pages/aggiungi_permessi.jsp");
			else 
				gotoPage("/jsp/pages/aggiungi_permessi_vam.jsp");
		}
		
		if (home!=null){
			res.sendRedirect("/guc/guc.rpm.MyConnectAction.us?db1="+d1+"&DB1="+d1+"&db2="+d2+"&DB2="+d2+"&check=CHECK");			
		}
//		res.sendRedirect("http://:8080/guc/guc.rpm.MyConnectAction.us?db1="+d1+"&DB1="+d1+"&db2="+d2+"&DB2="+d2+"&check=CHECK");
	}

	/********************************************************
	 * AGGIORNA ATTRIBUTI RUOLO								*
	 * INPUT STRING NOME, DESCRIZIONE, ENABLED, TYPE, NOTE	*
	 * OUTPUT NULL											*
	 ********************************************************/
	private void updateRoleAttribute (String nome, String desc, String enab, String type, String note){
		Db d = new Db();
		//INFO DB
		String id_db = d.getFieldValue(db, "id_db");
		String nomedb = d.getDBName(id_db);
		String tipo = d.getFieldValue(db, "tipo");
		String ip = d.getFieldValue(db, "ip");
		String utente = d.getFieldValue(db, "utente");
		String pass_utente = d.getFieldValue(db, "password_utente");
		String query = d.getQuery(id_db, "update_role_attribute");
		
		try{
			PreparedStatement pst = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nomedb;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			long ms = System.currentTimeMillis();
			Timestamp t = new Timestamp(ms);

			query=query+r.getIdr();
			pst = connection.prepareStatement(query);
			pst.setObject(1, nome);
			pst.setObject(2, desc);
			if (!tipo.equals("vam")){  //CAMPI AGGIUNTIVI PER DB TIPO CENTRIC
				pst.setTimestamp(3, t);
				pst.setObject(4, Boolean.parseBoolean(enab));
				pst.setObject(5, Integer.parseInt(type));
				if(query.contains("note")){
					pst.setObject(6, note);
				}
			}
			pst.executeUpdate();
			
			connection.close();
			pst.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/********************************************
	 * AGGIORNA PERMESSI RUOLO					*
	 * INPUT ARRAYLIST NUOVI_VALORI_PERMESSI	*
	 * 		 ARRAYLIST VECCHI_VALORI_PERMESSI	*
	 * 		 STRING TIPO_DB(CENTRIC|VAM)		*
	 * 		 ARRAYLIST PERMESSI_DISABILITATI	*
	 * OUTPUT NULL								*
	 ********************************************/
	private void updateRolePermission (ArrayList<String> permMod, ArrayList<String> p1, String tipo, ArrayList<String> T){
		ArrayList <String> p2 = new ArrayList<String>();

		//COSTRUISCI ARRAYLIST CON I NUOVI VALORI DEI PERMESSI
		if (!tipo.equals("vam")){  //DB TIPO CENTRIC
			String split[];
			int k = 0;
			int cont = 0;
			for (int i=0;i<p1.size();i++){
				split=p1.get(i).split(";");
				if (T.get(k).equals("false")){ //Permesso non disabilitato
					if (split[3].equals("true")){	//Permesso originario (TRUE)
						if (permMod.get(i).equals("false")) //Permesso modificato (FALSE)
							p2.add(split[0]+";"+split[1]+";"+split[2]+";false;"+split[4]+";"+split[5]);
						else 
							p2.add(p1.get(i));
					}
					else{ //Permesso originario (FALSE)
						if (permMod.get(i).equals("true")) //Permesso modificato (TRUE)
							p2.add(split[0]+";"+split[1]+";"+split[2]+";true;"+split[4]+";"+split[5]);
						else 
							p2.add(p1.get(i));
					}
				}
				else{ //Permesso disabilitato (TUTTI FALSE)
					p2.add(split[0]+";"+split[1]+";"+split[2]+";false;"+split[4]+";"+split[5]);
				}
				cont++;
				if (cont==4){
					k++;
					cont=0;
				}
			}
		}
		else{	//DB TIPO VAM
			String split[];
			for (int i=0;i<p1.size();i++){	//AGGIORNA I VALORI DEI PERMESSI
				split=p1.get(i).split(";");
				if (T.get(i).equals("false")){ //Permesso non disabilitato
					if (split[2].equals("w")){ //Permesso originario
						if (permMod.get(i).equals("r"))  //Permesso modificato
							p2.add(split[0]+";"+split[1]+";r");
						else
							p2.add(p1.get(i));
					}
					else {
						if (permMod.get(i).equals("w"))
							p2.add(split[0]+";"+split[1]+";w");
						else
							p2.add(p1.get(i));
					}
				}
				else { //Permesso disabilitato (R)
					p2.add(split[0]+";"+split[1]+";r");
				}
			}
		}
		r.setPerm(p2);
		
		//INFO DB
		Db d = new Db();
		String id_db = d.getFieldValue(db, "id_db");
		String nomedb = d.getDBName(id_db);
		String ip = d.getFieldValue(db, "ip");
		String utente = d.getFieldValue(db, "utente");
		String pass_utente = d.getFieldValue(db, "password_utente");
		String query = d.getQuery(id_db, "update_role_permission");

		try{
			PreparedStatement pst = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nomedb;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
			String type [] = {"role_view","role_add","role_edit","role_delete"};
			String idpextract [];
			int j=0;
			int k = 0;

			for(int i=0;i<permMod.size();i++){
				idpextract = p2.get(i).split(";");
				if (!tipo.equals("vam")){	//DB TIPO CENTRIC
					query=query.replace("NOMECAMPO", type[j]);
					pst = connection.prepareStatement(query); 
					if (T.get(k).equals("false"))
						pst.setBoolean(1, Boolean.parseBoolean(permMod.get(i)));
					else
						pst.setBoolean(1, false);
					pst.setInt(2, Integer.parseInt(idpextract[4]));
					pst.executeUpdate();
					query=query.replace(type[j],"NOMECAMPO");
					j++;
					if (j==4){ j=0; k++;}
				}
				else{	//DB TIPO VAM
					pst = connection.prepareStatement(query);
					if (T.get(i).equals("false"))
						pst.setString(1, idpextract[2]);
					else 
						pst.setString(1, "r");
					pst.setInt(2, Integer.parseInt(idpextract[1]));
					pst.executeUpdate();
				}
			}
			connection.close();
			pst.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	} 

	/****************************************
	 * CANCELLA RUOLO (LOGICA)				*
	 * INPUT STRING IP, UTENTE, PASSWORD,	*
	 * 		 STRING QUERY, NOME_DB, 		*
	 * 		 STRING TIPO_DB(CENTRIC|VAM)	*
	 * OUTPUT NULL							*
	 ****************************************/
	private void deleteRole(String ip, String utente, String pass_utente, String query, String nome, String tipo){
		try{
			Statement statement;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nome;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			query = query+r.getIdr();
			statement = connection.createStatement();		
			statement.executeUpdate(query);

			connection.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/********************************************
	 * AGGIORNA DB METADATI DOPO CANCELLAZIONE	*
	 * INPUT STRING ID_DB, ID_RUOLO				*
	 * OUTPUT INT STATUT (OPERAZIONE ESEGUITA)	*
	 ********************************************/
	private int aggiornaMetadati(int d, int r){
		int status=0;
		try{
			Statement statement=null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			String query;
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			query = "DELETE FROM associazione_ruoli WHERE (id_db1="+d+" and id_r1="+r+") OR (id_db2="+d+" and id_r2="+r+")";			
			statement = connection.createStatement();
			status=statement.executeUpdate(query);

			connection.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	/************************************************
	 * INFO E PERMESSI RUOLO			 			*
	 * INPUT STRING NOME_RUOLO_DB1, 				*
	 * 		 STRING NOME_DB, IP, UTENTE, PASSWORD	*
	 * 		 STRING QUERY_INFO, QUERY_PERMESSI		*
	 * OUTPUT NULL									*
	 ************************************************/
	private void getRoleInfo(String role, String db, String ip, String utente, String pass_utente, String query, String query2, String tipo){
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			query=query+"'"+role+"'";
			statement = connection.createStatement();		
			rs = statement.executeQuery(query);

			//ATTRIBUTI DEL RUOLO
			String idr = null;
			String descr = null;
			if (!tipo.equals("vam")){	//DB TIPO CENTRIC
				String enabled = null;
				String role_type = null;
				String note = null;
				while(rs.next())
				{ 
					idr = rs.getObject(1).toString();
					descr=rs.getObject(2).toString();
					enabled=rs.getObject(3).toString();
					role_type=rs.getObject(4).toString();
					if(query.contains("note")){
						if (rs.getObject(5)==null) note="";
						else note=rs.getObject(5).toString();
					}
				}	
				r.setIdr(Integer.parseInt(idr));
				r.setRole(role);
				r.setDescr(descr);
				r.setEnabled(enabled);
				r.setRole_type(role_type);
				if (note!=null) r.setNote(note);
			}
			else{	//DB TIPO VAM
				while(rs.next())
				{ 
					idr = rs.getObject(1).toString();
					descr=rs.getObject(2).toString();
				}
				r.setIdr(Integer.parseInt(idr));
				r.setRole(role);
				r.setDescr(descr);
			}

			//PERMESSI DEL RUOLO
			ResultSet c;
			ArrayList<String> permessi = new ArrayList<String>();

			if (!tipo.equals("vam")){ //DB TIPO CENTRIC
				query2=query2+"'"+role+"' ORDER BY permission_category.category, permission.description";
				c = statement.executeQuery(query2);
				String category, description,idrp,view,add,edit,delete,idp;
				while(c.next()){
					idrp=c.getObject(1).toString();
					view=c.getObject(2).toString();
					add=c.getObject(3).toString();
					edit=c.getObject(4).toString();
					delete=c.getObject(5).toString();
					category=c.getObject(6).toString();
					description=c.getObject(7).toString();
					idp=c.getObject(8).toString();
					permessi.add(category+";"+description+";role_view;"+view+";"+idrp+";"+idp);
					permessi.add(category+";"+description+";role_add;"+add+";"+idrp+";"+idp);
					permessi.add(category+";"+description+";role_edit;"+edit+";"+idrp+";"+idp);
					permessi.add(category+";"+description+";role_delete;"+delete+";"+idrp+";"+idp);
				}
			}
			else { //DB TIPO VAM
				query2=query2+"'"+role+"' ORDER BY subject_name";
				c = statement.executeQuery(query2);
				String id, subject_name, value;
				while(c.next()){
					id = c.getObject(1).toString();
					subject_name = c.getObject(2).toString();
					value=c.getObject(3).toString();
					permessi.add(subject_name+";"+id+";"+value);
				}

			}
			
			r.setPerm(permessi);			
			
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/************************************************
	 * ELENCO RUOLI COLLEGATI AD UN ALTRO RUOLO		*
	 * INPUT STRING ID_DB, ID_RUOLO					*
	 * OUTPUT NULL									*
	 ************************************************/
	private void getRuoliAssociati(String id_db, String id_r){
		try{
			Statement statement=null;
			ResultSet rs;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			String query=null;
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			query = "SELECT id_db2, id_r2 FROM associazione_ruoli WHERE (id_db1="+id_db+" and id_r1="+id_r+")";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			ruoli_ass = new ArrayList<String>();
			
			while (rs.next()){
				ruoli_ass.add(rs.getObject(1).toString()+";"+rs.getObject(2).toString());
			}
			
			query = "SELECT id_db1, id_r1 FROM associazione_ruoli WHERE (id_db2="+id_db+" and id_r2="+id_r+")";
			rs = statement.executeQuery(query);
			while (rs.next()){
				ruoli_ass.add(rs.getObject(1).toString()+";"+rs.getObject(2).toString());
			}
			
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/********************************************
	 * PERMESSO ASSOCIATO						*
	 * INPUT STRING ID_DB1 ID_DB2, ID_PERMESSO	*
	 * OUTPUT STRING ID_PERMESSO_ASSOCIATO		*
	 ********************************************/
	private String getPermessoAssociato(String id_db1, String id_db2, String id_p){
		String associato = null;
		try{
			Statement statement=null;
			ResultSet rs;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			String query=null;
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			query = "SELECT id_p2 FROM associazione_permessi WHERE (id_db1="+id_db1+" and id_db2="+id_db2+" and id_p1='"+id_p+"')";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while (rs.next()){
				associato = rs.getString("id_p2");
			}
			
			query = "SELECT id_p1 FROM associazione_permessi WHERE (id_db1="+id_db2+" and id_db2="+id_db1+" and id_p2='"+id_p+"')";
			rs = statement.executeQuery(query);
			while (rs.next()){
				associato = rs.getString("id_p1");
			}
			
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return associato;
	}
	
	/****************************************************
	 * CONTROLLO ESISTENZA PERMESSO PER UN RUOLO		*
	 * INPUT STRING ID_DB, ID_RUOLO, ID_PERMESSO		*
	 * OUTPUT BOOLEAN TROVATO (TRUE=RUOLO HA PERMESSO)	*
	 ****************************************************/
	private Boolean findPermesso (String id_db, String idr, String p_Ass){
		Boolean presente=false;
		String nomedb,ip,utente,pass_utente,tipo,query,alias = null;
		int count=0;
		
		Db app = new Db();
		nomedb = app.getDBName(id_db);
		alias = app.getAlias(id_db);
		tipo = app.getFieldValue(alias, "tipo");
		ip = app.getFieldValue(alias, "ip");
		utente = app.getFieldValue(alias, "utente");
		pass_utente = app.getFieldValue(alias, "password_utente");
		
		if (!tipo.equals("vam"))
			query = "SELECT count(*) FROM role_permission WHERE role_id="+idr+" and permission_id="+p_Ass+"";
		else
			query = "SELECT count(*) FROM capability INNER JOIN permessi_ruoli on category_name=nome WHERE permessi_ruoli.id="+idr+" and subject_name='"+p_Ass+"'";
				
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nomedb;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
			
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()){
				count=rs.getInt("count");
			}
			if (count>0) 
				presente=true;
			
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return presente;
	}
	
	/************************************************
	 * AGGIORNA/INSERISCI PERMESSO AD UN RUOLO		*
	 * INPUT STRING ID_DB, ID_RUOLO, ID_PERMESSO,	*
	 * 		 STRING [] VALORI_PERMESSI (CENTRIC),	*
	 * 		 STRING VALORE_PERMESSO (VAM),			*
	 * 		 STRING OPERAZIONE (ADD|UPDATE)			*
	 * 		 STRING TRASH (PERMESSO DISABILITATO)	*
	 * OUTPUT NULL									*
	 ************************************************/
	private void add_updatePermesso(String id_db,String idr,String p_Ass, String [] vaed, String val, String op,String trash){
		String nomedb,ip,utente,pass_utente,tipo,query,alias = null;
		Db app = new Db();
		//INFO DB
		nomedb = app.getDBName(id_db);
		alias=app.getAlias(id_db);
		tipo = app.getFieldValue(alias, "tipo");
		ip = app.getFieldValue(alias, "ip");
		utente = app.getFieldValue(alias, "utente");
		pass_utente = app.getFieldValue(alias, "password_utente");
		if (op.equals("add"))
			query = app.getQuery(id_db, "insert_role_permission");
		else 
			query = app.getQuery(id_db, "update_perm_associato");
		
		try{
			PreparedStatement pst = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nomedb;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
			pst = connection.prepareStatement(query);

			if (!tipo.equals("vam")){  //DB DESTINAZIONE CENTRIC
				if (op.equals("add")){ //AGGIUNGI PERMESSO 
					pst.setInt(1, Integer.parseInt(idr));
					pst.setInt(2, Integer.parseInt(p_Ass));
					
					if (val!=null){ //DB ORIGINE VAM
						if (val.equals("w")){ //VALORE PERMESSO = W
							pst.setBoolean(3, true);
							pst.setBoolean(4, true);
							pst.setBoolean(5, true);
							pst.setBoolean(6, true);
						}
						else {	//VALORE PERMESSO = R
							pst.setBoolean(3, false);
							pst.setBoolean(4, false);
							pst.setBoolean(5, false);
							pst.setBoolean(6, false);
						}
						if (trash.equals("true")){ //PERMESSO DISABILITATO
							pst.setBoolean(3, false);
							pst.setBoolean(4, false);
							pst.setBoolean(5, false);
							pst.setBoolean(6, false);
						}
						
					}
					else { // DB ORIGINE CENTRIC
						pst.setBoolean(3, Boolean.parseBoolean(vaed[0])); //VIEW	
						pst.setBoolean(4, Boolean.parseBoolean(vaed[1])); //ADD
						pst.setBoolean(5, Boolean.parseBoolean(vaed[2])); //EDIT
						pst.setBoolean(6, Boolean.parseBoolean(vaed[3])); //DELETE
						if (trash.equals("true")){	//PERMESSO DISABILITATO
							pst.setBoolean(3, false);
							pst.setBoolean(4, false);
							pst.setBoolean(5, false);
							pst.setBoolean(6, false);
						}
					}
					if (query.contains("entered") && query.contains("modified")){ //CAMPI AGGIUNTIVI NON COMUNI A TUTTI I DB
						long ms = System.currentTimeMillis();
						Timestamp t = new Timestamp(ms);
						pst.setTimestamp(7, t);
						pst.setTimestamp(8, t);
					}
					if (query.contains("offline")){	//CAMPI AGGIUNTIVI NON COMUNI A TUTTI I DB
						pst.setBoolean(9, false);
						pst.setBoolean(10, false);
						pst.setBoolean(11, false);
						pst.setBoolean(12, false);
					}
					pst.executeUpdate();
				}
				else{   //AGGIORNA PERMESSO SU DB CENTRIC
					if (val!=null){ 	//DB ORIGINE VAM
						if (val.equals("w")){	//VALORE PERMESSO = W
							pst.setBoolean(1, true); 
							pst.setBoolean(2, true);
							pst.setBoolean(3, true);
							pst.setBoolean(4, true);
						}
						else if (val.equals("r")){	//VALORE PERMESSO = R
							pst.setBoolean(1, false); 
							pst.setBoolean(2, false);
							pst.setBoolean(3, false);
							pst.setBoolean(4, false);
						}
						if (trash.equals("true")){ //PERMESSO DISABILITATO
							pst.setBoolean(1, false);
							pst.setBoolean(2, false);
							pst.setBoolean(3, false);
							pst.setBoolean(4, false);
						}
					}
					else { //DB ORIGINE CENTRIC
						pst.setBoolean(1, Boolean.parseBoolean(vaed[0])); //VIEW
						pst.setBoolean(2, Boolean.parseBoolean(vaed[1])); //ADD
						pst.setBoolean(3, Boolean.parseBoolean(vaed[2])); //EDIT
						pst.setBoolean(4, Boolean.parseBoolean(vaed[3])); //DELETE
						if (trash.equals("true")){	//PERMESSO DISABILITATO
							pst.setBoolean(1, false);
							pst.setBoolean(2, false);
							pst.setBoolean(3, false);
							pst.setBoolean(4, false);
						}
					}
					pst.setInt(5, Integer.parseInt(idr));
					pst.setInt(6, Integer.parseInt(p_Ass));
					pst.executeUpdate();
				}
			}
			else { //DB DESTINAZIONE VAM
				String q1 = app.getQuery(id_db, "role_name");
				q1=q1+idr;
				String role_name=null;
				int max = 0;
				Statement s;
				ResultSet r;
				
				s = connection.createStatement(); //nome del ruolo
				r = s.executeQuery(q1);
				while (r.next()){
					role_name = r.getObject(1).toString();
				}
				
				r = s.executeQuery("SELECT max(id) FROM capability"); //id del permesso
				while (r.next()){
					max = r.getInt(1);
				}
				r.close();
				s.close();
				
				if (op.equals("add")){ //AGGIUNGI PERMESSO
					pst.setInt(1, (max+1));
					pst.setString(2, role_name);
					pst.setString(3, p_Ass);
					pst.executeUpdate();
					
					PreparedStatement pst2;
					pst2 = connection.prepareStatement("INSERT INTO capability_permission values (?,?)");
					pst2.setInt(1, max+1);
					if (val!=null)	//DB SORGENTE VAM ?
						pst2.setString(2, val);
					else 
						pst2.setString(2, "w");
					
					if (vaed[0].equals("false") && vaed[1].equals("false") && vaed[2].equals("false") && vaed[3].equals("false"))
						pst2.setString(2, "r");
					
					if (trash.equals("true"))  //PERMESSO DISABILITATO
						pst2.setString(2, "r");
					pst2.executeUpdate();
					pst2.close();
				} 
				else{ //AGGIORNA PERMESSO
					if (val!=null) //DB SORGENTE VAM ?
						pst.setString(1, val);
					else 
						pst.setString(1, "w");
					
					if (vaed[0].equals("false") && vaed[1].equals("false") && vaed[2].equals("false") && vaed[3].equals("false"))
						pst.setString(1, "r");
					
					if (trash.equals("true"))  //PERMESSO DISABILITATO
						pst.setString(1, "r");
										
					int id=-1;
					String q2="SELECT id FROM capability WHERE category_name='"+role_name+"' and subject_name='"+p_Ass+"'";
					s = connection.createStatement(); //capability_id
					r = s.executeQuery(q2);
					while (r.next()){
						id = r.getInt("id");
					}
					s.close();
					r.close();
										
					pst.setInt(2, id);
					pst.executeUpdate();
				}
			}
			connection.close();
			pst.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}