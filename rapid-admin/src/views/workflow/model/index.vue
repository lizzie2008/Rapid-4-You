<template>
  <div class="app-container">
    <!--表格渲染-->
    <el-table ref="table" :data="tableData">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="id" />
      <el-table-column label="Key" prop="key" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="版本" prop="version" />
      <el-table-column label="修订" prop="revision" />
      <el-table-column label="部署ID" prop="deploymentId" />
      <el-table-column label="创建时间" prop="createTime" width="160px">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | timeFormat() }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="lastUpdateTime" width="160px">
        <template slot-scope="scope">
          <span>{{ scope.row.lastUpdateTime | timeFormat() }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="260px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-link
            icon="el-icon-edit"
            target="_blank"
            size="mini"
            style="margin-right: 10px"
            :href="
              'http://localhost:8080/workflow/modeler.html?modelId=' +
                scope.row.id
            "
          >打开设计器</el-link>
          <el-link
            icon="el-icon-edit"
            target="_blank"
            size="mini"
            style="margin-right: 10px"
            @click="deploy(scope.row.id)"
          >部署</el-link>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import modelApi from '@/api/workflow/model'

export default {
  name: 'ModelList',
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.refresh()
  },
  methods: {
    refresh() {
      // 获取分类下拉
      modelApi.getAll().then((res) => {
        this.tableData = res
      })
    },
    deploy(id) {
      modelApi.deploy(id).then((res) => {
        this.$notify({
          title: '部署成功',
          type: 'success'
        })
        this.refresh()
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
