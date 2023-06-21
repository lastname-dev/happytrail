<template>
  <div>
    <div class="page-header clear-filter">
      <parallax class="page-header-image" style="background-image: url('img/header.jpg')"> </parallax>
      <div class="container">
        <div class="content-center brand">
          <img class="n-logo" src="img/mini-logo.png" alt="" />
          <h1><span class="animated-text" te>HAPPY TRAIL</span></h1>
        </div>
      </div>
    </div>
    <!-- <main-section></main-section> -->
  </div>
</template>
<script>
import { Parallax } from "@/components";
// import MainSection from "./components/MainSection.vue";

export default {
  name: "index",
  bodyClass: "index-page",
  components: {
    Parallax,
    // MainSection,
  },
};
</script>
<style>
.animated-text {
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  animation: typing 5s steps(50) forwards;
}

@keyframes typing {
  from {
    width: 0;
  }
  to {
    width: 100%;
  }
}
</style>
