<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="taskList" style="width: 100%">
      <el-table-column label="流程标题" align="center" prop="title" min-width="150" show-overflow-tooltip />
      <el-table-column label="业务类型" align="center" prop="businessType" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.businessType === 'drawing'" type="primary">图纸</el-tag>
          <el-tag v-else-if="scope.row.businessType === 'document'" type="success">文档</el-tag>
          <span v-else>{{ scope.row.businessType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务编号" align="center" prop="businessNo" width="150" show-overflow-tooltip />
      <el-table-column label="当前节点" align="center" prop="nodeName" width="150" />
      <el-table-column label="发起人" align="center" prop="initiator" width="120" />
      <el-table-column label="发起时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220" fixed="right">
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
            icon="el-icon-check"
            style="color: #67C23A"
            @click="handleApprove(scope.row, true)"
          >通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            style="color: #F56C6C"
            @click="handleApprove(scope.row, false)"
          >驳回</el-button>
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

    <!-- 审批对话框 -->
    <el-dialog title="审批意见" :visible.sync="approveOpen" width="500px" append-to-body>
      <el-form ref="approveForm" :model="approveForm" label-width="100px">
        <el-form-item label="审批意见">
          <el-input v-model="approveForm.opinion" type="textarea" :rows="4" placeholder="请输入审批意见（可选）" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitApprove">确 定</el-button>
        <el-button @click="approveOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPendingTasks, approveTask } from "@/api/system/process"

export default {
  name: "PendingTasks",
  data() {
    return {
      loading: true,
      total: 0,
      taskList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      approveOpen: false,
      approveForm: {
        taskId: null,
        approved: true,
        opinion: null
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listPendingTasks(this.queryParams).then(response => {
        this.taskList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleView(row) {
      // 查看业务详情，根据业务类型跳转到不同页面
      if (row.businessType === 'drawing') {
        this.$router.push({ path: '/system/drawing', query: { id: row.businessId }})
      } else if (row.businessType === 'document') {
        // 文档跳转
        this.$modal.msgInfo("文档详情页面待开发")
      }
    },
    handleApprove(row, approved) {
      this.approveForm.taskId = row.id
      this.approveForm.approved = approved
      this.approveForm.opinion = approved ? '同意' : '驳回'
      this.approveOpen = true
    },
    submitApprove() {
      this.$modal.confirm('确认' + (this.approveForm.approved ? '通过' : '驳回') + '该流程？').then(() => {
        return approveTask(this.approveForm.taskId, this.approveForm.approved, this.approveForm.opinion)
      }).then(() => {
        this.$modal.msgSuccess("审批成功")
        this.approveOpen = false
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
