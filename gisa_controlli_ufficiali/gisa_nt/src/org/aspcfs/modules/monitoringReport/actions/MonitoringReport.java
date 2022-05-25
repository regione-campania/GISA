/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.monitoringReport.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.monitoringReport.base.TipoReport;
import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.DocumentException;


public final class MonitoringReport extends CFSModule 
{
	
	Logger logger = Logger.getLogger("MainLogger");
	
	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandDashboard(context);
	}
	
	public String executeCommandDashboard(ActionContext context) {
		
		 if (!hasPermission(context, "accounts-dashboard-view")) {
			 if (!hasPermission(context, "monitoring-report-view")) {
		        return ("PermissionError");
		     }
		 } 
		 
		 return (executeCommandGenerateReport(context));
		  
	}

	public String executeCommandGenerateReport(ActionContext context) {
		
		// TODO Auto-generated method stub
		String tipoReport = (String) context.getRequest().getParameter("tipoReport");
		try {
			if(tipoReport != null){
				TipoReport tr = TipoReport.valueOf(tipoReport);
				switch (tr) {
					case mobili: return ToExportReportMobili(context); 
					case lavoro: return ToExportReportWorkLoadDetails(context); 
					case riepilogo: return ToExportReportWorkLoadRiepilogo(context);
					case dettaglio_mobili: return ToExportReportMobiliDetails(context);
					default: System.out.println("Tipo Report non previsto!");
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return "ListOK";
		
	}
	
	private String ToExportReportMobili(ActionContext context) throws DocumentException, FileNotFoundException {
		
		// TODO Auto-generated method stub
		String data_inizio_periodo = (String) context.getRequest().getParameter("searchtimestampInizio");
		String data_fine_periodo = (String) context.getRequest().getParameter("searchtimestampFine");
		String between_date = null;
		if((data_inizio_periodo != null && !data_inizio_periodo.equals("") )&& (data_fine_periodo != null && !data_fine_periodo.equals(""))){
			between_date = " BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') ";
		}
		else if(data_inizio_periodo != null && !data_inizio_periodo.equals("")){
			between_date = " >= to_date('"+data_inizio_periodo+"','DD/MM/YYYY') ";
		}
		else{
			between_date = " <= to_date('"+data_fine_periodo+"','DD/MM/YYYY') ";
		}
		
		String ipgtw = (String) context.getRequest().getParameter("ipgtw");
		String fileName = "Report_monitoraggio_operatori_mobili.xls";
		java.sql.Connection db = null;    
		    try {
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    	db = this.getConnection(context);
		    	if(ipgtw !=null && ipgtw!="null" && !ipgtw.equals("null")){
		    		updateIPenabled(db,true);
		    	}
		    	else {
		    		updateIPenabled(db,false);
		    	}
		    	
				WritableWorkbook wb = Workbook.createWorkbook(bos);
				WritableSheet sheet = wb.createSheet("Monitoraggio", 0);
				//Font
				WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
				WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
				headerFormat.setBackground(Colour.YELLOW);
				headerFormat.setAlignment(Alignment.CENTRE);
				
				
				sheet.addCell( new Label(0,0,"PERIODO DAL "+ data_inizio_periodo +" AL "+data_fine_periodo,headerFormat) );
				sheet.mergeCells(0, 0, 5, 0);
				
				//Header
				sheet.addCell( new Label(0,1,"ASL",headerFormat) );
				sheet.addCell( new Label(1,1,"Assegnatario PC",headerFormat) );
				sheet.addCell( new Label(2,1,"IP",headerFormat) );
				sheet.addCell( new Label(3,1,"Data consegna PC",headerFormat) );
				sheet.addCell( new Label(4,1,"Attivita inserite",headerFormat) );
				sheet.addCell( new Label(5,1,"Operatori inseriti",headerFormat) );
				
				//Content
				int numFoglio = 1;
				int numRiga = 2;
				
				String select = " SELECT m.asl, (m.cognome || ' ' || m.nome) as assegnatario_pc, m.ip_portatile, m.data_consegna_portatile, numero_operatori_inseriti, numero_controlli_inseriti from ( "+
								" SELECT m.asl, (m.cognome || ' ' || m.nome) as assegnatario_pc, m.ip_portatile, m.data_consegna_portatile, sum(o.num_operazioni) as numero_operatori_inseriti "+
								" FROM view_monitoring_report_operatori_mobili_organization o "+ 
								" RIGHT JOIN monitoring_report m on m.ip_portatile = o.identificativo_pc "+
								" WHERE to_date(o.data_operazione_operatori,'YYYY-MM-DD') "+ between_date +
								//" BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') "+
								" GROUP BY m.ip_portatile, m.asl, assegnatario_pc, m.ip_portatile, m.data_consegna_portatile)org RIGHT JOIN "+
								" ( SELECT m.asl, (m.cognome || ' ' || m.nome) as assegnatario_pc, m.ip_portatile, m.data_consegna_portatile, sum(t.num_operazioni) as numero_controlli_inseriti "+
								" FROM view_monitoring_report_operatori_mobili_ticket t "+ 
								" RIGHT JOIN monitoring_report m on m.ip_portatile = t.identificativo_pc "+
								" WHERE to_date(t.data_operazione_controlli,'YYYY-MM-DD') " + between_date +
								//" BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') "+
								" GROUP BY m.ip_portatile, m.asl, assegnatario_pc, m.ip_portatile, m.data_consegna_portatile "+
								" ) tic on org.ip_portatile = tic.ip_portatile "+
								" RIGHT JOIN monitoring_report m on m.ip_portatile = tic.ip_portatile " +
								" WHERE ip_enabled = TRUE ";
				PreparedStatement stat = db.prepareStatement( select );
				ResultSet res = stat.executeQuery();
				//for(OrganizationView o : orgListAttivita){
				while( res.next() )
				{	
					//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
					if(numRiga % 60001 == 0){
						
						numRiga = 2;
						numFoglio++;
						sheet = wb.createSheet("Attivita'-" + numFoglio, numFoglio);
						
						//Riscrivo l'Header
						sheet.addCell( new Label(0,1,"ASL",headerFormat) );
						sheet.addCell( new Label(1,1,"Assegnatario PC",headerFormat) );
						sheet.addCell( new Label(2,1,"IP",headerFormat) );
						sheet.addCell( new Label(3,1,"Data operazione",headerFormat) );
						sheet.addCell( new Label(4,1,"Attivita inserite",headerFormat) );
						sheet.addCell( new Label(5,1,"Operatori inseriti",headerFormat) );
					}
					
					sheet.addCell( new Label(0,numRiga,res.getString("asl")));
					sheet.addCell( new Label(1,numRiga,res.getString("assegnatario_pc")));
					sheet.addCell( new Label(2,numRiga,res.getString("ip_portatile")));
					sheet.addCell( new Label(3,numRiga,res.getString("data_consegna_portatile")));
					sheet.addCell( new Label(4,numRiga,Integer.toString(res.getInt("numero_controlli_inseriti")))) ;
					sheet.addCell( new Label(5,numRiga,Integer.toString(res.getInt("numero_operatori_inseriti"))));
					
					numRiga++;
				}
				
				//Write & Close
				wb.write();
				wb.close();
				
				if(bos.size() > (1024 * 1024 * 10) ){
					
					fileName += ".zip";
				    context.getResponse().setContentType( "application/zip" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					
					//Inizio Zip
					GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
					
					ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
					
					int lunghezza;
					byte[] buffer = new byte[1024];
				    while ((lunghezza = bis.read(buffer)) > 0){
				    	zipOutputStream.write(buffer, 0, lunghezza);
				    }
				    
				    bos.close();
				    bis.close();
				    res.close();
				    zipOutputStream.finish();
				    zipOutputStream.close();
					//Fine Zip
					
				}
				else{
					context.getResponse().setContentType( "application/xls" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					context.getResponse().getOutputStream().write(bos.toByteArray());
				}
				
			} 
		    catch (IOException ioe) {
				ioe.printStackTrace();
			}
		    catch(WriteException we){
		    	we.printStackTrace();
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				this.freeConnection(context, db);
			}
		    
		return "-none-";
	}
	
	private void updateIPenabled(Connection db, boolean bool) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
		        "UPDATE monitoring_report " +
		        "SET ip_enabled = ? where ip_portatile = ? ");
		  
		int i = 0;
	    pst = db.prepareStatement(sql.toString());		
		pst.setBoolean(++i, bool);
		pst.setString(++i, "10.1.15.254");
		pst.executeUpdate();
		pst.close();
		
		
	}

	private String ToExportReportMobiliDetails(ActionContext context) {
		
		// TODO Auto-generated method stub
		
		String data_inizio_periodo = (String) context.getRequest().getParameter("searchtimestampMobiliDal");
		String data_fine_periodo = (String) context.getRequest().getParameter("searchtimestampMobiliAl");
		String between_date = null;
		if((data_inizio_periodo != null && !data_inizio_periodo.equals("") )&& (data_fine_periodo != null && !data_fine_periodo.equals(""))){
			between_date = " BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') ";
		}
		else if(data_inizio_periodo != null && !data_inizio_periodo.equals("")){
			between_date = " >= to_date('"+data_inizio_periodo+"','DD/MM/YYYY') ";
		}
		else{
			between_date = " <= to_date('"+data_fine_periodo+"','DD/MM/YYYY') ";
		}
		String ipgtw = (String) context.getRequest().getParameter("ipgtw");
		String fileName = "Report_monitoraggio_operatori_mobili_dettaglio.xls";
		java.sql.Connection db = null;    
		    try {
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    	db = this.getConnection(context);
		    	if(ipgtw !=null && ipgtw!="null" && !ipgtw.equals("null")){
		    		updateIPenabled(db,true);
		    	}
		    	else {
		    		updateIPenabled(db,false);
		    	}
				WritableWorkbook wb = Workbook.createWorkbook(bos);
				WritableSheet sheet = wb.createSheet("Monitoraggio", 0);
				//Font
				WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
				WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
				headerFormat.setBackground(Colour.YELLOW);
				headerFormat.setAlignment(Alignment.CENTRE);
				sheet.addCell( new Label(0,0,"PERIODO DAL "+ data_inizio_periodo +" AL "+data_fine_periodo,headerFormat) );
				sheet.mergeCells(0, 0, 6, 0);
				
				//Header
				sheet.addCell( new Label(0,1,"IP",headerFormat) );
				sheet.addCell( new Label(1,1,"Assegnatario Pc",headerFormat) );
				sheet.addCell( new Label(2,1,"Asl",headerFormat) );
				sheet.addCell( new Label(3,1,"Utente loggato",headerFormat) );
				sheet.addCell( new Label(4,1,"Tipo Operazione",headerFormat) );
				sheet.addCell( new Label(5,1,"Data Operazione",headerFormat) );
				sheet.addCell( new Label(6,1,"Numero operazioni",headerFormat) );
				
				//Content
				int numFoglio = 1;
				int numRiga = 2;
				
				/*select m.ip_portatile, (m.cognome || ' ' || m.nome) as assegnatario_pc, m.asl, utente_loggato, 
					tipologia_operazione, data_operazione, numero_operazioni
					from (
						select identificativo_pc, nome_cognome as utente_loggato,  sum(num_operazioni) as numero_operazioni, tipologia_operatore as tipologia_operazione,
						data_operazione_operatori as data_operazione 
						from view_monitoring_report_operatori_mobili_organization 
						group by nome_cognome, identificativo_pc, tipologia_operazione, data_operazione_operatori
						union
						select identificativo_pc, nome_cognome as utente_loggato, sum(num_operazioni) as numero_operazioni, tipologia_operazione,
						data_operazione_controlli as data_operazione
						from view_monitoring_report_operatori_mobili_ticket v
						group by v.nome_cognome, v.identificativo_pc, tipologia_operazione, data_operazione_controlli
					) a right join monitoring_report m 
					on a.identificativo_pc = m.ip_portatile
					where m.ip_enabled != false and m.ip_portatile is not null
				 */
				String select = " SELECT m.ip_portatile, (m.cognome || ' ' || m.nome) as assegnatario_pc, m.asl, utente_loggato, " +
								" tipologia_operazione, data_operazione, numero_operazioni "+ 
								" FROM ( "+
								"	SELECT identificativo_pc, nome_cognome as utente_loggato,  sum(num_operazioni) as numero_operazioni, tipologia_operatore as tipologia_operazione, "+
								"	data_operazione_operatori as data_operazione "+
								"	FROM view_monitoring_report_operatori_mobili_organization "+ 
								"	GROUP BY nome_cognome, identificativo_pc, tipologia_operazione, data_operazione_operatori "+
								"	UNION "+
								"	SELECT identificativo_pc, nome_cognome as utente_loggato, sum(num_operazioni) as numero_operazioni, tipologia_operazione, "+
								"	data_operazione_controlli as data_operazione "+
								"	FROM view_monitoring_report_operatori_mobili_ticket v "+
								"	GROUP BY v.nome_cognome, v.identificativo_pc, tipologia_operazione, data_operazione_controlli "+
								" ) a right join monitoring_report m on a.identificativo_pc = m.ip_portatile "+
								" WHERE m.ip_enabled != false and m.ip_portatile is not null AND to_date(data_operazione,'YYYY-MM-DD') "+
								between_date;
								//" BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') ";
				PreparedStatement stat = db.prepareStatement( select );
				ResultSet res = stat.executeQuery();
				//for(OrganizationView o : orgListAttivita){
				while( res.next() )
				{	
					//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
					if(numRiga % 60001 == 0){
						
						numRiga = 2;
						numFoglio++;
						sheet = wb.createSheet("Attivita'-" + numFoglio, numFoglio);
						
						//Riscrivo l'Header
						//Header
						sheet.addCell( new Label(0,1,"IP",headerFormat) );
						sheet.addCell( new Label(1,1,"Assegnatario Pc",headerFormat) );
						sheet.addCell( new Label(2,1,"Asl",headerFormat) );
						sheet.addCell( new Label(3,1,"Utente loggato",headerFormat) );
						sheet.addCell( new Label(4,1,"Tipo Operazione",headerFormat) );
						sheet.addCell( new Label(5,1,"Data Operazione",headerFormat) );
						sheet.addCell( new Label(6,1,"Numero operazioni",headerFormat) );
					}
					
					sheet.addCell( new Label(0,numRiga,res.getString("ip_portatile")));
					sheet.addCell( new Label(1,numRiga,res.getString("assegnatario_pc")));
					sheet.addCell( new Label(2,numRiga,res.getString("asl")));
					sheet.addCell( new Label(3,numRiga,res.getString("utente_loggato")));
					sheet.addCell( new Label(4,numRiga,res.getString("tipologia_operazione")));
					sheet.addCell( new Label(5,numRiga,getDateDDMMYYYY((res.getString("data_operazione")))));
					sheet.addCell( new Label(6,numRiga,res.getString("numero_operazioni")));

					
					numRiga++;
				}
				
				//Write & Close
				wb.write();
				wb.close();
				
				if(bos.size() > (1024 * 1024 * 10) ){
					
					fileName += ".zip";
				    context.getResponse().setContentType( "application/zip" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					
					//Inizio Zip
					GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
					
					ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
					
					int lunghezza;
					byte[] buffer = new byte[1024];
				    while ((lunghezza = bis.read(buffer)) > 0){
				    	zipOutputStream.write(buffer, 0, lunghezza);
				    }
				    
				    bos.close();
				    bis.close();
				    res.close();
				    zipOutputStream.finish();
				    zipOutputStream.close();
					//Fine Zip
					
				}
				else{
					context.getResponse().setContentType( "application/xls" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					context.getResponse().getOutputStream().write(bos.toByteArray());
				}
				
			} 
		    catch (IOException ioe) {
				ioe.printStackTrace();
			}
		    catch(WriteException we){
		    	we.printStackTrace();
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				this.freeConnection(context, db);
			}
		
		
		return "-none-";
	}
	
	
	
	private String ToExportReportWorkLoadDetails(ActionContext context) {
		
		// TODO Auto-generated method stub
		
		String data_inizio_periodo = (String) context.getRequest().getParameter("searchtimestampDal");
		String data_fine_periodo = (String) context.getRequest().getParameter("searchtimestampAl");
		String between_date = null;
		if((data_inizio_periodo != null && !data_inizio_periodo.equals("") )&& (data_fine_periodo != null && !data_fine_periodo.equals(""))){
			between_date = " BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') ";
		}
		else if(data_inizio_periodo != null && !data_inizio_periodo.equals("")){
			between_date = " >= to_date('"+data_inizio_periodo+"','DD/MM/YYYY') ";
		}
		else{
			between_date = " <= to_date('"+data_fine_periodo+"','DD/MM/YYYY') ";
		}
		String fileName = "Report_monitoraggio_carico_lavoro_utenti_dettaglio.xls";
		java.sql.Connection db = null;    
		    try {
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    	db = this.getConnection(context);
				WritableWorkbook wb = Workbook.createWorkbook(bos);
				WritableSheet sheet = wb.createSheet("Monitoraggio", 0);
				//Font
				WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
				WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
				headerFormat.setBackground(Colour.YELLOW);
				headerFormat.setAlignment(Alignment.CENTRE);
				sheet.addCell( new Label(0,0,"PERIODO DAL "+ data_inizio_periodo +" AL "+data_fine_periodo,headerFormat) );
				sheet.mergeCells(0, 0, 4, 0);
				
				//Header
				sheet.addCell( new Label(0,1,"ASL",headerFormat) );
				sheet.addCell( new Label(1,1,"Utente",headerFormat) );
				sheet.addCell( new Label(2,1,"Ruolo",headerFormat) );
				sheet.addCell( new Label(3,1,"Data Operazione",headerFormat) );
				sheet.addCell( new Label(4,1,"Tipo Operazione",headerFormat) );
				
				//Content
				int numFoglio = 1;
				int numRiga = 2;
				
				/*select s.user_id, l.description as asl, (c.namelast || ' ' || c.namefirst) as utente, r.role as ruolo, to_char(s.interaction_time,'DD-MM-YYYY') as data_operazione,
					d.operazione_carico_lavoro 
					from storico_operazioni_utenti s 
					left join decodifica_operazioni_utente d on s.action = d.action and s.command = d.command 
					left join access a on a.user_id = s.user_id
					left join contact c on c.user_id = s.user_id 
					left join role r on a.role_id = r.role_id 
					left join lookup_site_id l on l.code = a.site_id --per recuperare l'asl join su site_id
					where operazione_carico_lavoro is not null
				 */
				String select = " SELECT s.user_id, l.description as asl, (c.namelast || ' ' || c.namefirst) as utente, r.role as ruolo, " +
								" to_char(s.interaction_time,'DD-MM-YYYY') as data_operazione, d.operazione_carico_lavoro "+ 
								" FROM storico_operazioni_utenti s "+
								" LEFT JOIN decodifica_operazioni_utente d on s.action = d.action and s.command = d.command "+  
								" LEFT JOIN access a on a.user_id = s.user_id "+
								" LEFT JOIN contact c on c.user_id = s.user_id "+ 
								" LEFT JOIN role r on a.role_id = r.role_id "+
								" LEFT JOIN lookup_site_id l on l.code = a.site_id "+
								" WHERE operazione_carico_lavoro IS NOT NULL AND s.interaction_time " + between_date +
								//" BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') "+
								" AND (r.role_id != 1 and r.role_id != 32) AND s.ip NOT IN (SELECT ip FROM blacklist_ip) ";
				PreparedStatement stat = db.prepareStatement( select );
				ResultSet res = stat.executeQuery();
				//for(OrganizationView o : orgListAttivita){
				while( res.next() )
				{	
					//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
					if(numRiga % 60001 == 0){
						
						numRiga = 2;
						numFoglio++;
						sheet = wb.createSheet("Attivita'-" + numFoglio, numFoglio);
						
						//Riscrivo l'Header
						sheet.addCell( new Label(0,1,"ASL",headerFormat) );
						sheet.addCell( new Label(1,1,"Utente",headerFormat) );
						sheet.addCell( new Label(2,1,"Ruolo",headerFormat) );
						sheet.addCell( new Label(3,1,"Data Operazione",headerFormat) );
						sheet.addCell( new Label(4,1,"Tipo Operazione",headerFormat) );
					}
					
					sheet.addCell( new Label(0,numRiga,res.getString("asl")));
					sheet.addCell( new Label(1,numRiga,res.getString("utente")));
					sheet.addCell( new Label(2,numRiga,res.getString("ruolo")));
					sheet.addCell( new Label(3,numRiga,res.getString("data_operazione")));
					sheet.addCell( new Label(4,numRiga,res.getString("operazione_carico_lavoro"))) ;
					
					numRiga++;
				}
				
				//Write & Close
				wb.write();
				wb.close();
				
				if(bos.size() > (1024 * 1024 * 10) ){
					
					fileName += ".zip";
				    context.getResponse().setContentType( "application/zip" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					
					//Inizio Zip
					GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
					
					ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
					
					int lunghezza;
					byte[] buffer = new byte[1024];
				    while ((lunghezza = bis.read(buffer)) > 0){
				    	zipOutputStream.write(buffer, 0, lunghezza);
				    }
				    
				    bos.close();
				    bis.close();
				    res.close();
				    zipOutputStream.finish();
				    zipOutputStream.close();
					//Fine Zip
					
				}
				else{
					context.getResponse().setContentType( "application/xls" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					context.getResponse().getOutputStream().write(bos.toByteArray());
				}
				
			} 
		    catch (IOException ioe) {
				ioe.printStackTrace();
			}
		    catch(WriteException we){
		    	we.printStackTrace();
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				this.freeConnection(context, db);
			}
		
		
		return "-none-";
	}
	
	
	private String ToExportReportWorkLoadRiepilogo(ActionContext context) {
		
		// TODO Auto-generated method stub
		String data_inizio_periodo = (String) context.getRequest().getParameter("searchtimestampInizioPeriodo");
		String data_fine_periodo = (String) context.getRequest().getParameter("searchtimestampFinePeriodo");
		String between_date = null;
		if((data_inizio_periodo != null && !data_inizio_periodo.equals("") )&& (data_fine_periodo != null && !data_fine_periodo.equals(""))){
			between_date = " BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') ";
		}
		else if(data_inizio_periodo != null && !data_inizio_periodo.equals("")){
			between_date = " >= to_date('"+data_inizio_periodo+"','DD/MM/YYYY') ";
		}
		else{
			between_date = " <= to_date('"+data_fine_periodo+"','DD/MM/YYYY') ";
		}
		//Qui va fatta la query
		String fileName = "Report_monitoraggio_carico_lavoro_utenti_riepilogo.xls";
		java.sql.Connection db = null;    
		    try {
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    	db = this.getConnection(context);
				WritableWorkbook wb = Workbook.createWorkbook(bos);
				WritableSheet sheet = wb.createSheet("Monitoraggio", 0);
				//Font
				WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
				WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
				headerFormat.setBackground(Colour.YELLOW);
				headerFormat.setAlignment(Alignment.CENTRE);
				
				
				sheet.addCell( new Label(0,0,"PERIODO DAL "+ data_inizio_periodo +" AL "+data_fine_periodo,headerFormat) );
				sheet.mergeCells(0, 0, 4, 0);
				
				//Header
				sheet.addCell( new Label(0,1,"ASL",headerFormat) );
				sheet.addCell( new Label(1,1,"Utente",headerFormat) );
				sheet.addCell( new Label(2,1,"Ruolo",headerFormat) );
				sheet.addCell( new Label(3,1,"Tipo Operazione",headerFormat) );
				sheet.addCell( new Label(4,1,"Numero Operazioni",headerFormat) );
				
				
				//Content
				int numFoglio = 1;
				int numRiga = 2;
				
				/*select s.user_id, l.description as asl, (c.namelast || ' ' || c.namefirst) as utente, r.role as ruolo,
				d.operazione_carico_lavoro, count(*) as numero_operazioni  
				from storico_operazioni_utenti s 
				left join decodifica_operazioni_utente d on s.action = d.action and s.command = d.command 
				left join access a on a.user_id = s.user_id
				left join contact c on c.user_id = s.user_id 
				left join role r on a.role_id = r.role_id 
				left join lookup_site_id l on l.code = a.site_id --per recuperare l'asl join su site_id
				group by s.user_id, asl, utente, ruolo, d.operazione_carico_lavoro
				--where operazione_carico_lavoro is not null and */
				String select = " SELECT s.user_id, l.description as asl, (c.namelast || ' ' || c.namefirst) as utente, r.role as ruolo, "+
								" d.operazione_carico_lavoro, count(*) as numero_operazioni FROM storico_operazioni_utenti s "+
								" LEFT JOIN decodifica_operazioni_utente d on s.action = d.action and s.command = d.command "+ 
								" LEFT JOIN access a on a.user_id = s.user_id "+
								" LEFT JOIN contact c on c.user_id = s.user_id "+   
								" LEFT JOIN role r on a.role_id = r.role_id "+
								" LEFT JOIN lookup_site_id l on l.code = a.site_id "+
								" WHERE operazione_carico_lavoro IS NOT NULL AND s.interaction_time " + between_date +
								//" BETWEEN to_date('"+data_inizio_periodo+"','DD/MM/YYYY') and to_date('"+data_fine_periodo+"', 'DD/MM/YYYY') "+
								" AND (r.role_id != 1 and r.role_id != 32) AND s.ip NOT IN (SELECT ip FROM blacklist_ip) "+
								" GROUP BY s.user_id, asl, utente, ruolo, d.operazione_carico_lavoro ";				
				PreparedStatement stat = db.prepareStatement( select );
				ResultSet res = stat.executeQuery();
				//for(OrganizationView o : orgListAttivita){
				while( res.next() )
				{	
					//creo un foglio nuovo ogni 60.000 record (60.001 considerando l'header)
					if(numRiga % 60001 == 0){
						
						numRiga = 2;
						numFoglio++;
						sheet = wb.createSheet("Attivita'-" + numFoglio, numFoglio);
						
						//Riscrivo l'Header
						sheet.addCell( new Label(0,1,"ASL",headerFormat) );
						sheet.addCell( new Label(1,1,"Utente",headerFormat) );
						sheet.addCell( new Label(2,1,"Ruolo",headerFormat) );
						sheet.addCell( new Label(3,1,"Tipo Operazione",headerFormat) );
						sheet.addCell( new Label(4,1,"Numero Operazioni",headerFormat) );
					}
					
					sheet.addCell( new Label(0,numRiga,res.getString("asl")));
					sheet.addCell( new Label(1,numRiga,res.getString("utente")));
					sheet.addCell( new Label(2,numRiga,res.getString("ruolo")));
					sheet.addCell( new Label(3,numRiga,res.getString("operazione_carico_lavoro"))) ;
					sheet.addCell( new Label(4,numRiga,res.getString("numero_operazioni")));
					
					numRiga++;
				}
				
				//Write & Close
				wb.write();
				wb.close();
				
				if(bos.size() > (1024 * 1024 * 10) ){
					
					fileName += ".zip";
				    context.getResponse().setContentType( "application/zip" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					
					//Inizio Zip
					GZIPOutputStream zipOutputStream = new GZIPOutputStream(context.getResponse().getOutputStream());
					ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
					
					int lunghezza;
					byte[] buffer = new byte[1024];
				    while ((lunghezza = bis.read(buffer)) > 0){
				    	zipOutputStream.write(buffer, 0, lunghezza);
				    }
				    
				    bos.close();
				    bis.close();
				    res.close();
				    zipOutputStream.finish();
				    zipOutputStream.close();
					//Fine Zip
					
				}
				else{
					context.getResponse().setContentType( "application/xls" );
				    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + fileName + "\";" ); 
					context.getResponse().getOutputStream().write(bos.toByteArray());
				}
				
			} 
		    catch (IOException ioe) {
				ioe.printStackTrace();
			}
		    catch(WriteException we){
		    	we.printStackTrace();
		    }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				this.freeConnection(context, db);
			}
		
		
		return "-none-";
	}
	
	//2011-01-10
	private String getDateDDMMYYYY(String Sdate){
		
		String anno = "-"+Sdate.substring(0,4);
		String mese = "-"+Sdate.substring(5,7);
		String giorno = Sdate.substring(8,10);
		
		return giorno+mese+anno; 
	}
	
	
	
	
}