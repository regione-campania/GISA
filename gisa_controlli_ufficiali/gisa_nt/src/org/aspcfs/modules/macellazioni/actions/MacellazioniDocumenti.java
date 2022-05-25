/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioni.actions;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioni.base.Art17;
import org.aspcfs.modules.macellazioni.base.Art17ErrataCorrige;
import org.aspcfs.modules.macellazioni.base.Capo;
import org.aspcfs.modules.macellazioni.utils.PdfTool;
import org.aspcfs.modules.macellazioni.utils.Stats;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.registrotrasgressori.base.Trasgressione;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.stabilimenti.base.OrganizationAddress;
import org.aspcfs.modules.stabilimenti.base.OrganizationAddressList;
import org.aspcfs.utils.web.LookupList;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.daffodilwoods.daffodildb.server.sql99.dcl.sqlconnectionstatement.connectionname;
import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.newvaluescorrelationname;
import com.darkhorseventures.framework.actions.ActionContext;

public final class MacellazioniDocumenti extends CFSModule
{
	
	private static final SimpleDateFormat sdf  = new SimpleDateFormat( "dd/MM/yyyy" );
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
	ConfigTipo configTipo = null;
	
	public String executeCommandDefault(ActionContext context)
	{		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		return executeCommandToRegistroMacellazioni(context);
	}

	public String executeCommandToRegistroMacellazioni(ActionContext context)
	{		
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			//Recupero date di macellazione per riempire la combo
			ArrayList<String> listaDateMacellazione = null;
			
			//	listaDateMacellazione = Capo.loadDateMacellazioneByStabilimento( context.getParameter( "orgId" ), db );
			listaDateMacellazione = Capo.loadDateMacellazioneByStabilimentoNoPregresse( context.getParameter( "orgId" ), db );
			context.getRequest().setAttribute( "listaDateMacellazione", listaDateMacellazione );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "ToRegistroMacellazioniOK";
	}
	
	public String executeCommandRegistroMacellazioni(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );

			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			String data = context.getParameter( "comboDateMacellazione" );
			//SEDUTA DI MACELLAZIONE
			String sedutaMacellazione = context.getParameter( "comboSedutaMacellazione" );
			//
			
			
			
			
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return executeCommandToRegistroMacellazioni(context);
			}
			String anteMacellazione = context.getRequest().getParameter("ante_macellazione");
			
			//R.M: in caso di mancanza di esito BSE, il capo come non macellato deve risultare nel registro.
			String notEsitoBSE = context.getRequest().getParameter("notEsitoBSE");
			
			String suffissoTabella = "";
			String select = "SELECT * FROM m_registro_macellazioni" + suffissoTabella + " WHERE id_macello = ? AND (( data_macellazione = ? AND seduta_macellazione = ? ) OR data_macellazione is null )";
			
			
			/*if (anteMacellazione != null && anteMacellazione.equalsIgnoreCase("on"))
			{
				select += " OR data_macellazione is null )" ;
			}
			if (notEsitoBSE != null && notEsitoBSE.equalsIgnoreCase("on"))
			{
				select += " OR data_macellazione is null AND stato_macellazione ilike ? )" ;
			}7
			else
			{
				select += ")" ;
			}*/
			
				
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
			
			stat.setInt( 3, Integer.parseInt(sedutaMacellazione) );
			
			/*if (notEsitoBSE != null && notEsitoBSE.equalsIgnoreCase("on"))
			{
				stat.setString(3, "%Mancanza Esito del Test BSE%" );
	 		}*/
		
			//System.out.println("Stampo query: "+stat.toString());
			ResultSet res = stat.executeQuery();
			ArrayList<Stats> elenco = new ArrayList<Stats>();
			Stats header = new Stats();
			float[] sizes = { 0.1f, 0.075f, 0.1f, 0.12f, 0.12f, 0.1f, 0.13f, 0.06f, 0.075f, 0.10f, 0.1f, 0.18f };
			
				header.add( "PrgBov" );
			header.add( "Mod. 4" );
			header.add( "Data Arrivo" );
			header.add( "Specie" );		
			header.add( "Cod. Allev." );
			header.add( "Matricola" );
			header.add( "Data Nascita" );
			header.add( "Data Macellazione" );
			header.add( "Esito Visita P.M." );
			header.add( "Destinatario Carni" );
			header.add( "Dest. Carcassa" );
			header.add( "Cat. Rischio BSE" );
			
			while( res.next() )
			{
				Stats temp = new Stats();
				temp.add( res.getString( "progressivo" ) );

				temp.add( res.getString( "mod4" ) );
				temp.add( sdf.format( res.getTimestamp( "data_arrivo" ) ) );
				temp.add( res.getString( "specie" ) );
				
				temp.add( res.getString( "codice_azienda_provenienza" ) );
				temp.add( res.getString( "matricola" ) + "\n " );
				//temp.add( res.getString( "sesso" ) );
				if( res.getTimestamp( "data_nascita" ) != null ){
					temp.add( sdf.format( res.getTimestamp( "data_nascita" ) ) );
				}
				else{
					temp.add("");
				}
				if( res.getTimestamp( "data_macellazione" ) != null ){
					temp.add( sdf.format( res.getTimestamp( "data_macellazione" ) ) );
				}
				else{
					temp.add("");
				}
				temp.add( res.getString( "esito_vpm" ) );
				temp.add( res.getString( "destinatario_carni" ) );
				//if (anteMacellazione.equalsIgnoreCase("on"))
				//{
					temp.add( res.getString( "destinazione_carcassa" ) );
				//}
				temp.add( res.getString( "categoria_rischio" ) );
				elenco.add( temp );
			}
			if( elenco.size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun capo macellato il " + data );
				return executeCommandToRegistroMacellazioni(context);
			}
			
			PdfTool.printRegistroMacellazioni( context, org.getOrgId(), org.getName(), data, elenco, header, sizes );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "-none-";
	}

	public String executeCommandToArt17(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
	
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			//Recupero date di macellazione per riempire la combo
		//	ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneByStabilimento( context.getParameter( "orgId" ), db );
			ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneByStabilimentoNoPregresse( context.getParameter( "orgId" ), db );
			context.getRequest().setAttribute( "listaDateMacellazione", listaDateMacellazione );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "ToArt17OK";
	}
	
	
	public String executeCommandToMod10(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			//Recupero date di macellazione per riempire la combo
			//ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneByStabilimento( context.getParameter( "orgId" ), db );
			ArrayList<String> listaDateMacellazione = Capo.loadDateMacellazioneByStabilimentoTamponi( context.getParameter( "orgId" ), db );
			context.getRequest().setAttribute( "listaDateMacellazione", listaDateMacellazione );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "ToMod10OK";
	}
	
	public String executeCommandEsercentiArt17(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		getConfigTipo(context);
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			String data = context.getParameter( "comboDateMacellazione" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return executeCommandToArt17(context);
			}
			
			String suffissoVista = configTipo.getSuffissoViste();
			String select = "SELECT * FROM m_esercenti_macellazioni" + suffissoVista + " WHERE id_macello = ? AND data_macellazione = ?";
			PreparedStatement stat = db.prepareStatement( select );
					
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
					
			ResultSet		res		= stat.executeQuery();
			RowSetDynaClass	rsdc	= new RowSetDynaClass( res );
			
			if( rsdc.getRows().size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun capo macellato il " + data );
				return executeCommandToArt17(context);
			}
			
			TableFacade tf = TableFacadeFactory.createTableFacade( "2", context.getRequest() );
			tf.setItems( rsdc.getRows() );
			tf.setColumnProperties( "esercente", "numero_capi"  );
			tf.setStateAttr("restore");

			Limit limit = tf.getLimit();
			if( limit.isExported() )
			{
				tf.render();
				return null;
			}
			else
			{
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("esercente");
				cg.setTitle( "Destinatario Carni" );
				cg.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String ret = "";
		        				String temp		= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
		        				String id_eserc	= (String) (new HtmlCellEditor()).getValue(item, "id_esercente", rowCount);
		        				String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
		        				String data		= (String) (new HtmlCellEditor()).getValue(item, "data_macellazione", rowCount);
		        				try
		        				{
									data = sdf.format( sdf2.parse( data ) );
								}
		        				catch (ParseException e)
		        				{
									e.printStackTrace();
								}
		        				//COSA ZOZZA APPARATA
		        				temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
//		        				if(id_eserc.equals("-999")){
//		        						
//		        						
//										ret = "<a target=\"_blank\" href=\"GestioneDocumenti.do?command=GeneraPDFMacelli&tipo=Macelli_17&esercente=" + id_eserc + "&nomeEsercente=" + temp.replaceAll("&amp;", "u38") +"&orgId=" + id_mac + "&data=" 
//										+ data + "\">" + temp + "</a></div>";
//		        				}
//		        				else{
//		        					ret = "<a target=\"_blank\" href=\"GestioneDocumenti.do?command=GeneraPDFMacelli&tipo=Macelli_17&esercente=" + id_eserc + "&orgId=" + id_mac + "&data=" 
//		        							+ data + "\">" + temp + "</a></div>";
//		        				}
		        				
//		        				if(id_eserc.equals("-999")){
//	        						
//	        						
//									ret = "<a target=\"_blank\" href=\"MacellazioniDocumenti.do?command=Art17&esercente=" + id_eserc + "&nomeEsercente=" + temp.replaceAll("&amp;", "u38") +"&orgId=" + id_mac + "&data=" 
//									+ data + "\">" + temp + "</a></div>";
//	        				}
//	        				else{
//	        					ret = "<a target=\"_blank\" href=\"MacellazioniDocumenti.do?command=Art17&esercente=" + id_eserc + "&orgId=" + id_mac + "&data=" 
//	        							+ data + "\">" + temp + "</a></div>";
//	        				}
		        		// aggiunti link nuova gestione		
		        				if(id_eserc.equals("-999")){
        						
        						
								ret = ret+ "<a target=\"_blank\" href=\"GestioneDocumenti.do?command=GeneraPDFMacelli&tipo=Macelli_17&esercente=" + id_eserc + "&nomeEsercente=" + temp.replaceAll("&amp;", "u38") +"&orgId=" + id_mac + "&data=" 
								+ data + "\">" +  temp + " </a></div>";
        				}
        				else{
        					ret = ret + "<a target=\"_blank\" href=\"GestioneDocumenti.do?command=GeneraPDFMacelli&tipo=Macelli_17&esercente=" + id_eserc + "&orgId=" + id_mac + "&data=" 
        							+ data + "\">"+ temp + " </a></div>";
        				}
		        		//fine nuova gestione		
		        				return ret;
		        			}
		        		}
		        
		        	);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("numero_capi");
				cg.setTitle( "Capi Macellati" );
				
				cg.setFilterable( false );
			}

			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
				
		return "EsercentiArt17OK";
	}

	public String executeCommandSpeditoriMortiStalla(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			String data = context.getParameter( "data" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToArt17OK";
			}
			
			String select = "SELECT * FROM m_speditori_morti_stalla WHERE id_macello = ? AND data = ?";
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
			ResultSet		res		= stat.executeQuery();
			RowSetDynaClass	rsdc	= new RowSetDynaClass( res );
			
			if( rsdc.getRows().size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun capo morto in stalla il " + data );
				return "ToMortiStallaOK";
			}
			
			TableFacade tf = TableFacadeFactory.createTableFacade( "2", context.getRequest() );
			tf.setItems( rsdc.getRows() );
			tf.setColumnProperties( "speditore", "numero_capi"  );
			tf.setStateAttr("restore");

			Limit limit = tf.getLimit();
			if( limit.isExported() )
			{
				tf.render();
				return null;
			}
			else
			{
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("speditore");
				cg.setTitle( "Speditore" );
				cg.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String temp		= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
		        				String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
		        				String data		= (String) (new HtmlCellEditor()).getValue(item, "data", rowCount);
		        				try
		        				{
									data = sdf.format( sdf2.parse( data ) );
								}
		        				catch (ParseException e)
		        				{
									e.printStackTrace();
								}
		        				
		        				temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
		        				return "<a href=\"MacellazioniDocumenti.do?command=MortiStalla&speditore=" + temp + "&orgId=" + id_mac + "&data=" 
		        					+ data + "\">" + temp + "</a></div>";
		        			}
		        		}
		        
		        	);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("numero_capi");
				cg.setTitle( "Capi Macellati" );
				cg.setFilterable( false );
			}

			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "MortiStallaOK"; //"EsercentiArt17OK";
	}

	
	public String executeCommandToMortiStalla(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "ToMortiStallaOK";
	}

	public String executeCommandToBSE(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "ToBSEOK";
	}
	
	public String executeCommandBSE(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			
			LookupList aslList	= new LookupList( db, "lookup_site_id" );

			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			String data = context.getParameter( "data" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToBSEOK";
			}
			
			String select = "SELECT * FROM m_modulo_bse WHERE id_macello = ? AND data_prelievo = ?";
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
			ResultSet res = stat.executeQuery();
			
			ArrayList<Stats> elenco = new ArrayList<Stats>();
			float[] sizes = { 0.03f, 0.075f, 0.1f, 0.16f, 0.12f, 0.08f, 0.03f, 0.075f };
			
			Stats header = new Stats();
			header.add( "Num. Prog." );
			header.add( "Codice identificativo animale" );
			header.add( "Codice Azienda di Origine" );
			header.add( "Motivo del prelievo" );
			header.add( "Specie" );
			header.add( "Data di nascita" );
			header.add( "Sesso" );
			header.add( "Comune ultima azienda di provenienza" );
			
			for( int i = 1; res.next(); i++ )
			{
				Stats temp = new Stats();
				String motivo = res.getString( "motivo_prelievo" );
				if( res.getBoolean( "quattro_anni" ) )
				{
					if( motivo != null )
					{
						motivo += "\nEta' superiore a 48 mesi";
					}
					else
					{
						motivo = "Eta' superiore a 48 mesi";
					}
				}
				temp.add( i + "" );
				temp.add( res.getString( "matricola" ) );
				temp.add( res.getString( "codice_azienda" ) );
				temp.add( motivo );
				temp.add( res.getString( "specie" ) );
				temp.add( sdf.format( res.getTimestamp( "data_nascita" ) ) );
				temp.add( res.getString( "sesso" ) );
				temp.add( res.getString( "comune" ) );
				elenco.add( temp );
			}
			
			if( elenco.size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun prelievo BSE effettuato il " + data );
				return "ToBSEOK";
			}
			
			PdfTool.printModuloBSE( context, org, aslList.getSelectedValue(org.getSiteId()), data, elenco, header, sizes );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "ToBSEOK";
	}

	public String executeCommandToAbbattimento(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "ToAbbattimentoOK";
	}
	
	public String executeCommandSpeditoriAbbattimento(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			String data = context.getParameter( "data" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToAbbattimentoOK";
			}
			
			String select = "SELECT * FROM m_speditori_abbattimento WHERE id_macello = ? AND data_abbattimento = ?";
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
			ResultSet		res		= stat.executeQuery();
			RowSetDynaClass	rsdc	= new RowSetDynaClass( res );
			
			if( rsdc.getRows().size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun capo abbattuto il " + data );
				return "ToAbbattimentoOK";
			}
			
			TableFacade tf = TableFacadeFactory.createTableFacade( "2", context.getRequest() );
			tf.setItems( rsdc.getRows() );
			tf.setColumnProperties( "speditore", "numero_capi"  );
			tf.setStateAttr("restore");

			Limit limit = tf.getLimit();
			if( limit.isExported() )
			{
				tf.render();
				return null;
			}
			else
			{
				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("speditore");
				cg.setTitle( "Speditore" );
				cg.getCellRenderer().setCellEditor( 
		        		new CellEditor()
		        		{	
		        			public Object getValue(Object item, String property, int rowCount)
		        			{
		        				String temp		= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
		        				String id_mac	= (String) (new HtmlCellEditor()).getValue(item, "id_macello", rowCount);
		        				String data		= (String) (new HtmlCellEditor()).getValue(item, "data_abbattimento", rowCount);
		        				try
		        				{
									data = sdf.format( sdf2.parse( data ) );
								}
		        				catch (ParseException e)
		        				{
									e.printStackTrace();
								}
		        				
		        				temp = (temp == null || "".equals(temp.trim())) ? ("-") : (temp);
		        				return "<a href=\"MacellazioniDocumenti.do?command=Abbattimento&speditore=" + temp + "&orgId=" + id_mac 
		        					+ "&data=" + data + "\">" + temp + "</a></div>";
		        			}
		        		}
		        
		        	);
				
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("numero_capi");
				cg.setTitle( "Capi Macellati" );
				cg.setFilterable( false );
			}

			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "SpeditoriAbbattimentoOK";
	}
	
	public String executeCommandAbbattimento(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			
			LookupList aslList	= new LookupList( db, "lookup_site_id" );
			
			String speditore = context.getParameter("speditore");
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			
			String data = context.getParameter( "data" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToAbbattimentoOK";
			}
			
			String select = "SELECT * FROM m_modulo_abbattimento WHERE id_macello = ? AND data_abbattimento = ? AND upper( speditore ) = upper( ? )";
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
			stat.setString( 3, speditore );
			ResultSet res = stat.executeQuery();
			
			ArrayList<Stats> elenco = new ArrayList<Stats>();
			float[] sizes = { 0.03f, 0.075f, 0.045f, 0.10f, 0.15f, 0.15f };
			
			Stats header = new Stats();
			header.add( "n." );
			header.add( "SPECIE" );
//			header.add( "CATEGORIA" );
			header.add( "SESSO" );
			header.add( "CONTRASSEGNO di identificazione" );
			header.add( "DESTINAZIONI\ndelle carni" );
			header.add( "Motivo Abbattimento" );
			
			for( int i = 1; res.next(); i++ )
			{
				Stats temp = new Stats();
				
				temp.add( i + "" );
				temp.add( res.getString( "specie" ) );
//				temp.add( "che ci metto qui???" );
				temp.add( res.getString( "sesso" ) );
				temp.add( res.getString( "matricola" ) );
				temp.add( res.getString( "destinazione" ) );
				temp.add( res.getString( "motivo" ) );
				elenco.add( temp );
			}
			
			if( elenco.size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun capo abbattuto il " + data );
				return "ToAbbattimentoOK";
			}
			
			PdfTool.printModuloAbbattimento( context, org, aslList.getSelectedValue(org.getSiteId()), speditore, data, elenco, header, sizes );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		return "ToAbbattimentoOK";
	}
	

	public String executeCommandArt17(ActionContext context)
	{
		getConfigTipo(context);
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			
			LookupList aslList	= new LookupList( db, "lookup_site_id" );
			
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			int id_esercente = Integer.parseInt( context.getParameter( "esercente" ) );
			
			String data = context.getParameter( "data" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToArt17OK";
			}
			
			String nomeEsercente = context.getParameter("nomeEsercente") != null ? context.getParameter("nomeEsercente") : "";
			nomeEsercente = nomeEsercente.replace("u38", "&");
			PreparedStatement stat = null;
			String select = "";
			String suffissoVista = configTipo.getSuffissoViste();
			
			if(id_esercente == -999){
				select = "SELECT * FROM m_modulo_art17"+ suffissoVista + " WHERE id_macello = ? AND data_macellazione = ? AND esercente = ?";
				stat = db.prepareStatement( select );
				stat.setInt( 1, org.getOrgId() );
				stat.setTimestamp( 2, d );
				stat.setString( 3, nomeEsercente );
			}
			else{
				select = "SELECT * FROM m_modulo_art17"+ suffissoVista + " WHERE id_macello = ? AND data_macellazione = ? AND id_esercente = ?";
				stat = db.prepareStatement( select );
				stat.setInt( 1, org.getOrgId() );
				stat.setTimestamp( 2, d );
				stat.setInt( 3, id_esercente );
			}
			ResultSet res = stat.executeQuery();
			
			ArrayList<Stats> elenco = new ArrayList<Stats>();
				
			Stats header = new Stats();
				header.add( "Auricolare" );
				header.add( "Mezzene" );
			//header.add( "Eta' in mesi" );
				header.add( "Data di nascita" );
			header.add( "Specie" );
			header.add( "Categoria" );
				header.add( "Sesso" );
			header.add( "Rif Mod 4" );
			header.add( "Esito Visita" );
			
				stat = db.prepareStatement( "UPDATE m_capi SET articolo17 = true WHERE cd_matricola = ? and trashed_date is null " ); 
			
			while( res.next() )
			{
				Stats temp = new Stats();
				
				String sAuricolare = null;
					sAuricolare = res.getString( "matricola" );
				
				if (sAuricolare==null)
					sAuricolare=" ";
				
				stat.setString(1, sAuricolare);
				stat.executeUpdate();
				
				temp.add( sAuricolare ); //res.getString( "matricola" ) );
					temp.add( res.getString( "num_mezzene" ) );
				//temp.add( res.getString( "mesi" ) );
					temp.add( res.getString( "data_nascita" ) );
				temp.add( res.getString( "specie" ) );
				temp.add( res.getString( "categoria" ) );
				temp.add( res.getString( "sesso" ) );
				temp.add( res.getString( "mod4" ) );
				temp.add( res.getString( "esito" ) );
				elenco.add( temp );
			}
			
			if( elenco.size() == 0 )
			{
					context.getRequest().setAttribute( "messaggio", "Nessun capo macellato il " + data );
				return "ToArt17OK";
			}

			Art17 art17 = Art17.find( org.getOrgId(), id_esercente, nomeEsercente, d, db);
			Timestamp oggi = new Timestamp( System.currentTimeMillis() );
			if( art17 == null )
			{
				art17 = new Art17();
				art17.setData_modello( d );
				art17.setId_esercente( id_esercente );
				art17.setNome_esercente( nomeEsercente );
				art17.setId_macello( org.getOrgId() );
				art17.setUtente_prima_generazione( getActualUserId(context) );
				art17.setNum_generazioni( 0 );
				art17.setNum_capi_prima_generazione( elenco.size() );
				art17.setData_prima_generazione( oggi );
			}
			
			art17.setUtente_ultima_generazione( getActualUserId(context) );
			art17.setNum_generazioni( art17.getNum_generazioni() + 1 );
			art17.setNum_capi_ultima_generazione( elenco.size() );
			art17.setData_ultima_generazione( oggi );
			
			if( art17.getId() <= 0 )
			{
				art17.store( db );
			}
			else
			{
				art17.update( db );
			}
			
			art17 = Art17.find( org.getOrgId(), id_esercente, nomeEsercente, d, db);
			
			Organization esercente = null;
			if(id_esercente == -999){
				esercente = new Organization();
				esercente.setOrgId(-999);
				esercente.setName(nomeEsercente);
			}
			else{
				esercente = new Organization( db, id_esercente );
			}
			
			//For the image
		    String reportDir = getWebInfPath(context, "reports");

		  
				float[] sizes = { 0.1f, 0.1f, 0.075f, 0.075f, 0.075f, 0.10f, 0.15f, 0.15f };
				PdfTool.printModuloArt17( context, org, aslList.getSelectedValue(org.getSiteId()), esercente, art17, data, elenco, header, sizes, reportDir, db );
			
		    
			
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "-none-";
	}
	
	public String executeCommandMortiStalla(ActionContext context)
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-macellazioni-view"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = this.getConnection( context );
			
			LookupList aslList	= new LookupList( db, "lookup_site_id" );
			
			Organization org = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", org );
			String speditore = context.getParameter( "speditore" );
			
			String data = context.getParameter( "data" );
			Timestamp d = null;
			try
			{
				d = new Timestamp( sdf.parse( data ).getTime() );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToMortiStallaOK";
			}
			
			String select = "SELECT * FROM m_modulo_morti_stalla WHERE id_macello = ? AND data = ? AND speditore = ?";
			PreparedStatement stat = db.prepareStatement( select );
			stat.setInt( 1, org.getOrgId() );
			stat.setTimestamp( 2, d );
			stat.setString( 3, speditore );
			ResultSet res = stat.executeQuery();
			
			ArrayList<Stats> elenco = new ArrayList<Stats>();
			float[] sizes = { 0.075f, 0.1f, 0.045f, 0.10f, 0.15f, 0.15f };
			
			Stats header = new Stats();
			header.add( "Auricolare" );
			header.add( "Eta' in mesi" );
			header.add( "Sesso" );
			header.add( "Rif Mod 4" );
			header.add( "Esito Visita" );
			header.add( "Impianto di Destinazione" );
			
			while( res.next() )
			{
				Stats temp = new Stats();
				
				temp.add( res.getString( "matricola" ) );
				temp.add( res.getString( "mesi" ) );
				temp.add( res.getString( "sesso" ) );
				temp.add( res.getString( "mod4" ) );
				temp.add( res.getString( "esito" ) );
				temp.add( res.getString( "impianto" ) );
				elenco.add( temp );
			}
			
			if( elenco.size() == 0 )
			{
				context.getRequest().setAttribute( "messaggio", "Nessun capo morto in stalla il " + data );
				return "ToArt17OK";
			}

			Timestamp oggi = new Timestamp( System.currentTimeMillis() );
	
			
			PdfTool.printModuloMortiStalla( context, org, aslList.getSelectedValue(org.getSiteId()), speditore, data, elenco, header, sizes );
			
		}
		catch (Exception e1)
		{
			context.getRequest().setAttribute("Error", e1);
			e1.printStackTrace();
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "ToMortiStallaOK";
	}
	
	public String executeCommandViewModuleErrataCorrigeArt17(ActionContext context){
		
		String idCapo = context.getRequest().getParameter("idCapo");
		int orgId = -1;
		String tipoMacello = context.getRequest().getParameter("tipoMacello");
		if (tipoMacello == null)
			tipoMacello = (String) context.getRequest().getAttribute("tipoMacello");
		
		Capo c = null;
		
		
		String orgIdString = context.getRequest().getParameter("orgId");
		if (orgIdString == null)
			 orgIdString = (String) context.getRequest().getAttribute("orgId");
		
		if (orgIdString!=null && !orgIdString.equals(""))
			orgId = Integer.parseInt(orgIdString);
		
		Connection db = null;
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization (db, orgId);
			context.getRequest().setAttribute("OrgDetails", org);
			
			
				c = Capo.load(idCapo, db);
			
			context.getRequest().setAttribute("Capo", c);
			
			if (c.getErrata_corrige_generati()>=10){
				context.getRequest().setAttribute("Error", "Numero massimo moduli Errata Corrige generati raggiunto per questo capo.");
				return "errorPage";
				}
			
			Organization esercente = null;
			if (c.getDestinatario_1_id()>0)
				esercente = new Organization (db, c.getDestinatario_1_id());
			context.getRequest().setAttribute("esercente", esercente);
			
			LookupList aslList	= new LookupList( db, "lookup_site_id" );
			context.getRequest().setAttribute("aslList", aslList);
			
			LookupList specieList	= new LookupList( db, "m_lookup_specie" );
			specieList.removeElementByValue("Ovini");
			specieList.removeElementByValue("Caprini");
		//	String specieAttuale = specieList.getSelectedValue(c.getCd_specie());
			//specieList.removeElementByValue(specieAttuale);
			context.getRequest().setAttribute("specieList", specieList);
			
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = context.getIpAddress();
			String user_name = user.getUserRecord().getUsername();
			context.getRequest().setAttribute("userIp", ip);
			context.getRequest().setAttribute("userName", user_name);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date = new Date();
			context.getRequest().setAttribute("timeNow",dateFormat.format(date));
			
			Art17 art17 = Art17.find( org.getOrgId(), c.getDestinatario_1_id(), c.getDestinatario_1_nome(),c.getDataSessioneMacellazione(),  db);
			context.getRequest().setAttribute("art17", art17);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		if (context.getRequest().getAttribute("ErrataCorrige")!=null)
			return "art17erratacorrigeStampaOk";
		
		return "art17erratacorrigeOk";
		
	}
	
	public String executeCommandSalvaModuleErrataCorrigeArt17(ActionContext context) throws SQLException{
		
		Art17ErrataCorrige ErrataCorrige = new Art17ErrataCorrige(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		Connection db = null;
		int result1 = -1;
		int result2 = -1;
		try
		{
			db = this.getConnection( context );
			
			db.setAutoCommit(false);
			
			ErrataCorrige.setIdUtente(user.getUserId());
			ErrataCorrige.setIpUtente(ip);
			ErrataCorrige.setNomeUtente(user.getUsername());
			result1 = ErrataCorrige.insert(db);
			result2 = ErrataCorrige.aggiornaCapo(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			db.rollback();
			e.printStackTrace();
			String errore = "Errore su ";
			if (result1==-1)
				errore = errore + "inserimento modulo Errata Corrige.";
			if (result2==-1)
				errore = errore + "modifica capo. Modulo errata corrige NON inserito.";
				
			context.getRequest().setAttribute("Error", errore);
			return "errorPage";
		}
		finally
		{
			db.setAutoCommit(true);
			db.close();
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("definitivoDocumentale", "true");
		context.getRequest().setAttribute("tipoMacello", "1");
		context.getRequest().setAttribute("orgId", context.getRequest().getParameter("idMacello"));
		context.getRequest().setAttribute("idCapo", context.getRequest().getParameter("idCapo"));
		
		context.getRequest().setAttribute("ErrataCorrige", ErrataCorrige);
		context.getRequest().setAttribute("idErrataCorrige", ErrataCorrige.getId());
		
		// return executeCommandViewModuleErrataCorrigeArt17(context);
		return "art17erratacorrigeSalvaOk";
	}
	
public String executeCommandViewListaModuleErrataCorrigeArt17(ActionContext context){
		
		String idCapo = context.getRequest().getParameter("idCapo");
		int orgId = -1;
		String tipoMacello = context.getRequest().getParameter("tipoMacello");
		if (tipoMacello == null)
			tipoMacello = (String) context.getRequest().getAttribute("tipoMacello");
		
		Capo c = null;
		
		
		String orgIdString = context.getRequest().getParameter("orgId");
		if (orgIdString == null)
			 orgIdString = (String) context.getRequest().getAttribute("orgId");
		
		if (orgIdString!=null && !orgIdString.equals(""))
			orgId = Integer.parseInt(orgIdString);
		
		Connection db = null;
		try
		{
			db = this.getConnection( context );
			Organization org = new Organization (db, orgId);
			context.getRequest().setAttribute("OrgDetails", org);
			
			
				c = Capo.load(idCapo, db);
			
			context.getRequest().setAttribute("Capo", c);
			
			Art17ErrataCorrige ec = new Art17ErrataCorrige();
			ec.setIdMacello(orgId);
			ec.setIdCapo(idCapo);
			
			Vector ecList = new Vector();
			ecList = ec.load(db);
			context.getRequest().setAttribute("ecList", ecList);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
			return "art17erratacorrigeListaOk";
		}

public String executeCommandViewErrataCorrigeArt17(ActionContext context){
	
	String idErrataCorrige = context.getRequest().getParameter("idErrataCorrige");
	String idCapo = context.getRequest().getParameter("idCapo");
	int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
	
	Connection db = null;
	Capo c = null;
	try
	{
		db = this.getConnection( context );
		Organization org = new Organization (db, orgId);
		context.getRequest().setAttribute("OrgDetails", org);
		
			c = Capo.load(idCapo, db);
		
		context.getRequest().setAttribute("Capo", c);
		
		Organization esercente = null;
		if (c.getDestinatario_1_id()>0)
			esercente = new Organization (db, c.getDestinatario_1_id());
		context.getRequest().setAttribute("esercente", esercente);
		
		LookupList aslList	= new LookupList( db, "lookup_site_id" );
		context.getRequest().setAttribute("aslList", aslList);
		
		LookupList specieList	= new LookupList( db, "m_lookup_specie" );
		specieList.removeElementByValue("Ovini");
		specieList.removeElementByValue("Caprini");
	//	String specieAttuale = specieList.getSelectedValue(c.getCd_specie());
		//specieList.removeElementByValue(specieAttuale);
		context.getRequest().setAttribute("specieList", specieList);
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		String user_name = user.getUserRecord().getUsername();
		context.getRequest().setAttribute("userIp", ip);
		context.getRequest().setAttribute("userName", user_name);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		context.getRequest().setAttribute("timeNow",dateFormat.format(date));
		
		Art17 art17 = Art17.find( org.getOrgId(), c.getDestinatario_1_id(), c.getDestinatario_1_nome(),c.getDataSessioneMacellazione(),  db);
		context.getRequest().setAttribute("art17", art17);
		
		int id = Integer.parseInt(idErrataCorrige);
		Art17ErrataCorrige ec = new Art17ErrataCorrige(db, id);
		context.getRequest().setAttribute("ErrataCorrige", ec);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		this.freeConnection(context, db);
	}
		return "art17erratacorrigeStampaOk";
}


	private void getConfigTipo(ActionContext context)
	{
		if(context.getSession().getAttribute("configTipo")!=null)
			configTipo = (ConfigTipo)context.getSession().getAttribute("configTipo");
	}
}
