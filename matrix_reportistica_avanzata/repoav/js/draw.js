/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

onceDraw = false;


function drawPie(r, _data, c) {
	
	
	d3.selectAll("#pie").remove();
	d3.selectAll("#plot").remove();
	d3.selectAll("#bars").remove();
	d3.selectAll("#container").remove();
	d3.selectAll("tooltip").remove();
	
	ww = window.innerWidth;
	w = (c.length + 1) * 220;
	
	if(w < ww)
		ww = w;
	
	h = 400;
	var radius = 100;

	var svg = d3.select("body").append('div')
		.attr('id','container')
		.attr("width", window.innerWidth - 100)
		.style("overflow","auto")
		.append("svg")
		.attr("width", w)
		.attr("height", h)
		.attr("id", "plot")
		.style("border", "2px solid lightGrey")
		.style("border-radius", "6px")
		.style("overflow","scroll")
		.style("display", "block");
		
	//.append("g")
	//.attr("transform", "translate(" + w / 2 + "," + h / 2 + ")");


	for (var i = 0; i < c.length; i++) {

		var p = [];
		var e = [];
		var t = [];
		colTot = 0;
		for (var j = 0; j < data_glob.length; j++) {
			if (data_glob[j].colonna == c[i] && r.indexOf(data_glob[j].riga) > -1){
				p.push(data_glob[j]);
				e.push(data_glob[j]['valore'+_tipo]);
				if(data_glob[j]['target'+_tipo]-data_glob[j]['valore'+_tipo] > 0)
					t.push(data_glob[j]['target'+_tipo]-data_glob[j]['valore'+_tipo]);
				else 
					t.push(0);
				colTot += parseInt(data_glob[j]['valore'+_tipo]);
			}
		}
		
		p = p.map(a => parseFloat(a['perc'+_tipo]).toFixed(1));
		
		if(globalTipoGraf == 'Eseguiti'){
			p = [];
			for(var k=0; k<e.length; k++){
				//p.push((100/colTot*e[k]).toFixed(1));
				p.push(e[k]);
			}
		}
		
		var pl = p.length;
		if(globalTipoGraf == 'Eseguiti'){
			for (var j = 0; j < pl; j++) {
				p.push(parseInt(t[j]));
				//p.splice(j+1, 0, 100-p[j]);
			}
		}else{
			for (var j = 0; j < pl; j++) {
				p.push((100 - p[j]).toFixed(1));
				//p.splice(j+1, 0, 100-p[j]);
			}
		}

		var color = d3.scaleOrdinal()
			.range(colors);

		var arc = d3.arc()
			.outerRadius(radius)
			.innerRadius(0);

		var labelArc = d3.arc()
			.outerRadius(radius - 20)
			.innerRadius(radius - 20);

		var pie = d3.pie()
			.sort(null)
			.value(function (d) {
				return d;
			});

		var g = svg.selectAll(".arc" + i)
			.data(pie(p))
			.enter().append("g")
			.attr("class", "arc")
			.attr("transform", "translate(" + ((i * 220) + radius+20) + "," + h / 2 + ")");

		g.append("path")
		.attr("d", arc)
		.attr("desc", function (d, i) {
			var daFare = "";
			if(i / pl >=1)
				daFare = "da fare"
			if(globalTipoGraf != 'Eseguiti')
				return r[i%pl]+": " + daFare +" "+ d.data+"%";
			return r[i%pl]+": " + daFare +" "+ d.data;

		})
		.style("fill", function (d, i) { //return color(d.data);
			return colors[i % pl]
		})
		.style("opacity", function (d, i) {
			if (i / pl >= 1)
				return 0.2;
			return 1;
		})
		.on("mouseover", function (d) {
			div.transition()
			.style("opacity", .9);
			var t = d3.select(this).attr("desc");
			div.html(t)
			.style("left", (d3.event.pageX) + "px")
			.style("top", (d3.event.pageY - 28) + "px");
		})
		.on("mouseout", function (d) {
			div.transition()
			.duration(200)
			.style("opacity", 0);
		});

		g.append("text")
		.attr("font-family", "sans-serif")
		.attr("transform", function (d) {
			return "translate(" + labelArc.centroid(d) + ")";
		})
		//.attr("dy", ".35em")
		.text(function (d) {
			if(globalTipoGraf == 'Eseguiti')
				return d.data;
			return d.data + '%';
		});

		svg.append("text")
		.attr("transform", "translate(" + ((i * 220) + radius+20) + "," + ((h / 2) + radius+20) + ")")
		.attr("dy", ".35em")
		.attr("text-anchor", "middle")		
		.attr("descr", c[i])
		.attr("font-family", "sans-serif")
		.text(function (d) {
			if (c[i].length > 20)
				return c[i].substring(0, 20)+"...";
			else	
				return c[i];
		})
		.on("mouseover", function (d) {
			div.transition()
			.style("opacity", .9);
			var t = d3.select(this).attr("descr");
			div.html(t)
			.style("left", (d3.event.pageX) + "px")
			.style("top", (d3.event.pageY - 28) + "px");
		})
		.on("mouseout", function (d) {
			div.transition()
			.duration(200)
			.style("opacity", 0);
		});

	}
	
	var legend = svg.append("g")
		.attr("id", "legend")
		.attr("opacity", function(d){
			return showLegend;
		})
		.attr("font-family", "sans-serif")
		.attr("font-size", 10)
		.attr("text-anchor", "end")
		.selectAll("g")
		.data(r)
		.enter().append("g")
		.attr("transform", function (d, i) {
			return "translate(0," + i * 20 + ")";
		});

	var legendRect = legend.append("rect")
		.attr("id", "legendRect")
		.attr("x", ww - 17)
		.attr("originalX", ww - 17)
		.attr("width", 15)
		.attr("height", 15)
		.attr("fill", function (d, i) {
			return colors[i]
		})
		.attr("stroke", function (d, i) {
			return colors[i]
		})
		.attr("stroke-width", 2)

	var legendText = legend.append("text")
		.attr("id", "legendText")
		.attr("x", ww - 24)
		.attr("originalX", ww - 24)
		.attr("y", 9.5)
		.attr("dy", "0.32em")
		.text(function (d) {
			return d;
		});
		
		
		d3.select('div#container').on('scroll', function() { 
			legendText.attr("x", function(d){
				var scroll = d3.select("div#container")._groups[0][0].scrollLeft;
				var oldX = d3.select(this).attr("originalX");
				return parseInt(oldX) + parseInt(scroll) - 7;
			});
			legendRect.attr("x", function(d){
				var scroll = d3.select("div#container")._groups[0][0].scrollLeft;
				var oldX = d3.select(this).attr("originalX");
				return parseInt(oldX) + parseInt(scroll);
			});
		})

}

function drawBar(r, _data, c) {
		
	var _data = _data.filter(function (d, index, arr) {
			return r.indexOf(d.riga) > -1;
		});

	//console.log(_data)
	//console.log(r)
	
	var w = window.innerWidth - 100;
	ww = window.innerWidth - 100;

	var h = 400;
	
	//if(c.length*r.length*50 > w)
		w = c.length*r.length*50+100; 
	if(w < ww)
		ww = w;

	var svg = d3.select("body").append('div')
		.attr('id','container')
		.attr("width", window.innerWidth - 100)
		.style("overflow","auto")
		.append("svg")
		.attr("y", 5)
		.attr("width", w + 50)
		.attr("height", h + 200)
		.attr("id", "plot")
		.style("border", "2px solid lightGrey")
		.style("border-radius", "6px")
		.style("overflow","auto")
		/*.call(d3.zoom().scaleExtent([0, 10]).on("zoom", function(d){
				 var yNewScale = d3.event.transform.rescaleY(yScale);
				 gY.call(d3.axisLeft(y).ticks(null, "s").scale(yNewScale));
				 
				 d3.selectAll("rect#targetBar")
					.attr("y", function (d) {
							console.log(d3.select(this))
							return yNewScale(d.value);
						})
					.attr("height", function (d) {
							return h - yNewScale(d.value);
						})
				})
		)*/
		.on("mousedown.zoom", null)
		.on("touchstart.zoom", null)
		.on("touchmove.zoom", null)
		.on("touchend.zoom", null)
		.append("g")
		
	var g = svg.append("g").attr("transform", "translate(" + 50 + "," + 0 + ")");

	// The scale spacing the groups:
	var x0 = d3.scaleBand()
		.rangeRound([0, w-150])
		.paddingInner(0.1);

	// The scale for spacing each group's bar:
	var x1 = d3.scaleBand()
		.padding(0.05);

	var y = d3.scaleLinear()
		.rangeRound([h, 0]);

	var z = d3.scaleOrdinal()
		.range(colors);

	var keys = r;

	x0.domain(_data.map(function (d) {
			return d.colonna;
		}));
	x1.domain(keys).rangeRound([0, x0.bandwidth()]);
	//y.domain([0, d3.max(_data, function(d) { return d3.max(keys, function(d) { return parseInt(d.valore); }); })]).nice();
	yScale = y.domain([0, d3.max(_data, function (d) {
				if(dim1 == linee || dim2 == linee)
					return parseInt(d['valore'+_tipo]);
				return parseInt(d['target'+_tipo]);
			})]);

	console.log(_data);
	if (globalTipoGraf == "Eseguiti") {
		g.append("g")
		.selectAll("g")
		.data(_data)
		.enter().append("g")
		.attr("class", "bar")
		.attr("transform", function (d) {
			return "translate(" + x0(d.colonna) + ",0)";
		})
		.selectAll("rect")
		.data(function (d) {
			return keys.map(function (key) {
				return {
					key: d.riga,
					value: d['valore'+_tipo],
					value2: d['target'+_tipo]
				};
			});
		})
		.enter().append("rect")
		.attr("id", "eseguitiBar")
		.attr("descr", function (d, i) {
			return d.key + " " + parseInt(d.value) + "/" + parseInt(d.value2);
		})
		.attr("x", function (d) {
			return x1(d.key);
		})
		.attr("y", function (d) {
			return y(d.value);
		})
		.attr("width", x1.bandwidth())
		.attr("height", function (d) {
			return h - y(d.value);
		})
		.attr("fill", function (d) {
			return z(d.key);
		})
		.style("z-index", 2)
		.style("opacity", 1)
		.on("mouseover", function (d) {
			div.transition()
			.style("opacity", .9);
			var t = d3.select(this).attr("descr");
			div.html(t)
			.style("left", (d3.event.pageX) + "px")
			.style("top", (d3.event.pageY - 28) + "px");
		})
		.on("mouseout", function (d) {
			div.transition()
			.duration(200)
			.style("opacity", 0);
		})

		g.append("g")
		.selectAll("g")
		.data(_data)
		.enter().append("g")
		.attr("class", "bar")
		.attr("transform", function (d) {
			return "translate(" + x0(d.colonna) + ",0)";
		})
		.selectAll("rect")
		.data(function (d) {
			return keys.map(function (key) {
				return {
					key: d.riga,
					value: d['target'+_tipo],
					value2: d['valore'+_tipo],
					col: d.colonna
				};
			});
		})
		.enter().append("rect")
		.attr("id", "targetBar")
		.attr("descr", function (d, i) {
			return d.key + " " + parseInt(d.value2) + "/" + parseInt(d.value);
		})
		.attr("x", function (d) {
			return x1(d.key);
		})
		.attr("y", function (d) {
			return y(d.value);
		})
		.attr("width", x1.bandwidth())
		.attr("height", function (d) {
			return h - y(d.value);
		})
		.attr("fill", function (d) {
			return z(d.key);
		})
		.style("z-index", 1)
		.style("opacity", 0.1)
		.on("mouseover", function (d) {
			div.transition()
			.style("opacity", .9);
			var t = d3.select(this).attr("descr");
			div.html(t)
			.style("left", (d3.event.pageX) + "px")
			.style("top", (d3.event.pageY - 28) + "px");
		})
		.on("mouseout", function (d) {
			div.transition()
			.duration(200)
			.style("opacity", 0);
		})
	} else {
		var m = d3.max(_data, function (d) {
				return parseInt(d['perc'+_tipo])
			});
		/*if (m <= 25)
			y.domain([0, 25]);
		else if (m <= 50)
			y.domain([0, 50]);
		else if (m <= 75)
			y.domain([0, 75]);
		else
			y.domain([0, 100]);*/
		
		m = parseInt(m/10)*10+10;
		if (m>100)
			m = 100;
		y.domain([ 0, m]);
		console.log("scala %: " +y.domain());
		

		g.append("g")
		.selectAll("g")
		.data(_data)
		.enter().append("g")
		.attr("class", "bar")
		.attr("transform", function (d) {
			return "translate(" + x0(d.colonna) + ",0)";
		})
		.selectAll("rect")
		.data(function (d) {
			return keys.map(function (key) {
				return {
					key: d.riga,
					value: parseInt(d['perc'+_tipo]),
					col: d.colonna
				};
			});
		})
		.enter().append("rect")
		.attr("id", "bar")
		.attr("descr", function (d, i) {
			return d.key + ": " + d.value + "%"
		})
		.attr("x", function (d) {
			return x1(d.key);
		})
		.attr("y", function (d) {
			return y(d.value);
		})
		.attr("width", x1.bandwidth())
		.attr("height", function (d) {
			return h - y(d.value);
		})
		.attr("fill", function (d) {
			return z(d.key);
		})
		.attr("z-index", 0)
		.style("opacity", 1)
		.on("mouseover", function (d) {
			div.transition()
			.style("opacity", .9);
			var t = d3.select(this).attr("descr");
			div.html(t)
			.style("left", (d3.event.pageX) + "px")
			.style("top", (d3.event.pageY - 28) + "px");
		})
		.on("mouseout", function (d) {
			div.transition()
			.duration(200)
			.style("opacity", 0);
		})

	}

	gY = g.append("g")
	.attr("class", "y axis")
	.call(d3.axisLeft(y).ticks(null, "s"))
	.append("text")
	.attr("x", 2)
	.attr("y", y(y.ticks().pop()) + 0.5)
	.attr("dy", "0.32em")
	.attr("fill", "#000")
	.attr("font-weight", "bold")

	g.append("g")
	.attr("class", "axis")
	.attr("transform", "translate(0," + h + ")")
	.call(d3.axisBottom(x0))
	.selectAll("text")
	.attr("descr", function(d){return d;})
	.text(function(d){
		if(d.length > 35)
			return d.substring(0,30)+"..";
		return d;})
	.on("mouseover", function (d) {
		div.transition()
		.style("opacity", .9);
		var t = d3.select(this).attr("descr");
		div.html(t)
		.style("left", (d3.event.pageX) + "px")
		.style("top", (d3.event.pageY - 28) + "px");
	})
	.on("mouseout", function (d) {
		div.transition()
		.duration(200)
		.style("opacity", 0);
	})
	.attr("text-anchor", "start")
	.attr("transform", "rotate(65)");

	var legend = g.append("g")
		.style("position", "fixed")
		.style("x", ww)
		.style("y", 10)
		.attr("id","legend")
		.attr("opacity", function(d){
			return showLegend;
		})
		.attr("font-family", "sans-serif")
		.attr("font-size", 10)
		.attr("text-anchor", "end")
		.selectAll("g")
		.data(keys.slice().reverse())
		.enter().append("g")
		.attr("transform", function (d, i) {
			return "translate(0," + i * 20 + ")";
		});

	var legendRect = legend.append("rect")
	.attr("x", ww - 17)
	.attr("originalX", ww)
	.attr("id", "legendRect")
	.attr("width", 15)
	.attr("height", 15)
	.attr("fill", z)
	.attr("stroke", z)
	.attr("stroke-width", 2)

	var legendText = legend.append("text")
	.attr("id", "legendText")
	.attr("x", ww - 24)
	.attr("originalX", ww)
	.attr("y", 9.5)
	.attr("dy", "0.32em")
	.text(function (d) {
		return d;
	});
	
	d3.select('div#container').on('scroll', function() { 
		legendText.attr("x", function(d){
			var scroll = d3.select("div#container")._groups[0][0].scrollLeft;
			var oldX = d3.select(this).attr("originalX");
			return parseInt(oldX) + parseInt(scroll) - 7;
		});
		legendRect.attr("x", function(d){
			var scroll = d3.select("div#container")._groups[0][0].scrollLeft;
			var oldX = d3.select(this).attr("originalX");
			return parseInt(oldX) + parseInt(scroll);
		});
	})

	g.append("g")
	.attr("class", "grid")
	.attr("transform", "translate(0," + h + ")")
	.call(d3.axisBottom(x0)
		.ticks(5)
		.tickSize(-h)
		.tickFormat(""))

	g.append("g")
	.attr("class", "grid")
	.call(d3.axisLeft(y)
		.ticks(5)
		.tickSize(-w)
		.tickFormat(""))

}

function drawLines(r, _data, c) {
	
	d3.selectAll("#pie").remove();
	d3.selectAll("#plot").remove();
	d3.selectAll("#bars").remove();
	d3.selectAll("#container").remove();
	d3.selectAll("tooltip").remove();
	
	
	console.log("CUMULATA: " + cumulFlag)
	
	/*console.log(r);
	console.log(c);
	console.log(_data);*/

	//aggiungo 0 e riordino
	
	var newData = [];
	for(var i=0; i<c.length; i++){
		for(var j=0; j<r.length; j++){
			//var flag = 0;
			for(var k=0; k<_data.length; k++){
				if(_data[k].riga == r[j] && _data[k].colonna == c[i])
					newData.push(_data[k]);
				else
			//		flag = 1;
			//}
			//if(flag == 0)
				//if(globalTipoGraf == "Eseguiti")
				newData.push({riga: r[j], colonna: c[i], valore: 0, perc: 0, target: 0, valore_ups: 0, perc_ups: 0, target_ups: 0, valore_uba: 0, perc_uba: 0, target_uba: 0});
				//else
				//	_data.push({riga: r[j], colonna: c[i], perc: 0});
			}
		}
	}
	/*console.log(_data);
	console.log(newData);

	_data = _data.concat(newData);

	_data.sort(function (a, b) {
		if (a.colonna === b.colonna) {
			return b.riga - a.riga;
		}
		return a.colonna > b.colonna ? 1 : -1;
	});*/

	try {
		xx.remove();
		yy.remove();
	} catch {
		console.log("clean assi fallito")
	}

	var mat = [];
	for (var i = 0; i < r.length; i++) {
		var a = [];
		var cumul = 0
		for (var j = 0; j < _data.length; j++) {
			if (r[i] == _data[j].riga) {
				if (globalTipoGraf == "Eseguiti"){
					a.push(parseInt(_data[j]['valore'+_tipo]) + parseInt(cumul));
					//if(cumulFlag)
					//	cumul = parseInt(_data[j]['valore'+_tipo])
					}
				else{
					a.push(parseInt(_data[j]['perc'+_tipo]) + parseInt(cumul));
					//if(cumulFlag)
					//	cumul = parseInt(_data[j]['perc'+_tipo])
					}
			}
		}
		mat.push(a);
	}

	console.log(mat)

	w = window.innerWidth - 100;
	ww = window.innerWidth - 100;
	h = 400;
	
	if(c.length*200 > w)
		w = c.length*200; 
		//w = c.length*r.length*100; 
	
	var s = d3.select("body").append('div')
		.attr('id','container')
		.attr("width", window.innerWidth - 100)
		.style("overflow","auto")
		.append("svg")
		.attr("width", w + 50)
		.attr("height", h + 200)
		.attr("y", 5)
		.attr("id", "plot")
		.attr("opacity", function (d) {
			if (onceDraw)
				return 0;
			return d3.select(this).attr("opacity");
		})
		.attr("transform", function (d) {
			if (onceDraw)
				return "translate(0,10)";
		})
		.style("border", "2px solid lightGrey")
		.style("border-radius", "6px")
		.style("overflow","scroll");
	// .append("g");

	onceDraw = false;

	xScale = d3.scaleLinear()
		.domain([0, c.length])
		.range([0, w - 200]);

	var x = d3.axisBottom().scale(xScale)
		.tickFormat(function (d) {
			return c[d];
		})
		.ticks(c.length);

	xx = s.append("g")
		.attr("transform", "translate(50," + h + ")")
		.call(x)
		.selectAll("text")
		.attr("text-anchor", "start")
		.attr("transform", "rotate(45)");

	var max = -1;
	for (var i = 0; i < mat.length; i++) {
		for (var j = 0; j < mat[i].length; j++) {
			if (mat[i][j] > parseInt(max))
				max = parseInt(mat[i][j]);
		}
	}

	yScale = d3.scaleLinear()
		.domain([max, 0])
		.range([0, h]);

	console.log(mat);	
	if (globalTipoGraf != 'Eseguiti') {
		if(!cumulFlag){
		var m = d3.max(_data, function (d, mat) {
				return parseInt(d['perc'+_tipo]);
			});
		}else{
			var m = -9999;
			for(var i =0 ; i<mat.length; i++){
				for(var j=0; j<mat[i].length; j++){
					if(mat[i][j] > m)
						m = mat[i][j];
				}
			}
		}	
		console.log('max ' + m);
		/*if (m <= 25)
			yScale.domain([25, 0]);
		else if (m <= 50)
			yScale.domain([50, 0]);
		else if (m <= 75)
			yScale.domain([75, 0]);
		else
			yScale.domain([100, 0]);*/
		if(m <= 5)
			yScale.domain([5,0]);
		else{
			m = parseInt(m/10)*10+10;
			yScale.domain([m,0]);
		}
		console.log("scala %: " + yScale.domain());
	}

	var y = d3.axisLeft().scale(yScale);

	yy = s.append("g")
		.attr("transform", "translate(50, 0)")
		.call(y);

	for (var j = 0; j < mat.length; j++) {
		points = [];
		for (var i = 0; i < mat[j].length; i++) {
			p = {
				x: i,
				y: parseInt(mat[j][i])
			};
			points.push(p);
		}

		var lineFunction = d3.line()
			.x(function (d) {
				return xScale(d.x) + 50;
			})
			.y(function (d) {
				return yScale(d.y);
			})
			.curve(d3.curveLinear);

		var lineGraph = s.append("path")
			.attr("d", lineFunction(points))
			.attr("stroke", colors[j])
			.attr("stroke-width", 2)
			.attr("fill", "none")
			.attr("descr", r[j])
			.on("mouseover", function (d) {
				div.transition()
				.style("opacity", .9);
				var coords = d3.mouse(this);
				var xValue = xScale.invert(coords)[0];
				var yValue = yScale.invert(coords)[1];
				var t = d3.select(this).attr("descr");
				div.html(t)
				.style("left", (d3.event.pageX) + "px")
				.style("top", (d3.event.pageY - 28) + "px");
			})
			.on("mouseout", function (d) {
				div.transition()
				.duration(200)
				.style("opacity", 0);
			})

			s.selectAll("circles")
			.data(points)
			.enter()
			.append("circle")
			.attr("cx", function (d) {
				return xScale(d.x) + 50
			})
			.attr("cy", function (d) {
				return yScale(d.y)
			})
			.attr("descr", function (d) {
				if(globalTipoGraf == 'Eseguiti')
					return r[j] + ": " + d.y
				return r[j] + ": " + d.y + '%';
			})
			.attr("r", 3)
			.style("fill", function (d, i) {
				return colors[j]
			})
			.style("z-index", 999)
			//.attr("stroke", "black")
			.on("mouseover", function (d) {
				var t = d3.select(this).attr("descr");
				div.html(t)
				div.transition()
				.style("opacity", .9);
				div.html(t)
				.style("left", (d3.event.pageX) + "px")
				.style("top", (d3.event.pageY - 28) + "px");
			})
			.on("mouseout", function (d) {
				div.transition()
				.style("opacity", 0);
			})
	}

	s.append("g")
	.attr("class", "grid")
	.attr("transform", "translate(0," + h + ")")
	.call(d3.axisBottom(xScale)
		.ticks(5)
		.tickSize(-h)
		.tickFormat(""))

	s.append("g")
	.attr("class", "grid")
	.call(d3.axisLeft(yScale)
		.ticks(5)
		.tickSize(-w)
		.tickFormat(""))

	/*legend = d3.select("div#legend").style("opacity", 1).style("left", window.innerWidth - 750 + "px").style("top", 300 + "px").style("z-index", 999)

		s.selectAll("circlesLegend")
		.data(r)
		.enter()
		.append("circle")
		.attr("cx", w - 220)
		.attr("cy", function (d, i) {
			return 25 + i * 20
		}) // 100 is where the first dot appears. 25 is the distance between dots
		.attr("r", 7)
		.style("fill", function (d, i) {
			return colors[i]
		})

		s.selectAll("labelsLegend")
		.data(r)
		.enter()
		.append("text")
		.attr("x", w - 200)
		.attr("y", function (d, i) {
			return 30 + i * 20
		}) // 100 is where the first dot appears. 25 is the distance between dots
		.style("fill", function (d, i) {
			return colors[i]
		})
		.text(function (d) {
			return d
		})
		.attr("text-anchor", "left")
		.style("alignment-baseline", "middle")*/
		
		
		var legend = s.append("g")
		.attr("id", "legend")
		.attr("opacity", function(d){
			return showLegend;
		})
		.attr("font-family", "sans-serif")
		.attr("font-size", 10)
		.attr("text-anchor", "end")
		.selectAll("g")
		.data(r)
		.enter().append("g")
		.attr("transform", function (d, i) {
			return "translate(0," + i * 20 + ")";
		});

		var  legendRect = legend.append("rect")
		.attr("x", ww)
		.attr("originalX", ww)
		.attr("width", 15)
		.attr("height", 15)
		.attr("fill", function (d, i) {
			return colors[i]
		})
		.attr("stroke", function (d, i) {
			return colors[i]
		})
		.attr("stroke-width", 2)

		var legendText = legend.append("text")
		.attr("x", ww - 7)
		.attr("originalX", ww)
		.attr("y", 9.5)
		.attr("dy", "0.32em")
		.text(function (d) {
			return d;
		});
		
		
		
		d3.select('div#container').on('scroll', function() { 
			legendText.attr("x", function(d){
				var scroll = d3.select("div#container")._groups[0][0].scrollLeft;
				var oldX = d3.select(this).attr("originalX");
				return parseInt(oldX) + parseInt(scroll) - 7;
			});
			legendRect.attr("x", function(d){
				var scroll = d3.select("div#container")._groups[0][0].scrollLeft;
				var oldX = d3.select(this).attr("originalX");
				return parseInt(oldX) + parseInt(scroll);
			});
		})
}