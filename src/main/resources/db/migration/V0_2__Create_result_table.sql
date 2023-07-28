create table if not exists "result" (
     id  varchar constraint result_pk primary key default uuid_generate_v4(),
     student varchar,
     session_id varchar references "session" (id)
);