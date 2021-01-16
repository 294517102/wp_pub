create table bn_order_tbl (
  id                            integer auto_increment not null,
  title                         varchar(255),
  content                       varchar(255),
  name                          varchar(255),
  phone                         varchar(255),
  startime                      varchar(255),
  endtime                       varchar(255),
  roomnub                       varchar(255),
  arrival_time                  varchar(255),
  comment_time                  datetime(6),
  price                         varchar(255),
  day                           varchar(255),
  web_set_id                    integer,
  constraint pk_bn_order_tbl primary key (id)
);

create table cm_article_ext_tbl (
  id                            integer auto_increment not null,
  article_id                    integer,
  content                       varchar(255),
  insert_time                   datetime(6),
  constraint pk_cm_article_ext_tbl primary key (id)
);

create table cm_article_tbl (
  id                            integer auto_increment not null,
  author                        varchar(255),
  creator                       integer,
  describes                     varchar(255),
  insert_time                   datetime(6),
  issue_time                    datetime(6),
  keyword                       varchar(255),
  orderby                       integer,
  source                        varchar(255),
  title                         varchar(255),
  update_time                   datetime(6),
  url                           varchar(255),
  state                         integer,
  is_delete                     integer,
  view_times                    integer,
  web_set_id                    integer,
  thumbnail                     integer,
  column_id                     integer,
  constraint pk_cm_article_tbl primary key (id)
);

create table sm_article_accessory_tbl (
  article_id                    integer not null,
  accessory_id                  integer not null,
  constraint pk_sm_article_accessory_tbl primary key (article_id,accessory_id)
);

create table cm_code_tbl (
  id                            integer auto_increment not null,
  colname                       varchar(255),
  descname                      varchar(255),
  remark                        varchar(255),
  tablename                     varchar(255),
  value                         integer,
  constraint pk_cm_code_tbl primary key (id)
);

create table cm_column_tbl (
  id                            integer auto_increment not null,
  creator                       integer,
  datatime                      datetime(6),
  describes                     varchar(255),
  keyword                       varchar(255),
  model_id                      integer,
  icon                          integer,
  orderby                       integer,
  title                         varchar(255),
  type                          varchar(255),
  url                           varchar(255),
  state                         integer,
  is_delete                     integer,
  web_set_id                    integer,
  thumbnail                     integer,
  parent_id                     integer,
  constraint pk_cm_column_tbl primary key (id)
);

create table cm_comment_tbl (
  id                            integer auto_increment not null,
  comment_time                  datetime(6),
  content                       varchar(255),
  creator                       integer,
  name                          varchar(255),
  phone                         varchar(255),
  title                         varchar(255),
  email                         varchar(255),
  company                       varchar(255),
  address                       varchar(255),
  web_set_id                    integer,
  customer_info                 varchar(255),
  solve_time                    datetime(6),
  state                         integer,
  constraint pk_cm_comment_tbl primary key (id)
);

create table cm_home_slide_tbl (
  id                            integer auto_increment not null,
  accessory_id                  integer,
  creator                       integer,
  insert_time                   datetime(6),
  name                          varchar(255),
  orderby                       integer,
  remark                        varchar(255),
  remark_state                  integer,
  state                         default 0,
  url                           varchar(255),
  pause_time                    integer,
  delete_status                 integer,
  web_set_id                    integer,
  constraint pk_cm_home_slide_tbl primary key (id)
);

create table cm_web_set_tbl (
  id                            integer auto_increment not null,
  com_address                   varchar(255),
  com_email                     varchar(255),
  com_name                      varchar(255),
  copyright                     varchar(255),
  parent                        integer,
  creator                       integer,
  describes                     varchar(255),
  keyword                       varchar(255),
  linker                        varchar(255),
  logo_url                      varchar(255),
  record                        varchar(255),
  tel                           varchar(255),
  domain                        varchar(255),
  title                         varchar(255),
  title_additional              varchar(255),
  nav_icon                      varchar(255),
  state                         integer,
  language                      varchar(255),
  record_url                    varchar(255),
  insert_time                   datetime(6),
  template_id                   integer,
  constraint uq_cm_web_set_tbl_template_id unique (template_id),
  constraint pk_cm_web_set_tbl primary key (id)
);

create table sm_webset_role_tbl (
  web_set_id                    integer not null,
  role_id                       integer not null,
  constraint pk_sm_webset_role_tbl primary key (web_set_id,role_id)
);

create table sm_accessory_tbl (
  id                            integer auto_increment not null,
  insert_time                   datetime(6),
  creator                       integer,
  delete_status                 integer,
  ext                           varchar(255),
  height                        integer,
  info                          varchar(255),
  name                          varchar(255),
  path                          varchar(255),
  size                          integer,
  url                           varchar(255),
  original_url                  varchar(255),
  width                         integer,
  article_id                    integer,
  constraint pk_sm_accessory_tbl primary key (id)
);

create table sm_icon_tbl (
  id                            integer auto_increment not null,
  name                          varchar(255),
  describes                     varchar(255),
  url                           varchar(255),
  type                          integer,
  subscript                     varchar(255),
  constraint pk_sm_icon_tbl primary key (id)
);

create table sm_listener_browse_ext_tbl (
  id                            integer auto_increment not null,
  article_id                    integer,
  ip                            varchar(255),
  web_set_id                    integer,
  frequency                     integer,
  constraint pk_sm_listener_browse_ext_tbl primary key (id)
);

create table sm_listener_browse_tbl (
  id                            integer auto_increment not null,
  insert_time                   datetime(6),
  web_set_id                    integer,
  frequency                     integer,
  constraint pk_sm_listener_browse_tbl primary key (id)
);

create table sm_model_tbl (
  id                            integer auto_increment not null,
  code                          integer,
  creator                       integer,
  icon                          integer,
  ismenu                        integer,
  name                          varchar(255),
  sequence                      integer,
  parent_id                     integer,
  url                           varchar(255),
  constraint pk_sm_model_tbl primary key (id)
);

create table sm_res_tbl (
  id                            integer auto_increment not null,
  desc_                         varchar(255),
  insert_time                   datetime(6),
  name                          varchar(255),
  path                          varchar(255),
  url                           varchar(255),
  template_id                   integer,
  constraint pk_sm_res_tbl primary key (id)
);

create table sm_role_tbl (
  id                            integer auto_increment not null,
  creator                       integer,
  insert_time                   datetime(6),
  name                          varchar(255),
  keyword                       varchar(255),
  state                         integer,
  constraint pk_sm_role_tbl primary key (id)
);

create table sm_role_model_tbl (
  role_id                       integer not null,
  model_id                      integer not null,
  constraint pk_sm_role_model_tbl primary key (role_id,model_id)
);

create table sm_role_column_tbl (
  role_id                       integer not null,
  column_id                     integer not null,
  constraint pk_sm_role_column_tbl primary key (role_id,column_id)
);

create table sm_template_tbl (
  id                            integer auto_increment not null,
  insert_time                   datetime(6),
  name                          varchar(255),
  path                          varchar(255),
  web_set_id                    integer,
  constraint pk_sm_template_tbl primary key (id)
);

create table sm_user_tbl (
  id                            integer auto_increment not null,
  email                         varchar(255),
  is_locked                     integer,
  last_login                    datetime(6),
  password                      varchar(255),
  last_ip_address               varchar(255),
  phone                         varchar(255),
  dev_id                        varchar(255),
  thumbnail                     integer,
  is_delete                     integer,
  real_name                     varchar(255),
  token                         varchar(255),
  remark                        varchar(255),
  is_admin                      integer,
  web_set                       integer,
  parent_id                     integer,
  username                      varchar(255),
  constraint pk_sm_user_tbl primary key (id)
);

create table sm_user_role_tbl (
  user_id                       integer not null,
  role_id                       integer not null,
  constraint pk_sm_user_role_tbl primary key (user_id,role_id)
);

create table sm_version_tbl (
  id                            integer auto_increment not null,
  contents                      varchar(255),
  down_url                      varchar(255),
  major_update                  varchar(255),
  update_time                   datetime(6),
  version_id                    varchar(255),
  release_flag                  integer,
  real_version                  integer,
  type                          integer,
  constraint pk_sm_version_tbl primary key (id)
);

alter table bn_order_tbl add constraint fk_bn_order_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_bn_order_tbl_web_set_id on bn_order_tbl (web_set_id);

alter table cm_article_ext_tbl add constraint fk_cm_article_ext_tbl_article_id foreign key (article_id) references cm_article_tbl (id) on delete restrict on update restrict;
create index ix_cm_article_ext_tbl_article_id on cm_article_ext_tbl (article_id);

alter table cm_article_tbl add constraint fk_cm_article_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_cm_article_tbl_creator on cm_article_tbl (creator);

alter table cm_article_tbl add constraint fk_cm_article_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_cm_article_tbl_web_set_id on cm_article_tbl (web_set_id);

alter table cm_article_tbl add constraint fk_cm_article_tbl_thumbnail foreign key (thumbnail) references sm_accessory_tbl (id) on delete restrict on update restrict;
create index ix_cm_article_tbl_thumbnail on cm_article_tbl (thumbnail);

alter table cm_article_tbl add constraint fk_cm_article_tbl_column_id foreign key (column_id) references cm_column_tbl (id) on delete restrict on update restrict;
create index ix_cm_article_tbl_column_id on cm_article_tbl (column_id);

alter table sm_article_accessory_tbl add constraint fk_sm_article_accessory_tbl_cm_article_tbl foreign key (article_id) references cm_article_tbl (id) on delete restrict on update restrict;
create index ix_sm_article_accessory_tbl_cm_article_tbl on sm_article_accessory_tbl (article_id);

alter table sm_article_accessory_tbl add constraint fk_sm_article_accessory_tbl_sm_accessory_tbl foreign key (accessory_id) references sm_accessory_tbl (id) on delete restrict on update restrict;
create index ix_sm_article_accessory_tbl_sm_accessory_tbl on sm_article_accessory_tbl (accessory_id);

alter table cm_column_tbl add constraint fk_cm_column_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_cm_column_tbl_creator on cm_column_tbl (creator);

alter table cm_column_tbl add constraint fk_cm_column_tbl_model_id foreign key (model_id) references sm_model_tbl (id) on delete restrict on update restrict;
create index ix_cm_column_tbl_model_id on cm_column_tbl (model_id);

alter table cm_column_tbl add constraint fk_cm_column_tbl_icon foreign key (icon) references sm_icon_tbl (id) on delete restrict on update restrict;
create index ix_cm_column_tbl_icon on cm_column_tbl (icon);

alter table cm_column_tbl add constraint fk_cm_column_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_cm_column_tbl_web_set_id on cm_column_tbl (web_set_id);

alter table cm_column_tbl add constraint fk_cm_column_tbl_thumbnail foreign key (thumbnail) references sm_accessory_tbl (id) on delete restrict on update restrict;
create index ix_cm_column_tbl_thumbnail on cm_column_tbl (thumbnail);

alter table cm_column_tbl add constraint fk_cm_column_tbl_parent_id foreign key (parent_id) references cm_column_tbl (id) on delete restrict on update restrict;
create index ix_cm_column_tbl_parent_id on cm_column_tbl (parent_id);

alter table cm_comment_tbl add constraint fk_cm_comment_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_cm_comment_tbl_creator on cm_comment_tbl (creator);

alter table cm_comment_tbl add constraint fk_cm_comment_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_cm_comment_tbl_web_set_id on cm_comment_tbl (web_set_id);

alter table cm_home_slide_tbl add constraint fk_cm_home_slide_tbl_accessory_id foreign key (accessory_id) references sm_accessory_tbl (id) on delete restrict on update restrict;
create index ix_cm_home_slide_tbl_accessory_id on cm_home_slide_tbl (accessory_id);

alter table cm_home_slide_tbl add constraint fk_cm_home_slide_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_cm_home_slide_tbl_creator on cm_home_slide_tbl (creator);

alter table cm_home_slide_tbl add constraint fk_cm_home_slide_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_cm_home_slide_tbl_web_set_id on cm_home_slide_tbl (web_set_id);

alter table cm_web_set_tbl add constraint fk_cm_web_set_tbl_parent foreign key (parent) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_cm_web_set_tbl_parent on cm_web_set_tbl (parent);

alter table cm_web_set_tbl add constraint fk_cm_web_set_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_cm_web_set_tbl_creator on cm_web_set_tbl (creator);

alter table cm_web_set_tbl add constraint fk_cm_web_set_tbl_template_id foreign key (template_id) references sm_template_tbl (id) on delete restrict on update restrict;

alter table sm_webset_role_tbl add constraint fk_sm_webset_role_tbl_cm_web_set_tbl foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_sm_webset_role_tbl_cm_web_set_tbl on sm_webset_role_tbl (web_set_id);

alter table sm_webset_role_tbl add constraint fk_sm_webset_role_tbl_sm_role_tbl foreign key (role_id) references sm_role_tbl (id) on delete restrict on update restrict;
create index ix_sm_webset_role_tbl_sm_role_tbl on sm_webset_role_tbl (role_id);

alter table sm_accessory_tbl add constraint fk_sm_accessory_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_sm_accessory_tbl_creator on sm_accessory_tbl (creator);

alter table sm_accessory_tbl add constraint fk_sm_accessory_tbl_article_id foreign key (article_id) references cm_article_tbl (id) on delete restrict on update restrict;
create index ix_sm_accessory_tbl_article_id on sm_accessory_tbl (article_id);

alter table sm_listener_browse_ext_tbl add constraint fk_sm_listener_browse_ext_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_sm_listener_browse_ext_tbl_web_set_id on sm_listener_browse_ext_tbl (web_set_id);

alter table sm_listener_browse_tbl add constraint fk_sm_listener_browse_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_sm_listener_browse_tbl_web_set_id on sm_listener_browse_tbl (web_set_id);

alter table sm_model_tbl add constraint fk_sm_model_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_sm_model_tbl_creator on sm_model_tbl (creator);

alter table sm_model_tbl add constraint fk_sm_model_tbl_icon foreign key (icon) references sm_icon_tbl (id) on delete restrict on update restrict;
create index ix_sm_model_tbl_icon on sm_model_tbl (icon);

alter table sm_model_tbl add constraint fk_sm_model_tbl_parent_id foreign key (parent_id) references sm_model_tbl (id) on delete restrict on update restrict;
create index ix_sm_model_tbl_parent_id on sm_model_tbl (parent_id);

alter table sm_res_tbl add constraint fk_sm_res_tbl_template_id foreign key (template_id) references sm_template_tbl (id) on delete restrict on update restrict;
create index ix_sm_res_tbl_template_id on sm_res_tbl (template_id);

alter table sm_role_tbl add constraint fk_sm_role_tbl_creator foreign key (creator) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_sm_role_tbl_creator on sm_role_tbl (creator);

alter table sm_role_model_tbl add constraint fk_sm_role_model_tbl_sm_role_tbl foreign key (role_id) references sm_role_tbl (id) on delete restrict on update restrict;
create index ix_sm_role_model_tbl_sm_role_tbl on sm_role_model_tbl (role_id);

alter table sm_role_model_tbl add constraint fk_sm_role_model_tbl_sm_model_tbl foreign key (model_id) references sm_model_tbl (id) on delete restrict on update restrict;
create index ix_sm_role_model_tbl_sm_model_tbl on sm_role_model_tbl (model_id);

alter table sm_role_column_tbl add constraint fk_sm_role_column_tbl_sm_role_tbl foreign key (role_id) references sm_role_tbl (id) on delete restrict on update restrict;
create index ix_sm_role_column_tbl_sm_role_tbl on sm_role_column_tbl (role_id);

alter table sm_role_column_tbl add constraint fk_sm_role_column_tbl_cm_column_tbl foreign key (column_id) references cm_column_tbl (id) on delete restrict on update restrict;
create index ix_sm_role_column_tbl_cm_column_tbl on sm_role_column_tbl (column_id);

alter table sm_template_tbl add constraint fk_sm_template_tbl_web_set_id foreign key (web_set_id) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_sm_template_tbl_web_set_id on sm_template_tbl (web_set_id);

alter table sm_user_tbl add constraint fk_sm_user_tbl_thumbnail foreign key (thumbnail) references sm_accessory_tbl (id) on delete restrict on update restrict;
create index ix_sm_user_tbl_thumbnail on sm_user_tbl (thumbnail);

alter table sm_user_tbl add constraint fk_sm_user_tbl_web_set foreign key (web_set) references cm_web_set_tbl (id) on delete restrict on update restrict;
create index ix_sm_user_tbl_web_set on sm_user_tbl (web_set);

alter table sm_user_tbl add constraint fk_sm_user_tbl_parent_id foreign key (parent_id) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_sm_user_tbl_parent_id on sm_user_tbl (parent_id);

alter table sm_user_role_tbl add constraint fk_sm_user_role_tbl_sm_user_tbl foreign key (user_id) references sm_user_tbl (id) on delete restrict on update restrict;
create index ix_sm_user_role_tbl_sm_user_tbl on sm_user_role_tbl (user_id);

alter table sm_user_role_tbl add constraint fk_sm_user_role_tbl_sm_role_tbl foreign key (role_id) references sm_role_tbl (id) on delete restrict on update restrict;
create index ix_sm_user_role_tbl_sm_role_tbl on sm_user_role_tbl (role_id);

