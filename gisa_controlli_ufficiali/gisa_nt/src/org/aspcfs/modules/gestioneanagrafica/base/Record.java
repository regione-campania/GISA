/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspcfs.utils.Bean;

public class Record 
{
	private MetadatoTemplate metadato = new MetadatoTemplate();
    private String label;
    private String value;
    private String attributo;
    private String name;
    private String style;
    private List<Object> listaLookup = new ArrayList<>();
    private List<Object> addressList = new ArrayList<>();
    
    
    
	public Record(Map<String, String[]> properties,String prefix, boolean isPrefix) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
	{
		Bean.populate(this, properties, prefix, isPrefix);
	}
	
	public Record(ResultSet rs) throws SQLException
	{
		Bean.populate(this, rs);
	}
	
	public Record() 
	{
	}

	public Record(Map<String, String[]> parameterMap) 
	{
		Bean.populate(this, parameterMap);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public MetadatoTemplate getMetadato() {
		return metadato;
	}

	public void setMetadatoTemplate(MetadatoTemplate metadato) {
		this.metadato = metadato;
	}

    public String getAttributo() {
        return attributo;
    }

    public void setAttributo(String attributo) {
        this.attributo = attributo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Object> getListaLookup() {
        return listaLookup;
    }

    public void setListaLookup(List<Object> listaLookup) {
        this.listaLookup = listaLookup;
    }

    public List<Object> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Object> addressList) {
        this.addressList = addressList;
    }

    public void setMetadato(MetadatoTemplate metadato) {
        this.metadato = metadato;
    }
	
}
