drop table IF EXISTS quartz_task_info;

create table quartz_task_info (
    `ID` int not null ,
    `JOB_NAME` VARCHAR(200) NOT NULL,
    `JOB_GROUP` VARCHAR(200) NOT NULL,
    `JOB_DESCRIPTION` VARCHAR(250) NULL,
    `JOB_CLASS_NAME` VARCHAR(250) NOT NULL,
    `JOB_STATUS` VARCHAR(250) NOT NULL,
    `CRON_EXPRESSION` VARCHAR(250) NOT NULL,
    `CREATE_TIME` timestamp not null,
    primary key (ID)
);