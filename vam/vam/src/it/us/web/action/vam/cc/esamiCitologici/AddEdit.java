/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiCitologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.EsameCitologico;
import it.us.web.bean.vam.lookup.LookupEsameCitologicoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameCitologicoTipoPrelievo;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;

import java.sql.Connection;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;

public class AddEdit extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("citologico");
	}

	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
			
		EsameCitologico esame;
		
		if (booleanoFromRequest("modify")) {
			esame = (EsameCitologico) persistence.find( EsameCitologico.class, interoFromRequest( "id" ) );
		}
		else {
			esame = new EsameCitologico();
			esame.setEntered(new Date());
			esame.setEnteredBy(UtenteDAO.getUtenteAll(utente.getId(),connection));
		}
		
		BeanUtils.populate( esame, req.getParameterMap() );
		
		esame.setCartellaClinica( cc );
		
		LookupEsameCitologicoDiagnosi diagnosi = (interoFromRequest("idDiagnosi")>0)?((LookupEsameCitologicoDiagnosi)persistence.find(LookupEsameCitologicoDiagnosi.class, interoFromRequest("idDiagnosi"))):(null);
		LookupEsameCitologicoTipoPrelievo tipoPrelievo = (interoFromRequest("idTipoPrelievo")>0)?((LookupEsameCitologicoTipoPrelievo)persistence.find(LookupEsameCitologicoTipoPrelievo.class, interoFromRequest("idTipoPrelievo"))):(null);
		esame.setDiagnosi(diagnosi);
		esame.setTipoPrelievo(tipoPrelievo);
		esame.setModified( new Date() );
		esame.setModifiedBy( utente );
		
		if( esame.getId() > 0 )
			validaBean( esame , new ToEdit());
		else
			validaBean( esame , new ToAdd());
		
		if( esame.getId() > 0 )
		{
			persistence.update( esame );
			setMessaggio( "Esame Citologico modificato con successo" );
		}
		else
		{
			esame.setEntered( esame.getModified() );
			esame.setEnteredBy( utente );
			
			persistence.insert( esame );
			setMessaggio( "Esame Citologico inserito con successo" );
		}
		
		if(cc.getDataChiusura()!=null)
		{
			beanModificati.add(esame);
		}
		
		cc.setModified( esame.getModified() );
		cc.setModifiedBy( utente );
		persistence.update( cc );
		persistence.commit();

		
		redirectTo( "vam.cc.esamiCitologici.Detail.us?id=" + esame.getId() );
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta/Modifica Citologico";
	}
}
