<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <el-form v-if="crud.props.searchToggle" :inline="true">
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
      <crudOperation :permission="permission" />
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      lazy
      :load="getMenus"
      :data="crud.data"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      row-key="id"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column
        label="菜单标题"
        prop="title"
        width="200px"
        :show-overflow-tooltip="true"
      />
      <el-table-column prop="icon" label="图标" align="center" width="60px">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon ? scope.row.icon : ''" />
        </template>
      </el-table-column>
      <el-table-column prop="menuSort" align="center" label="排序" width="75px">
        <template slot-scope="scope">
          {{ scope.row.menuSort }}
        </template>
      </el-table-column>
      <el-table-column
        :show-overflow-tooltip="true"
        prop="path"
        label="路由地址"
      />
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
      <el-table-column
        :show-overflow-tooltip="true"
        prop="permission"
        label="权限标识"
        width="120px"
      />
      <el-table-column
        v-permission="['admin', 'dept:edit', 'dept:del']"
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
    <!--表单渲染-->
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible="crud.status.cu > 0"
      :title="crud.status.title"
      width="580px"
    >
      <el-form
        ref="form"
        :inline="true"
        :model="form"
        :rules="rules"
        size="small"
        label-width="80px"
      >
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="form.type" size="mini" style="width: 300px">
            <el-radio-button label="0">普通菜单</el-radio-button>
            <el-radio-button label="1">路由菜单</el-radio-button>
            <el-radio-button label="2">控件菜单</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.type.toString() === '0'" prop="iframe">
          <el-checkbox v-model="form.iframe">外链</el-checkbox>
        </el-form-item>
        <el-form-item
          v-if="form.type.toString() === '0'"
          label="菜单图标"
          prop="icon"
        >
          <el-popover
            placement="bottom-start"
            width="450"
            trigger="click"
            @show="$refs['iconSelect'].reset()"
          >
            <IconSelect ref="iconSelect" @selected="selected" />
            <el-input
              slot="reference"
              v-model="form.icon"
              style="width: 450px"
              placeholder="点击选择图标"
              readonly
            >
              <svg-icon
                v-if="form.icon"
                slot="prefix"
                :icon-class="form.icon"
                class="el-input__icon"
                style="height: 32px; width: 16px"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon" />
            </el-input>
          </el-popover>
        </el-form-item>

        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="附着标签"
          prop="affix"
        >
          <el-radio-group
            v-model="form.affix"
            size="mini"
            :disabled="form.iframe"
          >
            <el-radio-button :label="true">是</el-radio-button>
            <el-radio-button :label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="面包屑"
          prop="breadcrumb"
        >
          <el-radio-group
            v-model="form.breadcrumb"
            size="mini"
            :disabled="form.iframe"
          >
            <el-radio-button :label="true">是</el-radio-button>
            <el-radio-button :label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="是否缓存"
          prop="cache"
        >
          <el-radio-group
            v-model="form.cache"
            size="mini"
            :disabled="form.iframe"
          >
            <el-radio-button :label="true">是</el-radio-button>
            <el-radio-button :label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单标题" prop="title">
          <el-input
            v-model="form.title"
            style="width: 178px"
            placeholder="菜单标题 title"
          />
        </el-form-item>

        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="路由地址"
          prop="path"
        >
          <el-input
            v-model="form.path"
            placeholder="路由地址 path"
            style="width: 178px"
          />
        </el-form-item>

        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="组件名称"
          prop="componentName"
        >
          <el-input
            v-model="form.componentName"
            :disabled="form.iframe"
            style="width: 178px"
            placeholder="组件名称 name"
          />
        </el-form-item>
        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="组件路径"
          prop="component"
        >
          <el-input
            v-model="form.component"
            :disabled="form.iframe"
            style="width: 178px"
            placeholder="组件路径 component"
          />
        </el-form-item>
        <el-form-item label="菜单排序" prop="menuSort">
          <el-input-number
            v-model.number="form.menuSort"
            :min="0"
            :max="999"
            controls-position="right"
            style="width: 178px"
          />
        </el-form-item>
        <el-form-item label="权限标识" prop="permission">
          <el-input
            v-model="form.permission"
            :disabled="form.iframe"
            placeholder="权限标识"
            style="width: 178px"
          />
        </el-form-item>
        <el-form-item
          v-if="form.type.toString() === '0' || form.type.toString() === '1'"
          label="激活菜单"
          prop="activeMenu"
        >
          <el-input
            v-model="form.activeMenu"
            :disabled="form.iframe"
            placeholder="激活菜单 activeMenu"
            style="width: 178px"
          />
        </el-form-item>
        <el-form-item
          v-if="form.type.toString() === '0'"
          label="重定向"
          prop="redirect"
        >
          <el-input
            v-model="form.redirect"
            :disabled="form.iframe"
            style="width: 178px"
            placeholder="重定向 redirect"
          />
        </el-form-item>
        <el-form-item label="上级类目" prop="pid">
          <treeselect
            v-model="form.pid"
            :options="menus"
            :load-options="loadMenus"
            style="width: 450px"
            placeholder="选择上级类目"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button
          :loading="crud.status.cu === 2"
          type="primary"
          @click="crud.submitCU"
        >确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import rrOperation from '@/components/Crud/RR.operation'
import crudOperation from '@/components/Crud/CRUD.operation'
import udOperation from '@/components/Crud/UD.operation'
import CRUD, { presenter, header, form, crud } from '@/components/Crud/crud'
import crudMenu from '@/api/system/menu'
import IconSelect from '@/components/IconSelect'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'

const defaultForm = {
  id: null,
  title: null,
  redirect: null,
  menuSort: 999,
  path: null,
  component: null,
  componentName: null,
  iframe: false,
  roles: [],
  pid: 0,
  icon: null,
  cache: false,
  affix: false,
  breadcrumb: true,
  activeMenu: null,
  hidden: false,
  type: 0,
  permission: null
}
export default {
  name: 'Menu',
  components: {
    rrOperation,
    crudOperation,
    udOperation,
    IconSelect,
    Treeselect
  },
  cruds() {
    return CRUD({
      title: '菜单',
      url: 'api/menus',
      showOnPage: false,
      crudMethod: { ...crudMenu }
    })
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    return {
      menus: [],
      permission: {
        add: ['admin', 'menu:add'],
        edit: ['admin', 'menu:edit'],
        del: ['admin', 'menu:del']
      },
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        path: [{ required: true, message: '请输入地址', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.menus = []
      if (form.id != null) {
        if (form.pid === null) {
          form.pid = 0
        }
        this.getSupDepts(form.id)
      } else {
        this.menus.push({ id: 0, label: '顶级类目', children: null })
      }
    },
    [CRUD.HOOK.afterValidateCU](crud, form) {
      this.setDefaultProperties()
    },
    getMenus(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        crudMenu.getMenus(params).then((res) => {
          resolve(res.content)
        })
      }, 100)
    },
    getSupDepts(id) {
      crudMenu.getMenuSuperior(id).then((res) => {
        const children = res.map(function(obj) {
          if (!obj.leaf && !obj.children) {
            obj.children = null
          }
          return obj
        })
        this.menus = [{ id: 0, label: '顶级类目', children: children }]
      })
    },
    loadMenus({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        crudMenu.getMenusTree(parentNode.id).then((res) => {
          parentNode.children = res.map(function(obj) {
            if (!obj.leaf) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 100)
        })
      }
    },
    // 选中图标
    selected(name) {
      this.form.icon = name
    },
    // 切换类型时，自动设置相关属性
    setDefaultProperties() {
      // 普通菜单
      if (this.form.type.toString() === '0') {
        // 如果是外链
        if (this.form.iframe) {
          this.form.hidden = false
          this.form.affix = false
          this.form.breadcrumb = false
          this.form.cache = false
          this.form.componentName = null
          this.form.component = null
          this.form.permission = null
          this.form.activeMenu = null
          this.form.redirect = null
        }
      } else if (this.form.type.toString() === '1') {
        this.form.hidden = true
        this.form.icon = false
        this.form.redirect = null
      } else if (this.form.type.toString() === '2') {
        this.form.hidden = true
        this.form.icon = false
        this.form.affix = false
        this.form.breadcrumb = false
        this.form.cache = false
        this.form.componentName = null
        this.form.component = null
        this.form.redirect = null
      } else {
        throw new Error('未知的菜单类型：' + this.form.type.toString())
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .vue-treeselect__control,
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  height: 30px;
  line-height: 30px;
}
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
