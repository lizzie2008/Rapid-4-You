<template>
  <div class="app-container">
    <!--工具栏-->
    <el-form :inline="true" :model="form">
      <el-form-item>
        <el-input v-model="query.blurry" clearable placeholder="模糊搜索" />
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
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      lazy
      :load="getMenus"
      :data="crud.data"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      row-key="id"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" label="菜单标题" width="125px" prop="title" />
      <el-table-column prop="icon" label="图标" align="center" width="60px">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon ? scope.row.icon : ''" />
        </template>
      </el-table-column>
      <el-table-column prop="menuSort" align="center" label="排序">
        <template slot-scope="scope">
          {{ scope.row.menuSort }}
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="permission" label="权限标识" />
      <el-table-column :show-overflow-tooltip="true" prop="component" label="组件路径" />
      <el-table-column prop="iframe" label="外链" width="75px">
        <template slot-scope="scope">
          <span v-if="scope.row.iframe">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="cache" label="缓存" width="75px">
        <template slot-scope="scope">
          <span v-if="scope.row.cache">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="hidden" label="可见" width="75px">
        <template slot-scope="scope">
          <span v-if="scope.row.hidden">否</span>
          <span v-else>是</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建日期" width="135px">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | timeFormat('YYYY-MM-DD') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column v-permission="['admin','menu:edit','menu:del']" label="操作" width="130px" align="center" fixed="right">
        <template slot-scope="scope">
          <udOperation
            :data="scope.row"
            :permission="permission"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column> -->
    </el-table>
  </div>
</template>

<script>
import rrOperation from '@/components/Crud/RR.operation'
import CRUD, { presenter, header, form, crud } from '@/components/Crud/crud'
import crudMenu from '@/api/system/menu'

const defaultForm = { id: null, title: null, menuSort: 999, path: null, component: null, componentName: null, iframe: false, roles: [], pid: 0, icon: null, cache: false, hidden: false, type: 0, permission: null }
export default {
  name: 'Menu',
  components: { rrOperation },
  cruds() {
    return CRUD({ title: '菜单', url: 'api/menus', crudMethod: { ...crudMenu }})
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    return {
    }
  },
  created() {},
  methods: {
    getMenus(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        crudMenu.getMenus(params).then(res => {
          resolve(res.content)
        })
      }, 100)
    }
  }
}
</script>
