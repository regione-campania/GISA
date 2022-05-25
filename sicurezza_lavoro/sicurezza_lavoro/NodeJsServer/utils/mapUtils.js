/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
const https = require('https');
const conf = require('../config/config.js');

var mapUtilts = {

    geocodePromise: function(address){
        return new Promise((resolve, reject) => {
            var geocodingService = conf.hereMapsGeocodingService;
            geocodingService = geocodingService.replace("[KEY]", conf.hereMapsApiKey);
            geocodingService = geocodingService.replace("[ADDRESS]", address);

            var options = {
                host: conf.hereMapsHost,
                path: encodeURI(geocodingService),
            };
            
            https.get(options, function (res) {
                var json = '';
                res.on('data', function (chunk) {
                    json += chunk;
                });
                res.on('end', function () {
                    if (res.statusCode === 200) {
                        try {
                            resolve(json);
                        } catch (e) {
                            console.log(e.stack);
                        }
                    } else {
                        console.log('Status:', res.statusCode);
                    }
                });
            }).on('error', function (err) {
                console.log('Error:', err);
                reject(err);
            });
        })
    },

    geocode: async function (address) {
        try {
            let promise = this.geocodePromise(address);
            return(await promise);
        }
        catch(error) {
            // Promise rejected
            console.log(error);
        }
    }

    
}

module.exports = mapUtilts;
