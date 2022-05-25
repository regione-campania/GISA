/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
const { Pool } = require('pg')
var types = require('pg').types
var moment = require('moment')

var parseTs = function(val) {
   return val === null ? null : moment(val).format("YYYY-MM-DD HH:mm:ss");
}
var parseDt = function(val) {
  return val === null ? null : moment(val).format("YYYY-MM-DD");
}
types.setTypeParser(types.builtins.TIMESTAMPTZ, parseTs)
types.setTypeParser(types.builtins.TIMESTAMP, parseTs)
types.setTypeParser(types.builtins.DATE, parseDt)

var conf = require('../config/config.js');

const client = new Pool({
  user: conf.dbUser,
  host: conf.dbHost,
  database: conf.dbName,
  password: conf.dbPassword,
  port: conf.dbPort,
})
client.connect();
/*console.log("Server connected to db");
console.log(client.options);*/

exports.client = client;