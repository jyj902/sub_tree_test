var express = require('express');
var router = express.Router();

var fs = require('fs');

// UTC
router.get('/api/v1/course/bodypart/all', getCourse);


function getCourse(req, res) {
   var result = [
      {
         "createdDate":"2021-12-16T14:45:00.588",
         "modifiedDate":"2021-12-16T14:45:00.588",
         "id":1,
         "bodyPartName":"전신운동",
         "description":"test",
         "difficulty":1,
         "contentsCount":0,
         "totalCalorie":0,
         "thumbnailFileName":"course1.png",
         "thumbnailPath":"http://hnh.ai:8080/api/v1/course/bodypart/thumbnail/1",
         "isAi":true,
         "isVisible":true
      },
      {
         "createdDate":"2021-12-16T14:47:06.563",
         "modifiedDate":"2021-12-16T14:47:06.563",
         "id":2,
         "bodyPartName":"상체운동",
         "description":"test",
         "difficulty":2,
         "contentsCount":0,
         "totalCalorie":0,
         "thumbnailFileName":"dc8b3d02-a15a-4afa-a88b-989cf2a50476.png",
         "thumbnailPath":"http://hnh.ai:8080/api/v1/course/bodypart/thumbnail/2",
         "isAi":true,
         "isVisible":true
      }
   ];
   res.send(result);
}

module.exports = router;
