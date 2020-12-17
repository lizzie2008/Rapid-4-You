<template>
  <div class="app-container">
    <!--表格渲染-->
    <el-table ref="table" :data="tableData">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="处理人" prop="assignee" />
      <el-table-column label="任务key" prop="taskDefinitionKey" />
      <el-table-column label="流程定义id" prop="processDefinitionId" />
      <el-table-column label="创建时间" prop="createTime" width="160px">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | timeFormat() }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-link
            v-if="user.username === scope.row.assignee"
            icon="el-icon-edit"
            style="margin-right: 10px; font-size: unset"
            @click="getFormData(scope.row)"
          >完成任务</el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 填写申请单 -->
    <el-dialog
      :title="'任务-' + selectTask.name"
      :visible.sync="taskFormVisiable"
      width="600px"
    >
      <el-form ref="taskForm" :model="taskForm" :rules="rules">
        <el-form-item
          v-for="(property, index) in formProperties"
          :key="index"
          :label="property.name"
          label-width="80px"
          :prop="property.id"
        >
          <template
            v-if="property.type.name === 'string'"
          ><el-input
            v-model="taskForm[property.id]"
            placeholder="请输入内容"
          />
          </template>
          <template
            v-if="property.type.name === 'long'"
          ><el-input-number v-model="taskForm[property.id]" />
          </template>
          <template v-if="property.type.name === 'boolean'">
            <el-radio
              v-model="taskForm[property.id]"
              :label="true"
            >是</el-radio>
            <el-radio
              v-model="taskForm[property.id]"
              :label="false"
            >否</el-radio>
          </template>
          <template
            v-if="property.type.name === 'date'"
          ><el-date-picker
            v-model="taskForm[property.id]"
            type="datetime"
            format="yyyy-MM-dd hh:mm:ss"
            placeholder="选择日期时间"
          />
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">重置</el-button>
        <el-button type="primary" @click="completeTask">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import processApi from '@/api/workflow/task'
import store from '@/store'

export default {
  name: 'TaskList',
  data() {
    return {
      tableData: [],
      selectTask: {},
      taskForm: {},
      formProperties: [],
      taskFormVisiable: false,
      rules: {},
      user: store.getters.user
    }
  },
  created() {
    this.refresh()
  },
  methods: {
    refresh() {
      // 获取任务列表
      processApi.getAll().then((res) => {
        this.tableData = res.filter(
          (s) =>
            s.assignee === this.user.username || this.user.username === 'admin'
        )
      })
    },
    getFormData(selectTask) {
      this.formProperties = []
      this.selectTask = selectTask
      this.taskForm = {}
      this.rules = {}
      // 获取任务表单属性
      processApi.getFormProperties(selectTask.id).then((res) => {
        this.taskFormVisiable = true
        this.formProperties = res
        console.log(this.formProperties)
        this.formProperties.forEach((property) => {
          this.rules[property.id] = [
            {
              required: property.required,
              message: property.name + '不能为空',
              trigger: 'change'
            }
          ]
        })
      })
    },
    // 完成当前任务
    completeTask() {
      this.$refs['taskForm'].validate((valid) => {
        if (valid) {
          processApi
            .complete(this.selectTask.id, this.taskForm)
            .then((res) => {
              this.taskFormVisiable = false
              this.refresh()
            })
            .catch((err) => {
              console.log(err)
            })
        } else {
          console.log('提交数据有误')
          return false
        }
      })
    },
    resetForm() {
      this.$refs['taskForm'].resetFields()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
