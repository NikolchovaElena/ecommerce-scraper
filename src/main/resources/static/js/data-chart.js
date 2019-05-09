$(document).ready(function () {
    var width = $("#container").width();

    var id = $('#competitor_id').val();
    var url = $('#series_name').val();

    var myRegexp = /^(?:https?:\/\/)?(?:[^@\n]+@)?(?:www\.)?([^:\/\n?]+)/g;
    var match = myRegexp.exec(url);
    var seriesName = match[1];

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        url: '/logs/competitors/' + id,
        type: 'GET'
    })
        .done(function (data) {
            console.log(data);
            var chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'container',
                    defaultSeriesType: 'line',
                    zoomType: 'x',
                    events: {
                        resize: function () {
                            var w = this.chartWidth;
                            //var h=this.chartHeight;
                            myZoomButton.attr({
                                //SVG
                                transform: 'translate(' + (w - 90) + ',' + 50 + ')'
                            }).css({
                                //VML
                                left: (w - 90) + 'px',
                                top: 50 + 'px'
                            });
                        },
                        selection: function (event) {
                            if (event.xAxis) {
                                var fromMillis = Math.floor(event.xAxis[0].min);
                                var toMillis = Math.ceil(event.xAxis[0].max);

                                console.log("min = " + fromMillis);
                                console.log("max = " + toMillis);

                                chart.xAxis[0].setExtremes(fromMillis, toMillis, false);
                                chart.redraw();
                                myZoomButton.show();
                            }

                            // prevent default zoom reset button creation
                            event.preventDefault();
                        }
                    }
                },
                title: {
                    text: 'Price chart'
                },
                xAxis: {
                    type: 'datetime',
                    minRange: 259200000 // three days (3 * 24 * 3600 * 1000)
                },
                yAxis: {
                    title: {
                        text: 'Price'
                    }
                },
                series: [{
                    name: seriesName,
                    data: []
                }],
                responsive: {
                    rules: [{
                        condition: {
                            maxWidth: 500
                        },
                        chartOptions: {
                            legend: {
                                layout: 'horizontal',
                                align: 'center',
                                verticalAlign: 'bottom'
                            }
                        }
                    }]
                }

            });

            // populate chart series
            for (var i = 0; i < data.length; i++) {
                chart.series[0].addPoint([data[i].timestamp, data[i].value], false, false, false);
            }

            // show chart
            chart.redraw();

            // custom zoom reset button (is used instead of the default one)
            var myZoomButton = chart.renderer.button('Reset zoooom', chart.chartWidth - 90, 50, function () {
                chart.xAxis[0].setExtremes(null, null, false);
                chart.redraw();
                myZoomButton.hide();
            });

            drawSpecialButtons(chart)

            myZoomButton.hide().add();

            function drawSpecialButtons(chart) {

                var my1mButton
                var my3mButton
                var my6mButton
                var my1yButton
                var myMTDButton
                var myYTDButton
                var mySincePublicationButton
                var myAllButton

                function unselectButtons() {
                    my1mButton.setState(0)
                    my3mButton.setState(0)
                    my6mButton.setState(0)
                    my1yButton.setState(0)
                    myMTDButton.setState(0)
                    myYTDButton.setState(0)
                    mySincePublicationButton.setState(0)
                    myAllButton.setState(0)
                }

                my1mButton = chart.renderer.button('1m', 0, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    startDate.setMonth(startDate.getMonth() - 1);
                    var startMilliseconds = startDate.getTime();
                    if (startMilliseconds < chart.xAxis[0].getExtremes().dataMin) {
                        chart.xAxis[0].setExtremes(null, lastMilliseconds, false);
                    } else {
                        chart.xAxis[0].setExtremes(startMilliseconds, lastMilliseconds, false);
                    }
                    unselectButtons()
                    my1mButton.setState(2)
                    chart.redraw();
                })

                my3mButton = chart.renderer.button('3m', 26, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    var newMonth = startDate.getMonth() - 3
                    if (newMonth < -1) {
                        startDate.setFullYear(startDate.getFullYear() - 1);
                        newMonth = newMonth + 11;
                    }
                    startDate.setMonth(newMonth);
                    var startMilliseconds = startDate.getTime();
                    if (startMilliseconds < chart.xAxis[0].getExtremes().dataMin) {
                        chart.xAxis[0].setExtremes(null, lastMilliseconds, false);
                    } else {
                        chart.xAxis[0].setExtremes(startMilliseconds, lastMilliseconds, false);
                    }
                    unselectButtons()
                    my3mButton.setState(2)
                    chart.redraw();
                })

                my6mButton = chart.renderer.button('6m', 52, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    var newMonth = startDate.getMonth() - 6
                    if (newMonth < -1) {
                        startDate.setFullYear(startDate.getFullYear() - 1);
                        newMonth = newMonth + 11;
                    }
                    startDate.setMonth(newMonth);
                    var startMilliseconds = startDate.getTime();
                    if (startMilliseconds < chart.xAxis[0].getExtremes().dataMin) {
                        chart.xAxis[0].setExtremes(null, lastMilliseconds, false);
                    } else {
                        chart.xAxis[0].setExtremes(startMilliseconds, lastMilliseconds, false);
                    }
                    unselectButtons()
                    my6mButton.setState(2)
                    chart.redraw();
                })

                my1yButton = chart.renderer.button('1y', 78, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    startDate.setFullYear(startDate.getFullYear() - 1);
                    var startMilliseconds = startDate.getTime();
                    if (startMilliseconds < chart.xAxis[0].getExtremes().dataMin) {
                        chart.xAxis[0].setExtremes(null, lastMilliseconds, false);
                    } else {
                        chart.xAxis[0].setExtremes(startMilliseconds, lastMilliseconds, false);
                    }
                    unselectButtons()
                    my1yButton.setState(2)
                    chart.redraw();
                })

                myMTDButton = chart.renderer.button('MTD', 99, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    startDate.setDate(1);
                    chart.xAxis[0].setExtremes(startDate.getTime(), lastMilliseconds, false);
                    unselectButtons()
                    myMTDButton.setState(2)
                    chart.redraw();
                })

                myYTDButton = chart.renderer.button('YTD', 131, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    startDate.setMonth(0, 1);
                    var startMilliseconds = startDate.getTime();
                    if (startMilliseconds < chart.xAxis[0].getExtremes().dataMin) {
                        chart.xAxis[0].setExtremes(null, lastMilliseconds, false);
                    } else {
                        chart.xAxis[0].setExtremes(startMilliseconds, lastMilliseconds, false);
                    }
                    unselectButtons()
                    myYTDButton.setState(2)
                    chart.redraw();
                })

                mySincePublicationButton = chart.renderer.button('SP', 160, 0, function () {
                    var lastMilliseconds = chart.xAxis[0].getExtremes().dataMax
                    var startDate = new Date(lastMilliseconds)
                    startDate.setMonth(startDate.getMonth() - 1);
                    var startMilliseconds = startDate.getTime();
                    if (startMilliseconds < chart.xAxis[0].getExtremes().dataMin) {
                        chart.xAxis[0].setExtremes(null, lastMilliseconds, false);
                    } else {
                        chart.xAxis[0].setExtremes(startMilliseconds, lastMilliseconds, false);
                    }
                    unselectButtons()
                    mySincePublicationButton.setState(2)
                    chart.redraw();
                })

                myAllButton = chart.renderer.button('All', 181, 0, function () {
                    chart.xAxis[0].setExtremes(null, null, false);
                    unselectButtons()
                    myAllButton.setState(2)
                    chart.redraw();
                })

                my1mButton.add()
                my3mButton.add()
                my6mButton.add()
                my1yButton.add()
                myMTDButton.add()
                myYTDButton.add()
                mySincePublicationButton.add()
                myAllButton.add()
            }
        })
        .fail(function () {
            console.log("Could not load chart data");
        });
});


