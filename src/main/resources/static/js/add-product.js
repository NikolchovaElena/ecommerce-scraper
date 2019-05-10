$(document).ready(function () {
    $('#btn-add-product').click(function (event) {
        event.preventDefault();
        var name = $('#product-name').val();
        var price = $('#product-price').val();
        var currency = $('#product-currency').val();

       var data = {
            name: name,
            currentPrice: price,
            currency: currency
        };


        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/add/product/form',
            type: 'POST',
            data: JSON.stringify(data)
        })
            .done(function () {
                window.location = '/add/form';
            })
            .fail(function () {
                console.log("Could not add product");
            });
    });
});