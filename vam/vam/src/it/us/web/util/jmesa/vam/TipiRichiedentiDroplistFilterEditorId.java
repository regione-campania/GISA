/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.jmesa.vam;

import java.util.ArrayList;

import org.jmesa.view.html.editor.DroplistFilterEditor;

public class TipiRichiedentiDroplistFilterEditorId extends DroplistFilterEditor {
   @Override
   protected ArrayList<Option> getOptions()  {
	   ArrayList<Option> options = new ArrayList<Option>();
      options.add(new Option("1","Privato"));
      options.add(new Option("12", "Altro"));
      options.add(new Option("4", "Associazione"));
      options.add(new Option("3", "Personale ASL"));
      options.add(new Option("5", "Questura"));
      options.add(new Option("6", "Polizia"));
      options.add(new Option("7", "Carabinieri"));
      options.add(new Option("8", "Vigili del fuoco"));
      options.add(new Option("10", "Polizia Municipale"));
      options.add(new Option("11", "Corpo forestale"));
      options.add(new Option("13", "Polizia provinciale"));
      options.add(new Option("14", "Polizia di Stato"));
      options.add(new Option("15", "Guardia di Finanza"));
      return options;
   }
}