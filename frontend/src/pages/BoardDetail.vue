<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image" style="background-color: #ffae6b"> </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">{{ article.title }}</h1>
          <div class="text-center">
            <h4>{{ article.nickname }}</h4>
            <h4>{{ article.writtendate }}</h4>
          </div>
        </div>
        <div v-if="userCheck">
          <n-button class="transparent-button" @click="modifyArticle">MODIFY</n-button>
          <n-button class="transparent-button" @click="deleteArticle">DELETE</n-button>
        </div>
      </div>
    </div>

    <div class="section text-center">
      <div class="container">
        <div class="card">
          <div class="card-body">
            <p class="card-text p-5">{{ article.content }}</p>
            <n-button class="ml-2" type="primary" round icon @click.native="modals.classic = true">
              <i class="now-ui-icons ui-2_chat-round"></i>
            </n-button>
          </div>
        </div>

        <div class="card mb-2" style="width: 100%">
          <div class="row no-gutters">
            <div class="col-md-2">
              <img src="/img/bibi.png" alt="..." />
            </div>
            <div class="col-md-10">
              <div class="card-body">
                <router-link :to="`/profileother/${article.writer}`">
                  <h3 class="card-title hover-link">
                    {{ article.nickname }}
                  </h3>
                </router-link>

                <p class="card-text mt-4">소중한 것을 깨닫는 장소는 언제나 컴퓨터 앞이 아니라 파란 하늘 아래였다</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <modal :show.sync="modals.classic" headerClasses="justify-content-center">
      <h4 slot="header" class="title title-up">Comment</h4>
      <div v-if="comments.length == 0" class="text-center my-3">첫 번째 댓글을 남겨보세요!</div>
      <div v-else class="text-center my-3">
        <nav v-for="(comment, idx) in comments" :key="idx" aria-label="breadcrumb" class="comment-wrapper">
          <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">
              {{ comment.content }}
            </li>
            <p class="comment-date" style="font-size: small; right: -15px">
              {{ comment.writtendate }}
              <i
                v-if="comment.writer == userid"
                style="position: relative; top: -25px; left: -10px"
                class="now-ui-icons ui-1_simple-remove"
                @click="deleteComment(comment.commentid)"
              ></i>
            </p>
          </ol>
        </nav>
      </div>
      <div class="text-center">
        <div class="container d-flex">
          <div class="mt-3" style="width: 100%">
            <fg-input
              addon-left-icon="now-ui-icons users_single-02"
              placeholder="Write Comment ... "
              @keyup.enter="submit"
              v-model="comment"
            ></fg-input>
          </div>
        </div>
      </div>
      <!-- <template slot="footer">
        <n-button>Nice Button</n-button>
        <n-button type="primary" @click.native="modals.classic = false">Close</n-button>
      </template> -->
    </modal>
  </div>
</template>
<script>
import { Button, Modal, FormGroupInput } from "@/components";
import http from "@/util/http-common.js";

import "jquery/dist/jquery.min.js"; // jQuery 추가
import "bootstrap/dist/js/bootstrap.bundle.min.js"; // Bootstrap JavaScript 추가

export default {
  name: "BoardDetail",
  bodyClass: "landing-page",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    Modal,
  },
  data() {
    return {
      userid: "",
      modals: {
        classic: false,
      },
      comment: "",
      comments: [],
      article: {},
      userCheck: false,
    };
  },
  methods: {
    modifyArticle() {
      this.$router.push({
        name: "BoardWrite",
        params: { boardid: this.boardid },
      });
    },
    deleteArticle() {
      if (confirm("정말 삭제하시겠습니다?")) {
        http.delete(`/boards/${this.boardid}`).then().catch();
        alert("삭제가 완료되었습니다.");
        this.$router.push("/board");
      }
    },
    fetchData() {
      http
        .get(`/boards/detail/${this.boardid}`)
        .then(({ data }) => {
          this.article = data;
          this.comments = data.comments;
          if (JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.nickname === this.article.nickname) {
            this.userCheck = true;
          } else {
            this.userCheck = false;
          }
        })
        .catch(() => {
          alert("데이터를 가져오는 중에 오류가 발생했습니다.");
        });
    },
    submit() {
      if (this.comment.trim() === "") return; // 이거 지우면 빈문자열도 등록돼서 안돼!!!! (댓글 + 빈문자열 두개 등록 돼..)
      http
        .post(`boards/${this.boardid}/comments`, {
          content: this.comment,
        })
        .then(() => {
          this.comment = "";
          this.fetchData(); // 댓글 입력 후 데이터 다시 가져오기
        })
        .catch(() => {
          alert("오류가 발생하였습니다.");
        });
    },

    deleteComment(id) {
      http
        .delete(`boards/comments/${id}`)
        .then(() => {
          alert("삭제완료");
          this.fetchData(); // 댓글 삭제 후 데이터 다시 가져오기
        })
        .catch(() => {
          alert("오류가 발생하였습니다.");
        });
    },
  },
  created() {
    this.boardid = this.$route.params.boardid;
    this.userid = JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid;
    http
      .get(`/boards/detail/${this.boardid}`)
      .then(({ data }) => {
        this.article = data;
        this.comments = data.comments;
        if (JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.nickname === this.article.nickname) {
          this.userCheck = true;
        } else {
          this.userCheck = false;
        }
      })
      .catch();
  },
};
</script>
<style scoped>
.hover-link:hover {
  text-decoration: none;
  color: rgb(231, 157, 46);
}

.transparent-button {
  background-color: transparent;
}

.transparent-button:hover {
  background-color: #ffae6b17;
}
.comment-wrapper {
  position: relative;
}

.comment-date {
  position: absolute;
  bottom: 0;
  right: 0;
  margin-bottom: 5px;
  margin-right: 10px;
}
.transparent-button {
  background-color: transparent;
}
.transparent-button:hover {
  background-color: #ffae6b17;
}
</style>
<style lang="scss">
#javascriptComponents {
  .modal-buttons,
  .popover-buttons,
  .tooltip-buttons {
    .btn + .btn {
      margin-left: 3px;
    }
  }
}
</style>
