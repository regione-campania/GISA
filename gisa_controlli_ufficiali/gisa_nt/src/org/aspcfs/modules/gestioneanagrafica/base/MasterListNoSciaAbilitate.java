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

public class MasterListNoSciaAbilitate {
    
    private Integer id;
    private String codice_univoco_ml;
    private Boolean enabled;
    private Integer id_lookup_template_no_scia;

    
    public MasterListNoSciaAbilitate(ResultSet rs) throws SQLException
    {
        Bean.populate(this, rs);
    }
    
    public MasterListNoSciaAbilitate() 
    {
    }
    
    public MasterListNoSciaAbilitate(Integer id) 
    {
        this.id=id;
    }

    public MasterListNoSciaAbilitate(Map<String, String[]> parameterMap) 
    {
        Bean.populate(this, parameterMap);
    }
    
   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodice_univoco_ml() {
        return codice_univoco_ml;
    }

    public void setCodice_univoco_ml(String codice_univoco_ml) {
        this.codice_univoco_ml = codice_univoco_ml;
    }

    public Integer getId_lookup_template_no_scia() {
        return id_lookup_template_no_scia;
    }

    public void setId_lookup_template_no_scia(Integer id_lookup_template_no_scia) {
        this.id_lookup_template_no_scia = id_lookup_template_no_scia;
    }
    

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


}
