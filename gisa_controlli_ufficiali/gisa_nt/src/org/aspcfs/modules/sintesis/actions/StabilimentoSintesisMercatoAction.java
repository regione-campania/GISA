/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneml.base.SuapMasterListLineaAttivita;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.sintesis.base.SintesisOperatoreMercato;
import org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva;
import org.aspcfs.modules.sintesis.base.SintesisStabilimento;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;


public class StabilimentoSintesisMercatoAction extends CFSModule {


public String executeCommandListaOperatoriMercatoLinea(ActionContext context) throws IndirizzoNotFoundException{
	

	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	
	
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisRelazioneLineaProduttiva rel = new SintesisRelazioneLineaProduttiva(db, Integer.parseInt(idRelazione));
		rel.setPathCompleto(db);
		SintesisStabilimento stab = new SintesisStabilimento(db, rel.getIdStabilimento());

		context.getRequest().setAttribute("Stabilimento", stab);
		context.getRequest().setAttribute("Relazione", rel);
		
		ArrayList<SintesisOperatoreMercato> listaOperatori = new ArrayList<SintesisOperatoreMercato>();
	
		int idLinea = rel.getIdLineaMasterList();
		SuapMasterListLineaAttivita linea = new SuapMasterListLineaAttivita(db, idLinea);
			
		if (!linea.checkFlags(db, "{\"flagMercato\" : true}")){
			context.getRequest().setAttribute("Errore", "La linea selezionata non prevede la gestione degli operatori al mercato.");
			return "ListaMercatoOK";
		}
		
		ArrayList<SintesisOperatoreMercato> listaOperatoriLinea =  new ArrayList<SintesisOperatoreMercato>();
		listaOperatoriLinea = SintesisOperatoreMercato.getElencoOperatori(db, rel.getIdRelazione());
		listaOperatori.addAll(listaOperatoriLinea);
		context.getRequest().setAttribute("listaOperatori", listaOperatori);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return "ListaOperatoriMercatoOK";
}

public String executeCommandStoricoOperatoriMercatoLinea(ActionContext context) throws IndirizzoNotFoundException{ 
	

	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	String numBox = context.getRequest().getParameter("numBox");
	if (numBox==null)
		numBox = (String) context.getRequest().getAttribute("numBox");
	
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisRelazioneLineaProduttiva rel = new SintesisRelazioneLineaProduttiva(db, Integer.parseInt(idRelazione));
		rel.setPathCompleto(db);
		SintesisStabilimento stab = new SintesisStabilimento(db, rel.getIdStabilimento());

		context.getRequest().setAttribute("Stabilimento", stab);
		context.getRequest().setAttribute("Relazione", rel);
		
		ArrayList<SintesisOperatoreMercato> listaOperatoriStorico = new ArrayList<SintesisOperatoreMercato>();
	
		int idLinea = rel.getIdLineaMasterList();
		SuapMasterListLineaAttivita linea = new SuapMasterListLineaAttivita(db, idLinea);
			
		if (!linea.checkFlags(db, "{\"flagMercato\" : true}")){
			context.getRequest().setAttribute("Errore", "La linea selezionata non prevede la gestione degli operatori al mercato.");
			return "ListaMercatoOK";
		}
		
		ArrayList<SintesisOperatoreMercato> listaOperatoriLineaStorico =  new ArrayList<SintesisOperatoreMercato>();
		listaOperatoriLineaStorico = SintesisOperatoreMercato.getStoricoOperatori(db, rel.getIdRelazione(), Integer.parseInt(numBox));
		listaOperatoriStorico.addAll(listaOperatoriLineaStorico);
		context.getRequest().setAttribute("listaOperatoriStorico", listaOperatoriStorico);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return "StoricoOperatoriMercatoOK";
}

public String executeCommandRicercaOperatoreMercatoLinea(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "sintesis-mercati-ittici-add")) {
		return ("PermissionError");
	}
	
	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	String numBox = context.getRequest().getParameter("numBox");

	
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisRelazioneLineaProduttiva rel = new SintesisRelazioneLineaProduttiva(db, Integer.parseInt(idRelazione));
		rel.setPathCompleto(db);
		SintesisStabilimento stab = new SintesisStabilimento(db, rel.getIdStabilimento());
	
		context.getRequest().setAttribute("Stabilimento", stab);
		context.getRequest().setAttribute("Relazione", rel);
		context.getRequest().setAttribute("NumBox", numBox);
		
		LookupList aslList = new LookupList(db, "lookup_site_id");
		aslList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("AslList", aslList);		
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return "RicercaOperatoriMercatoOK";
}

public String executeCommandSearchOperatori(ActionContext context) throws IndirizzoNotFoundException {
	
	if (!hasPermission(context, "sintesis-mercati-ittici-add")) {
		return ("PermissionError");
	}
	
	String ragioneSociale = context.getRequest().getParameter("ragioneSociale");
	String partitaIva = context.getRequest().getParameter("partitaIva");
	String numRegistrazione = context.getRequest().getParameter("numRegistrazione");
	int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));

	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	String numBox = context.getRequest().getParameter("numBox");
	
	ragioneSociale = ragioneSociale.trim();
	partitaIva = partitaIva.trim();
	numRegistrazione = numRegistrazione.trim();
	
	RicercaList ricercaList = new RicercaList();
	
	StringBuffer sql = new StringBuffer();
	sql.append("select distinct on (riferimento_id) riferimento_id, * from ricerche_anagrafiche_old_materializzata o where 1=1 and id_norma=49 and id_stato = 0 and tipo_attivita = 1 and riferimento_id_nome_tab in ('opu_stabilimento') ");
			
	if (ragioneSociale!=null && !ragioneSociale.equals(""))
		sql.append("  and ragione_sociale ilike ? ");
	if (partitaIva!=null && !partitaIva.equals(""))
		sql.append("  and partita_iva  ilike ? ");
	if (numRegistrazione!=null && !numRegistrazione.equals(""))
		sql.append("  and n_reg   ilike ?  ");
	if (idAsl>0)
		sql.append("  and asl_rif = ? ");
		
	sql.append("order by riferimento_id asc");
	PreparedStatement pst;
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		pst = db.prepareStatement(sql.toString());
	
		int i = 0;
		if (ragioneSociale!=null && !ragioneSociale.equals(""))
			pst.setString(++i, "%"+ragioneSociale+"%");
		if (partitaIva!=null && !partitaIva.equals(""))
			pst.setString(++i, "%"+partitaIva+"%");
		if (numRegistrazione!=null && !numRegistrazione.equals(""))
			pst.setString(++i, "%"+numRegistrazione+"%");
		if (idAsl > 0)
			pst.setInt(++i, idAsl);		
		
		ResultSet rs = pst.executeQuery();
	

		while (rs.next()) {
			RicercaOpu thisopu = ricercaList.getObject(rs);
			ricercaList.add(thisopu);
		}

		context.getRequest().setAttribute("ricercaList", ricercaList);
	
		SintesisRelazioneLineaProduttiva rel = new SintesisRelazioneLineaProduttiva(db, Integer.parseInt(idRelazione));
		rel.setPathCompleto(db);
		SintesisStabilimento stab = new SintesisStabilimento(db, rel.getIdStabilimento());
		
		ArrayList<SintesisOperatoreMercato> listaOperatori = new ArrayList<SintesisOperatoreMercato>();
		ArrayList<SintesisOperatoreMercato> listaOperatoriLinea =  new ArrayList<SintesisOperatoreMercato>();
		listaOperatoriLinea = SintesisOperatoreMercato.getElencoOperatori(db, rel.getIdRelazione());
		listaOperatori.addAll(listaOperatoriLinea);
		context.getRequest().setAttribute("listaOperatori", listaOperatori);

		context.getRequest().setAttribute("Stabilimento", stab);
		context.getRequest().setAttribute("Relazione", rel);
		context.getRequest().setAttribute("NumBox", numBox);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return "SearchOperatoriOK";
}

public String executeCommandClonaOperatoreMercatoLinea(ActionContext context) throws IndirizzoNotFoundException {
	
	if (!hasPermission(context, "sintesis-mercati-ittici-add")) {
		return ("PermissionError");
	}
	
	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	String numBox = context.getRequest().getParameter("numBox");
	String riferimentoIdOperatoreDaClonare = context.getRequest().getParameter("riferimentoIdOperatoreDaClonare");
	String riferimentoIdNomeTabOperatoreDaClonare = context.getRequest().getParameter("riferimentoIdNomeTabOperatoreDaClonare");
	String riferimentoIdOperatore = null;
	
	PreparedStatement pst;
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisRelazioneLineaProduttiva rel = new SintesisRelazioneLineaProduttiva(db, Integer.parseInt(idRelazione));
		
		pst = db.prepareStatement("select * from clona_opu_sintesis_gins(?, ?, ?)");
		pst.setInt(1, Integer.parseInt(riferimentoIdOperatoreDaClonare));
		pst.setInt(2, rel.getIdStabilimento() );
		pst.setInt(3, getUserId(context));
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			riferimentoIdOperatore = rs.getString(1);
		
		context.getRequest().setAttribute("riferimentoIdOperatore", riferimentoIdOperatore);
		context.getRequest().setAttribute("riferimentoIdNomeTabOperatore", riferimentoIdNomeTabOperatoreDaClonare);
		context.getRequest().setAttribute("numBox", numBox);
		context.getRequest().setAttribute("idRelazione", idRelazione);

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return executeCommandInserisciOperatoreMercatoLinea(context);
}

public String executeCommandInserisciOperatoreMercatoLinea(ActionContext context) throws IndirizzoNotFoundException {
	
	if (!hasPermission(context, "sintesis-mercati-ittici-add")) {
		return ("PermissionError");
	}
	
	String idRelazione = context.getRequest().getParameter("idRelazione");
	if (idRelazione==null)
		idRelazione = (String) context.getRequest().getAttribute("idRelazione");
	String numBox = context.getRequest().getParameter("numBox");
	if (numBox==null)
		numBox = (String) context.getRequest().getAttribute("numBox");
	String riferimentoIdOperatore = context.getRequest().getParameter("riferimentoIdOperatore");
	if (riferimentoIdOperatore==null)
		riferimentoIdOperatore = (String) context.getRequest().getAttribute("riferimentoIdOperatore");
	String riferimentoIdNomeTabOperatore = context.getRequest().getParameter("riferimentoIdNomeTabOperatore");
	if (riferimentoIdNomeTabOperatore==null)
		riferimentoIdNomeTabOperatore = (String) context.getRequest().getAttribute("riferimentoIdNomeTabOperatore");

	PreparedStatement pst;
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisRelazioneLineaProduttiva rel = new SintesisRelazioneLineaProduttiva(db, Integer.parseInt(idRelazione));
		rel.setPathCompleto(db);
		
		SintesisOperatoreMercato operatore = new SintesisOperatoreMercato();
		operatore.setIdRelazioneSintesisMercato(Integer.parseInt(idRelazione));
		operatore.setIdStabilimentoSintesisMercato(rel.getIdStabilimento());
		operatore.setRiferimentoIdOperatore(Integer.parseInt(riferimentoIdOperatore));
		operatore.setRiferimentoIdNomeTabOperatore(riferimentoIdNomeTabOperatore);
		operatore.setNumBox(Integer.parseInt(numBox));
		operatore.setIdentificativo(db);
		operatore.insert(db, getUserId(context));
		
		context.getRequest().setAttribute("idRelazione", idRelazione);


	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return executeCommandListaOperatoriMercatoLinea(context);
}

public String executeCommandEliminaOperatoreMercatoLinea(ActionContext context) throws IndirizzoNotFoundException {
	
	if (!hasPermission(context, "sintesis-mercati-ittici-delete")) {
		return ("PermissionError");
	}
	
	int idOperatore = Integer.parseInt(context.getRequest().getParameter("idOperatore"));

	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisOperatoreMercato operatore = new SintesisOperatoreMercato(db, idOperatore);
		operatore.delete(db, getUserId(context));
		context.getRequest().setAttribute("idRelazione", String.valueOf(operatore.getIdRelazioneSintesisMercato()));


	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return executeCommandListaOperatoriMercatoLinea(context);
}

public String executeCommandCessazioneOperatoreMercatoLinea(ActionContext context) throws IndirizzoNotFoundException {
	
	if (!hasPermission(context, "sintesis-mercati-ittici-edit")) {
		return ("PermissionError");
	}
	
	int idOperatore = Integer.parseInt(context.getRequest().getParameter("idOperatore"));

	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		SintesisOperatoreMercato operatore = new SintesisOperatoreMercato(db, idOperatore);
		operatore.cessazione(db, getUserId(context));
		context.getRequest().setAttribute("idRelazione", String.valueOf(operatore.getIdRelazioneSintesisMercato()));


	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		this.freeConnection(context, db);
	}
	
	return executeCommandListaOperatoriMercatoLinea(context);
}

}

