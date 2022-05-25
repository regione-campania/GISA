/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc.rpm;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/********************************************
 * RECUPERA INFO RUOLI/PERMESSI TRA DUE DB  *
*********************************************/

public class MyConnectAction extends GenericAction
{
	private String db1;    	//PRIMO DB
	private String db2;		//SECONDO DB
	private ArrayList <String> r1;	//RUOLI DEL PRIMO DB
	private ArrayList <String> r2;	//RUOLI DEL SECONDO DB
	private ArrayList <String> p1;	//PERMESSI DEL PRIMO DB
	private ArrayList <String> p2;	//PERMESSI DEL SECONDO DB
	private ArrayList <String> CommonRole;	//RUOLI IN COMUNE
	private ArrayList <String> CommonPermission;	//PERMESSI IN COMUNE
	
	//INFO CONNESSIONE DB METADATI
	private String meta_name = ApplicationProperties.getProperty( "DATABASE" );
	private String meta_ip = ApplicationProperties.getProperty( "IP" );
	private String meta_port = ApplicationProperties.getProperty( "PORT" );
	private String meta_user = ApplicationProperties.getProperty( "USERNAME" );
	private String meta_password = ApplicationProperties.getProperty( "PASSWORD" );

	private ArrayList <String> Associazioni;	//PROCEDURA AUTOMATICA (COMMENTATA)

	private static final Logger logger = LoggerFactory.getLogger( MyConnectAction.class );

	@Override
	public void can() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		RolesBean rb=null;
		if (req.getParameter("db1")!= null){
		
		db1 = (String) req.getParameter("db1"); 
		db2 = (String) req.getParameter("db2");
		if (!db1.equals(db2)) 
			if (!db1.equals("sel1") && !db2.equals("sel2")){
					rb = new RolesBean();
					
					r1 = new ArrayList<String>();
					r2 = new ArrayList<String>();
					p1 = new ArrayList<String>();
					p2 = new ArrayList<String>();
					Associazioni = new ArrayList<String>();

					//INFO DEL DB1
					Db db = new Db();
					String id_db1 = db.getFieldValue(db1, "id_db");
					String nome = db.getDBName(id_db1);
					String ip = db.getFieldValue(db1, "ip");
					String utente = db.getFieldValue(db1, "utente");
					String pass_utente = db.getFieldValue(db1, "password_utente");
					String tipo = db.getFieldValue(db1, "tipo");
					String query1 = db.getQuery(id_db1, "lista");
					String query2 = db.getQuery(id_db1, "perm_db");
					getInfo(nome,ip,utente,pass_utente,query1,query2,tipo,1);  //RUOLI E PERMESSI DEL DB

					//INFO DEL DB2
					String id_db2 = db.getFieldValue(db2, "id_db");
					nome = db.getDBName(id_db2);
					ip = db.getFieldValue(db2, "ip");
					utente = db.getFieldValue(db2, "utente");
					pass_utente = db.getFieldValue(db2, "password_utente");
					tipo = db.getFieldValue(db2, "tipo");
					query1 = db.getQuery(id_db2, "lista");
					query2 = db.getQuery(id_db2, "perm_db");
					getInfo(nome,ip,utente,pass_utente,query1,query2,tipo,2); //RUOLI E PERMESSI DEL DB

					if (r1.size()>0) rb.setRoleDB1(r1);
					if (r2.size()>0) rb.setRoleDB2(r2);

					if (p1.size()>0) rb.setPermDB1(p1);
					if (p2.size()>0) rb.setPermDB2(p2);

					if (r1.size()>0 && r2.size()>0){	//RUOLI IN COMUNE
						CommonRole = new ArrayList<String>();
						CommonRole = getAR();
						CommonRole=rb.sortList(CommonRole);
						rb.setCommonRole(CommonRole);
					}

					if (p1.size()>0 && p2.size()>0){ 	//PERMESSI IN COMUNE
						CommonPermission = new ArrayList<String>();
						CommonPermission = getAP();
						CommonPermission=rb.sortList(CommonPermission);
						rb.setCommonPermission(CommonPermission);

				//		Associazioni = buildAss(id_db1,id_db2);   
				//		aggiorna(Associazioni);
					}
				}
			}
		req.setAttribute("RolesBean", rb);
		gotoPage( "/jsp/pages/ruoli_permessi.jsp" );
	}
	
	/************************************************
	 * RUOLI/PERMESSI DEL DB 						*
	 * INPUT STRING NOME_DB, IP, UTENTE, PASSWORD,	* 
	 * 		 STRING QUERY_RUOLI, QUERY_PERMESSI, 	*
	 * 		 STRING TIPO_DB(CENTRIC|VAM), 			*
	 * 		 INT NUM_DEL_DB(1|2)					*
	 * OUTPUT NULL									*
	 ************************************************/
	private void getInfo(String db,String ip, String utente, String pass_utente, String query1,String query2,String tipo,int num_db){
		ArrayList<String> r = new ArrayList<String>();
		ArrayList<String> permessi = new ArrayList<String>();

		//RUOLI
		try{ 
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			//RUOLI
			statement = connection.createStatement();		
			rs = statement.executeQuery(query1);
			while(rs.next()){ 
				r.add(rs.getObject(1).toString());
			}	
			rs.close();
			statement.close();
			
			//PERMESSI
			String category, description,idp;
			statement = connection.createStatement();
			rs = statement.executeQuery(query2);
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
	
		if (num_db==1){
			r1=r;
			p1=permessi;
		}
		else{
			r2=r;
			p2=permessi;
		}
	}
	
	/************************************************************
	 * NOMI RUOLI IN COMUNE (ASSOCIATI)							*
	 * INPUT NULL												*
	 * OUTPUT ARRAYLIST (NOME_RUOLO_DB1 <--> NOME_RUOLO_DB2)	*
	 ************************************************************/
	public ArrayList <String> getAR(){
		ArrayList <String> a = new ArrayList <String>();
		ArrayList <String> b = new ArrayList <String>();
		String split[];

		a=getCommonRole();	//RICAVA ELENCO (ID) DEI RUOLI IN COMUNE DAL DB DEI METADATI

		// INFO DB1
		Db db = new Db();
		String d1 = db.getFieldValue(db1, "id_db");
		String nome1 = db.getDBName(d1);
		String ip1 = db.getFieldValue(db1, "ip");
		String utente1 = db.getFieldValue(db1, "utente");
		String pass_utente1 = db.getFieldValue(db1, "password_utente");
		String tipo1 = db.getFieldValue(db1, "tipo");
		String query1 = db.getQuery(d1, "role_name");

		// INFO DB2
		String d2 = db.getFieldValue(db2, "id_db");
		String nome2 = db.getDBName(d2);
		String ip2 = db.getFieldValue(db2, "ip");
		String utente2 = db.getFieldValue(db2, "utente");
		String pass_utente2 = db.getFieldValue(db2, "password_utente");
		String tipo2 = db.getFieldValue(db2, "tipo");
		String query2 = db.getQuery(d2, "role_name");

		//DALL'ELENCO DEI RUOLI IN COMUNE ESTRAI I NOMI DEI RUOLI DAL DB DI APPARTENENZA
		String n1 = null; String n2=null;
		for(int i=0;i<a.size();i++){
			split = a.get(i).split(";"); 

			if (d1.equals(split[0])) 
				n1=getName(nome1,ip1,utente1,pass_utente1,query1,split[2],tipo1);
			else if(d1.equals(split[1]))
				n1=getName(nome1,ip1,utente1,pass_utente1,query1,split[3],tipo1);

			if (d2.equals(split[0])) 
				n2=getName(nome2,ip2,utente2,pass_utente2,query2,split[2],tipo2);
			else if(d2.equals(split[1]))
				n2=getName(nome2,ip2,utente2,pass_utente2,query2,split[3],tipo2);

			if (n1!=null && n2!=null)
				b.add(n1+" <--> "+n2);	//USATO PER LA VISUALIZZAZIONE NELLA JSP INIZIALE
		}
		return b;
	}

	/************************************************
	 * ID RUOLI IN COMUNE (ASSOCIATI)				*
	 * INPUT NULL									*
	 * OUTPUT ARRAYLIST (ID_DB1;ID_DB2;ID_R1;ID_R2)	*
	 ************************************************/
	private ArrayList <String> getCommonRole(){
		ArrayList <String> c = new ArrayList <String>();
		Db db = new Db();
		String d1 = db.getFieldValue(db1, "id_db");
		String d2 = db.getFieldValue(db2, "id_db");

		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			String id_db1, id_db2, id_r1, id_r2 = null;
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			String query = "SELECT id_db1,id_db2,id_r1,id_r2 FROM associazione_ruoli WHERE (id_db1="+d1+" AND id_db2="+d2+") OR (id_db1="+d2+" AND id_db2="+d1+")";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				id_db1 = rs.getString("id_db1");
				id_db2 = rs.getString("id_db2");
				id_r1 = rs.getString("id_r1");
				id_r2 = rs.getString("id_r2");
				c.add(id_db1+";"+id_db2+";"+id_r1+";"+id_r2);
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return c;
	}
	
	/****************************************************************
	 * NOMI PERMESSI IN COMUNE (ASSOCIATI)							*
	 * INPUT NULL													*
	 * OUTPUT ARRAYLIST (NOME_PERMESSO_DB1 <--> NOME_PERMESSO_DB2)	*
	 ****************************************************************/
	public ArrayList <String> getAP(){
		ArrayList <String> a = new ArrayList <String>();
		ArrayList <String> b = new ArrayList <String>();
		String split[];

		a=getCommonPermission();	//RICAVA ELENCO (ID) DEI PERMESSI IN COMUNE DAL DB DEI METADATI

		// INFO DB1
		Db db = new Db();
		String d1 = db.getFieldValue(db1, "id_db");
		String nome1 = db.getDBName(d1);
		String ip1 = db.getFieldValue(db1, "ip");
		String utente1 = db.getFieldValue(db1, "utente");
		String pass_utente1 = db.getFieldValue(db1, "password_utente");
		String tipo1 = db.getFieldValue(db1, "tipo");
		String query1 = db.getQuery(d1, "comm_perm");

		// INFO DB2
		String d2 = db.getFieldValue(db2, "id_db");
		String nome2 = db.getDBName(d2);
		String ip2 = db.getFieldValue(db2, "ip");
		String utente2 = db.getFieldValue(db2, "utente");
		String pass_utente2 = db.getFieldValue(db2, "password_utente");
		String tipo2 = db.getFieldValue(db2, "tipo");
		String query2 = db.getQuery(d2, "comm_perm");

		String arr_perm1[] = new String[a.size()];
		String arr_perm2[] = new String[a.size()];
		
		//ARRAY DEI PERMESSI IN COMUNE (ID)
		for(int i=0;i<a.size();i++){
			split = a.get(i).split(";"); 

			if (d1.equals(split[0])) 
				arr_perm1[i]=split[2];
			else if(d1.equals(split[1]))
				arr_perm1[i]=split[3]; 

			if (d2.equals(split[0])) 
				arr_perm2[i]=split[2];
			else if(d2.equals(split[1])) 
				arr_perm2[i]=split[3];
		}
		
		//RICAVA NOMI DEI PERMESSI A PARTIRE DAGLI ID
		ArrayList<String> L1 = getList(arr_perm1,nome1,ip1,utente1,pass_utente1,query1,tipo1);
		ArrayList<String> L2 = getList(arr_perm2,nome2,ip2,utente2,pass_utente2,query2,tipo2);
				
		//COSTRUISCI LISTA DELLE ASSOCIAZIONI (PER VISUALIZZAZIONE)
		String p[],p1[],p2[],Ass1 = null,Ass2 = null;
		for (int i=0;i<a.size();i++) {
			p=a.get(i).split(";");	//TUPLA DAI METADATI (ID_DB1;ID_DB2;ID_P1;ID_P2)
			
			for (int j=0;j<L1.size();j++){	//LISTA PERMESSI PRIMO DB
				p1=L1.get(j).split(";");
				if (d1.equals(p[0])){
					if(p[2].equals(p1[1]))
						Ass1=p1[0];
				}
				else {
					if(p[3].equals(p1[1]))
						Ass1=p1[0];
				}
			}
			
			for (int j=0;j<L2.size();j++){	//LISTA PERMESSI SECONDO DB
				p2=L2.get(j).split(";");
				if (d2.equals(p[0])){
					if(p[2].equals(p2[1]))
						Ass2=p2[0];
				}
				else{
					if(p[3].equals(p2[1]))
						Ass2=p2[0];
				}
			}
			b.add(Ass1+" <--> "+Ass2); //USATO PER LA VISUALIZZAZIONE NELLA JSP INIZIALE
			Ass1 = null;Ass2 = null;
		}
		
		//SALVA SU FILE LISTA PERMESSI ASSOCIATI
	/*	Collections.sort(b);
		String path = "prova.txt",s;
		try{
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			for(int i=0; i<b.size(); i++) {
				s = b.get(i) + System.getProperty( "line.separator" );
				fw.write(s);
			}
			fw.flush();
			fw.close();
		} catch (Exception e){
			e.printStackTrace();
		}*/
		
		return b;
	}

	/****************************************************
	 * NOMI PERMESSI DB (ASSOCIATI)						*
	 * INPUT STRING LISTA[] (ID PERMESSI),				*
	 * 		 STRING NOME_DB, IP, UTENTE, PASSWORD,		*
	 * 		 STRING QUERY_PERMESSI,						*
	 * 		 STRING TIPO_DB(CENTRIC|VAM)				*
	 * OUTPUT ARRAYLIST (CAT_PERM;DESCR_PERM;ID_PERM)	*
	 ****************************************************/
	private ArrayList<String> getList(String lista[],String db,String ip,String utente, String pass_utente, String query, String tipo){
		ArrayList<String> list = new ArrayList<String>();
		if (lista.length>0){
			try{
				Statement statement=null;
				ResultSet rs = null;
				String driverName = "org.postgresql.Driver";
				String databaseURL = "jdbc:postgresql://"+ip+"/"+db;	
				Class driverClass = Class.forName(driverName);
				Driver driver = (Driver) driverClass.newInstance();
				if (!tipo.equals("vam")){
					query=query+lista[0];
					if (lista.length>1) 
						for (int k=1;k<lista.length;k++)
								query=query+" OR permission.permission_id="+lista[k];	//COSTRUISCI LA QUERY PER TUTTI GLI ID DEI PERMESSI
				} 
				else {
					query=query+"'"+lista[0]+"'";
					if (lista.length>1) 
						for (int k=1;k<lista.length;k++)
								query=query+" OR name='"+lista[k]+"'";	//COSTRUISCI LA QUERY PER TUTTI GLI ID DEI PERMESSI
				}
				query=query+")";
							
				Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
				statement = connection.createStatement();
				rs = statement.executeQuery(query);
				
				//ESTRAI INFO PERMESSO (CATEGORIA;DESCRIZIONE_PERMESSO)
				while(rs.next()){
					if (!tipo.equals("vam"))
						list.add(rs.getObject(1).toString()+" -- "+rs.getObject(2).toString()+";"+rs.getObject(3).toString());
					else
						list.add(rs.getObject(1).toString()+";"+rs.getObject(1).toString());
				}
				
				connection.close();
				rs.close();
				statement.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/************************************************
	 * ID PERMESSI IN COMUNE (ASSOCIATI)			*
	 * INPUT NULL									*
	 * OUTPUT ARRAYLIST (ID_DB1;ID_DB2;ID_P1;ID_P2)	*
	 ************************************************/
	private ArrayList <String> getCommonPermission(){	
		ArrayList <String> c = new ArrayList <String>();
		Db db = new Db();
		String d1 = db.getFieldValue(db1, "id_db");
		String d2 = db.getFieldValue(db2, "id_db");
		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			String id_db1,id_db2,id_p1, id_p2 = null;
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			String query = "SELECT id_db1,id_db2,id_p1,id_p2 FROM associazione_permessi WHERE (id_db1="+d1+" AND id_db2="+d2+") OR (id_db1="+d2+" AND id_db2="+d1+")";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				id_db1 = rs.getString("id_db1");
				id_db2 = rs.getString("id_db2");
				id_p1 = rs.getString("id_p1");
				id_p2 = rs.getString("id_p2");
				c.add(id_db1+";"+id_db2+";"+id_p1+";"+id_p2);
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return c;	
	}
	
	/************************************************
	 * NOMI DEI RUOLI A PARTIRE DA ID 				*
	 * INPUT STRING NOME_DB, IP, UTENTE, PASSWORD,	*
	 * 		 STRING QUERY, COMMAND(DEPRECATED),		*
	 * 		 STRING TIPO_DB(CENTRIC|VAM)			*
	 * OUTPUT STRING NOME RUOLO						*
	 ************************************************/
	private String getName(String db,String ip, String utente, String pass_utente, String query,String id, String tipo){
		String name=null;
		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();
			if (!tipo.equals("vam"))
				query=query+id; 
			else
				query=query+"'"+id+"'";
						
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while(rs.next()){
				name = rs.getString(1);
			}
		
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return name;
	}
	
	/****************************************************
	 * PROCEDURE AUTOMATICHE ASS PERM					*
	 * INPUT STRING ID_DB1, ID_DB2						*
	 * OUTPUT ARRAYLIST (ID_DB1;ID_DB2;NOME_P1;NOME_P2)	*
	 ****************************************************/
	private ArrayList<String> buildAss(String id_db1,String id_db2){
		ArrayList<String> p = new ArrayList<String>();
		for (int i=0;i<p1.size();i++){
			for (int j=0; j<p2.size();j++)
				if (p1.get(i).equals(p2.get(j)))
					p.add(id_db1+";"+id_db2+";"+p1.get(i).split(" -- ")[2]+";"+p2.get(j).split(" -- ")[2]);
		}
		return p;
	}
	
	//AGGIORNA METADATI DEI PERMESSI
	private void aggiorna (ArrayList<String> Ass){
		try{
			PreparedStatement pst = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);			
			
			String split [];
			String query = "INSERT INTO associazione_permessi (id_db1,id_db2,id_p1,id_p2) values (?,?,?,?)"; 
			pst = connection.prepareStatement(query);
			for (int i=0;i<Ass.size();i++){
				split = Ass.get(i).split(";");
				pst.setInt(1, Integer.parseInt(split[0]));
				pst.setInt(2, Integer.parseInt(split[1]));
				pst.setString(3, split[2]);
				pst.setString(4, split[3]);
				pst.executeUpdate();
			}
			connection.close();
			pst.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
