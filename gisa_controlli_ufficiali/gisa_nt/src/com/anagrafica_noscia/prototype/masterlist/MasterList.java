/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.masterlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONObject;

public class MasterList {
	
	/*ritorna rappresentazione nested della master list */
	public static HashMap<Integer,Macroarea> getMasterList(String descFlussoOrigine,Connection conn)
	{
		HashMap<Integer,Macroarea> macros = new HashMap<Integer,Macroarea>();
		HashMap<Integer,Aggregazione> aggs = new HashMap<Integer,Aggregazione>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = conn.prepareStatement("select * from MASTER_LIST_DENORM a join lookup_flusso_originale_ml b"
					+ " on a.id_flusso_originale_aggregazione = b.code "+ ( descFlussoOrigine != null ? "where b.description ilike ? " : ""));
			
			if(descFlussoOrigine != null)
				pst.setString(1,descFlussoOrigine);
			
			rs = pst.executeQuery();
			
			/*popolo come albero di 3 livello bottom up */
			while(rs.next())
			{
				Attivita att = new Attivita(rs,conn);
				int idAgg = rs.getInt("id_aggregazione");
				if(!aggs.containsKey(idAgg))
				{
					Aggregazione agg = new Aggregazione(rs);
					aggs.put(agg.getIdAggregazione(), agg);
				}
				
				Aggregazione agg = aggs.get(idAgg);
				agg.addFiglio(att);
				
				int idMacro = agg.getIdMacroarea();
				
				if(!macros.containsKey(idMacro))
				{
					Macroarea macro = new Macroarea(rs);
					macros.put(  macro.getIdMacroarea(), macro);
				}
				
				Macroarea macro = macros.get(idMacro);
				macro.addFiglio(agg);
			}
			
			
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
		
		
		return macros;
	}
	
	
	
	
	/*
	 * 
	 * ritorna json object { key : value } dove key e' id macroarea, value e' la macroarea
	 * e ciascuna delle macroareae contiene il nesting come json object dei figli
	 */
	public static JSONObject getMasterListAsJson(String descFlussoOrigine,Connection conn)
	{
		HashMap<Integer,Macroarea> ml = getMasterList(descFlussoOrigine,conn);
		JSONObject toRet  =new JSONObject();
		for(Integer macroId : ml.keySet())
		{
			toRet.put(macroId+"", ml.get(macroId).asJSONOb());
		}
		
		return toRet;
		
	}
	
	
	
}
