-- ----------------------------
-- 物料版本历史表
-- ----------------------------
drop table if exists pro_item_version;
create table pro_item_version (
  id                    bigint(20)      not null auto_increment    comment '主键ID',
  item_id               bigint(20)      not null                   comment '关联物料ID',
  item_code             varchar(64)     not null                   comment '物料编码',
  item_name             varchar(255)    default ''                 comment '物料名称',
  item_type_id          bigint(20)      default null               comment '物料类型ID',
  item_type_code        varchar(64)     default ''                 comment '物料类型编码',
  item_type_name        varchar(255)    default ''                 comment '物料类型名称',
  item_version          varchar(32)     default ''                 comment '物料版本号',
  material_classify_ids varchar(255)    default ''                 comment '物料分类ids',
  material_classify_name varchar(255)   default ''                 comment '物料分类名称',
  specification         varchar(512)    default ''                 comment '规格型号',
  unit_id               bigint(20)      default null               comment '单位ID',
  unit_name             varchar(64)     default ''                 comment '单位名称',
  status                varchar(8)      default '0'                comment '状态',
  enable                varchar(8)      default '1'                comment '是否启用',
  vendor_id             bigint(20)      default null               comment '供应商ID',
  vendor_name           varchar(255)    default ''                 comment '供应商名称',
  material              varchar(255)    default ''                 comment '材质',
  color                 varchar(64)     default ''                 comment '颜色',
  weight                decimal(10,2)   default null               comment '重量',
  Info                  text                                       comment '描述',
  parameters_values     text                                       comment '技术参数数组',
  url                   varchar(512)    default ''                 comment '图片URL',
  publish_status        varchar(32)     default ''                 comment '发布状态',
  remake                varchar(512)    default ''                 comment '备注',
  create_by             varchar(64)     default ''                 comment '创建者',
  create_time           datetime                                   comment '创建时间',
  update_by             varchar(64)     default ''                 comment '更新者',
  update_time           datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=100 comment = '物料版本历史表';
