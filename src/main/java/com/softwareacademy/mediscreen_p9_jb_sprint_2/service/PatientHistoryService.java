package com.softwareacademy.mediscreen_p9_jb_sprint_2.service;

import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.AlreadyExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.DoesNotExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.Notes;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.PatientHistory;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.repository.PatientHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientHistoryService {

    @Autowired
    PatientHistoryRepository patientsHistoryRepository;

    public PatientHistory createPatientsHistory(PatientHistory patientsHistory) throws AlreadyExistsException {
        if (patientsHistoryRepository.findByFirstNameAndLastName(patientsHistory.getFirstName(), patientsHistory.getLastName()).isPresent()) {
            throw new AlreadyExistsException(patientsHistory.getFirstName() + " " + patientsHistory.getLastName() + "already Exists");
        }
        return patientsHistoryRepository.insert(patientsHistory);
    }

    public PatientHistory getPatientsHistory(String firstName, String lastName) throws DoesNotExistsException {
        Optional<PatientHistory> patientHistory = patientsHistoryRepository.findByFirstNameAndLastName(firstName, lastName);
        if (! patientHistory.isPresent()) {
            throw new DoesNotExistsException(firstName + " " + lastName + " does not exists");
        }
        return patientHistory.get();
    }

    public PatientHistory getPatientsHistoryById(Long id) throws DoesNotExistsException {
        Optional<PatientHistory> patientHistory = patientsHistoryRepository.findByPatientId(id);
        if (! patientHistory.isPresent()) {
            throw new DoesNotExistsException("Patient with id of " + id + "does not exists");
        }
        return patientHistory.get();
    }

    public List<PatientHistory> getAllPatientsHistories() {
        List<PatientHistory> getAll = patientsHistoryRepository.findAll();
        return getAll;
    }

    public Notes getNotesByCreationDate(String firstName, String lastName, LocalDate creationDate) throws Exception {
        Optional<PatientHistory> patientsHistory = patientsHistoryRepository.findByFirstNameAndLastName(firstName, lastName);
        for (Notes notes : patientsHistory.get().getNotes()) {
            if (notes.getCreationDate().equals(creationDate)) {
                return notes;
            }
        }
        return null;
    }

    public PatientHistory updateOrCreateNote(String firstName, String lastName, Notes noteUpdated) throws Exception {
        Optional<PatientHistory> patientsHistory = patientsHistoryRepository.findByFirstNameAndLastName(firstName, lastName);
        for (Notes notes : patientsHistory.get().getNotes()) {
            if (notes.getCreationDate().equals(noteUpdated.getCreationDate())) {
                notes.setNote(noteUpdated.getNote());
                return patientsHistoryRepository.save(patientsHistory.get());
            }
        }
        patientsHistory.get().getNotes().add(noteUpdated);
        return patientsHistoryRepository.save(patientsHistory.get());
    }

}
