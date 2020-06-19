import Vue from 'vue'
import Router from 'vue-router'
import Config from '../settings'
import { getToken } from '../utils/auth'

// 加载进度条
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(Router)
NProgress.configure({ showSpinner: false })

// 白名单
const whiteList = ['/login']
// 全局路由映射
export const constantRouterMap = [
  {
    path: '/',
    meta: { title: '首页' },
    component: (resolve) => require(['@/views/home'], resolve),
  },
  {
    path: '/login',
    meta: { title: '登录' },
    component: (resolve) => require(['@/views/login'], resolve),
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/features/401'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/features/404'], resolve),
    hidden: true
  }
]

// 定义路由对象
const router = new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

// 路由前统一处理
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title + ' - ' + Config.title
  }
  NProgress.start()
  if (getToken()) {   /* 有 token 的情况 */
    // 已登录且要跳转的页面是登录页
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      next()
      // if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
      //   store.dispatch('GetInfo').then(res => { // 拉取user_info
      //     // 动态路由，拉取菜单
      //     loadMenus(next, to)
      //   }).catch((err) => {
      //     console.log(err)
      //     store.dispatch('LogOut').then(() => {
      //       location.reload() // 为了重新实例化vue-router对象 避免bug
      //     })
      //   })
      // // 登录时未拉取 菜单，在此处拉取
      // } else if (store.getters.loadMenus) {
      //   // 修改成false，防止死循环
      //   store.dispatch('updateLoadMenus').then(res => {})
      //   loadMenus(next, to)
      // } else {
      //   next()
      // }
    }
  } else {  /* 没有 token 的情况 */
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

export default router