/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricercaunica.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.opu.base.OperatorePerDuplicati;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

import ext.aspcfs.modules.apiari.base.Operatore;

public class InterfAnalisiDuplicatiOpu extends CFSModule {
	
	
	
	
	public String executeCommandRicercaTuttiDuplicatiPerPIva(ActionContext cont)
	{
		Connection db = null;
		String view = null;
		
		String pIvaCercata = cont.getRequest().getParameter("pIvaCercata");
		
		try
		{
			
			if(pIvaCercata != null && !pIvaCercata.equals("") && !pIvaCercata.equals(" ")) 
			{
				db = getConnection(cont);
				ArrayList<OperatorePerDuplicati> operatoriConDuplicati = invocaDbiRicercaTuttiDuplicatiPerPIva(db, pIvaCercata);
				
				if(operatoriConDuplicati.size() == 1) //allora non esistono duplicati, quindi presentiamo lista vuota
				{
					operatoriConDuplicati.clear();
				}
				
				cont.getRequest().setAttribute("pIvaCercata",pIvaCercata);
				cont.getRequest().setAttribute("duplicatiPerPIva", operatoriConDuplicati);
				
			}
			
			view = "paginaRicercaDuplicatiPerPIva";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			view = "SystemError";
		}
		finally
		{
			freeConnection(cont,db);
		}
		
		return view;
	}
	
	
	
	public String executeCommandPreparaFormPerConvergenzaDuplicatiOperatore(ActionContext cont)
	{
		String view = null;
		Connection db = null;
		
		
		//utilizzo i dati sulle entries selezionate, per ripetere una ricerca per tutti i duplicati, prendendo solo quelli
		//selezionati, e rimandandoli al client
		//I QUALI NON ESISTE NEANCHE UN VALORE, DEVO FORNIRE L'INTERO SET DEI VALORI CHE SI POSSONO SCEGLIERE
		try
		{
			String pIvaRichiesta = cont.getRequest().getParameter("pIvaRichiesta");
			String jsonStrEntriesChecked = cont.getRequest().getParameter("entriesChecked");
			db = getConnection(cont);
			ArrayList<OperatorePerDuplicati> allDups = invocaDbiRicercaTuttiDuplicatiPerPIva(db, pIvaRichiesta);
			//elimino quelli che non rientrano tra i miei precedentemente checked
			//NB: gli id stabilimento rappresentano le diverse entry
			
			ArrayList<OperatorePerDuplicati> onlyChecked = new ArrayList<OperatorePerDuplicati>();
			
			
			
			for(int i=0;i<allDups.size();i++)
			{
				
				if(jsonStrEntriesChecked.contains( allDups.get(i).getIdStabilimento()+""  )) //se non c'e' l'id stabilimento (quello usato come chiave) tra i checked
				{
					//allDups.remove(i);
					onlyChecked.add(allDups.get(i));
					
				}
				
			}
			
			cont.getRequest().setAttribute("entriesChecked", onlyChecked);
			cont.getRequest().setAttribute("pIvfaRichiesta",pIvaRichiesta);
			
			//costruzione lookup list per i casi in cui alcune proprieta non hanno nessun candidato
			LookupList tipoImpresa = new LookupList(db, "lookup_opu_tipo_impresa");
			tipoImpresa.addItem(-1, "seleziona tipo impresa");
			
			LookupList lookupToponimi = new LookupList(db,"lookup_toponimi");
			lookupToponimi.addItem(-1,"seleziona toponimo");
			
			LookupList lookupTipoSocieta = new LookupList(db,"lookup_opu_tipo_impresa_societa");
			lookupTipoSocieta.addItem(-1,"seleziona tipo societa");
			
			
			HashMap<Integer,ArrayList<Integer>> miaCorrispTipoImpresaToTipoSocieta = getCorrispondenzaImpresaSocieta(db);
			
			//LookupList lookupTipoSocieta = new LookupList(db,"lookup_opu_tipo_societa");
			//lookupToponimi.addItem(-1,"seleziona")
			
			cont.getRequest().setAttribute("lookupListTipoImpresa", tipoImpresa);
			cont.getRequest().setAttribute("lookupToponimi",lookupToponimi);
			cont.getRequest().setAttribute("lookupTipoSocieta",lookupTipoSocieta);
			cont.getRequest().setAttribute("lookupTipoImpresaToTipoSocieta",miaCorrispTipoImpresaToTipoSocieta);
			
			view = "paginaPerFormPerConvergenzaOperatore";
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			view = "SystemError";
		}
		finally
		{
			freeConnection(cont,db);
		}
		
		return view;
	}
	
	
	
	public String executeCommandPreparaFormPerConvergenzaDuplicatiImpianti(ActionContext cont)
	{
		String view = null;
		Connection db = null;
		
		
		
		try
		{
			String pIvaRichiesta = cont.getRequest().getParameter("pIvaRichiesta");
			String jsonStrEntriesChecked = cont.getRequest().getParameter("entriesChecked");
			db = getConnection(cont);
			ArrayList<OperatorePerDuplicati> allDups = invocaDbiRicercaTuttiDuplicatiPerPIva(db, pIvaRichiesta);
			//elimino quelli che non rientrano tra i miei precedentemente checked
			//NB: gli id stabilimento rappresentano le diverse entry
			
			ArrayList<OperatorePerDuplicati> onlyChecked = new ArrayList<OperatorePerDuplicati>();
			for(int i=0;i<allDups.size();i++)
			{
				
				if(jsonStrEntriesChecked.contains( allDups.get(i).getIdStabilimento()+""  )) //se non c'e' l'id stabilimento (quello usato come chiave) tra i checked
				{
					onlyChecked.add(allDups.get(i));
					//System.out.println("mi tengo il checked con id stab "+allDups.get(i).getIdStabilimento());
				}
			}
			
			
			cont.getRequest().setAttribute("entriesChecked", onlyChecked);
			cont.getRequest().setAttribute("pIvaRichiesta",pIvaRichiesta);
			
			//costruzione lookup list per i casi in cui alcune proprieta non hanno nessun candidato
			LookupList tipoAttivita = new LookupList(db, "opu_lookup_tipologia_attivita");
			tipoAttivita.addItem(-1, "seleziona tipo attivita");
			
			LookupList tipoCarattere = new LookupList(db,"opu_lookup_tipologia_carattere");
			tipoCarattere.addItem(-1,"seleziona tipo carattere");
			
			LookupList toponimi = new LookupList(db,"lookup_toponimi");
			toponimi.addItem(-1,"seleziona toponimo");
			
			
			
			//LookupList lookupTipoSocieta = new LookupList(db,"lookup_opu_tipo_societa");
			//lookupToponimi.addItem(-1,"seleziona")
			
			cont.getRequest().setAttribute("lookupListTipoAttivita", tipoAttivita);
			cont.getRequest().setAttribute("lookupListTipoCarattere",tipoCarattere);
			cont.getRequest().setAttribute("lookupToponimi",toponimi);
			
			view = "paginaPerFormPerConvergenzaImpianto";
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			view = "SystemError";
		}
		finally
		{
			freeConnection(cont,db);
		}
		
		return view;
	}
	
	
	
	
	
	
	private HashMap<Integer, ArrayList<Integer>> getCorrispondenzaImpresaSocieta(Connection db) throws SQLException {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		HashMap<Integer,ArrayList<Integer>> toRet = new HashMap<Integer,ArrayList<Integer>>();
		
		try
		{
			pst = db.prepareStatement("select * from lookup_opu_tipo_impresa_societa");
			rs = pst.executeQuery();
			while(rs.next())
			{
				Integer impresa = rs.getInt("code_lookup_opu_tipo_impresa");
				if(!toRet.containsKey(impresa))
				{
					toRet.put(impresa, new ArrayList<Integer>());
				}
				toRet.get(impresa).add(new Integer(rs.getInt("code")));
			}
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(rs != null) rs.close();
			if(pst != null) pst.close();
		}
		
	}
	
	
	
	
	



	public String executeCommandConvergiDuplicati(ActionContext cont)
	{
		String view = null;
		Connection db = null;
		
		try
		{
			db = getConnection(cont);
			JSONObject jsonRepr = new JSONObject(cont.getRequest().getParameter("datiDaForm"));
			
			if(cont.getRequest().getParameter("perImpianti") == null) //per impresa
			{
				OperatorePerDuplicati opConDatiSceltiDaForm = new OperatorePerDuplicati();
				opConDatiSceltiDaForm.costruisciDaJsonFormConvergenzaImpresa(jsonRepr);
				invocaDbiConvergenzaImpresa(db,opConDatiSceltiDaForm);
			}
			else //per impianti
			{
				OperatorePerDuplicati opConDatiSceltiDaForm = new OperatorePerDuplicati();
				opConDatiSceltiDaForm.costruisciDaJsonFormConvergenzaImpianti(jsonRepr);
				invodaDbiConvergenzaImpianti(db,opConDatiSceltiDaForm);
			}
//			//controllo se il tipo societa inserito e consistente col tipo impresa
//			HashMap<Integer,ArrayList<Integer>> miaCorrispTipoImpresaToTipoSocieta = getCorrispondenzaImpresaSocieta(db);
//			boolean corrispondenzaValida = false;
//			
//			if(!corrispondenzaValida)
//			{
//				cont.getRequest().setAttribute("msgEsito","CORRISPONDENZA TRA TIPO IMPRESA / TIPO SOCIETA NON VALIDA");
//			}
			
			
			
//			System.out.println(jsonRepr);
			//SE SUCCESSO
			cont.getRequest().setAttribute("msgEsito","CONVERGENZA EFFETTUATA CON SUCCESSO");
			return executeCommandRicercaTuttiDuplicatiPerPIva(cont);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			view = "SystemError";
		}
		finally
		{
			freeConnection(cont,db);
		}
		return view;
	}
	
	
	
	
	



	private void invocaDbiConvergenzaImpresa(Connection conn,OperatorePerDuplicati opConDatiSceltiDaForm) throws Exception
	{
		
		ResultSet rs = null;
		PreparedStatement pst = null;
		try
		{
			pst = conn.prepareStatement( " select * from public_functions.suap_convergenza_impresa(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " );
			pst.setInt(1,opConDatiSceltiDaForm.getImpresaIdTipoImpresa() != null ? opConDatiSceltiDaForm.getImpresaIdTipoImpresa() : 0);
			pst.setInt(2,opConDatiSceltiDaForm.getImpresaIdTipoSocieta() != null ? opConDatiSceltiDaForm.getImpresaIdTipoSocieta() : 0);
			pst.setString(3,opConDatiSceltiDaForm.getRagioneSociale() );
			pst.setString(4,opConDatiSceltiDaForm.getPartitaIva());
			pst.setString(5,opConDatiSceltiDaForm.getCodFiscale());
			pst.setString(6,opConDatiSceltiDaForm.getDomicilioDigitale());
			pst.setString(7,opConDatiSceltiDaForm.getNomeRappSedeLegale());
			pst.setString(8,opConDatiSceltiDaForm.getCognomeRappSedeLegale());
			pst.setString(9,opConDatiSceltiDaForm.getCfRappSedeLegale());
			pst.setTimestamp(10,opConDatiSceltiDaForm.getDataNascitaRappSedeLegale() != null ? new Timestamp(opConDatiSceltiDaForm.getDataNascitaRappSedeLegale().getTime()) : null);
			pst.setString(11,opConDatiSceltiDaForm.getSessoRappSedeLegale());
			pst.setString(12,opConDatiSceltiDaForm.getComuneNascitaRappSedeLegale());
			pst.setString(13,opConDatiSceltiDaForm.getSiglaProvSoggFisico());
			pst.setString(14,opConDatiSceltiDaForm.getComuneResidenza());
			pst.setString(15,opConDatiSceltiDaForm.getIndirizzoRappSedeLegale());
			pst.setInt(16,opConDatiSceltiDaForm.getIdToponimoResidenza() != null ? opConDatiSceltiDaForm.getIdToponimoResidenza() : 0);
			pst.setString(17,opConDatiSceltiDaForm.getCivicoResidenza());
			pst.setString(18,opConDatiSceltiDaForm.getCapResidenza()); //cap_residenza
			pst.setString(19,opConDatiSceltiDaForm.getNazioneResidenza()); //nazione residenza
			pst.setString(20,opConDatiSceltiDaForm.getSiglaProvLegale());
			pst.setString(21,opConDatiSceltiDaForm.getComuneSedeLegale());
			pst.setString(22,opConDatiSceltiDaForm.getIndirizzoSedeLegale());
			pst.setString(23,opConDatiSceltiDaForm.getCivicoSedeLegale());
			pst.setString(24,opConDatiSceltiDaForm.getCapSedeLegale());
			pst.setString(25,opConDatiSceltiDaForm.getNazioneSedeLegale());
			pst.setInt(26,opConDatiSceltiDaForm.getIdToponimoSedeLegale() != null ? opConDatiSceltiDaForm.getIdToponimoSedeLegale() : 0);
			pst.setInt(27, opConDatiSceltiDaForm.getIdUtente() != null ? opConDatiSceltiDaForm.getIdUtente() : 0);
			pst.setArray(28, conn.createArrayOf("integer", opConDatiSceltiDaForm.getArrayImprese().toArray(new Integer[opConDatiSceltiDaForm.getArrayImprese().size()])));
			rs = pst.executeQuery();
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(rs != null) rs.close();
			if(pst != null) pst.close();
		}
		
	}


	private void invodaDbiConvergenzaImpianti(Connection db, OperatorePerDuplicati opConDatiSceltiDaForm) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = db.prepareStatement("select * from public_functions.suap_convergenza_stabilimenti(?, ?, ?, "+
					"?, ?, ?, ?, ?, ?, ?, ?,"+
					"? , ?  )");
			pst.setInt(1, opConDatiSceltiDaForm.getIdOperatore());
			pst.setInt(2,opConDatiSceltiDaForm.getStabIdAttivita());
			pst.setInt(3, opConDatiSceltiDaForm.getStabIdCarattere());
			pst.setString(4, opConDatiSceltiDaForm.getComuneStab());
			pst.setString(5,opConDatiSceltiDaForm.getIndirizzoStab());
			pst.setString(6,opConDatiSceltiDaForm.getSiglaProvOperativa());
			pst.setInt(7,opConDatiSceltiDaForm.getIdToponimoStab());
			pst.setString(8, opConDatiSceltiDaForm.getCapSedeOperativa()); //cap sede operativa
			pst.setString(9, opConDatiSceltiDaForm.getCivicoSedeStab());
			pst.setString(10,opConDatiSceltiDaForm.getNazioneSedeOperativa()); //nazione sede operativa
			pst.setTimestamp(11, opConDatiSceltiDaForm.getDataInizioAttivita() != null ? new Timestamp(opConDatiSceltiDaForm.getDataInizioAttivita().getTime()) : null);
			pst.setArray(12, db.createArrayOf("integer",opConDatiSceltiDaForm.getArrayImpianti().toArray(new Integer[opConDatiSceltiDaForm.getArrayImpianti().size()])));
			pst.setInt(13, opConDatiSceltiDaForm.getIdUtente());
			rs = pst.executeQuery();
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(rs != null) rs.close();
			if(pst != null) pst.close();
		}
	}

	private ArrayList<OperatorePerDuplicati> invocaDbiRicercaTuttiDuplicatiPerPIva(Connection db, String PIva) throws Exception
	{
		ArrayList<OperatorePerDuplicati> entries = new ArrayList<OperatorePerDuplicati>();
		ResultSet rs = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs2 = null;
		
		try
		{
			String query = "select id_stabilimento_out, id_opu_operatore_out, impresa_id_tipo_impresa_out, tipo_impresa_out,"+
							"impresa_id_tipo_societa_out, tipo_societa_out, ragione_sociale_out, partita_iva_out, codice_fiscale_impresa_out,"+
							" domicilio_digitale_out, nome_rapp_sede_legale_out, cognome_rapp_sede_legale_out, cf_rapp_sede_legale_out, data_nascita_rapp_sede_legale_out,"+
							" sesso_out, comune_nascita_rapp_sede_legale_out, sigla_prov_soggfisico_out, comune_residenza_out, indirizzo_rapp_sede_legale_out, "+
							" toponimo_residenza_out, id_toponimo_residenza_out, civico_residenza_out, sigla_prov_legale_out, comune_sede_legale_out, indirizzo_sede_legale_out, civico_sede_legale_out, "+
							" toponimo_sede_legale_out, id_toponimo_sede_legale_out, sigla_prov_operativa_out, comune_stab_out, indirizzo_stab_out, civico_sede_stab_out, "+
							" toponimo_sede_stab_out, id_toponimo_stab_out, stab_id_attivita_out, stab_id_carattere_out, data_inizio_attivita_out, data_fine_attivita_out,"
							+ "cap_stab,nazione_stab,cap_residenza,nazione_residenza,cap_sede_legale,nazione_sede_legale "
							+ "from public_functions.suap_ricerca_anagrafiche_da_convergere(?)";
			pst = db.prepareStatement(query);
			pst.setString(1,PIva);
			rs = pst.executeQuery();
			while(rs.next())
			{
				OperatorePerDuplicati op = new OperatorePerDuplicati();
				op.buildRecordPerDuplicatiV2(rs);
				
				pst2 = db.prepareStatement("select description from opu_lookup_tipologia_attivita where code = ?");
				pst2.setInt(1, op.getStabIdAttivita());
				rs2 = pst2.executeQuery();
				if(rs2.next())
				{
					op.setDescrTipoAttivita(rs2.getString("description"));
				}
				else
				{
					System.out.println("ATTENZIONE ATTIVITA PRIVA DI DESCRIZIONE PER ENTRY "+op.getIdOperatore());
				}
				
				rs2.close();
				pst2.close();
				
				pst2 = db.prepareStatement("select description from opu_lookup_tipologia_carattere where code = ?");
				pst2.setInt(1,op.getStabIdCarattere());
				rs2 = pst2.executeQuery();
				if(rs2.next())
				{
					op.setDescrTipoCarattere(rs2.getString("description"));
				}
				else
				{
					System.out.println("ATTENZIONE CARATTERE PRIVO DI DESCRIZIONE PER ENTRY "+op.getIdOperatore());
				}
				entries.add(op);
				
			}
			
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(rs != null) rs.close();
			if(pst != null) pst.close();
			if(rs2 != null) rs2.close();
			if(pst2 != null) pst2.close();
			
		}
		
		return entries;
	}
	



	

	
	
}
