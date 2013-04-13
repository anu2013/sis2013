CREATE TABLE Users(
	UserId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	UserLogInName VARCHAR(100),
	RoleId INTEGER,
	Password VARCHAR(100),
	CreatedDate TIMESTAMP,
	EndDate TIMESTAMP,
	Active SMALLINT,
	CreatedBy INTEGER,
	LastUpdatedBy INTEGER,
	LastUpdatedDate TIMESTAMP,
	PRIMARY KEY (UserId)
); 

CREATE TABLE Role(
	RoleId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	RoleName VARCHAR(100),
	PRIMARY KEY (RoleId)
);

CREATE TABLE UserProfile(
	UserId INTEGER NOT NULL,
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	MiddleName VARCHAR(255),
	DateOfBirth TIMESTAMP,
	Gender VARCHAR(15),
	CurrentAddress1 VARCHAR(255),
	CurrentAddress2 VARCHAR(255),
	CurrentCity VARCHAR(50),
	CurrentZip VARCHAR(20),
	CurrentState VARCHAR(50),
	CurrentCountry VARCHAR(50),
	MailingAddress1 VARCHAR(255),
	MailingAddress2 VARCHAR(255),
	MailingCity VARCHAR(50),
	MailingZip VARCHAR(20),
	MailingState VARCHAR(50),
	MailingCountry VARCHAR(50),
	Email1 VARCHAR(120),
	Email2 VARCHAR(120),
	HomePhone VARCHAR(15),
	MobilePhone VARCHAR(15),
	WorkPhone VARCHAR(15),
	PRIMARY KEY (UserId)
);

CREATE TABLE Admission(
	AdmissionId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Description VARCHAR(255),
	CreatedDate TIMESTAMP,
	EndDate TIMESTAMP,
	Status VARCHAR(20),
	ApplicationType VARCHAR(20),
	GradeLevelApplyingFor VARCHAR(20),
	TrackingNumber VARCHAR(25),
	AdmissionSeekingYear VARCHAR(20),
	CreatedBy INTEGER,
	LastUpdateBy INTEGER,
	LastUpdatedDate TIMESTAMP,
	PRIMARY KEY (AdmissionId)
);

CREATE TABLE AdmissionStep(
	AdmissionStepId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	AdmissionId INTEGER NOT NULL,
	AdmissionStepName VARCHAR(255),
	CreatedDate TIMESTAMP,
	EndDate TIMESTAMP,
	CreatedBy INTEGER,
	LastUpdatedBy INTEGER,
	LastUpdatedDate TIMESTAMP,
	PRIMARY KEY (AdmissionStepId)
);

CREATE TABLE AdmissionStepComment(
	AdmissionStepCommentId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	AdmissionId INTEGER NOT NULL,
	AdmissionStepId INTEGER NOT NULL,
	Comments VARCHAR(255),
	EnteredDate TIMESTAMP,
	EnteredBy INTEGER,
	PRIMARY KEY (AdmissionStepCommentId)
);

CREATE TABLE Parent(
	ParentId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER,
	ParentFirstName VARCHAR(255),
	ParentLastName VARCHAR(255),
	ParentEducation VARCHAR(255),
	ParentContactAddress1 VARCHAR(255),
	ParentContactAddress2 VARCHAR(255),
	ParentContactCity VARCHAR(255),
	ParentContactState VARCHAR(255),
	ParentContactZip VARCHAR(255),
	ParentContactCountry VARCHAR(255),
	RelationshipWithStudent VARCHAR(255),
	PRIMARY KEY (ParentId)
);

CREATE TABLE Admin(
	AdminId INTEGER,
	Designation VARCHAR(255),
	PRIMARY KEY (AdminId)
);

CREATE TABLE Teacher(
	TeacherId INTEGER,
	EducationalQualification VARCHAR(255),
	YearsOfExperience INTEGER,
	Specialization VARCHAR(255),
	PRIMARY KEY (TeacherId)
);

CREATE TABLE PreviousWorkHistory(
	PreviousWorkId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	TeacherId INTEGER,
	PreviousSchoolName VARCHAR(100),
	PreviousSchoolAddress1 VARCHAR(255),
	PreviousSchoolAddress2 VARCHAR(255),
	PreviousSchoolCity VARCHAR(100),
	PreviousSchoolZip VARCHAR(20),
	PreviousSchoolState VARCHAR(50),
	PreviousSchoolCountry VARCHAR(50),
	PRIMARY KEY (PreviousWorkId)
);

CREATE TABLE Student(
	StudentId INTEGER,
	AdmissionId INTEGER,
	AdmissionStatus VARCHAR(100),
	StartDate TIMESTAMP,
	Ethnicity VARCHAR(50),
	Race VARCHAR(50),
	HealthRecordsReceived SMALLINT,
	DisabilitySupportNeeded SMALLINT,
	IepNeeded SMALLINT,
	PRIMARY KEY (StudentId)
 );
 
CREATE TABLE PreviousEducation(
	PreviousSchoolId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER,
	PreviousSchoolName VARCHAR(100),
	PreviousSchoolAddress1 VARCHAR(255),
	PreviousSchoolAddress2 VARCHAR(255),
	PreviousSchoolCity VARCHAR(100),
	PreviousSchoolZip VARCHAR(20),
	PreviousSchoolState VARCHAR(50),
	PreviousSchoolCountry VARCHAR(50),
	LastAttendedGradeLevel VARCHAR(20),
	PRIMARY KEY (PreviousSchoolId) 
 );

CREATE TABLE GradeLevel(
	GradeLevelId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	GradeLevel VARCHAR(255),
	Description VARCHAR(255),
	Sortorder Integer,
	PRIMARY KEY (GradeLevelId)
);

CREATE TABLE StudentGradeLevel(
	StudentGradeLevelId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	GradeLevelId INTEGER,
	StudentId INTEGER,
	SchoolYear INTEGER,
	Status VARCHAR(100),
	PRIMARY KEY (StudentGradeLevelId)
);
 
CREATE TABLE StudentScoreCard(
	StudentScoreCardId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER,
	SubjectId INTEGER,
	SchoolYear INTEGER,
	FinalScore VARCHAR(100),
	Comments VARCHAR(255),
	Status VARCHAR(100),
	GradeLetter VARCHAR(5),
	PRIMARY KEY (StudentScoreCardId)
);

CREATE TABLE SchoolYearSchedule(
	SchoolYear INTEGER NOT NULL,
	StartDate TIMESTAMP,
	EndDate TIMESTAMP,
	PRIMARY KEY (SchoolYear)
);

CREATE TABLE Period(
	PeriodId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Description VARCHAR(255),
	PeriodCode VARCHAR(255),
	SortOrder INTEGER,
	StartTime VARCHAR(255),
	EndTime VARCHAR(255),
	SchoolYear INTEGER,
	PRIMARY KEY (PeriodId) 
);

CREATE TABLE Subject(
	SubjectId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	SubjectName VARCHAR(100),
	Description VARCHAR(255),
	PRIMARY KEY (SubjectId)
);

CREATE TABLE SubjectSchedule(
	SubjectScheduleId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	ScheduleName VARCHAR(100),
	PrimaryTeacherId INTEGER,
	SecondaryTeacherId INTEGER,
	PeriodId INTEGER,
	SubjectId INTEGER,
	SchoolYear INTEGER,
	ScheduleDays VARCHAR(100),
	PRIMARY KEY (SubjectScheduleId)
);

CREATE TABLE StudentSubjectSchedule(
	StudentSubjectScheduleId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER NOT NULL,
	SubjectScheduleId INTEGER NOT NULL,
	PRIMARY KEY (StudentSubjectScheduleId)
);

CREATE TABLE AttendanceTracking(
	AttendanceId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER,
	SubjectScheduleId INTEGER,
	SchoolYear INTEGER,
	AttendanceDate DATE,
	AttendanceFlag SMALLINT,
	AttendanceTakenBy VARCHAR(100),
	PRIMARY KEY (AttendanceId) 
);

CREATE TABLE Messages(
	MessageId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	MessageCreatedBy INTEGER,
	Subject VARCHAR(255),
	MessageCreatedDate TIMESTAMP,
	PRIMARY KEY (MessageId)
);

CREATE TABLE Conversations(
	ConversationId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	ConversationText CLOB NOT NULL,
	SentBy INTEGER,
	SentDate TIMESTAMP,
	ParentConversationId INTEGER,
	MessageId INTEGER,
	PRIMARY KEY (ConversationId)
);

CREATE TABLE Recipients(
	RecipientsRowId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	ConversationId INTEGER,
	RecipientId INTEGER,
	MessageReadDate TIMESTAMP,
	PRIMARY KEY (RecipientsRowId)
);

CREATE TABLE IepGoals(
	IepGoalId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER,
	SchoolYear INTEGER,
	GoalTitle VARCHAR(255),
	GoalDescription CLOB NOT NULL,
	Status VARCHAR(255),
	StartDate TIMESTAMP,
	EndDate TIMESTAMP,
	LastUpdatedBy VARCHAR(100),
	LastUpdatedDate TIMESTAMP,
	PRIMARY KEY (IepGoalId)
);

CREATE TABLE IepProgress(
	IepProgressId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	IepGoalId INTEGER,
	ProgressDetails CLOB NOT NULL,
	LastUpdatedBy VARCHAR(100),
	LastUpdatedDate TIMESTAMP,
	PRIMARY KEY (IepProgressId) 
);

CREATE TABLE IepProgressResources(
	IepProgressResourceId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	IepProgressId INTEGER,
	ResourceName VARCHAR(100),
	ResourceUrl VARCHAR(255),
	UploadedDate TIMESTAMP,
	PRIMARY KEY (IepProgressResourceId) 
);

CREATE TABLE Settings(
	SettingId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	SettingName VARCHAR(100) NOT NULL,
	SettingValue CLOB NOT NULL,
	LastUpdatedDate TIMESTAMP,
	PRIMARY KEY (SettingId)
);


ALTER TABLE Users ADD CONSTRAINT Users_FK00 FOREIGN KEY(RoleId) REFERENCES Role (RoleId) ON DELETE SET NULL;
ALTER TABLE UserProfile  ADD  CONSTRAINT UserProfile_FK00 FOREIGN KEY(UserId) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE Admin ADD CONSTRAINT Admin_FK00 FOREIGN KEY(AdminId) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE AdmissionStep ADD CONSTRAINT AdmissionStep_FK00 FOREIGN KEY(AdmissionId) REFERENCES Admission (AdmissionId) ON DELETE CASCADE;
ALTER TABLE AdmissionStepComment ADD CONSTRAINT AdmissionStepComment_FK00 FOREIGN KEY(AdmissionId) REFERENCES Admission (AdmissionId) ON DELETE CASCADE;
ALTER TABLE AdmissionStepComment ADD CONSTRAINT AdmissionStepComment_FK01 FOREIGN KEY(AdmissionStepId) REFERENCES AdmissionStep (AdmissionStepId) ON DELETE CASCADE;
ALTER TABLE Student ADD CONSTRAINT Student_FK00 FOREIGN KEY(StudentId) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE Parent ADD CONSTRAINT Parent_FK00 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;
ALTER TABLE Teacher ADD CONSTRAINT Teacher_FK00 FOREIGN KEY(TeacherId) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE PreviousWorkHistory ADD CONSTRAINT PreviousWorkHistory_FK00 FOREIGN KEY(TeacherId) REFERENCES Teacher (TeacherId) ON DELETE CASCADE;
ALTER TABLE PreviousEducation ADD CONSTRAINT PreviousEducation_FK00 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;

ALTER TABLE StudentGradeLevel ADD CONSTRAINT StudentGradeLevel_FK00 FOREIGN KEY(GradeLevelId) REFERENCES GradeLevel (GradeLevelId) ON DELETE SET NULL;
ALTER TABLE StudentGradeLevel ADD CONSTRAINT StudentGradeLevel_FK01 FOREIGN KEY(SchoolYear) REFERENCES SchoolYearSchedule (SchoolYear) ON DELETE SET NULL;
ALTER TABLE StudentGradeLevel ADD CONSTRAINT StudentGradeLevel_FK02 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;

ALTER TABLE StudentScoreCard ADD CONSTRAINT StudentGrade_FK00 FOREIGN KEY(SchoolYear) REFERENCES SchoolYearSchedule (SchoolYear) ON DELETE SET NULL;
ALTER TABLE StudentScoreCard ADD CONSTRAINT StudentGrade_FK01 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;
ALTER TABLE StudentScoreCard ADD CONSTRAINT StudentGrade_FK02 FOREIGN KEY(SubjectId) REFERENCES Subject (SubjectId) ON DELETE SET NULL;

ALTER TABLE Period ADD CONSTRAINT Period_FK00 FOREIGN KEY(SchoolYear) REFERENCES SchoolYearSchedule (SchoolYear) ON DELETE SET NULL;

ALTER TABLE SubjectSchedule ADD CONSTRAINT SubjectSchedule_FK00 FOREIGN KEY(SchoolYear) REFERENCES SchoolYearSchedule (SchoolYear) ON DELETE SET NULL;
ALTER TABLE SubjectSchedule ADD CONSTRAINT SubjectSchedule_FK01 FOREIGN KEY(PeriodId) REFERENCES Period (PeriodId) ON DELETE SET NULL;
ALTER TABLE SubjectSchedule ADD CONSTRAINT SubjectSchedule_FK02 FOREIGN KEY(SubjectId) REFERENCES Subject (SubjectId) ON DELETE SET NULL;
ALTER TABLE SubjectSchedule ADD CONSTRAINT SubjectSchedule_FK03 FOREIGN KEY(PrimaryTeacherId) REFERENCES Teacher (TeacherId) ON DELETE CASCADE;
ALTER TABLE SubjectSchedule ADD CONSTRAINT SubjectSchedule_FK04 FOREIGN KEY(SecondaryTeacherId) REFERENCES Teacher (TeacherId) ON DELETE CASCADE;

ALTER TABLE StudentSubjectSchedule ADD CONSTRAINT StudentSubjectSchedule_FK00 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;
ALTER TABLE StudentSubjectSchedule ADD CONSTRAINT StudentSubjectSchedule_FK01 FOREIGN KEY(SubjectScheduleId) REFERENCES SubjectSchedule (SubjectScheduleId) ON DELETE CASCADE;

ALTER TABLE AttendanceTracking ADD CONSTRAINT AttendanceTracking_FK00 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;
ALTER TABLE AttendanceTracking ADD CONSTRAINT AttendanceTracking_FK01 FOREIGN KEY(SubjectScheduleId ) REFERENCES SubjectSchedule (SubjectScheduleId) ON DELETE CASCADE;

ALTER TABLE Messages ADD CONSTRAINT Messages_FK00 FOREIGN KEY(MessageCreatedBy) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE Conversations ADD CONSTRAINT Conversations_FK00 FOREIGN KEY(MessageId) REFERENCES Messages (MessageId) ON DELETE CASCADE;
ALTER TABLE Conversations ADD CONSTRAINT Conversations_FK01 FOREIGN KEY(SentBy) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE Conversations ADD CONSTRAINT Conversations_FK02 FOREIGN KEY(ParentConversationId) REFERENCES Conversations (ConversationId) ON DELETE CASCADE;
ALTER TABLE Recipients ADD CONSTRAINT Recipients_FK00 FOREIGN KEY(ConversationId) REFERENCES Conversations (ConversationId) ON DELETE CASCADE;
ALTER TABLE Recipients ADD CONSTRAINT Recipients_FK01 FOREIGN KEY(RecipientId) REFERENCES Users (UserId) ON DELETE CASCADE;

ALTER TABLE IepGoals ADD CONSTRAINT IepGoals_FK00 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;
ALTER TABLE IepProgress ADD CONSTRAINT IepProgress_FK00 FOREIGN KEY(IepGoalId) REFERENCES IepGoals (IepGoalId) ON DELETE CASCADE;
ALTER TABLE IepProgressResources ADD CONSTRAINT IepProgressResources_FK00 FOREIGN KEY(IepProgressId) REFERENCES IepProgress (IepProgressId) ON DELETE CASCADE;

ALTER TABLE Settings ADD CONSTRAINT Settings_UK00 UNIQUE (SettingName);


INSERT INTO ROLE (ROLENAME) VALUES ('admin');
INSERT INTO ROLE (ROLENAME) VALUES ('teacher');
INSERT INTO ROLE (ROLENAME) VALUES ('student');

INSERT INTO USERS (USERLOGINNAME, ROLEID, PASSWORD, CREATEDDATE, ENDDATE, ACTIVE, CREATEDBY, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES ('admin', 1, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO USERS (USERLOGINNAME, ROLEID, PASSWORD, CREATEDDATE, ENDDATE, ACTIVE, CREATEDBY, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES ('teacher', 2, 'teacher', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO USERS (USERLOGINNAME, ROLEID, PASSWORD, CREATEDDATE, ENDDATE, ACTIVE, CREATEDBY, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES ('student', 3, 'student', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO USERS (USERLOGINNAME, ROLEID, PASSWORD, CREATEDDATE, ENDDATE, ACTIVE, CREATEDBY, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES ('abhi', 3, 'abhi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO USERS (USERLOGINNAME, ROLEID, PASSWORD, CREATEDDATE, ENDDATE, ACTIVE, CREATEDBY, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES ('mark', 3, 'mark', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO USERS (USERLOGINNAME, ROLEID, PASSWORD, CREATEDDATE, ENDDATE, ACTIVE, CREATEDBY, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES ('madison', 3, 'madison', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, NULL, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO USERPROFILE (USERID, FIRSTNAME, LASTNAME, MIDDLENAME, DATEOFBIRTH, GENDER, CURRENTADDRESS1, CURRENTADDRESS2, CURRENTCITY, CURRENTZIP, CURRENTSTATE, CURRENTCOUNTRY, MAILINGADDRESS1, MAILINGADDRESS2, MAILINGCITY, MAILINGZIP, MAILINGSTATE, MAILINGCOUNTRY, EMAIL1, EMAIL2, HOMEPHONE, MOBILEPHONE, WORKPHONE) 
	VALUES (1, 'Admin', 'Admin', NULL, '1975-03-03 08:10:28.032', 'Male', NULL, NULL, 'Windsor Locks', '06096', 'CT', 'USA', NULL, NULL, NULL, NULL, NULL, NULL, 'admin123@myschool.edu', NULL, '860-1123-555', NULL, NULL);
INSERT INTO USERPROFILE (USERID, FIRSTNAME, LASTNAME, MIDDLENAME, DATEOFBIRTH, GENDER, CURRENTADDRESS1, CURRENTADDRESS2, CURRENTCITY, CURRENTZIP, CURRENTSTATE, CURRENTCOUNTRY, MAILINGADDRESS1, MAILINGADDRESS2, MAILINGCITY, MAILINGZIP, MAILINGSTATE, MAILINGCOUNTRY, EMAIL1, EMAIL2, HOMEPHONE, MOBILEPHONE, WORKPHONE) 
	VALUES (2, 'Hijack', 'Houston', NULL, '1979-03-03 08:10:28.032', 'Female', NULL, NULL, 'Windsor', '06095', 'CT', 'USA', NULL, NULL, NULL, NULL, NULL, NULL, 'hijack@myschool.edu', NULL, '860-4523-759', NULL, NULL);
INSERT INTO USERPROFILE (USERID, FIRSTNAME, LASTNAME, MIDDLENAME, DATEOFBIRTH, GENDER, CURRENTADDRESS1, CURRENTADDRESS2, CURRENTCITY, CURRENTZIP, CURRENTSTATE, CURRENTCOUNTRY, MAILINGADDRESS1, MAILINGADDRESS2, MAILINGCITY, MAILINGZIP, MAILINGSTATE, MAILINGCOUNTRY, EMAIL1, EMAIL2, HOMEPHONE, MOBILEPHONE, WORKPHONE) 
	VALUES (3, 'Anthony', 'Austin', NULL, '2000-03-03 08:10:28.032', 'Male', NULL, NULL, 'Windsor', '06095', 'CT', 'USA', NULL, NULL, NULL, NULL, NULL, NULL, 'Anthony@myschool.edu', NULL, '860-4888-741', NULL, NULL);
INSERT INTO USERPROFILE (USERID, FIRSTNAME, LASTNAME, MIDDLENAME, DATEOFBIRTH, GENDER, CURRENTADDRESS1, CURRENTADDRESS2, CURRENTCITY, CURRENTZIP, CURRENTSTATE, CURRENTCOUNTRY, MAILINGADDRESS1, MAILINGADDRESS2, MAILINGCITY, MAILINGZIP, MAILINGSTATE, MAILINGCOUNTRY, EMAIL1, EMAIL2, HOMEPHONE, MOBILEPHONE, WORKPHONE) 
	VALUES (4, 'Abhinav', 'Karumudi', NULL, '2007-03-03 08:10:28.032', 'Male', NULL, NULL, 'Windsor Locks', '06096', 'CT', 'USA', NULL, NULL, NULL, NULL, NULL, NULL, 'abhinav@myschool.edu', NULL, '860-2222-453', NULL, NULL);	
INSERT INTO USERPROFILE (USERID, FIRSTNAME, LASTNAME, MIDDLENAME, DATEOFBIRTH, GENDER, CURRENTADDRESS1, CURRENTADDRESS2, CURRENTCITY, CURRENTZIP, CURRENTSTATE, CURRENTCOUNTRY, MAILINGADDRESS1, MAILINGADDRESS2, MAILINGCITY, MAILINGZIP, MAILINGSTATE, MAILINGCOUNTRY, EMAIL1, EMAIL2, HOMEPHONE, MOBILEPHONE, WORKPHONE) 
	VALUES (5, 'Mark', 'Grasso', NULL, '2000-03-03 08:10:28.032', 'Male', NULL, NULL, 'Windsor', '06095', 'CT', 'USA', NULL, NULL, NULL, NULL, NULL, NULL, 'mark@myschool.edu', NULL, '860-4888-741', NULL, NULL);
INSERT INTO USERPROFILE (USERID, FIRSTNAME, LASTNAME, MIDDLENAME, DATEOFBIRTH, GENDER, CURRENTADDRESS1, CURRENTADDRESS2, CURRENTCITY, CURRENTZIP, CURRENTSTATE, CURRENTCOUNTRY, MAILINGADDRESS1, MAILINGADDRESS2, MAILINGCITY, MAILINGZIP, MAILINGSTATE, MAILINGCOUNTRY, EMAIL1, EMAIL2, HOMEPHONE, MOBILEPHONE, WORKPHONE) 
	VALUES (6, 'Madison', 'Polaske', NULL, '2001-03-03 08:10:28.032', 'Male', NULL, NULL, 'Windsor', '06095', 'CT', 'USA', NULL, NULL, NULL, NULL, NULL, NULL, 'madison@myschool.edu', NULL, '860-4888-741', NULL, NULL);
	
INSERT INTO ADMISSION (DESCRIPTION, CREATEDDATE, ENDDATE, STATUS, APPLICATIONTYPE, GRADELEVELAPPLYINGFOR, TRACKINGNUMBER, ADMISSIONSEEKINGYEAR, CREATEDBY, LASTUPDATEBY, LASTUPDATEDDATE) 
	VALUES ('', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Approved', 'Manual', '1', NULL, '2012', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO ADMISSION (DESCRIPTION, CREATEDDATE, ENDDATE, STATUS, APPLICATIONTYPE, GRADELEVELAPPLYINGFOR, TRACKINGNUMBER, ADMISSIONSEEKINGYEAR, CREATEDBY, LASTUPDATEBY, LASTUPDATEDDATE) 
	VALUES ('', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Approved', 'Manual', '1', NULL, '2012', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO ADMISSION (DESCRIPTION, CREATEDDATE, ENDDATE, STATUS, APPLICATIONTYPE, GRADELEVELAPPLYINGFOR, TRACKINGNUMBER, ADMISSIONSEEKINGYEAR, CREATEDBY, LASTUPDATEBY, LASTUPDATEDDATE) 
	VALUES ('', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Approved', 'Manual', '1', NULL, '2012', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO ADMISSION (DESCRIPTION, CREATEDDATE, ENDDATE, STATUS, APPLICATIONTYPE, GRADELEVELAPPLYINGFOR, TRACKINGNUMBER, ADMISSIONSEEKINGYEAR, CREATEDBY, LASTUPDATEBY, LASTUPDATEDDATE) 
	VALUES ('', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Approved', 'Manual', '1', NULL, '2012', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO SCHOOLYEARSCHEDULE (SCHOOLYEAR, STARTDATE, ENDDATE) 
	VALUES (2012, '2012-09-01 18:04:09.815', '2013-05-30 18:05:00.519');
	
INSERT INTO GRADELEVEL (GRADELEVEL, SORTORDER, DESCRIPTION) 
	VALUES ('Freshman', 1, NULL);
INSERT INTO GRADELEVEL (GRADELEVEL, SORTORDER, DESCRIPTION) 
	VALUES ('Junior', 2, NULL);
INSERT INTO GRADELEVEL (GRADELEVEL, SORTORDER, DESCRIPTION) 
	VALUES ('Senior', 3, NULL);

INSERT INTO SUBJECT (SUBJECTNAME, Sortorder, DESCRIPTION) 
	VALUES ('Drawing', NULL);
INSERT INTO SUBJECT (SUBJECTNAME, DESCRIPTION) 
	VALUES ('English', NULL);
INSERT INTO SUBJECT (SUBJECTNAME, DESCRIPTION) 
	VALUES ('Spanish', NULL);
INSERT INTO SUBJECT (SUBJECTNAME, DESCRIPTION) 
	VALUES ('Maths', NULL);
INSERT INTO SUBJECT (SUBJECTNAME, DESCRIPTION) 
	VALUES ('Music', NULL);
	
INSERT INTO PERIOD (DESCRIPTION, PERIODCODE, SORTORDER, STARTTIME, ENDTIME, SCHOOLYEAR) 
	VALUES ('Period 1', 'P1', 1, '8:30 AM', '9:30 AM', 2012);
INSERT INTO PERIOD (DESCRIPTION, PERIODCODE, SORTORDER, STARTTIME, ENDTIME, SCHOOLYEAR) 
	VALUES ('Period 2', 'P2', 2, '9:30 AM', '10:30 AM', 2012);
INSERT INTO PERIOD (DESCRIPTION, PERIODCODE, SORTORDER, STARTTIME, ENDTIME, SCHOOLYEAR) 
	VALUES ('Period 3', 'P3', 3, '10:30 AM', '11:30 AM', 2012);
INSERT INTO PERIOD (DESCRIPTION, PERIODCODE, SORTORDER, STARTTIME, ENDTIME, SCHOOLYEAR) 
	VALUES ('Period 4', 'P4', 4, '12:30 PM', '1:30 PM', 2012);
INSERT INTO PERIOD (DESCRIPTION, PERIODCODE, SORTORDER, STARTTIME, ENDTIME, SCHOOLYEAR) 
	VALUES ('Period 5', 'P5', 5, '1:30 PM', '3:30 PM', 2012);
	
INSERT INTO TEACHER (TEACHERID, EDUCATIONALQUALIFICATION, YEARSOFEXPERIENCE, SPECIALIZATION) 
	VALUES (2, 'MS', 10, 'Maths');

INSERT INTO SUBJECTSCHEDULE (SCHEDULENAME, PRIMARYTEACHERID, SECONDARYTEACHERID, PERIODID, SUBJECTID, SCHOOLYEAR, SCHEDULEDAYS) 
	VALUES ('Drawing Session', 2, NULL, 1, 1, 2012, 'M,T,W,TH,F');
INSERT INTO SUBJECTSCHEDULE (SCHEDULENAME, PRIMARYTEACHERID, SECONDARYTEACHERID, PERIODID, SUBJECTID, SCHOOLYEAR, SCHEDULEDAYS) 
	VALUES ('English Session', 2, NULL, 2, 2, 2012, 'M,T,W,TH,F');
INSERT INTO SUBJECTSCHEDULE (SCHEDULENAME, PRIMARYTEACHERID, SECONDARYTEACHERID, PERIODID, SUBJECTID, SCHOOLYEAR, SCHEDULEDAYS) 
	VALUES ('Spanish Session', 2, NULL, 3, 3, 2012, 'M,T,W,TH,F');
INSERT INTO SUBJECTSCHEDULE (SCHEDULENAME, PRIMARYTEACHERID, SECONDARYTEACHERID, PERIODID, SUBJECTID, SCHOOLYEAR, SCHEDULEDAYS) 
	VALUES ('Maths Session', 2, NULL, 4, 4, 2012, 'M,T,W,TH,F');
INSERT INTO SUBJECTSCHEDULE (SCHEDULENAME, PRIMARYTEACHERID, SECONDARYTEACHERID, PERIODID, SUBJECTID, SCHOOLYEAR, SCHEDULEDAYS) 
	VALUES ('Music Session', 2, NULL, 5, 5, 2012, 'M,T,W,TH,F');

INSERT INTO STUDENT (STUDENTID, ADMISSIONID, ADMISSIONSTATUS, STARTDATE, ETHNICITY, RACE, HEALTHRECORDSRECEIVED, DISABILITYSUPPORTNEEDED, IEPNEEDED) 
	VALUES (3, 1, 'Approved', CURRENT_TIMESTAMP, NULL, 'American ', 1, 0, 0);
INSERT INTO STUDENT (STUDENTID, ADMISSIONID, ADMISSIONSTATUS, STARTDATE, ETHNICITY, RACE, HEALTHRECORDSRECEIVED, DISABILITYSUPPORTNEEDED, IEPNEEDED) 
	VALUES (4, 2, 'Approved', CURRENT_TIMESTAMP, NULL, 'American ', 1, 0, 0);
INSERT INTO STUDENT (STUDENTID, ADMISSIONID, ADMISSIONSTATUS, STARTDATE, ETHNICITY, RACE, HEALTHRECORDSRECEIVED, DISABILITYSUPPORTNEEDED, IEPNEEDED) 
	VALUES (5, 3, 'Approved', CURRENT_TIMESTAMP, NULL, 'American ', 1, 1, 1);
INSERT INTO STUDENT (STUDENTID, ADMISSIONID, ADMISSIONSTATUS, STARTDATE, ETHNICITY, RACE, HEALTHRECORDSRECEIVED, DISABILITYSUPPORTNEEDED, IEPNEEDED) 
	VALUES (6, 4, 'Approved', CURRENT_TIMESTAMP, NULL, 'American ', 1, 1, 1);
	
INSERT INTO STUDENTGRADELEVEL (GRADELEVELID, STUDENTID, SCHOOLYEAR, STATUS) 
	VALUES (1, 3, 2012, null);
INSERT INTO STUDENTGRADELEVEL (GRADELEVELID, STUDENTID, SCHOOLYEAR, STATUS) 
	VALUES (1, 4, 2012, null);
INSERT INTO STUDENTGRADELEVEL (GRADELEVELID, STUDENTID, SCHOOLYEAR, STATUS) 
	VALUES (1, 5, 2012, null);
INSERT INTO STUDENTGRADELEVEL (GRADELEVELID, STUDENTID, SCHOOLYEAR, STATUS) 
	VALUES (2, 6, 2012, null);

INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (3, 1);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (3, 2);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (3, 3);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (3, 4);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (3, 5);

INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (4, 1);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (4, 2);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (4, 3);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (4, 4);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (4, 5);

INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (5, 1);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (5, 2);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (5, 3);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (5, 4);
INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (5, 5);

INSERT INTO STUDENTSUBJECTSCHEDULE (STUDENTID, SUBJECTSCHEDULEID) VALUES (6, 5);

INSERT INTO IEPGOALS (STUDENTID, SCHOOLYEAR, GOALTITLE, GOALDESCRIPTION, STATUS, STARTDATE, ENDDATE, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES (5, 2012, 'Developmental of Speech', 'DAS is a speech disorder that interferes with a child''s ability to correctly pronounce sounds, syllables and words.', 'beginner', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Hijack Houston', CURRENT_TIMESTAMP);

INSERT INTO IEPPROGRESS (IEPGOALID, PROGRESSDETAILS, LASTUPDATEDBY, LASTUPDATEDDATE) 
	VALUES (1, 'working on speach', 'Hijack Houston', CURRENT_TIMESTAMP);
	
INSERT INTO IEPPROGRESSRESOURCES (IEPPROGRESSID, RESOURCENAME, RESOURCEURL, UPLOADEDDATE) 
	VALUES (1, 'Speech', 'http://www.tayloredmktg.com/dyspraxia/downloads/das.pdf', CURRENT_TIMESTAMP);
INSERT INTO IEPPROGRESSRESOURCES (IEPPROGRESSID, RESOURCENAME, RESOURCEURL, UPLOADEDDATE) 
	VALUES (1, 'IEP', 'http://www.youtube.com/watch?v=qh1meBo_m1w', CURRENT_TIMESTAMP);
INSERT INTO IEPPROGRESSRESOURCES (IEPPROGRESSID, RESOURCENAME, RESOURCEURL, UPLOADEDDATE) 
	VALUES (1, '', '', CURRENT_TIMESTAMP);
INSERT INTO IEPPROGRESSRESOURCES (IEPPROGRESSID, RESOURCENAME, RESOURCEURL, UPLOADEDDATE) 
	VALUES (1, '', '', CURRENT_TIMESTAMP);
INSERT INTO IEPPROGRESSRESOURCES (IEPPROGRESSID, RESOURCENAME, RESOURCEURL, UPLOADEDDATE) 
	VALUES (1, '', '', CURRENT_TIMESTAMP);
	
INSERT INTO MESSAGES (MESSAGECREATEDBY, SUBJECT, MESSAGECREATEDDATE) 
	VALUES (2, 'Blue Communication Folder ', CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p><font color="#3300ff" face="Comic Sans MS" size="3">Your child will have a blue communication folder that will be sent home each day.&nbsp; This folder will contain important notes regarding trips, concerts, lunches, special events, and notes from your child&#39;s teacher.&nbsp; It is IMPERATIVE that you send this folder back to school each day.</font></p><p>	<font color="#3300ff" face="Comic Sans MS" size="3">Here are some highly-suggested tips for using this folder effectively:</font></p><p>	<font color="#ff33ff" face="Comic Sans MS" size="3">1. Please keep the Lunch Menu in the <strong>return to school</strong> side of the folder.&nbsp; My Instructional Assistant will change out this menu each month.&nbsp; Each morning/night before school, ask your child what he or she would like for lunch the following day.&nbsp; Please then highlight his or her lunch choice on the menu.&nbsp;&nbsp; If your child is packing, please write a &quot;P&quot; on that particular day.&nbsp; This makes our morning routine MUCH easier!</font></p><p>	<font color="#3300ff" face="Comic Sans MS" size="3">2. I have included a small notebook made out of oak tag in your child&#39;s folder.&nbsp; Please also keep this notebook in your child&#39;s folder in the <strong>return to school</strong> side.&nbsp; This notebook is to be used for communication between parents and teacher.&nbsp; If I have written a note in your child&#39;s notebook, the notebook will be opened to the page where the note can be found.&nbsp; If you write a note to me, please do the same so that I can find it quickly!&nbsp; I have found this to be easy way of communication between parents and teachers in the past.&nbsp; As always, you can email me if you need to communicate also.</font></p><p>	<font color="#339966" face="Comic Sans MS" size="3">3. All other materials that are sent home will be found in the <strong>keep at home</strong> side of the folder.&nbsp; Please try to clean this side of the folder out each night as it makes the journal less bulky and easy to use.&nbsp; If there is a form that needs to be returned to school, please place the form in FRONT of the lunch menu and the communication journal so that it is easily accessible.</font></p>', 2, CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p>Thank you for providing this information.</p>', 3, CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (2, 2, CURRENT_TIMESTAMP);

	
INSERT INTO MESSAGES (MESSAGECREATEDBY, SUBJECT, MESSAGECREATEDDATE) 
	VALUES (2, 'Fundraiser Update ', CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p>Hello!  If you missed the pick-up date for fundraiser items, you can still pick them up here at the school.  They will be held for approx. 2 more weeks and are available during office hours (8:30-4:15).</p><p></p><p>Students are not allowed to bring the items home on the bus.  Thank you so much!</p>', 2, CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (3, 3, CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p>Thank you for the update.</p>', 3, CURRENT_TIMESTAMP, NULL, 2);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (4, 2, CURRENT_TIMESTAMP);

	
INSERT INTO MESSAGES (MESSAGECREATEDBY, SUBJECT, MESSAGECREATEDDATE) 
	VALUES (3, 'Sick Leave ', CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p>Dear Mrs. Hijack</p>	<p>I just wanted to let you know, as my dauther suffering from heavy fever and cold. She was unable to attend class today.</p>	<p>Thank you</p><p>Maria</p>', 3, CURRENT_TIMESTAMP, NULL, 3);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (5, 2, CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p>Thank you for letting us know, hope she will feel better soon.</p>', 2, CURRENT_TIMESTAMP, NULL, 3);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (6, 3, CURRENT_TIMESTAMP);
INSERT INTO CONVERSATIONS (CONVERSATIONTEXT, SENTBY, SENTDATE, PARENTCONVERSATIONID, MESSAGEID) 
	VALUES ('<p>Thank you!</p>', 3, CURRENT_TIMESTAMP, NULL, 3);
INSERT INTO RECIPIENTS (CONVERSATIONID, RECIPIENTID, MESSAGEREADDATE) 
	VALUES (7, 2, CURRENT_TIMESTAMP);


	

	
