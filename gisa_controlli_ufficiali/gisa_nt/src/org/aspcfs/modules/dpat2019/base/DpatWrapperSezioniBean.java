/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniNewBeanAbstract;
import org.json.JSONArray;


public class DpatWrapperSezioniBean extends DpatWrapperSezioniNewBeanAbstract<DpatSezioneNewBean>{
	  
	
	public JSONArray getJsonArray()
	{
		JSONArray toRet = new JSONArray();
		for(DpatSezioneNewBean bean : sezioni)
		{
			toRet.put(bean.getJsonObj());
		}
		return toRet;
	}
	
	public DpatWrapperSezioniBean(){}
	
	public DpatWrapperSezioniBean(int anno, Connection conn,boolean nonscaduti,boolean conFigli)
	{
		this.anno = anno;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query =  "select * from dpat_sez_new where anno = ? "+(nonscaduti ? " and data_scadenza is null": "")+" order by ordinamento,descrizione  " ;

		try
		{
			pst = conn.prepareStatement(query);
			pst.setInt(1, anno);
			 
			rs = pst.executeQuery();
			setSezioni(new DpatSezioneNewBean().buildList(conn, rs,nonscaduti,conFigli));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	
	public  int  getStatoDopoModifica(Connection db,int anno) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pst = null;
		int stato = -1;
		try
		{
			 
			/*logica scelta dello stato*/
			 /*SE IN TUTTO L'ALBERO, PER QUELL'ANNO, ESISTE ALMENO UN NODO IN STATO 2, VUOL DIRE CHE QUELL'ALBERO AD UN CERTO PUNTO E' STATO CONGELATO, QUINDI
			  * QUALUNQUE OPERAZIONE EFFETTUATA SU DI ESSO DEVE FAR ANDARE IL NODO IN STATO 0 (op post congelamento)
			  * SE INVECE NON ESISTONO NODI IN STATO 2 PUO ' ESSERE O PERCHE' E' UN ALBERO IN STATO INTERMEDIO DI MODIFICA (TUTTI 1, pre congelamento) E QUINDI IN TAL CASO
			  * LE OPERAZIONI SONO DI PRE-CONGELAMENTO (QUINDI DEVONO FAR ANDARE IN STATO 1) OPPURE PERCHE' IN POST CONGELAMENTO SI SONO FATTI DIVENTARE TUTTI I NODI DA STATO 2 A STATO0
			  * MA E' POCO PLAUSIBILE POICHE' QUESTO SIGNIFICHEREBBE CHE SONO STATI PUNTUALMENTE MODIFICATI TUTTI I NODI
			  */
			 /*per lo stato */
			
			 pst = db.prepareStatement("select  greatest(max(a.stato), max(b.stato)) as massimo from dpat_piano_attivita_new a join dpat_indicatore_new b on a.id = b.id_piano_attivita where a.anno = ? and a.data_scadenza is null and b.data_scadenza is null");
			 pst.setInt(1, anno);
			 rs = pst.executeQuery();
			 rs.next();
			 int max = rs.getInt(1);
			  stato = max % 2; /* se il massimo e' 2 o e' 0 allora e' modifica post congelamento, altrimenti se il massimo e' 1, sicuramente non esistono anche nodi in 0, quindi op pre congelamento */
			 return stato;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
}
