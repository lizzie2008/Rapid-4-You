<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <el-form v-if="crud.props.searchToggle" :inline="true">
        <!-- 搜索 -->
        <el-form-item>
          <el-input
            v-model="query.name"
            clearable
            placeholder="输入部门名称搜索"
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
          <rrOperation />
        </el-form-item>
      </el-form>
      <crudOperation :permission="permission" />
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column
        label="标题"
        prop="title"
        width="300"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="分类" prop="title1" />
      <el-table-column label="归档" prop="title2" />
      <el-table-column label="标签" prop="title3" />
      <el-table-column prop="createTime" label="创建日期">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | timeFormat("YYYY-MM-DD") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        v-permission="['admin', 'article:edit', 'article:del']"
        label="操作"
        width="130px"
        align="center"
        fixed="right"
      >
        <template slot-scope="scope">
          <udOperation
            :data="scope.row"
            :permission="permission"
            :disabled-dle="scope.row.id === 1"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import crudArticle from '@/api/blog/article'
import CRUD, { presenter, header, form, crud } from '@/components/Crud/crud'
import rrOperation from '@/components/Crud/RR.operation'
import crudOperation from '@/components/Crud/CRUD.operation'
import udOperation from '@/components/Crud/UD.operation'
import pagination from '@/components/Crud/Pagination'

const defaultForm = {
  id: null,
  name: null,
  isTop: true,
  subCount: 0,
  pid: null,
  deptSort: 999,
  enabled: 'true'
}
export default {
  name: 'Article',
  components: {
    crudOperation,
    rrOperation,
    udOperation,
    pagination
  },
  cruds() {
    return CRUD({
      title: '文章',
      url: 'api/article',
      showOnPage: false,
      crudMethod: { ...crudArticle }
    })
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  // 设置数据字典
  dicts: [],
  data() {
    return {
      articles: [],
      rules: {},
      permission: {
        add: ['admin', 'article:add'],
        edit: ['admin', 'article:edit'],
        del: ['admin', 'article:del']
      }
    }
  },
  methods: {}
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
