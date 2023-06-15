create table resume
(
    uuid      char(36) not null
        constraint uuid
            primary key,
    full_name text
);
create table contact
(
    id          serial,
    resume_uuid char(36) not null
        references resume
            on delete cascade,
    type        text,
    value       text
);
create unique index contact_uuid_type_index
    on contact (resume_uuid, type);
create table section
(
    id            serial
        primary key,
    resume_uuid   char(36) not null
        constraint sections_resume_uuid_fk
            references resume
            on delete cascade,
    section_type  text     not null,
    section_value text     not null
);
create unique index sections_uuid_type_index
    on section (resume_uuid, section_type);