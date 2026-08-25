<template>
  <div class="app-container">
    <!-- 第一行：搜索表单（整行） -->
    <el-row class="mb8">
      <el-col :xs="24" :sm="24" :md="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="100px" class="search-form-inline">
          <el-form-item label="技术文档名称" prop="techName">
            <el-input v-model="queryParams.techName" placeholder="请输入技术文档名称" clearable
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>

          <el-form-item label="文件名称" prop="fileName">
            <el-input v-model="queryParams.fileName" placeholder="请输入文件名称" clearable
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>

          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="queryParams.projectName" placeholder="请输入项目名称" clearable
                      @keyup.enter.native="handleQuery"/>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <!-- 第二行：操作按钮，左对齐 -->
    <el-row class="mb8" type="flex" justify="start" align="middle">
      <el-col :xs="24" :sm="24" :md="24">
        <div class="toolbar-actions-left">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                     v-hasPermi="['system:tech:add']">新增
          </el-button>

          <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                     v-hasPermi="['system:tech:edit']">修改
          </el-button>

          <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                     v-hasPermi="['system:tech:remove']">删除
          </el-button>

          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                     v-hasPermi="['system:tech:export']">导出
          </el-button>

          <!-- 保留原有 right-toolbar 组件 -->
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </div>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="techList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" min-width="60"/>
      <el-table-column label="技术文档编码" align="center" prop="techCode" min-width="120"/>
      <el-table-column label="技术文档名称" align="center" prop="techName" min-width="150"/>
      <el-table-column label="技术文档类型" align="center" prop="techType" min-width="100"/>
      <el-table-column label="版本" align="center" prop="techVersion" min-width="80"/>
      <el-table-column label="发布状态" align="center" prop="publishStatus" min-width="100">
        <template slot-scope="scope">
          <business-status-tag group="techPublishStatus" :value="scope.row.publishStatus" />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" min-width="80">

        <template slot-scope="scope">
          <business-status-tag group="techStatus" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="文件名称" align="center" prop="fileName" width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="项目名称" align="center" prop="projectName" width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="创建时间" align="center" prop="createTime" min-width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="140">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['system:tech:query']"
          >查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:tech:edit']"
            :disabled="scope.row.status === '1'"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:tech:remove']"
            :disabled="scope.row.status === '1'"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList" s
    />

    <!-- 添加或修改技术文档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" style="margin-bottom: 20px;">
        <!-- 第一行：技术文档编码 + 版本 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="技术文档编码" prop="techCode">
              <el-input v-model="form.techCode" placeholder="请输入技术文档编码"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行：技术文档名称 -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="技术文档名称" prop="techName">
              <el-input v-model="form.techName" placeholder="请输入技术文档名称"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行：技术文档类型 + 语言类型 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="技术文档类型" prop="techType">
              <el-select v-model="form.techType" placeholder="请选择技术文档类型" style="width: 100%;">
                <el-option label="需求文档" value="requirement"/>
                <el-option label="设计文档" value="design"/>
                <el-option label="测试文档" value="test"/>
                <el-option label="用户手册" value="manual"/>
                <el-option label="其他" value="other"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="语言类型" prop="techLanguage">
              <el-select v-model="form.techLanguage" placeholder="请选择语言类型" style="width: 100%;">
                <el-option label="中文" value="zh-CN"/>
                <el-option label="English" value="en-US"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第四行：项目名称 + 项目编码 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目名称" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入项目名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目编码" prop="projectCode">
              <el-input v-model="form.projectCode" placeholder="请输入项目编码"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第五行：是否最新版本 + 是否允许变更 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否最新版本" prop="latestVersion">
              <el-radio-group v-model="form.latestVersion">
                <el-radio label="Y">是</el-radio>
                <el-radio label="N">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否允许变更" prop="allowChange">
              <el-radio-group v-model="form.allowChange">
                <el-radio label="1">允许</el-radio>
                <el-radio label="0">不允许</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第六行：上传文件 -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="上传文件" prop="file">
              <single-file-upload v-model="fileList" @file-change="selectedFile = $event" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第七行：版本说明 -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="版本说明" prop="versionDescription">
              <el-input
                v-model="form.versionDescription"
                type="textarea"
                :rows="3"
                placeholder="请输入版本说明" style="border-radius: 4px;"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第八行：文档描述 -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="文档描述" prop="remark">
              <el-input
                v-model="form.remark"
                type="textarea"
                :rows="3"
                placeholder="请输入文档描述" style="border-radius: 4px;"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-alert
          v-if="!form.id"
          title="保存后将自动进入审批流程"
          type="info"
          :closable="false"
          show-icon style="margin-bottom: 15px;"
        />
      </el-form>

      <div slot="footer" class="dialog-footer" style="margin-top: 20px; text-align: right;">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitLoading">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listTech, getTech, delTech, addTech, updateTech} from "@/api/system/tech"

export default {
  name: "Tech",
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
      // 技术文档表格数据
      techList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 提交按钮loading
      submitLoading: false,
      // 选中的文件
      selectedFile: null,
      // 文件列表
      fileList: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        techCode: null,
        techName: null,
        techType: null,
        techLanguage: null,
        techVersion: null,
        latestVersion: null,
        status: null,
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
        versionDescription: null,
        flowKey: null,
        flowInsId: null,
        billType: null,
        projectId: null,
        projectName: null,
        projectCode: null,
        projectTaskId: null,
        projectTaskCode: null,
        projectTaskName: null,
        allowChange: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        techCode: [
          {required: true, message: "技术文档编码不能为空", trigger: "blur"}
        ],
        techName: [
          {required: true, message: "技术文档名称不能为空", trigger: "blur"}
        ],
        techType: [
          {required: true, message: "技术文档类型不能为空", trigger: "change"}
        ],
        techLanguage: [
          {required: true, message: "技术文档语言类型不能为空", trigger: "blur"}
        ],
        techVersion: [
          {required: true, message: "版本不能为空", trigger: "blur"}
        ],
        latestVersion: [
          {required: true, message: "是否最新版本不能为空", trigger: "blur"}
        ],
        publishStatus: [
          {required: true, message: "发布状态不能为空", trigger: "change"}
        ],
        fileName: [
          {required: true, message: "文件名称不能为空", trigger: "blur"}
        ],
        fileSuffix: [
          {required: true, message: "文件后缀不能为空", trigger: "blur"}
        ],
        fileSize: [
          {required: true, message: "文件大小不能为空", trigger: "blur"}
        ],
        filePath: [
          {required: true, message: "文件路径不能为空", trigger: "blur"}
        ],
        size: [
          {required: true, message: "文件大小不能为空", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询技术文档列表 */
    getList() {
      this.loading = true
      listTech(this.queryParams).then(response => {
        this.techList = response.rows
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
      this.selectedFile = null
      this.fileList = []
      this.form = {
        id: null,
        techCode: null,
        techName: null,
        techType: null,
        techLanguage: null,
        techVersion: null,
        latestVersion: "Y",
        status: null,
        ecoId: null,
        ecoTitle: null,
        ecoCode: null,
        ecoDescription: null,
        publishStatus: "draft",
        fileName: null,
        fileSuffix: null,
        fileSize: null,
        filePath: null,
        size: null,
        remark: null,
        versionDescription: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        flowKey: "tech_approval",
        flowInsId: null,
        billType: null,
        projectId: null,
        projectName: null,
        projectCode: null,
        projectTaskId: null,
        projectTaskCode: null,
        projectTaskName: null,
        allowChange: "1"
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
    /** 查看按钮操作 */
    handleView(row) {
      this.$router.push({
        path: '/system/tech-detail/' + row.id
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加技术文档"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTech(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改技术文档"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateTech(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            }).catch(error => {
              this.$modal.msgError(error.message || "修改失败")
            }).finally(() => {
              this.submitLoading = false
            })
          } else {
            if (!this.selectedFile) {
              this.$modal.msgError("请选择要上传的文件")
              this.submitLoading = false
              return
            }
            const formData = new FormData()
            formData.append('file', this.selectedFile)
            Object.keys(this.form).forEach(key => {
              if (this.form[key] !== null && this.form[key] !== undefined && this.form[key] !== '') {
                formData.append(key, this.form[key])
              }
            })
            addTech(formData).then(response => {
              this.$modal.msgSuccess("新增成功，已进入审批流程")
              this.open = false
              this.getList()
            }).catch(error => {
              this.$modal.msgError(error.message || "新增失败")
            }).finally(() => {
              this.submitLoading = false
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除技术文档编号为"' + ids + '"的数据项？').then(function () {
        return delTech(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/tech/export', {
        ...this.queryParams
      }, `tech_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

