/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;



public class CountView {
	
//	  CountView.getCount(asl,tipo,prov,comune,nome,categoria,idf,numVerbale,data1,data2,setCount);
	
	public static int getCount(int asl,int tipo_attivita,String identificativo, String numVerbale, String checkOp, String checkAtt, String checkEsito, String tipoRicerca)
	{
		Logger logger = Logger.getLogger("MainLogger");
		
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int value = -1;
		String select = "";
				
		try
		{
			db = GestoreConnessioni.getConnection()	;
			if(tipoRicerca.equals("op")){
				select = " select distinct org_id from view_globale_trashed_no_trashed WHERE TRUE ";
			}
			else{
				select = " select count(*) as recordcount from view_globale_trashed_no_trashed WHERE TRUE ";
			}
			
			
			if(asl != -1 && asl != -2)
			{
				select+= " AND asl_rif = ? ";
			}
			
			//aggiunta per la gestione del trashed_date
			if(checkOp != null && !"".equals(checkOp)){
					if(checkOp.contains("no_"))
						select+= " AND data_cancellazione_operatore IS NULL ";
					else {
						select+= " AND data_cancellazione_operatore IS NOT NULL ";
					}
			}
				
			if(checkAtt != null && !"".equals(checkAtt)){
					if(checkAtt.contains("no_"))
						select+= " AND data_cancellazione_attivita IS NULL ";
					else {
						select+= " AND data_cancellazione_attivita IS NOT NULL ";
					}
				}
			
			if(checkEsito != null && !"".equals(checkEsito)){
					select+= " AND esito ilike ? ";
			}
		
			
			if(tipo_attivita > -1){
				select+= " AND tipo_attivita = ? ";
			}
			
			if(identificativo != null && !"".equals(identificativo)){
				select+= " AND id_cu ILIKE ? ";
			}
			
			if(numVerbale != null && !"".equals(numVerbale)){
				select+= " AND num_verbale ILIKE ? ";
			}
			
			if(tipoRicerca.equals("op")){
				select = "select count(*) as recordcount from ( " + select + " ) v";
			}
			
			pst = db.prepareStatement(select);
			int i=0;
			
			if(asl!=-1 && asl!=-2){
				 pst.setInt(++i, asl);
			}
			 
			 if (checkEsito != null && checkEsito.contains("ok_")) {
				 pst.setString(++i,  "%respinto%".toLowerCase()); //prevedere il caso in cui il campione e respinto anche quando e senza esito per tot.tempo...
			 }else {
				 pst.setString(++i,  "%N.D%".toLowerCase());
			 }
			 
			 if(tipo_attivita > -1){
				 pst.setInt(++i, tipo_attivita);
			 }
			 
			 
			 if (identificativo != null && !"".equals(identificativo)) {
			      pst.setString(++i, "%"+identificativo.toLowerCase()+"%");
			 }
			 
			 if (numVerbale != null && !"".equals(numVerbale)) {
			      pst.setString(++i, "%"+numVerbale.toLowerCase()+"%");
			 }
		 
			 rs = pst.executeQuery();
			
			while ( rs.next() )
			{
				value	= rs.getInt("recordcount")	;
								
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		//System.out.println("RECORD COUNT AJAX: " +value);
		return value;

	}	
	
	

	public static int getCountQuery(int asl,int tipo_attivita,String identificativo, String num_verbale,int numero, String checkOp, String checkAtt, int qual, String inizio, String fine)
	{
		Logger logger = Logger.getLogger("MainLogger");
		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int value = -1;
		
		try
		{
			db = GestoreConnessioni.getConnection();
			String select 	=  " SELECT count(count) from (" + " SELECT count(*) as count, ragione_sociale, org_id, asl, tipologia_operatore, " +
							   " categoria_rischio, tipo_attivita, " +
							   " tipologia_campioni" +
							   " FROM view_globale_trashed_no_trashed " +
							   " WHERE TRUE  ";
			
			if(asl != -1 && asl != -2)
			{
				select+= " AND asl_rif = ? ";
			}
			
			
			//aggiunta per la gestione del trashed_date
			if(checkOp != null && !"".equals(checkOp)){
				if(checkOp.contains("no_"))
					select+= " AND data_cancellazione_operatore IS NULL ";
				else {
					select+= " AND data_cancellazione_operatore IS NOT NULL ";
				}
			}
			
			if(checkAtt != null && !"".equals(checkAtt)){
				if(checkAtt.contains("no_"))
					select+= " AND data_cancellazione_attivita IS NULL ";
				else {
					select+= " AND data_cancellazione_attivita IS NOT NULL ";
				}
			}
			
			
			
			if(tipo_attivita > -1){
				//Non ho selezionato la voce nessuno...
				if(qual !=4 ){
					select+= " AND tipo_attivita = ? ";
				}
				else{
					if(tipo_attivita == 3){
						select+= " AND (tipo_attivita NOT IN (1,2,3,4,6,7,8,9,15,700) OR tipo_attivita IS NULL)  ";
					}
					else{
						select+= " AND NOT tipo_attivita = ? ";
					}
				}
					
					
			}
			
			if(identificativo != null && !"".equals(identificativo)){
				select+= " AND id_cu ILIKE ? ";
			}
			
			
			if (num_verbale != null && !"".equals(num_verbale)) {
				select+=" AND " + DatabaseUtils.toLowerCase(db) + "(num_verbale) ILIKE ? ";       
			}
			
			if(inizio != null && !(inizio.equals(""))){   //CAST('2009-01-08 00:01:00' AS TIMESTAMP) 
		    	 if(fine != null && !(fine.equals(""))){
		    		 select+= " AND data_inizio_controllo "+
		    				 "between to_date('"+inizio+"', 'dd-mm-yyyy') and to_date('"+fine+"', 'dd-mm-yyyy') ";
		    	 }
		    	 else{
		    		 select += " AND data_inizio_controllo >= to_date('"+inizio+"', 'dd-mm-yyyy') "; 
		    	 }
		    }
		    else { //data_inizio non e valoizzata
		    	if(fine != null && !(fine.equals(""))){
		    		 select +=" AND data_inizio_controllo <= to_date('"+fine+"', 'dd-mm-yyyy' )"; 
			    }
		    }
			
			
			 select+= " GROUP BY ragione_sociale, org_id, asl, tipologia_operatore, " +
				        " categoria_rischio, tipo_attivita, " +
				        " tipologia_campioni  " ;
			
			if (qual != -1 && numero!= -1) {
				if (qual == 1){
					select+=" HAVING count(*) >= ? ";
				}
				else if (qual == 2){
					select+=" HAVING count(*) <= ? ";
				}
				else if(qual == 3){
					select+=" HAVING count(*) = ? ";
				}
				else{
					//select+=" HAVING count(*) >= ? ";
				}
			 }
			 
			 
			select+= " ) prova";
			
			pst = db.prepareStatement(select);
			int i=0;
			
			if(asl!=-1 && asl!=-2){
				 pst.setInt(++i, asl);
			}
			 
			 
			 
			 if(tipo_attivita > -1){
				 if(qual != 4 || tipo_attivita != 3){
					 pst.setInt(++i, tipo_attivita);
				 }
			 }
			 
			 if (identificativo != null && !"".equals(identificativo)) {
			      pst.setString(++i, "%"+identificativo.toLowerCase()+"%");
			 }
			 
			 if (num_verbale != null && !"".equals(num_verbale)) {
			      pst.setString(++i, "%"+num_verbale.toLowerCase()+"%");
			 }
			 
			  
			 if (numero > -1) {
				 pst.setInt(++i, numero);
			 }
			 
			 
			 rs = pst.executeQuery();
			
			while ( rs.next() )
			{
				value	= rs.getInt("count")	;
								
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}

		//System.out.println("tot:" +value);
		return value;

	}	
	
	
	
	
	
	public static String getProvincia(int asl){
	
		//System.out.println("Asl vale " + asl);
		String value = null;
		if(asl > -1 && asl > -2){
			switch (asl){
				case 201 : value = "AV"; break;
				case 202 : value = "BN"; break;
				case 203 : value = "CE"; break;
				case 204 : value = "NA"; break;
				case 205 : value = "NA"; break;
				case 206 : value = "NA"; break;
				case 207 : value = "SA"; break;
				
			}
		}
		
		//System.out.println("provincia vale: "+value);
		return value;
	}
		
}
	
	   
