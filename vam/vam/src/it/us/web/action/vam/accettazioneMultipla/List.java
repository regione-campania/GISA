/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazioneMultipla;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.accettazione.Home;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.Parameter;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Colonia;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupSpecie;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
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

public class List extends GenericAction  implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "LIST", "MAIN" );
		can( gui, "w" );
	}

	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		String[] mcs = (String[])(req.getParameter("microchip").split("-----"));
		String mcsString = "";
		
		for(int i=0;i<mcs.length;i++)
		{
			
			mcsString += "'" + mcs[i] + "'";
			if(i<mcs.length-1)
				mcsString += ",";
		}
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		connection = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		Statement st1 = null;
		ResultSet rs1 = null;
					
		Accettazione acc = null;
		ArrayList<Accettazione> accettazioni = new ArrayList<Accettazione>();
		st1 = connection.createStatement();
		rs1 = st1.executeQuery( "select acc.id, an.identificativo " +
"from accettazione acc "+
				           "  left join utenti_ ut on ut.id = acc.entered_by "+
				            " left join animale an on an.id = acc.animale  and an.trashed_date is null "+
				            " where acc.trashed_date is null and an.identificativo in ("+mcsString+") or an.tatuaggio in ("+mcsString+")");
		while(rs1.next())
		{
			acc = AccettazioneDAO.getAccettazione(rs1.getInt("id"), connection);
			accettazioni.add(acc);
			req.setAttribute("accettazione", acc);
		}
		
		req.setAttribute("accettazioni", accettazioni);
		
		gotoPage( "/jsp/vam/accettazioneMultipla/list.jsp" );
		
	} 
	
	private BUtente getEnteredByinfo(int id){
		BUtente b = null;
		try {
			b = (BUtente) persistence.find( BUtente.class, id );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

}
