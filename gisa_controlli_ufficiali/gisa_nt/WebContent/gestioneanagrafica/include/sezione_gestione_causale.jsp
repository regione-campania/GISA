<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<div id="specifica_causale" style="display:">
<center>
	<h2>seleziona la causale per cui si effettua questa operazione</h2>
	<br>
	<select id="id_causale" name="_b_id_causale" style="font-size: 20px;" onchange="cambio_causale();">
		<option value="1">pratica suap</option>
		<dhv:permission name="gestioneanagrafica-errata-corrige-scia-view">
			<option value="2">richiesta errata corrige</option>
			<option value="3">richiesta forze dell'ordine</option>
		</dhv:permission>  
		<option value="4" selected>presa visione diretta</option>
		<!--  <option value="5">altro</option> -->
	</select>

	<br><br>

	<div id="dati_presa_visione" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display:" >
		
		<label style="text-align:center; font-size: 15px;">data presa visione diretta </label><br>
		<input type="text" id="data_pratica_visione_diretta" name="_b_data_pratica" autocomplete="off" onkeydown="return false"  size="15" style="text-align:center;"/>
		<font color="red">*</font>
		<br><br>
		<label style="text-align:center; font-size: 15px;">nota presa visione diretta </label><br>
		<input type="text" id="nota_pratica_visione_diretta" name="_b_nota_pratica" autocomplete="off" size="100"/><br>
		<font color="red">*descrizione dalla modalità di rilevazione</font>
	</div>
	

	<div id="dati_pratiche_suap" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display: none">
		<input type="radio" id="creapraticasuap" name="gestionepraticasuap" value="1" checked/> 
			<label style="text-align:center; font-size: 15px;">NUOVA PRATICA SUAP</label>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="cercapraticasuap" name="gestionepraticasuap" value="2"/> 
			<label style="text-align:center; font-size: 15px;">CERCA PRATICA SUAP</label>
	</div>

	<div id="dati_errata_corrige" display="none" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display: none">
		<label style="text-align:center; font-size: 15px;">numero richiesta errata corrige </label><br>
		<input type="text" id="numero_pratica_errata_corrige" name="_b_numero_pratica" autocomplete="off" size="40" style="text-align:center;"/>
		<font color="red">*</font>
		<br><br>
		<label style="text-align:center; font-size: 15px;">data richiesta errata corrige </label><br>
		<input type="text" id="data_pratica_errata_corrige" name="_b_data_pratica" autocomplete="off" size="15" onkeydown="return false" style="text-align:center;"/>
		<font color="red">*</font>
		<br><br>
		<label style="text-align:center; font-size: 15px;">nota richiesta errata corrige </label><br>
		<input type="text" id="nota_pratica_errata_corrige" name="_b_nota_pratica" autocomplete="off" size="100"/>
		<font color="red">*</font>
		<br><br>
		<input type="hidden" id="idAggiuntaPratica" name="idAggiuntaPratica" value="${idAggiuntaPratica}"/>
		<a href="javascript:openUploadAllegatoGins(${idAggiuntaPratica}, 'richiesta_errata_corrige', 'GINS_Pratica')" id='allega'>Allega richiesta errata corrige</a>
		<input type='hidden' readonly='readonly' id='header_richiesta_errata_corrige' name='header_richiesta_errata_corrige' value=''/>
		<label id='titolo_richiesta_errata_corrige' name='titolo_richiesta_errata_corrige'></label>
		<input type='button' value='rimuovi allegato' 
			onclick="if(document.getElementById('header_richiesta_errata_corrige').value.trim() != ''){
						document.getElementById('header_richiesta_errata_corrige').value = '';
						document.getElementById('titolo_richiesta_errata_corrige').innerHTML = '';
					} "/>
	</div>
	
	<div id="dati_forze_ordine" display="none" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display: none">
		<label style="text-align:center; font-size: 15px;">data richiesta forze dell'ordine </label><br>
		<input type="text" id="data_pratica_forze_ordine" name="_b_data_pratica" autocomplete="off" size="15" onkeydown="return false" style="text-align:center;"/>
		<font color="red">*</font>
		<br><br>
		<label style="text-align:center; font-size: 15px;">nota richiesta forze dell'ordine </label><br>
		<input type="text" id="nota_pratica_forze_ordine" name="_b_nota_pratica" autocomplete="off" size="100"/>
		<font color="red">*</font>
	</div>
	
	<div id="dati_pratica_altro" display="none" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display: none">
		<label style="text-align:center; font-size: 15px;">descrizione</label><br>
		<input type="text" id="nota_pratica_altro" name="_b_nota_pratica" autocomplete="off" size="100"/>
		<font color="red">*</font>
		<br><br>
		<label style="text-align:center; font-size: 15px;">data</label><br>
		<input type="text" id="data_pratica_altro" name="_b_data_pratica" autocomplete="off" size="15" onkeydown="return false" style="text-align:center;"/>
		<font color="red">*</font>
	</div>
	
	<br><br>

	<button type="button" class="yellowBigButton" style="width: 250px;" 
		onClick="gestione_causale(); ">PROSEGUI</button>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" id="annulla_scelta_causale" class="yellowBigButton" style="width: 250px;" 
		onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestioneAnagraficaAction.do?command=Details&altId=${altId}'">ANNULLA</button>
</center>
</div>

<script>
var verifica_esistenza_pratica = '0';
if(document.getElementById('idTipologiaPratica').value == '1'){
	
	document.getElementById('annulla_scelta_causale').onclick = function(){
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='OpuStab.do?command=SearchForm';
	};
	
}

function cambio_causale(){
	var id_tipo_causale = document.getElementById('id_causale').value;
	
	if(id_tipo_causale == '1'){
		document.getElementById('dati_pratiche_suap').style.display='';
		document.getElementById('dati_presa_visione').style.display='none';
		document.getElementById('dati_errata_corrige').style.display='none';
		document.getElementById('dati_forze_ordine').style.display='none';
		document.getElementById('dati_pratica_altro').style.display='none';
	}
	
	if(id_tipo_causale == '2'){
		document.getElementById('dati_pratiche_suap').style.display='none';
		document.getElementById('dati_presa_visione').style.display='none';
		document.getElementById('dati_errata_corrige').style.display='';
		document.getElementById('dati_forze_ordine').style.display='none';
		document.getElementById('dati_pratica_altro').style.display='none';
	}
	
	if(id_tipo_causale == '3'){
		document.getElementById('dati_pratiche_suap').style.display='none';
		document.getElementById('dati_presa_visione').style.display='none';
		document.getElementById('dati_errata_corrige').style.display='none';
		document.getElementById('dati_forze_ordine').style.display='';
		document.getElementById('dati_pratica_altro').style.display='none';
	}
	
	if(id_tipo_causale == '4'){
	    document.getElementById('dati_pratiche_suap').style.display='none';
		document.getElementById('dati_presa_visione').style.display='';
		document.getElementById('dati_errata_corrige').style.display='none';
		document.getElementById('dati_forze_ordine').style.display='none';
		document.getElementById('dati_pratica_altro').style.display='none';
	}
	
	if(id_tipo_causale == '5'){
		document.getElementById('dati_pratiche_suap').style.display='none';
		document.getElementById('dati_errata_corrige').style.display='none';
		document.getElementById('dati_presa_visione').style.display='none';
		document.getElementById('dati_forze_ordine').style.display='none';
		document.getElementById('dati_pratica_altro').style.display='';
	}
}

function gestione_causale(){
	
	var id_tipo_causale = document.getElementById('id_causale').value;
	
	if(id_tipo_causale == '1'){
		
		if(document.getElementById('creapraticasuap').checked){
			if(document.getElementById('idTipologiaPratica').value == '1'){
				loadModalWindowCustom('Attendere Prego...'); 
				window.location.href='GestionePraticheAction.do?command=CreaPratica&tipo_operazione_pratica=' + document.getElementById('idTipologiaPratica').value;
			} else {
				loadModalWindowCustom('Attendere Prego...'); 
				window.location.href='GestionePraticheAction.do?command=CreaPratica&tipo_operazione_pratica=' + document.getElementById('idTipologiaPratica').value 
						+ '&altId='+ document.getElementById('altId').value;
			}
			
		}else{
			loadModalWindowCustom('Attendere Prego...'); 
			window.location.href='GestionePraticheAction.do?command=SearchFormPratica';
		}
	}
	
	if(id_tipo_causale == '2'){
		if(document.getElementById('numero_pratica_errata_corrige').value.trim() == ''){
			alert('Attenzione: inserire numero richiesta errata corrige!');
			return false;
		}
		
		//verifica esistenza numero pratica errata corrige
		controllaEsistenzaNumeroPratica(document.getElementById('numero_pratica_errata_corrige').value.trim(), '-1', id_tipo_causale);
		if(verifica_esistenza_pratica == '1'){
			alert('Attenzione, numero richiesta errata corrige non valido perchè già utilizzato!');
			return false;
		}
		
		if(document.getElementById('data_pratica_errata_corrige').value.trim() == ''){
			alert('Attenzione: inserire data richiesta errata corrige!');
			return false;
		}
		
		if(document.getElementById('nota_pratica_errata_corrige').value.trim() == ''){
			alert('Attenzione: inserire nota richiesta errata corrige!');
			return false;
		}
		
		if(document.getElementById('header_richiesta_errata_corrige').value.trim() == ''){
			alert('Attenzione: inserire allegato richiesta errata corrige!');
			return false;
		}
		document.getElementById('data_pratica_visione_diretta').disabled = true;
		document.getElementById('nota_pratica_visione_diretta').disabled = true;
		document.getElementById('data_pratica_forze_ordine').disabled = true;
		document.getElementById('nota_pratica_forze_ordine').disabled = true;
		
		
	}
	
	if(id_tipo_causale == '3'){
		if(document.getElementById('data_pratica_forze_ordine').value.trim() == ''){
			alert('Attenzione: inserire data richiesta forze dell ordine!');
			return false;
		}
		
		if(document.getElementById('nota_pratica_forze_ordine').value.trim() == ''){
			alert('Attenzione: inserire nota richiesta forze dell ordine!');
			return false;
		}
		document.getElementById('data_pratica_visione_diretta').disabled = true;
		document.getElementById('nota_pratica_visione_diretta').disabled = true;
		document.getElementById('numero_pratica_errata_corrige').disabled = true;
		document.getElementById('data_pratica_errata_corrige').disabled = true;
		document.getElementById('nota_pratica_errata_corrige').disabled = true;
	}
	
	if(id_tipo_causale == '4'){
		if(document.getElementById('data_pratica_visione_diretta').value.trim() == ''){
			alert('Attenzione: inserire data richiesta presa visione diretta!');
			return false;
		}
		
		if(document.getElementById('nota_pratica_visione_diretta').value.trim() == ''){
			alert('Attenzione: inserire nota richiesta presa visione diretta!');
			return false;
		}
		document.getElementById('data_pratica_forze_ordine').disabled = true;
		document.getElementById('nota_pratica_forze_ordine').disabled = true;
		document.getElementById('numero_pratica_errata_corrige').disabled = true;
		document.getElementById('data_pratica_errata_corrige').disabled = true;
		document.getElementById('nota_pratica_errata_corrige').disabled = true;
	}
	
	if(id_tipo_causale == '5'){
		if(document.getElementById('nota_pratica_altro').value.trim() == ''){
			alert('Attenzione: inserire una descrizione!');
			return false;
		}
		
		if(document.getElementById('data_pratica_altro').value.trim() == ''){
			alert('Attenzione: inserire il campo data!');
			return false;
		}
		
		document.getElementById('data_pratica_forze_ordine').disabled = true;
		document.getElementById('nota_pratica_forze_ordine').disabled = true;
		document.getElementById('data_pratica_visione_diretta').disabled = true;
		document.getElementById('nota_pratica_visione_diretta').disabled = true;
		document.getElementById('numero_pratica_errata_corrige').disabled = true;
		document.getElementById('data_pratica_errata_corrige').disabled = true;
		document.getElementById('nota_pratica_errata_corrige').disabled = true;
	}
	

	document.getElementById('specifica_causale').style='display: none';
	if(document.getElementById('idTipologiaPratica').value == '1') {
		document.getElementById('tipo_linee').style='display:'; 
	} else {
		document.getElementById('operazione_scheda').style='display:'
	}
	
}

popup_date('data_pratica_visione_diretta');
popup_date('data_pratica_errata_corrige');
popup_date('data_pratica_forze_ordine');
popup_date('data_pratica_altro');


function popup_date(elemento_html_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
		  minDate: '01-01-1990',
		  maxDate: 0,
		  dayNamesMin : [ 'do', 'lu', 'ma', 'me', 'gi', 've', 'sa' ],
		  monthNamesShort :['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
		  beforeShow: function(input, inst) {
              setTimeout(function () {
                         var offsets = $('#' + elemento_html_data).offset();
                         var top = offsets.top - 100;
                         inst.dpDiv.css({ top: top, left: offsets.left});
                         $(".ui-datepicker-next").hide();
							$(".ui-datepicker-prev").hide();
							$(".ui-state-default").css({'font-size': 15});
							$(".ui-datepicker-title").css({'text-align': 'center'});
							$(".ui-datepicker-calendar").css({'text-align': 'center'});
               });
             },
        onChangeMonthYear: function(year, month, inst) {
              setTimeout(function () {
                         var offsets = $('#' + elemento_html_data).offset();
                         var top = offsets.top - 100;
                         inst.dpDiv.css({ top: top, left: offsets.left});
                         $(".ui-datepicker-next").hide();
							$(".ui-datepicker-prev").hide();
							$(".ui-state-default").css({'font-size': 15});
							$(".ui-datepicker-title").css({'text-align': 'center'});
							$(".ui-datepicker-calendar").css({'text-align': 'center'});
               });
             }                                                  
		});
}


function controllaEsistenzaNumeroPratica(numeroPratica, comune_ric, id_causale_pratica)
{
	loadModalWindowCustom('Verifica esistenza pratica/richiesta in corso. Attendere...');
	DWRnoscia.controlloEsistenzaNumeroPratica(numeroPratica, comune_ric, id_causale_pratica,{callback:controllaEsistenzaNumeroPraticaCallBack,async:false});
}

function controllaEsistenzaNumeroPraticaCallBack(val)
{	
	var dati = val;
	var objresp;
	objresp = JSON.parse(dati);
	var len = objresp.length;
	if (len > 0){
		verifica_esistenza_pratica = '1';
		loadModalWindowUnlock();	
		
	} else {
		verifica_esistenza_pratica = '0';
		loadModalWindowUnlock();
		}
}

</script>
