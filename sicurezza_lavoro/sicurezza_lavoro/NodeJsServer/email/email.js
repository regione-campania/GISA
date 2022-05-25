/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var nodemailer = require('nodemailer');
var conf = require('../config/config.js');


function sendEmailAttivazioneNotifica(filename, content, valori) {

  var credentials = {
    host: conf.mailHost,
    port: conf.mailPort,
    secure: conf.mailSecure,
    tls: {
      rejectUnauthorized: false
    },
    auth: {
      user: conf.mailUsername,
      pass: conf.mailPassword
    }
  }

  var transporter = nodemailer.createTransport(credentials);

  const cun = filename.split("_")[0];

  var to = '';
  if (conf.mailToInvalidator == '') { //invio la mail agli effettivi destinatari solo se l'invalidator è vuoto (a scopo di test), in caso contrario lo invio ai tester
    to = valori.cantiere.indirizzo_mail + `${conf.mailToInvalidator}`; //indirizzo mail dell'asl

    valori.persona_ruoli.forEach(function (persona) { // indirizzo mail del resposabile
      if (persona.id_ruolo == 2)
        to += ", " + persona.pec + `${conf.mailToInvalidator}`;
    })
  } else {
    to = conf.mailCcnTest;
  }



  var mailOptions = {
    from: conf.mailFrom,
    to: to,
    bcc: conf.mailCcnTest,
    subject: `Gisa Sicurezza lavoro - Nuova notifica preliminare - CUN ${cun}`,
    html: `<p>Si allega quanto in oggetto</p>
        <p><strong>GISA - Sicurezza e prevenzione sui luoghi di lavoro</strong></p>`,
    attachments: {
      filename: filename,
      content: content
    }
  };

  transporter.sendMail(mailOptions, function (error, info) {
    if (error) {
      console.log(error);
    } else {
      console.log('Email notifica sent: ' + info.response);
    }
    //res.end();
  });
}


function sendEmailSupporto(nomeSegnalante, titolo, messaggio, emailSegnalante, telefonoSegnalante){

  var credentials = {
    host: conf.mailHost,
    port: conf.mailPort,
    secure: conf.mailSecure,
    tls: {
      rejectUnauthorized: false
    },
    auth: {
      user: conf.mailUsername,
      pass: conf.mailPassword
    }
  }

  var transporter = nodemailer.createTransport(credentials);

  var mailOptions = {
    from: conf.mailFrom,
    to: conf.mailCcnTest,
    //bcc: conf.mailCcnTest,
    subject: `Gisa Sicurezza lavoro - SUPPORTO [${titolo}][${nomeSegnalante}]`,
    html: `<p>${messaggio}</p>
          <p>Dati di ricontatto: ${emailSegnalante}  -  ${telefonoSegnalante}</p>`,
  };

  transporter.sendMail(mailOptions, function (error, info) {
    if (error) {
      console.log(error);
    } else {
      console.log('Email supporto sent: ' + info.response);
    }
    //res.end();
  });

}



function testMail(){

  var credentials = {
    host: "smtp.pec.actalis.it",
    port: 465,
    secure: true,
    tls: {
      rejectUnauthorized: false
    },
    auth: {
      user: "notificapreliminare@pec.regione.campania.it",
      pass: "Notifica?2"
    }
  }

  var transporter = nodemailer.createTransport(credentials);

  var mailOptions = {
    from: "notificapreliminare@pec.regione.campania.it",
    to: conf.mailCcnTest,
    //bcc: conf.mailCcnTest,
    subject: ``,
    html: ``,
  };

  transporter.sendMail(mailOptions, function (error, info) {
    if (error) {
      console.log(error);
    } else {
      console.log('Email supporto sent: ' + info.response);
    }
    //res.end();
  });

}




exports.sendEmailAttivazioneNotifica = sendEmailAttivazioneNotifica;
exports.sendEmailSupporto = sendEmailSupporto;
exports.testMail = testMail;
