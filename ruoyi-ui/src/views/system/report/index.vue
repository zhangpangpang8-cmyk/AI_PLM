<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="测试编码" prop="testCode">
        <el-input
          v-model="queryParams.testCode"
          placeholder="请输入测试编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="bomId" prop="bomId">
        <el-input
          v-model="queryParams.bomId"
          placeholder="请输入bomId"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="bom名称" prop="bomName">
        <el-input
          v-model="queryParams.bomName"
          placeholder="请输入bom名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="bom编码" prop="bomCode">
        <el-input
          v-model="queryParams.bomCode"
          placeholder="请输入bom编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试日期" prop="testDate">
        <el-date-picker clearable
          v-model="queryParams.testDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择测试日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="测试人员" prop="testers">
        <el-input
          v-model="queryParams.testers"
          placeholder="请输入测试人员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试环境" prop="testEnvironment">
        <el-input
          v-model="queryParams.testEnvironment"
          placeholder="请输入测试环境"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试目的" prop="testPurpose">
        <el-input
          v-model="queryParams.testPurpose"
          placeholder="请输入测试目的"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="硬件" prop="hardware">
        <el-input
          v-model="queryParams.hardware"
          placeholder="请输入硬件"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="软件" prop="software">
        <el-input
          v-model="queryParams.software"
          placeholder="请输入软件"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试工具" prop="testTools">
        <el-input
          v-model="queryParams.testTools"
          placeholder="请输入测试工具"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结论与建议" prop="inConclusion">
        <el-input
          v-model="queryParams.inConclusion"
          placeholder="请输入结论与建议"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试结果" prop="actualResults">
        <el-input
          v-model="queryParams.actualResults"
          placeholder="请输入测试结果"
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
          v-hasPermi="['system:report:add']"
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
          v-hasPermi="['system:report:edit']"
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
          v-hasPermi="['system:report:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:report:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="测试编码" align="center" prop="testCode" />
      <el-table-column label="bomId" align="center" prop="bomId" />
      <el-table-column label="bom名称" align="center" prop="bomName" />
      <el-table-column label="bom编码" align="center" prop="bomCode" />
      <el-table-column label="测试类型" align="center" prop="testType" />
      <el-table-column label="测试日期" align="center" prop="testDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.testDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="测试人员" align="center" prop="testers" />
      <el-table-column label="测试环境" align="center" prop="testEnvironment" />
      <el-table-column label="测试目的" align="center" prop="testPurpose" />
      <el-table-column label="硬件" align="center" prop="hardware" />
      <el-table-column label="软件" align="center" prop="software" />
      <el-table-column label="测试工具" align="center" prop="testTools" />
      <el-table-column label="结论与建议" align="center" prop="inConclusion" />
      <el-table-column label="测试结果" align="center" prop="actualResults" />
      <el-table-column label="申请流程" align="center" prop="flowKey" />
      <el-table-column label="流程实例id" align="center" prop="flowInsId" />
      <el-table-column label="单据类型" align="center" prop="billType" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:report:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:report:remove']"
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

    <!-- 添加或修改测试报告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="测试编码" prop="testCode">
              <el-input v-model="form.testCode" placeholder="请输入测试编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="bomId" prop="bomId">
              <el-input v-model="form.bomId" placeholder="请输入bomId" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="bom名称" prop="bomName">
              <el-input v-model="form.bomName" placeholder="请输入bom名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="bom编码" prop="bomCode">
              <el-input v-model="form.bomCode" placeholder="请输入bom编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="测试日期" prop="testDate">
              <el-date-picker clearable
                v-model="form.testDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择测试日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="测试人员" prop="testers">
              <el-input v-model="form.testers" placeholder="请输入测试人员" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="测试环境" prop="testEnvironment">
              <el-input v-model="form.testEnvironment" placeholder="请输入测试环境" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="测试目的" prop="testPurpose">
              <el-input v-model="form.testPurpose" placeholder="请输入测试目的" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="硬件" prop="hardware">
              <el-input v-model="form.hardware" placeholder="请输入硬件" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="软件" prop="software">
              <el-input v-model="form.software" placeholder="请输入软件" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="测试工具" prop="testTools">
              <el-input v-model="form.testTools" placeholder="请输入测试工具" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="结论与建议" prop="inConclusion">
              <el-input v-model="form.inConclusion" placeholder="请输入结论与建议" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="测试结果" prop="actualResults">
              <el-input v-model="form.actualResults" placeholder="请输入测试结果" />
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
import { listReport, getReport, delReport, addReport, updateReport } from "@/api/system/report"

export default {
  name: "Report",
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
      // 测试报告表格数据
      reportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        testCode: null,
        bomId: null,
        bomName: null,
        bomCode: null,
        testType: null,
        testDate: null,
        testers: null,
        testEnvironment: null,
        testPurpose: null,
        hardware: null,
        software: null,
        testTools: null,
        inConclusion: null,
        actualResults: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        testCode: [
          { required: true, message: "测试编码不能为空", trigger: "blur" }
        ],
        bomId: [
          { required: true, message: "bomId不能为空", trigger: "blur" }
        ],
        bomName: [
          { required: true, message: "bom名称不能为空", trigger: "blur" }
        ],
        bomCode: [
          { required: true, message: "bom编码不能为空", trigger: "blur" }
        ],
        testType: [
          { required: true, message: "测试类型不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询测试报告列表 */
    getList() {
      this.loading = true
      listReport(this.queryParams).then(response => {
        this.reportList = response.rows
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
        testCode: null,
        bomId: null,
        bomName: null,
        bomCode: null,
        testType: null,
        testDate: null,
        testers: null,
        testEnvironment: null,
        testPurpose: null,
        hardware: null,
        software: null,
        testTools: null,
        inConclusion: null,
        actualResults: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        status: null
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
      this.title = "添加测试报告"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getReport(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改测试报告"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addReport(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除测试报告编号为"' + ids + '"的数据项？').then(function() {
        return delReport(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/report/export', {
        ...this.queryParams
      }, `report_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
