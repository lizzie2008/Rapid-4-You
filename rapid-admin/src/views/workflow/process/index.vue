<template>
  <div class="app-container">
    <!--表格渲染-->
    <el-table ref="table" :data="tableData">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="id" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="key" prop="key" />
      <el-table-column label="版本" prop="version" />
      <el-table-column label="部署ID" prop="deploymentId" />
      <el-table-column label="操作" width="260px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-link
            icon="el-icon-edit"
            target="_blank"
            size="mini"
            style="margin-right: 10px"
            @click="centerDialogVisible = true"
          >查看流程</el-link>
          <el-link
            icon="el-icon-edit"
            target="_blank"
            size="mini"
            style="margin-right: 10px"
            @click="deploy(scope.row.id)"
          >删除</el-link>
          <el-link
            icon="el-icon-edit"
            target="_blank"
            size="mini"
            style="margin-right: 10px"
            @click="deploy(scope.row.id)"
          >发起流程</el-link>
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
        src="http://localhost:8080/api/processes/image/ask_for_leave:1:7510"
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
  name: 'ProcessList',
  data() {
    return {
      tableData: [],
      centerDialogVisible: false,
      width: '',
      height: ''
    }
  },
  created() {
    this.refresh()
  },
  methods: {
    refresh() {
      // 获取分类下拉
      processApi.getAll().then((res) => {
        this.tableData = res
      })
    },
    onLoad(e) {
      const img = e.target
      let width = 0
      if (img.fileSize > 0 || (img.width > 1 && img.height > 1)) {
        width = img.width + 100
      }
      this.width = width + 'px'
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
