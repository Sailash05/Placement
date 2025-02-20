package com.placementserver.placementserver.placement;

import com.placementserver.placementserver.repositories.StudentRepository;
import com.placementserver.placementserver.responses.DataResponse;
import com.placementserver.placementserver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementService {

    @Autowired
    PlacementRepository placementRepository;

    @Autowired
    StudentRepository studentRepository;

    public DataResponse<List<Placement>> getPlacedStudents(short year) {

        List<Placement> data = placementRepository.getPlacedStudents(year);

        return new DataResponse<List<Placement>>("Success", "Placed Students List", data);
    }

    public DataResponse<String> addPlacedStudent(Placement placement) {

        if(!studentRepository.existsById(placement.getRollno())) {
            return new DataResponse<String>("Failed","Student Not Found", "");
        }
        if(placementRepository.existsById(placement.getRollno())) {
            return new DataResponse<String>("Failed","Student Placement Details Already Added", "");
        }

        placementRepository.save(placement);
        return new DataResponse<String>("Success","Student Placement Details Added","");
    }

    public DataResponse<String> updatePlacedStudent(Placement placement) {

        if(!studentRepository.existsById(placement.getRollno())) {
            return new DataResponse<String>("Failed","Student Not Found", "");
        }

        int updateStatus = placementRepository.updatePlacedStudent(placement.getRollno(),
                placement.getName(),
                placement.getDepartment(),
                placement.getPassedOutYear(),
                placement.getCompanyName(),
                placement.getLpa());

        if(updateStatus >= 1) {
            return new DataResponse<String>("Success", "Student Placement Details Updated", "");
        }
        return new DataResponse<String>("Failed", "Wrong Update","");
    }
}
