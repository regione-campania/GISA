/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.statistiche;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class DiagnosticaCadavericaResult extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "STATISTICHE", "MAIN", "MAIN" );
		can( gui, "w" );
	}

	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("statistiche");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		String anno = stringaFromRequest( "anno" );
		int totMorti = 0;
		int totEsaminati = 0;
		String totPercentuale = "--";
		
		//cani
		List<Object[]> result = persistence.createSQLQuery( 
				"select x.morti as morti, y.esaminati as esaminati , " +
				"case when x.morti <> 0 then to_char( y.esaminati * 100.0 / x.morti, '990.99' ) || '%' else '--' end as perc from\r\n" + 
				"	(\r\n" + 
				"	select count(*) as morti from sync_cane \r\n" + 
				"	where \r\n" + 
				"	proprietario_tipo = 19 and\r\n" + 
				"	date_part( 'year', data_decesso ) = " + anno + 
				"	and\r\n" + 
				"	decesso_value ilike '%naturale%'\r\n" + 
				"	) x,\r\n" + 
				"	(\r\n" + 
				"	select count(*) as esaminati from\r\n" + 
				"	autopsia a \r\n" + 
				"	join cartella_clinica cc on a.cartella_clinica = cc.id\r\n" + 
				"	join accettazione acc on cc.accettazione = acc.id\r\n" + 
				"	join animale an on acc.animale = an.id\r\n" + 
				"	join sync_cane sc on sc.mc = an.identificativo\r\n" + 
				"	where\r\n" + 
				"	sc.proprietario_tipo = 19 and\r\n" + 
				"	date_part( 'year', sc.data_decesso ) = " + anno + 
				"	and\r\n" + 
				"	decesso_value ilike '%naturale%'\r\n" + 
				"	) y"
		).setMaxResults( 1 ).list();
				
		for( Object[] obj: result )
		{
			req.setAttribute( "anno", anno );
			req.setAttribute( "caniMorti", obj[0] );
			req.setAttribute( "caniEsaminati", obj[1] );
			req.setAttribute( "caniPercentuale", obj[2] );
			totMorti		+= ((BigInteger)obj[0]).intValue();
			totEsaminati	+= ((BigInteger)obj[1]).intValue();
		}
		
		//gatti
		result = persistence.createSQLQuery( 
				"select x.morti as morti, y.esaminati as esaminati ,\r\n" + 
				"case when x.morti <> 0 then to_char( y.esaminati * 100.0 / x.morti, '990.99' ) || '%' else '--' end as perc from\r\n" + 
				"	(\r\n" + 
				"	select count(*) as morti from sync_gatto \r\n" + 
				"	where \r\n" + 
				"	in_colonia and\r\n" + 
				"	date_part( 'year', data_decesso ) = " + anno + 
				"	and\r\n" + 
				"	decesso_value ilike '%naturale%'\r\n" + 
				"	) x,\r\n" + 
				"	(\r\n" + 
				"	select count(*) as esaminati from\r\n" + 
				"	autopsia a \r\n" + 
				"	join cartella_clinica cc on a.cartella_clinica = cc.id\r\n" + 
				"	join accettazione acc on cc.accettazione = acc.id\r\n" + 
				"	join animale an on acc.animale = an.id\r\n" + 
				"	join sync_gatto sc on sc.mc = an.identificativo\r\n" + 
				"	where\r\n" + 
				"	sc.in_colonia and\r\n" + 
				"	date_part( 'year', sc.data_decesso ) = " + anno + 
				"	and\r\n" + 
				"	decesso_value ilike '%naturale%'\r\n" + 
				"	) y"
		).setMaxResults( 1 ).list();
		
		for( Object[] obj: result )
		{
			req.setAttribute( "gattiMorti", obj[0] );
			req.setAttribute( "gattiEsaminati", obj[1] );
			req.setAttribute( "gattiPercentuale", obj[2] );
			totMorti		+= ((BigInteger)obj[0]).intValue();
			totEsaminati	+= ((BigInteger)obj[1]).intValue();
		}
		
		//sinantropi
		result = persistence.createSQLQuery( 
				"select x.morti as morti, y.esaminati as esaminati ,\r\n" + 
				"case when x.morti <> 0 then to_char( y.esaminati * 100.0 / x.morti, '990.99' ) || '%' else '--' end as perc from\r\n" + 
				"	(\r\n" + 
				"	select count(*) as morti from sinantropo\r\n" + 
				"	where \r\n" + 
				"	date_part( 'year', data_decesso ) = " + anno + 
				"	and\r\n" + 
				"	causa_decesso <> 2\r\n" + 
				"	) x,\r\n" + 
				"	(\r\n" + 
				"	select count(*) as esaminati from\r\n" + 
				"	autopsia a\r\n" + 
				"	join cartella_clinica cc on a.cartella_clinica = cc.id\r\n" + 
				"	join accettazione acc on cc.accettazione = acc.id\r\n" + 
				"	join animale an on acc.animale = an.id\r\n" + 
				"	join sinantropo sc on sc.numero_automatico = an.identificativo\r\n" + 
				"	where\r\n" + 
				"	date_part( 'year', sc.data_decesso ) = " + anno + 
				"	and\r\n" + 
				"	sc.causa_decesso <> 2\r\n" + 
				"	) y"
		).setMaxResults( 1 ).list();
		
		for( Object[] obj: result )
		{
			req.setAttribute( "sinantropiMorti", obj[0] );
			req.setAttribute( "sinantropiEsaminati", obj[1] );
			req.setAttribute( "sinantropiPercentuale", obj[2] );
			totMorti		+= ((BigInteger)obj[0]).intValue();
			totEsaminati	+= ((BigInteger)obj[1]).intValue();
		}
		
		if( totMorti > 0 )
		{
			DecimalFormat df = new DecimalFormat("0.00");
			df.setDecimalFormatSymbols( DecimalFormatSymbols.getInstance( Locale.US ) );
			totPercentuale = df.format( totEsaminati * 100.0 / totMorti ) + "%";
		}


		req.setAttribute( "totMorti", totMorti );
		req.setAttribute( "totEsaminati", totEsaminati );
		req.setAttribute( "totPercentuale", totPercentuale );
		
		gotoPage( "/jsp/vam/statistiche/diagnosticaCadaverica/result.jsp" );
	}
	
	
	
}
