-- ----------------------------
-- 物料单位字典初始化
-- ----------------------------

-- 插入字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('物料单位', 'sys_item_unit', '0', 'admin', NOW(), '物料管理中的单位字典');

-- 插入字典数据（单位列表）
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '个', '个', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '通用单位'),
(2, '件', '件', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '通用单位'),
(3, '套', '套', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '成套单位'),
(4, '台', '台', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '设备单位'),
(5, '片', '片', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '板材单位'),
(6, '幅', '幅', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '图纸/布料单位'),
(7, '把', '把', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '工具单位'),
(8, 'pcs', 'pcs', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '英文单位-个'),
(9, '千克', '千克', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '重量单位'),
(10, '米', '米', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '长度单位'),
(11, '厘米', '厘米', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '长度单位'),
(12, '毫米', '毫米', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '长度单位'),
(13, '升', '升', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '体积单位'),
(14, '毫升', '毫升', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '体积单位'),
(15, '箱', '箱', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '包装单位'),
(16, '包', '包', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '包装单位'),
(17, '瓶', '瓶', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '容器单位'),
(18, '罐', '罐', 'sys_item_unit', '', 'default', 'N', '0', 'admin', NOW(), '容器单位');
