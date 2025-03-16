/* Create the Engineers table */
CREATE TABLE Engineers (
    EngineerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    TelephoneNumber VARCHAR(15),
    EmailAddress VARCHAR(100),
    PhysicalAddress VARCHAR(255)
);

/* Create the Managers table */
CREATE TABLE Managers (
    ManagerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    TelephoneNumber VARCHAR(15),
    EmailAddress VARCHAR(100),
    PhysicalAddress VARCHAR(255)
);

/* Create the Architects table */
CREATE TABLE Architects (
    ArchitectID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    TelephoneNumber VARCHAR(15),
    EmailAddress VARCHAR(100),
    PhysicalAddress VARCHAR(255)
);

/* Create the Contractors table */
CREATE TABLE Contractors (
    ContractorID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    TelephoneNumber VARCHAR(15),
    EmailAddress VARCHAR(100),
    PhysicalAddress VARCHAR(255)
);

/* Create the Customers table */
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100),
    TelephoneNumber VARCHAR(15),
    EmailAddress VARCHAR(100),
    PhysicalAddress VARCHAR(255)
);

/* Create the Projects table */
CREATE TABLE Projects (
    ProjectNumber     INT AUTO_INCREMENT PRIMARY KEY,
    ProjectName       VARCHAR(100) NOT NULL,
    BuildingType      VARCHAR(50),
    PhysicalAddress   VARCHAR(255),
    ERFNumber         VARCHAR(50),
    TotalFee          DECIMAL(10,2) DEFAULT 0.00 NOT NULL,
    AmountPaidToDate  DECIMAL(10,2) DEFAULT 0.00 NOT NULL,
    StartDate         DATE NOT NULL DEFAULT (CURRENT_DATE),
    Deadline          DATE,
    Finalised         BOOLEAN DEFAULT FALSE,
    CompletionDate    DATE,
    EngineerID        INT,
    ManagerID         INT,
    ArchitectID       INT,
    ContractorID      INT,
    CustomerID        INT NOT NULL,
    
    /* Define foreign key constraints */
    CONSTRAINT fk_Projects_Engineer FOREIGN KEY (EngineerID) REFERENCES Engineers(EngineerID) ON DELETE SET NULL,
    CONSTRAINT fk_Projects_Manager FOREIGN KEY (ManagerID) REFERENCES Managers(ManagerID) ON DELETE SET NULL,
    CONSTRAINT fk_Projects_Architect FOREIGN KEY (ArchitectID) REFERENCES Architects(ArchitectID) ON DELETE SET NULL,
    CONSTRAINT fk_Projects_Contractor FOREIGN KEY (ContractorID) REFERENCES Contractors(ContractorID) ON DELETE SET NULL,
    CONSTRAINT fk_Projects_Customer FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);
