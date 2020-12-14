<template>
  <div class="app-container">
    <iframe
      :src="'http://localhost:8080/workflow/modeler.html?modelId=' + id"
      frameborder="0"
      class="iframe-shrink"
    />
  </div>
</template>

<script>
export default {
  name: 'ModelEdit',
  data() {
    null
  },
  created() {
    this.id = this.$route.params && this.$route.params.id
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.iframe-shrink {
  width: 100%;
  height: 700px;
}
</style>
