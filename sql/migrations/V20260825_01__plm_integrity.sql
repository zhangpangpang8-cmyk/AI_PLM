-- PLM 业务表完整性约束与常用查询索引。
-- 执行前请先备份数据库；该脚本按迁移版本只应执行一次。

-- 业务编码/版本唯一性
ALTER TABLE pj_phase
    ADD CONSTRAINT uk_pj_phase_project_code UNIQUE (project_id, phase_code);

ALTER TABLE pro_material_classify
    ADD CONSTRAINT uk_pro_material_classify_code UNIQUE (material_classify_code);

ALTER TABLE pro_item_bom
    ADD CONSTRAINT uk_pro_item_bom_code_version UNIQUE (bom_code, bom_version);

ALTER TABLE dm_drawing
    ADD CONSTRAINT uk_dm_drawing_code_version UNIQUE (drawing_code, drawing_version);

ALTER TABLE dm_tech
    ADD CONSTRAINT uk_dm_tech_code_version UNIQUE (tech_code, tech_version);

-- 项目、文档和物料常用关联查询
CREATE INDEX idx_pj_phase_project_status ON pj_phase (project_id, phase_status, sort_order);
CREATE INDEX idx_pj_task_log_project_task ON pj_task_log (project_id, task_id, log_date);
CREATE INDEX idx_pro_item_bom_item_parent ON pro_item_bom (item_id, parent_node, hierarchy);
CREATE INDEX idx_dm_drawing_project ON dm_drawing (project_id, project_task_id);
CREATE INDEX idx_dm_tech_project ON dm_tech (project_id, project_task_id);
CREATE INDEX idx_wf_instance_business_status ON wf_process_instance (business_type, business_id, status);
CREATE INDEX idx_wf_task_instance_status ON wf_process_task (instance_id, task_status);
CREATE INDEX idx_wf_node_definition_name ON wf_process_node (process_definition_id, node_name);
CREATE INDEX idx_wf_edge_source_sort ON wf_process_edge (source_node_id, sort);

-- 工作流核心关系。定义删除时同步清理设计数据，运行实例仍采用限制删除。
ALTER TABLE wf_process_node
    ADD CONSTRAINT fk_wf_node_definition FOREIGN KEY (process_definition_id)
        REFERENCES wf_process_definition (id) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE wf_process_edge
    ADD CONSTRAINT fk_wf_edge_definition FOREIGN KEY (process_definition_id)
        REFERENCES wf_process_definition (id) ON UPDATE RESTRICT ON DELETE CASCADE,
    ADD CONSTRAINT fk_wf_edge_source FOREIGN KEY (source_node_id)
        REFERENCES wf_process_node (id) ON UPDATE RESTRICT ON DELETE CASCADE,
    ADD CONSTRAINT fk_wf_edge_target FOREIGN KEY (target_node_id)
        REFERENCES wf_process_node (id) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE wf_process_instance
    ADD CONSTRAINT fk_wf_instance_definition FOREIGN KEY (process_definition_id)
        REFERENCES wf_process_definition (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE wf_process_task
    ADD CONSTRAINT fk_wf_task_instance FOREIGN KEY (instance_id)
        REFERENCES wf_process_instance (id) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE pj_phase
    ADD CONSTRAINT fk_pj_phase_project FOREIGN KEY (project_id)
        REFERENCES pj_overview (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE pj_task_log
    ADD CONSTRAINT fk_pj_task_log_project FOREIGN KEY (project_id)
        REFERENCES pj_overview (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE pro_item_version
    ADD CONSTRAINT fk_pro_item_version_item FOREIGN KEY (item_id)
        REFERENCES pro_item (id) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE pro_item_bom
    ADD CONSTRAINT fk_pro_item_bom_item FOREIGN KEY (item_id)
        REFERENCES pro_item (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
