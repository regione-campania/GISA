/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.actions;

import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.suap.base.LineaProduttiva;
import org.aspcfs.modules.suap.base.LineaProduttivaList;
import org.aspcfs.modules.suap.base.Operatore;
import org.aspcfs.modules.suap.base.SedeList;
import org.aspcfs.modules.suap.base.SoggettoFisico;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.utils.OperatoreInsertEsception;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import com.daffodilwoods.daffodildb.server.sql99.dcl.sqlcontrolstatement.throwexception;
import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.length;
import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;

public class OperatoreAction extends CFSModule {



	public String executeCommandVerificaEsistenza(ActionContext context,Connection db)throws SQLException,Exception,OperatoreInsertEsception 
	{
		//Commentato in data 03/04/2019 poichè l'informazione non viene utilizzata
		//Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");

		try 
		{
			//Commentato in data 03/04/2019 poichè l'informazione non viene utilizzata
			/*SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest(),nazioniList);
			soggettoAdded.setEnteredBy(getUserId(context));
			soggettoAdded.setModifiedBy(getUserId(context));
			newOperatore.setRappLegale(soggettoAdded);*/

			//Commentato in data 03/04/2019 poichè l'informazione non viene utilizzata
			//newOperatore.setRagioneSociale(context.getRequest().getParameter("ragioneSociale"));
			//newOperatore.setPartitaIva(context.getRequest().getParameter("partitaIva"));
			//newOperatore.setCodFiscale(context.getRequest().getParameter("codFiscale"));

			//UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");


			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */
			List<Operatore> listaOp = Operatore.checkEsistenzaOperatoreSuap(db,context.getRequest().getParameter("partitaIva"));

			int esitoCompare=-1 ;


			if (listaOp.size()>0)
			{
				context.getRequest().setAttribute("ListaOperatori", listaOp);
				esitoCompare = 2 ; 
			}

			return esitoCompare+"" ;

		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			return "-1";
		} 



	}




	public String executeCommandInsertSuap(ActionContext context,Connection db,MultipartRequest multi)throws SQLException,Exception,OperatoreInsertEsception {


		boolean recordInserted = false;
		boolean isValid = false;
		
		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");

		String  operazione = multi.getParameter("methodRequest");

		switch(operazione)
		{
		case "new" :{
			newOperatore.setIdOperazione(StabilimentoAction.SCIA_NUOVO_STABILIMENTO);
			break;
		}
		case "sospensione" :{
			newOperatore.setIdOperazione(StabilimentoAction.SCIA_SOSPENSIONE);
			break;
		}


		case "ampliamento" :{
			newOperatore.setIdOperazione(StabilimentoAction.SCIA_AMPLIAMENTO);
			break;
		}
		case "cessazione" :{
			newOperatore.setIdOperazione(StabilimentoAction.SCIA_CESSAZIONE);
			break;
		}
		case "cambioTitolarita" :{
			newOperatore.setIdOperazione(StabilimentoAction.SCIA_VARIAZIONE_TITOLARITA);
			break;
		}
		case "modificaStatoLuoghi" :
		{
			newOperatore.setIdOperazione(StabilimentoAction.SCIA_MODIFICA_STATO_LUOGHI);
			break;
		}
		default :{

		}

		}
		

		newOperatore.setPartitaIva(multi.getParameter("partitaIva"));
		newOperatore.setRagioneSociale(multi.getParameter("ragioneSociale"));
		newOperatore.setCodFiscale(multi.getParameter("codFiscale"));
		newOperatore.setNote(multi.getParameter("noteImpresa"));
		newOperatore.setDomicilioDigitale(multi.getParameter("domicilioDigitale"));
		
		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));

		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {



			LookupList nazioniList= new LookupList(db,"lookup_nazioni");

			/*Costruzione dell'operatore da inserire*/
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(db, context.getRequest(),multi,nazioniList);
			soggettoAdded.setEnteredBy(getUserId(context));
			soggettoAdded.setModifiedBy(getUserId(context));
			soggettoAdded.insert(db,context);
			newOperatore.setRappLegale(soggettoAdded);

			
			if(newOperatore.getTipo_impresa()!=1)
			{
			Indirizzo indirizzoAdded = null;
			indirizzoAdded = new Indirizzo(context.getRequest(),multi,nazioniList, db,context);
			indirizzoAdded.setTipologiaSede(1);
			newOperatore.getListaSediOperatore().add(indirizzoAdded);

			}
			else
			{
				newOperatore.getListaSediOperatore().add(soggettoAdded.getIndirizzo());

			}

			if (!"".equals(multi.getParameter("tipo_impresa")))
				newOperatore.setTipo_impresa(Integer.parseInt(multi.getParameter("tipo_impresa")));
			if (!"".equals(multi.getParameter("tipo_societa")))
				newOperatore.setTipo_societa(Integer.parseInt(multi.getParameter("tipo_societa")));
			
			if(newOperatore.getTipo_impresa()==1)
			{
				SedeList ss = new SedeList();
				ss.add(soggettoAdded.getIndirizzo());
				newOperatore.setListaSediOperatore(ss);
			}
				
			isValid = this.validateObject(context, db, newOperatore);

			if (isValid) 
			{
				recordInserted = newOperatore.insert(db,context);
				context.getRequest().setAttribute("opId",newOperatore.getIdOperatore());
				context.getRequest().setAttribute("Operatore", newOperatore);
				return "1" ;// Inserimento OK
			}





		} catch (SQLException errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			return "-1";
		}
		return "-1" ;


	}



}
