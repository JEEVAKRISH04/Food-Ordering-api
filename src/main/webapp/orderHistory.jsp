<!DOCTYPE html>
<html>
<head>
    <title>Orders DataTable</title>

    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">

    <!-- jQuery (LOAD FIRST) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" defer></script>

    <!-- DataTables JS (AFTER jQuery) -->
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js" defer></script>

    <style>
        table.dataTable {
            width: 100% !important;
            border-collapse: collapse;
        }
        table.dataTable th, table.dataTable td {
            text-align: center;
            padding: 8px;
        }
        table.dataTable thead th {
            padding: 10px 8px;
        }
        table.dataTable tbody td {
            padding: 10px 8px;
        }
    </style>
</head>

<body class="bg-gray-100 min-h-screen">

    <%@ include file="header.jsp" %>

    <!-- Wrapper div -->
    <div class="pt-28">

        <div class="w-[95%] mx-auto mb-4">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb flex text-gray-600 bg-white px-4 py-2 rounded-md shadow-sm">
                    <li class="breadcrumb-item flex items-center">
                        <i class="bi bi-house-door-fill mr-2"></i>
                        <a href="AdminDashboard" class="text-blue-600 hover:underline">Restaurant</a>
                    </li>
                    <li class="flex items-center">
                <span class="mx-2">/</span>
                <span>Orders History</span>
            </li>
                </ol>
            </nav>
        </div>

        <div class="w-[95%] mx-auto bg-white p-4 rounded-md shadow-md">
            <h2 class="text-2xl font-semibold mb-4 text-center">Orders History Table</h2>

            <table id="ordersTable" class="display">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>User ID</th>
                        <th>Restaurant ID</th>
                        <th>Total Amount</th>
                        <th>Status</th>
                        <th>Order Date</th>
                        <th>Delivery Address</th>
                    </tr>
                </thead>
            </table>

        </div>
    </div>

    <!-- DataTable Initialization Script -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            $('#ordersTable').DataTable({
                "ajax": {
                    "url": "ordersdata",
                    "dataSrc": function (json) {
                        console.log("Data received from Servlet:", json); // Debugging JSON
                        return json;
                    },
                    "error": function (xhr, error, thrown) {
                        console.error("Error fetching data:", error, thrown);
                    }
                },
                "columns": [
                    { "data": "orderId" },
                    { "data": "userId" },
                    { "data": "restaurantId" },
                    { "data": "totalAmount" },
                    { "data": "status" },
                    { "data": "orderDate" },
                    { "data": "deliveryAddress" }
                ],
                "autoWidth": false,
                "scrollX": true,
                "width": "100%"
            });
        });
    </script>

</body>
</html>
