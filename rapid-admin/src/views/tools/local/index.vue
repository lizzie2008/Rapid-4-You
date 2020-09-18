<template>
  <div class="app-container" style="padding: 8px;">
    <!-- 工具栏 -->
    <div class="crud-opts">
      <span class="crud-opts-left">
        <el-breadcrumb class="app-breadcrumb" separator="/">
          <transition-group name="breadcrumb">
            <el-breadcrumb-item v-for="(item,index) in folderRoutes" :key="index">
              <span v-if="index==folderRoutes.length-1" class="no-redirect">{{ item.name }}</span>
              <a v-else @click.prevent="toFolder(item.path)">{{ item.name }}</a>
            </el-breadcrumb-item>
          </transition-group>
        </el-breadcrumb>
      </span>
      <span class="crud-opts-right">
        <!-- 上传 -->
        <el-button class="filter-item" size="mini" type="primary" icon="el-icon-upload">上传</el-button>
        <!-- 同步 -->
        <el-button :icon="icon" class="filter-item" size="mini" type="warning">同步</el-button>
        <!-- 配置 -->
        <el-button class="filter-item" size="mini" type="success" icon="el-icon-s-tools">配置</el-button>
      </span>
    </div>
    <!-- 文件上传 -->
    <!-- <el-dialog
      :visible.sync="dialog"
      :close-on-click-modal="false"
      append-to-body
      width="500px"
      @close="doSubmit"
    >
      <el-upload
          :before-remove="handleBeforeRemove"
          :on-success="handleSuccess"
          :on-error="handleError"
          :file-list="fileList"
          :headers="headers"
          :action="qiNiuUploadApi"
          class="upload-demo"
          multiple
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" style="display: block;" class="el-upload__tip">请勿上传违法文件，且文件不超过15M</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doSubmit">确认</el-button>
      </div>
    </el-dialog>-->

    <!--表格渲染-->
    <el-table ref="table" v-loading="loading" :data="data" style="width: 100%;">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" :show-overflow-tooltip="true" label="文件名">
        <template slot-scope="scope">
          <a
            href="JavaScript:"
            class="el-link el-link--primary"
            type="primary"
            @click="handleRowClick(scope.row)"
          >{{ scope.row.name | smartPath(folderRoutes[folderRoutes.length - 1].path) }}</a>
        </template>
      </el-table-column>
      <el-table-column label="文件类型">
        <template slot-scope="scope">
          <span v-if="scope.row.type!=='目录'">{{ scope.row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小">
        <template slot-scope="scope">
          <span v-if="scope.row.type!=='目录'">{{ scope.row.size }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <!-- <pagination /> -->
  </div>
</template>

<script>
import { getFolders } from '@/api/tools/ali-oss'
import { getToken } from '@/utils/auth'

export default {
  name: 'Local',
  filters: {
    smartPath: function(value, currentPath) {
      return value.replace(currentPath, '')
    }
  },
  data() {
    return {
      data: [],
      loading: false,
      folderRoutes: [],
      permission: {
        del: ['admin', 'storage:del']
      },
      title: '文件',
      dialog: false,
      icon: 'el-icon-refresh',
      url: '',
      headers: { Authorization: getToken() },
      dialogImageUrl: '',
      dialogVisible: false,
      fileList: [],
      files: [],
      newWin: null
    }
  },
  created() {
    this.toFolder()
    this.buildBreadcrumb('')
  },
  methods: {
    handleRowClick(row) {
      // 点击行类型为目录
      if (row.type === '目录') {
        this.toFolder(row.name)
      }
    },
    toFolder(path) {
      // 点击行-类型为目录
      this.loading = true
      getFolders({ prefix: path })
        .then((res) => {
          this.data = res.content
          this.buildBreadcrumb(path)
          this.loading = false
        })
        .catch(() => (this.loading = false))
    },
    // 构建目录导航
    buildBreadcrumb(path) {
      this.folderRoutes = [{ name: '根目录', path: '' }]
      const splitPaths = path.split('/')
      for (let i = 0; i < splitPaths.length - 1; i++) {
        this.folderRoutes.push({
          name: splitPaths[i],
          path: this.folderRoutes[i].path + splitPaths[i] + '/'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 13px;
  line-height: 30px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
