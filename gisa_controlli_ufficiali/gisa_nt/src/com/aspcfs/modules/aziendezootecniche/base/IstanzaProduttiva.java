/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.aspcfs.modules.aziendezootecniche.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.modules.allevamenti.base.AllevamentoAjax;
import org.aspcfs.modules.allevamenti.base.OrganizationAddress;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.opu.base.SoggettoFisico;

public class IstanzaProduttiva {

	private int id ; 
	private int altId ;
	private int idLineaProduttiva ;
	private Impresa allevamento;
	private Stabilimento azienda;
	private SoggettoFisico detentore;
	private Timestamp dataInizioAttivita;
	private Timestamp dataFineAttivita;
	private int categoriaRischio;
	private Timestamp dataProssimoControllo;
	private String codiceSpecie ; 
	private String codiceTipologiaStruttura ; 
	private String codiceOrientamentoProduttivo;
	private String action = "" ;
	private int numeroCapi ;
	
	
	
	public int getNumeroCapi() {
		return numeroCapi;
	}
	public void setNumeroCapi(int numeroCapi) {
		this.numeroCapi = numeroCapi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAltId() {
		return altId;
	}
	public void setAltId(int altId) {
		this.altId = altId;
	}
	public int getIdLineaProduttiva() {
		return idLineaProduttiva;
	}
	public void setIdLineaProduttiva(int idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}
	public Impresa getAllevamento() {
		return allevamento;
	}
	public void setAllevamento(Impresa allevamento) {
		this.allevamento = allevamento;
	}
	public Stabilimento getAzienda() {
		return azienda;
	}
	public void setAzienda(Stabilimento azienda) {
		this.azienda = azienda;
	}
	public Timestamp getDataInizioAttivita() {
		return dataInizioAttivita;
	}
	public void setDataInizioAttivita(Timestamp dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;
	}
	public void setDataInizioAttivita(String dataInizioAttivita) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dataInizioAttivita!=null && !"".equals(dataInizioAttivita))
		this.dataInizioAttivita = new Timestamp(sdf.parse(dataInizioAttivita).getTime());
	}
	public void setDataFineAttivita(String dataFineAttivita) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dataFineAttivita!=null && !"".equals(dataFineAttivita) && !"null".equals(dataFineAttivita))
		this.dataFineAttivita = new Timestamp(sdf.parse(dataFineAttivita).getTime());	}

	public Timestamp getDataFineAttivita() {
		return dataFineAttivita;
	}
	public void setDataFineAttivita(Timestamp dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;
	}
	public int getCategoriaRischio() {
		return categoriaRischio;
	}
	public void setCategoriaRischio(int categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}
	public Timestamp getDataProssimoControllo() {
		return dataProssimoControllo;
	}
	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
		this.dataProssimoControllo = dataProssimoControllo;
	}

	public SoggettoFisico getDetentore() {
		return detentore;
	}
	public void setDetentore(SoggettoFisico detentore) {
		this.detentore = detentore;
	}

	public String getCodiceSpecie() {
		return codiceSpecie;
	}
	public void setCodiceSpecie(String codiceSpecie) {
		this.codiceSpecie = codiceSpecie;
	}
	public String getCodiceTipologiaStruttura() {
		return codiceTipologiaStruttura;
	}
	public void setCodiceTipologiaStruttura(String codiceTipologiaStruttura) {
		this.codiceTipologiaStruttura = codiceTipologiaStruttura;
	}
	public String getCodiceOrientamentoProduttivo() {
		return codiceOrientamentoProduttivo;
	}
	public void setCodiceOrientamentoProduttivo(String codiceOrientamentoProduttivo) {
		this.codiceOrientamentoProduttivo = codiceOrientamentoProduttivo;
	}
	public void insert(Connection db,int idUtente)
	{

		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{

			this.getAllevamento().insert(db, idUtente);
			this.getAzienda().insert(db, idUtente);

			/*INSERT INDIRIZZO DETENTORE : INPUT: indirizzo,comune,cap */
			String insertIndirizzoDetentore = "select inserisci_opu_indirizzo_allev(?,?,?)";
			int idIndirizzoDetentore =-1;
			pst=db.prepareStatement(insertIndirizzoDetentore);
			pst.setString(1,this.getDetentore().getIndirizzo().getVia());
			pst.setString(2,this.getDetentore().getIndirizzo().getComuneTesto());
			pst.setString(3,this.getDetentore().getIndirizzo().getCap());
			rs = pst.executeQuery();
			if(rs.next())
			{
				idIndirizzoDetentore = rs.getInt(1);
			}

			/*INSERT SOOGGETTO FISICO DETENTORE : INPUT: nominativo,cf,id_indirizzo_Residenza_detentore */
			String insertDetentore = "select inserisci_opu_soggetto_fisico_allev(?,?,?)";
			int isDetentore = -1 ;
			pst=db.prepareStatement(insertDetentore);
			pst.setString(1,this.getDetentore().getNome());
			pst.setString(2,this.getDetentore().getCodFiscale());
			pst.setInt(3,idIndirizzoDetentore);
			rs = pst.executeQuery();
			if(rs.next())
			{
				isDetentore = rs.getInt(1);
			}

			/*INSERT ATTIVITA : INPUT: id_nuova_linea_produttiva,id_azienda_stab,id_allevamento_impresa,id_sf_detentore,date1::timestamp ,date2 ::timestamp,291,'0'||o.specie_allevamento ,o.tipologia_strutt,o.orientamento_prod,idOrgid */
			String insertIstanzAttivitaProduttiva = "select inserisci_opu_allevamenti_attivita_produttiva(?,?,?,?,?::timestamp ,? ::timestamp,?,? ,?,?,-1)";

		
			
			int idAttivita=-1;
			pst=db.prepareStatement(insertIstanzAttivitaProduttiva);
			pst.setInt(1,idLineaProduttiva);
			pst.setInt(2,this.getAzienda().getId());
			pst.setInt(3,this.getAllevamento().getId());
			pst.setInt(4,isDetentore);
			pst.setTimestamp(5, this.getDataInizioAttivita());
			pst.setTimestamp(6, this.getDataFineAttivita());
			pst.setInt(7,idUtente);
			pst.setString(8, codiceSpecie);
			pst.setString(9, codiceTipologiaStruttura);
			pst.setString(10, codiceOrientamentoProduttivo);

			rs = pst.executeQuery();
			if(rs.next())
			{
				idAttivita = rs.getInt(1);
				this.setId(idAttivita);
				this.setAltId(idAttivita);

			}


		}
		catch(SQLException e)
		{

		}
	}
	
	
	public int getIdLineaAttivita(Connection db) throws SQLException
	{
		String sql = "select id_nuova_linea_attivita,decodifica_tipo_produzione_bdn,decodifica_specie_bdn,decodifica_tipo_produzione_bdn from ml8_linee_attivita_nuove_materializzata "
				+ "where  decodifica_codice_orientamento_bdn ilike ? "
				+ "and decodifica_specie_bdn ilike ? and  "
				+ "decodifica_tipo_produzione_bdn ilike ? ";
		int idLineaAttivita = -1 ;
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, codiceOrientamentoProduttivo);
		pst.setString(2, codiceSpecie);
		pst.setString(3, codiceTipologiaStruttura);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
			idLineaAttivita=rs.getInt(1);
		return idLineaAttivita;
	}
	
	public IstanzaProduttiva(Connection db,int altId) throws SQLException
	{
		queryRecord(db, altId);
	}
	
	public void queryRecord(Connection db,int altId) throws SQLException
	{
		String sql = "select * from opu_istanza_attivita_in_allevamento where alt_id =?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, altId);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			

			this.setAltId(rs.getInt("alt_id"));
			this.setIdLineaProduttiva(rs.getInt("id_linea_produttiva"));
			this.setDataInizioAttivita(rs.getTimestamp("data_inizio_attivita"));
			this.setDataFineAttivita(rs.getTimestamp("data_fine_attivita"));
			this.setCategoriaRischio(rs.getInt("categoria_rischio"));
			this.setDataProssimoControllo(rs.getTimestamp("data_prossimo_controllo"));
			this.setCodiceSpecie(rs.getString("codice_bdn_specie_allevata"));
			this.setCodiceTipologiaStruttura(rs.getString("codice_bdn_tipologia_allevamento"));
			this.setCodiceOrientamentoProduttivo(rs.getString("codice_bdn_orientamento_produttivo"));
			Impresa allevamento = new Impresa();
			allevamento.queryRecord(db, rs.getInt("id_allevamento_impresa"));
			this.setAllevamento(allevamento);
			this.setDetentore(new SoggettoFisico(db, rs.getInt("id_soggetto_fisico_detentore")));
			Stabilimento azienda = new Stabilimento();
			azienda.queryRecord(db, rs.getInt("id_allevamento_stabilimento"));
			this.setAzienda(azienda);
			
		}
		
	}
	

	public String getAction()
	{
		return action ;
	}

	public IstanzaProduttiva()
	{
		
	}
public  String getPrefissoAction(String actionName)
{
action = "AziendeZootecniche";
return action ;
}


public ArrayList equals(AllevamentoAjax all)
{
	ArrayList differenze = new ArrayList();

	if (!this.getAllevamento().getRagioneSociale().equals(all.getDenominazione()))
		differenze.add("denominazione");
	if (Integer.parseInt(this.getCodiceSpecie())!=all.getSpecie_allevata())
		differenze.add("specie_allevata");
	
//	if (this.getNumeroCapi()!=all.getNumero_capi())
//		differenze.add("numero_capi");
	
	if (!this.getAllevamento().getIdFiscaleAllevamento().trim().equals(all.getCodice_fiscale()))
		differenze.add("codice_fiscale");
	
	if (!this.getAllevamento().getProprietario().getCodFiscale().trim().equals(all.getCfProprietario()))
		differenze.add("cfProprietario");
	
	if (!this.getDetentore().getCodFiscale().trim().equals(all.getCfDetentore()))
		differenze.add("cfDetentore");
			
	//INDIRIZZO SEDE LEGALE
	
	
	if (!this.getAllevamento().getSedeLegale().getDescrizioneComune().equals(all.getComune()))
		differenze.add("comune");
		
	return differenze;
	
}

}
