import Vue from "vue";
import Router from "vue-router";

import Index from "./pages/Index.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";
import StarterNavbar from "./layout/StarterNavbar.vue";

import Login from "./pages/Login.vue";
import Signup from "./pages/Signup.vue";

import Search from "./pages/Search.vue";

import PlanWrite from "./pages/PlanWrite.vue";
import PlanDetail from "./pages/PlanDetail.vue";

import Hotplace from "./pages/HotPlace.vue";
import HotplaceWrite from "./pages/HotplaceWrite.vue";
import HotplaceDetail from "./pages/HotplaceDetail.vue";

import Board from "./pages/Board.vue";
import BoardWrite from "./pages/BoardWrite.vue";
import BoardDetail from "./pages/BoardDetail.vue";

import Profile from "./pages/Profile.vue";
import ProfileOther from "./pages/ProfileOther.vue";

import Festival from "./pages/Festival.vue";

import store from "@/store";

Vue.use(Router);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");

  if (checkUserInfo != null && token) {
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다.");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

export default new Router({
  mode: "history",
  linkExactActiveClass: "active",
  routes: [
    {
      path: "/",
      name: "index",
      components: {
        default: Index,
        header: MainNavbar,
        // footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },

    {
      path: "/hotplace",
      name: "hotplace",
      beforeEnter: onlyAuthUser,
      components: {
        default: Hotplace,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/festival",
      name: "festival",
      beforeEnter: onlyAuthUser,
      components: {
        default: Festival,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/hotplace/write/:hotplaceid",
      name: "HotplaceWrite",
      beforeEnter: onlyAuthUser,
      components: {
        default: HotplaceWrite,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/hotplace/detail/:hotplaceid",
      name: "HotplaceDetail",
      beforeEnter: onlyAuthUser,
      components: {
        default: HotplaceDetail,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },

    {
      path: "/plan/write/:planid",
      name: "PlanWrite",
      beforeEnter: onlyAuthUser,
      components: {
        default: PlanWrite,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/plan/detail/:planid",
      name: "PlanDetail",
      beforeEnter: onlyAuthUser,
      components: {
        default: PlanDetail,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },

    {
      path: "/board",
      name: "board",
      beforeEnter: onlyAuthUser,
      components: {
        default: Board,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/board/write/:boardid", // 등록인 경우 boardid 0, 수정인 경우 boardid 가져간다!
      name: "BoardWrite",
      beforeEnter: onlyAuthUser,
      components: {
        default: BoardWrite,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/board/detail/:boardid",
      name: "BoardDetail",
      beforeEnter: onlyAuthUser,
      components: {
        default: BoardDetail,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/signup",
      name: "signup",
      components: { default: Signup, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 },
      },
    },
    {
      path: "/profile/",
      name: "profile",
      beforeEnter: onlyAuthUser,
      components: {
        default: Profile,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/profileother/:userid",
      name: "profileother",
      beforeEnter: onlyAuthUser,
      components: {
        default: ProfileOther,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
    {
      path: "/search",
      name: "search",
      components: {
        default: Search,
        header: MainNavbar,
        footer: MainFooter,
      },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" },
      },
    },
  ],
  scrollBehavior: (to) => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  },
});
