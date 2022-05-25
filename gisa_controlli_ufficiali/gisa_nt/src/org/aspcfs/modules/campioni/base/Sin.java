/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.base;


public class Sin {
	
		
	boolean a1_1;
	boolean a1_2;
	boolean b1_1;
	boolean b1_2;
	boolean b1_3;
	boolean b1_4;
	boolean b2_1;
	boolean b2_2;
	boolean b2_3;
	boolean b2_4;
	boolean b3_1;
	boolean b3_2;
	boolean b3_3;
	boolean b3_4;

	
	String a2;
	String a2_check;
	String a2_2_check;
	String a2_1;
	String a2_2;
	String a3;
	String a4_1;
	String a4_2;
	String a5;
	String a6;
	String a7;
	String a8;
	String a9;
	String a10;
	String a11_1;
	String a11_2;
	String a12;
	String a13;
	String a14;
	String a15;
	String data;
	String b1_specie;
	String b2_testo;
	String b4_1_1 = "";
	String b4_1_2 = "";
	String b4_1_3 = "";
	String b4_1_4 = "";
	String b4_2_1 = "";
	String b4_2_2 = "";
	String b4_2_3 = "";
	String b4_2_4 = "";
	String b4_3_1 = "";
	String b4_3_2 = "";
	String b4_3_3 = "";
	String b4_3_4 = "";
	String b4_4_1 = "";
	String b4_4_2 = "";
	String b4_4_3 = "";
	String b4_4_4 = "";
	String b4_5_1 = "";
	String b4_5_2 = "";
	String b4_5_3 = "";
	String b4_5_4 = "";
	String b4_6_1 = "";
	String b4_6_2 = "";
	String b4_6_3 = "";
	String b4_6_4 = "";
	String b4_7_1 = "";
	String b4_7_2 = "";
	String b4_7_3 = "";
	String b4_7_4 = "";
	String telefono = "";
	
	
	boolean b4_1_5_1;
	boolean b4_1_5_2;
	
	boolean b4_2_5_1;
	boolean b4_2_5_2;
	
	boolean b4_3_5_1;
	boolean b4_3_5_2;
	
	boolean b4_4_5_1;
	boolean b4_4_5_2;
	
	boolean b4_5_5_1;
	boolean b4_5_5_2;
	
	boolean b4_6_5_1;
	boolean b4_6_5_2;
	
	boolean b4_7_5_1;
	boolean b4_7_5_2;
	
	
	
	private String orgId;
	private String idControllo;
	private String tipo;
	private String url;
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getB1Specie() {
		return b1_specie;
	}

	public void setB1Specie(String specie) {
		this.b1_specie = specie;
	}
	
	public String getB2Testo() {
		return b2_testo;
	}

	public void setB2Testo(String b2) {
		this.b2_testo = b2;
	} 
	
	public boolean isB4_1_5_1() {
		return b4_1_5_1;
	}

	public void setB4_1_5_1(boolean b4_1_5) {
		this.b4_1_5_1 = b4_1_5;
	}

	public boolean isB4_2_5_1() {
		return b4_2_5_1;
	}

	public void setB4_2_5_1(boolean b4_2_5) {
		this.b4_2_5_1 = b4_2_5;
	}

	public boolean isB4_3_5_1() {
		return b4_3_5_1;
	}

	public void setB4_3_5_1(boolean b4_3_5) {
		this.b4_3_5_1 = b4_3_5;
	}

	public boolean isB4_4_5_1() {
		return b4_4_5_1;
	}

	public void setB4_4_5_1(boolean b4_4_5) {
		this.b4_4_5_1 = b4_4_5;
	}

	public boolean isB4_5_5_1() {
		return b4_5_5_1;
	}

	public void setB4_5_5_1(boolean b4_5_5) {
		this.b4_5_5_1 = b4_5_5;
	}

	public boolean isB4_6_5_1() {
		return b4_6_5_1;
	}

	public void setB4_6_5_1(boolean b4_6_5) {
		this.b4_6_5_1 = b4_6_5;
	}

	public boolean isB4_7_5_1() {
		return b4_7_5_1;
	}

	public void setB4_7_5_1(boolean b4_7_5) {
		this.b4_7_5_1 = b4_7_5;
	}
	
	
	public boolean isB4_1_5_2() {
		return b4_1_5_2;
	}

	public void setB4_1_5_2(boolean b4_1_5) {
		this.b4_1_5_2 = b4_1_5;
	}

	public boolean isB4_2_5_2() {
		return b4_2_5_2;
	}

	public void setB4_2_5_2(boolean b4_2_5) {
		this.b4_2_5_2 = b4_2_5;
	}

	public boolean isB4_3_5_2() {
		return b4_3_5_2;
	}

	public void setB4_3_5_2(boolean b4_3_5) {
		this.b4_3_5_2 = b4_3_5;
	}

	public boolean isB4_4_5_2() {
		return b4_4_5_2;
	}

	public void setB4_4_5_2(boolean b4_4_5) {
		this.b4_4_5_2 = b4_4_5;
	}

	public boolean isB4_5_5_2() {
		return b4_5_5_2;
	}

	public void setB4_5_5_2(boolean b4_5_5) {
		this.b4_5_5_2 = b4_5_5;
	}

	public boolean isB4_6_5_2() {
		return b4_6_5_2;
	}

	public void setB4_6_5_2(boolean b4_6_5) {
		this.b4_6_5_2 = b4_6_5;
	}

	public boolean isB4_7_5_2() {
		return b4_7_5_2;
	}

	public void setB4_7_5_2(boolean b4_7_5) {
		this.b4_7_5_2 = b4_7_5;
	}
	
	
	
	public boolean isA1_1() {
		return a1_1;
	}
	
	public boolean isA1_2() {
		return a1_2;
	}
	
	public boolean isB1_1() {
		return b1_1;
	}
	
	public boolean isB1_2() {
		return b1_2;
	}
	
	public boolean isB1_3() {
		return b1_3;
	}
	
	public boolean isB1_4() {
		return b1_4;
	}
	
	public boolean isB2_1() {
		return b2_1;
	}
	
	public boolean isB2_2() {
		return b2_2;
	}
	
	public boolean isB2_3() {
		return b2_3;
	}
	
	public boolean isB2_4() {
		return b2_4;
	}
	
	public boolean isB3_1() {
		return b3_1;
	}
	
	public boolean isB3_2() {
		return b3_2;
	}
	
	public boolean isB3_3() {
		return b3_3;
	}
	
	public boolean isB3_4() {
		return b3_4;
	}
	
	public void setA1_1(boolean a1) {
		this.a1_1 = a1;
	}

	public void setA1_2(boolean a1) {
		this.a1_2 = a1;
	}

	public void setB1_1(boolean b1) {
		this.b1_1 = b1;
	}

	public void setB1_2(boolean b2) {
		this.b1_2 = b2;
	}

	public void setB1_3(boolean b3) {
		this.b1_3 = b3;
	}

	public void setB1_4(boolean b4) {
		this.b1_4 = b4;
	}
	
	public void setB2_1(boolean b2) {
		this.b2_1 = b2;
	}

	public void setB2_2(boolean b2) {
		this.b2_2 = b2;
	}
	
	public void setB2_3(boolean b2) {
		this.b2_3 = b2;
	}

	public void setB2_4(boolean b2) {
		this.b2_4 = b2;
	}
	
	public void setB3_1(boolean b3) {
		this.b3_1 = b3;
	}
	
	public void setB3_2(boolean b3) {
		this.b3_2 = b3;
	}	
	
	public void setB3_3(boolean b3) {
		this.b3_3 = b3;
	}
	
	public void setB3_4(boolean b3) {
		this.b3_4 = b3;
	}
	
	public String getA2() {
		return a2;
	}


	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA2_2() {
		return a2_2;
	}

	public void setA2_2(String a2_2) {
		this.a2_2 = a2_2;
	}
	
	public String getA2_1() {
		return a2_1;
	}

	public void setA2_check(String a2) {
		this.a2_check = a2;
	}

	public String getA2_check() {
		return a2_check;
	}
	
	public void setA2_2_check(String a2_2) {
		this.a2_2_check = a2_2;
	}

	public String getA2_2_check() {
		return a2_2_check;
	}
	
	
	public void setA2_1(String a2_1) {
		this.a2_1 = a2_1;
	}
	
	
	public String getA3() {
		return a3;
	}


	public void setA3(String a3) {
		this.a3 = a3;
	}


	public String getA4_1() {
		return a4_1;
	}


	public void setA4_1(String a4_1) {
		this.a4_1 = a4_1;
	}


	public String getA4_2() {
		return a4_2;
	}


	public void setA4_2(String a4_2) {
		this.a4_2 = a4_2;
	}


	public String getA5() {
		return a5;
	}


	public void setA5(String a5) {
		this.a5 = a5;
	}


	public String getA6() {
		return a6;
	}


	public void setA6(String a6) {
		this.a6 = a6;
	}


	public String getA7() {
		return a7;
	}


	public void setA7(String a7) {
		this.a7 = a7;
	}


	public String getA8() {
		return a8;
	}


	public void setA8(String a8) {
		this.a8 = a8;
	}


	public String getA9() {
		return a9;
	}


	public void setA9(String a9) {
		this.a9 = a9;
	}


	public String getA10() {
		return a10;
	}


	public void setA10(String a10) {
		this.a10 = a10;
	}


	public String getA11_1() {
		return a11_1;
	}


	public void setA11_1(String a11_1) {
		this.a11_1 = a11_1;
	}


	public String getA11_2() {
		return a11_2;
	}


	public void setA11_2(String a11_2) {
		this.a11_2 = a11_2;
	}


	public String getA12() {
		return a12;
	}


	public void setA12(String a12) {
		this.a12 = a12;
	}


	public String getA13() {
		return a13;
	}


	public void setA13(String a13) {
		this.a13 = a13;
	}

	public void setA14(String a14) {
		this.a14 = a14;
	}
	
	
	public String getA14() {
		return a14;
	}	
	
	public String getA15() {
		return a15;
	}

	public void setA15(String a15) {
		this.a15 = a15;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	public String getData() {
		return data;
	}

		
	public Sin() {}


	public void setOrgId(String orgId) {
		// TODO Auto-generated method stub
		this.orgId = orgId;
	}

	public String getOrgId() {
		return orgId;
	}


	public void setIdControllo(String id_controllo) {
		// TODO Auto-generated method stub
		this.idControllo = id_controllo;
	}

	public String getIdControllo() {
		return idControllo;
	}


	public void setTipo(String tipoSin) {
		// TODO Auto-generated method stub
		this.tipo = tipoSin;
	}

	public String getTipo() {
		return tipo;
	}

	
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}


	public String getUrl() {
		return url;
	}


	public void setB4_1_1(String value) {
		// TODO Auto-generated method stub
		this.b4_1_1=value;
	}
	

	public String getB4_1_1() {
		return b4_1_1;
	}

	
	public String getB4_1_2() {
		return b4_1_2;
	}


	public void setB4_1_2(String b4_1_2) {
		this.b4_1_2 = b4_1_2;
	}


	public String getB4_1_3() {
		return b4_1_3;
	}


	public void setB4_1_3(String b4_1_3) {
		this.b4_1_3 = b4_1_3;
	}


	public String getB4_1_4() {
		return b4_1_4;
	}


	public void setB4_1_4(String b4_1_4) {
		this.b4_1_4 = b4_1_4;
	}


	public String getB4_2_1() {
		return b4_2_1;
	}


	public void setB4_2_1(String b4_2_1) {
		this.b4_2_1 = b4_2_1;
	}


	public String getB4_2_2() {
		return b4_2_2;
	}


	public void setB4_2_2(String b4_2_2) {
		this.b4_2_2 = b4_2_2;
	}


	public String getB4_2_3() {
		return b4_2_3;
	}


	public void setB4_2_3(String b4_2_3) {
		this.b4_2_3 = b4_2_3;
	}


	public String getB4_2_4() {
		return b4_2_4;
	}


	public void setB4_2_4(String b4_2_4) {
		this.b4_2_4 = b4_2_4;
	}


	public String getB4_3_1() {
		return b4_3_1;
	}


	public void setB4_3_1(String b4_3_1) {
		this.b4_3_1 = b4_3_1;
	}


	public String getB4_3_2() {
		return b4_3_2;
	}


	public void setB4_3_2(String b4_3_2) {
		this.b4_3_2 = b4_3_2;
	}


	public String getB4_3_3() {
		return b4_3_3;
	}


	public void setB4_3_3(String b4_3_3) {
		this.b4_3_3 = b4_3_3;
	}


	public String getB4_3_4() {
		return b4_3_4;
	}


	public void setB4_3_4(String b4_3_4) {
		this.b4_3_4 = b4_3_4;
	}


	public String getB4_4_1() {
		return b4_4_1;
	}


	public void setB4_4_1(String b4_4_1) {
		this.b4_4_1 = b4_4_1;
	}


	public String getB4_4_2() {
		return b4_4_2;
	}


	public void setB4_4_2(String b4_4_2) {
		this.b4_4_2 = b4_4_2;
	}


	public String getB4_4_3() {
		return b4_4_3;
	}


	public void setB4_4_3(String b4_4_3) {
		this.b4_4_3 = b4_4_3;
	}


	public String getB4_4_4() {
		return b4_4_4;
	}


	public void setB4_4_4(String b4_4_4) {
		this.b4_4_4 = b4_4_4;
	}


	public String getB4_5_1() {
		return b4_5_1;
	}


	public void setB4_5_1(String b4_5_1) {
		this.b4_5_1 = b4_5_1;
	}


	public String getB4_5_2() {
		return b4_5_2;
	}


	public void setB4_5_2(String b4_5_2) {
		this.b4_5_2 = b4_5_2;
	}


	public String getB4_5_3() {
		return b4_5_3;
	}


	public void setB4_5_3(String b4_5_3) {
		this.b4_5_3 = b4_5_3;
	}


	public String getB4_5_4() {
		return b4_5_4;
	}


	public void setB4_5_4(String b4_5_4) {
		this.b4_5_4 = b4_5_4;
	}


	public String getB4_6_1() {
		return b4_6_1;
	}


	public void setB4_6_1(String b4_6_1) {
		this.b4_6_1 = b4_6_1;
	}


	public String getB4_6_2() {
		return b4_6_2;
	}


	public void setB4_6_2(String b4_6_2) {
		this.b4_6_2 = b4_6_2;
	}


	public String getB4_6_3() {
		return b4_6_3;
	}


	public void setB4_6_3(String b4_6_3) {
		this.b4_6_3 = b4_6_3;
	}


	public String getB4_6_4() {
		return b4_6_4;
	}


	public void setB4_6_4(String b4_6_4) {
		this.b4_6_4 = b4_6_4;
	}


	public String getB4_7_1() {
		return b4_7_1;
	}


	public void setB4_7_1(String b4_7_1) {
		this.b4_7_1 = b4_7_1;
	}


	public String getB4_7_2() {
		return b4_7_2;
	}


	public void setB4_7_2(String b4_7_2) {
		this.b4_7_2 = b4_7_2;
	}


	public String getB4_7_3() {
		return b4_7_3;
	}


	public void setB4_7_3(String b4_7_3) {
		this.b4_7_3 = b4_7_3;
	}


	public String getB4_7_4() {
		return b4_7_4;
	}


	public void setB4_7_4(String b4_7_4) {
		this.b4_7_4 = b4_7_4;
	}
	
	public boolean isCheckedPrelevatore(int i){
		
		boolean checked = false;
		String checkValue = this.getA2_check();
		if(checkValue != null && checkValue.equals(Integer.toString(i))){
			checked = true;
		}
		else{
			checked = false;
		}
			
		
		return checked;
	}
	
	public boolean isCheckedPuntoWM(int i){
		
		boolean checked = false;
		String checkValue = this.getA2_2_check();
		if(checkValue != null && checkValue.equals(Integer.toString(i))){
			checked = true;
		}
		else {
			checked = false;
		}
			
		
		return checked;
	}
	
}
