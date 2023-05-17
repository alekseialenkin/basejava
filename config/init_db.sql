create table contact
(
    id          int,
    type        TEXT     not null,
    value       TEXT     not null,
    resume_uuid CHAR(36) not null
        constraint contact_resume_uuid_fk
            references resume (uuid)
            on delete cascade
);
create table resume
(
    uuid      char(36) not null
        constraint uuid
            primary key,
    full_name text
);