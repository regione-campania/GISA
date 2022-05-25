/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.SuperUtenteAll;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.ClinicaNoH;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.SuperUtenteDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupAssociazioniDAO;
import it.us.web.dao.lookup.LookupCMIDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoSedeLesioneDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.lookup.LookupSpecieDAO;
import it.us.web.dao.lookup.LookupTipiRichiedenteDAO;
import it.us.web.dao.lookup.LookupTipoTrasferimentoDAO;
import it.us.web.dao.sinantropi.lookup.LookupSinantropiEtaDAO;
import it.us.web.util.DateUtils;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Type;
import org.jmesa.facade.TableFacade;
import org.jmesa.limit.RowSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrasferimentoDAO extends GenericDAO {
	public static final Logger logger = LoggerFactory.getLogger(TrasferimentoDAO.class);

	public static ArrayList<TrasferimentoNoH> getTraferimentiAll(Connection connection, RowSelect rowSelect, String filtroMc) {
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<TrasferimentoNoH> trasferimenti =  new ArrayList<TrasferimentoNoH>();
		try {
			stat = connection.prepareStatement(
					" select * from trasferimento t " +  
			        " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null " +  
					" left join accettazione acc on acc.id = cc.accettazione and acc.trashed_date is null " +  
			        " left join animale an on an.id = acc.animale and an.trashed_date is null " +
					" where t.trashed_date is null "  + filtroMc + 
					" order by t.entered desc limit " + rowSelect.getMaxRows() + " offset " + rowSelect.getRowStart() 
			);
			
			rs = stat.executeQuery();

			while (rs.next()) 
			{
				TrasferimentoNoH tr = new TrasferimentoNoH();
				tr.setId(rs.getInt("id"));	  
				tr.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));	 
				tr.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));	 
				tr.setDataRichiesta(rs.getDate("data_richiesta"));	 
				tr.setDataRiconsegna(rs.getDate("data_riconsegna"));	
				tr.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));	
				tr.setEntered(rs.getDate("entered"));	
				tr.setModified(rs.getDate("modified"));  
				tr.setNotaCriuv(rs.getString("nota_criuv"));	  
				tr.setNotaDestinatario(rs.getString("nota_destinatario"));	
				tr.setNotaDestinatario(rs.getString("nota_richiesta"));	
				tr.setNotaRiconsegna(rs.getString("nota_riconsegna"));	
				tr.setUrgenza(rs.getBoolean("urgenza"));	
				tr.setCartellaClinica(getCcAnimale(connection, rs.getInt("cartella_clinica")));	
				tr.setClinicaDestinazione(getClinica(rs.getInt("clinica_destinazione"),connection));	
				tr.setClinicaOrigine(getClinica(rs.getInt("clinica_origine"),connection));	  
				tr.setDataApprovazioneRiconsegna(rs.getDate("data_approvazione_riconsegna"));	  
				tr.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));	
				tr.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));	  
				tr.setCartellaClinicaDestinatario(getCc(connection, rs.getInt("cartella_clinica_destinatario")));	
				tr.setCartellaClinicaMittenteRiconsegna(getCc(connection, rs.getInt("cartella_clinica_mittente_riconsegna")));	 
				tr.setCartellaClinicaMortoDestinatario(getCc(connection, rs.getInt("cartella_clinica_morto_destinatario")));	
				tr.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));	 
				tr.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
				trasferimenti.add(tr);
			}

		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			close(rs, stat, null);
		}

		return trasferimenti;
	}
	
	
	public static Set<Trasferimento> getTrasferimenti(String tipo, int idClinica, Integer stato, Connection connection) throws SQLException
	{
		Set<Trasferimento> trasferimenti = new HashSet<Trasferimento>();

		String campoFiltroClinica = "";
		if(tipo.equals("I"))
			campoFiltroClinica = " clinica_destinazione ";
		else
			campoFiltroClinica = " clinica_origine ";

		String sql = " select * "
				+  " from trasferimento t "
				+  " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null "
				+  " where t.trashed_date is null and "
				+  " t." +campoFiltroClinica + " = ? order by t.data_richiesta desc, t.id desc";


		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			Trasferimento tr = new Trasferimento();
			if(stato==null || tr.getStato().getStato()==stato)
			{
				
					
				tr.setId(rs.getInt("id"));	  
				tr.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));	 
				tr.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));	 
				tr.setDataRichiesta(rs.getDate("data_richiesta"));	 
				tr.setDataRiconsegna(rs.getDate("data_riconsegna"));	
				tr.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));	
				tr.setEntered(rs.getDate("entered"));	
				tr.setModified(rs.getDate("modified"));  
				tr.setNotaCriuv(rs.getString("nota_criuv"));	  
				tr.setNotaDestinatario(rs.getString("nota_destinatario"));	
				tr.setNotaDestinatario(rs.getString("nota_richiesta"));	
				tr.setNotaRiconsegna(rs.getString("nota_riconsegna"));	
				tr.setUrgenza(rs.getBoolean("urgenza"));	
				tr.setCartellaClinica(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica")));	  
				tr.setClinicaDestinazione(ClinicaDAO.getClinica(rs.getInt("clinica_destinazione"),connection));	
				tr.setClinicaOrigine(ClinicaDAO.getClinica(rs.getInt("clinica_origine"),connection));	  
				tr.setDataApprovazioneRiconsegna(rs.getDate("data_approvazione_riconsegna"));	  
				tr.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));	
				tr.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));	  
				tr.setCartellaClinicaDestinatario(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_destinatario")));	
				tr.setCartellaClinicaMittenteRiconsegna(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_mittente_riconsegna")));	 
				tr.setCartellaClinicaMortoDestinatario(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_morto_destinatario")));	
				tr.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));	 
				tr.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
				
				if(tipo.equals("I")){
					if(tr.getStato().getStato()!=1 && tr.getStato().getStato()!=6)
						trasferimenti.add(tr);
				}else{
					trasferimenti.add(tr);
				}
			}

		}
		return trasferimenti;
	}
	
	
	
	
	public static ArrayList<Trasferimento> getTrasferimenti(String tipo, int idAnimale, int idClinica, Connection connection) throws SQLException
	{
		ArrayList<Trasferimento> trasferimenti = new ArrayList<Trasferimento>();

		String campoFiltroClinica = "";
		if(tipo.equals("I"))
			campoFiltroClinica = " clinica_destinazione ";
		else
			campoFiltroClinica = " clinica_origine ";

		
		
		String sql = "select * " +
				" from trasferimento t " +
				" join cartella_clinica c on t.cartella_clinica=c.id and c.trashed_date is null " + 
				" join clinica c_dest     on t.clinica_destinazione = c_dest.id and c_dest.trashed_date is null " +    
				" join clinica c_mittente on t.clinica_origine      = c_mittente.id and c_mittente.trashed_date is null " + 
				" join accettazione a on c.accettazione=a.id  " +
				" where (t.urgenza or ( c_dest.asl = c_mittente.asl or t.data_accettazione_criuv is not null)) AND " +
				" t.data_accettazione_destinatario is null AND " +
				" t.trashed_date is null and " +
				" a.animale= ? AND " +
				" t." +campoFiltroClinica + " = ? " +
				" order by t.data_richiesta desc, t.id desc ";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAnimale);
		st.setInt(2, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			Trasferimento tr = new Trasferimento();
			tr.setId(rs.getInt("id"));	  
			tr.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));	 
			tr.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));	 
			tr.setDataRichiesta(rs.getDate("data_richiesta"));	 
			tr.setDataRiconsegna(rs.getDate("data_riconsegna"));	
			tr.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));	
			tr.setEntered(rs.getDate("entered"));	
			tr.setModified(rs.getDate("modified"));  
			tr.setNotaCriuv(rs.getString("nota_criuv"));	  
			tr.setNotaDestinatario(rs.getString("nota_destinatario"));	
			tr.setNotaDestinatario(rs.getString("nota_richiesta"));	
			tr.setNotaRiconsegna(rs.getString("nota_riconsegna"));	
			tr.setUrgenza(rs.getBoolean("urgenza"));	
			tr.setCartellaClinica(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica")));	  
			tr.setClinicaDestinazione(ClinicaDAO.getClinica(rs.getInt("clinica_destinazione"),connection));	
			tr.setClinicaOrigine(ClinicaDAO.getClinica(rs.getInt("clinica_origine"),connection));	  
			tr.setDataApprovazioneRiconsegna(rs.getDate("data_approvazione_riconsegna"));	  
			tr.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));	
			tr.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));	  
			tr.setCartellaClinicaDestinatario(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_destinatario")));	
			tr.setCartellaClinicaMittenteRiconsegna(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_mittente_riconsegna")));	 
			tr.setCartellaClinicaMortoDestinatario(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_morto_destinatario")));	
			tr.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));	 
			tr.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
				
			if(tipo.equals("I"))
			{
				if(tr.getStato().getStato()!=1 && tr.getStato().getStato()!=6)
					trasferimenti.add(tr);
			}
			else
			{
				trasferimenti.add(tr);
			}

		}
		return trasferimenti;
	}
	
	
	public static ArrayList<Trasferimento> getTrasferimentiAccettati(String tipo, int idAnimale, int idClinica, Connection connection) throws SQLException
	{
		ArrayList<Trasferimento> trasferimenti = new ArrayList<Trasferimento>();

		String campoFiltroClinica = "";
		if(tipo.equals("I"))
			campoFiltroClinica = " clinica_destinazione ";
		else
			campoFiltroClinica = " clinica_origine ";

		
		
		String sql = "select * " +
				" from trasferimento t " +
				" join cartella_clinica c on t.cartella_clinica=c.id and c.trashed_date is null " + 
				" join clinica c_dest     on t.clinica_destinazione = c_dest.id and c_dest.trashed_date is null " +    
				" join clinica c_mittente on t.clinica_origine      = c_mittente.id and c_mittente.trashed_date is null " + 
				" join accettazione a on c.accettazione=a.id  " +
				" where (t.urgenza or ( c_dest.asl = c_mittente.asl or t.data_accettazione_criuv is not null)) AND " +
				" t.data_accettazione_destinatario is not null AND " +
				" t.trashed_date is null and " +
				" a.animale= ? AND " +
				" t." +campoFiltroClinica + " = ? " +
				" order by t.data_richiesta desc, t.id desc ";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAnimale);
		st.setInt(2, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			Trasferimento tr = new Trasferimento();
			tr.setId(rs.getInt("id"));	  
			tr.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));	 
			tr.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));	 
			tr.setDataRichiesta(rs.getDate("data_richiesta"));	 
			tr.setDataRiconsegna(rs.getDate("data_riconsegna"));	
			tr.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));	
			tr.setEntered(rs.getDate("entered"));	
			tr.setModified(rs.getDate("modified"));  
			tr.setNotaCriuv(rs.getString("nota_criuv"));	  
			tr.setNotaDestinatario(rs.getString("nota_destinatario"));	
			tr.setNotaDestinatario(rs.getString("nota_richiesta"));	
			tr.setNotaRiconsegna(rs.getString("nota_riconsegna"));	
			tr.setUrgenza(rs.getBoolean("urgenza"));	
			tr.setCartellaClinica(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica")));	  
			tr.setClinicaDestinazione(ClinicaDAO.getClinica(rs.getInt("clinica_destinazione"),connection));	
			tr.setClinicaOrigine(ClinicaDAO.getClinica(rs.getInt("clinica_origine"),connection));	  
			tr.setDataApprovazioneRiconsegna(rs.getDate("data_approvazione_riconsegna"));	  
			tr.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));	
			tr.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));	  
			tr.setCartellaClinicaDestinatario(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_destinatario")));	
			tr.setCartellaClinicaMittenteRiconsegna(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_mittente_riconsegna")));	 
			tr.setCartellaClinicaMortoDestinatario(CartellaClinicaDAO.getCc(connection, rs.getInt("cartella_clinica_morto_destinatario")));	
			tr.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));	 
			tr.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
				
			if(tipo.equals("I"))
			{
				if(tr.getStato().getStato()!=1 && tr.getStato().getStato()!=6)
					trasferimenti.add(tr);
			}
			else
			{
				trasferimenti.add(tr);
			}

		}
		return trasferimenti;
	}
	
	
	public static boolean cartellaClinicaMortoDestinatarioEsiste(Connection connection, int idTrasf) throws SQLException
	{
		String sql = " select t.cartella_clinica_morto_destinatario from trasferimento t where t.trashed_date is null and t.id = ? ";

		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idTrasf);
		ResultSet rs = st.executeQuery();

		if(rs.next() && rs.getInt("cartella_clinica_morto_destinatario")>0)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	public static Set<TrasferimentoNoH> getTrasferimenti(String tipo, Clinica clinica, Integer stato, Connection connection, RowSelect rowSelect, String filtroMc) throws SQLException
	{
		Set<TrasferimentoNoH> trasferimenti = new HashSet<TrasferimentoNoH>();

		String campoFiltroClinica = "";
		if(tipo.equals("I"))
			campoFiltroClinica = " clinica_destinazione ";
		else
			campoFiltroClinica = " clinica_origine ";

		
		String sql = " select *, u.id as id_utente, u.nome as nome, u.cognome as cognome "
				+  " from trasferimento t "
				+  " left join utenti_ u on u.id  = t.entered_by "
				+  " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null "
				+  " left join accettazione acc on acc.id = cc.accettazione and acc.trashed_date is null "
				+  " left join animale an on an.id = acc.animale and an.trashed_date is null "
				+  " where t.trashed_date is null  " + filtroMc
				+  " and t." +campoFiltroClinica + " = ? " 
				+  " order by t.data_richiesta desc, t.id desc "
				+  " limit " + rowSelect.getMaxRows() + " offset " + rowSelect.getRowStart() ;


		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, clinica.getId());
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			TrasferimentoNoH tr = new TrasferimentoNoH();
			if(stato==null || tr.getStato().getStato()==stato)
			{
				
				tr.setId(rs.getInt("id"));	  
				tr.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));	 
				tr.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));	 
				tr.setDataRichiesta(rs.getDate("data_richiesta"));	 
				tr.setDataRiconsegna(rs.getDate("data_riconsegna"));	
				tr.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));	
				tr.setEntered(rs.getDate("entered"));	
				tr.setModified(rs.getDate("modified"));  
				tr.setNotaCriuv(rs.getString("nota_criuv"));	  
				tr.setNotaDestinatario(rs.getString("nota_destinatario"));	
				tr.setNotaDestinatario(rs.getString("nota_richiesta"));	
				tr.setNotaRiconsegna(rs.getString("nota_riconsegna"));	
				tr.setUrgenza(rs.getBoolean("urgenza"));	
				tr.setCartellaClinica(getCcAnimale(connection, rs.getInt("cartella_clinica")));	
				
				BUtente u = new BUtente();
				u.setId(rs.getInt("id_utente"));
				u.setNome(rs.getString("nome"));
				u.setCognome(rs.getString("cognome"));
				tr.setEnteredBy(u);
				
				ClinicaNoH clinicaTemp = new ClinicaNoH();
				clinicaTemp.setNome(clinica.getNome());
				clinicaTemp.setAsl(clinica.getLookupAsl().getDescription());
				clinicaTemp.setCanileBdu(clinica.getCanileBdu());
				
				if(tipo.equals("I"))
					tr.setClinicaDestinazione(clinicaTemp);	
				else
					tr.setClinicaDestinazione(getClinica(rs.getInt("clinica_destinazione"),connection));	
				if(tipo.equals("U"))
					tr.setClinicaOrigine(clinicaTemp);	  
				else
					tr.setClinicaOrigine(getClinica(rs.getInt("clinica_origine"),connection));	  
				tr.setDataApprovazioneRiconsegna(rs.getDate("data_approvazione_riconsegna"));	  
				tr.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));	
				tr.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));	  
				tr.setCartellaClinicaDestinatario(getCc(connection, rs.getInt("cartella_clinica_destinatario")));	
				tr.setCartellaClinicaMittenteRiconsegna(getCc(connection, rs.getInt("cartella_clinica_mittente_riconsegna")));	 
				tr.setCartellaClinicaMortoDestinatario(getCc(connection, rs.getInt("cartella_clinica_morto_destinatario")));	
				tr.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));	 
				tr.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
				
				if(tipo.equals("I")){
					if(tr.getStato().getStato()!=1 && tr.getStato().getStato()!=6)
						trasferimenti.add(tr);
				}else{
					trasferimenti.add(tr);
				}
			}

		}
		return trasferimenti;
	}
	
	
	private static CartellaClinicaNoH getCc( Connection connection,int id) throws SQLException
	{
		CartellaClinicaNoH cc = null;
		String sql = " select cc.id, cc.numero "
				   + " from cartella_clinica cc "
				   + " where cc.id = ? and cc.trashed_date is null ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			cc = new CartellaClinicaNoH();
			cc.setNumero(rs.getString("numero"));
			cc.setId(rs.getInt("id"));
		}
		return cc;
	}
	
	private static CartellaClinicaNoH getCcAnimale( Connection connection,int id) throws SQLException
	{
		CartellaClinicaNoH cc = null;
		String sql = " select cc.id, cc.numero, an.identificativo "
				   + " from cartella_clinica cc, accettazione acc, animale an "
				   + " where cc.id = ? and cc.trashed_date is null and  "
				   + "       cc.accettazione = acc.id and "
				   + "       acc.animale = an.id ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			cc = new CartellaClinicaNoH();
			cc.setNumero(rs.getString("numero"));
			cc.setId(rs.getInt("id"));
			cc.setIdentificativoAnimale(rs.getString("identificativo"));
		}
		return cc;
	}
	
	
	public static ClinicaNoH getClinica( int id, Connection connection ) throws SQLException
	{
		ClinicaNoH c = new ClinicaNoH();
		String sql = " select c.nome, asl_rif.description as asl_desc, canile_bdu, data_cessazione " + 
				     " from clinica c, lookup_asl asl_rif " +
				     " where c.trashed_date is null and c.id = ? and c.asl = asl_rif.id " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			c = (ClinicaNoH)Bean.populate(c, rs);
			c.setAsl(rs.getString("asl_desc"));
			c.setNome(rs.getString("nome"));
			c.setDataCessazione(rs.getDate("data_cessazione"));
			c.setCanileBdu(rs.getInt("canile_bdu"));
		}
		return c;
	}
	
	
	
	public static ArrayList<TrasferimentoNoH> getTrasferimentiNoH(String tipo, int idAnimale, int idClinica, Connection connection) throws SQLException
	{
		ArrayList<TrasferimentoNoH> trasferimenti = new ArrayList<TrasferimentoNoH>();

		String campoFiltroClinica = "";
		if(tipo.equals("I"))
			campoFiltroClinica = " clinica_destinazione ";
		else
			campoFiltroClinica = " clinica_origine ";

		
		
		String sql = "select *, u.id as id_utente, u.nome as nome, u.cognome as cognome " +
				" from trasferimento t " +
				" left join utenti_ u on u.id  = t.entered_by " +
				" join cartella_clinica c on t.cartella_clinica=c.id and c.trashed_date is null " + 
				" join clinica c_dest     on t.clinica_destinazione = c_dest.id and c_dest.trashed_date is null " +    
				" join clinica c_mittente on t.clinica_origine      = c_mittente.id and c_mittente.trashed_date is null " + 
				" join accettazione a on c.accettazione=a.id  " +
				" where (t.urgenza or ( c_dest.asl = c_mittente.asl or t.data_accettazione_criuv is not null)) AND " +
				" t.data_accettazione_destinatario is null AND " +
				" t.trashed_date is null and " +
				" a.animale= ? AND " +
				" t." +campoFiltroClinica + " = ? " +
				" order by t.data_richiesta desc, t.id desc ";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAnimale);
		st.setInt(2, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			TrasferimentoNoH tr = new TrasferimentoNoH();
			tr.setId(rs.getInt("id"));	  
			tr.setDataAccettazioneCriuv(rs.getDate("data_accettazione_criuv"));	 
			tr.setDataAccettazioneDestinatario(rs.getDate("data_accettazione_destinatario"));	 
			tr.setDataRichiesta(rs.getDate("data_richiesta"));	 
			tr.setDataRiconsegna(rs.getDate("data_riconsegna"));	
			tr.setDataRifiutoCriuv(rs.getDate("data_rifiuto_criuv"));	
			tr.setEntered(rs.getDate("entered"));	
			tr.setModified(rs.getDate("modified")); 
			tr.setNotaRichiesta(rs.getString("nota_richiesta"));
			tr.setNotaCriuv(rs.getString("nota_criuv"));	  
			tr.setNotaDestinatario(rs.getString("nota_destinatario"));	
			tr.setNotaDestinatario(rs.getString("nota_richiesta"));	
			tr.setNotaRiconsegna(rs.getString("nota_riconsegna"));	
			tr.setUrgenza(rs.getBoolean("urgenza"));	
			tr.setCartellaClinica(CartellaClinicaDAONoH.getCc(connection, rs.getInt("cartella_clinica")));	  
			tr.setClinicaDestinazione(ClinicaDAONoH.getClinica(rs.getInt("clinica_destinazione"),connection));	
			tr.setClinicaOrigine(ClinicaDAONoH.getClinica(rs.getInt("clinica_origine"),connection));	  
			tr.setDataApprovazioneRiconsegna(rs.getDate("data_approvazione_riconsegna"));	  
			tr.setDataRifiutoRiconsegna(rs.getDate("data_rifiuto_riconsegna"));	
			tr.setNotaApprovazioneRiconsegna(rs.getString("nota_approvazione_riconsegna"));	  
			tr.setCartellaClinicaDestinatario(CartellaClinicaDAONoH.getCc(connection, rs.getInt("cartella_clinica_destinatario")));	
			tr.setCartellaClinicaMittenteRiconsegna(CartellaClinicaDAONoH.getCc(connection, rs.getInt("cartella_clinica_mittente_riconsegna")));	 
			tr.setCartellaClinicaMortoDestinatario(CartellaClinicaDAONoH.getCc(connection, rs.getInt("cartella_clinica_morto_destinatario")));	
			tr.setAutomaticoPerNecroscopia(rs.getBoolean("automatico_necroscopia"));	 
			tr.setOperazioniRichieste(LookupOperazioniAccettazioneDAO.getOperazioniByIdTrasferimento(connection,rs.getInt("id")));
			
			BUtente u = new BUtente();
			u.setId(rs.getInt("id_utente"));
			u.setNome(rs.getString("nome"));
			u.setCognome(rs.getString("cognome"));
			tr.setEnteredBy(u);
				
			if(tipo.equals("I"))
			{
				if(tr.getStato().getStato()!=1 && tr.getStato().getStato()!=6)
					trasferimenti.add(tr);
			}
			else
			{
				trasferimenti.add(tr);
			}

		}
		return trasferimenti;
	}

	
	}
