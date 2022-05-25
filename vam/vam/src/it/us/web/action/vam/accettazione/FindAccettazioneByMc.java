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
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class FindAccettazioneByMc extends GenericAction  implements Specie
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
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		String mc = stringaFromRequest("mc");
		mc = mc.replace("'", "''");
		Animale animale = null;
		
		boolean ricercaAllCliniche = booleanoFromRequest("ricercaAllCliniche");
		
		java.util.List<Animale> list = new ArrayList<Animale>();
		//VERSIONE VECCHIA
		//list = persistence.getNamedQuery( "GetAnimaleByIdentificativo" ).setString( "identificativo", mc ).list();
		
		
		//VERSIONE PEZZOTTA JNDI
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		Connection connection3 = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		Statement st1 = null;
		ResultSet rs1 = null;
					
		st1 = connection3.createStatement();
		rs1 = st1.executeQuery("select identificativo, data_nascita, specie, id, deceduto_non_anagrafe, razza, razza_sinantropo, specie_sinantropo from animale where (identificativo = '" + stringaFromRequest("mc") + "' or tatuaggio = '"+  stringaFromRequest("mc") +"')  and trashed_date is null " );
		while(rs1.next())
		{
			animale = new Animale();
			animale.setId(rs1.getInt("id"));
			animale.setDecedutoNonAnagrafe(rs1.getBoolean("deceduto_non_anagrafe"));
			animale.setIdentificativo(rs1.getString("identificativo"));
			animale.setRazza(rs1.getInt("razza"));
			animale.setDataNascita(rs1.getDate("data_nascita"));

			if (rs1.getString("razza_sinantropo")!=null && rs1.getString("razza_sinantropo").equals("")) 
				animale.setRazzaSinantropo(rs1.getString("razza_sinantropo"));
			if (rs1.getString("specie_sinantropo")!=null && !rs1.getString("specie_sinantropo").equals("")) 
				animale.setSpecieSinantropo(rs1.getString("specie_sinantropo"));
			
			int specie = rs1.getInt("specie");
			
			
			 ArrayList<LookupSpecie> lsList = new ArrayList<LookupSpecie>();
             rs1 = st1.executeQuery("select * from  lookup_specie");
             while (rs1.next())
             {
                     LookupSpecie ls = new LookupSpecie();
                     ls.setId(rs1.getInt("id"));
                     ls.setDescription(rs1.getString("description"));
                     ls.setEnabled(rs1.getBoolean("enabled"));
                     ls.setLevel(rs1.getInt("level"));
                     lsList.add(ls);
             }

             int i = 0;
             while (i<lsList.size())
             {
                     if (lsList.get(i).getId()==specie)
                     {
                             animale.setLookupSpecie(lsList.get(i));
                             break;
                     }
                     else i++;
             }
			
			list.add(animale);
		}
		connection3.close();
		GenericAction.aggiornaConnessioneChiusaSessione(req);
		//FINE PEZZOTTO JNDI
		
		if( list.size() > 0 )
		{
			animale = list.get( 0 );
		}
		else
		{
			setErrore("Nessun animale trovato con il microchip " + mc);
			goToAction( new Home() );
		}
		
		//VECCHIA VERSIONE
		//ArrayList<Accettazione> accettazioni = AnimaliUtil.getAccettazioni(animale.getAccettaziones(),utente.getClinica().getId());
		
		
		//VERSIONE PEZZOTTA JNDI
		ctx3 = new InitialContext();
		ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		connection3 = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		st1 = null;
		rs1 = null;
					
		st1 = connection3.createStatement();
//		rs1 = st1.executeQuery("select acc.id from accettazione acc, utenti u where animale = " + animale.getId() + " and acc.trashed_date is null and acc.entered_by = u.id and u.clinica = " + utente.getClinica().getId() );
		rs1 = st1.executeQuery("select acc.id,acc.data,acc.progressivo, acc.richiedente_tipo, acc.entered_by from animale a join accettazione acc on a.id=acc.animale and acc.trashed_date is null " +
				"join utenti_ u on acc.entered_by=u.id where animale="+animale.getId()+" and ("+ ricercaAllCliniche +" or u.clinica = "+utente.getClinica().getId() + " ) ");
		ArrayList<Accettazione> accettazioni = new ArrayList<Accettazione>(); 
		while(rs1.next())
		{
			Accettazione acc = new Accettazione();
			acc.setId(rs1.getInt("id"));
			acc.setData(rs1.getDate("data"));
			acc.setProgressivo(rs1.getInt("progressivo"));
			acc.setLookupTipiRichiedente(popolaTipiRichiedenti(rs1.getInt("richiedente_tipo")));	
			acc.setEnteredBy(getEnteredByinfo(rs1.getInt("entered_by")));
			accettazioni.add(acc);
		}
		//FINE PEZZOTTO JNDI

		if(accettazioni.isEmpty())
		{
			//VERSIONE JNDI
			connection3.close();
			GenericAction.aggiornaConnessioneChiusaSessione(req);
			setErrore("Nessuna accettazione trovata per l'animale con microchip " + mc);
			goToAction( new Home() );
		}
		else if(accettazioni.size()==1 && accettazioni.get(0).getEnteredBy().getClinica().getId()==utente.getClinica().getId())
		{
			//VERSIONE JNDI
			connection3.close();
			GenericAction.aggiornaConnessioneChiusaSessione(req);
			redirectTo("vam.accettazione.Detail.us?id="+accettazioni.get(0).getId());
		}
		else
		{
		
		//VECCHIA VERSIONE
		/*ArrayList<Trasferimento> trasfIngresso = (ArrayList<Trasferimento>) persistence.createCriteria( Trasferimento.class )
			.add( Restrictions.eq( "clinicaDestinazione", utente.getClinica()) )
			.add( Restrictions.isNull( "dataAccettazioneDestinatario" ) )
			.createAlias( "cartellaClinica", "cc" )
			.createAlias( "cc.accettazione", "acc" )
			.add( Restrictions.eq( "acc.animale", animale ))
			.list();*/

			//VERSIONE JNDI
			//TROVA TRASFERIMENTI PER LA CLINICA DELL'UTENTE
			ArrayList<Trasferimento> trasfIngresso = new ArrayList<Trasferimento>();
			rs1 = st1.executeQuery("select t.id from trasferimento t join cartella_clinica c on t.cartella_clinica=c.id join accettazione a on c.accettazione=a.id " +
					"where t.data_accettazione_destinatario is null AND a.animale="+animale.getId()+" AND t.clinica_destinazione="+utente.getClinica().getId());
			if (rs1.next()){
				Trasferimento t = new Trasferimento();
				t.setId(rs1.getInt("id"));
				trasfIngresso.add(t);
			}
				
			if( trasfIngresso.size() > 0 )
			{
				req.setAttribute( "tr",  trasfIngresso.get( 0 ) );
			}
			
			//Se il cane non è morto senza mc bisogna leggere in bdr l'info sullo stato attuale
			ServicesStatus status = new ServicesStatus();
			if(animale.getDecedutoNonAnagrafe())
			{
				//VECCHIA VERSIONE
				/*Accettazione accettazione = animale.getAccettaziones().iterator().next();
				req.setAttribute("proprietarioCognome",  accettazione.getProprietarioCognome());
				req.setAttribute("proprietarioNome",  accettazione.getProprietarioNome());
				req.setAttribute("proprietarioCodiceFiscale",  accettazione.getProprietarioCodiceFiscale());
				req.setAttribute("proprietarioDocumento",  accettazione.getProprietarioDocumento());
				req.setAttribute("proprietarioIndirizzo",  accettazione.getProprietarioIndirizzo());
				req.setAttribute("proprietarioCap",  accettazione.getProprietarioCap());
				req.setAttribute("proprietarioComune",  accettazione.getProprietarioComune());
				req.setAttribute("proprietarioProvincia",  accettazione.getProprietarioProvincia());
				req.setAttribute("proprietarioTelefono",  accettazione.getProprietarioTelefono());
				req.setAttribute("proprietarioTipo",  accettazione.getProprietarioTipo());
				if(animale.getAccettazioneConOpDaCompletare()!=null)
					RegistrazioniUtil.sincronizzaDaBdu(animale.getAccettazioneConOpDaCompletare(), null, persistence, connectionBdu, utente, false);
				*/
				
				//VERSIONE JNDI
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
							req.setAttribute("razza", (CaninaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
						else if(animale.getRazza()>0 && animale.getLookupSpecie().getId()==Specie.GATTO)
							req.setAttribute("razza", (FelinaRemoteUtil.getRazza(animale.getRazza(), connection,req).getDescription()));
					}
				}
			}
			else
			{
				if (animale.getLookupSpecie().getId() == CANE) 
				{
					ProprietarioCane proprietario = CaninaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connection,req);
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
					
					Colonia colonia = FelinaRemoteUtil.findColonia(animale.getIdentificativo(), utente, connection,req);
					req.setAttribute("colonia", colonia);
					
					ProprietarioGatto proprietario = FelinaRemoteUtil.findProprietario(animale.getIdentificativo(), utente, connection,req);
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
			req.setAttribute( "anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, persistence, utente, status, connectionBdu,req));
			req.setAttribute( "accettazioni", accettazioni );
			req.setAttribute( "specie", SpecieAnimali.getInstance() );
			
			//VERSIONE JNDI
			connection3.close();
			GenericAction.aggiornaConnessioneChiusaSessione(req);
				

			gotoPage( "/jsp/vam/accettazione/list.jsp" );
		}
	} 
	
	private LookupTipiRichiedente popolaTipiRichiedenti(int richiedenti_tipo){
		LookupTipiRichiedente ltr = new LookupTipiRichiedente();
		try {
			Context ctx3 = new InitialContext();
			javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
			Connection connection3 = ds3.getConnection();
			GenericAction.aggiornaConnessioneApertaSessione(req);
			Statement st1 = connection3.createStatement();;
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
			connection3.close();
			GenericAction.aggiornaConnessioneChiusaSessione(req);
		} catch (Exception e){
			e.printStackTrace();
		}
		return ltr;
	}
	
	private BUtente getEnteredByinfo(int id){
		BUtente b = null;
		try {
			b = (BUtente) persistence.find( BUtente.class, id );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

}
