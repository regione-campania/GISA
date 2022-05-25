/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneml.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneml.base.SuapMasterListAggregazioneList;
import org.aspcfs.modules.gestioneml.base.SuapMasterListLineaAttivitaList;
import org.aspcfs.modules.gestioneml.base.SuapMasterListMacroareaList;
import org.aspcfs.modules.gestioneml.util.MasterListImportExcel;
import org.aspcfs.modules.gestioneml.util.MasterListImportRiga;
import org.aspcfs.modules.gestioneml.util.MasterListImportUtil;
import org.aspcfs.modules.gestioneml.util.MasterListImportUtilAllevamenti;
import org.aspcfs.modules.gestioneml.util.MasterListImportUtilNoScia;
import org.aspcfs.modules.gestioneml.util.Nodo;
import org.aspcfs.modules.gestioneml.util.UtilExc;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;



public class GestioneMasterList extends CFSModule {
	
	public String executeCommandCostruisciMasterList(ActionContext context)  {

		String tipologiaDaVisualizzare = context.getParameter("tipologia");
		Connection db = null;
		try
		{
			db = super.getConnection(context);
			switch(tipologiaDaVisualizzare) 
			{
			case "macroarea" :
			{
				int flussoOrig =-1 ;
				if(context.getParameter("flussoOrig")!=null && !context.getParameter("flussoOrig").equals("null"))
				{
					flussoOrig = Integer.parseInt(context.getParameter("flussoOrig"));
				}
				int rev =-1 ;
				if(context.getParameter("rev")!=null && !context.getParameter("rev").equals("null"))
				{
					rev = Integer.parseInt(context.getParameter("rev"));
				}
				SuapMasterListMacroareaList masterListSuapMacroarea = new SuapMasterListMacroareaList();
				masterListSuapMacroarea.setFlussoOrig(flussoOrig);
				masterListSuapMacroarea.setRev(rev);

				String flagFisso = context.getParameter("flagFisso");
				String flagMobile = context.getParameter("flagMobile");
				String flagApicoltura = context.getParameter("flagApicoltura");
				String flagRegistrabili = context.getParameter("flagRegistrabili");
				String flagRiconoscibili = context.getParameter("flagRiconoscibili");
				String flagSintesis = context.getParameter("flagSintesis");
				String flagBdu = context.getParameter("flagBdu");
				String flagVam = context.getParameter("flagVam");
				String flagNoScia = context.getParameter("flagNoScia"); 
				
				if (flagFisso!=null && !flagFisso.equals("null"))
					masterListSuapMacroarea.setFlagFisso(flagFisso);
				if (flagMobile!=null && !flagMobile.equals("null"))
					masterListSuapMacroarea.setFlagMobile(flagMobile);
				if (flagApicoltura!=null && !flagApicoltura.equals("null"))
					masterListSuapMacroarea.setFlagApicoltura(flagApicoltura);
				if (flagRegistrabili!=null && !flagRegistrabili.equals("null"))
					masterListSuapMacroarea.setFlagRegistrabili(flagRegistrabili);
				if (flagRiconoscibili!=null && !flagRiconoscibili.equals("null"))
					masterListSuapMacroarea.setFlagRiconoscibili(flagRiconoscibili);
				if (flagSintesis!=null && !flagSintesis.equals("null"))
					masterListSuapMacroarea.setFlagSintesis(flagSintesis);
				if (flagBdu!=null && !flagBdu.equals("null"))
					masterListSuapMacroarea.setFlagBdu(flagBdu);
				if (flagVam!=null && !flagVam.equals("null"))
					masterListSuapMacroarea.setFlagVam(flagVam);
				if (flagNoScia!=null && !flagNoScia.equals("null"))
					masterListSuapMacroarea.setFlagNoScia(flagNoScia);
				
				masterListSuapMacroarea.buildList(db);
				context.getRequest().setAttribute("Lista", masterListSuapMacroarea);
				
				break ;
			}
			case "aggregazione" :
			{
				int flussoOrig =-1 ;
				if(context.getParameter("flussoOrig")!=null && !context.getParameter("flussoOrig").equals("null"))
				{
					flussoOrig = Integer.parseInt(context.getParameter("flussoOrig"));
				}
				
				if (context.getParameter("idMacroarea")==null || context.getParameter("idMacroarea").equals(""))
					break ;
				
				int idMacroarea = Integer.parseInt(context.getParameter("idMacroarea"));
				
				String flagFisso = context.getParameter("flagFisso");
				String flagMobile = context.getParameter("flagMobile");
				String flagApicoltura = context.getParameter("flagApicoltura");
				String flagRegistrabili = context.getParameter("flagRegistrabili");
				String flagRiconoscibili = context.getParameter("flagRiconoscibili");
				String flagSintesis = context.getParameter("flagSintesis");
				String flagBdu = context.getParameter("flagBdu");
				String flagVam = context.getParameter("flagVam");
				String flagNoScia = context.getParameter("flagNoScia");
				
				SuapMasterListAggregazioneList masterListSuapAggregazione = new SuapMasterListAggregazioneList();
				masterListSuapAggregazione.setIdMacroarea(idMacroarea);
				masterListSuapAggregazione.setFlussoOrig(flussoOrig);
				
				if (flagFisso!=null && !flagFisso.equals("null"))
					masterListSuapAggregazione.setFlagFisso(flagFisso);
				if (flagMobile!=null && !flagMobile.equals("null"))
					masterListSuapAggregazione.setFlagMobile(flagMobile);
				if (flagApicoltura!=null && !flagApicoltura.equals("null"))
					masterListSuapAggregazione.setFlagApicoltura(flagApicoltura);
				if (flagRegistrabili!=null && !flagRegistrabili.equals("null"))
					masterListSuapAggregazione.setFlagRegistrabili(flagRegistrabili);
				if (flagRiconoscibili!=null && !flagRiconoscibili.equals("null"))
					masterListSuapAggregazione.setFlagRiconoscibili(flagRiconoscibili);
				if (flagSintesis!=null && !flagSintesis.equals("null"))
					masterListSuapAggregazione.setFlagSintesis(flagSintesis);
				if (flagBdu!=null && !flagBdu.equals("null"))
					masterListSuapAggregazione.setFlagBdu(flagBdu);
				if (flagVam!=null && !flagVam.equals("null"))
					masterListSuapAggregazione.setFlagVam(flagVam);
				if (flagNoScia!=null && !flagNoScia.equals("null"))
					masterListSuapAggregazione.setFlagNoScia(flagNoScia);
				
				masterListSuapAggregazione.buildList(db);
				context.getRequest().setAttribute("Lista", masterListSuapAggregazione);
				break ;
				
			}
			case "attivita":
			{
				if (context.getParameter("idAggregazione")==null || context.getParameter("idAggregazione").equals(""))
					break ;
				
				int idAggregazione = Integer.parseInt(context.getParameter("idAggregazione"));
				
				String idFlussoOrig = context.getParameter("idFlussoOrig"); 
				String flagFisso = context.getParameter("flagFisso");
				String flagMobile = context.getParameter("flagMobile");
				String flagApicoltura = context.getParameter("flagApicoltura");
				String flagRegistrabili = context.getParameter("flagRegistrabili");
				String flagRiconoscibili = context.getParameter("flagRiconoscibili");
				String flagSintesis = context.getParameter("flagSintesis");
				String flagBdu = context.getParameter("flagBdu");
				String flagVam = context.getParameter("flagVam");
				String flagNoScia = context.getParameter("flagNoScia");
				
				SuapMasterListLineaAttivitaList masterListSuapLineaAttivita = new SuapMasterListLineaAttivitaList();
				masterListSuapLineaAttivita.setIdAggregazione(idAggregazione);
				
				if (flagFisso!=null && !flagFisso.equals("null"))
					masterListSuapLineaAttivita.setFlagFisso(flagFisso);
				if (flagMobile!=null && !flagMobile.equals("null"))
					masterListSuapLineaAttivita.setFlagMobile(flagMobile);
				if (flagApicoltura!=null && !flagApicoltura.equals("null"))
					masterListSuapLineaAttivita.setFlagApicoltura(flagApicoltura);
				if (flagRegistrabili!=null && !flagRegistrabili.equals("null"))
					masterListSuapLineaAttivita.setFlagRegistrabili(flagRegistrabili);
				if (flagRiconoscibili!=null && !flagRiconoscibili.equals("null"))
					masterListSuapLineaAttivita.setFlagRiconoscibili(flagRiconoscibili);
				if (flagSintesis!=null && !flagSintesis.equals("null"))
					masterListSuapLineaAttivita.setFlagSintesis(flagSintesis);
				if (flagBdu!=null && !flagBdu.equals("null"))
					masterListSuapLineaAttivita.setFlagBdu(flagBdu);
				if (flagVam!=null && !flagVam.equals("null"))
					masterListSuapLineaAttivita.setFlagVam(flagVam);
				if (flagNoScia!=null && !flagNoScia.equals("null"))
					masterListSuapLineaAttivita.setFlagNoScia(flagNoScia);
				
				masterListSuapLineaAttivita.buildList(db);
				context.getRequest().setAttribute("Lista", masterListSuapLineaAttivita);
				break ;
				
			}
			default :
			{
				
			}

			}
		}catch(SQLException e)
		{

		}
		finally
		{
			super.freeConnection(context, db);
		}

		return "masterListJSONOK";
	}
	
	
	public static String getPathCompleto(Connection db, int idLinea) throws SQLException{
		String ret = "";
		PreparedStatement pst = db.prepareStatement("select m.macroarea || ' -> ' || a.aggregazione || ' -> ' || l.linea_attivita l from "+ MasterListImportUtil.TAB_LINEA_ATTIVITA +" l join "+ MasterListImportUtil.TAB_AGGREGAZIONE +" a on a.id = l.id_aggregazione join "+ MasterListImportUtil.TAB_MACROAREA +" m on m.id = a.id_macroarea where l.id = ?");
		pst.setInt(1, idLinea);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			ret = rs.getString(1);
		return ret;
	}
	public static String getCodiceUnivoco(Connection db, int idLinea) throws SQLException{
		String ret = "";
		PreparedStatement pst = db.prepareStatement("select codice_univoco from "+ MasterListImportUtil.TAB_LINEA_ATTIVITA +" where id = ?");
		pst.setInt(1, idLinea);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			ret = rs.getString(1);
		return ret;
	}

}
