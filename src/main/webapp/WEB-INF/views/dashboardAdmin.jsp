<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <title>Validate Information</title>
    <link rel="stylesheet" href="./resources/css/headerAdmin.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {

            var data = google.visualization.arrayToDataTable([
                ['Month', 'Water Consumption', 'Electricity Consumption', 'Recycle Activity'],
                ['January', 200, 500, 50],    
                ['February', 250, 550, 60],   
                ['March', 180, 480, 140],  
                ['April', 100, 500, 50],    
                ['May', 150, 50, 160],   
                ['Jun', 180, 480, 140],    
                ['July', 290, 40, 240],  
            ]);

            var options = {
                title: 'Carbon Emission',
                isStacked: true,
            };

            var chart = new google.visualization.ColumnChart(document.getElementById('environmentChart'));
            chart.draw(data, options);
        }
    </script>
    <script>
        $(document).ready(function () {
            $("#header").load("headerAdmin.html");
        });
    </script>
</head>
<style>
    .report-collection {
        display: flex;
    }

    .report-card {
        display: flex;
        align-items: flex-start;
        width: 300px;
        padding: 1%;
        border: 1px solid grey;
        border-radius: 5px;
        margin: 3% 0 0 5%;
    }

    .report-card svg {
        margin: 10px 20px 10px 10px;
    }
</style>

<body>
    <!-- <div id="header"></div> -->
    <jsp:include page="headerAdmin.jsp" />
    <div class="title">
        <h1>DASHBOARD</h1>
    </div>
    <div class="report-collection">
        <div class="report-card">
            <div>
                <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor"
                    class="bi bi-person-fill-check" viewBox="0 0 16 16">
                    <path
                        d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m1.679-4.493-1.335 2.226a.75.75 0 0 1-1.174.144l-.774-.773a.5.5 0 0 1 .708-.708l.547.548 1.17-1.951a.5.5 0 1 1 .858.514ZM11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0" />
                    <path
                        d="M2 13c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4" />
                </svg>
            </div>
            <div>
                <h2>1290 Participant</h2>
            </div>
        </div>
        <div class="report-card">
            <div>
                <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor"
                    class="bi bi-globe-asia-australia" viewBox="0 0 16 16">
                    <path
                        d="m10.495 6.92 1.278-.619a.483.483 0 0 0 .126-.782c-.252-.244-.682-.139-.932.107-.23.226-.513.373-.816.53l-.102.054c-.338.178-.264.626.1.736a.476.476 0 0 0 .346-.027ZM7.741 9.808V9.78a.413.413 0 1 1 .783.183l-.22.443a.602.602 0 0 1-.12.167l-.193.185a.36.36 0 1 1-.5-.516l.112-.108a.453.453 0 0 0 .138-.326ZM5.672 12.5l.482.233A.386.386 0 1 0 6.32 12h-.416a.702.702 0 0 1-.419-.139l-.277-.206a.302.302 0 1 0-.298.52l.761.325Z" />
                    <path
                        d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0M1.612 10.867l.756-1.288a1 1 0 0 1 1.545-.225l1.074 1.005a.986.986 0 0 0 1.36-.011l.038-.037a.882.882 0 0 0 .26-.755c-.075-.548.37-1.033.92-1.099.728-.086 1.587-.324 1.728-.957.086-.386-.114-.83-.361-1.2-.207-.312 0-.8.374-.8.123 0 .24-.055.318-.15l.393-.474c.196-.237.491-.368.797-.403.554-.064 1.407-.277 1.583-.973.098-.391-.192-.634-.484-.88-.254-.212-.51-.426-.515-.741a6.998 6.998 0 0 1 3.425 7.692 1.015 1.015 0 0 0-.087-.063l-.316-.204a1 1 0 0 0-.977-.06l-.169.082a1 1 0 0 1-.741.051l-1.021-.329A1 1 0 0 0 11.205 9h-.165a1 1 0 0 0-.945.674l-.172.499a1 1 0 0 1-.404.514l-.802.518a1 1 0 0 0-.458.84v.455a1 1 0 0 0 1 1h.257a1 1 0 0 1 .542.16l.762.49a.998.998 0 0 0 .283.126 7.001 7.001 0 0 1-9.49-3.409Z" />
                </svg>
            </div>
            <div>
                <h2>81887 Emission</h2>
            </div>
        </div>
        <div class="report-card">
            <div>
                <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor"
                    class="bi bi-file-earmark-check" viewBox="0 0 16 16">
                    <path
                        d="M10.854 7.854a.5.5 0 0 0-.708-.708L7.5 9.793 6.354 8.646a.5.5 0 1 0-.708.708l1.5 1.5a.5.5 0 0 0 .708 0l3-3z" />
                    <path
                        d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z" />
                </svg>
            </div>
            <div>
                <h2>1290 Submission</h2>
            </div>
        </div>
    </div>
    <div>
        <div id="environmentChart" style="width: 1000px; height: 400px;"></div>
    </div>
</body>
</html>