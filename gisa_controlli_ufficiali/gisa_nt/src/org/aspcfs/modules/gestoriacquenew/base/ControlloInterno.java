/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.batik.svggen.font.table.LookupList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.DbUtil;
import org.aspcfs.utils.DatabaseUtils;


/*questa classe e' una versione semplificata e riscritta per la nuova versione di gestori acque di rete (e per i loro controlli interni) della classe Ticket di acque di rete (il cavaliere originale
 * gestito dalle asl)*/

public class ControlloInterno {

	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAlfa() {
		return alfa;
	}
	public void setAlfa(String alfa) {
		this.alfa = alfa;
	}
	public String getBeta() {
		return beta;
	}
	public void setBeta(String beta) {
		this.beta = beta;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTipoDecreto() {
		return tipoDecreto;
	}
	public void setTipoDecreto(String tipoDecreto) {
		this.tipoDecreto = tipoDecreto;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
	public String getRadon() {
		return radon;
	}
	public void setRadon(String radon) {
		this.radon = radon;
	}
	public String getTrizio() {
		return trizio;
	}
	public void setTrizio(String trizio) {
		this.trizio = trizio;
	}
	public Integer getIdControlloUfficiale() {
		return idControlloUfficiale;
	}
	public void setIdControlloUfficiale(Integer idControlloUfficiale) {
		this.idControlloUfficiale = idControlloUfficiale;
	}
	public Integer getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(Integer enteredBy) {
		this.enteredBy = enteredBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getTipologia() {
		return tipologia;
	}
	public void setTipologia(Integer tipologia) {
		this.tipologia = tipologia;
	}
	public Integer getTipoControllo() {
		return tipoControllo;
	}
	public void setTipoControllo(Integer tipoControllo) {
		this.tipoControllo = tipoControllo;
	}
	public Timestamp getDataInizioControllo() {
		return dataInizioControllo;
	}
	public void setDataInizioControllo(Timestamp dataInizioControllo) {
		this.dataInizioControllo = dataInizioControllo;
	}
	public Timestamp getDataFineControllo() {
		return dataFineControllo;
	}
	public void setDataFineControllo(Timestamp dataFineControllo) {
		this.dataFineControllo = dataFineControllo;
	}
//	public Integer getMotivoIspezione() {
//		return motivoIspezione;
//	}
//	public String getDescMotivoIspezione()
//	{
//		return descMotivoIspezione;
//	}
//	public void setDescMotivoIspezione(String s)
//	{
//		this.descMotivoIspezione = s;
//	}
	public String getDescAsl()
	{
		return this.descAsl;
	}
	public void setDescAsl(String s)
	{
		descAsl = s;
	}
//	public void setMotivoIspezione(Integer motivoIspezione) {
//		this.motivoIspezione = motivoIspezione;
//	}
//	public Integer getIdUnitaOperativa() {
//		return idUnitaOperativa;
//	}
//	public void setIdUnitaOperativa(Integer idUnitaOperativa) {
//		this.idUnitaOperativa = idUnitaOperativa;
//	}
	public Integer getOggettoIspezione() {
		return oggettoIspezione;
	}
	public void setOggettoIspezione(Integer oggettoIspezione) {
		this.oggettoIspezione = oggettoIspezione;
	}
	public Integer getIdComponenteNucleoIspettivo() {
		return idComponenteNucleoIspettivo;
	}
	public void setIdComponenteNucleoIspettivo(Integer idComponenteNucleoIspettivo) {
		this.idComponenteNucleoIspettivo = idComponenteNucleoIspettivo;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getCloro() {
		return cloro;
	}
	public void setCloro(String cloro) {
		this.cloro = cloro;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = altro;
	}
	public boolean isProtocolloRoutine() {
		return protocolloRoutine;
	}
	public void setProtocolloRoutine(boolean protocolloRoutine) {
		this.protocolloRoutine = protocolloRoutine;
	}
	public boolean isProtocollVerifica() {
		return protocollVerifica;
	}
	public void setProtocollVerifica(boolean protocollVerifica) {
		this.protocollVerifica = protocollVerifica;
	}
	public boolean isProtocolloReplicaMicro() {
		return protocolloReplicaMicro;
	}
	public void setProtocolloReplicaMicro(boolean protocolloReplicaMicro) {
		this.protocolloReplicaMicro = protocolloReplicaMicro;
	}
	public boolean isProtocolloReplicaChim() {
		return protocolloReplicaChim;
	}
	public void setProtocolloReplicaChim(boolean protocolloReplicaChim) {
		this.protocolloReplicaChim = protocolloReplicaChim;
	}
	public boolean isProtocolloRadioattivita() {
		return protocolloRadioattivita;
	}
	public void setProtocolloRadioattivita(boolean protocolloRadioattivita) {
		this.protocolloRadioattivita = protocolloRadioattivita;
	}
	public boolean isProtocolloRicercaFitosanitari() {
		return protocolloRicercaFitosanitari;
	}
	public void setProtocolloRicercaFitosanitari(boolean protocolloRicercaFitosanitari) {
		this.protocolloRicercaFitosanitari = protocolloRicercaFitosanitari;
	}
	
	public void setPuntoPrelievoPadre(PuntoPrelievo pp)
	{
		this.puntoPrelievoPadre = pp;
	}
	public PuntoPrelievo getPuntoPrelievoPadre()
	{
		return this.puntoPrelievoPadre;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getNonConformita() {
		return nonConformita;
	}
	public void setNonConformita(String nonConformita) {
		this.nonConformita = nonConformita;
	}
	private Integer statusId ; 
	private Integer siteId;  
	private String alfa;
	private String beta; 
	private String dose; 
	private String radon; 
	private String trizio; 
	private Integer id; 
	private Integer idControlloUfficiale; /*la versione padded di id*/
	private Integer enteredBy;  
	private Integer modifiedBy; 
	private Integer tipologia;  
	private Integer tipoControllo; 
	private Timestamp dataInizioControllo;  
	private Timestamp dataFineControllo; 
//	private Integer motivoIspezione; 
//	private String descMotivoIspezione;
	private String descAsl;
//	private Integer idUnitaOperativa;  
	private Integer oggettoIspezione; 
	private Integer idComponenteNucleoIspettivo;
	private String tipoDecreto;
	private String temperatura;
	private String cloro;
	private String ora;
	private  String  altro;
	private String note;
	private boolean protocolloRoutine;
	private boolean protocollVerifica;
	private boolean protocolloReplicaMicro;
	private boolean protocolloReplicaChim;
	private boolean protocolloRadioattivita;
	private boolean protocolloRicercaFitosanitari;
	private String esito;
	

	private String nonConformita;
	private PuntoPrelievo puntoPrelievoPadre;

	private String campione_finalitaMisura;
	private String campione_notaFinalitaMisura;
	private String campione_motivoPrelievo;
	private String campione_notaMotivoPrelievo;

	private String fornitura_denominazioneZona;
	private String fornitura_denominazioneGestore;

	private String punto_tipoAcqua;
	private String punto_note;

	private String campionamento_numeroPrelievi;
	private String campionamento_chi;

	private String DI_alfaTotaleMar;
	private String DI_alfaTotaleMisura;
	private String DI_alfaTotaleIncertezza;
	private String DI_alfaTotaleDataMisura;
	private String DI_alfaTotaleLaboratorio;
	private String DI_alfaTotaleMetodoProva;

	private String DI_betaTotaleMar;
	private String DI_betaTotaleMisura;
	private String DI_betaTotaleIncertezza;
	private String DI_betaTotaleDataMisura;
	private String DI_betaTotaleLaboratorio;
	private String DI_betaTotaleMetodoProva;

	private String DI_betaResiduaMar;
	private String DI_betaResiduaMisura;
	private String DI_betaResiduaIncertezza;
	private String DI_betaResiduaDataMisura;
	private String DI_betaResiduaLaboratorio;
	private String DI_betaResiduaMetodoProva;

	private String Radon_concentrazioneMar;
	private String Radon_concentrazioneMisura;
	private String Radon_concentrazioneIncertezza;
	private String Radon_concentrazioneDataMisura;
	private String Radon_concentrazioneLaboratorio;
	private String Radon_concentrazioneMetodoProva;

	private String Trizio_concentrazioneMar;
	private String Trizio_concentrazioneMisura;
	private String Trizio_concentrazioneIncertezza;
	private String Trizio_concentrazioneDataMisura;
	private String Trizio_concentrazioneLaboratorio;
	private String Trizio_concentrazioneMetodoProva;
	
	
	
	public static int getCodeFromLookupDesc(Connection db, String lookup, String codeField, String descField, String descValue, String condAggiuntiva) throws EccezioneDati,Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		try
		{
			String query ="select :codefield: from :lookup: where lower(:descfield:) = lower(?) and "+(condAggiuntiva != null && condAggiuntiva.length() > 0 ? condAggiuntiva : "1=1");
			query = query.replace(":codefield:", codeField);
			query = query.replace(":lookup:", lookup);
			query = query.replace(":descfield:",descField);
			
			pst = db.prepareStatement(query);
			pst.setString(1, descValue);
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = rs.getInt(1);
			}
			else
				throw new EccezioneDati("Codice Non trovato per lookup "+lookup);
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try {rs.close();} catch(Exception ex){}
			try {pst.close();} catch(Exception ex){}
		}
		return toRet;
	}
	
	
	public static String getDescFromLookupCode(Connection db, String lookup, String codeField, String descField, int codeValue, String condAggiuntiva ) throws EccezioneDati,Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		String toRet = null;
		try
		{
			String query ="select :descfield: from :lookup: where :codefield: = ? and "+(condAggiuntiva != null && condAggiuntiva.length() > 0 ? condAggiuntiva : "1=1");
			query = query.replace(":codefield:", codeField);
			query = query.replace(":lookup:", lookup);
			query = query.replace(":descfield:",descField);
			
			pst = db.prepareStatement(query);
			pst.setInt(1, codeValue);
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = rs.getString(1);
			}
			else
				throw new EccezioneDati("Descrizione lookup non trovata");
		}
		catch(Exception ex)
		{
//			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try {rs.close();} catch(Exception ex){}
			try {pst.close();} catch(Exception ex){}
		}
		return toRet;
	}
	
	public static List<ControlloInterno> buildList(ResultSet rs) throws SQLException
	{
		ArrayList<ControlloInterno> toReturn = new ArrayList<ControlloInterno>();
		
		while(rs.next())
		{
			toReturn.add(ControlloInterno.build(rs));
		}
		
		return toReturn;
		
	}
	
	
	public static ControlloInterno build(ResultSet rs)
	{
		ControlloInterno toRet = new ControlloInterno();
		try{toRet.setStatusId(rs.getInt("status_id"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setSiteId(rs.getInt("site_id"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setId(rs.getInt("ticketid"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setEnteredBy(rs.getInt("enteredby"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setModifiedBy(rs.getInt("modifiedby"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setTipologia(rs.getInt("tipologia"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setTipoControllo(rs.getInt("provvedimenti_prescrittivi"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setDataInizioControllo(rs.getTimestamp("assigned_date"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setDataFineControllo(rs.getTimestamp("data_fine_controllo"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setOggettoIspezione(rs.getInt("ispezione"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setOra(rs.getString("ore"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setIdControlloUfficiale(rs.getInt("id_controllo_ufficiale"));} catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setProtocolloRadioattivita(rs.getBoolean("prot_radioattivita")); } catch( Exception ex ) { ex.printStackTrace(); } 
		try{toRet.setProtocolloReplicaChim(rs.getBoolean("prot_replica_chim")); } catch( Exception ex ) { ex.printStackTrace(); } 
		try{toRet.setProtocolloReplicaMicro(rs.getBoolean("prot_replica_micro")); } catch( Exception ex ) { ex.printStackTrace(); } 
		try{toRet.setProtocolloRicercaFitosanitari(rs.getBoolean("prot_ricerca_fitosanitari")); } catch( Exception ex ) { ex.printStackTrace(); } 
		try{toRet.setProtocolloRoutine(rs.getBoolean("prot_routine")); } catch( Exception ex ) { ex.printStackTrace(); } 
		try{toRet.setProtocollVerifica(rs.getBoolean("prot_verifica")); } catch( Exception ex ) { ex.printStackTrace(); } 
		try{toRet.setDescAsl(rs.getString("desc_asl")); } catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setCloro(rs.getString("cloro")); } catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setTemperatura(rs.getString("temperatura")); } catch( Exception ex ) { ex.printStackTrace(); }
//		try{toRet.setDescMotivoIspezione(rs.getString("desc_motivo_ispezione")); } catch( Exception ex ) { ex.printStackTrace(); }
		try{toRet.setEsito(rs.getString("esito"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setNonConformita(rs.getString("nonconformitaformali"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTrizio(rs.getString("trizio"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setAlfa(rs.getString("alfa"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setBeta(rs.getString("beta"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDose(rs.getString("dose"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setRadon(rs.getString("radon"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTipoDecreto(rs.getString("tipo_decreto"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setAltro(rs.getString("altro"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setNote(rs.getString("note"));}catch(Exception ex){ex.printStackTrace();}
		
		try{toRet.setCampione_finalitaMisura(rs.getString("campione_finalita_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setCampione_notaFinalitaMisura(rs.getString("campione_nota_finalita_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setCampione_motivoPrelievo(rs.getString("campione_motivo_prelievo"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setCampione_notaMotivoPrelievo(rs.getString("campione_nota_motivo_prelievo"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setFornitura_denominazioneZona(rs.getString("fornitura_denominazione_zona"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setFornitura_denominazioneGestore(rs.getString("fornitura_denominazione_gestore"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setPunto_tipoAcqua(rs.getString("punto_tipo_acqua"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setPunto_note(rs.getString("punto_note"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setCampionamento_numeroPrelievi(rs.getString("campionamento_numero_prelievi"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setCampionamento_chi(rs.getString("campionamento_chi"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setDI_alfaTotaleMar(rs.getString("di_alfa_totale_mar"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_alfaTotaleMisura(rs.getString("di_alfa_totale_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_alfaTotaleIncertezza(rs.getString("di_alfa_totale_incertezza"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_alfaTotaleDataMisura(rs.getString("di_alfa_totale_data_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_alfaTotaleLaboratorio(rs.getString("di_alfa_totale_laboratorio"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_alfaTotaleMetodoProva(rs.getString("di_alfa_totale_metodo_prova"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setDI_betaTotaleMar(rs.getString("di_beta_totale_mar"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaTotaleMisura(rs.getString("di_beta_totale_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaTotaleIncertezza(rs.getString("di_beta_totaleincertezza"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaTotaleDataMisura(rs.getString("di_beta_totale_data_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaTotaleLaboratorio(rs.getString("di_beta_totale_laboratorio"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaTotaleMetodoProva(rs.getString("di_beta_totale_metodo_prova"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setDI_betaResiduaMar(rs.getString("di_beta_residua_mar"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaResiduaMisura(rs.getString("di_beta_residua_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaResiduaIncertezza(rs.getString("di_beta_residua_incertezza"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaResiduaDataMisura(rs.getString("di_beta_residua_data_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaResiduaLaboratorio(rs.getString("di_beta_residua_laboratorio"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDI_betaResiduaMetodoProva(rs.getString("di_beta_residua_metodo_prova"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setRadon_concentrazioneMar(rs.getString("radon_concentrazione_mar"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setRadon_concentrazioneMisura(rs.getString("radon_concentrazione_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setRadon_concentrazioneIncertezza(rs.getString("radon_concentrazione_incertezza"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setRadon_concentrazioneDataMisura(rs.getString("radon_concentrazione_data_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setRadon_concentrazioneLaboratorio(rs.getString("radon_concentrazione_laboratorio"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setRadon_concentrazioneMetodoProva(rs.getString("radon_concentrazione_metodo_prova"));}catch(Exception ex){ex.printStackTrace();}

		try{toRet.setTrizio_concentrazioneMar(rs.getString("trizio_concentrazione_mar"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTrizio_concentrazioneMisura(rs.getString("trizio_concentrazione_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTrizio_concentrazioneIncertezza(rs.getString("trizio_concentrazione_incertezza"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTrizio_concentrazioneDataMisura(rs.getString("trizio_concentrazione_data_misura"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTrizio_concentrazioneLaboratorio(rs.getString("trizio_concentrazione_laboratorio"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTrizio_concentrazioneMetodoProva(rs.getString("trizio_concentrazione_metodo_prova"));}catch(Exception ex){ex.printStackTrace();}
		
		return toRet;
	}
	
	public static List<ControlloInterno> searchAllPerPuntoPrelievo(Connection db, int idPuntoPrelievo, String tipoDecreto) throws Exception
	{
		 
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			/*mettere in join con tcui con idispezione != -1 e stesso ticketid per ottenere vero id ispezione (che cmq e' sempre 19) CABLATO TOGLIERE */
			pst= db.prepareStatement("select ticket.*,tcui.*,cpp.*, asl.description as desc_asl, 19 as ispezione from controlli_punti_di_prelievo_acque_rete cpp join tipocontrolloufficialeimprese tcui"
					+ " on cpp.id_controllo = tcui.idcontrollo join ticket on ticket.ticketid = tcui.idcontrollo join lookup_site_id asl on asl.code = ticket.site_id "
					+ " join lookup_tipo_controllo tc on tc.code = ticket.provvedimenti_prescrittivi "
					+ " where cpp.org_id_pdp = ? and tcui.ispezione = -1 and ticket.tipologia = 3 and (? is null or cpp.tipo_decreto = ? ) "
					);
			
			pst.setInt(1, idPuntoPrelievo);
			pst.setString(2, tipoDecreto);
			pst.setString(3, tipoDecreto);
			rs = pst.executeQuery();
			return buildList(rs);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close();} catch(Exception ex) {}
			try{rs.close();} catch(Exception ex){} 
		}
		
	}
	
	
	public static ControlloInterno searchByOid(Connection db,int idControlloInterno) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		try
		{
			
			/*mettere in join con tcui con idispezione != -1 e stesso ticketid per ottenere vero id ispezione (che cmq e' sempre 19) CABLATO TOGLIERE */
			pst= db.prepareStatement("select ticket.*,tcui.*,cpp.*, asl.description as desc_asl, 19 as ispezione from controlli_punti_di_prelievo_acque_rete cpp join tipocontrolloufficialeimprese tcui"
					+ " on cpp.id_controllo = tcui.idcontrollo join ticket on ticket.ticketid = tcui.idcontrollo join lookup_site_id asl on asl.code = ticket.site_id "
					+ " join lookup_tipo_controllo tc on tc.code = ticket.provvedimenti_prescrittivi "
					+ " where ticket.ticketid = ? and tcui.ispezione = -1 and ticket.tipologia = 3"
					);
			pst.setInt(1, idControlloInterno);
			rs = pst.executeQuery();
			if(rs.next())
			{
				return build(rs);
			}
			return null;
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	}
	
	public static ArrayList<ControlloInterno> search(Connection db,String codiceGisa, Timestamp data, String ora, String tipoDecreto) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pst = null;
		ArrayList<ControlloInterno> controlli = new ArrayList<ControlloInterno>();
		
		try
		{
			
			/*mettere in join con tcui con idispezione != -1 e stesso ticketid per ottenere vero id ispezione (che cmq e' sempre 19) CABLATO TOGLIERE */
			pst= db.prepareStatement("  select ticket.*,tcui.*,cpp.*, asl.description as desc_asl, 19 as ispezione " +            
									 "  from controlli_punti_di_prelievo_acque_rete cpp     " +                 
									 "  join tipocontrolloufficialeimprese tcui  on cpp.id_controllo = tcui.idcontrollo  " +    
									 "  join ticket on ticket.ticketid = tcui.idcontrollo  " +    
									 "  join lookup_site_id asl on asl.code = ticket.site_id   " +    
									 "  join lookup_tipo_controllo tc on tc.code = ticket.provvedimenti_prescrittivi   " +    
									 "  join gestori_acque_punti_prelievo pdp on pdp.id = cpp.org_id_pdp and pdp.trashed_date is null " +    
									 "  where pdp.codice_gisa = ? and ticket.assigned_date = ? and cpp.ore = ? and tcui.ispezione = -1 and ticket.tipologia = 3 and cpp.tipo_decreto = ? ");
			pst.setString(1, codiceGisa);
			pst.setTimestamp(2, data);
			pst.setString(3, ora);
			pst.setString(4, tipoDecreto);
			rs = pst.executeQuery();
			while(rs.next())
			{
				controlli.add(build(rs));
			}
			return controlli;
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{rs.close();} catch(Exception ex){}
			try{pst.close();} catch(Exception ex){}
		}
	}
	
	
	
	public String insert(Connection db, UserBean user) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		String toRet = null;
		try
		{
			/*inserisco il ticket */
			pst = db.prepareStatement("insert into ticket(status_id,site_id,ticketid,enteredby,modifiedby,tipologia,provvedimenti_prescrittivi,assigned_date,data_fine_controllo,problem,esito,nonconformitaformali,note) "
					+ "values (?,?,?,?,?,?,?,?,?,'',?,?,?)  ");
			
			int u = 0;
			
			if(false)
				throw new EccezioneDati();
			 
			int livello=1 ;
			if (user.getUserRecord().getGruppo_ruolo()==2)
				livello=2;
			
			int generatedTicketId =   DatabaseUtils.getNextInt(db,"ticket", "ticketid", livello) ;
			setId(generatedTicketId);
			
			pst.setInt(++u, getStatusId());
			pst.setInt(++u, getSiteId());
			pst.setInt(++u,  getId());
			pst.setInt(++u, getEnteredBy());
			pst.setInt(++u, getModifiedBy());
			pst.setInt(++u, getTipologia());
			pst.setInt(++u, getTipoControllo());
			pst.setTimestamp(++u, getDataInizioControllo());
			pst.setTimestamp(++u, getDataFineControllo());
			pst.setString(++u, getEsito());
			pst.setString(++u, getNonConformita());
			pst.setString(++u, getNote());
			pst.executeUpdate();
			
			pst.close();
			
			pst = db.prepareStatement("insert into tipocontrolloufficialeimprese(idcontrollo,tipoispezione, id_unita_operativa,modifiedby,ispezione)"
					+ " values(?,?,?,?,?)");
			u = 0;
			
			pst.setInt(++u, getId());
			pst.setInt(++u, /*getMotivoIspezione()*/-1);
			pst.setInt(++u, /*getIdUnitaOperativa()*/-1);
			pst.setInt(++u, getModifiedBy());
			pst.setInt(++u, -1); /*qui deve essere messo a -1 altrimenti viene successivamente ricancellata */
			
			pst.executeUpdate();
			
			pst.close();
			
			pst = db.prepareStatement("delete from tipocontrolloufficialeimprese where idcontrollo = ? and ispezione > 0");
			pst.setInt(1, getId());
			pst.executeUpdate();
			
			pst.close();
			
			
			pst = db.prepareStatement("insert into tipocontrolloufficialeimprese(idcontrollo,ispezione) values(?,?)");
			pst.setInt(1, getId());
			pst.setInt(2, getOggettoIspezione());
			pst.executeUpdate();
			
			pst.close();
			
			
			pst = db.prepareStatement("insert into cu_nucleo(id_controllo_ufficiale,id_componente) values(?,?)");
			pst.setInt(1, getId());
			pst.setInt(2, getIdComponenteNucleoIspettivo());
			pst.executeUpdate();
			
			pst.close();
			
			
			pst=db.prepareStatement("update ticket set id_controllo_ufficiale =trim(to_char( "+getId()+", '"+DatabaseUtils.getPaddedFromId(getId())+"')) where ticketid = "+getId());
			pst.executeUpdate();
			
			pst.close();
			
			pst = db.prepareStatement("insert into controlli_punti_di_prelievo_acque_rete (id_controllo,id_campione,org_id_pdp,temperatura, cloro, trizio, radon, dose, alfa, beta, ore,prot_radioattivita,prot_replica_chim,prot_replica_micro,prot_ricerca_fitosanitari,prot_routine,prot_verifica, tipo_decreto, altro, campione_finalita_misura,campione_nota_finalita_misura,campione_motivo_prelievo, campione_nota_motivo_prelievo, fornitura_denominazione_zona, fornitura_denominazione_gestore, punto_tipo_acqua, punto_note, campionamento_numero_prelievi, campionamento_chi,di_alfa_totale_mar, di_alfa_totale_misura, di_alfa_totale_incertezza, di_alfa_totale_data_misura, di_alfa_totale_laboratorio, di_alfa_totale_metodo_prova, di_beta_totale_mar, di_beta_totale_misura, di_beta_totale_incertezza, di_beta_totale_data_misura, di_beta_totale_laboratorio, di_beta_totale_metodo_prova, di_beta_residua_mar, di_beta_residua_misura, di_beta_residua_incertezza, di_beta_residua_data_misura, di_beta_residua_laboratorio, di_beta_residua_metodo_prova,radon_concentrazione_mar, radon_concentrazione_misura, radon_concentrazione_incertezza, radon_concentrazione_data_misura, radon_concentrazione_laboratorio, radon_concentrazione_metodo_prova, trizio_concentrazione_mar, trizio_concentrazione_misura, trizio_concentrazione_incertezza, trizio_concentrazione_data_misura, trizio_concentrazione_laboratorio, trizio_concentrazione_metodo_prova ) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
			u = 0;
			pst.setInt(++u, getId());
			pst.setInt(++u, -1);
			pst.setInt(++u, getPuntoPrelievoPadre().getId());
			pst.setString(++u, getTemperatura());
			pst.setString(++u, getCloro());
			pst.setObject(++u, getTrizio());
			pst.setObject(++u, getRadon());
			pst.setObject(++u, getDose());
			pst.setObject(++u, getAlfa());
			pst.setObject(++u, getBeta());
			pst.setString(++u, getOra());
			pst.setBoolean (++u, isProtocolloRadioattivita());
			pst.setBoolean(++u, isProtocolloReplicaChim());
			pst.setBoolean(++u, isProtocolloReplicaMicro());
			pst.setBoolean(++u, isProtocolloRicercaFitosanitari());
			pst.setBoolean(++u, isProtocolloRoutine());
			pst.setBoolean(++u, isProtocollVerifica());
			pst.setString(++u, getTipoDecreto());
			pst.setString(++u, getAltro());
			
			pst.setString(++u, getCampione_finalitaMisura());
			pst.setString(++u, getCampione_notaFinalitaMisura());
			pst.setString(++u, getCampione_motivoPrelievo());
			pst.setString(++u, getCampione_notaMotivoPrelievo());

			pst.setString(++u, getFornitura_denominazioneZona());
			pst.setString(++u, getFornitura_denominazioneGestore());

			pst.setString(++u, getPunto_tipoAcqua());
			pst.setString(++u, getPunto_note());

			pst.setString(++u, getCampionamento_numeroPrelievi());
			pst.setString(++u, getCampionamento_chi());

			pst.setString(++u, getDI_alfaTotaleMar());
			pst.setString(++u, getDI_alfaTotaleMisura());
			pst.setString(++u, getDI_alfaTotaleIncertezza());
			pst.setString(++u, getDI_alfaTotaleDataMisura());
			pst.setString(++u, getDI_alfaTotaleLaboratorio());
			pst.setString(++u, getDI_alfaTotaleMetodoProva());

			pst.setString(++u, getDI_betaTotaleMar());
			pst.setString(++u, getDI_betaTotaleMisura());
			pst.setString(++u, getDI_betaTotaleIncertezza());
			pst.setString(++u, getDI_betaTotaleDataMisura());
			pst.setString(++u, getDI_betaTotaleLaboratorio());
			pst.setString(++u, getDI_betaTotaleMetodoProva());

			pst.setString(++u, getDI_betaResiduaMar());
			pst.setString(++u, getDI_betaResiduaMisura());
			pst.setString(++u, getDI_betaResiduaIncertezza());
			pst.setString(++u, getDI_betaResiduaDataMisura());
			pst.setString(++u, getDI_betaResiduaLaboratorio());
			pst.setString(++u, getDI_betaResiduaMetodoProva());

			pst.setString(++u, getRadon_concentrazioneMar());
			pst.setString(++u, getRadon_concentrazioneMisura());
			pst.setString(++u, getRadon_concentrazioneIncertezza());
			pst.setString(++u, getRadon_concentrazioneDataMisura());
			pst.setString(++u, getRadon_concentrazioneLaboratorio());
			pst.setString(++u, getRadon_concentrazioneMetodoProva());

			pst.setString(++u, getTrizio_concentrazioneMar());
			pst.setString(++u, getTrizio_concentrazioneMisura());
			pst.setString(++u, getTrizio_concentrazioneIncertezza());
			pst.setString(++u, getTrizio_concentrazioneDataMisura());
			pst.setString(++u, getTrizio_concentrazioneLaboratorio());
			pst.setString(++u, getTrizio_concentrazioneMetodoProva());
			
			pst.executeUpdate();
			
			
			 
			
			toRet = "<br><font color='green'>Inserito controllo data inizio "+getDataInizioControllo() + "per Punto di prelievo con nome \""+getPuntoPrelievoPadre().getDenominazione() + "\" sito in \""+getPuntoPrelievoPadre().getIndirizzo().getVia() + "\" .... INSERITO CORRETTAMENTE</font>";
			System.out.println("INSERITO CONTROLLO "+getDataInizioControllo());
		}
		 
	    catch(EccezioneDati ex)
		{
			toRet = "<br><font color='red'> Controllo in data inizio "+getDataInizioControllo()+"Punto di prelievo con nome \""+getPuntoPrelievoPadre().getDenominazione() + "\" sito in \""+getPuntoPrelievoPadre().getIndirizzo().getVia() +" ....  NON INSERITO CORRETTAMENTE : <b>"+ex.getMessage()+"</b> </font>";
			System.out.println("CONTROLLO NON INSERITO "+getDataInizioControllo());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("CONTROLLO NON INSERITO "+getDataInizioControllo());
			toRet = "<br><font color='red'> Controllo in data inizio "+getDataInizioControllo()+"Punto di prelievo con nome \""+getPuntoPrelievoPadre().getDenominazione() + "\" sito in \""+getPuntoPrelievoPadre().getIndirizzo().getVia() + "\" ....  NON INSERITO CORRETTAMENTE : <b>ERRORE DI SISTEMA</b> </font>";
		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
		
		return toRet;
		
	}
	public String getCampione_finalitaMisura() {
		return campione_finalitaMisura;
	}
	public void setCampione_finalitaMisura(String campione_finalitaMisura) {
		this.campione_finalitaMisura = campione_finalitaMisura;
	}
	public String getCampione_notaFinalitaMisura() {
		return campione_notaFinalitaMisura;
	}
	public void setCampione_notaFinalitaMisura(String campione_notaFinalitaMisura) {
		this.campione_notaFinalitaMisura = campione_notaFinalitaMisura;
	}
	public String getCampione_motivoPrelievo() {
		return campione_motivoPrelievo;
	}
	public void setCampione_motivoPrelievo(String campione_motivoPrelievo) {
		this.campione_motivoPrelievo = campione_motivoPrelievo;
	}
	public String getCampione_notaMotivoPrelievo() {
		return campione_notaMotivoPrelievo;
	}
	public void setCampione_notaMotivoPrelievo(String campione_notaMotivoPrelievo) {
		this.campione_notaMotivoPrelievo = campione_notaMotivoPrelievo;
	}
	public String getFornitura_denominazioneZona() {
		return fornitura_denominazioneZona;
	}
	public void setFornitura_denominazioneZona(String fornitura_denominazioneZona) {
		this.fornitura_denominazioneZona = fornitura_denominazioneZona;
	}
	public String getFornitura_denominazioneGestore() {
		return fornitura_denominazioneGestore;
	}
	public void setFornitura_denominazioneGestore(String fornitura_denominazioneGestore) {
		this.fornitura_denominazioneGestore = fornitura_denominazioneGestore;
	}
	public String getPunto_tipoAcqua() {
		return punto_tipoAcqua;
	}
	public void setPunto_tipoAcqua(String punto_tipoAcqua) {
		this.punto_tipoAcqua = punto_tipoAcqua;
	}
	public String getPunto_note() {
		return punto_note;
	}
	public void setPunto_note(String punto_note) {
		this.punto_note = punto_note;
	}
	public String getCampionamento_numeroPrelievi() {
		return campionamento_numeroPrelievi;
	}
	public void setCampionamento_numeroPrelievi(String campionamento_numeroPrelievi) {
		this.campionamento_numeroPrelievi = campionamento_numeroPrelievi;
	}
	public String getCampionamento_chi() {
		return campionamento_chi;
	}
	public void setCampionamento_chi(String campionamento_chi) {
		this.campionamento_chi = campionamento_chi;
	}
	public String getDI_alfaTotaleMar() {
		return DI_alfaTotaleMar;
	}
	public void setDI_alfaTotaleMar(String dI_alfaTotaleMar) {
		DI_alfaTotaleMar = dI_alfaTotaleMar;
	}
	public String getDI_alfaTotaleMisura() {
		return DI_alfaTotaleMisura;
	}
	public void setDI_alfaTotaleMisura(String dI_alfaTotaleMisura) {
		DI_alfaTotaleMisura = dI_alfaTotaleMisura;
	}
	public String getDI_alfaTotaleIncertezza() {
		return DI_alfaTotaleIncertezza;
	}
	public void setDI_alfaTotaleIncertezza(String dI_alfaTotaleIncertezza) {
		DI_alfaTotaleIncertezza = dI_alfaTotaleIncertezza;
	}
	public String getDI_alfaTotaleDataMisura() {
		return DI_alfaTotaleDataMisura;
	}
	public void setDI_alfaTotaleDataMisura(String dI_alfaTotaleDataMisura) {
		DI_alfaTotaleDataMisura = dI_alfaTotaleDataMisura;
	}
	public String getDI_alfaTotaleLaboratorio() {
		return DI_alfaTotaleLaboratorio;
	}
	public void setDI_alfaTotaleLaboratorio(String dI_alfaTotaleLaboratorio) {
		DI_alfaTotaleLaboratorio = dI_alfaTotaleLaboratorio;
	}
	public String getDI_alfaTotaleMetodoProva() {
		return DI_alfaTotaleMetodoProva;
	}
	public void setDI_alfaTotaleMetodoProva(String dI_alfaTotaleMetodoProva) {
		DI_alfaTotaleMetodoProva = dI_alfaTotaleMetodoProva;
	}
	public String getDI_betaTotaleMar() {
		return DI_betaTotaleMar;
	}
	public void setDI_betaTotaleMar(String dI_betaTotaleMar) {
		DI_betaTotaleMar = dI_betaTotaleMar;
	}
	public String getDI_betaTotaleMisura() {
		return DI_betaTotaleMisura;
	}
	public void setDI_betaTotaleMisura(String dI_betaTotaleMisura) {
		DI_betaTotaleMisura = dI_betaTotaleMisura;
	}
	public String getDI_betaTotaleIncertezza() {
		return DI_betaTotaleIncertezza;
	}
	public void setDI_betaTotaleIncertezza(String dI_betaTotaleIncertezza) {
		DI_betaTotaleIncertezza = dI_betaTotaleIncertezza;
	}
	public String getDI_betaTotaleDataMisura() {
		return DI_betaTotaleDataMisura;
	}
	public void setDI_betaTotaleDataMisura(String dI_betaTotaleDataMisura) {
		DI_betaTotaleDataMisura = dI_betaTotaleDataMisura;
	}
	public String getDI_betaTotaleLaboratorio() {
		return DI_betaTotaleLaboratorio;
	}
	public void setDI_betaTotaleLaboratorio(String dI_betaTotaleLaboratorio) {
		DI_betaTotaleLaboratorio = dI_betaTotaleLaboratorio;
	}
	public String getDI_betaTotaleMetodoProva() {
		return DI_betaTotaleMetodoProva;
	}
	public void setDI_betaTotaleMetodoProva(String dI_betaTotaleMetodoProva) {
		DI_betaTotaleMetodoProva = dI_betaTotaleMetodoProva;
	}
	public String getDI_betaResiduaMar() {
		return DI_betaResiduaMar;
	}
	public void setDI_betaResiduaMar(String dI_betaResiduaMar) {
		DI_betaResiduaMar = dI_betaResiduaMar;
	}
	public String getDI_betaResiduaMisura() {
		return DI_betaResiduaMisura;
	}
	public void setDI_betaResiduaMisura(String dI_betaResiduaMisura) {
		DI_betaResiduaMisura = dI_betaResiduaMisura;
	}
	public String getDI_betaResiduaIncertezza() {
		return DI_betaResiduaIncertezza;
	}
	public void setDI_betaResiduaIncertezza(String dI_betaResiduaIncertezza) {
		DI_betaResiduaIncertezza = dI_betaResiduaIncertezza;
	}
	public String getDI_betaResiduaDataMisura() {
		return DI_betaResiduaDataMisura;
	}
	public void setDI_betaResiduaDataMisura(String dI_betaResiduaDataMisura) {
		DI_betaResiduaDataMisura = dI_betaResiduaDataMisura;
	}
	public String getDI_betaResiduaLaboratorio() {
		return DI_betaResiduaLaboratorio;
	}
	public void setDI_betaResiduaLaboratorio(String dI_betaResiduaLaboratorio) {
		DI_betaResiduaLaboratorio = dI_betaResiduaLaboratorio;
	}
	public String getDI_betaResiduaMetodoProva() {
		return DI_betaResiduaMetodoProva;
	}
	public void setDI_betaResiduaMetodoProva(String dI_betaResiduaMetodoProva) {
		DI_betaResiduaMetodoProva = dI_betaResiduaMetodoProva;
	}
	public String getRadon_concentrazioneMar() {
		return Radon_concentrazioneMar;
	}
	public void setRadon_concentrazioneMar(String radon_concentrazioneMar) {
		Radon_concentrazioneMar = radon_concentrazioneMar;
	}
	public String getRadon_concentrazioneMisura() {
		return Radon_concentrazioneMisura;
	}
	public void setRadon_concentrazioneMisura(String radon_concentrazioneMisura) {
		Radon_concentrazioneMisura = radon_concentrazioneMisura;
	}
	public String getRadon_concentrazioneIncertezza() {
		return Radon_concentrazioneIncertezza;
	}
	public void setRadon_concentrazioneIncertezza(String radon_concentrazioneIncertezza) {
		Radon_concentrazioneIncertezza = radon_concentrazioneIncertezza;
	}
	public String getRadon_concentrazioneDataMisura() {
		return Radon_concentrazioneDataMisura;
	}
	public void setRadon_concentrazioneDataMisura(String radon_concentrazioneDataMisura) {
		Radon_concentrazioneDataMisura = radon_concentrazioneDataMisura;
	}
	public String getRadon_concentrazioneLaboratorio() {
		return Radon_concentrazioneLaboratorio;
	}
	public void setRadon_concentrazioneLaboratorio(String radon_concentrazioneLaboratorio) {
		Radon_concentrazioneLaboratorio = radon_concentrazioneLaboratorio;
	}
	public String getRadon_concentrazioneMetodoProva() {
		return Radon_concentrazioneMetodoProva;
	}
	public void setRadon_concentrazioneMetodoProva(String radon_concentrazioneMetodoProva) {
		Radon_concentrazioneMetodoProva = radon_concentrazioneMetodoProva;
	}
	public String getTrizio_concentrazioneMar() {
		return Trizio_concentrazioneMar;
	}
	public void setTrizio_concentrazioneMar(String trizio_concentrazioneMar) {
		Trizio_concentrazioneMar = trizio_concentrazioneMar;
	}
	public String getTrizio_concentrazioneMisura() {
		return Trizio_concentrazioneMisura;
	}
	public void setTrizio_concentrazioneMisura(String trizio_concentrazioneMisura) {
		Trizio_concentrazioneMisura = trizio_concentrazioneMisura;
	}
	public String getTrizio_concentrazioneIncertezza() {
		return Trizio_concentrazioneIncertezza;
	}
	public void setTrizio_concentrazioneIncertezza(String trizio_concentrazioneIncertezza) {
		Trizio_concentrazioneIncertezza = trizio_concentrazioneIncertezza;
	}
	public String getTrizio_concentrazioneDataMisura() {
		return Trizio_concentrazioneDataMisura;
	}
	public void setTrizio_concentrazioneDataMisura(String trizio_concentrazioneDataMisura) {
		Trizio_concentrazioneDataMisura = trizio_concentrazioneDataMisura;
	}
	public String getTrizio_concentrazioneLaboratorio() {
		return Trizio_concentrazioneLaboratorio;
	}
	public void setTrizio_concentrazioneLaboratorio(String trizio_concentrazioneLaboratorio) {
		Trizio_concentrazioneLaboratorio = trizio_concentrazioneLaboratorio;
	}
	public String getTrizio_concentrazioneMetodoProva() {
		return Trizio_concentrazioneMetodoProva;
	}
	public void setTrizio_concentrazioneMetodoProva(String trizio_concentrazioneMetodoProva) {
		Trizio_concentrazioneMetodoProva = trizio_concentrazioneMetodoProva;
	}
	
	
	
	
	
}
