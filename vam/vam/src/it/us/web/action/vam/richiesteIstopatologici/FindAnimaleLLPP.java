/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.richiesteIstopatologici;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

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
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAutopsiaSalaSettoria;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoInteressamentoLinfonodale;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoSedeLesione;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumore;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumoriPrecedenti;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.AnimaleDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

public class FindAnimaleLLPP extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "RICHIESTA_ISTOPATOLOGICO", "ADD_LLPP", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("istopatologico");
	}

	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		String identificativo = stringaFromRequest( "identificativo" );
		
		ServicesStatus status = new ServicesStatus();
		HashMap<String, Object> fetchAnimale = AnimaliUtil.fetchAnimale( identificativo, persistence, utente, status, connection,req );
		Animale animale = (Animale)fetchAnimale.get("animale");
		
		if( animale == null ) 
		{
			if( status.isAllRight() )
			{
				setErrore("Nessun animale presente con l'identificativo " + identificativo);
			}
			else
			{
				setErrore( "Si è verificato un errore di comunicazione con la BDR di riferimento: " + status.getError() );
			}
			redirectTo("Index.us");				
			
		}
		else 
		{
			
			ArrayList<LookupEsameIstopatologicoInteressamentoLinfonodale> interessamentoLinfonodales
			= (ArrayList<LookupEsameIstopatologicoInteressamentoLinfonodale>) persistence.findAll(LookupEsameIstopatologicoInteressamentoLinfonodale.class);

			ArrayList<LookupEsameIstopatologicoTipoPrelievo> tipoPrelievos
			= (ArrayList<LookupEsameIstopatologicoTipoPrelievo>)persistence.createCriteria( LookupEsameIstopatologicoTipoPrelievo.class )
				.add( Restrictions.eq("deceduto", ((animale.getDataMorte()!=null)?(true):(false))) )
				.list();
	
			ArrayList<LookupEsameIstopatologicoTumore> tumores
				= (ArrayList<LookupEsameIstopatologicoTumore>) persistence.findAll(LookupEsameIstopatologicoTumore.class);
			
			ArrayList<LookupEsameIstopatologicoTumoriPrecedenti> tumoriPrecedentis
				= (ArrayList<LookupEsameIstopatologicoTumoriPrecedenti>) persistence.findAll(LookupEsameIstopatologicoTumoriPrecedenti.class);
			
			ArrayList<LookupEsameIstopatologicoSedeLesione> sediLesioniPadre
				= (ArrayList<LookupEsameIstopatologicoSedeLesione>)persistence.createCriteria( LookupEsameIstopatologicoSedeLesione.class )
					.add( Restrictions.isNull( "padre" ) )
					.addOrder( Order.asc( "level" ) )
					.list();
			
			ArrayList<LookupEsameIstopatologicoTipoDiagnosi> tipoDiagnosis
				= (ArrayList<LookupEsameIstopatologicoTipoDiagnosi>) persistence.findAll(LookupEsameIstopatologicoTipoDiagnosi.class);
			
			ArrayList<LookupEsameIstopatologicoWhoUmana> whoUmanaPadre
				= (ArrayList<LookupEsameIstopatologicoWhoUmana>)persistence.createCriteria( LookupEsameIstopatologicoWhoUmana.class )
					.add( Restrictions.isNull( "padre" ) )
					.addOrder( Order.asc( "level" ) )
					.list();
			
			ArrayList<LookupAlimentazioni> listAlimentazioni = (ArrayList<LookupAlimentazioni>) persistence.createCriteria( LookupAlimentazioni.class )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			ArrayList<LookupHabitat> listHabitat = (ArrayList<LookupHabitat>) persistence.createCriteria( LookupHabitat.class )
				.addOrder( Order.asc( "level" ) )
				.list();
			
			ArrayList<LookupAutopsiaSalaSettoria>		listAutopsiaSalaSettoria    = (ArrayList<LookupAutopsiaSalaSettoria>) persistence.createCriteria( LookupAutopsiaSalaSettoria.class )
					.add( Restrictions.eq( "enabled", true ) )
					.add( Restrictions.eq( "esameRiferimento", "Istopatologico" ) )
					.addOrder( Order.asc( "esterna" ) )
					.addOrder( Order.asc( "level" ) )
					.addOrder( Order.asc( "description" ) ).list();
			
			req.setAttribute( "listAutopsiaSalaSettoria", listAutopsiaSalaSettoria );
			
			req.setAttribute( "interessamentoLinfonodales", interessamentoLinfonodales );
			req.setAttribute( "tipoPrelievos", tipoPrelievos );
			req.setAttribute( "tumores", tumores );
			req.setAttribute( "tumoriPrecedentis", tumoriPrecedentis );
			req.setAttribute( "sediLesioniPadre", sediLesioniPadre );
			req.setAttribute( "tipoDiagnosis", tipoDiagnosis );
			req.setAttribute( "whoUmanaPadre", whoUmanaPadre );
			
			req.setAttribute( "animale", animale );
			
			req.setAttribute("listAlimentazioni", listAlimentazioni);		
			req.setAttribute("listHabitat", listHabitat);	
			
			req.setAttribute( "specie", SpecieAnimali.getInstance() );
			
			int specie = animale.getLookupSpecie().getId();
			Gatto gatto = null;
			Cane cane = null;
			if(!animale.getDecedutoNonAnagrafe())
			{
				if (specie == CANE ) 
				{
					cane = CaninaRemoteUtil.findCane(animale.getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniCaninaResponse res	= AnimaliUtil.fetchDatiDecessoCane(cane);
					req.setAttribute("res", res);
				}
				else if (specie == GATTO) 
				{
					gatto = FelinaRemoteUtil.findGatto(animale.getIdentificativo(), utente, status, connectionBdu,req);
					RegistrazioniFelinaResponse rfr	= AnimaliUtil.fetchDatiDecessoGatto(gatto);
					req.setAttribute("res", rfr);
				}
				else if (specie == SINANTROPO) 
				{
					RegistrazioniSinantropiResponse rsr = SinantropoUtil.getInfoDecesso(persistence, animale);
					req.setAttribute("res", rsr);
					req.setAttribute("fuoriAsl", false);
					req.setAttribute("versoAssocCanili", false);
				}
			}
			
			if(animale.getDecedutoNonAnagrafe())
			{
				Iterator<Accettazione> accettazioni = animale.getAccettaziones().iterator();
				if(accettazioni.hasNext())
				{
					Accettazione accettazione = accettazioni.next();
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
					req.setAttribute("randagio",  accettazione.getRandagio());
					req.setAttribute("nomeColonia",  accettazione.getNomeColonia());
				}
			}
			else
			{
				if (specie == CANE) 
				{
					cane = (Cane)fetchAnimale.get("cane");
					if(cane==null)
						cane = CaninaRemoteUtil.findCane(identificativo, utente, status, connectionBdu,req);
					
					ProprietarioCane proprietario = CaninaRemoteUtil.findProprietario(identificativo, utente, connectionBdu,req);
					
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
				else if (specie == GATTO) 
				{
					gatto = (Gatto)fetchAnimale.get("gatto");
					if(gatto==null)
						gatto = FelinaRemoteUtil.findGatto(identificativo, utente, status, connectionBdu,req);
					
					Colonia colonia = (Colonia)fetchAnimale.get("colonia");
					if(colonia==null)
						colonia = FelinaRemoteUtil.findColonia(identificativo, utente, connectionBdu,req);
					
					req.setAttribute("colonia", colonia);
					
					ProprietarioGatto proprietario = FelinaRemoteUtil.findProprietario(identificativo, utente, connectionBdu,req);
					
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
				else if (specie == SINANTROPO) 
				{
					Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, identificativo);
					Detenzioni detenzioni = SinantropoUtil.getLastActiveDetentore( persistence, animale.getIdentificativo() );
					if (detenzioni !=null && detenzioni.getLookupDetentori()!=null){
						if( detenzioni.getLookupDetentori().getId() == 1 ) //detentore privato
						{
							req.setAttribute("proprietarioCognome",  detenzioni.getDetentorePrivatoCognome());
							req.setAttribute("proprietarioNome",  detenzioni.getDetentorePrivatoNome());
							req.setAttribute("proprietarioCodiceFiscale",  detenzioni.getDetentorePrivatoCodiceFiscale());
							req.setAttribute("proprietarioDocumento", ((detenzioni.getLookupTipologiaDocumento()!=null)? detenzioni.getLookupTipologiaDocumento().getDescription():"") + ": "+ detenzioni.getDetentorePrivatoNumeroDocumento()  );
							req.setAttribute("proprietarioIndirizzo",  detenzioni.getLuogoDetenzione());
							req.setAttribute("proprietarioCap",   detenzioni.getComuneDetenzione().getCap());
							req.setAttribute("proprietarioComune",  detenzioni.getComuneDetenzione().getDescription());
							req.setAttribute("proprietarioProvincia", calcolaProvinciaSinantropo(detenzioni.getComuneDetenzione()));
							req.setAttribute("proprietarioTelefono",  detenzioni.getDetentorePrivatoTelefono());
							req.setAttribute("proprietarioTipo", "Detentore");
						}
						else //detentore non privato
						{
							if( detenzioni.getLookupDetentori().getLookupComuni() != null )
							{
								req.setAttribute("proprietarioCap",detenzioni.getLookupDetentori().getLookupComuni().getCap() );
								req.setAttribute("proprietarioComune", detenzioni.getLookupDetentori().getLookupComuni().getDescription() );
								req.setAttribute("proprietarioProvincia", calcolaProvinciaSinantropo( detenzioni.getLookupDetentori().getLookupComuni() ) );
							}
							req.setAttribute("proprietarioIndirizzo", detenzioni.getLookupDetentori().getIndirizzo() );
							req.setAttribute("proprietarioNome", detenzioni.getLookupDetentori().getDescription() );
							req.setAttribute("proprietarioTelefono", detenzioni.getLookupDetentori().getTelefono() );
							req.setAttribute("proprietarioTipo", "Detentore");
						}
					}
				}
			
		}
			
		boolean liberoProfessionista = booleanoFromRequest("liberoProfessionista");
		req.setAttribute("liberoProfessionista", liberoProfessionista);
			
		req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(animale, cane, gatto, persistence, utente, status, connectionBdu,req));
			
		gotoPage( "/jsp/vam/richiesteIstopatologici/addLLPP.jsp");
		
		
		}
	}
			
	private static String calcolaProvinciaSinantropo( LookupComuni comune )
	{
		String ret = "";
		
		if( comune.getAv() ) { ret = "AV"; }
		else if( comune.getBn() ) { ret = "BN"; }
		else if( comune.getCe() ) { ret = "CE"; }
		else if( comune.getNa() ) { ret = "NA"; }
		else if( comune.getSa() ) { ret = "SA"; }
		
		return ret;
	}
		

}
