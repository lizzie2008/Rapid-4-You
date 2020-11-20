<template>
  <div class="app-container" style="padding: 8px">
    <!-- 工具栏 -->
    <div class="crud-opts">
      <span>
        <el-breadcrumb class="app-breadcrumb" separator="/">
          <transition-group name="breadcrumb">
            <el-breadcrumb-item
              v-for="(item, index) in folderRoutes"
              :key="item.name"
            >
              <span
                v-if="index == folderRoutes.length - 1"
                class="no-redirect"
              >{{ item.name }}</span>
              <a v-else @click.prevent="toFolder(item.path)">{{ item.name }}</a>
            </el-breadcrumb-item>
          </transition-group>
        </el-breadcrumb>
      </span>
      <span class="opts-right">
        <!-- 上传 -->
        <el-button
          class="filter-item"
          size="mini"
          type="primary"
          icon="el-icon-upload"
          @click="dialog = true"
        >上传</el-button>
        <!-- 配置 -->
        <el-button
          class="filter-item"
          size="mini"
          type="info"
          icon="el-icon-s-tools"
          @click="doConfig"
        >配置</el-button>
        <el-button
          class="filter-item"
          size="mini"
          type="success"
          icon="el-icon-refresh"
          @click="refresh"
        >刷新</el-button>
        <el-popover v-model="showHelp" title="参考文档" trigger="hover">
          <el-link
            type="primary"
            style="font-size: 12px"
            target="_blank"
            href="https://help.aliyun.com/document_detail/31947.html?spm=a2c4g.11174283.2.42.8e787da27R1nOz"
          >阿里云OSS API文档</el-link>
          <a
            slot="reference"
            class="el-icon-question"
            @click="showHelp = !showHelp"
          />
        </el-popover>
      </span>
    </div>
    <!-- 文件上传 -->
    <el-dialog
      title="选择文件上传"
      :visible.sync="dialog"
      :close-on-click-modal="false"
      append-to-body
      width="40%"
      @close="doSubmit"
    >
      <el-upload
        drag
        multiple
        :before-upload="beforeUpload"
        :on-success="onSuccess"
        :on-error="onError"
        :file-list="fileList"
        :headers="headers"
        :action="uploadApi"
        :data="{ prefix: currentPath }"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip">上传的文件大小不能超过5MB</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doSubmit">确认</el-button>
      </div>
    </el-dialog>

    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="loading"
      :data="folders"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column
        prop="name"
        :show-overflow-tooltip="true"
        label="文件名"
        width="650px"
      >
        <template slot-scope="scope">
          <a
            href="JavaScript:"
            class="el-link el-link--primary"
            type="primary"
            style="font-size: unset"
            @click="handleRowClick(scope.row)"
          >{{ scope.row.name | smartPath(currentPath) }}</a>
        </template>
      </el-table-column>
      <el-table-column label="文件类型">
        <template slot-scope="scope">
          <span v-if="scope.row.type !== '目录'">{{ scope.row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小">
        <template slot-scope="scope">
          <span v-if="scope.row.type !== '目录'">{{ scope.row.size }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后编辑时间">
        <template slot-scope="scope">
          <span v-if="scope.row.type !== '目录'">{{
            scope.row.lastModified | timeFormat
          }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!--表单组件-->
    <configForm ref="form" />
  </div>
</template>

<script>
import { getFolders } from '@/api/tools/ali-oss'
import { getToken } from '@/utils/auth'
import configForm from './components/config'

export default {
  name: 'AliOss',
  components: { configForm },
  filters: {
    smartPath: function(value, currentPath) {
      return value.replace(currentPath, '')
    }
  },
  data() {
    return {
      folders: [],
      loading: false,
      showHelp: false,
      folderRoutes: [],
      permission: {
        del: ['admin', 'storage:del']
      },
      dialog: false,
      uploadApi: process.env.VUE_APP_BASE_API + '/api/ali-oss/upload',
      headers: { Authorization: getToken() },
      fileList: [],
      files: []
    }
  },
  computed: {
    currentPath: function() {
      return this.folderRoutes.length > 0
        ? this.folderRoutes[this.folderRoutes.length - 1].path
        : ''
    }
  },
  created() {
    this.refresh()
  },
  methods: {
    // 阿里云OSS配置
    doConfig() {
      const _this = this.$refs.form
      _this.init()
      _this.dialog = true
    },
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
          this.folders = res
          this._buildBreadcrumb(path)
          this.loading = false
        })
        .catch((err) => {
          this.folders = []
          this.loading = false
          console.log(err)
        })
    },
    // 上传有效性判断
    beforeUpload(file) {
      const isLt5M = file.size / 1024 / 1024 < 5
      if (!isLt5M) {
        this.$message.error('上传头像图片大小不能超过 5MB!')
      }
      return isLt5M
    },
    // 监听上传成功
    onSuccess(response, file, fileList) {
      if (response.code !== 0) {
        file.status = 'fail'
        this.$message.error(response.msg)
      }
      console.log(fileList)
    },
    // 监听上传失败
    onError(err, file, fileList) {
      this.$message.error('上传失败')
      console.log(err)
    },
    // 刷新列表数据
    doSubmit() {
      this.fileList = []
      this.dialog = false
      this.refresh()
    },
    // 刷新当前页面
    refresh() {
      this.toFolder(this.currentPath)
    },
    // 构建目录导航
    _buildBreadcrumb(path) {
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
.crud-opts {
  padding: 4px 0;
  display: -webkit-flex;
  display: flex;
  align-items: center;
}
.crud-opts .opts-right {
  margin-left: auto;
}

::v-deep .el-upload {
  width: 100%;
}
::v-deep .el-upload-dragger {
  width: 100%;
}
.el-icon-question {
  color: gray;
  font-size: 18px;
  margin-left: 10px;
  margin-right: 10px;
}
</style>
