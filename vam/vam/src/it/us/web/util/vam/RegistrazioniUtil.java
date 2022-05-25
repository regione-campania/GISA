/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.EsitoLeishmaniosi;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.Indirizzo;
import it.us.web.bean.remoteBean.PrelievoCampioniLeishmania;
import it.us.web.bean.remoteBean.Proprietario;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.remoteBean.RegistrazioniCanina;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.remoteBean.RegistrazioniInterface;
import it.us.web.bean.remoteBean.RegistrazioniSinantropiResponse;
import it.us.web.bean.remoteBean.Telefono;
import it.us.web.bean.sinantropi.Detenzioni;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.test.Utente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneNoH;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AnimaleAnagrafica;
import it.us.web.bean.vam.AnimaleAnagraficaNoH;
import it.us.web.bean.vam.AnimaleNoH;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.AttivitaBdrNoH;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.Leishmaniosi;
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.constants.IdTipiTrasferimentoAccettazione;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.dao.lookup.LookupAttivitaEsterneDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.vam.AttivitaBdrDAO;
import it.us.web.dao.vam.CartellaClinicaDAO;
import it.us.web.exceptions.ValidationBeanException;
import it.us.web.util.DateUtils;
import it.us.web.util.properties.Application;
import it.us.web.util.sinantropi.SinantropoUtil;

public class RegistrazioniUtil implements Specie
{

	@SuppressWarnings("unchecked")
	public static int getIdTipoBdr(Animale animale, Accettazione accettazione,LookupOperazioniAccettazione operazione, Connection connectionVam, BUtente utente,Connection connection,HttpServletRequest req) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		//VERONICA: Aggiunta condizione "|| operazione.getIdBdr() == 0" altrimenti il link veniva costruito con tipologia BDR 0
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr() == 0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl())
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(accettazione.getAdozioneVersoAssocCanili()!=null && accettazione.getAdozioneVersoAssocCanili())
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(accettazione!=null && accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}
	
	
	public static int getIdTipoBdr(Animale animale, Accettazione accettazione,LookupOperazioniAccettazione operazione, Connection connectionVam, BUtente utente,Connection connection,HttpServletRequest req, Boolean adozioneFuoriAsl, Boolean adozioneVersoAssocCanili) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		//VERONICA: Aggiunta condizione "|| operazione.getIdBdr() == 0" altrimenti il link veniva costruito con tipologia BDR 0
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr() == 0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(adozioneFuoriAsl!=null && adozioneFuoriAsl)
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(adozioneVersoAssocCanili!=null && adozioneVersoAssocCanili)
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(accettazione!=null && accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}
	
	public static int getIdTipoBdr(AnimaleNoH animale, Accettazione accettazione,LookupOperazioniAccettazione operazione, Connection connectionVam, BUtente utente,Connection connection,HttpServletRequest req) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		//VERONICA: Aggiunta condizione "|| operazione.getIdBdr() == 0" altrimenti il link veniva costruito con tipologia BDR 0
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr() == 0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl())
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(accettazione.getAdozioneVersoAssocCanili()!=null && accettazione.getAdozioneVersoAssocCanili())
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null &&( operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(accettazione!=null && accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}
	
	public static int getIdTipoBdr(AnimaleNoH animale, AccettazioneNoH accettazione,LookupOperazioniAccettazione operazione, Connection connectionVam, BUtente utente,Connection connection,HttpServletRequest req) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		//VERONICA: Aggiunta condizione "|| operazione.getIdBdr() == 0" altrimenti il link veniva costruito con tipologia BDR 0
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr() == 0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl())
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(accettazione.getAdozioneVersoAssocCanili()!=null && accettazione.getAdozioneVersoAssocCanili())
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(accettazione!=null && accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}
	
	public static int getIdTipoBdr(AnimaleNoH animale, AccettazioneNoH accettazione,LookupOperazioniAccettazione operazione, Connection connection, Connection connectionBdu,HttpServletRequest req) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		//VERONICA: Aggiunta condizione "|| operazione.getIdBdr() == 0" altrimenti il link veniva costruito con tipologia BDR 0
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr() == 0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl())
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(accettazione.getAdozioneVersoAssocCanili()!=null && accettazione.getAdozioneVersoAssocCanili())
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(accettazione!=null && accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connection, new ServicesStatus(), connectionBdu, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connection, new ServicesStatus(), connectionBdu, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(accettazione.getTipoTrasferimento().getId()==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}
	
	
	public static int getIdTipoBdrPreAcc(AnimaleNoH animale, Integer idTipoTrasf, boolean intraFuoriAsl, boolean versoAssocCanili, LookupOperazioniAccettazione operazione, Connection connectionVam, Connection connection,HttpServletRequest req) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(intraFuoriAsl)
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(versoAssocCanili)
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(idTipoTrasf==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				//AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, persistence, utente, new ServicesStatus(), connection, req);
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(idTipoTrasf==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagraficaNoH anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(idTipoTrasf==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(idTipoTrasf==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}	
	
	public static int getIdTipoBdrPreAcc(Animale animale, Integer idTipoTrasf, boolean intraFuoriAsl, boolean versoAssocCanili,LookupOperazioniAccettazione operazione, Connection connectionVam, Connection connection,HttpServletRequest req) throws Exception
	{
		int idTipoBdr = 0;
		
		if(operazione!=null && operazione.getIdBdr()!=null && operazione.getIdBdr()>0)
			idTipoBdr = operazione.getIdBdr();
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(intraFuoriAsl)
				idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(versoAssocCanili)
				idTipoBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(animale.getLookupSpecie().getId()==Specie.CANE)
				idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
				idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(operazione!=null && (operazione.getIdBdr()==null || operazione.getIdBdr()==0) && operazione.getId()==IdOperazioniBdr.trasferimento)
		{
			if(idTipoTrasf==IdTipiTrasferimentoAccettazione.proprietaIntraAsl)
			{
				//AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, persistence, utente, new ServicesStatus(), connection, req);
				AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.trasferimento;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
				{
					if(animale.getLookupSpecie().getId()==Specie.CANE)
						idTipoBdr = IdOperazioniInBdr.adozioneDaCanile;
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
						idTipoBdr = IdOperazioniInBdr.adozioneDaColonia;
				}
			}
			else if(idTipoTrasf==IdTipiTrasferimentoAccettazione.proprietaExtraAsl)
			{
				AnimaleAnagrafica anagrafica = AnimaliUtil.getAnagrafica(animale, connectionVam, new ServicesStatus(), connection, req);
				if(anagrafica.getStatoAttuale().indexOf("Privato")>=0 || anagrafica.getStatoAttuale().indexOf("commerciale")>=0)
					idTipoBdr = IdOperazioniInBdr.cessione;
				else if(anagrafica.getStatoAttuale().indexOf("Randagio")>=0)
					idTipoBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			}
			else if(idTipoTrasf==IdTipiTrasferimentoAccettazione.proprietaFuoriRegione)
				idTipoBdr = IdOperazioniInBdr.trasferimentoFuoriRegione;
			else if(idTipoTrasf==IdTipiTrasferimentoAccettazione.residenza)
				idTipoBdr = IdOperazioniInBdr.trasferimentoResidenzaProprietario;
				
		}
		return idTipoBdr;
	}	
	
	public static Integer getIdRegBdr(Accettazione accettazione, LookupOperazioniAccettazione operazione) throws Exception
	{
		Iterator<AttivitaBdr> attivita = accettazione.getAttivitaBdrs().iterator();
		while(attivita.hasNext())
		{
			AttivitaBdr att = attivita.next();
			if(att!=null && att.getOperazioneBdr()!=null && operazione!=null && att.getOperazioneBdr().getId()==operazione.getId())
				return att.getIdRegistrazioneBdr();
		}
		return null;
	}
	
	public static int getIdRegBdr(CartellaClinica cc, LookupOperazioniAccettazione operazione) throws Exception
	{
		Iterator<AttivitaBdr> attivita = cc.getAttivitaBdrs().iterator();
		while(attivita.hasNext())
		{
			AttivitaBdr att = attivita.next();
			if( att!=null && att.getOperazioneBdr()!=null && operazione!=null && att.getOperazioneBdr().getId()==operazione.getId())
			{
				if(att.getIdRegistrazioneBdr()==null)
					return 0;
				else
					return att.getIdRegistrazioneBdr();
			}
		}
		return 0;
	}
	
	public static Integer getIdRegBdr(CartellaClinicaNoH cc, LookupOperazioniAccettazione operazione) throws Exception
	{
		Iterator<AttivitaBdrNoH> attivita = cc.getAttivitaBdrs().iterator();
		while(attivita.hasNext())
		{
			AttivitaBdrNoH att = attivita.next();
			if(att.getOperazioneBdr().getId()==operazione.getId())
				return att.getIdRegistrazioneBdr();
		}
		return 0;
	}
	
	
	//Per il momento sincronizza solo le reg univoche(sterilizzazione, prelievo dna, decesso, ...)
	@SuppressWarnings("unchecked")
	public static void sincronizzaDaBdu(Accettazione acc, CartellaClinica cc, Persistence persistence, Connection connectionVam, Connection connection, BUtente utente, boolean ster,HttpServletRequest req) throws Exception
	{
		Integer regInserita       = null;
		Integer destAnimale       	  = 0;
		Integer idTipoAttivitaBdr = null;
		Set<LookupOperazioniAccettazione> operazioniBdr = new HashSet<LookupOperazioniAccettazione>();
		Set<AttivitaBdr> atts = null;
		AttivitaBdr att2 = null;
		boolean intraFuoriAsl = false;
		boolean versoAssocCanili = false;
		
		Animale animale = null;
		if(acc!=null)
		{
			animale = acc.getAnimale();
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.prelievoDna));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.prelievoLeishmania));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.rinnovoPassaporto));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.iscrizione));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.passaporto));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ricattura));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritrovamento));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritrovamentoSmarrNonDenunciato));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.smarrimento));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.furto));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.sterilizzazione));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasferimento));
			if(acc.getAdozioneFuoriAsl()!=null && acc.getAdozioneFuoriAsl())
				intraFuoriAsl = true;
			if(acc.getAdozioneVersoAssocCanili()!=null && acc.getAdozioneVersoAssocCanili())
				versoAssocCanili = true;
			atts = acc.getAttivitaBdrs();
		}
		else
		{
			animale           = cc.getAccettazione().getAnimale();
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario));
			operazioniBdr.add((LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoCanileOrigine));
			if(cc.getDestinazioneAnimale()!=null)
				destAnimale 	  = cc.getDestinazioneAnimale().getId();
			idTipoAttivitaBdr = getIdTipoAttivitaBdrCompletata(cc);
			if(cc.getAdozioneFuoriAsl()!=null && cc.getAdozioneFuoriAsl())
				intraFuoriAsl = true;
			if(cc.getAdozioneVersoAssocCanili()!=null && cc.getAdozioneVersoAssocCanili())
				versoAssocCanili = true;
			atts = cc.getAttivitaBdrs();
		}
			
		//Se ci sono attività in sospeso di tipo univoco
		Iterator<LookupOperazioniAccettazione> ops = operazioniBdr.iterator();
		while(ops.hasNext())
		{
			att2=null;
			LookupOperazioniAccettazione op = ops.next();
			if(acc!=null && acc.getOperazioniRichiesteBdrNonEseguiteId().contains(op.getId()))
			{
				//Recupero id reg inserita
				if(op.getId()==IdOperazioniBdr.prelievoDna && idTipoAttivitaBdr==null)
					regInserita = getIdRegUnivoca(animale.getIdentificativo(), connection, IdOperazioniInBdr.prelievoDna,req);
				else if(op.getId()==IdOperazioniBdr.prelievoLeishmania && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connectionVam, connection,req), acc.getData(), req);
				else if(op.getId()==IdOperazioniBdr.decesso && idTipoAttivitaBdr==null)
					regInserita = getIdRegUnivoca(animale.getIdentificativo(), connection, IdOperazioniInBdr.decesso,req);
				else if(op.getId()==IdOperazioniBdr.adozione && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connectionVam, connection,req), acc.getData(), req);
				else if(op.getId()==IdOperazioniBdr.rinnovoPassaporto && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connectionVam, connection,req), acc.getData(), req);
				else if(op.getId()==IdOperazioniBdr.iscrizione && idTipoAttivitaBdr==null)
					regInserita = getIdRegUnivoca(animale.getIdentificativo(), connection, IdOperazioniInBdr.iscrizione,req);
				else if(op.getId()==IdOperazioniBdr.passaporto && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.passaporto, acc.getData(),req);
				else if(op.getId()==IdOperazioniBdr.ricattura && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.ricattura, acc.getData(),req);
				else if(op.getId()==IdOperazioniBdr.ritrovamento && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.ritrovamento, acc.getData(),req);
				else if(op.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.ritrovamentoSmarrNonDenunciato, acc.getData(),req);
				else if(op.getId()==IdOperazioniBdr.smarrimento && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.smarrimento, acc.getData(),req);
				else if(op.getId()==IdOperazioniBdr.trasferimento && idTipoAttivitaBdr==null)
					regInserita = getIdReg(animale.getIdentificativo(), connection, getIdTipoBdrPreAcc(animale, (acc.getTipoTrasferimento()==null)?(null):(acc.getTipoTrasferimento().getId()), intraFuoriAsl, versoAssocCanili, op, connectionVam, connection,req), acc.getData(), req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("accettazione", acc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
//			Per la cc non si dovrebbe gestire il decesso perchè avviene chiusura e decesso in un'unica transazione,
//			però allineo lo stesso per evitare qualsiasi altro problema
			else if(destAnimale!=null && destAnimale==2 && idTipoAttivitaBdr==null)
			{
				if(op.getId()==IdOperazioniBdr.decesso)
					regInserita = getIdRegUnivoca(animale.getIdentificativo(), connection, IdOperazioniInBdr.decesso,req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			else if(destAnimale!=null && destAnimale==2 && idTipoAttivitaBdr==null)
			{
				regInserita = getIdReg(animale.getIdentificativo(), connection, getIdTipoBdrPreAcc(animale, -1, intraFuoriAsl, versoAssocCanili, op, connectionVam, connection,req),cc.getDataChiusura(), req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			else if(destAnimale!=null && destAnimale==5 && idTipoAttivitaBdr==null)
			{
				regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.reimmissione,cc.getDataChiusura(),req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			else if(destAnimale!=null && destAnimale==8 && idTipoAttivitaBdr==null)
			{
				regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.ritornoAslOrigine,cc.getDataChiusura(),req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			else if(destAnimale!=null && destAnimale==3 && idTipoAttivitaBdr==null)
			{
				if(animale.getLookupSpecie().getId()<3 && intraFuoriAsl)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.adozioneFuoriAsl,cc.getDataChiusura(),req);
				else if(animale.getLookupSpecie().getId()<3 && versoAssocCanili)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.adozioneVersoAssocCanili,cc.getDataChiusura(),req);
				else if(animale.getLookupSpecie().getId()==Specie.CANE && !intraFuoriAsl && !versoAssocCanili)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.adozioneDaCanile,cc.getDataChiusura(),req);
				else if(animale.getLookupSpecie().getId()==Specie.GATTO && !intraFuoriAsl && !versoAssocCanili)
					regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.adozioneDaColonia,cc.getDataChiusura(),req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			else if(destAnimale!=null && destAnimale==1 && idTipoAttivitaBdr==null)
			{
				regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.ritornoProprietario,cc.getDataChiusura(), req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			else if(destAnimale!=null && destAnimale==4 && idTipoAttivitaBdr==null)
			{
				regInserita = getIdReg(animale.getIdentificativo(), connection, IdOperazioniInBdr.trasfCanile,cc.getDataChiusura(), req);
				
				List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
				atts2 = persistence.createCriteria(AttivitaBdr.class)
						.add(Restrictions.eq("cc", cc))
						.add(Restrictions.isNull("idRegistrazioneBdr"))
						.add(Restrictions.eq("operazioneBdr", op)).list();
				if(!atts2.isEmpty())
					att2 = atts2.get(0);
			}
			
//La sterilizzazione viene sempre recuperata dalla cc nella sezione chirurgia, non serve allineare		
//			else if(ster)
//			{
//				//Recupero id reg inserita
//				if(op.getId()==IdOperazioniBdr.sterilizzazione)
//					regInserita = getIdRegUnivoca(animale.getIdentificativo(), connection, IdOperazioniInBdr.sterilizzazione);
//			}
		
			//Aggiorna in Vam
			if(regInserita !=null)
			{
				AttivitaBdr att = null;
				if(att2==null)
				{
					att = new AttivitaBdr();
					att.setAccettazione(acc);
					att.setCc(cc);
					att.setEntered(new Date());
					att.setEnteredBy(utente.getId());
					att.setIdRegistrazioneBdr(regInserita);
					att.setModifiedBy(utente.getId());
					att.setModified(new Date());
					att.setOperazioneBdr(op);
					persistence.insert(att);
				}
				else
				{
					att2.setIdRegistrazioneBdr(regInserita);
					persistence.update(att2);
				}
				
				if(att2==null)
					atts.add(att);
				else
				{
					atts.remove(att);
					atts.add(att2);
				}
				
				if(acc!=null)
				{
					acc.setAttivitaBdrs(atts);
					persistence.update(acc);
				}
				else
				{
					cc.setAttivitaBdrs(atts);
					persistence.update(cc);
				}	
				persistence.commit();
			}
		}
		
	}	
	
	
	//Per il momento sincronizza solo le reg univoche(sterilizzazione, prelievo dna, decesso, ...)
	@SuppressWarnings("unchecked")
	public static void sincronizzaPrelievoCampioniLeishmaniaDaBdu(CartellaClinica cc, Persistence persistence, Connection connection, BUtente utente, HttpServletRequest req) throws Exception
	{
		ArrayList<Integer> regInserite = new ArrayList<Integer>();
		Integer idTipoAttivitaBdr = null;
		LookupOperazioniAccettazione operazioneLeishmania = new LookupOperazioniAccettazione();
		boolean intraFuoriAsl = false;
		
		Animale animale = null;
		animale           = cc.getAccettazione().getAnimale();
		operazioneLeishmania = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.prelievoLeishmania);
		idTipoAttivitaBdr = getIdTipoAttivitaBdrCompletata(cc);
			
		//Se ci sono attività in sospeso di tipo univoco
		regInserite = getIdRegs(animale.getIdentificativo(), cc.getDataApertura(),cc.getDataChiusura(), req);
		
		
		List<AttivitaBdr> atts = new ArrayList<AttivitaBdr>();
		atts = persistence.createCriteria(AttivitaBdr.class)
				.add(Restrictions.eq("cc", cc))
				.add(Restrictions.isNull("idRegistrazioneBdr"))
				.add(Restrictions.eq("operazioneBdr", operazioneLeishmania)).list();
		
		List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
		atts2 = persistence.createCriteria(AttivitaBdr.class)
				.add(Restrictions.eq("cc", cc))
				.add(Restrictions.isNotNull("idRegistrazioneBdr"))
				.add(Restrictions.eq("operazioneBdr", operazioneLeishmania)).list();
		
		Set<Integer> attsInt = new HashSet<Integer>();
		for(int j=0;j<atts2.size();j++)
		{
			attsInt.add(atts2.get(j).getIdRegistrazioneBdr());
		}
		
	
		//Aggiorna in Vam
		int numUpdate = 0;
		for(int i=0;i<regInserite.size();i++)
		{
			AttivitaBdr att = null;
			if(!attsInt.contains(regInserite.get(i)))
			{
				att = new AttivitaBdr();
				att.setCc(cc);
				att.setEntered(new Date());
				att.setEnteredBy(utente.getId());
				att.setIdRegistrazioneBdr(regInserite.get(i));
				att.setModifiedBy(utente.getId());
				att.setModified(new Date());
				att.setOperazioneBdr(operazioneLeishmania);
				persistence.insert(att);
				
				atts.add(att);
			}
			else
			{
				att = atts2.get(numUpdate);
				att.setIdRegistrazioneBdr(regInserite.get(i));
				persistence.update(att);
				atts.add(att);
				numUpdate++;
			}
		}	
		
		persistence.update(cc);
			
		persistence.commit();
	}
	
	private static Integer getIdTipoAttivitaBdrCompletata(CartellaClinica cc)
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdr> iter = cc.getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdr att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0 && att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}
	
	private static Integer getIdTipoAttivitaBdrCompletata(CartellaClinicaNoH cc)
	{
		Integer idTipoAttivitaBdr = null;
		Iterator<AttivitaBdrNoH> iter = cc.getAttivitaBdrs().iterator();
		while(iter.hasNext())
		{
			AttivitaBdrNoH att = iter.next();
			if(att.getIdRegistrazioneBdr()!=null && att.getIdRegistrazioneBdr()>0 && att.getOperazioneBdr().getId()!=IdOperazioniBdr.sterilizzazione)
				idTipoAttivitaBdr = att.getOperazioneBdr().getId();
		}
		return idTipoAttivitaBdr;
	}
	
	//Ritorna l'id della registrazione(univoca: sterilizz, prelievo dna, decesso,...) in Bdu per quell'animale
	public static Integer getIdRegUnivoca( String identificativo, Connection connection, int idTipoRegBdu, HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException 
	{
		Persistence persistence = null;
		Class.forName("org.postgresql.Driver");
		//Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//Context ctx = new InitialContext();
			//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
			//connection = ds.getConnection();
		}
		else
		{
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
		}
		
		Integer ret = null;
		List<Integer> regs = null;
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			DataCaching data = new DataCaching(connection);
			ArrayList parameters = new ArrayList();
			try {
				
				QueryResult qr = data.execute("select * from public_functions.get_id_registrazione_univoca('"+identificativo+"'," + idTipoRegBdu + ")", parameters);
				result = (ArrayList) qr.getRows();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		else
			regs = persistence.createSQLQuery("select * from public_functions.get_id_registrazione_univoca('"+identificativo+"'," + idTipoRegBdu + ")", Integer.class).list();
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			
			HashMap<String,Integer> reg = (HashMap<String, Integer>) result.iterator().next();
			if (reg.get("get_id_registrazione_univoca")!=null && (Integer) reg.get("get_id_registrazione_univoca")> 0) {
				ret = ((Integer) reg.get("get_id_registrazione_univoca"));
			}
		}
		else
		{
			if( regs.size() > 0 && regs.get(0)!=null )
			{
				ret = regs.get( 0 );
			}
		}
		
		if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
		{
			//connection.close();
		}
		else
		{
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);
		}
		return ret;
	}
	
	
	    //Ritorna l'id della registrazione in Bdu per quell'animale per la data passata
		public static Integer getIdReg( String identificativo, Connection connection, int idTipoRegBdu, Date data, HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException 
		{
			Persistence persistence = null;
			Class.forName("org.postgresql.Driver");
			//Connection connection = null;
			Statement st = null;
			ResultSet rs = null;
			ArrayList result = null;
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				//Context ctx = new InitialContext();
				//javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/canina");
				//connection = ds.getConnection();
			}
			else
			{
				persistence = PersistenceFactory.getPersistenceCanina();
				aggiornaConnessioneApertaSessione(req);
			}
			
			Integer ret = null;
			List<Integer> regs = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataString = sdf.format(data);
			
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				DataCaching dataCache = new DataCaching(connection);
				ArrayList parameters = new ArrayList();
				try {
					
					QueryResult qr = dataCache.execute("select * from public_functions.get_id_registrazione('"+identificativo+"'," + idTipoRegBdu  + ",cast('" + dataString + "' as timestamp))", parameters);
					result = (ArrayList) qr.getRows();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			else
				regs = persistence.createSQLQuery("select * from public_functions.get_id_registrazione('"+identificativo+"'," + idTipoRegBdu + ",cast('" + dataString + "' as timestamp))", Integer.class).list();
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				
				HashMap<String,Integer> reg = (HashMap<String, Integer>) result.iterator().next();
				if (reg.get("get_id_registrazione")!=null && (Integer) reg.get("get_id_registrazione")> 0) {
					ret = ((Integer) reg.get("get_id_registrazione"));
				}
			}
			else
			{
				if( regs.size() > 0 && regs.get(0)!=null )
				{
					ret = regs.get( 0 );
				}
			}
			
			if(Application.get("modalita_comunicazione_vam_bdr").equals("jdbc"))
			{
				//connection.close();
			}
			else
			{
				PersistenceFactory.closePersistence( persistence, false );
				aggiornaConnessioneChiusaSessione(req);
			}
			return ret;
		}
		
		//Ritorna gli id delle registrazioni in Bdu per quell'animale per le date passate
		public static ArrayList<Integer> getIdRegs( String identificativo, Date dataStart, Date dataEnd, HttpServletRequest req) throws ClassNotFoundException, SQLException, NamingException 
		{
			Persistence persistence = null;
			Statement st = null;
			ResultSet rs = null;
			ArrayList result = null;
			
			persistence = PersistenceFactory.getPersistenceCanina();
			aggiornaConnessioneApertaSessione(req);
			
			ArrayList<Integer> ret = new ArrayList<Integer>();
			List<Integer> regs = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataStartString = sdf.format(dataStart);
			String dataEndString = null;
			if(dataEnd!=null)
				dataEndString = sdf.format(dataEnd);
			
			regs = persistence.createSQLQuery("select * from public_functions.get_id_registrazioni_leish('"+identificativo+"',cast('" + dataStartString + "' as timestamp)," + ((dataEnd==null) ? ("null") : ("cast('" + dataEndString + "' as timestamp)")) + ")").list();
		
			for( Integer reg: regs )
			{
				ret.add(reg);
			}
			
			PersistenceFactory.closePersistence( persistence, false );
			aggiornaConnessioneChiusaSessione(req);

			return ret;
		}
		
		
		public static void aggiornaConnessioneApertaSessione(HttpServletRequest req)
		{
			if(req!=null)
			{
				int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (0);
				numConnessioniDb = numConnessioniDb+1;
				req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
				req.getSession().setAttribute("timeConnOpen",     new Date());
			}
		}
		
		public static void aggiornaConnessioneChiusaSessione(HttpServletRequest req)
		{
			if(req!=null)
			{
				int numConnessioniDb = (req.getSession().getAttribute("numConnessioniDb")!=null) ? ((Integer)req.getSession().getAttribute("numConnessioniDb")) : (1);
				numConnessioniDb = numConnessioniDb-1;
				req.getSession().setAttribute("numConnessioniDb", numConnessioniDb);
				if(numConnessioniDb==0)
					req.getSession().setAttribute("timeConnOpen",     null);
			}
		}
		
		
		public static PrelievoCampioniLeishmania getRegPrelCampioniLeish( int id, HttpServletRequest req,Connection connection ) throws ClassNotFoundException, SQLException, NamingException 
		{
			Persistence persistence = null;
			Class.forName("org.postgresql.Driver");
			Statement st = null;
			ResultSet rs = null;
			ArrayList result = null;
			
			PrelievoCampioniLeishmania ret = null;
			List<EsitoLeishmaniosi> esiti = null;
			DataCaching data = new DataCaching(connection);
			
			ArrayList parameters = new ArrayList();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String inputData = "null";

				QueryResult qr = data.execute("select id_evento as id, * from public_functions.getregprelcampionileish(" + id + ")", parameters);
				
				result = (ArrayList) qr.getRows();
				
				Iterator<String> it = result.iterator();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			Iterator iter = result.iterator();
			if(iter.hasNext())
			{
				HashMap<String,Object> esito = (HashMap<String, Object>) iter.next();
				
					PrelievoCampioniLeishmania esitoTemp = new PrelievoCampioniLeishmania();
					esitoTemp.setDataPrelievoLeishmaniosi((Date) esito.get("dataprelievoleishmaniosi"));
					esitoTemp.setId((Integer) esito.get("id"));
					esitoTemp.setVeterinario((String) esito.get("veterinario"));
					
					ret = esitoTemp;
			}
				
			
			return ret;
		
		}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		@SuppressWarnings("unchecked")
		public static void sincronizzaDaBdu(Accettazione acc, CartellaClinica cc, Persistence persistence, Connection connectionBdu, BUtente utente, boolean ster,HttpServletRequest req, Connection connection) throws Exception
		{
			Integer regInserita       = null;
			Integer destAnimale       	  = 0;
			Integer idTipoAttivitaBdr = null;
			Set<LookupOperazioniAccettazione> operazioniBdr = new HashSet<LookupOperazioniAccettazione>();
			Set<AttivitaBdr> atts = null;
			AttivitaBdr att2 = null;
			boolean intraFuoriAsl = false;
			boolean versoAssocCanili = false;
			
			Animale animale = null;
			if(acc!=null)
			{
				animale = acc.getAnimale();
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.prelievoDna,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.prelievoLeishmania,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.rinnovoPassaporto,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.adozione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.decesso,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.iscrizione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.passaporto,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ricattura,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritrovamento,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritrovamentoSmarrNonDenunciato,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.smarrimento,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.furto,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.sterilizzazione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.trasferimento,connection));
				if(acc.getAdozioneFuoriAsl()!=null && acc.getAdozioneFuoriAsl())
					intraFuoriAsl = true;
				if(acc.getAdozioneVersoAssocCanili()!=null && acc.getAdozioneVersoAssocCanili())
					versoAssocCanili = true;
				atts = acc.getAttivitaBdrs();
			}
			else
			{
				animale  = cc.getAccettazione().getAnimale();
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.adozione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.decesso,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.reimmissione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritornoAslOrigine,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.trasfCanile,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritornoProprietario,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritornoCanileOrigine,connection));
				if(cc.getDestinazioneAnimale()!=null)
					destAnimale 	  = cc.getDestinazioneAnimale().getId();
				idTipoAttivitaBdr = getIdTipoAttivitaBdrCompletata(cc);
				if(cc.getAdozioneFuoriAsl()!=null && cc.getAdozioneFuoriAsl())
					intraFuoriAsl = true;
				if(cc.getAdozioneVersoAssocCanili()!=null && cc.getAdozioneVersoAssocCanili())
					versoAssocCanili = true;
				atts = cc.getAttivitaBdrs();
			}
				
			//Se ci sono attività in sospeso di tipo univoco
			Iterator<LookupOperazioniAccettazione> ops = operazioniBdr.iterator();
			while(ops.hasNext())
			{
				att2=null;
				LookupOperazioniAccettazione op = ops.next();
				if(acc!=null && acc.getOperazioniRichiesteBdrNonEseguiteId().contains(op.getId()))
				{
					//Recupero id reg inserita
					if(op.getId()==IdOperazioniBdr.prelievoDna && idTipoAttivitaBdr==null)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.prelievoDna,req);
					else if(op.getId()==IdOperazioniBdr.prelievoLeishmania && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					else if(op.getId()==IdOperazioniBdr.decesso && idTipoAttivitaBdr==null)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.decesso,req);
					else if(op.getId()==IdOperazioniBdr.adozione && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					else if(op.getId()==IdOperazioniBdr.rinnovoPassaporto && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					else if(op.getId()==IdOperazioniBdr.iscrizione && idTipoAttivitaBdr==null)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.iscrizione,req);
					else if(op.getId()==IdOperazioniBdr.passaporto && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.passaporto, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.ricattura && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ricattura, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.ritrovamento && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritrovamento, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritrovamentoSmarrNonDenunciato, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.smarrimento && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.smarrimento, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.trasferimento && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, (acc.getTipoTrasferimento()==null)?(null):(acc.getTipoTrasferimento().getId()), intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("accettazione", acc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
//				Per la cc non si dovrebbe gestire il decesso perchè avviene chiusura e decesso in un'unica transazione,
//				però allineo lo stesso per evitare qualsiasi altro problema
				else if(destAnimale!=null && destAnimale==2 && idTipoAttivitaBdr==null)
				{
					if(op.getId()==IdOperazioniBdr.decesso)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.decesso,req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==2 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, -1, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req),cc.getDataChiusura(), req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==5 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.reimmissione,cc.getDataChiusura(),req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==8 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritornoAslOrigine,cc.getDataChiusura(),req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==3 && idTipoAttivitaBdr==null)
				{
					if(animale.getLookupSpecie().getId()<3 && intraFuoriAsl)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneFuoriAsl,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()<3 && versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneVersoAssocCanili,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()==Specie.CANE && !intraFuoriAsl && !versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneDaCanile,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()==Specie.GATTO && !intraFuoriAsl && !versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneDaColonia,cc.getDataChiusura(),req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==1 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritornoProprietario,cc.getDataChiusura(), req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==4 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.trasfCanile,cc.getDataChiusura(), req);
					
					List<AttivitaBdr> atts2 = new ArrayList<AttivitaBdr>();
					atts2 = persistence.createCriteria(AttivitaBdr.class)
							.add(Restrictions.eq("cc", cc))
							.add(Restrictions.isNull("idRegistrazioneBdr"))
							.add(Restrictions.eq("operazioneBdr", op)).list();
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				
	//La sterilizzazione viene sempre recuperata dalla cc nella sezione chirurgia, non serve allineare		
//				else if(ster)
//				{
//					//Recupero id reg inserita
//					if(op.getId()==IdOperazioniBdr.sterilizzazione)
//						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.sterilizzazione);
//				}
			
				//Aggiorna in Vam
				if(regInserita !=null)
				{
					AttivitaBdr att = null;
					if(att2==null)
					{
						att = new AttivitaBdr();
						att.setAccettazione(acc);
						att.setCc(cc);
						att.setEntered(new Date());
						att.setEnteredBy(utente.getId());
						att.setIdRegistrazioneBdr(regInserita);
						att.setModifiedBy(utente.getId());
						att.setModified(new Date());
						att.setOperazioneBdr(op);
						persistence.insert(att);
					}
					else
					{
						att2.setIdRegistrazioneBdr(regInserita);
						persistence.update(att2);
					}
					
					if(att2==null)
						atts.add(att);
					else
					{
						atts.remove(att);
						atts.add(att2);
					}
					
					if(acc!=null)
					{
						acc.setAttivitaBdrs(atts);
						persistence.update(acc);
					}
					else
					{
						cc.setAttivitaBdrs(atts);
						persistence.update(cc);
					}	
					persistence.commit();
				}
			}
			
		}	
		
		
		
		public static void sincronizzaDaBduNoH(AccettazioneNoH acc, CartellaClinicaNoH cc, CartellaClinica ccH, Persistence persistence, Connection connectionBdu, BUtente utente, boolean ster,HttpServletRequest req, Connection connection) throws Exception
		{
			Integer regInserita       = null;
			Integer destAnimale       	  = 0;
			Integer idTipoAttivitaBdr = null;
			Set<LookupOperazioniAccettazione> operazioniBdr = new HashSet<LookupOperazioniAccettazione>();
			Set<AttivitaBdrNoH> atts = null;
			AttivitaBdrNoH att2 = null;
			boolean intraFuoriAsl = false;
			boolean versoAssocCanili = false;
			
			AnimaleNoH animale = null;
			if(acc!=null)
			{
				animale = acc.getAnimale();
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.prelievoDna,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.prelievoLeishmania,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.rinnovoPassaporto,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.adozione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.decesso,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.iscrizione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.passaporto,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ricattura,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritrovamento,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritrovamentoSmarrNonDenunciato,connection));
				//operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.smarrimento,connection));
				//operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.furto,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.sterilizzazione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.trasferimento,connection));
				if(acc.getAdozioneFuoriAsl()!=null && acc.getAdozioneFuoriAsl())
					intraFuoriAsl = true;
				if(acc.getAdozioneVersoAssocCanili()!=null && acc.getAdozioneVersoAssocCanili())
					versoAssocCanili = true;
				atts = acc.getAttivitaBdrs();
			}
			else
			{
				animale  = cc.getAccettazione().getAnimale();
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.adozione,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.decesso,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.reimmissione,connection));
				//operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritornoAslOrigine,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.trasfCanile,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritornoProprietario,connection));
				operazioniBdr.add(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione( IdOperazioniBdr.ritornoCanileOrigine,connection));
				if(cc.getDestinazioneAnimale()!=null)
					destAnimale 	  = cc.getDestinazioneAnimale().getId();
				idTipoAttivitaBdr = getIdTipoAttivitaBdrCompletata(cc);
				if(cc.getAdozioneFuoriAsl()!=null && cc.getAdozioneFuoriAsl())
					intraFuoriAsl = true;
				if(cc.getAdozioneVersoAssocCanili()!=null && cc.getAdozioneVersoAssocCanili())
					versoAssocCanili = true;
				atts = cc.getAttivitaBdrs();
			}
				
			//Se ci sono attività in sospeso di tipo univoco
			Iterator<LookupOperazioniAccettazione> ops = operazioniBdr.iterator();
			while(ops.hasNext())
			{
				att2=null;
				LookupOperazioniAccettazione op = ops.next();
				if(acc!=null && acc.getOperazioniRichiesteBdrNonEseguiteId().contains(op.getId()))
				{
					//Recupero id reg inserita
					if(op.getId()==IdOperazioniBdr.prelievoDna && idTipoAttivitaBdr==null)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.prelievoDna,req);
					else if(op.getId()==IdOperazioniBdr.prelievoLeishmania && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					else if(op.getId()==IdOperazioniBdr.decesso && idTipoAttivitaBdr==null)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.decesso,req);
					else if(op.getId()==IdOperazioniBdr.adozione && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					else if(op.getId()==IdOperazioniBdr.rinnovoPassaporto && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, null, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					else if(op.getId()==IdOperazioniBdr.iscrizione && idTipoAttivitaBdr==null)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.iscrizione,req);
					else if(op.getId()==IdOperazioniBdr.passaporto && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.passaporto, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.ricattura && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ricattura, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.ritrovamento && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritrovamento, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.ritrovamentoSmarrNonDenunciato && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritrovamentoSmarrNonDenunciato, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.smarrimento && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.smarrimento, acc.getData(),req);
					else if(op.getId()==IdOperazioniBdr.trasferimento && idTipoAttivitaBdr==null)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, (acc.getTipoTrasferimento()==null)?(null):(acc.getTipoTrasferimento().getId()), intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req), acc.getData(), req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(acc.getId(), connection, op);

					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
//				Per la cc non si dovrebbe gestire il decesso perchè avviene chiusura e decesso in un'unica transazione,
//				però allineo lo stesso per evitare qualsiasi altro problema
				else if(destAnimale!=null && destAnimale==2 && idTipoAttivitaBdr==null)
				{
					if(op.getId()==IdOperazioniBdr.decesso)
						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.decesso,req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==2 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, getIdTipoBdrPreAcc(animale, -1, intraFuoriAsl, versoAssocCanili, op, connection,  connectionBdu,req),cc.getDataChiusura(), req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==5 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.reimmissione,cc.getDataChiusura(),req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==8 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritornoAslOrigine,cc.getDataChiusura(),req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==3 && idTipoAttivitaBdr==null)
				{
					if(animale.getLookupSpecie().getId()<3 && intraFuoriAsl)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneFuoriAsl,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()<3 && versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneVersoAssocCanili,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()<3 && versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneVersoAssocCanili,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()==Specie.CANE && !intraFuoriAsl && !versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneDaCanile,cc.getDataChiusura(),req);
					else if(animale.getLookupSpecie().getId()==Specie.GATTO && !intraFuoriAsl && !versoAssocCanili)
						regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.adozioneDaColonia,cc.getDataChiusura(),req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==1 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.ritornoProprietario,cc.getDataChiusura(), req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				else if(destAnimale!=null && destAnimale==4 && idTipoAttivitaBdr==null)
				{
					regInserita = getIdReg(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.trasfCanile,cc.getDataChiusura(), req);
					
					List<AttivitaBdrNoH> atts2 = new ArrayList<AttivitaBdrNoH>();
					atts2 = getAttivitaBdrsNoH(ccH.getId(), connection, op);
					
					if(!atts2.isEmpty())
						att2 = atts2.get(0);
				}
				
	//La sterilizzazione viene sempre recuperata dalla cc nella sezione chirurgia, non serve allineare		
//				else if(ster)
//				{
//					//Recupero id reg inserita
//					if(op.getId()==IdOperazioniBdr.sterilizzazione)
//						regInserita = getIdRegUnivoca(animale.getIdentificativo(), connectionBdu, IdOperazioniInBdr.sterilizzazione);
//				}
			
				//Aggiorna in Vam
				if(regInserita !=null)
				{
					AttivitaBdrNoH att = null;
					if(att2==null)
					{
						att = new AttivitaBdrNoH();
						att.setAccettazione(acc);
						att.setCc(cc);
						att.setEntered(new Date());
						att.setEnteredBy(utente.getId());
						att.setIdRegistrazioneBdr(regInserita);
						att.setModifiedBy(utente.getId());
						att.setModified(new Date());
						att.setOperazioneBdr(op);
						AttivitaBdrDAO.insert(att, connection);
						//persistence.insert(att);
					}
					else
					{
						att2.setIdRegistrazioneBdr(regInserita);
						AttivitaBdrDAO.update(att2.getIdRegistrazioneBdr(), att2.getId(), connection);
						//persistence.update(att2);
					}
					
					if(att2==null)
						atts.add(att);
					else
					{
						atts.remove(att);
						atts.add(att2);
					}
					
					/*if(acc!=null)
					{
						acc.setAttivitaBdrs(atts);
						persistence.update(acc);
					}
					else
					{
						cc.setAttivitaBdrs(atts);
						persistence.update(ccH);
					}*/	
					//persistence.commit();
				}
			}
			
		}
		
		
		
		
		private static ArrayList<AttivitaBdr> getAttivitaBdrs( int idCc, Connection connection, LookupOperazioniAccettazione op2) throws SQLException
		{
			
			ArrayList<AttivitaBdr> atts = new ArrayList<AttivitaBdr>();
			String sql = " select att.id as idAtt,att.tipo_operazione, op.id as idOp, op.inbdr, att.id_registrazione_bdr "
					+ " from attivita_bdr att "
					+ " left join lookup_operazioni_accettazione op on op.id = att.tipo_operazione"
					+ " where att.cc = ? and att.id_registrazione_bdr is null and att.tipo_operazione = ?  ";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, idCc);
			st.setInt(2, op2.getId());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				AttivitaBdr att = new AttivitaBdr();
				att.setIdRegistrazioneBdr( rs.getInt("id_registrazione_bdr"));
				att.setId(rs.getInt("idAtt"));
				att.setOperazioneBdr(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(rs.getInt("tipo_operazione"), connection));
				
				LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
				op.setId( rs.getInt("idOp"));
				op.setInbdr( rs.getBoolean("inbdr"));
				att.setOperazioneBdr(op);

				atts.add(att);
			}
			return atts;
		}
		
		private static ArrayList<AttivitaBdrNoH> getAttivitaBdrsNoH( int idCc, Connection connection, LookupOperazioniAccettazione op2) throws SQLException
		{
			
			ArrayList<AttivitaBdrNoH> atts = new ArrayList<AttivitaBdrNoH>();
			String sql = " select att.id as idAtt,att.tipo_operazione, op.id as idOp, op.inbdr, att.id_registrazione_bdr "
					+ " from attivita_bdr att "
					+ " left join lookup_operazioni_accettazione op on op.id = att.tipo_operazione"
					+ " where att.cc = ? and att.id_registrazione_bdr is null and att.tipo_operazione = ?  ";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setInt(1, idCc);
			st.setInt(2, op2.getId());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				AttivitaBdrNoH att = new AttivitaBdrNoH();
				att.setIdRegistrazioneBdr( rs.getInt("id_registrazione_bdr"));
				att.setId(rs.getInt("idAtt"));
				att.setOperazioneBdr(LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(rs.getInt("tipo_operazione"), connection));
				
				LookupOperazioniAccettazione op = new LookupOperazioniAccettazione();
				op.setId( rs.getInt("idOp"));
				op.setInbdr( rs.getBoolean("inbdr"));
				att.setOperazioneBdr(op);

				atts.add(att);
			}
			return atts;
		}

		
}
