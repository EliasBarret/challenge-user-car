INSERT INTO user (first_name, last_name, email, birthday, login, password, phone)
VALUES ('John', 'Doe', 'john@example.com', '1990-01-01', 'john.doe', '$2a$10$2qW69UPV2w/3N0C9PvwNJ.tKLU4VhC/HNPUZPvvBZiOOWX4M1DVi2', '123456789');

INSERT INTO user (first_name, last_name, email, birthday, login, password, phone)
VALUES ('Jane', 'Smith', 'jane@example.com', '1985-03-15', 'jane.smith', '$2a$10$2qW69UPV2w/3N0C9PvwNJ.tKLU4VhC/HNPUZPvvBZiOOWX4M1DVi2', '987654321');

-- Inserção de carros fictícios
INSERT INTO car (year, license_plate, model, color, user_id)
VALUES (2018, 'ABC-1234', 'Toyota', 'Blue', 1);

INSERT INTO car (year, license_plate, model, color, user_id)
VALUES (2019, 'XYZ-5678', 'Honda', 'Red', 2);