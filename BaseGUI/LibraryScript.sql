-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Library` DEFAULT CHARACTER SET utf8 ;
USE `Library` ;

-- -----------------------------------------------------
-- Table `Library`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`users` (
  `LibraryCardNumber` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(20) NULL,
  `LastName` VARCHAR(30) NULL,
  `City` VARCHAR(30) NULL,
  `Address` VARCHAR(30) NULL,
  `PostalCode` VARCHAR(10) NULL,
  `Telephone` VARCHAR(20) NULL,
  `Password` VARCHAR(50) NULL,
  `Email` VARCHAR(40) NULL,
  `RegistrationDate` DATETIME NULL DEFAULT current_timestamp,
  `Banned` VARCHAR(3) NULL DEFAULT 'No',
  PRIMARY KEY (`LibraryCardNumber`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`books` (
  `BookID` INT NOT NULL AUTO_INCREMENT,
  `Title` VARCHAR(100) NULL,
  `Author` VARCHAR(50) NULL,
  `ISBN` VARCHAR(50) NULL,
  `Available` VARCHAR(3) NULL DEFAULT 'Yes',
  PRIMARY KEY (`BookID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`reservations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`reservations` (
  `ReservationID` INT NOT NULL AUTO_INCREMENT,
  `LibraryCardNumber` INT NOT NULL,
  `BookID` INT NOT NULL,
  `ReservationDate` TIMESTAMP NULL DEFAULT  CURRENT_TIMESTAMP,
  `ExpirationDate` TIMESTAMP NULL DEFAULT  CURRENT_TIMESTAMP,
  `Completed` VARCHAR(10) NULL,
  PRIMARY KEY (`ReservationID`),
  INDEX `fk_reservations_users1_idx` (`LibraryCardNumber` ASC),
  INDEX `fk_reservations_books1_idx` (`BookID` ASC),
  CONSTRAINT `fk_reservations_users1`
    FOREIGN KEY (`LibraryCardNumber`)
    REFERENCES `Library`.`users` (`LibraryCardNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservations_books1`
    FOREIGN KEY (`BookID`)
    REFERENCES `Library`.`books` (`BookID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`borrows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`borrows` (
  `BorrowID` INT NOT NULL AUTO_INCREMENT,
  `LibraryCardNumber` INT NOT NULL,
  `BookID` INT NOT NULL,
  `BorrowDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `ExpirationDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `ReturnDate` TIMESTAMP NULL,
  PRIMARY KEY (`BorrowID`),
  INDEX `fk_borrows_users_idx` (`LibraryCardNumber` ASC),
  INDEX `fk_borrows_books1_idx` (`BookID` ASC),
  CONSTRAINT `fk_borrows_users`
    FOREIGN KEY (`LibraryCardNumber`)
    REFERENCES `Library`.`users` (`LibraryCardNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_borrows_books1`
    FOREIGN KEY (`BookID`)
    REFERENCES `Library`.`books` (`BookID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`banned_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`banned_users` (
  `LibraryCardNumber` INT NOT NULL,
  `BanDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `ExpirationDate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `Reason` VARCHAR(500) NULL,
  PRIMARY KEY (`LibraryCardNumber`),
  INDEX `fk_banned_users_users1_idx` (`LibraryCardNumber` ASC),
  CONSTRAINT `fk_banned_users_users1`
    FOREIGN KEY (`LibraryCardNumber`)
    REFERENCES `Library`.`users` (`LibraryCardNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`dates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Library`.`dates` (
  `Date` DATE NOT NULL,
  PRIMARY KEY (`Date`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
