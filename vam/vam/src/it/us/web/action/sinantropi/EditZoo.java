/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.sinantropi;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.autopsie.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.sinantropi.lookup.LookupSinantropiEta;
import it.us.web.bean.sinantropi.lookup.LookupSpecieSinantropi;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaOrganoEsiti;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.DiagnosiEffettuate;
import it.us.web.bean.vam.lookup.LookupAutopsiaModalitaConservazione;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditZoo extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "BDR", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("sinantropi_anagrafica");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(EditZoo.class);			
		
		int idSinantropo  = interoFromRequest("idSinantropo");
		
		Sinantropo s = (Sinantropo)persistence.find(Sinantropo.class, idSinantropo);
		BeanUtils.populate(s, req.getParameterMap());	
		s.setModified(new Date());		
		s.setModifiedBy(utente);
		int idTaglia = interoFromRequest( "idTaglia" );
		if(idTaglia>0)
			s.setTaglia((LookupTaglie) persistence.find( LookupTaglie.class, interoFromRequest( "idTaglia" )));
		else
			s.setTaglia( null );
		
		int idEta = interoFromRequest( "idEta" );
		if(idEta>0)
			s.setEta((LookupSinantropiEta) persistence.find( LookupSinantropiEta.class, interoFromRequest( "idEta" )));
		else
			s.setEta( null );
		
		/* Gestione Specie Sinantropi */
		int specie = interoFromRequest("specieSinantropo");
		int idSS = 0;
		
		if (specie == 1)		
			idSS = interoFromRequest("tipologiaSinantropoU");
		else if (specie == 2)
			idSS = interoFromRequest("tipologiaSinantropoM");
		else if (specie == 3)
			idSS = interoFromRequest("tipologiaSinantropoRA");
		
		ArrayList<LookupSpecieSinantropi> lssList = (ArrayList<LookupSpecieSinantropi>) persistence.createCriteria( LookupSpecieSinantropi.class )
		.list();		
		
		LookupSpecieSinantropi lss = null;
		
		Iterator lssIterator = lssList.iterator();
		
		Set<LookupSpecieSinantropi> setSS = new HashSet<LookupSpecieSinantropi>(0);
		while(lssIterator.hasNext()) {			
			lss = (LookupSpecieSinantropi) lssIterator.next();			
			if (lss.getId() == idSS){  
				s.setRazza(lss.getDescription());
				s.setLookupSpecieSinantropi(lss);
			}
		}
		
		//UPDATE SU TAB ANIMALE
		int idAnimale = 0;
		String identificativo =""; 
		Animale a = null;
		Boolean flag = false;
		
		if (s.getMc()!=null && !s.getMc().equals("") && flag==false){
			identificativo=s.getMc();
			flag=true;
		}
		if (s.getNumeroUfficiale()!=null && !s.getNumeroUfficiale().equals("") && flag==false){
			identificativo = s.getNumeroUfficiale();
			flag=true;
		}
		if (s.getCodiceIspra()!=null && !s.getCodiceIspra().equals("") && flag==false){
			identificativo = s.getCodiceIspra();
			flag=true;
		}
		if (s.getNumeroAutomatico()!=null && !s.getNumeroAutomatico().equals("") && flag==false){
			identificativo = s.getNumeroAutomatico();
			flag=true;
		}
		
		if (!identificativo.equals("")){
			Boolean presente=false;
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
			Connection connection = ds.getConnection();
			GenericAction.aggiornaConnessioneApertaSessione(req);
			Statement st1 = connection.createStatement();
			ResultSet rs1 = st1.executeQuery("select id from animale where identificativo='"+identificativo+"'");
			while (rs1.next()){
				idAnimale = (Integer)rs1.getInt("id");
				presente=true;
			}
			rs1.close();
			st1.close();
			connection.close();	
			GenericAction.aggiornaConnessioneChiusaSessione(req);
			
			if (presente==true){
				a = (Animale)persistence.find(Animale.class, idAnimale);
				a.setSesso(s.getSesso());
				a.setEta(s.getEta());
				a.setRazzaSinantropo(s.getLookupSpecieSinantropi().getDescription());
				if (s.getLookupSpecieSinantropi().getUccello()==true)
					a.setSpecieSinantropo("1");
				else if (s.getLookupSpecieSinantropi().getMammifero()==true)
					a.setSpecieSinantropo("2");
				else if (s.getLookupSpecieSinantropi().getRettileAnfibio()==true)
					a.setSpecieSinantropo("3");
			}
		}
		
		try 
		{
			validaBean( s, new ToAdd() );
			
			if (a!=null){
				persistence.update(a);
			}
			
			persistence.update(s);
			persistence.commit();
		}
		catch (RuntimeException e)
		{
			try
			{		
				persistence.rollBack();				
			}
			catch (HibernateException e1)
			{				
				logger.error("Error during Rollback transaction" + e1.getMessage());
			}
			logger.error("Cannot update Sinantropo" + e.getMessage());
			throw e;		
		}
		
		
		setMessaggio("Modica Animale dello Zoo/Circo avvenuta con successo");
		redirectTo( "sinantropi.DetailZoo.us?idSinantropo="+idSinantropo);	
					
	}
}


