<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*" %>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="org.exolab.castor.jdo.oql.NoMoreTokensException"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.ReflectionUtil"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Partita"%>
<%@page import="com.darkhorseventures.database.ConnectionPool"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Esito"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.MacelliUtil"%>
<%@page import="java.util.ArrayList"%> 

<%@page import="org.aspcfs.utils.DateUtils"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.PatologiaRilevata"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Organi"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.modules.mu_wkf.base.*" %>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<jsp:useBean id="seduta"				class="org.aspcfs.modules.mu.base.SedutaUnivoca"				scope="request" />
<jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.stabilimenti.base.Organization"	scope="request" />
<jsp:useBean id="Speditore" class="org.aspcfs.modules.speditori.base.Organization" 	scope="request" />
<jsp:useBean id="SpeditoreAddress" class="org.aspcfs.modules.speditori.base.OrganizationAddress" 	scope="request" />
<!--<jsp:useBean id="Campione"			class="org.aspcfs.modules.macellazioninew.base.Campione"		scope="request" />-->
<!--<jsp:useBean id="Organo"			class="org.aspcfs.modules.macellazioninew.base.Organi"			scope="request" />-->

<jsp:useBean id="NCVAM"				class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="OrganiList" 		class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="OrganiListNew" 	class="java.util.TreeMap"				scope="request" />
<jsp:useBean id="PatologieRilevate"	class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="Esiti"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="EsitiId"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="CategorieId"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="Provvedimenti"		class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="casl_NCRilevate"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="casl_Provvedimenti_effettuati"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="Nazioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Matrici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ASL"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LuoghiVerifica"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Regioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Razze"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Specie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBovine"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiRisanamento"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="BseList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiMacellazione"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiVpm"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Patologie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PatologieOrgani"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiEsame"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Stadi"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Organi"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiAnalisi"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Molecole"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviASL"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvvedimentiVAM"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="look_ProvvedimentiCASL"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Veterinari"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiCampioni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviCampioni"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecolePNR"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleBatteriologico"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleParassitologico"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita_Gruppo"	class="org.aspcfs.utils.web.LookupList" scope="request" />


<jsp:useBean id="lookup_lesione_generici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_altro"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_milza"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_cuore"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_polmoni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_visceri"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_fegato"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_rene"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_mammella"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_apparato_genitale"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_stomaco"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_intestino"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_osteomuscolari"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="esitoVisitaAm"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User"		class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="dettaglioCapo" class="org.aspcfs.modules.mu.base.CapoUnivoco" scope="request" />

<%@ include file="../../initPage.jsp"%>




