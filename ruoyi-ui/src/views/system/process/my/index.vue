<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="instanceList" border>
      <el-table-column label="流程标题" align="center" prop="title" min-width="150" />
      <el-table-column label="业务类型" align="center" prop="businessType" min-width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.businessType === 'drawing'" type="primary">图纸</el-tag>
          <el-tag v-else-if="scope.row.businessType === 'document'" type="success">文档</el-tag>
          <el-tag v-else-if="scope.row.businessType === 'tech_doc'" type="warning">技术文档</el-tag>
          <span v-else>{{ scope.row.businessType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前节点" align="center" prop="currentNode" min-width="120" />
      <el-table-column label="流程状态" align="center" prop="status" min-width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'running'" type="warning">进行中</el-tag>
          <el-tag v-else-if="scope.row.status === 'approved'" type="success">已通过</el-tag>
          <el-tag v-else-if="scope.row.status === 'rejected'" type="danger">已驳回</el-tag>
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
      if (row.businessType === 'drawing') {
        this.$router.push({ path: '/system/drawing', query: { id: row.businessId }})
      }
    },
    formatDuration(seconds) {
      if (!seconds) return '-'
      const hours = Math.floor(seconds / 3600)
      const minutes = Math.floor((seconds % 3600) / 60)
      if (hours > 0) {
        return `${hours}小时${minutes}分钟`
      }
      return `${minutes}分钟`
    }
  }
}
</script>
