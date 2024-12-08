package com.example.Lab5AppDev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "pets") // Ensure this matches the table name in the database
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 20, message = "Name must not exceed 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can only contain letters and spaces")
    private String name;

    @Column(name = "animal_type", nullable = false)
    @NotBlank(message = "Animal type cannot be blank")
    @Size(max = 20, message = "Animal Type must not exceed 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Animal Type can only contain letters and spaces")
    private String animalType;

    @Column(nullable = false)
    @NotBlank(message = "Breed cannot be blank")
    @Size(max = 20, message = "Breed must not exceed 20 characters")
    @Pattern(regexp = "^[A-Za-z \\-]+$", message = "Breed can only contain letters, spaces, and hyphens")
    private String breed;

    @Column(nullable = false)
    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "household_eircode", nullable = false)
    @NotNull(message = "Household cannot be null")
    private Household household; // The household this pet belongs to
}