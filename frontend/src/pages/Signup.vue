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

                        <fg-input
                            class="no-border"
                            placeholder="CONFIRM"
                            type="password"
                            v-model="confirm"
                            addon-left-icon="now-ui-icons objects_key-25"
                        />
                        <fg-input
                            class="no-border"
                            placeholder="NICKNAME"
                            type="text"
                            v-model="nickname"
                            addon-left-icon="now-ui-icons users_circle-08"
                        />

                        <fg-input
                            class="no-border"
                            placeholder="PHONE"
                            type="tel"
                            v-model="phone"
                            addon-left-icon="now-ui-icons tech_tablet"
                        />

                        <fg-input
                            class="no-border"
                            placeholder="E-MAIL"
                            type="email"
                            v-model="email"
                            addon-left-icon="now-ui-icons ui-1_email-85"
                        />

                        <fg-input>
                            <el-date-picker
                                class="no-border"
                                placeholder="BIRTHDAY"
                                type="date"
                                v-model="birthday"
                            />
                        </fg-input>
                    </template>

                    <div class="card-footer text-center p-2 m-">
                        <n-button
                            type="neutral"
                            class="btn-md-custom mr-2"
                            round
                            size="md"
                            @click="signUp"
                            >SIGN UP</n-button
                        >
                        <router-link to="/login">
                            <n-button
                                type="neutral"
                                class="btn-md-custom"
                                round
                                size="md"
                                >SING IN</n-button
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
import http from "@/util/http-common.js";

export default {
    name: "login-page",
    bodyClass: "login-page",
    data() {
        return {
            id: "",
            password: "",
            confirm: "",
            nickname: "",
            phone: "",
            email: "",
            birthday: "",
        };
    },
    components: {
        Card,
        [Button.name]: Button,
        [DatePicker.name]: DatePicker,
        [FormGroupInput.name]: FormGroupInput,
    },
    methods: {
        signUp() {
            if (
                this.id == "" ||
                this.password == "" ||
                this.confirm == "" ||
                this.phone == "" ||
                this.email == "" ||
                this.nickname == "" ||
                this.birthday == ""
            ) {
                alert("모든 칸을 입력하세요.");
            } else if (this.confirm != this.password) {
                alert("비밀번호가 일치하지 않습니다.");
            } else {
                http.post(`/users`, {
                    userid: this.id,
                    userpw: this.password,
                    nickname: this.nickname,
                    phone: this.phone,
                    email: this.email,
                    birthday: this.birthday,
                })
                    .then((response) => {
                        console.log(response);
                        alert("회원가입이 완료되었습니다.");
                        this.$router.push({ name: "index" });
                    })
                    .catch();
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
