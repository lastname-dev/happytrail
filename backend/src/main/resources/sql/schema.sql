-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`users` (
    `userid` VARCHAR(20) NOT NULL,
    `userpw` VARCHAR(150) NOT NULL,
    `nickname` VARCHAR(30) NOT NULL,
    `phone` VARCHAR(13) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `birthday` DATE NOT NULL,
    `intro` VARCHAR(100) NULL DEFAULT NULL,
    `salt` VARCHAR(500) NULL DEFAULT NULL,
    `token` VARCHAR(1000) NULL DEFAULT NULL,
    `deleted` TINYINT(1) NULL DEFAULT '0',
    PRIMARY KEY (`userid`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`boards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`boards` (
                                                    `boardid` INT NOT NULL AUTO_INCREMENT,
                                                    `title` VARCHAR(45) NOT NULL,
    `content` VARCHAR(1000) NOT NULL,
    `writtendate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `view` INT NOT NULL DEFAULT '0',
    `writer` VARCHAR(20) NOT NULL,
    `nickname` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`boardid`),
    INDEX `fk_boards_users1_idx` (`writer` ASC) VISIBLE,
    CONSTRAINT `fk_boards_users1`
    FOREIGN KEY (`writer`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 15
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`bcomments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`bcomments` (
                                                       `boardid` INT NOT NULL,
                                                       `writer` VARCHAR(20) NOT NULL,
    `content` VARCHAR(300) NULL DEFAULT NULL,
    `writtendate` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `commentid` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`commentid`),
    INDEX `fk_bcomments_boards1_idx` (`boardid` ASC) VISIBLE,
    INDEX `fk_bcomments_users1_idx` (`writer` ASC) VISIBLE,
    CONSTRAINT `fk_bcomments_boards1`
    FOREIGN KEY (`boardid`)
    REFERENCES `enjoytrip`.`boards` (`boardid`),
    CONSTRAINT `fk_bcomments_users1`
    FOREIGN KEY (`writer`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`plans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`plans` (
                                                   `planid` INT NOT NULL AUTO_INCREMENT,
                                                   `title` VARCHAR(100) NOT NULL,
    `writer` VARCHAR(20) NOT NULL,
    `startdate` DATE NOT NULL,
    `enddate` DATE NOT NULL,
    `writtendate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`planid`),
    INDEX `fk_plan_users1_idx` (`writer` ASC) VISIBLE,
    CONSTRAINT `fk_plan_users1`
    FOREIGN KEY (`writer`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 16
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`day` (
                                                 `dayid` INT NOT NULL AUTO_INCREMENT,
                                                 `content` VARCHAR(1000) NULL DEFAULT NULL,
    `cost` INT NULL DEFAULT NULL,
    `day` INT NULL DEFAULT NULL,
    `planid` INT NOT NULL,
    PRIMARY KEY (`dayid`),
    INDEX `fk_day_plans1_idx` (`planid` ASC) VISIBLE,
    CONSTRAINT `fk_day_plans1`
    FOREIGN KEY (`planid`)
    REFERENCES `enjoytrip`.`plans` (`planid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 31
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`festival`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`festival` (
                                                      `festivalid` INT NOT NULL AUTO_INCREMENT,
                                                      `title` VARCHAR(100) NOT NULL,
    `img` VARCHAR(100) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `place` VARCHAR(100) NOT NULL,
    `desc` VARCHAR(2000) NOT NULL,
    `startdate` DATE NOT NULL,
    `enddate` DATE NOT NULL,
    PRIMARY KEY (`festivalid`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`follows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`follows` (
    `followeeid` VARCHAR(20) NOT NULL,
    `followerid` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`followerid`, `followeeid`),
    INDEX `fk_follows_users1_idx` (`followeeid` ASC) VISIBLE,
    INDEX `fk_follows_users2_idx` (`followerid` ASC) VISIBLE,
    CONSTRAINT `fk_follows_users1`
    FOREIGN KEY (`followeeid`)
    REFERENCES `enjoytrip`.`users` (`userid`),
    CONSTRAINT `fk_follows_users2`
    FOREIGN KEY (`followerid`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`hotplaces`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`hotplaces` (
                                                       `hotplaceid` INT NOT NULL AUTO_INCREMENT,
                                                       `title` VARCHAR(100) NOT NULL,
    `likecnt` INT NOT NULL DEFAULT '0',
    `commentcnt` INT NOT NULL DEFAULT '0',
    `content` VARCHAR(1000) NOT NULL,
    `writtendate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `writer` VARCHAR(20) NOT NULL,
    `nickname` VARCHAR(30) NOT NULL,
    `placename` VARCHAR(100) NULL DEFAULT NULL,
    `placeaddress` VARCHAR(100) NULL DEFAULT NULL,
    `lat` DOUBLE NULL DEFAULT NULL,
    `lng` DOUBLE NULL DEFAULT NULL,
    PRIMARY KEY (`hotplaceid`),
    INDEX `fk_hotplaces_users1_idx` (`writer` ASC) VISIBLE,
    CONSTRAINT `fk_hotplaces_users1`
    FOREIGN KEY (`writer`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 14
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`hcomments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`hcomments` (
                                                       `commentid` INT NOT NULL AUTO_INCREMENT,
                                                       `hotplaceid` INT NOT NULL,
                                                       `writer` VARCHAR(20) NOT NULL,
    `content` VARCHAR(300) NOT NULL,
    `writtendate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`commentid`),
    INDEX `fk_comments_hotplaces1_idx` (`hotplaceid` ASC) VISIBLE,
    INDEX `fk_comments_users1_idx` (`writer` ASC) VISIBLE,
    CONSTRAINT `fk_comments_hotplaces1`
    FOREIGN KEY (`hotplaceid`)
    REFERENCES `enjoytrip`.`hotplaces` (`hotplaceid`),
    CONSTRAINT `fk_comments_users1`
    FOREIGN KEY (`writer`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 9
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`images` (
                                                    `imageid` INT NOT NULL AUTO_INCREMENT,
                                                    `hotplaceid` INT NOT NULL,
                                                    `url` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`imageid`),
    INDEX `fk_images_hotplaces1_idx` (`hotplaceid` ASC) VISIBLE,
    CONSTRAINT `fk_images_hotplaces1`
    FOREIGN KEY (`hotplaceid`)
    REFERENCES `enjoytrip`.`hotplaces` (`hotplaceid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 38
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`likepost`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`likepost` (
    `userid` VARCHAR(20) NOT NULL,
    `hotplaceid` INT NOT NULL,
    PRIMARY KEY (`userid`, `hotplaceid`),
    INDEX `fk_like_hotplaces1_idx` (`hotplaceid` ASC) VISIBLE,
    CONSTRAINT `fk_like_hotplaces1`
    FOREIGN KEY (`hotplaceid`)
    REFERENCES `enjoytrip`.`hotplaces` (`hotplaceid`),
    CONSTRAINT `fk_like_users1`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`places`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`places` (
                                                    `placeid` INT NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(100) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `lat` DOUBLE NOT NULL,
    `lng` DOUBLE NOT NULL,
    `dayid` INT NOT NULL,
    PRIMARY KEY (`placeid`),
    INDEX `fk_places_day1_idx` (`dayid` ASC) VISIBLE,
    CONSTRAINT `fk_places_day1`
    FOREIGN KEY (`dayid`)
    REFERENCES `enjoytrip`.`day` (`dayid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 31
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


-- -----------------------------------------------------
-- Table `enjoytrip`.`share`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`share` (
                                                   `shareid` INT NOT NULL AUTO_INCREMENT,
                                                   `userid` VARCHAR(20) NOT NULL,
    `planid` INT NOT NULL,
    PRIMARY KEY (`shareid`, `userid`),
    INDEX `fk_share_users1_idx` (`userid` ASC) VISIBLE,
    INDEX `fk_share_plans1_idx` (`planid` ASC) VISIBLE,
    CONSTRAINT `fk_share_plans1`
    FOREIGN KEY (`planid`)
    REFERENCES `enjoytrip`.`plans` (`planid`),
    CONSTRAINT `fk_share_users1`
    FOREIGN KEY (`userid`)
    REFERENCES `enjoytrip`.`users` (`userid`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 11
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_as_cs;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
