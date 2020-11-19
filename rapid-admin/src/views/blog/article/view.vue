<template>
  <div class="app-container text-center">
    <h2>{{ article.title }}</h2>
    <small class="ptext-muted">
      创建：{{ article.createTime | timeFormat }}&nbsp;•&nbsp; 阅读：{{
        article.readSize
      }}&nbsp;•&nbsp; 点赞：{{ article.likeSize }}
    </small>
    <hr>
    <mavon-editor
      v-model="article.content"
      :editable="false"
      :subfield="false"
      :toolbars-flag="false"
      preview-background="#ffffff"
      default-open="preview"
    />
  </div>
</template>

<script>
import { get } from '@/api/blog/article'

export default {
  name: 'ArticleView',
  data() {
    return {
      article: {},
      loading: false
    }
  },
  created() {
    const id = this.$route.params && this.$route.params.id
    this.fetchData(id)
  },
  methods: {
    fetchData(id) {
      this.loading = true
      get(id)
        .then((res) => {
          this.article = res
          this.loading = false
        })
        .catch(() => (this.loading = false))
    }
  }
}
</script>
