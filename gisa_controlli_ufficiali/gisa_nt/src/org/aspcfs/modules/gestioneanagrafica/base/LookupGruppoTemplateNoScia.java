/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.aspcfs.utils.Bean;

public class LookupGruppoTemplateNoScia {
    
    private Integer id;
    private String label;
    private String hash_name;
    private Integer id_lookup_template_no_scia;
    
    
    public LookupGruppoTemplateNoScia(ResultSet rs) throws SQLException
    {
        Bean.populate(this, rs);
    }
    
    public LookupGruppoTemplateNoScia() 
    {
    }
    
    public LookupGruppoTemplateNoScia(Integer code) 
    {
        this.id=code;
    }

    public LookupGruppoTemplateNoScia(Map<String, String[]> parameterMap) 
    {
        Bean.populate(this, parameterMap);
    }
    
 
    
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash_name() {
        return hash_name;
    }

    public void setHash_name(String hash_name) {
        this.hash_name = hash_name;
    }

    public Integer getId_lookup_template_no_scia() {
        return id_lookup_template_no_scia;
    }

    public void setId_lookup_template_no_scia(Integer id_lookup_template_no_scia) {
        this.id_lookup_template_no_scia = id_lookup_template_no_scia;
    }
    

}
