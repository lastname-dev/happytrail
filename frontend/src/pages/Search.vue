<template>
    <div>
        <div class="page-header clear-filter">
            <parallax
                class="page-header-image"
                style="background-image: url('img/bg12.jpg')"
            >
            </parallax>
        </div>
        <div class="section m-5">
            <div class="row">
                <div class="col">
                    <form
                        class="d-flex my-3"
                        @submit.prevent="search"
                        role="search"
                        style="height: 80px"
                    >
                        <select
                            v-model="selectedArea"
                            class="form-select me-2 my-2"
                        >
                            <option value="0" selected>
                                검색 할 지역 선택
                            </option>
                            <option
                                v-for="area in areas"
                                :key="area.code"
                                :value="area.code"
                            >
                                {{ area.name }}
                            </option>
                        </select>
                        <select
                            v-model="selectedContentType"
                            class="form-select me-2 my-2"
                        >
                            <option value="0" selected>관광지 유형</option>
                            <option value="12">관광지</option>
                            <option value="14">문화시설</option>
                            <option value="15">축제공연행사</option>
                            <option value="25">여행코스</option>
                            <option value="28">레포츠</option>
                            <option value="32">숙박</option>
                            <option value="38">쇼핑</option>
                            <option value="39">음식점</option>
                        </select>
                        <input
                            v-model="keyword"
                            style="width: 100%"
                            class="me-2 my-2"
                            type="search"
                            placeholder="검색어"
                            aria-label="검색어"
                            @keyup.enter="search"
                        />
                    </form>

                    <div
                        id="map"
                        class="my-4"
                        style="width: 100%; height: 500px"
                    ></div>

                    <div class="row my-5">
                        <table class="table table-hover mb-4">
                            <thead>
                                <tr>
                                    <th>사진</th>
                                    <th>이름</th>
                                    <th>주소</th>
                                </tr>
                            </thead>
                            <tbody v-show="showTable">
                                <tr
                                    class="hover-link"
                                    v-for="area in tripList"
                                    :key="area.title"
                                    @click="
                                        moveCenter(
                                            area.latitude,
                                            area.longitude
                                        )
                                    "
                                >
                                    <td>
                                        <img
                                            :src="area.first_image"
                                            width="100px"
                                        />
                                    </td>
                                    <td>{{ area.title }}</td>
                                    <td>{{ area.addr1 }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script type="text/javascript" src=""></script>
<script>
import http from "@/util/http-common.js";
import { Button } from "@/components";

export default {
    name: "search",
    bodyClass: "profile-page",
    components: {
        [Button.name]: Button,
    },
    data() {
        return {
            serviceKey: "",
            areas: [],
            selectedArea: 0,
            selectedContentType: 0,
            keyword: "",
            lat: null,
            lng: null,
            showTable: false,
            tripList: [],
        };
    },
    async mounted() {
        await this.getAreas();
        navigator.geolocation.getCurrentPosition(this.onGeoOk, this.onGeoError);
        this.displayMarker(this.tripList);
    },
    methods: {
        onGeoOk(position) {
            this.lat = position.coords.latitude;
            this.lng = position.coords.longitude;
        },
        onGeoError() {
            alert("Can't find you. No weather for you.");
        },
        getAreas() {
            const areaUrl = `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=${this.serviceKey}&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json`;
            fetch(areaUrl)
                .then((response) => response.json())
                .then((data) => {
                    this.areas = data.response.body.items.item;
                });
        },
        search() {
            let searchUrl = "/search?";
            if (this.selectedArea == 0) {
                alert("지역을 선택하세요.");
                return;
            }
            searchUrl += `sido=${this.selectedArea}`;
            if (this.selectedContentType == 0) {
                alert("관광지 유형을 선택하세요.");
                return;
            }
            searchUrl += `&contentType=${this.selectedContentType}`;
            if (!this.keyword) {
                alert("검색어를 입력하세요.");
                return;
            } else searchUrl += `&title=${this.keyword}`;
            searchUrl += `&lat=${this.lat}&lng=${this.lng}`;
            http.get(searchUrl)
                .then(({ data }) => {
                    this.showTable = true;
                    this.tripList = data;
                    console.log(data);
                    this.displayMarker(data);
                })
                .catch(() => {
                    alert("검색 결과가 없습니다.");
                });
        },
        displayMarker(data) {
            var mapContainer = document.getElementById("map");
            var mapOption = {
                center: new kakao.maps.LatLng(37.500613, 127.036431),
                level: 5,
            };
            var map = new kakao.maps.Map(mapContainer, mapOption);
            var positions = [];

            var imageSrc =
                "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
            var imageSize = new kakao.maps.Size(24, 35);
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

            data.forEach((area) => {
                console.log("지역");
                console.log(area);
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: new kakao.maps.LatLng(
                        area.latitude,
                        area.longitude
                    ),
                    title: area.title,
                    image: markerImage,
                });
                var markerInfo = {
                    title: area.title,
                    latlng: new kakao.maps.LatLng(
                        area.latitude,
                        area.longitude
                    ),
                };
                positions.push(markerInfo);
            });

            map.setCenter(positions[0].latlng);
        },
        moveCenter(lat, lng) {
            var imageSrc =
                "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
            var imageSize = new kakao.maps.Size(24, 35);
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

            var map = new kakao.maps.Map(document.getElementById("map"), {
                center: new kakao.maps.LatLng(lat, lng),
                level: 5,
            });
            var marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(lat, lng),
                image: markerImage,
            });
            var markerInfo = {
                latlng: new kakao.maps.LatLng(lat, lng),
            };
            positions.push(markerInfo);
        },
        ////////// 기존꺼

        toggleSearchForm() {
            this.showMapWrap = !this.showMapWrap; // 검색 폼의 표시 여부를 토글
        },

        searchPlaces(event) {
            event.preventDefault();

            const keyword = document.getElementById("keyword").value;
            if (!keyword.replace(/^\s+|\s+$/g, "")) {
                alert("키워드를 입력해주세요!");
                return false;
            }
            const ps = new kakao.maps.services.Places();

            // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
            ps.keywordSearch(keyword, (data, status, pgn) => {
                if (status === kakao.maps.services.Status.OK) {
                    // 정상적으로 검색이 완료됐으면
                    // 검색 목록과 마커를 표출합니다
                    this.displayPlaces(data);
                    // 페이지 번호를 표출합니다
                    this.displayPagination(pgn);
                } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
                    alert("검색 결과가 존재하지 않습니다.");
                    return;
                } else if (status === kakao.maps.services.Status.ERROR) {
                    alert("검색 결과 중 오류가 발생했습니다.");
                    return;
                }
            });
        },
        displayInfowindow(marker, title) {
            var content =
                '<div style="padding:5px;z-index:1;">' + title + "</div>";

            this.infowindow.setContent(content);
            this.infowindow.open(map, marker);
        },
        removeAllChildNods(el) {
            while (el.hasChildNodes()) {
                el.removeChild(el.lastChild);
            }
        },
        displayPagination(pagination) {
            var paginationEl = document.getElementById("pagination"),
                fragment = document.createDocumentFragment(),
                i;

            // 기존에 추가된 페이지번호를 삭제합니다
            while (paginationEl.hasChildNodes()) {
                paginationEl.removeChild(paginationEl.lastChild);
            }

            for (i = 1; i <= pagination.last; i++) {
                var el = document.createElement("a");
                el.href = "#";
                el.innerHTML = i;

                if (i === pagination.current) {
                    el.className = "on";
                } else {
                    el.onclick = (function (i) {
                        return function () {
                            pagination.gotoPage(i);
                        };
                    })(i);
                }

                fragment.appendChild(el);
            }
            paginationEl.appendChild(fragment);
        },
        removeMarker() {
            for (var i = 0; i < this.markers.length; i++) {
                this.markers[i].setMap(null);
            }
            this.markers = [];
        },
        addMarker(position, idx, title) {
            var imageSrc =
                    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
                imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
                imgOptions = {
                    spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
                    spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                    offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
                },
                markerImage = new kakao.maps.MarkerImage(
                    imageSrc,
                    imageSize,
                    imgOptions
                ),
                marker = new kakao.maps.Marker({
                    position: position, // 마커의 위치
                    image: markerImage,
                });

            marker.setMap(this.map); // 지도 위에 마커를 표출합니다
            this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

            return marker;
        },
        getListItem(index, places) {
            var el = document.createElement("li"),
                itemStr =
                    '<span class="markerbg marker_' +
                    (index + 1) +
                    '"></span>' +
                    '<div class="info">' +
                    "   <h5>" +
                    places.place_name +
                    "</h5>";

            if (places.road_address_name) {
                itemStr +=
                    "    <span>" +
                    places.road_address_name +
                    "</span>" +
                    '   <span class="jibun gray">' +
                    places.address_name +
                    "</span>";
            } else {
                itemStr += "    <span>" + places.address_name + "</span>";
            }
            itemStr +=
                '  <span class="tel">' + places.phone + "</span>" + "</div>";

            el.innerHTML = itemStr;
            el.className = "item";

            return el;
        },
        displayPlaces(places) {
            var listEl = document.getElementById("placesList"),
                menuEl = document.getElementById("menu_wrap"),
                fragment = document.createDocumentFragment(),
                bounds = new kakao.maps.LatLngBounds(),
                listStr = "";

            // 검색 결과 목록에 추가된 항목들을 제거합니다
            this.removeAllChildNods(listEl);

            // 지도에 표시되고 있는 마커를 제거합니다
            this.removeMarker();

            for (var i = 0; i < places.length; i++) {
                // 마커를 생성하고 지도에 표시합니다
                var placePosition = new kakao.maps.LatLng(
                        places[i].y,
                        places[i].x
                    ),
                    marker = this.addMarker(placePosition, i),
                    itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                // LatLngBounds 객체에 좌표를 추가합니다
                bounds.extend(placePosition);

                // 마커와 검색결과 항목에 mouseover 했을때
                // 해당 장소에 인포윈도우에 장소명을 표시합니다
                // mouseout 했을 때는 인포윈도우를 닫습니다
                (function (
                    marker,
                    title,
                    place,
                    displayInfowindow,
                    infowindow
                ) {
                    kakao.maps.event.addListener(
                        marker,
                        "mouseover",
                        function () {
                            // displayInfowindow(marker, title);
                        }
                    );
                    kakao.maps.event.addListener(
                        marker,
                        "mouseout",
                        function () {
                            // infowindow.close();
                        }
                    );
                    itemEl.onmousedown = function () {
                        // displayInfowindow(marker, title);
                        console.log(place);
                    };
                    itemEl.onmouseout = function () {
                        // infowindow.close();
                    };
                })(
                    marker,
                    places[i].place_name,
                    places[i],
                    this.displayInfowindow.bind(this),
                    this.infowindow
                );

                fragment.appendChild(itemEl);
            }

            // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
            listEl.appendChild(fragment);
            menuEl.scrollTop = 0;

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
            this.map.setBounds(bounds);
        },
    },
};
</script>

<style>
select,
input {
    border-color: rgb(168, 168, 168);
    border-radius: 10px;
}

select option {
    font-size: 16px;
}

#map {
    width: 100%;
    height: 500px;
}
.option input {
    background: #eee;
    padding: 16px;
    margin: 8px 0;
    width: 85%;
    border: 0;
    outline: none;
    border-radius: 20px;
    box-shadow: inset 7px 2px 10px #babebc, inset -5px -5px 12px #fff;
}
.places-list-container {
    position: relative;
    left: -20px; /* 수정: 왼쪽으로 이동 */
}
.map_wrap,
.map_wrap * {
    margin: 0;
    padding: 0;
    font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
    font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
    color: #000;
    text-decoration: none;
}
.map_wrap {
    position: relative;
    width: 50%; /* 수정: map 너비 조정 */
    height: 100%;
    overflow: hidden;
}
.map-wrap-container {
    position: relative;
    width: 100%;
    height: 600px;
    display: flex;
    justify-content: center;
    margin-top: 100px;
}
#map {
    width: 100%;
    height: 100%;
}
#menu_wrap {
    position: absolute;
    top: 0;
    left: 20px; /* 수정: 왼쪽 위치 조정 */
    bottom: 0;
    width: 300px;
    margin: 10px 0 30px 10px;
    padding: 5px;
    overflow-y: auto;
    background: rgba(255, 255, 255, 0.7);
    z-index: 1;
    font-size: 12px;
    border-radius: 10px;
}
.bg_white {
    background: #fff;
}
#menu_wrap hr {
    display: block;
    height: 1px;
    border: 0;
    border-top: 2px solid #5f5f5f;
    margin: 3px 0;
}
#menu_wrap .option {
    text-align: center;
}
#menu_wrap .option p {
    margin: 10px 0;
}
#menu_wrap .option button {
    margin-left: 5px;
}
#placesList li {
    list-style: none;
}
#placesList .item {
    position: relative;
    border-bottom: 1px solid #888;
    overflow: hidden;
    cursor: pointer;
    min-height: 65px;
}
#placesList .item span {
    display: block;
    margin-top: 4px;
}
#placesList .item h5,
#placesList .item .info {
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
}
#placesList .item .info {
    padding: 10px 0 10px 55px;
}
#placesList .info .gray {
    color: #8a8a8a;
}
#placesList .info .jibun {
    padding-left: 26px;
    background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
        no-repeat;
}
#placesList .info .tel {
    color: #009900;
}
#placesList .item .markerbg {
    float: left;
    position: absolute;
    width: 36px;
    height: 37px;
    margin: 10px 0 0 10px;
    background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
        no-repeat;
}
#placesList .item .marker_1 {
    background-position: 0 -10px;
}
#placesList .item .marker_2 {
    background-position: 0 -56px;
}
#placesList .item .marker_3 {
    background-position: 0 -102px;
}
#placesList .item .marker_4 {
    background-position: 0 -148px;
}
#placesList .item .marker_5 {
    background-position: 0 -194px;
}
#placesList .item .marker_6 {
    background-position: 0 -240px;
}
#placesList .item .marker_7 {
    background-position: 0 -286px;
}
#placesList .item .marker_8 {
    background-position: 0 -332px;
}
#placesList .item .marker_9 {
    background-position: 0 -378px;
}
#placesList .item .marker_10 {
    background-position: 0 -423px;
}
#placesList .item .marker_11 {
    background-position: 0 -470px;
}
#placesList .item .marker_12 {
    background-position: 0 -516px;
}
#placesList .item .marker_13 {
    background-position: 0 -562px;
}
#placesList .item .marker_14 {
    background-position: 0 -608px;
}
#placesList .item .marker_15 {
    background-position: 0 -654px;
}
#pagination {
    margin: 10px auto;
    text-align: center;
}
#pagination a {
    display: inline-block;
    margin-right: 10px;
}
#pagination .on {
    font-weight: bold;
    cursor: default;
    color: #777;
}

.icon-bar {
    position: absolute;
    top: 50%;
    right: 20px;
    transform: translateY(-50%);
    display: flex;
    flex-direction: column;
    align-items: center;
}
.show-form #menu_wrap {
    display: block !important;
}

.icon-bar a {
    display: block;
    text-align: center;
    padding: 16px;
    transition: all 0.3s ease;
    color: white;
    font-size: 36px;
    margin-bottom: 10px;
    background-color: #ffbb84;
}

#menu_wrap::-webkit-scrollbar {
    display: none;
}

.table-hover tbody tr:hover {
    background-color: #da8a5f2d !important;
}

.hover-link:hover {
    text-decoration: none;
    font-size: 1.1em;
}
</style>
