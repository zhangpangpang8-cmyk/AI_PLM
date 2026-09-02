<template>
  <div class="bom-container">
    <div class="bom-header">
      <div class="breadcrumb">
        <i class="el-icon-menu"></i>
        <span class="breadcrumb-item">首页</span>
        <span class="breadcrumb-separator">/</span>
        <span class="breadcrumb-item">产品管理</span>
        <span class="breadcrumb-separator">/</span>
        <span class="breadcrumb-item active">BOM管理</span>
      </div>
      <h1 class="page-title">BOM管理</h1>
      <div class="header-actions">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新建BOM</el-button>
        <el-button type="primary" icon="el-icon-upload2" @click="handleImport">导入BOM</el-button>
        <el-button type="primary" icon="el-icon-search" @click="showAdvancedFilter = true">高级筛选</el-button>
        <el-button type="warning" icon="el-icon-lock" :disabled="!hasSelection" @click="handleDisable">禁用BOM</el-button>
        <el-button type="danger" icon="el-icon-delete" :disabled="!hasSelection" @click="handleDelete">删除BOM</el-button>
      </div>
    </div>

    <div class="bom-content">
      <div class="bom-sidebar">
        <div class="sidebar-header">
          <span class="sidebar-title">BOM目录</span>
          <el-button type="text" icon="el-icon-sort" @click="handleSort">排序</el-button>
        </div>

        <div class="sidebar-filters">
          <el-input
            v-model="searchQuery"
            placeholder="搜索BOM编号/名称"
            prefix-icon="el-icon-search"
            clearable
            @input="handleSearch"
          ></el-input>

          <el-select v-model="filterCategory" placeholder="产品分类" clearable style="width: 100%; margin-top: 10px;">
            <el-option
              v-for="item in categoryOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>

          <el-select v-model="filterStatus" placeholder="状态" clearable style="width: 100%; margin-top: 10px;">
            <el-option
              v-for="option in enableStatusOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            />
          </el-select>
        </div>

        <div class="bom-tree" v-loading="treeLoading">
          <el-tree
            v-if="bomTreeData.length > 0"
            :data="bomTreeData"
            :props="treeProps"
            node-key="id"
            :default-expand-all="false"
            :expand-on-click-node="false"
            highlight-current
            @node-click="handleNodeClick"
          >
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span class="tree-node-label">
                <i :class="getNodeIcon(data)"></i>
                {{ node.label }}
              </span>
              <span class="tree-node-code">{{ data.bomCode }}</span>
            </span>
          </el-tree>

          <div v-else class="empty-tree">
            <i class="el-icon-warning-outline"></i>
            <p>未找到匹配的BOM</p>
          </div>
        </div>
      </div>

      <div class="bom-main">
        <div v-if="!selectedBom" class="empty-state">
          <i class="el-icon-document"></i>
          <h3>欢迎使用BOM管理系统</h3>
          <p>请从左侧选择一个BOM进行查看或编辑，或者创建新的BOM</p>
          <div class="empty-actions">
            <el-button type="primary" @click="handleAdd">新建BOM</el-button>
            <el-button @click="handleImport">导入BOM</el-button>
          </div>
        </div>

        <div v-else class="bom-detail">
          <el-card>
            <div slot="header" class="card-header">
              <span class="card-title">{{ selectedBom.bomName }}</span>
              <business-status-tag group="enabledDisabled" :value="selectedBom.enable" />
            </div>

            <el-descriptions :column="2" border>
              <el-descriptions-item label="BOM编码">{{ selectedBom.bomCode }}</el-descriptions-item>
              <el-descriptions-item label="BOM版本">{{ selectedBom.bomVersion }}</el-descriptions-item>
              <el-descriptions-item label="物料编码">{{ selectedBom.itemCode }}</el-descriptions-item>
              <el-descriptions-item label="物料名称">{{ selectedBom.itemName }}</el-descriptions-item>
              <el-descriptions-item label="产品类型">{{ selectedBom.productName }}</el-descriptions-item>
              <el-descriptions-item label="层级">{{ selectedBom.hierarchy }}</el-descriptions-item>
              <el-descriptions-item label="用量">{{ selectedBom.dosage }}</el-descriptions-item>
              <el-descriptions-item label="创建时间">{{ selectedBom.createTime }}</el-descriptions-item>
              <el-descriptions-item label="版本简述" :span="2">{{ selectedBom.versionBriefly }}</el-descriptions-item>
              <el-descriptions-item label="备注" :span="2">{{ selectedBom.remark }}</el-descriptions-item>
            </el-descriptions>

            <div class="detail-actions">
              <el-button type="primary" icon="el-icon-edit" @click="handleUpdate(selectedBom)">编辑</el-button>
              <el-button type="success" icon="el-icon-copy-document" @click="handleCopy(selectedBom)">复制</el-button>
              <el-button type="warning" icon="el-icon-download" @click="handleExport(selectedBom)">导出</el-button>
            </div>
          </el-card>

          <el-card style="margin-top: 20px;">
            <div slot="header">
              <span>BOM结构</span>
            </div>
            <el-table :data="bomStructure" border stripe>
              <el-table-column prop="itemCode" label="物料编码" width="150"></el-table-column>
              <el-table-column prop="itemName" label="物料名称" width="200"></el-table-column>
              <el-table-column prop="hierarchy" label="层级" width="80"></el-table-column>
              <el-table-column prop="dosage" label="用量" width="80"></el-table-column>
              <el-table-column prop="enable" label="状态" width="100">
                <template slot-scope="scope">
                  <business-status-tag group="enabledDisabled" :value="scope.row.enable" size="small" />
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>
    </div>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="BOM编码" prop="bomCode">
          <el-input v-model="form.bomCode" placeholder="请输入BOM编码" />
        </el-form-item>
        <el-form-item label="BOM名称" prop="bomName">
          <el-input v-model="form.bomName" placeholder="请输入BOM名称" />
        </el-form-item>
        <el-form-item label="BOM版本" prop="bomVersion">
          <el-input v-model="form.bomVersion" placeholder="请输入BOM版本" />
        </el-form-item>
        <el-form-item label="物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="产品类型" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品类型" />
        </el-form-item>
        <el-form-item label="层级" prop="hierarchy">
          <el-input v-model="form.hierarchy" placeholder="请输入层级" />
        </el-form-item>
        <el-form-item label="用量" prop="dosage">
          <el-input v-model="form.dosage" placeholder="请输入用量" />
        </el-form-item>
        <el-form-item label="版本简述" prop="versionBriefly">
          <el-input v-model="form.versionBriefly" type="textarea" :rows="3" placeholder="请输入版本简述" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="启用状态" prop="enable">
          <el-radio-group v-model="form.enable">
            <el-radio v-for="option in enableStatusOptions" :key="option.value" :label="option.value">
              {{ option.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="高级筛选" :visible.sync="showAdvancedFilter" width="500px" append-to-body>
      <el-form label-width="100px">
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="advancedFilter.createTimeRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="更新时间">
          <el-date-picker
            v-model="advancedFilter.updateTimeRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="是否最新版本">
          <el-select v-model="advancedFilter.latestVersion" placeholder="请选择" clearable style="width: 100%;">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAdvancedFilter = false">取 消</el-button>
        <el-button type="primary" @click="applyAdvancedFilter">应 用</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBom, getBom, addBom, updateBom } from "@/api/system/bom"
import { ENABLE_STATUS_OPTIONS, buildBomTree, createBomForm } from '@/utils/material'

export default {
  name: "Bom",
  data() {
    return {
      searchQuery: '',
      filterCategory: '',
      filterStatus: '',
      enableStatusOptions: ENABLE_STATUS_OPTIONS,
      categoryOptions: [
        { value: 'product1', label: '产品类型1' },
        { value: 'product2', label: '产品类型2' },
        { value: 'product3', label: '产品类型3' }
      ],
      treeLoading: false,
      bomTreeData: [],
      treeProps: {
        children: 'children',
        label: 'bomName'
      },
      selectedBom: null,
      hasSelection: false,
      showAdvancedFilter: false,
      advancedFilter: {
        createTimeRange: [],
        updateTimeRange: [],
        latestVersion: ''
      },
      dialogVisible: false,
      dialogTitle: '',
      form: createBomForm(),
      bomStructure: [],
      rules: {
        bomCode: [
          { required: true, message: "BOM编码不能为空", trigger: "blur" }
        ],
        bomName: [
          { required: true, message: "BOM名称不能为空", trigger: "blur" }
        ],
        bomVersion: [
          { required: true, message: "BOM版本不能为空", trigger: "blur" }
        ],
        itemCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        itemName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getBomTree()
  },
  methods: {
    getBomTree() {
      this.treeLoading = true
      const params = {
        searchQuery: this.searchQuery,
        category: this.filterCategory,
        status: this.filterStatus
      }
      listBom(params).then(response => {
        this.bomTreeData = buildBomTree(response.rows)
        this.treeLoading = false
      }).catch(() => {
        this.treeLoading = false
      })
    },
    handleSearch() {
      this.getBomTree()
    },
    handleNodeClick(data) {
      this.selectedBom = data
      this.loadBomStructure(data)
    },
    loadBomStructure(bom) {
      listBom({ parentNode: bom.bomCode }).then(response => {
        this.bomStructure = response.rows
      })
    },
    getNodeIcon(data) {
      if (data.children && data.children.length > 0) {
        return 'el-icon-folder'
      }
      return 'el-icon-document'
    },
    handleSort() {
      this.$message.info('排序功能开发中')
    },
    handleAdd() {
      this.resetForm()
      this.dialogTitle = '新建BOM'
      this.dialogVisible = true
    },
    handleImport() {
      this.$message.info('导入功能开发中')
    },
    handleUpdate(row) {
      this.form = createBomForm()
      getBom(row.id).then(response => {
        this.form = createBomForm(response.data)
        this.dialogTitle = '编辑BOM'
        this.dialogVisible = true
      })
    },
    handleCopy(row) {
      this.form = createBomForm()
      getBom(row.id).then(response => {
        this.form = createBomForm({
          ...response.data,
          id: null,
          bomCode: response.data.bomCode + '-copy'
        })
        this.dialogTitle = '复制BOM'
        this.dialogVisible = true
      })
    },
    handleDisable() {
      this.$modal.confirm('是否确认禁用选中的BOM？').then(() => {
        this.$modal.msgSuccess('禁用成功')
      }).catch(() => {})
    },
    handleDelete() {
      this.$modal.confirm('是否确认删除选中的BOM？').then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getBomTree()
      }).catch(() => {})
    },
    handleExport(row) {
      this.download('system/bom/export', {
        id: row.id
      }, `bom_${row.bomCode}_${new Date().getTime()}.xlsx`)
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.dialogVisible = false
              this.getBomTree()
            })
          } else {
            addBom(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.dialogVisible = false
              this.getBomTree()
            })
          }
        }
      })
    },
    resetForm() {
      this.form = createBomForm()
      if (this.$refs["form"]) {
        this.$refs["form"].resetFields()
      }
    },
    applyAdvancedFilter() {
      this.showAdvancedFilter = false
      this.$message.success('高级筛选已应用')
      this.getBomTree()
    }
  }
}
</script>

<style scoped>
.bom-container {
  padding: 20px;
  background: #f5f7fa;
  min-height: calc(100vh - 84px);
}

.bom-header {
  margin-bottom: 20px;
}

.breadcrumb {
  font-size: 14px;
  color: #909399;
  margin-bottom: 15px;
}

.breadcrumb i {
  margin-right: 8px;
}

.breadcrumb-item {
  cursor: pointer;
}

.breadcrumb-item:hover {
  color: #409eff;
}

.breadcrumb-item.active {
  color: #303133;
  cursor: default;
}

.breadcrumb-separator {
  margin: 0 8px;
}

.page-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 15px 0;
}

.header-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.header-actions .el-button {
  border-radius: 4px;
}

.bom-content {
  display: flex;
  gap: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  overflow: hidden;
  min-height: 600px;
}

.bom-sidebar {
  width: 280px;
  border-right: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  padding: 20px;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.sidebar-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.sidebar-filters {
  padding: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.bom-tree {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.tree-node-label {
  font-size: 14px;
  color: #303133;
  margin-bottom: 4px;
}

.tree-node-label i {
  margin-right: 6px;
  color: #409eff;
}

.tree-node-code {
  font-size: 12px;
  color: #909399;
}

.empty-tree {
  text-align: center;
  padding: 40px 20px;
  color: #909399;
}

.empty-tree i {
  font-size: 48px;
  margin-bottom: 10px;
}

.empty-tree p {
  font-size: 14px;
}

.bom-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  min-height: 400px;
  color: #909399;
}

.empty-state i {
  font-size: 80px;
  color: #409eff;
  margin-bottom: 20px;
}

.empty-state h3 {
  font-size: 18px;
  color: #606266;
  margin: 0 0 10px 0;
}

.empty-state p {
  font-size: 14px;
  margin: 0 0 30px 0;
}

.empty-actions {
  display: flex;
  gap: 15px;
}

.empty-actions .el-button {
  min-width: 100px;
}

.bom-detail {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.detail-actions {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

::v-deep .el-tree {
  background: transparent;
}

::v-deep .el-tree-node__content {
  height: auto;
  padding: 8px 0;
}

::v-deep .el-tree-node__content:hover {
  background-color: #f5f7fa;
}

::v-deep .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #ecf5ff;
}
</style>
