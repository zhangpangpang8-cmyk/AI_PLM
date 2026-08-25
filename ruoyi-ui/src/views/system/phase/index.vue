<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="阶段编码" prop="phaseCode">
        <el-input v-model="queryParams.phaseCode" placeholder="请输入阶段编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="阶段名称" prop="phaseName">
        <el-input v-model="queryParams.phaseName" placeholder="请输入阶段名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="所属项目" prop="projectId">
        <el-select v-model="queryParams.projectId" placeholder="请选择项目" clearable filterable>
          <el-option v-for="item in projectOptions" :key="item.id" :label="item.projectName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="阶段类型" prop="phaseType">
        <el-select v-model="queryParams.phaseType" placeholder="请选择类型" clearable>
          <el-option v-for="item in phaseTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="阶段状态" prop="phaseStatus">
        <el-select v-model="queryParams.phaseStatus" placeholder="请选择状态" clearable>
          <el-option v-for="item in phaseStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:phase:add']" type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:phase:edit']" type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:phase:remove']" type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:phase:export']" type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-row :gutter="12" class="phase-summary">
      <el-col v-for="item in summaryCards" :key="item.label" :xs="12" :sm="6">
        <el-card shadow="never">
          <div class="summary-label">{{ item.label }}</div>
          <div class="summary-value" :class="item.type">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="phaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="阶段编码" prop="phaseCode" min-width="120" show-overflow-tooltip />
      <el-table-column label="阶段名称" prop="phaseName" min-width="150" show-overflow-tooltip />
      <el-table-column label="所属项目" min-width="160" show-overflow-tooltip>
        <template slot-scope="scope">{{ getProjectName(scope.row.projectId) }}</template>
      </el-table-column>
      <el-table-column label="阶段类型" prop="phaseType" width="100" align="center">
        <template slot-scope="scope">{{ optionLabel(phaseTypeOptions, scope.row.phaseType) }}</template>
      </el-table-column>
      <el-table-column label="状态" prop="phaseStatus" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.phaseStatus)">{{ optionLabel(phaseStatusOptions, scope.row.phaseStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="计划开始" prop="planStartDate" width="110" align="center" />
      <el-table-column label="计划结束" prop="planEndDate" width="110" align="center" />
      <el-table-column label="实际开始" prop="actualStartDate" width="110" align="center" />
      <el-table-column label="实际结束" prop="actualEndDate" width="110" align="center" />
      <el-table-column label="排序" prop="sortOrder" width="70" align="center" />
      <el-table-column label="操作" width="150" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button v-hasPermi="['system:phase:edit']" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button v-hasPermi="['system:phase:remove']" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="760px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属项目" prop="projectId">
              <el-select v-model="form.projectId" placeholder="请选择项目" filterable style="width: 100%">
                <el-option v-for="item in projectOptions" :key="item.id" :label="item.projectName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阶段编码" prop="phaseCode">
              <el-input v-model="form.phaseCode" placeholder="请输入阶段编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阶段名称" prop="phaseName">
              <el-input v-model="form.phaseName" placeholder="请输入阶段名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阶段类型" prop="phaseType">
              <el-select v-model="form.phaseType" placeholder="请选择类型" style="width: 100%">
                <el-option v-for="item in phaseTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阶段状态" prop="phaseStatus">
              <el-select v-model="form.phaseStatus" placeholder="请选择状态" style="width: 100%">
                <el-option v-for="item in phaseStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阶段排序" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="计划开始" prop="planStartDate"><el-date-picker v-model="form.planStartDate" type="date" value-format="yyyy-MM-dd" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="计划结束" prop="planEndDate"><el-date-picker v-model="form.planEndDate" type="date" value-format="yyyy-MM-dd" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="实际开始" prop="actualStartDate"><el-date-picker v-model="form.actualStartDate" type="date" value-format="yyyy-MM-dd" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="实际结束" prop="actualEndDate"><el-date-picker v-model="form.actualEndDate" type="date" value-format="yyyy-MM-dd" style="width: 100%" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="阶段交付物" prop="deliverables"><el-input v-model="form.deliverables" type="textarea" :rows="2" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="进入条件" prop="entryCriteria"><el-input v-model="form.entryCriteria" type="textarea" :rows="2" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="退出条件" prop="exitCriteria"><el-input v-model="form.exitCriteria" type="textarea" :rows="2" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="备注" prop="remark"><el-input v-model="form.remark" type="textarea" :rows="2" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPhase, getPhase, delPhase, addPhase, updatePhase } from '@/api/system/phase'
import { listOverview } from '@/api/system/overview'

const PHASE_TYPE_OPTIONS = [
  { label: '立项', value: 'initiation' },
  { label: '计划', value: 'planning' },
  { label: '执行', value: 'execution' },
  { label: '监控', value: 'monitoring' },
  { label: '结项', value: 'closure' }
]

const PHASE_STATUS_OPTIONS = [
  { label: '未开始', value: 'not_started' },
  { label: '进行中', value: 'in_progress' },
  { label: '已完成', value: 'completed' }
]

export default {
  name: 'PjPhase',
  data() {
    return {
      loading: true,
      showSearch: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      phaseList: [],
      projectOptions: [],
      title: '',
      open: false,
      phaseTypeOptions: PHASE_TYPE_OPTIONS,
      phaseStatusOptions: PHASE_STATUS_OPTIONS,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: null,
        phaseCode: null,
        phaseName: null,
        phaseType: null,
        phaseStatus: null
      },
      form: {},
      rules: {
        projectId: [{ required: true, message: '所属项目不能为空', trigger: 'change' }],
        phaseCode: [{ required: true, message: '阶段编码不能为空', trigger: 'blur' }],
        phaseName: [{ required: true, message: '阶段名称不能为空', trigger: 'blur' }],
        phaseType: [{ required: true, message: '阶段类型不能为空', trigger: 'change' }],
        phaseStatus: [{ required: true, message: '阶段状态不能为空', trigger: 'change' }],
        planEndDate: [{ validator: (rule, value, callback) => this.validateDateRange('planStartDate', value, callback), trigger: 'change' }],
        actualEndDate: [{ validator: (rule, value, callback) => this.validateDateRange('actualStartDate', value, callback), trigger: 'change' }]
      }
    }
  },
  computed: {
    summaryCards() {
      const today = new Date().toISOString().slice(0, 10)
      return [
        { label: '当前页阶段', value: this.phaseList.length, type: 'primary' },
        { label: '未开始', value: this.phaseList.filter(item => item.phaseStatus === 'not_started').length, type: 'info' },
        { label: '进行中', value: this.phaseList.filter(item => item.phaseStatus === 'in_progress').length, type: 'warning' },
        { label: '已完成', value: this.phaseList.filter(item => item.phaseStatus === 'completed').length, type: 'success' },
        { label: '已逾期', value: this.phaseList.filter(item => item.phaseStatus !== 'completed' && item.planEndDate && item.planEndDate < today).length, type: 'danger' }
      ]
    }
  },
  created() {
    this.getProjects()
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listPhase(this.queryParams).then(response => {
        this.phaseList = response.rows || []
        this.total = response.total || 0
      }).finally(() => {
        this.loading = false
      })
    },
    getProjects() {
      listOverview({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.projectOptions = response.rows || []
      })
    },
    getProjectName(projectId) {
      const project = this.projectOptions.find(item => String(item.id) === String(projectId))
      return project ? project.projectName : projectId || '-'
    },
    optionLabel(options, value) {
      const option = options.find(item => item.value === value)
      return option ? option.label : value || '-'
    },
    statusTagType(status) {
      return { not_started: 'info', in_progress: 'warning', completed: 'success' }[status] || 'info'
    },
    validateDateRange(startField, endValue, callback) {
      const startValue = this.form[startField]
      if (startValue && endValue && endValue < startValue) {
        callback(new Error('结束日期不能早于开始日期'))
      } else {
        callback()
      }
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = selection.length === 0
    },
    reset() {
      this.form = {
        id: null,
        projectId: null,
        phaseCode: null,
        phaseName: null,
        phaseType: 'planning',
        planStartDate: null,
        planEndDate: null,
        actualStartDate: null,
        actualEndDate: null,
        phaseStatus: 'not_started',
        deliverables: null,
        entryCriteria: null,
        exitCriteria: null,
        sortOrder: 0,
        remark: null
      }
      this.resetForm('form')
    },
    handleAdd() {
      this.reset()
      this.title = '新增项目阶段'
      this.open = true
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      getPhase(id).then(response => {
        this.form = response.data
        this.title = '修改项目阶段'
        this.open = true
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) return
        const request = this.form.id == null ? addPhase(this.form) : updatePhase(this.form)
        request.then(() => {
          this.$modal.msgSuccess(this.form.id == null ? '新增成功' : '修改成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      const label = row.phaseCode || this.ids.join(',')
      this.$modal.confirm(`是否确认删除阶段“${label}”？`).then(() => delPhase(ids)).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    handleExport() {
      this.download('system/phase/export', { ...this.queryParams }, `项目阶段_${Date.now()}.xlsx`)
    },
    cancel() {
      this.open = false
      this.reset()
    }
  }
}
</script>

<style scoped>
.phase-summary {
  margin-bottom: 16px;
}
.summary-label {
  color: #909399;
  font-size: 13px;
}
.summary-value {
  margin-top: 8px;
  font-size: 24px;
  font-weight: 600;
}
.summary-value.primary { color: #409eff; }
.summary-value.info { color: #909399; }
.summary-value.warning { color: #e6a23c; }
.summary-value.success { color: #67c23a; }
.summary-value.danger { color: #f56c6c; }
</style>
