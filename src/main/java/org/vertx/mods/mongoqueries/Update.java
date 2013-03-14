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

public class Update extends MongoQuery {

    public Update(String collection) {
        super("update", collection);
    }

    public Update criteria(JsonObject matcher) {
        putObject("criteria", matcher);
        return this;
    }

    public Update objNew(ObjNew values) {
        putObject("objNew", values);
        return this;
    }

    public Update upsert(boolean upsert) {
        putBoolean("upsert", upsert);
        return this;
    }

    public Update multi(boolean multi) {
        putBoolean("multi", multi);
        return this;
    }

}
