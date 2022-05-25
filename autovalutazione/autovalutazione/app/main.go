/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package main

import (

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/kataras/iris/v12"
	"github.com/kataras/iris/v12/sessions"
	"log"
	"fmt"
	"strings"
	"strconv"
	"io/ioutil"
	wkhtml "github.com/SebastiaanKlippert/go-wkhtmltopdf"
	"net/http"
    "net/url"
	"encoding/json"
	"time"
	"bytes"
)

var (
    cookieNameForSessionID = "mycookiesessionnameid"
    sess                   = sessions.New(sessions.Config{Cookie: cookieNameForSessionID})
)

var dbUrl = ""
var dbPort = ""
var dbName = ""
var dbUser = ""
var dbPasswd = ""
var port = ""
var endPointDocumentale = ""
var endPointLogin = ""
var connection = ""
func main() {
	setConfigFile("config/config.json");
	dbUrl = getProperty("dbUrl")
	dbPort = getProperty("dbPort")
	dbName = getProperty("dbName")
	dbUser = getProperty("dbUser")
	if getProperty("dbPasswd") == "" {
		dbPasswd = ""
	}else{
		dbPasswd = "password=" + getProperty("dbPasswd")
	}
	port = getProperty("port")
	endPointDocumentale = getProperty("endPointDocumentale")
	endPointLogin = getProperty("endPointLogin")
	connection = "host="+dbUrl+" port="+dbPort+" user="+dbUser+" dbname="+dbName+" "+dbPasswd+" sslmode=disable"
	db, err := gorm.Open(
		"postgres",
		connection,
	)

	if err != nil {
		panic(connection + " " + err.Error())
	}

	defer func() {
		_ = db.Close()
	}()

	app := newApp(db)
	
	app.Logger().SetLevel("info")
	//app.Logger().SetLevel("debug")

	app.Logger().Infof("\n ========================== \n Config file %s ", getConfig())

	// Start the web server on port ...
	app.Run(iris.Addr(":"+port), iris.WithCharset("utf-8"), iris.WithoutServerError(iris.ErrServerClosed))
}


func newApp(db *gorm.DB) *iris.Application {

	app := iris.New()
	
	tmpl := iris.HTML("./templates", ".html")
	
	// Enable re-build html on template files changes.
	tmpl.Reload(true)
	app.RegisterView(tmpl)

	//app.StaticWeb("/static", "./static")
	app.HandleDir("/static", iris.Dir("./static"))

	app.OnErrorCode(iris.StatusInternalServerError, func(ctx iris.Context) {

		errMessage := ctx.Values().GetString("error")
		if errMessage != "" {
			ctx.Writef("Internal server error: %s", errMessage)
			return
		}

		ctx.Writef("(Unexpected) internal server error")
	})
	
	app.Get("/home", func(ctx iris.Context) {
		
      ctx.View("home.html")
		
	})
	
	app.Get("/", func(ctx iris.Context) {
		
    //  ctx.View("main.html")
      ctx.View("login.html")
		
	})
	
	app.Get("/doc", func(ctx iris.Context) {
		
      	Szs := []Sz{}
        db.Raw("select id, doc, code, tit from appdocu order by ord::integer ").Find(&Szs)
		app.Logger().Infof("%v", Szs)
		ctx.ViewData("Szs", Szs)
		ctx.View("doc.html")
		
	})
	app.Get("/ml", func(ctx iris.Context) {

		session := sess.Start(ctx)

		Mls := []Ml{}
        db.Raw("select id_norma n_id, norma n_desc from norma(?::text, ?::text) ", session.GetString("osaId"), session.GetString("osaTab")).Find(&Mls)

		//app.Logger().Infof("%v", Mls)

		ctx.ViewData("Mls", Mls)
		ctx.View("ml.html")
	})
	
	app.Get("/ml_base", func(ctx iris.Context) {
/*
		Mls := []Ml{}
        db.Raw("select id_norma n_id, norma n_desc from norma ").Find(&Mls)

		//app.Logger().Infof("%v", Mls)

		ctx.ViewData("Mls", Mls)
	*/	
		ctx.View("ml_base.html")
	})

	
	app.Get("/get_mac/{idnor:int}", func(ctx iris.Context) {

		session := sess.Start(ctx)
		
		id :=""
		Macs := []Mac{}
		
		int_id,_ := ctx.Params().GetInt("idnor")
		
		if int_id==-1 {
			id = "%"
		} else {
			id = strconv.Itoa(int_id)
		}
		
		app.Logger().Infof(" Macros of N_id: %d", id)

		//db.Raw("select id_macroarea m_id, macroarea || '-' || norma  m_desc from macroarea where id_norma like ? ", id).Find(&Macs)
		//db.Raw("select distinct id_macroarea m_id, macroarea || ' (' || norma ||')' m_desc from ml10 where id_norma like ? ", id).Find(&Macs)
		db.Raw("select distinct id_macroarea m_id, macroarea m_desc from ml10(?::text, ?::text) where id_norma like ? order by 2", session.GetString("osaId"), session.GetString("osaTab"), id).Find(&Macs)
		qry:= fmt.Sprintf("select distinct id_macroarea m_id, macroarea m_desc from ml10(%s::text, %s::text) where id_norma like '%s' order by 2", session.GetString("osaId"), session.GetString("osaTab"), id)
		fmt.Println("###"+ qry)

		ctx.ViewData("Macs", Macs)
		ctx.View("mac.html")
	
	})

	app.Get("/get_agg/{idmac:int}", func(ctx iris.Context) {

		session := sess.Start(ctx)
		
		Aggs := []Agg{}
		
		id,_ := ctx.Params().GetInt("idmac")
		
		app.Logger().Infof(" Aggrs of M_id: %s", id)

		db.Raw("select id_aggregazione a_id, aggregazione a_desc from aggregazione(?::text, ?::text) where id_macroarea=? ", session.GetString("osaId"), session.GetString("osaTab"), id).Find(&Aggs)

		ctx.ViewData("Aggs", Aggs)
		ctx.View("agg.html")
	
	})

	app.Get("/get_lda/{idagg:int}", func(ctx iris.Context) {

		session := sess.Start(ctx)
		
		Ldas := []Lda{}
		
		id,_ := ctx.Params().GetInt("idagg")
		
		app.Logger().Infof(" Lda of A_id: %s", id)

		db.Raw("select id_linea l_id, attivita l_desc from lda(?::text, ?::text) where id_aggregazione=? ", session.GetString("osaId"), session.GetString("osaTab"), id).Find(&Ldas)

		ctx.ViewData("Ldas", Ldas)
		ctx.View("lda.html")
	
	})

	app.Get("/get_cls/{idlda:int}", func(ctx iris.Context) {
		
		idLda,_ := ctx.Params().GetInt("idlda")

		app.Logger().Infof(" Cl of Lda_id: %s", idLda)

		Cls := []Cl{}
		db.Raw("select code as L_id, description as l_desc, versione as ver from public.get_checklist_by_idlinea(?)", idLda).Find(&Cls)

		if (len(Cls) == 1){ //nuova gestione 
			if(Cls[0].L_id == -1){ //una row con -1 (linea categorizzabile senza chklist associate)
				//ctx.Redirect("/cl_1") //carico tutte le checklist
				//ctx.Redirect("/cl_1") //carico tutte le checklist
				Cls = []Cl{}
				ctx.ViewData("Cls", Cls)
				ctx.View("cl_1.html")
			}else{
				ctx.ViewData("Cls", Cls) //una row con chklist la visualizzo
				ctx.View("cl_1.html")
			}
		}else{
			ctx.ViewData("Cls", Cls) //più row, le visualizzo. Zero row darà pagina vuota
			ctx.View("cl_1.html")
		}
	})


	app.Get("/cl", func(ctx iris.Context) {

		Cls := []Cl{}
        db.Raw("select l_id, l_desc, ver from cl_lists order by 1 ").Find(&Cls)

		//app.Logger().Infof("%v", Cls)

		ctx.ViewData("Cls", Cls)
		ctx.View("cl.html")
	})

	app.Get("/cl_1", func(ctx iris.Context) {

		Cls := []Cl{}
        db.Raw("select l_id, l_desc, ver from cl_lists order by 1 ").Find(&Cls)
		//app.Logger().Infof("%v", Cls)

		ctx.ViewData("Cls", Cls)
		ctx.View("cl_1.html")
	})

app.Get("/get_chpqst/{idcl:int}", func(ctx iris.Context) {
		
	Qsts_1 := []Qst_1{}
	
	id,_ := ctx.Params().GetInt("idcl")

	session := sess.Start(ctx)

	Cls := []Cl{}
	db.Raw("select l_id, l_desc, ver from cl_lists where l_id = ? ", id).Find(&Cls)
	session.Set("selectedCl", string(Cls[0].L_desc))

	db.Raw("select *, row_number() over() from (select cq.q_id, cq.c_desc, unaccent(cq.domanda) domanda, unaccent(cq.sotto_domanda) sotto_domanda, cq.punti_si, cq.punti_no, coalesce(ur.risposta, '') risposta, coalesce(ur.punteggio, 0) punti_risposta, ? id_cl from cl_all cq left join utente_risposta ur on cq.q_id = ur.id_domanda and ur.id_utente = ? where l_id = ? order by 1) foo", id, session.Get("idUser"), id).Find(&Qsts_1)
	
	/*json, _ := json.Marshal(Qsts_1)
	app.Logger().Infof("%v", string(json))*/

	Us := User{}
	var userId,_ = strconv.Atoi(session.GetString("idUser"))
	Us.Id = userId

	ctx.ViewData("UserId", userId)
	ctx.ViewData("Qsts_1", Qsts_1)
	ctx.View("chpqst.html")
	
	})

	
app.Get("/get_chp/{idcl:int}", func(ctx iris.Context) {
		
	Chps := []Chp{}
	
	id,_ := ctx.Params().GetInt("idcl")
	
	app.Logger().Infof(" chapters of l_id: %d", id)

	db.Raw("select c_id, c_desc from cl_chapts where l_id=? AND not c_is_disabilitato order by 1", id).Find(&Chps)

	ctx.ViewData("Chps", Chps)
	ctx.View("chp.html")
	
	})

app.Get("/get_qst/{idchp:int}", func(ctx iris.Context) {
		
	Qsts := []Qst{}
	
	id,_ := ctx.Params().GetInt("idchp")
	
	app.Logger().Infof(" chapter of idchp: %d", id)

	db.Raw("select q_id, domanda, sotto_domanda, punti_si, punti_no from cl_quests cq where id_chap=? order by 1", id).Find(&Qsts)

	ctx.ViewData("Qsts", Qsts)
	ctx.View("qst.html")
	
	})

app.Post("/save_cl",  func(ctx iris.Context) {

	session := sess.Start(ctx)
	Qst_2 := []Qst_2{}
	err := ctx.ReadJSON(&Qst_2)
	if (err != nil) {
		app.Logger().Info("Errore lettura json")
		app.Logger().Info(err)
		return
	}

	var id_cl = Qst_2[0].Id_Cl

	db.Exec("insert into log_checklist (id_checklist, id_utente, entered) values (?, ?, current_timestamp)", id_cl, session.Get("idUser"))

	db.Exec("delete from utente_risposta where id_checklist = ?  and id_utente = ?", id_cl, session.Get("idUser"))

	for _, ans := range Qst_2 {	
		if (ans.Risposta != "") {	
			/*
			non è corretta per quando si disabilitano delle sottodomande, bisogna fare prima la delete dell intera cl
			db.Exec("insert into utente_risposta (id_utente, id_domanda, punteggio, risposta, id_checklist) values ( ?, ?, ?, ?, ?) on conflict (id_utente, id_domanda, id_checklist) do update set punteggio = ?, risposta = ?, entered = current_timestamp", 
				session.Get("idUser"), ans.Id_Domanda, ans.Punti_Risposta, ans.Risposta, ans.Id_Cl, ans.Punti_Risposta, ans.Risposta);*/ 
			db.Exec("insert into utente_risposta (id_utente, id_domanda, punteggio, risposta, id_checklist, entered) values ( ?, ?, ?, ?, ?, current_timestamp)", session.Get("idUser"), ans.Id_Domanda, ans.Punti_Risposta, ans.Risposta, ans.Id_Cl)
		}
	}
})

app.Post("/delete_cl",  func(ctx iris.Context) {

	session := sess.Start(ctx)
	Qst_2 := []Qst_2{}
	err := ctx.ReadJSON(&Qst_2)
	if (err != nil) {
		app.Logger().Info("Errore lettura json")
		app.Logger().Info(err)
		return
	}
	var id_cl = Qst_2[0].Id_Cl
	db.Exec("delete from utente_risposta where id_checklist = ?  and id_utente = ?", id_cl, session.Get("idUser"))

	/*for _, ans := range Qst_2 {	
		if (ans.Risposta != "") {	
			//db.Exec("delete from utente_risposta where id_domanda in (select q_id from cl_all where l_id in (select l_id from cl_all where q_id = ?) ) and id_utente = ?", ans.Id_Domanda, session.Get("idUser"))
			db.Exec("delete from utente_risposta where id_checklist = ?  and id_utente = ?", ans.Id_Cl, ans.Id_Domanda, session.Get("idUser"))
		}
	}*/
})


app.Get("/login",  func(ctx iris.Context) {
		app.Logger().Info("Errore chiamata login")
		ctx.Redirect("/")//, iris.StatusPermanentRedirect)
	})
	
app.Post("/login",  func(ctx iris.Context) {
	    
		session := sess.Start(ctx)
		
		app.Logger().Infof("New login attempt from %s", ctx.RemoteAddr())

		user := ctx.FormValue("user") 
		password := ctx.FormValue("password")
		cf := ctx.FormValue("cfSpid") 

		var jsonReq = []byte("{\"user\":\""+ user +"\", \"password\":\""+ password +"\" , \"cf\":\""+ cf +"\" }")
		app.Logger().Infof("DEBUG REQ ---> %s", string(jsonReq))

		session.Set("username", string(ctx.FormValue("nameSpid")))

    	req, err := http.NewRequest("POST", endPointLogin, bytes.NewBuffer(jsonReq))
		req.Header.Set("Content-Type", "application/json")

		client := &http.Client{}
		resp, err := client.Do(req)
		if err != nil {
			panic(err)
		}
		defer resp.Body.Close()
		body, _ := ioutil.ReadAll(resp.Body)
		var jsonStr = string(body) 
		app.Logger().Info(jsonStr)

		var userId = ""
		var osaType = ""
		var osaTab = "null"
		var osaId = "null"
		var idAsl = "-1";
		var ragione_sociale = "";
		if  (user == "admin" && password == "20071113!Reg") {
			userId = "1"
		} else if (jsonStr == "{}") {
			app.Logger().Infof("User <%s> o Password <%s> non validi", user, password)
			var errore = "Utente e/o Password non validi"
			if (cf != ""){
				errore = "Codice fiscale non registrato"
			}
			ctx.ViewData("Errore", errore)
			ctx.View("login.html")
			return
		} else {
			d := map[string]interface{}{}
			json.Unmarshal([]byte(jsonStr), &d)
			var messaggio = d["messaggio"].(string)

			app.Logger().Infof("DEBUG MESSAGGIO ---> %s", messaggio)


			var dd []LoginData
			json.Unmarshal([]byte(messaggio), &dd)

			app.Logger().Infof("DEBUG DD ---> %d", len(dd))

			if ( len(dd) == 0){
				app.Logger().Infof("User <%s> non ha stabilimento associato", user)
				var errore = "L'utente " + user + " non ha OSA associato"
				if (cf != ""){
					errore = "Il codice fiscale " + cf + " non ha OSA associato"
				}
				ctx.ViewData("Errore", errore)
				ctx.View("login.html")
				return
			}else{
				session.Set("LoginData", dd)
				ctx.ViewData("LoginData", dd)
				ctx.View("stabs.html")
				return
			}
			/*user = dd["username"].(string)
			userId = dd["user_id"].(string)
			osaType = dd["riferimento_id_nome"].(string)
			osaId = dd["riferimento_id"].(string)
			osaTab = dd["riferimento_id_nome_tab"].(string)
			idAsl = dd["id_asl"].(string)
			ragione_sociale = dd["ragione_sociale"].(string)*/
			session.Set("authenticated", true)
			session.Set("idUser", userId)
			session.Set("osaId", osaId)
			session.Set("osaType", osaType)
			session.Set("osaTab", osaTab)
			session.Set("ragione_sociale", ragione_sociale)
			session.Set("id_asl", idAsl)

		}
	})

app.Get("/postLogin", func(ctx iris.Context) {

	session := sess.Start(ctx)
	app.Logger().Infof("DEBUG POSTLOGIN ---> %s %s %s",  ctx.URLParam("rif_id"),
		ctx.URLParam("rif_nome"),
		ctx.URLParam("rif_tab"))

	var loginData = session.Get("LoginData").([]LoginData)
	app.Logger().Infof("DEBUG POSTLOGIN ---> %s", loginData)
	for _, d := range loginData {	
		if(d.Riferimento_id == ctx.URLParam("rif_id") && d.Riferimento_id_nome == ctx.URLParam("rif_nome") && d.Riferimento_id_nome_tab == ctx.URLParam("rif_tab")) {
			app.Logger().Infof("DEBUG TROVATO ---> %v",  d)
			session.Set("authenticated", true)
			session.Set("idUser", d.User_id)
			session.Set("osaId", d.Riferimento_id)
			session.Set("osaType", d.Riferimento_id_nome)
			session.Set("osaTab", d.Riferimento_id_nome_tab)
			//session.Set("username", d.Riferimento_id)
			session.Set("ragione_sociale", d.Ragione_sociale)
			session.Set("id_asl", d.Id_asl)
			app.Logger().Infof("user %s authenticated", d.Username)
			db.Exec("insert into log_access (id_utente, entered, id_asl, ip) values (?, current_timestamp, ?, ?)", d.User_id, d.Id_asl, ctx.RemoteAddr())
			ctx.ViewData("Ragione_Sociale", d.Ragione_sociale)
			ctx.ViewData("Username", session.GetString("username"))
			ctx.View("main.html")
		}
	}

	/*app.Logger().Info(userId)
	// Set user as authenticated
	session.Set("authenticated", true)
	session.Set("idUser", userId)
	session.Set("osaId", osaId)
	session.Set("osaType", osaType)
	session.Set("osaTab", osaTab)
	session.Set("username", user)
	session.Set("ragione_sociale", ragione_sociale)

	app.Logger().Infof("user %s authenticated", user)
	//db.Exec("insert into log_access (id_utente, first_login, last_login, id_asl) values (?, current_timestamp, current_timestamp, ?) on conflict (id_utente) do update set last_login = current_timestamp, id_asl = ?", session.Get("idUser"), idAsl, idAsl)
	db.Exec("insert into log_access (id_utente, entered, id_asl, ip) values (?, current_timestamp, ?, ?)", session.Get("idUser"), idAsl, ctx.RemoteAddr())
	ctx.ViewData("Ragione_Sociale", ragione_sociale)
	ctx.ViewData("Username", user)
	ctx.View("main.html")*/
})

app.Get("/public",  func(ctx iris.Context) {
	    
		session := sess.Start(ctx)

		var auth = false
		auth, _ = session.GetBoolean("authenticated");
		app.Logger().Infof("authenticated, %s", auth)
		//if(!auth){
		
			app.Logger().Infof("New public user from %s", ctx.RemoteAddr())

			//var userId = ""
			var osaType = ""
			var osaTab = "null"
			var osaId = "null"
			var idAsl = "-1";

			// Set user as authenticated
			session.Set("authenticated", true)
			session.Set("osaId", osaId)
			session.Set("osaType", osaType)
			session.Set("osaTab", osaTab)

			Users := []User{}

			db.Raw("select -nextval('public_user_id_seq') as id").Find(&Users)

			session.Set("idUser", Users[0].Id)
			session.Set("username", strconv.Itoa(Users[0].Id))

			app.Logger().Infof("Public user authenticated, ID: %s", Users[0].Id)

			db.Exec("insert into log_access (id_utente, entered, id_asl, ip) values (?, current_timestamp, ?, ?)", session.Get("idUser"), idAsl, ctx.RemoteAddr())
		//}
		
		ctx.ViewData("Username", "Ospite: " + session.GetString("username"))
		ctx.View("main.html")
	})


app.Get("/test", func(ctx iris.Context) {
		ctx.View("/test.html")
	
	})
	

app.Post("/h2p", func(ctx iris.Context ){

		session := sess.Start(ctx)

		currentTime := time.Now()
		stringTime := currentTime.Format("2006_01_02_15_04_05")

		var clString = strings.Replace(session.GetString("selectedCl"), " ", "_", -1)

		var filename = "Giava_Autovalutazione_"+clString+"_"+stringTime+".pdf"
		var filepdf ="/tmp/"+filename

	//	htmlStr := ctx.PostValue("htmlStr") 

		pdfg, err :=  wkhtml.NewPDFGenerator()
		if err != nil {
		  log.Fatal(err)
		}

		rawBodyAsBytes, err := ioutil.ReadAll(ctx.Request().Body)
		if err != nil {
		  log.Fatal(err)
		}  
		htmlStr := string(rawBodyAsBytes)

		//app.Logger().Info( "htmlStr\n", htmlStr, "\nend")

		pdfg.AddPage(wkhtml.NewPageReader(strings.NewReader(htmlStr)))		

		// Create PDF document in internal buffer
		err = pdfg.Create()
		if err != nil {
		  log.Fatal(err)
		}		

		/*		app.Logger().Infof("serving file %s\n",  filepdf)
		//ctx.Header("Content-type", "application/pdf")
	
		ctx.SendFile(filepdf, filepdf)
		*/

		pdfg.AddPage(wkhtml.NewPageReader(strings.NewReader(htmlStr)))		

		err = pdfg.WriteFile(filepdf) //scrivo file tpm da inviare a Documentale
		if err != nil {
		  log.Fatal(err)
		}

		if(session.GetString("osaType") != "null" && session.GetString("osaId") != "null"){
			content, err := ioutil.ReadFile(filepdf)
			if err != nil {
				log.Fatal(err)
			}

			data := url.Values{
				"baString": {string(content)},
				"provenienza":{"gisa_nt"},
				"tipoCertificato":{"CHECKLIST_GIAVA"},
				session.GetString("osaType"):{session.GetString("osaId")},
				"oggetto":{filename},
				"parentId":{"-1"},
				"folderId":{"-1"},
				"filename": {filename},
				"fileDimension": {string(len(string(content)))},
				"idUtente":{session.GetString("idUser")},
				"ipUtente":{ctx.RemoteAddr()},
			}
			app.Logger().Info( "Post documentale:\n", 
				url.Values{
					"provenienza":{"gisa_nt"},
					"tipoCertificato":{"CHECKLIST_GIAVA"},
					session.GetString("osaType"):{session.GetString("osaId")},
					"oggetto":{filename},
					"parentId":{"-1"},
					"folderId":{"-1"},
					"filename": {filename},
					"fileDimension": {string(len(string(content)))},
					"idUtente":{session.GetString("idUser")},
					"ipUtente":{ctx.RemoteAddr()},
				}, "\nend")

			resp, err := http.PostForm(endPointDocumentale, data)
			if err != nil {
				log.Fatal(err)
			}
			var res map[string]interface{}
			json.NewDecoder(resp.Body).Decode(&res)
			app.Logger().Info(resp)
		}

		ctx.ContentType("application/pdf")
		pdfg.SetOutput(ctx.ResponseWriter()) //imposto l'output per il browser
		ctx.SendFile(filepdf, filepdf)
	
	} )

	app.Get("/logout", func(ctx iris.Context) {
		session := sess.Start(ctx)
		session.Set("authenticated", false)
		var auth = false
		auth, _ = session.GetBoolean("authenticated");
		app.Logger().Infof("authenticated, %s", auth)
		//  ctx.View("main.html")
		ctx.Redirect("/")
			
	})
		
	return app
}




/*func logout(ctx iris.Context) {

    session := sess.Start(ctx)

    // Revoke users authentication
    session.Set("authenticated", false)
    // Or to remove the variable:
    session.Delete("authenticated")
    // Or destroy the whole session:
    session.Destroy()
}*/
