package com.softwareacademy.mediscreen_p9_jb_sprint_2.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.AlreadyExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.exceptions.DoesNotExistsException;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.Note;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.model.PatientHistory;
import com.softwareacademy.mediscreen_p9_jb_sprint_2.service.PatientHistoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = PatientHistoryController.class)
class PatientHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientHistoryService patientHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private static PatientHistory testPatientHistory;
    private static Note testNotes;
    private static List<Note> testListNotes;
    private static List<PatientHistory> getAllPatientsHistories = new ArrayList<>();

    @BeforeAll
    public static void setUpTestPatientHistory() {

        LocalDate creationDate = LocalDate.of(2022, 8, 15);
        String notes = "Test Notes";

        testNotes = new Note(creationDate, notes);

        testListNotes = new ArrayList<>();
        testListNotes.add(testNotes);

        testPatientHistory = new PatientHistory(0L, "Test", "TestHistory", testListNotes);
        getAllPatientsHistories.add(testPatientHistory);
    }

    @Test
    final void testGetPatientsHistoryByFirstAndLastNameShouldReturnStatusOk() throws Exception {
        when(patientHistoryService.getPatientsHistory(0L)).thenReturn(testPatientHistory);
        mockMvc.perform(get("/patientHistory").param("id", "0")).andExpect(status().isOk());
    }

    @Test
    final void testGetPatientsHistoryByIdShouldReturnStatusOk() throws Exception {
        when(patientHistoryService.getPatientsHistory(0L)).thenReturn(testPatientHistory);
        mockMvc.perform(get("/patientHistory/{id}", 0L)).andExpect(status().isOk());
    }

    @Test
    final void testGetAllPatientsHistoryShouldReturnStatusOk() throws Exception {
        when(patientHistoryService.getAllPatientsHistories()).thenReturn(getAllPatientsHistories);
        mockMvc.perform(get("/allPatientsHistory")).andExpect(status().isOk());
    }

    @Test
    final void testAddPatientHistoryShouldReturnStatusCreated() throws Exception {
        when(patientHistoryService.createPatientsHistory(Mockito.any(PatientHistory.class))).thenReturn(testPatientHistory);
        mockMvc.perform(post("/patientHistory/add").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(testPatientHistory))).andExpect(status().isCreated());
    }

    @Test
    final void testAddPatientHistoryAlreadyExistShouldReturnError400() throws Exception {
        when(patientHistoryService.createPatientsHistory(Mockito.any(PatientHistory.class))).thenThrow(AlreadyExistsException.class);
        mockMvc.perform(post("/patientHistory/add").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(testPatientHistory))).andExpect(status().isBadRequest());
    }

    @Test
    final void testUpdateOrAddNote() throws Exception {
        when(patientHistoryService.updateOrCreateNote(Mockito.anyLong(), Mockito.any(Note.class))).thenReturn(testPatientHistory);
        mockMvc.perform(put("/patientHistory").param("id", "0").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(testPatientHistory))).andExpect(status().isCreated());
    }

    @Test
    final void testUpdateOrAddNotePatientDoesNotExistShouldReturnError400() throws Exception {
        when(patientHistoryService.updateOrCreateNote(Mockito.anyLong(), Mockito.any(Note.class))).thenThrow(DoesNotExistsException.class);
        mockMvc.perform(put("/patientHistory").param("id", "0").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(testPatientHistory))).andExpect(status().isBadRequest());
    }

}
