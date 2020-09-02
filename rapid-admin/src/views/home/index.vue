<template>
  <div class="app-container">
    <aside>
      如果您是第一次使用系统，或者对于系统的功能不是很了解，可以打开引导，我们向您简单介绍下系统的功能。您也可以参考：
      <a href="#">详细产品文档</a>
    </aside>
    <el-button icon="el-icon-question" type="primary" @click.prevent.stop="guide">
      打开向导
    </el-button>
  </div>
</template>

<script>
import Driver from 'driver.js' // import driver.js
import 'driver.js/dist/driver.min.css' // import driver.js css
import steps from './steps'

export default {
  name: 'Guide',
  data() {
    return {
      driver: null
    }
  },
  mounted() {
    this.driver = new Driver({
      closeBtnText: '关闭',
      nextBtnText: '下一项',
      prevBtnText: '上一项',
      doneBtnText: '完成'
    })
  },
  methods: {
    guide() {
      this.driver.defineSteps(steps)
      this.driver.start()
    }
  }
}
</script>
