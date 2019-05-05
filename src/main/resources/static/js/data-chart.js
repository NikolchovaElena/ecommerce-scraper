// $(document).ready(function () {
//     $('#btn-delete-comment button').click(function (event) {
//         event.preventDefault();
//         var id = $(this).attr('comment_id');
//
//         $.ajax({
//             headers: {
//                 'Accept': 'application/json',
//                 'Content-Type': 'application/json'
//             },
//             url: '/logs/product/' + id,
//             type: 'GET'
//             })
//             .done(function () {
//
//             })
//             .fail(function () {
//                 console.log("Could not load chart");
//             });
//     });
// });

var ctx = document.getElementById('myChart').getContext('2d');
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'bar',
    // The data for our dataset
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
            label: 'Price',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: [0, 10, 5, 2, 20, 30, 45]
        }]
    },

    // Configuration options go here
    options: {}
});

