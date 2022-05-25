/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function popupAggiunta() {
  var stili = "top=30, left=30, width=600, height=150, status=no, menubar=no, toolbar=no scrollbars=no";
  var testo = window.open("", "", stili);
    
  testo.document.write("<html>\n");
  testo.document.write(" <head>\n");
  testo.document.write("  <title>G.I.S.A.</title>\n");
  testo.document.write("  <basefont size=2 face=Tahoma>\n");
  testo.document.write(" </head>\n");
  testo.document.write("<body>");
  testo.document.write("<center><b><font color=\"red\">Attenzione!</font></b><br/>");
  testo.document.write("In questa fase di transizione verso la gestione Operatore Unico, per le operazioni di inserimento contattare ORSA. <br/><br/>");
  testo.document.write("<b>Mail</b>: diletta.mandato@izsmportici.it <br/>");
  testo.document.write("Scheda da scaricare e compilare: <a href='operatorinonaltrove/AggiungiOperatoreNonAltrove.pdf' target='_blank'><font color='red'>clicca qui</font></a></center>\n");
  testo.document.write("</body>");
  testo.document.write("</html>");
 }
 