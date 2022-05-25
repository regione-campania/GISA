<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="JavaScript" TYPE="text/javascript" SRC="../javascript/gestoreCodiceFiscale.js"></script>
<form id="calcolocf" name="calcolocf" action="" method="post">
<table>
<tr>
<th>Nome</th>
<th>Cognome</th>
<th>Data di nascita</th>
<th>Sesso</th>
<th>Comune nascita</th>
<th>Codice fiscale</th>
<th></th>
</tr>

<tr>
<td><input type="text" id="nome_1178729" name="nome_1178729" value="silvana"/></td> 
<td><input type="text" id="cognome_1178729" name="cognome_1178729" value="municino'"/></td> 
<td><input type="text" id="data_1178729" name="data_1178729" value="12/09/1966"/></td> 
<td><input type="radio" id="sessom_1178729" name="sesso_1178729" value="M"/> M
<input type="radio" id="sessof_1178729" name="sesso_1178729" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1178729" name="comune_1178729" value="portici"/></td>
<td><input type="text" id="cf_1178729" name="cf_1178729" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178729,document.forms[0].nome_1178729,document.forms[0].cognome_1178729,document.forms[0].comune_1178729,document.forms[0].data_1178729,'cf_1178729')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1174990" name="nome_1174990" value="guido "/></td> 
<td><input type="text" id="cognome_1174990" name="cognome_1174990" value="rapanà"/></td> 
<td><input type="text" id="data_1174990" name="data_1174990" value="26/03/1969"/></td> 
<td><input type="radio" id="sessom_1174990" name="sesso_1174990" value="M" checked/> M
<input type="radio" id="sessof_1174990" name="sesso_1174990" value="F"/> F
</td>  
<td><input type="text" id="comune_1174990" name="comune_1174990" value="napoli"/></td>
<td><input type="text" id="cf_1174990" name="cf_1174990" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1174990,document.forms[0].nome_1174990,document.forms[0].cognome_1174990,document.forms[0].comune_1174990,document.forms[0].data_1174990,'cf_1174990')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173709" name="nome_1173709" value="giovanni"/></td> 
<td><input type="text" id="cognome_1173709" name="cognome_1173709" value="russo"/></td> 
<td><input type="text" id="data_1173709" name="data_1173709" value="29/01/1971"/></td> 
<td><input type="radio" id="sessom_1173709" name="sesso_1173709" value="M" checked/> M
<input type="radio" id="sessof_1173709" name="sesso_1173709" value="F"/> F
</td>  
<td><input type="text" id="comune_1173709" name="comune_1173709" value="portici"/></td>
<td><input type="text" id="cf_1173709" name="cf_1173709" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173709,document.forms[0].nome_1173709,document.forms[0].cognome_1173709,document.forms[0].comune_1173709,document.forms[0].data_1173709,'cf_1173709')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1170670" name="nome_1170670" value="pierluigi"/></td> 
<td><input type="text" id="cognome_1170670" name="cognome_1170670" value="grimaldi"/></td> 
<td><input type="text" id="data_1170670" name="data_1170670" value="27/07/1967"/></td> 
<td><input type="radio" id="sessom_1170670" name="sesso_1170670" value="M" checked/> M
<input type="radio" id="sessof_1170670" name="sesso_1170670" value="F"/> F
</td>  
<td><input type="text" id="comune_1170670" name="comune_1170670" value="salerno"/></td>
<td><input type="text" id="cf_1170670" name="cf_1170670" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1170670,document.forms[0].nome_1170670,document.forms[0].cognome_1170670,document.forms[0].comune_1170670,document.forms[0].data_1170670,'cf_1170670')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169906" name="nome_1169906" value="emilio"/></td> 
<td><input type="text" id="cognome_1169906" name="cognome_1169906" value="martellini"/></td> 
<td><input type="text" id="data_1169906" name="data_1169906" value="27/05/1961"/></td> 
<td><input type="radio" id="sessom_1169906" name="sesso_1169906" value="M" checked/> M
<input type="radio" id="sessof_1169906" name="sesso_1169906" value="F"/> F
</td>  
<td><input type="text" id="comune_1169906" name="comune_1169906" value="caserta"/></td>
<td><input type="text" id="cf_1169906" name="cf_1169906" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169906,document.forms[0].nome_1169906,document.forms[0].cognome_1169906,document.forms[0].comune_1169906,document.forms[0].data_1169906,'cf_1169906')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192034" name="nome_1192034" value="paolo "/></td> 
<td><input type="text" id="cognome_1192034" name="cognome_1192034" value="arenare"/></td> 
<td><input type="text" id="data_1192034" name="data_1192034" value="04/10/1982"/></td> 
<td><input type="radio" id="sessom_1192034" name="sesso_1192034" value="M" checked/> M
<input type="radio" id="sessof_1192034" name="sesso_1192034" value="F"/> F
</td>  
<td><input type="text" id="comune_1192034" name="comune_1192034" value="polla"/></td>
<td><input type="text" id="cf_1192034" name="cf_1192034" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192034,document.forms[0].nome_1192034,document.forms[0].cognome_1192034,document.forms[0].comune_1192034,document.forms[0].data_1192034,'cf_1192034')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169025" name="nome_1169025" value="antonello"/></td> 
<td><input type="text" id="cognome_1169025" name="cognome_1169025" value="mirone"/></td> 
<td><input type="text" id="data_1169025" name="data_1169025" value="14/01/1960"/></td> 
<td><input type="radio" id="sessom_1169025" name="sesso_1169025" value="M" checked/> M
<input type="radio" id="sessof_1169025" name="sesso_1169025" value="F"/> F
</td>  
<td><input type="text" id="comune_1169025" name="comune_1169025" value="napoli"/></td>
<td><input type="text" id="cf_1169025" name="cf_1169025" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169025,document.forms[0].nome_1169025,document.forms[0].cognome_1169025,document.forms[0].comune_1169025,document.forms[0].data_1169025,'cf_1169025')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1045964" name="nome_1045964" value="ANTONIO"/></td> 
<td><input type="text" id="cognome_1045964" name="cognome_1045964" value="COSTABILE"/></td> 
<td><input type="text" id="data_1045964" name="data_1045964" value="25/09/1953"/></td> 
<td><input type="radio" id="sessom_1045964" name="sesso_1045964" value="M" checked/> M
<input type="radio" id="sessof_1045964" name="sesso_1045964" value="F"/> F
</td>  
<td><input type="text" id="comune_1045964" name="comune_1045964" value="SALERNO"/></td>
<td><input type="text" id="cf_1045964" name="cf_1045964" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1045964,document.forms[0].nome_1045964,document.forms[0].cognome_1045964,document.forms[0].comune_1045964,document.forms[0].data_1045964,'cf_1045964')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042340" name="nome_1042340" value="emanuela"/></td> 
<td><input type="text" id="cognome_1042340" name="cognome_1042340" value="pavese"/></td> 
<td><input type="text" id="data_1042340" name="data_1042340" value="05/10/1958"/></td> 
<td><input type="radio" id="sessom_1042340" name="sesso_1042340" value="M"/> M
<input type="radio" id="sessof_1042340" name="sesso_1042340" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1042340" name="comune_1042340" value="avellino"/></td>
<td><input type="text" id="cf_1042340" name="cf_1042340" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042340,document.forms[0].nome_1042340,document.forms[0].cognome_1042340,document.forms[0].comune_1042340,document.forms[0].data_1042340,'cf_1042340')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1002853" name="nome_1002853" value="fabio"/></td> 
<td><input type="text" id="cognome_1002853" name="cognome_1002853" value="de pasquale"/></td> 
<td><input type="text" id="data_1002853" name="data_1002853" value="06/07/1967"/></td> 
<td><input type="radio" id="sessom_1002853" name="sesso_1002853" value="M" checked/> M
<input type="radio" id="sessof_1002853" name="sesso_1002853" value="F"/> F
</td>  
<td><input type="text" id="comune_1002853" name="comune_1002853" value="napoli"/></td>
<td><input type="text" id="cf_1002853" name="cf_1002853" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1002853,document.forms[0].nome_1002853,document.forms[0].cognome_1002853,document.forms[0].comune_1002853,document.forms[0].data_1002853,'cf_1002853')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1043669" name="nome_1043669" value="teresa"/></td> 
<td><input type="text" id="cognome_1043669" name="cognome_1043669" value="cauceglia"/></td> 
<td><input type="text" id="data_1043669" name="data_1043669" value="26/02/1959"/></td> 
<td><input type="radio" id="sessom_1043669" name="sesso_1043669" value="M"/> M
<input type="radio" id="sessof_1043669" name="sesso_1043669" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1043669" name="comune_1043669" value="vallo della lucania (SA)"/></td>
<td><input type="text" id="cf_1043669" name="cf_1043669" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1043669,document.forms[0].nome_1043669,document.forms[0].cognome_1043669,document.forms[0].comune_1043669,document.forms[0].data_1043669,'cf_1043669')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185805" name="nome_1185805" value="alessandro"/></td> 
<td><input type="text" id="cognome_1185805" name="cognome_1185805" value="grilli"/></td> 
<td><input type="text" id="data_1185805" name="data_1185805" value="10/09/1968"/></td> 
<td><input type="radio" id="sessom_1185805" name="sesso_1185805" value="M" checked/> M
<input type="radio" id="sessof_1185805" name="sesso_1185805" value="F"/> F
</td>  
<td><input type="text" id="comune_1185805" name="comune_1185805" value="napoli"/></td>
<td><input type="text" id="cf_1185805" name="cf_1185805" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185805,document.forms[0].nome_1185805,document.forms[0].cognome_1185805,document.forms[0].comune_1185805,document.forms[0].data_1185805,'cf_1185805')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041682" name="nome_1041682" value="RAFFAELE"/></td> 
<td><input type="text" id="cognome_1041682" name="cognome_1041682" value="DI STASIO"/></td> 
<td><input type="text" id="data_1041682" name="data_1041682" value="30/04/1969"/></td> 
<td><input type="radio" id="sessom_1041682" name="sesso_1041682" value="M" checked/> M
<input type="radio" id="sessof_1041682" name="sesso_1041682" value="F"/> F
</td>  
<td><input type="text" id="comune_1041682" name="comune_1041682" value="NAPOLI"/></td>
<td><input type="text" id="cf_1041682" name="cf_1041682" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041682,document.forms[0].nome_1041682,document.forms[0].cognome_1041682,document.forms[0].comune_1041682,document.forms[0].data_1041682,'cf_1041682')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164003" name="nome_1164003" value="federico"/></td> 
<td><input type="text" id="cognome_1164003" name="cognome_1164003" value="la rana"/></td> 
<td><input type="text" id="data_1164003" name="data_1164003" value="15/06/1940"/></td> 
<td><input type="radio" id="sessom_1164003" name="sesso_1164003" value="M" checked/> M
<input type="radio" id="sessof_1164003" name="sesso_1164003" value="F"/> F
</td>  
<td><input type="text" id="comune_1164003" name="comune_1164003" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164003" name="cf_1164003" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164003,document.forms[0].nome_1164003,document.forms[0].cognome_1164003,document.forms[0].comune_1164003,document.forms[0].data_1164003,'cf_1164003')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172341" name="nome_1172341" value="ENRICHETTA"/></td> 
<td><input type="text" id="cognome_1172341" name="cognome_1172341" value="LOMBARDI"/></td> 
<td><input type="text" id="data_1172341" name="data_1172341" value="23/12/1959"/></td> 
<td><input type="radio" id="sessom_1172341" name="sesso_1172341" value="M"/> M
<input type="radio" id="sessof_1172341" name="sesso_1172341" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1172341" name="comune_1172341" value="cerro al volturno"/></td>
<td><input type="text" id="cf_1172341" name="cf_1172341" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172341,document.forms[0].nome_1172341,document.forms[0].cognome_1172341,document.forms[0].comune_1172341,document.forms[0].data_1172341,'cf_1172341')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178246" name="nome_1178246" value="CONSOLATA MARIA"/></td> 
<td><input type="text" id="cognome_1178246" name="cognome_1178246" value="DE ANSERIS"/></td> 
<td><input type="text" id="data_1178246" name="data_1178246" value="01/08/1955"/></td> 
<td><input type="radio" id="sessom_1178246" name="sesso_1178246" value="M"/> M
<input type="radio" id="sessof_1178246" name="sesso_1178246" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1178246" name="comune_1178246" value="SAN SOSSIO BARONIA (AV)"/></td>
<td><input type="text" id="cf_1178246" name="cf_1178246" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178246,document.forms[0].nome_1178246,document.forms[0].cognome_1178246,document.forms[0].comune_1178246,document.forms[0].data_1178246,'cf_1178246')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1049064" name="nome_1049064" value="clemente "/></td> 
<td><input type="text" id="cognome_1049064" name="cognome_1049064" value="guida"/></td> 
<td><input type="text" id="data_1049064" name="data_1049064" value="09/04/1965"/></td> 
<td><input type="radio" id="sessom_1049064" name="sesso_1049064" value="M" checked/> M
<input type="radio" id="sessof_1049064" name="sesso_1049064" value="F"/> F
</td>  
<td><input type="text" id="comune_1049064" name="comune_1049064" value="caserta"/></td>
<td><input type="text" id="cf_1049064" name="cf_1049064" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1049064,document.forms[0].nome_1049064,document.forms[0].cognome_1049064,document.forms[0].comune_1049064,document.forms[0].data_1049064,'cf_1049064')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1174989" name="nome_1174989" value="pietro"/></td> 
<td><input type="text" id="cognome_1174989" name="cognome_1174989" value="daniele"/></td> 
<td><input type="text" id="data_1174989" name="data_1174989" value="31/05/1960"/></td> 
<td><input type="radio" id="sessom_1174989" name="sesso_1174989" value="M" checked/> M
<input type="radio" id="sessof_1174989" name="sesso_1174989" value="F"/> F
</td>  
<td><input type="text" id="comune_1174989" name="comune_1174989" value="NAPOLI"/></td>
<td><input type="text" id="cf_1174989" name="cf_1174989" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1174989,document.forms[0].nome_1174989,document.forms[0].cognome_1174989,document.forms[0].comune_1174989,document.forms[0].data_1174989,'cf_1174989')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1184949" name="nome_1184949" value="teresa"/></td> 
<td><input type="text" id="cognome_1184949" name="cognome_1184949" value="ventricelli"/></td> 
<td><input type="text" id="data_1184949" name="data_1184949" value="14/10/1965"/></td> 
<td><input type="radio" id="sessom_1184949" name="sesso_1184949" value="M"/> M
<input type="radio" id="sessof_1184949" name="sesso_1184949" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1184949" name="comune_1184949" value="laviano"/></td>
<td><input type="text" id="cf_1184949" name="cf_1184949" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184949,document.forms[0].nome_1184949,document.forms[0].cognome_1184949,document.forms[0].comune_1184949,document.forms[0].data_1184949,'cf_1184949')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194837" name="nome_1194837" value="pasquale manuele"/></td> 
<td><input type="text" id="cognome_1194837" name="cognome_1194837" value="del prete"/></td> 
<td><input type="text" id="data_1194837" name="data_1194837" value="07/03/1989"/></td> 
<td><input type="radio" id="sessom_1194837" name="sesso_1194837" value="M" checked/> M
<input type="radio" id="sessof_1194837" name="sesso_1194837" value="F"/> F
</td>  
<td><input type="text" id="comune_1194837" name="comune_1194837" value="napoli"/></td>
<td><input type="text" id="cf_1194837" name="cf_1194837" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194837,document.forms[0].nome_1194837,document.forms[0].cognome_1194837,document.forms[0].comune_1194837,document.forms[0].data_1194837,'cf_1194837')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1188757" name="nome_1188757" value="luca"/></td> 
<td><input type="text" id="cognome_1188757" name="cognome_1188757" value="muto"/></td> 
<td><input type="text" id="data_1188757" name="data_1188757" value="23/01/1971"/></td> 
<td><input type="radio" id="sessom_1188757" name="sesso_1188757" value="M" checked/> M
<input type="radio" id="sessof_1188757" name="sesso_1188757" value="F"/> F
</td>  
<td><input type="text" id="comune_1188757" name="comune_1188757" value=""/></td>
<td><input type="text" id="cf_1188757" name="cf_1188757" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1188757,document.forms[0].nome_1188757,document.forms[0].cognome_1188757,document.forms[0].comune_1188757,document.forms[0].data_1188757,'cf_1188757')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1066586" name="nome_1066586" value="giusy"/></td> 
<td><input type="text" id="cognome_1066586" name="cognome_1066586" value="pantalena"/></td> 
<td><input type="text" id="data_1066586" name="data_1066586" value="08/10/1982"/></td> 
<td><input type="radio" id="sessom_1066586" name="sesso_1066586" value="M"/> M
<input type="radio" id="sessof_1066586" name="sesso_1066586" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1066586" name="comune_1066586" value="san giorgio a cremano"/></td>
<td><input type="text" id="cf_1066586" name="cf_1066586" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1066586,document.forms[0].nome_1066586,document.forms[0].cognome_1066586,document.forms[0].comune_1066586,document.forms[0].data_1066586,'cf_1066586')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168868" name="nome_1168868" value="ivana"/></td> 
<td><input type="text" id="cognome_1168868" name="cognome_1168868" value="borrelli"/></td> 
<td><input type="text" id="data_1168868" name="data_1168868" value="10/11/1986"/></td> 
<td><input type="radio" id="sessom_1168868" name="sesso_1168868" value="M"/> M
<input type="radio" id="sessof_1168868" name="sesso_1168868" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1168868" name="comune_1168868" value="torre del greco"/></td>
<td><input type="text" id="cf_1168868" name="cf_1168868" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168868,document.forms[0].nome_1168868,document.forms[0].cognome_1168868,document.forms[0].comune_1168868,document.forms[0].data_1168868,'cf_1168868')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181552" name="nome_1181552" value="giacomo"/></td> 
<td><input type="text" id="cognome_1181552" name="cognome_1181552" value="cozzolino"/></td> 
<td><input type="text" id="data_1181552" name="data_1181552" value="30/07/1957"/></td> 
<td><input type="radio" id="sessom_1181552" name="sesso_1181552" value="M" checked/> M
<input type="radio" id="sessof_1181552" name="sesso_1181552" value="F"/> F
</td>  
<td><input type="text" id="comune_1181552" name="comune_1181552" value="torre del greco"/></td>
<td><input type="text" id="cf_1181552" name="cf_1181552" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181552,document.forms[0].nome_1181552,document.forms[0].cognome_1181552,document.forms[0].comune_1181552,document.forms[0].data_1181552,'cf_1181552')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1019797" name="nome_1019797" value="vespasiano"/></td> 
<td><input type="text" id="cognome_1019797" name="cognome_1019797" value="villano"/></td> 
<td><input type="text" id="data_1019797" name="data_1019797" value="07/12/1943"/></td> 
<td><input type="radio" id="sessom_1019797" name="sesso_1019797" value="M"/> M
<input type="radio" id="sessof_1019797" name="sesso_1019797" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1019797" name="comune_1019797" value="cesa(ce)"/></td>
<td><input type="text" id="cf_1019797" name="cf_1019797" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1019797,document.forms[0].nome_1019797,document.forms[0].cognome_1019797,document.forms[0].comune_1019797,document.forms[0].data_1019797,'cf_1019797')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181562" name="nome_1181562" value="adriana"/></td> 
<td><input type="text" id="cognome_1181562" name="cognome_1181562" value="greco"/></td> 
<td><input type="text" id="data_1181562" name="data_1181562" value="22/02/1932"/></td> 
<td><input type="radio" id="sessom_1181562" name="sesso_1181562" value="M"/> M
<input type="radio" id="sessof_1181562" name="sesso_1181562" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181562" name="comune_1181562" value="gimignano"/></td>
<td><input type="text" id="cf_1181562" name="cf_1181562" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181562,document.forms[0].nome_1181562,document.forms[0].cognome_1181562,document.forms[0].comune_1181562,document.forms[0].data_1181562,'cf_1181562')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1062310" name="nome_1062310" value="carmela"/></td> 
<td><input type="text" id="cognome_1062310" name="cognome_1062310" value="d'angiÃ²"/></td> 
<td><input type="text" id="data_1062310" name="data_1062310" value="28/06/1985"/></td> 
<td><input type="radio" id="sessom_1062310" name="sesso_1062310" value="M"/> M
<input type="radio" id="sessof_1062310" name="sesso_1062310" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1062310" name="comune_1062310" value="napoli"/></td>
<td><input type="text" id="cf_1062310" name="cf_1062310" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1062310,document.forms[0].nome_1062310,document.forms[0].cognome_1062310,document.forms[0].comune_1062310,document.forms[0].data_1062310,'cf_1062310')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167468" name="nome_1167468" value="aurelia"/></td> 
<td><input type="text" id="cognome_1167468" name="cognome_1167468" value="pisapia"/></td> 
<td><input type="text" id="data_1167468" name="data_1167468" value="30/05/1953"/></td> 
<td><input type="radio" id="sessom_1167468" name="sesso_1167468" value="M"/> M
<input type="radio" id="sessof_1167468" name="sesso_1167468" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1167468" name="comune_1167468" value="napoli"/></td>
<td><input type="text" id="cf_1167468" name="cf_1167468" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167468,document.forms[0].nome_1167468,document.forms[0].cognome_1167468,document.forms[0].comune_1167468,document.forms[0].data_1167468,'cf_1167468')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192046" name="nome_1192046" value="antonio"/></td> 
<td><input type="text" id="cognome_1192046" name="cognome_1192046" value="sacco"/></td> 
<td><input type="text" id="data_1192046" name="data_1192046" value="07/11/1961"/></td> 
<td><input type="radio" id="sessom_1192046" name="sesso_1192046" value="M" checked/> M
<input type="radio" id="sessof_1192046" name="sesso_1192046" value="F"/> F
</td>  
<td><input type="text" id="comune_1192046" name="comune_1192046" value="salerno"/></td>
<td><input type="text" id="cf_1192046" name="cf_1192046" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192046,document.forms[0].nome_1192046,document.forms[0].cognome_1192046,document.forms[0].comune_1192046,document.forms[0].data_1192046,'cf_1192046')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041468" name="nome_1041468" value="paolo"/></td> 
<td><input type="text" id="cognome_1041468" name="cognome_1041468" value="aiello"/></td> 
<td><input type="text" id="data_1041468" name="data_1041468" value="10/05/1978"/></td> 
<td><input type="radio" id="sessom_1041468" name="sesso_1041468" value="M" checked/> M
<input type="radio" id="sessof_1041468" name="sesso_1041468" value="F"/> F
</td>  
<td><input type="text" id="comune_1041468" name="comune_1041468" value="salerno"/></td>
<td><input type="text" id="cf_1041468" name="cf_1041468" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041468,document.forms[0].nome_1041468,document.forms[0].cognome_1041468,document.forms[0].comune_1041468,document.forms[0].data_1041468,'cf_1041468')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164447" name="nome_1164447" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1164447" name="cognome_1164447" value="simone"/></td> 
<td><input type="text" id="data_1164447" name="data_1164447" value="23/12/1959"/></td> 
<td><input type="radio" id="sessom_1164447" name="sesso_1164447" value="M" checked/> M
<input type="radio" id="sessof_1164447" name="sesso_1164447" value="F"/> F
</td>  
<td><input type="text" id="comune_1164447" name="comune_1164447" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164447" name="cf_1164447" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164447,document.forms[0].nome_1164447,document.forms[0].cognome_1164447,document.forms[0].comune_1164447,document.forms[0].data_1164447,'cf_1164447')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1002699" name="nome_1002699" value="luigi"/></td> 
<td><input type="text" id="cognome_1002699" name="cognome_1002699" value="pastore"/></td> 
<td><input type="text" id="data_1002699" name="data_1002699" value="06/01/1961"/></td> 
<td><input type="radio" id="sessom_1002699" name="sesso_1002699" value="M" checked/> M
<input type="radio" id="sessof_1002699" name="sesso_1002699" value="F"/> F
</td>  
<td><input type="text" id="comune_1002699" name="comune_1002699" value="Salerno"/></td>
<td><input type="text" id="cf_1002699" name="cf_1002699" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1002699,document.forms[0].nome_1002699,document.forms[0].cognome_1002699,document.forms[0].comune_1002699,document.forms[0].data_1002699,'cf_1002699')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193775" name="nome_1193775" value="ANGELA"/></td> 
<td><input type="text" id="cognome_1193775" name="cognome_1193775" value="TOSIELLO"/></td> 
<td><input type="text" id="data_1193775" name="data_1193775" value="10/08/1976"/></td> 
<td><input type="radio" id="sessom_1193775" name="sesso_1193775" value="M"/> M
<input type="radio" id="sessof_1193775" name="sesso_1193775" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1193775" name="comune_1193775" value="VALLE DELL'ANGELO"/></td>
<td><input type="text" id="cf_1193775" name="cf_1193775" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193775,document.forms[0].nome_1193775,document.forms[0].cognome_1193775,document.forms[0].comune_1193775,document.forms[0].data_1193775,'cf_1193775')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1024955" name="nome_1024955" value="leonilda"/></td> 
<td><input type="text" id="cognome_1024955" name="cognome_1024955" value="caputo"/></td> 
<td><input type="text" id="data_1024955" name="data_1024955" value="03/07/1948"/></td> 
<td><input type="radio" id="sessom_1024955" name="sesso_1024955" value="M"/> M
<input type="radio" id="sessof_1024955" name="sesso_1024955" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1024955" name="comune_1024955" value="nocera superiore"/></td>
<td><input type="text" id="cf_1024955" name="cf_1024955" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1024955,document.forms[0].nome_1024955,document.forms[0].cognome_1024955,document.forms[0].comune_1024955,document.forms[0].data_1024955,'cf_1024955')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1002859" name="nome_1002859" value="giovanni"/></td> 
<td><input type="text" id="cognome_1002859" name="cognome_1002859" value="brignola"/></td> 
<td><input type="text" id="data_1002859" name="data_1002859" value="08/08/1968"/></td> 
<td><input type="radio" id="sessom_1002859" name="sesso_1002859" value="M" checked/> M
<input type="radio" id="sessof_1002859" name="sesso_1002859" value="F"/> F
</td>  
<td><input type="text" id="comune_1002859" name="comune_1002859" value="Napoli"/></td>
<td><input type="text" id="cf_1002859" name="cf_1002859" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1002859,document.forms[0].nome_1002859,document.forms[0].cognome_1002859,document.forms[0].comune_1002859,document.forms[0].data_1002859,'cf_1002859')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185545" name="nome_1185545" value="viviana"/></td> 
<td><input type="text" id="cognome_1185545" name="cognome_1185545" value="zomack"/></td> 
<td><input type="text" id="data_1185545" name="data_1185545" value="05/03/1985"/></td> 
<td><input type="radio" id="sessom_1185545" name="sesso_1185545" value="M"/> M
<input type="radio" id="sessof_1185545" name="sesso_1185545" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1185545" name="comune_1185545" value="napoli"/></td>
<td><input type="text" id="cf_1185545" name="cf_1185545" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185545,document.forms[0].nome_1185545,document.forms[0].cognome_1185545,document.forms[0].comune_1185545,document.forms[0].data_1185545,'cf_1185545')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178595" name="nome_1178595" value="alessandro"/></td> 
<td><input type="text" id="cognome_1178595" name="cognome_1178595" value="grilli"/></td> 
<td><input type="text" id="data_1178595" name="data_1178595" value="10/09/1968"/></td> 
<td><input type="radio" id="sessom_1178595" name="sesso_1178595" value="M" checked/> M
<input type="radio" id="sessof_1178595" name="sesso_1178595" value="F"/> F
</td>  
<td><input type="text" id="comune_1178595" name="comune_1178595" value="napoli"/></td>
<td><input type="text" id="cf_1178595" name="cf_1178595" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178595,document.forms[0].nome_1178595,document.forms[0].cognome_1178595,document.forms[0].comune_1178595,document.forms[0].data_1178595,'cf_1178595')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194160" name="nome_1194160" value="michelangelo"/></td> 
<td><input type="text" id="cognome_1194160" name="cognome_1194160" value="sparano"/></td> 
<td><input type="text" id="data_1194160" name="data_1194160" value="22/12/1982"/></td> 
<td><input type="radio" id="sessom_1194160" name="sesso_1194160" value="M" checked/> M
<input type="radio" id="sessof_1194160" name="sesso_1194160" value="F"/> F
</td>  
<td><input type="text" id="comune_1194160" name="comune_1194160" value=""/></td>
<td><input type="text" id="cf_1194160" name="cf_1194160" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194160,document.forms[0].nome_1194160,document.forms[0].cognome_1194160,document.forms[0].comune_1194160,document.forms[0].data_1194160,'cf_1194160')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189869" name="nome_1189869" value="teresa"/></td> 
<td><input type="text" id="cognome_1189869" name="cognome_1189869" value="russo"/></td> 
<td><input type="text" id="data_1189869" name="data_1189869" value="25/06/1955"/></td> 
<td><input type="radio" id="sessom_1189869" name="sesso_1189869" value="M"/> M
<input type="radio" id="sessof_1189869" name="sesso_1189869" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1189869" name="comune_1189869" value="bagnoli irpino"/></td>
<td><input type="text" id="cf_1189869" name="cf_1189869" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189869,document.forms[0].nome_1189869,document.forms[0].cognome_1189869,document.forms[0].comune_1189869,document.forms[0].data_1189869,'cf_1189869')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194229" name="nome_1194229" value="francesco"/></td> 
<td><input type="text" id="cognome_1194229" name="cognome_1194229" value="imbriaco"/></td> 
<td><input type="text" id="data_1194229" name="data_1194229" value="29/09/1983"/></td> 
<td><input type="radio" id="sessom_1194229" name="sesso_1194229" value="M" checked/> M
<input type="radio" id="sessof_1194229" name="sesso_1194229" value="F"/> F
</td>  
<td><input type="text" id="comune_1194229" name="comune_1194229" value="salerno"/></td>
<td><input type="text" id="cf_1194229" name="cf_1194229" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194229,document.forms[0].nome_1194229,document.forms[0].cognome_1194229,document.forms[0].comune_1194229,document.forms[0].data_1194229,'cf_1194229')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164962" name="nome_1164962" value="DOMENICO"/></td> 
<td><input type="text" id="cognome_1164962" name="cognome_1164962" value="DEL VECCHIO"/></td> 
<td><input type="text" id="data_1164962" name="data_1164962" value="21/02/1966"/></td> 
<td><input type="radio" id="sessom_1164962" name="sesso_1164962" value="M" checked/> M
<input type="radio" id="sessof_1164962" name="sesso_1164962" value="F"/> F
</td>  
<td><input type="text" id="comune_1164962" name="comune_1164962" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164962" name="cf_1164962" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164962,document.forms[0].nome_1164962,document.forms[0].cognome_1164962,document.forms[0].comune_1164962,document.forms[0].data_1164962,'cf_1164962')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041870" name="nome_1041870" value="antonio"/></td> 
<td><input type="text" id="cognome_1041870" name="cognome_1041870" value="stabile"/></td> 
<td><input type="text" id="data_1041870" name="data_1041870" value="19/11/1950"/></td> 
<td><input type="radio" id="sessom_1041870" name="sesso_1041870" value="M" checked/> M
<input type="radio" id="sessof_1041870" name="sesso_1041870" value="F"/> F
</td>  
<td><input type="text" id="comune_1041870" name="comune_1041870" value="salerno"/></td>
<td><input type="text" id="cf_1041870" name="cf_1041870" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041870,document.forms[0].nome_1041870,document.forms[0].cognome_1041870,document.forms[0].comune_1041870,document.forms[0].data_1041870,'cf_1041870')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181283" name="nome_1181283" value="bianca"/></td> 
<td><input type="text" id="cognome_1181283" name="cognome_1181283" value="de tommasis"/></td> 
<td><input type="text" id="data_1181283" name="data_1181283" value="09/06/1959"/></td> 
<td><input type="radio" id="sessom_1181283" name="sesso_1181283" value="M"/> M
<input type="radio" id="sessof_1181283" name="sesso_1181283" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181283" name="comune_1181283" value="PIAZZA MUZII, 24/25/26"/></td>
<td><input type="text" id="cf_1181283" name="cf_1181283" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181283,document.forms[0].nome_1181283,document.forms[0].cognome_1181283,document.forms[0].comune_1181283,document.forms[0].data_1181283,'cf_1181283')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041904" name="nome_1041904" value="Domenico"/></td> 
<td><input type="text" id="cognome_1041904" name="cognome_1041904" value="De Angelis"/></td> 
<td><input type="text" id="data_1041904" name="data_1041904" value="25/05/1945"/></td> 
<td><input type="radio" id="sessom_1041904" name="sesso_1041904" value="M" checked/> M
<input type="radio" id="sessof_1041904" name="sesso_1041904" value="F"/> F
</td>  
<td><input type="text" id="comune_1041904" name="comune_1041904" value="Roccapiemonte"/></td>
<td><input type="text" id="cf_1041904" name="cf_1041904" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041904,document.forms[0].nome_1041904,document.forms[0].cognome_1041904,document.forms[0].comune_1041904,document.forms[0].data_1041904,'cf_1041904')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1191844" name="nome_1191844" value="michele "/></td> 
<td><input type="text" id="cognome_1191844" name="cognome_1191844" value="de falco"/></td> 
<td><input type="text" id="data_1191844" name="data_1191844" value="04/08/1971"/></td> 
<td><input type="radio" id="sessom_1191844" name="sesso_1191844" value="M" checked/> M
<input type="radio" id="sessof_1191844" name="sesso_1191844" value="F"/> F
</td>  
<td><input type="text" id="comune_1191844" name="comune_1191844" value="aversa"/></td>
<td><input type="text" id="cf_1191844" name="cf_1191844" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1191844,document.forms[0].nome_1191844,document.forms[0].cognome_1191844,document.forms[0].comune_1191844,document.forms[0].data_1191844,'cf_1191844')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185543" name="nome_1185543" value="raffaele"/></td> 
<td><input type="text" id="cognome_1185543" name="cognome_1185543" value="papa"/></td> 
<td><input type="text" id="data_1185543" name="data_1185543" value="28/03/1986"/></td> 
<td><input type="radio" id="sessom_1185543" name="sesso_1185543" value="M" checked/> M
<input type="radio" id="sessof_1185543" name="sesso_1185543" value="F"/> F
</td>  
<td><input type="text" id="comune_1185543" name="comune_1185543" value="CORSO GARIBALDI n  317"/></td>
<td><input type="text" id="cf_1185543" name="cf_1185543" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185543,document.forms[0].nome_1185543,document.forms[0].cognome_1185543,document.forms[0].comune_1185543,document.forms[0].data_1185543,'cf_1185543')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186927" name="nome_1186927" value="carlo"/></td> 
<td><input type="text" id="cognome_1186927" name="cognome_1186927" value="pellegrino"/></td> 
<td><input type="text" id="data_1186927" name="data_1186927" value="23/08/1987"/></td> 
<td><input type="radio" id="sessom_1186927" name="sesso_1186927" value="M" checked/> M
<input type="radio" id="sessof_1186927" name="sesso_1186927" value="F"/> F
</td>  
<td><input type="text" id="comune_1186927" name="comune_1186927" value="nocera inferiore"/></td>
<td><input type="text" id="cf_1186927" name="cf_1186927" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186927,document.forms[0].nome_1186927,document.forms[0].cognome_1186927,document.forms[0].comune_1186927,document.forms[0].data_1186927,'cf_1186927')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1188923" name="nome_1188923" value="antonio"/></td> 
<td><input type="text" id="cognome_1188923" name="cognome_1188923" value="zaccariello"/></td> 
<td><input type="text" id="data_1188923" name="data_1188923" value="19/10/1958"/></td> 
<td><input type="radio" id="sessom_1188923" name="sesso_1188923" value="M" checked/> M
<input type="radio" id="sessof_1188923" name="sesso_1188923" value="F"/> F
</td>  
<td><input type="text" id="comune_1188923" name="comune_1188923" value="san marcellino"/></td>
<td><input type="text" id="cf_1188923" name="cf_1188923" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1188923,document.forms[0].nome_1188923,document.forms[0].cognome_1188923,document.forms[0].comune_1188923,document.forms[0].data_1188923,'cf_1188923')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181254" name="nome_1181254" value="lea"/></td> 
<td><input type="text" id="cognome_1181254" name="cognome_1181254" value="miranda"/></td> 
<td><input type="text" id="data_1181254" name="data_1181254" value="19/10/1977"/></td> 
<td><input type="radio" id="sessom_1181254" name="sesso_1181254" value="M"/> M
<input type="radio" id="sessof_1181254" name="sesso_1181254" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181254" name="comune_1181254" value="castellammare di stabia"/></td>
<td><input type="text" id="cf_1181254" name="cf_1181254" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181254,document.forms[0].nome_1181254,document.forms[0].cognome_1181254,document.forms[0].comune_1181254,document.forms[0].data_1181254,'cf_1181254')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192103" name="nome_1192103" value="antonio "/></td> 
<td><input type="text" id="cognome_1192103" name="cognome_1192103" value="speranza"/></td> 
<td><input type="text" id="data_1192103" name="data_1192103" value="18/01/1977"/></td> 
<td><input type="radio" id="sessom_1192103" name="sesso_1192103" value="M" checked/> M
<input type="radio" id="sessof_1192103" name="sesso_1192103" value="F"/> F
</td>  
<td><input type="text" id="comune_1192103" name="comune_1192103" value="vallo della lucania"/></td>
<td><input type="text" id="cf_1192103" name="cf_1192103" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192103,document.forms[0].nome_1192103,document.forms[0].cognome_1192103,document.forms[0].comune_1192103,document.forms[0].data_1192103,'cf_1192103')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164446" name="nome_1164446" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1164446" name="cognome_1164446" value="d'aquino"/></td> 
<td><input type="text" id="data_1164446" name="data_1164446" value="06/06/1965"/></td> 
<td><input type="radio" id="sessom_1164446" name="sesso_1164446" value="M" checked/> M
<input type="radio" id="sessof_1164446" name="sesso_1164446" value="F"/> F
</td>  
<td><input type="text" id="comune_1164446" name="comune_1164446" value="napoli"/></td>
<td><input type="text" id="cf_1164446" name="cf_1164446" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164446,document.forms[0].nome_1164446,document.forms[0].cognome_1164446,document.forms[0].comune_1164446,document.forms[0].data_1164446,'cf_1164446')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178767" name="nome_1178767" value="roberta"/></td> 
<td><input type="text" id="cognome_1178767" name="cognome_1178767" value="carpinelli"/></td> 
<td><input type="text" id="data_1178767" name="data_1178767" value="22/11/1988"/></td> 
<td><input type="radio" id="sessom_1178767" name="sesso_1178767" value="M"/> M
<input type="radio" id="sessof_1178767" name="sesso_1178767" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1178767" name="comune_1178767" value="battipaglia"/></td>
<td><input type="text" id="cf_1178767" name="cf_1178767" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178767,document.forms[0].nome_1178767,document.forms[0].cognome_1178767,document.forms[0].comune_1178767,document.forms[0].data_1178767,'cf_1178767')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169886" name="nome_1169886" value="alessandra"/></td> 
<td><input type="text" id="cognome_1169886" name="cognome_1169886" value="del piano"/></td> 
<td><input type="text" id="data_1169886" name="data_1169886" value="11/05/1977"/></td> 
<td><input type="radio" id="sessom_1169886" name="sesso_1169886" value="M"/> M
<input type="radio" id="sessof_1169886" name="sesso_1169886" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1169886" name="comune_1169886" value="napoli"/></td>
<td><input type="text" id="cf_1169886" name="cf_1169886" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169886,document.forms[0].nome_1169886,document.forms[0].cognome_1169886,document.forms[0].comune_1169886,document.forms[0].data_1169886,'cf_1169886')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192054" name="nome_1192054" value="aniello"/></td> 
<td><input type="text" id="cognome_1192054" name="cognome_1192054" value="coppola"/></td> 
<td><input type="text" id="data_1192054" name="data_1192054" value="01/01/1948"/></td> 
<td><input type="radio" id="sessom_1192054" name="sesso_1192054" value="M" checked/> M
<input type="radio" id="sessof_1192054" name="sesso_1192054" value="F"/> F
</td>  
<td><input type="text" id="comune_1192054" name="comune_1192054" value="vallo della lucania"/></td>
<td><input type="text" id="cf_1192054" name="cf_1192054" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192054,document.forms[0].nome_1192054,document.forms[0].cognome_1192054,document.forms[0].comune_1192054,document.forms[0].data_1192054,'cf_1192054')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193888" name="nome_1193888" value="francesco"/></td> 
<td><input type="text" id="cognome_1193888" name="cognome_1193888" value="lupo"/></td> 
<td><input type="text" id="data_1193888" name="data_1193888" value="22/08/1947"/></td> 
<td><input type="radio" id="sessom_1193888" name="sesso_1193888" value="M" checked/> M
<input type="radio" id="sessof_1193888" name="sesso_1193888" value="F"/> F
</td>  
<td><input type="text" id="comune_1193888" name="comune_1193888" value="caivano"/></td>
<td><input type="text" id="cf_1193888" name="cf_1193888" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193888,document.forms[0].nome_1193888,document.forms[0].cognome_1193888,document.forms[0].comune_1193888,document.forms[0].data_1193888,'cf_1193888')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166100" name="nome_1166100" value="FRANCESCA"/></td> 
<td><input type="text" id="cognome_1166100" name="cognome_1166100" value="MINELLA"/></td> 
<td><input type="text" id="data_1166100" name="data_1166100" value="06/01/1972"/></td> 
<td><input type="radio" id="sessom_1166100" name="sesso_1166100" value="M"/> M
<input type="radio" id="sessof_1166100" name="sesso_1166100" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166100" name="comune_1166100" value="napoli"/></td>
<td><input type="text" id="cf_1166100" name="cf_1166100" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166100,document.forms[0].nome_1166100,document.forms[0].cognome_1166100,document.forms[0].comune_1166100,document.forms[0].data_1166100,'cf_1166100')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1194107" name="nome_1194107" value="paolo"/></td> 
<td><input type="text" id="cognome_1194107" name="cognome_1194107" value="gadola"/></td> 
<td><input type="text" id="data_1194107" name="data_1194107" value="05/06/1976"/></td> 
<td><input type="radio" id="sessom_1194107" name="sesso_1194107" value="M" checked/> M
<input type="radio" id="sessof_1194107" name="sesso_1194107" value="F"/> F
</td>  
<td><input type="text" id="comune_1194107" name="comune_1194107" value="perugia"/></td>
<td><input type="text" id="cf_1194107" name="cf_1194107" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194107,document.forms[0].nome_1194107,document.forms[0].cognome_1194107,document.forms[0].comune_1194107,document.forms[0].data_1194107,'cf_1194107')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172343" name="nome_1172343" value="luigi"/></td> 
<td><input type="text" id="cognome_1172343" name="cognome_1172343" value="d'atri"/></td> 
<td><input type="text" id="data_1172343" name="data_1172343" value="02/10/1960"/></td> 
<td><input type="radio" id="sessom_1172343" name="sesso_1172343" value="M" checked/> M
<input type="radio" id="sessof_1172343" name="sesso_1172343" value="F"/> F
</td>  
<td><input type="text" id="comune_1172343" name="comune_1172343" value="napoli"/></td>
<td><input type="text" id="cf_1172343" name="cf_1172343" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172343,document.forms[0].nome_1172343,document.forms[0].cognome_1172343,document.forms[0].comune_1172343,document.forms[0].data_1172343,'cf_1172343')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1192101" name="nome_1192101" value="luigi"/></td> 
<td><input type="text" id="cognome_1192101" name="cognome_1192101" value="dell'olmo"/></td> 
<td><input type="text" id="data_1192101" name="data_1192101" value="29/10/1955"/></td> 
<td><input type="radio" id="sessom_1192101" name="sesso_1192101" value="M" checked/> M
<input type="radio" id="sessof_1192101" name="sesso_1192101" value="F"/> F
</td>  
<td><input type="text" id="comune_1192101" name="comune_1192101" value="cardito"/></td>
<td><input type="text" id="cf_1192101" name="cf_1192101" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192101,document.forms[0].nome_1192101,document.forms[0].cognome_1192101,document.forms[0].comune_1192101,document.forms[0].data_1192101,'cf_1192101')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1184391" name="nome_1184391" value="antonio"/></td> 
<td><input type="text" id="cognome_1184391" name="cognome_1184391" value="celentano"/></td> 
<td><input type="text" id="data_1184391" name="data_1184391" value="30/03/1971"/></td> 
<td><input type="radio" id="sessom_1184391" name="sesso_1184391" value="M" checked/> M
<input type="radio" id="sessof_1184391" name="sesso_1184391" value="F"/> F
</td>  
<td><input type="text" id="comune_1184391" name="comune_1184391" value="napoli"/></td>
<td><input type="text" id="cf_1184391" name="cf_1184391" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184391,document.forms[0].nome_1184391,document.forms[0].cognome_1184391,document.forms[0].comune_1184391,document.forms[0].data_1184391,'cf_1184391')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1169732" name="nome_1169732" value="fulvio"/></td> 
<td><input type="text" id="cognome_1169732" name="cognome_1169732" value="di maggio"/></td> 
<td><input type="text" id="data_1169732" name="data_1169732" value="07/09/1969"/></td> 
<td><input type="radio" id="sessom_1169732" name="sesso_1169732" value="M" checked/> M
<input type="radio" id="sessof_1169732" name="sesso_1169732" value="F"/> F
</td>  
<td><input type="text" id="comune_1169732" name="comune_1169732" value="napoli"/></td>
<td><input type="text" id="cf_1169732" name="cf_1169732" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169732,document.forms[0].nome_1169732,document.forms[0].cognome_1169732,document.forms[0].comune_1169732,document.forms[0].data_1169732,'cf_1169732')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193454" name="nome_1193454" value="lucia "/></td> 
<td><input type="text" id="cognome_1193454" name="cognome_1193454" value="izzo"/></td> 
<td><input type="text" id="data_1193454" name="data_1193454" value="28/08/1963"/></td> 
<td><input type="radio" id="sessom_1193454" name="sesso_1193454" value="M"/> M
<input type="radio" id="sessof_1193454" name="sesso_1193454" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1193454" name="comune_1193454" value="casagiove"/></td>
<td><input type="text" id="cf_1193454" name="cf_1193454" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193454,document.forms[0].nome_1193454,document.forms[0].cognome_1193454,document.forms[0].comune_1193454,document.forms[0].data_1193454,'cf_1193454')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041903" name="nome_1041903" value="Carolina "/></td> 
<td><input type="text" id="cognome_1041903" name="cognome_1041903" value="de santis"/></td> 
<td><input type="text" id="data_1041903" name="data_1041903" value="23/11/1953"/></td> 
<td><input type="radio" id="sessom_1041903" name="sesso_1041903" value="M"/> M
<input type="radio" id="sessof_1041903" name="sesso_1041903" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1041903" name="comune_1041903" value="Marzano Appio"/></td>
<td><input type="text" id="cf_1041903" name="cf_1041903" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041903,document.forms[0].nome_1041903,document.forms[0].cognome_1041903,document.forms[0].comune_1041903,document.forms[0].data_1041903,'cf_1041903')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1190655" name="nome_1190655" value="claudia "/></td> 
<td><input type="text" id="cognome_1190655" name="cognome_1190655" value="giordano"/></td> 
<td><input type="text" id="data_1190655" name="data_1190655" value="21/10/1982"/></td> 
<td><input type="radio" id="sessom_1190655" name="sesso_1190655" value="M"/> M
<input type="radio" id="sessof_1190655" name="sesso_1190655" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1190655" name="comune_1190655" value="battipaglia"/></td>
<td><input type="text" id="cf_1190655" name="cf_1190655" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1190655,document.forms[0].nome_1190655,document.forms[0].cognome_1190655,document.forms[0].comune_1190655,document.forms[0].data_1190655,'cf_1190655')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172344" name="nome_1172344" value="elio"/></td> 
<td><input type="text" id="cognome_1172344" name="cognome_1172344" value="de falco"/></td> 
<td><input type="text" id="data_1172344" name="data_1172344" value="20/02/1964"/></td> 
<td><input type="radio" id="sessom_1172344" name="sesso_1172344" value="M" checked/> M
<input type="radio" id="sessof_1172344" name="sesso_1172344" value="F"/> F
</td>  
<td><input type="text" id="comune_1172344" name="comune_1172344" value="napoli"/></td>
<td><input type="text" id="cf_1172344" name="cf_1172344" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172344,document.forms[0].nome_1172344,document.forms[0].cognome_1172344,document.forms[0].comune_1172344,document.forms[0].data_1172344,'cf_1172344')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166705" name="nome_1166705" value="roberto"/></td> 
<td><input type="text" id="cognome_1166705" name="cognome_1166705" value="ragno"/></td> 
<td><input type="text" id="data_1166705" name="data_1166705" value="23/10/1962"/></td> 
<td><input type="radio" id="sessom_1166705" name="sesso_1166705" value="M" checked/> M
<input type="radio" id="sessof_1166705" name="sesso_1166705" value="F"/> F
</td>  
<td><input type="text" id="comune_1166705" name="comune_1166705" value="napoli"/></td>
<td><input type="text" id="cf_1166705" name="cf_1166705" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166705,document.forms[0].nome_1166705,document.forms[0].cognome_1166705,document.forms[0].comune_1166705,document.forms[0].data_1166705,'cf_1166705')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1012854" name="nome_1012854" value="emilio "/></td> 
<td><input type="text" id="cognome_1012854" name="cognome_1012854" value="masturzo"/></td> 
<td><input type="text" id="data_1012854" name="data_1012854" value="26/01/1948"/></td> 
<td><input type="radio" id="sessom_1012854" name="sesso_1012854" value="M" checked/> M
<input type="radio" id="sessof_1012854" name="sesso_1012854" value="F"/> F
</td>  
<td><input type="text" id="comune_1012854" name="comune_1012854" value="salerno"/></td>
<td><input type="text" id="cf_1012854" name="cf_1012854" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1012854,document.forms[0].nome_1012854,document.forms[0].cognome_1012854,document.forms[0].comune_1012854,document.forms[0].data_1012854,'cf_1012854')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1019611" name="nome_1019611" value="CARMINE "/></td> 
<td><input type="text" id="cognome_1019611" name="cognome_1019611" value="RUSSO"/></td> 
<td><input type="text" id="data_1019611" name="data_1019611" value="21/02/1963"/></td> 
<td><input type="radio" id="sessom_1019611" name="sesso_1019611" value="M" checked/> M
<input type="radio" id="sessof_1019611" name="sesso_1019611" value="F"/> F
</td>  
<td><input type="text" id="comune_1019611" name="comune_1019611" value="NAPOLI"/></td>
<td><input type="text" id="cf_1019611" name="cf_1019611" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1019611,document.forms[0].nome_1019611,document.forms[0].cognome_1019611,document.forms[0].comune_1019611,document.forms[0].data_1019611,'cf_1019611')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194818" name="nome_1194818" value="serena"/></td> 
<td><input type="text" id="cognome_1194818" name="cognome_1194818" value="marino"/></td> 
<td><input type="text" id="data_1194818" name="data_1194818" value="25/09/1968"/></td> 
<td><input type="radio" id="sessom_1194818" name="sesso_1194818" value="M"/> M
<input type="radio" id="sessof_1194818" name="sesso_1194818" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194818" name="comune_1194818" value="napoli"/></td>
<td><input type="text" id="cf_1194818" name="cf_1194818" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194818,document.forms[0].nome_1194818,document.forms[0].cognome_1194818,document.forms[0].comune_1194818,document.forms[0].data_1194818,'cf_1194818')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194110" name="nome_1194110" value="DONATELLA"/></td> 
<td><input type="text" id="cognome_1194110" name="cognome_1194110" value="LAUDATI"/></td> 
<td><input type="text" id="data_1194110" name="data_1194110" value="30/04/1958"/></td> 
<td><input type="radio" id="sessom_1194110" name="sesso_1194110" value="M"/> M
<input type="radio" id="sessof_1194110" name="sesso_1194110" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194110" name="comune_1194110" value="NAPOLI"/></td>
<td><input type="text" id="cf_1194110" name="cf_1194110" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194110,document.forms[0].nome_1194110,document.forms[0].cognome_1194110,document.forms[0].comune_1194110,document.forms[0].data_1194110,'cf_1194110')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185419" name="nome_1185419" value="gustavo"/></td> 
<td><input type="text" id="cognome_1185419" name="cognome_1185419" value="fusco"/></td> 
<td><input type="text" id="data_1185419" name="data_1185419" value="18/10/1968"/></td> 
<td><input type="radio" id="sessom_1185419" name="sesso_1185419" value="M"/> M
<input type="radio" id="sessof_1185419" name="sesso_1185419" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1185419" name="comune_1185419" value="napoli"/></td>
<td><input type="text" id="cf_1185419" name="cf_1185419" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185419,document.forms[0].nome_1185419,document.forms[0].cognome_1185419,document.forms[0].comune_1185419,document.forms[0].data_1185419,'cf_1185419')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1067337" name="nome_1067337" value="roberto"/></td> 
<td><input type="text" id="cognome_1067337" name="cognome_1067337" value="brunn"/></td> 
<td><input type="text" id="data_1067337" name="data_1067337" value="11/02/1978"/></td> 
<td><input type="radio" id="sessom_1067337" name="sesso_1067337" value="M" checked/> M
<input type="radio" id="sessof_1067337" name="sesso_1067337" value="F"/> F
</td>  
<td><input type="text" id="comune_1067337" name="comune_1067337" value="napoli"/></td>
<td><input type="text" id="cf_1067337" name="cf_1067337" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1067337,document.forms[0].nome_1067337,document.forms[0].cognome_1067337,document.forms[0].comune_1067337,document.forms[0].data_1067337,'cf_1067337')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1045712" name="nome_1045712" value="LUIGI"/></td> 
<td><input type="text" id="cognome_1045712" name="cognome_1045712" value="SORRENTINO"/></td> 
<td><input type="text" id="data_1045712" name="data_1045712" value="13/01/1976"/></td> 
<td><input type="radio" id="sessom_1045712" name="sesso_1045712" value="M" checked/> M
<input type="radio" id="sessof_1045712" name="sesso_1045712" value="F"/> F
</td>  
<td><input type="text" id="comune_1045712" name="comune_1045712" value="POMPEI (NA)"/></td>
<td><input type="text" id="cf_1045712" name="cf_1045712" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1045712,document.forms[0].nome_1045712,document.forms[0].cognome_1045712,document.forms[0].comune_1045712,document.forms[0].data_1045712,'cf_1045712')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192056" name="nome_1192056" value="elvira"/></td> 
<td><input type="text" id="cognome_1192056" name="cognome_1192056" value="bianco"/></td> 
<td><input type="text" id="data_1192056" name="data_1192056" value="31/08/1954"/></td> 
<td><input type="radio" id="sessom_1192056" name="sesso_1192056" value="M"/> M
<input type="radio" id="sessof_1192056" name="sesso_1192056" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1192056" name="comune_1192056" value=""/></td>
<td><input type="text" id="cf_1192056" name="cf_1192056" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192056,document.forms[0].nome_1192056,document.forms[0].cognome_1192056,document.forms[0].comune_1192056,document.forms[0].data_1192056,'cf_1192056')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1190737" name="nome_1190737" value="patrizia"/></td> 
<td><input type="text" id="cognome_1190737" name="cognome_1190737" value="morrica"/></td> 
<td><input type="text" id="data_1190737" name="data_1190737" value="15/07/1949"/></td> 
<td><input type="radio" id="sessom_1190737" name="sesso_1190737" value="M"/> M
<input type="radio" id="sessof_1190737" name="sesso_1190737" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1190737" name="comune_1190737" value="napoli"/></td>
<td><input type="text" id="cf_1190737" name="cf_1190737" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1190737,document.forms[0].nome_1190737,document.forms[0].cognome_1190737,document.forms[0].comune_1190737,document.forms[0].data_1190737,'cf_1190737')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1046072" name="nome_1046072" value="ANNA"/></td> 
<td><input type="text" id="cognome_1046072" name="cognome_1046072" value="DE VIVO"/></td> 
<td><input type="text" id="data_1046072" name="data_1046072" value="25/01/1964"/></td> 
<td><input type="radio" id="sessom_1046072" name="sesso_1046072" value="M"/> M
<input type="radio" id="sessof_1046072" name="sesso_1046072" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1046072" name="comune_1046072" value="SCAFATI (SA)"/></td>
<td><input type="text" id="cf_1046072" name="cf_1046072" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1046072,document.forms[0].nome_1046072,document.forms[0].cognome_1046072,document.forms[0].comune_1046072,document.forms[0].data_1046072,'cf_1046072')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1044711" name="nome_1044711" value="lordi"/></td> 
<td><input type="text" id="cognome_1044711" name="cognome_1044711" value="maria antonella"/></td> 
<td><input type="text" id="data_1044711" name="data_1044711" value="15/08/1956"/></td> 
<td><input type="radio" id="sessom_1044711" name="sesso_1044711" value="M"/> M
<input type="radio" id="sessof_1044711" name="sesso_1044711" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1044711" name="comune_1044711" value="salerno"/></td>
<td><input type="text" id="cf_1044711" name="cf_1044711" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1044711,document.forms[0].nome_1044711,document.forms[0].cognome_1044711,document.forms[0].comune_1044711,document.forms[0].data_1044711,'cf_1044711')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041457" name="nome_1041457" value="benito"/></td> 
<td><input type="text" id="cognome_1041457" name="cognome_1041457" value="colucci "/></td> 
<td><input type="text" id="data_1041457" name="data_1041457" value="19/02/1936"/></td> 
<td><input type="radio" id="sessom_1041457" name="sesso_1041457" value="M" checked/> M
<input type="radio" id="sessof_1041457" name="sesso_1041457" value="F"/> F
</td>  
<td><input type="text" id="comune_1041457" name="comune_1041457" value="baraggiano (PZ)"/></td>
<td><input type="text" id="cf_1041457" name="cf_1041457" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041457,document.forms[0].nome_1041457,document.forms[0].cognome_1041457,document.forms[0].comune_1041457,document.forms[0].data_1041457,'cf_1041457')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1050023" name="nome_1050023" value="dolorata"/></td> 
<td><input type="text" id="cognome_1050023" name="cognome_1050023" value="senese"/></td> 
<td><input type="text" id="data_1050023" name="data_1050023" value="10/05/1975"/></td> 
<td><input type="radio" id="sessom_1050023" name="sesso_1050023" value="M"/> M
<input type="radio" id="sessof_1050023" name="sesso_1050023" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1050023" name="comune_1050023" value="oliveto citra"/></td>
<td><input type="text" id="cf_1050023" name="cf_1050023" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1050023,document.forms[0].nome_1050023,document.forms[0].cognome_1050023,document.forms[0].comune_1050023,document.forms[0].data_1050023,'cf_1050023')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182784" name="nome_1182784" value="barbara"/></td> 
<td><input type="text" id="cognome_1182784" name="cognome_1182784" value="ninni"/></td> 
<td><input type="text" id="data_1182784" name="data_1182784" value="02/07/1970"/></td> 
<td><input type="radio" id="sessom_1182784" name="sesso_1182784" value="M"/> M
<input type="radio" id="sessof_1182784" name="sesso_1182784" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1182784" name="comune_1182784" value="napoli"/></td>
<td><input type="text" id="cf_1182784" name="cf_1182784" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182784,document.forms[0].nome_1182784,document.forms[0].cognome_1182784,document.forms[0].comune_1182784,document.forms[0].data_1182784,'cf_1182784')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1013902" name="nome_1013902" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1013902" name="cognome_1013902" value="caputo"/></td> 
<td><input type="text" id="data_1013902" name="data_1013902" value="01/01/1948"/></td> 
<td><input type="radio" id="sessom_1013902" name="sesso_1013902" value="M" checked/> M
<input type="radio" id="sessof_1013902" name="sesso_1013902" value="F"/> F
</td>  
<td><input type="text" id="comune_1013902" name="comune_1013902" value="lavello (pz)"/></td>
<td><input type="text" id="cf_1013902" name="cf_1013902" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1013902,document.forms[0].nome_1013902,document.forms[0].cognome_1013902,document.forms[0].comune_1013902,document.forms[0].data_1013902,'cf_1013902')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194109" name="nome_1194109" value="MARIANO"/></td> 
<td><input type="text" id="cognome_1194109" name="cognome_1194109" value="DE MATTEIS"/></td> 
<td><input type="text" id="data_1194109" name="data_1194109" value="19/06/1947"/></td> 
<td><input type="radio" id="sessom_1194109" name="sesso_1194109" value="M" checked/> M
<input type="radio" id="sessof_1194109" name="sesso_1194109" value="F"/> F
</td>  
<td><input type="text" id="comune_1194109" name="comune_1194109" value="NAPOLI"/></td>
<td><input type="text" id="cf_1194109" name="cf_1194109" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194109,document.forms[0].nome_1194109,document.forms[0].cognome_1194109,document.forms[0].comune_1194109,document.forms[0].data_1194109,'cf_1194109')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1176963" name="nome_1176963" value="teresa"/></td> 
<td><input type="text" id="cognome_1176963" name="cognome_1176963" value="gallo"/></td> 
<td><input type="text" id="data_1176963" name="data_1176963" value="14/05/1973"/></td> 
<td><input type="radio" id="sessom_1176963" name="sesso_1176963" value="M"/> M
<input type="radio" id="sessof_1176963" name="sesso_1176963" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1176963" name="comune_1176963" value="napoli"/></td>
<td><input type="text" id="cf_1176963" name="cf_1176963" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1176963,document.forms[0].nome_1176963,document.forms[0].cognome_1176963,document.forms[0].comune_1176963,document.forms[0].data_1176963,'cf_1176963')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189131" name="nome_1189131" value="francesco"/></td> 
<td><input type="text" id="cognome_1189131" name="cognome_1189131" value="mercaldo"/></td> 
<td><input type="text" id="data_1189131" name="data_1189131" value="17/07/1975"/></td> 
<td><input type="radio" id="sessom_1189131" name="sesso_1189131" value="M" checked/> M
<input type="radio" id="sessof_1189131" name="sesso_1189131" value="F"/> F
</td>  
<td><input type="text" id="comune_1189131" name="comune_1189131" value="benevento"/></td>
<td><input type="text" id="cf_1189131" name="cf_1189131" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189131,document.forms[0].nome_1189131,document.forms[0].cognome_1189131,document.forms[0].comune_1189131,document.forms[0].data_1189131,'cf_1189131')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189140" name="nome_1189140" value="alessandra"/></td> 
<td><input type="text" id="cognome_1189140" name="cognome_1189140" value="del piano "/></td> 
<td><input type="text" id="data_1189140" name="data_1189140" value="11/05/1977"/></td> 
<td><input type="radio" id="sessom_1189140" name="sesso_1189140" value="M"/> M
<input type="radio" id="sessof_1189140" name="sesso_1189140" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1189140" name="comune_1189140" value="napoli"/></td>
<td><input type="text" id="cf_1189140" name="cf_1189140" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189140,document.forms[0].nome_1189140,document.forms[0].cognome_1189140,document.forms[0].comune_1189140,document.forms[0].data_1189140,'cf_1189140')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182782" name="nome_1182782" value="errico"/></td> 
<td><input type="text" id="cognome_1182782" name="cognome_1182782" value="alfani"/></td> 
<td><input type="text" id="data_1182782" name="data_1182782" value="24/10/1945"/></td> 
<td><input type="radio" id="sessom_1182782" name="sesso_1182782" value="M" checked/> M
<input type="radio" id="sessof_1182782" name="sesso_1182782" value="F"/> F
</td>  
<td><input type="text" id="comune_1182782" name="comune_1182782" value="napoli"/></td>
<td><input type="text" id="cf_1182782" name="cf_1182782" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182782,document.forms[0].nome_1182782,document.forms[0].cognome_1182782,document.forms[0].comune_1182782,document.forms[0].data_1182782,'cf_1182782')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173370" name="nome_1173370" value="dario"/></td> 
<td><input type="text" id="cognome_1173370" name="cognome_1173370" value="parte"/></td> 
<td><input type="text" id="data_1173370" name="data_1173370" value="12/10/1972"/></td> 
<td><input type="radio" id="sessom_1173370" name="sesso_1173370" value="M" checked/> M
<input type="radio" id="sessof_1173370" name="sesso_1173370" value="F"/> F
</td>  
<td><input type="text" id="comune_1173370" name="comune_1173370" value="napoli"/></td>
<td><input type="text" id="cf_1173370" name="cf_1173370" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173370,document.forms[0].nome_1173370,document.forms[0].cognome_1173370,document.forms[0].comune_1173370,document.forms[0].data_1173370,'cf_1173370')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178840" name="nome_1178840" value="raffaele"/></td> 
<td><input type="text" id="cognome_1178840" name="cognome_1178840" value="fiorelli"/></td> 
<td><input type="text" id="data_1178840" name="data_1178840" value="30/06/1973"/></td> 
<td><input type="radio" id="sessom_1178840" name="sesso_1178840" value="M" checked/> M
<input type="radio" id="sessof_1178840" name="sesso_1178840" value="F"/> F
</td>  
<td><input type="text" id="comune_1178840" name="comune_1178840" value="napoli"/></td>
<td><input type="text" id="cf_1178840" name="cf_1178840" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178840,document.forms[0].nome_1178840,document.forms[0].cognome_1178840,document.forms[0].comune_1178840,document.forms[0].data_1178840,'cf_1178840')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042595" name="nome_1042595" value="michele"/></td> 
<td><input type="text" id="cognome_1042595" name="cognome_1042595" value="conforti"/></td> 
<td><input type="text" id="data_1042595" name="data_1042595" value="29/10/1966"/></td> 
<td><input type="radio" id="sessom_1042595" name="sesso_1042595" value="M" checked/> M
<input type="radio" id="sessof_1042595" name="sesso_1042595" value="F"/> F
</td>  
<td><input type="text" id="comune_1042595" name="comune_1042595" value="salerno"/></td>
<td><input type="text" id="cf_1042595" name="cf_1042595" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042595,document.forms[0].nome_1042595,document.forms[0].cognome_1042595,document.forms[0].comune_1042595,document.forms[0].data_1042595,'cf_1042595')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1184154" name="nome_1184154" value="angelo"/></td> 
<td><input type="text" id="cognome_1184154" name="cognome_1184154" value="padovani"/></td> 
<td><input type="text" id="data_1184154" name="data_1184154" value="27/06/1971"/></td> 
<td><input type="radio" id="sessom_1184154" name="sesso_1184154" value="M" checked/> M
<input type="radio" id="sessof_1184154" name="sesso_1184154" value="F"/> F
</td>  
<td><input type="text" id="comune_1184154" name="comune_1184154" value="Napoli"/></td>
<td><input type="text" id="cf_1184154" name="cf_1184154" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184154,document.forms[0].nome_1184154,document.forms[0].cognome_1184154,document.forms[0].comune_1184154,document.forms[0].data_1184154,'cf_1184154')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1014437" name="nome_1014437" value="biagio"/></td> 
<td><input type="text" id="cognome_1014437" name="cognome_1014437" value="guerra"/></td> 
<td><input type="text" id="data_1014437" name="data_1014437" value="05/11/1949"/></td> 
<td><input type="radio" id="sessom_1014437" name="sesso_1014437" value="M" checked/> M
<input type="radio" id="sessof_1014437" name="sesso_1014437" value="F"/> F
</td>  
<td><input type="text" id="comune_1014437" name="comune_1014437" value="napoli"/></td>
<td><input type="text" id="cf_1014437" name="cf_1014437" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1014437,document.forms[0].nome_1014437,document.forms[0].cognome_1014437,document.forms[0].comune_1014437,document.forms[0].data_1014437,'cf_1014437')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1064642" name="nome_1064642" value="francesco"/></td> 
<td><input type="text" id="cognome_1064642" name="cognome_1064642" value="sorrentino"/></td> 
<td><input type="text" id="data_1064642" name="data_1064642" value="29/01/1972"/></td> 
<td><input type="radio" id="sessom_1064642" name="sesso_1064642" value="M" checked/> M
<input type="radio" id="sessof_1064642" name="sesso_1064642" value="F"/> F
</td>  
<td><input type="text" id="comune_1064642" name="comune_1064642" value="SALERNO"/></td>
<td><input type="text" id="cf_1064642" name="cf_1064642" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1064642,document.forms[0].nome_1064642,document.forms[0].cognome_1064642,document.forms[0].comune_1064642,document.forms[0].data_1064642,'cf_1064642')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1165955" name="nome_1165955" value="ROBERTa"/></td> 
<td><input type="text" id="cognome_1165955" name="cognome_1165955" value="d'angelo"/></td> 
<td><input type="text" id="data_1165955" name="data_1165955" value="20/07/1969"/></td> 
<td><input type="radio" id="sessom_1165955" name="sesso_1165955" value="M"/> M
<input type="radio" id="sessof_1165955" name="sesso_1165955" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1165955" name="comune_1165955" value="NAPOLI"/></td>
<td><input type="text" id="cf_1165955" name="cf_1165955" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1165955,document.forms[0].nome_1165955,document.forms[0].cognome_1165955,document.forms[0].comune_1165955,document.forms[0].data_1165955,'cf_1165955')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1184009" name="nome_1184009" value="anna maria"/></td> 
<td><input type="text" id="cognome_1184009" name="cognome_1184009" value="satriano"/></td> 
<td><input type="text" id="data_1184009" name="data_1184009" value="29/05/1972"/></td> 
<td><input type="radio" id="sessom_1184009" name="sesso_1184009" value="M"/> M
<input type="radio" id="sessof_1184009" name="sesso_1184009" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1184009" name="comune_1184009" value="verona"/></td>
<td><input type="text" id="cf_1184009" name="cf_1184009" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184009,document.forms[0].nome_1184009,document.forms[0].cognome_1184009,document.forms[0].comune_1184009,document.forms[0].data_1184009,'cf_1184009')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1181510" name="nome_1181510" value="raffaella"/></td> 
<td><input type="text" id="cognome_1181510" name="cognome_1181510" value="margiotta"/></td> 
<td><input type="text" id="data_1181510" name="data_1181510" value="27/10/1972"/></td> 
<td><input type="radio" id="sessom_1181510" name="sesso_1181510" value="M"/> M
<input type="radio" id="sessof_1181510" name="sesso_1181510" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181510" name="comune_1181510" value="napoli"/></td>
<td><input type="text" id="cf_1181510" name="cf_1181510" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181510,document.forms[0].nome_1181510,document.forms[0].cognome_1181510,document.forms[0].comune_1181510,document.forms[0].data_1181510,'cf_1181510')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1170673" name="nome_1170673" value="vincenzo"/></td> 
<td><input type="text" id="cognome_1170673" name="cognome_1170673" value="corvino"/></td> 
<td><input type="text" id="data_1170673" name="data_1170673" value="22/02/1954"/></td> 
<td><input type="radio" id="sessom_1170673" name="sesso_1170673" value="M" checked/> M
<input type="radio" id="sessof_1170673" name="sesso_1170673" value="F"/> F
</td>  
<td><input type="text" id="comune_1170673" name="comune_1170673" value="casal di principe"/></td>
<td><input type="text" id="cf_1170673" name="cf_1170673" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1170673,document.forms[0].nome_1170673,document.forms[0].cognome_1170673,document.forms[0].comune_1170673,document.forms[0].data_1170673,'cf_1170673')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1191117" name="nome_1191117" value="barbara "/></td> 
<td><input type="text" id="cognome_1191117" name="cognome_1191117" value="ninni"/></td> 
<td><input type="text" id="data_1191117" name="data_1191117" value="02/07/1970"/></td> 
<td><input type="radio" id="sessom_1191117" name="sesso_1191117" value="M"/> M
<input type="radio" id="sessof_1191117" name="sesso_1191117" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1191117" name="comune_1191117" value=""/></td>
<td><input type="text" id="cf_1191117" name="cf_1191117" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1191117,document.forms[0].nome_1191117,document.forms[0].cognome_1191117,document.forms[0].comune_1191117,document.forms[0].data_1191117,'cf_1191117')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167724" name="nome_1167724" value="sossio"/></td> 
<td><input type="text" id="cognome_1167724" name="cognome_1167724" value="pezzullo"/></td> 
<td><input type="text" id="data_1167724" name="data_1167724" value="29/07/1961"/></td> 
<td><input type="radio" id="sessom_1167724" name="sesso_1167724" value="M" checked/> M
<input type="radio" id="sessof_1167724" name="sesso_1167724" value="F"/> F
</td>  
<td><input type="text" id="comune_1167724" name="comune_1167724" value="napoli"/></td>
<td><input type="text" id="cf_1167724" name="cf_1167724" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167724,document.forms[0].nome_1167724,document.forms[0].cognome_1167724,document.forms[0].comune_1167724,document.forms[0].data_1167724,'cf_1167724')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1168494" name="nome_1168494" value="letizia"/></td> 
<td><input type="text" id="cognome_1168494" name="cognome_1168494" value="liguori"/></td> 
<td><input type="text" id="data_1168494" name="data_1168494" value="12/06/1986"/></td> 
<td><input type="radio" id="sessom_1168494" name="sesso_1168494" value="M"/> M
<input type="radio" id="sessof_1168494" name="sesso_1168494" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1168494" name="comune_1168494" value="napoli"/></td>
<td><input type="text" id="cf_1168494" name="cf_1168494" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168494,document.forms[0].nome_1168494,document.forms[0].cognome_1168494,document.forms[0].comune_1168494,document.forms[0].data_1168494,'cf_1168494')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1175924" name="nome_1175924" value="germano "/></td> 
<td><input type="text" id="cognome_1175924" name="cognome_1175924" value="guzzi"/></td> 
<td><input type="text" id="data_1175924" name="data_1175924" value="06/11/1959"/></td> 
<td><input type="radio" id="sessom_1175924" name="sesso_1175924" value="M" checked/> M
<input type="radio" id="sessof_1175924" name="sesso_1175924" value="F"/> F
</td>  
<td><input type="text" id="comune_1175924" name="comune_1175924" value="battipaglia"/></td>
<td><input type="text" id="cf_1175924" name="cf_1175924" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1175924,document.forms[0].nome_1175924,document.forms[0].cognome_1175924,document.forms[0].comune_1175924,document.forms[0].data_1175924,'cf_1175924')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193361" name="nome_1193361" value="vincenzo"/></td> 
<td><input type="text" id="cognome_1193361" name="cognome_1193361" value="marano"/></td> 
<td><input type="text" id="data_1193361" name="data_1193361" value="05/10/1984"/></td> 
<td><input type="radio" id="sessom_1193361" name="sesso_1193361" value="M" checked/> M
<input type="radio" id="sessof_1193361" name="sesso_1193361" value="F"/> F
</td>  
<td><input type="text" id="comune_1193361" name="comune_1193361" value="napoli"/></td>
<td><input type="text" id="cf_1193361" name="cf_1193361" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193361,document.forms[0].nome_1193361,document.forms[0].cognome_1193361,document.forms[0].comune_1193361,document.forms[0].data_1193361,'cf_1193361')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1046727" name="nome_1046727" value="arcangela"/></td> 
<td><input type="text" id="cognome_1046727" name="cognome_1046727" value="oliva"/></td> 
<td><input type="text" id="data_1046727" name="data_1046727" value="20/01/1946"/></td> 
<td><input type="radio" id="sessom_1046727" name="sesso_1046727" value="M"/> M
<input type="radio" id="sessof_1046727" name="sesso_1046727" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1046727" name="comune_1046727" value="cirigliano (mt)"/></td>
<td><input type="text" id="cf_1046727" name="cf_1046727" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1046727,document.forms[0].nome_1046727,document.forms[0].cognome_1046727,document.forms[0].comune_1046727,document.forms[0].data_1046727,'cf_1046727')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189459" name="nome_1189459" value="raffaele"/></td> 
<td><input type="text" id="cognome_1189459" name="cognome_1189459" value="russomanno"/></td> 
<td><input type="text" id="data_1189459" name="data_1189459" value="06/11/2006"/></td> 
<td><input type="radio" id="sessom_1189459" name="sesso_1189459" value="M" checked/> M
<input type="radio" id="sessof_1189459" name="sesso_1189459" value="F"/> F
</td>  
<td><input type="text" id="comune_1189459" name="comune_1189459" value="caposele"/></td>
<td><input type="text" id="cf_1189459" name="cf_1189459" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189459,document.forms[0].nome_1189459,document.forms[0].cognome_1189459,document.forms[0].comune_1189459,document.forms[0].data_1189459,'cf_1189459')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1184565" name="nome_1184565" value="serena"/></td> 
<td><input type="text" id="cognome_1184565" name="cognome_1184565" value="ripa"/></td> 
<td><input type="text" id="data_1184565" name="data_1184565" value="26/09/1977"/></td> 
<td><input type="radio" id="sessom_1184565" name="sesso_1184565" value="M"/> M
<input type="radio" id="sessof_1184565" name="sesso_1184565" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1184565" name="comune_1184565" value="eboli"/></td>
<td><input type="text" id="cf_1184565" name="cf_1184565" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184565,document.forms[0].nome_1184565,document.forms[0].cognome_1184565,document.forms[0].comune_1184565,document.forms[0].data_1184565,'cf_1184565')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168350" name="nome_1168350" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1168350" name="cognome_1168350" value="ambra"/></td> 
<td><input type="text" id="data_1168350" name="data_1168350" value="01/09/1974"/></td> 
<td><input type="radio" id="sessom_1168350" name="sesso_1168350" value="M" checked/> M
<input type="radio" id="sessof_1168350" name="sesso_1168350" value="F"/> F
</td>  
<td><input type="text" id="comune_1168350" name="comune_1168350" value="napoli"/></td>
<td><input type="text" id="cf_1168350" name="cf_1168350" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168350,document.forms[0].nome_1168350,document.forms[0].cognome_1168350,document.forms[0].comune_1168350,document.forms[0].data_1168350,'cf_1168350')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1091375" name="nome_1091375" value="anna"/></td> 
<td><input type="text" id="cognome_1091375" name="cognome_1091375" value="marino"/></td> 
<td><input type="text" id="data_1091375" name="data_1091375" value="07/02/1960"/></td> 
<td><input type="radio" id="sessom_1091375" name="sesso_1091375" value="M"/> M
<input type="radio" id="sessof_1091375" name="sesso_1091375" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1091375" name="comune_1091375" value="sant'antimo"/></td>
<td><input type="text" id="cf_1091375" name="cf_1091375" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1091375,document.forms[0].nome_1091375,document.forms[0].cognome_1091375,document.forms[0].comune_1091375,document.forms[0].data_1091375,'cf_1091375')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1189184" name="nome_1189184" value="achille"/></td> 
<td><input type="text" id="cognome_1189184" name="cognome_1189184" value="arciero"/></td> 
<td><input type="text" id="data_1189184" name="data_1189184" value="13/08/1984"/></td> 
<td><input type="radio" id="sessom_1189184" name="sesso_1189184" value="M" checked/> M
<input type="radio" id="sessof_1189184" name="sesso_1189184" value="F"/> F
</td>  
<td><input type="text" id="comune_1189184" name="comune_1189184" value="napoli"/></td>
<td><input type="text" id="cf_1189184" name="cf_1189184" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189184,document.forms[0].nome_1189184,document.forms[0].cognome_1189184,document.forms[0].comune_1189184,document.forms[0].data_1189184,'cf_1189184')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172507" name="nome_1172507" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1172507" name="cognome_1172507" value="lorito"/></td> 
<td><input type="text" id="data_1172507" name="data_1172507" value="30/12/1964"/></td> 
<td><input type="radio" id="sessom_1172507" name="sesso_1172507" value="M" checked/> M
<input type="radio" id="sessof_1172507" name="sesso_1172507" value="F"/> F
</td>  
<td><input type="text" id="comune_1172507" name="comune_1172507" value="napoli"/></td>
<td><input type="text" id="cf_1172507" name="cf_1172507" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172507,document.forms[0].nome_1172507,document.forms[0].cognome_1172507,document.forms[0].comune_1172507,document.forms[0].data_1172507,'cf_1172507')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1041538" name="nome_1041538" value="gaetano"/></td> 
<td><input type="text" id="cognome_1041538" name="cognome_1041538" value="sessa"/></td> 
<td><input type="text" id="data_1041538" name="data_1041538" value="24/01/1944"/></td> 
<td><input type="radio" id="sessom_1041538" name="sesso_1041538" value="M" checked/> M
<input type="radio" id="sessof_1041538" name="sesso_1041538" value="F"/> F
</td>  
<td><input type="text" id="comune_1041538" name="comune_1041538" value="postiglione"/></td>
<td><input type="text" id="cf_1041538" name="cf_1041538" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041538,document.forms[0].nome_1041538,document.forms[0].cognome_1041538,document.forms[0].comune_1041538,document.forms[0].data_1041538,'cf_1041538')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1184168" name="nome_1184168" value="giuliano"/></td> 
<td><input type="text" id="cognome_1184168" name="cognome_1184168" value="izzo"/></td> 
<td><input type="text" id="data_1184168" name="data_1184168" value="17/05/1979"/></td> 
<td><input type="radio" id="sessom_1184168" name="sesso_1184168" value="M" checked/> M
<input type="radio" id="sessof_1184168" name="sesso_1184168" value="F"/> F
</td>  
<td><input type="text" id="comune_1184168" name="comune_1184168" value="benevento"/></td>
<td><input type="text" id="cf_1184168" name="cf_1184168" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184168,document.forms[0].nome_1184168,document.forms[0].cognome_1184168,document.forms[0].comune_1184168,document.forms[0].data_1184168,'cf_1184168')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1045962" name="nome_1045962" value="antonio"/></td> 
<td><input type="text" id="cognome_1045962" name="cognome_1045962" value="vairo"/></td> 
<td><input type="text" id="data_1045962" name="data_1045962" value="25/11/1961"/></td> 
<td><input type="radio" id="sessom_1045962" name="sesso_1045962" value="M" checked/> M
<input type="radio" id="sessof_1045962" name="sesso_1045962" value="F"/> F
</td>  
<td><input type="text" id="comune_1045962" name="comune_1045962" value="vallo della LUCANIA (sa)"/></td>
<td><input type="text" id="cf_1045962" name="cf_1045962" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1045962,document.forms[0].nome_1045962,document.forms[0].cognome_1045962,document.forms[0].comune_1045962,document.forms[0].data_1045962,'cf_1045962')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1043287" name="nome_1043287" value="pasquale"/></td> 
<td><input type="text" id="cognome_1043287" name="cognome_1043287" value="lenza"/></td> 
<td><input type="text" id="data_1043287" name="data_1043287" value="17/10/1958"/></td> 
<td><input type="radio" id="sessom_1043287" name="sesso_1043287" value="M" checked/> M
<input type="radio" id="sessof_1043287" name="sesso_1043287" value="F"/> F
</td>  
<td><input type="text" id="comune_1043287" name="comune_1043287" value="salerno"/></td>
<td><input type="text" id="cf_1043287" name="cf_1043287" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1043287,document.forms[0].nome_1043287,document.forms[0].cognome_1043287,document.forms[0].comune_1043287,document.forms[0].data_1043287,'cf_1043287')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1182744" name="nome_1182744" value="annamria raffaela "/></td> 
<td><input type="text" id="cognome_1182744" name="cognome_1182744" value="cassavia"/></td> 
<td><input type="text" id="data_1182744" name="data_1182744" value="13/09/1959"/></td> 
<td><input type="radio" id="sessom_1182744" name="sesso_1182744" value="M"/> M
<input type="radio" id="sessof_1182744" name="sesso_1182744" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1182744" name="comune_1182744" value="acri"/></td>
<td><input type="text" id="cf_1182744" name="cf_1182744" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182744,document.forms[0].nome_1182744,document.forms[0].cognome_1182744,document.forms[0].comune_1182744,document.forms[0].data_1182744,'cf_1182744')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041464" name="nome_1041464" value="paolo"/></td> 
<td><input type="text" id="cognome_1041464" name="cognome_1041464" value="vessa"/></td> 
<td><input type="text" id="data_1041464" name="data_1041464" value="29/01/1955"/></td> 
<td><input type="radio" id="sessom_1041464" name="sesso_1041464" value="M" checked/> M
<input type="radio" id="sessof_1041464" name="sesso_1041464" value="F"/> F
</td>  
<td><input type="text" id="comune_1041464" name="comune_1041464" value="salerno"/></td>
<td><input type="text" id="cf_1041464" name="cf_1041464" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041464,document.forms[0].nome_1041464,document.forms[0].cognome_1041464,document.forms[0].comune_1041464,document.forms[0].data_1041464,'cf_1041464')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1019190" name="nome_1019190" value="elettra"/></td> 
<td><input type="text" id="cognome_1019190" name="cognome_1019190" value="grisi"/></td> 
<td><input type="text" id="data_1019190" name="data_1019190" value="15/11/1965"/></td> 
<td><input type="radio" id="sessom_1019190" name="sesso_1019190" value="M"/> M
<input type="radio" id="sessof_1019190" name="sesso_1019190" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1019190" name="comune_1019190" value="salerno"/></td>
<td><input type="text" id="cf_1019190" name="cf_1019190" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1019190,document.forms[0].nome_1019190,document.forms[0].cognome_1019190,document.forms[0].comune_1019190,document.forms[0].data_1019190,'cf_1019190')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167192" name="nome_1167192" value="angela"/></td> 
<td><input type="text" id="cognome_1167192" name="cognome_1167192" value="bevilacqua"/></td> 
<td><input type="text" id="data_1167192" name="data_1167192" value="22/10/1976"/></td> 
<td><input type="radio" id="sessom_1167192" name="sesso_1167192" value="M"/> M
<input type="radio" id="sessof_1167192" name="sesso_1167192" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1167192" name="comune_1167192" value="salerno"/></td>
<td><input type="text" id="cf_1167192" name="cf_1167192" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167192,document.forms[0].nome_1167192,document.forms[0].cognome_1167192,document.forms[0].comune_1167192,document.forms[0].data_1167192,'cf_1167192')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173710" name="nome_1173710" value="renato"/></td> 
<td><input type="text" id="cognome_1173710" name="cognome_1173710" value="de falco"/></td> 
<td><input type="text" id="data_1173710" name="data_1173710" value="16/12/1971"/></td> 
<td><input type="radio" id="sessom_1173710" name="sesso_1173710" value="M" checked/> M
<input type="radio" id="sessof_1173710" name="sesso_1173710" value="F"/> F
</td>  
<td><input type="text" id="comune_1173710" name="comune_1173710" value="napoli"/></td>
<td><input type="text" id="cf_1173710" name="cf_1173710" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173710,document.forms[0].nome_1173710,document.forms[0].cognome_1173710,document.forms[0].comune_1173710,document.forms[0].data_1173710,'cf_1173710')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166103" name="nome_1166103" value="ENRICHETTA"/></td> 
<td><input type="text" id="cognome_1166103" name="cognome_1166103" value="LOMBARDI"/></td> 
<td><input type="text" id="data_1166103" name="data_1166103" value="23/12/1959"/></td> 
<td><input type="radio" id="sessom_1166103" name="sesso_1166103" value="M"/> M
<input type="radio" id="sessof_1166103" name="sesso_1166103" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166103" name="comune_1166103" value="cerr al volturno"/></td>
<td><input type="text" id="cf_1166103" name="cf_1166103" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166103,document.forms[0].nome_1166103,document.forms[0].cognome_1166103,document.forms[0].comune_1166103,document.forms[0].data_1166103,'cf_1166103')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042471" name="nome_1042471" value="pasquale "/></td> 
<td><input type="text" id="cognome_1042471" name="cognome_1042471" value="d'auria"/></td> 
<td><input type="text" id="data_1042471" name="data_1042471" value="11/01/1951"/></td> 
<td><input type="radio" id="sessom_1042471" name="sesso_1042471" value="M" checked/> M
<input type="radio" id="sessof_1042471" name="sesso_1042471" value="F"/> F
</td>  
<td><input type="text" id="comune_1042471" name="comune_1042471" value="fisciano"/></td>
<td><input type="text" id="cf_1042471" name="cf_1042471" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042471,document.forms[0].nome_1042471,document.forms[0].cognome_1042471,document.forms[0].comune_1042471,document.forms[0].data_1042471,'cf_1042471')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182012" name="nome_1182012" value="amalia"/></td> 
<td><input type="text" id="cognome_1182012" name="cognome_1182012" value="rufolo"/></td> 
<td><input type="text" id="data_1182012" name="data_1182012" value="10/06/1969"/></td> 
<td><input type="radio" id="sessom_1182012" name="sesso_1182012" value="M"/> M
<input type="radio" id="sessof_1182012" name="sesso_1182012" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1182012" name="comune_1182012" value="SALERNO"/></td>
<td><input type="text" id="cf_1182012" name="cf_1182012" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182012,document.forms[0].nome_1182012,document.forms[0].cognome_1182012,document.forms[0].comune_1182012,document.forms[0].data_1182012,'cf_1182012')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1047765" name="nome_1047765" value="alessandra"/></td> 
<td><input type="text" id="cognome_1047765" name="cognome_1047765" value="fabbricatore"/></td> 
<td><input type="text" id="data_1047765" name="data_1047765" value="20/12/1967"/></td> 
<td><input type="radio" id="sessom_1047765" name="sesso_1047765" value="M"/> M
<input type="radio" id="sessof_1047765" name="sesso_1047765" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1047765" name="comune_1047765" value="salerno"/></td>
<td><input type="text" id="cf_1047765" name="cf_1047765" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1047765,document.forms[0].nome_1047765,document.forms[0].cognome_1047765,document.forms[0].comune_1047765,document.forms[0].data_1047765,'cf_1047765')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1012551" name="nome_1012551" value="luigi"/></td> 
<td><input type="text" id="cognome_1012551" name="cognome_1012551" value="bianchini"/></td> 
<td><input type="text" id="data_1012551" name="data_1012551" value="16/06/1969"/></td> 
<td><input type="radio" id="sessom_1012551" name="sesso_1012551" value="M" checked/> M
<input type="radio" id="sessof_1012551" name="sesso_1012551" value="F"/> F
</td>  
<td><input type="text" id="comune_1012551" name="comune_1012551" value="muro lucano"/></td>
<td><input type="text" id="cf_1012551" name="cf_1012551" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1012551,document.forms[0].nome_1012551,document.forms[0].cognome_1012551,document.forms[0].comune_1012551,document.forms[0].data_1012551,'cf_1012551')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178249" name="nome_1178249" value="simona"/></td> 
<td><input type="text" id="cognome_1178249" name="cognome_1178249" value="carnovale"/></td> 
<td><input type="text" id="data_1178249" name="data_1178249" value="13/10/1966"/></td> 
<td><input type="radio" id="sessom_1178249" name="sesso_1178249" value="M"/> M
<input type="radio" id="sessof_1178249" name="sesso_1178249" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1178249" name="comune_1178249" value="napoli"/></td>
<td><input type="text" id="cf_1178249" name="cf_1178249" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178249,document.forms[0].nome_1178249,document.forms[0].cognome_1178249,document.forms[0].comune_1178249,document.forms[0].data_1178249,'cf_1178249')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193474" name="nome_1193474" value="vincenzo"/></td> 
<td><input type="text" id="cognome_1193474" name="cognome_1193474" value="gallucci"/></td> 
<td><input type="text" id="data_1193474" name="data_1193474" value="13/04/1969"/></td> 
<td><input type="radio" id="sessom_1193474" name="sesso_1193474" value="M" checked/> M
<input type="radio" id="sessof_1193474" name="sesso_1193474" value="F"/> F
</td>  
<td><input type="text" id="comune_1193474" name="comune_1193474" value="salerno"/></td>
<td><input type="text" id="cf_1193474" name="cf_1193474" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193474,document.forms[0].nome_1193474,document.forms[0].cognome_1193474,document.forms[0].comune_1193474,document.forms[0].data_1193474,'cf_1193474')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1045308" name="nome_1045308" value="pasquale"/></td> 
<td><input type="text" id="cognome_1045308" name="cognome_1045308" value="iannotto"/></td> 
<td><input type="text" id="data_1045308" name="data_1045308" value="17/10/1947"/></td> 
<td><input type="radio" id="sessom_1045308" name="sesso_1045308" value="M" checked/> M
<input type="radio" id="sessof_1045308" name="sesso_1045308" value="F"/> F
</td>  
<td><input type="text" id="comune_1045308" name="comune_1045308" value="albanella (sa)"/></td>
<td><input type="text" id="cf_1045308" name="cf_1045308" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1045308,document.forms[0].nome_1045308,document.forms[0].cognome_1045308,document.forms[0].comune_1045308,document.forms[0].data_1045308,'cf_1045308')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1013823" name="nome_1013823" value="liliana"/></td> 
<td><input type="text" id="cognome_1013823" name="cognome_1013823" value="scarpone "/></td> 
<td><input type="text" id="data_1013823" name="data_1013823" value="28/10/1944"/></td> 
<td><input type="radio" id="sessom_1013823" name="sesso_1013823" value="M"/> M
<input type="radio" id="sessof_1013823" name="sesso_1013823" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1013823" name="comune_1013823" value="giffoni valle piana"/></td>
<td><input type="text" id="cf_1013823" name="cf_1013823" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1013823,document.forms[0].nome_1013823,document.forms[0].cognome_1013823,document.forms[0].comune_1013823,document.forms[0].data_1013823,'cf_1013823')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185464" name="nome_1185464" value="massimo"/></td> 
<td><input type="text" id="cognome_1185464" name="cognome_1185464" value="faretra"/></td> 
<td><input type="text" id="data_1185464" name="data_1185464" value="28/05/1971"/></td> 
<td><input type="radio" id="sessom_1185464" name="sesso_1185464" value="M" checked/> M
<input type="radio" id="sessof_1185464" name="sesso_1185464" value="F"/> F
</td>  
<td><input type="text" id="comune_1185464" name="comune_1185464" value="avellino"/></td>
<td><input type="text" id="cf_1185464" name="cf_1185464" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185464,document.forms[0].nome_1185464,document.forms[0].cognome_1185464,document.forms[0].comune_1185464,document.forms[0].data_1185464,'cf_1185464')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182333" name="nome_1182333" value="alberto"/></td> 
<td><input type="text" id="cognome_1182333" name="cognome_1182333" value="ammirati"/></td> 
<td><input type="text" id="data_1182333" name="data_1182333" value="15/10/1986"/></td> 
<td><input type="radio" id="sessom_1182333" name="sesso_1182333" value="M" checked/> M
<input type="radio" id="sessof_1182333" name="sesso_1182333" value="F"/> F
</td>  
<td><input type="text" id="comune_1182333" name="comune_1182333" value="avellino"/></td>
<td><input type="text" id="cf_1182333" name="cf_1182333" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182333,document.forms[0].nome_1182333,document.forms[0].cognome_1182333,document.forms[0].comune_1182333,document.forms[0].data_1182333,'cf_1182333')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1192105" name="nome_1192105" value="guida"/></td> 
<td><input type="text" id="cognome_1192105" name="cognome_1192105" value="annamaria"/></td> 
<td><input type="text" id="data_1192105" name="data_1192105" value="11/10/1976"/></td> 
<td><input type="radio" id="sessom_1192105" name="sesso_1192105" value="M"/> M
<input type="radio" id="sessof_1192105" name="sesso_1192105" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1192105" name="comune_1192105" value="celle di brughiera"/></td>
<td><input type="text" id="cf_1192105" name="cf_1192105" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192105,document.forms[0].nome_1192105,document.forms[0].cognome_1192105,document.forms[0].comune_1192105,document.forms[0].data_1192105,'cf_1192105')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173707" name="nome_1173707" value="simonetta"/></td> 
<td><input type="text" id="cognome_1173707" name="cognome_1173707" value="ciamillo"/></td> 
<td><input type="text" id="data_1173707" name="data_1173707" value="18/05/1965"/></td> 
<td><input type="radio" id="sessom_1173707" name="sesso_1173707" value="M"/> M
<input type="radio" id="sessof_1173707" name="sesso_1173707" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1173707" name="comune_1173707" value="napoli"/></td>
<td><input type="text" id="cf_1173707" name="cf_1173707" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173707,document.forms[0].nome_1173707,document.forms[0].cognome_1173707,document.forms[0].comune_1173707,document.forms[0].data_1173707,'cf_1173707')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194819" name="nome_1194819" value="laura"/></td> 
<td><input type="text" id="cognome_1194819" name="cognome_1194819" value="ricciuti"/></td> 
<td><input type="text" id="data_1194819" name="data_1194819" value="03/09/1955"/></td> 
<td><input type="radio" id="sessom_1194819" name="sesso_1194819" value="M"/> M
<input type="radio" id="sessof_1194819" name="sesso_1194819" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194819" name="comune_1194819" value="napoli"/></td>
<td><input type="text" id="cf_1194819" name="cf_1194819" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194819,document.forms[0].nome_1194819,document.forms[0].cognome_1194819,document.forms[0].comune_1194819,document.forms[0].data_1194819,'cf_1194819')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194816" name="nome_1194816" value="giovanna"/></td> 
<td><input type="text" id="cognome_1194816" name="cognome_1194816" value="de carlo"/></td> 
<td><input type="text" id="data_1194816" name="data_1194816" value="28/02/1959"/></td> 
<td><input type="radio" id="sessom_1194816" name="sesso_1194816" value="M"/> M
<input type="radio" id="sessof_1194816" name="sesso_1194816" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194816" name="comune_1194816" value="caserta"/></td>
<td><input type="text" id="cf_1194816" name="cf_1194816" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194816,document.forms[0].nome_1194816,document.forms[0].cognome_1194816,document.forms[0].comune_1194816,document.forms[0].data_1194816,'cf_1194816')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1078102" name="nome_1078102" value="antonio"/></td> 
<td><input type="text" id="cognome_1078102" name="cognome_1078102" value="giliberti"/></td> 
<td><input type="text" id="data_1078102" name="data_1078102" value="09/02/1964"/></td> 
<td><input type="radio" id="sessom_1078102" name="sesso_1078102" value="M" checked/> M
<input type="radio" id="sessof_1078102" name="sesso_1078102" value="F"/> F
</td>  
<td><input type="text" id="comune_1078102" name="comune_1078102" value="Montoro Inferiore"/></td>
<td><input type="text" id="cf_1078102" name="cf_1078102" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1078102,document.forms[0].nome_1078102,document.forms[0].cognome_1078102,document.forms[0].comune_1078102,document.forms[0].data_1078102,'cf_1078102')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178731" name="nome_1178731" value="gennaro"/></td> 
<td><input type="text" id="cognome_1178731" name="cognome_1178731" value="trifari"/></td> 
<td><input type="text" id="data_1178731" name="data_1178731" value="20/07/1956"/></td> 
<td><input type="radio" id="sessom_1178731" name="sesso_1178731" value="M" checked/> M
<input type="radio" id="sessof_1178731" name="sesso_1178731" value="F"/> F
</td>  
<td><input type="text" id="comune_1178731" name="comune_1178731" value="portici"/></td>
<td><input type="text" id="cf_1178731" name="cf_1178731" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178731,document.forms[0].nome_1178731,document.forms[0].cognome_1178731,document.forms[0].comune_1178731,document.forms[0].data_1178731,'cf_1178731')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194510" name="nome_1194510" value="clelia"/></td> 
<td><input type="text" id="cognome_1194510" name="cognome_1194510" value="sibilo"/></td> 
<td><input type="text" id="data_1194510" name="data_1194510" value="31/07/1974"/></td> 
<td><input type="radio" id="sessom_1194510" name="sesso_1194510" value="M"/> M
<input type="radio" id="sessof_1194510" name="sesso_1194510" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194510" name="comune_1194510" value="napoli"/></td>
<td><input type="text" id="cf_1194510" name="cf_1194510" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194510,document.forms[0].nome_1194510,document.forms[0].cognome_1194510,document.forms[0].comune_1194510,document.forms[0].data_1194510,'cf_1194510')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1187301" name="nome_1187301" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1187301" name="cognome_1187301" value="bilo"/></td> 
<td><input type="text" id="data_1187301" name="data_1187301" value="10/04/1988"/></td> 
<td><input type="radio" id="sessom_1187301" name="sesso_1187301" value="M"/> M
<input type="radio" id="sessof_1187301" name="sesso_1187301" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1187301" name="comune_1187301" value="ariano irpino"/></td>
<td><input type="text" id="cf_1187301" name="cf_1187301" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1187301,document.forms[0].nome_1187301,document.forms[0].cognome_1187301,document.forms[0].comune_1187301,document.forms[0].data_1187301,'cf_1187301')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178873" name="nome_1178873" value="francesco"/></td> 
<td><input type="text" id="cognome_1178873" name="cognome_1178873" value="schiavo"/></td> 
<td><input type="text" id="data_1178873" name="data_1178873" value="30/01/1966"/></td> 
<td><input type="radio" id="sessom_1178873" name="sesso_1178873" value="M" checked/> M
<input type="radio" id="sessof_1178873" name="sesso_1178873" value="F"/> F
</td>  
<td><input type="text" id="comune_1178873" name="comune_1178873" value="battipaglia"/></td>
<td><input type="text" id="cf_1178873" name="cf_1178873" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178873,document.forms[0].nome_1178873,document.forms[0].cognome_1178873,document.forms[0].comune_1178873,document.forms[0].data_1178873,'cf_1178873')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1177645" name="nome_1177645" value="laura"/></td> 
<td><input type="text" id="cognome_1177645" name="cognome_1177645" value="de sio cesari"/></td> 
<td><input type="text" id="data_1177645" name="data_1177645" value="14/03/2017"/></td> 
<td><input type="radio" id="sessom_1177645" name="sesso_1177645" value="M"/> M
<input type="radio" id="sessof_1177645" name="sesso_1177645" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1177645" name="comune_1177645" value="napoli"/></td>
<td><input type="text" id="cf_1177645" name="cf_1177645" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1177645,document.forms[0].nome_1177645,document.forms[0].cognome_1177645,document.forms[0].comune_1177645,document.forms[0].data_1177645,'cf_1177645')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194836" name="nome_1194836" value="domenico"/></td> 
<td><input type="text" id="cognome_1194836" name="cognome_1194836" value="della gatta"/></td> 
<td><input type="text" id="data_1194836" name="data_1194836" value="27/05/1986"/></td> 
<td><input type="radio" id="sessom_1194836" name="sesso_1194836" value="M" checked/> M
<input type="radio" id="sessof_1194836" name="sesso_1194836" value="F"/> F
</td>  
<td><input type="text" id="comune_1194836" name="comune_1194836" value="caserta"/></td>
<td><input type="text" id="cf_1194836" name="cf_1194836" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194836,document.forms[0].nome_1194836,document.forms[0].cognome_1194836,document.forms[0].comune_1194836,document.forms[0].data_1194836,'cf_1194836')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1049433" name="nome_1049433" value="salvatore"/></td> 
<td><input type="text" id="cognome_1049433" name="cognome_1049433" value="fabbricatore"/></td> 
<td><input type="text" id="data_1049433" name="data_1049433" value="27/05/1962"/></td> 
<td><input type="radio" id="sessom_1049433" name="sesso_1049433" value="M" checked/> M
<input type="radio" id="sessof_1049433" name="sesso_1049433" value="F"/> F
</td>  
<td><input type="text" id="comune_1049433" name="comune_1049433" value="SALERNO"/></td>
<td><input type="text" id="cf_1049433" name="cf_1049433" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1049433,document.forms[0].nome_1049433,document.forms[0].cognome_1049433,document.forms[0].comune_1049433,document.forms[0].data_1049433,'cf_1049433')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1013906" name="nome_1013906" value="ornella adalgisa"/></td> 
<td><input type="text" id="cognome_1013906" name="cognome_1013906" value="talarico"/></td> 
<td><input type="text" id="data_1013906" name="data_1013906" value="21/03/1960"/></td> 
<td><input type="radio" id="sessom_1013906" name="sesso_1013906" value="M"/> M
<input type="radio" id="sessof_1013906" name="sesso_1013906" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1013906" name="comune_1013906" value="cosenza"/></td>
<td><input type="text" id="cf_1013906" name="cf_1013906" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1013906,document.forms[0].nome_1013906,document.forms[0].cognome_1013906,document.forms[0].comune_1013906,document.forms[0].data_1013906,'cf_1013906')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166102" name="nome_1166102" value="MAURIZIO"/></td> 
<td><input type="text" id="cognome_1166102" name="cognome_1166102" value="LIGUORI"/></td> 
<td><input type="text" id="data_1166102" name="data_1166102" value="25/04/1961"/></td> 
<td><input type="radio" id="sessom_1166102" name="sesso_1166102" value="M" checked/> M
<input type="radio" id="sessof_1166102" name="sesso_1166102" value="F"/> F
</td>  
<td><input type="text" id="comune_1166102" name="comune_1166102" value="napoli"/></td>
<td><input type="text" id="cf_1166102" name="cf_1166102" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166102,document.forms[0].nome_1166102,document.forms[0].cognome_1166102,document.forms[0].comune_1166102,document.forms[0].data_1166102,'cf_1166102')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1181253" name="nome_1181253" value="eugenio"/></td> 
<td><input type="text" id="cognome_1181253" name="cognome_1181253" value="dello ioio"/></td> 
<td><input type="text" id="data_1181253" name="data_1181253" value="14/09/1954"/></td> 
<td><input type="radio" id="sessom_1181253" name="sesso_1181253" value="M" checked/> M
<input type="radio" id="sessof_1181253" name="sesso_1181253" value="F"/> F
</td>  
<td><input type="text" id="comune_1181253" name="comune_1181253" value="gragnano"/></td>
<td><input type="text" id="cf_1181253" name="cf_1181253" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181253,document.forms[0].nome_1181253,document.forms[0].cognome_1181253,document.forms[0].comune_1181253,document.forms[0].data_1181253,'cf_1181253')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1044710" name="nome_1044710" value="pasquale"/></td> 
<td><input type="text" id="cognome_1044710" name="cognome_1044710" value="fiore"/></td> 
<td><input type="text" id="data_1044710" name="data_1044710" value="26/07/1938"/></td> 
<td><input type="radio" id="sessom_1044710" name="sesso_1044710" value="M" checked/> M
<input type="radio" id="sessof_1044710" name="sesso_1044710" value="F"/> F
</td>  
<td><input type="text" id="comune_1044710" name="comune_1044710" value="salerno"/></td>
<td><input type="text" id="cf_1044710" name="cf_1044710" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1044710,document.forms[0].nome_1044710,document.forms[0].cognome_1044710,document.forms[0].comune_1044710,document.forms[0].data_1044710,'cf_1044710')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186356" name="nome_1186356" value="giovanni"/></td> 
<td><input type="text" id="cognome_1186356" name="cognome_1186356" value="RUSSO"/></td> 
<td><input type="text" id="data_1186356" name="data_1186356" value="29/01/1971"/></td> 
<td><input type="radio" id="sessom_1186356" name="sesso_1186356" value="M" checked/> M
<input type="radio" id="sessof_1186356" name="sesso_1186356" value="F"/> F
</td>  
<td><input type="text" id="comune_1186356" name="comune_1186356" value="portici"/></td>
<td><input type="text" id="cf_1186356" name="cf_1186356" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186356,document.forms[0].nome_1186356,document.forms[0].cognome_1186356,document.forms[0].comune_1186356,document.forms[0].data_1186356,'cf_1186356')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186619" name="nome_1186619" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1186619" name="cognome_1186619" value="feemia"/></td> 
<td><input type="text" id="data_1186619" name="data_1186619" value="30/06/1959"/></td> 
<td><input type="radio" id="sessom_1186619" name="sesso_1186619" value="M" checked/> M
<input type="radio" id="sessof_1186619" name="sesso_1186619" value="F"/> F
</td>  
<td><input type="text" id="comune_1186619" name="comune_1186619" value="montecorvino pugliano "/></td>
<td><input type="text" id="cf_1186619" name="cf_1186619" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186619,document.forms[0].nome_1186619,document.forms[0].cognome_1186619,document.forms[0].comune_1186619,document.forms[0].data_1186619,'cf_1186619')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1188190" name="nome_1188190" value="LUIGI MARIA"/></td> 
<td><input type="text" id="cognome_1188190" name="cognome_1188190" value="PEPE"/></td> 
<td><input type="text" id="data_1188190" name="data_1188190" value="11/06/1960"/></td> 
<td><input type="radio" id="sessom_1188190" name="sesso_1188190" value="M" checked/> M
<input type="radio" id="sessof_1188190" name="sesso_1188190" value="F"/> F
</td>  
<td><input type="text" id="comune_1188190" name="comune_1188190" value="napoli"/></td>
<td><input type="text" id="cf_1188190" name="cf_1188190" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1188190,document.forms[0].nome_1188190,document.forms[0].cognome_1188190,document.forms[0].comune_1188190,document.forms[0].data_1188190,'cf_1188190')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189623" name="nome_1189623" value="MARCO"/></td> 
<td><input type="text" id="cognome_1189623" name="cognome_1189623" value="PADOVANI"/></td> 
<td><input type="text" id="data_1189623" name="data_1189623" value="22/04/1974"/></td> 
<td><input type="radio" id="sessom_1189623" name="sesso_1189623" value="M" checked/> M
<input type="radio" id="sessof_1189623" name="sesso_1189623" value="F"/> F
</td>  
<td><input type="text" id="comune_1189623" name="comune_1189623" value="napoli"/></td>
<td><input type="text" id="cf_1189623" name="cf_1189623" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189623,document.forms[0].nome_1189623,document.forms[0].cognome_1189623,document.forms[0].comune_1189623,document.forms[0].data_1189623,'cf_1189623')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168496" name="nome_1168496" value="anna"/></td> 
<td><input type="text" id="cognome_1168496" name="cognome_1168496" value="caiazza"/></td> 
<td><input type="text" id="data_1168496" name="data_1168496" value="07/02/1951"/></td> 
<td><input type="radio" id="sessom_1168496" name="sesso_1168496" value="M"/> M
<input type="radio" id="sessof_1168496" name="sesso_1168496" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1168496" name="comune_1168496" value="liveri"/></td>
<td><input type="text" id="cf_1168496" name="cf_1168496" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168496,document.forms[0].nome_1168496,document.forms[0].cognome_1168496,document.forms[0].comune_1168496,document.forms[0].data_1168496,'cf_1168496')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169023" name="nome_1169023" value="saverio"/></td> 
<td><input type="text" id="cognome_1169023" name="cognome_1169023" value="di martino"/></td> 
<td><input type="text" id="data_1169023" name="data_1169023" value="25/09/1978"/></td> 
<td><input type="radio" id="sessom_1169023" name="sesso_1169023" value="M" checked/> M
<input type="radio" id="sessof_1169023" name="sesso_1169023" value="F"/> F
</td>  
<td><input type="text" id="comune_1169023" name="comune_1169023" value="massa lubrenze"/></td>
<td><input type="text" id="cf_1169023" name="cf_1169023" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169023,document.forms[0].nome_1169023,document.forms[0].cognome_1169023,document.forms[0].comune_1169023,document.forms[0].data_1169023,'cf_1169023')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166704" name="nome_1166704" value="andrea"/></td> 
<td><input type="text" id="cognome_1166704" name="cognome_1166704" value="di giacomo"/></td> 
<td><input type="text" id="data_1166704" name="data_1166704" value="25/01/1963"/></td> 
<td><input type="radio" id="sessom_1166704" name="sesso_1166704" value="M" checked/> M
<input type="radio" id="sessof_1166704" name="sesso_1166704" value="F"/> F
</td>  
<td><input type="text" id="comune_1166704" name="comune_1166704" value="MERCATO SAN SEVERIN"/></td>
<td><input type="text" id="cf_1166704" name="cf_1166704" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166704,document.forms[0].nome_1166704,document.forms[0].cognome_1166704,document.forms[0].comune_1166704,document.forms[0].data_1166704,'cf_1166704')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1009082" name="nome_1009082" value="nanni"/></td> 
<td><input type="text" id="cognome_1009082" name="cognome_1009082" value="bruno"/></td> 
<td><input type="text" id="data_1009082" name="data_1009082" value="08/06/1942"/></td> 
<td><input type="radio" id="sessom_1009082" name="sesso_1009082" value="M" checked/> M
<input type="radio" id="sessof_1009082" name="sesso_1009082" value="F"/> F
</td>  
<td><input type="text" id="comune_1009082" name="comune_1009082" value="sanza (sa)"/></td>
<td><input type="text" id="cf_1009082" name="cf_1009082" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1009082,document.forms[0].nome_1009082,document.forms[0].cognome_1009082,document.forms[0].comune_1009082,document.forms[0].data_1009082,'cf_1009082')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1083588" name="nome_1083588" value="raffaele salvatore"/></td> 
<td><input type="text" id="cognome_1083588" name="cognome_1083588" value="turco"/></td> 
<td><input type="text" id="data_1083588" name="data_1083588" value="21/08/1964"/></td> 
<td><input type="radio" id="sessom_1083588" name="sesso_1083588" value="M" checked/> M
<input type="radio" id="sessof_1083588" name="sesso_1083588" value="F"/> F
</td>  
<td><input type="text" id="comune_1083588" name="comune_1083588" value="villa literno"/></td>
<td><input type="text" id="cf_1083588" name="cf_1083588" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1083588,document.forms[0].nome_1083588,document.forms[0].cognome_1083588,document.forms[0].comune_1083588,document.forms[0].data_1083588,'cf_1083588')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169017" name="nome_1169017" value="federico"/></td> 
<td><input type="text" id="cognome_1169017" name="cognome_1169017" value="pesa"/></td> 
<td><input type="text" id="data_1169017" name="data_1169017" value="23/11/1963"/></td> 
<td><input type="radio" id="sessom_1169017" name="sesso_1169017" value="M" checked/> M
<input type="radio" id="sessof_1169017" name="sesso_1169017" value="F"/> F
</td>  
<td><input type="text" id="comune_1169017" name="comune_1169017" value="napoli"/></td>
<td><input type="text" id="cf_1169017" name="cf_1169017" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169017,document.forms[0].nome_1169017,document.forms[0].cognome_1169017,document.forms[0].comune_1169017,document.forms[0].data_1169017,'cf_1169017')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1098830" name="nome_1098830" value="FELICE"/></td> 
<td><input type="text" id="cognome_1098830" name="cognome_1098830" value="RUSSO"/></td> 
<td><input type="text" id="data_1098830" name="data_1098830" value="28/05/1958"/></td> 
<td><input type="radio" id="sessom_1098830" name="sesso_1098830" value="M" checked/> M
<input type="radio" id="sessof_1098830" name="sesso_1098830" value="F"/> F
</td>  
<td><input type="text" id="comune_1098830" name="comune_1098830" value="NOCERA INFERIORE (SA)"/></td>
<td><input type="text" id="cf_1098830" name="cf_1098830" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1098830,document.forms[0].nome_1098830,document.forms[0].cognome_1098830,document.forms[0].comune_1098830,document.forms[0].data_1098830,'cf_1098830')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1048356" name="nome_1048356" value="marina"/></td> 
<td><input type="text" id="cognome_1048356" name="cognome_1048356" value="cauceglia"/></td> 
<td><input type="text" id="data_1048356" name="data_1048356" value="31/07/1963"/></td> 
<td><input type="radio" id="sessom_1048356" name="sesso_1048356" value="M"/> M
<input type="radio" id="sessof_1048356" name="sesso_1048356" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1048356" name="comune_1048356" value="portci (na)"/></td>
<td><input type="text" id="cf_1048356" name="cf_1048356" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1048356,document.forms[0].nome_1048356,document.forms[0].cognome_1048356,document.forms[0].comune_1048356,document.forms[0].data_1048356,'cf_1048356')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168939" name="nome_1168939" value="nicola"/></td> 
<td><input type="text" id="cognome_1168939" name="cognome_1168939" value="de pretis"/></td> 
<td><input type="text" id="data_1168939" name="data_1168939" value="05/05/1965"/></td> 
<td><input type="radio" id="sessom_1168939" name="sesso_1168939" value="M" checked/> M
<input type="radio" id="sessof_1168939" name="sesso_1168939" value="F"/> F
</td>  
<td><input type="text" id="comune_1168939" name="comune_1168939" value="napoli"/></td>
<td><input type="text" id="cf_1168939" name="cf_1168939" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168939,document.forms[0].nome_1168939,document.forms[0].cognome_1168939,document.forms[0].comune_1168939,document.forms[0].data_1168939,'cf_1168939')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164961" name="nome_1164961" value="RAFFAELE"/></td> 
<td><input type="text" id="cognome_1164961" name="cognome_1164961" value="CAVAGNOLI"/></td> 
<td><input type="text" id="data_1164961" name="data_1164961" value="28/02/1939"/></td> 
<td><input type="radio" id="sessom_1164961" name="sesso_1164961" value="M" checked/> M
<input type="radio" id="sessof_1164961" name="sesso_1164961" value="F"/> F
</td>  
<td><input type="text" id="comune_1164961" name="comune_1164961" value="COSENZA"/></td>
<td><input type="text" id="cf_1164961" name="cf_1164961" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164961,document.forms[0].nome_1164961,document.forms[0].cognome_1164961,document.forms[0].comune_1164961,document.forms[0].data_1164961,'cf_1164961')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1043668" name="nome_1043668" value="marina"/></td> 
<td><input type="text" id="cognome_1043668" name="cognome_1043668" value="cauceglia"/></td> 
<td><input type="text" id="data_1043668" name="data_1043668" value="31/07/1963"/></td> 
<td><input type="radio" id="sessom_1043668" name="sesso_1043668" value="M"/> M
<input type="radio" id="sessof_1043668" name="sesso_1043668" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1043668" name="comune_1043668" value="portici"/></td>
<td><input type="text" id="cf_1043668" name="cf_1043668" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1043668,document.forms[0].nome_1043668,document.forms[0].cognome_1043668,document.forms[0].comune_1043668,document.forms[0].data_1043668,'cf_1043668')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168395" name="nome_1168395" value="antonio"/></td> 
<td><input type="text" id="cognome_1168395" name="cognome_1168395" value="vitolo"/></td> 
<td><input type="text" id="data_1168395" name="data_1168395" value="04/05/1962"/></td> 
<td><input type="radio" id="sessom_1168395" name="sesso_1168395" value="M" checked/> M
<input type="radio" id="sessof_1168395" name="sesso_1168395" value="F"/> F
</td>  
<td><input type="text" id="comune_1168395" name="comune_1168395" value="salerno"/></td>
<td><input type="text" id="cf_1168395" name="cf_1168395" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168395,document.forms[0].nome_1168395,document.forms[0].cognome_1168395,document.forms[0].comune_1168395,document.forms[0].data_1168395,'cf_1168395')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1012544" name="nome_1012544" value="salvatore"/></td> 
<td><input type="text" id="cognome_1012544" name="cognome_1012544" value="milioti"/></td> 
<td><input type="text" id="data_1012544" name="data_1012544" value="04/06/1954"/></td> 
<td><input type="radio" id="sessom_1012544" name="sesso_1012544" value="M" checked/> M
<input type="radio" id="sessof_1012544" name="sesso_1012544" value="F"/> F
</td>  
<td><input type="text" id="comune_1012544" name="comune_1012544" value="milazzo"/></td>
<td><input type="text" id="cf_1012544" name="cf_1012544" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1012544,document.forms[0].nome_1012544,document.forms[0].cognome_1012544,document.forms[0].comune_1012544,document.forms[0].data_1012544,'cf_1012544')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1104087" name="nome_1104087" value="PIA "/></td> 
<td><input type="text" id="cognome_1104087" name="cognome_1104087" value="CARDONE"/></td> 
<td><input type="text" id="data_1104087" name="data_1104087" value="07/10/1944"/></td> 
<td><input type="radio" id="sessom_1104087" name="sesso_1104087" value="M"/> M
<input type="radio" id="sessof_1104087" name="sesso_1104087" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1104087" name="comune_1104087" value="BENEVENTO"/></td>
<td><input type="text" id="cf_1104087" name="cf_1104087" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1104087,document.forms[0].nome_1104087,document.forms[0].cognome_1104087,document.forms[0].comune_1104087,document.forms[0].data_1104087,'cf_1104087')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168354" name="nome_1168354" value="anna"/></td> 
<td><input type="text" id="cognome_1168354" name="cognome_1168354" value="maglione"/></td> 
<td><input type="text" id="data_1168354" name="data_1168354" value="01/01/1973"/></td> 
<td><input type="radio" id="sessom_1168354" name="sesso_1168354" value="M"/> M
<input type="radio" id="sessof_1168354" name="sesso_1168354" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1168354" name="comune_1168354" value="napoli"/></td>
<td><input type="text" id="cf_1168354" name="cf_1168354" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168354,document.forms[0].nome_1168354,document.forms[0].cognome_1168354,document.forms[0].comune_1168354,document.forms[0].data_1168354,'cf_1168354')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1041872" name="nome_1041872" value="ottavio"/></td> 
<td><input type="text" id="cognome_1041872" name="cognome_1041872" value="di gaeta"/></td> 
<td><input type="text" id="data_1041872" name="data_1041872" value="06/05/1962"/></td> 
<td><input type="radio" id="sessom_1041872" name="sesso_1041872" value="M" checked/> M
<input type="radio" id="sessof_1041872" name="sesso_1041872" value="F"/> F
</td>  
<td><input type="text" id="comune_1041872" name="comune_1041872" value="salerno"/></td>
<td><input type="text" id="cf_1041872" name="cf_1041872" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1041872,document.forms[0].nome_1041872,document.forms[0].cognome_1041872,document.forms[0].comune_1041872,document.forms[0].data_1041872,'cf_1041872')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1011839" name="nome_1011839" value="carmelina "/></td> 
<td><input type="text" id="cognome_1011839" name="cognome_1011839" value="laudisio"/></td> 
<td><input type="text" id="data_1011839" name="data_1011839" value="24/12/1933"/></td> 
<td><input type="radio" id="sessom_1011839" name="sesso_1011839" value="M"/> M
<input type="radio" id="sessof_1011839" name="sesso_1011839" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1011839" name="comune_1011839" value="sarno"/></td>
<td><input type="text" id="cf_1011839" name="cf_1011839" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1011839,document.forms[0].nome_1011839,document.forms[0].cognome_1011839,document.forms[0].comune_1011839,document.forms[0].data_1011839,'cf_1011839')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164005" name="nome_1164005" value="rita"/></td> 
<td><input type="text" id="cognome_1164005" name="cognome_1164005" value="chioccara"/></td> 
<td><input type="text" id="data_1164005" name="data_1164005" value="16/11/1948"/></td> 
<td><input type="radio" id="sessom_1164005" name="sesso_1164005" value="M"/> M
<input type="radio" id="sessof_1164005" name="sesso_1164005" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1164005" name="comune_1164005" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164005" name="cf_1164005" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164005,document.forms[0].nome_1164005,document.forms[0].cognome_1164005,document.forms[0].comune_1164005,document.forms[0].data_1164005,'cf_1164005')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1166101" name="nome_1166101" value="GIUSEPPE"/></td> 
<td><input type="text" id="cognome_1166101" name="cognome_1166101" value="PANTANO"/></td> 
<td><input type="text" id="data_1166101" name="data_1166101" value="23/04/1981"/></td> 
<td><input type="radio" id="sessom_1166101" name="sesso_1166101" value="M" checked/> M
<input type="radio" id="sessof_1166101" name="sesso_1166101" value="F"/> F
</td>  
<td><input type="text" id="comune_1166101" name="comune_1166101" value="napoli"/></td>
<td><input type="text" id="cf_1166101" name="cf_1166101" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166101,document.forms[0].nome_1166101,document.forms[0].cognome_1166101,document.forms[0].comune_1166101,document.forms[0].data_1166101,'cf_1166101')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194335" name="nome_1194335" value="rosa"/></td> 
<td><input type="text" id="cognome_1194335" name="cognome_1194335" value="pisciotta"/></td> 
<td><input type="text" id="data_1194335" name="data_1194335" value="18/04/1969"/></td> 
<td><input type="radio" id="sessom_1194335" name="sesso_1194335" value="M"/> M
<input type="radio" id="sessof_1194335" name="sesso_1194335" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194335" name="comune_1194335" value="nocera inferiore"/></td>
<td><input type="text" id="cf_1194335" name="cf_1194335" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194335,document.forms[0].nome_1194335,document.forms[0].cognome_1194335,document.forms[0].comune_1194335,document.forms[0].data_1194335,'cf_1194335')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169991" name="nome_1169991" value="catello"/></td> 
<td><input type="text" id="cognome_1169991" name="cognome_1169991" value="sorrentino"/></td> 
<td><input type="text" id="data_1169991" name="data_1169991" value="19/03/1961"/></td> 
<td><input type="radio" id="sessom_1169991" name="sesso_1169991" value="M" checked/> M
<input type="radio" id="sessof_1169991" name="sesso_1169991" value="F"/> F
</td>  
<td><input type="text" id="comune_1169991" name="comune_1169991" value="castellammare di stabia"/></td>
<td><input type="text" id="cf_1169991" name="cf_1169991" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169991,document.forms[0].nome_1169991,document.forms[0].cognome_1169991,document.forms[0].comune_1169991,document.forms[0].data_1169991,'cf_1169991')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186880" name="nome_1186880" value="annamaria"/></td> 
<td><input type="text" id="cognome_1186880" name="cognome_1186880" value="di maggio"/></td> 
<td><input type="text" id="data_1186880" name="data_1186880" value="30/09/1959"/></td> 
<td><input type="radio" id="sessom_1186880" name="sesso_1186880" value="M"/> M
<input type="radio" id="sessof_1186880" name="sesso_1186880" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1186880" name="comune_1186880" value="napoli"/></td>
<td><input type="text" id="cf_1186880" name="cf_1186880" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186880,document.forms[0].nome_1186880,document.forms[0].cognome_1186880,document.forms[0].comune_1186880,document.forms[0].data_1186880,'cf_1186880')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1184234" name="nome_1184234" value="serena"/></td> 
<td><input type="text" id="cognome_1184234" name="cognome_1184234" value="ripa"/></td> 
<td><input type="text" id="data_1184234" name="data_1184234" value="26/09/1977"/></td> 
<td><input type="radio" id="sessom_1184234" name="sesso_1184234" value="M"/> M
<input type="radio" id="sessof_1184234" name="sesso_1184234" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1184234" name="comune_1184234" value="eboli"/></td>
<td><input type="text" id="cf_1184234" name="cf_1184234" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1184234,document.forms[0].nome_1184234,document.forms[0].cognome_1184234,document.forms[0].comune_1184234,document.forms[0].data_1184234,'cf_1184234')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186359" name="nome_1186359" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1186359" name="cognome_1186359" value="scognamiglio"/></td> 
<td><input type="text" id="data_1186359" name="data_1186359" value="19/05/1977"/></td> 
<td><input type="radio" id="sessom_1186359" name="sesso_1186359" value="M" checked/> M
<input type="radio" id="sessof_1186359" name="sesso_1186359" value="F"/> F
</td>  
<td><input type="text" id="comune_1186359" name="comune_1186359" value="torre del greco"/></td>
<td><input type="text" id="cf_1186359" name="cf_1186359" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186359,document.forms[0].nome_1186359,document.forms[0].cognome_1186359,document.forms[0].comune_1186359,document.forms[0].data_1186359,'cf_1186359')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193571" name="nome_1193571" value="carlo"/></td> 
<td><input type="text" id="cognome_1193571" name="cognome_1193571" value="lentini"/></td> 
<td><input type="text" id="data_1193571" name="data_1193571" value="28/05/2018"/></td> 
<td><input type="radio" id="sessom_1193571" name="sesso_1193571" value="M" checked/> M
<input type="radio" id="sessof_1193571" name="sesso_1193571" value="F"/> F
</td>  
<td><input type="text" id="comune_1193571" name="comune_1193571" value="napoli"/></td>
<td><input type="text" id="cf_1193571" name="cf_1193571" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193571,document.forms[0].nome_1193571,document.forms[0].cognome_1193571,document.forms[0].comune_1193571,document.forms[0].data_1193571,'cf_1193571')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167652" name="nome_1167652" value="pasquale"/></td> 
<td><input type="text" id="cognome_1167652" name="cognome_1167652" value="apostolico"/></td> 
<td><input type="text" id="data_1167652" name="data_1167652" value="16/06/1983"/></td> 
<td><input type="radio" id="sessom_1167652" name="sesso_1167652" value="M" checked/> M
<input type="radio" id="sessof_1167652" name="sesso_1167652" value="F"/> F
</td>  
<td><input type="text" id="comune_1167652" name="comune_1167652" value="battipaglia"/></td>
<td><input type="text" id="cf_1167652" name="cf_1167652" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167652,document.forms[0].nome_1167652,document.forms[0].cognome_1167652,document.forms[0].comune_1167652,document.forms[0].data_1167652,'cf_1167652')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181288" name="nome_1181288" value="barabara "/></td> 
<td><input type="text" id="cognome_1181288" name="cognome_1181288" value="ninni"/></td> 
<td><input type="text" id="data_1181288" name="data_1181288" value="02/07/1970"/></td> 
<td><input type="radio" id="sessom_1181288" name="sesso_1181288" value="M"/> M
<input type="radio" id="sessof_1181288" name="sesso_1181288" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181288" name="comune_1181288" value="napoli"/></td>
<td><input type="text" id="cf_1181288" name="cf_1181288" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181288,document.forms[0].nome_1181288,document.forms[0].cognome_1181288,document.forms[0].comune_1181288,document.forms[0].data_1181288,'cf_1181288')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193453" name="nome_1193453" value="paolo"/></td> 
<td><input type="text" id="cognome_1193453" name="cognome_1193453" value="gadolo"/></td> 
<td><input type="text" id="data_1193453" name="data_1193453" value="05/06/1976"/></td> 
<td><input type="radio" id="sessom_1193453" name="sesso_1193453" value="M" checked/> M
<input type="radio" id="sessof_1193453" name="sesso_1193453" value="F"/> F
</td>  
<td><input type="text" id="comune_1193453" name="comune_1193453" value="perugia"/></td>
<td><input type="text" id="cf_1193453" name="cf_1193453" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193453,document.forms[0].nome_1193453,document.forms[0].cognome_1193453,document.forms[0].comune_1193453,document.forms[0].data_1193453,'cf_1193453')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1192060" name="nome_1192060" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1192060" name="cognome_1192060" value="lombardi"/></td> 
<td><input type="text" id="data_1192060" name="data_1192060" value="18/11/1969"/></td> 
<td><input type="radio" id="sessom_1192060" name="sesso_1192060" value="M" checked/> M
<input type="radio" id="sessof_1192060" name="sesso_1192060" value="F"/> F
</td>  
<td><input type="text" id="comune_1192060" name="comune_1192060" value=""/></td>
<td><input type="text" id="cf_1192060" name="cf_1192060" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192060,document.forms[0].nome_1192060,document.forms[0].cognome_1192060,document.forms[0].comune_1192060,document.forms[0].data_1192060,'cf_1192060')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1183670" name="nome_1183670" value="domenico"/></td> 
<td><input type="text" id="cognome_1183670" name="cognome_1183670" value="BARTOLOMEO DE IURI"/></td> 
<td><input type="text" id="data_1183670" name="data_1183670" value="10/08/1956"/></td> 
<td><input type="radio" id="sessom_1183670" name="sesso_1183670" value="M" checked/> M
<input type="radio" id="sessof_1183670" name="sesso_1183670" value="F"/> F
</td>  
<td><input type="text" id="comune_1183670" name="comune_1183670" value="napoli"/></td>
<td><input type="text" id="cf_1183670" name="cf_1183670" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1183670,document.forms[0].nome_1183670,document.forms[0].cognome_1183670,document.forms[0].comune_1183670,document.forms[0].data_1183670,'cf_1183670')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042732" name="nome_1042732" value="antonio"/></td> 
<td><input type="text" id="cognome_1042732" name="cognome_1042732" value="verdoliva"/></td> 
<td><input type="text" id="data_1042732" name="data_1042732" value="31/10/1964"/></td> 
<td><input type="radio" id="sessom_1042732" name="sesso_1042732" value="M" checked/> M
<input type="radio" id="sessof_1042732" name="sesso_1042732" value="F"/> F
</td>  
<td><input type="text" id="comune_1042732" name="comune_1042732" value="napoli"/></td>
<td><input type="text" id="cf_1042732" name="cf_1042732" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042732,document.forms[0].nome_1042732,document.forms[0].cognome_1042732,document.forms[0].comune_1042732,document.forms[0].data_1042732,'cf_1042732')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172338" name="nome_1172338" value="adriana"/></td> 
<td><input type="text" id="cognome_1172338" name="cognome_1172338" value="abbatiello"/></td> 
<td><input type="text" id="data_1172338" name="data_1172338" value="17/07/1970"/></td> 
<td><input type="radio" id="sessom_1172338" name="sesso_1172338" value="M"/> M
<input type="radio" id="sessof_1172338" name="sesso_1172338" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1172338" name="comune_1172338" value="durazzano"/></td>
<td><input type="text" id="cf_1172338" name="cf_1172338" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172338,document.forms[0].nome_1172338,document.forms[0].cognome_1172338,document.forms[0].comune_1172338,document.forms[0].data_1172338,'cf_1172338')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166105" name="nome_1166105" value="MARIA TERESA"/></td> 
<td><input type="text" id="cognome_1166105" name="cognome_1166105" value="PECORINI"/></td> 
<td><input type="text" id="data_1166105" name="data_1166105" value="17/06/1949"/></td> 
<td><input type="radio" id="sessom_1166105" name="sesso_1166105" value="M"/> M
<input type="radio" id="sessof_1166105" name="sesso_1166105" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166105" name="comune_1166105" value="napoli"/></td>
<td><input type="text" id="cf_1166105" name="cf_1166105" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166105,document.forms[0].nome_1166105,document.forms[0].cognome_1166105,document.forms[0].comune_1166105,document.forms[0].data_1166105,'cf_1166105')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1044425" name="nome_1044425" value="francesco"/></td> 
<td><input type="text" id="cognome_1044425" name="cognome_1044425" value="sorrentino"/></td> 
<td><input type="text" id="data_1044425" name="data_1044425" value="29/01/1972"/></td> 
<td><input type="radio" id="sessom_1044425" name="sesso_1044425" value="M" checked/> M
<input type="radio" id="sessof_1044425" name="sesso_1044425" value="F"/> F
</td>  
<td><input type="text" id="comune_1044425" name="comune_1044425" value="salerno"/></td>
<td><input type="text" id="cf_1044425" name="cf_1044425" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1044425,document.forms[0].nome_1044425,document.forms[0].cognome_1044425,document.forms[0].comune_1044425,document.forms[0].data_1044425,'cf_1044425')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1043284" name="nome_1043284" value="antonietta"/></td> 
<td><input type="text" id="cognome_1043284" name="cognome_1043284" value="aliberti"/></td> 
<td><input type="text" id="data_1043284" name="data_1043284" value="13/01/1977"/></td> 
<td><input type="radio" id="sessom_1043284" name="sesso_1043284" value="M"/> M
<input type="radio" id="sessof_1043284" name="sesso_1043284" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1043284" name="comune_1043284" value="pagani"/></td>
<td><input type="text" id="cf_1043284" name="cf_1043284" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1043284,document.forms[0].nome_1043284,document.forms[0].cognome_1043284,document.forms[0].comune_1043284,document.forms[0].data_1043284,'cf_1043284')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181284" name="nome_1181284" value="SALVATORE"/></td> 
<td><input type="text" id="cognome_1181284" name="cognome_1181284" value="RUSSO"/></td> 
<td><input type="text" id="data_1181284" name="data_1181284" value="03/07/1980"/></td> 
<td><input type="radio" id="sessom_1181284" name="sesso_1181284" value="M" checked/> M
<input type="radio" id="sessof_1181284" name="sesso_1181284" value="F"/> F
</td>  
<td><input type="text" id="comune_1181284" name="comune_1181284" value="napoli"/></td>
<td><input type="text" id="cf_1181284" name="cf_1181284" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181284,document.forms[0].nome_1181284,document.forms[0].cognome_1181284,document.forms[0].comune_1181284,document.forms[0].data_1181284,'cf_1181284')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1165956" name="nome_1165956" value="MICHELE"/></td> 
<td><input type="text" id="cognome_1165956" name="cognome_1165956" value="petrucci"/></td> 
<td><input type="text" id="data_1165956" name="data_1165956" value="02/10/1988"/></td> 
<td><input type="radio" id="sessom_1165956" name="sesso_1165956" value="M" checked/> M
<input type="radio" id="sessof_1165956" name="sesso_1165956" value="F"/> F
</td>  
<td><input type="text" id="comune_1165956" name="comune_1165956" value="londra"/></td>
<td><input type="text" id="cf_1165956" name="cf_1165956" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1165956,document.forms[0].nome_1165956,document.forms[0].cognome_1165956,document.forms[0].comune_1165956,document.forms[0].data_1165956,'cf_1165956')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042838" name="nome_1042838" value="dr.sapere"/></td> 
<td><input type="text" id="cognome_1042838" name="cognome_1042838" value=" luigi"/></td> 
<td><input type="text" id="data_1042838" name="data_1042838" value="06/04/1959"/></td> 
<td><input type="radio" id="sessom_1042838" name="sesso_1042838" value="M" checked/> M
<input type="radio" id="sessof_1042838" name="sesso_1042838" value="F"/> F
</td>  
<td><input type="text" id="comune_1042838" name="comune_1042838" value="salerno"/></td>
<td><input type="text" id="cf_1042838" name="cf_1042838" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042838,document.forms[0].nome_1042838,document.forms[0].cognome_1042838,document.forms[0].comune_1042838,document.forms[0].data_1042838,'cf_1042838')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168355" name="nome_1168355" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1168355" name="cognome_1168355" value="crispino"/></td> 
<td><input type="text" id="data_1168355" name="data_1168355" value="21/07/1958"/></td> 
<td><input type="radio" id="sessom_1168355" name="sesso_1168355" value="M" checked/> M
<input type="radio" id="sessof_1168355" name="sesso_1168355" value="F"/> F
</td>  
<td><input type="text" id="comune_1168355" name="comune_1168355" value="napoli"/></td>
<td><input type="text" id="cf_1168355" name="cf_1168355" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168355,document.forms[0].nome_1168355,document.forms[0].cognome_1168355,document.forms[0].comune_1168355,document.forms[0].data_1168355,'cf_1168355')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172342" name="nome_1172342" value="bruno"/></td> 
<td><input type="text" id="cognome_1172342" name="cognome_1172342" value="beneduce"/></td> 
<td><input type="text" id="data_1172342" name="data_1172342" value="04/10/1962"/></td> 
<td><input type="radio" id="sessom_1172342" name="sesso_1172342" value="M" checked/> M
<input type="radio" id="sessof_1172342" name="sesso_1172342" value="F"/> F
</td>  
<td><input type="text" id="comune_1172342" name="comune_1172342" value="napoli"/></td>
<td><input type="text" id="cf_1172342" name="cf_1172342" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172342,document.forms[0].nome_1172342,document.forms[0].cognome_1172342,document.forms[0].comune_1172342,document.forms[0].data_1172342,'cf_1172342')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1098835" name="nome_1098835" value="SALVATORE"/></td> 
<td><input type="text" id="cognome_1098835" name="cognome_1098835" value="FALCONE"/></td> 
<td><input type="text" id="data_1098835" name="data_1098835" value="20/06/1965"/></td> 
<td><input type="radio" id="sessom_1098835" name="sesso_1098835" value="M" checked/> M
<input type="radio" id="sessof_1098835" name="sesso_1098835" value="F"/> F
</td>  
<td><input type="text" id="comune_1098835" name="comune_1098835" value="SANT'EGIDIO DEL MONTE ALBINO (SA)"/></td>
<td><input type="text" id="cf_1098835" name="cf_1098835" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1098835,document.forms[0].nome_1098835,document.forms[0].cognome_1098835,document.forms[0].comune_1098835,document.forms[0].data_1098835,'cf_1098835')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1186895" name="nome_1186895" value="teresa"/></td> 
<td><input type="text" id="cognome_1186895" name="cognome_1186895" value="ferrari"/></td> 
<td><input type="text" id="data_1186895" name="data_1186895" value="27/07/1935"/></td> 
<td><input type="radio" id="sessom_1186895" name="sesso_1186895" value="M"/> M
<input type="radio" id="sessof_1186895" name="sesso_1186895" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1186895" name="comune_1186895" value="napoli"/></td>
<td><input type="text" id="cf_1186895" name="cf_1186895" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186895,document.forms[0].nome_1186895,document.forms[0].cognome_1186895,document.forms[0].comune_1186895,document.forms[0].data_1186895,'cf_1186895')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172506" name="nome_1172506" value="angelo"/></td> 
<td><input type="text" id="cognome_1172506" name="cognome_1172506" value="padovani"/></td> 
<td><input type="text" id="data_1172506" name="data_1172506" value="27/06/1971"/></td> 
<td><input type="radio" id="sessom_1172506" name="sesso_1172506" value="M" checked/> M
<input type="radio" id="sessof_1172506" name="sesso_1172506" value="F"/> F
</td>  
<td><input type="text" id="comune_1172506" name="comune_1172506" value="napoli"/></td>
<td><input type="text" id="cf_1172506" name="cf_1172506" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172506,document.forms[0].nome_1172506,document.forms[0].cognome_1172506,document.forms[0].comune_1172506,document.forms[0].data_1172506,'cf_1172506')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1002697" name="nome_1002697" value="bruno"/></td> 
<td><input type="text" id="cognome_1002697" name="cognome_1002697" value="pifano"/></td> 
<td><input type="text" id="data_1002697" name="data_1002697" value="02/09/1960"/></td> 
<td><input type="radio" id="sessom_1002697" name="sesso_1002697" value="M" checked/> M
<input type="radio" id="sessof_1002697" name="sesso_1002697" value="F"/> F
</td>  
<td><input type="text" id="comune_1002697" name="comune_1002697" value="Salerno"/></td>
<td><input type="text" id="cf_1002697" name="cf_1002697" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1002697,document.forms[0].nome_1002697,document.forms[0].cognome_1002697,document.forms[0].comune_1002697,document.forms[0].data_1002697,'cf_1002697')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164410" name="nome_1164410" value="MICHELE"/></td> 
<td><input type="text" id="cognome_1164410" name="cognome_1164410" value="DI IORIO"/></td> 
<td><input type="text" id="data_1164410" name="data_1164410" value="18/08/1947"/></td> 
<td><input type="radio" id="sessom_1164410" name="sesso_1164410" value="M" checked/> M
<input type="radio" id="sessof_1164410" name="sesso_1164410" value="F"/> F
</td>  
<td><input type="text" id="comune_1164410" name="comune_1164410" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164410" name="cf_1164410" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164410,document.forms[0].nome_1164410,document.forms[0].cognome_1164410,document.forms[0].comune_1164410,document.forms[0].data_1164410,'cf_1164410')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193569" name="nome_1193569" value="angela"/></td> 
<td><input type="text" id="cognome_1193569" name="cognome_1193569" value="maucione"/></td> 
<td><input type="text" id="data_1193569" name="data_1193569" value="27/12/1958"/></td> 
<td><input type="radio" id="sessom_1193569" name="sesso_1193569" value="M"/> M
<input type="radio" id="sessof_1193569" name="sesso_1193569" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1193569" name="comune_1193569" value="salerno"/></td>
<td><input type="text" id="cf_1193569" name="cf_1193569" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193569,document.forms[0].nome_1193569,document.forms[0].cognome_1193569,document.forms[0].comune_1193569,document.forms[0].data_1193569,'cf_1193569')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193882" name="nome_1193882" value="carmine"/></td> 
<td><input type="text" id="cognome_1193882" name="cognome_1193882" value="ruggiero"/></td> 
<td><input type="text" id="data_1193882" name="data_1193882" value="27/02/1988"/></td> 
<td><input type="radio" id="sessom_1193882" name="sesso_1193882" value="M" checked/> M
<input type="radio" id="sessof_1193882" name="sesso_1193882" value="F"/> F
</td>  
<td><input type="text" id="comune_1193882" name="comune_1193882" value="napoli"/></td>
<td><input type="text" id="cf_1193882" name="cf_1193882" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193882,document.forms[0].nome_1193882,document.forms[0].cognome_1193882,document.forms[0].comune_1193882,document.forms[0].data_1193882,'cf_1193882')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169019" name="nome_1169019" value="valeria"/></td> 
<td><input type="text" id="cognome_1169019" name="cognome_1169019" value="nocerino"/></td> 
<td><input type="text" id="data_1169019" name="data_1169019" value="20/05/1988"/></td> 
<td><input type="radio" id="sessom_1169019" name="sesso_1169019" value="M"/> M
<input type="radio" id="sessof_1169019" name="sesso_1169019" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1169019" name="comune_1169019" value="napoli"/></td>
<td><input type="text" id="cf_1169019" name="cf_1169019" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169019,document.forms[0].nome_1169019,document.forms[0].cognome_1169019,document.forms[0].comune_1169019,document.forms[0].data_1169019,'cf_1169019')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1096737" name="nome_1096737" value="andrea"/></td> 
<td><input type="text" id="cognome_1096737" name="cognome_1096737" value="petteruti"/></td> 
<td><input type="text" id="data_1096737" name="data_1096737" value="08/06/1951"/></td> 
<td><input type="radio" id="sessom_1096737" name="sesso_1096737" value="M" checked/> M
<input type="radio" id="sessof_1096737" name="sesso_1096737" value="F"/> F
</td>  
<td><input type="text" id="comune_1096737" name="comune_1096737" value="teano"/></td>
<td><input type="text" id="cf_1096737" name="cf_1096737" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1096737,document.forms[0].nome_1096737,document.forms[0].cognome_1096737,document.forms[0].comune_1096737,document.forms[0].data_1096737,'cf_1096737')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1189143" name="nome_1189143" value="giovanni"/></td> 
<td><input type="text" id="cognome_1189143" name="cognome_1189143" value="ferlaino"/></td> 
<td><input type="text" id="data_1189143" name="data_1189143" value="30/11/1976"/></td> 
<td><input type="radio" id="sessom_1189143" name="sesso_1189143" value="M" checked/> M
<input type="radio" id="sessof_1189143" name="sesso_1189143" value="F"/> F
</td>  
<td><input type="text" id="comune_1189143" name="comune_1189143" value="napoli"/></td>
<td><input type="text" id="cf_1189143" name="cf_1189143" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189143,document.forms[0].nome_1189143,document.forms[0].cognome_1189143,document.forms[0].comune_1189143,document.forms[0].data_1189143,'cf_1189143')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1006630" name="nome_1006630" value="gerardo"/></td> 
<td><input type="text" id="cognome_1006630" name="cognome_1006630" value="pugliese"/></td> 
<td><input type="text" id="data_1006630" name="data_1006630" value="26/11/1962"/></td> 
<td><input type="radio" id="sessom_1006630" name="sesso_1006630" value="M" checked/> M
<input type="radio" id="sessof_1006630" name="sesso_1006630" value="F"/> F
</td>  
<td><input type="text" id="comune_1006630" name="comune_1006630" value="salerno"/></td>
<td><input type="text" id="cf_1006630" name="cf_1006630" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1006630,document.forms[0].nome_1006630,document.forms[0].cognome_1006630,document.forms[0].comune_1006630,document.forms[0].data_1006630,'cf_1006630')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193277" name="nome_1193277" value="maria carbone"/></td> 
<td><input type="text" id="cognome_1193277" name="cognome_1193277" value="carbone"/></td> 
<td><input type="text" id="data_1193277" name="data_1193277" value="26/04/1982"/></td> 
<td><input type="radio" id="sessom_1193277" name="sesso_1193277" value="M"/> M
<input type="radio" id="sessof_1193277" name="sesso_1193277" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1193277" name="comune_1193277" value="san gennaro vesuviano"/></td>
<td><input type="text" id="cf_1193277" name="cf_1193277" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193277,document.forms[0].nome_1193277,document.forms[0].cognome_1193277,document.forms[0].comune_1193277,document.forms[0].data_1193277,'cf_1193277')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1172137" name="nome_1172137" value="elio"/></td> 
<td><input type="text" id="cognome_1172137" name="cognome_1172137" value="de falco"/></td> 
<td><input type="text" id="data_1172137" name="data_1172137" value="20/02/1964"/></td> 
<td><input type="radio" id="sessom_1172137" name="sesso_1172137" value="M" checked/> M
<input type="radio" id="sessof_1172137" name="sesso_1172137" value="F"/> F
</td>  
<td><input type="text" id="comune_1172137" name="comune_1172137" value="napoli"/></td>
<td><input type="text" id="cf_1172137" name="cf_1172137" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1172137,document.forms[0].nome_1172137,document.forms[0].cognome_1172137,document.forms[0].comune_1172137,document.forms[0].data_1172137,'cf_1172137')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164004" name="nome_1164004" value="michele mario"/></td> 
<td><input type="text" id="cognome_1164004" name="cognome_1164004" value="ricciardi"/></td> 
<td><input type="text" id="data_1164004" name="data_1164004" value="24/10/1943"/></td> 
<td><input type="radio" id="sessom_1164004" name="sesso_1164004" value="M" checked/> M
<input type="radio" id="sessof_1164004" name="sesso_1164004" value="F"/> F
</td>  
<td><input type="text" id="comune_1164004" name="comune_1164004" value="calitri"/></td>
<td><input type="text" id="cf_1164004" name="cf_1164004" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164004,document.forms[0].nome_1164004,document.forms[0].cognome_1164004,document.forms[0].comune_1164004,document.forms[0].data_1164004,'cf_1164004')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1168937" name="nome_1168937" value="flavia"/></td> 
<td><input type="text" id="cognome_1168937" name="cognome_1168937" value="BOCCHETTI"/></td> 
<td><input type="text" id="data_1168937" name="data_1168937" value="01/01/1941"/></td> 
<td><input type="radio" id="sessom_1168937" name="sesso_1168937" value="M"/> M
<input type="radio" id="sessof_1168937" name="sesso_1168937" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1168937" name="comune_1168937" value="napoli"/></td>
<td><input type="text" id="cf_1168937" name="cf_1168937" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168937,document.forms[0].nome_1168937,document.forms[0].cognome_1168937,document.forms[0].comune_1168937,document.forms[0].data_1168937,'cf_1168937')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1174986" name="nome_1174986" value="paolo"/></td> 
<td><input type="text" id="cognome_1174986" name="cognome_1174986" value="congedo"/></td> 
<td><input type="text" id="data_1174986" name="data_1174986" value="26/03/1962"/></td> 
<td><input type="radio" id="sessom_1174986" name="sesso_1174986" value="M" checked/> M
<input type="radio" id="sessof_1174986" name="sesso_1174986" value="F"/> F
</td>  
<td><input type="text" id="comune_1174986" name="comune_1174986" value="NAPOLI"/></td>
<td><input type="text" id="cf_1174986" name="cf_1174986" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1174986,document.forms[0].nome_1174986,document.forms[0].cognome_1174986,document.forms[0].comune_1174986,document.forms[0].data_1174986,'cf_1174986')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193265" name="nome_1193265" value="fabrizio"/></td> 
<td><input type="text" id="cognome_1193265" name="cognome_1193265" value="porzio"/></td> 
<td><input type="text" id="data_1193265" name="data_1193265" value="27/08/1967"/></td> 
<td><input type="radio" id="sessom_1193265" name="sesso_1193265" value="M" checked/> M
<input type="radio" id="sessof_1193265" name="sesso_1193265" value="F"/> F
</td>  
<td><input type="text" id="comune_1193265" name="comune_1193265" value="napoli"/></td>
<td><input type="text" id="cf_1193265" name="cf_1193265" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193265,document.forms[0].nome_1193265,document.forms[0].cognome_1193265,document.forms[0].comune_1193265,document.forms[0].data_1193265,'cf_1193265')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181555" name="nome_1181555" value="roberta"/></td> 
<td><input type="text" id="cognome_1181555" name="cognome_1181555" value="d'angelo"/></td> 
<td><input type="text" id="data_1181555" name="data_1181555" value="20/07/1969"/></td> 
<td><input type="radio" id="sessom_1181555" name="sesso_1181555" value="M"/> M
<input type="radio" id="sessof_1181555" name="sesso_1181555" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181555" name="comune_1181555" value="napoli"/></td>
<td><input type="text" id="cf_1181555" name="cf_1181555" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181555,document.forms[0].nome_1181555,document.forms[0].cognome_1181555,document.forms[0].comune_1181555,document.forms[0].data_1181555,'cf_1181555')"/></td>
</tr>



<tr>
<td><input type="text" id="nome_1167729" name="nome_1167729" value="maurizio"/></td> 
<td><input type="text" id="cognome_1167729" name="cognome_1167729" value="alfani"/></td> 
<td><input type="text" id="data_1167729" name="data_1167729" value="28/08/1952"/></td> 
<td><input type="radio" id="sessom_1167729" name="sesso_1167729" value="M" checked/> M
<input type="radio" id="sessof_1167729" name="sesso_1167729" value="F"/> F
</td>  
<td><input type="text" id="comune_1167729" name="comune_1167729" value="napoli"/></td>
<td><input type="text" id="cf_1167729" name="cf_1167729" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167729,document.forms[0].nome_1167729,document.forms[0].cognome_1167729,document.forms[0].comune_1167729,document.forms[0].data_1167729,'cf_1167729')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182742" name="nome_1182742" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1182742" name="cognome_1182742" value="d'amato"/></td> 
<td><input type="text" id="data_1182742" name="data_1182742" value="25/12/1940"/></td> 
<td><input type="radio" id="sessom_1182742" name="sesso_1182742" value="M" checked/> M
<input type="radio" id="sessof_1182742" name="sesso_1182742" value="F"/> F
</td>  
<td><input type="text" id="comune_1182742" name="comune_1182742" value="sassari"/></td>
<td><input type="text" id="cf_1182742" name="cf_1182742" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182742,document.forms[0].nome_1182742,document.forms[0].cognome_1182742,document.forms[0].comune_1182742,document.forms[0].data_1182742,'cf_1182742')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181170" name="nome_1181170" value="DONATELLA"/></td> 
<td><input type="text" id="cognome_1181170" name="cognome_1181170" value="SALERNO"/></td> 
<td><input type="text" id="data_1181170" name="data_1181170" value="03/10/1969"/></td> 
<td><input type="radio" id="sessom_1181170" name="sesso_1181170" value="M"/> M
<input type="radio" id="sessof_1181170" name="sesso_1181170" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181170" name="comune_1181170" value="napoli"/></td>
<td><input type="text" id="cf_1181170" name="cf_1181170" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181170,document.forms[0].nome_1181170,document.forms[0].cognome_1181170,document.forms[0].comune_1181170,document.forms[0].data_1181170,'cf_1181170')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185958" name="nome_1185958" value="domenico"/></td> 
<td><input type="text" id="cognome_1185958" name="cognome_1185958" value="iavarone"/></td> 
<td><input type="text" id="data_1185958" name="data_1185958" value="11/02/1976"/></td> 
<td><input type="radio" id="sessom_1185958" name="sesso_1185958" value="M" checked/> M
<input type="radio" id="sessof_1185958" name="sesso_1185958" value="F"/> F
</td>  
<td><input type="text" id="comune_1185958" name="comune_1185958" value="avellino"/></td>
<td><input type="text" id="cf_1185958" name="cf_1185958" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185958,document.forms[0].nome_1185958,document.forms[0].cognome_1185958,document.forms[0].comune_1185958,document.forms[0].data_1185958,'cf_1185958')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1176911" name="nome_1176911" value="giusy"/></td> 
<td><input type="text" id="cognome_1176911" name="cognome_1176911" value="paternuosto"/></td> 
<td><input type="text" id="data_1176911" name="data_1176911" value="02/06/1977"/></td> 
<td><input type="radio" id="sessom_1176911" name="sesso_1176911" value="M"/> M
<input type="radio" id="sessof_1176911" name="sesso_1176911" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1176911" name="comune_1176911" value="caserta"/></td>
<td><input type="text" id="cf_1176911" name="cf_1176911" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1176911,document.forms[0].nome_1176911,document.forms[0].cognome_1176911,document.forms[0].comune_1176911,document.forms[0].data_1176911,'cf_1176911')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167603" name="nome_1167603" value="giovanni"/></td> 
<td><input type="text" id="cognome_1167603" name="cognome_1167603" value="gaudio"/></td> 
<td><input type="text" id="data_1167603" name="data_1167603" value="15/02/1963"/></td> 
<td><input type="radio" id="sessom_1167603" name="sesso_1167603" value="M" checked/> M
<input type="radio" id="sessof_1167603" name="sesso_1167603" value="F"/> F
</td>  
<td><input type="text" id="comune_1167603" name="comune_1167603" value="napoli"/></td>
<td><input type="text" id="cf_1167603" name="cf_1167603" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167603,document.forms[0].nome_1167603,document.forms[0].cognome_1167603,document.forms[0].comune_1167603,document.forms[0].data_1167603,'cf_1167603')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1046164" name="nome_1046164" value="ANNA"/></td> 
<td><input type="text" id="cognome_1046164" name="cognome_1046164" value="ABATE"/></td> 
<td><input type="text" id="data_1046164" name="data_1046164" value="02/10/1973"/></td> 
<td><input type="radio" id="sessom_1046164" name="sesso_1046164" value="M"/> M
<input type="radio" id="sessof_1046164" name="sesso_1046164" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1046164" name="comune_1046164" value="NAPOLI"/></td>
<td><input type="text" id="cf_1046164" name="cf_1046164" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1046164,document.forms[0].nome_1046164,document.forms[0].cognome_1046164,document.forms[0].comune_1046164,document.forms[0].data_1046164,'cf_1046164')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1165282" name="nome_1165282" value="mariaclelia"/></td> 
<td><input type="text" id="cognome_1165282" name="cognome_1165282" value="visconti"/></td> 
<td><input type="text" id="data_1165282" name="data_1165282" value="27/12/1973"/></td> 
<td><input type="radio" id="sessom_1165282" name="sesso_1165282" value="M"/> M
<input type="radio" id="sessof_1165282" name="sesso_1165282" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1165282" name="comune_1165282" value="napoli"/></td>
<td><input type="text" id="cf_1165282" name="cf_1165282" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1165282,document.forms[0].nome_1165282,document.forms[0].cognome_1165282,document.forms[0].comune_1165282,document.forms[0].data_1165282,'cf_1165282')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1165061" name="nome_1165061" value="renato"/></td> 
<td><input type="text" id="cognome_1165061" name="cognome_1165061" value="de falco"/></td> 
<td><input type="text" id="data_1165061" name="data_1165061" value="16/12/1971"/></td> 
<td><input type="radio" id="sessom_1165061" name="sesso_1165061" value="M" checked/> M
<input type="radio" id="sessof_1165061" name="sesso_1165061" value="F"/> F
</td>  
<td><input type="text" id="comune_1165061" name="comune_1165061" value="NAPOLI"/></td>
<td><input type="text" id="cf_1165061" name="cf_1165061" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1165061,document.forms[0].nome_1165061,document.forms[0].cognome_1165061,document.forms[0].comune_1165061,document.forms[0].data_1165061,'cf_1165061')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168118" name="nome_1168118" value="felice"/></td> 
<td><input type="text" id="cognome_1168118" name="cognome_1168118" value="falco"/></td> 
<td><input type="text" id="data_1168118" name="data_1168118" value="11/07/1960"/></td> 
<td><input type="radio" id="sessom_1168118" name="sesso_1168118" value="M" checked/> M
<input type="radio" id="sessof_1168118" name="sesso_1168118" value="F"/> F
</td>  
<td><input type="text" id="comune_1168118" name="comune_1168118" value="napoli"/></td>
<td><input type="text" id="cf_1168118" name="cf_1168118" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168118,document.forms[0].nome_1168118,document.forms[0].cognome_1168118,document.forms[0].comune_1168118,document.forms[0].data_1168118,'cf_1168118')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166099" name="nome_1166099" value="IMMACOLATA"/></td> 
<td><input type="text" id="cognome_1166099" name="cognome_1166099" value="MONTANINO"/></td> 
<td><input type="text" id="data_1166099" name="data_1166099" value="01/12/1959"/></td> 
<td><input type="radio" id="sessom_1166099" name="sesso_1166099" value="M"/> M
<input type="radio" id="sessof_1166099" name="sesso_1166099" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166099" name="comune_1166099" value="napoli"/></td>
<td><input type="text" id="cf_1166099" name="cf_1166099" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166099,document.forms[0].nome_1166099,document.forms[0].cognome_1166099,document.forms[0].comune_1166099,document.forms[0].data_1166099,'cf_1166099')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1191841" name="nome_1191841" value="pasquale"/></td> 
<td><input type="text" id="cognome_1191841" name="cognome_1191841" value="angelino"/></td> 
<td><input type="text" id="data_1191841" name="data_1191841" value="09/12/1956"/></td> 
<td><input type="radio" id="sessom_1191841" name="sesso_1191841" value="M" checked/> M
<input type="radio" id="sessof_1191841" name="sesso_1191841" value="F"/> F
</td>  
<td><input type="text" id="comune_1191841" name="comune_1191841" value="caivano"/></td>
<td><input type="text" id="cf_1191841" name="cf_1191841" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1191841,document.forms[0].nome_1191841,document.forms[0].cognome_1191841,document.forms[0].comune_1191841,document.forms[0].data_1191841,'cf_1191841')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182743" name="nome_1182743" value="giulia"/></td> 
<td><input type="text" id="cognome_1182743" name="cognome_1182743" value="aliberta"/></td> 
<td><input type="text" id="data_1182743" name="data_1182743" value="25/04/1954"/></td> 
<td><input type="radio" id="sessom_1182743" name="sesso_1182743" value="M"/> M
<input type="radio" id="sessof_1182743" name="sesso_1182743" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1182743" name="comune_1182743" value="s. sofia de piro"/></td>
<td><input type="text" id="cf_1182743" name="cf_1182743" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182743,document.forms[0].nome_1182743,document.forms[0].cognome_1182743,document.forms[0].comune_1182743,document.forms[0].data_1182743,'cf_1182743')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1011221" name="nome_1011221" value="maria luisa"/></td> 
<td><input type="text" id="cognome_1011221" name="cognome_1011221" value="annunziata"/></td> 
<td><input type="text" id="data_1011221" name="data_1011221" value="15/11/1951"/></td> 
<td><input type="radio" id="sessom_1011221" name="sesso_1011221" value="M"/> M
<input type="radio" id="sessof_1011221" name="sesso_1011221" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1011221" name="comune_1011221" value="castellammare di stabia"/></td>
<td><input type="text" id="cf_1011221" name="cf_1011221" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1011221,document.forms[0].nome_1011221,document.forms[0].cognome_1011221,document.forms[0].comune_1011221,document.forms[0].data_1011221,'cf_1011221')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181279" name="nome_1181279" value="achille"/></td> 
<td><input type="text" id="cognome_1181279" name="cognome_1181279" value="arciero"/></td> 
<td><input type="text" id="data_1181279" name="data_1181279" value="13/03/1984"/></td> 
<td><input type="radio" id="sessom_1181279" name="sesso_1181279" value="M" checked/> M
<input type="radio" id="sessof_1181279" name="sesso_1181279" value="F"/> F
</td>  
<td><input type="text" id="comune_1181279" name="comune_1181279" value="napoli"/></td>
<td><input type="text" id="cf_1181279" name="cf_1181279" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181279,document.forms[0].nome_1181279,document.forms[0].cognome_1181279,document.forms[0].comune_1181279,document.forms[0].data_1181279,'cf_1181279')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192104" name="nome_1192104" value="angela"/></td> 
<td><input type="text" id="cognome_1192104" name="cognome_1192104" value="fiore"/></td> 
<td><input type="text" id="data_1192104" name="data_1192104" value="22/08/1964"/></td> 
<td><input type="radio" id="sessom_1192104" name="sesso_1192104" value="M"/> M
<input type="radio" id="sessof_1192104" name="sesso_1192104" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1192104" name="comune_1192104" value="montella"/></td>
<td><input type="text" id="cf_1192104" name="cf_1192104" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192104,document.forms[0].nome_1192104,document.forms[0].cognome_1192104,document.forms[0].comune_1192104,document.forms[0].data_1192104,'cf_1192104')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1011223" name="nome_1011223" value="lucia "/></td> 
<td><input type="text" id="cognome_1011223" name="cognome_1011223" value="de simone"/></td> 
<td><input type="text" id="data_1011223" name="data_1011223" value="28/12/2012"/></td> 
<td><input type="radio" id="sessom_1011223" name="sesso_1011223" value="M"/> M
<input type="radio" id="sessof_1011223" name="sesso_1011223" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1011223" name="comune_1011223" value="nocera inferiore"/></td>
<td><input type="text" id="cf_1011223" name="cf_1011223" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1011223,document.forms[0].nome_1011223,document.forms[0].cognome_1011223,document.forms[0].comune_1011223,document.forms[0].data_1011223,'cf_1011223')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1029713" name="nome_1029713" value="rosa"/></td> 
<td><input type="text" id="cognome_1029713" name="cognome_1029713" value="villani"/></td> 
<td><input type="text" id="data_1029713" name="data_1029713" value="30/10/1968"/></td> 
<td><input type="radio" id="sessom_1029713" name="sesso_1029713" value="M"/> M
<input type="radio" id="sessof_1029713" name="sesso_1029713" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1029713" name="comune_1029713" value="salerno"/></td>
<td><input type="text" id="cf_1029713" name="cf_1029713" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1029713,document.forms[0].nome_1029713,document.forms[0].cognome_1029713,document.forms[0].comune_1029713,document.forms[0].data_1029713,'cf_1029713')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166098" name="nome_1166098" value="MARIA PAOLA"/></td> 
<td><input type="text" id="cognome_1166098" name="cognome_1166098" value="INTERNICOLA"/></td> 
<td><input type="text" id="data_1166098" name="data_1166098" value="20/08/1939"/></td> 
<td><input type="radio" id="sessom_1166098" name="sesso_1166098" value="M"/> M
<input type="radio" id="sessof_1166098" name="sesso_1166098" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166098" name="comune_1166098" value="castellamare del golfo"/></td>
<td><input type="text" id="cf_1166098" name="cf_1166098" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166098,document.forms[0].nome_1166098,document.forms[0].cognome_1166098,document.forms[0].comune_1166098,document.forms[0].data_1166098,'cf_1166098')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1174985" name="nome_1174985" value="MARIA"/></td> 
<td><input type="text" id="cognome_1174985" name="cognome_1174985" value="robertazzi"/></td> 
<td><input type="text" id="data_1174985" name="data_1174985" value="14/02/1967"/></td> 
<td><input type="radio" id="sessom_1174985" name="sesso_1174985" value="M"/> M
<input type="radio" id="sessof_1174985" name="sesso_1174985" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1174985" name="comune_1174985" value="salerno"/></td>
<td><input type="text" id="cf_1174985" name="cf_1174985" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1174985,document.forms[0].nome_1174985,document.forms[0].cognome_1174985,document.forms[0].comune_1174985,document.forms[0].data_1174985,'cf_1174985')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1164001" name="nome_1164001" value="antonio"/></td> 
<td><input type="text" id="cognome_1164001" name="cognome_1164001" value="giannattasio"/></td> 
<td><input type="text" id="data_1164001" name="data_1164001" value="21/08/1956"/></td> 
<td><input type="radio" id="sessom_1164001" name="sesso_1164001" value="M" checked/> M
<input type="radio" id="sessof_1164001" name="sesso_1164001" value="F"/> F
</td>  
<td><input type="text" id="comune_1164001" name="comune_1164001" value="salerno"/></td>
<td><input type="text" id="cf_1164001" name="cf_1164001" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164001,document.forms[0].nome_1164001,document.forms[0].cognome_1164001,document.forms[0].comune_1164001,document.forms[0].data_1164001,'cf_1164001')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1002855" name="nome_1002855" value="antonella"/></td> 
<td><input type="text" id="cognome_1002855" name="cognome_1002855" value="tucillo"/></td> 
<td><input type="text" id="data_1002855" name="data_1002855" value="03/09/1971"/></td> 
<td><input type="radio" id="sessom_1002855" name="sesso_1002855" value="M"/> M
<input type="radio" id="sessof_1002855" name="sesso_1002855" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1002855" name="comune_1002855" value="Napoli"/></td>
<td><input type="text" id="cf_1002855" name="cf_1002855" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1002855,document.forms[0].nome_1002855,document.forms[0].cognome_1002855,document.forms[0].comune_1002855,document.forms[0].data_1002855,'cf_1002855')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1176857" name="nome_1176857" value="gennaro"/></td> 
<td><input type="text" id="cognome_1176857" name="cognome_1176857" value="russo"/></td> 
<td><input type="text" id="data_1176857" name="data_1176857" value="13/06/1985"/></td> 
<td><input type="radio" id="sessom_1176857" name="sesso_1176857" value="M" checked/> M
<input type="radio" id="sessof_1176857" name="sesso_1176857" value="F"/> F
</td>  
<td><input type="text" id="comune_1176857" name="comune_1176857" value="napoli"/></td>
<td><input type="text" id="cf_1176857" name="cf_1176857" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1176857,document.forms[0].nome_1176857,document.forms[0].cognome_1176857,document.forms[0].comune_1176857,document.forms[0].data_1176857,'cf_1176857')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193363" name="nome_1193363" value="marco"/></td> 
<td><input type="text" id="cognome_1193363" name="cognome_1193363" value="maione"/></td> 
<td><input type="text" id="data_1193363" name="data_1193363" value="03/07/1984"/></td> 
<td><input type="radio" id="sessom_1193363" name="sesso_1193363" value="M" checked/> M
<input type="radio" id="sessof_1193363" name="sesso_1193363" value="F"/> F
</td>  
<td><input type="text" id="comune_1193363" name="comune_1193363" value="nola"/></td>
<td><input type="text" id="cf_1193363" name="cf_1193363" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193363,document.forms[0].nome_1193363,document.forms[0].cognome_1193363,document.forms[0].comune_1193363,document.forms[0].data_1193363,'cf_1193363')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189681" name="nome_1189681" value="marco"/></td> 
<td><input type="text" id="cognome_1189681" name="cognome_1189681" value="padovani"/></td> 
<td><input type="text" id="data_1189681" name="data_1189681" value="22/04/1974"/></td> 
<td><input type="radio" id="sessom_1189681" name="sesso_1189681" value="M" checked/> M
<input type="radio" id="sessof_1189681" name="sesso_1189681" value="F"/> F
</td>  
<td><input type="text" id="comune_1189681" name="comune_1189681" value="napoli"/></td>
<td><input type="text" id="cf_1189681" name="cf_1189681" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189681,document.forms[0].nome_1189681,document.forms[0].cognome_1189681,document.forms[0].comune_1189681,document.forms[0].data_1189681,'cf_1189681')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178734" name="nome_1178734" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1178734" name="cognome_1178734" value="iovino"/></td> 
<td><input type="text" id="data_1178734" name="data_1178734" value="12/08/1944"/></td> 
<td><input type="radio" id="sessom_1178734" name="sesso_1178734" value="M" checked/> M
<input type="radio" id="sessof_1178734" name="sesso_1178734" value="F"/> F
</td>  
<td><input type="text" id="comune_1178734" name="comune_1178734" value="torre annunziata"/></td>
<td><input type="text" id="cf_1178734" name="cf_1178734" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178734,document.forms[0].nome_1178734,document.forms[0].cognome_1178734,document.forms[0].comune_1178734,document.forms[0].data_1178734,'cf_1178734')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168356" name="nome_1168356" value="andrea"/></td> 
<td><input type="text" id="cognome_1168356" name="cognome_1168356" value="russo"/></td> 
<td><input type="text" id="data_1168356" name="data_1168356" value="13/10/1972"/></td> 
<td><input type="radio" id="sessom_1168356" name="sesso_1168356" value="M" checked/> M
<input type="radio" id="sessof_1168356" name="sesso_1168356" value="F"/> F
</td>  
<td><input type="text" id="comune_1168356" name="comune_1168356" value="napoli"/></td>
<td><input type="text" id="cf_1168356" name="cf_1168356" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168356,document.forms[0].nome_1168356,document.forms[0].cognome_1168356,document.forms[0].comune_1168356,document.forms[0].data_1168356,'cf_1168356')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1004850" name="nome_1004850" value="maria teresa patrizia"/></td> 
<td><input type="text" id="cognome_1004850" name="cognome_1004850" value="caiazza"/></td> 
<td><input type="text" id="data_1004850" name="data_1004850" value="30/11/1958"/></td> 
<td><input type="radio" id="sessom_1004850" name="sesso_1004850" value="M"/> M
<input type="radio" id="sessof_1004850" name="sesso_1004850" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1004850" name="comune_1004850" value="cava de tirreni (SA)"/></td>
<td><input type="text" id="cf_1004850" name="cf_1004850" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1004850,document.forms[0].nome_1004850,document.forms[0].cognome_1004850,document.forms[0].comune_1004850,document.forms[0].data_1004850,'cf_1004850')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192066" name="nome_1192066" value="bartolomeo"/></td> 
<td><input type="text" id="cognome_1192066" name="cognome_1192066" value="logarzo"/></td> 
<td><input type="text" id="data_1192066" name="data_1192066" value="29/03/1955"/></td> 
<td><input type="radio" id="sessom_1192066" name="sesso_1192066" value="M" checked/> M
<input type="radio" id="sessof_1192066" name="sesso_1192066" value="F"/> F
</td>  
<td><input type="text" id="comune_1192066" name="comune_1192066" value=""/></td>
<td><input type="text" id="cf_1192066" name="cf_1192066" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192066,document.forms[0].nome_1192066,document.forms[0].cognome_1192066,document.forms[0].comune_1192066,document.forms[0].data_1192066,'cf_1192066')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1012545" name="nome_1012545" value="maddalena "/></td> 
<td><input type="text" id="cognome_1012545" name="cognome_1012545" value="lauro"/></td> 
<td><input type="text" id="data_1012545" name="data_1012545" value="17/02/1955"/></td> 
<td><input type="radio" id="sessom_1012545" name="sesso_1012545" value="M"/> M
<input type="radio" id="sessof_1012545" name="sesso_1012545" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1012545" name="comune_1012545" value="castel s.giorgio"/></td>
<td><input type="text" id="cf_1012545" name="cf_1012545" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1012545,document.forms[0].nome_1012545,document.forms[0].cognome_1012545,document.forms[0].comune_1012545,document.forms[0].data_1012545,'cf_1012545')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1096237" name="nome_1096237" value="gaetano"/></td> 
<td><input type="text" id="cognome_1096237" name="cognome_1096237" value="manfredi"/></td> 
<td><input type="text" id="data_1096237" name="data_1096237" value="04/01/1964"/></td> 
<td><input type="radio" id="sessom_1096237" name="sesso_1096237" value="M" checked/> M
<input type="radio" id="sessof_1096237" name="sesso_1096237" value="F"/> F
</td>  
<td><input type="text" id="comune_1096237" name="comune_1096237" value="ottaviano"/></td>
<td><input type="text" id="cf_1096237" name="cf_1096237" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1096237,document.forms[0].nome_1096237,document.forms[0].cognome_1096237,document.forms[0].comune_1096237,document.forms[0].data_1096237,'cf_1096237')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1183566" name="nome_1183566" value="dario"/></td> 
<td><input type="text" id="cognome_1183566" name="cognome_1183566" value="garzia"/></td> 
<td><input type="text" id="data_1183566" name="data_1183566" value="05/10/1962"/></td> 
<td><input type="radio" id="sessom_1183566" name="sesso_1183566" value="M" checked/> M
<input type="radio" id="sessof_1183566" name="sesso_1183566" value="F"/> F
</td>  
<td><input type="text" id="comune_1183566" name="comune_1183566" value="napoli"/></td>
<td><input type="text" id="cf_1183566" name="cf_1183566" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1183566,document.forms[0].nome_1183566,document.forms[0].cognome_1183566,document.forms[0].comune_1183566,document.forms[0].data_1183566,'cf_1183566')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193074" name="nome_1193074" value="ROSANNA"/></td> 
<td><input type="text" id="cognome_1193074" name="cognome_1193074" value="IODICE"/></td> 
<td><input type="text" id="data_1193074" name="data_1193074" value="03/04/1962"/></td> 
<td><input type="radio" id="sessom_1193074" name="sesso_1193074" value="M"/> M
<input type="radio" id="sessof_1193074" name="sesso_1193074" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1193074" name="comune_1193074" value="MACERATA CAMPANIA"/></td>
<td><input type="text" id="cf_1193074" name="cf_1193074" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193074,document.forms[0].nome_1193074,document.forms[0].cognome_1193074,document.forms[0].comune_1193074,document.forms[0].data_1193074,'cf_1193074')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1050366" name="nome_1050366" value="maria teresa"/></td> 
<td><input type="text" id="cognome_1050366" name="cognome_1050366" value="telese"/></td> 
<td><input type="text" id="data_1050366" name="data_1050366" value="05/07/1961"/></td> 
<td><input type="radio" id="sessom_1050366" name="sesso_1050366" value="M"/> M
<input type="radio" id="sessof_1050366" name="sesso_1050366" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1050366" name="comune_1050366" value="santa maria a vico"/></td>
<td><input type="text" id="cf_1050366" name="cf_1050366" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1050366,document.forms[0].nome_1050366,document.forms[0].cognome_1050366,document.forms[0].comune_1050366,document.forms[0].data_1050366,'cf_1050366')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164413" name="nome_1164413" value="ROBERTO"/></td> 
<td><input type="text" id="cognome_1164413" name="cognome_1164413" value="MINUCCI"/></td> 
<td><input type="text" id="data_1164413" name="data_1164413" value="22/04/1958"/></td> 
<td><input type="radio" id="sessom_1164413" name="sesso_1164413" value="M" checked/> M
<input type="radio" id="sessof_1164413" name="sesso_1164413" value="F"/> F
</td>  
<td><input type="text" id="comune_1164413" name="comune_1164413" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164413" name="cf_1164413" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164413,document.forms[0].nome_1164413,document.forms[0].cognome_1164413,document.forms[0].comune_1164413,document.forms[0].data_1164413,'cf_1164413')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189145" name="nome_1189145" value="enrichetta"/></td> 
<td><input type="text" id="cognome_1189145" name="cognome_1189145" value="lombardi"/></td> 
<td><input type="text" id="data_1189145" name="data_1189145" value="23/12/1959"/></td> 
<td><input type="radio" id="sessom_1189145" name="sesso_1189145" value="M"/> M
<input type="radio" id="sessof_1189145" name="sesso_1189145" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1189145" name="comune_1189145" value="cerro al volturno"/></td>
<td><input type="text" id="cf_1189145" name="cf_1189145" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189145,document.forms[0].nome_1189145,document.forms[0].cognome_1189145,document.forms[0].comune_1189145,document.forms[0].data_1189145,'cf_1189145')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1185540" name="nome_1185540" value="antonella"/></td> 
<td><input type="text" id="cognome_1185540" name="cognome_1185540" value="guardascione"/></td> 
<td><input type="text" id="data_1185540" name="data_1185540" value="28/03/1987"/></td> 
<td><input type="radio" id="sessom_1185540" name="sesso_1185540" value="M"/> M
<input type="radio" id="sessof_1185540" name="sesso_1185540" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1185540" name="comune_1185540" value="napoli"/></td>
<td><input type="text" id="cf_1185540" name="cf_1185540" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1185540,document.forms[0].nome_1185540,document.forms[0].cognome_1185540,document.forms[0].comune_1185540,document.forms[0].data_1185540,'cf_1185540')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042890" name="nome_1042890" value="mario"/></td> 
<td><input type="text" id="cognome_1042890" name="cognome_1042890" value="grimaldi"/></td> 
<td><input type="text" id="data_1042890" name="data_1042890" value="17/10/1935"/></td> 
<td><input type="radio" id="sessom_1042890" name="sesso_1042890" value="M" checked/> M
<input type="radio" id="sessof_1042890" name="sesso_1042890" value="F"/> F
</td>  
<td><input type="text" id="comune_1042890" name="comune_1042890" value="salerno"/></td>
<td><input type="text" id="cf_1042890" name="cf_1042890" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042890,document.forms[0].nome_1042890,document.forms[0].cognome_1042890,document.forms[0].comune_1042890,document.forms[0].data_1042890,'cf_1042890')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1175920" name="nome_1175920" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1175920" name="cognome_1175920" value="toto"/></td> 
<td><input type="text" id="data_1175920" name="data_1175920" value="08/09/1955"/></td> 
<td><input type="radio" id="sessom_1175920" name="sesso_1175920" value="M" checked/> M
<input type="radio" id="sessof_1175920" name="sesso_1175920" value="F"/> F
</td>  
<td><input type="text" id="comune_1175920" name="comune_1175920" value="scampitella"/></td>
<td><input type="text" id="cf_1175920" name="cf_1175920" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1175920,document.forms[0].nome_1175920,document.forms[0].cognome_1175920,document.forms[0].comune_1175920,document.forms[0].data_1175920,'cf_1175920')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1170867" name="nome_1170867" value="roberta"/></td> 
<td><input type="text" id="cognome_1170867" name="cognome_1170867" value="carpinelli"/></td> 
<td><input type="text" id="data_1170867" name="data_1170867" value="22/11/1988"/></td> 
<td><input type="radio" id="sessom_1170867" name="sesso_1170867" value="M"/> M
<input type="radio" id="sessof_1170867" name="sesso_1170867" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1170867" name="comune_1170867" value="battipaglia"/></td>
<td><input type="text" id="cf_1170867" name="cf_1170867" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1170867,document.forms[0].nome_1170867,document.forms[0].cognome_1170867,document.forms[0].comune_1170867,document.forms[0].data_1170867,'cf_1170867')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181737" name="nome_1181737" value="vincenzo"/></td> 
<td><input type="text" id="cognome_1181737" name="cognome_1181737" value="rufolo"/></td> 
<td><input type="text" id="data_1181737" name="data_1181737" value="14/02/1956"/></td> 
<td><input type="radio" id="sessom_1181737" name="sesso_1181737" value="M" checked/> M
<input type="radio" id="sessof_1181737" name="sesso_1181737" value="F"/> F
</td>  
<td><input type="text" id="comune_1181737" name="comune_1181737" value="olivo citra"/></td>
<td><input type="text" id="cf_1181737" name="cf_1181737" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181737,document.forms[0].nome_1181737,document.forms[0].cognome_1181737,document.forms[0].comune_1181737,document.forms[0].data_1181737,'cf_1181737')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1170633" name="nome_1170633" value="GIOVANNA maria"/></td> 
<td><input type="text" id="cognome_1170633" name="cognome_1170633" value="sena"/></td> 
<td><input type="text" id="data_1170633" name="data_1170633" value="28/07/1962"/></td> 
<td><input type="radio" id="sessom_1170633" name="sesso_1170633" value="M"/> M
<input type="radio" id="sessof_1170633" name="sesso_1170633" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1170633" name="comune_1170633" value="castelnuovo di conza"/></td>
<td><input type="text" id="cf_1170633" name="cf_1170633" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1170633,document.forms[0].nome_1170633,document.forms[0].cognome_1170633,document.forms[0].comune_1170633,document.forms[0].data_1170633,'cf_1170633')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1193889" name="nome_1193889" value="angioletto "/></td> 
<td><input type="text" id="cognome_1193889" name="cognome_1193889" value="sparano"/></td> 
<td><input type="text" id="data_1193889" name="data_1193889" value="22/08/1952"/></td> 
<td><input type="radio" id="sessom_1193889" name="sesso_1193889" value="M" checked/> M
<input type="radio" id="sessof_1193889" name="sesso_1193889" value="F"/> F
</td>  
<td><input type="text" id="comune_1193889" name="comune_1193889" value="novi velia"/></td>
<td><input type="text" id="cf_1193889" name="cf_1193889" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193889,document.forms[0].nome_1193889,document.forms[0].cognome_1193889,document.forms[0].comune_1193889,document.forms[0].data_1193889,'cf_1193889')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1048357" name="nome_1048357" value="bruno "/></td> 
<td><input type="text" id="cognome_1048357" name="cognome_1048357" value="nastri "/></td> 
<td><input type="text" id="data_1048357" name="data_1048357" value="19/05/1943"/></td> 
<td><input type="radio" id="sessom_1048357" name="sesso_1048357" value="M" checked/> M
<input type="radio" id="sessof_1048357" name="sesso_1048357" value="F"/> F
</td>  
<td><input type="text" id="comune_1048357" name="comune_1048357" value="salerno"/></td>
<td><input type="text" id="cf_1048357" name="cf_1048357" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1048357,document.forms[0].nome_1048357,document.forms[0].cognome_1048357,document.forms[0].comune_1048357,document.forms[0].data_1048357,'cf_1048357')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181560" name="nome_1181560" value="barbara"/></td> 
<td><input type="text" id="cognome_1181560" name="cognome_1181560" value="ninni"/></td> 
<td><input type="text" id="data_1181560" name="data_1181560" value="02/07/1970"/></td> 
<td><input type="radio" id="sessom_1181560" name="sesso_1181560" value="M"/> M
<input type="radio" id="sessof_1181560" name="sesso_1181560" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181560" name="comune_1181560" value="Napoli"/></td>
<td><input type="text" id="cf_1181560" name="cf_1181560" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181560,document.forms[0].nome_1181560,document.forms[0].cognome_1181560,document.forms[0].comune_1181560,document.forms[0].data_1181560,'cf_1181560')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194817" name="nome_1194817" value="guido"/></td> 
<td><input type="text" id="cognome_1194817" name="cognome_1194817" value="natale"/></td> 
<td><input type="text" id="data_1194817" name="data_1194817" value="12/04/1985"/></td> 
<td><input type="radio" id="sessom_1194817" name="sesso_1194817" value="M" checked/> M
<input type="radio" id="sessof_1194817" name="sesso_1194817" value="F"/> F
</td>  
<td><input type="text" id="comune_1194817" name="comune_1194817" value="torino"/></td>
<td><input type="text" id="cf_1194817" name="cf_1194817" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194817,document.forms[0].nome_1194817,document.forms[0].cognome_1194817,document.forms[0].comune_1194817,document.forms[0].data_1194817,'cf_1194817')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173499" name="nome_1173499" value="raffaele"/></td> 
<td><input type="text" id="cognome_1173499" name="cognome_1173499" value="ciardiello"/></td> 
<td><input type="text" id="data_1173499" name="data_1173499" value="14/01/1987"/></td> 
<td><input type="radio" id="sessom_1173499" name="sesso_1173499" value="M" checked/> M
<input type="radio" id="sessof_1173499" name="sesso_1173499" value="F"/> F
</td>  
<td><input type="text" id="comune_1173499" name="comune_1173499" value="villaricca"/></td>
<td><input type="text" id="cf_1173499" name="cf_1173499" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173499,document.forms[0].nome_1173499,document.forms[0].cognome_1173499,document.forms[0].comune_1173499,document.forms[0].data_1173499,'cf_1173499')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194528" name="nome_1194528" value="attilia"/></td> 
<td><input type="text" id="cognome_1194528" name="cognome_1194528" value="bruno"/></td> 
<td><input type="text" id="data_1194528" name="data_1194528" value="05/04/1970"/></td> 
<td><input type="radio" id="sessom_1194528" name="sesso_1194528" value="M"/> M
<input type="radio" id="sessof_1194528" name="sesso_1194528" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1194528" name="comune_1194528" value="caserta"/></td>
<td><input type="text" id="cf_1194528" name="cf_1194528" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194528,document.forms[0].nome_1194528,document.forms[0].cognome_1194528,document.forms[0].comune_1194528,document.forms[0].data_1194528,'cf_1194528')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181292" name="nome_1181292" value="barbara"/></td> 
<td><input type="text" id="cognome_1181292" name="cognome_1181292" value="ninni"/></td> 
<td><input type="text" id="data_1181292" name="data_1181292" value="02/07/1970"/></td> 
<td><input type="radio" id="sessom_1181292" name="sesso_1181292" value="M"/> M
<input type="radio" id="sessof_1181292" name="sesso_1181292" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181292" name="comune_1181292" value="napoli"/></td>
<td><input type="text" id="cf_1181292" name="cf_1181292" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181292,document.forms[0].nome_1181292,document.forms[0].cognome_1181292,document.forms[0].comune_1181292,document.forms[0].data_1181292,'cf_1181292')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192099" name="nome_1192099" value="fortunato"/></td> 
<td><input type="text" id="cognome_1192099" name="cognome_1192099" value="alfonso"/></td> 
<td><input type="text" id="data_1192099" name="data_1192099" value="28/02/1987"/></td> 
<td><input type="radio" id="sessom_1192099" name="sesso_1192099" value="M" checked/> M
<input type="radio" id="sessof_1192099" name="sesso_1192099" value="F"/> F
</td>  
<td><input type="text" id="comune_1192099" name="comune_1192099" value="avellino"/></td>
<td><input type="text" id="cf_1192099" name="cf_1192099" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192099,document.forms[0].nome_1192099,document.forms[0].cognome_1192099,document.forms[0].comune_1192099,document.forms[0].data_1192099,'cf_1192099')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1164007" name="nome_1164007" value="luciano"/></td> 
<td><input type="text" id="cognome_1164007" name="cognome_1164007" value="attanasio"/></td> 
<td><input type="text" id="data_1164007" name="data_1164007" value="16/10/1959"/></td> 
<td><input type="radio" id="sessom_1164007" name="sesso_1164007" value="M" checked/> M
<input type="radio" id="sessof_1164007" name="sesso_1164007" value="F"/> F
</td>  
<td><input type="text" id="comune_1164007" name="comune_1164007" value="NAPOLI"/></td>
<td><input type="text" id="cf_1164007" name="cf_1164007" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1164007,document.forms[0].nome_1164007,document.forms[0].cognome_1164007,document.forms[0].comune_1164007,document.forms[0].data_1164007,'cf_1164007')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1043665" name="nome_1043665" value="alfonso"/></td> 
<td><input type="text" id="cognome_1043665" name="cognome_1043665" value="cubicciotto"/></td> 
<td><input type="text" id="data_1043665" name="data_1043665" value="27/02/1959"/></td> 
<td><input type="radio" id="sessom_1043665" name="sesso_1043665" value="M" checked/> M
<input type="radio" id="sessof_1043665" name="sesso_1043665" value="F"/> F
</td>  
<td><input type="text" id="comune_1043665" name="comune_1043665" value="campagna (SA)"/></td>
<td><input type="text" id="cf_1043665" name="cf_1043665" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1043665,document.forms[0].nome_1043665,document.forms[0].cognome_1043665,document.forms[0].comune_1043665,document.forms[0].data_1043665,'cf_1043665')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168418" name="nome_1168418" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1168418" name="cognome_1168418" value="maddaloni"/></td> 
<td><input type="text" id="data_1168418" name="data_1168418" value="06/04/1967"/></td> 
<td><input type="radio" id="sessom_1168418" name="sesso_1168418" value="M" checked/> M
<input type="radio" id="sessof_1168418" name="sesso_1168418" value="F"/> F
</td>  
<td><input type="text" id="comune_1168418" name="comune_1168418" value="napoli"/></td>
<td><input type="text" id="cf_1168418" name="cf_1168418" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168418,document.forms[0].nome_1168418,document.forms[0].cognome_1168418,document.forms[0].comune_1168418,document.forms[0].data_1168418,'cf_1168418')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1001425" name="nome_1001425" value="francesco rosario"/></td> 
<td><input type="text" id="cognome_1001425" name="cognome_1001425" value="pavese"/></td> 
<td><input type="text" id="data_1001425" name="data_1001425" value="01/01/1959"/></td> 
<td><input type="radio" id="sessom_1001425" name="sesso_1001425" value="M" checked/> M
<input type="radio" id="sessof_1001425" name="sesso_1001425" value="F"/> F
</td>  
<td><input type="text" id="comune_1001425" name="comune_1001425" value="laurenzana (PZ)"/></td>
<td><input type="text" id="cf_1001425" name="cf_1001425" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1001425,document.forms[0].nome_1001425,document.forms[0].cognome_1001425,document.forms[0].comune_1001425,document.forms[0].data_1001425,'cf_1001425')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194527" name="nome_1194527" value="luigi"/></td> 
<td><input type="text" id="cognome_1194527" name="cognome_1194527" value="imparato"/></td> 
<td><input type="text" id="data_1194527" name="data_1194527" value="02/09/1961"/></td> 
<td><input type="radio" id="sessom_1194527" name="sesso_1194527" value="M" checked/> M
<input type="radio" id="sessof_1194527" name="sesso_1194527" value="F"/> F
</td>  
<td><input type="text" id="comune_1194527" name="comune_1194527" value="caserta"/></td>
<td><input type="text" id="cf_1194527" name="cf_1194527" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194527,document.forms[0].nome_1194527,document.forms[0].cognome_1194527,document.forms[0].comune_1194527,document.forms[0].data_1194527,'cf_1194527')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1043666" name="nome_1043666" value="miriam"/></td> 
<td><input type="text" id="cognome_1043666" name="cognome_1043666" value="carbone "/></td> 
<td><input type="text" id="data_1043666" name="data_1043666" value="13/10/1962"/></td> 
<td><input type="radio" id="sessom_1043666" name="sesso_1043666" value="M"/> M
<input type="radio" id="sessof_1043666" name="sesso_1043666" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1043666" name="comune_1043666" value="napoli"/></td>
<td><input type="text" id="cf_1043666" name="cf_1043666" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1043666,document.forms[0].nome_1043666,document.forms[0].cognome_1043666,document.forms[0].comune_1043666,document.forms[0].data_1043666,'cf_1043666')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1183052" name="nome_1183052" value="amato"/></td> 
<td><input type="text" id="cognome_1183052" name="cognome_1183052" value="grisi"/></td> 
<td><input type="text" id="data_1183052" name="data_1183052" value="20/01/1934"/></td> 
<td><input type="radio" id="sessom_1183052" name="sesso_1183052" value="M" checked/> M
<input type="radio" id="sessof_1183052" name="sesso_1183052" value="F"/> F
</td>  
<td><input type="text" id="comune_1183052" name="comune_1183052" value="palomonte"/></td>
<td><input type="text" id="cf_1183052" name="cf_1183052" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1183052,document.forms[0].nome_1183052,document.forms[0].cognome_1183052,document.forms[0].comune_1183052,document.forms[0].data_1183052,'cf_1183052')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1171461" name="nome_1171461" value="antonio"/></td> 
<td><input type="text" id="cognome_1171461" name="cognome_1171461" value="gammaldi"/></td> 
<td><input type="text" id="data_1171461" name="data_1171461" value="03/02/1959"/></td> 
<td><input type="radio" id="sessom_1171461" name="sesso_1171461" value="M" checked/> M
<input type="radio" id="sessof_1171461" name="sesso_1171461" value="F"/> F
</td>  
<td><input type="text" id="comune_1171461" name="comune_1171461" value="salerno"/></td>
<td><input type="text" id="cf_1171461" name="cf_1171461" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1171461,document.forms[0].nome_1171461,document.forms[0].cognome_1171461,document.forms[0].comune_1171461,document.forms[0].data_1171461,'cf_1171461')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1065942" name="nome_1065942" value="mario"/></td> 
<td><input type="text" id="cognome_1065942" name="cognome_1065942" value="costantino"/></td> 
<td><input type="text" id="data_1065942" name="data_1065942" value="23/03/1956"/></td> 
<td><input type="radio" id="sessom_1065942" name="sesso_1065942" value="M" checked/> M
<input type="radio" id="sessof_1065942" name="sesso_1065942" value="F"/> F
</td>  
<td><input type="text" id="comune_1065942" name="comune_1065942" value="castelcivita"/></td>
<td><input type="text" id="cf_1065942" name="cf_1065942" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1065942,document.forms[0].nome_1065942,document.forms[0].cognome_1065942,document.forms[0].comune_1065942,document.forms[0].data_1065942,'cf_1065942')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166339" name="nome_1166339" value="virginia"/></td> 
<td><input type="text" id="cognome_1166339" name="cognome_1166339" value="parisi"/></td> 
<td><input type="text" id="data_1166339" name="data_1166339" value="06/05/1956"/></td> 
<td><input type="radio" id="sessom_1166339" name="sesso_1166339" value="M"/> M
<input type="radio" id="sessof_1166339" name="sesso_1166339" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166339" name="comune_1166339" value="pontecagnano faiano"/></td>
<td><input type="text" id="cf_1166339" name="cf_1166339" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166339,document.forms[0].nome_1166339,document.forms[0].cognome_1166339,document.forms[0].comune_1166339,document.forms[0].data_1166339,'cf_1166339')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167467" name="nome_1167467" value="natalia"/></td> 
<td><input type="text" id="cognome_1167467" name="cognome_1167467" value="tranfaglia"/></td> 
<td><input type="text" id="data_1167467" name="data_1167467" value="25/01/1963"/></td> 
<td><input type="radio" id="sessom_1167467" name="sesso_1167467" value="M"/> M
<input type="radio" id="sessof_1167467" name="sesso_1167467" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1167467" name="comune_1167467" value="MERCATO SAN SEVERINo"/></td>
<td><input type="text" id="cf_1167467" name="cf_1167467" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167467,document.forms[0].nome_1167467,document.forms[0].cognome_1167467,document.forms[0].comune_1167467,document.forms[0].data_1167467,'cf_1167467')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168115" name="nome_1168115" value="ENRICHETTA"/></td> 
<td><input type="text" id="cognome_1168115" name="cognome_1168115" value="LOMBARDI"/></td> 
<td><input type="text" id="data_1168115" name="data_1168115" value="23/12/1959"/></td> 
<td><input type="radio" id="sessom_1168115" name="sesso_1168115" value="M"/> M
<input type="radio" id="sessof_1168115" name="sesso_1168115" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1168115" name="comune_1168115" value="cerro al volturno"/></td>
<td><input type="text" id="cf_1168115" name="cf_1168115" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168115,document.forms[0].nome_1168115,document.forms[0].cognome_1168115,document.forms[0].comune_1168115,document.forms[0].data_1168115,'cf_1168115')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1045714" name="nome_1045714" value="SALVATORE"/></td> 
<td><input type="text" id="cognome_1045714" name="cognome_1045714" value="D'ANTUONO"/></td> 
<td><input type="text" id="data_1045714" name="data_1045714" value="14/09/1959"/></td> 
<td><input type="radio" id="sessom_1045714" name="sesso_1045714" value="M" checked/> M
<input type="radio" id="sessof_1045714" name="sesso_1045714" value="F"/> F
</td>  
<td><input type="text" id="comune_1045714" name="comune_1045714" value="ANGRI (SA)"/></td>
<td><input type="text" id="cf_1045714" name="cf_1045714" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1045714,document.forms[0].nome_1045714,document.forms[0].cognome_1045714,document.forms[0].comune_1045714,document.forms[0].data_1045714,'cf_1045714')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181735" name="nome_1181735" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1181735" name="cognome_1181735" value="picciuoli"/></td> 
<td><input type="text" id="data_1181735" name="data_1181735" value="03/07/1966"/></td> 
<td><input type="radio" id="sessom_1181735" name="sesso_1181735" value="M" checked/> M
<input type="radio" id="sessof_1181735" name="sesso_1181735" value="F"/> F
</td>  
<td><input type="text" id="comune_1181735" name="comune_1181735" value="ricigliano"/></td>
<td><input type="text" id="cf_1181735" name="cf_1181735" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181735,document.forms[0].nome_1181735,document.forms[0].cognome_1181735,document.forms[0].comune_1181735,document.forms[0].data_1181735,'cf_1181735')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186361" name="nome_1186361" value="clara"/></td> 
<td><input type="text" id="cognome_1186361" name="cognome_1186361" value="calicchio"/></td> 
<td><input type="text" id="data_1186361" name="data_1186361" value="18/04/1980"/></td> 
<td><input type="radio" id="sessom_1186361" name="sesso_1186361" value="M"/> M
<input type="radio" id="sessof_1186361" name="sesso_1186361" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1186361" name="comune_1186361" value="napoli"/></td>
<td><input type="text" id="cf_1186361" name="cf_1186361" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186361,document.forms[0].nome_1186361,document.forms[0].cognome_1186361,document.forms[0].comune_1186361,document.forms[0].data_1186361,'cf_1186361')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1188994" name="nome_1188994" value="ILARIA"/></td> 
<td><input type="text" id="cognome_1188994" name="cognome_1188994" value="RUGGIERO"/></td> 
<td><input type="text" id="data_1188994" name="data_1188994" value="28/02/1983"/></td> 
<td><input type="radio" id="sessom_1188994" name="sesso_1188994" value="M"/> M
<input type="radio" id="sessof_1188994" name="sesso_1188994" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1188994" name="comune_1188994" value="SORRENTO"/></td>
<td><input type="text" id="cf_1188994" name="cf_1188994" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1188994,document.forms[0].nome_1188994,document.forms[0].cognome_1188994,document.forms[0].comune_1188994,document.forms[0].data_1188994,'cf_1188994')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167726" name="nome_1167726" value="roberto"/></td> 
<td><input type="text" id="cognome_1167726" name="cognome_1167726" value="vecchioni"/></td> 
<td><input type="text" id="data_1167726" name="data_1167726" value="15/11/1949"/></td> 
<td><input type="radio" id="sessom_1167726" name="sesso_1167726" value="M" checked/> M
<input type="radio" id="sessof_1167726" name="sesso_1167726" value="F"/> F
</td>  
<td><input type="text" id="comune_1167726" name="comune_1167726" value="napoli"/></td>
<td><input type="text" id="cf_1167726" name="cf_1167726" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167726,document.forms[0].nome_1167726,document.forms[0].cognome_1167726,document.forms[0].comune_1167726,document.forms[0].data_1167726,'cf_1167726')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1006636" name="nome_1006636" value="antonio"/></td> 
<td><input type="text" id="cognome_1006636" name="cognome_1006636" value="d'urso"/></td> 
<td><input type="text" id="data_1006636" name="data_1006636" value="01/01/1932"/></td> 
<td><input type="radio" id="sessom_1006636" name="sesso_1006636" value="M" checked/> M
<input type="radio" id="sessof_1006636" name="sesso_1006636" value="F"/> F
</td>  
<td><input type="text" id="comune_1006636" name="comune_1006636" value="solofra (AV)"/></td>
<td><input type="text" id="cf_1006636" name="cf_1006636" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1006636,document.forms[0].nome_1006636,document.forms[0].cognome_1006636,document.forms[0].comune_1006636,document.forms[0].data_1006636,'cf_1006636')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1175612" name="nome_1175612" value="roberto"/></td> 
<td><input type="text" id="cognome_1175612" name="cognome_1175612" value="quitadamo"/></td> 
<td><input type="text" id="data_1175612" name="data_1175612" value="01/08/1971"/></td> 
<td><input type="radio" id="sessom_1175612" name="sesso_1175612" value="M" checked/> M
<input type="radio" id="sessof_1175612" name="sesso_1175612" value="F"/> F
</td>  
<td><input type="text" id="comune_1175612" name="comune_1175612" value="SALERNO"/></td>
<td><input type="text" id="cf_1175612" name="cf_1175612" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1175612,document.forms[0].nome_1175612,document.forms[0].cognome_1175612,document.forms[0].comune_1175612,document.forms[0].data_1175612,'cf_1175612')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1048403" name="nome_1048403" value="raffaele "/></td> 
<td><input type="text" id="cognome_1048403" name="cognome_1048403" value="schiavo"/></td> 
<td><input type="text" id="data_1048403" name="data_1048403" value="18/11/1959"/></td> 
<td><input type="radio" id="sessom_1048403" name="sesso_1048403" value="M" checked/> M
<input type="radio" id="sessof_1048403" name="sesso_1048403" value="F"/> F
</td>  
<td><input type="text" id="comune_1048403" name="comune_1048403" value="ariano irpino"/></td>
<td><input type="text" id="cf_1048403" name="cf_1048403" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1048403,document.forms[0].nome_1048403,document.forms[0].cognome_1048403,document.forms[0].comune_1048403,document.forms[0].data_1048403,'cf_1048403')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181278" name="nome_1181278" value="silvia "/></td> 
<td><input type="text" id="cognome_1181278" name="cognome_1181278" value="d'angelo"/></td> 
<td><input type="text" id="data_1181278" name="data_1181278" value="30/08/1984"/></td> 
<td><input type="radio" id="sessom_1181278" name="sesso_1181278" value="M"/> M
<input type="radio" id="sessof_1181278" name="sesso_1181278" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1181278" name="comune_1181278" value="napoli"/></td>
<td><input type="text" id="cf_1181278" name="cf_1181278" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181278,document.forms[0].nome_1181278,document.forms[0].cognome_1181278,document.forms[0].comune_1181278,document.forms[0].data_1181278,'cf_1181278')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1178048" name="nome_1178048" value="lucia"/></td> 
<td><input type="text" id="cognome_1178048" name="cognome_1178048" value="garzia"/></td> 
<td><input type="text" id="data_1178048" name="data_1178048" value="11/03/1972"/></td> 
<td><input type="radio" id="sessom_1178048" name="sesso_1178048" value="M"/> M
<input type="radio" id="sessof_1178048" name="sesso_1178048" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1178048" name="comune_1178048" value="napoli"/></td>
<td><input type="text" id="cf_1178048" name="cf_1178048" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1178048,document.forms[0].nome_1178048,document.forms[0].cognome_1178048,document.forms[0].comune_1178048,document.forms[0].data_1178048,'cf_1178048')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167604" name="nome_1167604" value="valeria"/></td> 
<td><input type="text" id="cognome_1167604" name="cognome_1167604" value="de angelis"/></td> 
<td><input type="text" id="data_1167604" name="data_1167604" value="22/11/1980"/></td> 
<td><input type="radio" id="sessom_1167604" name="sesso_1167604" value="M"/> M
<input type="radio" id="sessof_1167604" name="sesso_1167604" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1167604" name="comune_1167604" value="napoli"/></td>
<td><input type="text" id="cf_1167604" name="cf_1167604" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167604,document.forms[0].nome_1167604,document.forms[0].cognome_1167604,document.forms[0].comune_1167604,document.forms[0].data_1167604,'cf_1167604')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1181285" name="nome_1181285" value="NICOLA MARIA"/></td> 
<td><input type="text" id="cognome_1181285" name="cognome_1181285" value="DE LELLIS"/></td> 
<td><input type="text" id="data_1181285" name="data_1181285" value="20/06/1964"/></td> 
<td><input type="radio" id="sessom_1181285" name="sesso_1181285" value="M" checked/> M
<input type="radio" id="sessof_1181285" name="sesso_1181285" value="F"/> F
</td>  
<td><input type="text" id="comune_1181285" name="comune_1181285" value="PIACENZA"/></td>
<td><input type="text" id="cf_1181285" name="cf_1181285" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1181285,document.forms[0].nome_1181285,document.forms[0].cognome_1181285,document.forms[0].comune_1181285,document.forms[0].data_1181285,'cf_1181285')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1019612" name="nome_1019612" value="PASQUALE"/></td> 
<td><input type="text" id="cognome_1019612" name="cognome_1019612" value="BRUNUINI"/></td> 
<td><input type="text" id="data_1019612" name="data_1019612" value="29/01/1956"/></td> 
<td><input type="radio" id="sessom_1019612" name="sesso_1019612" value="M" checked/> M
<input type="radio" id="sessof_1019612" name="sesso_1019612" value="F"/> F
</td>  
<td><input type="text" id="comune_1019612" name="comune_1019612" value="MILAZZO (ME)"/></td>
<td><input type="text" id="cf_1019612" name="cf_1019612" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1019612,document.forms[0].nome_1019612,document.forms[0].cognome_1019612,document.forms[0].comune_1019612,document.forms[0].data_1019612,'cf_1019612')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186127" name="nome_1186127" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1186127" name="cognome_1186127" value="picciuoli"/></td> 
<td><input type="text" id="data_1186127" name="data_1186127" value="03/06/1966"/></td> 
<td><input type="radio" id="sessom_1186127" name="sesso_1186127" value="M" checked/> M
<input type="radio" id="sessof_1186127" name="sesso_1186127" value="F"/> F
</td>  
<td><input type="text" id="comune_1186127" name="comune_1186127" value="ricigliano"/></td>
<td><input type="text" id="cf_1186127" name="cf_1186127" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186127,document.forms[0].nome_1186127,document.forms[0].cognome_1186127,document.forms[0].comune_1186127,document.forms[0].data_1186127,'cf_1186127')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1044781" name="nome_1044781" value="cristina"/></td> 
<td><input type="text" id="cognome_1044781" name="cognome_1044781" value="viscosi"/></td> 
<td><input type="text" id="data_1044781" name="data_1044781" value="21/01/1960"/></td> 
<td><input type="radio" id="sessom_1044781" name="sesso_1044781" value="M"/> M
<input type="radio" id="sessof_1044781" name="sesso_1044781" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1044781" name="comune_1044781" value="melizzano(BN)"/></td>
<td><input type="text" id="cf_1044781" name="cf_1044781" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1044781,document.forms[0].nome_1044781,document.forms[0].cognome_1044781,document.forms[0].comune_1044781,document.forms[0].data_1044781,'cf_1044781')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189144" name="nome_1189144" value="giovanni"/></td> 
<td><input type="text" id="cognome_1189144" name="cognome_1189144" value="ferlaino"/></td> 
<td><input type="text" id="data_1189144" name="data_1189144" value="30/11/1976"/></td> 
<td><input type="radio" id="sessom_1189144" name="sesso_1189144" value="M"/> M
<input type="radio" id="sessof_1189144" name="sesso_1189144" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1189144" name="comune_1189144" value="napoli"/></td>
<td><input type="text" id="cf_1189144" name="cf_1189144" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189144,document.forms[0].nome_1189144,document.forms[0].cognome_1189144,document.forms[0].comune_1189144,document.forms[0].data_1189144,'cf_1189144')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166701" name="nome_1166701" value="mario"/></td> 
<td><input type="text" id="cognome_1166701" name="cognome_1166701" value="chiacchio"/></td> 
<td><input type="text" id="data_1166701" name="data_1166701" value="08/06/1985"/></td> 
<td><input type="radio" id="sessom_1166701" name="sesso_1166701" value="M" checked/> M
<input type="radio" id="sessof_1166701" name="sesso_1166701" value="F"/> F
</td>  
<td><input type="text" id="comune_1166701" name="comune_1166701" value="napoli"/></td>
<td><input type="text" id="cf_1166701" name="cf_1166701" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166701,document.forms[0].nome_1166701,document.forms[0].cognome_1166701,document.forms[0].comune_1166701,document.forms[0].data_1166701,'cf_1166701')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192138" name="nome_1192138" value="rosa elisa"/></td> 
<td><input type="text" id="cognome_1192138" name="cognome_1192138" value="pugliese"/></td> 
<td><input type="text" id="data_1192138" name="data_1192138" value="26/09/1953"/></td> 
<td><input type="radio" id="sessom_1192138" name="sesso_1192138" value="M"/> M
<input type="radio" id="sessof_1192138" name="sesso_1192138" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1192138" name="comune_1192138" value="torre orsaia"/></td>
<td><input type="text" id="cf_1192138" name="cf_1192138" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192138,document.forms[0].nome_1192138,document.forms[0].cognome_1192138,document.forms[0].comune_1192138,document.forms[0].data_1192138,'cf_1192138')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1186897" name="nome_1186897" value="giovanni battista"/></td> 
<td><input type="text" id="cognome_1186897" name="cognome_1186897" value="itri"/></td> 
<td><input type="text" id="data_1186897" name="data_1186897" value="03/08/1970"/></td> 
<td><input type="radio" id="sessom_1186897" name="sesso_1186897" value="M"/> M
<input type="radio" id="sessof_1186897" name="sesso_1186897" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1186897" name="comune_1186897" value="SALERNO"/></td>
<td><input type="text" id="cf_1186897" name="cf_1186897" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1186897,document.forms[0].nome_1186897,document.forms[0].cognome_1186897,document.forms[0].comune_1186897,document.forms[0].data_1186897,'cf_1186897')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189708" name="nome_1189708" value="claudia"/></td> 
<td><input type="text" id="cognome_1189708" name="cognome_1189708" value="della valle"/></td> 
<td><input type="text" id="data_1189708" name="data_1189708" value="23/01/1960"/></td> 
<td><input type="radio" id="sessom_1189708" name="sesso_1189708" value="M"/> M
<input type="radio" id="sessof_1189708" name="sesso_1189708" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1189708" name="comune_1189708" value="roma"/></td>
<td><input type="text" id="cf_1189708" name="cf_1189708" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189708,document.forms[0].nome_1189708,document.forms[0].cognome_1189708,document.forms[0].comune_1189708,document.forms[0].data_1189708,'cf_1189708')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166104" name="nome_1166104" value="FRANCESCA"/></td> 
<td><input type="text" id="cognome_1166104" name="cognome_1166104" value="FUSARO"/></td> 
<td><input type="text" id="data_1166104" name="data_1166104" value="26/06/1957"/></td> 
<td><input type="radio" id="sessom_1166104" name="sesso_1166104" value="M"/> M
<input type="radio" id="sessof_1166104" name="sesso_1166104" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1166104" name="comune_1166104" value="napoli"/></td>
<td><input type="text" id="cf_1166104" name="cf_1166104" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166104,document.forms[0].nome_1166104,document.forms[0].cognome_1166104,document.forms[0].comune_1166104,document.forms[0].data_1166104,'cf_1166104')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192050" name="nome_1192050" value="pasquale"/></td> 
<td><input type="text" id="cognome_1192050" name="cognome_1192050" value="apostolico"/></td> 
<td><input type="text" id="data_1192050" name="data_1192050" value="16/06/1983"/></td> 
<td><input type="radio" id="sessom_1192050" name="sesso_1192050" value="M" checked/> M
<input type="radio" id="sessof_1192050" name="sesso_1192050" value="F"/> F
</td>  
<td><input type="text" id="comune_1192050" name="comune_1192050" value="battipaglia"/></td>
<td><input type="text" id="cf_1192050" name="cf_1192050" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192050,document.forms[0].nome_1192050,document.forms[0].cognome_1192050,document.forms[0].comune_1192050,document.forms[0].data_1192050,'cf_1192050')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194161" name="nome_1194161" value="gorea"/></td> 
<td><input type="text" id="cognome_1194161" name="cognome_1194161" value="tommaso"/></td> 
<td><input type="text" id="data_1194161" name="data_1194161" value="30/01/1952"/></td> 
<td><input type="radio" id="sessom_1194161" name="sesso_1194161" value="M" checked/> M
<input type="radio" id="sessof_1194161" name="sesso_1194161" value="F"/> F
</td>  
<td><input type="text" id="comune_1194161" name="comune_1194161" value="salerno"/></td>
<td><input type="text" id="cf_1194161" name="cf_1194161" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194161,document.forms[0].nome_1194161,document.forms[0].cognome_1194161,document.forms[0].comune_1194161,document.forms[0].data_1194161,'cf_1194161')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1193871" name="nome_1193871" value="alessandro"/></td> 
<td><input type="text" id="cognome_1193871" name="cognome_1193871" value="grilli"/></td> 
<td><input type="text" id="data_1193871" name="data_1193871" value="10/09/1968"/></td> 
<td><input type="radio" id="sessom_1193871" name="sesso_1193871" value="M" checked/> M
<input type="radio" id="sessof_1193871" name="sesso_1193871" value="F"/> F
</td>  
<td><input type="text" id="comune_1193871" name="comune_1193871" value="napoli"/></td>
<td><input type="text" id="cf_1193871" name="cf_1193871" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1193871,document.forms[0].nome_1193871,document.forms[0].cognome_1193871,document.forms[0].comune_1193871,document.forms[0].data_1193871,'cf_1193871')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1190047" name="nome_1190047" value="francesca"/></td> 
<td><input type="text" id="cognome_1190047" name="cognome_1190047" value="apostolico"/></td> 
<td><input type="text" id="data_1190047" name="data_1190047" value="06/12/1941"/></td> 
<td><input type="radio" id="sessom_1190047" name="sesso_1190047" value="M"/> M
<input type="radio" id="sessof_1190047" name="sesso_1190047" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1190047" name="comune_1190047" value="conegliano"/></td>
<td><input type="text" id="cf_1190047" name="cf_1190047" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1190047,document.forms[0].nome_1190047,document.forms[0].cognome_1190047,document.forms[0].comune_1190047,document.forms[0].data_1190047,'cf_1190047')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1189142" name="nome_1189142" value="achille"/></td> 
<td><input type="text" id="cognome_1189142" name="cognome_1189142" value="arciero"/></td> 
<td><input type="text" id="data_1189142" name="data_1189142" value="13/08/1984"/></td> 
<td><input type="radio" id="sessom_1189142" name="sesso_1189142" value="M" checked/> M
<input type="radio" id="sessof_1189142" name="sesso_1189142" value="F"/> F
</td>  
<td><input type="text" id="comune_1189142" name="comune_1189142" value="Napoli"/></td>
<td><input type="text" id="cf_1189142" name="cf_1189142" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1189142,document.forms[0].nome_1189142,document.forms[0].cognome_1189142,document.forms[0].comune_1189142,document.forms[0].data_1189142,'cf_1189142')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173708" name="nome_1173708" value="MARIA CRISTINA"/></td> 
<td><input type="text" id="cognome_1173708" name="cognome_1173708" value="CARELLA"/></td> 
<td><input type="text" id="data_1173708" name="data_1173708" value="14/06/1957"/></td> 
<td><input type="radio" id="sessom_1173708" name="sesso_1173708" value="M"/> M
<input type="radio" id="sessof_1173708" name="sesso_1173708" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1173708" name="comune_1173708" value="marzano appio"/></td>
<td><input type="text" id="cf_1173708" name="cf_1173708" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173708,document.forms[0].nome_1173708,document.forms[0].cognome_1173708,document.forms[0].comune_1173708,document.forms[0].data_1173708,'cf_1173708')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1013820" name="nome_1013820" value="sergio"/></td> 
<td><input type="text" id="cognome_1013820" name="cognome_1013820" value="schettini"/></td> 
<td><input type="text" id="data_1013820" name="data_1013820" value="06/09/1960"/></td> 
<td><input type="radio" id="sessom_1013820" name="sesso_1013820" value="M" checked/> M
<input type="radio" id="sessof_1013820" name="sesso_1013820" value="F"/> F
</td>  
<td><input type="text" id="comune_1013820" name="comune_1013820" value="giffoni dei casali"/></td>
<td><input type="text" id="cf_1013820" name="cf_1013820" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1013820,document.forms[0].nome_1013820,document.forms[0].cognome_1013820,document.forms[0].comune_1013820,document.forms[0].data_1013820,'cf_1013820')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1182786" name="nome_1182786" value="flora"/></td> 
<td><input type="text" id="cognome_1182786" name="cognome_1182786" value="orlandi"/></td> 
<td><input type="text" id="data_1182786" name="data_1182786" value="27/12/1980"/></td> 
<td><input type="radio" id="sessom_1182786" name="sesso_1182786" value="M"/> M
<input type="radio" id="sessof_1182786" name="sesso_1182786" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1182786" name="comune_1182786" value="napoli"/></td>
<td><input type="text" id="cf_1182786" name="cf_1182786" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1182786,document.forms[0].nome_1182786,document.forms[0].cognome_1182786,document.forms[0].comune_1182786,document.forms[0].data_1182786,'cf_1182786')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1012849" name="nome_1012849" value="carlo"/></td> 
<td><input type="text" id="cognome_1012849" name="cognome_1012849" value="de martino"/></td> 
<td><input type="text" id="data_1012849" name="data_1012849" value="11/09/1929"/></td> 
<td><input type="radio" id="sessom_1012849" name="sesso_1012849" value="M" checked/> M
<input type="radio" id="sessof_1012849" name="sesso_1012849" value="F"/> F
</td>  
<td><input type="text" id="comune_1012849" name="comune_1012849" value="salerno"/></td>
<td><input type="text" id="cf_1012849" name="cf_1012849" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1012849,document.forms[0].nome_1012849,document.forms[0].cognome_1012849,document.forms[0].comune_1012849,document.forms[0].data_1012849,'cf_1012849')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1176966" name="nome_1176966" value="carlo maria"/></td> 
<td><input type="text" id="cognome_1176966" name="cognome_1176966" value="iovino"/></td> 
<td><input type="text" id="data_1176966" name="data_1176966" value="07/02/1968"/></td> 
<td><input type="radio" id="sessom_1176966" name="sesso_1176966" value="M" checked/> M
<input type="radio" id="sessof_1176966" name="sesso_1176966" value="F"/> F
</td>  
<td><input type="text" id="comune_1176966" name="comune_1176966" value="napoli"/></td>
<td><input type="text" id="cf_1176966" name="cf_1176966" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1176966,document.forms[0].nome_1176966,document.forms[0].cognome_1176966,document.forms[0].comune_1176966,document.forms[0].data_1176966,'cf_1176966')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1167605" name="nome_1167605" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1167605" name="cognome_1167605" value="la rocca"/></td> 
<td><input type="text" id="data_1167605" name="data_1167605" value="07/01/1949"/></td> 
<td><input type="radio" id="sessom_1167605" name="sesso_1167605" value="M" checked/> M
<input type="radio" id="sessof_1167605" name="sesso_1167605" value="F"/> F
</td>  
<td><input type="text" id="comune_1167605" name="comune_1167605" value="marigliano"/></td>
<td><input type="text" id="cf_1167605" name="cf_1167605" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1167605,document.forms[0].nome_1167605,document.forms[0].cognome_1167605,document.forms[0].comune_1167605,document.forms[0].data_1167605,'cf_1167605')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1183666" name="nome_1183666" value="francesco"/></td> 
<td><input type="text" id="cognome_1183666" name="cognome_1183666" value="d'ambrosio"/></td> 
<td><input type="text" id="data_1183666" name="data_1183666" value="30/01/1976"/></td> 
<td><input type="radio" id="sessom_1183666" name="sesso_1183666" value="M" checked/> M
<input type="radio" id="sessof_1183666" name="sesso_1183666" value="F"/> F
</td>  
<td><input type="text" id="comune_1183666" name="comune_1183666" value="caserta"/></td>
<td><input type="text" id="cf_1183666" name="cf_1183666" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1183666,document.forms[0].nome_1183666,document.forms[0].cognome_1183666,document.forms[0].comune_1183666,document.forms[0].data_1183666,'cf_1183666')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1173497" name="nome_1173497" value="stefania"/></td> 
<td><input type="text" id="cognome_1173497" name="cognome_1173497" value="stabile"/></td> 
<td><input type="text" id="data_1173497" name="data_1173497" value="24/01/1972"/></td> 
<td><input type="radio" id="sessom_1173497" name="sesso_1173497" value="M"/> M
<input type="radio" id="sessof_1173497" name="sesso_1173497" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1173497" name="comune_1173497" value="napoli"/></td>
<td><input type="text" id="cf_1173497" name="cf_1173497" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1173497,document.forms[0].nome_1173497,document.forms[0].cognome_1173497,document.forms[0].comune_1173497,document.forms[0].data_1173497,'cf_1173497')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1042891" name="nome_1042891" value="mario"/></td> 
<td><input type="text" id="cognome_1042891" name="cognome_1042891" value="grimaldi"/></td> 
<td><input type="text" id="data_1042891" name="data_1042891" value="17/10/1935"/></td> 
<td><input type="radio" id="sessom_1042891" name="sesso_1042891" value="M" checked/> M
<input type="radio" id="sessof_1042891" name="sesso_1042891" value="F"/> F
</td>  
<td><input type="text" id="comune_1042891" name="comune_1042891" value="salerno"/></td>
<td><input type="text" id="cf_1042891" name="cf_1042891" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1042891,document.forms[0].nome_1042891,document.forms[0].cognome_1042891,document.forms[0].comune_1042891,document.forms[0].data_1042891,'cf_1042891')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1176858" name="nome_1176858" value="federica"/></td> 
<td><input type="text" id="cognome_1176858" name="cognome_1176858" value="lombardi"/></td> 
<td><input type="text" id="data_1176858" name="data_1176858" value="01/08/1984"/></td> 
<td><input type="radio" id="sessom_1176858" name="sesso_1176858" value="M"/> M
<input type="radio" id="sessof_1176858" name="sesso_1176858" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1176858" name="comune_1176858" value="napoli"/></td>
<td><input type="text" id="cf_1176858" name="cf_1176858" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1176858,document.forms[0].nome_1176858,document.forms[0].cognome_1176858,document.forms[0].comune_1176858,document.forms[0].data_1176858,'cf_1176858')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1192063" name="nome_1192063" value="erika"/></td> 
<td><input type="text" id="cognome_1192063" name="cognome_1192063" value="bonafine"/></td> 
<td><input type="text" id="data_1192063" name="data_1192063" value="10/12/1981"/></td> 
<td><input type="radio" id="sessom_1192063" name="sesso_1192063" value="M"/> M
<input type="radio" id="sessof_1192063" name="sesso_1192063" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1192063" name="comune_1192063" value=""/></td>
<td><input type="text" id="cf_1192063" name="cf_1192063" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1192063,document.forms[0].nome_1192063,document.forms[0].cognome_1192063,document.forms[0].comune_1192063,document.forms[0].data_1192063,'cf_1192063')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1191961" name="nome_1191961" value="pasqualina"/></td> 
<td><input type="text" id="cognome_1191961" name="cognome_1191961" value="maiuri"/></td> 
<td><input type="text" id="data_1191961" name="data_1191961" value="10/04/1970"/></td> 
<td><input type="radio" id="sessom_1191961" name="sesso_1191961" value="M"/> M
<input type="radio" id="sessof_1191961" name="sesso_1191961" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1191961" name="comune_1191961" value="moio della civitella"/></td>
<td><input type="text" id="cf_1191961" name="cf_1191961" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1191961,document.forms[0].nome_1191961,document.forms[0].cognome_1191961,document.forms[0].comune_1191961,document.forms[0].data_1191961,'cf_1191961')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1168495" name="nome_1168495" value="francesco"/></td> 
<td><input type="text" id="cognome_1168495" name="cognome_1168495" value="di donna"/></td> 
<td><input type="text" id="data_1168495" name="data_1168495" value="03/10/1967"/></td> 
<td><input type="radio" id="sessom_1168495" name="sesso_1168495" value="M" checked/> M
<input type="radio" id="sessof_1168495" name="sesso_1168495" value="F"/> F
</td>  
<td><input type="text" id="comune_1168495" name="comune_1168495" value="napoli"/></td>
<td><input type="text" id="cf_1168495" name="cf_1168495" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1168495,document.forms[0].nome_1168495,document.forms[0].cognome_1168495,document.forms[0].comune_1168495,document.forms[0].data_1168495,'cf_1168495')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1169257" name="nome_1169257" value="mario"/></td> 
<td><input type="text" id="cognome_1169257" name="cognome_1169257" value="volpe"/></td> 
<td><input type="text" id="data_1169257" name="data_1169257" value="14/04/1941"/></td> 
<td><input type="radio" id="sessom_1169257" name="sesso_1169257" value="M" checked/> M
<input type="radio" id="sessof_1169257" name="sesso_1169257" value="F"/> F
</td>  
<td><input type="text" id="comune_1169257" name="comune_1169257" value="EBOLI"/></td>
<td><input type="text" id="cf_1169257" name="cf_1169257" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1169257,document.forms[0].nome_1169257,document.forms[0].cognome_1169257,document.forms[0].comune_1169257,document.forms[0].data_1169257,'cf_1169257')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1194529" name="nome_1194529" value="michele"/></td> 
<td><input type="text" id="cognome_1194529" name="cognome_1194529" value="falco"/></td> 
<td><input type="text" id="data_1194529" name="data_1194529" value="04/08/1991"/></td> 
<td><input type="radio" id="sessom_1194529" name="sesso_1194529" value="M" checked/> M
<input type="radio" id="sessof_1194529" name="sesso_1194529" value="F"/> F
</td>  
<td><input type="text" id="comune_1194529" name="comune_1194529" value="aversa"/></td>
<td><input type="text" id="cf_1194529" name="cf_1194529" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1194529,document.forms[0].nome_1194529,document.forms[0].cognome_1194529,document.forms[0].comune_1194529,document.forms[0].data_1194529,'cf_1194529')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1002842" name="nome_1002842" value="Giuseppe maria"/></td> 
<td><input type="text" id="cognome_1002842" name="cognome_1002842" value="Carotenuto"/></td> 
<td><input type="text" id="data_1002842" name="data_1002842" value="28/10/1968"/></td> 
<td><input type="radio" id="sessom_1002842" name="sesso_1002842" value="M" checked/> M
<input type="radio" id="sessof_1002842" name="sesso_1002842" value="F"/> F
</td>  
<td><input type="text" id="comune_1002842" name="comune_1002842" value="Napoli"/></td>
<td><input type="text" id="cf_1002842" name="cf_1002842" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1002842,document.forms[0].nome_1002842,document.forms[0].cognome_1002842,document.forms[0].comune_1002842,document.forms[0].data_1002842,'cf_1002842')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1066435" name="nome_1066435" value="antonietta"/></td> 
<td><input type="text" id="cognome_1066435" name="cognome_1066435" value="giaquinto"/></td> 
<td><input type="text" id="data_1066435" name="data_1066435" value="22/12/1969"/></td> 
<td><input type="radio" id="sessom_1066435" name="sesso_1066435" value="M"/> M
<input type="radio" id="sessof_1066435" name="sesso_1066435" value="F" checked/> F
</td>  
<td><input type="text" id="comune_1066435" name="comune_1066435" value="limatola"/></td>
<td><input type="text" id="cf_1066435" name="cf_1066435" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1066435,document.forms[0].nome_1066435,document.forms[0].cognome_1066435,document.forms[0].comune_1066435,document.forms[0].data_1066435,'cf_1066435')"/></td>
</tr>


<tr>
<td><input type="text" id="nome_1004844" name="nome_1004844" value="Roberto"/></td> 
<td><input type="text" id="cognome_1004844" name="cognome_1004844" value="Meriano"/></td> 
<td><input type="text" id="data_1004844" name="data_1004844" value="24/08/1969"/></td> 
<td><input type="radio" id="sessom_1004844" name="sesso_1004844" value="M" checked/> M
<input type="radio" id="sessof_1004844" name="sesso_1004844" value="F"/> F
</td>  
<td><input type="text" id="comune_1004844" name="comune_1004844" value="salerno"/></td>
<td><input type="text" id="cf_1004844" name="cf_1004844" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1004844,document.forms[0].nome_1004844,document.forms[0].cognome_1004844,document.forms[0].comune_1004844,document.forms[0].data_1004844,'cf_1004844')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1165957" name="nome_1165957" value="biagio"/></td> 
<td><input type="text" id="cognome_1165957" name="cognome_1165957" value="guerra"/></td> 
<td><input type="text" id="data_1165957" name="data_1165957" value="05/11/1949"/></td> 
<td><input type="radio" id="sessom_1165957" name="sesso_1165957" value="M" checked/> M
<input type="radio" id="sessof_1165957" name="sesso_1165957" value="F"/> F
</td>  
<td><input type="text" id="comune_1165957" name="comune_1165957" value="napoli"/></td>
<td><input type="text" id="cf_1165957" name="cf_1165957" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1165957,document.forms[0].nome_1165957,document.forms[0].cognome_1165957,document.forms[0].comune_1165957,document.forms[0].data_1165957,'cf_1165957')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1166706" name="nome_1166706" value="giuseppe"/></td> 
<td><input type="text" id="cognome_1166706" name="cognome_1166706" value="ruggiero"/></td> 
<td><input type="text" id="data_1166706" name="data_1166706" value="17/03/1985"/></td> 
<td><input type="radio" id="sessom_1166706" name="sesso_1166706" value="M" checked/> M
<input type="radio" id="sessof_1166706" name="sesso_1166706" value="F"/> F
</td>  
<td><input type="text" id="comune_1166706" name="comune_1166706" value="napoli"/></td>
<td><input type="text" id="cf_1166706" name="cf_1166706" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1166706,document.forms[0].nome_1166706,document.forms[0].cognome_1166706,document.forms[0].comune_1166706,document.forms[0].data_1166706,'cf_1166706')"/></td>
</tr>

<tr>
<td><input type="text" id="nome_1051486" name="nome_1051486" value="GIOVANNI"/></td> 
<td><input type="text" id="cognome_1051486" name="cognome_1051486" value="CHIAVAZZO"/></td> 
<td><input type="text" id="data_1051486" name="data_1051486" value="01/12/1962"/></td> 
<td><input type="radio" id="sessom_1051486" name="sesso_1051486" value="M" checked/> M
<input type="radio" id="sessof_1051486" name="sesso_1051486" value="F"/> F
</td>  
<td><input type="text" id="comune_1051486" name="comune_1051486" value="ANGRI (SA)"/></td>
<td><input type="text" id="cf_1051486" name="cf_1051486" value=""/></td>  
<td><input type="button" name="bottoniCf" value="CALCOLA" onClick="javascript:CalcolaCF(document.forms[0].sesso_1051486,document.forms[0].nome_1051486,document.forms[0].cognome_1051486,document.forms[0].comune_1051486,document.forms[0].data_1051486,'cf_1051486')"/></td>
</tr>
</table>
</form>
<input type="button" value="CALCOLA TUTTI I CF" onClick="CalcolaTuttiCF()"/>
<input type="button" value="OTTIENI STRINGA UPDATE" onClick="GetUpdate()"/>

<textarea rows="10" cols="70" id="update" name="update"/></textarea>

<script>
function CalcolaTuttiCF(){
var buttons = document.getElementsByName('bottoniCf');
for(var i = 0; i < buttons.length; i++)  
   buttons[i].click();
}

function GetUpdate(){
var ret = '';
	var inputs = document.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++) {
	    if(inputs[i].name.indexOf('cf_') == 0) {
	    	var split = inputs[i].name.split('_');
	    	if (inputs[i].value!='')
			ret = ret + 'update farmacie_import set codice_fiscale_rapp = "'+inputs[i].value+'" where org_id = '+split[1]+';';
	    }
	}
	document.getElementById("update").innerHTML = ret;
}
</script>


