use enjoytrip;

# selectAll
SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h JOIN images i ON h.hotplaceid = i.hotplaceid
GROUP BY h.hotplaceid;

# selectUser
SELECT h.hotplaceid, title, likecnt, commentcnt, content, writer, nickname, placename, placeaddress, lat, lng,
	GROUP_CONCAT(url SEPARATOR ',') AS urls
FROM hotplaces h JOIN images i ON h.hotplaceid = i.hotplaceid
WHERE writer = "ssafy2"
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

