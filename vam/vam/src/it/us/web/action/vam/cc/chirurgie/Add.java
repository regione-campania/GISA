/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.chirurgie;


import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.lookup.LookupAritmie;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.IdOperazioniInBdr;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.vam.CaninaRemoteUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;

public class Add extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("chirurgia");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);

		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		
		LookupOperazioniAccettazione operazione = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.sterilizzazione);
		
		AttivitaBdr abdr = new AttivitaBdr();
		abdr.setCc				( cc );
		abdr.setEntered			( cc.getModified() );
		abdr.setEnteredBy		( utente.getId() );
		abdr.setModified		( abdr.getEntered() );
		abdr.setModifiedBy		( utente.getId() );
		abdr.setOperazioneBdr	( operazione );
		
		int idTipoRegBdr = 0;
		if(operazione!=null && operazione.getIdBdr()!=null)
			idTipoRegBdr = operazione.getIdBdr();
		if(operazione!=null && operazione.getIdBdr()==null && operazione.getId()==IdOperazioniBdr.adozione)
		{
			if(cc.getAdozioneFuoriAsl())
				idTipoRegBdr = IdOperazioniInBdr.adozioneFuoriAsl;
			else if(cc.getAdozioneVersoAssocCanili())
				idTipoRegBdr = IdOperazioniInBdr.adozioneVersoAssocCanili;
			else if(cc.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.CANE)
				idTipoRegBdr = IdOperazioniInBdr.adozioneDaCanile;
			else if(cc.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.GATTO)
				idTipoRegBdr = IdOperazioniInBdr.adozioneDaColonia;
		}
		if(cc.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.CANE || cc.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.GATTO)
			abdr.setIdRegistrazioneBdr(CaninaRemoteUtil.getUltimaRegistrazione(cc.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr, connection,req));
		//else
			//abdr.setIdRegistrazioneBdr(SinantropoUtil.getUltimaRegistrazione(cc.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr));
		
		persistence.insert( abdr );

			
		setMessaggio("Inserimento completato");
		redirectTo("vam.cc.chirurgie.List.us");
	}
}
