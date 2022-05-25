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
    return /^[+]?[0-9]+([\.,][0-9]+)?$/.test(str);
}

function isDecimalePositivo(Expression,nomeCampo,campo)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789";
    
    if (Expression.length < 1) 
        return (false);
    
    if( !testFloating( Expression ) )
    {
        alert('Inserire un decimale positivo nel campo ' + nomeCampo +'.');
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
 
 jQuery(document).ready(function(){
	 setTextAreaListner( "keypress", maxLength );
	 setTextAreaListner( "paste", maxLength );
 });
 