/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;



public class OrganizationUtente {
	
	int orgId;
	String servizio = "";
	String flag;
	String uo = "";
	String via_amm = "";
	String mail = "";
	String nome_presente_ispezione = "";
	String luogo_nascita_presente_ispezione = "";
	String giorno_presente_ispezione = "";
	String mese_presente_ispezione = "";
	String anno_presente_ispezione = "";
	String luogo_residenza_presente_ispezione = "";
	String via_ispezione = "";
	String num_civico_rappresentante = "";
	String num_civico_presente_ispezione = "";
	String doc_identita_presente_ispezione = "";
	String domicilio_digitale = "";
	String strumenti_ispezione = "";
	String dichiarazione = "";
	String responsabile_procedimento = "";
	String note = "";
	String numero_copie = "";
	String luogo_partenza_trasporto = "";
	String nazione_partenza_trasporto = "";
	String data_partenza_trasporto = "";
	String ora_partenza_trasporto = "";
	String destinazione_trasporto = "";
	String nazione_destinazione_trasporto = "";
	String data_arrivo_trasporto = "";
	String ora_arrivo_trasporto = "";
	String certificato_trasporto = "";
	String data_certificato_trasporto = "";
	String luogo_rilascio_trasporto = "";
	String ore = "";
	String numCampioni = "";	
	String descrizione = "";
	String indirizzo_legale = "";
	String residenza_legale = "";
	String numero_legale = "";
	String idControllo;
	String mod;
	String gravi;
	
	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getDomicilio_digitale() {
		return domicilio_digitale;
	}


	public void setDomicilio_digitale(String domicilio_digitale) {
		this.domicilio_digitale = domicilio_digitale;
	}

	public String getIndirizzo_legale() {
		return indirizzo_legale;
	}


	public void setIndirizzo_legale(String indirizzo_legale) {
		this.indirizzo_legale = indirizzo_legale;
	}


	public String getResidenza_legale() {
		return residenza_legale;
	}


	public void setResidenza_legale(String residenza_legale) {
		this.residenza_legale = residenza_legale;
	}


	public String getNumero_legale() {
		return numero_legale;
	}


	public void setNumero_legale(String numero_legale) {
		this.numero_legale = numero_legale;
	}


	public String getMod() {
		return mod;
	}

	public String getNum_civico_rappresentante() {
		return num_civico_rappresentante;
	}


	public void setNum_civico_rappresentante(String num_civico_rappresentante) {
		this.num_civico_rappresentante = num_civico_rappresentante;
	}


	public void setMod(String mod) {
		this.mod = mod;
	}

	
	public String getIdControllo() {
		return idControllo;
	}


	public void setIdControllo(String idControllo) {
		this.idControllo = idControllo;
	}


	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}


	public String getServizio() {
		return servizio;
	}

	public void setDescrizione(String desc) {
		this.descrizione = desc;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}

	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getUo() {
		return uo;
	}


	public void setUo(String uo) {
		this.uo = uo;
	}


	public String getVia_amm() {
		return via_amm;
	}


	public void setVia_amm(String via_amm) {
		this.via_amm = via_amm;
	}


	public String getNome_presente_ispezione() {
		return nome_presente_ispezione;
	}


	public void setNome_presente_ispezione(String nome_presente_ispezione) {
		this.nome_presente_ispezione = nome_presente_ispezione;
	}


	public String getLuogo_nascita_presente_ispezione() {
		return luogo_nascita_presente_ispezione;
	}


	public void setLuogo_nascita_presente_ispezione(
			String luogo_nascita_presente_ispezione) {
		this.luogo_nascita_presente_ispezione = luogo_nascita_presente_ispezione;
	}


	public String getGiorno_presente_ispezione() {
		return giorno_presente_ispezione;
	}


	public void setGiorno_presente_ispezione(String giorno_presente_ispezione) {
		this.giorno_presente_ispezione = giorno_presente_ispezione;
	}


	public String getMese_presente_ispezione() {
		return mese_presente_ispezione;
	}


	public void setMese_presente_ispezione(String mese_presente_ispezione) {
		this.mese_presente_ispezione = mese_presente_ispezione;
	}


	public String getAnno_presente_ispezione() {
		return anno_presente_ispezione;
	}


	public void setAnno_presente_ispezione(String anno_presente_ispezione) {
		this.anno_presente_ispezione = anno_presente_ispezione;
	}


	public String getLuogo_residenza_presente_ispezione() {
		return luogo_residenza_presente_ispezione;
	}


	public void setLuogo_residenza_presente_ispezione(
			String luogo_residenza_presente_ispezione) {
		this.luogo_residenza_presente_ispezione = luogo_residenza_presente_ispezione;
	}


	public String getVia_ispezione() {
		return via_ispezione;
	}


	public void setVia_ispezione(String via_ispezione) {
		this.via_ispezione = via_ispezione;
	}


	public String getNum_civico_presente_ispezione() {
		return num_civico_presente_ispezione;
	}


	public void setNum_civico_presente_ispezione(
			String num_civico_presente_ispezione) {
		this.num_civico_presente_ispezione = num_civico_presente_ispezione;
	}


	public String getDoc_identita_presente_ispezione() {
		return doc_identita_presente_ispezione;
	}


	public void setDoc_identita_presente_ispezione(
			String doc_identita_presente_ispezione) {
		this.doc_identita_presente_ispezione = doc_identita_presente_ispezione;
	}


	public String getStrumenti_ispezione() {
		return strumenti_ispezione;
	}


	public void setStrumenti_ispezione(String strumenti_ispezione) {
		this.strumenti_ispezione = strumenti_ispezione;
	}


	public String getDichiarazione() {
		return dichiarazione;
	}


	public void setDichiarazione(String dichiarazione) {
		this.dichiarazione = dichiarazione;
	}


	public String getResponsabile_procedimento() {
		return responsabile_procedimento;
	}


	public void setResponsabile_procedimento(String responsabile_procedimento) {
		this.responsabile_procedimento = responsabile_procedimento;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getNumero_copie() {
		return numero_copie;
	}


	public void setNumero_copie(String numero_copie) {
		this.numero_copie = numero_copie;
	}


	public String getLuogo_partenza_trasporto() {
		return luogo_partenza_trasporto;
	}


	public void setLuogo_partenza_trasporto(String luogo_partenza_trasporto) {
		this.luogo_partenza_trasporto = luogo_partenza_trasporto;
	}


	public String getNazione_partenza_trasporto() {
		return nazione_partenza_trasporto;
	}


	public void setNazione_partenza_trasporto(String nazione_partenza_trasporto) {
		this.nazione_partenza_trasporto = nazione_partenza_trasporto;
	}


	public String getData_partenza_trasporto() {
		return data_partenza_trasporto;
	}


	public void setData_partenza_trasporto(String data_partenza_trasporto) {
		this.data_partenza_trasporto = data_partenza_trasporto;
	}


	public String getOra_partenza_trasporto() {
		return ora_partenza_trasporto;
	}


	public void setOra_partenza_trasporto(String ora_partenza_trasporto) {
		this.ora_partenza_trasporto = ora_partenza_trasporto;
	}


	public String getDestinazione_trasporto() {
		return destinazione_trasporto;
	}


	public void setDestinazione_trasporto(String destinazione_trasporto) {
		this.destinazione_trasporto = destinazione_trasporto;
	}


	public String getNazione_destinazione_trasporto() {
		return nazione_destinazione_trasporto;
	}


	public void setNazione_destinazione_trasporto(
			String nazione_destinazione_trasporto) {
		this.nazione_destinazione_trasporto = nazione_destinazione_trasporto;
	}


	public String getData_arrivo_trasporto() {
		return data_arrivo_trasporto;
	}


	public void setData_arrivo_trasporto(String data_arrivo_trasporto) {
		this.data_arrivo_trasporto = data_arrivo_trasporto;
	}


	public String getOra_arrivo_trasporto() {
		return ora_arrivo_trasporto;
	}


	public void setOra_arrivo_trasporto(String ora_arrivo_trasporto) {
		this.ora_arrivo_trasporto = ora_arrivo_trasporto;
	}


	public String getCertificato_trasporto() {
		return certificato_trasporto;
	}


	public void setCertificato_trasporto(String certificato_trasporto) {
		this.certificato_trasporto = certificato_trasporto;
	}


	public String getData_certificato_trasporto() {
		return data_certificato_trasporto;
	}


	public void setData_certificato_trasporto(String data_certificato_trasporto) {
		this.data_certificato_trasporto = data_certificato_trasporto;
	}


	public String getLuogo_rilascio_trasporto() {
		return luogo_rilascio_trasporto;
	}


	public void setLuogo_rilascio_trasporto(String luogo_rilascio_trasporto) {
		this.luogo_rilascio_trasporto = luogo_rilascio_trasporto;
	}


	public String getOre() {
		return ore;
	}


	public void setOre(String ore) {
		this.ore = ore;
	}

	public String getGravi() {
		return gravi;
	}


	public void setGravi(String gravi) {
		this.gravi = gravi;
	}

	public String getNumCampioni() {
		return numCampioni;
	}


	public void setNumCampioni(String numCampioni) {
		this.numCampioni = numCampioni;
	}

	public OrganizationUtente(){}
	
	
	public OrganizationUtente (ResultSet rs_utente ) throws SQLException{
		
		if(rs_utente.next()) {
			
			servizio = rs_utente.getString("servizio");
			
			if(servizio == null){
				servizio = "";
			}
			
			flag = rs_utente.getString("flag");
			uo = rs_utente.getString("uo");
			
			if(uo == null){
				uo = "";
			}
			
			via_amm = rs_utente.getString("via_amm");
			
			if(via_amm == null){
				via_amm = "";
			}
			
			nome_presente_ispezione = rs_utente.getString("presente_ispezione");
			
			if(nome_presente_ispezione == null){
				nome_presente_ispezione = "";
			}
			
			luogo_nascita_presente_ispezione = rs_utente.getString("luogo_nascita");
			
			if(luogo_nascita_presente_ispezione == null){
				luogo_nascita_presente_ispezione = "";
			}
			
			giorno_presente_ispezione = rs_utente.getString("giorno_nascita");
			
			if(giorno_presente_ispezione == null){
				giorno_presente_ispezione = "";
			}
			
			mese_presente_ispezione = rs_utente.getString("mese_nascita");
			
			if(mese_presente_ispezione == null){
				mese_presente_ispezione = "";
			}
			
			anno_presente_ispezione = rs_utente.getString("anno_nascita");
			
			if(anno_presente_ispezione == null){
				anno_presente_ispezione = "";
			}
			
			luogo_residenza_presente_ispezione = rs_utente.getString("luogo_residenza_ispezione");
			
			if(luogo_residenza_presente_ispezione == null){
				luogo_residenza_presente_ispezione = "";
			}

			descrizione = rs_utente.getString("desc_risoluzione");
			
			if(descrizione == null){
				descrizione = "";
			}

			//indirizzo_legale = rs_utente.getString("indirizzo_legale");
			//residenza_legale = rs_utente.getString("residenza_legale");
	        //numero_legale = rs_utente.getString("numero_legale");
			residenza_legale = rs_utente.getString("luogo_residenza");

			if(residenza_legale == null){
				residenza_legale = "";
			}
					
			indirizzo_legale = rs_utente.getString("indirizzo_legale_rappresentante");
			
			if(indirizzo_legale == null){
				indirizzo_legale = "";
			}
			
			numero_legale = rs_utente.getString("num_civico_rappresentante");
			
			if(numero_legale == null){
				numero_legale = "";
			}
			
			via_ispezione = rs_utente.getString("via_ispezione");
			
			if(via_ispezione == null){
				via_ispezione = "";
			}
			
			num_civico_presente_ispezione = rs_utente.getString("civico_ispezione");
			
			if(num_civico_presente_ispezione == null){
				num_civico_presente_ispezione = "";
			}
			
			doc_identita_presente_ispezione = rs_utente.getString("doc_identita");
			
			if(doc_identita_presente_ispezione == null){
				doc_identita_presente_ispezione = "";
			}
			
			strumenti_ispezione = rs_utente.getString("strumenti_ispezione");
			
			if(strumenti_ispezione == null){
				strumenti_ispezione = "";
			}
			
			dichiarazione = rs_utente.getString("dichiarazione");
			
			if(dichiarazione == null){
				dichiarazione = "";
			}
			
			responsabile_procedimento = rs_utente.getString("responsabile");
			
			if(responsabile_procedimento == null){
				responsabile_procedimento = "";
			}
			
			note = rs_utente.getString("note");
			
			if(note == null){
				note = "";
			}
			
			numero_copie = rs_utente.getString("n_copie");
			
			if(numero_copie == null){
				numero_copie = "";
			}
			
			luogo_partenza_trasporto = rs_utente.getString("luogo_partenza_trasporto");
			
			if(luogo_partenza_trasporto == null){
				luogo_partenza_trasporto = "";
			}
			
			nazione_partenza_trasporto = rs_utente.getString("nazione_partenza_trasporto");
			
			if(nazione_partenza_trasporto == null){
				nazione_partenza_trasporto = "";
			}
			
			data_partenza_trasporto  = rs_utente.getString("data_partenza_trasporto");
			
			if(data_partenza_trasporto == null){
				data_partenza_trasporto = "";
			}
			
			ora_partenza_trasporto = rs_utente.getString("ora_partenza_trasporto");
			
			if(ora_partenza_trasporto == null){
				ora_partenza_trasporto = "";
			}
			
			destinazione_trasporto = rs_utente.getString("destinazione_trasporto");
			
			if(destinazione_trasporto == null){
				destinazione_trasporto = "";
			}
			
			nazione_destinazione_trasporto = rs_utente.getString("nazione_destinazione_trasporto");
			
			if(nazione_destinazione_trasporto == null){
				nazione_destinazione_trasporto = "";
			}
			
			data_arrivo_trasporto = rs_utente.getString("data_arrivo_trasporto");
			
			if(nazione_destinazione_trasporto == null){
				nazione_destinazione_trasporto = "";
			}
			
			ora_arrivo_trasporto = rs_utente.getString("ora_arrivo_trasporto");
			
			if(ora_arrivo_trasporto == null){
				ora_arrivo_trasporto = "";
			}
			
			certificato_trasporto = rs_utente.getString("certificato_trasporto");
			
			if(certificato_trasporto == null){
				certificato_trasporto = "";
			}
			
			data_certificato_trasporto = rs_utente.getString("data_certificato_trasporto");
			
			if(data_certificato_trasporto == null){
				data_certificato_trasporto = "";
			}
			
			luogo_rilascio_trasporto = rs_utente.getString("luogo_rilascio_trasporto");
			
			if(luogo_rilascio_trasporto == null){
				luogo_rilascio_trasporto = "";
			}
			
			ore = rs_utente.getString("ore");
			
			if(ore == null){
				ore = "";
			}
			
			mail = rs_utente.getString("mail");
			
			if(mail == null){
				mail = "";
			}
			
			gravi = rs_utente.getString("gravi"); 
			
			if(gravi == null){
				gravi = "gravi_1= ;gravi_2= ;gravi_3= ;gravi_4= ;gravi_5= ;gravi_6= ;gravi_7= ;gravi_8= ;gravi_9= ;gravi_10= ;gravi_11= ;gravi_12= ;gravi_13= ;";
			}
			
			numCampioni = rs_utente.getString("num_campioni");
			
			if(numCampioni == null){
				numCampioni = "";
			}
		}

	}	

	public void gestioneDatiUtente(Connection db, int idControllo, int orgId){
	
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		String sql_update = null;
		String sql_rapp = null;
		PreparedStatement pst_rapp = null;
		PreparedStatement pst_update = null;

		int i = 0;
		int num = 0;

		try {
		
			int k = 0, count = 0;
			sql = "select count(*) as countrecord from dati_utente_controlliufficiali where id_controllo = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(++i, idControllo);
			rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt("countrecord");
			}
			// Significa che ci sono tuple nella tabella
			if (count > 0) {
				i = 0;
				sql = "UPDATE dati_utente_controlliufficiali "
						+ "SET servizio = ?, " + " id_controllo = ?, "
						+ " uo = ?, " + " via_amm = ?, "
						+ " presente_ispezione = ?, " + " luogo_nascita = ?, "
						+ " giorno_nascita = ?, " + " mese_nascita = ?, "
						+ " anno_nascita = ?, " + " luogo_residenza_ispezione = ?, "
						+ " luogo_residenza = ?, "
						+ " indirizzo_legale_rappresentante = ?, " + " num_civico_rappresentante = ?, "
						+ " via_ispezione = ?, " + " civico_ispezione = ?, "
						+ " doc_identita = ?, " + " strumenti_ispezione = ?, " + " "
						+ " desc_risoluzione = ?, " + " mail = ?, "
						//+ " desc_risoluzione_iniz = ?, "
						+ " dichiarazione = ?, " + " responsabile = ?, "
						+ " note = ?, " + " n_copie = ?, "
						+ " luogo_partenza_trasporto = ?, "
						+ " nazione_partenza_trasporto = ?, "
						+ " data_partenza_trasporto = ?, "
						+ " ora_partenza_trasporto = ?, "
						+ " destinazione_trasporto = ?, "
						+ " nazione_destinazione_trasporto = ?, "
						+ " data_arrivo_trasporto = ?, "
						+ " ora_arrivo_trasporto = ?, "
						+ " certificato_trasporto = ?, "
						+ " data_certificato_trasporto = ?, "
						+ " luogo_rilascio_trasporto = ?, " + " ore = ?, "
						+ " num_campioni = ?, gravi = ?, " + " flag = ? "
						+ " where id_controllo = ? ";
				pst = db.prepareStatement(sql);
				pst.setString(++i, servizio);
				pst.setInt(++i, idControllo);
				pst.setString(++i, uo);
				pst.setString(++i, via_amm);
				pst.setString(++i, nome_presente_ispezione);
				pst.setString(++i, luogo_nascita_presente_ispezione);
				pst.setString(++i, giorno_presente_ispezione);
				pst.setString(++i, mese_presente_ispezione);
				pst.setString(++i, anno_presente_ispezione);
				pst.setString(++i, luogo_residenza_presente_ispezione);
				pst.setString(++i, residenza_legale);
				pst.setString(++i, indirizzo_legale);
				pst.setString(++i, numero_legale);				
				pst.setString(++i, via_ispezione);
				pst.setString(++i, num_civico_presente_ispezione);
				pst.setString(++i, doc_identita_presente_ispezione);
				pst.setString(++i, strumenti_ispezione);
				pst.setString(++i, descrizione);
				pst.setString(++i,mail);
				pst.setString(++i, dichiarazione);
				pst.setString(++i, responsabile_procedimento);
				pst.setString(++i, note);
				pst.setString(++i, numero_copie);
				pst.setString(++i, luogo_partenza_trasporto);
				pst.setString(++i, nazione_partenza_trasporto);
				pst.setString(++i, data_partenza_trasporto);
				pst.setString(++i, ora_partenza_trasporto);
				pst.setString(++i, destinazione_trasporto);
				pst.setString(++i, nazione_destinazione_trasporto);
				pst.setString(++i, data_arrivo_trasporto);
				pst.setString(++i, ora_arrivo_trasporto);
				pst.setString(++i, certificato_trasporto);
				pst.setString(++i, data_certificato_trasporto);
				pst.setString(++i, luogo_rilascio_trasporto);
				pst.setString(++i, ore);
				pst.setString(++i, numCampioni);
				pst.setString(++i, gravi);
				pst.setString(++i, mod);
				pst.setInt(++i, idControllo);
				
				
				num = pst.executeUpdate();

			} // chiudo if
			else {
				int j = 0;
			
				sql = "INSERT INTO dati_utente_controlliufficiali (servizio, id_controllo, "
						+ " uo, via_amm, presente_ispezione, luogo_nascita, giorno_nascita, "
						+ " mese_nascita, anno_nascita, luogo_residenza_ispezione, luogo_residenza, indirizzo_legale_rappresentante," 
						+ " num_civico_rappresentante, via_ispezione, civico_ispezione, "
						+ " doc_identita, strumenti_ispezione, desc_risoluzione, mail, dichiarazione, responsabile, "
						+ " note, n_copie, luogo_partenza_trasporto, nazione_partenza_trasporto, data_partenza_trasporto, ora_partenza_trasporto, "
						+ " destinazione_trasporto, nazione_destinazione_trasporto, data_arrivo_trasporto, ora_arrivo_trasporto, "
						+ " certificato_trasporto, data_certificato_trasporto, luogo_rilascio_trasporto, ore, num_campioni, gravi, flag )"
						+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = db.prepareStatement(sql);
				pst.setString(++j, servizio);
				pst.setInt(++j, idControllo);
				pst.setString(++j, uo);
				pst.setString(++j, via_amm);
				pst.setString(++j, nome_presente_ispezione);
				pst.setString(++j, luogo_nascita_presente_ispezione);
				pst.setString(++j, giorno_presente_ispezione);
				pst.setString(++j, mese_presente_ispezione);
				pst.setString(++j, anno_presente_ispezione);
				pst.setString(++j, luogo_residenza_presente_ispezione);
				pst.setString(++j, residenza_legale);
				pst.setString(++j, indirizzo_legale);
				pst.setString(++j, numero_legale);				
				pst.setString(++j, via_ispezione);
				pst.setString(++j, num_civico_presente_ispezione);
				pst.setString(++j, doc_identita_presente_ispezione);
				pst.setString(++j, strumenti_ispezione);
				pst.setString(++j, descrizione);
				pst.setString(++j, mail);
//				pst.setString(++j, desc_iniz);
				pst.setString(++j, dichiarazione);
				pst.setString(++j, responsabile_procedimento);
				pst.setString(++j, note);
				pst.setString(++j, numero_copie);
				pst.setString(++j, luogo_partenza_trasporto);
				pst.setString(++j, nazione_partenza_trasporto);
				pst.setString(++j, data_partenza_trasporto);
				pst.setString(++j, ora_partenza_trasporto);
				pst.setString(++j, destinazione_trasporto);
				pst.setString(++j, nazione_destinazione_trasporto);
				pst.setString(++j, data_arrivo_trasporto);
				pst.setString(++j, ora_arrivo_trasporto);
				pst.setString(++j, certificato_trasporto);
				pst.setString(++j, data_certificato_trasporto);
				pst.setString(++j, luogo_rilascio_trasporto);
				pst.setString(++j, ore);
				pst.setString(++j, numCampioni);
				pst.setString(++j, gravi);
				pst.setString(++j, mod);
				pst.execute();

			}

			// Estrarre la tipologia
			int tipologia = 0;
			int m = 0;
			sql = "select tipologia from organization where org_id = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(++m, orgId);
			rs = pst.executeQuery();
			if (rs.next()) {
				tipologia = rs.getInt("tipologia");
			}

			k = 0;
			
			if (tipologia != 3) {

				// update dati utente organization
				sql_update = "UPDATE organization "
						+ "SET city_legale_rapp = ?, "
						+ " address_legale_rapp = ? " + " where org_id = ? ";
				pst_update = db.prepareStatement(sql_update);
				pst_update.setString(++k, residenza_legale);
				pst_update.setString(++k, indirizzo_legale + " " + numero_legale);
				pst_update.setInt(++k, orgId);
				pst_update.executeUpdate();

			} else {

				// update dati utente ticket
				sql_update = "UPDATE ticket " + " SET city_legale_rapp = ?, "
						+ " address_legale_rapp = ? "
						+ " where org_id = ? and tipologia = 4";
				pst_update = db.prepareStatement(sql_update);
				pst_update.setString(++k, residenza_legale);
				pst_update.setString(++k, indirizzo_legale + " " + numero_legale);
				pst_update.setInt(++k, orgId);

				pst_update.executeUpdate();

			}
			
			//Gestione domicilio digitale
			// update dati utente organization
			
			int l = 0;
			String sql_up = null;
			PreparedStatement pst_up;
			
			sql_up = "UPDATE organization "
					+ "SET domicilio_digitale = ? "
					+ "where org_id = ? ";
			pst_up = db.prepareStatement(sql_up);
			pst_up.setString(++l, domicilio_digitale);
			pst_up.setInt(++l, orgId);
			pst_up.executeUpdate();

			if (mod.equals("false")) {

				int k2 = 0;
				int j = 0;
				sql_update = "UPDATE ticket " + "SET flag_mod5 = ? "
						+ " where ticketid = ? ";
				pst_update = db.prepareStatement(sql_update);
				pst_update.setString(++k2, mod);
				pst_update.setInt(++k2, idControllo);
				pst_update.executeUpdate();

			}
		
		}catch (Exception e) {
			e.printStackTrace();

		} 


	}
}
