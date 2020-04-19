package com.r00ta.telematics.platform.authentication.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtSchema {
    @JsonProperty("groups")
    public Set<String> groups;

    @JsonProperty("birthdate")
    public String birthdate;

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("iss")
    public String iss = "https://quarkus.io/using-jwt-rbac";

    public static JwtSchema createUserJwtSchema(String userId){
        JwtSchema schema = new JwtSchema();
        schema.groups = Set.of("User");
        schema.birthdate = "2001-07-13";
        schema.userId = userId;
        return schema;
    }
}

// Admin, Tester, User
//{
//        "iss": "https://quarkus.io/using-jwt-rbac",
//        "jti": "a-123",
//        "sub": "jdoe-using-jwt-rbac",
//        "upn": "jdoe@quarkus.io",
//        "preferred_username": "jdoe",
//        "aud": "using-jwt-rbac",
//        "birthdate": "2001-07-13",
//        "roleMappings": {
//        "group1": "Group1MappedRole",
//        "group2": "Group2MappedRole"
//        },
//        "groups": [
//        "Admin",
//        "Tester",
//        "User"
//        ]
//        }