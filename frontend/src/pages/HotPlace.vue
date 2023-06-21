<template>
    <div>
        <div class="page-header clear-filter">
            <parallax
                class="page-header-image"
                style="background-image: url(img/hotplace.jpg)"
            >
            </parallax>
        </div>

        <div class="section">
            <div class="row">
                <div class="col-md-4 mb-4 ml-auto mr-auto">
                    <fg-input
                        addon-left-icon="now-ui-icons ui-1_zoom-bold"
                        placeholder="search..."
                        v-model="keyword"
                        @input="performSearch"
                    >
                    </fg-input>
                </div>

                <div class="col-md-10 ml-auto mr-auto">
                    <div class="col-md-4 mb-5">
                        <el-tooltip
                            class="item"
                            content="모든 게시물 보기"
                            placement="top"
                        >
                            <n-button
                                class="ml-2"
                                :type="
                                    activeButton == 'all'
                                        ? 'primary'
                                        : 'default'
                                "
                                round
                                icon
                                @click="showAll"
                            >
                                <i class="now-ui-icons business_globe"></i>
                            </n-button>
                        </el-tooltip>
                        <el-tooltip
                            class="item"
                            content="follow한 유저의 게시물 보기"
                            placement="top"
                        >
                            <n-button
                                class="ml-2"
                                :type="
                                    activeButton == 'follow'
                                        ? 'primary'
                                        : 'default'
                                "
                                round
                                icon
                                @click="showFollow"
                            >
                                <i class="now-ui-icons users_single-02"></i>
                            </n-button>
                        </el-tooltip>
                        <el-tooltip
                            class="item"
                            content="좋아요한 게시물 보기"
                            placement="top"
                        >
                            <n-button
                                class="ml-2"
                                :type="
                                    activeButton == 'like'
                                        ? 'primary'
                                        : 'default'
                                "
                                round
                                icon
                                @click="showLike"
                            >
                                <i class="now-ui-icons ui-2_favourite-28"></i>
                            </n-button>
                        </el-tooltip>
                    </div>
                    <div class="row collections">
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
                                    @click="goToDetailPage(hotplace.hotplaceid)"
                                >
                                    <div class="overlay-content">
                                        <span class="likes">
                                            <i
                                                slot="label"
                                                class="now-ui-icons ui-2_favourite-28"
                                                >{{ hotplace.likecnt }}</i
                                            >
                                        </span>
                                        <span class="comments">
                                            <i
                                                slot="label"
                                                class="now-ui-icons ui-2_chat-round"
                                            >
                                                {{ hotplace.commentcnt }}
                                            </i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/util/http-common.js";
import { Button, FormGroupInput } from "@/components";
import { Tooltip } from "element-ui";

export default {
    name: "profile",
    bodyClass: "profile-page",
    components: {
        [Button.name]: Button,
        [Tooltip.name]: Tooltip,
        [FormGroupInput.name]: FormGroupInput,
    },
    data() {
        return {
            keyword: "",
            activeButton: "all",
            hotplaces: [],
        };
    },
    methods: {
        goToDetailPage(hotplaceid) {
            this.$router.push({
                name: "HotplaceDetail",
                params: { hotplaceid: hotplaceid },
            });
        },

        performSearch() {
            console.log(this.keyword);
            let URL = `/hotplaces/search/${this.keyword}`;
            if (this.activeButton == "follow")
                URL = `/hotplaces/search/follow/${this.keyword}`;
            else if (this.activeButton == "like")
                URL = `/hotplaces/search/like/${this.keyword}`;

            http.get(URL)
                .then(({ data }) => {
                    console.log("데이터");
                    console.log(data);
                    this.hotplaces = data;
                    this.hotplaces.forEach((hotplace) => {
                        hotplace.img = hotplace.urls
                            .split(",")
                            .map(
                                (url) =>
                                    `http://localhost:8888/static/resources/upload/${url}`
                            );
                    });
                })
                .catch();
        },
        showAll() {
            this.activeButton = "all";
            http.get(`/hotplaces`) // db 다녀오기
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
                })
                .catch();
        },
        showFollow() {
            this.activeButton = "follow";
            let userid = JSON.parse(sessionStorage.getItem("vuex")).memberStore
                .userInfo.userid;
            http.get(`/hotplaces/follow/${userid}`) // db 다녀오기
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
                })
                .catch();
        },
        showLike() {
            this.activeButton = "like";
            let userid = JSON.parse(sessionStorage.getItem("vuex")).memberStore
                .userInfo.userid;
            http.get(`/hotplaces/like/${userid}`) // db 다녀오기
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
                })
                .catch();
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
