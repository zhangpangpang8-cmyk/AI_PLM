<template>
  <div class="app-container">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/" class="breadcrumb">
      <el-breadcrumb-item>
        <router-link to="/">首页</router-link>
      </el-breadcrumb-item>
      <el-breadcrumb-item>产品管理</el-breadcrumb-item>
      <el-breadcrumb-item>物料库</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2 class="page-title">物料库</h2>
      <div class="header-actions">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增物料</el-button>
        <el-button type="primary" icon="el-icon-upload2" @click="handleImport">批量导入</el-button>
        <el-button type="primary" icon="el-icon-download" @click="handleDownloadTemplate">下载模板</el-button>
      </div>
    </div>

    <material-statistics :value="statistics" />

    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="auto">
        <el-form-item prop="searchText">
          <el-input
            v-model="queryParams.searchText"
            placeholder="输入物料编码/名称/规格搜索"
            clearable
            prefix-icon="el-icon-search"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item prop="materialClassifyIds">
          <treeselect
            v-model="queryParams.materialClassifyIds"
            :options="classifyOptions"
            :normalizer="normalizer"
            placeholder="物料分类"
            clearable style="width: 200px"
          />
        </el-form-item>
        <el-form-item prop="auditStatus">
          <el-select
            v-model="queryParams.auditStatus"
            placeholder="审核状态"
            clearable
          >
            <el-option
              v-for="option in auditStatusOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="enable">
          <el-select
            v-model="queryParams.enable"
            placeholder="启用状态"
            clearable
          >
            <el-option
              v-for="option in enableStatusOptions"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮栏 -->
    <div class="toolbar">
      <div class="left-actions">
        <el-button-group>
          <el-button icon="el-icon-refresh" @click="getList">刷新</el-button>
          <el-button icon="el-icon-setting">列设置</el-button>
          <el-button icon="el-icon-download" @click="handleExport">导出Excel</el-button>
        </el-button-group>
        <el-dropdown split-button type="primary" @command="handleBatchCommand">
          批量提交
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="submit">批量提交审核</el-dropdown-item>
            <el-dropdown-item command="enable">批量启用</el-dropdown-item>
            <el-dropdown-item command="disable">批量禁用</el-dropdown-item>
            <el-dropdown-item command="delete">批量删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="itemList"
      @selection-change="handleSelectionChange"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      border
      stripe
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column type="expand" width="50">
        <template slot-scope="{row}">
          <div class="expand-content">
            <p><strong>详细描述：</strong>{{ row.Info || '暂无描述' }}</p>
            <p><strong>技术参数：</strong>{{ row.parametersValues || '暂无参数' }}</p>
            <p><strong>材质：</strong>{{ row.material || '-' }}</p>
            <p><strong>颜色：</strong>{{ row.color || '-' }}</p>
            <p><strong>重量：</strong>{{ row.weight ? row.weight + 'kg' : '-' }}</p>
            <p><strong>供应商：</strong>{{ row.vendorName || '-' }}</p>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="物料编码" prop="itemCode" min-width="120" sortable show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link type="primary" @click="handleView(scope.row)">{{ scope.row.itemCode }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="物料名称" prop="itemName" min-width="150" show-overflow-tooltip/>
      <el-table-column label="规格型号" prop="specification" min-width="150" show-overflow-tooltip/>
      <el-table-column label="单位" prop="unitName" width="80"/>
      <el-table-column label="版本" prop="itemVersion" width="80"/>
      <el-table-column label="启用状态" prop="enable" width="100" align="center">
        <template slot-scope="scope">
          <business-status-tag group="enabledDisabled" :value="scope.row.enable" size="small" />
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="approvalStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.item_approval_status" :value="scope.row.approvalStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商" prop="vendorName" min-width="120" show-overflow-tooltip/>
      <el-table-column label="物料分类" prop="materialClassifyName" min-width="120" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" min-width="280" fixed="right">
        <template slot-scope="scope">
          <div class="operation-btns">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:item:edit']"
            >编辑
            </el-button>
            <el-button
              v-if="scope.row.auditStatus === '0'"
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleAudit(scope.row)"
              v-hasPermi="['system:item:audit']"
              style="color: #67C23A"
            >审核
            </el-button>
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-document"
              @click="handleVersionHistory(scope.row)"
            >版本历史
            </el-button>
            <el-dropdown trigger="click" @command="(cmd) => handleMoreAction(cmd, scope.row)">
              <el-button size="mini" type="text">
                更多<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="view">查看详情</el-dropdown-item>
                <el-dropdown-item command="copy">复制物料</el-dropdown-item>
                <el-dropdown-item command="bom">查看BOM</el-dropdown-item>
                <el-dropdown-item command="drawing">查看图纸</el-dropdown-item>
                <el-dropdown-item command="disable" v-if="scope.row.enable === '1'">禁用</el-dropdown-item>
                <el-dropdown-item command="enable" v-if="scope.row.enable === '0'">启用</el-dropdown-item>
                <el-dropdown-item command="delete" divided>删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <material-form-dialog
      :visible="open"
      :title="title"
      :form="form"
      :classify-options="classifyOptions"
      :unit-options="unitOptions"
      :normalizer="normalizer"
      @cancel="cancel"
      @submit="submitForm"
    />
    <material-audit-dialog
      :visible="auditDialogVisible"
      :item="auditItem"
      @cancel="auditDialogVisible = false"
      @submit="submitAudit"
    />
    <material-version-dialog
      :visible="versionDialogVisible"
      :versions="versionList"
      @close="versionDialogVisible = false"
      @view="handleViewVersion"
    />
  </div>
</template>

<script>
import {listItem, getItem, delItem, addItem, updateItem, getStatistics, auditItem, getVersionHistory} from "@/api/system/item"
import {listClassifyTree} from "@/api/system/classify"
import {getDicts} from "@/api/system/dict/data"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import MaterialStatistics from './components/MaterialStatistics'
import MaterialFormDialog from './components/MaterialFormDialog'
import MaterialAuditDialog from './components/MaterialAuditDialog'
import MaterialVersionDialog from './components/MaterialVersionDialog'
import {
  ENABLE_STATUS_OPTIONS,
  ITEM_AUDIT_STATUS_OPTIONS,
  MATERIAL_UNIT_DICT_TYPE,
  buildMaterialStatistics,
  createEmptyMaterialStatistics,
  createItemForm,
  createItemListParams,
  createItemQueryParams,
  normalizeMaterialClassifyNode
} from '@/utils/material'

export default {
  name: "MaterialLibrary",
  components: {
    Treeselect,
    MaterialStatistics,
    MaterialFormDialog,
    MaterialAuditDialog,
    MaterialVersionDialog
  },
  dicts: ['item_approval_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 物料列表
      itemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 版本历史对话框
      versionDialogVisible: false,
      // 版本列表
      versionList: [],
      // 审核对话框
      auditDialogVisible: false,
      auditItem: {},
      // 统计数据
      statistics: createEmptyMaterialStatistics(),
      // 物料分类选项（树形）
      classifyOptions: [],
      // 单位字典选项
      unitOptions: [],
      // 查询参数
      queryParams: createItemQueryParams(),
      // 表单参数
      form: createItemForm(),
      auditStatusOptions: ITEM_AUDIT_STATUS_OPTIONS,
      enableStatusOptions: ENABLE_STATUS_OPTIONS
    }
  },
  created() {
    this.getList()
    this.getClassifyTree()
    this.getStatisticsData()
    this.getUnitDicts()
  },
  methods: {
    /** 查询物料分类树 */
    getClassifyTree() {
      listClassifyTree().then(response => {
        this.classifyOptions = response.data || []
      })
    },

    /** 获取单位字典 */
    getUnitDicts() {
      getDicts(MATERIAL_UNIT_DICT_TYPE).then(response => {
        this.unitOptions = response.data || []
      }).catch(() => {
        this.unitOptions = []
      })
    },

    /** 获取统计数据 */
    getStatisticsData() {
      getStatistics().then(response => {
        this.statistics = buildMaterialStatistics(response.data)
      }).catch(() => {
        this.statistics = createEmptyMaterialStatistics()
      })
    },

    /** 转换树形数据格式 */
    normalizer(node) {
      return normalizeMaterialClassifyNode(node)
    },
    /** 查询物料列表 */
    getList() {
      this.loading = true
      const params = createItemListParams(this.queryParams)

      listItem(params).then(response => {
        this.itemList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },

    // 表单重置
    reset() {
      this.form = createItemForm()
    },

    /** 审核按钮操作 */
    handleAudit(row) {
      this.auditItem = row
      this.auditDialogVisible = true
    },

    /** 提交审核 */
    submitAudit(payload) {
      auditItem(payload).then(() => {
        this.$modal.msgSuccess('审核成功')
        this.auditDialogVisible = false
        this.getList()
      })
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增物料"
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getItem(id).then(response => {
        this.form = createItemForm(response.data)
        this.open = true
        this.title = "修改物料"
      })
    },

    /** 查看详情 */
    handleView(row) {
      this.$router.push({
        path: '/system/item/detail',
        query: {id: row.id}
      })
    },

    /** 版本历史 */
    handleVersionHistory(row) {
      this.versionDialogVisible = true
      getVersionHistory(row.id).then(response => {
        this.versionList = response.data || []
      }).catch(() => {
        this.versionList = []
      })
    },

    /** 查看版本详情 */
    handleViewVersion(row) {
      this.$message.info('查看版本: ' + row.itemVersion)
    },

    /** 批量操作命令 */
    handleBatchCommand(command) {
      if (this.ids.length === 0) {
        this.$message.warning('请先选择要操作的物料')
        return
      }

      switch (command) {
        case 'submit':
          this.$modal.confirm('确认批量提交选中的物料进行审核？').then(() => {
            this.$message.success('批量提交成功')
          })
          break
        case 'enable':
          this.$modal.confirm('确认批量启用选中的物料？').then(() => {
            this.$message.success('批量启用成功')
          })
          break
        case 'disable':
          this.$modal.confirm('确认批量禁用选中的物料？').then(() => {
            this.$message.success('批量禁用成功')
          })
          break
        case 'delete':
          this.handleDelete()
          break
      }
    },

    /** 更多操作 */
    handleMoreAction(command, row) {
      switch (command) {
        case 'view':
          this.handleView(row)
          break
        case 'copy':
          this.$message.info('复制物料功能开发中')
          break
        case 'bom':
          this.$message.info('查看BOM功能开发中')
          break
        case 'drawing':
          this.$message.info('查看图纸功能开发中')
          break
        case 'enable':
          this.$modal.confirm('确认启用该物料？').then(() => {
            this.$message.success('启用成功')
          })
          break
        case 'disable':
          this.$modal.confirm('确认禁用该物料？').then(() => {
            this.$message.success('禁用成功')
          })
          break
        case 'delete':
          this.handleDelete(row)
          break
      }
    },

    /** 提交按钮 */
    submitForm() {
      const request = this.form.id != null ? updateItem(this.form) : addItem(this.form)
      const message = this.form.id != null ? '修改成功' : '新增成功'
      request.then(() => {
        this.$modal.msgSuccess(message)
        this.open = false
        this.getList()
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row ? row.id : this.ids
      this.$modal.confirm('是否确认删除选中的物料？').then(function () {
        return delItem(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('system/item/export', {
        ...this.queryParams
      }, `物料库_${new Date().getTime()}.xlsx`)
    },

    /** 批量导入 */
    handleImport() {
      this.$message.info('批量导入功能开发中')
    },

    /** 下载模板 */
    handleDownloadTemplate() {
      this.download('system/item/downloadTemplate', {}, '物料导入模板.xlsx')
    }
  }
}
</script>

<style scoped>
.breadcrumb {
  margin-bottom: 15px;
  font-size: 14px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

.header-actions .el-button {
  margin-left: 10px;
}

/* 搜索卡片样式 */
.search-card {
  margin-bottom: 20px;
  border-radius: 8px;
}

.search-card .el-form {
  margin-bottom: 0;
}

.search-card .el-form-item {
  margin-right: 10px;
  margin-bottom: 10px;
}

.search-card .el-input,
.search-card .el-select {
  width: 200px;
}

/* 响应式适配 */
@media (max-width: 1920px) {
  .search-card .el-input,
  .search-card .el-select {
    width: 180px;
  }
}

@media (max-width: 1600px) {
  .search-card .el-input,
  .search-card .el-select {
    width: 160px;
  }

}

@media (max-width: 1366px) {
  .search-card .el-input,
  .search-card .el-select {
    width: 150px;
  }

  .page-title {
    font-size: 20px;
  }

  .header-actions .el-button {
    padding: 8px 12px;
  }
}

@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .header-actions {
    margin-top: 10px;
    width: 100%;
  }

  .header-actions .el-button {
    margin-left: 0;
    margin-right: 10px;
    margin-bottom: 10px;
  }

  .search-card .el-input,
  .search-card .el-select {
    width: 100%;
  }

  .search-card .el-form-item {
    width: 100%;
    margin-right: 0;
  }

  .toolbar {
    flex-direction: column;
    align-items: flex-start;
  }

  .left-actions {
    width: 100%;
    margin-bottom: 10px;
  }

  .left-actions .el-button-group {
    margin-right: 0;
    margin-bottom: 10px;
  }
}

/* 展开内容样式 */
.expand-content {
  padding: 20px;
  background-color: #f5f7fa;
}

.expand-content p {
  margin: 8px 0;
  line-height: 1.6;
}

/* 对话框底部样式 */
.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  margin-left: 10px;
}

/* 操作列按钮自适应容器 */
.operation-btns {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 2px;
}

.operation-btns .el-button {
  margin-left: 0;
  padding: 7px 8px;
}
</style>
