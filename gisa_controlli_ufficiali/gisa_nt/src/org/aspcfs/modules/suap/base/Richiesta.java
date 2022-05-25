/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

public class Richiesta {
	private int statoValidazione ;
	private int id;
	private String codiceFiscaleImpresa;
	private String note;
	private String partitaIva;
	private String ragioneSociale;
	private int enteredBy;
	private int modifiedBy;
	private int idIndirizzo;
//	private Date entered;
//	private Date modified;
//	private Date trashedDate;
	private String domicilioDigitale;
	private int tipoImpresa;
	private int tipoSocieta;
	private int idTipoRichiesta;
	private boolean validato;
	private String descrTipoRichiesta;
	private String descrTipoSocieta;
	private String descrTipoImpresa;
	private Timestamp entered;
	private Timestamp modified;
	private String numeroRegistrazione;
	private ArrayList<LineaProduttiva> lineeProduttiveAssociate = new ArrayList<LineaProduttiva>();
	private Operatore operatore;
	private String comuneRichiedente ;
	
	private int idStabRichiesta ;
	
	private int idAttivitaStab ;
	private String descrizione_linee_attivita ;
	private String[] arrayPermessi;
	
	private int altId = -1;
	private String codiceAziendaApicoltura;
	
	
	
	public int getIdAttivitaStab() {
		return idAttivitaStab;
	}

	public void setIdAttivitaStab(int idAttivitaStab) {
		this.idAttivitaStab = idAttivitaStab;
	}

	public String getCodiceAziendaApicoltura() {
		return codiceAziendaApicoltura;
	}

	public void setCodiceAziendaApicoltura(String codiceAziendaApicoltura) {
		this.codiceAziendaApicoltura = codiceAziendaApicoltura;
	}

	public int getStatoValidazione() {
		return statoValidazione;
	}

	public void setStatoValidazione(int statoValidazione) {
		this.statoValidazione = statoValidazione;
	}

	public String getDescrizione_linee_attivita() {
		return descrizione_linee_attivita;
	}

	public void setDescrizione_linee_attivita(String descrizione_linee_attivita) {
		this.descrizione_linee_attivita = descrizione_linee_attivita;
	}

	public int getIdStabRichiesta() {
		return idStabRichiesta;
	}

	public void setIdStabRichiesta(int idStabRichiesta) {
		this.idStabRichiesta = idStabRichiesta;
	}

	public String getComuneRichiedente() {
		return comuneRichiedente;
	}

	public void setComuneRichiedente(String comuneRichiedente) {
		this.comuneRichiedente = comuneRichiedente;
	}

	
	public Richiesta()
	{
		
	}

	
	public Richiesta(ResultSet rs) throws SQLException
	{
		buildRecord(rs);
		
	}
	
	
	private void buildRecord(ResultSet rs) throws SQLException
	{
		setId(rs.getInt("id"));
		
		setIdAttivitaStab(rs.getInt("id_attivita_stab"));
		setCodiceFiscaleImpresa(rs.getString("codice_fiscale_impresa"));
		setNote(rs.getString("note"));
		setPartitaIva(rs.getString("partita_iva"));
		setRagioneSociale(rs.getString("ragione_sociale"));
		setEnteredBy(rs.getInt("enteredby"));
		setModifiedBy(rs.getInt("modifiedby"));
		setDomicilioDigitale(rs.getString("domicilio_digitale"));
		setTipoImpresa(rs.getInt("tipo_impresa"));
		setTipoSocieta(rs.getInt("tipo_societa"));
		setIdTipoRichiesta(rs.getInt("id_tipo_richiesta"));
		setIdIndirizzo(rs.getInt("id_indirizzo"));
		setValidato( rs.getBoolean("validato") );
		setEntered(rs.getTimestamp("entered"));
		setModified(rs.getTimestamp("modified"));
		try
		{
		setComuneRichiedente(rs.getString("comune"));
		setIdStabRichiesta(rs.getInt("stabid"));
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			statoValidazione = rs.getInt("stato_validazione");
			
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			descrizione_linee_attivita = rs.getString("lista_attivita_descrizione");
			
		}
		catch(Exception e)
		{
			
		}
		

		try
		{
			altId = rs.getInt("alt_id");
			
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			codiceAziendaApicoltura = rs.getString("codice_nazionale_apicoltore");
			
		}
		catch(Exception e)
		{
			
		}
		
		try
		{
			
			Array sqlArrayPermessi = rs.getArray("lista_permessi");
			String[] permessiAsStrings =(String[]) sqlArrayPermessi.getArray();
			setArrayPermessiRichiesta(permessiAsStrings);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Attenzione  problemi ad estrarre lista permessi associati a richiesta");
		}
	}

	public void setArrayPermessiRichiesta(String[] permessiAsStrings) {
		this.arrayPermessi = permessiAsStrings;
	}
	
	public String[] getArrayPermessiRichiesta()
	{
		return this.arrayPermessi;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceFiscaleImpresa() {
		return codiceFiscaleImpresa;
	}

	public void setCodiceFiscaleImpresa(String codiceFiscaleImpresa) {
		this.codiceFiscaleImpresa = codiceFiscaleImpresa;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}

	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}

	public int getTipoImpresa() {
		return tipoImpresa;
	}

	public void setTipoImpresa(int tipoImpresa) {
		this.tipoImpresa = tipoImpresa;
	}

	public int getTipoSocieta() {
		return tipoSocieta;
	}

	public void setTipoSocieta(int tipoSocieta) {
		this.tipoSocieta = tipoSocieta;
	}

	public int getIdTipoRichiesta() {
		return idTipoRichiesta;
	}

	public void setIdTipoRichiesta(int idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}

	public boolean isValidato() {
		return validato;
	}

	public void setValidato(boolean validato) {
		this.validato = validato;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public int getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}

	public String getDescrTipoRichiesta() {
		return descrTipoRichiesta;
	}

	public void setDescrTipoRichiesta(String descrTipoRichiesta) {
		this.descrTipoRichiesta = descrTipoRichiesta;
	}

	public String getDescrTipoSocieta() {
		return descrTipoSocieta;
	}

	public void setDescrTipoSocieta(String descrTipoSocieta) {
		this.descrTipoSocieta = descrTipoSocieta;
	}

	public void setDescrTipoImpresa(String descrTipoImpresa) {
		this.descrTipoImpresa = descrTipoImpresa;
		
	}
	
	public String getDescrTipoImpresa()
	{
		return this.descrTipoImpresa;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public String generaNumeroRegistrazione(Connection db, String idRichiesta,boolean writeMode) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			char mode = writeMode ? 'w' : 'r';
			pst= db.prepareStatement("select suap_genera_numero_registrazione from public_functions.suap_genera_numero_registrazione(?,?)");
			pst.setInt(1,Integer.parseInt(idRichiesta));
			pst.setString(2, mode+"");
			rs = pst.executeQuery();
			rs.next();
			String codiceRegionale = rs.getString(1);
			setNumeroRegistrazione(codiceRegionale);
			return getNumeroRegistrazione();
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst!=null) pst.close();
			if(rs!=null) rs.close();
		}
		
	}
	
	
	public void caricaLineeProduttive(Connection db,String idRichiesta) throws Exception
	{
		//voglio ottenere la lista di tutte le linee produttive, sia quelle gia' validate che quelle non evase, per quella nuova richiesta
		
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			//ottengo prima il mapping delle descrizioni
			
			
			
			Stabilimento stab = new Stabilimento();
			stab.setIdOperatore(idRichiesta);
			stab.getListaLineeProduttive().setIdStabilimento(this.getIdStabRichiesta());
			stab.getListaLineeProduttive().buildList(db);
			
			
				
			/*	
			for(Object lineaProd : ((Stabilimento)stab).getListaLineeProduttive() )
				{
					int idTipoConfigurazione = ((LineaProduttiva) lineaProd).getIdLookupConfigurazioneValidazione();
					String nomeAttivita = ((LineaProduttiva) lineaProd).getAttivita();
					int statoLinea = ((LineaProduttiva) lineaProd).getStato();
					
					if(idTipoConfigurazione != 0 ) 
					{
						if(statoLinea != 0) //cioe' gia' validata
						{

							codiciNazionali.put(((LineaProduttiva) lineaProd).getId()+"", ((LineaProduttiva) lineaProd).getCodiceNazionale() != null ? ((LineaProduttiva) lineaProd).getCodiceNazionale() : "");
						}
						

						
						lineeProduttiveAssociate.add((LineaProduttiva)lineaProd);
						rappresentazioneLinee.put(((LineaProduttiva) lineaProd).getId(), new String[]{idTipoConfigurazione+"",((LineaProduttiva) lineaProd).getDescr_label(), nomeAttivita, ((LineaProduttiva) lineaProd).getPermesso(), statoLinea+""   }); //{id linea attivita : [idtipoconf , descrizioneCodiceRichiesto, nomeLineaAttivita, permesso, statovalidazione] } 
					}
				}*/
						
			
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(pst != null) pst.close();
			if(rs != null) rs.close();
		}
		
		
		
		
		
		
		HashMap<Integer, String[]> lineeProd = new HashMap<Integer, String[]> (); // {id linea attivita : [idtipoconf , descrizioneCodiceRichiesto, nomeLineaAttivita, permesso] } 
		
		
		//NB: AGGIUNGO LA LISTA DELLE LINEE PRODUTTIVE ASSOCIATE ALLA RICHIESTA AGGIUNGENDO LO STATO (se validate gia o no)
		
		
		
		
		
	}

	public ArrayList<LineaProduttiva> getLineeProduttiveAssociate() {
		return lineeProduttiveAssociate;
	}

	public void setLineeProduttiveAssociate(ArrayList<LineaProduttiva> lineeProduttiveAssociate) {
		this.lineeProduttiveAssociate = lineeProduttiveAssociate;
	}

	public Operatore getOperatore() {
		return operatore;
	}

	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}
	
	
	public Richiesta(Connection db, int idRichiesta) throws Exception 
	{
		String query0 = "select * from suap_get_lista_richieste(?) where (validato = false or validato is null) ";

		
		PreparedStatement pst = null,pst2 = null;
		ResultSet rs = null, rs2 = null;

		try
		{
			pst = db.prepareStatement( query0 );
		
				pst.setInt(1,idRichiesta);
			
			

			rs = pst.executeQuery();
			if(rs.next())
			{
				this.buildRecord(rs);

				this.caricaLineeProduttive(db, idRichiesta+"");
			}
		}
		
		catch(Exception ex)
		{
			throw ex;
		}
	
		
	}
	
	
	
	public Richiesta(Connection db, int idRichiesta,boolean flag) throws Exception 
	{
		String query0 = "select * from suap_get_lista_richieste(?) ";

		
		
		PreparedStatement pst = null,pst2 = null;
		ResultSet rs = null, rs2 = null;

		try
		{
			pst = db.prepareStatement( query0 );
		
				pst.setInt(1,idRichiesta);
			
			

			rs = pst.executeQuery();
			if(rs.next())
			{
				this.buildRecord(rs);

				this.caricaLineeProduttive(db, idRichiesta+"");
			}
		}
		
		catch(Exception ex)
		{
			throw ex;
		}
	
		
	}

	public int getAltId() {
		return altId;
	}

	public void setAltId(int altId) {
		this.altId = altId;
	}
	
}

