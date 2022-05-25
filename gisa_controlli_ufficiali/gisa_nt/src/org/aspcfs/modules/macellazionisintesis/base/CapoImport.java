/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionisintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.aspcfs.modules.sintesis.base.SintesisStabilimento;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.utils.DateUtils;



//AGGIUSTAMENTI:

//SPECIE: "BOVINO" CONVERTITO A 1; "BUFALINO" CONVERTITO A 5
//SESSO: "M" CONVERTITO A TRUE, "F" CONVERTITO A FALSE
//VINCOLO SANITARIO: "S" CONVERTITO A TRUE, "N" CONVERTITO A FALSE
// BSE: "S" CONVERTITO A 1, "N" CONVERTITO A -1
//DATE: CONVERTITE DA YYYY/MM/DD A DD/MM/YYYY
// SALTO LA PRIMA RIGA (INTESTAZIONE)

public class CapoImport {
	
private int idImport = -1;	
private int numeroRiga = -1;
private int id_macello = -1;
private String stato_macellazione = "Incompleto: Inseriti solo i dati sul controllo documentale";
private int progressivo_macellazione = 0;
private String vam_esito="";
private String cd_codice_azienda;
private String cd_codice_azienda_provenienza;
private String cd_matricola;
private int cd_specie = -1;
private boolean cd_maschio = false;
private Timestamp cd_data_nascita;
private int cd_categoria_rischio = -1;
private boolean cd_vincolo_sanitario;
private String cd_vincolo_sanitario_motivo;
private Timestamp cd_data_mod4;
private Timestamp cd_data_arrivo_macello;
private int cd_bse;
private Timestamp bse_data_prelievo;
private String bse_esito;
private boolean cd_info_catena_alimentare = true;

private int id_veterinario_1 =-1;
private int id_veterinario_2 =-1;
private int id_veterinario_3 =-1;

private String cd_veterinario_1 ="";
private String cd_veterinario_2 ="";
private String cd_veterinario_3 ="";

private boolean importabile = true;
private String erroreImport = "";

private Timestamp vpm_data=null;
private int cd_seduta_macellazione = 0;
private int vpm_esito = -1;

private int id = -1;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public int getVpm_esito() {
	return vpm_esito;
}
public void setVpm_esito(int vpm_esito) {
	this.vpm_esito = vpm_esito;
}

public int getId_macello() {
	return id_macello;
}
public void setId_macello(int id_macello) {
	this.id_macello = id_macello;
}
public void setId_macello(String id_macello) {
	try{ this.id_macello = Integer.parseInt(id_macello);}
	catch(Exception e){} 
}
public String getStato_macellazione() {
	return stato_macellazione;
}
public void setStato_macellazione(String stato_macellazione) {
	this.stato_macellazione = stato_macellazione;
}
public int getProgressivo_macellazione() {
	return progressivo_macellazione;
}
public void setProgressivo_macellazione(int progressivo_macellazione) {
	this.progressivo_macellazione = progressivo_macellazione;
}
public String getVam_esito() {
	return vam_esito;
}
public void setVam_esito(String vam_esito) {
	this.vam_esito = vam_esito;
}
public String getCd_codice_azienda() {
	return cd_codice_azienda;
}
public void setCd_codice_azienda(String cd_codice_azienda) {
	this.cd_codice_azienda = cd_codice_azienda;
}
public String getCd_codice_azienda_provenienza() {
	return cd_codice_azienda_provenienza;
}
public void setCd_codice_azienda_provenienza(String cd_codice_azienda_provenienza) {
	this.cd_codice_azienda_provenienza = cd_codice_azienda_provenienza;
}
public String getCd_matricola() {
	return cd_matricola;
}
public void setCd_matricola(String cd_matricola) {
	this.cd_matricola = cd_matricola.trim();
}
public int getCd_specie() {
	return cd_specie;
}
public void setCd_specie(int cd_specie) {
	this.cd_specie = cd_specie;
}
public void setCd_specie(String cd_specie) {
	if ("BOVINO".equals(cd_specie))
		this.cd_specie = 1;
	else if ("BUFALINO".equals(cd_specie))
		this.cd_specie = 5;
}
public boolean isCd_maschio() {
	return cd_maschio;
}
public void setCd_maschio(boolean cd_maschio) {
	this.cd_maschio = cd_maschio;
}
public void setCd_maschio(String cd_maschio) {
	if ("M".equals(cd_maschio))
		this.cd_maschio = true;
	else 
		this.cd_maschio = false;
}
public Timestamp getCd_data_nascita() {
	return cd_data_nascita;
}
public void setCd_data_nascita(Timestamp cd_data_nascita) {
	this.cd_data_nascita = cd_data_nascita;
}
public void setCd_data_nascita(String cd_data_nascita) {
	this.cd_data_nascita = 	DateUtils.parseTimestampString(
			cd_data_nascita, "yyyy/MM/dd");
}
public int getCd_categoria_rischio() {
	return cd_categoria_rischio;
}
public void setCd_categoria_rischio(int cd_categoria_rischio) {
	this.cd_categoria_rischio = cd_categoria_rischio;
}
public void setCd_categoria_rischio(String cd_categoria_rischio) {
	try{
		this.cd_categoria_rischio = Integer.parseInt(cd_categoria_rischio);
	}
catch (Exception e){}	
}

public boolean isCd_vincolo_sanitario() {
	return cd_vincolo_sanitario;
}
public void setCd_vincolo_sanitario(boolean cd_vincolo_sanitario) {
	this.cd_vincolo_sanitario = cd_vincolo_sanitario;
}
public void setCd_vincolo_sanitario(String cd_vincolo_sanitario) {
	try {
		this.cd_vincolo_sanitario = Boolean.valueOf(cd_vincolo_sanitario);
	} catch (Exception e) {} 
}
public String getCd_vincolo_sanitario_motivo() {
	return cd_vincolo_sanitario_motivo;
}
public void setCd_vincolo_sanitario_motivo(String cd_vincolo_sanitario_motivo) {
	this.cd_vincolo_sanitario_motivo = cd_vincolo_sanitario_motivo;
}
public Timestamp getCd_data_mod4() {
	return cd_data_mod4;
}
public void setCd_data_mod4(Timestamp cd_data_mod4) {
	this.cd_data_mod4 = cd_data_mod4;
}
public void setCd_data_mod4(String cd_data_mod4) {
	this.cd_data_mod4 = 	DateUtils.parseTimestampString(
			cd_data_mod4, "yyyy/MM/dd");
}
public Timestamp getCd_data_arrivo_macello() {
	return cd_data_arrivo_macello;
}
public void setCd_data_arrivo_macello(Timestamp cd_data_arrivo_macello) {
	this.cd_data_arrivo_macello = cd_data_arrivo_macello;
}
public void setCd_data_arrivo_macello(String cd_data_arrivo_macello) {
	this.cd_data_arrivo_macello = 	DateUtils.parseTimestampString(
			cd_data_arrivo_macello, "yyyy/MM/dd");
}
public int getCd_bse() {
	return cd_bse;
}
public void setCd_bse(int cd_bse) {
	this.cd_bse = cd_bse;
} 
public void setCd_bse(String cd_bse) {
	if ("S".equals(cd_bse))
		this.cd_bse = 1;
	else if ("N".equals(cd_bse))
		this.cd_bse = -1;
}
public Timestamp getBse_data_prelievo() {
	return bse_data_prelievo;
}
public void setBse_data_prelievo(Timestamp bse_data_prelievo) {
	this.bse_data_prelievo = bse_data_prelievo;
}
public void setBse_data_prelievo(String bse_data_prelievo) {
	this.bse_data_prelievo = 	DateUtils.parseTimestampString(
			bse_data_prelievo, "yyyy/MM/dd");
}	


/**
 * @return the bse_esito
 */
public String getBse_esito() {
	return bse_esito;
}
/**
 * @param bse_esito the bse_esito to set
 */
public void setBse_esito(String bse_esito) {
	this.bse_esito = bse_esito;
}
public CapoImport(){
	
}

public CapoImport(String[] valori){

	setCd_codice_azienda(valori[0]);
	setCd_codice_azienda_provenienza(valori[1]);
	setCd_matricola(valori[2]);
	setCd_specie(valori[3]);
	setCd_maschio (valori[4]);
	setCd_data_nascita (valori[5]);
	setCd_categoria_rischio(valori[7]);
	setCd_vincolo_sanitario(valori[8]);
	setCd_vincolo_sanitario_motivo(valori[9]);
	setCd_data_mod4 (valori[10]);
	setCd_data_arrivo_macello (valori[13]);
	setCd_bse (valori[14]);
	setBse_data_prelievo(valori[15]);
	setBse_esito(valori[16]);
	setId_veterinario_1(valori[24]);
	setId_veterinario_2(valori[24]);
	setId_veterinario_3(valori[24]);
	setAltriVeterinari(valori[24]);
	
	
}
public boolean isCd_info_catena_alimentare() {
	return cd_info_catena_alimentare;
}
public void setCd_info_catena_alimentare(boolean cd_info_catena_alimentare) {
	this.cd_info_catena_alimentare = cd_info_catena_alimentare;
}

public Timestamp getVpm_data() {
	return vpm_data;
}
public void setVpm_data(Timestamp vpm_data) {
	this.vpm_data = vpm_data;
}

public void setVpm_data(String vpm_data) {
	this.vpm_data = 	DateUtils.parseTimestampString(
			vpm_data, "dd/MM/yyyy");
}	

public String getVpm_dataString() {
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	Date date = vpm_data;
	String time = sdf.format(date);
	return time;
	
}
public int getCd_seduta_macellazione() {
	return cd_seduta_macellazione;
}
public void setCd_seduta_macellazione(int cd_seduta_macellazione) {
	this.cd_seduta_macellazione = cd_seduta_macellazione;
}
public void setCd_seduta_macellazione(String cd_seduta_macellazione) {
	try {
		this.cd_seduta_macellazione = Integer.parseInt(cd_seduta_macellazione);
		}
	catch (Exception e){}
	
}
public void insert(Connection db) throws SQLException{
	
	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO m_capi("
			+ "id_macello,  stato_macellazione,  vpm_data, cd_seduta_macellazione, progressivo_macellazione, "
			+ "vam_esito, cd_codice_azienda, cd_codice_azienda_provenienza, cd_matricola, cd_specie , "
			+ "cd_maschio ,cd_data_nascita ,  cd_categoria_rischio, cd_vincolo_sanitario, cd_vincolo_sanitario_motivo ,"
			+ "cd_data_mod4, cd_data_arrivo_macello , cd_bse , bse_data_prelievo, bse_esito, "
			+ "cd_info_catena_alimentare, cd_veterinario_1, cd_veterinario_2, cd_veterinario_3, id_import, "
			+ "solo_cd "
			+ ") values ("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ? ,?, ?, ?, "
			+ "?, ?, ?, ? , ?, "
			+ "? "
			+ "); ");
	int i = 0;
	PreparedStatement pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, this.getId_macello() );
	pst.setString(++i, this.getStato_macellazione());
	pst.setTimestamp(++i, this.getVpm_data());
	pst.setInt(++i, this.getCd_seduta_macellazione() );
	pst.setInt(++i, this.getProgressivo_macellazione());
	pst.setString(++i, this.getVam_esito());
	pst.setString(++i, this.getCd_codice_azienda());
	pst.setString(++i, this.getCd_codice_azienda_provenienza());
	pst.setString(++i, this.getCd_matricola());
	pst.setInt(++i, this.getCd_specie());
	pst.setBoolean(++i, this.isCd_maschio());
	pst.setTimestamp(++i, this.getCd_data_nascita());
	pst.setInt(++i, this.getCd_categoria_rischio());
	pst.setBoolean(++i, this.isCd_vincolo_sanitario());
	pst.setString(++i, this.getCd_vincolo_sanitario_motivo());
	pst.setTimestamp(++i, this.getCd_data_mod4());
	pst.setTimestamp(++i, this.getCd_data_arrivo_macello());
	pst.setInt(++i, this.getCd_bse());
	pst.setTimestamp(++i, this.getBse_data_prelievo());
	pst.setString(++i, this.getBse_esito());
	pst.setBoolean(++i, this.isCd_info_catena_alimentare());
	pst.setString(++i, this.getCd_veterinario_1());
	pst.setString(++i, this.getCd_veterinario_2());
	pst.setString(++i, this.getCd_veterinario_3());
	pst.setInt(++i, this.getIdImport());
	pst.setBoolean(++i, true);

	pst.execute();
	//pst.close();
	
 }

	
public boolean controllaUnivocitaMatricola (Connection db){
	boolean nonEsiste = true;
	StringBuffer sql = new StringBuffer();
	sql.append("select * from m_capi where trashed_date is null and cd_matricola ilike ?");
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
	pst.setString(1, this.getCd_matricola() );
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		nonEsiste=false;
		this.erroreImport = "Matricola gia' presente in banca dati";
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return nonEsiste;
}

public boolean controllaUnivocitaMatricolaNelFile (ArrayList<String> matricole){
	boolean nonEsiste = true;
	
	if (presentiMatricoleDuplicate(this.getCd_matricola(), matricole)){
		nonEsiste = false;
		this.erroreImport = "Matricola duplicata nel file.";
	}
	
	return nonEsiste;
}

public boolean controllaValiditaRiga (Connection db, ArrayList<String> matricole){
	
	boolean flagCampiObbligatori = true; 	//CI SONO TUTTI I CAMPI OBBLIGATORI?
	boolean flagUnivocitaMatricola = true; 	//LA MATRICOLA E' UNIVOCA IN GISA?
	boolean flagUnivocitaMatricolaNelFile = true;	//LA MATRICOLA E' UNIVOCA NEL FILE?
	
	flagCampiObbligatori = controllaCampiObbligatori();
	
	if (flagCampiObbligatori){
		flagUnivocitaMatricola = controllaUnivocitaMatricola(db);
		if (flagUnivocitaMatricola){
			flagUnivocitaMatricolaNelFile = controllaUnivocitaMatricolaNelFile(matricole);
		}
	}
	
	if (!flagCampiObbligatori || !flagUnivocitaMatricola || !flagUnivocitaMatricolaNelFile){
		this.importabile = false;
		return false;
	}
	return true;
}


public boolean controllaCampiObbligatori (){
	boolean esito = true;
	
	if (!stringaOk(this.getCd_matricola())){
		esito = false;
		this.erroreImport+="Matricola mancante.";
	}
	if (!stringaOk(this.getCd_codice_azienda_provenienza())){
		esito = false;
		this.erroreImport+="Codice azienda provenienza mancante.";
	}
	if (!numeroOk(this.getCd_specie())){
		esito = false;
		this.erroreImport+="Specie mancante.";
	}
	if (!dataOk(this.getCd_data_nascita())){
		esito = false;
		this.erroreImport+="Data di nascita mancante.";
	}
	if (!dataOk(this.getCd_data_arrivo_macello())){
		esito = false;
		this.erroreImport+="Data di arrivo al macello mancante.";
	}
	if (!stringaOk(this.getCd_veterinario_1())){
		esito = false;
		this.erroreImport+="Veterinario addetto al controllo mancante.";
	}
		return esito;
}


private boolean stringaOk (String stringa){
	if (stringa!=null && !stringa.equals("") && !stringa.equals("null"))
		return true;
	return false;
}
private boolean numeroOk (int numero){
	if (numero>-1)
		return true;
	return false;
}
private boolean dataOk (Timestamp data){
	if (data!=null)
		return true;
	return false;
}

public String getErroreImport() {
	return erroreImport;
}
public void setErroreImport(String erroreImport) {
	this.erroreImport = erroreImport;
}
public String getCd_veterinario_1() {
	return cd_veterinario_1;
}
public void setCd_veterinario_1(String cd_veterinario_1) {
	this.cd_veterinario_1 = cd_veterinario_1;
}
public String getCd_veterinario_2() {
	return cd_veterinario_2;
}
public void setCd_veterinario_2(String cd_veterinario_2) {
	this.cd_veterinario_2 = cd_veterinario_2;
}
public String getCd_veterinario_3() {
	return cd_veterinario_3;
}
public void setCd_veterinario_3(String cd_veterinario_3) {
	this.cd_veterinario_3 = cd_veterinario_3;
}
public int getId_veterinario_1() {
	return id_veterinario_1;
}
public void setId_veterinario_1(int id_veterinario_1) {
	this.id_veterinario_1 = id_veterinario_1;
}
public int getId_veterinario_2() {
	return id_veterinario_2;
}
public void setId_veterinario_2(int id_veterinario_2) {
	this.id_veterinario_2 = id_veterinario_2;
}
public int getId_veterinario_3() {
	return id_veterinario_3;
}
public void setId_veterinario_3(int id_veterinario_3) {
	this.id_veterinario_3 = id_veterinario_3;
}

public void setId_veterinario_1(String listaVet) {
	try {
	String uno = listaVet.split("/")[0];
	this.id_veterinario_1 = Integer.parseInt(uno);
	}
	catch (Exception e){}
}

public void setId_veterinario_2(String listaVet) {
	try {
	String due = listaVet.split("/")[1];
	this.id_veterinario_2 = Integer.parseInt(due);
	}
	catch (Exception e){}
}

public void setId_veterinario_3(String listaVet) {
	try {
	String tre = listaVet.split("/")[2];
	this.id_veterinario_3 = Integer.parseInt(tre);
	}
	catch (Exception e){}
}

public void setAltriVeterinari(String listaVet) {
	try {
	String lista[] = listaVet.split("/");
	if (lista.length>3)
		this.erroreImport += "Veterinari addetti al controllo oltre il terzo ignorati nell'import.";
	}
	catch (Exception e){}
}

public void setListaVeterinari (Connection db){
	if (this.id_veterinario_1>0)
		this.cd_veterinario_1 = recuperaVeterinario(db, id_veterinario_1);
	if (this.id_veterinario_2>0)
		this.cd_veterinario_2 = recuperaVeterinario(db, id_veterinario_2);
	if (this.id_veterinario_3>0)
		this.cd_veterinario_3 = recuperaVeterinario(db, id_veterinario_3);
}

private String recuperaVeterinario(Connection db, int idVeterinario){
	StringBuffer sql = new StringBuffer();
	String nome ="";
	sql.append("select c.namefirst, c.namelast from contact c left join access a on a.contact_id = c.contact_id where a.user_id = ?");
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
		pst.setInt(1, idVeterinario );
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			nome=rs.getString("namelast") + " "+ rs.getString("namefirst");
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return nome;
}
public boolean isImportabile() {
	return importabile;
}
public void setImportabile(boolean importabile) {
	this.importabile = importabile;
}
public int getIdImport() {
	return idImport;
}
public void setIdImport(int idImport) {
	this.idImport = idImport;
}


public boolean presentiMatricoleDuplicate(String str, ArrayList<String> list){
	int count = 0;
    for(String i : list){
        if(i.equalsIgnoreCase(str)){
        	count++;
        	if (count>1)
        		 return true;
        }
    }
    return false;
}
public int getNumeroRiga() {
	return numeroRiga;
}
public void setNumeroRiga(int numeroRiga) {
	this.numeroRiga = numeroRiga+1;
}


public CapoImport(Connection db, int idCapo){
	
	StringBuffer sql = new StringBuffer();
	sql.append("select * from m_capi where id =  ?");
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
	pst.setInt(1, idCapo );
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		buildRecord(rs);
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

private void buildRecord(ResultSet rs) throws SQLException{
	
	this.id=rs.getInt("id");
	this.id_macello = rs.getInt("id_macello");
	this.idImport = rs.getInt("id_import");
	this.vpm_data = rs.getTimestamp("vpm_data");
	this.cd_matricola = rs.getString("cd_matricola");
	
}

public void aggiornaLiberoConsumo(Connection db) throws SQLException{
	
	SintesisStabilimento org = new SintesisStabilimento (db, id_macello, true);
	String nome= org.getOperatore().getRagioneSociale();
	
	
	StringBuffer sqlBuffer = new StringBuffer();
	sqlBuffer.append("update m_capi set stato_macellazione = ?, vpm_esito = ?, destinatario_1_id = ?, destinatario_1_nome = ?, destinatario_1_in_regione = ?, "
			+ "destinatario_2_id = ?, destinatario_2_nome = ?, destinatario_2_in_regione = ?, solo_cd = ? where id = ? ");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sqlBuffer.toString());
	
	pst.setString(++i, this.getStato_macellazione() );
	pst.setInt(++i, this.getVpm_esito());
	pst.setInt(++i, this.getId_macello());
	pst.setString(++i, nome);
	pst.setBoolean(++i, true);
	pst.setInt(++i, this.getId_macello());
	pst.setString(++i, nome);
	pst.setBoolean(++i, true);
	pst.setBoolean(++i, false);
	pst.setInt(++i,  this.getId());
	pst.executeUpdate();
	
}





}
