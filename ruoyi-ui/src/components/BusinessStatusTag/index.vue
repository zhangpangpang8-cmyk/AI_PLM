<template>
  <el-tag v-if="meta.known" :type="meta.type" :size="size" :effect="effect">
    {{ meta.label }}
  </el-tag>
  <span v-else>{{ meta.label }}</span>
</template>

<script>
import { getBusinessStatusMeta } from '@/constants/business'

export default {
  name: 'BusinessStatusTag',
  props: {
    group: {
      type: String,
      required: true
    },
    value: {
      type: [String, Number],
      default: null
    },
    size: {
      type: String,
      default: undefined
    },
    effect: {
      type: String,
      default: 'light'
    }
  },
  computed: {
    meta() {
      return getBusinessStatusMeta(this.group, this.value)
    }
  }
}
</script>
