<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <h3>STABILIMENTO</h3>
        <fieldset>
                <legend>DATI STABILIMENTO</legend>
                <table id="numReg" style="display: none">
                        <tr>
                                <td>NUMERO REGISTRAZIONE STABILIMENTO</td>
                                <td>
                                        <input type = "text" name = "numeroRegistrazione" onchange="checkStabNumReg()" required="required">
                                </td>
                        </tr>
                        </table>
                        <table>
                        <tr>
                                <td>TIPO ATTIVITA</td>
                                <td>
                                        <%TipoAttivita.setRequired(true);
                                                TipoAttivita.setJsEvent("onchange='mostraDatiStabilimento(this.value);mostraAttivitaProduttive(\"attprincipale\",1,-1, false,"+newStabilimento.getTipoInserimentoScia()+");'");
                                        %> <%=TipoAttivita.getHtmlSelect("tipoAttivita", -1)%>
                                </td>
                        </tr>
                        <tr>
                                <td>CARATTERE</td>
                                <td>
                                        <%
                                        TipoCarattere.setRequired(true);
                                                TipoCarattere.setJsEvent("onchange=visualizzaData(this);");
                                        %> <%=TipoCarattere.getHtmlSelect("tipoCarattere", -1)%>
                                </td>
                        </tr>
                        
                         <tr style="display: none" id="trDataInizio">
                                <td>DATA INIZIO</td>
                                <td><input type="text" size="15" name="dataInizioAttivita"
                                        id="dataInizio" class="required" placeholder="dd/MM/YYYY" readonly>
                                </td>
                        </tr>
                        <tr style="display: none" id="trDataFine">
                                <td>DATA FINE</td>
                                <td><input type="text" size="15" name="dataFineAttivita"
                                        id="dataFine" class="required" value="" placeholder="dd/MM/YYYY" readonly></td>
                        </tr>
                        
                </table>
                <br> <br>
                <table id="datiIndirizzoStab" style="display: none">
                        <tr>
                                <td></td>
                                <td><input type="checkbox" onclick="copiaDaLegale()"
                                        id="checkSeddeOperativa"> SPUNTARE SE LA SEDE PRODUTTIVA
                                        COINCIDE CON LA SEDE LEGALE</td>
                        </tr>
                        <tr>
                                <td>PROVINCIA</td>
                                <td><select name="searchcodeIdprovinciaStab"
                                        id="searchcodeIdprovinciaStab" class="required">
                                                <option value=""></option>
                                </select> <input type="hidden" name="searchcodeIdprovinciaTestoStab"
                                        id="searchcodeIdprovinciaTestoStab" /></td>
                        </tr>
                        <tr>
                                <td>COMUNE</td>
                                <td><select name="searchcodeIdComuneStab"
                                        id="searchcodeIdComuneStab" class="required">
                                                <option value=""></option>
                                </select> <input type="hidden" name="searchcodeIdComuneTestoStab"
                                        id="searchcodeIdComuneTestoStab" /></td>
                        </tr>
                        <tr>
                                <td>INDIRIZZO</td>
                                <td>
                                        <table class="noborder">
                                                <tr>
                                                        <td><%=ToponimiList.getHtmlSelect("toponimoSedeOperativa", "VIA")%>
                                                        </td>
                                                        <td><input type="text" name="viaStab" id="viaStab" class="required">
                                                                       </td>

                                                        <td><input type="text" name="civicoSedeOperativa"
                                                                id="civicoSedeOperativa" required="required" placeholder="NUM."
                                                                size="4" maxlength="7"></td>
                                                        <td><input type="text" size="4" id="capStab" name="capStab"
                                                                maxlength="5" value="" required="required" placeholder="CAP">
                                                                <input type="button" value="Calcola CAP"
                                                                onclick="calcolaCap(document.getElementById('searchcodeIdComuneStab').value, 'capStab');" />
                                                        </td>
                                                        <td>
                                                                <!-- <input id="coord1button" type="button" value="Calcola Coordinate"
                                                            onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+getSelectedText('viaStab')+', '+ getSelectedText('searchcodeIdComuneStab') + ', '+ getSelectedText('searchcodeIdprovinciaStab') + ' '+ document.forms['example-advanced-form'].capStab.value);" /> 
                                                            --> <input id="coord1button" type="button"
                                                                value="Calcola Coordinate"
                                                                onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+document.getElementById('viaStab').value+', '+document.getElementById('civicoSedeOperativa').value, getSelectedText('searchcodeIdComuneStab'), getSelectedText('searchcodeIdprovinciaStab'), document.getElementById('capStab').value, document.getElementById('latStab'), document.getElementById('longStab'));" />
                                                        </td>
                                                </tr>
                                        </table>
                                </td>
                        </tr>
                        <tr>
                                <td>COORDINATE</td>
                                <td>LAT <input type="text" name="latStab" id="latStab" value=""
                                        class="required" onChange="controllaCoordinate(this, 'lat')" />
                                        LONG <input type="text" name="longStab" id="longStab" value=""
                                        class="required" onChange="controllaCoordinate(this, 'long')" />
                                </td>
                        </tr>
                        <tr>
                                <td>TELEFONO</td>
                                <td><input type="text" name="telefono"></td>
                        </tr>
                       
<script>
$(function() {
        $('#dataInizio').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'}); 
        $('#dataFine').datepick({dateFormat: 'dd/mm/yyyy', showOnFocus: false, showTrigger: '#calImg',onClose: controlloDate}); 
});
</script>
                        <tr>
                                <td>NOTE</td>
                                <td><textarea name="noteStab" cols="50" rows="5" id="noteStab"
                                                value=""></textarea></td>
                        </tr>
                </table>
             
             <!--  DATI SEDE MOBILE -->
	<!-- 	<%//@ include file="campi_mobile_add.jsp"%> -->

        </fieldset>