<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="taskList" border>
      <el-table-column label="流程标题" align="center" prop="title" min-width="150" />
      <el-table-column label="业务类型" align="center" prop="businessType" min-width="100">
        <template slot-scope="scope">
          <business-status-tag group="workflowBusinessType" :value="scope.row.businessType" />
        </template>
      </el-table-column>
      <el-table-column label="业务编号" align="center" prop="businessNo" min-width="120" />
      <el-table-column label="审批节点" align="center" prop="nodeName" min-width="120" />
      <el-table-column label="审批结果" align="center" prop="taskStatus" min-width="100">
        <template slot-scope="scope">
          <business-status-tag group="workflowTaskStatus" :value="scope.row.taskStatus" />
        </template>
      </el-table-column>
      <el-table-column label="审批意见" align="center" prop="approvalOpinion" min-width="150" show-overflow-tooltip />
      <el-table-column label="审批时间" align="center" prop="approvalTime" min-width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approvalTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="100">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看</el-button>
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
  </div>
</template>

<script>
import { listCompletedTasks } from "@/api/system/process"
import { getWorkflowBusinessRoute } from '@/utils/workflow'

export default {
  name: "CompletedTasks",
  data() {
    return {
      loading: true,
      total: 0,
      taskList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCompletedTasks(this.queryParams).then(response => {
        this.taskList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleView(row) {
      const route = getWorkflowBusinessRoute(row.businessType, row.businessId)
      route ? this.$router.push(route) : this.$modal.msgInfo("该业务类型暂未接入详情页面")
    }
  }
}
</script>
