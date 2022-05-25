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


public class MetadatoTemplate {
    
    private Integer id;
    private LookupTemplateNoScia lookupTemplateNoScia = new LookupTemplateNoScia();
    private LookupGruppoTemplateNoScia lookupGruppoTemplateNoScia = new LookupGruppoTemplateNoScia();
    private List<Object> indirizzo = new ArrayList<>();
    private List<Object> listaLookup = new ArrayList<>();
    private String html_label;
    private Boolean html_enabled;
    private String html_type;
    private String sql_campo;
    private String sql_origine;
    private String sql_condizione;
    private String html_ordine;
    private Integer id_template;
    private Integer ordine_int;
    private String destinazione;
    private Integer id_gruppo_template;
    private String value;
    private String sql_campo_lookup;
    private String sql_origine_lookup;
    private String sql_condizione_lookup;
    private String html_name;
    private String html_style;
    
    public MetadatoTemplate (String html_type, String html_label, String value, List<Object> listaLookup, String html_name, String html_style)
    {
        this.html_type=html_type;
        this.html_label=html_label;
        this.value=value;
        this.listaLookup = listaLookup;
        this.html_name = html_name;
        this.html_style = html_style;
    }
    
    
    public MetadatoTemplate(Map<String, String[]> properties,String prefix, boolean isPrefix) throws IllegalAccessException, InvocationTargetException, SQLException, IllegalArgumentException, ParseException
    {
        Bean.populate(this, properties, prefix, isPrefix);
    }
    
    public MetadatoTemplate(ResultSet rs) throws SQLException
    {
        Bean.populate(this, rs);
    }
    
    public MetadatoTemplate() 
    {
    }

    public MetadatoTemplate(Map<String, String[]> parameterMap) 
    {
        Bean.populate(this, parameterMap);
    }
    
    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSql_campo() 
    {
        return sql_campo;
    }

    public void setSql_campo(String sql_campo) 
    {
        this.sql_campo = sql_campo;
    }

    public String getSql_origine() 
    {
        return sql_origine;
    }

    public void setSql_origine(String sql_origine) 
    {
        this.sql_origine = sql_origine;
    }

    public String getSql_condizione() 
    {
        return sql_condizione;
    }

    public void setSql_condizione(String sql_condizione) 
    {
        this.sql_condizione = sql_condizione;
    }

    
    public Integer getOrdine_int() {
        return ordine_int;
    }

    public void setOrdine_int(Integer ordine_int) {
        this.ordine_int = ordine_int;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getSql_campo_lookup() {
        return sql_campo_lookup;
    }


    public void setSql_campo_lookup(String sql_campo_lookup) {
        this.sql_campo_lookup = sql_campo_lookup;
    }


    public String getSql_origine_lookup() {
        return sql_origine_lookup;
    }


    public void setSql_origine_lookup(String sql_origine_lookup) {
        this.sql_origine_lookup = sql_origine_lookup;
    }


    public String getSql_condizione_lookup() {
        return sql_condizione_lookup;
    }


    public void setSql_condizione_lookup(String sql_condizione_lookup) {
        this.sql_condizione_lookup = sql_condizione_lookup;
    }


    public List<Object> getListaLookup() {
        return listaLookup;
    }


    public void setListaLookup(List<Object> listaLookup) {
        this.listaLookup = listaLookup;
    }


    public String getHtml_label() {
        return html_label;
    }


    public void setHtml_label(String html_label) {
        this.html_label = html_label;
    }


    public Boolean getHtml_enabled() {
        return html_enabled;
    }


    public void setHtml_enabled(Boolean html_enabled) {
        this.html_enabled = html_enabled;
    }


    public String getHtml_type() {
        return html_type;
    }


    public void setHtml_type(String html_type) {
        this.html_type = html_type;
    }


    public String getHtml_ordine() {
        return html_ordine;
    }


    public void setHtml_ordine(String html_ordine) {
        this.html_ordine = html_ordine;
    }


    public String getHtml_name() {
        return html_name;
    }


    public void setHtml_name(String html_name) {
        this.html_name = html_name;
    }


    public List<Object> getIndirizzo() {
        return indirizzo;
    }


    public void setIndirizzo(List<Object> indirizzo) {
        this.indirizzo = indirizzo;
    }


    public String getHtml_style() {
        return html_style;
    }


    public void setHtml_style(String html_style) {
        this.html_style = html_style;
    }


    public LookupTemplateNoScia getLookupTemplateNoScia() {
        return lookupTemplateNoScia;
    }


    public void setLookupTemplateNoScia(LookupTemplateNoScia lookupTemplateNoScia) {
        this.lookupTemplateNoScia = lookupTemplateNoScia;
    }


    public LookupGruppoTemplateNoScia getLookupGruppoTemplateNoScia() {
        return lookupGruppoTemplateNoScia;
    }


    public void setLookupGruppoTemplateNoScia(LookupGruppoTemplateNoScia lookupGruppoTemplateNoScia) {
        this.lookupGruppoTemplateNoScia = lookupGruppoTemplateNoScia;
    }


    public Integer getId_template() {
        return id_template;
    }


    public void setId_template(Integer id_template) {
        this.id_template = id_template;
    }


    public Integer getId_gruppo_template() {
        return id_gruppo_template;
    }


    public void setId_gruppo_template(Integer id_gruppo_template) {
        this.id_gruppo_template = id_gruppo_template;
    }


}
