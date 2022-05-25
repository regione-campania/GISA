/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.accettazione;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.RowSelect;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.AccettazioneList;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdRichiesteVarie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;

import java.sql.Statement;
import java.sql.ResultSet;


public class ListClinica extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "ACCETTAZIONE", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("accettazione");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		TableFacade tableFacade = new TableFacadeImpl("accettazioni", req);
		int totalRows = 0;
		
		

		Iterator<Entry<String,String[]>> a1 = req.getParameterMap().entrySet().iterator();
		while(a1.hasNext())
		{
			Entry<String,String[]> b = a1.next();
			System.out.println(b.getKey());
			System.out.println(b.getValue()[0]);
			
		}
		
		ArrayList<AccettazioneList> accettazioniClinica = new ArrayList<AccettazioneList>();
		Context ctx3 = new InitialContext();
		javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
		Connection connection3 = ds3.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		Statement st1 = connection3.createStatement();
		
		String filtroMc = "";
		if(stringaFromRequest("accettazioni_f_animale.identificativo")!=null)
			filtroMc = "and a.identificativo ilike '%" + stringaFromRequest("accettazioni_f_animale.identificativo")+ "%'";
		String filtroProgressivo = "";
		if(stringaFromRequest("accettazioni_f_progressivoFormattato")!=null)
			filtroProgressivo = " and 'ACC-' || cl.nome_breve || '-' || to_char(acc.data, 'yyyy') || '-' || to_char(acc.progressivo,'FM00000') ilike '%" + stringaFromRequest("accettazioni_f_progressivoFormattato")+ "%'";
		String filtroTipoRic = "";
		if(stringaFromRequest("accettazioni_f_lookupTipiRichiedente.description")!=null)
			filtroTipoRic = "and tipo.description = '" + stringaFromRequest("accettazioni_f_lookupTipiRichiedente.description") + "'" ;
		String filtroData = "";
		if(stringaFromRequest("accettazioni_f_data")!=null)
		{
			String dataInizio = stringaFromRequest("accettazioni_f_data").split("-----")[0];
			String dataFine  = stringaFromRequest("accettazioni_f_data").split("-----")[1];
			if(!dataInizio.equals("nullo"))
			{
				filtroData = " and acc.data>= cast('" + dataInizio + "' as timestamp) " ;
			}
			if(!dataFine.equals("nullo"))
			{

				filtroData += " and acc.data<= cast('" + dataFine + "' as timestamp) " ;
			
			}
		}
		
		String orderBy = " order by ";
		if(stringaFromRequest("accettazioni_s_0_progressivoFormattato")!=null)
		{
			orderBy += " progressivo_formattato " + stringaFromRequest("accettazioni_s_0_progressivoFormattato") + " , ";
		
		}
		if(stringaFromRequest("accettazioni_s_3_lookupTipiRichiedente.description")!=null)
		{
			orderBy += " acc.richiedente_tipo " + stringaFromRequest("accettazioni_s_3_lookupTipiRichiedente.description") + " , ";
		
		}
		if(stringaFromRequest("accettazioni_s_2_animale.identificativo")!=null)
		{
			orderBy += " a.identificativo " + stringaFromRequest("accettazioni_s_2_animale.identificativo") + " , ";
		
		}
		if(stringaFromRequest("accettazioni_s_1_data")!=null)
		{
			orderBy += " acc.data " + stringaFromRequest("accettazioni_s_1_data") + " ";
		}
		else
		{
			orderBy += " acc.data desc ";
		}

		orderBy += " , id desc ";

		ResultSet rs1 = st1.executeQuery("select count(*) as total_rows " +
				"from animale a join accettazione acc on a.id=acc.animale " + filtroMc +
				" join utenti_ u on acc.entered_by=u.id " +
				" join lookup_tipi_richiedente tipo on acc.richiedente_tipo=tipo.id " + filtroTipoRic +
				" join clinica cl on u.clinica = cl.id " +
				"where u.clinica ="+utente.getClinica().getId()+ filtroProgressivo + filtroData + " and acc.trashed_date is null  " );
		if(rs1.next())
			totalRows = rs1.getInt("total_rows");
		
		RowSelect rowSelect = tableFacade.setTotalRows(totalRows);
		
		
		rs1 = st1.executeQuery("select acc.id,acc.data,acc.progressivo, acc.richiedente_tipo, acc.entered_by, acc.animale, a.identificativo, 'ACC-' || cl.nome_breve || '-' || to_char(acc.data, 'yyyy') || '-' || to_char(acc.progressivo,'FM00000') as progressivo_formattato " +
				"from animale a join accettazione acc on a.id=acc.animale " + filtroMc +
				" join utenti_ u on acc.entered_by=u.id " +
				" join lookup_tipi_richiedente tipo on acc.richiedente_tipo=tipo.id " + filtroTipoRic +
				" join clinica cl on u.clinica = cl.id " +
				"where u.clinica ="+utente.getClinica().getId()+ filtroProgressivo + filtroData + " and acc.trashed_date is null " +
				orderBy +
				"limit " + rowSelect.getMaxRows() + " offset " + rowSelect.getRowStart());
		while (rs1.next())
		{
			AccettazioneList a = new AccettazioneList();
			Animale animale = new Animale();
			animale.setId(rs1.getInt("animale"));
			animale.setIdentificativo(rs1.getString("identificativo"));
					
			a.setAnimale(animale);
			a.setId(rs1.getInt("id"));
			a.setData(rs1.getDate("data"));
			a.setProgressivo(rs1.getInt("progressivo"));
			a.setLookupTipiRichiedente(popolaTipiRichiedenti(rs1.getInt("richiedente_tipo")));	
			a.setEnteredBy(getEnteredByinfo(rs1.getInt("entered_by")));
			accettazioniClinica.add(a);
		}
		
		connection3.close();
		GenericAction.aggiornaConnessioneChiusaSessione(req);
		
/*		ArrayList<AccettazioneList> accettazioniClinica = (ArrayList<AccettazioneList>) persistence.createCriteria( Accettazione.class )
														.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
														.addOrder(Order.desc("data"))
														.addOrder(Order.desc("id"))
														.createCriteria( "enteredBy" )
															.add( Restrictions.eq( "clinica", utente.getClinica() ) )
															.list();
	*/	
		req.setAttribute( "accettazioniClinica", accettazioniClinica );
		req.setAttribute("limit", tableFacade.getLimit());
		
		gotoPage( "/jsp/vam/accettazione/listClinica.jsp" );
	}
	
	private LookupTipiRichiedente popolaTipiRichiedenti(int richiedenti_tipo){
		LookupTipiRichiedente ltr = new LookupTipiRichiedente();
		try {
			Context ctx3 = new InitialContext();
			javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
			Connection connection3 = ds3.getConnection();
			GenericAction.aggiornaConnessioneApertaSessione(req);
			Statement st1 = connection3.createStatement();;
			ResultSet rs1 = st1.executeQuery("select * from lookup_tipi_richiedente where id="+richiedenti_tipo);
			while (rs1.next()){
				ltr.setId(rs1.getInt("id"));
				ltr.setDescription(rs1.getString("description"));
				ltr.setEnabled(rs1.getBoolean("enabled"));
				ltr.setForzaPubblica(rs1.getBoolean("forza_pubblica"));
				ltr.setLevel(rs1.getInt("level"));
			}
			rs1.close();
			st1.close();
			connection3.close();
			GenericAction.aggiornaConnessioneChiusaSessione(req);
		} catch (Exception e){
			e.printStackTrace();
		}
		return ltr;
	}
	
	private BUtenteAll getEnteredByinfo(int id){
		BUtenteAll b = null;
		try {
			b = UtenteDAO.getUtenteAll(id, connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
