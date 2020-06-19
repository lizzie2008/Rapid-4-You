import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'

import 'normalize.css/normalize.css'
import './assets/styles/element-variables.scss'
import './assets/styles/index.scss'

import './assets/icons' // icon

import App from './App.vue'
import router from './router'
import store from './store'

Vue.use(Element, {
  size: Cookies.get('size') || 'small' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
