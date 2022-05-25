/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;


public class CampiEstesiV2 {

	public static ArrayList<HtmlTag> buildList(JSONArray ja, int idRelStabLp, int idIstanzaValore,  Connection db) throws Exception   /*se idRelStabLP e' != -1 cerca di agganciargli il valore */
	{
		ArrayList<HtmlTag> toRet = new ArrayList<HtmlTag>();
		for(int i = 0; i< ja.length(); i++)
		{
			JSONObject jsonCampo = (JSONObject) ja.get(i);
			String tipoCampo = (String)HtmlTag.getSafe(jsonCampo,"type");
			/*a seconda del campo l'actual type e' diverso */
			
			HtmlTag toAdd = null;
			if(tipoCampo.equalsIgnoreCase("text"))
			{
				toAdd = TextField.build(jsonCampo,idRelStabLp,idIstanzaValore,db );
			}
			/*todo implementa gli altri tipi*/
			else if(tipoCampo.equalsIgnoreCase("option"))
			{
				toAdd = Option.build(jsonCampo, idRelStabLp,  idIstanzaValore, db);
			}
			else if(tipoCampo.equalsIgnoreCase("select"))
			{
				toAdd = SelectOption.build(jsonCampo, idRelStabLp, idIstanzaValore, db);
			}
			else if(tipoCampo.equalsIgnoreCase("checkbox"))
			{
				toAdd = CheckBox.build(jsonCampo, idRelStabLp,idIstanzaValore, db);
			}
			else if(tipoCampo.equalsIgnoreCase("radio"))
			{
				toAdd = RadioGroup.build(jsonCampo, idRelStabLp,idIstanzaValore, db);
			}
			toRet.add(toAdd);
		}
		return toRet;
	}
	
	/*per una linea c'e' un solo template (un insieme di campi estesi ciascuno identificato da un oggetto json, tutti raggruppati in un json array */
	/*se passo un valore diverso da -1 per id stab rel lp cerca di agganciargli anche i valori */
	/*i valori sono in un json array associato a id_rel_stab_lp, pero' ce ne possono esser epiu' di uno, con id diverso, se ci sono diverse istanze del template */
	public static ArrayList<HtmlTag> findTemplateCampiDaLinea(Integer idLinea, int idRelStabLp ,int idIstanzaValore ,Connection conn ) throws Exception 
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<HtmlTag> toRet = new ArrayList<HtmlTag>();
		
		try
		{
			/*per hp SEMPRE UNICO TEMPLATE PER ID LINEA ML*/
			pst = conn.prepareStatement("select  a.template_json from campi_estesi_templates_v2 a where a.id_linea = ? ");
			pst.setInt(1, idLinea); 
			rs = pst.executeQuery();
			if(rs.next())
			{
				if(idIstanzaValore > 0)
				{
					System.out.println(">>>>>>IL TEMPLATE DI CONFIGURAZIONE CAMPI PER LA LINEA MASTER LIST (UNICO in campi_estesi_templates_v2) HA INOLTRE PER L'ISTANZA DI LINEA (IDRELSTABLP)  "+idRelStabLp+" ASSOCIATO UN SET DI"
							+ "  VALORI (in campi_estesi_valori_v2) CON ID ISTANZA VALORI "+idIstanzaValore);
				}
				else
				{
					System.out.println(">>>>>>IL TEMPLATE DI CONFIGURAZIONE CAMPI PER LA LINEA MASTER LIST (UNICO in campi_estesi_templates_v2) NON HA PER L'ISTANZA DI LINEA (IDRELSTABLP)  "+idRelStabLp+" ASSOCIATO UN SET DI"
							+ "  VALORI (in campi_estesi_valori_v2) ");
				}
				
				String ts = rs.getString(1);
				/*per hp e' sempre un json array, che rappresenta il template */
				JSONArray ja = new JSONArray(ts);
				toRet =  CampiEstesiV2.buildList(ja, idRelStabLp, idIstanzaValore, conn);
			}
			
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
		return toRet;
	}
	
	
	public static JSONObject getCampiEstesi( Integer idLinea, int idRelStabLp  ,Connection conn  ) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		JSONObject toRet = new JSONObject();
		
		try
		{
			
			System.out.println(">>>>>>CERCO CAMPI ESTESI PER LINEA CON ID (MASTER LIST) "+idLinea+", CERCANDONE TUTTE LE POSSIBILI ISTANZE DI VALORE (ID ISTANZA VALORE) ASSOCIATE ALLA LINEA CON ID ISTANZA (IDRELSTABLP) "+idRelStabLp);
			boolean atLeastOneValue = false;
			/*ottengo, per quell'idrelstablp tutte le diverse istanze di valori (SE ESISTONO) ovviamente per quell'istanza di linea*/
			pst = conn.prepareStatement("select id from campi_estesi_valori_v2 where id_rel_stab_lp = ?");
			pst.setInt(1, idRelStabLp);
			rs = pst.executeQuery();
			while(rs.next())
			{
				int idIstanzaValore = rs.getInt(1);
				ArrayList<HtmlTag> htmlTags = CampiEstesiV2.findTemplateCampiDaLinea(idLinea, idRelStabLp, idIstanzaValore, conn); 
				/*lo trasformo in un JSONArray */
				JSONArray toPut = new JSONArray();
				for(HtmlTag tag : htmlTags)
				{
					try
					{
						JSONObject obCampo = new JSONObject();
						obCampo.put("nome_campo", tag.name+"");
						obCampo.put("html", tag.generaHtml());
						toPut.put(obCampo);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				toRet.put(idIstanzaValore+"", toPut);
				atLeastOneValue = true;
			}
			
			if(!atLeastOneValue)
			{
				/*se entra qui non esiste neanche un valore per questo template di campi estesi, quindi non cerco di associargli valore */
				ArrayList<HtmlTag> htmlTags = CampiEstesiV2.findTemplateCampiDaLinea(idLinea, idRelStabLp, -1, conn); 
				/*lo trasformo in un JSONArray */
				JSONArray toPut = new JSONArray();
				for(HtmlTag tag : htmlTags)
				{
					JSONObject obCampo = new JSONObject();
					obCampo.put("nome_campo", tag.name);
					obCampo.put("html", tag.generaHtml());
					toPut.put(obCampo);
				}
				toRet.put(-1+"", toPut);
				atLeastOneValue = true;
			}
			 
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
		return toRet;
	}
	
	
	public static boolean upsertValoriCampi(int idRelStabLp, int idIstanzaVal, HashMap<String,String> campiArrivati ,Connection conn)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		JSONObject t = new JSONObject();
		JSONArray toInsert = new JSONArray();
		String strToInsert = null;
		boolean toRet = true;
		SimpleDateFormat sdf = new SimpleDateFormat("ss:mm:hh dd/MM/yyyy");
		
		try
		{
			if(idIstanzaVal < 0)
			{
				System.out.println(">>>>>>STO INSERENDO VALORI DI CAMPI ESTESI PER ISTANZA LINEA (IDRELSTABLP)"+idRelStabLp+" PER UNA NUOVA ISTANZA DI VALORI");
				/*allora sicuramente non esiste neanche un campo valorizzato per quell'istanza di linea */
				/*quindi tutti insert */
				/*creo array di  oggetti json e lo inserisco */
				for(String nomeCampo : campiArrivati.keySet())
				{
					String valCampo = campiArrivati.get(nomeCampo);
					t = new JSONObject();
					t.put("name", nomeCampo.toUpperCase() );
					t.put("value", valCampo);
					System.out.println("\t>>Insert di campo "+nomeCampo.toUpperCase()+" e valore "+valCampo);
					 
					toInsert.put( t);
				}
				
				strToInsert = toInsert.toString();
				
				pst = conn.prepareStatement("insert into campi_estesi_valori_v2(id_rel_stab_lp, valori_json) values(?,to_json(?::json)::json)");
				pst.setInt(1, idRelStabLp);
				pst.setString(2,strToInsert);
				pst.executeUpdate();
			}
			else
			{
				/*per l'id rel stab lp arrivato, e per l'id istanza valore (id sulla tabella dei valori) arrivati, devo prendere i soli campi che mi sono arrivati, andare sul json che sta sul db
				 * mettere quei campi a scaduti e inserire di nuovi valori. Cancellare tutta il vecchio contenuto del campo json, e sostituirlo con il nuovo
				 */
				pst = conn.prepareStatement("select valori_json from campi_estesi_valori_v2 where id = ? and id_rel_stab_lp = ?"); /*in realta' basterebbe restrizione su id*/
				pst.setInt(1, idIstanzaVal);
				pst.setInt(2, idRelStabLp);
				/*per hp e' unica tupla associata a quell'id relstablp con quell 'id (ovviamente) */
				rs = pst.executeQuery();
				if(!rs.next())
				{
					return false; /*non dovrebbe mai entrarci qui */
				}
				
				String oldJsonStr = rs.getString(1);
				JSONArray oldJsonArr = new JSONArray(oldJsonStr);
				int oldSizeArray = oldJsonArr.length(); /*me lo salvo poiche' questo array viene "allungato" con oggetti nuovi che sicuramente non saranno scaduti
				
				/*VOGLIO DISATTIVARE QUELLI CHE MI ARRIVANO CON VALORI DIVERSI RISPETTO A QUELLO CHE STAVA SUL DB (SUL DB COME ATTIVO OVVIAMENTE)
				 * E VOGLIO DISATTIVARE ANCHE QUELLI CHE NON MI ARRIVANO, MA CHE HANNO VALORI SUL DB (POICH' VUOL DIRE CHE SONO CHECKBOX CHE ERANO SETTATI E ORA SONO ANDATI IN OFF QUINDI NON ARRIVANO)
				 * E POI VOGLIO INSERIRE I SOLI VALORI CHE MI ARRIVANO
				 * QUINDI PER FARE QUESTO -> DISATTIVO (METTO A SCADUTE) TUTTE LE ENTRY DI VALORI PER I QUALI NON E' ARRIVATO UN VALORE DAL CLIENT IDENTICO 
				 * E INSERISCO SOLO QUELLI ARRIVATI
				 */
				
				for(int i = 0; i<oldSizeArray; i++) /*qui sto ciclando sui valori presenti in db */
				{
					JSONObject ob = oldJsonArr.getJSONObject(i);
					if(HtmlTag.getSafe(ob,"data_scadenza") != null) /*se e' uno scaduto non mi interessa */
						continue;
					
					boolean arrivatoUguale = false;
					for(String nomeCampo : campiArrivati.keySet())
					{
						
						if(ob.getString("name").equalsIgnoreCase(nomeCampo) && ob.getString("value").equals(campiArrivati.get(nomeCampo)))
						{
							arrivatoUguale = true;
							break;
						}
					}
					
					if(!arrivatoUguale)
					{
						/*allora o non e' arrivato, ma stava gia' come entry valore (allora e' un checkbox che passa da stato true a stato false)
						 * oppure e' arrivato, con valore differente rispetto a come era memorizzato
						 * in entrambi i casi quello vecchio va messo a scaduto
						 */
						ob.put("data_scadenza", sdf.format(new Date()));
						oldJsonArr.put(i,ob); /*lo risetto nell'array */
						System.out.println("\t>>Disabilito valore precedente di campo "+ob.getString("name")+" per ISTANZA LINEA "+idRelStabLp+" per ID ISTANZA VALORE"+idIstanzaVal+"");
					}
				}
				
				/* ora ciclo sugli arrivati e li inserisco*/
				for(String nomeCampo : campiArrivati.keySet())
				{
					 
					JSONObject newJsonObject = new JSONObject();
					newJsonObject.put("name", nomeCampo);
					newJsonObject.put("value", campiArrivati.get(nomeCampo));
					oldJsonArr.put(newJsonObject);
					System.out.println("\t>>Inserisco   campo "+nomeCampo+" con valore "+campiArrivati.get(nomeCampo)+" per ISTANZA LINEA (IDRELSTABLP)"+idRelStabLp+" per ID ISTANZA VAL"+idIstanzaVal);
				}
				
				
				/*a questo punto aggiungo IN UPDATE tutto l'array */
				pst.close();
				pst = conn.prepareStatement("update campi_estesi_valori_v2 set valori_json = to_json(?::json)::json where id = ? and id_rel_stab_lp = ?"); /*in realta' basterebbe restrizione su id*/
				pst.setString(1, oldJsonArr.toString() );
				pst.setInt(2, idIstanzaVal);
				pst.setInt(3,idRelStabLp);

				pst.executeUpdate();
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			toRet = false;
		}
		finally
		{
			try { rs.close(); } catch(Exception ex){}
			try { pst.close(); } catch(Exception ex){}
		}
		
		return toRet;
	}
}
