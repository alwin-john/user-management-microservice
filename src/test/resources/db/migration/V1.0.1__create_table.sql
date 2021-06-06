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

INSERT INTO account.user_info (user_id, user_name, "password", mobile_no, user_role, account_id, last_login, created_date)
VALUES(200, 'test', '$2a$10$WyzlmFV3bLEBK0ME1vKV9.5swlmxXCTj.Ji0rc36sAxwtwVnLDpPe', '123456', 'ACCOUNT', 0, NULL, '2020-12-23 19:49:36.546');

INSERT INTO account.user_info (user_id, user_name, "password", mobile_no, user_role, account_id, last_login, created_date)
VALUES(250, 'testUser', '$2a$10$pNHpPLe1JTOm9xDJUJy3VeyW93SnkfM.Q3SL0XTjpwD77zkILPvVi', '123456', 'USER', 200, NULL, '2020-12-23 23:52:59.089');

INSERT INTO account.user_info (user_id, user_name, "password", mobile_no, user_role, account_id, last_login, created_date)
VALUES(300, 'testUser1', '$2a$10$pNHpPLe1JTOm9xDJUJy3VeyW93SnkfM.Q3SL0XTjpwD77zkILPvVi', '123456', 'USER', 200, NULL, '2020-12-23 23:52:59.089');

