<template>
  <footer class="footer" :class="{ [`footer-${type}`]: type }" :data-background-color="backgroundColor">
    <div class="container">
      Welcome to HAPPY TRAIL
      <div class="copyright">청춘은 여행이다 찢어진 주머니에 두 손을 내리꽂은 채 그저 길을 떠나도 좋은 것이다</div>
    </div>
  </footer>
</template>
<script>
export default {
  props: {
    backgroundColor: String,
    type: String,
  },
  data() {
    return {
      year: new Date().getFullYear(),
    };
  },
};
</script>
<style></style>
