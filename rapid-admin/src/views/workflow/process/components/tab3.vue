<template>
  <div class="app-container">
    <!--表格渲染-->
    <el-table ref="table" :data="tableData">
      <el-table-column label="实例id" prop="id" width="80px" />
      <el-table-column label="流程id" prop="processDefinitionId" />
      <el-table-column label="流程名称" prop="processDefinitionName" />
      <el-table-column label="流程key" prop="processDefinitionKey" />
      <el-table-column label="流程版本" prop="processDefinitionVersion" width="80px" />
      <el-table-column label="部署id" prop="deploymentId" />
      <el-table-column label="开始时间" prop="startTime">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime | timeFormat() }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" prop="endTime">
        <template slot-scope="scope">
          <span>{{ scope.row.endTime | timeFormat() }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right">
        <template slot="header">
          <el-button
            type="success"
            icon="el-icon-refresh"
            @click="refresh"
          >刷新</el-button>
        </template>
        <template slot-scope="scope">
          <el-link
            icon="el-icon-view"
            style="margin-right: 10px; font-size: unset"
            @click="detail(scope.row.id)"
          >查看</el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 流程详情 -->
    <el-dialog title="流程详情" :visible.sync="dialogVisible">
      <el-table :data="activityInstances" max-height="800px">
        <el-table-column
          property="activityName"
          label="活动名称"
        />
        <el-table-column
          property="assignee"
          label="处理人"
        />
        <el-table-column label="流转时间" property="startTime">
          <template slot-scope="scope">
            <span>{{ scope.row.startTime | timeFormat() }}</span>
          </template>
        </el-table-column>
        <el-table-column label="处理时间" property="endTime">
          <template slot-scope="scope">
            <span>{{ scope.row.endTime | timeFormat() }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import processApi from '@/api/workflow/process'

export default {
  name: 'ProcessTab3',
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      activityInstances: []
    }
  },
  methods: {
    refresh() {
      // 获取流程列表
      processApi.historyList().then((res) => {
        this.tableData = res
      })
    },
    detail(id) {
      // 获取流程详情
      processApi.detail(id).then((res) => {
        this.dialogVisible = true
        this.activityInstances = res
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
