CREATE TABLE `users` (
  `UserID` int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
  `Email` varchar(255) UNIQUE NOT NULL,
  `IC` varchar(20) UNIQUE DEFAULT NULL,
  `Password` varchar(50) NOT NULL,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `PhoneNumber` varchar(20) DEFAULT NULL,
  `Occupation` varchar(50) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Category` varchar(50) DEFAULT NULL,
  `AddressProof` LONGBLOB DEFAULT NULL,
  `Region` varchar(255) DEFAULT NULL,
  `Role` varchar(20) DEFAULT 'USER',
  `Status` varchar(20) DEFAULT 'DISAPPROVED'
);

CREATE TABLE `waterConsumption` (
  `WaterID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `WaterProportionalFactor` decimal(10,2) DEFAULT 0,
  `WaterUsageValueRM` decimal(10,2) DEFAULT 0,
  `WaterUsageValueM3` decimal(10,2) DEFAULT 0,
  `WaterConsumptionProof` LONGBLOB DEFAULT NULL,
  `Status` varchar(20) DEFAULT 'PENDING'
);

CREATE TABLE `electricityConsumption` (
  `ElectricityID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ElectricityProportionalFactor` decimal(10,2) DEFAULT 0,
  `ElectricUsageValueRM` decimal(10,2) DEFAULT 0,
  `ElectricUsageValueM3` decimal(10,2) DEFAULT 0,
  `ElectricConsumptionProof` LONGBLOB DEFAULT NULL,
  `Status` varchar(20) DEFAULT 'PENDING'
);

CREATE TABLE `recycle` (
  `RecycleID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `RecycleKG` decimal(10,2) DEFAULT 0,
  `RecycleRM` decimal(10,2) DEFAULT 0,
  `RecycleProof` LONGBLOB DEFAULT NULL,
  `Status` varchar(20) DEFAULT 'PENDING'
);

CREATE TABLE `application` (
  `ApplicationID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `UserID` int(11) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `WaterID` int(11) DEFAULT NULL,
  `ElectricityID` int(11) DEFAULT NULL,
  `RecycleID` int(11) DEFAULT NULL,
  FOREIGN KEY (UserID) REFERENCES Users(UserID)
);