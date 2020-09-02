const steps = [
  {
    element: '#hamburger-container',
    popover: {
      title: '汉堡包式菜单',
      description: '打开 | 关闭 侧边栏',
      position: 'bottom'
    }
  },
  {
    element: '#breadcrumb-container',
    popover: {
      title: '面包屑导航',
      description: '当前页面路径',
      position: 'bottom'
    }
  },
  {
    element: '#header-search',
    popover: {
      title: '页面导航搜索',
      description: '快速搜索您需要的功能模块',
      position: 'left'
    }
  },
  {
    element: '#screenfull',
    popover: {
      title: '全屏设置',
      description: '切换全屏设置',
      position: 'left'
    }
  },
  {
    element: '#size-select',
    popover: {
      title: '文本大小',
      description: '切换系统文本大小',
      position: 'left'
    }
  },
  {
    element: '#tags-view-container',
    popover: {
      title: '标签页',
      description: '保存您打开的历史标签页',
      position: 'bottom'
    },
    padding: 0
  }
]

export default steps
