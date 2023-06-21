use enjoytrip;

select * from users;
select * from hotplaces;
select * from hcomments;
select * from images;
select * from follows;
select * from likepost;
select * from festival;

INSERT INTO users (userid, userpw, nickname, phone, email, birthday)
VALUES
("ssafy", "1234", "BIBI", "010-1234-1234", "danbi@naver.com", DATE('2000-08-22')),
("ssafy2", "1234", "SSAFY2", "010-1234-1234", "danbi@naver.com", DATE('2000-08-22')),
("ssafy3", "1234", "SSAFY3", "010-1234-1234", "danbi@naver.com", DATE('2000-08-22'));

insert into hotplaces (title, content, writer, nickname, placename, placeaddress, lat, lng)
values
("제주 여행", "너무 좋았네요!!", "ssafy", "BIBI", "제주", "제주 주소", 33.24373598, 126.588279),
("경주 여행", "최고의 여행!!", "ssafy", "BIBI", "경주", "경주 주소", 33.24373598, 126.588279),
("서울 여행", "행복한 여행!!", "ssafy2", "SSAFY2", "서울", "서울 주소", 33.24373598, 126.588279),
("부산 여행", "정말 예뻤어요", "ssafy2", "SSAFY2", "부산", "부산 주소", 33.24373598, 126.588279),
("하남 여행", "또 가고 싶네요!", "ssafy3", "SSAFY3", "하남", "하남 주소", 33.24373598, 126.588279),
("대구 여행", "우와!! 예뻐요 !!", "ssafy3", "SSAFY3", "대구", "대구 주소22", 33.24373598, 126.588279);

insert into images (hotplaceid, url)
values
(1, "img/jeju.jpg"), (1, "img/bg1.jpg"), 
(2, "img/bg4.jpg"), (2, "img/bg3.jpg"),
(3, "img/bg6.jpg"), (3, "img/bg4.jpg"), 
(4, "img/bg7.jpg"), (4, "img/bg6.jpg"),
(5, "img/bg8.jpg"), (5, "img/bg7.jpg"), 
(6, "img/bg1.jpg"), (6, "img/bg8.jpg");

insert into follows
values 
("ssafy", "ssafy2"),
("ssafy", "ssafy3"),
("ssafy3", "ssafy"),
("ssafy3", "ssafy2");

-- insert into likepost
-- values("ssafy", 3), ("ssafy", 4), ("ssafy", 5), ("ssafy", 6);


insert into hcomments(hotplaceid, writer, content, writtendate)
values
(1, "ssafy2", "저도 다녀올게요!!", CURRENT_TIMESTAMP),
(1, "ssafy3", "저도 다녀올게요!!", CURRENT_TIMESTAMP),
(3, "ssafy2", "저도 다녀올게요!!", CURRENT_TIMESTAMP);

INSERT INTO boards (title, content, writer, nickname)
VALUES
("한국 여행 추천해주세요!", "안녕하세요! 저는 한국 여행을 계획 중인데요. 여러분들이 추천하는 한국의 여행지를 알고 싶습니다. 주변에 추천해주실만한 장소가 있다면 알려주세요. 감사합니다!", "ssafy", "BIBI"),
("서울에서 먹을만한 음식 추천해주세요!", "안녕하세요! 다음 주에 서울로 여행을 갈 예정인데요. 서울에서 먹을 만한 유명한 음식점이나 로컬 맛집 추천해주세요. 혹시 음식 관련해서 특별한 팁이나 꿀잼 경험이 있다면 함께 알려주세요!", "ssafy2", "SSAFY2"),
("가을에 가볼만한 여행지 추천해주세요!", "안녕하세요! 가을이 다가오고 있는데요. 가을 풍경이 아름다운 여행지를 찾고 있습니다. 한국 내에서 가을에 가볼 만한 추천 여행지가 있다면 공유해주세요. 가을 특유의 아름다운 경치를 느낄 수 있는 곳이면 어디든 좋습니다!", "ssafy3", "SSAFY3"),
("가족과 함께 할 수 있는 가까운 여행지 추천해주세요!", "안녕하세요! 저는 가족과 함께 가까운 여행지를 찾고 있습니다. 가족 모두가 즐길 수 있는 가까운 여행지가 있다면 알려주세요. 주말에 가볼 수 있는 가까운 거리의 여행지나 가족 놀이동산 등 다양한 제안을 기다리고 있습니다!", "ssafy", "BIBI"),
("Title 1", "Content 1", "ssafy", "BIBI"),
("Title 2", "Content 2", "ssafy2", "SSAFY2"),
("Title 3", "Content 3", "ssafy3", "SSAFY3"),
("Title 4", "Content 4", "ssafy", "BIBI"),
("Title 5", "Content 5", "ssafy2", "SSAFY2"),
("Title 6", "Content 6", "ssafy3", "SSAFY3"),
("Title 7", "Content 7", "ssafy", "BIBI"),
("Title 8", "Content 8", "ssafy2", "SSAFY2"),
("Title 9", "Content 9", "ssafy3", "SSAFY3"),
("Title 10", "Content 10", "ssafy", "BIBI");


