package com.softwareacademy.mediscreen_p9_jb_sprint_2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Document(collection = "patientHistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistory {

    @Id
    private Long patientId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private List<Note> notes;


}
