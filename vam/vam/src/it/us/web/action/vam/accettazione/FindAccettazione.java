/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
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
import it.us.web.bean.vam.lookup.LookupAsl;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.constants.SpecieAnimali;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

public class FindAccettazione extends GenericAction implements Specie
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
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
		String numeroAccettazione = stringaFromRequest( "numeroAccettazione" );
		
		//VECCHIA VERSIONE
		/*String query = "                        select * " +
					   "           from accettazione acc, " +
					   "                       utenti ut, " +
					   "                      clinica cl  " +
					   "   where acc.trashed_date is null and " +
					   "           acc.entered_by = ut.id and " +
					   "               ut.clinica = cl.id and " + 
					   "               cl.id = " + utente.getClinica().getId() + " and " + 
					   " 'ACC-' || cl.nome_breve || '-' || to_char(acc.data, 'yyyy') || '-' || to_char(progressivo,'FM00000') = '"+ numeroAccettazione +"' ";
					   
		List<Accettazione> accettazioni = persistence.createSQLQuery(query, Accettazione.class).list();*/
		
		
		//VERSIONE PEZZOTTA JNDI
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		Connection connection3 = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		Statement st1 = null;
		ResultSet rs1 = null;
					
		Accettazione acc = null;
		List<Accettazione> accettazioni = new ArrayList<Accettazione>();
		st1 = connection3.createStatement();
		rs1 = st1.executeQuery( "select * " +
				   "           from accettazione acc, " +
				   "                       utenti_ ut, " +
				   "                      clinica cl  " +
				   "   where acc.trashed_date is null and " +
				   "           acc.entered_by = ut.id and " +
				   "               ut.clinica = cl.id and " + 
				   "               cl.id = " + utente.getClinica().getId() + " and " + 
				   " 'ACC-' || cl.nome_breve || '-' || to_char(acc.data, 'yyyy') || '-' || to_char(progressivo,'FM00000') = '"+ numeroAccettazione +"' ");
		while(rs1.next())
		{
			acc = new Accettazione();
			acc.setId(rs1.getInt("id"));
			accettazioni.add(acc);
		}
		connection3.close();
		GenericAction.aggiornaConnessioneChiusaSessione(req);
		//FINE PEZZOTTO JNDI
		
		
		if(accettazioni.isEmpty())
		{
			setErrore("Nessuna accettazione trovata per con il numero " + numeroAccettazione);
			goToAction( new Home() );
		}
		else
		{
			redirectTo("vam.accettazione.Detail.us?id="+accettazioni.get(0).getId());
		}
	}

}
