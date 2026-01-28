<%@ page import="java.util.List" %>
<%@ page import="com.food.model.Menu" %>
<%@ page import="com.food.model.Restaurants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="bg-gray-50 m-0 p-0">

<%@ include file="header.jsp" %>

<div class="container mx-auto pt-20 p-4">

    <%
    Integer restaurantId = (Integer) request.getAttribute("restaurantId");
    if (restaurantId == null) {
        // Try getting from parameters
        String restaurantIdParam = request.getParameter("restaurantId");
        if (restaurantIdParam != null) {
            try {
                restaurantId = Integer.parseInt(restaurantIdParam);
            } catch (NumberFormatException e) {
                // Handle error
            }
        }
    }
    List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
    // Get userId from session
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) userId = 1; // Default to 1 if not found - adjust as needed
    %>

    <!-- Back Button -->
    <div class="flex items-center mb-4">
        <a href="<%= request.getContextPath() %>/restaurants" class="text-orange-500 hover:text-orange-700 mr-4">
            <i class="bi bi-arrow-left text-xl"></i>
        </a>
        <span class="text-gray-600 hover:text-gray-800">Back to Restaurants</span>

        <!-- View Cart Button -->
        <a href="addtocart?action=viewcart&userId=<%= userId %>" class="ml-auto bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-full flex items-center">
            <i class="bi bi-cart-fill mr-2"></i> View Cart
        </a>
    </div>

    <!-- Restaurant Name -->
    <h2 class="text-3xl font-bold text-gray-900 mb-6 text-center">
        <%
        List<Restaurants> restaurants = (List<Restaurants>) session.getAttribute("restaurantList");
        if (restaurants != null && restaurantId != null) {
            for (Restaurants restro : restaurants) {
                if (restro.getRestaurantId() == restaurantId) {
        %>
                    <%= restro.getName() %>
        <%
                    break;
                }
            }
        } else {
        %>
            Menu
        <%
        }
        %>
    </h2>

    <!-- Search Bar -->
    <div class="flex justify-center mb-6">
        <input type="text" id="menuSearch" placeholder="Search Menu Items..." 
               class="border border-gray-300 rounded-full px-4 py-2 w-full max-w-md focus:outline-none focus:ring-2 focus:ring-orange-500">
    </div>

    <!-- Menu Items Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <% if (menuList != null && !menuList.isEmpty()) { 
            for (Menu menu : menuList) { %>

        <!-- Single Menu Card -->
        <div class="bg-white shadow-md rounded-lg overflow-hidden hover:shadow-lg transition-shadow duration-300 searchable-menu">
            <div class="relative">
                <img src="<%= menu.getImagePath() %>" alt="<%= menu.getName() %>" class="w-full h-48 object-cover">
            </div>
            <div class="p-4">
                <h3 class="text-lg font-bold text-gray-900"><%= menu.getName() %></h3>
                <p class="text-gray-500 text-sm mb-3"><%= menu.getDescription() %></p>
                <div class="flex justify-between items-center">
                    <p class="text-gray-800 font-semibold"><i class="bi bi-currency-rupee"></i><%= menu.getPrice() %></p>
                    <button class="bg-orange-500 hover:bg-orange-600 text-white px-3 py-1 rounded-full text-sm"
                            onclick="addToCart('<%= menu.getMenuId() %>', '<%= menu.getName() %>', '<%= menu.getDescription() %>', '<%= menu.getPrice() %>', '<%= menu.getImagePath() %>', '<%= restaurantId %>', '<%= userId %>')">
                        Add to Cart
                    </button>
                </div>
                <div class="flex items-center space-x-2 mt-3">
                    <button onclick="decreaseQuantity('<%= menu.getMenuId() %>')" class="bg-gray-300 text-gray-700 px-2 py-1 rounded">
                        <i class="bi bi-dash-lg"></i>
                    </button>
                    <span id="quantity-<%= menu.getMenuId() %>" class="text-gray-800 font-semibold">1</span>
                    <button onclick="increaseQuantity('<%= menu.getMenuId() %>')" class="bg-gray-300 text-gray-700 px-2 py-1 rounded">
                        <i class="bi bi-plus-lg"></i>
                    </button>
                </div>
            </div>
        </div>

        <% } 
        } else { %>
        <div class="col-span-full text-center py-8">
            <p class="text-gray-600 text-lg mb-2">No menu items available for this restaurant.</p>
            <a href="<%= request.getContextPath() %>/restaurants" class="mt-4 bg-orange-500 text-white px-4 py-2 rounded-lg">
                Browse Restaurants
            </a>
        </div>
        <% } %>
    </div>
</div>

<!-- Scripts -->
<script>
    function increaseQuantity(menuId) {
        let quantityElement = document.getElementById("quantity-" + menuId);
        let quantity = parseInt(quantityElement.innerText);
        quantityElement.innerText = quantity + 1;
    }

    function decreaseQuantity(menuId) {
        let quantityElement = document.getElementById("quantity-" + menuId);
        let quantity = parseInt(quantityElement.innerText);
        if (quantity > 1) {
            quantityElement.innerText = quantity - 1;
        }
    }

    function addToCart(menuId, name, description, price, imagePath, restaurantId, userId) {
        const quantity = parseInt(document.getElementById('quantity-' + menuId).innerText);

        const data = {
            menuId: menuId,
            name: name,
            description: description,
            price: price,
            imagePath: imagePath,
            quantity: quantity,
            restaurantId: restaurantId,
            userId: userId
        };

        console.log("Sending data:", data);  // Check in console

        $.ajax({
            url: 'addtocart',
            method: 'POST',
            data: data,
            success: function (response) {
                alert("Item added to cart!");
            },
            error: function (xhr, status, error) {
                console.log("Error:", error);
                console.log("Response text:", xhr.responseText);
                alert("Failed to add to cart: " + xhr.responseText);
            }
        });
    }

    // Menu Search Filter
    document.getElementById('menuSearch').addEventListener('input', function () {
        const searchText = this.value.toLowerCase();
        const menuItems = document.querySelectorAll('.searchable-menu');

        menuItems.forEach(function (item) {
            const itemName = item.querySelector('h3').innerText.toLowerCase();
            if (itemName.includes(searchText)) {
                item.style.display = '';
            } else {
                item.style.display = 'none';
            }
        });
    });
</script>

<%@ include file="footer.jsp" %>

</body>
</html>