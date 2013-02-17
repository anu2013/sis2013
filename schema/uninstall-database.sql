ALTER TABLE Users DROP CONSTRAINT Users_FK00;
ALTER TABLE UserProfile DROP CONSTRAINT UserProfile_FK00;
ALTER TABLE Admin DROP CONSTRAINT Admin_FK00;
ALTER TABLE Student DROP CONSTRAINT Student_FK00;
ALTER TABLE Parent DROP CONSTRAINT Parent_FK00;
ALTER TABLE Teacher DROP CONSTRAINT Teacher_FK00;
ALTER TABLE PreviousWorkHistory DROP CONSTRAINT PreviousWorkHistory_FK00;
ALTER TABLE StudentGradeLevel DROP CONSTRAINT StudentGradeLevel_FK00;
ALTER TABLE StudentGradeLevel DROP CONSTRAINT StudentGradeLevel_FK01;
ALTER TABLE StudentGradeLevel DROP CONSTRAINT StudentGradeLevel_FK02;
ALTER TABLE StudentScoreCard DROP CONSTRAINT StudentGrade_FK00;
ALTER TABLE StudentScoreCard DROP CONSTRAINT StudentGrade_FK01;
ALTER TABLE StudentScoreCard DROP CONSTRAINT StudentGrade_FK02;
ALTER TABLE Period DROP CONSTRAINT Period_FK00;
ALTER TABLE SubjectSchedule DROP CONSTRAINT SubjectSchedule_FK00;
ALTER TABLE SubjectSchedule DROP CONSTRAINT SubjectSchedule_FK01;
ALTER TABLE SubjectSchedule DROP CONSTRAINT SubjectSchedule_FK02;
ALTER TABLE SubjectSchedule DROP CONSTRAINT SubjectSchedule_FK03;
ALTER TABLE SubjectSchedule DROP CONSTRAINT SubjectSchedule_FK04;
ALTER TABLE StudentSubjectSchedule DROP CONSTRAINT StudentSubjectSchedule_FK00;
ALTER TABLE StudentSubjectSchedule DROP CONSTRAINT StudentSubjectSchedule_FK01;
ALTER TABLE AttendanceTracking DROP CONSTRAINT AttendanceTracking_FK00;
ALTER TABLE Messages DROP CONSTRAINT Messages_FK00;
ALTER TABLE Conversations DROP CONSTRAINT Conversations_FK00;
ALTER TABLE Conversations DROP CONSTRAINT Conversations_FK01;
ALTER TABLE Conversations DROP CONSTRAINT Conversations_FK02;
ALTER TABLE Recipients DROP CONSTRAINT Recipients_FK00;
ALTER TABLE Recipients DROP CONSTRAINT Recipients_FK01;
ALTER TABLE IepGoals DROP CONSTRAINT IepGoals_FK00;
ALTER TABLE IepProgress DROP CONSTRAINT IepProgress_FK00;
ALTER TABLE IepProgressResources DROP CONSTRAINT IepProgressResources_FK00;

DROP TABLE PreviousWorkHistory;
DROP TABLE PreviousEducation;
DROP TABLE StudentGradeLevel;
DROP TABLE GradeLevel;
DROP TABLE StudentScoreCard;
DROP TABLE AttendanceTracking;
DROP TABLE StudentSubjectSchedule;
DROP TABLE SubjectSchedule;
DROP TABLE Recipients;
DROP TABLE Conversations;
DROP TABLE Messages;
DROP TABLE IepProgressResources;
DROP TABLE IepProgress;
DROP TABLE IepGoals;
DROP TABLE AdmissionStepComment;
DROP TABLE AdmissionStep;
DROP TABLE Admission;
DROP TABLE Parent;
DROP TABLE Admin;
DROP TABLE Student;
DROP TABLE Teacher;
DROP TABLE Period;
DROP TABLE Subject;
DROP TABLE UserProfile;
DROP TABLE Users;
DROP TABLE Role;
DROP TABLE SchoolYearSchedule;
