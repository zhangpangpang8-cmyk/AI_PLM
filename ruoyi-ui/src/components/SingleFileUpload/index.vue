<template>
  <div class="single-file-upload">
    <el-upload
      :limit="1"
      :auto-upload="false"
      :on-change="handleChange"
      :on-remove="handleRemove"
      :file-list="value"
      :accept="accept"
      action="#"
      drag
      :multiple="false"
      list-type="text"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">{{ buttonText }}</div>
    </el-upload>
    <div v-if="tip" class="upload-tip">{{ tip }}</div>
  </div>
</template>

<script>
export default {
  name: 'SingleFileUpload',
  props: {
    value: {
      type: Array,
      default: () => []
    },
    accept: {
      type: String,
      default: '.pdf,.dwg,.dxf,.jpg,.png,.doc,.docx,.xls,.xlsx'
    },
    buttonText: {
      type: String,
      default: '选择文件'
    },
    tip: {
      type: String,
      default: '支持 PDF、DWG、DXF、图片、Office 等格式文件'
    }
  },
  methods: {
    handleChange(file, fileList) {
      const latestFileList = fileList.slice(-1)
      this.$emit('input', latestFileList)
      this.$emit('file-change', file.raw || null)
    },
    handleRemove(file, fileList) {
      this.$emit('input', fileList)
      this.$emit('file-change', null)
    }
  }
}
</script>

<style scoped>
.single-file-upload {
  width: 100%;
}

.upload-tip {
  margin-top: 8px;
  color: #909399;
  font-size: 12px;
}

::v-deep .el-upload-list {
  margin-top: 10px;
}
</style>
