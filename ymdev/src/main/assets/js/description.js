var meta = document.getElementsByTagName('meta');
var desc = '';
for(i in meta){
    if(typeof meta[i].name!="undefined" && meta[i].name.toLowerCase()=="description"){
        desc = meta[i].content;
        break;
    }
};

var json ={url:location.href,description:desc};
window.test_js_call.getDescription(JSON.stringify(json));
console.log(JSON.stringify(json));
var call = 'window.test_js_call.getDescription(json)';