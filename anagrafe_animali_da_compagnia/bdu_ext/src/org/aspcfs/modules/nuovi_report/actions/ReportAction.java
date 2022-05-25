/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.nuovi_report.base.Stats;
import org.aspcfs.modules.nuovi_report.util.PdfTool;
import org.aspcfs.modules.nuovi_report.util.ReportTool;

import com.darkhorseventures.framework.actions.ActionContext;

public class ReportAction extends CFSModule
{
	public String executeCommandDefault(ActionContext context)
	{
		if (!((hasPermission(context, "nuovi-report-view"))||(hasPermission(context, "nuovi-report-veterinari-view"))))
		{
		      return ("PermissionError");
		}
		return (executeCommandGenerateReport(context));
		
	}

		private String executeCommandGenerateReport(ActionContext context)
	{
		ArrayList<String>	filtri		= new ArrayList<String>();
		ArrayList<Stats>	risultati	= new ArrayList<Stats>();
		ArrayList<Stats>	risultati2	= new ArrayList<Stats>();
		ArrayList<Stats>	risultati3	= new ArrayList<Stats>();
		ArrayList<Stats>	elenco		= new ArrayList<Stats>();
		Stats				header		= new Stats();
		boolean 			tipo_report	= Boolean.parseBoolean( context.getRequest().getParameter("tipo_report"));
		String 				asl= context.getRequest().getParameter("aslRif");
		float[] sizes = queryReport( context, filtri, risultati, elenco, header,risultati2,risultati3);
		//if (tipo_report==true){
			//PdfTool.print2( context, context.getParameter( "reportName" ), filtri, risultati, elenco, header, sizes,tipo_report,risultati2,risultati3);
			//}
		//else
			PdfTool.print2( context, context.getParameter( "reportName" ), filtri, risultati, elenco, header, sizes,tipo_report,risultati2,risultati3 );
		return  "-none-";
	}

	private float[] queryReport(ActionContext context, ArrayList<String> filtri, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, ArrayList<Stats> risultati2,ArrayList<Stats> risultati3)
	{
		Connection	db		= null;
		float[]		sizes	= null;
		
		try
		{
			db = getConnection(context);
		
			switch (Integer.parseInt( context.getParameter( "reportId" ) ))
			{
			case REPORT_ANAGRAFATI:
				sizes = ReportTool.reportAnagrafati( context, risultati, elenco, header, db );
				break;
			case REPORT_ANAGRAFATI2:
				sizes = ReportTool.reportAnagrafatiRev2( context, risultati, elenco, header, db ,risultati2,risultati3);
				//sizes = ReportTool.reportAnagrafatiRev2( context, risultati, elenco, header, db ,risultati2);
				break;
			case REPORT_CATTURATI:
				sizes = ReportTool.reportCatturati( context, risultati, elenco, header, db );
				break;
			case REPORT_CESSIONI:
				sizes = ReportTool.reportCessioni( context, risultati, elenco, header, db );
				break;
			case REPORT_RESTITUITI:
				sizes = ReportTool.reportRestituiti( context, risultati, elenco, header, db );
				break;
			case REPORT_SMARRITI:
				sizes = ReportTool.reportSmarriti( context, risultati, elenco, header, db );
				break;
			case REPORT_STERILIZZATI:
				sizes = ReportTool.reportSterilizzati( context, risultati, elenco, header, db );
				break;
			case REPORT_ANAGRAFATI_VET:
				sizes = ReportTool.reportAnagrafatiVet( context, risultati, elenco, header, db );
				break;
			case REPORT_STERILIZZATI_VET:
				sizes = ReportTool.reportSterilizzatiVet( context, risultati, elenco, header, db );
				break;
			
			}
			
			ReportTool.load( context, filtri, db );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if( db != null )
			{
				try
				{
					db.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return sizes;
	}

	private void queryReportTest(ActionContext context, ArrayList<String> filtri,
			ArrayList<Stats> risultati, ArrayList<Stats> elenco)
	{
		filtri.add( "ASL: Benevento 1" );
		filtri.add( "Dal: 12/11/2008" );
		filtri.add( "Fino al: 11/11/2009" );
		filtri.add( "Stato: Tutti" );
		filtri.add( "Dettaglio: Microchip" );
		filtri.add( "Fuori Regione: No" );
		
		Stats st = new Stats();
		st.add( "ASL" );
		st.add( "registrati" );
		st.add( "vivi" );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 1  " );
		st.add( "668  " );
		st.add( "289  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 2  " );
		st.add( "78  " );
		st.add( "56  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Benevento 1  " );
		st.add( "123  " );
		st.add( "89  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 1  " );
		st.add( "668  " );
		st.add( "289  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 2  " );
		st.add( "78  " );
		st.add( "56  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Benevento 1  " );
		st.add( "123  " );
		st.add( "89  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 1  " );
		st.add( "668  " );
		st.add( "289  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 2  " );
		st.add( "78  " );
		st.add( "56  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Benevento 1  " );
		st.add( "123  " );
		st.add( "89  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 1  " );
		st.add( "668  " );
		st.add( "289  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 2  " );
		st.add( "78  " );
		st.add( "56  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Benevento 1  " );
		st.add( "123  " );
		st.add( "89  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 1  " );
		st.add( "668  " );
		st.add( "289  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Avellino 2  " );
		st.add( "78  " );
		st.add( "56  " );
		risultati.add( st );
		st = new Stats();
		st.add( "Benevento 1  " );
		st.add( "123  " );
		st.add( "89  " );
		risultati.add( st );
		
	}
	public static final int REPORT_ANAGRAFATI	= 1;
	public static final int REPORT_CESSIONI		= 2;
	public static final int REPORT_CATTURATI	= 3;
	public static final int REPORT_RESTITUITI	= 4;
	public static final int REPORT_SMARRITI		= 5;
	public static final int REPORT_STERILIZZATI	= 6;
	public static final int REPORT_ANAGRAFATI_VET	= 7;
	public static final int REPORT_STERILIZZATI_VET	= 8;
	public static final int REPORT_ANAGRAFATI2	= 9;
	
}
