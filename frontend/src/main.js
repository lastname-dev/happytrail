import Vue from "vue";
import App from "./App.vue";
// You can change this import to `import router from './starterRouter'` to quickly start development from a blank layout.
import router from "./router";
import NowUiKit from "./plugins/now-ui-kit";
import store from "./store";
import * as VueGoogleMaps from "vue2-google-maps";

Vue.config.productionTip = false;

Vue.use(NowUiKit);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
