<%@ page import="java.util.List"%>
<%@ page import="com.food.model.Restaurants"%>
<%
    // Check if restaurant list is in session
    if(session.getAttribute("restaurantList") == null) {
        // Redirect to the servlet to load the data
        response.sendRedirect(request.getContextPath() + "/restaurants");
        return; // Stop processing this page
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Restaurants</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-gray-50">
	<%@ include file="header.jsp"%>

	<!-- Main Content -->
	<main class="pt-24 pb-12 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">

		<!-- Scrollable Image Section -->
		<section class="mb-12">
			<h2 class="text-3xl font-bold text-gray-800 mb-6">What's on your mind?</h2>
			<div class="flex space-x-4 overflow-x-auto pb-4">
				<% 
                    // Array of food names and image filenames
                    String[][] foodItems = {
                        {"Burger", "burger.jpg"}, {"Pizza", "pizza.jpg"}, {"Pasta", "pasta.jpg"}, {"Sushi", "shushi.jpg"},
                        {"Noodles", "noodles.jpg"}, {"Fries", "fries.jpg"}, {"Sandwich", "sandwitch.jpg"}, {"Salad", "salad.jpg"},
                        {"Biryani", "biryani.jpg"}, {"Mutton Biryani", "muttonbiryani.jpg"}, {"KFC", "kfc.jpg"}, {"Kebab", "kebab.jpg"},
                        {"Cake", "cake.jpg"}, {"Ice Cream", "icecream.jpg"}, {"Donuts", "donuts.jpg"}, {"Coffee", "coffee.jpg"},
                        {"Juice", "juice.jpg"}, {"Smoothie", "smoothie.jpg"}, {"Wrap", "wrap.jpg"}, {"Shawarma", "shawarma.jpg"}
                    };

                    for(int i = 0; i < foodItems.length; i++) { 
                %>
				<div
					class="flex flex-col items-center w-48 flex-shrink-0 cursor-pointer">
					<img
						src="<%= request.getContextPath() %>/images/<%= foodItems[i][1] %>"
						class="h-40 w-40 rounded-lg object-cover shadow-md transition-transform duration-300 hover:scale-110"
						alt="<%= foodItems[i][0] %>">
					<p class="text-black font-semibold mt-2 text-center w-full"><%= foodItems[i][0] %></p>
				</div>
				<% } %>
			</div>
		</section>

		<!-- Filters -->
		<div class="flex space-x-4 mb-8 overflow-x-auto pb-4">
			<button class="px-4 py-2 bg-white border rounded-full hover:border-orange-500 hover:text-orange-500 transition-colors duration-200 flex-shrink-0">Filter</button>
			<button class="px-4 py-2 bg-white border rounded-full hover:border-orange-500 hover:text-orange-500 transition-colors duration-200 flex-shrink-0">Sort By</button>
			<button class="px-4 py-2 bg-white border rounded-full hover:border-orange-500 hover:text-orange-500 transition-colors duration-200 flex-shrink-0">
				<i class="bi bi-star-fill text-green-500 mr-2"></i>4.0+
			</button>
			<button class="px-4 py-2 bg-white border rounded-full hover:border-orange-500 hover:text-orange-500 transition-colors duration-200 flex-shrink-0">Pure Veg</button>
			<button class="px-4 py-2 bg-white border rounded-full hover:border-orange-500 hover:text-orange-500 transition-colors duration-200 flex-shrink-0">Offers</button>
		</div>

		<!-- Restaurants Grid -->
		<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
			<% 
                List<Restaurants> restaurants = (List<Restaurants>)session.getAttribute("restaurantList");
                if(restaurants != null && !restaurants.isEmpty()) {
                    for(Restaurants restro : restaurants) { 
            %>
			<!-- Restaurant Card -->
			<div
	class="bg-white rounded-xl shadow-md transition-transform duration-300 hover:shadow-lg hover:scale-105 hover:border-orange-400 border border-transparent restaurantCard"
	data-name="<%= restro.getName().toLowerCase() %>">

				<form action="menus" method="get">
				<input type="hidden" name="restaurantId" value="<%= restro.getRestaurantId() %>">
					<div class="p-4">
						<div class="aspect-square rounded-lg overflow-hidden">
							<img src="<%= restro.getImagePath() %>"
								alt="<%= restro.getName() %>" class="w-full h-full object-cover">
						</div>
						<h3 class="text-xl font-bold text-gray-800 mt-4"><%= restro.getName() %></h3>
						<div class="flex items-center mt-2">
							<i class="bi bi-star-fill text-green-500 mr-1"></i> <span
								class="text-gray-600"><%= restro.getRating() %></span>
						</div>
						<div class="text-gray-500 mt-2">
							<p class="truncate">
								<i class="bi bi-egg-fried text-yellow-500 mr-2"></i><%= restro.getCusineType() %></p>
							<p class="truncate">
								<i class="bi bi-geo-alt text-red-500 mr-2"></i><%= restro.getAddress() %></p>
						</div>
						<button type="submit"
							class="w-full mt-4 bg-orange-500 text-white py-2 rounded-lg hover:bg-orange-600 transition-colors duration-200">
							View Menu</button>
					</div>
				</form>
			</div>
			<%   
                    }
                } else { 
            %>
			<p class="text-gray-500 text-center col-span-full">
				No restaurants available. <a
					href="<%= request.getContextPath() %>/restaurants"
					class="text-orange-500 underline">Refresh data</a>
			</p>
			<% } %>
		</div>
	</main>

	<%@ include file="footer.jsp"%>

</body>
</html>
