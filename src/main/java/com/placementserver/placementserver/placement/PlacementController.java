package com.placementserver.placementserver.placement;


import com.placementserver.placementserver.responses.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/placement")
public class PlacementController {

    @Autowired
    private PlacementService placementService;

    @GetMapping("/getplacedstudents")
    public ResponseEntity<DataResponse<List<Placement>>> getPlacedStudents(@RequestParam("year") short year) {

        DataResponse<List<Placement>> response = placementService.getPlacedStudents(year);

        return new ResponseEntity<DataResponse<List<Placement>>>(response, HttpStatus.valueOf(200));
    }

    @PostMapping("/addplacedstudent")
    public ResponseEntity<DataResponse<String>> addPlacedStudent(@RequestBody Placement placement) {

        DataResponse<String> response = placementService.addPlacedStudent(placement);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(404));
        }
    }

    @PutMapping("/updateplacedstudent")
    public ResponseEntity<DataResponse<String>> updatePlacedStudent(@RequestBody Placement placement) {

        DataResponse<String> response = placementService.updatePlacedStudent(placement);

        if(response.getCondition().equals("Success")) {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<DataResponse<String>>(response, HttpStatus.valueOf(404));
        }
    }
}
