function register() {
    let registerEmail = $("#registerEmail").val();
    let registerPassword = $("#registerPassword").val();
    let registerPasswordConfirm = $("#registerPasswordConfirm").val();
    let registerLN = $("#registerLN").val();
    let registerFN = $("#registerFN").val();
    let registerBirth = $("#registerBirth").val();

    if (registerEmail === "" || registerPassword === "" || registerPasswordConfirm === "" || registerLN === "" || registerFN === "" || registerBirth === "") {
        alert("Please fill all these fields");
        return;
    }

    if (registerPassword !== registerPasswordConfirm) {
        alert("The confirm password confirmation does not match");
        return;
    }

    let emailRegex = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    if (!emailRegex.test(registerEmail)) {
        alert("Please input correct email");
        return;
    }

    let passwordRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
    if (!passwordRegex.test(registerPassword)) {
        alert("Please choose a stronger password. Try a mix of letters, numbers, and symbols");
        return;
    }

    $.ajax({
        type: "post",
        url: "/registerPost",
        data: JSON.stringify({
            "email": registerEmail,
            "password": registerPassword,
            "lastName": registerLN,
            "firstName": registerFN,
            "birth": registerBirth
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            if (data.result === "success") {
                alert(data.message);
                setTimeout(function () {
                    window.location.href = "login";
                }, 1000);
            } else {
                alert(data.message);
            }
        }
    });
}