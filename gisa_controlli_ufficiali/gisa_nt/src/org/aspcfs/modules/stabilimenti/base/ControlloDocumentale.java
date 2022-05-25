/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stabilimenti.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.modules.stabilimenti.storico.CampoModificato;
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.CustomLookupList;

public class ControlloDocumentale {
	private int id ;
	private Timestamp modifiedAsl ;
	private Timestamp modifiedStap ;
	private int userIdAsl ;
	private int userIdStap ;
	private int statoStap ;
	private int statoAsl ;
	private String utenteAsl ;
	private String utenteStap ; 
	private boolean esitoControlloFavorevole ;
	CustomLookupList cll = new CustomLookupList();

	public void buildControlloDocumentale(Connection db , int idStabilimento)
	{

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT qcds.*,(c.namelast || c.namefirst) as modificato_da_asl,(c.namelast || c.namefirst) as modificato_da_asl ,(c2.namelast || c2.namefirst) as modificato_da_stap " +
				"from quesiti_controllo_documentale_stabilimenti  qcds " +
				"left join contact c on (c.user_id=qcds.user_id_asl) " +
				"left join contact c2 on (c2.user_id =qcds.user_id_stap) "+
				" where  id_stabilimento = ?  order by qcds.id ");
		try
		{
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(1, idStabilimento);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
				modifiedAsl = rs.getTimestamp("modified_asl");
				modifiedStap = rs.getTimestamp("modified_stap");
				id = rs.getInt("id");
				statoStap = rs.getInt("stato_stap");
				statoAsl = rs.getInt("stato_asl");
				userIdAsl = rs.getInt("user_id_asl");
				userIdStap = rs.getInt("user_id_stap");
				utenteAsl = rs.getString("modificato_da_asl");
				utenteStap = rs.getString("modificato_da_stap");
				
			}
			rs.close();
			pst.close();

			cll = new CustomLookupList();
			cll.buildListControlloDocumentale(db,idStabilimento);

		}
		catch(SQLException e)
		{
			e.printStackTrace();

		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getModifiedAsl() {
		return modifiedAsl;
	}

	public void setModifiedAsl(Timestamp modifiedAsl) {
		this.modifiedAsl = modifiedAsl;
	}

	public Timestamp getModifiedStap() {
		return modifiedStap;
	}

	public void setModifiedStap(Timestamp modifiedStap) {
		this.modifiedStap = modifiedStap;
	}

	public int getUserIdAsl() {
		return userIdAsl;
	}

	public void setUserIdAsl(int userIdAsl) {
		this.userIdAsl = userIdAsl;
	}

	public int getUserIdStap() {
		return userIdStap;
	}

	public void setUserIdStap(int userIdStap) {
		this.userIdStap = userIdStap;
	}

	public int getStatoStap() {
		return statoStap;
	}

	public void setStatoStap(int statoStap) {
		this.statoStap = statoStap;
	}

	public int getStatoAsl() {
		return statoAsl;
	}

	public void setStatoAsl(int statoAsl) {
		this.statoAsl = statoAsl;
	}

	public String getUtenteAsl() {
		return utenteAsl;
	}

	public void setUtenteAsl(String utenteAsl) {
		this.utenteAsl = utenteAsl;
	}

	public String getUtenteStap() {
		return utenteStap;
	}

	public void setUtenteStap(String utenteStap) {
		this.utenteStap = utenteStap;
	}

	public CustomLookupList getCll() {
		return cll;
	}
	
	

	
	
	public boolean getEsitoControlloFavorevole() {
		boolean risposta_asl ; 
		boolean risposta_stap ; 

		Iterator it = cll.iterator();

		if (statoAsl == 1 && statoStap==1) // se lo stato e' definitivo siap er l'asl che per lo tap
		{
			esitoControlloFavorevole = true ;
			while (it.hasNext())
			{
				CustomLookupElement cle = (CustomLookupElement) it.next();
				risposta_asl = Boolean.parseBoolean(cle.getValue("risposta_asl"));
				risposta_stap= Boolean.parseBoolean(cle.getValue("risposta_stap"));
				if (risposta_asl != risposta_stap)
				{
					esitoControlloFavorevole = false ;
				}
			}
		}
		else
		{
			esitoControlloFavorevole = false ;
		}
		return esitoControlloFavorevole ;
	}

	
	public void setEsitoControlloFavorevole(boolean esitoControlloFavorevole) {
		this.esitoControlloFavorevole = esitoControlloFavorevole;
	}

	public void setCll(CustomLookupList cll) {
		this.cll = cll;
	}
	
public ArrayList<CampoModificato> checkModifiche(Connection db, ControlloDocumentale org) throws SQLException {

		
		Field[] campi = this.getClass().getDeclaredFields();

		ArrayList<CampoModificato> nomiCampiModificati = new ArrayList<CampoModificato>();

		Method metodo = null;
	
		for (int i = 0; i < campi.length; i++) {
			
			int k = 0;
			
			String nameToUpperCase = (campi[i].getName().substring(0, 1).toUpperCase() + campi[i].getName().substring(1,campi[i].getName().length()));
			//Escludere in questo if i campi che non vogliamo loggare
			if (!(nameToUpperCase.equals("id"))){
							
				//verifica se gia' esistono i campi della classe 
				PreparedStatement pst = db.prepareStatement("select count(*) as recordcount from lista_campi_classi where nome_classe = ? and nome_campo = ? ");
				pst.setString(1, this.getClass().getName());
				pst.setString(2, campi[i].getName().substring(0, 1).toUpperCase() + campi[i].getName().substring(1, campi[i].getName().length()));
				ResultSet rs = pst.executeQuery();
				
				int campiPresenti = 0;
				while(rs.next()){
					campiPresenti = rs.getInt("recordcount");
				}
				
				if(campiPresenti == 0){
					PreparedStatement pst2 = db.prepareStatement("Insert into lista_campi_classi (nome_campo, tipo_campo, nome_classe) values (?, ?, ?)");
					pst2.setString(1, campi[i].getName().substring(0, 1).toUpperCase() + campi[i].getName().substring(1, campi[i].getName().length()));
					pst2.setString(2, campi[i].getType().getSimpleName());
					pst2.setString(3, this.getClass().getName());
					pst2.executeUpdate();
					pst2.close();
				}	
				
				try {
					metodo = this.getClass().getMethod("get" + nameToUpperCase, null);
				} catch (NoSuchMethodException exc) {
				}

				if (metodo != null)
					try {
						if (((metodo.invoke(org) != null && metodo.invoke(this) != null) && !(metodo.invoke(org).equals(metodo.invoke(this))))
								|| (metodo.invoke(org) == null && metodo.invoke(this) != null)
								|| (metodo.invoke(org) != null && metodo.invoke(this) == null)) {
							CampoModificato campo = new CampoModificato();
							campo.setValorePrecedenteStringa(metodo.invoke(
										org).toString());
								campo.setValoreModificatoStringa(metodo.invoke(
										this).toString());
							campo.setNomeCampo(nameToUpperCase);
							campo.setValorePrecedente(metodo.invoke(org)
									.toString());
							campo.setValoreModificato(metodo.invoke(this)
									.toString());
							nomiCampiModificati.add(campo);
							k++;
						}

					} catch (Exception ecc) {
					}
					finally{
						
						rs.close();
					}
			}

		}
		
		return nomiCampiModificati;
	}
	
	
}
