<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane
        label="流程定义"
        name="first"
      ><process-tab1 ref="first" /></el-tab-pane>
      <el-tab-pane
        label="运行流程"
        name="second"
      ><process-tab2 ref="second" /></el-tab-pane>
      <el-tab-pane
        label="历史流程"
        name="third"
      ><process-tab3 ref="third" /></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import ProcessTab1 from './components/tab1'
import ProcessTab2 from './components/tab2'
import ProcessTab3 from './components/tab3'

export default {
  name: 'ProcessList',
  components: {
    ProcessTab1,
    ProcessTab2,
    ProcessTab3
  },
  data() {
    return {
      activeTab: ''
    }
  },
  watch: {
    activeTab(val) {
      this.$refs[val].refresh()
    }
  },
  created() {
    this.activeTab = 'first'
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
