/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionidocumenti.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import org.aspcfs.modules.macellazioni.base.Art17;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.utils.DateUtils;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.beans.GenericBean;

public class RegistroMacellazioni  extends GenericBean {
	
	//private String tipo = null; 
	private int idMacello = -1; 
	private Timestamp data = null;
	private int seduta = -1;
	private int anno=-1;
	private int progressivoMacellazione = -1;
	private LinkedHashMap<String, String[]> listaElementi = new LinkedHashMap<String, String[]>();
	private int tipoComboMacelli =-1;
	
	public int getSeduta() {
		return seduta;
	}
	public void setSeduta(String seduta) {
		if (seduta!=null && !seduta.equals("") && !seduta.equals("null")){
			this.seduta = Integer.parseInt(seduta);
		}
	}
	public void setSeduta(int seduta) {
		this.seduta = seduta;
	}
//	public String getTipo() {
//		return tipo;
//	}
//	public void setTipo(String tipo) {
//		this.tipo = tipo;
//	}
	public int getIdMacello() {
		return idMacello;
	}
	public void setIdMacello(int idMacello) {
		this.idMacello = idMacello;
	}
	public void setIdMacello(String idMacello) {
		if (idMacello!=null && !idMacello.equals("null") && !idMacello.equals(""))
			this.idMacello = Integer.parseInt(idMacello);
	}
	
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public void setData(String data) {
		this.data = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	
	
	public LinkedHashMap<String, String[]> getListaElementi() {
		return listaElementi;
	}

	
	public void popolaDaRegistro(Connection db, ConfigTipo configTipo)
	{
		
		tipoComboMacelli = configTipo.getIdTipo();
		
		PreparedStatement stat = null;
		String select = "";
		String suffissoVista = "";
		suffissoVista = configTipo.getSuffissoTabelle();
		
		try 
		{
			select = " SELECT * FROM m_registro_macellazioni" + suffissoVista + 
					 " WHERE id_macello = ? AND ( data_macellazione = ? OR (data_macellazione is null and data_arrivo =  ? ) )";
			
			if (tipoComboMacelli == 1)
				select +=" order by progressivo asc";
			
			stat = db.prepareStatement( select );
			stat.setInt( 1, idMacello );
			stat.setTimestamp( 2, data );
			stat.setTimestamp( 3, data );
			System.out.println("Query RegistroMacellazioni: "+stat.toString());
			ResultSet res = stat.executeQuery();
			buildRecord(res);
	
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	
	private void buildRecord(ResultSet rs){
		try {
			int i = 0;
			
			if (tipoComboMacelli==1) {
			while (rs.next()){
				String progressivo ="", mod4 ="", specie="", codAllev="", matricola="", esito="", destinatarioCarni="", destinazioneCarcassa="", rischio="";
				Timestamp dataArrivo=null, dataMacellazione=null, dataNascita=null;
				progressivo = rs.getString( "progressivo" );
				mod4= rs.getString( "mod4" );
				dataArrivo= rs.getTimestamp( "data_arrivo" );
				specie = rs.getString( "specie" );
				codAllev = rs.getString( "codice_azienda_provenienza" );
				matricola=rs.getString( "matricola" );
				dataNascita= rs.getTimestamp( "data_nascita" );
				dataMacellazione= rs.getTimestamp( "data_macellazione" );
				esito=rs.getString( "esito_vpm" );
				destinatarioCarni=rs.getString( "destinatario_carni" );
				destinazioneCarcassa=rs.getString( "destinazione_carcassa" );
				rischio=rs.getString( "categoria_rischio" );
				String[] valori = {progressivo, mod4, toDateasString(dataArrivo), specie, codAllev, matricola, toDateasString(dataNascita), toDateasString(dataMacellazione), esito, destinatarioCarni, destinazioneCarcassa, rischio};
				listaElementi.put(String.valueOf(i), valori);
				i++;
			}
			}
			else if (tipoComboMacelli==2) {
				while (rs.next())
				{
					String numSeduta="", partita="", codAllev="", esito="", propAnimali="", oviniMacellatiSeduta="", capriniMacellatiSeduta, categoria_macellazione = "";
					int numCapiOvini=0, numCapiCaprini=0;
					int numCapiOviniMacellati=0, numCapiCapriniMacellati=0;
					Timestamp dataArrivo=null, dataMacellazione=null;
					partita 		= rs.getString( 	"partita" 		);
					dataArrivo		= rs.getTimestamp( 	"data_arrivo" 	);
					codAllev		= rs.getString(		"codice_allev"	);
					numSeduta		= rs.getString(		"num_seduta"	);
					propAnimali		= rs.getString(		"prop_animali"	);
					numCapiOvini =  rs.getInt( "totale_ovini");
					if (numCapiOvini<0)
						numCapiOvini=0;
					numCapiCaprini =  rs.getInt( "totale_caprini");
					if (numCapiCaprini<0)
						numCapiCaprini=0;
					numCapiOviniMacellati =  rs.getInt( "ovini_macellati_partita");
					if (numCapiOviniMacellati<0)
						numCapiOviniMacellati=0;
					numCapiCapriniMacellati =  rs.getInt( "caprini_macellati_partita");
					if (numCapiCapriniMacellati<0)
						numCapiCapriniMacellati=0;
					oviniMacellatiSeduta   = rs.getString( "ovini_macellati_seduta");
					capriniMacellatiSeduta = rs.getString( "caprini_macellati_seduta");
					if(numSeduta!=null && !numSeduta.equals("") && oviniMacellatiSeduta.equals(""))
						oviniMacellatiSeduta = "0";
					if(numSeduta!=null && !numSeduta.equals("") && capriniMacellatiSeduta.equals(""))
						capriniMacellatiSeduta = "0";
					dataMacellazione= rs.getTimestamp( "data_macellazione" );
					esito=rs.getString( "esito_vpm" );
					categoria_macellazione = (rs.getBoolean( "specie_suina" )) ? "SUINI" : "OVICAPRINI";

					String[] valori = {toDateasString(dataArrivo), 
							 		   partita, categoria_macellazione,
							 		   String.valueOf(numCapiOvini), 
							 		   String.valueOf(numCapiCaprini), 
							 		   String.valueOf(numCapiOviniMacellati), 
							 		   String.valueOf(numCapiCapriniMacellati),
							 		   codAllev, 
							 		   toDateasString(dataMacellazione), 
							 		   numSeduta,
							 		   String.valueOf(oviniMacellatiSeduta),
							 		   String.valueOf(capriniMacellatiSeduta),
							 		   propAnimali,
							 		   esito
							 		   };
					
					listaElementi.put(String.valueOf(i), valori);
					i++;
				}
				
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void buildRecord2(ResultSet rs){
		try {
				setAnno(rs.getInt( "anno" ));
				setProgressivoMacellazione(rs.getInt( "progressivo" ));
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getProgressivoMacellazione() {
		return progressivoMacellazione;
	}
	public void setProgressivoMacellazione(int progressivoMacellazione) {
		this.progressivoMacellazione = progressivoMacellazione;
	}
	
	private boolean trovaColonna(ResultSet rs, String column)
	{
	  try
	  {
	    rs.findColumn(column);
	    return true;
	  } catch (SQLException sqlex)
	  {
	   
	  }
	  return false;
	}
	
	private String toDateasString(Timestamp time)
	{
		  String toRet = "";
		  try
		  { 
			  if (time != null){
			  	java.sql.Date d = new java.sql.Date(time.getTime());
			  
			  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  	toRet=sdf.format(d);
		  }
		  }
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  return toRet;
	}
	
	
}