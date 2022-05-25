/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.TrasferimentoNoH;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.sinantropi.lookup.LookupSinantropiEtaDAO;
import it.us.web.dao.vam.TrasferimentoDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class List extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "LIST", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		
		//VECCHIA VERSIONE
		//Animale animale = (Animale) persistence.find( Animale.class, interoFromRequest( "idAnimale" ) );
		
		
		//VERSIONE PEZZOTTA JNDI
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		Statement st1 = null;
		ResultSet rs1 = null;
					
		Animale animale = null;
		int specie = 0;
		
		int idAnimale   = interoFromRequest( "idAnimale" );
		
		req.setAttribute( "errore_apertura_nuova_accettazione", 		req.getAttribute("errore_apertura_nuova_accettazione"));
		
		if (idAnimale < 0)
			idAnimale = Integer.parseInt((String) req.getAttribute("idAnimale"));
		
		
		int eta = 0;
		st1 = connection.createStatement();
		rs1 = st1.executeQuery("select * from animale where id = " + idAnimale + " and trashed_date is null " );
		if(rs1.next())
		{
			animale = new Animale();
			animale.setId(rs1.getInt("id"));
			animale.setDecedutoNonAnagrafe(rs1.getBoolean("deceduto_non_anagrafe"));
			animale.setTatuaggio(rs1.getString("tatuaggio"));
			animale.setRazza(rs1.getInt("razza"));
			animale.setIdentificativo(rs1.getString("identificativo"));
			animale.setEta(LookupSinantropiEtaDAO.getEta( rs1.getInt("eta"), connection));
			animale.setSesso(rs1.getString("sesso"));
			animale.setSpecieSinantropo(rs1.getString("specie_sinantropo"));
			animale.setRazzaSinantropo(rs1.getString("razza_sinantropo"));
			specie = rs1.getInt("specie");
			eta = rs1.getInt("eta");
			if (rs1.getString("razza_sinantropo")!=null && rs1.getString("razza_sinantropo").equals("")) 
				animale.setRazzaSinantropo(rs1.getString("razza_sinantropo"));
			if (rs1.getString("specie_sinantropo")!=null && !rs1.getString("specie_sinantropo").equals("")) 
				animale.setSpecieSinantropo(rs1.getString("specie_sinantropo"));
		}
		
		//TROVA SPECIE ANIMALE
		ArrayList<LookupSpecie> lsList = new ArrayList<LookupSpecie>();
		rs1 = st1.executeQuery("select * from  lookup_specie"); 
		while (rs1.next()){
			LookupSpecie ls = new LookupSpecie();
			ls.setId(rs1.getInt("id"));
			ls.setDescription(rs1.getString("description"));
			ls.setEnabled(rs1.getBoolean("enabled"));
			ls.setLevel(rs1.getInt("level"));
			lsList.add(ls);
		}
		
		int i = 0; 
		while (i<lsList.size()){
			if (lsList.get(i).getId()==specie){
				animale.setLookupSpecie(lsList.get(i));
				break;
			}
			else i++;
		}
		
		//TROVA ETA' DEI SINANTROPI
		if (specie==3){
			if (eta!=0){
				ArrayList<LookupSinantropiEta> lseList = new ArrayList<LookupSinantropiEta>();
				rs1 = st1.executeQuery("select * from  lookup_sinantropi_eta"); 
				while (rs1.next()){
					LookupSinantropiEta lse = new LookupSinantropiEta();
					lse.setId(rs1.getInt("id"));
					lse.setDescription(rs1.getString("description"));
					lseList.add(lse);
				}
				
				i = 0; 
				while (i<lseList.size()){
					if (lseList.get(i).getId()==eta){
						animale.setEta(lseList.get(i));
						break;
					}
					else i++;
				}
			}
			else animale.setEta(null);
		}
				
		//TROVA ELENCO ACCETTAZIONI
		ArrayList<Accettazione> accettazioni = new ArrayList<Accettazione>();
		rs1 = st1.executeQuery("select acc.id,acc.data,acc.progressivo, acc.richiedente_tipo, acc.entered_by "
				+ " from animale a join accettazione acc on a.id=acc.animale and acc.trashed_date is null "
				+ " join utenti_ u on acc.entered_by=u.id " +
				"where animale="+animale.getId()+" and u.clinica ="+utente.getClinica().getId());
		while (rs1.next()){
			Accettazione a = new Accettazione();
			a.setId(rs1.getInt("id"));
			a.setData(rs1.getDate("data"));
			a.setProgressivo(rs1.getInt("progressivo"));
			a.setLookupTipiRichiedente(popolaTipiRichiedenti(rs1.getInt("richiedente_tipo"), connection));	
			a.setEnteredBy(getEnteredByinfo(rs1.getInt("entered_by")));
//			a.setOperazioniRichieste(popolaOperazioniAccettazione(a.getId()));
			accettazioni.add(a);
		}
		
		//TROVA TRASFERIMENTI PER LA CLINICA DELL'UTENTE
		ArrayList<TrasferimentoNoH> trasfIngresso = new ArrayList<TrasferimentoNoH>();
		trasfIngresso = TrasferimentoDAO.getTrasferimentiNoH("I", animale.getId(), utente.getClinica().getId(), connection);
		
		
		//FINE PEZZOTTO JNDI
		
		
		
/*		ArrayList<Accettazione> accettazioni = (ArrayList<Accettazione>) persistence.createCriteria( Accettazione.class )
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.addOrder(Order.desc("data"))
		.addOrder(Order.desc("id"))
		.add( Restrictions.eq( "animale", animale ) )
		.createCriteria( "enteredBy" )
			.add( Restrictions.eq( "clinica", utente.getClinica() ) ).list();

		ArrayList<Trasferimento> trasfIngresso = (ArrayList<Trasferimento>) persistence.createCriteria( Trasferimento.class )
		.add( Restrictions.eq( "clinicaDestinazione", utente.getClinica()) )
		.add( Restrictions.isNull( "dataAccettazioneDestinatario" ) )
		.createAlias( "cartellaClinica", "cc" )
		.createAlias( "cc.accettazione", "acc" )
		.add( Restrictions.eq( "acc.animale", animale ))
		.list();
*/

		if( trasfIngresso.size() > 0 )
		{
			req.setAttribute( "tr",  trasfIngresso.get( 0 ) );
		}
		
		//Se il cane non è morto senza mc bisogna leggere in bdr l'info sullo stato attuale
		ServicesStatus status = new ServicesStatus();
		if(animale.getDecedutoNonAnagrafe())
		{
			rs1 = st1.executeQuery("select proprietario_cognome, proprietario_nome, proprietario_codice_fiscale, proprietario_documento, " +
			"proprietario_indirizzo, proprietario_cap, proprietario_comune, proprietario_provincia, proprietario_telefono, proprietario_tipo " +
			"from accettazione where animale="+animale.getId());
			
			if (rs1.next()){
				req.setAttribute("proprietarioCognome",  rs1.getString(1));
				req.setAttribute("proprietarioNome",  rs1.getString(2));
				req.setAttribute("proprietarioCodiceFiscale",  rs1.getString(3));
				req.setAttribute("proprietarioDocumento",  rs1.getString(4));
				req.setAttribute("proprietarioIndirizzo",  rs1.getString(5));
				req.setAttribute("proprietarioCap",  rs1.getString(6));
				req.setAttribute("proprietarioComune",  rs1.getString(7));
				req.setAttribute("proprietarioProvincia",  rs1.getString(8));
				req.setAttribute("proprietarioTelefono",  rs1.getString(9));
				req.setAttribute("proprietarioTipo",  rs1.getString(10));
				
				if(animale.getLookupSpecie().getId()==Specie.SINANTROPO)
				{
					Sinantropo s = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo());
					req.setAttribute("razza",AnimaliUtil.getSpecieSinantropoString(animale,s) + " - " + animale.getRazzaSinantropo());
				}
				else
				{
					if(animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.CANE)
						req.setAttribute("razza", (CaninaRemoteUtil.getRazza(animale.getRazza(), connectionBdu,req).getDescription()));
					else if(animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
						req.setAttribute("razza", (FelinaRemoteUtil.getRazza(animale.getRazza(), connectionBdu,req).getDescription()));
				}
			}
		}
		else
		{
			if (animale.getLookupSpecie().getId() == CANE) 
			{
				ProprietarioCane proprietario = CaninaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connectionBdu,req);
				if(proprietario!=null)
				{
					req.setAttribute("proprietarioCognome",  proprietario.getCognome());
					req.setAttribute("proprietarioNome",  proprietario.getNome());
					req.setAttribute("proprietarioCodiceFiscale",  proprietario.getCodiceFiscale());
					req.setAttribute("proprietarioDocumento",  proprietario.getDocumentoIdentita());
					req.setAttribute("proprietarioIndirizzo",  proprietario.getVia());
					req.setAttribute("proprietarioCap",  proprietario.getCap());
					req.setAttribute("proprietarioComune",  proprietario.getCitta());
					req.setAttribute("proprietarioProvincia",  proprietario.getProvincia());
					req.setAttribute("proprietarioTelefono",  proprietario.getNumeroTelefono());
					req.setAttribute("proprietarioTipo",  proprietario.getTipo());
				}
			}
			else if (animale.getLookupSpecie().getId() == GATTO) 
			{
				Colonia colonia = FelinaRemoteUtil.findColonia(animale.getIdentificativo(), utente, connectionBdu,req);
				req.setAttribute("colonia", colonia);
				
				ProprietarioGatto proprietario = FelinaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connectionBdu,req);
				if(proprietario!=null)
				{
					req.setAttribute("proprietarioCognome",  proprietario.getCognome());
					req.setAttribute("proprietarioNome",  proprietario.getNome());
					req.setAttribute("proprietarioCodiceFiscale",  proprietario.getCodiceFiscale());
					req.setAttribute("proprietarioDocumento",  proprietario.getDocumentoIdentita());
					req.setAttribute("proprietarioIndirizzo",  proprietario.getVia());
					req.setAttribute("proprietarioCap",  proprietario.getCap());
					req.setAttribute("proprietarioComune",  proprietario.getCitta());
					req.setAttribute("proprietarioProvincia",  proprietario.getProvincia());
					req.setAttribute("proprietarioTelefono",  proprietario.getNumeroTelefono());
					req.setAttribute("proprietarioTipo",  proprietario.getTipo());
				}
			}
			
			if(animale.getAccettazioneConOpDaCompletare()!=null)
				RegistrazioniUtil.sincronizzaDaBdu(animale.getAccettazioneConOpDaCompletare(), null, persistence, connectionBdu, utente, false,req, connection);
			
		}
		
		req.setAttribute( "animale", animale );
		req.setAttribute( "accettazioni", accettazioni );
		req.setAttribute( "specie", SpecieAnimali.getInstance() );
		
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, persistence, utente, status, connectionBdu,req));
		
		
		gotoPage( "/jsp/vam/accettazione/list.jsp" );
	} 
	
	
	private LookupTipiRichiedente popolaTipiRichiedenti(int richiedenti_tipo,Connection connection){
		LookupTipiRichiedente ltr = new LookupTipiRichiedente();
		try {
			Statement st1 = connection.createStatement();;
			ResultSet rs1 = st1.executeQuery("select * from lookup_tipi_richiedente where id="+richiedenti_tipo);
			while (rs1.next()){
				ltr.setId(rs1.getInt("id"));
				ltr.setDescription(rs1.getString("description"));
				ltr.setEnabled(rs1.getBoolean("enabled"));
				ltr.setForzaPubblica(rs1.getBoolean("forza_pubblica"));
				ltr.setLevel(rs1.getInt("level"));
			}
			rs1.close();
			st1.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return ltr;
	}
	
	private BUtenteAll getEnteredByinfo(int id){
		BUtenteAll b = null;
		try {
			b = UtenteDAO.getUtenteAll(id, connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
