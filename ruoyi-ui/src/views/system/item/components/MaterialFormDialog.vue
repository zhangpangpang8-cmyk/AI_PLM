<template>
  <el-dialog
    :title="title"
    :visible="visible"
    width="800px"
    append-to-body
    @close="$emit('cancel')"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col v-for="field in inputFields" :key="field.prop" :span="12">
          <el-form-item :label="field.label" :prop="field.prop">
            <el-input v-model="form[field.prop]" :placeholder="field.placeholder" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="单位" prop="unitName">
            <el-select v-model="form.unitName" placeholder="请选择单位" clearable filterable class="full-width">
              <el-option
                v-for="dict in unitOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="物料分类" prop="materialClassifyIds">
            <treeselect
              v-model="form.materialClassifyIds"
              :options="classifyOptions"
              :normalizer="normalizer"
              placeholder="请选择物料分类"
              :multiple="false"
              :flat="true"
              :show-count="true"
              class="full-width"
            />
          </el-form-item>
        </el-col>

        <el-col v-for="field in detailFields" :key="field.prop" :span="12">
          <el-form-item :label="field.label" :prop="field.prop">
            <el-input v-model="form[field.prop]" :placeholder="field.placeholder" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="重量(kg)" prop="weight">
            <el-input-number v-model="form.weight" :precision="2" :step="0.1" class="full-width" />
          </el-form-item>
        </el-col>

        <el-col v-for="field in textareaFields" :key="field.prop" :span="24">
          <el-form-item :label="field.label" :prop="field.prop">
            <el-input v-model="form[field.prop]" type="textarea" :rows="3" :placeholder="field.placeholder" />
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="是否启用" prop="enable">
            <el-radio-group v-model="form.enable">
              <el-radio v-for="option in enableOptions" :key="option.value" :label="option.value">
                {{ option.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="remake">
            <el-input v-model="form.remake" placeholder="请输入备注" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('cancel')">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { ENABLE_STATUS_OPTIONS } from '@/utils/material'

const required = (message, trigger = 'blur') => [
  { required: true, message, trigger }
]

export default {
  name: 'MaterialFormDialog',
  components: { Treeselect },
  props: {
    visible: Boolean,
    title: { type: String, default: '' },
    form: { type: Object, required: true },
    classifyOptions: { type: Array, default: () => [] },
    unitOptions: { type: Array, default: () => [] },
    normalizer: { type: Function, required: true }
  },
  data() {
    return {
      enableOptions: ENABLE_STATUS_OPTIONS,
      inputFields: [
        { prop: 'itemCode', label: '物料编码', placeholder: '请输入物料编码' },
        { prop: 'itemName', label: '物料名称', placeholder: '请输入物料名称' },
        { prop: 'specification', label: '规格型号', placeholder: '请输入规格型号' }
      ],
      detailFields: [
        { prop: 'vendorName', label: '供应商', placeholder: '请输入供应商名称' },
        { prop: 'material', label: '材质', placeholder: '请输入材质' },
        { prop: 'color', label: '颜色', placeholder: '请输入颜色' }
      ],
      textareaFields: [
        { prop: 'Info', label: '详细描述', placeholder: '请输入详细描述' },
        { prop: 'parametersValues', label: '技术参数', placeholder: '请输入技术参数' }
      ],
      rules: {
        itemCode: required('物料编码不能为空'),
        itemName: required('物料名称不能为空'),
        specification: required('规格型号不能为空'),
        unitName: required('单位不能为空'),
        materialClassifyIds: required('物料分类不能为空', 'change'),
        enable: required('启用状态不能为空', 'change')
      }
    }
  },
  methods: {
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) this.$emit('submit')
      })
    }
  }
}
</script>

<style scoped>
.full-width { width: 100%; }
</style>
