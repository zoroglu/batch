create table BATCHTEMP
(
    ID               NUMBER       not null
        constraint PK_BATCHTEMP
            primary key,
    CODE             VARCHAR2(50) not null,
    VERSIONID        NUMBER,
    COMPANYID        NUMBER,
    constraint UK_BATCHTEMP_CODE_COMPANYID
        unique (CODE, COMPANYID)
);




create sequence BATCHTEMP_S;


INSERT INTO BATCHTEMP (ID, CODE, VERSIONID, COMPANYID)
VALUES (1, 'CURRENCYRATE', 0, 1);

INSERT INTO BATCHTEMP (ID, CODE, VERSIONID, COMPANYID)
VALUES (2, 'CURRENCYRATE', 0, 2);



commit;

