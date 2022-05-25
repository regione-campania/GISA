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
import org.aspcfs.modules.gestioneanagrafica.base.LookupTemplateNoScia;
import org.aspcfs.utils.Bean;


public class LookupTemplateNoSciaDAO extends GenericDAO{
    
    
    public LookupTemplateNoScia lookupTemplateNoScia;

    
    public LookupTemplateNoSciaDAO() throws SQLException
    {
        this.lookupTemplateNoScia = new LookupTemplateNoScia();
    }
    
    
    public LookupTemplateNoSciaDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
    {
        Bean.populate(this, properties);
    }
    
    public LookupTemplateNoSciaDAO(LookupTemplateNoScia configuraLookupTemplateNoScia)
    {
        this.lookupTemplateNoScia=configuraLookupTemplateNoScia;
    }
    
    public LookupTemplateNoSciaDAO(ResultSet rs) throws SQLException
    {
        Bean.populate(this, rs);
    }
    
    public ArrayList<LookupTemplateNoScia> getItems(Connection conn) throws SQLException {
        
        
        String sql= "select * from  public.lookup_template_no_scia";
        PreparedStatement st = conn.prepareStatement(sql);
        
        ResultSet rs = st.executeQuery();
        
        ArrayList<LookupTemplateNoScia>  listGruppo = new ArrayList<LookupTemplateNoScia>();
        
        while (rs.next())
        {
            listGruppo.add(new LookupTemplateNoScia(rs));
        }
        
        return listGruppo;
    }


    @Override
    public Object getItem(Connection connection) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
