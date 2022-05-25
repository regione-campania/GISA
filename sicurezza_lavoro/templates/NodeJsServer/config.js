var conf = {
  dev: {
    "httpPort": ,
    "httpsPort": ,
    
    "staticAngularAppPath": "",
    "tokenSecret": "",

    "sslPrivateKeyLocation": "",
    "sslPublicCertLocation": "",

    "dbUser": "",
    "dbHost": "",
    "dbName": "",
    "dbPassword": "",
    "dbPort": ,

    "mailHost": "",
    "mailFrom": "",
    "mailUsername": "",
    "mailPassword": "",
    "mailPort": ,
    "mailCcnTest": "",
    "mailToInvalidator": "",
    "mailSecure": ,

    "hereMapsHost": "",
    "hereMapsApiKey": "",
    "hereMapsGeocodingService": "",

    "ispezioniEnabled": false
  },
  production: {
    "httpPort": ,
    "httpsPort": ,
    
    "staticAngularAppPath": "",
    "tokenSecret": "",

    "sslPrivateKeyLocation": "",
    "sslPublicCertLocation": "",

    "dbUser": "",
    "dbHost": "",
    "dbName": "",
    "dbPassword": "",
    "dbPort": ,

    "mailHost": "",
    "mailFrom": "",
    "mailUsername": "",
    "mailPassword": "",
    "mailPort": ,
    "mailCcnTest": "",
    "mailToInvalidator": "",
    "mailSecure": ,

    "hereMapsHost": "",
    "hereMapsApiKey": "",
    "hereMapsGeocodingService": "",

    "ispezioniEnabled": false
  }
}

const env = process.argv[2];
console.log("########## AMBIENTE: " + env + "##########");
//env = "production" //per prodduzione
if (env == undefined) { //carico dev
  conf = conf.dev;
} else { //carico il conf passato come primo parametro
  conf = conf[env];
}

module.exports = conf;

