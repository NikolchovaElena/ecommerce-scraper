$(document).ready(function () {

    $('#btn-delete-product button').click(function (event) {
        event.preventDefault();
        var id = $(this).attr('product_id');

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/delete/product/' + id,
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