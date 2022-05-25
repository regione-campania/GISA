/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.LeishList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoSterilizzazione;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class SterilizzazioneLP  extends CFSModule {
	
	
	public String executeCommandDefault(ActionContext context) {
		
		if (hasPermission(context, "sterilizzazioniLP-add")) {
			
			return executeCommandAdd(context);
	}else{
		return executeCommandAdd(context);
	}
		
	}
	
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "sterilizzazioniLP-add")) {
			
				return ("PermissionError");
		}


		Connection db = null;
		try {
			db = this.getConnection(context);

			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "Add");
		
	}
	
	
	
	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!hasPermission(context, "sterilizzazioniLP-add")) 
				return ("PermissionError");


		Connection db = null;
		try {
			
			db = this.getConnection(context);
			db.setAutoCommit(false);
			
			EventoSterilizzazione ster = new EventoSterilizzazione();
			
			ster.setEnteredby(this.getUserId(context));
			ster.setModifiedby(this.getUserId(context));
			
			Animale thisAnimale = new Animale(db, context.getParameter("microchip"));
			ster.setMicrochip(context.getParameter("microchip"));
			ster.setFlagContributoRegionale(true);
			ster.setIdProgettoSterilizzazioneRichiesto(Integer.parseInt(context.getParameter("progetto")));
			ster.setIdAnimale(thisAnimale.getIdAnimale());
			ster.setIdAslRiferimento(thisAnimale.getIdAslRiferimento()); //ASL INSERIMENTO EVENTO UGUALE ASL ANIMALE ???
			ster.setIdStatoOriginale(thisAnimale.getStato());
			ster.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
			ster.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
			ster.setSpecieAnimaleId(thisAnimale.getIdSpecie());
			ster.setDataSterilizzazione(context.getParameter("dataSterilizzazione"));
			ster.setIdTipologiaEvento(EventoSterilizzazione.idTipologiaDB);
			ster.setIdSoggettoSterilizzante(context.getParameter("idSoggettoSterilizzante"));
			ster.setIdTipologiaSoggettoSterilizzante(context.getParameter("idTipologiaSoggettoSterilizzante"));
			ster.setNote(context.getParameter("note"));
			thisAnimale.setDataSterilizzazione(ster.getDataSterilizzazione());
			thisAnimale.update(db);
			ster.salvaRegistrazione(this.getUserId(context), this.getUserRole(context), this.getUserAsl(context), thisAnimale, db);
			
			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				String esitoInvioSinaaf[] = new Sinaaf().inviaInSinaaf(db, getUserId(context),ster.getIdEvento()+"", "evento");
				context.getRequest().setAttribute("messaggio", "Registrazione di sterilizzazione" + esitoInvioSinaaf[0]);
				context.getRequest().setAttribute("errore", "Registrazione di sterilizzazione" + esitoInvioSinaaf[1]);
			}
			
			Pratica.aggiornaMaschiFemmina(db, Integer.parseInt(context.getParameter("progetto")), thisAnimale.getSesso(), 1);
			
			context.getRequest().setAttribute("EventoSterilizzazione", ster);
			context.getRequest().setAttribute("animale", thisAnimale);
			context.getRequest().setAttribute("avvisoCertificati", "Attenzione, non potrai più stampare questo certificato dopo aver abbandonato la pagina");
			


		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			db.setAutoCommit(true);
			db.close();
			this.freeConnection(context, db);
			
		}
		

		return getReturn(context, "StampaCertificatiSterLP");
		
	}
}
