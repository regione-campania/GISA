/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(form, idCc) 
{
	var toReturn = true;
	if(
			(document.getElementById('codiceIspra')!=null && document.getElementById('codiceIspra').value!='' && 
			 document.getElementById('codiceIspra').value!=document.getElementById('oldCodiceIspra').value && 
			 myConfirm('Sicuro di modificare il codice Ispra?')
			 ) || 
			(
				document.getElementById('codiceIspra')==null || document.getElementById('codiceIspra').value=='' ||
			    document.getElementById('codiceIspra').value==document.getElementById('oldCodiceIspra').value
			 )
	   )
	{
	if(confrontaDate(form.dataChiusura.value,form.dataApertura.value)<0)
	{
		alert("La data di dimissione non può essere antecedente alla data di apertura della CC(" + form.dataApertura.value + ")");	
		form.dataChiusura.focus();
		toReturn = false;
	}
	if(!controllaDataAnnoCorrente(form.dataChiusura, 'Data dimissione'))
	{
		form.dataChiusura.focus();
		toReturn = false;
	}
	if(!toReturn)
		return false;
	
	if(form.destinazioneAninale.value==2 && form.dataMorte.value == '')
	{
		alert("Inserire la data del decesso");	
		form.dataMorte.focus();
		toReturn = false;
	}
	if(document.getElementById('intraFuoriAsl')!=null && document.getElementById('versoAssocCanili')!=null && document.getElementById('versoAssocCanili').checked==true && document.getElementById('intraFuoriAsl').checked==true)
	{
		alert("Scegliere una sola opzione tra 'fuori asl' e 'verso Associazioni/Canili'");	
		toReturn = false;
	}
	if(form.destinazioneAninale.value==5 && form.provinciaReimmissione!=null && form.provinciaReimmissione.value=="X")
	{
		alert("Inserire la provincia reimmissione");	
		toReturn = false;
	}
	if(form.destinazioneAninale.value==5 && form.provinciaReimmissione!=null && form.provinciaReimmissione.value!="X")
	{
		if((form.provinciaReimmissione.value=="AV" && form.comuneReimmissioneAV.value=="0") ||
			(form.provinciaReimmissione.value=="BN" && form.comuneReimmissioneBN.value=="0") ||
			(form.provinciaReimmissione.value=="CE" && form.comuneReimmissioneCE.value=="0") ||
			(form.provinciaReimmissione.value=="NA" && form.comuneReimmissioneNA.value=="0") ||
			(form.provinciaReimmissione.value=="SA" && form.comuneReimmissioneSA.value=="0") )
		{
			alert("Inserire il comune reimmissione");	
			toReturn = false;
		}
	}
	if(form.destinazioneAninale.value==5 && form.luogoReimmissione!=null && form.luogoReimmissione.value=="")
	{
		alert("Inserire il luogo reimmissione");	
		toReturn = false;
	}
	if(form.dataMorte!=null)
	{
		if(!controllaDataAnnoCorrente(form.dataMorte,'Data del decesso'))
		{
			form.dataMorte.focus();
			toReturn = false;
		}
	}
	if(!toReturn)
		return false;

	if(form.dataMorte!=null)
	{
		if(form.destinazioneAninale.value==2 && form.dataMorte.value != '' && confrontaDate(form.dataMorte.value,form.dataApertura.value)<0)
		{
			alert("La data del decesso non può essere antecedente alla data di apertura della CC(" + form.dataApertura.value + ")");	
			form.dataChiusura.focus();
			toReturn = false;
		}
	}
	if(!toReturn)
		return false;

	if(form.dataMorte!=null)
	{
		if(form.destinazioneAninale.value==2 && form.dataMorte.value != '' && confrontaDate(form.dataMorte.value,form.dataChiusura.value)>0)
		{
			alert("La data del decesso non pu� successiva alla data di dimissioni della CC(" + form.dataChiusura.value + ")");	
			form.dataChiusura.focus();
			toReturn = false;
		}
	}
	if(!toReturn)
		return false;

	if(form.destinazioneAninale.value==2 && form.causaMorteIniziale.value == '')
	{
		alert("Inserire la probabile causa del decesso");	
		form.causaMorteIniziale.focus();
		toReturn = false;
	}
	if(!toReturn)
		return false;

	/*if(form.destinazioneAninale.value==2 && document.getElementById("provincia").value=="")
	{
		alert("Selezionare la provincia");
		document.getElementById('provincia').focus();
		toReturn = false;	
	}
	if(!toReturn)
		return false;
	
	if(form.destinazioneAninale.value==2 && document.getElementById("provincia").value!="" && document.getElementById("comuneChooser"+document.getElementById("provincia").value).value=="")
	{
		alert("Selezionare il comune");
		document.getElementById('provincia').focus();
		toReturn = false;	
	}
	if(!toReturn)
		return false;
	
	if(form.destinazioneAninale.value==2 && document.getElementById("indirizzo").value=="")
	{
		alert("Inserire l'indirizzo");
		document.getElementById('indirizzo').focus();
		toReturn = false;	
	}*/
	
	if(!toReturn)
		return false;

	if(form.destinazioneAninale.value==2 &&  !document.getElementById('smaltimentoCarogna').checked)
	{
		alert("Selezionare almeno un'operazione");	
		form.causaMorteIniziale.focus();
		toReturn = false;
	}

	if(!toReturn)
		return false;
	
	var intraFuoriAsl = false;
	var versoAssocCanili = false;
	if(document.getElementById('intraFuoriAsl')!=null)
		intraFuoriAsl = document.getElementById('intraFuoriAsl').checked;
	if(document.getElementById('versoAssocCanili')!=null)
		versoAssocCanili = document.getElementById('versoAssocCanili').checked;
	
	TestDimissioni.check(document.getElementById('idDA').value, document.getElementById('idUtente').value, document.getElementById('idAnimale').value, idCc, intraFuoriAsl , versoAssocCanili, 
				{
						callback:function(msg) 
						{ 
							if(msg!=null && msg!='' /*&& !myConfirm(msg)*/)
							{
								alert(msg);
								toReturn = false;
							}
						},
						async: false,
						timeout:5000,
						errorHandler:function(message, exception)
						{
						    //Session timedout/invalidated
						    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
						    {
						        alert(message);
						        //Reload or display an error etc.
						        window.location.href=window.location.href;
						    }
						    else
						        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
						 }
	 			});
	
	//attendere();
	return toReturn;
	}
	else
	{
		return false;
	}
	
	
	
	
	
}


function abilitaDisabilitaIntraFuoriAsl(combo)
{
	if(combo.value=='3')
	{
		document.getElementById('intraFuoriAsl').disabled=false;
	}
	else
	{
		document.getElementById('intraFuoriAsl').disabled="disabled";
		document.getElementById('intraFuoriAsl').checked=false;
	}
}

function abilitaDisabilitaVersoAssocCanili(combo)
{
	if(document.getElementById('versoAssocCanili')!=null)
	{
		if(combo.value=='3')
		{
			document.getElementById('versoAssocCanili').disabled=false;
		}
		else
		{
			document.getElementById('versoAssocCanili').disabled="disabled";
			document.getElementById('versoAssocCanili').checked=false;
		}
	}
}

function abilitaDisabilitaDatiMorte(dataMorte, decedutoNonAnagrafe)
{
	var disabled  = true;
	var dataMorteDaMostrare = "";
	if(form.destinazioneAninale.value==2)
	{
		//document.getElementById('esameNecroscopico').disabled	=	false;
		document.getElementById('smaltimentoCarogna').disabled	=	false;
		disabled = false;
		if(decedutoNonAnagrafe=='true')
			dataMorteDaMostrare = dataMorte;		
	}
	else
	{
		//document.getElementById('esameNecroscopico').disabled	=	disabled;
		document.getElementById('smaltimentoCarogna').disabled	=	disabled;
	}
	form.dataMorte.disabled				=	disabled;
	document.getElementById("dataMorteCertaT").disabled = disabled;
	document.getElementById("dataMorteCertaF").disabled = disabled;
	form.causaMorteIniziale.disabled	=	disabled;
	form.indirizzo.disabled	=	disabled;
	form.note.disabled		=	disabled;
	form.provincia.disabled	=	disabled;
	form.comuneNA.disabled	=	disabled;
	form.comuneAV.disabled	=	disabled;
	form.comuneSA.disabled	=	disabled;
	form.comuneBN.disabled	=	disabled;
	form.comuneCE.disabled	=	disabled;
	form.dataMorte.value				=	dataMorteDaMostrare;
	form.dataMorteCerta.value		 	=	"";
	form.causaMorteIniziale.value	 	=	"";
	form.provincia.value	 	=	"";
	form.comuneNA.value	 	=	"";
	form.comuneAV.value	 	=	"";
	form.comuneSA.value	 	=	"";
	form.comuneBN.value	 	=	"";
	form.comuneCE.value	 	=	"";
	form.indirizzo.value 	=	"";
	form.note.value		 	=	"";
}


function abilitaDisabilitaDatiRilascio(specieAnimale)
{
	var disabled  = true;
	if(form.destinazioneAninale.value==5 && specieAnimale == '3')
	{
		document.getElementById('provinciaReimmissione').disabled	=	false;
		document.getElementById('comuneReimmissioneSA').disabled	=	false;
		document.getElementById('comuneReimmissioneAV').disabled	=	false;
		document.getElementById('comuneReimmissioneBN').disabled	=	false;
		document.getElementById('comuneReimmissioneCE').disabled	=	false;
		document.getElementById('comuneReimmissioneSA').disabled	=	false;
		document.getElementById('luogoReimmissione').disabled	=	false;
		document.getElementById('codiceIspra').disabled	=	false;
		document.getElementById('tdRilascio1').style.display =	"block";
		document.getElementById('tdRilascio3').style.display =	"block";
		document.getElementById('tdRilascio4').style.display =	"block";
		document.getElementById('tdRilascio6').style.display =	"block";
		document.getElementById('codiceIspra').style.display =	"block";
		document.getElementById('provinciaReimmissione').style.display =	"block";
		//document.getElementById('comuneReimmissioneSA').style.display =	"block";
		//document.getElementById('comuneReimmissioneAV').style.display =	"block";
		//document.getElementById('comuneReimmissioneBN').style.display =	"block";
		//document.getElementById('comuneReimmissioneCE').style.display =	"block";
		//document.getElementById('comuneReimmissioneSA').style.display =	"block";
		disabled = false;
	}
	else
	{
		document.getElementById('provinciaReimmissione').disabled	=	disabled;
		document.getElementById('comuneReimmissioneSA').disabled	=	disabled;
		document.getElementById('comuneReimmissioneAV').disabled	=	disabled;
		document.getElementById('comuneReimmissioneBN').disabled	=	disabled;
		document.getElementById('comuneReimmissioneCE').disabled	=	disabled;
		document.getElementById('comuneReimmissioneSA').disabled	=	disabled;
		document.getElementById('luogoReimmissione').disabled	=	disabled;
		document.getElementById('codiceIspra').disabled	=	disabled;
		document.getElementById('tdRilascio1').style.display =	"none";
		document.getElementById('tdRilascio3').style.display =	"none";
		document.getElementById('tdRilascio4').style.display =	"none";
		document.getElementById('tdRilascio6').style.display =	"none";
		document.getElementById('codiceIspra').style.display =	"none";
		document.getElementById('provinciaReimmissione').style.display =	"none";
		//document.getElementById('comuneReimmissioneSA').style.display =	"none";
		//document.getElementById('comuneReimmissioneAV').style.display =	"none";
		//document.getElementById('comuneReimmissioneBN').style.display =	"none";
		//document.getElementById('comuneReimmissioneCE').style.display =	"none";
		//document.getElementById('comuneReimmissioneSA').style.display =	"none";
	}
}


function chooseProvinciaReimmissione(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}
}

function chooseProvincia(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneChooserBN' ).style.display = "";
		document.getElementById( 'comuneChooserNA' ).style.display = "none";
		document.getElementById( 'comuneChooserSA' ).style.display = "none";
		document.getElementById( 'comuneChooserCE' ).style.display = "none";
		document.getElementById( 'comuneChooserAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneChooserBN' ).style.display = "none";
		document.getElementById( 'comuneChooserNA' ).style.display = "";
		document.getElementById( 'comuneChooserSA' ).style.display = "none";
		document.getElementById( 'comuneChooserCE' ).style.display = "none";
		document.getElementById( 'comuneChooserAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneChooserBN' ).style.display = "none";
		document.getElementById( 'comuneChooserNA' ).style.display = "none";
		document.getElementById( 'comuneChooserSA' ).style.display = "none";
		document.getElementById( 'comuneChooserCE' ).style.display = "none";
		document.getElementById( 'comuneChooserAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneChooserBN' ).style.display = "none";
		document.getElementById( 'comuneChooserNA' ).style.display = "none";
		document.getElementById( 'comuneChooserSA' ).style.display = "";
		document.getElementById( 'comuneChooserCE' ).style.display = "none";
		document.getElementById( 'comuneChooserAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneChooserBN' ).style.display = "none";
		document.getElementById( 'comuneChooserNA' ).style.display = "none";
		document.getElementById( 'comuneChooserSA' ).style.display = "none";
		document.getElementById( 'comuneChooserCE' ).style.display = "";
		document.getElementById( 'comuneChooserAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneChooserBN' ).style.display = "none";
		document.getElementById( 'comuneChooserNA' ).style.display = "none";
		document.getElementById( 'comuneChooserSA' ).style.display = "none";
		document.getElementById( 'comuneChooserCE' ).style.display = "none";
		document.getElementById( 'comuneChooserAV' ).style.display = "none";
	}
}