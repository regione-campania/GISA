<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php 
session_start();
$id_asl = $_SESSION['id_asl'];
//$id_asl = -1;
if ($id_asl == null)
	$id_asl = -1;	
$id_asl = $id_asl - 200;

?>

<!DOCTYPE html>
<meta charset="utf-8">
<style>
/* Center the loader */
    #loader {
        position: absolute;
        left: 50%;
        top: 50%;
        z-index: 999;
        width: 150px;
        height: 150px;
        margin: -75px 0 0 -75px;
        border: 16px solid #f3f3f3;
        border-radius: 50%;
        border-top: 16px solid #3498db;
        width: 120px;
        height: 120px;
        -webkit-animation: spin 2s linear infinite;
        animation: spin 2s linear infinite;
    }

    @-webkit-keyframes spin {
        0% { -webkit-transform: rotate(0deg); }
        100% { -webkit-transform: rotate(360deg); }
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

    /* Add animation to "page content" */
    .animate-bottom {
        position: relative;
        -webkit-animation-name: animatebottom;
        -webkit-animation-duration: 1s;
        animation-name: animatebottom;
        animation-duration: 1s
    }

    @-webkit-keyframes animatebottom {
        from { bottom:-100px; opacity:0 }
        to { bottom:0px; opacity:1 }
    }

    @keyframes animatebottom {
        from{ bottom:-100px; opacity:0 }
        to{ bottom:0; opacity:1 }
    }

input { 
    text-align: right; 
}

div.tooltip {
        overflow: auto;
        position: absolute;
        text-align: center;
        /*width: 100px;					
        height: 40px;*/
        padding: 2px;
        id: tooltip;
        font: 12px sans-serif;
        background: lightsteelblue;
        border: 0px;
        border-radius: 8px;
        pointer-events: none;
    }

.bigText {
    height:30px;
}

select{
	width:300px;
   }
   

.node rect {
  cursor: pointer;
  fill: #fff;
  fill-opacity: 0.5;
  stroke: #3182bd;
  stroke-width: 1.5px;
}

.node text {
  font: 10px sans-serif;
  pointer-events: none;
}


.link {
  fill: none;
  opacity: 0;
  stroke: #9ecae1;
  stroke-width: 1.5px;
}

valori1 {
	  overflow: auto;

	position: fixed;
	top: 30px;
	left: 790px;
	width: 700px;
	height: auto;
	padding: 5px;
	background-color: white;
	opacity: 0;
}

label{
	font-family: sans-serif;
	font-size: 13px;
}

valori1 p {
	opacity: 1;
	margin: 0;
	font-family: sans-serif;
	font-size: 13px;
	line-height: 16px;
}
	
valori2 {
 position: fixed;
    top: 300px;
	left: 800px;
        width: 700px;
        height: auto;
        padding: 10px;
        background-color: white;
		opacity: 1;
    }

    valori2 p {
		opacity: 1;
        margin: 0;
        font-family: sans-serif;
        font-size: 15px;
        line-height: 16px;
    }
	
button.manuale{ 
z-index: 999;
 position: fixed;
    top: 6px;
	left: 1040px; 
}

button.mod4{ 
z-index: 999;
 position: fixed;
    top: 6px;
	left: 1120px; 
}
 
button.logout{ 
	z-index:999;
	position: fixed;
    top: 6px;
	left: 1225px; 
}


</style>

<head>
  <title>Formule UPS-UBA</title>
</head>

<body>
<script src="https://d3js.org/d3.v4.min.js"></script>
<script>


function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function getUrlParam(parameter, defaultvalue){
    var urlparameter = defaultvalue;
    if(window.location.href.indexOf(parameter) > -1){
        urlparameter = getUrlVars()[parameter];
        }
    return urlparameter;
}

//var id_stuttura_glob = getUrlParam("id", 8);
var id_stuttura_glob = getUrlParam("id", <?php echo $id_asl; ?>);

if (id_stuttura_glob == -201){
	id_stuttura_glob = 8;
}
console.log("ID STRUTTURA:  " + id_stuttura_glob )


ANNO_GLOB = 2022;

var up_once = true;
var once = true;
var once_text = true;
var locked = false;

var nodes;

var data_n = [];

//id_stuttura_glob = 576;
data_strutt_glob = null;
data_piani_glob = null;
f_uba_glob = null;
f_ups_glob = null;
var piani_glob = null;

var valori , targets, ups, uba;

var id_piano_glob = null;

var id_formula_glob = null;
var id_formule_glob = [];

var id_formulaUps_glob = null;
var id_formuleUps_glob = [];

var id_f_uba = null;
var id_f_ups = null;

var id_formulaUba_glob = null;
var id_formuleUba_glob = [];

var changed_uba = [];
var changed_ups = [];

 var div = d3.select("body").append("div")
                .attr("class", "tooltip")
                .style("opacity", 0);


var data_formule_glob = null;
var stutture_lenght_glob = null;
var target_glob = null;
var clicked = null;
var old_clicked;

var margin = {top: 40, right: 20, bottom: 30, left: 0},
    width = 800,
    barHeight = 20,
    barWidth = (width - margin.left - margin.right) * 0.58
	tagetNodeW = 48;

var i = 0,
    duration = 400,
    root,
	node;

var diagonal = d3.linkHorizontal()
    .x(function(d) { return d.y; })
    .y(function(d) { return d.x; });
	
	
var ambiente = d3.select("body").append("div")
	.style("font-size", "10px")
	.style("font-family", "sans-serif")
	 .style("white-space", "nowrap");
	 
getAmbiente();
	
	
var ASL = d3.select("body").append("div")
	.style("font-size", "20px")
	.style("font-family", "sans-serif")
	 .style("white-space", "nowrap");
	 
var logout = d3.select("body")
	 .append("button")
	.attr("class", "logout")
	 .text("Logout")
	.on("click" ,function (d) {
        window.location = "logout.php";
    });
	
var manuale = d3.select("body")
	 .append("button")
	.attr("class", "manuale")
	 .text("Manuale")
	.on("click" ,function (d) {
        window.open("manuale_f.php");
    });
	
/*var mod4 = d3.select("body")
	 .append("button")
	.attr("class", "mod4")
	 .text("Valori mod. 4")
	.on("click" ,function (d) {
        window.open("mod4.php");
    });
*/

var svg = d3.select("body").append("svg")
    .attr("width", width) // + margin.left + margin.right)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.json("struttura_parent_json.php?id_struttura="+id_stuttura_glob+"&anno="+ANNO_GLOB, function(error, data) {
	  if (error) throw error;
	  data = data["strutt"];
	  console.log(data);
	  dpar = " - DPAR";
	  if (data[0].descr == 'Regione Campania' )
		data[0].descr = data[0].descr + dpar;
	  ASL.text(data[0].descr);
	  parent_glob = data[0].parent;
	  document.title = document.title + " - " + data[0].descr;
	});



d3.json("tree_json_2019.php?&id_struttura="+id_stuttura_glob+"&anno="+ANNO_GLOB, function(error, data) {
  if (error) throw error;
  //_data = data[0].get_struttura_piani_tree;
  //_data = _data.substring(1, _data.length-1);
  //_data = '{ "name": "flare", "children": [ ' + _data + '] }'
  //console.log(JSON.parse(data))
  //console.log(data);
  _json = JSON.parse(data);
  console.log(_json)
  data_piani_glob = _json;
  root = d3.hierarchy(_json);
  root.x0 = 0;
  root.y0 = 0;
  

  nodes = root.descendants();
  nodes_glob = nodes;
  console.log("NODI");
  console.log(nodes);
  console.log("ROOT");
  console.log(root);
  for (i=1; i<nodes.length; i++){
	nodes[i]._children = nodes[i].children;
	nodes[i].children = null;
  }  
  update(root);
});

d3.json("formule_ups_json.php", function(error, data) {
  drawFormule(data, 'ups');
});

d3.json("formule_uba_json.php", function(error, data) {
  drawFormule(data, 'uba');
});


function updateTree(){
	d3.json("tree_json_2019.php?&id_struttura="+id_stuttura_glob+"&anno="+ANNO_GLOB, function(error, data) {
	if (error) throw error;
	_json = JSON.parse(data);
	data_u = d3.hierarchy(_json);
	data_n = data_u.descendants();
	console.log("Aggiorno")
	console.log(data_n)
	data_piani_glob = _json;
	newValues();
	})
	
	
	d3.select("div#loader").style("display", "none");
}

function update(source) {

  // Compute the flattened node list.

   nodes = root.descendants();
	

  var height = Math.max(500, nodes.length * barHeight + margin.top + margin.bottom);

  d3.select("svg").transition()
      .duration(duration)
      .attr("height", height);

  d3.select(self.frameElement).transition()
      .duration(duration)
      .style("height", height + "px");

  // Compute the "layout". TODO https://github.com/d3/d3-hierarchy/issues/67
  var index = -1;
  root.eachBefore(function(n) {
    n.x = ++index * barHeight;
    n.y = n.depth * 10;
  });
  
  if(once_text){
  tar_val = svg.append("text")
	.text("Progr.")
	.style("font-family", "sans-serif")
	.style("font-size","11px")
	.style("font-weight", "bold")
	.style("text-anchor", "middle")
	.attr("x", barWidth+(tagetNodeW/2))
	.attr("y",-20);
	
	val_lab = svg.append("text")
		.text("Distribuiti")
		.style("font-family", "sans-serif")
		.style("font-size","11px")
		.style("font-weight", "bold")
	    .style("text-anchor", "middle")
		.attr("x", barWidth+tagetNodeW+(tagetNodeW/2))
		.attr("y",-20);
		
	 lab_ups = svg.append("text")
	.text("UPS")
	.style("font-family", "sans-serif")
	.style("font-size","11px")
	.style("font-weight", "bold")
	.style("text-anchor", "middle")
	.attr("x", barWidth+tagetNodeW*2 + (tagetNodeW/2))
	.attr("y",-20);
	
	lab_uba = svg.append("text")
		.text("UBA")
		.style("font-family", "sans-serif")
		.style("font-size","11px")
		.style("font-weight", "bold")
	    .style("text-anchor", "middle")
		.attr("x", barWidth+tagetNodeW*3+(tagetNodeW/2))
		.attr("y",-20);
		
		
	lab_ups_target = svg.append("text")
	.text("UPS")
	.style("font-family", "sans-serif")
	.style("font-size","11px")
	.style("font-weight", "bold")
	.style("text-anchor", "middle")
	.attr("x", barWidth+tagetNodeW*4 + (tagetNodeW/2))
	.attr("y",-20);
	
	lab_uba_target = svg.append("text")
		.text("UBA")
		.style("font-family", "sans-serif")
		.style("font-size","11px")
		.style("font-weight", "bold")
	    .style("text-anchor", "middle")
		.attr("x", barWidth+tagetNodeW*5+(tagetNodeW/2))
		.attr("y",-20);
		
	lab_ups = svg.append("text")
	.text("Distrib.")
	.style("font-family", "sans-serif")
	.style("font-size","11px")
	.style("font-weight", "bold")
	.style("text-anchor", "middle")
	.attr("x", barWidth+tagetNodeW*2 + (tagetNodeW/2))
	.attr("y",-30);
	
	lab_uba = svg.append("text")
		.text("Distrib.")
		.style("font-family", "sans-serif")
		.style("font-size","11px")
		.style("font-weight", "bold")
	    .style("text-anchor", "middle")
		.attr("x", barWidth+tagetNodeW*3+(tagetNodeW/2))
		.attr("y",-30);
		
	lab_ups_target = svg.append("text")
	.text("Progr.")
	.style("font-family", "sans-serif")
	.style("font-size","11px")
	.style("font-weight", "bold")
	.style("text-anchor", "middle")
	.attr("x", barWidth+tagetNodeW*4 + (tagetNodeW/2))
	.attr("y",-30);
	
	lab_uba_target = svg.append("text")
		.text("Progr.")
		.style("font-family", "sans-serif")
		.style("font-size","11px")
		.style("font-weight", "bold")
	    .style("text-anchor", "middle")
		.attr("x", barWidth+tagetNodeW*5+(tagetNodeW/2))
		.attr("y",-30);	
	
	once_text = false;
	}
  // Update the nodes�
   node = svg.selectAll(".node")
    .data(nodes, function(d) { return d.id; });
	
  var nodeEnter = node.enter().append("g")
      .attr("class", "node")
      .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
      .style("opacity", 0);

  // Enter any new nodes at the parent's previous position.
  
  			
  piani_glob = nodeEnter.append("rect")
      .attr("y", -barHeight / 2)
      .attr("height", barHeight)
      .attr("width", barWidth)
	.style("fill", function(d){ if (d.data.id_piano == id_piano_glob)
									return "blue"
								if(d.data.color != "") 
									return d.data.color
								return "#F0F0F0";})
	  .attr("id_piano", function(d){ return d.data.id_piano;})
	  .attr("fattore_uba", function(d){ return d.data.fattore_uba_reale;})
	  .attr("fattore_ups", function(d){ return d.data.fattore_ups_reale;})
	  .attr("id_formula_ups", function(d){ if (changed_ups[d.data.count] != null) return changed_ups[d.data.count];  return d.data.id_formula_ups;})
	  .attr("id_formula_uba", function(d){ if (changed_uba[d.data.count] != null) return changed_uba[d.data.count];  return  d.data.id_formula_uba;})
	  .attr("target", function(d){ return d.data.target;})
	  .attr("id",  function(d){ return "rect"+d.data.id_piano;})
      .on("click", click)
	  .on("mouseover", function (d) {
			if (d.data.name.length >= 90){
				div.transition()
					.style("opacity", .9);

				div.html(d.data.name)
					.style("left", (d3.event.pageX) + "px")
					.style("top", (d3.event.pageY - 28) + "px");
			}
		})
		.on("mouseout", function (d) {
			div.transition()
				.style("opacity", 0);
		});

   targetNode = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("x", barWidth)
		.attr("height", barHeight)
		.attr("width", tagetNodeW)
		.style("fill", "white")
		
	valoreNode = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("x", barWidth+tagetNodeW)
		.attr("height", barHeight)
		.attr("width", tagetNodeW)
		.style("fill", function(d){
			if (Math.round(d.data.valore) == Math.round(d.data.target) && d.data.case == 1)
				return "#ffcc00";
			else if (Math.round(d.data.valore) == Math.round(d.data.target) && d.data.case == 0)
				return "#00CC00";
			else if (Math.round(d.data.valore) > Math.round(d.data.target))
				return " #ffcc00";
			return "#C80000";
		})
		.attr("id", function(d){return "valoreNode"+d.data.id_piano;})

		
	upsNode = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("x", barWidth+tagetNodeW*2)
		.attr("height", barHeight)
		.attr("width", tagetNodeW)
		.style("fill", "white")
		
	ubaNode = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("x", barWidth+tagetNodeW*3)
		.attr("height", barHeight)
		.attr("width", tagetNodeW)
		.style("fill", "white")
		
		
	upsTargetNode = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("x", barWidth+tagetNodeW*4)
		.attr("height", barHeight)
		.attr("width", tagetNodeW)
		.style("fill", "white")
		
	ubaTargetNode = nodeEnter.append("rect")
		.attr("y", -barHeight / 2)
		.attr("x", barWidth+tagetNodeW*5)
		.attr("height", barHeight)
		.attr("width", tagetNodeW)
		.style("fill", "white")

  nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", 0.5)
	  .style("overflow", "hidden")
	  .style("font-size","9px")
	  .attr("id_piano", function(d) { return d.data.id_piano; })
	  .text(function(d) { 
			if (d.data.level == 0)
				return "TOTALE";
			/*if(d.data.level == 1)
				return d.data.path.split("-").pop();
			if(d.data.level == 2)
				return d.data.path.split("-").pop();*/
			if (d.data.name.length < 82)
				return d.data.name.toUpperCase();
			return d.data.name.substring(0,82).toUpperCase() + '...'; })
      //.text(function(d) { return d.data.name + ' - TARGET: ' + d.data.target + ' - VALORE: ' + d.data.valore; });
	
	valori =  nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", barWidth+tagetNodeW +  (tagetNodeW/2) + tagetNodeW/2 -1)
	  .style("text-anchor", "end")
	  .style("font-size","9px")
		.style("font-weight", "bold")
	  .attr("id", function(d) { return "valore"+d.data.id_piano; })
	  .text(function(d) { return numberWithCommas(Math.round(d.data.valore)); });
	  
	targets =   nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", barWidth + (tagetNodeW/2)+ tagetNodeW/2 -1)
	  .style("text-anchor", "end")
	  .style("font-size","9px")
	  .attr("id", function(d) { return "target"+d.data.id_piano; })
	  .text(function(d) { return numberWithCommas(Math.round(d.data.target)); });
	  
	ups =  nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", barWidth+tagetNodeW * 2 +  (tagetNodeW/2)+ tagetNodeW/2 -1)
	  .style("text-anchor", "end")
	  .style("font-size","9px")
	  .attr("id", function(d) { return "ups_impegnati"+d.data.id_piano; })
	  .text(function(d) { return numberWithCommas(Math.round(d.data.ups_impegnati)); });
	  
	uba =   nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", barWidth + +tagetNodeW * 3 + (tagetNodeW/2)+ tagetNodeW/2 -1)
	  .style("text-anchor", "end")
	  .style("font-size","9px")
	  .attr("id", function(d) { return "uba_impegnati"+d.data.id_piano; })
	  .text(function(d) { return numberWithCommas(Math.round(d.data.uba_impegnati)); });
	  
	  
	ups_target =  nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", barWidth+tagetNodeW * 4 +  (tagetNodeW/2)+ tagetNodeW/2 -1)
	  .style("text-anchor", "end")
	  .style("font-size","9px")
	  .attr("id", function(d) { return "ups_target" + d.data.id_piano; })
	  .text(function(d) { return numberWithCommas(Math.round(d.data.ups_target)); });
	  
	uba_target =   nodeEnter.append("text")
      .attr("dy", 3.5)
      .attr("dx", barWidth + +tagetNodeW * 5 + (tagetNodeW/2)+ tagetNodeW/2 -1)
	  .style("text-anchor", "end")
	  .style("font-size","9px")
	  .attr("id", function(d) { return "uba_target" + d.data.id_piano; })
	  .text(function(d) { return numberWithCommas(Math.round(d.data.uba_target)); });
	  
	  
  // Transition nodes to their new position.
  nodeEnter.transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })
      .style("opacity", 1);

  node.transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })
      .style("opacity", 1)
    .select("rect")
     .style("fill", function(d){ if (d.data.id_piano == id_piano_glob)
									return "blue"
								if(d.data.color != "") 
									return d.data.color
								return "#F0F0F0";})

  // Transition exiting nodes to the parent's new position.
  node.exit().transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
      .style("opacity", 0)
      .remove();

  // Stash the old positions for transition.
  root.each(function(d) {
    d.x0 = d.x;
    d.y0 = d.y;
  });
  d3.select("div#loader").style("display", "none");
  locked = false;
  
  
  if (data_n.length){ //uso nuovi valori se aggiornati
	 setTimeout(function(d){newValues()}, 200)
	 newValues();
	}

}


function newValues(){
 //console.log("Nuovi valori trovati")
 
data_n.forEach(function(d){
	
		if (Math.round(d.data.valore) == Math.round(d.data.target) && d.data.case == 1)
				col = "#ffcc00";
			else if (Math.round(d.data.valore) == Math.round(d.data.target) && d.data.case == 0)
				col = "#00CC00";
			else if (Math.round(d.data.valore) > Math.round(d.data.target))
				col = " #ffcc00";
			else
				col = "#C80000";
	
		d3.select("#valoreNode"+d.data.id_piano)
		.style("fill", col)
		/*d3.select("#valoreNode"+d.data.id_piano)
		.style("fill", function(d){
			if(d.data.id_piano == id_piano_glob)
				console.log(id_piano_glob+" "+d.data.valore+ " " + d.data.target);
			if (Math.round(d.data.valore) == Math.round(d.data.target))
				return "#00CC00";
			return "#C80000";
		});*/

		d3.select("#valore"+d.data.id_piano)
		.text(numberWithCommas(Math.round(d.data.valore)))
		
		d3.select("#ups_impegnati"+d.data.id_piano).text(numberWithCommas(Math.round(d.data.ups_impegnati)))
		d3.select("#uba_impegnati"+d.data.id_piano).text(numberWithCommas(Math.round(d.data.uba_impegnati)))
		d3.select("#ups_target"+d.data.id_piano).text(numberWithCommas(Math.round(d.data.ups_target)))
		d3.select("#uba_target"+d.data.id_piano).text(numberWithCommas(Math.round(d.data.uba_target)))
		
			d3.select('#rect'+d.data.id_piano).attr("id_formula_ups",  d.data.id_formula_ups)
			d3.select('#rect'+d.data.id_piano).attr("id_formula_uba",  d.data.id_formula_uba)
		
	})
	//console.log("AGGIORNATO")
}

// Toggle children on click.
function click(d) {
	if(locked)
		return;
  if (typeof old_clicked != "undefined")
	old_clicked.style("fill",old_color);
  old_clicked = d3.select(this);
  old_color = d3.select(this).style("fill");
  
 /* for (i=0; i<data_formule_glob.length; i++){
		if(d3.select("#valori1").select("#descr".concat(i+1)).attr("id_formula") == id_formula_glob)
			d3.select("#valori1").select("#pcampo".concat(i+1)).select("img#img").style("opacity", 1);
		else
			d3.select("#valori1").select("#pcampo".concat(i+1)).select("img#img").style("opacity", 0);

	}*/

  d3.select(this).style("fill", "blue");
  
  piano = d3.select(this).attr("id_piano");
  console.log("Hai cliccato il piano: " + d3.select(this).attr("id_piano"));
  
  f_uba_glob = d3.select(this).attr("fattore_uba");
  f_ups_glob = d3.select(this).attr("fattore_ups");
  
   id_f_ups = d3.select(this).attr("id_formula_ups");
   id_f_uba = d3.select(this).attr("id_formula_uba");
   
   d3.select('#ups').property('value', id_f_ups);
   d3.select('#uba').property('value', id_f_uba);


  

  target_glob = d3.select(this).attr("target");

  if (f_uba_glob == "")
		f_uba_glob = 0;
		
  if (f_ups_glob == "")
		f_ups_glob = 0;
		
  console.log("con formule ups " + id_f_ups + ' uba ' + id_f_uba);
  console.log("con fattori ups " + f_ups_glob + ' uba ' + f_uba_glob);

  //-----------------------------------
  // console.log(d3.select(this)._groups[0][0].__data__.data);
  piano = d.data.name;
  if (d.data.name == "")
	piano = d3.select(this)._groups[0][0].__data__.data.path.split("-").pop()
  d3.select("#valori1").select("#ppiano")
	.text("Selezionato: " + piano) //+ " id " + d.data.id_piano);
  id_piano_glob = d.data.id_piano;
  id_formula_glob = d.data.id_formula;
  
  
  
  
   if (typeof d.data.children != "undefined" && locked == false) {
	  locked = true;
	  console.log(d)
	  if (d.children ) {
		d._children = d.children;
		d.children = null;
	  } else {
		d.children = d._children;
		d._children = null;
	  }
	  update(d);
	  }
  
 
	d3.select("#valori1")
		.transition().duration(200)
		.style("transform","scale(0.5)")
		.style("opacity", 0)
	d3.select("#valori1")
	.transition().delay(500).duration(500)
	.style("transform","scale(1)")
	.style("opacity", 1)



}

function drawFormule(_data, type){
	//if(type == 'ups'){
		data = _data["json"];
		data_formule_glob = data;
		console.log(data);
		
		if(type == 'ups')
			id_formuleUps_glob = [];
		else if (type == 'uba')
			id_formuleUba_glob = [];
		
		for (i=0; i<data.length ; i++){
			
			if(id_formuleUps_glob.indexOf(data[i].id) == -1 && type == 'ups')
				id_formuleUps_glob.push(data[i].id); 
			if(id_formuleUba_glob.indexOf(data[i].id) == -1 && type == 'uba')
				id_formuleUba_glob.push(data[i].id); 

			
			d3.select("#valori1").select("select#"+type) //select in alto
			.append('option')
			.attr('value', function (d) {
			  return data_formule_glob[i].id;
			})
			.text(function (d) {
			  return data_formule_glob[i].descr +": "+data_formule_glob[i].testo;
			})	
			
			
			if(id_formule_glob.indexOf(data[i].id) == -1){ //select in basso
				id_formule_glob.push(data[i].id); 
				d3.select("#valori1").select("#formula")
				.append('option')
				.attr('value', function (d) {
				  return data_formule_glob[i].testo;
				})
				.text(function (d) {
				  return data_formule_glob[i].descr +": "+data_formule_glob[i].testo;
				})
				.attr("descr",function(d){
						  return data_formule_glob[i].descr;
				})
			}
		}
		
		console.log(id_formule_glob);
		console.log("SELECTION "+ type)
		console.log(d3.select("#valori1").select("select#"+type));
		
}


function color(d) {
  
  if (d.data.id_piano == id_piano_glob)
	return "blue";
  return d._children ? "orange" : d.children ? "orange" : "yellow";
}


function getAmbiente(){
	const Http = new XMLHttpRequest();
		const url='ambiente.php';
		console.log(url);
		Http.open("GET", url);
		Http.send();
		Http.onreadystatechange=(e)=>{
			if (Http.readyState == 4 && Http.status == 200){
				console.log(Http.responseText);
				 ambiente
					.text(Http.responseText)
					.style("color", "red")
					.style("font-weight", "bold")
				if(Http.responseText == 'SVILUPPO')
					d3.select("p#log").style("visibility", '');
			}
		}
}

	
function onlyUnique(value, index, self) { 
		return self.indexOf(value) === index;
}
	
	
d3.select("body").on('keydown', function () {
	console.log(d3.event)
	try{
	startDesc = d3.event.path[0].id.startsWith("descr");
	}catch{
	 startDesc = d3.event.srcElement.id.startsWith("descr");
	}
	
	try{
	startTesto = d3.event.path[0].id;
	}catch{
	 startTesto = d3.event.srcElement.id;
	}
	if(startDesc && d3.event.keyCode == 13) {
		try{
			i = d3.event.path[0].id.slice(-1);
		}catch{
			i = d3.event.srcElement.id.slice(-1);
		}
		new_val = d3.event.target.value;
		d3.select("#valori1").select("#descr".concat(i)).attr("value", new_val);
		
	}else if(startTesto == "testoUps"){ //testo formula nuva
		setTimeout(function(){
			//new_val = d3.event.target.value;
			new_val = d3.select("#valori1").select("#testoUps").property("value")
			//d3.select("#valori1").select("#testoSimu").attr("value", new_val)
			if(new_val.toUpperCase().indexOf("UBA") > -1 ){
				d3.select("#valori1").select("#ubaSimu").property("disabled", false)
				d3.select("#valori1").select("input#ubaCheck").property('checked', false).property("disabled", true);
			}else{
				d3.select("#valori1").select("#ubaSimu").property("disabled", true)
				d3.select("#valori1").select("input#ubaCheck").property("disabled", false);
			}
			UBA = parseInt(d3.select("#valori1").select("#ubaSimu").property("value"));
			VALORE = parseInt(d3.select("#valori1").select("#valSimu").property("value"))
			console.log(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
			ev = eval(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
			d3.select("#valori1").select("#finalSimu").attr("value", ev)
			
		}, 50);
	}else if(startTesto == "valSimu" || startTesto == "ubaSimu"){
		setTimeout(function(){
			UBA = parseInt(d3.select("#valori1").select("#ubaSimu").property("value"));
			VALORE = parseInt(d3.select("#valori1").select("#valSimu").property("value"))
			console.log(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
			ev = eval(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
			d3.select("#valori1").select("#finalSimu").attr("value", ev)
		}, 50);	
	}
});
	
function Add(){
	console.log("ADD");
	d3.select("label#descrFormulaLab")
	.style("display", "");
	d3.select("label#testo")
	.style("display", "");
	d3.select("label#upsCheck")
	.style("display", "");
	d3.select("label#ubaCheck")
	.style("display", "");
	d3.select("input#upsCheck")
	.style("display", "");
	d3.select("input#ubaCheck")
	.style("display", "");
	d3.select("input#testoUps")
	.style("display", "");
	d3.select("input#descrFormula")
	.style("display", "");
	d3.select("button#confirm")
	.style("display", "");
	
}

function modifyFormula(){
	console.log(d3.select("#valori1").select("#formula").property("value"));
	sel = d3.select("#valori1").select("#formula").property("value")
	d3.select("#valori1").select("#testoUps").property("value", sel)
	
	console.log(d3.select("#valori1").select("#formula"));
	index = d3.select("#valori1").select("#formula")._groups[0][0].selectedIndex;
	descr = d3.select("#valori1").select("#formula")._groups[0][0][index].label.split(':')[0];
	console.log(descr)
	d3.select("#valori1").select("#descrFormula").attr("value", descr)
	
	console.log("id formula selez: " + id_formule_glob[index-1]);
	id_formula_glob = id_formule_glob[index-1];
	
	if(sel == "Nuova formula"){
		d3.select("#valori1").select("#testoUps").property("value", "")
		d3.select("#valori1").select("#descrFormula").property("value", "")
		id_formula_glob = null;
	}
	if(sel.toUpperCase().indexOf("UBA") > -1 ){
		d3.select("#valori1").select("#ubaSimu").property("disabled", false)
		d3.select("#valori1").select("input#ubaCheck").property('checked', false).property("disabled", true);
	}else{
		d3.select("#valori1").select("#ubaSimu").property("disabled", true)
		d3.select("#valori1").select("input#ubaCheck").property("disabled", false);
	}
}


function changeFormula(type){
	
	//console.log(d3.select("#valori1").select("#formula"));
	indexUba = d3.select("#valori1").select("#uba")._groups[0][0].selectedIndex;
	indexUps = d3.select("#valori1").select("#ups")._groups[0][0].selectedIndex;
	
	console.log(indexUba);
	console.log(indexUps);
	
	id_formulaUps_glob = id_formuleUps_glob[indexUps-1];
	id_formulaUba_glob = id_formuleUba_glob[indexUba-1];

	console.log("id formule ups e uba " + id_formulaUps_glob + " " + id_formulaUba_glob);
	
	if(indexUps == 0){
		d3.select("#valori1").select("#testoUps").property("value", "")
		d3.select("#valori1").select("#descrFormula").property("value", "")
		id_formulaUps_glob = null;
	}
	if(indexUba == 0){
		d3.select("#valori1").select("#testoUps").property("value", "")
		d3.select("#valori1").select("#descrFormula").property("value", "")
		id_formulaUba_glob = null;
	}
}


function Conferma(){

	if(d3.select("#valori1").select("#descrFormula").property("value") == ""){
		alert("Aggiungere una descrizione!");
			return;
	}
	

	
	if(d3.select("#valori1").select("#testoUps").property("value").toUpperCase().indexOf("VALORE") < 0){
		alert("La variabile 'VALORE' deve comparire nella formula!");
		return;
	}

	//test
	UBA = 1; VALORE = 1000;
	fattore_test1 = eval(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
	UBA = 1; VALORE = 2000;
	fattore_test2 = eval(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
	if(fattore_test2/fattore_test1 != 2 && fattore_test1 != 0){
		alert("Formato formula non valido!");
		return;
		}
		
	

	d3.select("div#loader").style("display", "");
    if(d3.select("#valori1").select("#formula").property("value") == "Nuova formula")
		id_f = null;
	else
		id_f = id_formula_glob;
		
	console.log(id_f)
	
	descr_f = d3.select("#valori1").select("#descrFormula").property("value")
	testo_f = d3.select("#valori1").select("#testoUps").property("value")
	contiene_uba = d3.select("#valori1").select("#testoUps").property("value").toUpperCase().indexOf("UBA") > -1
	valida_uba = d3.select("#valori1").select("input#ubaCheck").property("checked")
	valida_ups = d3.select("#valori1").select("input#upsCheck").property("checked")
	UBA = 1; VALORE = 1;
	fattore_fin = eval(d3.select("#valori1").select("#testoUps").property("value").toUpperCase());
	
	const Http = new XMLHttpRequest();
	const url='update_formula.php?id_f='+id_f+'&descr_f='+descr_f+'&testo_f='+testo_f+
				'&contiene_uba='+contiene_uba+'&valida_uba='+valida_uba+'&valida_ups='+valida_ups+'&fattore_fin='+fattore_fin;
	
	console.log(url);
	Http.open("GET", url);
	Http.send();
	Http.onreadystatechange=(e)=>{
		if (Http.readyState == 4 && Http.status == 200){
			d3.select("div#loader").style("display", "none");
			var response = Http.responseText;
			d3.select("#log").attr("value", response);
			res = d3.select("#log").attr("value");
			console.log(res);
			res = JSON.parse(res)
			res=res["json"];
			console.log(res);
			setTimeout(function(d){
				d3.json("formule_ups_json.php", function(error, data) {
					var selectElement = document.getElementById("ups");

					while (selectElement.length > 1) {
					  selectElement.remove(1);
					}
					  drawFormule(data, 'ups');
					  d3.select('#ups').property('value', id_f_ups);

					});

					d3.json("formule_uba_json.php", function(error, data) {
					var selectElement = document.getElementById("uba");

					while (selectElement.length > 1) {
					  selectElement.remove(1);
					}
					  drawFormule(data, 'uba');
					  d3.select('#uba').property('value', id_f_uba);
					});
			 //select in basso
				 if(id_formule_glob.indexOf(res[0].id) == -1){
					 console.log("aggiungo Nuova formula")
					id_formule_glob.push(res[0].id); 
					d3.select("#valori1").select("#formula")
					.append('option')
					.attr('value', function (d) {
					  return res[0].testo;
					})
					.text(function (d) {
					  return res[0].descr +": "+res[0].testo;
					})
					.attr("descr",function(d){
							  return res[0].descr;
					})
					}
				 else if(id_formule_glob.indexOf(res[0].id) != -1){
					 console.log("modifo Nuova formula")
					//id_formule_glob.push(res[0].id); 
					d3.select("#valori1").select("#formula")
					.append('option')
					.attr('value', function (d) {
					  return res[0].testo;
					})
					.text(function (d) {
					  return res[0].descr +": "+res[0].testo;
					})
					.attr("descr",function(d){
							  return res[0].descr;
					})
					var x = document.getElementById("formula");
					x.remove(x.selectedIndex);
					 document.querySelector('#formula [value="' + res[0].testo + '"]').selected = true;
					}
			}, 200)
		}
	}
		
	
}

function Applica(type, force){
	d3.select("div#loader").style("display", "");

	if(type == 'uba'){
		is_uba = true;
		id_f = id_formulaUba_glob;
	}
	else{
		is_uba = false;
		id_f = id_formulaUps_glob;
	}

	if(id_piano_glob == null || id_f == null){
		alert("Selezionare una formula");
		d3.select("div#loader").style("display", "none");
		return;
	}
	
	id_p = id_piano_glob;
	
	const Http = new XMLHttpRequest();
	const url='update_piano_formula.php?id_formula='+id_f+'&id_piano='+id_p+'&is_uba='+is_uba+'&force='+force;
	console.log(url);
	Http.open("GET", url);
	Http.send();
	Http.onreadystatechange=(e)=>{
		if (Http.readyState == 4 && Http.status == 200){

			d3.select("div#loader").style("display", "none");
			var response = Http.responseText;
			console.log(response);
			d3.select("#log").attr("value", response);
			updateTree();
		}
	}
	
	d3.selectAll('rect')
	.filter(function(d){		
		c = d3.select(this).attr("id");
		if (d3.select(this).attr("id_piano") == id_p){
			if (type == 'uba'){
				d3.select(this).attr("id_formula_uba", id_f)
				changed_uba[c] = id_f

			}
			else{
				d3.select(this).attr("id_formula_ups", id_f)
				changed_ups[c] = id_f
			}
		}
		
	})
}


function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}



</script>

<div id="loader"></div>




<valori1 id="valori1" class="valori1">
	 <strong> <p id="ppiano"> Seleziona piano/attivita' <span id="piano"> </span>  </p></strong> <br>
	<hr>
	<p> UPS: <select id="ups" onchange="changeFormula('ups')" > <option selected disabled>Scegli</option> </select> 
		<button id="upsButton1" type="button" onclick="Applica('ups', false)"> Applica </button>  <button id="upsButton2" type="button" onclick="Applica('ups', true)"> Sovrascrivi </button> </p>
	<p> UBA: <select id="uba" onchange="changeFormula('uba')"> <option selected disabled>Scegli</option> </select>  
		<button id="ubaButton1" type="button" onclick="Applica('uba', false)"> Applica </button>  <button id="ubaButton2" type="button" onclick="Applica('uba', true)"> Sovrascrivi </button> </p>
	<hr>
 
	<fieldset style="border: 2px solid #3182bd">
<legend style="border: 2px solid #3182bd;margin-left: 1em; padding: 0.2em 0.8em ; 	border-radius: 5px;">Modifica/Aggiungi formula</legend>
	Formula:
	<select id="formula" onchange="modifyFormula()"> 
		    <option selected >Nuova formula</option>
	</select>
	<p><button id="addUps" type="button" onclick="Add()">+</button> <label id="descrFormulaLab" style="display: none;">Descrizione:</label> <input id="descrFormula" size="8" style="display: none;"> </input> 
																 <label id="testo" style="display: none;">Testo:</label> <input id="testoUps" size="8" style="display: none;">
																<input type="checkbox" id="upsCheck" value="UPS"  style="display: none;">  <label id="upsCheck" style="display: none;">UPS:</label> </input>
																<input type="checkbox" id="ubaCheck" value="UBA" style="display: none;">  <label id="ubaCheck" style="display: none;">UBA:</label> </input>
																<button id="confirm" type="button" onclick="Conferma()" style="display: none;">Conferma</button>
	</p>
	<br>
	

	<hr>
	Simulazione
	<p><label>Valore:</label> <input id="valSimu" size="5"> <label>UBA:</label>  <input id="ubaSimu" size="5" disabled>  <label>Finale:</label>  <input id="finalSimu" size="5" readonly> </p>
	
	
		<br>

	
	<p id="log" style="visibility: hidden"> LOG: <input id="log" size="80" class="bigText" readonly> </p> 
</valori1>


