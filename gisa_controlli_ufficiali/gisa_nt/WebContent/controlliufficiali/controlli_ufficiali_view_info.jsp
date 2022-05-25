<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label
		name="campioni.data_richiesta">Data Inizio Controllo</dhv:label></td>
	<td><zeroio:tz timestamp="<%=TicketDetails.getAssignedDate()%>"
		dateOnly="true"
		timeZone="<%=TicketDetails.getAssignedDateTimeZone()%>"
		showTimeZone="false" default="&nbsp;" /></td>
</tr>





<%
	if (TicketDetails.getDataFineControllo() != null) {
%>

<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="">Data Fine Controllo</dhv:label>
	</td>
	<td><%=(new SimpleDateFormat("dd/MM/yyyy"))
								.format(TicketDetails.getDataFineControllo()
										.getTime())%></td>
</tr>
<%
	}
%>



<%
if (request.getAttribute("tipologia")!=null && "201".equals(""+request.getAttribute("tipologia")))
{
	if(TicketDetails.getTipoCampione()==4)
	{
%>
<tr class="containerBody">
		<td class="formLabel">Verifica quantitativo prodotto raccolto</td>
		<td>
		<%=VerificaQuantitativo.getSelectedValue(TicketDetails.getQuantitativo()) %>
		Quintali <%=TicketDetails.getQuintali()%>
		</td>
	</tr>
	

<%	

	}
}
%>

<%
	if (TicketDetails.getTipoCampione() ==4  || TicketDetails.getTipoCampione() ==2  || TicketDetails.getTipoCampione() ==3  || TicketDetails.getTipoCampione() ==5) {
%>
<tr class="containerBody">
	<td valign="top" class="formLabel"><dhv:label name="sanzioni.note">Oggetto del Controllo</dhv:label>
	</td>
	<td>

	<table class="noborder">
		<tr>
			<td>
			<%
				String ispezioni = "<b></b> <br>";
					Iterator<Integer> kiave = TicketDetails
							.getLisaElementi_Ispezioni().keySet().iterator();
					String ispezioneSel = "";
					while (kiave.hasNext()) {

						int key = kiave.next();

						out.print("<b><font color='blue'> "
								+ IspezioneMacrocategorie.getValueFromId(key)
								+ "</font></b><br> ");

						HashMap<Integer, String> lista = TicketDetails
								.getLisaElementi_Ispezioni().get(key);

						Iterator<Integer> kiave1 = lista.keySet().iterator();

						while (kiave1.hasNext()) {

							out.println(lista.get(kiave1.next()) + "<br>");
						}

					}
			%>
			</td>
			<td>&nbsp;</td>
			<td>
			<%
				if (!"".equals(TicketDetails.getIspezioni_desc1())
							&& TicketDetails.getIspezioni_desc1() != null) {
						out
								.print("<br><b>Settore Alimenti per il consumo Umano (Descrizione): </b><br>"
										+ TicketDetails.getIspezioni_desc1());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc2())
							&& TicketDetails.getIspezioni_desc2() != null) {
						out
								.print("<br><b>Settore alimenti Zootecnici (Descrizione): </b><br>"
										+ TicketDetails.getIspezioni_desc2());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc3())
							&& TicketDetails.getIspezioni_desc3() != null) {

						out
								.print("<br><b>Note Settore Benessere Animale non durante il trasporto : </b><br> "
										+ TicketDetails.getIspezioni_desc3());

						out
								.print("<br><b>Settore Benessere Animale (Descrizione): </b><br> "
										+ TicketDetails.getIspezioni_desc3());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc4())
							&& TicketDetails.getIspezioni_desc4() != null) {
						out
								.print("<br><b>Settore Sanita animale (Descrizione): </b><br>"
										+ TicketDetails.getIspezioni_desc4());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc5())
							&& TicketDetails.getIspezioni_desc5() != null) {
						out
								.print("<br><b>Settore S.O.A. negli Impianti di trasformazione (Descrizione): </b><br>"
										+ TicketDetails.getIspezioni_desc5());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc6())
							&& TicketDetails.getIspezioni_desc6() != null) {
						out
								.print("<br><b>Settore Rifiuti S.O.A. nelle altre imprese (Descrizione): </b><br>"
										+ TicketDetails.getIspezioni_desc6());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc7())
							&& TicketDetails.getIspezioni_desc7() != null) {
						out.print("<br><b>Altro (Descrizione): </b><br>"
								+ TicketDetails.getIspezioni_desc7());

					}
					if (!"".equals(TicketDetails.getIspezioni_desc8())
							&& TicketDetails.getIspezioni_desc8() != null) {
						out
								.print("<br><b>Note Settore Benessere Animale durante il trasporto : </b><br>"
										+ TicketDetails.getIspezioni_desc8());

					}
			%>
			</td>
		</tr>


	</table>


	</td>
</tr>
<%
	}
%>

<%
if((TicketDetails.getNum_documento_accompagnamento()!=null && !TicketDetails.getNum_documento_accompagnamento().equals("")) || TicketDetails.getNum_specie1()>0 || TicketDetails.getNum_specie2()>0 || TicketDetails.getNum_specie3()>0
		|| TicketDetails.getNum_specie4()>0|| TicketDetails.getNum_specie5()>0|| TicketDetails.getNum_specie6()>0
		|| TicketDetails.getNum_specie7()>0|| TicketDetails.getNum_specie8()>0|| TicketDetails.getNum_specie9()>0
		|| TicketDetails.getNum_specie10()>0|| TicketDetails.getNum_specie11()>0|| TicketDetails.getNum_specie12()>0
		|| TicketDetails.getNum_specie13()>0|| TicketDetails.getNum_specie14()>0|| TicketDetails.getNum_specie15()>0
		|| TicketDetails.getNum_specie22()>0 || TicketDetails.getNum_specie23()>0 || TicketDetails.getNum_specie24()>0
		|| TicketDetails.getNum_specie25()>0 || TicketDetails.getNum_specie26()>0)
{
%>
<tr class="containerBody">
	<td valign="top" class="formLabel"><dhv:label name="sanzioni.note">Specie Animali Trasportati</dhv:label>
	</td>
	<td>
	<table class="noborder">
		<tr>
			
			<td>&nbsp;</td>
			<td>
			<%
			if (TicketDetails.getNum_documento_accompagnamento()!=null && !TicketDetails.getNum_documento_accompagnamento().equals("")) {
				out.print("<br><b>Num. docum. di accompagnam. controllati: </b>"
						+ TicketDetails.getNum_documento_accompagnamento());

			}
				if (TicketDetails.getNum_specie1() > 0) {
						out.print("<br><b> Num. capi bovini: </b>"
								+ TicketDetails.getNum_specie1());

					}
					if (TicketDetails.getNum_specie2() > 0) {
						out.print("<br><b> Num. capi suini: </b>"
								+ TicketDetails.getNum_specie2());

					}
					if (TicketDetails.getNum_specie3() > 0) {
						out.print("<br><b> Num. capi equidi: </b>"
								+ TicketDetails.getNum_specie3());

					}
					if (TicketDetails.getNum_specie4() > 0) {
						out.print("<br><b> Num. Altre specie: </b>"
								+ TicketDetails.getNum_specie4());

					}
					if (TicketDetails.getNum_specie5() > 0) {
						out.print("<br><b> Num. capi Bufali : </b>"
								+ TicketDetails.getNum_specie5());

					}
					if (TicketDetails.getNum_specie6() > 0) {
						out.print("<br><b> Num. Pesci di acqua dolce: </b>"
								+ TicketDetails.getNum_specie6());

					}
					if (TicketDetails.getNum_specie7() > 0) {
						out.print("<br><b> Num. Pesci ornamentali : </b>"
								+ TicketDetails.getNum_specie7());

					}
					if (TicketDetails.getNum_specie8()> 0) {
						out.print("<br><b> Num. Oche: </b>"
								+ TicketDetails.getNum_specie8());

					}
					if (TicketDetails.getNum_specie9() > 0) {
						out.print("<br><b> Num. Conigli: </b>"
								+ TicketDetails.getNum_specie9());

					}
					if (TicketDetails.getNum_specie10() > 0) {
						out.print("<br><b> Num. Ovaiole: </b>"
								+ TicketDetails.getNum_specie10());

					}
					if (TicketDetails.getNum_specie11()> 0) {
						out.print("<br><b> Num. Broiler: </b>"
								+ TicketDetails.getNum_specie11());

					}
					if (TicketDetails.getNum_specie12() > 0) {
						out.print("<br><b> Num. Vitelli: </b>"
								+ TicketDetails.getNum_specie12());

					}
					if (TicketDetails.getNum_specie13()> 0) {
						out.print("<br><b> Num. Struzzi: </b>"
								+ TicketDetails.getNum_specie13());

					}
					if (TicketDetails.getNum_specie14() > 0) {
						out.print("<br><b> Num. Cani: </b>"
								+ TicketDetails.getNum_specie14());

					}
					if (TicketDetails.getNum_specie15() > 0) {
						out.print("<br><b> Num. capi di Ovicaprini: </b>"
								+ TicketDetails.getNum_specie15());

					}
					if (TicketDetails.getNum_specie22() > 0) {
						out.print("<br><b> Num. capi di Pollame: </b>"
								+ TicketDetails.getNum_specie22());

					}
					if (TicketDetails.getNum_specie23() > 0) {
						out.print("<br><b> Num. Pesci: </b>"
								+ TicketDetails.getNum_specie23());

					}
					if (TicketDetails.getNum_specie24() > 0) {
						out.print("<br><b> Num. Uccelli: </b>"
								+ TicketDetails.getNum_specie24());

					}
					if (TicketDetails.getNum_specie25() > 0) {
						out.print("<br><b> Num. Rettili: </b>"
								+ TicketDetails.getNum_specie25());

					}
					if (TicketDetails.getNum_specie26() > 0) {
						out.print("<br><b> Num. capi di Altre specie: </b>"
								+ TicketDetails.getNum_specie26());

					}
			%>
			</td>
		</tr>

	</table>

	</td>
</tr>
<%
}
%>  


<dhv:evaluate if="<%=hasText(TicketDetails.getProblem())%>">
	<tr class="containerBody">
		<td valign="top" class="formLabel"><dhv:label
			name="sanzioni.note">Note</dhv:label></td>
		<td><%=toString(TicketDetails.getProblem())%></td>
	</tr>
</dhv:evaluate>


<!-- aggiunto da d.dauria -->
<%

	if (((TicketDetails.getNucleoIspettivo() > -1) && (TicketDetails
			.getComponenteNucleo() != ""))
			|| ((TicketDetails.getNucleoIspettivoDue() > -1) && (TicketDetails
					.getComponenteNucleoDue() != ""))
			|| ((TicketDetails.getNucleoIspettivoTre() > -1) && (TicketDetails
					.getComponenteNucleoTre() != ""))
			|| ((TicketDetails.getNucleoIspettivoQuattro() > -1) && (TicketDetails
					.getComponenteNucleoQuattro() != ""))
			|| ((TicketDetails.getNucleoIspettivoCinque() > -1) && (TicketDetails
					.getComponenteNucleoCinque() != ""))
			|| ((TicketDetails.getNucleoIspettivoSei() > -1) && (TicketDetails
					.getComponenteNucleoSei() != ""))
			|| ((TicketDetails.getNucleoIspettivoSette() > -1) && (TicketDetails
					.getComponenteNucleoSette() != ""))
			|| ((TicketDetails.getNucleoIspettivoOtto() > -1) && (TicketDetails
					.getComponenteNucleoOtto() != ""))
			|| ((TicketDetails.getNucleoIspettivoNove() > -1) && (TicketDetails
					.getComponenteNucleoNove() != ""))
			|| ((TicketDetails.getNucleoIspettivoDieci() > -1) && (TicketDetails
					.getComponenteNucleoDieci() != ""))) {
%>
<tr class="containerBody">
	<td name="" class="formLabel"><dhv:label name="">Nucleo Ispettivo</dhv:label>
	</td>
	<td>
	<%
		if ((TicketDetails.getNucleoIspettivo() > -1)
					&& (TicketDetails.getComponenteNucleo() != "")) {
	%> <b> <%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivo())%>:</b> <%=TicketDetails.getComponenteNucleo()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoDue() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoDue())%>:</b> <%=TicketDetails.getComponenteNucleoDue()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoTre() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoTre())%>:</b> <%=TicketDetails.getComponenteNucleoTre()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoQuattro() > -1) {
 %> <b><%=titoloNucleoTest
									.getSelectedValue(TicketDetails
											.getNucleoIspettivoQuattro())%>:</b> <%=TicketDetails.getComponenteNucleoQuattro()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoCinque() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoCinque())%>:</b> <%=TicketDetails.getComponenteNucleoCinque()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoSei() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoSei())%>:</b> <%=TicketDetails.getComponenteNucleoSei()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoSette() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoSette())%>:</b> <%=TicketDetails.getComponenteNucleoSette()%>
	<%
		}
	%> <%
 	if (TicketDetails.getNucleoIspettivoOtto() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoOtto())%>:</b> <%=TicketDetails.getComponenteNucleoOtto()%> <%
 	}
 %> <%
 	if (TicketDetails.getNucleoIspettivoNove() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoNove())%>:</b> <%=TicketDetails.getComponenteNucleoNove()%>  <%
 	}
 %> <%
 	if (TicketDetails.getNucleoIspettivoDieci() > -1) {
 %> <b><%=titoloNucleoTest.getSelectedValue(TicketDetails
									.getNucleoIspettivoDieci())%>:</b> <%=TicketDetails.getComponenteNucleoDieci()%> <%
 	}
 %>
	</td>
</tr>
<%
	}
%>

<%

if(TicketDetails.getSupervisionato_in_data()!=null)
{
%>

<tr class="containerBody">
		<td valign="top" class="formLabel">Controllo Risultato Efficace/congruo  ?</td>
		<td>
		
		<%=(TicketDetails.isSupervisione_flag_congruo()==true) ? "SI" : "NO : " + TicketDetails.getSupervisione_note()%> <br>
		Controllo Supervisionato in data <%=TicketDetails.getSupervisionato_in_data_string() %> da  
<dhv:username id="<%= TicketDetails.getSupervisionato_da() %>" /><br>
		</td>
	</tr>
	
	
<%
}
	//if (TicketDetails.getTipoCampione() == 5
		//	&& TicketDetails.isCategoriaisAggiornata() == false) {
%>
 