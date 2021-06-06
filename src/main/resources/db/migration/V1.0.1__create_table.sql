create table account.user_info(
	user_id bigint not null,
	user_name varchar(50) not null,
	password varchar(50) not null,
	mobile_no varchar(50) not null,
	role_id bigint not null,
	account_id bigint,
	last_login timestamp with time zone,
	created_date timestamp with time zone
);

create table account.user_role(
	role_id bigint not null,
	role_name varchar(50) not null,
	role_desc varchar(50) not null,
	created_date timestamp with time zone
);