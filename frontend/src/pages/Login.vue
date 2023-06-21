<template>
    <div class="page-header clear-filter">
        <div class="container">
            <div class="row justify-content-center">
                <card
                    class="card-signup"
                    header-classes="text-center"
                    color="orange"
                >
                    <template slot="header">
                        <div slot="header" class="logo-container">
                            <img v-lazy="'img/mini-logo.png'" alt="" />
                            <h3 class="card-title title-up">Sign Up</h3>
                        </div>
                    </template>

                    <template>
                        <fg-input
                            class="no-border"
                            placeholder="ID"
                            type="text"
                            v-model="id"
                            addon-left-icon="now-ui-icons users_circle-08"
                        />

                        <fg-input
                            class="no-border"
                            placeholder="PASSWORD"
                            type="password"
                            v-model="password"
                            addon-left-icon="now-ui-icons objects_key-25"
                        />
                    </template>

                    <div class="card-footer text-center p-2 m-2">
                        <n-button
                            type="neutral"
                            class="btn-md-custom"
                            round
                            size="md"
                            @click="confirm"
                            >SING IN</n-button
                        >
                        <router-link to="/signup" class="ml-2">
                            <n-button
                                type="neutral"
                                class="btn-md-custom"
                                round
                                size="md"
                                >SIGN UP</n-button
                            >
                        </router-link>
                    </div>
                </card>
            </div>
        </div>
    </div>
</template>
<script>
import { Card, FormGroupInput, Button } from "@/components";
import { DatePicker } from "element-ui";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
    name: "login-page",
    bodyClass: "login-page",
    data() {
        return {
            id: "",
            password: "",
            phone: "",
            email: "",
            birthday: "",
            user: {
                userid: "",
                userpw: "",
            },
        };
    },
    components: {
        Card,
        [Button.name]: Button,
        [DatePicker.name]: DatePicker,
        [FormGroupInput.name]: FormGroupInput,
    },
    computed: {
        ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
        async confirm() {
            this.user.userid = this.id;
            this.user.userpw = this.password;
            await this.userConfirm(this.user);
            let token = sessionStorage.getItem("access-token");
            // console.log("1. confirm() token >> " + token);
            if (this.isLogin) {
                await this.getUserInfo(token);
                alert(this.user.userid + "님 환영합니다.");
                // console.log("4. confirm() userInfo :: ", this.userInfo);
                this.$router.push({ name: "index" });
            } else {
            }
        },
    },
};
</script>

<style scoped>
.logo-container img {
    width: 45px;
    height: auto;
}

.btn-md-custom {
    font-size: 1.1rem;
    padding: 0.5rem 1rem;
}
</style>
