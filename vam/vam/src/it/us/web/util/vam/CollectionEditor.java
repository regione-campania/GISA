/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import it.us.web.bean.vam.CartellaClinica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;

public class CollectionEditor implements CellEditor
{
	public Object getValue(Object item, String property, int rowcount) 
	{
		HtmlBuilder html = new HtmlBuilder();
		Set<CartellaClinica> items = (Set<CartellaClinica>)item;
		
		Iterator<CartellaClinica> iter = items.iterator();
		
		
		while(iter.hasNext())
		{
			html.append(iter.next().getDiagnosis());
		}
        return html.toString();
    }
}