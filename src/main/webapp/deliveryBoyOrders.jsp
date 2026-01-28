<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery Dashboard</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- jQuery and DataTables -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.1/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.13.1/js/jquery.dataTables.min.js"></script>
    
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-gray-100">
    <!-- Header section -->
    <div class="bg-green-600 text-white p-4 shadow-md">
        <div class="container mx-auto flex justify-between items-center">
            <h1 class="text-2xl font-bold">Food Delivery - Delivery Dashboard</h1>
            <div>
                <span class="mr-4">Welcome, ${user.name}</span>
                <a href="logout" class="bg-white text-green-600 px-4 py-2 rounded hover:bg-gray-100">Logout</a>
            </div>
        </div>
    </div>
    
    <!-- Main content -->
    <div class="container mx-auto py-8">
        <!-- Status overview cards -->
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
            <div class="bg-white rounded-lg shadow p-4 border-l-4 border-blue-500">
                <div class="flex items-center">
                    <div class="p-3 rounded-full bg-blue-100 text-blue-500 mr-4">
                        <i class="fas fa-hourglass text-2xl"></i>
                    </div>
                    <div>
                        <p class="text-sm text-gray-500">Pending Pickups</p>
                        <p class="text-xl font-bold" id="pendingCount">0</p>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-lg shadow p-4 border-l-4 border-yellow-500">
                <div class="flex items-center">
                    <div class="p-3 rounded-full bg-yellow-100 text-yellow-500 mr-4">
                        <i class="fas fa-motorcycle text-2xl"></i>
                    </div>
                    <div>
                        <p class="text-sm text-gray-500">In Transit</p>
                        <p class="text-xl font-bold" id="transitCount">0</p>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-lg shadow p-4 border-l-4 border-green-500">
                <div class="flex items-center">
                    <div class="p-3 rounded-full bg-green-100 text-green-500 mr-4">
                        <i class="fas fa-check-circle text-2xl"></i>
                    </div>
                    <div>
                        <p class="text-sm text-gray-500">Delivered Today</p>
                        <p class="text-xl font-bold" id="deliveredCount">0</p>
                    </div>
                </div>
            </div>
            
            <div class="bg-white rounded-lg shadow p-4 border-l-4 border-purple-500">
                <div class="flex items-center">
                    <div class="p-3 rounded-full bg-purple-100 text-purple-500 mr-4">
                        <i class="fas fa-star text-2xl"></i>
                    </div>
                    <div>
                        <p class="text-sm text-gray-500">Rating</p>
                        <p class="text-xl font-bold">4.8/5</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Orders tabs -->
        <div class="bg-white rounded-lg shadow-md p-6">
            <h2 class="text-xl font-bold mb-4">My Assigned Orders</h2>
            
            <ul class="nav nav-tabs mb-4" id="ordersTabs" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="pending-tab" data-bs-toggle="tab" data-bs-target="#pending" type="button" role="tab" aria-controls="pending" aria-selected="true">
                        Pending Pickup
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="transit-tab" data-bs-toggle="tab" data-bs-target="#transit" type="button" role="tab" aria-controls="transit" aria-selected="false">
                        In Transit
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="delivered-tab" data-bs-toggle="tab" data-bs-target="#delivered" type="button" role="tab" aria-controls="delivered" aria-selected="false">
                        Delivered
                    </button>
                </li>
            </ul>
            
            <div class="tab-content" id="ordersTabContent">
                <!-- Pending Pickup Tab -->
                <div class="tab-pane fade show active" id="pending" role="tabpanel" aria-labelledby="pending-tab">
                    <table id="pendingOrdersTable" class="display table table-striped w-full">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Restaurant</th>
                                <th>Customer</th>
                                <th>Delivery Address</th>
                                <th>Amount</th>
                                <th>Time</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Data will be loaded via AJAX -->
                        </tbody>
                    </table>
                </div>
                
                <!-- In Transit Tab -->
                <div class="tab-pane fade" id="transit" role="tabpanel" aria-labelledby="transit-tab">
                    <table id="transitOrdersTable" class="display table table-striped w-full">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Restaurant</th>
                                <th>Customer</th>
                                <th>Delivery Address</th>
                                <th>Amount</th>
                                <th>Pickup Time</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Data will be loaded via AJAX -->
                        </tbody>
                    </table>
                </div>
                
                <!-- Delivered Tab -->
                <div class="tab-pane fade" id="delivered" role="tabpanel" aria-labelledby="delivered-tab">
                    <table id="deliveredOrdersTable" class="display table table-striped w-full">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Restaurant</th>
                                <th>Customer</th>
                                <th>Delivery Address</th>
                                <th>Amount</th>
                                <th>Delivered Time</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Data will be loaded via AJAX -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Order Details Modal -->
    <div class="modal fade" id="orderDetailsModal" tabindex="-1" aria-labelledby="orderDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="orderDetailsModalLabel">Order Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h6 class="font-bold">Order Information</h6>
                            <p><strong>Order ID:</strong> <span id="detailOrderId"></span></p>
                            <p><strong>Date & Time:</strong> <span id="detailOrderDate"></span></p>
                            <p><strong>Total Amount:</strong> $<span id="detailAmount"></span></p>
                            <p><strong>Status:</strong> <span id="detailStatus" class="badge bg-info"></span></p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="font-bold">Delivery Information</h6>
                            <p><strong>Customer:</strong> <span id="detailCustomer"></span></p>
                            <p><strong>Phone:</strong> <span id="detailPhone"></span></p>
                            <p><strong>Address:</strong> <span id="detailAddress"></span></p>
                        </div>
                    </div>
                    <h6 class="font-bold mb-3">Order Items</h6>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Item</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody id="orderItemsTable">
                            <!-- Order items will be loaded via AJAX -->
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer" id="orderActionFooter">
                    <!-- Action buttons will be added dynamically based on status -->
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
    <script>
    $(document).ready(function() {
        const deliveryBoyId = ${user.userId};
        let currentOrderDetails = null;

        // Initialize DataTables
        const pendingOrdersTable = $('#pendingOrdersTable').DataTable({
            columnDefs: [{ orderable: false, targets: [6] }]
        });
        const transitOrdersTable = $('#transitOrdersTable').DataTable({
            columnDefs: [{ orderable: false, targets: [6] }]
        });
        const deliveredOrdersTable = $('#deliveredOrdersTable').DataTable();

        // Load orders and update dashboard
        function loadOrders() {
            $.ajax({
                url: 'DeliveryBoyServlet',
                method: 'GET',
                data: { action: 'getOrders', deliveryBoyId: deliveryBoyId },
                dataType: 'json',
                success: function(data) {
                    let pendingCount = 0, transitCount = 0, deliveredCount = 0;
                    
                    pendingOrdersTable.clear();
                    transitOrdersTable.clear();
                    deliveredOrdersTable.clear();

                    data.forEach(order => {
                        const baseData = [
                            order.id,
                            order.restaurant.name,
                            order.customer.name,
                            order.deliveryAddress,
                            '₹' + order.totalAmount.toFixed(2),
                            new Date(order.orderTime).toLocaleTimeString()
                        ];

                        if (order.status === 'PENDING') {
                            pendingCount++;
                            pendingOrdersTable.row.add([
                                ...baseData,
                                `<div class="btn-group">
                                    <button class="btn btn-sm btn-primary viewDetailsBtn" data-id="${order.id}">
                                        <i class="fas fa-info-circle"></i>
                                    </button>
                                    <button class="btn btn-sm btn-success pickupBtn" data-id="${order.id}">
                                        <i class="fas fa-box"></i>
                                    </button>
                                </div>`
                            ]);
                        } else if (order.status === 'IN_TRANSIT') {
                            transitCount++;
                            transitOrdersTable.row.add([
                                ...baseData,
                                new Date(order.pickupTime).toLocaleTimeString(),
                                `<div class="btn-group">
                                    <button class="btn btn-sm btn-primary viewDetailsBtn" data-id="${order.id}">
                                        <i class="fas fa-info-circle"></i>
                                    </button>
                                    <button class="btn btn-sm btn-success deliverBtn" data-id="${order.id}">
                                        <i class="fas fa-check"></i>
                                    </button>
                                </div>`
                            ]);
                        } else if (order.status === 'DELIVERED') {
                            deliveredCount++;
                            deliveredOrdersTable.row.add([
                                ...baseData,
                                new Date(order.deliveredTime).toLocaleTimeString(),
                                '<span class="badge bg-success">Delivered</span>'
                            ]);
                        }
                    });

                    // Update status counts
                    $('#pendingCount').text(pendingCount);
                    $('#transitCount').text(transitCount);
                    $('#deliveredCount').text(deliveredCount);

                    // Redraw tables
                    pendingOrdersTable.draw();
                    transitOrdersTable.draw();
                    deliveredOrdersTable.draw();
                }
            });
        }

        // Handle order status updates
        function updateOrderStatus(orderId, status) {
            $.ajax({
                url: 'DeliveryBoyServlet',
                method: 'POST',
                data: { 
                    action: 'updateStatus',
                    orderId: orderId,
                    status: status
                },
                success: function() {
                    loadOrders();
                    $('#orderDetailsModal').modal('hide');
                }
            });
        }

        // Event handlers
        $(document).on('click', '.viewDetailsBtn', function() {
            const orderId = $(this).data('id');
            $.ajax({
                url: 'DeliveryBoyServlet',
                method: 'GET',
                data: { action: 'getOrderDetails', orderId: orderId },
                dataType: 'json',
                success: function(order) {
                    $('#detailOrderId').text(order.id);
                    $('#detailOrderDate').text(new Date(order.orderTime).toLocaleString());
                    $('#detailAmount').text('₹' + order.totalAmount.toFixed(2));
                    $('#detailStatus').text(order.status).removeClass().addClass('badge ' + 
                        (order.status === 'PENDING' ? 'bg-warning' : 
                         order.status === 'IN_TRANSIT' ? 'bg-info' : 'bg-success'));
                    $('#detailCustomer').text(order.customer.name);
                    $('#detailPhone').text(order.customer.phone);
                    $('#detailAddress').text(order.deliveryAddress);

                    $('#orderItemsTable').empty();
                    order.items.forEach(item => {
                        $('#orderItemsTable').append(`
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.quantity}</td>
                                <td>₹${item.price.toFixed(2)}</td>
                                <td>₹${(item.quantity * item.price).toFixed(2)}</td>
                            </tr>
                        `);
                    });

                    $('#orderActionFooter').empty();
                    if (order.status === 'PENDING') {
                        $('#orderActionFooter').append(`
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary confirmPickupBtn">Confirm Pickup</button>
                        `);
                    } else if (order.status === 'IN_TRANSIT') {
                        $('#orderActionFooter').append(`
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-success confirmDeliveryBtn">Confirm Delivery</button>
                        `);
                    }

                    $('#orderDetailsModal').modal('show');
                }
            });
        });

        $(document).on('click', '.pickupBtn, .confirmPickupBtn', function() {
            const orderId = $(this).data('id') || $('.confirmPickupBtn').data('id');
            updateOrderStatus(orderId, 'IN_TRANSIT');
        });

        $(document).on('click', '.deliverBtn, .confirmDeliveryBtn', function() {
            const orderId = $(this).data('id') || $('.confirmDeliveryBtn').data('id');
            updateOrderStatus(orderId, 'DELIVERED');
        });

        // Initial load
        loadOrders();
        setInterval(loadOrders, 30000); // Refresh every 30 seconds
    });
</script>
</body>
</html>