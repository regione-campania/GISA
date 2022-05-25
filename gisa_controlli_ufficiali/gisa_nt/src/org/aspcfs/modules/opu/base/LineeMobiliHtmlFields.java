/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.aspcfs.modules.campioni.base.ModuliCampioniHtmlFields;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.actions.EccezioneCodiceL30GiaInUso;
import org.aspcfs.utils.Jsonable;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class LineeMobiliHtmlFields extends Jsonable{
	private int id; 
	private int id_linea; 
	private String nome_campo; 
	private String tipo_campo; 
	private String label_campo; 
	private String tabella_lookup; 
	private String javascript_function; 
	private String javascript_function_evento; 
	private String link_value; 
	private int ordine_campo; 
	private int maxlength; 
	private int only_hd; 
	private String label_link; 
	private boolean multiple; 
	private boolean obbligatorio;
	private boolean redOnly=false ;
	private String dbiGenerazione;
	private String regex_expr;
	private String messaggio_se_invalid;
	private String valore_campo;
	private String indice;
	private Timestamp data_inserimento;
	private Integer id_utente_inserimento;
	private Timestamp data_modifica;
	private Integer id_utente_modifica;
	private LookupList lookupCampo ;
	
	public static int SOGLIA_EX_CODE_L30 = 200;
	
	public String getDbiGenerazione() {
		return dbiGenerazione;
	}
	public void setDbiGenerazione(String dbiGenerazione) {
		this.dbiGenerazione = dbiGenerazione;
	}
	
	public void setRegexExpr(String re)
	{
		this.regex_expr = re;
	}
	public String getRegexExpr()
	{
		return this.regex_expr;
	}
	
	public void setMessaggioSeInvalid(String msi)
	{
		this.messaggio_se_invalid = msi;
	}
	public String getMessaggioSeInvalid()
	{
		return this.messaggio_se_invalid;
	}
	
	public boolean isRedOnly() {
		return redOnly;
	}
	public void setRedOnly(boolean redOnly) {
		this.redOnly = redOnly;
	}
	public LookupList getLookupCampo() {
		return lookupCampo;
	}
	public void setLookupCampo(LookupList lookupCampo) {
		this.lookupCampo = lookupCampo;
	}

	private String html;

	public String getIndice() {
		return indice;
	}
	public void setIndice(String indice) {
		this.indice = indice;
	}
	
	public Timestamp getDataInserimento() {
		return data_inserimento;
	}
	public void setDataInserimento(Timestamp data_inserimento) {
		this.data_inserimento = data_inserimento;
	}
	
	public Integer getIdUtenteInserimento() {
		return id_utente_inserimento;
	}
	public void setIdUtenteInserimento(Integer id_utente_inserimento) {
		this.id_utente_inserimento = id_utente_inserimento;
	}
	
	public Timestamp getDataModifica() {
		return data_modifica;
	}
	public void setDataModifica(Timestamp data_modifica) {
		this.data_modifica = data_modifica;
	}
	
	public Integer getIdUtenteModifica() {
		return id_utente_modifica;
	}
	public void setIdUtenteModifica(Integer id_utente_modifica) {
		this.id_utente_modifica = id_utente_modifica;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_linea() {
		return id_linea;
	}
	public void setId_linea(int id_linea) {
		this.id_linea = id_linea;
	}
	public String getNome_campo() {
		return nome_campo;
	}
	public void setNome_campo(String nome_campo) {
		this.nome_campo = nome_campo;
	}
	public String getTipo_campo() {
		return tipo_campo;
	}
	public void setTipo_campo(String tipo_campo) {
		this.tipo_campo = tipo_campo;
	}
	public String getLabel_campo() {
		return label_campo;
	}
	public void setLabel_campo(String label_campo) {
		this.label_campo = label_campo;
	}
	public String getTabella_lookup() {
		return tabella_lookup;
	}
	public void setTabella_lookup(String tabella_lookup) {
		this.tabella_lookup = tabella_lookup;
	}
	public String getJavascript_function() {
		return javascript_function;
	}
	public void setJavascript_function(String javascript_function) {
		this.javascript_function = javascript_function;
	}
	public String getJavascript_function_evento() {
		return javascript_function_evento;
	}
	public void setJavascript_function_evento(String javascript_function_evento) {
		this.javascript_function_evento = javascript_function_evento;
	}
	public String getLink_value() {
		return link_value;
	}
	public void setLink_value(String link_value) {
		this.link_value = link_value;
	}
	public int getOrdine_campo() {
		return ordine_campo;
	}
	public void setOrdine_campo(int ordine_campo) {
		this.ordine_campo = ordine_campo;
	}
	public int getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}
	public int getOnly_hd() {
		return only_hd;
	}
	public void setOnly_hd(int only_hd) {
		this.only_hd = only_hd;
	}
	public String getLabel_link() {
		return label_link;
	}
	public void setLabel_link(String label_link) {
		this.label_link = label_link;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public boolean isObbligatorio() {
		return obbligatorio;
	}
	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	} 

	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

	public String getValore_campo() {
		return valore_campo;
	}
	public void setValore_campo(String valore_camo) {
		this.valore_campo = valore_camo;
	}
	
	public void buildRecord(Connection db, ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.id_linea=rs.getInt("id_linea");
		this.nome_campo=  rs.getString("nome_campo");
		this.tipo_campo=  rs.getString("tipo_campo");
		this.label_campo=  rs.getString("label_campo");
		this.tabella_lookup=  rs.getString("tabella_lookup");
		this.javascript_function=  rs.getString("javascript_function");
		this.javascript_function_evento=  rs.getString("javascript_function_evento");
		this.link_value =  rs.getString("link_value");
		this.ordine_campo = rs.getInt("ordine_campo");
		this.maxlength = rs.getInt("maxlength");
		this.only_hd=  rs.getInt("only_hd");
		this.label_link=  rs.getString("label_link");
		this.multiple=  rs.getBoolean("multiple");
		this.obbligatorio =  rs.getBoolean("obbligatorio");
		this.valore_campo=rs.getString("valore_campo");
		try {setDataInserimento(rs.getTimestamp("data_inserimento"));} catch(Exception ex){}
		try {setIdUtenteInserimento(rs.getInt("id_utente_inserimento"));} catch(Exception ex){}
		try {setDataModifica(rs.getTimestamp("data_modifica"));} catch(Exception ex){}
		try {setIdUtenteModifica(rs.getInt("id_utente_modifica"));} catch(Exception ex){}
		this.indice=rs.getString("indice");
		redOnly = rs.getBoolean("readonly");
		if (this.tabella_lookup!=null && !"".equals(tabella_lookup)){lookupCampo = new LookupList(db,tabella_lookup);}
		
		try{setDbiGenerazione(rs.getString("dbi_generazione"));} catch(Exception ex){ex.printStackTrace();}
		try {setRegexExpr(rs.getString("regex_expr"));} catch(Exception ex){ex.printStackTrace();}
		try {setMessaggioSeInvalid(rs.getString("messaggio_se_invalid"));} catch(Exception ex){ex.printStackTrace();}

	}
	
	/*
	public void buildRecordVersioneNuova(Connection db, ResultSet rs ) throws SQLException{

		this.id = rs.getInt("fields.id");
		this.id_linea=rs.getInt("fields.id_linea");
		this.nome_campo=  rs.getString("fields.nome_campo");
		this.tipo_campo=  rs.getString("fields.tipo_campo");
		this.label_campo=  rs.getString("fields.label_campo");
		this.tabella_lookup=  rs.getString("fields.tabella_lookup");
		this.javascript_function=  rs.getString("fields.javascript_function");
		this.javascript_function_evento=  rs.getString("fields.javascript_function_evento");
		this.link_value =  rs.getString("fields.link_value");
		this.ordine_campo = rs.getInt("fields.ordine_campo");
		this.maxlength = rs.getInt("fields.maxlength");
		this.only_hd=  rs.getInt("fields.only_hd");
		this.label_link=  rs.getString("fields.label_link");
		this.multiple=  rs.getBoolean("fields.multiple");
		this.obbligatorio =  rs.getBoolean("fields.obbligatorio");
		if(rs.getString("has_value").equalsIgnoreCase("si"))
		{
			this.valore_campo = rs.getString("vals.valore_campo");
		}
		
		try
		{
			setDbiGenerazione(rs.getString("fields.dbi_generazione"));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		

		if (this.tabella_lookup!=null && !"".equals(tabella_lookup))
		{
			lookupCampo = new LookupList(db,tabella_lookup);
		}
		
		this.html = generaHtmlAltern(db);
		
	
	}
	*/
	
	

	public void buildRecordNoValoriCampi(Connection db, ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.id_linea=rs.getInt("id_linea");
		this.nome_campo=  rs.getString("nome_campo");
		this.tipo_campo=  rs.getString("tipo_campo");
		this.label_campo=  rs.getString("label_campo");
		this.tabella_lookup=  rs.getString("tabella_lookup");
		this.javascript_function=  rs.getString("javascript_function");
		this.javascript_function_evento=  rs.getString("javascript_function_evento");
		this.link_value =  rs.getString("link_value");
		this.ordine_campo = rs.getInt("ordine_campo");
		this.maxlength = rs.getInt("maxlength");
		this.only_hd=  rs.getInt("only_hd");
		this.label_link=  rs.getString("label_link");
		this.multiple=  rs.getBoolean("multiple");
		this.obbligatorio =  rs.getBoolean("obbligatorio");
		
		
		try{setDbiGenerazione(rs.getString("dbi_generazione"));} catch(Exception ex){ex.printStackTrace();}
		try {setRegexExpr(rs.getString("regex_expr"));} catch(Exception ex){ex.printStackTrace();}
		try {setMessaggioSeInvalid(rs.getString("messaggio_se_invalid"));} catch(Exception ex){ex.printStackTrace();}

		if (this.tabella_lookup!=null && !"".equals(tabella_lookup))
		{
			lookupCampo = new LookupList(db,tabella_lookup);
		}
		
		this.html = generaHtmlAltern(db);
		

	}
	
	
	public void buildRecordConValore(Connection db, ResultSet rs,Integer idRelStabLpInOpu) throws SQLException{
		this.id = rs.getInt("id");
		this.id_linea=rs.getInt("id_linea");
		this.nome_campo=  rs.getString("nome_campo");
		this.tipo_campo=  rs.getString("tipo_campo");
		this.label_campo=  rs.getString("label_campo");
		this.tabella_lookup=  rs.getString("tabella_lookup");
		this.javascript_function=  rs.getString("javascript_function");
		this.javascript_function_evento=  rs.getString("javascript_function_evento");
		this.link_value =  rs.getString("link_value");
		this.ordine_campo = rs.getInt("ordine_campo");
		this.maxlength = rs.getInt("maxlength");
		this.only_hd=  rs.getInt("only_hd");
		this.label_link=  rs.getString("label_link");
		this.multiple=  rs.getBoolean("multiple");
		this.obbligatorio =  rs.getBoolean("obbligatorio");
		this.valore_campo = rs.getString("valore_campo");
		try {setRegexExpr(rs.getString("regex_expr"));} catch(Exception ex){ex.printStackTrace();}
		try {setMessaggioSeInvalid(rs.getString("messaggio_se_invalid"));} catch(Exception ex){ex.printStackTrace();}
		
		try { setDbiGenerazione(rs.getString("dbi_generazione")); } catch(Exception ex){ ex.printStackTrace(); }
		

		if (this.tabella_lookup!=null && !"".equals(tabella_lookup))
		{
			lookupCampo = new LookupList(db,tabella_lookup);
		}
		
		this.html = generaHtmlAlternConIdRelStabLp(db,idRelStabLpInOpu);
		
		

	}
	
	
	
	public static boolean updateValorePerFieldValues(Connection db,String newVal, Integer idCampoEsteso, Integer idRelStabLP)
	{
		PreparedStatement pst = null;
		
		try
		{
			pst = db.prepareStatement("update linee_mobili_fields_value set valore_campo = ? where id_linee_mobili_html_fields = ? and (id_opu_rel_stab_linea = ? or id_rel_stab_linea = ?)");
			pst.setString(1, newVal);
			pst.setInt(2, idCampoEsteso);
			pst.setInt(3, idRelStabLP);
			pst.setInt(4, idRelStabLP);
			int r = pst.executeUpdate();
			return r > 1 ;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
		}
		
	} 
	
	public static boolean updateValorePerFieldValuesSuperficie(Connection db,String newVal, Integer idCampoEsteso, Integer idCampoEsteso2, Integer idRelStabLP)
	{
		PreparedStatement pst = null;
		
		try
		{
			pst = db.prepareStatement("update linee_mobili_fields_value set valore_campo = ? where (id_linee_mobili_html_fields = ? or id_linee_mobili_html_fields = ?  ) and (id_opu_rel_stab_linea = ? or id_rel_stab_linea = ?)");
			pst.setString(1, newVal);
			pst.setInt(2, idCampoEsteso);
			pst.setInt(3, idCampoEsteso2);
			pst.setInt(4, idRelStabLP);
			pst.setInt(5, idRelStabLP);
			int r = pst.executeUpdate();
			return r > 1 ;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
		}
		
	} 
	
	
	
	public static boolean updateValorePerExCodeL30(Connection db,String newVal, Integer idCampoEsteso, Integer idRelStabLP) throws EccezioneCodiceL30GiaInUso, Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		try
		{ /*dobbiamo impedire che quelli importati (num seq <= LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30 o 'INSERIRE-VALORE') ottengano valori > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30, e viceversa
		   e che in ogni caso non accada che due diverse istanze di linea assumano lo stesso valore */
			String seqScelto = newVal.replaceAll("[^0-9]","");
			int seqSceltoparsed = -1;
			int idVecchioValore = -1;
			try
			{
				seqSceltoparsed = Integer.parseInt(seqScelto); /*PARTE DEL SEQUENZIALE SCELTA */
				
				pst = db.prepareStatement("select * from linee_mobili_fields_value where enabled and id_linee_mobili_html_fields = ? "
						+ "and (id_rel_stab_linea = ? or id_opu_rel_stab_linea = ?)");
				pst.setInt(1, idCampoEsteso);
				pst.setInt(2, idRelStabLP);
				pst.setInt(3, idRelStabLP);
				rs = pst.executeQuery();
				rs.next();
				String oldVal = rs.getString("valore_campo") != null ? rs.getString("valore_campo").toLowerCase() : null; /*VALORE PRECEDENTE SUL DB */
				idVecchioValore = rs.getInt("id");
				
				if(seqSceltoparsed > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30)
				{
					/*un valore > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30 puo' essere assegnato soltanto se aveva gia valore > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30, quindi appartiene a quelli generati */
					 
					if(oldVal != null)
					{
						String oldValT = oldVal.replaceAll("[^0-9]", "");
						try
						{
							int parsedOld = Integer.parseInt(oldValT);
							if(parsedOld <= LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30)
							{
								/*il valore precedente era < LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30, quindi sicuramente non apparteneva a quelli generati 
								 * che possono essere > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30
								 */
								throw new EccezioneCodiceL30GiaInUso("Valore non permesso(maggiore di "+SOGLIA_EX_CODE_L30 +"),perche' riservato per la generazione automatica POST VALIDAZIONE SCIA.");
							}
						}
						catch(NumberFormatException ex)
						{
							/*IL VALORE PRECEDENTE NON CONTENEVA UN SEQUENZIALE NUMERICO, QUINDI ERA IL DUMMY VALUE INSERISCI-VALORE, QUINDI SICURAMENTE NON APPARTENEVA A QUELLI GENERATI 
							 * CHE POSSONO ESSERE > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30
							 */
							throw new EccezioneCodiceL30GiaInUso("Valore non permesso(maggiore di "+SOGLIA_EX_CODE_L30 +"),perche' riservato per la generazione automatica POST VALIDAZIONE SCIA.");
						}
					}
					else /*SICCOME NON AVEVA VALORE, ALLORA NON PUO' ESSERE UNO DEI GENERATI , QUINDI NON PUO' PRENDERE VALORE > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30*/
					{
						throw new EccezioneCodiceL30GiaInUso("Valore non permesso(maggiore di "+SOGLIA_EX_CODE_L30 +"),perche' riservato per la generazione automatica POST VALIDAZIONE SCIA.");
					}
					
					
				}
				else /*SCELTO VALORE <= LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30 */
				{
					/*UN VALORE <= LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30 PUO' ESSERE ASSEGNATO SOLTANTO SE AVEVA GIa VALORE <= LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30 OPPURE NON AVEVA PROPRIO VALORE, QUINDI SE NON APPARTIENE A QUELLI GENERATI*/
					if(oldVal != null )
					{
						String oldValT = oldVal.replaceAll("[^0-9]","");
						try
						{
							int parsedOld = Integer.parseInt(oldValT);
							if(parsedOld > LineeMobiliHtmlFields.SOGLIA_EX_CODE_L30)
								throw new EccezioneCodiceL30GiaInUso("Valore non permesso(minore di "+SOGLIA_EX_CODE_L30 +"),perche' riservato per l'IMPORT / AGGIUNGI PREGRESSO");
							
						}
						catch(NumberFormatException ex) /* a db c'era un seq non numerico (DUMMY VALUE INSERISCI-QUI) quindi si tratta di quelli non generati, e va bene */
						{
							
						}
					}
				}
			}
			catch(NumberFormatException ex)
			{
				throw new EccezioneCodiceL30GiaInUso("Codice non valido");
			}
			catch(Exception ex)
			{
				if(ex instanceof EccezioneCodiceL30GiaInUso)
					throw ex;
				
				ex.printStackTrace();
			}
			
			pst.close();
			rs.close();
			
			pst = db.prepareStatement("select * from linee_mobili_fields_value a join linee_mobili_html_fields b on a.id_linee_mobili_html_fields = b.id "+
					" where a.id_linee_mobili_html_fields = ? and lower(valore_campo) = lower(?) and a.id_rel_stab_linea != ? and id_opu_rel_stab_linea != ?" );
			pst.setInt(1, idCampoEsteso);
			pst.setString(2, newVal);
			pst.setInt(3, idRelStabLP);
			pst.setInt(4, idRelStabLP);
			
			rs = pst.executeQuery();
			
			if(rs.next())
			{
				throw new EccezioneCodiceL30GiaInUso("Il valore scelto e' gia' assegnato per questa tipologia di linea e provincia");
			}
			
			pst.close();
			
			pst = db.prepareStatement("update linee_mobili_fields_value set enabled = true , valore_campo = ? where id = ? ");
			pst.setString(1, newVal);
			pst.setInt(2, idVecchioValore);
			int r = pst.executeUpdate();
			
			if(r > 0) /*SE L'AGGIORNAMENTO E' STATO EFFETTIVAMENTE FATTO, ALLORA POTREI AVER SCELTO UN VALORE SUPERIORE AL SEQ, QUINDI DEVO AGGIORNARE IL SEQ*/
			{
				/*aggiorno il seq */
				pst.close();
				/*mi serve la parte della provincia */
				int indInizioSeq = newVal.indexOf(seqScelto);
				String parteProv = newVal.substring(0, indInizioSeq);
				pst = db.prepareStatement("update seqs_campiestesi_autogenerati_per_provincia set seq = greatest(seq,?) where id_linee_mobili_html_fields = ? and lower(cod_provincia) = lower(?)");
				pst.setInt(1, seqSceltoparsed);
				pst.setInt(2,idCampoEsteso );
				pst.setString(3, parteProv);
				pst.executeUpdate();
				return true;
			}
			return false;
		}
		catch(Exception ex)
		{
			if(ex instanceof EccezioneCodiceL30GiaInUso)
				throw ex;
			
			ex.printStackTrace();
			return false;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
		
	}


/*
	private String generaHtml(Connection db) throws SQLException{

		if(tipo_campo.equalsIgnoreCase("checkbox"))
		{
			String text = "<input type=\"checkbox\" value=\""+true+"\" name=\""+this.nome_campo+"\" >"+this.label_campo+"</input>";
			return text;
		}
		if (tipo_campo.equalsIgnoreCase("select")){
			LookupList l = new LookupList(db, tabella_lookup);
			l.setJsEvent(javascript_function);
			if (multiple){
				l.setMultiple(true);
				l.setSelectSize(5);
				return  l.getHtmlSelect(nome_campo, -2);
			}
			else{
				return  l.getHtmlSelect(nome_campo, valore_campo);
			}

		}
		if (tipo_campo.equalsIgnoreCase("text")){
			String text = "<input type=\"text\" id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\"\" />";
			return  text;
		}
		if (tipo_campo.equalsIgnoreCase("hidden")){
			String text = "<input type=\"hidden\" id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\"\"/>";
			return  text;
		}
		if (tipo_campo.equalsIgnoreCase("data")){
			String text = "<input type=\"text\" readonly id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\"\"/>";
			String data = "<a href=\"#\" onClick=\"cal19.select2(document.forms[0]."+nome_campo+",\'anchor19\',\'dd/MM/yyyy\'); return false;\" NAME=\"anchor19\" ID=\"anchor19\"><img src=\"images/icons/stock_form-date-field-16.gif\" border=\"0\" align=\"absmiddle\"></a>";
			return text+data;

		}
		if (tipo_campo.equalsIgnoreCase("allegato")){
			String text = "<input type=\"text\" readonly id=\""+nome_campo+"\" name=\""+nome_campo+"\" value=\"\"/>";
			String data = "<a href=\"#\" onClick=\"openUploadAllegatoMobile(document.getElementById('stabId').value, document.getElementById('targa').value); return false\"><img src=\"gestione_documenti/images/new_file_icon.png\" width=\"20\"/><b>ALLEGA FILE</b></a>";
			return text+data;

		}


		return "";
	}

*/



	private String generaHtmlAltern(Connection db) throws SQLException{

		boolean hasValore = this.getValore_campo() != null && this.getValore_campo().trim().length() > 0 ? true : false;

		if(tipo_campo.equalsIgnoreCase("checkbox"))
		{
			String text = 
					"<input type=\"checkbox\" id=\""+this.nome_campo+"\" value=\""+true+"\" name=\""+this.nome_campo+this.id_linea+"\" >"+this.label_campo+"</input>";
			return text;
		}
		if (tipo_campo.equalsIgnoreCase("select")){
			try
			{
				LookupList l = new LookupList(db, tabella_lookup);
				l.setJsEvent(javascript_function);
				if (multiple){
					l.setMultiple(true);
					l.setSelectSize(5);
					return  l.getHtmlSelect(nome_campo+this.id_linea, 1);
				}
				else{
					return  l.getHtmlSelect(nome_campo+this.id_linea, valore_campo);
					
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}

		}
		if (tipo_campo.equalsIgnoreCase("text") ){
			String text = "<input type=\"text\" "+(getRegexExpr() != null ? ("pattern = \""+getRegexExpr()+"\" ") : " ")+(getMessaggioSeInvalid() != null ? ("title = \""+getMessaggioSeInvalid()+"\" ") : " ")+" class=\""+nome_campo+"\" id=\""+nome_campo+"\" name=\""+nome_campo+this.id_linea+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\" "+(this.obbligatorio ? "required" : "")+" "+(this.maxlength > 0 ? "maxlength=\"?\"".replace("?", this.maxlength+"") : "" )+"/>";
			return  text;
		}
		if(tipo_campo.equalsIgnoreCase("codice_stazione_recapito"))
		{
			String text = "<input type=\"text\" "+(getRegexExpr() != null ? ("pattern = \""+getRegexExpr()+"\" ") : " ")+(getMessaggioSeInvalid() != null ? ("title = \""+getMessaggioSeInvalid()+"\" ") : " ")+" class=\""+nome_campo+"\" id=\""+nome_campo+"\" name=\""+nome_campo+this.id_linea+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\" "+(this.obbligatorio ? "required" : "")+" "+(this.maxlength > 0 ? "maxlength=\"?\"".replace("?", this.maxlength+"") : "" )+"/>";
			return  text;
		}
		if (tipo_campo.equalsIgnoreCase("number")){
			String text = "<input type=\"number\" min = \"0\" id=\""+nome_campo+"\" name=\""+nome_campo+this.id_linea+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\" "+(this.obbligatorio ? "required" : "")+" />";
			return  text;
		}
		if (tipo_campo.equalsIgnoreCase("hidden")){
			String text = "<input type=\"hidden\" id=\""+nome_campo+"\" name=\""+nome_campo+this.id_linea+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\"/>";
			return  text; 
		}
		if (tipo_campo.equalsIgnoreCase("data")){
			String text = "<input type=\"text\"class=\""+nome_campo+"\" id=\""+nome_campo+this.id_linea+"\" name=\""+nome_campo+this.id_linea+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\"  "+(this.obbligatorio ? "required" : "")+"   />";

			String data = "<script> $(function() {	$('#"+nome_campo+this.id_linea+"').datepick({dateFormat: 'dd/mm/yyyy',  showOnFocus: false, showTrigger: '#calImg' }); }); </script>";
			return text+data;

		}
		if(tipo_campo.equalsIgnoreCase("codice_fiscale"))
		{
			String text = "<input type=\"text\" class = \""+nome_campo+"\" id=\""+nome_campo+this.id_linea+"\" name=\""+nome_campo+this.id_linea+"\" maxlength=\""+this.maxlength+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\"  "+(this.obbligatorio ? "required" : "")+"    />";
			return text;
		}
		if (tipo_campo.equalsIgnoreCase("allegato")){
			String text = "<input type=\"text\" readonly id=\""+nome_campo+"\" name=\""+nome_campo+this.id_linea+"\" value=\"\" "+(this.obbligatorio ? "required" : "")+"  />";
			String data = "<a href=\"#\" onClick=\"openUploadAllegatoMobile(document.getElementById('stabId').value, document.getElementById('targa').value); return false\"><img src=\"gestione_documenti/images/new_file_icon.png\" width=\"20\"/><b>ALLEGA FILE</b></a>";
			return text+data;

		}
		if(tipo_campo.equalsIgnoreCase("dummy_label"))
		{
			return "dummy_label";
		}
		if(tipo_campo.equalsIgnoreCase("horizontal_line"))
		{
			return "horizontal_line";
		}


		return "";
	}


	
	private String generaHtmlAlternConIdRelStabLp(Connection db,Integer idRelStabLpInOpu) throws SQLException{
		
		 
		
		boolean hasValore = this.getValore_campo() != null && this.getValore_campo().trim().length() > 0 ? true : false;

		if(tipo_campo.equalsIgnoreCase("checkbox"))
		{
			String text = 
					"<input type=\"checkbox\" id=\""+this.nome_campo+"\" value=\""+true+"\" name=\""+this.nome_campo+idRelStabLpInOpu+"\" >"+this.label_campo+"</input>";
			return text;
		}
		if (tipo_campo.equalsIgnoreCase("select")){
			try
			{
				LookupList l = new LookupList(db, tabella_lookup);
				l.setJsEvent(javascript_function);
				if (multiple){
					l.setMultiple(true);
					l.setSelectSize(5);
					return  l.getHtmlSelect(nome_campo+idRelStabLpInOpu, 1);
				}
				else{
					return  l.getHtmlSelect(nome_campo+idRelStabLpInOpu, valore_campo);
					
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}

		}
		if (tipo_campo.equalsIgnoreCase("text") || tipo_campo.equalsIgnoreCase("codice_stazione_recapito")){
			String text = "<input type=\"text\" "+(getRegexExpr() != null ? ("pattern = \""+getRegexExpr()+"\" ") : " ")+(getMessaggioSeInvalid() != null ? ("title = \""+getMessaggioSeInvalid()+"\" ") : " ")+" class=\""+nome_campo+"\" id=\""+nome_campo+"\" name=\""+nome_campo+idRelStabLpInOpu+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\" "+(this.obbligatorio ? "required" : "")+" "+(this.maxlength > 0 ? "maxlength=\"?\"".replace("?", this.maxlength+"") : "" )+"/>";
			return  text;
		}
		if (tipo_campo.equalsIgnoreCase("number")){
			String text = "<input type=\"number\" min = \"0\" id=\""+nome_campo+"\" name=\""+nome_campo+idRelStabLpInOpu+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\" "+(this.obbligatorio ? "required" : "")+" />";
			return  text;
		}
		if (tipo_campo.equalsIgnoreCase("hidden")){
			String text = "<input type=\"hidden\" id=\""+nome_campo+"\" name=\""+nome_campo+idRelStabLpInOpu+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\"/>";
			return  text; 
		}
		if (tipo_campo.equalsIgnoreCase("data")){
			String text = "<input type=\"text\"class=\""+nome_campo+"\" id=\""+nome_campo+idRelStabLpInOpu+"\" name=\""+nome_campo+idRelStabLpInOpu+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\"  "+(this.obbligatorio ? "required" : "")+"   />";

			String data = "<script> $(function() {	$('#"+nome_campo+idRelStabLpInOpu+"').datepick({dateFormat: 'dd/mm/yyyy',  showOnFocus: false, showTrigger: '#calImg' }); }); </script>";
			return text+data;

		}
		if(tipo_campo.equalsIgnoreCase("codice_fiscale"))
		{
			String text = "<input type=\"text\" class = \""+nome_campo+"\" id=\""+nome_campo+idRelStabLpInOpu+"\" name=\""+nome_campo+idRelStabLpInOpu+"\" maxlength=\""+this.maxlength+"\" value=\""+(hasValore ? this.getValore_campo() : "")+"\"  "+(this.obbligatorio ? "required" : "")+"    />";
			return text;
			
		}
		if (tipo_campo.equalsIgnoreCase("allegato")){
			String text = "<input type=\"text\" readonly id=\""+nome_campo+"\" name=\""+nome_campo+idRelStabLpInOpu+"\" value=\"\" "+(this.obbligatorio ? "required" : "")+"  />";
			String data = "<a href=\"#\" onClick=\"openUploadAllegatoMobile(document.getElementById('stabId').value, document.getElementById('targa').value); return false\"><img src=\"gestione_documenti/images/new_file_icon.png\" width=\"20\"/><b>ALLEGA FILE</b></a>";
			return text+data;

		}
		if(tipo_campo.equalsIgnoreCase("dummy_label"))
		{
			return "dummy_label";
		}
		if(tipo_campo.equalsIgnoreCase("horizontal_line"))
		{
			return "horizontal_line";
		}


		return "";
	}






	public LinkedHashMap<String, String> costruisciHtmlDaHashMap(Connection db, LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campi,int roleId) throws SQLException{

		String nomeCampo="";
		String html = "";
		String nomeLabel ="";
		ArrayList<LineeMobiliHtmlFields> campiHash = new ArrayList<LineeMobiliHtmlFields>();
		LinkedHashMap<String, String> risultato = new LinkedHashMap<String, String>();

		for (Map.Entry<String, ArrayList<LineeMobiliHtmlFields>> entry : campi.entrySet()) {
			nomeCampo = entry.getKey();
			campiHash = entry.getValue();
			nomeLabel =campiHash.get(0).getLabel_campo();
			//L'attributo readonly viene settato con onkeypress="return false;" poiche in presenza di readonly l'attributo required non funziona

			if (!(campiHash.get(0).getTipo_campo().equals("select") && campiHash.get(0).isMultiple())){ //SE NON E' UNA SELECT MULTIPLA
				if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("select")){
					LookupList l = new LookupList(db, campiHash.get(0).getTabella_lookup());
					l.setJsEvent(campiHash.get(0).getJavascript_function());
					html =  l.getHtmlSelect(campiHash.get(0).getNome_campo(), campiHash.get(0).getValore_campo());
				}
				else if(campiHash.get(0).getTipo_campo().equalsIgnoreCase("checkbox"))
				{
					String text = 
							"<input "+ ((campiHash.get(0).getValore_campo()!=null && campiHash.get(0).getValore_campo().equalsIgnoreCase("true"))?("checked=\"checked\""):("")) + " " + ((campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento())) ? (campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"") : ("")) + " type=\"checkbox\"" + (campiHash.get(0).isRedOnly() ? "disabled" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" value=\"true\" name=\""+campiHash.get(0).getNome_campo()+"\" />";
					html = text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("text") ){
					String text = "<input type=\"text\" "+  ((campiHash.get(0).getMaxlength()>0)?("maxlength=\"" + campiHash.get(0).getMaxlength() + "\""):("")) + " " +     (campiHash.get(0).isObbligatorio() ? "required " : "") +(campiHash.get(0).getRegexExpr() != null ? ("pattern = \""+campiHash.get(0).getRegexExpr()+"\" ") : " ")+(campiHash.get(0).getMessaggioSeInvalid() != null ? ("title = \""+campiHash.get(0).getMessaggioSeInvalid()+"\" ") : " ")+" "+(campiHash.get(0).isRedOnly() ? "onpaste=\"return false;\" onkeypress=\"return false;\"" : "") + " "+(campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento()) ? campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				//Ruolo diverso da HD e Orsa non si puo modificare se readonly e valore presente
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("codice_stazione_recapito")){
					String text = "<input type=\"text\" "+  ((campiHash.get(0).getMaxlength()>0)?("maxlength=\"" + campiHash.get(0).getMaxlength() + "\""):("")) + " " +     (campiHash.get(0).isObbligatorio() ? "required " : "") +(campiHash.get(0).getRegexExpr() != null ? ("pattern = \""+campiHash.get(0).getRegexExpr()+"\" ") : " ")+(campiHash.get(0).getMessaggioSeInvalid() != null ? ("title = \""+campiHash.get(0).getMessaggioSeInvalid()+"\" ") : " ")+" "+((campiHash.get(0).isRedOnly() && campiHash.get(0).getValore_campo()!=null && !campiHash.get(0).getValore_campo().equals("") && roleId!=1 && roleId !=31 && roleId !=32) ? "onpaste=\"return false;\" onkeydown=\"if(event.keyCode == 8){ return false;} else {return true;}\" onkeypress=\"return false;\"" : "") + " "+(campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento()) ? campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("number")){
					String text = "<input type=\"number\" "+(campiHash.get(0).isObbligatorio() ? "required " : "") +(campiHash.get(0).getRegexExpr() != null ? ("pattern = \""+campiHash.get(0).getRegexExpr()+"\" ") : " ")+(campiHash.get(0).getMessaggioSeInvalid() != null ? ("title = \""+campiHash.get(0).getMessaggioSeInvalid()+"\" ") : " ")+" "+(campiHash.get(0).isRedOnly() ? "onpaste=\"return false;\" onkeypress=\"return false;\"" : "") + " "+(campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento()) ? campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("hidden")){
					String text = "<input type=\"hidden\" id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("data")){
					String text = "<input type=\"text\"" +(campiHash.get(0).isObbligatorio() ? "required " : "") + "onpaste=\"return false;\" onkeypress=\"return false;\" id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					String data = "<a href=\"#\" onClick=\"cal19.select2(document.forms[0]."+campiHash.get(0).getNome_campo()+",\'anchor19\',\'dd/MM/yyyy\'); return false;\" NAME=\"anchor19\" ID=\"anchor19\"><img src=\"images/icons/stock_form-date-field-16.gif\" border=\"0\" align=\"absmiddle\"></a>";
					html= text+data;

				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("allegato")){
					String text = "<input type=\"text\" onpaste=\"return false;\" onkeypress=\"return false;\" id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					String data = "&nbsp;&nbsp;<a href=\"#\" onClick=\"openUploadAllegatoMobile(document.getElementById('stabId').value, document.getElementById('targa').value); return false\"><img src=\"gestione_documenti/images/new_file_icon.png\" width=\"20\"/>&nbsp;<b>ALLEGA FILE</b></a>";
					html= text+data;

				}

			}
			else{
				LookupList l = new LookupList(db, campiHash.get(0).getTabella_lookup());
				l.setJsEvent(campiHash.get(0).getJavascript_function());
				l.setMultiple(campiHash.get(0).isMultiple());
				l.setSelectSize(5);
				LookupList multipleSelects=new LookupList();
				
				if(campiHash.get(0).getValore_campo()!=null && !campiHash.get(0).getValore_campo().equals(""))
				{
					String[] valoriSplittati = campiHash.get(0).getValore_campo().split(";");
					for(int i=0;i<valoriSplittati.length;i++)
					{
						LookupElement el = new LookupElement();
						el.setCode(valoriSplittati[i]);
						el.setDescription(campiHash.get(0).getLookupCampo().getSelectedValue(valoriSplittati[i]));
						multipleSelects.add(el);
					}
				    l.setMultipleSelects(multipleSelects);
				}
			    html =  l.getHtmlSelect(nomeCampo, multipleSelects);
			}
			risultato.put(nomeLabel, html);
		}

		return risultato;
	}

	public LinkedHashMap<String, String> costruisciHtmlDaHashMap(Connection db, LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campi,int roleId, int idStabilimento) throws SQLException{

		String nomeCampo="";
		String html = "";
		String nomeLabel ="";
		ArrayList<LineeMobiliHtmlFields> campiHash = new ArrayList<LineeMobiliHtmlFields>();
		LinkedHashMap<String, String> risultato = new LinkedHashMap<String, String>();

		for (Map.Entry<String, ArrayList<LineeMobiliHtmlFields>> entry : campi.entrySet()) {
			nomeCampo = entry.getKey();
			campiHash = entry.getValue();
			nomeLabel =campiHash.get(0).getLabel_campo();
			//L'attributo readonly viene settato con onkeypress="return false;" poichè in presenza di readonly l'attributo required non funziona

			if (!(campiHash.get(0).getTipo_campo().equals("select") && campiHash.get(0).isMultiple())){ //SE NON E' UNA SELECT MULTIPLA
				if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("select")){
					LookupList l = new LookupList(db, campiHash.get(0).getTabella_lookup());
					l.setJsEvent(campiHash.get(0).getJavascript_function());
					html =  l.getHtmlSelect(campiHash.get(0).getNome_campo(), campiHash.get(0).getValore_campo());
				}
				else if(campiHash.get(0).getTipo_campo().equalsIgnoreCase("checkbox"))
				{
					String text = 
							"<input "+ ((campiHash.get(0).getValore_campo()!=null && campiHash.get(0).getValore_campo().equalsIgnoreCase("true"))?("checked=\"checked\""):("")) + " " + ((campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento())) ? (campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"") : ("")) + " type=\"checkbox\"" + (campiHash.get(0).isRedOnly() ? "disabled" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" value=\"true\" name=\""+campiHash.get(0).getNome_campo()+"\" />";
					html = text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("text") ){
					String text = "<input type=\"text\" "+  ((campiHash.get(0).getMaxlength()>0)?("maxlength=\"" + campiHash.get(0).getMaxlength() + "\""):("")) + " " +     (campiHash.get(0).isObbligatorio() ? "required " : "") +(campiHash.get(0).getRegexExpr() != null ? ("pattern = \""+campiHash.get(0).getRegexExpr()+"\" ") : " ")+(campiHash.get(0).getMessaggioSeInvalid() != null ? ("title = \""+campiHash.get(0).getMessaggioSeInvalid()+"\" ") : " ")+" "+(campiHash.get(0).isRedOnly() ? "onpaste=\"return false;\" onkeypress=\"return false;\"" : "") + " "+(campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento()) ? campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				//Ruolo diverso da HD e Orsa non si puo modificare se readonly e valore presente
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("codice_stazione_recapito")){
					
					String codStazione = "";
					
					if (campiHash.get(0).getValore_campo()!=null && !campiHash.get(0).getValore_campo().equals("")){
						codStazione = campiHash.get(0).getValore_campo();
					}
					else if (campiHash.get(0).getDbiGenerazione()!=null && !campiHash.get(0).getDbiGenerazione().equals("")){
						
						String dbiGenerazione = campiHash.get(0).getDbiGenerazione();
						dbiGenerazione = dbiGenerazione.replaceAll(":idRichiesta:", "-1");
						dbiGenerazione = dbiGenerazione.replaceAll(":idStabilimento:", "?");
						
						PreparedStatement pst = db.prepareStatement("select * from "+dbiGenerazione);
						pst.setInt(1, idStabilimento);
						ResultSet rs = pst.executeQuery();
						if (rs.next())
							codStazione = rs.getString(1);
					}
									
					String text = "<input type=\"text\" readonly"+  ((campiHash.get(0).getMaxlength()>0)?("maxlength=\"" + campiHash.get(0).getMaxlength() + "\""):("")) + " " +     (campiHash.get(0).isObbligatorio() ? "required " : "") +(campiHash.get(0).getRegexExpr() != null ? ("pattern = \""+campiHash.get(0).getRegexExpr()+"\" ") : " ")+(campiHash.get(0).getMessaggioSeInvalid() != null ? ("title = \""+campiHash.get(0).getMessaggioSeInvalid()+"\" ") : " ")+" "+((campiHash.get(0).isRedOnly() && campiHash.get(0).getValore_campo()!=null && !campiHash.get(0).getValore_campo().equals("") && roleId!=1 && roleId !=31 && roleId !=32) ? "onpaste=\"return false;\" onkeydown=\"if(event.keyCode == 8){ return false;} else {return true;}\" onkeypress=\"return false;\"" : "") + " "+(campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento()) ? campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+codStazione+"\"/>";
					html=  text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("number")){
					String text = "<input type=\"number\" "+(campiHash.get(0).isObbligatorio() ? "required " : "") +(campiHash.get(0).getRegexExpr() != null ? ("pattern = \""+campiHash.get(0).getRegexExpr()+"\" ") : " ")+(campiHash.get(0).getMessaggioSeInvalid() != null ? ("title = \""+campiHash.get(0).getMessaggioSeInvalid()+"\" ") : " ")+" "+(campiHash.get(0).isRedOnly() ? "onpaste=\"return false;\" onkeypress=\"return false;\"" : "") + " "+(campiHash.get(0).getJavascript_function_evento()!=null && !"".equals(campiHash.get(0).getJavascript_function_evento()) ? campiHash.get(0).getJavascript_function_evento() +"=\""+campiHash.get(0).getJavascript_function()+"\"" : "") + " id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("hidden")){
					String text = "<input type=\"hidden\" id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					html=  text;
				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("data")){
					String text = "<input type=\"text\"" +(campiHash.get(0).isObbligatorio() ? "required " : "") + "onpaste=\"return false;\" onkeypress=\"return false;\" id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					String data = "<a href=\"#\" onClick=\"cal19.select2(document.forms[0]."+campiHash.get(0).getNome_campo()+",\'anchor19\',\'dd/MM/yyyy\'); return false;\" NAME=\"anchor19\" ID=\"anchor19\"><img src=\"images/icons/stock_form-date-field-16.gif\" border=\"0\" align=\"absmiddle\"></a>";
					html= text+data;

				}
				else if (campiHash.get(0).getTipo_campo().equalsIgnoreCase("allegato")){
					String text = "<input type=\"text\" onpaste=\"return false;\" onkeypress=\"return false;\" id=\""+campiHash.get(0).getNome_campo()+"\" name=\""+campiHash.get(0).getNome_campo()+"\" value=\""+campiHash.get(0).getValore_campo()+"\"/>";
					String data = "&nbsp;&nbsp;<a href=\"#\" onClick=\"openUploadAllegatoMobile(document.getElementById('stabId').value, document.getElementById('targa').value); return false\"><img src=\"gestione_documenti/images/new_file_icon.png\" width=\"20\"/>&nbsp;<b>ALLEGA FILE</b></a>";
					html= text+data;

				}

			}
			else{
				LookupList l = new LookupList(db, campiHash.get(0).getTabella_lookup());
				l.setJsEvent(campiHash.get(0).getJavascript_function());
				l.setMultiple(campiHash.get(0).isMultiple());
				l.setSelectSize(5);
				LookupList multipleSelects=new LookupList();
				
				if(campiHash.get(0).getValore_campo()!=null && !campiHash.get(0).getValore_campo().equals(""))
				{
					String[] valoriSplittati = campiHash.get(0).getValore_campo().split(";");
					for(int i=0;i<valoriSplittati.length;i++)
					{
						LookupElement el = new LookupElement();
						el.setCode(valoriSplittati[i]);
						el.setDescription(campiHash.get(0).getLookupCampo().getSelectedValue(valoriSplittati[i]));
						multipleSelects.add(el);
					}
				    l.setMultipleSelects(multipleSelects);
				}
			    html =  l.getHtmlSelect(nomeCampo, multipleSelects);
			}
			risultato.put(nomeLabel, html);
		}

		return risultato;
	}





	public static void insertDettaglioMobile(Connection db,int lda_macroarea, int lda_rel_stab, int stabId, ActionContext context) throws SQLException {

		Logger logger = Logger.getLogger("MainLogger");


		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();

		boolean trovato = false ;

			int indice=0;

			//Prelevo indice
			String query_indice="SELECT max(indice) as max_indice from linee_mobili_fields_value where id_rel_stab_linea = ? ";
			PreparedStatement pst_ind = db.prepareStatement(query_indice);
			pst_ind.setInt(1, lda_rel_stab);
			ResultSet rs_ind = pst_ind.executeQuery();;
			if (rs_ind.next())
				indice=rs_ind.getInt("max_indice")+1;
			else
				indice=1;
			// determino l'insieme delle colonne
			sql.append("SELECT id, nome_campo,multiple FROM linee_mobili_html_fields where id_linea = ? and enabled order by ordine_campo");
			pst = db.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pst.setInt(1, lda_macroarea);
			rs = pst.executeQuery();

			sql = new StringBuffer();



			String verificaEsistenza = "select * from linee_mobili_fields_value where id_rel_stab_linea = ? and id_linee_mobili_html_fields=? and valore_campo=? and enabled= true";
			PreparedStatement pstVerifica = db.prepareStatement(verificaEsistenza);

			//sql.append("INSERT INTO linee_mobili_fields_value (id_rel_stab_linea, id_linee_mobili_html_fields,id_utente_inserimento, indice, valore_campo) values (?,?,?,?, ?) ");
			//select * from public.dbi_insert_campi_estesi(id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, id_utente_inserimento, id_opu_rel_stab_linea, riferimento_org_id)
			sql.append("select * from public.dbi_insert_campi_estesi(?, ?, ?, ?, ?, ?, -1)");

			
			PreparedStatement pst2 = db.prepareStatement(sql.toString());
			
			 
			
			ResultSet rsVerifica = null;
			
							
				while (rs.next()) {
					//num_campi=rs.
					trovato = true;
					int idCampo = rs.getInt("id");
					String nomeCampo = rs.getString("nome_campo");

					pst2.setInt(1, lda_rel_stab);
					pst2.setInt(2, idCampo);
					pst2.setInt(5, user_id);
					pst2.setInt(6, lda_rel_stab);

					if (rs.getBoolean("multiple") == false) {
						pst2.setInt(4, indice);
						pst2.setString(3, context.getParameter(nomeCampo));
						pst2.execute(); 
					} else {

						String[] valueSel = context.getRequest().getParameterValues(nomeCampo);
						if (valueSel != null) {
							for (int i = 0; i < valueSel.length; i++) {

								pstVerifica.setInt(1, lda_rel_stab);
								pstVerifica.setInt(2, idCampo);
								pstVerifica.setString(3, valueSel[i]);
								rsVerifica = pstVerifica.executeQuery();
								if (!rsVerifica.next()) {

									try {
										pst2.setInt(4, Integer.parseInt(valueSel[i]));
									} catch (Exception e) {
										pst2.setInt(4, indice);
									}
									pst2.setString(3, valueSel[i]);
									pst2.execute();
									}
							}
						} else {
							pst2.setString(4, "");
							pst2.execute(); 
						}

					}

				}
				

	}
	
	
	
	
	public static void updateDettaglioMobile(Connection db,int lda_macroarea, int lda_rel_stab, int stabId, ActionContext context) throws SQLException 
	{
		Logger logger = Logger.getLogger("MainLogger");

		PreparedStatement pstDatiCampiEstesi = null;
		ResultSet rsDatiCampiEstesi = null;
		StringBuffer sqlDatiCampiEstesi = new StringBuffer();		

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();

		boolean trovato = false ;
		int indice=0;

		//Prelevo indice
		String query_indice="SELECT max(indice) as max_indice from linee_mobili_fields_value where id_rel_stab_linea = ? ";
		PreparedStatement pst_ind = db.prepareStatement(query_indice);
		pst_ind.setInt(1, lda_rel_stab);
		ResultSet rs_ind = pst_ind.executeQuery();;
		if (rs_ind.next())
			indice=rs_ind.getInt("max_indice")+1;
		else
			indice=1;
		// determino l'insieme delle colonne
		sqlDatiCampiEstesi.append("SELECT id, nome_campo,multiple FROM linee_mobili_html_fields where id_linea = ? and enabled order by ordine_campo");
		pstDatiCampiEstesi = db.prepareStatement(sqlDatiCampiEstesi.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		pstDatiCampiEstesi.setInt(1, lda_macroarea);
		rsDatiCampiEstesi = pstDatiCampiEstesi.executeQuery();

		String verificaEsistenza = "select * from linee_mobili_fields_value where (id_rel_stab_linea = ? or id_opu_rel_stab_linea = ?)and id_linee_mobili_html_fields=? and valore_campo=? and enabled= true";
		PreparedStatement pstVerifica = db.prepareStatement(verificaEsistenza);

		String sqlUpdateValore = "select * from public.dbi_update_campi_estesi(?, ?, ?, ?, ?, ?, -1)";
		PreparedStatement pstUpdateValore = db.prepareStatement(sqlUpdateValore.toString());
		
		while (rsDatiCampiEstesi.next()) 
		{
			trovato = true;
			int idCampoDaInserire = rsDatiCampiEstesi.getInt("id");
			String nomeCampoDaInserire = rsDatiCampiEstesi.getString("nome_campo");
			boolean multipleCampoDaInserire = rsDatiCampiEstesi.getBoolean("multiple");
			
			pstUpdateValore.setInt(1, lda_rel_stab);
			pstUpdateValore.setInt(2, idCampoDaInserire);
			pstUpdateValore.setInt(5, user_id);
			pstUpdateValore.setInt(6, lda_rel_stab);

			if (!multipleCampoDaInserire) 
			{
				pstUpdateValore.setInt(4, indice);
				pstUpdateValore.setString(3, context.getParameter(nomeCampoDaInserire));
				pstUpdateValore.execute(); 
			} 
			else 
			{
				String[] valoriNuovi = context.getRequest().getParameterValues(nomeCampoDaInserire);
				pstVerifica.setInt(1, lda_rel_stab);
				pstVerifica.setInt(2, lda_rel_stab);
				
				String valoriNuoviString = "";
				
				if (valoriNuovi != null) 
				{
					for (int i = 0; i < valoriNuovi.length; i++) 
						valoriNuoviString += valoriNuovi[i] + ";";
					
					if(valoriNuovi.length>0)
						valoriNuoviString = valoriNuoviString.substring(0,valoriNuoviString.length()-1);
				} 
				else
					valoriNuoviString = idCampoDaInserire+"";
				
				pstUpdateValore.setString(3, valoriNuoviString);
				pstVerifica.setInt(4, indice);
				pstUpdateValore.execute();
			}
		}
				

	}















	public static void insertDettaglioMobileDaImort(Connection db,int lda_macroarea, int lda_rel_stab, int stabId,HashMap<String, String> valoriDistributori, int user_id) {

		Logger logger = Logger.getLogger("MainLogger");


		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		

	
		boolean trovato = false ;
		try {
			int indice=0;

			//Prelevo indice
			String query_indice="SELECT max(indice) as max_indice from linee_mobili_fields_value where id_rel_stab_linea = ? ";
			PreparedStatement pst_ind = db.prepareStatement(query_indice);
			pst_ind.setInt(1, lda_rel_stab);
			ResultSet rs_ind = pst_ind.executeQuery();;
			if (rs_ind.next())
				indice=rs_ind.getInt("max_indice")+1;
			else
				indice=1;
			// determino l'insieme delle colonne
			sql.append("SELECT id, nome_campo,multiple FROM linee_mobili_html_fields where id_linea = ? and enabled order by ordine_campo");
			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, lda_macroarea);
			rs = pst.executeQuery();

			sql = new StringBuffer();



			String verificaEsistenza = "select * from linee_mobili_fields_value where id_rel_stab_linea = ? and id_linee_mobili_html_fields=? and valore_campo=? and enabled= true";
			PreparedStatement pstVerifica = db.prepareStatement(verificaEsistenza);

			//sql.append("INSERT INTO linee_mobili_fields_value (id_rel_stab_linea, id_linee_mobili_html_fields,id_utente_inserimento, indice, valore_campo) values (?,?,?,?, ?) ");
			//select * from public.dbi_insert_campi_estesi(id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, id_utente_inserimento, id_opu_rel_stab_linea, riferimento_org_id)
			sql.append("select * from public.dbi_insert_campi_estesi(?, ?, ?, ?, ?, -1, -1)");

			PreparedStatement pst2 = db.prepareStatement(sql.toString());
			ResultSet rsVerifica = null;
			while (rs.next())
			{
				trovato = true ;
				int idCampo = rs.getInt("id");
				String nomeCampo = rs.getString("nome_campo");





				pst2.setInt(1, lda_rel_stab);
				pst2.setInt(2, idCampo);
				pst2.setInt(5, user_id);


				pst2.setInt(4, indice);
				pst2.setString(3, valoriDistributori.get(nomeCampo));
				pst2.execute();




			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
		}

	}
	
	
	public static void aggiornaDettaglioMobileDaImort(Connection db,int lda_macroarea, int lda_rel_stab, int stabId,HashMap<String, String> valoriDistributori,int indiceDistributore,int user_id) {

		Logger logger = Logger.getLogger("MainLogger");


		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();		


		boolean trovato = false ;
		try {
			int indice=0;

			//Prelevo indice
			db.prepareStatement("UPDATE linee_mobili_fields_value SET enabled=false where id_rel_stab_linea="+lda_rel_stab+" and indice ="+indiceDistributore).execute();
			
			String query_indice="SELECT max(indice) as max_indice from linee_mobili_fields_value where id_rel_stab_linea = ? ";
			PreparedStatement pst_ind = db.prepareStatement(query_indice);
			pst_ind.setInt(1, lda_rel_stab);
			ResultSet rs_ind = pst_ind.executeQuery();;
			if (rs_ind.next())
				indice=rs_ind.getInt("max_indice")+1;
			else
				indice=1;
			// determino l'insieme delle colonne
			sql.append("SELECT id, nome_campo,multiple FROM linee_mobili_html_fields where id_linea = ? and enabled order by ordine_campo");
			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, lda_macroarea);
			rs = pst.executeQuery();

			sql = new StringBuffer();



			String verificaEsistenza = "select * from linee_mobili_fields_value where id_rel_stab_linea = ? and id_linee_mobili_html_fields=? and valore_campo=? and enabled= true";
			PreparedStatement pstVerifica = db.prepareStatement(verificaEsistenza);

			//sql.append("INSERT INTO linee_mobili_fields_value (id_rel_stab_linea, id_linee_mobili_html_fields,id_utente_inserimento, indice, valore_campo) values (?,?,?,?, ?) ");
			//select * from public.dbi_insert_campi_estesi(id_rel_stab_linea, id_linee_mobili_html_fields, valore_campo, indice, id_utente_inserimento, id_opu_rel_stab_linea, riferimento_org_id)
			sql.append("select * from public.dbi_insert_campi_estesi(?, ?, ?, ?, ?, -1, -1)");

			
			
			PreparedStatement pst2 = db.prepareStatement(sql.toString());
			ResultSet rsVerifica = null;
			while (rs.next())
			{
				trovato = true ;
				int idCampo = rs.getInt("id");
				String nomeCampo = rs.getString("nome_campo");





				pst2.setInt(1, lda_rel_stab);
				pst2.setInt(2, idCampo);
				pst2.setInt(5, user_id);


				pst2.setInt(4, indice);
				pst2.setString(3, valoriDistributori.get(nomeCampo));
				pst2.execute();




			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
		}

	}

	public static void eliminazioneLogicaDettaglioMobile(Connection db,int indice, int lda_rel_stab, ActionContext context) {
		Logger logger = Logger.getLogger("MainLogger");

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();
		try {
			String query="update linee_mobili_fields_value set enabled=false where (id_rel_stab_linea = ? or id_opu_rel_stab_linea = ?) and indice = ? ;";
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, lda_rel_stab);
			pst.setInt(2, lda_rel_stab);
			pst.setInt(3, indice);
			pst.execute();
			//if (rs.next())
			//	indice=rs.getInt("max_indice")+1;
			//else
			//	indice=1;

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
		} finally {
		}

	}
	
	public static String dismissioneDettaglioMobile(Connection db,int indice, int lda_rel_stab, String note, String data, ActionContext context) {
		Logger logger = Logger.getLogger("MainLogger");

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int user_id = user.getUserRecord().getId();
		String esito = "";
		try {
				
			String query="select * from dismissione_campo(?, ?, ?, ?, ?);";
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, indice);
			pst.setInt(2, lda_rel_stab);
			pst.setInt(3, user_id);
			pst.setString(4, note);
			pst.setString(5, data);

			ResultSet rs = pst.executeQuery();
			if (rs.next())
				esito=rs.getString(1);

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
		} finally {
		}
		return esito;
	}
	
	
	@Override
	public JSONObject toJsonObject() {
		 
		 JSONObject jsonO = new JSONObject();
		jsonO.put("id", Jsonable.sanityString(getId()+""));
		jsonO.put("id_linea", Jsonable.sanityString(getId_linea() +""));
		jsonO.put("nome_campo", Jsonable.sanityString(getNome_campo()+""));
		jsonO.put("tipo_campo", Jsonable.sanityString(getTipo_campo()+""));
		jsonO.put("label_campo", Jsonable.sanityString(getLabel_campo()+""));
		jsonO.put("javascript_function", Jsonable.sanityString(getJavascript_function()+""));
		jsonO.put("javascript_function_evento", Jsonable.sanityString(getJavascript_function_evento()+""));
		jsonO.put("ordine_campo", Jsonable.sanityString(getOrdine_campo()+""));
		jsonO.put("maxlength", Jsonable.sanityString(getMaxlength()+""));
		jsonO.put("only_hd", Jsonable.sanityString(getOnly_hd()+""));
		jsonO.put("label_link", Jsonable.sanityString(getLabel_link()+""));
		jsonO.put("obbligatorio", Jsonable.sanityString(isObbligatorio()+""));
		jsonO.put("valore_campo", Jsonable.sanityString(getValore_campo()+""));
		jsonO.put("indice", Jsonable.sanityString(getIndice()+""));
		jsonO.put("redOnly", Jsonable.sanityString(isRedOnly()+""));
		jsonO.put("multiple", Jsonable.sanityString(isMultiple()+""));
		jsonO.put("dbi_generazione", Jsonable.sanityString(getDbiGenerazione()+""));
		jsonO.put("valore_campo", Jsonable.sanityString(getDbiGenerazione()+""));
		
		return jsonO;
	}
	@Override
	public String toJsonString() {
		return toJsonObject().toString();
	}


}
