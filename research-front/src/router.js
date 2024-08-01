// src/router.js
import Vue from 'vue'
import Router from 'vue-router'
import HomePage from './components/HomePage.vue'
import HProjectPage from './components/HProjectPage.vue'
import VProjectPage from './components/VProjectPage.vue'
import WorkPage from './components/WorkPage.vue'
import PaperPage from './components/PaperPage.vue'
import AwardPage from './components/AwardPage.vue'
import ResearcherPage from './components/ResearcherPage.vue'
import ResearcherDetailsPage from './components/ResearcherDetailsPage.vue'
import LeftNav from '@/components/LeftNav'
import TopNav from '@/components/TopNav'

Vue.use(Router)

const originalPush = Router.prototype.push
// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}


const routes = [
  // {
  //   path: '/',
  //   name: 'HomePage',
  //   component: HomePage
  // },
  {
    path: "/login",
    component: () => import("@/components/LogIn"),
    name: 'LogIn',
    hidden: true
  },
  {
    path: "/register",
    component: () => import("@/components/UserRegister"),
    name: 'register',
    hidden: true
  },
  {
    path: "/users",
    component: () => import("@/components/UserPage"),
    name: 'users',
    hidden: true
  },
  {
    path: '/',
    name: 'HomePage',
    component: HomePage,
    children: [{
      path: '/LeftNav',
      name: 'LeftNav',
      component: LeftNav

    },
    {
      path: '/TopNav',
      name: 'TopNav',
      component: TopNav
    }
    ]
  },
  {
    path: '/home',
    redirect: '/',
  },
  {
    path: '/hprojects',
    name: 'HProjectPage',
    component: HProjectPage
  },
  {
    path: '/vprojects',
    name: 'VProjectPage',
    component: VProjectPage
  },
  {
    path: '/works',
    name: 'WorkPage',
    component: WorkPage
  },
  {
    path: '/papers',
    name: 'PaperPage',
    component: PaperPage
  },
  {
    path: '/awards',
    name: 'AwardPage',
    component: AwardPage
  },
  {
    path: '/researchers',
    name: 'ResearcherPage',
    component: ResearcherPage
  },
  {
    path: '/researcher/details/:researcherId',
    name: 'ResearcherDetailsPage',
    component: ResearcherDetailsPage
  }
]

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router