<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" itemscope itemtype="http://schema.org/Product" xmlns:fb="http://ogp.me/ns/fb#">
<head>
  <title>ComboGrid Zero-Configuration demo</title>
  <meta name="Description" content="Zero-Configuration demo | Base example | jQuery Combogrid Plugin" />
 
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script   src="../javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="../css/jquery-ui-1.9.2.custom.css" />
		 <script src="//rawgithub.com/stidges/jquery-searchable/master/dist/jquery.searchable-1.0.0.min.js"></script>
		 
		 
		                

	<link rel="shortcut icon" type="image/png" href="/images/combogrid.png" />
	<link rel="icon" type="image/png" href="/images/combogrid.png" />
	
	<!-- REQUIRED - jQuery library -->            
  <!-- load jQuery from local file -->
  <!--script type="text/javascript" src="/resources/jquery/jquery-1.6.4.min.js"></script-->    
                  
	<!-- commented, remove this line to use IE & iOS favicons            
	<link rel="shortcut icon" href="images/icons/favicon.ico" />
	<link rel="apple-touch-icon" href="images/icons/ios-icon.png" />            
	end of comment -->

  <link rel="stylesheet" type="text/css" media="screen" href="http://combogrid.justmybit.com/resources/css/smoothness/jquery-ui-1.10.1.custom.css"/>
  <script type="text/javascript" src="http://combogrid.justmybit.com/resources/jquery/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" src="http://combogrid.justmybit.com/resources/jquery/jquery-ui-1.10.1.custom.min.js"></script>
  <link rel="stylesheet" type="text/css" media="screen" href="http://combogrid.justmybit.com/resources/css/smoothness/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="http://combogrid.justmybit.com/resources/plugin/jquery.ui.combogrid.js"></script>
  <script>
  jQuery(document).ready(function(){
	  /*$('#switcher').themeswitcher({
		  loadTheme:"Smoothness"
	  });*/
	  $( "#project" ).combogrid({
		  debug:true,
		  colModel: [{'columnName':'id','width':'10','label':'id'}, {'columnName':'name','width':'45','label':'title'},{'columnName':'author','width':'45','label':'author'}],
		  url: 'server.jsp',
		  select: function( event, ui ) {
			  $( "#project" ).val( ui.item.name );
			  return false;
		  }
	  });
  });
  </script>
  <script type="text/javascript">
  $(function () {
	    $('.dropdown-table').searchable({
	        striped: true,
	        searchType: 'fuzzy',
	        url:"prova.jsp"
	    });
	    
	    

	    $(".dropdown-search").bind("focus", function () {
	        var $this = $(this);
	        var $table = $this.next();
	        if ( $table.is(":hidden")) {
	            $table.show();
	        } else {
	            $table.hide();
	        }
	        var resultCount = ($table.find("tr:visible").length - 1);
	      
	    });
	});
  </script>
</head>
<body>
	
		
			
			  <div class="two-third">
	        <div style="float:left"><input size="30" id="project"/></div>
	        <div id="switcher" style="float:right"></div>
			  </div>
			
		</body>
</html>
