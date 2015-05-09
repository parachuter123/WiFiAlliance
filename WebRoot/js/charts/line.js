$(function () {
    var Users = [];
    Users.push([1, 20]);
    Users.push([2, 22]);
    Users.push([3, 20]);
    Users.push([4, 20]);
    Users.push([5, 30]);
    Users.push([6, 20]);
    Users.push([7, 10]);
    Users.push([8, 20]);
    Users.push([9, 50]);
    Users.push([10, 10]);

    var plot = $.plot($("#line-chart"),
           [ { data: Users, label: "接入用户数目"} ], {
               series: {
                   lines: { show: true },
                   points: { show: true }
               },
               
               grid: { hoverable: true, clickable: true },
               yaxis: { min: 1, max: 60 },
			   xaxis: { min: 1, max: 10 },
    	colors: ["#F90", "#3C4049", "#666", "#BBB"]
             });
});