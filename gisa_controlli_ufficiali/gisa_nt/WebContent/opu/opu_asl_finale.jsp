<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<h3>FORM</h3>
        <fieldset>
                <legend>SCHEDA STABILIMENTO</legend>
                <iframe height="650px" id="test2"
                        src="./schede_centralizzate/iframe.jsp?objectId=<%=newStabilimento.getIdStabilimento()%>&objectIdName=stab_id&tipo_dettaglio=15"
                        name="test"> </iframe>
                <div align="right">
                        <img src="images/icons/stock_print-16.gif" border="0"
                                align="absmiddle" height="16" width="16" /> <input type="button"
                                title="Stampa Ricevuta" value="Stampa Ricevuta"
                                onClick="openRichiestaPDFOpu('<%=newStabilimento.getIdStabilimento()%>', '-1', '-1', '-1', '15');">
                </div>
        </fieldset>