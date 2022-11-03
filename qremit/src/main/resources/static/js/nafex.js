'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');


function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/qremit/upload");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>Download Url : <a href='" + response.fileDownloadUri + "' >" + response.fileDownloadUri + "</a></p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = "<p>"+(response && response.message)+"<p>" || "Some Error Occurred";
            singleFileUploadSuccess.style.display = "block";

        }
    }

    xhr.send(formData);
}
function clearDataTable(){
    var xhr1 = new XMLHttpRequest();
    xhr1.open("GET", "/qremit/processData");
    xhr1.onload = function() {
        console.log(xhr1.responseText);
        if(xhr1.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Generated!</p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = "<p>Error Occurred. Reload The page and try again!<p>" || "Some Error Occurred";
            singleFileUploadSuccess.style.display = "block";

        }
    }
    xhr1.send();
    var frm = document.getElementById('singleFileUploadInput');
    frm.value = '';
    frm.reset();
}

singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);

