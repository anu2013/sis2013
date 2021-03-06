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
	SchoolYear INTEGER,
	AttendanceDate TIMESTAMP,
	AttendanceFlag SMALLINT,
	AttendanceTakenBy INTEGER,
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
	ConversationText VARCHAR(255),
	SentBy INTEGER,
	SentDate TIMESTAMP,
	ParentConversationId INTEGER,
	MessageId INTEGER,
	PRIMARY KEY (ConversationId)
);

CREATE TABLE Recipients(
	RecipientsRowId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	MessageId INTEGER,
	RecipientId INTEGER,
	MessageReadDate TIMESTAMP,
	PRIMARY KEY (RecipientsRowId)
);

CREATE TABLE IepGoals(
	IepGoalId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	StudentId INTEGER,
	SchoolYear INTEGER,
	GoalTitle VARCHAR(255),
	GoalDescription VARCHAR(255),
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
	ProgressDetails VARCHAR(255),
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

ALTER TABLE Messages ADD CONSTRAINT Messages_FK00 FOREIGN KEY(MessageCreatedBy) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE Conversations ADD CONSTRAINT Conversations_FK00 FOREIGN KEY(MessageId) REFERENCES Messages (MessageId) ON DELETE CASCADE;
ALTER TABLE Conversations ADD CONSTRAINT Conversations_FK01 FOREIGN KEY(SentBy) REFERENCES Users (UserId) ON DELETE CASCADE;
ALTER TABLE Conversations ADD CONSTRAINT Conversations_FK02 FOREIGN KEY(ParentConversationId) REFERENCES Conversations (ConversationId) ON DELETE CASCADE;
ALTER TABLE Recipients ADD CONSTRAINT Recipients_FK00 FOREIGN KEY(MessageId) REFERENCES Messages (MessageId) ON DELETE CASCADE;
ALTER TABLE Recipients ADD CONSTRAINT Recipients_FK01 FOREIGN KEY(RecipientId) REFERENCES Users (UserId) ON DELETE CASCADE;

ALTER TABLE IepGoals ADD CONSTRAINT IepGoals_FK00 FOREIGN KEY(StudentId) REFERENCES Student (StudentId) ON DELETE CASCADE;
ALTER TABLE IepProgress ADD CONSTRAINT IepProgress_FK00 FOREIGN KEY(IepGoalId) REFERENCES IepGoals (IepGoalId) ON DELETE CASCADE;
ALTER TABLE IepProgressResources ADD CONSTRAINT IepProgressResources_FK00 FOREIGN KEY(IepProgressId) REFERENCES IepProgress (IepProgressId) ON DELETE CASCADE;
