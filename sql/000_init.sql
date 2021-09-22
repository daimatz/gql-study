create table todo (
  `id` bigint not null auto_increment,
  `user_id` bigint not null,
  `json` longtext not null,
  primary key (`id`)
) engine = InnoDB, character set = `utf8mb4`, collate = `utf8mb4_bin`;

create table user (
  `id` bigint not null auto_increment,
  `json` longtext not null,
  primary key (`id`)
) engine = InnoDB, character set = `utf8mb4`, collate = `utf8mb4_bin`;
