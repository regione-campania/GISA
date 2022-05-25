<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttivaList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">

 <link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
 
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/jquery-ui.js"></script>

<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>

<script src="javascript/aggiuntaCampiEstesiScia.js"></script>
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">

<script>

function verificaSceltaNuovaLinea()
{
	if(document.getElementById("validatelp").value=="true")
		{

		
		
		
		
// 		loadModalWindowCustom("Modifica In Corso. Attendere Prego.");
		return true;
		}
	else
		alert("-Controllare di Aver Selezionato il percorso Completo della nuova linea");
	return false;
	}
$(document).ready(function () {

    $('#ecsf1').validate({ // initialize the plugin
        rules: {
            'idRelStabLp': {
                required: true
                
            }
        },
        messages: {
            'idRelStabLp': {
                required: "Seleziona Almeno Una Linea di Attivita Da Variare"            }
        }
    });

});

var fieldCheck ;
var idLinea ;
function verificaEsistenzaControlli(idRelStabLp,field)
{
	if(field.checked==true)
		{
	fieldCheck=field;
	idLinea = idRelStabLp;
	PopolaCombo.getNumeroControlliSuLinea(idRelStabLp,verificaEsistenzaControlliCallback);
		}
	else
		{
		abilitaLineeNuove(idRelStabLp,fieldCheck);
		}
				
	
	
}

function verificaEsistenzaControlliCallback(valRet)
{
	if(valRet>0)
		{
		if(confirm("Attenzione sulla linea che si intende variare sono presenti "+valRet+" controlli ufficiali.\nIntendi Continuare?")==true)
			{
			abilitaLineeNuove(idLinea,fieldCheck);
			}
		else
			{
			$("#attprincipale"+idLinea).html("");
			fieldCheck.checked=false;
			}

		}
	else
		{
		abilitaLineeNuove(idLinea,fieldCheck);
		}
	}



function annullaLineeProduttive(idTab,idRelStabLp)
{
	
	mostraAttivitaProduttiveErrataCorrige(idTab,1, -1, false,-1,idRelStabLp);
	}
function abilitaLineeNuove(idRiga,field)
{
	
	if(field.checked)
		{
		mostraAttivitaProduttiveErrataCorrige('attprincipale'+idRiga,1,-1, false,-1,idRiga);
		}
	else
		{
		$("#attprincipale"+idRiga).html("");
		}
}
	


</script>

<%@ include file="../initPage.jsp"%>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
<a href="OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>">Scheda Stabilimento</a> >
Errata Corrige Dati Impresa
</td>
</tr>
</table>



<%
String nomeContainer = StabilimentoDettaglio.getContainer();

String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"altId="+StabilimentoDettaglio.getAltId();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());

%>

<br>


<dhv:container name="suap"  selected="details" object="Operatore" param="<%=param%>">

<form id = "ecsf1" name = "ecsf" method="post" action="Gec.do?command=ErrataCorrigeDatiStabilimento" onsubmit="return verificaSceltaNuovaLinea()">
<input type = "hidden" name = "idStabilimento" value = "<%=StabilimentoDettaglio.getIdStabilimento() %>">
<input type = "hidden" name = "tipo_impresa" id = "tipo_impresa" value = "<%=StabilimentoDettaglio.getOperatore().getTipo_impresa() %>">


<fieldset>
		<legend><b>DATI IMPRESA</b></legend>
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		
		<tr>
			<td class="formLabel" nowrap>
						<p id="labelRagSoc">
							DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
						</p>
					</td>
					<td>
					
					<%=toHtml2(StabilimentoDettaglio.getOperatore().getRagioneSociale()) %>
						
						</td>
				</tr>
				<tr>
			<td class="formLabel" nowrap>PARTITA IVA</td>
					<td><%=toHtml2( StabilimentoDettaglio.getOperatore().getPartitaIva()) %></td>
				</tr>
				<tr id="codFiscaleTR">
				
			<td class="formLabel" nowrap>CODICE FISCALE<br>IMPRESA
					</td>
					<td><%=toHtml2(StabilimentoDettaglio.getOperatore().getCodFiscale()) %> 
					 </td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>TIPO IMPRESA</td>
					<td>
					<%=TipoImpresaList.getSelectedValue(StabilimentoDettaglio.getOperatore().getTipo_impresa()) %>
		
						</td>
				</tr>
				<tr id="tipo_societaTR">
					<td class="formLabel" nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
					<td>
					<%=TipoSocietaList.getSelectedValue(StabilimentoDettaglio.getOperatore().getTipo_societa()) %>
						</td>
				</tr>
				
				</table>
				</fieldset>
				
				<%if(StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getIdIndirizzo()>0){ %>
				<fieldset id = "setSedeLegale">
		<legend><b>DATI SEDE LEGALE</b></legend>
				
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				
				
				<tr id="searchcodeIdprovinciaTR">
					<td class="formLabel" nowrap>PROVINCIA</td>
					<td><%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getDescrizione_provincia() %></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>COMUNE</td>
					<td><%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getDescrizioneComune() %></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>INDIRIZZO</td>
					<td>
					<%=ToponimiList.getSelectedValue(StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getToponimo())+ " "+StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getVia()+" "+StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getCivico() %>
					<%=StabilimentoDettaglio.getOperatore().getSedeLegaleImpresa().getCap() %>	
						</td>
						</tr>	
						</table>
						
			</fieldset>
			<%} %>	
			
			<%if(StabilimentoDettaglio.getTipoAttivita()==StabilimentoDettaglio.ATTIVITA_FISSA){ %>
			<fieldset id = "setSedeLegale">
		<legend><b>DATI SEDE PRODUTTIVA</b></legend>
				
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				
				
				<tr id="searchcodeIdprovinciaTR">
					<td class="formLabel" nowrap>PROVINCIA</td>
					<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizione_provincia() %></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>COMUNE</td>
					<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneComune() %></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>INDIRIZZO</td>
					<td>
					<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneToponimo() + " "+StabilimentoDettaglio.getSedeOperativa().getVia()+" "+StabilimentoDettaglio.getSedeOperativa().getCivico() %>
					<%=StabilimentoDettaglio.getSedeOperativa().getCap() %>	
						</td>
						</tr>	
						</table>
						
			</fieldset>
			<%} %>
			
			<fieldset id = "setSedeLegale">
		<legend><b>VARIAZIONE ATTIVITA SVOLTE (Le modificabili sono solo quelle attive)</b></legend>
				
				<table cellpadding="8" cellspacing="0" border="0" style="width: 100%" class="pagedList">
				<tr>
				<th width="40%">Linea Attuale</th>
				<th width="5%%">Data Inizio Attivita</th>
				<th width="5%">Data Fine Attivita</th>
				<th width="5%">Seleziona</th>
				<th width="35%">Nuova Attivita</th>
				</tr>
				<input type="hidden" id = "validatelp" value = "false">
				<%
				LineaProduttivaList listaLinee = StabilimentoDettaglio.getListaLineeProduttive();
				System.out.println("SIZE LINEE ATTIVITA : "+listaLinee.size());
				for (int i = 0 ; i<listaLinee.size(); i ++)
				{
					LineaProduttiva lp = (LineaProduttiva)listaLinee.get(i);
					if(lp.getStato()==0){
					%>
					<tr>
				<td width="40%"><%=lp.getDescrizione_linea_attivita() %></td>
				<td width="5%"><%=toDateasString(lp.getDataInizio()) %></td>
				<td width="5%"><%=toDateasString(lp.getDataFine()) %></td>
				<td width="5%"><input type="checkbox" name ="idRelStabLp" onclick="verificaEsistenzaControlli(<%=lp.getId_rel_stab_lp()%>,this)" value = "<%=lp.getId_rel_stab_lp() %>"></td>
				<td width="35%"><div id="attprincipale<%=lp.getId_rel_stab_lp()%>"></div></td>
				</tr>
					<%
				}}
				%>
				
						</table>
						
			</fieldset>
			
				<input type = "hidden" name = "tipoAttivita" id="tipoAttivita" value="<%=StabilimentoDettaglio.getTipoAttivita() %>">
			<input type = "submit" value="Continua">
			
			<input type = "button" value="Annulla" onclick="location.href='OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>'">
			
			</form>
			
</dhv:container>
			
<div id="modalListaStabilimenti">
</div>			
		