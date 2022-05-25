<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
date_default_timezone_set('Europe/Berlin');

	function traduciNomeGiorno($GiornoInglese){
		$giorno="";
		switch (strtolower($GiornoInglese)) {
			case "sun":
		        $giorno = "Domenica";
		        break;
		    case "mon":
		        $giorno =  "Luned&igrave";
		        break;
		    case "tue":
		        $giorno =  "Marted&igrave";
		        break;
			case "wed":
		        $giorno =  "Mercoled&igrave";
		        break;
			case "thu":
		        $giorno =  "Gioved&igrave";
		        break;
			case "fri":
		        $giorno =  "Venerd&igrave";
		        break;
			case "sat":
		        $giorno =  "Sabato";
		        break;
			}
			return $giorno;
	}
	
	function GetNomeMese($Mese){
		$mese = "";
		switch (strtolower($Mese)) {
			case 1:
		        $mese = "Gennaio";
		        break;
		    case 2:
		        $mese = "Febbraio";
		        break;
		    case 3:
		        $mese = "Marzo";
		        break;
			case 4:
		        $mese = "Aprile";
		        break;
			case 5:
		        $mese = "Maggio";
		        break;
			case 6:
		        $mese = "Giugno";
		        break;
			case 7:
		        $mese = "Luglio";
		        break;
			case 8:
		        $mese = "Agosto";
		        break;
			case 9:
		        $mese = "Settembre";
		        break;
			case 10:
		        $mese = "Ottobre";
		        break;
			case 11:
		        $mese = "Novembre";
		        break;
			case 12:
		        $mese = "Dicembre";
		        break;
			}
			return $mese;		
	}
		
	function GetHeaderDateNow(){
		$NomeGiorno = traduciNomeGiorno(date("D")); 
		$Giorno = date("d"); 
		$Mese = date("m");
		$NomeMese = GetNomeMese($Mese);
		$Anno = date("Y");
		
		$data = $NomeGiorno . ' ' . $Giorno . ' ' . $NomeMese . ' ' . $Anno;
		return $data;
		
	}
	
	function GetDateNow(){
		$Giorno = date("d"); 
		$Mese = date("m");
		$Anno = date("Y");
		$data = $Giorno . '/' . $Mese . '/' . $Anno;
		return $data;
	}
	
	function FormDate2DBdate($strFormDate){
		$strDBDate='';
		if($strFormDate!=''){
			$giorno = substr($strFormDate,0,2);
			$mese = substr($strFormDate,3,2);
			$anno = substr($strFormDate,6,4);
			$strDBDate = $anno . "-" . $mese . "-" . $giorno;
		}
		return $strDBDate;
	}
	
	function DBDate2FormDate($strDBDate){
		$strDate='';
		if($strDBDate!=''){
			$giorno = substr($strDBDate,8,2);
			$mese = substr($strDBDate,5,2);
			$anno = substr($strDBDate,0,4);
			$strDate = $giorno . "/" . $mese . "/" . $anno;	
		}
		
		return $strDate;
	}
	
	function DBTimestamp2FormTimestamp($strDBTimestamp){
		$giorno = substr($strDBTimestamp,8,2);
		$mese = substr($strDBTimestamp,5,2);
		$anno = substr($strDBTimestamp,0,4);
		$orario = substr($strDBTimestamp,11,8);
		$strDate = $giorno . "/" . $mese . "/" . $anno . " " . $orario;
		return $strDate;
	}
	
	function GetFirstDateYear(){
		$Giorno = '01'; 
		$Mese = '01';
		$Anno = date("Y");
		$data = $Giorno . '/' . $Mese . '/' . $Anno;
		return $data;
	}
	
	//check format date yyyy-mm-dd
	function is_date($str){
		$stamp = strtotime( $str );
	    if (!is_numeric($stamp))
	        return FALSE; 
	        
		$giorno = substr($str,8,2);
		$mese = substr($str,5,2);
		$anno = substr($str,0,4);
		
		
	        
	    if (checkdate($mese, $giorno, $anno))
	        return TRUE;
	    
	    return FALSE;
	}
?>