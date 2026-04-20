<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图纸编号" prop="drawingCode">
        <el-input
          v-model="queryParams.drawingCode"
          placeholder="请输入图纸编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图纸名称" prop="drawingName">
        <el-input
          v-model="queryParams.drawingName"
          placeholder="请输入图纸名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图纸版本" prop="drawingVersion">
        <el-input
          v-model="queryParams.drawingVersion"
          placeholder="请输入图纸版本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否最新版本" prop="latestVersion">
        <el-input
          v-model="queryParams.latestVersion"
          placeholder="请输入是否最新版本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目id" prop="projectId">
        <el-input
          v-model="queryParams.projectId"
          placeholder="请输入项目id"
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
      <el-form-item label="项目编码" prop="projectCode">
        <el-input
          v-model="queryParams.projectCode"
          placeholder="请输入项目编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目任务id" prop="projectTaskId">
        <el-input
          v-model="queryParams.projectTaskId"
          placeholder="请输入项目任务id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目任务编码" prop="projectTaskCode">
        <el-input
          v-model="queryParams.projectTaskCode"
          placeholder="请输入项目任务编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目任务名称" prop="projectTaskName">
        <el-input
          v-model="queryParams.projectTaskName"
          placeholder="请输入项目任务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变更id" prop="ecoId">
        <el-input
          v-model="queryParams.ecoId"
          placeholder="请输入变更id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变更标题" prop="ecoTitle">
        <el-input
          v-model="queryParams.ecoTitle"
          placeholder="请输入变更标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变更编码" prop="ecoCode">
        <el-input
          v-model="queryParams.ecoCode"
          placeholder="请输入变更编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变更说明" prop="ecoDescription">
        <el-input
          v-model="queryParams.ecoDescription"
          placeholder="请输入变更说明"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件后缀" prop="fileSuffix">
        <el-input
          v-model="queryParams.fileSuffix"
          placeholder="请输入文件后缀"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件大小" prop="fileSize">
        <el-input
          v-model="queryParams.fileSize"
          placeholder="请输入文件大小"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件路径" prop="filePath">
        <el-input
          v-model="queryParams.filePath"
          placeholder="请输入文件路径"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件大小" prop="size">
        <el-input
          v-model="queryParams.size"
          placeholder="请输入文件大小"
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
      <el-form-item label="能否进行变更" prop="allowChange">
        <el-input
          v-model="queryParams.allowChange"
          placeholder="请输入能否进行变更"
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
          v-hasPermi="['system:drawing:add']"
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
          v-hasPermi="['system:drawing:edit']"
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
          v-hasPermi="['system:drawing:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:drawing:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="drawingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="图纸编号" align="center" prop="drawingCode" />
      <el-table-column label="图纸名称" align="center" prop="drawingName" />
      <el-table-column label="图纸类型" align="center" prop="drawingType" />
      <el-table-column label="图纸版本" align="center" prop="drawingVersion" />
      <el-table-column label="是否最新版本" align="center" prop="latestVersion" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="项目id" align="center" prop="projectId" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目编码" align="center" prop="projectCode" />
      <el-table-column label="项目任务id" align="center" prop="projectTaskId" />
      <el-table-column label="项目任务编码" align="center" prop="projectTaskCode" />
      <el-table-column label="项目任务名称" align="center" prop="projectTaskName" />
      <el-table-column label="变更id" align="center" prop="ecoId" />
      <el-table-column label="变更标题" align="center" prop="ecoTitle" />
      <el-table-column label="变更编码" align="center" prop="ecoCode" />
      <el-table-column label="变更说明" align="center" prop="ecoDescription" />
      <el-table-column label="发布状态" align="center" prop="publishStatus" />
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <el-table-column label="文件后缀" align="center" prop="fileSuffix" />
      <el-table-column label="文件大小" align="center" prop="fileSize" />
      <el-table-column label="文件路径" align="center" prop="filePath" />
      <el-table-column label="文件大小" align="center" prop="size" />
      <el-table-column label="图纸描述" align="center" prop="remark" />
      <el-table-column label="申请流程" align="center" prop="flowKey" />
      <el-table-column label="流程实例id" align="center" prop="flowInsId" />
      <el-table-column label="单据类型" align="center" prop="billType" />
      <el-table-column label="能否进行变更" align="center" prop="allowChange" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:drawing:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:drawing:remove']"
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

    <!-- 添加或修改图纸管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="图纸编号" prop="drawingCode">
              <el-input v-model="form.drawingCode" placeholder="请输入图纸编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图纸名称" prop="drawingName">
              <el-input v-model="form.drawingName" placeholder="请输入图纸名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图纸版本" prop="drawingVersion">
              <el-input v-model="form.drawingVersion" placeholder="请输入图纸版本" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="是否最新版本" prop="latestVersion">
              <el-input v-model="form.latestVersion" placeholder="请输入是否最新版本" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目id" prop="projectId">
              <el-input v-model="form.projectId" placeholder="请输入项目id" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目名称" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入项目名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目编码" prop="projectCode">
              <el-input v-model="form.projectCode" placeholder="请输入项目编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目任务id" prop="projectTaskId">
              <el-input v-model="form.projectTaskId" placeholder="请输入项目任务id" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目任务编码" prop="projectTaskCode">
              <el-input v-model="form.projectTaskCode" placeholder="请输入项目任务编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="项目任务名称" prop="projectTaskName">
              <el-input v-model="form.projectTaskName" placeholder="请输入项目任务名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="变更id" prop="ecoId">
              <el-input v-model="form.ecoId" placeholder="请输入变更id" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="变更标题" prop="ecoTitle">
              <el-input v-model="form.ecoTitle" placeholder="请输入变更标题" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="变更编码" prop="ecoCode">
              <el-input v-model="form.ecoCode" placeholder="请输入变更编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="变更说明" prop="ecoDescription">
              <el-input v-model="form.ecoDescription" placeholder="请输入变更说明" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="文件名称" prop="fileName">
              <el-input v-model="form.fileName" placeholder="请输入文件名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="文件后缀" prop="fileSuffix">
              <el-input v-model="form.fileSuffix" placeholder="请输入文件后缀" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="文件大小" prop="fileSize">
              <el-input v-model="form.fileSize" placeholder="请输入文件大小" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="文件路径" prop="filePath">
              <el-input v-model="form.filePath" placeholder="请输入文件路径" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="文件大小" prop="size">
              <el-input v-model="form.size" placeholder="请输入文件大小" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图纸描述" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入图纸描述" />
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
            <el-form-item label="能否进行变更" prop="allowChange">
              <el-input v-model="form.allowChange" placeholder="请输入能否进行变更" />
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
import { listDrawing, getDrawing, delDrawing, addDrawing, updateDrawing } from "@/api/system/drawing"

export default {
  name: "Drawing",
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
      // 图纸管理表格数据
      drawingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        drawingCode: null,
        drawingName: null,
        drawingType: null,
        drawingVersion: null,
        latestVersion: null,
        status: null,
        projectId: null,
        projectName: null,
        projectCode: null,
        projectTaskId: null,
        projectTaskCode: null,
        projectTaskName: null,
        ecoId: null,
        ecoTitle: null,
        ecoCode: null,
        ecoDescription: null,
        publishStatus: null,
        fileName: null,
        fileSuffix: null,
        fileSize: null,
        filePath: null,
        size: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        allowChange: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        drawingCode: [
          { required: true, message: "图纸编号不能为空", trigger: "blur" }
        ],
        drawingName: [
          { required: true, message: "图纸名称不能为空", trigger: "blur" }
        ],
        drawingType: [
          { required: true, message: "图纸类型不能为空", trigger: "change" }
        ],
        drawingVersion: [
          { required: true, message: "图纸版本不能为空", trigger: "blur" }
        ],
        latestVersion: [
          { required: true, message: "是否最新版本不能为空", trigger: "blur" }
        ],
        fileName: [
          { required: true, message: "文件名称不能为空", trigger: "blur" }
        ],
        fileSuffix: [
          { required: true, message: "文件后缀不能为空", trigger: "blur" }
        ],
        fileSize: [
          { required: true, message: "文件大小不能为空", trigger: "blur" }
        ],
        filePath: [
          { required: true, message: "文件路径不能为空", trigger: "blur" }
        ],
        size: [
          { required: true, message: "文件大小不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询图纸管理列表 */
    getList() {
      this.loading = true
      listDrawing(this.queryParams).then(response => {
        this.drawingList = response.rows
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
        drawingCode: null,
        drawingName: null,
        drawingType: null,
        drawingVersion: null,
        latestVersion: null,
        status: null,
        projectId: null,
        projectName: null,
        projectCode: null,
        projectTaskId: null,
        projectTaskCode: null,
        projectTaskName: null,
        ecoId: null,
        ecoTitle: null,
        ecoCode: null,
        ecoDescription: null,
        publishStatus: null,
        fileName: null,
        fileSuffix: null,
        fileSize: null,
        filePath: null,
        size: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        allowChange: null
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
      this.title = "添加图纸管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getDrawing(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改图纸管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDrawing(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDrawing(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除图纸管理编号为"' + ids + '"的数据项？').then(function() {
        return delDrawing(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/drawing/export', {
        ...this.queryParams
      }, `drawing_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
