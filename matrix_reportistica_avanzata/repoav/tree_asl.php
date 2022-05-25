<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->



<!DOCTYPE html>
<meta charset="utf-8">
<style>


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
		z-index: 100
    }

.bigText {
    height:30px;
}

.node_s rect {
  cursor: pointer;
  fill: #fff;
  fill-opacity: 0.5;
  stroke: #3182bd;
  stroke-width: 1.5px;
}

.node_s text {
  font: 10px sans-serif;
  pointer-events: none;
}

input.input-box, textarea { 
	background: #E8E8E8	; 
	  pointer-events: none;
	  font-size:11px;
-moz-user-select: none; -webkit-user-select: none; -ms-user-select:none; user-select:none;-o-user-select:none;
}


.link {
  fill: none;
  opacity: 0;
  stroke: #9ecae1;
  stroke-width: 1.5px;
}
	
body {
	overflow-x: hidden;
	}
	



</style>


<body>
<script src="js/d3.v4.min.js"></script>
<script>

function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
			vars[key] = value;
		});
	return vars;
}

function getUrlParam(parameter, defaultvalue) {
	var urlparameter = defaultvalue;
	if (window.location.href.indexOf(parameter) > -1) {
		urlparameter = getUrlVars()[parameter];
	}
	return urlparameter;
}

console.log("DEBUG TREE_ASL" + ID_ASL)

var once = true;
var once_text = true;
var once_rect = true;

var stutture_length_glob = null;
var target_glob = null;
var clicked = null;
var old_clicked_s;
var pianiRect;

var margin = {
	top: 40,
	right: 20,
	bottom: 30,
	left: 0
},
width = 800,
barHeight = 20,
barWidth = (width - margin.left - margin.right) * 0.58
tagetNodeW = 48;

var i = 0,
duration = 400,
root_s;

var diagonal = d3.linkHorizontal()
	.x(function (d) {
		return d.y;
	})
	.y(function (d) {
		return d.x;
	});

if (dim3 == strutture) {
	console.log("asl dim3");
	var svg_s = d3.select("#tree").append("svg")
		.attr("id","asl")
		.attr("width", width) // + margin.left + margin.right)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
} else if (dim4 == strutture) {
	console.log("asl dim4");
	var svg_s = d3.select("#tree2").append("svg")
		.attr("id","asl")
		.attr("width", width) // + margin.left + margin.right)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
}

var div = d3.select("body").append("div")
	.attr("class", "tooltip")
	.style("opacity", 0);

console.log("tree_all_asl_json.php?&id_struttura=" + ID_ASL +"&anno="+ANNO_GLOB);
d3.json("tree_all_asl_json.php?&id_struttura=" + ID_ASL +"&anno="+ANNO_GLOB, function (error, data_s) {
	if (error)
		throw error;
	//_data = data[0].get_struttura_piani_tree;
	//_data = _data.substring(1, _data.length-1);
	//_data = '{ "name": "flare", "children": [ ' + _data + '] }'
	//console.log(JSON.parse(data))
	//console.log(data);
	//console.log(data_s);
	var _json = JSON.parse(data_s);
	root_s = d3.hierarchy(_json);
	root_s.x0 = 0;
	root_s.y0 = 0;

	//console.log(root_s)
	//console.log("nodi")
	var nodes_s = root_s.descendants();

	console.log(nodes_s);

	for (i = 1; i < nodes_s.length; i++) {
		nodes_s[i]._children = nodes_s[i].children;
		nodes_s[i].children = null;
	}
	update_s(root_s);
	//updateTree();
});

function update_s(source) {

	var nodes_s = root_s.descendants();

	var height = Math.max(500, nodes_s.length * barHeight + margin.top + margin.bottom);

	d3.select("svg#asl").transition()
	.duration(duration)
	.attr("height", height);

	d3.select(self.frameElement).transition()
	.duration(duration)
	.style("height", height + "px");

	var index = -1;
	root_s.eachBefore(function (n) {
		n.x = ++index * barHeight;
		n.y = n.depth * 10;
	});
	// Update the nodes�
	var node_s = svg_s.selectAll(".node_s")
		.data(nodes_s, function (d) {
			return d.id /*|| (d.id = ++i)*/;
		});

	var nodeEnter_s = node_s.enter().append("g")
		.attr("class", "node_s")
		.attr("transform", function (d) {
			return "translate(" + source.y0 + "," + source.x0 + ")";
		})
		.style("opacity", 0)

		// Enter any new nodes at the parent's previous position.


	nodeEnter_s.append("rect")
		.attr("y", -barHeight / 2)
		.attr("height", barHeight)
		.attr("width", barWidth)
		.attr("id_struttura", function (d) {
			return d.data.id_struttura;
		})
		.attr("nome", function (d) {
			return d.data.descrizione_breve;
		})
		.on("click", click_s)
		.on("mouseover", function (d) {
			if (d.data.descrizione_breve.length >= 90) {
				div.transition()
				.style("opacity", .9);

				div.html(d.data.descrizione_breve)
				.style("left", (d3.event.pageX) + "px")
				.style("top", (d3.event.pageY - 28) + "px");
			}
		})
		.on("mouseout", function (d) {
			div.transition()
			.style("opacity", 0);
		});

	nodeEnter_s.append("text")
	.attr("dy", 3.5)
	.attr("dx", 0.5)
	.style("overflow", "hidden")
	.style("font-size", "9px")
	.text(function (d) {
		console.log(d);
		if (d.data.descrizione_breve.length < 82)
			return d.data.descrizione_breve.toUpperCase();
		return d.data.descrizione_breve.substring(0, 82).toUpperCase() + '...';
	})

	// Transition nodes to their new position.
	nodeEnter_s.transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + d.y + "," + d.x + ")";
	})
	.style("opacity", 1);

	node_s.transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + d.y + "," + d.x + ")";
	})
	.style("opacity", 1)
	.select("rect")

	// Transition exiting nodes to the parent's new position.
	node_s.exit().transition()
	.duration(duration)
	.attr("transform", function (d) {
		return "translate(" + source.y + "," + source.x + ")";
	})
	.style("opacity", 0)
	.remove();

	// Stash the old positions for transition.
	root_s.each(function (d) {
		d.x0 = d.x;
		d.y0 = d.y;
	});
	d3.select("div#loader").style("display", "none");

}

// Toggle children on click.
function click_s(d) {

	if (typeof old_clicked_s != "undefined")
		old_clicked_s.style("fill", old_color_s);

	if (typeof d.data.children != "undefined") {
		if (d.children) {
			d._children = d.children;
			d.children = null;
		} else {
			d.children = d._children;
			d._children = null;
		}
		update_s(d);
	}

	old_clicked_s = d3.select(this);
	old_color_s = d3.select(this).style("fill");

	d3.select(this).style("fill", "blue");

	console.log(d3.select(this));
	struttura = d3.select(this).attr("id_struttura");
	console.log("Hai cliccato la struttura: " + d3.select(this).attr("id_struttura"));

	id_s = struttura;
	//dim3.text(d3.select(this).attr("struttura"));

	if (dim3 == strutture){
		id_3_new = struttura;
		d3.select("#dim3").text(d3.select(this).attr("nome"));
	}
	else if (dim4 == strutture){
		id_4_new = struttura;
		d3.select("#dim4").text(d3.select(this).attr("nome"));
	}

	query();
}

var once = false;

function color(d) {
	return d._children ? "orange" : d.children ? "orange" : "yellow";
}

function onlyUnique(value, index, self) {
	return self.indexOf(value) === index;
}

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}


</script>



