/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.tiles;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.factory.BasicTilesContainerFactory;

public class ContainerFactory extends BasicTilesContainerFactory {

    protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
            TilesRequestContextFactory contextFactory) {
        List<URL> urls = new ArrayList<URL>();
        try {
            urls.add(applicationContext.getResource("/WEB-INF/tiles-defs.xml"));
        } catch (IOException e) {
            throw new DefinitionsFactoryException(
                    "Cannot load definition URLs", e);
        }
        return urls;
    }
}