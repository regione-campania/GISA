<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="postIt" class="org.aspcfs.modules.mycfs.base.PostIt" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<style>
/*
Code by Creative Punch
 http://creative-punch.net/2014/02/create-css3-post-it-note/#comments
Create a CSS3 post-it note without images
*/

@import url(http://fonts.googleapis.com/css?family=Satisfy);
@import url(https://fonts.googleapis.com/css?family=Muli);

.quote-container {
  margin-top: 50px;
  position: relative;
       word-break: break-all;
  
}

.note {
  color: #333;
  position: relative;
  width: 250px;
  margin: 0 auto;
  padding: 20px;
  font-family: Muli;
  font-size: 26;
  box-shadow: 2px 10px 15px 6px rgba(255,255,255,0.3);
}

.note .author {
  display: block;
  margin: 40px 0 0 0;
  text-align: right;
}

.blue {
  background: #4da5ff;
  -webkit-transform: rotate(2deg);
  -moz-transform: rotate(2deg);
  -o-transform: rotate(2deg);
  -ms-transform: rotate(2deg);
  transform: rotate(2deg);
}

.pin {
  background-color: #aaa;
  display: block;
  height: 32px;
  width: 2px;
  position: absolute;
  left: 50%;
  top: -16px;
  z-index: 1;
}

.pin:after {
  background-color: #f7e89f;
  background-image: radial-gradient(25% 25%, circle, hsla(0,0%,100%,.3), hsla(0,0%,0%,.3));
  border-radius: 50%;
  box-shadow: inset 0 0 0 1px hsla(0,0%,0%,.1),
              inset 3px 3px 3px hsla(0,0%,100%,.2),
              inset -3px -3px 3px hsla(0,0%,0%,.2),
              23px 20px 3px hsla(0,0%,0%,.15);
  content: '';
  height: 12px;
  left: -5px;
  position: absolute;
  top: -10px;
  width: 12px;
}

.pin:before {
  background-color: hsla(0,0%,0%,0.1);
  box-shadow: 0 0 .25em hsla(0,0%,0%,.1);
  content: '';
  height: 24px;
  width: 2px;
  left: 0;
  position: absolute;
  top: 8px;
  transform: rotate(57.5deg);
  -moz-transform: rotate(57.5deg);
  -webkit-transform: rotate(57.5deg);
  -o-transform: rotate(57.5deg);
  -ms-transform: rotate(57.5deg);
  transform-origin: 50% 100%;
  -moz-transform-origin: 50% 100%;
  -webkit-transform-origin: 50% 100%;
  -ms-transform-origin: 50% 100%;
  -o-transform-origin: 50% 100%;
}

</style>

<dhv:permission name="gestionepostit_regione-add">
<center>
<a href="MyCFS.do?command=PostItModifica&tipo=2" class="blue">Modifica</a>
</center>
</dhv:permission>

<!--
Code by Creative Punch
 http://creative-punch.net/2014/02/create-css3-post-it-note/#comments
Create a CSS3 post-it note without images
-->
<div class="quote-container">
      <i class="pin"></i>
  <blockquote class="note blue">
   <%=(postIt.getMessaggio()!=null) ? postIt.getMessaggio()  : "" %>
   
    <cite class="author">
    <% if (postIt.getMessaggio()!=null && !postIt.getMessaggio().equals("")) {%>
   REGIONE
    <%} %>
    </cite>
    
  </blockquote>

</div>

