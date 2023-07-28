alter table "result" add column score integer;
create index if not exists result_student_index on "result" (student);