<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.taglib.PermissionHandler"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIndicatoreNewBeanAbstract"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBeanInterface"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatSezioneNewBeanInterface"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniNewBeanAbstract"%>
<%@page import="org.aspcfs.modules.dpat2019.action.DpatSDC"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIndicatore"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatAttivita"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatPiano"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatSezione"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIstanza"%>
<%@page import="org.aspcfs.modules.dpat2019.base.PianoMonitoraggio"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@page import="java.net.URLEncoder" %>
<%@page import="org.aspcfs.modules.dpat2019.base.Dpat"%>


<jsp:useBean id="TicListPiani"
	class="org.aspcfs.modules.programmazzionecu.base.PianoMonitoraggioList"
	scope="request" />
<jsp:useBean id="lookup_dpat_tipo_item" class="org.aspcfs.utils.web.LookupList" scope="request"></jsp:useBean>
<jsp:useBean id="lookup_asl" class="org.aspcfs.utils.web.LookupList"
	scope="request"></jsp:useBean>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="lookup_sezioni_piani"
	class="org.aspcfs.utils.web.LookupList" scope="request"></jsp:useBean>
<jsp:useBean id="codiciInterniReadonlyIndicatori"
	class="java.util.ArrayList" scope="request"></jsp:useBean>	
	<jsp:useBean id="codiciInterniReadonlyPiani"
	class="java.util.ArrayList" scope="request"></jsp:useBean>	
	

<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<%@ include file="troubletickets_searchresults_menu.jsp"%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></SCRIPT>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js">
	
</script>

<script type="text/javascript" src="dwr/engine.js">
	
</script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript" type="text/javascript">
	
<%-- Preload image rollovers for drop-down menu --%>
function scrollToElement() {
	var ele = document.getElementById("scroll");
	if(ele!=null)
   	 $(window).scrollTop(ele.offset().top).scrollLeft(ele.offset().left);
}
</script>


<%

	/*posso essere qui con un modello congelato o precongelato */
	DpatWrapperSezioniNewBeanAbstract newDpatSezioniWrapper =   (DpatWrapperSezioniNewBeanAbstract)request.getAttribute("dpatNew");
	int anno = newDpatSezioniWrapper.getAnno();
	boolean congelato = request.getAttribute("congelato") != null && Boolean.parseBoolean((String)request.getAttribute("congelato")) == true;
	/*a seconda che sia settato o meno l'attributo nomeTemplate questo sara' un tipo diverso */
	//String nomeTemplateAsParameter = !isCongelato ? ("&nomeTemplate="+ URLEncoder.encode( (String) (request.getAttribute("nomeTemplate")),"UTF-8") ) : "" ;
	
%>

<script>

 
 

function apriDettaglioAttivita(codRaggruppamento,anno)
{
	
	
	var congelato = '<%=congelato%>';
	//if(congelato.toLowerCase() != 'true')
	if(false)
	{
		alert("Per un DPAT non ancora congelato non è mantenuta una lista delle versioni.");
		return;
	}
	
	window
	.open(
			'dpat2019.do?command=ListaAttivitaCodiceInterno_NewDpat&anno='+anno+'&codRaggruppamento='+codRaggruppamento+<%="'&congelato="+congelato+"'" %>,
			null,
			'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');

	
	
}


function apriDettaglioIndicatore(codRaggruppamento,anno)
{
	
	var congelato = '<%=congelato%>';
	if(false)
	//if(congelato.toLowerCase() != 'true')
	{
		alert("Per un DPAT non ancora congelato non è mantenuta una lista delle versioni.");
		return;
	}
	
	window
	.open(
			'dpat2019.do?command=ListaIndicatoriCodiceInterno_NewDpat&anno='+anno+'&codRaggruppamento='+codRaggruppamento+<%="'&congelato="+congelato+"'" %>,
			null,
			'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');

	
	
}

	var val;

	 
	
	/*per inserimento*/
	
	function apriPopupNuovaEntry(tipoInserimento, idPianoRiferimento, /*equivalente vecchio metodo openNotePianoMonitoraggioAdd*/
			nuovasezione, tipo_piano_attivita_ind,anno) {
		
		console.log("CHIAMATA apriPopupNuovaEntry "+"\nTipoInserimento "+tipoInserimento+"\nIdPianoRiferimento "+idPianoRiferimento+
				"\nNuovaSezione "+nuovasezione+"\nTipoPianoAttivita"+tipo_piano_attivita_ind);
		 
		
		var res;
		var result;
		// tipoInserimento : up , down ,firstchild
		window
				.open(
						'dpat2019.do?command=NuovaEntry&tipoPianoAtt='
								+ tipo_piano_attivita_ind + '&nuovasezione='
								+ nuovasezione + '&tipoInserimento='
								+ tipoInserimento + '&idPianoRiferimento='
								+ idPianoRiferimento+'&anno='+anno+<%="'&congelato="+congelato+"'" %>, 
						null,
						'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');
	}
	
	
	
	 
	
	/*per la cessazione*/
	
	function disabilitaEntry(dataScadenza, idPianoRiferimento, /*equivalente del vecchio metodo openNotePianoMonitoraggioReplace*/
			tipo_piano_attivita_ind, cessato,anno) { 
		
		console.log("CHIAMATA disabilitaEntry "+"\dataScadenza "+dataScadenza+"\idPianoRiferimento "+idPianoRiferimento+
				"\ntipo_piano_attivita_ind "+tipo_piano_attivita_ind+"\ncessato"+cessato);
		 
		
		
		var res;
		var result;
		 

		if (dataScadenza == '' || dataScadenza == 'null'
				|| dataScadenza == null) {
			window
					.open(
							'dpat2019.do?command=DisabilitaEntry&cessato=' + cessato+'&anno='+anno
									+ '&tipoPianoAtt='
									+ tipo_piano_attivita_ind
									+ '&idPianoRiferimento='
									+ idPianoRiferimento
									+<%="'&congelato="+congelato+"'" %>,
							null,
							'height=700px,width=900px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		} else {
			if (confirm('ATTENZIONE SU QUESTO RECORD CI SONO MODIFICHE NON ATTUALIZZATE . PROCEDERE COMUNQUE CON LA MODIFICA ?')) {

				window
						.open(
								'dpat2019.do?command=DisabilitaEntry&cessato=' + cessato+'&anno='+anno
										+ '&tipoPianoAtt='
										+ tipo_piano_attivita_ind
										+ '&idPianoRiferimento='
										+ idPianoRiferimento
										+<%="'&congelato="+congelato+"'" %>,
								null,
								'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');
			}

		}
	}
	
	 
	
	
	function openNotePianoMonitoraggioReplaceIndicatore_NewDpat(dataScadenza, idPianoRiferimento,
			tipo_piano_attivita_ind, cessato,anno) {

	
			
	
			console.log("CHIAMATA openNotePianoMonitoraggioReplaceIndicatore_NewDpat "+"\dataScadenza "+dataScadenza+"\idPianoRiferimento "+idPianoRiferimento+
                    "\ntipo_piano_attivita_ind "+tipo_piano_attivita_ind+"\ncessato"+cessato);
             
            
            var res;
            var result;
             
    
            if (dataScadenza == '' || dataScadenza == 'null'
                    || dataScadenza == null) {
                window
                        .open(
                                'dpat2019.do?command=UpdateEntryIndicatore&cessato=' + cessato+'&anno='+anno
                                        + '&tipoPianoAtt='
                                        + tipo_piano_attivita_ind
                                        + '&idPianoRiferimento='
                                        + idPianoRiferimento
                                        +<%="'&congelato="+congelato+"'" %>,
                                null,
                                'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');
            } else {
                if (confirm('ATTENZIONE SU QUESTO RECORD CI SONO MODIFICHE NON ATTUALIZZATE . PROCEDERE COMUNQUE CON LA MODIFICA ?')) {
    
                    window
                            .open(
                                    'dpat2019.do?command=ToMoveIndicatore&cessato=' + cessato+'&anno='+anno
                                            + '&tipoPianoAtt='
                                            + tipo_piano_attivita_ind
                                            + '&idPianoRiferimento='
                                            + idPianoRiferimento
                                            +<%="'&congelato="+congelato+"'" %>,
                                    null,
                                    'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');
                }
    
            }

			 
			}			
				
	
	
 
	
	function openNotePianoMonitoraggioReplaceAttivita_NewDpat(dataScadenza, idPianoRiferimento,	tipo_piano_attivita_ind, cessato,anno,descrIndicatore) {
		var res;
		var result;
		
		
		console.log("CHIAMATA openNotePianoMonitoraggioReplaceAttivita_NewDpat "+"\dataScadenza "+dataScadenza+"\idPianoRiferimento "+idPianoRiferimento+
				"\ntipo_piano_attivita_ind "+tipo_piano_attivita_ind+"\ncessato"+cessato);
		 

		if (descrIndicatore.toUpperCase() === 'INDICATORE DI DEFAULT DA SOSTITUIRE')
        {
           alert('Attenzione operazione non consentita fintanto che non si modifica l\'indicatore di default');
        }
    else{
				if (dataScadenza == '' || dataScadenza == 'null'
						|| dataScadenza == null) {
					window
							.open(
									'dpat2019.do?command=UpdateEntryAttivita&cessato=' + cessato+'&anno='+anno
								 
											+ '&tipoPianoAtt='
											+ tipo_piano_attivita_ind
											+ '&idPianoRiferimento='
											+ idPianoRiferimento
											+<%="'&congelato="+congelato+"'" %>,
									null,
									'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');
				} else {
					if (confirm('ATTENZIONE SU QUESTO RECORD CI SONO MODIFICHE NON ATTUALIZZATE . PROCEDERE COMUNQUE CON LA MODIFICA ?')) {
		
						window
								.open(
										'dpat2019.do?command=UpdateEntryAttivita&cessato=' + cessato+'&anno='+anno
										
												+ '&tipoPianoAtt='
												+ tipo_piano_attivita_ind
												+ '&idPianoRiferimento='
												+ idPianoRiferimento
												+<%="'&congelato="+congelato+"'" %>,
										null,
										'scrollbar=yes,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,modal=yes');
					}
		
				}
		 }
	}
	
	
	
	

	var colorprec = '';
	function hover(item) {

		$('#elenco tr').hover(function() {
			colorprec = $(this).css('background-color');
			$(this).css('background', '#dedede');
		}, function() {
			$(this).css('background', colorprec);
		});
	}

	function ricaricaPiani() { /*chi lo usa ? */

		if (document.getElementById("checkAtt").checked) {
			location.href = "dpat2019.do?command=SearchPianiMonitoraggioNewDpat&includiAttivita=si&anno=<%=anno+"&congelato="+congelato%>";
		} else {
			location.href = "dpat2019.do?command=SearchPianiMonitoraggioNewDpat&includiAttivita=no&anno=<%=anno+"&congelato="+congelato%>";
		}
	}
	
	function handlerCongelaConfigurazione(anno)
	{ 		
		var msg = "Attenzione, tramite il congelamento verrà sovrascritto il modello precedentemente congelato per l'anno <%=anno %>";
		
		if(confirm(msg) == true)
		{
			var urlDest = 'dpat2019.do?command=CongelaConfigurazioneDpatNEW&anno='+anno+'<%="&congelato="+congelato%>'
			document.location.href = urlDest;
			
		}
		return;
	
	}
	
	
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>Elenco SEZIONI-PIANI-ATTIVITA-INDICATORI</td>
	</tr>
</table>

<%
	int tipo_piano = -1;
	String descrizione_piano = "";
	if (request.getAttribute("searchcodetipo_piano") != null)
		tipo_piano = (Integer) request.getAttribute("searchcodetipo_piano");
	if (request.getAttribute("searchdescrizione_piano") != null)
		descrizione_piano = (String) request.getAttribute("searchdescrizione_piano");
%>




<div align="right">
 

 </div>
 <br>
 <div align="right">
 
	<span>
	<span  >
	<font style="color:red;">
		<b>
		<%=congelato ? "LE MODIFICHE EFFETTUATE A QUESTO MODELLO (ANNO "+anno+") SARANNO IMMEDIATAMENTE PROPAGATE (E VISIBILI SE IL MODELLO E' QUELLO IN USO)" : "<i>CONFIGURAZIONE DPAT PRE-CONGELAMENTO </i>: "+anno%>
		</b>
	</font>
	</span>
	<%
		 if(!congelato) /*solo se il piano non e' congelato attivo bottone CONGELA CONFIGURAZIONE*/
		 {%>
		
			 <input type="button" value="Congela Configurazione"
				onClick="handlerCongelaConfigurazione(<%= anno %>);"
				style="background-color: #FF4D00; font-weight: bold;" />
		 <%}
	      else {%>
	   	<input type="button" value="Congela Configurazione" disabled style="background-color: grey; font-weight: bold;" title="Il piano risulta già congelato"/>
	      	
	      <%} %>
 	</span>
</div>
  


 




<%=(request.getAttribute("ErrorDpat") != null) ? "<font color='red'>"
					+ request.getAttribute("ErrorDpat") + "</font>" : ""%>




<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details"
	id="elenco">
	<tr>
		<th>Codice</th>
		<th>Alias</th>
		<th>Tipo Attivita</th>
		<th>Descrizione</th>
		<th>Codice Esame</th>
		<th>Tipo Item</th>
		<th>Rendicontazione<br>per Campione</th>
<!-- 		<th>CodiceEsame</th> -->
		<th>Azioni</th>
<!-- 		<th>&nbsp;</th> -->
	</tr>
	<%
		
		Iterator<DpatSezioneNewBeanInterface> itSezioni =   newDpatSezioniWrapper.getSezioni().iterator();
		
		Iterator<DpatPianoAttivitaNewBeanInterface> itPiani = null;
		
		if (itSezioni.hasNext()) {
			while (itSezioni.hasNext()) {
				 
				
				DpatSezioneNewBeanInterface sezione = itSezioni.next();
				Timestamp scadenza = sezione.getScadenza();
				String descrizione = sezione.getDescrizione();
				if(scadenza != null) /*salto le scadute*/
					continue;
				
				String color = congelato ? sezione.getColor() : sezione.getColor()+"; opacity: 0.6; font-style:italic; ";
				if(color == null || color.trim().equals("") )
				{
					color = "#FFF";
				}
				
				
				itPiani = sezione.getPianiAttivitaFigli().iterator();
	%>

	<tr>
		<td colspan="8" style="background-color: <%=color%>" valign="center"><center><%=descrizione.toUpperCase()%></center></td>
	</tr>


	<%
	
	PermissionHandler p1 = new PermissionHandler();
	p1.setName("dpat-gestionepianiparticolari-view");
	p1.setPageContext(pageContext);
	
	 String descrIndicatore = "";
		while (itPiani.hasNext()) {
					//DpatPiano piano = itPiani.next(); NEWDPAT
					 
					DpatPianoAttivitaNewBeanInterface piano = itPiani.next();
					Timestamp scadenzaPiano = piano.getScadenza();
					if(scadenzaPiano != null)
						continue;
	%>
	<%
				  
				   boolean hasOneValid = false;
				   /*controllo che il piano attivita abbia almeno un indicatore e che non sia scaduto */
				   ArrayList<DpatIndicatoreNewBeanAbstract> beansFigli = piano.getIndicatoriFigli();
				  // System.out.println("piano attivita------"+piano.getOid());
				   if(beansFigli != null)
				   {
					   for(int p = 0; p< beansFigli.size() && !hasOneValid; p++ )
					   {
						   DpatIndicatoreNewBeanAbstract beanFiglio = beansFigli.get(p);
						   Timestamp dataScadenzaFiglio = beanFiglio.getScadenza();
						   descrIndicatore = beanFiglio.getDescrizione().replaceAll("'", "");
						   hasOneValid = dataScadenzaFiglio == null;
						   
						  // System.out.println(beanFiglio.getDescrizione()+","+beanFiglio.getOid()+" "+(beanFiglio.getScadenza() != null));
						  
					   }
				   }
				   long oidPiano = piano.getOid();
				   int annoPiano = piano.getAnno();
				   String descrPiano = piano.getDescrizione();
				   String codiceAliasAttivita = piano.getCodiceAliasAttivita();
				   String tipoAttivita = piano.getTipoAttivita();
				   String aliasPiano = piano.getAliasPiano();
				   String codiceEsame = piano.getCodiceEsame();
				   String codiceAslPiano = piano.getCodiceAsl();
				   Integer codiceRaggruppamentoPiano = piano.getCodiceRaggruppamento();
	%>
	<tr>
	 
	 
	 <td style="background-color: <%=color%>"><a href=  "#" onclick = "apriDettaglioAttivita(<%=codiceRaggruppamentoPiano%>,<%=annoPiano %>)" ><%=toHtml2(codiceAliasAttivita+"").toUpperCase()%>&nbsp;</a></td>
		<td style="background-color: <%=color%>"><%=toHtml2(aliasPiano).toUpperCase()%>&nbsp;</td>
		<td style="background-color: <%=color%>"><%=toHtml2(tipoAttivita).toUpperCase()%>&nbsp;</td>
		<td style="background-color: <%=color%>"><%=descrPiano %></td>		
		<td style="background-color: <%=color%>">&nbsp;<%=toHtml2(codiceEsame)%>&nbsp;
		</td>
		<td style="background-color: <%=color%>">&nbsp;&nbsp;
		</td>
		<td style="background-color: <%=color%>">&nbsp;&nbsp;
		</td>
<%-- 		<td style="background-color: <%=color%>">&nbsp;<%=toHtml2(lookup_asl.getSelectedValue(codiceAslPiano))%>&nbsp; --%>
<!-- 		</td> -->
		<td>
 	 
 			<%
				if (!hasOneValid) { /*cioè il piano attività non ha neanche un figlio non scaduto */
					
					
					
			%>
			 
			<table class="noborder">
				<tr>

					<td><a
						href="javascript:apriPopupNuovaEntry('firstchild',<%=oidPiano%>,'no','dpat_indicatore',<%=anno%>)"><img
							title="Aggiungi Sottopiano a <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/add.png"></a></td>
					<td><a
						href="javascript:apriPopupNuovaEntry('up',<%=oidPiano%>,'no','dpat_attivivita',<%=anno%>)"><img
							title="Aggiungi Piano Fratello Sopra <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/add_up_padre.png"></a></td>
					<td><a
						href="javascript:apriPopupNuovaEntry('down',<%=oidPiano%>,'no','dpat_attivivita',<%=anno%>)"><img
							title="Aggiungi Piano Fratello Sotto <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/add_down_padre.png"></a></td>

					<td><img style=" visibility : hidden; " width="25px" height="25px"
						src="./cu_programmazioni_2019/image/addup.png"></td>
					<td><img style=" visibility : hidden; " width="25px" height="25px"
						src="./cu_programmazioni_2019/image/adddown.png"></td>
						<td>
						<a
<%  
 			   if(((piano.getCodiceInternoPiano()!=null && codiciInterniReadonlyPiani.contains(piano.getCodiceInternoPiano().toString())) || (piano.getCodiceInternoAttivita()!=null && codiciInterniReadonlyPiani.contains(piano.getCodiceInternoAttivita().toString())))  && p1.doStartTag()!=1 )
 			   {
%>
					href="javascript:alert('Modifica non consentita. Rivolgersi all Help Desk di II livello.')">
 <%
 			   }
 			   else
 			   {
%> 				   
					href="javascript:openNotePianoMonitoraggioReplaceAttivita_NewDpat('<%=(scadenzaPiano+"")  .toString()%>',<%=oidPiano%>,'dpat_attivita','no',<%=anno%>,'<%=((descrIndicatore==null)?(null):((descrIndicatore+"").toUpperCase()))%>')"	>
<%
 			   }
%>
					<img
							title="ESEGUI VARIAZIONE SUL PIANO/ATTIVITA (LE POSSIBILI VARIAZIONI SONO DEL TIPO MODIFICA O SPOSTAMENTO)"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/edit.jpg"></a></td>
				</td>
					<td><a
						href="javascript:disabilitaEntry('<%=scadenzaPiano%>',<%=oidPiano%>,'dpat_attivivita','si',<%=anno%>)"><img
							title="Rendi Obsoleto <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/remove.png"></a>
				</tr>
			</table> 
		 
			<%
 	}
 	else {
 %>
			 
			 
			<table class="noborder">
				<tr>
					<td><img
						title="Aggiungi Sottopiano a <%=descrPiano%>"
						width="25px" height="25px" src="./cu_programmazioni_2019/image/add.png"
						style="opacity: 0.2"></td>
					<td><a
						href="javascript:apriPopupNuovaEntry('up',<%=oidPiano%>,'no','dpat_attivivita',<%=anno%>)"><img
							title="Aggiungi Piano Fratello Sopra <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/add_up_padre.png"></a></td>
					<td><a
						href="javascript:apriPopupNuovaEntry('down',<%=oidPiano%>,'no','dpat_attivivita',<%=anno%>)"><img
							title="Aggiungi Piano Fratello Giu <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/add_down_padre.png"></a></td>
					<td><img style=" visibility : hidden; " width="25px" height="25px"
						src="./cu_programmazioni_2019/image/addup.png"></td>
					<td><img style=" visibility : hidden; " width="25px" height="25px"
						src="./cu_programmazioni_2019/image/adddown.png"></td>
					<td><a
					
<%  
 			   if(((piano.getCodiceInternoPiano()!=null && codiciInterniReadonlyPiani.contains(piano.getCodiceInternoPiano().toString())) || (piano.getCodiceInternoAttivita()!=null && codiciInterniReadonlyPiani.contains(piano.getCodiceInternoAttivita().toString())))  && p1.doStartTag()!=1 )
 			   {
%>
					href="javascript:alert('Modifica non consentita. Rivolgersi all Help Desk di II livello.')">
 <%
 			   }
 			   else
 			   {
%> 				   
					href="javascript:openNotePianoMonitoraggioReplaceAttivita_NewDpat('<%=scadenzaPiano%>',<%=oidPiano%>,'dpat_attivita','no',<%=anno%>,'<%=(descrIndicatore+"").toUpperCase()%>' )"	>
<%
 			   }
%>
						
					
						<img
							title="ESEGUI VARIAZIONE SUL PIANO/ATTIVITA (LE POSSIBILI VARIAZIONI SONO DEL TIPO MODIFICA O SPOSTAMENTO)"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/edit.jpg"></a></td>
					<td><a
						href="javascript:disabilitaEntry('<%=scadenzaPiano%>',<%=oidPiano%>,'dpat_attivivita','si',<%=anno%>)"><img
							title="Rendi Obsoleto <%=descrPiano%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/remove.png"></a></td>


				</tr>
			</table>
			 
			
 <%
 	}
 %>
		</td>
	</tr>


  
	<%
		/*if (attivita.getElencoIndicatori().size() > 0) {
							for (DpatIndicatore sp : attivita.getElencoIndicatori()) MODIFICA NEWDPAT*/
		ArrayList<DpatIndicatoreNewBeanAbstract> indicatoriFigli = piano.getIndicatoriFigli();					
		if(indicatoriFigli.size() > 0) {
							for(DpatIndicatoreNewBeanAbstract sp : indicatoriFigli)			
		{
							    Timestamp scadenzaIndicatore = sp.getScadenza();
								if(scadenzaIndicatore != null) continue;
								
								Integer codiceRaggruppamentoIndicatore = sp.getCodiceRaggruppamento();
								String aliasIndicatore = sp.getAliasIndicatore();
								String codiceAliasIndicatore = sp.getCodiceAliasIndicatore();
								String tipoAttivitaPiano = piano.getTipoAttivita();
								String codiceEsameIndicatore = sp.getCodiceEsame();
								String codiceAslIndicatore = sp.getCodiceAsl();
								String descrizioneIndicatore = sp.getDescrizione();
							    Integer tipoItemDpat = sp.getTipoItemDpat();
							    Boolean rendic_per_campioni = sp.getRendic_per_campioni();
								Long oidIndicatore = sp.getOid();
	%>
	  
	 
	<tr>
	 
	<td style="background-color: <%=color%>"><a href=  "#" onclick = "apriDettaglioIndicatore(<%=codiceRaggruppamentoIndicatore %>,<%=annoPiano %>)" ><%=( toHtml2(codiceAliasAttivita+"").toUpperCase()+"."+toHtml2(codiceAliasIndicatore+"").toUpperCase() )%>&nbsp;</a></td>
		<td style="background-color: <%=color%>"><%=toHtml2(aliasIndicatore).toUpperCase()%>&nbsp;</td>
		<td style="background-color: <%=color%>"><%=toHtml2(tipoAttivitaPiano).toUpperCase()%>&nbsp;</td>

		<td style="background-color: <%=color%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=(descrizioneIndicatore+"").toUpperCase()%></td>

		<td style="background-color: <%=color%>">&nbsp;<%=toHtml2(codiceEsameIndicatore)%>&nbsp;
		</td>
		<td style="background-color: <%=color%>">&nbsp;<%=((tipoItemDpat!=null)?(toHtml2(lookup_dpat_tipo_item.getSelectedValue(tipoItemDpat))):(""))%>&nbsp;
		</td>
		
		<td style="background-color: <%=color%>">&nbsp;<%=((rendic_per_campioni!=null)?((rendic_per_campioni)?"SI":("NO")):("NO")) %>&nbsp;
		</td>
		
<%-- 		<td style="background-color: <%=color%>">&nbsp;<%=toHtml2(lookup_asl.getSelectedValue(codiceAslIndicatore))%>&nbsp; --%>
<!-- 		</td> -->
		
		<td>
		
<%		
		if(((sp.getCodiceInternoPianiGestioneCu()!=null && codiciInterniReadonlyIndicatori.contains(sp.getCodiceInternoPianiGestioneCu().toString())) || (sp.getCodiceInternoAttivitaGestioneCu()!=null && codiciInterniReadonlyIndicatori.contains(sp.getCodiceInternoAttivitaGestioneCu().toLowerCase()))) && p1.doStartTag()!=1)
	   {
%>
			<table class="noborder">
			<tr>
				<td>
					Modifiche non consentite.<br/> Rivolgersi all'Help Desk di II livello
				</td> 				 
		  		</tr>
			</table>
<%
	   }
		else
		{
%>		
		
			<table class="noborder">
				<tr>
					<td>
						<img 
						title="Aggiungi Sottopiano a <%=descrizioneIndicatore%>"
						width="25px" height="25px" src="./cu_programmazioni_2019/image/add.png"
						style=" visibility : hidden; ">
						</td>

					<td><img style=" visibility : hidden; " width="25px" height="25px"
						src="./cu_programmazioni_2019/image/add_up_padre.png"></td>
					<td><img style=" visibility : hidden; " width="25px" height="25px"
						src="./cu_programmazioni_2019/image/add_down_padre.png"></td>

					<td><a
						href="javascript:apriPopupNuovaEntry('up',<%=oidIndicatore%>,'no','dpat_indicatore',<%=anno%>)"><img
							title="Aggiungi Piano Fratello Sopra <%=descrizioneIndicatore%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/addup.png"></a></td>
					<td><a
						href="javascript:apriPopupNuovaEntry('down',<%=oidIndicatore%>,'no','dpat_indicatore',<%=anno%>)"><img
							title="Aggiungi Piano Fratello Giu <%=oidIndicatore%>"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/adddown.png"></a></td>
					<td><a
						href="javascript:openNotePianoMonitoraggioReplaceIndicatore_NewDpat('<%=scadenzaIndicatore%>',<%=oidIndicatore%>,'dpat_indicatore','no',<%=anno%>)"><img
							title="ESEGUI VARIAZIONE SU indicatore (LE POSSIBILI VARIAZIONI SONO DEL TIPO MODIFICA O SPOSTAMENTO)"
							width="25px" height="25px"
							src="./cu_programmazioni_2019/image/edit.jpg"></a></td>
					<td><a
						href="javascript:disabilitaEntry('<%=scadenzaIndicatore%>',<%=oidIndicatore%>,'dpat_indicatore','si',<%=anno%>)"><img
							title="Rendi Obsoleto <%=descrizioneIndicatore%>" width="25px"
							height="25px" src="./cu_programmazioni_2019/image/remove.png"></a></td>
				</tr>
			</table>
<%
		}
%>
		</td>
	</tr>
	 
	<%
		}
						}
					 
				}

			}
	%>

</table>
<%
	} else {
%>
<tr class="containerBody">
	<td colspan="<%=3%>">Nessuna Piano Trovata</td>
</tr>
</table>
<%
	}
%>
<br>
<br>

<script>
function funload(){
         window.sessionStorage.setItem('scrollPosPconf', window.scrollY);
}

function fload(){
         setTimeout(function(){
window.scrollTo(0,window.sessionStorage.getItem('scrollPosPconf')); }, 900);
}

window.onbeforeunload=funload;

window.onload=fload;
</script>
