package org.vertx.mods.mongo.test.integration.java;
/*
 * Copyright 2013 Red Hat, Inc.
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
 * @author John Oliver
 */

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.Binary;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.mods.MongoUtil;
import org.vertx.testtools.VertxAssert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.testComplete;

public class MongoBsonTest extends PersistorTestParent {

  @Override
  protected JsonObject getConfig() {
    JsonObject config = super.getConfig();
    config.putBoolean("use_mongo_types", true);
    return config;
  }

  @Test
  public void findOneHonoursBson() throws Exception {
    createBsonObjectInDb(new Handler<Message<JsonObject>>() {
                           public void handle(Message<JsonObject> reply) {
                             assertEquals(reply.body().toString(), "ok", reply.body().getString("status"));


                             JsonObject findOne = new JsonObject()
                                     .putString("action", "findone")
                                     .putString("collection", COLLECTION)
                                     .putObject("matcher", new JsonObject());

                             eb.send(ADDRESS, findOne, new Handler<Message<JsonObject>>() {
                               public void handle(Message<JsonObject> reply) {
                                 Number storedDate = reply.body().getObject("result").getObject("date").getNumber("$date");
                                 VertxAssert.assertNotNull(storedDate);
                                 VertxAssert.testComplete();
                               }
                             });
                           }
                         }
    );
  }


  @Test
  public void commandHonoursBson() throws Exception {
    createBsonObjectInDb(new Handler<Message<JsonObject>>() {
                           public void handle(Message<JsonObject> reply) {
                             assertEquals(reply.body().toString(), "ok", reply.body().getString("status"));

                             JsonObject find = new JsonObject()
                                     .putString("action", "command")
                                     .putString("command", "{findAndModify:\"" + COLLECTION + "\",remove:true}");

                             eb.send(ADDRESS, find, new Handler<Message<JsonObject>>() {
                               public void handle(Message<JsonObject> reply) {

                                 JsonObject commandResult = reply.body().getObject("result");
                                 JsonObject removedObject = commandResult.getObject("value");
                                 Number storedDate = removedObject.getObject("date").getNumber("$date");
                                 VertxAssert.assertNotNull(storedDate);
                                 VertxAssert.testComplete();
                               }
                             });
                           }
                         }
    );
  }

  private void createBsonObjectInDb(Handler<Message<JsonObject>> test) {
    deleteAll(new Handler<Message<JsonObject>>() {
      public void handle(Message<JsonObject> reply) {

        final String testValue = "{\"testKey\" : \"testValue\"}";
        final DBObject testValueDb = MongoUtil.convertJsonToBson(testValue);
        testValueDb.put("date", new Date());
        JsonObject document = MongoUtil.convertBsonToJson(testValueDb);

        JsonObject save = new JsonObject()
                .putString("action", "save")
                .putString("collection", COLLECTION)
                .putObject("document", document);

        eb.send(ADDRESS, save, new Handler<Message<JsonObject>>() {
          public void handle(Message<JsonObject> reply) {
            test.handle(reply);
          }
        });
      }
    });
  }

}

