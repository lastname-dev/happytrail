<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image" style="background-color: #ffae6b"> </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title" v-if="this.$route.params.boardid != 0">BOARD MODIFY</h1>
          <h1 class="title" v-else>BOARD REGIST</h1>
        </div>
      </div>
    </div>

    <div class="section text-center my-5">
      <div class="container">
        <div class="card">
          <div class="card-header m-3" style="font-size: 22px">
            <textarea placeholder="Title" class="form-control" v-model="title" rows="1"> </textarea>
          </div>
          <div class="card-body" style="font-size: 16px">
            <textarea class="form-control" v-model="content" rows="5"></textarea>
          </div>
          <div class="m-1" style="float: right">
            <n-button type="neutral" @click="moveToBoard">CANCEL</n-button>
            <n-button type="neutral" @click="modifyArticle" v-if="this.$route.params.boardid != 0">MODIFY</n-button>
            <n-button type="neutral" @click="registArticle" v-else>REGIST</n-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Button, FormGroupInput } from "@/components";
import http from "@/util/http-common.js";
export default {
  name: "BoardDetail",
  bodyClass: "landing-page",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  data() {
    return {
      title: "",
      content: "",
    };
  },
  methods: {
    registArticle() {
      // db에 저장
      http
        .post(`/boards`, {
          title: this.title,
          content: this.content,
          writer: JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid,
          nickname: JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.nickname,
        })
        .then()
        .catch();
      alert("등록이 완료되었습니다.");
      this.moveToBoard(); // 해당 디테일 페이지로 이동하도록 수정할 에정
    },
    modifyArticle() {
      // db에 저장
      http
        .put(`/boards`, {
          boardid: this.$route.params.boardid,
          title: this.title,
          writer: JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid,
          nickname: JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.nickname,
          content: this.content,
        })
        .then()
        .catch();
      alert("수정이 완료되었습니다.");
      this.moveToBoard(); // 해당 디테일 페이지로 이동하도록 수정할 에정
    },
    moveToBoard() {
      this.$router.push("/board");
    },
  },
  created() {
    if (this.$route.params.boardid != 0) {
      http
        .get(`/boards/detail/${this.$route.params.boardid}`)
        .then(({ data }) => {
          this.title = data.title;
          this.content = data.content;
        })
        .catch();
      // 수정인경우
      this.title = "수정 될 글 번호 : " + this.boardid;
      this.content = "수정 될 내용";
    }
  },
};
</script>
<style></style>
