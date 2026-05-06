<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="taskList">
      <el-table-column label="流程标题" align="center" prop="title" width="200" />
      <el-table-column label="业务类型" align="center" prop="businessType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.businessType === 'drawing'" type="primary">图纸</el-tag>
          <el-tag v-else-if="scope.row.businessType === 'document'" type="success">文档</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="业务编号" align="center" prop="businessNo" width="150" />
      <el-table-column label="审批节点" align="center" prop="nodeName" width="150" />
      <el-table-column label="审批结果" align="center" prop="taskStatus" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.taskStatus === 'approved'" type="success">通过</el-tag>
          <el-tag v-else-if="scope.row.taskStatus === 'rejected'" type="danger">驳回</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审批意见" align="center" prop="approvalOpinion" show-overflow-tooltip />
      <el-table-column label="审批时间" align="center" prop="approvalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approvalTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
      if (row.businessType === 'drawing') {
        this.$router.push({ path: '/system/drawing', query: { id: row.businessId }})
      }
    }
  }
}
</script>
