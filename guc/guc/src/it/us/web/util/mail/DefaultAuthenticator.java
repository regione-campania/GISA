/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 * Copyright 2001-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.us.web.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * This is a very simple authentication object that can be used for any
 * transport needing basic userName and password type authentication.
 *
 * @author <a href="mailto:quintonm@bellsouth.net">Quinton McCombs</a>
 * @version $Id: DefaultAuthenticator.java,v 1.1 2007/09/14 10:34:52 gnave Exp $
 */
public class DefaultAuthenticator extends Authenticator
{
    /** Stores the login information for authentication */
    private PasswordAuthentication authentication;

    /**
     * Default constructor
     *
     * @param userName user name to use when authentication is requested
     * @param password password to use when authentication is requested
     */
    public DefaultAuthenticator(String userName, String password)
    {
        this.authentication = new PasswordAuthentication(userName, password);
    }

    /**
     * Gets the authentication object that will be used to login to the mail
     * server.
     *
     * @return A <code>PasswordAuthentication</code> object containing the
     *         login information.
     */
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return this.authentication;
    }
}
