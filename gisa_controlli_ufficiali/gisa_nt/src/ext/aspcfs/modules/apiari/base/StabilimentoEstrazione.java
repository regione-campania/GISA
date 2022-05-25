/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import it.izs.apicoltura.apianagraficaattivita.ws.Api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

public class StabilimentoEstrazione extends GenericBean {


	Logger logger = Logger.getLogger(StabilimentoEstrazione.class);
	private List<Operatore> listaOperatori = null ;

	public static final String OPERAZIONE_INSERIMENTO_OK  ="1";
	public static final String OPERAZIONE_INSERIMENTO_KO_IMPRESA_ESISTENTE  ="2";
	public static final String OPERAZIONE_INSERIMENTO_KO_STABILIMENTO_ESISTENTE  ="4";

	private int tipoCarattere;
	private int tipologiaApiarioLaboratorio ;
	private int idVariazioneEseguita ;
	private String action = "" ;
	private String pageInclude = "" ;

	private int idOperatore ;
	private Indirizzo sedeOperativa =new Indirizzo();
	private Timestamp entered;
	private Timestamp modified;
	private int enteredBy ;
	private int modifiedBy ;
	private String codiceInterno;
	private String comuneSedeLegale;
	private String indirizzoSedeLegale;
	private SoggettoFisico detentore = new SoggettoFisico();
	private int categoriaRischio ;
	private Timestamp dataSincronizzazione ;

	/*PARAMETRO USATO PER MANTENERE LA GENERALIZZAZIONE DELLA GESTIONE DEI CONTROLLI*/
	private int orgId ;
	private int siteId ;
	private String name ;
	private Timestamp dataCessazione ;
	private Operatore operatore ;
	private int stato ;
	private int tipologia;
	private int idApicolturaClassificazione ; 
	private int idApicolturaSottospecie ; 
	private int idApicolturaModalita ; 
	private int numApiari ;
	private int numAlveari ;
	private int numSciami ;
	private int numPacchi ;
	private int numRegine ;
	private Timestamp dataApertura ;
	private Timestamp dataChiusura ;
	private String progressivoBDA ;
	private int idBda ;
	private Timestamp data_assegnazione_detentore;
	private Timestamp data_assegnazione_ubicazione;
	private Timestamp data_assegnazione_censimento;
	private String erroreValidazione ;
	private boolean sincronizzatoBdn ;
	private boolean flagLaboratorio ;

	
	private int numalAlveariEffettivi ;
	
	
	
	
	public int getTipoCarattere() {
		return tipoCarattere;
	}



	public void setTipoCarattere(int tipoCatattere) {
		this.tipoCarattere = tipoCatattere;
	}



	public boolean isFlagLaboratorio() {
		return flagLaboratorio;
	}



	public void setFlagLaboratorio(boolean flagLaboratorio) {
		this.flagLaboratorio = flagLaboratorio;
	}



	public int getTipologiaApiarioLaboratorio() {
		return tipologiaApiarioLaboratorio;
	}



	public void setTipologiaApiarioLaboratorio(int tipologiaApiarioLaboratorio) {
		this.tipologiaApiarioLaboratorio = tipologiaApiarioLaboratorio;
	}



	public int getNumalAlveariEffettivi() {
		return numalAlveariEffettivi;
	}



	public void setNumalAlveariEffettivi(int numalAlveariEffettivi) {
		this.numalAlveariEffettivi = numalAlveariEffettivi;
	}



	public String getErroreValidazione() {
		return erroreValidazione;
	}



	public void setErroreValidazione(String erroreValidazione) {
		this.erroreValidazione = erroreValidazione;
	}



	public boolean isSincronizzatoBdn() {
		return sincronizzatoBdn;
	}



	public void setSincronizzatoBdn(boolean sincronizzatoBdn) {
		this.sincronizzatoBdn = sincronizzatoBdn;
	}



	public int getNumPacchi() { 
		return numPacchi;
	}



	public void setNumPacchi(int numPacchi) {
		this.numPacchi = numPacchi;
	}

	public void setNumPacchi(String numPacchi) {
		if (numPacchi!= null && !"".equals(numPacchi))
			this.numPacchi = Integer.parseInt(numPacchi);
	}

	public int getNumRegine() {
		return numRegine;
	}



	public void setNumRegine(int numRegine) {
		this.numRegine = numRegine;
	}

	public void setNumRegine(String numRegine) {
		if (numRegine!= null && !"".equals(numRegine))
			this.numRegine = Integer.parseInt(numRegine);
	}

	public int getIdVariazioneEseguita() {
		return idVariazioneEseguita;
	}



	public void setIdVariazioneEseguita(int idVariazioneEseguita) {
		this.idVariazioneEseguita = idVariazioneEseguita;
	}



	public int getIdBda() {
		return idBda;
	}



	public void setIdBda(int idBda) {
		this.idBda = idBda;
	}


	public int getTipologia() {
		return tipologia;
	}



	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}



	public Timestamp getData_assegnazione_censimento() {
		return data_assegnazione_censimento;
	}



	public void setData_assegnazione_censimento(Timestamp data_assegnazione_censimento) {
		this.data_assegnazione_censimento = data_assegnazione_censimento;
	}



	public void setData_assegnazione_censimento(String data_assegnazione_censimento) throws ParseException {
		if (!"".equals(data_assegnazione_censimento))
		{
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
			this.data_assegnazione_censimento = new Timestamp(sdf.parse(data_assegnazione_censimento).getTime());
		}
	}

	public Timestamp getData_assegnazione_ubicazione() {
		return data_assegnazione_ubicazione;
	}



	public void setData_assegnazione_ubicazione(Timestamp data_assegnazione_ubicazione) {
		this.data_assegnazione_ubicazione = data_assegnazione_ubicazione;
	}

	public void setData_assegnazione_ubicazione(String data_assegnazione_ubicazione) throws ParseException {
		if (!"".equals(data_assegnazione_ubicazione))
		{
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
			this.data_assegnazione_ubicazione = new Timestamp(sdf.parse(data_assegnazione_ubicazione).getTime());
		}
	}



	public Timestamp getData_assegnazione_detentore() {
		return data_assegnazione_detentore;
	}



	public void setData_assegnazione_detentore(Timestamp data_assegnazione_detentore) {
		this.data_assegnazione_detentore = data_assegnazione_detentore;
	}


	public void setData_assegnazione_detentore(String dataAssegnazione) throws ParseException {
		if (!"".equals(dataAssegnazione))
		{
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
			this.data_assegnazione_detentore = new Timestamp(sdf.parse(dataAssegnazione).getTime());
		}
	}


	public String getProgressivoBDA() {
		return progressivoBDA;
	}



	public void setProgressivoBDA(String progressivoBDA) {
		this.progressivoBDA = progressivoBDA;
	}



	public int getNumAlveari() {
		return numAlveari;
	}



	public void setNumAlveari(int numAlveari) {
		this.numAlveari = numAlveari;
	}
	
	public int getNumApiari() {
		return numApiari;
	}

	public void setNumApiari(int numApiari) {
		this.numApiari = numApiari;
	}
	public void setNumAlveari(String numAlveari) {
		if (!"".equals(numAlveari))
			this.numAlveari = Integer.parseInt(numAlveari);
	}


	public int getNumSciami() {
		return numSciami;
	}



	public void setNumSciami(int numSciami) {
		this.numSciami = numSciami;
	}
	public void setNumSciami(String numSciami) {

		if (!"".equals(numSciami))
			this.numSciami = Integer.parseInt(numSciami);

	}


	public Timestamp getDataApertura() {
		return dataApertura;
	}



	public void setDataApertura(Timestamp dataApertura) {
		this.dataApertura = dataApertura;
	}


	public void setDataApertura(String dataApertura)  {
		if (!"".equals(dataApertura))
		{
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
			try {
				this.dataApertura = new Timestamp(sdf.parse(dataApertura).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Timestamp getDataChiusura() {
		return dataChiusura;
	}



	public void setDataChiusura(Timestamp dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public void setDataChiusura(String dataChiusura) throws ParseException {
		if (!"".equals(dataChiusura))
		{
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
			this.dataChiusura = new Timestamp(sdf.parse(dataChiusura).getTime());
		}
	}

	public int getIdApicolturaClassificazione() {
		return idApicolturaClassificazione;
	}



	public void setIdApicolturaClassificazione(int idApicolturaClassificazione) {
		this.idApicolturaClassificazione = idApicolturaClassificazione;
	}

	public void setIdApicolturaClassificazione(String idApicolturaClassificazione) {
		if (!"".equals(idApicolturaClassificazione))
			this.idApicolturaClassificazione = Integer.parseInt(idApicolturaClassificazione);
	}



	public int getIdApicolturaSottospecie() {
		return idApicolturaSottospecie;
	}



	public void setIdApicolturaSottospecie(int idApicolturaSottospecie) {
		this.idApicolturaSottospecie = idApicolturaSottospecie;
	}

	public void setIdApicolturaSottospecie(String idApicolturaSottospecie) {
		if (!"".equals(idApicolturaSottospecie))
			this.idApicolturaSottospecie = Integer.parseInt(idApicolturaSottospecie);
	}


	public int getIdApicolturaModalita() {
		return idApicolturaModalita;
	}



	public void setIdApicolturaModalita(int idApicolturaModalita) {
		this.idApicolturaModalita = idApicolturaModalita;
	}

	public void setIdApicolturaModalita(String idApicolturaModalita) {
		if (!"".equals(idApicolturaModalita))
			this.idApicolturaModalita = Integer.parseInt(idApicolturaModalita);
	}



	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}



	public void setComuneSedeLegale(String comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}
	
	public String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}



	public void setIndirizzoSedeLegale(String indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}
	
	public String getCodiceInterno() {
		return codiceInterno;
	}



	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}



	public int getStato() {
		return stato;
	}



	public void setStato(int stato) {
		this.stato = stato;
	}



	public StabilimentoEstrazione(){

		detentore = new SoggettoFisico();
		sedeOperativa = new Indirizzo() ;


	}

	
	public StabilimentoEstrazione(Api apiario , Connection db,ActionContext context) throws SQLException{
		
		
		
		
		this.setNumAlveari(apiario.getNumAlveari());
		this.setNumSciami(apiario.getNumSciami());
		this.setProgressivoBDA(apiario.getProgressivo()+"");
		this.setStato(StabilimentoAction.API_STATO_VALIDATO);
		this.setDataApertura(new Timestamp(apiario.getDtApertura().getMillisecond()));
		if(apiario.getDtChiusura()!=null)
			this.setDataChiusura(new Timestamp(apiario.getDtChiusura().getMillisecond()));

		Indirizzo ind = new Indirizzo();
		ind.setComune(ComuniAnagrafica.getIdComune(apiario.getComDescrizione(), db));
		ind.setComuneTesto(apiario.getComDescrizione());
		ind.setVia(apiario.getIndirizzo());
		ind.setCap(apiario.getCap());
		ind.setProvincia(apiario.getComProSigla() );
		ind.setIdAsl(db);
		ind.setTipologiaSede(5);
		this.setIdAsl(ind.getIdAsl());
		this.setSedeOperativa(ind);
		ind.insert(db, context);
		
		
		try {
			PreparedStatement pst = db.prepareStatement("select code from  apicoltura_lookup_classificazione where codice_bdn ilike ? ");

			pst.setString(1, apiario.getClassificazione());
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				this.setIdApicolturaClassificazione(rs.getInt(1));
			
			
			
			pst = db.prepareStatement("select code  from  apicoltura_lookup_sottospecie where codice_bdn ilike ? ");
			pst.setString(1, apiario.getApisotspeCodice());
			rs = pst.executeQuery();
			if (rs.next())
				this.setIdApicolturaSottospecie(rs.getInt(1));
			
			pst = db.prepareStatement("select code  from  apicoltura_lookup_modalita where codice_bdn ilike ? ");
			pst.setString(1, apiario.getApimodallCodice());
			rs = pst.executeQuery();
			if (rs.next())
				this.setIdApicolturaModalita(rs.getInt(1));
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}


	public StabilimentoEstrazione(Connection db, int idStabilimento) {

		try {
			queryRecord(db,idStabilimento);
			operatore = new Operatore();
			operatore.queryRecordOperatore(db, idOperatore);
			this.setName(operatore.getRagioneSociale());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public boolean delete(Connection db,int idUtente)
	{
		String sql = "UPDATE apicoltura_apiari set trashed_date = current_date,modified_by =? where id =?" ;
		try {
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idUtente);
			pst.setInt(2, this.getIdStabilimento());
			pst.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false ;
		}

				return true ;
	}

	public StabilimentoEstrazione(Connection db, int idStabilimento,boolean flagOperatore) {

		try {
			queryRecord(db,idStabilimento);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}




	public void update(Connection db,ActionContext context)
	{
		int i = 0 ;


		String sql = "update apicoltura_apiari set id_indirizzo=?,modified=current_timestamp,modified_by=? ,num_sciami=?,num_alveari=?,data_inizio = ?,id_apicoltura_lookup_classificazione=?,id_apicoltura_lookup_sottospecie=?,id_apicoltura_lookup_modalita=?,id_soggetto_fisico=?,num_pacchi=?,num_regine=? where id =? " ;
		try
		{
			Indirizzo sedeOperativa = this.getSedeOperativa();
			sedeOperativa.setIdIndirizzo(-1);
			sedeOperativa.insert(db, context);

			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(++i, sedeOperativa.getIdIndirizzo());
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, numSciami);
			pst.setInt(++i, numAlveari);
			pst.setTimestamp(++i, dataApertura);
			pst.setInt(++i, idApicolturaClassificazione);
			pst.setInt(++i, idApicolturaSottospecie);
			pst.setInt(++i, idApicolturaModalita);
			pst.setInt(++i, this.getDetentore().getIdSoggetto());
			pst.setInt(++i, numPacchi);
			pst.setInt(++i, numRegine);

			pst.setInt(++i, idStabilimento);
			pst.execute();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}


	}

	public Operatore getOperatore() {
		return operatore;
	}



	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}


	public int getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}

	public void setIdOperatore(String idOperatore) {
		if (idOperatore != null && ! "".equals(idOperatore))
			this.idOperatore = Integer.parseInt(idOperatore);
	}


	public StabilimentoEstrazione(ResultSet rs) throws SQLException{
		this.buildRecord(rs);
	}

	public void setSedeOperativa(Sede sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}

	public SoggettoFisico getDetentore() {
		return detentore;
	}

	public void setDetentore(SoggettoFisico Detentore) {
		this.detentore = Detentore;
	}




	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	public Timestamp getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Timestamp dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Indirizzo getSedeOperativa() {
		return sedeOperativa;
	}

	public void setSedeOperativa(Indirizzo sedeOperativa) {
		this.sedeOperativa = sedeOperativa;
	}





	public Timestamp getDataSincronizzazione() {
		return dataSincronizzazione;
	}

	public void setDataSincronizzazione(Timestamp dataSincronizzazione) {
		this.dataSincronizzazione = dataSincronizzazione;
	}

	public void queryRecord(Connection db , int idStabilimento) throws SQLException, IndirizzoNotFoundException
	{
		if (idStabilimento == -1){
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db.prepareStatement("Select o.*,impresa.ragione_sociale from "
				+ "apicoltura_apiari o join apicoltura_imprese impresa on impresa.id=o.id_operatore where o.trashed_date is null and impresa.trashed_date is null and o.id = ?");
		pst.setInt(1, idStabilimento);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
			sedeOperativa = new Indirizzo(db,sedeOperativa.getIdIndirizzo()) ;
			detentore = new SoggettoFisico(db,rs.getInt("id_soggetto_fisico"));

		}

		if (idStabilimento == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		rs.close();
		pst.close();



	}




	public boolean insert (Connection db, boolean insertLinee,ActionContext context) throws SQLException
	{
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
		try {
			modifiedBy = enteredBy;

			idAsl= sedeOperativa.getIdAsl();

			this.idStabilimento =DatabaseUtils.getNextSeqInt(db, context,"apicoltura_apiari","id");
			sql.append("INSERT INTO apicoltura_apiari (flag_laboratorio,id_apicoltura_lookup_sottospecie,id_apicoltura_lookup_classificazione,id_apicoltura_lookup_modalita,num_alveari,num_sciami,data_inizio,id_asl,progressivo,stato,num_pacchi,num_regine,");


			if (dataChiusura!=null) {
				sql.append("data_chiusura, ");
			}



			if (stato == StabilimentoAction.API_STATO_VALIDATO) {
				sql.append("sincronizzato_bdn, ");
			}

			if (idStabilimento > -1) {
				sql.append("id, ");
			}

			if (enteredBy > -1){
				sql.append("entered_by,");
			}

			if (modifiedBy > -1){
				sql.append("modified_by,");
			}
			if (idOperatore > -1){
				sql.append("id_operatore,");
			}
			if(detentore!=null && detentore.getIdSoggetto()>0)
			{
				sql.append("id_soggetto_fisico,");

			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				sql.append("id_indirizzo,");

			}



			sql.append("entered,modified)");
			sql.append("VALUES (?,?,?,?,?,?,?,(select codiceistatasl::int from comuni1 where id = ? ),?,?,?,?,");

			
			if (dataChiusura!=null) {
				sql.append("?, ");
			}



			if (stato == StabilimentoAction.API_STATO_VALIDATO) {
				sql.append("?,");
			}
			if (idStabilimento > -1) {
				sql.append("?,");
			}

			if (enteredBy > -1){
				sql.append("?,");
			}


			if (modifiedBy > -1){
				sql.append("?,");
			}
			if (idOperatore > -1){
				sql.append("?,");
			}
			if(detentore!=null && detentore.getIdSoggetto()>0)
			{
				sql.append("?,");

			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				sql.append("?,");

			}


			sql.append("current_date,current_date)");


			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setBoolean(++i, flagLaboratorio);
			pst.setInt(++i, idApicolturaSottospecie);
			pst.setInt(++i, idApicolturaClassificazione);
			pst.setInt(++i, idApicolturaModalita);
			pst.setInt(++i, numAlveari);
			pst.setInt(++i, numSciami);
			pst.setTimestamp(++i, dataApertura);
			pst.setInt(++i, this.getSedeOperativa().getComune());
			if (progressivoBDA!=null)
				pst.setInt(++i, Integer.parseInt(progressivoBDA));
			else
				pst.setInt(++i, 0);
				
			pst.setInt(++i, stato);
			pst.setInt(++i, numPacchi);
			pst.setInt(++i, numRegine);


			if (dataChiusura!=null)
				pst.setTimestamp(++i, dataChiusura);


			if (stato == StabilimentoAction.API_STATO_VALIDATO) {
				pst.setBoolean(++i,true);
			}
			if (idStabilimento > -1) {
				pst.setInt(++i,idStabilimento);
			}

			if (enteredBy > -1){
				pst.setInt(++i, this.enteredBy);
			}


			if (modifiedBy > -1){ 
				pst.setInt(++i, this.modifiedBy);
			}
			if (idOperatore > -1){
				pst.setInt(++i, idOperatore);
			}
			if(detentore!=null && detentore.getIdSoggetto()>0)
			{
				pst.setInt(++i, detentore.getIdSoggetto());

			}
			if(sedeOperativa!=null && sedeOperativa.getIdIndirizzo()>0)
			{
				pst.setInt(++i, sedeOperativa.getIdIndirizzo());

			}


			pst.execute();
			pst.close();


			if (doCommit) {
				db.commit();
			}
		} catch (SQLException e) {
			if (doCommit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (doCommit) {
				db.setAutoCommit(true);
			}
		}
		return true;
	}






	protected void buildRecord(ResultSet rs) throws SQLException {

		//this.setDataCessazione(rs.getTimestamp("data_cessazione"));
		try
		{
			this.setName(rs.getString("ragione_sociale"));
		}
		catch(PSQLException e)
		{

		}
		try
		{
			this.setFlagLaboratorio(rs.getBoolean("flag_laboratorio"));
		}
		catch(PSQLException e)
		{

		}
		
		
		numalAlveariEffettivi = rs.getInt("num_alveari_effettivi");
		setTipologia(Ticket.TIPO_API);
		numPacchi=rs.getInt("num_pacchi");
		numRegine=rs.getInt("num_regine");
		numAlveari = rs.getInt("num_alveari");
		numSciami = rs.getInt("num_sciami");
		dataApertura = rs.getTimestamp("data_inizio");
		dataChiusura = rs.getTimestamp("data_chiusura");
		idApicolturaClassificazione = rs.getInt("id_apicoltura_lookup_classificazione");
		idApicolturaSottospecie = rs.getInt("id_apicoltura_lookup_sottospecie");
		idApicolturaModalita = rs.getInt("id_apicoltura_lookup_modalita");
		this.setCodiceInterno(rs.getString("codice_interno"));
		this.setIdStabilimento(rs.getInt("id"));
		this.setOrgId(this.getIdStabilimento());
		this.setEnteredBy(rs.getInt("entered_by"));
		this.setModifiedBy(rs.getInt("modified_by"));
		this.setIdOperatore(rs.getInt("id_operatore"));
		this.setCategoriaRischio(rs.getInt("categoria_rischio"));
		sedeOperativa.setIdIndirizzo(rs.getInt("id_indirizzo"));
		data_assegnazione_detentore = rs.getTimestamp("data_assegnazione_detentore");
		data_assegnazione_ubicazione = rs.getTimestamp("data_assegnazione_ubicazione");
		data_assegnazione_censimento = rs.getTimestamp("data_assegnazione_censimento");
		progressivoBDA = rs.getInt("progressivo")+"";
		this.stato=rs.getInt("stato");
		idBda=rs.getInt("id_bda");
		idAsl=rs.getInt("id_asl");
		sincronizzatoBdn = rs.getBoolean("sincronizzato_bdn");
	}




	public String getAction()
	{
		return "ApicolturaApiari" ;
	}

	public String getPageInclude()
	{
		return pageInclude ;
	}


	public List<Operatore> getListaOperatori() {
		return listaOperatori;
	}



	public void setListaOperatori(List<Operatore> listaOperatori) {
		this.listaOperatori = listaOperatori;
	}


	public String checkEsistenza(Connection db) {
		boolean exist = false;
		String query = "select * " +
				" from apicoltura_apiari o " 
				+ " where o.id_indirizzo = ? and id_operatore = ?  ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setInt(++i, this.getSedeOperativa().getIdIndirizzo());
			pst.setInt(++i, this.getIdOperatore());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return "4"; 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return "";
	} 
	
	
	public boolean checkEsistenzaApiario(Connection db) {
		boolean exist = false;
		String query = "select * " +
				" from apicoltura_apiari o " 
				+ " where o.progressivo = ? and id_operatore = ?  ";

		PreparedStatement pst;
		try {
			int i = 0;
			pst = db.prepareStatement(query);
			pst.setInt(++i, Integer.parseInt(this.getProgressivoBDA()));
			pst.setInt(++i, this.getIdOperatore());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;
	} 


	public void cessaAttivita(Connection db) throws SQLException, ParseException
	{
		String sql = "update apicoltura_apiari set data_chiusura = ? , data_cessazione=current_timestamp,cessato_da=?,stato=? where id =?";


		PreparedStatement pst = db.prepareStatement(sql);
		pst.setTimestamp(1, dataChiusura);
		pst.setInt(2, modifiedBy);
		pst.setInt(3, StabilimentoAction.API_STATO_CESSATO);
		pst.setInt(4, idStabilimento);
		pst.execute();

	}

	public  void cambioStato(Connection db)
	{
		try {
			java.util.Date date = new java.util.Date();



			String update = "UPDATE apicoltura_apiari set stato = ?,modified_by=?,modified=current_timestamp,data_chiusura=? where id = ?";

			int i = 0;
			PreparedStatement pst = db.prepareStatement(update);
			pst.setInt(++i, this.getStato());
			pst.setInt(++i, this.getModifiedBy());
			pst.setTimestamp(++i, dataChiusura);
			pst.setInt(++i, this.getIdStabilimento());

			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {
			logger.error(e.getMessage());

		} finally {

		}



	}

	public boolean cambioDetentore(Connection db,ActionContext context)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			UserBean utente = (UserBean)context.getSession().getAttribute("User");
			// Controllare se c'e' gia' soggetto fisico, se no inserirlo
			int idRelazione = DatabaseUtils.getNextSeq(db,context,"apicoltura_rel_impresa_soggetto_fisico","id");

			sql.append("update apicoltura_apiari set data_assegnazione_detentore = ? ,flag_notifica_variazione_detentore=false,id_soggetto_fisico = ?,modified=current_timestamp,modified_by=? where id =?; INSERT INTO apicoltura_apiari_variazioni_detentore (");



			sql.append("id,id_soggetto_fisico_nuovo_detentore  , id_apicoltura_imprese , id_apicoltura_apiario,entered_by,data_assegnazione_detentore ) values (");

			idVariazioneEseguita = DatabaseUtils.getNextSeq(db, context,"apicoltura_apiari_variazioni_detentore", "id");

			sql.append("?,?,?,?,?,?");
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setTimestamp(++i, this.getData_assegnazione_detentore());
			pst.setInt(++i, this.getDetentore().getIdSoggetto());
			pst.setInt(++i, utente.getUserId());
			pst.setInt(++i, idStabilimento);


			pst.setInt(++i, idVariazioneEseguita);
			pst.setInt(++i, this.getDetentore().getIdSoggetto());
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, this.getIdStabilimento());
			pst.setInt(++i, utente.getUserId());
			pst.setTimestamp(++i, this.getData_assegnazione_detentore());




			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false ;
		} finally {

		}

		return true;

	}






	public int nuovoCensimento(Connection db,ActionContext context)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			UserBean utente = (UserBean)context.getSession().getAttribute("User");
			// Controllare se c'e' gia' soggetto fisico, se no inserirlo


			sql.append("INSERT INTO apicoltura_consistenza (id,id_apicoltura_imprese , id_apicoltura_apiario,data_censimento,num_sciami,num_alveari,entered_by,data_assegnazione_censimento,num_pacchi,num_regine  ) values (");
			sql.append("?,?,?,current_timestamp,?,?,?,?,?,?)");

			idVariazioneEseguita = DatabaseUtils.getNextSeq(db,context,"apicoltura_consistenza","id");



			int i = 0;
			PreparedStatement  pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idVariazioneEseguita);
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, idStabilimento);
			pst.setInt(++i,numSciami);
			pst.setInt(++i,numAlveari);
			pst.setInt(++i,utente.getUserId());
			pst.setTimestamp(++i, data_assegnazione_censimento);
			pst.setInt(++i,numPacchi);
			pst.setInt(++i,numRegine);

			pst.execute();

			sql = new StringBuffer();
			sql.append("update apicoltura_apiari set flag_notifica_variazione_censimento=false,num_alveari_effettivi=0,num_alveari = ?,num_sciami=?,modified=current_timestamp,modified_by=?,id_apicoltura_apiari_variazioni_censimenti=?,data_assegnazione_censimento=?,num_pacchi=?,num_regine=? where id =?;");

			i = 0;
			pst = db.prepareStatement(sql.toString());

			pst.setInt(++i,numAlveari);
			pst.setInt(++i,numSciami);
			pst.setInt(++i,utente.getUserId());
			pst.setInt(++i, idVariazioneEseguita);
			pst.setTimestamp(++i, data_assegnazione_censimento);
			pst.setInt(++i,numPacchi);
			pst.setInt(++i,numRegine);

			pst.setInt(++i, idStabilimento);







			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return -1 ;
		} finally {

		}

		return idVariazioneEseguita;

	}
	
	
	
	public boolean modificaCensimento( Connection db,ActionContext context, StabilimentoVariazioneCensimento censimento)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			UserBean utente = (UserBean)context.getSession().getAttribute("User");
			// Controllare se c'e' gia' soggetto fisico, se no inserirlo


			sql.append("update apicoltura_consistenza set num_sciami = ?,num_alveari = ?,data_assegnazione_censimento = ?,num_pacchi = ?,num_regine = ? where id = ? ");

			idVariazioneEseguita = censimento.getId();

			int i = 0;
			PreparedStatement  pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, censimento.getNumSciami());
			pst.setInt(++i, censimento.getNumAlveari());
			pst.setTimestamp(++i, censimento.getDataCensimento());
			pst.setInt(++i,censimento.getNumPacchi());
			pst.setInt(++i,censimento.getNumRegine());
			pst.setInt(++i,censimento.getId());

			pst.execute();

			sql = new StringBuffer();
			sql.append("update apicoltura_apiari set flag_notifica_variazione_censimento=false,num_alveari_effettivi=0,num_alveari = ?,num_sciami=?,modified=current_timestamp,modified_by=?,id_apicoltura_apiari_variazioni_censimenti=?,data_assegnazione_censimento=?,num_pacchi=?,num_regine=? where id =?;");

			i = 0;
			pst = db.prepareStatement(sql.toString());

			pst.setInt(++i,censimento.getNumAlveari());
			pst.setInt(++i, censimento.getNumSciami());
			pst.setInt(++i,utente.getUserId());
			pst.setInt(++i, idVariazioneEseguita);
			pst.setTimestamp(++i, censimento.getDataCensimento());
			pst.setInt(++i,censimento.getNumPacchi());
			pst.setInt(++i,censimento.getNumRegine());

			pst.setInt(++i, idStabilimento);

			pst.execute();
			pst.close();

			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false ;
		} finally {

		}

		return true;

	}
	
	
	
	
	public boolean deleteCensimento( Connection db,ActionContext context, StabilimentoVariazioneCensimento censimento)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			UserBean utente = (UserBean)context.getSession().getAttribute("User");
			sql.append("update apicoltura_consistenza set trashed_by = ?, trashed_date = now(), note = ? where id = ? ");
			idVariazioneEseguita = censimento.getId();
			
			int i = 0;
			PreparedStatement  pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, utente.getUserId());
			pst.setString(++i, censimento.getNote());
			pst.setInt(++i,censimento.getId());
			pst.execute();

			//Recupero vecchi dati censimenti per aggiornare apicoltura
			int idCensimento = 0;
			int numSciami = 0;
			int numAlveari = 0;
			int numPacchi = 0;
			int numRegine = 0;
			Timestamp dataCensimento = null;
			sql = new StringBuffer();
			sql.append("select * from apicoltura_apiari_variazioni_censimenti where trashed_by is null and id_apicoltura_apiario = ? order by data_censimento limit 1 ");

			i = 0;
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idStabilimento);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) 
			{
				numAlveari=rs.getInt("num_alveari");
				numSciami=rs.getInt("num_sciami");
				numPacchi=rs.getInt("num_pacchi");
				numRegine=rs.getInt("num_regine");
				idCensimento=rs.getInt("id");
				dataCensimento=rs.getTimestamp("data_assegnazione_censimento");
				if(dataCensimento==null)
					dataCensimento=rs.getTimestamp("data_censimento");
			}
			
			//Aggiorno attività di apicoltura
			sql.append("update apicoltura_consistenza set flag_notifica_variazione_censimento=false,modified=current_timestamp,modified_by=?, num_sciami = ?,num_alveari = ?,data_assegnazione_censimento = ?,num_pacchi = ?,num_regine = ? where id = ? ");

			idVariazioneEseguita = censimento.getId();

			i = 0;
			pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, utente.getUserId());
			pst.setInt(++i, numSciami);
			pst.setInt(++i, numAlveari);
			pst.setTimestamp(++i, dataCensimento);
			pst.setInt(++i,numPacchi);
			pst.setInt(++i,numRegine);
			pst.setInt(++i,idCensimento);
			pst.execute();
			pst.close();

		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false ;
		} finally {

		}

		return true;

	}

	public boolean cambioUbicazione(Connection db,ActionContext context,int idIndirizzoOld,boolean flagNomadismo)
			throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			java.util.Date date = new java.util.Date();

			UserBean utente = (UserBean)context.getSession().getAttribute("User");			
			// Controllare se c'e' gia' soggetto fisico, se no inserirlo

			sql.append("update apicoltura_apiari set id_asl=?,flag_nomadismo=?,flag_notifica_variazione_ubicazione=false,id_indirizzo = ?,modified=current_timestamp,modified_by=?,data_assegnazione_ubicazione=? where id =?;"
					+ "update apicoltura_apiari_variazioni_ubicazione set data_fine = current_timestamp where id_apicoltura_apiario = ? and id_opu_indirizzo=? ;"
					+ "INSERT INTO apicoltura_apiari_variazioni_ubicazione (");



			sql.append("id,id_apicoltura_imprese , id_apicoltura_apiario,id_opu_indirizzo,data_inizio,entered_by,data_assegnazione_ubicazione,flag_nomadismo ) values (");



			sql.append("?,?,?,?,current_timestamp,?,?,?)");

			idVariazioneEseguita = DatabaseUtils.getNextSeq(db,context,"apicoltura_apiari_variazioni_ubicazione","id");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, this.getSedeOperativa().getIdAsl());

			pst.setBoolean(++i, flagNomadismo);
			pst.setInt(++i, this.getSedeOperativa().getIdIndirizzo());
			pst.setInt(++i, utente.getUserId());
			pst.setTimestamp(++i, data_assegnazione_ubicazione);
			pst.setInt(++i, idStabilimento);

			pst.setInt(++i, idStabilimento);
			pst.setInt(++i, idIndirizzoOld);

			pst.setInt(++i, idVariazioneEseguita);
			pst.setInt(++i, this.getIdOperatore());
			pst.setInt(++i, idStabilimento);
			pst.setInt(++i, this.getSedeOperativa().getIdIndirizzo());
			pst.setInt(++i, utente.getUserId());
			pst.setTimestamp(++i, data_assegnazione_ubicazione);
			pst.setBoolean(++i, flagNomadismo);


			pst.execute();
			pst.close();



			/*
			 * Aggiornamento dati soggetto fisico se cambiano tutti i dati
			 * tranne quelli per il calcolo del codice fiscale
			 */

			/**
			 * Commentato da Veronica perche' dalla maschera di inserimento non
			 * si possono modificare in ogni caso i dati del soggetto
			 */
			// this.getRappLegale().update(db);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false ;

		} finally {

		}

		return true;

	}



	public String getContainer()
	{
		String container = "apiari" ;
		return container ; 
	} 


	public String toString()
	{

		JSONArray array = new JSONArray(); 
		HashMap<String, Object> obj = new HashMap<String, Object>();

		String txt = "";

		String ret = "" ;
		ret += "{_id_:_"+idStabilimento+"_," ;
		obj.put("_id_", idStabilimento);

		ret += "_idIndirizzo_:_"+this.getSedeOperativa().getIdIndirizzo()+"_," ;



		ret+="_detentore_:"+detentore.toString()+"}";



		return ret ;

	}





	public void errSincronizzaBdn(Connection db,String error) throws SQLException
	{
		String sql="update apicoltura_apiari set  esito_import = 'KO',descrizione_errore = ? where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, error);
		pst.setInt(2, this.getIdStabilimento());
		pst.execute();
	}


	public void sincronizzaBdn(Connection db,int modifiedby) throws SQLException
	{
		String sql="update apicoltura_apiari set esito_import='OK',descrizione_errore=null, sincronizzato_bdn = true , sincronizzato_da = ?,data_sincronizzazione = current_timestamp,stato="+StabilimentoAction.API_STATO_VALIDATO+" where id = ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, modifiedby);
		pst.setInt(2, this.getIdStabilimento());
		pst.execute();
	}



	public void getPrefissoAction(String actionName) {
		// TODO Auto-generated method stub
		actionName="ApicolturaApiari";
	}


	public void deleteRichiesta(Connection db,int modifiedby) throws SQLException
	{
		String sql = "update apicoltura_apiari set trashed_date = current_timestamp , modified_by = ? where id = ?";

		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1,modifiedby );
		pst.setInt(2, this.getIdStabilimento());
		pst.execute();
	}


	public void aggiornaDatiApiario(Connection db) throws SQLException
	{
		int i = 0 ;
		PreparedStatement pst = null;
		pst =db.prepareStatement("update apicoltura_apiari set data_inizio = ? , num_alveari=?,num_sciami=?,id_apicoltura_lookup_sottospecie=?,id_apicoltura_lookup_modalita=?,id_apicoltura_lookup_classificazione=? where id =?");
		pst.setTimestamp(++i, dataApertura);
		pst.setInt(++i, numAlveari);
		pst.setInt(++i, numSciami);
		pst.setInt(++i, idApicolturaSottospecie);
		pst.setInt(++i, idApicolturaModalita);
		pst.setInt(++i, idApicolturaClassificazione);
		pst.setInt(++i, idStabilimento);
		pst.execute();

		this.getSedeOperativa().updateCoordinate(db);

	}


	public void aggiornaCoordinate(Connection db) throws SQLException
	{


		this.getSedeOperativa().updateCoordinate(db);

	}

}
