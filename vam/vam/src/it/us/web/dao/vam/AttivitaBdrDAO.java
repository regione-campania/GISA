/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.AttivitaBdrNoH;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Diagnosi;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupPersonaleInterno;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupAssociazioniDAO;
import it.us.web.dao.lookup.LookupCMIDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
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
import java.util.HashSet;
import java.util.Vector;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttivitaBdrDAO extends GenericDAO {
	public static final Logger logger = LoggerFactory.getLogger(AttivitaBdrDAO.class);

	public static HashSet<AttivitaBdr> getAttivitaBdrCompletata( int idTipoOperazione, int idAcc, Connection connection) throws SQLException
	{
		HashSet<AttivitaBdr> atts = new HashSet<AttivitaBdr>();
		String sql = " select op.id, op.inbdr, att.id_registrazione_bdr "
				+ " from attivita_bdr att "
				+ " left join lookup_operazioni_accettazione op on op.id = att.tipo_operazione"
				+ " where att.tipo_operazione = ? and att.accettazione = ? and att.id_registrazione_bdr is not null and trashed_date is null ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idTipoOperazione);
		st.setInt(2, idAcc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			AttivitaBdr att = new AttivitaBdr();
			att.setIdRegistrazioneBdr( rs.getInt("id_registrazione_bdr"));
	
			LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
			op.setId( rs.getInt("id"));
			op.setInbdr( rs.getBoolean("inbdr"));
			att.setOperazioneBdr(op);

			atts.add(att);
		}
		return atts;
	}
	
	public static void insert( AttivitaBdrNoH att, Connection connection) throws SQLException
	{
		String sql = " INSERT INTO attivita_bdr( " +
            " descrizione, entered, entered_by, id_registrazione_bdr, modified, " +
            " modified_by, note, trashed_date, tipo_operazione ";
    	
		if(att.getAccettazione()!=null)
			sql += ", accettazione  ";
		if(att.getCc()!=null)
			sql += ", cc  ";

		sql += " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ";
		
		if(att.getAccettazione()!=null)
			sql += ", ?  ";
		if(att.getCc()!=null)
			sql += ", ?  ";

		sql += " ); ";
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, att.getDescrizione());
		st.setDate(2, new java.sql.Date(att.getEntered().getTime()));
		st.setInt(3, att.getEnteredBy());
		st.setInt(4, att.getIdRegistrazioneBdr());
		st.setDate(5, new java.sql.Date(att.getModified().getTime()));
		st.setInt(6, att.getModifiedBy());
		st.setString(7, att.getNote());
		st.setDate(8, (att.getTrashedDate()!=null)?(new java.sql.Date(att.getTrashedDate().getTime())):(null));
		st.setInt(9, att.getOperazioneBdr().getId());
		int i= 10;
		if(att.getAccettazione()!=null)
		{
			st.setInt(i, att.getAccettazione().getId());
			i++;
		}
		if(att.getCc()!=null)
			st.setInt(i, att.getCc().getId());
		st.execute();
		st.close();
	}
	
	
	public static void update( int idRegistrazioneBdr, int idAttivita , Connection connection) throws SQLException
	{
		String sql = " update attivita_bdr set id_registrazione_bdr = ? where id = ? ";
    	
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idRegistrazioneBdr);
		st.setInt(2, idAttivita);
		st.executeUpdate();
		st.close();
	}
	
	public static HashSet<LookupPersonaleInterno> getPersonaleInterno( int idAcc, Connection connection) throws SQLException
	{
		HashSet<LookupPersonaleInterno> pis = new HashSet<LookupPersonaleInterno>();
		String sql = " select pi.id, pi.enabled, pi.nominativo "
				+ " from lookup_personale_interno pi, accettazione_personaleinterno api "
				+ " where api.accettazione = ? and api.personale_interno = pi.id ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAcc);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			LookupPersonaleInterno pi = new LookupPersonaleInterno();
			pi.setEnabled(rs.getBoolean("enabled"));
			pi.setId(rs.getInt("id"));
			pi.setNominativo(rs.getString("nominativo"));
			pis.add(pi);
		}
		return pis;
	}
	
	
	
	private static Animale getAnimale(ResultSet rs,Connection conn) throws SQLException 
	{
		Animale animale = new Animale();
		animale.setId(rs.getInt("idAn"));
		animale.setIdentificativo(rs.getString("identificativo"));
		animale.setLookupSpecie(LookupSpecieDAO.getSpecie(rs.getInt("lookup_specie_id"),conn));
		animale.setDecedutoNonAnagrafe(rs.getBoolean("deceduto_non_anagrafe"));
		animale.setDataMorte(rs.getDate("deceduto_non_anagrafe_data_morte"));
		animale.setDataSmaltimentoCarogna(rs.getDate("data_smaltimento_carogna"));
		animale.setTatuaggio(rs.getString("tatuaggio"));
		animale.setDataNascita(rs.getDate("data_nascita"));
		animale.setEta(LookupSinantropiEtaDAO.getEta(rs.getInt("eta"),conn));
		animale.setClinicaChippatura(ClinicaDAO.getClinica(rs.getInt("clinica_chippatura"), conn));
		animale.setDataMortePresunta(rs.getBoolean("deceduto_non_anagrafe_data_morte_presunta"));
		animale.setCausaMorte(LookupCMIDAO.getCMI(rs.getInt("deceduto_non_anagrafe_causa_morte"),conn));
		animale.setComuneRitrovamento(LookupComuniDAO.getComune(rs.getInt("comune_ritrovamento"), conn));
		animale.setProvinciaRitrovamento(rs.getString("provincia_ritrovamento"));
		animale.setIndirizzoRitrovamento(rs.getString("indirizzo_ritrovamento"));
		animale.setNoteRitrovamento(rs.getString("note_ritrovamento"));
		animale.setDataSmaltimentoCarogna(rs.getDate("data_smaltimento_carogna"));
		animale.setDittaAutorizzata(rs.getString("ditta_autorizzata"));
		animale.setDdt(rs.getString("ddt"));
		animale.setAccettaziones(getAccettaziones(animale.getId(),conn));
		animale.setTaglia(rs.getInt("taglia"));
		animale.setSpecieSinantropo(rs.getString("specie_sinantropo"));
		animale.setRazzaSinantropo(rs.getString("razza_sinantropo"));
		animale.setMantello(rs.getInt("mantello"));
		animale.setRazza(rs.getInt("razza"));
		animale.setSesso(rs.getString("sesso"));
		return animale;
	}
	
	
	
	private static HashSet<Accettazione> getAccettaziones( int idAnimale, Connection connection) throws SQLException
	{
		HashSet<Accettazione> pis = new HashSet<Accettazione>();
		String sql = " select acc.id, sterilizzato "
				+ " from accettazione acc "
				+ " where acc.trashed_date is null and acc.animale = ? ";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idAnimale);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			Accettazione acc = new Accettazione();
			acc.setId(rs.getInt("id"));
			acc.setSterilizzato(rs.getBoolean("sterilizzato"));
			pis.add(acc);
		}
		return pis;
	}
	
	
	
}
