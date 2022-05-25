/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.campiestesiv2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;


public class SelectOption extends HtmlTag {
	
	
	@Override
	public String apriTag() {

		StringBuffer toRet = new StringBuffer("<select ");
//		toRet.append("type = \""+this.type+"\" ");
		toRet.append("name = \""+this.name+"\" ");
//		toRet.append("value = \""+this.valoreAssunto+"\" ");
		toRet.append("class = \"ordine-"+this.ordine+"\" ");
		for(Attr attr : attrs)
		{
			toRet.append(attr.getHtml());
		}
		for(Prop prop : props)
		{
			toRet.append(prop.getHtml());
		}
		toRet.append(" >");
		return toRet.toString();
	}

	
	@Override
	public String chiudiTag() {
		
		return "</select>";
	}
	
	
	public static SelectOption build(JSONObject jo, int idRelStabLp,  int idIstanzaValore,Connection db) throws Exception /*se idRelStabLp e' != -1 cerca di agganciargli il valore */
	{
		 SelectOption tf = new SelectOption();

		 tf.ordine = Integer.parseInt(getSafe(jo,"ordine") != null ? (String)getSafe(jo,"ordine") : "0");
		 tf.type = (String) getSafe(jo,"type");
		 tf.name = (String) getSafe(jo,"name");
		 tf.label = (String) getSafe(jo,"label");
		 JSONArray arrayAttrs = (JSONArray) getSafe(jo,"attrs");
		 JSONArray arrayProps = (JSONArray) getSafe(jo,"props");
		 tf.attrs = Attr.buildList(arrayAttrs);
		 tf.props = Prop.buildList(arrayProps);
		 
		 /*creo gli options figli, li metto come figli html tags da innestare  */
		 /*a seconda che siano specificati i valori, oppure che sia specificata una tabella di lookup */
		 if(getSafe(jo,"options")!= null && ((JSONArray)getSafe(jo,"options")).length() > 0)
		 { /*sono specificati i valori */
			 tf.childs =  CampiEstesiV2.buildList( (JSONArray)getSafe(jo,"options") , idRelStabLp, idIstanzaValore,db);
			
		 }
		 else if(getSafe(jo,"options_lookup_tabs") != null)
		 { 
			 JSONObject joTabellaLookup = (JSONObject) getSafe(jo,"options_lookup_tabs");
			 
			 String nomeTab = joTabellaLookup.getString("nome_tabella");
			 String idField = joTabellaLookup.getString("valore_field");
			 String labelField = joTabellaLookup.getString("label_field");
			 String defaultid = joTabellaLookup.getString("default_code");
			 String restrizione = (String) getSafe(joTabellaLookup,"restrizione");
			 tf.childs=getOptionsFromLookupTable(nomeTab,idField,labelField,defaultid, idRelStabLp, idIstanzaValore,restrizione,db);
			 
		 }
			 
		 
		 /*per quanto riguarda il valore settato, devo usare il valore secco per scegliere quale option selezionare */
		 if(idRelStabLp > 0 && idIstanzaValore > 0)
		 {
			 tf.searchAndSetValue(db, idRelStabLp, idIstanzaValore);
		 }
		 
		 return tf;
		
	}
	 
	
	@Override
	/*override di genera html perchx ho bisogno di selezionare il figlio option a seconda di quello che x il valore selezionato per la select */
	/*se non x selezionato nessun valore per la select, allora uso il default option come elemento da mostrare */
	
	public String generaHtml()
	{
		if(this.valoreAssunto == null || this.valoreAssunto.trim().length() == 0) 
		{
			return super.generaHtml(); /*se non x scelto nessun valore, posso usare tranquillamente la generazione generica dell'html */
		}
		else /*in caso contrario, ho un valore settato per la select, devo ridefinire la generazione html */
		{
			String toRet = this.label != null ? this.label+"&nbsp;" : "";
			toRet += apriTag();
			/*ciclo sui figli, e se becco quello che era di default, gli levo la proprietx di default prima di generarne l'html 
			 * cosi' non risulterx come il selezionato, mentre la metto sull'option che ha lo stesso idValore uguale all'id valore messo per la select 
			 */
			for(HtmlTag tagFiglio : childs)
			{
				Option tagFiglioOp = (Option)tagFiglio;
				
				if(tagFiglioOp.valoreAssunto.equalsIgnoreCase(this.valoreAssunto))
				{
					tagFiglioOp.isDefault = true;
				}
				else
				{
					tagFiglioOp.isDefault = false; /*questo leverx eventualmente anche quello che nel template era settato sul default */
				}
				
				toRet += tagFiglioOp.generaHtml();
			}
			
			toRet += chiudiTag();
			toRet = wrapIn("<div>",toRet);
			return toRet;
		}
		
	}
	

	protected static ArrayList<HtmlTag> getOptionsFromLookupTable(String nomeTab, String valoreField, String labelField, String valoreDefault, int idRelStabLp,int idIstanzaValore,String restrizione,Connection db ) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<HtmlTag> toRet = new ArrayList<HtmlTag>();
		try
		{
			pst = db.prepareStatement("select * from "+nomeTab+ (restrizione != null ? " where "+restrizione : ""));
			rs = pst.executeQuery();
			while(rs.next())
			{
				JSONObject jo = new JSONObject();
				String label = rs.getString(labelField);
				String valore = rs.getString(valoreField);
				/*queste proprietx estratte dal db le setto al json object poiche' se le aspetta il metodo di Option.build al suo interno */
				jo.put("valore",valore);
				jo.put("label", label);
				
				if(valoreDefault.equalsIgnoreCase(valore)) 
				{
					jo.put("default", "true");
				}
				Option opt = Option.build(jo,idRelStabLp,idIstanzaValore,db);
				toRet.add(opt);
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		return toRet;
	}

}
