/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.sql.Timestamp;

public class OperazioneSuOsa {
	
	//private String data_operazione;
	private Timestamp data_operazione;
	private String utente;
	private String tipo_operazione;
	private String numero_pratica;
	private String causale_pratica;
	private String tipo_pratica;
	private String id_evento_osa;
	private String id_tipo_operazione_evento;
	private String id_pratica_gins;
	
	
	byte[] stab_pre_modifica;		
	
	byte[] stab_post_modifica; 
	
	
	
	public OperazioneSuOsa(){}
	
	public OperazioneSuOsa(Timestamp data_operazione, String utente, String tipo_operazione,
						   String numero_pratica, String causale_pratica, String tipo_pratica,
						   String id_evento_osa, String id_tipo_operazione_evento, String id_pratica_gins){
		this.setData_operazione(data_operazione);
		this.setUtente(utente);
		this.setTipo_operazione(tipo_operazione);
		this.setNumero_pratica(numero_pratica);
		this.setCausale_pratica(causale_pratica);
		this.setTipo_pratica(tipo_pratica);
		this.setId_evento_osa(id_evento_osa);
		this.setId_tipo_operazione_evento(id_tipo_operazione_evento);
		this.setId_pratica_gins(id_pratica_gins);
	}

	public Timestamp getData_operazione() {
		return data_operazione;
	}

	public void setData_operazione(Timestamp data_operazione) {
		this.data_operazione = data_operazione;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getTipo_operazione() {
		return tipo_operazione;
	}

	public void setTipo_operazione(String tipo_operazione) {
		this.tipo_operazione = tipo_operazione;
	}

	public String getNumero_pratica() {
		return numero_pratica;
	}

	public void setNumero_pratica(String numero_pratica) {
		this.numero_pratica = numero_pratica;
	}

	public String getCausale_pratica() {
		return causale_pratica;
	}

	public void setCausale_pratica(String causale_pratica) {
		this.causale_pratica = causale_pratica;
	}

	public String getTipo_pratica() {
		return tipo_pratica;
	}

	public void setTipo_pratica(String tipo_pratica) {
		this.tipo_pratica = tipo_pratica;
	}

	public String getId_evento_osa() {
		return id_evento_osa;
	}

	public void setId_evento_osa(String id_evento_osa) {
		this.id_evento_osa = id_evento_osa;
	}

	public String getId_tipo_operazione_evento() {
		return id_tipo_operazione_evento;
	}

	public void setId_tipo_operazione_evento(String id_tipo_operazione_evento) {
		this.id_tipo_operazione_evento = id_tipo_operazione_evento;
	}

	public String getId_pratica_gins() {
		return id_pratica_gins;
	}

	public void setId_pratica_gins(String id_pratica_gins) {
		this.id_pratica_gins = id_pratica_gins;
	}


	public byte[] getStab_pre_modifica() {
		return stab_pre_modifica;
	}

	public void setStab_pre_modifica(byte[] stab_pre_modifica) {
		this.stab_pre_modifica = stab_pre_modifica;
	}

	public byte[] getStab_post_modifica() {
		return stab_post_modifica;
	}

	public void setStab_post_modifica(byte[] stab_post_modifica) {
		this.stab_post_modifica = stab_post_modifica;
	}

	
	public String insertAmpliamento(Connection db, Map<String, String> campiFissi, Map<String, String> campiEstesi,
			int userId, int altId, int idTipologiaPratica, String numeroPratica, int idComunePratica) {
		// TODO Auto-generated method stub
		String sql = "select * from public.pratica_ampliamento_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement st;
    	String risultato_query ="";
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3,userId);
	        st.setInt(4, altId);
	        st.setInt(5, idTipologiaPratica);
	        st.setString(6, numeroPratica);
	        st.setInt(7, idComunePratica);
	        System.out.println(st);
	        
	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("pratica_ampliamento_gestione_anagrafica");
	  		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return risultato_query;
    	
	}

	public String insertVariazioneTitolarita(Connection db, Map<String, String> campiFissi,
			Map<String, String> campiEstesi, int userId, int altId, int idTipologiaPratica, String numeroPratica,
			int idComunePratica) {
		// TODO Auto-generated method stub
		
	       
	    	String sql = "select * from public.pratica_variazione_titolarita_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
	    	PreparedStatement st;
	    	String risultato_query ="";

			try {
				st = db.prepareStatement(sql);
				st.setObject(1, campiFissi);
		    	st.setObject(2, campiEstesi);
		        st.setInt(3,userId);
		        st.setInt(4, altId);
		        st.setInt(5, idTipologiaPratica);
		        st.setString(6, numeroPratica);
		        st.setInt(7, idComunePratica);
		        System.out.println(st);

		        ResultSet rs = st.executeQuery();
		  		while (rs.next()){
		  			risultato_query = rs.getString("pratica_variazione_titolarita_gestione_anagrafica");
		  		}
		  		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
		return risultato_query;
	}

	public String insertVariazioneTitolaritaCambioSoggetto(Connection db, Map<String, String> campiFissi,
			Map<String, String> campiEstesi, int userId, int altId, int idTipologiaPratica, String numeroPratica,
			int idComunePratica) {
		// TODO Auto-generated method stub
		String risultato_query ="";
		String sql = "select * from public.pratica_variazione_titolarita_soggetto_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3,userId);
	        st.setInt(4, altId);
	        st.setInt(5, idTipologiaPratica);
	        st.setString(6, numeroPratica);
	        st.setInt(7, idComunePratica);
	        System.out.println(st);
   
	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("pratica_variazione_titolarita_soggetto_gestione_anagrafica");
	  		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		return risultato_query;
	}

	public String insertTrasferimentoSedeOperativa(Connection db, Map<String, String> campiFissi,
			Map<String, String> campiEstesi, int userId, int altId, int idTipologiaPratica, String numeroPratica,
			int idComunePratica) {
		// TODO Auto-generated method stub
        String risultato_query="";
	 	String sql = "select * from public.pratica_trasferimento_sede_operativa_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3, userId);
	        st.setInt(4, altId);
	        st.setInt(5, idTipologiaPratica);
	        st.setString(6, numeroPratica);
	        st.setInt(7, idComunePratica);
	        System.out.println(st);

	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("pratica_trasferimento_sede_operativa_gestione_anagrafica");
	  		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		return risultato_query;
	}

	public String insertCambioSedeLegale(Connection db, Map<String, String> campiFissi,
			Map<String, String> campiEstesi, int userId, int altId, int idTipologiaPratica, String numeroPratica,
			int idComunePratica) {
		// TODO Auto-generated method stub
		String sql = "select * from public.pratica_cambio_sede_legale_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
        String risultato_query="";

    	PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3,userId);
	        st.setInt(4, altId);
	        st.setInt(5, idTipologiaPratica);
	        st.setString(6, numeroPratica);
	        st.setInt(7, idComunePratica);
	        System.out.println(st);

	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("pratica_cambio_sede_legale_gestione_anagrafica");
	  		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return risultato_query;
	}

	public String insertAmpliamentoFisico(Connection db, Map<String, String> campiFissi,
			Map<String, String> campiEstesi, int userId, int altId, int idTipologiaPratica, String numeroPratica,
			int idComunePratica) {
		// TODO Auto-generated method stub
        String risultato_query="";
		String sql = "select * from public.pratica_ampliamento_fisico_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3, userId);
	        st.setInt(4, altId);
	        st.setInt(5, idTipologiaPratica);
	        st.setString(6, numeroPratica);
	        st.setInt(7, idComunePratica);
	        System.out.println(st);

	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("pratica_ampliamento_fisico_gestione_anagrafica");
	  		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
  		
		return risultato_query;
	}

	public String insertLineaPregressa(Connection db, Map<String, String> campiFissi, int stabId, int idLinea,
			String data_inizio, String data_fine, String cun, int userId) {
		// TODO Auto-generated method stub
		String risultato_query="";
		String sql = "select * from aggiungi_linea_pregressa_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	        st.setInt(2, stabId);
	        st.setInt(3, idLinea);
	        st.setString(4, data_inizio);
	        st.setString(5, data_fine);
	        st.setString(6, cun);
	        st.setInt(7, userId);
	        System.out.println(st);
	        
	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("aggiungi_linea_pregressa_gestione_anagrafica");
	  		}
	  		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		return risultato_query;
	}

	public String modifyGestioneAnagrafica(Connection db, Map<String, String> campiFissi, Map<String, String> campiEstesi, int userId,
			int altId) {
		// TODO Auto-generated method stub
		
	    String sql = "";
	    PreparedStatement st = null;
	    ResultSet rs = null;
	  	    
    	//chiamare dbi insert no scia e passare (campiFissi, campiEstesi, userId, idTipoPratica, numeroPratica)
        sql = "select * from public.modify_gestione_anagrafica(?,?,?,?)";
        String id_stabilimento = "";

    	try {
			st = db.prepareStatement(sql);
	        st.setObject(1, campiFissi);
			st.setObject(2, campiEstesi);
	        st.setInt(3, userId);
	        st.setInt(4, altId);
	    	
	        System.out.println(st);
	        
	        rs = st.executeQuery();
	        
	        
	        while(rs.next())
	        {
	        	id_stabilimento = rs.getString("modify_gestione_anagrafica");
	        }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    			
		return id_stabilimento;
	}

	public ArrayList<OperazioneSuOsa> getListaOperazioniStabilimento(Connection db, int altId) {
		// TODO Auto-generated method stub
		String sql = "select * from get_lista_operazioni_stabilimento_gestione_anagrafica(?)"; 
		ArrayList<OperazioneSuOsa> lista_operazioni = new ArrayList<OperazioneSuOsa>();
	    PreparedStatement st = null;
	    ResultSet rs = null;
        
     	try {
			st = db.prepareStatement(sql);
			st.setInt(1,altId);
	        System.out.println(st);
	        
	        rs = st.executeQuery();
	        
	        while(rs.next())
	        {
	        	lista_operazioni.add(new OperazioneSuOsa(
	        			rs.getTimestamp("data_operazione"), 
	        			rs.getString("utente"), 
	        			rs.getString("tipo_operazione"), 
	        			rs.getString("numero_pratica"), 
	        			rs.getString("causale_pratica"), 
	        			rs.getString("tipo_pratica"),
	        			rs.getString("id_evento_osa"),
	        			rs.getString("id_tipo_operazione_evento"),
	        			rs.getString("id_pratica_gins")
	        			));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
		
		return lista_operazioni;
	}

	public String modificaLinee(Connection db, Map<String, String> campiFissi, int idUtente, int altId) {
		// TODO Auto-generated method stub
		String sql = "select * from modify_linee_gestione_anagrafica(?,?,?)"; 
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    String id_stabilimento = "";
     	try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setInt(2, idUtente);
	    	st.setInt(3, altId);
	        System.out.println(st);
	        
	        rs = st.executeQuery();
	        
	        while(rs.next())
	        {
	        	id_stabilimento = rs.getString("modify_linee_gestione_anagrafica");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
        
		return id_stabilimento;
	}

	public void getDettaglioEvento(Connection db, int id_evento) {
		// TODO Auto-generated method stub
		String sql = "select * from eventi_su_osa where id = ?";
		PreparedStatement st;
		try {
			st = db.prepareStatement(sql);
			 st.setInt(1, id_evento);
			    ResultSet rs = st.executeQuery();
				while (rs.next()){
					
					this.stab_pre_modifica = rs.getBytes("pre_evento");
					this.stab_post_modifica = rs.getBytes("post_evento");
					
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}

	public String sospendiRiattiva(Connection db, Map<String, String> campiFissi, Map<String, String> campiEstesi,
			int userId, int altId, int idTipologiaPratica, String numeroPratica, int idComunePratica) {
		// TODO Auto-generated method stub

      String sql = "select * from public.pratica_sospendi_riattiva_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
 	  PreparedStatement st;
	  String risultato_query="";

	  try {
		st = db.prepareStatement(sql);
		st.setObject(1, campiFissi);
	 	st.setObject(2, campiEstesi);
	    st.setInt(3, userId);
	    st.setInt(4, altId);
	    st.setInt(5, idTipologiaPratica);
	    st.setString(6, numeroPratica);
	    st.setInt(7, idComunePratica);
	    System.out.println(st);

	    ResultSet rs = st.executeQuery();
			while (rs.next()){
				risultato_query = rs.getString("pratica_sospendi_riattiva_gestione_anagrafica");
			}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
		return risultato_query;
	}

	public String cessazioneGestioneAnagrafica(Connection db, Map<String, String> campiFissi,
			Map<String, String> campiEstesi, int userId, int altId, int idTipologiaPratica, String numeroPratica,
			int idComunePratica) {
		// TODO Auto-generated method stub
        String risultato_query="";
        String sql = "select * from public.pratica_cessazione_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
	    PreparedStatement st;
			try {
				st = db.prepareStatement(sql);
				st.setObject(1, campiFissi);
		    	st.setObject(2, campiEstesi);
		        st.setInt(3, userId);
		        st.setInt(4, altId);
		        st.setInt(5, idTipologiaPratica);
		        st.setString(6, numeroPratica);
		        st.setInt(7, idComunePratica);
		        System.out.println(st);
		        
		        ResultSet rs = st.executeQuery();
		  		while (rs.next()){
		  			risultato_query = rs.getString("pratica_cessazione_gestione_anagrafica");
		  		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return risultato_query;
	}
	
	public String insertTrasformazioneLinea(Connection db, Map<String, String> campiFissi, Map<String, String> campiEstesi,
			int userId, int altId, int idTipologiaPratica, String numeroPratica, int idComunePratica) {
		// TODO Auto-generated method stub
		String sql = "select * from public.pratica_trasformazione_gestione_anagrafica(?, ?, ?, ?, ?, ?, ?)";
    	PreparedStatement st;
    	String risultato_query ="";
		try {
			st = db.prepareStatement(sql);
			st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3,userId);
	        st.setInt(4, altId);
	        st.setInt(5, idTipologiaPratica);
	        st.setString(6, numeroPratica);
	        st.setInt(7, idComunePratica);
	        System.out.println(st);
	        
	        ResultSet rs = st.executeQuery();
	  		while (rs.next()){
	  			risultato_query = rs.getString("pratica_trasformazione_gestione_anagrafica");
	  		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return risultato_query;
    	
	}
	
	
}
