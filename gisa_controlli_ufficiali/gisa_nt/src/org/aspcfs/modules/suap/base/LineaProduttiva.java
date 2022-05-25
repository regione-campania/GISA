/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.LineeMobiliHtmlFields;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.Jsonable;
import org.json.JSONArray;

import com.darkhorseventures.framework.beans.GenericBean;
import com.google.gson.JsonArray;


public class LineaProduttiva extends GenericBean {


	public static final int NORMA_STABILIMENTI_852 = 1 ;
	public static final int NORMA_AZIENDE_AGRICOLE = 7 ;


	public static final int TIPO_GESTIONE_SCIA_REGISTRABILI = 1 ;
	public static final int TIPO_GESTIONE_SCIA_RICONOSCIBILI = 2 ;
	public static final int TIPO_GESTIONE_SCIA_AZIENDE_ZOOTECNICHE = 3 ;
	public static final int TIPO_GESTIONE_SCIA_APICOLTIURA = 4 ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public LineaProduttiva() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String codiceNazionale;
	private String dataInizioString;
	private String dataFineString;
	private String num_protocollo ;
	private String categoria 	;
	private String attivita 	;
	private String macrocategoria ;
	private String norma ;
	private int idMacroarea ;
	private int idCategoria 	;
	private int idAttivita 		;
	private int id ;
	private Timestamp dataInizio ;
	private Timestamp dataFine ;
	private int stato ;
	private int idRelazioneAttivita;
	private int tipoAttivitaProduttiva ; 
	private String codice;
	private boolean principale = false;
	private int idNorma ;
	private int id_rel_stab_lp ;
	private String descrizione_linea_attivita ;
	private String codice_ufficiale_esistente ;
	private int idTipoGestioneScia;
	private String numeroRegistrazione ;

	private int idVecchiaLinea = -1;
	private int idTipoVecchiaOrg = -1;

	private int categoriaRischio ;
	private Timestamp dataProssimoControllo ;
	private int idLookupConfigurazioneValidazione;
	
	private String descr_label ;
	private String permesso ;
	private HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>>  campiEstesi;
	private ArrayList<LineeMobiliHtmlFields> htmlFieldsValidazione = new ArrayList<LineeMobiliHtmlFields>();
	
	
	
	
	
	
	
	
	 
	

	public ArrayList<LineeMobiliHtmlFields> getHtmlFieldsValidazione() {
		return htmlFieldsValidazione;
	}

	public void setHtmlFieldsValidazione(ArrayList<LineeMobiliHtmlFields> htmlFieldsValidazione) {
		this.htmlFieldsValidazione = htmlFieldsValidazione;
	}

	public String getDescr_label() {
		return descr_label;
	}

	public void setDescr_label(String descr_label) {
		this.descr_label = descr_label;
	}

	public String getPermesso() {
		return permesso;
	}

	public void setPermesso(String permesso) {
		this.permesso = permesso;
	}

	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}

	public int getIdTipoGestioneScia() {
		return idTipoGestioneScia;
	}

	public void setIdTipoGestioneScia(int idTipoGestioneScia) {
		this.idTipoGestioneScia = idTipoGestioneScia;
	}

	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}



	public String getNum_protocollo() {
		return num_protocollo;
	}

	public void setNum_protocollo(String num_protocollo) {
		this.num_protocollo = num_protocollo;
	}

	public String getCodice_ufficiale_esistente() {
		return codice_ufficiale_esistente;
	}



	public void setCodice_ufficiale_esistente(String codice_ufficiale_esistente) {
		this.codice_ufficiale_esistente = codice_ufficiale_esistente;
	}



	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}







	public String getDescrizione_linea_attivita() {
		return descrizione_linea_attivita;
	}
	public void setDescrizione_linea_attivita(String descrizione_linea_attivita) {
		this.descrizione_linea_attivita = descrizione_linea_attivita;
	}
	public int getId_rel_stab_lp() {
		return id_rel_stab_lp;
	}
	public void setId_rel_stab_lp(int id_rel_stab_lp) {
		this.id_rel_stab_lp = id_rel_stab_lp;
	}
	public int getIdNorma() {
		return idNorma;
	}
	public void setIdNorma(int idNorma) {
		this.idNorma = idNorma;
	}
	public String getNorma() {
		return norma;
	}
	public void setNorma(String norma) {
		this.norma = norma;
	}
	public boolean isPrincipale() {
		return principale;
	}
	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public int getIdRelazioneAttivita() {
		return idRelazioneAttivita;
	}
	public void setIdRelazioneAttivita(int idRelazioneAttivita) {
		this.idRelazioneAttivita = idRelazioneAttivita;
	}

	public void setIdRelazioneAttivita(String idRelazioneAttivita) {
		this.idRelazioneAttivita = new Integer(idRelazioneAttivita).intValue();
	}

	public int getTipoAttivitaProduttiva() {
		return tipoAttivitaProduttiva;
	}

	public void setTipoAttivitaProduttiva(int tipoAttivitaProduttiva) {
		this.tipoAttivitaProduttiva = tipoAttivitaProduttiva;
	}
	public void setTipoAttivitaProduttiva(String tipoAttivitaProduttiva) {
		if (tipoAttivitaProduttiva != null && !"".equals(tipoAttivitaProduttiva))
			this.tipoAttivitaProduttiva = Integer.parseInt(tipoAttivitaProduttiva);
	}
	public Timestamp getDataInizio() {

		return dataInizio;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public void setDataInizio(String dataInizio) throws ParseException {
		if (dataInizio != null && ! dataInizio.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizio = new Timestamp(sdf.parse(dataInizio).getTime());

		}

	}

	public Timestamp getDataFine() {

		return dataFine;
	}



	public String getDataFineasString() {
		String dataFine = "" ;
		if (this.dataFine != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFine = sdf.format(new Date(this.dataFine.getTime()));

		}
		return dataFine;
	}



	public String getDataInizioString() {
		return dataInizioString;
	}

	public void setDataInizioString(String dataInizioString) {
		this.dataInizioString = dataInizioString;
	}

	public String getDataFineString() {
		return dataFineString;
	}

	public void setDataFineString(String dataFineString) {
		this.dataFineString = dataFineString;
	}

	public String getDataInizioasString() {
		String dataInizio = "" ;
		if (this.dataInizio != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataInizio = sdf.format(new Date(this.dataInizio.getTime()));

		}
		return dataInizio;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public void setDataFine(String dataFine) throws ParseException {
		if (dataFine != null && ! dataFine.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataFine = new Timestamp(sdf.parse(dataFine).getTime());

		}

	}



	public String getMacrocategoria() {
		return macrocategoria;
	}
	public void setMacrocategoria(String macrocategoria) {
		this.macrocategoria = macrocategoria;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}

	public void setStato(String stato) {
		this.stato = new Integer(stato).intValue();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = new Integer(id).intValue();
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = new Integer(idCategoria).intValue();
	}
	public int getIdAttivita() {
		return idAttivita;
	}
	public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
	}
	public void setIdAttivita(String idAttivita) {
		this.idAttivita = new Integer(idAttivita).intValue();
	}

	public int getIdMacroarea() {
		return idMacroarea;
	}
	public void setIdMacroarea(int idMacroarea) {
		this.idMacroarea = idMacroarea;
	}
	public void setIdMacroarea(String idMacroarea) {
		this.idMacroarea = new Integer(idMacroarea).intValue();
	}
	public LineaProduttiva(Connection db,int id)
	{
		queryRecord(db, id);
	}
	

	public int getIdVecchiaLinea() {
		return idVecchiaLinea;
	}

	public void setIdVecchiaLinea(int idVecchiaLinea) {
		this.idVecchiaLinea = idVecchiaLinea;
	}
	public void setIdVecchiaLinea(String idVecchiaLinea) {
		if (idVecchiaLinea!=null && !idVecchiaLinea.equals("") &&!idVecchiaLinea.equals("null"))
			this.idVecchiaLinea = Integer.parseInt(idVecchiaLinea);
	}

	public int getIdTipoVecchiaOrg() {
		return idTipoVecchiaOrg;
	}

	public void setIdTipoVecchiaOrg(int idTipoVecchiaOrg) {
		this.idTipoVecchiaOrg = idTipoVecchiaOrg;
	}

	public void queryRecord(Connection db, int id )
	{ 
		String sql = 	"select distinct rslp.data_inizio,rslp.stato,rslp.primario as primario," +
				"l.id_tipo_linea_produttiva," +
				"l.id_nuova_linea_attivita as id,rslp.id as id_rel_stab_lp,l.id_norma,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice_attivita as codice,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita  " +
				", rslp.id_vecchia_linea, rslp.id_tipo_vecchio_operatore  " +
				"from ml8_linee_attivita_nuove_materializzata l " + 
				"left join suap_ric_scia_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita " +
				"where l.id_nuova_linea_attivita = ?" +
				"order by l.id_Attivita" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			this.idRelazioneAttivita = id;
			if (rs.next())
				buildRecord(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		} 

	}
	
	
	
	public void queryRecordLineaProduttuva(Connection db, int id )
	{ 
		String sql = 	"select distinct l.id_nuova_linea_attivita as id_rel_stab_lp," +
				"l.id_tipo_linea_produttiva," +
				"l.id_nuova_linea_attivita as id,l.id_norma,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice_attivita as codice,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.path_descrizione as attivita ,"+ 
				"l.id_attivita as id_attivita  " +
				
				"from ml8_linee_attivita_nuove_materializzata l " + 
				"where l.id_nuova_linea_attivita = ?" +
				"order by l.id_Attivita" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			this.idRelazioneAttivita = id;
			if (rs.next())
				buildRecord(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		} 

	}








	public void buildRecord(ResultSet rs) throws SQLException
	{
		try
		{
			this.idNorma=rs.getInt("id_norma");
			
			try
			{
			this.id = rs.getInt("id_rel_stab_lp");
			//this.norma = rs.getString("norma");
			}catch
			(SQLException e)
			{
				
			}
			
			try
			{
				descr_label = rs.getString("descr_label");			
				}
			catch(SQLException e)
			{

			}
			
			try
			{
				permesso = rs.getString("permesso");
			}
			catch(SQLException e)
			{

			}
			
			
			

			try
			{
				codice_ufficiale_esistente = rs.getString("codice_ufficiale_esistente");
			}
			catch(SQLException e)
			{

			}
			try
			{
				this.numeroRegistrazione=rs.getString("numero_registrazione");

			}
			catch(SQLException e)
			{
			}


			try
			{
				idRelazioneAttivita = rs.getInt("id_linea_attivita");

			}
			catch(SQLException e)
			{
			}

			try
			{
				idTipoGestioneScia = rs.getInt("id_tipo_linea_produttiva");

			}
			catch(SQLException e)
			{
			}
			




			this.macrocategoria = rs.getString("macro");
			this.idMacroarea = rs.getInt("id_macrocategoria");
			this.idAttivita = rs.getInt("id_attivita");
			this.idCategoria = rs.getInt("id_categoria");
			this.categoria = rs.getString("aggregazione");
			this.attivita = rs.getString("attivita");
			
			
			//this.autorizzazione = rs.getString("autorizzazione");
			this.codice =rs.getString("codice");
			try
			{
				this.principale = rs.getBoolean("primario");
			}
			catch(SQLException e)
			{
			}
			try
			{
				this.descrizione_linea_attivita = rs.getString("descrizione_linea_attivita");
			}
			catch(SQLException e)
			{
			}


			try
			{
				this.dataInizio = rs.getTimestamp("data_inizio");
			}
			catch(SQLException e)
			{
			}
			try
			{
				this.dataFine = rs.getTimestamp("data_fine");
			}
			catch(SQLException e)
			{
			}

			this.dataInizioString =  getDataInizioasString();
			this.dataFineString = getDataFineasString();
			try
			{
				this.stato = rs.getInt("stato");
			}
			catch(SQLException e)
			{
			}

			try
			{
				this.id_rel_stab_lp = rs.getInt("id_rel_stab_lp");
			}catch(SQLException e)
			{
			}

			try
			{
				this.idVecchiaLinea = rs.getInt("id_vecchia_linea");
			}catch(SQLException e)
			{
			}

			try
			{
				this.idTipoVecchiaOrg = rs.getInt("id_tipo_vecchio_operatore");
			}catch(SQLException e)
			{
			}
			try
			{
				this.setIdLookupConfigurazioneValidazione(rs.getInt("id_lookup_configurazione_validazione"));
			}
			catch(SQLException ex)
			{
			}
			try
			{
				setCodiceNazionale(rs.getString("codice_nazionale"));
			}
			catch(SQLException ex)
			{
			}

		}
		catch(SQLException e)
		{
			throw (e) ;
		}
	}
	
	 
	
	
	/*cerca solo quelli di validazione, non dummy */
	public void searchCampiEstesiValidazione(Connection db) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			pst = db.prepareStatement("select * from linee_mobili_html_fields where gestione_interna is NULL and id_linea = ? and nome_campo is not null and enabled");
			pst.setInt(1, getIdRelazioneAttivita());
			rs = pst.executeQuery();
			
			
			
			while(rs.next())
			{
				LineeMobiliHtmlFields fields = new LineeMobiliHtmlFields();
				fields.buildRecordNoValoriCampi(db, rs);
				this.htmlFieldsValidazione.add(fields);
			}
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
	}
	
	
	
	/* cerca quelli di validazione e di inserimento scia, non dummy */
	public void searchTuttiCampiEstesiNonDummy(Connection db) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			pst = db.prepareStatement("select * from linee_mobili_html_fields where  id_linea = ? and nome_campo is not null");
			pst.setInt(1, getIdRelazioneAttivita());
			rs = pst.executeQuery();
			
			
			
			while(rs.next())
			{
				LineeMobiliHtmlFields fields = new LineeMobiliHtmlFields();
				fields.buildRecordNoValoriCampi(db, rs);
				this.htmlFieldsValidazione.add(fields);
			}
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
	}

	public LineaProduttiva(ResultSet rs) throws SQLException
	{
		buildRecord(rs);
	}

	public int getIdLookupConfigurazioneValidazione() {
		return idLookupConfigurazioneValidazione;
	}

	public void setIdLookupConfigurazioneValidazione(int idLookupConfigurazioneValidazione) {
		this.idLookupConfigurazioneValidazione = idLookupConfigurazioneValidazione;
	}

	public String getCodiceNazionale() {
		return codiceNazionale;
	}

	public void setCodiceNazionale(String codiceNazionale) {
		this.codiceNazionale = codiceNazionale;
	}

	public void addCampiEstesi(HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>>  valoriCampiEstesi) {
		this.campiEstesi = valoriCampiEstesi;
		
	}
	
	public HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>>  getCampiEstesi()
	{
		return this.campiEstesi;
	}
	

}
