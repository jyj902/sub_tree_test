var express = require('express');
var bodyParser = require('body-parser'); //requst 용 body를 버퍼에 넣고 파싱하는 것을 자동으로 처리 해 줌.
var logger = require('morgan'); //로그 출력
var colors = require('colors/safe');

const port = 3000;

var app = express();

app.use(logger('dev')); // Log 출력
// app.use(bodyParser.json());
console.log(colors.blue("directory = ", __dirname));
app.use(express.static(__dirname));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:false}));
app.use(require('./Router'));

app.get('/', function(req, res) {
	res.end('Welcome to static server');
});

app.use(handleError);

function handleError(err, req, res, next) {
   console.log('Error : ', err);
   res.status(err.code).send({msg:err.message});
}

app.listen(port, () => {
    var ip = require("ip");
    var hostname = ip.address();
    console.log("TIME = ",new Date().toLocaleString());
   console.log(colors.yellow(`Server running.... at http://${hostname}:${port}/`));
   console.log(colors.green(`In order to show web browser, connect to http://${hostname}:${port}/`));
   console.log(colors.yellow(` - get course : GET http://${hostname}:${port}/api/v1/course/bodypart/all/`));    
});
