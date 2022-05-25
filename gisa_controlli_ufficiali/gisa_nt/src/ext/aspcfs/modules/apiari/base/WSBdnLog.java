/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.text.TabExpander;

import org.apache.log4j.Logger;
import org.aspcfs.utils.GestoreConnessioni;

import com.sun.jmx.snmp.Timestamp;
import com.sun.xml.internal.ws.wsdl.writer.document.ParamType;

public class WSBdnLog {

	Logger logger = Logger.getLogger(WSBdnLog.class);
	public static final int TYPE_WS_INSERT_ATTIVITA = 1 ;
	public static final int TYPE_WS_INSERT_API = 2 ;
	public static final int TYPE_WS_INSERT_PERSONA = 3 ;
	public static final int TYPE_WS_SEARCH_PERSONA = 4 ;
	public static final int TYPE_WS_SEARCH_AZIENDA = 5 ;
	public static final int TYPE_WS_DELETE_AZIENDA = 6 ;

	public static final int TYPE_WS_INSERT_VAR_DET = 6 ;
	public static final int TYPE_WS_INSERT_VAR_UBI = 7 ;
	public static final int TYPE_WS_INSERT_VAR_CEN = 8 ;
	
	public static final int TYPE_WS_DELETE_API = 9 ;
	public static final int TYPE_WS_UPDATE_MODALITA_API = 10 ;
	public static final int TYPE_WS_UPDATE_ATTIVITA = 11 ;
	public static final int TYPE_WS_UPDATE_AZIENDA = 12 ;
	public static final int TYPE_WS_UPDATE_APIARIO = 13 ;
	public static final int TYPE_WS_UPDATE_PERSONA = 14 ;
	public static final int TYPE_WS_INSERT_PERSONA_STORICO = 15 ;
	public static final int TYPE_WS_SEARCHBYPK_MOVIMENTAZIONE = 16;
	public static final int TYPE_WS_SEARCH_MOVIMENTAZIONE = 17;
	public static final int TYPE_WS_UPDATE_MOVIMENTAZIONE = 17;
	public static final int TYPE_WS_SEARCH_ATTIVITA = 19 ;



	private int id ;
	private int tipoOperazione ;
	private int idOggetto ;
	private String nomeTabella ;
	private String esito ;
	private String noteEsito ;
	private int idUtente ;
	private java.sql.Timestamp dataInvio ;
	private java.sql.Timestamp dataRisposta ;

	private int idVariazione  ; 
	private String tabellaVariazione="" ;
	private Object parametri ;
	private String nomerServizio ;




	public Object getParametri() {
		return parametri;
	}
	public void setParametri(Object parametri) {
		this.parametri = parametri;
	}
	public String getNomerServizio() {
		return nomerServizio;
	}
	public void setNomerServizio(String nomerServizio) {
		this.nomerServizio = nomerServizio;
	}
	public int getIdVariazione() {
		return idVariazione;
	}
	public void setIdVariazione(int idVariazione) {
		this.idVariazione = idVariazione;
	}
	public String getTabellaVariazione() {
		return tabellaVariazione;
	}
	public void setTabellaVariazione(String tabellaVariazione) {
		this.tabellaVariazione = tabellaVariazione;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(int tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public int getIdOggetto() {
		return idOggetto;
	}
	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getNoteEsito() {
		return noteEsito;
	}
	public void setNoteEsito(String noteEsito) {
		this.noteEsito = noteEsito;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}



	public java.sql.Timestamp getDataRisposta() {
		return dataRisposta;
	}
	public void setDataRisposta(java.sql.Timestamp dataRisposta) {
		this.dataRisposta = dataRisposta;
	}
	public java.sql.Timestamp getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(java.sql.Timestamp dataInvio) {
		this.dataInvio = dataInvio;
	}


	public String parameter() throws ClassNotFoundException, NoSuchMethodException, SecurityException
	{

		String toRet = "" ;

		if (parametri!=null)
		{
			Class obj =parametri.getClass();
			Field[] fieldList = obj.getDeclaredFields();
			Method metodo = null; 
			Method[] listMethods = obj.getDeclaredMethods();

			for (Field field : fieldList)
			{
				Object ritorno = null ;

				for (Method met : listMethods)
				{
					if (met.getName().equalsIgnoreCase("get" + field.getName()))
					{
						try {
							ritorno = (String) met.invoke(parametri, new Object[0]);
							toRet+=""+field.getName()+"="+ritorno+";";
						}
						catch (Exception ecc) { }
						
					}
				}




			}
		}
		return toRet ;

	}

	public boolean insertBdnLogWs() 
	{


		int i = 0 ;
		Connection db = null;
		String sql = "insert into bdn_ws_log (tipo_operazione,id_oggetto,tabella_riferimento,esito,note_esito,id_utente,data_operazione,data_risposta,id_variazione,tabella_variazione,nome_servizio,parametri)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?)" ;
		PreparedStatement pst = null ;
		try {
			db = GestoreConnessioni.getConnection();
			pst = db.prepareStatement(sql);

			pst.setInt(++i, tipoOperazione);
			pst.setInt(++i, idOggetto);
			pst.setString(++i, nomeTabella);
			pst.setString(++i, esito);
			pst.setString(++i, noteEsito);
			pst.setInt(++i, idUtente);
			pst.setTimestamp(++i, dataInvio);
			pst.setTimestamp(++i, dataRisposta);
			pst.setInt(++i, idVariazione);
			pst.setString(++i, tabellaVariazione);
			pst.setString(++i, nomerServizio);

			pst.setString(++i, parameter());


			pst.execute();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}


		return true ;
	}

	public boolean insertBdnLogWsErrore() 
	{


		int i = 0 ;
		Connection db = null;
		String sql = "insert into bdn_ws_log (tipo_operazione,id_oggetto,tabella_riferimento,esito,note_esito,id_utente,data_operazione,data_risposta,id_variazione,tabella_variazione,nome_servizio,parametri)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?)" ;
		PreparedStatement pst = null ;
		try {
			db = GestoreConnessioni.getConnection();
			pst = db.prepareStatement(sql);

			pst.setInt(++i, tipoOperazione);
			pst.setInt(++i, idOggetto);
			pst.setString(++i, nomeTabella);
			pst.setString(++i, esito);
			pst.setString(++i, noteEsito);
			pst.setInt(++i, idUtente);
			pst.setTimestamp(++i, dataInvio);
			pst.setTimestamp(++i, dataRisposta);
			pst.setInt(++i, idVariazione);
			pst.setString(++i, tabellaVariazione);
			pst.setString(++i, nomerServizio);

			pst.setString(++i, parameter());


			pst.execute();
		//	db.commit();
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return false ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}


		return true ;
	}

public static String getNoteEsitoKO(Connection db, int idOggetto){
	String note ="";
	String sql = "select note_esito from bdn_ws_log where id_oggetto = ? and esito ilike '%ko%' order by data_operazione desc limit 1" ;
	PreparedStatement pst = null ;
	try {
		pst = db.prepareStatement(sql);
	
	pst.setInt(1, idOggetto);
	ResultSet rs = pst.executeQuery();
	if (rs.next())
		note = rs.getString("note_esito");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return note;
		
}

}
