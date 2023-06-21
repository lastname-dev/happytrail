<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image" style="background-color: #ffae6b"> </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title" v-if="hotplaceid != 0">HOTPLACE MODIFY</h1>
          <h1 class="title" v-else>HOTPLACE REGIST</h1>
        </div>
      </div>
    </div>

    <div class="section text-center">
      <div class="container">
        <div class="card">
          <div class="card-header m-3" style="font-size: 22px">
            <textarea placeholder="Title" class="form-control" v-model="hotplace.title" rows="1"> </textarea>
          </div>

          <div class="card-body" style="font-size: 16px">
            <textarea
              class="form-control"
              v-model="hotplace.content"
              rows="100"
              style="height: 300px"
              placeholder="내용을 작성해주세요!"
            ></textarea>

            <div class="custom-file mt-4">
              <input
                type="file"
                class="custom-file-input"
                id="validatedCustomFile"
                required
                multiple
                @change="onFileChange"
              />
              <label v-if="hotplace.img.length" class="custom-file-label" for="validatedCustomFile">
                {{ hotplace.img.length }} 개의 이미지
              </label>
              <label v-else class="custom-file-label" for="validatedCustomFile">
                1개 이상의 이미지를 등록해야 합니다!
              </label>

              <div class="invalid-feedback">Example invalid custom file feedback</div>
            </div>

            <div class="d-flex flex-row" style="overflow-x: auto; max-height: 200px">
              <div v-for="(img, index) in hotplace.img" :key="index" class="d-flex align-items-center mr-3 my-3">
                <div class="mr-2">{{ img.split("/").pop().split("_").pop() }}</div>
                <i class="now-ui-icons ui-1_simple-remove" @click="removeImage(index)"></i>
              </div>
            </div>
            <nav class="my-2 myplacearea" aria-label="breadcrumb" @click="showModal = true">
              <ol class="breadcrumb">
                <li class="breadcrumb-item active" aria-current="page">{{ selectedPlace }}</li>
              </ol>
            </nav>
          </div>

          <div class="m-1" style="float: right">
            <n-button type="neutral" @click="moveToProfile">CANCEL</n-button>
            <n-button type="neutral" @click="modifyHotplace" v-if="hotplaceid != 0">MODIFY</n-button>
            <n-button type="neutral" @click="registHotplace" v-else>REGIST</n-button>
          </div>
        </div>
      </div>
    </div>

    <modal :show.sync="showModal" headerClasses="justify-content-center">
      <h2 slot="header" class="title title-up mb-5">SEARCH PLACE</h2>
      <div class="text-center">
        <search-map></search-map>
      </div>
    </modal>
  </div>
</template>
<script>
import { Button, Modal, FormGroupInput } from "@/components";
import { mapState, mapActions, mapGetters } from "vuex";
import SearchMap from "@/components/SearchMap.vue";
import http from "@/util/http-common.js";

const placeStore = "placeStore";
export default {
  name: "BoardDetail",
  bodyClass: "landing-page",
  components: {
    Modal,
    SearchMap,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  computed: {
    ...mapState(placeStore, ["place"]),
    ...mapGetters("placeStore", ["checkPlace"]),
  },
  watch: {
    checkPlace(newVal) {
      this.place = newVal;
      this.selectedPlace = newVal.place_name + " (" + newVal.address_name + ")";
      this.showModal = false;
    },
  },
  data() {
    return {
      showModal: false,
      keyword: "",
      hotplace: { img: [] },
      selectedFiles: [],
      deleted: new Set(),
    };
  },
  methods: {
    ...mapActions("placeStore", ["selectPlace"]),
    registHotplace() {
      const hotplaceDto = {
        hotplaceid: 0,
        title: this.hotplace.title,
        likecnt: 0,
        commentcnt: 0,
        content: this.hotplace.content,
        writtendate: null,
        writer: this.userInfo.userid,
        nickname: this.userInfo.nickname,
        placename: this.place.place_name,
        placeaddress: this.place.address_name,
        lat: this.place.y,
        lng: this.place.x,
        urls: this.hotplace.img.join(","),
      };

      const formData = new FormData();
      formData.append("hotplaceDto", JSON.stringify(hotplaceDto));
      for (let i = 0; i < this.hotplace.img.length; i++) {
        formData.append("images", this.selectedFiles[i], this.hotplace.img[i]);
      }
      console.log(formData);

      if (this.hotplace.img.length === 0) alert("1장 이상의 이미지 등록은 필수입니다!");
      else {
        http
          .post("/hotplaces", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then(({ data }) => {
            alert("등록이 완료되었습니다.");
            this.$router.push({ name: "HotplaceDetail", params: { hotplaceid: data } });
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },

    modifyHotplace() {
      const hotplaceModifyDto = {
        // hotplaceModifyDto 객체 생성
        hotplaceid: this.hotplaceid,
        title: this.hotplace.title,
        content: this.hotplace.content,
        nickname: this.userInfo.nickname,
        placename: this.place.place_name,
        placeaddress: this.place.address_name,
        lat: this.place.y,
        lng: this.place.x,
        urls: this.hotplace.img.join(","),
        deleted: [...this.deleted],
      };

      if (this.hotplace.img.length === 0) alert("1장 이상의 이미지는 필수입니다!");
      else {
        // 서버로 데이터 전송
        http
          .put("/hotplaces", hotplaceModifyDto)
          .then(() => {
            alert("수정이 완료되었습니다.");
            this.$router.push({ name: "HotplaceDetail", params: { hotplaceid: this.hotplaceid } }); // 해당 디테일 페이지로 이동
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },

    moveToProfile() {
      this.$router.push("/profile");
    },

    onFileChange(event) {
      const newSelectedFiles = Array.from(event.target.files); // 새로 선택된 파일들을 newSelectedFiles에 할당
      const newSelectedFileNames = newSelectedFiles.map((file) => file.name); // 새로 선택된 파일들의 이름을 newSelectedFileNames 배열에 할당
      this.hotplace.img = [...this.hotplace.img, ...newSelectedFileNames]; // 기존의 hotplace.img 배열에 새로 선택된 파일들의 이름을 추가
      this.selectedFiles = [...this.selectedFiles, ...newSelectedFiles]; // 선택된 파일들을 selectedFiles에 추가
      // this.selectedFileNames = [...this.selectedFileNames, ...newSelectedFileNames]; // 선택된 파일들의 이름을 selectedFileNames 배열에 추가
      this.$forceUpdate(); // Vue에게 변경된 데이터를 감지하도록 강제로 업데이트를 요청
      console.log(this.hotplace.img.map((img) => img.split("/").pop().split("_").pop()).join(","));
    },

    removeImage(index) {
      this.deleted.add(this.hotplace.img[index]); // 삭제할 목록
      console.log(this.hotplace.urls);
      this.hotplace.img.splice(index, 1); // hotplace.img 배열에서 해당 이미지 제거
      this.selectedFiles.splice(index, 1); // selectedFiles 배열에서도 제거
      // this.selectedFileNames.splice(index, 1); // 선택된 파일 이름 배열에서도 제거
      this.$forceUpdate(); // Vue에게 변경된 데이터를 감지하도록 강제로 업데이트를 요청
    },
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem("vuex")).memberStore.userInfo;
    this.hotplaceid = this.$route.params.hotplaceid;
    this.selectedPlace = "SELECT PLACE ...";
    this.hotplace.img = []; // img 속성을 빈 배열로 초기화
    if (this.$route.params.hotplaceid != 0) {
      // 수정인경우
      http
        .get(`/hotplaces/${this.hotplaceid}`)
        .then(({ data }) => {
          this.hotplace = data;
          this.hotplace.img = this.hotplace.urls.split(",");
          this.selectedPlace = this.hotplace.placename + " (" + this.hotplace.placeaddress + ")";
        })
        .catch();
    }
  },
};
</script>
<style scoped>
.search-map {
  transform: scale(0.5);
  /* 원하는 축소 비율로 수정 */
}

.myplacearea {
  background-color: white;
}

.custom-modal .modal-content {
  width: 80%;
  height: 80%;
  max-width: 800px;
  max-height: 600px;
}

.now-ui-icons.ui-1_simple-remove {
  /* 기본 스타일 */
  padding: 8px;
  /* 여백 추가 */
}

.now-ui-icons.ui-1_simple-remove:hover {
  background-color: rgba(0, 0, 0, 0.1);
}
</style>
