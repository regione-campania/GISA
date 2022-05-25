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

import org.aspcfs.modules.gestioneanagrafica.base.MasterListNoSciaAbilitate;
import org.aspcfs.utils.Bean;


public class NoSciaAbilitateDAO extends GenericDAO{
    
    
    public MasterListNoSciaAbilitate noSciaAbilitate;

    
    public NoSciaAbilitateDAO() throws SQLException
    {
        this.noSciaAbilitate = new MasterListNoSciaAbilitate();
    }
    
    
    public NoSciaAbilitateDAO(Map<String, String[]> properties) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
    {
        Bean.populate(this, properties);
    }
    
    public NoSciaAbilitateDAO(MasterListNoSciaAbilitate noSciaAbilitate)
    {
        this.noSciaAbilitate=noSciaAbilitate;
    }
    
    public NoSciaAbilitateDAO(ResultSet rs) throws SQLException
    {
        Bean.populate(this, rs);
    }
    
    public ArrayList<MasterListNoSciaAbilitate> getItems(Connection conn) throws SQLException {
        
        
        String sql= "select * from  public.master_list_no_scia_abilitate where codice_univoco_ml ilike ? ";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, noSciaAbilitate.getCodice_univoco_ml());
        
        ResultSet rs = st.executeQuery();
        
        ArrayList<MasterListNoSciaAbilitate>  list = new ArrayList<MasterListNoSciaAbilitate>();
        
        while (rs.next())
        {
            list.add(new MasterListNoSciaAbilitate(rs));
        }
        
        return list;
    }


    @Override
    public Integer getItem(Connection conn) throws SQLException {
     
        Integer idLookupTemplate = 0;
        String sql= "select id_lookup_template_no_scia from  public.master_list_no_scia_abilitate where codice_univoco_ml ilike ? ";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, noSciaAbilitate.getCodice_univoco_ml());
        
        ResultSet rs = st.executeQuery();
        
        while (rs.next())
        {
            idLookupTemplate = rs.getInt(1);
        }
        
        return idLookupTemplate;
    }

}
