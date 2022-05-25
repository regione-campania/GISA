/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.dao.vam;

import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Clinica;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.StatoTrasferimento;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoSedeLesione;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.constants.Sql;
import it.us.web.dao.GenericDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoSedeLesioneDAO;
import it.us.web.dao.lookup.LookupEsameIstopatologicoTipoDiagnosiDAO;
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

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.infinispan.loaders.modifications.Prepare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClinicaDAO extends GenericDAO
{
	private static final Logger logger = LoggerFactory.getLogger( 

			ClinicaDAO.class );

	/**
	 * Obsoleto
	 * @param un
	 * @param pw
	 * @param persistence
	 * @return
	 */
	public static Clinica getClinica( int id, Connection connection ) throws 

	SQLException
	{
		Clinica c = null;
		String sql = "select id, comune as comune_id, asl as asl_id, nome,nome_breve as nomeBreve, indirizzo,telefono," + 
				" fax,email,note,entered,modified,trashed_date as trashedDate, canile_bdu, id_stabilimento_gisa, data_cessazione from clinica where trashed_date is null and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			c = new Clinica();
			c = (Clinica)Bean.populate(c, rs);
			c.setNome(rs.getString("nome"));
			c.setNomeBreve(rs.getString("nomeBreve"));
			c.setCanileBdu(rs.getInt("canile_bdu"));
			c.setIdStabilimentoGisa(rs.getInt("id_stabilimento_gisa"));
			c.setDataCessazione(rs.getDate("data_cessazione"));
			c.setLookupComuni(LookupComuniDAO.getComune(rs.getInt

					("comune_id"), connection));
			c.setLookupAsl(LookupAslDAO.getAsl(rs.getInt("asl_id"), 

					connection));
			//c.setUtentis(getUtenti(connection, rs.getInt("id")));
		}
		return c;
	}

	public static String getNomeBreve( int id, Connection connection ) throws 

	SQLException
	{
		String nomeBreve = null;
		String sql = " select nome,nome_breve as nomeBreve "
				+    " from clinica where trashed_date is null and id = ? " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			nomeBreve = rs.getString("nomeBreve");
		}
		return nomeBreve;
	}

	public static HashSet<BUtente> getUtenti(Connection connection, int 

			idClinica) throws SQLException
			{
		HashSet<BUtente> utenti = new HashSet<BUtente>();
		String sql = "select id from utenti where trashed_date is null and clinica = ?" ;
						PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			utenti.add(UtenteDAO.getUtente(rs.getInt("id"), connection));
		}
		return utenti;
			}

	public static ArrayList<EsameIstopatologico> getEsamiIstopatologici

	(Connection connection, int idClinica) throws SQLException
	{
		ArrayList<EsameIstopatologico> esami = new 

				ArrayList<EsameIstopatologico>();
		String sql = " select e.tipo_diagnosi, e.sede_lesione, e.numero,e.id, e.diagnosi_non_tumorale, e.data_esito, e.data_richiesta, e.outsideCC, "
						+    " wu.id as wu_id, wu.description as wu_description, wu.enabled as wu_enabled, wu.codice as wu_codice, wu.level as wu_level, "
		+    " an.identificativo, "
		+    " cc.id as id_cc, "
		+    " acc.id as id_acc, "
		+    " an2.id as id_animale, an2.identificativo as identificativo2, "
		+    " sede_lesione.id as id_sede_lesione, sede_lesione.description as description_sede_lesione, sede_lesione.codice as codice_sede_lesione, sede_lesione.level as level_sede_lesione, sede_lesione.enabled as enabled_sede_lesione "
+    " from esame_istopatologico e "
+    " left join utenti_ u on u.id = e.entered_by "
+    " left join animale an on an.id = e.animale and an.trashed_date is null "
+    " left join cartella_clinica cc on cc.id = e.cartella_clinica and cc.trashed_date is null "
+    " left join accettazione acc on cc.accettazione = acc.id and acc.trashed_date is null "
+    " left join animale an2 on an2.id = acc.animale and an2.trashed_date is null "
+    " left join lookup_esame_istopatologico_who_umana wu on wu.id = e.who_umana and wu.enabled "
+    " left join lookup_esame_istopatologico_sede_lesione sede_lesione on sede_lesione.id = e.sede_lesione and sede_lesione.enabled  "
		+    " where e.entered_by = u.id and e.trashed_date is null and u.clinica = ? "
		+    " order by e.data_richiesta desc " ;
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idClinica);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			EsameIstopatologico esame = new EsameIstopatologico();

			//esame.setTipoDiagnosi(LookupEsameIstopatologicoTipoDiagnosiDAO.get(rs.getInt("tipo_diagnosi"),connection));

			if(rs.getString("identificativo")!=null)
			{
				Animale animale = new Animale();
				animale.setIdentificativo(rs.getString

						("identificativo"));
				esame.setAnimale(animale);
			}

			if(rs.getString("identificativo2")!=null)
			{
				Animale an = new Animale();
				an.setId(rs.getInt("id_animale"));
				an.setIdentificativo(rs.getString

						("identificativo2"));
				CartellaClinica cc = new CartellaClinica();
				cc.setId(rs.getInt("id_cc"));
				Accettazione acc = new Accettazione();
				acc.setId(rs.getInt("id_acc"));
				acc.setAnimale(an);
				cc.setAccettazione(acc);
				esame.setCartellaClinica(cc);
			}

			if(rs.getInt("wu_id")>0)
			{
				LookupEsameIstopatologicoWhoUmana wu = new 

						LookupEsameIstopatologicoWhoUmana();
				wu.setId(rs.getInt("wu_id"));
				wu.setCodice(rs.getString("wu_codice"));
				wu.setDescription(rs.getString("wu_description"));
				wu.setEnabled(rs.getBoolean("wu_enabled"));
				wu.setLevel(rs.getInt("wu_level"));
				esame.setWhoUmana(wu);
			}

			esame.setOutsideCC(rs.getBoolean("outsideCC"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			
			if(rs.getInt("tipo_diagnosi")!=3)
				esame.setDiagnosiNonTumorale("");
			else
				esame.setDiagnosiNonTumorale(rs.getString("diagnosi_non_tumorale"));
		
			esame.setId(rs.getInt("id"));
			esame.setNumero(rs.getString("numero"));
			esame.setSedeLesione(LookupEsameIstopatologicoSedeLesioneDAO.getSede(rs, "_sede_lesione"));
			esami.add(esame);
		}
		return esami;
	}


	public static ArrayList<EsameIstopatologico> getEsamiIstopatologiciIzsmUnina(Connection connection, int idSalaSett) throws SQLException
	{
		ArrayList<EsameIstopatologico> esami = new ArrayList<EsameIstopatologico>();
		String sql = " select e.entered_by,e.tipo_diagnosi, e.tipo_accettazione, e.numero_accettazione_sigla, e.sede_lesione, e.numero,e.id, e.diagnosi_non_tumorale, e.data_esito, e.data_richiesta, e.outsideCC, "
				+    " wu.id as wu_id, wu.description as wu_description, wu.enabled as wu_enabled, wu.codice as wu_codice, wu.level as wu_level, "
				+    " an.identificativo, "
				+    " cc.id as id_cc, "
				+    " acc.id as id_acc, "
				+    " an2.id as id_animale, an2.identificativo as identificativo2 , "
		        +    " sede_lesione.id as id_sede_lesione, sede_lesione.description as description_sede_lesione, sede_lesione.codice as codice_sede_lesione, sede_lesione.level as level_sede_lesione, sede_lesione.enabled as enabled_sede_lesione "
				+    " from esame_istopatologico e "
				+    " left join utenti_ u on u.id = e.entered_by "
				+    " left join animale an on an.id = e.animale and an.trashed_date is null "
				+    " left join cartella_clinica cc on cc.id = e.cartella_clinica and cc.trashed_date is null "
				+    " left join accettazione acc on cc.accettazione = acc.id and acc.trashed_date is null "
			    +    " left join animale an2 on an2.id = acc.animale and an2.trashed_date is null "
	+    " left join lookup_esame_istopatologico_who_umana wu on wu.id = e.who_umana and wu.enabled "
	+    " left join lookup_esame_istopatologico_sede_lesione sede_lesione on sede_lesione.id = e.sede_lesione and sede_lesione.enabled  "		
	+    " where e.entered_by = u.id and e.trashed_date is null and ( e.sala_settoria = ? or e.sala_settoria is null )  "
			+    " order by e.data_richiesta desc " ;
		
		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idSalaSett);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			EsameIstopatologico esame = new EsameIstopatologico();


			if(rs.getString("identificativo")!=null)
			{
				Animale animale = new Animale();
				animale.setIdentificativo(rs.getString

						("identificativo"));
				esame.setAnimale(animale);
			}

			if(rs.getString("identificativo2")!=null)
			{
				Animale an = new Animale();
				an.setId(rs.getInt("id_animale"));
				an.setIdentificativo(rs.getString

						("identificativo2"));
				CartellaClinica cc = new CartellaClinica();
				cc.setId(rs.getInt("id_cc"));
				Accettazione acc = new Accettazione();
				acc.setId(rs.getInt("id_acc"));
				acc.setAnimale(an);
				cc.setAccettazione(acc);
				esame.setCartellaClinica(cc);
			}

			if(rs.getInt("wu_id")>0)
			{
				LookupEsameIstopatologicoWhoUmana wu = new 

						LookupEsameIstopatologicoWhoUmana();
				wu.setId(rs.getInt("wu_id"));
				wu.setCodice(rs.getString("wu_codice"));
				wu.setDescription(rs.getString("wu_description"));
				wu.setEnabled(rs.getBoolean("wu_enabled"));
				wu.setLevel(rs.getInt("wu_level"));
				esame.setWhoUmana(wu);
			}

			esame.setOutsideCC(rs.getBoolean("outsideCC"));
			esame.setDataEsito(rs.getDate("data_esito"));
			esame.setDataRichiesta(rs.getDate("data_richiesta"));
			if(rs.getInt("tipo_diagnosi")!=3)
				esame.setDiagnosiNonTumorale("");
			else
				esame.setDiagnosiNonTumorale(rs.getString("diagnosi_non_tumorale"));
			esame.setId(rs.getInt("id"));
			esame.setNumero(rs.getString("numero"));
			//esame.setSedeLesione(LookupEsameIstopatologicoSedeLesioneDAO.getSede(rs.getInt("sede_lesione"),connection));
			esame.setSedeLesione(LookupEsameIstopatologicoSedeLesioneDAO.getSede(rs, "_sede_lesione"));
			esame.setNumeroAccettazioneSigla(rs.getString("numero_accettazione_sigla"));
			esame.setTipoAccettazione(rs.getString("tipo_accettazione"));
			//esame.setEnteredBy(UtenteDAO.getUtenteAll(rs.getInt("entered_by"),connection));
			BUtenteAll utente = new BUtenteAll();
			utente.setId(rs.getInt("entered_by"));
			esame.setEnteredBy(utente);
			
			esami.add(esame);
		}
		return esami;
	}


	public static ArrayList<Trasferimento> getTrasferimenti(String tipo, int idClinica, int idAnimale, Integer stato, Connection connection) throws SQLException
	{
		ArrayList<Trasferimento> trasferimenti = new ArrayList<Trasferimento>();

		String campoFiltroClinica = "";
		if(tipo.equals("I"))
			campoFiltroClinica = " clinica_destinazione ";
		else
			campoFiltroClinica = " clinica_origine ";

		String sql = " select * "
				+  " from trasferimento t "
				+  " left join cartella_clinica cc  on cc.id  = t.cartella_clinica and cc.trashed_date is null "
				+  " left join accettazione     acc on acc.id = cc.accettazione    and acc.trashed_date is null "
				+  " left join animale          an  on an.id  = acc.animale        and an.trashed_date is null "
				+  " where t.trashed_date is null and "
				+  " t." +campoFiltroClinica + " = ? and "
				+  " an.id = ? ";


		PreparedStatement st = connection.prepareStatement(sql);
		st.setInt(1, idClinica);
		st.setInt(2, idAnimale);
		ResultSet rs = st.executeQuery();

		while(rs.next())
		{
			Trasferimento t = new Trasferimento();
			if(stato==null || t.getStato().getStato()==stato)
			{
				trasferimenti.add(t);
			}

		}
		return trasferimenti;
	}

	
	


}
