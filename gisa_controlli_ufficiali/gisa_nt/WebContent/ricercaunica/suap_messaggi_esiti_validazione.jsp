<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.suap.base.CodiciRisultatoFrontEnd"%>
<%@page import="org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta"%>
<%@page import="java.util.HashMap"%>
<jsp:useBean id="EsitoValidazione" class="org.aspcfs.modules.suap.base.RisultatoValidazioneRichiesta" scope="request"/>

<%
HashMap<Integer,RisultatoValidazioneRichiesta> listaMessaggi = new HashMap<Integer,RisultatoValidazioneRichiesta>();

/*CODICI PER VALIDAZIONE NUOVO STABILIMENTO*/
RisultatoValidazioneRichiesta nuovaSciaInserita = new RisultatoValidazioneRichiesta();
nuovaSciaInserita.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA EFFETTUATA CON SUCCESSO");
nuovaSciaInserita.setColor("green");
listaMessaggi.put(1, nuovaSciaInserita);

RisultatoValidazioneRichiesta nuovaSciaInseritaImpresaSovrapponibile = new RisultatoValidazioneRichiesta();
nuovaSciaInseritaImpresaSovrapponibile.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA EFFETTUATA CON SUCCESSO");
nuovaSciaInseritaImpresaSovrapponibile.setColor("green");
listaMessaggi.put(2, nuovaSciaInseritaImpresaSovrapponibile);

RisultatoValidazioneRichiesta nuovaSciaInseritaImpresaEsistente = new RisultatoValidazioneRichiesta();
nuovaSciaInseritaImpresaEsistente.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA EFFETTUATA CON SUCCESSO");
nuovaSciaInseritaImpresaEsistente.setColor("green");
listaMessaggi.put(3, nuovaSciaInseritaImpresaEsistente);

RisultatoValidazioneRichiesta nuovaSciaNonInseritaStabEsistente = new RisultatoValidazioneRichiesta();
nuovaSciaNonInseritaStabEsistente.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA NON EFFETTUATA: STABILIMENTO GIA' PRESENTE NEL SISTEMA");
nuovaSciaNonInseritaStabEsistente.setColor("red");
listaMessaggi.put(4, nuovaSciaNonInseritaStabEsistente);

RisultatoValidazioneRichiesta lineaGiaAttiva = new RisultatoValidazioneRichiesta();
lineaGiaAttiva.setDescrizioneErrore("NON E' STATO POSSIBILE VALIDARE LA RICHIESTA POICHE' ESISTE GIA' UNA LINEA DI ATTIVITA' CON LE STESSE CARATTERISTICHE");
lineaGiaAttiva.setColor("red");
listaMessaggi.put(5, lineaGiaAttiva);

RisultatoValidazioneRichiesta nuovaSciaValiazioneParziale = new RisultatoValidazioneRichiesta();
nuovaSciaValiazioneParziale.setDescrizioneErrore("VALIDAZIONE DELLA LINEA EFFETTUATA CON SUCCESSO.");
nuovaSciaValiazioneParziale.setColor("green");
listaMessaggi.put(7, nuovaSciaValiazioneParziale);

/*CODICI PER VALIDAZIONE DI MODIFICA (AMPLIAMENTO-CESSAZIONE-VARIAZIONE)*/

RisultatoValidazioneRichiesta sceltaCandidatoOpu = new RisultatoValidazioneRichiesta();
sceltaCandidatoOpu.setDescrizioneErrore("ATTENZIONE! A QUESTA IMPRESA RISULTANO GIA' ASSOCIATI I SEGUENTI STABILIMENTI. SELEZIONARE LO STABILIMENTO CHE CORRISPONDE CON QUELLO PER CUI SI E' RICHIESTA L'OPERAZIONE");
sceltaCandidatoOpu.setColor("yellow");
listaMessaggi.put(8, sceltaCandidatoOpu);

RisultatoValidazioneRichiesta candidatoUnicoInVecchiaAnagrafica = new RisultatoValidazioneRichiesta();
candidatoUnicoInVecchiaAnagrafica.setDescrizioneErrore("ATTENZIONE: LO STABILIMENTO NON "+
		"RISULTA ATTUALMENTE NELLA NUOVA ANAGRAFICA.<BR> TRATTASI DI "+
		"UNO STABILIMENTO PRESENTE NELLA VECCHIA ANAGRAFICA PER IL QUALE E'"+
		"RICHIESTO L'IMPORT NELLA NUOVA ANAGRAFICA, <BR> PRIMA DI "+
		"PROCEDERE ALLA VALIDAZIONE DELLA RICHIESTA <BR>"+
		"SI PREGA DI CONTATTARE L'HELP DESK PER PROCEDERE CON LA MODIFICA.");
candidatoUnicoInVecchiaAnagrafica.setColor("yellow");
listaMessaggi.put(9, candidatoUnicoInVecchiaAnagrafica);

RisultatoValidazioneRichiesta candidatoMultiploInVecchiaAnagrafica = new RisultatoValidazioneRichiesta();
candidatoMultiploInVecchiaAnagrafica.setDescrizioneErrore("ATTENZIONE: ESISTONO PIU' STABILIMENTI CANDIDATI ALLA MODIFICA RICHIESTA. "+
		"SI PREGA DI CONTATTARE L'HELP DESK PER PROCEDERE ALLA MODIFICA"+
		" DELL'IMPORT SPECIFICANDO LO STABILIMENTO DA MODIFICARE ");
candidatoMultiploInVecchiaAnagrafica.setColor("yellow");
listaMessaggi.put(10, candidatoMultiploInVecchiaAnagrafica);


RisultatoValidazioneRichiesta candidatoNonEsistente = new RisultatoValidazioneRichiesta();
candidatoNonEsistente.setDescrizioneErrore("ATTENZIONE: LO STABILIMENTO NON RISULTA ATTUALMENTE NELLA NUOVA ANAGRAFICA.<BR>"+
		"<BR>"+
		"PRIMA DI PROCEDERE ALLA VALIDAZIONE DELLA RICHIESTA<BR>"+
		"VERIFICARE SE: <BR>"+
		"<B>A)</B> TRATTASI DI STABILIMENTO PREGRESSO PER IL QUALE E' RICHIESTO L'INSERIMENTO IN ANAGRAFICA PRIMA DI PROCEDERE ALLA VALIDAZIONE DELLA RICHIESTA, TRAMITE <input   type=\"submit\" value=\"MASCHERA\" onClick = 'ritornaAPaginaPerImport();'//> PER L'AGGIUNTA DELLO STABILIMENTO PREGRESSO<BR>"+
		"<B>B)</B> TRATTASI DI RICHIESTA CON DATI ERRATI CHE DEVE ESSERE RESPINTA PERCHE' NON VALIDABILE<BR>"+
		"<B>C)</B> TRATTASI DI UNO STABILIMENTO GIA' CESSATO");
candidatoNonEsistente.setColor("yellow");
listaMessaggi.put(11, candidatoNonEsistente);



RisultatoValidazioneRichiesta lineaNonEsistente = new RisultatoValidazioneRichiesta();
lineaNonEsistente.setDescrizioneErrore("NON E' STATO POSSIBILE VALIDARE LA RICHIESTA. LA LINEA DI ATTIVITA NON RISULTA ASSOCIATA ALLO STABILIMENTO IN ANAGRAFICA OPPURE E' INATTIVA");
lineaNonEsistente.setColor("yellow");
listaMessaggi.put(404, lineaNonEsistente);


/*CODICI GENERICI*/
RisultatoValidazioneRichiesta erroreGenerico = new RisultatoValidazioneRichiesta();
erroreGenerico.setDescrizioneErrore("ERRORE INASPETTATO.");
erroreGenerico.setColor("red");
listaMessaggi.put(-1, erroreGenerico);


RisultatoValidazioneRichiesta tipoSciaNonPrevista = new RisultatoValidazioneRichiesta();
tipoSciaNonPrevista.setDescrizioneErrore("OPERAZIONE NON ANCORA IMPLEMENTATA");
tipoSciaNonPrevista.setColor("red");
listaMessaggi.put(100, tipoSciaNonPrevista);

RisultatoValidazioneRichiesta richiestaEvasa = new RisultatoValidazioneRichiesta();
richiestaEvasa.setDescrizioneErrore("OPERAZIONE NON CONSENTITA: VALIDAZIONE DELLA RICHIESTA GIA' EFFETTUATA");
richiestaEvasa.setColor("red");
listaMessaggi.put(99, richiestaEvasa);


RisultatoValidazioneRichiesta sciaValidazioneOkServerDocOffline = new RisultatoValidazioneRichiesta();
sciaValidazioneOkServerDocOffline.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA EFFETTUATA CON SUCCESSO  MA I DOCUMENTI NON è STATO POSSIBILE IMPORTARE LA DOCUMENTAZIONE IN QUANTO IL DOCUMENTALE è OFFLINE");
sciaValidazioneOkServerDocOffline.setColor("green");
listaMessaggi.put(12, sciaValidazioneOkServerDocOffline);

RisultatoValidazioneRichiesta sciaValidazioneVariazioneKoLineeNonPresenti = new RisultatoValidazioneRichiesta();
sciaValidazioneVariazioneKoLineeNonPresenti.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA NON ESEGUITA. LE LINEE DI ATTIVITA DELLA RICHIESTA NON CORRISPONDONO CON QUELLE DELLO STABILIMENTO.");
sciaValidazioneVariazioneKoLineeNonPresenti.setColor("red");
listaMessaggi.put(13, sciaValidazioneVariazioneKoLineeNonPresenti);

RisultatoValidazioneRichiesta sciaValidazioneCessazioneKOAnimaliPresenti = new RisultatoValidazioneRichiesta();
sciaValidazioneCessazioneKOAnimaliPresenti.setDescrizioneErrore("VALIDAZIONE DELLA RICHIESTA NON ESEGUITA. LA RICHIESTA DI CESSAZIONE NON E' POSSIBILE IN QUANTO RISULTANO CANI/GATTI ASSOCIATI ALLO STABILIMENTO.");
sciaValidazioneCessazioneKOAnimaliPresenti.setColor("red");
listaMessaggi.put(14, sciaValidazioneCessazioneKOAnimaliPresenti);


%>
	
<!-- STAMPA DELL'ESITO DELLA VALIDAZIONE RECUPERATO DALLA HASHMAP -->
<%if(EsitoValidazione.getIdRisultato()>0 ||EsitoValidazione.getIdRisultato()==-1 ){ %>


 
 <table style="width: 100%">	
<tr>
<td colspan="3" id ="tdMsg" style="background-color: <%=listaMessaggi.get(EsitoValidazione.getIdRisultato()).getColor() %>;" >
<%=listaMessaggi.get(EsitoValidazione.getIdRisultato()).getDescrizioneErrore() %>
</td>
</tr>
<%
if(EsitoValidazione.getIdRisultato()==CodiciRisultatoFrontEnd.SCIA_VALIDAZIONE_KO_LINEA_NON_PRESENTE)
{
%>
<tr><td>
<form method="post" action="InterfValidazioneRichieste.do?command=Archivia&stabId=<%=request.getAttribute("idRichiesta")%>">
					
					<textarea rows="8" cols="30" name="noteValidazione" placeholder="INSERIRE QUI LA MOTIVAZIONE"></textarea>
					<input type="hidden" name = "statoRichiesta" value = "<%=Stabilimento.STATO_RICHIESTA_NON_VALIDABILE%>">
					<input type="submit" value = "Archivia">
					
					</form>
		</td></tr>
		<%} %>			
</table>		
<jsp:include page="suap_messaggi_esiti_validazione_scelta_candidato.jsp" />

<%} %>







