<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程分类" prop="processCategory">
        <el-select v-model="queryParams.processCategory" placeholder="请选择流程分类" clearable>
          <el-option label="图纸审批" value="drawing"/>
          <el-option label="文档审批" value="document"/>
          <el-option label="变更通知" value="ecn"/>
          <el-option label="其他" value="other"/>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="正常" value="0"/>
          <el-option label="停用" value="1"/>
        </el-select>
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
          v-hasPermi="['system:process:definition:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:process:definition:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:process:definition:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:process:definition:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="definitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="流程标识" align="center" prop="processKey" width="150"/>
      <el-table-column label="流程名称" align="center" prop="processName" width="180"/>
      <el-table-column label="流程分类" align="center" prop="processCategory" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.processCategory === 'drawing'" type="primary">图纸审批</el-tag>
          <el-tag v-else-if="scope.row.processCategory === 'document'" type="success">文档审批</el-tag>
          <el-tag v-else-if="scope.row.processCategory === 'ecn'" type="warning">变更通知</el-tag>
          <el-tag v-else-if="scope.row.processCategory === 'other'" type="info">其他</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="流程类型" align="center" prop="processType" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.processType === 'drawing'" type="primary">图纸</el-tag>
          <el-tag v-else-if="scope.row.processType === 'document'" type="success">文档</el-tag>
          <el-tag v-else-if="scope.row.processType === 'ecn'" type="warning">变更通知</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="流程描述" align="center" prop="description" show-overflow-tooltip/>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-connection"
            @click="handleDesign(scope.row)"
            v-hasPermi="['system:process:definition:edit']"
          >设计
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['system:process:definition:query']"
          >查看
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:process:definition:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:process:definition:remove']"
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
      @pagination="getList"
    />

    <!-- 添加或修改流程定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="流程标识" prop="processKey">
          <el-input v-model="form.processKey" placeholder="请输入流程标识（英文）" :disabled="form.id != null"/>
        </el-form-item>
        <el-form-item label="流程名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入流程名称"/>
        </el-form-item>
        <el-form-item label="流程分类" prop="processCategory">
          <el-select v-model="form.processCategory" placeholder="请选择流程分类" style="width: 100%">
            <el-option label="图纸审批" value="drawing"/>
            <el-option label="文档审批" value="document"/>
            <el-option label="变更通知" value="ecn"/>
            <el-option label="其他" value="other"/>
          </el-select>
        </el-form-item>
        <el-form-item label="流程类型" prop="processType">
          <el-select v-model="form.processType" placeholder="请选择流程类型" style="width: 100%">
            <el-option label="图纸" value="drawing"/>
            <el-option label="文档" value="document"/>
            <el-option label="变更通知" value="ecn"/>
          </el-select>
        </el-form-item>
        <el-form-item label="流程描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入流程描述"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看流程定义详情对话框 -->
    <el-dialog title="流程定义详情" :visible.sync="viewOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="流程标识">{{ viewForm.processKey }}</el-descriptions-item>
        <el-descriptions-item label="流程名称">{{ viewForm.processName }}</el-descriptions-item>
        <el-descriptions-item label="流程分类">
          <el-tag v-if="viewForm.processCategory === 'drawing'" type="primary">图纸审批</el-tag>
          <el-tag v-else-if="viewForm.processCategory === 'document'" type="success">文档审批</el-tag>
          <el-tag v-else-if="viewForm.processCategory === 'ecn'" type="warning">变更通知</el-tag>
          <el-tag v-else-if="viewForm.processCategory === 'other'" type="info">其他</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="流程类型">
          <el-tag v-if="viewForm.processType === 'drawing'" type="primary">图纸</el-tag>
          <el-tag v-else-if="viewForm.processType === 'document'" type="success">文档</el-tag>
          <el-tag v-else-if="viewForm.processType === 'ecn'" type="warning">变更通知</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag v-if="viewForm.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ parseTime(viewForm.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="流程描述" :span="2">{{ viewForm.description || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ viewForm.remark || '-' }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">流程节点</el-divider>
      <el-table :data="viewNodes" size="small" border>
        <el-table-column label="节点标识" prop="nodeKey" width="150"/>
        <el-table-column label="节点名称" prop="nodeName" width="120"/>
        <el-table-column label="节点类型" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.nodeType === 'startEvent'" type="success" size="mini">开始</el-tag>
            <el-tag v-else-if="scope.row.nodeType === 'userTask'" type="primary" size="mini">审批</el-tag>
            <el-tag v-else-if="scope.row.nodeType === 'exclusiveGateway'" type="warning" size="mini">网关</el-tag>
            <el-tag v-else-if="scope.row.nodeType === 'endEvent'" type="danger" size="mini">结束</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审批人类型" prop="assigneeType" width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.assigneeType === 'user'">指定用户</span>
            <span v-else-if="scope.row.assigneeType === 'role'">角色</span>
            <span v-else-if="scope.row.assigneeType === 'dept'">部门</span>
            <span v-else-if="scope.row.assigneeType === 'leader'">部门负责人</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="审批人值" prop="assigneeValue"/>
      </el-table>

      <el-divider content-position="left">流程连线</el-divider>
      <el-table :data="viewEdges" size="small" border>
        <el-table-column label="连线标识" prop="edgeKey" width="150"/>
        <el-table-column label="源节点" prop="sourceNodeId" width="100"/>
        <el-table-column label="目标节点" prop="targetNodeId" width="100"/>
        <el-table-column label="条件文本" prop="conditionText"/>
        <el-table-column label="条件表达式" prop="conditionExpr"/>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="viewOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listProcessDefinition,
  getProcessDefinition,
  delProcessDefinition,
  addProcessDefinition,
  updateProcessDefinition
} from "@/api/system/process"

export default {
  name: "ProcessDefinition",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      definitionList: [],
      title: "",
      open: false,
      viewOpen: false,
      viewForm: {},
      viewNodes: [],
      viewEdges: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processName: null,
        processCategory: null,
        processType: null,
        status: null
      },
      form: {},
      rules: {
        processKey: [
          {required: true, message: "流程标识不能为空", trigger: "blur"}
        ],
        processName: [
          {required: true, message: "流程名称不能为空", trigger: "blur"}
        ],
        processCategory: [
          {required: true, message: "流程分类不能为空", trigger: "change"}
        ],
        processType: [
          {required: true, message: "流程类型不能为空", trigger: "change"}
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listProcessDefinition(this.queryParams).then(response => {
        this.definitionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        processKey: null,
        processName: null,
        processCategory: null,
        processType: null,
        description: null,
        status: "0",
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.$router.push({
        path: '/system/process/designer/index',
        query: {id: null}
      })
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessDefinition(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改流程定义"
      })
    },
    handleView(row) {
      this.viewForm = {}
      this.viewNodes = []
      this.viewEdges = []
      getProcessDefinition(row.id).then(response => {
        const data = response.data
        this.viewForm = data
        this.viewNodes = data.nodes || []
        this.viewEdges = data.edges || []
        this.viewOpen = true
      })
    },
    handleDesign(row) {
      this.$router.push({
        path: '/system/process/designer/index',
        query: {id: row.id}
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProcessDefinition(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProcessDefinition(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除流程定义编号为"' + ids + '"的数据项？').then(function () {
        return delProcessDefinition(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    handleExport() {
      this.download('system/process/definition/export', {
        ...this.queryParams
      }, `process_definition_${new Date().getTime()}.xlsx`)
    },
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用"
      this.$modal.confirm('确认要"' + text + '""' + row.processName + '"流程吗？').then(() => {
        return updateProcessDefinition(row)
      }).then(() => {
        this.$modal.msgSuccess(text + "成功")
      }).catch(() => {
        row.status = row.status === "0" ? "1" : "0"
      })
    }
  }
}
</script>
