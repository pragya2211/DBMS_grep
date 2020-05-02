create table classes
(
    ID          int auto_increment,
    Name        varchar(50)    not null,
    Description varchar(10000) null,
    constraint classes_ID_uindex
        unique (ID)
);

alter table classes
    add primary key (ID);

create table classhierarchy
(
    ParentID int not null,
    ClassID  int not null,
    primary key (ParentID, ClassID),
    constraint classhierarchy_ibfk_1
        foreign key (ParentID) references classes (ID),
    constraint classhierarchy_ibfk_2
        foreign key (ClassID) references classes (ID)
);

create index ClassID
    on classhierarchy (ClassID);

create table exceptions
(
    ID          int auto_increment,
    Name        varchar(50)    not null,
    Description varchar(10000) null,
    constraint exceptions_ID_uindex
        unique (ID)
);

alter table exceptions
    add primary key (ID);

create table files
(
    ID          int auto_increment,
    Name        varchar(50)          not null,
    Location    varchar(500)         not null,
    Visibility  tinyint(1) default 1 not null,
    Description varchar(500)         null,
    constraint files_ID_uindex
        unique (ID),
    constraint files_Location_uindex
        unique (Location)
);

alter table files
    add primary key (ID);

create table classinfile
(
    ClassID int not null,
    FileID  int not null,
    primary key (ClassID, FileID),
    constraint classinfile_ibfk_1
        foreign key (ClassID) references classes (ID),
    constraint classinfile_ibfk_2
        foreign key (FileID) references files (ID)
);

create index FileID
    on classinfile (FileID);

create table interfaces
(
    ID          int auto_increment,
    Name        varchar(50)    not null,
    Description varchar(10000) null,
    constraint interfaces_ID_uindex
        unique (ID)
);

alter table interfaces
    add primary key (ID);

create table classimplementsinterface
(
    InterfaceID int not null,
    ClassID     int not null,
    primary key (InterfaceID, ClassID),
    constraint classimplementsinterface_ibfk_1
        foreign key (InterfaceID) references interfaces (ID),
    constraint classimplementsinterface_ibfk_2
        foreign key (ClassID) references classes (ID)
);

create index ClassID
    on classimplementsinterface (ClassID);

create table interfaceinfile
(
    InterfaceID int not null,
    FileID      int not null,
    primary key (InterfaceID, FileID),
    constraint interfaceinfile_ibfk_1
        foreign key (InterfaceID) references interfaces (ID),
    constraint interfaceinfile_ibfk_2
        foreign key (FileID) references files (ID)
);

create index FileID
    on interfaceinfile (FileID);

create table objects
(
    ID          int auto_increment,
    Name        varchar(50)    not null,
    Description varchar(10000) null,
    constraint objects_ID_uindex
        unique (ID)
);

alter table objects
    add primary key (ID);

create table functions
(
    ID           int auto_increment,
    Name         varchar(50)    not null,
    Description  varchar(10000) null,
    Return_value int default 0  not null,
    constraint functions_ID_uindex
        unique (ID),
    constraint functions_objects_ID_fk
        foreign key (Return_value) references objects (ID)
);

alter table functions
    add primary key (ID);

create table exceptionthrownbyfunctions
(
    ExceptionID int not null,
    FunctionID  int not null,
    primary key (ExceptionID, FunctionID),
    constraint exceptionthrownbyfunctions_ibfk_1
        foreign key (ExceptionID) references exceptions (ID),
    constraint exceptionthrownbyfunctions_ibfk_2
        foreign key (FunctionID) references functions (ID)
);

create index FunctionID
    on exceptionthrownbyfunctions (FunctionID);

create table functionsinclasses
(
    ClassID    int not null,
    FunctionID int not null,
    primary key (ClassID, FunctionID),
    constraint functionsinclasses_ibfk_1
        foreign key (ClassID) references classes (ID),
    constraint functionsinclasses_ibfk_2
        foreign key (FunctionID) references functions (ID)
);

create index FunctionID
    on functionsinclasses (FunctionID);

create table functionsininterfaces
(
    InterfaceID int not null,
    FunctionID  int not null,
    primary key (InterfaceID, FunctionID),
    constraint functionsininterfaces_ibfk_1
        foreign key (InterfaceID) references interfaces (ID),
    constraint functionsininterfaces_ibfk_2
        foreign key (FunctionID) references functions (ID)
);

create index FunctionID
    on functionsininterfaces (FunctionID);

create table objectdefinitioninclass
(
    ObjectID int not null,
    ClassID  int not null,
    primary key (ObjectID, ClassID),
    constraint objectdefinitioninclass_ibfk_1
        foreign key (ObjectID) references objects (ID),
    constraint objectdefinitioninclass_ibfk_2
        foreign key (ClassID) references classes (ID)
);

create index ClassID
    on objectdefinitioninclass (ClassID);

create table parameters_of_function
(
    Function_ID int not null,
    Object_ID   int not null,
    primary key (Function_ID, Object_ID),
    constraint parameters_of_function___fk_1
        foreign key (Object_ID) references objects (ID),
    constraint parameters_of_function_functions_ID_fk
        foreign key (Function_ID) references functions (ID)
);

create table repositories
(
    ID          int auto_increment,
    Name        varchar(50)          not null,
    Description varchar(1000)        null,
    Location    varchar(500)         not null,
    Upvotes     int        default 0 not null,
    Price       float      default 0 not null,
    Downvotes   int        default 0 not null,
    Visibility  tinyint(1) default 1 not null,
    constraint Repositories_ID_uindex
        unique (ID)
);

alter table repositories
    add primary key (ID);

create table filesinrepository
(
    RepositoryID int not null,
    FileID       int not null,
    primary key (RepositoryID, FileID),
    constraint filesinrepository_ibfk_1
        foreign key (RepositoryID) references repositories (ID),
    constraint filesinrepository_ibfk_2
        foreign key (FileID) references files (ID)
);

create index FileID
    on filesinrepository (FileID);

create table tags
(
    ID          int auto_increment,
    Name        varchar(50)  not null,
    Description varchar(500) null,
    constraint tags_ID_uindex
        unique (ID),
    constraint tags_Name_uindex
        unique (Name)
);

alter table tags
    add primary key (ID);

create table tagsofclasses
(
    TagID   int not null,
    ClassID int not null,
    primary key (TagID, ClassID),
    constraint tagsofclasses_ibfk_1
        foreign key (ClassID) references classes (ID),
    constraint tagsofclasses_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index ClassID
    on tagsofclasses (ClassID);

create table tagsofexceptions
(
    TagID       int not null,
    ExceptionID int not null,
    primary key (TagID, ExceptionID),
    constraint tagsofexceptions_ibfk_1
        foreign key (ExceptionID) references exceptions (ID),
    constraint tagsofexceptions_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index ExceptionID
    on tagsofexceptions (ExceptionID);

create table tagsoffiles
(
    TagID  int not null,
    FileID int not null,
    primary key (TagID, FileID),
    constraint tagsoffiles_ibfk_1
        foreign key (FileID) references files (ID),
    constraint tagsoffiles_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index FileID
    on tagsoffiles (FileID);

create table tagsoffunctions
(
    TagID      int not null,
    FunctionID int not null,
    primary key (TagID, FunctionID),
    constraint tagsoffunctions_ibfk_1
        foreign key (FunctionID) references functions (ID),
    constraint tagsoffunctions_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index FunctionID
    on tagsoffunctions (FunctionID);

create table tagsofinterfaces
(
    TagID       int not null,
    InterfaceID int not null,
    primary key (TagID, InterfaceID),
    constraint tagsofinterfaces_ibfk_1
        foreign key (InterfaceID) references interfaces (ID),
    constraint tagsofinterfaces_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index InterfaceID
    on tagsofinterfaces (InterfaceID);

create table tagsofobjects
(
    TagID    int not null,
    ObjectID int not null,
    primary key (TagID, ObjectID),
    constraint tagsofobjects_ibfk_1
        foreign key (ObjectID) references objects (ID),
    constraint tagsofobjects_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index ObjectID
    on tagsofobjects (ObjectID);

create table tagsofrepositories
(
    TagID        int not null,
    RepositoryID int not null,
    primary key (TagID, RepositoryID),
    constraint tagsofrepositories_ibfk_1
        foreign key (RepositoryID) references repositories (ID),
    constraint tagsofrepositories_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index RepositoryID
    on tagsofrepositories (RepositoryID);

create table users
(
    ID         int auto_increment,
    Name       varchar(50)    not null,
    Bio        varchar(500)   null,
    Reputation int default 0  null,
    Password   varchar(10000) not null,
    user_id    varchar(20)    not null,
    constraint users_ID_uindex
        unique (ID),
    constraint users_user_id_uindex
        unique (user_id)
);

alter table users
    add primary key (ID);

create table bugs
(
    ID          int auto_increment,
    Name        varchar(50)   not null,
    Description varchar(500)  null,
    Upvotes     int default 0 null,
    Downvotes   int default 0 null,
    Owner       int           not null,
    constraint bugs_ID_uindex
        unique (ID),
    constraint bugs_users_ID_fk
        foreign key (Owner) references users (ID)
);

alter table bugs
    add primary key (ID);

create table buginfile
(
    BugID  int not null,
    FileID int not null,
    primary key (BugID, FileID),
    constraint buginfile_ibfk_1
        foreign key (BugID) references bugs (ID),
    constraint buginfile_ibfk_2
        foreign key (FileID) references files (ID)
);

create index FileID
    on buginfile (FileID);

create table downvoterofbugs
(
    BugID  int not null,
    UserID int not null,
    primary key (UserID, BugID),
    constraint downvoterofbugs_ibfk_1
        foreign key (UserID) references users (ID),
    constraint downvoterofbugs_ibfk_2
        foreign key (BugID) references bugs (ID)
);

create index BugID
    on downvoterofbugs (BugID);

create table downvotersofrepository
(
    RepositoryID int not null,
    UserID       int not null,
    primary key (UserID, RepositoryID),
    constraint downvotersofrepository_ibfk_1
        foreign key (UserID) references users (ID),
    constraint downvotersofrepository_ibfk_2
        foreign key (RepositoryID) references repositories (ID)
);

create index RepositoryID
    on downvotersofrepository (RepositoryID);

create table followerofbugs
(
    BugID      int not null,
    FollowerID int not null,
    primary key (FollowerID, BugID),
    constraint followerofbugs_ibfk_1
        foreign key (FollowerID) references users (ID),
    constraint followerofbugs_ibfk_2
        foreign key (BugID) references bugs (ID)
);

create index BugID
    on followerofbugs (BugID);

create table followerofrepositories
(
    RepositoryID int not null,
    FollowerID   int not null,
    primary key (FollowerID, RepositoryID),
    constraint followerofrepositories_ibfk_1
        foreign key (FollowerID) references users (ID),
    constraint followerofrepositories_ibfk_2
        foreign key (RepositoryID) references repositories (ID)
);

create index RepositoryID
    on followerofrepositories (RepositoryID);

create table questions
(
    ID           int auto_increment,
    Question     varchar(200)         not null,
    Asked_by     int                  not null,
    Description  varchar(10000)       null,
    Upvotes      int        default 0 not null,
    Downvotes    int        default 0 not null,
    Open         tinyint(1) default 1 not null,
    Last_updated timestamp            not null,
    constraint questions_ID_uindex
        unique (ID),
    constraint questions_Question_uindex
        unique (Question),
    constraint Asked_by_foreign_key
        foreign key (Asked_by) references users (ID)
);

alter table questions
    add primary key (ID);

create table answers
(
    ID           int auto_increment,
    Answer       varchar(10000) not null,
    Answered_by  int            not null,
    Upvotes      int default 0  null,
    Downvotes    int default 0  null,
    Question_ID  int            not null,
    Last_updated timestamp      not null,
    constraint answers_ID_uindex
        unique (ID),
    constraint answer_to_question
        foreign key (Question_ID) references questions (ID),
    constraint answered_by_fk
        foreign key (Answered_by) references users (ID)
);

alter table answers
    add primary key (ID);

create table downvotersofanswers
(
    AnswerID int not null,
    UserID   int not null,
    primary key (UserID, AnswerID),
    constraint downvotersofanswers_ibfk_1
        foreign key (UserID) references users (ID),
    constraint downvotersofanswers_ibfk_2
        foreign key (AnswerID) references answers (ID)
);

create index AnswerID
    on downvotersofanswers (AnswerID);

create table downvotersofquestions
(
    QuestionID int not null,
    UserID     int not null,
    primary key (UserID, QuestionID),
    constraint downvotersofquestions_ibfk_1
        foreign key (UserID) references users (ID),
    constraint downvotersofquestions_ibfk_2
        foreign key (QuestionID) references questions (ID)
);

create index QuestionID
    on downvotersofquestions (QuestionID);

create table followerofanswer
(
    AnswerID   int not null,
    FollowerID int not null,
    primary key (FollowerID, AnswerID),
    constraint followerofanswer_ibfk_1
        foreign key (FollowerID) references users (ID),
    constraint followerofanswer_ibfk_2
        foreign key (AnswerID) references answers (ID)
);

create index AnswerID
    on followerofanswer (AnswerID);

create table followerofquestions
(
    QuestionID int not null,
    FollowerID int not null,
    primary key (FollowerID, QuestionID),
    constraint followerofquestions_ibfk_1
        foreign key (FollowerID) references users (ID),
    constraint followerofquestions_ibfk_2
        foreign key (QuestionID) references questions (ID)
);

create index QuestionID
    on followerofquestions (QuestionID);

create table tagsofanswers
(
    TagID    int not null,
    AnswerID int not null,
    primary key (TagID, AnswerID),
    constraint tagsofanswers_ibfk_1
        foreign key (AnswerID) references answers (ID),
    constraint tagsofanswers_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index AnswerID
    on tagsofanswers (AnswerID);

create table tagsofbugs
(
    TagID int not null,
    BugID int not null,
    primary key (TagID, BugID),
    constraint TagOfBug_bugs_bugID_fk
        foreign key (BugID) references bugs (ID),
    constraint TagOfBug_tag_tagID_fk
        foreign key (TagID) references tags (ID)
);

create table tagsofquestions
(
    TagID      int not null,
    QuestionID int not null,
    primary key (TagID, QuestionID),
    constraint tagsofquestions_ibfk_1
        foreign key (QuestionID) references questions (ID),
    constraint tagsofquestions_ibfk_2
        foreign key (TagID) references tags (ID)
);

create index QuestionID
    on tagsofquestions (QuestionID);

create table transaction
(
    ID            int auto_increment,
    Buyer_ID      int             not null,
    Seller_ID     int             not null,
    Price         float default 0 not null,
    Repository_ID int             not null,
    Date          timestamp       not null,
    constraint Transaction_ID_uindex
        unique (ID),
    constraint Transaction___fk_buyer
        foreign key (Buyer_ID) references users (ID),
    constraint Transaction___fk_repo
        foreign key (Repository_ID) references repositories (ID),
    constraint Transaction___fk_seller
        foreign key (Seller_ID) references users (ID)
);

alter table transaction
    add primary key (ID);

create table upvotersofanswers
(
    AnswerID int not null,
    UserID   int not null,
    primary key (UserID, AnswerID),
    constraint upvotersofanswers_ibfk_1
        foreign key (UserID) references users (ID),
    constraint upvotersofanswers_ibfk_2
        foreign key (AnswerID) references answers (ID)
);

create index AnswerID
    on upvotersofanswers (AnswerID);

create table upvotersofbugs
(
    BugID  int not null,
    UserID int not null,
    primary key (UserID, BugID),
    constraint upvotersofbugs_ibfk_1
        foreign key (UserID) references users (ID),
    constraint upvotersofbugs_ibfk_2
        foreign key (BugID) references bugs (ID)
);

create index BugID
    on upvotersofbugs (BugID);

create table upvotersofquestions
(
    QuestionID int not null,
    UserID     int not null,
    primary key (UserID, QuestionID),
    constraint upvotersofquestions_ibfk_1
        foreign key (UserID) references users (ID),
    constraint upvotersofquestions_ibfk_2
        foreign key (QuestionID) references questions (ID)
);

create index QuestionID
    on upvotersofquestions (QuestionID);

create table upvotersofrepository
(
    RepositoryID int not null,
    UserID       int not null,
    primary key (UserID, RepositoryID),
    constraint upvotersofrepository_ibfk_1
        foreign key (UserID) references users (ID),
    constraint upvotersofrepository_ibfk_2
        foreign key (RepositoryID) references repositories (ID)
);

create index RepositoryID
    on upvotersofrepository (RepositoryID);


