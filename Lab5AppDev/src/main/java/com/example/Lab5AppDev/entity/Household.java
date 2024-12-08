package com.example.Lab5AppDev.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Household {

    @Id
    @Column(length = 8)
    private String eircode;

    @Column(name = "number_of_occupants", nullable = false)
    @Min(value = 0, message = "Number of occupants must be at least 0")
    private int numberOfOccupants;

    @Column(name = "max_number_of_occupants", nullable = false)
    @Min(value = 1, message = "Max number of occupants must be at least 1")
    private int maxNumberOfOccupants;

    @Column(name = "owner_occupied", nullable = false)
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pet> pets;

    @PrePersist
    @PreUpdate
    private void validateOccupants() {
        if (numberOfOccupants > maxNumberOfOccupants) {
            throw new IllegalArgumentException("Number of occupants cannot exceed the maximum allowed.");
        }
    }
}
