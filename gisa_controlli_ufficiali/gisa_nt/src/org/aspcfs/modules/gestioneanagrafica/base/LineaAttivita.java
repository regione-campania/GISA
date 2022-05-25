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

import org.aspcfs.utils.Bean;



public class LineaAttivita {
    
    private Integer id;
    private String linea_attivita;
    private String codice_univoco;
    private Aggregazione aggregazione = new Aggregazione();
    private String codice_attivita;
	private String descrizioneLinea;

    
    
    public String getDescrizioneLinea() {
		return descrizioneLinea;
	}


	public void setDescrizioneLinea(String descrizioneLinea) {
		this.descrizioneLinea = descrizioneLinea;
	}


	public LineaAttivita()
    {
        
    }
    
    
    public LineaAttivita(ResultSet rs) throws SQLException 
    {
        Bean.populate(this, rs);
    }
    
    public LineaAttivita(Map<String, String[]> properties) throws SQLException
    {
        Bean.populate(this, properties);
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getLinea_attivita() {
        return linea_attivita;
    }
    public void setLinea_attivita(String linea_attivita) {
        this.linea_attivita = linea_attivita;
    }
    
    
    public String getCodice_univoco() {
        return codice_univoco;
    }
    public void setCodice_univoco(String codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    
    
    public ArrayList<LineaAttivita> getLinee(Connection db) throws SQLException
    {
       
        
        String sql = "select * from public.get_linea_attivita_noscia()";
        
        
        PreparedStatement st  = db.prepareStatement(sql);
      
        ResultSet rs = st.executeQuery();

        ArrayList<LineaAttivita> listLinee = new ArrayList<LineaAttivita>();
        
        while(rs.next())
        {
            LineaAttivita la = new LineaAttivita();
            la.setCodice_univoco(rs.getString("codice_attivita"));
            la.setLinea_attivita(rs.getString("path_descrizione"));
            
            listLinee.add(la);
        }
        
        return listLinee;
        
        
        
    }


    public Aggregazione getAggregazione() {
        return aggregazione;
    }


    public void setAggregazione(Aggregazione aggregazione) {
        this.aggregazione = aggregazione;
    }


    public String getCodice_attivita() {
        return codice_attivita;
    }


    public void setCodice_attivita(String codice_attivita) {
        this.codice_attivita = codice_attivita;
    }




    
//	public String getCodiceLineaByAltId(Connection db, int altid) {
//		// TODO Auto-generated method stub
//		 
//	       String sqlNumeroLinee = "select ml8.codice codice_ml "
//	    				+ "from opu_relazione_stabilimento_linee_produttive rel "
//	    				+ "join opu_stabilimento s on rel.id_stabilimento = s.id "
//	    				+ "join ml8_linee_attivita_nuove_materializzata ml8 on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita "
//	    				+ "left join master_list_flag_linee_attivita flag on flag.codice_univoco = ml8.codice_attivita "
//	    				+ "where s.alt_id = ? and rel.enabled and flag.no_scia";
//	       PreparedStatement stNumeroLinee;
//		try {
//			stNumeroLinee = db.prepareStatement(sqlNumeroLinee);
//			stNumeroLinee.setInt(1, altid);
//		       ResultSet rsNumeroLinee = stNumeroLinee.executeQuery();
//		       while(rsNumeroLinee.next()){
//		    	 this.codice_univoco = rsNumeroLinee.getString("codice_ml");
//		       }
//			} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	       if(codice_univoco == null)
//	    	   codice_univoco = "TEST-SCIA";
//	       return codice_univoco;
//			
//	}


//	public void getLineaByCodice(Connection db, String codice) {
//		// TODO Auto-generated method stub
//		String sql = "select * from public.get_linea_attivita_noscia() where codice_attivita = ?";
//    
//        ResultSet rs;
//		try {
//			PreparedStatement st = db.prepareStatement(sql);
//	        st.setString(1, codice);
//			rs = st.executeQuery();
//			while(rs.next()){
//		        	this.descrizioneLinea = rs.getString("path_descrizione");
//		    }
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//       
//     
//	}
    
}
