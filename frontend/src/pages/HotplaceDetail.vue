<template>
    <div>
        <div class="page-header page-header-small">
            <parallax
                class="page-header-image"
                style="background-color: #ffae6b"
            >
            </parallax>
            <!-- <parallax class="page-header-image" :style="{ 'background-image': 'url(' + hotplace.img[0] + ')' }"></parallax> -->

            <div class="content-center">
                <div class="container">
                    <h1 class="title">{{ hotplace.title }}</h1>
                    <div class="text-center">
                        <h3>{{ hotplace.nickname }}</h3>
                        <h5>
                            {{ hotplace.placename }} (
                            {{ hotplace.placeaddress }} )
                        </h5>
                        <h5>{{ hotplace.writtendate }}</h5>
                    </div>
                    <div v-if="hotplace.writer == this.userid">
                        <n-button
                            class="transparent-button"
                            @click="modifyHotplace"
                            >MODIFY</n-button
                        >
                        <n-button
                            class="transparent-button"
                            @click="deleteHotplace"
                            >DELETE</n-button
                        >
                    </div>
                </div>
            </div>
        </div>

        <div class="image-container mt-5 p-4">
            <!-- <img :src="hotplace.img" alt="image" class="img-raised" style="width: 50%" /> -->

            <div
                id="carouselExampleControls"
                class="carousel slide col-md-4 my-4"
                data-ride="carousel"
                data-interval="false"
                style="
                    width: 100%;
                    height: 600px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    overflow: hidden;
                "
            >
                <div class="carousel-inner">
                    <div
                        v-for="(image, index) in hotplace.img"
                        :key="index"
                        :class="['carousel-item', { active: index === 0 }]"
                    >
                        <img
                            :src="image"
                            :alt="'Image ' + (index + 1)"
                            class="w-100"
                            style="object-fit: cover; height: 100%"
                        />
                    </div>
                </div>
                <a
                    class="carousel-control-prev"
                    href="#carouselExampleControls"
                    role="button"
                    data-slide="prev"
                >
                    <span
                        class="carousel-control-prev-icon"
                        aria-hidden="true"
                    ></span>
                </a>
                <a
                    class="carousel-control-next"
                    href="#carouselExampleControls"
                    role="button"
                    data-slide="next"
                >
                    <span
                        class="carousel-control-next-icon"
                        aria-hidden="true"
                    ></span>
                </a>
            </div>

            <div class="text-center m-5 col-md-6">
                {{ hotplace.content }}

                <div class="mt-5 p-2">
                    <n-button
                        :type="likestate ? 'primary' : 'default'"
                        round
                        icon
                        @click="likestateChange"
                    >
                        <i class="now-ui-icons ui-2_favourite-28"></i>
                    </n-button>

                    <n-button
                        class="ml-2"
                        type="primary"
                        round
                        icon
                        @click.native="modals.classic = true"
                    >
                        <i class="now-ui-icons ui-2_chat-round"></i>
                    </n-button>
                </div>
            </div>
        </div>

        <!-- Classic Modal -->
        <modal
            :show.sync="modals.classic"
            headerClasses="justify-content-center"
            class="modal-scrollable"
        >
            <h4 slot="header" class="title title-up">Comment</h4>
            <div v-if="comments.length == 0" class="text-center my-3">
                첫 번째 댓글을 남겨보세요!
            </div>
            <div v-else class="text-center my-3">
                <nav
                    v-for="(comment, idx) in comments"
                    :key="idx"
                    aria-label="breadcrumb"
                    class="comment-wrapper"
                >
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item active" aria-current="page">
                            {{ comment.content }}
                        </li>
                        <p
                            class="comment-date"
                            style="font-size: small; right: -15px"
                        >
                            {{ comment.writtendate }}
                            <i
                                v-if="comment.writer == userid"
                                style="
                                    position: relative;
                                    top: -25px;
                                    left: -10px;
                                "
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
        </modal>
    </div>
</template>
<script>
import { Button, Modal, FormGroupInput } from "@/components";
import "jquery/dist/jquery.min.js"; // jQuery 추가
import "bootstrap/dist/js/bootstrap.bundle.min.js"; // Bootstrap JavaScript 추가
import http from "@/util/http-common.js";

export default {
    name: "HotplaceDetail",
    bodyClass: "landing-page",
    components: {
        Modal,
        [Button.name]: Button,
        [FormGroupInput.name]: FormGroupInput,
    },
    data() {
        return {
            userid: "",
            likestate: false,
            modals: { classic: false },
            hotplace: {},
            comment: "",
            comments: [
                { content: "우와 멋져요", writtendate: "2023.06.04 23:11" },
                { content: "저도 꼭 갈래요!", writtendate: "2023.06.04 23:33" },
                {
                    content: "사진이 너무 예쁘네요!!",
                    writtendate: "2023.06.04 23:58",
                },
            ],
            userCheck: false,
        };
    },
    methods: {
        prevSlide() {
            $("#carouselExampleControls").carousel("prev");
        },
        nextSlide() {
            $("#carouselExampleControls").carousel("next");
        },
        likestateChange() {
            if (this.likestate) {
                // true -> false
                http.delete(`/hotplaces/${this.hotplaceid}/like/${this.userid}`)
                    .then()
                    .catch();
            } else {
                // false -> true
                http.post(`/hotplaces/${this.hotplaceid}/like/${this.userid}`)
                    .then()
                    .catch();
            }
            this.likestate = !this.likestate;
        },
        submit() {
            if (this.comment.trim() === "") return;
            http.post(`hotplaces/${this.hotplaceid}/comments`, {
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
        fetchData() {
            http.get(`/hotplaces/${this.hotplaceid}`)
                .then(({ data }) => {
                    console.log(data);
                    this.hotplace = data;
                    this.comments = data.comments;
                    if (
                        JSON.parse(sessionStorage.getItem("vuex")).memberStore
                            .userInfo.nickname === this.hotplace.writer
                    ) {
                        this.userCheck = true;
                    } else {
                        this.userCheck = false;
                    }
                })
                .catch(() => {
                    alert("데이터를 가져오는 중에 오류가 발생했습니다.");
                });
        },
        deleteComment(id) {
            http.delete(`hotplaces/comments/${id}`)
                .then(() => {
                    alert("삭제완료");
                    this.fetchData(); // 댓글 삭제 후 데이터 다시 가져오기
                })
                .catch(() => {
                    alert("오류가 발생하였습니다.");
                });
        },
        modifyHotplace() {
            this.$router.push({
                name: "HotplaceWrite",
                params: { hotplaceid: this.hotplaceid },
            });
        },
        deleteHotplace() {
            // 관련 댓글 먼저 삭제 필요
            if (confirm("정말 삭제하시겠습니까?")) {
                http.delete(`/hotplaces/${this.hotplaceid}`)
                    .then(() => {
                        alert("삭제가 완료되었습니다.");
                        this.$router.push("/profile");
                    })
                    .catch((error) => {
                        console.error("삭제 중 오류가 발생했습니다:", error);
                    });
            }
        },
    },
    created() {
        this.hotplaceid = this.$route.params.hotplaceid;
        this.userid = JSON.parse(
            sessionStorage.getItem("vuex")
        ).memberStore.userInfo.userid;
        http.get(`/hotplaces/${this.hotplaceid}/like/${this.userid}`)
            .then(({ data }) => {
                this.likestate = data == 1 ? true : false;
            })
            .catch();
        http.get(`/hotplaces/${this.hotplaceid}`)
            .then(({ data }) => {
                console.log(data);
                this.hotplace = data;
                this.comments = data.comments;
                this.hotplace.img = this.hotplace.urls
                    .split(",")
                    .map(
                        (url) =>
                            `http://localhost:8888/static/resources/upload/${url}`
                    );
            })
            .catch();
    },
};
</script>
<style scoped>
.carousel .carousel-inner {
    display: flex;
}

.carousel .carousel-inner .carousel-item {
    flex: 0 0 100%;
    max-width: 100%;
    height: auto;
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

<style>
@media (max-width: 767px) {
    #carouselExampleControls {
        width: 300px;
    }
}

@media (min-width: 768px) and (max-width: 991px) {
    #carouselExampleControls {
        width: 400px;
    }
}

@media (min-width: 992px) and (max-width: 1199px) {
    #carouselExampleControls {
        width: 500px;
    }
}

@media (min-width: 1200px) {
    #carouselExampleControls {
        width: 600px;
    }
}
</style>
