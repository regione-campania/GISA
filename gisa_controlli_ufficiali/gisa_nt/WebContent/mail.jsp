<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mycfs.base.Mail"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@page import="javax.mail.MessagingException"%>
<%@page import="javax.mail.internet.AddressException"%>
<%@page import="org.aspcfs.modules.suap.base.PecMailSender"%>
<%

Mail mail = new Mail();


mail.setHost("127.0.0.1");
mail.setFrom("segnalazioni@gisasegn.it");
mail.setUser("segnalazioni@gisasegn.it");
mail.setPass("");
mail.setPort(25);
mail.setRispondiA("s.squitieri@u-s.it");

//mail.setType("text/html");

mail.setTo("gisadev@u-s.it");
mail.setSogg(" [GISA] ");
String asl_rif = "";


HashMap<String, String> map = new HashMap<String, String>();

mail.setTesto("Prova");

mail.sendMail();

%>