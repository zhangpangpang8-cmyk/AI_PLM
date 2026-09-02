<template>
  <el-dialog title="物料审核" :visible="visible" width="500px" append-to-body @close="$emit('cancel')">
    <div class="item-summary">
      <el-descriptions :column="1" border size="small">
        <el-descriptions-item label="物料编码">{{ item.itemCode }}</el-descriptions-item>
        <el-descriptions-item label="物料名称">{{ item.itemName }}</el-descriptions-item>
        <el-descriptions-item label="规格型号">{{ item.specification }}</el-descriptions-item>
      </el-descriptions>
    </div>
    <el-form label-width="80px">
      <el-form-item label="审核结果">
        <el-radio-group v-model="auditStatus">
          <el-radio label="1">通过</el-radio>
          <el-radio label="2">驳回</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审核备注">
        <el-input v-model="auditRemark" type="textarea" :rows="3" placeholder="请输入审核备注" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('cancel')">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'MaterialAuditDialog',
  props: {
    visible: Boolean,
    item: { type: Object, default: () => ({}) }
  },
  data() {
    return { auditStatus: '1', auditRemark: '' }
  },
  watch: {
    visible(value) {
      if (value) {
        this.auditStatus = '1'
        this.auditRemark = ''
      }
    }
  },
  methods: {
    submit() {
      if (!this.auditStatus) {
        this.$message.warning('请选择审核结果')
        return
      }
      this.$emit('submit', {
        id: this.item.id,
        auditStatus: this.auditStatus,
        auditRemark: this.auditRemark
      })
    }
  }
}
</script>

<style scoped>
.item-summary { margin-bottom: 15px; }
</style>
