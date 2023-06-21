import http from "@/util/http-common.js";

async function login(user, success, fail) {
    await http.post(`/users/login`, user).then(success).catch(fail);
}

async function findById(userid, success, fail) {
    http.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token");
    await http.get(`/users/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
    http.defaults.headers["refresh-token"] =
        sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
    await http.post(`/users/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
    await http.get(`/users/logout/${userid}`).then(success).catch(fail);
}

export { login, findById, tokenRegeneration, logout };
