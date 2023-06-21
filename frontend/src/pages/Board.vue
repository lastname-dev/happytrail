<template>
  <div>
    <div class="page-header clear-filter">
      <parallax class="page-header-image" style="background-image: url('img/winter.jpg')"> </parallax>
    </div>
    <div class="section">
      <div class="container">
        <div class="col-sm-6 col-lg-3" style="float: right; display: flex; align-items: center">
          <fg-input
            addon-left-icon="now-ui-icons ui-1_zoom-bold"
            placeholder="search..."
            v-model="keyword"
            @keyup.enter="performSearch"
          >
          </fg-input>
          <router-link :to="{ name: 'BoardWrite', params: { boardid: 0 } }">
            <n-button type="primary" class="mb-4 ml-2" icon>
              <i class="now-ui-icons ui-1_simple-add"></i>
            </n-button>
          </router-link>
        </div>

        <table class="table table-hover mb-4">
          <colgroup>
            <col style="width: 10%" />
            <col style="width: 45%" />
            <col style="width: 15%" />
            <col style="width: 20%" />
            <col style="width: 10%" />
          </colgroup>
          <thead>
            <tr>
              <th scope="col">글번호</th>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
              <th scope="col">조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(article, idx) in pagedArticles" :key="idx">
              <th scope="row">
                {{ idx + 1 + pageSize * (currentPage - 1) }}
              </th>
              <td>
                <router-link
                  :to="{
                    name: 'BoardDetail',
                    params: { boardid: article.boardid },
                  }"
                  class="hover-link"
                  style="color: black"
                  >{{ article.title }}</router-link
                >
              </td>
              <td>{{ article.nickname }}</td>
              <td>{{ article.writtendate }}</td>
              <td>{{ article.view }}</td>
            </tr>
          </tbody>
        </table>
        <n-pagination
          type="primary"
          :page-count="pageCount"
          v-model="currentPage"
          @input="changePage"
          :active-class="'active'"
          :disabled-class="'disabled'"
          style="display: flex; justify-content: center"
        >
        </n-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { Button, FormGroupInput, Pagination } from "@/components";
// import Paginate from "vuejs-paginate";
import http from "@/util/http-common.js";

export default {
  name: "board",
  bodyClass: "profile-page",
  components: {
    // Paginate,
    [Button.name]: Button,
    [Pagination.name]: Pagination,
    [FormGroupInput.name]: FormGroupInput,
  },
  data() {
    return {
      pageSize: 10,
      currentPage: 1,
      keyword: "",
      articles: [],
    };
  },
  methods: {
    changePage(page) {
      this.currentPage = page;
    },
    performSearch() {
      http
        .get(`/boards/search/${this.keyword}`)
        .then(({ data }) => {
          console.log(data);
          this.articles = data;
        })
        .catch();
    },
  },
  watch: {
    pageSize: function () {
      this.currentPage = 1;
    },
    articles: function () {
      this.currentPage = 1;
    },
  },
  computed: {
    pageCount() {
      return Math.ceil(this.articles.length / this.pageSize);
    },
    pagedArticles() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.articles.slice(startIndex, endIndex);
    },
  },
  created() {
    http
      .get(`/boards`)
      .then(({ data }) => {
        this.articles = data;
      })
      .catch();
  },
};
</script>

<style scoped>
.hover-link:hover {
  text-decoration: none;
  font-size: 1.1em;
}
</style>

<style>
.table-hover tbody tr:hover {
  background-color: #ffd0b7a7 !important;
}
</style>
