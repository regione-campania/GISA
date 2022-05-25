/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.izsm.esamiIstopatologici;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.EsameIstopatologico;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoInteressamentoLinfonodale;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoSedeLesione;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoDiagnosi;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTipoPrelievo;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoTumoriPrecedenti;
import it.us.web.bean.vam.lookup.LookupEsameIstopatologicoWhoUmana;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.IstopatologicoUtil;
import it.us.web.util.vam.RegistroTumoriRemoteUtil;

import java.sql.Connection;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;

public class Edit extends GenericAction {

	
	public void can() throws AuthorizationException
	{
//		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
//		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("istopatologico");
	}

	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		int idCc = interoFromRequest("idCc");
				
		EsameIstopatologico esame = (EsameIstopatologico) persistence.find(EsameIstopatologico.class, interoFromRequest( "idEsame" ));
				
		BeanUtils.populate( esame, req.getParameterMap() );
		
		session.setAttribute("cc", esame.getCartellaClinica());
		session.setAttribute("idCc", esame.getCartellaClinica().getId());
		cc = esame.getCartellaClinica();
		
		LookupEsameIstopatologicoTipoDiagnosi tipoDiagnosi
			= (LookupEsameIstopatologicoTipoDiagnosi) persistence.find( LookupEsameIstopatologicoTipoDiagnosi.class, interoFromRequest( "idTipoDiagnosi" ) );

		LookupEsameIstopatologicoWhoUmana whoUmana = null;
				
		if( tipoDiagnosi.getId() == 1 )
		{
			whoUmana = (LookupEsameIstopatologicoWhoUmana) persistence.find( LookupEsameIstopatologicoWhoUmana.class, interoFromRequest( "idWhoUmana" ) );
		}
		else if( tipoDiagnosi.getId() == 2 )
		{
			//
		}
			
		//Se il trasferimento non è stato accettato o se il trasferimento non è avvenuto proprio(nessuna necroscopia esterna)
		if(cc!=null && !esame.getOutsideCC())
		{
			if(!cc.getTrasferimentiByCcPostTrasf().isEmpty())
				esame.setCartellaClinica( cc.getTrasferimentiByCcPostTrasf().iterator().next().getCartellaClinica() );	
			else
				esame.setCartellaClinica( cc );	
		}	
		esame.setTipoDiagnosi( tipoDiagnosi );
		esame.setWhoUmana( whoUmana );		
		esame.setModified( new Date() );
		esame.setModifiedBy( utente );
		
		
				
		persistence.update( esame );
		
		
		setMessaggio( "Esame Istopatologico salvato con successo" );
		
		if (esame.getOutsideCC()==true){
			req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(esame.getAnimale(), persistence, utente, new ServicesStatus(), connection,req));
			req.setAttribute("animale", esame.getAnimale());
		}
		else {
			req.setAttribute("anagraficaAnimale", AnimaliUtil.getAnagrafica(esame.getCartellaClinica().getAccettazione().getAnimale(), persistence, utente, new ServicesStatus(), connection,req));
			req.setAttribute("animale", esame.getCartellaClinica().getAccettazione().getAnimale());
		}
		
		persistence.commit();
		req.setAttribute("esame", esame);
		
		
		//Aggiorno registro tumori
		
//		if (esame.getTipoDiagnosi().getId() == 1){
//			RegistroTumoriRemoteUtil.aggiuntiEsitoTumorale(esame.getNumero(), utente);
//		}
		
		String mess = "";
		if(cc!=null)
		{
			if (cc.getPeso()==null || cc.getPeso().equals("")){
				mess = mess+" peso,";
			}
			if (cc.getLookupAlimentazionis().size() == 0){
				mess = mess+" alimentazione,";
			}
			if (cc.getLookupHabitats().size() == 0){
				mess = mess+" habitat,";
			}
		}
		
		if (!mess.equals("")) 
		{
			setMessaggio( "Potresti aggiungere i seguenti dettagli alla richiesta :" + mess+" cliccando sull'apposito bottone nella home dalla cc." );
		}
		
		
		if(stringaFromRequest("toCc")!=null && stringaFromRequest("toCc").equals("on"))
			gotoPage("/jsp/vam/cc/esamiIstopatologici/detail.jsp" );
		else
			gotoPage("/jsp/vam/izsm/esamiIstopatologici/detail.jsp" );
	}
}
