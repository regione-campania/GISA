/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//DD Tab Menu- Script rewritten April 27th, 07: http://www.dynamicdrive.com
//**Updated Feb 23rd, 08): Adds ability for menu to revert back to default selected tab when mouse moves out of menu

//Only 2 configuration variables below:

var ddtabmenu={
	disabletablinks: false, //Disable hyperlinks in 1st level tabs with sub contents (true or false)?
	snap2original: [true, 300], //Should tab revert back to default selected when mouse moves out of menu? ([true/false, delay_millisec]

	currentpageurl: window.location.href.replace("http://"+window.location.hostname, "").replace(/^\//, ""), //get current page url (minus hostname, ie: http://www.dynamicdrive.com/)

definemenu:function(tabid, dselected){
	this[tabid+"-menuitems"]=null;
	this[tabid+"-dselected"]=-1;
	this.addEvent(window, function(){ddtabmenu.init(tabid, dselected);}, "load");
},

showsubmenu:function(tabid, targetitem){
	var menuitems=this[tabid+"-menuitems"];
	this.clearrevert2default(tabid);
 for (i=0; i<menuitems.length; i++){
		menuitems[i].className="";
		if (typeof menuitems[i].hasSubContent!="undefined")
			document.getElementById(menuitems[i].getAttribute("rel")).style.display="none";
	}
	targetitem.className="current"
	if (typeof targetitem.hasSubContent!="undefined")
		document.getElementById(targetitem.getAttribute("rel")).style.display="block";
},

isSelected:function(menuurl){
	var menuurl=menuurl.replace("http://"+menuurl.hostname, "").replace(/^\//, "");
	return (ddtabmenu.currentpageurl==menuurl);
},

isContained:function(m, e){
	var e=window.event || e
	var c=e.relatedTarget || ((e.type=="mouseover")? e.fromElement : e.toElement);
	while (c && c!=m)try {c=c.parentNode;} catch(e){c=m;}
	if (c==m)
		return true
	else
		return false
},

revert2default:function(outobj, tabid, e){
	if (!ddtabmenu.isContained(outobj, tabid, e)){
		window["hidetimer_"+tabid]=setTimeout(function(){
			ddtabmenu.showsubmenu(tabid, ddtabmenu[tabid+"-dselected"]);
		}, ddtabmenu.snap2original[1]);
	}
},

clearrevert2default:function(tabid){
 if (typeof window["hidetimer_"+tabid]!="undefined")
		clearTimeout(window["hidetimer_"+tabid]);
},

addEvent:function(target, functionref, tasktype){ //assign a function to execute to an event handler (ie: onunload)
	var tasktype=(window.addEventListener)? tasktype : "on"+tasktype;
	if (target.addEventListener)
		target.addEventListener(tasktype, functionref, false);
	else if (target.attachEvent)
		target.attachEvent(tasktype, functionref);
	//Se l'evento è onload e non esiste addEventListner(caso IE 8.0), usiamo il metodo window.onload perchè il metodo 
	//attachEvent(usato in sostituzione dell'addEventListner) pare che non abbia effetto con l'onload
	if(tasktype=="onload" && !target.addEventListener)
		target.onload=functionref;
},

init:function(tabid, dselected){
	var menuitems=document.getElementById(tabid).getElementsByTagName("a")
	this[tabid+"-menuitems"]=menuitems;
	for (var x=0; x<menuitems.length; x++){
		if (menuitems[x].getAttribute("rel")){
			this[tabid+"-menuitems"][x].hasSubContent=true;
			if (ddtabmenu.disabletablinks)
				menuitems[x].onclick=function(){return false;};
			if (ddtabmenu.snap2original[0]==true){
				var submenu=document.getElementById(menuitems[x].getAttribute("rel"));
				menuitems[x].onmouseout=function(e){ddtabmenu.revert2default(submenu, tabid, e);};
				submenu.onmouseover=function(){ddtabmenu.clearrevert2default(tabid);};
				submenu.onmouseout=function(e){ddtabmenu.revert2default(this, tabid, e);};
			}
		}
		else //for items without a submenu, add onMouseout effect
			menuitems[x].onmouseout=function(e){this.className=""; if (ddtabmenu.snap2original[0]==true) ddtabmenu.revert2default(this, tabid, e);};
		menuitems[x].onmouseover=function(){ddtabmenu.showsubmenu(tabid, this);};
		if (dselected=="auto" && typeof setalready=="undefined" && this.isSelected(menuitems[x].href)){
			ddtabmenu.showsubmenu(tabid, menuitems[x]);
			this[tabid+"-dselected"]=menuitems[x];
			var setalready=true;
		}
		else if (parseInt(dselected)==x){
			ddtabmenu.showsubmenu(tabid, menuitems[x]);
			this[tabid+"-dselected"]=menuitems[x];
		}
	}
}
}