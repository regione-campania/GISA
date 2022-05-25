<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.aspcfs.utils.DateUtils" %>
<%
  String month = request.getParameter("month");
  String day = request.getParameter("day");
  String year = request.getParameter("year");
  String language = request.getParameter("language");
  String country = request.getParameter("country");
  if (language == null) {
    language = "en";
    country = "US";
  }
  Locale locale = new Locale(language, country);
  SimpleDateFormat formatter = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
  formatter.applyPattern(DateUtils.get4DigitYearDateFormat(formatter.toPattern()));
  Calendar cal = Calendar.getInstance();
  cal.set(Calendar.YEAR, Integer.parseInt(year));
  cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
  cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
  String formattedDate = formatter.format(cal.getTime());
%>
<html>
<head>
</head>
<body onload='page_init();'>
<script language='Javascript'>
function page_init() {
  parent.formatDate('<%= formattedDate %>');
}
</script>
</body>
</html>
