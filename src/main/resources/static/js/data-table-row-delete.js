$(document).ready(function () {

    $('#btn-delete-competitor button').click(function (event) {
        event.preventDefault();
        var id = $(this).attr('competitor_id');

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/delete/competitors/' + id,
            type: 'POST'
        })
            .done(function () {
                window.location = '/';
            })
            .fail(function () {
                console.log("Could not delete entry");
            });
    });
});