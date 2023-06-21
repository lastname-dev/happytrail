<template>
    <div>
        <div class="page-header page-header-small">
            <parallax
                class="page-header-image"
                style="background-color: #ffae6b"
            >
            </parallax>
            <div class="content-center">
                <div class="container">
                    <h1 class="title">{{ plan.title }}</h1>
                    <div class="text-center">
                        <h4>{{ plan.startdate }} ~ {{ plan.enddate }}</h4>
                        <h4>총 예상 경비 ₩ {{ totalcost }}</h4>

                        <div>
                            <n-button
                                class="transparent-button"
                                @click="addUser"
                                >ADDUSER</n-button
                            >
                            <!-- 계획에 다른 user 초대하기 -->
                            <n-button
                                class="transparent-button"
                                @click="modifyPlan"
                                >MODIFY</n-button
                            >
                            <n-button
                                class="transparent-button"
                                @click="deletePlan"
                                >DELETE</n-button
                            >
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="section text-center">
                <div v-for="(day, idx) in plan.days" :key="idx">
                    <h3>DAY {{ day.day }}</h3>
                    <h5 style="color: #7e7975">예상 경비 ₩ {{ day.cost }}</h5>
                    <p class="mb-5" style="width: 70%; margin: 0 auto">
                        {{ day.content }}
                    </p>
                </div>
            </div>

            <h2 class="text-center">여행 경로 MAP</h2>
            <div class="d-flex justify-content-center">
                <div id="map" style="width: 60%; height: 500px"></div>
                <div id="sidebar">
                    <div>
                        <div class="row">
                            <!-- <b>출발 : </b> -->
                            <select id="start" class="ml-3">
                                <option
                                    v-for="(day, idx) in plan.days"
                                    :key="idx"
                                    :value="day.day"
                                >
                                    {{ day.day }}일차
                                </option>
                            </select>
                            <!-- <br /> -->
                            <!-- <b>도착 : </b> -->
                            <select id="end" class="ml-1">
                                <option
                                    v-for="(day, idx) in plan.days"
                                    :key="idx"
                                    :value="day.day"
                                >
                                    {{ day.day }}일차
                                </option>
                            </select>
                            <!-- <br /> -->
                        </div>
                        <n-button id="submit" type="primary" @click="showView">
                            확인
                        </n-button>
                    </div>
                    <div id="directions-panel"></div>
                </div>
            </div>
            <div class="section text-center">
                <h3 v-if="plan.members.length">계획에 참여중인 사람들</h3>
                <div class="card mb-2" style="width: 100%">
                    <div class="row no-gutters">
                        <div
                            class="col-md-2 p-1"
                            v-for="(_, idx) in plan.members.length"
                            :key="idx"
                        >
                            <img
                                :src="peoples[idx]"
                                alt="..."
                                class="hover-user"
                                @click="deleteUser(plan.members[idx])"
                            />
                        </div>
                        <!-- <div class="col-md-2 p-1" v-for="(people, idx) in peoples" :key="idx">
              <img :src="people" alt="..." class="hover-user" @click="deleteUser" />
            </div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { Button, FormGroupInput } from "@/components";
import http from "@/util/http-common.js";
let directionsService;
let directionsRenderer;
export default {
    name: "BoardDetail",
    bodyClass: "landing-page",
    components: {
        [Button.name]: Button,
        [FormGroupInput.name]: FormGroupInput,
    },
    data() {
        return {
            totalcost: 0,
            planid: null,

            plan: {
                title: "제주 여행",
                startdate: "2023.04.22",
                enddate: "2023.05.15",
                totalcost: 0,
                days: [],
                members: [],
            },
            peoples: [
                "/img/bibi.png",
                "/img/bibi1.png",
                "/img/bibi2.png",
                "/img/bibi3.png",
            ],
        };
    },
    methods: {
        showPlan() {},
        modifyPlan() {
            this.$router.push({
                name: "PlanWrite",
                params: { planid: this.planid },
            });
        },
        deletePlan() {
            if (confirm("정말 삭제하시겠습니까?")) {
                http.delete(`/plans/${this.planid}`).then().catch();
                alert("삭제가 완료되었습니다.");
                this.$router.push("/profile");
            }
            this.$router.push("/profile");
        },
        deleteUser(id) {
            console.log(id);
            if (confirm("해당 유저를 계획에서 제거하시겠습니까?")) {
                http.delete(`/plans/${this.planid}/member/${id}`)
                    .then(() => {
                        alert("성공적으로 제거 되었습니다!");
                        location.reload();
                    })
                    .catch(() => {
                        alert("오류가 발생하였습니다.");
                    });
            }
        },
        addUser() {
            var userid = prompt("초대하고 싶은 user의 아이디를 입력해주세요!");
            if (userid != null && userid.trim() != "") {
                http.post(`/plans/${this.planid}/member/${userid}`)
                    .then(() => {
                        alert(userid + "님 초대 완료");
                        location.reload();
                    })
                    .catch(() => {
                        alert("존재하지 않는 회원입니다.");
                    });
            } else {
                alert("잘못된 아이디 입니다");
            }
        },
        initMap() {
            directionsService = new google.maps.DirectionsService();
            directionsRenderer = new google.maps.DirectionsRenderer();
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 14,
                center: { lat: 37.53484946518637, lng: 126.99253261317003 },
            });
            directionsRenderer.setMap(map);
        },
        showView() {
            const start = document.getElementById("start").value;
            const end = document.getElementById("end").value;
            this.calculateAndDisplayRoute(
                directionsService,
                directionsRenderer,
                start,
                end
            );
        },
        calculateAndDisplayRoute(
            directionsService,
            directionsRenderer,
            start,
            end
        ) {
            directionsService
                .route({
                    origin: new google.maps.LatLng(
                        this.plan.days[start - 1].places.y,
                        this.plan.days[end - 1].places.x
                    ),
                    destination: {
                        lat: this.plan.days[this.plan.days.length - 1].places.y,
                        lng: this.plan.days[this.plan.days.length - 1].places.x,
                    },
                    optimizeWaypoints: true,
                    travelMode: google.maps.TravelMode.TRANSIT,
                })
                .then((response) => {
                    directionsRenderer.setDirections(response);

                    const route = response.routes[0];
                    const summaryPanel =
                        document.getElementById("directions-panel");

                    summaryPanel.innerHTML = "";

                    // For each route, display summary information.
                    for (let i = 0; i < route.legs.length; i++) {
                        const routeSegment = i + 1;
                        console.log(route.legs[i]);
                        summaryPanel.innerHTML +=
                            "거리 " + route.legs[i].distance.text + "<br><br>";
                        // summaryPanel.innerHTML += "대중교통 " + route.legs[i].duration.text + "<br><br>";
                    }
                })
                .catch((e) =>
                    window.alert("Directions request failed due to " + e)
                );
        },
    },
    mounted() {
        this.planid = this.$route.params.planid;
        http.get(`/plans/${this.planid}`).then(({ data }) => {
            console.log(data);
            this.plan = data;
            this.plan.startdate = new Date(data.startDate).toLocaleDateString(
                "ko-KR"
            );
            this.plan.enddate = new Date(data.endDate).toLocaleDateString(
                "ko-KR"
            );
            for (const day of this.plan.days) {
                this.totalcost += day.cost;
                day.cost = day.cost
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
            this.totalcost = this.totalcost
                .toString()
                .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            if (this.plan.days.length > 1) {
                this.$nextTick(() => {
                    this.initMap();
                });
            }
        });
    },
};
</script>

<style>
.transparent-button {
    background-color: transparent !important;
}

.transparent-button:hover {
    background-color: #ffae6b17 !important;
}

.hover-user:hover {
    text-decoration: none;
    transform: scale(1.15);
}
#sidebar {
    flex-basis: 15rem;
    flex-grow: 1;
    padding: 1rem;
    max-width: 10rem;
    height: 100%;
    box-sizing: border-box;
    overflow: auto;
}

#map {
    flex-basis: 0;
    flex-grow: 4;
    height: 100%;
}

#directions-panel {
    margin-top: 10px;
}
</style>
