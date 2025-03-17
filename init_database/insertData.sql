/* NOTE: Dummy data generated use ChatGPT */

/* Insert data into the StructuralEngineers table */
INSERT INTO Engineers (Name, TelephoneNumber, EmailAddress, PhysicalAddress) 
VALUES 
    ('John Smith', '123-456-7890', 'john.smith@example.com', '123 Main St, City, Country'),
    ('Alice Johnson', '987-654-3210', 'alice.johnson@example.com', '456 Oak St, City, Country'),
    ('Robert Brown', '555-123-4567', 'robert.brown@example.com', '789 Pine St, City, Country'),
    ('Emily Davis', '333-555-7890', 'emily.davis@example.com', '321 Maple St, City, Country'),
    ('David Wilson', '444-333-2222', 'david.wilson@example.com', '654 Birch St, City, Country');

/* Insert data into the ProjectManagers table */
INSERT INTO Managers (Name, TelephoneNumber, EmailAddress, PhysicalAddress) 
VALUES 
    ('Samantha Green', '112-233-4455', 'samantha.green@example.com', '123 Elm St, City, Country'),
    ('Mark Taylor', '223-344-5566', 'mark.taylor@example.com', '654 Cedar St, City, Country'),
    ('Olivia Harris', '334-455-6677', 'olivia.harris@example.com', '987 Fir St, City, Country'),
    ('Chris Martin', '445-566-7788', 'chris.martin@example.com', '321 Pine St, City, Country'),
    ('Nancy Lewis', '556-677-8899', 'nancy.lewis@example.com', '456 Cedar Ave, City, Country');

/* Insert data into the Architects table */
INSERT INTO Architects (Name, TelephoneNumber, EmailAddress, PhysicalAddress) 
VALUES 
    ('Michael Scott', '123-789-4560', 'michael.scott@example.com', '123 River Rd, City, Country'),
    ('Sarah Adams', '234-890-5671', 'sarah.adams@example.com', '456 Lake St, City, Country'),
    ('Brian Clark', '345-901-6782', 'brian.clark@example.com', '789 Hill St, City, Country'),
    ('Linda Harris', '456-012-7893', 'linda.harris@example.com', '321 Ridge Ave, City, Country'),
    ('Thomas Young', '567-123-8904', 'thomas.young@example.com', '654 Mountain Rd, City, Country');

/* Insert data into the Contractors table */
INSERT INTO Contractors (Name, TelephoneNumber, EmailAddress, PhysicalAddress) 
VALUES 
    ('Paul Walker', '111-223-4455', 'paul.walker@example.com', '123 Beach Rd, City, Country'),
    ('George Lewis', '222-334-5566', 'george.lewis@example.com', '456 Desert Rd, City, Country'),
    ('Steve Brown', '333-445-6677', 'steve.brown@example.com', '789 Ocean Blvd, City, Country'),
    ('Daniel White', '444-556-7788', 'daniel.white@example.com', '321 Coastline Ave, City, Country'),
    ('Emma Green', '555-667-8899', 'emma.green@example.com', '654 Island Dr, City, Country');

/* Insert data into the Customers table */
INSERT INTO Customers (Name, TelephoneNumber, EmailAddress, PhysicalAddress) 
VALUES 
    ('Grace King', '777-888-9990', 'grace.king@example.com', '123 Harbor St, City, Country'),
    ('Daniel Moore', '888-999-0001', 'daniel.moore@example.com', '456 Oak Rd, City, Country'),
    ('Sophia Lee', '999-000-1112', 'sophia.lee@example.com', '789 Mountain Ave, City, Country'),
    ('Jacob Walker', '101-212-3234', 'jacob.walker@example.com', '321 Valley Blvd, City, Country'),
    ('Mia Johnson', '212-323-4345', 'mia.johnson@example.com', '654 Hillside Rd, City, Country');

/* Insert data into the Projects table */
INSERT INTO Projects (ProjectName, BuildingType, PhysicalAddress, ERFNumber, TotalFee, AmountPaidToDate, StartDate, Deadline, Finalised, CompletionDate, EngineerID, ManagerID, ArchitectID, ContractorID, CustomerID) 
VALUES 
    ('Skyline Tower', 'Apartment Block', '123 Downtown St', 'ERF001', 500000.00, 200000.00, '2024-03-15', '2025-12-31', FALSE, NULL, 1, 1, 1, 1, 1),
    ('Ocean Breeze', 'House', '456 Coastal Rd', 'ERF002', 300000.00, 150000.00, '2023-08-20', '2025-08-15', FALSE, NULL, 2, 2, 2, 2, 2),
    ('Mountain View', 'Store', '789 Summit Ave', 'ERF003', 400000.00, 100000.00, '2024-06-10', '2025-10-30', FALSE, NULL, 3, 3, 3, 3, 3),
    ('Lakeside Plaza', 'Office Building', '321 Lakeshore Dr', 'ERF004', 600000.00, 250000.00, '2023-11-05', '2026-01-01', FALSE, NULL, 4, 4, 4, 4, 4),
    ('Sunset Ridge', 'Apartment Block', '654 Ridgeway St', 'ERF005', 700000.00, 350000.00, '2024-01-10', '2025-11-15', FALSE, NULL, 5, 5, 5, 5, 5);
