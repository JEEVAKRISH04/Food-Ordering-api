
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Track Order</title>
    
    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
    
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>

   <body class="bg-gray-100 min-h-screen">

    <%@ include file="header.jsp" %>

    <!-- Wrapper div for entire content -->
    <div class="pt-20">

    <!-- Main Container -->
    <div class="container mx-auto my-10 p-5">

        <!-- Heading -->
        <div class="text-center mb-8">
            <h1 class="text-4xl font-bold mb-2">Track Your Order</h1>
            <p class="text-lg text-gray-700">Stay updated with your order status!</p>
        </div>

        <!-- Order Status Card -->
        <div class="bg-white shadow-lg rounded-lg p-8 max-w-xl mx-auto">
            <h2 class="text-2xl font-semibold mb-6">Order Details</h2>

            <!-- Display Order ID -->
            <p><strong>Order ID:</strong> <%= request.getParameter("orderId") %></p>

            <!-- Order Status -->
            <div class="mt-6">
                <h3 class="text-xl font-semibold mb-4">Order Status:</h3>

                <!-- Status List -->
                <ul class="space-y-4">
                    <li class="flex justify-between items-center bg-gray-100 p-3 rounded-lg">
                        <span>Order Placed</span>
                        <i class="bi bi-check-circle-fill text-green-500 text-xl"></i>
                    </li>
                    <li class="flex justify-between items-center bg-gray-100 p-3 rounded-lg">
                        <span>Preparing</span>
                        <i class="bi bi-check-circle-fill text-green-500 text-xl"></i>
                    </li>
                    <li class="flex justify-between items-center bg-gray-100 p-3 rounded-lg">
                        <span>Out for Delivery</span>
                        <span class="text-yellow-500 font-semibold">In Progress</span>
                    </li>
                    <li class="flex justify-between items-center bg-gray-100 p-3 rounded-lg">
                        <span>Delivered</span>
                        <span class="text-gray-400 font-semibold">Pending</span>
                    </li>
                </ul>
            </div>
        </div>

        <!-- Back Button -->
        <div class="text-center mt-8 flex flex-col md:flex-row justify-center gap-4">
            <a href="<%= request.getContextPath() %>/restaurants" class="btn btn-primary w-full md:w-auto">Back to Home</a>
        </div>
    </div>

    <!-- Footer Include -->
    
        <%@ include file="footer.jsp" %>
   

</body>
</html>
