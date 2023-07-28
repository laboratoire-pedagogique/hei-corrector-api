create table if not exists "test" (
    id  varchar constraint test_pk primary key default uuid_generate_v4(),
    kata varchar,
    total_points integer,
    passed integer,
    failed integer,
    test_result_id varchar references "result" (id)
);