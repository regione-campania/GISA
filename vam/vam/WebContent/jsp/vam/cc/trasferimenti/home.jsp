<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>

           
    <h4 class="titolopagina">
     		Trasferimenti
    </h4>

	<div class="area-contenuti-2">

		<form name="trasferimenti" action="vam.cc.trasferimenti.Home.us" method="post">
			<jmesa:tableModel limit="${limitOut}" items="${clinica.trasferimentiUscitaOrderByStato }" id="trOut" var="tr">
				<jmesa:htmlTable styleClass="tabella" caption="Trasferimenti in Uscita">
					<jmesa:htmlRow>
						<jmesa:htmlColumn title="Azioni" sortable="false" filterable="false" >
							<a onclick="$( '#dettaglio_${tr.id }_div' ).dialog( 'open' );" style="cursor: pointer; text-decoration: underline; color: blue;" >Dettaglio Trasferimento</a>
								<br/><br/>
								<a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinica.id }" style="cursor: pointer; text-decoration: underline; color: blue;">
									Cc originale: ${tr.cartellaClinica.numero }
								</a>
							<c:if test="${tr.cartellaClinicaDestinatario!=null}">
								<br/><br/>
								<a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinicaDestinatario.id }" style="cursor: pointer; text-decoration: underline; color: blue;">
									Cc destinatario: ${tr.cartellaClinicaDestinatario.numero }
								</a>
							</c:if>
							<c:if test="${tr.cartellaClinicaMortoDestinatario!=null}">
								<br/><br/>
								<a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinicaMortoDestinatario.id }" style="cursor: pointer; text-decoration: underline; color: blue;">
									Cartella necroscopica destinatario: ${tr.cartellaClinicaMortoDestinatario.numero }
								</a>
							</c:if>
							<c:if test="${tr.cartellaClinicaMittenteRiconsegna!=null}">
								<br/><br/>
								<a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinicaMittenteRiconsegna.id }" style="cursor: pointer; text-decoration: underline; color: blue;">
									Cc post rientro animale: ${tr.cartellaClinicaMittenteRiconsegna.numero }
								</a>
							</c:if>
							<br/><br/>
							<%@ include file="detailInclude.jsp" %>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="stato" filterable="false" sortable="false"/>					
						<jmesa:htmlColumn property="cartellaClinica.identificativoAnimale" title="Animale" sortable="false"/>
						<jmesa:htmlColumn 
							property="dataRichiesta" 
							pattern="dd/MM/yyyy" 
							cellEditor="org.jmesa.view.editor.DateCellEditor" 
							title="Data Richiesta" 
							filterable="false" 
							sortable="false" />
						<jmesa:htmlColumn property="operazioniRichieste" title="Motivazioni/Operazioni Richieste" filterable="false" sortable="false" />
						<jmesa:htmlColumn property="clinicaDestinazione" title="Clinica di Destinazione" filterable="false" sortable="false">(${tr.clinicaDestinazione.asl }) ${tr.clinicaDestinazione.nome }</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:tableModel>
			
			<jmesa:tableModel limit="${limitIn}" items="${clinica.trasferimentiIngressoNoAttesaRifiutatoCriuvOrderByStato }" id="trIn" var="tr">
				<jmesa:htmlTable styleClass="tabella" caption="Trasferimenti in Ingresso">
					<jmesa:htmlRow>
					<jmesa:htmlColumn title="Azioni" sortable="false" filterable="false" >
						<a onclick="$( '#dettaglio_${tr.id }_div' ).dialog( 'open' );" style="cursor: pointer; text-decoration: underline; color: blue;" >Dettaglio Trasferimento</a>
						<c:if test="${tr.dataAccettazioneDestinatario == null }">
							<br/><br/><a href="vam.cc.trasferimenti.ToAccettazioneDestinatario.us?id=${tr.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Procedi con Accettazione</a>
						</c:if>
						<c:if test="${tr.dataAccettazioneDestinatario != null}">
							<br/><br/><a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinica.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Cc originale: ${tr.cartellaClinica.numero }</a>
							<br/><br/><a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinicaDestinatario.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Cc destinatario: ${tr.cartellaClinicaDestinatario.numero }</a>
							<c:if test="${tr.cartellaClinicaMortoDestinatario!=null}">
								<br/><br/><a href="vam.cc.Detail.us?idCartellaClinica=${tr.cartellaClinicaMortoDestinatario.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Cartella necroscopica destinatario: ${tr.cartellaClinicaMortoDestinatario.numero }</a>
							</c:if>
						</c:if>
						<br/><br/>
						<%@ include file="detailInclude.jsp" %>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="stato" filterable="false" sortable="false"/>
						<jmesa:htmlColumn property="cartellaClinica.identificativoAnimale" title="Animale" sortable="false">
							${tr.cartellaClinica.identificativoAnimale }
						</jmesa:htmlColumn>
						<jmesa:htmlColumn 
							property="dataRichiesta" 
							pattern="dd/MM/yyyy" 
							cellEditor="org.jmesa.view.editor.DateCellEditor" 
							title="Data Richiesta" 
							filterable="false" 
							sortable="false" />
						<jmesa:htmlColumn property="operazioniRichieste" title="Motivazioni/Operazioni Richieste" filterable="false" sortable="false" />
						<jmesa:htmlColumn property="clinicaOrigine" title="Clinica di Origine" filterable="false" sortable="false">(${tr.clinicaOrigine.asl }) ${tr.clinicaOrigine.nome }</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:tableModel>
		</form>
		
	</div>

	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		};
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.cc.trasferimenti.Home.us?' + parameterString;
		};
	</script>