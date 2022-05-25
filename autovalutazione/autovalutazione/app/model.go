/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package main

type Ml struct {
	N_id 	int		`gorm:"type:varchar" json:"N_id"`
	N_desc 	string 	`gorm:"type:varchar" json:"N_desc"`	
}
type Mac struct {
	M_id 	int		`gorm:"type:varchar" json:"M_id"`
	M_desc 	string 	`gorm:"type:varchar" json:"M_desc"`	
}
type Agg struct {
	A_id 	int		`gorm:"type:varchar" json:"A_id"`
	A_desc 	string 	`gorm:"type:varchar" json:"A_desc"`	
}
type Lda struct {
	L_id 	int		`gorm:"type:varchar" json:"L_id"`
	L_desc 	string 	`gorm:"type:varchar" json:"L_desc"`	
}

type Cl struct {
	L_id 	int		`gorm:"type:int" json:"L_id"`
	L_desc 	string 	`gorm:"type:varchar" json:"L_desc"`	
	Ver		string 	`gorm:"type:varchar" json:"Ver"	`
}

type Chp struct {
	C_id 	int		`gorm:"type:int" json:"C_id"`
	C_desc 	string 	`gorm:"type:varchar" json:"C_desc"`	
}

type Qst struct {
	Q_id 			int		`gorm:"type:int" json:"Q_id"`
	Domanda 		string 	`gorm:"type:varchar" json:"Domanda"`	
	SottoDomanda 	string 	`gorm:"type:varchar" json:"SottoDomanda"`	
	Punti_si 		int 	`gorm:"type:int" json:"Punti_si"`	
	Punti_no 		int 	`gorm:"type:int" json:"Punti_no"`	
}

type Qst_1 struct {
	Q_id 			int		`gorm:"type:int" json:"Q_id"`
	C_desc	 		string 	`gorm:"type:varchar" json:"C_desc"`	
	Domanda 		string 	`gorm:"type:varchar" json:"Domanda"`	
	SottoDomanda 	string 	`gorm:"type:varchar" json:"SottoDomanda"`	
	Punti_si 		int 	`gorm:"type:int" json:"Punti_si"`	
	Punti_no 		int 	`gorm:"type:int" json:"Punti_no"`	
	Risposta 		string 	`gorm:"type:varchar" json:"Risposta"`	
	Punti_Risposta  int 	`gorm:"type:int" json:"Punti_Risposta"`
	Id_Cl 			int 	`gorm:"type:int" json:"Id_Cl"`	
	Row_Number 		int 	`gorm:"type:int" json:"Row_Number"`	
}

type Qst_2 struct {
	Id_Domanda 		string	`gorm:"type:varchar" json:"Id_Domanda"`
	Capitolo	 	string 	`gorm:"type:varchar" json:"Capitolo"`	
	Domanda 		string 	`gorm:"type:varchar" json:"Domanda"`	
	SottoDomanda 	string 	`gorm:"type:varchar" json:"SottoDomanda"`	
	Punti_Risposta 	int 	`gorm:"type:int" json:"Punti_Risposta"`	
	Risposta 		string 	`gorm:"type:varchar" json:"Risposta"`
	Id_Cl 			int 	`gorm:"type:int" json:"Id_Cl"`	
}

type Sz struct {
	Id 			int		`gorm:"type:int" json:"Id"`
	Doc	 		string 	`gorm:"type:varchar" json:"Doc"`	
	Code	 	string 	`gorm:"type:varchar" json:"Code"`	
	Tit	 		string 	`gorm:"type:varchar" json:"Tit"`	
	ord	 		string 	`gorm:"type:varchar" json:"ord"`	
}

type User struct {
	Id 			int		`gorm:"type:int" json:"Id"`
}


type LoginData struct {
	Username 		string		`gorm:"type:varchar" json:"username"`
	Cf 				string		`gorm:"type:varchar" json:"cf"`
	Role 			string		`gorm:"type:varchar" json:"role"`
	Role_id 		string		`gorm:"type:varchar" json:"role_id"`
	Last_login 		string		`gorm:"type:varchar" json:"last_login"`
	User_id 		string		`gorm:"type:varchar" json:"user_id"`
	Id_asl 			string		`gorm:"type:varchar" json:"id_asl"`
	Riferimento_id 	string		`gorm:"type:varchar" json:"riferimento_id"`
	Riferimento_id_nome 			string		`gorm:"type:varchar" json:"riferimento_id_nome"`	
	Riferimento_id_nome_tab			string		`gorm:"type:varchar" json:"riferimento_id_nome_tab"`
	Opu_stabilimento 	string		`gorm:"type:varchar" json:"opu_stabilimento"`
	Ragione_sociale 	string		`gorm:"type:varchar" json:"ragione_sociale"`
	Dati_anag 			string		`gorm:"type:varchar" json:"dati_anag"`
	Nome 				string		`gorm:"type:varchar" json:"nome"`
	Cognome 			string		`gorm:"type:varchar" json:"cognome"`
}

