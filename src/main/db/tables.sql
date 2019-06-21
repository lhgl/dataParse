
create table captura.video
(
	id int auto_increment,
	title_id varchar(200) not null,
	data_criacao datetime default now(6) not null,
	constraint video_pk
		primary key (id)
);

create unique index video_id_uindex
	on captura.video (id);

create index video_title_id_index
	on captura.video (title_id);

create table captura.video_info
(
	id int auto_increment,
	info_key varchar(200) not null,
	value varchar(200) null,
	video_id int not null,
	constraint video_info_pk
		primary key (id),
	constraint video_info_video__fk
		foreign key (video_id) references captura.video (id)
);

create unique index captura_video_id_uindex
	on captura.video_info (id);

create index video_infos_key_index
	on captura.video_info (info_key);

create table captura.video_sub
(
	id int auto_increment,
	video_id int not null,
	start_time TIME(6) not null,
	end_time TIME(6) not null,
	sub varchar(200) null,
	constraint video_sub_pk
		primary key (id),
	constraint video_sub_video_id_fk
		foreign key (video_id) references captura.video (id)
);

create unique index video_sub_id_index
	on captura.video_sub (id);

create index video_sub_start_time_end_time_index
	on captura.video_sub (start_time, end_time);

create index video_sub_video_id_index
	on captura.video_sub (video_id);

create index video_sub_sub_index
	on captura.video_sub (sub);


create table captura.video_action_units(
	id int auto_increment,
	video_id int not null,
	frame float not null,
	face_id float not null,
	capture_time TIME(6) not null,
	confidence float not null,
	success float not null,
	AU01_r float not null,
	AU02_r float not null,
	AU04_r float not null,
	AU05_r float not null,
	AU06_r float not null,
	AU07_r float not null,
	AU09_r float not null,
	AU10_r float not null,
	AU12_r float not null,
	AU14_r float not null,
	AU15_r float not null,
	AU17_r float not null,
	AU20_r float not null,
	AU23_r float not null,
	AU25_r float not null,
	AU26_r float not null,
	AU45_r float not null,
	AU01_c float not null,
	AU02_c float not null,
	AU04_c float not null,
	AU05_c float not null,
	AU06_c float not null,
	AU07_c float not null,
	AU09_c float not null,
	AU10_c float not null,
	AU12_c float not null,
	AU14_c float not null,
	AU15_c float not null,
	AU17_c float not null,
	AU20_c float not null,
	AU23_c float not null,
	AU25_c float not null,
	AU26_c float not null,
	AU28_c float not null,
	AU45_c float not null,
	constraint video_au_pk
		primary key (id),
	constraint video_au_video__fk
		foreign key (video_id) references captura.video (id)
);

create unique index captura_video_au_id_uindex
	on captura.video_action_units (id);

create index video_au_sub_capture_time_index
	on captura.video_action_units (capture_time);
	
create table captura.video_sub_tag
(
	id int auto_increment,
	tag varchar(20) not null,
	constraint tag_pk
		primary key (id)
);

create unique index video_sub_tag_tag_uindex
	on captura.video_sub_tag (tag);

create table captura.video_sub_token_tag
(
	id int auto_increment,
	video_sub_id int not null,
	token varchar(200) null,
	tag varchar(20) not null,
	constraint video_sub_token_tag_pk
		primary key (id),
	constraint video_sub_token_tag_video_sub_id_fk
		foreign key (video_sub_id) references captura.video_sub (id),
	constraint video_sub_token_tag_video_sub_tag_tag_fk
		foreign key (tag) references captura.video_sub_tag (tag)
);

create index video_sub_token_tag_tag_index
	on captura.video_sub_token_tag (tag);

create index video_sub_token_tag_token_index
	on captura.video_sub_token_tag (token);

create index video_sub_token_tag_video_sub_id_index
	on captura.video_sub_token_tag (video_sub_id);

