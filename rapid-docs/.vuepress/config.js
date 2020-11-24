module.exports = {
  title: "Rapid 4 You",
  description: '一款简单快速构建信息管理系统前后端的框架.',
  dest: 'public',
  head: [
    ['link', { rel: 'icon', href: '/favicon.ico' }],
    ['meta', { name: 'viewport', content: 'width=device-width,initial-scale=1,user-scalable=no' }]
  ],
  theme: 'reco',
  themeConfig: {
    nav: [
      { text: '主页', link: '/', icon: 'reco-home' },
      {
        text: '文档',
        icon: 'reco-message',
        items: [
          { text: '项目介绍', link: '/guide/about/' },
          { text: '前端指南', link: '/guide/rapid-admin/' },
          { text: '后端指南', link: '/guide/rapid-api/' }
        ]
      },
      { text: 'TimeLine', link: '/timeline/', icon: 'reco-date' },

      {
        text: 'Contact',
        icon: 'reco-message',
        items: [
          { text: 'GitHub', link: 'https://github.com/recoluan', icon: 'reco-github' }
        ]
      }
    ],
    sidebar: {
      '/guide/rapid-admin/': [
        {
          title: '基础',
          collapsable: false,
          children: [
            '',
            'installUse',
            'blog',
            'frontMatter'
          ]
        },
        {
          title: '进阶',
          collapsable: false,
          children: [
            'timeline',
            'valine',
            'password',
            'configJs',
            'home',
            'notfound',
            'mode',
            'local',
            'codeTheme',
            'sidebar'
          ]
        },
        {
          title: '默认主题配置',
          collapsable: false,
          children: [
            'abstract',
            'syntax',
            'customStyleAndScript'
          ]
        },
        {
          title: '其他',
          collapsable: false,
          children: [
            'updatetoone',
            'recommend'
          ]
        }
      ],
    },
    // type: 'blog',
    // 博客设置
    blogConfig: {
      category: {
        // 在导航栏菜单中所占的位置，默认3
        location: 3,
        text: '分类'
      },
      tag: {
        // 在导航栏菜单中所占的位置，默认4
        location: 4,
        text: '标签'
      }
    },
    logo: '/logo.png',
    // 搜索设置
    search: true,
    searchMaxSuggestions: 10,
    // 自动形成侧边导航
    // sidebar: 'auto',
    // 最后更新时间
    lastUpdated: 'Last Updated',
    // 作者
    author: 'Leo Hsu',
    // 作者头像
    authorAvatar: '/avatar.png',
    // 备案号
    record: '京ICP备17074256号',
    // 项目开始时间
    startYear: '2019'
  },
  markdown: {
    lineNumbers: true
  }
}  
