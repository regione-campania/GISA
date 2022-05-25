<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <h3>IMPRESA</h3>
        <fieldset>
                <fieldset>
                        <legend>DATI IMPRESA</legend>
                        <table style="height: 100%; width: 100%">
                                <tr id="tipo_societaTR">
                                        <td nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
                                        <td><%=TipoSocietaList.getHtmlSelect("tipo_societa", -1)%></td>
                                </tr>
                                <tr>
                                        <td nowrap>
                                                <p id="labelRagSoc">
                                                        DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
                                                </p>
                                        </td>
                                        <td><input type="text" size="70" id="ragioneSociale"
                                                class="required" name="ragioneSociale"></td>
                                </tr>
                                <tr>
                                        <td nowrap>PARTITA IVA</td>
                                        <td><input type="text" size="70" min="11" maxlength="11"
                                                id="partitaIva" name="partitaIva"></td>
                                </tr>
                                <tr id="codFiscaleTR">
                                        <td nowrap>CODICE FISCALE<br>IMPRESA
                                        </td>
                                        <td><input type="text" size="70" maxlength="16"
                                                id="codFiscale" name="codFiscale" class="required"> <input
                                                type="checkbox" id="codFiscaleUguale"
                                                onClick="gestisciCFUguale(this)" /> Uguale alla P.IVA</td>
                                </tr>
                                <tr id="codFiscaleTR">
                                        <td nowrap>DOMICILIO DIGITALE(PEC)</td>
                                        <td><input type="text" size="100" maxlength="300"
                                                id="domicilioDigitale" class="required" name="domicilioDigitale">
                                        </td>
                                </tr>
                                <tr>
                                        <td>NOTE</td>
                                        <td><textarea name="noteImpresa" cols="50" rows="5"
                                                        id="noteImpresa" value=""></textarea></td>
                                </tr>
                        </table>
                </fieldset>
                <fieldset>
                        <legend>DATI TITOLARE/LEGALE RAPPRESENTANTE</legend>
                        <table style="height: 100%; width: 100%">
                                <tr>
                                        <td>NOME</td>
                                        <td><input type="text" size="70" id="nome" name="nome"
                                                class="required"></td>
                                </tr>
                                <tr>
                                        <td><label for="cognome-2">COGNOME </label></td>
                                        <td><input type="text" size="70" id="cognome" name="cognome"
                                                class="required"></td>
                                </tr>
                                <tr>
                                        <td><label for="sesso-2">SESSO </label></td>
                                        <td><div class="test">
                                                        <input type="radio" name="sesso" id="sesso1" value="M"
                                                                checked="checked" class="required css-radio"> <label
                                                                for="sesso1" class="css-radiolabel radGroup2">M</label> <input
                                                                type="radio" name="sesso" id="sesso2" value="F"
                                                                class="required css-radio"> <label for="sesso2"
                                                                class="css-radiolabel radGroup2">F</label>
                                                </div></td>
                                </tr>
                                <tr>
                                        <td><label for="dataN-2">DATA NASCITA </label></td>
                                        <td><input type="text" size="15" name="dataNascita"
                                                id="dataNascita2" class="required" placeholder="dd/MM/YYYY">&nbsp;&nbsp;
                                        </td>
<script>
$(function() {
        $('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});// , showOnFocus: false, showTrigger: '#calImg'}); 
});
</script>
                                </tr>
                                <tr>
                                        <td><label for="nazioneN-2">NAZIONE NASCITA</label></td>
                                        <td>
                                                <%
                                                        NazioniList
                                                                        .setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");
                                                %>
                                                <%=NazioniList.getHtmlSelect("nazioneNascita", 106)%></td>
                                </tr>
                                <tr>
                                        <td nowrap>COMUNE NASCITA</td>
                                        <td><select name="comuneNascita" id="comuneNascita"
                                                class="required">
                                                        <option value="">SELEZIONA COMUNE</option>
                                        </select> <input type="hidden" name="comuneNascitaTesto"
                                                id="comuneNascitaTesto" /></td>
                                </tr>
                                <tr>
                                        <td nowrap>CODICE FISCALE</td>
                                        <td><input type="text" name="codFiscaleSoggetto"
                                                readonly="readonly" id="codFiscaleSoggetto" class="required" /></td>
                                </tr>
                                <tr>
                                        <td>&nbsp;</td>
                                        <td><input type="button" id="calcoloCF" class="newButtonClass"
                                                value="CALCOLA CODICE FISCALE"
                                                onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].comuneNascitainput,document.forms[0].dataNascita,'codFiscaleSoggetto')"></input>
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
                                        <td>
                                                <%
                                                        NazioniList
                                                                        .setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");
                                                %>
                                                <%=NazioniList.getHtmlSelect("nazioneResidenza", 106)%></td>
                                </tr>
                                <tr id="addressLegaleCountryTR">
                                        <td nowrap>PROVINCIA RESIDENZA</td>
                                        <td><select id="addressLegaleCountry" class="required"
                                                name="addressLegaleCountry">
                                                        <option value="">SELEZIONA PROVINCIA</option>
                                        </select> <input type="hidden" id="addressLegaleCountryTesto"
                                                name="addressLegaleCountryTesto" /></td>
                                </tr>
                                <tr>
                                        <td nowrap>COMUNE RESIDENZA</td>
                                        <td><select name="addressLegaleCity" id="addressLegaleCity"
                                                class="required">
                                                        <option value="">SELEZIONA COMUNE</option>
                                        </select> <input type="hidden" name="addressLegaleCityTesto"
                                                id="addressLegaleCityTesto" /></td>
                                </tr>
                                <tr>
                                        <td>INDIRIZZO RESIDENZA</td>
                                        <td>
                                                <table class="noborder">
                                                        <tr>
                                                                <td><%=ToponimiList.getHtmlSelect("toponimoResidenza", "VIA")%>
                                                                </td>
                                                                <td><input type="text" name="addressLegaleLine1"
                                                                        id="addressLegaleLine1" class="required">
                                                                            
                                                               </td>
                                                                <td><input type="text" name="civicoResidenza"
                                                                        id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"
                                                                        required="required"></td>
                                                                <td><input type="text" name="capResidenza"
                                                                        id="capResidenza" size="4" placeholder="CAP" maxlength="5"
                                                                        required="required" onfocus="chkCap(document.getElementById('addressLegaleCity').value, 'capResidenza')"  title="CAP residenza non valido"> 
                                                                        <input type="button" value="Calcola CAP"
                                                                        onclick="calcolaCap(document.getElementById('addressLegaleCity').value, 'capResidenza');" />
                                                                </td>
                                                        </tr>
                                                </table>
                                        </td>
                                </tr>
                                <tr>
                                        <td>DOMICILIO DIGITALE<br>(PEC)
                                        </td>
                                        <td><input type="text" size="70" name="domicilioDigitalePecSF">
                                        </td>
                                </tr>
                        </table>
                </fieldset>
                <fieldset id="setSedeLegale">
                        <legend>DATI SEDE LEGALE</legend>
                        <table style="height: 100%; width: 100%">
                                <tr>
                                        <td></td>
                                        <td><input type="checkbox" onclick="copiaDaResidenza()"
                                                id="checkSeddeLegale"> SPUNTARE SE L'INDIRIZZO DI
                                                RESIDENZA COINCIDE CON LA SEDE LEGALE</td>
                                </tr>
                                <tr>
                                        <td><label for="nazioneN-2">NAZIONE</label></td>
                                        <td>
                                                <%
                                                        NazioniList
                                                                        .setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via')\"");
                                                %>
                                                <%=NazioniList.getHtmlSelect("nazioneSedeLegale", 106)%></td>
                                </tr>
                                <tr id="searchcodeIdprovinciaTR">
                                        <td>PROVINCIA</td>
                                        <td><select name="searchcodeIdprovincia"
                                                id="searchcodeIdprovincia" class="required">
                                                        <option value=""></option>
                                        </select> <input type="hidden" name="searchcodeIdprovinciaTesto"
                                                id="searchcodeIdprovinciaTesto" /></td>
                                </tr>
                                <tr>
                                        <td>COMUNE</td>
                                        <td><select name="searchcodeIdComune" id="searchcodeIdComune"
                                                class="required">
                                                        <option value=""></option>
                                        </select> <input type="hidden" name="searchcodeIdComuneTesto"
                                                id="searchcodeIdComuneTesto" /></td>
                                </tr>
                                <tr>
                                        <td>INDIRIZZO</td>
                                        <td>
                                                <table class="noborder">
                                                        <tr>
                                                                <td><%=ToponimiList.getHtmlSelect("toponimoSedeLegale", "VIA")%>
                                                                </td>
                                                                <td><input type="text" name="via" id="via" class="required" size="80">
                                                                               
                                                                </td>
                                                                <td><input type="text" name="civicoSedeLegale"
                                                                        id="civicoSedeLegale" size="5" placeholder="NUM."
                                                                        maxlength="15" required="required"></td>
                                                                <td><input type="text" name="presso" id="presso" size="4"
                                                                        placeholder="CAP" maxlength="5" required="required"
                                                                        onfocus="chkCap(document.getElementById('searchcodeIdComune').value, 'presso')"  title="CAP residenza non valido"> 
                                                                        <input
                                                                        type="button" value="Calcola CAP"
                                                                        onclick="calcolaCap(document.getElementById('searchcodeIdComune').value, 'presso');" />
                                                                </td>
                                                        </tr>
                                                </table>
                                        </td>
                                </tr>
                        </table>
                </fieldset>
        </fieldset>