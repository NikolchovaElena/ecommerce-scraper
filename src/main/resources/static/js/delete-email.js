$(document).ready(function () {

    $('#btn-delete-email button').click(function (event) {
        event.preventDefault();
        var id = $(this).attr('email_id');

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/delete/email/' + id,
            type: 'POST'
        })
            .done(function () {
                window.location = '/emails';
            })
            .fail(function () {
                console.log("Could not delete email");
            });
    });
});