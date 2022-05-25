/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.aspcfs.modules.mu.operazioni.base.Macellazione;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class CapoUnivoco extends GenericBean {

	public static final int idStatoDocumentale = 1;
	public static final int idStatoMacellato = 2;
	public static final int idStatoArrivatoDeceduto = 4;

	private int numeroCapi = 0;
	private int specieCapo = -1;
	private String matricola = "";
	private int categoriaCapo = -1;
	private int categoriaBovina = -1;
	private int categoriaBufalina = -1;
	private int razzaBovina = -1;
	private String sesso = "";
	private Timestamp dataNascita = null;
	private int categoriaRischio = -1;
	private int idPartita = -1;
	private int idSeduta = -1;
	private int id = -1;
	private String numeroPartita = "";
	private String specieCapoNome = "";
	private int idStato = -1;
	private boolean flagArrivatoDeceduto = false;
	private int idPathMacellazione = -1;

	private Timestamp dataMacellazione = null;

	private PartitaUnivoca partita = new PartitaUnivoca();
	private SedutaUnivoca seduta = new SedutaUnivoca();

	private Macellazione dettagliMacellazione = new Macellazione();

	// GESTIONE PARTI
	private int numeroParti = -1;
	private int numeroPartiAssegnate = 0;
	
	
	
	/*INFORMAZIONI AZIENDA PROVENIENZA BDN*/
	private String infoAziendaProvenienza = "";
	private String ragioneSocialeAziendaProvenienza = "";
	private String denominazioneAslAziendaProvenienza  = "";
	private int  idAslAziendaProvenienza = -1;
	private String  codAslAziendaProv = "";

	public int getNumeroCapi() {
		return numeroCapi;
	}

	public void setNumeroCapi(String numeroCapi) {
		if (numeroCapi != null && !numeroCapi.equals("") && !numeroCapi.equals("null"))
			this.numeroCapi = Integer.parseInt(numeroCapi);
	}

	public void setNumeroCapi(int numeroCapi) {
		this.numeroCapi = numeroCapi;
	}

	public int getSpecieCapo() {
		return specieCapo;
	}

	public void setSpecieCapo(int specieCapo) {
		this.specieCapo = specieCapo;
	}

	public void setSpecieCapo(String specieCapo) {
		if (specieCapo != null && !specieCapo.equals("") && !specieCapo.equals("null"))
			this.specieCapo = Integer.parseInt(specieCapo);
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public int getCategoriaBovina() {
		return categoriaBovina;
	}

	public void setCategoriaBovina(int categoriaBovina) {
		this.categoriaBovina = categoriaBovina;
	}

	public void setCategoriaBovina(String categoriaBovina) {
		if (categoriaBovina != null && !categoriaBovina.equals("") && !categoriaBovina.equals("null"))
			this.categoriaBovina = Integer.parseInt(categoriaBovina);
	}

	public int getCategoriaBufalina() {
		return categoriaBufalina;
	}

	public void setCategoriaBufalina(int categoriaBufalina) {
		this.categoriaBufalina = categoriaBufalina;
	}

	public void setCategoriaBufalina(String categoriaBufalina) {
		if (categoriaBufalina != null && !categoriaBufalina.equals("") && !categoriaBufalina.equals("null"))
			this.categoriaBufalina = Integer.parseInt(categoriaBufalina);
	}

	public int getRazzaBovina() {
		return razzaBovina;
	}

	public void setRazzaBovina(int razzaBovina) {
		this.razzaBovina = razzaBovina;
	}

	public void setRazzaBovina(String razzaBovina) {
		if (razzaBovina != null && !razzaBovina.equals("") && !razzaBovina.equals("null"))
			this.razzaBovina = Integer.parseInt(razzaBovina);
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = DateUtils.parseDateStringNew(dataNascita, "dd/MM/yyyy");
	}

	public int getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}

	public void setCategoriaRischio(String categoriaRischio) {
		if (categoriaRischio != null && !categoriaRischio.equals("") && !categoriaRischio.equals("null"))
			this.categoriaRischio = Integer.parseInt(categoriaRischio);
	}

	public int getIdPartita() {
		return idPartita;
	}

	public void setIdPartita(int idPartita) {
		this.idPartita = idPartita;
	}

	public int getIdSeduta() {
		return idSeduta;
	}

	public void setIdSeduta(int idSeduta) {
		this.idSeduta = idSeduta;
	}

	public int getCategoriaCapo() {
		return categoriaCapo;
	}

	public void setCategoriaCapo(int categoriaCapo) {
		this.categoriaCapo = categoriaCapo;
	}

	public void setCategoriaCapo(String categoriaCapo) {
		if (categoriaCapo != null && !categoriaCapo.equals("") && !categoriaCapo.equals("null"))
			this.categoriaCapo = Integer.parseInt(categoriaCapo);
	}

	public int getNumeroParti() {
		return numeroParti;
	}

	public void setNumeroParti(int numeroParti) {
		this.numeroParti = numeroParti;
	}

	public int getNumeroPartiAssegnate() {
		return numeroPartiAssegnate;
	}

	public void setNumeroPartiAssegnate(int numeroPartiAssegnate) {
		this.numeroPartiAssegnate = numeroPartiAssegnate;
	}

	public int getIdStato() {
		return idStato;
	}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public Timestamp getDataMacellazione() {
		return dataMacellazione;
	}

	public void setDataMacellazione(Timestamp dataMacellazione) {
		this.dataMacellazione = dataMacellazione;
	}

	public PartitaUnivoca getPartita() {
		return partita;
	}

	public void setPartita(PartitaUnivoca partita) {
		this.partita = partita;
	}

	public SedutaUnivoca getSeduta() {
		return seduta;
	}

	public void setSeduta(SedutaUnivoca seduta) {
		this.seduta = seduta;
	}

	public boolean isFlagArrivatoDeceduto() {
		return flagArrivatoDeceduto;
	}

	public void setFlagArrivatoDeceduto(boolean flagArrivatoDeceduto) {
		this.flagArrivatoDeceduto = flagArrivatoDeceduto;
	}

	public void setFlagArrivatoDeceduto(String flagArrivatoDeceduto) {
		this.flagArrivatoDeceduto = DatabaseUtils.parseBoolean(flagArrivatoDeceduto);
	}

	public int getIdPathMacellazione() {
		return idPathMacellazione;
	}

	public void setIdPathMacellazione(int idPathMacellazione) {
		this.idPathMacellazione = idPathMacellazione;
	}

	public void setIdPathMacellazione(String idPathMacellazione) {
		this.idPathMacellazione = Integer.parseInt(idPathMacellazione);
	}

	public Macellazione getDettagliMacellazione() {
		return dettagliMacellazione;
	}

	public void setDettagliMacellazione(Macellazione dettagliMacellazione) {
		this.dettagliMacellazione = dettagliMacellazione;
	}
	

	public String getInfoAziendaProvenienza() {
		return infoAziendaProvenienza;
	}

	public void setInfoAziendaProvenienza(String infoAziendaProvenienza) {
		this.infoAziendaProvenienza = infoAziendaProvenienza;
	}

	public String getRagioneSocialeAziendaProvenienza() {
		return ragioneSocialeAziendaProvenienza;
	}

	public void setRagioneSocialeAziendaProvenienza(String ragioneSocialeAziendaProvenienza) {
		this.ragioneSocialeAziendaProvenienza = ragioneSocialeAziendaProvenienza;
	}



	public String getDenominazioneAslAziendaProvenienza() {
		return denominazioneAslAziendaProvenienza;
	}

	public void setDenominazioneAslAziendaProvenienza(String denominazioneAslAziendaProvenienza) {
		this.denominazioneAslAziendaProvenienza = denominazioneAslAziendaProvenienza;
	}

	public int getIdAslAziendaProvenienza() {
		return idAslAziendaProvenienza;
	}

	public void setIdAslAziendaProvenienza(int idAslAziendaProvenienza) {
		this.idAslAziendaProvenienza = idAslAziendaProvenienza;
	}
	
	
	public void setIdAslAziendaProvenienza(String idAslAziendaProvenienza) {
		this.idAslAziendaProvenienza = Integer.parseInt(idAslAziendaProvenienza);
	}

	public String getCodAslAziendaProv() {
		return codAslAziendaProv;
	}

	public void setCodAslAziendaProv(String codAslAziendaProv) {
		this.codAslAziendaProv = codAslAziendaProv;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO mu_capi (entered ");

		if (idPartita > -1)
			sql.append(", id_partita");
		// if (numeroCapi>-1)
		// sql.append(", num_capi");
		if (specieCapo > 0)
			sql.append(", specie");
		if (matricola != null)
			sql.append(", matricola");
		if (categoriaCapo > 0)
			sql.append(", categoria_capo");
		if (categoriaBovina > 0)
			sql.append(", categoria_bovina");
		if (categoriaBufalina > 0)
			sql.append(", categoria_bufalina");
		if (razzaBovina > 0)
			sql.append(", razza_bovina");
		if (sesso != null)
			sql.append(", sesso");
		if (dataNascita != null)
			sql.append(", data_nascita");
		if (categoriaRischio > 0)
			sql.append(", categoria_rischio");
		if (numeroParti > 0)
			sql.append(", numero_parti");
		// if (numeroPartiAssegnate>0)
		sql.append(", numero_parti_assegnate");
		if (idStato > 0)
			sql.append(", id_stato");
		if (idPathMacellazione > 0)
			sql.append(", id_path_macellazione");
		
		if (infoAziendaProvenienza != null && !("").equals(infoAziendaProvenienza)){
			sql.append(", info_azienda_provenienza");
		}
		
		
		if (ragioneSocialeAziendaProvenienza != null && !("").equals(ragioneSocialeAziendaProvenienza)){
			sql.append(", ragione_sociale_azienda_provenienza");
		}
		
		
		if (denominazioneAslAziendaProvenienza != null && !("").equals(denominazioneAslAziendaProvenienza)){
			sql.append(", denominazione_asl_azienda_provenienza");
		}
		

		if (idAslAziendaProvenienza > 0){
			sql.append(", id_asl_azienda_provenienza");
		}
		
		
		if (codAslAziendaProv != null && !("").equals(codAslAziendaProv)){
			sql.append(", cod_asl_azienda_prov");
		}
		

		sql.append(", flag_arrivato_deceduto");

		sql.append(")");

		sql.append(" VALUES (now() ");

		if (idPartita > -1)
			sql.append(", ?");
		// if (numeroCapi>-1)
		// sql.append(", ?");
		if (specieCapo > 0)
			sql.append(", ?");
		if (matricola != null)
			sql.append(", ?");
		if (categoriaCapo > 0)
			sql.append(", ?");
		if (categoriaBovina > 0)
			sql.append(", ?");
		if (categoriaBufalina > 0)
			sql.append(", ?");
		if (razzaBovina > 0)
			sql.append(", ?");
		if (sesso != null)
			sql.append(", ?");
		if (dataNascita != null)
			sql.append(", ?");
		if (categoriaRischio > 0)
			sql.append(", ?");
		if (numeroParti > 0)
			sql.append(", ?");
		// if (numeroPartiAssegnate>0)
		sql.append(", ?");
		if (idStato > 0)
			sql.append(", ?");
		if (idPathMacellazione > 0)
			sql.append(", ?");
		
		if (infoAziendaProvenienza != null && !("").equals(infoAziendaProvenienza)){
			sql.append(", ?");
		}
		
		
		if (ragioneSocialeAziendaProvenienza != null && !("").equals(ragioneSocialeAziendaProvenienza)){
			sql.append(", ?");
		}
		
		
		if (denominazioneAslAziendaProvenienza != null && !("").equals(denominazioneAslAziendaProvenienza)){
			sql.append(", ?");
		}
		
		

		
		if (idAslAziendaProvenienza > 0){
			sql.append(", ?");
		}
		
		
		if (codAslAziendaProv != null && !("").equals(codAslAziendaProv)){
			sql.append(", ?");
		}
		

		sql.append(", ?");

		sql.append(")");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		if (idPartita > -1)
			pst.setInt(++i, idPartita);
		// if (numeroCapi>-1)
		// pst.setInt(++i, numeroCapi);
		if (specieCapo > 0)
			pst.setInt(++i, specieCapo);
		if (matricola != null)
			pst.setString(++i, matricola);
		if (categoriaCapo > 0)
			pst.setInt(++i, categoriaCapo);
		if (categoriaBovina > 0)
			pst.setInt(++i, categoriaBovina);
		if (categoriaBufalina > 0)
			pst.setInt(++i, categoriaBufalina);
		if (razzaBovina > 0)
			pst.setInt(++i, razzaBovina);
		if (sesso != null)
			pst.setString(++i, sesso);
		if (dataNascita != null)
			pst.setTimestamp(++i, dataNascita);
		if (categoriaRischio > 0)
			pst.setInt(++i, categoriaRischio);
		if (numeroParti > 0)
			pst.setInt(++i, numeroParti);
		// if (numeroPartiAssegnate>0)
		pst.setInt(++i, numeroPartiAssegnate);
		if (idStato > 0)
			pst.setInt(++i, idStato);
		if (idPathMacellazione > 0)
			pst.setInt(++i, idPathMacellazione);
		
		if (infoAziendaProvenienza != null && !("").equals(infoAziendaProvenienza)){
			pst.setString(++i, infoAziendaProvenienza);
		}
		
		
		if (ragioneSocialeAziendaProvenienza != null && !("").equals(ragioneSocialeAziendaProvenienza)){
			pst.setString(++i, ragioneSocialeAziendaProvenienza);
		}
		
		
		if (denominazioneAslAziendaProvenienza != null && !("").equals(denominazioneAslAziendaProvenienza)){
			pst.setString(++i, denominazioneAslAziendaProvenienza);
		}
		

		
		if (idAslAziendaProvenienza > 0){
			pst.setInt(++i, idAslAziendaProvenienza);
		}
		
		
		if (codAslAziendaProv != null && !("").equals(codAslAziendaProv)){
			pst.setString(++i, codAslAziendaProv);
		}
		

		pst.setBoolean(++i, flagArrivatoDeceduto);

		pst.execute();

		pst.close();
		return true;

	}

	public CapoUnivoco() {

	}

	public CapoUnivoco(ActionContext context, Connection db, int i) {
		setMatricola(context.getRequest().getParameter("matricola_" + i));
		setCategoriaCapo(context.getRequest().getParameter("specie_" + i));
		setCategoriaBovina(context.getRequest().getParameter("categoriabovina_" + i));
		setCategoriaBufalina(context.getRequest().getParameter("categoriabufalina_" + i));
		setRazzaBovina(context.getRequest().getParameter("razza_" + i));
		setSesso(context.getRequest().getParameter("sesso_" + i));
		setDataNascita(context.getRequest().getParameter("datanascita_" + i));
		setCategoriaRischio(context.getRequest().getParameter("rischio_" + i));
		setFlagArrivatoDeceduto(context.getRequest().getParameter("flagDeceduto_" + i));
	}

	public CapoUnivoco(ResultSet rs) throws SQLException {
		setMatricola(rs.getString("matricola"));
		setCategoriaCapo(rs.getInt("categoria_capo"));
		setCategoriaBovina(rs.getInt("categoria_bovina"));
		setCategoriaBufalina(rs.getInt("categoria_bufalina"));
		setRazzaBovina(rs.getInt("razza_bovina"));
		setSesso(rs.getString("sesso"));
		setDataNascita(rs.getTimestamp("data_nascita"));
		setCategoriaRischio(rs.getInt("categoria_rischio"));
		setSpecieCapo(rs.getInt("specie"));
		setSpecieCapoNome();
		setNumeroCapi(rs.getInt("num_capi"));
		setIdPartita(rs.getInt("id_partita"));
		setIdSeduta(rs.getInt("id_seduta"));
		setNumeroParti(rs.getInt("numero_parti"));
		setNumeroPartiAssegnate(rs.getInt("numero_parti_assegnate"));
		setId(rs.getInt("id"));
		setIdStato(rs.getInt("id_stato"));
		setFlagArrivatoDeceduto(rs.getBoolean("flag_arrivato_deceduto"));
		setIdPathMacellazione(rs.getInt("id_path_macellazione"));

	}

	public Vector ricercaCapo(Connection db) {
		ResultSet rs = null;
		Vector capoList = new Vector();
		PreparedStatement pst;
		try {

			String query = "select * from mu_capi where id>0  ";
			// query = query.substring(0, query.indexOf("order by"));

			if (matricola != null && !matricola.equals(""))
				query += " and matricola ilike ? ";

			if (specieCapo > 0)
				query += " and specie = ? ";

			query += " order by id ASC ";

			pst = db.prepareStatement(query);
			int i = 0;

			if (matricola != null && !matricola.equals(""))
				pst.setString(++i, "%" + matricola + "%");

			if (specieCapo > 0)
				pst.setInt(++i, specieCapo);

			rs = DatabaseUtils.executeQuery(db, pst);
			while (rs.next()) {
				CapoUnivoco capo = new CapoUnivoco(rs);

				if (capo.getIdPathMacellazione() > 0) {
					try {
						Macellazione dettagliMacellazione = (Macellazione) Class
								.forName(
										"org.aspcfs.modules.mu.operazioni.base"
												+ ApplicationProperties.getProperty(String.valueOf(capo
														.getIdPathMacellazione()))).newInstance();
						dettagliMacellazione.getDettaglioMacellazioneByIdCapo(capo.getId(), db);
						capo.setDettagliMacellazione(dettagliMacellazione);

					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				capoList.add(capo);
			}
			rs.close();
			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capoList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroPartita() {
		return numeroPartita;
	}

	public void setNumeroPartita(String numeroPartita) {
		this.numeroPartita = numeroPartita;
	}

	public void setSpecieCapoNome() {
		String nome = "";
		switch (specieCapo) {
		case 1:
			nome = "Bovini";
			break;
		case 2:
			nome = "Ovini";
			break;
		case 3:
			nome = "Caprini";
			break;
		case 4:
			nome = "Agnelli";
			break;
		case 5:
			nome = "Capretti";
			break;
		case 6:
			nome = "Cinghiali";
			break;
		case 7:
			nome = "Suini";
			break;
		}
		this.specieCapoNome = nome;
	}

	public String getSpecieCapoNome() {
		return specieCapoNome;
	}

	public void setSpecieCapoNome(String specieCapoNome) {
		this.specieCapoNome = specieCapoNome;
	}

	public CapoUnivoco(Connection db, int idCapo) {

		String sql = "select * from mu_capi where id = ?";

		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setInt(1, idCapo);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRecord(rs, db);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public CapoUnivoco(Connection db, int idCapo, boolean buildDettagliPartita, boolean buildDettagliSeduta) {

		String sql = "select * from mu_capi where id = ?";

		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);

			pst.setInt(1, idCapo);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				buildRecord(rs, buildDettagliPartita, buildDettagliSeduta);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buildRecord(ResultSet rs, Connection db) throws SQLException {

		setMatricola(rs.getString("matricola"));
		setCategoriaCapo(rs.getInt("categoria_capo"));
		setCategoriaBovina(rs.getInt("categoria_bovina"));
		setCategoriaBufalina(rs.getInt("categoria_bufalina"));
		setRazzaBovina(rs.getInt("razza_bovina"));
		setSesso(rs.getString("sesso"));
		setDataNascita(rs.getTimestamp("data_nascita"));
		setCategoriaRischio(rs.getInt("categoria_rischio"));
		setSpecieCapo(rs.getInt("specie"));
		setSpecieCapoNome();
		setNumeroCapi(rs.getInt("num_capi"));
		setIdPartita(rs.getInt("id_partita"));
		setIdSeduta(rs.getInt("id_seduta"));
		setId(rs.getInt("id"));
		setNumeroParti(rs.getInt("numero_parti"));
		setNumeroPartiAssegnate(rs.getInt("numero_parti_assegnate"));
		setIdStato(rs.getInt("id_stato"));
		setFlagArrivatoDeceduto(rs.getBoolean("flag_arrivato_deceduto"));
		setIdPathMacellazione(rs.getInt("id_path_macellazione"));
		
		if (this.getIdPathMacellazione() > 0 && dettagliMacellazione.getId() <= 0) {
			try {
				Macellazione dettagliMacellazione = (Macellazione) Class
						.forName(
								"org.aspcfs.modules.mu.operazioni.base."
										+ ApplicationProperties.getProperty(String.valueOf(this.getIdPathMacellazione()))).newInstance();
				dettagliMacellazione.getDettaglioMacellazioneByIdCapo(this.getId(), db);
				this.setDettagliMacellazione(dettagliMacellazione);

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public void buildRecord(ResultSet rs) throws SQLException {

		setMatricola(rs.getString("matricola"));
		setCategoriaCapo(rs.getInt("categoria_capo"));
		setCategoriaBovina(rs.getInt("categoria_bovina"));
		setCategoriaBufalina(rs.getInt("categoria_bufalina"));
		setRazzaBovina(rs.getInt("razza_bovina"));
		setSesso(rs.getString("sesso"));
		setDataNascita(rs.getTimestamp("data_nascita"));
		setCategoriaRischio(rs.getInt("categoria_rischio"));
		setSpecieCapo(rs.getInt("specie"));
		setSpecieCapoNome();
		setNumeroCapi(rs.getInt("num_capi"));
		setIdPartita(rs.getInt("id_partita"));
		setIdSeduta(rs.getInt("id_seduta"));
		setId(rs.getInt("id"));
		setNumeroParti(rs.getInt("numero_parti"));
		setNumeroPartiAssegnate(rs.getInt("numero_parti_assegnate"));
		setIdStato(rs.getInt("id_stato"));
		setFlagArrivatoDeceduto(rs.getBoolean("flag_arrivato_deceduto"));
	}

	public void buildRecord(ResultSet rs, boolean buildDettagliPartita, boolean buildDettagliSeduta)
			throws SQLException {

		setMatricola(rs.getString("matricola"));
		setCategoriaCapo(rs.getInt("categoria_capo"));
		setCategoriaBovina(rs.getInt("categoria_bovina"));
		setCategoriaBufalina(rs.getInt("categoria_bufalina"));
		setRazzaBovina(rs.getInt("razza_bovina"));
		setSesso(rs.getString("sesso"));
		setDataNascita(rs.getTimestamp("data_nascita"));
		setCategoriaRischio(rs.getInt("categoria_rischio"));
		setSpecieCapo(rs.getInt("specie"));
		setSpecieCapoNome();
		setNumeroCapi(rs.getInt("num_capi"));
		setIdPartita(rs.getInt("id_partita"));
		setIdSeduta(rs.getInt("id_seduta"));
		setId(rs.getInt("id"));
		setNumeroParti(rs.getInt("numero_parti"));
		setNumeroPartiAssegnate(rs.getInt("numero_parti_assegnate"));
		setIdStato(rs.getInt("id_stato"));
		setFlagArrivatoDeceduto(rs.getBoolean("flag_arrivato_deceduto"));
		setIdPathMacellazione(rs.getInt("id_path_macellazione"));
		
		Connection db = GestoreConnessioni.getConnection();

		if (buildDettagliPartita || buildDettagliSeduta) {
			

			if (buildDettagliPartita)
				partita = new PartitaUnivoca(db, idPartita);

			if (buildDettagliSeduta)
				seduta = new SedutaUnivoca(db, idSeduta);
		}
			
			if (this.getIdPathMacellazione() > 0 && dettagliMacellazione.getId() <= 0) {
				try {
					Macellazione dettagliMacellazione = (Macellazione) Class
							.forName(
									"org.aspcfs.modules.mu.operazioni.base."
											+ ApplicationProperties.getProperty(String.valueOf(this.getIdPathMacellazione()))).newInstance();
					dettagliMacellazione.getDettaglioMacellazioneByIdCapo(this.getId(), db);
					this.setDettagliMacellazione(dettagliMacellazione);

				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

			GestoreConnessioni.freeConnection(db);
		}
	}

	public void aggiungiSeduta(Connection db, int idSeduta) {
		String sql = "update mu_capi set id_seduta = ? where id = ? ";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idSeduta);
			pst.setInt(2, this.id);

			if (this.idSeduta <= 0)
				pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateStato(Connection db) {
		String sql = "update mu_capi set id_stato = ?, id_path_macellazione = ? where id = ? ";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setInt(1, idStato);
			pst.setInt(2, idPathMacellazione);
			pst.setInt(3, this.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
