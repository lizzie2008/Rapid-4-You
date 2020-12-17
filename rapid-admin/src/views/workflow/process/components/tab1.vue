<template>
  <div class="app-container">
    <!--表格渲染-->
    <el-table ref="table" :data="tableData">
      <el-table-column label="id" prop="id" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="key" prop="key" />
      <el-table-column label="版本" prop="version" />
      <el-table-column label="部署id" prop="deploymentId" />
      <el-table-column align="center" fixed="right">
        <template slot="header">
          <el-button type="success" icon="el-icon-refresh" @click="refresh">刷新</el-button>
        </template>
        <template slot-scope="scope">
          <el-link
            icon="el-icon-edit"
            style="margin-right: 10px; font-size: unset"
            @click="start(scope.row.id)"
          >发起</el-link>
          <el-link
            icon="el-icon-view"
            style="margin-right: 10px; font-size: unset"
            @click="showImage(scope.row.id)"
          >查看</el-link>
          <el-popconfirm
            title="确定删除该部署吗？"
            @onConfirm="del(scope.row.deploymentId)"
          >
            <el-link
              slot="reference"
              icon="el-icon-edit"
              style="margin-right: ; font-size: unset"
            >删除</el-link>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 流程图弹出框 -->
    <el-dialog
      title="查看流程"
      :visible.sync="centerDialogVisible"
      :width="width"
      append-to-body
    >
      <img
        :src="baseUrl + '/api/processes/image/' + processDefinitionId"
        @load="onLoad"
      >
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="centerDialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import processApi from '@/api/workflow/process'

export default {
  name: 'ProcessTab',
  data() {
    return {
      tableData: [],
      centerDialogVisible: false,
      processDefinitionId: null,
      width: '',
      height: '',
      baseUrl: process.env.VUE_APP_BASE_API
    }
  },
  methods: {
    refresh() {
      // 获取流程列表
      processApi.getAll().then((res) => {
        this.tableData = res
      })
    },
    start(id) {
      processApi.start(id).then((res) => {
        this.$notify({
          title: '启动流程实例成功',
          type: 'success'
        })
        this.refresh()
      })
    },
    showImage(id) {
      this.centerDialogVisible = true
      this.processDefinitionId = id
    },
    onLoad(e) {
      const img = e.target
      let width = 0
      if (img.fileSize > 0 || (img.width > 1 && img.height > 1)) {
        width = img.width + 100
      }
      this.width = width + 'px'
    },
    del(id) {
      // 删除流程部署
      processApi.del(id).then((res) => {
        this.$notify({
          title: '删除部署成功',
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
