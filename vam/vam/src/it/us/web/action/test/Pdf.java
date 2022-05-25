/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.test;

import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.apache.commons.beanutils.PropertyUtils;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.vam.Accettazione;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.report.PdfReport;

public class Pdf extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{

	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		//piccolo test
		BUtente u = new BUtente();
		u.setEntered( new Date() );
		Object o = null;
		o = PropertyUtils.getProperty( u, "entered" );
		System.out.println( o );
		
		PdfReport report = new PdfReport();
		report.setOrientamento( PdfReport.ORIZZONTALE );
		report.setIntestazione( "Elenco Accettazioni" );
		report.setFooterLeft( "footer sinistro" );
		report.setFooterRight( "footer destro" );
		report.setFooterMiddle( "footer centrale" );
		
		report.setItems( persistence.findAll( Accettazione.class ) );
		report.setElementiPerPagina( 5 );
		
		report.addColonna( "data",								"DATA",				0.1f, -1 );
		report.addColonna( "animale.identificativo",			"IDENTIFICATIVO",	0.1f, -1 );
		report.addColonna( "richiedenteNome",					"NOME",				0.1f, -1 );
		report.addColonna( "richiedenteCognome",				"COGNOME",			0.1f, -1 );
		report.addColonna( "richiedenteForzaPubblicaComune",	"Comune Comando",	0.1f, 13 );
		
		report.addFiltro( "Bannane > 10" );
		report.addFiltro( "Scimmie < 4" );
		report.addFiltro( "XXXX > yyyy" );
		report.addFiltro( "Beta > alpha" );
		
		report.addRiepilogoItem( "Asl bn 1", "10" );
		report.addRiepilogoItem( "Asl bn 2", "10" );
		report.addRiepilogoItem( "Asl bn 3", "10" );
		report.addRiepilogoItem( "Asl bn 4", "10" );
		report.addRiepilogoItem( "Asl bn 5", "10" );
		report.addRiepilogoItem( "Asl bn 6", "10" );
		report.addRiepilogoItem( "Asl bn 7", "10" );
		report.addRiepilogoItem( "Asl bn 8", "10" );
		report.addRiepilogoItem( "Asl bn 9", "10" );
		report.addRiepilogoItem( "Asl bn 10", "10" );
		report.addRiepilogoItem( "Asl bn 11", "10" );
		report.addRiepilogoItem( "Asl bn 12", "10" );
		report.addRiepilogoItem( "Asl bn 13", "10" );
		report.addRiepilogoItem( "Asl bn 14", "10" );
		
		res.setContentType( "application/pdf" );
		res.setHeader( "Content-Disposition","attachment; filename=\"pippo.pdf\";" );
		ServletOutputStream sout = res.getOutputStream();
		sout.write( report.render() );
		sout.flush();
	}

	@Override
	public void setSegnalibroDocumentazione() {
		// TODO Auto-generated method stub
		
	}

}
