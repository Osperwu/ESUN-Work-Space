import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
//搭配springboot
import Register from '../views/Register.vue';
import Login from '../views/Login.vue';
import CreatePost from '../views/CreatePost.vue';
import PostList from '../views/PostList.vue';
import AddComment from '../views/AddComment.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      // component: HomeView,
      component: PostList,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    { path: '/register', component: Register },
    { path: '/login', component: Login },
    { path: '/posts/create', component: CreatePost },
    { path: '/posts', component: PostList },
    { path: '/comments/add', component: AddComment }
  ],
})


export default router
