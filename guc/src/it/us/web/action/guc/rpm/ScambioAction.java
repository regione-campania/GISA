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

/*******************************************
 * ASSOCIA/SEPARA RUOLI/PERMESSI TRA 2 DB  *
 * ESPORTA RUOLO TRA DB					   *
********************************************/

public class ScambioAction extends GenericAction{
	RoleBean r = null;
	
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
		String d1 = req.getParameter("D1"); //DB
		String d2 = req.getParameter("D2");

		String dx = req.getParameter("dx"); //SCAMBIO (EXPORT)
		String sx = req.getParameter("sx");

		String mod1 = req.getParameter("mod1"); //MODIFICA
		String mod2 = req.getParameter("mod2");

		String RoleDB1 = req.getParameter("RoleDB1"); //RUOLI
		String RoleDB2 = req.getParameter("RoleDB2");

		String AR = req.getParameter("ASSOCIA_R"); //ASSOCIA-SEPARA RUOLI
		String SR = req.getParameter("SEPARA_R");

		String AP = req.getParameter("ASSOCIA_P"); //ASSOCIA-SEPARA PERMESSI 
		String SP = req.getParameter("SEPARA_P");

		String PDB1 = req.getParameter("PDB1"); //PERMESSI
		String PDB2 = req.getParameter("PDB2");

		String pagina="ruoli_permessi";

		if (d1!=null && d2!=null){
			r = new RoleBean();

			if (dx!=null){  //SCAMBIO (EXPORT)
				if (RoleDB1!=null){
					Boolean ok = Left2Right(RoleDB1,d1,d2); //COPIA RUOLO E PERMESSI DA DB1 A DB2
					if (ok==true)
						res.sendRedirect(req.getHeader("Referer"));   //OPERAZIONE ESEGUITA CORRETTAMENTE
					else {
						req.setAttribute("r1", RoleDB1);	//ERRORE : RUOLO GIA' ESISTENTE SU DB DI DESTINAZIONE
						req.setAttribute("cmd", "SC");
						pagina="error";
					}
				}
				else{
					r.setMsg("ERROR : Selezionare un ruolo");
				}
			}

			if (sx!=null){  // SCAMBIO
				if (RoleDB2!=null){
					Boolean ok = Right2Left(RoleDB2,d1,d2); //COPIA RUOLO E PERMESSI DA DB2 A DB1
					if (ok==true)
						res.sendRedirect(req.getHeader("Referer"));	//OPERAZIONE ESEGUITA CORRETTAMENTE
					else {
						req.setAttribute("r1", RoleDB2);	//ERRORE : RUOLO GIA' ESISTENTE SU DB DI DESTINAZIONE
						req.setAttribute("cmd", "SC");
						pagina="error";
					}
				}
				else{
					r.setMsg("ERROR : Selezionare un ruolo");
				}
			}

			if (mod1!=null){ //MODIFICA RUOLO DB1
				if (RoleDB1!=null){
					Db db = new Db();
					String id_db = db.getFieldValue(d1, "id_db"); //Preleva info db (IP,PORTA,ECC).....
					String nome = db.getDBName(id_db);
					String tipo = db.getFieldValue(d1, "tipo");
					String ip = db.getFieldValue(d1, "ip");
					String utente = db.getFieldValue(d1, "utente");
					String pass_utente = db.getFieldValue(d1, "password_utente");
					String query = db.getQuery(id_db, "info_ruolo");    //......e query specificate
					String query2 = db.getQuery(id_db, "permessi_ruolo");
					getRoleInfo(RoleDB1,nome,ip,utente,pass_utente,query,query2,tipo);
					req.setAttribute("RoleBean", r);
					if (!tipo.equals("vam"))	//VAI ALLA PAGINA DI MODIFICA
						pagina = "modifica_ruolo";
					else
						pagina = "modifica_ruolo_vam";
				}
				else{
					r.setMsg("ERROR : Selezionare un ruolo");
				}
			}

			if (mod2!=null){  //MODIFICA RUOLO DB2
				if (RoleDB2!=null){
					Db db = new Db();
					String id_db = db.getFieldValue(d2, "id_db");  //Preleva info db (IP,PORTA,ECC).....
					String nome = db.getDBName(id_db);
					String tipo = db.getFieldValue(d2, "tipo");
					String ip = db.getFieldValue(d2, "ip");
					String utente = db.getFieldValue(d2, "utente");
					String pass_utente = db.getFieldValue(d2, "password_utente");
					String query = db.getQuery(id_db, "info_ruolo");    //......e query specificate
					String query2 = db.getQuery(id_db, "permessi_ruolo");
					getRoleInfo(RoleDB2,nome,ip,utente,pass_utente,query,query2,tipo);
					req.setAttribute("RoleBean", r);
					if (!tipo.equals("vam"))	//VAI ALLA PAGINA DI MODIFICA
						pagina = "modifica_ruolo";
					else
						pagina = "modifica_ruolo_vam";
				}
				else{
					r.setMsg("ERROR : Selezionare un ruolo");
				}
			}

			if (AR!=null){ //ASSOCIA RUOLI
				if (RoleDB1!=null && RoleDB2!=null){
					Db db = new Db();
					//INFO DB1
					String id_db1 = db.getFieldValue(d1, "id_db");
					String nome1 = db.getDBName(id_db1);
					String ip = db.getFieldValue(d1, "ip");
					String utente = db.getFieldValue(d1, "utente");
					String pass_utente = db.getFieldValue(d1, "password_utente");
					String query = db.getQuery(id_db1, "idr");
					String id1 = getIdR(RoleDB1,nome1,ip,utente,pass_utente,query);

					//INFO DB2
					String id_db2 = db.getFieldValue(d2, "id_db");
					String nome2 = db.getDBName(id_db2);
					Boolean presente = false;
					presente = controllaAR(id_db1,id_db2,id1);	//CONTROLLA SE IL RUOLO DEL DB1 HA GIA' ASSOCIATI
					if(presente==false){
						ip = db.getFieldValue(d2, "ip");
						utente = db.getFieldValue(d2, "utente");
						pass_utente = db.getFieldValue(d2, "password_utente");
						query = db.getQuery(id_db2, "idr");
						String id2 = getIdR(RoleDB2,nome2,ip,utente,pass_utente,query);
						presente = controllaAR(id_db2,id_db1,id2);	//CONTROLLA SE IL RUOLO DEL DB2 HA GIA' ASSOCIATI
						if (presente==false){
							aggiornaMetadati(Integer.parseInt(id_db1),Integer.parseInt(id_db2),id1,id2,"INS_R");
							res.sendRedirect(req.getHeader("Referer"));	//OPERAZIONE ESEGUITA CORRETTAMENTE
						}
						else {
							req.setAttribute("r1", RoleDB1);	//ERRORE : RUOLO DB2 GIA' ASSOCIATO
							req.setAttribute("r2", RoleDB2);
							req.setAttribute("cmd", "AR");
							pagina="error";
						}
					}
					else {
						req.setAttribute("r1", RoleDB1);	//ERRORE : RUOLO DB1 GIA' ASSOCIATO
						req.setAttribute("r2", RoleDB2);
						req.setAttribute("cmd", "AR");
						pagina="error";
					}
				}
				else{
					r.setMsg("ERROR : Selezionare due ruoli da associare");
				}
			}

			if (SR!=null){  //SEPARA RUOLI
				if (RoleDB1!=null && RoleDB2!=null){
					Db db = new Db();
					//INFO DB1
					String id_db1 = db.getFieldValue(d1, "id_db");
					String nome1 = db.getDBName(id_db1);
					String ip = db.getFieldValue(d1, "ip");
					String utente = db.getFieldValue(d1, "utente");
					String pass_utente = db.getFieldValue(d1, "password_utente");
					String query = db.getQuery(id_db1, "idr");
					String id1 = getIdR(RoleDB1,nome1,ip,utente,pass_utente,query);

					//INFO DB2
					String id_db2 = db.getFieldValue(d2, "id_db");
					String nome2 = db.getDBName(id_db2);
					Boolean presente = false;
					presente = controllaAR(id_db1,id_db2,id1);	//CONTROLLA SE IL RUOLO DEL DB1 E' ASSOCIATO
					if(presente==true){
						ip = db.getFieldValue(d2, "ip");
						utente = db.getFieldValue(d2, "utente");
						pass_utente = db.getFieldValue(d2, "password_utente");
						query = db.getQuery(id_db2, "idr");
						String id2 = getIdR(RoleDB2,nome2,ip,utente,pass_utente,query);
						presente = controllaAR(id_db2,id_db1,id2);	//CONTROLLA SE IL RUOLO DEL DB2 E' ASSOCIATO
						if(presente==true){
							int status = aggiornaMetadati(Integer.parseInt(id_db1),Integer.parseInt(id_db2),id1,id2,"DEL_R");
							if (status==0){
								req.setAttribute("r1", RoleDB1);	//ERRORE : RUOLI NON ASSOCIATI TRA LORO
								req.setAttribute("r2", RoleDB2);
								req.setAttribute("cmd", "SR");
								pagina="error";
							} 
							else res.sendRedirect(req.getHeader("Referer"));	//OPERAZIONE ESEGUITA CORRETTAMENTE
						}
						else {
							req.setAttribute("r1", RoleDB1);	//ERRORE : RUOLO DB2 NON ASSOCIATO
							req.setAttribute("r2", RoleDB2);
							req.setAttribute("cmd", "SR");
							pagina="error";
						}
					}
					else {
						req.setAttribute("r1", RoleDB1);	//ERRORE : RUOLO DB1 NON ASSOCIATO
						req.setAttribute("r2", RoleDB2);
						req.setAttribute("cmd", "SR");
						pagina="error";
					}
				}
				else{
					r.setMsg("ERROR : Selezionare due ruoli da separare");
				}
			}

			if (AP!=null){ //ASSOCIA PERMESSI
				if (PDB1!=null && PDB2!=null){
					Db db = new Db();
					//INFO DB1
					String id_db1 = db.getFieldValue(d1, "id_db");
					String nome1 = db.getDBName(id_db1);
					String tipo1 = db.getFieldValue(d1, "tipo");
					String ip = db.getFieldValue(d1, "ip");
					String utente = db.getFieldValue(d1, "utente");
					String pass_utente = db.getFieldValue(d1, "password_utente");
					String query = db.getQuery(id_db1, "idp");
					
					String idp1;
					if (!tipo1.equals("vam"))
						idp1=getIdP(PDB1,nome1,ip,utente,pass_utente,query);
					else
						idp1=PDB1;
					
					//INFO DB2
					String id_db2 = db.getFieldValue(d2, "id_db");
					String nome2 = db.getDBName(id_db2);
					String tipo2 = db.getFieldValue(d2, "tipo");
					Boolean presente = false;
					presente = controllaAP(id_db1,id_db2,idp1);	//CONTROLLA SE IL PERMESSO DEL DB1 HA GIA' ASSOCIATI
					if(presente==false){
						ip = db.getFieldValue(d2, "ip");
						utente = db.getFieldValue(d2, "utente");
						pass_utente = db.getFieldValue(d2, "password_utente");
						query = db.getQuery(id_db2, "idp");
						
						String idp2;
						if (!tipo2.equals("vam"))
							idp2 = getIdP(PDB2,nome2,ip,utente,pass_utente,query);
						else
							idp2=PDB2;
						
						presente = controllaAP(id_db2,id_db1,idp2);	//CONTROLLA SE IL PERMESSO DEL DB2 HA GIA' ASSOCIATI
						if(presente==false){
							aggiornaMetadati(Integer.parseInt(id_db1),Integer.parseInt(id_db2),idp1,idp2,"INS_P");
							res.sendRedirect(req.getHeader("Referer"));	//OPERAZIONE ESEGUITA CORRETTAMENTE
						}
						else {
							req.setAttribute("p1", PDB1);	//ERRORE : PERMESSO DB2 GIA' ASSOCIATO
							req.setAttribute("p2", PDB2);
							req.setAttribute("cmd", "AP");
							pagina="error";
						}
					}
					else {
						req.setAttribute("p1", PDB1);	//ERRORE : PERMESSO DB1 GIA' ASSOCIATO
						req.setAttribute("p2", PDB2);
						req.setAttribute("cmd", "AP");
						pagina="error";
					}
				}
				else{
					r.setMsg("ERROR : Selezionare due permessi da associare");
				}
			}

			if (SP!=null){ //SEPARA PERMESSI
				if(PDB1!=null && PDB2!=null){
					Db db = new Db();
					//INFO DB1
					String id_db1 = db.getFieldValue(d1, "id_db");
					String nome1 = db.getDBName(id_db1);
					String tipo1 = db.getFieldValue(d1, "tipo");
					String ip = db.getFieldValue(d1, "ip");
					String utente = db.getFieldValue(d1, "utente");
					String pass_utente = db.getFieldValue(d1, "password_utente");
					String query = db.getQuery(id_db1, "idp");
					
					String idp1;
					if (!tipo1.equals("vam"))
						idp1=getIdP(PDB1,nome1,ip,utente,pass_utente,query);
					else
						idp1=PDB1;

					//INFO DB2
					String id_db2 = db.getFieldValue(d2, "id_db");
					String nome2 = db.getDBName(id_db2);
					String tipo2 = db.getFieldValue(d2, "tipo");
					Boolean presente = false;
					presente = controllaAP(id_db1,id_db2,idp1); //CONTROLLA SE IL PERMESSO DEL DB1 E' ASSOCIATO
					if(presente==true){
						ip = db.getFieldValue(d2, "ip");
						utente = db.getFieldValue(d2, "utente");
						pass_utente = db.getFieldValue(d2, "password_utente");
						query = db.getQuery(id_db2, "idp");
						
						String idp2;
						if (!tipo2.equals("vam"))
							idp2 = getIdP(PDB2,nome2,ip,utente,pass_utente,query);
						else
							idp2=PDB2;
						
						presente = controllaAP(id_db2,id_db1,idp2);	//CONTROLLA SE IL PERMESSO DEL DB2 E' ASSOCIATO
						if(presente==true){
							int status = aggiornaMetadati(Integer.parseInt(id_db1),Integer.parseInt(id_db2),idp1,idp2,"DEL_P");
							if (status==0){
								req.setAttribute("p1", PDB1);	
								req.setAttribute("p2", PDB2);
								req.setAttribute("cmd", "SP");
								pagina="error";
							}
							else res.sendRedirect(req.getHeader("Referer"));	//OPERAZIONE ESEGUITA CORRETTAMENTE
						}
						else {
							req.setAttribute("p1", PDB1);	//ERRORE : PERMESSO DB2 NON ASSOCIATO
							req.setAttribute("p2", PDB2);
							req.setAttribute("cmd", "SP");
							pagina="error";
						}
					}
					else {
						req.setAttribute("p1", PDB1);	//ERRORE : PERMESSO DB1 NON ASSOCIATO
						req.setAttribute("p2", PDB2);
						req.setAttribute("cmd", "SP");
						pagina="error";
					}
				}
				else{
					r.setMsg("ERROR : Selezionare due permessi da separare");
				}
			}
		}
		req.setAttribute("RoleBean", r);
		gotoPage( "/jsp/pages/"+pagina+".jsp");
	}
	
	//-----------------------------------------------------------------
	//	METODI INTERNI
	//-----------------------------------------------------------------	
	
	/****************************************************
	 * COPIA RUOLO DA DB1 A DB2							*
	 * INPUT STRING NOME_RUOLO_DB1, NOME_DB1, NOME_DB2	*
	 * OUTPUT BOOLEAN (TRUE=OPERAZIONE ESEGUITA)		*
	 ****************************************************/
	private boolean Left2Right(String RoleDB1, String d1, String d2){
		boolean ok = false;
		boolean presente = false;
		Db db = new Db();
		//INFO DB1
		String id_db1 = db.getFieldValue(d1, "id_db");
		String nome1 = db.getDBName(id_db1);
		String tipo1 = db.getFieldValue(d1, "tipo");
		String ip = db.getFieldValue(d1, "ip");
		String utente = db.getFieldValue(d1, "utente");
		String pass_utente = db.getFieldValue(d1, "password_utente");
		String query = db.getQuery(id_db1, "info_ruolo");
		String query2 = db.getQuery(id_db1, "permessi_ruolo");
		String alias;
		getRoleInfo(RoleDB1,nome1,ip,utente,pass_utente,query,query2,tipo1);

		//INFO DB2
		String id_db2 = db.getFieldValue(d2, "id_db");
		String nome2 = db.getDBName(id_db2);
		String tipo2 = db.getFieldValue(d2, "tipo");

		query = db.getQuery(id_db1, "idr");
		String idr1 = getIdR(RoleDB1,nome1,ip,utente,pass_utente,query);
		presente = controllaAR(id_db1,id_db2,idr1); //CONTROLLA SE IL RUOLO ESISTE SUL DB DI DESTINAZIONE
		if (presente==false){
			ip = db.getFieldValue(d2, "ip");
			utente = db.getFieldValue(d2, "utente");
			pass_utente = db.getFieldValue(d2, "password_utente");
			query = db.getQuery(id_db2, "insert_role");
			alias=d2;

			long ms = System.currentTimeMillis();
			Timestamp t = new Timestamp(ms);
			r.setEnteredby(999);
			r.setEntered(t);
			r.setModifiedby(999);
			r.setModified(t);
			r.setEnabled("true");

			insertRole(r,nome2,ip,utente,pass_utente,query,tipo2,alias);	//INSERISCI RUOLO NEL DB DI DESTINAZIONE

			query = db.getQuery(id_db2, "idr");
			String idr2 = getIdR(RoleDB1,nome2,ip,utente,pass_utente,query);

			query = db.getQuery(id_db2, "insert_role_permission");
			insertPermission(r,nome2,nome1,ip,utente,pass_utente,query,idr2,tipo2,tipo1,d2,d1);	//INSERISCI PERMESSI DEL RUOLO NEL DB DI DESTINAZIONE

			aggiornaMetadati(Integer.parseInt(id_db1),Integer.parseInt(id_db2),idr1,idr2,"INS_R");	//AGGIORNA TABELLA DEI METADATI
			ok = true;
		}
		return ok;
	}

	/****************************************************
	 * COPIA RUOLO DA DB2 A DB1							*
	 * INPUT STRING NOME_RUOLO_DB2, NOME_DB2, NOME_DB1	*
	 * OUTPUT BOOLEAN (TRUE=OPERAZIONE ESEGUITA)		*
	 ****************************************************/
	private boolean Right2Left(String RoleDB2, String d1, String d2){
		boolean ok = false;
		boolean presente = false;
		Db db = new Db();
		//INFO DB1
		String id_db1 = db.getFieldValue(d2, "id_db");
		String nome1 = db.getDBName(id_db1);
		String tipo1 = db.getFieldValue(d2, "tipo");
		String ip = db.getFieldValue(d2, "ip");
		String utente = db.getFieldValue(d2, "utente");
		String pass_utente = db.getFieldValue(d2, "password_utente");
		String query = db.getQuery(id_db1, "info_ruolo");
		String query2 = db.getQuery(id_db1, "permessi_ruolo");
		String alias;
		getRoleInfo(RoleDB2,nome1,ip,utente,pass_utente,query,query2,tipo1);

		//INFO DB2
		String id_db2 = db.getFieldValue(d1, "id_db");
		String nome2 = db.getDBName(id_db2);
		String tipo2 = db.getFieldValue(d1, "tipo");
		query = db.getQuery(id_db1, "idr");
		String idr1 = getIdR(RoleDB2,nome1,ip,utente,pass_utente,query);
		presente = controllaAR(id_db1,id_db2,idr1);
		if (presente==false){
			ip = db.getFieldValue(d1, "ip");
			utente = db.getFieldValue(d1, "utente");
			pass_utente = db.getFieldValue(d1, "password_utente");
			query = db.getQuery(id_db2, "insert_role");
			alias=d1;
			
			long ms = System.currentTimeMillis();
			Timestamp t = new Timestamp(ms);
			r.setEnteredby(999);
			r.setEntered(t);
			r.setModifiedby(999);
			r.setModified(t);
			r.setEnabled("true");

			insertRole(r,nome2,ip,utente,pass_utente,query,tipo2,alias);	//INSERISCI RUOLO NEL DB DI DESTINAZIONE

			query = db.getQuery(id_db2, "idr");
			String idr2 = getIdR(RoleDB2,nome2,ip,utente,pass_utente,query);
			
			query = db.getQuery(id_db2, "insert_role_permission");
			insertPermission(r,nome2,nome1,ip,utente,pass_utente,query,idr2,tipo2,tipo1,d2,d1);	//INSERISCI PERMESSI DEL RUOLO NEL DB DI DESTINAZIONE

			aggiornaMetadati(Integer.parseInt(id_db1),Integer.parseInt(id_db2),idr1,idr2,"INS_R");	//AGGIORNA TABELLA DEI METADATI
			ok = true;
		}
		return ok;
	}

	/************************************************
	 * ID DEL RUOLO									*
	 * INPUT STRING NOME_RUOLO_DB1, 				*
	 * 		 STRING NOME_DB, IP, UTENTE, PASSWORD	*
	 * 		 STRING QUERY							*
	 * OUTPUT STRING ID_RUOLO						*
	 ************************************************/
	private String getIdR (String role,String db,String ip,String utente,String pass_utente,String query){
		String idr=null;
		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
			query=query+"'"+role+"'";
			
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				idr = rs.getObject(1).toString();
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return idr;
	}

	/************************************************
	 * ID DEL PERMESSO								*
	 * INPUT STRING NOME_PERMESSO (CAT -- DESCR),	*
	 * 		 STRING NOME_DB, IP, UTENTE, PASSWORD	*
	 * 		 STRING QUERY							*
	 * OUTPUT STRING ID_PERMESSO					*
	 ************************************************/
	private String getIdP (String cat_des,String db,String ip,String utente,String pass_utente,String query){
		String idp=null;
		String [] split = cat_des.split(" -- ");
		try{
			PreparedStatement pst=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			pst = connection.prepareStatement(query);
			pst.setString(1, split[1]);
			pst.setString(2, split[0]);
			rs=pst.executeQuery();
			while(rs.next()){
				idp = rs.getObject(1).toString();
			}
			connection.close();
			pst.close();
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return idp;
	}

	/************************************************
	 * INFO E PERMESSI RUOLO			 			*
	 * INPUT STRING NOME_RUOLO_DB1, 				*
	 * 		 STRING NOME_DB, IP, UTENTE, PASSWORD	*
	 * 		 STRING QUERY_INFO, QUERY_PERMESSI		*
	 * OUTPUT NULL									*
	 ************************************************/
	private void getRoleInfo(String role, String db, String ip, String utente, String pass_utente, String query, String query2,String tipo){
		try{
			Statement statement;
			ResultSet rs ;			
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			//INFO RUOLO
			query=query+"'"+role+"'";
			statement = connection.createStatement();		
			rs = statement.executeQuery(query);

			String idr = null;
			String descr = null;
			if (!tipo.equals("vam")){
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
			else{
				while(rs.next())
				{ 
					idr = rs.getObject(1).toString();
					descr=rs.getObject(2).toString();
				}
				r.setIdr(Integer.parseInt(idr));
				r.setRole(role);
				r.setDescr(descr);
			}

			//INFO PERMESSI DEL RUOLO
			ResultSet c;
			ArrayList<String> permessi = new ArrayList<String>();

			if (!tipo.equals("vam")){
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
			else {
				query2=query2+"'"+role+"' ORDER BY capability.subject_name";
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
	 * CONTROLLO ASSOCIAZIONE TRA RUOLI				*
	 * INPUT STRING ID_DB1, ID_DB2, ID_RUOLO		*
	 * OUTPUT BOOLEAN (TRUE=ESISTE ASSOCIAZIONE)	*
	 ************************************************/
	private boolean controllaAR(String db1, String db2, String r){
		boolean trovato=false;
		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			String query = "SELECT count(*) FROM associazione_ruoli WHERE ((id_db1="+db1+" AND id_db2="+db2+" AND id_r1="+r+") OR ((id_db1="+db2+" AND id_db2="+db1+")) AND (id_r2="+r+"))";
			int count=0;
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				count = rs.getInt("count");
			}
			if (count!=0)
				trovato=true;
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return trovato;
	}

	/************************************************
	 * CONTROLLO ASSOCIAZIONE TRA PERMESSI			*
	 * INPUT STRING ID_DB1, ID_DB2, ID_PERMESSO		*
	 * OUTPUT BOOLEAN (TRUE=ESISTE ASSOCIAZIONE)	*
	 ************************************************/
	private boolean controllaAP(String db1, String db2, String p){
		boolean trovato = false;
		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			String query = "SELECT count(*) FROM associazione_permessi WHERE ((id_db1="+db1+" AND id_db2="+db2+" AND id_p1='"+p+"') OR ((id_db1="+db2+" AND id_db2="+db1+")) AND (id_p2='"+p+"'))";
			int count=0;
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				count = rs.getInt("count");
			}
			if (count!=0)
				trovato=true;
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return trovato;
	}

	/****************************************************************
	 * PERMESSO ASSOCIATO 											*
	 * INPUT STRING ID_DB1, ID_DB2, ID_PERMESSO						*
	 * OUTPUT STRING PERMESSO_ASSOCIATO(ID_DB1;ID_DB2;ID_P1,ID_P2)	*
	 ****************************************************************/
	private String getPermessoAssociato(String db1, String db2, String idP,String alias1,String alias2){
		String result=null;
		Db database = new Db();
		String id1=database.getFieldValue(alias1, "id_db");
		String id2=database.getFieldValue(alias2, "id_db");
		try{
			Statement statement=null;
			ResultSet rs = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			String id_db1,id_db2,id_p1, id_p2 = null;
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			String query = "SELECT id_db1,id_db2,id_p1,id_p2 FROM associazione_permessi WHERE ((id_db1="+id1+" AND id_db2="+id2+" AND id_p1='"+idP+"') OR ((id_db1="+id2+" AND id_db2="+id1+")) AND (id_p2='"+idP+"'))";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()){
				id_db1 = rs.getString("id_db1");
				id_db2 = rs.getString("id_db2");
				id_p1 = rs.getString("id_p1");
				id_p2 = rs.getString("id_p2");
				result=(id_db1+";"+id_db2+";"+id_p1+";"+id_p2);
			}
			connection.close();
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/************************************************
	 * INSERISCI RUOLO								*
	 * INPUT BEAN RUOLO								*
	 * 		 STRING NOME_DB, IP, UTENTE, PASSWORD,	*
	 * 		 STRING QUERY, TIPO_DB(CENTRIC|VAM)		*
	 * OUTPUT NULL									*
	 ************************************************/
	private void insertRole(RoleBean r, String db,String ip, String utente, String pass_utente, String query, String tipo,String alias){
		try{
			PreparedStatement pst = null;	
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db;
			Class driverClass = Class.forName(driverName);
			
			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);
			
			pst = connection.prepareStatement(query); 
			pst.setString(1, r.getRole());
			pst.setString(2, r.getDescr());
			if (!tipo.equals("vam")){
				pst.setInt(3, r.getEnteredby());
				pst.setTimestamp(4, r.getEntered());
				pst.setInt(5, r.getModifiedby());
				pst.setTimestamp(6, r.getModified());
				pst.setBoolean(7, Boolean.parseBoolean(r.getEnabled()));
				pst.setInt(8, Integer.parseInt(r.getRole_type()));
				if(query.contains("note"))
					pst.setString(9, r.getNote());
			}
			pst.executeUpdate();
			
			if (tipo.equals("vam")){ //AGGIORNA TAB RUOLI ABILITATI (SOLO VAM)
				Db app = new Db();
				String id_db = app.getFieldValue(alias, "id_db");
				String q1 = app.getQuery(id_db, "idr");
				Statement s = null;
				ResultSet rs = null;
				String idr = null;
				
				q1 = q1+"'"+r.getRole()+"'";
				s = connection.createStatement();
				rs = s.executeQuery(q1);
				while (rs.next()){
					idr = rs.getObject(1).toString();
				}
				s.close();
				rs.close();
							
				PreparedStatement ps = null;
				String q2 = "INSERT INTO permessi_ruoli_abilitati (id_ruolo,enabled) values (?,?)";
				ps = connection.prepareStatement(q2); 
				ps.setInt(1, Integer.parseInt(idr));
				ps.setBoolean(2, true);
				ps.executeUpdate();
				ps.close();
			}
			connection.close();
			pst.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/****************************************************************
	 * INSERISCI PERMESSI (COMUNI AI DB) AL RUOLO INSERITO			*
	 * INPUT BEAN RUOLO												*
	 * 		 STRING NOME_DB1, NOME_DB2, IP, UTENTE, PASSWORD,		*
	 * 		 STRING QUERY, ID_RUOLO_DB_DESTINAZIONE,				* 
	 * 		 STRING TIPO_DB1(CENTRIC|VAM), TIPO_DB2(CENTRIC|VAM)	*
	 * OUTPUT NULL													*
	 ****************************************************************/
	private void insertPermission(RoleBean r, String db1, String db2, String ip, String utente, String pass_utente, String query,String new_idr,String tipo1,String tipo2,String alias1,String alias2) {
		try{	
			PreparedStatement pst = null;
			PreparedStatement pst2 = null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+ip+"/"+db1;
			Class driverClass = Class.forName(driverName);

			Driver driver = (Driver) driverClass.newInstance();
			Connection connection = DriverManager.getConnection(databaseURL, utente , pass_utente);

			pst = connection.prepareStatement(query);
			ArrayList<String> associato = new ArrayList<String>();
			String split[];
			String s[];
			String p,p_a;
			int k = 0;
			int passo=1, index=0;
			
			if (!tipo2.equals("vam")){
				passo=4;
				index=5;
			}
			else {
				passo=1;
				index=0;
			}
			for (int i=0;i<r.getPerm().size();i=(i+passo)){ //estrai id permessi collegati
				p=r.getPerm().get(i);
				split = p.split(";");
				p_a= getPermessoAssociato(db2,db1,split[index],alias2,alias1);
				if (p_a!=null){
					s=p_a.split(";"); 
					if (s[2].equals(split[index])){
						associato.add(k, s[3]+";"+split[index]);
						k++;
					}
					else if (s[3].equals(split[index])){
						associato.add(k, s[2]+";"+split[index]);
						k++;
					}
				} 
			}
			
			/* POLITICA PERMESSI DB DIVERSI :
			 * DB DESTINAZIONE --- DB SORGENTE  --- VALORI PERMESSI
			 * 	   CENTRIC			  VAM		     W=TTTT R=FFFF			
			 * 		 VAM		   VAM/CENTRIC			   W*/
			//imposta permessi sul db di destinazione
			String l[];   
			String as[];
			for (int i=0;i<associato.size();i++){
				for(int j=0;j<r.getPerm().size();j=(j+passo)){
					as = associato.get(i).split(";");
					l = r.getPerm().get(j).split(";");
					if (as[1].equals(l[index])){
						if (!tipo1.equals("vam")){  //db destinazione tipo centric
							
							if (tipo2.equals("vam")){ //db sorgente tipo vam
								if (l[2].equals("w")){ 
									pst.setBoolean(3,true);	//VIEW
									pst.setBoolean(4,true);	//ADD
									pst.setBoolean(5,true);	//EDIT
									pst.setBoolean(6,true);	//DELETE
								}
								else if (l[2].equals("r")){
									pst.setBoolean(3,true);	//VIEW
									pst.setBoolean(4,false);//ADD
									pst.setBoolean(5,false);//EDIT
									pst.setBoolean(6,false);//DELETE
								}
							}
							else { //db sorgente tipo centric
								pst.setBoolean(3,Boolean.parseBoolean(r.getPerm().get(j).split(";")[3]));	//VIEW
								pst.setBoolean(4,Boolean.parseBoolean(r.getPerm().get(j+1).split(";")[3])); //ADD
								pst.setBoolean(5,Boolean.parseBoolean(r.getPerm().get(j+2).split(";")[3])); //EDIT
								pst.setBoolean(6,Boolean.parseBoolean(r.getPerm().get(j+3).split(";")[3])); //DELETE
							}
		
							pst.setInt(1, Integer.parseInt(new_idr)); //ID RUOLO
							pst.setInt(2, Integer.parseInt(as[0]));	  //ID PERMESSO	
							if (query.contains("entered") && query.contains("modified")){  //CAMPI AGGIUNTIVI NON COMUNI A TUTTI I DB
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
						if (tipo1.equals("vam")){ //db destinazione tipo vam
							int max=0;
							Statement statement=null;
							ResultSet rs = null;
							statement = connection.createStatement();
							rs = statement.executeQuery("SELECT max(id) FROM capability");	//RICAVA NUOVO ID DEL PERMESSO
							while (rs.next()){
								max = rs.getInt(1);
							}
							pst.setInt(1, max+1);
							pst.setString(2, r.getRole());
							pst.setString(3, as[0]);
							pst.executeUpdate();
							rs.close();
							
							pst2 = connection.prepareStatement("INSERT INTO capability_permission values (?,?)");	//INSERISCI VALORE PERMESSO
							pst2.setInt(1, max+1);
							pst2.setString(2, "w");
							pst2.executeUpdate();
							pst2.close();
						}
					}
				}
			}			
			connection.close();
			pst.close();	
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	/************************************************************
	 * AGGIORNA DB METADATI										*
	 * INPUT STRING ID_DB1, ID_DB2, 							*
	 * 		 STRING INFO_1 (ID_RUOLO_1|ID_PERMESSO_1), 			*
	 * 		 STRING INFO_2 (ID_RUOLO_2|ID_PERMESSO_2), 			*
	 * 		 STRING COMMAND (INSERT|DELETE -> RUOLO|PERMESSO)	*
	 * OUTPUT INT STATUS (OPERAZIONE ESEGUITA)					*
	 ************************************************************/
	private int aggiornaMetadati(int d1, int d2, String r1, String r2, String command){
		int status=0;
		try{
			Statement statement=null;
			String driverName = "org.postgresql.Driver";
			String databaseURL = "jdbc:postgresql://"+meta_ip+":"+meta_port+"/"+meta_name;	
			Class driverClass = Class.forName(driverName);
			Driver driver = (Driver) driverClass.newInstance();

			String query=null;
			Connection connection = DriverManager.getConnection(databaseURL, meta_user , meta_password);
			if(command.equals("INS_R"))		//NUOVA ASSOCIAZIONE TRA RUOLI
				query = "INSERT into associazione_ruoli (id_db1,id_db2,id_r1,id_r2) values ("+d1+","+d2+","+r1+","+r2+")";
			if(command.equals("DEL_R"))		//ELIMINA ASSOCIAZIONE TRA RUOLI
				query = "DELETE FROM associazione_ruoli WHERE (id_db1="+d1+" and id_db2="+d2+" and id_r1="+r1+" and id_r2="+r2+") OR (id_db1="+d2+" and id_db2="+d1+" and id_r1="+r2+" and id_r2="+r1+")";
			if(command.equals("INS_P"))		//NUOVA ASSOCIAZIONE TRA PERMESSI
				query = "INSERT into associazione_permessi (id_db1,id_db2,id_p1,id_p2) values ("+d1+","+d2+",'"+r1+"','"+r2+"')";
			if(command.equals("DEL_P"))		//ELIMINA ASSOCIAZIONE TRA PERMESSI
				query = "DELETE FROM associazione_permessi WHERE (id_db1="+d1+" and id_db2="+d2+" and id_p1='"+r1+"' and id_p2='"+r2+"') OR (id_db1="+d2+" and id_db2="+d1+" and id_p1='"+r2+"' and id_p2='"+r1+"')";
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
}

