<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <el-form v-if="crud.props.searchToggle" :inline="true">
        <el-form-item>
          <el-input
            v-model="query.blurry"
            clearable
            placeholder="输入名称或者描述搜索"
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
    <!-- 表单渲染 -->
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.title"
      width="520px"
    >
      <el-form
        ref="form"
        :inline="true"
        :model="form"
        :rules="rules"
        size="small"
        label-width="80px"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" style="width: 380px;" />
        </el-form-item>
        <el-form-item label="角色级别" prop="level">
          <el-input-number
            v-model.number="form.level"
            :min="1"
            controls-position="right"
            style="width: 145px;"
          />
        </el-form-item>
        <el-form-item label="数据范围" prop="dataScope">
          <el-select
            v-model="form.dataScope"
            style="width: 140px"
            placeholder="请选择数据范围"
            @change="changeScope"
          >
            <el-option v-for="item in dateScopes" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.dataScope === '自定义'" label="数据权限" prop="depts">
          <treeselect
            v-model="form.depts"
            :load-options="loadDepts"
            :options="depts"
            multiple
            style="width: 380px"
            placeholder="请选择"
          />
        </el-form-item>
        <el-form-item label="描述信息" prop="description">
          <el-input v-model="form.description" style="width: 380px;" rows="5" type="textarea" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
    <el-row :gutter="15">
      <!--角色管理-->
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="17" style="margin-bottom: 10px">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="role-span">角色列表</span>
          </div>
          <el-table
            ref="table"
            v-loading="crud.loading"
            highlight-current-row
            style="width: 100%;"
            :data="crud.data"
            @selection-change="crud.selectionChangeHandler"
            @current-change="handleCurrentChange"
          >
            <el-table-column :selectable="checkboxT" type="selection" width="55" />
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="dataScope" label="数据权限" />
            <el-table-column prop="level" label="角色级别" />
            <el-table-column :show-overflow-tooltip="true" prop="description" label="描述" />
            <el-table-column
              :show-overflow-tooltip="true"
              width="135px"
              prop="createTime"
              label="创建日期"
            >
              <template slot-scope="scope">
                <span>{{ scope.row.createTime | timeFormat('YYYY-MM-DD') }}</span>
              </template>
            </el-table-column>
            <el-table-column
              v-permission="['admin','roles:edit','roles:del']"
              label="操作"
              width="130px"
              align="center"
              fixed="right"
            >
              <template slot-scope="scope">
                <udOperation
                  v-if="scope.row.level >= level"
                  :data="scope.row"
                  :permission="permission"
                />
              </template>
            </el-table-column>
          </el-table>
          <!--分页组件-->
          <pagination />
        </el-card>
      </el-col>
      <!-- 菜单授权 -->
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="7">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <el-tooltip class="item" effect="dark" content="选择指定角色分配菜单" placement="top">
              <span class="role-span">菜单分配</span>
            </el-tooltip>
            <el-button
              v-permission="['admin','roles:edit']"
              :disabled="!showButton"
              :loading="menuLoading"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px"
              type="primary"
              @click="saveMenu"
            >保存</el-button>
          </div>
          <el-tree
            ref="menu"
            lazy
            :data="menus"
            :default-checked-keys="menuIds"
            :load="getMenuDatas"
            :props="defaultProps"
            check-strictly
            accordion
            show-checkbox
            node-key="id"
            @check="menuChange"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CRUD, { presenter, header, form, crud } from '@/components/Crud/crud'
import rrOperation from '@/components/Crud/RR.operation'
import crudOperation from '@/components/Crud/CRUD.operation'
import udOperation from '@/components/Crud/UD.operation'
import pagination from '@/components/Crud/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import crudRoles from '@/api/system/role'
import { getDepts, getDeptSuperior } from '@/api/system/dept'
import { getMenusTree } from '@/api/system/menu'

const defaultForm = {
  id: null,
  name: null,
  depts: [],
  description: null,
  dataScope: '全部',
  level: 3
}
export default {
  name: 'Role',
  components: {
    Treeselect,
    pagination,
    crudOperation,
    rrOperation,
    udOperation
  },
  cruds() {
    return CRUD({
      title: '角色',
      url: 'api/roles',
      sort: 'level,asc',
      crudMethod: { ...crudRoles }
    })
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    return {
      defaultProps: { children: 'children', label: 'label', isLeaf: 'leaf' },
      dateScopes: ['全部', '本级', '自定义'],
      level: 3,
      currentId: 0,
      menuLoading: false,
      showButton: false,
      menus: [],
      menuIds: [],
      depts: [],
      permission: {
        add: ['admin', 'role:add'],
        edit: ['admin', 'role:edit'],
        del: ['admin', 'role:del']
      },
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        permission: [
          { required: true, message: '请输入权限', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    crudRoles.getLevel().then((data) => {
      this.level = data.level
    })
  },
  methods: {
    getMenuDatas(node, resolve) {
      setTimeout(() => {
        getMenusTree(node.data.id ? node.data.id : 0).then((res) => {
          resolve(res)
        })
      }, 100)
    },
    [CRUD.HOOK.afterRefresh]() {
      this.$refs.menu.setCheckedKeys([])
    },
    // 编辑前
    [CRUD.HOOK.beforeToEdit](crud, form) {
      if (form.dataScope === '自定义') {
        if (form.id == null) {
          this.getDepts()
        } else {
          this.getSupDepts(form.depts)
        }
      }
      const depts = []
      form.depts.forEach(function(dept) {
        depts.push(dept.id)
      })
      form.depts = depts
    },
    // 提交前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      if (crud.form.dataScope === '自定义' && crud.form.depts.length === 0) {
        this.$message({
          message: '自定义数据权限不能为空',
          type: 'warning'
        })
        return false
      } else if (crud.form.dataScope === '自定义') {
        const depts = []
        crud.form.depts.forEach(function(data) {
          const dept = { id: data }
          depts.push(dept)
        })
        crud.form.depts = depts
      } else {
        crud.form.depts = []
      }
      return true
    },
    [CRUD.HOOK.afterAddError](crud) {
      this.afterErrorMethod(crud)
    },
    [CRUD.HOOK.afterEditError](crud) {
      this.afterErrorMethod(crud)
    },
    afterErrorMethod(crud) {
      const depts = []
      crud.form.depts.forEach(function(dept) {
        depts.push(dept.id)
      })
      crud.form.depts = depts
    },
    // 触发单选
    handleCurrentChange(val) {
      if (val) {
        const _this = this
        // 清空菜单的选中
        this.$refs.menu.setCheckedKeys([])
        // 保存当前的角色id
        this.currentId = val.id
        // 初始化默认选中的key
        this.menuIds = []
        val.menus.forEach(function(data) {
          _this.menuIds.push(data.id)
        })
        this.showButton = true
      }
    },
    menuChange(menu) {
      // 判断是否在 menuIds 中，如果存在则删除，否则添加
      const index = this.menuIds.indexOf(menu.id)
      if (index !== -1) {
        this.menuIds.splice(index, 1)
      } else {
        this.menuIds.push(menu.id)
      }
    },
    // 保存菜单
    saveMenu() {
      this.menuLoading = true
      const role = { id: this.currentId, menus: [] }
      // 得到已选中的 key 值
      this.menuIds.forEach(function(id) {
        const menu = { id: id }
        role.menus.push(menu)
      })
      crudRoles
        .editMenu(role)
        .then(() => {
          this.crud.notify('保存成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
          this.menuLoading = false
          this.update()
        })
        .catch((err) => {
          this.menuLoading = false
          console.log(err.response.data.message)
        })
    },
    // 改变数据
    update() {
      // 无刷新更新 表格数据
      crudRoles.get(this.currentId).then((res) => {
        for (let i = 0; i < this.crud.data.length; i++) {
          if (res.id === this.crud.data[i].id) {
            this.crud.data[i] = res
            break
          }
        }
      })
    },
    // 获取部门数据
    getDepts() {
      getDepts({ enabled: true }).then((res) => {
        this.depts = res.content.map(function(obj) {
          if (obj.hasChildren) {
            obj.children = null
          }
          return obj
        })
      })
    },
    getSupDepts(depts) {
      const ids = []
      depts.forEach((dept) => {
        ids.push(dept.id)
      })
      getDeptSuperior(ids).then((res) => {
        const date = res.content
        this.buildDepts(date)
        this.depts = date
      })
    },
    buildDepts(depts) {
      depts.forEach((data) => {
        if (data.children) {
          this.buildDepts(data.children)
        }
        if (data.hasChildren && !data.children) {
          data.children = null
        }
      })
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        getDepts({ enabled: true, pid: parentNode.id }).then((res) => {
          parentNode.children = res.content.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 200)
        })
      }
    },
    // 如果数据权限为自定义则获取部门数据
    changeScope() {
      if (this.form.dataScope === '自定义') {
        this.getDepts()
      }
    },
    checkboxT(row) {
      return row.level >= this.level
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.role-span {
  font-weight: bold;
  color: #303133;
  font-size: 15px;
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep .vue-treeselect__multi-value {
  margin-bottom: 0;
}
::v-deep .vue-treeselect__multi-value-item {
  border: 0;
  padding: 0;
}
</style>
