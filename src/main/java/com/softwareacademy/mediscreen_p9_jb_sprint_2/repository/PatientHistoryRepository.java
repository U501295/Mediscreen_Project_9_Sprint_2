package com.softwareacademy.mediscreen_p9_jb_sprint_2.repository;

import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, Long> {


    Optional<PatientHistory> findByPatientId(Long patientId);
}
