/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

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

import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.beans.GenericBean;


public class LineaProduttiva extends GenericBean {
	
	
	public static final int NORMA_STABILIMENTI_852 = 49 ; 
	public static final int NORMA_STABILIMENTI_853 = 43 ;
	public static final int NORMA_AZIENDE_AGRICOLE = 7 ;
	public static final int NORMA_OPU_RICHIESTE = 1007 ;
	public static final int NORMA_SINTESIS = 2000 ;
	public static final int NORMA_APICOLTURA = 17 ;

	
	
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

	private HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>>  campiEstesi;

	private boolean sciaSospensione ;
	private Timestamp dataSospensionevolontaria ;
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
	private int id_lookup_tipo_linee_mobili;
	
	private int idVecchiaLinea = -1;
	private int idTipoVecchiaOrg = -1;

	private int categoriaRischio ;
	private Timestamp dataProssimoControllo ;
	private int numeroControlliUfficialiEseguiti;
	
	private boolean enabled ;
	
	private boolean consentiUploadFile;
	private boolean consentiValoriMultipli;
	private boolean existCampiEstesi;
	
	
	private String codiceNazionale;
	private String decodificaTipoProduzioneBdn;
	private String decodificaCodiceOrientamentoBdn;
	private String decodificaSpecieBdn;
	
	private ArrayList<LineeMobiliHtmlFields> htmlFieldsValidazione = new ArrayList<LineeMobiliHtmlFields>();
	
	private LineaProduttivaFlag flags = new LineaProduttivaFlag();
	private int tipo_carattere;
	
	public ArrayList<LineeMobiliHtmlFields> getHtmlFieldsValidazione() {
		return htmlFieldsValidazione;
	}

	public void setHtmlFieldsValidazione(ArrayList<LineeMobiliHtmlFields> htmlFieldsValidazione) {
		this.htmlFieldsValidazione = htmlFieldsValidazione;
	}
	
	public String getDecodificaTipoProduzioneBdn() {
		return decodificaTipoProduzioneBdn;
	}




	public void setDecodificaTipoProduzioneBdn(String decodificaTipoProduzioneBdn) {
		this.decodificaTipoProduzioneBdn = decodificaTipoProduzioneBdn;
	}




	public String getDecodificaCodiceOrientamentoBdn() {
		return decodificaCodiceOrientamentoBdn;
	}




	public void setDecodificaCodiceOrientamentoBdn(String decodificaCodiceOrientamentoBdn) {
		this.decodificaCodiceOrientamentoBdn = decodificaCodiceOrientamentoBdn;
	}




	public String getDecodificaSpecieBdn() {
		return decodificaSpecieBdn;
	}




	public void setDecodificaSpecieBdn(String decodificaSpecieBdn) {
		this.decodificaSpecieBdn = decodificaSpecieBdn;
	}




	public boolean isSciaSospensione() {
		return sciaSospensione;
	}




	public void setSciaSospensione(boolean sciaSospensione) {
		this.sciaSospensione = sciaSospensione;
	}




	public Timestamp getDataSospensionevolontaria() {
		return dataSospensionevolontaria;
	}


	
	
	public HashMap<Integer, ArrayList<LineaProduttivaCampoEsteso>> getCampiEstesi() {
		return campiEstesi;
	}




	public void setCampiEstesi(HashMap<Integer, ArrayList<LineaProduttivaCampoEsteso>> campiEstesi) {
		this.campiEstesi = campiEstesi;
	}




	public void addCampiEstesi(HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>>  valoriCampiEstesi) {
		this.campiEstesi = valoriCampiEstesi;
		
	}
	
	
	public void setDataSospensionevolontaria(Timestamp dataSospensionevolontaria) {
		this.dataSospensionevolontaria = dataSospensionevolontaria;
	}

	
	public Boolean getConsentiValoriMultipli() {
		return consentiValoriMultipli;
	}


	public void setConsentiValoriMultipli(Boolean consentiValoriMultipli) {
		this.consentiValoriMultipli = consentiValoriMultipli;
	}
	
	public Boolean existCampiEstesi() {
		return existCampiEstesi;
	}


	public void setExistCampiEstesi(Boolean existCampiEstesi) {
		this.existCampiEstesi = existCampiEstesi;
	}
	
	public Boolean getConsentiUploadFile() {
		return consentiUploadFile;
	}


	public void setConsentiUploadFile(Boolean consentiUploadFile) {
		this.consentiUploadFile = consentiUploadFile;
	}
	
	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public int getNumeroControlliUfficialiEseguiti() {
		return numeroControlliUfficialiEseguiti;
	}


	public void setNumeroControlliUfficialiEseguiti(int numeroControlliUfficialiEseguiti) {
		this.numeroControlliUfficialiEseguiti = numeroControlliUfficialiEseguiti;
	}


	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}
	
	
	public int getMobile(){
		return id_lookup_tipo_linee_mobili;
	}
	
	public void setMobile(int id_lookup_tipo_linee_mobili){
		this.id_lookup_tipo_linee_mobili=id_lookup_tipo_linee_mobili;
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

	

	private InformazioniStabilimento infoStab = new InformazioniStabilimento() ;

	
	
	
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
	public void queryRecord(Connection db, int id )
	{ 
		String sql = 	"select distinct rslp.data_inizio,rslp.stato,rslp.primario as primario," +
				"l.id_nuova_linea_attivita as id,rslp.id as id_rel_stab_lp,l.id_norma,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice as codice,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita  " +
				", rslp.id_vecchia_linea, rslp.id_tipo_vecchio_operatore, l.id_lookup_tipo_linee_mobili  " +
				"from ml8_linee_attivita_nuove_materializzata l " + 
				"left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita " +
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
	
	public void queryRecordByIdRelStabLp(Connection db, int idRelStabL )
	{ 
		String sql = 	"select distinct rslp.data_inizio,rslp.stato,rslp.primario as primario," +
				"rslp.id_stabilimento, " +
				"l.id_nuova_linea_attivita as id,rslp.id as id_rel_stab_lp,l.id_norma,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice as codice,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita  " +
				", rslp.id_vecchia_linea, rslp.id_tipo_vecchio_operatore, l.id_lookup_tipo_linee_mobili " +
				"from ml8_linee_attivita_nuove_materializzata l " + 
				"left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita " +
				"where rslp.id = ?" +
				"order by l.id_Attivita" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idRelStabL);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				buildRecord(rs);
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		} 
 
	}
	
	
	public int getProgressivoLineaAttivita(Connection db ,String numeroRegistrazioneStabilimento)
	{ 
		String sql = 	"select * " +
				"from dbi_opu_get_progressivo_linea_per_stabilimento(?)" ;
		
		int progressivo = -1 ;
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, numeroRegistrazioneStabilimento);
			ResultSet rs = pst.executeQuery();

			if (rs.next())
				progressivo = rs.getInt(1);
			
			
		}
		catch(SQLException e)
		{
			e.printStackTrace() ;
		} 
		return progressivo ; 
	}
	
	public void queryRecordScelta(Connection db, int id )
	{ 
		String sql = 	"select distinct path_descrizione as descrizione_linea_attivita," +
				"l.id_nuova_linea_attivita as id,l.id_norma,l.norma as norma, l.macroarea as macro,l.id_macroarea as id_macrocategoria,"+
				"l.codice as codice,l.aggregazione as aggregazione,l.id_aggregazione as id_categoria ,l.attivita as attivita ,"+ 
				"l.id_attivita as id_attivita,l.id_lookup_tipo_linee_mobili  " +
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
			this.id = rs.getInt("id");
			this.id_lookup_tipo_linee_mobili=rs.getInt("id_lookup_tipo_linee_mobili");
			//this.norma = rs.getString("norma");
			
			try
			{
				this.tipo_carattere = rs.getInt("tipo_carattere");
			}
			catch(SQLException e)
			{
				
			}
			
			try
			{
				existCampiEstesi = rs.getBoolean("exist_campi_estesi");
			}
			catch(SQLException e)
			{
				
			}

			try
			{
				consentiUploadFile = rs.getBoolean("consenti_upload_file");
			}
			catch(SQLException e)
			{
				
			}
			
			try
			{
				consentiValoriMultipli = rs.getBoolean("consenti_valori_multipli");
			}
			catch(SQLException e)
			{
				
			}
			
			try
			{
				idStabilimento = rs.getInt("id_stabilimento");
			}
			catch(SQLException e)
			{
				
			}
			
			
			try
			{
				sciaSospensione = rs.getBoolean("scia_sospensione");
			}
			catch(SQLException e)
			{
				
			}
			
			try
			{
				dataSospensionevolontaria = rs.getTimestamp("data_sospensione_volontaria");
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
				System.out.println("Colonna data_inizio non selezionata");
			}
			
			try
			{
				this.setCodiceNazionale(rs.getString("codice_nazionale"));

			}
			catch(SQLException e)
			{
				System.out.println("Colonna codice_nazionale non selezionata");
			}
			
			try
			{
				idRelazioneAttivita = rs.getInt("id_linea_attivita");

			}
			catch(SQLException e)
			{
				System.out.println("Colonna id_linea_produttiva non selezionata");
			}
			
			try
			{
				idTipoGestioneScia = rs.getInt("id_tipo_linea_produttiva");

			}
			catch(SQLException e)
			{
				System.out.println("Colonna id_linea_produttiva non selezionata");
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
				System.out.println("Colonna data_inizio non selezionata");
			}
			try
			{
				this.descrizione_linea_attivita = rs.getString("descrizione_linea_attivita");
			}
			catch(SQLException e)
			{
				System.out.println("Colonna data_inizio non selezionata");
			}
			
			
			try
			{
				this.dataInizio = rs.getTimestamp("data_inizio");
			}
			catch(SQLException e)
			{
				System.out.println("Colonna data_inizio non selezionata");
			}
			try
			{
				this.dataFine = rs.getTimestamp("data_fine");
			}
			catch(SQLException e)
			{
				System.out.println("Colonna data_inizio non selezionata");
			}
			
			this.dataInizioString =  getDataInizioasString();
			this.dataFineString = getDataFineasString();
			try
			{
			this.stato = rs.getInt("stato");
			}
			catch(SQLException e)
			{
				System.out.println("Colonna stato non selezionata");
			}
			
			try
			{
				this.id_rel_stab_lp = rs.getInt("id_rel_stab_lp");
			}catch(SQLException e)
			{
				System.out.println("Colonna id_rel_stab_lp non selezionata");
			}
			
			try
			{
				this.idVecchiaLinea = rs.getInt("id_vecchia_linea");
			}catch(SQLException e)
			{
				System.out.println("Colonna id_vecchia_linea non selezionata");
			}
			
			try
			{
				this.idTipoVecchiaOrg = rs.getInt("id_tipo_vecchio_operatore");
			}catch(SQLException e)
			{
				System.out.println("Colonna id_tipo_vecchio_operatore non selezionata");
			}
			
			
			try {setFlags(new LineaProduttivaFlag(rs));} catch(Exception e)
			{
				System.out.println("Colonna id_tipo_vecchio_operatore non selezionata");
			}
			
		}
		catch(SQLException e)
		{
			throw (e) ;
		}
	}
	
	public LineaProduttiva(ResultSet rs) throws SQLException
	{
		buildRecord(rs);
	}
	
		
	public boolean verificaEsistenzaLinea(Connection db,int idStabilimento) throws SQLException
	{
		String sql = "select * from opu_relazione_stabilimento_linee_produttive  where enabled and id_stabilimento =? and id_linea_produttiva = ?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idStabilimento);
		pst.setInt(2, this.idRelazioneAttivita);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			return true ;
		return false;
	}
	
	public String getDescrizioneAttivita(Connection db){
		String descrizione = "";
		String query = "select false as primario,att.description from opu_lookup_attivita_linee_produttive_aggregazioni att " +
				"left join opu_relazione_attivita_produttive_aggregazioni rel on " +
				"(att.code = rel.id_attivita_aggregazione) where rel.id = ?";
		try {
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, this.getIdRelazioneAttivita());
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()){
				descrizione = rs.getString("description");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return descrizione;
	}
	
	public boolean insert(Connection db) throws SQLException {
		// TODO Auto-generated method stub
		
		return true;
		
	}
	public InformazioniStabilimento getInfoStab() {
		return infoStab;
	}

	public void setInfoStab(InformazioniStabilimento infoStab) {
		this.infoStab = infoStab;
	}



	public int getIdVecchiaLinea() {
		return idVecchiaLinea;
	}
	
	public void generaNumeroProtocollo(Connection db , String numProtocolloStabilimento) throws SQLException
	{
		String sel = "select max(progressivo)+1,num_protocollo from opu_progressivi_protocolli_linee_stabilimento where num_protocollo =? group by num_protocollo ";
		PreparedStatement pst = db.prepareStatement(sel);
		pst.setString(1, numProtocolloStabilimento);
		ResultSet rs = pst.executeQuery();
		
		int prog=0;
		if (rs.next())
		{
			prog = rs.getInt(1) ;
		
		}
		
		else
		{
			prog=prog+1 ;
		}
		String progressivoPadding = org.aspcfs.utils.StringUtils.zeroPad(prog,3);
		this.num_protocollo=numProtocolloStabilimento+"-"+progressivoPadding;
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
	
	
	private ArrayList<LineeAttivita> candidati;
	public void setCandidatiNuoveLineeRanking(ArrayList<LineeAttivita> candidati)
	{
		this.candidati = candidati;
	}
	
	public ArrayList<LineeAttivita> getCandidatiPerRanking()
	{
		return this.candidati;
	}

	public void setDecodificaBdn(Connection db) throws SQLException{

		/* Controlli sul benessere */
		
		String sql = null;
		PreparedStatement pst = null;
		
		//Estraggo dati dalla linea del controllo
		sql = "select l.decodifica_tipo_produzione_bdn, l.decodifica_codice_orientamento_bdn, l.decodifica_specie_bdn, l.livello, l.id_attivita "
	          + " from ml8_linee_attivita_nuove_materializzata l where l.id_nuova_linea_attivita = ? ";
	    pst = db.prepareStatement(sql);
		pst.setInt(1, idAttivita);
		ResultSet rs1 = pst.executeQuery();
		String allev = "";
		String specieAllev= "";
		int idAttivita = -1;
		int livello = -1;
		
		if (rs1.next()) {
			allev = rs1.getString("decodifica_tipo_produzione_bdn");
			specieAllev = rs1.getString("decodifica_specie_bdn");
			idAttivita = rs1.getInt("id_attivita");
			livello = rs1.getInt("livello");
		}
		
		if (livello>3){
		
			sql = "select decodifica_tipo_produzione_bdn, decodifica_codice_orientamento_bdn, decodifica_specie_bdn "
			          + " from ml8_linee_attivita_nuove_materializzata "
			         + " where livello = 3 and id_attivita   = ?  ";
				pst = db.prepareStatement(sql);
				pst.setInt(1, idAttivita);
				ResultSet rs2 = pst.executeQuery();
				// Il CU riguarda una linea che prevede le checklist? 
				
				if (rs2.next()) {
					allev = rs2.getString("decodifica_tipo_produzione_bdn");
					specieAllev = rs2.getString("decodifica_specie_bdn");
				}
		}
		
		decodificaTipoProduzioneBdn = allev;
		decodificaSpecieBdn = specieAllev;

	}




	public String getCodiceNazionale() {
		return codiceNazionale;
	}




	public void setCodiceNazionale(String codiceNazionale) {
		this.codiceNazionale = codiceNazionale;
	}




	public LineaProduttivaFlag getFlags() {
		return flags;
	}




	public void setFlags(LineaProduttivaFlag flags) {
		this.flags = flags;
	}
	
	
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

	public int getTipo_carattere() {
		return tipo_carattere;
	}

	public void setTipo_carattere(int tipo_carattere) {
		this.tipo_carattere = tipo_carattere;
	}
	
	public static ArrayList<LineaProduttiva> getListaLineeDaRicerca(Connection db, int riferimentoId, String riferimentoIdNomeTab) throws SQLException{
		ArrayList<LineaProduttiva> listaLinee = new ArrayList<LineaProduttiva>();
		PreparedStatement pst = db.prepareStatement("select * from ricerche_anagrafiche_old_materializzata where riferimento_id = ? and riferimento_id_nome_tab = ?");
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			LineaProduttiva linea = new LineaProduttiva();
			linea.setId_rel_stab_lp(rs.getInt("id_linea"));
			linea.setCodice(rs.getString("codice_macroarea")+"-"+rs.getString("codice_aggregazione")+"-"+rs.getString("codice_attivita"));
			linea.setDescrizione_linea_attivita(rs.getString("path_attivita_completo"));
			listaLinee.add(linea);
		}
		return listaLinee;		
		
	}
	
}
