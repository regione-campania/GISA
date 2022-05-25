/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.jmesa;

import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;

import java.util.HashMap;
import java.util.TreeMap;

import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;

public class HashMapCellEditor implements CellEditor
{
	public Object getValue(Object item, String property, int rowcount) 
	{
		
		HashMap h = (HashMap)item;
		Utente u = (Utente)h.get("u");
		TreeMap<String, Ruolo> ruoli = u.getHashRuoli();
		String ruolo = ruoli.get(property) != null && ruoli.get(property).getRuoloInteger() > 0 ? ruoli.get(property).getRuoloString() : "N.D.";
        
        HtmlBuilder html = new HtmlBuilder();
        html.append(ruolo);
        return html.toString();
    }
}
