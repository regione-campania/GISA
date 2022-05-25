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
  - Version: $Id: projects_center_news_by_category.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="newsList" class="com.zeroio.iteam.base.NewsArticleList" scope="request"/>
<jsp:useBean id="projectNewsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="newsArticleCategoryList" class="com.zeroio.iteam.base.NewsArticleCategoryList" scope="request"/>
<jsp:useBean id="taskCategoryList" class="org.aspcfs.modules.tasks.base.TaskCategoryList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_news_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%-- begin list of article categories and their subjects --%>
<%
  int count = 0;
  int previousCategoryId = -2;
  int rowid = 0;
  Iterator i = newsList.iterator();
  while (i.hasNext()) {
    ++count;
    rowid = (rowid != 1?1:2);
    NewsArticle thisArticle = (NewsArticle) i.next();
%>
  <dhv:evaluate if="<%= previousCategoryId != thisArticle.getCategoryId() %>">
  <dhv:evaluate if="<%= count > 1 %>">
  </table>
  <br />
  </dhv:evaluate>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <%= toHtml(newsArticleCategoryList.getValueFromId(thisArticle.getCategoryId())) %>
    </th>
  </tr>
  </dhv:evaluate>
  <tr class="row<%= rowid %>">
    <td width="8" valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisArticle.getId() %>);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="top">
      <a href="javascript:popURL('ProjectManagementNews.do?command=Details&pid=<%= thisArticle.getProjectId() %>&id=<%= thisArticle.getId() %>&popup=true','Project_News','600','400','yes','yes');"><%= toHtml(thisArticle.getSubject()) %></a>
      <font color="red">
      <dhv:evaluate if="<%= thisArticle.getStatus() == NewsArticle.DRAFT %>">(Draft)</dhv:evaluate>
      <dhv:evaluate if="<%= thisArticle.getStatus() == NewsArticle.UNAPPROVED %>">(Unapproved)</dhv:evaluate>
      </font>
    </td>
  </tr>
<%
    if (previousCategoryId != thisArticle.getCategoryId()) {
      previousCategoryId = thisArticle.getCategoryId();
    }
  }
%>
<dhv:evaluate if="<%= newsList.size() > 0 %>">
  </table>
</dhv:evaluate>
