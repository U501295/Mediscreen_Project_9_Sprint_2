package com.softwareacademy.mediscreen_p9_jb_sprint_2.service;

import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.AlreadyExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.DoesNotExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.Note;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.PatientHistory;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.repository.PatientHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientHistoryService {

    @Autowired
    PatientHistoryRepository patientsHistoryRepository;

    public PatientHistory createPatientsHistory(PatientHistory patientHistory) throws AlreadyExistsException {
        if (patientsHistoryRepository.findByFirstNameAndLastName(patientHistory.getFirstName(), patientHistory.getLastName()).isPresent()) {
            throw new AlreadyExistsException(patientHistory.getFirstName() + " " + patientHistory.getLastName() + " " + "already Exists");
        }
        Note noteToBeAdded = new Note(patientHistory.getNotes().get(0).getContent());
        List<Note> listOfNotesToBeAdded = new ArrayList<>();
        listOfNotesToBeAdded.add(noteToBeAdded);
        PatientHistory historyToBeAdded = new PatientHistory(patientHistory.getPatientId(), patientHistory.getFirstName(), patientHistory.getLastName(), listOfNotesToBeAdded);
        return patientsHistoryRepository.insert(historyToBeAdded);
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

    public Note getNotesByCreationDate(String firstName, String lastName, LocalDate creationDate) throws Exception {
        Optional<PatientHistory> patientsHistory = patientsHistoryRepository.findByFirstNameAndLastName(firstName, lastName);
        for (Note note : patientsHistory.get().getNotes()) {
            if (note.getCreationDate().equals(creationDate)) {
                return note;
            }
        }
        return null;
    }

    public PatientHistory updateOrCreateNote(String firstName, String lastName, Note noteUpdated) throws Exception {
        Optional<PatientHistory> patientsHistory = patientsHistoryRepository.findByFirstNameAndLastName(firstName, lastName);
        for (Note note : patientsHistory.get().getNotes()) {
            if (note.getCreationDate().equals(noteUpdated.getCreationDate())) {
                String newLine = System.getProperty("line.separator");
                note.setContent(note.getContent() + newLine + noteUpdated.getContent());
                return patientsHistoryRepository.save(patientsHistory.get());
            }
        }
        patientsHistory.get().getNotes().add(noteUpdated);
        return patientsHistoryRepository.save(patientsHistory.get());
    }

}
