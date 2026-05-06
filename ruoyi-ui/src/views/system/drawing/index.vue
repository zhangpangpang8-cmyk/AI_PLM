<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
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
      <el-table-column label="图纸编号" align="center" prop="drawingCode" />
      <el-table-column label="图纸名称" align="center" prop="drawingName" />
      <el-table-column label="图纸类型" align="center" prop="drawingType" />
      <el-table-column label="图纸版本" align="center" prop="drawingVersion" />
      <el-table-column label="是否最新版本" align="center" prop="latestVersion">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.latestVersion === '1'" type="success" size="small">是</el-tag>
          <el-tag v-else type="info" size="small">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="项目编码" align="center" prop="projectCode" />
      <el-table-column label="项目任务编码" align="center" prop="projectTaskCode" />
      <el-table-column label="项目任务名称" align="center" prop="projectTaskName" />
      <el-table-column label="发布状态" align="center" prop="publishStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.publishStatus === '1'" type="success" size="small">已发布</el-tag>
          <el-tag v-else type="info" size="small">未发布</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <el-table-column label="文件大小" align="center" prop="fileSize" />
      <el-table-column label="图纸描述" align="center" prop="remark" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看</el-button>
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
    <el-dialog
  :title="title"
  :visible.sync="open"
  width="700px"
  append-to-body
  :close-on-click-modal="false"
>
  <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="margin-bottom: 20px;">
    <!-- 第一行：图纸编号 + 图纸版本 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="图纸编号" prop="drawingCode">
          <el-input v-model="form.drawingCode" placeholder="请输入图纸编号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图纸版本" prop="drawingVersion">
          <el-input v-model="form.drawingVersion" placeholder="如: V1.0" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第二行：图纸名称 -->
    <el-row>
      <el-col :span="24">
        <el-form-item label="图纸名称" prop="drawingName">
          <el-input v-model="form.drawingName" placeholder="请输入图纸名称" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第三行：图纸类型 + 状态 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="图纸类型" prop="drawingType">
          <el-select v-model="form.drawingType" placeholder="请选择图纸类型" style="width: 100%;">
            <el-option label="机械图纸" value="机械图纸" />
            <el-option label="电气图纸" value="电气图纸" />
            <el-option label="建筑图纸" value="建筑图纸" />
            <el-option label="工艺图纸" value="工艺图纸" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0" style="color: #409EFF;">正常</el-radio>
            <el-radio label="1" style="color: #F56C6C;">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第四行：项目名称 + 项目编码 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="项目编码" prop="projectCode">
          <el-input v-model="form.projectCode" placeholder="请输入项目编码" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第五行：项目任务名称 + 项目任务编码 -->
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="项目任务名称" prop="projectTaskName">
          <el-input v-model="form.projectTaskName" placeholder="请输入任务名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="项目任务编码" prop="projectTaskCode">
          <el-input v-model="form.projectTaskCode" placeholder="请输入任务编码" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第六行：上传文件 -->
    <el-row>
      <el-col :span="24">
        <el-form-item label="上传文件" prop="file">
          <el-upload
            ref="upload"
            :limit="1"
            :auto-upload="false"
            :on-change="handleFileChange"
            :on-remove="handleFileRemove"
            :file-list="fileList"
            accept=".pdf,.dwg,.dxf,.jpg,.png,.doc,.docx,.xls,.xlsx"
            action="#"
            drag
            :multiple="false"
            style="display: flex; align-items: center; justify-content: flex-start;"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">选择文件</div>
            <div slot="tip" class="el-upload__tip" style="font-size: 12px; color: #909399; margin-top: 5px;">
              支持 PDF、DWG、DXF、图片、Office 等格式文件
            </div>
          </el-upload>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 第七行：图纸描述 -->
    <el-row>
      <el-col :span="24">
        <el-form-item label="图纸描述" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="4"
            placeholder="请输入图纸描述"
            style="border-radius: 4px;"
          />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>

  <div slot="footer" class="dialog-footer" style="margin-top: 20px; text-align: right;">
    <el-button @click="cancel">取 消</el-button>
    <el-button type="primary" @click="submitForm">确 定</el-button>
  </div>
</el-dialog>
  </div>
</template>

<script>
import { listDrawing, getDrawing, delDrawing, addDrawing, updateDrawing } from "@/api/system/drawing"

export default {
  name: "Drawing",
  dicts: ['sys_normal_disable'],
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
      // 文件列表
      fileList: [],
      // 选中的文件
      selectedFile: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        drawingCode: null,
        drawingName: null,
        drawingType: null,
        projectName: null,
        projectCode: null,
        projectTaskCode: null,
        projectTaskName: null,
        fileName: null
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
        ]
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
        status: "0",
        projectId: null,
        projectName: null,
        projectCode: null,
        projectTaskId: null,
        projectTaskCode: null,
        projectTaskName: null,
        remark: null
      }
      this.fileList = []
      this.selectedFile = null
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
    /** 文件改变 */
    handleFileChange(file, fileList) {
      this.selectedFile = file.raw
      this.fileList = fileList.slice(-1) // 只保留最后一个文件
    },
    /** 文件移除 */
    handleFileRemove(file, fileList) {
      this.selectedFile = null
      this.fileList = fileList
    },
    /** 查看文件 */
    handleView(row) {
      if (row.filePath) {
        window.open(row.filePath, '_blank')
      } else {
        this.$modal.msgWarning("文件路径不存在")
      }
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
            // 修改
            updateDrawing(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            // 新增
            if (!this.selectedFile) {
              this.$modal.msgError("请上传文件")
              return
            }
            const formData = new FormData()
            formData.append('file', this.selectedFile)
            // 添加表单字段
            Object.keys(this.form).forEach(key => {
              if (this.form[key] !== null && this.form[key] !== undefined && this.form[key] !== '') {
                formData.append(key, this.form[key])
              }
            })
            addDrawing(formData).then(response => {
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

<style scoped>
.el-upload__tip {
  margin-top: 10px;
  color: #909399;
  font-size: 12px;
}
</style>
