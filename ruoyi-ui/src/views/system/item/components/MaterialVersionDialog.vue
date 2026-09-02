<template>
  <el-dialog title="版本历史" :visible="visible" width="900px" append-to-body @close="$emit('close')">
    <el-table :data="versions" border stripe>
      <el-table-column label="版本号" prop="itemVersion" width="100" />
      <el-table-column label="物料编码" prop="itemCode" width="120" />
      <el-table-column label="物料名称" prop="itemName" width="150" />
      <el-table-column label="规格型号" prop="specification" width="150" />
      <el-table-column label="发布时间" prop="createTime" width="160" />
      <el-table-column label="发布人" prop="createBy" width="100" />
      <el-table-column label="操作" width="120" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="$emit('view', scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script>
export default {
  name: 'MaterialVersionDialog',
  props: {
    visible: Boolean,
    versions: { type: Array, default: () => [] }
  }
}
</script>
