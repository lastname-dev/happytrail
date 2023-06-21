use enjoytrip;

select * from users;
select * from hotplaces;
select * from hcomments;
select * from images;
select * from follows;
select * from likepost;
select * from festival;
select * from festival where festivalid = 22;

# selectAll
SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h JOIN images i ON h.hotplaceid = i.hotplaceid
GROUP BY h.hotplaceid;

# selectUser
SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h JOIN images i ON h.hotplaceid = i.hotplaceid
WHERE writer = "ssafy"
GROUP BY h.hotplaceid;

#selectFollow
SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h JOIN images i ON h.hotplaceid = i.hotplaceid
WHERE h.writer IN (
  SELECT followeeid
  FROM follows
  WHERE followerid = "ssafy2"
)
GROUP BY h.hotplaceid;

#selectLike
SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h 
JOIN images i ON h.hotplaceid = i.hotplaceid
WHERE h.hotplaceid IN (
  SELECT hotplaceid
  FROM likepost
  WHERE userid = "ssafy"
)
GROUP BY h.hotplaceid;

SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer,  writtendate, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h 
JOIN images i ON h.hotplaceid = i.hotplaceid
WHERE h.hotplaceid IN (
  SELECT hotplaceid
  FROM likepost
  WHERE userid = 'ssafy') AND (
	title LIKE CONCAT('%', '서', '%')
	OR nickname LIKE CONCAT('%', '서', '%')
	OR placename LIKE CONCAT('%', '서', '%')
	OR placeaddress LIKE CONCAT('%', '서', '%')
)
GROUP BY h.hotplaceid;

SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer,  writtendate, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h 
JOIN images i ON h.hotplaceid = i.hotplaceid
WHERE h.hotplaceid IN (
  SELECT hotplaceid
  FROM likepost
  WHERE userid = "ssafy"
) AND (
	title LIKE CONCAT('%', '서', '%')
	OR nickname LIKE CONCAT('%', '서', '%')
	OR placename LIKE CONCAT('%', '서', '%')
	OR placeaddress LIKE CONCAT('%', '서', '%')
)
GROUP BY h.hotplaceid;