<!-- searchbar.jsp -->

<nav class="fixed w-full bg-orange-500 shadow-lg z-50 m-0 p-0">
	<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
		<div class="flex justify-between h-16 items-center">
			<!-- Left Section: Logo -->
			<div class="flex items-center space-x-6">
				<img src="images/52.jpg" class="transition-transform duration-300 hover:scale-110 w-13 h-12 rounded-full" alt="Logo">
			</div>

			<!-- Center: Search Bar -->
			<form id="searchForm" class="relative w-1/3">
				<div class="input-group flex items-center bg-white rounded-full shadow-md overflow-hidden">
					<input id="searchInput" class="form-control flex-1 px-2 py-2 border-none focus:ring-2 focus:ring-orange-400 outline-none rounded-l-full" type="text" placeholder="Search for food..." aria-label="Search">
					<button class="btn bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-r-full transition duration-300" type="submit">
						<i class="bi bi-search"></i>
					</button>
				</div>
			</form>

			<!-- Right Section -->
			<div class="flex items-center space-x-6">
				<a href="#" class="text-white hover:text-gray-200 flex items-center transition duration-300">
					<i class="bi bi-gift mr-2"></i>Offers
				</a>
				<a href="#" class="text-white hover:text-gray-200 flex items-center transition duration-300">
					<i class="bi bi-info-circle mr-2"></i>Help
				</a>
				<a href="addtocart?action=viewcart" class="text-white hover:text-gray-200 flex items-center transition duration-300">
					<i class="bi bi-cart3 mr-2"></i>Cart
				</a>
				<a href="orderspage" class="text-white hover:text-gray-200 flex items-center transition duration-300">
					<i class="bi bi-clock-history mr-2"></i>Order History
				</a>
				<a href="logout" class="text-white hover:text-gray-200 flex items-center transition duration-300">
					<i class="bi bi-box-arrow-right mr-2"></i>Logout
				</a>
			</div>
		</div>
	</div>
</nav>

<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function(){
    // Disable submit
    $('#searchForm').on('submit', function(e){
        e.preventDefault();
    });

    // Search functionality
    $('#searchInput').on('keyup', function(){
        var value = $(this).val().toLowerCase();
        $('.restaurantCard').filter(function(){
            $(this).toggle($(this).data('name').toLowerCase().indexOf(value) > -1);
        });
    });
});

</script>
