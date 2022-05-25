<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="org.json.*" %>

<%!
private String createTableFromJson(String json) throws ParseException{
	String output = "";
	String jsonString = "["+json.replaceAll("<br/><br/>", ",")+"]";
	HashMap<Integer, String> hash = new HashMap<Integer, String>();
	Map<Integer, String> treeMap = null;
	JSONArray topArray = new JSONArray(jsonString); 
	
	ArrayList<String> headers = new ArrayList<String>();
	ArrayList<String> body = new ArrayList<String>();
		
	for(int i = 0; i < topArray.length(); i++){
		JSONObject c = topArray.getJSONObject(i);

		if (treeMap==null){
		for (int j = 0; j<c.names().length();j++){
			int index = jsonString.indexOf(c.names().getString(j));
			hash.put(index, c.names().getString(j));
		}
		treeMap = new TreeMap<Integer, String>(hash);
		}
		
		
		
		for(int k = 0; k<treeMap.size(); k++){
			int key = (int) treeMap.keySet().toArray()[k];
			String nome = treeMap.get(key); 
			String value = (String) c.get(nome);
			if (nome.equals("info")){
				if (!headers.contains(value))
					headers.add(value);
			}
			else if (nome.equals("valore"))
				body.add(value);
		} 
	}
	
	output= "<table style=\"border-collapse: collapse\" border=\"1px solid black\" >";
	
	//headers
	output+="<tr>";
	for (int i = 0; i<headers.size(); i++)
		output+= "<th>"+headers.get(i)+"</th>";
	output+="</tr>";
	
	//corpo
	int indiceCol = 0;
	output+="<tr>";
	for (int i = 0; i<body.size(); i++) {
		indiceCol++;
		output+= "<td>"+body.get(i)+"</td>";
		
		if (i==(body.size()-1))
			output+="</tr>";
		else if (indiceCol == headers.size()){
			indiceCol = 0;
			output+="</tr><tr>";
		}
	}
	
	output+="</table>";
	return output;
}
%>

