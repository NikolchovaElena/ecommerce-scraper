//turn to inline mode
$.fn.editable.defaults.mode = 'inline';

$(document).ready(function () {
    $('#url').editable({
        success: function (response) {
            if (response.status === 'error') return response.msg;
        }
    });

    $('#xPathToPrice').editable({
        success: function (response) {
            if (response.status === 'error') return response.msg;
        }
    });

    $('#xPathToTitle').editable({
        success: function (response) {
            if (response.status === 'error') return response.msg;
        }
    });

    $('.product-name').editable({
        type: 'text',
        pk: '',
        url: '/edit/product-name',
        success: function (response) {
            if (response.status === 'error') return response.msg;
            return window.location = '/';
        }
    });

    $('.product-name-single-edit').editable({
        success: function (response) {
            if (response.status === 'error') return response.msg;
        }
    });

    $('.current-price-edit').editable({
        success: function (response) {
            if (response.status === 'error') return response.msg;
        }
    });

    $('.currency-edit').editable({
        success: function (response) {
            if (response.status === 'error') return response.msg;
        }
    });


});
