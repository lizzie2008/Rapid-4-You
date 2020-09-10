<template>
  <el-form v-if="crud.props.searchToggle" :inline="true">
    <el-form-item>
      <el-input
        v-model="query.name"
        clearable
        placeholder="输入岗位名称搜索"
      />
    </el-form-item>
    <el-form-item>
      <el-date-picker
        v-model="query.createTime"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format=" yyyy-MM-dd HH:mm:ss"
      />
    </el-form-item>
    <el-form-item>
      <el-select
        v-model="query.enabled"
        clearable
        placeholder="状态"
      >
        <el-option
          v-for="item in dict.dict.job_status"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <rrOperation />
    </el-form-item>
  </el-form>
</template>

<script>
import { header } from '@/components/Crud/crud'
import rrOperation from '@/components/Crud/RR.operation'
export default {
  components: { rrOperation },
  mixins: [header()],
  props: {
    dict: {
      type: Object,
      required: true
    },
    permission: {
      type: Object,
      required: true
    }
  }
}
</script>
