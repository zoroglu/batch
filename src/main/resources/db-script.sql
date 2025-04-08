create table BATCH.BATCH
(
    ID               NUMBER       not null
        constraint PK_BATCH
            primary key,
    CODE             VARCHAR2(50) not null,
    APIINVENTORYCODE VARCHAR2(50) not null,
    CREATEDATE       DATE,
    CREATEUSER       VARCHAR2(50),
    UPDATEUSER       VARCHAR2(50),
    UPDATEDATE       DATE,
    VERSIONID        NUMBER,
    COMPANYID        NUMBER,
    constraint UK_BATCH_CODE_COMPANYID
        unique (CODE, COMPANYID)
)
/




create sequence BATCH.BATCH_S
    /


