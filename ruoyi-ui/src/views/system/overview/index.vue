<template>
  <div class="app-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="page-title-row">
        <h2 class="page-title">项目概况</h2>
        <div class="page-actions">
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新建项目</el-button>
          <el-button type="primary" icon="el-icon-download" @click="handleExport">导出数据</el-button>
        </div>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards">
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-label">审核中的项目</div>
              <div class="stat-value auditing">{{ statistics.auditingCount }}</div>
              <div class="stat-trend">
                <i class="el-icon-success" style="color: #67C23A;"></i>
                本月新增项目 {{ statistics.newThisMonth }}个
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-label">项目总进度</div>
              <div class="stat-value progress">{{ statistics.totalProgress }}%</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-label">风险项目数</div>
              <div class="stat-value risk">{{ statistics.riskCount }}</div>
              <div class="stat-trend risk-trend">
                <i class="el-icon-warning" style="color: #F56C6C;"></i>
                需要关注的风险项目
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 搜索表单 -->
    <el-card shadow="never" class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="auto">
        <el-form-item>
          <el-input
            v-model="queryParams.projectName"
            placeholder="请输入项目名称/编号"
            prefix-icon="el-icon-search"
            clearable
            @keyup.enter.native="handleQuery"
            style="width: 220px;"
          />
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.status" placeholder="请选择项目状态" clearable style="width: 180px;">
            <el-option label="已完成" value="3"/>
            <el-option label="审核中" value="2"/>
            <el-option label="进行中" value="1"/>
            <el-option label="已暂停" value="4"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.classificationName" placeholder="请选择项目分类" clearable
                     style="width: 180px;">
            <el-option label="研制生产类" value="研制生产类"/>
            <el-option label="技术开发类" value="技术开发类"/>
            <el-option label="服务支持类" value="服务支持类"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.nickName" placeholder="请选择项目经理" filterable clearable
                     style="width: 180px;">
            <el-option
              v-for="user in userList"
              :key="user.userId"
              :label="user.nickName"
              :value="user.nickName"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="-"
            start-placeholder="开始时间范围"
            end-placeholder="结束时间范围"
            value-format="yyyy-MM-dd"
            style="width: 260px;"
          />
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.sortType" placeholder="开始日期-逆序" clearable style="width: 180px;">
            <el-option label="开始日期-正序" value="start_asc"/>
            <el-option label="开始日期-逆序" value="start_desc"/>
            <el-option label="进度-正序" value="progress_asc"/>
            <el-option label="进度-逆序" value="progress_desc"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 数据表格 -->
    <el-card shadow="never" class="table-card">
      <el-table v-loading="loading" :data="overviewList" stripe style="width: 100%;">
        <el-table-column label="项目编号" align="center" prop="projectCode" width="120" fixed="left">
          <template slot-scope="scope">
            <el-link type="primary" @click="handleView(scope.row)">{{ scope.row.projectCode }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="项目名称" align="center" prop="projectName" min-width="180" show-overflow-tooltip/>
        <el-table-column label="项目分类" align="center" prop="classificationName" width="120">
          <template slot-scope="scope">
            <span>{{ getClassificationText(scope.row.classificationId || scope.row.classificationName) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="项目经理" align="center" prop="nickName" width="100"/>
        <el-table-column label="项目进度" align="center" prop="projectProgress" width="180">
          <template slot-scope="scope">
            <el-progress
              :percentage="scope.row.projectProgress || 0"
              :color="getProgressColor(scope.row.projectProgress)"
              :stroke-width="12"
            />
          </template>
        </el-table-column>
        <el-table-column label="开始日期" align="center" prop="startTime" width="120">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="计划结束时间" align="center" prop="planEndTime" width="120">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status" width="100">
          <template slot-scope="scope">
            <el-tag
              :type="getStatusType(scope.row.status)"
              size="small"
              effect="light"
            >
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审核状态" align="center" prop="auditStatus" width="100">
          <template slot-scope="scope">
            <el-tag
              :type="getAuditStatusType(scope.row.auditStatus)"
              size="small"
              effect="light"
            >
              {{ getAuditStatusText(scope.row.auditStatus) }}
            </el-tag>
          </template>
          </el-table-column>
        <el-table-column label="创建人" align="center" prop="createBy" width="100"/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="160">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="320" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button size="mini" type="success" icon="el-icon-s-operation" @click="handleGantt(scope.row)">甘特图</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
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

    <!-- 添加或修改项目概述对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目编码" prop="projectCode">
              <el-input v-model="form.projectCode" placeholder="请输入项目编码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入项目名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目分类" prop="classificationName">
              <el-select v-model="form.classificationName" placeholder="请选择项目分类" style="width: 100%;"
                         @change="handleClassificationChange">
                <el-option label="研制生产类" value="1"/>
                <el-option label="技术开发类" value="2"/>
                <el-option label="服务支持类" value="3"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目经理" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请选择项目经理" readonly @click="handleSelectUser">
                <i slot="suffix" class="el-icon-user" style="cursor: pointer;" @click="handleSelectUser"></i>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目开始日期" prop="startTime">
              <el-date-picker
                v-model="form.startTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择项目开始日期"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目结束日期" prop="endTime">
              <el-date-picker
                v-model="form.endTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择项目结束日期"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目进度" prop="projectProgress">
              <el-input-number v-model="form.projectProgress" :min="0" :max="100" placeholder="请输入项目进度"
                               style="width: 100%;"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择项目状态" style="width: 100%;">
                <el-option label="进行中" value="1"/>
                <el-option label="审核中" value="2"/>
                <el-option label="已完成" value="3"/>
                <el-option label="已暂停" value="4"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目描述" prop="projectInfo">
              <el-input v-model="form.projectInfo" type="textarea" :rows="3" placeholder="请输入项目描述"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="选择项目经理" :visible.sync="userDialogVisible" width="600px" append-to-body>
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
        <el-table-column label="用户名称" align="center" prop="nickName"/>
        <el-table-column label="用户账号" align="center" prop="userName"/>
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
import {listOverview, getOverview, delOverview, addOverview, updateOverview, getStatistics} from "@/api/system/overview"
import {listUser} from "@/api/system/user"
import Pagination from "../../../components/Pagination/index.vue";

export default {
  name: "Overview",
  components: {Pagination},
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 项目概述表格数据
      overviewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 统计数据
      statistics: {
        auditingCount: 0,
        totalProgress: 0,
        riskCount: 0,
        newThisMonth: 0
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectCode: null,
        projectName: null,
        classificationName: null,
        nickName: null,
        status: null,
        sortType: 'start_desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectCode: [
          {required: true, message: "项目编码不能为空", trigger: "blur"}
        ],
        projectName: [
          {required: true, message: "项目名称不能为空", trigger: "blur"}
        ],
        classificationName: [
          {required: true, message: "项目分类不能为空", trigger: "change"}
        ],
        nickName: [
          {required: true, message: "项目经理不能为空", trigger: "blur"}
        ],
        startTime: [
          {required: true, message: "项目开始日期不能为空", trigger: "change"}
        ],
        endTime: [
          {required: true, message: "项目结束日期不能为空", trigger: "change"}
        ],
        status: [
          {required: true, message: "项目状态不能为空", trigger: "change"}
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
    /** 获取用户列表 */
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
    /** 选择用户行 */
    handleSelectUserRow(row) {
      this.form.userId = row.userId
      this.form.nickName = row.nickName
      this.userDialogVisible = false
    },
    /** 获取用户列表 */
    getUserList() {
      listUser({pageNum: 1, pageSize: 1000, status: '0'}).then(response => {
        this.userList = response.rows
      })
    },
    /** 获取统计数据 */
    getStatistics() {
      getStatistics().then(response => {
        const data = response.data
        this.statistics = {
          auditingCount: data.auditingCount || 0,
          newThisMonth: data.newThisMonth || 0,
          totalProgress: data.totalProgress ? parseFloat(data.totalProgress).toFixed(2) : 0,
          riskCount: data.riskCount || 0
        }
      })
    },
    /** 查询项目概述列表 */
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

      listOverview(this.queryParams).then(response => {
        this.overviewList = response.rows
        this.total = response.total
        this.loading = false
      })
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
        '2': '审核中',
        '3': '已完成',
        '4': '已暂停'
      }
      return statusMap[status] || status
    },
    /** 获取状态标签类型 */
    getStatusType(status) {
      const statusMap = {
        '3': 'success',
        '2': 'warning',
        '1': 'primary',
        '4': 'info'
      }
      return statusMap[status] || 'info'
    },
    getClassificationText(classification) {
      const classificationMap = {
        '1': '研制生产类',
        '2': '技术开发类',
        '3': '服务支持类'
      }
      return classificationMap[classification] || classification
    },

    /** 获取审核状态标签类型 */
    getAuditStatusText(auditStatus) {
      const auditStatusMap = {
        '0': '待审核',
        '1': '审核通过',
        '2': '审核驳回'
      }
      return auditStatusMap[auditStatus] || auditStatus
    },
    //** 获取审核状态标签类型 */
    getAuditStatusType(auditStatus) {
      const auditStatusMap = {
        '0': 'info',
        '1': 'success',
        '2': 'danger'
      }
      return auditStatusMap[auditStatus] || 'info'
    },
    /** 项目分类变化时自动设置分类ID */
    handleClassificationChange(value) {
      if (value) {
        this.form.classificationId = parseInt(value)
      } else {
        this.form.classificationId = null
      }
    },
    /** 项目经理变化时自动设置用户ID */
    handleUserChange(value) {
      const selectedUser = this.userList.find(user => user.nickName === value)
      if (selectedUser) {
        this.form.userId = selectedUser.userId
      } else {
        this.form.userId = null
      }
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
        projectCode: null,
        projectName: null,
        classificationId: null,
        classificationName: null,
        userId: null,
        nickName: null,
        status: '1',
        startTime: null,
        endTime: null,
        projectProgress: 0,
        projectInfo: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        priority: null,
        projectType: null,
        planStartTime: null,
        planEndTime: null,
        actualStartTime: null,
        actualEndTime: null,
        budget: null,
        actualCost: null,
        completionRate: null,
        healthStatus: null,
        closeTime: null,
        closeReason: null
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
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 查看项目详情 */
    handleView(row) {
      this.$message.info('查看项目详情功能待实现')
    },
    /** 甘特图 */
    handleGantt(row) {
      this.$message.info('甘特图功能待实现')
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新建项目"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getOverview(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "编辑项目"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOverview(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.getStatistics()
            })
          } else {
            addOverview(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除项目编号为"' + row.projectCode + '"的数据项？').then(function () {
        return delOverview(ids)
      }).then(() => {
        this.getList()
        this.getStatistics()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/overview/export', {
        ...this.queryParams
      }, `project_overview_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.page-header {
  margin-bottom: 20px;
}

.page-header {
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
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 8px;
}

.stat-value.auditing {
  color: #409EFF;
}

.stat-value.progress {
  color: #409EFF;
}

.stat-value.risk {
  color: #67C23A;
}

.stat-trend {
  font-size: 12px;
  color: #606266;
}

.stat-trend.risk-trend {
  color: #F56C6C;
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
