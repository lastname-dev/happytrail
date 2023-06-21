<template>
  <div>
    <div class="page-header clear-filter">
      <parallax class="page-header-image" style="background-image: url(img/hotplacebg4.jpg)"> </parallax>
    </div>

    <div class="section mt-5">
      <div class="row">
        <div class="col-md-9 ml-auto mr-auto">
          <div class="row collections">
            <div class="col-md-3" v-for="(festival, idx) in festivals" :key="idx">
              <div class="image-container">
                <img :src="festival.img" alt="image" class="img-raised" @click="openModal(festival.festivalid)" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 모달 -->
    <modal :show.sync="modals.classic" headerClasses="justify-content-center" class="modal-scrollable">
      <h3 slot="header" class="title title-up" style="color: #3e372d">{{ selectedFestival.title }}</h3>
      <div class="text-center my-3">
        <div style="width: 100%">
          <b>시작일</b> : {{ selectedFestival.startdate }} <br />
          <b>종료일</b> : {{ selectedFestival.enddate }} <br />
          <b>주소 </b> : {{ selectedFestival.address }} <br />
          <b>장소</b> : {{ selectedFestival.place }} <br />
          <br />
          <img :src="selectedFestival.img" alt="image" class="mb-4" />
          {{ selectedFestival.desc }}
        </div>
      </div>
      <div class="text-center">
        <n-button type="primary" class="btn btn-primary btn-round" @click="goToplan">계획 세우러 가기</n-button>
      </div>
    </modal>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { Modal, Button, FormGroupInput } from "@/components";
import { Tooltip } from "element-ui";

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Modal,
    [Button.name]: Button,
    [Tooltip.name]: Tooltip,
    [FormGroupInput.name]: FormGroupInput,
  },
  data() {
    return {
      festivals: [], // 모든 이미지가 담긴 배열
      selectedFestival: {
        title: "",
        place: "",
        address: "",
        desc: "",
        startdate: "",
        enddate: "",
      },
      modals: { classic: false },
      displayedFestivals: [], // 현재 표시되는 이미지 배열
      currentIndex: 0, // 현재 표시되는 이미지의 인덱스
      intervalId: null, // setInterval 함수의 ID
    };
  },
  methods: {
    performSearch() {
      console.log(this.keyword);
      let URL = `/hotplaces/search/${this.keyword}`;
      if (this.activeButton == "follow") URL = `/hotplaces/search/follow/${this.keyword}`;
      else if (this.activeButton == "like") URL = `/hotplaces/search/like/${this.keyword}`;

      http
        .get(URL)
        .then(({ data }) => {
          console.log("데이터");
          console.log(data);
          this.hotplaces = data;
          this.hotplaces.forEach((hotplace) => {
            hotplace.img = hotplace.urls
              .split(",")
              .map((url) => `http://localhost:8888/static/resources/upload/${url}`); // 임시 방편
          });
        })
        .catch();
    },
    showAll() {
      http
        .get(`/festivals`) // db 다녀오기
        .then(({ data }) => {
          this.festivals = data;
          this.festivals.forEach((festival) => {
            festival.img = festival.img.split(",").map((filename) => `/img/festival/${filename}`);
          });
        })
        .catch((error) => {
          console.error("Error fetching festivals:", error);
        });
    },
    openModal(festivalid) {
      this.modals.classic = true;
      console.log(festivalid);
      http
        .get(`/festivals/${festivalid}`) // db 다녀오기
        .then(({ data }) => {
          this.selectedFestival = data;
          this.selectedFestival.img = `img/festival/` + this.selectedFestival.img;
          console.log(this.selectedFestival);
        })
        .catch((error) => {
          console.error("Error fetching festivals:", error);
        });
    },
    goToplan() {
      this.$router.push({
        name: "PlanWrite",
        params: { planid: 0 },
        query: {
          title: this.selectedFestival.title,
          startdate: this.selectedFestival.startdate,
          enddate: this.selectedFestival.enddate,
        },
      });
    },
  },
  created() {
    this.showAll();
  },
};
</script>

<style>
.image-container {
  position: relative;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 92%;
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
</style>
