package com.placementserver.placementserver.controllers;

import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.responses.RoleAndName;
import com.placementserver.placementserver.services.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
public class GeneralController {

    @Autowired
    private JWTService jwtService;

    @GetMapping("/token")
    public ResponseEntity<DataResponse<RoleAndName>> validateToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if(jwtService.validateAndExtractRole(token)) {
            DataResponse<RoleAndName> response = new DataResponse<RoleAndName>("Failed","Your Token is expired",new RoleAndName());
            return new ResponseEntity<>(response,HttpStatusCode.valueOf(401));
        }
        Claims claims = jwtService.extractClaims(token);
        String role = claims.get("role",String.class);
        String userName = claims.get("user_name", String.class);
        Long userNameLong = Long.parseLong(userName);
        DataResponse<RoleAndName> response = new DataResponse<RoleAndName>("Success","Your Token is valid",new RoleAndName(role,userNameLong));
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(200));
    }
}
