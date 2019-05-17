$(document).ready(function () {

    $('#xPath-price-btn').click(function (event) {
        event.preventDefault();
        var xPathToPrice = $('#inputPriceXPath');

        scrape(xPathToPrice);
    });

    $('#xPath-title-btn').click(function (event) {
        event.preventDefault();
        var xPathToTitle = $('#inputTitleXPath');

        scrape(xPathToTitle);
    });

    var scrape = function (xPathId) {
        var url = $('#inputProductLink').val();
        var data = {
            url: url,
            xPath: xPathId.val()
        };
        console.log(data);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/scrape/input',
            type: 'POST',
            data: JSON.stringify(data)
        })
            .done(function (data) {
                if ((xPathId).next().is('div')) {
                    (xPathId).next().remove();
                }
                if (data.response == null) {
                    $(xPathId)
                        .after(`<div class="text-danger">Incorrect xPath</div>`);
                } else {
                    $(xPathId)
                        .after(`<div class="text-danger">${data.response}</div>`);
                }
            })
            .fail(function ($xhr, textStatus, errorThrown) {
                console.log("ERROR : ", errorThrown);
                console.log("ERROR : ", $xhr);
                console.log("ERROR : ", textStatus);
            });
    }
});