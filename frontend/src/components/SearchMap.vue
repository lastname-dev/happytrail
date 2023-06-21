<template>
    <div>
        <div class="conatainer">
            <div class="map-wrap-container">
                <div id="menu_wrap" class="bg_white">
                    <div class="option m-3">
                        <div>
                            <form v-on:submit="searchPlaces($event)">
                                <input
                                    type="text"
                                    value=""
                                    id="keyword"
                                    size="5"
                                />
                                <n-button
                                    style="
                                        margin-left: 10px;
                                        background-color: #ff8c00;
                                    "
                                    size="sm"
                                    round
                                    icon
                                    v-on:click="searchPlaces"
                                >
                                    <i class="now-ui-icons ui-1_zoom-bold"></i>
                                </n-button>
                            </form>
                        </div>
                    </div>
                    <hr style="background: #ff8c00; height: 1px; border: 0" />
                    <div class="places-list-container">
                        <ul id="placesList"></ul>
                    </div>
                    <div id="pagination"></div>
                </div>
                <div class="map_wrap" v-show="false">
                    <div id="map"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { Button } from "@/components";
import { mapState, mapActions, mapGetters } from "vuex";

const placeStore = "placeStore";
export default {
    name: "SearchMap",
    components: {
        [Button.name]: Button,
    },
    computed: {
        ...mapState(placeStore, ["place"]),
        ...mapGetters("placeStore", ["checkPlace"]),
    },
    data() {
        return {
            showMapWrap: false,
            map: null,
            markers: [],
            infowindow: null,
            latitude: 0,
            longitude: 0,
            search: {
                keyword: null,
                pgn: null,
                results: [],
            },
        };
    },
    created() {
        if (!("geolocation" in navigator)) {
            return;
        }

        // get position
        navigator.geolocation.getCurrentPosition(
            (pos) => {
                this.latitude = pos.coords.latitude;
                this.longitude = pos.coords.longitude;

                if (window.kakao && window.kakao.maps) {
                    this.initMap();
                } else {
                    const script = document.createElement("script");
                    /* global kakao */
                    script.onload = () => kakao.maps.load(this.initMap);
                    script.src = "";
                    document.head.appendChild(script);
                }
            },
            (err) => {
                alert(err.message);
            }
        );
    },
    methods: {
        ...mapActions("placeStore", ["selectPlace"]),
        toggleSearchForm() {
            this.showMapWrap = !this.showMapWrap; // 검색 폼의 표시 여부를 토글
        },
        initMap() {
            const container = document.getElementById("map");
            const options = {
                center: new kakao.maps.LatLng(this.latitude, this.longitude),
                level: 3,
            };
            this.map = new kakao.maps.Map(container, options);
            this.displayMarker([[this.latitude, this.longitude]]);
            this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
        },
        displayMarker(markerPositions) {
            if (this.markers.length > 0) {
                this.markers.forEach((marker) => marker.setMap(null));
            }

            const positions = markerPositions.map(
                (position) => new kakao.maps.LatLng(...position)
            );

            if (positions.length > 0) {
                this.markers = positions.map(
                    (position) =>
                        new kakao.maps.Marker({
                            map: this.map,
                            position,
                        })
                );

                const bounds = positions.reduce(
                    (bounds, latlng) => bounds.extend(latlng),
                    new kakao.maps.LatLngBounds()
                );

                this.map.setBounds(bounds);
            }
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
            var self = this;
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
                    itemEl.onmousedown = () => {
                        self.$store.dispatch("placeStore/selectPlace", place);
                        // displayInfowindow(marker, title);
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
    width: 100%;
    justify-content: center;
}
#map {
    width: 100%;
    height: 100%;
}
#menu_wrap {
    position: absolute;
    top: -150px;
    left: 20px; /* 수정: 왼쪽 위치 조정 */
    bottom: 0;
    height: 100%;
    width: 400px;
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
    margin-right: 30px;
    display: block;
    margin-top: 4px;
}
#placesList .item h5,
#placesList .item .info {
    margin-right: 30px;
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
    padding-left: 0px;
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
</style>
