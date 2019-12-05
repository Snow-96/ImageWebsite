function login() {
    $.ajax({
        type: "post",
        url: "/loginPost",
        data: JSON.stringify({
            "email": $("#loginEmail").val(),
            "password": $("#loginPassword").val()
        }),
        dataType: "json",
        contentType: "application/json",
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

function logout() {
    $.get("/logout",
        function (data) {
            if (data.result === "success") {
                window.location.href = "login";
            }
        });
}