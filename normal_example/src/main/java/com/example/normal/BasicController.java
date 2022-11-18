package com.example.normal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BasicController {

    private final BasicService basicService;


    @GetMapping("/data")
    public ResponseEntity<String> loadData(){
        basicService.loadData();
        return ResponseEntity.ok("Load Data Completed");
    }

    @GetMapping("/normal-list")
    public ResponseEntity<List<Ad>> list(){
        return ResponseEntity.ok(basicService.findNormalList());
    }

}
