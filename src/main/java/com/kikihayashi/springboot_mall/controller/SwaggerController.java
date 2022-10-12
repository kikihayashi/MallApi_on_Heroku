package com.kikihayashi.springboot_mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "controller名稱客製")
@RequestMapping(path = "/root客製")
public class SwaggerController {

    @ApiOperation("method名稱客製")
    @ApiResponses({
            @ApiResponse(code = 401, message = "沒有權限"),
            @ApiResponse(code = 403, message = "禁用"),
            @ApiResponse(code = 404, message = "找不到路徑")
    })
    @GetMapping("/swagger")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("測試成功");
    }
}
