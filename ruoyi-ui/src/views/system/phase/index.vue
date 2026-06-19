<template>
  <div class="app-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title-row">
        <h2 class="page-title">任务分配</h2>
        <div class="page-actions">
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新建任务</el-button>
          <el-button type="primary" icon="el-icon-upload2" @click="handleImport">批量导入</el-button>
          <el-button type="primary" icon="el-icon-download" @click="handleExport">导出任务</el-button>
        </div>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards">
      <el-col :span="4">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-label">总阶段数</div>
            <div class="stat-value primary">{{ statistics.totalCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-label">进行中</div>
            <div class="stat-value info">{{ statistics.inProgressCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-label">已完成</div>
            <div class="stat-value success">{{ statistics.completedCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-label">已延期</div>
            <div class="stat-value danger">{{ statistics.overdueCount }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-label">高优先级</div>
            <div class="stat-value warning">{{ statistics.highPriorityCount }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-card shadow="never" class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="auto">
        <el-form-item>
          <el-input
            v-model="queryParams.phaseName"
            placeholder="请输入阶段名称"
            prefix-icon="el-icon-search"
            clearable
            @keyup.enter.native="handleQuery"            style="width: 180px;"
          />
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="queryParams.projectName"
            placeholder="请输入项目名称"
            prefix-icon="el-icon-search"
            clearable
            style="width: 180px;"
          />
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 140px;">
            <el-option label="已完成" value="3"/>
            <el-option label="进行中" value="1"/>
            <el-option label="已暂停" value="4"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.priority" placeholder="请选择优先级" clearable style="width: 140px;">
            <el-option label="高" value="high"/>
            <el-option label="中" value="medium"/>
            <el-option label="低" value="low"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="queryParams.assigneeName"
            placeholder="请输入负责人"
            prefix-icon="el-icon-search"
            clearable
            style="width: 180px;"
          />
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="-"
            start-placeholder="截止日期开始"
            end-placeholder="截止日期结束"
            value-format="yyyy-MM-dd"
            style="width: 260px;"
          />
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.sortType" placeholder="排序方式" clearable style="width: 140px;">
            <el-option label="开始日期-逆序" value="start_desc"/>
            <el-option label="开始日期-正序" value="start_asc"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮 -->
    <div class="toolbar">
      <div class="view-tabs">
        <el-button-group>
          <el-button type="primary" size="mini" icon="el-icon-s-grid">表格视图</el-button>
          <el-button size="mini" icon="el-icon-data-line">甘特图</el-button>
          <el-button size="mini" icon="el-icon-menu">看板视图</el-button>
          <el-button size="mini" icon="el-icon-share">层次视图</el-button>
        </el-button-group>
      </div>
      <div class="batch-actions">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDeleteBatch"
        >批量删除</el-button>
        <el-button
          type="primary"
          icon="el-icon-user"
          size="mini"
          :disabled="multiple"
          @click="handleBatchAssign"
        >批量分配</el-button>
      </div>
    </div>

    <el-card shadow="never" class="table-card">
      <el-table
        v-loading="loading"
        :data="phaseList"
        stripe
        @selection-change="handleSelectionChange"        style="width: 100%;"
      >
        <el-table-column type="selection" width="50" align="center" fixed="left" />
        <el-table-column label="阶段编码" align="center" prop="phaseCode" width="140" fixed="left">
          <template #default="{ row }">
            <el-link type="primary" @click="handleView(row)">{{ row.phaseCode }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="阶段名称" align="center" prop="phaseName" min-width="150" show-overflow-tooltip />
        <el-table-column label="所属项目" align="center" prop="projectName" min-width="150" show-overflow-tooltip />
        <el-table-column label="状态" align="center" prop="status" width="100">
          <template #default="{ row }">
            <el-tag
              :type="getStatusType(row.status)"
              size="small"
              effect="light"
            >
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="优先级" align="center" prop="priority" width="80">
          <template #default="{ row }">
            <el-tag
              :type="getPriorityType(row.priority)"
              size="small"
              effect="light"
            >
              {{ getPriorityText(row.priority) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="负责人" align="center" prop="assigneeName" width="100" />
        <el-table-column label="开始日期" align="center" prop="startTime" width="120">
          <template #default="{ row }">
            <span>{{ parseTime(row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="截止日期" align="center" prop="endTime" width="120">
          <template #default="{ row }">
            <span>{{ parseTime(row.endTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="进度" align="center" prop="progress" width="150">
          <template #default="{ row }">
            <el-progress
              :percentage="row.progress || 0"
              :color="getProgressColor(row.progress)"
              :stroke-width="8"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="260" fixed="right">
          <template #default="{ row }">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="handleUpdate(row)">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(row)">删除</el-button>
            <el-button size="mini" type="success" icon="el-icon-user" @click="handleTransfer(row)">转移</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
        style="margin-top: 20px;"
      />
    </el-card>

    <!-- 添加或修改任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="任务编码" prop="taskCode">
              <el-input v-model="form.taskCode" placeholder="请输入任务编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="taskName">
              <el-input v-model="form.taskName" placeholder="请输入任务名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属项目" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入所属项目" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="assigneeName">
              <el-input v-model="form.assigneeName" placeholder="请选择负责人" readonly @click="handleSelectUser">
                <i slot="suffix" class="el-icon-user" style="cursor: pointer;" @click="handleSelectUser"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始日期" prop="startTime">
              <el-date-picker
                v-model="form.startTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择开始日期"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="截止日期" prop="endTime">
              <el-date-picker
                v-model="form.endTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择截止日期"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优先级" prop="priority">
              <el-select v-model="form.priority" placeholder="请选择优先级" style="width: 100%;">
                <el-option label="高" value="high" />
                <el-option label="中" value="medium" />
                <el-option label="低" value="low" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%;">
                <el-option label="进行中" value="1" />
                <el-option label="已完成" value="3" />
                <el-option label="已暂停" value="4" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进度" prop="progress">
              <el-input-number v-model="form.progress" :min="0" :max="100" placeholder="请输入进度" style="width: 100%;" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="任务描述" prop="taskDesc">
              <el-input v-model="form.taskDesc" type="textarea" :rows="3" placeholder="请输入任务描述" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 选择负责人对话框 -->
    <el-dialog title="选择负责人" :visible.sync="userDialogVisible" width="600px" append-to-body>
      <el-form :model="userQueryParams" ref="userQueryForm" size="small" :inline="true">
        <el-form-item label="用户名称">
          <el-input
            v-model="userQueryParams.nickName"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleUserQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleUserQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetUserQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="userLoading" :data="userList" @row-click="handleSelectUserRow">
        <el-table-column label="用户名称" align="center" prop="nickName" />
        <el-table-column label="用户账号" align="center" prop="userName" />
      </el-table>
      <pagination
        v-show="userTotal>0"
        :total="userTotal"
        :page.sync="userQueryParams.pageNum"
        :limit.sync="userQueryParams.pageSize"
        @pagination="getUserList"
      />
    </el-dialog>
  </div>
</template>

<script>
import { listPhase, getPhase, delPhase, addPhase, updatePhase } from "@/api/system/phase"
import { listUser } from "@/api/system/user"
import Pagination from "@/components/Pagination/index.vue"

export default {
  name: "Task",
  components: { Pagination },
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
      // 用户列表
      userList: [],
      // 用户选择对话框
      userDialogVisible: false,
      // 用户列表加载状态
      userLoading: false,
      // 用户总数
      userTotal: 0,
      // 用户查询参数
      userQueryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: null
      },
      // 总条数
      total: 0,
      // 任务表格数据
      phaseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 统计数据
      statistics: {
        totalCount: 0,
        inProgressCount: 0,
        completedCount: 0,
        overdueCount: 0,
        highPriorityCount: 0
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: null,
        projectName: null,
        status: null,
        priority: null,
        assigneeName: null,
        sortType: 'start_desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskCode: [
          { required: true, message: "任务编码不能为空", trigger: "blur" }
        ],
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        assigneeName: [
          { required: true, message: "负责人不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "开始日期不能为空", trigger: "change" }
        ],
        endTime: [
          { required: true, message: "截止日期不能为空", trigger: "change" }
        ],
        priority: [
          { required: true, message: "优先级不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getStatistics()
    this.getUserList()
  },
  methods: {
    /** 查询任务列表 */
    getList() {
      this.loading = true
      // 处理日期范围
      if (this.dateRange && this.dateRange.length === 2) {
        this.queryParams.startTime = this.dateRange[0]
        this.queryParams.endTime = this.dateRange[1]
      } else {
        this.queryParams.startTime = null
        this.queryParams.endTime = null
      }

      listPhase(this.queryParams).then(response => {
        this.listPhase = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 获取统计数据 */
    getStatistics() {
      calcStatistics().then(response => {
        const data = response.data
        this.statistics = {
          totalCount: data.totalCount || 0,
          inProgressCount: data.inProgressCount || 0,
          completedCount: data.completedCount || 0,
          overdueCount: data.overdueCount || 0,
          highPriorityCount: data.highPriorityCount || 0
        }
      })
    },
    /** 获取用户列表 */
    getUserList() {
      listUser({ pageNum: 1, pageSize: 1000, status: '0' }).then(response => {
        this.userList = response.rows
      })
    },
    /** 选择用户行 */
    handleSelectUserRow(row) {
      this.form.assigneeId = row.userId
      this.form.assigneeName = row.nickName
      this.userDialogVisible = false
    },
    /** 获取进度条颜色 */
    getProgressColor(progress) {
      if (progress >= 100) return '#67C23A'
      if (progress >= 70) return '#409EFF'
      if (progress >= 30) return '#E6A23C'
      return '#F56C6C'
    },
    /** 获取状态文本 */
    getStatusText(status) {
      const statusMap = {
        '1': '进行中',
        '3': '已完成',
        '4': '已暂停'
      }
      return statusMap[status] || status
    },
    /** 获取状态标签类型 */
    getStatusType(status) {
      const statusMap = {
        '3': 'success',
        '1': 'primary',
        '4': 'info'
      }
      return statusMap[status] || 'info'
    },
    /** 获取优先级文本 */
    getPriorityText(priority) {
      const priorityMap = {
        'high': '高',
        'medium': '中',
        'low': '低'
      }
      return priorityMap[priority] || priority
    },
    /** 获取优先级标签类型 */
    getPriorityType(priority) {
      const priorityMap = {
        'high': 'danger',
        'medium': '',
        'low': 'info'
      }
      return priorityMap[priority] || 'info'
    },
    /** 选择用户 */
    handleSelectUser() {
      this.userDialogVisible = true
      this.getUserList()
    },
    /** 搜索用户 */
    handleUserQuery() {
      this.userQueryParams.pageNum = 1
      this.getUserList()
    },
    /** 重置用户搜索 */
    resetUserQuery() {
      this.resetForm("userQueryForm")
      this.handleUserQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 查看任务详情 */
    handleView(row) {
      this.$message.info('查看任务详情功能待实现')
    },
    /** 批量分配 */
    handleBatchAssign() {
      this.$message.info('批量分配功能待实现')
    },
    /** 转移任务 */
    handleTransfer(row) {
      this.$message.info('转移任务功能待实现')
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新建任务"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPhase(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑任务"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePhase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.getStatistics()
            })
          } else {
            addPhase(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
              this.getStatistics()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除任务编码为"' + row.taskCode + '"的数据项？').then(function() {
        return delPhase(ids)
      }).then(() => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 批量删除操作 */
    handleDeleteBatch() {
      this.$modal.confirm('是否确认删除选中的 ' + this.ids.length + ' 个任务？').then(() => {
        return delPhase(this.ids.join(','))
      }).then(() => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess("批量删除成功")
      }).catch(() => {})
    },
 /** 导出按钮操作 */
handleExport() {
  this.download('system/task/export', {
    ...this.queryParams
  }, `task_list_${new Date().getTime()}.xlsx`)
},
    /** 批量导入 */
    handleImport() {
      this.$message.info('批量导入功能待实现')
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
        taskCode: null,
        taskName: null,
        projectName: null,
        assigneeId: null,
        assigneeName: null,
        priority: 'medium',
        status: '1',
        startTime: null,
        endTime: null,
        progress: 0,
        taskDesc: null
      }
      this.resetForm("form")
    }
  }
}
</script>

<style scoped>.page-header {
  margin-bottom: 20px;
}

.page-title-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

.page-actions {
  display: flex;
  gap: 10px;
}

.page-actions .el-button {
  margin-left: 10px;
}

.statistics-cards {
  margin-bottom: 20px;
}

.stat-card {
  border-radius: 8px;
  text-align: center;
  padding: 20px 0;
}

.stat-content {
  padding: 10px 0;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
}

.stat-value.primary {
  color: #409EFF;
}

.stat-value.success {
  color: #67C23A;
}

.stat-value.warning {
  color: #E6A23C;
}

.stat-value.danger {
  color: #F56C6C;
}

.stat-value.info {
  color: #909399;
}

.search-card {
  margin-bottom: 20px;
  border-radius: 8px;
}

.search-card .el-form {
  margin-bottom: 0;
}

.search-card .el-form-item {
  margin-bottom: 16px;
}

.toolbar {
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.view-tabs .el-button-group .el-button {
  padding: 8px 16px;
}

.batch-actions .el-button {
  margin-left: 10px;
}

.table-card {
  border-radius: 8px;
}

.table-card .el-table {
  font-size: 13px;
}

.table-card .el-table th {
  background-color: #F5F7FA;
  color: #606266;
  font-weight: 500;
}

.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  margin-left: 10px;
}
</style>
