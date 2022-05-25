/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedeCentralizzate.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspcfs.utils.DatabaseUtils;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.actions.ActionContext;

public class ListaOperatori {
	
	Logger logger = Logger.getLogger("MainLogger");
	
	private String ragioneSociale;
	public String getRagioneSociale() {
		return ragioneSociale;
	}



	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}



	public String getPartitaIva() {
		return partitaIva;
	}



	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}



	public String getTarga() {
		return targa;
	}



	public void setTarga(String targa) {
		this.targa = targa;
	}



	public String getComune() {
		return comune;
	}



	public void setComune(String comune) {
		this.comune = comune;
	}




	private String partitaIva;
	private String targa;
	private String comune;
	
	
	private LinkedHashMap<String, String[]> listaElementi = new LinkedHashMap<String, String[]>();
	
	
	
	public void popolaScheda(Connection db){
		
		PreparedStatement pst;
		PreparedStatement pst2;
		int rowLine = -1;
		String id = "";
		String nome = "";
		String comune = "";
		String targa = "";
		String partitaIva = "";
		String tipologia = "";
		String idTipoScheda ="";
		String objectIdName ="";
		String linkDettaglio ="";
		
		try {
			
			String querySelect = " select id, nome, partita_iva, targa, comune, tipologia, id_tipo_scheda, object_id_name from( ";
			String queryOrg = " select o.org_id as id, o.name as nome, o.partita_iva, o.nome_correntista as targa, a.city as comune, m.tipo_descrizione as tipologia, m.scheda_code as id_tipo_scheda, 'org_id' as  object_id_name from organization o left join organization_address a on a.org_id = o.org_id and a.address_type = 5 left join mapping_lookup_tipologia_scheda_operatore m on m.tipo_code = o.tipologia where o.trashed_date is null ";
			querySelect += queryOrg;
			querySelect += " UNION ";
			String queryStab = " select id_stabilimento as id, ragione_sociale as nome, partita_iva, '' as targa, comune_stab as comune, m.tipo_descrizione as tipologia, m.scheda_code as id_tipo_scheda, 'stab_id' as  object_id_name from opu_operatori_denormalizzati_view left join mapping_lookup_tipologia_scheda_operatore m on m.scheda_code = 15";
			querySelect += queryStab;
			querySelect +=" ) ss where ss.id > 0";
						
			if (this.getRagioneSociale()!=null && !this.getRagioneSociale().equals(""))
				querySelect+=" and ss.nome ilike ? ";
			if (this.getComune()!=null && !this.getComune().equals(""))
				querySelect+=" and ss.comune ilike ? ";
			if (this.getTarga()!=null && !this.getTarga().equals(""))
				querySelect+=" and ss.targa ilike ? ";
			if (this.getPartitaIva()!=null && !this.getPartitaIva().equals(""))
				querySelect+=" and ss.partita_iva ilike ? ";
			
			
			pst = db.prepareStatement(querySelect);
			int i = 0;
			
			if (this.getRagioneSociale()!=null && !this.getRagioneSociale().equals(""))
				pst.setString(++i, "%"+this.getRagioneSociale()+"%");
			if (this.getComune()!=null && !this.getComune().equals(""))
				pst.setString(++i, "%"+this.getComune()+"%");
			if (this.getTarga()!=null && !this.getTarga().equals(""))
				pst.setString(++i, "%"+this.getTarga()+"%");
			if (this.getPartitaIva()!=null && !this.getPartitaIva().equals(""))
				pst.setString(++i, "%"+this.getPartitaIva()+"%");
			
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				rowLine = rs.getRow();
				id = rs.getString("id");
				nome = rs.getString("nome");
				targa = rs.getString("targa");
				comune = rs.getString("comune");
				partitaIva = rs.getString("partita_iva");
				tipologia = rs.getString("tipologia");
				idTipoScheda = rs.getString("id_tipo_scheda");
				objectIdName = rs.getString("object_id_name");
				linkDettaglio = generaLinkDettaglio(id, idTipoScheda, objectIdName);
				
				String[] valori = {id, nome, targa, comune, partitaIva, tipologia, linkDettaglio};
				listaElementi.put(String.valueOf(rowLine), valori);
			
			
			}	
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	System.out.println("Scheda centralizzata errore su query "+query);
			e.printStackTrace();
		} 
	}
	
	
	
	public LinkedHashMap<String, String[]> getListaElementi() {
		return listaElementi;
	}

		


public ListaOperatori() {
	// TODO Auto-generated constructor stub
}

private String generaLinkDettaglio(String id, String idTipoScheda, String objectIdName){
	
	return "?object_id="+id+"&tipo_dettaglio="+idTipoScheda+"&object_id_name="+objectIdName+"&output_type=json&visualizzazione=screen";
}



}
