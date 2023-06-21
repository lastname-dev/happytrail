<template>
  <div>
    <div class="page-header clear-filter">
      <parallax class="page-header-image" style="background-image: url('img/jeju.jpg')"> </parallax>
      <div class="container">
        <div class="photo-container">
          <img src="img/bibi.png" alt="" />
        </div>
        <h3 class="title">{{ this.userinfo.nickname }}</h3>
        <p class="category">traveler</p>
        <div class="content">
          <div class="social-description">
            <h2>{{ this.userinfo.posts }}</h2>
            <p>post</p>
          </div>
          <div class="social-description">
            <h2>{{ this.userinfo.follower }}</h2>
            <p>follower</p>
          </div>
          <div class="social-description">
            <h2>{{ this.userinfo.follow }}</h2>
            <p>follow</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <h3 class="title">About me</h3>
        <h5 class="description">{{ userinfo.intro }}</h5>
        <div class="row text-center">
          <div class="col-md-6 ml-auto mr-auto">
            <h2 class="title text-center">My Trip</h2>
          </div>

          <!-- ############################## PLAN TAB ############################## -->
          <tabs
            pills
            class="nav-align-center"
            tab-content-classes="gallery"
            tab-nav-classes="nav-pills-just-icons"
            type="primary"
          >
            <tab-pane title="Plans">
              <i slot="label" class="now-ui-icons design_bullet-list-67"></i>

              <div class="col-md-10 ml-auto mr-auto">
                <div class="row collections">
                  <div class="col-md-4">
                    <div class="image-container">
                      <img
                        src="img/plus.png"
                        alt="image"
                        class="hover-add"
                        style="width: 30%"
                        @click="goToPlanRegistPage"
                      />
                    </div>
                  </div>
                  <div class="col-md-4" v-for="(plan, idx) in plans" :key="idx">
                    <div class="card">
                      <div class="card-body">
                        <h5 class="card-title">
                          {{ plan.title }}
                        </h5>
                        <p class="card-text">
                          {{ plan.date }}
                        </p>
                        <a class="btn btn-primary" @click="goToPlanDetailPage(plan.planid)">DETAIL</a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </tab-pane>

            <!-- ############################## RECORD TAB ############################## -->
            <tab-pane title="Hotplace">
              <i slot="label" class="now-ui-icons media-1_album"></i>

              <div class="center-align-input">
                <div class="row collections text-center">
                  <div class="col-md-4">
                    <div class="image-container">
                      <img
                        src="img/plus.png"
                        alt="image"
                        class="hover-add"
                        style="width: 30%"
                        @click="goToHotplaceRegistPage"
                      />
                    </div>
                  </div>

                  <div class="col-md-4" v-for="(hotplace, idx) in hotplaces" :key="idx">
                    <div class="image-container">
                      <img :src="hotplace.img[0]" alt="image" class="img-raised" />
                      <div class="overlay" @click="goToHotplaceDetailPage(hotplace.hotplaceid)">
                        <div class="overlay-content">
                          <span class="likes">
                            <i slot="label" class="now-ui-icons ui-2_favourite-28">{{ hotplace.likecnt }}</i>
                          </span>
                          <span class="comments">
                            <i slot="label" class="now-ui-icons ui-2_chat-round">
                              {{ hotplace.commentcnt }}
                            </i>
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </tab-pane>

            <!-- ############################## INFO TAB ############################## -->
            <tab-pane title="Info">
              <i slot="label" class="now-ui-icons users_single-02"></i>

              <div class="center-align-input my-input">
                <div class="row collections text-center">
                  <template>
                    <fg-input
                      class="no-border"
                      addon-left-icon="now-ui-icons users_circle-08"
                      v-model="userinfo.nickname"
                      value=""
                    />

                    <fg-input
                      class="no-border"
                      addon-left-icon="now-ui-icons objects_key-25"
                      placeholder="PASSWORD"
                      type="password"
                      v-model="userinfo.userpw"
                    />

                    <fg-input
                      class="no-border"
                      placeholder="PHONE"
                      type="tel"
                      v-model="userinfo.phone"
                      addon-left-icon="now-ui-icons tech_tablet"
                    />

                    <fg-input
                      class="no-border"
                      placeholder="E-MAIL"
                      type="email"
                      v-model="userinfo.email"
                      addon-left-icon="now-ui-icons ui-1_email-85"
                    />

                    <fg-input
                      class="no-border"
                      placeholder="AboutMe"
                      type="text"
                      v-model="userinfo.intro"
                      addon-left-icon="now-ui-icons design-2_ruler-pencil"
                    />

                    <div class="date-picker-container">
                      <fg-input>
                        <el-date-picker
                          class="no-border"
                          placeholder="BIRTHDAY"
                          type="date"
                          v-model="userinfo.birthday"
                        />
                      </fg-input>
                    </div>
                  </template>
                </div>
              </div>

              <div class="text-center p-2 m-1">
                <n-button type="neutral" class="btn-md-custom" round size="md" @click="modifyInfo">MODIFY</n-button>
                <n-button type="neutral" class="btn-md-custom" round size="md" @click="signOut">SIGNOUT</n-button>
              </div>
            </tab-pane>
          </tabs>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Button, FormGroupInput, Tabs, TabPane } from "@/components";
import { DatePicker } from "element-ui";
import http from "@/util/http-common.js";
import { mapState } from "vuex";

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Tabs,
    TabPane,
    [Button.name]: Button,
    [DatePicker.name]: DatePicker,
    [FormGroupInput.name]: FormGroupInput,
  },
  data() {
    return {
      userinfo: {
        nickname: "BIBI",
        userpw: "",
        phone: "010-1004-1004",
        email: "bibi@happy.com",
        birthday: "",
        intro: "",
      },
      followState: true,
      hotplaces: [],
      plans: [{}],
    };
  },
  methods: {
    changeFollow() {
      this.followState = !this.followState;
    },
    modifyInfo() {
      console.log(this.userinfo);
      http
        .put(`/users/${JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid}`, {
          userpw: this.userinfo.userpw,
          nickname: this.userinfo.nickname,
          phone: this.userinfo.phone,
          email: this.userinfo.email,
          birthday: this.userinfo.birthday,
          intro: this.userinfo.intro,
        })
        .then()
        .catch();
      alert("수정 되었습니다.");
    },
    signOut() {
      if (confirm("정말 탈퇴하시겠습니까?")) {
        http
          .delete(`/users/${JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid}`)
          .then(() => {
            alert("탈퇴 완료되었습니다. 그 동안 EnjoyTrip을 이용해주셔서 감사합니다!");
            this.$router.push("/");
          })
          .catch(() => {
            alert("오류가 발생하였습니다. 다시 시도해주세요");
          });
      }
    },
    goToPlanRegistPage() {
      this.$router.push({ name: "PlanWrite", params: { planid: 0 } });
    },
    goToPlanDetailPage(id) {
      this.$router.push({ name: "PlanDetail", params: { planid: id } });
    },

    goToHotplaceRegistPage() {
      this.$router.push({
        name: "HotplaceWrite",
        params: { hotplaceid: 0 },
      });
    },
    goToHotplaceDetailPage(hotplaceid) {
      this.$router.push({
        name: "HotplaceDetail",
        params: { hotplaceid: hotplaceid },
      });
    },
    showMyHotplace() {
      let userid = JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid;
      http
        .get(`/hotplaces/user/${userid}`) // db 다녀오기
        .then(({ data }) => {
          this.hotplaces = data;
          this.hotplaces.forEach((hotplace) => {
            hotplace.img = hotplace.urls
              .split(",")
              .map((url) => `http://localhost:8888/static/resources/upload/${url}`);
          });
          console.log(this.hotplaces[0].img);
        })
        .catch();
    },
  },
  created() {
    let userid = JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid;

    http
      .get(`/users/info/${JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo.userid}`)
      .then(({ data }) => {
        this.userinfo = data.userInfo;
      })
      .catch();
    http.get(`/plans/user/${userid}`).then(({ data }) => {
      this.plans = data;
      console.log(this.plans);
      for (const plan of this.plans) {
        plan.date =
          new Date(plan.startdate).toLocaleDateString("ko-KR") +
          " ~ " +
          new Date(plan.enddate).toLocaleDateString("ko-KR");
      }
    });
    this.showMyHotplace();
  },
};
</script>

<style>
.hover-add:hover {
  text-decoration: none;
  transform: scale(1.15);
}

.my-input {
  width: 50%;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

.image-container {
  /* position: relative; */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.overlay {
  width: 100%;
  height: 90%;
  background-color: rgba(173, 173, 173, 0.242);
  opacity: 0;
  transition: opacity 0.3s;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  cursor: pointer;
}

.overlay:hover {
  opacity: 1;
}

.overlay-content {
  color: white;
  font-size: 18px;
  display: flex;
  align-items: center;
}

.overlay-content span {
  margin: 0 5px;
}

.overlay-link {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

/* 추가된 스타일 */
.date-picker-container {
  position: relative;
  display: inline-block;
  width: 100%;
}

.date-picker-input {
  width: 100%;
}

.date-picker-calendar {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 9999;
  width: 100%;
}

.date-picker-calendar .el-date-picker {
  width: 100%;
}
</style>
