function uploadImage() {
    let picture = $('#uploadFile')[0].files[0];
    let fileData = new FormData();
    fileData.append('file', picture);
    $.ajax({
        url: "/uploadImage",
        type: "post",
        data: fileData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.result === "success") {
                alert(data.message);
                setTimeout(function () {
                    window.location.href = "gallery";
                }, 1000);
            } else {
                alert(data.message);
            }
        }
    });
}