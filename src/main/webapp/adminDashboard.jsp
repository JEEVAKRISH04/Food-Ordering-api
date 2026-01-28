
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin Dashboard</title>
<%
    if (session == null || session.getAttribute("userId") == null || !"ADMIN".equals(session.getAttribute("role"))) {
        response.sendRedirect("Login.jsp?error=unauthorized");
        return;
    }
%>
<!-- Bootstrap CSS -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Tailwind CSS -->
<script src="https://cdn.tailwindcss.com"></script>

<!-- Include Select2 CSS and JS -->
<link
	href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<!-- Bootstrap JS Bundle with Popper -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<!-- Font Awesome -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
	rel="stylesheet">
	<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">



   

    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
	

<script>
	tailwind.config = {
		theme : {
			extend : {
				colors : {
					primary : '#f97316', // Orange-500
					secondary : '#ea580c', // Orange-600
					dark : '#1e293b', // Slate-800
				}
			}
		}
	}
</script>

<style>
.sidebar {
	transition: all 0.3s ease;
}

.content {
	transition: all 0.3s ease;
}

.active-tab {
	background-color: #f97316;
	color: white;
}
</style>
</head>
 <body class="bg-gray-100 min-h-screen">

    <%@ include file="header.jsp" %>
      <div class="pt-20">
	<div class="flex h-screen overflow-hidden">
		<!-- Sidebar -->
		<div id="sidebar"
			class="sidebar bg-dark text-white w-64 flex-shrink-0 h-full">
			<div class="p-4 border-b border-gray-700">
				<h1 class="text-2xl font-bold text-primary flex items-center">
					<i class="fas fa-utensils mr-2"></i> Food App
				</h1>
				<p class="text-xs text-gray-400">Admin Dashboard</p>
			</div>

			<nav class="mt-4">
				<ul>
					<li class="mb-1">
						<button onclick="showTab('dashboard')"
							class="flex items-center p-3 w-full text-left hover:bg-gray-700 rounded transition-colors"
							id="dashboard-tab">
							<i class="fas fa-chart-line mr-3 w-5 text-center"></i> Dashboard
						</button>
					</li>
					<li class="mb-1">
						<button
							class="flex items-center p-3 w-full text-left hover:bg-gray-700 rounded transition-colors"
							id="restaurants-tab" data-bs-toggle="modal"
							data-bs-target="#addRestaurantModal">
							<i class="fas fa-store mr-3 w-5 text-center"></i> Restaurants
						</button>

					</li>
					<li class="mb-1">
						<button type="button"
							class="flex items-center p-3 w-full text-left hover:bg-gray-700 rounded transition-colors"
							id="menu-tab" data-bs-toggle="modal"
							data-bs-target="#addMenuModal">
							<i class="fas fa-clipboard-list mr-3 w-5 text-center"></i> Menu
							Items
						</button>

					</li>
					
					<li class="mb-1">
						<button onclick="window.location.href='ordersadminpage';"
							class="flex items-center p-3 w-full text-left hover:bg-gray-700 rounded transition-colors"
							id="orders-tab">
							<i class="fas fa-shopping-cart mr-3 w-5 text-center"></i> Orders
						</button>
					</li>
					<li class="mb-1">
						<button onclick="window.location.href='deliveryagentpage';"
							class="flex items-center p-3 w-full text-left hover:bg-gray-700 rounded transition-colors"
							id="orders-tab">
							<i class="fas fa-shopping-cart mr-3 w-5 text-center"></i> Delivery Management
						</button>
					</li>


					</li>
				</ul>
			</nav>

			<div class="absolute bottom-0 w-64 border-t border-gray-700 p-4">
				<div class="flex items-center">
					<div
						class="w-10 h-10 rounded-full bg-primary flex items-center justify-center text-white font-bold">
						A</div>
					<div class="ml-3">
						<p class="text-sm font-medium">Admin <%= session.getAttribute("userName") %></p>
						<p class="text-xs text-gray-400"><%= session.getAttribute("userName") %>@foodapp.com</p>
					</div>
				</div>
				<a href="logout"
					class="mt-3 flex items-center text-gray-400 hover:text-white">
					<i class="fas fa-sign-out-alt mr-2"></i> Logout
				</a>
			</div>
		</div>
		<!-- Add Restaurant Modal -->
		<div class="modal fade" id="addRestaurantModal" tabindex="-1"
			aria-labelledby="addRestaurantModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="addRestaurantForm" method="POST"
						enctype="multipart/form-data" action="restaurant">


						<!-- Replace with actual servlet URL -->
						<div class="modal-header">
							<h5 class="modal-title" id="addRestaurantModalLabel">Add
								Restaurant</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>

						<div class="modal-body">
							<div class="mb-3">
								<label for="name" class="form-label">Restaurant Name</label> <input
									type="text" class="form-control" id="name" name="name" required />
							</div>

							<div class="mb-3">
								<label for="email" class="form-label">Email</label> <input
									type="email" class="form-control" id="email" name="email"
									required />
							</div>

							<div class="mb-3">
								<label for="phoneno" class="form-label">Phone Number</label> <input
									type="text" class="form-control" id="phoneno" name="phoneno"
									required />
							</div>

							<div class="mb-3">
								<label for="address" class="form-label">Address</label>
								<textarea class="form-control" id="address" name="address"
									required></textarea>
							</div>

							<div class="mb-3">
								<label for="rating" class="form-label">Rating (0.0 -
									9.9)</label> <input type="number" step="0.1" max="9.9"
									class="form-control" id="rating" name="rating" value="0.0" />
							</div>

							<div class="mb-3">
								<label for="imagepath" class="form-label">Upload Image</label> <input
									type="file" class="form-control" id="imagepath"
									name="imagepath" accept="image/*" required />
							</div>


							<div class="mb-3">
								<label for="cusinetype" class="form-label">Cuisine Type</label>
								<select class="form-select" id="cusinetype" name="cusinetype"
									required>
									<option value="">Select Cuisine Type</option>
									<option value="Indian">Indian</option>
									<option value="Chinese">Chinese</option>
									<option value="Italian">Italian</option>
									<option value="Mexican">Mexican</option>
									<option value="Thai">Thai</option>
									<option value="American">American</option>
									<option value="Other">Other</option>
								</select>
							</div>

						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary">Add
								Restaurant</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="addMenuModal" tabindex="-1"
			aria-labelledby="addMenuModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="addMenuForm" enctype="multipart/form-data">
						<div class="modal-header">
							<h5 class="modal-title" id="addMenuModalLabel">Add New Menu
								Item</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>

						<div class="modal-body">

							<!-- Restaurant ID -->
							<!-- Dropdown -->
							<div class="mb-3">
								<label for="restaurantid" class="form-label">Restaurant
									Name</label> <select id="restaurantid" name="restaurantid"
									style="width: 100%;" required></select>
							</div>





							<!-- Name -->
							<div class="mb-3">
								<label for="name" class="form-label">Item Name</label> <input
									type="text" class="form-control" id="name" name="name" required>
							</div>

							<!-- Price -->
							<div class="mb-3">
								<label for="price" class="form-label">Price (₹)</label> <input
									type="number" step="0.01" class="form-control" id="price"
									name="price" required>
							</div>

							    <!-- Image Upload -->
    <div class="mb-3">
        <label for="imagefile" class="form-label">Upload Image</label>
        <input type="file" class="form-control" id="imagefile" name="imagefile" accept="image/*">
    </div>

							<!-- Category (Dropdown) -->
							<div class="mb-3">
								<label for="category" class="form-label">Category</label> <select
									class="form-select" id="category" name="category" required>
									<option value="">Select Category</option>
									<option value="Starter">Starter</option>
									<option value="Main Course">Main Course</option>
									<option value="Dessert">Dessert</option>
									<option value="Beverage">Beverage</option>
									<option value="Other">Other</option>
								</select>
							</div>

							<!-- Description -->
							<div class="mb-3">
								<label for="description" class="form-label">Description</label>
								<textarea class="form-control" id="description"
									name="description"></textarea>
							</div>

							<!-- Availability -->
							<div class="mb-3">
								<label class="form-label">Availability</label> <select
									class="form-select" name="isavailable" required>
									<option value="true">Available</option>
									<option value="false">Not Available</option>
								</select>
							</div>

							<!-- Ratings -->
							<div class="mb-3">
								<label for="ratings" class="form-label">Ratings (0-5)</label> <input
									type="number" step="0.1" min="0" max="5" class="form-control"
									id="ratings" name="ratings">
							</div>

						</div>

						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-primary">Add Menu
								Item</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- Main Content -->
		<div class="flex flex-col flex-1 overflow-hidden">
			<!-- Top Navigation -->
			<header class="bg-white shadow-sm">
				<div class="flex justify-between items-center px-6 py-4">
					<div class="flex items-center">
						<button id="sidebarToggle"
							class="text-gray-500 focus:outline-none lg:hidden">
							<i class="fas fa-bars"></i>
						</button>
						<h2 class="text-xl font-semibold text-gray-800 ml-4"
							id="page-title">Dashboard</h2>
					</div>
					<div class="flex items-center">
						<div class="relative">
							<button
								class="flex items-center text-gray-500 focus:outline-none">
								<i class="fas fa-bell text-lg"></i> <span
									class="absolute top-0 right-0 h-2 w-2 bg-red-500 rounded-full"></span>
							</button>
						</div>
					</div>
				</div>
			</header>

			<!-- Page Content -->
			<main class="flex-1 overflow-y-auto p-4 bg-gray-100">
				<!-- Dashboard Tab -->
				<div id="dashboard-content" class="tab-content">
					<div
						class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
						<div class="bg-white rounded-lg shadow p-4">
							<div class="flex items-center">
								<div class="p-3 rounded-full bg-orange-100 text-primary">
									<i class="fas fa-store text-xl"></i>
								</div>
								<div class="ml-4">
									<p class="text-sm text-gray-500">Total Restaurants</p>
									<h3 class="text-2xl font-semibold total-restaurants">0</h3>
									<!-- Dynamic -->
								</div>
							</div>
						</div>
						<div class="bg-white rounded-lg shadow p-4">
							<div class="flex items-center">
								<div class="p-3 rounded-full bg-blue-100 text-blue-500">
									<i class="fas fa-clipboard-list text-xl"></i>
								</div>
								<div class="ml-4">
									<p class="text-sm text-gray-500">Menu Items</p>
									<h3 class="text-2xl font-semibold total-menus">0</h3>
									<!-- Dynamic -->
								</div>
							</div>
						</div>
						<div class="bg-white rounded-lg shadow p-4">
							<div class="flex items-center">
								<div class="p-3 rounded-full bg-green-100 text-green-500">
									<i class="fas fa-users text-xl"></i>
								</div>
								<div class="ml-4">
									<p class="text-sm text-gray-500">Total Users</p>
									<h3 class="text-2xl font-semibold total-users">0</h3>
									<!-- Dynamic -->
								</div>
							</div>
						</div>
						<div class="bg-white rounded-lg shadow p-4">
							<div class="flex items-center">
								<div class="p-3 rounded-full bg-purple-100 text-purple-500">
									<i class="fas fa-shopping-cart text-xl"></i>
								</div>
								<div class="ml-4">
									<p class="text-sm text-gray-500">Total Orders</p>
									<h3 class="text-2xl font-semibold total-orders">0</h3>
									<!-- Dynamic -->
								</div>
							</div>
						</div>
					</div>
					<div class="w-[100%] mx-auto bg-white p-4 rounded-md shadow-md">
            <h2 class="text-2xl font-semibold mb-4 text-center">User Management Table</h2>

            <table id="userTable" class="display stripe hover w-full text-center">
                <thead class="bg-gray-200">
                    <tr>
                        <th class="py-2 px-2">User ID</th>
                        <th class="py-2 px-2">Username</th>
                        <th class="py-2 px-2">Address</th>
                        <th class="py-2 px-2">Role</th>
                        <th class="py-2 px-2">Action</th>
                    </tr>
                </thead>
            </table>
        </div>
			</main>
			
		</div>
	</div>
 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
 <script>
 $(document).ready(function() {
	    console.log("✅ jQuery & DataTables loaded!");

	    var table = $('#userTable').DataTable({
	        "ajax": {
	            "url": 'usersData',
	            "dataSrc": "",
	            "error": function(xhr, error, thrown) {
	                console.error("❌ AJAX Error:", xhr.responseText);
	                alert("Error loading data from server.");
	            },
	            "complete": function(xhr, status) {
	                console.log("📜 Raw Response: ", xhr.responseText);
	            }
	        },
	        "columns": [
	            { "data": "userId" },
	            { "data": "name" },
	            { "data": "address" },
	            { "data": "role" },
	            { 
	                "data": null,
	                "defaultContent": '<button class="delete-btn">Delete</button>'
	            }
	        ]
	    });

	    // ✅ Delete user and refresh page
	    $('#userTable tbody').on('click', '.delete-btn', function() {
	        var data = table.row($(this).parents('tr')).data();
	        var userId = data.userId;

	        if (confirm('Are you sure you want to delete user with ID ' + userId + '?')) {
	            $.ajax({
	                url: 'usersData',
	                type: 'POST',
	                data: { userId: userId },
	                success: function(response) {
	                    alert('✅ User deleted successfully!');
	                    location.reload(); // 🔄 Auto-refresh the page after deletion
	                },
	                error: function(xhr, status, error) {
	                    console.error("❌ Error deleting user:", error);
	                    alert('Failed to delete user');
	                }
	            });
	        }
	    });
	});


		$(document).ready(function() {
			$.ajax({
				url : 'dashboard-data',
				method : 'GET',
				success : function(data) {
					console.log(data); // Check data

					// Update counts dynamically
					$('.total-restaurants').text(data.totalRestaurants);
					$('.total-menus').text(data.totalMenus);
					$('.total-users').text(data.totalUsers);
					$('.total-orders').text(data.totalOrders);
				},
				error : function(err) {
					console.log("Error loading dashboard data", err);
				}
			});
		});

		$('#addRestaurantForm').submit(function(e) {
			e.preventDefault();

			var form = $('#addRestaurantForm')[0]; // Get the form DOM element
			var formData = new FormData(form); // Create FormData object from form

			$.ajax({
				type : "POST",
				url : "restaurants",
				data : formData,
				processData : false, // Important for file upload
				contentType : false, // Important for file upload
				dataType : "json",
				success : function(response) {
					if (response.status === "success") {
						alert(response.message);
						location.reload();
					} else {
						alert("Failed to add restaurant");
					}
				},
				error : function() {
					alert("Error occurred while adding restaurant");
				}
			});
		});

		$(document).ready(
				function() {

					// Fetch restaurant data
					$.ajax({
						url : 'fetchRestaurants',
						method : 'GET',
						dataType : 'json',
						success : function(data) {
							console.log("Fetched Data:", data); // Check if data is coming

							if (data.length > 0) {
								// Empty dropdown first
								$('#restaurantid').empty();

								// Add default placeholder
								$('#restaurantid').append('<option></option>');

								// Add restaurants
								$.each(data, function(index, restaurant) {
									$('#restaurantid').append(
											new Option(restaurant.name,
													restaurant.id));
								});

								// Initialize Select2
								$('#restaurantid').select2({
									placeholder : 'Select a restaurant',
									allowClear : true,
									width : '100%' // Important for visibility
								});
							} else {
								console.log("No restaurant data found.");
							}
						},
						error : function(xhr, status, error) {
							console.log("Error fetching restaurants:", error);
						}
					});

					// On selecting restaurant, set name
					$('#restaurantid').on('select2:select', function(e) {
						var selectedText = e.params.data.text;
						$('#restaurantname').val(selectedText);
					});

				});
		
		
		
		
		$(document).ready(function() {
		    $('#addMenuForm').on('submit', function(event) {
		        event.preventDefault(); // prevent default form submit

		        var form = $('#addMenuForm')[0];
		        var formData = new FormData(form); // create FormData object to handle file

		        $.ajax({
		            url: 'menus',
		            type: 'POST',
		            enctype: 'multipart/form-data',
		            data: formData,
		            processData: false, // prevent jQuery from converting the data
		            contentType: false, // prevent jQuery from setting contentType
		            success: function(response) {
		                alert('Menu item added successfully!');
		                location.reload();
		            },
		            error: function(xhr, status, error) {
		                alert('Error: ' + error);
		            }
		        });
		    });
		});


	</script>
</body>
</html>