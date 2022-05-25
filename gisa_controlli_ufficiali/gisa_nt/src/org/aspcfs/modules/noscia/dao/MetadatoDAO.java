/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import org.aspcfs.modules.gestioneanagrafica.base.LookupTemplateNoScia;
import org.aspcfs.modules.gestioneanagrafica.base.MetadatoTemplate;
import org.aspcfs.utils.Bean;


public class MetadatoDAO extends GenericDAO{
    
    private MetadatoTemplate metadato;

    public MetadatoDAO()
    {
        this.metadato=new MetadatoTemplate();
    }
    

    public MetadatoDAO(MetadatoTemplate metadato)
    {
        this.metadato=metadato;
    }
    
    public MetadatoDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
    {
        Bean.populate(this, properties);
    }
    
    public MetadatoDAO(Map<String, String[]> properties,String prefix, boolean isPrefix) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
    {
        Bean.populate(this, properties, prefix, isPrefix);
    }
    
    public MetadatoDAO(ResultSet rs) throws SQLException 
    {
        Bean.populate(this, rs);
    }
    
  
    public ArrayList<MetadatoTemplate> getItems(Connection conn) throws SQLException 
    {
        String sql = " select * from public.cerca_metadato (?) "  ;
        
        PreparedStatement st = conn.prepareStatement(sql);
        st.setObject(1, metadato.getLookupGruppoTemplateNoScia().getId());
        
        
        ResultSet rs = st.executeQuery();
        ArrayList<MetadatoTemplate> metadati = new ArrayList<MetadatoTemplate>();
        
        while(rs.next())
        {
            MetadatoTemplate metadato = new MetadatoTemplate(rs);
            LookupTemplateNoScia tipo = new LookupTemplateNoScia(metadato.getLookupGruppoTemplateNoScia().getId());
            LookupTemplateNoSciaDAO tipoDao = new LookupTemplateNoSciaDAO(tipo);
            metadato.setLookupTemplateNoScia((LookupTemplateNoScia) tipoDao.getItem(conn));
          //  metadato.setId_oggetto(this.metadato.getId_oggetto());
        
            metadati.add(metadato);
        }
        
        return metadati;
    }


    @Override
    public Object getItem(Connection connection) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}
