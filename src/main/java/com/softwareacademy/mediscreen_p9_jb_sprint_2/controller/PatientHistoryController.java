package com.softwareacademy.mediscreen_p9_jb_sprint_2.controller;

import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.Notes;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.PatientHistory;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.service.PatientHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientHistoryController {

    @Autowired
    private PatientHistoryService patientHistoryService;


    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    @GetMapping("/patientHistory")
    public PatientHistory getPatientsHistoryByFirstAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName ) throws Exception {
        PatientHistory patientHistory = patientHistoryService.getPatientsHistory(firstName, lastName);
        return patientHistory;
    }

    @GetMapping("/patientHistory/{id}")
    public PatientHistory getPatientsHistoryById(@PathVariable Long id) throws Exception {
        PatientHistory patientHistory = patientHistoryService.getPatientsHistoryById(id);
        return patientHistory;
    }

    @GetMapping("/allPatientsHistory")
    public List<PatientHistory> getAllPatientsHistory() {
        List<PatientHistory> getAll = patientHistoryService.getAllPatientsHistories();
        return getAll;
    }

    @PostMapping("/patientHistory/add")
    public ResponseEntity<Object> addPatientHistory(@RequestBody PatientHistory patientHistory) {
        try {
            PatientHistory createdPatient = patientHistoryService.createPatientsHistory(patientHistory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/patientHistory")
    public ResponseEntity<Object> updateOrAddNote(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestBody Notes notes) throws Exception {
        try {
            PatientHistory createdNotes = patientHistoryService.updateOrCreateNote(firstName, lastName, notes);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdNotes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
