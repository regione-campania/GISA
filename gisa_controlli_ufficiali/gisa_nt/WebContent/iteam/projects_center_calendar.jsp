<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id: projects_center_calendar.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="calendarView" class="org.aspcfs.utils.web.CalendarView" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr>
    <td width="66%" valign="top">
      <%= calendarView.getHtml() %>
    </td>
    <td width="33%" valign="top">
    - News articles<br />
    <br />
    - Events (new capability to record an item for a specific date)<br />
    <br />
    - # of tickets closed each day<br />
    - # of new tickets each day<br />
    - 3 new/5 closed<br />
    <br />
    - # of documents uploaded<br />
    <br />
    - Outline start date<br />
    - Outline finish date<br />
    <br />
    User specific<br />
    - Estimated ticket resolutions each day<br />
    - # of activities due on a specific date<br />
    <br />
    Discussion Messages on a specific date<br />
    </td>
