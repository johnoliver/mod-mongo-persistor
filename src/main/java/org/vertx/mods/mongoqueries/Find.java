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

public class Find extends MongoQuery {

    public Find(String collection) {
        super("find", collection);
    }

    public Find matcher(JsonObject matcher) {
        putObject("matcher", matcher);
        return this;
    }

    public Find keys(JsonObject keys) {
        putObject("keys", keys);
        return this;
    }

    public Find sort(JsonObject by) {
        putObject("sort", by);
        return this;
    }

    public Find hasKeys(String key) {
        return keys(new JsonObject().putBoolean(key, true));
    }

    public Find limit(int numberOfReplies) {
        putNumber("limit", numberOfReplies);
        return this;
    }

}
