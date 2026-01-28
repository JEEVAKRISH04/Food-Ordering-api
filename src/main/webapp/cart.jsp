<%@ page import="java.util.List,com.food.model.AddToCart,com.food.model.Cart" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Shopping Cart</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-gray-100">

<%@ include file="header.jsp" %>

<div class="container mx-auto pt-20 p-6">

    <!-- Cart Heading -->
    <h2 class="text-4xl font-extrabold text-center text-gray-800 mb-10">🛒 Your Shopping Cart</h2>

    <!-- Back Buttons -->
    <div class="flex flex-wrap items-center mb-8 gap-4">

        <!-- Back to Restaurants -->
        <a href="<%= request.getContextPath() %>/restaurants" 
           class="flex items-center text-orange-600 hover:text-orange-800 font-medium transition">
            <i class="bi bi-arrow-left text-xl mr-2"></i>
            <span>Back to Restaurants</span>
        </a>

        <!-- Clear Cart Button -->
        <%
            // Get userId from session
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) userId = 1; // Default to 1 if not found - adjust as needed
        
            // Get DB cart
            List<Cart> dbCart = (List<Cart>) request.getAttribute("dbCart");
            boolean hasItems = dbCart != null && !dbCart.isEmpty();
            
            if (hasItems) {
        %>
        <form action="removeFromCart" method="post" class="ml-auto">
            <input type="hidden" name="action" value="clearAll">
            <input type="hidden" name="userId" value="<%= userId %>">
            <button type="submit"
                    class="flex items-center gap-2 bg-red-500 hover:bg-red-600 text-white px-5 py-2 rounded-lg shadow-md transition duration-300">
                <i class="bi bi-trash"></i>
                <span>Clear Cart</span>
            </button>
        </form>
        <% } %>
    </div>

    <%
        // Get total from request
        double total = 0;
        if (request.getAttribute("total") != null) {
            total = (double) request.getAttribute("total");
        }
        
        if (hasItems) {
    %>
    <table class="w-full bg-white shadow-md rounded-lg overflow-hidden">
        <thead>
            <tr class="bg-gray-200">
                <th class="p-3 text-left">Item</th>
                <th class="p-3 text-center">Quantity</th>
                <th class="p-3 text-right">Price</th>
                <th class="p-3 text-right">Total</th>
                <th class="p-3 text-right">Action</th>
            </tr>
        </thead>
        <tbody>
        <% for (Cart item : dbCart) { 
             double itemTotal = item.getPrice() * item.getQuantity();
        %>
            <tr class="border-b">
                <td class="p-3">
                    <div class="flex items-center space-x-3">
                        <img src="<%= item.getImagePath() %>" alt="" class="w-16 h-16 object-cover rounded">
                        <span><%= item.getMenuName() %></span>
                    </div>
                </td>
                <td class="p-3 text-center">
                    <button onclick="updateQuantity('<%= item.getMenuId() %>', 'decrease', <%= userId %>)" class="bg-gray-300 px-2">-</button>
                    <span class="mx-2" id="quantity-<%= item.getMenuId() %>"><%= item.getQuantity() %></span>
                    <button onclick="updateQuantity('<%= item.getMenuId() %>', 'increase', <%= userId %>)" class="bg-gray-300 px-2">+</button>
                </td>
                <td class="p-3 text-right">
                    <i class="bi bi-currency-rupee"></i>
                    <span id="price-<%= item.getMenuId() %>"><%= item.getPrice() %></span>
                </td>
                <td class="p-3 text-right">
                    <i class="bi bi-currency-rupee"></i>
                    <span class="itemTotal" id="totalPrice-<%= item.getMenuId() %>"><%= itemTotal %></span>
                </td>
                <td class="p-3 text-right">
   <form action="removeFromCart" method="post">
    <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
    <input type="hidden" name="cartId" value="<%= item.getCartId() %>">
    <input type="hidden" name="userId" value="<%= userId %>">
    <button type="submit" class="text-red-500 hover:text-red-700">
        <i class="bi bi-trash"></i> Remove
    </button>
</form>
</td>
            </tr>
        <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3" class="p-3 text-right font-bold">Total:</td>
                <td class="p-3 text-right font-bold" colspan="2">
                    <i class="bi bi-currency-rupee"></i>
                    <span id="overallTotal"><%= total %></span>
                </td>
            </tr>
        </tfoot>
    </table>
    <% } else { %>
        <div class="text-center p-8 bg-white rounded-lg shadow">
            <div class="text-4xl mb-4">🛒</div>
            <p class="text-xl text-gray-600 mb-6">Your cart is empty.</p>
            <a href="<%= request.getContextPath() %>/restaurants" 
               class="bg-orange-500 hover:bg-orange-600 text-white px-6 py-3 rounded-lg shadow-md transition inline-block">
                Browse Restaurants
            </a>
        </div>
    <% } %>

    <!-- Back to Menu -->
    <%
        Integer restaurantId = (Integer) session.getAttribute("restaurantId");
        if (restaurantId != null) {
    %>
    <div class="mt-8 sm:mx-6 lg:mx-15">
        <form action="<%= request.getContextPath() %>/menus" method="get">
            <input type="hidden" name="restaurantId" value="<%= restaurantId %>">
            <button type="submit"
                    class="flex items-center bg-blue-500 hover:bg-blue-600 text-white px-5 py-2 rounded-lg shadow-md transition">
                <i class="bi bi-arrow-left mr-2"></i>
                Back to Menu
            </button>
        </form>
    </div>
    <% } %>	

</div>
<!-- Proceed to Checkout Button -->
<% if (hasItems) { %>
<div class="mt-8 flex justify-end">
    <form action="<%= request.getContextPath() %>/checkoutpage" method="post">
    <input type="hidden" name="userId" value="<%= userId %>">
    <button type="submit" class="flex items-center bg-green-500 hover:bg-green-600 text-white px-6 py-3 rounded-lg shadow-md transition">
        <i class="bi bi-bag-check mr-2"></i>
        Proceed to Checkout
    </button>
</form>

</div>
<% } %>
  <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- SCRIPT -->
<!-- SCRIPT -->
<script>
function updateQuantity(menuId, action, userId) {
    $.ajax({
        url: "updateCart",
        type: "POST",
        data: { 
            menuId: menuId, 
            action: action, 
            userId: userId 
        },
        dataType: "json",
        success: function(response) {
            if (response.success) {
                // Update quantity display
                $("#quantity-" + menuId).text(response.quantity);

                // Update item total price
                var itemPrice = parseFloat($("#price-" + menuId).text());
                var itemTotal = response.quantity * itemPrice;
                $("#totalPrice-" + menuId).text(itemTotal.toFixed(2));

                // Update overall total directly from response
                $("#overallTotal").text(response.total.toFixed(2));

                // If quantity is zero, remove the row
                if(response.quantity === 0) {
                    // Find the row containing this menu item and remove it
                    $("#quantity-" + menuId).closest('tr').remove();
                    
                    // If cart becomes empty, show empty cart message
                    if($('tbody tr').length === 0) {
                        $('table').replaceWith(
                            '<div class="text-center p-8 bg-white rounded-lg shadow">' +
                            '<div class="text-4xl mb-4">🛒</div>' +
                            '<p class="text-xl text-gray-600 mb-6">Your cart is empty.</p>' +
                            '<a href="<%= request.getContextPath() %>/restaurants" ' +
                            'class="bg-orange-500 hover:bg-orange-600 text-white px-6 py-3 rounded-lg shadow-md transition inline-block">' +
                            'Browse Restaurants</a></div>'
                        );
                    }
                }
            } else {
                console.error("Error updating cart:", response.error);
                alert("Failed to update cart: " + response.error);
            }
        },
        error: function(xhr, status, error) {
            console.error("AJAX Error:", error);
            alert("Failed to update cart. Please try again.");
        }
    });
}
</script>

<%@ include file="footer.jsp" %>

</body>
</html>