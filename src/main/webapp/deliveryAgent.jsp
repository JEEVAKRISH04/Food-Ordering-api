<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delivery Management - Admin</title>

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

    <!-- Custom Styles -->
    <style>
        .btn-primary {
            @apply bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out transform hover:scale-105 flex items-center justify-center gap-2;
        }
        .btn-success {
            @apply bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out transform hover:scale-105 flex items-center justify-center gap-2;
        }
        .btn-danger {
            @apply bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out transform hover:scale-105 flex items-center justify-center gap-2;
        }
        .btn-info {
            @apply bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out transform hover:scale-105 flex items-center justify-center gap-2;
        }
        .btn-warning {
            @apply bg-yellow-500 hover:bg-yellow-600 text-white font-semibold py-2 px-4 rounded-lg transition duration-300 ease-in-out transform hover:scale-105 flex items-center justify-center gap-2;
        }
    </style>
</head>
<body class="bg-gray-100 min-h-screen">
    <%@ include file="header.jsp" %>
    <div class="container mx-auto px-4 py-6 pt-20">
 
           <div class="px-6 py-4 border-b border-gray-200 ">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb flex text-gray-600 bg-white px-4 py-2 rounded-md shadow-sm">
            <li class="flex items-center">
                <i class="bi bi-house-door-fill mr-2 text-blue-600"></i>
                <a href="AdminDashboard" class="text-blue-600 no-underline hover:text-blue-700">
                    Admin Dashboard
                </a>
            </li>
            <li class="flex items-center">
                <span class="mx-2">/</span>
                <span>Delivery Management</span>
            </li>
        </ol>
    </nav>
</div>

        <!-- Main content -->
        <div class="container mx-auto ">
            <div class="bg-white rounded-lg shadow-md p-6 mb-6">
                <h2 class="text-xl font-bold mb-4">Delivery Management</h2>
                <ul class="nav nav-tabs mb-4" id="deliveryTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="pending-tab" data-bs-toggle="tab" data-bs-target="#pending" type="button" role="tab" aria-controls="pending" aria-selected="true">
                            <i class="bi bi-clock-fill mr-2"></i>Pending Orders
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="assigned-tab" data-bs-toggle="tab" data-bs-target="#assigned" type="button" role="tab" aria-controls="assigned" aria-selected="false">
                            <i class="bi bi-truck mr-2"></i>Assigned Orders
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="delivery-boys-tab" data-bs-toggle="tab" data-bs-target="#delivery-boys" type="button" role="tab" aria-controls="delivery-boys" aria-selected="false">
                            <i class="bi bi-people-fill mr-2"></i>Delivery Boys
                        </button>
                    </li>
                </ul>
                <div class="tab-content" id="deliveryTabContent">
                    <!-- Pending Orders Tab -->
                    <div class="tab-pane fade show active" id="pending" role="tabpanel" aria-labelledby="pending-tab">
                        <table id="pendingOrdersTable" class="display table table-striped w-full">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Restaurant</th>
                                    <th>Amount</th>
                                    <th>Date</th>
                                    <th>Address</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Data will be loaded via AJAX -->
                            </tbody>
                        </table>
                    </div>
                     <!-- Assigned Orders Tab -->
                    <div class="tab-pane fade" id="assigned" role="tabpanel" aria-labelledby="assigned-tab">
                        <table id="assignedOrdersTable" class="display table table-striped w-full">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Restaurant</th>
                                    <th>Amount</th>
                                    <th>Delivery Boy</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Data will be loaded via AJAX -->
                            </tbody>
                        </table>
                    </div>
                    <!-- Delivery Boys Tab -->
                    <div class="tab-pane fade" id="delivery-boys" role="tabpanel" aria-labelledby="delivery-boys-tab">
                        <table id="deliveryBoysTable" class="display table table-striped w-full">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Phone</th>
                                    <th>Email</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Data will be loaded via AJAX -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        
        <!-- Assign Delivery Boy Modal -->
        <div class="modal fade" id="assignDeliveryBoyModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title flex items-center">
                            <i class="bi bi-truck mr-2"></i>Assign Delivery Boy
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="assignOrderId">
                        <div class="mb-3">
                            <label for="deliveryBoySelect" class="form-label flex items-center">
                                <i class="bi bi-person-check-fill mr-2"></i>Select Delivery Boy
                            </label>
                            <select id="deliveryBoySelect" class="form-select">
                                <!-- Options will be dynamically populated -->
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary flex items-center" data-bs-dismiss="modal">
                            <i class="bi bi-x-circle mr-2"></i>Close
                        </button>
                        <button type="button" class="btn btn-primary flex items-center" id="assignDeliveryBoyBtn">
                            <i class="bi bi-check-circle mr-2"></i>Assign
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Bootstrap, jQuery and custom script -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Bootstrap CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">

   <script>
   $(document).ready(function() {
	    console.log('Document Ready - Initializing Delivery Management...');

	    // 1️⃣ Initialize Pending Orders DataTable
	    let pendingOrdersTable = $('#pendingOrdersTable').DataTable({
	        columns: [
	            { data: 'orderId' },
	            { data: 'userName' },
	            { data: 'restaurantName' },
	            { 
	                data: 'totalAmount', 
	                render: DataTable.render.number(',', '.', 2, '₹') 
	            },
	            { 
	                data: 'orderDate', 
	                render: function(data) {
	                    return new Date(data).toLocaleDateString();
	                }
	            },
	            { data: 'deliveryAddress' },
	            { 
	                data: null,
	                render: function(data, type, row) {
	                    // Ensure we have the orderId in the button's data attributes
	                    return `
	                        <button class="btn btn-sm btn-primary assign-delivery-boy" 
	                                data-order-id="${row.orderId}">
	                            Assign Delivery Boy
	                        </button>
	                    `;
	                }
	            }
	        ]
	    });

	    // 2️⃣ Initialize Assigned Orders DataTable
	    let assignedOrdersTable = $('#assignedOrdersTable').DataTable({
	        columns: [
	            { data: 'orderId' },
	            { data: 'userName' },
	            { data: 'restaurantName' },
	            { 
	                data: 'totalAmount', 
	                render: DataTable.render.number(',', '.', 2, '₹') 
	            },
	            { 
	                data: 'deliveryBoyName', 
	                render: function(data) {
	                    return data || 'Not Assigned';
	                }
	            },
	            { data: 'status' },
	            { 
	                data: null,
	                render: function(data, type, row) {
	                    return `
	                        <button class="btn btn-sm btn-info view-order-details" 
	                                data-id="${row.orderId}">
	                            View Details
	                        </button>
	                    `;
	                }
	            }
	        ]
	    });

	    // 3️⃣ Initialize Delivery Boys DataTable
	    let deliveryBoysTable = $('#deliveryBoysTable').DataTable({
	        columns: [
	            { data: 'userId' },
	            { data: 'name' },
	            { data: 'phoneNo' },
	            { data: 'email' },
	            { 
	                data: 'status', 
	                render: function(data) {
	                    return data || 'Active';
	                }
	            },
	            { 
	                data: null,
	                render: function(data, type, row) {
	                    return `
	                        <button class="btn btn-sm btn-warning view-delivery-boy" 
	                                data-id="${row.userId}">
	                            View Details
	                        </button>
	                    `;
	                }
	            }
	        ]
	    });

	    // 4️⃣ Load Pending Orders
	    function loadPendingOrders(table) {
	        console.log('Loading Pending Orders...');
	        $.ajax({
	            url: 'pending-orders',
	            method: 'GET',
	            dataType: 'json',
	            success: function(data) {
	                console.log('Pending Orders Data:', data);
	                table.clear();
	                table.rows.add(data).draw();
	            },
	            error: function(xhr, status, error) {
	                console.error("Error loading pending orders:", error);
	                alert("Failed to load pending orders: " + error);
	            }
	        });
	    }

	    // 5️⃣ Load Assigned Orders
	    function loadAssignedOrders(table) {
	        console.log('Loading Assigned Orders...');
	        $.ajax({
	            url: 'assigned-orders',
	            method: 'GET',
	            dataType: 'json',
	            success: function(data) {
	                console.log('Assigned Orders Data:', data);
	                table.clear();
	                table.rows.add(data).draw();
	            },
	            error: function(xhr, status, error) {
	                console.error("Error loading assigned orders:", error);
	                alert("Failed to load assigned orders: " + error);
	            }
	        });
	    }

	    // 6️⃣ Load Delivery Boys
	    function loadDeliveryBoys(table) {
	        console.log('Loading Delivery Boys...');
	        $.ajax({
	            url: 'deliveryBoys',
	            method: 'GET',
	            dataType: 'json',
	            success: function(data) {
	                console.log('Delivery Boys Data:', data);
	                table.clear();
	                table.rows.add(data).draw();
	            },
	            error: function(xhr, status, error) {
	                console.error("Error loading delivery boys:", error);
	                alert("Failed to load delivery boys: " + error);
	            }
	        });
	    }

	    // 7️⃣ Load Delivery Boys for Modal Dropdown
	    function loadDeliveryBoysForDropdown() {
	        $.ajax({
	            url: 'deliveryBoys',
	            method: 'GET',
	            dataType: 'json',
	            success: function(data) {
	                // Clear previous options
	                $('#deliveryBoySelect').empty();
	                
	                // Add default option
	                $('#deliveryBoySelect').append('<option value="">Select a delivery boy</option>');
	                
	                // Add delivery boys to dropdown
	                $.each(data, function(i, boy) {
	                    $('#deliveryBoySelect').append(
	                        $('<option></option>').val(boy.userId).text(boy.name + ' - ' + boy.phoneNo)
	                    );
	                });
	            },
	            error: function(xhr, status, error) {
	                console.error("Error loading delivery boys for dropdown:", error);
	                alert("Failed to load delivery boys dropdown: " + error);
	            }
	        });
	    }

	    // 8️⃣ Assign Delivery Boy Button Click Handler (UPDATED)
	    $(document).on('click', '.assign-delivery-boy', function() {
	        // Method 1: Try to get orderId from data attribute
	        let orderId = $(this).data('order-id');
	        
	        // Method 2: Try to get orderId from DataTables row data
	        if (!orderId) {
	            let rowData = pendingOrdersTable.row($(this).closest('tr')).data();
	            orderId = rowData ? rowData.orderId : null;
	        }

	        // Detailed logging
	        console.log('Assign Delivery Boy - Raw Data:', {
	            buttonElement: this,
	            dataAttribute: $(this).data('order-id'),
	            rowData: pendingOrdersTable.row($(this).closest('tr')).data(),
	            retrievedOrderId: orderId
	        });

	        // Robust validation
	        if (!orderId) {
	            console.error('Could not retrieve Order ID');
	            alert('Error: Unable to find Order ID. Please try again or contact support.');
	            return;
	        }

	        // Set order ID in hidden input
	        $('#assignOrderId').val(orderId);
	        
	        // Load delivery boys for dropdown
	        loadDeliveryBoysForDropdown();
	        
	        // Show modal
	        $('#assignDeliveryBoyModal').modal('show');
	    });

	    // 9️⃣ Confirm Assign Delivery Boy Button Handler
	    $(document).on('click', '#assignDeliveryBoyBtn', function() {
	        let orderId = $('#assignOrderId').val();
	        let userId = $('#deliveryBoySelect').val();

	        // Comprehensive validation
	        if (!orderId || orderId.trim() === '') {
	            alert('Order ID is missing. Please select an order first.');
	            return;
	        }
	        
	        if (!userId) {
	            alert('Please select a delivery boy');
	            return;
	        }

	        // Prepare assignment data
	        let assignmentData = {
	            orderId: parseInt(orderId),
	            deliveryBoyId: parseInt(userId)
	        };

	        console.log('Assignment Request:', assignmentData);

	        // AJAX request to assign delivery boy
	        $.ajax({
	            url: 'assign-delivery-boy',
	            method: 'POST',
	            contentType: 'application/json',
	            data: JSON.stringify(assignmentData),
	            dataType: 'json',
	            success: function(response) {
	                console.log('Assignment Response:', response);

	                if (response.success) {
	                    // Success handling
	                    alert(response.message || 'Delivery boy assigned successfully');
	                    $('#assignDeliveryBoyModal').modal('hide');
	                    
	                    // Reload tables
	                    loadPendingOrders(pendingOrdersTable);
	                    loadAssignedOrders(assignedOrdersTable);
	                } else {
	                    // Error handling
	                    alert(response.message || 'Assignment failed');
	                }
	            },
	            error: function(xhr, status, error) {
	                // Comprehensive error handling
	                console.error('Assignment Error:', {
	                    status: xhr.status,
	                    responseText: xhr.responseText,
	                    error: error
	                });
	                
	                try {
	                    let errorResponse = JSON.parse(xhr.responseText);
	                    alert(errorResponse.message || 'Failed to assign delivery boy');
	                } catch (e) {
	                    alert('Unexpected error occurred. Please check console.');
	                }
	            }
	        });
	    });

	    // 🔟 Tab Event Handlers
	    $('#pending-tab').on('shown.bs.tab', function() {
	        loadPendingOrders(pendingOrdersTable);
	    });

	    $('#assigned-tab').on('shown.bs.tab', function() {
	        loadAssignedOrders(assignedOrdersTable);
	    });

	    $('#delivery-boys-tab').on('shown.bs.tab', function() {
	        loadDeliveryBoys(deliveryBoysTable);
	    });

	    // Initial load for the active tab
	    if ($('#pending-tab').hasClass('active')) {
	        loadPendingOrders(pendingOrdersTable);
	    }
	});
</script>
</body>
</html>