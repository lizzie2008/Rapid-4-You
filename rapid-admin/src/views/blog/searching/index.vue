<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <el-row type="flex" justify="space-between">
        <el-input
          v-model="keyword"
          placeholder="请输入全文检索内容"
          clearable
          style="width: 60%"
          @change="page = 1"
          @keyup.enter.native="searching"
        >
          <el-button
            slot="append"
            icon="el-icon-search"
            :loading="loading"
            @click="searching"
          />
        </el-input>
        <el-button
          type="primary"
          icon="el-icon-refresh"
          :loading="operating"
          @click="syncToEs"
        >同步 Elasticsearch</el-button>
      </el-row>
    </div>
    <small
      v-if="result.content == null || result.content.length == 0"
      class="text-muted"
    >
      没有找到匹配的记录
    </small>
    <el-card v-else v-loading="loading" class="box-card">
      <div v-for="item in result.content" :key="item.id" class="search-result">
        <router-link
          class="search-title"
          :to="{ path: '/blog/article/' + item.id }"
        >
          <span v-html="item.title" />
        </router-link>
        <p class="search-snapshot">
          <span class="text-muted">{{ item.createTime | timeFormat }} - </span>
          <span class="text-muted" v-html="item.content">}</span>
        </p>
      </div>
      <el-pagination
        layout="prev, pager, next"
        :total="result.totalElements"
        :current-page.sync="page"
        :page-size="size"
        :hide-on-single-page="true"
        @current-change="searching"
      />
    </el-card>
  </div>
</template>

<script>
import { syncToEs, highLightQuery } from '@/api/blog/article'

export default {
  name: 'Searching',
  components: {},
  data() {
    return {
      keyword: '',
      searchFields: 'title,content',
      page: 1,
      size: 20,
      sort: null,
      result: {},
      operating: false,
      loading: false
    }
  },
  created() {},
  methods: {
    searching() {
      if (
        typeof this.keyword === 'undefined' ||
        this.keyword === null ||
        this.keyword === ''
      ) {
        return
      } else {
        // 关键字不为空，调用检索接口
        this.loading = true
        highLightQuery({
          keyword: this.keyword,
          searchFields: this.searchFields,
          page: this.page - 1,
          size: this.size,
          sort: this.sort
        })
          .then((res) => {
            this.loading = false
            this.result = res
            console.log(this.result)
          })
          .catch(() => (this.loading = false))
      }
    },
    syncToEs() {
      this.operating = true
      syncToEs()
        .then((res) => {
          this.operating = false
          this.$notify({
            title: `已同步${res}条数据到 Elasticsearch`,
            type: 'success'
          })
        })
        .catch(() => (this.operating = false))
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.search-result {
  margin-bottom: 30px;
}
.search-result .search-title {
  font-size: 16px;
  color: #007bff;
  text-decoration: underline;
}
.search-result .search-snapshot {
  font-size: 14px;
}
.el-pagination {
  text-align: center;
}
</style>
