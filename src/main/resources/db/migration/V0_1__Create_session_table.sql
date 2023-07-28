create extension if not exists "uuid-ossp";

create table if not exists "session" (
    id  varchar constraint session_pk primary key default uuid_generate_v4(),
    name varchar,
    associated_course varchar,
    date timestamp without time zone default now(),
    type varchar,
    source varchar
);