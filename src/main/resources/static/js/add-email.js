$(document).ready(function () {
    $('#btn-add-email').click(function (event) {
        event.preventDefault();
        var email = $('#email').val();

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/add/email',
            type: 'POST',
            data: email
        })
            .done(function () {
                window.location = '/emails';
            })
            .fail(function () {
                console.log("Could not add email");
            });
    });
});