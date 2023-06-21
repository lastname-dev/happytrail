<template>
  <div class="section section-nucleo-icons" data-background-color="white">
    <div class="container">
      <div class="row">
        <router-link to="/festival"><h2 class="title" style="color: #3e372d">Local Festival</h2></router-link>
        <div class="d-flex flex-row" style="overflow-x: auto">
          <transition-group name="fade">
            <div v-for="(festival, idx) in displayedFestivals" :key="idx" class="d-flex align-items-center mr-3 my-3">
              <img :src="festival.img" alt="image" class="col-md-3" @click="openModal(festival.festivalid)" />
            </div>
          </transition-group>
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
import { Button, Modal } from "@/components";

export default {
  components: {
    Modal,
    [Button.name]: Button,
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
  mounted() {
    // 초기 이미지 표시
    this.displayedFestivals = this.festivals.slice(0, 5); // 첫 5개 이미지를 표시

    // 이미지 순환 애니메이션 시작
    this.startImageAnimation();
  },
  methods: {
    startImageAnimation() {
      // 이미지 변경 주기마다 호출
      this.intervalId = setInterval(() => {
        // 다음 이미지를 표시하기 위해 인덱스를 업데이트
        this.currentIndex++;

        // 인덱스가 이미지 개수를 초과하면 처음으로 돌아감
        if (this.currentIndex >= this.festivals.length) {
          this.currentIndex = 0;
        }

        // 다음 이미지를 displayedFestivals에 넣어줌
        const startIndex = this.currentIndex;
        const endIndex = startIndex + 3; // 5개의 이미지를 표시하고 싶은 경우
        this.displayedFestivals = [...this.festivals.slice(startIndex, endIndex)];
      }, 2000);

      // 이미지 초기 표시 후 2초 뒤에 이미지 변경 시작
      setTimeout(() => {
        this.startImageAnimation();
      }, 3000);
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
  beforeDestroy() {
    // 컴포넌트가 파괴되기 전에 intervalId를 해제
    clearInterval(this.intervalId);
  },
  created() {
    http
      .get(`/festivals`) // db 다녀오기
      .then(({ data }) => {
        this.festivals = data;
        this.festivals.forEach((festival) => {
          festival.img = festival.img.split(",").map((filename) => `img/festival/${filename}`);
        });
      })
      .catch((error) => {
        console.error("Error fetching festivals:", error);
      });
  },
};
</script>

<style>
.modal-content {
  color: #3e372d;
}
</style>
