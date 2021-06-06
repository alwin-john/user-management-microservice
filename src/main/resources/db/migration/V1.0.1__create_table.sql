create table account.user_info(
	user_id bigint not null,
	user_name varchar(50) not null,
	password varchar(200) not null,
	mobile_no varchar(50) not null,
	user_role varchar(50) not null,
	account_id bigint,
	last_login timestamp with time zone,
	created_date timestamp with time zone
);