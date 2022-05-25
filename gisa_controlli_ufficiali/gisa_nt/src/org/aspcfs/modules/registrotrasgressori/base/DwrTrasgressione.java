/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.base;

	import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.Permission;
import org.aspcfs.modules.admin.base.PermissionList;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.RolePermissionList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.darkhorseventures.database.ConnectionElement;

	public class DwrTrasgressione { 


		
		public static void updateTrasgressione(int idSanzione,int anno,String nomeColonna,String value) throws SQLException
		{
			
			
			Connection db = null ;
			WebContext ctx = WebContextFactory.get();
			ConnectionElement ce = (ConnectionElement) ctx.getSession().getAttribute("ConnectionElement");
			SystemStatus systemStatus = (SystemStatus) ((Hashtable) ctx.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
			HttpServletRequest req = ctx.getHttpServletRequest();
			
			UserBean user = (UserBean) ctx.getSession().getAttribute("User");
			int userId = user.getUserId();
			
			try {
				db = GestoreConnessioni.getConnection();
				String sqlVer = "select "+nomeColonna+" from registro_trasgressori_values where id_sanzione = ? and anno = ? and trashed_date is null";
				PreparedStatement pst = null ;
				ResultSet rs = null ;
				pst = db.prepareStatement(sqlVer);
				pst.setInt(1, idSanzione);
				pst.setInt(2, anno);
				rs = pst.executeQuery();

				if (rs.next())
				{
					String oldValue = String.valueOf(rs.getObject(1));
					ResultSetMetaData rsmd=rs.getMetaData();
					String tipo = rsmd.getColumnTypeName(1);					
					String nome_campo_edit = nomeColonna ;
					String update = "update registro_trasgressori_values set "+nome_campo_edit +" =? where id_sanzione = ? and anno = ? and trashed_date is null ";
					PreparedStatement pst2 = db.prepareStatement(update);
					pst2 = setValore(pst2, tipo, value);
					pst2.setInt(2, idSanzione);
					pst2.setInt(3, anno);
					int esito = pst2.executeUpdate(); 
					if (esito>-1)
						updateTrasgressioneStorico(db, idSanzione, anno, nome_campo_edit, oldValue, value, userId);
				}
				
			} catch (SQLException e) {
				throw e ;
			}
			catch (NumberFormatException g) {
				throw g ;
			}	
			finally
			{
				GestoreConnessioni.freeConnection(db);
			}
		}
		
		
		private static PreparedStatement setValore(PreparedStatement pst, String tipo, String value) {
			if (tipo.contains("bool")) {
				try {
					pst.setBoolean(1, Boolean.parseBoolean(value));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (tipo.contains("float")) {
				try {
					if (value.equals(""))
						pst.setObject(1, null);
					else
						pst.setFloat(1, Float.parseFloat(value));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (tipo.contains("int")) {
				try {
					if (value.equals(""))
						pst.setObject(1, null);
					else
						pst.setInt(1, Integer.parseInt(value));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (tipo.contains("text")) {
				try {
					pst.setString(1, value);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (tipo.contains("timestamp")) {
				try {
					if (value.equals(""))
						pst.setObject(1, null);
					else
						pst.setTimestamp(1,DatabaseUtils.parseDateToTimestamp(value));
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					pst.setObject(1, value);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return pst;
		}
		
		public static void updateTrasgressioneStorico(Connection db, int idSanzione,int anno,String nomeColonna,String oldValue, String newValue, int userId) throws SQLException
		{
			
			try {
					String insert = "insert into registro_trasgressori_values_storico (id_sanzione, anno, nome_campo, vecchio_valore, nuovo_valore, utente_modifica, data_modifica) values (?, ?, ?, ?, ?, ?, now())";
					PreparedStatement pst2 = db.prepareStatement(insert);
					int i = 0;
					pst2.setInt(++i, idSanzione);
					pst2.setInt(++i, anno);
					pst2.setString(++i, nomeColonna);
					pst2.setString(++i, oldValue);
					pst2.setString(++i, newValue);
					pst2.setInt(++i, userId);
					pst2.execute(); 
				
			} catch (SQLException e) {
				throw e ;
			}
			catch (NumberFormatException g) {
				throw g ;
			}	
		}
		
		/*
		 * public static Timestamp controlloDate(Connection db, int idSanzione,int anno,String nomeColonna, String newValue) throws SQLException
		{
			Timestamp dataAccertamento = null;
			Timestamp dataEmissione = null;
			Timestamp dataProtocollo = null;
			Timestamp dataValore = DatabaseUtils.parseDateToTimestamp(newValue);
			Timestamp dataReturn = null;
			try {
					String select = "select t.assigned_date as data_accertamento, r.data_prot_entrata, r.data_emissione from registro_trasgressioni_values r inner join ticket t on r.id_sanzione = t.ticketid where r.id_sanzione = ? and r.anno = ? and r.trashed_date is null";
					PreparedStatement pst2 = db.prepareStatement(select);
					int i = 0;
					pst2.setInt(++i, idSanzione);
					pst2.setInt(++i, anno);
					ResultSet rs = 	pst2.executeQuery();
					while (rs.next()){
						dataAccertamento = rs.getTimestamp("data_accertamento");
						dataEmissione = rs.getTimestamp("data_emissione");
						dataProtocollo = rs.getTimestamp("data_prot_entrata");
					}
					
					if (nomeColonna.equals("data_prot_entrata")){
						if (dataValore.before(dataAccertamento))
							dataReturn = dataValore;
						if (dataValore.after(dataEmissione))
							dataReturn = dataValore;
					}
					else if (nomeColonna.equals("data_emissione")){
						if (dataValore.before(dataAccertamento))
							dataReturn = dataValore;
						if (dataValore.before(dataProtocollo))
							dataReturn = dataValore;
					}
				
			} catch (SQLException e) {
				throw e ;
			}
			catch (NumberFormatException g) {
				throw g ;
			}	
			return dataReturn;
		}
		 * 
		 */
	}


