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

import org.aspcfs.modules.gestioneanagrafica.base.LookupGruppoTemplateNoScia;
import org.aspcfs.utils.Bean;

public class LookupGruppoTemplateNoSciaDAO extends GenericDAO{
    
public LookupGruppoTemplateNoScia lookupGruppoTemplateNoScia;
    
    public LookupGruppoTemplateNoSciaDAO() throws SQLException
    {
        this.lookupGruppoTemplateNoScia = new LookupGruppoTemplateNoScia();
    }
    
    
    public LookupGruppoTemplateNoSciaDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
    {
        Bean.populate(this, properties);
    }
    
    public LookupGruppoTemplateNoSciaDAO(LookupGruppoTemplateNoScia lookupGruppoTemplateNoScia)
    {
        this.lookupGruppoTemplateNoScia=lookupGruppoTemplateNoScia;
    }
    
    public LookupGruppoTemplateNoSciaDAO(ResultSet rs) throws SQLException
    {
        Bean.populate(this, rs);
    }

    @Override
    public ArrayList<LookupGruppoTemplateNoScia> getItems(Connection conn) throws SQLException {
      
        String sql= "select * from  public.lookup_gruppo_template_no_scia where id_lookup_template_no_scia = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, lookupGruppoTemplateNoScia.getId_lookup_template_no_scia());
        ResultSet rs = st.executeQuery();
        
        ArrayList<LookupGruppoTemplateNoScia>  listRichieste = new ArrayList<LookupGruppoTemplateNoScia>();
        
        while (rs.next())
        {
            listRichieste.add(new LookupGruppoTemplateNoScia(rs));
        }
        
        return listRichieste;
    }

}
