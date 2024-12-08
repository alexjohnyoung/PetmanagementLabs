-- Create the household table
CREATE TABLE household (
    eircode VARCHAR(8) NOT NULL PRIMARY KEY,
    number_of_occupants INT NOT NULL CHECK (number_of_occupants > 0),
    max_number_of_occupants INT NOT NULL CHECK (max_number_of_occupants > 0),
    owner_occupied BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT chk_occupants CHECK (number_of_occupants <= max_number_of_occupants)
);

-- Create the pet table
CREATE TABLE pet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    breed VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    age INT NOT NULL CHECK (age >= 0),
    household_eircode VARCHAR(8),
    CONSTRAINT fk_household FOREIGN KEY (household_eircode)
     REFERENCES household (eircode)
     ON DELETE CASCADE
);