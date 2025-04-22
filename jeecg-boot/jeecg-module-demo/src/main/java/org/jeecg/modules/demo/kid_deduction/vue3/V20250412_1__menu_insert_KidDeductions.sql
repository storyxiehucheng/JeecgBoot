-- 注意：该页面对应的前台目录为views/kid_deduction文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2025041205031980360', NULL, '儿童习惯扣分模板表', '/kid_deduction/kidDeductionsList', 'kid_deduction/KidDeductionsList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025041205031990361', '2025041205031980360', '添加儿童习惯扣分模板表', NULL, NULL, 0, NULL, NULL, 2, 'kid_deduction:kid_deductions:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025041205031990362', '2025041205031980360', '编辑儿童习惯扣分模板表', NULL, NULL, 0, NULL, NULL, 2, 'kid_deduction:kid_deductions:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025041205031990363', '2025041205031980360', '删除儿童习惯扣分模板表', NULL, NULL, 0, NULL, NULL, 2, 'kid_deduction:kid_deductions:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025041205031990364', '2025041205031980360', '批量删除儿童习惯扣分模板表', NULL, NULL, 0, NULL, NULL, 2, 'kid_deduction:kid_deductions:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025041205031990365', '2025041205031980360', '导出excel_儿童习惯扣分模板表', NULL, NULL, 0, NULL, NULL, 2, 'kid_deduction:kid_deductions:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025041205031990366', '2025041205031980360', '导入excel_儿童习惯扣分模板表', NULL, NULL, 0, NULL, NULL, 2, 'kid_deduction:kid_deductions:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-12 17:03:36', NULL, NULL, 0, 0, '1', 0);