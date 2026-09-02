<template>
  <div class="statistics-cards">
    <el-row :gutter="20" type="flex" justify="space-between">
      <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="3">
        <div class="stat-card stat-total">
          <div class="stat-label">物料总数</div>
          <div class="stat-value">{{ value.totalCount }}</div>
          <div class="stat-compare">较上月 +{{ value.compareLastMonth }}</div>
        </div>
      </el-col>
      <el-col
        v-for="item in firstRow"
        :key="item.classifyId || item.name"
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
        :xl="3"
      >
        <div class="stat-card" :class="'stat-' + item.color">
          <div class="stat-label">{{ item.name }}</div>
          <div class="stat-value">{{ item.count }}</div>
          <div class="stat-percent">占比 {{ item.percent }}%</div>
        </div>
      </el-col>
    </el-row>
    <el-row v-if="secondRow.length" :gutter="20" type="flex" justify="space-between" class="second-row">
      <el-col
        v-for="item in secondRow"
        :key="item.classifyId || item.name"
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
        :xl="3"
      >
        <div class="stat-card" :class="'stat-' + item.color">
          <div class="stat-label">{{ item.name }}</div>
          <div class="stat-value">{{ item.count }}</div>
          <div class="stat-percent">占比 {{ item.percent }}%</div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'MaterialStatistics',
  props: {
    value: {
      type: Object,
      required: true
    }
  },
  computed: {
    firstRow() {
      return this.value.categories.slice(0, 7)
    },
    secondRow() {
      return this.value.categories.slice(7)
    }
  }
}
</script>

<style scoped>
.statistics-cards {
  margin-bottom: 20px;
}

.second-row {
  margin-top: 20px;
}

.stat-card {
  min-height: 120px;
  padding: 20px 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
  cursor: pointer;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
}

.stat-total,
.stat-blue { background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%); }
.stat-green,
.stat-light-green { background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 100%); }
.stat-yellow { background: linear-gradient(135deg, #fff9e6 0%, #fff3cd 100%); }
.stat-purple { background: linear-gradient(135deg, #f3e5f5 0%, #e1bee7 100%); }
.stat-teal { background: linear-gradient(135deg, #e0f2f1 0%, #b2dfdb 100%); }
.stat-orange { background: linear-gradient(135deg, #fff3e0 0%, #ffe0b2 100%); }
.stat-pink { background: linear-gradient(135deg, #fce4ec 0%, #f8bbd0 100%); }
.stat-light-blue { background: linear-gradient(135deg, #e1f5fe 0%, #b3e5fc 100%); }

.stat-label {
  margin-bottom: 10px;
  color: #606266;
  font-size: 14px;
  line-height: 1.4;
  word-break: break-all;
}

.stat-value {
  margin-bottom: 8px;
  color: #303133;
  font-size: 28px;
  font-weight: bold;
}

.stat-compare,
.stat-percent {
  color: #909399;
  font-size: 13px;
}

@media (max-width: 1600px) {
  .stat-card {
    min-height: 100px;
    padding: 15px 10px;
  }

  .stat-value { font-size: 24px; }
  .stat-label { font-size: 13px; }
}
</style>
