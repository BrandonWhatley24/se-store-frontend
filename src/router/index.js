import { createWebHistory, createRouter } from "vue-router";
import MainPage from "@/pages/MainPage.vue";
import Transactions from "@/pages/Transactions.vue";
import LoginPage from "@/pages/LoginPage.vue";

const routes = [
  {
    path: "/",
    name: "LoginPage",
    component: LoginPage,
  },
  {
    path: "/MainPage",
    name: "MainPage",
    component: MainPage,
  },
  {
    path: "/Transactions",
    name: "Transactions",
    component: Transactions,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;