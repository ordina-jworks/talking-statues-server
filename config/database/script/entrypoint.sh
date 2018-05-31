#!/usr/bin/env bash
username="appuser"
password="admin"

mongo talking_statues \
        --host localhost \
        --port 27017 \
        -u admin \
        -p admin \
        --authenticationDatabase admin \
        --eval "db.createUser({user: '$username', pwd: '$password', roles:[{role:'readWrite', db: 'talking_statues'}]});" \

#db.createCollection("monuments", {
#    validator: {
#        $jsonSchema: {
#            bsonType: "object",
#            required: [ "_id", "information", "longitude", "latitude", "area" ],
#            properties: {
#                _id: {
#                    bsonType: "string",
#                    description: "must be a string and is required"
#                },
#                information: {
#                    bsonType: "object",
#                    required: [ "language", "name", "description" ],
#                    language: {
#                        enum: [ "NL", "FR", "EN", "DE" ],
#                        description: "can only be one of the enum values and is required"
#                    },
#                    name: {
#                        bsonType: "string",
#                        description: "must be a string and is required"
#                    },
#                    description: {
#                        bsonType: "string",
#                        description: "must be a string and is required"
#                    },
#                    question: {
#                        bsonType: "object",
#                        required: [ "question", "answer" ],
#                        question: {
#                            bsonType: "string",
#                            description: "must be a string and is required"
#                        },
#                        answer: {
#                            bsonType: "string",
#                            description: "must be a string and is required"
#                        }
#                    },
#                    longitude: {
#                        bsonType: "double",
#                        description: "must be a double and is not required"
#                    },
#                    latitude: {
#                        bsonType: "double",
#                        description: "must be a double and is not required"
#                    },
#                    area: {
#                        bsonType: "string",
#                        description: "must be a string and is required"
#                    }
#                }
#            }
#        }
#    }});
#
#db.createUser({ "user" : "appUser",
#    "pwd" : "1234",
#    "roles" : [
#        {
#            "role" : "ROLE_USER",
#            "db" : "talking_statues"
#        } ]
#});