/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.ImReal;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.postgresql.util.PSQLException;

import ext.aspcfs.modules.apicolture.actions.StabilimentoAction;

public class StabilimentoListEstrazione extends Vector implements SyncableList {

	public static final int TIPOLOGIA_APIARIO = 1 ;
	public static final int TIPOLOGIA_DESTINAZIONE = 3 ;
	
	
	
	private static Logger log = Logger.getLogger(org.aspcfs.modules.opu.base.StabilimentoList.class);
	protected PagedListInfo pagedListInfo = null;
	private int idOperatore ;
	private int idStabilimento ; 
	private int stato ;
	private int idAsl ;
	private int idAslApiario ;
	private Boolean flag_notifica_variazione_ubicazione ;
	private Boolean flag_notifica_variazione_censimento ;
	private Boolean flag_notifica_variazione_detentore ;
	private String cun ;
	private String ragioneSociale ;
	private String cfProprietario;
	private int statoApiario ;
	private int statoPregresso ;
	private String comune ;
	private int idApiarioScelto ;
	private int progressivoApiario ;
	private int progressivoApiarioFiltro ;
	private Boolean flagLaboratorio =null;
	private int inRegione ;
	private boolean apicoltore ;
	private boolean delegato ;
	private String codice_fiscale_impresa ;
	private int id_utente_access_ext_delegato; 
	
	
	
	
	public String getcodice_fiscale_impresa() {
		return codice_fiscale_impresa;
	}

	public void setcodice_fiscale_impresa(String codice_fiscale_impresa) {
		this.codice_fiscale_impresa = codice_fiscale_impresa;
	}
	
	public int getInRegione() {
		return inRegione;
	}

	public void setInRegione(int inRegione) {
		this.inRegione = inRegione;
	}
	
	public int getid_utente_access_ext_delegato() {
		return id_utente_access_ext_delegato;
	}

	public void setid_utente_access_ext_delegato(int id_utente_access_ext_delegato) {
		this.id_utente_access_ext_delegato = id_utente_access_ext_delegato;
	}
	
	public boolean getApicoltore() {
		return apicoltore;
	}

	public void setDelegato(boolean delegato) {
		this.delegato = delegato;
	}
	
	public boolean getDelegato() {
		return delegato;
	}

	public void setApicoltore(boolean apicoltore) {
		this.apicoltore = apicoltore;
	}
	
	public void setInRegione(String inRegione) {
		if (inRegione!=null && !"".equals(inRegione))
		this.inRegione = Integer.parseInt(inRegione);
	}

	public Stabilimento getLaboratorio()
	{
		if (this.size()>0)
		{
			Iterator<Stabilimento> itStab = this.iterator();
			while (itStab.hasNext())
			{
				Stabilimento stab = itStab.next();
				if (stab.isFlagLaboratorio())
					return stab;
			}
		}
		return null ;
	}
	
	public boolean isFlagLaboratorio() {
		return flagLaboratorio;
	}

	public void setFlagLaboratorio(boolean flagLaboratorio) {
		this.flagLaboratorio = flagLaboratorio;
	}

	public int getProgressivoApiario() {
		return progressivoApiario;
	}

	public void setProgressivoApiario(int progressivoApiario) {
		this.progressivoApiario = progressivoApiario;
	}
	
	public void setProgressivoApiario(String progressivoApiario) {
		
		if(progressivoApiario!=null && !"".equals(progressivoApiario))
			this.progressivoApiario = Integer.parseInt(progressivoApiario);
	}
	
	
	

	public int getProgressivoApiarioFiltro() {
		return progressivoApiarioFiltro;
	}

	public void setProgressivoApiarioFiltro(int progressivoApiarioFiltro) {
		this.progressivoApiarioFiltro = progressivoApiarioFiltro;
	}

	
public void setProgressivoApiarioFiltro(String progressivoApiarioFiltro) {
		
		if(progressivoApiarioFiltro!=null && !"".equals(progressivoApiarioFiltro))
			this.progressivoApiarioFiltro = Integer.parseInt(progressivoApiarioFiltro);
	}

	public int getIdApiarioScelto() {
		return idApiarioScelto;
	}

	public void setIdApiarioScelto(int idApiarioScelto) {
		this.idApiarioScelto = idApiarioScelto;
	}
	
	public void setIdApiarioScelto(String idApiarioScelto) {
		if (idApiarioScelto!=null && !"".equals(idApiarioScelto))
			this.idApiarioScelto = Integer.parseInt(idApiarioScelto);
	}


	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public int getStatoPregresso() {
		return statoPregresso;
	}

	public void setStatoPregresso(int statoPregresso) {
		this.statoPregresso = statoPregresso;
	}

	public int getStatoApiario() {
		return statoApiario;
	}

	public void setStatoApiario(int statoApiario) {
		this.statoApiario = statoApiario;
	}

	public String getCun() {
		return cun;
	}

	public void setCun(String cun) {
		this.cun = cun;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCfProprietario() {
		return cfProprietario;
	}

	public void setCfProprietario(String cfProprietario) {
		this.cfProprietario = cfProprietario;
	}

	public boolean isFlag_notifica_variazione_ubicazione() {
		return flag_notifica_variazione_ubicazione;
	}

	public void setFlag_notifica_variazione_ubicazione(boolean flag_notifica_variazione_ubicazione) {
		this.flag_notifica_variazione_ubicazione = flag_notifica_variazione_ubicazione;
	}

	public boolean isFlag_notifica_variazione_censimento() {
		return flag_notifica_variazione_censimento;
	}

	public void setFlag_notifica_variazione_censimento(boolean flag_notifica_variazione_censimento) {
		this.flag_notifica_variazione_censimento = flag_notifica_variazione_censimento;
	}

	public boolean isFlag_notifica_variazione_detentore() {
		return flag_notifica_variazione_detentore;
	}

	public void setFlag_notifica_variazione_detentore(boolean flag_notifica_variazione_detentore) {
		this.flag_notifica_variazione_detentore = flag_notifica_variazione_detentore;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public void setIdAslApiario(String idAslApiario) {
		if (idAslApiario!= null && !"".equals(idAslApiario))
		this.idAslApiario = Integer.parseInt(idAslApiario);
	}

	public int getIdAslApiario() {
		return idAslApiario;
	}

	public void setIdAslApiario(int idAslApiario) {
		this.idAslApiario = idAslApiario;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public int getIdStabilimento() {
		return idStabilimento;
	}

	public void setIdStabilimento(int idStabilimento) {
		this.idStabilimento = idStabilimento;
	}
	
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}

	public int getIdOperatore() {
		return idOperatore;
	}

	
	
	public void setIdOperatore(int idOperatore) {
		this.idOperatore = idOperatore;
	}
	
	

	

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLastAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	public void setLastAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setNextAnchor(Timestamp tmp) {
		// TODO Auto-generated method stub

	}

	public void setNextAnchor(String tmp) {
		// TODO Auto-generated method stub

	}

	public void setSyncType(int tmp) {
		// TODO Auto-generated method stub

	}
	
	

	
	
	
	
	
    public ResultSet queryList(final Connection connection, PreparedStatement preparedStatement) throws SQLException {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        final StringBuffer sb3 = new StringBuffer();
        final StringBuffer sb4 = new StringBuffer();
        sb2.append(" select distinct on (t2.id_apicoltore) t2.id_apicoltore, * FROM( SELECT id_apicoltore, count(id_apicoltore) as num_tot_apiari, sum(num_alveari) as num_tot_alveari, sum(num_sciami) as num_tot_sciami  FROM public_functions.dbi_get_apicoltura_lista_apiari()  where  (codice_fiscale_impresa = ? or ? is null) and         (id_utente_access_ext_delegato = ? or ? <0) and        (codice_fiscale_delegato = ? or ? is null)  group by id_apicoltore) t, public_functions.dbi_get_apicoltura_lista_apiari() t2  WHERE t.id_apicoltore = t2.id_apicoltore ");
        this.createFilter(connection, sb3);
        if (this.pagedListInfo != null) {
            preparedStatement = connection.prepareStatement(sb2.toString() + sb3.toString());
            this.prepareFilter(preparedStatement);
            final ResultSet executeQuery = preparedStatement.executeQuery();
            if (executeQuery.next()) {
                this.pagedListInfo.setMaxRecords(executeQuery.getInt("recordcount"));
            }
            executeQuery.close();
            preparedStatement.close();
            if (!this.pagedListInfo.getCurrentLetter().equals("")) {
                preparedStatement = connection.prepareStatement(sb2.toString() + sb3.toString());
                int prepareFilter = this.prepareFilter(preparedStatement);
                preparedStatement.setString(++prepareFilter, this.pagedListInfo.getCurrentLetter().toLowerCase());
                final ResultSet executeQuery2 = preparedStatement.executeQuery();
                if (executeQuery2.next()) {
                    this.pagedListInfo.setCurrentOffset(executeQuery2.getInt("recordcount"));
                }
                executeQuery2.close();
                preparedStatement.close();
            }
            this.pagedListInfo.appendSqlTail(connection, sb4);
        }
        else {
            sb4.append("");
        }
        sb.append("select distinct on (t2.id_apicoltore) t2.id_apicoltore, * FROM( SELECT id_apicoltore, count(id_apicoltore) as num_tot_apiari, sum(num_alveari) as num_tot_alveari, sum(num_sciami) as num_tot_sciami  FROM public_functions.dbi_get_apicoltura_lista_apiari()  where  (codice_fiscale_impresa = ? or ? is null) and         (id_utente_access_ext_delegato = ? or ? <0) and        (codice_fiscale_delegato = ? or ? is null)  group by id_apicoltore) t, public_functions.dbi_get_apicoltura_lista_apiari() t2  WHERE t.id_apicoltore = t2.id_apicoltore ");
        preparedStatement = connection.prepareStatement(sb.toString() + sb3.toString() + sb4.toString());
        this.prepareFilter(preparedStatement);
        final ResultSet executeQuery3 = preparedStatement.executeQuery();
        if (this.pagedListInfo != null) {
            this.pagedListInfo.doManualOffset(connection, executeQuery3);
        }
        return executeQuery3;
    }
    
    public ResultSet queryStabilimento(final Connection connection, PreparedStatement prepareStatement, final int n) throws SQLException {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        final StringBuffer sb3 = new StringBuffer();
        final StringBuffer sb4 = new StringBuffer();
        sb.append("select s.* from apicoltura_apiari where 1=1 and id =? ");
        prepareStatement = connection.prepareStatement(sb.toString());
        prepareStatement.setInt(1, n);
        return prepareStatement.executeQuery();
    }
    
    protected void createFilter(final Connection connection, StringBuffer sb) {
        if (sb == null) {
            sb = new StringBuffer(" where ");
        }
    }
    
    protected int prepareFilter(final PreparedStatement preparedStatement) throws SQLException {
        final int n = 0;
        if (this.apicoltore) {
            preparedStatement.setString(1, this.codice_fiscale_impresa);
            preparedStatement.setString(2, this.codice_fiscale_impresa);
            preparedStatement.setInt(3, -1);
            preparedStatement.setInt(4, -1);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, null);
        }
        if (this.delegato) {
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, null);
            preparedStatement.setInt(3, this.id_utente_access_ext_delegato);
            preparedStatement.setInt(4, this.id_utente_access_ext_delegato);
            preparedStatement.setString(5, this.codice_fiscale_impresa);
            preparedStatement.setString(6, this.codice_fiscale_impresa);
        }
        return n;
    }
    
    public void buildList(final Connection connection) throws SQLException, IndirizzoNotFoundException {
        final PreparedStatement preparedStatement = null;
        final ResultSet queryList = this.queryList(connection, preparedStatement);
        while (queryList.next()) {
            this.add(this.getObject(queryList));
        }
        queryList.close();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }
    
    public StabilimentoEstrazione getObject(final ResultSet set) throws SQLException {
        final StabilimentoEstrazione stabilimentoEstrazione = new StabilimentoEstrazione();
        stabilimentoEstrazione.setNumAlveari(set.getInt("num_tot_alveari"));
        stabilimentoEstrazione.setNumSciami(set.getInt("num_tot_sciami"));
        stabilimentoEstrazione.setDataSincronizzazione(set.getTimestamp("apicoltore_data_registrazione_bdn"));
        stabilimentoEstrazione.setEntered(set.getTimestamp("apicoltore_data_registrazione_bdar"));
        stabilimentoEstrazione.setIdAsl(set.getInt("id_asl"));
        stabilimentoEstrazione.setNumApiari(set.getInt("num_tot_apiari"));
        stabilimentoEstrazione.setComuneSedeLegale(set.getString("comune_sede_legale"));
        stabilimentoEstrazione.setIndirizzoSedeLegale(set.getString("via_sede_legale"));
        final Operatore operatore = new Operatore();
        operatore.setCodiceAzienda(set.getString("codice_azienda"));
        operatore.setRagioneSociale(set.getString("ragione_sociale"));
        operatore.setcodice_fiscale_impresa(set.getString("codice_fiscale_impresa"));
        stabilimentoEstrazione.setOperatore(operatore);
        return stabilimentoEstrazione;
    }
    
    public void setSyncType(final String s) {
    }
    
    static {
        StabilimentoListEstrazione.log = Logger.getLogger((Class)StabilimentoList.class);
    }
}