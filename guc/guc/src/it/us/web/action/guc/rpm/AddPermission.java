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
import java.util.ArrayList;

import org.postgresql.Driver;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;

/************************************
 * AGGIUNGE PERMESSI AD UN RUOLO 	*
*************************************/

public class AddPermission extends GenericAction {

	@Override
	public void can() throws AuthorizationException {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() throws Exception {
		String dbname = req.getParameter("DB");
		String nome_ruolo = req.getParameter("rn");		
		String N = req.getParameter("N");	//numero di permessi disponibili
		ArrayList<String> permessi = new ArrayList<String>();
		String save = req.getParameter("save");
		String home = req.getParameter("home");
		
		if (save!=null){
			//INFO DEL DB
			Db d = new Db();
			String id_db = d.getFieldValue(dbname, "id_db");
			String nome = d.getDBName(id_db);
			String ip = d.getFieldValue(dbname, "ip");
			String utente = d.getFieldValue(dbname, "utente");
			String pass_utente = d.getFieldValue(dbname, "password_utente");
			String tipo = d.getFieldValue(dbname, "tipo");
	
			//PRENDI VALORI DALLA JSP
			String add,cat,des,read,write,perm,val="w";
			String vaed [] = new String[4];
			for (int i=0;i<Integer.parseInt(N);i++){
				add=req.getParameter("S"+i);
				if (!tipo.equals("vam")){ //DB TIPO CENTRIC	
					cat = req.getParameter("cat"+i);		//categoria
					des = req.getParameter("des"+i);		//descrizione
					vaed [0] = req.getParameter("t"+i);		//view
					vaed [1] = req.getParameter("u"+i);		//add
					vaed [2] = req.getParameter("v"+i);		//edit
					vaed [3] = req.getParameter("z"+i);		//delete
					if (add!=null){
						for (int j=0;j<4;j++){
							if (vaed[j]!=null)
								vaed[j]="true";
							else
								vaed[j]="false";
						}
						permessi.add(cat+";"+des+";"+vaed[0]+";"+vaed[1]+";"+vaed[2]+";"+vaed[3]);
					}
				}
				else{ //DB TIPO VAM
					perm = req.getParameter("p"+i);			//funzione
					read = req.getParameter("perm"+i);		//read
					write = req.getParameter("perm"+i);		//write	
					if (add!=null){
						if ((read==null && write==null)) val="w";
						else if(read.equals("r")) val="r";
						else if(write.equals("w")) val="w";
						permessi.add(perm+";"+val);
					}
				}
			}
	
			//SCRIVI PERMESSI NEL DB
			if (permessi.size()>0){
				if (!tipo.equals("vam")){ // DB TIPO CENTRIC
					String q1 = d.getQuery(id_db, "idr");
					String idr=getInfo(nome,ip,utente,pass_utente,tipo,"idr",q1,nome_ruolo);	//recupera id del ruolo
	
					String q2 = d.getQuery(id_db, "idp");
					String q3 = d.getQuery(id_db, "insert_role_permission");
					String idp;
					String p;
					for (int i=0;i<permessi.size();i++){
						idp = getInfo(nome,ip,utente,pass_utente,tipo,"idp",q2,permessi.get(i)); //recupera id del permesso
						p = permessi.get(i)+";"+idr+";"+idp;
						permessi.set(i, p);
					}
					insertPermission(nome,ip,utente,pass_utente,tipo,q3,permessi);	//aggiungi permessi nel db
				}
				else { //DB TIPO VAM
					String q3 = d.getQuery(id_db, "insert_role_permission");
					String p;
					for (int i=0;i<permessi.size();i++){
						p = permessi.get(i)+";"+nome_ruolo;
						permessi.set(i, p);
					}
					insertPermission(nome,ip,utente,pass_utente,tipo,q3,permessi);	//aggiungi permessi nel db
				}
			}
			String D1 = req.getParameter("DB1");
			String D2 = req.getParameter("DB2");
			String or = req.getParameter("or");
			String tm = req.getParameter("tm");
			String r1,r2;
			if (dbname.equals(D1)){
				r1 = nome_ruolo;
				r2 = or;
			}
			else{
				r1 = or;
				r2 = nome_ruolo;
			}
			String myurl = "/guc/guc.rpm.ScambioAction.us?D1="+D1+"&D2="+D2+"&RoleDB1="+r1+"&"+tm+"=MODIFICA"+"&RoleDB2="+r2;
			res.sendRedirect(myurl);
		}
		
		if (home!=null){
			String d1 = req.getParameter("DB1");
			String d2 = req.getParameter("DB2");
			res.sendRedirect("/guc/guc.rpm.MyConnectAction.us?db1="+d1+"&DB1="+d1+"&db2="+d2+"&DB2="+d2+"&check=CHECK");			
		}
	}

	/************************************************
	 * RECUPERA ID DEL RUOLO O DEL PERMESSO			*
	 * INPUT STRING NOME_DB, IP, UTENTE, PASSWORD,	*
	 * 		 STRING TIPO_DB(CENTRIC|VAM),			*
	 * 		 STRING COMMAND (IDR | IDP),			* 
	 * 		 STRING QUERY, 							*
	 * 		 STRING INFO (RUOLO | PERMESSO) 		*
	 * OUTPUT STRING ID								*
	 ************************************************/
	private String getInfo(String nomedb,String ip, String utente,String pass_utente,String tipo,String command,String query,String info){
		String id=null;
		try{
			PreparedStatement pst = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nomedb;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			if (command.equals("idr")){ //get id del ruolo
				query=query+"'"+info+"'";
				pst = connection.prepareStatement(query); 
			}
			else {						//get id del permesso
				if (!tipo.equals("vam")){ //DB TIPO CENTRIC
					String split[] = info.split(";");
					pst = connection.prepareStatement(query); 
					pst.setString(1, split[1]);     //description
					pst.setString(2, split[0]);		//category		
				}
				else{	//DB TIPO VAM
					pst = connection.prepareStatement(query); 
					pst.setString(1, info);
				}
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				id = rs.getObject(1).toString();
			}
			connection.close();
			pst.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return id;
	}

	/************************************************
	 * INSERISCI PERMESSI							*
	 * INPUT STRING NOME_DB, IP, UTENTE, PASSWORD,	*
	 * 		 STRING TIPO_DB(CENTRIC|VAM),			*
	 * 		 STRING QUERY, 							*
	 * 		 STRING INFO (PERMESSI) 				*
	 * OUTPUT NULL									*
	 ************************************************/
	private void insertPermission(String nomedb,String ip, String utente,String pass_utente,String tipo,String query, ArrayList<String> info){
		try{
			PreparedStatement pst = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+nomedb;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			pst = connection.prepareStatement(query); 
			if (!tipo.equals("vam")){	//DB TIPO CENTRIC
				String split [];
				for (int i=0;i<info.size();i++){
					split = info.get(i).split(";");
					pst.setInt(1, Integer.parseInt(split[6])); // IDR
					pst.setInt(2, Integer.parseInt(split[7])); // IDP
					pst.setBoolean(3, Boolean.parseBoolean(split[2])); //VIEW
					pst.setBoolean(4, Boolean.parseBoolean(split[3])); //ADD
					pst.setBoolean(5, Boolean.parseBoolean(split[4])); //EDIT
					pst.setBoolean(6, Boolean.parseBoolean(split[5])); //DELETE
					pst.executeUpdate();
				}
			}
			else{ //DB TIPO VAM
				int max=0;
				Statement statement=null;
				ResultSet rs = null;
				String split[];
				for (int i=0;i<info.size();i++){ 
					statement = connection.createStatement();
					rs = statement.executeQuery("SELECT max(id) FROM capability");	//RICAVA NUOVO ID DEL PERMESSO
					while (rs.next()){
						max = rs.getInt(1);
					}
					split = info.get(i).split(";"); 
					
					//TAB CAPABILITY
					pst.setInt(1, max+1);   // ID
					pst.setString(2, split[2]); //nome ruolo
					pst.setString(3, split[0]);  //nome permesso
					pst.executeUpdate();
					rs.close();
					
					//TAB CAPABILITY_PERMISSION
					PreparedStatement pst2 = connection.prepareStatement("INSERT INTO capability_permission values (?,?)");	//INSERISCI VALORE PERMESSO
					pst2.setInt(1, max+1);			//id
					pst2.setString(2, split[1]);	//valore permesso (r|w)
					pst2.executeUpdate();
					pst2.close();
				}
			}
			connection.close();
			pst.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
