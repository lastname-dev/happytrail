<template>
  <navbar
    position="fixed"
    type="primary"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"
  >
    <h1><router-link v-popover:popover1 class="navbar-brand" to="/">HAPPY TRAIL</router-link></h1>
    <template slot="navbar-menu">
      <li class="nav-item">
        <router-link class="nav-link text-white" to="/search">
          <i class="now-ui-icons ui-1_zoom-bold"></i> SEARCH
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/hotplace">
          <i class="now-ui-icons design_image"></i> HOTPLACE
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/festival">
          <i class="now-ui-icons objects_spaceship"></i> Festival
        </router-link>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/board">
          <i class="now-ui-icons design_bullet-list-67"></i> BOARD
        </router-link>
      </li>

      <li class="nav-item">
        <div class="nav-link text-white">
          <n-button type="neutral" v-if="userInfo" @click.prevent="onClickLogout">
            <i class="now-ui-icons users_circle-08"></i> LOGOUT
          </n-button>
          <a v-else>
            <router-link to="/login"> <i class="now-ui-icons users_circle-08"></i> LOGIN </router-link>
          </a>
        </div>
      </li>

      <li class="nav-item">
        <router-link class="nav-link text-white" to="/profile">
          <i class="now-ui-icons users_single-02"></i> MYPAGE
        </router-link>
      </li>
    </template>
  </navbar>
</template>

<script>
import { Button, Navbar } from "@/components";
import { Popover } from "element-ui";
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";
export default {
  name: "main-navbar",
  props: {
    transparent: Boolean,
    colorOnScroll: Number,
  },
  components: {
    Navbar,
    [Popover.name]: Popover,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.userid);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "index" });
      alert("로그아웃이 완료되었습니다.");
    },
  },
};
</script>

<style scoped></style>
