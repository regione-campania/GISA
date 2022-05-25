/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Displays a calendar in a new window
 * @arg1 = form name of the object being modified
 * @arg2 = element name of the date entry field 
 */
function popCalendar(formname, element) {
  return popCalendar(formname, element, 'en', 'US');
}
function popCalendar(formname, element, language, country, timeZone) {
  var theDate = eval("document." + formname + "." + element + ".value");
  var filename = ('month.jsp?action=popup&form=' + formname + '&element=' + element + '&date=' + theDate + '&language=' + language + '&country=' + country + '&timeZone='+timeZone);
  var posx = 0;
  var posy = 0;
  posx = (screen.width - 200)/2;
  posy = (screen.height - 200)/2;
  var newwin=window.open(filename, 'popcalendar', 'WIDTH=210,HEIGHT=200,RESIZABLE=yes,SCROLLBARS=no,STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenx=' + posx + ',screeny=' + posy);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}
