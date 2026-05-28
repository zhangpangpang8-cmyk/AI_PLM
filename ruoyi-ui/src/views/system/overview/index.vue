<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目编码" prop="projectCode">
        <el-input
          v-model="queryParams.projectCode"
          placeholder="请输入项目编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目分类id" prop="classificationId">
        <el-input
          v-model="queryParams.classificationId"
          placeholder="请输入项目分类id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目分类名称" prop="classificationName">
        <el-input
          v-model="queryParams.classificationName"
          placeholder="请输入项目分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目经理id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入项目经理id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目经理名称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入项目经理名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目开始日期" prop="startTime">
        <el-date-picker clearable
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择项目开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="项目结束日期" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择项目结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="项目进度" prop="projectProgress">
        <el-input
          v-model="queryParams.projectProgress"
          placeholder="请输入项目进度"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请流程" prop="flowKey">
        <el-input
          v-model="queryParams.flowKey"
          placeholder="请输入申请流程"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程实例id" prop="flowInsId">
        <el-input
          v-model="queryParams.flowInsId"
          placeholder="请输入流程实例id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="优先级" prop="priority">
        <el-input
          v-model="queryParams.priority"
          placeholder="请输入优先级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划开始日期" prop="planStartTime">
        <el-date-picker clearable
          v-model="queryParams.planStartTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择计划开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="计划结束日期" prop="planEndTime">
        <el-date-picker clearable
          v-model="queryParams.planEndTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择计划结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际开始日期" prop="actualStartTime">
        <el-date-picker clearable
          v-model="queryParams.actualStartTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际结束日期" prop="actualEndTime">
        <el-date-picker clearable
          v-model="queryParams.actualEndTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="项目预算" prop="budget">
        <el-input
          v-model="queryParams.budget"
          placeholder="请输入项目预算"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际成本" prop="actualCost">
        <el-input
          v-model="queryParams.actualCost"
          placeholder="请输入实际成本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成率" prop="completionRate">
        <el-input
          v-model="queryParams.completionRate"
          placeholder="请输入完成率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结项时间" prop="closeTime">
        <el-date-picker clearable
          v-model="queryParams.closeTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择结项时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:overview:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:overview:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:overview:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:overview:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="overviewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="项目编码" align="center" prop="projectCode" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目分类id" align="center" prop="classificationId" />
      <el-table-column label="项目分类名称" align="center" prop="classificationName" />
      <el-table-column label="项目经理id" align="center" prop="userId" />
      <el-table-column label="项目经理名称" align="center" prop="nickName" />
      <el-table-column label="项目状态" align="center" prop="status" />
      <el-table-column label="项目开始日期" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目结束日期" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目进度" align="center" prop="projectProgress" />
      <el-table-column label="项目描述" align="center" prop="projectInfo" />
      <el-table-column label="申请流程" align="center" prop="flowKey" />
      <el-table-column label="流程实例id" align="center" prop="flowInsId" />
      <el-table-column label="单据类型" align="center" prop="billType" />
      <el-table-column label="优先级" align="center" prop="priority" />
      <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column label="计划开始日期" align="center" prop="planStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划结束日期" align="center" prop="planEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际开始日期" align="center" prop="actualStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束日期" align="center" prop="actualEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actualEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目预算" align="center" prop="budget" />
      <el-table-column label="实际成本" align="center" prop="actualCost" />
      <el-table-column label="完成率" align="center" prop="completionRate" />
      <el-table-column label="健康度" align="center" prop="healthStatus" />
      <el-table-column label="结项时间" align="center" prop="closeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.closeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结项说明" align="center" prop="closeReason" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:overview:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:overview:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改项目概述对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目编码" prop="projectCode">
              <el-input v-model="form.projectCode" placeholder="请输入项目编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目名称" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入项目名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目分类id" prop="classificationId">
              <el-input v-model="form.classificationId" placeholder="请输入项目分类id" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目分类名称" prop="classificationName">
              <el-input v-model="form.classificationName" placeholder="请输入项目分类名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目经理id" prop="userId">
              <el-input v-model="form.userId" placeholder="请输入项目经理id" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目经理名称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入项目经理名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目开始日期" prop="startTime">
              <el-date-picker clearable
                v-model="form.startTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择项目开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目结束日期" prop="endTime">
              <el-date-picker clearable
                v-model="form.endTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择项目结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目进度" prop="projectProgress">
              <el-input v-model="form.projectProgress" placeholder="请输入项目进度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目描述" prop="projectInfo">
              <el-input v-model="form.projectInfo" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="申请流程" prop="flowKey">
              <el-input v-model="form.flowKey" placeholder="请输入申请流程" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="流程实例id" prop="flowInsId">
              <el-input v-model="form.flowInsId" placeholder="请输入流程实例id" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="优先级" prop="priority">
              <el-input v-model="form.priority" placeholder="请输入优先级" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="计划开始日期" prop="planStartTime">
              <el-date-picker clearable
                v-model="form.planStartTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择计划开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="计划结束日期" prop="planEndTime">
              <el-date-picker clearable
                v-model="form.planEndTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择计划结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="实际开始日期" prop="actualStartTime">
              <el-date-picker clearable
                v-model="form.actualStartTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择实际开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="实际结束日期" prop="actualEndTime">
              <el-date-picker clearable
                v-model="form.actualEndTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择实际结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目预算" prop="budget">
              <el-input v-model="form.budget" placeholder="请输入项目预算" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="实际成本" prop="actualCost">
              <el-input v-model="form.actualCost" placeholder="请输入实际成本" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="完成率" prop="completionRate">
              <el-input v-model="form.completionRate" placeholder="请输入完成率" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="结项时间" prop="closeTime">
              <el-date-picker clearable
                v-model="form.closeTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择结项时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="结项说明" prop="closeReason">
              <el-input v-model="form.closeReason" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
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
import { listOverview, getOverview, delOverview, addOverview, updateOverview } from "@/api/system/overview"

export default {
  name: "Overview",
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectCode: null,
        projectName: null,
        classificationId: null,
        classificationName: null,
        userId: null,
        nickName: null,
        status: null,
        startTime: null,
        endTime: null,
        projectProgress: null,
        projectInfo: null,
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
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectCode: [
          { required: true, message: "项目编码不能为空", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        classificationId: [
          { required: true, message: "项目分类id不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "项目经理id不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "项目开始日期不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "项目结束日期不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询项目概述列表 */
    getList() {
      this.loading = true
      listOverview(this.queryParams).then(response => {
        this.overviewList = response.rows
        this.total = response.total
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
      this.form = {
        id: null,
        projectCode: null,
        projectName: null,
        classificationId: null,
        classificationName: null,
        userId: null,
        nickName: null,
        status: null,
        startTime: null,
        endTime: null,
        projectProgress: null,
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
      this.title = "添加项目概述"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getOverview(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改项目概述"
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
            })
          } else {
            addOverview(this.form).then(response => {
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
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除项目概述编号为"' + ids + '"的数据项？').then(function() {
        return delOverview(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/overview/export', {
        ...this.queryParams
      }, `overview_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
