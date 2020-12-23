const { description } = require('../../package')

module.exports = {
  title: 'Rapid 4 You',
  port: 8060,
  description: '一款简单快速构建信息管理系统前后端的框架',
  head: [
    ['meta', { name: 'theme-color', content: '#3eaf7c' }],
    ['meta', { name: 'apple-mobile-web-app-capable', content: 'yes' }],
    ['meta', { name: 'apple-mobile-web-app-status-bar-style', content: 'black' }]
  ],
  themeConfig: {
    repo: '',
    editLinks: false,
    docsDir: '',
    editLinkText: '',
    lastUpdated: false,
    nav: [
      {
        text: '文档指南',
        link: '/guide/',
      },
      {
        text: '配置',
        link: '/config/'
      },
      {
        text: 'Github',
        link: 'https://github.com/lizzie2008/Rapid-4-You'
      }
    ],
    sidebar: {
      '/guide/': [
        {
          title: '指南',
          collapsable: false,
          children: [
            '',
            '快速上手',
            '版本信息',
          ]
        },
        {
          title: '前端手册',
          collapsable: false,
          children: [
          ]
        },
        {
          title: '后端手册',
          collapsable: false,
          children: [
          ]
        },
        {
          title: '部署项目',
          collapsable: false,
          children: [
          ]
        }
        ,
        {
          title: '其他',
          collapsable: false,
          children: [
          ]
        }
      ],
    }
  },
  plugins: [
    '@vuepress/plugin-back-to-top',
    '@vuepress/plugin-medium-zoom',
  ]
}
