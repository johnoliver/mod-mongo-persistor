package org.vertx.mods.mongoqueries;

/*
 * Copyright 2013 jClarity Ltd.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://insightfullogic.com">Richard Warburton</a>
 */

import org.vertx.java.core.json.JsonObject;

public class Count extends MongoQuery {

    public Count(String collection) {
        super("count", collection);
    }

    public Count matcher(JsonObject matcher) {
        putObject("matcher", matcher);
        return this;
    }

}
