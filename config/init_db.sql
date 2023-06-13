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
create table section
(
    id            integer default nextval('sections_id_seq'::regclass) not null,
    resume_uuid   char(36)                                             not null
        constraint sections_resume_uuid_fk
            references resume
            on delete cascade,
    section_type  text                                                 not null,
    section_value text                                                 not null
);