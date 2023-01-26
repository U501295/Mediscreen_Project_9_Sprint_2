package com.softwareacademy.mediscreen_p9_jb_sprint_2.service;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.AlreadyExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.DoesNotExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.Note;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.PatientHistory;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.repository.PatientHistoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@WebMvcTest(PatientHistoryService.class)
class PatientHistoryServiceTest {

    private static PatientHistory testPatientsHistory;
    private static Note testNotes;
    private static List<Note> testListNotes;
    private static Validator validator;

    @MockBean
    private PatientHistoryRepository patientsHistoryRepository;

    @Autowired PatientHistoryService patientsHistoryService;

    @BeforeAll
    public static void setUpTestPatientHistory() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        LocalDate creationDate = LocalDate.of(2022, 8, 15);
        String notes = "Test Notes";

        testNotes = new Note(creationDate, notes);

        testListNotes = new ArrayList<>();
        testListNotes.add(testNotes);

        testPatientsHistory = new PatientHistory(0L, "Test", "TestHistory", testListNotes);
    }

    @Test
    final void createDuplicatedPatientTest_shouldThrowException() throws AlreadyExistsException {
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        assertThrows(AlreadyExistsException.class, () -> patientsHistoryService.createPatientsHistory(testPatientsHistory));

    }

    @Test
    final void testGetPatientsHistory() throws DoesNotExistsException {
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        PatientHistory foundPatientsHistory = patientsHistoryService.getPatientsHistory(0L);
        assertThat(foundPatientsHistory).isEqualTo(testPatientsHistory);
    }

    @Test
    final void testGetPatientsHistoryById() throws Exception {
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        PatientHistory foundPatientsHistory = patientsHistoryService.getPatientsHistoryById(0L);
        assertThat(foundPatientsHistory).isEqualTo(testPatientsHistory);
    }

    @Test
    final void getPatientHistoryUnknown_shouldThrowException() throws DoesNotExistsException {
        assertThrows(DoesNotExistsException.class, () -> patientsHistoryService.getPatientsHistory(0L));
    }

    @Test
    final void getPatientHistoryUnknownId_shouldThrowException() throws DoesNotExistsException {
        assertThrows(DoesNotExistsException.class, () -> patientsHistoryService.getPatientsHistoryById(5L));
    }

    @Test
    final void testGetAllPatientsHistories() {
        List<PatientHistory> findAll = new ArrayList<>();
        findAll.add(testPatientsHistory);
        when(patientsHistoryRepository.findAll()).thenReturn(findAll);
        List<PatientHistory> foundAll = patientsHistoryService.getAllPatientsHistories();
        assertThat(foundAll).isEqualTo(findAll);
    }

    @Test
    final void testGetNotesByCreationDate() throws Exception {
        LocalDate creationDate = LocalDate.of(2022, 8, 15);
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        Note foundNotes = patientsHistoryService.getNotesByCreationDate(0L, creationDate);
        assertThat(foundNotes).isEqualTo(testNotes);
    }

    @Test
    final void testGetNotesByCreationDateDoesNotExist() throws Exception {
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        Note foundNotes = patientsHistoryService.getNotesByCreationDate(0L,  LocalDate.now());
        assertThat(foundNotes).isNull();
    }

    @Test
    final void testCreateNote() throws Exception {
        LocalDate creationDate = LocalDate.of(2022, 8, 25);
        String addedText = "Test Added Notes";
        Note addedNotes = new Note(creationDate, addedText);
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        when(patientsHistoryRepository.save(testPatientsHistory)).thenReturn(testPatientsHistory);
        patientsHistoryService.updateOrCreateNote(0L, addedNotes);
        assertThat(testPatientsHistory.getNotes().get(1).getContent()).isEqualTo(addedText);

    }

    @Test
    final void testUpdateNote() throws Exception {
        LocalDate creationDate = LocalDate.of(2022, 8, 15);
        String updatedText =  "Test Notes";
        Note updatedNotes = new Note(creationDate, updatedText);
        when(patientsHistoryRepository.findByPatientId(0L)).thenReturn(Optional.of(testPatientsHistory));
        when(patientsHistoryRepository.save(testPatientsHistory)).thenReturn(testPatientsHistory);
        patientsHistoryService.updateOrCreateNote(0L, updatedNotes);
        String newLine = System.getProperty("line.separator");
        assertThat(testPatientsHistory.getNotes().get(0).getContent()).isEqualTo(updatedText+ newLine + "-->" +updatedText);

    }

    /*
    @Test
    final void createPatientHistoryWithFieldsErrorTest_shouldThrowException() {

        Note noDate = new Note(null, "Test");
        Note noText = new Note(LocalDate.of(2022, 5, 5), null);

        List<Note> noDateList = new ArrayList<>();
        List<Note> noTextList = new ArrayList<>();

        noDateList.add(noDate);
        noTextList.add(noText);

        PatientHistory patientsHistory1 = new PatientHistory(0L, "", "Michel", null);
        Set<ConstraintViolation<PatientHistory>> violations1 = validator.validate(patientsHistory1);
        assertFalse(violations1.isEmpty());

        PatientHistory patientsHistory2 = new PatientHistory(0L, "Jean", "", null);
        Set<ConstraintViolation<PatientHistory>> violations2 = validator.validate(patientsHistory2);
        assertFalse(violations2.isEmpty());

        PatientHistory patientsHistory3 = new PatientHistory(0L, null, "Michel", null);
        Set<ConstraintViolation<PatientHistory>> violations3 = validator.validate(patientsHistory3);
        assertFalse(violations3.isEmpty());

        PatientHistory patientsHistory4 = new PatientHistory(0L, "Jean", null, null );
        Set<ConstraintViolation<PatientHistory>> violations4 = validator.validate(patientsHistory4);
        assertFalse(violations4.isEmpty());

        PatientHistory patientsHistory5 = new PatientHistory(0L, "", "Michel", noDateList);
        Set<ConstraintViolation<PatientHistory>> violations5 = validator.validate(patientsHistory5);
        assertFalse(violations5.isEmpty());

        PatientHistory patientsHistory6 = new PatientHistory(0L, "", "Michel", noTextList);
        Set<ConstraintViolation<PatientHistory>> violations6 = validator.validate(patientsHistory6);
        assertFalse(violations6.isEmpty());

    }*/

}
