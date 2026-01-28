<%@ page import="java.util.List" %>
<%@ page import="com.food.model.AddToCart" %> 
<%@ page import="com.food.model.Cart" %> 
<%
List<Cart> cart = (List<Cart>) session.getAttribute("dbCart");


double totalPrice = 0;
%>

<% if(request.getAttribute("errorMessage") != null) { %>
<div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative my-4">
    Error: <%= request.getAttribute("errorMessage") %>
</div>
<% } %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- Bootstrap Icons CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>

<body class="bg-gray-50 min-h-screen">
    <%@ include file="header.jsp" %>

    <div class="container mx-auto px-4 py-8">
        <h2 class="text-3xl font-semibold mb-6">Checkout</h2>

        <% if(cart == null || cart.isEmpty()) { %>
            <div class="bg-yellow-100 border border-yellow-400 text-yellow-700 px-4 py-3 rounded mb-4">
                Your cart is empty
            </div>
            <a href="menupage" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">Browse Menu</a>
        <% } else { %>

        <!-- Cart Table -->
        <div class="overflow-x-auto">
            <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                <thead class="bg-gray-100">
                    <tr>
                        <th class="py-3 px-6 text-left">Item</th>
                        <th class="py-3 px-6 text-left">Quantity</th>
                        <th class="py-3 px-6 text-left">Price</th>
                        <th class="py-3 px-6 text-left">Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Cart item : cart) {
                        double subTotal = item.getPrice() * item.getQuantity();
                        totalPrice += subTotal;
                    %>
                    <tr class="border-t border-gray-200">
                        <td class="py-3 px-6"><%= item.getMenuName() %></td>
                        <td class="py-3 px-6"><%= item.getQuantity() %></td>
                        <td class="py-3 px-6">
                            <i class="bi bi-currency-rupee"></i><%= item.getPrice() %>
                        </td>
                        <td class="py-3 px-6">
                            <i class="bi bi-currency-rupee"></i><%= String.format("%.2f", subTotal) %>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
                <tfoot class="bg-gray-100">
                    <tr>
                        <td colspan="3" class="py-3 px-6 text-right font-bold">Total:</td>
                        <td class="py-3 px-6 font-bold">
                            <i class="bi bi-currency-rupee"></i><%= String.format("%.2f", totalPrice) %>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <!-- Delivery & Payment Form -->
        <form action="OrderConformationpage" method="post" class="mt-8 bg-white p-6 rounded-lg shadow-md">
            <div class="mb-4">
                <label for="address" class="block mb-2 font-medium">Delivery Address</label>
                <textarea name="address" id="address" class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400" required></textarea>
            </div>

            <div class="mb-4">
                <label for="paymentMethod" class="block mb-2 font-medium">Payment Method</label>
                <select name="paymentMethod" id="paymentMethod" class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400" required>
                    <option value="CREDIT_CARD">Credit Card</option>
                    <option value="DEBIT_CARD">Debit Card</option>
                    <option value="UPI">UPI</option>
                    <option value="COD">Cash on Delivery (COD)</option>
                </select>
            </div>

            <div class="flex space-x-4">
                <input type="submit" value="Confirm Order" class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 cursor-pointer">
                <a href="<%= request.getContextPath() %>/addtocart" class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">Back to Cart</a>
            </div>
        </form>
        <% } %>
    </div>
	<%@ include file="footer.jsp"%>
</body>
</html>
