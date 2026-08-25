<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="instanceList" border>
      <el-table-column label="流程标题" align="center" prop="title" min-width="150" />
      <el-table-column label="业务类型" align="center" prop="businessType" min-width="100">
        <template slot-scope="scope">
          <business-status-tag group="workflowBusinessType" :value="scope.row.businessType" />
        </template>
      </el-table-column>
      <el-table-column label="当前节点" align="center" prop="currentNode" min-width="120" />
      <el-table-column label="流程状态" align="center" prop="status" min-width="100">
        <template slot-scope="scope">
          <business-status-tag group="workflowInstanceStatus" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="发起时间" align="center" prop="startTime" min-width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" min-width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.duration">{{ formatDuration(scope.row.duration) }}</span>
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
import { listMyProcesses } from "@/api/system/process"
import { formatWorkflowDuration, getWorkflowBusinessRoute } from '@/utils/workflow'

export default {
  name: "MyProcesses",
  data() {
    return {
      loading: true,
      total: 0,
      instanceList: [],
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
      listMyProcesses(this.queryParams).then(response => {
        this.instanceList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleView(row) {
      const route = getWorkflowBusinessRoute(row.businessType, row.businessId)
      route ? this.$router.push(route) : this.$modal.msgInfo("该业务类型暂未接入详情页面")
    },
    formatDuration(seconds) {
      return formatWorkflowDuration(seconds)
    }
  }
}
</script>
