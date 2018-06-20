create schema public
;

comment on schema public is 'standard public schema'
;

create table app_users
(
	id serial not null
		constraint app_users_pkey
			primary key,
	name varchar(255) not null
)
;

create unique index app_users_name_uindex
	on app_users (name)
;

create table user_logs
(
	id serial not null
		constraint user_logs_pkey
			primary key,
	user_id integer not null
		constraint user_logs_app_users_id_fk
			references app_users
				on update cascade on delete cascade,
	user_agent varchar(255) not null,
	ip varchar(32) not null,
	logged_at timestamp default now() not null
)
;

