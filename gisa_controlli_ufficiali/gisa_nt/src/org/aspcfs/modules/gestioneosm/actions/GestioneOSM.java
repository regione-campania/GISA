/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package org.aspcfs.modules.gestioneosm.actions;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneosm.base.SinvsaAnagrafica;
import org.aspcfs.modules.gestioneosm.base.SinvsaProdottoSpecie;
import org.aspcfs.modules.gestioneosm.base.SinvsaSezioneAttivita;

import com.darkhorseventures.framework.actions.ActionContext;


public final class GestioneOSM extends CFSModule {
	Logger logger = Logger.getLogger("MainLogger");

	public String executeCommandDetails(ActionContext context) {
		Connection db = null;

		int riferimentoId = -1;
		String riferimentoIdNomeTab = "";

		try {riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimentoId"));} catch (Exception e) {}
		if (riferimentoId==-1)
			try {riferimentoId = (Integer) context.getRequest().getAttribute("riferimentoId");} catch (Exception e) {}

		riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");

		try {
			db = this.getConnection(context);
			
			SinvsaAnagrafica anagrafica = new SinvsaAnagrafica(db, riferimentoId, riferimentoIdNomeTab);
			context.getRequest().setAttribute("Anagrafica", anagrafica);
			context.getRequest().setAttribute("riferimentoId", String.valueOf(riferimentoId));
			context.getRequest().setAttribute("riferimentoIdNomeTab", riferimentoIdNomeTab);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally{
			freeConnection(context, db);
		}

		return "DetailsOK";

	}

	public String executeCommandInviaOSM(ActionContext context) {

		if (!(hasPermission(context, "osm-invio-view"))) {
			return ("PermissionError");
		}

		Connection db = null;

		int riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimentoId"));
		String riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");

		SinvsaAnagrafica anagrafica = null;

		try {
			db = this.getConnection(context);
			anagrafica = new SinvsaAnagrafica(db, riferimentoId, riferimentoIdNomeTab);
			anagrafica.invio(db, getUserId(context), -1);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("riferimentoId", riferimentoId);
		context.getRequest().setAttribute("riferimentoIdNomeTab", riferimentoIdNomeTab);

		return executeCommandDetails(context);

	}
 
	public String executeCommandPrepareInvioMassivoOSM(ActionContext context) { 
 
		Connection db = null;

		int tot = -1;

		try {
			db = this.getConnection(context);

			PreparedStatement pst = db.prepareStatement("select count(distinct riferimento_id) as tot from get_osm_da_inviare(-1)");
			ResultSet rs = pst.executeQuery();

			if (rs.next())
				tot = rs.getInt("tot");


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("tot", String.valueOf(tot));

		return "PrepareInvioMassivoOK";

	}

	public String executeCommandInvioMassivoOSM(ActionContext context) throws IOException, SQLException {

		Connection db = null; 
		ArrayList<SinvsaAnagrafica> listaAnagrafiche = new ArrayList<SinvsaAnagrafica>(); 
		int tot = Integer.parseInt(context.getRequest().getParameter("tot"));
		int num = Integer.parseInt(context.getRequest().getParameter("num"));
		int idImportMassivo = -1;
		int totOK = 0;
		int totKO = 0;

		PreparedStatement pst = null;
		ResultSet rs = null;

		String messaggio = "";

		try {
			db = this.getConnection(context);

			pst = null;
			rs = null;

			pst = db.prepareStatement("select COALESCE(max(id_import_massivo), 0)+1 as max from sinvsa_osm_anagrafica_esiti");
			rs = pst.executeQuery();

			if (rs.next())
				idImportMassivo = rs.getInt("max");

			if (idImportMassivo<=0)
				idImportMassivo = 1;

			pst = db.prepareStatement("select distinct riferimento_id, riferimento_id_nome_tab from get_osm_da_inviare("+num+")");
			rs = pst.executeQuery();

			while (rs.next()){
				SinvsaAnagrafica anag = new SinvsaAnagrafica(rs.getInt("riferimento_id"), rs.getString("riferimento_id_nome_tab"));
				listaAnagrafiche.add(anag);
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		for (int k=0; k<listaAnagrafiche.size(); k++){
			SinvsaAnagrafica anag = (SinvsaAnagrafica) listaAnagrafiche.get(k);

			try {
				db = this.getConnection(context);

				SinvsaAnagrafica anagrafica = new SinvsaAnagrafica(db, anag.getRiferimentoId(), anag.getRiferimentoIdNomeTab());
				anagrafica.invio(db, getUserId(context), idImportMassivo);

				boolean ok = true;
				if (anagrafica.getIdPersona()<=0 || anagrafica.getIdSedeLegale()<=0 || anagrafica.getIdSedeOperativa()<=0 || anagrafica.getIdImpresa()<=0)
					ok = false;
				if (ok){
					for (int i=0; i<anagrafica.getListaSezioneAttivita().size(); i++){
						SinvsaSezioneAttivita sezioneAttivita = (SinvsaSezioneAttivita)anagrafica.getListaSezioneAttivita().get(i);
						if (sezioneAttivita.getIdSezioneAttivita()<=0)
							ok = false;
						if (ok){
							for (int j=0; j<sezioneAttivita.getListaProdottoSpecie().size(); j++){
								SinvsaProdottoSpecie prodottoSpecie = (SinvsaProdottoSpecie)sezioneAttivita.getListaProdottoSpecie().get(j);
								if (prodottoSpecie.getIdProdottoSpecie()<=0)
									ok = false;
							}
						}
					}
				}



				if (ok){
					totOK++;
					messaggio+= "<div style='background-color: lime'> ["+anagrafica.getRiferimentoIdNomeTab()+"/"+anagrafica.getRiferimentoId()+"] "+anagrafica.getImpresaRagioneSociale()+ " ("+anagrafica.getImpresaPartitaIva()+") : OK"+"</div><br/>";
				}
				else {
					totKO++;
					messaggio+= "<div style='background-color: lightsalmon'> ["+anagrafica.getRiferimentoIdNomeTab()+"/"+anagrafica.getRiferimentoId()+"] "+anagrafica.getImpresaRagioneSociale()+ " ("+anagrafica.getImpresaPartitaIva()+") : KO"+"</div><br/>";
				}
			} catch (Exception e) {
				context.getRequest().setAttribute("Error", e); 
				e.printStackTrace();
			} finally{
				freeConnection(context, db);
			}
		} 



		context.getRequest().setAttribute("num", String.valueOf(num));
		context.getRequest().setAttribute("tot", String.valueOf(tot));
		context.getRequest().setAttribute("totOK", String.valueOf(totOK));
		context.getRequest().setAttribute("totKO", String.valueOf(totKO));
		context.getRequest().setAttribute("messaggio", messaggio);
		context.getRequest().setAttribute("idImportMassivo", String.valueOf(idImportMassivo));

		return "InvioMassivoOK";


	}
	


}
