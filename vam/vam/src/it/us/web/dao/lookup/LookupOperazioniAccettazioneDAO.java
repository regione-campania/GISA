/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.lookup;

import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupTipoTrasferimento;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.util.Md5;
import it.us.web.util.bean.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupOperazioniAccettazioneDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( LookupOperazioniAccettazioneDAO.class );
	
	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static LookupOperazioniAccettazione getOperazioneAccettazione( int id, Connection connection ) throws SQLException
	{
		LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
		String sql = "select  verso_assoc_canili, id,description,inbdr,obbligo_cc,canina,felina,sinantropi,level,enabled,enabled_in_page,approfondimenti, "
				   + "approfondimento_diagnostico_medicina,richiesta_prelievi_malattie_infettive,alta_specialita_chirurgica,"
				   + "diagnostica_strumentale,effettuabile_fuori_asl,effettuabile_fuori_asl_morto,effettuabile_da_morto,"
				   + "effettuabile_da_vivo,scelta_asl,intra_fuori_asl,hidden_in_page, "
				   + "enabled_default,id_bdr "
				   + "from lookup_operazioni_accettazione where enabled and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			op.setDescription(rs.getString("description"));
			op.setId(rs.getInt("id"));
			op.setEnabled(rs.getBoolean("enabled"));
			op.setInbdr(rs.getBoolean("inbdr"));
			op.setAltaSpecialitaChirurgica(rs.getBoolean("alta_specialita_chirurgica"));
			op.setApprofondimenti(rs.getBoolean("approfondimenti"));
			op.setApprofondimentoDiagnosticoMedicina(rs.getBoolean("approfondimento_diagnostico_medicina"));
			op.setCanina(rs.getBoolean("canina"));
			op.setDiagnosticaStrumentale(rs.getBoolean("diagnostica_strumentale"));
			op.setEffettuabileDaMorto(rs.getBoolean("effettuabile_da_morto"));
			op.setEffettuabileDaVivo(rs.getBoolean("effettuabile_da_vivo"));
			op.setEffettuabileFuoriAsl(rs.getBoolean("effettuabile_fuori_asl"));
			op.setEffettuabileFuoriAslMorto(rs.getBoolean("effettuabile_fuori_asl_morto"));
			op.setEnabledDefault(rs.getBoolean("enabled_default"));
			op.setEnabledInPage(rs.getBoolean("enabled_in_page"));
			op.setFelina(rs.getBoolean("felina"));
			op.setIdBdr(rs.getInt("id_bdr"));
			op.setIntraFuoriAsl(rs.getBoolean("intra_fuori_asl"));
			op.setVersoAssocCanili(rs.getBoolean("verso_assoc_canili"));
			op.setLevel(rs.getInt("level"));
			op.setObbligoCc(rs.getBoolean("obbligo_cc"));
			op.setRichiestaPrelieviMalattieInfettive(rs.getBoolean("richiesta_prelievi_malattie_infettive"));
			op.setSceltaAsl(rs.getBoolean("scelta_asl"));
			op.setHiddenInPage(rs.getBoolean("hidden_in_page"));
		}
		return op;
	}
	
	public static LookupOperazioniAccettazione getOperazioneAccettazione( ResultSet rs ) throws SQLException
	{
		LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
		op.setDescription(rs.getString("description"));
		op.setId(rs.getInt("id"));
		op.setEnabled(rs.getBoolean("enabled"));
		op.setInbdr(rs.getBoolean("inbdr"));
		op.setAltaSpecialitaChirurgica(rs.getBoolean("alta_specialita_chirurgica"));
		op.setApprofondimenti(rs.getBoolean("approfondimenti"));
		op.setApprofondimentoDiagnosticoMedicina(rs.getBoolean("approfondimento_diagnostico_medicina"));
		op.setCanina(rs.getBoolean("canina"));
		op.setDiagnosticaStrumentale(rs.getBoolean("diagnostica_strumentale"));
		op.setEffettuabileDaMorto(rs.getBoolean("effettuabile_da_morto"));
		op.setEffettuabileDaVivo(rs.getBoolean("effettuabile_da_vivo"));
		op.setEffettuabileFuoriAsl(rs.getBoolean("effettuabile_fuori_asl"));
		op.setEffettuabileFuoriAslMorto(rs.getBoolean("effettuabile_fuori_asl_morto"));
		op.setEnabledDefault(rs.getBoolean("enabled_default"));
		op.setEnabledInPage(rs.getBoolean("enabled_in_page"));
		op.setFelina(rs.getBoolean("felina"));
		op.setIdBdr(rs.getInt("id_bdr"));
		op.setIntraFuoriAsl(rs.getBoolean("intra_fuori_asl"));
		op.setVersoAssocCanili(rs.getBoolean("verso_assoc_canili"));
		op.setLevel(rs.getInt ("level"));
		op.setObbligoCc(rs.getBoolean("obbligo_cc"));
		op.setRichiestaPrelieviMalattieInfettive(rs.getBoolean("richiesta_prelievi_malattie_infettive"));
		op.setSceltaAsl(rs.getBoolean("scelta_asl"));
		op.setCovid(rs.getBoolean("covid"));
		return op;
	}
	
	
	
	public static ArrayList<LookupOperazioniAccettazione> getOperazioniAccettazione(Connection connection, String specieAnimale ) throws SQLException
	{
		ArrayList<LookupOperazioniAccettazione> ops = new ArrayList<LookupOperazioniAccettazione>();
		String sql = "select  verso_assoc_canili, id,description,inbdr,obbligo_cc,canina,felina,sinantropi,level,enabled,enabled_in_page,approfondimenti, "
				   + "approfondimento_diagnostico_medicina,richiesta_prelievi_malattie_infettive,alta_specialita_chirurgica,"
				   + "diagnostica_strumentale,effettuabile_fuori_asl,effettuabile_fuori_asl_morto,effettuabile_da_morto,"
				   + "effettuabile_da_vivo,scelta_asl,intra_fuori_asl,"
				   + "enabled_default,id_bdr, covid "
				   + "from lookup_operazioni_accettazione where enabled and " + specieAnimale + " and hidden_in_page order by id asc, level asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			ops.add(getOperazioneAccettazione(rs));
		}
		return ops;
	}
	
	
	public static ArrayList<LookupOperazioniAccettazione> getOperazioniCovid(Connection connection ) throws SQLException
	{
		ArrayList<LookupOperazioniAccettazione> ops = new ArrayList<LookupOperazioniAccettazione>();
		String sql = "select  verso_assoc_canili, id,description,inbdr,obbligo_cc,canina,felina,sinantropi,level,enabled,enabled_in_page,approfondimenti, "
				   + "approfondimento_diagnostico_medicina,richiesta_prelievi_malattie_infettive,alta_specialita_chirurgica,"
				   + "diagnostica_strumentale,effettuabile_fuori_asl,effettuabile_fuori_asl_morto,effettuabile_da_morto,"
				   + "effettuabile_da_vivo,scelta_asl,intra_fuori_asl,covid,"
				   + "enabled_default,id_bdr "
				   + "from lookup_operazioni_accettazione where enabled and covid and hidden_in_page order by id asc, level asc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			ops.add(getOperazioneAccettazione(rs));
		}
		return ops;
	}
	
	
	public static ArrayList<LookupDestinazioneAnimale> getDestinazioneAnimale( Connection connection, String tipologiaAnimale) throws SQLException
	{
		ArrayList<LookupDestinazioneAnimale> list = new ArrayList<LookupDestinazioneAnimale>();
		String sql = " select * from lookup_destinazione_animale where "+tipologiaAnimale+" = true order by level asc";
		PreparedStatement st = connection.prepareStatement(sql);
		//st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		if(rs.next())
		{
			LookupDestinazioneAnimale corrente = new LookupDestinazioneAnimale();
			corrente.setId(rs.getInt("id"));
			corrente.setDescription(rs.getString("description"));
			corrente.setDescriptionSinantropo(rs.getString("description_sinantropo"));
			corrente.setLevel(rs.getInt("level"));
			corrente.setEnabled(rs.getBoolean("enabled"));
			corrente.setCane(rs.getBoolean("cane"));
			corrente.setGatto(rs.getBoolean("gatto"));
			corrente.setSinantropo(rs.getBoolean("sinantropo"));
			corrente.setDimissioniCcTrasferimento(rs.getBoolean("dimissioni_cc_trasferimento"));
			list.add(corrente);
		}
		return list;
	}

	
	public static LookupDestinazioneAnimale getDestinazioneAnimale( Connection connection, int id) throws SQLException
	{
		String sql = " select * from lookup_destinazione_animale where enabled = true and id = ?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		LookupDestinazioneAnimale corrente=null;
		if(rs.next())
		{
		 corrente = new LookupDestinazioneAnimale();
			corrente.setId(rs.getInt("id"));
			corrente.setDescription(rs.getString("description"));
			corrente.setDescriptionSinantropo(rs.getString("description_sinantropo"));
			corrente.setLevel(rs.getInt("level"));
			corrente.setEnabled(rs.getBoolean("enabled"));
			corrente.setCane(rs.getBoolean("cane"));
			corrente.setGatto(rs.getBoolean("gatto"));
			corrente.setSinantropo(rs.getBoolean("sinantropo"));
			corrente.setDimissioniCcTrasferimento(rs.getBoolean("dimissioni_cc_trasferimento"));
		}
		return corrente;
	}
	
	
	public static Set<LookupOperazioniAccettazione> getOperazioniByIdTrasferimento(Connection conn, int id) throws SQLException{
		Set<LookupOperazioniAccettazione> operazioni = new HashSet<LookupOperazioniAccettazione>();
		String query="select l.* from trasferimento t join trasferimento_operazionirichieste tt on t.id=tt.trasferimento join lookup_operazioni_accettazione l on tt.operazione_richiesta=l.id where t.id = ? ";
		PreparedStatement st = conn.prepareStatement(query);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		
		while (rs.next()) {

			LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
			op.setDescription(rs.getString("description"));
			op.setId(rs.getInt("id"));
			op.setEnabled(rs.getBoolean("enabled"));
			op.setInbdr(rs.getBoolean("inbdr"));
			op.setAltaSpecialitaChirurgica(rs.getBoolean("alta_specialita_chirurgica"));
			op.setApprofondimenti(rs.getBoolean("approfondimenti"));
			op.setApprofondimentoDiagnosticoMedicina(rs.getBoolean("approfondimento_diagnostico_medicina"));
			op.setCanina(rs.getBoolean("canina"));
			op.setDiagnosticaStrumentale(rs.getBoolean("diagnostica_strumentale"));
			op.setEffettuabileDaMorto(rs.getBoolean("effettuabile_da_morto"));
			op.setEffettuabileDaVivo(rs.getBoolean("effettuabile_da_vivo"));
			op.setEffettuabileFuoriAsl(rs.getBoolean("effettuabile_fuori_asl"));
			op.setEffettuabileFuoriAslMorto(rs.getBoolean("effettuabile_fuori_asl_morto"));
			op.setEnabledDefault(rs.getBoolean("enabled_default"));
			op.setEnabledInPage(rs.getBoolean("enabled_in_page"));
			op.setFelina(rs.getBoolean("felina"));
			op.setIdBdr(rs.getInt("id_bdr"));
			op.setIntraFuoriAsl(rs.getBoolean("intra_fuori_asl"));
			op.setVersoAssocCanili(rs.getBoolean("verso_assoc_canili"));
			op.setLevel(rs.getInt("level"));
			op.setObbligoCc(rs.getBoolean("obbligo_cc"));
			op.setRichiestaPrelieviMalattieInfettive(rs.getBoolean("richiesta_prelievi_malattie_infettive"));
			op.setSceltaAsl(rs.getBoolean("scelta_asl"));
			
			operazioni.add(op);

		}
		
		if (operazioni.isEmpty())
			return null;
		else
			return operazioni;
	}


	
}
