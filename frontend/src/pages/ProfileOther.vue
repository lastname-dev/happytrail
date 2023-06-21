<template>
    <div>
        <div class="page-header clear-filter">
            <parallax
                class="page-header-image"
                style="background-image: url('img/jeju.jpg')"
            >
            </parallax>
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
                <div class="button-container">
                    <a
                        @click="changeFollow"
                        class="btn btn-primary btn-round btn-lg"
                        >{{ followState ? "Follow" : "Unfollow" }}</a
                    >
                </div>
                <h3 class="title">About me</h3>
                <h5 class="description">{{ userinfo.intro }}</h5>

                <div class="row text-center">
                    <tabs
                        pills
                        class="nav-align-center"
                        tab-content-classes="gallery"
                        tab-nav-classes="nav-pills-just-icons"
                        type="primary"
                    >
                        <!-- ############################## RECORD TAB ############################## -->
                        <tab-pane title="Hotplace">
                            <i
                                slot="label"
                                class="now-ui-icons media-1_album"
                            ></i>

                            <div class="center-align-input">
                                <div class="row collections text-center">
                                    <div
                                        class="col-md-4"
                                        v-for="(hotplace, idx) in hotplaces"
                                        :key="idx"
                                    >
                                        <div class="image-container">
                                            <img
                                                :src="hotplace.img[0]"
                                                alt="image"
                                                class="img-raised"
                                            />
                                            <div
                                                class="overlay"
                                                @click="
                                                    goToHotplaceDetailPage(
                                                        hotplace.hotplaceid
                                                    )
                                                "
                                            >
                                                <div class="overlay-content">
                                                    <span class="likes">
                                                        <i
                                                            slot="label"
                                                            class="now-ui-icons ui-2_favourite-28"
                                                            >{{
                                                                hotplace.likecnt
                                                            }}</i
                                                        >
                                                    </span>
                                                    <span class="comments">
                                                        <i
                                                            slot="label"
                                                            class="now-ui-icons ui-2_chat-round"
                                                        >
                                                            {{
                                                                hotplace.commentcnt
                                                            }}
                                                        </i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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

export default {
    name: "profileother",
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
            followState: true,
            hotplaces: [],
            userinfo: {
                nickname: "BIBI",
                userpw: "",
                phone: "010-1004-1004",
                email: "bibi@happy.com",
                birthday: "",
                intro: "",
            },
            //   plans: [
            //     {
            //       title: "부산 여행",
            //       date: "2023.08.20~2023.08.22",
            //     },
            //     {
            //       title: "경주 여행",
            //       date: "2023.08.20~2023.08.22",
            //     },
            //     {
            //       title: "여수 여행",
            //       date: "2023.08.20~2023.08.22",
            //     },
            //     {
            //       title: "제주 여행",
            //       date: "2023.08.20~2023.08.22",
            //     },
            //     {
            //       title: "담양 여행",
            //       date: "2023.08.20~2023.08.22",
            //     },
            //   ],
        };
    },
    methods: {
        changeFollow() {
            console.log(this.userinfo.userid);
            http.post(`/users/follow/${this.userinfo.userid}`)
                .then(() => {
                    this.followState = !this.followState;
                })
                .catch(() => {
                    console.log("실패");
                });
        },
        showMyHotplace() {
            http.get(`/hotplaces/user/${this.userinfo.userid}`) // db 다녀오기
                .then(({ data }) => {
                    this.hotplaces = data;
                    this.hotplaces.forEach((hotplace) => {
                        hotplace.img = hotplace.urls
                            .split(",")
                            .map(
                                (url) =>
                                    `http://localhost:8888/static/resources/upload/${url}`
                            );
                    });
                    console.log(this.hotplaces[0].img);
                })
                .catch();
        },
        goToHotplaceDetailPage(hotplaceid) {
            this.$router.push({
                name: "HotplaceDetail",
                params: { hotplaceid: hotplaceid },
            });
        },
    },
    created() {
        this.userid = this.$route.params.userid;
        http.get(`/users/info/${this.userid}`)
            .then(({ data }) => {
                console.log(data);
                if (data.userInfo.deleted) {
                    alert("탈퇴한 회원입니다");
                    this.$router.push({
                        name: "board",
                    });
                }
                this.userinfo = data.userInfo;
                console.log(this.userinfo);
                this.followState = data.followstate;

                this.showMyHotplace();
            })
            .catch();
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
