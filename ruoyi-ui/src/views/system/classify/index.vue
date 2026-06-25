<template>
  <div class="app-container">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator="/" class="breadcrumb">
      <el-breadcrumb-item>
        <router-link to="/">首页</router-link>
      </el-breadcrumb-item>
      <el-breadcrumb-item>产品管理</el-breadcrumb-item>
      <el-breadcrumb-item>物料分类</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2 class="page-title">物料分类</h2>
      <div class="header-actions">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增分类</el-button>
        <el-button type="primary" icon="el-icon-upload2" @click="handleImport">批量导入</el-button>
      </div>
    </div>

    <!-- 搜索区域 -->
    <el-card class="search-card" shadow="never">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="auto">
        <el-form-item prop="materialClassifyName">
          <el-input
            v-model="queryParams.materialClassifyName"
            placeholder="搜索分类名称"
            clearable
            prefix-icon="el-icon-search"
            style="width: 200px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item prop="enable">
          <el-select
            v-model="queryParams.enable"
            placeholder="是否启用"
            clearable
            style="width: 150px"
          >
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <div class="expand-btn">
        <el-button size="small" icon="el-icon-sort" @click="toggleExpandAll">{{
            expandAll ? '折叠' : '展开'
          }}
        </el-button>
      </div>
    </el-card>

    <!-- 数据表格 -->
    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="classifyList"
      row-key="id"
      :default-expand-all="expandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      border
      stripe
      style="width: 100%"
    >
      <el-table-column
        label="物料类型名称"
        prop="materialClassifyName"
        min-width="200"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <span v-if="scope.row.parentClassifyId === 0 || !scope.row.parentClassifyId">
            <i class="el-icon-folder-opened" style="color: #409EFF; margin-right: 5px;"></i>
          </span>
          <span v-else>
            <i class="el-icon-folder" style="color: #909399; margin-right: 5px;"></i>
          </span>
          {{ scope.row.materialClassifyName }}
        </template>
      </el-table-column>
      <el-table-column
        label="物料类型编码"
        prop="materialClassifyCode"
        width="150"
        show-overflow-tooltip
      />
      <el-table-column
        label="排序"
        prop="orderNum"
        width="100"
        align="center"
      />
      <el-table-column
        label="是否启用"
        prop="enable"
        width="100"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.enable === '1' ? 'success' : 'info'" size="small">
            {{ scope.row.enable === '1' ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="280"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="default"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:classify:edit']" style="margin-right: 5px;"
          >编辑
          </el-button>
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-plus"
            @click="handleAddChild(scope.row)"
            v-hasPermi="['system:classify:add']" style="margin-right: 5px;"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:classify:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="物料类型名称" prop="materialClassifyName">
              <el-input v-model="form.materialClassifyName" placeholder="请输入物料类型名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="物料类型编码" prop="materialClassifyCode">
              <el-input v-model="form.materialClassifyCode" placeholder="请输入物料类型编码"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" :min="0" :max="999" controls-position="right"
                               style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="启用状态" prop="enable">
              <el-radio-group v-model="form.enable">
                <el-radio label="1">是</el-radio>
                <el-radio label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 技术参数区域 -->
        <el-divider content-position="left">技术参数</el-divider>
        <el-table :data="parameterList" border stripe style="width: 100%; margin-bottom: 15px;">
          <el-table-column label="参数名称" min-width="150">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.paramName"
                placeholder="请输入参数名称"
                size="small"
              />
            </template>
          </el-table-column>
          <el-table-column label="输入提示" min-width="200">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.inputTip"
                placeholder="请输入输入提示"
                size="small"
              />
            </template>
          </el-table-column>
          <el-table-column label="单位" width="120">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.unit"
                placeholder="请输入单位"
                size="small"
              />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100" align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDeleteParameter(scope.$index)" style="color: #F56C6C;"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-if="parameterList.length === 0" class="empty-tip">暂无数据</div>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleAddParameter">添加参数</el-button>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listClassify, getClassify, delClassify, addClassify, updateClassify} from "@/api/system/classify"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"


export function listClassifyTree() {
  return request({
    url: '/system/classify/tree',
    method: 'get'
  })
}

export default {
  name: "MaterialClassify",
  components: {Treeselect},
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
      // 物料分类表格数据
      classifyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开所有
      expandAll: false,
      // 刷新表格
      refreshTable: true,
      // 分类选项（用于树形选择器）
      classifyOptions: [],
      // 查询参数
      queryParams: {
        materialClassifyName: null,
        enable: null
      },
      // 表单参数
      form: {},
      // 技术参数列表
      parameterList: [],
      // 表单校验
      rules: {
        materialClassifyName: [
          {required: true, message: "物料类型名称不能为空", trigger: "blur"}
        ],
        materialClassifyCode: [
          {required: true, message: "物料类型编码不能为空", trigger: "blur"}
        ],
        orderNum: [
          {required: true, message: "显示顺序不能为空", trigger: "blur"}
        ],
        enable: [
          {required: true, message: "是否启用不能为空", trigger: "change"}
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询物料分类列表 */
    getList() {
      this.loading = true
      listClassify(this.queryParams).then(response => {
        const dataList = response.data || []

        if (dataList && dataList.length > 0) {
          this.classifyList = this.handleTree(dataList, "id", "parentClassifyId")
        } else {
          this.classifyList = []
        }

        this.total = dataList.length
        this.loading = false
      }).catch(error => {
        console.error('获取数据失败:', error)
        this.loading = false
      })
    },

    /** 转换树形数据 */
    handleTree(data, id, parentId, children) {
      const config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
      }

      const childrenListMap = {}
      const nodeIds = {}
      const tree = []

      for (const d of data) {
        const parentId = d[config.parentId]
        if (childrenListMap[parentId] == null) {
          childrenListMap[parentId] = []
        }
        nodeIds[d[config.id]] = d
        childrenListMap[parentId].push(d)
      }

      for (const d of data) {
        const parentId = d[config.parentId]
        if (nodeIds[parentId] == null) {
          tree.push(d)
        }
      }

      for (const t of tree) {
        adaptToChildrenList(t)
      }

      function adaptToChildrenList(o) {
        if (childrenListMap[o[config.id]] !== null) {
          o[config.childrenList] = childrenListMap[o[config.id]]
        }
        if (o[config.childrenList]) {
          for (const c of o[config.childrenList]) {
            adaptToChildrenList(c)
          }
        }
      }

      return tree
    },


    toggleExpandAll() {
      this.refreshTable = false
      this.expandAll = !this.expandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
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
        materialClassifyCode: null,
        materialClassifyName: null,
        parentClassifyId: 0,
        ancestors: null,
        ancestorsName: null,
        orderNum: 0,
        enable: '1',
        remark: null,
        mesSyncId: null
      }
      this.parameterList = []
      this.resetForm("form")
    },

    /** 搜索按钮操作 */
    handleQuery() {
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
      this.title = "添加分类"
    },

    /** 新增子分类 */
    handleAddChild(row) {
      this.reset()
      this.form.parentClassifyId = row.id
      this.open = true
      this.title = "添加分类"
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getClassify(row.id).then(response => {
        this.form = response.data
        // 解析技术参数JSON
        if (this.form.parameters) {
          try {
            this.parameterList = JSON.parse(this.form.parameters)
          } catch (e) {
            this.parameterList = []
          }
        } else {
          this.parameterList = []
        }
        this.open = true
        this.title = "修改分类"
      })
    },

    /** 添加参数 */
    handleAddParameter() {
      this.parameterList.push({
        paramName: '',
        inputTip: '',
        unit: ''
      })
    },

    /** 删除参数 */
    handleDeleteParameter(index) {
      this.parameterList.splice(index, 1)
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 将技术参数列表转换为JSON字符串
          this.form.parameters = JSON.stringify(this.parameterList)

          if (this.form.id != null) {
            updateClassify(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addClassify(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除分类名称为"' + row.materialClassifyName + '"的数据项？').then(function () {
        return delClassify(row.id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('system/classify/export', {
        ...this.queryParams
      }, `物料分类_${new Date().getTime()}.xlsx`)
    },

    /** 批量导入 */
    handleImport() {
      this.$message.info('批量导入功能开发中')
    }
  }
}
</script>

<style scoped>.breadcrumb {
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
  position: relative;
}

.search-card .el-form {
  margin-bottom: 0;
}

.expand-btn {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
}

/* 表格样式优化 */
.el-table {
  font-size: 14px;
}

.el-table th {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 500;
}

/* 空数据提示 */
.empty-tip {
  text-align: center;
  padding: 40px;
  color: #909399;
  font-size: 14px;
}

/* 对话框底部样式 */
.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  margin-left: 10px;
}

/* 分隔线样式 */
::v-deep .el-divider__text {
  font-weight: 500;
  color: #303133;
}

/* 响应式适配 */
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

  .expand-btn {
    position: static;
    transform: none;
    margin-top: 10px;
  }
}
</style>
