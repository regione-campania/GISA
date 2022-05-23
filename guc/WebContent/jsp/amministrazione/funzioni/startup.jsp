<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="r" />

<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="MAIN" r="r" />
<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="ADD" r="r" />
<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="CAMBIO PASSWORD" r="r" />
<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="CAMBIO RUOLO" r="r" />
<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="DELETE" r="r" />
<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="EDIT" r="r" />
<us:can f="AMMINISTRAZIONE" sf="UTENTI" og="ASSOCIAZIONE CLINICA" r="w" />

<us:can f="AMMINISTRAZIONE" sf="FUNZIONI" og="MAIN" r="r" />
<us:can f="AMMINISTRAZIONE" sf="FUNZIONI" og="PERMISSION LIST" r="r" />
<us:can f="AMMINISTRAZIONE" sf="FUNZIONI" og="ANAGRAFA" r="w" />

<us:can f="AMMINISTRAZIONE" sf="RUOLI" og="MAIN" r="r" />
<us:can f="AMMINISTRAZIONE" sf="RUOLI" og="ADD" r="r" />
<us:can f="AMMINISTRAZIONE" sf="RUOLI" og="DELETE" r="r" />
<us:can f="AMMINISTRAZIONE" sf="RUOLI" og="EDIT" r="r" />
<us:can f="AMMINISTRAZIONE" sf="RUOLI" og="PERMISSION EDIT" r="r" />

<!-- GESTIONE CLINICA -->
<us:can f="CLINICA" sf="MAIN" og="MAIN" r="w" />
<us:can f="CLINICA" sf="ADD" og="MAIN" r="w" />
<us:can f="CLINICA" sf="DETAIL" og="MAIN" r="w" />
<us:can f="CLINICA" sf="DELETE" og="MAIN" r="w" />
<us:can f="CLINICA" sf="LIST" og="MAIN" r="w" />
<us:can f="CLINICA" sf="EDIT" og="MAIN" r="w" />

<!-- GESTIONE ACCETTAZIONE -->
<us:can f="ACCETTAZIONE" sf="MAIN" og="MAIN" r="w" />
<us:can f="ACCETTAZIONE" sf="ADD" og="MAIN" r="w" />
<us:can f="ACCETTAZIONE" sf="DETAIL" og="MAIN" r="w" />
<us:can f="ACCETTAZIONE" sf="DELETE" og="MAIN" r="w" />
<us:can f="ACCETTAZIONE" sf="LIST" og="MAIN" r="w" />
<us:can f="ACCETTAZIONE" sf="EDIT" og="MAIN" r="w" />

<!-- GESTIONE BDR -->
<us:can f="BDR" sf="MAIN" og="MAIN" r="w" />
<us:can f="BDR" sf="ADD" og="MAIN" r="w" />
<us:can f="BDR" sf="DETAIL" og="MAIN" r="w" />
<us:can f="BDR" sf="DELETE" og="MAIN" r="w" />
<us:can f="BDR" sf="LIST" og="MAIN" r="w" />
<us:can f="BDR" sf="EDIT" og="MAIN" r="w" />

<!-- GESTIONE CC -->
<us:can f="CC" sf="MAIN" og="MAIN" r="w" />
<us:can f="CC" sf="ADD" og="MAIN" r="w" />
<us:can f="CC" sf="DETAIL" og="MAIN" r="w" />
<us:can f="CC" sf="DELETE" og="MAIN" r="w" />
<us:can f="CC" sf="LIST" og="MAIN" r="w" />
<us:can f="CC" sf="EDIT" og="MAIN" r="w" />

<!-- GESTIONE TRSFERIMENTI -->
<us:can f="TRASFERIMENTI" sf="MAIN" og="MAIN" r="w" />
<us:can f="TRASFERIMENTI" sf="ADD" og="MAIN" r="w" />
<us:can f="TRASFERIMENTI" sf="DETAIL" og="MAIN" r="w" />
<us:can f="TRASFERIMENTI" sf="DELETE" og="MAIN" r="w" />
<us:can f="TRASFERIMENTI" sf="LIST" og="MAIN" r="w" />
<us:can f="TRASFERIMENTI" sf="EDIT" og="MAIN" r="w" />

<!-- GESTIONE AGENDA -->
<us:can f="AGENDA" sf="MAIN" og="MAIN" r="w" />
<us:can f="AGENDA" sf="ADD" og="MAIN" r="w" />
<us:can f="AGENDA" sf="DETAIL" og="MAIN" r="w" />
<us:can f="AGENDA" sf="DELETE" og="MAIN" r="w" />
<us:can f="AGENDA" sf="LIST" og="MAIN" r="w" />
<us:can f="AGENDA" sf="EDIT" og="MAIN" r="w" />

<!-- GESTIONE PERSONALE -->
<us:can f="PERSONALE" sf="MAIN" og="MAIN" r="w" />
<us:can f="PERSONALE" sf="ADD" og="MAIN" r="w" />
<us:can f="PERSONALE" sf="DETAIL" og="MAIN" r="w" />
<us:can f="PERSONALE" sf="DELETE" og="MAIN" r="w" />
<us:can f="PERSONALE" sf="LIST" og="MAIN" r="w" />
<us:can f="PERSONALE" sf="EDIT" og="MAIN" r="w" />


<!-- GESTIONE MAGAZZINO-->
<us:can f="MAGAZZINO" sf="MAIN" og="MAIN" r="w" />
<us:can f="MAGAZZINO" sf="ADD" og="FARMACI" r="w" />
<us:can f="MAGAZZINO" sf="DETAIL" og="FARMACI" r="w" />
<us:can f="MAGAZZINO" sf="DELETE" og="FARMACI" r="w" />
<us:can f="MAGAZZINO" sf="LIST" og="FARMACI" r="w" />
<us:can f="MAGAZZINO" sf="EDIT" og="FARMACI" r="w" />
<us:can f="MAGAZZINO" sf="ADD" og="AS" r="w" />
<us:can f="MAGAZZINO" sf="DETAIL" og="AS" r="w" />
<us:can f="MAGAZZINO" sf="DELETE" og="AS" r="w" />
<us:can f="MAGAZZINO" sf="LIST" og="AS" r="w" />
<us:can f="MAGAZZINO" sf="EDIT" og="AS" r="w" />
<us:can f="MAGAZZINO" sf="ADD" og="MANGIMI" r="w" />
<us:can f="MAGAZZINO" sf="DETAIL" og="MANGIMI" r="w" />
<us:can f="MAGAZZINO" sf="DELETE" og="MANGIMI" r="w" />
<us:can f="MAGAZZINO" sf="LIST" og="MANGIMI" r="w" />
<us:can f="MAGAZZINO" sf="EDIT" og="MANGIMI" r="w" />


<!-- GESTIONE HELP DESK -->
<us:can f="HD" sf="MAIN" og="MAIN" r="w" />
<us:can f="HD" sf="ADD" og="MAIN" r="w" />
<us:can f="HD" sf="DETAIL" og="MAIN" r="w" />
<us:can f="HD" sf="DELETE" og="MAIN" r="w" />
<us:can f="HD" sf="LIST" og="MAIN" r="w" />
<us:can f="HD" sf="EDIT" og="MAIN" r="w" />


<!-- GESTIONE STATISTICHE E REPORT -->
<us:can f="REPORT" sf="MAIN" og="MAIN" r="r" />

<!-- GESTIONE TESTING -->
<us:can f="TESTING" sf="MAIN" og="MAIN" r="w" />
<us:can f="TESTING" sf="ADD" og="MAIN" r="w" />
<us:can f="TESTING" sf="DETAIL" og="MAIN" r="w" />
<us:can f="TESTING" sf="DELETE" og="MAIN" r="w" />
<us:can f="TESTING" sf="LIST" og="MAIN" r="w" />
<us:can f="TESTING" sf="EDIT" og="MAIN" r="w" />


<p>
Funzioni anagrafate con successo
</p>
