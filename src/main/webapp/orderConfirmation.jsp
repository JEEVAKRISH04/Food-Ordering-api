<%@ page import="java.util.List" %>
<%@ page import="com.food.model.OrderItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Order Confirmation</title>

<!-- Tailwind CSS CDN -->
<script src="https://cdn.tailwindcss.com"></script>

<!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>


<body class="bg-gray-100 min-h-screen">

<%@ include file="header.jsp" %>

<!-- Wrapper div for entire content -->
<div class="pt-20">

<!-- Order Confirmation Container -->
<div class="container mx-auto my-12 p-5">
<!-- Heading -->
<div class="text-center mb-8">
<h1 class="text-4xl font-bold mb-2">Order Confirmation</h1>
<p class="text-lg text-gray-600">Thank you for your purchase!</p>
</div>

<!-- Order Summary -->
<div class="bg-white shadow-lg rounded-xl p-8 max-w-3xl mx-auto">
<h2 class="text-2xl font-semibold mb-6 border-b pb-4">Order Summary</h2>

<div class="grid gap-4 mb-6">
<p><strong>Order ID:</strong> <%= request.getAttribute("orderId") %></p>
<p><strong>Total Price:</strong> <i class="bi bi-currency-rupee"></i><%= request.getAttribute("price") %></p>
<p><strong>Delivery Address:</strong> <%= request.getAttribute("deliveryAddress") %></p>
<p><strong>Payment Method:</strong> <%= request.getAttribute("paymentMethod") %></p>
</div>

<!-- Ordered Items -->
<div>
<h3 class="text-xl font-semibold mb-3">Items:</h3>
<ul class="divide-y divide-gray-300">
<%
List<OrderItem> orderItems = (List<OrderItem>)request.getAttribute("orderItems");
if(orderItems != null && !orderItems.isEmpty()) {
    for(OrderItem item : orderItems) {
%>
<li class="flex justify-between items-center py-3">
<span>Menu ID: <%= item.getMenuId() %> (x<%= item.getQuantity() %>)</span>
<span><i class="bi bi-currency-rupee"></i><%= item.getPrice() * item.getQuantity() %></span>
</li>
<%
    }
} else {
%>
<li class="py-3 text-gray-500">No items found</li>
<%
}
%>
</ul>
</div>
</div>

<!-- Action Buttons -->
<div class="flex flex-col md:flex-row justify-center gap-4 mt-10 text-center">
<a href="<%= request.getContextPath() %>/restaurants" class="btn btn-primary w-full md:w-auto">Continue Shopping</a>
<a href="trackorder?orderId=<%= request.getAttribute("orderId") %>" class="btn btn-secondary">Track Order</a>
</div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- Footer Include -->
<%@ include file="footer.jsp" %>

</body>
</html>