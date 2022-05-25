/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.jmesa;

import java.util.ArrayList;
import java.util.TreeMap;

import org.jmesa.view.html.editor.DroplistFilterEditor;

public class AslDroplistFilterEditor extends DroplistFilterEditor {
	
	private static TreeMap<String, String> mapValueLabel = new TreeMap<String, String>();
	
   public static TreeMap<String, String> getMapValueLabel() {
		return mapValueLabel;
	}

	public static void setMapValueLabel(TreeMap<String, String> mapValueLabel) {
		AslDroplistFilterEditor.mapValueLabel = mapValueLabel;
	}

@Override
   protected ArrayList<Option> getOptions()  {
	  ArrayList<Option> options = new ArrayList<Option>();
	  for(String key : mapValueLabel.keySet()){
		  options.add(new Option(mapValueLabel.get(key),key));
	  }
      return options;
   }
}