/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestoriacquenew.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Indirizzo;


public class PuntoPrelievo {
	
	public static final int CHUNK_SIZE = 100; /*il numero risultati per paginazione */
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Integer getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(Integer idAsl) {
		this.idAsl = idAsl;
	}
	public Integer getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(Integer idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public Integer getIdLookupTipologia() {
		return idLookupTipologia;
	}
	public void setIdLookupTipologia(Integer idLookupTipologia) {
		this.idLookupTipologia = idLookupTipologia;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getNomeGestore() {
		return nomeGestore;
	}
	public void setNomeGestore(String nomeGestore) {
		this.nomeGestore = nomeGestore;
	}
	public String getCodice() {
		return codice;
	}
	public String getCodiceGisa() {
		return codiceGisa;
	}
	public void setTipoDecreto(String tipoDecreto) {
		this.tipoDecreto = tipoDecreto;
	}
	public String getTipoDecreto() {
		return tipoDecreto;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public void setCodiceGisa(String codiceGisa) {
		this.codiceGisa = codiceGisa;
	}
	public Integer getIdGestore() {
		return idGestore;
	}
	public void setIdGestore(Integer idGestore) {
		this.idGestore = idGestore;
	}
	
	public void setDescrizioneTipologia(String s)
	{
		this.descrizioneTipologia = s;
	}
	public String getDescrizioneTipologia()
	{
		return this.descrizioneTipologia;
	}
	public Indirizzo getIndirizzo()
	{
		return this.indirizzo;
	}
	public void setIndirizzo(Indirizzo ind)
	{
		this.indirizzo = ind;
	}
	
	public void setDescrizioneAsl(String de)
	{
		this.descrizioneAsl = de;
	}
	public String getDescrizioneAsl ()
	{
		return this.descrizioneAsl;
	}
	public Timestamp getDataInserimento()
	{
		return this.dataInserimento;
	}
	public void setDataInserimento(Timestamp ts)
	{
		this.dataInserimento = ts;
	}
	public List<ControlloInterno> getControlliInterni()
	{
		return this.controlliInterni;
	}
	public void setControlliInterni(List<ControlloInterno> controlliInterni)
	{
		this.controlliInterni = controlliInterni;
	}
	
	
	private Integer id;
	private String denominazione;
	private String nomeGestore;
	private Integer idAsl;
	private Integer idIndirizzo;
	private Integer idLookupTipologia;
	private String stato;
	private String codice;
	private String codiceGisa;
	private String tipoDecreto;
	private Integer idGestore;
	private String descrizioneTipologia;
	private String descrizioneAsl;
	private Timestamp dataInserimento;
	private Indirizzo indirizzo = new Indirizzo();
	private List<ControlloInterno> controlliInterni = new ArrayList<ControlloInterno>();
	public GestoreAcque gestorePadre;
	
	 
	public PuntoPrelievo(){}
	public static PuntoPrelievo build(Connection db, ResultSet rs,boolean searchCI)
	{
		PuntoPrelievo toRet = new PuntoPrelievo();
		
		try{toRet.setId(rs.getInt("id"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDenominazione(rs.getString("denominazione"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setIdAsl(rs.getInt("id_asl"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setNomeGestore(rs.getString("nome_gestore"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setIdIndirizzo(rs.getInt("id_indirizzo"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setIdLookupTipologia(rs.getInt("id_lookup_tipologia"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setStato(rs.getString("stato"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setCodice(rs.getString("codice"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setCodiceGisa(rs.getString("codice_gisa"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setIdGestore(rs.getInt("id_gestore"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDescrizioneAsl(rs.getString("descrizione_asl"));} catch(Exception ex){}
		try{toRet.setDescrizioneTipologia(rs.getString("descrizione_tipologia"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setDataInserimento(rs.getTimestamp("data_inserimento"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.setTipoDecreto(rs.getString("tipo_decreto"));} catch(Exception ex){}
		
		toRet.indirizzo = new Indirizzo();
		
		try{toRet.indirizzo.setDescrizioneToponimo(rs.getString("desc_toponimo"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.indirizzo.setVia(rs.getString("desc_via"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.indirizzo.setCivico(rs.getString("desc_civico"));}catch(Exception ex){ex.printStackTrace();}
		try{toRet.indirizzo.setComune(rs.getInt("id_comune"));} catch(Exception ex){ex.printStackTrace();}
		try{toRet.indirizzo.setDescrizioneComune(rs.getString("descrizione_comune"));} catch(Exception ex){ex.printStackTrace();}
		try{toRet.indirizzo.setLatitudine(rs.getDouble("latitudine"));} catch(Exception ex){ex.printStackTrace();}
		try{toRet.indirizzo.setLongitudine(rs.getDouble("longitudine"));} catch(Exception ex){ex.printStackTrace();}
		
		/*provo ad agganciarci i controlli interni eventualmente associati */
		if(searchCI)
		{
			try
			{
				toRet.setControlliInterni(ControlloInterno.searchAllPerPuntoPrelievo(db, toRet.getId(),toRet.getTipoDecreto()));
			} catch(Exception ex){ex.printStackTrace();}
		}
		
		
		return toRet;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*necessita di : descrizione tipologia,  indirizzo completo (salvato in  via)[che poi verra' normalizzato] e desc comune, desc asl e tutti i dati del punto di prelievo */
	/*ritorna messaggio che indica esito */
	public String insert(Connection db)  
	{
		
		
		String toRet = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			/*data la descrizione della tipologia, ne ritrovo l'id. 
			 * Se non esiste la tipologia, la aggiungo
			 */
			pst = db.prepareStatement("select * from gestori_acque_lookup_tipologie_punti_prelievo where regexp_replace(description,'\\s','','g') ilike regexp_replace(?,'\\s','','g')");
			 
			
			pst.setString(1, getDescrizioneTipologia().toUpperCase());
			rs = pst.executeQuery();
			
			/*dalla descrizione della tipologia creo / inserisco id tipologia */
			if(rs.next())
			{
				int idTipologia = rs.getInt("code");
				this.setIdLookupTipologia(idTipologia);
			}
			else
			{
				/*rs.close();
				pst.close();
				pst = db.prepareStatement("insert into gestori_acque_lookup_tipologie_punti_prelievo(code,description,enabled) values((select  case when count(*) = 0 then 1 else max(code) +1 end  from gestori_acque_lookup_tipologie_punti_prelievo ),?,true) returning code");
				pst.setString(1, getDescrizioneTipologia().toUpperCase());
				pst.execute();
				rs = pst.getResultSet();
				rs.next();
				int idTipologiaInserita = rs.getInt(1);  
				setIdLookupTipologia(idTipologiaInserita);
				*/
				/*la tipologia deve esistere */
				throw new EccezioneDati("TIPOLOGIA \""+getDescrizioneTipologia().toUpperCase()+"\" INESISTENTE");
				
			}
			rs.close();
			pst.close();
			
			
			
			/*analisi indirizzo */
			if(getIndirizzo() == null)
			{
				throw new EccezioneDati("INDIRIZZO MANCANTE O INCOMPLETO");
			}
			
			/*dalla descrizione dell'indirizzo creo/ottengo normalizzazione indirizzo */
			/*pst = db.prepareStatement("select * from normalizzazione_indirizzo(?)");
			pst.setString(1,getIndirizzo().getVia());
			rs  = pst.executeQuery();
			rs.next();*/
			/*l'ho normalizzata e la salvo nel bean */
			/*getIndirizzo().setToponimo(rs.getString("toponimoout"));
			getIndirizzo().setDescrizioneToponimo(rs.getString("toponout"));
			getIndirizzo().setVia(rs.getString("viaout"));
			getIndirizzo().setCivico(rs.getString("civicoout"));

			rs.close();
			pst.close();*/

			/*dalla descrizione del comune ottengo l'id */
			getIndirizzo().setIdComuneFromdescrizione(db, getIndirizzo().getDescrizioneComune());

			
			
			/*controllo che il codice del comune del punto di prelievo, appartenga al set dei comuni
			 * validi per il gestore acque
			 */
			
			/*String descComune = getIndirizzo().getDescrizioneComune();
			if(descComune == null || descComune.trim().length() == 0
					|| gestorePadre.getComuniAccettatiPerPP().get(descComune.toUpperCase()) == null)
				
			{
				throw new EccezioneDati("Comune ("+descComune+") non risconosciuto tra quelli validi per il Gestore Rete ("+gestorePadre.getNome()+")");
			}
			*/
			/*PER HP NON SI POSSONO AVERE STESSI INDIRIZZI PER DIFFERENTI PUNTI PRELIEVO (QUINDI NON FACCIO CONTROLLO PER SEMPLICITA TODO )*/

			pst = db.prepareStatement("insert into gestori_acque_indirizzi(id_toponimo,desc_toponimo,desc_via,desc_civico,id_comune,latitudine,longitudine) values(?,?,?,?,?,?,?) returning id");
			if(getIndirizzo().getToponimo()!=null && !getIndirizzo().getToponimo().equals(""))
				pst.setInt(1, Integer.parseInt(getIndirizzo().getToponimo()));
			else
				pst.setObject(1, null);
			if(getIndirizzo().getDescrizioneToponimo()!=null)
				pst.setString(2, getIndirizzo().getDescrizioneToponimo().toUpperCase());
			else
				pst.setString(2, getIndirizzo().getDescrizioneToponimo());
			if(getIndirizzo().getVia()!=null)
				pst.setString(3, getIndirizzo().getVia().toUpperCase());
			else
				pst.setString(3, getIndirizzo().getVia().toUpperCase());
			if(getIndirizzo().getCivico()!=null)
				pst.setString(4, getIndirizzo().getCivico().toUpperCase());
			else
				pst.setString(4, getIndirizzo().getCivico());
			pst.setInt(5, getIndirizzo().getComune());
			pst.setDouble(6, getIndirizzo().getLatitudine());
			pst.setDouble(7, getIndirizzo().getLongitudine());
			pst.execute();
			rs = pst.getResultSet();
			rs.next();
			setIdIndirizzo(rs.getInt(1));

				
				
				
			
			/*dalla descrizione asl setto id asl*/
			pst.close();
			rs.close();
			try
			{
				pst = db.prepareStatement("select * from lookup_site_id where description ilike ? and enabled = true");
				pst.setString(1, getDescrizioneAsl().toUpperCase());
				rs = pst.executeQuery();
				if(!rs.next())
				{
					throw new EccezioneDati("ASL \""+getDescrizioneAsl().toUpperCase()+"\" NON TROVATA");
				}
				
				int idAsl = rs.getInt("code");
				setIdAsl(idAsl);
				
			}
			catch(Exception ex)
			{
				setIdAsl(16); //fuori regione
			}
			
			
			/*ora posso inserire il punto prelievo */
			/*controllo che non esista punto di prelievo con quel nome */
			pst.close();
			rs.close();
//			pst = db.prepareStatement("select * from gestori_acque_punti_prelievo where denominazione ilike ?");
//			pst.setString(1, getDenominazione());
//			rs = pst.executeQuery();
//			if(rs.next())
//			{
//				throw new EccezioneDati("Denominazione Punto di Prelievo duplicata per punto prelievo "+getDenominazione());
//			}
			
			 
			pst = db.prepareStatement("insert into gestori_acque_punti_prelievo(denominazione, id_asl, id_indirizzo, id_lookup_tipologia,stato,codice,id_gestore,data_inserimento,codice_gisa)"
					+ " values(?,?,?,?,upper(?)::stato_punto_prelievo,?,?,CURRENT_TIMESTAMP,?) returning id");
			int u= 0;
			
			int idGestore = getIdGestore() != null && getIdGestore() > 0 ? getIdGestore( ) : gestorePadre.getId();
			
			pst.setString(++u, getDenominazione().toUpperCase());
			pst.setInt(++u,getIdAsl());
			pst.setInt(++u, getIdIndirizzo());
			pst.setInt(++u,getIdLookupTipologia());
			pst.setString(++u, getStato() != null ? getStato().toUpperCase() : null);
			pst.setString(++u,  getCodice() != null ? getCodice().toUpperCase() : null);
			pst.setInt(++u,idGestore);
			pst.setString(++u,idGestore + "__" + getCodice().toUpperCase());
			pst.execute();
			rs = pst.getResultSet();
			rs.next();
			
			setId(rs.getInt(1));
			
			toRet = "<br><font color='green'> Punto di prelievo con nome \""+getDenominazione() + "\" sito in \""+getIndirizzo().getVia() + "\" per Gestore Acque \""+ gestorePadre.getNome() +"\" .... INSERITO CORRETTAMENTE</font>";
			System.out.println("INSERITO PUNTO PRELIEVO \""+getDenominazione()+"\"");
		}
		 
	    catch(EccezioneDati ex)
		{
			toRet = "<br><font color='red'> Punto di prelievo con nome \""+getDenominazione() + "\" sito in \""+getIndirizzo().getVia() + "\" per Gestore Acque \""+ gestorePadre.getNome() +"\" ....  NON INSERITO CORRETTAMENTE : <b>"+ex.getMessage()+"</b> </font>";
			System.out.println("NON INSERITO PUNTO PRELIEVO \""+getDenominazione()+"\""+ex.getMessage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("NON INSERITO PUNTO PRELIEVO "+getDenominazione());
			toRet = "<br><font color='red'> PUNTO DI PRELIEVO CON NOME \""+getDenominazione() + "\" SITO IN \""+getIndirizzo().getVia() + "\" per Gestore Acque \""+ gestorePadre.getNome() +"\" ....  NON INSERITO CORRETTAMENTE : <b>ERRORE DI SISTEMA :replace:</b> </font>";

			//toRet = toRet.replace(":replace:", ex.getMessage().toLowerCase().matches("^.*(unique|duplicate).*$") ? " Denominazione duplicata per punto prelievo" : "");
						
			toRet = toRet.replace(":replace:", (ex.getMessage().toLowerCase().contains("unique") || ex.getMessage().toLowerCase().contains("duplicate") || ex.getMessage().toLowerCase().contains("univoco")  || ex.getMessage().toLowerCase().contains("duplicato")) ? " RECORD DUPLICATO" : "");

		}
		finally
		{
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
		}
	
		return toRet;
	
	}
	
	
	
	
	
	
	/*necessita di : descrizione tipologia,  indirizzo completo (salvato in  via)[che poi verra' normalizzato] e desc comune, desc asl e tutti i dati del punto di prelievo */
	/*ritorna messaggio che indica esito */
	public String modifyStato(Connection db, UserBean utente)  
	{
		String toRet = "<br><font color='###color###'> Punto di prelievo con nome \""+getDenominazione() + "\" sito in \""+getIndirizzo().getVia() + "\" ###esito### CORRETTAMENTE</font>";
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			if(db.getAutoCommit())
			{
				db.setAutoCommit(false);
			}
			
			//Calcolo il nuovo stato
			String nuovoStato = calcolaCambioStato(stato);
			
			//Aggiornamento Stato
			pst = db.prepareStatement("update gestori_acque_punti_prelievo set stato = upper(?)::stato_punto_prelievo where id = ? ");
			
			pst.setString(1, nuovoStato);
			pst.setInt(2,id);
			pst.execute();
			//Fine Aggiornamento Stato
			
			//Storicizzazione modifica stato
			pst = db.prepareStatement("INSERT INTO gestori_acque_punti_prelievo_log_modifica_stato(stato_precedente, stato_attuale, id_punto_prelievo, data_modifica, utente_modifica) " +
                                      "VALUES (?, ?, ?, current_timestamp,?)");
			
			pst.setString(1, stato);
			pst.setString(2, nuovoStato);
			pst.setInt(3,id);
			pst.setInt(4,utente.getUserId());
			pst.execute();
			//Fine Storicizzazione modifica stato
			
			//Se tutto è andato come Dio comanda posso committare tutte le modifiche
			db.commit();
			
			toRet = toRet.replace("###color###", "green").replace("###esito###", "AGGIORNATO");
			System.out.println(toRet);
			
		}
		 
		catch(Exception ex)
		{
			ex.printStackTrace();
			toRet = toRet.replace("###color###", "red").replace("###esito###", "NON AGGIORNATO");
			System.out.println(toRet);
		}
		finally
		{
			try
			{
				pst.close();
			} 
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				rs.close();
			} 
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	
		return toRet;
	}
	
	
	
	public static PuntoPrelievo searchByIdGestore(Connection conn, int idGestore,int idPuntoPrelievo,boolean searchCI) throws Exception
	{
		PreparedStatement pst  = null;
		ResultSet rs = null;
		
		try
		{
			ArrayList<PuntoPrelievo> allPPsPerGestore = searchAllPerGestore(conn,idGestore,searchCI);
			for(PuntoPrelievo pp0 : allPPsPerGestore)
			{
				if (pp0.getId().equals(idPuntoPrelievo))
				{
					return pp0;
				}
			}
			throw new Exception("Id puntoPrelievo ("+idPuntoPrelievo+") Non trovato per il gestore fornito("+idGestore+") !");
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
	
	public static PuntoPrelievo searchById(Connection db, int id, boolean searchCI) throws Exception
	{
		PuntoPrelievo toRet = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	
		try
		{
			pst = db.prepareStatement("select gest.nome as nome_gestore,tipologia.description as descrizione_tipologia, comuni1.nome as descrizione_comune, asl.description as descrizione_asl,"
					+ " pp.*, gest.* , ind.* from gestori_acque_punti_prelievo pp join gestori_acque_gestori gest on "
					+ " gest.id = pp.id_gestore left join lookup_site_id asl on asl.code = pp.id_asl left join "
					+ " gestori_acque_lookup_tipologie_punti_prelievo tipologia on tipologia.code = pp.id_lookup_tipologia"
					+ " left join gestori_acque_indirizzi ind on ind.id = pp.id_indirizzo left join comuni1 on ind.id_comune = comuni1.id "
					+ "where pp.id = ? and pp.trashed_date is null "
					+" order by pp.data_inserimento desc,asl.description, pp.denominazione"  
					 );
			pst.setInt(1, id);
			
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet = new PuntoPrelievo().build(db,rs,false);
			}
			
			 
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	
	
	public static PuntoPrelievo searchByDenominazione(Connection db, int idGestore,String denominazionePuntoPrelievo,boolean searchCI) throws Exception
	{
		PreparedStatement pst  = null;
		ResultSet rs = null;
		
		try
		{
			ArrayList<PuntoPrelievo> allPPsPerGestore = searchAllPerGestore(db,idGestore,searchCI);
			for(PuntoPrelievo pp0 : allPPsPerGestore)
			{
				if (pp0.getDenominazione().equalsIgnoreCase(denominazionePuntoPrelievo))
				{
					return pp0;
				}
			}
			throw new EccezioneDati(" puntoPrelievo con denominazione ("+denominazionePuntoPrelievo+") Non trovato per il gestore fornito("+idGestore+") !");
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
	
	
	
	/*code non e' l'id ma il codice parlante */
	public static PuntoPrelievo searchByCodiceGisaPP(Connection db, int idGestore, String codiceGisa,boolean searchCI) throws EccezioneDati, Exception
	{
		PreparedStatement pst  = null;
		ResultSet rs = null;
		
		try
		{
			ArrayList<PuntoPrelievo> listaPPs = search(db,idGestore,searchCI,codiceGisa,null);
			if(listaPPs.isEmpty())
				throw new EccezioneDati(" puntoPrelievo con codice ("+codiceGisa+") Non trovato per il gestore fornito("+idGestore+") !");
			else
				return listaPPs.get(0);
		}
		catch(EccezioneDati ex)
		{
			throw ex;
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
	
	
	
	public static ArrayList<PuntoPrelievo> searchAllPerGestore(Connection db, int idGestore,boolean searchCI) throws Exception
	{

		ArrayList<PuntoPrelievo> toRet = new ArrayList<PuntoPrelievo>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		 
		
	
		try
		{
			pst = db.prepareStatement("select gest.nome as nome_gestore,tipologia.description as descrizione_tipologia, comuni1.nome as descrizione_comune, asl.description as descrizione_asl,"
					+ " pp.*, gest.* , ind.* from gestori_acque_punti_prelievo pp join gestori_acque_gestori gest on "
					+ " gest.id = pp.id_gestore left join lookup_site_id asl on asl.code = pp.id_asl left join "
					+ " gestori_acque_lookup_tipologie_punti_prelievo tipologia on tipologia.code = pp.id_lookup_tipologia"
					+ " left join gestori_acque_indirizzi ind on ind.id = pp.id_indirizzo left join comuni1 on ind.id_comune = comuni1.id "
					+ "where (-1=? or gest.id = ?) and pp.trashed_date is null "
					+" order by pp.data_inserimento desc,asl.description, pp.denominazione"  
					 );
			pst.setInt(1, idGestore);
			pst.setInt(2, idGestore);
			
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new PuntoPrelievo().build(db,rs,searchCI));
			}
			
			 
			 
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		
	
	}
	
	
	
	public static ArrayList<PuntoPrelievo> search(Connection db, int idGestore,boolean searchCI,String codiceGisa, String codice) throws Exception
	{

		ArrayList<PuntoPrelievo> toRet = new ArrayList<PuntoPrelievo>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		 
		
	
		try
		{
			pst = db.prepareStatement("select gest.nome as nome_gestore,tipologia.description as descrizione_tipologia, comuni1.nome as descrizione_comune, asl.description as descrizione_asl,"
					+ " pp.*, gest.* , ind.* from gestori_acque_punti_prelievo pp join gestori_acque_gestori gest on "
					+ " gest.id = pp.id_gestore left join lookup_site_id asl on asl.code = pp.id_asl left join "
					+ " gestori_acque_lookup_tipologie_punti_prelievo tipologia on tipologia.code = pp.id_lookup_tipologia"
					+ " left join gestori_acque_indirizzi ind on ind.id = pp.id_indirizzo left join comuni1 on ind.id_comune = comuni1.id "
					+ "where pp.trashed_date is null and gest.id = ? and (upper(pp.codice_gisa) =   upper(?) or ? is null) and (upper(pp.codice) = upper(?) or ? is null) "
					+" order by pp.data_inserimento desc,asl.description, pp.denominazione"  
					 );
			pst.setInt(1, idGestore);
			pst.setString(2, codiceGisa);
			pst.setString(3, codiceGisa);
			pst.setString(4, codice);
			pst.setString(5, codice);
			
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRet.add(new PuntoPrelievo().build(db,rs,searchCI));
			}
			
			 
			 
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		
	
	}
	
	
	
	public static ArrayList<PuntoPrelievo> searchChunkPerGestore(Connection db, int idGestore, int indiceChunk,boolean searchCI) throws Exception
	{
		ArrayList<PuntoPrelievo> toRet = new ArrayList<PuntoPrelievo>();
		ArrayList<PuntoPrelievo> toRetTemp = new ArrayList<PuntoPrelievo>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		/*indice chunk indica quale chunk usare */
		/*es se indice = 1 e chunk = 50 devo prendere da quello di indice 50 a quello di indice 99 (inclusi) */
		/*se indice = 0 allora prendo i primi dati dal chunk size*/
		/*quindi se indice = 0, allora uso la limit sul chunk size, altrimenti li prendo tutti e poi li filtro */
		
	
		try
		{
			pst = db.prepareStatement("select gest.nome as nome_gestore,tipologia.description as descrizione_tipologia, comuni1.nome as descrizione_comune ,asl.description as descrizione_asl,"
					+ " pp.*, gest.*, gest.nome as nome_gestore, ind.* from gestori_acque_punti_prelievo pp join gestori_acque_gestori gest on "
					+ " gest.id = pp.id_gestore left join lookup_site_id asl on asl.code = pp.id_asl left join "
					+ " gestori_acque_lookup_tipologie_punti_prelievo tipologia on tipologia.code = pp.id_lookup_tipologia"
					+ " left join gestori_acque_indirizzi ind on ind.id = pp.id_indirizzo left join comuni1 on ind.id_comune = comuni1.id "
					+ " where pp.trashed_date is null and (?=-1 or gest.id = ? ) "
					+" order by pp.stato,pp.data_inserimento desc, asl.description, pp.denominazione" /*ordinamento necessario visto che si lavora per chunk */
					//+ (indiceChunk == 0 ? (" limit "+ CHUNK_SIZE) : "")
					 );
			pst.setInt(1, idGestore);
			pst.setInt(2, idGestore);
			
			rs = pst.executeQuery();
			while(rs.next())
			{
				toRetTemp.add(new PuntoPrelievo().build(db,rs,searchCI));
			}
			
			/*se il chunk index e' > 0 ...*/
			if(indiceChunk > 0)
			{
				for(int j = CHUNK_SIZE * indiceChunk; j < (CHUNK_SIZE * (indiceChunk+1)) && j < toRetTemp.size(); j++)
				{
					toRet.add(toRetTemp.get(j));
					
				}
				return toRet;
			}
			else
			{
				return toRetTemp; /*in caso il chunk index era 0 allora in toRetTemp ho gia' i primi CHUNK_SIZE punti prelievo richiesti */
			}
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		
		
	}
	
	
	private String calcolaCambioStato(String stato)
	{
		String toRet = "INATTIVO";
		if(stato.equalsIgnoreCase("inattivo"))
			toRet = "ATTIVO";
		
		return toRet;
	}
	
	
	
	
	
	
}
