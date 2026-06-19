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

    <div class="statistics-cards">
      <el-row :gutter="20" type="flex" justify="space-between">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="3">
          <div class="stat-card stat-total">
            <div class="stat-label">物料总数</div>
            <div class="stat-value">{{ statistics.totalCount }}</div>
            <div class="stat-compare">较上月 +{{ statistics.compareLastMonth }}</div>
          </div>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="3" v-for="(item, index) in statistics.categories.slice(0, 7)"
                :key="index">
          <div class="stat-card" :class="'stat-' + item.color">
            <div class="stat-label">{{ item.name }}</div>
            <div class="stat-value">{{ item.count }}</div>
            <div class="stat-percent">占比 {{ item.percent }}%</div>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" type="flex" justify="space-between" style="margin-top: 20px;">
        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="3" v-for="(item, index) in statistics.categories.slice(7)"
                :key="'second-' + index">
          <div class="stat-card" :class="'stat-' + item.color">
            <div class="stat-label">{{ item.name }}</div>
            <div class="stat-value">{{ item.count }}</div>
            <div class="stat-percent">占比 {{ item.percent }}%</div>
          </div>
        </el-col>
      </el-row>
    </div>

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
        <el-form-item prop="itemTypeCode">
          <el-select
            v-model="queryParams.itemTypeCode"
            placeholder="物料类型"
            clearable
          >
            <el-option label="器件原材料" value="RAW_MATERIAL"></el-option>
            <el-option label="结构类" value="STRUCTURE"></el-option>
            <el-option label="紧固件线材辅料" value="FASTENER"></el-option>
            <el-option label="工具" value="TOOL"></el-option>
            <el-option label="印制板类" value="PCB"></el-option>
            <el-option label="外协产品" value="OUTSOURCED"></el-option>
            <el-option label="其他" value="OTHER"></el-option>
            <el-option label="半成品" value="SEMI_FINISHED"></el-option>
            <el-option label="成品" value="FINISHED"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="materialClassifyName">
          <el-input
            v-model="queryParams.materialClassifyName"
            placeholder="物料分类"
            clearable
            prefix-icon="el-icon-folder"
          />
        </el-form-item>
        <el-form-item prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="状态"
            clearable
          >
            <el-option label="已完成" value="COMPLETED"></el-option>
            <el-option label="进行中" value="IN_PROGRESS"></el-option>
            <el-option label="草稿" value="DRAFT"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="enable">
          <el-select
            v-model="queryParams.enable"
            placeholder="启用状态"
            clearable
          >
            <el-option label="启用" value="1"></el-option>
            <el-option label="禁用" value="0"></el-option>
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
      <el-table-column label="物料类型" prop="itemTypeName" min-width="120"/>
      <el-table-column label="规格型号" prop="specification" min-width="150" show-overflow-tooltip/>
      <el-table-column label="单位" prop="unitName" width="80"/>
      <el-table-column label="版本" prop="itemVersion" width="80"/>
      <el-table-column label="状态" prop="status" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="getStatusTagType(scope.row.status)" size="small">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="启用状态" prop="enable" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.enable === '1' ? 'success' : 'info'" size="small">
            {{ scope.row.enable === '1' ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="供应商" prop="vendorName" min-width="120" show-overflow-tooltip/>
      <el-table-column label="物料分类" prop="materialClassifyName" min-width="120" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" width="280" fixed="right">
        <template slot-scope="scope">
          <el-button-group>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:item:edit']"
            >编辑
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
          </el-button-group>
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

    <!-- 添加或修改物料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="物料编码" prop="itemCode">
              <el-input v-model="form.itemCode" placeholder="请输入物料编码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料名称" prop="itemName">
              <el-input v-model="form.itemName" placeholder="请输入物料名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料类型" prop="itemTypeCode">
              <el-select v-model="form.itemTypeCode" placeholder="请选择物料类型" style="width: 100%">
                <el-option label="器件原材料" value="RAW_MATERIAL"></el-option>
                <el-option label="结构类" value="STRUCTURE"></el-option>
                <el-option label="紧固件线材辅料" value="FASTENER"></el-option>
                <el-option label="工具" value="TOOL"></el-option>
                <el-option label="印制板类" value="PCB"></el-option>
                <el-option label="外协产品" value="OUTSOURCED"></el-option>
                <el-option label="其他" value="OTHER"></el-option>
                <el-option label="半成品" value="SEMI_FINISHED"></el-option>
                <el-option label="成品" value="FINISHED"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格型号" prop="specification">
              <el-input v-model="form.specification" placeholder="请输入规格型号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="form.unitName" placeholder="请输入单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料分类" prop="materialClassifyName">
              <el-input v-model="form.materialClassifyName" placeholder="请输入物料分类"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商" prop="vendorName">
              <el-input v-model="form.vendorName" placeholder="请输入供应商名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="材质" prop="material">
              <el-input v-model="form.material" placeholder="请输入材质"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="颜色" prop="color">
              <el-input v-model="form.color" placeholder="请输入颜色"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="重量(kg)" prop="weight">
              <el-input-number v-model="form.weight" :precision="2" :step="0.1" style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细描述" prop="Info">
              <el-input v-model="form.Info" type="textarea" :rows="3" placeholder="请输入详细描述"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="技术参数" prop="parametersValues">
              <el-input v-model="form.parametersValues" type="textarea" :rows="3" placeholder="请输入技术参数"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否启用" prop="enable">
              <el-radio-group v-model="form.enable">
                <el-radio label="1">启用</el-radio>
                <el-radio label="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="remake">
              <el-input v-model="form.remake" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 版本历史对话框 -->
    <el-dialog title="版本历史" :visible.sync="versionDialogVisible" width="900px" append-to-body>
      <el-table :data="versionList" border stripe>
        <el-table-column label="版本号" prop="itemVersion" width="100"/>
        <el-table-column label="物料编码" prop="itemCode" width="120"/>
        <el-table-column label="物料名称" prop="itemName" width="150"/>
        <el-table-column label="规格型号" prop="specification" width="150"/>
        <el-table-column label="状态" prop="status" width="100" align="center">
          <template slot-scope="scope">
            <el-tag :type="getStatusTagType(scope.row.status)" size="small">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="发布时间" prop="createTime" width="160"/>
        <el-table-column label="发布人" prop="createBy" width="100"/>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="handleViewVersion(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {listItem, getItem, delItem, addItem, updateItem} from "@/api/system/item"

export default {
  name: "MaterialLibrary",
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
      // 统计数据
      statistics: {
        totalCount: 1795,
        compareLastMonth: 0,
        categories: [
          {name: '器件原材料', count: 1348, percent: 75.10, color: 'green'},
          {name: '结构类', count: 136, percent: 7.58, color: 'yellow'},
          {name: '紧固件线材辅料', count: 132, percent: 7.35, color: 'purple'},
          {name: '工具', count: 94, percent: 5.24, color: 'blue'},
          {name: '印制板类', count: 49, percent: 2.73, color: 'teal'},
          {name: '外协产品', count: 22, percent: 1.23, color: 'orange'},
          {name: '其他', count: 7, percent: 0.39, color: 'pink'},
          {name: '半成品', count: 6, percent: 0.33, color: 'light-blue'},
          {name: '成品', count: 1, percent: 0.06, color: 'light-green'}
        ]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        searchText: null,
        itemTypeCode: null,
        materialClassifyName: null,
        status: null,
        enable: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          {required: true, message: "物料编码不能为空", trigger: "blur"}
        ],
        itemName: [
          {required: true, message: "物料名称不能为空", trigger: "blur"}
        ],
        itemTypeCode: [
          {required: true, message: "物料类型不能为空", trigger: "change"}
        ],
        specification: [
          {required: true, message: "规格型号不能为空", trigger: "blur"}
        ],
        unitName: [
          {required: true, message: "单位不能为空", trigger: "blur"}
        ],
        enable: [
          {required: true, message: "启用状态不能为空", trigger: "change"}
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询物料列表 */
    getList() {
      this.loading = true
      const params = {...this.queryParams}

      // 处理搜索文本
      if (params.searchText) {
        params.itemCode = params.searchText
        params.itemName = params.searchText
        params.specification = params.searchText
      }

      listItem(params).then(response => {
        this.itemList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },

    /** 获取状态标签类型 */
    getStatusTagType(status) {
      const typeMap = {
        'COMPLETED': 'success',
        'IN_PROGRESS': 'warning',
        'DRAFT': 'info'
      }
      return typeMap[status] || 'info'
    },

    /** 获取状态文本 */
    getStatusText(status) {
      const textMap = {
        'COMPLETED': '已完成',
        'IN_PROGRESS': '进行中',
        'DRAFT': '草稿'
      }
      return textMap[status] || status
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },

    // 表单重置
    reset() {
      this.form = {
        id: null,
        itemCode: null,
        itemName: null,
        itemTypeCode: null,
        itemTypeName: null,
        specification: null,
        unitName: null,
        materialClassifyName: null,
        vendorName: null,
        material: null,
        color: null,
        weight: null,
        Info: null,
        parametersValues: null,
        enable: '1',
        remake: null,
        status: 'DRAFT',
        itemVersion: 'V1.0'
      }
      this.resetForm("form")
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
        this.form = response.data
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
      // TODO: 调用API获取版本历史
      this.versionList = [
        {...row, itemVersion: 'V1.0', createTime: '2026-06-17 10:00:00', createBy: 'admin'},
        {...row, itemVersion: 'V0.9', createTime: '2026-06-16 15:30:00', createBy: 'admin'}
      ]
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
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addItem(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
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

/* 统计卡片样式 */
.statistics-cards {
  margin-bottom: 20px;
}

.stat-card {
  padding: 20px 15px;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  cursor: pointer;
  min-height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
}

.stat-total {
  background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
}

.stat-green {
  background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 100%);
}

.stat-yellow {
  background: linear-gradient(135deg, #fff9e6 0%, #fff3cd 100%);
}

.stat-purple {
  background: linear-gradient(135deg, #f3e5f5 0%, #e1bee7 100%);
}

.stat-blue {
  background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
}

.stat-teal {
  background: linear-gradient(135deg, #e0f2f1 0%, #b2dfdb 100%);
}

.stat-orange {
  background: linear-gradient(135deg, #fff3e0 0%, #ffe0b2 100%);
}

.stat-pink {
  background: linear-gradient(135deg, #fce4ec 0%, #f8bbd0 100%);
}

.stat-light-blue {
  background: linear-gradient(135deg, #e1f5fe 0%, #b3e5fc 100%);
}

.stat-light-green {
  background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 100%);
}

.stat-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 10px;
  word-break: break-all;
  line-height: 1.4;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 8px;
}

.stat-compare,
.stat-percent {
  font-size: 13px;
  color: #909399;
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

  .stat-card {
    padding: 15px 10px;
    min-height: 100px;
  }

  .stat-value {
    font-size: 24px;
  }

  .stat-label {
    font-size: 13px;
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
</style>
