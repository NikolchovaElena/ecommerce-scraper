//turn to inline mode
$.fn.editable.defaults.mode = 'inline';

$(document).ready(function() {
    $('#url').editable({
        success: function(response) {
            if(response.status === 'something went wrong') return response.msg;
        }
    });

    $('#xPathToPrice').editable({
            success: function(response) {
                if(response.status === 'something went wrong') return response.msg;
            }
        });
    $('#xPathToTitle').editable({
        success: function(response) {
            if(response.status === 'something went wrong') return response.msg;
        }
    });
});
