/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.esamiIstopatologici;

import it.us.web.action.GenericAction;
import it.us.web.action.documentale.ListaAllegati;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class GestioneImmagini extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("esameNecroscopico");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		if (interoFromRequest("id")!=-1){
			EsameIstopatologico esame = (EsameIstopatologico) persistence.find (EsameIstopatologico.class, interoFromRequest("id") );	
			if(esame.getTipoDiagnosi().getId()!=3)
				esame.setDiagnosiNonTumorale("");
			req.setAttribute( "esame", esame);
			req.setAttribute( "idIstopatologico", String.valueOf(esame.getId()) );
			
			if (esame.getCartellaClinica()!=null)
			req.setAttribute( "idAccettazione", String.valueOf(esame.getCartellaClinica().getAccettazione().getId()) );


		}
		//gotoPage( "uploadPopup", "/jsp/vam/cc/esamiIstopatologici/gestioneImmagini.jsp" );
		goToAction(new ListaAllegati());

	}
}


