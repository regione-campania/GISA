<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<center>
  
       <%if(StabilimentoSoggettoAScia.getOperatore().getTipo_impresa()!=9 && StabilimentoSoggettoAScia.getOperatore().getTipo_impresa()!=10){ %>
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton" 
                                   value="<%=SuapTipoScia.getSelectedValue(StabilimentoAction.SCIA_AMPLIAMENTO)  %>" style="width: 400px !important;"
                                onclick="selezionaOperazione('ampliamento', this.form)">
                               
                         </td>
                 </tr>
        </table>
        <%} %>
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton"
                                value="<%=SuapTipoScia.getSelectedValue(StabilimentoAction.SCIA_VARIAZIONE_TITOLARITA)  %>" style="width: 400px !important;"
                                onclick="selezionaOperazione('cambioTitolarita', this.form)" width="100%">
                        </td>
                </tr>
        </table>
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton"
                                value="<%=SuapTipoScia.getSelectedValue(StabilimentoAction.SCIA_MODIFICA_STATO_LUOGHI)  %>" style="width: 400px !important;"
                                onclick="selezionaOperazione('modificaStatoLuoghi', this.form)" width="100%">
                        </td>
                </tr>
        </table>
        <table>
                <tr>
                        <td><input type="button" class="yellowBigButton"
                            value="<%=SuapTipoScia.getSelectedValue(StabilimentoAction.SCIA_CESSAZIONE)  %>"
                                onclick="selezionaOperazione('cessazione', this.form)" style="width: 400px !important;"></td>
                </tr>
                
                <%
                if (newStabilimento.getTipoInserimentoScia() == newStabilimento.TIPO_SCIA_RICONOSCIUTI)
                {
                	%>
                	 <tr>
                        <td><input type="button" class="yellowBigButton"
                            value="<%=SuapTipoScia.getSelectedValue(StabilimentoAction.SCIA_SOSPENSIONE)  %>"
                                onclick="selezionaOperazione('sospensione', this.form)" style="width: 400px !important;"></td>
                </tr>
                	<%
                }
                %>
                
        </table>
  
               
  </center>