<template>
  <div class="app-container">
    <!-- 面包屑导航 -->
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item>进度跟踪</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 页面标题 -->
    <div class="page-header">
      <span class="page-title">进度跟踪</span>
      <el-button type="primary" size="small" icon="el-icon-refresh" @click="refreshData">刷新数据</el-button>
    </div>

    <!-- 项目选择和搜索 -->
    <div class="search-section">
      <el-select v-model="queryParams.projectId" placeholder="请选择项目" clearable style="width: 200px;">
        <el-option
          v-for="item in projectOptions"
          :key="item.id"
          :label="item.projectName"
          :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary" size="small" icon="el-icon-search" @click="handleQuery">搜索</el-button>
      <el-button size="small" icon="el-icon-refresh" @click="resetQuery">重置</el-button>
    </div>

    <!-- 进度概览 -->
    <div class="progress-overview">
      <div class="overview-card">
        <div class="card-title">总体进度</div>
        <div class="progress-value" :style="{color: getProgressColor(overallProgress)}">{{ overallProgress }}%</div>
        <el-progress :percentage="parseFloat(overallProgress)" :color="getProgressColor(overallProgress)"></el-progress>
      </div>
      <div class="overview-card">
        <div class="card-title">设计阶段</div>
        <div class="progress-value" :style="{color: getProgressColor(designProgress)}">{{ designProgress }}%</div>
        <el-progress :percentage="parseFloat(designProgress)" :color="getProgressColor(designProgress)"></el-progress>
      </div>
    </div>

    <!-- 进度趋势和里程碑 -->
    <div class="middle-section">
      <div class="left-panel">
        <!-- 进度趋势 -->
        <div class="panel-card">
          <div class="panel-title">进度趋势</div>
          <div ref="progressChart" class="chart-container"></div>
        </div>

        <!-- 预警信息 -->
        <div class="panel-card">
          <div class="panel-title">预警信息</div>
          <div class="warning-list">
            <div v-for="(warning, index) in warningList" :key="index" class="warning-item">
              <i class="el-icon-warning" style="color: #E6A23C;"></i>
              <span>{{ warning }}</span>
            </div>
            <el-empty v-if="warningList.length === 0" description="暂无预警信息" :image-size="80"></el-empty>
          </div>
        </div>
      </div>

      <!-- 里程碑 -->
      <div class="right-panel">
        <div class="panel-card">
          <div class="panel-title">里程碑</div>
          <el-empty v-if="milestoneList.length === 0" description="暂无里程碑数据" :image-size="80">
            <div class="empty-tip">请检查项目是否已设置里程碑</div>
          </el-empty>
          <div v-else class="milestone-list">
            <div v-for="(milestone, index) in milestoneList" :key="index" class="milestone-item">
              <div class="milestone-dot"></div>
              <div class="milestone-content">
                <div class="milestone-name">{{ milestone.name }}</div>
                <div class="milestone-date">{{ milestone.date }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 任务完成情况 -->
    <div class="task-statistics">
      <div class="panel-title">任务完成情况</div>
      <div class="stat-cards">
        <div class="stat-card">
          <div class="stat-label">总任务数</div>
          <div class="stat-value">{{ statistics.totalTasks }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">已完成任务</div>
          <div class="stat-value" style="color: #67C23A;">{{ statistics.completedTasks }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">进行中任务</div>
          <div class="stat-value" style="color: #409EFF;">{{ statistics.inProgressTasks }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">已延任务</div>
          <div class="stat-value" style="color: #F56C6C;">{{ statistics.delayedTasks }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">未开始任务</div>
          <div class="stat-value">{{ statistics.notStartedTasks }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">完成率</div>
          <div class="stat-value" style="color: #409EFF;">{{ statistics.completionRate }}%</div>
        </div>
      </div>
    </div>

    <!-- 任务进度表格 -->
    <div class="task-progress-section">
      <div class="panel-title">任务进度</div>
      <el-table v-loading="loading" :data="taskList" border style="width: 100%">
        <el-table-column label="任务编码" prop="taskCode" width="150"></el-table-column>
        <el-table-column label="任务名称" prop="taskName" width="200">
          <template slot-scope="scope">
            <span style="color: #67C23A;">{{ scope.row.taskName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)" size="small">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="进度" prop="progress" width="200">
          <template slot-scope="scope">
            <div class="progress-cell">
              <el-progress :percentage="scope.row.progress" :color="getProgressColor(scope.row.progress)" show-text></el-progress>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="阶段" prop="phase" width="120">
          <template slot-scope="scope">
            <el-tag type="info" size="small">{{ scope.row.phase }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="负责人" prop="assignee" width="120"></el-table-column>
        <el-table-column label="开始日期" prop="startDate" width="120"></el-table-column>
        <el-table-column label="截止日期" prop="endDate" width="120"></el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
import { listLog, getLog, delLog, addLog, updateLog } from "@/api/system/log"
import { listOverview, getOverview, getStatistics } from "@/api/system/overview"
import * as echarts from 'echarts'
import Pagination from "../../../components/Pagination/index.vue";

export default {
  name: "Log",
  components: {Pagination},
  data() {
    return {
      loading: false,
      total: 0,
      taskList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectId: null,
      },
      projectOptions: [],
      overallProgress: '0.00',
      designProgress: '0.00',
      warningList: [],
      milestoneList: [],
      statistics: {
        totalTasks: 0,
        completedTasks: 0,
        inProgressTasks: 0,
        delayedTasks: 0,
        notStartedTasks: 0,
        completionRate: '0.00'
      },
      progressChart: null
    }
  },
  created() {
    this.getProjectList()
    this.getList()
    this.getStatisticsData()
  },
  mounted() {
    this.$nextTick(() => {
      this.initProgressChart()
    })
  },
  beforeDestroy() {
    if (this.progressChart) {
      this.progressChart.dispose()
    }
  },
  methods: {
    getProjectList() {
      listOverview({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.projectOptions = response.rows
      })
    },
    getList() {
      this.loading = true
      listLog(this.queryParams).then(response => {
        this.taskList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getStatisticsData() {
      getStatistics().then(response => {
        const data = response.data

        this.overallProgress = data.totalProgress || '0.00'
        this.warningList = this.generateWarningList()
        this.statistics = {
          totalTasks: data.totalTasks || 0,
          completedTasks: data.completedTasks || 0,
          inProgressTasks: data.inProgressTasks || 0,
          delayedTasks: data.delayedTasks || 0,
          notStartedTasks: data.notStartedTasks || 0,
          completionRate: data.completionRate || '0.00'
        }
      })
    },
    generateWarningList() {
      const warnings = []
      if (this.projectOptions.length > 0 && this.queryParams.projectId) {
        const project = this.projectOptions.find(p => p.id === this.queryParams.projectId)
        if (project && project.healthStatus === '2') {
          warnings.push(`项目: ${project.projectName} 已延期`)
        }
      }
      return warnings
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
      this.getStatisticsData()
    },
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        projectId: null,
      }
      this.handleQuery()
    },
    refreshData() {
      this.getProjectList()
      this.getList()
      this.getStatisticsData()
      this.$message.success('数据已刷新')
    },
    initProgressChart() {
      const chartDom = this.$refs.progressChart
      if (!chartDom) return

      this.progressChart = echarts.init(chartDom)
      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: function(params) {
            return `${params[0].axisValue}<br/>
              ${params[0].marker} 实际进度: ${params[0].value}%<br/>
              ${params[1].marker} 计划进度: ${params[1].value}%`
          }
        },
        legend: {
          data: ['实际进度', '计划进度'],
          bottom: 0
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          top: '5%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['04-21'],
          axisLine: {
            lineStyle: {
              color: '#E5E5E5'
            }
          }
        },
        yAxis: {
          type: 'value',
          max: 100,
          axisLabel: {
            formatter: '{value}%'
          },
          splitLine: {
            lineStyle: {
              color: '#E5E5E5',
              type: 'dashed'
            }
          }
        },
        series: [
          {
            name: '实际进度',
            type: 'line',
            data: [0],
            itemStyle: {
              color: '#409EFF'
            },
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2
            }
          },
          {
            name: '计划进度',
            type: 'line',
            data: [0],
            itemStyle: {
              color: '#F56C6C'
            },
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2,
              type: 'dashed'
            }
          }
        ]
      }
      this.progressChart.setOption(option)
    },
    getStatusType(status) {
      const typeMap = {
        '已完成': 'success',
        '进行中': 'primary',
        '未开始': 'info',
        '已延期': 'danger'
      }
      return typeMap[status] || 'info'
    },
    getProgressColor(progress) {
      const num = parseFloat(progress)
      if (num >= 100) return '#67C23A'
      if (num >= 70) return '#409EFF'
      if (num >= 30) return '#E6A23C'
      return '#F56C6C'
    }
  }
}
</script>

<style scoped>
.app-container {
  background: #F0F2F5;
  padding: 20px;
}

.breadcrumb {
  margin-bottom: 16px;
  color: #666;
  font-size: 14px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.page-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.search-section {
  background: #fff;
  padding: 16px;
  border-radius: 4px;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.progress-overview {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
  margin-bottom: 16px;
}

.overview-card {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
}

.card-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.progress-value {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 12px;
}

.middle-section {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 16px;
  margin-bottom: 16px;
}

.left-panel {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.panel-card {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
}

.panel-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
}

.chart-container {
  width: 100%;
  height: 300px;
}

.warning-list {
  min-height: 100px;
}

.warning-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px;
  background: #FFF1F0;
  border-left: 3px solid #F56C6C;
  margin-bottom: 8px;
  color: #F56C6C;
}

.empty-tip {
  color: #999;
  font-size: 12px;
  margin-top: -8px;
}

.milestone-list {
  max-height: 400px;
  overflow-y: auto;
}

.milestone-item {
  display: flex;
  gap: 12px;
  padding: 12px 0;
  border-bottom: 1px solid #F0F0F0;
}

.milestone-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: #409EFF;
  margin-top: 4px;
  flex-shrink: 0;
}

.milestone-name {
  font-size: 14px;
  color: #333;
  margin-bottom: 4px;
}

.milestone-date {
  font-size: 12px;
  color: #999;
}

.task-statistics {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
  margin-bottom: 16px;
}

.stat-cards {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
}

.stat-card {
  background: #F5F7FA;
  padding: 16px;
  border-radius: 4px;
  text-align: center;
}

.stat-label {
  font-size: 12px;
  color: #666;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.task-progress-section {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
}

.progress-cell {
  padding: 4px 0;
}
</style>
