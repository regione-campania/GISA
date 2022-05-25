/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function avviaPopup( pagina )
{
	var l = 0;
	var t = 0;
	var w = screen.width/2;
	var h = screen.height-100;
	window.open(pagina,"","width=" + w + ",height=" + h + ",top=" + t + ",left=" + l + ",scrollbars=yes, resizable=yes");
};

function avviaPopupPiccola(element , pagina, width, height) 
{
	var coords = {x: 0, y: 0};
	
	while (element) 
	{
		coords.x += element.offsetLeft;
		coords.y += element.offsetTop;
		element = element.offsetParent;
	} 
	coords.y += parseInt('140');
	
	var overflowX = 0;
	overflowX += parseInt(coords.x);
	overflowX += parseInt(width);
	overflowX -= parseInt(screen.width); 
	if(overflowX > 0 )
	{
		coords.x = parseInt(screen.width);
		coords.x -= parseInt(width);
	}
	
	var overflowY = 0;
	overflowY += parseInt(coords.y);
	overflowY += parseInt(height);
	overflowY -= parseInt(screen.height); 
	if(overflowY > 0 )
	{
		coords.y = parseInt(screen.height);
		coords.y -= parseInt(height);
		coords.y -= parseInt('60');
	}
   	
	window.open(pagina ,"","width=" + width + ",height=" + height + ",top=" + coords.y  + ",left=" + coords.x + ",scrollbars=yes, resizable=yes");

}

function openPopupFromAction(form) {
    window.open('', 'formpopup', 'width=700,height=700,resizeable,scrollbars');
    form.target = 'formpopup';
}


function checkMail(obj,str){
    var mail=obj.value;

    if (mail.length > 0) {
      var msg = "Formato nel campo "+str+" non valido.";
      var i=0;
      while(mail.charAt(i)!='@')
      {
        if (i<mail.length)
        {
        i++;
        }
        else{
        alert (msg);
        obj.focus();
        return false;
        }
      }//end while
       while(mail.charAt(i)!='.')
      {
        if (i<mail.length)
        {
        i++;
        }
      else
   {
            alert (msg);
            obj.focus();
            return false;
     }
   }//if
    }
    return true;
  }

function isNumber(Expression,nomeCampo,campo)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789.,";
    
    if (Expression.length < 1) 
        return (false);
    
    for (var i = 0; i < Expression.length; i++) 
    {
        var ch = Expression.substr(i, 1);
        var a = RefString.indexOf(ch, 0);
        if (a == -1)
        {
            alert('Inserire un valore numerico nel campo ' + nomeCampo +'.');
            campo.value='';
            campo.focus();
        	return false;
        }
    }
    return(true);
}

function isIntPositivo(Expression,nomeCampo,campo)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789";
    
    if (Expression.length < 1) 
        return (false);
    
    for (var i = 0; i < Expression.length; i++) 
    {
        var ch = Expression.substr(i, 1);
        var a = RefString.indexOf(ch, 0);
        if (a == -1)
        {
            alert('Inserire un intero positivo nel campo ' + nomeCampo +'.');
            campo.value='';
            campo.focus();
        	return false;
        }
    }
    return(true);
}

function isVoidOrIntPositivo(Expression,nomeCampo,campo)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789";
    
    if (Expression.length < 1) 
        return (true);
    
    for (var i = 0; i < Expression.length; i++) 
    {
        var ch = Expression.substr(i, 1);
        var a = RefString.indexOf(ch, 0);
        if (a == -1)
        {
            alert('Inserire un intero positivo nel campo ' + nomeCampo +'.');
            campo.value='';
            campo.focus();
        	return false;
        }
    }
    return(true);
}

function testFloating(str) {    
    return /^[+]?[0-9]+([\.][0-9]+)?$/.test(str);
}

function isDecimalePositivo(Expression,nomeCampo,campo)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789";
    
    if (Expression.length < 1) 
        return (false);
    
    if( !testFloating( Expression ) )
    {
        alert('Inserire un decimale positivo nel campo ' + nomeCampo +' usando il punto come separatore.');
        campo.value='';
        campo.focus();
    	return false;
    }
    
    return(true);
}

function toggleDiv( idDiv )
{
	var value = document.getElementById( idDiv );

	if( value != undefined )
	{
		if( value.style.display == "none" )
		{
			value.style.display = "block";
			protect( value, false );
		}
		else
		{
			value.style.display = "none";
			protect( value, true );
		}
	}
};

function toggleDivMantello( idDiv )
{
	var value = document.getElementById( idDiv );
	
	if( value != undefined )
	{
		if( value.style.display == "none" )
		{
			value.style.display = "block";
			for(var i=0;i<value.childNodes.length;i++)
			{
				if(value.childNodes[i].tagName!=undefined && value.childNodes[i].tagName=="select" && value.childNodes[i].Get("name")=="mantello")
				{
					value.childNodes[i].disabled=false;
					break;
				}
			}
			protect( value, false );
		}
		else
		{
			value.style.display = "none";
			for(var i=0;i<value.childNodes.length;i++)
			{
				if(value.childNodes[i].tagName!=undefined && value.childNodes[i].tagName=="select" && value.childNodes[i].Get("name")=="mantello")
				{
					value.childNodes[i].disabled=disabled;
					break;
				}
			}
			protect( value, true );
		}
	}
};

function toggleDivMantello2( idDiv )
{
	var value = document.getElementById( idDiv );
	
	if( value != undefined )
	{
		if( value.style.display == "none" )
		{
			value.style.display = "block";
			for(var i=0;i<value.childNodes.length;i++)
			{
				if(value.childNodes[i].tagName!=undefined && value.childNodes[i].tagName=="select" && value.childNodes[i].Get("name")=="mantello")
				{
					value.childNodes[i].disabled=false;
					break;
				}
			}
			protect( value, false );
		}
	}
};

function protect(anObject, protection) {
	 if (anObject == null) {
	  return true;
	 } // if (anObject == null)
	 var members = anObject.children.length;
	 var i = 0;
	 for (i=0;i<members;i++) {
	  var curObject = anObject.children.item(i);
	  if (curObject != null) {
	   protect(curObject,protection);
		} // if (curObject != null)
	 } // for (i=0;i<=members;i++)
	 anObject.disabled = protection;
	 return true;
}; // function protect(anObject, protection)
	
	
function attachEvent(name,elementName,callBack) {
    var element = elementName;
    if(typeof elementName == 'string') {
      element = document.getElementById(elementName);
    }
    if (element.addEventListener) {
      element.addEventListener(name, callBack,false);
    } else if (element.attachEvent) {
      element.attachEvent('on' + name, callBack);
    }
};

function maxLength()
 {
 
       var field=  event != null ? event.srcElement:e.target;
       if(field.maxChars  != null) {  
         if(field.value.length >= parseInt(field.maxChars)) {
           event.returnValue=false; 
           //alert("more than " +field.maxChars + " chars");
           return false;
         }
       }
 }  

 function maxLengthPaste()
 {
       event.returnValue=false;
       var field=  event != null ? event.srcElement:e.target;
       if(field.maxChars != null) {
         if((field.value.length +  window.clipboardData.getData("Text").length) > parseInt(field.maxChars)) {
           //alert("more than " +field.maxChars + " chars");
           return false;
         }
       }
       event.returnValue=true;
 }
 
 function setTextAreaListner(eve,func) {
	 if( document.forms != undefined && document.forms[0] != undefined )
	 {
		   var ele = document.forms[0].elements;
		   for(var i = 0; i <ele.length;i++) {
		    element = ele[i];
		    if (element.type) {
		      switch (element.type) {
		        case 'textarea':
		        attachEvent(eve,element,func);
		       }
		     }
		  }
	 }
	 else
	 {
		 
	 }
	}
 

function isFuturo(input) 
{
	var oggi = new Date();
	oggi.setHours(0,0,0,0);

	var arrInput  = input.split("/");

	var inputDate = new Date(arrInput[2],arrInput[1]-1,arrInput[0]);

	var inputTime = inputDate.getTime();
	var oggiTime  = oggi.getTime();

	if (inputTime<=oggiTime) 
		return false;
	else 
		return true;	
}


function confrontaDate(data1,data2) 
{

var arr1 = data1.split("/");
var arr2 = data2.split("/");

var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);

var r1 = d1.getTime();
var r2 = d2.getTime();

if (r1<r2)
	return -1;
else if(r1==r2)
	return 0;
else
	return 1;
}
 
 
 jQuery(document).ready(function(){
	 setTextAreaListner( "keypress", maxLength );
	 setTextAreaListner( "paste", maxLength );
 });
 
 
 
 function controllaDataFutura(data){
		
		var d = new Date();
		var gg = d.getDate();
		var mm = d.getMonth()+1;
		var yy = d.getFullYear();
		
		var arr = data.split("/");
		var status = 0;
		if (arr[2]>yy) {
			status = 1;
		}
		
		if (arr[2]==yy && arr[1]>mm) {
			status = 1;
		}
		
		if (arr[2]==yy && arr[1]==mm && arr[0]>gg){
			status = 1;
		}
		return status; //0 data OK ----- 1 data FUTURA
	}
 
 
 function controllaDataAnnoCorrente(campo,nome){


     var currentTime = new Date();
     var year = currentTime.getFullYear();


     var data = campo.value;
     var data_limite = '01/01/2015';
     var data_limite2 = '01/01/2015';

     var arr1 = data.split('/');
     var arr2 = data_limite.split('/');
     var arr3 = data_limite2.split('/');

     var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
     var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
     var d3 = new Date(arr3[2],arr3[1]-1,arr3[0]);
     var oggi = new Date();

     var r1 = d1.getTime();
     var r2 = d2.getTime();
     var r3 = d3.getTime();
     var r4 = oggi.getTime();
//   alert(r1);
//   alert(r2);
//   alert(r3);
//   alert(r4);
     if(r4>=r1 && r4<=r3)
     {
     var anno1 = parseInt(campo.value.substr(6),10);
     //alert(anno1);
     //alert(year);
     if (year!=anno1){
                     alert(nome + " deve essere nell'anno corrente ("+year+")" );
                     return false;
             }
             }
     return true;
}
 
 function controllaDataAnnoCorrente2(campo){


     var currentTime = new Date();
     var year = currentTime.getFullYear();


     var data = campo;
     var data_limite = '01/01/2015';
     var data_limite2 = '01/01/2015';

     var arr1 = data.split('/');
     var arr2 = data_limite.split('/');
     var arr3 = data_limite2.split('/');

     var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
     var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
     var d3 = new Date(arr3[2],arr3[1]-1,arr3[0]);
     var oggi = new Date();

     var r1 = d1.getTime();
     var r2 = d2.getTime();
     var r3 = d3.getTime();
     var r4 = oggi.getTime();
     if(r4>=r1 && r4<=r3)
     {
    	 var anno1 = parseInt(campo.substr(6),10);
	     if (year!=anno1)
	     {
	         return false;
	     }
     }
     return true;
}