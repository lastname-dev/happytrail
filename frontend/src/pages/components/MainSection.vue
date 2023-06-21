<template>
  <div class="section section-nucleo-icons">
    <div class="container">
      <div class="row">
        <div class="col-md-4" data-background-color="">
          <router-link to="/search">
            <img src="img/searchicon.png" />
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Button } from "@/components";

export default {
  components: {
    [Button.name]: Button,
  },
  data() {
    return {
      tabs: [(img = ["img/searchicon.png", "img/festivalicon.png", ""])],
    };
  },
};
</script>
<style></style>
