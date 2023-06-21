<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image" style="background-color: #ffae6b"> </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title" v-if="planid != 0">PLAN MODIFY</h1>
          <h1 class="title" v-else>PLAN REGIST</h1>
        </div>
      </div>
    </div>

    <div class="section text-center">
      <div class="container">
        <div class="card">
          <div class="card-header m-3" style="font-size: 22px">
            <textarea placeholder="Title" class="form-control" v-model="title" rows="1"> </textarea>
          </div>

          <div class="row px-4 py-3">
            <div class="col-md-6">
              <div class="datepicker-container">
                <fg-input>
                  <el-date-picker type="date" popper-class="date-picker" placeholder="START DATE" v-model="startDate">
                  </el-date-picker>
                </fg-input>
              </div>
            </div>
            <div class="col-md-6">
              <div class="datepicker-container">
                <fg-input>
                  <el-date-picker type="date" popper-class="date-picker" placeholder="END DATE" v-model="endDate">
                  </el-date-picker>
                </fg-input>
              </div>
            </div>
          </div>

          <!-- ############## DAY FORM ############## -->
          <div v-for="dayForm in dayForms" :key="dayForm.number">
            <h3 class="mt-5">DAY {{ dayForm.number }}</h3>
            <div class="mx-4">
              <fg-input
                placeholder="ESTIMATED COST"
                type="number"
                v-model="dayForm.cost"
                addon-left-icon="now-ui-icons business_money-coins"
              />
              <!-- <fg-input placeholder="PLACE" type="text" v-model="dayForm.place"
                addon-left-icon="now-ui-icons location_pin" /> -->
            </div>
            <div class="mx-4">
              <nav
                class="my-2 myplacearea"
                aria-label="breadcrumb"
                @click="
                  {
                    selectedDay = dayForm.number;
                    showModal = true;
                  }
                "
              >
                <ol class="breadcrumb">
                  <li class="breadcrumb-item active" aria-current="page">
                    {{ selectedPlace[dayForm.number - 1] ? selectedPlace[dayForm.number - 1] : "SELECT PLACE ..." }}
                  </li>
                </ol>
              </nav>
              <!-- <fg-input placeholder="PLACE" type="text" v-model="dayForm.place"
                addon-left-icon="now-ui-icons location_pin" /> -->
            </div>
            <div class="card-body" style="font-size: 16px">
              <textarea
                class="form-control"
                v-model="dayForm.content"
                rows="8"
                placeholder="계획을 작성해주세요!"
              ></textarea>
            </div>
          </div>
          <!-- ##################################### -->

          <div>
            <n-button class="ml-2" type="primary" round icon @click="addDay">
              <i class="now-ui-icons ui-1_simple-add"></i>
            </n-button>
            <n-button class="ml-2" type="primary" round icon @click="deleteDay">
              <i class="now-ui-icons ui-1_simple-delete"></i>
            </n-button>
          </div>

          <div class="m-1" style="float: right">
            <n-button type="neutral" @click="moveToProfile">CANCEL</n-button>
            <n-button type="neutral" @click="modifyPlan" v-if="planid != 0">MODIFY</n-button>
            <n-button type="neutral" @click="registPlan" v-else>REGIST</n-button>
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
import { Button, FormGroupInput, Modal } from "@/components";
import { DatePicker } from "element-ui";
import { mapState, mapActions, mapGetters } from "vuex";
import SearchMap from "@/components/SearchMap.vue";
import http from "@/util/http-common.js";
const placeStore = "placeStore";
export default {
  name: "PlanWrite",
  bodyClass: "landing-page",
  components: {
    Modal,
    SearchMap,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    [DatePicker.name]: DatePicker,
  },
  data() {
    return {
      selectedDay: 0,
      showModal: false,
      title: "",
      content: "",
      startDate: "",
      endDate: "",
      cost: "",
      dayForms: [], // DAY FORM의 데이터를 저장할 배열
      selectedPlace: [],
      plan: [],
      planid: null,
    };
  },
  computed: {
    ...mapState(placeStore, ["place"]),
    ...mapGetters("placeStore", ["checkPlace"]),
  },
  watch: {
    checkPlace(newVal) {
      this.selectedPlace[this.selectedDay - 1] = newVal.place_name + " (" + newVal.address_name + ")";
      console.log(this.selectedDay);
      const placeid = this.dayForms[this.selectedDay - 1].places.placeid;
      this.dayForms[this.selectedDay - 1].places = newVal;
      this.dayForms[this.selectedDay - 1].places.placeid = placeid;
      this.showModal = false;
    },
  },
  methods: {
    ...mapActions("placeStore", ["selectPlace"]),
    registPlan() {
      http
        .post(`/plans`, {
          title: this.title,
          startDate: this.startDate,
          endDate: this.endDate,
          days: this.dayForms,
        })
        .then(() => {
          alert("등록이 완료되었습니다.");
        })
        .catch(() => {
          alert("오류가 발생하였습니다.");
        });
      this.moveToProfile(); // 해당 디테일 페이지로 이동하도록 수정할 에정
    },
    modifyPlan() {
      console.log(this.dayForms);
      http
        .put(`/plans/${this.$route.params.planid}`, {
          id: this.$route.params.planid,
          title: this.title,
          startDate: this.startDate,
          endDate: this.endDate,
          days: this.dayForms,
        })
        .then(() => {
          alert("수정이 완료되었습니다.");
          //   this.moveToProfile(); // 해당 디테일 페이지로 이동하도록 수정할 에정
        })
        .catch(() => {
          alert("오류가 발생하였습니다.");
        });
    },
    moveToProfile() {
      this.$router.push("/profile");
    },
    addDay() {
      const dayNumber = this.dayForms.length + 1; // 새로운 DAY FORM의 번호
      const dayForm = {
        cost: "",
        content: "",
        number: dayNumber,
        places: [],
      }; // 새로운 DAY FORM의 초기 데이터
      this.dayForms.push(dayForm); // dayForms 배열에 추가
    },
    deleteDay() {
      if (this.dayForms.length > 1) {
        this.dayForms.pop(); // dayForms 배열에서 마지막 요소 삭제
      }
    },
    onPlaceChanged(dayForm) {
      dayForm.place = this.$refs.autocomplete.getPlace().formatted_address;
    },
  },
  created() {
    this.planid = this.$route.params.planid;
    if (this.$route.params.planid != 0) {
      http
        .get(`/plans/${this.$route.params.planid}`)
        .then(({ data }) => {
          console.log(data);
          this.plan = data;
          this.title = this.plan.title;
          this.startDate = this.plan.startDate;
          this.endDate = this.plan.endDate;

          for (let i = 0; i < this.plan.days.length; i++) {
            const day = this.plan.days[i];
            const dayForm = {
              cost: day.cost,
              content: day.content,
              dayid: day.dayid,
              planid: day.planid,
              number: i + 1,
              places: day.places,
            };
            this.selectedPlace[i] = day.places.place_name + " (" + day.places.address_name + ")";
            this.dayForms.push(dayForm);
          }
        })
        .catch((error) => {
          alert(error + "오류 발생");
        });
    } else {
      this.addDay(); // dayForm 최소 하나를 추가
      if (this.$route.query.title) {
        // 축제 계획을 세우는 경우
        this.title = this.$route.query.title;
        const startDateParts = this.$route.query.startdate.split("-");
        this.startDate = new Date(startDateParts[0], startDateParts[1] - 1, startDateParts[2]);
        const endDateParts = this.$route.query.enddate.split("-");
        this.endDate = new Date(endDateParts[0], endDateParts[1] - 1, endDateParts[2]);
      }
    }
  },
};
</script>
<style>
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
</style>
