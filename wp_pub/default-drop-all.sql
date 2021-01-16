alter table bn_order_tbl drop foreign key fk_bn_order_tbl_web_set_id;
drop index ix_bn_order_tbl_web_set_id on bn_order_tbl;

alter table cm_article_ext_tbl drop foreign key fk_cm_article_ext_tbl_article_id;
drop index ix_cm_article_ext_tbl_article_id on cm_article_ext_tbl;

alter table cm_article_tbl drop foreign key fk_cm_article_tbl_creator;
drop index ix_cm_article_tbl_creator on cm_article_tbl;

alter table cm_article_tbl drop foreign key fk_cm_article_tbl_web_set_id;
drop index ix_cm_article_tbl_web_set_id on cm_article_tbl;

alter table cm_article_tbl drop foreign key fk_cm_article_tbl_thumbnail;
drop index ix_cm_article_tbl_thumbnail on cm_article_tbl;

alter table cm_article_tbl drop foreign key fk_cm_article_tbl_column_id;
drop index ix_cm_article_tbl_column_id on cm_article_tbl;

alter table sm_article_accessory_tbl drop foreign key fk_sm_article_accessory_tbl_cm_article_tbl;
drop index ix_sm_article_accessory_tbl_cm_article_tbl on sm_article_accessory_tbl;

alter table sm_article_accessory_tbl drop foreign key fk_sm_article_accessory_tbl_sm_accessory_tbl;
drop index ix_sm_article_accessory_tbl_sm_accessory_tbl on sm_article_accessory_tbl;

alter table cm_column_tbl drop foreign key fk_cm_column_tbl_creator;
drop index ix_cm_column_tbl_creator on cm_column_tbl;

alter table cm_column_tbl drop foreign key fk_cm_column_tbl_model_id;
drop index ix_cm_column_tbl_model_id on cm_column_tbl;

alter table cm_column_tbl drop foreign key fk_cm_column_tbl_icon;
drop index ix_cm_column_tbl_icon on cm_column_tbl;

alter table cm_column_tbl drop foreign key fk_cm_column_tbl_web_set_id;
drop index ix_cm_column_tbl_web_set_id on cm_column_tbl;

alter table cm_column_tbl drop foreign key fk_cm_column_tbl_thumbnail;
drop index ix_cm_column_tbl_thumbnail on cm_column_tbl;

alter table cm_column_tbl drop foreign key fk_cm_column_tbl_parent_id;
drop index ix_cm_column_tbl_parent_id on cm_column_tbl;

alter table cm_comment_tbl drop foreign key fk_cm_comment_tbl_creator;
drop index ix_cm_comment_tbl_creator on cm_comment_tbl;

alter table cm_comment_tbl drop foreign key fk_cm_comment_tbl_web_set_id;
drop index ix_cm_comment_tbl_web_set_id on cm_comment_tbl;

alter table cm_home_slide_tbl drop foreign key fk_cm_home_slide_tbl_accessory_id;
drop index ix_cm_home_slide_tbl_accessory_id on cm_home_slide_tbl;

alter table cm_home_slide_tbl drop foreign key fk_cm_home_slide_tbl_creator;
drop index ix_cm_home_slide_tbl_creator on cm_home_slide_tbl;

alter table cm_home_slide_tbl drop foreign key fk_cm_home_slide_tbl_web_set_id;
drop index ix_cm_home_slide_tbl_web_set_id on cm_home_slide_tbl;

alter table cm_web_set_tbl drop foreign key fk_cm_web_set_tbl_parent;
drop index ix_cm_web_set_tbl_parent on cm_web_set_tbl;

alter table cm_web_set_tbl drop foreign key fk_cm_web_set_tbl_creator;
drop index ix_cm_web_set_tbl_creator on cm_web_set_tbl;

alter table cm_web_set_tbl drop foreign key fk_cm_web_set_tbl_template_id;

alter table sm_webset_role_tbl drop foreign key fk_sm_webset_role_tbl_cm_web_set_tbl;
drop index ix_sm_webset_role_tbl_cm_web_set_tbl on sm_webset_role_tbl;

alter table sm_webset_role_tbl drop foreign key fk_sm_webset_role_tbl_sm_role_tbl;
drop index ix_sm_webset_role_tbl_sm_role_tbl on sm_webset_role_tbl;

alter table sm_accessory_tbl drop foreign key fk_sm_accessory_tbl_creator;
drop index ix_sm_accessory_tbl_creator on sm_accessory_tbl;

alter table sm_accessory_tbl drop foreign key fk_sm_accessory_tbl_article_id;
drop index ix_sm_accessory_tbl_article_id on sm_accessory_tbl;

alter table sm_listener_browse_ext_tbl drop foreign key fk_sm_listener_browse_ext_tbl_web_set_id;
drop index ix_sm_listener_browse_ext_tbl_web_set_id on sm_listener_browse_ext_tbl;

alter table sm_listener_browse_tbl drop foreign key fk_sm_listener_browse_tbl_web_set_id;
drop index ix_sm_listener_browse_tbl_web_set_id on sm_listener_browse_tbl;

alter table sm_model_tbl drop foreign key fk_sm_model_tbl_creator;
drop index ix_sm_model_tbl_creator on sm_model_tbl;

alter table sm_model_tbl drop foreign key fk_sm_model_tbl_icon;
drop index ix_sm_model_tbl_icon on sm_model_tbl;

alter table sm_model_tbl drop foreign key fk_sm_model_tbl_parent_id;
drop index ix_sm_model_tbl_parent_id on sm_model_tbl;

alter table sm_res_tbl drop foreign key fk_sm_res_tbl_template_id;
drop index ix_sm_res_tbl_template_id on sm_res_tbl;

alter table sm_role_tbl drop foreign key fk_sm_role_tbl_creator;
drop index ix_sm_role_tbl_creator on sm_role_tbl;

alter table sm_role_model_tbl drop foreign key fk_sm_role_model_tbl_sm_role_tbl;
drop index ix_sm_role_model_tbl_sm_role_tbl on sm_role_model_tbl;

alter table sm_role_model_tbl drop foreign key fk_sm_role_model_tbl_sm_model_tbl;
drop index ix_sm_role_model_tbl_sm_model_tbl on sm_role_model_tbl;

alter table sm_role_column_tbl drop foreign key fk_sm_role_column_tbl_sm_role_tbl;
drop index ix_sm_role_column_tbl_sm_role_tbl on sm_role_column_tbl;

alter table sm_role_column_tbl drop foreign key fk_sm_role_column_tbl_cm_column_tbl;
drop index ix_sm_role_column_tbl_cm_column_tbl on sm_role_column_tbl;

alter table sm_template_tbl drop foreign key fk_sm_template_tbl_web_set_id;
drop index ix_sm_template_tbl_web_set_id on sm_template_tbl;

alter table sm_user_tbl drop foreign key fk_sm_user_tbl_thumbnail;
drop index ix_sm_user_tbl_thumbnail on sm_user_tbl;

alter table sm_user_tbl drop foreign key fk_sm_user_tbl_web_set;
drop index ix_sm_user_tbl_web_set on sm_user_tbl;

alter table sm_user_tbl drop foreign key fk_sm_user_tbl_parent_id;
drop index ix_sm_user_tbl_parent_id on sm_user_tbl;

alter table sm_user_role_tbl drop foreign key fk_sm_user_role_tbl_sm_user_tbl;
drop index ix_sm_user_role_tbl_sm_user_tbl on sm_user_role_tbl;

alter table sm_user_role_tbl drop foreign key fk_sm_user_role_tbl_sm_role_tbl;
drop index ix_sm_user_role_tbl_sm_role_tbl on sm_user_role_tbl;

drop table if exists bn_order_tbl;

drop table if exists cm_article_ext_tbl;

drop table if exists cm_article_tbl;

drop table if exists sm_article_accessory_tbl;

drop table if exists cm_code_tbl;

drop table if exists cm_column_tbl;

drop table if exists cm_comment_tbl;

drop table if exists cm_home_slide_tbl;

drop table if exists cm_web_set_tbl;

drop table if exists sm_webset_role_tbl;

drop table if exists sm_accessory_tbl;

drop table if exists sm_icon_tbl;

drop table if exists sm_listener_browse_ext_tbl;

drop table if exists sm_listener_browse_tbl;

drop table if exists sm_model_tbl;

drop table if exists sm_res_tbl;

drop table if exists sm_role_tbl;

drop table if exists sm_role_model_tbl;

drop table if exists sm_role_column_tbl;

drop table if exists sm_template_tbl;

drop table if exists sm_user_tbl;

drop table if exists sm_user_role_tbl;

drop table if exists sm_version_tbl;

