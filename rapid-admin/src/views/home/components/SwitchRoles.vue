<template>
  <div>
    <div style="margin-bottom:15px;">切换至角色:</div>
    <el-radio-group v-model="switchRoles" @change="handleChange">
      <el-radio-button :label="1">系统超级管理员</el-radio-button>
      <el-radio-button :label="2">集团工会管理员</el-radio-button>
      <el-radio-button :label="3">我买网管理员</el-radio-button>
      <el-radio-button :label="4">专业化公司管理员</el-radio-button>
    </el-radio-group>
  </div>
</template>

<script>
export default {
  data() {
    return {
      switchRoles: null
    }
  },
  methods: {
    handleChange(val) {
      this.$store.dispatch('user/changeRoles', val).then(() => {
        this.$emit('change')
      })
    }
  }
  // computed: {
  //   switchRoles: {
  //     get() {
  //       return "系统超级管理员";
  //     },
  //     set(val) {
  //       this.$store.dispatch("user/changeRoles", this.roles[val]).then(() => {
  //         this.$emit("change");
  //       });
  //     },
  //   },
  // },
}
</script>
