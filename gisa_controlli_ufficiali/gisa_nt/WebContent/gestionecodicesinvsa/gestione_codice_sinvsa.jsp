<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.gestioneanagrafica.base.AnagraficaCodiceSINVSARecord" %>

<%
	String action = request.getParameter("action");
	int riferimentoId = Integer.parseInt(request.getParameter("riferimentoId"));
	String riferimentoIdNomeTab = request.getParameter("riferimentoIdNomeTab");
	AnagraficaCodiceSINVSARecord recordCodice = PopolaCombo.getCodiceSINVSA(riferimentoId, riferimentoIdNomeTab);
	int idUtente = Integer.parseInt(request.getParameter("idRuoloUtente"));
	boolean haPermessoDiEditare = false;
%>

<dhv:permission name="inserimento-codice-sinvsa-view">
	<% haPermessoDiEditare = true; %>
</dhv:permission>

<div id="container-codice-sinvsa">
	<% if(haPermessoDiEditare) { %>
		<form id="form-codice-sinvsa" method="post" action="<%= action.concat(".do?command=SetCodiceSINVSA")%>" 
			novalidate>
			<table>
				<tbody>
					<td class="label">
						<label for="codice-sinvsa">CODICE SINVSA</label>
					</td>
					<td>
						<input id="codice-sinvsa" name="codice-sinvsa" data-tooltip="Fornire un valore valido" 
							value="<%= recordCodice != null ? recordCodice.getCodiceSINVSA() : "" %>">
					</td>
					<td class="label">
						<label for="data-codice-sinvsa">DATA INSERIMENTO</label>
					</td>
					<td>
						<input type="date" id="data-codice-sinvsa" name="data-codice-sinvsa" data-tooltip="Fornire una data valida"
							value="<%= recordCodice != null ? recordCodice.getDataCodiceSINVSA().toString() : "" %>">
					</td>
					<td style="border: none;">
						<% if(recordCodice == null) { %>
							<input type="submit" id="bottone-inserisci" value="INSERISCI">
						<% } else { %>
							<input type="button" value="MODIFICA" id="bottone-modifica" onclick="abilitaModifica()">
						<% } %>
					</td>
					<input type="hidden" id="riferimento-id" name="riferimento-id" value="<%= riferimentoId %>">
					<input type="hidden" id="riferimento-id-nome-tab" name="riferimento-id-nome-tab" 
						value="<%= riferimentoIdNomeTab %>">
					<input type="hidden" id="user-id" name="user-id" value="<%= idUtente %>">
				</tbody>
			</table>
		</form>
	<% } else if (recordCodice != null){ %>
		<table>
			<tbody>
				<tr>
					<td class="label">
						CODICE SINVSA
					</td>
					<td class="input-value" id="codice-sinvsa">
						<%= recordCodice.getCodiceSINVSA() %>
					</td>
					<td class="label">
						DATA INSERIMENTO
					</td>
					<td class="input-value" id="data-codice-sinvsa">
						<%= recordCodice.getDataCodiceSINVSA() %>
					</td>
				</tr>
			</tbody>
		</table>
	<% } %>
</div>

<script>
//InvalidInputTooltip constructor
function InvalidInputTooltip(element) {
    if(!(element instanceof HTMLInputElement))
        throw 'Invalid Argument'
    this.anchorElement = element
    this.template = document.createElement('div')
    this.template.classList.add('tooltip')
	this.template.classList.add('error-tooltip')
    this.template.innerText = this.anchorElement.dataset.tooltip ? this.anchorElement.dataset.tooltip : ''
    this.add()
    this.rearrange()
    this.hide()
	this.anchorElement.addEventListener('input', event => {
		if(event.target.checkValidity() == false)
			this.show()
		else
			this.hide()
	})
}

//methods
InvalidInputTooltip.prototype.add = function() {
	this.anchorElement.insertAdjacentElement('afterend', this.template)
}

InvalidInputTooltip.prototype.remove = function() {
    this.template.remove()
}

InvalidInputTooltip.prototype.show = function() {
    this.template.style.display = 'inline-block'
    this.template.style.opacity = 1
    this.rearrange()
} 

InvalidInputTooltip.prototype.hide = function() {
	this.template.style.top = 0
    this.template.style.opacity = 0
    this.template.style.display = 'none'
}

InvalidInputTooltip.prototype.rearrange = function() {
    let parentBox = this.anchorElement.getBoundingClientRect()
    let thisBox = this.template.getBoundingClientRect()
    //questo posizionamento è specifico solo a questa jsp
    let position = {
        x: ((parentBox.x + parentBox.width/2) - thisBox.width/2) - 5,
        y: parentBox.height + 10 //pixels
    }
    this.template.style.left = position.x
    this.template.style.top = position.y
}

</script>

<script>
//Gestione Codice SINVSA
inizializza()

function inizializza() {
	setRangeDate()
	let form = document.getElementById('form-codice-sinvsa')
	let inputCodice = document.getElementById('codice-sinvsa')
	let inputDataCodice = document.getElementById('data-codice-sinvsa')
	if(!form || !inputCodice || !inputDataCodice)
		return
	if(inputCodice.tagName.toLowerCase() != 'input' || inputDataCodice.tagName.toLowerCase() != 'input')
		return 
	form.addEventListener('submit', checkForm)
	inputCodice.addEventListener('input', setPatternCodice)
	inputDataCodice.addEventListener('input', setRangeDate)
	//inizializzazione tooltip
	let tooltips = document.querySelectorAll('.tooltip')
	if(tooltips)
		tooltips.forEach(t => t.remove())
	let elements = document.querySelectorAll('input[data-tooltip]')
	elements.forEach(el => new InvalidInputTooltip(el))
}

function setPatternCodice() {
	let input = document.getElementById('codice-sinvsa')
	input.required = true
	input.pattern = '[A-Za-z0-9]+'
}

function setRangeDate() {
	let input = document.getElementById('data-codice-sinvsa')
	input.min = '2000-01-01'
	input.max = dateToISOFormatString(new Date())
}

function dateToISOFormatString(date) {
	if(typeof date == 'string')
		date = new Date(date.split('/').reverse().join('-'))
	let y = date.getFullYear()
	let m = date.getMonth() + 1  //i mesi vanno da 0 a 11
	m = m >= 10 ? m : '0'+m
	let d = date.getDate()
	d = d >= 10 ? d : '0'+d
	return y+'-'+m+'-'+d
}

function dateToEURFormatString(date) {
	if(typeof date == 'string')
		date = new Date(date)
	let y = date.getFullYear()
	let m = date.getMonth() + 1  //i mesi vanno da 0 a 11
	m = m >= 10 ? m : '0'+m
	let d = date.getDate()
	d = d >= 10 ? d : '0'+d
	return d+'/'+m+'/'+y
}

function checkForm(event) {
	event.preventDefault()
	let form = event.target
	let isFormValid = true
	let inputs = form.querySelectorAll('input:not(input[type=hidden], input[type=submit], input[type=button])')
	if(!inputs)
		isFormValid = false
	else {
		inputs.forEach(input => {
			if(input.value == '' || input.checkValidity() == false)
				isFormValid = false
		})
	}
	if(isFormValid) {
		let submitButton = form.querySelector('input[type="submit"]')
		let animation = document.createElement('div')
		animation.className = 'loader'
		submitButton.replaceWith(animation)
		form.submit()
	}
	else
		alert('I valori inseriti non sono corretti.')
}
</script>

<% if(recordCodice != null) { %>
	<script>
		let container = document.getElementById('container-codice-sinvsa')
		let inputs = container.querySelectorAll('input:not(input[type=hidden], input[type=submit], input[type=button])')
		inputs.forEach(input => {
			let span = document.createElement('span')
			span.id = input.id
			if(input.type == 'date')
				span.innerText = dateToEURFormatString(input.value)
			else
				span.innerText = input.value
			input.replaceWith(span)
		})

		function abilitaModifica() {
			let spanCodice = document.getElementById('codice-sinvsa')
			let inputCodice = document.createElement('input')
			inputCodice.id = 'codice-sinvsa'
			inputCodice.name = inputCodice.id
			inputCodice.value = spanCodice.innerText
			inputCodice.dataset.tooltip = 'Fornire un valore valido'
			spanCodice.replaceWith(inputCodice)
			
			let spanDataCodice = document.getElementById('data-codice-sinvsa')
			let inputDataCodice = document.createElement('input')
			inputDataCodice.type = 'date'
			inputDataCodice.id = 'data-codice-sinvsa'
			inputDataCodice.name = inputDataCodice.id
			console.log(spanDataCodice.innerText)
			inputDataCodice.value = dateToISOFormatString(spanDataCodice.innerText)
			inputDataCodice.dataset.tooltip = 'Fornire una data valida'
			spanDataCodice.replaceWith(inputDataCodice)
			
			let bottoneModifica = document.getElementById('bottone-modifica')
			let bottoneInserisci = document.createElement('input')
			bottoneInserisci.type = 'submit'
			bottoneInserisci.id = 'bottone-inserisci'
			bottoneInserisci.value = 'INSERISCI'
			bottoneModifica.replaceWith(bottoneInserisci)
			inizializza()
		}
	</script>
<% } %>

<style>	
	#container-codice-sinvsa {
	display: inline-flex;
	min-width: 512px;
	justify-content: space-between;
	align-items: center;
	margin: 8px 0;
	position: relative;
	}
	
	#container-codice-sinvsa table td {
		padding: 3px 6px;
		border: 1px solid #888;
	}
	
	#container-codice-sinvsa table .label {
		background-color: #BDCFFF;
	}
	
	#container-codice-sinvsa form label,
	#container-codice-sinvsa table .label {
		font-weight: bold;
	}
	
	input:invalid {
		border-color: red;
		border-radius: 2px;
		outline: 2px solid #ff4f4f;
		outline-offset: 0.7px;
		transition: .1s;
	}
	
	#codice-sinvsa {
		text-transform: none !important;
	}
</style>

<style>
/* utilities */
	.tooltip {
		display: none;
		position: absolute;
		z-index: 1000;
		background-color: white;
		border: 2px solid black;
		border-radius: 2px;
		color: black;
		padding: 3px 6px;
		box-shadow: 0 0 1px 0px #676767;
		top: 0;
		opacity: 0;
		transition: top .6s, opacity .3s;
	}

	.error-tooltip {
		border: 2px solid #ff4f4f;
		color: white;
		background: #ff4f4f;
	}

	.loader {
	  border: 4px solid #b8b8b8;
	  border-radius: 50%;
	  border-top: 4px solid #4996E3;
	  width: 10px;
	  height: 10px;
	  -webkit-animation: spin 2s linear infinite; /* for Safari */
	  animation: spin 2s linear infinite;
	}
	/* for Safari */
	@-webkit-keyframes spin {
	  0% { -webkit-transform: rotate(0deg); }
	  100% { -webkit-transform: rotate(360deg); }
	}
	
	@keyframes spin {
	  0% { transform: rotate(0deg); }
	  100% { transform: rotate(360deg); }
	}
</style>
