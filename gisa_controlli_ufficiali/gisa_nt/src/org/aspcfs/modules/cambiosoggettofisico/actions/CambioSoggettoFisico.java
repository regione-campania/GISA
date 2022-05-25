/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.cambiosoggettofisico.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class CambioSoggettoFisico extends CFSModule{

	Logger logger = Logger.getLogger(CambioSoggettoFisico.class);


	public String executeCommandAdd(ActionContext context)
	{
		
		if (!(hasPermission(context, "pratiche_suap-view"))) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
		
			int id = -1;
			
			String idString = context.getRequest().getParameter("idStabilimentoOpu");
			if (idString==null)
				idString = (String) context.getRequest().getAttribute("idStabilimentoOpu");
			id = Integer.parseInt(idString);
			
			String dataRichiesta =  context.getRequest().getParameter("dataRichiesta");
			context.getRequest().setAttribute("dataRichiesta", dataRichiesta);

			Stabilimento stab = new Stabilimento(db, id);
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			context.getRequest().setAttribute("Operatore", stab.getOperatore());
		
			LookupList listaToponimi = new LookupList();
			listaToponimi.setTable("lookup_toponimi");
			listaToponimi.buildList(db);
			listaToponimi.setRequired(true);
			context.getRequest().setAttribute("ToponimiList", listaToponimi);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "");
			context.getRequest().setAttribute("ProvinceList", provinceList);
			
			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			context.getRequest().setAttribute("NazioniList", NazioniList);

			
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}

		return"addCambioOK";

	}
	
	public String executeCommandInsert(ActionContext context)
	{
		if (!(hasPermission(context, "pratiche_suap-view"))) {
			return ("PermissionError");
		}
		
		Connection db = null ;
		
		int altIdStabilimento = -1;
		String messaggioUscita = "";
		
		int idStabilimento =  Integer.parseInt(context.getRequest().getParameter("idStabilimento"));
		String dataRichiesta = context.getRequest().getParameter("dataRichiesta");

		String nome = context.getRequest().getParameter("nome");
		String cognome = context.getRequest().getParameter("cognome");
		String dataNascita = context.getRequest().getParameter("dataNascita");
		String comuneNascita = context.getRequest().getParameter("comuneNascitainput");
		String sesso = context.getRequest().getParameter("sesso");

		String codFiscale = context.getRequest().getParameter("codFiscaleSoggetto");
		int idNazioneResidenza = Integer.parseInt(context.getRequest().getParameter("nazioneResidenza"));
		int idComuneResidenza = Integer.parseInt(context.getRequest().getParameter("addressLegaleCityId"));
		String provinciaResidenza = context.getRequest().getParameter("addressLegaleCountrySigla");
		int idProvinciaResidenza = -1;
		int idToponimoResidenza = Integer.parseInt(context.getRequest().getParameter("toponimoResidenzaId"));
		String capResidenza = context.getRequest().getParameter("capResidenza");
		String viaResidenza = context.getRequest().getParameter("addressLegaleLine1input");
		String civicoResidenza = context.getRequest().getParameter("civicoResidenza");
		String pec = context.getRequest().getParameter("domicilioDigitalePecSF");

		Stabilimento oldStab = null;
		
		try
		{
			db = this.getConnection(context);
			
			oldStab = new Stabilimento(db, idStabilimento);
			
			PreparedStatement pstProvincia = db.prepareStatement("select code from lookup_province where cod_provincia ilike ?");
			pstProvincia.setString(1, provinciaResidenza);
			ResultSet rsProvincia = pstProvincia.executeQuery();
			while (rsProvincia.next())
				idProvinciaResidenza = rsProvincia.getInt("code");
			
			PreparedStatement pst = db.prepareStatement("SELECT * from public_functions.suap_insert_richiesta_variazione_titolarita(?, ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?)");
			
			int i = 0;
			
			pst.setInt(++i, getUserId(context));
			pst.setString(++i, dataRichiesta);
			pst.setInt(++i, idStabilimento);
			pst.setInt(++i, idToponimoResidenza);
			pst.setString(++i, civicoResidenza);
			pst.setString(++i, viaResidenza);
			pst.setString(++i, capResidenza);
			pst.setInt(++i, idComuneResidenza);
			pst.setInt(++i, idProvinciaResidenza);
			pst.setString(++i, "ITALIA");
			pst.setDouble(++i, 0.0);
			pst.setDouble(++i, 0.0);
			pst.setString(++i, cognome);
			pst.setString(++i, nome);
			pst.setString(++i, dataNascita);
			pst.setString(++i, comuneNascita);
			pst.setString(++i, codFiscale);
			pst.setString(++i, sesso);

			pst.setInt(++i, oldStab.getOperatore().getSedeLegale().getToponimo()!=null ? Integer.parseInt(oldStab.getOperatore().getSedeLegale().getToponimo()) : 100);
			pst.setString(++i, oldStab.getOperatore().getSedeLegale().getCivico());
			pst.setString(++i, oldStab.getOperatore().getSedeLegale().getVia());
			pst.setString(++i, oldStab.getOperatore().getSedeLegale().getCap());
			pst.setInt(++i, oldStab.getOperatore().getSedeLegale().getComune());
			pst.setInt(++i, oldStab.getOperatore().getSedeLegale().getIdProvincia());
			pst.setString(++i, oldStab.getOperatore().getSedeLegale().getNazione());
			pst.setDouble(++i, oldStab.getOperatore().getSedeLegale().getLatitudine());
			pst.setDouble(++i, oldStab.getOperatore().getSedeLegale().getLongitudine());
			pst.setInt(++i, oldStab.getOperatore().getTipo_impresa());
			pst.setInt(++i, oldStab.getOperatore().getTipo_societa());
			pst.setString(++i, oldStab.getOperatore().getRagioneSociale());
			pst.setString(++i, oldStab.getOperatore().getCodFiscale());
			pst.setString(++i, oldStab.getOperatore().getPartitaIva());
			pst.setString(++i, oldStab.getOperatore().getDomicilioDigitale());
			
			System.out.println("CAMBIO SOGGETTO FISICO QUERY: "+pst.toString());
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				altIdStabilimento = rs.getInt(1);
				if (altIdStabilimento>0)
					messaggioUscita = "Pratica inserita correttamente. Visualizzarla? Cliccando ANNULLA si tornera' al dettaglio dello stabilimento.";
				else
					messaggioUscita = "Si e' verificato un errore nel salvataggio della pratica.";
			}
			else 					
				messaggioUscita = "Si e' verificato un errore nel salvataggio della pratica.";

			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("idStabilimento", String.valueOf(idStabilimento));
		context.getRequest().setAttribute("altIdStabilimento", String.valueOf(altIdStabilimento));
		context.getRequest().setAttribute("messaggioUscita", messaggioUscita);
		return "InsertOK";
	}


	
	
}
