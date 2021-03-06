<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--
 Portions Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%--
 Portions Copyright 2001-2004 Concursive Corporation
 Use as licensed.
--%>
<%-- This jsp shows a calendar to the user and sends the selected date back
     to the calling form.
     NOTE: THIS JSP DOES NOT REQUIRE A USER TO BE LOGGED IN --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="org.aspcfs.modules.mycfs.base.CalendarEvent" %>
<%@ page import="org.aspcfs.utils.web.CalendarView" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<html>
<head>
<title><dhv:label name="calendar.calendar">Calendar</dhv:label></title>
<jsp:include page="templates/cssInclude.jsp" flush="true"/>
<% 
   String formName = request.getParameter("form"); 
   String element = request.getParameter("element"); 
   String language = request.getParameter("language");
   String country = request.getParameter("country");
   if (language == null) {
     language = "en";
     country = "US";
   }
%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
  function openWindow(month, day, year) {
    width = 600;
    height = 400;
    url='schedule.jsp?month=' + month + 
          '&&year=' + year + '&&day=' + day;
    Win = open(url, 'as_events', 'toolbar=0,location=0,directories=0,status=0,menubar=0,resizable=1,width=' + width + ',height=' + height + ',scrollbars=yes');
    Win.focus();
  }
  function formatDate(val) {
    opener.document.<%= formName %>.<%= element %>.value = val; 
    window.close();
  }
  function returnDate(dayVal, monthVal, yearVal) {
    window.frames['server_commands'].location.href='month_format.jsp?month=' + monthVal + '&day=' + dayVal + '&year=' + yearVal + '&language=<%= language %>&country=<%= country %>';
  }
</script>
</head>
<body>
<form name="monthBean" action="month.jsp">
<%
  //Retrieve the parameters
  String year = request.getParameter("year");
  String month = request.getParameter("month");
  String day = request.getParameter("day");
  String origDay = request.getParameter("origDay");
  String origYear = request.getParameter("origYear");
  String origMonth = request.getParameter("origMonth");
  String dateString = request.getParameter("date");
  String timeZone = request.getParameter("timeZone");

  //If the user clicks the next/previous arrow, increment/decrement the month
  //Range checking is not necessary on the month.  The calendar object automatically
  //increments the year when necessary
  if (month != null) {
    try {
      int monthTmp = Integer.parseInt(month);
      if (request.getParameter("next.x") != null) { ++monthTmp; }
      if (request.getParameter("prev.x") != null) { monthTmp += -1; }
      month = String.valueOf(monthTmp);
      day = "1";
    } catch(NumberFormatException e) {
    }
  }
  
  // Create the calendar with the selected locale
  Locale locale = new Locale(language, country);
  CalendarView calendarView = new CalendarView(locale);
  if (User.getUserId() != -1 && User != null) {
 		calendarView.setSystemStatus(User.getSystemStatus(getServletConfig()));
	}

  //Check to see if this should be a popup window
  String action = request.getParameter("action");
  if ((action != null) && (action.equals("popup"))) {
    out.println("<input type=\"hidden\" name=\"action\" value=\"popup\">");
    calendarView.setPopup(true);
    calendarView.setBorderSize(0);
  } else {
    calendarView.setHeaderSpace(true);
    calendarView.setMonthArrows(true);
    calendarView.setBorderSize(1);
  }
  
  // Break apart String into fields, using locale (from input field)
  if (dateString != null) {
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(DateFormat.SHORT, locale).parse(dateString);
      Calendar parseCal = Calendar.getInstance();
      parseCal.setTime(tmpDate);
      month = String.valueOf(parseCal.get(Calendar.MONTH) + 1);
      day = String.valueOf(parseCal.get(Calendar.DAY_OF_MONTH));
      year = String.valueOf(parseCal.get(Calendar.YEAR));
    } catch (Exception e) {
    }
  }
  
  // Set the calendar with appropriate values
  calendarView.setYear(year);
  calendarView.setMonth(month);
  calendarView.setDay(day);

  //set the timezone if the user is logged in
  org.aspcfs.modules.login.beans.UserBean thisUser = (org.aspcfs.modules.login.beans.UserBean) request.getSession().getAttribute("User");
  if (thisUser.getUserId() != -1 && thisUser != null && thisUser.getUserRecord() != null) {
    	calendarView.setTimeZone(TimeZone.getTimeZone(thisUser.getUserRecord().getTimeZone()));
  }else {
    calendarView.setTimeZone(TimeZone.getTimeZone(timeZone));
  }
  
  //Configure the month to highlight a date that was passed in
  String origStatus = request.getParameter("origStatus");
  if ((origStatus == null) && ((day == null) || (day.startsWith("undefined")))) {
    origStatus = "blank";
  } else if (origStatus == null) {
    origStatus = "highlight";
  }
  //Restore the original request date from the calling app,
  //this allows the calling date to be highlighted differently from
  //other dates
  if ((origYear != null) && (origMonth != null) && (origDay != null)) {
  } else {
    origYear = calendarView.getYear();
    origMonth = calendarView.getMonth();
    origDay = calendarView.getDay();
  }
  calendarView.getEventList().put(origMonth + "/" + origDay + "/" + origYear, origStatus);
%>
<input type="hidden" name="origYear" value="<%= origYear %>">
<input type="hidden" name="origMonth" value="<%= origMonth %>">
<input type="hidden" name="origDay" value="<%= origDay %>">
<input type="hidden" name="origStatus" value="<%= origStatus %>">
<input type="hidden" name="form" value="<%= formName %>">
<input type="hidden" name="element" value="<%= element %>">
<input type="hidden" name="language" value="<%= language %>">
<input type="hidden" name="country" value="<%= country %>">
<input type="hidden" name="timeZone" value="<%=timeZone%>">
<%= calendarView.getHtml() %>
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</body>
</html>
